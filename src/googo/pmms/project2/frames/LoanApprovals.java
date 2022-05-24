package googo.pmms.project2.frames;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.SendSms;
import googo.pmms.project2.loanHelper.Amortization;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.ListTableModel;
import googo.pmms.project2.frameHelper.Resource;
import googo.pmms.project2.reportsHelper.BVAReport;
import googo.pmms.project2.reportsHelper.LoanSavingsSharesOthers;
import googo.pmms.project2.reportsHelper.OtherReportsPdf;
import googo.pmms.project2.reportsHelper.ReportsPDFFormulars;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stanchart
 */
public class LoanApprovals extends javax.swing.JFrame implements MouseListener,DocumentListener {
  String userId;
    loanDatabaseQuaries loan=new loanDatabaseQuaries();
   SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
    OtherReportsPdf demoothers = new OtherReportsPdf();
    DatabaseQuaries dbq =new DatabaseQuaries();
    LoanSavingsSharesOthers lsso=new LoanSavingsSharesOthers();
        fileInputOutPutStreams fios= new fileInputOutPutStreams();
         DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
         Formartter ffm= new Formartter();
        ReportsPDFFormulars statement= new ReportsPDFFormulars(); 
        BVAReport budget=new BVAReport();
        CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
 Formartter fmt = new Formartter();
List  amortDetails;
 
DecimalFormat df1 = new DecimalFormat("#.##");
DecimalFormat df2 = new DecimalFormat("#");

     Calendar cal = Calendar.getInstance();
    int n,s;
ArrayList<Object> data4;

ArrayList<String>  column1, data,data1 ;

 ArrayList<ArrayList<Object>> data5;
   double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0;
   Calendar startDate;
   ButtonGroup group1,group2,group3,group4;
   ObjectTableModel  model;
  
      SendSms sendsms= new SendSms(); 
    public LoanApprovals(String userId) {
        this.userId=userId;
        initComponents();
        
        java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
  
      this.setTitle("LOAN APPROVAL WINDOW-"+dbq.title(userId)); 
     jTree2.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       jTree2.addMouseListener( this);
       jTable1.addMouseListener(this);
       jTable1.setAutoscrolls(true);
jTable1.setRowHeight(30);
jTable1.setGridColor(Color.gray);
    jTable3.setAutoscrolls(true);
jTable3.setRowHeight(30);
jTable3.setGridColor(Color.gray);
    jTable4.setAutoscrolls(true);
jTable4.setRowHeight(30);
jTable4.setGridColor(Color.gray);
jTable4.addMouseListener(this);

      Resource rscs=new Resource(userId);
      
     switch(dbq.title(userId)){
   
            case "Supervisor":
                
                rscs.supervisorAccessRights(jTree2);
                this.processTableDetails();
                 break;
         case "Manager":
         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "7");     
             rscs.managerAccessRights(jTree2);
         this.processTableDetails2();
         
         
         break;
        }
    }
    
