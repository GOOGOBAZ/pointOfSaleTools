/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.loanHelper;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;


public class Amortization {
Calendar cal = Calendar.getInstance();
public ArrayList<Object> data4;
Formartter fmt = new Formartter();
public  ArrayList<String>  column1;
fileInputOutPutStreams fios= new fileInputOutPutStreams();
DatabaseQuaries dbq =new DatabaseQuaries();
 ArrayList<ArrayList<Object>> data5;
 public  double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0,interestInstalmentvx;
   public Calendar startDate;
   public ButtonGroup group1,group2,group3,group4;
   public ObjectTableModel  model;        
    public amortizationModel holder;
    DecimalFormat df1 = new DecimalFormat("#.##");
DecimalFormat df2 = new DecimalFormat("#");
 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
 String userId;
 public Amortization (String use){
 column1=new  ArrayList<>();  
        column1.add("Inst.No");
        column1.add("Pri.Paid");
        column1.add("RunBal PriPaid");
        column1.add("Int.Paid");
        column1.add("RunBal IntPaid");
        column1.add("Inst.Paid");
        column1.add("Begin.bal");
        column1.add("End.bal");
        column1.add("Inst.due Date");
userId=use;
 }
 
    public double calculateInstalmentFlatInnterestAmount(double principle1,int tenure1, double interest1, int PaymentPlan1){
       double instalment=0.0;
       
     instalment = (double)calculateFlatInterestInstalment(principle1,tenure1, interest1,PaymentPlan1)+(double)amount(principle1,tenure1);
    return   instalment;  
 }
    
 public double calculateInstalmentFlatInnterestAmount1(double principle1,int tenure1,int tenure2, double interest1, int PaymentPlan1){
       double instalment=0.0;
       
     instalment = (double)calculateFlatInterestInstalment1(principle1,tenure1,tenure2, interest1,PaymentPlan1)+(double)amount(principle1,tenure2);
    return   instalment;  
 }
 public double periodInterestRate(double annualInterestRate, int periodOfTimeToPay){
 double rate=0.0;
 switch(periodOfTimeToPay){
     case 1:
     rate=  (double)annualInterestRate/360;  
     break;    
     case 2:
     rate=  (double)annualInterestRate/48;  
     break;   
    case 3:
     rate=  (double)annualInterestRate/24;  
     break;  
    case 4:
     rate=  (double)annualInterestRate/12;  
     break;  
    case 5:
     rate=  (double)annualInterestRate/4;  
     break;  
        case 6:
     rate=  (double)annualInterestRate/2;  
     break;  
         case 7:
     rate=  (double)annualInterestRate/1;  
    break;  
               case 8:
     rate= (double) annualInterestRate/.5;  
    break;  
 }
 
 return rate/100; 
 }
   

public double calculateFlatInterestInstalment(double principle,int tenure, double rate,int PaymentPlan){
   

annualInterest=totalInterestOnFlat(principle,tenure,rate,PaymentPlan);

   interest= annualInterest/tenure;  


return interest;
}

public double calculateFlatInterestInstalment1(double principle,int tenure, int tenure1,double rate,int PaymentPlan){
   

annualInterest=totalInterestOnFlat(principle,tenure,rate,PaymentPlan);

   interest= annualInterest/tenure1;  


return interest;
}

public double totalInterestOnFlat(double principle1,int tenure1, double rate1,int PaymentPlan1){

return principle1*rate1*time(tenure1,PaymentPlan1)/100;


}



public double time(double periodOfLoan,int plan){
double period=0.0;
double value=0.0;
switch(plan){

     case 1:
     period=(double) periodOfLoan/360;  
     DecimalFormat df = new DecimalFormat("#.####################################");
     df.setRoundingMode(RoundingMode.CEILING);

    value= parseDouble(df.format(period)) ;
     
     break;    
     case 2:
     period= (double)periodOfLoan/48; 
    DecimalFormat df1 = new DecimalFormat("#.####################################");
    value= parseDouble(df1.format(period)) ;
     break;   
    case 3:
     period= (double) periodOfLoan/24; 
     DecimalFormat df12 = new DecimalFormat("#.####################################");
    df12.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df12.format(period)) ;
     break;  
    case 4:
    period=  (double)periodOfLoan/12;  
   DecimalFormat  df123 = new DecimalFormat("#.####################################");
   df123.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df123.format(period)) ;
     break;  
    case 5:
    period=(double) periodOfLoan/4;  
     DecimalFormat df123v = new DecimalFormat("#.####################################");
      df123v.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df123v.format(period)) ;
     break;  
        case 6:
    period= (double)periodOfLoan/2; 
     DecimalFormat df123c = new DecimalFormat("#.####################################");
      df123c.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df123c.format(period)) ;
     break;  
         case 7:
    period= (double)periodOfLoan/1;  
     DecimalFormat df123x = new DecimalFormat("#.####################################");
      df123x.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df123x.format(period)) ;
    break;  
               case 8:
   period= (double)periodOfLoan/.5;  
    DecimalFormat df1x23x = new DecimalFormat("#.####################################");
      df1x23x.setRoundingMode(RoundingMode.CEILING);
    value= parseDouble(df1x23x.format(period)) ;
    break;  


}

