package com.udacity.course3.reviews.repository;

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
public class ReviewRepositoryTest {
    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;

    @Test
    public void testFindById() {
        Review savedReview = this.createReview("some_text");
        Optional<Review> foundReview = this.reviewRepository.findById(savedReview.getId());

        Assert.isTrue(
            savedReview.hashCode() == foundReview.get().hashCode(),
            "Saved review is not equal to found review."
        );
    }

    @Test
    public void testFindAll() {
        Review firstSavedReview = this.createReview("first_text");
        Review secondSavedReview = this.createReview("second_text");

        Iterable<Review> result = this.reviewRepository.findAll();
        List<Review> foundReviews = Lists.newArrayList(result);

        Assert.isTrue(
            foundReviews.spliterator().getExactSizeIfKnown() == 2,
            "Number of found reviews is not correct."
        );
        Assert.isTrue(
            foundReviews.get(0).hashCode() == firstSavedReview.hashCode(),
            "First found review doesn't match expected result."
        );

        Assert.isTrue(
            foundReviews.get(1).hashCode() == secondSavedReview.hashCode(),
            "Second found comment doesn't match expected result."
        );
    }

    @Test
    public void testFindByProductId() {
        Product firstProduct = new Product();
        firstProduct.setName("first_product_name");

        Product secondProduct = new Product();
        secondProduct.setName("second_product_name");

        this.productRepository.save(firstProduct);
        this.productRepository.save(secondProduct);

        Review firstReview = this.createReview("first_review_text", firstProduct);
        this.createReview("second_review_text", secondProduct);
        this.createReview("third_review_text", secondProduct);

        Iterable<Review> result = this.reviewRepository.findByProductId(firstProduct.getId());
        List<Review> foundReviews = Lists.newArrayList(result);

        Assert.isTrue(
                foundReviews.size() == 1,
                "Number of found reviews is not correct."
        );
        Assert.isTrue(
                foundReviews.get(0).hashCode() == firstReview.hashCode(),
                "First found review doesn't match expected result."
        );
    }

    private Review createReview(String text)
    {
        Product product = new Product();
        product.setName("some_product_name");

        this.productRepository.save(product);

        return this.createReview(text, product);
    }

    private Review createReview(String text, Product product)
    {
        Review review = new Review();
        review.setText(text);
        review.setProduct(product);

        return this.reviewRepository.save(review);
    }
}
