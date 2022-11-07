package googo.pmms.project2.frames;


import googo.pmms.project2.accountsHelper.CreatingFolders;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingMain;
import googo.pmms.project2.loanHelper.MaxmumAmountBorrowedFormulas;
import googo.pmms.project2.accountsHelper.PostingModal;
import googo.pmms.project2.accountsHelper.SendSms;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databases.BackUpRestoreDB;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.HeaderRenderer2;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.frameHelper.Resource;
import googo.pmms.project2.frameHelper.groupSummuryModel;
import googo.pmms.project2.frameHelper.loanManagementModel;
import googo.pmms.project2.frameHelper.loanManagementModel1;
import googo.pmms.project2.loanHelper.amortizeLoan;
import googo.pmms.project2.reportsHelper.BalanceSheet;
import googo.pmms.project2.smallFrames.BorrowingCharacteristics1;
import googo.pmms.project2.smallFrames.CreateInterest;
import googo.pmms.project2.smallFrames.GaurantorsDetails;
import googo.pmms.project2.smallFrames.SelectingGroupDetails;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stanchart
 */
public class LoanManagmentWindow extends javax.swing.JFrame implements MouseListener,IUpdateText{
    
     String userId;
   fileInputOutPutStreams fios= new fileInputOutPutStreams();
    DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    ArrayList<String> data4, column1;
 ArrayList<ArrayList<String>> data5;
          JOptionPane p;
    PostingModal model ;
    JFrame fi, f,fa,fb,fc;
    Date date;
  SimpleDateFormat df;
String StopinterestRate="";
    SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
       CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
 DatabaseQuaries dbq= new DatabaseQuaries();
 MaxmumAmountBorrowedFormulas maxValue= new MaxmumAmountBorrowedFormulas();
 loanDatabaseQuaries loan=new loanDatabaseQuaries();
 ObjectTableModel models=null;
 TableRowSorter<ObjectTableModel> sorter=null;
 String accountNumberxxxxxc=null,account="",theLoanId="";
   
List amortDetails,theOthers;
    
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
//    Formartter ffm = new Formartter();
 
    GregorianCalendar cal = new GregorianCalendar(); 
          
     JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
//    Formartter form= new Formartter();
    AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
   ReportsDatabase report =new ReportsDatabase();
    BalanceSheet bsheet= new BalanceSheet();
    PostingMain post= new PostingMain(LoanManagmentWindow.this);
    BackUpRestoreDB dbBackUp= new BackUpRestoreDB();
    CreatingFolders filesW= new CreatingFolders();

      
        DecimalFormat df1 = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("#");
      
        int n,s;
 
        double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0;
        Calendar startDate;
        ButtonGroup group1,group2,group3,group4;
      
        Formartter fmt = new Formartter();
       