return value;
}
public double amount(double princip,int tenure1){
 double cash=0.0;

  cash=princip/tenure1;   
return cash;
}


    public double calculateInstalmentReducingInnterestAmount(double initialAmount, double interest, int totalNumberOfPayments, int periodOfTime,Component c){
 double instalment=0.0;
//  JOptionPane.showMessageDialog(c, "interest="+interest+";"+"totalNumberOfPayments="+totalNumberOfPayments+"periodOfTime="+periodOfTime);
//  JOptionPane.showMessageDialog(c, "periodInterestRate="+periodInterestRate(interest, periodOfTime));
//   JOptionPane.showMessageDialog(c, "onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments))="+onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments));
//   JOptionPane.showMessageDialog(c, "(onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments)-1);="+(onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments)-1));
//   
 instalment=(periodInterestRate(interest, periodOfTime)*initialAmount*onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments))/(onePlusInterestPowern(periodInterestRate(interest, periodOfTime), totalNumberOfPayments)-1);
// JOptionPane.showMessageDialog(c, "instalment=="+instalment);
 return instalment;
 }
    
    
    public double interestInstalmentReducingInterest(double initialAmountTI, double interest1TI,int tenure1TI, int PaymentPlan1TI,Component c){
    
    interestInstalmentvx  =calculateInstalmentReducingInnterestAmount(initialAmountTI, interest1TI, tenure1TI, PaymentPlan1TI,c)-(periodInterestRate(interest1TI, PaymentPlan1TI)*initialAmountTI);
    return (periodInterestRate(interest1TI, PaymentPlan1TI)*interestInstalmentvx) ;
   
    }
    
    
 public double totalInterestPaidReducingInterest(double initialAmountTI, double interest1TI,int tenure1TI, int PaymentPlan1TI,Component c){
 
     double principalBal=0.0, totalInterest=0.0,newAmount=0.0,interestInstalmentin=0.0,instalmentAmount=0.0; data4= new ArrayList();
     int i=0;
     instalmentAmount=calculateInstalmentReducingInnterestAmount(parseDouble(df1.format(initialAmountTI)), parseDouble(df1.format(interest1TI)),tenure1TI, PaymentPlan1TI,c);
     while (i<=tenure1TI-1){
   interestInstalmentin  =(periodInterestRate(parseDouble(df1.format(interest1TI)), PaymentPlan1TI)*parseDouble(df1.format(initialAmountTI)));
//   JOptionPane.showMessageDialog(c, "interestInstalmentin=="+interestInstalmentin);
     
     data4.add(i, parseDouble(df1.format(interestInstalmentin)));
     
   
     newAmount=parseDouble(df1.format(instalmentAmount))-parseDouble(df1.format(interestInstalmentin));
     initialAmountTI=initialAmountTI-newAmount;
   
     i++;
     }
     for(int z=0;z<=data4.size()-1;z++){
     totalInterest=totalInterest+parseDouble(data4.get(z).toString());
     
     }
return totalInterest;
    }
 
    
    public double onePlusInterestPowern(double interestPeriod,int tenure){
        
double poweredValue=0.0;

poweredValue=Math.pow(1+interestPeriod, tenure);

return poweredValue;
}


