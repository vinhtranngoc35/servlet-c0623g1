package com.example.demo1.service;

import com.example.demo1.dao.ProductDAO;
import com.example.demo1.model.Product;
import com.example.demo1.service.dto.Page;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static List<Product> products = new ArrayList<>();

    private static int idCurrent;

    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }


    public void create(Product product){
        product.setId(++idCurrent);
        products.add(product);
    }

    public Page<Product> getProducts(int page){
        return productDAO.findAll(page);
    }
}