//    public void setUserID(String userid){
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

        jPanel1 = new javax.swing.JPanel();
        titledocumentationjLabel = new javax.swing.JLabel();
        titleCollateraljLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        refreshButton = new javax.swing.JButton();
        documentAuthorisationPanel = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        documentTitleJLabelCheckBox = new javax.swing.JLabel();
        document7JLabel = new javax.swing.JLabel();
        documentTitleJLabelJlabel = new javax.swing.JLabel();
        document8JLabel = new javax.swing.JLabel();
        document1TextField = new javax.swing.JTextField();
        document1JLabel = new javax.swing.JLabel();
        document2TextField = new javax.swing.JTextField();
        document2JLabel = new javax.swing.JLabel();
        document3TextField = new javax.swing.JTextField();
        document4JLabel = new javax.swing.JLabel();
        document3JLabel = new javax.swing.JLabel();
        document5JLabel = new javax.swing.JLabel();
        document6JLabel = new javax.swing.JLabel();
        document8TextField = new javax.swing.JTextField();
        document4TextField = new javax.swing.JTextField();
        document6TextField = new javax.swing.JTextField();
        document7TextField = new javax.swing.JTextField();
        document5TextField = new javax.swing.JTextField();
        ApproveDocumentButton = new javax.swing.JButton();
        document8CheckBoxTest = new javax.swing.JCheckBox();
        documentTitleJLabelTextField = new javax.swing.JLabel();
        document3CheckBoxTest = new javax.swing.JCheckBox();
        document4CheckBoxTest = new javax.swing.JCheckBox();
        document5CheckBoxTest = new javax.swing.JCheckBox();
        document6CheckBoxTest = new javax.swing.JCheckBox();
        document7CheckBoxTest = new javax.swing.JCheckBox();
        document1CheckBoxTest = new javax.swing.JCheckBox();
        document2CheckBoxTest = new javax.swing.JCheckBox();
        rejectDocumentButton = new javax.swing.JButton();
        homeDocumentButton = new javax.swing.JButton();
        loanDetailsDocumentButton = new javax.swing.JButton();
        collateralApprovalDocumentButton = new javax.swing.JButton();
        approveLoanDocumentButton = new javax.swing.JButton();
        collateralAuthorisationPanel = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        collateralType1JLabel = new javax.swing.JLabel();
        collateralNameTitleJLabelJlabel = new javax.swing.JLabel();
        collateralTitleJLabelCheckBox = new javax.swing.JLabel();
        collateralType2JLabel = new javax.swing.JLabel();
        collateralType4JLabel = new javax.swing.JLabel();
        collateralType8JLabel = new javax.swing.JLabel();
        collateralName1JLabel = new javax.swing.JLabel();
        collateralName2JLabel = new javax.swing.JLabel();
        collateralName3JLabel = new javax.swing.JLabel();
        collateralName5JLabel = new javax.swing.JLabel();
        collateralTypeTitleJLabelJlabel = new javax.swing.JLabel();
        collateralName6JLabel = new javax.swing.JLabel();
        collateralName7JLabel = new javax.swing.JLabel();
        collateralName8JLabel = new javax.swing.JLabel();
        collateralName4JLabel = new javax.swing.JLabel();
        collateral2TextField = new javax.swing.JTextField();
        collateral3TextField = new javax.swing.JTextField();
        collateral4TextField = new javax.swing.JTextField();
        collateral5TextField = new javax.swing.JTextField();
        collateral6TextField = new javax.swing.JTextField();
        collateral7TextField = new javax.swing.JTextField();
        collateral8TextField = new javax.swing.JTextField();
        collateralType5JLabel = new javax.swing.JLabel();
        collateralType6JLabel = new javax.swing.JLabel();
        collateralType7JLabel = new javax.swing.JLabel();
        collateralType3JLabel = new javax.swing.JLabel();
        collateral1TextField = new javax.swing.JTextField();
        ApproveCollateralButton = new javax.swing.JButton();
        collateralTitleJLabelTextField = new javax.swing.JLabel();
        collateral1CheckBoxTest = new javax.swing.JCheckBox();
        collateral8CheckBoxTest = new javax.swing.JCheckBox();
        collateral2CheckBoxTest = new javax.swing.JCheckBox();
        collateral3CheckBoxTest = new javax.swing.JCheckBox();
        collateral4CheckBoxTest = new javax.swing.JCheckBox();
        collateral5CheckBoxTest = new javax.swing.JCheckBox();
        collateral6CheckBoxTest = new javax.swing.JCheckBox();
        collateral7CheckBoxTest = new javax.swing.JCheckBox();
        rejectCollateralButton = new javax.swing.JButton();
        homeCollateralButton = new javax.swing.JButton();
        loanDetailsCollateralButton = new javax.swing.JButton();
        documentApprovaCollateralButton = new javax.swing.JButton();
        approveLoanCollateralButton = new javax.swing.JButton();
        loanDetailsAuthorisationPanel = new javax.swing.JPanel();
        authoriserIdjTextField1 = new javax.swing.JTextField();
        InterestRatejTextField = new javax.swing.JTextField();
        princempalAmountamountField = new javax.swing.JFormattedTextField();
        totalLoanAmountjLabel = new javax.swing.JLabel();
        theLoanInstalmentamountFieldDebit3 = new javax.swing.JFormattedTextField();
        theLoanInstalmentjLabel6 = new javax.swing.JLabel();
        totalLoanAmountamountFieldDebit4 = new javax.swing.JFormattedTextField();
        instalmentStaerDatejTextField3 = new javax.swing.JTextField();
        instalmentEndDatejLabel7 = new javax.swing.JLabel();
        borrowerAccountNamejTextField4 = new javax.swing.JTextField();
        InterestRatejLabel = new javax.swing.JLabel();
        instalmentEndDatejTextField5 = new javax.swing.JTextField();
        loanTenurejLabel9 = new javax.swing.JLabel();
        loanTenurejTextField6 = new javax.swing.JTextField();
        loanIDjLabel1a0 = new javax.swing.JLabel();
        borrowerAccountNamejLabel11 = new javax.swing.JLabel();
        loanCyclejTextField9 = new javax.swing.JTextField();
        authoriserIdjLabel12 = new javax.swing.JLabel();
        princempalAmountjLabel = new javax.swing.JLabel();
        totalInterestAmountjLabel14 = new javax.swing.JLabel();
        noOfIndtalmentsjLabel = new javax.swing.JLabel();
        noOfIndtalmentsjTextField = new javax.swing.JTextField();
        loanCycleStatusjLabel16 = new javax.swing.JLabel();
        InputterIdjTextField10 = new javax.swing.JTextField();
        loanSerialNumberjLabel17 = new javax.swing.JLabel();
        inputterIDjLabel18 = new javax.swing.JLabel();
        loanSerialNumberjTextField11 = new javax.swing.JTextField();
        loanIDjTextField12 = new javax.swing.JTextField();
        instalmentStartDatejLabel19 = new javax.swing.JLabel();
        totalInterestamountFieldDebit5 = new javax.swing.JFormattedTextField();
        ApproveLoanDetailsButton = new javax.swing.JButton();
        rejectLoanDetailsButton = new javax.swing.JButton();
        documentApprovalLoanDetailsButton = new javax.swing.JButton();
        collateralApprovalLoanDetailsButton = new javax.swing.JButton();
        homeLoanDetailsButton = new javax.swing.JButton();
        approveLoanLoanDetailsButton = new javax.swing.JButton();
        homeAuthorisationTablePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        loanDetailsButtonHome = new javax.swing.JButton();
        loanDocumentaionjButtonHome = new javax.swing.JButton();
        loanCollateraljButtonHome = new javax.swing.JButton();
        loanDetailsRejectionPanel = new javax.swing.JPanel();
        OkRejectLoanDetailsCommentsjButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RejectLoanDetailsCommentsjTextArea = new javax.swing.JTextArea();
        StatementRejectjLabel4 = new javax.swing.JLabel();
        ReturnRejectLoanDetailsCommentsjButton = new javax.swing.JButton();
        SaveRejectLoanDetailsCommentsjButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        titleloanDetailsjLabel = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        titleloanDetailsjLabel1 = new javax.swing.JLabel();
        loanDetailsAuthorisationPanel1 = new javax.swing.JPanel();
        InterestRatejTextField1 = new javax.swing.JTextField();
        princempalAmountamountField1 = new javax.swing.JFormattedTextField();
        totalLoanAmountjLabel1 = new javax.swing.JLabel();
        totalLoanAmountamountFieldDebit5 = new javax.swing.JFormattedTextField();
        instalmentStaerDatejTextField4 = new javax.swing.JTextField();
        instalmentEndDatejLabel8 = new javax.swing.JLabel();
        InterestRatejLabel1 = new javax.swing.JLabel();
        instalmentEndDatejTextField6 = new javax.swing.JTextField();
        loanTenurejTextField7 = new javax.swing.JTextField();
        princempalAmountjLabel1 = new javax.swing.JLabel();
        noOfIndtalmentsjLabel1 = new javax.swing.JLabel();
        noOfIndtalmentsjTextField1 = new javax.swing.JTextField();
        instalmentStartDatejLabel20 = new javax.swing.JLabel();
        documentApprovalLoanDetailsButton1 = new javax.swing.JButton();
        collateralApprovalLoanDetailsButton1 = new javax.swing.JButton();
        homeLoanDetailsButton1 = new javax.swing.JButton();
        loanTenurejLabel10 = new javax.swing.JLabel();
        totalInterestAmountjLabel15 = new javax.swing.JLabel();
        totalInterestamountFieldDebit6 = new javax.swing.JFormattedTextField();
        theLoanInstalmentjLabel7 = new javax.swing.JLabel();
        theLoanInstalmentamountFieldDebit4 = new javax.swing.JFormattedTextField();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(32767, 32767));
        jPanel1.setLayout(null);

        titledocumentationjLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titledocumentationjLabel.setForeground(java.awt.SystemColor.controlLtHighlight);
        titledocumentationjLabel.setText("Loan documentation");
        titledocumentationjLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(titledocumentationjLabel);
        titledocumentationjLabel.setBounds(990, 30, 10, 10);
        titledocumentationjLabel.setVisible(false);

        titleCollateraljLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titleCollateraljLabel.setForeground(java.awt.SystemColor.controlLtHighlight);
        titleCollateraljLabel.setText("Loan Authorisation");
        titleCollateraljLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(titleCollateraljLabel);
        titleCollateraljLabel.setBounds(160, 0, 990, 30);

        jTree2.setBackground(java.awt.SystemColor.activeCaption);
        jTree2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaption, null));
        jTree2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTree2.setForeground(new java.awt.Color(255, 255, 255));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Log Out");
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(0, 0, 160, 620);

        refreshButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        jPanel1.add(refreshButton);
        refreshButton.setBounds(490, 580, 200, 40);

        documentAuthorisationPanel.setBackground(java.awt.SystemColor.activeCaption);
        documentAuthorisationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        documentAuthorisationPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                documentAuthorisationPanelMouseEntered(evt);
            }
        });
        documentAuthorisationPanel.setLayout(null);

        jButton6.setText("APPROVE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(jButton6);
        jButton6.setBounds(250, 600, 130, 23);

        documentTitleJLabelCheckBox.setText("Test Document Validity");
        documentAuthorisationPanel.add(documentTitleJLabelCheckBox);
        documentTitleJLabelCheckBox.setBounds(650, 20, 140, 30);

        document7JLabel.setForeground(new java.awt.Color(0, 102, 102));
        document7JLabel.setText("Required document");
        document7JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document7JLabel);
        document7JLabel.setBounds(20, 300, 470, 30);
        document7JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument7.txt")));

        document7JLabel.setVisible(false);

        documentTitleJLabelJlabel.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        documentTitleJLabelJlabel.setText("Required document name");
        documentAuthorisationPanel.add(documentTitleJLabelJlabel);
        documentTitleJLabelJlabel.setBounds(60, 20, 150, 30);

        document8JLabel.setForeground(new java.awt.Color(0, 0, 255));
        document8JLabel.setText("Required callaterel type");
        document8JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document8JLabel);
        document8JLabel.setBounds(20, 350, 470, 30);
        document8JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument8.txt")));

        document8JLabel.setVisible(false);
        documentAuthorisationPanel.add(document1TextField);
        document1TextField.setBounds(520, 60, 110, 30);
        document1TextField.setVisible(false);
        document1TextField.setText("");

        document1JLabel.setForeground(new java.awt.Color(0, 102, 102));
        document1JLabel.setText("Required document");
        document1JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document1JLabel);
        document1JLabel.setBounds(20, 60, 470, 30);
        document1JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument1.txt")));

        document1JLabel.setVisible(false);
        documentAuthorisationPanel.add(document2TextField);
        document2TextField.setBounds(520, 100, 110, 30);
        document2TextField.setVisible(false);
        document2TextField.setText("");

        document2JLabel.setForeground(new java.awt.Color(0, 0, 255));
        document2JLabel.setText("Required callaterel type");
        document2JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document2JLabel);
        document2JLabel.setBounds(20, 100, 470, 30);
        document2JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument2.txt")));

        document2JLabel.setVisible(false);
        documentAuthorisationPanel.add(document3TextField);
        document3TextField.setBounds(520, 140, 110, 30);
        document3TextField.setVisible(false);
        document3TextField.setText("");

        document4JLabel.setForeground(new java.awt.Color(0, 0, 255));
        document4JLabel.setText("Required callaterel type");
        document4JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document4JLabel);
        document4JLabel.setBounds(20, 180, 470, 30);
        document4JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument4.txt")));

        document4JLabel.setVisible(false);

        document3JLabel.setForeground(new java.awt.Color(0, 102, 102));
        document3JLabel.setText("Required document");
        document3JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document3JLabel);
        document3JLabel.setBounds(20, 140, 470, 30);
        document3JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument3.txt")));

        document3JLabel.setVisible(false);

        document5JLabel.setForeground(new java.awt.Color(0, 102, 102));
        document5JLabel.setText("Required document");
        document5JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document5JLabel);
        document5JLabel.setBounds(20, 220, 470, 30);
        document5JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument5.txt")));

        document5JLabel.setVisible(false);

        document6JLabel.setForeground(new java.awt.Color(0, 0, 255));
        document6JLabel.setText("Required callaterel type");
        document6JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        documentAuthorisationPanel.add(document6JLabel);
        document6JLabel.setBounds(20, 260, 470, 30);
        document6JLabel.setText(fios.stringFileReader(fios.createFileName("persistence","loanDocumentation", "actualDocument6.txt")));

        document6JLabel.setVisible(false);
        documentAuthorisationPanel.add(document8TextField);
        document8TextField.setBounds(520, 350, 110, 30);
        document8TextField.setVisible(false);
        document8TextField .setText("");
        documentAuthorisationPanel.add(document4TextField);
        document4TextField.setBounds(520, 180, 110, 30);
        document4TextField.setVisible(false);
        document4TextField.setText("");
        documentAuthorisationPanel.add(document6TextField);
        document6TextField.setBounds(520, 260, 110, 30);
        document6TextField.setVisible(false);
        document6TextField.setText("");
        documentAuthorisationPanel.add(document7TextField);
        document7TextField.setBounds(520, 300, 110, 30);
        document7TextField.setVisible(false);
        document7TextField.setText("");
        documentAuthorisationPanel.add(document5TextField);
        document5TextField.setBounds(520, 220, 110, 30);
        document5TextField .setVisible(false);
        document5TextField .setText("");

        ApproveDocumentButton.setText("APPROVE  DOCUMENT");
        ApproveDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApproveDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(ApproveDocumentButton);
        ApproveDocumentButton.setBounds(120, 410, 180, 30);

        document8CheckBoxTest.setText("Check Document 8");
        document8CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document8CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document8CheckBoxTest);
        document8CheckBoxTest.setBounds(640, 350, 140, 30);
        document8CheckBoxTest .setVisible(false);

        documentTitleJLabelTextField.setText("Document Serial Number");
        documentAuthorisationPanel.add(documentTitleJLabelTextField);
        documentTitleJLabelTextField.setBounds(490, 20, 150, 30);

        document3CheckBoxTest.setText("Check Document 3");
        document3CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document3CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document3CheckBoxTest);
        document3CheckBoxTest.setBounds(640, 140, 140, 30);
        document3CheckBoxTest.setVisible(false);

        document4CheckBoxTest.setText("Check Document 4");
        document4CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document4CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document4CheckBoxTest);
        document4CheckBoxTest.setBounds(640, 180, 140, 30);
        document4CheckBoxTest.setVisible(false);

        document5CheckBoxTest.setText("Check Document 5");
        document5CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document5CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document5CheckBoxTest);
        document5CheckBoxTest.setBounds(640, 220, 140, 30);
        document5CheckBoxTest.setVisible(false);

        document6CheckBoxTest.setText("Check Document 6");
        document6CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document6CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document6CheckBoxTest);
        document6CheckBoxTest.setBounds(640, 260, 140, 30);
        document6CheckBoxTest.setVisible(false);

        document7CheckBoxTest.setText("Check Document 7");
        document7CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document7CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document7CheckBoxTest);
        document7CheckBoxTest.setBounds(640, 300, 140, 30);
        document7CheckBoxTest .setVisible(false);

        document1CheckBoxTest.setText("Check Document 1");
        document1CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document1CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document1CheckBoxTest);
        document1CheckBoxTest.setBounds(640, 60, 140, 30);
        document1CheckBoxTest.setVisible(false);

        document2CheckBoxTest.setText("Check Document 2");
        document2CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                document2CheckBoxTestActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(document2CheckBoxTest);
        document2CheckBoxTest.setBounds(640, 100, 140, 30);
        document2CheckBoxTest.setVisible(false);

        rejectDocumentButton.setText("REJECT DOCUMENT");
        rejectDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(rejectDocumentButton);
        rejectDocumentButton.setBounds(300, 410, 160, 30);

        homeDocumentButton.setText("home");
        homeDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(homeDocumentButton);
        homeDocumentButton.setBounds(120, 440, 160, 23);

        loanDetailsDocumentButton.setText("loan details");
        loanDetailsDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanDetailsDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(loanDetailsDocumentButton);
        loanDetailsDocumentButton.setBounds(280, 440, 170, 23);

        collateralApprovalDocumentButton.setText("coll approval");
        collateralApprovalDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateralApprovalDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(collateralApprovalDocumentButton);
        collateralApprovalDocumentButton.setBounds(450, 440, 170, 23);

        approveLoanDocumentButton.setText("APPROVE/REJECT");
        approveLoanDocumentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveLoanDocumentButtonActionPerformed(evt);
            }
        });
        documentAuthorisationPanel.add(approveLoanDocumentButton);
        approveLoanDocumentButton.setBounds(460, 410, 160, 30);
        approveLoanDocumentButton.setEnabled(false);

        jPanel1.add(documentAuthorisationPanel);
        documentAuthorisationPanel.setBounds(960, 10, 10, 10);
        documentAuthorisationPanel.setVisible(false);

        collateralAuthorisationPanel.setBackground(java.awt.SystemColor.activeCaption);
        collateralAuthorisationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        collateralAuthorisationPanel.setLayout(null);

        jButton8.setText("APPROVE");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(jButton8);
        jButton8.setBounds(250, 600, 130, 23);

        collateralType1JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralType1JLabel.setText("Required callaterel type");
        collateralType1JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType1JLabel);
        collateralType1JLabel.setBounds(10, 80, 140, 30);
        collateralType1JLabel  .setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType1.txt")));
        collateralType1JLabel.setVisible(false);

        collateralNameTitleJLabelJlabel.setText("Required callaterel name");
        collateralAuthorisationPanel.add(collateralNameTitleJLabelJlabel);
        collateralNameTitleJLabelJlabel.setBounds(190, 30, 150, 30);

        collateralTitleJLabelCheckBox.setText("Test Validity");
        collateralAuthorisationPanel.add(collateralTitleJLabelCheckBox);
        collateralTitleJLabelCheckBox.setBounds(650, 30, 100, 30);

        collateralType2JLabel.setForeground(new java.awt.Color(0, 0, 255));
        collateralType2JLabel.setText("Required callaterel type");
        collateralType2JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType2JLabel);
        collateralType2JLabel.setBounds(10, 120, 140, 30);
        collateralType2JLabel  .setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType2.txt")));
        collateralType2JLabel.setVisible(false);

        collateralType4JLabel.setForeground(new java.awt.Color(0, 0, 255));
        collateralType4JLabel.setText("Required callaterel type");
        collateralType4JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType4JLabel);
        collateralType4JLabel.setBounds(10, 200, 140, 30);
        collateralType4JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType4.txt")));
        collateralType4JLabel .setVisible(false);

        collateralType8JLabel.setForeground(new java.awt.Color(0, 0, 204));
        collateralType8JLabel.setText("Required callaterel type");
        collateralType8JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType8JLabel);
        collateralType8JLabel.setBounds(10, 360, 140, 30);
        collateralType8JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType8.txt")));
        collateralType8JLabel.setVisible(false);

        collateralName1JLabel.setBackground(java.awt.SystemColor.controlLtHighlight);
        collateralName1JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralName1JLabel.setText("Required callaterel type");
        collateralName1JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName1JLabel);
        collateralName1JLabel.setBounds(170, 80, 340, 30);
        collateralName1JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName1.txt")));
        collateralName1JLabel.setVisible(false);

        collateralName2JLabel.setForeground(new java.awt.Color(0, 0, 204));
        collateralName2JLabel.setText("Required callaterel type");
        collateralName2JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName2JLabel);
        collateralName2JLabel.setBounds(170, 120, 340, 30);
        collateralName2JLabel .setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName2.txt")));
        collateralName2JLabel .setVisible(false);

        collateralName3JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralName3JLabel.setText("Required callaterel type");
        collateralName3JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName3JLabel);
        collateralName3JLabel.setBounds(170, 160, 340, 30);
        collateralName3JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName3.txt")));
        collateralName3JLabel .setVisible(false);

        collateralName5JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralName5JLabel.setText("Required callaterel type");
        collateralName5JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName5JLabel);
        collateralName5JLabel.setBounds(170, 240, 340, 30);
        collateralName5JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName5.txt")));
        collateralName5JLabel .setVisible(false);

        collateralTypeTitleJLabelJlabel.setText("Required callaterel type");
        collateralAuthorisationPanel.add(collateralTypeTitleJLabelJlabel);
        collateralTypeTitleJLabelJlabel.setBounds(10, 30, 150, 30);

        collateralName6JLabel.setForeground(new java.awt.Color(0, 0, 204));
        collateralName6JLabel.setText("Required callaterel type");
        collateralName6JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName6JLabel);
        collateralName6JLabel.setBounds(170, 280, 340, 30);
        collateralName6JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName6.txt")));
        collateralName6JLabel .setVisible(false);

        collateralName7JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralName7JLabel.setText("Required callaterel type");
        collateralName7JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName7JLabel);
        collateralName7JLabel.setBounds(170, 320, 340, 30);
        collateralName7JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName7.txt")));
        collateralName7JLabel .setVisible(false);

        collateralName8JLabel.setForeground(new java.awt.Color(0, 0, 204));
        collateralName8JLabel.setText("Required callaterel type");
        collateralName8JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName8JLabel);
        collateralName8JLabel.setBounds(170, 360, 340, 30);
        collateralName8JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName8.txt")));
        collateralName8JLabel.setVisible(false);

        collateralName4JLabel.setForeground(new java.awt.Color(0, 0, 204));
        collateralName4JLabel.setText("Required callaterel type");
        collateralName4JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralName4JLabel);
        collateralName4JLabel.setBounds(170, 200, 340, 30);
        collateralName4JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralName4.txt")));
        collateralName4JLabel.setVisible(false);
        collateralAuthorisationPanel.add(collateral2TextField);
        collateral2TextField.setBounds(530, 120, 110, 30);
        collateral2TextField.setVisible(false);
        collateral2TextField.setText("");
        collateralAuthorisationPanel.add(collateral3TextField);
        collateral3TextField.setBounds(530, 160, 110, 30);
        collateral3TextField .setVisible(false);
        collateral3TextField.setText("");
        collateralAuthorisationPanel.add(collateral4TextField);
        collateral4TextField.setBounds(530, 200, 110, 30);
        collateral4TextField .setVisible(false);
        collateral4TextField.setText("");
        collateralAuthorisationPanel.add(collateral5TextField);
        collateral5TextField.setBounds(530, 240, 110, 30);
        collateral5TextField.setVisible(false);
        collateral5TextField.setText("");
        collateralAuthorisationPanel.add(collateral6TextField);
        collateral6TextField.setBounds(530, 280, 110, 30);
        collateral6TextField .setVisible(false);
        collateral6TextField .setText("");
        collateralAuthorisationPanel.add(collateral7TextField);
        collateral7TextField.setBounds(530, 320, 110, 30);
        collateral7TextField .setVisible(false);
        collateral7TextField .setText("");
        collateralAuthorisationPanel.add(collateral8TextField);
        collateral8TextField.setBounds(530, 360, 110, 30);
        collateral8TextField .setVisible(false);
        collateral8TextField.setText("");

        collateralType5JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralType5JLabel.setText("Required callaterel type");
        collateralType5JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType5JLabel);
        collateralType5JLabel.setBounds(10, 240, 140, 30);
        collateralType5JLabel .setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType5.txt")));
        collateralType5JLabel.setVisible(false);

        collateralType6JLabel.setForeground(new java.awt.Color(0, 0, 255));
        collateralType6JLabel.setText("Required callaterel type");
        collateralType6JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType6JLabel);
        collateralType6JLabel.setBounds(10, 280, 140, 30);
        collateralType6JLabel.setVisible(false);

        collateralType6JLabel .setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType6.txt")));

        collateralType7JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralType7JLabel.setText("Required callaterel type");
        collateralType7JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType7JLabel);
        collateralType7JLabel.setBounds(10, 320, 140, 30);
        collateralType7JLabel.setVisible(false);
        collateralType7JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType7.txt")));

        collateralType3JLabel.setForeground(new java.awt.Color(0, 102, 102));
        collateralType3JLabel.setText("Required callaterel type");
        collateralType3JLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        collateralAuthorisationPanel.add(collateralType3JLabel);
        collateralType3JLabel.setBounds(10, 160, 140, 30);
        collateralType3JLabel.setText(fios.stringFileReader(fios.createFileName("persistence", "collateralSecurity", "collateralType3.txt")));
        collateralType3JLabel.setVisible(false);
        collateralAuthorisationPanel.add(collateral1TextField);
        collateral1TextField.setBounds(530, 80, 110, 30);
        collateral1TextField.setVisible(false);
        collateral1TextField.setText("");

        ApproveCollateralButton.setText("APPROVE COLLATERAL");
        ApproveCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApproveCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(ApproveCollateralButton);
        ApproveCollateralButton.setBounds(130, 410, 190, 30);

        collateralTitleJLabelTextField.setText("Evidance of  Ownership");
        collateralAuthorisationPanel.add(collateralTitleJLabelTextField);
        collateralTitleJLabelTextField.setBounds(520, 30, 130, 30);

        collateral1CheckBoxTest.setText("Check  1");
        collateral1CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral1CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral1CheckBoxTest);
        collateral1CheckBoxTest.setBounds(650, 80, 80, 23);
        collateral1CheckBoxTest .setVisible(false);

        collateral8CheckBoxTest.setText("Check l 8");
        collateral8CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral8CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral8CheckBoxTest);
        collateral8CheckBoxTest.setBounds(650, 370, 80, 23);
        collateral8CheckBoxTest .setVisible(false);

        collateral2CheckBoxTest.setText("Checkl 2");
        collateral2CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral2CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral2CheckBoxTest);
        collateral2CheckBoxTest.setBounds(650, 120, 80, 23);
        collateral2CheckBoxTest.setVisible(false);

        collateral3CheckBoxTest.setText("Check 3");
        collateral3CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral3CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral3CheckBoxTest);
        collateral3CheckBoxTest.setBounds(650, 170, 80, 23);
        collateral3CheckBoxTest.setVisible(false);

        collateral4CheckBoxTest.setText("Check 4");
        collateral4CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral4CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral4CheckBoxTest);
        collateral4CheckBoxTest.setBounds(650, 210, 80, 23);
        collateral4CheckBoxTest.setVisible(false);

        collateral5CheckBoxTest.setText("Check 5");
        collateral5CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral5CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral5CheckBoxTest);
        collateral5CheckBoxTest.setBounds(650, 250, 70, 23);
        collateral5CheckBoxTest.setVisible(false);

        collateral6CheckBoxTest.setText("Check  6");
        collateral6CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral6CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral6CheckBoxTest);
        collateral6CheckBoxTest.setBounds(650, 290, 80, 23);
        collateral6CheckBoxTest.setVisible(false);

        collateral7CheckBoxTest.setText("Check  7");
        collateral7CheckBoxTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateral7CheckBoxTestActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(collateral7CheckBoxTest);
        collateral7CheckBoxTest.setBounds(650, 330, 80, 23);
        collateral7CheckBoxTest.setVisible(false);

        rejectCollateralButton.setText("REJECT COLLATERAL");
        rejectCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(rejectCollateralButton);
        rejectCollateralButton.setBounds(320, 410, 160, 30);

        homeCollateralButton.setText("home");
        homeCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(homeCollateralButton);
        homeCollateralButton.setBounds(130, 440, 170, 23);

        loanDetailsCollateralButton.setText("loan details");
        loanDetailsCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanDetailsCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(loanDetailsCollateralButton);
        loanDetailsCollateralButton.setBounds(300, 440, 170, 23);

        documentApprovaCollateralButton.setText("doc details");
        documentApprovaCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentApprovaCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(documentApprovaCollateralButton);
        documentApprovaCollateralButton.setBounds(470, 440, 170, 23);

        approveLoanCollateralButton.setText("APPROVE/REJECT");
        approveLoanCollateralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveLoanCollateralButtonActionPerformed(evt);
            }
        });
        collateralAuthorisationPanel.add(approveLoanCollateralButton);
        approveLoanCollateralButton.setBounds(480, 410, 160, 30);
        approveLoanCollateralButton.setEnabled(false);

        jPanel1.add(collateralAuthorisationPanel);
        collateralAuthorisationPanel.setBounds(970, 10, 10, 10);
        collateralAuthorisationPanel.setVisible(false);

        loanDetailsAuthorisationPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanDetailsAuthorisationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel.setLayout(null);

        authoriserIdjTextField1.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(authoriserIdjTextField1);
        authoriserIdjTextField1.setBounds(650, 340, 110, 30);

        InterestRatejTextField.setForeground(new java.awt.Color(0, 102, 51));
        loanDetailsAuthorisationPanel.add(InterestRatejTextField);
        InterestRatejTextField.setBounds(650, 50, 110, 30);

        princempalAmountamountField.setForeground(new java.awt.Color(0, 102, 51));
        princempalAmountamountField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        princempalAmountamountField.setValue(null);
        princempalAmountamountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                princempalAmountamountFieldActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(princempalAmountamountField);
        princempalAmountamountField.setBounds(410, 50, 100, 30);

        totalLoanAmountjLabel.setText("TOTAL LOAN AMNT");
        loanDetailsAuthorisationPanel.add(totalLoanAmountjLabel);
        totalLoanAmountjLabel.setBounds(10, 120, 120, 30);

        theLoanInstalmentamountFieldDebit3.setForeground(new java.awt.Color(0, 102, 51));
        theLoanInstalmentamountFieldDebit3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        theLoanInstalmentamountFieldDebit3.setValue(null);
        theLoanInstalmentamountFieldDebit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theLoanInstalmentamountFieldDebit3ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(theLoanInstalmentamountFieldDebit3);
        theLoanInstalmentamountFieldDebit3.setBounds(650, 120, 110, 30);

        theLoanInstalmentjLabel6.setText("THE LOAN INST. AMNT");
        loanDetailsAuthorisationPanel.add(theLoanInstalmentjLabel6);
        theLoanInstalmentjLabel6.setBounds(520, 120, 120, 30);

        totalLoanAmountamountFieldDebit4.setForeground(new java.awt.Color(0, 102, 51));
        totalLoanAmountamountFieldDebit4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        totalLoanAmountamountFieldDebit4.setValue(null);
        totalLoanAmountamountFieldDebit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalLoanAmountamountFieldDebit4ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(totalLoanAmountamountFieldDebit4);
        totalLoanAmountamountFieldDebit4.setBounds(140, 120, 110, 30);

        instalmentStaerDatejTextField3.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(instalmentStaerDatejTextField3);
        instalmentStaerDatejTextField3.setBounds(140, 190, 110, 30);

        instalmentEndDatejLabel7.setForeground(new java.awt.Color(0, 51, 204));
        instalmentEndDatejLabel7.setText("INST. END DATE");
        loanDetailsAuthorisationPanel.add(instalmentEndDatejLabel7);
        instalmentEndDatejLabel7.setBounds(260, 190, 120, 30);

        borrowerAccountNamejTextField4.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(borrowerAccountNamejTextField4);
        borrowerAccountNamejTextField4.setBounds(140, 340, 160, 30);

        InterestRatejLabel.setForeground(new java.awt.Color(0, 0, 204));
        InterestRatejLabel.setText("INTEREST RATE");
        loanDetailsAuthorisationPanel.add(InterestRatejLabel);
        InterestRatejLabel.setBounds(520, 50, 120, 30);

        instalmentEndDatejTextField5.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(instalmentEndDatejTextField5);
        instalmentEndDatejTextField5.setBounds(410, 190, 100, 30);

        loanTenurejLabel9.setForeground(new java.awt.Color(0, 51, 204));
        loanTenurejLabel9.setText("LOAN TENURE");
        loanDetailsAuthorisationPanel.add(loanTenurejLabel9);
        loanTenurejLabel9.setBounds(520, 190, 120, 30);

        loanTenurejTextField6.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(loanTenurejTextField6);
        loanTenurejTextField6.setBounds(650, 190, 110, 30);

        loanIDjLabel1a0.setText("LOAN ID");
        loanDetailsAuthorisationPanel.add(loanIDjLabel1a0);
        loanIDjLabel1a0.setBounds(260, 260, 90, 30);

        borrowerAccountNamejLabel11.setForeground(new java.awt.Color(0, 51, 204));
        borrowerAccountNamejLabel11.setText("BORROWER A/C  NAME");
        loanDetailsAuthorisationPanel.add(borrowerAccountNamejLabel11);
        borrowerAccountNamejLabel11.setBounds(10, 340, 120, 30);

        loanCyclejTextField9.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(loanCyclejTextField9);
        loanCyclejTextField9.setBounds(650, 260, 110, 30);

        authoriserIdjLabel12.setForeground(new java.awt.Color(0, 51, 204));
        authoriserIdjLabel12.setText("AUTHORISER  ID");
        loanDetailsAuthorisationPanel.add(authoriserIdjLabel12);
        authoriserIdjLabel12.setBounds(530, 340, 110, 30);

        princempalAmountjLabel.setForeground(new java.awt.Color(0, 0, 204));
        princempalAmountjLabel.setText("PRINCIMPAL AMNT");
        loanDetailsAuthorisationPanel.add(princempalAmountjLabel);
        princempalAmountjLabel.setBounds(270, 50, 110, 30);

        totalInterestAmountjLabel14.setText("TOTAL INTEREST AMNT");
        loanDetailsAuthorisationPanel.add(totalInterestAmountjLabel14);
        totalInterestAmountjLabel14.setBounds(260, 120, 140, 30);

        noOfIndtalmentsjLabel.setForeground(new java.awt.Color(0, 0, 204));
        noOfIndtalmentsjLabel.setText("NO. OF  INSTS");
        loanDetailsAuthorisationPanel.add(noOfIndtalmentsjLabel);
        noOfIndtalmentsjLabel.setBounds(10, 50, 120, 30);

        noOfIndtalmentsjTextField.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(noOfIndtalmentsjTextField);
        noOfIndtalmentsjTextField.setBounds(140, 50, 110, 30);

        loanCycleStatusjLabel16.setText("LOAN CYCLE STATUS");
        loanDetailsAuthorisationPanel.add(loanCycleStatusjLabel16);
        loanCycleStatusjLabel16.setBounds(520, 260, 120, 30);

        InputterIdjTextField10.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(InputterIdjTextField10);
        InputterIdjTextField10.setBounds(420, 340, 100, 30);

        loanSerialNumberjLabel17.setText("LOAN S/N");
        loanDetailsAuthorisationPanel.add(loanSerialNumberjLabel17);
        loanSerialNumberjLabel17.setBounds(10, 260, 90, 30);

        inputterIDjLabel18.setForeground(new java.awt.Color(0, 51, 204));
        inputterIDjLabel18.setText("IN PUTTER ID");
        loanDetailsAuthorisationPanel.add(inputterIDjLabel18);
        inputterIDjLabel18.setBounds(310, 340, 100, 30);

        loanSerialNumberjTextField11.setForeground(new java.awt.Color(0, 102, 51));
        loanDetailsAuthorisationPanel.add(loanSerialNumberjTextField11);
        loanSerialNumberjTextField11.setBounds(140, 260, 110, 30);

        loanIDjTextField12.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel.add(loanIDjTextField12);
        loanIDjTextField12.setBounds(350, 260, 160, 30);

        instalmentStartDatejLabel19.setForeground(new java.awt.Color(0, 51, 204));
        instalmentStartDatejLabel19.setText("INST. START DATE");
        loanDetailsAuthorisationPanel.add(instalmentStartDatejLabel19);
        instalmentStartDatejLabel19.setBounds(10, 190, 110, 30);

        totalInterestamountFieldDebit5.setForeground(new java.awt.Color(0, 102, 51));
        totalInterestamountFieldDebit5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        totalInterestamountFieldDebit5.setValue(null);
        totalInterestamountFieldDebit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalInterestamountFieldDebit5ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(totalInterestamountFieldDebit5);
        totalInterestamountFieldDebit5.setBounds(410, 120, 100, 30);

        ApproveLoanDetailsButton.setText("Approve Loan Details");
        ApproveLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApproveLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(ApproveLoanDetailsButton);
        ApproveLoanDetailsButton.setBounds(190, 390, 160, 30);

        rejectLoanDetailsButton.setText("Reject Loan Details");
        rejectLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(rejectLoanDetailsButton);
        rejectLoanDetailsButton.setBounds(350, 390, 150, 30);

        documentApprovalLoanDetailsButton.setText("doc approval");
        documentApprovalLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentApprovalLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(documentApprovalLoanDetailsButton);
        documentApprovalLoanDetailsButton.setBounds(500, 420, 160, 23);

        collateralApprovalLoanDetailsButton.setText("coll approval");
        collateralApprovalLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateralApprovalLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(collateralApprovalLoanDetailsButton);
        collateralApprovalLoanDetailsButton.setBounds(350, 420, 150, 23);

        homeLoanDetailsButton.setBackground(new java.awt.Color(0, 102, 102));
        homeLoanDetailsButton.setForeground(new java.awt.Color(0, 0, 204));
        homeLoanDetailsButton.setText("home");
        homeLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(homeLoanDetailsButton);
        homeLoanDetailsButton.setBounds(190, 420, 160, 23);

        approveLoanLoanDetailsButton.setText("APPROVE/REJECT");
        approveLoanLoanDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveLoanLoanDetailsButtonActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel.add(approveLoanLoanDetailsButton);
        approveLoanLoanDetailsButton.setBounds(500, 390, 160, 30);
        approveLoanLoanDetailsButton.setEnabled(false);

        jPanel1.add(loanDetailsAuthorisationPanel);
        loanDetailsAuthorisationPanel.setBounds(1000, 10, 10, 10);
        loanDetailsAuthorisationPanel.setVisible(false);

        homeAuthorisationTablePanel.setBackground(java.awt.SystemColor.activeCaption);
        homeAuthorisationTablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        homeAuthorisationTablePanel.setLayout(null);

        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable2.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable2);

        homeAuthorisationTablePanel.add(jScrollPane4);
        jScrollPane4.setBounds(20, 30, 740, 370);

        loanDetailsButtonHome.setText("loan details");
        loanDetailsButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanDetailsButtonHomeActionPerformed(evt);
            }
        });
        homeAuthorisationTablePanel.add(loanDetailsButtonHome);
        loanDetailsButtonHome.setBounds(230, 430, 150, 23);
        loanDetailsButtonHome.setEnabled(false);

        loanDocumentaionjButtonHome.setText("doc approval");
        loanDocumentaionjButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanDocumentaionjButtonHomeActionPerformed(evt);
            }
        });
        homeAuthorisationTablePanel.add(loanDocumentaionjButtonHome);
        loanDocumentaionjButtonHome.setBounds(530, 430, 140, 23);
        loanDocumentaionjButtonHome.setEnabled(false);

        loanCollateraljButtonHome.setText("coll approval");
        loanCollateraljButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanCollateraljButtonHomeActionPerformed(evt);
            }
        });
        homeAuthorisationTablePanel.add(loanCollateraljButtonHome);
        loanCollateraljButtonHome.setBounds(380, 430, 150, 23);
        loanCollateraljButtonHome.setEnabled(false);

        jPanel1.add(homeAuthorisationTablePanel);
        homeAuthorisationTablePanel.setBounds(990, 10, 10, 10);
        homeAuthorisationTablePanel.setVisible(false);

        loanDetailsRejectionPanel.setLayout(null);

        OkRejectLoanDetailsCommentsjButton.setText("OK");
        OkRejectLoanDetailsCommentsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkRejectLoanDetailsCommentsjButtonActionPerformed(evt);
            }
        });
        loanDetailsRejectionPanel.add(OkRejectLoanDetailsCommentsjButton);
        OkRejectLoanDetailsCommentsjButton.setBounds(260, 310, 47, 23);

        RejectLoanDetailsCommentsjTextArea.setColumns(20);
        RejectLoanDetailsCommentsjTextArea.setForeground(new java.awt.Color(0, 0, 255));
        RejectLoanDetailsCommentsjTextArea.setRows(5);
        jScrollPane1.setViewportView(RejectLoanDetailsCommentsjTextArea);
        RejectLoanDetailsCommentsjTextArea.setLineWrap(true);
        RejectLoanDetailsCommentsjTextArea.setWrapStyleWord(true);

        String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.

        String rejectComments=fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "rejectedComments"+activeAccount+".txt"));

        RejectLoanDetailsCommentsjTextArea.setText(rejectComments);

        loanDetailsRejectionPanel.add(jScrollPane1);
        jScrollPane1.setBounds(20, 50, 500, 260);

        StatementRejectjLabel4.setText("Kindly provide coments, Suggestions and recommendations regarding your rejection of ");
        loanDetailsRejectionPanel.add(StatementRejectjLabel4);
        StatementRejectjLabel4.setBounds(20, 4, 470, 30);

        ReturnRejectLoanDetailsCommentsjButton.setText("Return");
        ReturnRejectLoanDetailsCommentsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReturnRejectLoanDetailsCommentsjButtonActionPerformed(evt);
            }
        });
        loanDetailsRejectionPanel.add(ReturnRejectLoanDetailsCommentsjButton);
        ReturnRejectLoanDetailsCommentsjButton.setBounds(310, 310, 80, 23);

        SaveRejectLoanDetailsCommentsjButton.setText("Save");
        SaveRejectLoanDetailsCommentsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveRejectLoanDetailsCommentsjButtonActionPerformed(evt);
            }
        });
        loanDetailsRejectionPanel.add(SaveRejectLoanDetailsCommentsjButton);
        SaveRejectLoanDetailsCommentsjButton.setBounds(200, 310, 60, 23);

        jPanel1.add(loanDetailsRejectionPanel);
        loanDetailsRejectionPanel.setBounds(970, 30, 10, 10);
        loanDetailsRejectionPanel.setVisible(false);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 990, 550);
        jPanel2.add(jTextField1);
        jTextField1.setBounds(260, 500, 380, 40);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Export Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(130, 500, 130, 40);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(0, 500, 130, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(160, 30, 990, 550);

        jPanel3.setBackground(java.awt.SystemColor.activeCaption);
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable3);

        jPanel3.add(jScrollPane5);
        jScrollPane5.setBounds(0, 160, 990, 190);

        titleloanDetailsjLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titleloanDetailsjLabel.setForeground(java.awt.SystemColor.controlLtHighlight);
        titleloanDetailsjLabel.setText("Amortization Schedule");
        titleloanDetailsjLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(titleloanDetailsjLabel);
        titleloanDetailsjLabel.setBounds(0, 130, 990, 30);
        titleloanDetailsjLabel.setVisible(false);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(410, 500, 190, 40);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("Reject");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);
        jButton4.setBounds(600, 500, 190, 40);

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Approve");
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);
        jButton5.setBounds(220, 500, 190, 40);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable4);

        jPanel3.add(jScrollPane6);
        jScrollPane6.setBounds(0, 380, 990, 168);

        titleloanDetailsjLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        titleloanDetailsjLabel1.setForeground(java.awt.SystemColor.controlLtHighlight);
        titleloanDetailsjLabel1.setText("Loan Security");
        titleloanDetailsjLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(titleloanDetailsjLabel1);
        titleloanDetailsjLabel1.setBounds(0, 350, 990, 30);
        titleloanDetailsjLabel.setVisible(false);

        loanDetailsAuthorisationPanel1.setBackground(java.awt.SystemColor.activeCaption);
        loanDetailsAuthorisationPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.setLayout(null);

        InterestRatejTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InterestRatejTextField1.setForeground(new java.awt.Color(0, 102, 51));
        loanDetailsAuthorisationPanel1.add(InterestRatejTextField1);
        InterestRatejTextField1.setBounds(500, 10, 140, 30);

        princempalAmountamountField1.setForeground(new java.awt.Color(0, 102, 51));
        princempalAmountamountField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        princempalAmountamountField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        princempalAmountamountField1.setValue(null);
        princempalAmountamountField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                princempalAmountamountField1ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(princempalAmountamountField1);
        princempalAmountamountField1.setBounds(500, 50, 140, 30);

        totalLoanAmountjLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanAmountjLabel1.setText("TOTAL LOAN AMNT");
        totalLoanAmountjLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(totalLoanAmountjLabel1);
        totalLoanAmountjLabel1.setBounds(650, 50, 170, 30);

        totalLoanAmountamountFieldDebit5.setForeground(new java.awt.Color(0, 102, 51));
        totalLoanAmountamountFieldDebit5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        totalLoanAmountamountFieldDebit5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanAmountamountFieldDebit5.setValue(null);
        totalLoanAmountamountFieldDebit5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalLoanAmountamountFieldDebit5ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(totalLoanAmountamountFieldDebit5);
        totalLoanAmountamountFieldDebit5.setBounds(830, 50, 150, 30);

        instalmentStaerDatejTextField4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        instalmentStaerDatejTextField4.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel1.add(instalmentStaerDatejTextField4);
        instalmentStaerDatejTextField4.setBounds(200, 90, 120, 30);

        instalmentEndDatejLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        instalmentEndDatejLabel8.setForeground(new java.awt.Color(0, 51, 204));
        instalmentEndDatejLabel8.setText("INST. END DATE");
        instalmentEndDatejLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(instalmentEndDatejLabel8);
        instalmentEndDatejLabel8.setBounds(330, 90, 160, 30);

        InterestRatejLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        InterestRatejLabel1.setForeground(new java.awt.Color(0, 0, 204));
        InterestRatejLabel1.setText("INTEREST RATE");
        InterestRatejLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(InterestRatejLabel1);
        InterestRatejLabel1.setBounds(330, 10, 160, 30);

        instalmentEndDatejTextField6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        instalmentEndDatejTextField6.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel1.add(instalmentEndDatejTextField6);
        instalmentEndDatejTextField6.setBounds(500, 90, 140, 30);

        loanTenurejTextField7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loanTenurejTextField7.setForeground(new java.awt.Color(0, 102, 102));
        loanDetailsAuthorisationPanel1.add(loanTenurejTextField7);
        loanTenurejTextField7.setBounds(830, 90, 150, 30);

        princempalAmountjLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        princempalAmountjLabel1.setText("PRINCIMPAL AMNT");
        princempalAmountjLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(princempalAmountjLabel1);
        princempalAmountjLabel1.setBounds(330, 50, 160, 30);

        noOfIndtalmentsjLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        noOfIndtalmentsjLabel1.setForeground(new java.awt.Color(0, 0, 204));
        noOfIndtalmentsjLabel1.setText("NO. OF  INSTS");
        noOfIndtalmentsjLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(noOfIndtalmentsjLabel1);
        noOfIndtalmentsjLabel1.setBounds(10, 10, 180, 30);

        noOfIndtalmentsjTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        noOfIndtalmentsjTextField1.setForeground(new java.awt.Color(0, 102, 102));
        noOfIndtalmentsjTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        loanDetailsAuthorisationPanel1.add(noOfIndtalmentsjTextField1);
        noOfIndtalmentsjTextField1.setBounds(200, 10, 120, 30);

        instalmentStartDatejLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        instalmentStartDatejLabel20.setForeground(new java.awt.Color(0, 51, 204));
        instalmentStartDatejLabel20.setText("INST. START DATE");
        instalmentStartDatejLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(instalmentStartDatejLabel20);
        instalmentStartDatejLabel20.setBounds(10, 90, 180, 30);

        documentApprovalLoanDetailsButton1.setText("doc approval");
        documentApprovalLoanDetailsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentApprovalLoanDetailsButton1ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(documentApprovalLoanDetailsButton1);
        documentApprovalLoanDetailsButton1.setBounds(500, 420, 160, 23);

        collateralApprovalLoanDetailsButton1.setText("coll approval");
        collateralApprovalLoanDetailsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateralApprovalLoanDetailsButton1ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(collateralApprovalLoanDetailsButton1);
        collateralApprovalLoanDetailsButton1.setBounds(350, 420, 150, 23);

        homeLoanDetailsButton1.setBackground(new java.awt.Color(0, 102, 102));
        homeLoanDetailsButton1.setForeground(new java.awt.Color(0, 0, 204));
        homeLoanDetailsButton1.setText("home");
        homeLoanDetailsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeLoanDetailsButton1ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(homeLoanDetailsButton1);
        homeLoanDetailsButton1.setBounds(190, 420, 160, 23);

        loanTenurejLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loanTenurejLabel10.setForeground(new java.awt.Color(0, 51, 204));
        loanTenurejLabel10.setText("LOAN TENURE");
        loanTenurejLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(loanTenurejLabel10);
        loanTenurejLabel10.setBounds(650, 90, 170, 30);

        totalInterestAmountjLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalInterestAmountjLabel15.setText("TOTAL INTEREST AMNT");
        totalInterestAmountjLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(totalInterestAmountjLabel15);
        totalInterestAmountjLabel15.setBounds(10, 50, 180, 30);

        totalInterestamountFieldDebit6.setForeground(new java.awt.Color(0, 102, 51));
        totalInterestamountFieldDebit6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        totalInterestamountFieldDebit6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalInterestamountFieldDebit6.setValue(null);
        totalInterestamountFieldDebit6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalInterestamountFieldDebit6ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(totalInterestamountFieldDebit6);
        totalInterestamountFieldDebit6.setBounds(200, 50, 120, 30);

        theLoanInstalmentjLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        theLoanInstalmentjLabel7.setForeground(new java.awt.Color(0, 0, 153));
        theLoanInstalmentjLabel7.setText("THE LOAN INST. AMNT");
        theLoanInstalmentjLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDetailsAuthorisationPanel1.add(theLoanInstalmentjLabel7);
        theLoanInstalmentjLabel7.setBounds(650, 10, 170, 30);

        theLoanInstalmentamountFieldDebit4.setForeground(new java.awt.Color(0, 102, 51));
        theLoanInstalmentamountFieldDebit4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        theLoanInstalmentamountFieldDebit4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        theLoanInstalmentamountFieldDebit4.setValue(null);
        theLoanInstalmentamountFieldDebit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theLoanInstalmentamountFieldDebit4ActionPerformed(evt);
            }
        });
        loanDetailsAuthorisationPanel1.add(theLoanInstalmentamountFieldDebit4);
        theLoanInstalmentamountFieldDebit4.setBounds(830, 10, 150, 30);

        jPanel3.add(loanDetailsAuthorisationPanel1);
        loanDetailsAuthorisationPanel1.setBounds(0, 0, 990, 130);
        loanDetailsAuthorisationPanel.setVisible(false);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(160, 30, 990, 550);
        jPanel3.setVisible(false);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1150, 650);

        jButton34.setBackground(new java.awt.Color(51, 0, 51));
        jButton34.setText("jButton5");
        getContentPane().add(jButton34);
        jButton34.setBounds(1600, 430, 73, 23);

        jButton35.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton35.setText("jButton5");
        getContentPane().add(jButton35);
        jButton35.setBounds(1590, 360, 73, 23);

        jButton36.setText("jButton8");
        getContentPane().add(jButton36);
        jButton36.setBounds(1720, 360, 73, 23);

        jButton38.setBackground(new java.awt.Color(255, 102, 0));
        jButton38.setText("Blue");
        getContentPane().add(jButton38);
        jButton38.setBounds(1730, 130, 53, 23);

        jButton39.setBackground(new java.awt.Color(204, 255, 204));
        jButton39.setText("Blue");
        getContentPane().add(jButton39);
        jButton39.setBounds(1730, 130, 53, 23);

        jButton40.setBackground(java.awt.SystemColor.inactiveCaption);
        jButton40.setText("Blue");
        getContentPane().add(jButton40);
        jButton40.setBounds(1730, 130, 53, 23);

        jButton41.setBackground(new java.awt.Color(204, 165, 165));
        jButton41.setText("Blue");
        getContentPane().add(jButton41);
        jButton41.setBounds(1730, 130, 53, 23);

        jButton42.setBackground(new java.awt.Color(204, 204, 204));
        jButton42.setText("Blue");
        getContentPane().add(jButton42);
        jButton42.setBounds(1730, 130, 53, 23);

        jButton53.setBackground(new java.awt.Color(204, 204, 0));
        jButton53.setText("Blue");
        getContentPane().add(jButton53);
        jButton53.setBounds(1730, 130, 53, 23);

        jButton43.setBackground(java.awt.SystemColor.activeCaption);
        jButton43.setText("jButton5");
        getContentPane().add(jButton43);
        jButton43.setBounds(1630, 280, 73, 23);

        jButton44.setBackground(java.awt.SystemColor.activeCaption);
        jButton44.setText("Blue");
        getContentPane().add(jButton44);
        jButton44.setBounds(1730, 130, 53, 23);

        jButton45.setBackground(new java.awt.Color(201, 222, 223));
        jButton45.setText("Blue");
        getContentPane().add(jButton45);
        jButton45.setBounds(1730, 130, 53, 23);

        jButton46.setBackground(java.awt.SystemColor.activeCaption);
        jButton46.setText("Blue");
        getContentPane().add(jButton46);
        jButton46.setBounds(1730, 130, 53, 23);

        jButton47.setBackground(java.awt.SystemColor.activeCaption);
        jButton47.setText("Blue");
        getContentPane().add(jButton47);
        jButton47.setBounds(1730, 130, 53, 23);

        jButton48.setBackground(new java.awt.Color(0, 204, 255));
        jButton48.setText("Blue");
        getContentPane().add(jButton48);
        jButton48.setBounds(1730, 130, 53, 23);

        jButton52.setBackground(new java.awt.Color(0, 204, 102));
        jButton52.setText("Blue");
        getContentPane().add(jButton52);
        jButton52.setBounds(1730, 130, 53, 23);

        jButton56.setBackground(java.awt.SystemColor.activeCaption);
        jButton56.setText("Blue");
        getContentPane().add(jButton56);
        jButton56.setBounds(1730, 130, 53, 23);

        jButton54.setBackground(new java.awt.Color(0, 204, 204));
        jButton54.setText("Blue");
        getContentPane().add(jButton54);
        jButton54.setBounds(1730, 130, 53, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
           LoanApprovals frm5 = new LoanApprovals(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
//        frm5.setUserID(userId);
        this.dispose();  
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void ApproveCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApproveCollateralButtonActionPerformed
String activeAccountn =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));   
     switch(numbColl){
         case 1:
             
    if(collateral1TextField.getText().equals("")){ JOptionPane.showMessageDialog(this, "Please first input a valid collateral in the first field to proceed!!");return;}else
          if(collateral1CheckBoxTest.isSelected()==false){
          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
          }
      
      else{break;}  
       
       
             
      case 2:
//     if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first  collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second  collateral to proceed!!");return;} 
//  
//     else{break;}
       
            if(collateral1TextField.getText().equals("")){
     
     JOptionPane.showMessageDialog(this, "Please first input valid collateral in the first field to proceed!!");return; 
     }else if(collateral1CheckBoxTest.isSelected()==false){
         
   JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;      
         }else if(collateral2TextField.getText().equals("")){
         
          JOptionPane.showMessageDialog(this, "Please first input a valid collateral in the second field to proceed!!");return; 
         
         }
else  if(collateral2CheckBoxTest.isSelected()==false){
      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
             }
       else{break;}
            case 3:
//      if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;} 
//  
//     else{break;}
            
              if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;
}
          else{break;}    
                  case 4:
                       if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}else if(collateral4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fourth field to proceed!!");return;
} else if(collateral4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}
          else{break;} 
                       
