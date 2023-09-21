package com.example.demo1.dao;

import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO  extends DatabaseConnection{

    public Page<Product> findAll(int page){
        Page<Product> result = new Page<>();
        result.setCurrentPage(page);
        List<Product> content = new ArrayList<>();
        String SELECT_ALL = "SELECT p.*, c.name category_name " +
                "FROM products p JOIN categories c on " +
                "c.id = p.category_id " +
                "LIMIT 2 OFFSET ?";
        String SELECT_COUNT = "SELECT COUNT(1) cnt FROM products";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, (page-1) * 2);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                content.add(product);
            }
            result.setContent(content);
            PreparedStatement preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            var rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt") /2));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return result;
    }
}