               SendSms sendsms= new SendSms();
    public LoanManagmentWindow(String userId) {
      this.userId=userId;
        initComponents();
//        dbq.feelWithAccountNamesCurrentLiabilitesCustomers(jComboBox2);
        java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
      this.setTitle("LOAN MANAGEMENT MODULE-"+dbq.title(userId));
      
//        jTextField3.setText(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt")));
        
        jTable1.addMouseListener( this);
          jTree1.addMouseListener( this);
     
        jTable3.addMouseListener( this);
        
        jTable4.addMouseListener(this);
        processTableDetails();
 jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
     

 jTable4.setAlignmentX(LEFT_ALIGNMENT);
jTable4.setAlignmentY(CENTER_ALIGNMENT);
jTable4.setAutoscrolls(true);
jTable4.setShowHorizontalLines(true);
jTable4.setShowGrid(true);
jTable4.setRowHeight(25);
jTable4.setGridColor(Color.gray);
     Resource rscs=new Resource(userId);
     
     switch(dbq.title(userId)){
            case "Loans Officer":
                rscs.loansOfficerAccessRights(jTree1);
                 jButton5.setEnabled(false);
              jButton12.setEnabled(false);
            jButton4.setEnabled(false);
            jButton9.setEnabled(false);
            jButton8.setEnabled(false);
            jButton7.setEnabled(false);
                
                break;
          case "Cashier":
        
              jButton5.setEnabled(false);
              jButton12.setEnabled(false);
            jButton4.setEnabled(false);
//            jButton9.setEnabled(false);
            jButton8.setEnabled(false);
            jButton7.setEnabled(false);
            break;
            case "Accountant":
                   jButton5.setEnabled(false);
              jButton12.setEnabled(false);
            jButton4.setEnabled(false);
//            jButton9.setEnabled(false);
            jButton8.setEnabled(false);
            jButton7.setEnabled(false);
                  break;
                  
                   case "Supervisor":
                       
//                       rscs.supervisorAccessRights(jTree2);
                       
                            jButton5.setEnabled(true);
              jButton12.setEnabled(true);
            jButton4.setEnabled(true);
            jButton9.setEnabled(true);
            jButton8.setEnabled(true);
            jButton7.setEnabled(true);
                       
                       break;
        
        }
    }
  private void invokeMoreLoanDetails(){
  
    BorrowingCharacteristics1 frm = new BorrowingCharacteristics1(userId);
       
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
  
  }
//     public void setUserID(String userid){
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton13 = new javax.swing.JButton();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton24 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton27 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton14 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);
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

        jPanel3.setBackground(java.awt.SystemColor.activeCaption);
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        jPanel3.setLayout(null);

        jTree1.setBackground(java.awt.SystemColor.activeCaption);
        jTree1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        jTree1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTree1.setForeground(new java.awt.Color(255, 255, 255));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Loan Application");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Log Out");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(0, 4, 80, 720);

        jTabbedPane1.setBackground(java.awt.SystemColor.activeCaption);
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jButton9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/addf.png"))); // NOI18N
        jButton9.setText("GAURANTOR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9);
        jButton9.setBounds(590, 0, 140, 40);
        jButton9.setEnabled(false);

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("WAIVE INCOME");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(0, 0, 140, 40);
        jButton5.setEnabled(false);

        jButton6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton6.setText("WRITE OFF");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(380, 0, 109, 40);
        jButton6.setEnabled(false);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("UPLOAD ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(490, 0, 100, 40);

        jButton8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton8.setText("STOP INTEREST ACCUM");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(730, 0, 200, 40);
        jButton8.setEnabled(false);

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setText("CHANGE ACCUM METHOD");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(930, 0, 220, 40);
        jButton7.setEnabled(false);

        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(0, 40, 1190, 520);

        jButton3.setBackground(java.awt.SystemColor.activeCaption);
        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(1150, 0, 48, 40);
        jButton1.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("ASSIGN GROUP");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(0, 650, 132, 40);
        jButton2.setEnabled(false);
        jButton2.setVisible(false);

        jComboBox5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(130, 650, 160, 40);
        jComboBox5.setVisible(false);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("ASSIGN P-OWNER");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(850, 560, 144, 40);
        jButton1.setEnabled(false);

        jComboBox3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox3.setForeground(java.awt.SystemColor.activeCaption);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(990, 560, 200, 40);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("SELECT START DATE-TO CHANGE DUE DATE");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 610, 330, 40);
        jLabel4.setVisible(false);

        jTextField3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(51, 0, 51));
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });
        jPanel1.add(jTextField3);
        jTextField3.setBounds(330, 610, 120, 40);
        jTextField3.setText("");
        jTextField3.setVisible(false);

        jButton23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton23);
        jButton23.setBounds(450, 610, 30, 40);
        jButton23.setVisible(false);

        jButton22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton22.setText("Apply");
        jButton22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton22);
        jButton22.setBounds(480, 610, 110, 40);
        jButton22.setVisible(false);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Search By:");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel5);
        jLabel5.setBounds(330, 560, 73, 40);

        jTextField4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(51, 0, 51));
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });
        jPanel1.add(jTextField4);
        jTextField4.setBounds(400, 560, 250, 40);
        jTextField2.setText("");

        jButton32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton32.setText("Print");
        jButton32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton32);
        jButton32.setBounds(0, 560, 110, 40);

        jButton33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton33.setText("Export");
        jButton33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton33);
        jButton33.setBounds(270, 560, 60, 40);

        jButton12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton12.setText("CREATE INTEREST/DUE DATE");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton12);
        jButton12.setBounds(140, 0, 240, 40);
        jButton12.setEnabled(false);

        jCheckBox1.setText(" All Loan Details");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1);
        jCheckBox1.setBounds(110, 580, 160, 20);

        jCheckBox2.setText("Excel-Table Contents");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox2);
        jCheckBox2.setBounds(110, 560, 160, 20);

        jButton13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton13.setText("UPDATE LOAN DETAILS");
        jButton13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton13);
        jButton13.setBounds(650, 560, 200, 40);
        jButton13.setEnabled(false);

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox3.setText("None Compound Interest");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox3);
        jCheckBox3.setBounds(770, 610, 190, 40);
        jCheckBox3.setVisible(false);

        jCheckBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox4.setText("Compound  Interest");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox4);
        jCheckBox4.setBounds(610, 610, 160, 40);
        jCheckBox4.setVisible(false);

        jButton24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton24.setText("Apply");
        jButton24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton24);
        jButton24.setBounds(960, 610, 110, 40);
        jButton24.setVisible(false);

        jTabbedPane1.addTab("ACTIVE LOANS", jPanel1);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.setLayout(null);

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(0, 40, 1160, 560);

        jButton20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton20.setText("Print");
        jButton20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton20);
        jButton20.setBounds(0, 600, 110, 50);

        jButton21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton21.setText("Export Excel");
        jButton21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton21);
        jButton21.setBounds(110, 600, 110, 50);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Search By:");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jLabel3);
        jLabel3.setBounds(220, 600, 80, 50);

        jTextField2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(51, 0, 51));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField2);
        jTextField2.setBounds(300, 600, 170, 50);
        jTextField2.setText("");

        jButton10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10);
        jButton10.setBounds(1120, 0, 40, 40);

        jButton11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton11.setText("VIEW CLOSING NOTES");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11);
        jButton11.setBounds(0, 0, 210, 40);
        jButton11.setEnabled(false);

        jTabbedPane1.addTab("CLOSED LOANS", jPanel2);

        jPanel4.setBackground(java.awt.SystemColor.activeCaption);
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(null);

        jTable3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jPanel4.add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 1180, 630);

        jButton27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton27.setText("Export Excel");
        jButton27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton27);
        jButton27.setBounds(110, 630, 110, 50);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Search By:");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(jLabel6);
        jLabel6.setBounds(220, 630, 80, 50);

        jButton26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton26.setText("Print");
        jButton26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton26);
        jButton26.setBounds(0, 630, 110, 50);

        jTextField5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(51, 0, 51));
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
        });
        jPanel4.add(jTextField5);
        jTextField5.setBounds(300, 630, 170, 50);
        jTextField2.setText("");

        jTabbedPane1.addTab("WRITTEN OFF LOANS", jPanel4);

        jPanel5.setLayout(null);

        jPanel9.setBackground(java.awt.SystemColor.activeCaption);
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(null);

        jButton28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton28.setText("Print");
        jButton28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton28);
        jButton28.setBounds(0, 640, 110, 40);

        jButton29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton29.setText("Export Excel");
        jButton29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton29);
        jButton29.setBounds(110, 640, 110, 40);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Search By:");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel7);
        jLabel7.setBounds(220, 640, 80, 40);

        jTextField6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(51, 0, 51));
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
        });
        jPanel9.add(jTextField6);
        jTextField6.setBounds(300, 640, 170, 40);
        jTextField2.setText("");

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(jTable7);

        jPanel9.add(jScrollPane8);
        jScrollPane8.setBounds(0, 0, 1210, 640);

        jPanel5.add(jPanel9);
        jPanel9.setBounds(0, 0, 1220, 690);

        jTabbedPane1.addTab("REJECTED LOANS", jPanel5);

        jPanel6.setLayout(null);

        jPanel10.setBackground(java.awt.SystemColor.activeCaption);
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(null);

        jButton30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton30.setText("Print");
        jButton30.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton30);
        jButton30.setBounds(10, 650, 110, 40);

        jButton31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton31.setText("Export Excel");
        jButton31.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton31);
        jButton31.setBounds(120, 650, 110, 40);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Search By:");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel8);
        jLabel8.setBounds(230, 650, 80, 40);

        jTextField7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(51, 0, 51));
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
        });
        jPanel10.add(jTextField7);
        jTextField7.setBounds(310, 650, 170, 40);
        jTextField2.setText("");

        jTable4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable4);

        jPanel10.add(jScrollPane5);
        jScrollPane5.setBounds(0, 50, 1190, 600);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("GENERATE UNDER GROUP");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel2);
        jLabel2.setBounds(290, 10, 200, 30);

        jComboBox2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "DISBURSED", "COMPLETED", "APPLIED", "APPROVED" }));
        jPanel10.add(jComboBox2);
        jComboBox2.setBounds(490, 10, 150, 30);

        jButton14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton14.setText("SELECT");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton14);
        jButton14.setBounds(640, 10, 120, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("SEARCH  BY:");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel9);
        jLabel9.setBounds(0, 10, 100, 30);

        jComboBox4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GROUP", "BORROWER" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox4);
        jComboBox4.setBounds(100, 10, 190, 30);

        jPanel6.add(jPanel10);
        jPanel10.setBounds(0, 0, 1200, 690);

        jTabbedPane1.addTab("GROUP LOANS", jPanel6);

        jPanel7.setBackground(java.awt.SystemColor.activeCaption);
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(null);

        jTable5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jPanel7.add(jScrollPane6);
        jScrollPane6.setBounds(0, 0, 1210, 690);

        jTabbedPane1.addTab("BORROWER'S DATA", jPanel7);

        jPanel8.setBackground(java.awt.SystemColor.activeCaption);
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(null);

        jTable6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable6);

        jPanel8.add(jScrollPane7);
        jScrollPane7.setBounds(10, 10, 1200, 680);

        jTabbedPane1.addTab("SECURITY", jPanel8);

        jPanel3.add(jTabbedPane1);
        jTabbedPane1.setBounds(80, 0, 1210, 720);

        jButton15.setBackground(java.awt.SystemColor.activeCaption);
        jButton15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton15.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/refresh.png"))); // NOI18N
        jButton15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton15);
        jButton15.setBounds(1310, 110, 48, 40);
        jButton15.setVisible(false);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 1390, 760);

        jButton56.setBackground(new java.awt.Color(255, 204, 204));
        jButton56.setText("Blue");
        getContentPane().add(jButton56);
        jButton56.setBounds(2110, 120, 53, 23);

        jButton52.setBackground(new java.awt.Color(0, 204, 102));
        jButton52.setText("Blue");
        getContentPane().add(jButton52);
        jButton52.setBounds(2050, 150, 53, 23);

        jButton48.setBackground(new java.awt.Color(0, 204, 255));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1970, 160, 53, 23);

        jButton47.setBackground(new java.awt.Color(255, 255, 204));
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1950, 180, 53, 23);

        jButton46.setBackground(new java.awt.Color(255, 204, 153));
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1950, 210, 53, 23);

        jButton44.setBackground(new java.awt.Color(204, 204, 255));
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(2010, 220, 53, 23);

        jButton53.setBackground(new java.awt.Color(204, 204, 0));
        jButton53.setText("Blue");
        getContentPane().add(jButton53);
        jButton53.setBounds(2120, 210, 53, 23);

        jButton54.setBackground(new java.awt.Color(0, 204, 204));
        jButton54.setText("Blue");
        getContentPane().add(jButton54);
        jButton54.setBounds(2120, 170, 53, 23);

        jButton42.setBackground(new java.awt.Color(204, 204, 204));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(2200, 150, 53, 23);

        jButton41.setBackground(new java.awt.Color(204, 165, 165));
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(2200, 180, 53, 23);

        jButton40.setBackground(new java.awt.Color(0, 153, 255));
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(2200, 210, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 255, 204));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(2190, 230, 53, 23);

        jButton38.setBackground(new java.awt.Color(152, 198, 94));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(2200, 260, 53, 23);

        jButton43.setBackground(new java.awt.Color(255, 204, 204));
        jButton43.setText("jButton5");
        getContentPane().add(jButton43);
        jButton43.setBounds(2060, 240, 73, 23);

        jButton45.setBackground(new java.awt.Color(201, 222, 223));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1980, 250, 53, 23);

        jButton35.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton35.setText("jButton5");
        getContentPane().add(jButton35);
        jButton35.setBounds(2020, 320, 73, 23);

        jButton36.setText("jButton8");
        getContentPane().add(jButton36);
        jButton36.setBounds(2150, 320, 73, 23);

        jButton34.setBackground(new java.awt.Color(255, 102, 0));
        jButton34.setForeground(new java.awt.Color(102, 102, 0));
        jButton34.setText("jButton5");
        getContentPane().add(jButton34);
        jButton34.setBounds(2030, 390, 73, 23);

        jButton16.setBackground(new java.awt.Color(51, 51, 0));
        jButton16.setText("jButton16");
        getContentPane().add(jButton16);
        jButton16.setBounds(2130, 380, 40, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        
          if(loan.getMethod(this)==2){
          jCheckBox3.setSelected(false);
      jCheckBox4.setSelected(true);
        }else if(loan.getMethod(this)==1){
        
          jCheckBox3.setSelected(true);
      jCheckBox4.setSelected(false);
        }
        
        
        
        
        
        
//          SwingWorker<Void,Void> updateInterest = new SwingWorker(){     
//        @Override
//        protected Object doInBackground() throws Exception {
//            
//       loan.updateInterestRepaymentsInterest(LoanManagmentWindow.this); 
 
//        loan.updateAmdaPenalty2(LoanManagmentWindow.this); 
            
//                    if(loan.getThePenaltyStatus(LoanManagmentWindow.this)==1){
//            loan.updateAmdaPenalty(LoanManagmentWindow.this); 
//               }
//        return null;
//        } };
//        updateInterest.execute();
        
        theOthers=new ArrayList();
 jTabbedPane1.setSelectedIndex(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "windowChangeLoanManagement"+this.userId+".txt")));

