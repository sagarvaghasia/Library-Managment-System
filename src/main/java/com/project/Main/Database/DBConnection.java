package com.project.Main.Database;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements IDBConnection {

    private static String databaseUrl;
    private static String databaseUser;
    private static IDBConnection dbConnection;
    private static String databasePassword;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Getter
    private Connection connection;

    private DBConnection() {
        this.databaseUrl = "jdbc:mysql://" + System.getenv("DATABASE_URL") + ":" +
                System.getenv("DATABASE_PORT") + "/" +
                System.getenv("DATABASE_NAME");
        this.databaseUser = System.getenv("DATABASE_USER");
        this.databasePassword = System.getenv("DATABASE_PASSWORD");
    }

    public static IDBConnection instance() {

        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }

    @Override
    public Connection getConnection() throws SQLException {

        if (this.connection == null) {
            this.connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        }

        return this.connection;
    }

}
