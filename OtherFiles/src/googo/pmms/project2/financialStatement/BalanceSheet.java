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
public interface BalanceSheet {
    
    public String getTitle(String date);
    
    public List<String> getTableHeaders();
    
    public List<List<Object>> getBodyList();
    
     public Map<Integer,List<Object>> getBodyMap();
     
       public void setTitle(String title);
    
    public void setTableHeader(String header);
    
    public void setBodyList(List body);
    
     public void setBodyMap(Integer index,List<Object> body);
    
}
