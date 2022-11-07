/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;

/**
 *
 * @author SSRN
 */
public class ComboListener extends KeyAdapter{
    
    
	@SuppressWarnings("rawtypes")
	JComboBox combo;
    @SuppressWarnings("rawtypes")
	List parameters;
	
	@SuppressWarnings("rawtypes")
	public ComboListener(JComboBox comboBox, List listParam)
	{
		combo = comboBox;
		parameters = listParam;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void keyTyped(KeyEvent key)
	{
				// TODO Auto-generated method stub
				String text = ((JTextField)key.getSource()).getText();
                                
				combo.setModel(new listComboBoxModel(getFilteredList(text)));
//				combo.setSelectedIndex(-1);
				((JTextField)combo.getEditor().getEditorComponent()).setText(text);
				combo.showPopup();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getFilteredList(String text)
	{
		List v = new ArrayList();
                
		for(int a = 0;a<parameters.size();a++)
		{
			if(parameters.get(a).toString().compareToIgnoreCase(text)==0)
			{
				v.add(parameters.get(a).toString());
			}
		}
		
                
                
             
                 
                 return v;
	}
    
}
