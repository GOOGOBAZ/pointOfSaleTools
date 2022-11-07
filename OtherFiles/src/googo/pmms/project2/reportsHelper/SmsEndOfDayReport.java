        /*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
        package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.accountsHelper.FormattingUrlData;
        import googo.pmms.project2.accountsHelper.SendSms;
        import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
        import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
        import java.awt.Component;
        import java.util.List;

        /**
        *
        * @author STAT SOLUTIONS
        */
        public class SmsEndOfDayReport implements Runnable {
 int numberOfMessages=0;
     FormattingUrlData smsForm=new FormattingUrlData();
        String message="";   
        SendSms sendsms= new SendSms();
        fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
        String userId;
        DatabaseQuaries dbq =new DatabaseQuaries();
        ReportsDatabase rdb = new ReportsDatabase();
        Component c;

        public SmsEndOfDayReport(Component c1,String userid){
        c=c1;  this.userId=userid;
        }



        public boolean sendDailyReport(){
        Thread t=new Thread(this);
        t.start();
        return true;
        } 



        @Override
        public void run() {
     
       
    List<List> reportD=   rdb.smsDailyReport(c);
      
            if(!reportD.isEmpty()){
        dbq.backUpDetails(c).forEach((item)->{
             message=("Dear "+item.get(0).toString()+" "+fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyName.txt"))+" "+"END OF DAY REPORT"+" "+"on"+" "+item.get(3)+":");
          reportD.forEach(itemOn->{
        message=message+itemOn.get(0)+" "+itemOn.get(1)+";";
        });
            message=message+" Sent by PinkApple";
//    message= message.replaceAll("/", "%2F").replaceAll(":", "%3A").replaceAll(";", "%3B").replaceAll(",", "%2C").replaceAll("=", "%3D").replaceAll("\\s", "%20");
          
          
   String contact="256"+item.get(1).toString().substring(1, 10);
    if(sendsms.smsBalanceEnough()){
            numberOfMessages=Math.round(message.length()/158)+1;
    sendsms.sendTheMessage(smsForm.formSMSMessage(message), "backup", contact); 
     sendsms.reduceBalanceBulkBalance(numberOfMessages);
     
      }
   
     
        });

            }
        
        
        
        }





        }