//    if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;} 
//  else if(collateral4TextField.getText().equals("")&&collateral4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;} 
//     else{break;}
//         
                        case 5:
                              if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}else if(collateral4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fourth field to proceed!!");return;
} else if(collateral4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;
}else if(collateral5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fifth field to proceed!!");return;
} else if(collateral5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;
}
          else{break;} 
//   if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;} 
//  else if(collateral4TextField.getText().equals("")&&collateral4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;} 
//   else if(collateral5TextField.getText().equals("")&&collateral5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;}  
//  
//  else{break;}
         
           
                        case 6:
                   if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}else if(collateral4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fourth field to proceed!!");return;
} else if(collateral4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;
}else if(collateral5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fifth field to proceed!!");return;
} else if(collateral5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}else if(collateral6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the sixth field to proceed!!");return;
} else if(collateral6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}
          else{break;}             
                            
//  if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;} 
//  else if(collateral4TextField.getText().equals("")&&collateral4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;} 
//   else if(collateral5TextField.getText().equals("")&&collateral5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;}  
//   else if(collateral6TextField.getText().equals("")&&collateral6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith collateral to proceed!!");return;} 
//   
//  else{break;}
           
                              case 7:
          if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}else if(collateral4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fourth field to proceed!!");return;
} else if(collateral4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;
}else if(collateral5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fifth field to proceed!!");return;
} else if(collateral5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}else if(collateral6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the sixth field to proceed!!");return;
} else if(collateral6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}else if(collateral7TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the seventh field to proceed!!");return;
} else if(collateral7CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the seventh collateral to proceed!!");return;
}
          else{break;}                              
