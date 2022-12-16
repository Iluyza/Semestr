package org.example.model.managers;

import org.example.model.database.PostgresConnectionProvider;
import org.example.model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionManager {

    public static boolean checkPermission(User user){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select permission from users where id = ? and username = ? and password = ?");
            statement.setInt(1,user.getId());
            statement.setString(2, user.getUsername());
            statement.setInt(3,user.getPassword());
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1)==0;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean checkPermission(int id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select permission from users where id = ?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(1)==0;
        } catch (SQLException e) {
            return false;
        }
    }
}
