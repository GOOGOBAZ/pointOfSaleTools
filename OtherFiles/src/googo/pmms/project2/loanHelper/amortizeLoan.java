/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.loanHelper;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingMain;
import googo.pmms.project2.accountsHelper.SendSms;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author STAT SOLUTIONS
 */
public class amortizeLoan {
    
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
       
        DecimalFormat df1 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("#");
        DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
        Calendar cal = Calendar.getInstance();
        int n,s;
        ArrayList<Object> data4;

        
    SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
       CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
        
        ArrayList<String>  column1, data,data1 ;
  DatabaseQuaries dbq= new DatabaseQuaries();
        ArrayList<ArrayList<Object>> data5;
        double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0;
        Calendar startDate;
        ButtonGroup group1,group2,group3,group4;
        Formartter fmt = new Formartter();
           String userId;
           List amortDetails;
               SendSms sendsms= new SendSms();
    
    public amortizeLoan(String userId) {
            this.userId=userId;
    }
    
    
    public synchronized void createTheLoan(ObjectTableModel model, List AmortValues,List AmortValues1,Component c){

      Integer ty=35;  Double newAmount=0.0,differ1=0.0,differ2=0.0,lastInstalment=0.0, lastpInstalment=0.0,lastIInstalment=0.0,newInterest=0.0;
//   fios.stringFileWriter(fios.createFileName("test", "testit", fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]", 7)[0]+"trdyr.txt"),AmortValues.get(0).toString()+";"+AmortValues.get(1).toString()+";"+AmortValues.get(2).toString()+";"+AmortValues.get(3).toString()+";"+AmortValues.get(4).toString()+";"+AmortValues.get(5).toString());       
        if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))!=1&& fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))!=1){
 
        return;
        }else{
      
    fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]", 7)[0]+".txt"),ty.toString());
    fios.forceFileExistance(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"));//Stores a value which indicates the necessary amortization type, either as reducing interest or flat interest
    int windowhh =fios.intFileReader(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"));//Stores a value which indicates the necessary amortization type, either as reducing interest or flat interest

    switch(windowhh ){
   
    case 1:
 
  

    String amortz=   fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")); //Shows  the basic details of a loan
    
    fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "loanStore"+amortz.split("[,]", 7)[0] +".txt")); //Shows all the details of a loan

       data1 = new ArrayList();
    String var="";
    String count =""+getCounterl();
    String countLoans =count.substring(1, 5);
    
    for(int l=0;l<16;l++){

    if(l==0){

    var=model.getRowCount()+"";
    
    data1.add(l, model.getRowCount()+"");//Total Number of  Instalments:0
    }

    if(l==1){
  var=model.getValueAt(0, 2).toString();
    data1.add(l, model.getValueAt(0, 2).toString());//Total Principal Amount:1

    }
    if(l==2){
    var=model.getValueAt(0, 4).toString();
    data1.add(l, model.getValueAt(0, 4).toString());  //Total interest Amount:2  

    }
    if(l==3){
    var=model.getValueAt(0, 6).toString();
    
    data1.add(l, model.getValueAt(0, 6).toString());   //Total balance remaining Amount:3 

    }
    if(l==4){
        
    var=model.getValueAt(0, 8).toString();
    
    data1.add(l, model.getValueAt(0, 8).toString()); //Instalment start date:4    

    }
    if(l==5){
        
    var=model.getValueAt(model.getRowCount()-1, 8).toString();
    
    data1.add(l, model.getValueAt(model.getRowCount()-1, 8).toString());  //Instalment end date:5

    }

    if(l==6){

    var=model.getValueAt(0, 5).toString();
    
    data1.add(l, model.getValueAt(0, 5).toString());    //Instalment Amount:6


    }
    if(l==7){
        
    var=AmortValues.get(1).toString();
    
    data1.add(l, AmortValues.get(1).toString());   //Interest Rate:7

    }
    
    if(l==8){
        
    var=amortz.split("[,]", 7)[2]+" "+amortz.split("[,]", 7)[3];
    
    data1.add(l, amortz.split("[,]", 7)[2]+" "+amortz.split("[,]", 7)[3]);  // Loan Tenure :8
    
    }
    if(l==9){
        
    var="newloan"+amortz.split("[,]", 7)[0];
    
    data1.add(l, "newloan"+amortz.split("[,]", 7)[0]);   //Loan Id:9   

    }
    if(l==10){
        
    var=amortz.split("[,]", 7)[1];
    
    data1.add(l, amortz.split("[,]", 7)[1]);   //Applicant Account Name:10

    }
    if(l==11){

    var=amortz.split("[,]", 7)[0];
    
    data1.add(l, amortz.split("[,]", 7)[0]);  //Applicant Account Name:11
    
    }
    if(l==12){
        
    var="Applied";
    
    data1.add(l, "Applied");   //loan cycle status   :12  

    }
    if(l==13){
    //                var=fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt"));//Holds the value that indicates the active user of the person logged in
     var=this.userId;
    data1.add(l, this.userId); //Inputter id:13       

    }
    if(l==14){
        
    var="NL"+countLoans;
    
    data1.add(l, "NL"+countLoans);    // Transaction Id:14

    }   

    if(l==15){

    var=AmortValues.get(5).toString();

    data1.add(l, AmortValues.get(5).toString().split("\\s+")[2].trim());      //Portfolio owner  


    }  
   
    fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "loanStore"+amortz.split("[,]", 7)[0] +".txt"), var);
    
    fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "loanStore"+amortz.split("[,]", 7)[0] +".txt"), ",");     

    } 

    data1.add(AmortValues1.get(0).toString());

    data1.add(AmortValues1.get(1).toString());
    
       data1.add(amortz.split("[,]", 7)[5]);
       
          data1.add(amortz.split("[,]", 7)[6]);

    fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "remainingInstalements"+amortz.split("[,]", 7)[0] +".txt"), model.getRowCount()+"");            

   loan.insertLoanAppStore(data1,AmortValues1,AmortValues.get(3).toString(),c);
    

    Double InterestInstalment=parseDouble(model.getValueAt(0, 5).toString())-parseDouble(model.getValueAt(0, 1).toString());

    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "interestInstalment"+amortz.split("[,]", 7)[0]+ ".txt"), InterestInstalment.toString());//Interest instalment that will be used in the determination od the interest value during loan repayment in the loandatabase class
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "princimpalInstalment"+amortz.split("[,]", 7)[0]+ ".txt"), model.getValueAt(0, 1).toString());



    if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+amortz.split("[,]", 7)[0]+".txt"))==15){//Holds a value that shows whether the loan has been rejected or not. If the value held is 15, it means tha loan has been rejected



    Integer xl=2;
    fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());


    Integer xcu=1;
    fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","loandetails"+ amortz.split("[,]", 7)[0]+".txt"),xcu.toString());
    Integer xcup=1;
    fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedLoanDetails"+amortz.split("[,]", 7)[0]+".txt"),xcup.toString());
    Integer zx=2;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+amortz.split("[,]", 7)[0]+".txt"),zx.toString()); 

    Integer sdg=1;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+amortz.split("[,]", 7)[0]+".txt"), sdg.toString());   
    Integer xxy=3;

    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+amortz.split("[,]", 7)[0]+ ".txt"), xxy.toString());

    fios.stringFileWriter(fios.createFileName("loanAuthorisation", "loanDetails", "loanDetailsValues"+amortz.split("[,]", 7)[0]+".txt"), fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+amortz.split("[,]", 7)[0]+".txt")));
    }else{

    Integer xxy=2;

    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+amortz.split("[,]", 7)[0]+ ".txt"), xxy.toString());

    }
    
    
    
    
    
    
    
    
    int i=0,j=0; 

    while(i<model.getRowCount()){

    data = new ArrayList();
    j=0;
    while(j<model.getColumnCount()){
        

    
         data.add(j, model.getValueAt(i,j).toString());
        


    if(j==1){

    newAmount=newAmount+parseDouble(model.getValueAt(i,1).toString());

    }

     if(j==3){

    newInterest=newInterest+parseDouble(model.getValueAt(i,3).toString());
    

    }
    

    if(i==model.getRowCount()-1){

    differ1=parseDouble(model.getValueAt(0, 2).toString())-newAmount;
    
    differ2=parseDouble(model.getValueAt(0, 4).toString())-newInterest;
    
  

    lastpInstalment=parseDouble(model.getValueAt(model.getRowCount()-1, 1).toString())+ differ1;
    
    lastInstalment=parseDouble(model.getValueAt(model.getRowCount()-1, 5).toString())+ differ1;

    lastIInstalment=parseDouble(model.getValueAt(model.getRowCount()-1, 3).toString())+ differ2;
    
    lastInstalment=lastInstalment+ differ2;

    
    
    
    }
    
    
    j++;
    }


    i++;

    if(i==model.getRowCount()){
        
    data.remove(1);
    
    data.add(1, lastpInstalment.toString());
    
     data.remove(3);
    
    data.add(3, lastIInstalment.toString());

    data.remove(5);
    
    data.add(5, lastInstalment.toString());

    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "specialPAmount"+amortz.split("[,]", 7)[0] +".txt"), lastpInstalment.toString());
    
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "specialIAmount"+amortz.split("[,]", 7)[0] +".txt"), lastInstalment.toString());
    
    }


