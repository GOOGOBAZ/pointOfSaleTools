        /*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
        package googo.pmms.project2.frameHelper;

        import googo.pmms.project2.accountsHelper.Formartter;

        import static java.lang.Double.parseDouble;

        import java.util.ArrayList;

        import java.util.List;

        import javax.swing.table.AbstractTableModel;

        /**
        *
        * @author SSRN
        */
        public class loanManagementModel  extends AbstractTableModel {

        Formartter fmt = new Formartter();

        List <String> columns = null;

        List<List<Object>>data =null;

        public loanManagementModel(List<List<Object>>  d,  List <String>  c){
            
        this.columns = c;
        
        this.data= d;    
        
        
          addBottomRow();
        
        }
        
        public void addRow(List<Object> rows) { 


        if(data.isEmpty()){

        data.add(rows);

        addBottomRow();

        }else if(!data.isEmpty()){
        data.add(rows);
        removeRow1(getIndexToRemove());  
        addBottomRow();

        }
        this.fireTableDataChanged();
        updateSquenceNo();
        } 

        public void addRow1(List<Object> rows)   { 

        data.add(rows);

        this.fireTableDataChanged();
        } 

        public void removeRow(int index)  { 

        data.remove(index);
        
        removeRow1(getIndexToRemove()); 

        addBottomRow();
      
        this.fireTableDataChanged();
        
        updateSquenceNo();
        
        } 

        public void removeRow1(int index) 
        { 

        data.remove(index);


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
        return false;
        }   
        @Override
        public Class getColumnClass(int c) {

        return super.getColumnClass(c);
        }

        public List getRow(int rowIndex) { 

        return data.get(rowIndex);

        }

        public double totalValueColumn(int columnIndex) { 

        double total=0.0;

        for (int count = 0; count <getRowCount(); count++){

        total+=parseDouble(getValueAt(count, columnIndex).toString().replace(",", ""));

        }

        return total;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
       
      
        fireTableCellUpdated(row, col);
        }   

        private void addBottomRow(){
          
       
            
        List lastRow=new ArrayList();

        lastRow.add("Total");

        lastRow.add("");

        lastRow.add("");

     lastRow.add("");
     
//        lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(3)+""));
         lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(4)+""));
          lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(5)+""));
           lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(6)+""));
            lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(7)+""));
             lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(8)+""));
//              lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(9)+""));
//               lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(10)+""));
//                lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(11)+""));
//                 lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(12)+""));
//                  lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(13)+""));
//                   lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(14)+""));
//                    lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(15)+""));
//                     lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(16)+""));
//                      lastRow.add(fmt.formatForStatementNumbersNormal(totalValueColumn(17)+""));
                    
                    lastRow.add("");
  lastRow.add("");
        lastRow.add("");
lastRow.add("");
        lastRow.add("");
        addRow1(lastRow);

        }

        public int getIndexToRemove(){

        int theIndex=0;

        while(theIndex<data.size()){

        if(getValueAt(theIndex, 1).toString().equalsIgnoreCase("Total")){

        break;
        }

        theIndex++;

        }

        return   theIndex; 
        }

        public void updateSquenceNo(){

        int theIndex=0;

        while(theIndex<data.size()-1){

        setValueAt(theIndex+1, theIndex, 0);
        
        theIndex++;
        }


        }
        }