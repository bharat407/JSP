package com.example.springtest.controller;

import com.example.springtest.model.Product;
import com.example.springtest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        response.put("status", "success");
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Product product = productService.getProductById(id);
        if (product != null) {
            response.put("status", "success");
            response.put("data", product);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        Product newProduct = productService.addProduct(product);
        response.put("status", "success");
        response.put("data", newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            response.put("status", "success");
            response.put("data", updatedProduct);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        productService.deleteProduct(id);
        response.put("status", "success");
        response.put("message", "Product deleted successfully");
        return ResponseEntity.ok(response);
    }
}
