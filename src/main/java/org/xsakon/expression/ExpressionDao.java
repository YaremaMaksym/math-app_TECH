package org.xsakon.expression;

import org.xsakon.jdbc.DBConnectionManager;

import java.sql.*;
import java.util.Optional;

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
}
