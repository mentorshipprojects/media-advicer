package org.example.repository;

import org.example.entity.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static com.neovisionaries.i18n.LanguageCode.pa;

//public class ProfileRepositoryImpl{
//    private PreparedStatement statement;
//    private Connection connection;
//    private ResultSet result;
//
//
//    public ProfileRepositoryImpl() {
//        try {
//
//
//            Properties prop = new Properties();
//
//            // load a properties file
//            prop.setProperty("username", "User1");
//            prop.setProperty("password", "1122");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/xxnote2",prop);
//            // get the property value and print it out
//            System.out.println(prop.getProperty("jdbc.host"));
//            System.out.println(prop.getProperty("jdbc.username"));
//            System.out.println(prop.getProperty("jdbc.password"));
//
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//
////    ADD USER
//
//
//    public void addUser(String username, String password){
//        //Add new user
//        String sql = "INSERT INTO user(chatId, name, password) VALUES(?,?,?)";
//        try {
//            statement = connection.prepareStatement(sql);
////            statement.setLong(1, chatId);
//            statement.setString(2, username);
//            statement.setString(3, password);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Error " + e);
//        }
//    }


public class ProfileRepositoryImpl {
    public Profile getUserByUsernameByPassword(String username){


//        String sql = "SELECT name, FROM user WHERE email=" + username + "'" +
//                "AND password=" + password + "'";
        String sql = "select * FROM users WHERE name = username";
        try (
                Datasource dataSource = new Datasource();
                // get connection with our database
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        )
        {
            if(resultSet.next()) {

                Profile user = new Profile(
                        resultSet.getLong("chatId"),
                        resultSet.getString("password"),
                        resultSet.getString("username")
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






//    GET USER


//    public ArrayList<String> getUser(String username){
//        //Get a userdata by chatId
//        ArrayList <String> arrayList = new ArrayList<String>();
//
//        try {
//            String query = "select * FROM users WHERE name = ?";
//            statement = connection.prepareStatement(query);
////            statement.setLong(1, chatId);
//            result = statement.executeQuery();
//
//            while (result.next()){
//                arrayList.add(result.getString("name"));
//                arrayList.add(result.getString("password"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            closeDB();
//        }
//        return arrayList;
//    }
//
//    public void closeDB(){
//        try {
//            // Объязательно закрываем. После 8 часов работы если БД не закрыта - она упадет.
//            result.close();
//            statement.close();
//            connection.close();
//        } catch (Exception e) { }
//    }
//}
