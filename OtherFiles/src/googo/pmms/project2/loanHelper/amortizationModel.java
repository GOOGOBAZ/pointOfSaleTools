/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.loanHelper;

import java.util.ArrayList;

/**
 *
 * @author Stanchart
 */
public class amortizationModel {
    ArrayList <Object> obj= new ArrayList();int k=0;
    
    public amortizationModel(ArrayList<Object> d, int c){
    this.obj=d;
    this.k=c;

    }
    public int size(){
    
    return obj.size();
    
    }
    public Object returnValueAt(int c){
    
    return obj.get(c);
    }
    public void addValueAt(Object o,int c){
    
   obj.add(c, o);
    }
    
}
