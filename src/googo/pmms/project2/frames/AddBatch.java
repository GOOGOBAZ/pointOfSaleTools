/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frames;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author STAT SOLUTIONS
 */
public class AddBatch extends javax.swing.JFrame implements MouseListener, IUpdateText, ActionListener{
String userId;
//JFrame reflection;
//    List amortDetails;
//    DecimalFormat NumberFormat =new DecimalFormat("#,###");
//        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
//    Formartter ffm = new Formartter();
//    Date Trndate,valuedate;
//   ArrayList<String> data4, column1;
//List data5;
//    String text,position;
// Integer Value,Value1;
//    GregorianCalendar cal = new GregorianCalendar(); 
//    
//         BatchPostingHelper bPost=new BatchPostingHelper(); 
//         
//     JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries dbq =new DatabaseQuaries();
//    loanDatabaseQuaries loan=new loanDatabaseQuaries();
//    Formartter form= new Formartter();
//    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
//   ReportsDatabase report =new ReportsDatabase();
//    BalanceSheet bsheet= new BalanceSheet();
//    PostingMain post= new PostingMain();
//    BackUpRestoreDB dbBackUp= new BackUpRestoreDB();
//    CreatingFolders filesW= new CreatingFolders();
//   private PostingEntryWindow.ProcessLoanRepayment laonPay;
//  String completeStatus="Not Complete",flag="Not Flagged"; int temper=0;
//    String batchNumber;
    OtherLoanReports otherLoans=new  OtherLoanReports();
//     LoanSavingsSharesOthers loanSaveShare=new LoanSavingsSharesOthers();
//         ListDataModel model1;
         ListDataModel result;
//         String theAccount="";
//         
////     OtherLoanReports otherLoans=new  OtherLoanReports();
//     
//    List loanPaymentOrder;
//    SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
//  
//        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
//    
// int n=0;
//   
//          JOptionPane p;
//    PostingModal model ;
//    JFrame fi, f,fa,fb,fc;
//    Date date;
//  SimpleDateFormat df;
     Formartter fmt= new Formartter();
// AccountNumberCreationDataBase db =new AccountNumberCreationDataBase();

