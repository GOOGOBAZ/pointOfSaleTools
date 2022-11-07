/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.email;

import googo.pmms.project2.accountsHelper.SendSms;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author SSRN
 */
public class SmsUtility extends SendSms {
    
    fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
       

       DatabaseQuaries dbq =new DatabaseQuaries();
       
  
       
         DecimalFormat NumberFormat =new DecimalFormat("#,###");
         
         String[] messageSent=null;
  
    public synchronized void processTheSMS(){

       
    List<Integer> smsIds=dbq.getAllPendingIds();
    
    for(int id:smsIds){
        
    if(dbq.smsForInsufficientFunds(id)){
    
    processInsufficientSms(id);
    
    }else{
//     JOptionPane.showMessageDialog(c, id+"");
    processNonInsufficientSms(id);
    }
    
    
    }
    
    
    
    
    
    
    }
    

private void processNonInsufficientSms(int smsId){
 
    String results="";
   
messageSent=fios.stringFileReader(fios.createFileName("emailDetails", "sms", "message"+smsId+".txt")).split("[:]", 4);
// JOptionPane.showMessageDialog(c, messageSent);
    results=this.sendTheMessage(messageSent[1], messageSent[2], messageSent[0]);
//     JOptionPane.showMessageDialog(c, results);
//    fios.stringFileWriter(fios.createFileName("test", "testit", "smsFeedBack.txt"),results+"");
//    response=results.split("[=]", 3)[1].substring(0, 1);
    
 
      
        switch (results.trim()) {
            case "400":
                fios.deleteFileExistance(fios.createFileName("emailDetails", "sms", "message"+smsId+".txt"));
                dbq.updateMessageSent(smsId+"", "Delivered", "Delivered Successfully");
                break;
       
            default:
                dbq.updateMessageSent(smsId+"", "Failed", "Failed");
                break;
        }
    
    

}


private void processInsufficientSms(int smsId){
 String results="";
    if(this.smsBalanceEnough()){
        
messageSent=fios.stringFileReader(fios.createFileName("emailDetails", "sms", "message"+smsId+".txt")).split("[:]", 3);
if(messageSent.length>2){
    
    results=this.sendTheMessage(messageSent[1], messageSent[2], messageSent[0]);
//      fios.stringFileWriter(fios.createFileName("test", "testit", "smsFeedBack.txt"),results);
//    response=results.split("[=]", 3)[1].substring(0, 1);
    
    
    switch (results) {
        case "0":
            fios.deleteFileExistance(fios.createFileName("emailDetails", "sms", "message"+smsId+".txt"));
            dbq.updateMessageSent(smsId+"", "Delivered", "Delivered Successfully");
            break;
        case "7":
            dbq.updateMessageSent(smsId+"", "Failed", "Invalid Number");
            break;
        default:
            dbq.updateMessageSent(smsId+"", "Failed", "Failed");
            break;
    }

this.reduceBalance();
    }
}}




















}
