/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.awt.Component;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Stanchart
 */

   class NumberRenderer1 extends JTextPane implements TableCellRenderer {
    DecimalFormat NumberFormat1 =new DecimalFormat("#,###.##");
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      
       Number c = (Number)parseDouble(value.toString());
       String text1 = NumberFormat1.format(c );
this.setText(text1);
       return this;
}} 

