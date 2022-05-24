/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SSRN
 */
public class ListDataModel_1  extends AbstractTableModel {
    
       
   List <String> columns = null;
   
List<List<Object>>data =null;
     
   public ListDataModel_1(List<List<Object>>  d,  List <String>  c){
       this.columns = c;
       this.data= d;    
   }
public void addRow(List<Object> rows) 
{ 
data.add(rows);
this.fireTableDataChanged();
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
            public boolean isCellEditable(int row, int column) {
                return ((column==2||column==3||column==4||column==6)&&row!=data.size()-1);
            }   
           @Override
    public Class getColumnClass(int c) {
        if(c == 6){
                    return Boolean.class;
                }
                return super.getColumnClass(c);
    }
    
        
      
     public List getRow(int rowIndex) { 
 return data.get(rowIndex);

}
   @Override
     public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }   

}
