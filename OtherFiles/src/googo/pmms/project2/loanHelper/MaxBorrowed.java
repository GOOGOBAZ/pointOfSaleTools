/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.loanHelper;

import googo.pmms.project2.accountsHelper.PostingModal;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanchart
 */
public class MaxBorrowed {
    
  fileInputOutPutStreams fios= new fileInputOutPutStreams();
    DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
    PostingModal model ;
    JFrame fi, f,fa,fb,fc;
    Date date;
  SimpleDateFormat df;
  SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
 DatabaseQuaries dbq= new DatabaseQuaries();
 
 MaxmumAmountBorrowedFormulas maxValue= new MaxmumAmountBorrowedFormulas();

  DecimalFormat df1 = new DecimalFormat("#,###");
   
  
   public double maxAmountBorrowed(String accountNumber){
  
       double maxi=0.0;
       
       fios.forceFileExistance(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
       
       int typeOfBasis =fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
       
            switch(typeOfBasis){
                case 0:

                double fixedValue=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "fixedMaxLent.txt")));
                maxi=fixedValue;
                

                break;
                case 1:

                String formulaOthers=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));
                int operands= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));

                if(operands==3){

                    String [] formulaBits=formulaOthers.split("[,]", 3);
                    String d1="",d2="",d3="";

                    if(formulaBits[0].equals("Shares")){
                        d1=dbq.getShares(accountNumber);
                    } else{
                        d1=formulaBits[0];

                    }

                    d2=formulaBits[1];

                    if(formulaBits[2].replaceAll(",", "").equals("Shares")){
                        d3=dbq.getShares(accountNumber);

                    }else{
                        d3=formulaBits[2].replaceAll(",", "");

                    }
                 maxi=maxValue.makeFormulaValue(d1, d2, d3);
                    
                }else if(operands==5){
                    String [] formulaBits=formulaOthers.split("[,]", 5);
                    String d1="",d2="",d3="",d4="",d5="";
                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getShares(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }
                    d3=formulaBits[2];
                    if(formulaBits[3].equals("Shares")){

                        d4=dbq.getShares(accountNumber);

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4].replaceAll(",", "");
                     maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5);
               
                } else if(operands==7){

                    String [] formulaBits=formulaOthers.split("[,]", 7);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="";

                    if(formulaBits[0].equals("Shares")){

                        d1=dbq.getShares(accountNumber);

                    }else{
                        d1=formulaBits[0];
                    }

                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getShares(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }

                    d3=formulaBits[2];

                    if(formulaBits[3].equals("Shares")){

                        d4=dbq.getShares(accountNumber);

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4];

                    if(formulaBits[5].equals("Shares")){

                        d6=dbq.getShares(accountNumber);

                    }else{
                        d6=formulaBits[5];
                    }

                    if(formulaBits[6].replaceAll(",", "").equals("Shares")){

                        d7=dbq.getShares(accountNumber);

                    }else{
                        d7=formulaBits[6].replaceAll(",", "");
                    }
                    
                   maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7);
                   
                } else if(operands==9){

                    String [] formulaBits=formulaOthers.split("[,]", 9);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";

                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getShares(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }

                    if(formulaBits[2].equals("Shares")){

                        d3=dbq.getShares(accountNumber);

                    }else{
                        d3=formulaBits[2];
                    }

                    d4=formulaBits[3];

                    if(formulaBits[4].equals("Shares")){

                        d5=dbq.getShares(accountNumber);

                    }else{
                        d5=formulaBits[4];
                    }
                    d6=formulaBits[5];

                    if(formulaBits[6].equals("Shares")){

                        d7=dbq.getShares(accountNumber);

                    }else{
                        d7=formulaBits[6];
                    }

                    if(formulaBits[7].equals("Shares")){

                        d8=dbq.getShares(accountNumber);

                    }else{
                        d8=formulaBits[7];
                    }
                    d9=formulaBits[8].replaceAll(",", "");
                    maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9);
                   

                }

                break;
                case 2:
