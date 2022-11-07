///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package googo.pmms.project2.accountsHelper;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.mortbay.log.Log;
//
///**
// *
// * @author STAT SOLUTIONS
// */
//public class dbBackUp {
//    
//    
//
//        String username = "root";
//        String password = "PRINCE?;=2020";
////        String dbName = "luntek_icplatform";
//        String mysqldumpPath = "/usr/bin/mysqldump";
//        String backupPath = "/data/mysql_backup/";
//
////        String username = "root";
////        String password = "root";
////        String dbName = "guns";
////        String mysqldumpPath = "D:/MySQL/mysql-8.0.15-winx64/bin/mysqldump";
////        String backupPath = "F:/backup";
//
//        File file = new File(backupPath);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_3);
//        String backupName = sdf.format(new Date());
//
//        //mysqldump -uroot -proot  guns > F:/backup/20190614.sql
//                 Log.info ("Start executing backup sql");
//        List<String> command = new ArrayList<>();
//        command.add(mysqldumpPath);
//        command.add("-u"+username);
//        command.add("-p"+password);
//        command.add(dbName);
//        command.add(">");
//        command.add(backupPath+"/"+backupName+".sql");
//        ProcessBuilder builder = new ProcessBuilder(command);
//                 Log.info("command command: {}", command);
//        try {
//            Process start = builder.start();
//            BufferedReader br2 = new BufferedReader(new InputStreamReader(start.getErrorStream()));
//                 StringBuilder buf = new StringBuilder(); // Save the output result stream
//        String line ;
//        while((line = br2.readLine()) != null) buf.append(line); //
//                         Log.info("backup sql returns output: {}", buf.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//                 Log.info ("execute backup sql complete");
//    
//
//    
//}
