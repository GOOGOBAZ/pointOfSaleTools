/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

/**
 *
 * @author STAT SOLUTIONS
 */
public class DepreciationFormulas {
    
    
    public double depreciatedStraightLine(double purchasePrice,double salvageValaue,int usefulLife){
    double thedepriciation=0.0;
    
    thedepriciation = (purchasePrice-salvageValaue)/usefulLife;
    
    return thedepriciation;
    }
    
    public double depreciatedReducingBalance(double purchasePrice,double salvageValaue,int usefulLife){
    
        double thedepriciation=0.0,depreciationRate=0.0;
     
    depreciationRate = (100 /usefulLife)* 2;
    
    thedepriciation = ((purchasePrice-salvageValaue)*depreciationRate)/100;
    
    return thedepriciation;
    
    
    
    }
    
    
    
    
}
