package com.javagda19.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MysqlConnectionParameters {
    private static final String PROPERTIES_FILE_NAME = "/jdbc.properties";
    private Properties properties;

    private String dbHost;
    private String dbPort;
    private String dbUsername;
    private String dbPassword;
    private String dbName;

    public MysqlConnectionParameters() throws IOException {
        loadConfigurationFrom(PROPERTIES_FILE_NAME);

        dbHost = loadParameter("jdbc.database.host");
        dbPort = loadParameter("jdbc.database.port");
        dbUsername = loadParameter("jdbc.database.username");
        dbPassword = loadParameter("jdbc.database.password");
        dbName = loadParameter("jdbc.database.name");
    }


    public Properties loadConfigurationFrom(String resourceName) throws IOException {
        properties = new Properties();

        InputStream propertiesStream = this.getClass().getResourceAsStream(resourceName);
        if (propertiesStream == null) {
            throw new FileNotFoundException("Resource files cannot be loaded.");
        }
        properties.load(propertiesStream);
        return properties;
    }

    private String loadParameter(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
