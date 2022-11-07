/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.smallFrames;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frames.Calender;
import googo.pmms.project2.frames.LoanManagmentWindow;
import googo.pmms.project2.frames.PostingEntryWindow;
import googo.pmms.project2.frames.mainPrinter;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import googo.pmms.project2.reportsHelper.OtherLoanReports;
import googo.pmms.project2.reportsHelper.ReportsPDFFormulars;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author STAT SOLUTIONS
 */
public class PrintReceiptAuto extends javax.swing.JFrame implements MouseListener, IUpdateText, ActionListener,KeyListener{
String userId;
 mainPrinter printReceipt=new mainPrinter();
//JFrame reflection;
//    List amortDetails;
//    DecimalFormat NumberFormat =new DecimalFormat("#,###");
//        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
//    Formartter ffm = new Formartter();
//    Date Trndate,valuedate;
    DecimalFormat NumberFormat =new DecimalFormat("#,###");
       ReportsPDFFormulars statement= new ReportsPDFFormulars(); 
//List data5;
//    String text,position;
// Integer Value,Value1;
//    GregorianCalendar cal = new GregorianCalendar(); 
//    
       String theBatchNumbeer,theBatchNumbeer1,accountNumber;
//         BatchPostingHelper bPost=new BatchPostingHelper(); 
//         
//     JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    DatabaseQuaries dbq =new DatabaseQuaries();
     List theCollectionReport;
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
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
//PostingEntryWindow component;
PostingEntryWindow component;

String cycleNumber;
List GroupIds;

