/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frames;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.frameHelper.MyComboBoxModel;
import googo.pmms.project2.frameHelper.MyTableModel;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.frameHelper.ListDataModel_1111;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.reportsHelper.BudgetMaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.Timer;
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
public class Createinternalaccounts extends JFrame implements MouseListener,ActionListener,IUpdateText{
     
     SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
   ReportsDatabase rdb =new ReportsDatabase();
        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
    fileInputOutPutStreams fios= new fileInputOutPutStreams();   
     loanDatabaseQuaries loan=new loanDatabaseQuaries();
    DecimalFormat NumberFormat =new DecimalFormat("#,###");
    DatabaseQuaries dbq= new DatabaseQuaries();
    MyTableModel model;
 ArrayList<ArrayList<String>> data5;
    MyComboBoxModel modelcombo,modelcombo1;
      SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
           Calendar cal = Calendar.getInstance();
             Formartter fmt= new Formartter();
             BudgetMaster budger= new BudgetMaster();
    int Value;
    ArrayList<String> data4, column1,data;
    String accountStatus, jTFuserId1, accountNumber,ledger_balance,ledger,today ,thistime,ledger1,account,accountNameTrue, accountNumberTrue;
    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
    List accountDetails;
       String userId;
    public Createinternalaccounts(String userId) {
        new Timer(1000, this).start();
       
        initComponents();
         column1= new ArrayList();
        column1.add(0, "CDate");
        column1.add(1, "Ledger Id");
        column1.add(2, "L Name");
        column1.add(3, "A/C Name");
        column1.add(4, "Balance");
        column1.add(5, "AC Status");

         jTable3.setVisible(true);
        jTable2.setVisible(false);
        jTable2.setAutoscrolls(true);
jTable2.setRowHeight(30);
jTable2.setGridColor(Color.gray);

jTable1.addMouseListener(this);
        approveDelletePanel.setVisible(false);
        mainAccountDisplayPanel.setVisible(true);
        
        ancdb.feelActiveAccounts(jTable3,column1);
         java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
      this.setTitle("ACCOUNT SETUP MODULE-"+dbq.title(userId)); 
//         int s=jTable2.getRowCount();
//         for(int i=1;i<=s;i++){
//        if(s%i==0){jTable2.setBackground(Color.red);}}
         jPanel3.setVisible(true);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       jTree1.addMouseListener( this);
        jTable2.addMouseListener(this);  
        this.userId=userId;
       
jTable1.setAutoscrolls(true);
jTable1.setRowHeight(30);
jTable1.setGridColor(Color.gray);

jTable1.addMouseListener(this);


jTable3.setAutoscrolls(true);
jTable3.setRowHeight(30);
jTable3.setGridColor(Color.gray);

jTable3.addMouseListener(this);
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

        staffPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jComboHomeParish1 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jComboHomeParish2 = new javax.swing.JComboBox();
        jButton7 = new javax.swing.JButton();
        jComboHomeParish3 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lastName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        lastName2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jComboMainCat = new javax.swing.JComboBox();
        jComboBoxSubCat3 = new javax.swing.JComboBox();
        jComboBoxSubCat2 = new javax.swing.JComboBox();
        jButton18 = new javax.swing.JButton();
        accountsNumber = new javax.swing.JTextField();
        lastName5 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        ledgerOpeningBalance = new javax.swing.JButton();
        ledgerBalance = new javax.swing.JButton();
        amountFieldDebit2 = new javax.swing.JFormattedTextField();
        creatLedger = new javax.swing.JButton();
        RejectLedgerCreation = new javax.swing.JButton();
        jComboBoxSubCat5 = new javax.swing.JComboBox();
        jComboBoxSubCat4 = new javax.swing.JComboBox();
        creatLedger1 = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jButton110 = new javax.swing.JButton();
        jLabel78 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jButton109 = new javax.swing.JButton();
        jCheckBox69 = new javax.swing.JCheckBox();
        jButton17 = new javax.swing.JButton();
        expenseSelector = new javax.swing.JComboBox<>();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        approveDelletePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField10 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        mainAccountDisplayPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField12 = new javax.swing.JTextField();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        budgetjPanel8 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jComboBox7 = new javax.swing.JComboBox<>();
        jCheckBox2 = new javax.swing.JCheckBox();
        jComboBox8 = new javax.swing.JComboBox<>();
        jButton25 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton22 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        amountCredit2 = new javax.swing.JFormattedTextField();
        jButton21 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton24 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jButton20 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        amountCredit3 = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jButton27 = new javax.swing.JButton();
        jCheckBox9 = new javax.swing.JCheckBox();
        jButton28 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        amountCredit4 = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        BudgetItemsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField9 = new javax.swing.JTextField();
        clock = new javax.swing.JFormattedTextField();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        staffPanel.setBackground(java.awt.SystemColor.activeCaption);
        staffPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), null));
        staffPanel.setForeground(new java.awt.Color(0, 255, 51));
        staffPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        staffPanel.setFocusCycleRoot(true);
        staffPanel.setPreferredSize(new java.awt.Dimension(900, 697));
        staffPanel.setLayout(null);

        jTree1.setBackground(java.awt.SystemColor.activeCaption);
        jTree1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Navigation tool");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Log Out");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                jTree1TreeExpanded(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        staffPanel.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 180, 430);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jPanel4.setBackground(java.awt.SystemColor.activeCaption);
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));
        jPanel4.setLayout(null);

        jButton10.setForeground(new java.awt.Color(0, 153, 153));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/DELETE LEDGER.png"))); // NOI18N
        jButton10.setText("DELETE LEDGER");
        jButton10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton10);
        jButton10.setBounds(370, 0, 220, 40);

        jButton12.setForeground(new java.awt.Color(0, 204, 51));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/APPROVE.jpg"))); // NOI18N
        jButton12.setText("APPROVE LEDGER");
        jButton12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12);
        jButton12.setBounds(170, 0, 200, 40);

        jButton13.setForeground(new java.awt.Color(0, 153, 153));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/LEDGER.jpg"))); // NOI18N
        jButton13.setText("CREATE LEDGER");
        jButton13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13);
        jButton13.setBounds(0, 0, 170, 40);

        jButton19.setForeground(new java.awt.Color(0, 153, 153));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/budget-icon.jpg"))); // NOI18N
        jButton19.setText("ASSIGN BUDGET PROJECTIONS");
        jButton19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton19);
        jButton19.setBounds(590, 0, 220, 40);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(0, 0, 820, 40);

        jPanel5.setBackground(new java.awt.Color(255, 204, 255));
        jPanel5.setLayout(null);

        jLabel1.setText("Emp Name");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(10, 11, 79, 27);

        jLabel2.setText("Emp Name");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(385, 238, 50, 14);

        jLabel3.setText("Emp Name");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(530, 218, 50, 14);

        jLabel4.setText("Emp Name");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(530, 198, 50, 14);

        jLabel5.setText("Emp Name");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(530, 178, 50, 14);

        jLabel6.setText("Emp Name");
        jPanel5.add(jLabel6);
        jLabel6.setBounds(530, 158, 50, 14);

        jLabel7.setText("Emp Name");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(530, 138, 50, 14);

        jLabel8.setText("Emp Name");
        jPanel5.add(jLabel8);
        jLabel8.setBounds(530, 118, 50, 14);

        jLabel9.setText("Emp Name");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(530, 98, 50, 14);

        jLabel10.setText("Emp Name");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(530, 78, 50, 14);

        jTextField1.setText("jTextField1");
        jPanel5.add(jTextField1);
        jTextField1.setBounds(283, 270, 59, 20);

        jTextField2.setText("jTextField1");
        jPanel5.add(jTextField2);
        jTextField2.setBounds(283, 212, 59, 20);

        jTextField3.setText("jTextField1");
        jPanel5.add(jTextField3);
        jTextField3.setBounds(283, 154, 59, 20);

        jTextField4.setText("jTextField1");
        jPanel5.add(jTextField4);
        jTextField4.setBounds(283, 96, 59, 20);

        jTextField5.setText("jTextField1");
        jPanel5.add(jTextField5);
        jTextField5.setBounds(478, 38, 59, 20);

        jTextField6.setText("jTextField1");
        jPanel5.add(jTextField6);
        jTextField6.setBounds(413, 38, 59, 20);

        jTextField7.setText("jTextField1");
        jPanel5.add(jTextField7);
        jTextField7.setBounds(348, 38, 59, 20);

        jTextField8.setText("jTextField1");
        jPanel5.add(jTextField8);
        jTextField8.setBounds(93, 14, 110, 20);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(0, 40, 0, 330);

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));
        jPanel3.setLayout(null);

        jButton9.setText("FIN. STAT.CAT");
        jPanel3.add(jButton9);
        jButton9.setBounds(0, 0, 122, 30);

        jComboHomeParish1.setForeground(new java.awt.Color(0, 0, 204));
        jComboHomeParish1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Balance Sheet", "Income Statement" }));
        jComboHomeParish1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboHomeParish1ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboHomeParish1);
        jComboHomeParish1.setBounds(128, 0, 110, 30);

        jButton3.setText("FIN.STAT.SUB CAT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(244, 0, 140, 30);

        jComboHomeParish2.setForeground(new java.awt.Color(0, 0, 204));
        jComboHomeParish2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboHomeParish2ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboHomeParish2);
        jComboHomeParish2.setBounds(390, 0, 138, 30);

        jButton7.setText("A/C TYPE");
        jPanel3.add(jButton7);
        jButton7.setBounds(534, 0, 110, 30);

        jComboHomeParish3.setForeground(new java.awt.Color(0, 0, 204));
        jComboHomeParish3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        jComboHomeParish3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboHomeParish3ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboHomeParish3);
        jComboHomeParish3.setBounds(650, 0, 144, 30);

        jButton2.setText("ACCOUNT NAME");
        jPanel3.add(jButton2);
        jButton2.setBounds(0, 49, 150, 23);

        jButton1.setText("ACCOUNT NUMBER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(0, 91, 150, 23);

        lastName.setForeground(new java.awt.Color(0, 153, 51));
        jPanel3.add(lastName);
        lastName.setBounds(160, 90, 230, 24);

        jTextArea1.setColumns(10);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Purpose of the account"));
        jScrollPane2.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(396, 48, 220, 80);

        jButton4.setText("SAVE NEW ACCOUNT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);
        jButton4.setBounds(622, 48, 170, 23);

        jButton6.setText("REJECT NEW ACCOUNT");
        jPanel3.add(jButton6);
        jButton6.setBounds(622, 89, 170, 23);

        jButton5.setText("DELETE EXISTING ACCOUNT");
        jPanel3.add(jButton5);
        jButton5.setBounds(622, 118, 169, 23);

        lastName2.setForeground(new java.awt.Color(0, 153, 51));
        jPanel3.add(lastName2);
        lastName2.setBounds(160, 48, 230, 24);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 40, 810, 0);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel11.setBackground(new java.awt.Color(0, 153, 153));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("LEDGER VERIFICATION");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(340, 20, 180, 30);

        jButton8.setForeground(new java.awt.Color(0, 204, 204));
        jButton8.setText("APPROVE LEDGER");
        jButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8);
        jButton8.setBounds(160, 80, 150, 30);
        jButton8.setVisible(false);

        jButton16.setForeground(new java.awt.Color(0, 204, 204));
        jButton16.setText("RETURN");
        jButton16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton16);
        jButton16.setBounds(460, 80, 150, 30);

        jButton11.setForeground(new java.awt.Color(0, 204, 204));
        jButton11.setText("DELETE LEDGER");
        jButton11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11);
        jButton11.setBounds(310, 80, 150, 30);
        jButton11 .setVisible(false);

        jButton15.setForeground(new java.awt.Color(0, 204, 204));
        jButton15.setText("REJECT LEDGER");
        jButton15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton15);
        jButton15.setBounds(310, 80, 150, 30);
        jButton15.setVisible(false);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 40, 820, 200);
        jPanel2.setVisible(false);
        jPanel2.getAccessibleContext().setAccessibleParent(jComboHomeParish2);

        jPanel13.setBackground(java.awt.SystemColor.activeCaption);
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setLayout(null);

        jButton14.setForeground(new java.awt.Color(0, 153, 51));
        jButton14.setText("Account Number");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton14);
        jButton14.setBounds(2, 110, 170, 30);

        jComboMainCat.setForeground(new java.awt.Color(0, 102, 51));
        jComboMainCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cat.1", "Debits", "Credits" }));
        jComboMainCat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboMainCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMainCatActionPerformed(evt);
            }
        });
        jPanel13.add(jComboMainCat);
        jComboMainCat.setBounds(0, 10, 110, 40);

        jComboBoxSubCat3.setForeground(new java.awt.Color(0, 102, 0));
        jComboBoxSubCat3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cat.3" }));
        jComboBoxSubCat3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBoxSubCat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSubCat3ActionPerformed(evt);
            }
        });
        jPanel13.add(jComboBoxSubCat3);
        jComboBoxSubCat3.setBounds(220, 10, 230, 40);
        jComboBoxSubCat3.setEnabled(false);

        jComboBoxSubCat2.setForeground(new java.awt.Color(0, 102, 0));
        jComboBoxSubCat2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cat.2" }));
        jComboBoxSubCat2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBoxSubCat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSubCat2ActionPerformed(evt);
            }
        });
        jPanel13.add(jComboBoxSubCat2);
        jComboBoxSubCat2.setBounds(110, 10, 110, 40);
        jComboBoxSubCat2.setEnabled(false);

        jButton18.setForeground(new java.awt.Color(0, 153, 0));
        jButton18.setText("Ledger Name");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton18);
        jButton18.setBounds(2, 50, 170, 30);

        accountsNumber.setForeground(new java.awt.Color(0, 153, 51));
        jPanel13.add(accountsNumber);
        accountsNumber.setBounds(180, 110, 210, 30);
        accountsNumber.setEditable(false);

        lastName5.setForeground(new java.awt.Color(0, 153, 51));
        jPanel13.add(lastName5);
        lastName5.setBounds(180, 50, 210, 30);

        jEditorPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Purpose"));
        jScrollPane3.setViewportView(jEditorPane1);

        jPanel13.add(jScrollPane3);
        jScrollPane3.setBounds(400, 50, 160, 90);

        ledgerOpeningBalance.setForeground(new java.awt.Color(0, 153, 0));
        ledgerOpeningBalance.setText("Ledger Opening Bal.");
        ledgerOpeningBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ledgerOpeningBalanceActionPerformed(evt);
            }
        });
        jPanel13.add(ledgerOpeningBalance);
        ledgerOpeningBalance.setBounds(2, 80, 170, 30);

        ledgerBalance.setForeground(new java.awt.Color(0, 0, 153));
        ledgerBalance.setText("Ledger Balance");
        ledgerBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ledgerBalanceActionPerformed(evt);
            }
        });
        jPanel13.add(ledgerBalance);
        ledgerBalance.setBounds(2, 80, 170, 30);

        amountFieldDebit2.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit2.setValue(0);
        amountFieldDebit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit2ActionPerformed(evt);
            }
        });
        jPanel13.add(amountFieldDebit2);
        amountFieldDebit2.setBounds(180, 80, 210, 30);
        amountFieldDebit2.setEditable(false);

        creatLedger.setForeground(new java.awt.Color(0, 153, 51));
        creatLedger.setText("Create Ledger");
        creatLedger.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        creatLedger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatLedgerActionPerformed(evt);
            }
        });
        jPanel13.add(creatLedger);
        creatLedger.setBounds(570, 80, 240, 30);
        creatLedger.setEnabled(false);

        RejectLedgerCreation.setForeground(new java.awt.Color(102, 0, 0));
        RejectLedgerCreation.setText("Cancel Ledger Creation");
        RejectLedgerCreation.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RejectLedgerCreation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejectLedgerCreationActionPerformed(evt);
            }
        });
        jPanel13.add(RejectLedgerCreation);
        RejectLedgerCreation.setBounds(570, 110, 240, 30);
        RejectLedgerCreation.setEnabled(false);

        jComboBoxSubCat5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cat.5" }));
        jComboBoxSubCat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSubCat5ActionPerformed(evt);
            }
        });
        jPanel13.add(jComboBoxSubCat5);
        jComboBoxSubCat5.setBounds(510, 10, 240, 40);
        jComboBoxSubCat5 .setEnabled(false);
        jComboBoxSubCat5 .setVisible(false);

        jComboBoxSubCat4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Cat.4" }));
        jComboBoxSubCat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSubCat4ActionPerformed(evt);
            }
        });
        jPanel13.add(jComboBoxSubCat4);
        jComboBoxSubCat4.setBounds(450, 10, 350, 40);
        jComboBoxSubCat4.setEnabled(false);

        creatLedger1.setForeground(new java.awt.Color(0, 0, 102));
        creatLedger1.setText("Return Selection");
        creatLedger1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        creatLedger1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatLedger1ActionPerformed(evt);
            }
        });
        jPanel13.add(creatLedger1);
        creatLedger1.setBounds(570, 50, 240, 30);

        jLabel83.setForeground(java.awt.SystemColor.control);
        jLabel83.setText("SET FINANCIAL YEAR");
        jLabel83.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.add(jLabel83);
        jLabel83.setBounds(10, 165, 130, 30);

        jLabel84.setForeground(java.awt.SystemColor.control);
        jLabel84.setText("START DATE");
        jLabel84.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.add(jLabel84);
        jLabel84.setBounds(148, 165, 90, 30);
        jPanel13.add(jTextField52);
        jTextField52.setBounds(245, 165, 90, 30);
        fios.forceFileExistance(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"));
        jTextField52 .setText(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt")));

        jButton110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton110ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton110);
        jButton110.setBounds(335, 165, 20, 30);

        jLabel78.setForeground(java.awt.SystemColor.control);
        jLabel78.setText("END DATE");
        jLabel78.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.add(jLabel78);
        jLabel78.setBounds(360, 165, 70, 30);
        jPanel13.add(jTextField51);
        jTextField51.setBounds(435, 165, 90, 30);
        fios.forceFileExistance(fios.createFileName("persistence", "operatingCycle", "YEARendDate.txt"));
        jTextField51 .setText(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARendDate.txt")));

        jButton109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton109ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton109);
        jButton109.setBounds(525, 165, 20, 30);

        jCheckBox69.setText("Check To Use");
        jCheckBox69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox69ActionPerformed(evt);
            }
        });
        jPanel13.add(jCheckBox69);
        jCheckBox69.setBounds(550, 165, 130, 30);

        jButton17.setText("Return");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton17);
        jButton17.setBounds(690, 165, 90, 30);

        expenseSelector.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        expenseSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseSelectorActionPerformed(evt);
            }
        });
        jPanel13.add(expenseSelector);
        expenseSelector.setBounds(2, 140, 170, 22);
        expenseSelector.setVisible(false);

        jButton58.setText("Operating Expenses");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton58);
        jButton58.setBounds(180, 140, 129, 23);

        jButton59.setText("Main Revenue");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton59);
        jButton59.setBounds(450, 140, 140, 23);

        jButton60.setText("Employment Expenses");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton60);
        jButton60.setBounds(310, 140, 140, 23);

        jPanel1.add(jPanel13);
        jPanel13.setBounds(0, 40, 820, 200);
        jPanel13.setVisible(false);

        approveDelletePanel.setBackground(java.awt.SystemColor.activeCaption);
        approveDelletePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        approveDelletePanel.setLayout(null);

        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable2.setForeground(new java.awt.Color(0, 102, 51));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable2);
        jTable2.setVisible(false);

        approveDelletePanel.add(jScrollPane4);
        jScrollPane4.setBounds(10, 10, 800, 360);
        approveDelletePanel.add(jTextField10);
        jTextField10.setBounds(460, 370, 350, 40);
        jTextField10.setVisible(false);

        jButton37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton37.setText("Export Excel");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        approveDelletePanel.add(jButton37);
        jButton37.setBounds(90, 370, 130, 40);

        jButton49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton49.setText("Print");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        approveDelletePanel.add(jButton49);
        jButton49.setBounds(10, 370, 80, 40);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Search");
        approveDelletePanel.add(jLabel12);
        jLabel12.setBounds(330, 370, 130, 40);

        jPanel1.add(approveDelletePanel);
        approveDelletePanel.setBounds(0, 240, 820, 408);

        mainAccountDisplayPanel.setBackground(java.awt.SystemColor.activeCaption);
        mainAccountDisplayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainAccountDisplayPanel.setLayout(null);

        jTable3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable3.setForeground(new java.awt.Color(0, 102, 51));
        jScrollPane5.setViewportView(jTable3);
        jTable2.setVisible(false);

        mainAccountDisplayPanel.add(jScrollPane5);
        jScrollPane5.setBounds(10, 10, 800, 360);
        mainAccountDisplayPanel.add(jTextField12);
        jTextField12.setBounds(500, 370, 310, 40);

        jButton50.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton50.setText("Export Excel");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });
        mainAccountDisplayPanel.add(jButton50);
        jButton50.setBounds(120, 370, 120, 40);

        jButton51.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton51.setText("Print");
        jButton51.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        mainAccountDisplayPanel.add(jButton51);
        jButton51.setBounds(10, 370, 110, 40);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Search");
        mainAccountDisplayPanel.add(jLabel13);
        jLabel13.setBounds(360, 370, 100, 40);

        jPanel1.add(mainAccountDisplayPanel);
        mainAccountDisplayPanel.setBounds(0, 240, 820, 410);

        budgetjPanel8.setBackground(java.awt.SystemColor.activeCaption);
        budgetjPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        budgetjPanel8.setLayout(null);

        jTabbedPane1.setBackground(java.awt.SystemColor.activeCaption);
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel8.setBackground(java.awt.SystemColor.activeCaption);
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(null);

        jPanel11.setBackground(java.awt.SystemColor.activeCaption);
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setForeground(java.awt.SystemColor.control);
        jPanel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel11.setLayout(null);

        jLabel21.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel21.setText("Entitywide Operating Cycle");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.add(jLabel21);
        jLabel21.setBounds(140, 10, 160, 30);

        jLabel22.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel22.setText("Starting Month");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.add(jLabel22);
        jLabel22.setBounds(10, 50, 120, 30);
        jPanel11.add(jTextField15);
        jTextField15.setBounds(140, 50, 140, 30);
        jTextField15.setEditable(false);

        jLabel14.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel14.setText("Ending Month");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.add(jLabel14);
        jLabel14.setBounds(10, 90, 120, 30);
        jPanel11.add(jTextField13);
        jTextField13.setBounds(140, 90, 140, 30);
        jTextField13.setEditable(false);

        jComboBox7.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        jPanel11.add(jComboBox7);
        jComboBox7.setBounds(290, 90, 120, 30);

        jCheckBox2.setText("USE");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel11.add(jCheckBox2);
        jCheckBox2.setBounds(140, 130, 140, 30);

        jComboBox8.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "Augus", "September", "October", "November", "December" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        jPanel11.add(jComboBox8);
        jComboBox8.setBounds(290, 50, 120, 30);

        jButton25.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton25.setText("RETURN");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton25);
        jButton25.setBounds(290, 130, 120, 30);

        jPanel8.add(jPanel11);
        jPanel11.setBounds(0, 0, 440, 170);

        jPanel12.setBackground(java.awt.SystemColor.activeCaption);
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setLayout(null);

        jLabel19.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel19.setText("Budget Effective Period");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.add(jLabel19);
        jLabel19.setBounds(80, 10, 150, 30);
        jPanel12.add(jTextField14);
        jTextField14.setBounds(120, 60, 120, 30);
        jTextField14.setEditable(false);

        jLabel20.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel20.setText("Year");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.add(jLabel20);
        jLabel20.setBounds(10, 100, 100, 30);

        jCheckBox1.setText("USE");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel12.add(jCheckBox1);
        jCheckBox1.setBounds(250, 100, 70, 30);

        jLabel23.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel23.setText("Month");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.add(jLabel23);
        jLabel23.setBounds(10, 60, 100, 30);
        jPanel12.add(jTextField11);
        jTextField11.setBounds(120, 100, 120, 30);

        jComboBox5.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel12.add(jComboBox5);
        jComboBox5.setBounds(250, 60, 110, 30);

        jButton22.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton22.setText("RETURN");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton22);
        jButton22.setBounds(120, 135, 120, 30);

        jPanel8.add(jPanel12);
        jPanel12.setBounds(440, 0, 370, 170);

        jTabbedPane1.addTab("Set Budget Constant Variables", jPanel8);

        jPanel9.setBackground(java.awt.SystemColor.activeCaption);
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(null);

        jComboBox4.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Expense Account", "Revenue Account" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel9.add(jComboBox4);
        jComboBox4.setBounds(120, 10, 160, 30);

        jLabel18.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel18.setText("Annual Budget Estimate");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel18);
        jLabel18.setBounds(530, 10, 140, 30);

        jLabel16.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel16.setText("Account Category");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel16);
        jLabel16.setBounds(10, 10, 110, 30);

        jComboBox3.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel9.add(jComboBox3);
        jComboBox3.setBounds(350, 10, 180, 30);

        amountCredit2.setForeground(new java.awt.Color(0, 102, 51));
        amountCredit2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountCredit2.setValue(null);
        amountCredit2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountCredit2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                amountCredit2MouseEntered(evt);
            }
        });
        amountCredit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountCredit2ActionPerformed(evt);
            }
        });
        jPanel9.add(amountCredit2);
        amountCredit2.setBounds(670, 10, 120, 30);
        amountCredit2.setEditable(false);

        jButton21.setText("Apply");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton21);
        jButton21.setBounds(360, 80, 120, 40);

        jButton23.setText("Cancel");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton23);
        jButton23.setBounds(240, 80, 120, 40);

        jLabel24.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel24.setText("Account");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel24);
        jLabel24.setBounds(280, 10, 70, 30);

        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel9.add(jCheckBox4);
        jCheckBox4.setBounds(790, 15, 20, 21);

        jButton24.setText("RETURN");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton24);
        jButton24.setBounds(300, 115, 120, 40);

        jTabbedPane1.addTab("Create Budget Item", jPanel9);

        jPanel10.setBackground(java.awt.SystemColor.activeCaption);
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(null);

        jPanel14.setBackground(java.awt.SystemColor.activeCaption);
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel14.setLayout(null);

        jCheckBox3.setText("Apply Monthly Growth Rate");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel14.add(jCheckBox3);
        jCheckBox3.setBounds(0, 0, 190, 30);

        jLabel17.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel17.setText("Budget %  Mothly Growth Rate");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel17);
        jLabel17.setBounds(10, 50, 190, 30);
        jLabel17.setVisible(false);
        jPanel14.add(jTextField16);
        jTextField16.setBounds(200, 50, 50, 30);
        jTextField16.setVisible(false);

        jLabel15.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel15.setText("Start Year");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel15);
        jLabel15.setBounds(10, 140, 120, 30);
        jLabel15.setVisible(false);

        jComboBox6.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jPanel14.add(jComboBox6);
        jComboBox6.setBounds(130, 140, 120, 30);
        jComboBox6.setVisible(false);

        jComboBox11.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        jPanel14.add(jComboBox11);
        jComboBox11.setBounds(80, 80, 170, 30);
        jComboBox11.setVisible(false);

        jLabel25.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel25.setText("Account");
        jLabel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel25);
        jLabel25.setBounds(10, 80, 70, 30);
        jLabel25.setVisible(false);

        jButton29.setText("Return");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton29);
        jButton29.setBounds(260, 50, 120, 40);
        jButton29.setVisible(false);

        jLabel31.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel31.setText("Start Month");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel31);
        jLabel31.setBounds(10, 110, 120, 30);
        jLabel31.setVisible(false);

        jComboBox13.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox13.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jComboBox13ComponentAdded(evt);
            }
        });
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        jPanel14.add(jComboBox13);
        jComboBox13.setBounds(130, 110, 120, 30);
        jComboBox13.setVisible(false);

        jButton20.setText("Cancel");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton20);
        jButton20.setBounds(260, 90, 120, 40);
        jButton20.setVisible(false);

        jButton32.setText("Apply");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton32);
        jButton32.setBounds(260, 130, 120, 40);
        jButton32.setVisible(false);

        jPanel10.add(jPanel14);
        jPanel14.setBounds(0, 0, 400, 170);

        jPanel15.setBackground(java.awt.SystemColor.activeCaption);
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(null);

        jCheckBox7.setText("Apply Monthly Changes");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });
        jPanel15.add(jCheckBox7);
        jCheckBox7.setBounds(0, 0, 180, 30);
        jCheckBox7.setVisible(false);

        jPanel17.setBackground(java.awt.SystemColor.activeCaption);
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel17.setLayout(null);

        amountCredit3.setForeground(new java.awt.Color(0, 102, 51));
        amountCredit3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountCredit3.setValue(null);
        amountCredit3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountCredit3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                amountCredit3MouseEntered(evt);
            }
        });
        amountCredit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountCredit3ActionPerformed(evt);
            }
        });
        jPanel17.add(amountCredit3);
        amountCredit3.setBounds(100, 90, 120, 30);

        jLabel27.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel27.setText("Budget Estimate");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.add(jLabel27);
        jLabel27.setBounds(0, 90, 100, 30);

        jLabel26.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel26.setText("Affected Account");
        jLabel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.add(jLabel26);
        jLabel26.setBounds(0, 0, 100, 30);

        jComboBox9.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        jPanel17.add(jComboBox9);
        jComboBox9.setBounds(100, 0, 160, 30);

        jLabel28.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel28.setText("Affected Year");
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.add(jLabel28);
        jLabel28.setBounds(0, 60, 140, 30);

        jComboBox10.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        jPanel17.add(jComboBox10);
        jComboBox10.setBounds(140, 60, 120, 30);

        jLabel34.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel34.setText("Affected Month");
        jLabel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.add(jLabel34);
        jLabel34.setBounds(0, 30, 140, 30);

        jComboBox16.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        jPanel17.add(jComboBox16);
        jComboBox16.setBounds(140, 30, 120, 30);

        jButton27.setText("Return");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton27);
        jButton27.setBounds(260, 0, 120, 30);

        jCheckBox9.setText("Use As A Monthly Estimate");
        jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox9ActionPerformed(evt);
            }
        });
        jPanel17.add(jCheckBox9);
        jCheckBox9.setBounds(0, 120, 180, 20);

        jButton28.setBackground(java.awt.SystemColor.activeCaption);
        jButton28.setForeground(new java.awt.Color(0, 204, 102));
        jButton28.setText("Apply");
        jButton28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton28);
        jButton28.setBounds(260, 60, 120, 30);

        jButton26.setText("Cancel");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton26);
        jButton26.setBounds(260, 30, 120, 30);

        jCheckBox10.setText("Use");
        jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox10ActionPerformed(evt);
            }
        });
        jPanel17.add(jCheckBox10);
        jCheckBox10.setBounds(220, 90, 70, 30);

        jPanel15.add(jPanel17);
        jPanel17.setBounds(0, 30, 410, 140);
        jPanel17.setVisible(false);

        jCheckBox8.setText("Adjust Budget Item");
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });
        jPanel15.add(jCheckBox8);
        jCheckBox8.setBounds(0, 0, 180, 30);

        jCheckBox5.setText("Apply Annual Changes");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel15.add(jCheckBox5);
        jCheckBox5.setBounds(180, 0, 230, 30);
        jCheckBox5.setVisible(false);

        jPanel16.setBackground(java.awt.SystemColor.activeCaption);
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.setLayout(null);

        amountCredit4.setForeground(new java.awt.Color(0, 102, 51));
        amountCredit4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountCredit4.setValue(null);
        amountCredit4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountCredit4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                amountCredit4MouseEntered(evt);
            }
        });
        amountCredit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountCredit4ActionPerformed(evt);
            }
        });
        jPanel16.add(amountCredit4);
        amountCredit4.setBounds(140, 40, 120, 30);

        jLabel29.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel29.setText("Budget Estimate");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.add(jLabel29);
        jLabel29.setBounds(0, 40, 140, 30);

        jLabel30.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel30.setText("Affected Account");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.add(jLabel30);
        jLabel30.setBounds(10, 10, 100, 30);

        jComboBox12.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        jPanel16.add(jComboBox12);
        jComboBox12.setBounds(110, 10, 150, 30);

        jLabel32.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel32.setText("Affected Year");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.add(jLabel32);
        jLabel32.setBounds(0, 100, 140, 30);

        jComboBox14.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        jPanel16.add(jComboBox14);
        jComboBox14.setBounds(140, 100, 120, 30);

        jLabel33.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel33.setText("Affected Month");
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.add(jLabel33);
        jLabel33.setBounds(0, 70, 140, 30);

        jComboBox15.setForeground(new java.awt.Color(0, 153, 102));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        jPanel16.add(jComboBox15);
        jComboBox15.setBounds(140, 70, 120, 30);

        jButton30.setText("Cancel");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton30);
        jButton30.setBounds(260, 50, 120, 40);

        jButton31.setText("Return");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton31);
        jButton31.setBounds(260, 10, 120, 40);

        jButton33.setText("Apply");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton33);
        jButton33.setBounds(260, 90, 120, 40);

        jPanel15.add(jPanel16);
        jPanel16.setBounds(0, 30, 410, 140);
        jPanel16.setVisible(false);

        jPanel10.add(jPanel15);
        jPanel15.setBounds(400, 0, 410, 170);

        jTabbedPane1.addTab("Update Budget Item", jPanel10);

        budgetjPanel8.add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 820, 200);

        jPanel1.add(budgetjPanel8);
        budgetjPanel8.setBounds(0, 40, 820, 200);
        budgetjPanel8.setVisible(false);

        BudgetItemsPanel.setBackground(java.awt.SystemColor.activeCaption);
        BudgetItemsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BudgetItemsPanel.setLayout(null);

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable1);

        BudgetItemsPanel.add(jScrollPane6);
        jScrollPane6.setBounds(10, 10, 800, 360);
        BudgetItemsPanel.add(jTextField9);
        jTextField9.setBounds(20, 370, 330, 40);
        jTextField9.setVisible(false);

        jPanel1.add(BudgetItemsPanel);
        BudgetItemsPanel.setBounds(-10, 240, 830, 410);

        staffPanel.add(jPanel1);
        jPanel1.setBounds(180, 0, 820, 650);

        clock.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        clock.setForeground(new java.awt.Color(0, 153, 51));
        clock.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        clock.setCaretColor(new java.awt.Color(0, 153, 51));
        clock.setMaximumSize(new java.awt.Dimension(150, 20));
        clock.setPreferredSize(new java.awt.Dimension(150, 20));
        clock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clockActionPerformed(evt);
            }
        });
        staffPanel.add(clock);
        clock.setBounds(0, 430, 180, 220);
        clock .setEditable(false);

        getContentPane().add(staffPanel);
        staffPanel.setBounds(0, 0, 1000, 652);

        jButton34.setBackground(new java.awt.Color(51, 0, 51));
        jButton34.setText("jButton5");
        getContentPane().add(jButton34);
        jButton34.setBounds(1430, 460, 73, 23);

        jButton35.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton35.setText("jButton5");
        getContentPane().add(jButton35);
        jButton35.setBounds(1420, 390, 73, 23);

        jButton36.setText("jButton8");
        getContentPane().add(jButton36);
        jButton36.setBounds(1550, 390, 73, 23);

        jButton38.setBackground(new java.awt.Color(152, 198, 94));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(1600, 330, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 255, 204));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(1590, 300, 53, 23);

        jButton40.setBackground(new java.awt.Color(0, 153, 255));
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(1600, 280, 53, 23);

        jButton41.setBackground(new java.awt.Color(204, 165, 165));
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(1600, 250, 53, 23);

        jButton42.setBackground(new java.awt.Color(204, 204, 204));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(1600, 220, 53, 23);

        jButton43.setBackground(java.awt.SystemColor.activeCaption);
        jButton43.setText("jButton5");
        getContentPane().add(jButton43);
        jButton43.setBounds(1460, 310, 73, 23);

        jButton44.setBackground(new java.awt.Color(204, 204, 255));
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(1410, 290, 53, 23);

        jButton45.setBackground(new java.awt.Color(201, 222, 223));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1380, 320, 53, 23);

        jButton46.setBackground(java.awt.SystemColor.activeCaption);
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1350, 280, 53, 23);

        jButton47.setBackground(new java.awt.Color(255, 255, 204));
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1350, 250, 53, 23);

        jButton48.setBackground(new java.awt.Color(0, 204, 255));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1370, 230, 53, 23);

        jButton52.setBackground(new java.awt.Color(0, 204, 102));
        jButton52.setText("Blue");
        getContentPane().add(jButton52);
        jButton52.setBounds(1450, 220, 53, 23);

        jButton53.setBackground(new java.awt.Color(204, 204, 0));
        jButton53.setText("Blue");
        getContentPane().add(jButton53);
        jButton53.setBounds(1520, 280, 53, 23);

        jButton54.setBackground(new java.awt.Color(0, 204, 204));
        jButton54.setText("Blue");
        getContentPane().add(jButton54);
        jButton54.setBounds(1520, 240, 53, 23);

        jButton56.setBackground(java.awt.SystemColor.activeCaption);
        jButton56.setText("Blue");
        getContentPane().add(jButton56);
        jButton56.setBounds(1510, 190, 53, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1MouseClicked

    private void jTree1TreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_jTree1TreeExpanded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1TreeExpanded

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTree1ValueChanged

    private void jComboHomeParish1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboHomeParish1ActionPerformed
       
    }//GEN-LAST:event_jComboHomeParish1ActionPerformed

    private void jComboHomeParish3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboHomeParish3ActionPerformed
       
    }//GEN-LAST:event_jComboHomeParish3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboHomeParish2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboHomeParish2ActionPerformed
     
    }//GEN-LAST:event_jComboHomeParish2ActionPerformed

    private void jComboMainCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMainCatActionPerformed
  
                accountDetails=new ArrayList<>();
 accountDetails.add(0, jComboMainCat.getSelectedItem().toString()); //Account cat 1:0
        
        jComboMainCat.setEnabled(false); 
        jComboBoxSubCat2.setEnabled(true); 
        String selection = jComboMainCat.getSelectedItem().toString();
       switch(selection){
           case "Debits":
           data4 = new ArrayList<>();
             data4.add("Assets");
           data4.add("Expenses");
                     modelcombo = new MyComboBoxModel(data4);
            jComboBoxSubCat2.setModel(modelcombo);    
       break;
           case "Credits":
           data4 = new ArrayList<>();
           data4.add("Revenues");
           data4.add("Liabilities");
            data4.add("Equity/Capital");
             modelcombo = new MyComboBoxModel(data4);
           jComboBoxSubCat2.setModel(modelcombo); 
 break;
       }                  

    }//GEN-LAST:event_jComboMainCatActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_jButton4ActionPerformed

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        jButton8.setVisible(true);
           jButton11.setVisible(false);
           jButton15.setVisible(true);
        column1= new ArrayList();
         column1.add(0, "CDate");
         column1.add(1, "AC Number");
         column1.add(2, "AC Name");
         column1.add(3, "AC Cat5");
         column1.add(4, "Balance");
         column1.add(5, "AC Status");
         column1.add(6, "Select");
         jTable3.setVisible(false);
        jTable2.setVisible(true);
        approveDelletePanel.setVisible(true);
        mainAccountDisplayPanel.setVisible(false);
        BudgetItemsPanel.setVisible(false);
     ancdb.feelInactiveAccounts(jTable2,column1);   
        jTable2.repaint();
        jPanel2.setVisible(true);
     jPanel13.setVisible(false);
      jButton13.setEnabled(false);
      jButton10.setEnabled(false);
       jButton19.setEnabled(false);
              jTextField10.setVisible(true);
       HeaderRenderer header = new HeaderRenderer(jTable2.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton9.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton46.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton47.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton28.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable2,jTextField10);   

        int n=0;

jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable2.getColumnModel().getColumnCount()){
        jTable2.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        if(n==2){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==3){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==4){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
        if(n==5){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(80);
        }
        n++;

        }
        jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
        if(col==4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
        
        
        
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
 jTable2.setVisible(false);
        jPanel13.setVisible(true);
     jPanel2.setVisible(false);
     jButton12.setEnabled(false);
     jButton10.setEnabled(false);
     jButton19.setEnabled(false);
  
    }//GEN-LAST:event_jButton13ActionPerformed

    private void clockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clockActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jComboBoxSubCat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSubCat3ActionPerformed

         accountDetails.add(2, jComboBoxSubCat3.getSelectedItem().toString());//Account cat 3:2
        
        jComboBoxSubCat3.setEnabled(false);
     jComboBoxSubCat4.setEnabled(true); 
        String selection1 =jComboBoxSubCat3.getSelectedItem().toString();
        switch(selection1){
            case "Fixed Assets&Investments":
            data = new ArrayList<>();
            data .add("Fixed Assets/Non-Current Assets");
            data .add("Investments");

            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
          
            case "Current Assets, Loans And Advances":
            data = new ArrayList<>();
            data .add("Inventories");
            data .add("Accounts Receivables/Debtors");
             data .add("Cash And Bank Balance");
            data .add("Loans And Advances");
           
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat4.setModel(modelcombo1);
            break; 
            
             case "Deferred Taxes Assets":
            data = new ArrayList<>();
            data .add("Deferred Tax Assets");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
    

            case "Current Liabilities & Provisions":
            data  = new ArrayList<>();
            data .add("Current Liabilities");
            data .add("Provisions");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
            case "Non-Current Liabilities/Long Term Liabilities":
            data  = new ArrayList<>();
            data .add("Secured Liabilities");
            data .add("Unsecured Liabilities");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
             case "Deferred Taxes Liabilities":
            data  = new ArrayList<>();
            data .add("Deferred Tax Liabilities");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
  
             case "Equity":
            data  = new ArrayList<>();
            data .add("Share Capital");
            data .add("Reserves And Surplus");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;    
   
            case "Revenue/Income":
            data  = new ArrayList<>();
            data .add("Main Income");
            data .add("Other Income");
            data .add("Income From Short Term Insurance Business");
            modelcombo1 = new MyComboBoxModel(data );
           jComboBoxSubCat4.setModel(modelcombo1);
            break;
         
            case "Cost Of Operation":
            data  = new ArrayList<>();
            data .add("Opening Stock");
            data .add("Purchases");
            data .add("Other Direct Costs");
            data .add("Factory Overheads");
            data .add("Closing Stock");
       
          
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
            case "Operating Expenses":
            data  = new ArrayList<>();
            data .add("Operating Expense");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
                 case "Administrative Expenses":
            data  = new ArrayList<>();
            data .add("Administrative Expense");
            data .add("Employment Expense");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
 
            case "Financing Expenses":
            data  = new ArrayList<>();
            data .add("Financing Expense");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
                           case "Expenses Purely Related To Short Term Insurance Business Income":
            data  = new ArrayList<>();
            data .add("Expense Purely Related To Short Term Insurance Business Income");
            
           
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
                           case "General Expenses":
            data  = new ArrayList<>();
             data .add("General Expensee");
             
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
     case "Income Tax Expenses"://now i know
            data  = new ArrayList<>();
            data .add("Income Taxes Expense");
            
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
                                     case "Dividends paid during the period":
            data  = new ArrayList<>();
            data .add("Dividend paid during the period");
            
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
            
//               case "Restatement of retained earnings for corrections of prior period errors":
//            data  = new ArrayList<>();
//            data .add("Restatement of retained earning for corrections of prior period errors");
//            
//            modelcombo1 = new MyComboBoxModel(data );
//            jComboBoxSubCat4.setModel(modelcombo1);
//            break;
//               case "Restatement of retained earnings for changes in accounting policy":
//            data  = new ArrayList<>();
//            data .add("Restatement of retained earning for changes in accounting policy");
//            
//            modelcombo1 = new MyComboBoxModel(data );
//            jComboBoxSubCat4.setModel(modelcombo1);
//            break;
            case "Retained earnings at start of period":
            data  = new ArrayList<>();
         data .add("Retained earning at start of period");
   modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat4.setModel(modelcombo1);
            break;
            
           
    }

    }//GEN-LAST:event_jComboBoxSubCat3ActionPerformed

    private void jComboBoxSubCat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSubCat2ActionPerformed

        
      accountDetails.add(1, jComboBoxSubCat2.getSelectedItem().toString()); //Account cat 2:1
        jComboBoxSubCat2.setEnabled(false);
           jComboBoxSubCat3.setEnabled(true);  
  

        String selection1 =jComboBoxSubCat2.getSelectedItem().toString();
        switch(selection1){
            case "Assets":
            data = new ArrayList<>();
            data .add("Fixed Assets&Investments");
            data .add("Current Assets, Loans And Advances");
            data .add("Deferred Taxes Assets");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat3.setModel(modelcombo1);
            break;
              

  
            case "Liabilities":
            data  = new ArrayList<>();
            data .add("Current Liabilities & Provisions");
            data .add("Non-Current Liabilities/Long Term Liabilities");
            data .add("Deferred Taxes Liabilities");

            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat3.setModel(modelcombo1);
            break;
             case "Equity/Capital":
            data  = new ArrayList<>();
            data .add("Equity");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat3.setModel(modelcombo1);
            break;    
        
            case "Revenues":
            data  = new ArrayList<>();
            data .add("Revenue/Income");
            
            modelcombo1 = new MyComboBoxModel(data );
           jComboBoxSubCat3.setModel(modelcombo1);
            break;
   
            case "Expenses":
              
            data  = new ArrayList<>();
            data .add("Cost Of Operation");
            data .add("Operating Expenses");
            data .add("Administrative Expenses");
            data .add("Financing Expenses");
      data .add("Expenses Purely Related To Short Term Insurance Business Income");
            data .add("Income Tax Expenses");
            data .add("Dividends paid during the period");
//            data .add("Restatement of retained earnings for corrections of prior period errors");
//            data .add("Restatement of retained earnings for changes in accounting policy");
            data .add("Retained earnings at start of period");
            data .add("General Expenses");
          
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat3.setModel(modelcombo1);
            break;}
        
    }//GEN-LAST:event_jComboBoxSubCat2ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void creatLedgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatLedgerActionPerformed

        if(jComboMainCat.isEnabled()==true){JOptionPane.showMessageDialog(rootPane, "Please select the MAIN ledger category");}
 else if(jComboBoxSubCat2.isEnabled()==true){ JOptionPane.showMessageDialog(rootPane, "Please select the SUB ledger category");}
 else if(jComboBoxSubCat3.isEnabled()==true){JOptionPane.showMessageDialog(rootPane, "Please select the ledger category");} else if(jComboBoxSubCat4.isEnabled()==true){JOptionPane.showMessageDialog(rootPane, "Please select the ledger category");} else if(jComboBoxSubCat5.isEnabled()==true){JOptionPane.showMessageDialog(rootPane, "Please select the ledger category");}
   else     if ("".equals(jEditorPane1.getText())|"".equals(lastName5.getText())|"".equalsIgnoreCase(amountFieldDebit2.getValue().toString())){
         JOptionPane.showMessageDialog(this, "You need to put ledger Name & Opening balance & Purpose before creating the ledger");
         
         
         } else{  
       
    accountDetails.add(5, accountsNumber.getText()); //Account Number:5 
    
     accountDetails.add(6, amountFieldDebit2.getValue().toString());   //Account balance:6 
     
       accountDetails.add(7,  lastName5.getText());  //Account Name:7
       
       accountDetails.add(8,  jEditorPane1.getText()); //Account Purpose:8
       
       accountDetails.add(9, fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt")));//User Id:9
       
       accountDetails.add(8,  "10"); //Product Code:10
       
       accountDetails.add(8,  jComboBoxSubCat5.getSelectedItem().toString()); //Product Name:11
       
     ancdb.createAccount(accountDetails);
  
//      dbq.updateMaster(amountFieldDebit2.getValue().toString(),accountsNumber.getText(),accountDetails.get(4).toString(),sdf.fmt(new Date(System.currentTimeMillis()))); 
       
    JOptionPane.showMessageDialog(this, "Ledger successfully created!!");
        Createinternalaccounts mtfo15 = new Createinternalaccounts(userId);
        mtfo15.setVisible(true);
        Dimension mtscreen = Toolkit.getDefaultToolkit().getScreenSize();
        mtfo15.setSize(mtscreen.getSize());
        mtfo15.pack();
//        mtfo15.setUserID(userId);
        this.dispose();    
  
       }       
        
    }//GEN-LAST:event_creatLedgerActionPerformed

    private void RejectLedgerCreationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejectLedgerCreationActionPerformed
    
        Createinternalaccounts mtfo15 = new Createinternalaccounts(userId);
        mtfo15.setVisible(true);
        Dimension mtscreen = Toolkit.getDefaultToolkit().getScreenSize();
        mtfo15.setSize(mtscreen.getSize());
        mtfo15.pack();