public ObjectTableModel amortizereducingBalanceReducingInstalment( List details,Component cc){

     double initialPrincipleAmount=parseDouble(details.get(0).toString());
     
        double initialInterestRate=parseDouble(details.get(1).toString());
        
        int initialLoanTerm= parseInt(details.get(2).toString().replace("'", "").replace(".0", ""));
        
     int termType= parseInt(details.get(4).toString().replace("'", "").replace(".0", ""));
     
     int amortType=fios.intFileReader(fios.createFileName("persistence", "interestPlan", "twoInOneAmortzation.txt"));
   
int count=1, x=0;

 double principalInstalmentS= amount(initialPrincipleAmount, initialLoanTerm);
 
  double instalmentAmount= calculateInstalmentReducingInnterestAmount(initialPrincipleAmount, initialInterestRate, initialLoanTerm, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")),cc);
 
  
  double interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
  double principalInstalment =instalmentAmount-interestInstalment;

  double runningInterestBal=totalInterestPaidReducingInterest(initialPrincipleAmount, initialInterestRate,initialLoanTerm,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")),cc); 

  double openingBalance=    (double)(instalmentAmount*initialLoanTerm);
  
double closingAmount=openingBalance-instalmentAmount;
double initialPrincipleAmounts=initialPrincipleAmount;
double instalmentAmounts=principalInstalmentS+interestInstalment;
double openingBalances=initialPrincipleAmount+runningInterestBal;
double closingAmounts=openingBalances-instalmentAmounts;



    if(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt")).equalsIgnoreCase("YES")){
 
     int instalmentTerm1=parseInt(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt")));
     
     
     
     
     
     
        switch (amortType) {
        case 2:
        String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=openingBalances-savings;
        
        YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        int secondTenure=instalmentTerm1-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        
//              JOptionPane.showMessageDialog(cc, firstBalance);
//                   JOptionPane.showMessageDialog(cc, principalInstalmentS);
//                        JOptionPane.showMessageDialog(cc, interestInstalment);
         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalmentS;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalmentS;
            
             if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-principalInstalmentS;
             princimpalfirstTotal=princimpalfirstTotal-principalInstalmentS;
               diff=firstBalance-firstBalanceTotal;
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalmentS;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=runningInterestBal-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=openingBalances-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        openingBalances=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmounts=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalances-instalmentAmounts;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmounts=secondPrincipalInstalment+secondInterestInstalment;
        openingBalances=openingBalances;
        closingAmounts=openingBalances-instalmentAmounts;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        openingBalances=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmounts=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalances-instalmentAmounts;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmounts=secondPrincipalInstalment+secondInterestInstalment;
        openingBalances=openingBalances;
        closingAmounts=openingBalances-instalmentAmounts;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
        
        break;
        
        case 3:
          
             if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

        data5=new ArrayList<>();
        
        
//        JOptionPane.showMessageDialog(cc, principalInstalmentS);
        
//        JOptionPane.showMessageDialog(cc, initialPrincipleAmounts);
        
//         JOptionPane.showMessageDialog(cc, initialPrincipleAmounts);
        
        double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalmentS=0.0;
        
        double newinitialPrincipleAmounts=initialPrincipleAmounts;
        
        initialPrincipleAmounts=0.0;
        
        instalmentAmounts=principalInstalmentS+interestInstalment;
        
        closingAmounts=openingBalances-instalmentAmounts;
        
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
//        JOptionPane.showMessageDialog(cc, i+" "+(instalmentTerm1-1));
        if(i<(instalmentTerm1-1)){
        openingBalances=closingAmounts;

           
        
        runningInterestBal=runningInterestBal-interestInstalment;
        
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*instalmentTerm1)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        
        }else if(i==(instalmentTerm1-1)){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalmentS=newprincipalInstalmentS;
        
//        double newinitialPrincipleAmounts=initialPrincipleAmounts;
        
        initialPrincipleAmounts=newinitialPrincipleAmounts;
        
        instalmentAmounts=principalInstalmentS+interestInstalment;
        
        closingAmounts=openingBalances-instalmentAmounts;
            
            
         openingBalances=closingAmounts;

//           JOptionPane.showConfirmDialog(cc, principalInstalmentS);
        
        runningInterestBal=runningInterestBal-interestInstalment;
        
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        }
        
        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        data5=new ArrayList<>();
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }}
            
            
            
            
        break;
        default:
        // double

        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

        data5=new ArrayList<>();
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        data5=new ArrayList<>();
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }}            break;
        }

        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO"); 
        }else{


         switch (amortType) {
        case 2:
         
            String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=openingBalances-savings;
        
        YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        int secondTenure=initialLoanTerm-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        
//              JOptionPane.showMessageDialog(cc, firstBalance);
//                   JOptionPane.showMessageDialog(cc, principalInstalmentS);
//                        JOptionPane.showMessageDialog(cc, interestInstalment);
         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalmentS;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalmentS;
            
             if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-principalInstalmentS;
             princimpalfirstTotal=princimpalfirstTotal-principalInstalmentS;
               diff=firstBalance-firstBalanceTotal;
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalmentS;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=runningInterestBal-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=openingBalances-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        openingBalances=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmounts=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalances-instalmentAmounts;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmounts=secondPrincipalInstalment+secondInterestInstalment;
        openingBalances=openingBalances;
        closingAmounts=openingBalances-instalmentAmounts;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        openingBalances=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmounts=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalances-instalmentAmounts;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmounts=secondPrincipalInstalment+secondInterestInstalment;
        openingBalances=openingBalances;
        closingAmounts=openingBalances-instalmentAmounts;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmounts=initialPrincipleAmounts-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
            
            
            break;
         
          case 3:
            
              
            break;
            
          default:
                if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

        data5=new ArrayList<>();
        for(int i=0;i<initialLoanTerm;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        data5=new ArrayList<>();
        for(int i=0;i<initialLoanTerm;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalmentS))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmounts))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmounts))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalances))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmounts))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalances=closingAmounts;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmounts=initialPrincipleAmounts-principalInstalmentS;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmounts=principalInstalmentS+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmounts=openingBalances-instalmentAmounts;
        instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }}
              
              break;
         }
        
        
      





        }


        model=new ObjectTableModel (data5,column1);


        return model;
        }




