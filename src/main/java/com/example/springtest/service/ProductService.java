package com.example.springtest.service;

import com.example.springtest.model.Product;
import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    Product getProductById(Long id);
}