//      models= loan.loanManagementTableFillt(jTable2, column1);
 sortTable(jTable4,jTextField7);   
 sortTable(jTable2,jTextField4); 
    sortTable(jTable1,jTextField2);  
        sortTable(jTable3,jTextField5);  
   dbq.fillWithUserNames(jComboBox3);
     loan.fillWithUserNames2(jComboBox5);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
     
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    int i=1;
     while(i<=5){
     
     JOptionPane.showMessageDialog(this, "SYSTEM LOG OUT ERROR!!!!!!!!!");
     }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(jComboBox3.getSelectedItem().toString().equals("Select Portfolio Owner")){JOptionPane.showMessageDialog(this, "Please First Select The Portfolio Owner");return;}else{
       
        if(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt")).equalsIgnoreCase("NOT YET")){
        
        JOptionPane.showMessageDialog(this, "Please first Select the loan from the table");
        
        }else{
        
             if(loan.assignPortfolioOwner(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt")), jComboBox3.getSelectedItem().toString().split("\\s+")[2].trim())){
             
             jButton1.setEnabled(false);
             JOptionPane.showMessageDialog(this, "Loan Portfolio Owner was successfully Assigned");
               LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
             } else{
             JOptionPane.showMessageDialog(this, "Loan Portfolio Owner Assignment Failed");
             
             }
              }}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
//     jButton1.setEnabled(true);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//     
//        
//        if(jComboBox5.getSelectedItem().toString().equals("Select Group Id")){JOptionPane.showMessageDialog(this, "Please First Select The Group Id");return;}else{
//       
//        if(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "GrouPId.txt")).equalsIgnoreCase("NOT YET")){
//        
//        JOptionPane.showMessageDialog(this, "Please first Select the loan from the table to Assign the Group ID");
//        
//        }else{
//        
//             if(loan.assignGroupId(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "GrouPId.txt")), jComboBox5.getSelectedItem().toString())){
//             
//             jButton2.setEnabled(false);
//             JOptionPane.showMessageDialog(this, "Group Id was successfully Assigned");
//               LoanManagmentWindow f = new   LoanManagmentWindow(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();  
//             } else{
//             JOptionPane.showMessageDialog(this, "Group Id Assignment Failed");
//             
//             }
//              }}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
      printTable();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        String theCounter=getCounter();
        ObjectTableModel original = (ObjectTableModel)jTable1.getModel();
        List<List>  d=new ArrayList();  List c=new ArrayList();

        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

        d.add(c);

        for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {

            if(!original.getRow(targetRow).isEmpty()){
                if(jTable1.isCellSelected(targetRow, 0)){
                    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
                }
////                else{
////     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
////           
////           }
            }
        }

        writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

        generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
if(dbq.newLedgerCreated()){
JOptionPane.showMessageDialog(this, "A NEW LEDGER HAS BEEN CREATED\n\nPLEASE FIRST ACTIVATE IT BEFORE YOU PROCEED");
return;
}else{  
        if(uploadDate()){
        
        JOptionPane.showMessageDialog(this, "Data uploaded successfully!!");
         LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
        }
}     
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if(report.noExistingInterest(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt")))){

JOptionPane.showMessageDialog(this, dbq.AccountName(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt")))+ " "+"Has neither Interest nor Accumulated Interest remaining");

return;
}else{     
             
        WaiveInterest f = new WaiveInterest(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
  
      String accountNumber=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
      
       
        if(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt")).equals("Disbursed")){
               if(dbq.testTable("bsanca02222000110")){
                      String outStandingPrincimpal=loan.remainingTotalPrincipal(accountNumber); 
        JOptionPane.showMessageDialog(this, "You about to Write Off"+" "+dbq.AccountName(accountNumber)+"'s loan worth UGX:"+fmt.formatForStatementNumbers(outStandingPrincimpal));
        
   
      
        Object[] optionsSGS1nhh = {"Yes",  "No"};
        int nSGS1nhh = JOptionPane.showOptionDialog(this,  "Writing off this loan will reduce your income by the outstanding princimpal!!!\n"+"Do you want to  continue ?",
        "LOAN WRITEOFF", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1nhh, optionsSGS1nhh[0]);
        if(nSGS1nhh==JOptionPane.YES_OPTION){
            
            if(writeOffOutStandingPrincimpal(accountNumber,outStandingPrincimpal)){
            List provisionDetails=new ArrayList();
//
        provisionDetails.add(dbq.AccountName(accountNumber));//account Name:0
        provisionDetails.add(accountNumber);//account number:1
        provisionDetails.add("365-5000");//range provisioned:2
        provisionDetails.add("100");//percentage provisioned:3
        provisionDetails.add(loan.numberOfDaysInArrears(accountNumber));//Days in arrears:4
         provisionDetails.add(outStandingPrincimpal);//outstanding princimpal:5
        provisionDetails.add(outStandingPrincimpal);//amount provisioned:6     
          loan. adjustStatusOfWriteOff(provisionDetails);
          
          JOptionPane.showMessageDialog(this, dbq.AccountName(accountNumber)+"'s loan was successfully written off!!");
         
          LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
            }else{
            
            JOptionPane.showMessageDialog(this, dbq.AccountName(accountNumber)+"'s loan write off failed!!!"); 
            }
            
        }
        else if (nSGS1nhh==JOptionPane.NO_OPTION){ this.setVisible(true);}
    
      }else{
      JOptionPane.showMessageDialog(this, "Please first create the \"Loans Written Off Expenses Ledger\"");
      
      }}else{
         JOptionPane.showMessageDialog(this, "Please Select Active/Disbursed loan!!");   
        
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed

         Object[] optionsSGS1nhh = {"Yes",  "No"};
        int nSGS1nhh = JOptionPane.showOptionDialog(this,  "Changing the Due Date will put the loan out of arrears or may put in arrears!!!\n"+"Do you want to  continue ?",
        "CHANGE DUE DATE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1nhh, optionsSGS1nhh[0]);
        if(nSGS1nhh==JOptionPane.YES_OPTION){
//            JOptionPane.showMessageDialog(this, fmt.dateForDatabaseWithFullYearBeginningWithDate(jTextField3.getText()));
         loan.updateOneInstalmenDueDate(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt")),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField3.getText()));
         
          JOptionPane.showMessageDialog(this, "The instalment Due Date was successfully changed");
         
          LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 
        
          
            
        }
        else if (nSGS1nhh==JOptionPane.NO_OPTION){ 
            
            jButton22.setEnabled(false);
            
            this.setVisible(true);
        
        
        }
        
        
        
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed

      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"2000");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
            String theCounter=getCounter();
        ObjectTableModel original = (ObjectTableModel)jTable3.getModel();
        List<List>  d=new ArrayList();  List c=new ArrayList();

        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

        d.add(c);

        for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {

            if(!original.getRow(targetRow).isEmpty()){
                if(jTable3.isCellSelected(targetRow, 0)){
                    d.add(original.getRow(jTable3.convertRowIndexToModel(targetRow)));
                }
////                else{
////     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
////           
////           }
            }
        }

        writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

        generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyPressed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
     this.printTableGroup(jTable4.getModel().getValueAt(jTable4.convertRowIndexToModel(0), 3).toString());
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
 List<List>  d=new ArrayList();  List c=new ArrayList(); 
        groupSummuryModel original = (groupSummuryModel)jTable4.getModel();
     
        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
            if(jTable4.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable4.convertRowIndexToModel(targetRow)));
            }
//            else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }}
         String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"GroupLoan"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter); 
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
             printTable();
//             this.getGraphics().drawImage(img, n, n, jTree1);
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
   
        List<List>  d=new ArrayList();  List c=new ArrayList();      
        
        
        if(jCheckBox2.isSelected()){
            
     loanManagementModel original = (loanManagementModel)jTable2.getModel();
     
        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
            if(jTable2.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable2.convertRowIndexToModel(targetRow)));
            }
//            else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }}
         String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter); 
        
        }else if(jCheckBox1.isSelected()){
        
     loanManagementModel1 original = loan.fullLoanDetailsForUpate();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
    if(!original.getRow(targetRow).isEmpty()){
//            if(jTable2.isCellSelected(targetRow, 0)){
    d.add(original.getRow(targetRow));
//            }
//            else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
    }}
     String theCounter=getCounter();     
        
writeExcel.creatSpecialExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
        
        }else{
        
        JOptionPane.showMessageDialog(this, "Please select the approproate export option from the check box options!!!!");
        
        }
        
        
        


    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
       fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "windowChangeLoanManagement"+this.userId+".txt"), jTabbedPane1.getSelectedIndex()+"");
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
//JOptionPane.showMessageDialog(this, account.substring(11, 22));

        List theNotes=Arrays.asList(loan.getTheClosingNotes(account,this).split("[:]"));
        
        
          List< List<Object>> dataBody=new ArrayList();
           List<String> tableHeaders=new ArrayList();
        tableHeaders.add("S/n");
        tableHeaders.add("Characteristic");
        tableHeaders.add("The Comment");
        
   for(Object note:theNotes){
       
   dataBody.add(Arrays.asList(note.toString().split("[;]")));
   
   }
   
   
    ListDataModel  model1= new ListDataModel(dataBody, tableHeaders);   
        
    List theOtherDetailsStatement= new ArrayList();
    
      theOtherDetailsStatement.add(model1);
      
        theOtherDetailsStatement.add(account.substring(11, 22));
        
            ReportView f1 = new ReportView(userId,"closingNotes",theOtherDetailsStatement);
            
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();  
            
    
//         jTable1.setModel(model1);
//         TableRowSorter<ListDataModel> sorter = new TableRowSorter<>((ListDataModel)model1);
//                            jTable1.setRowSorter(sorter); 
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
     
             
        CreateInterest f = new CreateInterest(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
     if(jCheckBox2.isSelected()){
     
     jCheckBox2.setSelected(true);
     jCheckBox1.setSelected(false);
     
     }else if(!jCheckBox2.isSelected()){
      jCheckBox2.setSelected(false);
     jCheckBox1.setSelected(false);
     
     }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
       if(jCheckBox1.isSelected()){
     
     jCheckBox1.setSelected(true);
     jCheckBox2.setSelected(false);
     
     }else if(!jCheckBox1.isSelected()){
      jCheckBox1.setSelected(false);
     jCheckBox2.setSelected(false);
     
     }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
invokeMoreLoanDetails();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
      
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
theOthers=new ArrayList();
        theOthers.add(jComboBox4.getSelectedItem().toString());
// theOthers.add(jComboBox1.getSelectedItem().toString());
 theOthers.add(jComboBox2.getSelectedItem().toString());
 
 SelectingGroupDetails frm = new SelectingGroupDetails(userId,(LoanManagmentWindow)this,theOthers);
       
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        if("".equalsIgnoreCase(StopinterestRate)){
        JOptionPane.showMessageDialog(this, "Please first select the borrower's account whose rate you want to stop!!!!");
       jButton8.setEnabled(false);
        return;
        }else{
        
        
        
     Object[] optionsES = {"Continue",  "Cancel"};
        int nES = JOptionPane.showOptionDialog(this,  "Are you sure about stopping interest rate accumulation for  "+dbq.AccountName(StopinterestRate),
        "STOP INTEREST RATE!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsES, optionsES[0]);
        if(nES==JOptionPane.YES_OPTION){
           
                loan.stopInterest(StopinterestRate);
                
                JOptionPane.showMessageDialog(this, dbq.AccountName(StopinterestRate)+"'"+" interest rate accumulation was successfully stopped!!");
          jButton8.setEnabled(false);
          StopinterestRate="";
        }
        else if (nES==JOptionPane.NO_OPTION){ 
            
            
         jButton8.setEnabled(false);
        
         StopinterestRate="";
        
        }    
        
        
        
        
    
        
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
            
           jCheckBox4.setVisible(true);    
        jCheckBox3.setVisible(true);    
        jButton24.setVisible(true);   
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
              if(jCheckBox3.isSelected()){
      loan.setMethod(1);
      jCheckBox4.setSelected(false);
      }else if(!jCheckBox3.isSelected()){
       loan.setMethod(0);
         jCheckBox3.setSelected(false);
      }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
                 if(jCheckBox4.isSelected()){
      loan.setMethod(2);
      jCheckBox3.setSelected(false);
      jCheckBox4.setSelected(true);
      }else if(!jCheckBox4.isSelected()){
       loan.setMethod(0);
         jCheckBox4.setSelected(false);
      }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
  loan.createTheIndividualM("newloan"+StopinterestRate);
  JOptionPane.showMessageDialog(this, dbq.AccountName(accountNumberxxxxxc)+" "+"accrual method was changed successfully!");
    LoanManagmentWindow f = new   LoanManagmentWindow(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
  jButton7.setEnabled(false);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    if("".equalsIgnoreCase(StopinterestRate)){
        JOptionPane.showMessageDialog(this, "Please first select the borrower's account whose gaurantors you want to set!!!!");
       jButton8.setEnabled(false);
        return;
        }else{
        
       if(dbq.numberOfGaurantors(theLoanId)>=2){
        JOptionPane.showMessageDialog(this, "Only two gaurantors are allowed for each loan!!!!");
       jButton8.setEnabled(false);
        return;
        }else{
        
        GaurantorsDetails frmMain = new GaurantorsDetails(this.userId,fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt")));
        StopinterestRate="";
        frmMain.pack();
        frmMain.setVisible(true);     
        
    } 
        
    }
    }//GEN-LAST:event_jButton9ActionPerformed
 
     
//  private void invokeMoreLoanDetails(){
  
//    BorrowingCharacteristics frm = new BorrowingCharacteristics(userId,PostingEntryWindow.class);
//       
//        frm.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        frm.setSize(screen.getSize());
////        frm.setUserID(userId);
//        frm.pack();
//  
//  }
    
    
    
    public void updateTable(String groupName){
    
   
    
    }
    private boolean writeOffOutStandingPrincimpal(String accountNumber,String Amount){
  
       //Debit Account Number:0
        //Transaction Date:1
        //Narrative 1:2
        //Narrative 2:3
        //Value Date:4
        //Debit Amount:5
        //Credit Account Number:6
        //Credit Account Name:7
        //Debit Reference Number:8
        //Batch Number:9
        //Transaction Type:10
        //Transaction Sequence Number:11


        String BatchNumber= batchCode();
        List debit1= new ArrayList();
        //Borrower account number:0
        //instalment number:1
        //Date instalment Accrued:2
        //Amount Accrued:3
        debit1 .add(0,  "02-2220001-10");
        debit1 .add(1, sdf.format(fmt.getTodayDate()));
        debit1.add(2, "Loan Write Off for"+" "+dbq.AccountName(accountNumber));
        debit1 .add(3, "Dated "+" "+sdf.format(fmt.getTodayDate()));
        debit1 .add(4, sdf.format(fmt.getTodayDate()));
        debit1 .add(5,  Amount);
        debit1 .add(6, "01-1280001-10");
        debit1 .add(7, dbq.AccountName("01128000110"));
        debit1 .add(8, "000zib");
        debit1 .add(9, BatchNumber);
        debit1 .add(10, "System");
        debit1 .add(11, dbq.getTransactionSequenceNumber()+"");

         List credit= new ArrayList();
        credit.add(0, "01-1280001-10");
        credit.add(1, sdf.format(fmt.getTodayDate()));
        credit.add(2, "Loan Write Off for"+" "+dbq.AccountName(accountNumber));
        credit .add(3, "Dated "+" "+sdf.format(fmt.getTodayDate()));
        credit.add(4, sdf.format(fmt.getTodayDate()));
        credit.add(5, Amount);
        credit.add(6, "02-2220001-10");
        credit.add(7, dbq.AccountName("02222000110"));
        credit.add(8, "000zib");
        credit.add(9, BatchNumber);
        credit.add(10, "System");
        credit.add(11, dbq.getTransactionSequenceNumber()+"");

        //        try {
        //          wait(5000);
        //      } catch (InterruptedException ex) {
        //          Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        SwingWorker<Void,Void> postTheEntry = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
 googo.pmms.project2.accountsHelper.PostingMain postMe=new googo.pmms.project2.accountsHelper.PostingMain(LoanManagmentWindow.this);
      postMe.generalPosting(debit1, credit);
      
        return null;
        }};

        postTheEntry.execute();
    
      

      return true;
      
  
  }

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
//            java.util.logging.Logger.getLogger(LoanManagmentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoanManagmentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoanManagmentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoanManagmentWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoanManagmentWindow().setVisible(true);
//            }
//        });
//    }
                    private void   filterTable(String query){
                          
                     sorter.setRowFilter(RowFilter.regexFilter(query));


                    }
                    
                    
                    public void processTableDetails(){
                  
  loanManagementModel    modelM=null;
   
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
        
 
      column1 =new ArrayList<>();
       column1.add("ID");
        column1.add("NAME");
        column1.add("BUSINESS");
          column1.add("RATE(ANNUAL)");
          column1.add("PRI");
         column1.add("O PRI");
         column1.add("O INT");
           column1.add("O LP");
        column1.add("O LAMT");
       column1.add("TENURE");
//       column1.add("ARREARS");
       column1.add("DATE(PROCESSED)");
        column1.add("OWNER");
           column1.add("YEAR");
 modelM= loan.loanManagementTableFilltActiveLoans(jTable2, column1,this);   
  
 

jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(40);
int h=0;
 HeaderRenderer2 header = new HeaderRenderer2(jTable2.getTableHeader().getDefaultRenderer());
jTable2.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h<jTable2.getColumnModel().getColumnCount()){
        jTable2.getColumnModel().getColumn(h).setHeaderRenderer(header);

        if(h==0){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(70);

        }
        if(h==1){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(300);
        }
        if(h==2){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(150);
        }if(h==3){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(70);
        }
        if(h==4){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(115);
        }
         if(h==5){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(115);
        }
           if(h==6){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(115);
        }
             if(h==7){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(115);
        }
                if(h==8){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(115);
        }
         if(h==9){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(95);
        }
           if(h==10){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
             if(h==11){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(70);
        }
             
               if(h==12){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(0);
        }
        h++;

        }
        
//    sortTable(jTable2,jTextField4);   
    
jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(row), 12);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.BOLD,15));
      if(col>=4&&col<=8){
          String text="";
//          if(row<(table.getRowCount()-1)){
           this.setHorizontalAlignment(JLabel.RIGHT);
         
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
//          }
        this.setText(text);
        
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
       setBackground(jButton46.getBackground());
            setForeground(jButton46.getForeground());
        if(isSelected){setBackground(jComboBox3.getForeground());} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(Color.WHITE);
            setForeground(jButton16.getBackground());
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
    jTable2.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable2.setRowSorter(sorter); 
           
         
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
        
    
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
 models= loan.loanManagementTableFilltClosedLoans(jTable1, column1);   
  

jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
jTable1.setRowHeight(30);
int hx=0;
 HeaderRenderer headerx = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());
jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hx<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hx).setHeaderRenderer(headerx);

        if(hx==0){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(80);

        }
        if(hx==1){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
        if(hx==2){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(220);
        }if(hx==3){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
        if(hx==4){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
         if(hx==5){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
           if(hx==6){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
             if(hx==7){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
                if(hx==8){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
         if(hx==9){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
           if(hx==10){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
             if(hx==11){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(60);
        }
        hx++;

        }
   
jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(row), 12);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.PLAIN,14));
      if(col>=3&&col<=8){
           this.setHorizontalAlignment(JLabel.RIGHT);
         String text="";
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        this.setText(text);
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
         setBackground(jButton34.getBackground());
            setForeground(Color.WHITE);
        if(isSelected){setBackground(Color.CYAN);} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(jComboBox3.getForeground());
            setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
//    jTable1.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable1.setRowSorter(sorter);   
           
           
     jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);      
      
 models= loan.loanManagementTableFilltWrittenOffLoans(jTable3, column1);   
  

jTable3.setAlignmentX(LEFT_ALIGNMENT);
jTable3.setAlignmentY(CENTER_ALIGNMENT);
jTable3.setAutoscrolls(true);
jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
jTable3.setRowHeight(30);
int hx1=0;
 HeaderRenderer headerx1 = new HeaderRenderer(jTable3.getTableHeader().getDefaultRenderer());
jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hx1<jTable3.getColumnModel().getColumnCount()){
        jTable3.getColumnModel().getColumn(hx1).setHeaderRenderer(headerx1);

        if(hx1==0){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(80);

        }
        if(hx1==1){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
        if(hx1==2){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(220);
        }if(hx1==3){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
        if(hx1==4){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
         if(hx1==5){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
           if(hx1==6){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
             if(hx1==7){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
                if(hx1==8){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
         if(hx1==9){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
           if(hx1==10){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
             if(hx1==11){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(60);
        }
        hx1++;

        }
         
jTable3.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(row), 9);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.PLAIN,14));
      if(col>=3&&col<=8){
           this.setHorizontalAlignment(JLabel.RIGHT);
         String text="";
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        this.setText(text);
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
       setBackground(jButton34.getBackground());
            setForeground(Color.WHITE);
        if(isSelected){setBackground(Color.CYAN);} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(jComboBox3.getForeground());
            setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
    jTable3.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable3.setRowSorter(sorter);             
                    }
                    
                    
                    public void processTableDetailsGroupLoans(){
                  
  loanManagementModel    modelM=null;
   
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
        
 
      column1 =new ArrayList<>();
       column1.add("TR ID");
        column1.add("LOAN ID");
         column1.add("BORROWER NAME");
          column1.add("PRI");
         column1.add("O PRI");
         column1.add("O INT");
          column1.add("OACC INT");
           column1.add("O LP");
        column1.add("O LAMT");
       column1.add("LOAN CYCLE");
//       column1.add("ARREARS");
       column1.add("LAST DATE");
        column1.add("PORT OWNER");
 modelM= loan.loanManagementTableFilltActiveLoans(jTable2, column1,this);   
  
 

jTable2.setAlignmentX(LEFT_ALIGNMENT);
jTable2.setAlignmentY(CENTER_ALIGNMENT);
jTable2.setAutoscrolls(true);
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
jTable2.setRowHeight(30);
int h=0;
 HeaderRenderer2 header = new HeaderRenderer2(jTable2.getTableHeader().getDefaultRenderer());
jTable2.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h<jTable2.getColumnModel().getColumnCount()){
        jTable2.getColumnModel().getColumn(h).setHeaderRenderer(header);

        if(h==0){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(77);

        }
        if(h==1){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(180);
        }
        if(h==2){
        jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(240);
        }if(h==3){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
        if(h==4){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
         if(h==5){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
           if(h==6){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
             if(h==7){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
                if(h==8){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(120);
        }
         if(h==9){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(95);
        }
           if(h==10){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(100);
        }
             if(h==11){
          jTable2.getColumnModel().getColumn(h).setMinWidth(0);
        jTable2.getColumnModel().getColumn(h).setMaxWidth(700);
        jTable2.getColumnModel().getColumn(h).setPreferredWidth(70);
        }
        h++;

        }
        
//    sortTable(jTable2,jTextField4);   
    
jTable2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(row), 9);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.PLAIN,13));
      if(col>=3&&col<=8){
          String text="";
//          if(row<(table.getRowCount()-1)){
           this.setHorizontalAlignment(JLabel.RIGHT);
         
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
//          }
        this.setText(text);
        
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
         setBackground(jButton34.getBackground());
            setForeground(Color.WHITE);
        if(isSelected){setBackground(Color.CYAN);} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(jComboBox3.getForeground());
            setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
    jTable2.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable2.setRowSorter(sorter); 
           
         
jTable2.setShowHorizontalLines(true);
jTable2.setShowGrid(true);
        
    
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
 models= loan.loanManagementTableFilltClosedLoans(jTable1, column1);   
  

jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
jTable1.setRowHeight(30);
int hx=0;
 HeaderRenderer headerx = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());
jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hx<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(hx).setHeaderRenderer(headerx);

        if(hx==0){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(80);

        }
        if(hx==1){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
        if(hx==2){
        jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(220);
        }if(hx==3){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
        if(hx==4){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
         if(hx==5){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
           if(hx==6){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
             if(hx==7){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
                if(hx==8){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(130);
        }
         if(hx==9){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
           if(hx==10){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(100);
        }
             if(hx==11){
          jTable1.getColumnModel().getColumn(hx).setMinWidth(0);
        jTable1.getColumnModel().getColumn(hx).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(hx).setPreferredWidth(60);
        }
        hx++;

        }
   
jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(row), 9);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.PLAIN,14));
      if(col>=3&&col<=8){
           this.setHorizontalAlignment(JLabel.RIGHT);
         String text="";
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        this.setText(text);
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
     setBackground(jButton34.getBackground());
            setForeground(Color.WHITE);
        if(isSelected){setBackground(Color.CYAN);} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(jComboBox3.getForeground());
            setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
    jTable1.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable1.setRowSorter(sorter);   
           
           
     jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);      
      
 models= loan.loanManagementTableFilltWrittenOffLoans(jTable3, column1);   
  

jTable3.setAlignmentX(LEFT_ALIGNMENT);
jTable3.setAlignmentY(CENTER_ALIGNMENT);
jTable3.setAutoscrolls(true);
jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
jTable3.setRowHeight(30);
int hx1=0;
 HeaderRenderer headerx1 = new HeaderRenderer(jTable3.getTableHeader().getDefaultRenderer());
jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hx1<jTable3.getColumnModel().getColumnCount()){
        jTable3.getColumnModel().getColumn(hx1).setHeaderRenderer(headerx1);

        if(hx1==0){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(80);

        }
        if(hx1==1){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
        if(hx1==2){
        jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(220);
        }if(hx1==3){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
        if(hx1==4){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
         if(hx1==5){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
           if(hx1==6){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
             if(hx1==7){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
                if(hx1==8){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(130);
        }
         if(hx1==9){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
           if(hx1==10){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(100);
        }
             if(hx1==11){
          jTable3.getColumnModel().getColumn(hx1).setMinWidth(0);
        jTable3.getColumnModel().getColumn(hx1).setMaxWidth(700);
        jTable3.getColumnModel().getColumn(hx1).setPreferredWidth(60);
        }
        hx1++;

        }
         
jTable3.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        
           String cvalue = (String)jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(row), 9);
           
     this.setHorizontalAlignment(JLabel.LEFT);
     
this.setFont(new Font("Arial",Font.PLAIN,14));
      if(col>=3&&col<=8){
           this.setHorizontalAlignment(JLabel.RIGHT);
         String text="";
       
       if(parseDouble(value.toString().replaceAll(",", ""))<0){
            
             text = "("+fmt.formatForStatementNumbers(value.toString().replaceAll(",", ""))+")" ;
            }else{
        text = fmt.formatForStatementNumbers(value.toString().replaceAll(",", "") );
        }
        this.setText(text);
      }
      else{this.setText(value.toString());}
      
             
               if(cvalue.equals("Renewed")){
        setBackground(jButton34.getBackground());
            setForeground(Color.WHITE);
        if(isSelected){setBackground(Color.CYAN);} 
        
           
        } else if(cvalue.equals("Applied")) {
          setBackground(Color.WHITE);
           setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
                     
        }  else if(cvalue.equals("Disbursed")) {
             setBackground(jComboBox3.getForeground());
            setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
              
             
           }  else if(cvalue.equals("Submitted")) {
             setBackground(Color.YELLOW);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }else if(cvalue.equals("Authorised")) {
             setBackground(Color.ORANGE);
            setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
              
        }
        
           else if(cvalue.equals("Completed")) {
             setBackground(Color.GREEN);
             setForeground(Color.BLUE);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                 else if(cvalue.equals("Rejected")) {
             setBackground(Color.RED);
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
                    else if(cvalue.equals("WrittenOff")) {
             setBackground(jButton48.getBackground());
             setForeground(Color.BLACK);
              if(isSelected){setBackground(Color.CYAN);}
                     
        }
         
        return this;
    }   
});
    jTable3.addMouseListener(this);
     sorter = new TableRowSorter<>(models);
           jTable3.setRowSorter(sorter);             
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
 
 private synchronized boolean uploadDate(){
 
 boolean updaloadsuccessful=false;  String periodSet="0",thePeriod="30";
  
  amortizeLoan amorty =new amortizeLoan(userId);
        
        JFileChooser fileChooser = new JFileChooser();

        int returnValue = fileChooser.showOpenDialog(null);

        FileInputStream fis=null;File selectedFile=null;XSSFWorkbook workbook=null;XSSFSheet spreadSheet=null;XSSFRow row=null;

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
        selectedFile = fileChooser.getSelectedFile();

        try {
        //               String     filename = selectedFile.getAbsolutePath();

        fis=new FileInputStream(selectedFile);

        workbook=new XSSFWorkbook(fis);

        spreadSheet=workbook.getSheetAt(0);

        } catch (FileNotFoundException ex) {

        JOptionPane.showMessageDialog(this, ex.toString());
        } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, ex.toString());
        }

        Iterator <Row>   rowIterator=spreadSheet.iterator();
int t1=0;
        while(rowIterator.hasNext()){
           
        row=(XSSFRow)rowIterator.next();
        List    amortDetailsx =null,amortDetailsx1 =null;
        Iterator <Cell>  cellIterator=row.iterator();
        
fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"));
 
        int t=0; amortDetailsx = new ArrayList();amortDetailsx1 = new ArrayList(); 
            if(t1>0){

        
        while(cellIterator.hasNext()){
//            
//JOptionPane.showMessageDialog(this, t); 
//
//JOptionPane.showMessageDialog(this, cellIterator.next().toString());

       

        if(t==0){
            
       accountNumberxxxxxc=   cellIterator.next().toString();
       amortDetailsx1.add(accountNumberxxxxxc);
//       JOptionPane.showMessageDialog(this, "accountNumberxxxxxc="+accountNumberxxxxxc);
//            SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
//  
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                loan.createNewLoanAmort(accountNumberxxxxxc,LoanManagmentWindow.this);
//                return null;
//            }
//        };
//        createNewLoan.execute();
        
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"),accountNumberxxxxxc);
       
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        
        
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), dbq.AccountName(accountNumberxxxxxc));
       
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
//          if(n==1){var=accountNumber2;}//Account Number
//                if(n==2){var=jComboBox3.getSelectedItem().toString();}//Account Name
//                if(n==3){var=jTextField36.getText();}//Loan Tenure
//                 if(n==4){var=jComboBox8.getSelectedItem().toString();}//period Type
//                  if(n==5){var=amountFieldDebit7.getValue().toString();}//Principal Amount
//                   if(n==6){var=jTextField35.getText();}//Repayment source
//                   if(n==7){var=jComboBox2.getSelectedItem().toString();}//Loan usage/Purpose
        }
        
      
        if(t==1){
String tenure=cellIterator.next().toString();
   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), tenure);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");

          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "MONTHS");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
       
//      JOptionPane.showMessageDialog(this, "tenure="+tenure);   
        }

          if(t==2){
      String princimpal=       cellIterator.next().toString(); 
//          JOptionPane.showMessageDialog(this, t+";"+princimpal);
             amortDetailsx.add(princimpal);//PRINCIPAL :0
   fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), princimpal);
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");

          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "Mothly Income");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
        
        
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), "BUSINESS PURPOSE");
        fios.stringFileWriterAppend(fios.createFileName("loanApplication", "amortValues", "amortizeMe"+this.userId+".txt"), ",");
       
//            JOptionPane.showMessageDialog(this, "princimpal="+princimpal);   
          }
        
          if(t==3){
//           JOptionPane.showMessageDialog(this, t+";"+cellIterator.next().toString());
String rate=cellIterator.next().toString().replace("'", "");
           amortDetailsx.add(rate);//INTEREST RATE:1
//         JOptionPane.showMessageDialog(this, "rate="+rate);    
          
          } 
           
           if(t==4){
           String anotherT=cellIterator.next().toString().replace("'", "");    
           amortDetailsx.add(anotherT);//LOAN TENURE:2
           
//            JOptionPane.showMessageDialog(this, "anotherT="+anotherT); 
           }
         
          
            if(t==5){
//              JOptionPane.showMessageDialog(this, t+";"+cellIterator.next().toString());
String startDate=cellIterator.next().toString().replace("'", "");
           amortDetailsx.add(startDate);//START DATE:3
           
//           JOptionPane.showMessageDialog(this, "anotherT="+startDate); 
           
          }
           if(t==6){
      String tenureType=cellIterator.next().toString().replace("'", "");         
               
             amortDetailsx.add(tenureType);//TENURE TYPE:4
             
//              JOptionPane.showMessageDialog(this, "tenureType="+tenureType); 
           }
            
              if(t==7){
//              JOptionPane.showMessageDialog(this, t+";"+cellIterator.next().toString());
String portOwner=cellIterator.next().toString();
String accountName=dbq.getUserName(portOwner);
           amortDetailsx.add(accountName+" "+portOwner);
           
           
//               JOptionPane.showMessageDialog(this, "accountName+\" \"+portOwner="+accountName+" "+portOwner); 
          }
        if(t==8){
            
            String interestRegime=cellIterator.next().toString().replace("'", "");
             amortDetailsx.add(interestRegime);//INTEREST REGIME
             
//                JOptionPane.showMessageDialog(this, "interestRegime="+interestRegime); 
           }
              
        
          if(t==9){
              
     periodSet=  cellIterator.next().toString().replace("'", "");
     
//       JOptionPane.showMessageDialog(this, "periodSet="+periodSet); 
           }

            if(t==10){
        thePeriod=  cellIterator.next().toString().replace("'", "");
//         JOptionPane.showMessageDialog(this, "thePeriod="+thePeriod); 
           }
            

t++;
        }
        
//               JOptionPane.showMessageDialog(rootPane, "periodSet= "+ periodSet);
//             JOptionPane.showMessageDialog(rootPane, "thePeriod= "+ thePeriod);
            if(parseInt(periodSet)==1){
        
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt"), thePeriod);
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "YES");
        }else if(parseInt(periodSet)==0){
          fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt"), thePeriod);
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO");
        } else{
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "payPeriod"+this.userId+".txt"), thePeriod);
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "hasPeriodBeenSet"+this.userId+".txt"), "NO"); 
        
        }    
        
//    JOptionPane.showMessageDialog(this, amortDetailsx.get(0).toString()+";"+amortDetailsx.get(1).toString()+";"+amortDetailsx.get(2).toString()+";"+amortDetailsx.get(3).toString()+";"+amortDetailsx.get(4).toString()+";"+amortDetailsx.get(5).toString());
//             SwingWorker<Void,Void>createNewLoan=new SwingWorker(){
//  
//
//            @Override
//            protected Object doInBackground() throws Exception {
      


String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
              List loandetails = loan.getLoanDetails(trnId,this);  
//             
              
              
//              fios.stringFileWriter(fios.createFileName("test", "testit", accountNumberxxxxxc+"trdyr.txt"),amorty.amortizeme(amortDetailsx).toString()+";"+amortDetailsx.get(0).toString()+";"+amortDetailsx.get(1).toString()+";"+amortDetailsx.get(2).toString()+";"+amortDetailsx.get(3).toString()+";"+amortDetailsx.get(4).toString()+";"+amortDetailsx.get(5).toString()); 
          List otherDetails=new ArrayList();
          
          
              
              amorty.createTheLoan(amorty.amortizeme(amortDetailsx,this), amortDetailsx,updateDefaults(otherDetails,accountNumberxxxxxc),LoanManagmentWindow.this);
            
  loan.updateSubmited(accountNumberxxxxxc,"Approved"); 
       
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+accountNumberxxxxxc+ ".txt"), "4");
   
  
  
//      Integer zxsd=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxsd.toString()); 


//  fios.intFileWriterReplace(fios.createFileName("logOut", "pdffile","authorisorId"+activeAccountf+".txt"),userId);
  
    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
    
        
         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        sendsms.createLoanApprovedSMS(accountNumberxxxxxc, loandetails.get(4).toString(),LoanManagmentWindow.this);
             return null; } };
     updatesendSms.execute();
        
  
     
}
//    fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
  
      Integer zxso=4;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+accountNumberxxxxxc+".txt"),zxso.toString());   