public ObjectTableModel amortizeFlatInterestRate(List details,Component c){
    
double initialPrincipleAmount=parseDouble(details.get(0).toString());//principal
    
    double initialInterestRate=parseDouble(details.get(1).toString());//interest rate
    
    int initialLoanTerm= parseInt(details.get(2).toString().replace("'", "").replace(".0", ""));//loan term
    
     int instalmentTerm1=parseInt(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt")));
     
    int termType= parseInt(details.get(4).toString().replace("'", "").replace(".0", ""));//
//    JOptionPane.showMessageDialog(c, initialPrincipleAmount);
//     JOptionPane.showMessageDialog(c, initialInterestRate);
//      JOptionPane.showMessageDialog(c, initialLoanTerm);
//       JOptionPane.showMessageDialog(c, instalmentTerm1);
      
         
    int count=1, x=0;
    
    double principalInstalment= amount(initialPrincipleAmount, instalmentTerm1);
    
    double totalInterest= totalInterestOnFlat(initialPrincipleAmount,initialLoanTerm, initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//      JOptionPane.showMessageDialog(c, principalInstalment);
//         JOptionPane.showMessageDialog(c, totalInterest);
    double interestInstalment=(double)calculateFlatInterestInstalment1(initialPrincipleAmount,initialLoanTerm,instalmentTerm1,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
   
    double instalmentAmount=calculateInstalmentFlatInnterestAmount1(initialPrincipleAmount,initialLoanTerm,instalmentTerm1,initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    
    double beginningLoanBalance=(double)totalInterestOnFlat(initialPrincipleAmount, initialLoanTerm,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))+initialPrincipleAmount;
   
    double closingLoanBalance=beginningLoanBalance-instalmentAmount;

    int amortType=fios.intFileReader(fios.createFileName("persistence", "interestPlan", "twoInOneAmortzation.txt"));



    if(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt")).equalsIgnoreCase("YES")){
 
    
     
     
        switch (amortType) {
        case 2:
        String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=beginningLoanBalance-savings;
        
        YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        int secondTenure=instalmentTerm1-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        
//              JOptionPane.showMessageDialog(cc, firstBalance);
//                   JOptionPane.showMessageDialog(cc, principalInstalmentS);
//                        JOptionPane.showMessageDialog(cc, interestInstalment);
         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalment;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalment;
            
             if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-principalInstalment;
             princimpalfirstTotal=princimpalfirstTotal-principalInstalment;
               diff=firstBalance-firstBalanceTotal;
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=totalInterest-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=beginningLoanBalance-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        beginningLoanBalance=firstClosingBalance;


        totalInterest=totalInterest-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=beginningLoanBalance-instalmentAmount;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        totalInterest=totalInterest-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        beginningLoanBalance=beginningLoanBalance;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        beginningLoanBalance=closingLoanBalance;


        totalInterest=totalInterest-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        beginningLoanBalance=firstClosingBalance;


        totalInterest=totalInterest-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=beginningLoanBalance-instalmentAmount;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        totalInterest=totalInterest-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        beginningLoanBalance=beginningLoanBalance;
        beginningLoanBalance=closingLoanBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        beginningLoanBalance=closingLoanBalance;


        totalInterest=totalInterest-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
        
        break;
        
        case 3:
          
            
              principalInstalment=0.0;
        
        instalmentAmount=principalInstalment+interestInstalment;
        
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
        
             if(termType==4){
                 
                 
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

        data5=new ArrayList<>();
        
        
        
      
        
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
//        JOptionPane.showMessageDialog(cc, i+" "+(instalmentTerm1-1));
        if(i<(instalmentTerm1-1)){
            
  initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     
     closingLoanBalance=closingLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        
        }
        
        
        if(i==(instalmentTerm1)-2){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalment=initialPrincipleAmount;
        
          instalmentAmount=principalInstalment+interestInstalment;
        
 initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=instalmentAmount;
     
     closingLoanBalance=beginningLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        }
        
        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        data5=new ArrayList<>();
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;
       
        
         if(i<(instalmentTerm1-1)){
            
  initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     
     closingLoanBalance=closingLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
     instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
        }
        
        
        if(i==(instalmentTerm1)-2){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalment=initialPrincipleAmount;
        
          instalmentAmount=principalInstalment+interestInstalment;
        
 initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=instalmentAmount;
     
     closingLoanBalance=beginningLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
     instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        }
      
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }
             
             }
            
            
            
            
        break;
        default:
        // double
   if(termType==4){//Term type indicates whether the amortization is based on monthly or other periods like weekly or daily
      
//        long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
     
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
intslmentDate=fmt.newInstalmentDate(intslmentDate);
     }
       }else{
       
 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
               
//String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
     
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//intslmentDate=fmt.newInstalmentDate(intslmentDate);

     }
    
       
    
       }
            
            break;
        }

        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO"); 
        }else{


         switch (amortType) {
        case 2:
         
            String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=beginningLoanBalance-savings;
        
        YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        int secondTenure=initialLoanTerm-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        

         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalment;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalment;
            
             if(firstBalanceTotal>firstBalance){
                 
            firstBalanceTotal=firstBalanceTotal-principalInstalment;
            
             princimpalfirstTotal=princimpalfirstTotal-principalInstalment;
             
               diff=firstBalance-firstBalanceTotal;
               
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=totalInterest-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=beginningLoanBalance-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        beginningLoanBalance=firstClosingBalance;


        totalInterest=totalInterest-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=beginningLoanBalance-instalmentAmount;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        totalInterest=totalInterest-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        beginningLoanBalance=beginningLoanBalance;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        beginningLoanBalance=closingLoanBalance;


        totalInterest=totalInterest-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        beginningLoanBalance=firstClosingBalance;


        totalInterest=totalInterest-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=beginningLoanBalance-instalmentAmount;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        totalInterest=totalInterest-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        beginningLoanBalance=beginningLoanBalance;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        beginningLoanBalance=closingLoanBalance;


        totalInterest=totalInterest-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
            
            
            break;
         
          case 3:
              
                      principalInstalment=0.0;
        
        instalmentAmount=principalInstalment+interestInstalment;
        
        closingLoanBalance=beginningLoanBalance-instalmentAmount;
            
                if(termType==4){//Term type indicates whether the amortization is based on monthly or other periods like weekly or daily
      
//        long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
     
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
  
      if(i<(initialLoanTerm-1)){
            
  initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     
     closingLoanBalance=closingLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
 intslmentDate=fmt.newInstalmentDate(intslmentDate);
        
        }
        
        
        if(i==(initialLoanTerm)-2){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalment=initialPrincipleAmount;
        
          instalmentAmount=principalInstalment+interestInstalment;
        
 initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=instalmentAmount;
     
     closingLoanBalance=beginningLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    intslmentDate=fmt.newInstalmentDate(intslmentDate);
        }
      
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }
             
             
            
            
            

     
       }else{
       
 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
               
//String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
     
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
 if(i<(initialLoanTerm-1)){
            
  initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     
     closingLoanBalance=closingLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
  instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
        }
        
        
        if(i==(initialLoanTerm)-2){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalment=initialPrincipleAmount;
        
          instalmentAmount=principalInstalment+interestInstalment;
        
 initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
  
     totalInterest=totalInterest-interestInstalment;
     
     beginningLoanBalance=instalmentAmount;
     
     closingLoanBalance=beginningLoanBalance-instalmentAmount;
     
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        }
      
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }
          

       }
            break;
            
          default:
            
              if(termType==4){//Term type indicates whether the amortization is based on monthly or other periods like weekly or daily
      
//        long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
//       JOptionPane.showMessageDialog(c, initialPrincipleAmount);   
//   JOptionPane.showMessageDialog(c, principalInstalment);
   
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
intslmentDate=fmt.newInstalmentDate(intslmentDate);
     }
       }else{
       
 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
               
//String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
     
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//intslmentDate=fmt.newInstalmentDate(intslmentDate);

     }
    
       }
              
              break;
         }
        
        
      





        }


        model=new ObjectTableModel (data5,column1);


        return model;

}