//                                  
// if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral to proceed!!");return;} 
//  else if(collateral4TextField.getText().equals("")&&collateral4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;} 
//   else if(collateral5TextField.getText().equals("")&&collateral5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;}  
//   else if(collateral6TextField.getText().equals("")&&collateral6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith collateral to proceed!!");return;} 
//    else if(collateral7TextField.getText().equals("")&&collateral7CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the seventh collateral to proceed!!");return;} 
//  else{break;}
                           
                                    case 8:
                                        
             if(collateral1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input  valid collateral in field one to proceed!!");return;
      }
      
      else if(collateral1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first collateral to proceed!!");return;
      }     
              
      
      
        
     else if(collateral2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input  valid collateral in second field to proceed!!");return;
     }   
         
     else if(collateral2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second collateral to proceed!!");return;
     }

 
     else if(collateral3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the third field to proceed!!");return;
} else if(collateral3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral to proceed!!");return;
}else if(collateral4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fourth field to proceed!!");return;
} else if(collateral4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral to proceed!!");return;
}else if(collateral5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the fifth field to proceed!!");return;
} else if(collateral5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}else if(collateral6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the sixth field to proceed!!");return;
} else if(collateral6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth collateral to proceed!!");return;
}else if(collateral7TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the seventh field to proceed!!");return;
} else if(collateral7CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the seventh collateral to proceed!!");return;
}else if(collateral8TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input the valid collateral in  the eighth field to proceed!!");return;
} else if(collateral8CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the eigth collateral to proceed!!");return;
}
          else{break;}                             
                                        
// if(collateral1TextField.getText().equals("")&&collateral1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first collateral  to proceed!!");return;}
//     else if(collateral2TextField.getText().equals("")&&collateral2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second collateral  to proceed!!");return;} 
//     else if(collateral3TextField.getText().equals("")&&collateral3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third collateral  to proceed!!");return;} 
//  else if(collateral4TextField.getText().equals("")&&collateral4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth collateral  to proceed!!");return;} 
//   else if(collateral5TextField.getText().equals("")&&collateral5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth collateral  to proceed!!");return;}  
//   else if(collateral6TextField.getText().equals("")&&collateral6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith collateral  to proceed!!");return;} 
//    else if(collateral7TextField.getText().equals("")&&collateral7CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the seventh collateral  to proceed!!");return;} 
// else if(collateral8TextField.getText().equals("")&&collateral8CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the eigth collateral  to proceed!!");return;} 
//    else{break;}
//           
    }     

        fios.forceFileExistanceAppend(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"));
          Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
   
           
   Integer xc=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","home"+  activeAccountn+".txt"),xc.toString());
    Integer xcu=2;
    
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","collateral"+  activeAccountn+".txt"),xcu.toString());
  
   Integer xfb=2;
  fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedCollateral"+  activeAccountn+".txt"),xfb.toString());
   
   
     Integer zxs=2;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+ activeAccountn+".txt"),zxs.toString()); 
   
  Integer sdg=29;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+ activeAccountn+".txt"), sdg.toString());   
    
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_ApproveCollateralButtonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void ApproveDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApproveDocumentButtonActionPerformed
  String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.     
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
      if(document1TextField.getText().equals("")){ JOptionPane.showMessageDialog(this, "Please first input a valid document in the first field to proceed!!");return;}else
          if(document1CheckBoxTest.isSelected()==false){
          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
          }
      
      else{break;}  
       
             
      case 2:
     if(document1TextField.getText().equals("")){
     
     JOptionPane.showMessageDialog(this, "Please first input a valid document in the first field to proceed!!");return; 
     }else if(document1CheckBoxTest.isSelected()==false){
         
   JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;      
         }else if(document2TextField.getText().equals("")){
         
          JOptionPane.showMessageDialog(this, "Please first input a valid document in the second field to proceed!!");return; 
         
         }
else  if(document2CheckBoxTest.isSelected()==false){
      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
             }
       else{break;}
       
           
            case 3:
      if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}
  
     else{break;}
             
                  case 4:
       if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}else if(document4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fourth field to proceed!!");return;
} else if(document4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;
}
  
     else{break;}               
                      
                      
//    if(document1TextField.getText().equals("")&&document1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;}
//     else if(document2TextField.getText().equals("")&&document2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;} 
//     else if(document3TextField.getText().equals("")&&document3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;} 
//  else if(document4TextField.getText().equals("")&&document4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;} 
//     else{break;}
         
                        case 5:
        if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}else if(document4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fourth field to proceed!!");return;
} else if(document4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;
}else if(document5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fifth field to proceed!!");return;
} else if(document5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;
}
  
     else{break;}                       
                            
//   if(document1TextField.getText().equals("")&&document1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;}
//     else if(document2TextField.getText().equals("")&&document2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;} 
//     else if(document3TextField.getText().equals("")&&document3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;} 
//  else if(document4TextField.getText().equals("")&&document4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;} 
//   else if(document5TextField.getText().equals("")&&document5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;}  
//  
//  else{break;}
         
           
                        case 6:
                             if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}else if(document4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fourth field to proceed!!");return;
} else if(document4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;
}else if(document5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fifth field to proceed!!");return;
} else if(document5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;
}else if(document6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the sixth field to proceed!!");return;
} else if(document6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sixth document to proceed!!");return;
}
  
     else{break;} 
//  if(document1TextField.getText().equals("")&&document1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;}
//     else if(document2TextField.getText().equals("")&&document2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;} 
//     else if(document3TextField.getText().equals("")&&document3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;} 
//  else if(document4TextField.getText().equals("")&&document4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;} 
//   else if(document5TextField.getText().equals("")&&document5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;}  
//   else if(document6TextField.getText().equals("")&&document6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;} 
//   
//  else{break;}
           
                              case 7:
            if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}else if(document4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fourth field to proceed!!");return;
} else if(document4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;
}else if(document5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fifth field to proceed!!");return;
} else if(document5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;
}else if(document6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the sixth field to proceed!!");return;
} else if(document6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the seventh document to proceed!!");return;
}else if(document7TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the seventh field to proceed!!");return;
} else if(document7CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the seventh document to proceed!!");return;
}
  
     else{break;}                              
                                  
// if(document1TextField.getText().equals("")&&document1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;}
//     else if(document2TextField.getText().equals("")&&document2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;} 
//     else if(document3TextField.getText().equals("")&&document3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;} 
//  else if(document4TextField.getText().equals("")&&document4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;} 
//   else if(document5TextField.getText().equals("")&&document5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;}  
//   else if(document6TextField.getText().equals("")&&document6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;} 
//    else if(document7TextField.getText().equals("")&&document7CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the seventh document to proceed!!");return;} 
//  else{break;}
                           
                                    case 8:
         if(document1TextField.getText().equals("")){
       JOptionPane.showMessageDialog(this, "Please first input a valid document in field one to proceed!!");return;
      }
      
      else if(document1CheckBoxTest.isSelected()==false){
           JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;
      }     
              
      
      
        
     else if(document2TextField.getText().equals("")){
         
      JOptionPane.showMessageDialog(this, "Please first input a valid document in second field to proceed!!");return;
     }   
         
     else if(document2CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;
     }

 
     else if(document3TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the third field to proceed!!");return;
} else if(document3CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;
}else if(document4TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fourth field to proceed!!");return;
} else if(document4CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;
}else if(document5TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the fifth field to proceed!!");return;
} else if(document5CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;
}else if(document6TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the sixth field to proceed!!");return;
} else if(document6CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the seventh document to proceed!!");return;
}else if(document7TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the seventh field to proceed!!");return;
} else if(document7CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the eighth document to proceed!!");return;
}else if(document8TextField.getText().equals("")){
      JOptionPane.showMessageDialog(this, "Please first input a valid document in  the eighth field to proceed!!");return;
} else if(document8CheckBoxTest.isSelected()==false){

JOptionPane.showMessageDialog(this, "Please first confirm the eighth document to proceed!!");return;
}
  
     else{break;}                                     
                                        
// if(document1TextField.getText().equals("")&&document1CheckBoxTest.isSelected()==false){
//          JOptionPane.showMessageDialog(this, "Please first confirm the first document to proceed!!");return;}
//     else if(document2TextField.getText().equals("")&&document2CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the second document to proceed!!");return;} 
//     else if(document3TextField.getText().equals("")&&document3CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the third document to proceed!!");return;} 
//  else if(document4TextField.getText().equals("")&&document4CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fourth document to proceed!!");return;} 
//   else if(document5TextField.getText().equals("")&&document5CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the fifth document to proceed!!");return;}  
//   else if(document6TextField.getText().equals("")&&document6CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the sith document to proceed!!");return;} 
//    else if(document7TextField.getText().equals("")&&document7CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the seventh document to proceed!!");return;} 
// else if(document8TextField.getText().equals("")&&document8CheckBoxTest.isSelected()==false){
//      JOptionPane.showMessageDialog(this, "Please first confirm the eigth document to proceed!!");return;} 
//    else{break;}
           
    }     
        
        
        
     fios.forceFileExistanceAppend(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"));
          Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
        
        
        
  
         
    Integer xfl=2;
  fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccount+".txt"),xfl.toString()); 
  
   Integer xc=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","home"+  activeAccount+".txt"),xc.toString());
    Integer xcu=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","document"+  activeAccount+".txt"),xcu.toString());
  
  Integer xfG=2;
  fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedDocement"+  activeAccount+".txt"),xfG.toString());
  
  
  Integer sdg=29;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+activeAccount+".txt"), sdg.toString());
  
  
  LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_ApproveDocumentButtonActionPerformed

    private void documentAuthorisationPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documentAuthorisationPanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_documentAuthorisationPanelMouseEntered

    private void rejectLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectLoanDetailsButtonActionPerformed
 fios.forceFileExistanceAppend(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"));
          Integer xsl=5;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xsl.toString());
   
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_rejectLoanDetailsButtonActionPerformed

    private void ApproveLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApproveLoanDetailsButtonActionPerformed
String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.   
          Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
   
   Integer xc=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","home"+ activeAccount+".txt"),xc.toString());
    Integer xcu=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","loandetails"+ activeAccount+".txt"),xcu.toString());
   Integer xcup=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedLoanDetails"+activeAccount+".txt"),xcup.toString());
   Integer zx=2;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccount+".txt"),zx.toString()); 
     
    
  Integer sdg=29;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+activeAccount+".txt"), sdg.toString());
   
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_ApproveLoanDetailsButtonActionPerformed

    private void princempalAmountamountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_princempalAmountamountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_princempalAmountamountFieldActionPerformed

    private void theLoanInstalmentamountFieldDebit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theLoanInstalmentamountFieldDebit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theLoanInstalmentamountFieldDebit3ActionPerformed

    private void totalLoanAmountamountFieldDebit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalLoanAmountamountFieldDebit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalLoanAmountamountFieldDebit4ActionPerformed

    private void totalInterestamountFieldDebit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalInterestamountFieldDebit5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalInterestamountFieldDebit5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      switch(dbq.title(userId)){
            case "Loans Officer":
       
                break;
            case "Accountant":
                     

break;
            case "Supervisor":
       
                break;
        
        
           case "Manager":
  fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "7");
                break;
        
        }
        
        
        
    }//GEN-LAST:event_formWindowOpened

    private void OkRejectLoanDetailsCommentsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkRejectLoanDetailsCommentsjButtonActionPerformed
