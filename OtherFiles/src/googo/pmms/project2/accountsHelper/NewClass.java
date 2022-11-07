/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.text.AttributedString;
import static javafx.beans.binding.Bindings.concat;

/**
 *
 * @author user
 */
public class NewClass {
    
    
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int y=0;
       if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
           return NO_SUCH_PAGE;
       }
 
       Graphics2D g2d = (Graphics2D) graphics;
       
       g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
                
 
       //Prepare the rendering
 
       StringBuffer[] no=new StringBuffer[500];
       StringBuffer[] iname=new StringBuffer[500];
       no[459]=new StringBuffer();
       iname[459]=new StringBuffer();
       no[0]=new StringBuffer("1");
       no[1]=new StringBuffer("19");
        
       iname[0]=new StringBuffer("Bada 129");
       iname[1]=new StringBuffer("Kaju");
       Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
       g2d.setFont(f);
       g2d.drawString("PT", 5, y);
       g2d.drawString("", 5, y+=15);
       g2d.drawString("=====================================", 5, y+=15);
       g2d.drawString("No.  ItemName    Rate    Qty(gm)   Amount", 5, y+=15);
       g2d.drawString("=====================================", 5, y+=15);
       g2d.drawString("", 5, y+=15);
      
       for (int i = 0; i < 2; i++) {
           StringBuffer finals=new StringBuffer();
           
           StringBuffer firstString=new StringBuffer();
           firstString.append(concat(iname[i], 15-iname[i].length()));
            
           StringBuffer secondString=new StringBuffer();
           secondString.append(concat(no[i], 15-no[i].length()));
               
         firstString.append(secondString);              
               
           //g2d.drawString(firstString.toString(), 5, y+=15);
           AttributedString a = new AttributedString(firstString.toString());
           System.out.println(firstString);
           graphics.drawString(a.getIterator(), 5, y+=15);
            
       }
      
        
       g2d.drawString("=====================================", 5, y+=15);
       g2d.drawString("Total:                            200", 5, y+=15);
       g2d.drawString("=====================================", 5, y+=15);
        
       return PAGE_EXISTS;
   }
    
}
