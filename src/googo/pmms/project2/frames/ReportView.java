/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frames;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.AssetRegisterModel;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect1;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport1;
import googo.pmms.project2.frameHelper.ListTableModel;
import googo.pmms.project2.frameHelper.ReportsModelData;
import googo.pmms.project2.reportsHelper.BVAReport;
import googo.pmms.project2.reportsHelper.LoanSavingsSharesOthers;
import googo.pmms.project2.reportsHelper.OtherReportsPdf;
import googo.pmms.project2.reportsHelper.ReportsPDFFormulars;
import googo.pmms.project2.frameHelper.Printerdd;
import googo.pmms.project2.frameHelper.ReportsModelStatusReport;
import googo.pmms.project2.frameHelper.TableHeaderRender;
import googo.pmms.project2.frameHelper.budgetEstimatesModel;
import googo.pmms.project2.frameHelper.loanSatementModel;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.RowFilter;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SSRN
 */
public class ReportView extends javax.swing.JFrame implements MouseListener,PropertyChangeListener{

    /**
     * Creates new form ReportView
     */
     String userId;
     String signal;
     List otherDetails;
      SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
    OtherReportsPdf demoothers = new OtherReportsPdf();
    DatabaseQuaries dbq =new DatabaseQuaries();
    LoanSavingsSharesOthers lsso=new LoanSavingsSharesOthers();
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
         DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
         Formartter fmt= new Formartter();
        ReportsPDFFormulars statement= new ReportsPDFFormulars(); 
        BVAReport budget=new BVAReport();
        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
        ReportsDatabase rdb = new ReportsDatabase();
         Boolean status;
           private ProgressMonitor progressMonitor;
           SwingWorker<Boolean, Void> balanceSheetMaker;
    public ReportView(String userId,String signalw,List details) {
        
           this.userId=userId;
            this.signal=signalw;
            this.otherDetails=details;
        initComponents();
//          footerField = new JTextField("Page {0}");
         java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
      this.setTitle("REPORTS MODULE-"+dbq.title(userId));  
      
      jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setRowHeight(30);
jTable1.setGridColor(Color.gray);

jTable1.addMouseListener(this);

jTable3StatementAnalaysis.addMouseListener(this);


 jTable3StatementAnalaysis.setAlignmentX(LEFT_ALIGNMENT);
jTable3StatementAnalaysis.setAlignmentY(CENTER_ALIGNMENT);
jTable3StatementAnalaysis.setAutoscrolls(true);
jTable3StatementAnalaysis.setShowHorizontalLines(true);
jTable3StatementAnalaysis.setShowGrid(true);
jTable3StatementAnalaysis.setRowHeight(30);
jTable3StatementAnalaysis.setGridColor(Color.gray);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        statementAnalysisPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3StatementAnalaysis = new javax.swing.JTable();
        statmentMainTablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Export Excel");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(250, 660, 110, 40);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Print");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(10, 660, 110, 40);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Send Email");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(130, 660, 110, 40);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(102, 153, 255));
        jTextPane1.setAutoscrolls(false);
        jScrollPane3.setViewportView(jTextPane1);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 1180, 70);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Search By Any Column Field");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(370, 660, 180, 40);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField1);
        jTextField1.setBounds(560, 660, 320, 40);

        statementAnalysisPanel.setBackground(java.awt.SystemColor.activeCaption);
        statementAnalysisPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(102, 255, 255)));
        statementAnalysisPanel.setLayout(null);

        jTable3StatementAnalaysis.setAutoCreateRowSorter(true);
        jTable3StatementAnalaysis.setBackground(new java.awt.Color(0, 153, 102));
        jTable3StatementAnalaysis.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 0)));
        jTable3StatementAnalaysis.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTable3StatementAnalaysis.setForeground(new java.awt.Color(0, 102, 102));
        jTable3StatementAnalaysis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3StatementAnalaysis.setToolTipText("Double click the table for further anlysis");
        jScrollPane4.setViewportView(jTable3StatementAnalaysis);

        statementAnalysisPanel.add(jScrollPane4);
        jScrollPane4.setBounds(0, 0, 1180, 590);

        jPanel2.add(statementAnalysisPanel);
        statementAnalysisPanel.setBounds(0, 70, 1180, 590);
        statementAnalysisPanel.setVisible(false);

        statmentMainTablePanel.setBackground(java.awt.SystemColor.activeCaption);
        statmentMainTablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statmentMainTablePanel.setLayout(null);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        statmentMainTablePanel.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 1180, 590);

        jPanel2.add(statmentMainTablePanel);
        statmentMainTablePanel.setBounds(0, 70, 1180, 590);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 10, 1180, 980);

        jButton5.setBackground(new java.awt.Color(255, 204, 204));
        jButton5.setText("jButton5");
        getContentPane().add(jButton5);
        jButton5.setBounds(1490, 310, 73, 23);

        jButton6.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton6.setText("jButton5");
        getContentPane().add(jButton6);
        jButton6.setBounds(1450, 410, 73, 23);

        jButton7.setBackground(new java.awt.Color(51, 0, 51));
        jButton7.setText("jButton5");
        getContentPane().add(jButton7);
        jButton7.setBounds(1450, 470, 73, 23);

        jButton9.setBackground(new java.awt.Color(255, 204, 153));
        jButton9.setText("Blue");
        getContentPane().add(jButton9);
        jButton9.setBounds(1350, 280, 53, 23);

        jButton10.setBackground(new java.awt.Color(255, 153, 0));
        jButton10.setText("Blue");
        getContentPane().add(jButton10);
        jButton10.setBounds(1430, 200, 53, 23);

        jButton11.setBackground(new java.awt.Color(0, 204, 255));
        jButton11.setText("Blue");
        getContentPane().add(jButton11);
        jButton11.setBounds(1370, 230, 53, 23);

        jButton12.setBackground(new java.awt.Color(204, 204, 255));
        jButton12.setText("Blue");
        getContentPane().add(jButton12);
        jButton12.setBounds(1410, 290, 53, 23);

        jButton13.setBackground(new java.awt.Color(0, 204, 204));
        jButton13.setText("Blue");
        getContentPane().add(jButton13);
        jButton13.setBounds(1520, 240, 53, 23);

        jButton14.setBackground(new java.awt.Color(0, 153, 255));
        jButton14.setText("Blue");
        getContentPane().add(jButton14);
        jButton14.setBounds(1600, 280, 53, 23);

        jButton15.setBackground(new java.awt.Color(204, 204, 0));
        jButton15.setText("Blue");
        getContentPane().add(jButton15);
        jButton15.setBounds(1520, 280, 53, 23);

        jButton16.setBackground(new java.awt.Color(255, 204, 204));
        jButton16.setText("Blue");
        getContentPane().add(jButton16);
        jButton16.setBounds(1560, 200, 53, 23);

        jButton17.setBackground(new java.awt.Color(204, 204, 204));
        jButton17.setText("Blue");
        getContentPane().add(jButton17);
        jButton17.setBounds(1630, 220, 53, 23);

        jButton20.setBackground(new java.awt.Color(204, 153, 255));
        jButton20.setText("Blue");
        getContentPane().add(jButton20);
        jButton20.setBounds(1350, 190, 53, 23);

        jButton21.setBackground(new java.awt.Color(0, 204, 102));
        jButton21.setText("Blue");
        getContentPane().add(jButton21);
        jButton21.setBounds(1450, 220, 53, 23);

        jButton23.setBackground(new java.awt.Color(201, 222, 223));
        jButton23.setText("Blue");
        getContentPane().add(jButton23);
        jButton23.setBounds(1420, 330, 53, 23);

        jButton24.setBackground(new java.awt.Color(204, 255, 204));
        jButton24.setText("Blue");
        getContentPane().add(jButton24);
        jButton24.setBounds(1640, 300, 53, 23);

        jButton25.setBackground(new java.awt.Color(204, 165, 165));
        jButton25.setText("Blue");
        getContentPane().add(jButton25);
        jButton25.setBounds(1640, 250, 53, 23);

        jButton27.setBackground(new java.awt.Color(152, 198, 94));
        jButton27.setText("Blue");
        getContentPane().add(jButton27);
        jButton27.setBounds(1640, 320, 53, 23);

        jButton8.setText("jButton8");
        getContentPane().add(jButton8);
        jButton8.setBounds(1580, 420, 73, 23);

        jButton35.setBackground(new java.awt.Color(255, 255, 204));
        jButton35.setText("Blue");
        getContentPane().add(jButton35);
        jButton35.setBounds(1350, 250, 53, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton5.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton5.setBackground(jButton10.getBackground()); 
                    
                    break;
                case "3":
                     jButton5.setBackground(jButton11.getBackground());
                    break;
                case "4":
                    jButton5.setBackground(jButton12.getBackground()); 
                    break;
                case "5":
                    jButton5.setBackground(jButton13.getBackground()); 
                    break;
                case "6":
                  jButton5.setBackground(jButton14.getBackground());   
                    break;
                case "7":
                   jButton5.setBackground(jButton15.getBackground());  
                    break;
                case "8":
                  jButton5.setBackground(jButton16.getBackground());   
                    break;
                case "9":
                   jButton5.setBackground(jButton17.getBackground());  
                    break;  
                case "10":
                    jButton5.setBackground(jButton25.getBackground()); 
                    break;
                case "11":
                     jButton5.setBackground(jButton25.getBackground());
                    break;
                case "12":
                    jButton5.setBackground(jButton20.getBackground()); 
                    break; 
                case "13":
                     jButton5.setBackground(jButton21.getBackground());
                    break;  
                case "14":
                   jButton5.setBackground(jButton9.getBackground());  
                    break; 
                case "15":
                     jButton5.setBackground(jButton23.getBackground());
                    break;
                case "16":
                  jButton5.setBackground(jButton24.getBackground());   
                    break;  
                case "17":
                     jButton5.setBackground(jButton25.getBackground());
                    break;
                case "18":
                     jButton5.setBackground(jButton25.getBackground());
                    break; 
                case "19":
                     jButton5.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton5.setBackground(jButton25.getBackground());   
                    break;  
             
                        
                    

}
      


switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton8.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton8.setBackground(jButton10.getBackground()); 
                    
                    break;
                case "3":
                     jButton8.setBackground(jButton11.getBackground());
                    break;
                case "4":
                    jButton8.setBackground(jButton12.getBackground()); 
                    break;
                case "5":
                    jButton8.setBackground(jButton13.getBackground()); 
                    break;
                case "6":
                  jButton8.setBackground(jButton14.getBackground());   
                    break;
                case "7":
                   jButton8.setBackground(jButton15.getBackground());  
                    break;
                case "8":
                  jButton8.setBackground(jButton16.getBackground());   
                    break;
                case "9":
                   jButton8.setBackground(jButton17.getBackground());  
                    break;  
                case "10":
                    jButton8.setBackground(jButton25.getBackground()); 
                    break;
                case "11":
                     jButton8.setBackground(jButton25.getBackground());
                    break;
                case "12":
                    jButton8.setBackground(jButton20.getBackground()); 
                    break; 
                case "13":
                     jButton8.setBackground(jButton21.getBackground());
                    break;  
                case "14":
                   jButton8.setBackground(jButton9.getBackground());  
                    break; 
                case "15":
                     jButton8.setBackground(jButton23.getBackground());
                    break;
                case "16":
                  jButton8.setBackground(jButton24.getBackground());   
                    break;  
                case "17":
                     jButton8.setBackground(jButton25.getBackground());
                    break;
                case "18":
                     jButton8.setBackground(jButton25.getBackground());
                    break; 
                case "19":
                     jButton8.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton8.setBackground(jButton25.getBackground());   
                    break;  
             
                        
                    

}
  HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer()); 
        switch(signal){
            case "BudgetVarianceAnalysisReport":
                fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
           jTextPane1.setText("\t"+"BUDGET VARIANCE ANLYSIS REPORT FOR THE PERIOD "+"\n"+"\t"+"\t"+"\t"+"\t"+" "+otherDetails.get(0).toString().toUpperCase()+" "+"TO"+" "+otherDetails.get(1).toString().toUpperCase()+"\t"+"\t");
           
          if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Budget Report was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Budget Report is empty");
     
       }
         
        sortTable(jTable1,jTextField1);   
            this.renderTheTables(jTable1, 1);
                break;
        case "DemographicDetails":
            fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
        jTextPane1.setText("LIST OF CUSTOMERS BY THIER DEMOGRAPHIC CHARACTERISTICS "+"\n"+"\t"+"\t"+"\t"+"\t"+"AS AT "+" "+sdf.format(fmt.getTodayDate())+"\t"+"\t");

        demoothers.createCustomerDemographReal(jTable1);
        sortTable(jTable1,jTextField1);
        int n11=0;
        while(n11<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(n11).setHeaderRenderer(header);
        n11++;       
        }
        
        break;

        case "TotalLoanSavingsReport":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
        jTextPane1.setText("TOTAL LOAN AND SAVINGS FOR ALL MEMBERS FOR THE MONTHS "+"\n"+"\t"+"RANGING FROM"+" "+otherDetails.get(2).toString().toUpperCase()+" "+"TO"+" "+otherDetails.get(3).toString().toUpperCase().toUpperCase()+"\t"+"\t");  

        lsso.createLoanSavingsOnOthers(jTable1,parseInt(otherDetails.get(0).toString()),parseInt(otherDetails.get(1).toString()));

        sortTable(jTable1,jTextField1);   

      
            break;
 case "TotalLoanPaymentReport":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
        jTextPane1.setText("TOTAL LOAN REPAYLMENTS FOR ALL MEMBERS FOR THE MONTHS "+"\n"+"\t"+"RANGING FROM"+" "+otherDetails.get(2).toString().toUpperCase()+" "+"TO"+" "+otherDetails.get(3).toString().toUpperCase().toUpperCase()+"\t"+"\t");  

        lsso.createLoanPaymentsOnOthers(jTable1,parseInt(otherDetails.get(0).toString()),parseInt(otherDetails.get(1).toString()));

        sortTable(jTable1,jTextField1);   

        int ns=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(ns<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(ns).setHeaderRenderer(header);

        if(ns==0){
        jTable1.getColumnModel().getColumn(ns).setMinWidth(0);
        jTable1.getColumnModel().getColumn(ns).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(ns).setPreferredWidth(60);

        }
        if(ns==1){
        jTable1.getColumnModel().getColumn(ns).setMinWidth(0);
        jTable1.getColumnModel().getColumn(ns).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(ns).setPreferredWidth(110);
        }
        if(ns==2){
        jTable1.getColumnModel().getColumn(ns).setMinWidth(0);
        jTable1.getColumnModel().getColumn(ns).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(ns).setPreferredWidth(260);
        }
        ns++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }

        if(col>=3&&col<=4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            break;
     case "TotalSavingsPaymentReport":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
        jTextPane1.setText("TOTAL SAVINGS CONTRIBUTED FOR ALL MEMBERS FOR THE MONTHS "+"\n"+"\t"+"RANGING FROM"+" "+otherDetails.get(2).toString().toUpperCase()+" "+"TO"+" "+otherDetails.get(3).toString().toUpperCase().toUpperCase()+"\t"+"\t");  

        lsso.createSavingsPaymentsOnOthers(jTable1,parseInt(otherDetails.get(0).toString()),parseInt(otherDetails.get(1).toString()));

        sortTable(jTable1,jTextField1);   

        int nsl=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(nsl<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(nsl).setHeaderRenderer(header);

        if(nsl==0){
        jTable1.getColumnModel().getColumn(nsl).setMinWidth(0);
        jTable1.getColumnModel().getColumn(nsl).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(nsl).setPreferredWidth(60);

        }
        if(nsl==1){
        jTable1.getColumnModel().getColumn(nsl).setMinWidth(0);
        jTable1.getColumnModel().getColumn(nsl).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(nsl).setPreferredWidth(110);
        }
        if(nsl==2){
        jTable1.getColumnModel().getColumn(nsl).setMinWidth(0);
        jTable1.getColumnModel().getColumn(nsl).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(nsl).setPreferredWidth(260);
        }
        nsl++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }

        if(col>=3&&col<=4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            break;       
            
            case "CustomerStatment":
                
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 
jTextPane1.setText("STATEMENT OF ACCOUNT FOR ACCOUNT NUMBER:"+" "+otherDetails.get(0).toString()+"\n"+"\t"+"\t"+" "+dbq.AccountName(otherDetails.get(0).toString()).toUpperCase()+" "+"FROM"+" "+otherDetails.get(1).toString().toUpperCase().toUpperCase()+" "+"TO"+" "+otherDetails.get(2).toString().toUpperCase().toUpperCase()+"\t"+"\t");  

        statement.createStatementSt(jTable1,otherDetails.get(0).toString(),fmt.forDatabaseWithFullYearBeginningWithDate(otherDetails.get(1).toString()),fmt.forDatabaseWithFullYearBeginningWithDate(otherDetails.get(2).toString()));

        sortTable(jTable1,jTextField1);   

        int hc=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hc<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hc).setHeaderRenderer(header);

        if(hc==0){
        jTable1.getColumnModel().getColumn(hc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hc).setPreferredWidth(100);

        }
        if(hc==1){
        jTable1.getColumnModel().getColumn(hc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hc).setPreferredWidth(100);
        }
        if(hc==2){
        jTable1.getColumnModel().getColumn(hc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hc).setPreferredWidth(300);
        }if(hc==6){
        jTable1.getColumnModel().getColumn(hc).setMinWidth(4);
        jTable1.getColumnModel().getColumn(hc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hc).setPreferredWidth(4);
        }
        hc++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
// this.setText(value.toString());
        
if(col>=3&&col<=5){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("0002x"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            

            break;

        
         case "customerStatementAmnt":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
jTextPane1.setText("STATEMENT OF ACCOUNT FOR ACCOUNT NUMBER:"+" "+otherDetails.get(0).toString()+"\n"+"\t"+"\t"+" "+dbq.AccountName(otherDetails.get(0).toString()).toUpperCase()+" "+"FROM"+" "+otherDetails.get(1).toString().toUpperCase().toUpperCase()+" "+"TO"+" "+otherDetails.get(2).toString().toUpperCase().toUpperCase()+"\t"+"\t");  

        statement.createStatementStAmount(jTable1,otherDetails.get(0).toString(),fmt.forDatabaseWithFullYearBeginningWithDate(otherDetails.get(1).toString()),fmt.forDatabaseWithFullYearBeginningWithDate(otherDetails.get(2).toString()),otherDetails.get(3).toString());

        sortTable(jTable1,jTextField1);   

        int h1=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(100);

        }
        if(h1==1){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(100);
        }
        if(h1==2){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(300);
        }
        if(h1==6){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(4);
        }
        h1++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

if(col>=3&&col<=5){
             String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals(""))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
     this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        
        return this;
        }   
        });




            break;
            
         case "BalanceSheet":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);             
             jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(false);
