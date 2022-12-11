package com.project.Main.Database;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public interface IDBConnection {

    public Connection getConnection() throws SQLException;

}
