package googo.pmms.project2.frames;

import googo.pmms.project2.databases.DatabaseQuaries;
import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class mainPrinter {
    
        DatabaseQuaries dbq =new DatabaseQuaries();
        
     PrinterOptions p=new PrinterOptions();
     
PrinterService print=new PrinterService();
    

public void printTheReceipt(List receiptDetails,Component c){
    
    
    switch(receiptDetails.get(0).toString()){
    
        case "Loan Payment Receipt":
            
         List loanPDetailsX=   dbq.getLoanPaymentDetails(receiptDetails,c);
//        JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
                                         
         
            
               if(dbq.forStamp(c)==2){
                 loanPaymentReceitStamp(loanPDetailsX,c);
//                 JOptionPane.showMessageDialog(c, loanPDetailsX.get(0));
               }else if(dbq.forStamp(c)==1){
               loanPaymentReceit(loanPDetailsX,c);
//               JOptionPane.showMessageDialog(c, loanPDetailsX.get(0));
               }
            
            break;
            
             case "Loan Disbursement Receipt":
//                    JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List loanPDetailsX1=   dbq.getLoanPaymentDetails(receiptDetails,c);
         
        
            
               if(dbq.forStamp(c)==2){
                  loanDisbursementReceitStamp(loanPDetailsX1,c);
               }else if(dbq.forStamp(c)==1){
                 loanDisbursementReceit(loanPDetailsX1,c);
               }
            
            break;
            
              case "Savings Receipt":
//                            JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List loanPDetailsX2=   dbq.getSavingsDetails(receiptDetails,c);
         
          
              if(dbq.forStamp(c)==2){
                  savingsReceipPrintStamp(loanPDetailsX2,c);
               }else if(dbq.forStamp(c)==1){
                  savingsReceipPrint(loanPDetailsX2,c);
               }
            break;
            
             case "Shares Contribution Receipt":
            
            break;
            
             case "Savings Withdraw Receipt":
//                 JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List loanPDetailsX2c=   dbq.getSavingsDetails(receiptDetails,c);
         
         
            
             if(dbq.forStamp(c)==2){
                  savingsWithdrawReceipPrintStamp(loanPDetailsX2c,c);
               }else if(dbq.forStamp(c)==1){
                   savingsWithdrawReceipPrint(loanPDetailsX2c,c);
               }
                 
            break; 
            
               case "Shares Withdraw Receipt":
            
            break; 
            
            
         case "Mini Loan Statement":
//                      JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                       JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                        JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List<List> loanPDetailsX2cState=   dbq.getMiniStatementDetails(receiptDetails,c);
         
           
               if(dbq.forStamp(c)==2){
                  loanMiniStatementStamp(loanPDetailsX2cState,c);
               }else if(dbq.forStamp(c)==1){
                  loanMiniStatement(loanPDetailsX2cState,c);
               }
            break; 
            
              case "Mini Savings Statement":
//                      JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                       JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                        JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List<List> savingsPDetailsXsa2cState=   dbq.getMiniSavingsDetails(receiptDetails,c);
         
      
           
              if(dbq.forStamp(c)==2){
                   savingsMiniStatementStamp(savingsPDetailsXsa2cState,c);
               }else if(dbq.forStamp(c)==1){
                   savingsMiniStatement(savingsPDetailsXsa2cState,c);
               }
            
            break; 
            
            
            case "Savings Historical Receipt":
//                            JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List loanPDetailsX2f=   dbq.getSavingsDetailsHistorical(receiptDetails,c);
         
           
            
              if(dbq.forStamp(c)==2){
               savingsReceipPrintStamp(loanPDetailsX2f,c);
               }else if(dbq.forStamp(c)==1){
               savingsReceipPrint(loanPDetailsX2f,c);
               }
            
            
            break;
            
           
            
             case "Savings Withdraw Historical Receipt":
//                 JOptionPane.showMessageDialog(c, receiptDetails.get(0));
//                  JOptionPane.showMessageDialog(c, receiptDetails.get(1));
//                   JOptionPane.showMessageDialog(c, receiptDetails.get(2));
               List loanPDetailsX2cg=   dbq.getSavingsDetailsHistorical(receiptDetails,c);
         
               if(dbq.forStamp(c)==2){
               savingsWithdrawReceipPrintStamp(loanPDetailsX2cg,c);
               }else if(dbq.forStamp(c)==1){
               savingsWithdrawReceipPrint(loanPDetailsX2cg,c);
               }
            
            
                 
            break; 
    }



}










     public void loanPaymentReceit(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
p.emphasized(true);
p.underLine(2);
p.newLine();
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Repayment Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX"+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Payment Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Payment Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Payment Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();
p.newLine();
p.alignLeft();
p.setText("Loan  Paid: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan  Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(9).toString()+"/="+p.underLine(0));
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
p.newLine();

p.addLineSeperator();
p.doubleStrik(true);

p.underLine(2) ;
p.newLine();
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));

p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.newLine();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();

p.addLineSeperator();
//p.newLine();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.setText("Customer Signiture:");
p.addLineSeperatorX();
//p.newLine();

//p.addLineSeperator();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
    
 public void loanDisbursementReceit(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Disbursment Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Disbursemnt Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Disbursement Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Disbursement Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Loan  Disbursed: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(15).toString()+"/="+p.underLine(0));
p.newLine();  
//p.alignLeft();
//p.setText("Loan  Remaining: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(9).toString()+"/="+p.underLine(0));
//p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
//p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        
    }
  
 public void savingsReceipPrint(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Made Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Savings Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Savings Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Savings Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Savings Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());