    public PrintReceiptAuto(String userId,PostingEntryWindow comp) {
        
         
          
        initComponents();
        this.userId=userId;
        component=comp;
   
        Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
        this.setIconImage(img);
        this.setTitle("SUMMURY REPORT DETAILS"); 

    jTable7.addMouseListener(this);
    jTable6.addMouseListener(this);
 jTable6.setAlignmentX(LEFT_ALIGNMENT);
jTable6.setAlignmentY(CENTER_ALIGNMENT);
jTable6.setAutoscrolls(true);
jTable6.setShowHorizontalLines(true);
jTable6.setShowGrid(true);
jTable6.setRowHeight(30);
jTable6.setGridColor(Color.gray); 

   jTable7.setAlignmentX(LEFT_ALIGNMENT);
jTable7.setAlignmentY(CENTER_ALIGNMENT);
jTable7.setAutoscrolls(true);
jTable7.setShowHorizontalLines(true);
jTable7.setShowGrid(true);
jTable7.setRowHeight(30);
jTable7.setGridColor(Color.gray); 



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
        PrintLoanReceiptPanel = new javax.swing.JPanel();
        ReceiptDetails = new javax.swing.JPanel();
        creditAccountField72 = new javax.swing.JFormattedTextField();
        TransactionsDetails = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel102 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jTextField60 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jButton237 = new javax.swing.JButton();
        jButton236 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        customerDetails = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        printReceipController = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
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
        setAutoRequestFocus(false);
        setMaximumSize(new java.awt.Dimension(1290, 700));
        setMinimumSize(new java.awt.Dimension(1290, 700));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setPreferredSize(new java.awt.Dimension(1290, 700));
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

        PrintLoanReceiptPanel.setBackground(new java.awt.Color(0, 102, 102));
        PrintLoanReceiptPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PrintLoanReceiptPanel.setLayout(null);

        ReceiptDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        ReceiptDetails.setForeground(new java.awt.Color(255, 255, 255));
        ReceiptDetails.setLayout(null);

        creditAccountField72.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        creditAccountField72.setForeground(new java.awt.Color(0, 102, 51));
        try {
            creditAccountField72.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-#######-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        creditAccountField72.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        creditAccountField72.setValue(null);
        creditAccountField72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditAccountField72ActionPerformed(evt);
            }
        });
        creditAccountField72.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                creditAccountField72KeyPressed(evt);
            }
        });
        ReceiptDetails.add(creditAccountField72);
        creditAccountField72.setBounds(50, 10, 140, 40);
        creditAccountField72.addKeyListener(this);
        creditAccountField72.setVisible(false);
        creditAccountField72.setVisible(false);

        TransactionsDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        TransactionsDetails.setForeground(new java.awt.Color(255, 255, 255));
        TransactionsDetails.setLayout(null);

        jTable6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable6);

        TransactionsDetails.add(jScrollPane7);
        jScrollPane7.setBounds(10, 20, 1180, 370);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TransactionsDetails.add(jButton1);
        jButton1.setBounds(460, 430, 200, 40);
        jButton1.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("SEARCH HERE:");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TransactionsDetails.add(jLabel3);
        jLabel3.setBounds(670, 430, 110, 40);

        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        TransactionsDetails.add(jTextField2);
        jTextField2.setBounds(780, 430, 240, 40);

        ReceiptDetails.add(TransactionsDetails);
        TransactionsDetails.setBounds(10, 50, 1200, 520);
        TransactionsDetails.setVisible(false);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("SUBMIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        ReceiptDetails.add(jButton3);
        jButton3.setBounds(820, 10, 200, 40);
        jButton1.setEnabled(false);

        jLabel102.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel102.setText("START DATE");
        jLabel102.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ReceiptDetails.add(jLabel102);
        jLabel102.setBounds(200, 10, 100, 40);
        jLabel102.setVisible(false);

        jLabel101.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel101.setText("END DATE");
        jLabel101.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ReceiptDetails.add(jLabel101);
        jLabel101.setBounds(510, 10, 100, 40);
        jLabel101.setVisible(false);

        jTextField60.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ReceiptDetails.add(jTextField60);
        jTextField60.setBounds(300, 10, 180, 40);
        jTextField60.setVisible(false);

        jTextField59.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ReceiptDetails.add(jTextField59);
        jTextField59.setBounds(610, 10, 180, 40);
        jTextField59.setVisible(false);

        jButton237.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton237.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton237ActionPerformed(evt);
            }
        });
        ReceiptDetails.add(jButton237);
        jButton237.setBounds(480, 10, 30, 40);
        jButton237.setVisible(false);

        jButton236.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton236.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton236ActionPerformed(evt);
            }
        });
        ReceiptDetails.add(jButton236);
        jButton236.setBounds(790, 10, 30, 40);
        jButton236.setVisible(false);

        jButton73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton73.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jButton73HierarchyChanged(evt);
            }
        });
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });
        ReceiptDetails.add(jButton73);
        jButton73.setBounds(20, 10, 30, 40);
        jButton73.setVisible(false);

        PrintLoanReceiptPanel.add(ReceiptDetails);
        ReceiptDetails.setBounds(20, 20, 1220, 580);

        customerDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        customerDetails.setForeground(new java.awt.Color(255, 255, 255));
        customerDetails.setLayout(null);

        jTable7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(jTable7);

        customerDetails.add(jScrollPane8);
        jScrollPane8.setBounds(30, 10, 1170, 460);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("SEARCH HERE:");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerDetails.add(jLabel4);
        jLabel4.setBounds(890, 500, 110, 40);

        jTextField3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        customerDetails.add(jTextField3);
        jTextField3.setBounds(1000, 500, 200, 40);

        PrintLoanReceiptPanel.add(customerDetails);
        customerDetails.setBounds(10, 10, 1240, 620);
        customerDetails.setVisible(false);

        jPanel1.add(PrintLoanReceiptPanel);
        PrintLoanReceiptPanel.setBounds(20, 70, 1270, 640);
        PrintLoanReceiptPanel.setVisible(false);

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(null);
        jPanel1.add(jPanel4);
        jPanel4.setBounds(1290, 220, 10, 50);
        jPanel4.setVisible(false);

        printReceipController.setBackground(new java.awt.Color(0, 51, 51));
        printReceipController.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        printReceipController.setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receipt Type:", "Loan Payment Receipt", "Loan Disbursement Receipt", "Savings Withdraw Historical Receipt", "Savings Historical Receipt", "Mini Loan Statement", "Mini Savings Statement" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        printReceipController.add(jComboBox1);
        jComboBox1.setBounds(370, 10, 480, 40);

        jPanel1.add(printReceipController);
        printReceipController.setBounds(20, 10, 1270, 60);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);
        jPanel1.add(jPanel3);
        jPanel3.setBounds(1300, 130, 10, 60);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 0, 1350, 720);

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

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// this.component.initialiseSummuryR();
//  
// this.component.resetCounter();
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 
        
        if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Loan Payment Receipt")){
        
