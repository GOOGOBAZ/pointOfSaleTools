

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SSRN
 */
public class ProfitAndLossNew implements BalanceSheet {
    
    Formartter ffm=new Formartter();
String materialDates1,materialDates2;
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    ListDataModel model1;
String title;
    List<String> tableHeaders=new ArrayList();
    List< List<Object>> dataBody=new ArrayList();
     
     Map<Integer, List<Object>> data=new HashMap();
     
     
   BalanceSheetUtility bUtility=new BalanceSheetUtility();  
             
    public ProfitAndLossNew(List materialDate){
    materialDates1=ffm.forDatabaseWithFullYearBeginningWithDate(materialDate.get(0).toString());
    materialDates2=ffm.forDatabaseWithFullYearBeginningWithDate(materialDate.get(1).toString());
    }
    



                        public ListDataModel createProfitAndLoss(Component c){
                            
                        this.setBodyList(bUtility.comperativePeriodTitles(materialDates2, "Year"));
                        
                        IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);

                        ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);

                        boolean bs=true;

                        if(!this.isIncomeEmpty(c)){

                        this.setBodyList(income.revenueTitle());


                        if(!isMainIncomeEmpty(c)){  

//                        this.setBodyList(income.mainIncomeTitle());
                        int t=0;
                        while(t<income.mainIncome(c).size()){

                        this.setBodyList(income.mainIncome(c).get(t));
                        
                        t++;
                        }
//                            this.setBodyList(totalmainIncome(c));

                            if(!isOtheIncomeEmpty(c)){

//                            this.setBodyList(income.otherIncomeTitle());

                            int p=0;

                            while(p<income.otherIncomes(c).size()){

                            this.setBodyList(income.otherIncomes(c).get(p));
                            p++;
                            }

//                            this.setBodyList(totalOtherIncome());

                            this.setBodyList(this.totalAllIncome(c));
//
                            if(!isExpenseEmpty(c)){ 

                            this.setBodyList(expense.expenseTitle());

                            if(!isExpensesEmpty(c)){
                            int y=0;     
                            while(y<expense.expensesDeclared(c).size()){

                            this.setBodyList(expense.expensesDeclared(c).get(y));
                            y++;
                            }
                            this.setBodyList(totalExpenses(c));
} } } }else{
                 
                            if(!isOtheIncomeEmpty(c)){

//                            this.setBodyList(income.otherIncomeTitle());

                            int p=0;

                            while(p<income.otherIncomes(c).size()){

                            this.setBodyList(income.otherIncomes(c).get(p));
                            p++;
                            }

//                            this.setBodyList(totalOtherIncome());

                            this.setBodyList(this.totalAllIncome(c));
//
                            if(!isExpenseEmpty(c)){ 

                            this.setBodyList(expense.expenseTitle());

                            if(!isExpensesEmpty(c)){
                            int y=0;     
                            while(y<expense.expensesDeclared(c).size()){

                            this.setBodyList(expense.expensesDeclared(c).get(y));
                            y++;
                            }
                            this.setBodyList(totalExpenses(c));
} } }       
                        
                        }
                     this.setBodyList(profitBeforeTax(c));
                       
                            if(!isIncomeTaxEmpty(c)){
                                
                           this.setBodyList( expense.incomeTaxE(c).get(0));
                             this.setBodyList(profitForTheYear(c));
                            }
                            
                            
                            if(!isRetainedEarningStartYearEmpty(c)){
                            
                             this.setBodyList( expense.retainedEarningY(c).get(0));
                            }
                          
                            
                             if(!isRestatementRetainedErrorsEmpty(c)){
                            
                             this.setBodyList( expense.periodErrorS(c).get(0));
                            }
                             
                             
                             if(!isRestatementRetainedAccountPriEmpty(c)){
                            
                             this.setBodyList( expense.periodAccountPolicy(c).get(0));
                            }
                             
                             
                             if(!isDevidendPaidEmpty(c)){
                            
                             this.setBodyList( expense.devidendsPaidInAyear(c).get(0));
                            }
                             
                            this.setBodyList(retainedEarningsAtEndYear(c));    
                        
                        
                        }else if(!isExpenseEmpty(c)){ 

                            this.setBodyList(expense.expenseTitle());

                            if(!isExpensesEmpty(c)){
                            int y=0;     
                            while(y<expense.expensesDeclared(c).size()){

                            this.setBodyList(expense.expensesDeclared(c).get(y));
                            y++;
                            }
//                            this.setBodyList(totalExpenses());

                            } 
                
                     this.setBodyList(profitBeforeTax(c));
                       
                            if(!isIncomeTaxEmpty(c)){
                                
                           this.setBodyList( expense.incomeTaxE(c).get(0));
                             this.setBodyList(profitForTheYear(c));
                            }
                            
                            
                            if(!isRetainedEarningStartYearEmpty(c)){
                            
                             this.setBodyList( expense.retainedEarningY(c).get(0));
                            }
                          
                            
                             if(!isRestatementRetainedErrorsEmpty(c)){
                            
                             this.setBodyList( expense.periodErrorS(c).get(0));
                            }
                             
                             
                             if(!isRestatementRetainedAccountPriEmpty(c)){
                            
                             this.setBodyList( expense.periodAccountPolicy(c).get(0));
                            }
                             
                             
                             if(!isDevidendPaidEmpty(c)){
                            
                             this.setBodyList( expense.devidendsPaidInAyear(c).get(0));
                            }
                             
                            this.setBodyList(retainedEarningsAtEndYear(c));  
                        
                        
                        
                        }else{

                            bs=false;    
                            }


                              if(bs){


                            model1= new ListDataModel( getBodyList(), getTableHeaders());
                           

                       return model1;
                              }

                            return model1;
                            }

                           private List profitForTheYear(Component c){
                        List<List<Object>> totalIncome=new ArrayList();
                        List<List<Object>> totalExpenses=new ArrayList();
                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 
                           
                             IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
                             
                           totalIncome.addAll( income.mainIncome(c));
                            totalIncome.addAll( income.otherIncomes(c));
                            
                            
                            totalExpenses.addAll(expense.expensesDeclared(c));
                             totalExpenses.addAll(expense.incomeTaxE(c));
                  
                             List profitForYear=new ArrayList();
                            List amount=bUtility.diffTwoNumbers(totalIncome,  totalExpenses);
                            profitForYear.add("Profit for the period");
                            profitForYear.add("");
                            profitForYear.add(amount.get(0));
                            profitForYear.add(amount.get(1));

                            return   profitForYear;

                            }


                            private List profitBeforeTax(Component c){
List<List<Object>> totalIncome=new ArrayList();

     ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 
     IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
     totalIncome.addAll( income.mainIncome(c));
        totalIncome.addAll(income.otherIncomes(c));
                            List totalPBTax=new ArrayList();
//                            fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),expense.expensesDeclared(c).size()+"");
                            List amount=bUtility.diffTwoNumbers(totalIncome, expense.expensesDeclared(c));
                            totalPBTax.add("Profit before tax");
                            totalPBTax.add("");
                            totalPBTax.add(amount.get(0));
                            totalPBTax.add(amount.get(1));

                            return   totalPBTax;

                            }

                            
                            
                   

                            private List retainedEarningsAtEndYear(Component c){
List<List<Object>> retainedInTotalIncome=new ArrayList();
List<List<Object>> retainedInTotalExpenses=new ArrayList();
 ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 
     IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
                            List retainEndYear=new ArrayList();
                            retainedInTotalIncome.addAll(income.mainIncome(c));
                             retainedInTotalIncome.addAll(income.otherIncomes(c));
                                 
                        retainedInTotalExpenses.addAll(expense.expensesDeclared(c));
                         retainedInTotalExpenses.addAll(expense.incomeTaxE(c));
                        retainedInTotalExpenses.addAll(expense.retainedEarningY(c));
                       retainedInTotalExpenses.addAll(expense.periodErrorS(c));
                        retainedInTotalExpenses.addAll(expense.periodAccountPolicy(c));
                        retainedInTotalExpenses.addAll(expense.devidendsPaidInAyear(c));
                   
                            List amount=bUtility.diffTwoNumbers(retainedInTotalIncome, retainedInTotalExpenses);
                            retainEndYear.add("Retained earnings at end of Period");
                            retainEndYear.add("");
                            retainEndYear.add(amount.get(0));
                            retainEndYear.add(amount.get(1));

                            return   retainEndYear;

                            }
                            private List totalExpenses(Component c){

      ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 

                            List totalExpenses=new ArrayList();

                            List amount=bUtility.totalSummation(expense.expensesDeclared(c), new ArrayList());
                            totalExpenses.add("Total Expenses");
                            totalExpenses.add("");
                            totalExpenses.add(amount.get(0));
                            totalExpenses.add(amount.get(1));

                            return   totalExpenses;

                            }

                         
                            
                             private List totalAllIncome(Component c){
                       IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
               
                            List tatoAsset=new ArrayList();

                            List amount=bUtility.totalSummation(income.mainIncome(c), income.otherIncomes(c));

                            tatoAsset.add("Total Income");
                            tatoAsset.add("");
                            tatoAsset.add(amount.get(0));
                            tatoAsset.add(amount.get(1));

                            return   tatoAsset;


                            }
                            public List totalmainIncome(Component c){
                              
                             IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
  
                            List mainIncome =new ArrayList();
                            List amount1=bUtility.totalSummation(income.mainIncome(c), new ArrayList());
                            mainIncome.add("Total Main Income");
                            mainIncome.add("");
                            mainIncome.add(amount1.get(0));
                            mainIncome.add(amount1.get(1));
                            return mainIncome;
                            }


                            public List  totalOtherIncome(Component c){
                             IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
   
                            List otherIncome=new ArrayList();

                            List amount1=bUtility.totalSummation(income.otherIncomes(c), new ArrayList());
                            otherIncome.add("Total OtherIncome");
                            otherIncome.add("");
                            otherIncome.add(amount1.get(0));
                            otherIncome.add(amount1.get(1));
                            return otherIncome;
                            }
                            public boolean isIncomeEmpty(Component c){
                                IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
     
                            boolean incomeIsEmpty=false;
                            if(income.mainIncome(c).isEmpty()&&income.otherIncomes(c).isEmpty()){
                            incomeIsEmpty=true;
                            }

                            return incomeIsEmpty;
                            }
                            public boolean isMainIncomeEmpty(Component c){
                            IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
 
                            boolean mainIncomeIsEmpty=false;
                            if(income.mainIncome(c).isEmpty()){
                            mainIncomeIsEmpty=true;

                            }

                            return mainIncomeIsEmpty;
                            }
                            public boolean isOtheIncomeEmpty(Component c){
                                 IncomeAccounts income=IncomeAccounts.incomeProducer(materialDates1,materialDates2);
 
                            boolean otherIncomeIsEmpty=false;
                            if(income.otherIncomes(c).isEmpty()){
                            otherIncomeIsEmpty=true;

                            }

                            return otherIncomeIsEmpty;
                            }

                            public boolean isRetainedEarningStartYearEmpty(Component c){

                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 
                            
                            boolean retainedEarningsIsEmpty=false;
                            if(expense.retainedEarningY(c).isEmpty()){

                            retainedEarningsIsEmpty=true;
                            }

                            return retainedEarningsIsEmpty;
                            }
                            public boolean isExpenseEmpty(Component c){
                                
                            boolean expenseIsEmpty=false;

                            ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2); 
                            
                            if((expense.expensesDeclared(c).isEmpty()&&expense.devidendsPaidInAyear(c).isEmpty()&&expense.incomeTaxE(c).isEmpty()&&expense.periodAccountPolicy(c).isEmpty()&&expense.periodErrorS(c).isEmpty()&&expense.retainedEarningY(c).isEmpty())){
                            expenseIsEmpty=true;
                            }

                            return expenseIsEmpty;
                            }
                            public boolean isExpensesEmpty(Component c){

                          ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);                    
                            boolean expenseIsEmpty=false;
                            if(expense.expensesDeclared(c).isEmpty()){

                            expenseIsEmpty=true;
                            }


                            return expenseIsEmpty;
                            }
                            
                            
                            
                            public boolean isIncomeTaxEmpty(Component c){

                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);  
                           
                            boolean incomeTaxIsEmpty=false;
                            
                            if(expense.incomeTaxE(c).isEmpty()){

                            incomeTaxIsEmpty=true;
                            }

                            return incomeTaxIsEmpty;
                            }
                       public boolean isRestatementRetainedErrorsEmpty(Component c){

                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);  
                           
                            boolean ErrorRestatmentTaxIsEmpty=false;
                            
                            if(expense.periodErrorS(c).isEmpty()){

                            ErrorRestatmentTaxIsEmpty=true;
                            }

                            return ErrorRestatmentTaxIsEmpty;
                            }
                           public boolean isRestatementRetainedAccountPriEmpty(Component c){

                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);  
                           
                            boolean accountPrincePriEmpty=false;
                            
                            if(expense.periodErrorS(c).isEmpty()){

                            accountPrincePriEmpty=true;
                            }

                            return accountPrincePriEmpty;
                            }
                
                 
                  public boolean isDevidendPaidEmpty(Component c){

                           ExpensesAccounts expense=ExpensesAccounts.expenseProducer(materialDates1,materialDates2);  
                           
                            boolean isdevidendsPaid=false;
                            
                            if(expense.devidendsPaidInAyear(c).isEmpty()){

                            isdevidendsPaid=true;
                            }

                            return isdevidendsPaid;
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