////p.newLine();
//p.addLineSeperatorX();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 public void savingsWithdrawReceipPrint(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

p.color(0);
p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Withdrawal ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Withdrawal Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Withdrawal Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Withdrawal Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Withdrawal Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(7).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());

p.newLine();
p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 public void loanStatament(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

p.color(0);
p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Withdrawal ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Withdrawal Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Withdrawal Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Withdrawal Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Withdrawal Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(7).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
//p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 
 
 
     public void loanMiniStatement(List<List> stateDetails,Component c) {

List receiptDetails=stateDetails.get(0);
List<List>otherRDetails=stateDetails.get(1);



 
p.resetAll();

p.initialize();

p.feedBack((byte)2);
p.newLine();
//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Repayment Statement ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Last Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX"+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Statement Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Statement Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Statement Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();


//    p.alignLeft();
p.setText("Instalment");
p.setText("   "); 
//    p.alignLeft();
p.setText("Date");
p.setText("   "); 
//p.alignLeft();
p.setText("AmtPaid");
p.setText("   "); 
//p.alignLeft();
p.setText("Balance");
//p.newLine();  
p.addLineSeperatorX();
p.newLine(); 

if(otherRDetails.size()>=1){
otherRDetails.forEach((item)->{
//    p.alignLeft();
p.setText(item.get(0).toString());
p.setText("     "); 
//    p.alignLeft();
p.setText(item.get(1).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(2).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(5).toString());
p.addLineSeperatorX();
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
});}else{
p.setText("EMPTY");
p.setText("     "); 
//    p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.addLineSeperatorX();
p.newLine();   


}
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();

p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.newLine();
//p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(16).toString());
//p.newLine();
p.addLineSeperatorX();
//p.barcode();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
     
     
    public void savingsMiniStatement(List<List> theOtheDetails,Component c) {
    
        
 
List receiptDetails=theOtheDetails.get(0);
List<List>otherRDetails=theOtheDetails.get(1);

 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Made Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Savings Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Savings Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Savings Statement ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();


if(otherRDetails.size()>=1){
otherRDetails.forEach((item)->{
//    p.alignLeft();
p.setText(item.get(0).toString());
p.setText("     "); 
//    p.alignLeft();
p.setText(item.get(1).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(2).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(5).toString());
p.addLineSeperatorX();
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
});}else{
p.setText("EMPTY");
p.setText("     "); 
//    p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.addLineSeperatorX();
p.newLine();   


}  
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());
//p.newLine();
p.addLineSeperatorX();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        
    
    }   
   
    
    
    
    
    
    
   

     public void loanPaymentReceitStamp(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));