// approveLoanLoanDetailsButton.setEnabled(false);
 
//  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+accountNumberxxxxxc+".txt"))==4){
//     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
//         @Override
//         protected Object doInBackground() throws Exception {
//          loan.updateLoanStoreAll(userId+"", accountNumberxxxxxc,"Approved","newloan"+accountNumberxxxxxc,trnId);   
//             
//             return null;  } };
//     updateLoanStore.execute();
//     }   
               
     
        //loandetails
         //0 No of loan instalments
             // 1 Loan Tenure
             //2 Interest Rate
             //3 Total Interest amount
             // 4 princimpal amount
             // 5 total loan amount
             //6 intalment start date
             //7 intalment end date
             //8 The instalment amount
//        List loanDetails= new ArrayList();
//       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
//         loanDetails.add(loandetails.get(3).toString());//interest amount
//  loanDetails.add(loandetails.get(6).toString());//instalment start date
//  loanDetails.add(loandetails.get(7).toString());//instalment end date
//    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
//   loanDetails.add("Approved");//loan cycle status
//    loanDetails.add(dbq.AccountName(accountNumberxxxxxc));//Account Name
//    loanDetails.add(accountNumberxxxxxc);//Account Number
    
    
//      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         @Override
//         protected Object doInBackground() throws Exception {
//           loan.updateLoanReportATable(loanDetails); 
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();
       
       
        
            try {
                wait(6000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoanManagmentWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
t1++;
        }

updaloadsuccessful=true;
        }



