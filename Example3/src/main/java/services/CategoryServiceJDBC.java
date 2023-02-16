package services;

import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceJDBC extends DatabaseContext implements ICategoryService {


    private static final String SELECT_ALL_CATEGORY ="select * from category" ;
    private static final String FIND_CATEGORY_BY_ID = "select * from  category where idCategory=";
    private static final String SP_INSERTCATEGORY = "call spInsertCategory(?,?)";

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Category category = getCategoryByResultSet(rs);
                categories.add(category);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return categories;
    }

    private Category getCategoryByResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCategory");
        String nameCategory = rs.getString("nameCategory");
        Category category = new Category(id, nameCategory);
        return category;
    }

    @Override
    public Category findCategoryById(int id) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = FIND_CATEGORY_BY_ID + id;
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Category category = getCategoryByResultSet(rs);
                return category;
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public boolean insertCategoryBySP(String nameCategory) {
        boolean check = false;
        try {
            Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall(SP_INSERTCATEGORY);
            callableStatement.setString(1, nameCategory);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);

            System.out.println(this.getClass() + " insertCategoryBySP: " + callableStatement);
            callableStatement.execute();

            check = callableStatement.getBoolean(2);

        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return check;
    }
}
