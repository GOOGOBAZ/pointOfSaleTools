/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package googo.pmms.project2.systemclasses;
    import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
/**
 *
 * @author STAT SOLUTIONS
 */
public class MysystemTray{
TrayIcon trayIcon=null;
//start of main method
public  void mainTheActual(Component c){
    //checking for support
    if(!SystemTray.isSupported()){
       JOptionPane.showMessageDialog(c, "Tray is not supported!!!");
        return ;
    }else{
    SystemTray tray = SystemTray.getSystemTray();
    Image image = Toolkit.getDefaultToolkit().getImage("ICON_LOGO.jpg");

    MouseListener mouseListener = new MouseListener() {
                
        public void mouseClicked(MouseEvent e) {
            System.out.println("Tray Icon - Mouse clicked!");                 
        }

        public void mouseEntered(MouseEvent e) {
            System.out.println("Tray Icon - Mouse entered!");                 
        }

        public void mouseExited(MouseEvent e) {
            System.out.println("Tray Icon - Mouse exited!");                 
        }

        public void mousePressed(MouseEvent e) {
            System.out.println("Tray Icon - Mouse pressed!");                 
        }

        public void mouseReleased(MouseEvent e) {
            System.out.println("Tray Icon - Mouse released!");                 
        }
    };

    ActionListener exitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Exiting...");
            System.exit(0);
        }
    };
            
    PopupMenu popup = new PopupMenu();
    MenuItem defaultItem = new MenuItem("Exit");
    defaultItem.addActionListener(exitListener);
    popup.add(defaultItem);

    trayIcon = new TrayIcon(image, "Tray Demo", popup);

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            trayIcon.displayMessage("Action Event", 
                "An Action Event Has Been Performed!",
                TrayIcon.MessageType.INFO);
        }
    };
            
    trayIcon.setImageAutoSize(true);
    trayIcon.addActionListener(actionListener);
    trayIcon.addMouseListener(mouseListener);

    try {
        tray.add(trayIcon);
    } catch (AWTException e) {
        System.err.println("TrayIcon could not be added.");
   
    //  System Tray is not supported

}
 
}//end of main
}
}//end of class

