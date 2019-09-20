package com.javagda19.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnection {

    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "czarymary1234";
    private static final String DB_NAME = "jdbc_students";

    private MysqlDataSource dataSource;

    public MysqlConnection() {
        initialize();
    }

    private void initialize() {
        dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(DB_PORT));
        dataSource.setUser(DB_USERNAME);
        dataSource.setServerName(DB_HOST);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDatabaseName(DB_NAME);
        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
    }
}