loan.insertNewLoanAmort(data, amortz.split("[,]", 7)[0],"NL"+countLoans,"newloan"+amortz.split("[,]", 7)[0]);
    }

 
    
    
    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApplied.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
    
        SwingWorker<Void,Void>isendSms=new SwingWorker(){
        @Override
        protected Object doInBackground() throws Exception {
        sendsms.createLoanApliedSMS(amortz.split("[,]", 7)[0], AmortValues.get(0).toString(),c);
            return null;
        }
};
 isendSms.execute();

    }

    } 
    
    
    }
    
    
    }
    
  public ObjectTableModel amortizeme(List amortDetails,Component kk){
  
      Amortization amortize = new Amortization(this.userId); 
//          amortDetailsx.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetailsx.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetailsx.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetailsx.add(jTextField1.getText());//Start date
//    amortDetailsx.add(n+"");//Interest rate type
//    amortDetailsx.add(jComboBox1.getSelectedItem().toString());//Interest rate type

//      JOptionPane.showMessageDialog(c, amortDetails.get(0)+"\n"+amortDetails.get(1)+"\n"+amortDetails.get(2)+"\n"+amortDetails.get(3)+"\n"+amortDetails.get(4)+"\n"+amortDetails.get(5));

ObjectTableModel mymodel=null;

switch(parseInt(amortDetails.get(6).toString().replace("'", "").replace(".0", ""))){
    

    case 1:
      mymodel=amortize.amortizeFlatInterestRate(amortDetails,kk);  
    break;
     case 2:
        mymodel=amortize.amortizereducingBalanceReducingInstalment(amortDetails,kk); 
    break;
     case 3:
     
       mymodel=amortize.amortizeReducingBalanceFixedInstalment(amortDetails,kk);
    break;
   
}   
 
return mymodel;
}  

public String getCounterl(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt")))+1);
fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt"), thecounter);
return thecounter;
}  
}
