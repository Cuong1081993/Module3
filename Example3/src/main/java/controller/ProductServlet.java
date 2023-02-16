package controller;

import exception.CategoryInvalidException;
import model.Category;
import model.Product;
import services.CategoryServiceJDBC;
import services.ICategoryService;
import services.IProductService;
import services.ProductServiceJDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = {"", "/product"})
public class ProductServlet extends HttpServlet {
    private IProductService productService;
    private ICategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceJDBC();
        categoryService = new CategoryServiceJDBC();
        List<Category> categories = categoryService.getAllCategory();
        if (getServletContext().getAttribute("categories") == null) {
            getServletContext().setAttribute("categories", categories);
        }
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
                showEditProduct(req, resp);
                break;
            case "delete":
                showDeleteProduct(req, resp);
                break;
            default:
                showListProduct(req, resp);
        }
    }

    private void showListProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = "";
        List<Category> categories = categoryService.getAllCategory();
        if (req.getParameter("search") != null) {
            search = req.getParameter("search");
        }
        try {
            List<Product> products = productService.getAllProduct();
            req.setAttribute("search", search);
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/product.jsp");
            requestDispatcher.forward(req, resp);
        }catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDeleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findProductById(id);

        req.setAttribute("product", product);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showEditProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormCreateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/create.jsp");
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
        int id = Integer.parseInt(req.getParameter("id"));
        productService.deleteProduct(id);
        resp.sendRedirect("/product");
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        int id = req.getParameter()
        Product product =


        validateIdView(errors, req, product);
        validateNameView(errors, req, product);
//        validatePriceView(errors, req, product);
//        validateQuantityView(errors, req, product);
        validateColorView(errors, req, product);
        validateDescriptionView(errors, req, product);
        validateCategoryView(errors, req, product);
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
                requestDispatcher = req.getRequestDispatcher("/edit.jsp");
                requestDispatcher.forward(req, resp);
            }
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
            requestDispatcher = req.getRequestDispatcher("/edit.jsp");
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

    private void validateColorView(List<String> errors, HttpServletRequest req, Product product) {
        String color = req.getParameter("color");
        product.setColor(color);
        if (color.equals("")) {
            errors.add("Color is not empty");
        }
    }

    private void validateQuantityView(List<String> errors, HttpServletRequest req, Product product) {


    }

    private void validateCategoryView(List<String> errors, HttpServletRequest req, Product product) {
        int idCategory = -1;
        try {
            idCategory = Integer.parseInt(req.getParameter("idCategory"));
            // kiem tra country co hop le hay khong
            if (categoryService.findCategoryById(idCategory) == null) {
                throw new CategoryInvalidException("Category is exists");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Category is not valid");
        } catch (CategoryInvalidException e) {
            errors.add(e.getMessage());
        }
        product.setIdCategory(idCategory);
    }

    private void validatePriceView(List<String> errors, HttpServletRequest req, Product product) {

    }

    private void validateNameView(List<String> errors, HttpServletRequest req, Product product) {
        String name = req.getParameter("name");
        product.setNameProduct(name);
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
            errors.add("ID product not valid");
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

//        validateIdView(errors, req, product);
        validateNameView(errors, req, product);
//        validatePriceView(errors, req, product);
//        validateQuantityView(errors, req, product);
        validateColorView(errors, req, product);
        validateDescriptionView(errors, req, product);
        validateCategoryView(errors, req, product);
        if (errors.isEmpty()) {
            product.setIdProduct(productService.getAllProduct().size() + 1);
            productService.addProduct(product);
            req.setAttribute("message", "Them san pham thanh cong");
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/create.jsp");
        requestDispatcher.forward(req, resp);
    }
}