String formulaOthersg=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));
                int operandsg= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));

                if(operandsg==3){

                    String [] formulaBits=formulaOthersg.split("[,]", 3);
                    String d1="",d2="",d3="";

                    if(formulaBits[0].equals("Savings")){
                        d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
                    } else{
                        d1=formulaBits[0];

                    }

                    d2=formulaBits[1];

                    if(formulaBits[2].replaceAll(",", "").equals("Savings")){
                        d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));;

                    }else{
                        d3=formulaBits[2].replaceAll(",", "");

                    }
                 maxi=maxValue.makeFormulaValue(d1, d2, d3);
                    
                }else if(operandsg==5){
                    String [] formulaBits=formulaOthersg.split("[,]", 5);
                    String d1="",d2="",d3="",d4="",d5="";
                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }
                    d3=formulaBits[2];
                    if(formulaBits[3].equals("Savings")){

                        d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4].replaceAll(",", "");
                     maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5);
               
                } else if(operandsg==7){

                    String [] formulaBits=formulaOthersg.split("[,]", 7);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="";

                    if(formulaBits[0].equals("Savings")){

                        d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d1=formulaBits[0];
                    }

                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }

                    d3=formulaBits[2];

                    if(formulaBits[3].equals("Savings")){

                        d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4];

                    if(formulaBits[5].equals("Savings")){

                        d6=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d6=formulaBits[5];
                    }

                    if(formulaBits[6].replaceAll(",", "").equals("Savings")){

                        d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d7=formulaBits[6].replaceAll(",", "");
                    }
                    
                   maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7);
                   
                } else if(operandsg==9){

                    String [] formulaBits=formulaOthersg.split("[,]", 9);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";

                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }

                    if(formulaBits[2].equals("Savings")){

                        d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d3=formulaBits[2];
                    }

                    d4=formulaBits[3];

                    if(formulaBits[4].equals("Savings")){

                        d5=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d5=formulaBits[4];
                    }
                    d6=formulaBits[5];

                    if(formulaBits[6].equals("Savings")){

                        d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d7=formulaBits[6];
                    }

                    if(formulaBits[7].equals("Savings")){

                        d8=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d8=formulaBits[7];
                    }
                    d9=formulaBits[8].replaceAll(",", "");
                    maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9);}
                break;
                case 3:

                break;
                case 4:

                break;
                default:

                break;
            }
   
   
   
   
   return maxi;
   }
  
   
   public double maxAmountBorrowedF(String accountNumber){
  
       double maxi=0.0;
       
       fios.forceFileExistance(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
       
       int typeOfBasis =fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
       
            switch(typeOfBasis){
                case 0:

                double fixedValue=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "fixedMaxLent.txt")));
                maxi=fixedValue;
                

                break;
                case 1:

                String formulaOthers=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));
                int operands= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));

                if(operands==3){

                    String [] formulaBits=formulaOthers.split("[,]", 3);
                    String d1="",d2="",d3="";

                    if(formulaBits[0].equals("Shares")){
                        d1=dbq.getSharesF(accountNumber);
                    } else{
                        d1=formulaBits[0];

                    }

                    d2=formulaBits[1];

                    if(formulaBits[2].replaceAll(",", "").equals("Shares")){
                        d3=dbq.getSharesF(accountNumber);

                    }else{
                        d3=formulaBits[2].replaceAll(",", "");

                    }
                 maxi=maxValue.makeFormulaValue(d1, d2, d3);
                    
                }else if(operands==5){
                    String [] formulaBits=formulaOthers.split("[,]", 5);
                    String d1="",d2="",d3="",d4="",d5="";
                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getSharesF(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }
                    d3=formulaBits[2];
                    if(formulaBits[3].equals("Shares")){

                        d4=dbq.getSharesF(accountNumber);

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4].replaceAll(",", "");
                     maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5);
               
                } else if(operands==7){

                    String [] formulaBits=formulaOthers.split("[,]", 7);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="";

                    if(formulaBits[0].equals("Shares")){

                        d1=dbq.getSharesF(accountNumber);

                    }else{
                        d1=formulaBits[0];
                    }

                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getSharesF(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }

                    d3=formulaBits[2];

                    if(formulaBits[3].equals("Shares")){

                        d4=dbq.getSharesF(accountNumber);

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4];

                    if(formulaBits[5].equals("Shares")){

                        d6=dbq.getSharesF(accountNumber);

                    }else{
                        d6=formulaBits[5];
                    }

                    if(formulaBits[6].replaceAll(",", "").equals("Shares")){

                        d7=dbq.getSharesF(accountNumber);

                    }else{
                        d7=formulaBits[6].replaceAll(",", "");
                    }
                    
                   maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7);
                   
                } else if(operands==9){

                    String [] formulaBits=formulaOthers.split("[,]", 9);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";

                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Shares")){

                        d2=dbq.getSharesF(accountNumber);

                    }else{
                        d2=formulaBits[1];
                    }

                    if(formulaBits[2].equals("Shares")){

                        d3=dbq.getSharesF(accountNumber);

                    }else{
                        d3=formulaBits[2];
                    }

                    d4=formulaBits[3];

                    if(formulaBits[4].equals("Shares")){

                        d5=dbq.getSharesF(accountNumber);

                    }else{
                        d5=formulaBits[4];
                    }
                    d6=formulaBits[5];

                    if(formulaBits[6].equals("Shares")){

                        d7=dbq.getSharesF(accountNumber);

                    }else{
                        d7=formulaBits[6];
                    }

                    if(formulaBits[7].equals("Shares")){

                        d8=dbq.getSharesF(accountNumber);

                    }else{
                        d8=formulaBits[7];
                    }
                    d9=formulaBits[8].replaceAll(",", "");
                    maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9);
                   

                }

                break;
                case 2:
