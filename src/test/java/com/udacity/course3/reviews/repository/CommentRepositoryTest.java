package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    public void testFindById() {
        Comment savedComment = this.createComment("some_text");
        Optional<Comment> foundComment = this.commentRepository.findById(savedComment.getId());

        Assert.isTrue(
            savedComment.hashCode() == foundComment.get().hashCode(),
            "Saved comment is not equal to found comment."
        );
    }

    @Test
    public void testFindAll() {
        Comment firstSavedComment = this.createComment("first_text");
        Comment secondSavedComment = this.createComment("second_text");

        Iterable<Comment> result = this.commentRepository.findAll();
        List<Comment> foundComments = Lists.newArrayList(result);

        Assert.isTrue(
            foundComments.spliterator().getExactSizeIfKnown() == 2,
            "Number of found comments is not correct."
        );
        Assert.isTrue(
            foundComments.get(0).hashCode() == firstSavedComment.hashCode(),
            "First found comment doesn't match expected result."
        );

        Assert.isTrue(
            foundComments.get(1).hashCode() == secondSavedComment.hashCode(),
            "Second found comment doesn't match expected result."
        );
    }

    @Test
    public void testFindByReviewId() {
        Product product = new Product();
        product.setName("some_product_name");

        this.productRepository.save(product);

        Review firstReview = new Review();
        firstReview.setProduct(product);
        firstReview.setText("first_review_text");

        Review secondReview = new Review();
        secondReview.setProduct(product);
        secondReview.setText("second_review_text");

        this.reviewRepository.save(firstReview);
        this.reviewRepository.save(secondReview);

        Comment firstReviewComment = createComment("first_review_comment", firstReview);

        createComment("second_review_comment", secondReview);
        createComment("another_second_review_comment", secondReview);

        Iterable<Comment> result = this.commentRepository.findByReviewId(firstReview.getId());
        List<Comment> foundComments = Lists.newArrayList(result);

        Assert.isTrue(
                foundComments.size() == 1,
                "Number of found comments is not correct."
        );
        Assert.isTrue(
                foundComments.get(0).hashCode() == firstReviewComment.hashCode(),
                "First found comment doesn't match expected result."
        );
    }

    private Comment createComment(String text)
    {
        Product product = new Product();
        product.setName("some_product_name");

        this.productRepository.save(product);

        Review review = new Review();
        review.setText("some_review_text");
        review.setProduct(product);

        return this.createComment(text, review);
    }

    private Comment createComment(String text, Review review)
    {
        Product product = new Product();
        product.setName("some_product_name");

        this.productRepository.save(product);
        this.reviewRepository.save(review);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setReview(review);

        comment = this.commentRepository.save(comment);

        return comment;
    }
}
