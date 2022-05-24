
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.reportsHelper.netProfits;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SSRN
 */
public class LiabilityEquityAccounts {
    
     private static List<LiabilityEquityAccounts> liabilityEquityAccountStore=new ArrayList();
     
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
     Formartter ffm=new Formartter();
        ReportsDatabase rdb = new ReportsDatabase();
      String balanceSheetDate;
       List<String> currentLiabilityt,nonCurrentLiabilityT,equityT;
         netProfits netprofits= new netProfits();
   private LiabilityEquityAccounts(String sheetDate){
    balanceSheetDate=sheetDate;
    


    currentLiabilityt=new ArrayList();
currentLiabilityt.add("Current Liabilities");
currentLiabilityt.add("Provisions");
currentLiabilityt.add("Deferred Tax Liabilities");
nonCurrentLiabilityT=new ArrayList();
nonCurrentLiabilityT.add("Secured Liabilities");
 nonCurrentLiabilityT.add("Unsecured Liabilities");   
    equityT=new ArrayList();
equityT.add("Share Capital");
 equityT.add("Reserves And Surplus");   
    }
    
     public static synchronized LiabilityEquityAccounts assetProducer(String dateSheet){
 
    
    return new LiabilityEquityAccounts(dateSheet);
    }
   
   
    public List<Object>  mainLiabilitiesEquityTitle(){
  List liabEquTitle =new ArrayList();
  liabEquTitle.add("LIABILITIES AND EQUITY");
   liabEquTitle.add("");
    liabEquTitle.add("");
     liabEquTitle.add("");
     return liabEquTitle;
  }
      public List<Object>  currentLiabilitiesTitle(){
  List cLtitle =new ArrayList();
  cLtitle.add("Current liabilities");
   cLtitle.add("");
    cLtitle.add("");
     cLtitle.add("");
     return cLtitle;
  }
  public List<Object>  nonCurrentLiabilitiesTitle(){
  List nCLtitle =new ArrayList();
  nCLtitle.add("Non-current liabilities");
   nCLtitle.add("");
    nCLtitle.add("");
     nCLtitle.add("");
     return nCLtitle;
  }  
    
  public List<List<Object>>  currentLiabilities(Component c){
  List currentLiabilities =null;
 List<List<Object>> currentLiabilitiesv =new ArrayList();
 
  for(String currentLiabilitiesw:currentLiabilityt){
   
Map<Integer, String> level5=rdb.getLevel5Items(currentLiabilitiesw); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
//       fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),balanceSheetDate); 
       
        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString())!=0.0){
              
            currentLiabilities =new ArrayList();
        currentLiabilities.add(entry.getValue());
        
         currentLiabilities.add("");
         
        currentLiabilities.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString()));
        
        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString())==0){
        
            currentLiabilities.add("-");
            
        }else{
        
        currentLiabilities.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString()));
        }
        currentLiabilitiesv.add(currentLiabilities);
        }
  
  
  }
    
    }

  
     return currentLiabilitiesv;
  } 
  
 
  
  
  
    public List<List<Object>>  nonCurrentLiabilities(Component c){
  List nonCurrentLiabilities =null;
 List<List<Object>> nonCurrentLiabilitiesv =new ArrayList();
 
  for(String nonCurrentLiabilitiesw:nonCurrentLiabilityT){
          
Map<Integer, String> level5=rdb.getLevel5Items(nonCurrentLiabilitiesw); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
      
        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString())!=0.0){
            nonCurrentLiabilities =new ArrayList();
        nonCurrentLiabilities.add(entry.getValue());
        
         nonCurrentLiabilities.add("");
         
       nonCurrentLiabilities.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString()));
        
        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString())==0){
        
           nonCurrentLiabilities.add("-");
            
        }else{
        
       nonCurrentLiabilities.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString()));
        }
          nonCurrentLiabilitiesv.add(nonCurrentLiabilities);
        }
  
  
  }
 
    }

  
  
     return nonCurrentLiabilitiesv;
  } 
   
    
public List<Object> equityTitle(){
  List eTitle =new ArrayList();
  eTitle.add("Equity");
   eTitle.add("");
    eTitle.add("");
     eTitle.add("");
     return eTitle;
  }  
    
  public List<List<Object>>  equity(Component c){
 List equity =null, equity1=null;
 List<List<Object>> equityv =new ArrayList();
 
        for(String equityw:equityT){

        Map<Integer, String> level5=rdb.getLevel5Items(equityw);

        Double balanceSheetCurrentAmount=0.0,balanceSheetEarlierAmount=0.0,currentProfits=0.0,earklierProfits=0.0; String accountName="";

        for(Map.Entry<Integer, String> entry:level5.entrySet()){

        balanceSheetCurrentAmount=parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString());

        balanceSheetEarlierAmount=parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString());

        accountName=  entry.getValue();  

        currentProfits=  parseDouble(netprofits.theNetProfits(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))),balanceSheetDate,c));

        earklierProfits= parseDouble(netprofits.theNetProfits(ffm.earlierDate(ffm.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt")))),ffm.earlierDate(balanceSheetDate),c));

        if(balanceSheetCurrentAmount!=0.0){
            
        equity =new ArrayList();
          equity1 =new ArrayList();
        

        equity.add(accountName);

        equity.add("");

        equity.add(balanceSheetCurrentAmount);

        
        
        if(balanceSheetEarlierAmount==0){

        equity.add("-");

        }else{ 
        equity.add(balanceSheetEarlierAmount);      
        }




        //  
        }
        if(accountName.equalsIgnoreCase("Retained Earnings")){

        Double reserves=currentProfits;
         String reserveName="";
         
         if(currentProfits<0){
         
         reserveName="Operating Period NetLoss";
         }else{
         reserveName="Operating Period NetProfit";
         }
        if(reserves!=0){
        equity1.add(reserveName);
        equity1.add("");
        equity1.add(reserves);
        }
       
        
        Double reserves2=earklierProfits;
        if(reserves2!=0){

        equity1.add(reserves2);
         
        }else{

        equity1.add("-");
        
        }
        equityv.add(equity1);
        }
        equityv.add(equity);
        
        
        
        }

        
        }


        return equityv;
        }



        }
