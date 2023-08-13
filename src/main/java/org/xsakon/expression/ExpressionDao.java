package org.xsakon.expression;

import org.xsakon.jdbc.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExpressionDao {
    public void save(Expression expression){
        String query = "INSERT INTO expressions (id, expression) VALUES (nextval('expression_sequence'), ?)";

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, expression.getExpression());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
