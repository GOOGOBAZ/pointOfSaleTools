
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.frameHelper.ListDataModel;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SSRN
 */
public class TheBalanceSheet implements  BalanceSheet {
    Formartter ffm=new Formartter();
String materialDates;
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    ListDataModel model1;
String title;
    List<String> tableHeaders=new ArrayList();
    List< List<Object>> dataBody=new ArrayList();
     
     Map<Integer, List<Object>> data=new HashMap();
     
     
   BalanceSheetUtility bUtility=new BalanceSheetUtility();  
             
    public TheBalanceSheet(String materialDate){
    materialDates=ffm.forDatabaseWithFullYearBeginningWithDate(materialDate);
    }
    



                      public ListDataModel createBalanceSheet(Component c){
                          
                        this.setBodyList(bUtility.comperativePeriodTitles(materialDates, "Year"));
                        
                        LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);

                        AssetAccount asset=AssetAccount.assetProducer(materialDates);

                        boolean bs=true;

                        if(!isAssetsEmpty(c)){

                        this.setBodyList(asset.mainAssetTitle());


                        if(!isCurrentAssetsEmpty(c)){  

                        this.setBodyList(asset.currentAssetTitle());
                        int t=0;
                        while(t<asset.currentAssets(c).size()){

                        this.setBodyList(asset.currentAssets(c).get(t));
                        
                        t++;
                        }
                            this.setBodyList(totalcurrentAssets(c));

                            if(!isNonCurrentAssetsEmpty(c)){

                            this.setBodyList(asset.nonCurrentAssetTitle());

                            int p=0;

                            while(p<asset.nonCurrentAssets(c).size()){

                            this.setBodyList(asset.nonCurrentAssets(c).get(p));
                            p++;
                            }

                            this.setBodyList(totalNoncurrentAssets(c));

                            this.setBodyList(this.totalAssets(c));
//
                            if(!isLiabilitiesEmpty(c)){ 

                            this.setBodyList(liabEqui.mainLiabilitiesEquityTitle());

                            if(!isCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.currentLiabilitiesTitle());
                            int y=0;     
                            while(y<liabEqui.currentLiabilities(c).size()){

                            this.setBodyList(liabEqui.currentLiabilities(c).get(y));
                            y++;
                            }
                            this.setBodyList(totalCurrentLiabilities(c));

                            if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList(totalNoncurrentLiabilities(c)); 

                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){

                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }


                            }else{
                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

                            }

                            } else if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList( totalNoncurrentLiabilities(c));
                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }
                            }else  if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            } }      
                            }else{

                            this.setBodyList(this.totalAssets(c));

                            if(!isLiabilitiesEmpty(c)){ 

                            this.setBodyList(liabEqui.mainLiabilitiesEquityTitle());

                            if(!isCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.currentLiabilitiesTitle());

                            int y=0;     
                            while(y<liabEqui.currentLiabilities(c).size()){

                            this.setBodyList(liabEqui.currentLiabilities(c).get(y));
                            y++;
                            }

                            this.setBodyList( totalCurrentLiabilities(c));

                            if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());

                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }

                            this.setBodyList(liabEqui.nonCurrentLiabilities(c));

                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){

                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }


                            }else{
                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

                            }

                            }else if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList( liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList( totalNoncurrentLiabilities(c));
                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }
                            }else if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList(totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

}
                            }

                            }else  if(!isNonCurrentAssetsEmpty(c)){

                            this.setBodyList(asset.nonCurrentAssetTitle());
                            int p=0;

                            while(p<asset.nonCurrentAssets(c).size()){

                            this.setBodyList(asset.nonCurrentAssets(c).get(p));
                            p++;
                            }
                            this.setBodyList(totalNoncurrentAssets(c));
                            
                            
                            this.setBodyList(this.totalAssets(c));


                            if(!isLiabilitiesEmpty(c)){

                            this.setBodyList(liabEqui.mainLiabilitiesEquityTitle());

                            if(!isCurrentLiabilityEmpty(c)){

                            this.setBodyList( liabEqui.currentLiabilitiesTitle());
                            int y=0;     
                            while(y<liabEqui.currentLiabilities(c).size()){

                            this.setBodyList(liabEqui.currentLiabilities(c).get(y));
                            y++;
                            }
                            this.setBodyList(totalCurrentLiabilities(c));
                            if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList(totalNoncurrentLiabilities(c));
                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }
                            }else{
                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

                            }

                            }else if(!isNonCurrentLiabilityEmpty(c)){



                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList(totalNoncurrentLiabilities(c));
                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

                            }else if(!isEquityEmpty(c)){

                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));

                            }}   }



                            }
                            else if(!isLiabilitiesEmpty(c)){

                            this.setBodyList(liabEqui.mainLiabilitiesEquityTitle());

                            if(!isCurrentLiabilityEmpty(c)){
                            this.setBodyList(liabEqui.currentLiabilitiesTitle());
                            int y=0;     
                            while(y<liabEqui.currentLiabilities(c).size()){

                            this.setBodyList(liabEqui.currentLiabilities(c).get(y));
                            y++;
                            }
                            this.setBodyList(totalCurrentLiabilities(c));


                            if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList(totalNoncurrentLiabilities(c));

                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }


                            }else{
                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }


                            }
                            }else if(!isNonCurrentLiabilityEmpty(c)){

                            this.setBodyList(liabEqui.nonCurrentLiabilitiesTitle());
                            int z=0;
                            while(z<liabEqui.nonCurrentLiabilities(c).size()){

                            this.setBodyList( liabEqui.nonCurrentLiabilities(c).get(z));
                            z++;
                            }
                            this.setBodyList(totalNoncurrentLiabilities(c));

                            this.setBodyList(this.totalLiabilities(c));

                            if(!isEquityEmpty(c)){
                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList( totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }

                            } else if(!isEquityEmpty(c)){

                            this.setBodyList( liabEqui.equityTitle());
                            int jk=0;
                            while(jk<liabEqui.equity(c).size()){
                            this.setBodyList( liabEqui.equity(c).get(jk));
                            jk++;
                            }
                            this.setBodyList(totalequity(c));
                            this.setBodyList(this.totalLiabilitiesequity(c));
                            }


                            }
                            else{

                            bs=false;    
                            }



                              if(bs){


                            model1= new ListDataModel( getBodyList(), getTableHeaders());
                           

                       return model1;
                              }

                            return model1;
                            }
                           private List totalLiabilitiesequity(Component c){
                        
                           LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);
                            List amount1=bUtility.totalSummation(liabEqui.equity(c), new ArrayList());

                            List amount2=bUtility.totalSummation(liabEqui.currentLiabilities(c), liabEqui.nonCurrentLiabilities(c));

                            List totaLiabilitiesEquity=new ArrayList();

                            totaLiabilitiesEquity.add("Total liabilities and equity");
                            totaLiabilitiesEquity.add("");
                            
                            if(amount1.get(0).toString().equals("-")&&amount2.get(0).toString().equals("-")){
                             totaLiabilitiesEquity.add("-");
                             }else if(amount1.get(0).toString().equals("-")){
                              totaLiabilitiesEquity.add(amount2.get(0).toString());
                             
                             }else if(amount2.get(0).toString().equals("-")){
                             totaLiabilitiesEquity.add(amount1.get(0).toString());
                             }else{
                            
                                
                            totaLiabilitiesEquity.add((parseDouble(amount1.get(0).toString())+parseDouble(amount2.get(0).toString())));
                            }
     
                            
