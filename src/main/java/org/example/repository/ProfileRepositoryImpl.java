package org.example.repository;
import org.example.entity.Profile;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ProfileRepositoryImpl {
    public Profile getUserByUsernameByPassword(String name){

        String sql = "SELECT user_id, email, password, name, created_at, deleted_at FROM user WHERE name ='" + name + "'";
        try (
                Datasource dataSource = new Datasource();
                // get connection with our database
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        )
        {
            if(resultSet.next()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date createdAt = resultSet.getString("created_at").equals("") ?
                        null : sdf.parse(resultSet.getString("created_at"));
                Date deletedAt = resultSet.getString("deleted_at").equals("") ? null :
                        sdf.parse(resultSet.getString("deleted_at"));

                Profile user = new Profile(
                        resultSet.getLong("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        createdAt,
                        deletedAt
                );

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