//             JOptionPane.showMessageDialog(this, theBatchNumbeer);
            List printingDetails=new ArrayList();
         printingDetails.add("Loan Payment Receipt"); // batchNumber
        printingDetails.add(theBatchNumbeer); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,this);
            
        }else if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Loan Disbursement Receipt")){
        
//         JOptionPane.showMessageDialog(c, batch);
      List printingDetails=new ArrayList();
         printingDetails.add("Loan Disbursement Receipt"); // batchNumber
        printingDetails.add(theBatchNumbeer); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,this);
        }else if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Savings Historical Receipt")){
        
//         JOptionPane.showMessageDialog(this, theBatchNumbeer);
         
      List printingDetails=new ArrayList();
         printingDetails.add("Savings Historical Receipt"); // batchNumber
        printingDetails.add(accountNumber); // batchNumber
         printingDetails.add(theBatchNumbeer); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,this);
        }else if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Savings Withdraw Historical Receipt")){
        
//         JOptionPane.showMessageDialog(c, batch);
      List printingDetails=new ArrayList();
         printingDetails.add("Savings Withdraw Historical Receipt"); // batchNumber
        printingDetails.add(accountNumber); // batchNumber
           printingDetails.add(theBatchNumbeer); // batchNumber
        printingDetails.add(this.userId);// account Number
        printReceipt.printTheReceipt(printingDetails,this);
        }
