package com.example.bakeryshop.service.jdbc;

import com.example.bakeryshop.model.Country;
import com.example.bakeryshop.service.ICountryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryServiceJBDC extends DatabaseContext implements ICountryService {
    private static final String SELECT_ALL_COUNTRY = "select * from country;";
    private static final String FIND_COUNTRY_BY_ID = "select * from country where idCountry = ";
    private static final String SP_INSERTCOUNTRY = "call spInsertCountry(?, ?);";

    @Override
    public List<Country> getAllCountry() {
        List<Country> countries = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_ALL_COUNTRY);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                countries.add(country);
            }

            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return countries;
    }

    private Country getCountryFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idcountry");
        String name = rs.getString("name");

        Country country = new Country(id, name);
        return country;
    }


    @Override
    public Country findCountryById(long id) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = FIND_COUNTRY_BY_ID + id;
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                return country;
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public boolean insertCountryBySP(String nameCountry) {
        boolean check = false;
        try {
            Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall(SP_INSERTCOUNTRY);
            callableStatement.setString(1, nameCountry);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);

            System.out.println(this.getClass() + " insertCountryBySP: " + callableStatement);
            callableStatement.execute();

            check = callableStatement.getBoolean(2);

        } catch (SQLException sqlException) {
            printSQLException(sqlException);

        }
        return check;
    }
}
