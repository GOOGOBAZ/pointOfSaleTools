/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author STAT SOLUTIONS
 */

  public class TableHeaderRender implements TableCellRenderer {

    DefaultTableCellRenderer renderer;

    public TableHeaderRender(JTable table) {
        
        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
        
        renderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
        return renderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, col);
    }

}