//       
    
     PrintReceiptAuto fv = new PrintReceiptAuto(this.userId,this.component);
        fv.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fv.setSize(screen.getSize());
        fv.pack();
        this.dispose();  
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton73HierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jButton73HierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton73HierarchyChanged

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed

        if(dbq.fillMeWithAllAccounts(jTable7)){
                ReceiptDetails.setVisible(false);

                customerDetails.setVisible(true);
            }else{

                JOptionPane.showMessageDialog(this, "No Accounts Found!!!!");
                return;}
            sortTable(jTable7,jTextField3);
                         HeaderRenderer header = new HeaderRenderer(jTable7.getTableHeader().getDefaultRenderer());

        int h1=0;

        //        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable7.getColumnModel().getColumnCount()){
            jTable7.getColumnModel().getColumn(h1).setHeaderRenderer(header);

            if(h1==0){
                jTable7.getColumnModel().getColumn(h1).setMinWidth(0);
                jTable7.getColumnModel().getColumn(h1).setMaxWidth(100);
                jTable7.getColumnModel().getColumn(h1).setPreferredWidth(80);

            }

            if(h1==1){
                jTable7.getColumnModel().getColumn(h1).setMinWidth(0);
                jTable7.getColumnModel().getColumn(h1).setMaxWidth(2000);
                jTable7.getColumnModel().getColumn(h1).setPreferredWidth(200);

            }
            
            if(h1==2){
                jTable7.getColumnModel().getColumn(h1).setMinWidth(0);
                jTable7.getColumnModel().getColumn(h1).setMaxWidth(2000);
                jTable7.getColumnModel().getColumn(h1).setPreferredWidth(150);

            }
            
            if(h1==3){
                jTable7.getColumnModel().getColumn(h1).setMinWidth(0);
                jTable7.getColumnModel().getColumn(h1).setMaxWidth(2000);
                jTable7.getColumnModel().getColumn(h1).setPreferredWidth(150);

            }
             if(h1==4){
                jTable7.getColumnModel().getColumn(h1).setMinWidth(0);
                jTable7.getColumnModel().getColumn(h1).setMaxWidth(2000);
                jTable7.getColumnModel().getColumn(h1).setPreferredWidth(150);

            }
            h1++;

        }
        jTable7.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                this.setHorizontalAlignment(JLabel.LEFT);

                if(row==table.getRowCount()-1){
                    this.setFont(new Font("Arial",Font.BOLD,20));
                }else{
                    this.setFont(new Font("Arial",Font.PLAIN,18));
                }

                if(col==4){
                    Number c = (Number)parseDouble(value.toString());
                    String text = NumberFormat.format(c );
                    this.setText(text);
                }else{
                    this.setText(value.toString());
                }
                if (row%2==0) {
                    setBackground(jButton49.getBackground());
                    setForeground(jButton51.getBackground());
                    if(isSelected){
                        
                        setBackground(Color.CYAN);
                    }
                } else {
                    setBackground(jButton50.getBackground());
                    setForeground(jButton51.getBackground());
                    if(isSelected){setBackground(Color.CYAN);}
                }

                return this;
            }
        });
  
    }//GEN-LAST:event_jButton73ActionPerformed

    private void creditAccountField72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditAccountField72ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditAccountField72ActionPerformed

    private void creditAccountField72KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_creditAccountField72KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditAccountField72KeyPressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed


        switch(jComboBox1.getSelectedItem().toString()){
            case "Loan Payment Receipt":
                
                 jButton73.setVisible(true);
//jLabel1.setVisible(true);
creditAccountField72.setVisible(true);
      PrintLoanReceiptPanel   .setVisible(true);       
                break;
            case "Loan Disbursement Receipt":
  jButton73.setVisible(true);
//jLabel1.setVisible(true);
creditAccountField72.setVisible(true);
      PrintLoanReceiptPanel   .setVisible(true);     
                
                break;
            case "Savings Historical Receipt":
                   jButton73.setVisible(true);
//jLabel1.setVisible(true);
creditAccountField72.setVisible(true);
      PrintLoanReceiptPanel   .setVisible(true);  
                break;
//            case "Shares Contribution Receipt":
//                  PrintLoanReceiptPanel   .setVisible(false); 
//                break;
            case "Savings Withdraw Historical Receipt":
                jButton73.setVisible(true);
//jLabel1.setVisible(true);
creditAccountField72.setVisible(true);
      PrintLoanReceiptPanel   .setVisible(true); 
                break;
//             case "Shares Withdraw Receipt":
//                   PrintLoanReceiptPanel   .setVisible(false); 
//                 break;
            case "Mini Loan Statement":
                
                  PrintLoanReceiptPanel.setVisible(false); 
                  
     SelectedAccountDetails theNames=new SelectedAccountDetails(this.userId,"FOrPrintintTheReceipt","hh");

        
       theNames.setVisible(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        theNames.setSize(screen.getSize());
        
        theNames.pack(); 
        this.dispose();
                break;
                
        case "Mini Savings Statement":
                
                  PrintLoanReceiptPanel .setVisible(false); 
                  
     SelectedAccountDetails theNamesx=new SelectedAccountDetails(this.userId,"FOrPrintintTheReceipt","hh");

        
       theNamesx.setVisible(true);
        
        Dimension screenx = Toolkit.getDefaultToolkit().getScreenSize();
        
        theNamesx.setSize(screenx.getSize());
        
        theNamesx.pack(); 
        this.dispose();
                break;         
                
                
        
        }
        
       
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        
        
        TransactionsDetails.setVisible(true);
        
        if(creditAccountField72.getValue()==null){JOptionPane.showMessageDialog(this, "Please Select a Valid Account Number"); return;}
        
        else if(dbq.testTable("bsanca"+fmt.formatAccountWithSeperators(creditAccountField72.getValue().toString()))==false)
        {JOptionPane.showMessageDialog(this, "Account  does not exist");
        return;
        }
        
        else if (jTextField60.getText().equalsIgnoreCase("")||jTextField59.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField60.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField59.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }else{
            
        


          List theOtherDetailsStatements= new ArrayList();
          
      theOtherDetailsStatements.add(fmt.formatAccountWithSeperators(creditAccountField72.getValue().toString()));
      
        theOtherDetailsStatements.add(jTextField60.getText());  
         theOtherDetailsStatements.add(jTextField59.getText().trim()); 
          theOtherDetailsStatements.add(creditAccountField72.getValue().toString()); 
//     
      statement.createStatementStXv(jTable6,theOtherDetailsStatements.get(0).toString(),fmt.forDatabaseWithFullYearBeginningWithDate(theOtherDetailsStatements.get(1).toString()),fmt.forDatabaseWithFullYearBeginningWithDate(theOtherDetailsStatements.get(2).toString()),this);

        sortTable(jTable6,jTextField2);   

        int h1=0;

jTable6.setShowHorizontalLines(true);
jTable6.setShowGrid(true);

      HeaderRenderer header = new HeaderRenderer(jTable6.getTableHeader().getDefaultRenderer());
      
        jTable6.getColumnModel().getColumns().nextElement().setResizable(true);
        
        while(h1<jTable6.getColumnModel().getColumnCount()){
        jTable6.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(100);

        }
        if(h1==1){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(200);
        }
        if(h1==2){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(200);
        }
        if(h1==3){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(200);
        }
         if(h1==4){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(700);
        }
          if(h1==5){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(250);
        }
           if(h1==6){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(250);
        }
            if(h1==7){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(4);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(250);
        }
            
        h1++;

        }
        
//         jTable6.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//
//                this.setHorizontalAlignment(JLabel.LEFT);
//
//                if(row==table.getRowCount()-1){
//                    this.setFont(new Font("Arial",Font.BOLD,20));
//                }else{
//                    this.setFont(new Font("Arial",Font.PLAIN,18));
//                }
//
//                if(col==4){
//                    Number c = (Number)parseDouble(value.toString());
//                    String text = NumberFormat.format(c );
//                    this.setText(text);
//                }else{
//                    this.setText(value.toString());
//                }
//                if (row%2==0) {
//                    setBackground(jButton49.getBackground());
//                    setForeground(jButton51.getBackground());
//                    if(isSelected){
//                        
//                        setBackground(Color.CYAN);
//                    }
//                } else {
//                    setBackground(jButton50.getBackground());
//                    setForeground(jButton51.getBackground());
//                    if(isSelected){setBackground(Color.CYAN);}
//                }
//
//                return this;
//            }
//        });
         
        jTable6.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

if(col>=5&&col<=7){
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
        
  if (row%2==0) {
                    setBackground(jButton49.getBackground());
                    setForeground(jButton51.getBackground());
                    if(isSelected){
                        
                        setBackground(Color.CYAN);
                    }
                } else {
                    setBackground(jButton50.getBackground());
                    setForeground(jButton51.getBackground());
                    if(isSelected){setBackground(Color.CYAN);}
                }

                return this;
            }
       
        });

       
