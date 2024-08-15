package com.example.productmanagerthymeleaf.service;

import com.example.productmanagerthymeleaf.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductService implements IProductService {
    private static List<Product> productList;

    static {
        productList = new ArrayList<>();
        productList.add(new Product(0, "Nike", 555555.2, "Xin ", "kien nguyen"));
        productList.add(new Product(1, "Channel", 6666666.5, "Xin", "em Trang"));
        productList.add(new Product(2, "Pt2000", 11111.6, "Cui", "Anh Khoi"));
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Product findById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }


    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }

    @Override
    public void updateProduct(int id, Product product) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) {
                productList.set(i, product);
                return;
            }
        }
    }

    @Override
    public void deleteProduct(int id) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) {
                productList.remove(id);
                return;
            }
        }
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                result.add(product);
            }
        }
        return result;
    }
}
