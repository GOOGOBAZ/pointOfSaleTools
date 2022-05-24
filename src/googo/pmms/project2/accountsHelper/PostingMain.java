        /*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
        package googo.pmms.project2.accountsHelper;

        import googo.pmms.project2.reportsHelper.BalanceSheet;
        import googo.pmms.project2.databaseConnectors.JdbcConnector;
        import googo.pmms.project2.databases.ReportsDatabase;
        import googo.pmms.project2.databases.DatabaseQuaries;
        import googo.pmms.project2.databases.AccountNumberCreationDataBase;
        import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.frames.mainPrinter;
//import googo.pmms.project2.frames.PostingEntryWindow;
        import googo.pmms.project2.loanHelper.MaxBorrowed;
        import java.awt.Component;
        import static java.lang.Double.parseDouble;
        import static java.lang.Integer.parseInt;
        import java.sql.Date;
        import java.text.DecimalFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
import java.time.YearMonth;
        import java.util.ArrayList;
        import java.util.GregorianCalendar;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
        import javax.swing.SwingWorker;



        public class PostingMain {
     
        MaxBorrowed mxb=new MaxBorrowed();
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
        Formartter fmt= new Formartter();
        GregorianCalendar cal = new GregorianCalendar();
        JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt")));
        DatabaseQuaries dbq =new DatabaseQuaries();
        loanDatabaseQuaries loan=new loanDatabaseQuaries();
        AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
        ReportsDatabase report =new ReportsDatabase();
        BalanceSheet bsheet= new BalanceSheet();
        DecimalFormat numberFormat =new DecimalFormat("#.##");
        DecimalFormat nu =new DecimalFormat("#.#");
        DecimalFormat nuw =new DecimalFormat("#");
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
        String userId;
        SendSms sendsms= new SendSms();
        DecimalFormat df1 = new DecimalFormat("#,###");
//        PostingEntryWindow posting= new PostingEntryWindow(this.userId);
       Component c;
       
       mainPrinter printReceipt=new mainPrinter();
     
        public void setUserID(String userid){
        this.userId=userid;

        }
        
        public PostingMain(Component c1){
        c=c1;
        }
        
        public synchronized void recoverTheReturnOnInvestment(String returnOnInverstment,String accountNumber,Component c){
        
        //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
//JOptionPane.showMessageDialog(c, returnOnInverstment);
//JOptionPane.showMessageDialog(c, dbq.AccountName(accountNumber));
        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        if(parseDouble(returnOnInverstment)>0.0){
        debit1 .add(0,  "02-2770001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Return on shares Investiment for"+" "+dbq.AccountName(accountNumber));
        debit1 .add(3, "Accrued on "+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  returnOnInverstment);
        debit1 .add(6, "05-5220001-10");
        debit1 .add(7, dbq.AccountName("05522000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "05-5220001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
         credit.add(2, "Return on shares Investiment for"+" "+dbq.AccountName(accountNumber));
        credit .add(3, "Accrued on "+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, returnOnInverstment);
        credit.add(6, "02-2770001-10");
        credit.add(7, dbq.AccountName("02277000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

//        //        try {
//        //          wait(5000);
//        //      } catch (InterruptedException ex) {
//        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        //      }
//        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {

        generalPosting(debit1, credit);
//        return null;
//        }};
//
//        postTheEntry.execute();
        }
            
        }
        
        
        public synchronized void completeProvisioning(){
        
            double existingAmountProvisioned=dbq.existingProvisionedAmount();
            
            double amountToProvision=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "actualProvisioned.txt")));
        
            if(amountToProvision>0){
                
            double diff=existingAmountProvisioned-amountToProvision;
            
            if(diff>0){
            
                reduceAmountProvisioned(diff);
            
            
            }else if(diff<0){
            
            increaseAmountProvisioned(Math.abs(diff));
            }
            
            }
        
     fios.stringFileWriter(fios.createFileName("persistence", "loanSetUp", "actualProvisioned.txt"), "0.0");     
        
        
        }
        
        
        
        
        
        
        
        public synchronized void completeLoanPenaltyUpdates(String accountNumber,Component c){

//            JOptionPane.showMessageDialog(c, accountNumber);
            
        int chargesOn=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesOnTheX.txt"));//The actual item to pick the charge from, interest, principal,instalment or loan amount

        switch(chargesOn){
        case 1:


        int chargesAs1=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesAsY.txt"));
       
        switch(chargesAs1){
        case 1:

        updateActuallyLoans(accountNumber, "instalmentAsPercentage",c);
//        JOptionPane.showMessageDialog(c, chargesAs1);
        break;
        case 2:
//JOptionPane.showMessageDialog(c, chargesAs1);
            
        updateActuallyLoans(accountNumber, "instalmentAsFraction",c);
        
        break;
        case 3:
            
        updateActuallyLoans(accountNumber, "instalmentAsFixedFigure",c);

        break;
        }  




        break;
        case 2:


        int chargesAs2=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesAsY.txt"));
        switch(chargesAs2){
        case 1:
        updateActuallyLoans(accountNumber, "princepalAsPercentage",c);

        break;
        case 2:
        updateActuallyLoans(accountNumber, "princepalAsFraction",c);

        break;
        case 3:
        updateActuallyLoans(accountNumber, "princepalAsFixedFigure",c);

        break;
        }
        break;
        case 3:


        int chargesAs=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesAsY.txt"));
        switch(chargesAs){
        case 1:
        updateActuallyLoans(accountNumber, "interestAsPercentage",c);

        break;
        case 2:

        updateActuallyLoans(accountNumber, "interestAsFraction",c);
        break;
        case 3:

        updateActuallyLoans(accountNumber, "interestAsFixedFigure",c);
        break;

        }     
        break;
        case 4:


        int chargesAs3=fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesAsY.txt"));
        switch(chargesAs3){
        case 1:
        updateActuallyLoans(accountNumber, "loanBalanceAsPercentage",c);

        break;
        case 2:
        updateActuallyLoans(accountNumber, "loanBalanceAsFraction",c);
        break;
        case 3:

        updateActuallyLoans(accountNumber, "loanBalanceAsFixedFigure",c);

        break;

        }      
        break;
        }
        
        }



        public synchronized void updateActuallyLoans(String accountNumber, String updateType,Component c){

        List processingDetails=new ArrayList();
//JOptionPane.showMessageDialog(c, accountNumber+";"+updateType);
        if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "ChargeOnce.txt"))==12){

        processingDetails.add(accountNumber);
        processingDetails.add(updateType);
        processingDetails.add("ChargeOnce");
        processLoanArrears(processingDetails,c);


        }else if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "RecycleCharges.txt"))==12){

        processingDetails.add(accountNumber);
        processingDetails.add(updateType);
        processingDetails.add("RecycleCharges");
        processLoanArrears(processingDetails,c);


        }else if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "RecycleCompoundCharges.txt"))==12){

        processingDetails.add(accountNumber);//Account Number
        processingDetails.add(updateType);//Charge type, percentage, fraction or whole
        processingDetails.add("RecycleCompoundCharges");//chargeOnce,Recyle or compound
        processLoanArrears(processingDetails,c);



        }


        }



        public synchronized void accumulateInterest(List accrualDetails){

        String instalmentNextDueDate = null, instalmentStartDate=null,bigInstalmentNextDueDate=null,instalmentEndDate = null;long recoverNumberInstalments=0;double instalmentSize=0.0;
        //
        //        amortizationDetails.add(accountNumber);//Account number:0
        //        amortizationDetails.add(theInstalmentForAccumulatedInterest.get(y));//instalment number:1

        List interestToAccumulateDetails=loan.detailsOfInterestToAccumulate(accrualDetails);  //String query = "SELECT instalment_due_date,interest_amount, princimpal_amount, PrincipalRemaining FROM"+" "+"newloan"+instalmentDatails.get(0).toString()+" "+"WHERE instalment_no="+instalmentDatails.get(1).toString();
        //returned=((interest/principal)*remaiPri);

        //  date=rst3.getString("instalment_due_date");  //instalment due date
        //  interest=rst3.getDouble("interest_amount");
        //  principal=rst3.getDouble("princimpal_amount");
        //remaiPri=rst3.getDouble("PrincipalRemaining");
        //  
        //  
        //  theDetails.add(date);
        //theDetails.add(returned);

        ////  fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),interestToAccumulateDetails.get(0).toString()+"");  

        instalmentStartDate=fmt.dateConverterForDatabase(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(interestToAccumulateDetails.get(0).toString()))+fmt.convertMillsAsNT(fios.stringFileReader(fios.createFileName("persistence", "useCRB", "frequencyOfAccumulatedInterest.txt")), fios.stringFileReader(fios.createFileName("persistence", "useCRB", "daysPastInstalmentDate.txt"))));//Determine the effective date to begin accumulating the interest. The entity determines the days allowed before the system can begin accumulating the interest


        bigInstalmentNextDueDate=fmt.dateConverterForDatabase(fmt.convertTimeToMillseconds(fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(interestToAccumulateDetails.get(0).toString()))));

long nextDueDate=fmt.convertTimeToMillseconds(fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(interestToAccumulateDetails.get(0).toString())));
long today=System.currentTimeMillis();

        String accountNumber=accrualDetails.get(0).toString();

        int intalmentNumber=parseInt(accrualDetails.get(1).toString());


        try {
           if(nextDueDate<today){
        instalmentEndDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentStartDate), "30"));//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           }else if(nextDueDate>=today){
           
            instalmentEndDate=sdfS.format(new Date(today));//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           
           }
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        List accrualDetailsFinal=new ArrayList();//create a list for transporting the details of  accumulating interest to the next step

        accrualDetailsFinal.add(intalmentNumber);//Instalment Number:0
        accrualDetailsFinal.add(accountNumber);//Account Number:1
        accrualDetailsFinal.add(interestToAccumulateDetails.get(1).toString());//Accumulated interest:2


        switch(fios.stringFileReader(fios.createFileName("persistence", "useCRB", "frequencyOfInterestAccrual.txt"))){//Determine the next instalment due date which is dependent on the cycle for accumulating the interest. If its daily forinstance, increament to the next due data by adding one to the staring date. 

        case "Daily":
            
        try {
        instalmentNextDueDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentStartDate), "1"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
           if(nextDueDate<today){
        recoverNumberInstalments=30;   //Specify the total number of instalments to recover  
           }else if(nextDueDate>=today){
           
            recoverNumberInstalments=fmt.diffDays(sdf.format(new Date(today)), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentStartDate));//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           
           }
      
        instalmentSize=  parseDouble(interestToAccumulateDetails.get(1).toString())/30;//Determine the isntalment size for recoveing the instalment 

        break;
        case "Weekily":
        try {
        instalmentNextDueDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentStartDate), "8"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         if(nextDueDate<today){
          recoverNumberInstalments=4;  //Specify the total number of instalments to recover  
           }else if(nextDueDate>=today){
           
            recoverNumberInstalments=((fmt.diffDays(sdf.format(new Date(today)), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentStartDate)))/30)*4;//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           
           }
      
        instalmentSize=  parseDouble(interestToAccumulateDetails.get(1).toString())/4;
        
        
               
        
        break;
        case "Fortnightly":
        try {
        instalmentNextDueDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentStartDate), "15"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        if(nextDueDate<today){
          recoverNumberInstalments=2;  //Specify the total number of instalments to recover  
           }else if(nextDueDate>=today){
           
            recoverNumberInstalments=((fmt.diffDays(sdf.format(new Date(today)), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentStartDate)))/30)*2;//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           
           }
     
        instalmentSize=  parseDouble(interestToAccumulateDetails.get(1).toString())/2;//Determine the isntalment size for recoveing the instalment
        break;
        case "Monthly":
        try {
        instalmentNextDueDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentStartDate), "30"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         if(nextDueDate<today){
           recoverNumberInstalments=1;    //Specify the total number of instalments to recover  
           }else if(nextDueDate>=today){
           
            recoverNumberInstalments=((fmt.diffDays(sdf.format(new Date(today)), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentStartDate)))/30)*1;//Determine the instalment accumulation end date which should be 30 days from the time when the accumulation of interest begins
           
           }
           
        instalmentSize=  parseDouble(interestToAccumulateDetails.get(1).toString())/1;//Determine the isntalment size for recoveing the instalment
        break;

        }




        accrualDetailsFinal.add(instalmentStartDate);//instalment recovery start date:3   
        accrualDetailsFinal.add(instalmentNextDueDate);//instalment next due date:4    
        accrualDetailsFinal.add(instalmentEndDate);//instalment recovery end date:5
        accrualDetailsFinal.add(recoverNumberInstalments);//Total number of instalments to be recovered:6
        accrualDetailsFinal.add(instalmentSize);//instalment size:7






        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){ //Determine the method that is going to be used for recovering the instalments which is dependent on the entity's income recognition method; Either accrual or cash

        accrualDetailsFinal.add("Accrual");//instalment recovery method:8

        }

        else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){

        accrualDetailsFinal.add("Cash");////instalment recovery method:8

        }

        accrualDetailsFinal.add(bigInstalmentNextDueDate);//The actual instalment Next due Date:9


        SwingWorker<Void,Void> accrueInsteresForAccumulationinterestPay=new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {   
        loan.accrueInsteresForAccumulation(accrualDetailsFinal);//Forward to the next class for furhter processing
        return null;
        }  };
        accrueInsteresForAccumulationinterestPay.execute();
        }
        
        
        
        public synchronized void accrueInterestForMembers(List accrualDetails){
        //Account number:0
        //instalment number:1
        //Next instalment due date:2
        //Date instalment Accrued:3

        //     fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),"remainingTotalAmount amountTobeAccrued");

        long nextInstalmentDueDate=1L,nextAccrualDate=1L;
        
        String borrowerAccountNumber=accrualDetails.get(0).toString();

        double amountTobeAccrued=loan.getTheAmountForAccrual(accrualDetails);//Picks the amount by deviding the interest rate with 30 days

        double remainingTotalAmount=loan.getTheRemainingAmountForAccrual(accrualDetails);//As the accrual process is taking place, its the accrued interest which is reduced and the remaining accrued interest is increased

        int existingDaysAccrued=fios.intFileReader(fios.createFileName("loanApplication", "loanProcessing", "NumberOfDaysAccrued"+borrowerAccountNumber+".txt"));

        
//        fios.stringFileWriter(fios.createFileName("test", "testit", borrowerAccountNumber+"trdyr.txt"),borrowerAccountNumber+""+";"+amountTobeAccrued+";"+remainingTotalAmount+";"+existingDaysAccrued);
       
        try {
        nextInstalmentDueDate=sdfS.parse(fmt.forDatabaseWithFullYearBeginningWithDate(accrualDetails.get(2).toString())).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        nextAccrualDate=fmt.getExDateIncreamented(new Date(System.currentTimeMillis()), "1").getTime();
        
        //        fios.stringFileWriter(fios.createFileName("test", "testit", borrowerAccountNumber+"trdyr.txt"),remainingTotalAmount+";"+amountTobeAccrued);
//        if(remainingTotalAmount>0.0){
//
//        if(existingDaysAccrued==30||nextInstalmentDueDate==nextAccrualDate){
//        List accrualDatailsTwo=new ArrayList();
//        accrualDatailsTwo.add(borrowerAccountNumber);//Borrower account number:0
//        accrualDatailsTwo.add(accrualDetails.get(1).toString());//instalment number:1
//        accrualDatailsTwo.add(accrualDetails.get(2).toString());//Date instalment Accrued:2
//        accrualDatailsTwo.add(remainingTotalAmount);//Amount Accrued:3
//        accrualDatailsTwo.add("ALAO");//Accrual status:4
//        accrueInterestActually(accrualDatailsTwo);
//
//        }else{
//
//
//        List accrualDatailsTwo=new ArrayList();
//        accrualDatailsTwo.add(borrowerAccountNumber);//Borrower account number:0
//        accrualDatailsTwo.add(accrualDetails.get(1).toString());//instalment number:1
//        accrualDatailsTwo.add(accrualDetails.get(2).toString());//Date instalment Accrued:2
//        accrualDatailsTwo.add(amountTobeAccrued);//Amount Accrued:3
//        accrualDatailsTwo.add("NYAO");//Accrual status:4
//        accrueInterestActually(accrualDatailsTwo);
//
//
//        }
//
//
//
//        }
           //    fios.stringFileWriter(fios.createFileName("test", "testit", details.get(0).toString()+"trdyr.txt"),details.get(5).toString());   
        //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        String accountNumber=borrowerAccountNumber;
        String interestAC=fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setInterestAc1.txt"));
        String BatchNumber= batchCode();
//        fios.stringFileWriter(fios.createFileName("test", "testit", accountNumber+"trdyr.txt"),BatchNumber);
        boolean conf1=false;
        List debit1= new ArrayList();List credit= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "01-1190001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Interest Receivable from"+" "+dbq.AccountName(accountNumber)+" "+"due on"+" "+accrualDetails.get(2).toString());
        debit1 .add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  amountTobeAccrued);
        debit1 .add(6, fmt.putSeparatorsOnNormaAccount(interestAC));
        debit1 .add(7, dbq.AccountName(accountNumber));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(interestAC));
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Interest Received from"+dbq.AccountName(accountNumber)+" "+"due on"+" "+accrualDetails.get(2).toString());
        credit.add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, amountTobeAccrued);
        credit.add(6, "01-1190001-10");
        credit.add(7, dbq.AccountName("01119000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");
         
          SwingWorker<Void,Void> postFinal = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        
        generalPosting(debit1, credit);


          return null;
        }};
        postFinal  .execute();
          //Account number:0
        //instalment number:1
        //Next instalment due date:2
        //Date instalment Accrued:3
        
        
          
        List LoanDetailsForLoanSchedule = new ArrayList(); 
        if(accrualDetails.get(3).equals("ALAO")){
        LoanDetailsForLoanSchedule.add(accountNumber);//Account Number:0
        LoanDetailsForLoanSchedule.add(accrualDetails.get(1));//Instalment Number:1
        LoanDetailsForLoanSchedule.add(amountTobeAccrued);//Interest to be accrued:2
        LoanDetailsForLoanSchedule.add("ALA");//Accrual status:3


        SwingWorker<Void,Void> updateAccrualFinal = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        loan.updateLoanScheduleAccrual(LoanDetailsForLoanSchedule);
        return null;
        }};

        updateAccrualFinal.execute();
        } else if(accrualDetails.get(3).equals("NYAO")){

        LoanDetailsForLoanSchedule.add(accountNumber);//Account Number:0
        LoanDetailsForLoanSchedule.add(accrualDetails.get(1));//Instalment Number:1
        LoanDetailsForLoanSchedule.add(amountTobeAccrued);//Interest to be accrued:2
        LoanDetailsForLoanSchedule.add("NYA");//Accrual status:3
        SwingWorker<Void,Void> updateAccrualFinal = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        loan.updateLoanScheduleAccrual(LoanDetailsForLoanSchedule);

        return null;
        }};

        updateAccrualFinal.execute();
        }
        

        fios.stringFileWriter(fios.createFileName("loanApplication", "loanProcessing", "accrueingStatus"+accrualDetails.get(0).toString()+".txt"),"Accrueing");   
      
        }


        public synchronized boolean loanRepaymentsNewWrittenOff(List debit ,List  credit,List processOrder,List otherOrders){
    
    String completeStatus=otherOrders.get(0).toString();
    
      String trxId  =otherOrders.get(1).toString();  
      
       YearMonth MonthYearPaid =(YearMonth)otherOrders.get(2); 
       
       String closingNotes=otherOrders.get(3).toString();   
       
          double currentAccruedInterest= 0, currentInterestNow = 0,currentAccumulatedInterestNow= 0,currentLoanPenaltyNow= 0,currentPrincipalNow= 0;int currentInstalmentId= 0;
          
          double totalInterestPaid=0.0,totalAccumulatedInterestPaid=0.0,totalLoanPenaltyPaid=0.0,totalPrincipalPaid=0.0,totalAccruedInterestPaid=0.0;//Total interest,accumulated interest,loan penalty or principal to be posted in the general ledger after update of the loan schedule   
         
             double amountPaid=0.0;
          
        String accountNumberNow=fmt.formatAccountWithSeperators(debit.get(0).toString());//Account Number of the loan being paid.
       
        List trnIdInterest=loan.loanTrnIdInterestRateWrittenOff(accountNumberNow);
        
        boolean successful=false,successful1=false,successful2=false;//Status of loan payment finally

        amountPaid=parseDouble(debit.get(5).toString());//The loan amount being paid, catering for Interest,principal,penalty and accumulated interest

           List newPaymentCame=new ArrayList();
        
newPaymentCame.add(MonthYearPaid.getMonth().toString());//MonthPaidFor
newPaymentCame.add(MonthYearPaid.getYear());//YearPaidFor
newPaymentCame.add(trnIdInterest.get(0));//loanTrnId
newPaymentCame.add("writtenOffloan"+accountNumberNow);//LoanId
newPaymentCame.add(debit.get(9).toString());//BatchCode
newPaymentCame.add("0.0");//AmountDisbursed
newPaymentCame.add("0.0");//Expected Interest
newPaymentCame.add("0.0");//Expected Total Amount
newPaymentCame.add(trnIdInterest.get(1));//Interest Rate
newPaymentCame.add(amountPaid);//Amount Paid
newPaymentCame.add("0.0");//PrincipalPaid
newPaymentCame.add("0.0");//Interest Paid
newPaymentCame.add("0.0");//AccumulatedInterestPaid
newPaymentCame.add("0.0");//LoanPenaltyPaid

  SwingWorker<Void,Void> createTheInitailLoanPayState=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        dbq.createTheInitailLoanPayStateWrittenOff(newPaymentCame,c); 
                                return null;
                            }  };
                     createTheInitailLoanPayState.execute();

        switch(completeStatus){//This informs whether the instalment payment is being completed or if its in the middle

        case "Not Complete":

        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
            
        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
        List newOrder=new ArrayList(processOrder);

        for(Object order:newOrder){//Follow the processing order as it was choosen

        switch(order.toString()){
        case "Interest":// if the item in the queue is interest
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){



        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccruedInterest=amountPaid;

        }
        //               
        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();

        loan.updateNewAccruedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);
        
        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }





        }else{
        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();


        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentInterestNow,c);
        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated


        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }
        break;

        case "Accumulated Interest":

        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccumulatedInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
        //                        return null;
        //                    }  };
        //             accumulatedInterestPay.execute();

        loan.updateNewAccumulatedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }


        break;
        case "Loan Penalty":

        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentLoanPenaltyNow=amountPaid;

        }

        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
        //                        return null;
        //                    }  };
        //             loanPenaltyPay.execute();
        loan.updateNewLoanPenaltyNowWrittenOff(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);     
        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }

        break;
        case "Principal":


        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentPrincipalNow=amountPaid;

        }

        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
        //                        return null;
        //                    }  };
        //             principalPayLoan.execute();
        loan.updateNewLoanPrincipalNowWrittenOff(accountNumberNow,currentInstalmentId,currentPrincipalNow,c); 
        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }



        break;
        }

        }
        }
        break;
        
        case "Complete":

        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
        List newOrder=new ArrayList(processOrder); int y=0;

        while(y<newOrder.size()){//Follow the processing order as it was choosen

        switch(newOrder.get(y).toString()){
        case "Interest":// if the item in the queue is interest
            
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){



        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccruedInterest=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();
        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);  
        
        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }





        }else{
        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();
        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentInterestNow,c);  
        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }
        break;

        case "Accumulated Interest":

        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccumulatedInterestNow=amountPaid;

        }
        //               
        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
        //                        return null;
        //                    }  };
        //             accumulatedInterestPay.execute();
        loan.updateNewAccumulatedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }


        break;
        case "Loan Penalty":

        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentLoanPenaltyNow=amountPaid;

        }

        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
        //                        return null;
        //                    }  };
        //             loanPenaltyPay.execute();
        loan.updateNewLoanPenaltyNowWrittenOff(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);  
        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }

        break;
        case "Principal":

        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentPrincipalNow=amountPaid;

        }

        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
        //                        return null;
        //                    }  };
        //             principalPayLoan.execute();
        loan.updateNewLoanPrincipalNowWrittenOff(accountNumberNow,currentInstalmentId,currentPrincipalNow,c);  
        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }



        break;
        }
        y++;
        }
        //    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),currentPrincipalNow+"");  

        }
   if(totalInterestPaid>0.0){
   totalInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalAccruedInterestPaid>0.0){
      totalAccruedInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalPrincipalPaid>0){
      totalPrincipalPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalAccumulatedInterestPaid>0){
    totalAccumulatedInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if(totalLoanPenaltyPaid>0){
      totalLoanPenaltyPaid+=amountPaid;
   amountPaid=0.0;
   } 

        String newLoanId=   loan.closeLoanAccount(accountNumberNow); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumberNow);//Borrower Account Number:1
        loan.updateClosedLoanWrittenOff(closedDetails);
        dbq.updateClosedStatementWrittenOff(closedDetails);
//        JOptionPane.showMessageDialog(c, "closed");
        
        break;

        }
        
        String borrowerAccount=fmt.formatAccountWithSeperators(debit.get(0).toString()); 
        
       if(debit.get(10).toString().equalsIgnoreCase("LoanR")){
        
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.debitCashFirst(borrowerAccount, debit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"Account Deposit for Loan Payment");

        }
        }
       
        if(debit.get(10).toString().equalsIgnoreCase("LoanRD")){
        
         dbq.reduceSavings(debit);

        }
       
       
       
       
       
        successful1= dbq.debit(debit,c);

        if(successful1){

        if(totalInterestPaid>0.0){

//           fios.stringFileWriter(fios.createFileName("test", "testit", "totalInterestPaid"+"trdyr.txt"),totalInterestPaid+"");  

//
//        SwingWorker<Void,Void> payTotalInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   

        processInterestPaymentWrittenOff(totalInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   

//        return null;
//        }  };
//        payTotalInterestPaid.execute();
        successful2=true;
        }

        if(totalAccruedInterestPaid>0.0){

//
//        SwingWorker<Void,Void> payTotalAccruedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedInterestPaymentWrittenOff(totalAccruedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));  
//        return null;
//        }  };
//        payTotalAccruedInterestPaid.execute();
        successful2=true;
        }





        if(totalAccumulatedInterestPaid>0){

        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis



//
//        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedAccumulatedInterestPaymentWrittenOff(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalAccumulatedInterestPaid.execute();
        successful2=true;

        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis


//        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccumulatedInterestPayment(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   
//        return null;
//        }  };
//        payTotalAccumulatedInterestPaid.execute();
        successful2=true;
        }

        }

        if(totalLoanPenaltyPaid>0){


        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedLoanPenaltyPaymentWrittenOff(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();

        successful2=true;
        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processLoanPenaltyPaymentWrittenOff(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));
//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();
        successful2=true;
        }

        }

        if(totalPrincipalPaid>0){
// fios.stringFileWriter(fios.createFileName("test", "testit", "totalPrincipalPaid"+"trdyr.txt"),totalInterestPaid+";"+credit.get(5).toString());  
//        SwingWorker<Void,Void> payTotalPrincipalPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processPrincipalPaymentWrittenOff(totalPrincipalPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalPrincipalPaid.execute();   

        successful2=true;


        }



        }

        if(successful1==successful2){



        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){//Accumulated Interest used


        }

        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

        }



        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        SwingWorker<Void,Void> loanSms= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        sendsms.createLoanPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        return null;
        }

        };
        loanSms.execute();



        } 

        SwingWorker<Void,Void> loanSaving= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLaonSavingsSharesForLoanAmount(fmt.formatAccountWithSeperators(debit.get(0).toString()),debit.get(5).toString());
       
        return null;
        }

        };
        loanSaving.execute();
        successful=true;
        }

//        SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(debit,c);
//       
//        return null;
//        }
//
//        };
//        
//        updateLoanBalances.execute();
        



        return successful;
        }

       
           public synchronized boolean loanRepaymentsNew(List theDetails,List debit,List credit,List processOrder,List otherOrders){
//               String summuryReportText="";
//       int counter=1;
//      
//    String completeStatus=otherOrders.get(0).toString();
//    
      String trxId  =otherOrders.get(1).toString();  
//      
//       YearMonth MonthYearPaid =(YearMonth)otherOrders.get(2); 
//       
//       String closingNotes=otherOrders.get(3).toString();   
//       
//          double currentAccruedInterest= 0, currentInterestNow = 0,currentAccumulatedInterestNow= 0,currentLoanPenaltyNow= 0,currentPrincipalNow= 0;int currentInstalmentId= 0;
//          
//          double totalInterestPaid=0.0,totalAccumulatedInterestPaid=0.0,totalLoanPenaltyPaid=0.0,totalPrincipalPaid=0.0,totalAccruedInterestPaid=0.0;//Total interest,accumulated interest,loan penalty or principal to be posted in the general ledger after update of the loan schedule   
//         
//             double amountPaid=0.0;
//          
//        String accountNumberNow=fmt.formatAccountWithSeperators(debit.get(0).toString());//Account Number of the loan being paid.
//       
//        List trnIdInterest=loan.loanTrnIdInterestRate(accountNumberNow);
//        
        boolean successful=false,successful1=false,successful2=false;//Status of loan payment finally
//
//        amountPaid=parseDouble(debit.get(5).toString());//The loan amount being paid, catering for Interest,principal,penalty and accumulated interest
//
//           List newPaymentCame=new ArrayList();
//           
//             if(!processOrder.isEmpty()){
//    
//            for(Object txt:processOrder){
//              
//                if(counter==1){
//                summuryReportText=txt.toString();
//                }else{
//                  summuryReportText=summuryReportText.concat(";"); 
//                   summuryReportText=summuryReportText.concat(txt.toString());
//                }
//                
//           
//           
//             counter++;
//            }
//    
//            }
    

//JOptionPane.showMessageDialog(c, summuryReportText);
            
//            JOptionPane.showMessageDialog(c, otherOrders.get(0).toString());//Completion status
//          JOptionPane.showMessageDialog(c, otherOrders.get(1).toString());//Brach sequence number
//            JOptionPane.showMessageDialog(c, otherOrders.get(2).toString());//Month year
//              JOptionPane.showMessageDialog(c, otherOrders.get(3).toString());//Closure comments
//      
//             
//                      
//            JOptionPane.showMessageDialog(c, processOrder.get(0).toString());//Interest/Principal/Accumulated Interest/loan Penalty
//          JOptionPane.showMessageDialog(c, processOrder.get(1).toString());//Interest/Principal/Accumulated Interest/loan Penalty
//            JOptionPane.showMessageDialog(c, processOrder.get(2).toString());//Interest/Principal/Accumulated Interest/loan Penalty
//              JOptionPane.showMessageDialog(c, processOrder.get(3).toString());//Interest/Principal/Accumulated Interest/loan Penalty
//              
//              
////              
//                  JOptionPane.showMessageDialog(c, debit.get(0).toString());//Debit Account Number
//          JOptionPane.showMessageDialog(c, debit.get(1).toString());//Txn Date
//            JOptionPane.showMessageDialog(c, debit.get(2).toString());//Narration
//              JOptionPane.showMessageDialog(c, debit.get(3).toString());//Txn Type
//                  JOptionPane.showMessageDialog(c, debit.get(4).toString());//Value Date
//          JOptionPane.showMessageDialog(c, debit.get(5).toString());//Amount Paid
//            JOptionPane.showMessageDialog(c, debit.get(6).toString());//Credit Account Number
//              JOptionPane.showMessageDialog(c, debit.get(7).toString());//Credit Account Name
//                  JOptionPane.showMessageDialog(c, debit.get(8).toString());//Code 002
//          JOptionPane.showMessageDialog(c, debit.get(9).toString());//Batch Number
//            JOptionPane.showMessageDialog(c, debit.get(10).toString());//Loan payament Type LOANR
//              JOptionPane.showMessageDialog(c, debit.get(11).toString());//Code 22
//    
//               JOptionPane.showMessageDialog(c, debit.get(12).toString());//Code 24
//
//
//
//    
//                  JOptionPane.showMessageDialog(c, credit.get(0).toString());//Credit Account Number
//          JOptionPane.showMessageDialog(c, credit.get(1).toString());//Txn Date
//            JOptionPane.showMessageDialog(c, credit.get(2).toString());//Narration
//              JOptionPane.showMessageDialog(c, credit.get(3).toString());//Txn Type
//                  JOptionPane.showMessageDialog(c, credit.get(4).toString());//Value Date
//          JOptionPane.showMessageDialog(c, credit.get(5).toString());//Amount Paid
//            JOptionPane.showMessageDialog(c, credit.get(6).toString());//Debit Account Number
//              JOptionPane.showMessageDialog(c, credit.get(7).toString());//Debit Account Name
//                  JOptionPane.showMessageDialog(c, credit.get(8).toString());//Code 002
//          JOptionPane.showMessageDialog(c, credit.get(9).toString());//Batch Number
//            JOptionPane.showMessageDialog(c, credit.get(10).toString());//Loan payament Type LOANR
//              JOptionPane.showMessageDialog(c, credit.get(11).toString());//Code 22

//postedDetails.add(theStartDateObject);
//
//
//postedDetails.add(loanOfficer);
//
//if(!jTextField25.getText().equalsIgnoreCase("")){
//
//thePeriodSet=parseInt(jTextField25.getText());
//}
//postedDetails.add(thePeriodSet);//thePeriodSet  18
////JOptionPane.showMessageDialog(this, "compuM"+compuM);
//postedDetails.add(compuM);//thePeriodSet  18
//   
//            JOptionPane.showMessageDialog(c, "0" +theDetails.get(0).toString());//Batch Code batch 0
//          JOptionPane.showMessageDialog(c, "1" +theDetails.get(1).toString());//Transanction Code  tnxId 1
//            JOptionPane.showMessageDialog(c, "2" +theDetails.get(2).toString());//Txn Type    txnType      2
//              JOptionPane.showMessageDialog(c, "3" +theDetails.get(3).toString());//Txn Code   txnCode  3
//              
//                   JOptionPane.showMessageDialog(c, "4" +theDetails.get(4).toString());//DrAccountNumber  4
//          JOptionPane.showMessageDialog(c, "5" +theDetails.get(5).toString());//CrAccountNumber   5
//            JOptionPane.showMessageDialog(c, "6" +theDetails.get(6).toString());//Narration  6
//              JOptionPane.showMessageDialog(c, "7" +theDetails.get(7).toString());   //amount  7
//              
//              
//                   JOptionPane.showMessageDialog(c, "8" +theDetails.get(8).toString());//interestRate  8
//          JOptionPane.showMessageDialog(c, "9" +theDetails.get(9).toString());//tenure    9
//            JOptionPane.showMessageDialog(c, "10" +theDetails.get(10).toString());//DateFetched   10
//              JOptionPane.showMessageDialog(c, "11" +theDetails.get(11).toString());//periodType   11
//    
//       JOptionPane.showMessageDialog(c, "12" +theDetails.get(12).toString());//periodSubType  12
//          JOptionPane.showMessageDialog(c, "13" +theDetails.get(13).toString());//userId    13
//            JOptionPane.showMessageDialog(c, "14" +theDetails.get(14).toString());//interest Regime   14
//              JOptionPane.showMessageDialog(c, "15" +theDetails.get(15).toString());//periodSubType  15
//              
//                     JOptionPane.showMessageDialog(c, "16" +theDetails.get(16).toString());//periodSubType  16
//          JOptionPane.showMessageDialog(c, "17" +theDetails.get(17).toString());//loanOfficer    17
//            JOptionPane.showMessageDialog(c, "18" +theDetails.get(18).toString());//thePeriodSet  18
//              JOptionPane.showMessageDialog(c, "19" +theDetails.get(19).toString());//compute Method   19
//              
       
List theLoanFeed=loan.repayTheLoan(theDetails,c);
   
//           
//        
//newPaymentCame.add(MonthYearPaid.getMonth().toString());//MonthPaidFor
//newPaymentCame.add(MonthYearPaid.getYear());//YearPaidFor
//newPaymentCame.add(trnIdInterest.get(0));//loanTrnId
//newPaymentCame.add("newloan"+accountNumberNow);//LoanId
//newPaymentCame.add(debit.get(9).toString());//BatchCode
//newPaymentCame.add("0.0");//AmountDisbursed
//newPaymentCame.add("0.0");//Expected Interest
//newPaymentCame.add("0.0");//Expected Total Amount
//newPaymentCame.add(trnIdInterest.get(1));//Interest Rate
//newPaymentCame.add(amountPaid);//Amount Paid
//newPaymentCame.add("0.0");//PrincipalPaid
//newPaymentCame.add("0.0");//Interest Paid
//newPaymentCame.add("0.0");//AccumulatedInterestPaid
//newPaymentCame.add("0.0");//LoanPenaltyPaid
//
//  SwingWorker<Void,Void> createTheInitailLoanPayState=new SwingWorker(){
//                            @Override
//                            protected Object doInBackground() throws Exception {   
//                        dbq.createTheInitailLoanPayState(newPaymentCame,c); 
//                                return null;
//                            }  };
//                     createTheInitailLoanPayState.execute();
//
//        switch(completeStatus){//This informs whether the instalment payment is being completed or if its in the middle
//
//        case "Not Complete":
////JOptionPane.showMessageDialog(c, "In now");
//        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
//            
//        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
//        List newOrder=new ArrayList(processOrder);
//
//        for(Object order:newOrder){//Follow the processing order as it was choosen
//
//        switch(order.toString()){
//        case "Interest":// if the item in the queue is interest
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){
//
//
//
//        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending
//
//        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentAccruedInterest=amountPaid;
//
//        }
//        //               
//        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
//        //                        return null;
//        //                    }  };
//        //             interestPay.execute();
//
//        loan.updateNewAccruedInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);
//        
//        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
//        
//        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//
//
//
//        }else{
//        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending
//
//        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentInterestNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
//        //                        return null;
//        //                    }  };
//        //             interestPay.execute();
//
////JOptionPane.showMessageDialog(c, currentInterestNow);
//        loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow,c);
//        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
//        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated
//
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//        }
//        break;
//
//        case "Accumulated Interest":
//
//        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentAccumulatedInterestNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
//        //                        return null;
//        //                    }  };
//        //             accumulatedInterestPay.execute();
//
//        loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
//        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//        break;
//        case "Loan Penalty":
//
//        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentLoanPenaltyNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
//        //                        return null;
//        //                    }  };
//        //             loanPenaltyPay.execute();
//        loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);     
//        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//        break;
//        case "Principal":
//
//
//        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentPrincipalNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
//        //                        return null;
//        //                    }  };
//        //             principalPayLoan.execute();
//        loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow,c); 
//        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//
//        break;
//        }
//
//        }
//        }
//        break;
//        case "Complete":
//
//        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
//        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
//        List newOrder=new ArrayList(processOrder); int y=0;
//
//        while(y<newOrder.size()){//Follow the processing order as it was choosen
//
//        switch(newOrder.get(y).toString()){
//        case "Interest":// if the item in the queue is interest
//            
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){
//
//
//
//        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending
//
//        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentAccruedInterest=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
//        //                        return null;
//        //                    }  };
//        //             interestPay.execute();
//        loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);  
//        
//        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
//        
//        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//
//
//
//        }else{
//        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending
//
//        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentInterestNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
//        //                        return null;
//        //                    }  };
//        //             interestPay.execute();
//        loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow,c);  
//        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
//        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//        }
//        break;
//
//        case "Accumulated Interest":
//
//        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentAccumulatedInterestNow=amountPaid;
//
//        }
//        //               
//        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
//        //                        return null;
//        //                    }  };
//        //             accumulatedInterestPay.execute();
//        loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
//        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//        break;
//        case "Loan Penalty":
//
//        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentLoanPenaltyNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
//        //                        return null;
//        //                    }  };
//        //             loanPenaltyPay.execute();
//        loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);  
//        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//        break;
//        case "Principal":
//
//        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   
//
//        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process
//
//        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest
//
//        currentPrincipalNow=amountPaid;
//
//        }
//
//        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
//        //                    @Override
//        //                    protected Object doInBackground() throws Exception {   
//        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
//        //                        return null;
//        //                    }  };
//        //             principalPayLoan.execute();
//        loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow,c);  
//        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
//        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated
//
//        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
//        break;
//        } 
//
//        }
//
//
//
//        break;
//        }
//        y++;
//        }
        //    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),currentPrincipalNow+"");  
//JOptionPane.showMessageDialog(c, currentPrincipalNow);
//        }
//   if(totalInterestPaid>0.0){
//   totalInterestPaid+=amountPaid;
//   amountPaid=0.0;
//   }else  if (totalAccruedInterestPaid>0.0){
//      totalAccruedInterestPaid+=amountPaid;
//   amountPaid=0.0;
//   }else  if (totalPrincipalPaid>0){
//      totalPrincipalPaid+=amountPaid;
//   amountPaid=0.0;
//   }else  if (totalAccumulatedInterestPaid>0){
//    totalAccumulatedInterestPaid+=amountPaid;
//   amountPaid=0.0;
//   }else  if(totalLoanPenaltyPaid>0){
//      totalLoanPenaltyPaid+=amountPaid;
//   amountPaid=0.0;
//   } 
//
//        String newLoanId=   loan.closeLoanAccount(accountNumberNow); 
//
//        List closedDetails=new ArrayList();
//        closedDetails.add(newLoanId);//New loan Id:0
//        closedDetails.add("Completed");//Loan cycle status:1
//        closedDetails.add(accountNumberNow);//Borrower Account Number:1
//        loan.updateClosedLoan(closedDetails);
//        dbq.updateClosedStatement(closedDetails);
////        JOptionPane.showMessageDialog(c, "closed");
//        
//        break;

//        }
//        JOptionPane.showMessageDialog(c, theDetails.get(4).toString());
//        String borrowerAccount=fmt.formatAccountWithSeperators(theDetails.get(4).toString()); 
           String borrowerAccount=theDetails.get(4).toString(); 
       if(debit.get(10).toString().equalsIgnoreCase("LoanR")){
        
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.debitCashFirst(borrowerAccount, debit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"Account Deposit for Loan Payment");

        }
        }
       
        if(debit.get(10).toString().equalsIgnoreCase("LoanRD")){
        
         dbq.reduceSavings(debit);

        }
       
       
//       JOptionPane.showMessageDialog(c, theLoanFeed.get(0));
//        JOptionPane.showMessageDialog(c, theLoanFeed.get(1));
//        JOptionPane.showMessageDialog(c, theLoanFeed.get(2));
//         JOptionPane.showMessageDialog(c, theLoanFeed.get(3));
//          JOptionPane.showMessageDialog(c, theLoanFeed.get(4));
        successful1= dbq.debit(debit,c);

        if(successful1){

        if(parseDouble(theLoanFeed.get(2).toString())>0.0){ //ìnterest

//           fios.stringFileWriter(fios.createFileName("test", "testit", "totalInterestPaid"+"trdyr.txt"),totalInterestPaid+"");  

//
//        SwingWorker<Void,Void> payTotalInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   

        processInterestPayment(parseDouble(theLoanFeed.get(2).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   

//        return null;
//        }  };
//        payTotalInterestPaid.execute();
        successful2=true;
        }

//        if(totalAccruedInterestPaid>0.0){
//
////
////        SwingWorker<Void,Void> payTotalAccruedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedInterestPayment(totalAccruedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));  
////        return null;
////        }  };
////        payTotalAccruedInterestPaid.execute();
//        successful2=true;
//        }




//
        if(parseDouble(theLoanFeed.get(3).toString())>0.0){
//
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
//
//
//
////
////        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedAccumulatedInterestPayment(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
////        return null;
////        }  };
////        payTotalAccumulatedInterestPaid.execute();
//        successful2=true;
//
//        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis
//
//
////        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
        processAccumulatedInterestPayment(parseDouble(theLoanFeed.get(3).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   
////        return null;
////        }  };
////        payTotalAccumulatedInterestPaid.execute();
//        successful2=true;
//        }
//
        }

        if(parseDouble(theLoanFeed.get(4).toString())>0){ //Penalty


//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
////
////        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedLoanPenaltyPayment(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
////        return null;
////        }  };
////        payTotalLoanPenaltyPaid.execute();
//
//        successful2=true;
//        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {  

 
        processLoanPenaltyPayment(parseDouble(theLoanFeed.get(4).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));

//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();
        successful2=true;
//        }

        }

        if(parseDouble(theLoanFeed.get(1).toString())>0.0){// Principal
// fios.stringFileWriter(fios.createFileName("test", "testit", "totalPrincipalPaid"+"trdyr.txt"),totalInterestPaid+";"+credit.get(5).toString());  
//        SwingWorker<Void,Void> payTotalPrincipalPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
if(theLoanFeed.get(5).toString().equalsIgnoreCase("Renewed")){

            processPrincipalPaymentRenewed(parseDouble(theLoanFeed.get(1).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));
}  else{
        processPrincipalPayment(parseDouble(theLoanFeed.get(1).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));    
}
//        processPrincipalPayment(parseDouble(theLoanFeed.get(1).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalPrincipalPaid.execute();   

        successful2=true;


        }



        }

        if(successful1==successful2){


//
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){//Accumulated Interest used
//
//
//        }

//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis
//
//        }



        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        SwingWorker<Void,Void> loanSms= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        sendsms.createLoanPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        return null;
        }

        };
        loanSms.execute();



        } 

        SwingWorker<Void,Void> loanSaving= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLaonSavingsSharesForLoanAmount(fmt.formatAccountWithSeperators(debit.get(0).toString()),debit.get(5).toString());
       
        return null;
        }

        };
        loanSaving.execute();
        successful=true;
        }

//        SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(debit,c);
//       
//        return null;
//        }
//
//        };
//        
//        updateLoanBalances.execute();
        



        return successful;
        }

        
//        public synchronized void updateLoanBalancesWrittenOff(List debit){
//          
//    try {
//                      wait(2000);
//                  } catch (InterruptedException ex) {
//                      Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//                  }
//            
//            
//           dbq.updateDetailsLoanStaR(debit.get(9).toString(),loan.getThoseDetails(fmt.formatAccountWithSeperators(debit.get(0).toString())));
//            
//            
//            
//            
//        
//        }
//           public synchronized void updateLoanBalances(List debit,Component c){
//          
////    try {
////                      wait(2000);
////                  } catch (InterruptedException ex) {
////                      Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
////                  }
//            
//            
//           dbq.updateDetailsLoanStaR(debit.get(9).toString(),loan.getThoseDetails(fmt.formatAccountWithSeperators(debit.get(0).toString())),c);
//            
//            
//            
//            
//        
//        }
        
        public void processDepreciationF(String assetAccount,double amount,Component c){
        
        
         if(amount>0.0){
        
        String completionStatus="Not Complete";
//         JOptionPane.showMessageDialog(this, txnCode);
//    JOptionPane.showMessageDialog(this, dbq.getBankUsingId(tnxId));
  
   double instalmentsRemaining= dbq.depriAmountRemaining(assetAccount);
   
//   JOptionPane.showMessageDialog(c, "instalmentsRemaining"+instalmentsRemaining);
   
 String  DrAccountNumber= dbq.provisionForDepreciationExpenseAccount();
 
 String CrAccountNumber =dbq.currentAccumulatedDepLedger(assetAccount);
 
 String Narration= dbq.AccountName(assetAccount)+"'s "+"Accumulated Depreciation";
 
 String DateFetched=sdf.format(new java.util.Date(System.currentTimeMillis()));
 
 String batch=this.batchCode();
 
   if(amount>=instalmentsRemaining){
   amount=instalmentsRemaining;
   completionStatus="Complete";
   }
  List  debit=new ArrayList();   

    debit .add(0, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Debit Account Number:0
    debit .add(1, DateFetched);//Transaction Date:1
    debit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new java.util.Date(System.currentTimeMillis())));//Narrative 1:2
    debit .add(3, "From"+" "+dbq.AccountName(DrAccountNumber));//Narrative 2:3
    debit .add(4, DateFetched);//Value Date:4
    debit .add(5, amount);//Debit Amount:5
    debit .add(6, fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Credit Account Number:6  
    debit .add(7, dbq.AccountName(CrAccountNumber));//Credit Account Name:7 
    debit .add(8, "0002");//Debit Reference Number:8
    debit .add(9, batch);//Batch Number:9
    debit .add(10, "Gen");//Transaction Type:10
    debit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

  List  credit=new ArrayList();    

    credit .add(0,  fmt.putSeparatorsOnNormaAccount(CrAccountNumber));//Debit Account Number:0
    credit .add(1, DateFetched);//Transaction Date:1
    credit .add(2, Narration+" "+"Processed on"+" "+sdf.format(new java.util.Date(System.currentTimeMillis())));//Narrative 1:2
    credit .add(3, "On"+" "+dbq.AccountName(CrAccountNumber));//Narrative 2:3
    credit .add(4, DateFetched);//Value Date:4
    credit .add(5, amount);//Debit Amount:5
    credit .add(6, fmt.putSeparatorsOnNormaAccount(DrAccountNumber));//Credit Account Number:6  
    credit .add(7, dbq.AccountName(DrAccountNumber));//Credit Account Name:7 
    credit .add(8, "0002");//Debit Reference Number:8
    credit .add(9, batch);//Batch Number:9
    credit .add(10, "Gen");//Transaction Type:10
    credit .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 

    accumDepreciation(debit, credit,assetAccount,completionStatus,c);
        
        }
        
        }
        
          public synchronized boolean accumDepreciation(List debit ,List  credit,String assetAccount,String completeStatus,Component c){
            
          double  
                  currentDepreInstalNow = 0,
                  totalAccumulatedDepre= 0;
                  
          int currentDepreScheduleId= 0,theAssetId=0;
             
          double amountPaid=0.0;
          
        String accountNumberNow=assetAccount;//Account Number of the loan being paid.

        boolean successful=false,
                successful1=false,
                successful2=false;//Status of loan payment finally

        amountPaid=parseDouble(debit.get(5).toString());//The accumulated depreciation amount being paid.
//JOptionPane.showMessageDialog(c, amountPaid+";"+accountNumberNow);
        switch(completeStatus){//This informs whether the instalment payment is being completed or if its in the middle

        case "Not Complete":

        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
          
            theAssetId  =dbq.currentDepreciStoreId(accountNumberNow);
            
        currentDepreScheduleId=dbq.currentDepreciScheduleId(theAssetId);//The loan instalment id that is next in the queue


        currentDepreInstalNow=dbq.currentDepreciationInstalmentNow(currentDepreScheduleId);//Pick the loan interest instalment from the schedule that is pending
//JOptionPane.showMessageDialog(c, theAssetId+";"+currentDepreScheduleId+";"+currentDepreInstalNow);
        if(currentDepreInstalNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentDepreInstalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentDepreInstalNow=amountPaid;

        }
      

        dbq.updateNewDepreciatioScheduleNow(accountNumberNow,theAssetId,currentDepreScheduleId,currentDepreInstalNow,c);
        
        totalAccumulatedDepre+=currentDepreInstalNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentDepreInstalNow;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }
        break;
        

        case "Complete":

            while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
          theAssetId  =dbq.currentDepreciStoreId(accountNumberNow);
        currentDepreScheduleId=dbq.currentDepreciScheduleId(theAssetId);//The loan instalment id that is next in the queue


        currentDepreInstalNow=dbq.currentDepreciationInstalmentNow(currentDepreScheduleId);//Pick the loan interest instalment from the schedule that is pending

        if(currentDepreInstalNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentDepreInstalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentDepreInstalNow=amountPaid;

        }
      

        dbq.updateNewDepreciatioScheduleNow(accountNumberNow,theAssetId,currentDepreScheduleId,currentDepreInstalNow,c);
        
        totalAccumulatedDepre+=currentDepreInstalNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentDepreInstalNow;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }

        dbq.updateEndedDepreciation(theAssetId,"Ended");

        break;

        }
        
       
        successful= dbq.debit(debit,c);

        if(successful){
successful1=dbq.credit(credit, c);
      }
        
          
      return successful1;    
          }    
//        ,,
          
          
          
public synchronized boolean loanRepaymentsNewBank(List theDetails,List debit,List credit,List processOrder,List otherOrders){
    
//     String summuryReportText="";
//       int counter=1;
//      
//    String completeStatus=otherOrders.get(0).toString();
//    
      String trxId  =otherOrders.get(1).toString();  
//      
//       YearMonth MonthYearPaid =(YearMonth)otherOrders.get(2); 
//       
//       String closingNotes=otherOrders.get(3).toString();   
//       
//          double currentAccruedInterest= 0, currentInterestNow = 0,currentAccumulatedInterestNow= 0,currentLoanPenaltyNow= 0,currentPrincipalNow= 0;int currentInstalmentId= 0;
//          
//          double totalInterestPaid=0.0,totalAccumulatedInterestPaid=0.0,totalLoanPenaltyPaid=0.0,totalPrincipalPaid=0.0,totalAccruedInterestPaid=0.0;//Total interest,accumulated interest,loan penalty or principal to be posted in the general ledger after update of the loan schedule   
//         
//             double amountPaid=0.0;
//          
//        String accountNumberNow=fmt.formatAccountWithSeperators(debit.get(0).toString());//Account Number of the loan being paid.
//       
//        List trnIdInterest=loan.loanTrnIdInterestRate(accountNumberNow);
//        
        boolean successful=false,successful1=false,successful2=false;//Status of loan payment finally
//
//        amountPaid=parseDouble(debit.get(5).toString());//The loan amount being paid, catering for Interest,principal,penalty and accumulated interest
//
//           List newPaymentCame=new ArrayList();
//           
//             if(!processOrder.isEmpty()){
//    
//            for(Object txt:processOrder){
//              
//                if(counter==1){
//                summuryReportText=txt.toString();
//                }else{
//                  summuryReportText=summuryReportText.concat(";"); 
//                   summuryReportText=summuryReportText.concat(txt.toString());
//                }
//                
//           
//           
//             counter++;
//            }
//    
//            }
//    

       
List theLoanFeed=loan.repayTheLoan(theDetails,c);
   

  String borrowerAccount=fmt.formatAccountWithSeperators(debit.get(0).toString()); 
        
       if(debit.get(10).toString().equalsIgnoreCase("LoanR")){
        
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.debitBankFirst(borrowerAccount, debit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"Savings for Loan Payment",trxId);

        }
        }
       
        if(debit.get(10).toString().equalsIgnoreCase("LoanRD")){
        
         dbq.reduceSavings(debit);

        }
//       
       
//       JOptionPane.showMessageDialog(c, theLoanFeed.get(0));
//        JOptionPane.showMessageDialog(c, theLoanFeed.get(1));
//        JOptionPane.showMessageDialog(c, theLoanFeed.get(2));
//         JOptionPane.showMessageDialog(c, theLoanFeed.get(3));
//          JOptionPane.showMessageDialog(c, theLoanFeed.get(4));
        successful1= dbq.debit(debit,c);

        if(successful1){

        if(parseDouble(theLoanFeed.get(2).toString())>0.0){ //ìnterest

//           fios.stringFileWriter(fios.createFileName("test", "testit", "totalInterestPaid"+"trdyr.txt"),totalInterestPaid+"");  

//
//        SwingWorker<Void,Void> payTotalInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   

        processInterestPayment(parseDouble(theLoanFeed.get(2).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   

//        return null;
//        }  };
//        payTotalInterestPaid.execute();
        successful2=true;
        }

//        if(totalAccruedInterestPaid>0.0){
//
////
////        SwingWorker<Void,Void> payTotalAccruedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedInterestPayment(totalAccruedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));  
////        return null;
////        }  };
////        payTotalAccruedInterestPaid.execute();
//        successful2=true;
//        }




//
        if(parseDouble(theLoanFeed.get(3).toString())>0.0){
//
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
//
//
//
////
////        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedAccumulatedInterestPayment(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
////        return null;
////        }  };
////        payTotalAccumulatedInterestPaid.execute();
//        successful2=true;
//
//        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis
//
//
////        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
        processAccumulatedInterestPayment(parseDouble(theLoanFeed.get(3).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   
////        return null;
////        }  };
////        payTotalAccumulatedInterestPaid.execute();
//        successful2=true;
//        }
//
        }

        if(parseDouble(theLoanFeed.get(4).toString())>0){ //Penalty


//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
////
////        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
////        @Override
////        protected Object doInBackground() throws Exception {   
//        processAccruedLoanPenaltyPayment(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
////        return null;
////        }  };
////        payTotalLoanPenaltyPaid.execute();
//
//        successful2=true;
//        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processLoanPenaltyPayment(parseDouble(theLoanFeed.get(4).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));
//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();
        successful2=true;
//        }

        }

        if(parseDouble(theLoanFeed.get(1).toString())>0.0){// Principal
// fios.stringFileWriter(fios.createFileName("test", "testit", "totalPrincipalPaid"+"trdyr.txt"),totalInterestPaid+";"+credit.get(5).toString());  
//        SwingWorker<Void,Void> payTotalPrincipalPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processPrincipalPayment(parseDouble(theLoanFeed.get(1).toString()),credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalPrincipalPaid.execute();   

        successful2=true;


        }



        }

        if(successful1==successful2){


//
//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){//Accumulated Interest used
//
//
//        }

//        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis
//
//        }



        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        SwingWorker<Void,Void> loanSms= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        sendsms.createLoanPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        return null;
        }

        };
        loanSms.execute();



        } 

        SwingWorker<Void,Void> loanSaving= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLaonSavingsSharesForLoanAmount(fmt.formatAccountWithSeperators(debit.get(0).toString()),debit.get(5).toString());
       
        return null;
        }

        };
        loanSaving.execute();
        successful=true;
        }

//        SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(debit,c);
//       
//        return null;
//        }
//
//        };
//        
//        updateLoanBalances.execute();
        



        return successful;
        }




public synchronized boolean loanRepaymentsNewBankWrittenOff(List debit ,List  credit,List processOrder,List otherOrders){
    
    String completeStatus=otherOrders.get(0).toString();
    
      String trxId  =otherOrders.get(1).toString();  
      
       YearMonth MonthYearPaid =(YearMonth)otherOrders.get(2); 
       
       String closingNotes=otherOrders.get(3).toString();
       
       
          double currentAccruedInterest= 0, currentInterestNow = 0,currentAccumulatedInterestNow= 0,currentLoanPenaltyNow= 0,currentPrincipalNow= 0;int currentInstalmentId= 0;
             double totalInterestPaid=0.0,totalAccumulatedInterestPaid=0.0,totalLoanPenaltyPaid=0.0,totalPrincipalPaid=0.0,totalAccruedInterestPaid=0.0;//Total interest,accumulated interest,loan penalty or principal to be posted in the general ledger after update of the loan schedule   
          double amountPaid=0.0;
        String accountNumberNow=fmt.formatAccountWithSeperators(debit.get(0).toString());//Account Number of the loan being paid.
         List trnIdInterest=loan.loanTrnIdInterestRateWrittenOff(accountNumberNow);
        boolean successful=false,successful1=false,successful2=false;//Status of loan payment finally

        amountPaid=parseDouble(debit.get(5).toString());//The loan amount being paid, catering for Interest,principal,penalty and accumulated interest


 List newPaymentCame=new ArrayList();
        
newPaymentCame.add(MonthYearPaid.getMonth().toString());//MonthPaidFor
newPaymentCame.add(MonthYearPaid.getYear());//YearPaidFor
newPaymentCame.add(trnIdInterest.get(0));//loanTrnId
newPaymentCame.add("writtenOffloan"+accountNumberNow);//LoanId
newPaymentCame.add(debit.get(9).toString());//BatchCode
newPaymentCame.add("0.0");//AmountDisbursed
newPaymentCame.add("0.0");//Expected Interest
newPaymentCame.add("0.0");//Expected Total Amount
newPaymentCame.add(trnIdInterest.get(1));//Interest Rate
newPaymentCame.add(amountPaid);//Amount Paid
newPaymentCame.add("0.0");//PrincipalPaid
newPaymentCame.add("0.0");//Interest Paid
newPaymentCame.add("0.0");//AccumulatedInterestPaid
newPaymentCame.add("0.0");//LoanPenaltyPaid

                 
                        SwingWorker<Void,Void> createTheInitailLoanPayState=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        dbq.createTheInitailLoanPayStateWrittenOff(newPaymentCame,c); 
                                return null;
                            }  };
                     createTheInitailLoanPayState.execute();





        switch(completeStatus){//This informs whether the instalment payment is being completed or if its in the middle

        case "Not Complete":

        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
            
        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
        List newOrder=new ArrayList(processOrder);

        for(Object order:newOrder){//Follow the processing order as it was choosen

        switch(order.toString()){
        case "Interest":// if the item in the queue is interest
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){



        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccruedInterest=amountPaid;

        }
        //               
        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();

        loan.updateNewAccruedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);
        
        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }





        }else{
        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();


        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentInterestNow,c);
        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated


        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }
        break;

        case "Accumulated Interest":

        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccumulatedInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
        //                        return null;
        //                    }  };
        //             accumulatedInterestPay.execute();

        loan.updateNewAccumulatedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }


        break;
        case "Loan Penalty":

        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentLoanPenaltyNow=amountPaid;

        }

        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
        //                        return null;
        //                    }  };
        //             loanPenaltyPay.execute();
        loan.updateNewLoanPenaltyNowWrittenOff(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);     
        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }

        break;
        case "Principal":


        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentPrincipalNow=amountPaid;

        }

        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
        //                        return null;
        //                    }  };
        //             principalPayLoan.execute();
        loan.updateNewLoanPrincipalNowWrittenOff(accountNumberNow,currentInstalmentId,currentPrincipalNow,c); 
        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }



        break;
        }

        }
        }
        break;
        case "Complete":

        while(amountPaid>=1){//The loan amount keeps on reducing and as long as its still available, we process.
        currentInstalmentId=loan.currentLoanInstalmentNow(accountNumberNow);//The loan instalment id that is next in the queue
        List newOrder=new ArrayList(processOrder); int y=0;

        while(y<newOrder.size()){//Follow the processing order as it was choosen

        switch(newOrder.get(y).toString()){
        case "Interest":// if the item in the queue is interest
            
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){



        currentAccruedInterest=loan.currentAccruedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentAccruedInterest>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccruedInterest){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccruedInterest=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentAccruedInterest);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();
        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccruedInterest,c);  
        
        totalAccruedInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        
        amountPaid-=currentAccruedInterest;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }





        }else{
        currentInterestNow=loan.currentInterestRemainNow(accountNumberNow,currentInstalmentId);//Pick the loan interest instalment from the schedule that is pending

        if(currentInterestNow>0){// if the interest instalment is postive and greater than zero, process

        if(amountPaid<=currentInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentInterestNow=amountPaid;

        }

        //                SwingWorker<Void,Void> interestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewInterestNow(accountNumberNow,currentInstalmentId,currentInterestNow);  
        //                        return null;
        //                    }  };
        //             interestPay.execute();
        loan.updateNewInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentInterestNow,c);  
        totalInterestPaid+=currentInterestNow;//Pick the interest updated in the schedule for posting in the general ledger
        amountPaid-=currentInterestNow;//reduce the amount being paid by the interest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }
        }
        break;

        case "Accumulated Interest":

        currentAccumulatedInterestNow=loan.currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentAccumulatedInterestNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentAccumulatedInterestNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentAccumulatedInterestNow=amountPaid;

        }
        //               
        //                SwingWorker<Void,Void> accumulatedInterestPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewAccumulatedInterestNow(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow);  
        //                        return null;
        //                    }  };
        //             accumulatedInterestPay.execute();
        loan.updateNewAccumulatedInterestNowWrittenOff(accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
        totalAccumulatedInterestPaid+=currentAccumulatedInterestNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentAccumulatedInterestNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }


        break;
        case "Loan Penalty":

        currentLoanPenaltyNow=loan.currentLoanPenaltytNow(accountNumberNow,currentInstalmentId,c);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentLoanPenaltyNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentLoanPenaltyNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentLoanPenaltyNow=amountPaid;

        }

        //                SwingWorker<Void,Void> loanPenaltyPay=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPenaltyNow(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow);  
        //                        return null;
        //                    }  };
        //             loanPenaltyPay.execute();
        loan.updateNewLoanPenaltyNowWrittenOff(accountNumberNow,currentInstalmentId,currentLoanPenaltyNow,c);  
        totalLoanPenaltyPaid+=currentLoanPenaltyNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentLoanPenaltyNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }

        break;
        case "Principal":

        currentPrincipalNow=loan.currentLoanPrincipalNow(accountNumberNow,currentInstalmentId);//Pick the loan accumulatedInterest instalment from the schedule that is pending   

        if(currentPrincipalNow>0){// if the accumulatedInterest instalment is postive and greater than zero, process

        if(amountPaid<=currentPrincipalNow){//if the amount being paid is less than the existing interest, then the update is on the amount being paid otherwise its on the whole interest

        currentPrincipalNow=amountPaid;

        }

        //                SwingWorker<Void,Void> principalPayLoan=new SwingWorker(){
        //                    @Override
        //                    protected Object doInBackground() throws Exception {   
        //                 loan.updateNewLoanPrincipalNow(accountNumberNow,currentInstalmentId,currentPrincipalNow);  
        //                        return null;
        //                    }  };
        //             principalPayLoan.execute();
        loan.updateNewLoanPrincipalNowWrittenOff(accountNumberNow,currentInstalmentId,currentPrincipalNow,c);  
        totalPrincipalPaid+=currentPrincipalNow;//Pick the accumulatedInterest updated in the schedule for posting in the general ledger
        amountPaid-=currentPrincipalNow;//reduce the amount being paid by the accumulatedInterest updated

        if(amountPaid<1){//if the amount being paid has reduced, to less than zero, get out of the loop
        break;
        } 

        }



        break;
        }
        y++;
        }
        //    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),currentPrincipalNow+"");  

        }
   if(totalInterestPaid>0.0){
   totalInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalAccruedInterestPaid>0.0){
      totalAccruedInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalPrincipalPaid>0){
      totalPrincipalPaid+=amountPaid;
   amountPaid=0.0;
   }else  if (totalAccumulatedInterestPaid>0){
    totalAccumulatedInterestPaid+=amountPaid;
   amountPaid=0.0;
   }else  if(totalLoanPenaltyPaid>0){
      totalLoanPenaltyPaid+=amountPaid;
   amountPaid=0.0;
   } 

         String newLoanId=   loan.closeLoanAccount(accountNumberNow); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumberNow);//Borrower Account Number:1
        loan.updateClosedLoanWrittenOff(closedDetails);
        dbq.updateClosedStatementWrittenOff(closedDetails);

        break;

        }
        
        String borrowerAccount=fmt.formatAccountWithSeperators(debit.get(0).toString()); 
//        
//       if(debit.get(10).toString().equalsIgnoreCase("LoanR")){
//        
//        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.debitBankFirst(borrowerAccount, debit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"Savings for Loan Payment",trxId);

//        }
//        }
//       
//        if(debit.get(10).toString().equalsIgnoreCase("LoanRD")){
//        
//         dbq.reduceSavings(debit);
//
//        }
//       
       
       
       
       
        successful1= dbq.debit(debit,c);

        if(successful1){

        if(totalInterestPaid>0.0){

//           fios.stringFileWriter(fios.createFileName("test", "testit", "totalInterestPaid"+"trdyr.txt"),totalInterestPaid+"");  

//
//        SwingWorker<Void,Void> payTotalInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   

        processInterestPaymentWrittenOff(totalInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   

//        return null;
//        }  };
//        payTotalInterestPaid.execute();
        successful2=true;
        }

        if(totalAccruedInterestPaid>0.0){

//
//        SwingWorker<Void,Void> payTotalAccruedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedInterestPaymentWrittenOff(totalAccruedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));  
//        return null;
//        }  };
//        payTotalAccruedInterestPaid.execute();
        successful2=true;
        }





        if(totalAccumulatedInterestPaid>0){

        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis



//
//        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedAccumulatedInterestPaymentWrittenOff(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalAccumulatedInterestPaid.execute();
        successful2=true;

        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis


//        SwingWorker<Void,Void> payTotalAccumulatedInterestPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccumulatedInterestPaymentWrittenOff(totalAccumulatedInterestPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));   
//        return null;
//        }  };
//        payTotalAccumulatedInterestPaid.execute();
        successful2=true;
        }

        }

        if(totalLoanPenaltyPaid>0){


        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){//Using accrual accounting basis
//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processAccruedLoanPenaltyPaymentWrittenOff(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();

        successful2=true;
        }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

//
//        SwingWorker<Void,Void> payTotalLoanPenaltyPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processLoanPenaltyPaymentWrittenOff(totalLoanPenaltyPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString()));
//        return null;
//        }  };
//        payTotalLoanPenaltyPaid.execute();
        successful2=true;
        }

        }

        if(totalPrincipalPaid>0){
// fios.stringFileWriter(fios.createFileName("test", "testit", "totalPrincipalPaid"+"trdyr.txt"),totalInterestPaid+";"+credit.get(5).toString());  
//        SwingWorker<Void,Void> payTotalPrincipalPaid=new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {   
        processPrincipalPaymentWrittenOff(totalPrincipalPaid,credit,fmt.formatAccountWithSeperators(debit.get(0).toString())); 
//        return null;
//        }  };
//        payTotalPrincipalPaid.execute();   

        successful2=true;


        }



        }

        if(successful1==successful2){



        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useAccumulatedInterest.txt"))==1){//Accumulated Interest used


        }

        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){//Use cash accounting basis

        }



        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        SwingWorker<Void,Void> loanSms= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        sendsms.createLoanPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        return null;
        }

        };
        loanSms.execute();



        } 

        SwingWorker<Void,Void> loanSaving= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLaonSavingsSharesForLoanAmount(fmt.formatAccountWithSeperators(debit.get(0).toString()),debit.get(5).toString());
       
        return null;
        }

        };
        loanSaving.execute();
        successful=true;
        }

//  SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(debit,c);
//       
//        return null;
//        }
//
//        };
//        updateLoanBalances.execute();


        return successful;
        }









//        public boolean loanRepayments(List debit ,List  credit,Component c){
//
//        boolean confirm=false, confirm1=false, confirm2=false,confirmz=false,confirmx=false,confrimBr=false,confrimBr1=false,confrimBr2=false;
//
//        String borrowerAccount=fmt.formatAccountWithSeperators(debit.get(0).toString()); 
//        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){
//
//        this.debitCashFirst(borrowerAccount, debit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"Savings for Loan Payment");
//
//        }
//
//
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "principalPending"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "excessIdentifyer"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "theyDid"+borrowerAccount+".txt"));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "treatOnceFirst"+borrowerAccount));
//
//        confirm1= dbq.debit(debit,c);
//        if(confirm1){
//        confirm2= dbq.credit(credit,c);
//        }
//
//        if(confirm1==confirm2){
//        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
//        SwingWorker<Void,Void> loanSms= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        sendsms.createLoanPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString());
//        return null;
//        }
//
//        };
//        loanSms.execute();
//
//
//
//        } 
//
//        SwingWorker<Void,Void> loanSaving= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        dbq.updateLaonSavingsSharesForLoanAmount(fmt.formatAccountWithSeperators(debit.get(0).toString()),debit.get(5).toString());
//        return null;
//        }
//
//        };
//        loanSaving.execute();
//
//        confirmz=updateLoanReapayment(debit, credit);
//
//        if(loan.testTable("newloan"+borrowerAccount)){
//
//        SwingWorker<Void,Void> loanPort= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        loan.updateloanPortfolio(borrowerAccount);
//        return null;
//        }
//
//        };
//        loanPort.execute();
//        if(loan.accountIsPerforming(borrowerAccount)){
//
//
//
//        SwingWorker<Void,Void> loanPortloan= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//        loan.updateloanPortfolioperforming(borrowerAccount);
//        return null;
//        }
//
//        };
//        loanPortloan.execute();
//
//        }
//        
//        
//        
//        if(loan.accountIsAtRisk(borrowerAccount,c)){
//
//
//        SwingWorker<Void,Void> loanPortRisk= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//        loan.updateloanPortfolioAtRisk(borrowerAccount);
//        return null;
//        }
//
//        };
//        loanPortRisk.execute();
//
//
//        }
//
//
//        }
//        if(confirmz){
//
//        if(hasUsedGuarantors(borrowerAccount)){
//
//        if(guarantorsTiedToLoan(borrowerAccount)){
//
//
//        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "principalPending"+borrowerAccount))==45){   
//
//
//        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowedC"+borrowerAccount+".txt"))==35){   
//
//        if(gurantorStillAround(borrowerAccount)){
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));   
//        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));  
//        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));  
//
//        String overallPrincimpalAccumulated=fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        String princimlpalComingIn= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));
//        String guarantorsRemaining= fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
//        String guarantorsTotal= fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
//
//
//        double totalP=parseDouble(princimlpalComingIn)+parseDouble(overallPrincimpalAccumulated);
//
//
//        if(totalP<parseDouble(guarantorsTotal)){
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount), totalP+"");
//
//        }else{
//
//
//        if(!(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "treatOnceFirst"+borrowerAccount))==18)){
//
//
//
//        Double extra=totalP-parseDouble(guarantorsTotal);
//        //if(){      
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount),extra.toString()); 
//        Integer sd=26;
//        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "theyDid"+borrowerAccount+".txt"),sd.toString());
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount),princimlpalComingIn);
//
//
//
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount),guarantorsRemaining);
//
//
//
//
//        Integer zx=18;
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "treatOnceFirst"+borrowerAccount),zx.toString()); 
//        Integer zxj=19;
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "excessIdentifyer"+borrowerAccount),zxj.toString()); 
//
//        //}
//
//        }
//
//
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount), totalP+"");
//
//
//        } 
//        confirmx= returnSharesForGuarntors(borrowerAccount);
//
//        if(confirmz==confirmx==true){confirm=true;}
//
//        Integer s=3;
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "principalPending"+borrowerAccount),s.toString());
//
//        }else{
//
//        confrimBr= returnSharesForBorrower(borrowerAccount,debit);
//
//        if(confrimBr){
//        confirm=true;
//        }
//        }
//
//
//
//
//        }else{
//
//        confrimBr1= returnSharesForBorrower(borrowerAccount,debit);   
//        if(confrimBr1){
//        confirm=true;
//
//        } 
//        }
//
//
//        }else{confirm=true;}  }else{confirm=true;} 
//
//        }else{confirm=true;}
//
//
//
//        }
//
//
//        }
//
//        if(g1(borrowerAccount,debit.get(9).toString(),debit.get(10).toString()))  {
//
//        if(g2(borrowerAccount,debit.get(9).toString(),debit.get(10).toString())) {
//        if(g3(borrowerAccount,debit.get(9).toString(),debit.get(10).toString())) {
//        if(g4(borrowerAccount,debit.get(9).toString(),debit.get(10).toString())) {
//
//        if(g5(borrowerAccount,debit.get(9).toString(),debit.get(10).toString())) {
//
//
//        }
//        }
//
//        }
//
//        }   
//
//        }
//
//
//        if( fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "excessIdentifyer"+borrowerAccount))==19){
//
//
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount));
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount), fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount)));
//        confrimBr1= returnSharesForBorrower(borrowerAccount,debit);   
//        if(confrimBr1){
//        confirm=true;
//        } 
//
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "excessIdentifyer"+borrowerAccount)); 
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "excessPT"+borrowerAccount));
//
//
//
//        }
//
//
//
//        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "theyDid"+borrowerAccount+".txt"))==26){
//        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount));
//        String remainingAmount=(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt")))-parseDouble( fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount))))+"";
//
//        fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),remainingAmount); 
//
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "theyDid"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance( fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount)); 
//        }else{
//
//        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));  
//
//        String remainingAmount=(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt")))-parseDouble( fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount))))+"";
//
//        fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),remainingAmount); 
//
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowerAccount));
//        }
//
//        if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "endLoan"+borrowerAccount+".txt"))==256){
//        dbq.updateBorrowing(borrowerAccount, "NB");
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore14"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+borrowerAccount+".txt"));      
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "lockedSecondGuarantorB1"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB1"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB2"+borrowerAccount+".txt"));      
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+borrowerAccount+".txt"));              
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+borrowerAccount+".txt")); 
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox5SelectedS"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowedC"+borrowerAccount+".txt"));  
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "endLoan"+borrowerAccount+".txt"));
//
//
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountQualifiedAfterGuarantors"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "theyDid"+borrowerAccount+".txt"));
//
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "GuarantorsAccount"+borrowerAccount+".txt"));
//        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+borrowerAccount+".txt"));
//
//
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "treatOnceFirst"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "principalPending"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "partlyPaid"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "overallTotal"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "haveYou"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("ploanRepayment", "partlyPaid", "excessPT"+borrowerAccount));
//        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "excessIdentifyer"+borrowerAccount));
//
//
//
//
//        }
//
//        return confirm;
//
//        }



        public boolean loanDisbursement(List debit ,List credit){
      
            boolean confirm=false, confirm1=false,confirm2=false;
      
            

          String borrowerAccount=fmt.formatAccountWithSeperators(credit.get(0).toString()); 


        Double D=parseDouble(debit.get(5).toString());

        int dramt=D.intValue();

        if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"))==46) {



        if(!(dramt==fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt")+".txt"))){
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "disbursementStatusAmount"+borrowerAccount+".txt"));
        Double amount=parseDouble(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt"))+".0");

        debit.add(5, amount);
        credit.add(5, amount);


        }
        }



        confirm1= dbq.debit(debit,c);
        if(confirm1){
        confirm2= dbq.credit(credit,c);
        }
        
        if(confirm1==confirm2){

        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDisbursementPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createDisbursementPaymentSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);

        }

        

//          if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "ReceiptEmphasis.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
//        sendsms.createReceiptEmphasisSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);
//
//        }

//        dbq.updateLaonSavingsSharesForDisbursements(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString());
        confirm=true;


        Integer xl=1;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"),xl.toString());

        Integer xxy=5;



        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+borrowerAccount+ ".txt"), xxy.toString());
        dbq.updateBorrowingStatus(fmt.formatAccountWithSeperators(credit.get(0).toString()),"B");

        }

//        loan.updateLoanStoreAll(this.userId, borrowerAccount,"Disbursed","newloan"+borrowerAccount,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+borrowerAccount+".txt")).split("[,]", 15)[14].replace(",", ""));


        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.creditCashFirst(borrowerAccount, credit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"loan Cashed out");

        }
//        
// SwingWorker<Void,Void> updateTheTable = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//       updateTheTable(credit,c);
//
//
//        return null;
//        }};
//
//        updateTheTable.execute();
//        
//         
//         SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(credit,c);
//       
//        return null;
//        }
//
//        };
//        updateLoanBalances.execute();
//        
//        
          SwingWorker<Void,Void> updateLoanDisbursedCount= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLoanDisburseCounts(borrowerAccount,debit.get(5).toString());
       
        return null;
        }

        };
        updateLoanDisbursedCount.execute();
        
           SwingWorker<Void,Void> collectionReport= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.collectOnDisbursement(borrowerAccount);
       
        return null;
        }

        };
        collectionReport.execute();

        return confirm;

        }
       
        
        
        public boolean renewedLoanDisburse(List debit ,List credit){
      
            boolean confirm=false, confirm1=false,confirm2=false;
      
            

          String borrowerAccount=fmt.formatAccountWithSeperators(credit.get(0).toString()); 


        Double D=parseDouble(debit.get(5).toString());

        int dramt=D.intValue();

        if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"))==46) {



        if(!(dramt==fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt")+".txt"))){
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "disbursementStatusAmount"+borrowerAccount+".txt"));
        Double amount=parseDouble(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt"))+".0");

        debit.add(5, amount);
        credit.add(5, amount);


        }
        }



        confirm1= dbq.debit(debit,c);
        if(confirm1){
        confirm2= dbq.credit(credit,c);
        }
        
        if(confirm1==confirm2){

        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDisbursementPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createDisbursementPaymentSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);

        }

        

//          if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "ReceiptEmphasis.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
//        sendsms.createReceiptEmphasisSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);
//
//        }

//        dbq.updateLaonSavingsSharesForDisbursements(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString());
        confirm=true;


        Integer xl=1;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"),xl.toString());

        Integer xxy=5;



        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+borrowerAccount+ ".txt"), xxy.toString());
        dbq.updateBorrowingStatus(fmt.formatAccountWithSeperators(credit.get(0).toString()),"B");

        }

//        loan.updateLoanStoreAll(this.userId, borrowerAccount,"Disbursed","newloan"+borrowerAccount,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+borrowerAccount+".txt")).split("[,]", 15)[14].replace(",", ""));


//        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){
//
//        this.creditCashFirst(borrowerAccount, credit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"loan Cashed out");
//
//        }
//        
// SwingWorker<Void,Void> updateTheTable = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//       updateTheTable(credit,c);
//
//
//        return null;
//        }};
//
//        updateTheTable.execute();
//        
//         
//         SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(credit,c);
//       
//        return null;
//        }
//
//        };
//        updateLoanBalances.execute();
//        
//        
          SwingWorker<Void,Void> updateLoanDisbursedCount= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.updateLoanDisburseCounts(borrowerAccount,debit.get(5).toString());
       
        return null;
        }

        };
        updateLoanDisbursedCount.execute();
        
           SwingWorker<Void,Void> collectionReport= new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {

        dbq.collectOnDisbursement(borrowerAccount);
       
        return null;
        }

        };
        collectionReport.execute();

        return confirm;

        }
        
        
        
        
        
        
        
        public synchronized void updateTheTable(List credit,Component c){
        
//            JOptionPane.showMessageDialog(c, ""+credit.get(0).toString());
                try {
                      wait(1000);
                  } catch (InterruptedException ex) {
                      Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
                  }
            
            
          String borrowerAccount=fmt.formatAccountWithSeperators(credit.get(0).toString());      
//         JOptionPane.showMessageDialog(c, borrowerAccount);
 List interestRateId=loan.loanTrnIdInterestRatePI(borrowerAccount);    
//   JOptionPane.showMessageDialog(c, credit.get(9).toString());
//     JOptionPane.showMessageDialog(c, credit.get(5).toString());
//       JOptionPane.showMessageDialog(c, interestRateId.get(2));
    List newloanDis=new ArrayList();

newloanDis.add(interestRateId.get(0));//loanTrnId
newloanDis.add("newloan"+borrowerAccount);//LoanId
newloanDis.add(credit.get(9).toString());//BatchCode
newloanDis.add(credit.get(5).toString());//AmountDisbursed
newloanDis.add(interestRateId.get(1));//Expected Interest
newloanDis.add(interestRateId.get(2));//Expected Total Amount
newloanDis.add(interestRateId.get(3));//Interest Rate
newloanDis.add("0.0");//Amount Paid
newloanDis.add("0.0");//PrincipalPaid
newloanDis.add("0.0");//Interest Paid
newloanDis.add("0.0");//AccumulatedInterestPaid
newloanDis.add("0.0");//LoanPenaltyPaid
//JOptionPane.showMessageDialog(c, newloanDis.size());
dbq.createTheInitailLoanDisState(newloanDis,c); 
        
        
        
        
        
        
        
        }
        
        
public boolean loanDisbursementBank(List debit ,List credit,String txnCode){
        boolean confirm=false, confirm1=false,confirm2=false;
        
        String borrowerAccount=fmt.formatAccountWithSeperators(credit.get(0).toString()); 



        Double D=parseDouble(debit.get(5).toString());

        int dramt=D.intValue();

        if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"))==46) {



        if(!(dramt==fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt")+".txt"))){
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "disbursementStatusAmount"+borrowerAccount+".txt"));
        Double amount=parseDouble(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+borrowerAccount+".txt"))+".0");

        debit.add(5, amount);
        credit.add(5, amount);


        }
        }



        confirm1= dbq.debit(debit,c);
        if(confirm1){
        confirm2= dbq.credit(credit,c);
        }
        if(confirm1==confirm2){

        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDisbursementPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createDisbursementPaymentSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);

        }

//         if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "ReceiptEmphasis.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
//        sendsms.createReceiptEmphasisSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);
//
//        }
// 
        dbq.updateLaonSavingsSharesForDisbursements(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString());
        confirm=true;


        Integer xl=1;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+borrowerAccount+".txt"),xl.toString());

        Integer xxy=5;



        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+borrowerAccount+ ".txt"), xxy.toString());
        dbq.updateBorrowingStatus(fmt.formatAccountWithSeperators(credit.get(0).toString()),"B");

        }

//        loan.updateLoanStoreAll(this.userId, borrowerAccount,"Disbursed","newloan"+borrowerAccount,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+borrowerAccount+".txt")).split("[,]", 15)[14].replace(",", ""));


        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        this.creditBankFirst(borrowerAccount, credit,  dbq.AccountName(borrowerAccount)+"'s"+" "+"loan Cashed out",txnCode);

        }
//        
// SwingWorker<Void,Void> updateTheTable = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//       updateTheTable(credit,c);
//
//
//        return null;
//        }};
//
//        updateTheTable.execute();

//        
//         SwingWorker<Void,Void> updateLoanBalances= new SwingWorker(){
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        updateLoanBalances(credit,c);
//       
//        return null;
//        }
//
//        };
//        updateLoanBalances.execute();
        
        
        return confirm;

        }
        public boolean regularShareContributionWithCharges(List debit ,List  credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }

        public boolean regularShareContribution(List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false,confirm3=false; String borrowingQ,guanteeringQ,borrowingQs;

         if(debit.get(10).toString().equalsIgnoreCase("CapR")){
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){
        if( fios.intFileReader(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+fmt.formatAccountWithSeperators(debit.get(0).toString())+".txt"))==365){

        this.debitCashFirst(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit,  dbq.AccountName(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"'s"+" "+"Savings4Shares");

        }}
         }
  if(debit.get(10).toString().equalsIgnoreCase("CapRD")){
  
 dbq.reduceSavings(debit);
 
  }


        confirm3=dbq.capitaliseShare(fmt.formatAccountWithSeperators(debit.get(0).toString()), numberFormat.format(parseDouble(debit.get(5).toString().replaceAll(",", ""))),debit.get(2).toString());


        borrowingQs=0.5*mxb.maxAmountBorrowedF(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        guanteeringQ=0.5*mxb.maxAmountBorrowed(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        borrowingQ=(parseDouble(borrowingQs)+parseDouble( guanteeringQ))+"";

        fios.stringFileWriter(fios.createFileName("persistence", "interestAccount", "TestingautoManageCash.txt"), borrowingQs+","+guanteeringQ+","+borrowingQs);
        dbq.updateBoGa(fmt.formatAccountWithSeperators(debit.get(0).toString()), borrowingQ,guanteeringQ);




        if(confirm3){

        if(dbq.debit(debit,c)){


        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }
        if(confirm1==confirm2){
        dbq.updateLaonSavingsSharesForShares(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString());


        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "SharesContributionPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createSharesPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        } 
        confirm=true;

        }
        }else{confirm=false;}
        return confirm;

        }

public boolean regularShareContributionBank(List debit ,List credit,String txnId){

        boolean confirm=false, confirm1=false,confirm2=false,confirm3=false; String borrowingQ,guanteeringQ,borrowingQs;

         if(debit.get(10).toString().equalsIgnoreCase("CapR")){
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){
        if( fios.intFileReader(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+fmt.formatAccountWithSeperators(debit.get(0).toString())+".txt"))==365){

        this.debitBankFirst(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit,  dbq.AccountName(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"'s"+" "+"Savings4Shares",txnId);

        }}
         }
  if(debit.get(10).toString().equalsIgnoreCase("CapRD")){
  
 dbq.reduceSavings(debit);
 
  }


        confirm3=dbq.capitaliseShare(fmt.formatAccountWithSeperators(debit.get(0).toString()), numberFormat.format(parseDouble(debit.get(5).toString().replaceAll(",", ""))),debit.get(2).toString());


        borrowingQs=0.5*mxb.maxAmountBorrowedF(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        guanteeringQ=0.5*mxb.maxAmountBorrowed(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        borrowingQ=(parseDouble(borrowingQs)+parseDouble( guanteeringQ))+"";

        fios.stringFileWriter(fios.createFileName("persistence", "interestAccount", "TestingautoManageCash.txt"), borrowingQs+","+guanteeringQ+","+borrowingQs);
        dbq.updateBoGa(fmt.formatAccountWithSeperators(debit.get(0).toString()), borrowingQ,guanteeringQ);




        if(confirm3){

        if(dbq.debit(debit,c)){


        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }
        if(confirm1==confirm2){
        dbq.updateLaonSavingsSharesForShares(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString());


        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "SharesContributionPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createSharesPaymentSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        } 
        confirm=true;

        }
        }else{confirm=false;}
        return confirm;

        }


        public boolean oneTimeShareContribution(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }

        public boolean generalPosting(List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false;




        if(dbq.debit(debit,c)){



        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        
           public boolean generalPosting1xx(List debit ,List credit,JTextField fieldMe){

        boolean confirm=false, confirm1=false,confirm2=false;




        if(dbq.debit1xx(debit,c,fieldMe)){



        confirm1= true;
        if(dbq.credit1xx(credit,c,fieldMe)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        
        
        public boolean savingsPosting(List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false;




        if(dbq.debitSave(debit,c)){



        confirm1= true;
        if(dbq.creditSave(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){



        dbq.updateLaonSavingsSharesForSavings(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString());
        confirm=true;

        }
        if(debit.get(10).toString().equalsIgnoreCase("Save2")){
        
        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "SavingsPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createSavingsSMS(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString(),c);
        }
        
         SwingWorker<Void,Void> initialiseSavingsValueTotal = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
dbq.initialiseSavingsValueTotal();
//dbq.initialiseSharesValueTotal();
            return null;   
            } };


            initialiseSavingsValueTotal.execute();
        }     

        return confirm;

        }

        public boolean  salaryPosting (List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false;




        if(dbq.debit(debit,c)){



        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        String accountNumber=fmt.formatAccountWithSeperators(credit.get(0).toString());      

        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){

        if( this.creditCashFirst(accountNumber, credit,  dbq.AccountName(accountNumber)+"'s"+" "+"Salary Cashed out")){
        confirm=true;
        }



        }else{ confirm=true;}

        }
        return confirm;





        }
        public boolean cashWithdrawPosting(List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false;




        if(dbq.debit(debit,c)){



        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){
            if(debit.get(10).toString().equalsIgnoreCase("WdrawS2")){
        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "WithDrawPayment.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllCustomerAccountTransact.txt"))==7){
        sendsms.createwithdrawTransacttSMS(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString(),c);

        }        


        dbq.updateLaonSavingsSharesForWithdraws(fmt.formatAccountWithSeperators(debit.get(0).toString()), debit.get(5).toString());
        confirm=true;
            }
        if( fios.intFileReader(fios.createFileName("persistence", "interestAccount", "withDrawChargesSet.txt"))==15){

        processWithdrawCharges(debit);
        confirm=true;



        }else{
        confirm=true;
        }
        }
        return confirm;

        }
        public boolean loanWriteOffs(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }

        public boolean loanResheduling(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        public boolean loanTopUps(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        public boolean loanCancellation(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        public boolean bankToCash(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }

        public boolean cashToBank(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        public boolean charges(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;

        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }
        public boolean insurance(List debit ,List credit){
        boolean confirm=false, confirm1=false,confirm2=false;
        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }

        if(confirm1==confirm2){

        confirm=true;
        }
        return confirm;

        }

        public boolean decapitalise(List debit ,List credit){

        boolean confirm=false, confirm1=false,confirm2=false,confirm3=false;String borrowingQ,guanteeringQ,borrowingQs;

        String accountNumber=fmt.formatAccountWithSeperators(credit.get(0).toString());

        fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindowCopy"+accountNumber+".txt"), fios.intFileReader(fios.createFileName("sharesAvailable","shareValues", "usedWindow"+accountNumber+".txt"))+"");



        confirm3=dbq.ReduceShares(fmt.formatAccountWithSeperators(credit.get(0).toString()),numberFormat.format(parseDouble(credit.get(5).toString().replaceAll(",", ""))),credit.get(2).toString());


        borrowingQs=0.5*mxb.maxAmountBorrowedF(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        guanteeringQ=0.5*mxb.maxAmountBorrowed(fmt.formatAccountWithSeperators(debit.get(0).toString()))+"";

        borrowingQ= (parseDouble(borrowingQs)+parseDouble( guanteeringQ))+"";

        dbq.updateBoGa(fmt.formatAccountWithSeperators(credit.get(0).toString()), borrowingQ, guanteeringQ);

        if(confirm3){
        if(dbq.debit(debit,c)){
        confirm1= true;
        if(dbq.credit(credit,c)){
        confirm2= true;
        }

        }


        if(confirm1==confirm2){






        confirm=true;

        }
        }else{ confirm=false;}


        if(credit.get(10).toString().equalsIgnoreCase("DCap")){
        if(fios.intFileReader(fios.createFileName("persistence", "interestAccount", "autoManageCash.txt"))==46){
        if( fios.intFileReader(fios.createFileName("sharesAvailable","shareValues", "usedWindowCopy"+accountNumber+".txt"))==365){
        if( this.creditCashFirst(accountNumber, credit,  dbq.AccountName(accountNumber)+"'s"+" "+"Decapitalised Shares cashed out")){
        Integer v=7;
        fios.intFileWriterReplace(fios.createFileName("sharesAvailable","shareValues", "usedWindowCopy"+accountNumber+".txt"),v.toString());
        }
        }  
        } 
        }
  
          if(credit.get(10).toString().equalsIgnoreCase("DCapD")){
          dbq.captureSavingsIndividual(credit,c);
          
          }
 
        return confirm;


        }

        public boolean isReducableCapital(List debit ,List credit){

        return dbq.testReducebilityOfShares(fmt.formatAccountWithSeperators(credit.get(0).toString()), credit.get(5).toString());

        }

        public boolean accountsAreFine(List debit ,List credit){
        boolean confirm=false;
        if((parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))>=parseInt("4000000")&&parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))<=parseInt("4109999"))&&(parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))>=parseInt("5020000")&&parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))<=parseInt("5029999"))){

        confirm=true;
        }else if((parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))>=parseInt("4000000")&&parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))<=parseInt("4109999"))&&(parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))>=parseInt("5020000")&&parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))<=parseInt("5029999"))){
        confirm=true;

        }
        return confirm;
        }



        public boolean accountsAreFineLR(List debit ,List credit){
        boolean confirm=false;
        if((parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))>=parseInt("1280000")&&parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))<=parseInt("1289999"))&&(parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))>=parseInt("5020000")&&parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))<=parseInt("5029999"))){

        confirm=true;
        }else if((parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))>=parseInt("1280000")&&parseInt(fmt.getMasterAccountCode(credit.get(0).toString()))<=parseInt("1289999"))&&(parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))>=parseInt("5020000")&&parseInt(fmt.getMasterAccountCode(debit.get(0).toString()))<=parseInt("5029999"))){
        confirm=true;

        }
        return confirm;
        }






//        private boolean updateLoanReapayment(List debit, List credit){
//
//        boolean confirm=false, confirmi=false, confirm1=false, confirm2=false,confirm3=false, confirm4=false,confirmc=false;
//        String accountDebit=debit.get(0).toString();
//
//        Integer xc=39;
//
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "loanRepayment"),xc.toString());
//
//        confirm=loan.updateLoanAmortzationShedule(fmt.formatAccountWithSeperators(accountDebit), debit.get(5).toString());
//
//        if( fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "interestPending"+fmt.formatAccountWithSeperators(accountDebit)))==45){
//
//        if(fios.intFileReader(fios.createFileName("postingEntry", "generalTrn", "waiveInterest.txt"))==7){
//        confirmi=true;
//        fios.intFileWriterReplace(fios.createFileName("postingEntry", "generalTrn", "waiveInterest.txt"), "3");
//        Integer sdv=0;
//
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "interestPending"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());//Holds a value of 45 which indicates that interest needs to be earned
//
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());//Holds the total amount of Interest paid
//
//        Integer xcs=3;
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "loanRepayment"),xcs.toString());
//
//
//        }else{
//
//        List  debits = new ArrayList();
//        List  credits = new ArrayList();  
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+fmt.formatAccountWithSeperators(accountDebit)));
//
//        double Interest=  parseDouble(fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+fmt.formatAccountWithSeperators(accountDebit))));
//
//
//        String interestAC=fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setInterestAc1.txt"));
//
//        if(Interest>0.0){
//
//        debits .add(0, credit.get(0).toString()) ;
//        debits .add(1, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debits .add(2, "Interest from"+" "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+fmt.formatAccountWithSeperators(accountDebit)+".txt")).split("[,]", 15)[10]+"'s"+" "+"loan instalments paid.");
//        debits .add(3, "Repaid on"+"  "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debits .add(4, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debits .add(5, Interest+"");
//        debits .add(6,  fmt.putSeparatorsOnNormaAccount(interestAC.split("[,]", 2)[0]));
//        debits .add(7, interestAC.split("[,]", 2)[1]);
//        debits .add(8, "intest1");
//        debits .add(9, debit.get(9).toString());
//        debits .add(10, debit.get(10).toString());
//        debits .add(11, dbq.getTransactionSequenceNumber()+"");
//
//
//        credits.add(0, fmt.putSeparatorsOnNormaAccount(interestAC.split("[,]", 2)[0]));
//        credits.add(1, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        credits.add(2, "Interest from"+" "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+fmt.formatAccountWithSeperators(accountDebit)+".txt")).split("[,]", 15)[10]+"'s"+" "+"  "+"loan repayment.");
//        credits.add(3, "Paid on"+" "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        credits.add(4, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        credits.add(5, Interest+"");
//        credits.add(6, credit.get(0).toString());
//        credits.add(7, dbq.AccountName(credit.get(0).toString()));
//        credits.add(8, "asms0001");
//        credits.add(9, credit.get(9).toString());
//        credits.add(10, credit.get(10).toString());
//        credits.add(11, dbq.getTransactionSequenceNumber()+"");
//
//        if(dbq.debit(debits,c)){
//        confirm1= true;
//        if(dbq.credit(credits,c)){
//        confirm2= true;
//        }
//
//        }
//
//        }
//
//
//        if(confirm1==confirm2){
//        dbq.updateLaonSavingsSharesForInterest(fmt.formatAccountWithSeperators(debit.get(0).toString()), debits.get(5).toString());
//        confirmi=true;
//
//        Integer sdv=0;
//
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "interestPending"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());//Holds a value of 45 which indicates that interest needs to be earned
//
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());//Holds the total amount of Interest paid
//
//        Integer xcs=3;
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "loanRepayment"),xcs.toString());
//        }else{
//
//        confirmi=false;
//
//        }
//
//        }
//
//        }
//        if( fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "chargesPending"+fmt.formatAccountWithSeperators(accountDebit)))==450){//Holds a value of 450 which shows that charges were accrued and need to be posted
//        List  debitsC = new ArrayList();
//        List  creditsC = new ArrayList();  
//
//        double charges=  parseDouble(fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalCharges"+fmt.formatAccountWithSeperators(accountDebit))));
//
//
//        if(charges>0.0){
//
//        String chargesAC="03312000110";
//
//
//
//        debitsC .add(0, credit.get(0).toString());
//        debitsC .add(1, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debitsC .add(2, "Ln L Pyt Chrge-"+" "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+fmt.formatAccountWithSeperators(accountDebit)+".txt")).split("[,]", 15)[10]+"'s"+" "+"loan instalment paid.");
//        debitsC .add(3, "Repaid on"+"  "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debitsC .add(4, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        debitsC .add(5,  charges+"");
//        debitsC .add(6, fmt.putSeparatorsOnNormaAccount(chargesAC.split("[,]", 2)[0]));
//        debitsC .add(7, chargesAC);
//        debitsC .add(8, "charges1");
//        debitsC .add(9, debit.get(9).toString());
//        debitsC .add(10, debit.get(10).toString());
//        debitsC .add(11, dbq.getTransactionSequenceNumber()+"");
//
//
//        creditsC.add(0, fmt.putSeparatorsOnNormaAccount(chargesAC));
//        creditsC.add(1, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        creditsC.add(2, "Ln L Pyt Chrge-"+" "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+fmt.formatAccountWithSeperators(accountDebit)+".txt")).split("[,]", 15)[10]+"'s"+" "+"  "+"loan repayment.");
//        creditsC.add(3, "Paid on"+" "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        creditsC.add(4, fmt.dateConverterForNormalDate(System.currentTimeMillis()));
//        creditsC.add(5, charges+"");
//        creditsC.add(6, credit.get(0).toString());
//        creditsC.add(7, dbq.AccountName(fmt.formatAccountWithSeperators(credit.get(0).toString())));
//        creditsC.add(8, "asms0001");
//        creditsC.add(9, credit.get(9).toString());
//        creditsC.add(10, credit.get(10).toString());
//        creditsC.add(11, dbq.getTransactionSequenceNumber()+"");
//
//
//        if(dbq.debit(debitsC,c)){
//        confirm3= true;
//        if(dbq.credit(creditsC,c)){
//        confirm4= true;
//        }
//
//        }
//
//        if(confirm3==confirm4){
//        dbq.updateLaonSavingsSharesForCharges(fmt.formatAccountWithSeperators(debit.get(0).toString()), debitsC.get(5).toString());
//        confirmc=true;
//
//        Integer sdv=0;
//
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "chargesPending"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());
//
//        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalCharges"+fmt.formatAccountWithSeperators(accountDebit)),sdv.toString());
//
//        }else{
//
//        confirm=false;
//
//        }
//
//
//
//        if(confirmi||confirmc){
//
//        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "principalPending2"+accountDebit))==45){
//
//
//
//        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal2"+accountDebit));   
//
//        String amountai= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal2"+accountDebit));
//
//        Integer zw=7;
//
//        fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "principalPending2"+accountDebit), zw.toString());
//
//        dbq.updateLaonSavingsSharesForPrincimpal(accountDebit, amountai);
//
//
//
//        }
//
//        confirm=true; 
//
//        }
//
//
//
//
//
//        }
//
//        }
//        return confirm;
//        }


        public boolean  returnSharesForGuarntors(String borrowersAccount){


        boolean conf =false,confa=false,confb=false,confc=false,confd=false,confe=false;

        Integer z=fios.intFileReader(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowersAccount+".txt"));


        if(z==1){

        String details=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowersAccount+".txt"));

        confa=processTheGuarantorReturn(borrowersAccount,details.split("[;]", 7)[1],details.split("[;]", 7)[4],z.toString());

        if(confa){conf=true;}



        } else if(z==2){

        String details=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowersAccount+".txt"));

        String details1=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowersAccount+".txt"));

        confa=processTheGuarantorReturn(borrowersAccount,details.split("[;]", 7)[1],details.split("[;]", 7)[4],"1");  
        if(confa){

        confb=processTheGuarantorReturn(borrowersAccount,details1.split("[;]", 7)[1],details1.split("[;]", 7)[4],z.toString());

        if(confb){conf=true;}else{conf=false;}
        }


        }else if(z==3){
        String details=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowersAccount+".txt"));
        String details1=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowersAccount+".txt"));

        String details2=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowersAccount+".txt"));

        confa=processTheGuarantorReturn(borrowersAccount,details.split("[;]", 7)[1],details.split("[;]", 7)[4],"1");  
        if(confa){
        confb=processTheGuarantorReturn(borrowersAccount,details1.split("[;]", 7)[1],details1.split("[;]", 7)[4],"2");

        }


        if(confb){
        confc= processTheGuarantorReturn(borrowersAccount,details2.split("[;]", 7)[1],details2.split("[;]", 7)[4],z.toString());
        if(confc){conf=true;}else{conf=false;}
        }
        }else if(z==4){



        String details=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowersAccount+".txt"));
        String details1=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowersAccount+".txt"));

        String details2=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowersAccount+".txt"));
        String details3=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowersAccount+".txt"));
        confa=processTheGuarantorReturn(borrowersAccount,details.split("[;]", 7)[1],details.split("[;]", 7)[4],"1");  
        if(confa){
        confb=processTheGuarantorReturn(borrowersAccount,details1.split("[;]", 7)[1],details1.split("[;]", 7)[4],"2");
        }


        if(confb){
        confc= processTheGuarantorReturn(borrowersAccount,details2.split("[;]", 7)[1],details2.split("[;]", 7)[4],"3");
        }
        if(confc){
        confd= processTheGuarantorReturn(borrowersAccount,details3.split("[;]", 7)[1],details3.split("[;]", 7)[4],z.toString());

        if(confd){conf=true;}else{conf=false;}
        }



        }else if(z==5){
        String details=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowersAccount+".txt"));
        String details1=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowersAccount+".txt"));

        String details2=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowersAccount+".txt"));
        String details3=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowersAccount+".txt"));
        String details4=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowersAccount+".txt"));
        confa=processTheGuarantorReturn(borrowersAccount,details.split("[;]", 7)[1],details.split("[;]", 7)[4],"1");  
        if(confa){
        confb=processTheGuarantorReturn(borrowersAccount,details1.split("[;]", 7)[1],details1.split("[;]", 7)[4],"2");
        if(confb){conf=true;}else{conf=false;}
        }


        if(confb){
        confc= processTheGuarantorReturn(borrowersAccount,details2.split("[;]", 7)[1],details2.split("[;]", 7)[4],"3");
        }
        if(confc){
        confd= processTheGuarantorReturn(borrowersAccount,details3.split("[;]", 7)[1],details3.split("[;]", 7)[4],"4");

        }

        if(confd) {
        confe=processTheGuarantorReturn(borrowersAccount,details4.split("[;]", 7)[1],details4.split("[;]", 7)[4],z.toString());
        if(confe){conf=true;}else{conf=false;}
        }


        }

        return conf;
        }
    
        public boolean  returnSharesForBorrower(String borrowersAccount,List debit){

        boolean ready=false,steady=false,overall=false;
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount));
        ready=TwentyFivePercentsB( borrowersAccount, twentyFivePercent(hundredPercent(fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount)))),debit.get(9).toString(),debit.get(10).toString());  

        if(ready){

        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount));

        steady=HundredPercentB( borrowersAccount,hundredPercent(fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount))),debit);

        }  
        if( steady){

        overall=true;
        } 

        return overall;
        }


        public boolean   processTheGuarantorReturn(String borrowersAccount,String guarntorsAccount,String amountGuaranteed,String gN){

        Integer t=1; String var="";

        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", gN+"amountReturned"+borrowersAccount+".txt")); 

        while(t<=5){

        if(t==1){

        var=borrowersAccount;

        }
        if(t==2){

        var=guarntorsAccount;
        }
        if(t==3){

        var=amountGuaranteed;
        }
        if(t==4){

        var=fractionOfAmount(borrowersAccount, amountGuaranteed);

        }
        if(t==5){

        var=twentyFivePercent(hundredPercent(fractionOfAmount(borrowersAccount, amountGuaranteed)));

        }
        fios.stringFileWriterAppend(fios.createFileName("loanRepayment", "partlyPaid", gN+"amountReturned"+borrowersAccount+".txt"),var);
        fios.stringFileWriterAppend(fios.createFileName("loanRepayment", "partlyPaid", gN+"amountReturned"+borrowersAccount+".txt"),";");

        t++;
        }
        Integer c=45;
        fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", gN+"exists"+borrowersAccount+".txt"),c.toString()); 

        return  true; 

        }


        public boolean    TwentyFivePercentsG(String bAccount, String gAccount,String amount,String batchRef,String trnType){
        boolean conf=false;
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        debit .add(1, sdf.format(fmt.getTodayDate()));
        debit .add(2,  dbq.AccountName(gAccount)+" "+"Guarant to"+" "+dbq.AccountName(bAccount));
        debit .add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        debit .add(4, sdf.format(fmt.getTodayDate()));
        debit .add(5, amount);
        debit .add(6, fmt.putSeparatorsOnNormaAccount(gAccount));
        debit .add(7, dbq.AccountName(gAccount));
        debit .add(8, "000zib");
        debit .add(9, batchRef);
        debit .add(10, trnType);
        debit .add(11,  dbq.getTransactionSequenceNumber()+"");


        credit.add(0,  fmt.putSeparatorsOnNormaAccount(gAccount));
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2,  dbq.AccountName(gAccount)+" "+"Guarant to"+" "+dbq.AccountName(bAccount));
        credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, amount);
        credit.add(6,  fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        credit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        credit.add(8, "000zib");
        credit.add(9, batchRef);
        credit.add(10, trnType);
        credit.add(11,  dbq.getTransactionSequenceNumber()+"");

        if(!(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1].equalsIgnoreCase("0"))){

        conf= generalPosting(debit, credit);


        }
        return conf;
        }


        public boolean HundredPercentG(String bAccount, String gAccount,String amount,String batchRef,String trnType){
        boolean conf1=false;
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0,  fmt.putSeparatorsOnNormaAccount(gAccount));
        debit .add(1, sdf.format(fmt.getTodayDate()));
        debit .add(2,  dbq.AccountName(gAccount)+" "+"Guarant"+" "+dbq.AccountName(bAccount));
        debit .add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        debit .add(4, sdf.format(fmt.getTodayDate()));
        debit .add(5, amount);
        debit .add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        debit .add(7,dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        debit .add(8, "000zib");
        debit .add(9, batchRef);
        debit .add(10, trnType);
        debit .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2,  dbq.AccountName(gAccount)+" "+"Guarant"+" "+dbq.AccountName(bAccount));
        credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, amount);
        credit.add(6, fmt.putSeparatorsOnNormaAccount(gAccount));
        credit.add(7, dbq.AccountName(gAccount));
        credit.add(8, "000zib");
        credit.add(9, batchRef);
        credit.add(10, trnType);
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(!(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt"))).split("[,]", 2)[0].equalsIgnoreCase("0")){

        if(regularShareContribution(debit, credit)){


        conf1=true;
        }
        }
        return conf1;
        }


        public boolean hasUsedGuarantors(String borrowersAccount){
        boolean yesUsed=false;

        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){

        yesUsed=true;

        }

        return yesUsed;
        }

        public boolean guarantorsTiedToLoan(String borrowersAccount){
        boolean yesTied=false;

        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){

        yesTied=true;
        }
        return  yesTied;

        }

        public boolean gurantorStillAround(String borrowersAccount){

        boolean stillHas=false;
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalGuarantorR"+borrowersAccount));
        if(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowersAccount+".txt")))>0.0){

        stillHas=true;

        }else{
        Integer tx=320;
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowersAccount+".txt"),tx.toString());  
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowersAccount+".txt"),tx.toString());  
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowersAccount+".txt"),tx.toString());  
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowersAccount+".txt"),tx.toString());  
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowersAccount+".txt"),tx.toString());  
        fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowersAccount+".txt"),tx.toString());  
        }
        return stillHas;
        }
        public String twentyFivePercent(String amount){

        Double amountdevide=(parseDouble(amount)/4);
        return amountdevide+"";
        }

        public String fractionOfAmount(String borrowersAccount, String amount){



        String amountFraction="";
        fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowersAccount+".txt"));
        fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount));
        amountFraction=(parseDouble( fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+borrowersAccount)))*(parseDouble(amount)/parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowersAccount+".txt")))))+"";

        return amountFraction;

        }
        public String   hundredPercent(String amount){

        Double amountdevide=((parseDouble(amount)/3)*4);
        return amountdevide.toString();

        }
        public boolean g1(String accountNumber,String batchRef,String trnType){
        boolean ready=false,steady=false;String details2="";
        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "1"+"exists"+accountNumber+".txt"))==45 ){

        details2= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "1"+"amountReturned"+accountNumber+".txt"));

        ready=TwentyFivePercentsG(accountNumber, details2.split("[;]", 5)[1],details2.split("[;]", 5)[4].replaceAll(";", ""),batchRef,trnType);  
        } 
        if(ready){
        steady=HundredPercentG(accountNumber,details2.split("[;]", 5)[1],hundredPercent(details2.split("[;]", 5)[3]),batchRef,trnType);
        }
        if(steady){
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "1"+"exists"+accountNumber+".txt"));
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "1"+"amountReturned"+accountNumber+".txt"));   
        }

        return steady;

        }
        public boolean g2(String accountNumber,String batchRef,String trnType){
        boolean ready=false,steady=false; String details2="";
        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "2"+"exists"+accountNumber+".txt"))==45 ){

        details2= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "2"+"amountReturned"+accountNumber+".txt"));

        ready=TwentyFivePercentsG(accountNumber, details2.split("[;]", 5)[1],details2.split("[;]", 5)[4].replaceAll(";", ""),batchRef,trnType);  
        } 
        if(ready){
        steady=HundredPercentG(accountNumber,details2.split("[;]", 5)[1],hundredPercent(details2.split("[;]", 5)[3]),batchRef,trnType);
        }
        if(steady){
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "2"+"exists"+accountNumber+".txt"));
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "2"+"amountReturned"+accountNumber+".txt"));   
        }
        return steady;
        }
        public boolean g3(String accountNumber,String batchRef,String trnType){
        boolean ready=false,steady=false; String details2="";

        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "3"+"exists"+accountNumber+".txt"))==45 ){

        details2= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "3"+"amountReturned"+accountNumber+".txt"));

        ready=TwentyFivePercentsG(accountNumber, details2.split("[;]", 5)[1],details2.split("[;]", 5)[4].replaceAll(";", ""),batchRef,trnType);  
        } 
        if(ready){
        steady=HundredPercentG(accountNumber,details2.split("[;]", 5)[1],hundredPercent(details2.split("[;]", 5)[3]),batchRef,trnType);
        }
        if(steady){
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "3"+"exists"+accountNumber+".txt"));
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "3"+"amountReturned"+accountNumber+".txt"));   
        }
        return steady;
        }
        public boolean g4(String accountNumber,String batchRef,String trnType){
        boolean ready=false,steady=false;String details2="";
        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "4"+"exists"+accountNumber+".txt"))==45 ){
        details2= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "4"+"amountReturned"+accountNumber+".txt"));

        ready=TwentyFivePercentsG(accountNumber, details2.split("[;]", 5)[1],details2.split("[;]", 5)[4].replaceAll(";", ""),batchRef,trnType);  
        } 
        if(ready){
        steady=HundredPercentG(accountNumber,details2.split("[;]", 5)[1],hundredPercent(details2.split("[;]", 5)[3]),batchRef,trnType);
        }
        if(ready){
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "4"+"exists"+accountNumber+".txt"));
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "4"+"amountReturned"+accountNumber+".txt"));   
        }
        return steady;
        }
        public boolean g5(String accountNumber,String batchRef,String trnType){
        boolean ready=false,steady=false; String details2="";
        if(fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "5"+"exists"+accountNumber+".txt"))==45 ){

        details2= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "5"+"amountReturned"+accountNumber+".txt"));

        ready=TwentyFivePercentsG(accountNumber, details2.split("[;]", 5)[1],details2.split("[;]", 5)[4].replaceAll(";", ""),batchRef,trnType);  
        } 
        if(ready){
        steady=HundredPercentG(accountNumber,details2.split("[;]", 5)[1],hundredPercent(details2.split("[;]", 5)[3]),batchRef,trnType);
        }

        if(steady){
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "5"+"exists"+accountNumber+".txt"));
        fios.deleteFileExistance(fios.createFileName("loanRepayment", "partlyPaid", "5"+"amountReturned"+accountNumber+".txt"));   
        }
        return steady;
        }


        public boolean    TwentyFivePercentsB(String bAccount,String amount,String batchRef,String trnType){
        boolean conf=false;
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        debit .add(1, sdf.format(fmt.getTodayDate()));
        debit .add(2,  "25% Shares"+" "+dbq.AccountName(bAccount)+"'s"+" "+"Borrowing");
        debit .add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        debit .add(4, sdf.format(fmt.getTodayDate()));
        debit .add(5, amount);
        debit .add(6, fmt.putSeparatorsOnNormaAccount(bAccount));
        debit .add(7, dbq.AccountName(bAccount));
        debit .add(8, "000zib");
        debit .add(9, batchRef);
        debit .add(10, trnType);
        debit .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0,  fmt.putSeparatorsOnNormaAccount(bAccount));
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "25% Shares"+" "+dbq.AccountName(bAccount)+"'s"+" "+"Borrowing");
        credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, amount);
        credit.add(6,  fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        credit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
        credit.add(8, "000zib");
        credit.add(9, batchRef);
        credit.add(10, trnType);
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(!(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1].equalsIgnoreCase("0"))){

        conf= generalPosting(debit, credit);


        }
        return conf;
        }


        public boolean HundredPercentB(String bAccount, String amount,List debit1){
        boolean conf1=false;
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0,  fmt.putSeparatorsOnNormaAccount(bAccount));
        debit .add(1, debit1.get(1).toString());
        debit .add(2, "100% Shares"+" "+dbq.AccountName(bAccount)+"'s"+" "+"Borrowing");
        debit .add(3, "Guaranteed on"+" "+debit1.get(1).toString());
        debit .add(4, debit1.get(1).toString());
        debit .add(5, amount);
        debit .add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        debit .add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        debit .add(8, "000zib");
        debit .add(9, debit1.get(9).toString());
        debit .add(10, debit1.get(10).toString());
        debit .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
        credit.add(1, debit1.get(1).toString());
        credit.add(2, "100% Shares"+" "+dbq.AccountName(bAccount)+"'s"+" "+"Borrowing");
        credit.add(3, "Guaranteed on"+" "+debit1.get(1).toString());
        credit.add(4, debit1.get(1).toString());
        credit.add(5, amount);
        credit.add(6, fmt.putSeparatorsOnNormaAccount(bAccount));
        credit.add(7, dbq.AccountName(bAccount));
        credit.add(8, "000zib");
        credit.add(9, debit1.get(9).toString());
        credit.add(10,debit1.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(!(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt"))).split("[,]", 2)[0].equalsIgnoreCase("0")){

        if(regularShareContribution(debit, credit)){

        conf1=true;
        }
        }
        return conf1;
        }


        public boolean debitCashFirst(String creditAccount, List debit1,String narration){
        boolean conf1=false;
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0,  fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        debit .add(1, debit1.get(1));
        debit .add(2, narration);
        debit .add(3, "Dated"+" "+debit1.get(1));
        debit .add(4, debit1.get(1));
        debit .add(5, debit1.get(5));
        debit .add(6, fmt.putSeparatorsOnNormaAccount(creditAccount));
        debit .add(7, dbq.AccountName(creditAccount));
        debit .add(8, "000zib");
        debit .add(9, debit1.get(9).toString());
        debit .add(10, debit1.get(10).toString());
        debit .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(creditAccount));
        credit.add(1, debit1.get(1));
        credit.add(2, narration);
        credit.add(3, "Dated"+" "+debit1.get(1));
        credit.add(4, debit1.get(1));
        credit.add(5, debit1.get(5));
        credit.add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        credit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        credit.add(8, "000zib");
        credit.add(9, debit1.get(9).toString());
        credit.add(10, debit1.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");



        if(generalPosting(debit, credit)){

        conf1=true;
        }

        return conf1;
        }
        
        public boolean debitBankFirst(String creditAccount, List debit1,String narration,String txnCode){
            String theBankAccount=dbq.getBankUsingId(txnCode);
        boolean conf1=false;
        
        List debit= new ArrayList();List credit= new ArrayList();
        debit .add(0,  fmt.putSeparatorsOnNormaAccount(theBankAccount));
        debit .add(1, debit1.get(1));
        debit .add(2, narration);
        debit .add(3, "Dated"+" "+debit1.get(1));
        debit .add(4, debit1.get(1));
        debit .add(5, debit1.get(5));
        debit .add(6, fmt.putSeparatorsOnNormaAccount(creditAccount));
        debit .add(7, dbq.AccountName(creditAccount));
        debit .add(8, "000zib");
        debit .add(9, debit1.get(9).toString());
        debit .add(10, debit1.get(10).toString());
        debit .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(creditAccount));
        credit.add(1, debit1.get(1));
        credit.add(2, narration);
        credit.add(3, "Dated"+" "+debit1.get(1));
        credit.add(4, debit1.get(1));
        credit.add(5, debit1.get(5));
        credit.add(6, fmt.putSeparatorsOnNormaAccount(theBankAccount));
        credit.add(7, dbq.AccountName(theBankAccount));
        credit.add(8, "000zib");
        credit.add(9, debit1.get(9).toString());
        credit.add(10, debit1.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");



        if(generalPosting(debit, credit)){

        conf1=true;
        }

        return conf1;
        }
        
        
        
        public boolean creditCashFirst(String debitAccount, List creditCash,String narration){


        boolean conf1=false;
        List debit1= new ArrayList();List credit= new ArrayList();
        debit1 .add(0,  fmt.putSeparatorsOnNormaAccount(debitAccount));
        debit1 .add(1, creditCash.get(1));
        debit1.add(2, narration);
        debit1 .add(3,  "Dated"+" "+creditCash.get(1));
        debit1 .add(4, creditCash.get(1));
        debit1 .add(5, creditCash.get(5));
        debit1 .add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        debit1 .add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        debit1 .add(8, "000zib");
        debit1 .add(9, creditCash.get(9).toString());
        debit1 .add(10, creditCash.get(10).toString());
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setCashAc1.txt")).split(",", 2)[0]));
        credit.add(1, creditCash.get(1));
        credit.add(2, narration);
        credit.add(3, "Dated"+" "+creditCash.get(1));
        credit.add(4, creditCash.get(1));
        credit.add(5, creditCash.get(5));
        credit.add(6, fmt.putSeparatorsOnNormaAccount(debitAccount));
        credit.add(7, dbq.AccountName(debitAccount));
        credit.add(8, "000zib");
        credit.add(9, creditCash.get(9).toString());
        credit.add(10, creditCash.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(generalPosting(debit1, credit)){

        // fios.stringFileWriter(fios.createFileName("persistence", "interestAccount", "hhsetCashAc1.txt"), debitAccount+amount+narration);

        conf1=true;
        }

        return conf1;
        }
        
        
       public boolean creditBankFirst(String debitAccount, List creditCash,String narration,String txnCode){
 
           
           String theBankAccount=dbq.getBankUsingId(txnCode);

        boolean conf1=false;
        List debit1= new ArrayList();List credit= new ArrayList();
        debit1 .add(0,  fmt.putSeparatorsOnNormaAccount(debitAccount));
        debit1 .add(1, creditCash.get(1));
        debit1.add(2, narration);
        debit1 .add(3,  "Dated"+" "+creditCash.get(1));
        debit1 .add(4, creditCash.get(1));
        debit1 .add(5, creditCash.get(5));
        debit1 .add(6, fmt.putSeparatorsOnNormaAccount(theBankAccount));
        debit1 .add(7, dbq.AccountName(theBankAccount));
        debit1 .add(8, "000zib");
        debit1 .add(9, creditCash.get(9).toString());
        debit1 .add(10, creditCash.get(10).toString());
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(theBankAccount));
        credit.add(1, creditCash.get(1));
        credit.add(2, narration);
        credit.add(3, "Dated"+" "+creditCash.get(1));
        credit.add(4, creditCash.get(1));
        credit.add(5, creditCash.get(5));
        credit.add(6, fmt.putSeparatorsOnNormaAccount(debitAccount));
        credit.add(7, dbq.AccountName(debitAccount));
        credit.add(8, "000zib");
        credit.add(9, creditCash.get(9).toString());
        credit.add(10, creditCash.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(generalPosting(debit1, credit)){

        // fios.stringFileWriter(fios.createFileName("persistence", "interestAccount", "hhsetCashAc1.txt"), debitAccount+amount+narration);

        conf1=true;
        }

        return conf1;
        } 
        
        
        
        
        public boolean processWithdrawCharges(List otherDetails){
        String chargesAC=fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setChargesAc1.txt"));


        boolean conf1=false;
        List debit1= new ArrayList();List credit= new ArrayList();
        debit1 .add(0,  otherDetails.get(0).toString());
        debit1 .add(1, otherDetails.get(1).toString());
        debit1.add(2, "Savings Withdraw Charges");
        debit1 .add(3,  "Dated"+" "+otherDetails.get(1).toString());
        debit1 .add(4, otherDetails.get(1).toString());
        debit1 .add(5,  fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "withDrawCharges.txt")));
        debit1 .add(6, fmt.putSeparatorsOnNormaAccount(chargesAC.split("[,]", 2)[0]));
        debit1 .add(7, chargesAC.split("[,]", 2)[1]);
        debit1 .add(8, "000zib");
        debit1 .add(9, otherDetails.get(9).toString());
        debit1 .add(10, otherDetails.get(10).toString());
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");


        credit.add(0, fmt.putSeparatorsOnNormaAccount(chargesAC.split("[,]", 2)[0]));
        credit.add(1, otherDetails.get(1).toString());
        credit.add(2, dbq.AccountName(fmt.formatAccountWithSeperators(otherDetails.get(0).toString()))+"'s"+" "+"Savings Withdraw Charges");
        credit.add(3, "Dated"+" "+otherDetails.get(1).toString());
        credit.add(4, otherDetails.get(1).toString());
        credit.add(5, fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "withDrawCharges.txt")));
        credit.add(6, otherDetails.get(0).toString());
        credit.add(7, dbq.AccountName(fmt.formatAccountWithSeperators(otherDetails.get(0).toString())));
        credit.add(8, "000zib");
        credit.add(9, otherDetails.get(9).toString());
        credit.add(10, otherDetails.get(10).toString());
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        if(generalPosting(debit1, credit)){
        conf1=true;
        }

        return conf1;
        }

        
        
        
        private synchronized  void   processInterestPaymentWrittenOff(double totalInterestPaid,List credit,String debitAccountNumber){
        //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
//        String interestAC=fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setInterestAc1.txt"));

        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Interest writtenOff recovered from"+" "+dbq.AccountName(debitAccountNumber)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccountNumber));
        credits.add(7, dbq.AccountName(debitAccountNumber));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);

//        List newInD=new ArrayList();
//        newInD.add("Interest");
//          newInD.add(totalInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);



        }
private synchronized  void   processInterestPayment(double totalInterestPaid,List credit,String debitAccountNumber){
        //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        String interestAC=fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setInterestAc1.txt"));

        credits.add(0, fmt.putSeparatorsOnNormaAccount(interestAC.split("[,]", 2)[0]));//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Interest from"+" "+dbq.AccountName(debitAccountNumber)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccountNumber));
        credits.add(7, dbq.AccountName(debitAccountNumber));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);

//        List newInD=new ArrayList();
//        newInD.add("Interest");
//          newInD.add(totalInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);



        }

private synchronized  void   processAccruedInterestPaymentWrittenOff(double totalAccruedInterestPaid,List credit,String debitAccount){
        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Interest writtenOff recovered from"+" "+dbq.AccountName(debitAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccruedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccount));
        credits.add(7, dbq.AccountName(debitAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
        } 


        private synchronized  void   processAccruedInterestPayment(double totalAccruedInterestPaid,List credit,String debitAccount){
        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "01-1190001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Interest from"+" "+dbq.AccountName(debitAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccruedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccount));
        credits.add(7, dbq.AccountName(debitAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
        } 
        
                private synchronized  void       processAccruedAccumulatedInterestPaymentWrittenOff(double totalAccumulatedInterestPaid,List credit,String debitAccountNu){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Accumulated Interest writtenOff recovered from"+" "+dbq.AccountName(debitAccountNu)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccumulatedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccountNu));
        credits.add(7, dbq.AccountName(debitAccountNu));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
        
//          List newInD=new ArrayList();
//        newInD.add("AccumulatedInterest");
//          newInD.add(totalAccumulatedInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD);
        
        
        } 
        private synchronized  void       processAccruedAccumulatedInterestPayment(double totalAccumulatedInterestPaid,List credit,String debitAccountNu){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "01-1340001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Accumulated Interest from"+" "+dbq.AccountName(debitAccountNu)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccumulatedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitAccountNu));
        credits.add(7, dbq.AccountName(debitAccountNu));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
        
//          List newInD=new ArrayList();
//        newInD.add("AccumulatedInterest");
//          newInD.add(totalAccumulatedInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD);
        
        
        } 
        
        private synchronized  void       processAccumulatedInterestPayment(double totalAccumulatedInterestPaid,List credit,String debitedAccount){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3110001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Accumulated Interest from"+" "+dbq.AccountName(debitedAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccumulatedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedAccount));
        credits.add(7, dbq.AccountName(debitedAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//        List newInD=new ArrayList();
//        newInD.add("AccumulatedInterest");
//          newInD.add(totalAccumulatedInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);
        }
        
        
        private synchronized  void       processAccumulatedInterestPaymentWrittenOff(double totalAccumulatedInterestPaid,List credit,String debitedAccount){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Accumulated Interest writtenOff recovered from"+" "+dbq.AccountName(debitedAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccumulatedInterestPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedAccount));
        credits.add(7, dbq.AccountName(debitedAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//        List newInD=new ArrayList();
//        newInD.add("AccumulatedInterest");
//          newInD.add(totalAccumulatedInterestPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);
        }
        
        
        private synchronized  void     processAccruedLoanPenaltyPaymentWrittenOff(double totalAccruedLoanPenaltyPaid,List credit,String debitedLedgers){


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Penalty writtenOff recovered from"+" "+dbq.AccountName(debitedLedgers)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccruedLoanPenaltyPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedLedgers));
        credits.add(7, dbq.AccountName(debitedLedgers));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);


        }
        private synchronized  void     processAccruedLoanPenaltyPayment(double totalAccruedLoanPenaltyPaid,List credit,String debitedLedgers){


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "01-1350001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Penalty from"+" "+dbq.AccountName(debitedLedgers)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalAccruedLoanPenaltyPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedLedgers));
        credits.add(7, dbq.AccountName(debitedLedgers));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);


        }
        
        
        
                
                private synchronized  void       processLoanPenaltyPaymentWrittenOff(double totalLoanPenaltyPaid,List credit,String loanDebitedLedger){


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3060001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Penalty writtenOff recovered from"+" "+dbq.AccountName(loanDebitedLedger)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalLoanPenaltyPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(loanDebitedLedger));
        credits.add(7, dbq.AccountName(loanDebitedLedger));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//        List newInD=new ArrayList();
//        newInD.add("LoanPenalty");
//          newInD.add(totalLoanPenaltyPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);

        } 
        
        private synchronized  void       processLoanPenaltyPayment(double totalLoanPenaltyPaid,List credit,String loanDebitedLedger){


        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3120001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Penalty from"+" "+dbq.AccountName(loanDebitedLedger)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalLoanPenaltyPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(loanDebitedLedger));
        credits.add(7, dbq.AccountName(loanDebitedLedger));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//        List newInD=new ArrayList();
//        newInD.add("LoanPenalty");
//          newInD.add(totalLoanPenaltyPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);

        } 
        
        
                
                
        private synchronized  void   processPrincipalPayment(double totalPrincipalPaid,List credit,String debitedBorrowerAccount){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "01-1280001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Payment from"+" "+dbq.AccountName(debitedBorrowerAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalPrincipalPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedBorrowerAccount));
        credits.add(7, dbq.AccountName(debitedBorrowerAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//          List newInD=new ArrayList();
//        newInD.add("PrincimpalPayment");
//          newInD.add(totalPrincipalPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);

        }

                
        private synchronized  void   processPrincipalPaymentRenewed(double totalPrincipalPaid,List credit,String debitedBorrowerAccount){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "01-1280002-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Loan Payment from"+" "+dbq.AccountName(debitedBorrowerAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalPrincipalPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedBorrowerAccount));
        credits.add(7, dbq.AccountName(debitedBorrowerAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//          List newInD=new ArrayList();
//        newInD.add("PrincimpalPayment");
//          newInD.add(totalPrincipalPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);

        }

        
        private synchronized  void   processPrincipalPaymentWrittenOff(double totalPrincipalPaid,List credit,String debitedBorrowerAccount){

        //Credit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Credit Amount:5
        //Debit Account Number:6
        //Debit Account Name:7
        //Credit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        List  credits = new ArrayList();  
        credits.add(0, "03-3260001-10");//Credit Account Number:0
        credits.add(1, credit.get(1));//Transaction Date:1
        credits.add(2, "Written Off Principal Recovered from"+" "+dbq.AccountName(debitedBorrowerAccount)+"'s"+" "+"  "+"loan repayment.");
        credits.add(3, "Paid on"+" "+credit.get(1));
        credits.add(4, credit.get(1));
        credits.add(5, totalPrincipalPaid+"");
        credits.add(6, fmt.putSeparatorsOnNormaAccount(debitedBorrowerAccount));
        credits.add(7, dbq.AccountName(debitedBorrowerAccount));
        credits.add(8, "asms0001");
        credits.add(9, credit.get(9).toString());
        credits.add(10, credit.get(10).toString());
        credits.add(11, dbq.getTransactionSequenceNumber()+"");


        dbq.credit(credits,c);
//          List newInD=new ArrayList();
//        newInD.add("PrincimpalPayment");
//          newInD.add(totalPrincipalPaid+"");
//          newInD.add(credit.get(9).toString());
//        dbq.updateTheInterest(newInD,c);

        }




//
//        public boolean accrueInterestActually(List details){
//
//        
//        }






        public synchronized void processLoanArrears( List processingDetails,Component c){
        int z=0;

        // processingDetails.add(accountNumber);//Account Number
        //  processingDetails.add(updateType);//Charge type, percentage, fraction or whole
        //   processingDetails.add("RecycleCompoundCharges");//chargeOnce,Recyle or compound

        List instalmentsInEarries=loan.instalmentsDueForPenalty(processingDetails.get(0).toString(),processingDetails.get(2).toString(),c);//This returns a list of all instalments in Arreas

//JOptionPane.showMessageDialog(c, instalmentsInEarries.get(z).toString());

        if(!instalmentsInEarries.get(z).toString().equalsIgnoreCase("9999")){

        while(z<=instalmentsInEarries.size()-1){
            
        String instalmentStartDate=loan.theInstalmentStartDate(processingDetails.get(0).toString(),processingDetails.get(2).toString(),instalmentsInEarries.get(z).toString());
//JOptionPane.showMessageDialog(c, instalmentStartDate);
        //   fios.stringFileWriter(fios.createFileName("test", "testit", instalmentsInEarries.get(z).toString()+"trdyr.txt"),instalmentStartDate);
        List transactionDetails=new ArrayList();
        transactionDetails.add(processingDetails.get(0));//Account Number
        transactionDetails.add(processingDetails.get(1));//Charge type, percentage, fraction or whole
        transactionDetails.add(processingDetails.get(2));///chargeOnce,Recyle or compound
        transactionDetails.add(instalmentsInEarries.get(z).toString());///Instalment Number
        transactionDetails.add(instalmentStartDate);///Instalment start date
                    SwingWorker<Void,Void> trnsactionThread = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {

        processLoanUpdateArrears(transactionDetails,c);   

             return null;
                    }};
                    
                    trnsactionThread.execute();
                    
                  try {
                      wait(5000);
                  } catch (InterruptedException ex) {
                      Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
                  }

        z++;
        }

        }

        }



        public synchronized void processLoanUpdateArrears( List transactionDetails,Component c){

        //0transactionDetails.add(processingDetails.get(0));//Account Number
        //1transactionDetails.add(processingDetails.get(1));//Charge type, percentage, fraction or whole
        //2transactionDetails.add(processingDetails.get(2));///chargeOnce,Recyle or compound
        //3transactionDetails.add(instalmentsInEarries.get(z).toString());///Instalment Number
        //4transactionDetails.add(instalmentStartDate);///Instalment start date
            
        fios.forceFileExistanceZero(fios.createFileName("persistence", "loanArrears", "charges.txt"));
//        JOptionPane.showMessageDialog(c, transactionDetails.get(0).toString());
        String accountNumber=transactionDetails.get(0).toString();

        String instalmentNumber=transactionDetails.get(3).toString();

        String accrualType=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt"));
        
             Double charge=0.0,intmCharge=0.0;   List futherDetails=new ArrayList();
        switch(transactionDetails.get(1).toString()){

        case   "instalmentAsPercentage": 

       

        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges.txt"))))/100)*loan.theRemainingInstalmentNow(accountNumber,parseInt(instalmentNumber));
      charge=this.chargeActual(intmCharge, accountNumber, c);
//      JOptionPane.showMessageDialog(c, charge+"");
        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);

        break;
        case  "instalmentAsFraction":
 

        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges1.txt")))))*loan.theRemainingInstalmentNow(accountNumber,parseInt(instalmentNumber));
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        break;
        case   "instalmentAsFixedFigure":

        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges2.txt")))));
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        break;
        case  "princepalAsPercentage":


        intmCharge=( (parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges.txt"))))/100)*loan.currentLoanPrincipalNow(accountNumber,parseInt(instalmentNumber)); 
      charge=this.chargeActual(intmCharge, accountNumber, c);
//      JOptionPane.showMessageDialog(c, charge+"");
        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
    
        break;
        case   "princepalAsFraction":



        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges1.txt")))))*loan.currentLoanPrincipalNow(accountNumber,parseInt(instalmentNumber));
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);

        break;
        case   "princepalAsFixedFigure":
        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges2.txt")))));
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        break;
        case   "interestAsPercentage":


        intmCharge=( (parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges.txt"))))/100)*loan.currentInterestRemainNow(accountNumber,parseInt(instalmentNumber)); 
      charge=this.chargeActual(intmCharge, accountNumber, c);
//      JOptionPane.showMessageDialog(c, charge+"");
        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);

        break;
        case   "interestAsFraction":


        intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges1.txt")))))*loan.currentInterestRemainNow(accountNumber,parseInt(instalmentNumber));
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        break;
        case   "interestAsFixedFigure":
       
            intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges2.txt")))));
     
       charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        break;
        case    "loanBalanceAsPercentage":



        intmCharge=( (parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges.txt"))))/100)*loan.totalInstalmentRemainingNow(accountNumber); 
      charge=this.chargeActual(intmCharge, accountNumber, c);
//      JOptionPane.showMessageDialog(c, charge+"");
        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
       

        break;
        case    "loanBalanceAsFraction":

  intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges1.txt")))))*loan.totalInstalmentRemainingNow(accountNumber);
      charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);
        
        break;
        case    "loanBalanceAsFixedFigure":
   intmCharge=((parseDouble( fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "charges2.txt")))));
     
       charge=this.chargeActual(intmCharge, accountNumber, c);

        futherDetails.add(accountNumber);//Account number:0
        futherDetails.add(instalmentNumber);//Instalment Number:1
        futherDetails.add(charge);//charge:2
        futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        futherDetails.add("Cash");//Accounting system used:5
        updatePenaltyDetails(futherDetails,c);


        break;
        }

        } 
      

        public synchronized void updatePenaltyDetails(List futherDetails ,Component c){
        //     futherDetails.add(accountNumber);//Account number:0
        //         futherDetails.add(instalmentNumber);//Instalment Number:1
        //            futherDetails.add(charge);//charge:2
        //             futherDetails.add(accrualType);//Accrual type:daily,weekly,monthly etc:3
        //               futherDetails.add(transactionDetails.get(4));//InstalmentStartDate:4
        //               futherDetails.add("Cash");//Accounting system used:5

        List finalUpdate=new ArrayList();
        
        List finalUpdateOther=new ArrayList();
        

        String    instalmentNextDueDate="",instalmentEndDate="",recoveredNumberInstalments="";
        
       
        instalmentNextDueDate=futherDetails.get(4).toString();
       recoveredNumberInstalments=this.accrualPeriod(futherDetails.get(0).toString(), c);
//       JOptionPane.showMessageDialog(c, instalmentNextDueDate+";"+recoveredNumberInstalments);
        try {

        instalmentEndDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(instalmentNextDueDate),recoveredNumberInstalments ));//Instalment nextDue date:4 YYYY-MM-DD:8

        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }   
        double chargeSize=(parseDouble(futherDetails.get(2).toString())*parseInt(recoveredNumberInstalments));



        finalUpdate.add(futherDetails.get(1).toString());//Instalment Number:0  
        finalUpdate.add(futherDetails.get(0).toString());//Account number:1
        finalUpdate.add(futherDetails.get(4).toString());//Recover start date:3 YYYY-MM-DD:2
        finalUpdate.add(instalmentNextDueDate);//Instalment Start date:4 YYYY-MM-DD:3
        finalUpdate.add(instalmentEndDate);//Recover end date:5 YYYY-MM-DD:4
        finalUpdate.add(recoveredNumberInstalments);//Recover Number of Instalments:5
        finalUpdate.add(recoveredNumberInstalments);//Recover Remaining Instalments:6    
        finalUpdate.add("0");//Recovered Instalments:7  
        finalUpdate.add(instalmentNextDueDate);//Instalment nextDue date:4 YYYY-MM-DD:8
        finalUpdate.add(chargeSize);//Recover charge:9
        finalUpdate.add(futherDetails.get(2).toString());//Recover charge remaining:10
        finalUpdate.add("0.0");//Recover charge recovered:11
        finalUpdate.add("NCO");//Recover status1:12//NCO-Not yet Recovered
        finalUpdate.add(futherDetails.get(5).toString());//Recover method:13
        finalUpdate.add(parseDouble(futherDetails.get(2).toString()));//Recover instalment size:14
        finalUpdateOther.add(futherDetails.get(0).toString());//Account number:0
        finalUpdateOther.add(futherDetails.get(1).toString());//Instalment Number:1
        finalUpdateOther.add("NP");//Instalment Status:2


                          SwingWorker<Void,Void> createNewChargeToRecover = new SwingWorker() {
                            @Override
                            protected Object doInBackground() throws Exception {


        loan.createNewChargeToRecover(finalUpdate);

                                wait(5000);
                                return null;
                                
                            }
                        };
                        
                        
                        createNewChargeToRecover.execute();

                         
                   SwingWorker<Void,Void> exclusiveUpdateOfLoan = new SwingWorker() {
                            @Override
                            protected Object doInBackground() throws Exception {


        loan.exclusiveUpdateOfLoan(finalUpdateOther);

                                wait(5000);
                                return null;
                                
                            }
                        };
                        
                        
                        exclusiveUpdateOfLoan.execute();



        


        } 



        public synchronized void processFinalLoansInArrears(List theFinalDetails,String Id,Component c){


        //        details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0/
        //       details.add(rst3.getString("AccountNumber"));//Account Number:1/
        //        details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
        //       details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
        //       details.add(rst3.getString("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
        //       details.add(rst3.getString("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
        //       details.add(rst3.getString("RecoverCharge"));//Recover Charge Amount:6
        //       details.add(rst3.getString("RecoverChargeRemaining"));//Recover remaining charge Amount:7
        //       details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8/
        //       details.add(rst3.getString("RecoveryInstalmentSize"));//Recover Instalment size used:9

        long numberOfDays=1L,instalmentEndDate=1L,nextInstalmentDueDate=1L; String instalmentStartDate="",instalmentAfterDate="";
int recoverRemainingTimes=0,recoveredTimes=0;
      
String theInstalmentNumber=theFinalDetails.get(0).toString();

        String accountNumber=theFinalDetails.get(1).toString();

       
        
         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(theFinalDetails.get(2).toString());//dd/mm/yyyy

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDate);

// JOptionPane.showMessageDialog(c, instalmentStartDate+"\n"+numberOfDays);
 
//        recoverRemainingTimes=parseInt(theFinalDetails.get(5).toString())-parseInt(this.accrualPeriod(accountNumber, c));  
//
        recoveredTimes=parseInt(loan.reductionValue(accountNumber, c));
//     JOptionPane.showMessageDialog(c, recoveredTimes);
        try {
            
        instalmentEndDate = sdfS.parse(theFinalDetails.get(3).toString()).getTime();
        
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(theFinalDetails.get(2).toString()), loan.reductionValue(accountNumber, c)));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
   try {
        nextInstalmentDueDate = sdfS.parse(fmt.forDatabaseWithFullYearBeginningWithDate(instalmentStartDate)).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        while(numberOfDays>=0){
        
        List loanPenaltyDetails=new ArrayList();
        
        loanPenaltyDetails.add(Id);//Instalment Id:0
        loanPenaltyDetails.add(theInstalmentNumber);//instalment number:1
        loanPenaltyDetails.add(accountNumber);//Account number:2
        loanPenaltyDetails.add(theFinalDetails.get(2).toString());//instalment due date:3
        loanPenaltyDetails.add( theFinalDetails.get(3).toString());//Instalment End date:4
        loanPenaltyDetails.add(theFinalDetails.get(4).toString());//Recover Nnumber of Instalments:5
        loanPenaltyDetails.add(theFinalDetails.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
        loanPenaltyDetails.add(theFinalDetails.get(6).toString());//Recover Charge Amount:7
        loanPenaltyDetails.add(theFinalDetails.get(7).toString());//Recover remaining charge Amount:8
        loanPenaltyDetails.add(theFinalDetails.get(9).toString());//Recover Instalment size used:9
           
         
  try {
        nextInstalmentDueDate = sdfS.parse(fmt.forDatabaseWithFullYearBeginningWithDate(instalmentStartDate)).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
  
//  JOptionPane.showMessageDialog(c, recoveredTimes+";"+parseInt(theFinalDetails.get(4).toString()));
    if(recoveredTimes>=parseInt(theFinalDetails.get(4).toString())){
//        ||nextInstalmentDueDate>=instalmentEndDate
      loanPenaltyDetails.add("CO");//Recover Instalment size used:9
//        JOptionPane.showMessageDialog(c, "CO");
         loan.updateLoanScheduleLoanPenalty(loanPenaltyDetails,c);
        break;
        } else{
    loanPenaltyDetails.add("NCO");//Recover Instalment size used:9
//     JOptionPane.showMessageDialog(c, "NCO");
    }
        
        
//        SwingWorker<Void,Void> accrueInterest;
//        accrueInterest = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
        loan.updateLoanScheduleLoanPenalty(loanPenaltyDetails,c);
//        wait(5000);
//        return null;
//
//        }
//        };
//
//
//        accrueInterest.execute();
//        try {
//        wait(5000);
//        } catch (InterruptedException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //
        
       
        
     try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), loan.reductionValue(accountNumber, c)));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(System.currentTimeMillis( )),instalmentStartDate);
        
         recoveredTimes=recoveredTimes+parseInt(loan.reductionValue(accountNumber, c));
      
        }
        
    
        
        }

//        public synchronized void processFinalRecoverChargesAccrual(List finalDetails){
//        //    fios.stringFileWriter(fios.createFileName("test", "testit",finalDetails.get(0).toString()+"trdyr"+finalDetails.get(1).toString()+".txt"),finalDetails.get(2).toString()+"gg");           
//        //        loanPenaltyDetails.add(Id);//Instalment Id:0
//        //
//        //            loanPenaltyDetails.add(theInstalmentNumber);//instalment number:1
//        //
//        //            loanPenaltyDetails.add(accountNumber);//Account number:2
//        //
//        //            loanPenaltyDetails.add( instalmentStartDate);//Next instalment due date:3
//        //
//        //            loanPenaltyDetails.add( theFinalDetails.get(3).toString());//Instalment End date:4
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(4).toString());//Recover Nnumber of Instalments:5
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(5).toString());//Recover Remaining instalments:6
//        //
//        //
//        //            loanPenaltyDetails.add(amountTobeRecovered);//Recover Charge Amount:7
//        //
//        //            loanPenaltyDetails.add(remainingTotalAmount);//Recover remaining charge Amount:8
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(9).toString());//Recover Instalment size used:9
//
//        int recoveredTimes=0,recoverRemainingTimes=0;
//
//
//        long   nextInstalmentDueDate=1L,instalmentEndDate=1L;String instalmentAfterDate="";
//
//        switch(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt"))){
//        case "Daily":
//        recoverRemainingTimes=parseInt(finalDetails.get(6).toString())-1;  
//
//        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingTimes;
//        try {
//        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "1"));
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        List postPenaltyDetails=new ArrayList();
//
//        postPenaltyDetails.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails.add("0.0");//Recover remaining charge Amount:6
//        postPenaltyDetails.add(finalDetails.get(8).toString());//Recover Instalment size used:7
//        postPenaltyDetails.add("CO");//Accrual status:8
//        postPenaltyDetails.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails);
//
//        }else{
//        List postPenaltyDetails1=new ArrayList();
//
//        postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        postPenaltyDetails1.add("NCO");//Accrual status:8
//        postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails1);
//
//
//        }
//
//
//
//        break;
//        case "Weekily":
//
//        recoverRemainingTimes=parseInt(finalDetails.get(6).toString())-8;  
//
//        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingTimes;
//        try {
//        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "7"));
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        List postPenaltyDetails=new ArrayList();
//
//        postPenaltyDetails.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails.add("0.0");//Recover remaining charge Amount:6
//        postPenaltyDetails.add(finalDetails.get(8).toString());//Recover Remaining instalment amount:7
//        postPenaltyDetails.add("CO");//Accrual status:8
//        postPenaltyDetails.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails);
//
//        }else{
//        List postPenaltyDetails1=new ArrayList();
//
//        postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        postPenaltyDetails1.add("NCO");//Accrual status:8
//        postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails1);
//
//
//        }  
//
//        break;
//        case "Fortnightly":
//            
//        recoverRemainingTimes=parseInt(finalDetails.get(6).toString())-15;  
//
//        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingTimes;
//        try {
//        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "7"));
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        List postPenaltyDetails=new ArrayList();
//
//        postPenaltyDetails.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails.add("0.0");//Recover remaining charge Amount:6
//        postPenaltyDetails.add(finalDetails.get(8).toString());//Recover Instalment size used:7
//        postPenaltyDetails.add("CO");//Accrual status:8
//        postPenaltyDetails.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails);
//
//        }else{
//        List postPenaltyDetails1=new ArrayList();
//
//        postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        postPenaltyDetails1.add("NCO");//Accrual status:8
//        postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails1);
//
//
//        }  
//
//        break;
//        case "Monthly":
//            
//        recoverRemainingTimes=parseInt(finalDetails.get(6).toString())-30;  
//
//        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingTimes;
//        try {
//        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "7"));
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        List postPenaltyDetails=new ArrayList();
//
//        postPenaltyDetails.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails.add("0.0");//Recover remaining charge Amount:6
//        postPenaltyDetails.add(finalDetails.get(8).toString());//Recover Instalment size used:7
//        postPenaltyDetails.add("CO");//Accrual status:8
//        postPenaltyDetails.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails);
//
//        }else{
//        List postPenaltyDetails1=new ArrayList();
//
//        postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        postPenaltyDetails1.add("NCO");//Accrual status:8
//        postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        postAccruedPenalty(postPenaltyDetails1);
//
//
//        }  
//
//        break;
//
//
//
//        }
//
//
//
//
//        }
//        public synchronized void processFinalRecoverChargesCash(List finalDetails){
//
//        //        loanPenaltyDetails.add(Id);//Instalment Id:0
//        //
//        //            loanPenaltyDetails.add(theInstalmentNumber);//instalment number:1
//        //
//        //            loanPenaltyDetails.add(accountNumber);//Account number:2
//        //
//        //            loanPenaltyDetails.add( instalmentStartDate);//Next instalment due date:3
//        //
//        //            loanPenaltyDetails.add( theFinalDetails.get(3).toString());//Instalment End date:4
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(4).toString());//Recover Nnumber of Instalments:5
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(5).toString());//Recover Remaining instalments:6
//        //
//        //
//        //            loanPenaltyDetails.add(amountTobeRecovered);//Recover Charge Amount:7
//        //
//        //            loanPenaltyDetails.add(remainingTotalAmount);//Recover remaining charge Amount:8
//        //
//        //            loanPenaltyDetails.add(theFinalDetails.get(9).toString());//Recover Instalment size used:9
//
//        int recoveredTimes=parseInt(finalDetails.get(4).toString())-parseInt(finalDetails.get(6).toString());
//
//        int recoverRemainingTimes=parseInt(finalDetails.get(6).toString())-1;
//
//        long   nextInstalmentDueDate=1L,instalmentEndDate=1L;String instalmentAfterDate="";
//
//
//        try {
//        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "1"));
//        } catch (ParseException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        List postPenaltyDetails=new ArrayList();
//
//        postPenaltyDetails.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails.add("0.0");//Recover remaining charge Amount:6
//        postPenaltyDetails.add(finalDetails.get(8).toString());//Recover Instalment size used:7
//        postPenaltyDetails.add("CO");//Accrual status:8
//        postPenaltyDetails.add(recoveredTimes);//Recovered Times:9
//        postCashPenalty(postPenaltyDetails);
//
//        }else{
//        List postPenaltyDetails1=new ArrayList();
//
//        postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        postPenaltyDetails1.add("NCO");//Accrual status:8
//        postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        postCashPenalty(postPenaltyDetails1);
//
//
//        }
//
//
//
//
//
//        }

//        public synchronized void postAccruedPenalty(List postingDetails){
//
//
//        //Debit Account Number:0
//        //Transaction Date:1
//        //Narrative 1:2
//        //Narrative 2:3
//        //Value Date:4
//        //Debit Amount:5
//        //Credit Account Number:6
//        //Credit Account Name:7
//        //Debit Reference Number:8
//        //Batch Number:9
//        //Transaction Type:10
//        //Transaction Sequence Number:11
//        String accountNumber=postingDetails.get(2).toString();
//
//        String BatchNumber= batchCode();
//        List debit1= new ArrayList();List credit= new ArrayList();
//        //Borrower account number:0
//        //instalment number:1
//        //Date instalment Accrued:2
//        //Amount Accrued:3
//        debit1 .add(0,  "01-1350001-10");
//        debit1 .add(1, sdf.format(fmt.getTodayDate()));
//        debit1.add(2, "Loan Penalty Receivable from"+" "+dbq.AccountName(accountNumber));
//        debit1 .add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
//        debit1 .add(4, sdf.format(fmt.getTodayDate()));
//        debit1 .add(5,  postingDetails.get(6).toString());
//        debit1 .add(6, "03-3120001-10");
//        debit1 .add(7, dbq.AccountName("03312000110"));
//        debit1 .add(8, "000zib");
//        debit1 .add(9, BatchNumber);
//        debit1 .add(10, "System");
//        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");
//
//
//        credit.add(0, "03-3120001-10");
//        credit.add(1, sdf.format(fmt.getTodayDate()));
//        credit.add(2, "Accumulated Interest Receivable from"+" "+dbq.AccountName(accountNumber));
//        credit.add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
//        credit.add(4, sdf.format(fmt.getTodayDate()));
//        credit.add(5, postingDetails.get(6).toString());
//        credit.add(6, "01-1350001-10");
//        credit.add(7, dbq.AccountName("01135000110"));
//        credit.add(8, "000zib");
//        credit.add(9, BatchNumber);
//        credit.add(10, "System");
//        credit.add(11, dbq.getTransactionSequenceNumber()+"");
//
//        try {
//        wait(5000);
//        } catch (InterruptedException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        generalPosting(debit1, credit);
//        return null;
//        }};
//
//        postTheEntry.execute();
//
//        //       postPenaltyDetails1.add(finalDetails.get(0).toString());//Instalment Id:0
//        //           postPenaltyDetails1.add(finalDetails.get(1).toString());//instalment number:1
//        //           postPenaltyDetails1.add(finalDetails.get(2).toString());//Account number:2
//        //             postPenaltyDetails1.add(instalmentAfterDate);//Next instalment due date:3
//        //           postPenaltyDetails1.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        //           postPenaltyDetails1.add(recoverRemainingTimes);//Recover Remaining instalments:5
//        //            postPenaltyDetails1.add(finalDetails.get(8).toString());//Recover remaining charge Amount:6
//        //            postPenaltyDetails1.add(finalDetails.get(9).toString());//Recover Instalment size used:7
//        //            postPenaltyDetails1.add("NCO");//Accrual status:8 
//        //postPenaltyDetails1.add(recoveredTimes);//Recovered Times:9
//        double remainingChargeAmount=parseDouble(postingDetails.get(6).toString())-parseDouble(postingDetails.get(7).toString());
//
//        List LoanDetailsForLoanPenaltySchedule = new ArrayList();
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(0).toString()); //Instalment Id:0
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(1).toString());//instalment number:1
//
//        LoanDetailsForLoanPenaltySchedule.add(accountNumber);//Account number:2
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(3).toString());//Next instalment due date:3
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(4).toString());//Recover Instalments:4
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(5).toString());//Recover Remaining instalments:5
//
//        LoanDetailsForLoanPenaltySchedule.add(remainingChargeAmount);///Recover remaining charge Amount:6
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(6).toString());///Recover remaining charge Amount:7
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(8).toString());//Accrual status:8
//
//        LoanDetailsForLoanPenaltySchedule.add(postingDetails.get(9).toString());//Recovered Times:9
//        try {
//        wait(5000);
//        } catch (InterruptedException ex) {
//        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        SwingWorker<Void,Void> updateLoanScheduleLoanPenaltyx = new SwingWorker() {
//        @Override
//        protected Object doInBackground() throws Exception {
//
//        loan.updateLoanScheduleLoanPenalty(LoanDetailsForLoanPenaltySchedule);
//
//
//        return null;
//        }};
//
//        updateLoanScheduleLoanPenaltyx.execute();
//
//
//
//        }
        public synchronized void postCashPenalty(List postingDetails){





        }


        public synchronized void processFinalAccumulatedInterest(List theFinalDetailsAccumulate,String Id){

        ////   details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0
        ////details.add(rst3.getString("AccountNumber"));//Account Number:1
        ////  details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
        ////details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
        ////details.add(rst3.getInt("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
        ////details.add(rst3.getInt("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
        ////details.add(rst3.getDouble("TotalAccumulatedInterest"));//Recover Accumulated Interest Amount:6
        ////details.add(rst3.getDouble("AccumulatedInterestRemaining"));//Recover remaining Accumulated Interest Amount:7
        ////details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8
        ////details.add(rst3.getDouble("RecoveryInstalmentSize"));//Recover Instalment size used:9



        String theInstalmentNumber=theFinalDetailsAccumulate.get(0).toString();

        String accountNumber=theFinalDetailsAccumulate.get(1).toString();




        switch(theFinalDetailsAccumulate.get(8).toString()){  //Decide on the accumulation method which is either cash based or accruels

        case "Accrual"://If the method is accruels, proceed for further processing

        List accumulatedInterestD=new ArrayList();
        accumulatedInterestD.add(Id);//Instalment Id:0
        accumulatedInterestD.add(theInstalmentNumber);//instalment number:1
        accumulatedInterestD.add(accountNumber);//Account number:2
        accumulatedInterestD.add( theFinalDetailsAccumulate.get(2).toString());//Next instalment due date:3
        accumulatedInterestD.add( theFinalDetailsAccumulate.get(3).toString());//Instalment End date:4
        accumulatedInterestD.add(theFinalDetailsAccumulate.get(4).toString());//Recover Nnumber of Instalments:5
        accumulatedInterestD.add(theFinalDetailsAccumulate.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
        accumulatedInterestD.add(theFinalDetailsAccumulate.get(6).toString());//Recover Accumulated Interest Amount:7
        accumulatedInterestD.add(theFinalDetailsAccumulate.get(7).toString());//Recover remaining Accumulated Interest Amount:8
        accumulatedInterestD.add(theFinalDetailsAccumulate.get(9).toString());//Recover Instalment size used:9

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        processFinaAccumulatedInterestAccrual(accumulatedInterestD);
        //                        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();

        break;

        case "Cash":
        List accumulatedInterestC=new ArrayList();
        accumulatedInterestC.add(Id);//Instalment Id:0
        accumulatedInterestC.add(theInstalmentNumber);//instalment number:1
        accumulatedInterestC.add(accountNumber);//Account number:2
        accumulatedInterestC.add( theFinalDetailsAccumulate.get(2).toString());//Next instalment due date:3
        accumulatedInterestC.add( theFinalDetailsAccumulate.get(3).toString());//Instalment End date:4
        accumulatedInterestC.add(theFinalDetailsAccumulate.get(4).toString());//Recover Nnumber of Instalments:5
        accumulatedInterestC.add(theFinalDetailsAccumulate.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
        accumulatedInterestC.add(theFinalDetailsAccumulate.get(6).toString());//Recover Accumulated Interest Amount:7
        accumulatedInterestC.add(theFinalDetailsAccumulate.get(7).toString());//Recover remaining Accumulated Interest Amount:8
        accumulatedInterestC.add(theFinalDetailsAccumulate.get(9).toString());//Recover Instalment size used:9

        SwingWorker<Void,Void> accrueInterestC;
        accrueInterestC = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        processFinalRecoverAccumulatedInterestCash(accumulatedInterestC);
        //                        wait(5000);
        return null;

        }
        };


        accrueInterestC.execute();


        break;
        }

        }

        
        
        
        
        
        public synchronized void processFinaAccumulatedInterestAccrual(List finalDetails){

        //    accumulatedInterestD.add(Id);//Instalment Id:0
        //    accumulatedInterestD.add(theInstalmentNumber);//instalment number:1
        //    accumulatedInterestD.add(accountNumber);//Account number:2
        //    accumulatedInterestD.add( theFinalDetailsAccumulate.get(2).toString());//Next instalment due date:3
        //    accumulatedInterestD.add( theFinalDetailsAccumulate.get(3).toString());//Instalment End date:4
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(4).toString());//Recover Nnumber of Instalments:5
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(6).toString());//Recover Accumulated Interest Amount:6
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(7).toString());//Recover remaining Accumulated Interest Amount:7
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(9).toString());//Recover Instalment size used:9

        long   nextInstalmentDueDate=1L,  instalmentEndDate=1L,numberOfDays=1L;  
        String instalmentAfterDate="",instalmentStartDate="";  
        int recoveredTimes=0,recoverRemainingInstalments=0;

        double instalmentSize=parseDouble(finalDetails.get(9).toString());

        double remainingAmount=parseDouble(finalDetails.get(8).toString());


       

        switch(fios.stringFileReader(fios.createFileName("persistence", "useCRB", "frequencyOfInterestAccrual.txt"))){

        case "Daily": 


        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){


   List accumulatedInterest=new ArrayList();

        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "1"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
//         fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=28||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//        break;
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10

//fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), instalmentStartDate);

//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postAccruedAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "1"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-1; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Weekily":


        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

 List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "8"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());
//
//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postAccruedAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "8"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-8; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Fortnightly":

        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

 List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "15"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postAccruedAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "15"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-15; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Monthly":

        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

 List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "30"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postAccruedAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "30"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-30; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;

        } }

        
        
        
        

        public synchronized void processFinalRecoverAccumulatedInterestCash(List finalDetails){
        //    accumulatedInterestD.add(Id);//Instalment Id:0
        //    accumulatedInterestD.add(theInstalmentNumber);//instalment number:1
        //    accumulatedInterestD.add(accountNumber);//Account number:2
        //    accumulatedInterestD.add( theFinalDetailsAccumulate.get(2).toString());//Next instalment due date:3
        //    accumulatedInterestD.add( theFinalDetailsAccumulate.get(3).toString());//Instalment End date:4
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(4).toString());//Recover Nnumber of Instalments:5
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(6).toString());//Recover Accumulated Interest Amount:6
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(7).toString());//Recover remaining Accumulated Interest Amount:7
        //    accumulatedInterestD.add(theFinalDetailsAccumulate.get(9).toString());//Recover Instalment size used:9

        long   nextInstalmentDueDate=1L,  instalmentEndDate=1L,numberOfDays=1L;  
        String instalmentAfterDate="",instalmentStartDate="";  
        int recoveredTimes=0,recoverRemainingInstalments=0;

        double instalmentSize=parseDouble(finalDetails.get(9).toString());

        double remainingAmount=parseDouble(finalDetails.get(8).toString());


        

        switch(fios.stringFileReader(fios.createFileName("persistence", "useCRB", "frequencyOfInterestAccrual.txt"))){

        case "Daily": 


        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "1"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postCashAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "1"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-1; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Weekily":


        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "8"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postCashAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "8"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-8; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Fortnightly":

        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "15"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postCashAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "15"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-15; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;
        case "Monthly":

        instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment



        while(numberOfDays>=0){

List accumulatedInterest=new ArrayList();


        try {
        nextInstalmentDueDate = sdfS.parse(finalDetails.get(3).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        instalmentEndDate = sdfS.parse(finalDetails.get(4).toString()).getTime();
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentAfterDate=sdfS.format(fmt.getExDateIncreamented(sdfS.parse(finalDetails.get(3).toString()), "30"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // fios.stringFileWriter(fios.createFileName("test", "testit", numberOfDays+"statusy.txt"), "recoverRemainingInstalments:"+recoverRemainingInstalments+";"+"remainingAmount:"+remainingAmount+";"+"recoveredTimes:"+recoveredTimes+";"+"instalmentStartDate:"+instalmentStartDate+";"+"finalDetails.get(6).toString():"+finalDetails.get(6).toString());

//        if(recoveredTimes>=30||nextInstalmentDueDate>=instalmentEndDate){
//
//        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
//        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
//        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
//        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
//        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
//        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
//        accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
//        accumulatedInterest.add(remainingAmount);//Recover Instalment size used:7
//        accumulatedInterest.add("CO");//Accrual status:8
//        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
//        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10
//
//        }else{

        accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        accumulatedInterest.add(recoverRemainingInstalments);//Recover Remaining instalments:5
        accumulatedInterest.add(remainingAmount);//Recover remaining Accumulated Interest Amount:6
        accumulatedInterest.add(instalmentSize);//Recover Instalment size used:7
        accumulatedInterest.add("CO");//Accrual status:8
        accumulatedInterest.add(recoveredTimes);//Recovered Times:9
        accumulatedInterest.add(instalmentStartDate);//Accrual Date:10



//        }

        SwingWorker<Void,Void> accrueInterest;
        accrueInterest = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
        postCashAccumulatedInterest(accumulatedInterest);
        //        wait(5000);
        return null;

        }
        };


        accrueInterest.execute();
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        instalmentStartDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), "30"));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //         instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(3).toString());//dd/mm/yyyy:thr instalment start date be the instalment next due date

        numberOfDays=fmt.diffDays(fmt.dateConverterForNormalDate(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(finalDetails.get(4).toString()))),instalmentStartDate);//Pick the number of days since the last instalment




        recoverRemainingInstalments=parseInt(finalDetails.get(6).toString())-30; 


        recoveredTimes=parseInt(finalDetails.get(5).toString())-recoverRemainingInstalments;

        remainingAmount-=instalmentSize;
        }

        break;

        }
        }

        public synchronized void postAccruedAccumulatedInterest(List postingDetails){


        //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11
        String accountNumber=postingDetails.get(2).toString();

        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "01-1340001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Accumulated Interest Receivable from"+" "+dbq.AccountName(accountNumber)+"  Due on "+postingDetails.get(10).toString()+",");
        debit1 .add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  postingDetails.get(7).toString());
        debit1 .add(6, "03-3110001-10");
        debit1 .add(7, dbq.AccountName("03311000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "03-3110001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Accumulated Interest Receivable from"+" "+dbq.AccountName(accountNumber)+"  Due  0n "+postingDetails.get(10).toString()+",");
        credit.add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, postingDetails.get(7).toString());
        credit.add(6, "01-1340001-10");
        credit.add(7, dbq.AccountName("01134000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        generalPosting(debit1, credit);
        return null;
        }};

        postTheEntry.execute();
        // 
        //accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        //accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        //accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        //accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        //accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        //accumulatedInterest.add(recoverRemainingTimes);//Recover Remaining instalments:5
        //accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
        //accumulatedInterest.add(finalDetails.get(8).toString());//Recover Instalment size used:7
        //accumulatedInterest.add("CO");//Accrual status:8

        double remainingAccumulatedInterestAmount=parseDouble(postingDetails.get(6).toString())-parseDouble(postingDetails.get(7).toString());

        List detailsForAccumulatedSchedule = new ArrayList();

        detailsForAccumulatedSchedule.add(postingDetails.get(0).toString()); //Instalment Id:0

        detailsForAccumulatedSchedule.add(postingDetails.get(1).toString());//instalment number:1

        detailsForAccumulatedSchedule.add(accountNumber);//Account number:2

        detailsForAccumulatedSchedule.add(postingDetails.get(3).toString());//Next instalment due date:3

        detailsForAccumulatedSchedule.add(postingDetails.get(4).toString());//Recover Instalments:4

        detailsForAccumulatedSchedule.add(postingDetails.get(5).toString());//Recover Remaining instalments:5

        detailsForAccumulatedSchedule.add(remainingAccumulatedInterestAmount);///Recover remaining accumulated Interest Amount:6

        detailsForAccumulatedSchedule.add(postingDetails.get(7).toString());///Recover instalment size:7

        detailsForAccumulatedSchedule.add(postingDetails.get(8).toString());//Accrual status:8

        detailsForAccumulatedSchedule.add(postingDetails.get(9).toString());//Recovered Times:9
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingWorker<Void,Void> updateLoanScheduleAccumulatedInterestx = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        loan.updateLoanScheduleAccumulatedInterest(detailsForAccumulatedSchedule);


        return null;
        }};

        updateLoanScheduleAccumulatedInterestx.execute();

      

        }
       
        
        
        
        
        public synchronized void postCashAccumulatedInterest(List postingDetails){

        String accountNumber=postingDetails.get(2).toString();

        //accumulatedInterest.add(finalDetails.get(0).toString());//Instalment Id:0
        //accumulatedInterest.add(finalDetails.get(1).toString());//instalment number:1
        //accumulatedInterest.add(finalDetails.get(2).toString());//Account number:2
        //accumulatedInterest.add(instalmentAfterDate);//Next instalment due date:3
        //accumulatedInterest.add(finalDetails.get(5).toString());//Recover Number Instalmnets:4
        //accumulatedInterest.add(recoverRemainingTimes);//Recover Remaining instalments:5
        //accumulatedInterest.add("0.0");//Recover remaining charge Amount:6
        //accumulatedInterest.add(finalDetails.get(8).toString());//Recover Instalment size used:7
        //accumulatedInterest.add("CO");//Accrual status:8

        double remainingAccumulatedInterestAmount=parseDouble(postingDetails.get(6).toString())-parseDouble(postingDetails.get(7).toString());

        List detailsForAccumulatedSchedule = new ArrayList();

        detailsForAccumulatedSchedule.add(postingDetails.get(0).toString()); //Instalment Id:0

        detailsForAccumulatedSchedule.add(postingDetails.get(1).toString());//instalment number:1

        detailsForAccumulatedSchedule.add(accountNumber);//Account number:2

        detailsForAccumulatedSchedule.add(postingDetails.get(3).toString());//Next instalment due date:3

        detailsForAccumulatedSchedule.add(postingDetails.get(4).toString());//Recover Instalments:4

        detailsForAccumulatedSchedule.add(postingDetails.get(5).toString());//Recover Remaining instalments:5

        detailsForAccumulatedSchedule.add(remainingAccumulatedInterestAmount);///Recover remaining accumulated Interest Amount:6

        detailsForAccumulatedSchedule.add(postingDetails.get(7).toString());///Recover instalment size:7

        detailsForAccumulatedSchedule.add(postingDetails.get(8).toString());//Accrual status:8

        detailsForAccumulatedSchedule.add(postingDetails.get(9).toString());//Recovered Times:9
        try {
        wait(5000);
        } catch (InterruptedException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingWorker<Void,Void> updateLoanScheduleAccumulatedInterestx = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        loan.updateLoanScheduleAccumulatedInterest(detailsForAccumulatedSchedule);


        return null;
        }};

        updateLoanScheduleAccumulatedInterestx.execute();

        }
        
        
        
        
        
        
    public void reduceAmountProvisioned(double diff){
    //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "05-5090001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Reversal of Excess Provision for bad loans"+" "+",");
        debit1 .add(3, "Reversed on"+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  diff+"");
        debit1 .add(6, "02-2350001-10");
        debit1 .add(7, dbq.AccountName("02235000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "02-2350001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Reversal of Excess Provision for bad loans"+",");
        credit.add(3, "Recognized on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, diff+"");
        credit.add(6, "05-5090001-10");
        credit.add(7, dbq.AccountName("05509000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        generalPosting(debit1, credit);
        return null;
        }};

        postTheEntry.execute();
    
    }
        

    
      public void completeWriteOffDirect(String amountToWriteOff,String accountNumber){
    //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        String BatchNumber= batchCode();
        List debit1x= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1x .add(0,  "02-2400001-10");
        debit1x .add(1, sdf.format(fmt.getTodayDate()));
        debit1x.add(2, "Bad loan written off for"+" "+dbq.AccountName(accountNumber));
        debit1x .add(3, "Written off on"+" "+sdf.format(fmt.getTodayDate()));
        debit1x .add(4, sdf.format(fmt.getTodayDate()));
        debit1x .add(5,  amountToWriteOff+"");
        debit1x .add(6, "01-1280001-10");
        debit1x .add(7, dbq.AccountName("01128000110"));
        debit1x .add(8, "000zib");
        debit1x .add(9, BatchNumber);
        debit1x .add(10, "System");
        debit1x .add(11, dbq.getTransactionSequenceNumber()+"");

         List creditx= new ArrayList();
        creditx.add(0, "01-1280001-10");
        creditx.add(1, sdf.format(fmt.getTodayDate()));
        creditx.add(2, "Bad loan written off for"+" "+dbq.AccountName(accountNumber));
        creditx.add(3, "Written off on"+" "+sdf.format(fmt.getTodayDate()));
        creditx.add(4, sdf.format(fmt.getTodayDate()));
        creditx.add(5, amountToWriteOff+"");
        creditx.add(6, "02-2400001-10");
        creditx.add(7, dbq.AccountName("02240000110"));
        creditx.add(8, "000zib");
        creditx.add(9, BatchNumber);
        creditx.add(10, "System");
        creditx.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        generalPosting(debit1x, creditx);
        return null;
        }};

        postTheEntry.execute();
    
    }  
       public void completeWriteOffAccrual(String amountToWriteOff,String accountNumber){
    //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "05-5090001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Bad loan written off for"+" "+dbq.AccountName(accountNumber));
        debit1 .add(3, "Written off on"+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  amountToWriteOff+"");
        debit1 .add(6, "01-1280001-10");
        debit1 .add(7, dbq.AccountName("01128000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "01-1280001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Bad loan written off for"+" "+dbq.AccountName(accountNumber));
        credit.add(3, "Written off on"+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, amountToWriteOff+"");
        credit.add(6, "05-5090001-10");
        credit.add(7, dbq.AccountName("05509000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        generalPosting(debit1, credit);
        return null;
        }};

        postTheEntry.execute();
    
    }
     
            
      public void      increaseAmountProvisioned(double diff){
      //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "02-2350001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Provision for bad loans"+" "+",");
        debit1 .add(3, "Dated "+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  diff+"");
        debit1 .add(6, "05-5090001-10");
        debit1 .add(7, dbq.AccountName("05509000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "05-5090001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Provision for bad loans"+" "+",");
        credit .add(3, "Dated "+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, diff+"");
        credit.add(6, "02-2350001-10");
        credit.add(7, dbq.AccountName("02235000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {

        generalPosting(debit1, credit);
        return null;
        }};

        postTheEntry.execute();
    
      

      
      
      }  
        
      
        
        public String batchCode(){
        String batch="";int theNumber=0;


        fios.forceFileExistance(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));   

        String Thebatch= fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));
        theNumber=parseInt(Thebatch)+1;
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"), theNumber+"");
        batch="BTN"+(theNumber+"");
        return batch; 
        } 

private double chargeActual(double charge,String accountNumber,Component c){
double theCharge=0.0;
  String accrualType=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt"));
  String tenureType=loan.tenureType(accountNumber,c);

        switch(tenureType){
    case "DAYS":
    switch(accrualType){
    case "Daily":
       theCharge=  charge;
        break;
        case "Weekily":
           theCharge=  charge/7.5;   
            break;
            
            case "Fortnightly":
            theCharge=  charge/15; 
            break;
                
        case "Monthly":
            theCharge=  charge/30; 
            break;
        case "Quaterly":
                   theCharge=  charge/90; 
            
            break;
            
             case "Half Yearly":
            
                   theCharge=  charge/180; 
            break;
            
        case "Annually": 
                   theCharge=  charge/360; 
            break;
    case "Biennially": 
                   theCharge=  charge/720; 
            break;
    }
        break;
     case "WEEKS":
       switch(accrualType){
    case "Daily":
        theCharge=  charge; 
        break;
        case "Weekily":
             theCharge=  charge;
            break;
            
            case "Fortnightly":
             theCharge=  charge/2;
            break;
                
        case "Monthly":
             theCharge=  charge/4;
            break;
        case "Quaterly":
             theCharge=  charge/12;
            
            break;
            
             case "Half Yearly":
             theCharge=  charge/24;
            
            break;
            
        case "Annually": 
            theCharge=  charge/48; 
            break;
    case "Biennially": 
             theCharge=  charge/96;
            break;
    }
        break;
         case "FORTNIGHTS":
    switch(accrualType){
    case "Daily":
       theCharge=  charge;  
        break;
        case "Weekily":
            theCharge=  charge;   
            break;
            
            case "Fortnightly":
             theCharge=  charge;
            break;
                
        case "Monthly":
           theCharge=  charge/2;  
            break;
        case "Quaterly":
            theCharge=  charge/6;  
            
            break;
            
             case "Half Yearly":
               theCharge=  charge/12;  
            
            break;
            
        case "Annually": 
              theCharge=  charge/24;     
            break;
    case "Biennially": 
              theCharge=  charge/48; 
            break;
    }
        break;
         case "MONTHS":
   switch(accrualType){
    case "Daily":
         theCharge=  charge; 
        break;
        case "Weekily":
              theCharge=  charge; 
            break;
            
            case "Fortnightly":
              theCharge=  charge; 
            break;
                
        case "Monthly":
              theCharge=  charge; 
            break;
        case "Quaterly":
              theCharge=  charge/3; 
            
            break;
            
             case "Half Yearly":
             theCharge=  charge/6; 
            
            break;
            
        case "Annually": 
             theCharge=  charge/12; 
            break;
    case "Biennially": 
             theCharge=  charge/24; 
            break;
    }
        break;
         case "QUARTERS":
switch(accrualType){
    case "Daily":
          theCharge=  charge;
        break;
        case "Weekily":
                 theCharge=  charge;
            break;
            
            case "Fortnightly":
                 theCharge=  charge;
            break;
                
        case "Monthly":
                 theCharge=  charge;
            break;
        case "Quaterly":
            
                 theCharge=  charge;
            break;
            
             case "Half Yearly":
            
                 theCharge=  charge/2;
            break;
            
        case "Annually": 
                 theCharge=  charge/4;
            break;
    case "Biennially": 
            theCharge=  charge/8;
            break;
    }
        break;
         case "HALF YEARS":
switch(accrualType){
    case "Daily":
        theCharge=  charge;
        break;
        case "Weekily":
         theCharge=  charge;   
            break;
            
            case "Fortnightly":
            theCharge=  charge;
            break;
                
        case "Monthly":
           theCharge=  charge; 
            break;
        case "Quaterly":
            
            theCharge=  charge;
            break;
            
             case "Half Yearly":
            
            theCharge=  charge;
            break;
            
        case "Annually": 
            theCharge=  charge/2;
            break;
    case "Biennially": 
            theCharge=  charge/4;
            break;
    }
        break;
     case "YEARS":
switch(accrualType){
    case "Daily":
         theCharge=  charge;   
        break;
        case "Weekily":
                theCharge=  charge;
            break;
            
            case "Fortnightly":
                theCharge=  charge;
            break;
                
        case "Monthly":
                theCharge=  charge;
            break;
        case "Quaterly":
            
                theCharge=  charge;
            break;
            
             case "Half Yearly":
                theCharge=  charge;
            
            break;
            
        case "Annually": 
                theCharge=  charge;
            break;
    case "Biennially": 
                theCharge=  charge/2;
            break;
    }
           
        break;
         case "BIENNIALS":
switch(accrualType){
    case "Daily":
           theCharge=  charge; 
        break;
        case "Weekily":
                theCharge=  charge;
            break;
            
            case "Fortnightly":
                theCharge=  charge;
            break;
                
        case "Monthly":
                theCharge=  charge;
            break;
        case "Quaterly":
                theCharge=  charge;
            
            break;
            
             case "Half Yearly":
                theCharge=  charge;
            
            break;
            
        case "Annually": 
                theCharge=  charge;
            break;
    case "Biennially": 
                theCharge=  charge;
            break;
    }
         
        break;
        }
    

return theCharge;

}

private String accrualPeriod(String accountNumber,Component c){
String theCharge="";
  String accrualType=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt"));
  String tenureType=loan.tenureType(accountNumber,c);
//JOptionPane.showMessageDialog(c, accrualType);
//JOptionPane.showMessageDialog(c, tenureType);
        switch(tenureType){
    case "DAYS":
    switch(accrualType){
    case "Daily":
       theCharge=  "1";
        break;
        case "Weekily":
           theCharge=  "1";  
            break;
            
            case "Fortnightly":
           theCharge=  "1";
            break;
                
        case "Monthly":
         theCharge=  "1";
            break;
        case "Quaterly":
              theCharge=  "1";
            
            break;
            
             case "Half Yearly":
            
                theCharge=  "1";
            break;
            
        case "Annually": 
                theCharge=  "1";
            break;
    case "Biennially": 
           theCharge=  "1"; 
            break;
    }
        break;
     case "WEEKS":
       switch(accrualType){
    case "Daily":
        theCharge= "7"; 
        break;
        case "Weekily":
             theCharge= "1";
            break;
            
            case "Fortnightly":
               theCharge= "1";
            break;
                
        case "Monthly":
              theCharge= "1";
            break;
        case "Quaterly":
             theCharge= "1";
            
            break;
            
             case "Half Yearly":
           theCharge= "1";
            
            break;
            
        case "Annually": 
          theCharge= "1";
            break;
    case "Biennially": 
             theCharge= "1";
            break;
    }
        break;
         case "FORTNIGHTS":
    switch(accrualType){
    case "Daily":
       theCharge= "15"; 
        break;
        case "Weekily":
            theCharge= "2"; 
            break;
            
            case "Fortnightly":
             theCharge= "1";
            break;
                
        case "Monthly":
            theCharge= "1";  
            break;
        case "Quaterly":
             theCharge= "1";  
            
            break;
            
             case "Half Yearly":
               theCharge= "1"; 
            
            break;
            
        case "Annually": 
             theCharge= "1"; 
            break;
    case "Biennially": 
         theCharge= "1";
            break;
    }
        break;
         case "MONTHS":
   switch(accrualType){
    case "Daily":
       theCharge= "30";
        break;
        case "Weekily":
             theCharge= "4";
            break;
            
            case "Fortnightly":
              theCharge= "2";
            break;
                
        case "Monthly":
              theCharge= "1";
            break;
        case "Quaterly":
              theCharge= "1";
            
            break;
            
             case "Half Yearly":
              theCharge= "1"; 
            
            break;
            
        case "Annually": 
              theCharge= "1";
            break;
    case "Biennially": 
            theCharge= "1"; 
            break;
    }
        break;
         case "QUARTERS":
switch(accrualType){
    case "Daily":
       theCharge= "90";
        break;
        case "Weekily":
             theCharge= "12";
            break;
            
            case "Fortnightly":
                theCharge= "6";
            break;
                
        case "Monthly":
           theCharge= "3";
            break;
        case "Quaterly":
            
              theCharge= "1";
            break;
            
             case "Half Yearly":
            
                 theCharge= "1";
            break;
            
        case "Annually": 
                 theCharge= "1";
            break;
    case "Biennially": 
            theCharge= "1";
            break;
    }
        break;
         case "HALF YEARS":
switch(accrualType){
    case "Daily":
        theCharge=  "180";
        break;
        case "Weekily":
         theCharge=  "24";   
            break;
            
            case "Fortnightly":
            theCharge= "12";
            break;
                
        case "Monthly":
           theCharge=  "6";
            break;
        case "Quaterly":
            
            theCharge=  "2";
            break;
            
             case "Half Yearly":
            
            theCharge=  "1";
            break;
            
        case "Annually": 
            theCharge=  "1";
            break;
    case "Biennially": 
            theCharge=  "1";
            break;
    }
        break;
     case "YEARS":
switch(accrualType){
    case "Daily":
         theCharge= "360";   
        break;
        case "Weekily":
                theCharge= "48";
            break;
            
            case "Fortnightly":
                theCharge=  "24";
            break;
                
        case "Monthly":
                theCharge= "12";
            break;
        case "Quaterly":
            
                theCharge=  "4";
            break;
            
             case "Half Yearly":
                theCharge= "2";
            
            break;
            
        case "Annually": 
                theCharge= "1";
            break;
    case "Biennially": 
                theCharge= "1";
            break;
    }
           
        break;
         case "BIENNIALS":
switch(accrualType){
    case "Daily":
           theCharge= "720";
        break;
        case "Weekily":
                theCharge= "96";
            break;
            
            case "Fortnightly":
                theCharge= "48";
            break;
                
        case "Monthly":
                theCharge= "24";
            break;
        case "Quaterly":
                theCharge= "8";
            
            break;
            
             case "Half Yearly":
                theCharge= "4";
            
            break;
            
        case "Annually": 
                theCharge= "2";
            break;
    case "Biennially": 
                theCharge= "1";
            break;
    }
         
        break;
        }
    

return theCharge;

}


        }


