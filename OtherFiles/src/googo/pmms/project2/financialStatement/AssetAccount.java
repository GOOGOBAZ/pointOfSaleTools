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
public class AssetAccount {
    
    private static List<AssetAccount> assetAccountStore=new ArrayList();
    
    
   fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
    ReportsDatabase rdb = new ReportsDatabase();
    String balanceSheetDate;
    List<String> currentAssets,nonCurrentAssetsY;
    Formartter ffm=new Formartter();
    private AssetAccount(String sheetDate){
        
balanceSheetDate=sheetDate;
currentAssets=new ArrayList();
currentAssets.add("Investments");
currentAssets.add("Inventories");
currentAssets.add("Accounts Receivables/Debtors");
currentAssets.add("Cash And Bank Balance");
currentAssets.add("Loans And Advances");
currentAssets.add("Deferred Tax Assets");
nonCurrentAssetsY=new ArrayList();

nonCurrentAssetsY.add("Fixed Assets/Non-Current Assets");

    }

    public static synchronized AssetAccount assetProducer(String dateSheet){
   
    return new AssetAccount(dateSheet);
    }
    
    
  public List<Object>  mainAssetTitle(){
  List aTitle =new ArrayList();
  aTitle.add("ASSETS");
   aTitle.add("");
    aTitle.add("");
     aTitle.add("");
     return aTitle;
  }
    public List<Object>  currentAssetTitle(){
  List ctitle =new ArrayList();
  ctitle.add("Current assets");
   ctitle.add("");
    ctitle.add("");
     ctitle.add("");
     return ctitle;
  }
  public List<Object>  nonCurrentAssetTitle(){
  List nCAtitle =new ArrayList();
  nCAtitle.add("Non-current assets");
   nCAtitle.add("");
    nCAtitle.add("");
     nCAtitle.add("");
     return nCAtitle;
  }  
    
  public List<List<Object>> currentAssets(Component c){
  List currentAssetsvx=null;
      List<List<Object>> currentAssetsvT =new ArrayList(); 
   

      
    for(String current:currentAssets){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(current); 

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   
              
        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString())!=0.0){
             
            currentAssetsvx =new ArrayList(); 
            
        currentAssetsvx.add(entry.getValue());
        
         currentAssetsvx.add("");
         
        currentAssetsvx.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString()));
        


        if(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString())==0){
        
            currentAssetsvx.add("-");
            
        }else{
        
        currentAssetsvx.add(parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString()));
       
        }
  
currentAssetsvT.add(currentAssetsvx);
        }
  
  
  }
    
    }


     return currentAssetsvT;
  } 
  
    public List<List<Object>>  nonCurrentAssets(Component c){
        List nonCurrentAssetsK=null;
 
    
  List<List<Object>>  nonCurrentAssets =new ArrayList();
 
      
    for(String nonCurrent:nonCurrentAssetsY){
  
     
Map<Integer, String> level5=rdb.getLevel5Items(nonCurrent); 

Double balanceSheetCurrentAmount=0.0,balanceSheetEarlierAmount=0.0;

for(Map.Entry<Integer, String> entry:level5.entrySet()){
 
   balanceSheetCurrentAmount=parseDouble(rdb.getBalancesSheet(entry.getValue(),balanceSheetDate,c).get(entry.getValue()).toString());
      balanceSheetEarlierAmount=        parseDouble(rdb.getBalancesSheet(entry.getValue(),ffm.earlierDate(balanceSheetDate),c).get(entry.getValue()).toString());
        if(balanceSheetCurrentAmount!=0.0){
             
            nonCurrentAssetsK =new ArrayList(); 
          String accountName=  entry.getValue();
            if(accountName.equalsIgnoreCase("Accumulated Depreciation/Amortization")){
          accountName=  "Less"+" "+"Accumulated Depreciation/Amortization";
            balanceSheetCurrentAmount=-balanceSheetCurrentAmount;
            balanceSheetEarlierAmount=-balanceSheetEarlierAmount;
            }
            
        nonCurrentAssetsK.add(accountName);
        
         nonCurrentAssetsK.add("");
         
        nonCurrentAssetsK.add(balanceSheetCurrentAmount);
        


        if(balanceSheetEarlierAmount==0){
        
            nonCurrentAssetsK.add("-");
            
        }else{
        
        nonCurrentAssetsK.add(balanceSheetEarlierAmount);
       
        }
  
nonCurrentAssets.add(nonCurrentAssetsK);
        }
  
  
  }
    
    }


 
return nonCurrentAssets; 
    
}
}