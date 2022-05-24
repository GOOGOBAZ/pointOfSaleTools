    /*
    * To change this license header, choose License Headers in Project Properties.
    * To change this template file, choose Tools | Templates
    * and open the template in the editor.
    */
    package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

    /**
    *
    * @author Stanchart
    */
    public class updateLoans extends JFrame {
        MaxmumAmountBorrowedFormulas mxn=new MaxmumAmountBorrowedFormulas();
        Formartter fmt= new Formartter();
         GregorianCalendar cal = new GregorianCalendar(); 
    fileInputOutPutStreams fios= new fileInputOutPutStreams();  
     loanDatabaseQuaries loan=new loanDatabaseQuaries();
      DatabaseQuaries dbq= new DatabaseQuaries();
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
      String userId; int kloans=0;
      String [] accounts;
        PostingMain postinAccrualsn=new PostingMain(updateLoans.this); 
   SendSms sendsms= new SendSms();
//     String   instalmentStartDate = null,instalmentStartDates=null;
        List theUpdateDetaisl=null, provisionDetails=null;  
     public updateLoans(String userid){
     
     this.userId=userid;
     
     }
     
     
//     public synchronized void accrueInterestProcessNow(List theAccountS){
//     
//     for(Object accountNumber:theAccountS){
//
//
//if(fios.stringFileReader(fios.createFileName("loanApplication", "loanProcessing", "accrueingStatus"+accountNumber+".txt")).equalsIgnoreCase("NeverAccrued")){
//  SwingWorker<Void,Void> sendAccount;
//        sendAccount = new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
//
//    processNeverLoanInterestAccrueing(accountNumber.toString());
//    
//    return null;
//
//            }
//        };
//        sendAccount.execute();
//        
//      try {
//wait(600000);
//         } catch (InterruptedException ex) {
//             Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
//         }   
//
//      
//             }else {
////    if(fios.stringFileReader(fios.createFileName("loanApplication", "loanProcessing", "accrueingStatus"+accountNumber+".txt")).equalsIgnoreCase("Accrueing")){
//
//SwingWorker<Void,Void> sendAccount1;
//        sendAccount1 = new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
//
//processContinueingInterestAccrueing(accountNumber.toString());
//
//  return null;
//
//            }
//        };
//        sendAccount1.execute();
//    
//      try {
//wait(6000);
//         } catch (InterruptedException ex) {
//             Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
//         }  
//}
//
//  
//} 
//
//     
//     }
     
     
     
     
     
     public synchronized void processReturnOnInvestments(List theAccountNumbers,Component c){

        if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "firstReturnOI.txt"))!=7){

        processTheFirstThreshhold(theAccountNumbers,c);
        
        fios.intFileWriterReplace(fios.createFileName("persistence", "loanSetUp", "firstReturnOI.txt"), "7");
        
        }else{

        processReturnOnInvestmentNormal(theAccountNumbers,c);

        }




        } 

        public synchronized void   processTheFirstThreshhold(List theAccountNumbers,Component c){
            
             String theFirstThresholdDate="";
            

        String startOperatingTime=dbq.getTheStartThresholdDate();//yyyy-mm-dd
        
//         JOptionPane.showMessageDialog(c, startOperatingTime);
         
            try {
                
                theFirstThresholdDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sdfS.format(fmt.getExDateDecreamented(sdfS.parse(startOperatingTime), "1")));
           
//                JOptionPane.showMessageDialog(c, theFirstThresholdDate);
                
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }
// JOptionPane.showMessageDialog(c, theFirstThresholdDate);
//        fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),startOperatingTime+";"+theFirstThresholdDate);
        if(initialiseSharesRIO(theFirstThresholdDate,theAccountNumbers,c)){

        
//          JOptionPane.showMessageDialog(c, "IN");   


//        SwingWorker<Void,Void> processROIInitial;
//            processROIInitial = new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
                 String   instalmentStartDatesx = theFirstThresholdDate;//DD/MM/YYYY
                
//                 JOptionPane.showMessageDialog(c, instalmentStartDatesx);    
                  
                 long  numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),theFirstThresholdDate);
                    
                    
                    while(numberOfDays>=1){
                        
//                         JOptionPane.showMessageDialog(c, numberOfDays);  
//                         JOptionPane.showMessageDialog(c, numberOfDays);  
                        processFinalRecoverReturnOnInvestment(instalmentStartDatesx,theAccountNumbers,c);
                        
                        try {
                            instalmentStartDatesx=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDatesx), "1"));
                        } catch (ParseException ex) {
                            Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDatesx);
                        
