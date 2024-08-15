package com.example.productmanagerthymeleaf.service;

import com.example.productmanagerthymeleaf.model.Product;

import java.util.List;

public interface IProductService {
        List <Product> findAll();
       Product findById(int id);
       void addProduct(Product product);
       void updateProduct(int id,Product product);
       void deleteProduct(int id);
       List<Product> searchByName(String name);

}