//            TheBalanceSheet finPos=new TheBalanceSheet(otherDetails.get(0).toString()); 
            
          jTextPane1.setText("\t"+"\t"+"STATEMENT OF FINANCIAL POSITION AT"+" "+otherDetails.get(0).toString()+"\t"+"\n"+"\t"+"\t"+"\t"+"\t"+"CURRENCY: UGANDA SHILLINGS");  

             if((ListDataModel)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Financial Postion was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(1));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Financial Postion is empty");
     
       }
         
    int xc=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(false);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(xc<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(xc).setHeaderRenderer(header);

        if(xc==0){
        jTable1.getColumnModel().getColumn(xc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xc).setPreferredWidth(450);

        }
        if(xc==1){
        jTable1.getColumnModel().getColumn(xc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xc).setPreferredWidth(80);
        }
        if(xc==2){
        jTable1.getColumnModel().getColumn(xc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xc).setPreferredWidth(200);
        }
        if(xc==3){
        jTable1.getColumnModel().getColumn(xc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xc).setPreferredWidth(200);
        }
        xc++;

        }  
       sortTable(jTable1,jTextField1);   

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);
        
//         if(!(value.toString().equalsIgnoreCase("Current assets")||value.toString().equalsIgnoreCase("Non-current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total non-current assets")||value.toString().equalsIgnoreCase("LIABILITIES AND EQUITY")||value.toString().equalsIgnoreCase("Total liabilities and equity")||value.toString().equalsIgnoreCase("Current liabilities")||value.toString().equalsIgnoreCase("Non-current liabilities")||value.toString().equalsIgnoreCase("Equity")||value.toString().equalsIgnoreCase("Total liabilities")||value.toString().equalsIgnoreCase("Total equity")||value.toString().equalsIgnoreCase("Total non-current liabilities")||value.toString().equalsIgnoreCase("Total current liabilities")||value.toString().equalsIgnoreCase("ASSETS")||value.toString().equalsIgnoreCase("Total assets")||value.toString().equalsIgnoreCase("ASSETS")||value.toString().equalsIgnoreCase("Total assets"))){
//   
//   this.setFont(new Font("Arial",Font.PLAIN,18));
//   }    

if(value.toString().equalsIgnoreCase("Current assets")||value.toString().equalsIgnoreCase("Non-current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total non-current assets")){
  
     this.setFont(new Font("Arial",Font.PLAIN,18));
   
    }
         
   if(value.toString().equalsIgnoreCase("LIABILITIES AND EQUITY")||value.toString().equalsIgnoreCase("Total liabilities and equity")){
   
     this.setFont(new Font("Arial",Font.BOLD,24));
     
    }   
     
      if(value.toString().equalsIgnoreCase("Current liabilities")||value.toString().equalsIgnoreCase("Non-current liabilities")||value.toString().equalsIgnoreCase("Equity")||value.toString().equalsIgnoreCase("Total liabilities")||value.toString().equalsIgnoreCase("Total equity")||value.toString().equalsIgnoreCase("Total non-current liabilities")||value.toString().equalsIgnoreCase("Total current liabilities")){
    
     this.setFont(new Font("Arial",Font.PLAIN,18));
    
    } 
      
   if(value.toString().equalsIgnoreCase("ASSETS")||value.toString().equalsIgnoreCase("Total assets")){
    
     this.setFont(new Font("Arial",Font.BOLD,24));
     
    }
   
   
  if(row==0){
  
       this.setFont(new Font("Arial",Font.BOLD,24));
       this.setHorizontalAlignment(RIGHT);
  }
   
 this.setText(value.toString());
      if(row>1){   
if(col>=2&&col<=3){
            String text="";
      
        if(!(value.toString().equals("-")||value.toString().equals(""))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        
        
}
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }}
      
 
    
      
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });  
//        }   
             break;
             
         case "ProfitAndLoss":
 fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
          jTextPane1.setText("STATEMENT OF INCOME AND RETAINED EARNINGS FOR  THE PERIOD ENDED"+"\t"+"\n"+"\t"+"\t"+otherDetails.get(1).toString()+" "+"CURRENCY: UGANDA SHILLINGS");  

          if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Income and Retained Earnings was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Income and Retained Earnings is empty");
     
       }
          
       
       
       
           int xdc=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(false);

        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        
        while(xdc<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(xdc).setHeaderRenderer(header);

        if(xdc==0){
        jTable1.getColumnModel().getColumn(xdc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xdc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xdc).setPreferredWidth(450);

        }
        if(xdc==1){
        jTable1.getColumnModel().getColumn(xdc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xdc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xdc).setPreferredWidth(80);
        }
        if(xdc==2){
        jTable1.getColumnModel().getColumn(xdc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xdc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xdc).setPreferredWidth(200);
        }
        if(xdc==3){
        jTable1.getColumnModel().getColumn(xdc).setMinWidth(0);
        jTable1.getColumnModel().getColumn(xdc).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(xdc).setPreferredWidth(200);
        }
        xdc++;

        }  
       sortTable(jTable1,jTextField1);   

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);
        
//         if(!(value.toString().equalsIgnoreCase("Current assets")||value.toString().equalsIgnoreCase("Non-current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total current assets")||value.toString().equalsIgnoreCase("Total non-current assets")||value.toString().equalsIgnoreCase("LIABILITIES AND EQUITY")||value.toString().equalsIgnoreCase("Total liabilities and equity")||value.toString().equalsIgnoreCase("Current liabilities")||value.toString().equalsIgnoreCase("Non-current liabilities")||value.toString().equalsIgnoreCase("Equity")||value.toString().equalsIgnoreCase("Total liabilities")||value.toString().equalsIgnoreCase("Total equity")||value.toString().equalsIgnoreCase("Total non-current liabilities")||value.toString().equalsIgnoreCase("Total current liabilities")||value.toString().equalsIgnoreCase("ASSETS")||value.toString().equalsIgnoreCase("Total assets")||value.toString().equalsIgnoreCase("ASSETS")||value.toString().equalsIgnoreCase("Total assets"))){
//   
//   this.setFont(new Font("Arial",Font.PLAIN,18));
//   }    

this.setText(value.toString());

     
//      if(value.toString().equalsIgnoreCase("Gross Interest Income")||value.toString().equalsIgnoreCase("Dividends paid during the Operating period")||value.toString().equalsIgnoreCase("Loan Insurance Charges")){
    
    
    
//    } 
      
   if(value.toString().equalsIgnoreCase("Profit before tax")||value.toString().equalsIgnoreCase("Profit for the period")){
    
     this.setFont(new Font("Arial",Font.BOLD,24));
   
    
    }else{
    this.setFont(new Font("Arial",Font.PLAIN,18));
   
   }
        
    
      if(value.toString().equalsIgnoreCase("REVENUE")||value.toString().equalsIgnoreCase("Total Income")||value.toString().equalsIgnoreCase("Retained earnings at end of Period")){
    
     this.setFont(new Font("Arial",Font.BOLD,24));
     
    }					 
      
      if(value.toString().equalsIgnoreCase("EXPENSES")||value.toString().equalsIgnoreCase("Total Expenses")){
   
     this.setFont(new Font("Arial",Font.BOLD,24));
     
    } 
   
  if(row==0){
  
       this.setFont(new Font("Arial",Font.BOLD,24));
       this.setHorizontalAlignment(RIGHT);
       
  }
    

      if(row>1){   
if(col>=2&&col<=3){
            String text="";
      
        if(!(value.toString().equals("-")||value.toString().equals(""))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        
        
}
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }}
      
 
    
      
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });  
             
             break; 
             
             case "AgingAnalysis":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t"+"\t"+"DETAILS OF THE LOANS AGING ANLYSIS AS AT"+" "+fmt.fromDatabaseWithDashSeperatorBeginningWithYear(fmt.dateConverterForDatabase(System.currentTimeMillis( ))));  

      if((ListDataModel)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of loan Aging Analysis was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(0));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statementof loan Aging Analysis is empty");
     
       }
          
  int hck1=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hck1<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hck1).setHeaderRenderer(header);

        if(hck1==0){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(60);

        }
        if(hck1==1){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(230);
        }
        if(hck1==2){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==3){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==4){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==5){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==6){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==7){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(150);
        }if(hck1==8){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(100);
        }if(hck1==9){
        jTable1.getColumnModel().getColumn(hck1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hck1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hck1).setPreferredWidth(100);
        }
        
        hck1++;

        }
   sortTable(jTable1,jTextField1);  
//        this.setHorizontalAlignment(JLabel.LEFT);

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          

        this.setHorizontalAlignment(JLabel.LEFT);
        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,16));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,15));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=8){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("0002x"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            

            break; 
             
           case "ProjectedPaymens":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"PROJECTED LOAN PAYMENT DETAILS FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of projected loan details was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of projected loan details is empty");
     
       }
          int hckx=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckx<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hckx).setHeaderRenderer(header);

        if(hckx==0){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(60);

        }
        if(hckx==1){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(230);
        }
        if(hckx==2){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }if(hckx==3){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(90);
        }if(hckx==4){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }if(hckx==5){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }if(hckx==6){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }if(hckx==7){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }if(hckx==8){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }
        if(hckx==9){
        jTable1.getColumnModel().getColumn(hckx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckx).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckx).setPreferredWidth(150);
        }
        hckx++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=4&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            

            break;  
            
    case "quickSummury":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"QUICK SUMMURY REPORT AS AT "+sdf.format(new Date(System.currentTimeMillis())));  

      if((ListDataModel)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of quick summury was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(0));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Quick Summury is empty");
     
       }
          int hckxf=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxf<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hckxf).setHeaderRenderer(header);

        if(hckxf==0){
        jTable1.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckxf).setPreferredWidth(250);

        }
        if(hckxf==1){
        jTable1.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }
        if(hckxf==2){
        jTable1.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckxf).setPreferredWidth(250);
        }if(hckxf==3){
        jTable1.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }
        
        
        hckxf++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,20));
        }
 this.setText(value.toString());
        