//                        try {
//                            wait(1200);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
//                    return null;
//                } };
//           processROIInitial.execute();
//        
      
        
       
        
        } 
    }



        public synchronized void     processReturnOnInvestmentNormal(List theAccountNumbers,Component c){
            
            
        String todayDate=fmt.dateConverterForNormalDate(System.currentTimeMillis( ));//"dd/MM/yyyy"
        
        
       
        
         
            
        for(Object accountNumber:theAccountNumbers){
//            JOptionPane.showMessageDialog(c, "accountName="+dbq.AccountName(accountNumber.toString()));
            
            String lastDateReturnOI=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(dbq.pickTheLastDate(accountNumber.toString()));//"dd/MM/yyyy" 
//            JOptionPane.showMessageDialog(c, "lastDateReturnOI=="+lastDateReturnOI);
            if(!lastDateReturnOI.equalsIgnoreCase("1970-01-01")){
            
              long  numberOfDays=fmt.diffDays( todayDate,lastDateReturnOI);
                    
//         JOptionPane.showMessageDialog(c, "numberOfDays=="+numberOfDays);
         
                    while(numberOfDays>=1){
            String startingDate=null;
         
            try {
                startingDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(lastDateReturnOI), "1"));
                
//                  JOptionPane.showMessageDialog(c, "startingDate=="+startingDate);
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }             
  
            String forDateBase = null;
        
            try {
                
                forDateBase = fmt.forDatabaseWithFullYearBeginningWithDate(sdf.format(fmt.getExDateDecreamented(sdf.parse(startingDate), "1")));
                
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            
        double theBalance=parseDouble(dbq.getTheDetailsOfAccount(forDateBase,accountNumber.toString(),c).get(0).toString());
//    JOptionPane.showMessageDialog(c, "theBalance=="+theBalance);
        if(theBalance>=2000000.0){
            
         
        YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(startingDate).substring(0, 7));

        double returnOnInverstment=mxn.theActualReturnOnInvestmentHEFSacco(theBalance,theStartDateObject.getMonthValue(),theStartDateObject.getYear()); 
//           JOptionPane.showMessageDialog(c, "returnOnInverstment=="+returnOnInverstment);
         if(!(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt")).equalsIgnoreCase(theStartDateObject.getMonth().toString()))){
       
             fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),"0.0");
        
             fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt"),theStartDateObject.getMonth().toString());
        }
         
        double monthlyReturnBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt")))+returnOnInverstment;
//          JOptionPane.showMessageDialog(c, "monthlyReturnBalance=="+ monthlyReturnBalance);
        double renningBalance= parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt")))+returnOnInverstment;
//            JOptionPane.showMessageDialog(c, "renningBalance=="+ renningBalance);
       theUpdateDetaisl=new ArrayList();
        theUpdateDetaisl.add(fmt.forDatabaseWithFullYearBeginningWithDate(startingDate));//Value date:0
        theUpdateDetaisl.add(theStartDateObject.getMonth().toString());//Value month:1
        theUpdateDetaisl.add(theStartDateObject.getYear());//Value year:2
        theUpdateDetaisl.add(dbq.AccountName(accountNumber.toString()));//Account Name:3
        theUpdateDetaisl.add(accountNumber.toString());//Account Number:4
        theUpdateDetaisl.add(theBalance);//Shares balance:5
        theUpdateDetaisl.add(returnOnInverstment);//Return on investemnt:6
        theUpdateDetaisl.add(monthlyReturnBalance);//Monthly return:7
        theUpdateDetaisl.add(renningBalance);//Annual return:8
          
      fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),monthlyReturnBalance+"");
      
      fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt"), renningBalance+"");       
        
//fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"originalShares.txt"), theBalance+"");   
        
        
//        SwingWorker<Void,Void> processROIInitialrecoverTheReturnOnInvestment = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//             if(returnOnInverstment>1){
//        postinAccrualsn.recoverTheReturnOnInvestment(returnOnInverstment+"",accountNumber.toString(),c);
//        }
//        return null;
//        } }; 
//        
//        processROIInitialrecoverTheReturnOnInvestment.execute();
        
        
        
        }else if(theBalance<2000000.0){
            
//      double theOtherBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", "sharesValueF"+accountNumber.toString()+"originalSharesF.txt")));  
       
//      JOptionPane.showMessageDialog(c, "theOtherBalance=="+theOtherBalance);
      
           YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(todayDate).substring(0, 7));
           
  if(!(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt")).equalsIgnoreCase(theStartDateObject.getMonth().toString()))){
       
      fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),"0.0");
      
        fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt"),theStartDateObject.getMonth().toString());
        
        }
  
        double returnOnInverstment=mxn.theActualReturnOnInvestmentHEFSacco(0.0,theStartDateObject.getMonthValue(),theStartDateObject.getYear()); 
        
      double monthlyReturnBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt")))+returnOnInverstment;
        
        double renningBalance= parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt")))+returnOnInverstment;
        

      theUpdateDetaisl=new ArrayList();

       theUpdateDetaisl.add(fmt.forDatabaseWithFullYearBeginningWithDate(startingDate));//Value date:0
        theUpdateDetaisl.add(theStartDateObject.getMonth().toString());//Value month:1
        theUpdateDetaisl.add(theStartDateObject.getYear());//Value year:2
        theUpdateDetaisl.add(dbq.AccountName(accountNumber.toString()));//Account Name:3
        theUpdateDetaisl.add(accountNumber.toString());//Account Number:4
        theUpdateDetaisl.add(theBalance);//Shares balance:5
        theUpdateDetaisl.add(returnOnInverstment);//Return on investemnt:6
        theUpdateDetaisl.add(monthlyReturnBalance);//Monthly return:7
        theUpdateDetaisl.add(renningBalance);//Annual return:8
        
       fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),monthlyReturnBalance+"");
      
      fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt"), renningBalance+"");       
        
//fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"originalShares.txt"), theOtherBalance+"");  

