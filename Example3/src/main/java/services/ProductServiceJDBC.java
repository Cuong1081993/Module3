package services;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceJDBC extends DatabaseContext implements IProductService {

    private static final String SELECT_ALL_PRODUCT = "select * from products;";
    private static final String SELECT_PRODUCT_BY_KW_ALLCATEGORY = "select * from products where name like ?;";
    private static final String SELECT_PRODUCT_BY_KW_IDCATEGORY = "select * from products where idCategory=? and name like?;";
    private static final String INSERT_PRODUCT = "INSERT INTO `products` (`name`, `price`,`quantity`, `color`,`description`,`idCategory`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String FIND_PRODUCT_BY_ID = "select * from products where idProduct=";
    private static final String UPDATE_PRODUCT = "UPDATE products SET `name`=?, `price`=?, `quantity`=?,`color`=?,`description`=?,`idCategory`=? WHERE `idProduct` =?; ";
    private static final String DELETE_PRODUCT = "DELETE  FROM products where (`idProduct` =?);";
    private static final String SP_GETALLPRODUCT_BYIDCATEGORY = "call spGetAllProductByIdCategory(?);";
    private static final String SELECT_PRODUCT_BY_KW_ALLCATEGORY_PAGGING = "SELECT SQL_CALC_FOUND_ROWS * FROM products where name like ? limit ?, ?;";
    private static final String SELECT_PRODUCT_BY_KW_IDCATEGORY_PAGGING = "SELECT SQL_CALC_FOUND_ROWS * FROM products where idCategory = ? and name like ? limit ? , ? ";

    private int noOfRecords;

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        Product product = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                product = getProductFromResultSet(rs);
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idProduct");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int quantity = rs.getInt("quantity");
        String color = rs.getString("color");
        String description = rs.getString("description");
        int idCategory = rs.getInt("idCategory");

        Product product = new Product(id, name, price, quantity, color, description, idCategory);
        return product;
    }


    @Override
    public List<Product> getAllProductByKwAndIdCategory(String kw, int idCategory) {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            if (idCategory == 1) {
                //SELECT * From product where name like ?
                preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_KW_ALLCATEGORY);
                preparedStatement.setString(1, "%" + kw + "%");
            } else {
                //SELECT * FROM customer where idCountry = ? and name like?;";
                preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_KW_IDCATEGORY);
                preparedStatement.setInt(1, idCategory);
                preparedStatement.setString(2, "%" + kw + "%");
            }
            System.out.println(this.getClass() + "getAllProductByKwAndIdCategory: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResultSet(rs);
                products.add(product);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void addProduct(Product product) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, product.getNameProduct());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getIdCategory());

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
                Product product = getProductFromResultSet(rs);
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
            preparedStatement.setString(1, product.getNameProduct());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getIdCategory());
            preparedStatement.setInt(7,product.getIdProduct());

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllProductByIdCategory(int idCategory) {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall(SP_GETALLPRODUCT_BYIDCATEGORY);
            callableStatement.setLong(1, idCategory);

            System.out.println(this.getClass() + " getAllProductByIdCategory: " + callableStatement);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResultSet(rs);
                products.add(product);
            }
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return products;
    }

    @Override
    public int getNoOfRecords() {
        return 0;
    }

    @Override
    public List<Product> getAllProductByIdCategoryPagging(String kw, int idCategory, int offset, int numberOfPage) {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            if (idCategory == -1) {
                //SELECT * FROM customer where name like ? limit ?, ?;
                preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_KW_ALLCATEGORY_PAGGING);
                preparedStatement.setString(1, "%" + kw + "%");
                preparedStatement.setInt(2, offset);
                preparedStatement.setInt(3, numberOfPage);
            } else {
                //SELECT * FROM customer where idCountry = ? and name like ? limit ? , ? ";
                preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_KW_IDCATEGORY_PAGGING);
                preparedStatement.setLong(1, idCategory);
                preparedStatement.setString(2, "%" + kw + "%");
                preparedStatement.setInt(3, offset);
                preparedStatement.setInt(4, numberOfPage);
            }
            System.out.println(this.getClass() + " getAllProductByIdCategoryPagging: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResultSet(rs);
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
}