public ObjectTableModel amortizeFlatInterestRater(List details){

    
    if(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt")).equalsIgnoreCase("YES")){
    
    double initialPrincipleAmount=parseDouble(details.get(0).toString());//principal
    
    double initialInterestRate=parseDouble(details.get(1).toString());//interest rate
    
    int initialLoanTerm= parseInt(details.get(2).toString().replace("'", "").replace(".0", ""));//loan term
    
     int instalmentTerm1=parseInt(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt")));
     
    int termType= parseInt(details.get(4).toString().replace("'", "").replace(".0", ""));//
    
    int count=1, x=0;
    
    double principalInstalment= amount(initialPrincipleAmount, instalmentTerm1);
    
    double totalInterest= totalInterestOnFlat(initialPrincipleAmount,initialLoanTerm, initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    
    double interestInstalment=(double)calculateFlatInterestInstalment1(initialPrincipleAmount,initialLoanTerm,instalmentTerm1,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
   
    double instalmentAmount=calculateInstalmentFlatInnterestAmount1(initialPrincipleAmount,initialLoanTerm,instalmentTerm1,initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    
    double beginningLoanBalance=(double)totalInterestOnFlat(initialPrincipleAmount, initialLoanTerm,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))+initialPrincipleAmount;
   
    double closingLoanBalance=beginningLoanBalance-instalmentAmount;
    
    
 
    
     
    
    if(termType==4){//Term type indicates whether the amortization is based on monthly or other periods like weekly or daily
      
//        long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
     
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
intslmentDate=fmt.newInstalmentDate(intslmentDate);
     }
       }else{
       
 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
               
//String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
     
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//intslmentDate=fmt.newInstalmentDate(intslmentDate);

     }
    
       }
    
 fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO"); 
    }else{
    
      double initialPrincipleAmount=parseDouble(details.get(0).toString());//principal
    
    double initialInterestRate=parseDouble(details.get(1).toString());//interest rate
    
    int initialLoanTerm= parseInt(details.get(2).toString().replace("'", "").replace(".0", ""));//loan term
    
    
    int termType= parseInt(details.get(4).toString().replace("'", "").replace(".0", ""));//
    
    int count=1, x=0;
    double principalInstalment= amount(initialPrincipleAmount, initialLoanTerm);
    double totalInterest= totalInterestOnFlat(initialPrincipleAmount,initialLoanTerm, initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    double interestInstalment=(double)calculateFlatInterestInstalment(initialPrincipleAmount,initialLoanTerm,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    double instalmentAmount=calculateInstalmentFlatInnterestAmount(initialPrincipleAmount,initialLoanTerm,initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
    double beginningLoanBalance=(double)totalInterestOnFlat(initialPrincipleAmount, initialLoanTerm,initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))+initialPrincipleAmount;
    double closingLoanBalance=beginningLoanBalance-instalmentAmount;
    
    
    
    
    
    
    
    
    if(termType==4){//Term type indicates whether the amortization is based on monthly or other periods like weekly or daily
      
//        long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
     
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
intslmentDate=fmt.newInstalmentDate(intslmentDate);
     }
       }else{
       
 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
               
//String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

       data5=new ArrayList<>();
      
//   amortDetails.add(jFTFLoanAmount.getValue().toString());//Principal amount
//    amortDetails.add(jTFLoanTerm2.getText());//Interest Rate
//    amortDetails.add(jTFLoanTerm1.getText());//loan Tenure
//    amortDetails.add(jTextField1.getText());//Start date
//    amortDetails.add(n+"");//Payment term type  
       
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
     
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(totalInterest))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(beginningLoanBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingLoanBalance))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
     totalInterest=totalInterest-interestInstalment;
     beginningLoanBalance=beginningLoanBalance-instalmentAmount;
     closingLoanBalance=closingLoanBalance-instalmentAmount;
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//intslmentDate=fmt.newInstalmentDate(intslmentDate);

     }
    
       }
    
    
    }
 model=new ObjectTableModel (data5,column1);