//SwingWorker<Void,Void> processROIInitialrecoverTheReturnOnInvestment = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//            if(returnOnInverstment>1){
//        postinAccrualsn.recoverTheReturnOnInvestment(returnOnInverstment+"",accountNumber.toString(),c);
//        }
//        return null;
//        } }; processROIInitialrecoverTheReturnOnInvestment.execute();
        }

 SwingWorker<Void,Void> insertTheOrinal= new SwingWorker() {
        @Override

        protected Object doInBackground() throws Exception { 
             if(parseDouble(theUpdateDetaisl.get(6).toString())>1){
        dbq.updateOriginalSharesDetails(theUpdateDetaisl);
             }
        return null;
        }};insertTheOrinal.execute();

  try {
                            lastDateReturnOI=sdf.format(fmt.getExDateIncreamented(sdf.parse(lastDateReturnOI), "1"));
                        } catch (ParseException ex) {
                            Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
  
                        numberOfDays=fmt.diffDays( todayDate,lastDateReturnOI);
            try {
                wait(130000);
            } catch (InterruptedException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }



        }
            }
        
        }
        }
        
        public synchronized boolean initialiseSharesRIO(String theFirstThresholdDate,List theAccountNumbers,Component c){
            
        boolean completed=false;
        
//        JOptionPane.showMessageDialog(c, "Start");
        
         YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(theFirstThresholdDate).substring(0, 7));//yyyy-mm-dd
        
         String month=theStartDateObject.getMonth().toString();
        
        int Year=theStartDateObject.getYear();
        
        
        
        
         if(Year>fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "timeYear1.txt"))){
    
             
             
             
             
       
        
        
        for(Object account:theAccountNumbers){
            
        List theIndividualDetails=dbq.getTheDetailsOfAccount(fmt.forDatabaseWithFullYearBeginningWithDate(theFirstThresholdDate),account.toString(),c);//yyyy-mm-dd

        List theFinalDetails=new ArrayList();

        theFinalDetails.add(fmt.forDatabaseWithFullYearBeginningWithDate(theFirstThresholdDate));//Date of starting accrual:0
        theFinalDetails.add(month);//Month before starting RIO:1
        theFinalDetails.add(Year);//Year before starting ROI:2
          theFinalDetails.add(dbq.AccountName(account.toString()));//Account Name of Investor:3
        theFinalDetails.add(account.toString());//Account Number of Investor:4
        theFinalDetails.add(theIndividualDetails.get(0).toString());//The original shares before ROI:5
        theFinalDetails.add("0.0"); //Daily Return:6
        theFinalDetails.add("0.0"); //Monthly Return:7
        theFinalDetails.add("0.0"); //Return run balance:8
       fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", account.toString()+"MonthlyReturn.txt"), "0.0");
       fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", account.toString()+"reunningBalance.txt"), "0.0");
        fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", account.toString()+"originalShares.txt"), theIndividualDetails.get(0).toString());
        SwingWorker<Void,Void> insertTheOrinal= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception { 
        dbq.   updateOriginalSharesDetails(theFinalDetails);
        return null;
        }};insertTheOrinal.execute();
            try {
                wait(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        completed=true;
         fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "timeYear1.txt"),Year+""); 
        }
         
            if(Year==fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "timeYear1.txt"))){
            
              completed=true;
            }
         
         
        return completed;
        
        }

        
        
        
        
        
        public synchronized void  processFinalRecoverReturnOnInvestment(String thestartDate,List theAccounts,Component c){
        
            String forDateBase=fmt.forDatabaseWithFullYearBeginningWithDate(thestartDate);//yyyy-mm-dd
            
        for(Object accountNumber:theAccounts){
            
          String valueDate="";
          
        double theBalance=parseDouble(dbq.getTheDetailsOfAccount(forDateBase,accountNumber.toString(),c).get(0).toString());
        
       
   
        if(theBalance>=2000000.0){
     
           
            try {
                
                valueDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(thestartDate), "1"));//dd/MM/yyyy
                
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate).substring(0, 7));
        
        if(!(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt")).equalsIgnoreCase(theStartDateObject.getMonth().toString()))){
        fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),"0.0");
        fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt"),theStartDateObject.getMonth().toString());
        }

        double returnOnInverstment=mxn.theActualReturnOnInvestmentHEFSacco(theBalance,theStartDateObject.getMonthValue(),theStartDateObject.getYear()); 
         
        double monthlyReturnBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt")))+returnOnInverstment;
        
        double renningBalance= parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt")))+returnOnInverstment;
        
       theUpdateDetaisl=new ArrayList();
        theUpdateDetaisl.add(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate));//Value date:0
        theUpdateDetaisl.add(theStartDateObject.getMonth().toString());//Value month:1
        theUpdateDetaisl.add(theStartDateObject.getYear());//Value year:2
        theUpdateDetaisl.add(dbq.AccountName(accountNumber.toString()));//Account Name:3
        theUpdateDetaisl.add(accountNumber.toString());//Account Number:4
        theUpdateDetaisl.add(theBalance);//Shares balance:5
        theUpdateDetaisl.add(returnOnInverstment);//Return on investemnt:6
        theUpdateDetaisl.add(monthlyReturnBalance);//Monthly return:7
        theUpdateDetaisl.add(renningBalance);//Annual return:8

        
  fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),monthlyReturnBalance+"");
       fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt"), renningBalance+"");       
        
//fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"originalShares.txt"), theBalance+"");

        }else {
            String newDate=null;
              try {
                  
                  newDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(thestartDate), "1"));//dd/MM/yyyy 
              } catch (ParseException ex) {
                  Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
              }
      
              
