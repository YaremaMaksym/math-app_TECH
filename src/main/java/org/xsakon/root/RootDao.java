package org.xsakon.root;

import org.xsakon.expression.Expression;
import org.xsakon.jdbc.DBConnectionManager;

import java.sql.*;
import java.util.*;

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

    public List<Expression> selectAllByRoots(List<Double> rootValues) {
        String query = "SELECT e.id AS expression_id, e.expression AS expression, r.value AS root_value " +
                "FROM expressions e " +
                "RIGHT JOIN roots r ON e.id = r.expression_id " +
                "WHERE e.id IN (SELECT expression_id FROM roots WHERE value IN (" + String.join(",", Collections.nCopies(rootValues.size(), "?")) + "))";

        Map<Long, Expression> expressionMap = new HashMap<>();
        List<Expression> expressions = new ArrayList<>();

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < rootValues.size(); i++) {
                preparedStatement.setDouble(i + 1, rootValues.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    long expressionId = resultSet.getLong("expression_id");
                    String expressionStr = resultSet.getString("expression");
                    double rootValue = resultSet.getDouble("root_value");

                    Expression expression = expressionMap.getOrDefault(expressionId, new Expression(expressionId, expressionStr));
                    expression.addRoot(rootValue);

                    expressionMap.put(expressionId, expression);
                }
            }

            expressions.addAll(expressionMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expressions;
    }

    public List<Expression> selectAllWithSingleRoot() {
        String query = "SELECT expressions.id, expression, MAX(roots.value) AS root_value " +
                "FROM expressions\n" +
                "JOIN roots ON expressions.id = roots.expression_id " +
                "GROUP BY expressions.id, expression " +
                "HAVING COUNT(expression_id) = 1";

        List<Expression> expressions = new ArrayList<>();

        try (Connection connection = DBConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    long expressionId = resultSet.getLong("id");
                    String expressionStr = resultSet.getString("expression");
                    double rootValue = resultSet.getDouble("root_value");

                    Expression expression = new Expression(expressionId, expressionStr);
                    expression.addRoot(rootValue);
                    expressions.add(expression);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expressions;
    }
}