//        mtfo15.setUserID(userId);
        this.dispose();      
         
    }//GEN-LAST:event_RejectLedgerCreationActionPerformed

    private void ledgerOpeningBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ledgerOpeningBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ledgerOpeningBalanceActionPerformed
       
    private void ledgerBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ledgerBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ledgerBalanceActionPerformed

    private void amountFieldDebit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit2ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
String accNu="";
        
//        String accNu= fios.stringFileReader(fios.createFileName("accountManagement", "createAccounts", "accountActivate"));
//     if(accNu.equalsIgnoreCase("Approved")){
//     JOptionPane.showMessageDialog(this, "PLEASE FIRST SELECT THE LEDGER TO APPROVE");
//     return;
//     
//     }else
//         List<List> waveDetailsc1=new ArrayList();
       
 ListDataModel_1111 original = (ListDataModel_1111)jTable2.getModel();
 
List theLedgersToApprove=new ArrayList();


if(original.getRowCount()==0){
    
    JOptionPane.showMessageDialog(this, "There are no ledgers to Approve!!");
return;
}else{

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    

 if((boolean)(original.getValueAt(targetRow, 6))){
    accNu=  original.getValueAt(jTable2.convertRowIndexToModel(targetRow), 1).toString();
    theLedgersToApprove.add(accNu);
     
  
     } 
}

