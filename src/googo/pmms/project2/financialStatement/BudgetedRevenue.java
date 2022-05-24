/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SSRN
 */
public class BudgetedRevenue extends MaxmumAmountBorrowedFormulas{
      private static List<BudgetedRevenue> budgetedRevenueStore=new ArrayList();
    
    
   fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
    ReportsDatabase rdb = new ReportsDatabase();
    String budgetRevenueDateStart,budgetRevenueDateEnd;
    List budgetRevenueItelms;
    Formartter ffm=new Formartter();
       DecimalFormat df1 = new DecimalFormat("###.##"); 
    private BudgetedRevenue(String startDate,String endDate){
        
budgetRevenueDateStart=startDate;
budgetRevenueDateEnd=endDate;
budgetRevenueItelms=rdb.getBudgetRevenueItemsExisting(startDate,endDate);

    }

    public static synchronized BudgetedRevenue budgetedRevenueProducer(String startDate,String endDate){
 
   
    
    return new BudgetedRevenue(startDate,endDate);
    }
   public List  incomeTitle(){
  List aTitle =new ArrayList();
  aTitle.add("Income");
   aTitle.add("");
    aTitle.add("");
     aTitle.add("");
      aTitle.add("");
        aTitle.add("");
     return aTitle;
  }   
   
  public List<List> budgetRevenue(Component c){
  String varinceFigure,percentGain;  List budgetItemt=null;
  List<List> budgetListList =new ArrayList(); 
  for(Object revenueItem:budgetRevenueItelms){
 budgetItemt =new ArrayList(); 
  double budgetMount=rdb.getbudgetedTotal(revenueItem.toString(),budgetRevenueDateStart,budgetRevenueDateEnd);
  double actualAmount=rdb.getbActualTotal(revenueItem.toString(),budgetRevenueDateStart,budgetRevenueDateEnd,c);
  double incomeSurplus=this.subtract(actualAmount, budgetMount);
  double percentageGain=this.percentage(incomeSurplus, budgetMount);
  percentGain=df1.format(percentageGain)+"%";
  if(incomeSurplus>0){
  varinceFigure="Favourable";
  }else{
    varinceFigure="Unfavourable";

  }
   budgetItemt.add(revenueItem);
  budgetItemt.add(budgetMount);
    budgetItemt.add(actualAmount);
     budgetItemt.add(incomeSurplus);
       budgetItemt.add(percentGain);
         budgetItemt.add(varinceFigure);
      budgetListList.add(budgetItemt);
  }
  
  return budgetListList;
  }
}