if(col==1&&col==3){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            

            break;           
            
            
            
            
            
          case "roInvestment":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"RETURN ON INVESTMENT FOR"+" "+dbq.AccountName(otherDetails.get(0).toString())+" "+"FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(2).toString());  

      if((ListDataModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of return on investment was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(3));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of return on investment is empty");
     
       }
          int hd=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd).setHeaderRenderer(header);

        if(hd==0){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(60);

        }
        if(hd==1){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(100);
        }
        if(hd==2){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(250);
        }if(hd==3){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(150);
        }if(hd==4){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(150);
        }if(hd==5){
        jTable1.getColumnModel().getColumn(hd).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd).setPreferredWidth(150);
        }
        
        hd++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;   
       
         case "roInvestmentDG":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"GENERAL DAILY RETURN ON INVESTMENT FOR FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of return on investment was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of return on investment details is empty");
     
       }
          int hd2=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2).setHeaderRenderer(header);

        if(hd2==0){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(60);

        }
        if(hd2==1){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(100);
        }
        if(hd2==2){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(250);
        }if(hd2==3){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(150);
        }if(hd2==4){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(150);
        }if(hd2==5){
        jTable1.getColumnModel().getColumn(hd2).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2).setPreferredWidth(150);
        }
        
        hd2++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;  
         
         
        case "accountabalances":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"ACCOUNT BALANCES AS AT  "+" "+"\n\t"+"\t\t"+otherDetails.get(0).toString());  

      if((ListDataModel)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of account balance was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(1));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of account balance details is empty");
     
       }
          int hd2c=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2c<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2c).setHeaderRenderer(header);

        if(hd2c==0){
        jTable1.getColumnModel().getColumn(hd2c).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2c).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2c).setPreferredWidth(60);

        }
        if(hd2c==1){
        jTable1.getColumnModel().getColumn(hd2c).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2c).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2c).setPreferredWidth(100);
        }
        if(hd2c==2){
        jTable1.getColumnModel().getColumn(hd2c).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2c).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2c).setPreferredWidth(250);
        }if(hd2c==3){
        jTable1.getColumnModel().getColumn(hd2c).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2c).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2c).setPreferredWidth(150);
        }
        
        hd2c++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;  
         
         
         
         
         case "generalLedgerAll":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"GENERAL LEDGER ITEMS FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The General ledger statement was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The General ledger statement is empty");
     
       }
          int hd1x=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd1x<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd1x).setHeaderRenderer(header);

        if(hd1x==0){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(80);

        }
        if(hd1x==1){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(120);
        }
        if(hd1x==2){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(120);
        }if(hd1x==3){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(150);
        }if(hd1x==4){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(150);
        }if(hd1x==5){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(220);
      }if(hd1x==6){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(200);
    }if(hd1x==7){
        jTable1.getColumnModel().getColumn(hd1x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x).setPreferredWidth(200);
        }
        
        hd1x++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=4){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break; 
             
              case "generalLedgerAccount":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"GENERAL LEDGER ITEMS FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(2).toString());  

      if((ListDataModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The General ledger statement was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(3));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The General ledger statement is empty");
     
       }
          int hd1x1=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd1x1<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd1x1).setHeaderRenderer(header);

        if(hd1x1==0){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(80);

        }
        if(hd1x1==1){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(120);
        }
        if(hd1x1==2){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(120);
        }if(hd1x1==3){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(150);
        }if(hd1x1==4){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(150);
        }if(hd1x1==5){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(220);
      }if(hd1x1==6){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(200);
    }if(hd1x1==7){
        jTable1.getColumnModel().getColumn(hd1x1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd1x1).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd1x1).setPreferredWidth(200);
        }
        
        hd1x1++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=4){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break; 
          case "sharesContributed":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"STATEMENT OF SHARES CONTRIBUTIONS FOR THE PERIOD"+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of shares contributions was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(3));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of shares contributions is empty");
     
       }
          int hd2x=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2x<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2x).setHeaderRenderer(header);

        if(hd2x==0){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(60);

        }
        if(hd2x==1){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(100);
        }
        if(hd2x==2){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(150);
        }if(hd2x==3){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(150);
        }if(hd2x==4){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(50);
        }if(hd2x==5){
        jTable1.getColumnModel().getColumn(hd2x).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2x).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2x).setPreferredWidth(150);
        }
        
        hd2x++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=2&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;  
         
          case "savingsContributed":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t"+"SAVINGS FOR"+" "+dbq.AccountName(otherDetails.get(0).toString())+" "+"("+otherDetails.get(0).toString()+")"+" "+"FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(2).toString());  

      if((ListDataModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of savings contributions was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(3));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of savings contributions is empty");
     
       }
          int he=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(he<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(he).setHeaderRenderer(header);

        if(he==0){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(5);

        }
         if(he==1){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(60);

        }
        if(he==2){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(150);
        }
        if(he==3){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(100);
        }if(he==4){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(100);
        }if(he==5){
        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(he).setPreferredWidth(100);
        }
//        if(he==5){
//        jTable1.getColumnModel().getColumn(he).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(he).setMaxWidth(1500);
//        jTable1.getColumnModel().getColumn(he).setPreferredWidth(150);
//        }
        
        he++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            private static final long serialVersionUID = 1L;
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


//        if(row==table.getRowCount()-1){
//        this.setFont(new Font("Arial",Font.BOLD,20));
//        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
//        }
 this.setText(value.toString());
        
if(col>=3&&col<=5){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break; 
         
   case "listGroupSharesContributed":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"LIST OF ALL MEMBERS SHARES CONTRIBUTIONS FOR THE PERIOD"+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of shares contributions was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of list of shares contributions is empty");
     
       }
          int hb=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hb<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hb).setHeaderRenderer(header);

        if(hb==0){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(60);

        }
        if(hb==1){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(100);
        }
        if(hb==2){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
        }if(hb==3){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
        }if(hb==4){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
        }if(hb==5){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(50);
        }
        if(hb==6){
        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
        }
        hb++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;     
         
     case "listGroupSavingsContributedAll":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t"+"LIST OF ALL MEMBERS SAVINGS FOR THE PERIOD"+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of savings was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of list of savings is empty");
     
       }
          int hbs=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hbs<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hbs).setHeaderRenderer(header);

        if(hbs==0){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(60);

        }
        if(hbs==1){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(100);
        }
        if(hbs==2){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(150);
        }if(hbs==3){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(150);
        }if(hbs==4){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(150);
        }if(hbs==5){
        jTable1.getColumnModel().getColumn(hbs).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hbs).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hbs).setPreferredWidth(150);
        }
//        if(hb==6){
//        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
//        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
//        }
        hbs++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;        
         
         
         case "listGroupSavingsContributed":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"LIST OF  MEMBERS WITH SAVINGS  FOR THE PERIOD"+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Savings  was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of list of savings  is empty");
     
       }
          int hec=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hec<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hec).setHeaderRenderer(header);

        if(hec==0){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(60);

        }
        if(hec==1){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(100);
        }
        if(hec==2){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(150);
        }if(hec==3){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(150);
        }if(hec==4){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(150);
        }if(hec==5){
        jTable1.getColumnModel().getColumn(hec).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hec).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hec).setPreferredWidth(50);
        }
//        if(hb==6){
//        jTable1.getColumnModel().getColumn(hb).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hb).setMaxWidth(1500);
//        jTable1.getColumnModel().getColumn(hb).setPreferredWidth(150);
//        }
        hec++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=5){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;    
         
 case "individualMontlyReturnOnInvestment":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
  jTextPane1.setText("\t\t"+"MONTHLY RETURN ON INVESTMENT FOR"+" "+dbq.AccountName(otherDetails.get(0).toString())+" "+"FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(2).toString());  

      if((ListDataModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of individual montly return on investment was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(3));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of individual montly return on investment is empty");
     
       }
          int hd2w=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2w<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2w).setHeaderRenderer(header);

        if(hd2w==0){
        jTable1.getColumnModel().getColumn(hd2w).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2w).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2w).setPreferredWidth(60);

        }
        if(hd2w==1){
        jTable1.getColumnModel().getColumn(hd2w).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2w).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2w).setPreferredWidth(100);
        }
        if(hd2w==2){
        jTable1.getColumnModel().getColumn(hd2w).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2w).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2w).setPreferredWidth(250);
        }if(hd2w==3){
        jTable1.getColumnModel().getColumn(hd2w).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2w).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2w).setPreferredWidth(150);
        }if(hd2w==4){
        jTable1.getColumnModel().getColumn(hd2w).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2w).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2w).setPreferredWidth(150);
        }
        hd2w++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            break;      
         case "groupMontlyReturnOnInvestment":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
  jTextPane1.setText("\t\t"+"MONTHLY RETURN ON INVESTMENT FOR   THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of group montly return on investment was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of group montly return on investment is empty");
     
       }
          int hd2wf=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2wf<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2wf).setHeaderRenderer(header);

        if(hd2wf==0){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(60);

        }
        if(hd2wf==1){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(100);
        }
        if(hd2wf==2){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(250);
        }if(hd2wf==3){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(150);
        }if(hd2wf==4){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(150);
        }if(hd2wf==5){
        jTable1.getColumnModel().getColumn(hd2wf).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2wf).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2wf).setPreferredWidth(150);
        }
        hd2wf++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=3&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            break;   
         case "provisionedItemsNew":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"STATEMENT OF BAD LOANS PROVISIONED FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of provisioned items was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of provision of bad loans is empty");
     
       }
          int hd2d=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hd2d<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hd2d).setHeaderRenderer(header);

        if(hd2d==0){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(60);

        }
        if(hd2d==1){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(130);
        }
        if(hd2d==2){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(250);
        }if(hd2d==3){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(100);
        }if(hd2d==4){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(100);
        }if(hd2d==5){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(100);
        }
        if(hd2d==6){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(150);
        }
        if(hd2d==7){
        jTable1.getColumnModel().getColumn(hd2d).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hd2d).setMaxWidth(1500);
        jTable1.getColumnModel().getColumn(hd2d).setPreferredWidth(150);
        }
        hd2d++;

        }
   sortTable(jTable1,jTextField1);  
  

       jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
 this.setText(value.toString());
        