//      double theOtherBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"originalShares.txt")));  

      YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(newDate).substring(0, 7));

      if(!(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt")).equalsIgnoreCase(theStartDateObject.getMonth().toString()))){
       
          fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),"0.0");
          
        fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"currentMonth.txt"),theStartDateObject.getMonth().toString());
        
        }   
      
        double returnOnInverstment=mxn.theActualReturnOnInvestmentHEFSacco(0.0,theStartDateObject.getMonthValue(),theStartDateObject.getYear()); 
        
        double monthlyReturnBalance=parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt")))+returnOnInverstment;
        
        double renningBalance= parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt")))+returnOnInverstment;

      theUpdateDetaisl=new ArrayList();

       theUpdateDetaisl.add(fmt.forDatabaseWithFullYearBeginningWithDate(newDate));//Value date:0
       
        theUpdateDetaisl.add(theStartDateObject.getMonth().toString());//Value month:1
        
        theUpdateDetaisl.add(theStartDateObject.getYear());//Value year:2
        
        theUpdateDetaisl.add(dbq.AccountName(accountNumber.toString()));//Account Name:3
        
        theUpdateDetaisl.add(accountNumber.toString());//Account Number:4
        
        theUpdateDetaisl.add(theBalance);//Shares balance:5
        
        theUpdateDetaisl.add(returnOnInverstment);//Return on investemnt:6
        
        theUpdateDetaisl.add(monthlyReturnBalance);//Monthly return:7
        
        theUpdateDetaisl.add(renningBalance);//Annual return:8
        
  fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"MonthlyReturn.txt"),monthlyReturnBalance+"");
       
  fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"reunningBalance.txt"), renningBalance+"");       
        
