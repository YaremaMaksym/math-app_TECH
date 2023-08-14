package org.xsakon.root;

import org.xsakon.expression.Expression;
import org.xsakon.jdbc.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RootDao {
    public void save(Root root){
        String query = "INSERT INTO roots (id, expression_id, value) VALUES (nextval('root_sequence'), ?, ?)";

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, root.getExpressionId());
            preparedStatement.setDouble(2, root.getValue());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Expression> selectAllByRoot(double rootValue) {
        String query = "SELECT * FROM expressions " +
                "INNER JOIN roots ON expressions.id = roots.expression_id " +
                "WHERE roots.value = ?";

        List<Expression> expressions = new ArrayList<>();

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, rootValue);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Expression expression = new Expression(resultSet.getLong("id"), resultSet.getString("expression"));
                    expressions.add(expression);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expressions;
    }
}
