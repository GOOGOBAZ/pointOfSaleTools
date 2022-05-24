/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.util.ArrayList;
import java.util.List;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author SSRN
 */
public class listComboBoxModel implements MutableComboBoxModel{
    
 private Object selectedItem;
     
   List columns = new ArrayList();
   public listComboBoxModel( List c){
       this.columns = c;
        
   }

    @Override
    public void addElement(Object item) {
  columns.add(item.toString());
    }

    @Override
    public void removeElement(Object obj) {
        columns.remove(obj.toString());
    }

    @Override
    public void insertElementAt(Object item, int index) {
     columns.get(index);
    }

    @Override
    public void removeElementAt(int index) {
        columns.remove(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
     selectedItem = anItem;   
    }

    @Override
    public Object getSelectedItem() {
         return selectedItem;
    }

    @Override
    public int getSize() {
     return columns.size();
    }

    @Override
    public Object getElementAt(int index) {
        return columns.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        
    }
    public List getAllElments() {
         return columns;
    }
    
}