//fios.stringFileWriter(fios.createFileName("sharesAvailable", "shareValues", accountNumber.toString()+"originalShares.txt"), theOtherBalance+"");
        }

 SwingWorker<Void,Void> insertTheOrinal= new SwingWorker() {
        @Override

        protected Object doInBackground() throws Exception { 
        dbq.updateOriginalSharesDetails(theUpdateDetaisl);
        return null;
        }};insertTheOrinal.execute();



                try {
                    wait(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
                }


        }



        }
        
        public synchronized void updateLoanPenalties(Component c){

        accounts=fios.stringFileReader(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered")).split("[;]");//Pick the accounts considered that were put on file and place them into an array

        while(kloans<=accounts.length-1){
//JOptionPane.showMessageDialog(c, accounts[kloans].replace(";", ""));
//JOptionPane.showMessageDialog(c, this, userId, WIDTH, null);
        //
//        SwingWorker<Void,Void> processLoanPenalty= new SwingWorker() {
//                    @Override
//                    protected Object doInBackground() throws Exception {

        //                fmt.convertMillsAsNTs(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt")));

        //Pick the oldest instalment in arrears so that you only process the account that is in arrears
        if(loan.isTheAccountInArrears(accounts[kloans].replace(";", ""))){

//JOptionPane.showMessageDialog(c, accounts[kloans].replace(";", "")+"IN");

        postinAccrualsn.completeLoanPenaltyUpdates(accounts[kloans].replace(";", ""),c);

        }
//                return null;
//                                
//                            }
//                        };
//                        
//                        
//                        processLoanPenalty.execute();
//                try {
//                    wait(5000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
//                }
        kloans++;
        }


        }

        public synchronized void processTheActualPenaltyLoan(Component c){

        //        
        //  
        //        try {
        //            wait(10000);
        //        } catch (InterruptedException ex) {
        //            Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
        //        }


        List loansChargableIds=loan.getTheLoansOutstandingChargeableIds(c);

        for(Object Id:loansChargableIds){
JOptionPane.showMessageDialog(c, "Id:loansChargableIds=="+Id);

        //        details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0
        //       details.add(rst3.getString("AccountNumber"));//Account Number:1
        //        details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
        //       details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
        //       details.add(rst3.getString("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
        //       details.add(rst3.getString("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
        //       details.add(rst3.getString("RecoverCharge"));//Recover Charge Amount:6
        //       details.add(rst3.getString("RecoverChargeRemaining"));//Recover remaining charge Amount:7
        //       details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8
        //       details.add(rst3.getString("RecoveryInstalmentSize"));//Recover Instalment size used:9
        List chargeAccrualDetails=loan.getThePenaltyChargeDetails(Id.toString(),c);

//        SwingWorker<Void,Void> processLoanPenaltyFinal= new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {

        postinAccrualsn. processFinalLoansInArrears(chargeAccrualDetails,Id.toString(),c);

//        return null;
//                        
//                    }
//                };
//                
//                
//                processLoanPenaltyFinal.execute();
//        try {
//            wait(350000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
//        }
 
    }
    
    
    
    
    
    }
    
    
    
     public boolean arrears(List accountArres){
        boolean statusco=false;
    
    int k=0;
    while(k<=accountArres.size()-1){
   
    
        loan.updateArrearReport(accountArres.get(k).toString());
     
    k++;
    }
return statusco;
    }    
     
     
             
             public boolean dueWriteOff(List accountdueWriteOff){
        boolean statusco=false;
    
    int k=0;
    while(k<=accountdueWriteOff.size()-1){
   
    
        loan.updateLoansDueWriteOff(accountdueWriteOff.get(k).toString());
     
    k++;
    }
return statusco;
    }  
   
             
    
    
public boolean updateLoanPort(List activeAccounts){
boolean updated=false; int k=0;
 while(k<=activeAccounts.size()-1){

    
      updated=  loan.updateloanPortfolio(activeAccounts.get(k).toString());
     
    k++;
    }
return updated;

}


public boolean updateLoanPortPerfroming(List activeAccounts){
boolean updated=false; int k=0;
 while(k<=activeAccounts.size()-1){

    
      updated=  loan.updateloanPortfolioperforming(activeAccounts.get(k).toString());
     
    k++;
    }
return updated;

}

public boolean updateLoanPortAtRisk (List activeAccounts){
boolean updated=false; int k=0;
 while(k<=activeAccounts.size()-1){

    
      updated=  loan.updateloanPortfolioAtRisk(activeAccounts.get(k).toString());
     
    k++;
    }
return updated;
}
    
    public boolean createIndividualPostingNames(){
        
    List<String> accountNumbers=dbq.getCustomerAccountNumbers(); 
    
    int z=0;

    boolean completed=false;
    
    List finalDetails=new ArrayList();
    
    while(z<accountNumbers.size()){
         
    List<String> accountDetails=new ArrayList();
    
    accountDetails.add(dbq.getDBCurrentDate());
    
    accountDetails.add(accountNumbers.get(z));
    
    accountDetails.add(dbq.AccountName(accountNumbers.get(z)));
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    accountDetails.add("0.0");
    accountDetails.add("0.0");
      accountDetails.add("0.0");
    accountDetails.add("0.0");
     accountDetails.add("0.0");
     accountDetails.add(fmt.month(cal.get(GregorianCalendar.MONTH)));
    accountDetails.add(cal.get(GregorianCalendar.YEAR)+"");
    accountDetails.add((cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH))+"");
    accountDetails.add(userId);
    accountDetails.add(dbq.getDBCurrentDate());
    accountDetails.add(dbq.getDBCurrentTime());
    finalDetails.add(accountDetails);
    
    z++;
    }
    completed=dbq.updatelist(finalDetails);
    
    return completed;
    }
    
     public boolean createIndividualPostingName(String accountNumber){
        
    
          
  
    boolean completed=false;
    
    
    List finalDetails=new ArrayList();
    
   
         
    List<String> accountDetails=new ArrayList();
    
    accountDetails.add(dbq.getDBCurrentDate());
    
    accountDetails.add(accountNumber);
    
    accountDetails.add(dbq.AccountName(accountNumber));
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    
    accountDetails.add("0.0");
    accountDetails.add("0.0");
    accountDetails.add("0.0");
      accountDetails.add("0.0");
    accountDetails.add("0.0");
     accountDetails.add("0.0");
     accountDetails.add(fmt.month(cal.get(GregorianCalendar.MONTH)));
    accountDetails.add(cal.get(GregorianCalendar.YEAR)+"");
     accountDetails.add((cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH))+"");
    accountDetails.add(userId);
    accountDetails.add(dbq.getDBCurrentDate());
    accountDetails.add(dbq.getDBCurrentTime());
    finalDetails.add(accountDetails);
    
   
    completed=dbq.updatelist(finalDetails);
    
    return completed;
    }
     
     
   public void processSmsRemindersForLoanPaymentTomorrow(Component c){
 
   List<List>theItemsForReminder=loan.getTheNumberOfDetailsForSmsReminders(fmt.dateConverterForDatabase((System.currentTimeMillis()+(24*60*60*1000))));
 
   if(!theItemsForReminder.isEmpty()){
   for(List theItems:theItemsForReminder){
  
   sendsms.createSmsTomorrowLoanReminder(theItems.get(0).toString(), theItems.get(1).toString(),c);
   
   }
  }
   
   } 
    
   
   public void processAccumulatedDepreciation(Component c){
   
     
       
   List candidateAccountsForAccumDe=dbq.accumDepreCanAccountAuto();
   
  
   
   for(Object theAccount:candidateAccountsForAccumDe){
       
//   JOptionPane.showMessageDialog(c, dbq.AccountMasterName(theAccount.toString()));     
   
       if(!theAccount.toString().equalsIgnoreCase("EMPTY")){
       
   int LastDepreciationNumber=dbq.deprecAccumDueDates(theAccount.toString(),c);
       
//   JOptionPane.showMessageDialog(c, LastDepreciationNumber);
   
      double amount=dbq.depriAmount(theAccount.toString(), LastDepreciationNumber, c);
      
     postinAccrualsn. processDepreciationF(theAccount.toString(),amount, c);
       }
   }
   
   
   
   }
   
   
   
   
   
   
       public void processSmsRemindersForLoanPaymentToday(Component c){
 
 
   
     List<List>theItemsForReminder=loan.getTheNumberOfDetailsForSmsReminders(fmt.dateConverterForDatabase((System.currentTimeMillis())));
   
  if(!theItemsForReminder.isEmpty()){
   for(List theItems:theItemsForReminder){
  
   sendsms.createSmsTodayLoanReminder(theItems.get(0).toString(), theItems.get(1).toString(),c);
   
   }
  }
   
   }
    
    
    
    
        public void processSmsRemindersForLoanPaymentArrears(Component c){
 
 
   
     List<List>theItemsForReminder=loan.getTheNumberOfDetailsForSmsRemindersaArrears();
   
  if(!theItemsForReminder.isEmpty()){
   for(List theItems:theItemsForReminder){
  
   sendsms.createSmsArrearsLoanReminder(theItems.get(0).toString(), theItems.get(1).toString(),c);
   
   }
  }
   
   }
    
   