String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
         Object[] options = {"Continue",  "Cancel"};
        int n = JOptionPane.showOptionDialog(this,  "ARE YOU SURE YOU WANT TO REJECT"+" "+fios.stringFileReaderEmpty(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"))+".txt")).split("[,]", 15)[10]+"'S LOAN DETAILS ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
        if(n==JOptionPane.YES_OPTION){
        
          Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
   
   
    Integer xcu=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","loandetails"+ activeAccount+".txt"),xcu.toString());
   Integer xcup=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedLoanDetails"+ activeAccount+".txt"),xcup.toString());
   Integer zx=2;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccount+".txt"),zx.toString()); 
     
  Integer sdg=15;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+activeAccount+".txt"), sdg.toString());
   
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        }
        else if (n==JOptionPane.NO_OPTION){ this.setVisible(true);
        
        
        
        
        
        }
        
        
        
        
        
        
    
    }//GEN-LAST:event_OkRejectLoanDetailsCommentsjButtonActionPerformed

    private void ReturnRejectLoanDetailsCommentsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnRejectLoanDetailsCommentsjButtonActionPerformed
    Object[] options = {"Continue",  "Cancel"};
        int n = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
        if(n==JOptionPane.YES_OPTION){
        
            Integer xlg=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xlg.toString());
          
    
        LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
        
        }
        else if (n==JOptionPane.NO_OPTION){ this.setVisible(true);}
    }//GEN-LAST:event_ReturnRejectLoanDetailsCommentsjButtonActionPerformed

    private void SaveRejectLoanDetailsCommentsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveRejectLoanDetailsCommentsjButtonActionPerformed

        String activeAccounts =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); 
     fios.stringFileWriter(fios.createFileName("loanAuthorisation", "displayWindow", "rejectedComments"+activeAccounts+".txt"),RejectLoanDetailsCommentsjTextArea.getText());
    
      

    }//GEN-LAST:event_SaveRejectLoanDetailsCommentsjButtonActionPerformed

    private void document1CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document1CheckBoxTestActionPerformed
String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
      if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}  
       
             break;
      case 2:
     if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 2)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}
             break;
            case 3:
      if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 3)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}
             break;
                  case 4:
     if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 4)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");} 
             break;
                        case 5:
     if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 5)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}  
             break;  
                        case 6:
       if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 6)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}  
             break;
                              case 7:
    if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 7)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}  
                              break;
                                    case 8:
  if(document1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 8)[0].replaceAll(";", " ").trim())){document1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}  
             break;
    }     
        
        
        
       
    }//GEN-LAST:event_document1CheckBoxTestActionPerformed

    private void document2CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document2CheckBoxTestActionPerformed
   String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 2)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}
             break;
            case 3:
      if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 3)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}
             break;
                  case 4:
     if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 4)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");} 
             break;
                        case 5:
     if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 5)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}  
             break;  
                        case 6:
       if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 6)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}  
             break;
                              case 7:
    if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 7)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}  
                              break;
                                    case 8:
  if(document2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt")).split("[;]", 8)[1].replaceAll(";", " ").trim())){document2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document2TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 2");document2CheckBoxTest.setSelected(false);document2TextField.setText(" ");}  
             break;
    }     
    }//GEN-LAST:event_document2CheckBoxTestActionPerformed

    private void rejectDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectDocumentButtonActionPerformed

        
        String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
    
     Integer xc=77;
     fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "rejectedDovCol"+activeAccount+".txt"), xc.toString());
        
        
        int numdocs= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));  

     switch( numdocs){
         case 1:
  
           fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"), document1TextField.getText());
            JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");
             break;
      case 2:

                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String var="";
           int i=1;
              for(;i<=2;i++){
                
             if(i==1){
             var=document1TextField.getText();
             }   
             
            
              if(i==2){
                var=document2TextField.getText();   
                  
              }    
    
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  var);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
             
              
              } 
              JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");

             break;
            case 3:
  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varr="";
           int ii=1;
              for(;ii<=3;ii++){
                
             if(ii==1){
             varr=document1TextField.getText();
             }   
             
            
              if(ii==2){
                varr=document2TextField.getText();   
                  
              }    
    if(ii==3){
                varr=document3TextField.getText();   
                  
              }  
              
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varr);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              } 
         
        JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");

             break;
                  case 4:
               fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varv="";
           int iv=1;
              for(;iv<=4;iv++){
                
             if(iv==1){
             varv=document1TextField.getText();
             }   
             
            
              if(iv==2){
                varv=document2TextField.getText();   
                  
              }    
    if(iv==3){
                varv=document3TextField.getText();   
                  
              }  
              
     if(iv==4){
                varv=document4TextField.getText();   
                  
              }  
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varv);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              }
              
           JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");
 
             break;
                        case 5:
 fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varc="";
           int ic=1;
              for(;ic<=5;ic++){
                
             if(ic==1){
             varc=document1TextField.getText();
             }   
             
            
              if(ic==2){
                varc=document2TextField.getText();   
                  
              }    
    if(ic==3){
                varc=document3TextField.getText();   
                  
              }  
              
     if(ic==4){
                varc=document4TextField.getText();   
                  
              }  
     if(ic==5){
                varc=document5TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varc);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              }
            JOptionPane.showMessageDialog(this,  "Document Rejected Successfully!!!");
        
          
             break;  
                        case 6:

                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varn="";
           int in=1;
              for(;in<=6;in++){
                
             if(in==1){
             varn=document1TextField.getText();
             }   
             
            
              if(in==2){
                varn=document2TextField.getText();   
                  
              }    
    if(in==3){
                varn=document3TextField.getText();   
                  
              }  
              
     if(in==4){
                varn=document4TextField.getText();   
                  
              }  
     if(in==5){
                varn=document5TextField.getText();   
                  
              }
      if(in==6){
                varn=document6TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varn);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              } 
              
              JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");
          
             break;
                              case 7:

              
                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varb="";
           int ib=1;
              for(;ib<=7;ib++){
                
             if(ib==1){
             varb=document1TextField.getText();
             }   
             
            
              if(ib==2){
                varb=document2TextField.getText();   
                  
              }    
    if(ib==3){
                varb=document3TextField.getText();   
                  
              }  
              
     if(ib==4){
                varb=document4TextField.getText();   
                  
              }  
     if(ib==5){
                varb=document5TextField.getText();   
                  
              }
      if(ib==6){
                varb=document6TextField.getText();   
                  
              }
      if(ib==7){
                varb=document7TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varb);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              } 
              
              JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");
         
                                
                                  
                                    case 8:
                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"));
           String varm="";
           int im=1;
              for(;im<=8;im++){
                
             if(im==1){
             varm=document1TextField.getText();
             }   
             
            
              if(im==2){
                varm=document2TextField.getText();   
                  
              }    
    if(im==3){
                varm=document3TextField.getText();   
                  
              }  
              
     if(im==4){
                varm=document4TextField.getText();   
                  
              }  
     if(im==5){
                varm=document5TextField.getText();   
                  
              }
      if(im==6){
                varm=document6TextField.getText();   
                  
              }
      if(im==7){
                varm=document7TextField.getText();   
                  
              }
      if(im==8){
                varm=document8TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  varm);    
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeAccount+".txt"),  ";"); 
              } 
              JOptionPane.showMessageDialog(this, "Document Rejected Successfully!!!");
                  break;
    } 
    
    Integer xfl=2;
  fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccount+".txt"),xfl.toString()); 
  
  
    Integer xcu=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","document"+ activeAccount+".txt"),xcu.toString());
  
  Integer xfG=2;
  fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedDocement"+ activeAccount+".txt"),xfG.toString());
  
  
  Integer sdg=15;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+activeAccount+".txt"), sdg.toString());
  
  LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_rejectDocumentButtonActionPerformed

    private void document4CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document4CheckBoxTestActionPerformed
    String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 2)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}
             break;
            case 3:
      if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 3)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}
             break;
                  case 4:
     if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 4)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");} 
             break;
                        case 5:
     if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 5)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}  
             break;  
                        case 6:
       if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 6)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}  
             break;
                              case 7:
    if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 7)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}  
                              break;
                                    case 8:
  if(document4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 8)[3].replaceAll(";", " ").trim())){document4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 4");document4CheckBoxTest.setSelected(false);document4TextField.setText("");}  
             break;
    }     
    }//GEN-LAST:event_document4CheckBoxTestActionPerformed

    private void document5CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document5CheckBoxTestActionPerformed
     
        String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 2)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}
             break;
            case 3:
      if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 3)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}
             break;
                  case 4:
     if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 4)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");} 
             break;
                        case 5:
     if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 5)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}  
             break;  
                        case 6:
       if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 6)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}  
             break;
                              case 7:
    if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 7)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}  
                              break;
                                    case 8:
  if(document5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+ activeaccountz+".txt")).split("[;]", 8)[4].replaceAll(";", " ").trim())){document5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 5");document5CheckBoxTest.setSelected(false);document5TextField.setText("");}  
             break;
    }     
    }//GEN-LAST:event_document5CheckBoxTestActionPerformed

    private void document6CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document6CheckBoxTestActionPerformed
    String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
        
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 2)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}
             break;
            case 3:
      if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 3)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}
             break;
                  case 4:
     if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 4)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");} 
             break;
                        case 5:
     if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 5)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}  
             break;  
                        case 6:
       if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 6)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}  
             break;
                              case 7:
    if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 7)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}  
                              break;
                                    case 8:
  if(document6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 8)[5].replaceAll(";", " ").trim())){document6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 6");document6CheckBoxTest.setSelected(false);document6TextField.setText("");}  
             break;
    }     
    }//GEN-LAST:event_document6CheckBoxTestActionPerformed

    private void document7CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document7CheckBoxTestActionPerformed
      String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.  
        
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 2)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}
             break;
            case 3:
      if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 3)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}
             break;
                  case 4:
     if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 4)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");} 
             break;
                        case 5:
     if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 5)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}  
             break;  
                        case 6:
       if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 6)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}  
             break;
                              case 7:
    if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 7)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}  
                              break;
                                    case 8:
  if(document7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 8)[6].replaceAll(";", " ").trim())){document7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 7");document7CheckBoxTest.setSelected(false);document7TextField.setText("");}  
             break;
    }     
    }//GEN-LAST:event_document7CheckBoxTestActionPerformed

    private void document8CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document8CheckBoxTestActionPerformed
    
        
       String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.       
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));   
     switch(numbDocf){
         case 1:
     
       
             break;
      case 2:
     if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 2)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}
             break;
            case 3:
      if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 3)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}
             break;
                  case 4:
     if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 4)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");} 
             break;
                        case 5:
     if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 5)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}  
             break;  
                        case 6:
       if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 6)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}  
             break;
                              case 7:
    if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 7)[7].replaceAll(";", " ").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}  
                              break;
                                    case 8:
  if(document8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 8)[7].replaceAll(";", "").trim())){document8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 8");document8CheckBoxTest.setSelected(false);document8TextField.setText(" ");}  
             break;
    }     
    }//GEN-LAST:event_document8CheckBoxTestActionPerformed

    private void document3CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_document3CheckBoxTestActionPerformed
       
      String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.      
        int numbDocf= fios.intFileReader(fios.createFileName("persistence", "loanDocumentation", "numberOfDocs.txt"));
        switch(numbDocf){
            case 1:

            break;
            case 2:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 2)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
            case 3:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 3)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");JOptionPane.showMessageDialog(this, "  "+fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"))+".txt")).split("[;]", 3)[2].replaceAll(";", " "));}
            break;
            case 4:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 4)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
            case 5:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 5)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
            case 6:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 6)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
            case 7:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 7)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
            case 8:
            if(document3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "docs"+activeaccountz+".txt")).split("[;]", 8)[2].replaceAll(";", " ").trim())){document3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");document3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 3");document3CheckBoxTest.setSelected(false);document3TextField.setText("");}
            break;
        }
    }//GEN-LAST:event_document3CheckBoxTestActionPerformed

    private void collateral1CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral1CheckBoxTestActionPerformed
       
        String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.    
        
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")))){
                document1CheckBoxTest.setSelected(true);
                JOptionPane.showMessageDialog(this, "Correct!!");document1CheckBoxTest.setSelected(true);document1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong docement number 1");document1CheckBoxTest.setSelected(false);document1TextField.setText(" ");}

            break;
            case 2:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 3:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 4:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 5:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 6:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 7:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
            case 8:
            if(collateral1TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[0].replaceAll(";", " ").trim())){collateral1CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral1CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 1");collateral1CheckBoxTest.setSelected(false);collateral1TextField.setText(" ");}
            break;
        }

    }//GEN-LAST:event_collateral1CheckBoxTestActionPerformed

    private void collateral2CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral2CheckBoxTestActionPerformed
       
     String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.         
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 3:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 4:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 5:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 6:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 7:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
            case 8:
            if(collateral2TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[1].replaceAll(";", " ").trim())){collateral2CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral2CheckBoxTest.setSelected(true);collateral1TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 2");collateral2CheckBoxTest.setSelected(false);collateral2TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral2CheckBoxTestActionPerformed

    private void collateral3CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral3CheckBoxTestActionPerformed
    String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.     
        
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+ activeaccountz+".txt")).split("[;]", 2)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 3:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+ activeaccountz+".txt")).split("[;]", 3)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 4:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+ activeaccountz+".txt")).split("[;]", 4)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 5:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 6:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 7:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
            case 8:
            if(collateral3TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[2].replaceAll(";", " ").trim())){collateral3CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral3CheckBoxTest.setSelected(true);collateral3TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 3");collateral3CheckBoxTest.setSelected(false);collateral3TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral3CheckBoxTestActionPerformed

    private void collateral4CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral4CheckBoxTestActionPerformed
       
      String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.   
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 3:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 4:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 5:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 6:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 7:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
            case 8:
            if(collateral4TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[3].replaceAll(";", " ").trim())){collateral4CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral4CheckBoxTest.setSelected(true);collateral4TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 4");collateral4CheckBoxTest.setSelected(false);collateral4TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral4CheckBoxTestActionPerformed

    private void collateral5CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral5CheckBoxTestActionPerformed
     String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.   
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 3:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 4:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 5:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 6:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 7:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
            case 8:
            if(collateral5TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[4].replaceAll(";", " ").trim())){collateral5CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral5CheckBoxTest.setSelected(true);collateral5TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 5");collateral5CheckBoxTest.setSelected(false);collateral5TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral5CheckBoxTestActionPerformed

    private void collateral6CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral6CheckBoxTestActionPerformed
        String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 3:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 4:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 5:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 6:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 7:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
            case 8:
            if(collateral6TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[5].replaceAll(";", " ").trim())){collateral6CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral6CheckBoxTest.setSelected(true);collateral6TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 6");collateral6CheckBoxTest.setSelected(false);collateral6TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral6CheckBoxTestActionPerformed

    private void collateral7CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral7CheckBoxTestActionPerformed
       
         String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts. 
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 3:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 4:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 5:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 6:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 7:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 8:
            if(collateral7TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[6].replaceAll(";", " ").trim())){collateral7CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral7CheckBoxTest.setSelected(true);collateral7TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 7");collateral7CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
        }
    }//GEN-LAST:event_collateral7CheckBoxTestActionPerformed

    private void collateral8CheckBoxTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateral8CheckBoxTestActionPerformed
        String activeaccountz =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
        int numbColl= fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
        switch(numbColl){
            case 1:

            break;
            case 2:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 2)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 3:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 3)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 4:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 4)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 5:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 5)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 6:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 6)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 7:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 7)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
            case 8:
            if(collateral8TextField.getText().trim().equals(fios.stringFileReader(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccountz+".txt")).split("[;]", 8)[7].replaceAll(";", " ").trim())){collateral8CheckBoxTest.setSelected(true);JOptionPane.showMessageDialog(this, "Correct!!");collateral8CheckBoxTest.setSelected(true);collateral8TextField.setEditable(false);}else{JOptionPane.showMessageDialog(this, "Wrong collateral number 8");collateral8CheckBoxTest.setSelected(false);collateral7TextField.setText(" ");}
            break;
        }
       
        
        
        
    }//GEN-LAST:event_collateral8CheckBoxTestActionPerformed

    private void rejectCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectCollateralButtonActionPerformed
 
   String activeaccount =fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
   Integer xc=77;
     fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "rejectedDovCol"+activeaccount+".txt"), xc.toString());
    int numbCol=fios.intFileReader(fios.createFileName("persistence", "collateralSecurity", "numberOfCallaterals.txt"));
 
     switch(numbCol){
         case 1:
             
       
           fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"), collateral1TextField.getText());
     JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
        
             break;
      case 2:
             
                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varg="";
           int ig=1;
              for(;ig<=2;ig++){
                
             if(ig==1){
             varg=collateral1TextField.getText();
             }   
             
            
              if(ig==2){
                varg=collateral2TextField.getText();   
                  
              }    
    
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  varg);    //Holds the text values that represent collaterals
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
          
           JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
   
              
           
          
             break;
            case 3:
     fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varz="";
           int iz=1;
              for(;iz<=3;iz++){
                
             if(iz==1){
             varz=collateral1TextField.getText();
             }   
             
            
              if(iz==2){
                varz=collateral2TextField.getText();   
                  
              }    
    if(iz==3){
                varz=collateral3TextField.getText();   
                  
              }  
              
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  varz);  //Holds the text values that represent collaterals  
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
              JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
    
             break;
                  case 4:
  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varh="";
           int ih=1;
              for(;ih<=4;ih++){
                
             if(ih==1){
             varh=collateral1TextField.getText();
             }   
             
            
              if(ih==2){
                varh=collateral2TextField.getText();   
                  
              }    
    if(ih==3){
                varh=collateral3TextField.getText();   
                  
              }  
              
     if(ih==4){
                varh=collateral4TextField.getText();   
                  
              }  
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation",  "colls"+activeaccount+".txt"),  varh);  //Holds the text values that represent collaterals  
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation",  "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
               JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
         break;
                        case 5:
   fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varr="";
           int ir=1;
              for(;ir<=5;ir++){
                
             if(ir==1){
             varr=collateral1TextField.getText();
             }   
             
            
              if(ir==2){
                varr=collateral2TextField.getText();   
                  
              }    
    if(ir==3){
                varr=collateral3TextField.getText();   
                  
              }  
              
     if(ir==4){
                varr=collateral3TextField.getText();   
                  
              }  
     if(ir==5){
                varr=collateral5TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  varr);    //Holds the text values that represent collaterals
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
               JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
       break;  
                        case 6:
   fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varo="";
           int io=1;
              for(;io<=6;io++){
                
             if(io==1){
             varo=collateral1TextField.getText();
             }   
             
            
              if(io==2){
                varo=collateral2TextField.getText();   
                  
              }    
    if(io==3){
                varo=collateral3TextField.getText();   
                  
              }  
              
     if(io==4){
                varo=collateral4TextField.getText();   
                  
              }  
     if(io==5){
                varo=collateral5TextField.getText();   
                  
              }
      if(io==6){
                varo=collateral6TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  varo);    //Holds the text values that represent collaterals
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
               JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
         
             break;
                              case 7:

             if(  authoriserIdjTextField1.getText().equals("")||loanCyclejTextField9.getText().equals("")||InterestRatejTextField.getText().equals("")||instalmentStaerDatejTextField3.getText().equals("")||borrowerAccountNamejTextField4.getText().equals("")||instalmentEndDatejTextField5.getText().equals("")||loanTenurejTextField6.getText().equals("")){JOptionPane.showMessageDialog(this, "Please input a valid collateral evidance serial Number");return;}
          
          else{
              
                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String var="";
           int i=1;
              for(;i<=7;i++){
                
             if(i==1){
             var=authoriserIdjTextField1.getText();
             }   
             
            
              if(i==2){
                var=loanCyclejTextField9.getText();   
                  
              }    
    if(i==3){
                var=InterestRatejTextField.getText();   
                  
              }  
              
     if(i==4){
                var=instalmentStaerDatejTextField3.getText();   
                  
              }  
     if(i==5){
                var=borrowerAccountNamejTextField4.getText();   
                  
              }
      if(i==6){
                var=instalmentEndDatejTextField5.getText();   
                  
              }
      if(i==7){
                var=loanTenurejTextField6.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  var); //Holds the text values that represent collaterals   
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
               JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
               ReturnRejectLoanDetailsCommentsjButton.setEnabled(false);
                loanDetailsAuthorisationPanel.setVisible(true);
    homeAuthorisationTablePanel.setVisible(false);   
    titleCollateraljLabel.setVisible(true);
          }                      
                                  
                                    case 8:
 
                  fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"));//Holds the text values that represent collaterals
           String varj="";
           int ij=1;
              for(;ij<=8;ij++){
                
             if(ij==1){
             varj=collateral1TextField.getText();
             }   
             
            
              if(ij==2){
                varj=collateral2TextField.getText();   
                  
              }    
    if(ij==3){
                varj=collateral3TextField.getText();   
                  
              }  
              
     if(ij==4){
                varj=collateral4TextField.getText();   
                  
              }  
     if(ij==5){
                varj=collateral5TextField.getText();   
                  
              }
      if(ij==6){
                varj=collateral6TextField.getText();   
                  
              }
      if(ij==7){
                varj=collateral7TextField.getText();   
                  
              }
      if(ij==8){
                varj=collateral8TextField.getText();   
                  
              }
           fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  varj);  //Holds the text values that represent collaterals  
          fios.stringFileWriterAppend(fios.createFileName("loanApplication", "collateralSDocumentation", "colls"+activeaccount+".txt"),  ";"); //Holds the text values that represent collaterals
              } 
               JOptionPane.showMessageDialog(this, "Collateral Rejected Successfully!!!");
            
             break;
    } 
     
     
if(fios.intFileReader(fios.createFileName("loanAuthorisation", "displayWindow","loanInitialisation"+activeaccount+".txt"))==20){//Holds a value which indicates that the loan authorisation table is being accessed for the first time
        loanDetailsButtonHome.setEnabled(true);
        loanCollateraljButtonHome.setEnabled(true);
        loanDocumentaionjButtonHome.setEnabled(true);
}
  
   
          Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
   
           
      Integer xcu=3;
    
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","collateral"+activeaccount+".txt"),xcu.toString());
  
   Integer xfb=2;
  fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisedCollateral"+ activeaccount+".txt"),xfb.toString());
   
   
     Integer zxs=2;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeaccount+".txt"),zxs.toString()); 
      
    
  Integer sdg=15;
 fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+activeaccount+".txt"), sdg.toString());
    
    
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_rejectCollateralButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    int i=1;
     while(i<=5){
     
     JOptionPane.showMessageDialog(this, "SYSTEM LOG OUT ERROR!!!!!!!!!");}
    }//GEN-LAST:event_formWindowClosing

    private void homeLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeLoanDetailsButtonActionPerformed
     Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
      homeAuthorisationTablePanel.setVisible(true);
     loanDetailsAuthorisationPanel.setVisible(false);
     collateralAuthorisationPanel.setVisible(false); 
     documentAuthorisationPanel.setVisible(false);
      loanDetailsRejectionPanel.setVisible(false);  
      
    }//GEN-LAST:event_homeLoanDetailsButtonActionPerformed

    private void approveLoanLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveLoanLoanDetailsButtonActionPerformed

