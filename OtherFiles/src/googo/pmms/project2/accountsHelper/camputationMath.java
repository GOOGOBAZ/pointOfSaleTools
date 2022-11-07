/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import static java.lang.Double.parseDouble;

/**
 *
 * @author Stanchart
 */
public class camputationMath {
    
    
   public String aAspercentageOfb(String a,String b){
   double dividend=0.0,divisor=0.0,quotient=0,percent=0;
   dividend=parseDouble(a);
    divisor=parseDouble(b);
     if(divisor==0){
         
         quotient= 0.0;
     }else{
         
    quotient= dividend/100;
     
     }
    percent=quotient*divisor;
    return percent+"";
 
   }
    public String aAsfractionOfb(String a,String b){
    
    double dividend=0,divisor=0, quotient=0;
    dividend=parseDouble(a);
    divisor=parseDouble(b);
     if(divisor==0){
        
         quotient= 0.0;
     }else{
         
    quotient= dividend/divisor;
     
     }
 
        return quotient+"";

    }
     public String differenceOfaAndb(String a,String b){
     
      double firstOp=0,secondOp=0,difference=0;
    firstOp=parseDouble(a);
    secondOp=parseDouble(b);
    difference=firstOp-secondOp;
    
       return difference+"";
  
     
     }
      public String sumOfaAndb(String a,String b){
       double firstOp=0,secondOp=0,sum=0;
    firstOp=parseDouble(a);
    secondOp=parseDouble(b);
    sum=firstOp+secondOp;
    return sum+"";
 
      }
      public String productOfaAndb(String a,String b){
      
       double firstOp=0,secondOp=0,product=0;
    firstOp=parseDouble(a);
    secondOp=parseDouble(b);
    product=firstOp*secondOp;
    return product+"";
      }
      public String quotiontOfaAndb(String a,String b){
      
       double dividend=0,divisor=0, quotient=0;
    dividend=parseDouble(a);
    divisor=parseDouble(b);
     if(divisor==0){
        
         quotient= 0.0;
     }else{
         
    quotient= dividend/divisor;
     
     }
 
        return quotient+"";
      
      
      
      
      
      }
//   public String aRootOfb(String a,String b){
//      
//      
//      
//      
//      
//      
//      
//      }
//    
//     public String aPowerb(String a,String b){
//      
//      
//      
//      
//      
//      
//      
//      }
//    
//     public String aModulusb(String a,String b){
//      
//      
//      
//      
//      
//      
//      
//      }
    
    
    
    
    
    
    
    
    
    
    
    
}
