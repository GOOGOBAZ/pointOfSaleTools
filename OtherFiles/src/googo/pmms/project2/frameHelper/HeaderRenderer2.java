/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frameHelper;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author SSRN
 */
public class HeaderRenderer2 implements UIResource, TableCellRenderer {

    private TableCellRenderer original;

    public HeaderRenderer2(TableCellRenderer original) {
        this.original = original;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                                                                 Object value, boolean isSelected, boolean hasFocus, int row,
                                                                                                 int column) {
        Component comp = original.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        comp.setFont(new Font("Arial",Font.BOLD,15));
         comp.setBackground(java.awt.Color.ORANGE);
         comp.setForeground(java.awt.Color.BLUE);
        return comp;
    }

}