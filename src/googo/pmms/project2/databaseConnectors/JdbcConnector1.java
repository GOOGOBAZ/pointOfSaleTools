/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databaseConnectors;

import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.IpAddressSelector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanchart
 */
public class JdbcConnector1 {
   fileInputOutPutStreams fios= new fileInputOutPutStreams();
   IpAddressSelector ip =new IpAddressSelector();
    String db="";
String queary ="";
    String queary1="";
    Connection con=null;
 public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    
    
    public JdbcConnector1(String DataBase){
    
    this.db=DataBase;
    
    }
    
        
       private Connection connect(){
           
           
      queary  = "jdbc:mysql://"+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "hostAddress.txt"))+":3306"+"/"+db.trim()+"?user="+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "DBAccessUserDetails.txt")).trim()+"&password="+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "DBAccessPasswordDetails.txt")).trim();
  queary1  = "jdbc:mysql://localhost:3306"+"/"+db.trim()+"?user="+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "DBAccessUserDetails.txt")).trim()+"&password="+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "DBAccessPasswordDetails.txt")).trim();
         
  
  if(ip.getThisComputerIp().equalsIgnoreCase(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "hostAddress.txt")))){
//           fios.stringFileWriter(fios.createFileName("emailDetails", "sendMail", "testing.txt"), queary+","+queary1);
try {
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
 Logger.getLogger(JdbcConnector1.class.getName()).log(Level.SEVERE, null, ex);
 }
  
  try {
 con = DriverManager.getConnection(queary1.replaceAll("\\s", ""));
 
 } catch (SQLException ex) {
 Logger.getLogger(JdbcConnector1.class.getName()).log(Level.SEVERE, null, ex); 
 }
          
  
  
  }else{
      
      
           
        try {
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
 Logger.getLogger(JdbcConnector1.class.getName()).log(Level.SEVERE, null, ex);
 }
  
  try {
 con = DriverManager.getConnection(queary.replaceAll("\\s", ""));
 
 } catch (SQLException ex) {
 Logger.getLogger(JdbcConnector1.class.getName()).log(Level.SEVERE, null, ex); 
 }   
           
           }
  
  
     return  con;
    }
       
       
   public Connection createConnection(){
   
   
//   if(threadConnection.get() == null) {
            Connection connection = connect();
            threadConnection.set(connection);
            return threadConnection.get();
//        } else
//            return threadConnection.get();
   
   
   }     
       
public  void closeConnection(Connection connection){       
       try {
           connection.close();
         
       } catch (SQLException ex) {
           Logger.getLogger(JdbcConnector1.class.getName()).log(Level.SEVERE, null, ex);
       }
       threadConnection.remove();  
}

    
}
