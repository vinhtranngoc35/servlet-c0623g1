package com.example.demo1.controller;

import com.example.demo1.service.ProductImportService;
import com.example.demo1.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product-import", name = "productImportController")
public class ProductImportController extends HttpServlet {
    private ProductService productService;

    private ProductImportService productImportService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            showList(req,resp);
            return;
        }

        if(action.equals("edit")){
            showEdit(req, resp);
            return;
        }
        var products = productService.findAll();
        req.setAttribute("products", products);
        req.setAttribute("productsJSON", new ObjectMapper().writeValueAsString(products));
        req.getRequestDispatcher("product-import/create.jsp").forward(req,resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setAttribute("productImport", productImportService
                .findById(Integer.parseInt(req.getParameter("id"))));
        var products = productService.findAll();
        req.setAttribute("products", products);
        req.setAttribute("productsJSON", new ObjectMapper().writeValueAsString(products));
        req.getRequestDispatcher("product-import/edit.jsp").forward(req,resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productImports", productImportService.findAll());
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("product-import/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if("edit".equals(action)){
            edit(req, resp);
            return;
        }
        productImportService.create(req);
        resp.sendRedirect("/product-import?message=Created Successfully");


    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productImportService.update(req);
        resp.sendRedirect("/product-import?message=Updated Successfully");
    }

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        productImportService = new ProductImportService();
    }
}