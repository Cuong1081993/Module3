package com.example.bakeryshop.service.jdbc;

import com.example.bakeryshop.model.Customer;
import com.example.bakeryshop.service.ICustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceJDBC extends DatabaseContext implements ICustomerService {
    private static final String SELECT_ALL_CUSTOMER = "select * from customer;";
    private static final String INSERT_CUSTOMER = "INSERT INTO `customer` (`name`, `address`, `idCountry`) VALUES (?, ?, ?);";
    private static final String FIND_BY_ID = "select * from customer where idcustomer = ";
    private static final String SP_GETALLCUSTOMER_BYIDCOUNTRY = "call spGetAllCustomerByIdCountry(?);";
    private static final String DELETE_CUSTOMER = "DELETE FROM `customer` WHERE (`idcustomer` = ?);";
    private static final String SELECT_CUSTOMERS_BY_KW_IDCOUNTRY = "SELECT * FROM customer where idCountry = ? and name like ?;";
    private static final String SELECT_CUSTOMERS_BY_KW_ALLCOUNTRY = "SELECT * FROM customer where name like ?;";
    private static final String SELECT_CUSTOMERS_BY_KW_ALLCOUNTRY_PAGGING = "SELECT SQL_CALC_FOUND_ROWS * FROM customer where name like ? limit ?, ?;";
    private static final String SELECT_CUSTOMERS_BY_KW_IDCOUNTRY_PAGGING = "SELECT SQL_CALC_FOUND_ROWS * FROM customer where idCountry = ? and name like ? limit ? , ? ";
    private static final String UPDATE_CUSTOMER = "UPDATE customer SET `name` = ?, address = ?, idcountry= ? WHERE `idcustomer` = ?;";

    private int noOfRecords;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMER);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                 customer = getCustomerFromResulset(rs);
            }
            customers.add(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    private Customer getCustomerFromResulset(ResultSet rs) throws SQLException {
        int id = rs.getInt("idcustomer");
        String name = rs.getString("name");
        String address = rs.getString("address");
        int idCountry = rs.getInt("idCountry");

        Customer customer = new Customer(id, name, address, idCountry);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomersByKwAndIdCountry(String kw, long idCountry) {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            if (idCountry == 1) {
                //SELECT * From customer where name like :
                preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_KW_ALLCOUNTRY);
                preparedStatement.setString(1, "%" + kw + "%");
            } else {
                //SELECT * FROM customer where idCountry = ? and name like?;";
                preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_KW_IDCOUNTRY);
                preparedStatement.setLong(1, idCountry);
                preparedStatement.setString(2, "%" + kw + "%");
            }
            System.out.println(this.getClass() + "getAllCustomerByKwAndIdCountry: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResulset(rs);
                customers.add(customer);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByIdCountryPagging(String kw, long idCountry, int offset, int numberOfPage) {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement;
            if (idCountry == -1) {
                //SELECT * FROM customer where name like ? limit ?, ?;
                preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_KW_ALLCOUNTRY_PAGGING);
                preparedStatement.setString(1, "%" + kw + "%");
                preparedStatement.setInt(2, offset);
                preparedStatement.setInt(3, numberOfPage);
            } else {
                //SELECT * FROM customer where idCountry = ? and name like ? limit ? , ? ";
                preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_KW_IDCOUNTRY_PAGGING);
                preparedStatement.setLong(1, idCountry);
                preparedStatement.setString(2, "%" + kw + "%");
                preparedStatement.setInt(3, offset);
                preparedStatement.setInt(4, numberOfPage);
            }
            System.out.println(this.getClass() + " getAllCustomersByIdCountryPagging: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResulset(rs);
                customers.add(customer);
            }
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            while (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setLong(3, customer.getIdCountry());

            preparedStatement.executeUpdate();

            connection.close();


        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Customer findCustomerById(long id) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = FIND_BY_ID + id;
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Customer customer = getCustomerFromResulset(rs);
                return customer;
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {  //UPDATE customer SET `name` = ?, address = ?, idcountry= ? WHERE `idcustomer` = ?;
            Connection connection = getConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(UPDATE_CUSTOMER);
           preparedStatement.setString(1,customer.getName());
           preparedStatement.setString(2,customer.getAddress());
           preparedStatement.setLong(3,customer.getIdCountry());
           preparedStatement.setLong(4,customer.getId());
           preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(long id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
            preparedStatement.setLong(1, id);

            System.out.println(this.getClass() + " deleteCustomer: " + preparedStatement);

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public List<Customer> getAllCustomerByIdCountry(long idCountry) {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection  = getConnection();
            CallableStatement callableStatement = connection.prepareCall(SP_GETALLCUSTOMER_BYIDCOUNTRY);
            callableStatement.setLong(1, idCountry);

            System.out.println(this.getClass() + " getAllCustomerByIdCountry: " + callableStatement);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResulset(rs);
                customers.add(customer);
            }
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return customers;
    }

    @Override
    public int getNoOfRecords() {
        return this.noOfRecords;
    }
}
