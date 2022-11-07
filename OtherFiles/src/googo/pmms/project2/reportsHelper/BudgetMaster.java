/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.reportsHelper;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author SSRN
 */
public class BudgetMaster  implements Runnable{
    Formartter fmt= new Formartter();
        Calendar cal = Calendar.getInstance();
        List budgetDetailsList=null;
          AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
           fileInputOutPutStreams fios= new fileInputOutPutStreams();
    public boolean createBudgetItem(List budgetDetails){
        
        boolean budgetCreated=false;
        
        
     int operatingCycleStartMonth=fmt.monthNumber(budgetDetails.get(0).toString());  //0 Entity operating cycle starting month
      int operatingCycleEndMonth=fmt.monthNumber(budgetDetails.get(1).toString());  //1 Entity operating cycle ending month
 int budgetEffectiveMonth=fmt.monthNumber(budgetDetails.get(2).toString());  //2 Budger effective month
  int budgetEffectiveYear=  parseInt(budgetDetails.get(3).toString());     //3 Budger effective year
    String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  if(operatingCycleStartMonth==0&&operatingCycleEndMonth==11){//Normal operating cycle following calander year
  
  if(operatingCycleStartMonth==budgetEffectiveMonth){//Budget set at the start of the year and the whole twelve months to be updated
//  String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  while(nu<12){
   
 budgetDetailsList=new ArrayList();
 
  budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear);
  budgetDetailsList.add(fmt.month(nu));
  budgetDetailsList.add(nu);
  budgetDetailsList.add(monthlyBudgetEstimate);  
 budgetCreated= ancdb.createBudgetItem(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;
  }
  
  
  }else{//Budget set in the middle of the year so only months up to the end of the year need to be updated
  
  int nu=budgetEffectiveMonth;
  while(nu<12){
   
 budgetDetailsList=new ArrayList();
 
  budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear);
  budgetDetailsList.add(fmt.month(nu));
  budgetDetailsList.add(nu);
  budgetDetailsList.add(monthlyBudgetEstimate);  
  budgetCreated=  ancdb.createBudgetItem(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;
  }
  
  }
  
  
  }else{//Operating cycle follow other calender years, there will be crossing of the years
  
  if(operatingCycleStartMonth==budgetEffectiveMonth){//Budget set at the start of the year and the whole twelve months to be updated
//  String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int mont=budgetEffectiveMonth;
  int nu=0;int z=0;
  while(nu<12){
   
 budgetDetailsList=new ArrayList();
 if(mont<12){
  budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear);
  budgetDetailsList.add(fmt.month(mont));
  budgetDetailsList.add(mont);
 budgetDetailsList.add(monthlyBudgetEstimate);  
 }else{
 budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear+1);
  budgetDetailsList.add(fmt.month(z));
  budgetDetailsList.add(z);
 
  budgetDetailsList.add(monthlyBudgetEstimate);  
 
 z++;
 }
  
  
   budgetCreated= ancdb.createBudgetItem(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;
  }
  
  
  }else{//Budget set in the middle of the year so only months up to the end of the year need to be updated
 
     if(budgetEffectiveMonth>operatingCycleEndMonth){ 
//      String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(13-budgetEffectiveMonth)+operatingCycleEndMonth;
  int mont=budgetEffectiveMonth;
  int z=0;
//  fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),mont+"");
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
 if(mont<12){
  budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear);
  budgetDetailsList.add(fmt.month(mont));
  budgetDetailsList.add(mont);
  budgetDetailsList.add(monthlyBudgetEstimate);  
 }else{
 budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear+1);
  budgetDetailsList.add(fmt.month(z));
  budgetDetailsList.add(z);
  budgetDetailsList.add(monthlyBudgetEstimate);  
 
 z++;
 }
  
  
  budgetCreated=  ancdb.createBudgetItem(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;
  
  }
  
     }else if(budgetEffectiveMonth<operatingCycleEndMonth){
     
//     String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(operatingCycleEndMonth-budgetEffectiveMonth)+1;
int mont=budgetEffectiveMonth;
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
  budgetDetailsList.add(budgetDetails.get(4));
    budgetDetailsList.add(budgetDetails.get(5));
  budgetDetailsList.add(budgetEffectiveYear);
  budgetDetailsList.add(fmt.month(mont));
  budgetDetailsList.add(mont);
  budgetDetailsList.add(monthlyBudgetEstimate);  
   budgetCreated= ancdb.createBudgetItem(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;

     }
  
  
  
  }
  }
  }     //4 The account
        //5 Budget amount
    
    
    return budgetCreated;
    }
    
    
     public boolean updateBudgetItem(List budgetDetails,String separator){
            boolean budgetUpdated=false;
        int operatingCycleStartMonth=fmt.monthNumber(budgetDetails.get(4).toString());  //0 Entity operating cycle starting month
      int operatingCycleEndMonth=fmt.monthNumber(budgetDetails.get(5).toString());//1 Entity operating cycle ending month

      int budgetEffectiveMonth=fmt.monthNumber(budgetDetails.get(2).toString());  //2 Budger effective month
  int budgetEffectiveYear=  parseInt(budgetDetails.get(3).toString());     //3 Budger effective year
    String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(1).toString());   
    switch(separator){
    
        case "MonthlyUpdateOnEachItem":
            
             budgetUpdated= ancdb.updateBudgetOnAmonth(budgetDetails); 
             
            break;
    case "GrowthRateUpdateOnEachItem":
        List detailsNow=new ArrayList();
        detailsNow.add(budgetDetails.get(0).toString());
                detailsNow.add(fmt.month(operatingCycleStartMonth));
                detailsNow.add(budgetEffectiveYear);
                 fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),budgetDetails.get(0).toString()+";"+operatingCycleStartMonth+";"+budgetEffectiveYear);
        double intialAmount=parseDouble(ancdb.getInitialAmount(detailsNow));
        
         if(operatingCycleEndMonth==11){//Normal operating cycle following calander year
  
 //Budget set in the middle of the year so only months up to the end of the year need to be updated
  
  int nu=budgetEffectiveMonth;
  while(nu<12){
      
   intialAmount=theNewInitial(intialAmount,parseInt(budgetDetails.get(1).toString()));
   
 budgetDetailsList=new ArrayList();

  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(intialAmount);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);

  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;
  }
  
  
  
  
  }else{//Operating cycle follow other calender years, there will be crossing of the years
  
  //Budget set in the middle of the year so only months up to the end of the year need to be updated
 
     if(budgetEffectiveMonth>operatingCycleEndMonth){ //This means the budget cycle overlaps two years
//      String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(13-budgetEffectiveMonth)+operatingCycleEndMonth;
  int mont=budgetEffectiveMonth;
  int z=0;
//  fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),mont+"");
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
 if(mont<12){
      intialAmount=theNewInitial(intialAmount,parseInt(budgetDetails.get(1).toString()));
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(intialAmount);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);
 
 }else{
intialAmount=theNewInitial(intialAmount,parseInt(budgetDetails.get(1).toString()));
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(intialAmount);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear+1);

 
 z++;
 }


  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;
  
  }
  
     }else if(budgetEffectiveMonth<operatingCycleEndMonth){
     
//     String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(operatingCycleEndMonth-budgetEffectiveMonth)+1;
int mont=budgetEffectiveMonth;
  while(nu<numberOfMonth){
   intialAmount=theNewInitial(intialAmount,parseInt(budgetDetails.get(1).toString()));
 budgetDetailsList=new ArrayList();
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(intialAmount);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear); 
 budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;

     }
  
  
  
  }

  }   
        
        break;
    case "AnualUpdateOnEachItem":
               if(operatingCycleEndMonth==11){//Normal operating cycle following calander year
  
 //Budget set in the middle of the year so only months up to the end of the year need to be updated
  
  int nu=budgetEffectiveMonth;
  while(nu<12){
   
 budgetDetailsList=new ArrayList();

  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(budgetDetails.get(1).toString());
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);

  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;
  }
  
  
  
  
  }else{//Operating cycle follow other calender years, there will be crossing of the years
  
  //Budget set in the middle of the year so only months up to the end of the year need to be updated
 
     if(budgetEffectiveMonth>operatingCycleEndMonth){ //This means the budget cycle overlaps two years
//      String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(13-budgetEffectiveMonth)+operatingCycleEndMonth;
  int mont=budgetEffectiveMonth;
  int z=0;
//  fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),mont+"");
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
 if(mont<12){
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(budgetDetails.get(1).toString());
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);
 
 }else{

  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(budgetDetails.get(1).toString());
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear+1);

 
 z++;
 }


  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;
  
  }
  
     }else if(budgetEffectiveMonth<operatingCycleEndMonth){
     
//     String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(operatingCycleEndMonth-budgetEffectiveMonth)+1;
int mont=budgetEffectiveMonth;
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(budgetDetails.get(1).toString());
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear); 
 budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;

     }
  
  
  
  }

  }   
        
        break;
    case "AnualUpdateOnAllItems":
 
          if(operatingCycleEndMonth==11){//Normal operating cycle following calander year
  
 //Budget set in the middle of the year so only months up to the end of the year need to be updated
  
  int nu=budgetEffectiveMonth;
  while(nu<12){
   
 budgetDetailsList=new ArrayList();

  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(monthlyBudgetEstimate);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);

  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;
  }
  
  
  
  
  }else{//Operating cycle follow other calender years, there will be crossing of the years
  
  //Budget set in the middle of the year so only months up to the end of the year need to be updated
 
     if(budgetEffectiveMonth>operatingCycleEndMonth){ //This means the budget cycle overlaps two years
//      String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(13-budgetEffectiveMonth)+operatingCycleEndMonth;
  int mont=budgetEffectiveMonth;
  int z=0;
//  fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),mont+"");
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
 if(mont<12){
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(monthlyBudgetEstimate);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear);
 
 }else{

  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(monthlyBudgetEstimate);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear+1);

 
 z++;
 }


  budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;
  
  }
  
     }else if(budgetEffectiveMonth<operatingCycleEndMonth){
     
//     String monthlyBudgetEstimate=getMontBudgetEstimate(budgetDetails.get(6).toString());
  int nu=0;
  int numberOfMonth=(operatingCycleEndMonth-budgetEffectiveMonth)+1;
int mont=budgetEffectiveMonth;
  while(nu<numberOfMonth){
   
 budgetDetailsList=new ArrayList();
  budgetDetailsList.add(budgetDetails.get(0));
    budgetDetailsList.add(monthlyBudgetEstimate);
  budgetDetailsList.add(fmt.month(nu));
 budgetDetailsList.add(budgetEffectiveYear); 
 budgetUpdated=  ancdb.updateBudgetOnAmonth(budgetDetailsList);
//  Thread theOne=new Thread(this);
//  theOne.start();
  nu++;mont++;

     }
  
  
  
  }

  }   
        
        break;
    
    
    }
    
    return budgetUpdated;
    }
     public boolean updateBudgetItems(List budgetDetails){
            boolean budgetUpdated=false;
    
    
    
    return budgetUpdated;
    }
    
    private double theNewInitial(double initialAmount, int rate){
  return (double)(initialAmount+((initialAmount*rate)/100));
    }
    
   private String getMontBudgetEstimate(String theAmount){
       Double theMonth=0.0;
   Double theAmountCo=parseDouble(theAmount);
   
   theMonth=theAmountCo/12;
   
   return theMonth.toString();
   } 

    @Override
    public void run() {
    ancdb.createBudgetItem(budgetDetailsList);
    }
    
    
}
