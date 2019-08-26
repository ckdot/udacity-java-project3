package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private final ReviewRepository reviews;
    private final ProductRepository products;

    public ReviewsController(ReviewRepository reviews, ProductRepository products) {
        this.reviews = reviews;
        this.products = products;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@RequestBody Review review, @PathVariable("productId") Integer productId) {
        Optional<Product> product = this.products.findById(productId);

        if (!product.isPresent()) {
            return new ResponseEntity<>("Could not find product.", HttpStatus.NOT_FOUND);
        }

        review.setProduct(product.get());

        review = this.reviews.save(review);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> product = this.products.findById(productId);

        if (!product.isPresent()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

        List<Review> reviews = this.reviews.findByProductId(productId);

        return new ResponseEntity<List<?>>(reviews, HttpStatus.OK);
    }
}