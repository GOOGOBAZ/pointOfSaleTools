/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stanchart
 */
public class ObjectTableModel extends AbstractTableModel {
  ArrayList <String> columns = new ArrayList();
   
ArrayList<ArrayList<Object>> data = new ArrayList();

ArrayList<Color> rowColours = new ArrayList();
     
       
     
   public ObjectTableModel(ArrayList<ArrayList<Object>> d, ArrayList<String> c){
       this.columns = c;
       this.data= d;    
   }
         
        
        
public void addRow(int i, ArrayList <Object> rows) 
{ 
 data.add(i, rows);
this.fireTableRowsInserted(i, i);
} 
   
@Override
          public String getColumnName(int column) {
        return columns.get(column);
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
    public Object getValueAt(int i, int i1) {
        return data.get(i).get(i1);
    }
   @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }   
       public List getRow(int rowIndex) { 
 return data.get(rowIndex);

}
}