return updaloadsuccessful;
 
 
 
 }
 
 private void printTable() {
        /* Fetch printing properties from the GUI components */

        MessageFormat header = null;
        
        /* if we should print a header */
       
            /* create a MessageFormat around the header text */
            header = new MessageFormat("LOAN LISTING AS AT  "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
        
//
        MessageFormat footer = null;
//        
//        /* if we should print a footer */
//     
//            /* create a MessageFormat around the footer text */
            footer = new MessageFormat("Page {0}");
      

        /* determine the print mode */
      

        try {
            /* print the table */
            boolean complete = jTable2.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            /* if printing completes */
            if (complete) {
                /* show a success message */
                JOptionPane.showMessageDialog(this,  "Printing Complete", "Printing Result",JOptionPane.INFORMATION_MESSAGE);
            } else {
                /* show a message indicating that printing was cancelled */
                JOptionPane.showMessageDialog(this,
                                              "Printing Cancelled",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
 
 
 private void printTableGroup(String groupName) {
        /* Fetch printing properties from the GUI components */

        MessageFormat header = null;
        
        /* if we should print a header */
       
            /* create a MessageFormat around the header text */
            header = new MessageFormat("GROUP LOAN LISTING FOR"+" "+groupName+" AS AT  "+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
        
//
        MessageFormat footer = null;
//        
//        /* if we should print a footer */
//     
//            /* create a MessageFormat around the footer text */
            footer = new MessageFormat("Page {0}");
      

        /* determine the print mode */
      

        try {
            /* print the table */
            boolean complete = jTable4.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            /* if printing completes */
            if (complete) {
                /* show a success message */
                JOptionPane.showMessageDialog(this,  "Printing Complete", "Printing Result",JOptionPane.INFORMATION_MESSAGE);
            } else {
                /* show a message indicating that printing was cancelled */
                JOptionPane.showMessageDialog(this,
                                              "Printing Cancelled",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
 
 
 
 public String batchCode(){
        String batch="";int theNumber=0;


        fios.forceFileExistance(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));   

        String Thebatch= fios.stringFileReader(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"));
        theNumber=parseInt(Thebatch)+1;
        fios.stringFileWriter(fios.createFileName("postingEntry", "generalTrn", "batchNumber.txt"), theNumber+"");
        batch="BTN"+(theNumber+"");
        return batch; 
        } 
 
 public List updateDefaults(List theCollection,String accountNumber){
 
        theCollection.add("Existing Borrower");
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
            theCollection.add("Excellent");
              theCollection.add("0792416111");
 
 return theCollection;
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
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
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public javax.swing.JTextField jTextField7;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
         if (me.getClickCount()==2){

    if(me.getSource().equals(jTree1)){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
    String window =node.toString();
    switch (window){
    case "Loan Application":
      Integer xl=0;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());
       Object[] options = {"Continue",  "Cancel"};
        int n = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
        if(n==JOptionPane.YES_OPTION){
            fios.intFileWriterReplace(fios.createFileName("persistence", "topUpsSetUp", "byPassLoanPosting.txt"), "77");
        NewLoanApplication frm = new NewLoanApplication(userId);
        frm.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm.setSize(screen.getSize());
//        frm.setUserID(userId);
        frm.pack();
        this.dispose();
        }
        else if (n==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
        
    case "Amortization Calculator":
    
      Object[] optionsES = {"Continue",  "Cancel"};
        int nES = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsES, optionsES[0]);
        if(nES==JOptionPane.YES_OPTION){
             Integer xh=0;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), xh.toString());
       Integer zx=1;
      fios.forceFileExistance(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"));
     fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"), zx.toString());
     fios.intFileWriterReplace(fios.createFileName("amortization", "flipWindows", "destWindow.txt"), zx.toString());
       amortDetails=new ArrayList();
        AmotizationCalculator frm1 = new AmotizationCalculator(userId,amortDetails);
        frm1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm1.setSize(screen.getSize());
        frm1.pack();

        this.dispose();
        }
        else if (nES==JOptionPane.NO_OPTION){ this.setVisible(true);}
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
    
               case "Email Module":
    Object[] optionsy = {"Continue",  "Cancel"};
    int ny = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsy, optionsy[0]);
    if(ny==JOptionPane.YES_OPTION){
         email f = new email(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }
    else if (ny==JOptionPane.NO_OPTION){ this.setVisible(true);}
    break;
       case "Loan authorisation Module":
    Object[] optionsx = {"Continue",  "Cancel"};
    int nx = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsx, optionsx[0]);
    if(nx==JOptionPane.YES_OPTION){
         LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }
    else if (nx==JOptionPane.NO_OPTION){ this.setVisible(true);}
    break;  
           
         case "Operations Module":
    Object[] option = {"Continue",  "Cancel"};
    int nu = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,option, option[0]);
    if(nu==JOptionPane.YES_OPTION){
    OperationsModule f = new OperationsModule (userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }
    else if (nu==JOptionPane.NO_OPTION){ this.setVisible(true);
    
    }
    break;           
       
           case "Posting Module":
    Object[] optionv = {"Continue",  "Cancel"};
    int nuv = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionv, optionv[0]);
    if(nuv==JOptionPane.YES_OPTION){
    PostingEntryWindow f = new PostingEntryWindow (userId,amortDetails);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        
        this.dispose();
    }
    else if (nuv==JOptionPane.NO_OPTION){ this.setVisible(true);
    
    }
    break;       
        case "Ledger Reconcilliantion":
           Object[] optionsSGS1nhh = {"Continue",  "Cancel"};
        int nSGS1nhh = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1nhh, optionsSGS1nhh[0]);
        if(nSGS1nhh==JOptionPane.YES_OPTION){
             Integer xh=0;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "showNecessaryAmorts.txt"), xh.toString());
        LedgerReconcilliation frm5 = new LedgerReconcilliation(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
//        frm5.setUserID(userId);
        this.dispose();
        }
        else if (nSGS1nhh==JOptionPane.NO_OPTION){ this.setVisible(true);}
   
    break;      
        case "Log Out":
        Object[] optionsSGS = {"Continue",  "Cancel"};
        int nSGS = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
        if(nSGS==JOptionPane.YES_OPTION){
             switch(dbq.title(userId)){
            case "Loans Officer":
         Login frm5 = new Login();
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
        this.dispose();
                break;
            case "Accountant":
           PostingEntryWindow ff = new PostingEntryWindow (userId,amortDetails);
        ff.setVisible(true);
        Dimension screens = Toolkit.getDefaultToolkit().getScreenSize();
        ff.setSize(screens.getSize());
        ff.pack();
        this.dispose();
                
                
                break;
            case "Supervisor":
            PostingEntryWindow fj = new PostingEntryWindow (userId,amortDetails);
        fj.setVisible(true);
        Dimension screenj = Toolkit.getDefaultToolkit().getScreenSize();
        fj.setSize(screenj.getSize());
        fj.pack();
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
            PostingEntryWindow fxs = new PostingEntryWindow (userId,amortDetails);
        fxs.setVisible(true);
        Dimension screenxs = Toolkit.getDefaultToolkit().getScreenSize();
        fxs.setSize(screenxs.getSize());
        fxs.pack();
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
        else if (nSGS==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
  
    
    
    case "Reports Module":
 
      Object[] optionsSSV = {"Continue",  "Cancel"};
    int nSSV = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
    "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsSSV,  optionsSSV[0]);
    if(nSSV==JOptionPane.YES_OPTION){
    Integer xh=16;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "ApprovalReports.txt"), xh.toString());  
        
        Reportx f = new  Reportx(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
    }
    else if (nSSV==JOptionPane.NO_OPTION){ this.setVisible(true);}
         
          
        break;
      }}
         
       if(me.getSource() == jTable2){  
                
                    int selectedRow =jTable2.getSelectedRow();
                    int selectedColumn =jTable2.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	
         
          Object cvalue1 = jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 9);
    
   
         
          if(cvalue1.toString().equals("Completed")){
          
          
     fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1).toString());     
       Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());  
   
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1).toString().substring(11, 22));       
    
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),"Completed");     
        
                   
   }else if(cvalue1.toString().equals("Rejected")){
fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1).toString());     
       Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString()); 
      fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),"Rejected");   
          }else if(cvalue1.toString().equals("WrittenOff")){
          
       fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1).toString());     
       Integer xl=6;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());  
      fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1).toString().substring(14, 25));       
            fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 9).toString());   
          }
   
          
          else{
            
      
          
          
     fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "runningLoanAccount.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 0).toString());     
       Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());     
          
         fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),loan.borrowerAccount(jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 0).toString()));      
          
      fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),"Disbursed");         
   
