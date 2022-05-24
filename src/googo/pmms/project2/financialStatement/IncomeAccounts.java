
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
public class IncomeAccounts {
     private static List<IncomeAccounts> incomeAccountStore=new ArrayList();
    
    
   fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
    ReportsDatabase rdb = new ReportsDatabase();
    String incomeDateStart,incomeDateEnd;
    List mainIncomeAccounts,otherIncomeAccounts;
    Formartter ffm=new Formartter();
    private IncomeAccounts(String startDate,String endDate){
        
incomeDateStart=startDate;
incomeDateEnd=endDate;
mainIncomeAccounts=new ArrayList();
mainIncomeAccounts.add("Main Income");
otherIncomeAccounts=new ArrayList();

otherIncomeAccounts.add("Other Income");
otherIncomeAccounts.add("Income From Short Term Insurance Business");

    }

    public static synchronized IncomeAccounts incomeProducer(String startDate,String endDate){
 
   
    
    return new IncomeAccounts(startDate,endDate);
    }
    
    
  public List<Object>  revenueTitle(){
  List aTitle =new ArrayList();
  aTitle.add("REVENUE");
   aTitle.add("");
    aTitle.add("");
     aTitle.add("");
     return aTitle;
  }
    public List<Object>  mainIncomeTitle(){
  List ctitle =new ArrayList();
  ctitle.add("Main Income");
   ctitle.add("");
    ctitle.add("");
     ctitle.add("");
     return ctitle;
  }
  public List<Object>  otherIncomeTitle(){
  List nCAtitle =new ArrayList();
  nCAtitle.add("Other Income");
   nCAtitle.add("");
    nCAtitle.add("");
     nCAtitle.add("");
     return nCAtitle;
  }  
  
    
  public List<List<Object>> mainIncome(Component c){
  List mainIncome=null;
      List<List<Object>> mainIncomeListList =new ArrayList(); 
   

    for(Object current:mainIncomeAccounts){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),incomeDateStart,incomeDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            mainIncome =new ArrayList(); 
            
        mainIncome.add(entry.getValue());
        
         mainIncome.add("");
         
        mainIncome.add(parseDouble(rdb.getBalances(entry.getValue(),incomeDateStart,incomeDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(incomeDateStart),ffm.earlierDate(incomeDateEnd),c).get(entry.getValue()).toString())==0){
        
            mainIncome.add("-");
            
        }else{
        
        mainIncome.add(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(incomeDateStart),ffm.earlierDate(incomeDateEnd),c).get(entry.getValue()).toString()));
       
        }
  
mainIncomeListList.add(mainIncome);
        }
  
  
  }
    
    }


     return mainIncomeListList;
  } 
  
    public List<List<Object>>  otherIncomes(Component c){
        List otherIncomeK=null;
 
    
  List<List<Object>> otheIncomeList =new ArrayList();
 
      
    for(Object otherIN:otherIncomeAccounts){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(otherIN.toString()); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalances(entry.getValue(),incomeDateStart,incomeDateEnd,c).get(entry.getValue()).toString())!=0.0){
             
            otherIncomeK =new ArrayList(); 
            
        otherIncomeK.add(entry.getValue());
        
         otherIncomeK.add("");
         
        otherIncomeK.add(parseDouble(rdb.getBalances(entry.getValue(),incomeDateStart,incomeDateEnd,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalances(entry.getValue(),ffm.earlierDate(incomeDateStart),ffm.earlierDate(incomeDateEnd),c).get(entry.getValue()).toString())==0){
        
            otherIncomeK.add("-");
            
        }else{
        
        otherIncomeK.add(rdb.getBalances(entry.getValue(),ffm.earlierDate(incomeDateStart),ffm.earlierDate(incomeDateEnd),c).get(entry.getValue()).toString());
       
        }
  
otheIncomeList.add(otherIncomeK);
        }
  
  
  }
    
    }


 
return otheIncomeList; 
    
}
}
