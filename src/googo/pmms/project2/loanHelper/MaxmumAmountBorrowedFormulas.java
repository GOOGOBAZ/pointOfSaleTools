/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.loanHelper;

import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import static java.lang.Double.parseDouble;
import java.time.YearMonth;

/**
 *
 * @author Stanchart
 */
public class MaxmumAmountBorrowedFormulas {
    
//  DatabaseQuaries dbq =new DatabaseQuaries();   
     fileInputOutPutStreams fios= new fileInputOutPutStreams();  
    
    public double add (double a,double b){
    double firstOp=0,secondOp=0,sum=0;
    firstOp=a;
    secondOp=b;
    sum=firstOp+secondOp;
    return sum;
    }
     
     public  double multiply (double a,double b){
    double firstOp=0,secondOp=0,product=0;
    firstOp=a;
    secondOp=b;
    product=firstOp*secondOp;
    return product;
    
    
    }
      public  double devide (double a,double b){
         
   double dividend=0,divisor=0, quotient=0;
    dividend=a;
    divisor=b;
     if(divisor==0){
        
         quotient= 0.0;
     }else{
         
    quotient= dividend/divisor;
     
     }
 
        return quotient;
    
    
    }
       public  double subtract (double a,double b){
    double firstOp=0,secondOp=0,difference=0;
    firstOp=a;
    secondOp=b;
    difference=firstOp-secondOp;
    return difference;
    
    
    }
        public double percentage (double a, double b){
   double dividend=0.0,divisor=0.0,quotient=0,percent=0;
    dividend=a;
    divisor=b;
     if(divisor==0){
         
         quotient= 0.0;
     }else{
         
    quotient= dividend/divisor;
     
     }
    percent=quotient*100;
    return percent;
    
    }
    
     public long add (long a,long b){
    long firstOp=0,secondOp=0,sum=0;
    firstOp=a;
    secondOp=b;
    sum=firstOp+secondOp;
    return sum;
    }
     
     public  long multiply (long a,long b){
    long firstOp=0,secondOp=0,product=0;
    firstOp=a;
    secondOp=b;
    product=firstOp*secondOp;
    return product;
    
    
    }
      public  long devide (long a,long b){
         
   long dividend=0,divisor=0, quotient=0;
    dividend=a;
    divisor=b;
     if(divisor==0){
        
         quotient= 0;
     }else{
         
    quotient= dividend/divisor;
     
     }
 
        return quotient;
    
    
    }
       public  long subtract (long a,long b){
    long firstOp=0,secondOp=0,difference=0;
    firstOp=a;
    secondOp=b;
    difference=firstOp-secondOp;
    return difference;
    
    
    }
        public long percentage (long a, long b){
            
   long dividend=0,divisor=0,quotient=0,percent=0;
   
    dividend=a;
    divisor=b;
     if(divisor==0){
         
         quotient= 0;
     }else{
         
    quotient= dividend/100;
     
     }
    percent=quotient*divisor;
    return percent;
    
    }
        
   public  boolean testFormula1(String s1, String s2,String s3){
       boolean test=true;
   if((s1.equals("(")||s1.equals("*")||s1.equals("+")||s1.equals("-")|| s1.equals("/")||s1.equals("%"))||(s3.equals(")")||s3.equals("*")||s3.equals("+")||s3.equals("-")|| s3.equals("/")||s3.equals("%"))||!(s2.equals("/")||s2.equals("*")||s2.equals("+")||s2.equals("%")||s2.equals("-"))){
test=false;
   }
   
else {test=true;}
   return test;
   }
   