if(col>=4&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
         break;  
         
         case "loanStatusReport":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"STATEMENT OF LOAN STATUS REPORT AS AT"+" "+sdk.format(new Date(System.currentTimeMillis())));  

      if((ReportsModelStatusReport)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Loan Status Report was succesfully created");   
               jTable1.setModel((ReportsModelStatusReport)otherDetails.get(0));
         TableRowSorter<ReportsModelStatusReport> sorter = new TableRowSorter<>((ReportsModelStatusReport)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Loan Status Report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 3);
      break;
         case "budgetEstimates":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"STATEMENT OF BUDGET ESTIMATES AS AT"+" "+sdk.format(new Date(System.currentTimeMillis())));  

      if((budgetEstimatesModel)otherDetails.get(3)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Budget Estimates was succesfully created");   
               jTable1.setModel((budgetEstimatesModel)otherDetails.get(3));
         TableRowSorter<budgetEstimatesModel> sorter = new TableRowSorter<>((budgetEstimatesModel)otherDetails.get(3));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Budget Estimates is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 4);
      break; 
          case "assetRegisterReport":
fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"STATEMENT OF ASSET REGISTER AS AT"+" "+sdk.format(new Date(System.currentTimeMillis())));  

      if((AssetRegisterModel)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of asset register was succesfully created");   
               jTable1.setModel((AssetRegisterModel)otherDetails.get(0));
         TableRowSorter<AssetRegisterModel> sorter = new TableRowSorter<>((AssetRegisterModel)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of asset register is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 5);
      break; 
                  case "LoanStatement":
//fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"LOAN STATEMENT FOR  "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "stateName"+this.userId+".txt"))+" AS AT"+" "+sdk.format(new Date(System.currentTimeMillis())));  

      if((loanSatementModel)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of asset register was succesfully created");   
               jTable1.setModel((loanSatementModel)otherDetails.get(0));
         TableRowSorter<loanSatementModel> sorter = new TableRowSorter<>((loanSatementModel)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of asset register is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 8);
      break;
      
      
      case "summuryStats":
//fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"LIST OF SUMMURY STATISTICS AS AT "+otherDetails.get(0));  

      if((ListDataModel)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of summury statistics was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(1));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of summury statistics is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 9);
      break; 
      
      
       case "indiDailyRoiShares":
//fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 jTextPane1.setText("\t\t"+"DAILY RIO ON SHARES FOR"+" "+dbq.AccountName(otherDetails.get(0).toString())+" "+"FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(1).toString()+" AND "+otherDetails.get(2).toString());  

      if((ListDataModel)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of individual ROI on shares was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(1));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of individual ROI on shares is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 10);
      break; 
      
      
      
       case "closingNotes":
//fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt"), signal);
 
           jTextPane1.setText("\t\t"+"CLOSING NOTES STATEMENT FOR  "+dbq.AccountName(otherDetails.get(1).toString())+" AS AT"+" "+sdk.format(new Date(System.currentTimeMillis())));  

      if((ListDataModel)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of closing notes was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(0));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of closing notes is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
//   this.renderTheTables(jTable1, 8);
      break;                
     
        case "returnShares":
 jTextPane1.setText("\t\t"+"GROUP MONTLY ROI ON SHARES FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of return on shares was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of return on shares is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
//   this.renderTheTables(jTable1, 8);
      break;  
      
      case "returnShares1":
 jTextPane1.setText("\t\t"+"GROUP MONTLY ROI ON SAVINGS FOR THE PERIOD "+" "+"\n\t"+"\t\t"+"BETWEEN"+" "+otherDetails.get(0).toString()+" AND "+otherDetails.get(1).toString());  

      if((ListDataModel)otherDetails.get(2)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of return on savings was succesfully created");   
               jTable1.setModel((ListDataModel)otherDetails.get(2));
         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)otherDetails.get(2));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of return on savings is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