//        Integer xl=1;
//   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());   
//          
          
          }

                
           }
       
     NewLoanApplication f = new   NewLoanApplication(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();   
       
       
       }
        if(me.getSource() == jTable1){  
                
                    int selectedRow =jTable1.getSelectedRow();
                    int selectedColumn =jTable1.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1) {
	
         
          Object cvalue1 = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 12);
    
         
          if(cvalue1.toString().equals("Completed")){
          
          
     fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"),jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 0).toString());     
       Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());  
   
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),loan.borrowerLoanId(jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 0).toString()));       
    
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),"Completed");     
//         JOptionPane.showMessageDialog(this, "dddddddd");
  NewLoanApplication f = new   NewLoanApplication(userId);
  
        f.setVisible(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        f.setSize(screen.getSize());
        
        f.pack();
//        f.setUserID(userId);
        this.dispose();  
          }
          
//          else{
//        Integer xl=1;
//   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());   
////           JOptionPane.showMessageDialog(this, "dddddddd");
//  NewLoanApplication f = new   NewLoanApplication(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();  
//          
//          }
//         
//           
           }
//                   
//          
//         
       }  
                   
//                   else 
          if(me.getSource() == jTable3){  
                
                    int selectedRow =jTable3.getSelectedRow();
                    int selectedColumn =jTable3.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	
         
          Object cvalue1 = jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(selectedRow), 9);
    
         
          if(cvalue1.toString().equals("Completed")){
          
          
     fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "closedLoanAccount.txt"),jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(selectedRow), 1).toString());     
       Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());  
   
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(selectedRow), 1).toString().substring(11, 22));       
    
        fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),"Completed");     
        
          
          
          } else
          
          {
        Integer xl=1;
   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "fromLoanManagementTable.txt"),xl.toString());   
          
          
          }
          
           
           }
     NewLoanApplication f = new   NewLoanApplication(userId);
     
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();      
         
       } 
         
         
         
         }
   
    
    
    
    }
    

    @Override
    public void mousePressed(MouseEvent me) {
        if(me.getSource() == jTable2){  
                
                    int selectedRow =jTable2.getSelectedRow();
                    int selectedColumn =jTable2.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 Object cvalue1 = jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 0);
         
               if(!cvalue1.toString().equalsIgnoreCase("Total")){
                   
//         Object cvalue = jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 1);
         
            theLoanId= jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 0).toString();
        
          String accountNumber = loan.borrowerAccount(theLoanId);
          
           fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccount"+this.userId+".txt"),accountNumber); 
           
           fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt"),accountNumber);
           
           fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "GrouPId.txt"),accountNumber);
          fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "cycleStatus.txt"),jTable2.getModel().getValueAt(jTable2.convertRowIndexToModel(selectedRow), 9).toString());   
             
       StopinterestRate=       accountNumber; 
               
               }
          switch(dbq.title(userId)){
            case "Loans Officer":
           jButton13.setEnabled(true);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(false);
        jButton12.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(false);
        jTextField3.setVisible(false);
        jButton23.setVisible(false);
        jButton22.setVisible(false);
        jButton22.setEnabled(false);
        jComboBox3.setEnabled(false);
    }
                break;
            case "Accountant":
              jButton13.setEnabled(true);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(false);
        jButton12.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(true);
        jButton9.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(false);
        jTextField3.setVisible(false);
        jButton23.setVisible(false);
        jButton22.setVisible(false);
        jButton22.setEnabled(false);
        jComboBox3.setEnabled(false);
    }
                
                break;
            case "Supervisor":
       jButton13.setEnabled(true);
        jButton2.setEnabled(true);
        jButton5.setEnabled(true);
        jButton4.setEnabled(true);
        jButton6.setEnabled(true);
        jButton12.setEnabled(true);
        jButton13.setEnabled(true);
        jButton9.setEnabled(true);
          jButton8.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(true);
        jTextField3.setVisible(true);
        jButton23.setVisible(true);
        jButton22.setVisible(true);
        jButton22.setEnabled(false);
    }
                break;
        
        
           case "Manager":
    jButton13.setEnabled(true);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(false);
        jButton12.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(false);
        jTextField3.setVisible(false);
        jButton23.setVisible(false);
        jButton22.setVisible(false);
        jButton22.setEnabled(false);
        jComboBox3.setEnabled(false);
    }
                break;
         case "Cashier":
            jButton13.setEnabled(true);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(false);
        jButton12.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(false);
        jTextField3.setVisible(false);
        jButton23.setVisible(false);
        jButton22.setVisible(false);
        jButton22.setEnabled(false);
        jComboBox3.setEnabled(false);
    }
                break;
               case "System Admin":
             jButton13.setEnabled(true);
        jButton2.setEnabled(false);
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(false);
        jButton12.setEnabled(false);
        jButton8.setEnabled(false);
        jButton1.setEnabled(true);
    if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){    
        jLabel4.setVisible(false);
        jTextField3.setVisible(false);
        jButton23.setVisible(false);
        jButton22.setVisible(false);
        jButton22.setEnabled(false);
        jComboBox3.setEnabled(false);
    }
                break;
        }
            
        jButton1.setEnabled(true);    
  
         
         
         
         
           }
        
        
        } if(me.getSource() == jTable1){  
                
                    int selectedRow =jTable1.getSelectedRow();
                    int selectedColumn =jTable1.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	
         
          Object cvalue1 = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 1);
          
          account=cvalue1.toString();
          
    jButton11.setEnabled(true);
         
           
           }
                   
          
         
       }
    
    
    }

    @Override
    public void mouseReleased(MouseEvent me) {
//           if(me.getSource() == jTable2){  
//                
//                    int selectedRow =jTable2.getSelectedRow();
//                    int selectedColumn =jTable2.getSelectedColumn();
//		   if (selectedRow > -1&&selectedColumn>-1)
//	   {
//        jButton1.setEnabled(false);
//        jButton2.setEnabled(false);
//         jButton5.setEnabled(false); 
//    }}
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
      
}

    @Override
    public void updateText(String text) {
      switch( fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"))){
        case 2000:jTextField3.setText(text);
           jButton22.setEnabled(true);
        
        break;
      
      
    }
    }



    
}  
 

 
  

 
