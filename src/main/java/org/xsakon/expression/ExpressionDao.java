package org.xsakon.expression;

import org.xsakon.jdbc.DBConnectionManager;

import java.sql.*;
import java.util.*;

public class ExpressionDao {
    public Optional<Long> save(Expression expression){
        String query = "INSERT INTO expressions (id, expression) VALUES (nextval('expression_sequence'), ?)";

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, expression.getExpression());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                return Optional.of(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Expression> findByExpression(String expressionInputStr) {
        String query = "SELECT * FROM expressions " +
                "WHERE expression = ?";

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, expressionInputStr);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    long expressionId = resultSet.getLong("id");
                    String expressionStr = resultSet.getString("expression");

                    Expression expression = new Expression(expressionId, expressionStr);
                    return Optional.of(expression);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
