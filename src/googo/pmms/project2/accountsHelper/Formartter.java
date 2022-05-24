        /*
         * To change this license header, choose License Headers in Project Properties.
         * To change this template file, choose Tools | Templates
         * and open the template in the editor.
         */
        package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
        import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
        import java.text.DateFormat;
        import java.text.DecimalFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
import java.util.concurrent.TimeUnit;
        import java.util.logging.Level;
        import java.util.logging.Logger;

        /**
         *
         * @author Stanchart
         */
        public class Formartter {
             fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
           MaxmumAmountBorrowedFormulas MAth= new  MaxmumAmountBorrowedFormulas();
            DecimalFormat df1 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("#,###");
         DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
             Calendar cal = Calendar.getInstance();
                Date dbDate = new Date();
                SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
                 SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
                 
                 
                
                 public String earlierDate(String date){
                 
                  String dates="", ad1="",ad2="", ad3="", ad4="";
                    ad1=date.substring(0, 4);
            
              ad2=date.substring(5, 7);

              ad3 = date.substring(8, 10);
                   ad4=(parseInt(ad1)-1)+"";
                dates=ad4+"-"+ad2+"-"+ad3;

              return dates;
                 }
                 
                    public String yearOnlyFromDateString(String date){//YYYY-MM-DD

              String  ad1="";
              
 ad1=date.substring(0, 4);
             

              return ad1;
          }
                
                 
                    public String monthOnlyFromDateString(String date){//YYYY-MM-DD


              String ad2=""; 

             ad2=date.substring(5, 7);

              return ad2;
          }
                   
          public String forDatabaseWithFullYearBeginningWithDate(String date){

              String dates="", ad1="",ad2="", ad3=""; 

              ad1=date.substring(0, 2);
              
              ad2=date.substring(3, 5);

              ad3 = date.substring(6, 10);

                dates=ad3+"-"+ad2+"-"+ad1;

              return dates;
          }
//          public String forDatabaseWithDotSeperatorBeginningWithDate(String date){
//          String dates="", ad1="",ad2="", ad3=""; 
//
//              ad1=date.substring(0, 2);ad2=date.substring(3, 5);
//
//              ad3 = date.substring(6, 10);
//
//                dates=ad3+"-"+ad2+"-"+ad1;
//
//              return dates;
//
//
//
//          }  

//           public String forDatabaseWithSlashSeperatorBeginningWithYear(String date){
//
//               String dates="", ad1="",ad2="", ad3=""; 
//
//              ad1=date.substring(0, 4);ad2=date.substring(5, 7);
//
//              ad3 = date.substring(8, 10);
//
//                dates=ad1+"-"+ad2+"-"+ad3;
//
//              return dates;
//
//
//
//          }
public Date getExDateIncreamented(Date date, String days){
    long actualDate;
long theMills=TimeUnit.DAYS.toMillis(Long.valueOf(days));  
long dates=date.getTime();

actualDate=(dates+theMills);

return new Date(actualDate);
}

public String getExDateIncre(String date,int days){//"dd/MM/yyyy"
	
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate=null;
        try {
        newDate=sdf.parse(date);
        } catch (ParseException ex) {
        Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(newDate); // Now use today date.
        c.add(c.get(Calendar.DAY_OF_MONTH), days); // Adding 5 days
        String output = sdf.format(c.getTime());
        return output;

}

public Date getExDateDecreamented(Date date, String days){

 long actualDate;
long theMills=TimeUnit.DAYS.toMillis(Long.valueOf(days));  
long dates=date.getTime();

actualDate=dates-theMills;
return new Date(actualDate);
}
//          public String forDatabaseWithDotSeperatorBeginningWithYear(String date){
//
//          String dates="", ad1="",ad2="", ad3=""; 
//
//              ad1=date.substring(0, 2);ad2=date.substring(3, 5);
//
//              ad3 = date.substring(6, 10);
//
//                dates=ad3+"-"+ad2+"-"+ad1;
//
//              return dates;
//
//
//          } 

           public String dateConverterForDatabase(long dateToConvert){//milliseconds
            String DateConverted="";

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            DateConverted = df.format(new Date(dateToConvert));

            return DateConverted;//"yyyy-MM-dd"
            }
           
           
             public String dateConverterForNormalDate(long dateToConvert){
            String DateConverted="";

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            DateConverted = df.format(new Date(dateToConvert));

            return DateConverted;//"dd/MM/yyyy"
            }

           public String fromDatabaseWithDashSeperatorBeginningWithYear(String date){//"yyyy-MM-dd"

          String dates="", ad1="",ad2="", ad3=""; 

              ad1=date.substring(0, 4);ad2=date.substring(5, 7);

              ad3 = date.substring(8, 10);

                dates=ad3+"/"+ad2+"/"+ad1;

              return dates;//"dd/MM/yyyy"


          } 

             public String formatAccountWithSeperators(String account){
          
                 
           String S1mt=getTypeOfAccountCode(account);
           String S2mt=getMasterAccountCode(account);
           String S3mt=getNumberOfAccountsCode(account);


           String S4mt=S1mt+S2mt+S3mt;
           return S4mt;
           }
             
             
           public String getTypeOfAccountCode(String accountType){
           String typeOfAccountCode="";

           typeOfAccountCode =accountType.substring(0,2);
           return   typeOfAccountCode;
           }
           
           
           public String getMasterAccountCode(String accountMaster){
           String masterAccountCode="";

           masterAccountCode =accountMaster.substring(3,10);
           return   masterAccountCode;
           }
           
            public String getMasterAccountCode1(String accountMaster){
           String masterAccountCode="";

           masterAccountCode =accountMaster.substring(2,9);
           return   masterAccountCode;
           }
           
           public String getNumberOfAccountsCode(String accountNumberOf){
           String numberOfAccountsCode="";

           numberOfAccountsCode =accountNumberOf.substring(11,13);
           return   numberOfAccountsCode;
           }
           
           
        public String putSeparatorsOnNormaAccount(String accountNumber){
            
            
         String formattedAccount="", s1="",s2="",s3="";
           s1=accountNumber.substring(0, 2);
            s2=accountNumber.substring(2, 9);
          s3=accountNumber.substring(9, 11);
        formattedAccount=s1+"-"+s2+"-"+s3;

        return formattedAccount;
        }
//        05502000010
        public String getMasterAccountNumber(String accounNumber){
           String formattedAccount="", s1="";
           s1=accounNumber.substring(0, 5);
          
        formattedAccount=s1+"000010";

        return formattedAccount;
        
        }



        public String month (int month){
        String monthReturned="";
        switch(month)
        {
            case 0:
        monthReturned= "January";
           break;     
          case 1:
        monthReturned= "February";
           break;   
        case 2:
        monthReturned= "March";
           break; 
            case 3:
        monthReturned= "April";
           break;     
          case 4:
        monthReturned= "May";
           break;   
        case 5:
        monthReturned= "June";
           break; 
         case 6:
        monthReturned= "July";
           break; 
            case 7:
        monthReturned= "August";
           break;     
          case 8:
        monthReturned= "September";
           break;   
        case 9:
        monthReturned= "October";
           break;    
           case 10:
        monthReturned= "November";
           break;     
          case 11:
        monthReturned= "December";
           break;   


        }


        return monthReturned;
        }
        
        public int monthNumber(String Month){
        int monthReturned=0;
        switch(Month)
        {
            case "January":
        monthReturned= 0;
           break;     
          case "February":
        monthReturned= 1;
           break;   
        case "March":
        monthReturned=2 ;
           break; 
            case "April":
        monthReturned=3 ;
           break;     
          case "May":
        monthReturned=4 ;
           break;   
        case "June":
        monthReturned= 5;
           break; 
         case  "July":
        monthReturned=6;
           break; 
            case "August":
        monthReturned= 7;
           break;     
          case "September":
        monthReturned= 8;
           break;   
        case "October":
        monthReturned=9 ;
           break;    
        case "November" :
        monthReturned=10 ;
           break;     
          case "December" :
        monthReturned=11;
           break;   


        }


        return monthReturned;
        }
         public String formatForStatementNumbers(String dataBaseValeu)  {
         String formattedString="";
         if(dataBaseValeu.equals("-")){
         formattedString="-";

         }else{
         formattedString= df2.format( Math.abs(parseDouble(dataBaseValeu)));
         }

         return formattedString;
         }

          public String formatForStatementNumbersNormal(String dataBaseValeu)  {
         String formattedString="";
         if(dataBaseValeu.equals("-")){
         formattedString="-";

         }else{
         formattedString= df2.format(parseDouble(dataBaseValeu));
         }

         return formattedString;
         }
         
         public Date formatDateStringToDatabaseDate(String dataBaseValeu)  {//"yyyy-MM-dd"
              Date formattedDate=null;
             
             try {
               
                    
                    formattedDate=sdf.parse(dataBaseValeu.trim());
                    
                } catch (ParseException ex) {
                    Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return formattedDate;
         }
               public String formatDateToDatabaseDate(Date dataBaseValeu)  {
              String formattedString=null;
             
              formattedString=sdfS.format(dataBaseValeu);//"yyyy-MM-dd"
                
                return formattedString;
         }
public long convertTimeToMillseconds(String Timestamp){//"dd/MM/yyyy"
         Date timestamp=null; long stamp=0;
                 
    try {
     timestamp =sdf.parse(Timestamp);//"dd/MM/yyyy"
      stamp=  timestamp.getTime();
                    
                     } catch (ParseException ex) {
                    Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
                }
    
    return stamp;  
}
               
       public long diffDays(String date1 ,String date2)//date1 minus date2
   {
       Long l1=0L,l2=0L,l3=0L;
                
       
                try {
                    l1=sdf.parse(date1).getTime();//"dd/MM/yyyy"
                   l2=sdf.parse(date2).getTime();//"dd/MM/yyyy"
                  l3=l1-l2;
                    
                  
                } catch (ParseException ex) {
                    Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
                }
                  return l3 / (60 * 60 * 24 * 1000);
   }       
               
               
     public Date convertTdate(String dateNow){//"dd/MM/yyyy"
      Date todayDate=null;
                try {
                    sdf= new SimpleDateFormat("dd/MM/yyyy");
                    todayDate=sdf.parse(dateNow);
                } catch (ParseException ex) {
                    Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
                }
     
     return todayDate;
     
     }          
               
      public Date getTodayDate(){
      Date todayDate=null;
    todayDate=  cal.getTime();
      
      
      return todayDate;
      
      
      }     
      
      public Date getTodayDateLess1(){
      Date todayDate=null;
    todayDate=  this.getExDateDecreamented(cal.getTime(), "1");
      
      
      return todayDate;
      
      
      }          
               
       public String ageCalYears(String dateRef){//"dd/MM/yyyy"
             String Age="";
           
           try {
                    long times=sdf.parse(dateRef).getTime();
                    long age=MAth.subtract((System.currentTimeMillis()),(times)); 
                     long years=Math.abs((age/((long)1000* 60 * 60*24*365)));
                     long months=Math.abs((age/(long)(1000* 60 * 60*24*30)));
                  
                  if(age>((long)1000* 60 * 60*24*365)){
               
                 if(years==1){
                 Age=years+"Yr";
                 }else{
                 Age=years+"Yrs";
                 }
                 
                         
                         
                  }  else {
                 
                  if(months==1){
                 Age=months+"mth";
                 }else{
                 Age=months+"mths";
                 }
                  
                  }
                  
                  
                } catch (ParseException ex) {
                    Logger.getLogger(Formartter.class.getName()).log(Level.SEVERE, null, ex);
                }
           
return Age;
       
       }        
               
     public long convertMillsAsNT(String numberOfTimes, String TypeOfTime){
     long theMills=0;
     switch(TypeOfTime){
     
         case "Days":
           theMills=TimeUnit.DAYS.toMillis(Long.valueOf(numberOfTimes));  
             break;
case "Weeks":
    
    theMills=TimeUnit.DAYS.toMillis(Long.valueOf(numberOfTimes)*7);  
    break;
case "Months":
    theMills=TimeUnit.DAYS.toMillis(Long.valueOf(numberOfTimes)*30);  
    
    break;
case "Quaters":
      theMills=TimeUnit.DAYS.toMillis(Long.valueOf(numberOfTimes)*90);  
    break;
case "Years":
    
     theMills=TimeUnit.DAYS.toMillis(Long.valueOf(numberOfTimes)*365);  
    
    break;
 
     }
     
     
     return  theMills;
     }          
               
   public long convertMillsAsNTs(String TypeOfTime){
     long theMills=0;
     switch(TypeOfTime){
     




         case "Daily":
           theMills=TimeUnit.DAYS.toMillis(Long.valueOf(1));  
             break;
case "Weekily":
    
    theMills=TimeUnit.DAYS.toMillis(Long.valueOf(1)*7);  
    break;
case "Monthly":
    theMills=TimeUnit.DAYS.toMillis(Long.valueOf(1)*30);  
    
    break;
case "Quaterly":
      theMills=TimeUnit.DAYS.toMillis(Long.valueOf(1)*90);  
    break;
case "Annually":
    
     theMills=TimeUnit.DAYS.toMillis(Long.valueOf(1)*365);  
    
    break;
 
     }
     
     
     return  theMills;
     }               
               
     public String newInstalmentDateReduced(String Dateg){// "dd/MM/yyyy"
         


String dates="", ad1="",ad2="", ad3="", ad4="",adx1="",adx2="",adx3="";
        
              ad1=Dateg.substring(0, 2);
            
              ad2=Dateg.substring(3, 5);

              ad3 = Dateg.substring(6, 10);
              
              
              if(parseInt(ad2)==01){
              adx2="12";
              adx3=(parseInt(ad3)-1)+"";
              dates=ad1+"/"+adx2+"/"+adx3;
              }else if(parseInt(ad1)>=29){
                  
             if((parseInt(ad2)-1)<10){
                  adx2="0"+(parseInt(ad2)-1)+"";
              dates="28"+"/"+adx2+"/"+ad3;
                
                }else{
                   adx2=(parseInt(ad2)-1)+"";
              dates="28"+"/"+adx2+"/"+ad3;  
                } 
              
              
              }else{
               
                if((parseInt(ad2)-1)<10){
                  adx2="0"+(parseInt(ad2)-1)+"";
              dates=ad1+"/"+adx2+"/"+ad3;
                
                }else{
                   adx2=(parseInt(ad2)-1)+"";
              dates=ad1+"/"+adx2+"/"+ad3;  
                }
              }
              
                 


    
return dates;
}
     
     
     public String newInstalmentDate(String Dateg){//dd/mm/yyyy
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),Dateg);
String dates="", ad1="",ad2="", ad3="", ad4="",adx1="",adx2="",adx3="";
        
              ad1=Dateg.substring(0, 2);
            
              ad2=Dateg.substring(3, 5);

              ad3 = Dateg.substring(6, 10);
              
              
              if(parseInt(ad2)==12){
              adx2="01";
              adx3=(parseInt(ad3)+1)+"";
              dates=ad1+"/"+adx2+"/"+adx3;
              }else if(parseInt(ad1)>=29){
             if((parseInt(ad2)+1)<10){
                  adx2="0"+(parseInt(ad2)+1)+"";
              dates=28+"/"+adx2+"/"+ad3;
                
                }else{
                   adx2=(parseInt(ad2)+1)+"";
              dates=28+"/"+adx2+"/"+ad3;  
                } 
              
              
              }else{
               
                if((parseInt(ad2)+1)<10){
                  adx2="0"+(parseInt(ad2)+1)+"";
              dates=ad1+"/"+adx2+"/"+ad3;
                
                }else{
                   adx2=(parseInt(ad2)+1)+"";
              dates=ad1+"/"+adx2+"/"+ad3;  
                }
              }
              
                 


    
return dates;
}

     
        
     public String newInstalmentDateYear(String Dateg){//dd/mm/yyyy
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),Dateg);
String dates="", ad1="",ad2="", ad3="",adx2="",adx3="";
        
              ad1=Dateg.substring(0, 2);
            
              ad2=Dateg.substring(3, 5);

              ad3 = Dateg.substring(6, 10);
              
              adx3=(parseInt(ad3)+1)+"";
              dates=ad1+"/"+ad2+"/"+adx3;  
return dates;
}


  public long instalmentDate(long date, int periods){

  long theMills=0;

switch(periods){

     case 1:
    
    theMills=date+ TimeUnit.DAYS.toMillis(1)*1;
    
    break;
    case 2:
        double d =7.5;
        long l = (new Double(d)).longValue();
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*l;
    
    break;
    case 3:
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*15;
    
    break;
    case 4:
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*30;
    
    break;
    case 5:
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*90;
    
    break;
    case 6:
      theMills=date+ (TimeUnit.DAYS.toMillis(1))*180;
    
    break;
        
    case 7:
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*360;
    
    break;
    case 8:
    theMills=date+ (TimeUnit.DAYS.toMillis(1))*720;
    
    break;
    
      
    }

    return theMills;
 }


   public String dateConverter(long dateToConvert){
    String DateConverted="";
 
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    DateConverted = df.format(new Date(dateToConvert));
 
    return DateConverted;
    }
   
     
     
     
          
        }
         
         
         
        