//        String activeAccountf =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"));  //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
//      String loandetails =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccountf+".txt"));   
//        
//        if(approveLoanLoanDetailsButton.getText().equalsIgnoreCase("Approve Loan Request")&&approveLoanLoanDetailsButton.isEnabled()==true){   
//        loan.updateSubmited(activeAccountf,"Approved"); 
//       Integer xsd=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xsd.toString());
//    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+ ".txt"), "4");
//   
//   fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
//// fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisorId"+activeAccountf+".txt"),this.userId);
//  
//      Integer zxsd=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxsd.toString()); 
// approveLoanLoanDetailsButton.setEnabled(false);
//
//  fios.intFileWriterReplace(fios.createFileName("logOut", "pdffile","authorisorId"+activeAccountf+".txt"),this.userId);
//  
//    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//     sendsms.createLoanApprovedSMS(activeAccountf, loandetails.split("[,]", 16)[1]+"");
//  
//     
//}
//    fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
//  
//      Integer zxso=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxso.toString());   
// approveLoanLoanDetailsButton.setEnabled(false);
// 
//  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"))==4){
//     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//          loan.updateLoanStoreAll(userId+"", activeAccountf,"Approved",fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccountf+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));   
//             
//             return null;
//         }
//     
//     };
//     updateLoanStore.execute();
//     }   
//          
//        List loanDetails= new ArrayList();
//       loanDetails.add(loandetails.split("[,]", 16)[1]);//Princimpal amount
//         loanDetails.add(loandetails.split("[,]", 16)[2]);//interest amount
//  loanDetails.add(loandetails.split("[,]", 16)[4]);//instalment start date
//  loanDetails.add(loandetails.split("[,]", 16)[5]);//instalment start date
//    loanDetails.add(loandetails.split("[,]", 16)[0]);//instalment start date
//   loanDetails.add("Approved");//loan cycle status
//    loanDetails.add(dbq.AccountName(activeAccountf));//loan cycle status
//    loanDetails.add(activeAccountf);//loan cycle status
//    
//    
//      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//           loan.updateLoanReportATable(loanDetails); 
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();
// 
//
//  LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();
//   
//   } else if(approveLoanLoanDetailsButton.getText().equalsIgnoreCase("Reject Loan Request")&&approveLoanLoanDetailsButton.isEnabled()==true){
//    Integer xlf=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xlf.toString());
//   
//   
//    
//    fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
//    
//        
// 
//fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+".txt"),"3");
//     
//  
//              
//                        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDeclined.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
// 
//   SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        sendsms.createLoanDeclinedSMS(activeAccountf, loandetails.split("[,]", 16)[1]+"");
//             
//             return null;
//         }
//     
//     };
//     updatesendSms.execute();
//
//    
//      SwingWorker<Void,Void>updatesendCleanHouse=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//       
//             cleanTableHouse(activeAccountf);
//             return null;
//         }
//     
//     };
//     updatesendCleanHouse.execute();
//
//}
//
//    
//
// 
//   LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
////         f.setUserID(userId);
//        f.pack();
//        this.dispose();
//}     
    }//GEN-LAST:event_approveLoanLoanDetailsButtonActionPerformed

    private void approveLoanDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveLoanDocumentButtonActionPerformed

//    String activeAccount =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"));  //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.     
//        if(approveLoanDocumentButton.getText().equalsIgnoreCase("Approve Loan Request")&&approveLoanDocumentButton.isEnabled()==true){
//            
//             
//               SwingWorker<Void,Void>updatesendupdateSubmit=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//       
//             loan.updateSubmited(activeAccount,"Approved");
//             return null;
//         }
//     
//     };
//     updatesendupdateSubmit.execute();
//        Integer xl=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
//   
//    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccount+ ".txt"), "4");
//// Integer mercy=1;
////fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccount+".txt"),mercy.toString()); 
//   
//  fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccount+".txt"));
//  
//      Integer zxs=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"))+".txt"),zxs.toString()); 
////     fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisorId"+activeAccount+".txt"),this.userId);
//} else if(approveLoanDocumentButton.getText().equalsIgnoreCase("Reject Loan Request")&&approveLoanDocumentButton.isEnabled()==true){
//    Integer xl=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
//   
//   fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccount+".txt"),"3"); 
//   
//  fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccount+".txt"));
//  
//     
//
////fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccount+".txt"),"4"); 
//}
//  
//  approveLoanDocumentButton.setEnabled(false);
//  
//    if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccount+".txt"))==4){
//     
//        
//        SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//          loan.updateLoanStoreAll(userId+"", activeAccount,"Approved",fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccount+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();
//    
//     
//     }   
//    String loandetails =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccount+".txt"));        
//        
//        
//  if(approveLoanDocumentButton.getText().equalsIgnoreCase("Approve Loan Request")&&approveLoanDocumentButton.isEnabled()==false){
//
//         
//        List loanDetails= new ArrayList();
//       loanDetails.add(loandetails.split("[,]", 16)[1]);//Princimpal amount
//         loanDetails.add(loandetails.split("[,]", 16)[2]);//interest amount
//  loanDetails.add(loandetails.split("[,]", 16)[4]);//instalment start date
//  loanDetails.add(loandetails.split("[,]", 16)[5]);//instalment start date
//    loanDetails.add(loandetails.split("[,]", 16)[0]);//instalment start date
//   loanDetails.add("Approved");//loan cycle status
//    loanDetails.add(dbq.AccountName(activeAccount));//loan cycle status
//    loanDetails.add(activeAccount);//loan cycle status
//    
//    
//
//  
//  SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//            loan.updateLoanReportATable(loanDetails); 
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();
//  
//  
//   if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//  
//         SwingWorker<Void,Void>updateApprovedSMS=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        sendsms.createLoanApprovedSMS(activeAccount, loandetails.split("[,]", 16)[1]+"");
//             return null;
//         }
//     
//     };
//     updateApprovedSMS.execute();
//      
//  
//}
//} else if(approveLoanDocumentButton.getText().equalsIgnoreCase("Reject Loan Request")&&approveLoanDocumentButton.isEnabled()==false){
//    
//       if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDeclined.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//   
//            SwingWorker<Void,Void>updateDeclinedSMS=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        sendsms.createLoanDeclinedSMS(activeAccount, loandetails.split("[,]", 16)[1]+"");
//             return null;
//         }
//     
//     };
//     updateDeclinedSMS.execute();
//           
//           
//          
//  
//           
//       }
//       
//       
//       
//       
//        SwingWorker<Void,Void>updateCleanHouse=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//       cleanTableHouse(activeAccount);
//             return null;
//         }
//     
//     };
//     updateCleanHouse.execute();
//     
//    
//
//  
//}
//
//    
//
//
//
//   
//   LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose(); 
//  
    }//GEN-LAST:event_approveLoanDocumentButtonActionPerformed

    private void approveLoanCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveLoanCollateralButtonActionPerformed
//   String activeAccountf =  fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt")); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
//     String loandetails =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccountf+".txt"));        
//
//   
//   if(approveLoanCollateralButton.getText().equalsIgnoreCase("Approve Loan Request")&&approveLoanCollateralButton.isEnabled()==true){  
//      
//  fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+ ".txt"), "4");
//       
//       
//      
//       
//        SwingWorker<Void,Void>updateUpdateSubmitted=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        loan.updateSubmited(activeAccountf,"Approved");
//             return null;
//         }
//     
//     };
//     updateUpdateSubmitted.execute();
//        Integer xsd=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xsd.toString());
//   
//   
//    
//  fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
//  
//      Integer zxsd=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"))+".txt"),zxsd.toString()); 
// approveLoanCollateralButton.setEnabled(false);
////  fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","authorisorId"+activeAccountf+".txt"),this.userId);
//        
//
//
//        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//    
//            SwingWorker<Void,Void>updateSms=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//           sendsms.createLoanApprovedSMS(activeAccountf, loandetails.split("[,]", 16)[1]+"");
//             
//             return null;
//         }
//     
//     };
//     updateSms.execute();
//           
//  
//}
//      
//           
//        List loanDetails= new ArrayList();
//       loanDetails.add(loandetails.split("[,]", 16)[1]);//Princimpal amount
//         loanDetails.add(loandetails.split("[,]", 16)[2]);//interest amount
//  loanDetails.add(loandetails.split("[,]", 16)[4]);//instalment start date
//  loanDetails.add(loandetails.split("[,]", 16)[5]);//instalment start date
//    loanDetails.add(loandetails.split("[,]", 16)[0]);//instalment start date
//   loanDetails.add("Approved");//loan cycle status
//    loanDetails.add(dbq.AccountName(activeAccountf));//loan cycle status
//    loanDetails.add(activeAccountf);//loan cycle status
//  
//  
//   SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
//         
//     
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        loan.updateLoanReportATable(loanDetails); 
//             
//             return null;
//         }
//     
//     };
//     updateReportATable.execute();
//     
//  LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();
//   
//   } else if(approveLoanDocumentButton.getText().equalsIgnoreCase("Reject Loan Request")&&approveLoanDocumentButton.isEnabled()==true){
//       
//
//fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+".txt"),"3");  
//    
//
//       
// 
//    Integer xlf=1;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xlf.toString());
//   
//   
//   
//  fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
//  
//      Integer zxso=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+fios.stringFileReader(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"))+".txt"),zxso.toString());   
// approveLoanCollateralButton.setEnabled(false);
//  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"))==4){
//     
//    
//      SwingWorker<Void,Void>updateLoanStoreAll=new SwingWorker(){
//
//         @Override
//         protected Object doInBackground() throws Exception {
//         loan.updateLoanStoreAll(userId+"", activeAccountf,"Approved",fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+activeAccountf+".txt")).split("[,]", 16)[14].replaceAll("[,]", " "));
//             
//             return null;
//         }
//     
//     };
//     updateLoanStoreAll.execute();
//     }   
//        
//  
//
//    
//                 if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDeclined.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
//  
//                     
//                     
//  
//
//   SwingWorker<Void,Void>updatesmsOne=new SwingWorker(){
//
//         @Override
//         protected Object doInBackground() throws Exception {
//        sendsms.createLoanDeclinedSMS(activeAccountf, loandetails.split("[,]", 16)[1]+"");
//             
//             return null;
//         }
//     
//     };
//     updatesmsOne.execute();
//    
//     
//     SwingWorker<Void,Void>updateLoanClean=new SwingWorker(){
//
//         @Override
//         protected Object doInBackground() throws Exception {
//         cleanTableHouse(activeAccountf);
//             
//             return null;
//         }
//     
//     };
//     updateLoanClean.execute();
//              
//
//  
//}
//
//
//   LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();
//}     
//        
        
    }//GEN-LAST:event_approveLoanCollateralButtonActionPerformed

    private void homeCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeCollateralButtonActionPerformed
     Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
      homeAuthorisationTablePanel.setVisible(true);
     loanDetailsAuthorisationPanel.setVisible(false);
     collateralAuthorisationPanel.setVisible(false); 
     documentAuthorisationPanel.setVisible(false);
      loanDetailsRejectionPanel.setVisible(false);  
    
    }//GEN-LAST:event_homeCollateralButtonActionPerformed

    private void homeDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeDocumentButtonActionPerformed
       Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        homeAuthorisationTablePanel.setVisible(true);
     loanDetailsAuthorisationPanel.setVisible(false);
     collateralAuthorisationPanel.setVisible(false); 
     documentAuthorisationPanel.setVisible(false);
      loanDetailsRejectionPanel.setVisible(false);  
    }//GEN-LAST:event_homeDocumentButtonActionPerformed

    private void loanDetailsCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanDetailsCollateralButtonActionPerformed
     Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_loanDetailsCollateralButtonActionPerformed

    private void loanDetailsDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanDetailsDocumentButtonActionPerformed
         Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
     LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_loanDetailsDocumentButtonActionPerformed

    private void loanDetailsButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanDetailsButtonHomeActionPerformed

         Integer xl=2;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
     LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_loanDetailsButtonHomeActionPerformed

    private void documentApprovaCollateralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentApprovaCollateralButtonActionPerformed
    Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_documentApprovaCollateralButtonActionPerformed

    private void documentApprovalLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentApprovalLoanDetailsButtonActionPerformed
    Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
     LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_documentApprovalLoanDetailsButtonActionPerformed

    private void loanDocumentaionjButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanDocumentaionjButtonHomeActionPerformed
       Integer xl=4;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_loanDocumentaionjButtonHomeActionPerformed

    private void collateralApprovalDocumentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateralApprovalDocumentButtonActionPerformed
        Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose(); 
    }//GEN-LAST:event_collateralApprovalDocumentButtonActionPerformed

    private void loanCollateraljButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanCollateraljButtonHomeActionPerformed
         Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_loanCollateraljButtonHomeActionPerformed

    private void collateralApprovalLoanDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateralApprovalLoanDetailsButtonActionPerformed
  Integer xl=3;
   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());   
        
    LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//       userId f.setUserID(userId);
        this.dispose();
    }//GEN-LAST:event_collateralApprovalLoanDetailsButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         try {
             jTable1.print();
         } catch (PrinterException ex) {
             Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        List<List>  d=new ArrayList();  List c=new ArrayList();
        
        ListTableModel original = (ListTableModel)jTable1.getModel();
        
for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

d.add(c);

for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
   if(!original.getRow(targetRow).isEmpty()){
        if(jTable1.isCellSelected(targetRow, 0)){
    d.add(original.getRow(jTable1.convertRowIndexToModel(targetRow)));
        }
    }
}

   String theCounter=getCounter();     
        
writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
  
generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void princempalAmountamountField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_princempalAmountamountField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_princempalAmountamountField1ActionPerformed

    private void theLoanInstalmentamountFieldDebit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theLoanInstalmentamountFieldDebit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_theLoanInstalmentamountFieldDebit4ActionPerformed

    private void totalLoanAmountamountFieldDebit5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalLoanAmountamountFieldDebit5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalLoanAmountamountFieldDebit5ActionPerformed

    private void totalInterestamountFieldDebit6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalInterestamountFieldDebit6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalInterestamountFieldDebit6ActionPerformed

    private void documentApprovalLoanDetailsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentApprovalLoanDetailsButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_documentApprovalLoanDetailsButton1ActionPerformed

    private void collateralApprovalLoanDetailsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateralApprovalLoanDetailsButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_collateralApprovalLoanDetailsButton1ActionPerformed

    private void homeLoanDetailsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeLoanDetailsButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_homeLoanDetailsButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
              
        Object[] optionsSF = {"Yes", "No"};
                        int nSF = JOptionPane.showOptionDialog(this, "Are you sure you want to cancel"+" "+dbq.AccountName(fios.createFileName("loanApplication", "amortValues", "activeAccountApproval"+this.userId+".txt"))+"'s"+"  "+"Loan Approval?",
                                "Alert!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsSF, optionsSF[0]);
                        if (nSF == JOptionPane.YES_OPTION) {
                    LoanApprovals fo15 = new LoanApprovals(userId);
                    fo15.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    fo15.setSize(screen.getSize());
                    fo15.pack();
                    this.dispose();
                        } else if (nSF == JOptionPane.NO_OPTION) {
                            this.setVisible(true);
                        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

          
        List rejectedDetails=new ArrayList();
      
       String activeAccount =  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccountApproval"+userId+".txt"));  //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
              String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
              
        Object[] optionsSF = {"Yes", "No"};
                        int nSF = JOptionPane.showOptionDialog(this, "Are you sure you want to REJECT"+" "+dbq.AccountName(activeAccount)+"'s"+"  "+"Loan Approval?",
                                "Alert!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsSF, optionsSF[0]);
                        if (nSF == JOptionPane.YES_OPTION) {
         String rejectionReason=JOptionPane.showInputDialog("Please put the rejection Reaseon");                     
                    String rejectLoanId =   loan.closeRejectedLoanAccount(activeAccount);        
                    List loandetails = loan.getLoanDetails(trnId,this);
  
//        loan.updateSubmited(activeAccount,"Rejected"); 
        
     
       
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccount+ ".txt"), "4");
   
     
  
              
                        if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanDeclined.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
 
   SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        sendsms.createLoanDeclinedSMS(activeAccount, loandetails.get(4).toString(),LoanApprovals.this);
             return null; } };
     updatesendSms.execute();

}

             

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
             
     
   
    
      SwingWorker<Void,Void>updateReportATableAll=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
           loan.   updateLoanStoreAll(userId, activeAccount, "Rejected",rejectLoanId,trnId);
             
             return null;
         }
     
     };
     updateReportATableAll.execute();

       rejectedDetails.add(trnId);//Loan Trn ID:0
         rejectedDetails.add(rejectLoanId);//Loan ID:1
  rejectedDetails.add(loandetails.get(4).toString()); //Princimpal Amount:2
  rejectedDetails.add(loandetails.get(2).toString()); //Interest Rate:3
    rejectedDetails.add(loandetails.get(1).toString());//Loan Tenure:4
   rejectedDetails.add(rejectionReason);//Reason for Rejection:5
   rejectedDetails.add(loan.inputterId(trnId));//Inputter Id:6p
    rejectedDetails.add(userId);//Authorisor Id:7
    rejectedDetails.add(dbq.AccountName(activeAccount));//Borrower Name:8
    rejectedDetails.add(activeAccount); //Borrower Account:9
            SwingWorker<Void,Void>updateRejectedR=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        loan.createRejectedLoan(rejectedDetails);
             return null; } };
     updateRejectedR.execute();
     
     
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
        List loanDetails= new ArrayList();
       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
         loanDetails.add(loandetails.get(3).toString());//interest amount
  loanDetails.add(loandetails.get(6).toString());//instalment start date
  loanDetails.add(loandetails.get(7).toString());//instalment end date
    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
   loanDetails.add("Rejected");//loan cycle status
    loanDetails.add(dbq.AccountName(activeAccount));//Account Name
    loanDetails.add(activeAccount);//Account Number
    
    
      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
           loan.updateLoanReportATable(loanDetails); 
             
             return null;
         }
     
     };
     updateReportATable.execute();
   JOptionPane.showMessageDialog(this, dbq.AccountName(activeAccount)+"'s"+"  "+"Loan Approval was successfully REJECTED");
 
   LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
//         f.setUserID(userId);
        f.pack();
        this.dispose(); 
                        } else if (nSF == JOptionPane.NO_OPTION) {
                            this.setVisible(true);
                        }
        
        
    
               
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
 String activeAccountf =  fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeAccountApproval"+userId+".txt"));  //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
              String trnId=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+userId+".txt"));
              List loandetails = loan.getLoanDetails(trnId,this);
              
         switch(dbq.title(userId)){
            case "Loans Officer":
       
                break;
            case "Accountant":
                     

break;
            case "Supervisor":
      if(fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"))==7){
      
      loan.updateSubmited(activeAccountf,"Authorised");  
       
  
       
JOptionPane.showMessageDialog(this,  dbq.AccountName(activeAccountf)+"'s"+" "+"Loan Request submitted for further Approval");     
      }else{
       loan.updateSubmited(activeAccountf,"Approved"); 
       
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+ ".txt"), "4");
   
  
  
//      Integer zxsd=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxsd.toString()); 


//  fios.intFileWriterReplace(fios.createFileName("logOut", "pdffile","authorisorId"+activeAccountf+".txt"),userId);
  
    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
    
        
         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        sendsms.createLoanApprovedSMS(activeAccountf, loandetails.get(4).toString(),LoanApprovals.this);
             return null; } };
     updatesendSms.execute();
        
  
     
}
//    fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
  
      Integer zxso=4;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxso.toString());   
 approveLoanLoanDetailsButton.setEnabled(false);
 
  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"))==4){
     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
          loan.updateLoanStoreAll(userId+"", activeAccountf,"Approved","newloan"+activeAccountf,trnId);   
             
             return null;  } };
     updateLoanStore.execute();
     }   
               
     
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
        List loanDetails= new ArrayList();
       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
         loanDetails.add(loandetails.get(3).toString());//interest amount
  loanDetails.add(loandetails.get(6).toString());//instalment start date
  loanDetails.add(loandetails.get(7).toString());//instalment end date
    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
   loanDetails.add("Approved");//loan cycle status
    loanDetails.add(dbq.AccountName(activeAccountf));//Account Name
    loanDetails.add(activeAccountf);//Account Number
    
    
      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
           loan.updateLoanReportATable(loanDetails); 
             
             return null;
         }
     
     };
     updateReportATable.execute();
       
  
       
JOptionPane.showMessageDialog(this,  dbq.AccountName(activeAccountf)+"'s"+" "+"Loan Request was sucessfully Aproved!!");     
      
      }
                
                break;
        
        
           case "Manager":
 loan.updateSubmited(activeAccountf,"Approved"); 
       
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+activeAccountf+ ".txt"), "4");
   
  
  
//      Integer zxsd=4;
//    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxsd.toString()); 


//  fios.intFileWriterReplace(fios.createFileName("logOut", "pdffile","authorisorId"+activeAccountf+".txt"),userId);
  
    if(fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "LoanApproved.txt"))==7||fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "AllLoanTransactions.txt"))==7){
    
        
         SwingWorker<Void,Void>updatesendSms=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
        sendsms.createLoanApprovedSMS(activeAccountf, loandetails.get(4).toString(),LoanApprovals.this);
             return null; } };
     updatesendSms.execute();
        
  
     
}
//    fios.deleteFileExistance(fios.createFileName("loanAuthorisation", "loanDetails","loanDetailsValues"+activeAccountf+".txt"));
  
      Integer zxso=4;
    fios.intFileWriterReplace(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"),zxso.toString());   
 approveLoanLoanDetailsButton.setEnabled(false);
 
  if(  fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoanApprovals"+activeAccountf+".txt"))==4){
     SwingWorker<Void,Void>updateLoanStore=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
          loan.updateLoanStoreAll(userId+"", activeAccountf,"Approved","newloan"+activeAccountf,trnId);   
             
             return null;  } };
     updateLoanStore.execute();
     }   
               
     
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
        List loanDetails= new ArrayList();
       loanDetails.add(loandetails.get(4).toString());//Princimpal amount
         loanDetails.add(loandetails.get(3).toString());//interest amount
  loanDetails.add(loandetails.get(6).toString());//instalment start date
  loanDetails.add(loandetails.get(7).toString());//instalment end date
    loanDetails.add(loandetails.get(1).toString());//Loan Tenure
   loanDetails.add("Approved");//loan cycle status
    loanDetails.add(dbq.AccountName(activeAccountf));//Account Name
    loanDetails.add(activeAccountf);//Account Number
    
    
      SwingWorker<Void,Void>updateReportATable=new SwingWorker(){
         @Override
         protected Object doInBackground() throws Exception {
           loan.updateLoanReportATable(loanDetails); 
             
             return null;
         }
     
     };
     updateReportATable.execute();
           
  
       
JOptionPane.showMessageDialog(this,  dbq.AccountName(activeAccountf)+"'s"+" "+"Loan Request was sucessfully Aproved!!");
                break;
        
        }
        
 

  LoanApprovals f = new LoanApprovals(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
//        f.setUserID(userId);
        this.dispose();
   
   
    }//GEN-LAST:event_jButton5ActionPerformed

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
//            java.util.logging.Logger.getLogger(LoanApprovals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoanApprovals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoanApprovals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoanApprovals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoanApprovals().setVisible(true);
//            }
//        });
//    }
//public synchronized void cleanTableHouse(String activeAccountf){
//
//loan.deleteLoanAppStore(activeAccountf);
//               loan.dropTableLoan(activeAccountf);
//               
//fios.deleteFileExistance(fios.createFileName("loanApplication","amortValues","loanStore"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","amortValues","amortizeStatus"+activeAccountf+".txt"));
//fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation","collDisplay"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanApplication" ,"collateralSDocumentation","colls"+activeAccountf+".txt")); 
//fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation","docDisplay"+activeAccountf+".txt"));
//fios.deleteFileExistance(fios.createFileName("loanApplication" ,"collateralSDocumentation","docs"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfCollateral"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfDocumentation"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfLoanApprovals"+activeAccountf+".txt"));
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfLoandetails"+activeAccountf+".txt"));
//
//fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfMovementDoc"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedCollateral"+activeAccountf+".txt"));
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedDocement"+activeAccountf+".txt"));
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedLoanDetails"+activeAccountf+".txt"));
//
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","collateral"+activeAccountf+".txt")); 
//
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","document"+activeAccountf+".txt"));
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","loandetails"+activeAccountf+".txt")); 
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","loanInitialisation"+activeAccountf+".txt")); 
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","rejectedComments"+activeAccountf+".txt")); 
//
//
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","userIdDetails","currentHolder"+activeAccountf+".txt")); 
// 
//fios.deleteFileExistance(fios.createFileName("loanAuthorisation","loanDetails","loanDetailsValues"+activeAccountf+".txt")); 
//
//
//}
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

 public void processTableDetails(){
          


 List     column1g =new ArrayList<>();
       column1g.add("S/N");
        column1g.add("LOAN ID");
         column1g.add("BORROWER");
       column1g.add("PRINCIPAL");
        column1g.add("INTEREST");
       column1g.add("PUT BY");
loan.loanAuthorisationDetails(jTable1, column1g); 

   HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton56.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton52.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton42.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton54.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton48.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton47.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton41.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton40.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton44.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton53.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton38.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton39.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton40.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton38.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable1,jTextField1);   

        int n=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(200);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==2){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(300);
        }
        if(n==3){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(200);
        }
         if(n==4){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
          if(n==5){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(100);
        }
        n++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


      
        this.setFont(new Font("Arial",Font.PLAIN,18));
        
if(col>=3 && col<=4){
 this.setHorizontalAlignment(RIGHT);

}
        if(col>=3 && col<=4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
     
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
        
        
        
        
   
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        
  
 
 }
 public void processTableDetails2(){
          


 List     column1g =new ArrayList<>();
       column1g.add("S/N");
        column1g.add("LOAN ID");
         column1g.add("BORROWER");
       column1g.add("PRINCIPAL");
        column1g.add("INTEREST");
       column1g.add("PUT BY");
loan.loanAuthorisationDetails2(jTable1, column1g); 

   HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton56.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton52.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton42.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton54.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton48.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton47.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton41.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton40.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton44.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton53.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton38.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton39.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton40.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton38.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable1,jTextField1);   

        int n=0;

jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(200);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(150);

        }
        if(n==1){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(250);
        }
        if(n==2){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(300);
        }
        if(n==3){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(200);
        }
         if(n==4){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
          if(n==5){
        jTable1.getColumnModel().getColumn(n).setMinWidth(0);
        jTable1.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable1.getColumnModel().getColumn(n).setPreferredWidth(100);
        }
        n++;

        }
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


      
        this.setFont(new Font("Arial",Font.PLAIN,18));
        