//   this.renderTheTables(jTable1, 8);
      break;
      
              case "dailyCollectionsTotal":
 jTextPane1.setText("\t\t"+"DAILY TOTAL COLLECTION STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily collection report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;
       case "grossLoanPortFolioAnly":
 jTextPane1.setText("\t\t"+"GROSS LOAN PORTFOLIO STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+sdf.format(new Date(System.currentTimeMillis())));  

      if((ListDataModelPortfolioReport)otherDetails.get(0)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Gross Loan Portfolio report  was succesfully created");   
               jTable1.setModel((ListDataModelPortfolioReport)otherDetails.get(0));
         TableRowSorter<ListDataModelPortfolioReport> sorter = new TableRowSorter<>((ListDataModelPortfolioReport)otherDetails.get(0));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of gross Loan Portfolio report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 13);
      break;
      
      case "dailyCollectionsTotalArrear":
 jTextPane1.setText("\t\t"+"DAILY TOTAL COLLECTIONS WITH ARREARS STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily collection report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;
      
      case "dailyCollectionsPrincipal":
 jTextPane1.setText("\t\t"+"DAILY PRINCIMPAL COLLECTIONS  STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily principal collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily principal collection report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;
   case "dailyCollectionsPrincipalArrears":
 jTextPane1.setText("\t\t"+"DAILY PRINCIMPAL COLLECTIONS WITH ARREARS  STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily principal with arrears collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily principal collection with arrears report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;   
      case "dailyCollectionsInterest":
 jTextPane1.setText("\t\t"+"DAILY INTEREST  STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily interest collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily interest report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;  
       case "dailyCollectionsInterestWithArrears":
 jTextPane1.setText("\t\t"+"DAILY INTEREST COLLECTIONS WITH ARREARS  STATEMENT "+" "+"\n\t"+"\t\t"+"AS AT"+" "+otherDetails.get(0).toString());  

      if((ListDataModelDailyCollect)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily interest with arrears collections report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect> sorter = new TableRowSorter<>((ListDataModelDailyCollect)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily interest collection with arrears report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 12);
      break;  
      case "summuryReportWithOneByOneSelections":
 jTextPane1.setText("\t\t"+"SUMMURY  STATEMENT "+" "+"\n\t"+"\t\t"+"FOR THE PERIOD BETWEEN"+" "+fmt.fromDatabaseWithDashSeperatorBeginningWithYear(otherDetails.get(1).toString())+"  AND  "+fmt.fromDatabaseWithDashSeperatorBeginningWithYear(otherDetails.get(2).toString()));  

      if((ListTableModel)otherDetails.get(4)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of summury report  was succesfully created");   
               jTable1.setModel((ListTableModel)otherDetails.get(4));
         TableRowSorter<ListTableModel> sorter = new TableRowSorter<>((ListTableModel)otherDetails.get(4));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of summury  report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 14);
      break; 
      
      case "moneyLendersInterest":
 jTextPane1.setText("\t\t"+"INTEREST ACCRUED STATEMENT FOR "+" "+dbq.AccountName(otherDetails.get(0).toString()) +"\n\t"+"\t\t"+"AS AT"+" "+sdf.format(new Date(System.currentTimeMillis())));  

      if((ListDataModelPortfolioReport1)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Interest Accrued was succesfully created");   
               jTable1.setModel((ListDataModelPortfolioReport1)otherDetails.get(1));
         TableRowSorter<ListDataModelPortfolioReport1> sorter = new TableRowSorter<>((ListDataModelPortfolioReport1)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of Interest Accrued report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 15);
      break;
      
         case "dailySummuryReport":
             
 jTextPane1.setText("\t\t DAILY  SUMMURY STATEMENT(EXCLUDING"+"\n\t SAVING/DEPOSITS MADE AND SAVINGS WITHDRAWS MADE) "+" "+"\n\t"+"\t\t\t\t"+"AS AT "+" "+fmt.fromDatabaseWithDashSeperatorBeginningWithYear(otherDetails.get(0).toString()));  

      if((ListDataModelDailyCollect1)otherDetails.get(1)!=null){
            JOptionPane.showMessageDialog(this, "The Statement of Daily Summury report  was succesfully created");   
               jTable1.setModel((ListDataModelDailyCollect1)otherDetails.get(1));
         TableRowSorter<ListDataModelDailyCollect1> sorter = new TableRowSorter<>((ListDataModelDailyCollect1)otherDetails.get(1));
                            jTable1.setRowSorter(sorter); 
               }  else{
       JOptionPane.showMessageDialog(this, "The Statement of daily Summury report is empty");
     
       }
    jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);  
sortTable(jTable1,jTextField1);       
   this.renderTheTables(jTable1, 16);
      break;
      
      
            }
        
   
            
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//JOptionPane.showMessageDialog(this, jTextPane1.getText());
PrinterJob job = PrinterJob.getPrinterJob();
   job.setPrintable(new Printerdd(jTable1,JTable.PrintMode.FIT_WIDTH,new MessageFormat(jTextPane1.getText()),new MessageFormat("Page {0}")));
    boolean doPrint = job.printDialog();
    if (doPrint) {
    try {
        job.print();
       JOptionPane.showMessageDialog(this,  "Printing Complete", "Printing Result",JOptionPane.INFORMATION_MESSAGE);  
    } catch (PrinterException e) {
        JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + e.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
    }
}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
   
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     
        

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        List<List>  d=new ArrayList();  List c=new ArrayList();
        
        if(signal.equalsIgnoreCase("CustomerStatment")){
        ReportsModelData original = (ReportsModelData)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
           if(jTable1.isCellSelected(targetRow, 0)){
    d.add(original.getRow(targetRow));
           }
//           else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}
        
        
        }else if(signal.equalsIgnoreCase("loanStatusReport")){
       ReportsModelStatusReport original = (ReportsModelStatusReport)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
           if(jTable1.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
           }
//           else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}  
        
        
        
        }else if(signal.equalsIgnoreCase("budgetEstimates")){
             budgetEstimatesModel original = (budgetEstimatesModel)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
           if(jTable1.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
           }
//           else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}  
        
       
        }else if(signal.equalsIgnoreCase("grossLoanPortFolioAnly")){
             ListDataModelPortfolioReport original = (ListDataModelPortfolioReport)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
           if(jTable1.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
           }
//           else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}  
        
       
        }
        
        
        else{
         
            
        ListDataModel original = (ListDataModel)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(jTable1.convertRowIndexToModel(targetRow)).isEmpty()){
            if(jTable1.isCellSelected(jTable1.convertRowIndexToModel(targetRow), 0)){
    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
            }
//            else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}
}
   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ReportView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ReportView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ReportView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ReportView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ReportView("","").setVisible(true);
//            }
//        });
//    }
private void sortTable(JTable table,JTextField textField){

   
    
TableRowSorter<TableModel> rowSorter  = new TableRowSorter<>(table.getModel());
                     
                      table.setRowSorter(rowSorter);
                     textField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    

}

 private void generateFile(String fileName){
  JOptionPane.showMessageDialog(this, fileName);  
   
     if (Desktop.isDesktopSupported()) {
      try {          
          // no application registered for PDFs
          
          File myFile = new File(fios.createFileName1("Excel", "Files", fileName+".xlsx"));
        
          Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
          Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
      }
     }
    
  }
 public String getCounter(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt")))+1);

fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"), thecounter);
return thecounter;
} 
 
 public int print(Graphics g, PageFormat pf, int page,JPanel panel)
    throws PrinterException {
    if (page > 0) {
        return NO_SUCH_PAGE;
    }

    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    // Print the entire visible contents of a
    // java.awt.Frame.
    jPanel2.printAll(g);

    return PAGE_EXISTS;
}
 
 

 
 
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3StatementAnalaysis;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel statementAnalysisPanel;
    private javax.swing.JPanel statmentMainTablePanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
          if (me.getClickCount()==2){
    
    if(me.getSource()==jTable1){
//       if() 
    if(fios.stringFileReader(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt")).equalsIgnoreCase("CustomerStatment")){
    
     int selectedRow =jTable1.getSelectedRow();
     
                    int selectedColumn =jTable1.getSelectedColumn();
                    
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	
         Object cvalue = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 6);
         
         if(cvalue.toString().trim().equalsIgnoreCase("0001x")){
         JOptionPane.showMessageDialog(this, "Analysis cannot be performed on Opening Balance");
         return;
         
         } else if(cvalue.toString().trim().equalsIgnoreCase("0002x")){
         
         JOptionPane.showMessageDialog(this, "Analysis cannot be performed on closing balance");
           return;
         } else{
       
         statementAnalysisPanel.setVisible(true);
    statmentMainTablePanel.setVisible(false);
       statement.createStatementStAnalysis(jTable3StatementAnalaysis, cvalue.toString().trim());
       this.renderTheTables(jTable3StatementAnalaysis, 6);
        
           }}
        }else if(fios.stringFileReader(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt")).equalsIgnoreCase("customerStatementAmnt")){
             int selectedRow =jTable1.getSelectedRow();
     
                    int selectedColumn =jTable1.getSelectedColumn();
                    
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	
         Object cvalue = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 6);
         
         if(cvalue.toString().trim().equalsIgnoreCase("0001x")){
         JOptionPane.showMessageDialog(this, "Analysis cannot be performed on Opening Balance");
         return;
         
         } else if(cvalue.toString().trim().equalsIgnoreCase("0002x")){
         
         JOptionPane.showMessageDialog(this, "Analysis cannot be performed on closing balance");
           return;
         } else{
       
         statementAnalysisPanel.setVisible(true);
    statmentMainTablePanel.setVisible(false);
       statement.createStatementStAnalysis(jTable3StatementAnalaysis, cvalue.toString().trim());
       this.renderTheTables(jTable3StatementAnalaysis, 6);
        
           }}
        
        
        }else if(fios.stringFileReader(fios.createFileName("accountManagement", "accountName", "tableConsidered.txt")).equalsIgnoreCase("assetRegisterReport")){
        
           int selectedRow =jTable1.getSelectedRow();
     
                    int selectedColumn =jTable1.getSelectedColumn();
                    
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	
         Object cvalue = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 0);
       
         statementAnalysisPanel.setVisible(true);
         
    statmentMainTablePanel.setVisible(false);
    
       statement.createStatementDepSchedule(jTable3StatementAnalaysis, cvalue.toString().trim());
       
       this.renderTheTables(jTable3StatementAnalaysis, 7);
        
           }
        
        
        }
      
           
        }
    if(me.getSource()==jTable3StatementAnalaysis){
       statementAnalysisPanel.setVisible(false);
    statmentMainTablePanel.setVisible(true);
         

    }
    
    
    
    }
    
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
       if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
         
        }
    }

    private void renderTheTables(JTable theTable,int catRender){
    HeaderRenderer header = new HeaderRenderer(theTable.getTableHeader().getDefaultRenderer()); 
    
    switch(catRender){
    
        case 1:
          
        int h=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(h).setHeaderRenderer(header);

        if(h==0){
        theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(300);

        }
        if(h==1){
        theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(150);
        }
        if(h==2){
        theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(150);
        }if(h==3){
          theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(150);
        }
        if(h==4){
          theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(150);
        }
         if(h==5){
          theTable.getColumnModel().getColumn(h).setMinWidth(0);
        theTable.getColumnModel().getColumn(h).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(h).setPreferredWidth(200);
        }
        h++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
// this.setText(value.toString());
        
if(col>=1&&col<=3){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals(""))){
            
            if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        }
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
   
            break;
        case 2:
             int n=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        theTable.getColumnModel().getColumn(n).setMinWidth(0);
        theTable.getColumnModel().getColumn(n).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(n).setPreferredWidth(60);

        }
        if(n==1){
        theTable.getColumnModel().getColumn(n).setMinWidth(0);
        theTable.getColumnModel().getColumn(n).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(n).setPreferredWidth(110);
        }
        if(n==2){
        theTable.getColumnModel().getColumn(n).setMinWidth(0);
        theTable.getColumnModel().getColumn(n).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(n).setPreferredWidth(260);
        }
        n++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }

        if(col>=3&&col<=5){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
            
            break;
        case 3:
             int k=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k).setHeaderRenderer(header);

        if(k==0){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(60);

        }
        if(k==1){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(200);
        }
        if(k==2){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
         if(k==3){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
          if(k==4){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
           if(k==5){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
            if(k==6){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
             if(k==7){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
              if(k==8){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
               if(k==9){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
                if(k==10){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(150);
        }
                 if(k==11){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
        }
                  if(k==12){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
        }
                             if(k==13){
        theTable.getColumnModel().getColumn(k).setMinWidth(0);
        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
        }
////                                        if(k==14){
////        theTable.getColumnModel().getColumn(k).setMinWidth(0);
////        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
////        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
////        }
////                                                   if(k==15){
////        theTable.getColumnModel().getColumn(k).setMinWidth(0);
////        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
////        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
////        }
////                                                              if(k==16){
////        theTable.getColumnModel().getColumn(k).setMinWidth(0);
////        theTable.getColumnModel().getColumn(k).setMaxWidth(1500);
////        theTable.getColumnModel().getColumn(k).setPreferredWidth(130);
////        }
        k++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,13));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,12));
        }

        if(col>=2&&col<=13){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
   
            break;
        case 4:
            
        switch(rdb.getnumberOfBudgetPeriods()){   
            
        case 1:
              int ka=0;

        theTable.setShowHorizontalLines(true);
        theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(ka<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(ka).setHeaderRenderer(header);

        if(ka==0){
        theTable.getColumnModel().getColumn(ka).setMinWidth(0);
        theTable.getColumnModel().getColumn(ka).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(ka).setPreferredWidth(200);

        }
        if(ka==1){
        theTable.getColumnModel().getColumn(ka).setMinWidth(0);
        theTable.getColumnModel().getColumn(ka).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(ka).setPreferredWidth(140);
        } 
            if(ka==2){
        theTable.getColumnModel().getColumn(ka).setMinWidth(0);
        theTable.getColumnModel().getColumn(ka).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(ka).setPreferredWidth(140);
        } 
        
        ka++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,28));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
         
            
            
            break;
        case 2:
            
          int k1=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k1<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k1).setHeaderRenderer(header);

        if(k1==0){
        theTable.getColumnModel().getColumn(k1).setMinWidth(0);
        theTable.getColumnModel().getColumn(k1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k1).setPreferredWidth(200);

        }
        if(k1==1){
        theTable.getColumnModel().getColumn(k1).setMinWidth(0);
        theTable.getColumnModel().getColumn(k1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k1).setPreferredWidth(140);
        }
        if(k1==2){
        theTable.getColumnModel().getColumn(k1).setMinWidth(0);
        theTable.getColumnModel().getColumn(k1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k1).setPreferredWidth(140);
        }
              if(k1==3){
        theTable.getColumnModel().getColumn(k1).setMinWidth(0);
        theTable.getColumnModel().getColumn(k1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k1).setPreferredWidth(140);
        } 
        
        k1++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,19));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,17));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
             
            
            
            break;
        case 3:
          int k2=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k2<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k2).setHeaderRenderer(header);

        if(k2==0){
        theTable.getColumnModel().getColumn(k2).setMinWidth(0);
        theTable.getColumnModel().getColumn(k2).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k2).setPreferredWidth(200);

        }
        if(k2==1){
        theTable.getColumnModel().getColumn(k2).setMinWidth(0);
        theTable.getColumnModel().getColumn(k2).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k2).setPreferredWidth(140);
        }
        if(k2==2){
        theTable.getColumnModel().getColumn(k2).setMinWidth(0);
        theTable.getColumnModel().getColumn(k2).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k2).setPreferredWidth(140);
        }
        if(k2==3){
        theTable.getColumnModel().getColumn(k2).setMinWidth(0);
        theTable.getColumnModel().getColumn(k2).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k2).setPreferredWidth(140);
        }
          if(k2==3){
        theTable.getColumnModel().getColumn(k2).setMinWidth(0);
        theTable.getColumnModel().getColumn(k2).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k2).setPreferredWidth(140);
        } 
        k2++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,18));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,16));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
             
                 
            
            
            
            break;
        case 4:
          int k3=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k3<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k3).setHeaderRenderer(header);

        if(k3==0){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(200);

        }
        if(k3==1){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(140);
        }
        if(k3==2){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(140);
        }
        if(k3==3){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(140);
        }
         if(k3==4){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(140);
        }
        if(k3==5){
        theTable.getColumnModel().getColumn(k3).setMinWidth(0);
        theTable.getColumnModel().getColumn(k3).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k3).setPreferredWidth(140);
        }    
         
         
        k3++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,17));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,15));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
            
            
            
            break;     
          case 5:
              
      int k4=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k4<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k4).setHeaderRenderer(header);

        if(k4==0){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(200);

        }
        if(k4==1){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
        if(k4==2){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
        if(k4==3){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
         if(k4==4){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
           if(k4==5){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
           if(k4==6){
        theTable.getColumnModel().getColumn(k4).setMinWidth(0);
        theTable.getColumnModel().getColumn(k4).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k4).setPreferredWidth(140);
        }
         
        k4++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,16));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,14));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
                     
              
              
              
              
              break;
        case 6:
             int k5=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k5<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k5).setHeaderRenderer(header);

        if(k5==0){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(200);

        }
        if(k5==1){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
        if(k5==2){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
        if(k5==3){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
         if(k5==4){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
           if(k5==5){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
             if(k5==6){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
           if(k5==7){
        theTable.getColumnModel().getColumn(k5).setMinWidth(0);
        theTable.getColumnModel().getColumn(k5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k5).setPreferredWidth(140);
        }
        k5++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,15));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,14));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
             
            
            
            break;
        case 7:
         int k6=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k6<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k6).setHeaderRenderer(header);

        if(k6==0){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(200);

        }
        if(k6==1){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
        if(k6==2){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
        if(k6==3){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
         if(k6==4){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
           if(k6==5){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
             if(k6==6){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
           if(k6==7){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
              if(k6==7){
        theTable.getColumnModel().getColumn(k6).setMinWidth(0);
        theTable.getColumnModel().getColumn(k6).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k6).setPreferredWidth(140);
        }
        k6++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,15));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,13));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
             
            
            break;
        case 8:
        int k7=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k7<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k7).setHeaderRenderer(header);

        if(k7==0){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(200);

        }
        if(k7==1){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
        if(k7==2){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
        if(k7==3){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
         if(k7==4){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
           if(k7==5){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
             if(k7==6){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
           if(k7==7){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
           if(k7==8){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
              if(k7==9){
        theTable.getColumnModel().getColumn(k7).setMinWidth(0);
        theTable.getColumnModel().getColumn(k7).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k7).setPreferredWidth(140);
        }
        k7++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,14));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,13));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
              
            
            
            break;
        case 9:
            
       int k8=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k8<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k8).setHeaderRenderer(header);

        if(k8==0){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(200);

        }
        if(k8==1){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
        if(k8==2){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
        if(k8==3){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
         if(k8==4){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
           if(k8==5){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
             if(k8==6){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
           if(k8==7){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
           if(k8==8){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
           
              if(k8==9){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }
               if(k8==10){
        theTable.getColumnModel().getColumn(k8).setMinWidth(0);
        theTable.getColumnModel().getColumn(k8).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k8).setPreferredWidth(140);
        }      
              
              
        k8++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,14));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,13));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
              
                  
            
            break; 
        case 10:
            
       int k9=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k9<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k9).setHeaderRenderer(header);

        if(k9==0){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(200);

        }
        if(k9==1){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
        if(k9==2){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
        if(k9==3){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
         if(k9==4){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
           if(k9==5){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
             if(k9==6){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
           if(k9==7){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
           if(k9==8){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
           
              if(k9==9){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
              
                  if(k9==10){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }
                 if(k9==11){
        theTable.getColumnModel().getColumn(k9).setMinWidth(0);
        theTable.getColumnModel().getColumn(k9).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k9).setPreferredWidth(140);
        }          
                  
        k9++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,13));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,12));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
                
              
            
            
            
            
            break;
        case 11:
           
       int k10=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k10<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k10).setHeaderRenderer(header);

        if(k10==0){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(200);

        }
        if(k10==1){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
        if(k10==2){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
        if(k10==3){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
         if(k10==4){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
           if(k10==5){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
             if(k10==6){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
           if(k10==7){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
           if(k10==8){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
           
              if(k10==9){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
              
                  if(k10==10){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
                  
                      if(k10==11){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }
                          if(k10==12){
        theTable.getColumnModel().getColumn(k10).setMinWidth(0);
        theTable.getColumnModel().getColumn(k10).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k10).setPreferredWidth(140);
        }              
                      
                      
        k10++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,13));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,12));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
              
            
            
            break;
        case 12:
             int k11=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(k11<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(k11).setHeaderRenderer(header);

        if(k11==0){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(200);

        }
        if(k11==1){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
        if(k11==2){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
        if(k11==3){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
         if(k11==4){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
           if(k11==5){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
             if(k11==6){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
           if(k11==7){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
           if(k11==8){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
           
              if(k11==9){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
              
                  if(k11==10){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
                  
                      if(k11==11){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
                      
                               if(k11==12){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }
                                  if(k11==13){
        theTable.getColumnModel().getColumn(k11).setMinWidth(0);
        theTable.getColumnModel().getColumn(k11).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(k11).setPreferredWidth(140);
        }                        
                               
        k11++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,12));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,11));
        }

        if(col>=1){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
              
             
            
            break;  
            
            
            
            } 
            break;
        case 5:
   
             int kc=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(kc<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(kc).setHeaderRenderer(header);

        if(kc==0){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(60);

        }
        if(kc==1){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(200);
        }
        if(kc==2){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(200);
        }
         if(kc==3){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(100);
        }
          if(kc==4){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(100);
        }
           if(kc==5){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(60);
        }
            if(kc==6){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(130);
        }
             if(kc==7){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(120);
        }
              if(kc==8){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(120);
        }
               if(kc==9){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(120);
        }
                if(kc==10){
        theTable.getColumnModel().getColumn(kc).setMinWidth(0);
        theTable.getColumnModel().getColumn(kc).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kc).setPreferredWidth(100);
        }
         
        kc++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,15));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,14));
        }

        if(col>=7&&col<=9){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        }); 
            
 
            break;
        case 6:
            
//      HeaderRenderer header = new HeaderRenderer(jTable3StatementAnalaysis.getTableHeader().getDefaultRenderer());
sortTable(theTable,jTextField1);   

        int h1=0;


        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(500);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(100);

        }
        if(h1==1){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(500);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(100);
        }
        if(h1==2){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(600);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(300);
        }if(h1==6){
        theTable.getColumnModel().getColumn(h1).setMinWidth(4);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(4);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(4);
        }
        h1++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
// this.setText(value.toString());
        
if(col>=3&&col<=4){
         String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals(""))){
            
            if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        }
        this.setText(text);
        
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton8.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });        
            
            
            
            break;
        case 7:
           int kcz=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(kcz<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(kcz).setHeaderRenderer(header);

        if(kcz==0){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(60);

        }
        if(kcz==1){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(200);
        }
        if(kcz==2){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(200);
        }
         if(kcz==3){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(100);
        }
          if(kcz==4){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(100);
        }
           if(kcz==5){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(60);
        }
            if(kcz==6){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(130);
        }
             if(kcz==7){
        theTable.getColumnModel().getColumn(kcz).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz).setPreferredWidth(120);
        }
            
         
        kcz++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,15));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,14));
        }

        if(col>=3&&col<=6){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });  
            
            break;
        case 8:
   TableHeaderRender header1 = new TableHeaderRender(theTable);            
          int kcz1=0;

theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(kcz1<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(kcz1).setHeaderRenderer(header1);

        if(kcz1==0){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);

        }
        if(kcz1==1){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
        if(kcz1==2){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(60);
        }
         if(kcz1==3){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
          if(kcz1==4){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
           if(kcz1==5){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
            if(kcz1==6){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(60);
        }
             if(kcz1==7){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
          if(kcz1==8){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
          if(kcz1==9){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
           if(kcz1==10){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
            if(kcz1==11){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
             if(kcz1==12){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }   
           if(kcz1==13){
        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
        }
//          if(kcz1==14){
//        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
//        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
//        }
//           if(kcz1==15){
//        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
//        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
//        }
//            if(kcz1==16){
//        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
//        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
//        }
//             if(kcz1==17){
//        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
//        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(100);
//        } 
//               if(kcz1==17){
//        theTable.getColumnModel().getColumn(kcz1).setMinWidth(0);
//        theTable.getColumnModel().getColumn(kcz1).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(kcz1).setPreferredWidth(120);
//        } 
        kcz1++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,16));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,15));
        }

        if((col>=3&&col<=5)||(col>=7&&col<=12)){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
        this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            break;
        case 9:
            
//            HeaderRenderer headerv = new HeaderRenderer(theTable.getTableHeader().getDefaultRenderer()); 
          int hckxfy=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(true);
        while(hckxfy<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy).setHeaderRenderer(header);

        if(hckxfy==0){
        theTable.getColumnModel().getColumn(hckxfy).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy).setPreferredWidth(300);

        }
        if(hckxfy==1){
        theTable.getColumnModel().getColumn(hckxfy).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy).setPreferredWidth(40);
        }
        if(hckxfy==2){
        theTable.getColumnModel().getColumn(hckxfy).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy).setPreferredWidth(300);
        }if(hckxfy==3){
        theTable.getColumnModel().getColumn(hckxfy).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy).setPreferredWidth(40);
        }
        
        
        hckxfy++;

        }
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
// this.setText(value.toString());
        
if(col==1||col==3){
            String text="";
//          JOptionPane.showMessageDialog(this, value.toString());
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A")||value.toString().equals("COMING SOON"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }

else

{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            
            
            
            break; 
        case 10:
            
        int hckxfy5=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxfy5<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy5).setHeaderRenderer(header);

        if(hckxfy5==0){
        theTable.getColumnModel().getColumn(hckxfy5).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy5).setPreferredWidth(250);

        }
        
        
        if(hckxfy5==1){
        theTable.getColumnModel().getColumn(hckxfy5).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy5).setPreferredWidth(40);
        }
        if(hckxfy5==2){
        theTable.getColumnModel().getColumn(hckxfy5).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy5).setPreferredWidth(250);
        }if(hckxfy5==3){
        theTable.getColumnModel().getColumn(hckxfy5).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy5).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy5).setPreferredWidth(40);
        }
        
        
        hckxfy5++;

        }
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
        
