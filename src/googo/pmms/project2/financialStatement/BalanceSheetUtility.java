/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.reportsHelper.netProfits;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SSRN
 */

public class BalanceSheetUtility extends MaxmumAmountBorrowedFormulas implements SumTwoLists,DiffTwoLists {
    
    fileInputOutPutStreams fios= new fileInputOutPutStreams();  
    Formartter ffm =new Formartter();
      netProfits netprofits= new netProfits();
         DecimalFormat df1 = new DecimalFormat("###.##"); 
    public List comperativePeriodTitles(String materialDate,String ComperativePeriod){
    
        
        
      List comPT =new ArrayList();
      
      
      switch(ComperativePeriod){
      
      
          case "Year":
              
        comPT.add("");

        comPT.add("");
        comPT.add(ffm.yearOnlyFromDateString(materialDate));
      comPT.add(ffm.yearOnlyFromDateString(ffm.earlierDate(materialDate)));
        
        break;
          case "Month":
              
             comPT.add("");

        comPT.add("");
        
        comPT.add(ffm.monthOnlyFromDateString(materialDate));
        
      comPT.add(ffm.monthOnlyFromDateString(ffm.earlierDate(materialDate))); 
              break;
      
 
      
      }

     return comPT;
    
    
    }

    @Override
    public List totalSummation(List<List<Object>> list1a, List<List<Object>> list1b) {
        
        Double amount1=0.0,amount2=0.0;int i=0;List sum=new ArrayList();
        if(list1b.isEmpty()){
        while(i<list1a.size()){
        
        amount1+=parseDouble(list1a.get(i).get(2).toString());
        if(!list1a.get(i).get(3).toString().equalsIgnoreCase("-")){
        amount2+=parseDouble(list1a.get(i).get(3).toString());
        }
        i++;
        }

        }else{
       list1a.addAll(list1b);
        
        while(i<list1a.size()){
        
        
        amount1+=parseDouble(list1a.get(i).get(2).toString());
        
        if(!list1a.get(i).get(3).toString().equalsIgnoreCase("-")){
        amount2+=parseDouble(list1a.get(i).get(3).toString());
        }
        i++;
        
//        fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),amount1+";"+amount2); 
        }
        }
             if(amount1==0.0){
              sum.add("-");
             }else{
              sum.add(amount1.toString());
             }
             
             if(amount2==0.0){
              sum.add("-");
             }else{
              sum.add(amount2.toString());
             }
          
              
      return sum;  
    }

    @Override
    public List diffTwoNumbers(List<List<Object>> list1a, List<List<Object>> list1b) {
        
        Double amount1=0.0,amount2=0.0,amount3=0.0,amount4=0.0,amountx1=0.0,amountx2=0.0;int i=0;List diff=new ArrayList();
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),list1a.size()+";"+list1b.size());
        if(list1b.isEmpty()){
        while(i<list1a.size()){
        
        amountx1+=parseDouble(list1a.get(i).get(2).toString());
        if(!list1a.get(i).get(3).toString().equalsIgnoreCase("-")){
        amountx2+=parseDouble(list1a.get(i).get(3).toString());
        }
        i++;
        }

        }else{
       
        
       for(List lista:list1a){
        amount1+=parseDouble(lista.get(2).toString());
        
        if(!lista.get(3).toString().equalsIgnoreCase("-")){
        amount2+=parseDouble(lista.get(3).toString());
        } }
        
       for(List listb:list1b){
   
        amount3+=parseDouble(listb.get(2).toString());
        
        if(!listb.get(3).toString().equalsIgnoreCase("-")){
        amount4+=parseDouble(listb.get(3).toString());
        }  }
     amountx1=   amount1-amount3;
         amountx2=amount2-amount4;
         
//    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),amountx1+";"+amountx2+";"+amount1+";"+amount2+";"+amount3+";"+amount4);     
        }
             if(amountx1==0.0){
              diff.add("-");
             }else{
              diff.add(amountx1.toString());
             }
             
             if(amountx2==0.0){
              diff.add("-");
             }else{
              diff.add(amountx2.toString());
             }
          
              
      return diff; 
    }

    @Override
    public List totalSummation1(List<List> list1a, List<List> list1b) {
     double amount1=0.0,amount2=0.0,amount3=0.0, percent=0.0;int i=0;List sum=new ArrayList();
        if(list1b.isEmpty()){
            
       for(List tobeSumed:list1a){
        amount1+=parseDouble(tobeSumed.get(1).toString());
        amount2+=parseDouble(tobeSumed.get(2).toString());
          amount3+=parseDouble(tobeSumed.get(3).toString());
          percent=this.percentage(amount3, amount1);
        }

        }else{
       list1a.addAll(list1b);
        
       for(List tobeSumed:list1a){
        amount1+=parseDouble(tobeSumed.get(1).toString());
        amount2+=parseDouble(tobeSumed.get(2).toString());
          amount3+=parseDouble(tobeSumed.get(3).toString());
           percent=this.percentage(amount3, amount1);
        }
        }
             if(amount1==0.0){
              sum.add("-");
             }else{
              sum.add(amount1+"");
             }
             
             if(amount2==0.0){
              sum.add("-");
             }else{
              sum.add(amount2+"");
             }
            if(amount3==0.0){
              sum.add("-");
             }else{
              sum.add(amount3+"");
             }
             sum.add(df1.format(percent)+"%"); 
      return sum;  
    }

    @Override
    public List diffTwoNumbers1(List<List> list1a, List<List> list1b) {
        
     double amount1=0.0,amount2=0.0,amount3=0.0,amount4=0.0,amount5=0.0,amount6=0.0,amountx1=0.0,amountx2=0.0,amountx3=0.0,amountx4=0.0,percent=0.0;List diff=new ArrayList();
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),list1a.size()+";"+list1b.size());
        if(list1b.isEmpty()){
            
       for(List theItem:list1a){
        
        amountx1+=parseDouble(theItem.get(1).toString());
      amountx2+=parseDouble(theItem.get(2).toString());
      amountx3+=parseDouble(theItem.get(3).toString());
      percent=this.percentage(amountx3, amountx1);
        }

        }else{
       
        
       for(List lista:list1a){
        amount1+=parseDouble(lista.get(1).toString());
        

        amount2+=parseDouble(lista.get(2).toString());
       
        amount3+=parseDouble(lista.get(3).toString()); 
       
       }
        
       for(List listb:list1b){
   
        amount4+=parseDouble(listb.get(1).toString());
        
      
        amount5+=parseDouble(listb.get(2).toString());
        
        amount6+=parseDouble(listb.get(2).toString());
         }
     amountx1=   amount1-amount4;
         amountx2=amount2-amount5;
         amountx3=amount3-amount6;
         percent=this.percentage(amountx3, amountx1);
         
//    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),amountx1+";"+amountx2+";"+amount1+";"+amount2+";"+amount3+";"+amount4);     
        }
             if(amountx1==0.0){
              diff.add("-");
             }else{
              diff.add(amountx1+"");
             }
             
             if(amountx2==0.0){
              diff.add("-");
             }else{
              diff.add(amountx2+"");
             }
             
              if(amountx3==0.0){
              diff.add("-");
             }else{
              diff.add(amountx3+"");
             }
           
              diff.add(df1.format(percent)+"%");
             
              
      return diff; 
    }

   

   
}
