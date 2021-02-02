package com.example.ServletTest.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BasicConnectionPool implements ConnectionPool {
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_TIMEOUT = 5;
    private static final Logger logger = Logger.getLogger(BasicConnectionPool.class);

    public static BasicConnectionPool create() throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(createConnection());
            } catch (NamingException e) {
                logger.error(e.getMessage());
            }
        }
        return new BasicConnectionPool(pool);
    }


    private BasicConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                try {
                    connectionPool.add(createConnection());
                } catch (NamingException e) {
                    logger.error(e.getMessage());
                }
            } else {
                logger.error("Maximum pool size reached, no available connections!");
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        if(!connection.isValid(MAX_TIMEOUT)){
            try {
                connection = createConnection();
            } catch (NamingException e) {
                logger.error(e.getMessage());
            }
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() throws SQLException, NamingException {
        Context initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/ServletTest");
        logger.info("Connection is created and ready for use");

        return dataSource.getConnection();
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
        logger.info("Closing all connections");
    }
}
