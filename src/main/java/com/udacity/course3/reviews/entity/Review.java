package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Review {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @ManyToOne
    private Product product;

    @OneToMany
    private Collection<Comment> comments;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