if(theLedgersToApprove.isEmpty()){
JOptionPane.showMessageDialog(this, "Please first select the ledgers to Approve!!!!");
return;
}else{
    for(Object theLedger:theLedgersToApprove){
    
         if(!ancdb.testTable("bsanca"+theLedger.toString())){
              ancdb.activateAccount(theLedger.toString(),this);
//         ancdb.createTriggerAccount(theLedger.toString());
        ancdb.updateStatus(theLedger.toString());
      
     }else{
      
      JOptionPane.showMessageDialog(this, dbq.AccountName(theLedger.toString())+"'s LEDGER IS ALREADY ACTIVE");
        
     }
    
    }


}


column1= new ArrayList();
         column1.add(0, "CDate");
         column1.add(1, "AC Number");
         column1.add(2, "AC Name");
         column1.add(3, "AC Cat5");
         column1.add(4, "Balance");
         column1.add(5, "AC Status");
          column1.add(6, "Select");
        jTable2.setVisible(true);
         fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        JOptionPane.showMessageDialog(this, "All the selected ledgers were successfully Approved!!!");
     ancdb.feelInactiveAccounts(jTable2,column1); 
        

}
         
        
        
        
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBoxSubCat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSubCat4ActionPerformed

        accountDetails.add(3, jComboBoxSubCat4.getSelectedItem().toString());//Account cat 4:3
        
        jComboBoxSubCat4.setEnabled(false);
     jComboBoxSubCat5.setEnabled(true); 
      jComboBoxSubCat5.setVisible(true); 
        String selection1 =jComboBoxSubCat4.getSelectedItem().toString();
        switch(selection1){
  
        case "Fixed Assets/Non-Current Assets":
        data = new ArrayList<>();
        data .add("Land & Building");
        data .add("Office Equipment");
        data .add("Motor Vehicles");
        data .add("Furniture, Fixtures & Fittings");
        data .add("Computers And Accessories");
        data .add("Other Fixed Assets");
        data .add("Intangible Assets");
        data .add("Accumulated Depreciation/Amortization");
        data .add("Computer Software And Hardware");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
          
            case "Investments":
            data = new ArrayList<>();
            data .add("Shares");
            data .add("Debentures");
             data .add("Fixed Deposits");
            data .add("Government Securities");
             data .add("Other Investments");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;    
    
            case "Inventories":
            data = new ArrayList<>();
            data .add("Stores/Consumables Including Packing Materials");
            data .add("Raw Materials");
             data .add("Work-In-Progress");
            data .add("Finished Goods Or Trade Goods");
             
  
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;    
       case "Accounts Receivables/Debtors":

            data = new ArrayList<>();
            data .add("Trade Receivables");
            data .add("Debtors");
             data .add("Interest Receivable");
            data .add("Prepayments");
            data .add("Mobile Money");
            data .add("Accounts Receivable");
            data .add("Allowance for Doubtful Accounts");
            data .add("Allowance for Doubtful Interest");
             data .add("Accumulated Interest Receivable");
            data .add("Loan Penalty Receivable");
            
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
            case "Cash And Bank Balance":
 
                
            data = new ArrayList<>();
            data .add("Bank Balance");
            data .add("Cash At Hand");
             data .add("Cash Equivalents");
   
           
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;       
                
            case "Loans And Advances":
 

            data = new ArrayList<>();
            data .add("Loans To Related Parties");
            data .add("Advances To Staff");
             data .add("Deposits (Other Than Fixed Deposit)");
            data .add("Loans To Customers");
           data .add("Balances With Tax Authority");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;       
          case "Deferred Tax Assets":
 

            data = new ArrayList<>();
            data .add("Deferred Tax Asset");
            modelcombo1 = new MyComboBoxModel(data);
            jComboBoxSubCat5.setModel(modelcombo1);
            break;            
        
       
            
         
            case "Current Liabilities":
         

            data  = new ArrayList<>();
            data .add("Accounts Payable");
            data .add("Unknown Mobile Money");
         
            data .add("Liability For Leased Assets");
             data .add("Accrued Interest");
            data .add("Amounts Due To Others");
             data .add("Unpaid Matured Debts");
            data .add("Insurance Payable");
            data .add("Interest Payable");
            data .add("Tax Payable");
            data .add("PAYE Payable");
             data .add("NSSF Payable");
            data .add("Return On Investment Payable");
            data .add("Unclassified Amounts");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
                
             case "Provisions":    
            data  = new ArrayList<>();
            data .add("Provision For Income Tax");
            data .add("Provision For Bad Debts");
             data .add("Proposed Dividends");
            data .add("Other Provisions");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;   
      
            case "Secured Liabilities":
            data  = new ArrayList<>();
           data .add("Secured Loan From Financial Institutions");
            data .add("Secured Other Loans");
            data .add("Debt Securities Issued");
            data .add("Secured Due To Related Parties");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
                  
            case "Unsecured Liabilities":
   
            data  = new ArrayList<>();
            data .add("Unsecured Loan From Financial Institutions");
            data .add("Unsecured Other Loans");
            data .add("Payables For More Than One Operating Cycle");
            data .add("Creditors For More Than One Operating Cycle");
            data .add("Unsecured Due To Related Parties");
            
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;   
                
             case "Deferred Tax Liabilities":
            data  = new ArrayList<>();
            data .add("Deferred Tax Liability");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
     
            case "Share Capital":
            data  = new ArrayList<>();
            data .add("Issued, Subscribed And Paid Up Capital");
            data .add("Additional Issued Share Capital");
                     modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;    
    case "Reserves And Surplus":
            data  = new ArrayList<>();
            data .add("Education Fund 1%");
            data .add("Capital Reserve");
            data .add("Education Fund");
            data .add("Statutory Reserve Fund 10%");
            data .add("Revaluation Reserve");
            data .add("Co-Operative Development Fund");
             data .add("Retained Earnings");
            data .add("Accumulated Profit Or Loss Or Surplus Or Deficit");
            data .add("General Reserve");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break; 
                 
                 
            case "Main Income":

            data  = new ArrayList<>();
            data .add("Sales");
            data .add("Gross Interest Income");
            data .add("Gross Receipt Of Profession Fees");
            data .add("Gross Receipt Of Consultancy Fees");
            
            modelcombo1 = new MyComboBoxModel(data );
           jComboBoxSubCat5.setModel(modelcombo1);
            break;
          case "Other Income":
   
            data  = new ArrayList<>();
            data .add("Ledger Fee");
            data .add("Savings Withdraw Charges");
            data .add("Bad Debts Interest Recovered");
             data .add("Admin Costs");
             data .add("Charges");
             data .add("Membership Fees");
             data .add("Loan Insurance Charges");
             data .add("Accumulated Interest Income");
             data .add("Loan Surcharge");
              data .add("Commission And Fees");
              data .add("Bad Debts Principal RecoveredX");
              data .add("Loan Processing Fees");
              data .add("Annual Subscription Fees");
              data .add("Provision For Bad And Doubtful Debts");
              data .add("Bad Debts Recovered");
              data .add("Savings Penalties");
              data .add("Other Incomes");
               data .add("Bad Debts Principal Recovered");
              
            modelcombo1 = new MyComboBoxModel(data );
           jComboBoxSubCat5.setModel(modelcombo1);
            break;
             case "Income From Short Term Insurance Business":

            data  = new ArrayList<>();
            data .add("Gross Amount Received");
            data .add("Amount Recovered");
            data .add("Reserve For Unexpired Risk Brought From Previous Year Of Income");
            data .add("Other Purely Short Term Insurance Income");
            data .add("Any Other Income");
            
            modelcombo1 = new MyComboBoxModel(data );
           jComboBoxSubCat5.setModel(modelcombo1);
            break;    
         
            case "Opening Stock":
    
            data  = new ArrayList<>();
            data .add("Opening Stock Of Raw Materials");
            data .add("Opening Stock Of Work In Progress");
            data .add("Opening Stock Of Trading Goods");
            data .add("Opening Stock Of Manufactured Goods");
          modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
             case "Purchases":
            data  = new ArrayList<>();
            data .add("Local Purchases Net Of Duties And Taxes");
            data .add("Imports CIF Value And Taxes");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;    
             case "Other Direct Costs":
            data  = new ArrayList<>();
            data .add("Direct Wages");
            data .add("Interest Expenses");
            data .add("Direct Expenses");

          modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;    
              case "Factory Overheads":
            data  = new ArrayList<>();
            data .add("Factory Rent");
            data .add("Factory Rates");
            data .add("Fuel And Power");
            data .add("Indirect Wages");
            data .add("Consumables");
            data .add("Depreciation");
            data .add("Other Factory Overheads");
   
          modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;   
               case "Closing Stock":
            data  = new ArrayList<>();
            data .add("Closing Stock Of Raw Materials");
            data .add("Closing Stock Of Work In Progress");
            data .add("Closing Stock Of Manufactured Goods");
            data .add("Closing Stock Of Trading Goods");
          modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;  
                
           
            case "Operating Expense":
            data  = new ArrayList<>();
            data .add("Advertisement Expense");
             data .add("Audit Expenses");
             data .add("Bad Debts Written Off");
            data .add("Commission"); 
             data .add("Computer Soft And Hardware Expenses");
             data .add("Maintainance of Office Equipment");
             data .add("News Papers And Periodicals");
             data .add("Donations");
             data .add("Entertainment And Refreshments");
             data .add("Freight And Transport");
             data .add("Air Time Allowance");
             data .add("Hotel, Boarding And Lodging Expenses");
             data .add("Legal Expenses");
            data .add("Fuel");
            data .add("Utilities Expenses");
            data .add("Provision For Bad And Doubtful Debts Expense");
            data .add("Rent Expense");
            data .add("Bad Debts Expense");
            data .add("Car Maintainance And Repairs");
            data .add("Provision for Depreciation");
            data .add("Office Maintainance Expense");
            data .add("Consultancy");
            data .add("Loan Recovery Expenses");
            data .add("Stationery And Printing");
            data .add("Lunch Allowance");
            data .add("Telephone And Internet");
             data .add("Training Expenditure");
             data .add("Lincenses And Permits");
             data .add("Parking And Security");
               data .add("Compliance And Regulatory Expenses");
               data .add("Caveat Charges");
            
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
                 case "Administrative Expense":
            data  = new ArrayList<>();
            data .add("Depreciation Expense");
            data .add("Loss On Disposal Of Assets");
            data .add("Management Fees");
            data .add("AGM Expenses");
            data .add("Loans Committee Expenses");
            data .add("Admin Expenses");
            
           
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
             case "Employment Expense":
            data  = new ArrayList<>();
            data .add("Salaries And Wages");
            data .add("Bonus");
            data .add("PAYEE");
            data .add("Board Sitting Allowances");
            data .add("Employee Allowances");
            data .add("Directors Allowances");
             data .add("Contribution To Retirement Fund");
             data .add("Medical Allowance");
             data .add("NSSF Contributions");
              data .add("Festive Allowance");
                data .add("Employee Welfare");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
 
            case "Financing Expense":
            data  = new ArrayList<>();
             data .add("Interest Expense");
            data .add("Bank Charges");
            data .add("Start Up Expenses");
            data .add("Lending License");
             data .add("Company Stamps");
             data .add("Unrealized Exchange Loss");
             data .add("Return On Investment Expense");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
           case "Expense Purely Related To Short Term Insurance Business Income":
            data  = new ArrayList<>();
           data .add("Claims Admitted During The Year");
           data .add("Premiums Returned To Insured");
           data .add("Reserves For Unexpired Risk Carried Forward");
           data .add("Agency Expenses");
           data .add("Other Expenses Related To Short Term Busines");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
                case "General Expensee":
            data  = new ArrayList<>();
             data .add("General expense");
          
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
        
             case "Income Taxes Expense":
            data  = new ArrayList<>();
            data .add("Income Tax Expense");
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
           case "Dividend paid during the period":
            data  = new ArrayList<>();
            data .add("Dividends paid during the Operating period");  
            modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
//             case "Restatement of retained earning for corrections of prior period errors":
//            data  = new ArrayList<>();
//            data .add("Restatement of retained earnings for corrections of previous period errors");  
//            modelcombo1 = new MyComboBoxModel(data );
//            jComboBoxSubCat5.setModel(modelcombo1);
//            break;
//            
//           
//            case "Restatement of retained earning for changes in accounting policy":
//                
//            data  = new ArrayList<>();
//            data .add("Restatement of retained earnings for changes in accounting policies");  
//            modelcombo1 = new MyComboBoxModel(data );
//            jComboBoxSubCat5.setModel(modelcombo1);
//            break;
            case "Retained earning at start of period":
            data  = new ArrayList<>();
         data .add("Retained earnings at start of Operating period");
   modelcombo1 = new MyComboBoxModel(data );
            jComboBoxSubCat5.setModel(modelcombo1);
            break;
           
           
    }
    }//GEN-LAST:event_jComboBoxSubCat4ActionPerformed

    private void jComboBoxSubCat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSubCat5ActionPerformed

        if(jComboBoxSubCat2.getSelectedItem().toString().equalsIgnoreCase("Expenses")){
         feelExpenseNames(expenseSelector);                
                expenseSelector.setVisible(true);
        }else{
         expenseSelector.setVisible(false);
        
        }
        
        
        accountDetails.add(4, jComboBoxSubCat5.getSelectedItem().toString());//Account cat 5:4
        creatLedger1.setEnabled(false);
        creatLedger.setEnabled(true);
                RejectLedgerCreation.setEnabled(true);
        RejectLedgerCreation.setEnabled(true);
   jComboBoxSubCat5.setEnabled(false);
     String selection3 = jComboBoxSubCat5.getSelectedItem().toString();
    accountsNumber.setText(ancdb.createAccountNumber(selection3,"10",selection3,this));
  
    }//GEN-LAST:event_jComboBoxSubCat5ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        jButton11.setVisible(true);
             jButton8.setVisible(false);
              jButton15.setVisible(false);
        column1= new ArrayList();
        column1.add(0, "CDate");
        column1.add(1, "Ledger Id");
        column1.add(2, "LName");
        column1.add(3, "A/C Name");
        column1.add(4, "Balance");
        column1.add(5, "AC Status");

         jTable3.setVisible(false);
        jTable2.setVisible(true);
        approveDelletePanel.setVisible(true);
        mainAccountDisplayPanel.setVisible(false);
        BudgetItemsPanel.setVisible(false);
        ancdb.feelActiveAccounts(jTable2,column1);

        jPanel2.setVisible(true);
        jPanel13.setVisible(false);
        jButton13.setEnabled(false);
        jButton12.setEnabled(false);
         jButton19.setEnabled(false);
         jTextField10.setVisible(true);
         HeaderRenderer header = new HeaderRenderer(jTable2.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton38.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton47.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton45.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton28.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable2,jTextField10);   

        int n=0;

jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable2.getColumnModel().getColumnCount()){
        jTable2.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        if(n==2){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==3){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==4){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
        if(n==5){
        jTable2.getColumnModel().getColumn(n).setMinWidth(0);
        jTable2.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable2.getColumnModel().getColumn(n).setPreferredWidth(80);
        }
        n++;

        }
        jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
        if(col==4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

        String accNu= fios.stringFileReader(fios.createFileName("accountManagement", "createAccounts", "accountActivate"));
        
     if(accNu.equalsIgnoreCase("Approved")){
         JOptionPane.showMessageDialog(this, "PLEASE FIRST SELECT THE LEDGER TO DELETE");
       
       }else if(ancdb.testTable("bsanca"+accNu)){
           
        if(dbq.hasAccountBalance(accNu)){JOptionPane.showMessageDialog(this, "Nill Ledger Balance first");return;
        
        } 
        else{
      Object[] optionsSGS = {"Continue",  "Cancel"};
    int nSGS = JOptionPane.showOptionDialog(this,  "This action will permanently delete the ledger including the completed loans !!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
         if((loan.testTable("newloan"+accNu)))
         
         {
             JOptionPane.showMessageDialog(this, ancdb.AccountNameDB(accNu)+" "+"Has an active loan and the ledger can't be deleted!!");
             return;
         }else{
             
         if((loan.testTable("closedloan1"+accNu))){
         
         loan.deleteLoan(accNu);
         
         }    
       if(parseInt(accNu.substring(2, 9))>=5020000&&parseInt(accNu.substring(2, 9))<=5029999){
        
        ancdb.deletecustomer(accNu); 
         ancdb.deleteAccount(accNu);
         ancdb.dropAccount(accNu);
       
         }else{
          ancdb.deleteAccount(accNu);
         ancdb.dropAccount(accNu);
         }}
    }
    else if (nSGS==JOptionPane.NO_OPTION){ return;}
        
         }
        
        ancdb.dropAccount(accNu);
        column1= new ArrayList();
         column1.add(0, "CDate");
         column1.add(1, "Ledger Id");
         column1.add(2, "L Name");
         column1.add(3, "A/C Name");
         column1.add(4, "Balance");
         column1.add(5, "AC Status");
         
        jTable2.setVisible(true);
        fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        
       JOptionPane.showMessageDialog(this, dbq.AccountName(accNu)+" "+"'s"+" "+"ledger was successfully deleted!!!!"); 
       
     ancdb.feelActiveAccounts(jTable2,column1); 
   
     
     }else{ JOptionPane.showMessageDialog(this, "THE LEDGER DOES NOT EXIST");}

     
    }//GEN-LAST:event_jButton11ActionPerformed

    private void creatLedger1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatLedger1ActionPerformed
   if(jComboBoxSubCat2.isEnabled()==true){
   
   jComboBoxSubCat2.setEnabled(false);
   jComboMainCat.setEnabled(true);
   
   }else if(jComboBoxSubCat3.isEnabled()==true){
   jComboBoxSubCat3.setEnabled(false);
   jComboBoxSubCat2.setEnabled(true);
   }else if(jComboBoxSubCat4.isEnabled()==true){
   jComboBoxSubCat4.setEnabled(false);
    jComboBoxSubCat3.setEnabled(true);
   }else if(jComboBoxSubCat5.isEnabled()==true){
   jComboBoxSubCat5.setEnabled(false);
   jComboBoxSubCat4.setEnabled(true);
   jComboBoxSubCat4.setVisible(true);
   jComboBoxSubCat5.setVisible(false);
   
   }
    }//GEN-LAST:event_creatLedger1ActionPerformed

    private void jButton110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton110ActionPerformed
        Integer z1=101;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton110ActionPerformed

    private void jButton109ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton109ActionPerformed
        Integer z1=102;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton109ActionPerformed

    private void jCheckBox69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox69ActionPerformed
        if(jTextField52.getText().equals("")||jTextField51.getText().equals("")){

            JOptionPane.showMessageDialog(this, "Please Select dates for the operating cycle");
            jCheckBox69.setSelected(false);
        }else{

            if(jCheckBox69.isSelected()){
                jTextField52.setEditable(false);
                jTextField51.setEditable(false);

                jCheckBox69.setSelected(true);

                fios.stringFileWriter(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"), jTextField52.getText());
                fios.stringFileWriter(fios.createFileName("persistence", "operatingCycle", "YEARendDate.txt"), jTextField51.getText());

                Integer xti=15;
                fios.intFileWriterReplace(fios.createFileName("persistence", "operatingCycle", "financialYearSet.txt"), xti.toString());

            } else if(!jCheckBox69.isSelected()){

                Integer xti=5;
                fios.intFileWriterReplace(fios.createFileName("persistence", "operatingCycle", "financialYearSet.txt"), xti.toString());
                jTextField52.setText("");
                jTextField51.setText("");
                jCheckBox69.setSelected(false);
            }

        }
    }//GEN-LAST:event_jCheckBox69ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
