package com.example.bakeryshop.service.jdbc;


import com.example.bakeryshop.model.Country;
import com.example.bakeryshop.model.Customer;
import com.example.bakeryshop.model.Product;
import com.example.bakeryshop.service.ICountryService;
import com.example.bakeryshop.service.IProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductServiceJDBC extends DatabaseContext implements IProductService {
    private static final String SELECT_ALL_PRODUCT = "select * from products";
    private static final String INSERT_PRODUCT = "INSERT INTO `products` (`name`, `image`, `price`,`title`,`description`) VALUES (?, ?, ?, ?, ?);";
    private static final String FIND_PRODUCT_BY_ID = "select * from `products` where idProduct = ";
    private static final String UPDATE_PRODUCT = "UPDATE products SET `name` = ?, image = ?, price = ?, title=?, description =? WHERE `idProduct` = ?;";
    private static final String DELETE_PRODUCT = "DELETE FROM `products` WHERE (`idProduct` = ?);";
    private static final String SELECT_ALL_PRODUCT_PAGGING = "SELECT SQL_CALC_FOUND_ROWS * FROM products where `name` like ? limit ?,?";

    private int noOfRecords;
    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Product product = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                product = getProductFromResulset(rs);
            }
            productList.add(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public List<Product> getAllProducts(String nameSearch, int page, int numberOfPage) {
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            //SELECT_ALL_PRODUCT_PAGGING = "SELECT * FROM products where `name` like ? limit ?,?";
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT_PAGGING);
            preparedStatement.setString(1, "%" + nameSearch + "%");
            preparedStatement.setInt(2, page);
            preparedStatement.setInt(3, numberOfPage);


            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResulset(rs);
                productList.add(product);
            }
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            while (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }

        return productList;
    }

    @Override
    public List<Product> getAllProductsPagging(String kw, int offset, int numberOfPage) {

        List<Product> products = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;

                preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT_PAGGING);
                preparedStatement.setString(1, "%" + kw + "%");
                preparedStatement.setInt(2,offset);
                preparedStatement.setInt(3,numberOfPage);
            System.out.println(this.getClass() + " getAllProductPagging: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResulset(rs);
                products.add(product);
            }
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            while (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    private Product getProductFromResulset(ResultSet rs) throws SQLException {
        int idProduct = rs.getInt("idProduct");
        String name = rs.getString("name");
        String image = rs.getString("image");
        double price = rs.getDouble("price");
        String title = rs.getString("title");
        String description = rs.getString("description");
        Product product = new Product(idProduct, name, image, price, title, description);
        return product;
    }


    @Override
    public void addProduct(Product product) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImage());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getTitle());
            preparedStatement.setString(5, product.getDescription());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            connection.close();


        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Product findProductById(int id) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = FIND_PRODUCT_BY_ID + id;
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Product product = getProductFromResulset(rs);
                return product;
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void updateProduct(Product product) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImage());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getTitle());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6,product.getIdProduct());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            preparedStatement.setInt(1, id);

            System.out.println(this.getClass() + " deleteProduct: " + preparedStatement);

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public int getNoOfRecords() {
        return this.noOfRecords;
    }
}

