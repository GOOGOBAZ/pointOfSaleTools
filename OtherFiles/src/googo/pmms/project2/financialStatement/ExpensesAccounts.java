
  

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.ReportsDatabase;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SSRN
 */
public class ExpensesAccounts {
    
    private static List<ExpensesAccounts> expenseAccountStore=new ArrayList();
    
    
   fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
    ReportsDatabase rdb = new ReportsDatabase();
    String exoebseDateStart,exoebseDateEnd;
    List mainExpensesAccounts,incomeTaxExpe,devidedExpensesAccounts,periodErrorsTaxExpe,accountPTaxExpe,retaiStartExpensesAccounts;
    Formartter ffm=new Formartter();
    private ExpensesAccounts(String startDate,String endDate){
        
exoebseDateStart=startDate;
exoebseDateEnd=endDate;
mainExpensesAccounts=new ArrayList();
mainExpensesAccounts.add("Opening Stock");
mainExpensesAccounts.add("Purchases");
mainExpensesAccounts.add("Other Direct Costs");
mainExpensesAccounts.add("Factory Overheads");
mainExpensesAccounts.add("Closing Stock");
mainExpensesAccounts.add("Employment Expense");
mainExpensesAccounts.add("Operating Expense");
mainExpensesAccounts.add("Administrative Expense");
mainExpensesAccounts.add("Financing Expense");
mainExpensesAccounts.add("Expense Purely Related To Short Term Insurance Business Income");
mainExpensesAccounts.add("General Expensee");

incomeTaxExpe=new ArrayList();
incomeTaxExpe.add("Income Taxes Expense");
devidedExpensesAccounts=new ArrayList();
devidedExpensesAccounts.add("Dividend paid during the period");

periodErrorsTaxExpe=new ArrayList();
periodErrorsTaxExpe.add("Restatement of retained earning for corrections of prior period errors");

accountPTaxExpe=new ArrayList();
accountPTaxExpe.add("Restatement of retained earning for changes in accounting policy");

retaiStartExpensesAccounts=new ArrayList();
retaiStartExpensesAccounts.add("Retained earning at start of period");
    }

    public static synchronized ExpensesAccounts expenseProducer(String startDate,String endDate){
 
    return new ExpensesAccounts(startDate,endDate);
    }
    
    
  public List<Object>  expenseTitle(){
  List aTitle =new ArrayList();
  aTitle.add("EXPENSES");
   aTitle.add("");
    aTitle.add("");
     aTitle.add("");
     return aTitle;
  }
    
    
  public List<List<Object>> expensesDeclared(Component c){
  List expensesDe=null;
      List<List<Object>> mainexpenset =new ArrayList(); 
   

      
    for(Object current:mainExpensesAccounts){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            expensesDe =new ArrayList(); 
            
        expensesDe.add(entry.getValue());
        
         expensesDe.add("");
         
        expensesDe.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            expensesDe.add("-");
            
        }else{
        
        expensesDe.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
mainexpenset.add(expensesDe);
        }
  
  
  }
    
    }


     return mainexpenset;
  } 
  
    public List<List<Object>>  incomeTaxE(Component c){
        List incomeTaxK=null;
 
    
  List<List<Object>> incomeTaxList =new ArrayList();
 
      
    for(Object otherIN:incomeTaxExpe){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(otherIN.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            incomeTaxK =new ArrayList(); 
            
        incomeTaxK.add(entry.getValue());
        
         incomeTaxK.add("");
         
        incomeTaxK.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            incomeTaxK.add("-");
            
        }else{
        
        incomeTaxK.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
incomeTaxList.add(incomeTaxK);
        }
  
  
  }
    
    }


     return incomeTaxList;
  } 
   public List<List<Object>> devidendsPaidInAyear(Component c){
  List devidendsPaidY=null;
      List<List<Object>> devdendPaidList =new ArrayList(); 
   

      
    for(Object current:devidedExpensesAccounts){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            devidendsPaidY =new ArrayList(); 
            
        devidendsPaidY.add(entry.getValue());
        
         devidendsPaidY.add("");
         
        devidendsPaidY.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            devidendsPaidY.add("-");
            
        }else{
        
        devidendsPaidY.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
devdendPaidList.add(devidendsPaidY);
        }
  
  
  }
    
    }


     return devdendPaidList;
  } 
    public List<List<Object>> periodErrorS(Component c){
  List periodErrord=null;
      List<List<Object>> mainPeriodErrorsList =new ArrayList(); 
   

      
    for(Object current:periodErrorsTaxExpe){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            periodErrord =new ArrayList(); 
            
        periodErrord.add(entry.getValue());
        
         periodErrord.add("");
         
        periodErrord.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            periodErrord.add("-");
            
        }else{
        
        periodErrord.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
mainPeriodErrorsList.add(periodErrord);
        }
  
  
  }
    
    }


     return mainPeriodErrorsList;
  } 
     public List<List<Object>> periodAccountPolicy(Component c){
  List changeAccountP=null;
      List<List<Object>> changeAccountList =new ArrayList(); 
   

      
    for(Object current:accountPTaxExpe){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            changeAccountP =new ArrayList(); 
            
        changeAccountP.add(entry.getValue());
        
         changeAccountP.add("");
         
        changeAccountP.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            changeAccountP.add("-");
            
        }else{
        
        changeAccountP.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
changeAccountList.add(changeAccountP);
        }
  
  
  }
    
    }


     return changeAccountList;
  } 
      public List<List<Object>> retainedEarningY(Component c){
  List retainedEarning=null;
      List<List<Object>> mainRetained=new ArrayList(); 
   

      
    for(Object current:retaiStartExpensesAccounts){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            retainedEarning =new ArrayList(); 
            
        retainedEarning.add(entry.getValue());
        
         retainedEarning.add("");
         
        retainedEarning.add(parseDouble(rdb.getBalances(entry.getValue(),exoebseDateStart,exoebseDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString())==0){
        
            retainedEarning.add("-");
            
        }else{
        
        retainedEarning.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(exoebseDateStart),ffm.earlierDate(exoebseDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
mainRetained.add(retainedEarning);
        }
  
  
  }
    
    }


     return mainRetained;
  } 
}