//if(col==1&&col==3){
//            String text="";
//       
//        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
//            
//            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
//            
//             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
//            }else{
//        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
//        }
//        }
//        this.setText(text);
//       this.setHorizontalAlignment(RIGHT);
//        }else{
        this.setText(value.toString());
//        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            
                   
            
            
            
            break;
            
            
        case 11:
            
            int hckxfy54=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxfy54<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy54).setHeaderRenderer(header);

        if(hckxfy54==0){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(50);

        }
        
        
        if(hckxfy54==1){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(140);
        }
        if(hckxfy54==2){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(150);
        }if(hckxfy54==3){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(140);
        
         }if(hckxfy54==4){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(140);
        
          }if(hckxfy54==5){
        theTable.getColumnModel().getColumn(hckxfy54).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54).setPreferredWidth(140);
        }
        
        hckxfy54++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
        
//if(col==1&&col==3){
//            String text="";
//       
//        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
//            
//            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
//            
//             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
//            }else{
//        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
//        }
//        }
//        this.setText(text);
//       this.setHorizontalAlignment(RIGHT);
//        }else{
        this.setText(value.toString());
//        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            
                   
            
            
            
            break;
            
        case 12:
              
            int hckxfy54x=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxfy54x<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy54x).setHeaderRenderer(header);

        if(hckxfy54x==0){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(50);

        }
        
        
        if(hckxfy54x==1){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(340);
        }
        if(hckxfy54x==2){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(150);
        }if(hckxfy54x==3){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(150);
        
         }if(hckxfy54x==4){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(140);
        
          }if(hckxfy54x==5){
        theTable.getColumnModel().getColumn(hckxfy54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x).setPreferredWidth(80);
        
          }
        
        hckxfy54x++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String tx="";
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
      