fios.intFileWriterReplace(fios.createFileName("postingEntry", "generalTrn", "budgetEstimateNo.txt"), rdb.getnumberOfBudgetPeriods()+"");
        
//        if(dbq.ExpenseledgersExist()){
//        jButton58.setEnabled(false);
//        
//        }
        
        
        
        if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"))==7){
        
          jTextField15.setText(fios.stringFileReader(fios.createFileName("accountManagement", "masterBalances", "startingTextMonth.txt")));
           jTextField13.setText(fios.stringFileReader(fios.createFileName("accountManagement", "masterBalances", "endingTextMonth.txt")));  
            jCheckBox2.setSelected(true);
        jComboBox8.setEnabled(false);
        jComboBox7.setEnabled(false);
        }
        
         if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"))==7){
        
          jTextField14.setText(fios.stringFileReader(fios.createFileName("accountManagement", "masterBalances", "startingeffectiveMonth.txt")));
           jTextField11.setText(fios.stringFileReader(fios.createFileName("accountManagement", "masterBalances", "startingEffectiveYear.txt")));  
            jCheckBox1.setSelected(true);
        jComboBox5.setEnabled(false);
        
        }
        
        
        
       if(fios.intFileReader(fios.createFileName("persistence", "operatingCycle", "financialYearSet.txt"))==15 ){
jCheckBox69.setSelected(true);
}else if(fios.intFileReader(fios.createFileName("persistence", "operatingCycle", "financialYearSet.txt"))==5 ){
jCheckBox69.setSelected(false);
jTextField52.setText("");
jTextField51.setText("");
}
       
       
       
        switch(dbq.title(userId)){
            case "Loans Officer":
        jButton13.setEnabled(true);
        jButton12.setEnabled(false);
        jButton10.setEnabled(false);
        jLabel83.setEnabled(false);
        jLabel84.setEnabled(false);
        jTextField52.setEnabled(false);
        jButton110.setEnabled(false);
        jLabel78.setEnabled(false);
        jTextField51.setEnabled(false);
        jButton109.setEnabled(false);
        jCheckBox69.setEnabled(false);
//        jButton17.setVisible(false);
                break;
            case "Accountant":
        jButton13.setEnabled(true);
        jButton12.setEnabled(false);
        jButton10.setEnabled(false);
        jLabel83.setEnabled(false);
        jLabel84.setEnabled(false);
        jTextField52.setEnabled(false);
        jButton110.setEnabled(false);
        jLabel78.setEnabled(false);
        jTextField51.setEnabled(false);
        jButton109.setEnabled(false);
        jCheckBox69.setEnabled(false);
        jButton17.setVisible(false);
                break;
            case "Supervisor":
        jButton13.setEnabled(true);
        jButton12.setEnabled(true);
        jButton10.setEnabled(true);
        jLabel83.setEnabled(false);
        jLabel84.setEnabled(false);
        jTextField52.setEnabled(false);
        jButton110.setEnabled(false);
        jLabel78.setEnabled(false);
        jTextField51.setEnabled(false);
        jButton109.setEnabled(false);
        jCheckBox69.setEnabled(false);
//        jButton17.setVisible(false);
        jButton13.setEnabled(false);
                break;
        
        
           case "Manager":
         jButton13.setEnabled(true);
        jButton12.setEnabled(false);
        jButton10.setEnabled(false);
        
        jLabel83.setEnabled(true);
        jLabel84.setEnabled(true);
        jTextField52.setEnabled(true);
        jButton110.setEnabled(true);
        jLabel78.setEnabled(true);
        jTextField51.setEnabled(true);
        jButton109.setEnabled(true);
        jCheckBox69.setEnabled(true);
        jComboMainCat.setEnabled(false);
        jComboBoxSubCat2.setEnabled(false);
        jComboBoxSubCat3.setEnabled(false);
        jComboBoxSubCat4.setEnabled(false);
        jButton18.setEnabled(false);
        ledgerOpeningBalance.setEnabled(false);
        jButton14.setEnabled(false);
        lastName5.setEnabled(false);
        amountFieldDebit2.setEnabled(false);
        accountsNumber.setEnabled(false);
        jEditorPane1.setEnabled(false);
        creatLedger1.setEnabled(false);
        creatLedger.setEnabled(false);
        RejectLedgerCreation.setEnabled(false);
        jButton17.setVisible(true);
                break;
         case "Cashier":
        jButton13.setEnabled(true);
        jButton12.setEnabled(false);
        jButton10.setEnabled(false);
        jLabel83.setEnabled(false);
        jLabel84.setEnabled(false);
        jTextField52.setEnabled(false);
        jButton110.setEnabled(false);
        jLabel78.setEnabled(false);
        jTextField51.setEnabled(false);
        jButton109.setEnabled(false);
        jCheckBox69.setEnabled(false);
//        jButton17.setVisible(false);
                break;
               case "System Admin":
    jButton13.setEnabled(false);
    jButton12.setEnabled(false);
    jButton10.setEnabled(false);
    jLabel83.setEnabled(false);
    jLabel84.setEnabled(false);
    jTextField52.setEnabled(false);
    jButton110.setEnabled(false);
    jLabel78.setEnabled(false);
    jTextField51.setEnabled(false);
    jButton109.setEnabled(false);
    jCheckBox69.setEnabled(false);
//    jButton17.setVisible(false);
                break;
        }
   HeaderRenderer header = new HeaderRenderer(jTable3.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton40.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton48.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton28.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable3,jTextField12);   

        int n=0;

jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable3.getColumnModel().getColumnCount()){
        jTable3.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        if(n==2){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==3){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==4){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
        if(n==5){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(80);
        }
        n++;

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
//if(col==2){
// this.setHorizontalAlignment(RIGHT);
//
//}
        if(col==4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
        
        
    }//GEN-LAST:event_formWindowOpened

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
       String accNu= fios.stringFileReader(fios.createFileName("accountManagement", "createAccounts", "accountActivate"));
//     if(ancdb.testTable("bsanca"+accNu)==true){
//        if(dbq.hasAccountBalance(accNu)==true){JOptionPane.showMessageDialog(this, "Nill Account Balance first");return;} 
//        else{
       if(accNu.equalsIgnoreCase("Approved")){
         JOptionPane.showMessageDialog(this, "PLEASE FIRST SELECT THE LEDGER TO REJECT");
       
       }else{
      Object[] optionsSGS = {"Continue",  "Cancel"};
    int nSGS = JOptionPane.showOptionDialog(this,  "This action will permanently delete the ledger !!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
//         if((loan.testTable("newloan"+accNu)))
//         
//         {
//             JOptionPane.showMessageDialog(this, ancdb.AccountNameDB(accNu)+" "+"Has an active loan and the account can't be deleted!!");
//             return;
//         }else{
             
//         if((loan.testTable("closedloan1"+accNu))){
//         
//         loan.deleteLoan(accNu);
//         }    
//       if(parseInt(accNu.substring(2, 9))>=5020000&&parseInt(accNu.substring(2, 9))<=5029999){
//        
//        ancdb.deletecustomer(accNu); 
//         ancdb.deleteAccount(accNu);
//       
//         }else{
          ancdb.deleteAccount(accNu);
//         }
//    }
    }
    else if (nSGS==JOptionPane.NO_OPTION){ return;}
        
//         }
        
//        ancdb.dropAccount(accNu);
        column1= new ArrayList();
         column1.add(0, "CDate");
         column1.add(1, "AC Number");
         column1.add(2, "AC Name");
         column1.add(3, "AC Cat5");
         column1.add(4, "Balance");
         column1.add(5, "AC Status");
         
        jTable2.setVisible(true);
         fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
     ancdb.feelInactiveAccounts(jTable2,column1);   
       }
     
//     }else{ JOptionPane.showMessageDialog(this, "THE ACCOUNT DOES NOT EXIST");}
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
  Createinternalaccounts mtfo15 = new Createinternalaccounts(userId);
        mtfo15.setVisible(true);
        Dimension mtscreen = Toolkit.getDefaultToolkit().getScreenSize();
        mtfo15.setSize(mtscreen.getSize());
        mtfo15.pack();
//        mtfo15.setUserID(userId);
        this.dispose();    
    }//GEN-LAST:event_jButton17ActionPerformed

    private void expenseSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseSelectorActionPerformed
     lastName5.setText(expenseSelector.getSelectedItem().toString());
     jEditorPane1.setText(expenseSelector.getSelectedItem().toString());
    }//GEN-LAST:event_expenseSelectorActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

              
        
        fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  "Approved");
        jButton11.setVisible(true);
             jButton8.setVisible(false);
              jButton15.setVisible(false);
        column1= new ArrayList();
        column1.add(0, "Account");
        column1.add(1, "Budget Month");
        column1.add(2, "Budget Year");
        column1.add(3, "Budget Amount");
jTextField9.setVisible(true);
         jTable3.setVisible(false);
        jTable1.setVisible(true);
        approveDelletePanel.setVisible(false);
        mainAccountDisplayPanel.setVisible(false);
        ancdb.feelActiveBudgetAccounts(jTable1,column1);

        jPanel2.setVisible(false);
        jPanel13.setVisible(false);
        jButton13.setEnabled(false);
        jButton12.setEnabled(false);
         jButton19.setEnabled(true);
         jButton10.setEnabled(false);
         budgetjPanel8.setVisible(true);
        
        
        
         HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton9.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton40.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton40.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton41.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton42.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton43.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton44.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton45.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton47.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton48.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton46.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton53.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton52.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton27.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton28.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable1,jTextField9);   

        int n=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(450);

        }
        if(n==1){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(200);
        }
        if(n==2){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(200);
        }
        if(n==3){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(200);
        }
        n++;

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
if(col==2){
 this.setHorizontalAlignment(RIGHT);

}
        if(col==3){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
       this.setHorizontalAlignment(RIGHT);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton36.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton35.getBackground());
        setForeground(jButton34.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });    
        
        
        
        
        
        
        
        
        
        
  
         
    }//GEN-LAST:event_jButton19ActionPerformed

    private void amountCredit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountCredit2ActionPerformed
       
    }//GEN-LAST:event_amountCredit2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