//         
//        
        }
        
        
         
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton237ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton237ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"802");
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton237ActionPerformed

    private void jButton236ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton236ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"803");
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton236ActionPerformed

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
    private javax.swing.JPanel PrintLoanReceiptPanel;
    public javax.swing.JPanel ReceiptDetails;
    public javax.swing.JPanel TransactionsDetails;
    private javax.swing.JFormattedTextField creditAccountField72;
    public javax.swing.JPanel customerDetails;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton236;
    private javax.swing.JButton jButton237;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JButton jButton73;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JPanel printReceipController;
    // End of variables declaration//GEN-END:variables
  @Override
    public void updateText(String text) {
     switch( fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"))){ 
                case 802:jTextField60.setText(text);break;    
                case 803:jTextField59.setText(text);break; 
           
//             
                     
                    
                
     }
    
    } 
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
   if (me.getClickCount()==2){
    
    
   
     if(me.getSource()==jTable7){
         
     int selectedRow =jTable7.getSelectedRow();
                    int selectedColumn =jTable7.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable7.getModel().getValueAt(jTable7.convertRowIndexToModel(selectedRow), 2);
          
          String accountNumber = cvalue.toString();
          
//          JOptionPane.showMessageDialog(this, accountNumber);
          
      creditAccountField72.setValue(fmt.putSeparatorsOnNormaAccount(accountNumber));
      this.accountNumber=accountNumber;
      ReceiptDetails.setVisible(true);
      jLabel102.setVisible(true);
              jTextField60.setVisible(true);
              jButton237.setVisible(true);
                      jLabel101.setVisible(true);
                      jTextField59.setVisible(true);
                              jButton236.setVisible(true);

       customerDetails.setVisible(false);
     }
    
    }
     
     
        }
    
    
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
     if(me.getSource()==jTable6){
         
     int selectedRow =jTable6.getSelectedRow();
                    int selectedColumn =jTable6.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable6.getModel().getValueAt(jTable6.convertRowIndexToModel(selectedRow), 1);
          
          theBatchNumbeer = cvalue.toString();
          jButton1.setEnabled(true);
//          JOptionPane.showMessageDialog(this, accountNumber);
          
      
     }
    
    }
     
      
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
    public void actionPerformed(ActionEvent e) {
 
    }

    @Override
    public void keyTyped(KeyEvent e) {
 
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    
}

