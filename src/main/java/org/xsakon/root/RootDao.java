package org.xsakon.root;

import org.xsakon.jdbc.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}