//JOptionPane.showMessageDialog(c, receiptDetails.get(0));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
p.emphasized(true);
p.underLine(2);
p.newLine();
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Repayment Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX"+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Payment Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Payment Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Payment Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();
p.newLine();
p.alignLeft();
p.setText("Loan  Paid: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan  Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(9).toString()+"/="+p.underLine(0));
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
p.newLine();

p.addLineSeperator();
p.doubleStrik(true);

p.underLine(2) ;
p.newLine();
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));

p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.newLine();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();

p.addLineSeperator();
//p.newLine();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.setText("Customer Signiture:");
p.addLineSeperatorX();
//p.newLine();

//p.addLineSeperator();
//p.newLine();
//p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
    
 public void loanDisbursementReceitStamp(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Disbursment Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Disbursemnt Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Disbursement Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Disbursement Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Loan  Disbursed: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(15).toString()+"/="+p.underLine(0));
p.newLine();  
//p.alignLeft();
//p.setText("Loan  Remaining: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(9).toString()+"/="+p.underLine(0));
//p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
//p.newLine();
//p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
//p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        
    }
  
 public void savingsReceipPrintStamp(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Made Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Savings Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Savings Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Savings Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Savings Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
//p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());
////p.newLine();
//p.addLineSeperatorX();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 public void savingsWithdrawReceipPrintStamp(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

p.color(0);
p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Withdrawal ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Withdrawal Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Withdrawal Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Withdrawal Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Withdrawal Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(7).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());

