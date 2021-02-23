package com.example.paymentsystem.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MySQLQueryProperties {
    private static final Logger logger = Logger.getLogger(MySQLQueryProperties.class);
    private static MySQLQueryProperties instance;
    private Properties properties;
    private static final String fileName = "mysqlQueries.properties";

    private MySQLQueryProperties() {
        logger.info("Initializing mysql queries properties class");
        properties = new Properties();
        try {
            FileInputStream stream = new FileInputStream(fileName);
            properties.load(stream);
        } catch (IOException exception) {
            //TODO: Create high level exception
            logger.error(exception);
        }
    }

    public static synchronized MySQLQueryProperties getInstance() {
        if (instance == null) {
            instance = new MySQLQueryProperties();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