if(col==5){
tx=value.toString();
}       
 
 
if(col>=2&&col<=4){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
          if(tx.equalsIgnoreCase("-ve")){
            
            setBackground(Color.RED);
        setForeground(Color.WHITE);
        }
        if(isSelected){setBackground(Color.CYAN);}
      
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
         if(tx.equalsIgnoreCase("-ve")){
               setBackground(Color.RED);
           setForeground(Color.WHITE);
           }
        if(isSelected){setBackground(Color.CYAN);}
          
        }  


        return this;
        }   
        });
            
                   
            
           
            
            
            
            break;
        case 13:
            
              int hckxfy54x1=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxfy54x1<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy54x1).setHeaderRenderer(header);

        if(hckxfy54x1==0){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(50);

        }
        
        
        if(hckxfy54x1==1){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(240);
        }
        if(hckxfy54x1==2){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(150);
        }if(hckxfy54x1==3){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(150);
        
         }if(hckxfy54x1==4){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(140);
        
          }if(hckxfy54x1==5){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(140);
        
          }if(hckxfy54x1==6){
        theTable.getColumnModel().getColumn(hckxfy54x1).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54x1).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54x1).setPreferredWidth(150);
        
          }
        
        hckxfy54x1++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String tx="";
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
      
if(col==5){
tx=value.toString();
}       
 
 
if(col>=2&&col<=6){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
//          if(tx.equalsIgnoreCase("-ve")){
//            
//            setBackground(Color.RED);
//        setForeground(Color.WHITE);
//        }
        if(isSelected){setBackground(Color.CYAN);}
      
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
//         if(tx.equalsIgnoreCase("-ve")){
//               setBackground(Color.RED);
//           setForeground(Color.WHITE);
//           }
        if(isSelected){setBackground(Color.CYAN);}
          
        }  


        return this;
        }   
        });
            
          
            
            
            break;
          case 14:
                 int hckxfy54xs=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxfy54xs<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hckxfy54xs).setHeaderRenderer(header);

        if(hckxfy54xs==0){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(50);

        }
        
        
        if(hckxfy54xs==1){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(370);
        }
        if(hckxfy54xs==2){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(140);
        }if(hckxfy54xs==3){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(140);
        
         }if(hckxfy54xs==4){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(140);
        
          }if(hckxfy54xs==5){
        theTable.getColumnModel().getColumn(hckxfy54xs).setMinWidth(0);
        theTable.getColumnModel().getColumn(hckxfy54xs).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hckxfy54xs).setPreferredWidth(200);
        
          }
        
        hckxfy54xs++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String tx="";
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
      
if(col==5){
tx=value.toString();
}       
 
 
if(col>=2&&col<=4){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
//          if(tx.equalsIgnoreCase("-ve")){
//            
//            setBackground(Color.RED);
//        setForeground(Color.WHITE);
//        }
        if(isSelected){setBackground(Color.CYAN);}
      
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
//         if(tx.equalsIgnoreCase("-ve")){
//               setBackground(Color.RED);
//           setForeground(Color.WHITE);
//           }
        if(isSelected){setBackground(Color.CYAN);}
          
        }  


        return this;
        }   
        });
            
                   
            
              
              break;      
           case 15:
               
                 int hb=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hb<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(hb).setHeaderRenderer(header);

        if(hb==0){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(50);

        }
        
        
        if(hb==1){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(100);
        }
        if(hb==2){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        }if(hb==3){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        
         }if(hb==4){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        
          }if(hb==5){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        
          }if(hb==6){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        
          }if(hb==7){
        theTable.getColumnModel().getColumn(hb).setMinWidth(0);
        theTable.getColumnModel().getColumn(hb).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(hb).setPreferredWidth(140);
        
          }
        
        hb++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String tx="";
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,16));
        
 this.setText(value.toString());
      
if(col==5){
tx=value.toString();
}       
 
 
if(col>=2&&col<=7){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
//          if(tx.equalsIgnoreCase("-ve")){
//            
//            setBackground(Color.RED);
//        setForeground(Color.WHITE);
//        }
        if(isSelected){setBackground(Color.CYAN);}
      
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
//         if(tx.equalsIgnoreCase("-ve")){
//               setBackground(Color.RED);
//           setForeground(Color.WHITE);
//           }
        if(isSelected){setBackground(Color.CYAN);}
          
        }  


        return this;
        }   
        });
            
            
               break;  
               
                case 16:
              
            int x54x=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
theTable.setShowHorizontalLines(true);
theTable.setShowGrid(true);
theTable.setRowHeight(25);
        theTable.getColumnModel().getColumns().nextElement().setResizable(false);
        while(x54x<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(x54x).setHeaderRenderer(header);

//        if(x54x==0){
//        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
//        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
//        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(50);
//
//        }
        
        
        if(x54x==0){
        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(340);
        }
        if(x54x==1){
        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(150);
        }if(x54x==2){
        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(150);
        
         }if(x54x==3){
        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(140);
        
          }if(x54x==4){
        theTable.getColumnModel().getColumn(x54x).setMinWidth(0);
        theTable.getColumnModel().getColumn(x54x).setMaxWidth(1500);
        theTable.getColumnModel().getColumn(x54x).setPreferredWidth(80);
        
          }
        
        x54x++;
        }
        
   sortTable(theTable,jTextField1);  
  

       theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String tx="";
          
        this.setHorizontalAlignment(JLabel.LEFT);


     
        this.setFont(new Font("Arial",Font.PLAIN,15));
        
 this.setText(value.toString());
      
if(col==4){
tx=value.toString();
}       
 
 
if(col>=1&&col<=3){
            String text="";
       
        if(!(value.toString().equals("-")||value.toString().equals("")||value.toString().equals("N/A"))){
            
            if(parseDouble(value.toString().replaceAll(",", "").replaceAll(";", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        }
        }
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton5.getBackground());
        setForeground(jButton7.getBackground());
          if(tx.equalsIgnoreCase("-ve")){
            
            setBackground(Color.RED);
        setForeground(Color.WHITE);
        }
        if(isSelected){setBackground(Color.CYAN);}
      
        } else {
        setBackground(jButton6.getBackground());
        setForeground(jButton7.getBackground());
         if(tx.equalsIgnoreCase("-ve")){
               setBackground(Color.RED);
           setForeground(Color.WHITE);
           }
        if(isSelected){setBackground(Color.CYAN);}
          
        }  


        return this;
        }   
        });
            
                   
            
           
            
            
            
            break;
  
                
    
    }
    
    }
    
    
    
    
    
    }
  