p.newLine();
p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 public void loanStatamentStamp(List receiptDetails,Component c) {

//     String logo=System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg";
        
// print.printImage(dbq.printDrivers(c),new File(logo));
 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

p.color(0);
p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Withdrawal ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Withdrawal Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Withdrawal Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Withdrawal Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Withdrawal Made: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(7).toString()+"/="+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Balance: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(8).toString()+"/="+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
//p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
//p.addLineSeperatorX();
//p.newLine();
//p.setText("OFFICE NUMBER: "+receiptDetails.get(17).toString());
p.addLineSeperatorX();
//p.newLine();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
 
 
 
 
 
 
     public void loanMiniStatementStamp(List<List> stateDetails,Component c) {

List receiptDetails=stateDetails.get(0);
List<List>otherRDetails=stateDetails.get(1);



 
p.resetAll();

p.initialize();

p.feedBack((byte)2);
p.newLine();
//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Loan Repayment Statement ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Last Batch Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Loan ID: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
p.emphasized(false);
p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX"+receiptDetails.get(6).toString()+"/="+p.underLine(0));
p.newLine();
p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
p.newLine();
p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
p.newLine();
p.setText("Statement Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(12).toString()+p.underLine(0));
p.newLine();
p.setText("Statement Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(13).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Statement Details ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();


//    p.alignLeft();
p.setText("Instalment");
p.setText("   "); 
//    p.alignLeft();
p.setText("Date");
p.setText("   "); 
//p.alignLeft();
p.setText("AmtPaid");
p.setText("   "); 
//p.alignLeft();
p.setText("Balance");
//p.newLine();  
p.addLineSeperatorX();
p.newLine(); 

if(otherRDetails.size()>=1){
otherRDetails.forEach((item)->{
//    p.alignLeft();
p.setText(item.get(0).toString());
p.setText("     "); 
//    p.alignLeft();
p.setText(item.get(1).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(2).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(5).toString());
p.addLineSeperatorX();
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
});}else{
p.setText("EMPTY");
p.setText("     "); 
//    p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.addLineSeperatorX();
p.newLine();   


}
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.newLine();
//p.addLineSeperatorX();
//p.newLine();
//p.setText("OFFICE NUMBER: "+receiptDetails.get(16).toString());
//p.newLine();
p.addLineSeperatorX();
//p.barcode();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        

    }
     
     
    public void savingsMiniStatementStamp(List<List> theOtheDetails,Component c) {
    
        
 
List receiptDetails=theOtheDetails.get(0);
List<List>otherRDetails=theOtheDetails.get(1);

 
p.resetAll();

p.initialize();

p.feedBack((byte)2);

//p.color(0);
//p.chooseFont(1);
p.alignCenter();
//p.emphasized(true);
//p.underLine(2);
p.setText(receiptDetails.get(1).toString());
p.newLine();
p.setText(receiptDetails.get(2).toString());
p.newLine();
p.setText(receiptDetails.get(3).toString());
p.newLine();
p.newLine();
//p.addLineSeperatorX();
p.underLine(0);
p.emphasized(false);
p.underLine(0);
p.alignCenter();
p.setText(" *** Savings Made Receipt ***");
p.newLine();
p.addLineSeperator();
p.newLine();

p.alignLeft();
p.setText("Account Number: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(9).toString()+p.underLine(0));
p.newLine();  
p.alignLeft();
p.setText("Savings Id: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(0).toString()+p.underLine(0));
p.newLine();   
p.alignLeft();
p.emphasized(true);
p.setText("Customer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
p.newLine();   
//p.emphasized(false);
//p.setText("Loan+Interest: " +p.underLine(0)+p.underLine(2)+"UGX "+receiptDetails.get(6).toString()+"/="+p.underLine(0));
//p.newLine();
//p.setText("Date Taken: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(7).toString()+p.underLine(0));
//p.newLine();
//p.setText("Loan Status: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(14).toString()+p.underLine(0));
//p.newLine();
p.setText("Savings Date: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(10).toString()+p.underLine(0));
p.newLine();
p.setText("Savings Time: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(11).toString()+p.underLine(0));
p.newLine();

p.underLine(0);
p.emphasized(false);
//p.underLine(0);
p.addLineSeperator();
p.newLine();
p.alignCenter();
p.setText("***** Savings Statement ***** ");
p.newLine();
p.addLineSeperator();
p.newLine();


if(otherRDetails.size()>=1){
otherRDetails.forEach((item)->{
//    p.alignLeft();
p.setText(item.get(0).toString());
p.setText("     "); 
//    p.alignLeft();
p.setText(item.get(1).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(2).toString());
p.setText("    "); 
//p.alignLeft();
p.setText(item.get(5).toString());
p.addLineSeperatorX();
p.newLine();   
//p.alignLeft();
//p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);
});}else{
p.setText("EMPTY");
p.setText("     "); 
//    p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.setText("    "); 
//p.alignLeft();
p.setText("EMPTY");
p.addLineSeperatorX();
p.newLine();   


}  
p.alignLeft();
p.emphasized(true);
//p.setText("Customer Name: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(4).toString()+p.underLine(0));
//p.newLine();   
//p.emphasized(false);

p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.underLine(2) ;
p.setText("Officer: " +p.underLine(0)+p.underLine(2)+receiptDetails.get(5).toString()+p.underLine(0));
p.newLine();
p.addLineSeperatorX();

p.addLineSeperator();
p.doubleStrik(true);
//p.newLine();
p.underLine(2) ;
p.setText("Officer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.addLineSeperator();
p.doubleStrik(true);
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.newLine();
p.underLine(2) ;
p.setText("Customer Signiture:");
p.addLineSeperatorX();
p.newLine();
p.setText("OFFICE NUMBER: "+receiptDetails.get(12).toString());
//p.newLine();
p.addLineSeperatorX();
p.feed((byte)3);
p.finit();

print.feedPrinter(dbq.printDrivers(c),p.finalCommandSet().getBytes());
        
    
    }    
    
    
    
    
}
