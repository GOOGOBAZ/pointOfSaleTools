/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SSRN
 */
public class ReportsModelData  extends AbstractTableModel {
    
       
   List <String> columns = null;
   
Map<Integer, List<Object>> data =null;
     
   public ReportsModelData(Map<Integer, List<Object>>  d,  List <String>  c){
       this.columns = c;
       this.data= d;    
   }
      public void addRow(Integer i, List <Object> rows) 
{ 
 data.put(i, rows);
this.fireTableRowsInserted(i, i);
} 

    @Override
    public int getRowCount() {
return data.size();
    }

    @Override
    public int getColumnCount() {
       return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return data.get(rowIndex).get(columnIndex);
    }
    
    
    @Override
          public String getColumnName(int column) {
        return columns.get(column);
    }
          
           @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
     public List getRow(int rowIndex) { 
 return data.get(rowIndex);

}
}