//        public void processSmsBirthDay(){
// 
// 
//   
//     List theItemsForReminder=dbq.getAccountNumbersForBDs();
//   
//  if(!theItemsForReminder.isEmpty()){
//   for(List theItems:theItemsForReminder){
//  
//   sendsms.createSmsArrearsLoanReminder(theItems.get(0).toString(), theItems.get(1).toString());
//   
//   }
//  }
//   
//   }  
    
    public synchronized void processNeverLoanInterestAccrueing(String accountNumber){
   String instalmentStartDatme="";
//        
//        PostingMain postinAccrualsnP=new PostingMain(); 
//        boolean x=true;


    List<List> startingInstalmentDetails=loan.getStartingLoanInstalmentForAccrual(accountNumber);//intalment Number:0,next instalment due date "yyyy-mm-dd
     
    for(List instalment:startingInstalmentDetails){
        
    Date theDate=null;
            try {
                theDate=sdf.parse(fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalment.get(1).toString())));
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!instalment.get(0).equals("xx")){
        if(theDate.before(fmt.getTodayDate())){

      int instalmentNumber=parseInt(instalment.get(0).toString());
  
  instalmentStartDatme=fmt.newInstalmentDateReduced(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalment.get(1).toString()));//dd/mm/yyyy
    
//      numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDatme);
  
        int numberOfDays=1; 
    while(numberOfDays<=30){
    
        List amortizationDetails=new ArrayList();
        amortizationDetails.add(accountNumber);//Account number:0
        amortizationDetails.add(instalmentNumber);//instalment number:1
        amortizationDetails.add( instalmentStartDatme);//Next instalment due date:2
        if(numberOfDays==30){
         amortizationDetails.add("ALAO");//Next instalment due date:2
        
        }else{
        
             amortizationDetails.add("NYAO");//Next instalment due date:2
        
        }
        amortizationDetails.add( instalmentStartDatme);//Next instalment due date:2
        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                
                postinAccrualsn.accrueInterestForMembers(amortizationDetails);
                
//                        wait(5000);
return null;

            }
        };
        accrueInterest.execute();
        
try {
    instalmentStartDatme=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDatme), "1"));
} catch (ParseException ex) {
    Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
}
//numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDatme);
  try {
              wait(8000);
          } catch (InterruptedException ex) {
              Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
          }
  
  numberOfDays++;
    }
    
    }else{
            
     
        Long numberOfDays=1L;      
      int instalmentNumber=parseInt(instalment.get(0).toString());
  
  instalmentStartDatme=fmt.newInstalmentDateReduced(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalment.get(1).toString()));//dd/mm/yyyy
    
      numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDatme);

    while(numberOfDays>0){
    
        List amortizationDetails=new ArrayList();
        amortizationDetails.add(accountNumber);//Account number:0
        amortizationDetails.add(instalmentNumber);//instalment number:1
        amortizationDetails.add( instalmentStartDatme);//Next instalment due date:2
        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                
                postinAccrualsn.accrueInterestForMembers(amortizationDetails);
                
//                        wait(5000);
return null;

            }
        };
        accrueInterest.execute();
        
try {
    instalmentStartDatme=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDatme), "1"));
} catch (ParseException ex) {
    Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
}
numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDatme);
  try {
              wait(6000);
          } catch (InterruptedException ex) {
              Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
          }
  
    }
        
            
        }
    
    }
    }}
    
     public synchronized void processContinueingInterestAccrueing(String accountNumber){
         
//   fios.stringFileWriter(fios.createFileName("test", "testit", accountNumber+"trdyr.txt"),"in");  
   
//          PostingMain postinAccrualsn=new PostingMain();   
      long numberOfDays=1L;    String instalmentStartDate="";
        
    List theInstalmentForAccrualDetails=loan.getTheInstalmentForApprovalDetails(accountNumber);//intalment Number:0,next instalment due date "yyyy
     
    
//    if(!theInstalmentForAccrualDetails.get(0).equals("xx")){
    
      int instalmentNumber=parseInt(theInstalmentForAccrualDetails.get(0).toString());
  
            try {
                instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeDate3New.txt"))), "1"));//dd/mm/yyyy
            } catch (ParseException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }
    
      numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDate);
   
    while(numberOfDays>=0){
    
            try {
                List amortizationDetails=new ArrayList();
                
                amortizationDetails.add(accountNumber);//Account number:0
                
                amortizationDetails.add(instalmentNumber);//instalment number:1
                
                amortizationDetails.add( instalmentStartDate);//Next instalment due date:2
                
                SwingWorker<Void,Void> accrueInterest = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        
                        
                        postinAccrualsn.accrueInterestForMembers(amortizationDetails);
                        
//                        wait(5000);
                        return null;
                        
                    }
                };
                
                
                accrueInterest.execute();
                wait(5000);
                
                try {
                    instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "1"));
                } catch (ParseException ex) {
                    Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                numberOfDays=fmt.diffDays( fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDate);
            } catch (InterruptedException ex) {
                Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    } 
         
    
    
