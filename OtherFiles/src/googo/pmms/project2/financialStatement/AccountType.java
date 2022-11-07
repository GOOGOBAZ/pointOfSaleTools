/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.financialStatement;

import java.util.List;
import java.util.Map;

/**
 *
 * @author SSRN
 */
public interface AccountType {
    
    public String getAccountName();
    
    public String getAccountBalance();
    
    public List<String>getAccountNames();
    
    public Map<String,String>getAccountDetails();
    
       public void setAccountName(String accountName);
       
    public void setAccountBalance(String accountBalance);
    
    public void setAccountNames(List accountNames);
    
    public Map<String,String>setAccountDetails(String accountName,String accountBalance);
}