if(col>=3 && col<=4){
 this.setHorizontalAlignment(RIGHT);

}
        if(col>=3 && col<=4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
     
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
        
        
        
        
   
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
        
  
 
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApproveCollateralButton;
    private javax.swing.JButton ApproveDocumentButton;
    private javax.swing.JButton ApproveLoanDetailsButton;
    private javax.swing.JTextField InputterIdjTextField10;
    private javax.swing.JLabel InterestRatejLabel;
    private javax.swing.JLabel InterestRatejLabel1;
    private javax.swing.JTextField InterestRatejTextField;
    private javax.swing.JTextField InterestRatejTextField1;
    private javax.swing.JButton OkRejectLoanDetailsCommentsjButton;
    private javax.swing.JTextArea RejectLoanDetailsCommentsjTextArea;
    private javax.swing.JButton ReturnRejectLoanDetailsCommentsjButton;
    private javax.swing.JButton SaveRejectLoanDetailsCommentsjButton;
    private javax.swing.JLabel StatementRejectjLabel4;
    private javax.swing.JButton approveLoanCollateralButton;
    private javax.swing.JButton approveLoanDocumentButton;
    private javax.swing.JButton approveLoanLoanDetailsButton;
    private javax.swing.JLabel authoriserIdjLabel12;
    private javax.swing.JTextField authoriserIdjTextField1;
    private javax.swing.JLabel borrowerAccountNamejLabel11;
    private javax.swing.JTextField borrowerAccountNamejTextField4;
    private javax.swing.JCheckBox collateral1CheckBoxTest;
    private javax.swing.JTextField collateral1TextField;
    private javax.swing.JCheckBox collateral2CheckBoxTest;
    private javax.swing.JTextField collateral2TextField;
    private javax.swing.JCheckBox collateral3CheckBoxTest;
    private javax.swing.JTextField collateral3TextField;
    private javax.swing.JCheckBox collateral4CheckBoxTest;
    private javax.swing.JTextField collateral4TextField;
    private javax.swing.JCheckBox collateral5CheckBoxTest;
    private javax.swing.JTextField collateral5TextField;
    private javax.swing.JCheckBox collateral6CheckBoxTest;
    private javax.swing.JTextField collateral6TextField;
    private javax.swing.JCheckBox collateral7CheckBoxTest;
    private javax.swing.JTextField collateral7TextField;
    private javax.swing.JCheckBox collateral8CheckBoxTest;
    private javax.swing.JTextField collateral8TextField;
    private javax.swing.JButton collateralApprovalDocumentButton;
    private javax.swing.JButton collateralApprovalLoanDetailsButton;
    private javax.swing.JButton collateralApprovalLoanDetailsButton1;
    private javax.swing.JPanel collateralAuthorisationPanel;
    private javax.swing.JLabel collateralName1JLabel;
    private javax.swing.JLabel collateralName2JLabel;
    private javax.swing.JLabel collateralName3JLabel;
    private javax.swing.JLabel collateralName4JLabel;
    private javax.swing.JLabel collateralName5JLabel;
    private javax.swing.JLabel collateralName6JLabel;
    private javax.swing.JLabel collateralName7JLabel;
    private javax.swing.JLabel collateralName8JLabel;
    private javax.swing.JLabel collateralNameTitleJLabelJlabel;
    private javax.swing.JLabel collateralTitleJLabelCheckBox;
    private javax.swing.JLabel collateralTitleJLabelTextField;
    private javax.swing.JLabel collateralType1JLabel;
    private javax.swing.JLabel collateralType2JLabel;
    private javax.swing.JLabel collateralType3JLabel;
    private javax.swing.JLabel collateralType4JLabel;
    private javax.swing.JLabel collateralType5JLabel;
    private javax.swing.JLabel collateralType6JLabel;
    private javax.swing.JLabel collateralType7JLabel;
    private javax.swing.JLabel collateralType8JLabel;
    private javax.swing.JLabel collateralTypeTitleJLabelJlabel;
    private javax.swing.JCheckBox document1CheckBoxTest;
    private javax.swing.JLabel document1JLabel;
    private javax.swing.JTextField document1TextField;
    private javax.swing.JCheckBox document2CheckBoxTest;
    private javax.swing.JLabel document2JLabel;
    private javax.swing.JTextField document2TextField;
    private javax.swing.JCheckBox document3CheckBoxTest;
    private javax.swing.JLabel document3JLabel;
    private javax.swing.JTextField document3TextField;
    private javax.swing.JCheckBox document4CheckBoxTest;
    private javax.swing.JLabel document4JLabel;
    private javax.swing.JTextField document4TextField;
    private javax.swing.JCheckBox document5CheckBoxTest;
    private javax.swing.JLabel document5JLabel;
    private javax.swing.JTextField document5TextField;
    private javax.swing.JCheckBox document6CheckBoxTest;
    private javax.swing.JLabel document6JLabel;
    private javax.swing.JTextField document6TextField;
    private javax.swing.JCheckBox document7CheckBoxTest;
    private javax.swing.JLabel document7JLabel;
    private javax.swing.JTextField document7TextField;
    private javax.swing.JCheckBox document8CheckBoxTest;
    private javax.swing.JLabel document8JLabel;
    private javax.swing.JTextField document8TextField;
    private javax.swing.JButton documentApprovaCollateralButton;
    private javax.swing.JButton documentApprovalLoanDetailsButton;
    private javax.swing.JButton documentApprovalLoanDetailsButton1;
    private javax.swing.JPanel documentAuthorisationPanel;
    private javax.swing.JLabel documentTitleJLabelCheckBox;
    private javax.swing.JLabel documentTitleJLabelJlabel;
    private javax.swing.JLabel documentTitleJLabelTextField;
    private javax.swing.JPanel homeAuthorisationTablePanel;
    private javax.swing.JButton homeCollateralButton;
    private javax.swing.JButton homeDocumentButton;
    private javax.swing.JButton homeLoanDetailsButton;
    private javax.swing.JButton homeLoanDetailsButton1;
    private javax.swing.JLabel inputterIDjLabel18;
    private javax.swing.JLabel instalmentEndDatejLabel7;
    private javax.swing.JLabel instalmentEndDatejLabel8;
    private javax.swing.JTextField instalmentEndDatejTextField5;
    private javax.swing.JTextField instalmentEndDatejTextField6;
    private javax.swing.JTextField instalmentStaerDatejTextField3;
    private javax.swing.JTextField instalmentStaerDatejTextField4;
    private javax.swing.JLabel instalmentStartDatejLabel19;
    private javax.swing.JLabel instalmentStartDatejLabel20;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree2;
    private javax.swing.JButton loanCollateraljButtonHome;
    private javax.swing.JLabel loanCycleStatusjLabel16;
    private javax.swing.JTextField loanCyclejTextField9;
    private javax.swing.JPanel loanDetailsAuthorisationPanel;
    private javax.swing.JPanel loanDetailsAuthorisationPanel1;
    private javax.swing.JButton loanDetailsButtonHome;
    private javax.swing.JButton loanDetailsCollateralButton;
    private javax.swing.JButton loanDetailsDocumentButton;
    private javax.swing.JPanel loanDetailsRejectionPanel;
    private javax.swing.JButton loanDocumentaionjButtonHome;
    private javax.swing.JLabel loanIDjLabel1a0;
    private javax.swing.JTextField loanIDjTextField12;
    private javax.swing.JLabel loanSerialNumberjLabel17;
    private javax.swing.JTextField loanSerialNumberjTextField11;
    private javax.swing.JLabel loanTenurejLabel10;
    private javax.swing.JLabel loanTenurejLabel9;
    private javax.swing.JTextField loanTenurejTextField6;
    private javax.swing.JTextField loanTenurejTextField7;
    private javax.swing.JLabel noOfIndtalmentsjLabel;
    private javax.swing.JLabel noOfIndtalmentsjLabel1;
    private javax.swing.JTextField noOfIndtalmentsjTextField;
    private javax.swing.JTextField noOfIndtalmentsjTextField1;
    private javax.swing.JFormattedTextField princempalAmountamountField;
    private javax.swing.JFormattedTextField princempalAmountamountField1;
    private javax.swing.JLabel princempalAmountjLabel;
    private javax.swing.JLabel princempalAmountjLabel1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton rejectCollateralButton;
    private javax.swing.JButton rejectDocumentButton;
    private javax.swing.JButton rejectLoanDetailsButton;
    private javax.swing.JFormattedTextField theLoanInstalmentamountFieldDebit3;
    private javax.swing.JFormattedTextField theLoanInstalmentamountFieldDebit4;
    private javax.swing.JLabel theLoanInstalmentjLabel6;
    private javax.swing.JLabel theLoanInstalmentjLabel7;
    private javax.swing.JLabel titleCollateraljLabel;
    private javax.swing.JLabel titledocumentationjLabel;
    private javax.swing.JLabel titleloanDetailsjLabel;
    private javax.swing.JLabel titleloanDetailsjLabel1;
    private javax.swing.JLabel totalInterestAmountjLabel14;
    private javax.swing.JLabel totalInterestAmountjLabel15;
    private javax.swing.JFormattedTextField totalInterestamountFieldDebit5;
    private javax.swing.JFormattedTextField totalInterestamountFieldDebit6;
    private javax.swing.JFormattedTextField totalLoanAmountamountFieldDebit4;
    private javax.swing.JFormattedTextField totalLoanAmountamountFieldDebit5;
    private javax.swing.JLabel totalLoanAmountjLabel;
    private javax.swing.JLabel totalLoanAmountjLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
// if(e.getSource() == jTable2){  
//                
//                    int selectedRow =jTable2.getSelectedRow();
//                    int selectedColumn =jTable2.getSelectedColumn();
//		   if (selectedRow > -1&&selectedColumn>-1)
//	   {
//	 
//       Object cvalue = jTable2.getModel().getValueAt( jTable2.convertRowIndexToModel(selectedRow), 1);
//         String accountNumber = cvalue.toString().substring(7, 18);
//    
//       if(fios.intFileReader(fios.createFileName("loanAuthorisation", "displayWindow","loanInitialisation"+accountNumber+".txt"))==20){//Holds a value which indicates that the loan authorisation table is being accessed for the first time
//           
//     
//        
//           
//           
//           if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//          Integer xl= 3;
//            fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
//   
//         
//         }else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//    Integer xl=4;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());     
//   
//   
//         }else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//       Integer xl=2;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         }
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//          Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         
//         }  else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15 ){
//             
//           Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());         
//          JOptionPane.showMessageDialog(this, "Both loan details and collateral have been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//          else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//              
//            Integer xl=3;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());         
//          JOptionPane.showMessageDialog(this, "Both documentaion and collateral have  been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");  
//              
//          }
//         
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//         Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         JOptionPane.showMessageDialog(this, "All the three items have been rejected and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//          Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         JOptionPane.showMessageDialog(this, "Both documentation and loan details have been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//         
//         fios.stringFileWriter(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"),accountNumber); //It holds the account number of a loan selected from the table. Once a loan is selected, its account number is placed in this file for futher use in other parts.
//    
//    
//    
//    
//          
//    
//      LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose(); 
//      
//       }
//       
//       else {
//      if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//          Integer xl= 3;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());
//   
//         
//         }else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//    Integer xl=4;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());     
//   
//   
//         }else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//       Integer xl=2;
//   fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         }
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//          Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         
//         }  else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15 ){
//             
//           Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());         
//          JOptionPane.showMessageDialog(this, "Both loan details and collateral have been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//          else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==1){
//              
//            Integer xl=3;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());         
//          JOptionPane.showMessageDialog(this, "Both documentaion and collateral have  been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");  
//              
//          }
//         
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//         Integer xl=2;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         JOptionPane.showMessageDialog(this, "All the three items have been rejected and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//         else if(fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfCollateral"+accountNumber+".txt"))==1&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfDocumentation"+accountNumber+".txt"))==15&&fios.intFileReader(fios.createFileName("loanApplication", "fromLoanApprovals", "statusOfLoandetails"+accountNumber+".txt"))==15){
//          Integer xl=3;
//          fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationTable.txt"),xl.toString());       
//         JOptionPane.showMessageDialog(this, "Both documentation and loan details have been rejected by you and are pending to be edited."+"\n"+"Kindly contact your officer for resubmision");
//         }
//         
//         fios.stringFileWriter(fios.createFileName("loanAuthorisation", "displayWindow", "authorisationAccount.txt"),accountNumber); 
//    
//       
//       
//     fios.stringFileWriter(fios.createFileName("loanAuthorisation", "userIdDetails", "currentHolder"+accountNumber+".txt"),  this.userId);
//       
//    Integer xh=20;
//    fios.intFileWriterReplace(fios.createFileName("loanAuthorisation", "displayWindow","loanInitialisation"+accountNumber+".txt"),xh.toString());  //Holds a value which indicates that the loan authorisation table is being accessed for the first time 
//      
//      LoanApprovals f = new LoanApprovals(userId);
//        f.setVisible(true);
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        f.setSize(screen.getSize());
//        f.pack();
////        f.setUserID(userId);
//        this.dispose();  
//       
//       
//       
//       }
//        
//    }
// }
     if (e.getClickCount()==2){

  if(e.getSource()==jTable1){
  
  
    int selectedRow =jTable1.getSelectedRow();
                    int selectedColumn =jTable1.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	
       String loanId = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 1).toString();
         
     String trnLoanId = jTable1.getModel().getValueAt(jTable1.convertRowIndexToModel(selectedRow), 0).toString();
         
          
           String accountNumber = loanId.substring(7, 18);
           
         List loanDetails=loan.getLoanDetails(trnLoanId,this);
           
            noOfIndtalmentsjTextField1.setText(loanDetails.get(0).toString());
            loanTenurejTextField7.setText(loanDetails.get(1).toString());
            InterestRatejTextField1.setText(loanDetails.get(2).toString());
            totalInterestamountFieldDebit6.setValue(parseDouble(loanDetails.get(3).toString()));
            princempalAmountamountField1.setValue(parseDouble(loanDetails.get(4).toString()));
            totalLoanAmountamountFieldDebit5.setValue(parseDouble(loanDetails.get(5).toString()));
//            instalmentStaerDatejTextField4.setText(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(loanDetails.get(6).toString()));
//            instalmentEndDatejTextField6.setText(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(loanDetails.get(7).toString()));
              instalmentStaerDatejTextField4.setText(loanDetails.get(6).toString());
            instalmentEndDatejTextField6.setText(loanDetails.get(7).toString());      
            theLoanInstalmentamountFieldDebit4.setValue(parseDouble(loanDetails.get(8).toString()));
           column1 =new ArrayList<>();
       column1.add("INST.NO");
       column1.add("P AMT");
       column1.add("I AMT");
       column1.add("INST AMT");
       column1.add("INST. DD");
       column1.add("INST. PS");
       column1.add("INST. P");
      column1.add("INST. PD");
      column1.add("INST. PV (DAYS)");
  loan.loanClosedTableFill(jTable3, column1,loanId);   
           
         titleCollateraljLabel.setText("Loan Authorisation for:"+" "+dbq.AccountName(accountNumber));
                 
    fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeAccountApproval"+this.userId+".txt"),accountNumber); 
         fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "activeTrnId"+this.userId+".txt"),trnLoanId);    
    jPanel2.setVisible(false);
     jPanel3.setVisible(true);       
         refreshButton.setVisible(false);
           titleloanDetailsjLabel.setVisible(true);       
           
         HeaderRenderer header = new HeaderRenderer(jTable3.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton56.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton52.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton42.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton54.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton48.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton47.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton41.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton40.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton44.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton53.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton38.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton39.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton40.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton38.getBackground());   
                    break;  

}
     
        
        
     sortTable(jTable3,jTextField1);   

        int n=0;

jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
//        jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n<jTable3.getColumnModel().getColumnCount()){
        jTable3.getColumnModel().getColumn(n).setHeaderRenderer(header);

        if(n==0){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(200);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(80);

        }
        if(n==1){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        if(n==2){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        if(n==3){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
         if(n==4){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(150);
        }
          if(n==5){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(100);
        }
           if(n==6){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(100);
        }
            if(n==7){
        jTable3.getColumnModel().getColumn(n).setMinWidth(0);
        jTable3.getColumnModel().getColumn(n).setMaxWidth(500);
        jTable3.getColumnModel().getColumn(n).setPreferredWidth(180);
        }
        n++;

        }
        jTable3.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


      
        this.setFont(new Font("Arial",Font.PLAIN,18));
        
if(col>=1 && col<=3){
 this.setHorizontalAlignment(RIGHT);

}
        if(col>=1 && col<=3){
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        String text = NumberFormat.format(c );
        this.setText(text);
     
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
        
        
        
        
   
jTable4.setShowHorizontalLines(true);
jTable4.setShowGrid(true); 


         loan.loanSecurity(jTable4,loanId,this);  
         
          HeaderRenderer header2 = new HeaderRenderer(jTable4.getTableHeader().getDefaultRenderer()); 
        
        switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton36.setBackground(jButton56.getBackground());
                    break;
                case "2":
                    jButton36.setBackground(jButton52.getBackground()); 
                    
                    break;
                case "3":
                     jButton36.setBackground(jButton42.getBackground());
                    break;
                case "4":
                    jButton36.setBackground(jButton54.getBackground()); 
                    break;
                case "5":
                    jButton36.setBackground(jButton48.getBackground()); 
                    break;
                case "6":
                  jButton36.setBackground(jButton47.getBackground());   
                    break;
                case "7":
                   jButton36.setBackground(jButton41.getBackground());  
                    break;
                case "8":
                  jButton36.setBackground(jButton46.getBackground());   
                    break;
                case "9":
                   jButton36.setBackground(jButton40.getBackground());  
                    break;  
                case "10":
                    jButton36.setBackground(jButton44.getBackground()); 
                    break;
                case "11":
                     jButton36.setBackground(jButton53.getBackground());
                    break;
                case "12":
                    jButton36.setBackground(jButton39.getBackground()); 
                    break; 
                case "13":
                     jButton36.setBackground(jButton38.getBackground());
                    break;  
                case "14":
                   jButton36.setBackground(jButton45.getBackground());  
                    break; 
                case "15":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "16":
                  jButton36.setBackground(jButton39.getBackground());   
                    break;  
                case "17":
                     jButton36.setBackground(jButton40.getBackground());
                    break;
                case "18":
                     jButton36.setBackground(jButton53.getBackground());
                    break; 
                case "19":
                     jButton36.setBackground(jButton38.getBackground());
                    break;
                case "20":
                  jButton36.setBackground(jButton38.getBackground());   
                    break;  

}
//     
//        
//        
////     sortTable(jTable3,jTextField1);   
//
        int n2=0;

//       jTable1.getColumnModel().getColumns().nextElement().setResizable(false);
        while(n2<jTable4.getColumnModel().getColumnCount()){
        jTable4.getColumnModel().getColumn(n2).setHeaderRenderer(header2);

        if(n2==0){
        jTable4.getColumnModel().getColumn(n2).setMinWidth(0);
        jTable4.getColumnModel().getColumn(n2).setMaxWidth(100);
        jTable4.getColumnModel().getColumn(n2).setPreferredWidth(80);

        }
        if(n2==1){
        jTable4.getColumnModel().getColumn(n2).setMinWidth(0);
        jTable4.getColumnModel().getColumn(n2).setMaxWidth(200);
        jTable4.getColumnModel().getColumn(n2).setPreferredWidth(180);
        }
        if(n2==2){
        jTable4.getColumnModel().getColumn(n2).setMinWidth(0);
        jTable4.getColumnModel().getColumn(n2).setMaxWidth(800);
        jTable4.getColumnModel().getColumn(n2).setPreferredWidth(600);
        }
//        if(n2==3){
//        jTable4.getColumnModel().getColumn(n2).setMinWidth(0);
//        jTable4.getColumnModel().getColumn(n2).setMaxWidth(500);
//        jTable4.getColumnModel().getColumn(n2).setPreferredWidth(180);
//        }
         
        n2++;

        }
        jTable4.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


      
        this.setFont(new Font("Arial",Font.PLAIN,12));
//        
//if(col>=1 && col<=3){
// this.setHorizontalAlignment(RIGHT);
//
//}
//        if(col>=1 && col<=3){
//        Number c = (Number)parseDouble(value.toString());
//        String text = NumberFormat.fmt(c );
//        this.setText(text);
//     
//        }else{
        this.setText(value.toString());
//        }
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
           }
       
  }

  
    if(e.getSource()==jTree2){
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree2.getLastSelectedPathComponent();
    String window =node.toString();
    switch (window){
  
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
    
     Integer rs=35;
     fios.intFileWriterReplace(fios.createFileName("amortization", "flipWindows", "destWindow.txt"), rs.toString());
           amortDetails=new ArrayList();
        AmotizationCalculator frm5 = new AmotizationCalculator(userId,amortDetails);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
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
          case "Posting Module":

            Object[] optionsSFb = {"Continue",  "Cancel"};
            int nSFb = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
            "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSFb, optionsSFb[0]);
            if(nSFb==JOptionPane.YES_OPTION){
            PostingEntryWindow f = new PostingEntryWindow(userId,amortDetails);
            f.setVisible(true);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize(screen.getSize());
            f.pack();
            this.dispose();
            }
            else if (nSFb==JOptionPane.NO_OPTION){ this.setVisible(true);}
            break;
        case "Log Out": 
             Object[] optionsSGS = {"Continue",  "Cancel"};
        int nSGS = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
        if(nSGS==JOptionPane.YES_OPTION){
     
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
           PostingEntryWindow ff = new PostingEntryWindow (userId,amortDetails);
        ff.setVisible(true);
        Dimension screens = Toolkit.getDefaultToolkit().getScreenSize();
        ff.setSize(screens.getSize());
        ff.pack();
        this.dispose();
                
                
                break;
            case "Supervisor":
            PostingEntryWindow f = new PostingEntryWindow (userId,amortDetails);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();
                break;
        
        
           case "Manager":
               fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "ManagerLoggedIn.txt"), "49");
            Login fx = new Login();
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
        Object[] optionsSGS1 = {"Continue",  "Cancel"};
        int nSGS1 = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1, optionsSGS1[0]);
        if(nSGS1==JOptionPane.YES_OPTION){
    
          Integer xh=15;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "ApprovalReports.txt"), xh.toString());  
            
            
            
            Reportx frm5 = new Reportx(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
//         frm5.setUserID(userId);
        this.dispose();
        }
        else if (nSGS1==JOptionPane.NO_OPTION){ this.setVisible(true);}
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
        case "Operations Module":
        Object[] optionsSGS1v = {"Continue",  "Cancel"};
        int nSGS1v = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1v, optionsSGS1v[0]);
        if(nSGS1v==JOptionPane.YES_OPTION){
    
        
            
            OperationsModule frm5 = new OperationsModule(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
//         frm5.setUserID(userId);
        this.dispose();
        }
        else if (nSGS1v==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
        case "Email Module":
        Object[] optionsSGS1J = {"Continue",  "Cancel"};
        int nSGS1J = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS1J, optionsSGS1J[0]);
        if(nSGS1J==JOptionPane.YES_OPTION){
    
          Integer xh=15;
        fios.intFileWriterReplace(fios.createFileName("amortization", "allowUsage", "ApprovalReports.txt"), xh.toString());  
            
            
            
            email frm5 = new email(userId);
        frm5.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frm5.setSize(screen.getSize());
        frm5.pack();
//         frm5.setUserID(userId);
        this.dispose();
        }
        else if (nSGS1J==JOptionPane.NO_OPTION){ this.setVisible(true);}
        break;
      }
    
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
    public void insertUpdate(DocumentEvent e) {
    
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
     
   
      }

    @Override
    public void changedUpdate(DocumentEvent e) {
   
        
    }
}
