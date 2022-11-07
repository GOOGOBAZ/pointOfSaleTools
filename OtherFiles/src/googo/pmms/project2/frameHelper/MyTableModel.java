package googo.pmms.project2.frameHelper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stanchart
 */
public class MyTableModel extends AbstractTableModel {
     
   ArrayList <String> columns = new ArrayList();
   
ArrayList<ArrayList<String>> data = new ArrayList();
     
   public MyTableModel(ArrayList<ArrayList<String>> d, ArrayList<String> c){
       this.columns = c;
       this.data= d;    
   }
      public void addRow(int i, ArrayList <String> rows) 
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

}