return model;

}

public ObjectTableModel amortizeReducingBalanceFixedInstalment( List details,Component c){

    
 double initialPrincipleAmount=parseDouble(details.get(0).toString());
        double initialInterestRate=parseDouble(details.get(1).toString());
        int initialLoanTerm= parseInt(details.get(2).toString().replace("'", "").replace(".0", ""));
         int termType= parseInt(details.get(4).toString().replace("'", "").replace(".0", ""));
int count=1, x=0;
  double instalmentAmount= calculateInstalmentReducingInnterestAmount(initialPrincipleAmount, initialInterestRate, initialLoanTerm, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")),c);
  double interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
  double principalInstalment =instalmentAmount-interestInstalment;
double runningInterestBal=totalInterestPaidReducingInterest(initialPrincipleAmount, initialInterestRate,initialLoanTerm,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")),c); 
//JOptionPane.showMessageDialog(c, runningInterestBal);
double openingBalance=    (double)(instalmentAmount*initialLoanTerm);
double closingAmount=openingBalance-instalmentAmount;
   
     
     int amortType=fios.intFileReader(fios.createFileName("persistence", "interestPlan", "twoInOneAmortzation.txt"));


    if(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt")).equalsIgnoreCase("YES")){
 
     int instalmentTerm1=parseInt(fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt")));
     
     
     
     
     
     
        switch (amortType) {
        case 2:
        String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=openingBalance-savings;
        
        YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        int secondTenure=instalmentTerm1-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        
//              JOptionPane.showMessageDialog(cc, firstBalance);
//                   JOptionPane.showMessageDialog(cc, principalInstalmentS);
//                        JOptionPane.showMessageDialog(cc, interestInstalment);
         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalment;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalment;
            
             if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-principalInstalment;
             princimpalfirstTotal=princimpalfirstTotal-principalInstalment;
               diff=firstBalance-firstBalanceTotal;
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=runningInterestBal-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=openingBalance-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        openingBalance=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalance-instalmentAmount;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        openingBalance=openingBalance;
        closingAmount=openingBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalance=closingAmount;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        openingBalance=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalance-instalmentAmount;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        openingBalance=openingBalance;
        closingAmount=openingBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        openingBalance=closingAmount;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
        
        break;
        
        case 3:
           principalInstalment=0.0;

        
        instalmentAmount=principalInstalment+interestInstalment;
        
        closingAmount=openingBalance-instalmentAmount;
            
             if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

        data5=new ArrayList<>();
        
        
        principalInstalment=0.0;

        instalmentAmount=principalInstalment+interestInstalment;
        
        closingAmount=openingBalance-instalmentAmount;
        
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
//        JOptionPane.showMessageDialog(cc, i+" "+(instalmentTerm1-1));
        if(i<(instalmentTerm1-1)){
        openingBalance=closingAmount;

           
        
        runningInterestBal=runningInterestBal-interestInstalment;
        
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmount=principalInstalment+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*instalmentTerm1)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        
        }
        
        
        
        if(i==(instalmentTerm1-2)){
        
//             double newprincipalInstalmentS=principalInstalmentS;
        
        principalInstalment=initialPrincipleAmount;
        
//        double newinitialPrincipleAmounts=initialPrincipleAmounts;
        
//        initialPrincipleAmount=newinitialPrincipleAmounts;
        
        instalmentAmount=principalInstalment+interestInstalment;
        
        closingAmount=openingBalance-instalmentAmount;
            
            
         openingBalance=closingAmount;

//           JOptionPane.showConfirmDialog(cc, principalInstalmentS);
        
        runningInterestBal=runningInterestBal-interestInstalment;
        
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmount=principalInstalment+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);
        }
        
        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        data5=new ArrayList<>();
        for(int i=0;i<instalmentTerm1;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalance=closingAmount;


        runningInterestBal=runningInterestBal-interestInstalment;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
        interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        instalmentAmount=principalInstalment+interestInstalment;
        principalInstalment =instalmentAmount-interestInstalment;
        openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        //    intslmentDate=fmt.newInstalmentDate(intslmentDate);


        }}
            
            
            
            
        break;
        default:
         if(termType==4){
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

// long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
     intslmentDate=fmt.newInstalmentDate(intslmentDate);
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
     }
    }else{
// String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<instalmentTerm1;i++){
         data4=new ArrayList<>();
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
//     intslmentDate=fmt.newInstalmentDate(intslmentDate);
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));   
    
    
    
    
    
    }}

             break;
        }

        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO"); 
        }else{


         switch (amortType) {
        case 2:
         
            
              YearMonth theStartDateObject=YearMonth.now();
        
//         YearMonth theStartDateObject=YearMonth.;
        
        int firstTenure=12-theStartDateObject.getMonthValue();
        
        if(firstTenure>=1&&firstTenure<=11){
                                     
        
            String accountNumber= fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]")[0]; 
       
        double savings=parseDouble(dbq.memberSavings(accountNumber));
        
        double firstBalance=openingBalance-savings;
        
      
        
        int secondTenure=initialLoanTerm-firstTenure;
        
        int k=0;
        
        double princimpalfirstTotal=0.0,  interestFirstTotal=0.0,   firstBalanceTotal=0.0,diff=0.0;
        
        while(k<initialLoanTerm){
        
//              JOptionPane.showMessageDialog(cc, firstBalance);
//                   JOptionPane.showMessageDialog(cc, principalInstalmentS);
//                        JOptionPane.showMessageDialog(cc, interestInstalment);
         
             princimpalfirstTotal=princimpalfirstTotal+  principalInstalment;
             
                firstBalanceTotal=firstBalanceTotal+principalInstalment;
            
             if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-principalInstalment;
             princimpalfirstTotal=princimpalfirstTotal-principalInstalment;
               diff=firstBalance-firstBalanceTotal;
             princimpalfirstTotal=princimpalfirstTotal+ diff; 
             
             break;
            }
            
             
              
            interestFirstTotal=interestFirstTotal+interestInstalment;
            
            firstBalanceTotal=firstBalanceTotal+interestInstalment;
            
            if(firstBalanceTotal>firstBalance){
            firstBalanceTotal=firstBalanceTotal-interestInstalment;
             interestFirstTotal=interestFirstTotal-interestInstalment;
               diff=firstBalance-firstBalanceTotal;
             interestFirstTotal=interestFirstTotal+ diff; 
             
             break;
            }

//         JOptionPane.showMessageDialog(cc, firstBalanceTotal);
             
             
        initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
             
       interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
 
            k++;
        }
        
//        
//        List separatedInterestPrinc=new ArrayList();
//separatedInterestPrinc.add(initialPrincipleAmount);
//separatedInterestPrinc.add(initialInterestRate);
//separatedInterestPrinc.add(initialLoanTerm);
//separatedInterestPrinc.add(firstBalance);
//separatedInterestPrinc.add(firstTenure);
//separatedInterestPrinc.add("ReducingReduing");

//List returnedSep=separeteInterestPrincimpl(separatedInterestPrinc);

         double firstPrincipalAmount=princimpalfirstTotal;
        
        double firstInterestAmount=interestFirstTotal;
        
       
       
        double secondInterestAmount=runningInterestBal-firstInterestAmount;
        
        double secondPrincipalAmount=savings-secondInterestAmount;
        
        double firstInterestInstalment=(firstInterestAmount/firstTenure);
        
        double secondInterestInstalment=secondInterestAmount/secondTenure;
        
        double firstPrincipalInstalment=firstPrincipalAmount/firstTenure;
        
        double secondPrincipalInstalment=secondPrincipalAmount/secondTenure;
        
        double firstInstalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        
          double secondInstalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        
        double firstClosingBalance=openingBalance-firstInstalmentAmount;


        
        if(termType==4){
        String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());


        data5=new ArrayList<>();

        for(int i=0;i<firstTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        
        openingBalance=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalance-instalmentAmount;
        
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        openingBalance=openingBalance;
        closingAmount=openingBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
        data4=new ArrayList<>();
        //       String instalentDueDate=this.newInstalmentDate(intslmentDate);
        data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
        data4.add(8, intslmentDate);
        data5.add(x, data4);

        count++;
        x++;
        openingBalance=closingAmount;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }
        }else{
        //   String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());
        
        long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       
        data5=new ArrayList<>();
        
         for(int i=0;i<firstTenure;i++){
       data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(firstPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(firstInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(firstInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(firstClosingBalance))));
        data4.add(8, instalentDueDate);
        data5.add(x, data4);

        count++;
        x++;

        
        openingBalance=firstClosingBalance;


        runningInterestBal=runningInterestBal-firstInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;

        initialPrincipleAmount=initialPrincipleAmount-firstPrincipalInstalment;
        
        instalmentAmount=firstPrincipalInstalment+firstInterestInstalment;
        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;

        //    principalInstalment =instalmentAmount-interestInstalment;

        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
//        if(i!=firstTenure){
        firstClosingBalance=openingBalance-instalmentAmount;
         instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
        
//        }
//        JOptionPane.showMessageDialog(cc, firstClosingBalance);
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
//        intslmentDate=fmt.newInstalmentDate(intslmentDate);

        }

        
//         JOptionPane.showMessageDialog(cc, firstClosingBalance);
         
         
        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        runningInterestBal=runningInterestBal-secondInterestInstalment;
        instalmentAmount=secondPrincipalInstalment+secondInterestInstalment;
        openingBalance=openingBalance;
        closingAmount=openingBalance-instalmentAmount;

        for(int i=0;i<secondTenure;i++){
            data4=new ArrayList<>();
        //       String instalentDueDate=fmt.newInstalmentDate(instDate);
        String instalentDueDate=fmt.dateConverter(instDate);
         data4.add(0, count);
        data4.add(1, Math.abs(parseDouble(df2.format(secondPrincipalInstalment))));
        data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
        data4.add(3, Math.abs(parseDouble(df2.format(secondInterestInstalment))));
        data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
        data4.add(5, Math.abs(parseDouble(df2.format(secondInstalmentAmount))));
        data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
        data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
      data4.add(8, instalentDueDate);
        data5.add(x, data4);
        count++;
        x++;
        openingBalance=closingAmount;


        runningInterestBal=runningInterestBal-secondInterestInstalment;

        initialPrincipleAmount=initialPrincipleAmount-secondPrincipalInstalment;
        //     
        //     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;

        //    interestInstalment=(periodInterestRate(initialInterestRate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
        //     instalmentAmounts=principalInstalmentS+interestInstalment;
        //    principalInstalment =instalmentAmount-interestInstalment;
        //     openingBalance= (double)(instalmentAmount*initialLoanTerm)-instalmentAmount;
        //     closingAmount=openingBalance-instalmentAmount;
        closingAmount=openingBalance-instalmentAmount;
        //    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
            instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));

        }
        
      
        
        }  
            
        
        }else{
        
            if(termType==4){
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

// long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
     intslmentDate=fmt.newInstalmentDate(intslmentDate);
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
     }
    }else{
// String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
//     intslmentDate=fmt.newInstalmentDate(intslmentDate);
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));   
    
    
    
    
    
    }}

        }
        
                                                                                                               
            
            break;
         
          case 3:
            
              
            break;
            
          default:
                 if(termType==4){
String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

// long instDate=instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
//       String instalentDueDate=dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, intslmentDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
     intslmentDate=fmt.newInstalmentDate(intslmentDate);
//    instDate=instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
     }
    }else{
// String intslmentDate=fmt.newInstalmentDate(details.get(3).toString());

 long instDate=fmt.instalmentDate(fmt.convertTimeToMillseconds(details.get(3).toString()),parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));
       data5=new ArrayList<>();
     for(int i=0;i<initialLoanTerm;i++){
         data4=new ArrayList<>();
       String instalentDueDate=fmt.dateConverter(instDate);
       data4.add(0, count);
       data4.add(1, Math.abs(parseDouble(df2.format(principalInstalment))));
       data4.add(2, Math.abs(parseDouble(df2.format(initialPrincipleAmount))));
       data4.add(3, Math.abs(parseDouble(df2.format(interestInstalment))));
       data4.add(4, Math.abs(parseDouble(df2.format(runningInterestBal))));
       data4.add(5, Math.abs(parseDouble(df2.format(instalmentAmount))));
       data4.add(6, Math.abs(parseDouble(df2.format(openingBalance))));
       data4.add(7, Math.abs(parseDouble(df2.format(closingAmount))));
       data4.add(8, instalentDueDate);
       data5.add(x, data4);
  
     count++;
     x++;
     runningInterestBal=runningInterestBal-interestInstalment;
     initialPrincipleAmount=initialPrincipleAmount-principalInstalment;
    interestInstalment=(periodInterestRate(initialInterestRate, parseInt(details.get(4).toString().replace("'", "").replace(".0", "")))*initialPrincipleAmount);
    principalInstalment =instalmentAmount-interestInstalment;
     openingBalance= closingAmount;
     closingAmount=openingBalance-instalmentAmount;
//     intslmentDate=fmt.newInstalmentDate(intslmentDate);
    instDate=fmt.instalmentDate(instDate,parseInt(details.get(4).toString().replace("'", "").replace(".0", "")));   
    
    
    
    
    
    }}

              
              break;
         }
        
        
      





        }


        model=new ObjectTableModel (data5,column1);


        return model;
    
}





}
