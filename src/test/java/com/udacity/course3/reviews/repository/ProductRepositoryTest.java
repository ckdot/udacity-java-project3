package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
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
public class ProductRepositoryTest {
    @Autowired private ProductRepository productRepository;

    @Test
    public void testFindById() {
        String name = "mockname";

        Product product = new Product();
        product.setName(name);

        product = this.productRepository.save(product);
        Optional<Product> foundProduct = this.productRepository.findById(product.getId());

        Assert.isTrue(product.hashCode() == foundProduct.get().hashCode(), "Saved product is not equal to found product.");
    }

    @Test
    public void testFindAll() {
        String firstName = "first_name";
        String secondName = "second_name";

        Product firstProduct = new Product();
        firstProduct.setName(firstName);

        Product secondProduct = new Product();
        secondProduct.setName(secondName);

        firstProduct = this.productRepository.save(firstProduct);
        secondProduct = this.productRepository.save(secondProduct);

        Iterable<Product> result = this.productRepository.findAll();
        List<Product> products = Lists.newArrayList(result);

        Assert.isTrue(products.spliterator().getExactSizeIfKnown() == 2, "Number of found products is not correct.");
        Assert.isTrue(products.get(0).hashCode() == firstProduct.hashCode(), "First found product doesn't match expected result.");
        Assert.isTrue(products.get(1).hashCode() == secondProduct.hashCode(), "Second found product doesn't match expected result.");
    }
}