//    }
    
    
    
   
    
     public synchronized void processAccumulatedInterestNormal(String accountNumber,String Signal){
         
//          PostingMain postinAccrualsn=new PostingMain(); 
            int y=0;
        
    List theInstalmentForAccumulatedInterest=loan.getTheInstalmentForAccumulatedInterestDetails(accountNumber,Signal);//intalment Number:0 "yyyy "SELECT instalment_no FROM"+" "+"newloan"+accountNumber+" "+"WHERE ((instalment_due_date<"+"'"+getDBCurrentDate()+"'"+")"+" "+"AND"+" "+"((instalment_status="+"'"+"PP"+"'"+")"+" OR "+"(instalment_status="+"'"+"NY"+"'"+")))"; Returns a list of all instalment numbers whose principal amount is  outstanding and not paid but only for the first time
     

  if(parseInt(theInstalmentForAccumulatedInterest.get(0).toString())!=9999){//if the value in the first index is 9999, it means that the selection returned an empty set which means that there no outstanding princimpal not paid for the first time

   
    while(y<theInstalmentForAccumulatedInterest.size()){//Iterate through all the instalment numbers
    
     
                List amortizationDetails=new ArrayList();//create a list store for the detais of further processing upon picking the instalment with an outstind pricipal
                
                amortizationDetails.add(accountNumber);//Account number:0
                
                amortizationDetails.add(theInstalmentForAccumulatedInterest.get(y));//instalment number:1

                
                SwingWorker<Void,Void> accumulateInterest = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        
                        
                        postinAccrualsn.accumulateInterest(amortizationDetails);//Foward the details for furhther processing

            //                        wait(5000);
                        return null;
                        
                    }
                };
//
                
                accumulateInterest.execute();
        try {
wait(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
        }
        y++;
            

    }
    } 
    }
     
     
     
     
 public synchronized void processTheActualAccumulatedInterest(){
       

    List accumulatableInterestIds=loan.getTheAccumulatableInterestIds();// "SELECT TrnId FROM accumulatedinterestrecoverstore WHERE RecoverStatus1="+"'"+"NCO"+"'";
    
    for(Object Id:accumulatableInterestIds){
        

//        details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0
//       details.add(rst3.getString("AccountNumber"));//Account Number:1
 //        details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
//       details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
//       details.add(rst3.getString("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
//       details.add(rst3.getString("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
//       details.add(rst3.getString("RecoverCharge"));//Recover Charge Amount:6
//       details.add(rst3.getString("RecoverChargeRemaining"));//Recover remaining charge Amount:7
//       details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8
//       details.add(rst3.getString("RecoveryInstalmentSize"));//Recover Instalment size used:9

        List chargeAccumulateDetails=loan.getTheAccumulatableInterestDetails(Id.toString());//"SELECT InstalmentNumber, AccountNumber,RecoverNextDueDate,  RecoverEndDate,RecoverNumberInstalments, RecoverRemainingInstalments,  TotalAccumulatedInterest,  AccumulatedInterestRemaining,  RecoveryMethod,  RecoveryInstalmentSize FROM accumulatedinterestrecoverstore WHERE TrnId="+aId;

        SwingWorker<Void,Void> processAccumulatedInterestFinalx= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            
          postinAccrualsn. processFinalAccumulatedInterest(chargeAccumulateDetails,Id.toString());
     
        return null;

        }
        };


        processAccumulatedInterestFinalx.execute();
        try {
        wait(35000);
        } catch (InterruptedException ex) {
        Logger.getLogger(updateLoans.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    
    
 }
    
        public synchronized boolean processProvisionWritteOff(List theAccountsAtRisk){

        for(Object theAccount:theAccountsAtRisk){
            


        List theProvisionDetails=loan.getDeatailsForProvisionWriteOff(theAccount.toString());
        //        theDetailsForWriteOffProvision.add(Math.abs(fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()))));
        //theDetailsForWriteOffProvision.add(remainingTotalPrincipal(accountNumber));

//        fios.stringFileWriter(fios.createFileName("test", "testit","uuu"+theProvisionDetails.get(2).toString()+"trdyr.txt"),theProvisionDetails.get(0).toString()+";"+theProvisionDetails.get(1).toString());
        
provisionWriteOffBadLoans(theProvisionDetails); 

        }

        return true;
        }
    
    
    
        public synchronized void  provisionWriteOffBadLoans(List theDetaills){

            
//             fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),theDetaills.get(0).toString());
            
        switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "NumberProvisionedWriteOff.txt"))){
        case 1:
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
//       
//            switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        
//                case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
           List provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6 
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
        
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }     
}
//        break;

        }  
        
        
//        }    

        break;

        case 2:
             if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
//        switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

     
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
           List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6 
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }     
}
//        break;

//        }  
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
         
  
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            List     provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6 
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        
        provisionBadLoan(theProvisionedInstalment);      
}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        } 
        }
            
            break;

        case 3:
            
          if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
         List      provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }      
}
//        break;
//
//        }  
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
          List    provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
        }
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
         
     
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }     
        }