//                            totaLiabilitiesEquity.add((parseDouble(amount1.get(0).toString())+parseDouble(amount2.get(0).toString())));
                            
                             if(amount1.get(1).toString().equals("-")||amount1.get(1).toString().equals("-")){
                             totaLiabilitiesEquity.add("-");
                             }else{
                            totaLiabilitiesEquity.add((parseDouble(amount1.get(1).toString())+parseDouble(amount2.get(1).toString())));
                            }
                            

                            return   totaLiabilitiesEquity;


                            }


                            private List totalLiabilities(Component c){

     LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);
                            List tatoLiabi=new ArrayList();

                            List amount=bUtility.totalSummation(liabEqui.currentLiabilities(c), liabEqui.nonCurrentLiabilities(c));
                            tatoLiabi.add("Total liabilities");
                            tatoLiabi.add("");
                            tatoLiabi.add(amount.get(0));
                            tatoLiabi.add(amount.get(1));

                            return   tatoLiabi;

                            }

                            private List totalequity(Component c){


     LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);
                            List totaequity=new ArrayList();

                            List amount=bUtility.totalSummation(liabEqui.equity(c), new ArrayList());
                            totaequity.add("Total equity");
                            totaequity.add("");
                            totaequity.add(amount.get(0));
                            totaequity.add(amount.get(1));

                            return   totaequity;

                            }

                            private List totalNoncurrentLiabilities(Component c){


     LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);
                            List totalNonCurrentLiabilities=new ArrayList();

                            List amount=bUtility.totalSummation(liabEqui.nonCurrentLiabilities(c), new ArrayList());
                            totalNonCurrentLiabilities.add("Total non-current liabilities");
                            totalNonCurrentLiabilities.add("");
                            totalNonCurrentLiabilities.add(amount.get(0));
                            totalNonCurrentLiabilities.add(amount.get(1));

                            return   totalNonCurrentLiabilities;

                            }
                            private List totalCurrentLiabilities(Component c){

     LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);

                            List totalCurrentLiabilities=new ArrayList();

                            List amount=bUtility.totalSummation(liabEqui.currentLiabilities(c), new ArrayList());
                            totalCurrentLiabilities.add("Total current liabilities");
                            totalCurrentLiabilities.add("");
                            totalCurrentLiabilities.add(amount.get(0));
                            totalCurrentLiabilities.add(amount.get(1));

                            return   totalCurrentLiabilities;

                            }

                         
                            
                             private List totalAssets(Component c){
                    AssetAccount asset=AssetAccount.assetProducer(materialDates);
               
                            List tatoAsset=new ArrayList();

                            List amount=bUtility.totalSummation(asset.currentAssets(c), asset.nonCurrentAssets(c));

                            tatoAsset.add("Total assets");
                            tatoAsset.add("");
                            tatoAsset.add(amount.get(0));
                            tatoAsset.add(amount.get(1));

                            return   tatoAsset;


                            }
                            public List totalcurrentAssets(Component c){
                              
                                AssetAccount asset=AssetAccount.assetProducer(materialDates);
  
                            List currentAssets =new ArrayList();
                            List amount1=bUtility.totalSummation(asset.currentAssets(c), new ArrayList());
                            currentAssets.add("Total current assets");
                            currentAssets.add("");
                            currentAssets.add(amount1.get(0));
                            currentAssets.add(amount1.get(1));
                            return currentAssets;
                            }


                            public List  totalNoncurrentAssets(Component c){
                                
                                AssetAccount asset= AssetAccount.assetProducer(materialDates);
   
                            List nonCurrentAssets =new ArrayList();

                            List amount1=bUtility.totalSummation(asset.nonCurrentAssets(c), new ArrayList());
                            
                            nonCurrentAssets.add("Total non-current assets");
                            nonCurrentAssets.add("");
                            nonCurrentAssets.add(amount1.get(0));
                            nonCurrentAssets.add(amount1.get(1));
                            return nonCurrentAssets;
                            }
                            public boolean isAssetsEmpty(Component c){
                                AssetAccount asset=AssetAccount.assetProducer(materialDates);
     
                            boolean assetIsEmpty=false;
                            if(asset.currentAssets(c).isEmpty()&&asset.nonCurrentAssets(c).isEmpty()){
                            assetIsEmpty=true;
                            }

                            return assetIsEmpty;
                            }
                            public boolean isCurrentAssetsEmpty(Component c){
                                AssetAccount asset=AssetAccount.assetProducer(materialDates);
 
                            boolean currentAssetIsEmpty=false;
                            if(asset.currentAssets(c).isEmpty()){
                            currentAssetIsEmpty=true;

                            }

                            return currentAssetIsEmpty;
                            }
                            public boolean isNonCurrentAssetsEmpty(Component c){
                                AssetAccount asset=AssetAccount.assetProducer(materialDates);
 
                            boolean nonCurrentAssetIsEmpty=false;
                            if(asset.nonCurrentAssets(c).isEmpty()){
                            nonCurrentAssetIsEmpty=true;

                            }

                            return nonCurrentAssetIsEmpty;
                            }

                            public boolean isEquityEmpty(Component c){

                            LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);
                            
                            boolean equityIsEmpty=false;
                            if(liabEqui.equity(c).isEmpty()){

                            equityIsEmpty=true;
                            }

                            return equityIsEmpty;
                            }
                            public boolean isLiabilitiesEmpty(Component c){
                                
                            boolean liabilityIsEmpty=false;

                            LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates); 
                            
                            if((liabEqui.currentLiabilities(c).isEmpty()&&liabEqui.nonCurrentLiabilities(c).isEmpty()&&liabEqui.equity(c).isEmpty())){
                            liabilityIsEmpty=true;
                            }

                            return liabilityIsEmpty;
                            }
                            public boolean isCurrentLiabilityEmpty(Component c){

                            LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);                     
                            boolean currentLiabilityIsEmpty=false;
                            if(liabEqui.currentLiabilities(c).isEmpty()){

                            currentLiabilityIsEmpty=true;
                            }


                            return currentLiabilityIsEmpty;
                            }
                            public boolean isNonCurrentLiabilityEmpty(Component c){

                            LiabilityEquityAccounts liabEqui=LiabilityEquityAccounts.assetProducer(materialDates);                    
                            boolean nonCurrentLiabilityIsEmpty=false;
                            if(liabEqui.nonCurrentLiabilities(c).isEmpty()){

                            nonCurrentLiabilityIsEmpty=true;
                            }

                            return nonCurrentLiabilityIsEmpty;
                            }

                            

    @Override
    public String getTitle(String date) {
        return title;
    }

    @Override
    public List<String> getTableHeaders() {
        tableHeaders.add(null);
           tableHeaders.add(null);
              tableHeaders.add(null);
                 tableHeaders.add(null);
                 
       return tableHeaders;
    }

    @Override
    public List<List<Object>> getBodyList() {
       return dataBody;
    }

    @Override
    public Map<Integer, List<Object>> getBodyMap() {
        return data;
    }

    @Override
    public void setTitle(String title) {
       this.title=title;
    }

    @Override
    public void setTableHeader(String header) {
        tableHeaders.add(header);
    }

    @Override
    public void setBodyList(List body) {
        dataBody.add(body);
    }

    @Override
    public void setBodyMap(Integer index, List<Object> body) {
        data.put(index, body);
    }

  
    

}
