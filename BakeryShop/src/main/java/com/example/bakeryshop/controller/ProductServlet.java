package com.example.bakeryshop.controller;

import com.example.bakeryshop.Utils.ValidateUtils;
import com.example.bakeryshop.model.Customer;
import com.example.bakeryshop.model.Product;
import com.example.bakeryshop.service.IProductService;
import com.example.bakeryshop.service.jdbc.ProductServiceJDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet",urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {
    private IProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                showFormCreateProduct(req, resp);
                break;
            case "edit":
                showFormEditProduct(req, resp);
                break;
            case "delete":
                showFormDeleteProduct(req, resp);
                break;
            default:
                showListProduct(req, resp);
        }

    }

    private void showListProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String kw = "";
        int numberOfPage = 3;
        int page = 1;
        List<Product> productList = productService.getAllProducts();

        if (req.getParameter("kw") != null) {
            kw = req.getParameter("kw");
        }
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<Product> productsPagging = productService.getAllProducts(kw,(page - 1) * numberOfPage, numberOfPage);

        req.setAttribute("kw", kw);
        req.setAttribute("products", productsPagging);

        int noOfRecords = productService.getNoOfRecords();

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / numberOfPage);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product.jsp");

        requestDispatcher.forward(req, resp);
    }

    private void showFormDeleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findProductById(id);

        req.setAttribute("product", product);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/deleteProduct.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormEditProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = null;
        Product product;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if ((product = productService.findProductById(id)) == null) {
                error = "ID product not exists";
                req.setAttribute("error", error);
            } else {
                req.setAttribute("product", product);
            }
        } catch (NumberFormatException numberFormatException) {
            error = "ID product not valid";
            req.setAttribute("error", error);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/editProduct.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormCreateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/createProduct.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create": 
                insertProduct(req, resp);
                break;
            case "edit":
                editProduct(req, resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            default:
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idProduct = Integer.parseInt(req.getParameter("id"));
        productService.deleteProduct(idProduct);
        resp.sendRedirect("/product");
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Product product = new Product();


        validateIdView(errors, req, product);
        validateNameProductView(errors, req, product);
        validateImageView(errors, req, product);
        validatePriceView(errors, req, product);
        validateTitleView(errors, req, product);
        validateDescriptionView(errors, req, product);
//        System.out.println(customer);
        RequestDispatcher requestDispatcher;
//        System.out.println(errors);
        if (errors.isEmpty()) {
            try {
                productService.updateProduct(product);
                resp.sendRedirect("/product");
            } catch (Exception ex) {
                errors.add(ex.getMessage());
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
                requestDispatcher = req.getRequestDispatcher("/editProduct.jsp");
                requestDispatcher.forward(req, resp);
            }
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("customer", product);
            requestDispatcher = req.getRequestDispatcher("/editProduct.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private void validateDescriptionView(List<String> errors, HttpServletRequest req, Product product) {
        String description = req.getParameter("description");
        product.setDescription(description);
        if (description.equals("")) {
            errors.add("Description is not empty");
        }
    }

    private void validateTitleView(List<String> errors, HttpServletRequest req, Product product) {
        String title = req.getParameter("title");
        product.setTitle(title);
        if (title.equals("")) {
            errors.add("Title is not empty");
        }
    }

    private void validatePriceView(List<String> errors, HttpServletRequest req, Product product) {
    }

    private void validateImageView(List<String> errors, HttpServletRequest req, Product product) {
        String image = req.getParameter("image");
        product.setImage(image);
        if (image.equals("")) {
            errors.add("Image is not empty");
        } else {
            if (ValidateUtils.isImageValid(image) == false) {
                errors.add("Image does not match");
            }
        }
    }

    private void validateNameProductView(List<String> errors, HttpServletRequest req, Product product) {
        String name = req.getParameter("name");
        product.setName(name);
        if (name.equals("")) {
            errors.add("Name is not empty");
        }
    }

    private boolean validateIdView(List<String> errors, HttpServletRequest req, Product product) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if (productService.findProductById(id) == null) {
                throw new NullPointerException("Product not exists");
            }
            product.setIdProduct(id);
        } catch (NumberFormatException numberFormatException) {
            errors.add("ID Product not valid");
            return false;
        } catch (NullPointerException nullPointerException) {
            errors.add(nullPointerException.getMessage());
            return false;
        }
        return true;

    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Product product = new Product();


        validateNameProductView(errors, req, product);
        validateImageView(errors, req, product);
        validatePriceView(errors, req, product);
        validateTitleView(errors, req, product);
        validateDescriptionView(errors, req, product);
        if (errors.isEmpty()) {
            product.setIdProduct(productService.getAllProducts().size() + 1);
            productService.addProduct(product);
            req.setAttribute("message", "Add product successfully");
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/createProduct.jsp");
        requestDispatcher.forward(req, resp);
    }
}