//        break;
//        
//        
//        
//        }    
        }
            break;

        case 4:
            
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
       List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6   
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//
//        }  
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
        
        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
          
       List provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6  
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }     
}
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){

//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
        
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
  List           provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6 
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
     if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//        
//        
//        
//        }   
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
//      switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite4.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")))/100) ;      
        
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        List    provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6  
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
       
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        
//        
//        }    
        
        
        
        }     
            
            
            
            break;

        case 5:
           if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
  List      provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6    
            
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//
//        }  
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
  List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6       
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List  provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }   
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
//      switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite4.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")))/100) ;      
        
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
     List  provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6       
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
         }
        }     

//        break;
//        
//        
//        
//        }    
//        
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
//    switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite5.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")))/100) ;      
       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
 List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6         
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
     if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }     
}
//        break;
//        
//        
//        
//        }    
        }     
           
            
            break;

        case 6:
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List  provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6       
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//
//        }  
//        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
  List  provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6          
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }      
}
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
   
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
     List     provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6        
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        } 
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
//      switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite4.txt"))){
//        case 77:
if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")))/100) ;      
       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
   List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6        
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//        
//        
//        
//        }    
        
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
//    switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite5.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")))/100) ;      
     
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List  provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6        
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }    
            
        
        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite6.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")))/100) ;      
    
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6          
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }   
//        
        
        
        }    
                
            
            
            
            break;

        case 7:
     if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

 
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
       List        provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6     
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }    
}
//        break;
//
//        }  
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
  
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List       provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
   
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
   List      provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6         
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        
//        
//        }    
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
//      switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite4.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")))/100) ;      
        
   
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6         
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        } 
}
//        break;
//        
//        
//        
//        }    
//        
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
//    switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite5.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")))/100) ;      
     
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List    provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }    
            
        
        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite6.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")))/100) ;      
     
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List        provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6     
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }   
        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[1]))){
        
//          switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite7.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper7.txt")))/100) ;      
    
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper7.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6         
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }   
        
        
        
        }       
            
            
            break;

        case 8:
           if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
       
//              fios.stringFileWriter(fios.createFileName("test", "testit", theDetaills.get(2).toString()+"trdyr.txt"),parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100)+";"+parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))+";"+parseDouble(theDetaills.get(1).toString()));  
             
//              switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")))/100) ;      

         
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6           
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  

//        break;
//
//        }  
}
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite2.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")))/100) ;      
  
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List        provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper2.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6       
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower2.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        } 
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite3.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")))/100) ;      
       
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
   List      provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper3.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower3.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        
//        
//        } 
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
//      switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite4.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")))/100) ;      
        
       
        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
     List    provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper4.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6    
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        
        
        
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower4.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }    
//        
        
        
        }else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
//    switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite5.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")))/100) ;      
      
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
     List    provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper5.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
       if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower5.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        
//        
//        }    
            
        
        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
//         switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite6.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")))/100) ;      
        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List   provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper6.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower6.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   
}
//        break;
//        
//        
//        
//        }   
        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[1]))){
        
//          switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite7.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper7.txt")))/100) ;      
    
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
      List     provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper7.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6      
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
         if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower7.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }  
}
//        break;
//        
//        
//        
//        }   
//        
        
        
        } else if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower8.txt")).split("[-]", 2)[0]))&&(parseInt(theDetaills.get(0).toString())<=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower8.txt")).split("[-]", 2)[1]))){
        
//            switch(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite8.txt"))){
//        case 77:
//if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){  
        double theProvisionedInstalment=parseDouble(theDetaills.get(1).toString())*  (parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper8.txt")))/100) ;      
        
         SwingWorker<Void,Void> provisionedCaptured= new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
    List     provisionDetails=new ArrayList();

        provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
        provisionDetails.add(theDetaills.get(2).toString());//account number:1
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower8.txt")));//range provisioned:2
        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper8.txt")));//percentage provisioned:3
        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6     
          loan. captureProvisioned(provisionDetails);
     
        return null;

        }
        };
        provisionedCaptured.execute();
        provisionBadLoan(theProvisionedInstalment);      
//}
//        break;
//        case 66:
if(fios.intFileReader(fios.createFileName("persistence", "loanSetUp", "provisionWrite1.txt"))==66){
        if((parseInt(theDetaills.get(0).toString())>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower8.txt")).split("[-]", 2)[1]))){
        
        writeOffBadLoan(theDetaills);   
        
        }   } 
//        break;
//        
//        
//        
//        }
        
        
        }      
           
            
            
            
            break;
            
        }
        }
  
  private synchronized void provisionBadLoan(double instalment){
  
  
      double pendingProvisioned=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "actualProvisioned.txt")))+instalment;      
         
      fios.stringFileWriter(fios.createFileName("persistence", "loanSetUp", "actualProvisioned.txt"), pendingProvisioned+"");
      
      
      
  
  }
  
  
   private synchronized void writeOffBadLoan (List instalment){
   
   if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){ 
       
   postinAccrualsn.completeWriteOffAccrual(instalment.get(1).toString(),instalment.get(2).toString());
   
    loan.adjustStatusOfWriteOff(instalment);
    
   }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){
   
    postinAccrualsn.completeWriteOffDirect(instalment.get(1).toString(),instalment.get(2).toString());
   
   loan.adjustStatusOfWriteOff(instalment);
   
   }
   
   
   
   
   }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    }