switch(jComboBox4.getSelectedItem().toString()){
    case "Expense Account": ancdb.fillComboBoxBudgetAccount(jComboBox3, "Expenses"); break;
    case "Revenue Account": ancdb.fillComboBoxBudgetAccount(jComboBox3, "Revenues"); break;


}

    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
                jTextField14.setText(jComboBox5.getSelectedItem().toString());
               fios.stringFileWriter(fios.createFileName("accountManagement", "masterBalances", "startingeffectiveMonth.txt"),jComboBox5.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
             jTextField13.setText(jComboBox7.getSelectedItem().toString());
              fios.stringFileWriter(fios.createFileName("accountManagement", "masterBalances", "endingTextMonth.txt"),jComboBox7.getSelectedItem().toString()); 
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed

    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
   
                 jTextField15.setText(jComboBox8.getSelectedItem().toString());
      fios.stringFileWriter(fios.createFileName("accountManagement", "masterBalances", "startingTextMonth.txt"),jComboBox8.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed

        
        if(jCheckBox2.isSelected()){
            
            if((13-fmt.monthNumber(jTextField15.getText()))+(fmt.monthNumber(jTextField13.getText()))==12||(13-fmt.monthNumber(jTextField15.getText()))+(fmt.monthNumber(jTextField13.getText()))==24){
            
            fios.intFileWriterReplace(  fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"), "7");
jComboBox8.setEnabled(false);
        jComboBox7.setEnabled(false);
        jCheckBox2.setSelected(true);
            }else{
            
            JOptionPane.showMessageDialog(this, "The budgeting process applies for only the operating year that covers 12 months");
            fios.intFileWriterReplace(  fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"), "4");
jComboBox8.setEnabled(true);
        jComboBox7.setEnabled(true);
        jCheckBox2.setSelected(false);
            return;

            }
        
}else if(!jCheckBox2.isSelected()){
    
    
fios.intFileWriterReplace(  fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"), "4");
jComboBox8.setEnabled(true);
        jComboBox7.setEnabled(true);
        jCheckBox2.setSelected(false);
        
        
}
        
      
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
 
        if(!(jTextField14.getText().equals("")||jTextField11.getText().equals(""))){
        
      int todayYear=cal.get(GregorianCalendar.YEAR);
      int todayMonth=cal.get(GregorianCalendar.MONTH);
        int select1Year=parseInt(jTextField11.getText());
       int selectMonth=fmt.monthNumber(jTextField14.getText());
   
           if(todayYear>select1Year){
           
           JOptionPane.showMessageDialog(this, "The budget effective period should not be ealier than today");
           jCheckBox1.setSelected(false);
           jComboBox5.setEnabled(true);
           this.repaint();
          
           }else{ 
            
           if(todayMonth>selectMonth){
           
            
           JOptionPane.showMessageDialog(this, "The budget effective period should not be ealier than today");
           jCheckBox1.setSelected(false);
           jComboBox5.setEnabled(true);
           this.repaint();
           
           }else{ 
            
            
        if(jCheckBox1.isSelected()){
fios.intFileWriterReplace(  fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"), "7");
jComboBox5.setEnabled(false);
        jCheckBox1.setSelected(true);
        fios.stringFileWriter(fios.createFileName("accountManagement", "masterBalances", "startingEffectiveYear.txt"),jTextField11.getText());
}else if(!jCheckBox1.isSelected()){
fios.intFileWriterReplace(  fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"), "4");
jComboBox5.setEnabled(true);
        jCheckBox1.setSelected(false);
}
           
           }  
           
           }  
        }else{
        
        JOptionPane.showMessageDialog(this, "Please First Select the Appropriate effective date for budget implementation");
         jCheckBox1.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
      Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed

        if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"))!=7){
     JOptionPane.showMessageDialog(this, "Please First Set Entity Operating Cycle");
     return;
     }else if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"))!=7){
         
        JOptionPane.showMessageDialog(this, "Please First Set Efective date for budget to apply");
        
      return;
     }
   if(amountCredit2.getValue()==null){
   JOptionPane.showMessageDialog(this, "Please first input the Annual Budget Estimate");
   return;
   } else{
       
   List budgetItem=new ArrayList();
   
     budgetItem.add(jTextField15.getText());//0 Entity operating cycle starting month
      budgetItem.add(jTextField13.getText());//1 Entity operating cycle ending month
        budgetItem.add(jTextField14.getText());//2 Budger effective month
         budgetItem.add(jTextField11.getText());//3 Budger effective year
          budgetItem.add(jComboBox4.getSelectedItem().toString());//4 The account Type
           budgetItem.add(jComboBox3.getSelectedItem().toString());//5 The account
           budgetItem.add(amountCredit2.getValue().toString());//6 Budget amount
          
         
       if(budger. createBudgetItem(budgetItem)){
       
       JOptionPane.showMessageDialog(this, "A new budget item on"+" "+budgetItem.get(4)+" "+"has successfully been created!!");
       
       }else{
       
           
       JOptionPane.showMessageDialog(this, "Creation of the budget item on"+" "+budgetItem.get(4)+" "+"has failed!!");   
       
       }

       Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
   
   }
  
    

    }//GEN-LAST:event_jButton21ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
   
        
        ancdb.fillComboBoxBudgetAccountExistingInBudget(jComboBox11);
        
                 
        if(jCheckBox3.isSelected()){
              jLabel31.setVisible(true);
            jComboBox11.setVisible(true);
                    jComboBox13.setVisible(true);
                    jComboBox6.setVisible(true);
jButton20.setVisible(true);
        jButton29.setVisible(true);
jLabel17.setVisible(true);
jTextField16.setVisible(true);
jLabel15.setVisible(true);
jComboBox6.setVisible(true);
jLabel25.setVisible(true);
jCheckBox4.setSelected(false);

jButton32.setVisible(true);


        }else if(!jCheckBox3.isSelected()){
            jLabel31.setVisible(false);
             jComboBox11.setVisible(false);
                    jComboBox13.setVisible(false);
                    jComboBox6.setVisible(false);
            jButton20.setVisible(false);
        jButton29.setVisible(false);
jLabel17.setVisible(false);
jTextField16.setVisible(false);
jLabel15.setVisible(false);
jComboBox6.setVisible(false);
jLabel25.setVisible(false);
jLabel16.setEnabled(true);
jComboBox4.setEnabled(true);
jLabel24.setEnabled(true);
jComboBox3.setEnabled(true);
jLabel18.setEnabled(true);
amountCredit2.setValue(null);
amountCredit2.setEnabled(true);
      jButton32.setVisible(false);  
     jTextField16.setText("");
        }
                     
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed

        jLabel17.setVisible(false);
jTextField16.setVisible(false);
jLabel15.setVisible(false);
jComboBox6.setVisible(false);
jComboBox9.setVisible(false);
jLabel25.setVisible(false);
jLabel16.setEnabled(true);
jComboBox4.setEnabled(true);
jLabel24.setEnabled(true);
jComboBox3.setEnabled(true);
jLabel18.setEnabled(true);
amountCredit2.setValue(null);
amountCredit2.setEnabled(true);
//jCheckBox3.setSelected(false);
   amountCredit2.setEditable(false);
       jCheckBox4 .setSelected(false);
        
        
        
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
if(jCheckBox4.isSelected()){

amountCredit2.setEditable(false);
}else{
amountCredit2.setEditable(true);

}
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
     amountCredit2.setEditable(true);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void amountCredit2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit2MouseEntered
    
    }//GEN-LAST:event_amountCredit2MouseEntered

    private void amountCredit2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit2MouseClicked
       if(!amountCredit2.isEditable()){
     JOptionPane.showMessageDialog(this, "Please first select the account to set the budget on");
     
     }
    }//GEN-LAST:event_amountCredit2MouseClicked

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
     if(jCheckBox5.isSelected()){
         
            ancdb.fillComboBoxBudgetAccountExistingInBudget(jComboBox9);
               jPanel16.setVisible(false);
               jPanel17.setVisible(true);
     jCheckBox7.setVisible(false);

     }else  if(!jCheckBox7.isSelected()){

     jCheckBox7.setVisible(true);
     jPanel17.setVisible(false);
     } 
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
         Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
       Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
      
              
           if(jCheckBox7.isSelected()){
                  ancdb.fillComboBoxBudgetAccountExistingInBudget(jComboBox12);
               jPanel16.setVisible(true);
               jPanel17.setVisible(false);
     jCheckBox7.setVisible(true);
     jCheckBox5.setVisible(false);
     
     }else  if(!jCheckBox7.isSelected()){
 jPanel16.setVisible(false);
     jCheckBox5.setVisible(true);
     
     }   
              
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
     amountCredit3.setValue(null);
          amountCredit4.setValue(null);
      jPanel16.setVisible(false);
               jPanel17.setVisible(false);
     jCheckBox7.setVisible(false);
      jCheckBox5.setVisible(false);
      jCheckBox8.setVisible(true);
      jCheckBox8.setSelected(false);
      jCheckBox7.setSelected(false);
      jCheckBox5.setSelected(false);
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
     Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void amountCredit3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit3MouseClicked

    private void amountCredit3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit3MouseEntered

    private void amountCredit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountCredit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit3ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
          Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void amountCredit4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit4MouseClicked

    private void amountCredit4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountCredit4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit4MouseEntered

    private void amountCredit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountCredit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountCredit4ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        if(jCheckBox8.isSelected()){
     jCheckBox7.setVisible(true);
     jCheckBox5.setVisible(true);
     jCheckBox8.setVisible(false);
     }else  if(!jCheckBox8.isSelected()){
       jCheckBox7.setVisible(false);
     jCheckBox5.setVisible(false);
     jCheckBox8.setVisible(false);
     }
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox9ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
     amountCredit3.setValue(null);
     amountCredit4.setValue(null);
      jPanel16.setVisible(false);
               jPanel17.setVisible(false);
     jCheckBox7.setVisible(false);
      jCheckBox5.setVisible(false);
            jCheckBox8.setVisible(true);
             jCheckBox8.setSelected(false);
                jCheckBox7.setSelected(false);
      jCheckBox5.setSelected(false);
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
         Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jComboBox13ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jComboBox13ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ComponentAdded

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
     ancdb.fillComboBoxBudgetDatesExistingInBudget(jComboBox13,jComboBox11.getSelectedItem().toString());
       ancdb.fillComboBoxBudgetYeearsExistingInBudget(jComboBox6,jComboBox11.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
         ancdb.fillComboBoxBudgetDatesExistingInBudget(jComboBox16,jComboBox9.getSelectedItem().toString());
       ancdb.fillComboBoxBudgetYeearsExistingInBudget(jComboBox10,jComboBox9.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
       ancdb.fillComboBoxBudgetDatesExistingInBudget(jComboBox15,jComboBox12.getSelectedItem().toString());
       ancdb.fillComboBoxBudgetYeearsExistingInBudget(jComboBox14,jComboBox12.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed

        if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"))!=7){
     JOptionPane.showMessageDialog(this, "Please First Set Entity Operating Cycle");
     return;
     }else if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"))!=7){
         
        JOptionPane.showMessageDialog(this, "Please First Set Efective date for budget to apply");
        
      return;
     }
        
        
        
   if(jTextField16.getText().equalsIgnoreCase("")){
       JOptionPane.showMessageDialog(this, "Please first in put the for the  budget estimate");
       
       
       }else{

       List budgetItemuAs=new ArrayList();
   
     budgetItemuAs.add(jComboBox11.getSelectedItem().toString());////0 The account
     
      budgetItemuAs.add(jTextField16.getText());//1 Budget growth rate
      
        budgetItemuAs.add(jComboBox13.getSelectedItem().toString());//2 Budger effective month
        
          budgetItemuAs.add(jComboBox6.getSelectedItem().toString());//3 Budger effective year 
          
       budgetItemuAs.add(jComboBox13.getItemAt(0));//4 Operating cycle start month
       
          budgetItemuAs.add(jComboBox13.getItemAt(jComboBox13.getItemCount()-1));//5  Operating cycle end month
          
       if(budger.updateBudgetItem(budgetItemuAs,"GrowthRateUpdateOnEachItem")){
       
       JOptionPane.showMessageDialog(this, "Budget growth rate update  on"+" \""+budgetItemuAs.get(0)+"\" "+"has successfully been done!!");
       
       }else{
       
           
       JOptionPane.showMessageDialog(this, "Budget growth rate update  on"+" "+budgetItemuAs.get(0)+" "+"has failed!!");   
       
       }
        }
   
  
    Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();       
        
        
        
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed

        if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "entityOperatingUsed.txt"))!=7){
     JOptionPane.showMessageDialog(this, "Please First Set Entity Operating Cycle");
     return;
     }else if(fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "budgetEffectiveUsed.txt"))!=7){
         
        JOptionPane.showMessageDialog(this, "Please First Set Efective date for budget to apply");
        
      return;
     }
        
        
        
        if(jCheckBox9.isSelected()){
   if(amountCredit3.getValue()==null){
       JOptionPane.showMessageDialog(this, "Please first in put the amount of budget estimate");
       
       
       }else{
 if(amountCredit3.getValue()==null){
       JOptionPane.showMessageDialog(this, "Please first in put the amount of budget estimate");
       
       
       }else{
       List budgetItemuA=new ArrayList();
   
     budgetItemuA.add(jComboBox9.getSelectedItem().toString());////0 The account
      budgetItemuA.add(amountCredit3.getValue().toString());//1 Budget amount
        budgetItemuA.add(jComboBox16.getSelectedItem().toString());//2 Budger effective month
          budgetItemuA.add(jComboBox10.getSelectedItem().toString());//3 Budger effective year   
       budgetItemuA.add(jComboBox16.getItemAt(0));//4 Operating cycle start month
          budgetItemuA.add(jComboBox16.getItemAt(jComboBox16.getItemCount()-1));//5  Operating cycle end month
       if(budger.updateBudgetItem(budgetItemuA,"AnualUpdateOnEachItem")){
       
       JOptionPane.showMessageDialog(this, "Budget update  on"+" "+budgetItemuA.get(0)+" "+"has successfully been done!!");
       
       }else{
       
           
       JOptionPane.showMessageDialog(this, "Budget update on"+" "+budgetItemuA.get(0)+" "+"has failed!!");   
       
       }
        }}
   
  }else{
        
        if(amountCredit3.getValue()==null){
       JOptionPane.showMessageDialog(this, "Please first in put the amount of budget estimate");
       
       
       }else{
       List budgetItemuA=new ArrayList();
   
     budgetItemuA.add(jComboBox9.getSelectedItem().toString());////0 The account
      budgetItemuA.add(amountCredit3.getValue().toString());//1 Budget amount
        budgetItemuA.add(jComboBox16.getSelectedItem().toString());//2 Budger effective month
          budgetItemuA.add(jComboBox10.getSelectedItem().toString());//3 Budger effective year   
       budgetItemuA.add(jComboBox16.getItemAt(0));//4 Operating cycle start month
          budgetItemuA.add(jComboBox16.getItemAt(jComboBox16.getItemCount()-1));//5  Operating cycle end month
       if(budger.updateBudgetItem(budgetItemuA,"AnualUpdateOnAllItems")){
       
       JOptionPane.showMessageDialog(this, "Budget update  on"+" "+budgetItemuA.get(0)+" "+"has successfully been done!!");
       
       }else{
       
           
       JOptionPane.showMessageDialog(this, "Budget update on"+" "+budgetItemuA.get(0)+" "+"has failed!!");   
       
       }
        }
      
   
       
       
       }
    Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();     
        
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
       if(amountCredit4.getValue()==null){
       JOptionPane.showMessageDialog(this, "Please first in put the amount of budget estimate");
       
       
       }else{
       List budgetItemu=new ArrayList();
   
     budgetItemu.add(jComboBox12.getSelectedItem().toString());////0 The account
     
      budgetItemu.add(amountCredit4.getValue().toString());//1 Budget amount
      
        budgetItemu.add(jComboBox15.getSelectedItem().toString());//2 Budger effective month
        
          budgetItemu.add(jComboBox14.getSelectedItem().toString());//3 Budger effective year
          
       budgetItemu.add(jComboBox15.getItemAt(0));//4 Operating cycle start month
       
          budgetItemu.add(jComboBox15.getItemAt(jComboBox15.getItemCount()-1));//5  Operating cycle end month
         
       if(budger.updateBudgetItem(budgetItemu,"MonthlyUpdateOnEachItem")){
       
       JOptionPane.showMessageDialog(this, "Budget update  on"+" "+budgetItemu.get(0)+" "+"has successfully been done!!");
       
       }else{
       
           
       JOptionPane.showMessageDialog(this, "Budget update on"+" "+budgetItemu.get(0)+" "+"has failed!!");   
       
       }

       Createinternalaccounts fo15 = new Createinternalaccounts(userId);
        fo15.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        fo15.setSize(screen.getSize());
        fo15.pack();
//        fo15.setUserID(userId);
        this.dispose();
   
       
       
       }
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox10ActionPerformed
      if(jCheckBox10.isSelected()){
      amountCredit3.setEditable(false);
      
      }else{
      amountCredit3.setEditable(true);
      }
    }//GEN-LAST:event_jCheckBox10ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
       jLabel31.setVisible(false);
             jComboBox11.setVisible(false);
                    jComboBox13.setVisible(false);
                    jComboBox6.setVisible(false);
            jButton20.setVisible(false);
        jButton29.setVisible(false);