String formulaOthersg=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));
                int operandsg= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));

                if(operandsg==3){

                    String [] formulaBits=formulaOthersg.split("[,]", 3);
                    String d1="",d2="",d3="";

                    if(formulaBits[0].equals("Savings")){
                        d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
                    } else{
                        d1=formulaBits[0];

                    }

                    d2=formulaBits[1];

                    if(formulaBits[2].replaceAll(",", "").equals("Savings")){
                        d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d3=formulaBits[2].replaceAll(",", "");

                    }
                 maxi=maxValue.makeFormulaValue(d1, d2, d3);
                    
                }else if(operandsg==5){
                    String [] formulaBits=formulaOthersg.split("[,]", 5);
                    String d1="",d2="",d3="",d4="",d5="";
                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }
                    d3=formulaBits[2];
                    if(formulaBits[3].equals("Savings")){

                        d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4].replaceAll(",", "");
                     maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5);
               
                } else if(operandsg==7){

                    String [] formulaBits=formulaOthersg.split("[,]", 7);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="";

                    if(formulaBits[0].equals("Savings")){

                        d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d1=formulaBits[0];
                    }

                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }

                    d3=formulaBits[2];

                    if(formulaBits[3].equals("Savings")){

                        d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d4=formulaBits[3];
                    }
                    d5=formulaBits[4];

                    if(formulaBits[5].equals("Savings")){

                        d6=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d6=formulaBits[5];
                    }

                    if(formulaBits[6].replaceAll(",", "").equals("Savings")){

                        d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d7=formulaBits[6].replaceAll(",", "");
                    }
                    
                   maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7);
                   
                } else if(operandsg==9){

                    String [] formulaBits=formulaOthersg.split("[,]", 9);
                    String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";

                    d1=formulaBits[0];
                    if(formulaBits[1].equals("Savings")){

                        d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d2=formulaBits[1];
                    }

                    if(formulaBits[2].equals("Savings")){

                        d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d3=formulaBits[2];
                    }

                    d4=formulaBits[3];

                    if(formulaBits[4].equals("Savings")){

                        d5=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d5=formulaBits[4];
                    }
                    d6=formulaBits[5];

                    if(formulaBits[6].equals("Savings")){

                        d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d7=formulaBits[6];
                    }

                    if(formulaBits[7].equals("Savings")){

                        d8=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));

                    }else{
                        d8=formulaBits[7];
                    }
                    d9=formulaBits[8].replaceAll(",", "");
                    maxi=maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9);}
                break;
                case 3:

                break;
                case 4:

                break;
                default:

                break;
            }
   
   
   
   
   return maxi;
   }
}
