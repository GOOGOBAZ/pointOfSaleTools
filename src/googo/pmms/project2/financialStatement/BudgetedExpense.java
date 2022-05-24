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
public class BudgetedExpense extends MaxmumAmountBorrowedFormulas{
       private static List<BudgetedExpense> budgetedExpenseStore=new ArrayList();
    
    
   fileInputOutPutStreams fios= new fileInputOutPutStreams(); 
    ReportsDatabase rdb = new ReportsDatabase();
    String budgetExpenseDateStart,budgetExpenseDateEnd;
    List budgetExpenseItelms;
    Formartter ffm=new Formartter();
     DecimalFormat df1 = new DecimalFormat("###.##"); 
    private BudgetedExpense(String startDate,String endDate){
      
budgetExpenseDateStart=startDate;
budgetExpenseDateEnd=endDate;
budgetExpenseItelms=rdb.getBudgetExpenseItemsExisting(startDate,endDate);

    }

    public static synchronized BudgetedExpense budgetedExpenseProducer(String startDate,String endDate){
 
   
    
    return new BudgetedExpense(startDate,endDate);
    }
   public List  budgetExpensesTitle(){
  List aTitle =new ArrayList();
    aTitle.add("EXPENSES");
   aTitle.add("");
    aTitle.add("");
     aTitle.add("");
      aTitle.add("");
        aTitle.add("");
     return aTitle;
  }   
   
  public List<List> budgetExpenseS(Component c){
  String varinceFigure,percentGain;  List budgetItemt=null;
  List<List> budgetListList =new ArrayList(); 
  for(Object expenseItem:budgetExpenseItelms){
 budgetItemt =new ArrayList(); 
  double budgetMount=rdb.getbudgetedTotal(expenseItem.toString(),budgetExpenseDateStart,budgetExpenseDateEnd);
  double actualAmount=rdb.getbActualTotal(expenseItem.toString(),budgetExpenseDateStart,budgetExpenseDateEnd,c);
  double incomeSurplus=this.subtract(budgetMount, actualAmount);
  double percentageGain=this.percentage(incomeSurplus, budgetMount);
  percentGain=df1.format(percentageGain)+"%";
  if(incomeSurplus>0){
  varinceFigure="Favourable";
  }else{
    varinceFigure="Unfavourable";
  
  }
   budgetItemt.add(expenseItem);
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
