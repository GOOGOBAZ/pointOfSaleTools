/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frames;

import googo.pmms.project2.accountsHelper.BatchRefNumber;
import googo.pmms.project2.accountsHelper.CreatingFolders;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingFieldValues;
import googo.pmms.project2.loanHelper.MaxBorrowed;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.accountsHelper.PostingMain;
import googo.pmms.project2.accountsHelper.PostingModal;
import googo.pmms.project2.accountsHelper.SendSms;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.BackUpRestoreDB;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.loanHelper.amortizeLoan;
import googo.pmms.project2.reportsHelper.LoanSavingsSharesOthers;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import googo.pmms.project2.smallFrames.OtherLoanDetails;
import googo.pmms.project2.topupLoan.DecisionTopUpImp;
import googo.pmms.project2.topupLoan.topUpDatabaseImp;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Stanchart
 */
public class NewLoanApplication extends javax.swing.JFrame implements BatchRefNumber,MouseListener,PostingFieldValues  {
     String closingNotes="1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;Not Specified:4;Top Ups;Not Specified:5;Others (Please Specify);Not Specified"; 
    
    double oldAmount = 0, newAmount,newQaulified=0.0,finalQualified=0;
    List<Object> topups;
   MaxBorrowed mxb=new MaxBorrowed();
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
    PostingModal model ;
    JFrame fi, f,fa,fb,fc;
    Date date;
  SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
  SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
       CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
 DatabaseQuaries dbq= new DatabaseQuaries();
 MaxmumAmountBorrowedFormulas maxValue= new MaxmumAmountBorrowedFormulas();
 loanDatabaseQuaries loan=new loanDatabaseQuaries();
  DecimalFormat df1 = new DecimalFormat("#,###");
   PostingMain post= new PostingMain(NewLoanApplication.this);
    Formartter fmt = new Formartter();
   List theCollection;
  BackUpRestoreDB dbBackUp= new BackUpRestoreDB();
    CreatingFolders filesW= new CreatingFolders();
//   private PostingEntryWindow.ProcessLoanRepayment laonPay;
  String completeStatus="Not Complete",flag="Not Flagged"; int temper=0;
    String batchNumber;
    OtherLoanReports otherLoans=new  OtherLoanReports();
     LoanSavingsSharesOthers loanSaveShare=new LoanSavingsSharesOthers();
         ListDataModel model1;
         ListDataModel result;
         String theAccount="";     
    List loanPaymentOrder;
 String userId,optionTwo;
 List amortDetails;
 
  SendSms sendsms= new SendSms();
  
    public NewLoanApplication(String userId) {
    this.userId=userId;
        initComponents();
        java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
      this.setTitle("LOAN APPLICATION MODULE");
     
     
         jTreeNewLoanApplication.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       jTreeNewLoanApplication.addMouseListener( this);
       jTable3.addMouseListener(this);


 jTable3.setAlignmentX(LEFT_ALIGNMENT);
jTable3.setAlignmentY(CENTER_ALIGNMENT);
jTable3.setAutoscrolls(true);
jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
jTable3.setRowHeight(35);
jTable3.setGridColor(Color.gray);


 jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(100);
jTable2.setGridColor(Color.gray);
    }
//public void setUserID(String userid){
//this.userId=userid;
//}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeNewLoanApplication = new javax.swing.JTree();
        jLabel61 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        newLoanPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jTextField18 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jCheckBox48 = new javax.swing.JCheckBox();
        jCheckBox49 = new javax.swing.JCheckBox();
        jCheckBox50 = new javax.swing.JCheckBox();
        jCheckBox51 = new javax.swing.JCheckBox();
        amountFieldDebit7 = new javax.swing.JFormattedTextField();
        amountFieldDebit8 = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jCheckBox52 = new javax.swing.JCheckBox();
        jLabel63 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton6 = new javax.swing.JButton();
        jComboBox13 = new javax.swing.JComboBox();
        jButton20 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jComboBox12GroupId = new javax.swing.JComboBox<>();
        jComboBox11GroupName = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        jComboBox14 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        updateExistingBorrowersDetails = new javax.swing.JCheckBox();
        jButton17 = new javax.swing.JButton();
        useDefaultBorrowerDetails = new javax.swing.JCheckBox();
        guarantorSetupPanel = new javax.swing.JPanel();
        bneedsLabel64 = new javax.swing.JLabel();
        gballowedLabel65 = new javax.swing.JLabel();
        gnamwLabel66 = new javax.swing.JLabel();
        gacnoLabel67 = new javax.swing.JLabel();
        actionLabel69 = new javax.swing.JLabel();
        ggallowedLabel70 = new javax.swing.JLabel();
        bhasLabel71 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox10 = new javax.swing.JComboBox();
        guarantoursAccount5 = new javax.swing.JTextField();
        guarantoursAccount6 = new javax.swing.JTextField();
        guarantoursAccount7 = new javax.swing.JTextField();
        guarantoursAccount8 = new javax.swing.JTextField();
        guarantoursAccount9 = new javax.swing.JTextField();
        amountFieldDebit2 = new javax.swing.JFormattedTextField();
        amountFieldDebit3 = new javax.swing.JFormattedTextField();
        amountFieldDebit4 = new javax.swing.JFormattedTextField();
        amountFieldDebit5 = new javax.swing.JFormattedTextField();
        amountFieldDebit6 = new javax.swing.JFormattedTextField();
        amountFieldDebit9 = new javax.swing.JFormattedTextField();
        amountFieldDebit10 = new javax.swing.JFormattedTextField();
        amountFieldDebit11 = new javax.swing.JFormattedTextField();
        amountFieldDebit12 = new javax.swing.JFormattedTextField();
        amountFieldDebit13 = new javax.swing.JFormattedTextField();
        amountFieldDebit14 = new javax.swing.JFormattedTextField();
        amountFieldDebit15 = new javax.swing.JFormattedTextField();
        amountFieldDebit16 = new javax.swing.JFormattedTextField();
        amountFieldDebit17 = new javax.swing.JFormattedTextField();
        amountFieldDebit18 = new javax.swing.JFormattedTextField();
        amountFieldDebit19 = new javax.swing.JFormattedTextField();
        amountFieldDebit20 = new javax.swing.JFormattedTextField();
        amountFieldDebit21 = new javax.swing.JFormattedTextField();
        amountFieldDebit22 = new javax.swing.JFormattedTextField();
        amountFieldDebit23 = new javax.swing.JFormattedTextField();
        secondCheckBox2 = new javax.swing.JCheckBox();
        thirdCheckBox2 = new javax.swing.JCheckBox();
        fourthCheckBox2 = new javax.swing.JCheckBox();
        fifthCheckBox2 = new javax.swing.JCheckBox();
        firstCheckBox2 = new javax.swing.JCheckBox();
        refreshButton2 = new javax.swing.JButton();
        submitButton2 = new javax.swing.JButton();
        guarantoursAccount10 = new javax.swing.JTextField();
        guarantoursAccount11 = new javax.swing.JTextField();
        guarantoursAccount12 = new javax.swing.JTextField();
        guarantoursAccount13 = new javax.swing.JTextField();
        guarantoursAccount14 = new javax.swing.JTextField();
        gallowamtLabel68 = new javax.swing.JLabel();
        amountFieldDebit24 = new javax.swing.JFormattedTextField();
        amountFieldDebit25 = new javax.swing.JFormattedTextField();
        amountFieldDebit26 = new javax.swing.JFormattedTextField();
        amountFieldDebit27 = new javax.swing.JFormattedTextField();
        amountFieldDebit28 = new javax.swing.JFormattedTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        firstCheckBox1 = new javax.swing.JCheckBox();
        secondCheckBox1 = new javax.swing.JCheckBox();
        thirdCheckBox1 = new javax.swing.JCheckBox();
        fourthCheckBox1 = new javax.swing.JCheckBox();
        fifthCheckBox1 = new javax.swing.JCheckBox();
        submitButton1 = new javax.swing.JButton();
        refreshButton1 = new javax.swing.JButton();
        loanClosedPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox5 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        topUpPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel88 = new javax.swing.JLabel();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jTextField10 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        topUpPanel1 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton48 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel1.setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.setLayout(null);

        jTreeNewLoanApplication.setBackground(java.awt.SystemColor.activeCaption);
        jTreeNewLoanApplication.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        jTreeNewLoanApplication.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTreeNewLoanApplication.setForeground(new java.awt.Color(255, 255, 255));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Navigation Guide");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Log Out");
        treeNode1.add(treeNode2);
        jTreeNewLoanApplication.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTreeNewLoanApplication);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 10, 140, 680);

        jLabel61.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("TO");
        jLabel61.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel61MousePressed(evt);
            }
        });
        jPanel1.add(jLabel61);
        jLabel61.setBounds(140, 10, 1160, 30);
        jLabel61.setVisible(false);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jTabbedPane1.setBackground(java.awt.SystemColor.activeCaption);
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });
        jTabbedPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTabbedPane1PropertyChange(evt);
            }
        });

        jPanel3.setLayout(null);

        newLoanPanel.setBackground(java.awt.SystemColor.activeCaption);
        newLoanPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        newLoanPanel.setForeground(new java.awt.Color(255, 255, 255));
        newLoanPanel.setLayout(null);

        jLabel10.setBackground(new java.awt.Color(71, 140, 165));
        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ACCOUNT NUMBER");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jLabel10);
        jLabel10.setBounds(450, 10, 150, 30);

        jComboBox2.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PERSONAL EFFECTS", "BUSINESS PURPOSE", "ASSET FINANCING", "HOME IMPROVEMENT", "SCHOOL FEES" }));
        newLoanPanel.add(jComboBox2);
        jComboBox2.setBounds(580, 130, 260, 30);
        jComboBox2.setVisible(false);

        jLabel15.setBackground(new java.awt.Color(71, 140, 165));
        jLabel15.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("LOAN TENURE REQUIRED");
        newLoanPanel.add(jLabel15);
        jLabel15.setBounds(390, 50, 170, 30);

        jComboBox3.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });
        newLoanPanel.add(jComboBox3);
        jComboBox3.setBounds(140, 10, 210, 30);

        jTextField18.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(0, 102, 102));
        jTextField18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jTextField18);
        jTextField18.setBounds(570, 50, 60, 30);

        jLabel25.setBackground(new java.awt.Color(71, 140, 165));
        jLabel25.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("LOAN PURPOSE");
        newLoanPanel.add(jLabel25);
        jLabel25.setBounds(390, 130, 130, 30);
        jLabel25.setVisible(false);

        jLabel26.setBackground(new java.awt.Color(71, 140, 165));
        jLabel26.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("TOTAL LOAN AMOUNT:");
        newLoanPanel.add(jLabel26);
        jLabel26.setBounds(20, 130, 140, 30);
        jLabel26 .setVisible(false);

        jLabel27.setBackground(new java.awt.Color(71, 140, 165));
        jLabel27.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("LOAN AMOUNT REQUIRED");
        newLoanPanel.add(jLabel27);
        jLabel27.setBounds(390, 90, 160, 30);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("PROCESS LOAN REQUEST");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton1);
        jButton1.setBounds(640, 540, 230, 30);
        jButton1.setEnabled(false);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("AMORTIZE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton3);
        jButton3.setBounds(30, 540, 150, 30);
        jButton3.setEnabled(false);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("LOAN APPROVALS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton4);
        jButton4.setBounds(450, 540, 190, 30);
        jButton4.setEnabled(false);

        jLabel28.setBackground(new java.awt.Color(71, 140, 165));
        jLabel28.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("MAX LOAN ACCESSIBLE");
        newLoanPanel.add(jLabel28);
        jLabel28.setBounds(20, 90, 150, 30);

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("DOCUMENTATION&COLLATERAL");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton5);
        jButton5.setBounds(180, 540, 270, 30);
        jButton5.setEnabled(false);

        jLabel23.setBackground(new java.awt.Color(71, 140, 165));
        jLabel23.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("REPAYMENT SOURCE");
        newLoanPanel.add(jLabel23);
        jLabel23.setBounds(20, 130, 130, 30);
        jLabel23.setVisible(false);

        jTextField35.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTextField35.setForeground(new java.awt.Color(0, 102, 102));
        jTextField35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jTextField35);
        jTextField35.setBounds(180, 130, 200, 30);
        jTextField35.setVisible(false);

        jTextField36.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTextField36.setForeground(new java.awt.Color(0, 102, 102));
        jTextField36.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField36ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jTextField36);
        jTextField36.setBounds(180, 50, 30, 30);
        jTextField36.setEditable(false);
        jTextField36.setText("");

        jLabel36.setBackground(new java.awt.Color(71, 140, 165));
        jLabel36.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("MAX LOAN TENURE");
        newLoanPanel.add(jLabel36);
        jLabel36.setBounds(20, 50, 130, 30);

        jComboBox4.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DAYS", "WEEKS", "FORTNIGHTS", "MONTHS", "QUARTERS", "HALF YEARS", "YEARS", "BIENNIALS" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jComboBox4);
        jComboBox4.setBounds(630, 50, 190, 30);
        try{

            populateComboBox(jComboBox3);

        }catch(SQLException e){e.toString();}

        jComboBox8.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox8.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DAYS", "WEEKS", "FORTNIGHTS", "MONTHS", "QUARTERS", "HALF YEARS", "YEARS", "BIENNIALS" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jComboBox8);
        jComboBox8.setBounds(210, 50, 120, 30);
        jComboBox8.setEditable(true);
        jComboBox8.setEnabled(true);
        jComboBox8.setSelectedItem("");

        jCheckBox48.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jCheckBox48.setText("USE");
        jCheckBox48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox48ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox48);
        jCheckBox48.setBounds(820, 50, 50, 30);

        jCheckBox49.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jCheckBox49.setText("USE");
        jCheckBox49.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                jCheckBox49AncestorRemoved(evt);
            }
        });
        jCheckBox49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox49ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox49);
        jCheckBox49.setBounds(330, 50, 50, 30);

        jCheckBox50.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jCheckBox50.setText("USE");
        jCheckBox50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox50ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox50);
        jCheckBox50.setBounds(820, 90, 50, 30);

        jCheckBox51.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jCheckBox51.setText("USE");
        jCheckBox51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox51ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox51);
        jCheckBox51.setBounds(330, 90, 50, 30);

        amountFieldDebit7.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit7.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        amountFieldDebit7.setValue(null);
        amountFieldDebit7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit7ActionPerformed(evt);
            }
        });
        newLoanPanel.add(amountFieldDebit7);
        amountFieldDebit7.setBounds(570, 90, 250, 30);
        amountFieldDebit7.setText("");

        amountFieldDebit8.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit8.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        amountFieldDebit8.setValue(null);
        amountFieldDebit8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit8ActionPerformed(evt);
            }
        });
        newLoanPanel.add(amountFieldDebit8);
        amountFieldDebit8.setBounds(180, 90, 150, 30);
        amountFieldDebit8.setEditable(false);

        jLabel29.setBackground(java.awt.SystemColor.activeCaption);
        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("ACCOUNT NAME");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jLabel29);
        jLabel29.setBounds(20, 10, 120, 30);

        jLabel37.setBackground(new java.awt.Color(71, 140, 165));
        jLabel37.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("INSTALMENT START DATE:");
        newLoanPanel.add(jLabel37);
        jLabel37.setBounds(20, 170, 190, 30);
        jLabel37.setVisible(false);

        jLabel38.setBackground(new java.awt.Color(71, 140, 165));
        jLabel38.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("THE LOAN PRINCIPAL AMOUNT:");
        newLoanPanel.add(jLabel38);
        jLabel38.setBounds(20, 210, 220, 30);
        jLabel38 .setVisible(false);

        jLabel39.setBackground(new java.awt.Color(71, 140, 165));
        jLabel39.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("TOTAL NUMBER OF LOAN INSTALMENTS:");
        newLoanPanel.add(jLabel39);
        jLabel39.setBounds(20, 250, 270, 30);
        jLabel39.setVisible(false);

        jLabel41.setBackground(new java.awt.Color(71, 140, 165));
        jLabel41.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel41);
        jLabel41.setBounds(230, 90, 180, 30);

        jLabel42.setBackground(new java.awt.Color(71, 140, 165));
        jLabel42.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel42);
        jLabel42.setBounds(190, 130, 210, 30);

        jLabel43.setBackground(new java.awt.Color(71, 140, 165));
        jLabel43.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 255));
        newLoanPanel.add(jLabel43);
        jLabel43.setBounds(270, 170, 110, 30);

        jLabel45.setBackground(new java.awt.Color(71, 140, 165));
        jLabel45.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 102, 102));
        jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE DOCUMENTATION&COLLATERAL APPROVAL STAGE");
        newLoanPanel.add(jLabel45);
        jLabel45.setBounds(20, 50, 850, 30);
        jLabel45.setVisible(false);

        jLabel46.setBackground(new java.awt.Color(71, 140, 165));
        jLabel46.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel46);
        jLabel46.setBounds(240, 210, 160, 30);

        jLabel47.setBackground(new java.awt.Color(71, 140, 165));
        jLabel47.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 153));
        newLoanPanel.add(jLabel47);
        jLabel47.setBounds(290, 250, 130, 30);

        jLabel49.setBackground(new java.awt.Color(71, 140, 165));
        jLabel49.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("APPLICANT'S LOAN ID:");
        newLoanPanel.add(jLabel49);
        jLabel49.setBounds(420, 90, 180, 30);
        jLabel49 .setVisible(false);

        jLabel50.setBackground(new java.awt.Color(71, 140, 165));
        jLabel50.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel50);
        jLabel50.setBounds(600, 90, 210, 30);

        jLabel51.setBackground(new java.awt.Color(71, 140, 165));
        jLabel51.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("THE LOAN INSTALMENT AMOUNT:");
        newLoanPanel.add(jLabel51);
        jLabel51.setBounds(420, 130, 220, 30);
        jLabel51.setVisible(false);

        jLabel52.setBackground(new java.awt.Color(71, 140, 165));
        jLabel52.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel52);
        jLabel52.setBounds(640, 130, 130, 30);

        jLabel53.setBackground(new java.awt.Color(71, 140, 165));
        jLabel53.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("INSTALMENT END DATE:");
        newLoanPanel.add(jLabel53);
        jLabel53.setBounds(420, 170, 170, 30);
        jLabel53.setVisible(false);

        jLabel54.setBackground(new java.awt.Color(71, 140, 165));
        jLabel54.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel54);
        jLabel54.setBounds(590, 170, 130, 30);

        jLabel55.setBackground(new java.awt.Color(71, 140, 165));
        jLabel55.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("THE LOAN TOTAL INTEREST AMOUNT:");
        newLoanPanel.add(jLabel55);
        jLabel55.setBounds(420, 210, 260, 30);
        jLabel55.setVisible(false);

        jLabel58.setBackground(new java.awt.Color(71, 140, 165));
        jLabel58.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel58);
        jLabel58.setBounds(670, 210, 130, 30);

        jLabel59.setBackground(new java.awt.Color(71, 140, 165));
        jLabel59.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("THE LOAN TENURE:");
        newLoanPanel.add(jLabel59);
        jLabel59.setBounds(430, 250, 130, 30);
        jLabel59.setVisible(false);

        jLabel60.setBackground(new java.awt.Color(71, 140, 165));
        jLabel60.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 204));
        newLoanPanel.add(jLabel60);
        jLabel60.setBounds(560, 250, 130, 30);

        jLabel62.setBackground(new java.awt.Color(71, 140, 165));
        jLabel62.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("APPLICANT'S ACCOUNT NAME:");
        newLoanPanel.add(jLabel62);
        jLabel62.setBounds(20, 90, 210, 30);
        jLabel62 .setVisible(false);

        jCheckBox52.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox52.setText("Use Guarantors");
        jCheckBox52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox52ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox52);
        jCheckBox52.setBounds(710, 280, 170, 30);
        jCheckBox52.setVisible(false);

        jLabel63.setBackground(new java.awt.Color(71, 140, 165));
        jLabel63.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("GUARANTORS");
        newLoanPanel.add(jLabel63);
        jLabel63.setBounds(190, 170, 130, 30);
        jLabel63.setVisible(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/Search.png"))); // NOI18N
        jButton2.setToolTipText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton2);
        jButton2.setBounds(350, 10, 30, 30);

        jCheckBox1.setToolTipText("Check To Edit Searched Item");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox1);
        jCheckBox1.setBounds(380, 10, 30, 30);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton6.setToolTipText("Refresh");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton6);
        jButton6.setBounds(410, 10, 30, 30);

        jComboBox13.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox13.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        jComboBox13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox13KeyPressed(evt);
            }
        });
        newLoanPanel.add(jComboBox13);
        jComboBox13.setBounds(610, 10, 210, 30);

        jButton20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton20.setText("Other Loan Details");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton20);
        jButton20.setBounds(30, 490, 180, 40);
        jButton20.setVisible(false);

        jButton16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton16.setText("Economic Status");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton16);
        jButton16.setBounds(210, 490, 170, 40);
        jButton16.setEnabled(false);
        jButton16.setVisible(false);

        jButton22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton22.setText("Bio Data");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton22);
        jButton22.setBounds(380, 490, 170, 40);
        jButton22.setEnabled(false);
        jButton22.setVisible(false);

        jComboBox12GroupId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jComboBox12GroupId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12GroupIdActionPerformed(evt);
            }
        });
        newLoanPanel.add(jComboBox12GroupId);
        jComboBox12GroupId.setBounds(30, 450, 200, 40);
        jComboBox12GroupId.setVisible(false);

        jComboBox11GroupName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11GroupNameActionPerformed(evt);
            }
        });
        newLoanPanel.add(jComboBox11GroupName);
        jComboBox11GroupName.setBounds(230, 450, 200, 40);
        jComboBox11GroupName.setVisible(false);

        jLabel31.setBackground(java.awt.SystemColor.activeCaption);
        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel31.setText("Group Id");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jLabel31);
        jLabel31.setBounds(30, 420, 90, 30);
        jLabel31.setVisible(false);

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jTextField5);
        jTextField5.setBounds(120, 420, 100, 30);
        jTextField5.setText("");
        jTextField5.setEditable(false);
        jTextField5.setVisible(false);

        jLabel30.setBackground(java.awt.SystemColor.activeCaption);
        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel30.setText("Group Name");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jLabel30);
        jLabel30.setBounds(220, 420, 100, 30);
        jLabel30.setVisible(false);
        newLoanPanel.add(jTextField4);
        jTextField4.setBounds(320, 420, 280, 30);
        jTextField4.setText("");
        jTextField4.setEditable(false);
        jTextField4.setVisible(false);

        jCheckBox4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox4.setText("Assign Group");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jCheckBox4);
        jCheckBox4.setBounds(30, 390, 190, 30);
        jCheckBox4.setVisible(false);

        jComboBox14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Individual", "Group" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jComboBox14);
        jComboBox14.setBounds(180, 350, 180, 30);
        jComboBox14.setVisible(false);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Borrowing Category");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        newLoanPanel.add(jLabel18);
        jLabel18.setBounds(30, 350, 150, 30);
        jLabel18.setVisible(false);

        updateExistingBorrowersDetails.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updateExistingBorrowersDetails.setText("Update Existing Borrower's Details");
        updateExistingBorrowersDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateExistingBorrowersDetailsActionPerformed(evt);
            }
        });
        newLoanPanel.add(updateExistingBorrowersDetails);
        updateExistingBorrowersDetails.setBounds(30, 320, 330, 30);
        updateExistingBorrowersDetails.setVisible(false);

        jButton17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton17.setText("Testing123");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        newLoanPanel.add(jButton17);
        jButton17.setBounds(720, 360, 150, 30);
        jButton17.setVisible(false);

        useDefaultBorrowerDetails.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        useDefaultBorrowerDetails.setText("Use Default Borrower's Details");
        useDefaultBorrowerDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useDefaultBorrowerDetailsActionPerformed(evt);
            }
        });
        newLoanPanel.add(useDefaultBorrowerDetails);
        useDefaultBorrowerDetails.setBounds(30, 280, 330, 30);
        useDefaultBorrowerDetails.setSelected(true);
        useDefaultBorrowerDetails.setVisible(false);

        jPanel3.add(newLoanPanel);
        newLoanPanel.setBounds(0, 0, 1160, 620);

        guarantorSetupPanel.setBackground(java.awt.SystemColor.activeCaption);
        guarantorSetupPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        guarantorSetupPanel.setLayout(null);

        bneedsLabel64.setBackground(new java.awt.Color(0, 51, 51));
        bneedsLabel64.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        bneedsLabel64.setForeground(new java.awt.Color(255, 255, 255));
        bneedsLabel64.setText("B NEEDS");
        bneedsLabel64.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bneedsLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bneedsLabel64MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(bneedsLabel64);
        bneedsLabel64.setBounds(620, 10, 80, 30);
        bneedsLabel64.setVisible(false);

        gballowedLabel65.setBackground(new java.awt.Color(0, 51, 51));
        gballowedLabel65.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        gballowedLabel65.setForeground(new java.awt.Color(255, 255, 255));
        gballowedLabel65.setText("GBALLOWED ");
        gballowedLabel65.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gballowedLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gballowedLabel65MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(gballowedLabel65);
        gballowedLabel65.setBounds(310, 10, 90, 30);
        gballowedLabel65.setVisible(false);

        gnamwLabel66.setBackground(new java.awt.Color(0, 51, 51));
        gnamwLabel66.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        gnamwLabel66.setForeground(new java.awt.Color(255, 255, 255));
        gnamwLabel66.setText("G NAME");
        gnamwLabel66.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gnamwLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gnamwLabel66MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(gnamwLabel66);
        gnamwLabel66.setBounds(10, 10, 160, 30);
        gnamwLabel66.setVisible(false);

        gacnoLabel67.setBackground(new java.awt.Color(0, 51, 51));
        gacnoLabel67.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        gacnoLabel67.setForeground(new java.awt.Color(255, 255, 255));
        gacnoLabel67.setText("G A/C NO.");
        gacnoLabel67.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gacnoLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gacnoLabel67MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(gacnoLabel67);
        gacnoLabel67.setBounds(180, 10, 120, 30);
        gacnoLabel67.setVisible(false);

        actionLabel69.setBackground(new java.awt.Color(0, 51, 51));
        actionLabel69.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        actionLabel69.setForeground(new java.awt.Color(255, 255, 255));
        actionLabel69.setText("ACTION");
        actionLabel69.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        actionLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                actionLabel69MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(actionLabel69);
        actionLabel69.setBounds(800, 10, 70, 30);
        actionLabel69.setVisible(false);

        ggallowedLabel70.setBackground(new java.awt.Color(0, 51, 51));
        ggallowedLabel70.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        ggallowedLabel70.setForeground(new java.awt.Color(255, 255, 255));
        ggallowedLabel70.setText("GGALLOWED");
        ggallowedLabel70.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ggallowedLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ggallowedLabel70MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(ggallowedLabel70);
        ggallowedLabel70.setBounds(410, 10, 90, 30);
        ggallowedLabel70.setVisible(false);

        bhasLabel71.setBackground(new java.awt.Color(0, 51, 51));
        bhasLabel71.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        bhasLabel71.setForeground(new java.awt.Color(255, 255, 255));
        bhasLabel71.setText("B HAS");
        bhasLabel71.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bhasLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bhasLabel71MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(bhasLabel71);
        bhasLabel71.setBounds(710, 10, 80, 30);
        bhasLabel71.setVisible(false);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jComboBox1);
        jComboBox1.setBounds(10, 90, 160, 30);
        jComboBox1.setEditable(true);
        jComboBox1  .setEnabled(true);
        jComboBox1.setSelectedItem("");
        jComboBox1.setVisible(false);

        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jComboBox6);
        jComboBox6.setBounds(10, 130, 160, 30);
        jComboBox6.setEditable(true);
        jComboBox6  .setEnabled(true);
        jComboBox6.setSelectedItem("");
        jComboBox6.setVisible(false);

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jComboBox7);
        jComboBox7.setBounds(10, 170, 160, 30);
        jComboBox7.setEditable(true);
        jComboBox7  .setEnabled(true);
        jComboBox7.setSelectedItem("");
        jComboBox7.setVisible(false);

        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jComboBox9);
        jComboBox9.setBounds(10, 210, 160, 30);
        jComboBox9.setEditable(true);
        jComboBox9  .setEnabled(true);
        jComboBox9.setSelectedItem("");
        jComboBox9.setVisible(false);

        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jComboBox10);
        jComboBox10.setBounds(10, 50, 160, 30);
        jComboBox10.setEditable(true);
        jComboBox10  .setEnabled(true);
        jComboBox10.setSelectedItem("");
        jComboBox10.setVisible(false);
        guarantorSetupPanel.add(guarantoursAccount5);
        guarantoursAccount5.setBounds(180, 50, 120, 30);
        guarantoursAccount5.setVisible(false);
        guarantoursAccount5.setText("");
        guarantorSetupPanel.add(guarantoursAccount6);
        guarantoursAccount6.setBounds(180, 90, 120, 30);
        guarantoursAccount6 .setVisible(false);
        guarantoursAccount6.setText("");
        guarantorSetupPanel.add(guarantoursAccount7);
        guarantoursAccount7.setBounds(180, 130, 120, 30);
        guarantoursAccount7 .setVisible(false);
        guarantoursAccount7 .setText("");
        guarantorSetupPanel.add(guarantoursAccount8);
        guarantoursAccount8.setBounds(180, 170, 120, 30);
        guarantoursAccount8  .setVisible(false);
        guarantoursAccount8  .setText("");
        guarantorSetupPanel.add(guarantoursAccount9);
        guarantoursAccount9.setBounds(180, 210, 120, 30);
        guarantoursAccount9 .setVisible(false);
        guarantoursAccount9  .setText("");

        amountFieldDebit2.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit2.setValue(null);
        amountFieldDebit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit2);
        amountFieldDebit2.setBounds(310, 50, 90, 30);
        amountFieldDebit2.setVisible(false);

        amountFieldDebit3.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit3.setValue(null);
        amountFieldDebit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit3ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit3);
        amountFieldDebit3.setBounds(310, 90, 90, 30);
        amountFieldDebit3 .setVisible(false);

        amountFieldDebit4.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit4.setValue(null);
        amountFieldDebit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit4ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit4);
        amountFieldDebit4.setBounds(310, 130, 90, 30);
        amountFieldDebit4.setVisible(false);

        amountFieldDebit5.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit5.setValue(null);
        amountFieldDebit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit5ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit5);
        amountFieldDebit5.setBounds(310, 170, 90, 30);
        amountFieldDebit5 .setVisible(false);

        amountFieldDebit6.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit6.setValue(null);
        amountFieldDebit6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit6ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit6);
        amountFieldDebit6.setBounds(310, 210, 90, 30);
        amountFieldDebit6 .setVisible(false);

        amountFieldDebit9.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit9.setValue(null);
        amountFieldDebit9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit9ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit9);
        amountFieldDebit9.setBounds(410, 50, 90, 30);
        amountFieldDebit9.setVisible(false);

        amountFieldDebit10.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit10.setValue(null);
        amountFieldDebit10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit10ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit10);
        amountFieldDebit10.setBounds(410, 90, 90, 30);
        amountFieldDebit10.setVisible(false);

        amountFieldDebit11.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit11.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit11.setValue(null);
        amountFieldDebit11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit11ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit11);
        amountFieldDebit11.setBounds(410, 130, 90, 30);
        amountFieldDebit11.setVisible(false);

        amountFieldDebit12.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit12.setValue(null);
        amountFieldDebit12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit12ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit12);
        amountFieldDebit12.setBounds(410, 170, 90, 30);
        amountFieldDebit12 .setVisible(false);

        amountFieldDebit13.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit13.setValue(null);
        amountFieldDebit13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit13ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit13);
        amountFieldDebit13.setBounds(410, 210, 90, 30);
        amountFieldDebit13.setVisible(false);

        amountFieldDebit14.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit14.setValue(null);
        amountFieldDebit14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit14ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit14);
        amountFieldDebit14.setBounds(620, 50, 80, 30);
        amountFieldDebit14 .setVisible(false);

        amountFieldDebit15.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit15.setValue(null);
        amountFieldDebit15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit15ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit15);
        amountFieldDebit15.setBounds(620, 90, 80, 30);
        amountFieldDebit15.setVisible(false);

        amountFieldDebit16.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit16.setValue(null);
        amountFieldDebit16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit16ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit16);
        amountFieldDebit16.setBounds(620, 130, 80, 30);
        amountFieldDebit16.setVisible(false);

        amountFieldDebit17.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit17.setValue(null);
        amountFieldDebit17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit17ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit17);
        amountFieldDebit17.setBounds(620, 170, 80, 30);
        amountFieldDebit17.setVisible(false);

        amountFieldDebit18.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit18.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit18.setValue(null);
        amountFieldDebit18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit18ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit18);
        amountFieldDebit18.setBounds(620, 210, 80, 30);
        amountFieldDebit18 .setVisible(false);

        amountFieldDebit19.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit19.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit19.setValue(null);
        amountFieldDebit19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit19ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit19);
        amountFieldDebit19.setBounds(710, 50, 80, 30);
        amountFieldDebit19 .setVisible(false);

        amountFieldDebit20.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit20.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit20.setValue(null);
        amountFieldDebit20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit20ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit20);
        amountFieldDebit20.setBounds(710, 90, 80, 30);
        amountFieldDebit20.setVisible(false);

        amountFieldDebit21.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit21.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit21.setValue(null);
        amountFieldDebit21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit21ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit21);
        amountFieldDebit21.setBounds(710, 130, 80, 30);
        amountFieldDebit21.setVisible(false);

        amountFieldDebit22.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit22.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit22.setValue(null);
        amountFieldDebit22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit22ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit22);
        amountFieldDebit22.setBounds(710, 170, 80, 30);
        amountFieldDebit22.setVisible(false);

        amountFieldDebit23.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit23.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit23.setValue(null);
        amountFieldDebit23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit23ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit23);
        amountFieldDebit23.setBounds(710, 210, 80, 30);
        amountFieldDebit23 .setVisible(false);

        secondCheckBox2.setText("USE");
        secondCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondCheckBox2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(secondCheckBox2);
        secondCheckBox2.setBounds(800, 90, 70, 30);
        secondCheckBox2 .setVisible(false);
        secondCheckBox2 .setEnabled(false);

        thirdCheckBox2.setText("USE");
        thirdCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirdCheckBox2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(thirdCheckBox2);
        thirdCheckBox2.setBounds(800, 130, 70, 30);
        thirdCheckBox2.setVisible(false);
        thirdCheckBox2.setEnabled(false);

        fourthCheckBox2.setText("USE ");
        fourthCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourthCheckBox2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(fourthCheckBox2);
        fourthCheckBox2.setBounds(800, 170, 70, 30);
        fourthCheckBox2 .setVisible(false);
        fourthCheckBox2 .setEnabled(false);

        fifthCheckBox2.setText("USE ");
        fifthCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fifthCheckBox2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(fifthCheckBox2);
        fifthCheckBox2.setBounds(800, 210, 70, 30);
        fifthCheckBox2 .setVisible(false);
        fifthCheckBox2 .setEnabled(false);

        firstCheckBox2.setText("USE ");
        firstCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstCheckBox2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(firstCheckBox2);
        firstCheckBox2.setBounds(800, 50, 70, 30);
        firstCheckBox2 .setVisible(false);

        refreshButton2.setText("REFRESH");
        refreshButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButton2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(refreshButton2);
        refreshButton2.setBounds(460, 280, 120, 30);
        refreshButton2.setVisible(false);

        submitButton2.setText("SUBMIT");
        submitButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButton2ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(submitButton2);
        submitButton2.setBounds(340, 280, 120, 30);
        submitButton2.setVisible(false);
        guarantorSetupPanel.add(guarantoursAccount10);
        guarantoursAccount10.setBounds(10, 50, 160, 30);
        guarantoursAccount10.setVisible(false);
        guarantoursAccount10.setText("");
        guarantorSetupPanel.add(guarantoursAccount11);
        guarantoursAccount11.setBounds(10, 90, 160, 30);
        guarantoursAccount11.setVisible(false);
        guarantoursAccount11.setText("");
        guarantorSetupPanel.add(guarantoursAccount12);
        guarantoursAccount12.setBounds(10, 130, 160, 30);
        guarantoursAccount12 .setVisible(false);
        guarantoursAccount12 .setText("");
        guarantorSetupPanel.add(guarantoursAccount13);
        guarantoursAccount13.setBounds(10, 170, 160, 30);
        guarantoursAccount13 .setVisible(false);
        guarantoursAccount13 .setText("");
        guarantorSetupPanel.add(guarantoursAccount14);
        guarantoursAccount14.setBounds(10, 210, 160, 30);
        guarantoursAccount14.setVisible(false);
        guarantoursAccount14 .setText("");

        gallowamtLabel68.setBackground(new java.awt.Color(0, 51, 51));
        gallowamtLabel68.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        gallowamtLabel68.setForeground(new java.awt.Color(255, 255, 255));
        gallowamtLabel68.setText("GALLOW AMT");
        gallowamtLabel68.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gallowamtLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gallowamtLabel68MousePressed(evt);
            }
        });
        guarantorSetupPanel.add(gallowamtLabel68);
        gallowamtLabel68.setBounds(510, 10, 100, 30);
        gallowamtLabel68.setVisible(false);

        amountFieldDebit24.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit24.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit24.setValue(null);
        amountFieldDebit24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit24ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit24);
        amountFieldDebit24.setBounds(510, 50, 80, 30);
        amountFieldDebit24.setVisible(false);

        amountFieldDebit25.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit25.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit25.setValue(null);
        amountFieldDebit25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit25ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit25);
        amountFieldDebit25.setBounds(510, 90, 80, 30);
        amountFieldDebit25.setVisible(false);

        amountFieldDebit26.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit26.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit26.setValue(null);
        amountFieldDebit26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit26ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit26);
        amountFieldDebit26.setBounds(510, 130, 80, 30);
        amountFieldDebit26.setVisible(false);

        amountFieldDebit27.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit27.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit27.setValue(null);
        amountFieldDebit27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit27ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit27);
        amountFieldDebit27.setBounds(510, 170, 80, 30);
        amountFieldDebit27.setVisible(false);

        amountFieldDebit28.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit28.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit28.setValue(null);
        amountFieldDebit28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit28ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(amountFieldDebit28);
        amountFieldDebit28.setBounds(510, 210, 80, 30);
        amountFieldDebit28 .setVisible(false);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jButton7);
        jButton7.setBounds(590, 210, 20, 30);
        jButton7.setVisible(false);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jButton8);
        jButton8.setBounds(590, 50, 20, 30);
        jButton8.setVisible(false);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jButton9);
        jButton9.setBounds(590, 90, 20, 30);
        jButton9.setVisible(false);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jButton10);
        jButton10.setBounds(590, 130, 20, 30);
        jButton10.setVisible(false);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(jButton11);
        jButton11.setBounds(590, 170, 20, 30);
        jButton11.setVisible(false);

        firstCheckBox1.setText("USE ");
        firstCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstCheckBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(firstCheckBox1);
        firstCheckBox1.setBounds(310, 50, 70, 30);
        firstCheckBox1 .setVisible(false);

        secondCheckBox1.setText("USE");
        secondCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondCheckBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(secondCheckBox1);
        secondCheckBox1.setBounds(310, 90, 70, 30);
        secondCheckBox1 .setVisible(false);

        thirdCheckBox1.setText("USE");
        thirdCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirdCheckBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(thirdCheckBox1);
        thirdCheckBox1.setBounds(310, 130, 70, 30);
        thirdCheckBox1.setVisible(false);

        fourthCheckBox1.setText("USE ");
        fourthCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourthCheckBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(fourthCheckBox1);
        fourthCheckBox1.setBounds(310, 170, 70, 30);
        fourthCheckBox1 .setVisible(false);

        fifthCheckBox1.setText("USE ");
        fifthCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fifthCheckBox1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(fifthCheckBox1);
        fifthCheckBox1.setBounds(310, 210, 70, 30);
        fifthCheckBox1 .setVisible(false);

        submitButton1.setText("SUBMIT");
        submitButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButton1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(submitButton1);
        submitButton1.setBounds(30, 280, 120, 30);
        submitButton1.setVisible(false);

        refreshButton1.setText("REFRESH");
        refreshButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButton1ActionPerformed(evt);
            }
        });
        guarantorSetupPanel.add(refreshButton1);
        refreshButton1.setBounds(150, 280, 120, 30);
        refreshButton1.setVisible(false);

        jPanel3.add(guarantorSetupPanel);
        guarantorSetupPanel.setBounds(0, 0, 1170, 620);
        guarantorSetupPanel.setVisible(false);

        loanClosedPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanClosedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        loanClosedPanel.setForeground(new java.awt.Color(255, 255, 255));
        loanClosedPanel.setLayout(null);

        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable2.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable2);

        loanClosedPanel.add(jScrollPane4);
        jScrollPane4.setBounds(10, 10, 1100, 370);

        jComboBox5.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jComboBox5.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CHOOSE ACTION", "RETURN TO LOAN MANAGEMENT", "PROCEED TO NEW LOAN APPLICATION" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        loanClosedPanel.add(jComboBox5);
        jComboBox5.setBounds(320, 590, 260, 30);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        loanClosedPanel.add(jScrollPane2);
        jScrollPane2.setBounds(10, 410, 1104, 160);

        jLabel17.setBackground(java.awt.SystemColor.activeCaption);
        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel17.setText("Loan Security");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loanClosedPanel.add(jLabel17);
        jLabel17.setBounds(10, 380, 1104, 30);

        jPanel3.add(loanClosedPanel);
        loanClosedPanel.setBounds(0, 0, 1170, 620);
        loanClosedPanel.setVisible(false);

        jTabbedPane1.addTab("Loan Origination", jPanel3);

        jDesktopPane4.setBackground(java.awt.SystemColor.activeCaption);
        jDesktopPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTabbedPane2.setBackground(java.awt.SystemColor.activeCaption);
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane2MousePressed(evt);
            }
        });
        jTabbedPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane2KeyPressed(evt);
            }
        });

        jPanel4.setBackground(java.awt.SystemColor.activeCaption);
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel4.setLayout(null);

        topUpPanel.setBackground(java.awt.SystemColor.activeCaption);
        topUpPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Total Loan Amount Qualified:");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel1);
        jLabel1.setBounds(450, 40, 250, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel2);
        jLabel2.setBounds(710, 80, 40, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Actual Take Home Amount");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel3);
        jLabel3.setBounds(450, 320, 250, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Princimpal Amount Paid");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel4);
        jLabel4.setBounds(450, 280, 250, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Remaining Number Instalments");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel5);
        jLabel5.setBounds(10, 120, 250, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Interest Rate");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel6);
        jLabel6.setBounds(10, 40, 250, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Princimpal Remaining");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel7);
        jLabel7.setBounds(450, 80, 250, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel8);
        jLabel8.setBounds(710, 40, 40, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel9);
        jLabel9.setBounds(710, 320, 40, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel11);
        jLabel11.setBounds(270, 120, 160, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel12);
        jLabel12.setBounds(10, 5, 860, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel13);
        jLabel13.setBounds(710, 280, 40, 30);

        jButton12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton12.setText("Decline");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        topUpPanel.add(jButton12);
        jButton12.setBounds(480, 380, 130, 48);

        jButton13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton13.setText("Proceed");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        topUpPanel.add(jButton13);
        jButton13.setBounds(350, 380, 130, 48);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Number Instalments Paid");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel14);
        jLabel14.setBounds(10, 160, 250, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Loan Tenure");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel16);
        jLabel16.setBounds(10, 240, 250, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel19);
        jLabel19.setBounds(270, 160, 160, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Number Instalments In Arrears");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel20);
        jLabel20.setBounds(10, 200, 250, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel21);
        jLabel21.setBounds(270, 200, 160, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Total Loan Amount In Arrears");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel22);
        jLabel22.setBounds(10, 80, 250, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel24);
        jLabel24.setBounds(270, 80, 40, 30);

        jFormattedTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField1);
        jFormattedTextField1.setBounds(750, 80, 120, 30);
        jFormattedTextField1.setEditable(false);

        jFormattedTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField2);
        jFormattedTextField2.setBounds(750, 280, 120, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField4);
        jFormattedTextField4.setBounds(310, 80, 120, 30);
        jFormattedTextField4.setEditable(false);

        jCheckBox2.setToolTipText("Check to adjust the takehome amount");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox2);
        jCheckBox2.setBounds(870, 320, 21, 30);

        jCheckBox3.setToolTipText("Check to adjust the loan tenure");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox3);
        jCheckBox3.setBounds(430, 240, 21, 30);

        jTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jTextField1);
        jTextField1.setBounds(270, 240, 160, 30);
        jTextField1.setEditable(false);

        jTextField3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jTextField3);
        jTextField3.setBounds(750, 40, 120, 30);
        jTextField3.setEditable(false);

        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jTextField2);
        jTextField2.setBounds(750, 320, 120, 30);
        jTextField2.setEditable(false);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("Outstanding Loan Penalty");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel32);
        jLabel32.setBounds(450, 200, 250, 30);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel33);
        jLabel33.setBounds(710, 240, 40, 30);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel34);
        jLabel34.setBounds(710, 200, 40, 30);

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Interest  Remaining");
        jLabel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel35);
        jLabel35.setBounds(450, 120, 250, 30);

        jLabel40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel40.setText("Outstanding Accumulated Interest");
        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel40);
        jLabel40.setBounds(450, 240, 250, 30);

        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel44);
        jLabel44.setBounds(710, 120, 40, 30);

        jCheckBox5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox5.setText("O/S Interest");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox5);
        jCheckBox5.setBounds(330, 280, 110, 30);

        jCheckBox6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox6.setText("Interest");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox6);
        jCheckBox6.setBounds(70, 280, 80, 30);

        jFormattedTextField3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField3);
        jFormattedTextField3.setBounds(750, 200, 120, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField5);
        jFormattedTextField5.setBounds(750, 120, 120, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField6);
        jFormattedTextField6.setBounds(750, 240, 120, 30);
        jFormattedTextField2.setEditable(false);

        jLabel88.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel88.setText("Include:");
        jLabel88.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel88);
        jLabel88.setBounds(10, 280, 60, 30);

        jCheckBox11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox11.setText("Penalty");
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox11);
        jCheckBox11.setBounds(150, 280, 73, 30);

        jCheckBox12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox12.setText("Accu Interest");
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox12ActionPerformed(evt);
            }
        });
        topUpPanel.add(jCheckBox12);
        jCheckBox12.setBounds(220, 280, 110, 30);

        jLabel89.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel89.setText("Outstanding Interest");
        jLabel89.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel.add(jLabel89);
        jLabel89.setBounds(450, 160, 250, 30);

        jLabel90.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel90.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel.add(jLabel90);
        jLabel90.setBounds(710, 160, 40, 30);

        jFormattedTextField13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jFormattedTextField13);
        jFormattedTextField13.setBounds(750, 160, 120, 30);
        jFormattedTextField2.setEditable(false);

        jTextField10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel.add(jTextField10);
        jTextField10.setBounds(270, 40, 160, 30);

        jPanel4.add(topUpPanel);
        topUpPanel.setBounds(0, 0, 1150, 590);

        jTabbedPane2.addTab("TopUp Loan", jPanel4);

        jPanel5.setBackground(java.awt.SystemColor.activeCaption);
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel5.setLayout(null);

        topUpPanel1.setBackground(java.awt.SystemColor.activeCaption);
        topUpPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.setLayout(null);

        jLabel56.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel56.setText("Total Princimpal Loan Qualified");
        jLabel56.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel56);
        jLabel56.setBounds(450, 40, 250, 30);

        jLabel57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel57.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel57);
        jLabel57.setBounds(270, 40, 50, 30);

        jLabel64.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel64.setText("Actual Take Home Amount");
        jLabel64.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel64);
        jLabel64.setBounds(450, 80, 250, 30);

        jLabel65.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel65.setText("Princimpal Amount Paid");
        jLabel65.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel65);
        jLabel65.setBounds(10, 80, 250, 30);

        jLabel66.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel66.setText("Remaining Number Instalments");
        jLabel66.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel66);
        jLabel66.setBounds(10, 120, 250, 30);

        jLabel67.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel67.setText("Interest Rate");
        jLabel67.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel67);
        jLabel67.setBounds(450, 160, 250, 30);

        jLabel68.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel68.setText("Remaining Princimpal Amount");
        jLabel68.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel68);
        jLabel68.setBounds(10, 40, 250, 30);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel69);
        jLabel69.setBounds(710, 40, 40, 30);

        jLabel70.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel70.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel70);
        jLabel70.setBounds(710, 80, 40, 30);

        jLabel71.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel71.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel71);
        jLabel71.setBounds(270, 120, 160, 30);

        jLabel72.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel72.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel72.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel72);
        jLabel72.setBounds(10, 5, 830, 30);

        jLabel73.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel73.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel73);
        jLabel73.setBounds(270, 80, 50, 30);

        jButton14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton14.setText("Decline");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        topUpPanel1.add(jButton14);
        jButton14.setBounds(408, 396, 130, 48);

        jButton15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton15.setText("Proceed");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        topUpPanel1.add(jButton15);
        jButton15.setBounds(276, 396, 130, 48);

        jLabel74.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel74.setText("Number Instalments Paid");
        jLabel74.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel74);
        jLabel74.setBounds(10, 160, 250, 30);

        jLabel75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel75.setText("Loan Tenure");
        jLabel75.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel75);
        jLabel75.setBounds(450, 120, 250, 30);

        jLabel76.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel76.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel76);
        jLabel76.setBounds(710, 160, 160, 30);

        jLabel77.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel77.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel77);
        jLabel77.setBounds(270, 160, 160, 30);

        jLabel78.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel78.setText("Number Instalments In Arrears");
        jLabel78.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel78);
        jLabel78.setBounds(10, 200, 250, 30);

        jLabel79.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel79.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel79);
        jLabel79.setBounds(270, 200, 160, 30);

        jLabel80.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel80.setText("Total Instalment Amount In Arrears");
        jLabel80.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel80);
        jLabel80.setBounds(450, 200, 250, 30);

        jLabel81.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel81.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel81);
        jLabel81.setBounds(710, 200, 40, 30);

        jFormattedTextField7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField7);
        jFormattedTextField7.setBounds(320, 40, 110, 30);
        jFormattedTextField1.setEditable(false);

        jFormattedTextField8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField8);
        jFormattedTextField8.setBounds(320, 80, 110, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField9);
        jFormattedTextField9.setBounds(750, 200, 120, 30);
        jFormattedTextField4.setEditable(false);

        jCheckBox7.setToolTipText("Check to adjust the takehome amount");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });
        topUpPanel1.add(jCheckBox7);
        jCheckBox7.setBounds(870, 80, 21, 30);

        jCheckBox8.setToolTipText("Check to adjust the loan tenure");
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });
        topUpPanel1.add(jCheckBox8);
        jCheckBox8.setBounds(870, 120, 21, 30);

        jTextField7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jTextField7);
        jTextField7.setBounds(710, 120, 160, 30);
        jTextField1.setEditable(false);

        jTextField8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jTextField8);
        jTextField8.setBounds(750, 40, 120, 30);
        jTextField3.setEditable(false);

        jTextField9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jTextField9);
        jTextField9.setBounds(750, 80, 120, 30);
        jTextField2.setEditable(false);

        jLabel82.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel82.setText("Charges On Outstanding Instalments");
        jLabel82.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel82);
        jLabel82.setBounds(450, 240, 250, 30);

        jLabel83.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel83.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel83);
        jLabel83.setBounds(270, 280, 50, 30);

        jLabel84.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel84.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel84);
        jLabel84.setBounds(710, 240, 40, 30);

        jLabel85.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel85.setText("Outstanding Princimpal");
        jLabel85.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel85);
        jLabel85.setBounds(10, 240, 250, 30);

        jLabel86.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel86.setText("Outstanding Interest");
        jLabel86.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topUpPanel1.add(jLabel86);
        jLabel86.setBounds(10, 280, 250, 30);

        jLabel87.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel87.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topUpPanel1.add(jLabel87);
        jLabel87.setBounds(270, 240, 50, 30);

        jCheckBox9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox9.setText("Include Outstanding Charges");
        topUpPanel1.add(jCheckBox9);
        jCheckBox9.setBounds(680, 280, 209, 30);

        jCheckBox10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox10.setText("Include Outstanding Interest");
        topUpPanel1.add(jCheckBox10);
        jCheckBox10.setBounds(450, 280, 230, 30);

        jFormattedTextField10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField10);
        jFormattedTextField10.setBounds(750, 240, 120, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField11);
        jFormattedTextField11.setBounds(320, 240, 110, 30);
        jFormattedTextField2.setEditable(false);

        jFormattedTextField12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        topUpPanel1.add(jFormattedTextField12);
        jFormattedTextField12.setBounds(320, 280, 110, 30);
        jFormattedTextField2.setEditable(false);

        jPanel5.add(topUpPanel1);
        topUpPanel1.setBounds(0, 0, 950, 500);

        jTabbedPane2.addTab("Reschedule Loan", jPanel5);

        jDesktopPane4.add(jTabbedPane2);
        jTabbedPane2.setBounds(0, 0, 1160, 620);

        jTabbedPane1.addTab("PostLoan Operations", jDesktopPane4);
        jDesktopPane4.setVisible(false);

        jPanel2.add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 1170, 650);
        jTabbedPane1.setVisible(false);

        jDesktopPane1.setBackground(java.awt.SystemColor.activeCaption);
        jDesktopPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jDesktopPane1.add(jScrollPane3);
        jScrollPane3.setBounds(24, 12, 1080, 540);

        jButton19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton19.setText("Print");
        jButton19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jDesktopPane1.add(jButton19);
        jButton19.setBounds(120, 560, 110, 48);

        jButton18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton18.setText("Export Excel");
        jButton18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jDesktopPane1.add(jButton18);
        jButton18.setBounds(230, 560, 110, 48);

        jLabel48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Search By:");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jDesktopPane1.add(jLabel48);
        jLabel48.setBounds(340, 560, 80, 48);

        jTextField6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(51, 0, 51));
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
        });
        jDesktopPane1.add(jTextField6);
        jTextField6.setBounds(430, 560, 360, 48);
        jTextField1.setText("");

        jPanel2.add(jDesktopPane1);
        jDesktopPane1.setBounds(0, -20, 1170, 670);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(134, 42, 1170, 650);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1350, 940);

        jButton48.setBackground(new java.awt.Color(201, 222, 223));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1760, 420, 53, 23);

        jButton45.setBackground(new java.awt.Color(255, 255, 204));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1780, 420, 53, 23);

        jButton46.setBackground(java.awt.SystemColor.activeCaption);
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1770, 420, 53, 23);

        jButton41.setBackground(java.awt.SystemColor.activeCaption);
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(1770, 420, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 204, 255));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(1770, 420, 53, 23);

        jButton43.setBackground(new java.awt.Color(204, 204, 204));
        jButton43.setText("Blue");
        getContentPane().add(jButton43);
        jButton43.setBounds(1770, 420, 53, 23);

        jButton47.setBackground(new java.awt.Color(204, 255, 204));
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1770, 420, 53, 23);

        jButton44.setBackground(new java.awt.Color(204, 165, 165));
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(1770, 420, 53, 23);

        jButton42.setBackground(new java.awt.Color(0, 204, 102));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(1770, 420, 53, 23);

        jButton29.setBackground(new java.awt.Color(0, 204, 204));
        jButton29.setText("Blue");
        getContentPane().add(jButton29);
        jButton29.setBounds(1770, 420, 53, 23);

        jButton40.setBackground(java.awt.SystemColor.activeCaption);
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(1770, 420, 53, 23);

        jButton38.setBackground(new java.awt.Color(204, 204, 0));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(1770, 420, 53, 23);

        jButton37.setBackground(new java.awt.Color(204, 153, 255));
        jButton37.setText("Blue");
        getContentPane().add(jButton37);
        jButton37.setBounds(1770, 420, 53, 23);

        jButton35.setBackground(new java.awt.Color(255, 204, 204));
        jButton35.setText("Blue");
        getContentPane().add(jButton35);
        jButton35.setBounds(1770, 420, 53, 23);

        jButton34.setBackground(new java.awt.Color(0, 204, 255));
        jButton34.setText("Blue");
        getContentPane().add(jButton34);
        jButton34.setBounds(1770, 420, 53, 23);

        jButton32.setBackground(new java.awt.Color(0, 153, 153));
        jButton32.setText("Blue");
        getContentPane().add(jButton32);
        jButton32.setBounds(1770, 420, 53, 23);

        jButton33.setBackground(new java.awt.Color(152, 198, 94));
        jButton33.setText("Blue");
        getContentPane().add(jButton33);
        jButton33.setBounds(1760, 420, 53, 23);

        jButton36.setBackground(java.awt.SystemColor.activeCaption);
        jButton36.setText("Blue");
        getContentPane().add(jButton36);
        jButton36.setBounds(1780, 420, 53, 23);

        jButton30.setBackground(new java.awt.Color(0, 153, 255));
        jButton30.setText("Blue");
        getContentPane().add(jButton30);
        jButton30.setBounds(1770, 420, 53, 23);

        jButton31.setBackground(new java.awt.Color(255, 204, 153));
        jButton31.setText("Blue");
        getContentPane().add(jButton31);
        jButton31.setBounds(1770, 420, 53, 23);

        jButton49.setBackground(new java.awt.Color(255, 204, 204));
        jButton49.setText("jButton5");
        getContentPane().add(jButton49);
        jButton49.setBounds(1750, 390, 73, 23);

        jButton50.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton50.setText("jButton5");
        getContentPane().add(jButton50);
        jButton50.setBounds(1750, 390, 73, 23);

        jButton51.setBackground(new java.awt.Color(0, 51, 51));
        jButton51.setText("jButton5");
        getContentPane().add(jButton51);
        jButton51.setBounds(1760, 390, 73, 23);

        jMenu3.setText("Return To Loan Management");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });
        jMenu3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenu3KeyPressed(evt);
            }
        });

        jRadioButtonMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_NUM_LOCK, 0));
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Return To Loan Managment");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
     
              LoanManagmentWindow frm5 = new LoanManagmentWindow(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
        this.dispose();
        jComboBox3.setEditable(true);

    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenu3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenu3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3KeyPressed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jCheckBox48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox48ActionPerformed
     
        if(!"".equals( jTextField18.getText())){
            
        if(!jComboBox8.getSelectedItem().toString().equals(jComboBox4.getSelectedItem().toString())){
        
        JOptionPane.showMessageDialog(this, "Both the Max and Required Tenure must be measured in the same time value");
        jCheckBox48.setSelected(false);
            jTextField18.setText("");
            jComboBox4.setSelectedItem(jComboBox8.getSelectedItem().toString());
        }else{    
            
            if((parseInt(jTextField36.getText())<parseInt(jTextField18.getText()))){
            
            JOptionPane.showMessageDialog(this, "You cannot set Tenure which is more than the Maxmum Loan Tenure allowed");
            jCheckBox48.setSelected(false);
            jTextField18.setText("");
            } else{
             if(jCheckBox48.isSelected()==true){
                 jComboBox4.setEditable(false);
     jTextField18.setEditable(false);
     jTextField36.setEnabled(false);
jCheckBox49.setEnabled(false);
  jCheckBox49.setSelected(false);
     jComboBox8.setEnabled(false);
     }else if(jCheckBox50.isSelected()==false){
       jTextField18.setEditable(true);
     jTextField36.setEnabled(true);
jCheckBox49.setEnabled(true);
jComboBox4.setEditable(true);
     jComboBox8.setEnabled(true);
     }
      
            } } }else{
            JOptionPane.showMessageDialog(this, "Please enter the amount required for borrowing in the field");
      jCheckBox48.setSelected(false);
       }
    }//GEN-LAST:event_jCheckBox48ActionPerformed

    private void jCheckBox49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox49ActionPerformed
    
        if(jTextField36.getText().equals("")||jComboBox8.getSelectedItem().equals("")){
         JOptionPane.showMessageDialog(this, "Please Select The Account Number Of The Loan Applicant");
      jCheckBox49.setSelected(false);
       }else{ if(jCheckBox49.isSelected()==true){
     
     jTextField18.setEnabled(false);
jComboBox4.setEnabled(false);
jCheckBox48.setEnabled(false);
   jCheckBox48.setSelected(false);
     jTextField18.setText("");
     jComboBox4.setSelectedItem("");
     }else if(jCheckBox49.isSelected()==false){
      jTextField18.setEnabled(true);
jComboBox4.setEnabled(true);
jCheckBox48.setEnabled(true);

     }}
    }//GEN-LAST:event_jCheckBox49ActionPerformed

    private void jCheckBox50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox50ActionPerformed
   
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        if(null != amountFieldDebit7.getValue()){
            if(parseDouble(amountFieldDebit8.getValue().toString())<parseDouble(amountFieldDebit7.getValue().toString())){
            JOptionPane.showMessageDialog(this, "You cannot Borrow an amount which is more than the maximum allowed  Loan Amount");
            amountFieldDebit7.setValue(null);
            jCheckBox50.setSelected(false);
            } else{
             if(jCheckBox50.isSelected()==true){
     amountFieldDebit7.setEditable(false);
     amountFieldDebit8.setEnabled(false);
jCheckBox51.setEnabled(false);
   jCheckBox51.setSelected(false);
   
   Integer u=12;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "useMe"+borrowerAccount+".txt"), u.toString());
     
     }else if(jCheckBox50.isSelected()==false){
      amountFieldDebit7.setEditable(true);
      amountFieldDebit8.setEnabled(true);

jCheckBox51.setEnabled(true);
Integer u=11;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "useMe"+borrowerAccount+".txt"), u.toString());
     }
      
        }
        }else{
            
            JOptionPane.showMessageDialog(this, "Please enter the amount required for borrowing in the field");
            
      jCheckBox50.setSelected(false);
      
       }
        
    }//GEN-LAST:event_jCheckBox50ActionPerformed

    private void jCheckBox51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox51ActionPerformed
    
            if(jCheckBox51.isSelected()==true){
              amountFieldDebit7.setValue(null);
     amountFieldDebit7.setEnabled(false);
jCheckBox50.setEnabled(false);
 jCheckBox50.setSelected(false);
     
     }else if(jCheckBox51.isSelected()==false){
    
   amountFieldDebit7.setEnabled(true);
jCheckBox50.setEnabled(true);

     }
    }//GEN-LAST:event_jCheckBox51ActionPerformed

    private void amountFieldDebit7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit7ActionPerformed

    private void amountFieldDebit8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit8ActionPerformed

    private void jTextField36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField36ActionPerformed
  
     
    }//GEN-LAST:event_jTextField36ActionPerformed

    private void jCheckBox49AncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jCheckBox49AncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox49AncestorRemoved

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if(dbq.newLedgerCreated()){
            
JOptionPane.showMessageDialog(this, "A NEW LEDGER HAS BEEN CREATED\n\nPLEASE FIRST ACTIVATE IT BEFORE YOU PROCEED");

return;

}else{  
            
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt"), ""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt")))+1));

        String accountNumber2=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));  
        
        if(dbq.testTable("bsanca"+accountNumber2)){
            
        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
            
        if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "guarantorSetupLoan"+accountNumber2+ ".txt"))==23){
       
            JOptionPane.showMessageDialog(this, "Please First Set Up The Guarantors Before You Proceed");return;
        }
        }
        
        
        
        if(parseDouble(amountFieldDebit8.getValue().toString())<=0.0){
            
        JOptionPane.showMessageDialog(this, "The System cannot amortize the amount BECAUSE it is less than one");return;
        }

        
        
        
        if(jTextField35.getText().equals("")){
        JOptionPane.showMessageDialog(this, "Please input the payment source");
        return;
        }


//        if((jCheckBox49.isSelected()==true&&jCheckBox51.isSelected()==true)||(jCheckBox48.isSelected()==true&&jCheckBox50.isSelected()==true)||jCheckBox49.isSelected()==true&&jCheckBox50.isSelected()==true||jCheckBox48.isSelected()==true&&jCheckBox51.isSelected()==true){
       
            
            if(loan.loanExists(accountNumber2)==true){
        JOptionPane.showMessageDialog(this, jComboBox3.getSelectedItem().toString()+" " +"Has already applied for a loan");
        return;
        }else{

       

        if(jCheckBox49.isSelected()==true&&jCheckBox51.isSelected()==true){

        fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

        int n=1; String var="";
        while(n<=7){
        if(n==1){var=accountNumber2;}//Account Number
        if(n==2){var=jComboBox3.getSelectedItem().toString();}//Account Name
        if(n==3){var=jTextField36.getText();}//Actual loan Tenure
        if(n==4){var=jComboBox8.getSelectedItem().toString();}//Type of loan Tenure
        if(n==5){var=amountFieldDebit8.getValue().toString();}//Amount Applied for
        if(n==6){var=jTextField35.getText();}//Payment Source
        if(n==7){var=jComboBox2.getSelectedItem().toString();}//Loan Purpose

        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), var);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        n++;
        }

completeAmortSpace(accountNumber2);

        }else if(jCheckBox48.isSelected()==true&&jCheckBox50.isSelected()==true){

        fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        int n=1; String var="";
        while(n<=7){
        if(n==1){var=accountNumber2;}
        if(n==2){var=jComboBox3.getSelectedItem().toString();}
        if(n==3){var=jTextField18.getText();}
        if(n==4){var=jComboBox4.getSelectedItem().toString();}
        if(n==5){var=amountFieldDebit7.getValue().toString();}
        if(n==6){var=jTextField35.getText();}
        if(n==7){var=jComboBox2.getSelectedItem().toString();}
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), var);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        n++;
        }
     completeAmortSpace(accountNumber2);
        }else if(jCheckBox49.isSelected()==true&&jCheckBox50.isSelected()==true){

        fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        int n=1; String var="";
        while(n<=7){
        if(n==1){var=accountNumber2;}//Account Number
        if(n==2){var=jComboBox3.getSelectedItem().toString();}//Account Name
        if(n==3){var=jTextField36.getText();}//Loan Tenure
        if(n==4){var=jComboBox8.getSelectedItem().toString();}//period Type
        if(n==5){var=amountFieldDebit7.getValue().toString();}//Principal Amount
        if(n==6){var=jTextField35.getText();}//Repayment source
        if(n==7){var=jComboBox2.getSelectedItem().toString();}//Loan usage/Purpose
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), var);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        n++;       



        }
completeAmortSpace(accountNumber2);
        }else if(jCheckBox48.isSelected()==true&&jCheckBox51.isSelected()==true){

        fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
        int n=1; String var="";
        while(n<=7){
        if(n==1){var=accountNumber2;}
        if(n==2){var=jComboBox3.getSelectedItem().toString();}
        if(n==3){var=jTextField18.getText();}
        if(n==4){var=jComboBox4.getSelectedItem().toString();}
        if(n==5){var=amountFieldDebit8.getValue().toString();}
        if(n==6){var=jTextField35.getText();}
        if(n==7){var=jComboBox2.getSelectedItem().toString();}
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), var);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        n++;       





        }
completeAmortSpace(accountNumber2);
        }else{
            
            JOptionPane.showMessageDialog(this, "Please select the loan tenure and loan amount required");
            
            return;
        
        }    
  } 
        
        
//        
//        }
      
  }else{
        
        JOptionPane.showMessageDialog(this, "NO ACTIVE ACCOUNT FOUND");
        
       
        
        

  
        }
  
        
}
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void completeAmortSpace(String accountNumber2){
      Integer xh=1;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), xh.toString());

//        SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
//
//
//        @Override
//        protected Object doInBackground() throws Exception {
//        loan.createNewLoanAmort(accountNumber2,NewLoanApplication.this);
//        return null;
//        }
//        };
//        createNewLoan.execute();
        
       if(!useDefaultBorrowerDetails.isSelected()){
       
           if(updateExistingBorrowersDetails.isVisible()){
           
               if(updateExistingBorrowersDetails.isSelected()){
               
               if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Individual")){
               if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }
               
               }
                   
                   
               
               }
                   
                   
               
               }else if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Group")){
               
               
                   if(jCheckBox4.isSelected()){
                   
                        if(jTextField5.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField5.getText().equalsIgnoreCase("Select Group Id")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("Select")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }
               
               }
                   
                   
               
               }
                       
                       
               
            }
            
            }
            
            }
            
            }
                       
                       
                       
                       
                   
                   }else if(!jCheckBox4.isSelected()){
                   
                       if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }
               
               }
                   
                   
               
               }
                       
                       
                       
                       
                   
                   
                   }
                   
                   
                   
               
               }
               
               }else if(!updateExistingBorrowersDetails.isSelected()){
                 
                   addItemInList("Existing Borrower");
       
       List theExistingItems=loan.otherLoanDetailsForUpate(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));
         
       addItemInList(theExistingItems.get(0).toString());
         addItemInList(theExistingItems.get(1).toString());
          addItemInList(theExistingItems.get(2).toString());
           addItemInList(theExistingItems.get(3).toString());
      
               addItemInList(theExistingItems.get(4).toString());
         addItemInList(theExistingItems.get(5).toString());
          addItemInList(theExistingItems.get(6).toString());
           addItemInList(theExistingItems.get(7).toString());
            addItemInList(theExistingItems.get(8).toString());
         addItemInList(theExistingItems.get(9).toString());
          addItemInList(theExistingItems.get(10).toString());
           addItemInList(theExistingItems.get(11).toString());
           
            addItemInList(theExistingItems.get(12).toString());
         addItemInList(theExistingItems.get(13).toString());
          addItemInList(theExistingItems.get(14).toString());
           addItemInList(theExistingItems.get(15).toString());
            addItemInList(theExistingItems.get(16).toString());
         addItemInList(theExistingItems.get(17).toString());
          addItemInList(theExistingItems.get(18).toString());
           addItemInList(theExistingItems.get(19).toString());
           
            addItemInList(theExistingItems.get(20).toString());
           addItemInList(theExistingItems.get(21).toString());
            addItemInList(theExistingItems.get(22).toString());
         addItemInList(theExistingItems.get(23).toString());
          addItemInList(theExistingItems.get(24).toString());
           addItemInList(theExistingItems.get(25).toString());
          
           
                     AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();
            }
               
               
               
           
           }else if(!updateExistingBorrowersDetails.isVisible()){
            if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Individual")){
               if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }
               
               }
                   
                   
               
               }
                   
                   
               
               }else if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Group")){
               
               
                   if(jCheckBox4.isSelected()){
                   
                        if(jTextField5.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField5.getText().equalsIgnoreCase("Select Group Id")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("Select")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }
               
               }
                   
                   
               
               }
                       
                       
               
            }
            
            }
            
            }
            
            }
                       
                       
                       
                       
                   
                   }else if(!jCheckBox4.isSelected()){
                   
                       if(jButton20.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select other loan details");
               return;
               }else if(!jButton20.isEnabled()){
               
                     if(jButton16.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Economic status details");
               return;
               }else if(!jButton16.isEnabled()){
                    if(jButton22.isEnabled()){
               JOptionPane.showMessageDialog(this, "Please first select Bio Data details");
               return;
               }else if(!jButton22.isEnabled()){

            AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();  
                   
               
               }   }  } }  }  }
       
       
       }else if(useDefaultBorrowerDetails.isSelected()){

           
           if(loan.borrowerExists(accountNumber2)){
                theCollection=new ArrayList();
            addItemInList("Existing Borrower");
       
       List theExistingItems=loan.otherLoanDetailsForUpate(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));
         
       addItemInList(theExistingItems.get(0).toString());
         addItemInList(theExistingItems.get(1).toString());
          addItemInList(theExistingItems.get(2).toString());
           addItemInList(theExistingItems.get(3).toString());
      
               addItemInList(theExistingItems.get(4).toString());
         addItemInList(theExistingItems.get(5).toString());
          addItemInList(theExistingItems.get(6).toString());
           addItemInList(theExistingItems.get(7).toString());
            addItemInList(theExistingItems.get(8).toString());
         addItemInList(theExistingItems.get(9).toString());
          addItemInList(theExistingItems.get(10).toString());
           addItemInList(theExistingItems.get(11).toString());
           
            addItemInList(theExistingItems.get(12).toString());
         addItemInList(theExistingItems.get(13).toString());
          addItemInList(theExistingItems.get(14).toString());
           addItemInList(theExistingItems.get(15).toString());
            addItemInList(theExistingItems.get(16).toString());
         addItemInList(theExistingItems.get(17).toString());
          addItemInList(theExistingItems.get(18).toString());
           addItemInList(theExistingItems.get(19).toString());
           
            addItemInList(theExistingItems.get(20).toString());
           addItemInList(theExistingItems.get(21).toString());
            addItemInList(theExistingItems.get(22).toString());
         addItemInList(theExistingItems.get(23).toString());
          addItemInList(theExistingItems.get(24).toString());
           addItemInList(theExistingItems.get(25).toString());
          
           
                     AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();
           
           
           }else {
           
           
           
           
           
           
           
        AmotizationCalculator f = new  AmotizationCalculator(userId,theCollection);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 
           }
       }
        
    }
    
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        fios.stringFileWriter(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovalsAccountAp.txt"), fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt")));
        
        
        
        loanApprovalsMaker f = new  loanApprovalsMaker(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        theCollection=new ArrayList();
        
      
        
        
        
        
        if(loan.loanExists(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt")))){
        
        jTabbedPane1.setEnabled(true);
        }
        
        
        dbq.fillMeWithNonBorrowingCustomers(jTable3);


HeaderRenderer header = new HeaderRenderer(jTable3.getTableHeader().getDefaultRenderer());   
        
        int h1=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable3.getColumnModel().getColumnCount()){
        jTable3.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable3.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable3.getColumnModel().getColumn(h1).setPreferredWidth(100);

        }
       
       if(h1==1){
        jTable3.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable3.getColumnModel().getColumn(h1).setPreferredWidth(400);

        }
        if(h1==2){
        jTable3.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable3.getColumnModel().getColumn(h1).setPreferredWidth(200);

        }
         if(h1==3){
        jTable3.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable3.getColumnModel().getColumn(h1).setPreferredWidth(300);

        }
          if(h1==4){
        jTable3.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(h1).setMaxWidth(1500);
        jTable3.getColumnModel().getColumn(h1).setPreferredWidth(200);

        }
        h1++;

        }
        jTable3.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
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
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton50.getBackground());
        setForeground(jButton51.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
// DecisionTopUpImp decideTopUp=new DecisionTopUpImp(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));
// topUpDatabaseImp topupData=new topUpDatabaseImp(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));   
if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){

//   if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+accountNumber+".txt"))==10){
//                            jPanel2.setVisible(true);
//                            guarantorSetup.setVisible(false);
//                            jPanel3.setVisible(false);
//
//
//                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+accountNumber+".txt"))==9){
//
//                            jPanel2.setVisible(false);
//                            guarantorSetup.setVisible(true);
//                            jPanel3.setVisible(false);
//
//                            } 
    
                dbq.feelWithCustomersNames(jComboBox10,accountNumber);
                dbq.feelWithCustomersNames(jComboBox1,accountNumber);
                dbq.feelWithCustomersNames(jComboBox6,accountNumber);
                dbq.feelWithCustomersNames(jComboBox7,accountNumber);
                dbq.feelWithCustomersNames(jComboBox9,accountNumber);



int numbGuaU= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                switch(numbGuaU){
                case 1:
                submitButton1.setVisible(true);
                refreshButton1.setVisible(true);
                submitButton2.setVisible(false);
                refreshButton2.setVisible(false); 
                gnamwLabel66.setVisible(true);
                gacnoLabel67.setVisible(true);
                firstCheckBox1.setVisible(true);
                jComboBox10.setVisible(true);
                guarantoursAccount10.setVisible(true);
                guarantoursAccount5.setVisible(true);

                    gballowedLabel65.setVisible(false); 
                    ggallowedLabel70.setVisible(false); 
                    gallowamtLabel68.setVisible(false); 
                    bneedsLabel64.setVisible(false); 
                    bhasLabel71.setVisible(false); 
                    actionLabel69.setVisible(false); 
                    firstCheckBox1.setVisible(true);
                    secondCheckBox1.setVisible(false); 
                    thirdCheckBox1.setVisible(false); 
                    fourthCheckBox1.setVisible(false); 
                    fifthCheckBox1.setVisible(false); 
                break;
                case 2:
               submitButton1.setVisible(true);
                refreshButton1.setVisible(true);
                submitButton2.setVisible(false);
                refreshButton2.setVisible(false); 
                gnamwLabel66.setVisible(true);
                gacnoLabel67.setVisible(true);
                firstCheckBox1.setVisible(true);
                secondCheckBox1.setVisible(true);
                jComboBox10.setVisible(true);
                guarantoursAccount10.setVisible(true);
                guarantoursAccount5.setVisible(true);

                jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);
                    gballowedLabel65.setVisible(false); 
                    ggallowedLabel70.setVisible(false); 
                    gallowamtLabel68.setVisible(false); 
                    bneedsLabel64.setVisible(false); 
                    bhasLabel71.setVisible(false); 
                    actionLabel69.setVisible(false); 
                      firstCheckBox1.setVisible(true);
                    secondCheckBox1.setVisible(true); 
                    thirdCheckBox1.setVisible(false); 
                    fourthCheckBox1.setVisible(false); 
                    fifthCheckBox1.setVisible(false); 
                break;
                case 3:
               submitButton1.setVisible(true);
                refreshButton1.setVisible(true);
                submitButton2.setVisible(false);
                refreshButton2.setVisible(false); 
                gnamwLabel66.setVisible(true);
                gacnoLabel67.setVisible(true);
                firstCheckBox1.setVisible(true);
                secondCheckBox1.setVisible(true);
                thirdCheckBox1.setVisible(true);
                jComboBox10.setVisible(true);
                guarantoursAccount10.setVisible(true);
                guarantoursAccount5.setVisible(true);

                jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);

                jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
  gballowedLabel65.setVisible(false); 
                    ggallowedLabel70.setVisible(false); 
                    gallowamtLabel68.setVisible(false); 
                    bneedsLabel64.setVisible(false); 
                    bhasLabel71.setVisible(false); 
                    actionLabel69.setVisible(false); 
                    firstCheckBox1.setVisible(true);
                    secondCheckBox1.setVisible(true); 
                    thirdCheckBox1.setVisible(true); 
                    fourthCheckBox1.setVisible(false); 
                    fifthCheckBox1.setVisible(false); 
                break;
                case 4:
                submitButton1.setVisible(true);
                refreshButton1.setVisible(true);
                submitButton2.setVisible(false);
                refreshButton2.setVisible(false); 
                gnamwLabel66.setVisible(true);
                gacnoLabel67.setVisible(true);
                firstCheckBox1.setVisible(true);
                secondCheckBox1.setVisible(true);
                thirdCheckBox1.setVisible(true);
                fourthCheckBox1.setVisible(true);
                jComboBox10.setVisible(true);
                guarantoursAccount10.setVisible(true);
                guarantoursAccount5.setVisible(true);

                jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);

                jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);

                jComboBox7.setVisible(true);
                guarantoursAccount13.setVisible(true);
                guarantoursAccount8.setVisible(true);
                  gballowedLabel65.setVisible(false); 
                    ggallowedLabel70.setVisible(false); 
                    gallowamtLabel68.setVisible(false); 
                    bneedsLabel64.setVisible(false); 
                    bhasLabel71.setVisible(false); 
                    actionLabel69.setVisible(false); 
                     firstCheckBox1.setVisible(true);
                    secondCheckBox1.setVisible(true); 
                    thirdCheckBox1.setVisible(true); 
                    fourthCheckBox1.setVisible(true); 
                    fifthCheckBox1.setVisible(false); 
                break;
                case 5:
                submitButton1.setVisible(true);
                refreshButton1.setVisible(true);
                submitButton2.setVisible(false);
                refreshButton2.setVisible(false); 
                gnamwLabel66.setVisible(true);
                gacnoLabel67.setVisible(true);
                jComboBox10.setVisible(true);
                firstCheckBox1.setVisible(true);
                secondCheckBox1.setVisible(true);
                thirdCheckBox1.setVisible(true);
                fourthCheckBox1.setVisible(true);
                fifthCheckBox1.setVisible(true);   
                guarantoursAccount10.setVisible(true);
                guarantoursAccount5.setVisible(true);

                jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);

                jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                jComboBox7.setVisible(true);
                guarantoursAccount13.setVisible(true);
                guarantoursAccount8.setVisible(true);   
                jComboBox9.setVisible(true);
                guarantoursAccount14.setVisible(true);
                guarantoursAccount9.setVisible(true);
                  gballowedLabel65.setVisible(false); 
                    ggallowedLabel70.setVisible(false); 
                    gallowamtLabel68.setVisible(false); 
                    bneedsLabel64.setVisible(false); 
                    bhasLabel71.setVisible(false); 
                    actionLabel69.setVisible(false); 
                       firstCheckBox1.setVisible(true);
                    secondCheckBox1.setVisible(true); 
                    thirdCheckBox1.setVisible(true); 
                    fourthCheckBox1.setVisible(true); 
                    fifthCheckBox1.setVisible(true); 
                break;  

                } 
                    
      if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){
    
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                            switch(numbGua){
                            case 1:
                            submitButton2.setVisible(true);
                            refreshButton2.setVisible(true);  
                            submitButton1.setVisible(false);
                            refreshButton1.setVisible(false); 

                            jComboBox10.setVisible(true);
                            guarantoursAccount10.setVisible(true);
                            guarantoursAccount5.setVisible(true);
                            amountFieldDebit2.setVisible(true);
                            amountFieldDebit9.setVisible(true);
                            amountFieldDebit14.setVisible(true);
                            amountFieldDebit19.setVisible(true);
                            firstCheckBox2.setVisible(true);
                            amountFieldDebit24.setVisible(true);
                            jButton8.setVisible(true);

                            gnamwLabel66.setVisible(true);
                            gacnoLabel67.setVisible(true);
                            gballowedLabel65.setVisible(true);
                            ggallowedLabel70.setVisible(true);
                            gallowamtLabel68.setVisible(true);
                            bneedsLabel64.setVisible(true);
                            bhasLabel71.setVisible(true);
                            actionLabel69.setVisible(true);
                            firstCheckBox1.setVisible(false); 
                            secondCheckBox1.setVisible(false); 
                            thirdCheckBox1.setVisible(false); 
                            fourthCheckBox1.setVisible(false); 
                            fifthCheckBox1.setVisible(false); 
                            break;
                            case 2:
                            submitButton2.setVisible(true);
                            refreshButton2.setVisible(true);  
                            submitButton1.setVisible(false);
                            refreshButton1.setVisible(false);  
                            jComboBox10.setVisible(true);
                            guarantoursAccount10.setVisible(true);
                            guarantoursAccount5.setVisible(true);
                            amountFieldDebit2.setVisible(true);
                            amountFieldDebit9.setVisible(true);
                            amountFieldDebit14.setVisible(true);
                            amountFieldDebit19.setVisible(true);
                            firstCheckBox2.setVisible(true);
                            amountFieldDebit24.setVisible(true);
                            jButton8.setVisible(true);
                            jComboBox1.setVisible(true);
                            guarantoursAccount11.setVisible(true);
                            guarantoursAccount6.setVisible(true);
                            amountFieldDebit3.setVisible(true);
                            amountFieldDebit10.setVisible(true);
                            amountFieldDebit15.setVisible(true);
                            amountFieldDebit20.setVisible(true);
                            secondCheckBox2.setVisible(true);
                            amountFieldDebit25.setVisible(true);
                            jButton9.setVisible(true);
                            gnamwLabel66.setVisible(true);
                            gacnoLabel67.setVisible(true);
                            gballowedLabel65.setVisible(true);
                            ggallowedLabel70.setVisible(true);
                            gallowamtLabel68.setVisible(true);
                            bneedsLabel64.setVisible(true);
                            bhasLabel71.setVisible(true);
                            actionLabel69.setVisible(true);
                            firstCheckBox1.setVisible(false); 
                            secondCheckBox1.setVisible(false); 
                            thirdCheckBox1.setVisible(false); 
                            fourthCheckBox1.setVisible(false); 
                            fifthCheckBox1.setVisible(false); 
                            break;
                            case 3:
                            submitButton2.setVisible(true);
                            refreshButton2.setVisible(true);  
                            submitButton1.setVisible(false);
                            refreshButton1.setVisible(false);   
                            jComboBox10.setVisible(true);
                            guarantoursAccount10.setVisible(true);
                            guarantoursAccount5.setVisible(true);
                            amountFieldDebit2.setVisible(true);
                            amountFieldDebit9.setVisible(true);
                            amountFieldDebit14.setVisible(true);
                            amountFieldDebit19.setVisible(true);
                            firstCheckBox2.setVisible(true);
                            amountFieldDebit24.setVisible(true);
                            jButton8.setVisible(true);
                            jComboBox1.setVisible(true);
                            guarantoursAccount11.setVisible(true);
                            guarantoursAccount6.setVisible(true);
                            amountFieldDebit3.setVisible(true);
                            amountFieldDebit10.setVisible(true);
                            amountFieldDebit15.setVisible(true);
                            amountFieldDebit20.setVisible(true);
                            secondCheckBox2.setVisible(true);
                            amountFieldDebit25.setVisible(true);
                            jButton9.setVisible(true);
                            jComboBox6.setVisible(true);
                            guarantoursAccount12.setVisible(true);
                            guarantoursAccount7.setVisible(true);
                            amountFieldDebit4.setVisible(true);
                            amountFieldDebit11.setVisible(true);
                            amountFieldDebit16.setVisible(true);
                            amountFieldDebit21.setVisible(true);
                            thirdCheckBox2.setVisible(true);
                            amountFieldDebit26.setVisible(true);
                            jButton10.setVisible(true);
                            gnamwLabel66.setVisible(true);
                            gacnoLabel67.setVisible(true);
                            gballowedLabel65.setVisible(true);
                            ggallowedLabel70.setVisible(true);
                            gallowamtLabel68.setVisible(true);
                            bneedsLabel64.setVisible(true);
                            bhasLabel71.setVisible(true);
                            actionLabel69.setVisible(true);
                            firstCheckBox1.setVisible(false); 
                            secondCheckBox1.setVisible(false); 
                            thirdCheckBox1.setVisible(false); 
                            fourthCheckBox1.setVisible(false); 
                            fifthCheckBox1.setVisible(false); 
                            break;
                            case 4:
                            submitButton2.setVisible(true);
                            refreshButton2.setVisible(true);  
                            submitButton1.setVisible(false);
                            refreshButton1.setVisible(false);   
                            jComboBox10.setVisible(true);
                            guarantoursAccount10.setVisible(true);
                            guarantoursAccount5.setVisible(true);
                            amountFieldDebit2.setVisible(true);
                            amountFieldDebit9.setVisible(true);
                            amountFieldDebit14.setVisible(true);
                            amountFieldDebit19.setVisible(true);
                            firstCheckBox2.setVisible(true);
                            amountFieldDebit24.setVisible(true);
                            jButton8.setVisible(true);
                            jComboBox1.setVisible(true);
                            guarantoursAccount11.setVisible(true);
                            guarantoursAccount6.setVisible(true);
                            amountFieldDebit3.setVisible(true);
                            amountFieldDebit10.setVisible(true);
                            amountFieldDebit15.setVisible(true);
                            amountFieldDebit20.setVisible(true);
                            secondCheckBox2.setVisible(true);
                            amountFieldDebit25.setVisible(true);
                            jButton9.setVisible(true);
                            jComboBox6.setVisible(true);
                            guarantoursAccount12.setVisible(true);
                            guarantoursAccount7.setVisible(true);
                            amountFieldDebit4.setVisible(true);
                            amountFieldDebit11.setVisible(true);
                            amountFieldDebit16.setVisible(true);
                            amountFieldDebit21.setVisible(true);
                            thirdCheckBox2.setVisible(true);
                            amountFieldDebit26.setVisible(true);
                            jButton10.setVisible(true);
                            jComboBox7.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            fourthCheckBox2.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            jButton11.setVisible(true);
                            gnamwLabel66.setVisible(true);
                            gacnoLabel67.setVisible(true);
                            gballowedLabel65.setVisible(true);
                            ggallowedLabel70.setVisible(true);
                            gallowamtLabel68.setVisible(true);
                            bneedsLabel64.setVisible(true);
                            bhasLabel71.setVisible(true);
                            actionLabel69.setVisible(true);
                            firstCheckBox1.setVisible(false); 
                            secondCheckBox1.setVisible(false); 
                            thirdCheckBox1.setVisible(false); 
                            fourthCheckBox1.setVisible(false); 
                            fifthCheckBox1.setVisible(false); 
                            break;
                            case 5:
                            submitButton2.setVisible(true);
                            refreshButton2.setVisible(true);  
                            submitButton1.setVisible(false);
                            refreshButton1.setVisible(false); 
                            jComboBox10.setVisible(true);
                            guarantoursAccount10.setVisible(true);
                            guarantoursAccount5.setVisible(true);
                            amountFieldDebit2.setVisible(true);
                            amountFieldDebit9.setVisible(true);
                            amountFieldDebit14.setVisible(true);
                            amountFieldDebit19.setVisible(true);
                            firstCheckBox2.setVisible(true);
                            amountFieldDebit24.setVisible(true);
                            jButton8.setVisible(true);
                            jComboBox1.setVisible(true);
                            guarantoursAccount11.setVisible(true);
                            guarantoursAccount6.setVisible(true);
                            amountFieldDebit3.setVisible(true);
                            amountFieldDebit10.setVisible(true);
                            amountFieldDebit15.setVisible(true);
                            amountFieldDebit20.setVisible(true);
                            secondCheckBox2.setVisible(true);
                            amountFieldDebit25.setVisible(true);
                            jButton9.setVisible(true);
                          jComboBox6.setVisible(true);
                            guarantoursAccount12.setVisible(true);
                            guarantoursAccount7.setVisible(true);
                            amountFieldDebit4.setVisible(true);
                            amountFieldDebit11.setVisible(true);
                            amountFieldDebit16.setVisible(true);
                            amountFieldDebit21.setVisible(true);
                            thirdCheckBox2.setVisible(true);
                            amountFieldDebit26.setVisible(true);
                            jButton10.setVisible(true);
                            jComboBox7.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            fourthCheckBox2.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            jButton11.setVisible(true);
                            jComboBox9.setVisible(true);
                            guarantoursAccount14.setVisible(true);
                            guarantoursAccount9.setVisible(true);
                            amountFieldDebit6.setVisible(true);
                            amountFieldDebit13.setVisible(true);
                            amountFieldDebit18.setVisible(true);
                            amountFieldDebit23.setVisible(true);
                            fifthCheckBox2.setVisible(true);
                            amountFieldDebit28.setVisible(true);
                            jButton7.setVisible(true);
                            gnamwLabel66.setVisible(true);
                            gacnoLabel67.setVisible(true);
                            gballowedLabel65.setVisible(true);
                            ggallowedLabel70.setVisible(true);
                            gallowamtLabel68.setVisible(true);
                            bneedsLabel64.setVisible(true);
                            bhasLabel71.setVisible(true);
                            actionLabel69.setVisible(true);
                            firstCheckBox1.setVisible(false); 
                            secondCheckBox1.setVisible(false); 
                            thirdCheckBox1.setVisible(false); 
                            fourthCheckBox1.setVisible(false); 
                            fifthCheckBox1.setVisible(false); 
                            break;  

                    }   

                 


                            
                            int numbGuaUS= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                            switch(numbGuaUS){
                            case 1:
                            if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit2.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit9.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit24.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            firstCheckBox2.setSelected(true);
                            jComboBox10.setVisible(false);
                            jButton8.setEnabled(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            amountFieldDebit2.setEditable(false);
                            amountFieldDebit9.setEditable(false);
                            amountFieldDebit24.setEditable(false);
                            amountFieldDebit14.setEditable(false);
                            amountFieldDebit19.setEditable(false);
                            guarantoursAccount10.setEditable(false);
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount10.setVisible(false);
                            guarantoursAccount10.setText("");
                            jComboBox10.setVisible(true);
                            jComboBox10.setSelectedItem("");
                            guarantoursAccount5.setEditable(true);
                            amountFieldDebit2.setEditable(true);
                            amountFieldDebit9.setEditable(true);
                            amountFieldDebit24.setEditable(true);
                            amountFieldDebit14.setEditable(true);
                            amountFieldDebit19.setEditable(true);

                            amountFieldDebit2.setValue(null);
                            amountFieldDebit9.setValue(null);
                            amountFieldDebit24.setValue(null);
                            amountFieldDebit14.setValue(null);
                            amountFieldDebit19.setValue(null);
                            jButton8.setEnabled(true); 
                            guarantoursAccount10.setEditable(true);
                
                
                }
                break;
                case 2:
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit2.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit9.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit24.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            firstCheckBox2.setSelected(true);
                            jComboBox10.setVisible(false);
                            jButton8.setEnabled(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            amountFieldDebit2.setEditable(false);
                            amountFieldDebit9.setEditable(false);
                            amountFieldDebit24.setEditable(false);
                            amountFieldDebit14.setEditable(false);
                            amountFieldDebit19.setEditable(false);
                            guarantoursAccount10.setEditable(false);
if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedSecondGuarantorB1"+accountNumber+".txt"))==900){         
                            jComboBox1.setVisible(false);
                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount6.setVisible(false);
                            amountFieldDebit3.setVisible(false);
                            amountFieldDebit10.setVisible(false);
                            amountFieldDebit25.setVisible(false);
                            jButton9.setVisible(false);
                            amountFieldDebit15.setVisible(false);
                            amountFieldDebit20.setVisible(false);
                            secondCheckBox2.setVisible(false);
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedSecondGuarantorB1"+accountNumber+".txt"))==910){

                                      jComboBox1.setVisible(true);
                            guarantoursAccount11.setVisible(true);
                            guarantoursAccount6.setVisible(true);
                            amountFieldDebit3.setVisible(true);
                            amountFieldDebit10.setVisible(true);
                            amountFieldDebit25.setVisible(true);
                            jButton9.setVisible(true);
                            amountFieldDebit15.setVisible(true);
                            amountFieldDebit20.setVisible(true);
                            secondCheckBox2.setVisible(true);


}
              
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==320){
                            guarantoursAccount10.setVisible(false);
                            guarantoursAccount10.setText("");
                            jComboBox10.setVisible(true);
                            jComboBox10.setSelectedItem("");
                            guarantoursAccount5.setEditable(true);
                            amountFieldDebit2.setEditable(true);
                            amountFieldDebit9.setEditable(true);
                            amountFieldDebit24.setEditable(true);
                            amountFieldDebit14.setEditable(true);
                            amountFieldDebit19.setEditable(true);
                            amountFieldDebit2.setValue(null);
                            amountFieldDebit9.setValue(null);
                            amountFieldDebit24.setValue(null);
                            amountFieldDebit14.setValue(null);
                            amountFieldDebit19.setValue(null);
                            jButton8.setEnabled(true); 
                            guarantoursAccount10.setEditable(true);
                
                
                }


               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit3.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit10.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit25.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit15.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit20.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            secondCheckBox2.setSelected(true);
                            jComboBox1.setVisible(false);
                            jButton9.setEnabled(false);
                            guarantoursAccount6.setEditable(false);
                      
                            amountFieldDebit3.setEditable(false);
                            amountFieldDebit10.setEditable(false);
                            amountFieldDebit25.setEditable(false);
                            amountFieldDebit15.setEditable(false);
                            amountFieldDebit20.setEditable(false);
                            guarantoursAccount11.setEditable(false);
                            
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                            amountFieldDebit3.setEditable(true);
                            amountFieldDebit10.setEditable(true);
                            amountFieldDebit25.setEditable(true);
                            amountFieldDebit15.setEditable(true);
                            amountFieldDebit20.setEditable(true);

                            amountFieldDebit3.setValue(null);
                            amountFieldDebit10.setValue(null);
                            amountFieldDebit25.setValue(null);
                            amountFieldDebit15.setValue(null);
                            amountFieldDebit20.setValue(null);
                            jButton9.setEnabled(true); 
                            guarantoursAccount11.setEditable(true);

                }
                    
                    
               
  
                break;
                case 3:
                    
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==300){

                                    guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[0] );
                                    guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[1] );
                                    amountFieldDebit2.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[2] ));
                                    amountFieldDebit9.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[3] ));
                                    amountFieldDebit24.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[4] ));
                                    amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[5] ));
                                    amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                                    firstCheckBox2.setSelected(true);
                                    jComboBox10.setVisible(false);
                                    jButton8.setEnabled(false);
                                    guarantoursAccount5.setEditable(false);
                                    jComboBox10.setVisible(false);
                                    amountFieldDebit2.setEditable(false);
                                    amountFieldDebit9.setEditable(false);
                                    amountFieldDebit24.setEditable(false);
                                    amountFieldDebit14.setEditable(false);
                                    amountFieldDebit19.setEditable(false);
                                    guarantoursAccount10.setEditable(false);
if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB1"+accountNumber+".txt"))==900){         
                                         jComboBox1.setVisible(false);
                                guarantoursAccount11.setVisible(false);
                                guarantoursAccount6.setVisible(false);
                                amountFieldDebit3.setVisible(false);
                                amountFieldDebit10.setVisible(false);
                                amountFieldDebit25.setVisible(false);
                                jButton9.setVisible(false);
                                amountFieldDebit15.setVisible(false);
                                amountFieldDebit20.setVisible(false);
                                secondCheckBox2.setVisible(false);

                                jComboBox6.setVisible(false);
                                guarantoursAccount12.setVisible(false);
                                guarantoursAccount7.setVisible(false);
                                amountFieldDebit4.setVisible(false);
                                amountFieldDebit11.setVisible(false);
                                amountFieldDebit26.setVisible(false);
                                jButton10.setVisible(false);
                                amountFieldDebit16.setVisible(false);
                                amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);

                                 }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB1"+accountNumber+".txt"))==910){
                                     jComboBox1.setVisible(false);
                                guarantoursAccount11.setVisible(true);
                                guarantoursAccount6.setVisible(true);
                                amountFieldDebit3.setVisible(true);
                                amountFieldDebit10.setVisible(true);
                                amountFieldDebit25.setVisible(true);
                                jButton9.setVisible(true);
                                amountFieldDebit15.setVisible(true);
                                amountFieldDebit20.setVisible(true);
                                secondCheckBox2.setVisible(true);

                                jComboBox6.setVisible(true);
                                guarantoursAccount12.setVisible(true);
                                guarantoursAccount7.setVisible(true);
                                amountFieldDebit4.setVisible(true);
                                amountFieldDebit11.setVisible(true);
                                amountFieldDebit26.setVisible(true);
                                jButton10.setVisible(true);
                                amountFieldDebit16.setVisible(true);
                                amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);

}
              
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==320){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    amountFieldDebit2.setEditable(true);
                    amountFieldDebit9.setEditable(true);
                    amountFieldDebit24.setEditable(true);
                    amountFieldDebit14.setEditable(true);
                    amountFieldDebit19.setEditable(true);
                    amountFieldDebit2.setValue(null);
                    amountFieldDebit9.setValue(null);
                    amountFieldDebit24.setValue(null);
                    amountFieldDebit14.setValue(null);
                    amountFieldDebit19.setValue(null);
                    jButton8.setEnabled(true); 
                    guarantoursAccount10.setEditable(true);


                    
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit3.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit10.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit25.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit15.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit20.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            secondCheckBox2.setSelected(true);
                            jComboBox1.setVisible(false);
                            jButton9.setEnabled(false);
                            guarantoursAccount6.setEditable(false);
                      
                            amountFieldDebit3.setEditable(false);
                            amountFieldDebit10.setEditable(false);
                            amountFieldDebit25.setEditable(false);
                            amountFieldDebit15.setEditable(false);
                            amountFieldDebit20.setEditable(false);
                            guarantoursAccount11.setEditable(false);
                          if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB2"+accountNumber+".txt"))==900){         
                                

                          jComboBox6.setVisible(false);
                guarantoursAccount12.setVisible(false);
                guarantoursAccount7.setVisible(false);
                amountFieldDebit4.setVisible(false);
                amountFieldDebit11.setVisible(false);
                amountFieldDebit26.setVisible(false);
                jButton10.setVisible(false);
                        amountFieldDebit16.setVisible(false);
                        amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);
                                            }  else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB2"+accountNumber+".txt"))==910){
                                            
                                           
                          jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                amountFieldDebit4.setVisible(true);
                amountFieldDebit11.setVisible(true);
                amountFieldDebit26.setVisible(true);
                jButton10.setVisible(true);
                        amountFieldDebit16.setVisible(true);
                        amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);     
                                                
                                                
                                            }
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                            amountFieldDebit3.setEditable(true);
                            amountFieldDebit10.setEditable(true);
                            amountFieldDebit25.setEditable(true);
                            amountFieldDebit15.setEditable(true);
                            amountFieldDebit20.setEditable(true);

                            amountFieldDebit3.setValue(null);
                            amountFieldDebit10.setValue(null);
                            amountFieldDebit25.setValue(null);
                            amountFieldDebit15.setValue(null);
                            amountFieldDebit20.setValue(null);
                            jButton9.setEnabled(true); 
                            guarantoursAccount11.setEditable(true);
                }
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit4.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit11.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit26.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit16.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit21.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            thirdCheckBox2.setSelected(true);
                          jComboBox6.setVisible(false);
                            jButton10.setEnabled(false);
                            guarantoursAccount7.setEditable(false);
                      
                            amountFieldDebit4.setEditable(false);
                            amountFieldDebit11.setEditable(false);
                            amountFieldDebit26.setEditable(false);
                            amountFieldDebit16.setEditable(false);
                            amountFieldDebit21.setEditable(false);
                            guarantoursAccount12.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                            amountFieldDebit4.setEditable(true);
                            amountFieldDebit11.setEditable(true);
                            amountFieldDebit26.setEditable(true);
                            amountFieldDebit16.setEditable(true);
                            amountFieldDebit21.setEditable(true);

                            amountFieldDebit4.setValue(null);
                            amountFieldDebit11.setValue(null);
                            amountFieldDebit26.setValue(null);
                            amountFieldDebit16.setValue(null);
                            amountFieldDebit21.setValue(null);
                            jButton10.setEnabled(true); 
                            guarantoursAccount12.setEditable(true);

                }     

                break;
                case 4:
                                   
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit2.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit9.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit24.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            firstCheckBox2.setSelected(true);
                            jComboBox10.setVisible(false);
                            jButton8.setEnabled(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            amountFieldDebit2.setEditable(false);
                            amountFieldDebit9.setEditable(false);
                            amountFieldDebit24.setEditable(false);
                            amountFieldDebit14.setEditable(false);
                            amountFieldDebit19.setEditable(false);
                            guarantoursAccount10.setEditable(false);
                        
                
                if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB1"+accountNumber+".txt"))==900){         
                  jComboBox1.setVisible(false);
                guarantoursAccount11.setVisible(false);
                guarantoursAccount6.setVisible(false);
                amountFieldDebit3.setVisible(false);
                amountFieldDebit10.setVisible(false);
                amountFieldDebit25.setVisible(false);
                jButton9.setVisible(false);
                        amountFieldDebit15.setVisible(false);
                        amountFieldDebit20.setVisible(false);
                                secondCheckBox2.setVisible(false);

                              jComboBox6.setVisible(false);
                guarantoursAccount12.setVisible(false);
                guarantoursAccount7.setVisible(false);
                amountFieldDebit4.setVisible(false);
                amountFieldDebit11.setVisible(false);
                amountFieldDebit26.setVisible(false);
                jButton10.setVisible(false);
                        amountFieldDebit16.setVisible(false);
                        amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB1"+accountNumber+".txt"))==910){
                                            
                  jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);
                amountFieldDebit3.setVisible(true);
                amountFieldDebit10.setVisible(true);
                amountFieldDebit25.setVisible(true);
                jButton9.setVisible(true);
                        amountFieldDebit15.setVisible(true);
                        amountFieldDebit20.setVisible(true);
                                secondCheckBox2.setVisible(true);

                              jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                amountFieldDebit4.setVisible(true);
                amountFieldDebit11.setVisible(true);
                amountFieldDebit26.setVisible(true);
                jButton10.setVisible(true);
                        amountFieldDebit16.setVisible(true);
                        amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);
                                
                  
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                                            
                                            
                                            
                                            }
              
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==320){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    amountFieldDebit2.setEditable(true);
                    amountFieldDebit9.setEditable(true);
                    amountFieldDebit24.setEditable(true);
                    amountFieldDebit14.setEditable(true);
                    amountFieldDebit19.setEditable(true);
                    amountFieldDebit2.setValue(null);
                    amountFieldDebit9.setValue(null);
                    amountFieldDebit24.setValue(null);
                    amountFieldDebit14.setValue(null);
                    amountFieldDebit19.setValue(null);
                    jButton8.setEnabled(true); 
                    guarantoursAccount10.setEditable(true);


                    
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit3.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit10.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit25.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit15.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit20.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            secondCheckBox2.setSelected(true);
                            jComboBox1.setVisible(false);
                            jButton9.setEnabled(false);
                            guarantoursAccount6.setEditable(false);
                      
                            amountFieldDebit3.setEditable(false);
                            amountFieldDebit10.setEditable(false);
                            amountFieldDebit25.setEditable(false);
                            amountFieldDebit15.setEditable(false);
                            amountFieldDebit20.setEditable(false);
                            guarantoursAccount11.setEditable(false);
                          if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB2"+accountNumber+".txt"))==900){         
                                

                          jComboBox6.setVisible(false);
                guarantoursAccount12.setVisible(false);
                guarantoursAccount7.setVisible(false);
                amountFieldDebit4.setVisible(false);
                amountFieldDebit11.setVisible(false);
                amountFieldDebit26.setVisible(false);
                jButton10.setVisible(false);
                        amountFieldDebit16.setVisible(false);
                        amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                                          
                          }   else  if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB2"+accountNumber+".txt"))==910){
                          
                          
                          jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                amountFieldDebit4.setVisible(true);
                amountFieldDebit11.setVisible(true);
                amountFieldDebit26.setVisible(true);
                jButton10.setVisible(true);
                        amountFieldDebit16.setVisible(true);
                        amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);
                                
                  
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                          
                          
                          }
                            
                          
                          
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                            amountFieldDebit3.setEditable(true);
                            amountFieldDebit10.setEditable(true);
                            amountFieldDebit25.setEditable(true);
                            amountFieldDebit15.setEditable(true);
                            amountFieldDebit20.setEditable(true);

                            amountFieldDebit3.setValue(null);
                            amountFieldDebit10.setValue(null);
                            amountFieldDebit25.setValue(null);
                            amountFieldDebit15.setValue(null);
                            amountFieldDebit20.setValue(null);
                            jButton9.setEnabled(true); 
                            guarantoursAccount11.setEditable(true);
                            
   
                            

                }
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit4.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit11.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit26.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit16.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit21.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            thirdCheckBox2.setSelected(true);
                          jComboBox6.setVisible(false);
                            jButton10.setEnabled(false);
                            guarantoursAccount7.setEditable(false);
                      
                            amountFieldDebit4.setEditable(false);
                            amountFieldDebit11.setEditable(false);
                            amountFieldDebit26.setEditable(false);
                            amountFieldDebit16.setEditable(false);
                            amountFieldDebit21.setEditable(false);
                            guarantoursAccount12.setEditable(false);
                          
                             if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB3"+accountNumber+".txt"))==900){         
                                
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                                          
                         }
                             else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB3"+accountNumber+".txt"))==910){
                                
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                          
                          
                          }
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                            amountFieldDebit4.setEditable(true);
                            amountFieldDebit11.setEditable(true);
                            amountFieldDebit26.setEditable(true);
                            amountFieldDebit16.setEditable(true);
                            amountFieldDebit21.setEditable(true);

                            amountFieldDebit4.setValue(null);
                            amountFieldDebit11.setValue(null);
                            amountFieldDebit26.setValue(null);
                            amountFieldDebit16.setValue(null);
                            amountFieldDebit21.setValue(null);
                            jButton10.setEnabled(true); 
                            guarantoursAccount12.setEditable(true);
          }     
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount13.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount8 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit5.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit12.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit27.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit17.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit22.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                           fourthCheckBox2.setSelected(true);
                          jComboBox7.setVisible(false);
                            jButton11.setEnabled(false);
                            guarantoursAccount8.setEditable(false);
                      
                            amountFieldDebit5.setEditable(false);
                            amountFieldDebit12.setEditable(false);
                            amountFieldDebit27.setEditable(false);
                            amountFieldDebit17.setEditable(false);
                            amountFieldDebit22.setEditable(false);
                            guarantoursAccount13.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount13.setVisible(false);
                            guarantoursAccount13.setText("");
                            guarantoursAccount8.setEditable(true);
                            amountFieldDebit5.setEditable(true);
                            amountFieldDebit12.setEditable(true);
                            amountFieldDebit27.setEditable(true);
                            amountFieldDebit17.setEditable(true);
                            amountFieldDebit22.setEditable(true);

                            amountFieldDebit5.setValue(null);
                            amountFieldDebit12.setValue(null);
                            amountFieldDebit27.setValue(null);
                            amountFieldDebit17.setValue(null);
                            amountFieldDebit22.setValue(null);
                            jButton11.setEnabled(true); 
                            guarantoursAccount13.setEditable(true);

                }   
                    
                    
                    
                break;
                case 5:
                    
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit2.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit9.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit24.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            firstCheckBox2.setSelected(true);
                            jComboBox10.setVisible(false);
                            jButton8.setEnabled(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            amountFieldDebit2.setEditable(false);
                            amountFieldDebit9.setEditable(false);
                            amountFieldDebit24.setEditable(false);
                            amountFieldDebit14.setEditable(false);
                            amountFieldDebit19.setEditable(false);
                            guarantoursAccount10.setEditable(false);
                if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB1"+accountNumber+".txt"))==900){         
                                          jComboBox1.setVisible(false);
                guarantoursAccount11.setVisible(false);
                guarantoursAccount6.setVisible(false);
                amountFieldDebit3.setVisible(false);
                amountFieldDebit10.setVisible(false);
                amountFieldDebit25.setVisible(false);
                jButton9.setVisible(false);
                        amountFieldDebit15.setVisible(false);
                        amountFieldDebit20.setVisible(false);
                                secondCheckBox2.setVisible(false);

                              jComboBox6.setVisible(false);
                guarantoursAccount12.setVisible(false);
                guarantoursAccount7.setVisible(false);
                amountFieldDebit4.setVisible(false);
                amountFieldDebit11.setVisible(false);
                amountFieldDebit26.setVisible(false);
                jButton10.setVisible(false);
                        amountFieldDebit16.setVisible(false);
                        amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                             
                             fifthCheckBox2.setVisible(false);
                          jComboBox9.setVisible(false);
                            jButton7.setVisible(false);
                            guarantoursAccount9.setVisible(false);
                            amountFieldDebit6.setVisible(false);
                            amountFieldDebit13.setVisible(false);
                            amountFieldDebit28.setVisible(false);
                            amountFieldDebit18.setVisible(false);
                            amountFieldDebit23.setVisible(false);
                            guarantoursAccount14.setVisible(false);
                                            }else  if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB1"+accountNumber+".txt"))==910){         
                                          jComboBox1.setVisible(true);
                guarantoursAccount11.setVisible(true);
                guarantoursAccount6.setVisible(true);
                amountFieldDebit3.setVisible(true);
                amountFieldDebit10.setVisible(true);
                amountFieldDebit25.setVisible(true);
                jButton9.setVisible(true);
                        amountFieldDebit15.setVisible(true);
                        amountFieldDebit20.setVisible(true);
                                secondCheckBox2.setVisible(true);

                              jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                amountFieldDebit4.setVisible(true);
                amountFieldDebit11.setVisible(true);
                amountFieldDebit26.setVisible(true);
                jButton10.setVisible(true);
                        amountFieldDebit16.setVisible(true);
                        amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);
                                
                  
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                             
                             fifthCheckBox2.setVisible(true);
                          jComboBox9.setVisible(true);
                            jButton7.setVisible(true);
                            guarantoursAccount9.setVisible(true);
                            amountFieldDebit6.setVisible(true);
                            amountFieldDebit13.setVisible(true);
                            amountFieldDebit28.setVisible(true);
                            amountFieldDebit18.setVisible(true);
                            amountFieldDebit23.setVisible(true);
                            guarantoursAccount14.setVisible(true);
                                            }
              
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+accountNumber+".txt"))==320){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    amountFieldDebit2.setEditable(true);
                    amountFieldDebit9.setEditable(true);
                    amountFieldDebit24.setEditable(true);
                    amountFieldDebit14.setEditable(true);
                    amountFieldDebit19.setEditable(true);
                    amountFieldDebit2.setValue(null);
                    amountFieldDebit9.setValue(null);
                    amountFieldDebit24.setValue(null);
                    amountFieldDebit14.setValue(null);
                    amountFieldDebit19.setValue(null);
                    jButton8.setEnabled(true); 
                    guarantoursAccount10.setEditable(true);
                
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit3.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit10.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit25.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit15.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit20.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            secondCheckBox2.setSelected(true);
                            jComboBox1.setVisible(false);
                            jButton9.setEnabled(false);
                            guarantoursAccount6.setEditable(false);
                      
                            amountFieldDebit3.setEditable(false);
                            amountFieldDebit10.setEditable(false);
                            amountFieldDebit25.setEditable(false);
                            amountFieldDebit15.setEditable(false);
                            amountFieldDebit20.setEditable(false);
                            guarantoursAccount11.setEditable(false);
                          if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB2"+accountNumber+".txt"))==900){         
                                

                          jComboBox6.setVisible(false);
                guarantoursAccount12.setVisible(false);
                guarantoursAccount7.setVisible(false);
                amountFieldDebit4.setVisible(false);
                amountFieldDebit11.setVisible(false);
                amountFieldDebit26.setVisible(false);
                jButton10.setVisible(false);
                        amountFieldDebit16.setVisible(false);
                        amountFieldDebit21.setVisible(false);
                                thirdCheckBox2.setVisible(false);
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                             
                              fifthCheckBox2.setVisible(false);
                          jComboBox9.setVisible(false);
                            jButton7.setVisible(false);
                            guarantoursAccount9.setVisible(false);
                            amountFieldDebit6.setVisible(false);
                            amountFieldDebit13.setVisible(false);
                            amountFieldDebit28.setVisible(false);
                            amountFieldDebit18.setVisible(false);
                            amountFieldDebit23.setVisible(false);
                            guarantoursAccount14.setVisible(false);
                                          
                          }  else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB2"+accountNumber+".txt"))==910){         
                                

                          jComboBox6.setVisible(true);
                guarantoursAccount12.setVisible(true);
                guarantoursAccount7.setVisible(true);
                amountFieldDebit4.setVisible(true);
                amountFieldDebit11.setVisible(true);
                amountFieldDebit26.setVisible(true);
                jButton10.setVisible(true);
                        amountFieldDebit16.setVisible(true);
                        amountFieldDebit21.setVisible(true);
                                thirdCheckBox2.setVisible(true);
                                
                  
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                             
                              fifthCheckBox2.setVisible(true);
                          jComboBox9.setVisible(true);
                            jButton7.setVisible(true);
                            guarantoursAccount9.setVisible(true);
                            amountFieldDebit6.setVisible(true);
                            amountFieldDebit13.setVisible(true);
                            amountFieldDebit28.setVisible(true);
                            amountFieldDebit18.setVisible(true);
                            amountFieldDebit23.setVisible(true);
                            guarantoursAccount14.setVisible(true);
                                          
                          }
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");

                            guarantoursAccount6.setEditable(true);
                            amountFieldDebit3.setEditable(true);
                            amountFieldDebit10.setEditable(true);
                            amountFieldDebit25.setEditable(true);
                            amountFieldDebit15.setEditable(true);
                            amountFieldDebit20.setEditable(true);

                            amountFieldDebit3.setValue(null);
                            amountFieldDebit10.setValue(null);
                            amountFieldDebit25.setValue(null);
                            amountFieldDebit15.setValue(null);
                            amountFieldDebit20.setValue(null);
                            jButton9.setEnabled(true); 
                            guarantoursAccount11.setEditable(true);

                }
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit4.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit11.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit26.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit16.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit21.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                            thirdCheckBox2.setSelected(true);
                          jComboBox6.setVisible(false);
                            jButton10.setEnabled(false);
                            guarantoursAccount7.setEditable(false);
                      
                            amountFieldDebit4.setEditable(false);
                            amountFieldDebit11.setEditable(false);
                            amountFieldDebit26.setEditable(false);
                            amountFieldDebit16.setEditable(false);
                            amountFieldDebit21.setEditable(false);
                            guarantoursAccount12.setEditable(false);
                          
                             if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB3"+accountNumber+".txt"))==900){         
                                
                                
                  
                          jComboBox7.setVisible(false);
                            jButton11.setVisible(false);
                            guarantoursAccount8.setVisible(false);
                            amountFieldDebit5.setVisible(false);
                            amountFieldDebit12.setVisible(false);
                            amountFieldDebit27.setVisible(false);
                            amountFieldDebit17.setVisible(false);
                            amountFieldDebit22.setVisible(false);
                            guarantoursAccount13.setVisible(false);
                             fourthCheckBox2.setVisible(false);
                             
                              fifthCheckBox2.setVisible(false);
                          jComboBox9.setVisible(false);
                            jButton7.setVisible(false);
                            guarantoursAccount9.setVisible(false);
                            amountFieldDebit6.setVisible(false);
                            amountFieldDebit13.setVisible(false);
                            amountFieldDebit28.setVisible(false);
                            amountFieldDebit18.setVisible(false);
                            amountFieldDebit23.setVisible(false);
                            guarantoursAccount14.setVisible(false);
                                          
                          }  else  if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB3"+accountNumber+".txt"))==910){         
                                
                                
                  
                          jComboBox7.setVisible(true);
                            jButton11.setVisible(true);
                            guarantoursAccount8.setVisible(true);
                            amountFieldDebit5.setVisible(true);
                            amountFieldDebit12.setVisible(true);
                            amountFieldDebit27.setVisible(true);
                            amountFieldDebit17.setVisible(true);
                            amountFieldDebit22.setVisible(true);
                            guarantoursAccount13.setVisible(true);
                             fourthCheckBox2.setVisible(true);
                             
                              fifthCheckBox2.setVisible(true);
                          jComboBox9.setVisible(true);
                            jButton7.setVisible(true);
                            guarantoursAccount9.setVisible(true);
                            amountFieldDebit6.setVisible(true);
                            amountFieldDebit13.setVisible(true);
                            amountFieldDebit28.setVisible(true);
                            amountFieldDebit18.setVisible(true);
                            amountFieldDebit23.setVisible(true);
                            guarantoursAccount14.setVisible(true);
                                          
                          } 
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                            amountFieldDebit4.setEditable(true);
                            amountFieldDebit11.setEditable(true);
                            amountFieldDebit26.setEditable(true);
                            amountFieldDebit16.setEditable(true);
                            amountFieldDebit21.setEditable(true);

                            amountFieldDebit4.setValue(null);
                            amountFieldDebit11.setValue(null);
                            amountFieldDebit26.setValue(null);
                            amountFieldDebit16.setValue(null);
                            amountFieldDebit21.setValue(null);
                            jButton10.setEnabled(true); 
                            guarantoursAccount12.setEditable(true);

                }     
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount13.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount8 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit5.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit12.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit27.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit17.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit22.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                           fourthCheckBox2.setSelected(true);
                          jComboBox7.setVisible(false);
                            jButton11.setEnabled(false);
                            guarantoursAccount8.setEditable(false);
                      
                            amountFieldDebit5.setEditable(false);
                            amountFieldDebit12.setEditable(false);
                            amountFieldDebit27.setEditable(false);
                            amountFieldDebit17.setEditable(false);
                            amountFieldDebit22.setEditable(false);
                            guarantoursAccount13.setEditable(false);
                            
                            if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB4"+accountNumber+".txt"))==900){         
                                
                                
                  
                             
                              fifthCheckBox2.setVisible(false);
                          jComboBox9.setVisible(false);
                            jButton7.setVisible(false);
                            guarantoursAccount9.setVisible(false);
                            amountFieldDebit6.setVisible(false);
                            amountFieldDebit13.setVisible(false);
                            amountFieldDebit28.setVisible(false);
                            amountFieldDebit18.setVisible(false);
                            amountFieldDebit23.setVisible(false);
                            guarantoursAccount14.setVisible(false);
                                          
                          } else      if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB4"+accountNumber+".txt"))==910){         
                                
                                
                  
                             
                              fifthCheckBox2.setVisible(true);
                          jComboBox9.setVisible(true);
                            jButton7.setVisible(true);
                            guarantoursAccount9.setVisible(true);
                            amountFieldDebit6.setVisible(true);
                            amountFieldDebit13.setVisible(true);
                            amountFieldDebit28.setVisible(true);
                            amountFieldDebit18.setVisible(true);
                            amountFieldDebit23.setVisible(true);
                            guarantoursAccount14.setVisible(true);
                                          
                          }
                          
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount13.setVisible(false);
                            guarantoursAccount13.setText("");
                            guarantoursAccount8.setEditable(true);
                            amountFieldDebit5.setEditable(true);
                            amountFieldDebit12.setEditable(true);
                            amountFieldDebit27.setEditable(true);
                            amountFieldDebit17.setEditable(true);
                            amountFieldDebit22.setEditable(true);

                            amountFieldDebit5.setValue(null);
                            amountFieldDebit12.setValue(null);
                            amountFieldDebit27.setValue(null);
                            amountFieldDebit17.setValue(null);
                            amountFieldDebit22.setValue(null);
                            jButton11.setEnabled(true); 
                            guarantoursAccount13.setEditable(true);

                }    
                 if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+accountNumber+".txt"))==300){

                            guarantoursAccount14.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[0] );
                            guarantoursAccount9 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[1] );
                            amountFieldDebit6.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[2] ));
                            amountFieldDebit13.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[3] ));
                            amountFieldDebit28.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[4] ));
                            amountFieldDebit18.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[5] ));
                            amountFieldDebit23.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+accountNumber+".txt")).split("[;]", 7)[6].replaceAll(";", "")));
                           fifthCheckBox2.setSelected(true);
                          jComboBox9.setVisible(false);
                            jButton7.setEnabled(false);
                            guarantoursAccount9.setEditable(false);
                      
                            amountFieldDebit6.setEditable(false);
                            amountFieldDebit13.setEditable(false);
                            amountFieldDebit28.setEditable(false);
                            amountFieldDebit18.setEditable(false);
                            amountFieldDebit23.setEditable(false);
                            guarantoursAccount14.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+accountNumber+".txt"))==320){

                            guarantoursAccount14.setVisible(false);
                            guarantoursAccount14.setText("");

                            guarantoursAccount9.setEditable(true);
                            amountFieldDebit6.setEditable(true);
                            amountFieldDebit13.setEditable(true);
                            amountFieldDebit28.setEditable(true);
                            amountFieldDebit18.setEditable(true);
                            amountFieldDebit23.setEditable(true);

                            amountFieldDebit6.setValue(null);
                            amountFieldDebit13.setValue(null);
                            amountFieldDebit28.setValue(null);
                            amountFieldDebit18.setValue(null);
                            amountFieldDebit23.setValue(null);
                            jButton7.setEnabled(true); 
                            guarantoursAccount14.setEditable(true);

                }     
                    
                    
                break;

                }
                             }else{
      
  int numbGuaUS= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                            switch(numbGuaUS){
                            case 1:
                            if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            firstCheckBox1.setSelected(true);
                            jComboBox10.setVisible(false);
                            guarantoursAccount5.setEditable(false);
                            guarantoursAccount10.setEditable(false);
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount10.setVisible(false);
                            guarantoursAccount10.setText("");
                            jComboBox10.setVisible(true);
                            jComboBox10.setSelectedItem("");
                            guarantoursAccount5.setEditable(true);
                            guarantoursAccount10.setEditable(true);
                
                
                }
                break;
                case 2:
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                            firstCheckBox1.setSelected(true);
                            jComboBox10.setVisible(false);
                            guarantoursAccount5.setEditable(false);
                           guarantoursAccount10.setEditable(false);

              
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==420){
                            guarantoursAccount10.setVisible(false);
                            guarantoursAccount10.setText("");
                            jComboBox10.setVisible(true);
                            jComboBox10.setSelectedItem("");
                            guarantoursAccount5.setEditable(true);
                            firstCheckBox1.setVisible(true);
                            guarantoursAccount10.setEditable(true);
                }


               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            secondCheckBox1.setSelected(true);
                            jComboBox1.setVisible(false);
                            guarantoursAccount6.setEditable(false);
                            guarantoursAccount11.setEditable(false);

                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                            guarantoursAccount11.setEditable(true);

                }
                    
                    
               
  
                break;
                case 3:
                    
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==400){

                                    guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[0] );
                                    guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                                   
                                    firstCheckBox1.setSelected(true);
                                    jComboBox10.setVisible(false);
                                    
                                    guarantoursAccount5.setEditable(false);
                                    jComboBox10.setVisible(false);
                                     guarantoursAccount10.setEditable(false);
         
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==420){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    
                    guarantoursAccount10.setEditable(true);


                    
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                            secondCheckBox1.setSelected(true);
                            jComboBox1.setVisible(false);
                            guarantoursAccount6.setEditable(false);                    
                            guarantoursAccount11.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                 
                            guarantoursAccount11.setEditable(true);
                }
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                            thirdCheckBox1.setSelected(true);
                          jComboBox6.setVisible(false);
                          
                            guarantoursAccount7.setEditable(false);
                      
                          
                            guarantoursAccount12.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                             guarantoursAccount12.setEditable(true);

                }     

                break;
                case 4:
                                   
if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                           
                            firstCheckBox1.setSelected(true);
                        
                            jButton8.setEnabled(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            guarantoursAccount10.setEditable(false);
                        
    
}else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==420){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    guarantoursAccount10.setEditable(true);


                    
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[1] .replaceAll(";", ""));
                            
                            secondCheckBox1.setSelected(true);
                            jComboBox1.setVisible(false);
                            guarantoursAccount6.setEditable(false);
     
                            guarantoursAccount11.setEditable(false);
                         
                          
                          
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");
                            guarantoursAccount6.setEditable(true);
                            
                            guarantoursAccount11.setEditable(true);
                            
   
                            

                }
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                           
                            thirdCheckBox1.setSelected(true);
                          jComboBox6.setVisible(false);
                            jButton10.setEnabled(false);
                            guarantoursAccount7.setEditable(false);
                      
                            
                            guarantoursAccount12.setEditable(false);
                          
                             
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                            
                            guarantoursAccount12.setEditable(true);
          }     
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount13.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount8 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                           fourthCheckBox1.setSelected(true);
                          jComboBox7.setVisible(false);
                           guarantoursAccount8.setEditable(false);
                      
                            guarantoursAccount13.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount13.setVisible(false);
                            guarantoursAccount13.setText("");
                            guarantoursAccount8.setEditable(true);
                           
                            guarantoursAccount13.setEditable(true);

                }   
                    
                    
                    
                break;
                case 5:
                    
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount10.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount5 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                            firstCheckBox1.setSelected(true);
                            jComboBox10.setVisible(false);
                            guarantoursAccount5.setEditable(false);
                            jComboBox10.setVisible(false);
                            
                            guarantoursAccount10.setEditable(false);
                              
          }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+accountNumber+".txt"))==420){
                    guarantoursAccount10.setVisible(false);
                    guarantoursAccount10.setText("");
                    jComboBox10.setVisible(true);
                    jComboBox10.setSelectedItem("");
                    guarantoursAccount5.setEditable(true);
                    
                    guarantoursAccount10.setEditable(true);
                
                }

               if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount11.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount6 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                           
                            secondCheckBox1.setSelected(true);
                            jComboBox1.setVisible(false);
  
                            guarantoursAccount6.setEditable(false);
                      
                            
                            guarantoursAccount11.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount11.setVisible(false);
                            guarantoursAccount11.setText("");

                            guarantoursAccount6.setEditable(true);
                           
                            guarantoursAccount11.setEditable(true);

                }
               
                if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount12.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount7 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                            thirdCheckBox1.setSelected(true);
                          jComboBox6.setVisible(false);
                       
                            guarantoursAccount7.setEditable(false);
                      
                            guarantoursAccount12.setEditable(false);
                          
                            
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount12.setVisible(false);
                            guarantoursAccount12.setText("");
                            guarantoursAccount7.setEditable(true);
                            
                            guarantoursAccount12.setEditable(true);

                }     
                  if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount13.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount8 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+accountNumber+".txt")).split("[;]", 2)[1].replaceAll(";", "") );
                            
                           fourthCheckBox1.setSelected(true);
                          jComboBox7.setVisible(false);
                        
                            guarantoursAccount8.setEditable(false);
                      
                       
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount13.setVisible(false);
                            guarantoursAccount13.setText("");
                            guarantoursAccount8.setEditable(true);
                            
                            guarantoursAccount13.setEditable(true);

                }
                  
                  
                 if( fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5SelectedS"+accountNumber+".txt"))==400){

                            guarantoursAccount14.setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+accountNumber+".txt")).split("[;]", 2)[0] );
                            guarantoursAccount9 .setText(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+accountNumber+".txt")).split("[;]", 2)[1] .replaceAll(";", ""));
                            
                           fifthCheckBox1.setSelected(true);
                          jComboBox9.setVisible(false);
                         
                            guarantoursAccount9.setEditable(false);
                      
                            guarantoursAccount14.setEditable(false);
                          
                            
                            }else if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5SelectedS"+accountNumber+".txt"))==420){

                            guarantoursAccount14.setVisible(false);
                            guarantoursAccount14.setText("");

                            guarantoursAccount9.setEditable(true);
                            
                            guarantoursAccount14.setEditable(true);

                }     
                    
                    
                break;

                }
      }
}

                fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"));
              
                int valueLoan= fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"));

                if( valueLoan==1){

                if(loan.loanExists(accountNumber)==false){


                Integer xxy=1;

                fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+accountNumber+ ".txt"), xxy.toString());

                } 



//                updateFields(jTextField14,accountNumber);
                String maxTenure=     fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt"));
                String[] tenure=maxTenure.split("[,]", 2);
                int timeReal=parseInt(tenure[0]);
                String timeLabel=tenure[1].replaceAll(",", "");
                jTextField36.setText(timeReal+"");
                jComboBox8.setSelectedItem(timeLabel);
                jComboBox8.setEditable(false);
//jTabbedPane1.setVisible(false);
jTabbedPane1.setVisible(true);
jDesktopPane1.setVisible(false);
newLoanPanel.setVisible(true);
loanClosedPanel.setVisible(false);
                fios.forceFileExistance(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
int typeOfBasis =fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "basedOnx.txt"));
switch(typeOfBasis){
case 0:

double fixedValue=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "fixedMaxLent.txt")));
amountFieldDebit8.setValue(fixedValue);

break; 
case 1:
    
String formulaOthers=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));       
int operands= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));  
   if(operands==3){
   
  String [] formulaBits=formulaOthers.split("[,]", 3);
   String d1="",d2="",d3="";
   

   if(formulaBits[0].equals("Shares")){
   d1=dbq.getShares(accountNumber);
   } else{
   d1=formulaBits[0];
   
   }
   
  d2=formulaBits[1];

  
  if(formulaBits[2].replaceAll(",", "").equals("Shares")){
   d3=dbq.getShares(accountNumber);
  
  
  }else{
  d3=formulaBits[2].replaceAll(",", "");
  
  

  }
    amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3));
   }else if(operands==5){
   String [] formulaBits=formulaOthers.split("[,]", 5);
   String d1="",d2="",d3="",d4="",d5="";
   d1=formulaBits[0];
   if(formulaBits[1].equals("Shares")){
   
   d2=dbq.getShares(accountNumber);
   
   }else{
   d2=formulaBits[1];
   }
   d3=formulaBits[2];
   if(formulaBits[3].equals("Shares")){
   
   d4=dbq.getShares(accountNumber);
   
   }else{
   d4=formulaBits[3];
   }
   d5=formulaBits[4].replaceAll(",", "");
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5));
   } else if(operands==7){
       
       
   String [] formulaBits=formulaOthers.split("[,]", 7);
   String d1="",d2="",d3="",d4="",d5="",d6="",d7="";
  
   if(formulaBits[0].equals("Shares")){
   
   d1=dbq.getShares(accountNumber);
   
   }else{
   d1=formulaBits[0];
   }
   
   if(formulaBits[1].equals("Shares")){
   
   d2=dbq.getShares(accountNumber);
   
   }else{
   d2=formulaBits[1];
   }
   
   
   
   d3=formulaBits[2];
   
   if(formulaBits[3].equals("Shares")){
   
   d4=dbq.getShares(accountNumber);
   
   }else{
   d4=formulaBits[3];
   }
   d5=formulaBits[4];
   
   if(formulaBits[5].equals("Shares")){
   
   d6=dbq.getShares(accountNumber);
   
   }else{
   d6=formulaBits[5];
   }
   
   if(formulaBits[6].replaceAll(",", "").equals("Shares")){
   
   d7=dbq.getShares(accountNumber);
   
   }else{
   d7=formulaBits[6].replaceAll(",", "");
   }
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7));
    
   } else if(operands==9){
      
       
       
   String [] formulaBits=formulaOthers.split("[,]", 9);
   String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";
  
   
   d1=formulaBits[0];
   if(formulaBits[1].equals("Shares")){
   
   d2=dbq.getShares(accountNumber);
   
   }else{
   d2=formulaBits[1];
   }
   
   if(formulaBits[2].equals("Shares")){
   
   d3=dbq.getShares(accountNumber);
   
   }else{
   d3=formulaBits[2];
   }
   
   
   
   d4=formulaBits[3];
   
   if(formulaBits[4].equals("Shares")){
   
   d5=dbq.getShares(accountNumber);
   
   }else{
   d5=formulaBits[4];
   }
   d6=formulaBits[5];
   
   if(formulaBits[6].equals("Shares")){
   
   d7=dbq.getShares(accountNumber);
   
   }else{
   d7=formulaBits[6];
   }
   
   if(formulaBits[7].equals("Shares")){
   
   d8=dbq.getShares(accountNumber);
   
   }else{
   d8=formulaBits[7];
   }
   d9=formulaBits[8].replaceAll(",", "");
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9));
    
   
   
   
   } 
    
    
    
break; 
case 2:
String formulaOthersg=fios.stringFileReader(fios.createFileName("persistence", "maxAmountLent", "formulaString.txt"));       
int operandsg= fios.intFileReader(fios.createFileName("persistence", "maxAmountLent", "numberOperands.txt"));  
   if(operandsg==3){
   
  String [] formulaBits=formulaOthersg.split("[,]", 3);
   String d1="",d2="",d3="";
   

   if(formulaBits[0].equals("Savings")){
   d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   } else{
   d1=formulaBits[0];
   
   }
   
  d2=formulaBits[1];

  
  if(formulaBits[2].replaceAll(",", "").equals("Savings")){
   d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
  
  
  }else{
  d3=formulaBits[2].replaceAll(",", "");
  
  

  }
    amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3));
   }else if(operandsg==5){
   String [] formulaBits=formulaOthersg.split("[,]", 5);
   String d1="",d2="",d3="",d4="",d5="";
   d1=formulaBits[0];
   if(formulaBits[1].equals("Savings")){
   
   d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d2=formulaBits[1];
   }
   d3=formulaBits[2];
   if(formulaBits[3].equals("Savings")){
   
   d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d4=formulaBits[3];
   }
   d5=formulaBits[4].replaceAll(",", "");
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5));
   } else if(operandsg==7){
       
       
   String [] formulaBits=formulaOthersg.split("[,]", 7);
   String d1="",d2="",d3="",d4="",d5="",d6="",d7="";
  
   if(formulaBits[0].equals("Savings")){
   
   d1=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d1=formulaBits[0];
   }
   
   if(formulaBits[1].equals("Savings")){
   
   d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d2=formulaBits[1];
   }
   
   
   
   d3=formulaBits[2];
   
   if(formulaBits[3].equals("Savings")){
   
   d4=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d4=formulaBits[3];
   }
   d5=formulaBits[4];
   
   if(formulaBits[5].equals("Savings")){
   
   d6=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d6=formulaBits[5];
   }
   
   if(formulaBits[6].replaceAll(",", "").equals("Savings")){
   
   d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d7=formulaBits[6].replaceAll(",", "");
   }
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7));
    
   } else if(operandsg==9){
      
       
       
   String [] formulaBits=formulaOthersg.split("[,]", 9);
   String d1="",d2="",d3="",d4="",d5="",d6="",d7="",d8="",d9="";
  
   
   d1=formulaBits[0];
   if(formulaBits[1].equals("Savings")){
   
   d2=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d2=formulaBits[1];
   }
   
   if(formulaBits[2].equals("Savings")){
   
   d3=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d3=formulaBits[2];
   }
   
   
   
   d4=formulaBits[3];
   
   if(formulaBits[4].equals("Savings")){
   
   d5=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d5=formulaBits[4];
   }
   d6=formulaBits[5];
   
   if(formulaBits[6].equals("Savings")){
   
   d7=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d7=formulaBits[6];
   }
   
   if(formulaBits[7].equals("Savings")){
   
   d8=dbq.accountBalance(accountNumber,sdf.format(new Date(System.currentTimeMillis())));
   
   }else{
   d8=formulaBits[7];
   }
   d9=formulaBits[8].replaceAll(",", "");
   amountFieldDebit8.setValue(maxValue.makeFormulaValue(d1, d2, d3,d4,d5,d6,d7,d8,d9));
    
   
   
   
   } 
    
    
    

break; 
case 3:
 
    


                        break; 
                        case 4:





                        break; 
                        default:


                        break;
                        }

                        jTextField35.setText("From Monthly Income");

                        fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+ accountNumber+".txt"));

                        int windowhhsj =fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+ accountNumber+".txt"));
                        String details=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+accountNumber+".txt"));
                        switch(windowhhsj ){

                        case 1:
//                        jCheckBox52.setVisible(true);
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(true);
                        jComboBox8.setVisible(true);
                        jCheckBox49.setVisible(true);
                        amountFieldDebit8.setVisible(true);
                        jCheckBox51.setVisible(true);
//                        jTextField35.setVisible(true);

//                        jTextField14.setVisible(true);
                        jTextField18.setVisible(true);
                        jComboBox4.setVisible(true);
                        jCheckBox48.setVisible(true);
                        amountFieldDebit7.setVisible(true);
                        jCheckBox50.setVisible(true);
//                        jComboBox2.setVisible(true);

                        jButton3.setEnabled(true);
                        jButton5.setEnabled(false);
                        jButton4.setEnabled(false);
                        jButton1.setEnabled(false);

                        jLabel29.setVisible(true);
                        jLabel10.setVisible(true);
                        jLabel36.setVisible(true);
                        jLabel15.setVisible(true);
                        jLabel28.setVisible(true);
                        jLabel27.setVisible(true);
//                        jLabel23.setVisible(true);
//                        jLabel25.setVisible(true);
                        jLabel45.setVisible(false);

                        jLabel62.setVisible(false);
                        jLabel41.setVisible(false);
                        jLabel49.setVisible(false);
                        jLabel50.setVisible(false);

                        jLabel26.setVisible(false);
                        jLabel42.setVisible(false);
                        jLabel51.setVisible(false);
                        jLabel52.setVisible(false);

                        jLabel37.setVisible(false);
                        jLabel43.setVisible(false);
                        jLabel53.setVisible(false);
                        jLabel54.setVisible(false);

                        jLabel38.setVisible(false);
                        jLabel46.setVisible(false);
                        jLabel55.setVisible(false);
                        jLabel58.setVisible(false);

                        jLabel39.setVisible(false);
                        jLabel47.setVisible(false);
                        jLabel59.setVisible(false);
                        jLabel60.setVisible(false);

                        jTextField36.setEnabled(true);
                        jComboBox8.setEnabled(true);
                        jCheckBox49.setEnabled(true);
                        amountFieldDebit8.setEnabled(true);
                        jCheckBox51.setEnabled(true);
//                        jTextField35.setEnabled(true);
//                        jTextField14.setEnabled(true);
                        jTextField18.setEnabled(true);
                        jComboBox4.setEnabled(true);
                        jCheckBox48.setEnabled(true);
                        amountFieldDebit7.setEnabled(true);
                        jCheckBox50.setEnabled(true);
//                        jComboBox2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel45.setEnabled(true);

                        jComboBox2.setSelectedIndex(1);

                        break;
                        case 2:
                        jCheckBox52.setVisible(false);  
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(false);
                        jComboBox8.setVisible(false);
                        jCheckBox49.setVisible(false);
                        amountFieldDebit8.setVisible(false);
                        jCheckBox51.setVisible(false);
                        jTextField35.setVisible(false);

//                        jTextField14.setVisible(false);
                        jTextField18.setVisible(false);
                        jComboBox4.setVisible(false);
                        jCheckBox48.setVisible(false);
                        amountFieldDebit7.setVisible(false);
                        jCheckBox50.setVisible(false);
                        jComboBox2.setVisible(false);

                        jButton3.setEnabled(false);
                        jButton5.setEnabled(true);
                        jButton4.setEnabled(false);
                        jButton1.setEnabled(false);

                        jLabel29.setVisible(true);
                        jLabel10.setVisible(false);
                        jLabel36.setVisible(false);
                        jLabel15.setVisible(false);
                        jLabel28.setVisible(false);
                        jLabel27.setVisible(false);
                        jLabel23.setVisible(false);
                        jLabel25.setVisible(false);

                        jLabel45.setVisible(true);
                        jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE SECOND STAGE OF DOCUMENTATION&COLLATERAL APPROVAL");
                        jLabel62.setVisible(true);
                        jLabel41.setVisible(true);
                        jLabel41.setText(details.split("[,]", 13)[10]);

                        jLabel49.setVisible(true);
                        jLabel50.setVisible(true);
                        jLabel50.setText(details.split("[,]", 13)[9]);

                        jLabel26.setVisible(true);
                        jLabel42.setVisible(true);
                        jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                        jLabel51.setVisible(true);
                        jLabel52.setVisible(true);

                        jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                        jLabel37.setVisible(true);
                        jLabel43.setVisible(true);
                        jLabel43.setText(details.split("[,]", 13)[4]);

                        jLabel53.setVisible(true);
                        jLabel54.setVisible(true);
                        jLabel54.setText(details.split("[,]", 13)[5]);

                        jLabel38.setVisible(true);
                        jLabel46.setVisible(true);
                        jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                        jLabel55.setVisible(true);
                        jLabel58.setVisible(true);
                        jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                        jLabel39.setVisible(true);
                        jLabel47.setVisible(true);
                        jLabel47.setText(details.split("[,]", 13)[0]);
                        jLabel59.setVisible(true);
                        jLabel60.setVisible(true);
                        jLabel60.setText(details.split("[,]", 13)[8]);

                        jTextField36.setEnabled(true);
                        jComboBox8.setEnabled(true);
                        jCheckBox49.setEnabled(true);
                        amountFieldDebit8.setEnabled(true);
                        jCheckBox51.setEnabled(true);
//                        jTextField35.setEnabled(true);
//                        jTextField14.setEnabled(true);
                        jTextField18.setEnabled(true);
                        jComboBox4.setEnabled(true);
                        jCheckBox48.setEnabled(true);
                        amountFieldDebit7.setEnabled(true);
                        jCheckBox50.setEnabled(true);
//                        jComboBox2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel45.setEnabled(true);
                        jComboBox2.setSelectedIndex(1);
                        break;
                        case 3:
                        jCheckBox52.setVisible(false);  
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(false);
                        jComboBox8.setVisible(false);
                        jCheckBox49.setVisible(false);
                        amountFieldDebit8.setVisible(false);
                        jCheckBox51.setVisible(false);
                        jTextField35.setVisible(false);

//                        jTextField14.setVisible(false);
                        jTextField18.setVisible(false);
                        jComboBox4.setVisible(false);
                        jCheckBox48.setVisible(false);
                        amountFieldDebit7.setVisible(false);
                        jCheckBox50.setVisible(false);
                        jComboBox2.setVisible(false);

                        jButton3.setEnabled(false);
                        jButton5.setEnabled(false);
                        jButton4.setEnabled(true);
                        jButton1.setEnabled(false);

                        jLabel29.setVisible(true);
                        jLabel10.setVisible(false);
                        jLabel36.setVisible(false);
                        jLabel15.setVisible(false);
                        jLabel28.setVisible(false);
                        jLabel27.setVisible(false);
                        jLabel23.setVisible(false);
                        jLabel25.setVisible(false);

                        jLabel45.setVisible(true);
                        jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE SECOND LAST STAGE OF LOAN APPROVALS");

                        jLabel62.setVisible(true);
                        jLabel41.setVisible(true);
                        jLabel41.setText(details.split("[,]", 13)[10]);

                        jLabel49.setVisible(true);
                        jLabel50.setVisible(true);
                        jLabel50.setText(details.split("[,]", 13)[9]);

                        jLabel26.setVisible(true);
                        jLabel42.setVisible(true);
                        jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                        jLabel51.setVisible(true);
                        jLabel52.setVisible(true);

                        jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                        jLabel37.setVisible(true);
                        jLabel43.setVisible(true);
                        jLabel43.setText(details.split("[,]", 13)[4]);

                        jLabel53.setVisible(true);
                        jLabel54.setVisible(true);
                        jLabel54.setText(details.split("[,]", 13)[5]);

                        jLabel38.setVisible(true);
                        jLabel46.setVisible(true);
                        jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                        jLabel55.setVisible(true);
                        jLabel58.setVisible(true);
                        jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                        jLabel39.setVisible(true);
                        jLabel47.setVisible(true);
                        jLabel47.setText(details.split("[,]", 13)[0]);
                        jLabel59.setVisible(true);
                        jLabel60.setVisible(true);
                        jLabel60.setText(details.split("[,]", 13)[8]);

                        jTextField36.setEnabled(true);
                        jComboBox8.setEnabled(true);
                        jCheckBox49.setEnabled(true);
                        amountFieldDebit8.setEnabled(true);
                        jCheckBox51.setEnabled(true);
//                        jTextField35.setEnabled(true);
//                        jTextField14.setEnabled(true);
                        jTextField18.setEnabled(true);
                        jComboBox4.setEnabled(true);
                        jCheckBox48.setEnabled(true);
                        amountFieldDebit7.setEnabled(true);
                        jCheckBox50.setEnabled(true);
//                        jComboBox2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel45.setEnabled(true);
                        jComboBox2.setSelectedIndex(1);
                        break;
                        case 4:
                        jCheckBox52.setVisible(false);  
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(false);
                        jComboBox8.setVisible(false);
                        jCheckBox49.setVisible(false);
                        amountFieldDebit8.setVisible(false);
                        jCheckBox51.setVisible(false);
                        jTextField35.setVisible(false);

//                        jTextField14.setVisible(false);
                        jTextField18.setVisible(false);
                        jComboBox4.setVisible(false);
                        jCheckBox48.setVisible(false);
                        amountFieldDebit7.setVisible(false);
                        jCheckBox50.setVisible(false);
                        jComboBox2.setVisible(false);
                        jLabel29.setVisible(true);
                        jLabel10.setVisible(false);
                        jLabel36.setVisible(false);
                        jLabel15.setVisible(false);
                        jLabel28.setVisible(false);
                        jLabel27.setVisible(false);
                        jLabel23.setVisible(false);
                        jLabel25.setVisible(false);
                        jButton3.setEnabled(false);
                        jButton5.setEnabled(false);
                        jButton4.setEnabled(false);
                        jButton1.setEnabled(true);

                        jLabel45.setVisible(true);
                        jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE FINAL STAGE OF LOAN PROCESSING");
                        jLabel62.setVisible(true);
                        jLabel41.setVisible(true);
                        jLabel41.setText(details.split("[,]", 13)[10]);

                        jLabel49.setVisible(true);
                        jLabel50.setVisible(true);
                        jLabel50.setText(details.split("[,]", 13)[9]);

                        jLabel26.setVisible(true);
                        jLabel42.setVisible(true);
                        jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                        jLabel51.setVisible(true);
                        jLabel52.setVisible(true);

                        jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                        jLabel37.setVisible(true);
                        jLabel43.setVisible(true);
                        jLabel43.setText(details.split("[,]", 13)[4]);

                        jLabel53.setVisible(true);
                        jLabel54.setVisible(true);
                        jLabel54.setText(details.split("[,]", 13)[5]);

                        jLabel38.setVisible(true);
                        jLabel46.setVisible(true);
                        jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                        jLabel55.setVisible(true);
                        jLabel58.setVisible(true);
                        jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                        jLabel39.setVisible(true);
                        jLabel47.setVisible(true);
                        jLabel47.setText(details.split("[,]", 13)[0]);
                        jLabel59.setVisible(true);
                        jLabel60.setVisible(true);
                        jLabel60.setText(details.split("[,]", 13)[8]);

                        jTextField36.setEnabled(true);
                        jComboBox8.setEnabled(true);
                        jCheckBox49.setEnabled(true);
                        amountFieldDebit8.setEnabled(true);
                        jCheckBox51.setEnabled(true);
//                        jTextField35.setEnabled(true);
//                        jTextField14.setEnabled(true);
                        jTextField18.setEnabled(true);
                        jComboBox4.setEnabled(true);
                        jCheckBox48.setEnabled(true);
                        amountFieldDebit7.setEnabled(true);
                        jCheckBox50.setEnabled(true);
//                        jComboBox2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel45.setEnabled(true);
                        jComboBox2.setSelectedIndex(1);
                        break;
                        case 5:
                        jCheckBox52.setVisible(false);  
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(true);
                        jTextField36.setEnabled(false);
                        jComboBox8.setVisible(true);
                        jComboBox8.setEnabled(false);
                        jCheckBox49.setVisible(true);
                        jCheckBox49.setEnabled(false);
                        amountFieldDebit8.setVisible(true);
                        amountFieldDebit8.setEnabled(false);

                        jCheckBox51.setVisible(true);
                        jCheckBox51.setEnabled(false);
                        jTextField35.setVisible(true);
                        jTextField35.setEnabled(false);
//                        jTextField14.setVisible(true);
//                        jTextField14.setEnabled(false);
                        jTextField18.setVisible(true);
                        jTextField18.setEnabled(false);
                        jComboBox4.setVisible(true);
                        jComboBox4.setEnabled(false);

                        jCheckBox48.setVisible(true);
                        jCheckBox48.setEnabled(false);

                        amountFieldDebit7.setVisible(true);
                        amountFieldDebit7.setEnabled(false);
                        jCheckBox50.setVisible(true);
                        jCheckBox50.setEnabled(false);
//                        jComboBox2.setVisible(true);
                        jComboBox2.setEnabled(false);
                        jButton3.setEnabled(true);
                        jButton3.setEnabled(false);
                        jButton5.setEnabled(false);

                        jButton4.setEnabled(false);
                        jButton1.setEnabled(false);

                        jLabel29.setVisible(true);

                        jLabel10.setVisible(true);
                        jLabel10.setEnabled(false);
                        jLabel36.setVisible(true);
                        jLabel36.setEnabled(false);
                        jLabel15.setVisible(true);
                        jLabel15.setEnabled(false);
                        jLabel28.setVisible(true);
                        jLabel28.setEnabled(false);
                        jLabel27.setVisible(true);
                        jLabel27.setEnabled(false);
//                        jLabel23.setVisible(true);
                        jLabel23.setEnabled(false);
                        jLabel25.setVisible(true);
                        jLabel25.setEnabled(false);
                        jLabel45.setVisible(true);
                        jLabel45.setEnabled(true);
                        jLabel62.setVisible(false);
                        jLabel41.setVisible(false);
                        jLabel49.setVisible(false);
                        jLabel50.setVisible(false);

                        jLabel26.setVisible(false);
                        jLabel42.setVisible(false);
                        jLabel51.setVisible(false);
                        jLabel52.setVisible(false);

                        jLabel37.setVisible(false);
                        jLabel43.setVisible(false);
                        jLabel53.setVisible(false);
                        jLabel54.setVisible(false);

                        jLabel38.setVisible(false);
                        jLabel46.setVisible(false);
                        jLabel55.setVisible(false);
                        jLabel58.setVisible(false);

                        jLabel39.setVisible(false);
                        jLabel47.setVisible(false);
                        jLabel59.setVisible(false);
                        jLabel60.setVisible(false);
                        jLabel45.setText("THE  LOAN PROCESSING WAS COMPLETED AND THE LOAN WAS DISBURSED TO THE BORROWER");

                        jButton14.setEnabled(true);
//                         jButton16.setEnabled(true);
//                        jButton15.setEnabled(false);
                        jTextField36.setText(" ");
                        jComboBox8.setSelectedItem(" ");
                        amountFieldDebit8.setValue(null);
                        jTextField35.setText(" ");
                        jComboBox2.setSelectedItem(" ");
                        jComboBox4.setSelectedItem(" ");


                        break;
                        case 6:
//                        jCheckBox52.setVisible(true);
                        jComboBox3.setVisible(true);
                        jTextField36.setVisible(true);
                        jComboBox8.setVisible(true);
                        jCheckBox49.setVisible(true);
                        amountFieldDebit8.setVisible(true);
                        jCheckBox51.setVisible(true);
//                        jTextField35.setVisible(true);

//                        jTextField14.setVisible(true);
                        jTextField18.setVisible(true);
                        jComboBox4.setVisible(true);
                        jCheckBox48.setVisible(true);
                        amountFieldDebit7.setVisible(true);
                        jCheckBox50.setVisible(true);
//                        jComboBox2.setVisible(true);

                        jButton3.setEnabled(true);
                        jButton5.setEnabled(false);
                        jButton4.setEnabled(false);
                        jButton1.setEnabled(false);

                        jLabel29.setVisible(true);
                        jLabel10.setVisible(true);
                        jLabel36.setVisible(true);
                        jLabel15.setVisible(true);
                        jLabel28.setVisible(true);
                        jLabel27.setVisible(true);
//                        jLabel23.setVisible(true);
//                        jLabel25.setVisible(true);
                        jLabel45.setVisible(false);

                        jLabel62.setVisible(false);
                        jLabel41.setVisible(false);
                        jLabel49.setVisible(false);
                        jLabel50.setVisible(false);

                        jLabel26.setVisible(false);
                        jLabel42.setVisible(false);
                        jLabel51.setVisible(false);
                        jLabel52.setVisible(false);

                        jLabel37.setVisible(false);
                        jLabel43.setVisible(false);
                        jLabel53.setVisible(false);
                        jLabel54.setVisible(false);

                        jLabel38.setVisible(false);
                        jLabel46.setVisible(false);
                        jLabel55.setVisible(false);
                        jLabel58.setVisible(false);

                        jLabel39.setVisible(false);
                        jLabel47.setVisible(false);
                        jLabel59.setVisible(false);
                        jLabel60.setVisible(false);

                        jTextField36.setEnabled(true);
                        jComboBox8.setEnabled(true);
                        jCheckBox49.setEnabled(true);
                        amountFieldDebit8.setEnabled(true);
                        jCheckBox51.setEnabled(true);
//                        jTextField35.setEnabled(true);
//                        jTextField14.setEnabled(true);
                        jTextField18.setEnabled(true);
                        jComboBox4.setEnabled(true);
                        jCheckBox48.setEnabled(true);
                        amountFieldDebit7.setEnabled(true);
                        jCheckBox50.setEnabled(true);
//                        jComboBox2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel45.setEnabled(true);

                        jComboBox2.setSelectedIndex(1);      

                        jTextField36.setText(fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[0]);
                        jComboBox8.setSelectedItem(fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[1]);
                        jComboBox8.setEditable(false);                

                        jComboBox3.setSelectedItem(dbq.AccountName(accountNumber));
                        break;              

                        }


                        jComboBox3.setSelectedItem(dbq.AccountName(accountNumber));

                        }
        
        else if(valueLoan==2){
// JOptionPane.showMessageDialog(this, "Rukoye");
            column1 =new ArrayList<>();
 
       column1.add("INST.NO");
       column1.add("P AMT");
//       column1.add("OP AMT");
       column1.add("I AMT");
//       column1.add("OI AMT");
//        column1.add("ACCI AMT");
//       column1.add("OACCI AMT");
//       column1.add("LP AMT");
//       column1.add("OLP AMT");
       column1.add("INST AMT");
//       column1.add("OINST AMT");
       column1.add("INST. DD");
       column1.add("INST. PS");
       column1.add("INST. P");
      column1.add("INST. PD");
      column1.add("PV (DAYS)");
      
  loan.loanClosedTableFill(jTable2, column1,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt")));   
   
  loan.loanSecurity(jTable1, fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt")),this);
  
  jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(20);

jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(20);
jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
        String itO=jTable2.getModel().getValueAt(row, 8).toString();
        
        if(itO.equalsIgnoreCase("NY")){
        
        itO="0";
        
        }else if(itO.isEmpty()){
        
           itO="0";
        }
        
//JOptionPane.showMessageDialog(this, itO);

        Integer cvalue1 = parseInt(itO);
        
          long cvalue =cvalue1.longValue();
          
     this.setHorizontalAlignment(JLabel.CENTER);
      if(col==1||col==2||col==3||col==6){
          
          String cv=value.toString();
          if(cv.equalsIgnoreCase("NY")){
          cv="0.0";
          }
          Number c = (Number)parseDouble(cv.replace(",", ""));
       String text = NumberFormat.format(c );
           this.setText(text);
      
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue==0){
        setBackground(Color.GREEN);
            setForeground(Color.BLACK);
         
           
        } else if(cvalue>0) {
          setBackground(Color.orange);
           setForeground(Color.BLACK);
 
              
                     
        }  else if(cvalue<0&&cvalue>=-30) {
             setBackground(jButton3.getBackground());
            setForeground(Color.BLACK);
              
        }     
        
           else if(cvalue<-30&&cvalue>=-60) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
                          
        }else if(cvalue<-60){
         setBackground(Color.RED);
             setForeground(Color.BLUE);
        
        }
      if(isSelected){setBackground(Color.CYAN);
          setForeground(Color.BLUE);
          
          }    
        return this;
    }   
});

jTabbedPane1.setVisible(true);
 jDesktopPane1.setVisible(false);
  loanClosedPanel.setVisible(true);  
//  jDesktopPane1.setVisible(false);
newLoanPanel.setVisible(false);
//loanClosedPanel.setVisible(false);
  
//    jButton15  .setVisible(false);      
      jButton14  .setVisible(false);
//       jButton16.setEnabled(false);
       jLabel61.setVisible(true);
        jLabel61.setText("LOAN REPAYMENT HISTORY FOR "+"  "+dbq.AccountName(accountNumber)+" "+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"))+"-"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt")));
       
        }else if(valueLoan==3){
    
      column1 =new ArrayList<>();
       column1.add("INST.NO");
       column1.add("P AMT");
       column1.add("P PD");
       column1.add("I AMT");
       column1.add("I PD");
        column1.add("ACCI AMT");
       column1.add("ACCI PD");
       column1.add("LP AMT");
       column1.add("LP PD");
       column1.add("INST AMT");
       column1.add("OINST AMT");
        column1.add("INST. PD");
       column1.add("INST. DD");
       column1.add("INST. PS"); 
      column1.add("INST. PDD");
      column1.add("PV (DAYS)");
      
  loan.loanRunningLoanFill(jTable2, column1,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "runningLoanAccount.txt"))); 
  
  loan.loanSecurity(jTable1, fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "runningLoanAccount.txt")),this);
  
  jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(20);

jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(20);
jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
       String cvalue1 = jTable2.getModel().getValueAt(row, 13).toString();
       
     this.setHorizontalAlignment(JLabel.CENTER);
      if(col>=1&&col<=11){
          Number c = (Number)parseDouble(value.toString().replace(",", ""));
       String text = NumberFormat.format(c );
           this.setText(text);
      
      }else if(col==11){
      if(!(value.toString().equalsIgnoreCase("NY"))){
         Number c = (Number)parseDouble(value.toString());
       String text = NumberFormat.format(c );
           this.setText(text);
      }else{
      
      this.setText(value.toString());
      
      }
      
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue1.equals("NY")){
        setBackground(Color.WHITE);
            setForeground(Color.BLACK);
       
        
           
        } else if(cvalue1.equals("PP")) {
          setBackground(Color.BLUE);
           setForeground(Color.WHITE);
        
              
                     
        }  else if(cvalue1.equals("NP")) {
             setBackground(Color.RED);
            setForeground(Color.BLACK);
    
              
        }     
        
           else if(cvalue1.equals("P")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
       
                     
        }
          if(isSelected){setBackground(Color.CYAN);
          setForeground(Color.BLUE);
          
          } 
        return this;
    }   
});

jTabbedPane1.setVisible(true);
 newLoanPanel.setVisible(false);
 jDesktopPane1.setVisible(false);
  loanClosedPanel.setVisible(true);          
//    jButton15  .setVisible(false);      
      jButton14  .setVisible(false);
//       jButton16  .setVisible(false);
       jLabel61.setVisible(true);
        jLabel61.setText("LOAN REPAYMENT FOR "+"  "+dbq.AccountName(accountNumber)+"-"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "runningLoanAccount.txt")));    
        
        
        
        
        
        }else if(valueLoan==4){
        jTabbedPane1.setVisible(false);
          String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
         if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){     
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
                        case 1:
  if(!(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 2:
  if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 3:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 4:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 5:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      }
                        break;
                    }
         }
        
    if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWin"+borrowerAccount+".txt"))==3){
        jTabbedPane1.setVisible(false);
        guarantorSetupPanel.setVisible(true);
        loanClosedPanel.setVisible(false);
jLabel61.setText("Guarantors for:"+" "+dbq.AccountName(accountNumber)+";"+" "+"Account Number:"+" "+accountNumber);
jButton14.setVisible(false);
//jButton16.setVisible(false);
// jButton15.setVisible(false);
    jLabel61.setVisible(true);
    }else if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWin"+borrowerAccount+".txt"))==5){
           jCheckBox52.setVisible(false);
           jLabel63.setVisible(false);
      
        jDesktopPane1.setVisible(true);
        jTabbedPane1.setVisible(false);
        guarantorSetupPanel.setVisible(false);
        loanClosedPanel.setVisible(false);
        jComboBox3.setSelectedItem(borrowerAccount);
        
//        updateFields(jTextField14,borrowerAccount);
        
        
jTextField18.setEnabled(false);
amountFieldDebit7.setEnabled(false);
jComboBox4.setEnabled(false);
jCheckBox49.setSelected(true);
jCheckBox51.setSelected(true);
        
        amountFieldDebit8.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedAfterGuarantors"+borrowerAccount+".txt"))));
   }
    
    
        Integer xG = 9;
        fios.intFileWriterReplace( fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+borrowerAccount+".txt") , xG.toString());
        }else if(valueLoan==5){
        jTabbedPane1.setVisible(false);
         theCollection=new ArrayList();
  processLoanRequest( accountNumber);  
  jComboBox3.setSelectedItem(dbq.AccountName(accountNumber));
        }else if(valueLoan==6){
        
        
        column1 =new ArrayList<>();
       column1.add("INST.NO");
       column1.add("O/S P AMT");
       column1.add("O/S I AMT");
        column1.add("O/S ACCI AMT");
       column1.add("O/S LP AMT");
       column1.add("O/S INST AMT");
       column1.add("INST. DD");
      column1.add("PV (DAYS)");
      
  loan.loanWrittenOffLoanFill(jTable2, column1,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"))); 
  
  loan.loanSecurity(jTable1, fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt")),this);
  
  jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(40);

jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(40);

  HeaderRenderer header5 = new HeaderRenderer(jTable2.getTableHeader().getDefaultRenderer());
       
          int hckx3f=0;
//        this.setHorizontalAlignment(JLabel.LEFT);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
        jTable2.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckx3f<jTable2.getColumnModel().getColumnCount()){
        jTable2.getColumnModel().getColumn(hckx3f).setHeaderRenderer(header5);

        if(hckx3f==0){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(60);
        }
        if(hckx3f==1){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }
        if(hckx3f==2){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }if(hckx3f==3){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }if(hckx3f==4){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }
        
        if(hckx3f==5){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }
        if(hckx3f==6){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(150);
        }
        if(hckx3f==7){
        jTable2.getColumnModel().getColumn(hckx3f).setMinWidth(0);
        jTable2.getColumnModel().getColumn(hckx3f).setMaxWidth(1500);
        jTable2.getColumnModel().getColumn(hckx3f).setPreferredWidth(100);
        }
        
        
        hckx3f++;

        }
       jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


       
            
        this.setFont(new Font("Arial",Font.PLAIN,23));
        
        

            if(col>=1&&col<=5){
            this.setHorizontalAlignment(RIGHT);
                        String text="";
       
     
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "").replaceAll(";", "") );
        
        

            this.setText(text);
            }else{
            this.setText(value.toString());
            }
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

jTabbedPane1.setVisible(true);
 newLoanPanel.setVisible(false);
 jDesktopPane1.setVisible(false);
  loanClosedPanel.setVisible(true);          
//    jButton15  .setVisible(false);      
      jButton14  .setVisible(false);
//       jButton16  .setVisible(false);
       jLabel61.setVisible(true);
        jLabel61.setText("LOAN WRITTEN OFF FOR "+"  "+dbq.AccountName(accountNumber)+"-"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt")));    
        
        
        
        
        
        
        
        }else{
        jTabbedPane1.setVisible(false);
          jDesktopPane1.setVisible(true);
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
  
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(dbq.newLedgerCreated()){
JOptionPane.showMessageDialog(this, "A NEW LEDGER HAS BEEN CREATED\n\nPLEASE FIRST ACTIVATE IT BEFORE YOU PROCEED");
return;
}else{  
        
        String accountg=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));       
        if( fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusPostion"+accountg+".txt"))==46){
      
           JOptionPane.showMessageDialog(this, fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"))+".txt")).split("[,]", 15)[10]+"'s"+" "+"Loan Needs To Be Manually Posted");
           JOptionPane.showMessageDialog(this, "You can only disburse UGX"+" "+fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "disbursementStatusAmount"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"))+".txt")));

            PostingEntryWindow f = new PostingEntryWindow (userId,amortDetails);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();   
        
        } else{     
             
         LoanProcessing f = new LoanProcessing(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();        
                }  
}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      int i=1;
     while(i<=5){
     
     JOptionPane.showMessageDialog(this, "SYSTEM LOG OUT ERROR!!!!!!!!!");}
    }//GEN-LAST:event_formWindowClosing

    private void jLabel61MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel61MousePressed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
   
        String action=jComboBox5.getSelectedItem().toString();
        switch(action){
            case "RETURN TO LOAN MANAGEMENT":
               LoanManagmentWindow frm5 = new LoanManagmentWindow(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
        this.dispose();  
                break;
        
        case "PROCEED TO NEW LOAN APPLICATION":
             Integer xl=0;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());
     fios.intFileWriterReplace(fios.createFileName("persistence", "topUpsSetUp", "byPassLoanPosting.txt"), "77");    
   NewLoanApplication frm = new NewLoanApplication(userId);
        frm.setVisible(true);
        Dimension screens = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screens.getSize());
        frm.pack();
        this.dispose();   
                break;
        
        }
        
        

    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
  
        String accountNumber=dbq.getAccountNumber(jComboBox3.getSelectedItem().toString());
       
               dbq.feelWithAccountNumbers(jComboBox13,jComboBox3.getSelectedItem().toString());
                theCollection=new ArrayList();

                 
        processLoanRequest(accountNumber);
       
////       this.updateDefaults(accountNumber);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void bneedsLabel64MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bneedsLabel64MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bneedsLabel64MousePressed

    private void gballowedLabel65MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gballowedLabel65MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gballowedLabel65MousePressed

    private void gnamwLabel66MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gnamwLabel66MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gnamwLabel66MousePressed

    private void gacnoLabel67MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gacnoLabel67MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gacnoLabel67MousePressed

    private void actionLabel69MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actionLabel69MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_actionLabel69MousePressed

    private void ggallowedLabel70MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ggallowedLabel70MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ggallowedLabel70MousePressed

    private void bhasLabel71MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bhasLabel71MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bhasLabel71MousePressed

    private void amountFieldDebit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit2ActionPerformed

    private void amountFieldDebit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit3ActionPerformed

    private void amountFieldDebit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit4ActionPerformed

    private void amountFieldDebit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit5ActionPerformed

    private void amountFieldDebit6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit6ActionPerformed

    private void amountFieldDebit9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit9ActionPerformed

    private void amountFieldDebit10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit10ActionPerformed

    private void amountFieldDebit11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit11ActionPerformed

    private void amountFieldDebit12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit12ActionPerformed

    private void amountFieldDebit13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit13ActionPerformed

    private void amountFieldDebit14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit14ActionPerformed

    private void amountFieldDebit15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit15ActionPerformed

    private void amountFieldDebit16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit16ActionPerformed

    private void amountFieldDebit17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit17ActionPerformed

    private void amountFieldDebit18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit18ActionPerformed

    private void amountFieldDebit19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit19ActionPerformed

    private void amountFieldDebit20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit20ActionPerformed

    private void amountFieldDebit21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit21ActionPerformed

    private void amountFieldDebit22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit22ActionPerformed

    private void amountFieldDebit23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit23ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed

    String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
    
    if(!borrowerAccount.isEmpty()){
        
    dbq.feelWithCustomersAccounts(jComboBox10.getSelectedItem().toString(), guarantoursAccount5);

    if(guarantoursAccount5.getText().equalsIgnoreCase(borrowerAccount)){

    JOptionPane.showMessageDialog(this, jComboBox10.getSelectedItem().toString()+" "+"CANNOT GUARANTEE THE SAME");
    guarantoursAccount5.setText("");
    jComboBox10.setSelectedItem("");
    return;
    }

//    String guarantorsAccount=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "GuarantorsAccount"+borrowerAccount+".txt"));

    if(!guarantoursAccount5.getText().equalsIgnoreCase("")){

    amountFieldDebit2.setValue((0.5*mxb.maxAmountBorrowedF(guarantoursAccount5.getText())+0.5*mxb.maxAmountBorrowed(guarantoursAccount5.getText())));
    amountFieldDebit9.setValue((0.5*mxb.maxAmountBorrowed(guarantoursAccount5.getText())));
    
    amountFieldDebit14.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"))));    
    amountFieldDebit19.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt")))); 

    }

    }
        
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void submitButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButton2ActionPerformed

        
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        fios.forceFileExistanceZero(borrowerAccount);
        Integer xc=4;
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xc.toString());
      Integer xG = 10;
      fios.intFileWriterReplace( fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+borrowerAccount+".txt") , xG.toString());

      Integer xGSH = 16;
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "guarantorSetupLoan"+borrowerAccount+ ".txt"),xGSH.toString());
      
       fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedAfterGuarantors"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt")));
       
       Integer cv=5;
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWin"+borrowerAccount+".txt"),cv.toString());
       
       
       NewLoanApplication f = new NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
        
        
    }//GEN-LAST:event_submitButton2ActionPerformed

    private void firstCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstCheckBox2ActionPerformed
                 setBatchNumber();
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox10.getSelectedItem()==null&&jComboBox10.isVisible()==true||guarantoursAccount5.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         firstCheckBox2.setSelected(false);
         return;
        } else if(amountFieldDebit24.getValue()==null)  {JOptionPane.showMessageDialog(this, "Please Input the Amount the Gaurantor is willing to guarantee");firstCheckBox2.setSelected(false);
         return;}else if(jButton8.isEnabled()){
         
          JOptionPane.showMessageDialog(this, "Please synchronise the amount allowed to be guaranteed first");
         firstCheckBox2.setSelected(false);
         return;
         
         
         }
        
        if(firstCheckBox2.isSelected()){
            secondCheckBox2.setEnabled(true);
           jButton8.setEnabled(false);
           guarantoursAccount10.setText(jComboBox10.getSelectedItem().toString());
           guarantoursAccount10.setVisible(true);
           guarantoursAccount10.setEditable(false);
            guarantoursAccount5.setText(guarantoursAccount5.getText());
            guarantoursAccount5.setEditable(false);
            jComboBox10.setVisible(false);
            amountFieldDebit2.setEditable(false);
            amountFieldDebit9.setEditable(false);
                amountFieldDebit24.setEditable(false);
                 amountFieldDebit14.setEditable(false);
                  amountFieldDebit19.setEditable(false);
                  Integer ty=35;
         fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+borrowerAccount+".txt"),ty.toString());
          fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowedC"+borrowerAccount+".txt"),ty.toString());
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=7){
               if(z==1){
              var1=guarantoursAccount10.getText();
               }
               if(z==2){
              var1=guarantoursAccount5.getText();
               }
                if(z==3){
              var1=amountFieldDebit2.getValue().toString();
               }
                 if(z==4){
              var1=amountFieldDebit9.getValue().toString();
               }
                 if(z==5){
              var1=amountFieldDebit24.getValue().toString();
               }
                  if(z==6){
              var1=amountFieldDebit14.getValue().toString();
               }
                  if(z==7){
              var1=amountFieldDebit19.getValue().toString();
               } 
            fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt"), var1);
            fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt"), ";");
            z++;
            }

            fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
            fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));

            Double totalGuarantor=  parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt")));//Select the total amount allowed to guarantee from the existing total of amount guaranteed to the specefici borrower.
            Double totalGuarantors=totalGuarantor+parseDouble(amountFieldDebit24.getValue().toString());//Add the amount allowed to guarantee to the existing total of amount guaranteed to the specefici borrower.
            fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"),totalGuarantors.toString());//save the tatol in two files, one which will remain constant and the other which will be reduced as we loop over according to the amount paid back.
            fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),totalGuarantors.toString());   
          
            Integer tx=300;
            fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"),tx.toString()); 

            fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt")).split("[;]", 7)[4]); //Set the amount allowed to gurantee after the accept chebox is selected and the amount has been set in the file 
            Integer xc=1;
            fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"),xc.toString());

            
            debitHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt")).split("[;]", 7)[1],  hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"))));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
            
            
            debitTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"))));//Send the remaining 25% to database to be debited from customer to the one of the sacco,s current accounts.

            
        }  else if(!firstCheckBox2.isSelected()){
             Integer ty=30;
         fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+borrowerAccount+".txt"),ty.toString());
             secondCheckBox2.setEnabled(true);
               Integer tx=320;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount10.setVisible(false);
           guarantoursAccount10.setText("");
            guarantoursAccount10.setEditable(true);
           jComboBox10.setVisible(true);
            jComboBox10.setSelectedItem("");
            guarantoursAccount5.setEditable(true);
            amountFieldDebit2.setEditable(true);
            amountFieldDebit9.setEditable(true);
                amountFieldDebit24.setEditable(true);
                 amountFieldDebit14.setEditable(true);
                  amountFieldDebit19.setEditable(true);
                  
            amountFieldDebit2.setValue(null);
            amountFieldDebit9.setValue(null);
                amountFieldDebit24.setValue(null);
                 amountFieldDebit14.setValue(null);
                  amountFieldDebit19.setValue(null);
     
          jButton8.setEnabled(true); 
           if( creditTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"))))){
              creditHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore1"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee1"+borrowerAccount+".txt"))));
         }
        
        }else{return;} 
          
    }//GEN-LAST:event_firstCheckBox2ActionPerformed

    private void refreshButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButton2ActionPerformed

        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
         if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){     
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
                        case 1:
  if(!(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300) ){
      

      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 2:
  if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 3:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 4:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      } 
                        break;
                        case 5:
 if(!((fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"))==300)||(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowerAccount+".txt"))==300)) ){
      
      setBorrow(borrowerAccount);
      
      }
                        break;
                    }
         }
        
//        NewLoanApplication f = new NewLoanApplication(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
//        this.dispose();
    }//GEN-LAST:event_refreshButton2ActionPerformed

    private void gallowamtLabel68MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gallowamtLabel68MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gallowamtLabel68MousePressed

    private void amountFieldDebit25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit25ActionPerformed

    private void amountFieldDebit26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit26ActionPerformed

    private void amountFieldDebit27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit27ActionPerformed

    private void amountFieldDebit28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit28ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
          
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
        
        case 5:
     
     
   
 if(amountFieldDebit28.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  
  if(jButton10.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  
  if(jButton11.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 if(jComboBox7.isVisible()&&jComboBox9.isVisible()){
 if(jComboBox7.getSelectedItem().toString().equalsIgnoreCase(jComboBox9.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHas=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed=parseDouble(amountFieldDebit28.getValue().toString());
    Double amountGuarantor=parseDouble(amountFieldDebit13.getValue().toString());
    Double difference=(parseDouble(amountFieldDebit18.getValue().toString())-amountBorrowerHas);
    if(amountAllowed>difference){
        
  if(difference>amountGuarantor){
      
  amountFieldDebit6.setValue(parseDouble(amountFieldDebit6.getValue().toString())-amountGuarantor);
amountFieldDebit13.setValue(parseDouble(amountFieldDebit13.getValue().toString())-amountGuarantor);
amountFieldDebit28.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit23.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

       
  

  }else{
        
amountFieldDebit6.setValue(parseDouble(amountFieldDebit6.getValue().toString())-difference);
amountFieldDebit13.setValue(parseDouble(amountFieldDebit13.getValue().toString())-difference);
amountFieldDebit28.setValue(difference);
double now=amountBorrowerHas+difference;
amountFieldDebit23.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor<amountAllowed){
    
    amountFieldDebit6.setValue(parseDouble(amountFieldDebit6.getValue().toString())-amountGuarantor);
amountFieldDebit13.setValue(parseDouble(amountFieldDebit13.getValue().toString())-amountGuarantor);
amountFieldDebit28.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit23.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit6.setValue(parseDouble(amountFieldDebit6.getValue().toString())-amountAllowed);
amountFieldDebit13.setValue(parseDouble(amountFieldDebit13.getValue().toString())-amountAllowed);
amountFieldDebit28.setValue(amountAllowed);
double now=amountBorrowerHas+amountAllowed;
amountFieldDebit23.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton7.setEnabled(false);
                    
                    }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        
          String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
          
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
                    case 1:
       
   
 if(amountFieldDebit24.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
     Double amountBorrowerHas= parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed=parseDouble(amountFieldDebit24.getValue().toString());
    Double amountGuarantor=parseDouble(amountFieldDebit9.getValue().toString());
    Double difference=(parseDouble(amountFieldDebit14.getValue().toString())-amountBorrowerHas);
    if(amountAllowed>difference){
        
  if(difference>amountGuarantor){
      
  amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor);
amountFieldDebit24.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-difference);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-difference);
amountFieldDebit24.setValue(difference);
double now=amountBorrowerHas+difference;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor<amountAllowed){
    
    amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor);
amountFieldDebit24.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountAllowed);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountAllowed);
amountFieldDebit24.setValue(amountAllowed);
double now=amountBorrowerHas+amountAllowed;
amountFieldDebit19.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton8.setEnabled(false);


    
 break;
                        

 case 2:
       
   
 if(amountFieldDebit24.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
     Double amountBorrowerHas2=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed2=parseDouble(amountFieldDebit24.getValue().toString());
    Double amountGuarantor2=parseDouble(amountFieldDebit9.getValue().toString());
    Double difference2=(parseDouble(amountFieldDebit14.getValue().toString())-amountBorrowerHas2);
    if(amountAllowed2>difference2){
        
  if(difference2>amountGuarantor2){
      
  amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor2);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor2);
amountFieldDebit24.setValue(amountGuarantor2);
double now=amountBorrowerHas2+amountGuarantor2;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-difference2);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-difference2);
amountFieldDebit24.setValue(difference2);
double now=amountBorrowerHas2+difference2;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor2<amountAllowed2){
    
    amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor2);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor2);
amountFieldDebit24.setValue(amountGuarantor2);
double now=amountBorrowerHas2+amountGuarantor2;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountAllowed2);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountAllowed2);
amountFieldDebit24.setValue(amountAllowed2);
double now=amountBorrowerHas2+amountAllowed2;
amountFieldDebit19.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton8.setEnabled(false);


    
 
 if(parseDouble(amountFieldDebit14.getValue().toString())==parseDouble(amountFieldDebit19.getValue().toString())){
jComboBox1.setVisible(false);
guarantoursAccount11.setVisible(false);
guarantoursAccount6.setVisible(false);
amountFieldDebit3.setVisible(false);
amountFieldDebit10.setVisible(false);
amountFieldDebit25.setVisible(false);
jButton9.setVisible(false);
        amountFieldDebit15.setVisible(false);
        amountFieldDebit20.setVisible(false);
                secondCheckBox2.setVisible(false);
                
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedSecondGuarantorB1"+borrowersAccount+".txt"),cv.toString());                                        

}else{
 jComboBox1.setVisible(true);
guarantoursAccount11.setVisible(true);
guarantoursAccount6.setVisible(true);
amountFieldDebit3.setVisible(true);
amountFieldDebit10.setVisible(true);
amountFieldDebit25.setVisible(true);
jButton9.setVisible(true);
        amountFieldDebit15.setVisible(true);
        amountFieldDebit20.setVisible(true);
                secondCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedSecondGuarantorB1"+borrowersAccount+".txt"),cv.toString());             

          }
break; 
     
     case 3:
       
   
 if(amountFieldDebit24.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
     Double amountBorrowerHas3= parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed3=parseDouble(amountFieldDebit24.getValue().toString());
    Double amountGuarantor3=parseDouble(amountFieldDebit9.getValue().toString());
    Double difference3=(parseDouble(amountFieldDebit14.getValue().toString())-amountBorrowerHas3);
    if(amountAllowed3>difference3){
        
  if(difference3>amountGuarantor3){
      
  amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor3);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor3);
amountFieldDebit24.setValue(amountGuarantor3);
double now=amountBorrowerHas3+amountGuarantor3;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-difference3);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-difference3);
amountFieldDebit24.setValue(difference3);
double now=amountBorrowerHas3+difference3;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor3<amountAllowed3){
    
    amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor3);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor3);
amountFieldDebit24.setValue(amountGuarantor3);
double now=amountBorrowerHas3+amountGuarantor3;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountAllowed3);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountAllowed3);
amountFieldDebit24.setValue(amountAllowed3);
double now=amountBorrowerHas3+amountAllowed3;
amountFieldDebit19.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton8.setEnabled(false);


if(parseDouble(amountFieldDebit14.getValue().toString())==parseDouble(amountFieldDebit19.getValue().toString())){

    jComboBox1.setVisible(false);
guarantoursAccount11.setVisible(false);
guarantoursAccount6.setVisible(false);
amountFieldDebit3.setVisible(false);
amountFieldDebit10.setVisible(false);
amountFieldDebit25.setVisible(false);
jButton9.setVisible(false);
        amountFieldDebit15.setVisible(false);
        amountFieldDebit20.setVisible(false);
                secondCheckBox2.setVisible(false);
                
               jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
              
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB1"+borrowersAccount+".txt"),cv.toString());                                        

}else{
 jComboBox1.setVisible(true);
guarantoursAccount11.setVisible(true);
guarantoursAccount6.setVisible(true);
amountFieldDebit3.setVisible(true);
amountFieldDebit10.setVisible(true);
amountFieldDebit25.setVisible(true);
jButton9.setVisible(true);
        amountFieldDebit15.setVisible(true);
        amountFieldDebit20.setVisible(true);
                secondCheckBox2.setVisible(true);
                
           jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
              
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB1"+borrowersAccount+".txt"),cv.toString());             

          }

break; 
         
         case 4:
     
  
 if(amountFieldDebit24.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
     Double amountBorrowerHas4= parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed4=parseDouble(amountFieldDebit24.getValue().toString());
    Double amountGuarantor4=parseDouble(amountFieldDebit9.getValue().toString());
    Double difference4=(parseDouble(amountFieldDebit14.getValue().toString())-amountBorrowerHas4);
    if(amountAllowed4>difference4){
        
  if(difference4>amountGuarantor4){
      
  amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor4);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor4);
amountFieldDebit24.setValue(amountGuarantor4);
double now=amountBorrowerHas4+amountGuarantor4;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-difference4);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-difference4);
amountFieldDebit24.setValue(difference4);
double now=amountBorrowerHas4+difference4;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor4<amountAllowed4){
    
    amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor4);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor4);
amountFieldDebit24.setValue(amountGuarantor4);
double now=amountBorrowerHas4+amountGuarantor4;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountAllowed4);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountAllowed4);
amountFieldDebit24.setValue(amountAllowed4);
double now=amountBorrowerHas4+amountAllowed4;
amountFieldDebit19.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton8.setEnabled(false);

if(parseDouble(amountFieldDebit14.getValue().toString())==parseDouble(amountFieldDebit19.getValue().toString())){
jComboBox1.setVisible(false);
guarantoursAccount11.setVisible(false);
guarantoursAccount6.setVisible(false);
amountFieldDebit3.setVisible(false);
amountFieldDebit10.setVisible(false);
amountFieldDebit25.setVisible(false);
jButton9.setVisible(false);
        amountFieldDebit15.setVisible(false);
        amountFieldDebit20.setVisible(false);
                secondCheckBox2.setVisible(false);
             jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
                
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB1"+borrowersAccount+".txt"),cv.toString());                                        

}else{
 jComboBox1.setVisible(true);
guarantoursAccount11.setVisible(true);
guarantoursAccount6.setVisible(true);
amountFieldDebit3.setVisible(true);
amountFieldDebit10.setVisible(true);
amountFieldDebit25.setVisible(true);
jButton9.setVisible(true);
        amountFieldDebit15.setVisible(true);
        amountFieldDebit20.setVisible(true);
                secondCheckBox2.setVisible(true);
              jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
                
                jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB1"+borrowersAccount+".txt"),cv.toString());             

          }
break;
             
             
             case 5:
     
    
  
 if(amountFieldDebit24.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
     Double amountBorrowerHas5= parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed5=parseDouble(amountFieldDebit24.getValue().toString());
    Double amountGuarantor5=parseDouble(amountFieldDebit9.getValue().toString());
    Double difference5=(parseDouble(amountFieldDebit14.getValue().toString())-amountBorrowerHas5);
    if(amountAllowed5>difference5){
        
  if(difference5>amountGuarantor5){
      
  amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor5);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor5);
amountFieldDebit24.setValue(amountGuarantor5);
double now=amountBorrowerHas5+amountGuarantor5;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-difference5);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-difference5);
amountFieldDebit24.setValue(difference5);
double now=amountBorrowerHas5+difference5;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor5<amountAllowed5){
    
    amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountGuarantor5);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountGuarantor5);
amountFieldDebit24.setValue(amountGuarantor5);
double now=amountBorrowerHas5+amountGuarantor5;
amountFieldDebit19.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit2.setValue(parseDouble(amountFieldDebit2.getValue().toString())-amountAllowed5);
amountFieldDebit9.setValue(parseDouble(amountFieldDebit9.getValue().toString())-amountAllowed5);
amountFieldDebit24.setValue(amountAllowed5);
double now=amountBorrowerHas5+amountAllowed5;
amountFieldDebit19.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton8.setEnabled(false);

 if(parseDouble(amountFieldDebit14.getValue().toString())==parseDouble(amountFieldDebit19.getValue().toString())){
jComboBox1.setVisible(false);
guarantoursAccount11.setVisible(false);
guarantoursAccount6.setVisible(false);
amountFieldDebit3.setVisible(false);
amountFieldDebit10.setVisible(false);
amountFieldDebit25.setVisible(false);
jButton9.setVisible(false);
        amountFieldDebit15.setVisible(false);
        amountFieldDebit20.setVisible(false);
                secondCheckBox2.setVisible(false);
        jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
                
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
jComboBox9.setVisible(false);
guarantoursAccount14.setVisible(false);
guarantoursAccount9.setVisible(false);
amountFieldDebit6.setVisible(false);
amountFieldDebit13.setVisible(false);
amountFieldDebit28.setVisible(false);
jButton7.setVisible(false);
amountFieldDebit18.setVisible(false);
amountFieldDebit23.setVisible(false);
fifthCheckBox2.setVisible(false);

                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB1"+borrowersAccount+".txt"),cv.toString());                                        

}else{
 jComboBox1.setVisible(true);
guarantoursAccount11.setVisible(true);
guarantoursAccount6.setVisible(true);
amountFieldDebit3.setVisible(true);
amountFieldDebit10.setVisible(true);
amountFieldDebit25.setVisible(true);
jButton9.setVisible(true);
        amountFieldDebit15.setVisible(true);
        amountFieldDebit20.setVisible(true);
                secondCheckBox2.setVisible(true);
             jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
                
                jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);

jComboBox9.setVisible(true);
guarantoursAccount14.setVisible(true);
guarantoursAccount9.setVisible(true);
amountFieldDebit6.setVisible(true);
amountFieldDebit13.setVisible(true);
amountFieldDebit28.setVisible(true);
jButton7.setVisible(true);
amountFieldDebit18.setVisible(true);
amountFieldDebit23.setVisible(true);
fifthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB1"+borrowersAccount+".txt"),cv.toString());             

          }

break;                   
 }  // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        
          String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
          
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
 
 case 2:
   
     
   
 if(amountFieldDebit25.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 
 
 if(jComboBox10.isVisible()&&jComboBox1.isVisible()){
 if(jComboBox10.getSelectedItem().toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHas=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed=parseDouble(amountFieldDebit25.getValue().toString());
    Double amountGuarantor=parseDouble(amountFieldDebit10.getValue().toString());
    Double difference=(parseDouble(amountFieldDebit15.getValue().toString())-amountBorrowerHas);
    if(amountAllowed>difference){
        
  if(difference>amountGuarantor){
      
  amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantor);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantor);
amountFieldDebit25.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-difference);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-difference);
amountFieldDebit25.setValue(difference);
double now=amountBorrowerHas+difference;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor<amountAllowed){
    
    amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantor);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantor);
amountFieldDebit25.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountAllowed);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountAllowed);
amountFieldDebit25.setValue(amountAllowed);
double now=amountBorrowerHas+amountAllowed;
amountFieldDebit20.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton9.setEnabled(false);



 

break; 
    
 case 3:
     
   
     
   
 if(amountFieldDebit25.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 

 
 if(jComboBox10.isVisible()&&jComboBox1.isVisible()){
 if(jComboBox10.getSelectedItem().toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

  fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt")); 
    Double amountBorrowerHasa=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAlloweda=parseDouble(amountFieldDebit25.getValue().toString());
    Double amountGuarantora=parseDouble(amountFieldDebit10.getValue().toString());
    Double differencea=(parseDouble(amountFieldDebit15.getValue().toString())-amountBorrowerHasa);
    if(amountAlloweda>differencea){
        
  if(differencea>amountGuarantora){
      
  amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora);
amountFieldDebit25.setValue(amountGuarantora);
double now=amountBorrowerHasa+amountGuarantora;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-differencea);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-differencea);
amountFieldDebit25.setValue(differencea);
double now=amountBorrowerHasa+differencea;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantora<amountAlloweda){
    
    amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora);
amountFieldDebit25.setValue(amountGuarantora);
double now=amountBorrowerHasa+amountGuarantora;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountAlloweda);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountAlloweda);
amountFieldDebit25.setValue(amountAlloweda);
double now=amountBorrowerHasa+amountAlloweda;
amountFieldDebit20.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton9.setEnabled(false);



if(parseDouble(amountFieldDebit15.getValue().toString())==parseDouble(amountFieldDebit20.getValue().toString())){
jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
                
                
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB2"+borrowersAccount+".txt"),cv.toString());                                        

}else{
jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedThirdGuarantorB2"+borrowersAccount+".txt"),cv.toString());             

          }
break; 
         
         case 4:
     
  
   
 if(amountFieldDebit25.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 

 
 if(jComboBox10.isVisible()&&jComboBox1.isVisible()){
 if(jComboBox10.getSelectedItem().toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHasa1=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAlloweda1=parseDouble(amountFieldDebit25.getValue().toString());
    Double amountGuarantora1=parseDouble(amountFieldDebit10.getValue().toString());
    Double differencea1=(parseDouble(amountFieldDebit15.getValue().toString())-amountBorrowerHasa1);
    if(amountAlloweda1>differencea1){
        
  if(differencea1>amountGuarantora1){
      
  amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora1);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora1);
amountFieldDebit25.setValue(amountGuarantora1);
double now=amountBorrowerHasa1+amountGuarantora1;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-differencea1);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-differencea1);
amountFieldDebit25.setValue(differencea1);
double now=amountBorrowerHasa1+differencea1;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantora1<amountAlloweda1){
    
    amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora1);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora1);
amountFieldDebit25.setValue(amountGuarantora1);
double now=amountBorrowerHasa1+amountGuarantora1;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountAlloweda1);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountAlloweda1);
amountFieldDebit25.setValue(amountAlloweda1);
double now=amountBorrowerHasa1+amountAlloweda1;
amountFieldDebit20.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton9.setEnabled(false);


if(parseDouble(amountFieldDebit15.getValue().toString())==parseDouble(amountFieldDebit20.getValue().toString())){

             jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
                
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
                
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB2"+borrowersAccount+".txt"),cv.toString());                                        

}else{
 jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
                
                jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB2"+borrowersAccount+".txt"),cv.toString());             

          }

break;
             case 5:
     
 
   
 if(amountFieldDebit25.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 

 
 if(jComboBox10.isVisible()&&jComboBox1.isVisible()){
 if(jComboBox10.getSelectedItem().toString().equalsIgnoreCase(jComboBox1.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHasa1G=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAlloweda1G=parseDouble(amountFieldDebit25.getValue().toString());
    Double amountGuarantora1G=parseDouble(amountFieldDebit10.getValue().toString());
    Double differencea1G=(parseDouble(amountFieldDebit15.getValue().toString())-amountBorrowerHasa1G);
    if(amountAlloweda1G>differencea1G){
        
  if(differencea1G>amountGuarantora1G){
      
  amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora1G);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora1G);
amountFieldDebit25.setValue(amountGuarantora1G);
double now=amountBorrowerHasa1G+amountGuarantora1G;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-differencea1G);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-differencea1G);
amountFieldDebit25.setValue(differencea1G);
double now=amountBorrowerHasa1G+differencea1G;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantora1G<amountAlloweda1G){
    
    amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountGuarantora1G);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountGuarantora1G);
amountFieldDebit25.setValue(amountGuarantora1G);
double now=amountBorrowerHasa1G+amountGuarantora1G;
amountFieldDebit20.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit3.setValue(parseDouble(amountFieldDebit3.getValue().toString())-amountAlloweda1G);
amountFieldDebit10.setValue(parseDouble(amountFieldDebit10.getValue().toString())-amountAlloweda1G);
amountFieldDebit25.setValue(amountAlloweda1G);
double now=amountBorrowerHasa1G+amountAlloweda1G;
amountFieldDebit20.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton9.setEnabled(false);


if(parseDouble(amountFieldDebit15.getValue().toString())==parseDouble(amountFieldDebit20.getValue().toString())){

                jComboBox6.setVisible(false);
guarantoursAccount12.setVisible(false);
guarantoursAccount7.setVisible(false);
amountFieldDebit4.setVisible(false);
amountFieldDebit11.setVisible(false);
amountFieldDebit26.setVisible(false);
jButton10.setVisible(false);
        amountFieldDebit16.setVisible(false);
        amountFieldDebit21.setVisible(false);
                thirdCheckBox2.setVisible(false);
                
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
      
jComboBox9.setVisible(false);
guarantoursAccount14.setVisible(false);
guarantoursAccount9.setVisible(false);
amountFieldDebit6.setVisible(false);
amountFieldDebit13.setVisible(false);
amountFieldDebit28.setVisible(false);
jButton7.setVisible(false);
amountFieldDebit18.setVisible(false);
amountFieldDebit23.setVisible(false);
fifthCheckBox2.setVisible(false);
                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB2"+borrowersAccount+".txt"),cv.toString());                                        

}else{
jComboBox6.setVisible(true);
guarantoursAccount12.setVisible(true);
guarantoursAccount7.setVisible(true);
amountFieldDebit4.setVisible(true);
amountFieldDebit11.setVisible(true);
amountFieldDebit26.setVisible(true);
jButton10.setVisible(true);
        amountFieldDebit16.setVisible(true);
        amountFieldDebit21.setVisible(true);
                thirdCheckBox2.setVisible(true);
                
                jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);

jComboBox9.setVisible(true);
guarantoursAccount14.setVisible(true);
guarantoursAccount9.setVisible(true);
amountFieldDebit6.setVisible(true);
amountFieldDebit13.setVisible(true);
amountFieldDebit28.setVisible(true);
jButton7.setVisible(true);
amountFieldDebit18.setVisible(true);
amountFieldDebit23.setVisible(true);
fifthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB2"+borrowersAccount+".txt"),cv.toString());             

          }
break;                   
 } 
        
        
        jButton9.setEnabled(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
          String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
          
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
        
        case 3:
     
      
     
   
 if(amountFieldDebit26.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 
 if(jComboBox1.isVisible()&&jComboBox6.isVisible()){
 if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase(jComboBox6.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHas=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed=parseDouble(amountFieldDebit26.getValue().toString());
    Double amountGuarantor=parseDouble(amountFieldDebit11.getValue().toString());
    Double difference=(parseDouble(amountFieldDebit16.getValue().toString())-amountBorrowerHas);
    if(amountAllowed>difference){
        
  if(difference>amountGuarantor){
      
  amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantor);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantor);
amountFieldDebit26.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-difference);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-difference);
amountFieldDebit26.setValue(difference);
double now=amountBorrowerHas+difference;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor<amountAllowed){
    
    amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantor);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantor);
amountFieldDebit26.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountAllowed);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountAllowed);
amountFieldDebit26.setValue(amountAllowed);
double now=amountBorrowerHas+amountAllowed;
amountFieldDebit21.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton10.setEnabled(false);



 

 
break; 
         
         case 4:
     

   
 if(amountFieldDebit26.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 
 if(jComboBox1.isVisible()&&jComboBox6.isVisible()){
 if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase(jComboBox6.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHasW=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowedW=parseDouble(amountFieldDebit26.getValue().toString());
    Double amountGuarantorW=parseDouble(amountFieldDebit11.getValue().toString());
    Double differenceW=(parseDouble(amountFieldDebit16.getValue().toString())-amountBorrowerHasW);
    if(amountAllowedW>differenceW){
        
  if(differenceW>amountGuarantorW){
      
  amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantorW);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantorW);
amountFieldDebit26.setValue(amountGuarantorW);
double now=amountBorrowerHasW+amountGuarantorW;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-differenceW);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-differenceW);
amountFieldDebit26.setValue(differenceW);
double now=amountBorrowerHasW+differenceW;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantorW<amountAllowedW){
    
    amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantorW);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantorW);
amountFieldDebit26.setValue(amountGuarantorW);
double now=amountBorrowerHasW+amountGuarantorW;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountAllowedW);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountAllowedW);
amountFieldDebit26.setValue(amountAllowedW);
double now=amountBorrowerHasW+amountAllowedW;
amountFieldDebit21.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton10.setEnabled(false);



if(parseDouble(amountFieldDebit16.getValue().toString())==parseDouble(amountFieldDebit21.getValue().toString())){
       
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
      

                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB3"+borrowersAccount+".txt"),cv.toString());                                        

}else{
      
jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFouthGuarantorB3"+borrowersAccount+".txt"),cv.toString());             

          }

 

break;
             case 5:
     
 
   
 if(amountFieldDebit26.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 
 if(jComboBox1.isVisible()&&jComboBox6.isVisible()){
 if(jComboBox1.getSelectedItem().toString().equalsIgnoreCase(jComboBox6.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHasWr=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowedWr=parseDouble(amountFieldDebit26.getValue().toString());
    Double amountGuarantorWr=parseDouble(amountFieldDebit11.getValue().toString());
    Double differenceWr=(parseDouble(amountFieldDebit16.getValue().toString())-amountBorrowerHasWr);
    if(amountAllowedWr>differenceWr){
        
  if(differenceWr>amountGuarantorWr){
      
  amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantorWr);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantorWr);
amountFieldDebit26.setValue(amountGuarantorWr);
double now=amountBorrowerHasWr+amountGuarantorWr;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-differenceWr);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-differenceWr);
amountFieldDebit26.setValue(differenceWr);
double now=amountBorrowerHasWr+differenceWr;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantorWr<amountAllowedWr){
    
    amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountGuarantorWr);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountGuarantorWr);
amountFieldDebit26.setValue(amountGuarantorWr);
double now=amountBorrowerHasWr+amountGuarantorWr;
amountFieldDebit21.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit4.setValue(parseDouble(amountFieldDebit4.getValue().toString())-amountAllowedWr);
amountFieldDebit11.setValue(parseDouble(amountFieldDebit11.getValue().toString())-amountAllowedWr);
double now=amountBorrowerHasWr+amountAllowedWr;
amountFieldDebit26.setValue(amountAllowedWr);
amountFieldDebit21.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton10.setEnabled(false);
                 
     

if(parseDouble(amountFieldDebit16.getValue().toString())==parseDouble(amountFieldDebit21.getValue().toString())){
      
                jComboBox7.setVisible(false);
guarantoursAccount13.setVisible(false);
guarantoursAccount8.setVisible(false);
amountFieldDebit5.setVisible(false);
amountFieldDebit12.setVisible(false);
amountFieldDebit27.setVisible(false);
jButton11.setVisible(false);
amountFieldDebit17.setVisible(false);
amountFieldDebit22.setVisible(false);
fourthCheckBox2.setVisible(false);
      
                jComboBox9.setVisible(false);
guarantoursAccount14.setVisible(false);
guarantoursAccount9.setVisible(false);
amountFieldDebit6.setVisible(false);
amountFieldDebit13.setVisible(false);
amountFieldDebit28.setVisible(false);
jButton7.setVisible(false);
amountFieldDebit18.setVisible(false);
amountFieldDebit23.setVisible(false);
fifthCheckBox2.setVisible(false);
      

                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB3"+borrowersAccount+".txt"),cv.toString());                                        

}else{
    
                jComboBox7.setVisible(true);
guarantoursAccount13.setVisible(true);
guarantoursAccount8.setVisible(true);
amountFieldDebit5.setVisible(true);
amountFieldDebit12.setVisible(true);
amountFieldDebit27.setVisible(true);
jButton11.setVisible(true);
amountFieldDebit17.setVisible(true);
amountFieldDebit22.setVisible(true);
fourthCheckBox2.setVisible(true);
       
                jComboBox9.setVisible(true);
guarantoursAccount14.setVisible(true);
guarantoursAccount9.setVisible(true);
amountFieldDebit6.setVisible(true);
amountFieldDebit13.setVisible(true);
amountFieldDebit28.setVisible(true);
jButton7.setVisible(true);
amountFieldDebit18.setVisible(true);
amountFieldDebit23.setVisible(true);
fifthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB3"+borrowersAccount+".txt"),cv.toString());             

          }            
        
                    }     
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
    String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
          
    int numbGua= fios.intFileReader(fios.createFileName("persistence", "gaurantors", "useXGuarantors.txt"));   
                    switch(numbGua){
        
        case 4:
      
     
   
 if(amountFieldDebit27.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton10.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 if(jComboBox6.isVisible()&&jComboBox7.isVisible()){
 if(jComboBox6.getSelectedItem().toString().equalsIgnoreCase(jComboBox7.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}

   fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
    Double amountBorrowerHas=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowed=parseDouble(amountFieldDebit27.getValue().toString());
    Double amountGuarantor=parseDouble(amountFieldDebit12.getValue().toString());
    Double difference=(parseDouble(amountFieldDebit17.getValue().toString())-amountBorrowerHas);
    if(amountAllowed>difference){
        
  if(difference>amountGuarantor){
      
  amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountGuarantor);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountGuarantor);
amountFieldDebit27.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-difference);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-difference);
amountFieldDebit27.setValue(difference);
double now=amountBorrowerHas+difference;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantor<amountAllowed){
    
    amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountGuarantor);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountGuarantor);
amountFieldDebit27.setValue(amountGuarantor);
double now=amountBorrowerHas+amountGuarantor;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountAllowed);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountAllowed);
amountFieldDebit27.setValue(amountAllowed);
double now=amountBorrowerHas+amountAllowed;
amountFieldDebit22.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton11.setEnabled(false);
 
break;
   case 5:
      
     
   
 if(amountFieldDebit27.getValue()==null){JOptionPane.showMessageDialog(this, "Please input  Valid Amount the guarantor is willing to offer");return;}   
 
 
 
 if(jButton8.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton9.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
  if(jButton10.isEnabled()){JOptionPane.showMessageDialog(this, "Please Set Gaurantor one first");return;} 
 if(jComboBox6.isVisible()&&jComboBox7.isVisible()){
 if(jComboBox6.getSelectedItem().toString().equalsIgnoreCase(jComboBox7.getSelectedItem().toString())) {
 JOptionPane.showMessageDialog(this, "You cannot use the same guarantor on one borrower twice");return;
 }}
fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"));
   
    Double amountBorrowerHasL=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"))); 
    Double amountAllowedL=parseDouble(amountFieldDebit27.getValue().toString());
    Double amountGuarantorL=parseDouble(amountFieldDebit12.getValue().toString());
    Double differenceL=(parseDouble(amountFieldDebit17.getValue().toString())-amountBorrowerHasL);
    if(amountAllowedL>differenceL){
        
  if(differenceL>amountGuarantorL){
      
  amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountGuarantorL);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountGuarantorL);
amountFieldDebit27.setValue(amountGuarantorL);
double now=amountBorrowerHasL+amountGuarantorL;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  
  
  }else{
        
amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-differenceL);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-differenceL);
amountFieldDebit27.setValue(differenceL);
double now=amountBorrowerHasL+differenceL;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 

  } 
    
    } else if(amountGuarantorL<amountAllowedL){
    
    amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountGuarantorL);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountGuarantorL);
amountFieldDebit27.setValue(amountGuarantorL);
double now=amountBorrowerHasL+amountGuarantorL;
amountFieldDebit22.setValue(now);
     
   fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"), now+""); 
    
    }else{
   amountFieldDebit5.setValue(parseDouble(amountFieldDebit5.getValue().toString())-amountAllowedL);
amountFieldDebit12.setValue(parseDouble(amountFieldDebit12.getValue().toString())-amountAllowedL);
amountFieldDebit27.setValue(amountAllowedL);
double now=amountBorrowerHasL+amountAllowedL;
amountFieldDebit22.setValue(now);       
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowersAccount+".txt"),now+"");     
   
    }

    
jButton11.setEnabled(false);

if(parseDouble(amountFieldDebit17.getValue().toString())==parseDouble(amountFieldDebit22.getValue().toString())){

        
                jComboBox9.setVisible(false);
guarantoursAccount14.setVisible(false);
guarantoursAccount9.setVisible(false);
amountFieldDebit6.setVisible(false);
amountFieldDebit13.setVisible(false);
amountFieldDebit28.setVisible(false);
jButton7.setVisible(false);
amountFieldDebit18.setVisible(false);
amountFieldDebit23.setVisible(false);
fifthCheckBox2.setVisible(false);
      

                Integer cv=900;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB4"+borrowersAccount+".txt"),cv.toString());                                        

}else{
     
                jComboBox9.setVisible(true);
guarantoursAccount14.setVisible(true);
guarantoursAccount9.setVisible(true);
amountFieldDebit6.setVisible(true);
amountFieldDebit13.setVisible(true);
amountFieldDebit28.setVisible(true);
jButton7.setVisible(true);
amountFieldDebit18.setVisible(true);
amountFieldDebit23.setVisible(true);
fifthCheckBox2.setVisible(true);
                Integer cv=910;
fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "lockedFifthGuarantorB4"+borrowersAccount+".txt"),cv.toString());             

          } 
 
break;                 
                    }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void firstCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstCheckBox1ActionPerformed
      
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox10.getSelectedItem()==null&&jComboBox10.isVisible()==true||guarantoursAccount5.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         firstCheckBox1.setSelected(false);
         return;
        } 
        
        if(firstCheckBox1.isSelected()){
         
           guarantoursAccount10.setText(jComboBox10.getSelectedItem().toString());
           guarantoursAccount10.setVisible(true);
           guarantoursAccount10.setEditable(false);
            guarantoursAccount5.setText(guarantoursAccount5.getText());
            guarantoursAccount5.setEditable(false);
            jComboBox10.setVisible(false);
           
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=2){
               if(z==1){
              var1=guarantoursAccount10.getText();
               }
               if(z==2){
              var1=guarantoursAccount5.getText();
               }
         
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore1S"+borrowerAccount +".txt"), ";");
              z++;
               }
       
                  Integer tx=400;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+borrowerAccount+".txt"),tx.toString());  
         
        }  else if(!firstCheckBox1.isSelected()){
               Integer tx=420;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox1SelectedS"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount10.setVisible(false);
           guarantoursAccount10.setText("");
            guarantoursAccount10.setEditable(true);
           jComboBox10.setVisible(true);
            jComboBox10.setSelectedItem("");
            guarantoursAccount5.setEditable(true);
          }
    }//GEN-LAST:event_firstCheckBox1ActionPerformed

    private void submitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButton1ActionPerformed
     String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        jTabbedPane1.setVisible(true);
     guarantorSetupPanel.setVisible(false);
     loanClosedPanel.setVisible(false);
     jCheckBox52.setSelected(false);
//      Integer xG = 10;
//      fios.intFileWriterReplace( fios.createFileName("persistence", "gaurantors", "guarantorLoanWindows"+borrowerAccount+".txt") , xG.toString());
      
    }//GEN-LAST:event_submitButton1ActionPerformed

    private void refreshButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButton1ActionPerformed

//        NewLoanApplication f = new NewLoanApplication(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
//        this.dispose();
        
    }//GEN-LAST:event_refreshButton1ActionPerformed

    private void secondCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondCheckBox2ActionPerformed
    
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox1.getSelectedItem()==null&&jComboBox1.isVisible()==true||guarantoursAccount6.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         secondCheckBox2.setSelected(false);
         return;
        } else if(amountFieldDebit25.getValue()==null)  {JOptionPane.showMessageDialog(this, "Please Input the Amount the Gaurantor is willing to guarantee");secondCheckBox2.setSelected(false);
         return;}else if(jButton9.isEnabled()){
         
          JOptionPane.showMessageDialog(this, "Please synchronise the amount allowed to be guaranteed first");
        secondCheckBox2.setSelected(false);
         return;
         
         
         }
        
        if(secondCheckBox2.isSelected()){
            thirdCheckBox2.setEnabled(true);
           jButton9.setEnabled(false);
           guarantoursAccount11.setText(jComboBox1.getSelectedItem().toString());
           guarantoursAccount11.setVisible(true);
           guarantoursAccount11.setEditable(false);
            guarantoursAccount6.setText(guarantoursAccount6.getText());
            guarantoursAccount6.setEditable(false);
            jComboBox1.setVisible(false);
            amountFieldDebit3.setEditable(false);
            amountFieldDebit10.setEditable(false);
                amountFieldDebit25.setEditable(false);
                 amountFieldDebit15.setEditable(false);
                  amountFieldDebit20.setEditable(false);
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=7){
               if(z==1){
              var1=guarantoursAccount11.getText();
               }
               if(z==2){
              var1=guarantoursAccount6.getText();
               }
                if(z==3){
              var1=amountFieldDebit3.getValue().toString();
               }
                 if(z==4){
              var1=amountFieldDebit10.getValue().toString();
               }
                 if(z==5){
              var1=amountFieldDebit25.getValue().toString();
               }
                  if(z==6){
              var1=amountFieldDebit15.getValue().toString();
               }
                  if(z==7){
              var1=amountFieldDebit20.getValue().toString();
               } 
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt"), ";");
              z++;
               }
               
             fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
            fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
                Double totalGuarantor=  parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt")));
                Double totalGuarantors=totalGuarantor+parseDouble(amountFieldDebit25.getValue().toString());
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"),totalGuarantors.toString());
       fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),totalGuarantors.toString()); 
                  Integer tx=300;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"),tx.toString());  
  
             fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt")).split("[;]", 7)[4]); //Set the amount allowed to gurantee after the accept chebox is selected and the amount has been set in the file 
              Integer xc=2;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"),xc.toString()); 
               debitHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt"))));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
              debitTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt"))));
              
        }  else if(!secondCheckBox2.isSelected()){
             thirdCheckBox2.setEnabled(true);
               Integer tx=320;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox2Selected"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount11.setVisible(false);
           guarantoursAccount11.setText("");
            guarantoursAccount11.setEditable(true);
           jComboBox1.setVisible(true);
            jComboBox1.setSelectedItem("");
            guarantoursAccount6.setEditable(true);
            amountFieldDebit3.setEditable(true);
            amountFieldDebit10.setEditable(true);
                amountFieldDebit25.setEditable(true);
                 amountFieldDebit15.setEditable(true);
                  amountFieldDebit20.setEditable(true);
                  
            amountFieldDebit3.setValue(null);
            amountFieldDebit10.setValue(null);
                amountFieldDebit25.setValue(null);
                 amountFieldDebit15.setValue(null);
                  amountFieldDebit20.setValue(null);
     
          jButton9.setEnabled(true); 
         if( creditTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt"))))){
              creditHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore2"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee2"+borrowerAccount+".txt"))));
         }
        
        }else{return;}   
          
                  
          
    }//GEN-LAST:event_secondCheckBox2ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
     String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
    
    if(!borrowerAccount.isEmpty()){
        
    dbq.feelWithCustomersAccounts(jComboBox7.getSelectedItem().toString(), guarantoursAccount8);

    if(guarantoursAccount8.getText().equalsIgnoreCase(borrowerAccount)){

    JOptionPane.showMessageDialog(this, jComboBox7.getSelectedItem().toString()+" "+"CANNOT GUARANTEE HIMSELF");
    guarantoursAccount8.setText("");
    jComboBox7.setSelectedItem("");
    return;
    }


    if(!guarantoursAccount8.getText().equalsIgnoreCase("")){

    amountFieldDebit5.setValue((0.5*mxb.maxAmountBorrowedF(guarantoursAccount8.getText())+0.5*mxb.maxAmountBorrowed(guarantoursAccount8.getText())));
    amountFieldDebit12.setValue((0.5*mxb.maxAmountBorrowed(guarantoursAccount8.getText())));
    amountFieldDebit17.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"))));    
    amountFieldDebit22.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt")))); 

    }

    }
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
      String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
    
    if(!borrowerAccount.isEmpty()){
        
    dbq.feelWithCustomersAccounts(jComboBox9.getSelectedItem().toString(), guarantoursAccount9);

    if(guarantoursAccount9.getText().equalsIgnoreCase(borrowerAccount)){

    JOptionPane.showMessageDialog(this, jComboBox9.getSelectedItem().toString()+" "+"CANNOT GUARANTEE HIMSELF");
    guarantoursAccount9.setText("");
    jComboBox9.setSelectedItem("");
    return;
    }


    if(!guarantoursAccount9.getText().equalsIgnoreCase("")){

    amountFieldDebit6.setValue(0.5*mxb.maxAmountBorrowedF(guarantoursAccount9.getText())+0.5*mxb.maxAmountBorrowed(guarantoursAccount9.getText()));
    amountFieldDebit13.setValue((0.5*mxb.maxAmountBorrowed(guarantoursAccount9.getText())));
    amountFieldDebit18.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"))));    
    amountFieldDebit23.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt")))); 

    }

    }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void thirdCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirdCheckBox2ActionPerformed
     
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox6.getSelectedItem()==null&&jComboBox6.isVisible()==true||guarantoursAccount7.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         thirdCheckBox2.setSelected(false);
         return;
        } else if(amountFieldDebit26.getValue()==null)  {JOptionPane.showMessageDialog(this, "Please Input the Amount the Gaurantor is willing to guarantee");thirdCheckBox2.setSelected(false);
         return;}else if(jButton10.isEnabled()){
         
          JOptionPane.showMessageDialog(this, "Please synchronise the amount allowed to be guaranteed first");
        thirdCheckBox2.setSelected(false);
         return;
         
         
         }
        
        if(thirdCheckBox2.isSelected()){
            fourthCheckBox2.setEnabled(true);
           jButton10.setEnabled(false);
           guarantoursAccount12.setText(jComboBox6.getSelectedItem().toString());
           guarantoursAccount12.setVisible(true);
           guarantoursAccount12.setEditable(false);
            guarantoursAccount7.setText(guarantoursAccount7.getText());
            guarantoursAccount7.setEditable(false);
            jComboBox6.setVisible(false);
            amountFieldDebit4.setEditable(false);
            amountFieldDebit11.setEditable(false);
                amountFieldDebit26.setEditable(false);
                 amountFieldDebit16.setEditable(false);
                  amountFieldDebit21.setEditable(false);
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=7){
               if(z==1){
              var1=guarantoursAccount12.getText();
               }
               if(z==2){
              var1=guarantoursAccount7.getText();
               }
                if(z==3){
              var1=amountFieldDebit4.getValue().toString();
               }
                 if(z==4){
              var1=amountFieldDebit11.getValue().toString();
               }
                 if(z==5){
              var1=amountFieldDebit26.getValue().toString();
               }
                  if(z==6){
              var1=amountFieldDebit16.getValue().toString();
               }
                  if(z==7){
              var1=amountFieldDebit21.getValue().toString();
               } 
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt"), ";");
              z++;
               }
               fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
               fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
               Double totalGuarantor=  parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt")));
                Double totalGuarantors=totalGuarantor+parseDouble(amountFieldDebit26.getValue().toString());
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"),totalGuarantors.toString());
               fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),totalGuarantors.toString()); 
              Integer tx=300;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"),tx.toString());  
                
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt")).split("[;]", 7)[4]); //Set the amount allowed to gurantee after the accept chebox is selected and the amount has been set in the file 
              Integer xc=3;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"),xc.toString()); 
             
              debitHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"))));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
              debitTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"))));
              
        }  else if(!thirdCheckBox2.isSelected()){
            fourthCheckBox2.setEnabled(true);
               Integer tx=320;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox3Selected"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount12.setVisible(false);
           guarantoursAccount12.setText("");
            guarantoursAccount12.setEditable(true);
           jComboBox6.setVisible(true);
            jComboBox6.setSelectedItem("");
            guarantoursAccount7.setEditable(true);
            amountFieldDebit4.setEditable(true);
            amountFieldDebit11.setEditable(true);
                amountFieldDebit26.setEditable(true);
                 amountFieldDebit16.setEditable(true);
                  amountFieldDebit21.setEditable(true);
                  
            amountFieldDebit4.setValue(null);
            amountFieldDebit11.setValue(null);
                amountFieldDebit26.setValue(null);
                 amountFieldDebit16.setValue(null);
                  amountFieldDebit21.setValue(null);
     
          jButton10.setEnabled(true); 
          
         if( creditTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"))))){
              creditHundredPercent(  fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore3"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee3"+borrowerAccount+".txt"))));
         }
        
        }else{return;}   
         
          
    }//GEN-LAST:event_thirdCheckBox2ActionPerformed

    private void amountFieldDebit24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit24ActionPerformed

    private void fourthCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourthCheckBox2ActionPerformed
      String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox7.getSelectedItem()==null&&jComboBox7.isVisible()==true||guarantoursAccount8.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         fourthCheckBox2.setSelected(false);
         return;
        } else if(amountFieldDebit27.getValue()==null)  {JOptionPane.showMessageDialog(this, "Please Input the Amount the Gaurantor is willing to guarantee");fourthCheckBox2.setSelected(false);
         return;}else if(jButton11.isEnabled()){
         
          JOptionPane.showMessageDialog(this, "Please synchronise the amount allowed to be guaranteed first");
        fourthCheckBox2.setSelected(false);
         return;
         
         
         }
        
        if(fourthCheckBox2.isSelected()){
            fifthCheckBox2.setEnabled(true);
           jButton11.setEnabled(false);
           guarantoursAccount13.setText(jComboBox7.getSelectedItem().toString());
           guarantoursAccount13.setVisible(true);
           guarantoursAccount13.setEditable(false);
            guarantoursAccount8.setText(guarantoursAccount8.getText());
            guarantoursAccount8.setEditable(false);
            jComboBox7.setVisible(false);
            amountFieldDebit5.setEditable(false);
            amountFieldDebit12.setEditable(false);
                amountFieldDebit27.setEditable(false);
                 amountFieldDebit17.setEditable(false);
                  amountFieldDebit22.setEditable(false);
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=7){
               if(z==1){
              var1=guarantoursAccount13.getText();
               }
               if(z==2){
              var1=guarantoursAccount8.getText();
               }
                if(z==3){
              var1=amountFieldDebit5.getValue().toString();
               }
                 if(z==4){
              var1=amountFieldDebit12.getValue().toString();
               }
                 if(z==5){
              var1=amountFieldDebit27.getValue().toString();
               }
                  if(z==6){
              var1=amountFieldDebit17.getValue().toString();
               }
                  if(z==7){
              var1=amountFieldDebit22.getValue().toString();
               } 
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt"), ";");
              z++;
               }
              fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
            fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
                 Double totalGuarantor=  parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt")));
                Double totalGuarantors=totalGuarantor+parseDouble(amountFieldDebit27.getValue().toString());
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"),totalGuarantors.toString());
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),totalGuarantors.toString()); 
              Integer tx=300;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"),tx.toString());  
  fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt")).split("[;]", 7)[4]); //Set the amount allowed to gurantee after the accept chebox is selected and the amount has been set in the file 
              Integer xc=4;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"),xc.toString()); 
            debitHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"))));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
              debitTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"))));  
              
        }  else if(!fourthCheckBox2.isSelected()){
             fifthCheckBox2.setEnabled(true);
               Integer tx=320;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox4Selected"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount13.setVisible(false);
           guarantoursAccount13.setText("");
            guarantoursAccount13.setEditable(true);
           jComboBox7.setVisible(true);
            jComboBox7.setSelectedItem("");
            guarantoursAccount8.setEditable(true);
            amountFieldDebit5.setEditable(true);
            amountFieldDebit12.setEditable(true);
                amountFieldDebit27.setEditable(true);
                 amountFieldDebit17.setEditable(true);
                  amountFieldDebit22.setEditable(true);
                  
            amountFieldDebit5.setValue(null);
            amountFieldDebit12.setValue(null);
                amountFieldDebit27.setValue(null);
                 amountFieldDebit17.setValue(null);
                  amountFieldDebit22.setValue(null);
     
          jButton11.setEnabled(true); 
         
        if( creditTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"))))){
              creditHundredPercent(  fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore4"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee4"+borrowerAccount+".txt"))));
         }
        
        }else{return;}   
          
          
    }//GEN-LAST:event_fourthCheckBox2ActionPerformed

    private void fifthCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fifthCheckBox2ActionPerformed
             String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox9.getSelectedItem()==null&&jComboBox9.isVisible()==true||guarantoursAccount9.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         fifthCheckBox2.setSelected(false);
         return;
        } else if(amountFieldDebit28.getValue()==null)  {JOptionPane.showMessageDialog(this, "Please Input the Amount the Gaurantor is willing to guarantee");fourthCheckBox2.setSelected(false);
         return;}else if(jButton7.isEnabled()){
         
          JOptionPane.showMessageDialog(this, "Please synchronise the amount allowed to be guaranteed first");
        fifthCheckBox2.setSelected(false);
         return;
         
         
         }
        
        if(fifthCheckBox2.isSelected()){
           jButton7.setEnabled(false);
           guarantoursAccount14.setText(jComboBox9.getSelectedItem().toString());
           guarantoursAccount14.setVisible(true);
           guarantoursAccount14.setEditable(false);
            guarantoursAccount9.setText(guarantoursAccount9.getText());
            guarantoursAccount9.setEditable(false);
            jComboBox9.setVisible(false);
            amountFieldDebit6.setEditable(false);
            amountFieldDebit13.setEditable(false);
                amountFieldDebit28.setEditable(false);
                 amountFieldDebit18.setEditable(false);
                  amountFieldDebit23.setEditable(false);
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=7){
               if(z==1){
              var1=guarantoursAccount14.getText();
               }
               if(z==2){
              var1=guarantoursAccount9.getText();
               }
                if(z==3){
              var1=amountFieldDebit6.getValue().toString();
               }
                 if(z==4){
              var1=amountFieldDebit13.getValue().toString();
               }
                 if(z==5){
              var1=amountFieldDebit28.getValue().toString();
               }
                  if(z==6){
              var1=amountFieldDebit18.getValue().toString();
               }
                  if(z==7){
              var1=amountFieldDebit23.getValue().toString();
               } 
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt"), ";");
              z++;
               }
       
               fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"));
            fios.forceFileExistanceZero(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"));
                 Double totalGuarantor=  parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt")));
                Double totalGuarantors=totalGuarantor+parseDouble(amountFieldDebit28.getValue().toString());
              fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+borrowerAccount+".txt"),totalGuarantors.toString());
                fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "totalGuarantorR"+borrowerAccount+".txt"),totalGuarantors.toString()); 
              
              Integer tx=300;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowerAccount+".txt"),tx.toString());  
     fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"),fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt")).split("[;]", 7)[4]); //Set the amount allowed to gurantee after the accept chebox is selected and the amount has been set in the file 
              Integer xc=5;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "numberOfGuarantors"+borrowerAccount+".txt"),xc.toString()); //Number of guarantors actually used
               debitHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"))));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
              debitTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"))));
        }  else if(!fifthCheckBox2.isSelected()){
               Integer tx=320;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox5Selected"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount14.setVisible(false);
           guarantoursAccount14.setText("");
            guarantoursAccount14.setEditable(true);
           jComboBox9.setVisible(true);
            jComboBox9.setSelectedItem("");
            guarantoursAccount9.setEditable(true);
            amountFieldDebit6.setEditable(true);
            amountFieldDebit13.setEditable(true);
                amountFieldDebit28.setEditable(true);
                 amountFieldDebit18.setEditable(true);
                  amountFieldDebit23.setEditable(true);
                  
            amountFieldDebit6.setValue(null);
            amountFieldDebit13.setValue(null);
                amountFieldDebit28.setValue(null);
                 amountFieldDebit18.setValue(null);
                  amountFieldDebit23.setValue(null);
     
          jButton7.setEnabled(true); 
          
         if( creditTwentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt")).split("[;]", 7)[1],twentyFivePercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"))))){
              creditHundredPercent( fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "guarantorStore5"+borrowerAccount +".txt")).split("[;]", 7)[1],hundredPercent(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountAllowedToGuarantee5"+borrowerAccount+".txt"))));
         }
        
        }else{return;}
    }//GEN-LAST:event_fifthCheckBox2ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));

        if(!borrowerAccount.isEmpty()){

            dbq.feelWithCustomersAccounts(jComboBox6.getSelectedItem().toString(), guarantoursAccount7);

            if(guarantoursAccount6.getText().equalsIgnoreCase(borrowerAccount)){

                JOptionPane.showMessageDialog(this, jComboBox6.getSelectedItem().toString()+" "+"CANNOT GUARANTEE HIMSELF");
                guarantoursAccount6.setText("");
                jComboBox6.setSelectedItem("");
                return;
            }

            if(!guarantoursAccount7.getText().equalsIgnoreCase("")){

                amountFieldDebit4.setValue((0.5*mxb.maxAmountBorrowedF(guarantoursAccount7.getText())+0.5*mxb.maxAmountBorrowed(guarantoursAccount7.getText())));
                amountFieldDebit11.setValue((0.5*mxb.maxAmountBorrowed(guarantoursAccount7.getText())));
                amountFieldDebit16.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"))));
                amountFieldDebit21.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt"))));

            }

        }
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));

        if(!borrowerAccount.isEmpty()){

            dbq.feelWithCustomersAccounts(jComboBox1.getSelectedItem().toString(), guarantoursAccount6);

            if(guarantoursAccount5.getText().equalsIgnoreCase(borrowerAccount)){

                JOptionPane.showMessageDialog(this, jComboBox1.getSelectedItem().toString()+" "+"CANNOT GUARANTEE HIMSELF");
                guarantoursAccount5.setText("");
                jComboBox10.setSelectedItem("");
                return;
            }

            String guarantorsAccount=fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "GuarantorsAccount"+borrowerAccount+".txt"));

            if(!guarantoursAccount6.getText().equalsIgnoreCase("")){

                amountFieldDebit3.setValue((0.5*mxb.maxAmountBorrowedF(guarantoursAccount6.getText())+0.5*mxb.maxAmountBorrowed(guarantoursAccount6.getText())));
                amountFieldDebit10.setValue((0.5*mxb.maxAmountBorrowed(guarantoursAccount6.getText())));
                amountFieldDebit15.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+borrowerAccount+".txt"))));
                amountFieldDebit20.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+borrowerAccount+".txt"))));

            }

        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void secondCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondCheckBox1ActionPerformed
        
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox1.getSelectedItem()==null&&jComboBox1.isVisible()==true||guarantoursAccount6.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         secondCheckBox1.setSelected(false);
         return;
        } 
        
        if(secondCheckBox1.isSelected()){
           jButton9.setEnabled(false);
           guarantoursAccount11.setText(jComboBox1.getSelectedItem().toString());
           guarantoursAccount11.setVisible(true);
           guarantoursAccount11.setEditable(false);
            guarantoursAccount6.setText(guarantoursAccount6.getText());
            guarantoursAccount6.setEditable(false);
            jComboBox1.setVisible(false);
            
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=2){
               if(z==1){
              var1=guarantoursAccount11.getText();
               }
               if(z==2){
              var1=guarantoursAccount6.getText();
               }
                
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore2S"+borrowerAccount +".txt"), ";");
              z++;
               }
       
                  Integer tx=400;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+borrowerAccount+".txt"),tx.toString());  
  
        }  else if(!secondCheckBox1.isSelected()){
               Integer tx=420;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox2SelectedS"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount11.setVisible(false);
           guarantoursAccount11.setText("");
            guarantoursAccount11.setEditable(true);
           jComboBox1.setVisible(true);
            jComboBox1.setSelectedItem("");
            guarantoursAccount6.setEditable(true);
          
          }
    }//GEN-LAST:event_secondCheckBox1ActionPerformed

    private void thirdCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirdCheckBox1ActionPerformed
         
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
        
        
        if(jComboBox6.getSelectedItem()==null&&jComboBox6.isVisible()==true||guarantoursAccount7.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         thirdCheckBox1.setSelected(false);
         return;
        } 
        
        if(thirdCheckBox1.isSelected()){
       
           guarantoursAccount12.setText(jComboBox6.getSelectedItem().toString());
           guarantoursAccount12.setVisible(true);
           guarantoursAccount12.setEditable(false);
            guarantoursAccount7.setText(guarantoursAccount7.getText());
            guarantoursAccount7.setEditable(false);
            jComboBox6.setVisible(false);
            
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=2){
               if(z==1){
              var1=guarantoursAccount12.getText();
               }
               if(z==2){
              var1=guarantoursAccount7.getText();
               }
                
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore3S"+borrowerAccount +".txt"), ";");
              z++;
               }
       
                  Integer tx=400;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+borrowerAccount+".txt"),tx.toString());  
  
        }  else if(!thirdCheckBox2.isSelected()){
               Integer tx=420;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox3SelectedS"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount12.setVisible(false);
           guarantoursAccount12.setText("");
            guarantoursAccount12.setEditable(true);
           jComboBox6.setVisible(true);
            jComboBox6.setSelectedItem("");
            guarantoursAccount7.setEditable(true);
           
          }
    }//GEN-LAST:event_thirdCheckBox1ActionPerformed

    private void fourthCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourthCheckBox1ActionPerformed
      String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        
        if(jComboBox7.getSelectedItem()==null&&jComboBox7.isVisible()==true||guarantoursAccount8.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         fourthCheckBox1.setSelected(false);
         return;
        } 
        
        if(fourthCheckBox1.isSelected()){
         
           guarantoursAccount13.setText(jComboBox7.getSelectedItem().toString());
           guarantoursAccount13.setVisible(true);
           guarantoursAccount13.setEditable(false);
            guarantoursAccount8.setText(guarantoursAccount8.getText());
            guarantoursAccount8.setEditable(false);
            jComboBox7.setVisible(false);
           
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=2){
               if(z==1){
              var1=guarantoursAccount13.getText();
               }
               if(z==2){
              var1=guarantoursAccount8.getText();
               }
               
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore4S"+borrowerAccount +".txt"), ";");
              z++;
               }
       
                  Integer tx=400;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+borrowerAccount+".txt"),tx.toString());  
  
        }  else if(!fourthCheckBox1.isSelected()){
               Integer tx=420;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox4SelectedS"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount13.setVisible(false);
           guarantoursAccount13.setText("");
            guarantoursAccount13.setEditable(true);
           jComboBox7.setVisible(true);
            jComboBox7.setSelectedItem("");
        }
    }//GEN-LAST:event_fourthCheckBox1ActionPerformed

    private void fifthCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fifthCheckBox1ActionPerformed
                 String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        
        if(jComboBox9.getSelectedItem()==null&&jComboBox9.isVisible()==true||guarantoursAccount9.getText().equalsIgnoreCase("")){
            
            JOptionPane.showMessageDialog(this, "Please Select the Account Name from a dropdown Menu");
         fifthCheckBox1.setSelected(false);
         return;
        } 
        
        if(fifthCheckBox1.isSelected()){
       
           guarantoursAccount14.setText(jComboBox9.getSelectedItem().toString());
           guarantoursAccount14.setVisible(true);
           guarantoursAccount14.setEditable(false);
            guarantoursAccount9.setText(guarantoursAccount9.getText());
            guarantoursAccount9.setEditable(false);
            jComboBox9.setVisible(false);
            
        fios.deleteFileExistance(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+borrowerAccount +".txt"));            
                  
               int z=1; String var1="";
               while(z<=2){
               if(z==1){
              var1=guarantoursAccount14.getText();
               }
               if(z==2){
              var1=guarantoursAccount9.getText();
               }
                
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+borrowerAccount +".txt"), var1);
             fios.stringFileWriterAppend(fios.createFileName("persistence", "gaurantors", "guarantorStore5S"+borrowerAccount +".txt"), ";");
              z++;
               }
       
                  Integer tx=400;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox5SelectedS"+borrowerAccount+".txt"),tx.toString());  
  
        }  else if(!fifthCheckBox2.isSelected()){
               Integer tx=420;
              fios.intFileWriterReplace(fios.createFileName("persistence", "gaurantors", "checkBox5SelectedS"+borrowerAccount+".txt"),tx.toString());  
           guarantoursAccount14.setVisible(false);
           guarantoursAccount14.setText("");
            guarantoursAccount14.setEditable(true);
           jComboBox9.setVisible(true);
            jComboBox9.setSelectedItem("");
            guarantoursAccount9.setEditable(true);
             
          }
    }//GEN-LAST:event_fifthCheckBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
if(dbq.newLedgerCreated()){
JOptionPane.showMessageDialog(this, "A NEW LEDGER HAS BEEN CREATED\n\nPLEASE FIRST ACTIVATE IT BEFORE YOU PROCEED");
return;
}else{              setBatchNumber();
        String accountNumber2=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));   
       if(!(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "rejectedDovCol"+accountNumber2+".txt"))==77)){ 
        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
    
if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){
    
   if(  fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowedC"+accountNumber2+".txt"))==35){
     
      Double borowersAmount=parseDouble(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "newPrincipalAmount"+ accountNumber2+".txt")))-parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "totalGuarantorC"+accountNumber2+".txt"))); 
      
       debitHundredPercent( accountNumber2, hundredPercent(borowersAmount.toString()));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
            debitTwentyFivePercent(accountNumber2,twentyFivePercent(borowersAmount.toString()));//Send the remaining 25% to database to be debited from customer to the one of the sacco,s current accounts.
       dbq.updateBorrowing(accountNumber2, "B");
  
   
   
   }else{
     Double borowersAmount=parseDouble(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "newPrincipalAmount"+ accountNumber2+".txt")));   
     debitHundredPercent( accountNumber2, hundredPercent(borowersAmount.toString()));//Send 100% of the amount allowed to gurantee together with the guarantors account number to database for debiting it off the equity and credit gurantors savings account. This will be only amount allowed to guarantee which represents only 100%
          
     debitTwentyFivePercent(accountNumber2,twentyFivePercent(borowersAmount.toString()));//Send the remaining 25% to database to be debited from customer to the one of the sacco,s current accounts.
      
     dbq.updateBorrowing(accountNumber2, "B");
          
         
   }
  
  }
 }
       }
       CollateralandDocuments f = new  CollateralandDocuments(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed

    }//GEN-LAST:event_jComboBox3KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      jCheckBox1.setSelected(false);
        dbq.feelWithAccountNamesCurrentLiabilitesCustomerssu(jComboBox3,jComboBox3.getSelectedItem().toString());
      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
if(jCheckBox1.isSelected()){
 jComboBox3.setEditable(true);

}else if(!jCheckBox1.isSelected()){
jComboBox3.setEditable(false);
}
        
       
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

 Integer xl=5;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());   
          
        NewLoanApplication frm = new NewLoanApplication(userId);
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        Object[] optionsSGS = {"Continue",  "Cancel"};
        
        int nSGS = JOptionPane.showOptionDialog(this,  "Are you sure you want to Decline the loan TopUp request?",  "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
        
        if(nSGS==JOptionPane.YES_OPTION){
       
            fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWin"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))+".txt"),"5");
        
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),"5");
            NewLoanApplication f = new NewLoanApplication(userId);
            
            f.setVisible(true);
            
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            
            f.setSize(screen.getSize());
            
            f.pack();
            
            this.dispose();
        }
        else if (nSGS==JOptionPane.NO_OPTION){ 
            
            this.setVisible(true);
        
        }

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        
        
        String accountNumber2b=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
       
        topUpDatabaseImp topupData=new topUpDatabaseImp(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));  
        
 optionTwo=   fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWinAfter"+accountNumber2b+".txt"));
   
switch(optionTwo){        
    case "reshedule":
        
        
        
        break;
    case "topUp":
            setBatchNumber();

                String accountNumbers=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
           
                DecisionTopUpImp decideTopUp=new DecisionTopUpImp(accountNumbers); 

            if(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "allowTopUps.txt"))==0){

            JOptionPane.showMessageDialog(this, "The Institution Policy Does Not Allow TopUps On Loans");

            }else if(decideTopUp.isQualifyingForTopUp(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
            if(jCheckBox2.isSelected()){
            JOptionPane.showMessageDialog(this, "Please first adjust the amount to borrow");

            }else if(jCheckBox3.isSelected()){

            JOptionPane.showMessageDialog(this, "Please first adjust the tenure to borrow");
            }else{

            JOptionPane.showMessageDialog(this, "Are you sure to create TopUp Loan??");
            Object[] optionsSGS = {"Continue",  "Cancel"};
            int nSGS = JOptionPane.showOptionDialog(this,  "This process will close the running loan, pay outstanding loan and create a new loan\n"+"Do you want to  continue ?",
            "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
            if(nSGS==JOptionPane.YES_OPTION){

            topups=new ArrayList();   

            topups.add(jTextField3.getText().replace(",", ""));//For amortisation(0)

            topups.add(jTextField2.getText().replace(",", ""));//For disbursement(1)
            
            topups.add(jTextField1.getText());//Loan Tenure(2)
            
            topups.add(jTextField10.getText());//Interest Rate(3)
            
            topups.add(jFormattedTextField4.getValue().toString().replace(",", ""));//Amount In Arrears(4)
            
            topups.add(jLabel21.getText());//Number of installments In Arrears  (5)   

            fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "topUpOn"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))+".txt"),"77");

            fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "takeHomeTopUp"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))+".txt"),jTextField2.getText().replace(",", ""));
            
        int interestIn=  fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "interestIn"+accountNumber2b+".txt"));
        
        int penaltyIn=     fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "loanPenaltyIn"+accountNumber2b+".txt"));
        
        int accumulatedInterestIn=   fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "accumulatedInterestIn"+accountNumber2b+".txt"));  
        
        int outstandingInterestIn=  fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "outStandingInterestIn"+accountNumber2b+".txt"));    



            if(interestIn==77&&penaltyIn==77&&accumulatedInterestIn==77){
            
                if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
                
            
            
            }else if(interestIn==77&&penaltyIn==77){
            if(waiveAccumulatedInt(accountNumbers)){
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Accumulated Interest Failed!!!");
            
            }
            
            
            }else if(interestIn==77&&accumulatedInterestIn==77){
            
            if(waiveLoanPenalty(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Penalty Failed!!!");
            
            }
            
            
            }else if(penaltyIn==77&&accumulatedInterestIn==77){
            if(waiveMainInterest(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Interest Failed!!!");
            
            }
            
            
            
           }else if(accumulatedInterestIn==77){
            if(waiveBothMainInterestPenalty(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Interest Failed!!!");
            
            }
            
            
            }else if(interestIn==77){
            if(waiveBothAccumulatedPenalty(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Interest Failed!!!");
            
            }
            
            
            
           }else if(penaltyIn==77){
            
             if(waiveBothAccumulatedInterest(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Interest Failed!!!");
            
            }
            
            
            
           }else if(outstandingInterestIn==77&&penaltyIn==77&&accumulatedInterestIn==77){
            
             if(waiveBothAccumulatedInterest(accountNumbers)){
                
             if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
            
            }else{
               JOptionPane.showMessageDialog(this, "Waive of Loan Interest Failed!!!");
            
            }
            
            
              }else if(outstandingInterestIn==77&&accumulatedInterestIn==77){
            
            
            
             }else if(outstandingInterestIn==77&&penaltyIn==77){
            
            
            
             }else if(outstandingInterestIn==77){
            
            
            
            }else{
              if(completeLoanClosing(accountNumbers)){
                    
                    if(createTopUp(topups,accountNumbers,this)){
                    JOptionPane.showMessageDialog(this, "Top Up Loan was successfully created");
                    clearME();
                    
                    }else{
                    
                     JOptionPane.showMessageDialog(this, "Creation of Loan Top Up Faild!!");
                    }
               
                
                }else{
                JOptionPane.showMessageDialog(this, "Loan closing failed!!!");
                
                }
             
             }
            
            
            
            
            
            
  
            

//            if(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "chargeInterestOnRemainingLoanAmount.txt"))==1){
//
//            if(closeLoanFirstNormal(accountNumber2b)){
//
//            amortizeTopUpLoan(topups,accountNumber2b);
//
//            }else{
//
//            amortizeTopUpLoan(topups,accountNumber2b);
//            }
//
//
//            }else if(closeLoanFirstTopUp(accountNumber2b)){
//
//
//            amortizeTopUpLoan(topups,accountNumber2b);
//
//
//            }else{
//            amortizeTopUpLoan(topups,accountNumber2b);
//            }



            }
            else if (nSGS==JOptionPane.NO_OPTION){
            this.setVisible(true);
            }
            }



            }else{

            JOptionPane.showMessageDialog(this, "According to the agreed procedure for TopUp,"+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))+" "+"does not qualify for a topup loan at this stage");

            }

            break;
            }
    }//GEN-LAST:event_jButton13ActionPerformed

    public void clearME(){
     NewLoanApplication f = new   NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();  
    }
    
    public boolean waiveAccumulatedInt(String accountNumber){
    
    loan.waiveAccumulatedInterest(accountNumber,this);
    
    return true;
    }
     public boolean waiveLoanPenalty(String accountNumber){
    
    loan.waiveLoanPenalty(accountNumber,this);
    
    return true;
    }
     
     public boolean waiveMainInterest(String accountNumber){
    
    loan.waiveInterest(accountNumber,this);
    
    return true;
    }
     
     
      public boolean waiveBothMainInterestPenalty(String accountNumber){
    
    loan.waiveAccumulatedInterest(accountNumber,this);
     loan.waiveLoanPenalty(accountNumber,this);
    return true;
    }
      
        public boolean waiveBothAccumulatedInterest(String accountNumber){
    
    loan.waiveAccumulatedInterest(accountNumber,this);
     loan.waiveInterest(accountNumber,this);
    return true;
    }  
     
             
                public boolean waiveBothAccumulatedPenalty(String accountNumber){
    
    loan.waiveInterest(accountNumber,this);
     loan.waiveLoanPenalty(accountNumber,this);
    return true;
    }
    private boolean completeLoanClosing(String accountNumbers){
    
        String batch=null;
        loanPaymentOrder=new ArrayList(); 

        double remainingInstalments=parseDouble(loan.remainingTotalLoanBalance(accountNumbers));

        loanPaymentOrder.add(0,"Accumulated Interest");          
        loanPaymentOrder.add(1,"Interest");
        loanPaymentOrder.add(2,"Loan Penalty");
        loanPaymentOrder.add(3,"Principal");           

 completeStatus="Complete";
    batch=this.batchCode();
List debitLP=new ArrayList();   

        
          debitLP .add(0, fmt.putSeparatorsOnNormaAccount(accountNumbers));//Debit Account Number:0
           debitLP .add(1, sdf.format(new Date(System.currentTimeMillis())));//Transaction Date:1
           debitLP .add(2, dbq.AccountName(accountNumbers)+"'s LOAN CLOSED FOR TOP UP"+"DATED"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
           debitLP .add(3, "LOAN CLOSED FOR TOP UP LOAN");//Narrative 2:3
           debitLP .add(4, sdf.format(new Date(System.currentTimeMillis())));//Value Date:4
          debitLP .add(5, remainingInstalments);//Debit Amount:5
         debitLP .add(6, fmt.putSeparatorsOnNormaAccount("01128000110"));//Credit Account Number:6  
           debitLP .add(7, dbq.AccountName("01128000110"));//Credit Account Name:7 
            debitLP .add(8, "0002");//Debit Reference Number:8
            debitLP .add(9, batch);//Batch Number:9
           debitLP .add(10, "LoanR");//Transaction Type:10
            debitLP .add(11,dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11
           
          List   creditLP=new ArrayList();    
            
            creditLP .add(0, fmt.putSeparatorsOnNormaAccount("01128000110"));//Debit Account Number:0
           creditLP .add(1, sdf.format(new Date(System.currentTimeMillis())));//Transaction Date:1
           creditLP .add(2, dbq.AccountName(accountNumbers)+"'s LOAN CLOSED FOR TOP UP"+"DATED"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
           creditLP .add(3, "LOAN CLOSED FOR TOP UP LOAN");//Narrative 2:3
           creditLP .add(4, sdf.format(new Date(System.currentTimeMillis())));//Value Date:4
          creditLP .add(5, remainingInstalments);//Debit Amount:5
         creditLP .add(6,  fmt.putSeparatorsOnNormaAccount(accountNumbers));//Credit Account Number:6  
           creditLP .add(7, dbq.AccountName(accountNumbers));//Credit Account Name:7 
            creditLP .add(8, "0002");//Debit Reference Number:8
            creditLP .add(9, batch);//Batch Number:9
           creditLP .add(10, "LoanR");//Transaction Type:10
            creditLP .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
            
// if(post.accountsAreFineLR(debit, credit)){

   
//         SwingWorker<Void,Void> postTheEntry;
//            postTheEntry = new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
                   YearMonth theStartDateObject=YearMonth.parse(sdf.format(new Date(System.currentTimeMillis())).substring(0, 7));     
      List theOtherDetails=new ArrayList();
        theOtherDetails.add(completeStatus);
        theOtherDetails.add(dbq.getTransactionSequenceNumber());
        theOtherDetails.add(theStartDateObject);
        theOtherDetails.add(closingNotes);
//                     post.loanRepaymentsNew(debitLP, creditLP,loanPaymentOrder,theOtherDetails);
                    
//                    return null;
//                }};
//
//        postTheEntry.execute();



return true;
}


private boolean createTopUp(List topups,String accountNumbers,Component c){
String  batch="";
List amortDetailsx = new ArrayList();
    
//   List amortDetailsx1 = new ArrayList(); 
  
  amortizeLoan amorty =new amortizeLoan(userId);  


  double amount=parseDouble(topups.get(0).toString());
  
  double amountDisburse=parseDouble(topups.get(1).toString());

  double tenure=parseDouble(topups.get(2).toString());
  
  double interestRate=parseDouble(topups.get(3).toString());
  
  String DateFetched=sdf.format(new Date(System.currentTimeMillis()));
  int  interestRegime=0;
switch (fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan.txt"))) {
        case 1:
            interestRegime=1;
            break;
        case 2:
            interestRegime=2;
            break;
        case 3:
            interestRegime=3;
            break;
        default:
            break;
    }
  
    if(amount>0.0){
        
         fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));   
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "creditLedger"+this.userId+".txt"), accountNumbers);
//          SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
//  
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                
//                loan.createNewLoanAmort(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),NewLoanApplication.this);
//                
//                return null;
//            }
//        };
//        createNewLoan.execute();
        
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"),accountNumbers);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        
        
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), dbq.AccountName(accountNumbers));
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");   
        
      
   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), tenure+"");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");

          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "MONTHS");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");  
        
         amortDetailsx.add(amount);//PRINCIPAL :0
   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), amount+"");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");

          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "Mothly Income");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        
        
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "BUSINESS PURPOSE");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");

        amortDetailsx.add(interestRate);//INTEREST RATE:1

        amortDetailsx.add(tenure);//LOAN TENURE

        amortDetailsx.add(sdf.format(new Date(System.currentTimeMillis())));//START DATE

        amortDetailsx.add("4");//TENURE TYPE

        String accountName=dbq.getUserName(this.userId);
        
        amortDetailsx.add(accountName+" "+userId);

        amortDetailsx.add(interestRegime);//INTEREST REGIME
   
        
//       JOptionPane.showMessageDialog(this, amortDetailsx.get(0).toString()+";"+amortDetailsx.get(1).toString()+";"+amortDetailsx.get(2).toString()+";"+amortDetailsx.get(3).toString()+";"+amortDetailsx.get(4).toString()+";"+amortDetailsx.get(5).toString());  
        
        
//JOptionPane.showConfirmDialog(this, loandetails.size()+"");
        
        List theDetailsPrevious=new ArrayList();
        
        
            amorty.createTheLoan(amorty.amortizeme(amortDetailsx,this), amortDetailsx,theDetailsPrevious,NewLoanApplication.this);
            
  loan.updateSubmited(dbq.AccountName(accountNumbers),"Approved"); 
       
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+dbq.AccountName(accountNumbers)+ ".txt"), "4");
        
   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"), loan.getActiveId(accountNumbers,this));      
        
               String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
              List loandetails = loan.getLoanDetails(trnId,this); 
  
    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
    
        
         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        sendsms.createLoanApprovedSMS(dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt"))), loandetails.get(4).toString(),c);
             return null; } };
     updatesendSms.execute();     
}

  
      Integer zxso=4;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+accountNumbers+".txt"),zxso.toString());   
 
  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+accountNumbers+".txt"))==4){
     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
          loan.updateLoanStoreAll(userId+"", fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),"Approved","newloan"+fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),trnId);   
             
             return null;  } };
     updateLoanStore.execute();
     }   
  
        List loanDetails= new ArrayList();
       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
         loanDetails.add(loandetails.get(3).toString());//interest amount
  loanDetails.add(loandetails.get(6).toString());//instalment start date
  loanDetails.add(loandetails.get(7).toString());//instalment end date
    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
   loanDetails.add("Approved");//loan cycle status
   
    loanDetails.add(dbq.AccountName(accountNumbers));//Account Name
    
    loanDetails.add(accountNumbers);//Account Number
    
    
      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
           loan.updateLoanReportATable(loanDetails); 
             
             return null;
         }
     
     };
     updateReportATable.execute();   
        
       batch=this.batchCode(); 
        
 List   debitn=new ArrayList();   

    debitn .add(0, fmt.putSeparatorsOnNormaAccount("01128000110"));//Debit Account Number:0
    debitn .add(1, DateFetched);//Transaction Date:1
    debitn .add(2, dbq.AccountName(accountNumbers)+"'s  loan Top Up"+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    debitn .add(3, "Top Up Loan");//Narrative 2:3
    debitn .add(4, DateFetched);//Value Date:4
    debitn .add(5, amountDisburse);//Debit Amount:5
    debitn .add(6, fmt.putSeparatorsOnNormaAccount(accountNumbers));//Credit Account Number:6  
    debitn .add(7, dbq.AccountName(accountNumbers));//Credit Account Name:7 
    debitn .add(8, "0002");//Debit Reference Number:8
    debitn .add(9, batch);//Batch Number:9
    debitn .add(10, "Gen");//Transaction Type:10
    debitn .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11

   List creditn=new ArrayList();    

    creditn .add(0,  fmt.putSeparatorsOnNormaAccount(accountNumbers));//Debit Account Number:0
    creditn .add(1, DateFetched);//Transaction Date:1
    creditn .add(2, dbq.AccountName(accountNumbers)+"'s  loan Top Up"+"Processed on"+" "+sdf.format(new Date(System.currentTimeMillis())));//Narrative 1:2
    creditn .add(3, "Top Up Loan");//Narrative 2:3
    creditn .add(4, DateFetched);//Value Date:4
    creditn .add(5, amountDisburse);//Debit Amount:5
    creditn .add(6, fmt.putSeparatorsOnNormaAccount("01128000110"));//Credit Account Number:6  
    creditn .add(7, dbq.AccountName("01128000110"));//Credit Account Name:7 
    creditn .add(8, "0002");//Debit Reference Number:8
    creditn .add(9, batch);//Batch Number:9
    creditn .add(10, "Gen");//Transaction Type:10
    creditn .add(11, dbq.getTransactionSequenceNumber());//Transaction Sequence Number:11 
//
//      SwingWorker<Void,Void>genLoan=new SwingWorker(){
//            @Override
//            protected Object doInBackground() throws Exception {
            post.loanDisbursement(debitn, creditn);
//                return null;
//            } };
//      genLoan.execute();  

 String loandetailsNew =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumbers+".txt"));       
     
 List loanDetailsNew= new ArrayList();
 
       loanDetailsNew.add(loandetailsNew.split("[,]", 16)[1]);//Princimpal amount
         loanDetailsNew.add(loandetailsNew.split("[,]", 16)[2]);//interest amount
  loanDetailsNew.add(loandetailsNew.split("[,]", 16)[4]);//instalment start date
  loanDetailsNew.add(loandetailsNew.split("[,]", 16)[5]);//instalment start date
    loanDetailsNew.add(loandetailsNew.split("[,]", 16)[0]);//instalment start date
   loanDetailsNew.add("Disbursed");//loan cycle status
    loanDetailsNew.add(dbq.AccountName(accountNumbers));//loan cycle status
    loanDetailsNew.add(accountNumbers);//loan cycle status
     SwingWorker<Void,Void>updateLoanReportATable=new SwingWorker(){
            @Override
            protected Object doInBackground() throws Exception {
             loan.updateLoanReportATable(loanDetailsNew); 
                return null;
            } };
      updateLoanReportATable.execute(); 
   
       

        

        Integer xxy=5;

        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+accountNumbers+ ".txt"), xxy.toString());
       
        fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"));
        Integer xl=0;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());

        loan.updateLoanStoreAll(userId, accountNumbers,"Disbursed","newloan"+accountNumbers,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumbers+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));
        
}
    
      SwingWorker<Void,Void>submittedSecurity=new SwingWorker(){
       @Override
       protected Object doInBackground() throws Exception {
//      loan. processSecurity(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "creditLedger"+userId+".txt")),"1;Land;Not Specified");
      return null;
       }
};
 submittedSecurity.execute();
   
//    }else{
// JOptionPane.showMessageDialog(this, "The Expense Txn Narration:"+Narration+" "+"Amount:"+amount+" "+"Dated:"+DateFetched+" "+"has no corresponding txn Code defined\n\nPlease click ok to skip this txn");
//    break;
//    
//    } 
    return true;

}



    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
  
        String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        DecisionTopUpImp decideTopUp=new DecisionTopUpImp(accountNumber);
      
        double princimpalRemaining1=0.0,amountQualified1=0.0, takeHomeAmount1=0.0,interestIncluded1=0.0,loanPenaltyIncluded1=0.0,accumulatedInterestIncluded1=0.0,compareAmount1=0.0,differ1=0.0,outStandingInterest1=0.0;
        
                if(jCheckBox2.isSelected()){
            
         jTextField2.setEditable(true);  
         

        }else if(!jCheckBox2.isSelected()){
            
          takeHomeAmount1=parseDouble(jTextField2.getText().replace(",", "")); 
          
           princimpalRemaining1=parseDouble(jFormattedTextField1.getValue().toString().replace(",", ""));
        
        amountQualified1=parseDouble(jTextField3.getText().replace(",", "")); 
        
      interestIncluded1=parseDouble(jFormattedTextField5.getValue().toString().replace(",", ""));

      loanPenaltyIncluded1=parseDouble(jFormattedTextField3.getValue().toString().replace(",", ""));

        accumulatedInterestIncluded1=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        accumulatedInterestIncluded1=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        outStandingInterest1=parseDouble(jFormattedTextField13.getValue().toString().replace(",", ""));
          
if(jCheckBox6.isSelected()&&jCheckBox11.isSelected()&&jCheckBox12.isSelected()&&!jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+interestIncluded1+loanPenaltyIncluded1+accumulatedInterestIncluded1;


}else if(jCheckBox6.isSelected()&&jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&!jCheckBox5.isSelected()){
compareAmount1=princimpalRemaining1+takeHomeAmount1+interestIncluded1+loanPenaltyIncluded1;

} else if(jCheckBox6.isSelected()&&!jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&!jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+interestIncluded1;

}else if(!jCheckBox6.isSelected()&&!jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&!jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1;

}else if(!jCheckBox6.isSelected()&&jCheckBox11.isSelected()&&jCheckBox12.isSelected()&&jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+outStandingInterest1+loanPenaltyIncluded1+accumulatedInterestIncluded1;


}else if(!jCheckBox6.isSelected()&&jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+outStandingInterest1+accumulatedInterestIncluded1;


}else if(!jCheckBox6.isSelected()&&jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+outStandingInterest1+loanPenaltyIncluded1;


}else if(!jCheckBox6.isSelected()&&!jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+outStandingInterest1;


}else if(!jCheckBox6.isSelected()&&!jCheckBox11.isSelected()&&jCheckBox12.isSelected()&&jCheckBox5.isSelected()){

compareAmount1=princimpalRemaining1+takeHomeAmount1+outStandingInterest1+accumulatedInterestIncluded1;


}else if(!jCheckBox6.isSelected()&&!jCheckBox11.isSelected()&&!jCheckBox12.isSelected()&&!jCheckBox5.isSelected()){
compareAmount1=princimpalRemaining1+takeHomeAmount1;

}
    if(compareAmount1>amountQualified1){
      JOptionPane.showMessageDialog(this, "You cannot borrow more than"+NumberFormat.format(amountQualified1)+"");    
        differ1=compareAmount1-amountQualified1;
        takeHomeAmount1=takeHomeAmount1-differ1;
        if(takeHomeAmount1>0){
        jTextField2.setText(NumberFormat.format(takeHomeAmount1));  
        }else{
           JOptionPane.showMessageDialog(this, "You have no amount qualified"+"");    
          jTextField2.setText("0");  
        
        }
        }   
     
     }else{
     
      jCheckBox2.setSelected(false);
      
     jTextField2.setEditable(false);
    
     
      jTextField3.setText(NumberFormat.format(newQaulified));
     }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        if(jCheckBox2.isSelected()){
            
         jTextField2.setEditable(true);  
         
oldAmount=parseDouble( jTextField2.getText().replace(",", ""));

finalQualified=parseDouble(jTextField3.getText().replace(",", ""));

        }else if(!jCheckBox2.isSelected()){
         
     double actualA=parseDouble(jTextField2.getText().replace(",", ""));
      
     double actualA1=(calculateAmountToBorrow(accountNumber)-decideTopUp.remainingTotalPrincimpal());
      
     newAmount=oldAmount-actualA;
     
     newQaulified=finalQualified-newAmount;
     
    jTextField2.setText(NumberFormat.format(actualA));
    
     jCheckBox2.setText(actualA+"");
     
     if(actualA>actualA1){
     
     JOptionPane.showMessageDialog(this, "You cannot borrow more than"+NumberFormat.format(actualA1)+"");
     
     jCheckBox2.setSelected(true);
     
     jTextField2.setText(NumberFormat.format(actualA1));
     
      jTextField3.setText(NumberFormat.format(calculateAmountToBorrow(accountNumber)));
      
     return;
     
     }else{
     
      jCheckBox2.setSelected(false);
      
     jTextField2.setEditable(false);
    
     }
      jTextField3.setText(NumberFormat.format(newQaulified));
     }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
      
        if(jCheckBox3.isSelected()){
     
     jTextField1.setEditable(true);
     
     
     }else if(!jCheckBox3.isSelected()){
         
     int actualA=parseInt(jTextField1.getText());

     int actualA1=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[0]);
     
     String timing=fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[1];
     if(actualA>actualA1){
     
     JOptionPane.showMessageDialog(this, "You cannot borrow for more than"+actualA1+" "+timing);
     jCheckBox3.setSelected(true);
     jTextField1.setText(actualA1+"");
     return;
     }else{
     
      jCheckBox3.setSelected(false);
     jTextField1.setEditable(false);
     }
     
     }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
      
String accountNumber=jComboBox13.getSelectedItem().toString();
         theCollection=new ArrayList();
        processLoanRequest(accountNumber);
   

    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13KeyPressed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed

       sortTable(jTable3,jTextField6);
    }//GEN-LAST:event_jTextField6KeyPressed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        String theCounter=getCounter1();
        ObjectTableModel original = (ObjectTableModel)jTable3.getModel();
        List<List>  d=new ArrayList();  List c=new ArrayList();

        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

        d.add(c);

        for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {

            if(!original.getRow(targetRow).isEmpty()){
                if(jTable3.isCellSelected(targetRow, 0)){
                    d.add(original.getRow(jTable3.convertRowIndexToModel(targetRow)));
                }
            }
        }

        writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

        generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        try {
            jTable3.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jTabbedPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1PropertyChange

      
      
   
   
    }//GEN-LAST:event_jTabbedPane1PropertyChange

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jTabbedPane2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane2KeyPressed


     
    }//GEN-LAST:event_jTabbedPane2KeyPressed

    private void jTabbedPane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MousePressed
           
        String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        if(jTabbedPane2.getSelectedIndex()==0){
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWinAfter"+accountNumber+".txt"), "topUp");
        
     

        topUpDatabaseImp topupData=new topUpDatabaseImp(accountNumber); 

        DecisionTopUpImp decideTopUp=new DecisionTopUpImp(accountNumber);

        double initialAmountQualified=calculateAmountToBorrow(accountNumber);

        double outstandingPrincimpal=decideTopUp.remainingTotalPrincimpal();
        
        double outstandingInterest=decideTopUp.remainingTotalInterest();
        
        double outStandingInterest=decideTopUp.outStandingInterest();
        
         double outStandingCharges=decideTopUp.outStandingCharges();
         
         
          double outStandingAccumulatedInterest=decideTopUp.outStandingAccumulatedInterest();

        newLoanPanel.setVisible(false);

        loanClosedPanel.setVisible(false);

        topUpPanel.setVisible(true);

        guarantorSetupPanel.setVisible(false);
        
         jCheckBox6.setSelected(false);
        jCheckBox11.setSelected(false);
        jCheckBox12.setSelected(false);
        jCheckBox5 .setSelected(false);

        jLabel2.setText("UGX");   

        jLabel8.setText("UGX");

        jLabel13.setText("UGX"); 

        jLabel9.setText("UGX");

        jLabel90.setText("UGX");

        jLabel24.setText("UGX");

        jLabel33.setText("UGX");

        jLabel34.setText("UGX");

        jLabel44.setText("UGX");

        jTextField3.setText(NumberFormat.format(initialAmountQualified));   

        jFormattedTextField1.setValue(outstandingPrincimpal); 

        jFormattedTextField5.setValue(outstandingInterest); 

        jFormattedTextField2.setValue(decideTopUp.princimpalAmountPaid());
        
        jFormattedTextField13.setValue(outStandingInterest);
        
        
        jFormattedTextField3.setValue(outStandingCharges);
                
                jFormattedTextField6.setValue(outStandingAccumulatedInterest);

        if(decideTopUp.princimpalAmountPaid()>=calculateAmountToBorrow(accountNumber))
        {

        jTextField2.setText(NumberFormat.format(calculateAmountToBorrow(accountNumber)));

        }else{
        jTextField2.setText(NumberFormat.format((calculateAmountToBorrow(accountNumber)-decideTopUp.remainingTotalPrincimpal())));
        }
        jLabel11.setText(NumberFormat.format(decideTopUp.remainingNumberOfInstalments()));

        jLabel19.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsPaid()));



        jLabel21.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsInArrears()));

        
        //     jFormattedTextField5.setValue(topupData.);
        jFormattedTextField4.setValue(decideTopUp.amountInArrears());

        String amorts=   fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

        String[] amortthem=amorts.split("[,]", 7);

        jTextField1.setText(amortthem[2]);

        jTextField10.setText(fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan5.txt"))+"");

        jLabel12.setText("PROCESSING LOAN TOP UP FOR:"+"  "+topupData.getBorrowerName());
        }else if(jTabbedPane2.getSelectedIndex()==1){
           fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWinAfter"+accountNumber+".txt"), "topUp");
        
     

        topUpDatabaseImp topupData=new topUpDatabaseImp(accountNumber); 

        DecisionTopUpImp decideTopUp=new DecisionTopUpImp(accountNumber);

        double initialAmountQualified=calculateAmountToBorrow(accountNumber);

        double outstandingPrincimpal=decideTopUp.remainingTotalPrincimpal();
        
        double outstandingInterest=decideTopUp.remainingTotalInterest();
        
        double outStandingInterest=decideTopUp.outStandingInterest();
        
         double outStandingCharges=decideTopUp.outStandingCharges();
         
         
          double outStandingAccumulatedInterest=decideTopUp.outStandingAccumulatedInterest();

        newLoanPanel.setVisible(false);

        loanClosedPanel.setVisible(false);

        topUpPanel.setVisible(true);

        guarantorSetupPanel.setVisible(false);

        jLabel2.setText("UGX");   

        jLabel8.setText("UGX");

        jLabel13.setText("UGX"); 

        jLabel9.setText("UGX");

        jLabel90.setText("UGX");

        jLabel24.setText("UGX");

        jLabel33.setText("UGX");

        jLabel34.setText("UGX");

        jLabel44.setText("UGX");

        jTextField3.setText(NumberFormat.format(initialAmountQualified));   

        jFormattedTextField1.setValue(outstandingPrincimpal); 

        jFormattedTextField5.setValue(outstandingInterest); 

        jFormattedTextField2.setValue(decideTopUp.princimpalAmountPaid());
        
        jFormattedTextField13.setValue(outStandingInterest);
        
        
        jFormattedTextField3.setValue(outStandingCharges);
                
                jFormattedTextField6.setValue(outStandingAccumulatedInterest);

        if(decideTopUp.princimpalAmountPaid()>=calculateAmountToBorrow(accountNumber))
        {

        jTextField2.setText(NumberFormat.format(calculateAmountToBorrow(accountNumber)));

        }else{
        jTextField2.setText(NumberFormat.format((calculateAmountToBorrow(accountNumber)-decideTopUp.remainingTotalPrincimpal())));
        }
        jLabel11.setText(NumberFormat.format(decideTopUp.remainingNumberOfInstalments()));

        jLabel19.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsPaid()));



        jLabel21.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsInArrears()));

        
        //     jFormattedTextField5.setValue(topupData.);
        jFormattedTextField4.setValue(decideTopUp.amountInArrears());

        String amorts=   fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

        String[] amortthem=amorts.split("[,]", 7);

        jTextField1.setText(amortthem[2]);

        jTextField10.setText(fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan5.txt"))+"");

        jLabel12.setText("PROCESSING LOAN RESCHEDULING FOR:"+"  "+topupData.getBorrowerName());
        
        
        }
    }//GEN-LAST:event_jTabbedPane2MousePressed

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
           
        String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
      
        
        
        if(jTabbedPane1.getSelectedIndex()==1){
            
  if(loan.loanExists(accountNumber)){
      
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWinAfter"+accountNumber+".txt"), "topUp");
        
     

        topUpDatabaseImp topupData=new topUpDatabaseImp(accountNumber); 

        DecisionTopUpImp decideTopUp=new DecisionTopUpImp(accountNumber);

        double initialAmountQualified=calculateAmountToBorrow(accountNumber);

        double outstandingPrincimpal=decideTopUp.remainingTotalPrincimpal();
        
        double outstandingInterest=decideTopUp.remainingTotalInterest();
        
        double outStandingInterest=decideTopUp.outStandingInterest();
        
         double outStandingCharges=decideTopUp.outStandingCharges();
         
         
          double outStandingAccumulatedInterest=decideTopUp.outStandingAccumulatedInterest();

        newLoanPanel.setVisible(false);

        loanClosedPanel.setVisible(false);

        topUpPanel.setVisible(true);

        guarantorSetupPanel.setVisible(false);
        
//        jCheckBox6.setSelected(false);
//        jCheckBox11.setSelected(false);
//        jCheckBox12.setSelected(false);
//        jCheckBox5 .setSelected(false);       
        jLabel2.setText("UGX");   

        jLabel8.setText("UGX");

        jLabel13.setText("UGX"); 

        jLabel9.setText("UGX");

        jLabel90.setText("UGX");

        jLabel24.setText("UGX");

        jLabel33.setText("UGX");

        jLabel34.setText("UGX");

        jLabel44.setText("UGX");

        jTextField3.setText(NumberFormat.format(initialAmountQualified));   

        jFormattedTextField1.setValue(outstandingPrincimpal); 

        jFormattedTextField5.setValue(outstandingInterest); 

        jFormattedTextField2.setValue(decideTopUp.princimpalAmountPaid());
        
        jFormattedTextField13.setValue(outStandingInterest);
        
        
        jFormattedTextField3.setValue(outStandingCharges);
                
                jFormattedTextField6.setValue(outStandingAccumulatedInterest);

        if(decideTopUp.princimpalAmountPaid()>=calculateAmountToBorrow(accountNumber))
        {

        jTextField2.setText(NumberFormat.format(calculateAmountToBorrow(accountNumber)));

        }else{
        jTextField2.setText(NumberFormat.format((calculateAmountToBorrow(accountNumber)-decideTopUp.remainingTotalPrincimpal())));
        }
        jLabel11.setText(NumberFormat.format(decideTopUp.remainingNumberOfInstalments()));

        jLabel19.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsPaid()));



        jLabel21.setText(NumberFormat.format(decideTopUp.numberOfInstalmentsInArrears()));

        
        //     jFormattedTextField5.setValue(topupData.);
        jFormattedTextField4.setValue(decideTopUp.amountInArrears());

        String amorts=   fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

        String[] amortthem=amorts.split("[,]", 7);

        jTextField1.setText(amortthem[2]);

        jTextField10.setText(fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan5.txt"))+"");

        jLabel12.setText("PROCESSING LOAN TOP UP FOR:"+"  "+topupData.getBorrowerName());
         }else{
        
        JOptionPane.showMessageDialog(this, dbq.AccountName(accountNumber)+" "+"Has no running loan");
        
        return;
        }
        }else if(jTabbedPane1.getSelectedIndex()==0){
        
         NewLoanApplication f = new   NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();   
        
        }
       
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
  String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        if(jCheckBox6.isSelected()){
         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "interestIn"+accountNumber+".txt"), "77");
        jCheckBox6.setSelected(true);

        jCheckBox5.setSelected(false);
          
        double princimpalRemaining=parseDouble(jFormattedTextField1.getValue().toString().replace(",", ""));
        
        double amountQualified=parseDouble(jTextField3.getText().replace(",", "")); 
        
        double takeHomeAmount=parseDouble(jTextField2.getText().replace(",", "")); 

        double interestIncluded=parseDouble(jFormattedTextField5.getValue().toString().replace(",", ""));

        double loanPenaltyIncluded=parseDouble(jFormattedTextField3.getValue().toString().replace(",", ""));

        double accumulatedInterestIncluded=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        double compareAmount=0.0,differ=0.0;
        
        if(jCheckBox11.isSelected()&&jCheckBox12.isSelected()){
        compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded+accumulatedInterestIncluded;
        }else if(jCheckBox11.isSelected()&&!jCheckBox12.isSelected()){
            
            compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded;  
        }else if(!(jCheckBox11.isSelected()&&jCheckBox12.isSelected())){
        
             compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded; 
        
        }
        
        if(compareAmount>amountQualified){
        
        differ=compareAmount-amountQualified;
        takeHomeAmount=takeHomeAmount-differ;
        if(takeHomeAmount>0){
        jTextField2.setText(NumberFormat.format(takeHomeAmount));  
        }else{
        
          jTextField2.setText("0");  
        
        }
        }
         
    
     }else if(!jCheckBox6.isSelected()){
      jCheckBox6.setSelected(false);
          fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "interestIn"+accountNumber+".txt"), "771");
     }
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
  String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        if(jCheckBox11.isSelected()){
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanPenaltyIn"+accountNumber+".txt"), "77");     
        jCheckBox11.setSelected(true);
          
        double princimpalRemaining=parseDouble(jFormattedTextField1.getValue().toString().replace(",", ""));
        
        double amountQualified=parseDouble(jTextField3.getText().replace(",", "")); 
        
        double takeHomeAmount=parseDouble(jTextField2.getText().replace(",", "")); 

        double interestIncluded=parseDouble(jFormattedTextField5.getValue().toString().replace(",", ""));

        double loanPenaltyIncluded=parseDouble(jFormattedTextField3.getValue().toString().replace(",", ""));

        double accumulatedInterestIncluded=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        double compareAmount=0.0,differ=0.0;
        
        if(jCheckBox6.isSelected()&&jCheckBox12.isSelected()){
        compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded+accumulatedInterestIncluded;
        }else if(jCheckBox6.isSelected()&&!jCheckBox12.isSelected()){
            
            compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded;  
        }else if(!(jCheckBox6.isSelected()&&jCheckBox12.isSelected())){
        
             compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded; 
        
        }
        
        if(compareAmount>amountQualified){
        
        differ=compareAmount-amountQualified;
        takeHomeAmount=takeHomeAmount-differ;
        
        
        
        if(takeHomeAmount>0){
         jTextField2.setText(NumberFormat.format(takeHomeAmount));   
        }else{
        
        jTextField2.setText("0");
        
        }
        }
         
    
     }else if(!jCheckBox11.isSelected()){
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanPenaltyIn"+accountNumber+".txt"), "771");        
      jCheckBox11.setSelected(false);
     }
    }//GEN-LAST:event_jCheckBox11ActionPerformed

    private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox12ActionPerformed

    String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));      
        if(jCheckBox12.isSelected()){
     fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "accumulatedInterestIn"+accountNumber+".txt"), "77");      
        jCheckBox12.setSelected(true);
          
        double princimpalRemaining=parseDouble(jFormattedTextField1.getValue().toString().replace(",", ""));
        
        double amountQualified=parseDouble(jTextField3.getText().replace(",", "")); 
        
        double takeHomeAmount=parseDouble(jTextField2.getText().replace(",", "")); 

        double interestIncluded=parseDouble(jFormattedTextField5.getValue().toString().replace(",", ""));

        double loanPenaltyIncluded=parseDouble(jFormattedTextField3.getValue().toString().replace(",", ""));

        double accumulatedInterestIncluded=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        double compareAmount=0.0,differ=0.0;
        
        if(jCheckBox6.isSelected()&&jCheckBox11.isSelected()){
        compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded+accumulatedInterestIncluded;
        }else if(jCheckBox6.isSelected()&&!jCheckBox11.isSelected()){
            
            compareAmount=princimpalRemaining+takeHomeAmount+accumulatedInterestIncluded+loanPenaltyIncluded;  
        }else if(!(jCheckBox6.isSelected()&&jCheckBox11.isSelected())){
        
             compareAmount=princimpalRemaining+takeHomeAmount+accumulatedInterestIncluded+loanPenaltyIncluded; 
        
        }
        
        if(compareAmount>amountQualified){
        
        differ=compareAmount-amountQualified;
        takeHomeAmount=takeHomeAmount-differ;
         if(takeHomeAmount>0){
         jTextField2.setText(NumberFormat.format(takeHomeAmount));   
        }else{
        
        jTextField2.setText("0");
        
        } 
        }
         
    
     }else if(!jCheckBox12.isSelected()){
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "accumulatedInterestIn"+accountNumber+".txt"), "771");         
      jCheckBox12.setSelected(false);
     }
    }//GEN-LAST:event_jCheckBox12ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed

        String accountNumber=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        if(jCheckBox5.isSelected()){
  fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "outStandingInterestIn"+accountNumber+".txt"), "77");        
        jCheckBox5.setSelected(true);

        jCheckBox6.setSelected(false);
          
        double princimpalRemaining=parseDouble(jFormattedTextField1.getValue().toString().replace(",", ""));
        
        double amountQualified=parseDouble(jTextField3.getText().replace(",", "")); 
        
        double takeHomeAmount=parseDouble(jTextField2.getText().replace(",", "")); 

        double interestIncluded=parseDouble(jFormattedTextField13.getValue().toString().replace(",", ""));

        double loanPenaltyIncluded=parseDouble(jFormattedTextField3.getValue().toString().replace(",", ""));

        double accumulatedInterestIncluded=parseDouble(jFormattedTextField6.getValue().toString().replace(",", ""));
        
        double compareAmount=0.0,differ=0.0;
        
        if(jCheckBox11.isSelected()&&jCheckBox12.isSelected()){
        compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded+accumulatedInterestIncluded;
        }else if(jCheckBox11.isSelected()&&!jCheckBox12.isSelected()){
            
            compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded;  
        }else if(!(jCheckBox11.isSelected()&&jCheckBox12.isSelected())){
        
             compareAmount=princimpalRemaining+takeHomeAmount+interestIncluded+loanPenaltyIncluded; 
        
        }
        
        if(compareAmount>amountQualified){
        
        differ=compareAmount-amountQualified;
        takeHomeAmount=takeHomeAmount-differ;
        if(takeHomeAmount>0){
        jTextField2.setText(NumberFormat.format(takeHomeAmount));  
        }else{
        
          jTextField2.setText("0");  
        
        }
        }
         
    
     }else if(!jCheckBox5.isSelected()){
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "outStandingInterestIn"+accountNumber+".txt"), "771");        
      jCheckBox5.setSelected(false);
     }
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
 
        if(updateExistingBorrowersDetails.isVisible()){
        
       if(updateExistingBorrowersDetails.isSelected()){
       addItemInList("Existing Borrower");
         if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Individual")){
      
              addItemInList("Individual");
         addItemInList("Group 1");
            addItemInList("Group 1");
        }else if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Group")){
        
        addItemInList("Group");
        
        if(jCheckBox4.isSelected()){
        
            if(jTextField5.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField5.getText().equalsIgnoreCase("Select Group Id")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("Select")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
            addItemInList(jTextField5.getText());
            addItemInList(jTextField4.getText());
            }
            
            }
            
            }
            
            }
            
        }else if(!jCheckBox4.isSelected()){
        
             addItemInList("Group 1");
            addItemInList("Group 1");
            
        
        }
        
        }
        
       
       
       }else if(!updateExistingBorrowersDetails.isSelected()){
      
          
       
       }
        
        }else if(!updateExistingBorrowersDetails.isVisible()){
            
             String  existance="First Time Borrower";
             
     if(loan.borrowerExists(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
         
     existance="Existing Borrower";
     
     }else if(!loan.borrowerExists(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
     
     
      existance="First Time Borrower";
     
     
     }
            
            addItemInList(existance);
            
          if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Individual")){
      
              addItemInList("Individual");
         addItemInList("Group 1");
        addItemInList("Group 1");
        }else if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Group")){
        
            
            addItemInList("Group");
//        addItemInList("Group 1");
//        addItemInList("Group 1");
        if(jCheckBox4.isSelected()){
        
            if(jTextField5.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField5.getText().equalsIgnoreCase("Select Group Id")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Id!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
             if(jTextField4.getText().equalsIgnoreCase("Select")){
            
            
            JOptionPane.showMessageDialog(this, "Please first set Group Name!!!!");
            return;
            }else{
            addItemInList(jTextField5.getText());
            addItemInList(jTextField4.getText());
            }
            
            }
            
            }
            
            }
            
        }else if(!jCheckBox4.isSelected()){
        
             addItemInList("Group 1");
            addItemInList("Group 1");
            
        
        }
        
        }
        
        }
        
      
        
        
        addItemInList("Cycle"+loan.cycleNumber(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))));

      
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "OtherLoanDetails"+this.userId+".txt"),"7");
       
    jButton20.setEnabled(false);
            jButton16.setEnabled(false);
//    jButton22.setEnabled(true);
       OtherLoanDetails frm = new OtherLoanDetails(userId,(NewLoanApplication)this);
       
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "OtherLoanDetails"+this.userId+".txt"),"77");
    jButton20.setEnabled(false);
            jButton16.setEnabled(false);
//    jButton22.setEnabled(true);
       OtherLoanDetails frm = new OtherLoanDetails(userId,(NewLoanApplication)this);
       
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed

        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "OtherLoanDetails"+this.userId+".txt"),"777");
     jButton20.setEnabled(false);
            jButton16.setEnabled(false);
    jButton22.setEnabled(false);
//        jButton17.setEnabled(true);
       OtherLoanDetails frm = new OtherLoanDetails(userId,(NewLoanApplication)this);
       
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jComboBox12GroupIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12GroupIdActionPerformed
        jTextField5.setText(jComboBox12GroupId.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox12GroupIdActionPerformed

    private void jComboBox11GroupNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11GroupNameActionPerformed
        jTextField4.setText(jComboBox11GroupName.getSelectedItem().toString().split("\\s+")[0].trim());
    }//GEN-LAST:event_jComboBox11GroupNameActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
 if(jCheckBox4.isSelected()){
  jLabel31.setVisible(true);
jTextField5.setVisible(true);
        jLabel30.setVisible(true);
                jTextField4.setVisible(true);
        jTextField4.setEditable(true);
        jComboBox12GroupId.setVisible(true);
        jComboBox11GroupName.setVisible(true);
        loan.fillWithGroupIds(jComboBox12GroupId,this);
        loan.fillWithGroupNames(jComboBox11GroupName,this);
 
 }else if(!jCheckBox4.isSelected()){
  jLabel31.setVisible(false);
jTextField5.setVisible(false);
        jLabel30.setVisible(false);
                jTextField4.setVisible(false);
        jTextField4.setEditable(false);
        jComboBox12GroupId.setVisible(false);
        jComboBox11GroupName.setVisible(false);
        loan.fillWithGroupIds(jComboBox12GroupId,this);
        loan.fillWithGroupNames(jComboBox11GroupName,this);
 }
       
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox52ActionPerformed

        String borrowersAccount=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        Integer xc=4;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xc.toString());

        Integer cv=3;
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "displayOptionLoanWin"+borrowersAccount+".txt"),cv.toString());

        NewLoanApplication f = new NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }//GEN-LAST:event_jCheckBox52ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
       
        if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Individual")){
         
            jCheckBox4.setVisible(false);
            jButton20.setVisible(true);
            jButton16.setVisible(true);
            jButton22.setVisible(true);
//            jButton17.setVisible(true);

            jCheckBox4.setVisible(false);
            jLabel31.setVisible(false);
            jTextField5.setVisible(false);
            jLabel30.setVisible(false);
            jTextField4.setVisible(false);
            jTextField4.setEditable(false);
            jComboBox12GroupId.setVisible(false);
            jComboBox11GroupName.setVisible(false);
            jCheckBox4.setSelected(false);
         }else if(jComboBox14.getSelectedItem().toString().equalsIgnoreCase("Group")){
         
             jCheckBox4.setVisible(true);
               jButton20.setVisible(true);
                 jButton16.setVisible(true);
                 jButton22.setVisible(true);
//                         jButton17.setVisible(true);
                           jCheckBox4.setSelected(false);
         
         }
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void updateExistingBorrowersDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateExistingBorrowersDetailsActionPerformed
       if(updateExistingBorrowersDetails.isSelected()){
       jLabel18.setVisible(true);
       jComboBox14.setVisible(true);
           
           
       }else if(!updateExistingBorrowersDetails.isSelected()){
        jLabel18.setVisible(false);
       jComboBox14.setVisible(false);
       
       
       }
    }//GEN-LAST:event_updateExistingBorrowersDetailsActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
       for(Object the:theCollection){
       
       JOptionPane.showMessageDialog(this, the);
       
       }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void useDefaultBorrowerDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useDefaultBorrowerDetailsActionPerformed
    
        if(useDefaultBorrowerDetails.isSelected()){
      jLabel18.setVisible(false);
      jComboBox14.setVisible(false);
      updateExistingBorrowersDetails  .setVisible(false);
      jButton20.setVisible(false);
      jButton16.setVisible(false);
      jButton22.setVisible(false);
      jCheckBox4.setVisible(false);
      jLabel31.setVisible(false);
      jTextField5.setVisible(false);
      jLabel30 .setVisible(false);       
       jTextField4  .setVisible(false);  
       jComboBox12GroupId.setVisible(false); 
       jComboBox11GroupName .setVisible(false);    
        this.updateDefaults(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));
      }else if(!useDefaultBorrowerDetails.isSelected()){
           theCollection=new ArrayList();
         
       
          if(loan.LoanCycleThere(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
                
                    updateExistingBorrowersDetails.setVisible(true);
                 jLabel18.setVisible(false);
                jComboBox14.setVisible(false);
                }else{
                updateExistingBorrowersDetails.setVisible(false);
                jLabel18.setVisible(true);
                jComboBox14.setVisible(true);
                }
      
      }
    }//GEN-LAST:event_useDefaultBorrowerDetailsActionPerformed

private void  populateComboBox(JComboBox combo) throws SQLException{
dbq.feelWithAccountNamesCurrentLiabilitesCustomers(combo);
}
public void updateFields(JTextField tf, String accounNumber) {

dbq.getAccountNumber(accounNumber, tf);



}
private double calculateAmountToBorrow  (String accountNumber){

     setBorrow(accountNumber);

 Double max2=(.5*mxb.maxAmountBorrowedF(accountNumber));
            
            Double max1=(.5*mxb.maxAmountBorrowed(accountNumber));  
            
   Double max=max2+max1;       

if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
    
if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){
    
   
      if(!(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsAllowed"+accountNumber+".txt"))==35)){        
    

            amountFieldDebit8.setValue(max1);
            
            Object[] optionsSGS1 = {"Don't Use Guarantors",  "Use Guarantors"};
            
int nSGS1 = JOptionPane.showOptionDialog(this, "\t\t"+ jComboBox3.getSelectedItem().toString()+"\n"+"\t\t"+"QUALIFIES FOR (WITH GUARANTORS) UGX:"+" "+df1.format(max)+"\n"+"\t\t"+"CAN BORROW(WITHOUT GUARANTORS) UGX:"+" "+df1.format(max1)+""+" "+"\n\n"+"DOUBLE CLICK TO USE GUARATORS OR NOT USE GUARATORS",
        
"CHOOSE ACTION", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1, optionsSGS1[0]);

            if(nSGS1==JOptionPane.YES_OPTION){
                
            jCheckBox52.setVisible(false);
            
            jLabel63.setVisible(false);
            
            Integer xxy=33;
            
fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "guarantorSetupLoan"+accountNumber+ ".txt"), xxy.toString());

            }
            
            else if (nSGS1==JOptionPane.NO_OPTION){ 
                
//            jCheckBox52.setVisible(true);
          
            jLabel63.setVisible(true);
            
            Integer xxy=23;
            
fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "guarantorSetupLoan"+accountNumber+ ".txt"), xxy.toString());

            }
  
}else{
      
amountFieldDebit8.setValue(parseDouble(fios.stringFileReader(fios.createFileName("persistence", "gaurantors", "amountQualifiedAfterGuarantors"+accountNumber+".txt"))));      
      
      }


         }else {
    
            jCheckBox52.setVisible(false);
            
            jLabel63.setVisible(false);
            
            amountFieldDebit8.setValue(max);
            
            }

            }else{

            amountFieldDebit8.setValue(max);
            }

return max;
            }
private void setBorrow(String account){

           Double max2=(.5*mxb.maxAmountBorrowedF(account));
            
            Double max1=(.5*mxb.maxAmountBorrowed(account));
          Double max  = max2+ max1;
fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithGuarantors"+account+".txt") , max.toString());  

fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantors"+account+".txt") , max1.toString()); 

fios.stringFileWriter(fios.createFileName("persistence", "gaurantors", "amountQualifiedWithoutGuarantorsTrans"+account+".txt") , max1.toString()); 
}

               
public void debitHundredPercent( String guarantorAccount, String amount){
     List debit= new ArrayList();List credit= new ArrayList();
            debit .add(0, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
            debit .add(1, sdf.format(fmt.getTodayDate()));
            debit .add(2, dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
            debit .add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
            debit .add(4, sdf.format(fmt.getTodayDate()));
            debit .add(5, df1.format(parseDouble(amount)));
            debit .add(6, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
            debit .add(7, dbq.AccountName(guarantorAccount));
            debit .add(8, "000zib");
             debit .add(9, getBatchNumber());
             debit .add(10, "DCap");
             debit .add(11, dbq.getTransactionSequenceNumber());
             
      
              credit.add(0, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
     credit.add(1, sdf.format(fmt.getTodayDate()));
      credit.add(2, "Guaranteed to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
      credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
      credit.add(4, sdf.format(fmt.getTodayDate()));
      credit.add(5, df1.format(parseDouble(amount)));
      credit.add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
      credit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
      credit.add(8, "000zib");
      credit.add(9, getBatchNumber());
      credit.add(10, "DCap");
      credit.add(11, dbq.getTransactionSequenceNumber());

      if(!(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt"))).split("[,]", 2)[0].equalsIgnoreCase("0")){
      
      post.decapitalise(debit, credit);
      
      } else{JOptionPane.showMessageDialog(this, "Please, First Set the Capital Account before you proceed"); return;}


}
   public void    debitTwentyFivePercent( String guarantorAccount,String amount){
   List debit= new ArrayList();List credit= new ArrayList();
     debit .add(0, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
            debit .add(1, sdf.format(fmt.getTodayDate()));
            debit .add(2, dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
            debit .add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
            debit .add(4, sdf.format(fmt.getTodayDate()));
            debit .add(5, df1.format(parseDouble(amount)));
            debit .add(6, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
            debit .add(7, dbq.AccountName( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
            debit .add(8, "000zib");
             debit .add(9, getBatchNumber());
             debit .add(10, "DCap");
             debit .add(11, dbq.getTransactionSequenceNumber());
             
      
    credit.add(0, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
     credit.add(1, sdf.format(fmt.getTodayDate()));
      credit.add(2, dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
      credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
      credit.add(4, sdf.format(fmt.getTodayDate()));
      credit.add(5, df1.format(parseDouble(amount)));
      credit.add(6, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
      credit.add(7, dbq.AccountName(guarantorAccount));
      credit.add(8, "000zib");
      credit.add(9, getBatchNumber());
      credit.add(10, "DCap");
      credit.add(11, dbq.getTransactionSequenceNumber());

      if(!(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1].equalsIgnoreCase("0"))){
  
    post.generalPosting(debit, credit);
      
      } else{JOptionPane.showMessageDialog(this, "Please, First Set the Guarantor Asset Account before you proceed"); return;}


   }

 public String   hundredPercent(String amount){
 
 Double amountdevide=((parseDouble(amount)/3)*4);
return amountdevide.toString();
 
 }
   
public String twentyFivePercent(String amount){

Double amountdevide=(parseDouble(amount)/3);
return amountdevide.toString();
}

public boolean creditHundredPercent(  String guarantorAccount,String amount){
    boolean success=false;
 List debit= new ArrayList();List credit= new ArrayList();
 debit .add(0, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
            debit .add(1, sdf.format(fmt.getTodayDate()));
            debit .add(2, dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
            debit .add(3, "Returned on"+" "+sdf.format(fmt.getTodayDate()));
            debit .add(4, sdf.format(fmt.getTodayDate()));
            debit .add(5, df1.format(parseDouble(amount)));
            debit .add(6, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
            debit .add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
            debit .add(8, "000zib");
             debit .add(9, getBatchNumber());
             debit .add(10, "DCap");
             debit .add(11, dbq.getTransactionSequenceNumber());
             
      
              credit.add(0, fmt.putSeparatorsOnNormaAccount(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt")).split("[,]", 2)[0]));
     credit.add(1, sdf.format(fmt.getTodayDate()));
      credit.add(2,  dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
      credit.add(3, "Guaranteed on"+" "+sdf.format(fmt.getTodayDate()));
      credit.add(4, sdf.format(fmt.getTodayDate()));
      credit.add(5, df1.format(parseDouble(amount)));
      credit.add(6, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
      credit.add(7, dbq.AccountName(guarantorAccount));
      credit.add(8, "000zib");
      credit.add(9, getBatchNumber());
      credit.add(10, "DCap");
      credit.add(11, dbq.getTransactionSequenceNumber());
      
   if(!(fios.stringFileReader(fios.createFileName("persistence", "interestAccount", "setcapitalAc1.txt"))).split("[,]", 2)[0].equalsIgnoreCase("0")){
      
     success= post.regularShareContribution(debit, credit);
      } else{JOptionPane.showMessageDialog(this, "Please, First Set the Capital Account before you proceed");}   
      
  return success;    
}
  public  boolean   creditTwentyFivePercent( String guarantorAccount,String amount){
    boolean success=false;
      List debit= new ArrayList();List credit= new ArrayList();
  debit .add(0,  fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
            debit .add(1, sdf.format(fmt.getTodayDate()));
            debit .add(2,  dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
            debit .add(3, "Returned on"+" "+sdf.format(fmt.getTodayDate()));
            debit .add(4, sdf.format(fmt.getTodayDate()));
            debit .add(5, df1.format(parseDouble(amount)));
            debit .add(6,fmt.putSeparatorsOnNormaAccount(guarantorAccount));
            debit .add(7, dbq.AccountName(guarantorAccount));
            debit .add(8, "000zib");
             debit .add(9, getBatchNumber());
             debit .add(10, "DCap");
             debit .add(11, dbq.getTransactionSequenceNumber());
             
      
              credit.add(0, fmt.putSeparatorsOnNormaAccount(guarantorAccount));
     credit.add(1, sdf.format(fmt.getTodayDate()));
      credit.add(2,  dbq.AccountName(guarantorAccount).split("\\s")[0]+" "+"Guarant to"+" "+dbq.AccountName(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"))).split("\\s")[0]);
      credit.add(3, "Returned on"+" "+sdf.format(fmt.getTodayDate()));
      credit.add(4, sdf.format(fmt.getTodayDate()));
      credit.add(5, df1.format(parseDouble(amount)));
      credit.add(6, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
      credit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1]));
      credit.add(8, "000zib");
      credit.add(9, getBatchNumber());
      credit.add(10, "DCap");
      credit.add(11, dbq.getTransactionSequenceNumber());

      if(!(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount2.txt")).split("[,]", 3)[1].equalsIgnoreCase("0"))){
      
      success=post.generalPosting(debit, credit);
      } else{JOptionPane.showMessageDialog(this, "Please, First Set the Guarantor Asset Account before you proceed"); }
  return success;
  }

private void processLoanRequest(String accountNumber){

                String groupId="G"+""+getCounter().substring(1, 5);
                
            
                
            fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
            
            fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"), accountNumber);

            if(!loan.loanExists(accountNumber)){

                Integer xxy=1;

                fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+accountNumber+ ".txt"), xxy.toString());

            }

//            updateFields(jTextField14,accountNumber);
            jTextField36.setText(fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[0]);
            jComboBox8.setSelectedItem(fios.stringFileReader(fios.createFileName("persistence", "loanTenure", "handleValues.txt")).split("[,]", 2)[1].replaceAll(",", ""));
            jComboBox8.setEditable(false);

             calculateAmountToBorrow(accountNumber);

            jTextField35.setText("Mothly Income");

            fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+ accountNumber+".txt"));

            int windowhhsj =fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+ accountNumber+".txt"));
            
            String details=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+accountNumber+".txt"));
            switch(windowhhsj ){

                case 1:
               this.updateDefaults(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")));      
              
                    
             
     if(loan.borrowerExists(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
         
       useDefaultBorrowerDetails.setVisible(false);  
     updateExistingBorrowersDetails.setVisible(true);  
     
     }else if(!loan.borrowerExists(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt")))){
     
     
       useDefaultBorrowerDetails.setVisible(true);  
     updateExistingBorrowersDetails.setVisible(false);  
     
     }
             
                
                jComboBox3.setVisible(true);
                jTextField36.setVisible(true);
                jComboBox8.setVisible(true);
                jCheckBox49.setVisible(true);
                amountFieldDebit8.setVisible(true);
                jCheckBox51.setVisible(true);
//                jTextField35.setVisible(true);

//                jTextField14.setVisible(true);
                jTextField18.setVisible(true);
                jComboBox4.setVisible(true);
                jCheckBox48.setVisible(true);
                amountFieldDebit7.setVisible(true);
                jCheckBox50.setVisible(true);
//                jComboBox2.setVisible(true);

                jButton3.setEnabled(true);
                jButton5.setEnabled(false);
                jButton4.setEnabled(false);
                jButton1.setEnabled(false);
               
                jLabel29.setVisible(true);
                jLabel10.setVisible(true);
                jLabel36.setVisible(true);
                jLabel15.setVisible(true);
                jLabel28.setVisible(true);
                jLabel27.setVisible(true);
//                jLabel23.setVisible(true);
//                jLabel25.setVisible(true);
                jLabel45.setVisible(false);

                jLabel62.setVisible(false);
                jLabel41.setVisible(false);
                jLabel49.setVisible(false);
                jLabel50.setVisible(false);

                jLabel26.setVisible(false);
                jLabel42.setVisible(false);
                jLabel51.setVisible(false);
                jLabel52.setVisible(false);

                jLabel37.setVisible(false);
                jLabel43.setVisible(false);
                jLabel53.setVisible(false);
                jLabel54.setVisible(false);

                jLabel38.setVisible(false);
                jLabel46.setVisible(false);
                jLabel55.setVisible(false);
                jLabel58.setVisible(false);

                jLabel39.setVisible(false);
                jLabel47.setVisible(false);
                jLabel59.setVisible(false);
                jLabel60.setVisible(false);
                jTextField36.setEnabled(true);
                jComboBox8.setEnabled(true);
                jCheckBox49.setEnabled(true);
                amountFieldDebit8.setEnabled(true);
                jCheckBox51.setEnabled(true);
//                jTextField35.setEnabled(true);
//                jTextField14.setEnabled(true);
                jTextField18.setEnabled(true);
                jComboBox4.setEnabled(true);
                jCheckBox48.setEnabled(true);
                amountFieldDebit7.setEnabled(true);
                jCheckBox50.setEnabled(true);
//                jComboBox2.setEnabled(true);

                jLabel45.setEnabled(true);
                jLabel36.setEnabled(true);
                jLabel10.setEnabled(true);
                jLabel15.setEnabled(true);
                jLabel28.setEnabled(true);
                jLabel27.setEnabled(true);
//                jLabel23.setEnabled(true);
//                jLabel25.setEnabled(true);
                jButton14.setEnabled(false);
//                  jButton16.setEnabled(false);
//                jButton15.setEnabled(true);
                jComboBox2.setSelectedIndex(1);
//                  jTextField5.setVisible(true);  
//                 jTextField4.setVisible(true);  
//                 jCheckBox4.setVisible(true); 
//                jLabel31.setVisible(true); 
//                jLabel30.setVisible(true);
            jTextField5.setText(groupId);
             jTextField4.setText(jComboBox3.getSelectedItem().toString().replaceAll(" ","").trim());
                break;
                case 2:
                     useDefaultBorrowerDetails.setVisible(false);  
                       jCheckBox4   .setVisible(false);
            jLabel31  .setVisible(false); 
            jTextField5.setVisible(false);
            jLabel30    .setVisible(false);   
            jTextField4 .setVisible(false);     
            jComboBox12GroupId.setVisible(false);
            jComboBox11GroupName.setVisible(false);
            jButton20.setVisible(false);
            jButton16.setVisible(false);
            jButton22.setVisible(false);
            updateExistingBorrowersDetails.setVisible(false);
            jLabel18.setVisible(false);
            jComboBox14.setVisible(false);
                        updateExistingBorrowersDetails.setVisible(false);
//                     if(loan.LoanCycleThere(accountNumber,this)){
//                
//                    jCheckBox13.setVisible(true);
//                 jLabel18.setVisible(false);
//                jComboBox14.setVisible(false);
//                }else{
//                jCheckBox13.setVisible(false);
//                jLabel18.setVisible(true);
//                jComboBox14.setVisible(true);
//                }
//                    JOptionPane.showMessageDialog(this, "ddd");
                     jLabel18.setVisible(false);
                jComboBox14.setVisible(false);
                 jCheckBox52.setVisible(false);
                jComboBox3.setVisible(true);
                jTextField36.setVisible(false);
                jComboBox8.setVisible(false);
                jCheckBox49.setVisible(false);
                amountFieldDebit8.setVisible(false);
                jCheckBox51.setVisible(false);
                jTextField35.setVisible(false);

//                jTextField14.setVisible(false);
                jTextField18.setVisible(false);
                jComboBox4.setVisible(false);
                jCheckBox48.setVisible(false);
                amountFieldDebit7.setVisible(false);
                jCheckBox50.setVisible(false);
                jComboBox2.setVisible(false);

                jButton3.setEnabled(false);
                jButton5.setEnabled(true);
                jButton4.setEnabled(false);
                jButton1.setEnabled(false);

                jLabel29.setVisible(true);
                jLabel10.setVisible(false);
                jLabel36.setVisible(false);
                jLabel15.setVisible(false);
                jLabel28.setVisible(false);
                jLabel27.setVisible(false);
                jLabel23.setVisible(false);
                jLabel25.setVisible(false);

                jLabel45.setVisible(true);
                jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE SECOND STAGE OF DOCUMENTATION&COLLATERAL APPROVAL");
                jLabel62.setVisible(true);
                jLabel41.setVisible(true);
                jLabel41.setText(details.split("[,]", 13)[10]);

                jLabel49.setVisible(true);
                jLabel50.setVisible(true);
                jLabel50.setText(details.split("[,]", 13)[9]);

                jLabel26.setVisible(true);
                jLabel42.setVisible(true);
                jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                jLabel51.setVisible(true);
                jLabel52.setVisible(true);

                jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                jLabel37.setVisible(true);
                jLabel43.setVisible(true);
                jLabel43.setText(details.split("[,]", 13)[4]);

                jLabel53.setVisible(true);
                jLabel54.setVisible(true);
                jLabel54.setText(details.split("[,]", 13)[5]);

                jLabel38.setVisible(true);
                jLabel46.setVisible(true);
                jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                jLabel55.setVisible(true);
                jLabel58.setVisible(true);
                jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                jLabel39.setVisible(true);
                jLabel47.setVisible(true);
                jLabel47.setText(details.split("[,]", 13)[0]);
                jLabel59.setVisible(true);
                jLabel60.setVisible(true);
                jLabel60.setText(details.split("[,]", 13)[8]);
                jTextField36.setEnabled(true);
                jComboBox8.setEnabled(true);
                jCheckBox49.setEnabled(true);
                amountFieldDebit8.setEnabled(true);
                jCheckBox51.setEnabled(true);
//                jTextField35.setEnabled(true);
//                jTextField14.setEnabled(true);
                jTextField18.setEnabled(true);
                jComboBox4.setEnabled(true);
                jCheckBox48.setEnabled(true);
                amountFieldDebit7.setEnabled(true);
                jCheckBox50.setEnabled(true);
//                jComboBox2.setEnabled(true);

                jLabel45.setEnabled(true);
                jLabel36.setEnabled(true);
                jLabel10.setEnabled(true);
                jLabel15.setEnabled(true);
                jLabel28.setEnabled(true);
                jLabel27.setEnabled(true);
//                jLabel23.setEnabled(true);
//                jLabel25.setEnabled(true);
                jButton14.setEnabled(false);
//                  jButton16.setEnabled(false);
//                jButton15.setEnabled(true);
                jComboBox2.setSelectedIndex(1);
//                 jTextField5.setVisible(false);  
//                 jTextField4.setVisible(false);  
//                 jCheckBox4.setVisible(false); 
//                jLabel31.setVisible(false); 
//                        jLabel30.setVisible(false); 
                break;
                case 3:
                     useDefaultBorrowerDetails.setVisible(false);  
                       jCheckBox4   .setVisible(false);
            jLabel31  .setVisible(false); 
            jTextField5.setVisible(false);
            jLabel30    .setVisible(false);   
            jTextField4 .setVisible(false);     
            jComboBox12GroupId.setVisible(false);
            jComboBox11GroupName.setVisible(false);
            jButton20.setVisible(false);
            jButton16.setVisible(false);
            jButton22.setVisible(false);
            updateExistingBorrowersDetails.setVisible(false);
            jLabel18.setVisible(false);
            jComboBox14.setVisible(false);
                        updateExistingBorrowersDetails.setVisible(false);
                     jLabel18.setVisible(false);
                jComboBox14.setVisible(false);
//                     JOptionPane.showMessageDialog(this, "xxxx");
                 jCheckBox52.setVisible(false);    
                jComboBox3.setVisible(true);
                jTextField36.setVisible(false);
                jComboBox8.setVisible(false);
                jCheckBox49.setVisible(false);
                amountFieldDebit8.setVisible(false);
                jCheckBox51.setVisible(false);
                jTextField35.setVisible(false);

//                jTextField14.setVisible(false);
                jTextField18.setVisible(false);
                jComboBox4.setVisible(false);
                jCheckBox48.setVisible(false);
                amountFieldDebit7.setVisible(false);
                jCheckBox50.setVisible(false);
                jComboBox2.setVisible(false);

                jButton3.setEnabled(false);
                jButton5.setEnabled(false);
                jButton4.setEnabled(true);
                jButton1.setEnabled(false);

                jLabel29.setVisible(true);
                jLabel10.setVisible(false);
                jLabel36.setVisible(false);
                jLabel15.setVisible(false);
                jLabel28.setVisible(false);
                jLabel27.setVisible(false);
                jLabel23.setVisible(false);
                jLabel25.setVisible(false);

                jLabel45.setVisible(true);
                jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE SECOND LAST STAGE OF LOAN APPROVALS");

                jLabel62.setVisible(true);
                jLabel41.setVisible(true);
                jLabel41.setText(details.split("[,]", 13)[10]);

                jLabel49.setVisible(true);
                jLabel50.setVisible(true);
                jLabel50.setText(details.split("[,]", 13)[9]);

                jLabel26.setVisible(true);
                jLabel42.setVisible(true);
                jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                jLabel51.setVisible(true);
                jLabel52.setVisible(true);

                jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                jLabel37.setVisible(true);
                jLabel43.setVisible(true);
                jLabel43.setText(details.split("[,]", 13)[4]);

                jLabel53.setVisible(true);
                jLabel54.setVisible(true);
                jLabel54.setText(details.split("[,]", 13)[5]);

                jLabel38.setVisible(true);
                jLabel46.setVisible(true);
                jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                jLabel55.setVisible(true);
                jLabel58.setVisible(true);
                jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                jLabel39.setVisible(true);
                jLabel47.setVisible(true);
                jLabel47.setText(details.split("[,]", 13)[0]);
                jLabel59.setVisible(true);
                jLabel60.setVisible(true);
                jLabel60.setText(details.split("[,]", 13)[8]);
                jTextField36.setEnabled(true);
                jComboBox8.setEnabled(true);
                jCheckBox49.setEnabled(true);
                amountFieldDebit8.setEnabled(true);
                jCheckBox51.setEnabled(true);
//                jTextField35.setEnabled(true);
//                jTextField14.setEnabled(true);
                jTextField18.setEnabled(true);
                jComboBox4.setEnabled(true);
                jCheckBox48.setEnabled(true);
                amountFieldDebit7.setEnabled(true);
                jCheckBox50.setEnabled(true);
//                jComboBox2.setEnabled(true);

                jLabel45.setEnabled(true);
                jLabel36.setEnabled(true);
                jLabel10.setEnabled(true);
                jLabel15.setEnabled(true);
                jLabel28.setEnabled(true);
                jLabel27.setEnabled(true);
//                jLabel23.setEnabled(true);
                jLabel25.setEnabled(true);
                jButton14.setEnabled(false);
//                 jButton16.setEnabled(false);
//                jButton15.setEnabled(true);
                jComboBox2.setSelectedIndex(1);
//                 jTextField5.setVisible(false);  
//                 jTextField4.setVisible(false);  
//                 jCheckBox4.setVisible(false); 
//                jLabel31.setVisible(false); 
//                        jLabel30.setVisible(false);
                break;
                case 4:
//                    JOptionPane.showMessageDialog(this, "xxxx4");
                      useDefaultBorrowerDetails.setVisible(false);   
                    jCheckBox4   .setVisible(false);
            jLabel31  .setVisible(false); 
            jTextField5.setVisible(false);
            jLabel30    .setVisible(false);   
            jTextField4 .setVisible(false);     
            jComboBox12GroupId.setVisible(false);
            jComboBox11GroupName.setVisible(false);
            jButton20.setVisible(false);
            jButton16.setVisible(false);
            jButton22.setVisible(false);
            updateExistingBorrowersDetails.setVisible(false);
            jLabel18.setVisible(false);
            jComboBox14.setVisible(false);
                        updateExistingBorrowersDetails.setVisible(false); 
                    jLabel18.setVisible(false);
                jComboBox14.setVisible(false);
                 jCheckBox52.setVisible(false);    
                jComboBox3.setVisible(true);
                jTextField36.setVisible(false);
                jComboBox8.setVisible(false);
                jCheckBox49.setVisible(false);
                amountFieldDebit8.setVisible(false);
                jCheckBox51.setVisible(false);
                jTextField35.setVisible(false);

//                jTextField14.setVisible(false);
                jTextField18.setVisible(false);
                jComboBox4.setVisible(false);
                jCheckBox48.setVisible(false);
                amountFieldDebit7.setVisible(false);
                jCheckBox50.setVisible(false);
                jComboBox2.setVisible(false);
                jLabel29.setVisible(true);
                jLabel10.setVisible(false);
                jLabel36.setVisible(false);
                jLabel15.setVisible(false);
                jLabel28.setVisible(false);
                jLabel27.setVisible(false);
                jLabel23.setVisible(false);
                jLabel25.setVisible(false);
                jButton3.setEnabled(false);
                jButton5.setEnabled(false);
                jButton4.setEnabled(false);
                jButton1.setEnabled(true);

                jLabel45.setVisible(true);
                jLabel45.setText("DETAILS OF THE LOAN APPLIED FOR AND WHICH IS AT THE FINAL STAGE OF LOAN PROCESSING");
                jLabel62.setVisible(true);
                jLabel41.setVisible(true);
                jLabel41.setText(details.split("[,]", 13)[10]);

                jLabel49.setVisible(true);
                jLabel50.setVisible(true);
                jLabel50.setText(details.split("[,]", 13)[9]);

                jLabel26.setVisible(true);
                jLabel42.setVisible(true);
                jLabel42.setText(df1.format(parseDouble(details.split("[,]", 13)[3])));

                jLabel51.setVisible(true);
                jLabel52.setVisible(true);

                jLabel52.setText(df1.format(parseDouble(details.split("[,]", 13)[6])));

                jLabel37.setVisible(true);
                jLabel43.setVisible(true);
                jLabel43.setText(details.split("[,]", 13)[4]);

                jLabel53.setVisible(true);
                jLabel54.setVisible(true);
                jLabel54.setText(details.split("[,]", 13)[5]);

                jLabel38.setVisible(true);
                jLabel46.setVisible(true);
                jLabel46.setText(df1.format(parseDouble(details.split("[,]", 13)[1])));

                jLabel55.setVisible(true);
                jLabel58.setVisible(true);
                jLabel58.setText(df1.format(parseDouble(details.split("[,]", 13)[2])));

                jLabel39.setVisible(true);
                jLabel47.setVisible(true);
                jLabel47.setText(details.split("[,]", 13)[0]);
                jLabel59.setVisible(true);
                jLabel60.setVisible(true);
                jLabel60.setText(details.split("[,]", 13)[8]);
                jTextField36.setEnabled(true);
                jComboBox8.setEnabled(true);
                jCheckBox49.setEnabled(true);
                amountFieldDebit8.setEnabled(true);
                jCheckBox51.setEnabled(true);
//                jTextField35.setEnabled(true);
//                jTextField14.setEnabled(true);
                jTextField18.setEnabled(true);
                jComboBox4.setEnabled(true);
                jCheckBox48.setEnabled(true);
                amountFieldDebit7.setEnabled(true);
                jCheckBox50.setEnabled(true);
//                jComboBox2.setEnabled(true);

                jLabel45.setEnabled(true);
                jLabel36.setEnabled(true);
                jLabel10.setEnabled(true);
                jLabel15.setEnabled(true);
                jLabel28.setEnabled(true);
                jLabel27.setEnabled(true);
//                jLabel23.setEnabled(true);
//                jLabel25.setEnabled(true);
                jButton14.setEnabled(false);
//                jButton15.setEnabled(true);
                jComboBox2.setSelectedIndex(1);
//                 jTextField5.setVisible(false);  
//                 jTextField4.setVisible(false);  
//                 jCheckBox4.setVisible(false); 
//                jLabel31.setVisible(false); 
//                        jLabel30.setVisible(false);
                break;
                case 5:
                    useDefaultBorrowerDetails.setVisible(false);
            jCheckBox4   .setVisible(false);
            jLabel31  .setVisible(false); 
            jTextField5.setVisible(false);
            jLabel30    .setVisible(false);   
            jTextField4 .setVisible(false);     
            jComboBox12GroupId.setVisible(false);
            jComboBox11GroupName.setVisible(false);
            jButton20.setVisible(false);
            jButton16.setVisible(false);
            jButton22.setVisible(false);
            updateExistingBorrowersDetails.setVisible(false);
            jLabel18.setVisible(false);
            jComboBox14.setVisible(false);
//                    JOptionPane.showMessageDialog(this, "xxxx5");
                 jCheckBox52.setVisible(false);
                jComboBox3.setVisible(true);
                jTextField36.setVisible(false);
                jTextField36.setEnabled(false);
                jComboBox8.setVisible(false);
                jComboBox8.setEnabled(false);
                jCheckBox49.setVisible(false);
                jCheckBox49.setEnabled(false);
                amountFieldDebit8.setVisible(false);
                amountFieldDebit8.setEnabled(false);

                jCheckBox51.setVisible(false);
                jCheckBox51.setEnabled(false);
                jTextField35.setVisible(false);
                jTextField35.setEnabled(false);
//                jTextField14.setVisible(true);
//                jTextField14.setEnabled(true);
                jTextField18.setVisible(false);
                jTextField18.setEnabled(false);
                jComboBox4.setVisible(false);
                jComboBox4.setEnabled(false);

                jCheckBox48.setVisible(false);
                jCheckBox48.setEnabled(false);

                amountFieldDebit7.setVisible(false);
                amountFieldDebit7.setEnabled(false);
                jCheckBox50.setVisible(false);
                jCheckBox50.setEnabled(false);
//                jComboBox2.setVisible(false);
                jComboBox2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton3.setEnabled(false);
                jButton5.setEnabled(false);

                jButton4.setEnabled(false);
                jButton1.setEnabled(false);

                jLabel29.setVisible(false);

                jLabel10.setVisible(false);
                jLabel10.setEnabled(false);
                jLabel36.setVisible(false);
                jLabel36.setEnabled(false);
                jLabel15.setVisible(false);
                jLabel15.setEnabled(false);
                jLabel28.setVisible(false);
                jLabel28.setEnabled(false);
                jLabel27.setVisible(false);
                jLabel27.setEnabled(false);
                jLabel23.setVisible(false);
                jLabel23.setEnabled(false);
                jLabel25.setVisible(false);
                jLabel25.setEnabled(false);
                jLabel45.setVisible(true);
                jLabel45.setEnabled(true);
                jLabel62.setVisible(false);
                jLabel41.setVisible(false);
                jLabel49.setVisible(false);
                jLabel50.setVisible(false);

                jLabel26.setVisible(false);
                jLabel42.setVisible(false);
                jLabel51.setVisible(false);
                jLabel52.setVisible(false);

                jLabel37.setVisible(false);
                jLabel43.setVisible(false);
                jLabel53.setVisible(false);
                jLabel54.setVisible(false);

                jLabel38.setVisible(false);
                jLabel46.setVisible(false);
                jLabel55.setVisible(false);
                jLabel58.setVisible(false);

                jLabel39.setVisible(false);
                jLabel47.setVisible(false);
                jLabel59.setVisible(false);
                jLabel60.setVisible(false);
                jLabel45.setText("THE  LOAN PROCESSING WAS COMPLETED AND THE LOAN WAS DISBURSED TO THE BORROWER");

                jButton14.setEnabled(true);
//                 jButton16.setEnabled(true);
//                jButton15.setEnabled(false);
                jTextField36.setText(" ");
                jComboBox8.setSelectedItem(" ");
                amountFieldDebit8.setValue(null);
                jTextField35.setText(" ");
                jComboBox2.setSelectedItem(" ");
                jComboBox4.setSelectedItem(" ");
//                 jTextField5.setVisible(false);  
//                 jTextField4.setVisible(false);  
//                 jCheckBox4.setVisible(false); 
//                jLabel31.setVisible(false); 
//                        jLabel30.setVisible(false);
                break;
                    case 6:
//                             JOptionPane.showMessageDialog(this, "xxxx6");
                         jLabel45.setText("THE  LOAN PROCESSING WAS COMPLETED AND THE LOAN WAS DISBURSED TO THE BORROWER");
  useDefaultBorrowerDetails.setVisible(false);
                         jLabel18.setVisible(false);
     updateExistingBorrowersDetails.setVisible(false);
                jComboBox14.setVisible(false);
                jButton14.setEnabled(true);
//                 jButton16.setEnabled(true);
//                jButton15.setEnabled(false);
                jTextField36.setText(" ");
                jComboBox8.setSelectedItem(" ");
                amountFieldDebit8.setValue(null);
                jTextField35.setText(" ");
                jComboBox2.setSelectedItem(" ");
                jComboBox4.setSelectedItem(" ");
//                    jTextField5.setVisible(false);  
//                 jTextField4.setVisible(false);  
//                 jCheckBox4.setVisible(false); 
//                jLabel31.setVisible(false); 
//                        jLabel30.setVisible(false);     
                         break;
                     }
        


}
//private boolean processArrearsFirst(String amountInArrears,String accountNumber){
//
//
//String BathNumber=this.batchCode();
//boolean success=false;
//      List debit= new ArrayList();List credit= new ArrayList();
//      
//      
//              debit.add(0, fmt.putSeparatorsOnNormaAccount(accountNumber));
//     debit.add(1, sdf.format(fmt.getTodayDate()));
//      debit.add(2, dbq.AccountName(accountNumber).split("\\s")[0]+"'s"+" "+"Loan Payment");
//      debit.add(3, "Paid on"+" "+sdf.format(fmt.getTodayDate()));
//      debit.add(4, sdf.format(fmt.getTodayDate()));
//      debit.add(5, df1.format(parseDouble(amountInArrears)));
//      debit.add(6, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//      debit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//      debit.add(8, "000zib");
//      debit.add(9, getBatchNumber());
//      debit.add(10, "LoanR");
//      debit.add(11, dbq.getTransactionSequenceNumber());
//      
//  credit .add(0,  fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//            credit .add(1, sdf.format(fmt.getTodayDate()));
//            credit .add(2,  dbq.AccountName(accountNumber).split("\\s")[0]+" "+"Loan Arrears Payment DD"+" "+sdf.format(fmt.getTodayDate()));
//            credit .add(3, "Paid on"+" "+sdf.format(fmt.getTodayDate()));
//            credit .add(4, sdf.format(fmt.getTodayDate()));
//            credit .add(5, df1.format(parseDouble(amountInArrears)));
//            credit .add(6,fmt.putSeparatorsOnNormaAccount(accountNumber));
//            credit .add(7, dbq.AccountName(accountNumber));
//            credit .add(8, "000zib");
//             credit .add(9, getBatchNumber());
//             credit .add(10, "LoanR");
//             credit .add(11, dbq.getTransactionSequenceNumber());
//
//      
//      success=post.loanRepayments(debit, credit,NewLoanApplication.this);
//   
//  return success;
//
//}
//private boolean processCurrentInstalmentFirst(String theInstalment,String accountNumber){
//boolean processSuccess=false;
//
//if(parseDouble(theInstalment)>0){
//
//processSuccess=this.processArrearsFirst(theInstalment, accountNumber);
//
//}else{
//processSuccess=true;
//}
//
//
//return processSuccess;
//}

//public boolean closeLoanFirstNormal(String accountNumber){
//    
//String BathNumber=this.batchCode();
//boolean success=false;
//      List debit= new ArrayList();List credit= new ArrayList();
//  Double amount=parseDouble(loan.getRemainingAmountRepayment(accountNumber));
//
//            if(amount>0.0){
//  debit.add(0, fmt.putSeparatorsOnNormaAccount(accountNumber));
//     debit.add(1, sdf.format(fmt.getTodayDate()));
//      debit.add(2, dbq.AccountName(accountNumber).split("\\s")[0]+"'s"+" "+"Loan Payment");
//      debit.add(3, "Paid on"+" "+sdf.format(fmt.getTodayDate()));
//      debit.add(4, sdf.format(fmt.getTodayDate()));
//      debit.add(5, df1.format(amount));
//      debit.add(6, fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//      debit.add(7, dbq.AccountName(fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//      debit.add(8, "000zib");
//      debit.add(9, getBatchNumber());
//      debit.add(10, "LoanR");
//      debit.add(11, dbq.getTransactionSequenceNumber());
//      
//  credit .add(0,  fmt.putSeparatorsOnNormaAccount( fios.stringFileReader(fios.createFileName("persistence", "disburseAc", "setDisburseAccount1.txt")).split("[,]", 3)[1]));
//            credit .add(1, sdf.format(fmt.getTodayDate()));
//            credit .add(2,  dbq.AccountName(accountNumber).split("\\s")[0]+" "+"Loan Payment DD"+" "+sdf.format(fmt.getTodayDate()));
//            credit .add(3, "Paid on"+" "+sdf.format(fmt.getTodayDate()));
//            credit .add(4, sdf.format(fmt.getTodayDate()));
//            credit .add(5, df1.format(amount));
//            credit .add(6,fmt.putSeparatorsOnNormaAccount(accountNumber));
//            credit .add(7, dbq.AccountName(accountNumber));
//            credit .add(8, "000zib");
//             credit .add(9, getBatchNumber());
//             credit .add(10, "LoanR");
//             credit .add(11, dbq.getTransactionSequenceNumber());
//
//
//     
//      
//      success=post.loanRepayments(debit, credit,NewLoanApplication.this);
//
//
//          
//            }else{
//            success=true;
//            
//            }
//
//  
//   
//  return success;
//
//
//}

public void amortizeTopUpLoan(List topups1,String accountNumber2){
// loan.createNewLoanAmort(accountNumber2,this);
String typeOfTime=  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt")).split("[,]", 7)[3];  
    
fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));

            fios.forceFileExistanceAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
            int n=1; String var="";
            while(n<=7){
                if(n==1){var=accountNumber2;}
                if(n==2){var=dbq.AccountName(accountNumber2);}
                if(n==3){var=topups1.get(2).toString();}
                 if(n==4){var=typeOfTime;}
                  if(n==5){var=topups1.get(0).toString();}
                   if(n==6){var="Monthly Income";}
                   if(n==7){var="•BUSINESS PURPOSE";}
                   
                fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), var);
                fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
                n++;
            }
  
  Integer xh=1;
  fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), xh.toString());
   amortDetails=new ArrayList();
  amortDetails.add(jTextField5.getText());
   amortDetails.add(jTextField4.getText());
  
     AmotizationCalculator fTopUp = new  AmotizationCalculator(userId,amortDetails);
        fTopUp.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fTopUp.setSize(screen.getSize());
        fTopUp.pack();
        this.dispose(); 

}
private boolean closeLoanFirstTopUp(String accountNumber){
    


return loan.closeTopUpLoan(accountNumber);

}
public String getCounter(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanCounter.txt")))+1);

return thecounter;
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
 public String getCounter1(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt")))+1);

fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"), thecounter);

return thecounter;
} 
 
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
 
 public void addItemInList(String theItem){
 
 theCollection.add(theItem);
 }
 
 public void updateDefaults(String accountNumber){
 String  existance="First Time Borrower";
     if(loan.borrowerExists(accountNumber)){
     existance="Existing Borrower";
     
     }else if(!loan.borrowerExists(accountNumber)){
     
     
      existance="First Time Borrower";
     
     
     }
     
     
     
     
  theCollection=new ArrayList();
        theCollection.add(existance);
        theCollection.add("Individual");
        theCollection.add("Group 1");
        theCollection.add("Group 1");
        theCollection.add("Cycle"+loan.cycleNumber(accountNumber));
        theCollection.add("Salary Loan");
        theCollection.add("Friends");
        theCollection.add("Single Instalment Loan");
        theCollection .add("Business Financing");
        theCollection.add("No History");
        theCollection.add("Can't Tell");
        theCollection.add("No");
        theCollection.add("0"); 
        theCollection.add("Level 1"); 
        theCollection.add("Monthly Income"); 
         theCollection.add("100,000-200,000");
         theCollection.add("Very Low");
        theCollection.add("Self Employment");
        theCollection.add("Agriculture");
         theCollection.add("Male");
           theCollection.add("Single");
           theCollection.add("Minor");
           theCollection.add("No Education");
           
            theCollection.add("Email");
           theCollection.add("augbazi@mail.com");
           theCollection.add("Excellent");
              theCollection.add("0782231039");
 
 
 }
 
 
 
 
 
 public void addItemFromExistingB(){
 
//     List detailsOfExistingBorrower=loan.pickThoseOtherLoanDe();
     
     
 
 }
 
 
 public void setTheField(String theItemI){
 
     switch(theItemI){
         case "Other Loan Details":
            jButton16.setEnabled(true);
             
             break;
      case "Economic Status":
          jButton22.setEnabled(true);  
          break;
      case "Bio Data":
          
//         jButton17 .setEnabled(true);  
          break;
          
//      case "Bio Data":
//          break;
     }
 
 }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel actionLabel69;
    private javax.swing.JFormattedTextField amountFieldDebit10;
    private javax.swing.JFormattedTextField amountFieldDebit11;
    private javax.swing.JFormattedTextField amountFieldDebit12;
    private javax.swing.JFormattedTextField amountFieldDebit13;
    private javax.swing.JFormattedTextField amountFieldDebit14;
    private javax.swing.JFormattedTextField amountFieldDebit15;
    private javax.swing.JFormattedTextField amountFieldDebit16;
    private javax.swing.JFormattedTextField amountFieldDebit17;
    private javax.swing.JFormattedTextField amountFieldDebit18;
    private javax.swing.JFormattedTextField amountFieldDebit19;
    private javax.swing.JFormattedTextField amountFieldDebit2;
    private javax.swing.JFormattedTextField amountFieldDebit20;
    private javax.swing.JFormattedTextField amountFieldDebit21;
    private javax.swing.JFormattedTextField amountFieldDebit22;
    private javax.swing.JFormattedTextField amountFieldDebit23;
    private javax.swing.JFormattedTextField amountFieldDebit24;
    private javax.swing.JFormattedTextField amountFieldDebit25;
    private javax.swing.JFormattedTextField amountFieldDebit26;
    private javax.swing.JFormattedTextField amountFieldDebit27;
    private javax.swing.JFormattedTextField amountFieldDebit28;
    private javax.swing.JFormattedTextField amountFieldDebit3;
    private javax.swing.JFormattedTextField amountFieldDebit4;
    private javax.swing.JFormattedTextField amountFieldDebit5;
    private javax.swing.JFormattedTextField amountFieldDebit6;
    private javax.swing.JFormattedTextField amountFieldDebit7;
    private javax.swing.JFormattedTextField amountFieldDebit8;
    private javax.swing.JFormattedTextField amountFieldDebit9;
    private javax.swing.JLabel bhasLabel71;
    private javax.swing.JLabel bneedsLabel64;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox fifthCheckBox1;
    private javax.swing.JCheckBox fifthCheckBox2;
    private javax.swing.JCheckBox firstCheckBox1;
    private javax.swing.JCheckBox firstCheckBox2;
    private javax.swing.JCheckBox fourthCheckBox1;
    private javax.swing.JCheckBox fourthCheckBox2;
    private javax.swing.JLabel gacnoLabel67;
    private javax.swing.JLabel gallowamtLabel68;
    private javax.swing.JLabel gballowedLabel65;
    private javax.swing.JLabel ggallowedLabel70;
    private javax.swing.JLabel gnamwLabel66;
    private javax.swing.JPanel guarantorSetupPanel;
    private javax.swing.JTextField guarantoursAccount10;
    private javax.swing.JTextField guarantoursAccount11;
    private javax.swing.JTextField guarantoursAccount12;
    private javax.swing.JTextField guarantoursAccount13;
    private javax.swing.JTextField guarantoursAccount14;
    private javax.swing.JTextField guarantoursAccount5;
    private javax.swing.JTextField guarantoursAccount6;
    private javax.swing.JTextField guarantoursAccount7;
    private javax.swing.JTextField guarantoursAccount8;
    private javax.swing.JTextField guarantoursAccount9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton22;
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
    private javax.swing.JButton jButton4;
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
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox48;
    private javax.swing.JCheckBox jCheckBox49;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox50;
    private javax.swing.JCheckBox jCheckBox51;
    private javax.swing.JCheckBox jCheckBox52;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11GroupName;
    private javax.swing.JComboBox<String> jComboBox12GroupId;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField11;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    private javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTree jTreeNewLoanApplication;
    private javax.swing.JPanel loanClosedPanel;
    private javax.swing.JPanel newLoanPanel;
    private javax.swing.JButton refreshButton1;
    private javax.swing.JButton refreshButton2;
    private javax.swing.JCheckBox secondCheckBox1;
    private javax.swing.JCheckBox secondCheckBox2;
    private javax.swing.JButton submitButton1;
    private javax.swing.JButton submitButton2;
    private javax.swing.JCheckBox thirdCheckBox1;
    private javax.swing.JCheckBox thirdCheckBox2;
    private javax.swing.JPanel topUpPanel;
    private javax.swing.JPanel topUpPanel1;
    private javax.swing.JCheckBox updateExistingBorrowersDetails;
    private javax.swing.JCheckBox useDefaultBorrowerDetails;
    // End of variables declaration//GEN-END:variables

 @Override
    public void mouseClicked(MouseEvent me) {
         if (me.getClickCount()==2){

  
//fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+ accountNumber+".txt")
        
          if(me.getSource() == jTable3){  
                
                    int selectedRow =jTable3.getSelectedRow();
                    int selectedColumn =jTable3.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	
       
     
          String accountNumber = jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(selectedRow), 2).toString();
         
          
        Integer xl=1;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());   
          
          
          
         
         
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),accountNumber); 
          }
    
     NewLoanApplication f = new   NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();        
            
         
                
           
       
          }
        
        
  
    if(me.getSource()==jTreeNewLoanApplication){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeNewLoanApplication.getLastSelectedPathComponent();
    String window =node.toString();
    switch (window){
    case "LOAN SETUP":

       Object[] options = {"Continue",  "Cancel"};
        int n = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
        if(n==JOptionPane.YES_OPTION){
        loanManagementSetup frm = new loanManagementSetup(userId);
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
        frm.pack();
        this.dispose();
        }
        else if (n==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;

        case "Loan Calculator":
       
            Object[] optionsSF = {"Continue",  "Cancel"};
            int nSF = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
            "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSF, optionsSF[0]);
            if(nSF==JOptionPane.YES_OPTION){
            LoanCalculator f = new LoanCalculator(userId);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();
            }
            else if (nSF==JOptionPane.NO_OPTION){ this.setVisible(true);}
            break;
    case "COLLATERAL AND DOCUMENTAION":
 
        Object[] optionsSSV = {"Continue",  "Cancel"};
        int nSSV = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsSSV,  optionsSSV[0]);
        if(nSSV==JOptionPane.YES_OPTION){
            
            
        CollateralandDocuments frm3 = new  CollateralandDocuments(userId);
        frm3.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm3.setSize(screen.getSize());
        frm3.pack();
        this.dispose();
        }
        else if (nSSV==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
        case "Amortization Calculator":
           
        Object[] optionsSGS = {"Continue",  "Cancel"};
        int nSGS = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
        if(nSGS==JOptionPane.YES_OPTION){
             Integer xh=0;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), xh.toString());
        amortDetails=new ArrayList();
        AmotizationCalculator frm5 = new AmotizationCalculator(userId,amortDetails);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
        this.dispose();
        }
        else if (nSGS==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
  
    
    
    case "Log Out":
           
        String borrowerAccount=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        
        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
            
            
        if(!(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "checkBox1Selected"+borrowerAccount+".txt"))==300) ){

        setBorrow(borrowerAccount);

        }
        
        } 
        
        
        Integer zxkj=1;
        
        fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"));
        
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+userId+".txt"), zxkj.toString());
        
        Object[] optionsSGS1 = {"Continue",  "Cancel"};
        int nSGS1 = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1, optionsSGS1[0]);
        if(nSGS1==JOptionPane.YES_OPTION){
            if(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "byPassLoanPosting.txt"))==77){
          fios.intFileWriterReplace(fios.createFileName("persistence", "topUpsSetUp", "byPassLoanPosting.txt"), "7777");   
             LoanManagmentWindow ffx = new LoanManagmentWindow (userId);
        ffx.setVisible(true);
        Dimension screensx = Toolkit.getDefaultToolkit().getScreenSize();
        ffx.setSize(screensx.getSize());
        ffx.pack();
        this.dispose();
            
            
            
            
            }else{
 switch(dbq.title(userId)){
            case "Loans Officer":
         LoanManagmentWindow ffx = new LoanManagmentWindow (userId);
        ffx.setVisible(true);
        Dimension screensx = Toolkit.getDefaultToolkit().getScreenSize();
        ffx.setSize(screensx.getSize());
        ffx.pack();
        this.dispose();
                break;
            case "Accountant":
         PostingEntryWindow fh = new  PostingEntryWindow(userId,amortDetails);
        fh.setVisible(true);
        Dimension screenh = Toolkit.getDefaultToolkit().getScreenSize();
        fh.setSize(screenh.getSize());
        fh.pack();
        this.dispose();
                
                
                break;
            case "Supervisor":
        PostingEntryWindow f = new  PostingEntryWindow(userId,amortDetails);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
                break;
        
        
           case "Manager":
            LoanApprovals fx = new LoanApprovals (userId);
        fx.setVisible(true);
        Dimension screenx = Toolkit.getDefaultToolkit().getScreenSize();
        fx.setSize(screenx.getSize());
        fx.pack();
        this.dispose();
                break;
         case "Cashier":
       JFrame fg = new  Login();
        fg.setVisible(true);
        Dimension screeng = Toolkit.getDefaultToolkit().getScreenSize();
        fg.setSize(screeng.getSize());
        fg.pack();
        this.dispose();
                break;
               case "System Admin":
            CreateNewStaff fxs1 = new CreateNewStaff (userId);
        fxs1.setVisible(true);
        Dimension screenxs1 = Toolkit.getDefaultToolkit().getScreenSize();
        fxs1.setSize(screenxs1.getSize());
        fxs1.pack();
        this.dispose();
                break;
        }
            }
       
        }
        else if (nSGS1==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
        }}}
    }
    

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public String batchCode() {
    String batch="";int theNumber=0;
//            
//            
//    fios.forceFileExistance(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));   
//
//    String Thebatch= fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));
//    theNumber=parseInt(Thebatch)+1;
//   fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"), theNumber+"");
//   batch="BTN"+(theNumber+"");
return dbq.batchNumber()+""; 
    }

    @Override
    public String getTransactionSequenceNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUseId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLogInTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTransactionDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTransactionType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValueDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getChequeNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrReferenceNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrAccountNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrAccountName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNumberOfShares() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValueOfShares() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrAmount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrNarrative1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDrNarrative2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrAccountNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrAccountName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrAmount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrNarrative1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrNarrative2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCrReferenceNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTransactionSequenceNumber(String a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUserId(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogInTime(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTransactionDate(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTransactionType(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValueDate(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getBatchNumber() {
        return    batchNumber;
    
    }

    @Override
    public void setChequeNumber(String a) {
        
    }

    @Override
    public void setDrReferenceNumber(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrAccountNumber(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrAccountName(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNumberOfShares(int a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValueOfShares(double a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrAmount(double a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrNarrative1(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrNarrative2(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrAccountNumber(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrAccountName(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrAmount(double a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrNarrative1(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrNarrative2(String a) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrReferenceNumber(String a) {
        
    }

    @Override
    public void setBatchNumber() {
         batchNumber=this.batchCode();
    }
}
