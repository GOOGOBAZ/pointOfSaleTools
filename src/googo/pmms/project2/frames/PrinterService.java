package googo.pmms.project2.frames;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class PrinterService implements Printable,MouseListener {
private static int width = 576; 
    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /*
         * User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        /* Now we perform our rendering */

        g.setFont(new Font("Roman", 0, 8));
        g.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
    }

    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void feedPrinter(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }
    
public void printImage(String printerName,File imageFile){
    
		    
    
		try {
			// read the file
			BufferedImage image = ImageIO.read(imageFile);
			
			// convert to grayscale
			BufferedImage imageGrayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                        
//			Graphics2D g = imageGrayscale.createGraphics();
//                        
//			g.drawImage(image, 0, 0, null);
//                        
//			g.dispose();
//			
//			// resize / rotate to fil the printer resolution
//			int width = imageGrayscale.getWidth();
//			int height = imageGrayscale.getHeight();
//                        
//			int TARGET_WIDTH = this.width;
//			
//			if (width <= TARGET_WIDTH && height <= TARGET_WIDTH) { // image can fit in paper
//				if (width < height) {	// we have to rotate to save paper
//					BufferedImage imageRotated = new BufferedImage(height, width, BufferedImage.TYPE_BYTE_GRAY);
//					g = imageRotated.createGraphics();
//					g.rotate(Math.PI/2);
//					g.drawImage(imageGrayscale, 0, -height, width, height, null);
//					g.dispose();
//					// update size
//					width = imageRotated.getWidth();
//					height = imageRotated.getHeight();
//					// fit width dimension to %8
//					BufferedImage imageResized = new BufferedImage((width/8)*8, height, BufferedImage.TYPE_BYTE_GRAY);
//					g = imageResized.createGraphics();
//					g.drawImage(imageRotated, 0, 0, width, height, null);
//					g.dispose();
//					image = imageResized;
//				} else { // no need to rotate
//					// fit width dimension to %8
//					BufferedImage imageResized = new BufferedImage((width/8)*8, height, BufferedImage.TYPE_BYTE_GRAY);
//					g = imageResized.createGraphics();
//					g.drawImage(imageGrayscale, 0, 0, width, height, null);
//					g.dispose();
//					image = imageResized;
//				}
//			} else if (width <= TARGET_WIDTH && height > TARGET_WIDTH) { // image fit in paper, no need to resize nor rotate
//				// fit width dimension to %8
//				BufferedImage imageResized = new BufferedImage((width/8)*8, height, BufferedImage.TYPE_BYTE_GRAY);
//				g = imageResized.createGraphics();
//				g.drawImage(imageGrayscale, 0, 0, width, height, null);
//				g.dispose();
//				image = imageResized;
//			} else if (width > TARGET_WIDTH && height <= TARGET_WIDTH) { // image fit in paper, no need to resize but rotate
//				BufferedImage imageRotated = new BufferedImage(height, width, BufferedImage.TYPE_BYTE_GRAY);
//				g = imageRotated.createGraphics();
//				g.rotate(Math.PI/2);
//				g.drawImage(imageGrayscale, 0, -height, width, height, null);
//				g.dispose();
//				// update size
//				width = imageRotated.getWidth();
//				height = imageRotated.getHeight();
//				// fit width dimension to %8
//				BufferedImage imageResized = new BufferedImage((width/8)*8, height, BufferedImage.TYPE_BYTE_GRAY);
//				g = imageResized.createGraphics();
//				g.drawImage(imageRotated, 0, 0, width, height, null);
//				g.dispose();
//				image = imageResized;
//			} else { // image doesn't fit in paper
//				if (width > height) { // we have to rotate to maximize printed size
//					BufferedImage imageRotated = new BufferedImage(height, width, BufferedImage.TYPE_BYTE_GRAY);
//					g = imageRotated.createGraphics();
//					g.rotate(Math.PI/2);
//					g.drawImage(imageGrayscale, 0, -height, width, height, null);
//					g.dispose();
//					imageGrayscale = imageRotated;
//					width = imageGrayscale.getWidth();
//					height = imageGrayscale.getHeight();
//				}
//				// resize
//				height = (int) (height * (  (double)TARGET_WIDTH /width  ));
//				width = TARGET_WIDTH;
//				BufferedImage ImageResized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//				g = ImageResized.createGraphics();
//				g.drawImage(imageGrayscale, 0, 0, width, height, null);
//				g.dispose();
//				image = ImageResized;
//			}
			
//			// update size
//			width = image.getWidth();
//			height = image.getHeight();
			
			// extract bytes
			byte[] bytelist = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//			int[] pixList = new int[image.getWidth()*image.getHeight()];
//			for (int i = 0; i < pixList.length; i++) {
//				pixList[i] = Byte.toUnsignedInt( bytelist[i] );
//                                
                                printImage(bytelist, printerName);
//			}
			
			
                        
                

		} catch (IOException ex) { 
            Logger.getLogger(PrinterService.class.getName()).log(Level.SEVERE, null, ex);
        }           
               
	}



private void printImage(byte[] bytelist,String printerName){

    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob(); 
        
//           bytes = text.getBytes("CP437");
        
        try {

            Doc doc = new SimpleDoc(bytelist, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
}

    @Override
    public void mouseClicked(MouseEvent me) {
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}