jLabel17.setVisible(false);
jTextField16.setVisible(false);
jLabel15.setVisible(false);
jComboBox6.setVisible(false);
jLabel25.setVisible(false);
jLabel16.setEnabled(true);
jComboBox4.setEnabled(true);
jLabel24.setEnabled(true);
jComboBox3.setEnabled(true);
jLabel18.setEnabled(true);
amountCredit2.setValue(null);
amountCredit2.setEnabled(true);
      jButton32.setVisible(false);  
     jTextField16.setText("");
     jCheckBox3.setSelected(false);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
      try {
             jTable2.print();
         } catch (PrinterException ex) {
             Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
      List<List>  d=new ArrayList();  List c=new ArrayList();
        
//        if(signal.equalsIgnoreCase("CustomerStatment")){
        ObjectTableModel original = (ObjectTableModel)jTable2.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
            if(jTable2.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable2.convertRowIndexToModel(targetRow)));
            }
    }
}
        
        
//        }else{
         
            
//        ListDataModel original = (ListDataModel)adminMainTable.getModel();
//        
//for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}
//
//d.add(c);
//
//for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
//    if(!original.getRow(targetRow).isEmpty()){
//    d.add(original.getRow(targetRow));
//    }
//}
//}
   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
     try {
             jTable3.print();
         } catch (PrinterException ex) {
             Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
             List<List>  d=new ArrayList();  List c=new ArrayList();
        
//        if(signal.equalsIgnoreCase("CustomerStatment")){
        ObjectTableModel original = (ObjectTableModel)jTable3.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
           if(jTable3.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable3.convertRowIndexToModel(targetRow)));
           }
           
//           else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }
}
        
        
//        }else{
         
            
//        ListDataModel original = (ListDataModel)adminMainTable.getModel();
//        
//for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}
//
//d.add(c);
//
//for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
//    if(!original.getRow(targetRow).isEmpty()){
//    d.add(original.getRow(targetRow));
//    }
//}
//}
   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
     Object[] optionsSGS = {"Yes",  "No"};
    int nSGS = JOptionPane.showOptionDialog(this,  "Are you sure, you want to create All Operating Expenses?",
    "CREATE OPERATING EXPENSES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
    
        List dataMix  = new ArrayList<>();
            dataMix .add("Advertisement Expense");
             dataMix .add("Audit Expenses");
             dataMix .add("Bad Debts Written Off");
            dataMix .add("Commission"); 
             dataMix .add("Computer Soft And Hardware Expenses");
             dataMix .add("Maintainance of Office Equipment");
             dataMix .add("News Papers And Periodicals");
             dataMix .add("Donations");
             dataMix .add("Entertainment And Refreshments");
             dataMix .add("Freight And Transport");
             dataMix .add("Air Time Allowance");
             dataMix .add("Hotel, Boarding And Lodging Expenses");
             dataMix .add("Legal Expenses");
            dataMix .add("Fuel");
            dataMix .add("Utilities Expenses");
            dataMix .add("Provision For Bad And Doubtful Debts Expense");
            dataMix .add("Rent Expense");
            dataMix .add("Bad Debts Expense");
            dataMix .add("Car Maintainance And Repairs");
            dataMix .add("Provision for Depreciation");
            dataMix .add("Office Maintainance Expense");
            dataMix .add("Consultancy");
            dataMix .add("Loan Recovery Expenses");
            dataMix .add("Stationery And Printing");
            dataMix .add("Lunch Allowance");
            dataMix .add("Telephone And Internet");
             dataMix .add("Training Expenditure");
             dataMix .add("Lincenses And Permits");
             dataMix .add("Parking And Security");
              dataMix .add("Compliance And Regulatory Expenses");
              dataMix .add("Caveat Charges");
          int d=0;   
             while(d<dataMix.size()){
             String accountsNumber=ancdb.createAccountNumber(dataMix.get(d).toString(),"10",dataMix.get(d).toString(),this);    
        List       accountDetails1=new ArrayList<>();
        accountDetails1.add(0, "Debits"); //Account cat 1:0
        accountDetails1.add(1, "Expenses"); //Account cat 2:1                
        accountDetails1.add(2, "Operating Expenses"); //Account cat 2:1              
        accountDetails1.add(3, "Operating Expense"); //Account cat 2:1 
        accountDetails1.add(4, dataMix.get(d).toString()); //Account cat 2:1   
        accountDetails1.add(5, accountsNumber); //Account Number:5 
        accountDetails1.add(6, "0");   //Account balance:6 
        accountDetails1.add(7, dataMix.get(d).toString()+accountsNumber.substring(8, 9));  //Account Name:7   
        accountDetails1.add(8,  dataMix.get(d).toString()+accountsNumber.substring(8, 9)); //Account Purpose:8 
        accountDetails1.add(9, this.userId);//User Id:9 
        accountDetails1.add(10,  "10"); //Product Code:10
        accountDetails1.add(11, dataMix.get(d)); //Product Name:11
        ancdb.createAccount(accountDetails1);
        d++;
             }
     JOptionPane.showMessageDialog(this,"The Operating Expenses ledgers were succesfully created" );
      Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
     
    }
    else if (nSGS==JOptionPane.NO_OPTION){ 
       Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
        
       
             
             
           
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
     
        Object[] optionsSGS = {"Yes",  "No"};
    int nSGS = JOptionPane.showOptionDialog(this,  "Are you sure, you want to create All Employees Expenses?",
    "CREATE OPERATING EXPENSES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
    
        List dataMix  = new ArrayList<>();
             dataMix .add("Salaries And Wages");
            dataMix .add("Bonus");
            dataMix .add("PAYEE");
            dataMix .add("Board Sitting Allowances");
            dataMix .add("Employee Allowances");
            dataMix .add("Directors Allowances");
             dataMix .add("Contribution To Retirement Fund");
             dataMix .add("Medical Allowance");
             dataMix .add("NSSF Contributions");
             dataMix .add("Festive Allowance");
             dataMix .add("Employee Welfare");
             
          int d=0;   
             while(d<dataMix.size()){
             String accountsNumber=ancdb.createAccountNumber(dataMix.get(d).toString(),"10",dataMix.get(d).toString(),this);    
        List       accountDetails1=new ArrayList<>();
        accountDetails1.add(0, "Debits"); //Account cat 1:0
        accountDetails1.add(1, "Expenses"); //Account cat 2:1                
        accountDetails1.add(2, "Administrative Expenses"); //Account cat 2:1              
        accountDetails1.add(3, "Employment Expense"); //Account cat 2:1 
        accountDetails1.add(4, dataMix.get(d).toString()); //Account cat 2:1   
        accountDetails1.add(5, accountsNumber); //Account Number:5 
        accountDetails1.add(6, "0");   //Account balance:6 
        accountDetails1.add(7, dataMix.get(d).toString()+accountsNumber.substring(8, 9));  //Account Name:7   
        accountDetails1.add(8,  dataMix.get(d).toString()+accountsNumber.substring(8, 9)); //Account Purpose:8 
        accountDetails1.add(9, this.userId);//User Id:9 
        accountDetails1.add(10,  "10"); //Product Code:10
        accountDetails1.add(11, dataMix.get(d)); //Product Name:11
        ancdb.createAccount(accountDetails1);
        d++;
             }
     JOptionPane.showMessageDialog(this,"The Employment Expenses ledgers were succesfully created" );
      Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
     
    }
    else if (nSGS==JOptionPane.NO_OPTION){ 
       Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
       
    
           
        
        Object[] optionsSGS = {"Yes",  "No"};
    int nSGS = JOptionPane.showOptionDialog(this,  "Are you sure, you want to create All Main Income?",
    "CREATE MAIN INCOME", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
    
        List accountDetails1  = new ArrayList<>();
    String accountsNumber1=ancdb.createAccountNumber("Gross Interest Income","10","Gross Interest Income",this);       
          
            accountDetails1.add(0, "Credits"); //Account cat 1:0
        accountDetails1.add(1, "Revenues"); //Account cat 2:1                
        accountDetails1.add(2, "Revenue/Income"); //Account cat 2:1              
        accountDetails1.add(3, "Main Income"); //Account cat 2:1 
        accountDetails1.add(4, "Gross Interest Income"); //Account cat 2:1   
        accountDetails1.add(5, accountsNumber1); //Account Number:5 
        accountDetails1.add(6, "0");   //Account balance:6 
        accountDetails1.add(7, "Gross Interest Income"+accountsNumber1.substring(8, 9));  //Account Name:7   
        accountDetails1.add(8,  "Gross Interest Income"+accountsNumber1.substring(8, 9)); //Account Purpose:8 
        accountDetails1.add(9, this.userId);//User Id:9 
        accountDetails1.add(10,  "10"); //Product Code:10
        accountDetails1.add(11, "Gross Interest Income"); //Product Name:11
        ancdb.createAccount(accountDetails1);
      
          
        
           List dataMix  = new ArrayList<>();
          dataMix .add("Ledger Fee");
             dataMix .add("Savings Withdraw Charges");
              dataMix .add("Bad Debts Interest Recovered");
                dataMix .add("Bad Debts Principal RecoveredX");
              dataMix .add("Admin Costs");
              dataMix .add("Charges");
              dataMix .add("Loan Insurance Charges");
               dataMix .add("Membership Fees");
              dataMix .add("Accumulated Interest Income");
             dataMix .add("Loan Surcharge");
              dataMix .add("Commission And Fees");
              dataMix .add("Loan Processing Fees");
              dataMix .add("Bad Debts Recovered");
              dataMix .add("Savings Penalties");
              dataMix .add("Other Incomes");
              dataMix .add("Bad Debts Principal Recovered");
              
          int d=0;   
             while(d<dataMix.size()){
             String accountsNumber=ancdb.createAccountNumber(dataMix.get(d).toString(),"10",dataMix.get(d).toString(),this);    
        List       accountDetails=new ArrayList<>();
        accountDetails.add(0, "Credits"); //Account cat 1:0
        accountDetails.add(1, "Revenues"); //Account cat 2:1                
        accountDetails.add(2, "Revenue/Income"); //Account cat 2:1                           
        accountDetails.add(3, "Other Income"); //Account cat 2:1 
        accountDetails.add(4, dataMix.get(d).toString()); //Account cat 2:1   
        accountDetails.add(5, accountsNumber); //Account Number:5 
        accountDetails.add(6, "0");   //Account balance:6 
        accountDetails.add(7, dataMix.get(d).toString()+accountsNumber.substring(8, 9));  //Account Name:7   
        accountDetails.add(8,  dataMix.get(d).toString()+accountsNumber.substring(8, 9)); //Account Purpose:8 
        accountDetails.add(9, this.userId);//User Id:9 
        accountDetails.add(10,  "10"); //Product Code:10
        accountDetails.add(11, dataMix.get(d)); //Product Name:11
        ancdb.createAccount(accountDetails);
        d++;
             }
     JOptionPane.showMessageDialog(this,"The Main Income ledgers were succesfully created" );
      Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
     
    }
    else if (nSGS==JOptionPane.NO_OPTION){ 
       Createinternalaccounts f = new  Createinternalaccounts(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    }//GEN-LAST:event_jButton59ActionPerformed
  
       private void feelExpenseNames(JComboBox jComboBox2){
       
        int v=0; ArrayList <String> userposting=new ArrayList();
       
        String [] post=fios.stringFileReader(fios.createFileName("resources", "posting", "expenseNames.txt")).split("[;]");   
        
        while(v<=post.length-1){if(v==post.length-1){userposting.add(post[v].replace(";", ""));}else{ userposting.add(post[v]);} v++; }
       
        modelcombo = new MyComboBoxModel(userposting);
       
        jComboBox2.setModel(modelcombo); 
       
       
       
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

public String getCounter(){
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt")))+1);

fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"), thecounter);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BudgetItemsPanel;
    private javax.swing.JButton RejectLedgerCreation;
    private javax.swing.JTextField accountsNumber;
    private javax.swing.JFormattedTextField amountCredit2;
    private javax.swing.JFormattedTextField amountCredit3;
    private javax.swing.JFormattedTextField amountCredit4;
    private javax.swing.JFormattedTextField amountFieldDebit2;
    private javax.swing.JPanel approveDelletePanel;
    private javax.swing.JPanel budgetjPanel8;
    private javax.swing.JFormattedTextField clock;
    private javax.swing.JButton creatLedger;
    private javax.swing.JButton creatLedger1;
    private javax.swing.JComboBox<String> expenseSelector;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton109;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton110;
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
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
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
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox69;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JComboBox jComboBoxSubCat2;
    private javax.swing.JComboBox jComboBoxSubCat3;
    private javax.swing.JComboBox jComboBoxSubCat4;
    private javax.swing.JComboBox jComboBoxSubCat5;
    private javax.swing.JComboBox jComboHomeParish1;
    private javax.swing.JComboBox jComboHomeParish2;
    private javax.swing.JComboBox jComboHomeParish3;
    private javax.swing.JComboBox jComboMainCat;
    private javax.swing.JEditorPane jEditorPane1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTree jTree1;
    private javax.swing.JTextField lastName;
    private javax.swing.JTextField lastName2;
    private javax.swing.JTextField lastName5;
    private javax.swing.JButton ledgerBalance;
    private javax.swing.JButton ledgerOpeningBalance;
    private javax.swing.JPanel mainAccountDisplayPanel;
    private javax.swing.JPanel staffPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount()==2){
    
    if(me.getSource()==jTree1){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
    String window =node.toString();
    switch (window){
         
    case "Log Out":
    Object[] optionsSGS = {"Continue",  "Cancel"};
    int nSGS = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
    if(nSGS==JOptionPane.YES_OPTION){
      OperationsModule f = new  OperationsModule(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }
    else if (nSGS==JOptionPane.NO_OPTION){ this.setVisible(true);}
    break;
    }}}
    }

    @Override
    public void mousePressed(MouseEvent e) {
   
     if(e.getSource() == jTable2){  
       
       fios.deleteFileExistance(fios.createFileName("accountManagement", "createAccounts", "accountActivate"));          
                    int selectedRow =jTable2.getSelectedRow();
                    int selectedColumn =jTable2.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	   
         Object cvalue = jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1);
         
         String accountNumber1 = cvalue.toString();  
    
          fios.stringFileWriter(fios.createFileName("accountManagement", "createAccounts", "accountActivate"),  accountNumber1);

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
           clock.setValue(System.currentTimeMillis());
    }

    @Override
    public void updateText(String text) {
      switch( fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"))){
        case 101:jTextField52.setText(text);break;
        case 102:jTextField51.setText(text);break;
    
    }
    }

}
