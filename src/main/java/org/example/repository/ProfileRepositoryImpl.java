package org.example.repository;

import org.example.entity.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProfileRepositoryImpl implements ProfileRepository {

    public ProfileRepositoryImpl() {
        try (InputStream input = ProfileRepositoryImpl.class.getClassLoader().getResourceAsStream("db.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("jdbc.host"));
            System.out.println(prop.getProperty("jdbc.username"));
            System.out.println(prop.getProperty("jdbc.password"));

            // connection to DB
            //
            //

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Profile profile) {

    }

    @Override
    public void delete(Long chatId) {

    }

    @Override
    public Profile get(Long chatId) {
        return null;
    }
}