/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databaseConnectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Stanchart
 */
public class SynclonisedConnector {//assume it's a singleton
  
    


private Properties prop = new Properties();

    public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

    private Connection connect() throws SQLException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://" + prop.getProperty("hostname") + "/" + prop.getProperty("database") + "?"
                            + "user=" + prop.getProperty("username") + "&password=" + prop.getProperty("password"));
            return connection;
        } catch (SQLException e) {          
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 

        return null;

    }

    public Connection getConnection() throws SQLException {

        if(threadConnection.get() == null) {
            Connection connection = connect();
            threadConnection.set(connection);
            return threadConnection.get();
        } else
            return threadConnection.get();
    } 

    private void freeConnection(Connection connection) throws SQLException {
        connection.close();
        threadConnection.remove();
    }
}
    
    