   public  boolean testFormula1(String s1, String s2,String s3,String s4){
       boolean test=false;
  
   return test;
   }
 public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5){
       boolean test;
   if(!(s1.equals("("))||(s2.equals("*")||s2.equals("+")||s2.equals("-")|| s2.equals("/")||s2.equals("%"))||!(s3.equals("*")||s3.equals("+")||s3.equals("-")|| s3.equals("/")||s3.equals("%"))||(s4.equals("/")||s4.equals("*")||s4.equals("+")||s4.equals("%")||s4.equals("-"))||!(s5.equals(")"))){
test=false;
   }
   
else {test=true;}
   return test;
   }
 
 public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5,String s6){
       boolean test=false;
  
   return test;
   }
  public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5,String s6,String s7){
       boolean test=false;
       if(s1.equals("(")){
           
   if((s2.equals("*")||s2.equals("+")||s2.equals("-")||s2.equals("/")||s2.equals("%"))||!(s3.equals("+")||s3.equals("*")||s3.equals("-")||s3.equals("/")||s3.equals("%"))|| (s4.equals("*")||s4.equals("+")||s4.equals("-")||s4.equals("/")||s4.equals("%"))||!(s5.equals(")"))||!(s6.equals("*")||s6.equals("+")||s6.equals("-")||s6.equals("/")||s6.equals("%"))||(s7.equals("*")||s7.equals("+")||s7.equals("-")||s7.equals("/")||s7.equals("%"))){
   test=false;
   }else {test=true;}
   
       
  }else{
  if((s1.equals("*")||s1.equals("+")||s1.equals("-")||s1.equals("/")||s1.equals("%"))||!(s2.equals("+")||s2.equals("*")||s2.equals("-")||s2.equals("/")||s2.equals("%"))|| !(s3.equals("("))||(s4.equals("+")||s4.equals("*")||s4.equals("-")||s4.equals("/")||s4.equals("%"))||!(s5.equals("*")||s5.equals("+")||s5.equals("-")||s5.equals("/")||s5.equals("%"))||(s6.equals("+")||s6.equals("*")||s6.equals("-")||s6.equals("/")||s6.equals("%"))||!(s7.equals(")"))){
   test=false;
   }else {test=true;}
 
  }     
       
       
       
   return test;
   }
  public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5,String s6,String s7,String s8){
       boolean test=false;
   
   return test;
   }
  public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9){
  boolean test=false;
       if(s1.equals("(")&&s2.equals("(")){
           
   if((s3.equals("*")||s3.equals("+")||s3.equals("-")||s3.equals("/")||s3.equals("%"))||!(s4.equals("+")||s4.equals("*")||s4.equals("-")||s4.equals("/")||s4.equals("%"))|| (s4.equals("*")||s5.equals("+")||s5.equals("-")||s5.equals("/")||s5.equals("%"))||!(s6.equals(")"))||!(s7.equals("*")||s7.equals("+")||s7.equals("-")||s7.equals("/")||s7.equals("%"))||(s8.equals("*")||s8.equals("+")||s8.equals("-")||s8.equals("/")||s8.equals("%"))||!(s9.equals(")"))){
   test=false;
   }else {test=true;}
   
       
  }else{
  if(!(s1.equals("("))||(s2.equals("*")||s2.equals("+")||s2.equals("-")||s2.equals("/")||s2.equals("%"))||!(s3.equals("+")||s3.equals("*")||s3.equals("-")||s3.equals("/")||s3.equals("%"))|| !(s4.equals("("))||(s5.equals("+")||s5.equals("*")||s5.equals("-")||s5.equals("/")||s5.equals("%"))||!(s6.equals("*")||s6.equals("+")||s6.equals("-")||s6.equals("/")||s6.equals("%"))||(s7.equals("+")||s7.equals("*")||s7.equals("-")||s7.equals("/")||s7.equals("%"))||!(s8.equals(")"))||!(s9.equals(")"))){
   test=false;
   }else {test=true;}
 
  }     
       
       
       
   return test;
   }
  
 public  boolean testFormula1(String s1, String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10){
       boolean test=false;
  
   return test;
   }
  
  public   String makeFormula(String s1,String s2,String s3){
  return s1.concat(s2).concat(s3);
  }
   public  String makeFormula(String s1,String s2,String s3,String s4){
  return s1.concat(s2).concat(s3).concat(s4);
  }
  public  String makeFormula(String s1,String s2,String s3,String s4,String s5){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5);
  }
   public  String makeFormula(String s1,String s2,String s3,String s4,String s5,String s6){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5).concat(s6);
  }
   public  String makeFormula(String s1,String s2,String s3,String s4,String s5,String s6,String s7){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5).concat(s6).concat(s7);
  }
   public  String makeFormula(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5).concat(s6).concat(s7).concat(s8);
  }
    public  String makeFormula(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5).concat(s6).concat(s7).concat(s8).concat(s9);
  }
    public  String makeFormula(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10){
  return s1.concat(s2).concat(s3).concat(s4).concat(s5).concat(s6).concat(s7).concat(s8).concat(s9).concat(s10);
  }
   public  double makeFormulaValue(String s1,String s2,String s3){
       double d1,d3,exp=0.0;
      
d1=parseDouble(s1);d3=parseDouble(s3); if(s2.equals("*")){exp=multiply(d1,d3);}else if (s2.equals("+")){exp=add(d1,d3);}else if(s2.equals("/")){exp=devide(d1,d3);}else if (s2.equals("-")){ exp=subtract(d1,d3);}else if(s2.equals("%")){exp=percentage(d1,d3);}else{exp=0.0;}

   return exp;
   }
   public  double makeFormulaValue(String s1,String s2,String s3,String s4){
   
    double d1,d2,d3,d4,exp=0.0; d1=10000.0;d2=10000.0;d3=10000.0; return 0.0; 
  }
   public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5){
    double d1,d2,d3,exp=0.0;
       
   d1=parseDouble(s2);
   d3=parseDouble(s4);
   if(s3.equals("*")){exp=multiply(d1,d3);}else if (s3.equals("+")){exp=add(d1,d3);}else if(s3.equals("/")){exp=devide(d1,d3);}else if (s3.equals("-")){ exp=subtract(d1,d3);}else if(s3.equals("%")){exp=percentage(d1,d3);}else{exp=0.0;}
 
   return exp;
   } 
    public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5,String s6){
     return 0.0; 
   }
    public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5,String s6,String s7){
 double d1=0,d2=0,d3=0,d4=0,d5=0,d6=0, exp=0.0;
   
  
 

 if(s1.equals("(")){ d1=parseDouble(s2); d3=parseDouble(s4);d4=parseDouble(s7); 

   if (s3.equals("+")&&s6.equals("+")){exp=add(add(d1,d3),d4);}
   else if(s3.equals("+")&&s6.equals("-")){exp=subtract(add(d1,d3),d4);}
   else if (s3.equals("+")&&s6.equals("*")){ exp=multiply(add(d1,d3),d4);}
   else if(s3.equals("+")&&s6.equals("/")){exp=devide(add(d1,d3),d4);}
   else if(s3.equals("+")&&s6.equals("%")){exp=percentage(add(d1,d3),d4);}
   else if(s3.equals("-")&&s6.equals("+")){exp=add(subtract(d1,d3),d4);}
   else if(s3.equals("-")&&s6.equals("-")){exp=subtract(subtract(d1,d3),d4);}
   else if(s3.equals("-")&&s6.equals("*")){exp=multiply(subtract(d1,d3),d4);}
   else if(s3.equals("-")&&s6.equals("/")){exp=devide(subtract(d1,d3),d4);}
   else if(s3.equals("-")&&s6.equals("%")){exp=percentage(subtract(d1,d3),d4);}
   else if(s3.equals("*")&&s6.equals("+")){exp=add(multiply(d1,d3),d4);}
   else if(s3.equals("*")&&s6.equals("-")){exp=subtract(multiply(d1,d3),d4);}
   else if(s3.equals("*")&&s6.equals("*")){exp=multiply(multiply(d1,d3),d4);}
   else if(s3.equals("*")&&s6.equals("/")){exp=devide(multiply(d1,d3),d4);}
   else if(s3.equals("*")&&s6.equals("%")){exp=percentage(multiply(d1,d3),d4);}
   else if(s3.equals("/")&&s6.equals("+")){exp=add(devide(d1,d3),d4);}
   else if(s3.equals("/")&&s6.equals("-")){exp=subtract(devide(d1,d3),d4);}
   else if(s3.equals("/")&&s6.equals("*")){exp=multiply(devide(d1,d3),d4);}
   else if(s3.equals("/")&&s6.equals("/")){exp=devide(devide(d1,d3),d4);}
   else if(s3.equals("/")&&s6.equals("%")){exp=percentage(devide(d1,d3),d4);}
   else if(s3.equals("%")&&s6.equals("+")){exp=add(percentage(d1,d3),d4);}
   else if(s3.equals("%")&&s6.equals("-")){exp=subtract(percentage(d1,d3),d4);}
   else if(s3.equals("%")&&s6.equals("*")){exp=multiply(percentage(d1,d3),d4);}
    else if(s3.equals("%")&&s6.equals("/")){exp=devide(percentage(d1,d3),d4);}
   else if(s3.equals("%")&&s6.equals("%")){exp=percentage(percentage(d1,d3),d4);}
  }else{ 
  d2=parseDouble(s1);
  d5=parseDouble(s4);
  d6=parseDouble(s6);        
  if (s2.equals("+")&&s5.equals("+")){exp=add(d2,add(d5,d6));}
   else if(s2.equals("+")&&s5.equals("-")){exp=add(d2,subtract(d5,d6));}
   else if(s2.equals("+")&&s5.equals("*")){exp=add(d2,multiply(d5,d6));}
   else if(s2.equals("+")&&s5.equals("/")){exp=add(d2,devide(d5,d6));}
   else if(s2.equals("+")&&s5.equals("%")){exp=add(d2,percentage(d5,d6));}
    else if(s2.equals("-")&&s5.equals("+")){exp=subtract(d2,add(d5,d6));}
   else if(s2.equals("-")&&s5.equals("-")){exp=subtract(d2,subtract(d5,d6));}
    else if(s2.equals("-")&&s5.equals("*")){exp=subtract(d2,multiply(d5,d6));}
   else if(s2.equals("-")&&s5.equals("/")){exp=subtract(d2,devide(d5,d6));}
    else if(s2.equals("-")&&s5.equals("%")){exp=subtract(d2,percentage(d5,d6));}
    else if(s2.equals("*")&&s5.equals("+")){exp=multiply(d2,add(d5,d6));}
    else if(s2.equals("*")&&s5.equals("-")){exp=multiply(d2,subtract(d5,d6));}
   else if(s2.equals("*")&&s5.equals("*")){exp=multiply(d2,multiply(d5,d6));}
    else if(s2.equals("*")&&s5.equals("/")){exp=multiply(d2,devide(d5,d6));}
   else if(s2.equals("*")&&s5.equals("%")){exp=multiply(d2,percentage(d5,d6));}
   else if(s2.equals("/")&&s5.equals("+")){exp=devide(d2,add(d5,d6));}
   else if(s2.equals("/")&&s5.equals("-")){exp=devide(d2,subtract(d5,d6));}
   else if(s2.equals("/")&&s5.equals("*")){exp=devide(d2,multiply(d5,d6));}
  else if(s2.equals("/")&&s5.equals("/")){exp=devide(d2,devide(d5,d6));}
  else if(s2.equals("/")&&s5.equals("%")){exp=devide(d2,percentage(d5,d6));}
  else if(s2.equals("%")&&s5.equals("+")){exp=percentage(d2,add(d5,d6));}
 else if(s2.equals("%")&&s5.equals("-")){exp=percentage(d2,subtract(d5,d6));}
else if(s2.equals("%")&&s5.equals("*")){exp=percentage(d2,multiply(d5,d6));}
else if(s2.equals("%")&&s5.equals("/")){exp=percentage(d2,devide(d5,d6));}
else if(s2.equals("%")&&s5.equals("%")){exp=percentage(d2,percentage(d5,d6));}      

         }
   
   return exp;
   
   }
  public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8){
     return 0.0; 
   } 
  
  public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9){
 double d1=0,d2=0,d3=0,d4=0,d5=0,d6=0, exp=0.0;

 if(s1.equals("(")&&s2.equals("(")){
  d1=parseDouble(s3);
  d3=parseDouble(s5);
  d4=parseDouble(s8); 

   if (s4.equals("+")&&s7.equals("+")){exp=add(add(d1,d3),d4);}
   else if(s4.equals("+")&&s7.equals("-")){exp=subtract(add(d1,d3),d4);}
   else if (s4.equals("+")&&s7.equals("*")){ exp=multiply(add(d1,d3),d4);}
   else if(s4.equals("+")&&s7.equals("/")){exp=devide(add(d1,d3),d4);}
   else if(s4.equals("+")&&s7.equals("%")){exp=percentage(add(d1,d3),d4);}
   else if(s4.equals("-")&&s7.equals("+")){exp=add(subtract(d1,d3),d4);}
   else if(s4.equals("-")&&s7.equals("-")){exp=subtract(subtract(d1,d3),d4);}
   else if(s4.equals("-")&&s7.equals("*")){exp=multiply(subtract(d1,d3),d4);}
   else if(s4.equals("-")&&s7.equals("/")){exp=devide(subtract(d1,d3),d4);}
   else if(s4.equals("-")&&s7.equals("%")){exp=percentage(subtract(d1,d3),d4);}
   else if(s4.equals("*")&&s7.equals("+")){exp=add(multiply(d1,d3),d4);}
   else if(s4.equals("*")&&s7.equals("-")){exp=subtract(multiply(d1,d3),d4);}
   else if(s4.equals("*")&&s7.equals("*")){exp=multiply(multiply(d1,d3),d4);}
   else if(s4.equals("*")&&s7.equals("/")){exp=devide(multiply(d1,d3),d4);}
   else if(s4.equals("*")&&s7.equals("%")){exp=percentage(multiply(d1,d3),d4);}
   else if(s4.equals("/")&&s7.equals("+")){exp=add(devide(d1,d3),d4);}
   else if(s4.equals("/")&&s7.equals("-")){exp=subtract(devide(d1,d3),d4);}
   else if(s4.equals("/")&&s7.equals("*")){exp=multiply(devide(d1,d3),d4);}
   else if(s4.equals("/")&&s7.equals("/")){exp=devide(devide(d1,d3),d4);}
   else if(s4.equals("/")&&s7.equals("%")){exp=percentage(devide(d1,d3),d4);}
   else if(s4.equals("%")&&s7.equals("+")){exp=add(percentage(d1,d3),d4);}
   else if(s4.equals("%")&&s7.equals("-")){exp=subtract(percentage(d1,d3),d4);}
   else if(s4.equals("%")&&s7.equals("*")){exp=multiply(percentage(d1,d3),d4);}
    else if(s4.equals("%")&&s7.equals("/")){exp=devide(percentage(d1,d3),d4);}
   else if(s4.equals("%")&&s7.equals("%")){exp=percentage(percentage(d1,d3),d4);}
  }else{ 
  d2=parseDouble(s2);
  d5=parseDouble(s5);
  d6=parseDouble(s7);        
  if (s3.equals("+")&&s6.equals("+")){exp=add(d2,add(d5,d6));}
   else if(s3.equals("+")&&s6.equals("-")){exp=add(d2,subtract(d5,d6));}
   else if(s3.equals("+")&&s6.equals("*")){exp=add(d2,multiply(d5,d6));}
   else if(s3.equals("+")&&s6.equals("/")){exp=add(d2,devide(d5,d6));}
   else if(s3.equals("+")&&s6.equals("%")){exp=add(d2,percentage(d5,d6));}
    else if(s3.equals("-")&&s6.equals("+")){exp=subtract(d2,add(d5,d6));}
   else if(s3.equals("-")&&s6.equals("-")){exp=subtract(d2,subtract(d5,d6));}
    else if(s3.equals("-")&&s6.equals("*")){exp=subtract(d2,multiply(d5,d6));}
   else if(s3.equals("-")&&s6.equals("/")){exp=subtract(d2,devide(d5,d6));}
    else if(s3.equals("-")&&s6.equals("%")){exp=subtract(d2,percentage(d5,d6));}
    else if(s3.equals("*")&&s6.equals("+")){exp=multiply(d2,add(d5,d6));}
    else if(s3.equals("*")&&s6.equals("-")){exp=multiply(d2,subtract(d5,d6));}
   else if(s3.equals("*")&&s6.equals("*")){exp=multiply(d2,multiply(d5,d6));}
    else if(s3.equals("*")&&s6.equals("/")){exp=multiply(d2,devide(d5,d6));}
   else if(s3.equals("*")&&s6.equals("%")){exp=multiply(d2,percentage(d5,d6));}
   else if(s3.equals("/")&&s6.equals("+")){exp=devide(d2,add(d5,d6));}
   else if(s3.equals("/")&&s6.equals("-")){exp=devide(d2,subtract(d5,d6));}
   else if(s3.equals("/")&&s6.equals("*")){exp=devide(d2,multiply(d5,d6));}
  else if(s3.equals("/")&&s6.equals("/")){exp=devide(d2,devide(d5,d6));}
  else if(s3.equals("/")&&s6.equals("%")){exp=devide(d2,percentage(d5,d6));}
  else if(s3.equals("%")&&s6.equals("+")){exp=percentage(d2,add(d5,d6));}
 else if(s3.equals("%")&&s6.equals("-")){exp=percentage(d2,subtract(d5,d6));}
else if(s3.equals("%")&&s6.equals("*")){exp=percentage(d2,multiply(d5,d6));}
else if(s3.equals("%")&&s6.equals("/")){exp=percentage(d2,devide(d5,d6));}
else if(s3.equals("%")&&s6.equals("%")){exp=percentage(d2,percentage(d5,d6));}      
         
         
         }
   
   return exp;
   
   }
   public  double makeFormulaValue(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String s9,String s10){
     return 0.0; 
   } 
   
   
  public int add (int a,int b){
    int firstOp=0,secondOp=0,sum=0;
    firstOp=a;
    secondOp=b;
    sum=firstOp+secondOp;
    return sum;
    }
     public  int multiply (int a,int b){
    int firstOp=0,secondOp=0,product=0;
    firstOp=a;
    secondOp=b;
    product=firstOp*secondOp;
    return product;
    
    
    }
     
       public  int subtract (int a,int b){
    int firstOp=0,secondOp=0,difference=0;
    firstOp=a;
    secondOp=b;
    difference=firstOp-secondOp;
    return difference;
    
    
    }
        
  public double theActualReturnOnInvestmentHEFSacco(double theShares,int theMonth,int year){

      YearMonth yearMonthObject = YearMonth.of(year,theMonth);
      
   int daysInMonth = yearMonthObject.lengthOfMonth(); //28  
  
  return ((theShares*parseDouble(fios.stringFileReader(fios.createFileName("sharesAvailable", "shareValues", "rateShares.txt"))))/daysInMonth);
  
  }
   
   
   
   
   
   
   
   
   
   
}
