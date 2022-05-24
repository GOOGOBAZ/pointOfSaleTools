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
public class PureListModel  extends AbstractTableModel {
    
       
   List  columns = null;
   
List<List>data =null;
     
   public PureListModel(List<List>  d,  List   c){
       this.columns = c;
       this.data= d;    
   }
      public void addRow(Integer i, List<Object> rows) 
{ 
 data.add(i,rows);
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
        return columns.get(column).toString();
    }
          
 
     public List getRow(int rowIndex) { 
 return data.get(rowIndex);

}
     
      @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 8){
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
    
    
   @Override
     public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }
}
