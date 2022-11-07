/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanchart
 */
public class loanDatabaseConnector {
  
    String queary  = "jdbc:mysql://localhost/pmms_loans?user=root&password=PRINCE?;=2020";
    Connection con=null;
  
   
        
       public  synchronized Connection createConnection(){
try {
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
 Logger.getLogger( JDBCConnectionPigs.class.getName()).log(Level.SEVERE, null, ex);
 }
  

  try {
 con = DriverManager.getConnection(queary);
 } catch (SQLException ex) {
 Logger.getLogger( JDBCConnectionPigs.class.getName()).log(Level.SEVERE, null, ex); 
 }
      

     return  con;
    }
public void closeConnection(){       
 try {
 con.close();
 } catch (SQLException ex) {
  Logger.getLogger( JDBCConnectionPigs.class.getName()).log(Level.SEVERE, null, ex);
   } 
}







  
}