    public AddBatch(String userId) {
        
         
          
        initComponents();
 this.userId=userId;
  jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
jTable1.setRowHeight(30);
jTable1.setGridColor(Color.gray);

     jTable1.addMouseListener( this);
        Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
       this.setTitle("ADD NEW ITEM"); 
//        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton51 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jButton67 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jButton72 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1013, 547));
        setMinimumSize(new java.awt.Dimension(1013, 547));
        setPreferredSize(new java.awt.Dimension(1013, 547));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 153), new java.awt.Color(0, 204, 153)));
        jPanel1.setLayout(null);

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoscrolls(false);
        jTable1.setDropMode(javax.swing.DropMode.ON);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 980, 440);

        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jTextField1);
        jTextField1.setBounds(580, 460, 350, 40);

        jLabel1.setText("SEARCH HERE");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel1);
        jLabel1.setBounds(420, 460, 150, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1000, 550);

        jButton51.setBackground(new java.awt.Color(0, 51, 51));
        jButton51.setText("jButton5");
        getContentPane().add(jButton51);
        jButton51.setBounds(1800, 360, 73, 23);

        jButton50.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton50.setText("jButton5");
        getContentPane().add(jButton50);
        jButton50.setBounds(1790, 330, 73, 23);

        jButton49.setBackground(new java.awt.Color(255, 204, 204));
        jButton49.setText("jButton5");
        getContentPane().add(jButton49);
        jButton49.setBounds(1870, 320, 73, 23);

        jButton31.setBackground(new java.awt.Color(255, 204, 153));
        jButton31.setText("Blue");
        getContentPane().add(jButton31);
        jButton31.setBounds(1810, 310, 53, 23);

        jButton38.setBackground(new java.awt.Color(204, 204, 0));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(1810, 290, 53, 23);

        jButton32.setBackground(new java.awt.Color(0, 153, 153));
        jButton32.setText("Blue");
        getContentPane().add(jButton32);
        jButton32.setBounds(1870, 290, 53, 23);

        jButton47.setBackground(new java.awt.Color(204, 255, 204));
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1930, 290, 53, 23);

        jButton29.setBackground(new java.awt.Color(0, 204, 204));
        jButton29.setText("Blue");
        getContentPane().add(jButton29);
        jButton29.setBounds(1920, 270, 53, 23);

        jButton37.setBackground(new java.awt.Color(204, 153, 255));
        jButton37.setText("Blue");
        getContentPane().add(jButton37);
        jButton37.setBounds(1870, 270, 53, 23);

        jButton33.setBackground(new java.awt.Color(152, 198, 94));
        jButton33.setText("Blue");
        getContentPane().add(jButton33);
        jButton33.setBounds(1810, 270, 53, 23);

        jButton35.setBackground(new java.awt.Color(255, 204, 204));
        jButton35.setText("Blue");
        getContentPane().add(jButton35);
        jButton35.setBounds(1810, 250, 53, 23);

        jButton45.setBackground(new java.awt.Color(255, 255, 204));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1870, 250, 53, 23);

        jButton42.setBackground(new java.awt.Color(0, 204, 102));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(1920, 230, 53, 23);

        jButton43.setBackground(new java.awt.Color(204, 204, 204));
        jButton43.setText("Blue");
        getContentPane().add(jButton43);
        jButton43.setBounds(1870, 230, 53, 23);

        jButton30.setBackground(new java.awt.Color(0, 153, 255));
        jButton30.setText("Blue");
        getContentPane().add(jButton30);
        jButton30.setBounds(1810, 230, 53, 23);

        jButton36.setBackground(java.awt.SystemColor.activeCaption);
        jButton36.setText("Blue");
        getContentPane().add(jButton36);
        jButton36.setBounds(1810, 210, 53, 23);

        jButton44.setBackground(new java.awt.Color(204, 165, 165));
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(1870, 210, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 204, 255));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(1920, 210, 53, 23);

        jButton41.setBackground(java.awt.SystemColor.activeCaption);
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(1920, 190, 53, 23);

        jButton46.setBackground(java.awt.SystemColor.activeCaption);
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1870, 190, 53, 23);

        jButton40.setBackground(java.awt.SystemColor.activeCaption);
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(1810, 190, 53, 23);

        jButton48.setBackground(new java.awt.Color(201, 222, 223));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1870, 170, 53, 23);

        jButton52.setBackground(new java.awt.Color(0, 51, 51));
        jButton52.setText("jButton5");
        getContentPane().add(jButton52);
        jButton52.setBounds(1800, 360, 73, 23);

        jButton53.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton53.setText("jButton5");
        getContentPane().add(jButton53);
        jButton53.setBounds(1790, 330, 73, 23);

        jButton54.setBackground(new java.awt.Color(255, 204, 204));
        jButton54.setText("jButton5");
        getContentPane().add(jButton54);
        jButton54.setBounds(1870, 320, 73, 23);

        jButton34.setBackground(new java.awt.Color(255, 204, 153));
        jButton34.setText("Blue");
        getContentPane().add(jButton34);
        jButton34.setBounds(1810, 310, 53, 23);

        jButton55.setBackground(new java.awt.Color(204, 204, 0));
        jButton55.setText("Blue");
        getContentPane().add(jButton55);
        jButton55.setBounds(1810, 290, 53, 23);

        jButton56.setBackground(new java.awt.Color(0, 153, 153));
        jButton56.setText("Blue");
        getContentPane().add(jButton56);
        jButton56.setBounds(1870, 290, 53, 23);

        jButton57.setBackground(new java.awt.Color(204, 255, 204));
        jButton57.setText("Blue");
        getContentPane().add(jButton57);
        jButton57.setBounds(1930, 290, 53, 23);

        jButton58.setBackground(new java.awt.Color(0, 204, 204));
        jButton58.setText("Blue");
        getContentPane().add(jButton58);
        jButton58.setBounds(1920, 270, 53, 23);

        jButton59.setBackground(new java.awt.Color(204, 153, 255));
        jButton59.setText("Blue");
        getContentPane().add(jButton59);
        jButton59.setBounds(1870, 270, 53, 23);

        jButton60.setBackground(new java.awt.Color(152, 198, 94));
        jButton60.setText("Blue");
        getContentPane().add(jButton60);
        jButton60.setBounds(1810, 270, 53, 23);

        jButton61.setBackground(new java.awt.Color(255, 204, 204));
        jButton61.setText("Blue");
        getContentPane().add(jButton61);
        jButton61.setBounds(1810, 250, 53, 23);

        jButton62.setBackground(new java.awt.Color(255, 255, 204));
        jButton62.setText("Blue");
        getContentPane().add(jButton62);
        jButton62.setBounds(1870, 250, 53, 23);

        jButton63.setBackground(new java.awt.Color(0, 204, 102));
        jButton63.setText("Blue");
        getContentPane().add(jButton63);
        jButton63.setBounds(1920, 230, 53, 23);

        jButton64.setBackground(new java.awt.Color(204, 204, 204));
        jButton64.setText("Blue");
        getContentPane().add(jButton64);
        jButton64.setBounds(1870, 230, 53, 23);

        jButton65.setBackground(new java.awt.Color(0, 153, 255));
        jButton65.setText("Blue");
        getContentPane().add(jButton65);
        jButton65.setBounds(1810, 230, 53, 23);

        jButton66.setBackground(java.awt.SystemColor.activeCaption);
        jButton66.setText("Blue");
        getContentPane().add(jButton66);
        jButton66.setBounds(1810, 210, 53, 23);

        jButton67.setBackground(new java.awt.Color(204, 165, 165));
        jButton67.setText("Blue");
        getContentPane().add(jButton67);
        jButton67.setBounds(1870, 210, 53, 23);

        jButton68.setBackground(new java.awt.Color(204, 204, 255));
        jButton68.setText("Blue");
        getContentPane().add(jButton68);
        jButton68.setBounds(1920, 210, 53, 23);

        jButton69.setBackground(java.awt.SystemColor.activeCaption);
        jButton69.setText("Blue");
        getContentPane().add(jButton69);
        jButton69.setBounds(1920, 190, 53, 23);

        jButton70.setBackground(java.awt.SystemColor.activeCaption);
        jButton70.setText("Blue");
        getContentPane().add(jButton70);
        jButton70.setBounds(1870, 190, 53, 23);

        jButton71.setBackground(java.awt.SystemColor.activeCaption);
        jButton71.setText("Blue");
        getContentPane().add(jButton71);
        jButton71.setBounds(1810, 190, 53, 23);

        jButton72.setBackground(new java.awt.Color(201, 222, 223));
        jButton72.setText("Blue");
        getContentPane().add(jButton72);
        jButton72.setBounds(1870, 170, 53, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
  
        switch(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", userId+"batchPosintType.txt"))){
       case"Loan":
     
    result=  otherLoans.createBatchPostingLoansSpecial(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", userId+"batchPosintNo.txt")));
//            createBatchPostingLoansSpecial(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", userId+"batchPosintNo.txt")));
 
    jTable1.setModel(result);
    
    TableRowSorter<ListDataModel> sorter = new TableRowSorter<>(result);

        jTable1.setRowSorter(sorter); 
   

   HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());
        int h=0;

        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(h).setHeaderRenderer(header);

        if(h==0){
        jTable1.getColumnModel().getColumn(h).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(50);

        }
        if(h==1){
        jTable1.getColumnModel().getColumn(h).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(100);
        }
        if(h==2){
        jTable1.getColumnModel().getColumn(h).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(180);
        }if(h==3){
        jTable1.getColumnModel().getColumn(h).setMinWidth(4);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(120);
     }if(h==4){
        jTable1.getColumnModel().getColumn(h).setMinWidth(4);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(110);
        }if(h==5){
        jTable1.getColumnModel().getColumn(h).setMinWidth(4);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(110);
        }if(h==6){
        jTable1.getColumnModel().getColumn(h).setMinWidth(6);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(110);
        }if(h==7){
        jTable1.getColumnModel().getColumn(h).setMinWidth(7);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(110);
        }if(h==8){
        jTable1.getColumnModel().getColumn(h).setMinWidth(8);
        jTable1.getColumnModel().getColumn(h).setMaxWidth(1000);
        jTable1.getColumnModel().getColumn(h).setPreferredWidth(110);
        }
        h++;

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
// this.setText(value.toString());
        
//if(col>=4&&col<=7){
//         String text="";
//           this.setHorizontalAlignment(RIGHT);
//        if(!(value.toString().equals("-")||value.toString().equals(""))){
//            
//            if(parseDouble(value.toString().replaceAll(",", ""))<0){
//            
//             text = "("+ffm.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
//            }else{
//        text = ffm.formatForStatementNumbers(value.toString().replaceAll(",", "") );
//        }
//        }
//        this.setText(text);
//        
//        }else{
        this.setText(value.toString());
//        }
        if (row%2==0) {
        setBackground(jButton49.getBackground());
        setForeground(jButton51.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton50.getBackground());
        setForeground(jButton51.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
          break; 
//    case "Savings":
//    
//        jTable1.setModel( otherLoans.createAllOutstandingSavingsPaymentsSpecialxc(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", userId+"batchPosintNo.txt"))));
//
//        TableRowSorter<ListDataModel> sorters = new TableRowSorter<>(result);
//
//        jTable1.setRowSorter(sorters); 
//sortTable(jTable1,jTextField1);   
//   HeaderRenderer headers = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());
//        int hs=0;
//
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
//        while(hs<jTable1.getColumnModel().getColumnCount()){
//        jTable1.getColumnModel().getColumn(hs).setHeaderRenderer(headers);
//
//        if(hs==0){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(50);
//
//        }
//        if(hs==1){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(100);
//        }
//        if(hs==2){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(180);
//        }if(hs==3){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(120);
//     }if(hs==4){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(110);
//        }if(hs==5){
//        jTable1.getColumnModel().getColumn(hs).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hs).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hs).setPreferredWidth(110);
//        }
//        hs++;
//
//        }
//        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//
//        this.setHorizontalAlignment(JLabel.LEFT);
//
//
//        if(row==table.getRowCount()-1){
//        this.setFont(new Font("Arial",Font.BOLD,20));
//        }else{
//        this.setFont(new Font("Arial",Font.PLAIN,18));
//        }
//// this.setText(value.toString());
//        
//if(col>=4&&col<=5){
//         String text="";
//           this.setHorizontalAlignment(RIGHT);
//        if(!(value.toString().equals("-")||value.toString().equals(""))){
//            
//            if(parseDouble(value.toString().replaceAll(",", ""))<0){
//            
//             text = "("+ffm.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
//            }else{
//        text = ffm.formatForStatementNumbers(value.toString().replaceAll(",", "") );
//        }
//        }
//        this.setText(text);
//        
//        }else{
//        this.setText(value.toString());
//        }
//        if (row%2==0) {
//        setBackground(jButton50.getBackground());
//        setForeground(jButton49.getBackground());
//        if(isSelected){setBackground(Color.CYAN);}
//        } else {
//        setBackground(jButton51.getBackground());
//        setForeground(jButton49.getBackground());
//        if(isSelected){setBackground(Color.CYAN);}
//        }  
//
//
//        return this;
//        }   
//        });
//        
//        
//        break;
//     case "Shares":
//       
//        jTable1.setModel( otherLoans.createAllOutstandingSharesPaymentsSpecialxc(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", userId+"batchPosintNo.txt"))));
//
//        TableRowSorter<ListDataModel> sorterss = new TableRowSorter<>(result);
//
//        jTable1.setRowSorter(sorterss); 
//sortTable(jTable1,jTextField1);   
//   HeaderRenderer headerss = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());
//        int hss=0;
//
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
//        while(hss<jTable1.getColumnModel().getColumnCount()){
//        jTable1.getColumnModel().getColumn(hss).setHeaderRenderer(headerss);
//
//        if(hss==0){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(50);
//
//        }
//        if(hss==1){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(100);
//        }
//        if(hss==2){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(180);
//        }if(hss==3){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(120);
//     }if(hss==4){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(110);
//        }if(hss==5){
//        jTable1.getColumnModel().getColumn(hss).setMinWidth(4);
//        jTable1.getColumnModel().getColumn(hss).setMaxWidth(1000);
//        jTable1.getColumnModel().getColumn(hss).setPreferredWidth(110);
//        }
//        hss++;
//
//        }
//        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//
//        this.setHorizontalAlignment(JLabel.LEFT);
//
//
//        if(row==table.getRowCount()-1){
//        this.setFont(new Font("Arial",Font.BOLD,20));
//        }else{
//        this.setFont(new Font("Arial",Font.PLAIN,18));
//        }
//// this.setText(value.toString());
//        
//if(col>=4&&col<=5){
//         String text="";
//           this.setHorizontalAlignment(RIGHT);
//        if(!(value.toString().equals("-")||value.toString().equals(""))){
//            
//            if(parseDouble(value.toString().replaceAll(",", ""))<0){
//            
//             text = "("+ffm.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
//            }else{
//        text = ffm.formatForStatementNumbers(value.toString().replaceAll(",", "") );
//        }
//        }
//        this.setText(text);
//        
//        }else{
//        this.setText(value.toString());
//        }
//        if (row%2==0) {
//        setBackground(jButton50.getBackground());
//        setForeground(jButton49.getBackground());
//        if(isSelected){setBackground(Color.CYAN);}
//        } else {
//        setBackground(jButton51.getBackground());
//        setForeground(jButton49.getBackground());
//        if(isSelected){setBackground(Color.CYAN);}
//        }  
//
//
//        return this;
//        }   
//        });
//        
//        
//         
//         
//         
//         
//         break;
           
   }
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
////        public static String u=AddBatch.this.userId;
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
//            java.util.logging.Logger.getLogger(AddBatch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AddBatch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AddBatch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AddBatch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AddBatch().setVisible(true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
      
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
    public void updateText(String text) {
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
}

