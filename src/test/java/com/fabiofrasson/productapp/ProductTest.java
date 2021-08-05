package com.fabiofrasson.productapp;

import com.fabiofrasson.productapp.product.Product;
import com.fabiofrasson.productapp.product.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTest {

  @Autowired private ProductRepository repository;

  @Test
  @Rollback(value = false)
  @Order(1)
  public void shouldCreateProduct() {
    Product product = new Product("iPhone 10", 789);
    Product savedProduct = repository.save(product);

    Assertions.assertNotNull(savedProduct);
  }

  @Test
  @Rollback(false)
  @Order(2)
  public void shouldNotFindProductByName() {
    String name = "iPhone 11";

    Product product = repository.findByName(name);

    Assertions.assertNull(product);
  }

  @Test
  @Rollback(false)
  @Order(3)
  public void shouldFindProductByName() {
    String name = "iPhone 10";
    Product product = repository.findByName(name);

    Assertions.assertEquals(product.getName(), name);
  }

  @Test
  @Rollback(false)
  @Order(5)
  public void shouldUpdateProduct() {
    String productName = "Kindle Reader";
    Product product = new Product(productName, 199);
    product.setId(1);

    repository.save(product);

    Product updatedProduct = repository.findByName(productName);

    Assertions.assertEquals(updatedProduct.getName(), productName);
  }

  @Test
  @Order(4)
  public void shouldListProducts() {
    List<Product> products = repository.findAll();
    List<Product> productList = new ArrayList<>();

    for (Product product : products) {
      System.out.println(product);
    }

    Assertions.assertNotSame(products, productList);
  }

  @Test
  @Rollback(false)
  @Order(6)
  public void shouldDeleteProduct() {
    Integer id = 1;

    boolean existsBeforeDeletion = repository.existsById(id);

    repository.deleteById(id);

    boolean notExistsAfterDeletion = repository.existsById(id);

    Assertions.assertTrue(existsBeforeDeletion);
    Assertions.assertFalse(notExistsAfterDeletion);
  }
}
