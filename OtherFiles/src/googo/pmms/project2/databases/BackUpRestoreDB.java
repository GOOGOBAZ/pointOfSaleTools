/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databases;

import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mortbay.log.Log;

/**
 *
 * @author Stanchart
 */
public class BackUpRestoreDB implements Runnable {
      SimpleDateFormat df =new SimpleDateFormat("dd.MM.yyyy");
           String username = "root";
            String password = "PRINCE?;=2020";
             String dbName = "";
              String backupPath = "";
       fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
         String userId;
       public void setUserID(String userid){
this.userId=userid;
}
    public boolean backUpTheDataBase(String theDataBase){


             this.dbName =   theDataBase;
     

        Thread t=new Thread(this);
        t.start();

           try {
    wait(2000);
    } catch (InterruptedException ex) {
    Logger.getLogger(BackUpRestoreDB.class.getName()).log(Level.SEVERE, null, ex);
    }
return true;
    } 
  


    @Override
    public void run() {
      backupPath=fios.createDirectoryName("dataBaseBackUps", "oneOffBacks");
       File file = new File(backupPath);
        if (!file.exists()) {
            file.mkdirs();
        }
 String backupName = dbName+df.format(new Date());
 String mysqldumpPath=fios.stringFileReader(fios.createFileName("dataBaseBackUps", "oneOffBacks", "excutionString"));
 List<String> command = new ArrayList<>();
        command.add(mysqldumpPath);
        command.add("-u"+username);
        command.add("-p"+password);
        command.add("--routines");
         command.add("--events");
        command.add(dbName);
        command.add("-r");//java must use "-r" instead of ">"
        command.add(backupPath+"/"+backupName+".sql");
        ProcessBuilder builder = new ProcessBuilder(command);
        
         try {
            Process start = builder.start();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(start.getErrorStream()));
                 StringBuilder buf = new StringBuilder(); // Save the output result stream
        String line ;
        while((line = br2.readLine()) != null) buf.append(line); //
                         Log.info("backup sql returns output: {}", buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
           Log.info ("execute backup sql complete");
    }






}
