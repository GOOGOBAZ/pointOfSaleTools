/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.frames;

import googo.pmms.project2.reportsHelper.BalanceSheet;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.frameHelper.IUpdateText;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.reportsHelper.*;
import googo.pmms.project2.databases.ReportsDatabase;
import googo.pmms.project2.databases.DatabaseQuaries;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databases.AccountNumberCreationDataBase;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.excel.CreateExcelWorkBook;
import googo.pmms.project2.financialStatement.BudgetVarianceAnalysisReport;
import googo.pmms.project2.financialStatement.ProfitAndLossNew;
import googo.pmms.project2.financialStatement.TheBalanceSheet;
import googo.pmms.project2.frameHelper.AssetRegisterModel;
import googo.pmms.project2.frameHelper.HeaderRenderer;
import googo.pmms.project2.frameHelper.ListDataModel;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect;
import googo.pmms.project2.frameHelper.ListDataModelDailyCollect1;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport;
import googo.pmms.project2.frameHelper.ListDataModelPortfolioReport1;
import googo.pmms.project2.frameHelper.ListTableModel;
import googo.pmms.project2.frameHelper.ReportsModel;
import googo.pmms.project2.frameHelper.ReportsModelStatusReport;
import googo.pmms.project2.frameHelper.budgetEstimatesModel;
import googo.pmms.project2.frameHelper.loanManagementModel1;
import googo.pmms.project2.reportsHelper.BogaStatement;
import googo.pmms.project2.reportsHelper.OtherLoanReports;
import googo.pmms.project2.reportsHelper.loanStatement;
import googo.pmms.project2.smallFrames.SelectedAccountDetails;
import googo.pmms.project2.smallFrames.SummuryReportDetails;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
public class Reportx extends javax.swing.JFrame implements  IUpdateText, MouseListener,KeyListener{
   
    Formartter form= new Formartter();
    
    Component comp=this;

       List summuryReportDetails;
    
 SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdk =new SimpleDateFormat("dd.MM.yyyy");
    
      AccountNumberCreationDataBase ancdb=new AccountNumberCreationDataBase();
      
    ReportsPDFFormulars pdf = new ReportsPDFFormulars();
    
loanDatabaseQuaries loan=new loanDatabaseQuaries();

ReportsDatabase rdb = new ReportsDatabase();
  
     fileInputOutPutStreams fios= new fileInputOutPutStreams();
     

    
     DatabaseQuaries dbq= new DatabaseQuaries();
     
DecimalFormat df1 = new DecimalFormat("#.##");

DecimalFormat df2 = new DecimalFormat("#");

 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
 
     Calendar cal = Calendar.getInstance();
     
     netProfits netprofits= new netProfits();
     
     CreateExcelWorkBook writeExcel= new CreateExcelWorkBook();
     
    int n,s,counter=1;
ArrayList<Object> data4;

ArrayList<String>  column1, data,data1 ;

 ArrayList<ArrayList<Object>> data5;
   double loanAmount,loanTerm,interestRate, annualInterest=0.0,interest=0.0;
   Calendar startDate;
   ButtonGroup group1,group2,group3,group4;
   ObjectTableModel  model;
     SimpleDateFormat df;
  String text,summuryReportText,theMlAccount="";
  int realMonth, otherMonth;
   
   Integer Value,Value1;
   double newbalance,ledgerBalance,creditamount;
       JdbcConnector csx = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); 
    DatabaseQuaries quaries =new DatabaseQuaries();
    ReportsDatabase quary =new ReportsDatabase();
    Formartter fmt = new Formartter();
    BalanceSheet sheet= new BalanceSheet();
    ProfitAndLoss pAndL = new  ProfitAndLoss();
    trialBalance trialB= new trialBalance ();
    OtherReportsPdf other =new OtherReportsPdf();
    loanStatement lstat= new loanStatement();
    BogaStatement boga = new BogaStatement();
     OtherLoanReports otherLoans=new  OtherLoanReports();
     LoanSavingsSharesOthers loanSaveShare=new LoanSavingsSharesOthers();
        String userId;
         AssetRegisterModel assetRegisterModel1;
         ListDataModel result;
         List amortDetails;
         budgetEstimatesModel budgetResult;
         ListDataModelDailyCollect dailyCol;
            ListDataModelDailyCollect1 dailyCol1;
         ListTableModel sumDRepo;
           ListDataModelPortfolioReport porfolio;
           ListDataModelPortfolioReport1 porfolio1;
         Component c;
         ReportsModelStatusReport statusModel;
         
         String theAccount="",theAccountcn="",idNow="";
    /**
     * Creates new form Reportx
     * @param userId
    
     */
    public Reportx(String userId) {
        this.userId=userId;
        initComponents();
         java.awt.Image img = new ImageIcon(System.getProperty("user.dir")+"/"+"ICON_LOGO.jpg").getImage();
      this.setIconImage(img);
      this.setTitle("REPORTS WINDOW");
   
    jTree3.addMouseListener(this);
    jTable3.addMouseListener(this);
     jTable4.addMouseListener(this);
     jTable6.addMouseListener(this);
       jTable7.addMouseListener(this);
          jTable5.addMouseListener( this);
            jTree2.addMouseListener( this);
             jTree3.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
     jTree2.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      jTree3.addSelectionRow(5);
      jTree2.addSelectionRow(5);
      jTable3.setAlignmentX(LEFT_ALIGNMENT);
jTable3.setAlignmentY(CENTER_ALIGNMENT);
jTable3.setAutoscrolls(true);
jTable3.setShowHorizontalLines(true);
jTable3.setShowGrid(true);
jTable3.setRowHeight(40);
jTable3.setGridColor(Color.gray);
  
 jTable7.setAlignmentX(LEFT_ALIGNMENT);
jTable7.setAlignmentY(CENTER_ALIGNMENT);
jTable7.setAutoscrolls(true);
jTable7.setShowHorizontalLines(true);
jTable7.setShowGrid(true);
jTable7.setRowHeight(40);
jTable7.setGridColor(Color.gray);



        
         jTable8.setAlignmentX(LEFT_ALIGNMENT);
jTable8.setAlignmentY(CENTER_ALIGNMENT);
jTable8.setAutoscrolls(true);
jTable8.setShowHorizontalLines(true);
jTable8.setShowGrid(true);
jTable8.setRowHeight(40);
jTable8.setGridColor(Color.gray);

       jTable8.addMouseListener(this);

jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
jTable1.setRowHeight(40);
jTable1.setCellSelectionEnabled(false);
jTable1.setColumnSelectionAllowed(false);
jTable1.setRowSelectionAllowed(true);
jTable1.setAlignmentX(LEFT_ALIGNMENT);
jTable1.setAlignmentY(CENTER_ALIGNMENT);
jTable1.setAutoscrolls(true);
jTable1.setShowHorizontalLines(true);
jTable1.setShowGrid(true);
jTable1.setRowHeight(40);

jTable6.setAlignmentX(LEFT_ALIGNMENT);
jTable6.setAlignmentY(CENTER_ALIGNMENT);
jTable6.setAutoscrolls(true);
jTable6.setShowHorizontalLines(true);
jTable6.setShowGrid(true);
jTable6.setRowHeight(40);
jTable6.setCellSelectionEnabled(false);
jTable6.setColumnSelectionAllowed(false);
jTable6.setRowSelectionAllowed(true);
jTable6.setAlignmentX(LEFT_ALIGNMENT);
jTable6.setAlignmentY(CENTER_ALIGNMENT);
jTable6.setAutoscrolls(true);
jTable6.setShowHorizontalLines(true);
jTable6.setShowGrid(true);
jTable6.setRowHeight(40);

 jTable4.setAlignmentX(LEFT_ALIGNMENT);
jTable4.setAlignmentY(CENTER_ALIGNMENT);
jTable4.setAutoscrolls(true);
jTable4.setShowHorizontalLines(true);
jTable4.setShowGrid(true);
jTable4.setRowHeight(40);
jTable4.setGridColor(Color.gray);


 jTable5.setAlignmentX(LEFT_ALIGNMENT);
jTable5.setAlignmentY(CENTER_ALIGNMENT);
jTable5.setAutoscrolls(true);
jTable5.setShowHorizontalLines(true);
jTable5.setShowGrid(true);
jTable5.setRowHeight(40);
jTable5.setGridColor(Color.gray);
jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
     this.setHorizontalAlignment(JLabel.CENTER);
      if(col==4){
          Number c = (Number)parseDouble(value.toString());
       String text = NumberFormat.format(c );
           this.setText(text);
      
      }
      else{this.setText(value.toString());
      }
//      if(isSelected){setBackground(Color.CYAN);}
   
        return this;
    }   
});
    jTable1.addMouseListener(this);

    
         quickSumTable.setAlignmentX(LEFT_ALIGNMENT);
quickSumTable.setAlignmentY(CENTER_ALIGNMENT);
quickSumTable.setAutoscrolls(true);
quickSumTable.setShowHorizontalLines(true);
quickSumTable.setShowGrid(true);
quickSumTable.setRowHeight(40);
quickSumTable.setCellSelectionEnabled(false);
quickSumTable.setColumnSelectionAllowed(false);
quickSumTable.setRowSelectionAllowed(true);
quickSumTable.setAlignmentX(LEFT_ALIGNMENT);
quickSumTable.setAlignmentY(CENTER_ALIGNMENT);
quickSumTable.setAutoscrolls(true);
quickSumTable.setShowHorizontalLines(true);
quickSumTable.setShowGrid(true);
quickSumTable.setRowHeight(45);
        
        
 accountTable.setAlignmentX(LEFT_ALIGNMENT);
accountTable.setAlignmentY(CENTER_ALIGNMENT);
accountTable.setAutoscrolls(true);
accountTable.setShowHorizontalLines(true);
accountTable.setShowGrid(true);
accountTable.setRowHeight(40);
accountTable.setCellSelectionEnabled(false);
accountTable.setColumnSelectionAllowed(false);
accountTable.setRowSelectionAllowed(true);
accountTable.setAlignmentX(LEFT_ALIGNMENT);
accountTable.setAlignmentY(CENTER_ALIGNMENT);
accountTable.setAutoscrolls(true);
accountTable.setShowHorizontalLines(true);
accountTable.setShowGrid(true);
accountTable.setRowHeight(40);


accountTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int col) {
     this.setHorizontalAlignment(JLabel.CENTER);
      if(col==4){
          Number c = (Number)parseDouble(value.toString());
       String text = NumberFormat.format(c );
           this.setText(text);
      
      }
      else{this.setText(value.toString());  }
//      if(isSelected){setBackground(Color.CYAN);}
   
        return this;
    }   
});
   accountTable.addMouseListener(this);
   jTable2.addMouseListener(this);
//   jTextField17.addKeyListener(this);
    
     switch(dbq.title(userId)){
            case "Loans Officer":
////        OtherReportsSubPanel.setVisible(false);
//        OtherReportsPanel.setVisible(true);
//loanPortFolioButton.setEnabled(true);
//jButton83.setEnabled(true);
//assetsButto2.setEnabled(true);
//bogaStatementButton.setEnabled(true);
//jButton18.setEnabled(true);
//jButton19.setEnabled(true);
//jButton22.setEnabled(false);
//cashFlowButton2.setEnabled(false);
//loanApprovedButton.setEnabled(false);
//loanAppliedButton.setEnabled(false);
//jButton8.setEnabled(false);
//jButton51.setEnabled(false);
//loanPortFolioButton.setEnabled(false);
//jButton82.setEnabled(false);
////OtherReportsSubPanel.setVisible(false);
//balanceSheetButton2.setEnabled(false);
//        incomeStatementButton2.setEnabled(false);
//        trialBalanceButton2.setEnabled(false);
//                jButton91.setEnabled(false);
//                cashFlowButton4.setEnabled(false);
////                OtherReportsSubPanel.setVisible(false);
//                jLabel4.setVisible(false);
//                jLabel6.setVisible(false);
//                
//                
//balanceSheetButton6.setVisible(false);
//balanceSheetButton3.setVisible(false);
//jButton12.setVisible(false);
//jButton17.setVisible(false);
//jButton9.setVisible(false);
//liabilitiesButton1.setVisible(false);
//assetsButto1.setVisible(false);
//trialBalanceButton1.setVisible(false);
//balanceSheetButton1.setVisible(false);
//accountStatement1.setVisible(false);
//incomeStatementButton1.setVisible(false);
//jButton15.setVisible(false);
//cashFlowButton1.setVisible(false);



                break;
            case "Accountant":
//                     
//balanceSheetButton6.setVisible(false);
//balanceSheetButton3.setVisible(false);
//jButton12.setVisible(false);
//jButton17.setVisible(false);
//jButton9.setVisible(false);
//liabilitiesButton1.setVisible(false);
//assetsButto1.setVisible(false);
//trialBalanceButton1.setVisible(false);
//balanceSheetButton1.setVisible(false);
//accountStatement1.setVisible(false);
//incomeStatementButton1.setVisible(false);
//jButton15.setVisible(false);
//cashFlowButton1.setVisible(false);
//             
//
//                cashFlowButton4.setEnabled(false);
//                OtherReportsSubPanel.setVisible(false);
//                jLabel4.setVisible(false);
//                jLabel6.setVisible(false);
//                
//         loanStatement.setVisible(false);
//                 jButton73.setVisible(false);
//                 loansInArrearsButton.setVisible(false);
//loanPortFolioButton.setVisible(false);
//jButton83.setVisible(false);
//assetsButto2.setVisible(false);
//bogaStatementButton.setVisible(false);
//jButton18.setVisible(false);
//jButton19.setVisible(false);
//jButton22.setVisible(false);
//cashFlowButton2.setVisible(false);
//loanApprovedButton.setVisible(false);
//loanAppliedButton.setVisible(false);
//jButton8.setVisible(false);
//jButton51.setVisible(false);
//loanPortFolioButton.setVisible(false);
//jButton82.setVisible(false);
//jLabel5.setVisible(false);
break;
            case "Supervisor":
       
                break;
        
        
           case "Manager":

                break;
        
        }
    
    
    
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

        financialSupportPanel = new javax.swing.JPanel();
        cashFlowButton = new javax.swing.JButton();
        incomeStatementButton = new javax.swing.JButton();
        balanceSheetButton = new javax.swing.JButton();
        trialBalanceButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        accountStatement = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        BalancesReportSubPanel = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        liabilitiesButton3 = new javax.swing.JButton();
        assetsButto3 = new javax.swing.JButton();
        cashFlowButton3 = new javax.swing.JButton();
        incomeStatementButton3 = new javax.swing.JButton();
        trialBalanceButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        accountStatement3 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        balanceSheetButton4 = new javax.swing.JButton();
        balanceSheetButton5 = new javax.swing.JButton();
        BalancesPanel = new javax.swing.JPanel();
        StaffMembersPanel1 = new javax.swing.JPanel();
        LoginsPan1 = new javax.swing.JPanel();
        CustomersDemographyPanel1 = new javax.swing.JPanel();
        OverDrawnAccounts1 = new javax.swing.JPanel();
        customerByBalance1 = new javax.swing.JPanel();
        customerAccounts1 = new javax.swing.JPanel();
        nonCurrentAssetsAccounts1 = new javax.swing.JPanel();
        nonCurrentLiablitiesAccounts1 = new javax.swing.JPanel();
        StaffMembersPanel21 = new javax.swing.JPanel();
        StaffMembersPanel32 = new javax.swing.JPanel();
        StaffMembersPanel33 = new javax.swing.JPanel();
        StaffMembersPanel34 = new javax.swing.JPanel();
        StaffMembersPanel35 = new javax.swing.JPanel();
        StaffMembersPanel36 = new javax.swing.JPanel();
        StaffMembersPanel37 = new javax.swing.JPanel();
        StaffMembersPanel38 = new javax.swing.JPanel();
        StaffMembersPanel39 = new javax.swing.JPanel();
        ExpensesAccounts1 = new javax.swing.JPanel();
        currentLiabilitiesAccounts1 = new javax.swing.JPanel();
        currentAssetsAccounts1 = new javax.swing.JPanel();
        EquityAccounts1 = new javax.swing.JPanel();
        AssetAccounts1 = new javax.swing.JPanel();
        LiabilitiesAccounts1 = new javax.swing.JPanel();
        IncomeAccounts1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton58 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton120 = new javax.swing.JButton();
        jButton156 = new javax.swing.JButton();
        jButton157 = new javax.swing.JButton();
        jButton158 = new javax.swing.JButton();
        jButton159 = new javax.swing.JButton();
        jButton160 = new javax.swing.JButton();
        jButton161 = new javax.swing.JButton();
        jButton162 = new javax.swing.JButton();
        jButton163 = new javax.swing.JButton();
        jButton164 = new javax.swing.JButton();
        jButton165 = new javax.swing.JButton();
        jButton166 = new javax.swing.JButton();
        jButton167 = new javax.swing.JButton();
        jButton168 = new javax.swing.JButton();
        jButton169 = new javax.swing.JButton();
        jButton170 = new javax.swing.JButton();
        jButton171 = new javax.swing.JButton();
        jButton172 = new javax.swing.JButton();
        jButton173 = new javax.swing.JButton();
        jButton174 = new javax.swing.JButton();
        jButton175 = new javax.swing.JButton();
        jButton176 = new javax.swing.JButton();
        jButton177 = new javax.swing.JButton();
        jButton178 = new javax.swing.JButton();
        QuickSummuryPanel = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        quickSumTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel25 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        generalLedgerPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTree3 = new javax.swing.JTree();
        jLabel10 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jPanel24Table = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel96 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jPanel27All = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jButton219 = new javax.swing.JButton();
        jLabel94 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jButton221 = new javax.swing.JButton();
        jButton224 = new javax.swing.JButton();
        jButton225 = new javax.swing.JButton();
        jButton226 = new javax.swing.JButton();
        jPanel25ByLedger = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jButton217 = new javax.swing.JButton();
        jTextField54 = new javax.swing.JTextField();
        jButton218 = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jButton220 = new javax.swing.JButton();
        jButton216 = new javax.swing.JButton();
        jButton222 = new javax.swing.JButton();
        jButton223 = new javax.swing.JButton();
        financialSupportPanel1 = new javax.swing.JPanel();
        cashFlowButton4 = new javax.swing.JButton();
        incomeStatementButton2 = new javax.swing.JButton();
        balanceSheetButton2 = new javax.swing.JButton();
        trialBalanceButton2 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        accountStatement2 = new javax.swing.JButton();
        jButton91 = new javax.swing.JButton();
        cashFlowButton5 = new javax.swing.JButton();
        cashFlowButton6 = new javax.swing.JButton();
        FinancialReportsPanel = new javax.swing.JPanel();
        balanceSheetPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        valueDate8 = new javax.swing.JTextField();
        jButton38 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton76 = new javax.swing.JButton();
        jButton115 = new javax.swing.JButton();
        jLabel143 = new javax.swing.JLabel();
        pAndLPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        valueDate9 = new javax.swing.JTextField();
        jButton39 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        valueDate13 = new javax.swing.JTextField();
        jButton77 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton78 = new javax.swing.JButton();
        jButton116 = new javax.swing.JButton();
        jLabel142 = new javax.swing.JLabel();
        trialBalancePanel1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        valueDate12 = new javax.swing.JTextField();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton97 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        valueDate14 = new javax.swing.JTextField();
        jButton98 = new javax.swing.JButton();
        GeneralLedgerPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton25 = new javax.swing.JButton();
        jTextField58 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        accountStatementPanal1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();
        jTextField17 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jButton230 = new javax.swing.JButton();
        jButton231 = new javax.swing.JButton();
        jButton232 = new javax.swing.JButton();
        jButton233 = new javax.swing.JButton();
        accountStatementPanal = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        JBTrnSqNo4 = new javax.swing.JButton();
        debitAccountField1 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        valueDate1 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        valueDate = new javax.swing.JTextField();
        jButton28 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        amountFieldDebit2 = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton75 = new javax.swing.JButton();
        jButton114 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        budgetVarianceAnalysis = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jButton117 = new javax.swing.JButton();
        jTextField18 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jButton118 = new javax.swing.JButton();
        jButton119 = new javax.swing.JButton();
        AssetRegister = new javax.swing.JPanel();
        jButton40 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        loanReportsSubPanel = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        assetsButto2 = new javax.swing.JButton();
        cashFlowButton2 = new javax.swing.JButton();
        loanApprovedButton = new javax.swing.JButton();
        loanAppliedButton = new javax.swing.JButton();
        loanPortFolioButton = new javax.swing.JButton();
        loanStatement = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        bogaStatementButton = new javax.swing.JButton();
        jButton82 = new javax.swing.JButton();
        jButton83 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        loansInArrearsButton = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton238 = new javax.swing.JButton();
        bogaStatementButton1 = new javax.swing.JButton();
        jButton326 = new javax.swing.JButton();
        jButton327 = new javax.swing.JButton();
        loanReportsPanel = new javax.swing.JPanel();
        BOGAPanel = new javax.swing.JPanel();
        jButton50 = new javax.swing.JButton();
        loansDisbursedPanel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jButton99 = new javax.swing.JButton();
        jButton100 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        loansDue1 = new javax.swing.JPanel();
        jButton72 = new javax.swing.JButton();
        portfolioAtRisk = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton90 = new javax.swing.JButton();
        jButton85 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        loanPortFolioPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jButton92 = new javax.swing.JButton();
        jButton88 = new javax.swing.JButton();
        jButton105 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton55 = new javax.swing.JButton();
        jButton308 = new javax.swing.JButton();
        jButton323 = new javax.swing.JButton();
        loansDueForWriteOff = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton63 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton81 = new javax.swing.JButton();
        jButton94 = new javax.swing.JButton();
        jButton324 = new javax.swing.JButton();
        jButton325 = new javax.swing.JButton();
        loansCompletedPanel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jButton93 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jButton101 = new javax.swing.JButton();
        jButton104 = new javax.swing.JButton();
        loanStatementPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        JBTrnSqNo3 = new javax.swing.JButton();
        debitAccountField2 = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton48 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        loansAppliedPanel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton86 = new javax.swing.JButton();
        jButton95 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton227 = new javax.swing.JButton();
        jButton234 = new javax.swing.JButton();
        jButton235 = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        loanDisbursementSchedule = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jButton239 = new javax.swing.JButton();
        jButton240 = new javax.swing.JButton();
        jButton241 = new javax.swing.JButton();
        BOGAPanel1 = new javax.swing.JPanel();
        jButton319 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        loansApprovedPanel = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jButton57 = new javax.swing.JButton();
        loansDue = new javax.swing.JPanel();
        jButton59 = new javax.swing.JButton();
        jPanel51 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jButton309 = new javax.swing.JButton();
        jButton310 = new javax.swing.JButton();
        loansInArrears = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton84 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton80 = new javax.swing.JButton();
        jButton79 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton67 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jButton62 = new javax.swing.JButton();
        debitAccountField3 = new javax.swing.JFormattedTextField();
        jButton68 = new javax.swing.JButton();
        jButton69 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jButton121 = new javax.swing.JButton();
        jButton122 = new javax.swing.JButton();
        loansToStaffMembers = new javax.swing.JPanel();
        jButton66 = new javax.swing.JButton();
        summuryOnLoans = new javax.swing.JPanel();
        jButton49 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton61 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jButton123 = new javax.swing.JButton();
        jButton124 = new javax.swing.JButton();
        jButton208 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jButton209 = new javax.swing.JButton();
        jButton126 = new javax.swing.JButton();
        jButton127 = new javax.swing.JButton();
        BOGAPanel2 = new javax.swing.JPanel();
        jButton322 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jCheckBox4 = new javax.swing.JCheckBox();
        loansDisbursedPanel1 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jButton328 = new javax.swing.JButton();
        jButton329 = new javax.swing.JButton();
        jButton330 = new javax.swing.JButton();
        loansDisbursedPanel2 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jTextField91 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jButton331 = new javax.swing.JButton();
        jButton332 = new javax.swing.JButton();
        jButton333 = new javax.swing.JButton();
        jButton334 = new javax.swing.JButton();
        jButton335 = new javax.swing.JButton();
        jLabel148 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        OtherReportsSubPanel = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        liabilitiesButton1 = new javax.swing.JButton();
        assetsButto1 = new javax.swing.JButton();
        cashFlowButton1 = new javax.swing.JButton();
        incomeStatementButton1 = new javax.swing.JButton();
        trialBalanceButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        accountStatement1 = new javax.swing.JButton();
        balanceSheetButton1 = new javax.swing.JButton();
        balanceSheetButton3 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        balanceSheetButton6 = new javax.swing.JButton();
        balanceSheetButton7 = new javax.swing.JButton();
        balanceSheetButton8 = new javax.swing.JButton();
        balanceSheetButton9 = new javax.swing.JButton();
        balanceSheetButton10 = new javax.swing.JButton();
        OtherReportsPanel = new javax.swing.JPanel();
        LoginsPan = new javax.swing.JPanel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel102 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jButton237 = new javax.swing.JButton();
        jButton236 = new javax.swing.JButton();
        jLabel108 = new javax.swing.JLabel();
        CustomersDemographyPanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton107 = new javax.swing.JButton();
        jButton108 = new javax.swing.JButton();
        customerByBalancePanel = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jButton215 = new javax.swing.JButton();
        jButton228 = new javax.swing.JButton();
        jButton229 = new javax.swing.JButton();
        customerBySharesPanel = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        customerByContactDetailsPanel = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        OverDrawnPostionAccounts = new javax.swing.JPanel();
        jButton43 = new javax.swing.JButton();
        ExpensesAccounts = new javax.swing.JPanel();
        jButton42 = new javax.swing.JButton();
        EquityAccounts = new javax.swing.JPanel();
        jButton36 = new javax.swing.JButton();
        AssetAccounts = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        LiabilitiesAccounts = new javax.swing.JPanel();
        jButton35 = new javax.swing.JButton();
        IncomeAccounts = new javax.swing.JPanel();
        jButton37 = new javax.swing.JButton();
        StaffMembersPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        loanSavingsToday = new javax.swing.JPanel();
        jButton96 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        totalLoanShares = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton110 = new javax.swing.JButton();
        jButton111 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        totalLoanSavingsCombo2 = new javax.swing.JComboBox<>();
        totalLoanSavingsCombo1 = new javax.swing.JComboBox<>();
        jButton112 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton109 = new javax.swing.JButton();
        jButton113 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        loanPayments = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        totalLoanSavingsCombo5 = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        totalLoanSavingsCombo6 = new javax.swing.JComboBox<>();
        jButton195 = new javax.swing.JButton();
        jButton196 = new javax.swing.JButton();
        jButton197 = new javax.swing.JButton();
        savingsContributions = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        totalLoanSavingsCombo4 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        totalLoanSavingsCombo3 = new javax.swing.JComboBox<>();
        jButton193 = new javax.swing.JButton();
        jButton194 = new javax.swing.JButton();
        jButton198 = new javax.swing.JButton();
        returnOnInvestmentPanel = new javax.swing.JPanel();
        jButton129 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        totalLoanShares2 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jButton134 = new javax.swing.JButton();
        jButton135 = new javax.swing.JButton();
        jButton136 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jButton137 = new javax.swing.JButton();
        jButton147 = new javax.swing.JButton();
        totalLoanShares3 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jButton138 = new javax.swing.JButton();
        jButton139 = new javax.swing.JButton();
        jButton140 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jButton141 = new javax.swing.JButton();
        jButton148 = new javax.swing.JButton();
        jLabel75 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jButton182 = new javax.swing.JButton();
        totalLoanShares4 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jButton142 = new javax.swing.JButton();
        jButton143 = new javax.swing.JButton();
        jButton144 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jButton145 = new javax.swing.JButton();
        jButton149 = new javax.swing.JButton();
        totalLoanShares1 = new javax.swing.JPanel();
        jButton130 = new javax.swing.JButton();
        jButton131 = new javax.swing.JButton();
        jButton132 = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jButton133 = new javax.swing.JButton();
        jButton146 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jButton155 = new javax.swing.JButton();
        jTextField38 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        provisionForBadLoansPanel = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jButton179 = new javax.swing.JButton();
        jTextField40 = new javax.swing.JTextField();
        jButton180 = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jButton181 = new javax.swing.JButton();
        sharesSummuryPanel = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel29 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jButton151 = new javax.swing.JButton();
        jButton152 = new javax.swing.JButton();
        jButton185 = new javax.swing.JButton();
        jButton150 = new javax.swing.JButton();
        jButton184 = new javax.swing.JButton();
        jLabel80 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jButton192 = new javax.swing.JButton();
        jButton186 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jButton187 = new javax.swing.JButton();
        jButton188 = new javax.swing.JButton();
        jButton189 = new javax.swing.JButton();
        jButton190 = new javax.swing.JButton();
        jButton191 = new javax.swing.JButton();
        jLabel85 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        savingsSummuryPanel = new javax.swing.JPanel();
        individualSavings = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jButton154 = new javax.swing.JButton();
        jButton183 = new javax.swing.JButton();
        jButton201 = new javax.swing.JButton();
        jButton202 = new javax.swing.JButton();
        jButton203 = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jButton204 = new javax.swing.JButton();
        groupSavingsPanel = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jButton199 = new javax.swing.JButton();
        jButton200 = new javax.swing.JButton();
        jButton205 = new javax.swing.JButton();
        jButton206 = new javax.swing.JButton();
        jButton207 = new javax.swing.JButton();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel89 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jButton153 = new javax.swing.JButton();
        membersSelectionPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField39 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        returnOnInvestmentsButton = new javax.swing.JButton();
        jButton244 = new javax.swing.JButton();
        jButton245 = new javax.swing.JButton();
        returnOnInvestmentsButton1 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jButton246 = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        DailyCollections = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel38 = new javax.swing.JPanel();
        jButton102 = new javax.swing.JButton();
        jButton248 = new javax.swing.JButton();
        jLabel100 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jTextField65 = new javax.swing.JTextField();
        jButton271 = new javax.swing.JButton();
        jButton247 = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jTextField66 = new javax.swing.JTextField();
        jButton272 = new javax.swing.JButton();
        jButton273 = new javax.swing.JButton();
        jButton274 = new javax.swing.JButton();
        jButton275 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jTextField78 = new javax.swing.JTextField();
        jButton276 = new javax.swing.JButton();
        jButton277 = new javax.swing.JButton();
        jButton278 = new javax.swing.JButton();
        jButton279 = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jTextField79 = new javax.swing.JTextField();
        jButton280 = new javax.swing.JButton();
        jButton281 = new javax.swing.JButton();
        jButton282 = new javax.swing.JButton();
        jButton283 = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        jButton284 = new javax.swing.JButton();
        jButton285 = new javax.swing.JButton();
        jButton286 = new javax.swing.JButton();
        jLabel126 = new javax.swing.JLabel();
        jButton287 = new javax.swing.JButton();
        jLabel127 = new javax.swing.JLabel();
        jTextField80 = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jTextField81 = new javax.swing.JTextField();
        jButton288 = new javax.swing.JButton();
        jButton289 = new javax.swing.JButton();
        jButton290 = new javax.swing.JButton();
        jButton291 = new javax.swing.JButton();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jTextField82 = new javax.swing.JTextField();
        jButton292 = new javax.swing.JButton();
        jButton293 = new javax.swing.JButton();
        jButton294 = new javax.swing.JButton();
        jButton295 = new javax.swing.JButton();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jTextField83 = new javax.swing.JTextField();
        jButton296 = new javax.swing.JButton();
        jButton297 = new javax.swing.JButton();
        jButton298 = new javax.swing.JButton();
        jButton299 = new javax.swing.JButton();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jTextField84 = new javax.swing.JTextField();
        jButton300 = new javax.swing.JButton();
        jButton301 = new javax.swing.JButton();
        jButton302 = new javax.swing.JButton();
        jButton303 = new javax.swing.JButton();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jTextField85 = new javax.swing.JTextField();
        jButton304 = new javax.swing.JButton();
        jButton305 = new javax.swing.JButton();
        jButton306 = new javax.swing.JButton();
        jButton307 = new javax.swing.JButton();
        DailySummuryReport = new javax.swing.JPanel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jTextField88 = new javax.swing.JTextField();
        jButton317 = new javax.swing.JButton();
        jButton318 = new javax.swing.JButton();
        membersSelectionPanel1 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField77 = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        jPanel41SpecificSummuryReport = new javax.swing.JPanel();
        jButton311 = new javax.swing.JButton();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jTextField87 = new javax.swing.JTextField();
        jButton312 = new javax.swing.JButton();
        jTextField86 = new javax.swing.JTextField();
        jButton313 = new javax.swing.JButton();
        jButton314 = new javax.swing.JButton();
        jButton315 = new javax.swing.JButton();
        jButton316 = new javax.swing.JButton();
        returnOnInvestmentPanels = new javax.swing.JPanel();
        jComboBox12 = new javax.swing.JComboBox<>();
        GroupROI = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jButton249 = new javax.swing.JButton();
        jButton250 = new javax.swing.JButton();
        jButton251 = new javax.swing.JButton();
        jLabel110 = new javax.swing.JLabel();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jButton252 = new javax.swing.JButton();
        jButton253 = new javax.swing.JButton();
        jButton320 = new javax.swing.JButton();
        MonthlyIndividualROI = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jButton254 = new javax.swing.JButton();
        jButton255 = new javax.swing.JButton();
        jButton256 = new javax.swing.JButton();
        jLabel112 = new javax.swing.JLabel();
        jTextField69 = new javax.swing.JTextField();
        jTextField70 = new javax.swing.JTextField();
        jButton257 = new javax.swing.JButton();
        jButton258 = new javax.swing.JButton();
        jLabel113 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jButton259 = new javax.swing.JButton();
        GroupSavingsAndShares = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jButton260 = new javax.swing.JButton();
        jButton261 = new javax.swing.JButton();
        jButton262 = new javax.swing.JButton();
        jLabel115 = new javax.swing.JLabel();
        jTextField72 = new javax.swing.JTextField();
        jTextField73 = new javax.swing.JTextField();
        jButton263 = new javax.swing.JButton();
        jButton264 = new javax.swing.JButton();
        DailyIndividualROI = new javax.swing.JPanel();
        jButton265 = new javax.swing.JButton();
        jButton266 = new javax.swing.JButton();
        jButton267 = new javax.swing.JButton();
        jLabel116 = new javax.swing.JLabel();
        jTextField74 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jButton268 = new javax.swing.JButton();
        jButton269 = new javax.swing.JButton();
        jLabel117 = new javax.swing.JLabel();
        jButton270 = new javax.swing.JButton();
        jTextField76 = new javax.swing.JTextField();
        jLabel118 = new javax.swing.JLabel();
        jButton321 = new javax.swing.JButton();
        jComboBox13 = new javax.swing.JComboBox<>();
        summuryReportJPanel = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jButton243 = new javax.swing.JButton();
        jButton242 = new javax.swing.JButton();
        jButton103 = new javax.swing.JButton();

        financialSupportPanel.setBackground(new java.awt.Color(102, 102, 0));
        financialSupportPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        financialSupportPanel.setLayout(null);

        cashFlowButton.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton.setText("CASH FLOW STATEMENT");
        cashFlowButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButtonActionPerformed(evt);
            }
        });
        financialSupportPanel.add(cashFlowButton);
        cashFlowButton.setBounds(10, 240, 240, 30);
        cashFlowButton.setEnabled(false);
        cashFlowButton.setVisible(false);

        incomeStatementButton.setForeground(new java.awt.Color(0, 0, 153));
        incomeStatementButton.setText("P&L STATEMENT");
        incomeStatementButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        incomeStatementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeStatementButtonActionPerformed(evt);
            }
        });
        financialSupportPanel.add(incomeStatementButton);
        incomeStatementButton.setBounds(10, 120, 240, 30);

        balanceSheetButton.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton.setText("BALANCE SHEET");
        balanceSheetButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButtonActionPerformed(evt);
            }
        });
        financialSupportPanel.add(balanceSheetButton);
        balanceSheetButton.setBounds(10, 80, 240, 30);

        trialBalanceButton.setForeground(new java.awt.Color(0, 0, 153));
        trialBalanceButton.setText("TRIAL BALANCE");
        trialBalanceButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trialBalanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trialBalanceButtonActionPerformed(evt);
            }
        });
        financialSupportPanel.add(trialBalanceButton);
        trialBalanceButton.setBounds(10, 160, 240, 30);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SELECT FINANCIAL  REPORT");
        financialSupportPanel.add(jLabel3);
        jLabel3.setBounds(30, 0, 180, 40);

        accountStatement.setForeground(new java.awt.Color(0, 0, 153));
        accountStatement.setText("ACCOUNT STATEMENT");
        accountStatement.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountStatementActionPerformed(evt);
            }
        });
        financialSupportPanel.add(accountStatement);
        accountStatement.setBounds(10, 40, 240, 30);

        jButton30.setForeground(new java.awt.Color(0, 0, 153));
        jButton30.setText("GENERAL LEDGER");
        jButton30.setAutoscrolls(true);
        jButton30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        financialSupportPanel.add(jButton30);
        jButton30.setBounds(10, 200, 240, 30);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
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

        BalancesReportSubPanel.setBackground(new java.awt.Color(153, 0, 0));
        BalancesReportSubPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BalancesReportSubPanel.setLayout(null);

        jButton20.setForeground(new java.awt.Color(0, 0, 153));
        jButton20.setText("NON-CURRENT ASSETS BALANCES");
        jButton20.setAutoscrolls(true);
        jButton20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(jButton20);
        jButton20.setBounds(10, 240, 240, 30);

        jButton32.setForeground(new java.awt.Color(0, 0, 153));
        jButton32.setText("MAIN INCOME BALANCES");
        jButton32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(jButton32);
        jButton32.setBounds(10, 480, 240, 30);

        jButton13.setForeground(new java.awt.Color(0, 0, 153));
        jButton13.setText("OPERATING EXPENSES BALANCES");
        jButton13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(jButton13);
        jButton13.setBounds(10, 440, 240, 30);

        jButton33.setForeground(new java.awt.Color(0, 0, 153));
        jButton33.setText("OPERATING COSTS BALANCES");
        jButton33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BalancesReportSubPanel.add(jButton33);
        jButton33.setBounds(10, 400, 240, 30);

        jButton10.setForeground(new java.awt.Color(0, 0, 153));
        jButton10.setText("CURRENT LIABILITIES BALANCES");
        jButton10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(jButton10);
        jButton10.setBounds(10, 360, 240, 30);

        liabilitiesButton3.setForeground(new java.awt.Color(0, 0, 153));
        liabilitiesButton3.setText("NON-CURRENT LIABILITIES BALANCES");
        liabilitiesButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        liabilitiesButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesButton3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(liabilitiesButton3);
        liabilitiesButton3.setBounds(10, 320, 240, 30);

        assetsButto3.setForeground(new java.awt.Color(0, 0, 153));
        assetsButto3.setText("CURRENT ASSETS BALANCES");
        assetsButto3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        assetsButto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsButto3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(assetsButto3);
        assetsButto3.setBounds(10, 280, 240, 30);

        cashFlowButton3.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton3.setText("EQUITY BALANCES");
        cashFlowButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(cashFlowButton3);
        cashFlowButton3.setBounds(10, 120, 240, 30);

        incomeStatementButton3.setForeground(new java.awt.Color(0, 0, 153));
        incomeStatementButton3.setText("EXPENSES BALANCES");
        incomeStatementButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        incomeStatementButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeStatementButton3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(incomeStatementButton3);
        incomeStatementButton3.setBounds(10, 160, 240, 30);

        trialBalanceButton3.setForeground(new java.awt.Color(0, 0, 153));
        trialBalanceButton3.setText("LIABILITIES BALANCES");
        trialBalanceButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trialBalanceButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trialBalanceButton3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(trialBalanceButton3);
        trialBalanceButton3.setBounds(10, 80, 240, 30);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("SELECT BALANCES  REPORTS");
        BalancesReportSubPanel.add(jLabel12);
        jLabel12.setBounds(30, 0, 180, 40);

        accountStatement3.setForeground(new java.awt.Color(0, 0, 153));
        accountStatement3.setText("ASSET BALANCES");
        accountStatement3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatement3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountStatement3ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(accountStatement3);
        accountStatement3.setBounds(10, 40, 240, 30);

        jButton34.setForeground(new java.awt.Color(0, 0, 153));
        jButton34.setText("INVESTMENTS BALANCES");
        jButton34.setAutoscrolls(true);
        jButton34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BalancesReportSubPanel.add(jButton34);
        jButton34.setBounds(10, 520, 240, 30);

        balanceSheetButton4.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton4.setText("REVENUE BALANCES");
        balanceSheetButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton4ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(balanceSheetButton4);
        balanceSheetButton4.setBounds(10, 200, 240, 30);

        balanceSheetButton5.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton5.setText("OTHER INCOMES BALANCES");
        balanceSheetButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton5ActionPerformed(evt);
            }
        });
        BalancesReportSubPanel.add(balanceSheetButton5);
        balanceSheetButton5.setBounds(10, 560, 240, 30);

        getContentPane().add(BalancesReportSubPanel);
        BalancesReportSubPanel.setBounds(650, 910, 290, 130);
        BalancesReportSubPanel.setVisible(false);

        BalancesPanel.setBackground(new java.awt.Color(153, 153, 153));
        BalancesPanel.setLayout(null);

        StaffMembersPanel1.setLayout(null);
        BalancesPanel.add(StaffMembersPanel1);
        StaffMembersPanel1.setBounds(10, 10, 40, 50);

        LoginsPan1.setLayout(null);
        BalancesPanel.add(LoginsPan1);
        LoginsPan1.setBounds(60, 10, 40, 50);

        CustomersDemographyPanel1.setLayout(null);
        BalancesPanel.add(CustomersDemographyPanel1);
        CustomersDemographyPanel1.setBounds(110, 10, 40, 50);

        OverDrawnAccounts1.setLayout(null);
        BalancesPanel.add(OverDrawnAccounts1);
        OverDrawnAccounts1.setBounds(160, 10, 30, 50);

        customerByBalance1.setLayout(null);
        BalancesPanel.add(customerByBalance1);
        customerByBalance1.setBounds(200, 10, 40, 50);

        customerAccounts1.setLayout(null);
        BalancesPanel.add(customerAccounts1);
        customerAccounts1.setBounds(250, 10, 40, 50);

        nonCurrentAssetsAccounts1.setLayout(null);
        BalancesPanel.add(nonCurrentAssetsAccounts1);
        nonCurrentAssetsAccounts1.setBounds(10, 80, 40, 40);

        nonCurrentLiablitiesAccounts1.setLayout(null);

        StaffMembersPanel21.setLayout(null);

        StaffMembersPanel32.setLayout(null);

        StaffMembersPanel33.setLayout(null);

        StaffMembersPanel34.setLayout(null);

        StaffMembersPanel35.setLayout(null);
        StaffMembersPanel34.add(StaffMembersPanel35);
        StaffMembersPanel35.setBounds(10, 10, 40, 50);

        StaffMembersPanel36.setLayout(null);

        StaffMembersPanel37.setLayout(null);

        StaffMembersPanel38.setLayout(null);

        StaffMembersPanel39.setLayout(null);
        StaffMembersPanel38.add(StaffMembersPanel39);
        StaffMembersPanel39.setBounds(10, 10, 40, 50);

        StaffMembersPanel37.add(StaffMembersPanel38);
        StaffMembersPanel38.setBounds(10, 10, 40, 50);

        StaffMembersPanel36.add(StaffMembersPanel37);
        StaffMembersPanel37.setBounds(10, 10, 40, 50);

        StaffMembersPanel34.add(StaffMembersPanel36);
        StaffMembersPanel36.setBounds(10, 10, 40, 50);

        StaffMembersPanel33.add(StaffMembersPanel34);
        StaffMembersPanel34.setBounds(10, 10, 40, 50);

        StaffMembersPanel32.add(StaffMembersPanel33);
        StaffMembersPanel33.setBounds(10, 10, 40, 50);

        StaffMembersPanel21.add(StaffMembersPanel32);
        StaffMembersPanel32.setBounds(0, 10, 40, 50);

        nonCurrentLiablitiesAccounts1.add(StaffMembersPanel21);
        StaffMembersPanel21.setBounds(20, 90, 40, 50);

        BalancesPanel.add(nonCurrentLiablitiesAccounts1);
        nonCurrentLiablitiesAccounts1.setBounds(100, 80, 40, 40);

        ExpensesAccounts1.setLayout(null);
        BalancesPanel.add(ExpensesAccounts1);
        ExpensesAccounts1.setBounds(500, 10, 30, 40);

        currentLiabilitiesAccounts1.setLayout(null);
        BalancesPanel.add(currentLiabilitiesAccounts1);
        currentLiabilitiesAccounts1.setBounds(60, 80, 30, 40);

        currentAssetsAccounts1.setLayout(null);
        BalancesPanel.add(currentAssetsAccounts1);
        currentAssetsAccounts1.setBounds(540, 10, 30, 40);

        EquityAccounts1.setLayout(null);
        BalancesPanel.add(EquityAccounts1);
        EquityAccounts1.setBounds(400, 10, 40, 50);

        AssetAccounts1.setLayout(null);
        BalancesPanel.add(AssetAccounts1);
        AssetAccounts1.setBounds(300, 10, 40, 50);

        LiabilitiesAccounts1.setLayout(null);
        BalancesPanel.add(LiabilitiesAccounts1);
        LiabilitiesAccounts1.setBounds(350, 10, 40, 50);

        IncomeAccounts1.setLayout(null);
        BalancesPanel.add(IncomeAccounts1);
        IncomeAccounts1.setBounds(450, 10, 40, 40);

        getContentPane().add(BalancesPanel);
        BalancesPanel.setBounds(770, 880, 120, 70);
        BalancesPanel .setVisible(false);

        jLabel11.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel11.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel11.setText("BALANCES REPORTS");
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 690, 260, 40);
        jLabel11.setVisible(false);

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel2.setText("FINANCIAL REPORTS");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 690, 260, 40);
        jLabel2.setVisible(false);

        jButton58.setBackground(new java.awt.Color(153, 204, 255));
        jButton58.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton58.setText("Return To Home");
        jButton58.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton58);
        jButton58.setBounds(680, 690, 150, 40);

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel6.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel6.setText("OTHER REPORTS");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel6MousePressed(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 690, 260, 40);
        jLabel6.setVisible(false);

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel1.setText("LOAN REPORTS");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 690, 260, 40);
        jLabel1.setVisible(false);

        jButton120.setBackground(java.awt.SystemColor.activeCaption);
        jButton120.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton120.setText("REFRESH");
        jButton120.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton120ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton120);
        jButton120.setBounds(830, 690, 130, 40);

        jButton156.setBackground(new java.awt.Color(0, 51, 51));
        jButton156.setText("jButton5");
        getContentPane().add(jButton156);
        jButton156.setBounds(1800, 360, 73, 23);

        jButton157.setBackground(java.awt.SystemColor.controlLtHighlight);
        jButton157.setText("jButton5");
        getContentPane().add(jButton157);
        jButton157.setBounds(1790, 330, 73, 23);

        jButton158.setBackground(new java.awt.Color(255, 204, 204));
        jButton158.setText("jButton5");
        getContentPane().add(jButton158);
        jButton158.setBounds(1870, 320, 73, 23);

        jButton159.setBackground(new java.awt.Color(255, 204, 153));
        jButton159.setText("Blue");
        getContentPane().add(jButton159);
        jButton159.setBounds(1810, 310, 53, 23);

        jButton160.setBackground(new java.awt.Color(204, 204, 0));
        jButton160.setText("Blue");
        getContentPane().add(jButton160);
        jButton160.setBounds(1810, 290, 53, 23);

        jButton161.setBackground(new java.awt.Color(0, 153, 153));
        jButton161.setText("Blue");
        getContentPane().add(jButton161);
        jButton161.setBounds(1870, 290, 53, 23);

        jButton162.setBackground(new java.awt.Color(204, 255, 204));
        jButton162.setText("Blue");
        getContentPane().add(jButton162);
        jButton162.setBounds(1930, 290, 53, 23);

        jButton163.setBackground(new java.awt.Color(0, 204, 204));
        jButton163.setText("Blue");
        getContentPane().add(jButton163);
        jButton163.setBounds(1920, 270, 53, 23);

        jButton164.setBackground(new java.awt.Color(204, 153, 255));
        jButton164.setText("Blue");
        getContentPane().add(jButton164);
        jButton164.setBounds(1870, 270, 53, 23);

        jButton165.setBackground(new java.awt.Color(152, 198, 94));
        jButton165.setText("Blue");
        getContentPane().add(jButton165);
        jButton165.setBounds(1810, 270, 53, 23);

        jButton166.setBackground(new java.awt.Color(255, 255, 204));
        jButton166.setText("Blue");
        getContentPane().add(jButton166);
        jButton166.setBounds(1870, 250, 53, 23);

        jButton167.setBackground(new java.awt.Color(204, 204, 204));
        jButton167.setText("Blue");
        getContentPane().add(jButton167);
        jButton167.setBounds(1870, 230, 53, 23);

        jButton168.setBackground(new java.awt.Color(204, 165, 165));
        jButton168.setText("Blue");
        getContentPane().add(jButton168);
        jButton168.setBounds(1870, 210, 53, 23);

        jButton169.setBackground(new java.awt.Color(0, 204, 255));
        jButton169.setText("Blue");
        getContentPane().add(jButton169);
        jButton169.setBounds(1920, 250, 53, 23);

        jButton170.setBackground(new java.awt.Color(0, 204, 102));
        jButton170.setText("Blue");
        getContentPane().add(jButton170);
        jButton170.setBounds(1920, 230, 53, 23);

        jButton171.setBackground(new java.awt.Color(204, 204, 255));
        jButton171.setText("Blue");
        getContentPane().add(jButton171);
        jButton171.setBounds(1920, 210, 53, 23);

        jButton172.setBackground(java.awt.SystemColor.activeCaption);
        jButton172.setText("Blue");
        getContentPane().add(jButton172);
        jButton172.setBounds(1920, 190, 53, 23);

        jButton173.setBackground(java.awt.SystemColor.activeCaption);
        jButton173.setText("Blue");
        getContentPane().add(jButton173);
        jButton173.setBounds(1870, 190, 53, 23);

        jButton174.setBackground(new java.awt.Color(201, 222, 223));
        jButton174.setText("Blue");
        getContentPane().add(jButton174);
        jButton174.setBounds(1870, 170, 53, 23);

        jButton175.setBackground(java.awt.SystemColor.activeCaption);
        jButton175.setText("Blue");
        getContentPane().add(jButton175);
        jButton175.setBounds(1810, 210, 53, 23);

        jButton176.setBackground(new java.awt.Color(255, 204, 204));
        jButton176.setText("Blue");
        getContentPane().add(jButton176);
        jButton176.setBounds(1810, 250, 53, 23);

        jButton177.setBackground(new java.awt.Color(0, 153, 255));
        jButton177.setText("Blue");
        getContentPane().add(jButton177);
        jButton177.setBounds(1810, 230, 53, 23);

        jButton178.setBackground(java.awt.SystemColor.activeCaption);
        jButton178.setText("Blue");
        getContentPane().add(jButton178);
        jButton178.setBounds(1810, 190, 53, 23);

        QuickSummuryPanel.setBackground(java.awt.SystemColor.activeCaption);
        QuickSummuryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        QuickSummuryPanel.setLayout(null);

        quickSumTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(quickSumTable);

        QuickSummuryPanel.add(jScrollPane9);
        jScrollPane9.setBounds(10, 80, 870, 510);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Arial", 0, 28)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(102, 153, 255));
        jTextPane1.setAutoscrolls(false);
        jScrollPane10.setViewportView(jTextPane1);

        QuickSummuryPanel.add(jScrollPane10);
        jScrollPane10.setBounds(10, 0, 870, 80);

        getContentPane().add(QuickSummuryPanel);
        QuickSummuryPanel.setBounds(990, 690, 190, 40);
        QuickSummuryPanel.setVisible(true);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setForeground(new java.awt.Color(255, 102, 0));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel25.setLayout(null);

        jPanel27.setBackground(java.awt.SystemColor.activeCaption);
        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27.setLayout(null);

        generalLedgerPanel2.setBackground(java.awt.SystemColor.activeCaption);
        generalLedgerPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        generalLedgerPanel2.setLayout(null);

        jTree2.setBackground(java.awt.SystemColor.activeCaption);
        jTree2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane3.setViewportView(jTree2);
        jTree2.setVisible(false);

        generalLedgerPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(40, 10, 30, 40);
        jScrollPane3.setVisible(false);

        jTree3.setBackground(java.awt.SystemColor.activeCaption);
        jTree3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree3.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane4.setViewportView(jTree3);
        jTree3.setVisible(false);

        generalLedgerPanel2.add(jScrollPane4);
        jScrollPane4.setBounds(10, 10, 30, 40);
        jScrollPane4.setVisible(false);

        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 180, -25, 0));
        generalLedgerPanel2.add(jLabel10);
        jLabel10.setBounds(10, 10, 540, 40);

        jPanel23.setBackground(java.awt.SystemColor.activeCaption);
        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel23.setLayout(null);

        jLabel90.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel90.setText("CATEGORY OF LISTING");
        jLabel90.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel23.add(jLabel90);
        jLabel90.setBounds(20, 20, 200, 40);

        jComboBox10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIST ALL POSTED TXNS", "LIST TXNS FROM SPECIFIC LEDGER" }));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        jPanel23.add(jComboBox10);
        jComboBox10.setBounds(220, 20, 320, 40);

        jPanel24Table.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel24Table.setLayout(null);

        jTable5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(jTable5);

        jPanel24Table.add(jScrollPane8);
        jScrollPane8.setBounds(10, 10, 620, 470);

        jLabel96.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel96.setText("SEARCH HERE");
        jLabel96.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel24Table.add(jLabel96);
        jLabel96.setBounds(40, 480, 130, 30);

        jTextField27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel24Table.add(jTextField27);
        jTextField27.setBounds(170, 480, 320, 30);

        jPanel23.add(jPanel24Table);
        jPanel24Table.setBounds(20, 20, 690, 530);
        jPanel24Table.setVisible(false);

        jPanel27All.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27All.setLayout(null);

        jLabel93.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel93.setText("START DATE");
        jLabel93.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27All.add(jLabel93);
        jLabel93.setBounds(30, 40, 110, 40);

        jTextField55.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel27All.add(jTextField55);
        jTextField55.setBounds(160, 40, 230, 40);

        jButton219.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton219.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton219.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton219ActionPerformed(evt);
            }
        });
        jPanel27All.add(jButton219);
        jButton219.setBounds(390, 40, 40, 40);

        jLabel94.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel94.setText("END DATE");
        jLabel94.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27All.add(jLabel94);
        jLabel94.setBounds(30, 110, 110, 40);

        jTextField26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel27All.add(jTextField26);
        jTextField26.setBounds(160, 110, 230, 40);

        jButton221.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton221.setText("SUBMIT");
        jButton221.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton221ActionPerformed(evt);
            }
        });
        jPanel27All.add(jButton221);
        jButton221.setBounds(160, 170, 210, 40);

        jButton224.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton224.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton224.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton224ActionPerformed(evt);
            }
        });
        jPanel27All.add(jButton224);
        jButton224.setBounds(390, 110, 40, 40);

        jButton225.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton225.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jPanel27All.add(jButton225);
        jButton225.setBounds(270, 230, 160, 40);

        jButton226.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton226.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton226.setText("PDF");
        jPanel27All.add(jButton226);
        jButton226.setBounds(80, 230, 160, 40);

        jPanel23.add(jPanel27All);
        jPanel27All.setBounds(130, 60, 550, 330);
        jPanel27All.setVisible(false);

        jPanel25ByLedger.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel25ByLedger.setLayout(null);

        jLabel92.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel92.setText("THE LEDGER");
        jLabel92.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel25ByLedger.add(jLabel92);
        jLabel92.setBounds(20, 40, 140, 40);

        jLabel59.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel59.setText("END DATE");
        jLabel59.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel25ByLedger.add(jLabel59);
        jLabel59.setBounds(20, 160, 140, 40);

        jTextField53.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel25ByLedger.add(jTextField53);
        jTextField53.setBounds(170, 40, 180, 40);

        jButton217.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton217.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton217ActionPerformed(evt);
            }
        });
        jPanel25ByLedger.add(jButton217);
        jButton217.setBounds(360, 40, 40, 40);

        jTextField54.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel25ByLedger.add(jTextField54);
        jTextField54.setBounds(170, 160, 180, 40);

        jButton218.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton218.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton218.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton218ActionPerformed(evt);
            }
        });
        jPanel25ByLedger.add(jButton218);
        jButton218.setBounds(360, 160, 40, 40);

        jLabel95.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel95.setText("START DATE");
        jLabel95.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel25ByLedger.add(jLabel95);
        jLabel95.setBounds(20, 100, 140, 40);

        jTextField56.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel25ByLedger.add(jTextField56);
        jTextField56.setBounds(170, 100, 180, 40);

        jButton220.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton220.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton220.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton220ActionPerformed(evt);
            }
        });
        jPanel25ByLedger.add(jButton220);
        jButton220.setBounds(360, 100, 40, 40);

        jButton216.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton216.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton216.setText("Excel");
        jPanel25ByLedger.add(jButton216);
        jButton216.setBounds(260, 280, 180, 40);

        jButton222.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton222.setText("SUBMIT");
        jButton222.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton222ActionPerformed(evt);
            }
        });
        jPanel25ByLedger.add(jButton222);
        jButton222.setBounds(170, 220, 180, 40);

        jButton223.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton223.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton223.setText("PDF");
        jPanel25ByLedger.add(jButton223);
        jButton223.setBounds(60, 280, 180, 40);

        jPanel23.add(jPanel25ByLedger);
        jPanel25ByLedger.setBounds(130, 60, 550, 330);
        jPanel25ByLedger.setVisible(false);

        generalLedgerPanel2.add(jPanel23);
        jPanel23.setBounds(30, 20, 850, 570);
        jPanel23.setVisible(false);

        jPanel27.add(generalLedgerPanel2);
        generalLedgerPanel2.setBounds(270, 20, 930, 600);

        financialSupportPanel1.setBackground(java.awt.SystemColor.activeCaption);
        financialSupportPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        financialSupportPanel1.setLayout(null);

        cashFlowButton4.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton4.setText("CASH FLOW STATEMENT");
        cashFlowButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton4ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(cashFlowButton4);
        cashFlowButton4.setBounds(10, 280, 240, 30);
        cashFlowButton4.setVisible(false);

        incomeStatementButton2.setForeground(new java.awt.Color(0, 0, 153));
        incomeStatementButton2.setText("P&L STATEMENT");
        incomeStatementButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        incomeStatementButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeStatementButton2ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(incomeStatementButton2);
        incomeStatementButton2.setBounds(10, 130, 240, 30);

        balanceSheetButton2.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton2.setText("BALANCE SHEET");
        balanceSheetButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton2ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(balanceSheetButton2);
        balanceSheetButton2.setBounds(10, 160, 240, 30);

        trialBalanceButton2.setForeground(new java.awt.Color(0, 0, 153));
        trialBalanceButton2.setText("TRIAL BALANCE");
        trialBalanceButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trialBalanceButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trialBalanceButton2ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(trialBalanceButton2);
        trialBalanceButton2.setBounds(10, 70, 240, 30);

        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("SELECT FINANCIAL  REPORT");
        financialSupportPanel1.add(jLabel35);
        jLabel35.setBounds(30, 0, 180, 40);

        accountStatement2.setForeground(new java.awt.Color(0, 0, 153));
        accountStatement2.setText("ACCOUNT BALANCES & LEDGER STATEMENTS");
        accountStatement2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatement2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountStatement2ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(accountStatement2);
        accountStatement2.setBounds(10, 40, 240, 30);

        jButton91.setForeground(new java.awt.Color(0, 0, 153));
        jButton91.setText("GENERAL LEDGER");
        jButton91.setAutoscrolls(true);
        jButton91.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton91ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(jButton91);
        jButton91.setBounds(10, 100, 240, 30);

        cashFlowButton5.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton5.setText("BUDGGET VARIANCE ANALYSIS");
        cashFlowButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton5ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(cashFlowButton5);
        cashFlowButton5.setBounds(10, 190, 240, 30);
        cashFlowButton4.setVisible(false);

        cashFlowButton6.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton6.setText("ASSET REGISTER");
        cashFlowButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton6ActionPerformed(evt);
            }
        });
        financialSupportPanel1.add(cashFlowButton6);
        cashFlowButton6.setBounds(10, 220, 240, 30);
        cashFlowButton4.setVisible(false);

        jPanel27.add(financialSupportPanel1);
        financialSupportPanel1.setBounds(10, 20, 260, 600);

        FinancialReportsPanel.setBackground(java.awt.SystemColor.activeCaption);
        FinancialReportsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FinancialReportsPanel.setLayout(null);

        balanceSheetPanel.setBackground(java.awt.SystemColor.activeCaption);
        balanceSheetPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetPanel.setLayout(null);
        balanceSheetPanel.add(jLabel20);
        jLabel20.setBounds(160, 110, 520, 30);

        jPanel3.setBackground(java.awt.SystemColor.activeCaption);
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("VALUE DATE");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(jLabel21);
        jLabel21.setBounds(10, 30, 170, 30);

        valueDate8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        valueDate8.setForeground(new java.awt.Color(0, 204, 102));
        valueDate8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate8ActionPerformed(evt);
            }
        });
        jPanel3.add(valueDate8);
        valueDate8.setBounds(180, 30, 100, 30);
        valueDate1.setText("");

        jButton38.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton38);
        jButton38.setBounds(280, 30, 20, 30);

        jButton23.setBackground(new java.awt.Color(153, 204, 255));
        jButton23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton23.setText("Submmit");
        jButton23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton23);
        jButton23.setBounds(180, 80, 120, 30);

        jButton24.setBackground(new java.awt.Color(153, 204, 255));
        jButton24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton24.setText("Export Excel");
        jButton24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton24);
        jButton24.setBounds(170, 120, 150, 30);

        jButton76.setBackground(new java.awt.Color(153, 204, 255));
        jButton76.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton76.setText("Email Report");
        jButton76.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton76ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton76);
        jButton76.setBounds(160, 170, 150, 30);

        jButton115.setBackground(new java.awt.Color(153, 204, 255));
        jButton115.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton115.setText("Export PDF");
        jButton115.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton115ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton115);
        jButton115.setBounds(10, 170, 150, 30);

        balanceSheetPanel.add(jPanel3);
        jPanel3.setBounds(280, 160, 320, 220);

        jLabel143.setBackground(java.awt.SystemColor.activeCaption);
        jLabel143.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel143.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel143.setText("Balance Sheet");
        jLabel143.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetPanel.add(jLabel143);
        jLabel143.setBounds(370, 80, 110, 40);

        FinancialReportsPanel.add(balanceSheetPanel);
        balanceSheetPanel.setBounds(30, 30, 850, 560);
        balanceSheetPanel.setVisible(false);

        pAndLPanel.setBackground(java.awt.SystemColor.activeCaption);
        pAndLPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pAndLPanel.setLayout(null);
        pAndLPanel.add(jLabel23);
        jLabel23.setBounds(150, 120, 530, 30);

        jPanel4.setBackground(java.awt.SystemColor.activeCaption);
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(null);

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("START DATE");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(jLabel22);
        jLabel22.setBounds(20, 50, 170, 30);

        valueDate9.setForeground(new java.awt.Color(0, 204, 102));
        valueDate9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate9ActionPerformed(evt);
            }
        });
        jPanel4.add(valueDate9);
        valueDate9.setBounds(190, 50, 100, 30);
        valueDate9.setText(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt")));

        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton39);
        jButton39.setBounds(290, 50, 20, 30);

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("END DATE");
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(jLabel28);
        jLabel28.setBounds(20, 90, 170, 30);

        valueDate13.setForeground(new java.awt.Color(0, 204, 102));
        valueDate13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate13ActionPerformed(evt);
            }
        });
        jPanel4.add(valueDate13);
        valueDate13.setBounds(190, 90, 100, 30);
        valueDate13.setText("");

        jButton77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton77ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton77);
        jButton77.setBounds(290, 90, 20, 30);

        jButton27.setBackground(new java.awt.Color(153, 204, 255));
        jButton27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton27.setText("Export Excel");
        jButton27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton27);
        jButton27.setBounds(170, 170, 140, 30);

        jButton26.setBackground(new java.awt.Color(153, 204, 255));
        jButton26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton26.setText("Export PDF");
        jButton26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton26);
        jButton26.setBounds(20, 130, 150, 30);

        jButton78.setBackground(new java.awt.Color(153, 204, 255));
        jButton78.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton78.setText("Email Report");
        jButton78.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton78ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton78);
        jButton78.setBounds(20, 170, 150, 30);

        jButton116.setBackground(new java.awt.Color(153, 204, 255));
        jButton116.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton116.setText("Submmit");
        jButton116.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton116ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton116);
        jButton116.setBounds(190, 130, 120, 30);

        pAndLPanel.add(jPanel4);
        jPanel4.setBounds(240, 170, 350, 260);

        jLabel142.setBackground(java.awt.SystemColor.activeCaption);
        jLabel142.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel142.setForeground(java.awt.SystemColor.controlLtHighlight);
        jLabel142.setText("Profit And Loss");
        jLabel142.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pAndLPanel.add(jLabel142);
        jLabel142.setBounds(320, 50, 110, 40);

        FinancialReportsPanel.add(pAndLPanel);
        pAndLPanel.setBounds(30, 30, 850, 560);
        pAndLPanel.setVisible(false);

        trialBalancePanel1.setBackground(java.awt.SystemColor.activeCaption);
        trialBalancePanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trialBalancePanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        trialBalancePanel1.setLayout(null);
        trialBalancePanel1.add(jLabel27);
        jLabel27.setBounds(130, 140, 520, 30);

        jPanel14.setBackground(java.awt.SystemColor.activeCaption);
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("START DATE");
        jLabel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel26);
        jLabel26.setBounds(20, 20, 170, 30);

        valueDate12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        valueDate12.setForeground(new java.awt.Color(0, 204, 102));
        valueDate12.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate12ActionPerformed(evt);
            }
        });
        jPanel14.add(valueDate12);
        valueDate12.setBounds(190, 20, 100, 30);
        valueDate1.setText("");

        jButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton44);
        jButton44.setBounds(290, 20, 20, 30);

        jButton45.setBackground(new java.awt.Color(153, 204, 255));
        jButton45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton45.setText("Submmit");
        jButton45.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton45);
        jButton45.setBounds(190, 100, 120, 30);

        jButton46.setBackground(new java.awt.Color(153, 204, 255));
        jButton46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton46.setText("Email Report");
        jButton46.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton46);
        jButton46.setBounds(30, 140, 150, 30);

        jButton97.setBackground(new java.awt.Color(153, 204, 255));
        jButton97.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton97.setText("Export Excel");
        jButton97.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton97ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton97);
        jButton97.setBounds(180, 140, 150, 30);

        jLabel43.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("END DATE");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.add(jLabel43);
        jLabel43.setBounds(20, 60, 170, 30);

        valueDate14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        valueDate14.setForeground(new java.awt.Color(0, 204, 102));
        valueDate14.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate14ActionPerformed(evt);
            }
        });
        jPanel14.add(valueDate14);
        valueDate14.setBounds(190, 60, 100, 30);
        valueDate1.setText("");

        jButton98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton98ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton98);
        jButton98.setBounds(290, 60, 20, 30);

        trialBalancePanel1.add(jPanel14);
        jPanel14.setBounds(230, 200, 340, 190);

        FinancialReportsPanel.add(trialBalancePanel1);
        trialBalancePanel1.setBounds(30, 30, 850, 560);
        trialBalancePanel1.setVisible(false);

        GeneralLedgerPanel1.setBackground(java.awt.SystemColor.activeCaption);
        GeneralLedgerPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GeneralLedgerPanel1.setLayout(null);

        jTable3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable3.setForeground(new java.awt.Color(0, 102, 51));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable3);

        GeneralLedgerPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(100, 40, 700, 390);

        jButton25.setBackground(new java.awt.Color(153, 204, 255));
        jButton25.setText("Return To Chart");
        jButton25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        GeneralLedgerPanel1.add(jButton25);
        jButton25.setBounds(190, 560, 150, 30);

        jTextField58.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        GeneralLedgerPanel1.add(jTextField58);
        jTextField58.setBounds(370, 450, 190, 30);

        jLabel99.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel99.setText("SEARCH HERE");
        jLabel99.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GeneralLedgerPanel1.add(jLabel99);
        jLabel99.setBounds(230, 450, 140, 30);

        FinancialReportsPanel.add(GeneralLedgerPanel1);
        GeneralLedgerPanel1.setBounds(30, 30, 850, 560);
        GeneralLedgerPanel1 .setVisible(false);

        accountStatementPanal1.setBackground(java.awt.SystemColor.activeCaption);
        accountStatementPanal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatementPanal1.setLayout(null);

        accountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(accountTable);

        accountStatementPanal1.add(jScrollPane2);
        jScrollPane2.setBounds(0, 10, 800, 490);

        jTextField17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField17KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });
        accountStatementPanal1.add(jTextField17);
        jTextField17.setBounds(380, 500, 420, 40);

        jLabel50.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel50.setText("Search By Member");
        jLabel50.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatementPanal1.add(jLabel50);
        jLabel50.setBounds(240, 500, 140, 40);

        jButton230.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton230.setText("Print");
        jButton230.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton230.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton230ActionPerformed(evt);
            }
        });
        accountStatementPanal1.add(jButton230);
        jButton230.setBounds(0, 560, 110, 40);

        jButton231.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton231.setText("Export Excel");
        jButton231.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton231.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton231ActionPerformed(evt);
            }
        });
        accountStatementPanal1.add(jButton231);
        jButton231.setBounds(110, 560, 110, 40);

        jButton232.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton232.setText("Print");
        jButton232.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton232.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton232ActionPerformed(evt);
            }
        });
        accountStatementPanal1.add(jButton232);
        jButton232.setBounds(60, 500, 90, 40);

        jButton233.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton233.setText("Export Excel");
        jButton233.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton233.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton233ActionPerformed(evt);
            }
        });
        accountStatementPanal1.add(jButton233);
        jButton233.setBounds(150, 500, 90, 40);

        FinancialReportsPanel.add(accountStatementPanal1);
        accountStatementPanal1.setBounds(30, 30, 850, 560);
        accountStatementPanal1.setVisible(false);

        accountStatementPanal.setBackground(java.awt.SystemColor.activeCaption);
        accountStatementPanal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatementPanal.setLayout(null);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        JBTrnSqNo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        JBTrnSqNo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTrnSqNo4ActionPerformed(evt);
            }
        });
        jPanel1.add(JBTrnSqNo4);
        JBTrnSqNo4.setBounds(160, 10, 20, 30);
        JBTrnSqNo4.setEnabled(false);

        debitAccountField1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        debitAccountField1.setForeground(new java.awt.Color(0, 51, 255));
        try {
            debitAccountField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-#######-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        debitAccountField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        debitAccountField1.setValue(null);
        debitAccountField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debitAccountField1ActionPerformed(evt);
            }
        });
        debitAccountField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                debitAccountField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                debitAccountField1KeyReleased(evt);
            }
        });
        jPanel1.add(debitAccountField1);
        debitAccountField1.setBounds(180, 10, 120, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("START DATE");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 50, 170, 30);

        valueDate1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        valueDate1.setForeground(new java.awt.Color(0, 204, 102));
        valueDate1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDate1ActionPerformed(evt);
            }
        });
        jPanel1.add(valueDate1);
        valueDate1.setBounds(180, 50, 100, 30);
        valueDate1.setText("");

        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton29);
        jButton29.setBounds(280, 50, 20, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("END DATE");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 90, 170, 30);

        valueDate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        valueDate.setForeground(new java.awt.Color(0, 204, 102));
        valueDate.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 51, 255), null));
        valueDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueDateActionPerformed(evt);
            }
        });
        jPanel1.add(valueDate);
        valueDate.setBounds(180, 90, 100, 30);

        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton28);
        jButton28.setBounds(280, 90, 20, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("AMOUNT");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel19);
        jLabel19.setBounds(10, 130, 170, 30);

        amountFieldDebit2.setForeground(new java.awt.Color(0, 102, 51));
        amountFieldDebit2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###,###,###,###"))));
        amountFieldDebit2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        amountFieldDebit2.setValue(null);
        amountFieldDebit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldDebit2ActionPerformed(evt);
            }
        });
        jPanel1.add(amountFieldDebit2);
        amountFieldDebit2.setBounds(180, 130, 120, 30);

        jLabel44.setBackground(new java.awt.Color(255, 255, 255));
        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Ledger Id");
        jLabel44.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel44);
        jLabel44.setBounds(10, 10, 150, 30);

        accountStatementPanal.add(jPanel1);
        jPanel1.setBounds(260, 100, 370, 170);

        jPanel2.setBackground(java.awt.SystemColor.activeCaption);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jButton4.setBackground(new java.awt.Color(153, 204, 255));
        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("Submmit");
        jButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);
        jButton4.setBounds(120, 0, 140, 30);

        jButton5.setBackground(new java.awt.Color(153, 204, 255));
        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Export Excel");
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);
        jButton5.setBounds(190, 30, 130, 30);

        jButton75.setBackground(new java.awt.Color(153, 204, 255));
        jButton75.setText("Email Report");
        jButton75.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton75);
        jButton75.setBounds(130, 60, 130, 30);
        jButton75.setVisible(false);

        jButton114.setBackground(new java.awt.Color(153, 204, 255));
        jButton114.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton114.setText("Export PDF");
        jButton114.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton114ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton114);
        jButton114.setBounds(60, 30, 130, 30);

        accountStatementPanal.add(jPanel2);
        jPanel2.setBounds(260, 270, 370, 90);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Ledger  Category");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatementPanal.add(jLabel9);
        jLabel9.setBounds(260, 60, 140, 30);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Member/Customer Ledgers", "Asset Ledgers", "Fixed Assets Ledgers", "Current Assets Ledgers", "Receivable Ledgers", "Bank Ledgers", "Cash Ledgers", "Loan Ledgers", "Liabilities Ledgers", "Expense Ledgers", "Equity Ledgers", "Revenue Ledgers" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        accountStatementPanal.add(jComboBox1);
        jComboBox1.setBounds(400, 60, 230, 30);

        FinancialReportsPanel.add(accountStatementPanal);
        accountStatementPanal.setBounds(30, 30, 850, 560);
        accountStatementPanal.setVisible(false);

        budgetVarianceAnalysis.setBackground(java.awt.SystemColor.activeCaption);
        budgetVarianceAnalysis.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        budgetVarianceAnalysis.setLayout(null);

        jLabel53.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel53.setText("END DATE");
        jLabel53.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        budgetVarianceAnalysis.add(jLabel53);
        jLabel53.setBounds(260, 250, 120, 30);

        jLabel54.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel54.setText("START DATE");
        jLabel54.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        budgetVarianceAnalysis.add(jLabel54);
        jLabel54.setBounds(260, 190, 120, 30);

        jButton117.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton117.setText("SUBMIT");
        jButton117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton117ActionPerformed(evt);
            }
        });
        budgetVarianceAnalysis.add(jButton117);
        jButton117.setBounds(410, 300, 120, 30);

        jTextField18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        budgetVarianceAnalysis.add(jTextField18);
        jTextField18.setBounds(380, 250, 200, 30);

        jTextField23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        budgetVarianceAnalysis.add(jTextField23);
        jTextField23.setBounds(380, 190, 200, 30);

        jButton118.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton118ActionPerformed(evt);
            }
        });
        budgetVarianceAnalysis.add(jButton118);
        jButton118.setBounds(580, 190, 20, 30);

        jButton119.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton119ActionPerformed(evt);
            }
        });
        budgetVarianceAnalysis.add(jButton119);
        jButton119.setBounds(580, 250, 20, 30);

        FinancialReportsPanel.add(budgetVarianceAnalysis);
        budgetVarianceAnalysis.setBounds(30, 30, 850, 560);
        budgetVarianceAnalysis.setVisible(false);

        AssetRegister.setBackground(java.awt.SystemColor.activeCaption);
        AssetRegister.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AssetRegister.setLayout(null);

        jButton40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton40.setText("CREATE ASSET REGISTER");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        AssetRegister.add(jButton40);
        jButton40.setBounds(280, 250, 260, 50);

        FinancialReportsPanel.add(AssetRegister);
        AssetRegister.setBounds(30, 40, 850, 550);
        AssetRegister.setVisible(false);

        jPanel27.add(FinancialReportsPanel);
        FinancialReportsPanel.setBounds(270, 20, 930, 600);

        jPanel25.add(jPanel27);
        jPanel27.setBounds(0, 0, 1290, 660);

        jTabbedPane1.addTab("FINANCIAL REPORTS", jPanel25);

        jPanel28.setLayout(null);

        jPanel31.setBackground(java.awt.SystemColor.activeCaption);
        jPanel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel31.setLayout(null);

        loanReportsSubPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanReportsSubPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanReportsSubPanel.setForeground(new java.awt.Color(255, 255, 255));
        loanReportsSubPanel.setLayout(null);

        jButton18.setForeground(new java.awt.Color(0, 153, 153));
        jButton18.setText("LOANS DUE");
        jButton18.setAutoscrolls(true);
        jButton18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton18);
        jButton18.setBounds(10, 170, 240, 30);

        jButton21.setForeground(new java.awt.Color(0, 153, 153));
        jButton21.setText("LOANS WRITTEN OFF");
        jButton21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton21);
        jButton21.setBounds(10, 580, 240, 30);
        jButton21.setVisible(false);

        jButton22.setForeground(new java.awt.Color(0, 153, 153));
        jButton22.setText("LOANS COMPLETED");
        jButton22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton22);
        jButton22.setBounds(10, 230, 240, 30);

        jButton8.setForeground(new java.awt.Color(0, 153, 153));
        jButton8.setText("LOAN REGISTER");
        jButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton8);
        jButton8.setBounds(10, 470, 240, 30);

        assetsButto2.setForeground(new java.awt.Color(0, 153, 102));
        assetsButto2.setText("MONEY LENDERS INTEREST");
        assetsButto2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        assetsButto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsButto2ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(assetsButto2);
        assetsButto2.setBounds(10, 350, 240, 30);

        cashFlowButton2.setForeground(new java.awt.Color(0, 153, 153));
        cashFlowButton2.setText("LOANS DISBURSED");
        cashFlowButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton2ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(cashFlowButton2);
        cashFlowButton2.setBounds(10, 260, 240, 30);

        loanApprovedButton.setForeground(new java.awt.Color(0, 204, 153));
        loanApprovedButton.setText("LOAN AGING ANALYSIS");
        loanApprovedButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanApprovedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanApprovedButtonActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(loanApprovedButton);
        loanApprovedButton.setBounds(10, 380, 240, 30);
        //loanApprovedButton.setVisible(false);

        loanAppliedButton.setForeground(new java.awt.Color(0, 153, 102));
        loanAppliedButton.setText("LOAN STATUS REPORT");
        loanAppliedButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanAppliedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanAppliedButtonActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(loanAppliedButton);
        loanAppliedButton.setBounds(10, 200, 240, 30);

        loanPortFolioButton.setForeground(new java.awt.Color(0, 153, 102));
        loanPortFolioButton.setText("GROSS LOAN PORTFOLIO");
        loanPortFolioButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPortFolioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanPortFolioButtonActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(loanPortFolioButton);
        loanPortFolioButton.setBounds(10, 320, 240, 30);

        loanStatement.setForeground(new java.awt.Color(0, 153, 102));
        loanStatement.setText("LOAN PAYMENT SCHEDULE");
        loanStatement.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanStatement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanStatementActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(loanStatement);
        loanStatement.setBounds(10, 50, 240, 30);

        jButton51.setForeground(new java.awt.Color(0, 153, 153));
        jButton51.setText("LOANS DUE");
        jButton51.setAutoscrolls(true);
        jButton51.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton51);
        jButton51.setBounds(10, 580, 240, 30);
        jButton51.setVisible(false);

        jButton52.setForeground(new java.awt.Color(0, 153, 153));
        jButton52.setText("LOAN INST OTHER THAN SCHDULED");
        jButton52.setAutoscrolls(true);
        jButton52.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanReportsSubPanel.add(jButton52);
        jButton52.setBounds(10, 580, 240, 30);
        jButton52 .setVisible(false);

        jButton53.setForeground(new java.awt.Color(0, 153, 153));
        jButton53.setText("LOAN INST OTHER THAN SCHDULED");
        jButton53.setAutoscrolls(true);
        jButton53.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton53);
        jButton53.setBounds(10, 580, 240, 30);
        jButton53.setVisible(false);

        jButton54.setForeground(new java.awt.Color(0, 153, 153));
        jButton54.setText("LOAN INST OTHER THAN SCHDULED");
        jButton54.setAutoscrolls(true);
        jButton54.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanReportsSubPanel.add(jButton54);
        jButton54.setBounds(10, 580, 240, 30);
        jButton54.setVisible(false);

        jButton19.setForeground(new java.awt.Color(0, 153, 153));
        jButton19.setText("LOANS TO STAFF MEMBERS");
        jButton19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton19);
        jButton19.setBounds(10, 290, 240, 30);

        jButton73.setForeground(new java.awt.Color(0, 153, 153));
        jButton73.setText("LOAN ARREARS");
        jButton73.setAutoscrolls(true);
        jButton73.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton73);
        jButton73.setBounds(10, 580, 240, 30);
        jButton73.setVisible(false);

        bogaStatementButton.setForeground(new java.awt.Color(0, 153, 153));
        bogaStatementButton.setText("PORTFOLIO SUMMURY BY OFFICERS");
        bogaStatementButton.setAutoscrolls(true);
        bogaStatementButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bogaStatementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bogaStatementButtonActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(bogaStatementButton);
        bogaStatementButton.setBounds(10, 440, 240, 30);
        //bogaStatementButton.setEnabled(false);
        //bogaStatementButton.setVisible(false);

        jButton82.setForeground(new java.awt.Color(0, 153, 153));
        jButton82.setText("PORTFOLIO AT RISK");
        jButton82.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton82ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton82);
        jButton82.setBounds(10, 580, 240, 30);
        jButton82.setVisible(false);

        jButton83.setForeground(new java.awt.Color(0, 153, 153));
        jButton83.setText("PERFORMING PORTFOLIO");
        jButton83.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton83ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton83);
        jButton83.setBounds(10, 610, 240, 30);
        jButton83.setVisible(false);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SELECT LOAN REPORT");
        loanReportsSubPanel.add(jLabel5);
        jLabel5.setBounds(30, 0, 210, 40);

        loansInArrearsButton.setForeground(new java.awt.Color(0, 153, 153));
        loansInArrearsButton.setText("LOANS IN ARREARS");
        loansInArrearsButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansInArrearsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loansInArrearsButtonActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(loansInArrearsButton);
        loansInArrearsButton.setBounds(10, 580, 240, 30);
        loansInArrearsButton.setVisible(false);

        jButton41.setForeground(new java.awt.Color(0, 153, 153));
        jButton41.setText("LOAN DISBURSEMENT SCHEDULE");
        jButton41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton41);
        jButton41.setBounds(10, 50, 240, 30);
        jButton41.setVisible(false);

        jButton238.setForeground(new java.awt.Color(0, 153, 153));
        jButton238.setText("LOAN PAYMENT STATEMENT");
        jButton238.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton238.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton238ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton238);
        jButton238.setBounds(10, 80, 240, 30);

        bogaStatementButton1.setForeground(new java.awt.Color(0, 153, 153));
        bogaStatementButton1.setText("PORTFOLIO LISTS  BY OFFICERS");
        bogaStatementButton1.setAutoscrolls(true);
        bogaStatementButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bogaStatementButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bogaStatementButton1ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(bogaStatementButton1);
        bogaStatementButton1.setBounds(10, 410, 240, 30);
        //bogaStatementButton.setEnabled(false);
        //bogaStatementButton.setVisible(false);

        jButton326.setForeground(new java.awt.Color(0, 153, 153));
        jButton326.setText("HISTORICAL LOAN PYT STATEMENT");
        jButton326.setAutoscrolls(true);
        jButton326.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton326.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton326ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton326);
        jButton326.setBounds(10, 110, 240, 30);

        jButton327.setForeground(new java.awt.Color(0, 153, 153));
        jButton327.setText("STATEMENT OF LOAN STATUS");
        jButton327.setAutoscrolls(true);
        jButton327.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton327.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton327ActionPerformed(evt);
            }
        });
        loanReportsSubPanel.add(jButton327);
        jButton327.setBounds(10, 140, 240, 30);

        jPanel31.add(loanReportsSubPanel);
        loanReportsSubPanel.setBounds(0, 0, 270, 650);

        loanReportsPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanReportsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanReportsPanel.setLayout(null);

        BOGAPanel.setBackground(java.awt.SystemColor.activeCaption);
        BOGAPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BOGAPanel.setLayout(null);

        jButton50.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton50.setText("LOAN PORTFOLIO SUMMURY BY OFFICERS");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });
        BOGAPanel.add(jButton50);
        jButton50.setBounds(230, 230, 370, 40);

        loanReportsPanel.add(BOGAPanel);
        BOGAPanel.setBounds(30, 40, 850, 560);
        BOGAPanel.setVisible(false);

        loansDisbursedPanel.setBackground(java.awt.SystemColor.activeCaption);
        loansDisbursedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDisbursedPanel.setLayout(null);

        jPanel15.setBackground(java.awt.SystemColor.activeCaption);
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setLayout(null);
        jPanel15.add(jTextField19);
        jTextField19.setBounds(160, 70, 100, 30);
        jPanel15.add(jTextField20);
        jTextField20.setBounds(160, 30, 100, 30);

        jLabel45.setText("START DATE");
        jLabel45.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.add(jLabel45);
        jLabel45.setBounds(30, 70, 120, 30);

        jLabel46.setText("START DATE");
        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.add(jLabel46);
        jLabel46.setBounds(30, 30, 120, 30);

        jButton99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton99ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton99);
        jButton99.setBounds(260, 70, 20, 30);

        jButton100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton100ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton100);
        jButton100.setBounds(260, 30, 20, 30);

        jButton60.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton60.setText("CREATE LOANS DISBURSED REPORT");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton60);
        jButton60.setBounds(20, 110, 300, 40);

        loansDisbursedPanel.add(jPanel15);
        jPanel15.setBounds(260, 180, 340, 170);

        loanReportsPanel.add(loansDisbursedPanel);
        loansDisbursedPanel.setBounds(30, 40, 850, 560);
        loansDisbursedPanel.setVisible(false);

        loansDue1.setBackground(java.awt.SystemColor.activeCaption);
        loansDue1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDue1.setLayout(null);

        jButton72.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton72.setText("CREATE LOANS DUE REPORT");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });
        loansDue1.add(jButton72);
        jButton72.setBounds(290, 200, 270, 40);

        loanReportsPanel.add(loansDue1);
        loansDue1.setBounds(30, 40, 850, 560);
        loansDue1.setVisible(false);

        portfolioAtRisk.setBackground(java.awt.SystemColor.activeCaption);
        portfolioAtRisk.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        portfolioAtRisk.setLayout(null);

        jPanel6.setBackground(java.awt.SystemColor.activeCaption);
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(null);

        jLabel37.setText("START DATE");
        jLabel37.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jLabel37);
        jLabel37.setBounds(10, 10, 120, 30);

        jLabel38.setText("END  DATE");
        jLabel38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jLabel38);
        jLabel38.setBounds(10, 50, 120, 30);

        jTextField11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jTextField11);
        jTextField11.setBounds(140, 10, 120, 30);

        jTextField6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jTextField6);
        jTextField6.setBounds(140, 50, 120, 30);

        jButton90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton90ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton90);
        jButton90.setBounds(260, 10, 20, 30);

        jButton85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton85ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton85);
        jButton85.setBounds(260, 50, 20, 30);

        jButton64.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton64.setText("CREATE PORTFOLIO AT RISK");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton64);
        jButton64.setBounds(10, 90, 270, 40);

        portfolioAtRisk.add(jPanel6);
        jPanel6.setBounds(260, 150, 290, 140);

        loanReportsPanel.add(portfolioAtRisk);
        portfolioAtRisk.setBounds(30, 40, 850, 560);
        portfolioAtRisk.setVisible(false);

        loanPortFolioPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanPortFolioPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPortFolioPanel.setLayout(null);

        jPanel9.setBackground(java.awt.SystemColor.activeCaption);
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(null);

        jLabel39.setText("START DATE");
        jLabel39.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel39);
        jLabel39.setBounds(10, 10, 110, 30);

        jLabel40.setText("END  DATE");
        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jLabel40);
        jLabel40.setBounds(10, 50, 110, 30);

        jTextField13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jTextField13);
        jTextField13.setBounds(130, 10, 110, 30);

        jTextField9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.add(jTextField9);
        jTextField9.setBounds(130, 50, 110, 30);

        jButton92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton92ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton92);
        jButton92.setBounds(240, 10, 20, 30);

        jButton88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton88ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton88);
        jButton88.setBounds(240, 50, 20, 30);

        jButton105.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton105.setText("CREATE GROSS LOAN PORTFOLIO");
        jButton105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton105ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton105);
        jButton105.setBounds(10, 90, 269, 30);

        loanPortFolioPanel.add(jPanel9);
        jPanel9.setBounds(420, 40, 290, 150);
        jPanel9.setVisible(false);

        jPanel17.setBackground(java.awt.SystemColor.activeCaption);
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.setLayout(null);

        jButton55.setBackground(new java.awt.Color(0, 0, 51));
        jButton55.setForeground(java.awt.SystemColor.control);
        jButton55.setText(" LOAN DETAILS REPORT");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton55);
        jButton55.setBounds(10, 10, 250, 30);

        loanPortFolioPanel.add(jPanel17);
        jPanel17.setBounds(130, 70, 270, 50);
        jPanel17.setVisible(false);

        jButton308.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton308.setText("CREATE GROSS LOAN PORTFOLIO");
        jButton308.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton308ActionPerformed(evt);
            }
        });
        loanPortFolioPanel.add(jButton308);
        jButton308.setBounds(320, 240, 280, 30);

        jButton323.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton323.setText("EXPORT PDF");
        jButton323.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton323ActionPerformed(evt);
            }
        });
        loanPortFolioPanel.add(jButton323);
        jButton323.setBounds(390, 280, 150, 30);

        loanReportsPanel.add(loanPortFolioPanel);
        loanPortFolioPanel.setBounds(30, 40, 850, 560);
        loanPortFolioPanel.setVisible(false);

        loansDueForWriteOff.setBackground(java.awt.SystemColor.activeCaption);
        loansDueForWriteOff.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDueForWriteOff.setLayout(null);

        jPanel12.setBackground(java.awt.SystemColor.activeCaption);
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setLayout(null);

        jButton63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/APPROVE.jpg"))); // NOI18N
        jButton63.setText("EXPORT EXCEL");
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton63);
        jButton63.setBounds(20, 140, 160, 40);
        jButton63.setVisible(false);

        jLabel33.setText("START DATE");
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.add(jLabel33);
        jLabel33.setBounds(10, 10, 90, 30);
        jLabel33.setVisible(false);

        jLabel34.setText("END  DATE");
        jLabel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.add(jLabel34);
        jLabel34.setBounds(10, 50, 90, 30);
        jLabel34.setVisible(false);
        jPanel12.add(jTextField7);
        jTextField7.setBounds(110, 10, 110, 30);
        jTextField7.setVisible(false);
        jPanel12.add(jTextField4);
        jTextField4.setBounds(110, 50, 110, 30);
        jTextField4.setVisible(false);

        jButton81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton81ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton81);
        jButton81.setBounds(220, 50, 20, 30);
        jButton81.setVisible(false);

        jButton94.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton94ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton94);
        jButton94.setBounds(220, 10, 20, 30);
        jButton94.setVisible(false);

        jButton324.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton324.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton324.setText("EXPORT PDF");
        jButton324.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton324ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton324);
        jButton324.setBounds(230, 140, 170, 40);
        jButton324.setVisible(false);

        jButton325.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton325.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/submit2.png"))); // NOI18N
        jButton325.setText("CREATE THE LOAN REGISTER(EXCEL)");
        jButton325.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton325ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton325);
        jButton325.setBounds(20, 80, 340, 40);

        loansDueForWriteOff.add(jPanel12);
        jPanel12.setBounds(230, 150, 420, 200);

        loanReportsPanel.add(loansDueForWriteOff);
        loansDueForWriteOff.setBounds(30, 40, 850, 560);
        loansDueForWriteOff.setVisible(false);

        loansCompletedPanel.setBackground(java.awt.SystemColor.activeCaption);
        loansCompletedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansCompletedPanel.setLayout(null);

        jPanel18.setBackground(java.awt.SystemColor.activeCaption);
        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel18.setLayout(null);

        jButton93.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton93.setText("CREATE LOANS COMPLETED");
        jButton93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton93ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton93);
        jButton93.setBounds(10, 90, 260, 40);

        jLabel36.setText("START DATE");
        jPanel18.add(jLabel36);
        jLabel36.setBounds(10, 10, 120, 30);

        jLabel49.setText("END  DATE");
        jPanel18.add(jLabel49);
        jLabel49.setBounds(10, 50, 110, 30);
        jPanel18.add(jTextField12);
        jTextField12.setBounds(120, 10, 130, 30);
        jPanel18.add(jTextField14);
        jTextField14.setBounds(120, 50, 130, 30);

        jButton101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton101ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton101);
        jButton101.setBounds(250, 50, 20, 30);

        jButton104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton104ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton104);
        jButton104.setBounds(250, 10, 20, 30);

        loansCompletedPanel.add(jPanel18);
        jPanel18.setBounds(280, 150, 280, 140);

        loanReportsPanel.add(loansCompletedPanel);
        loansCompletedPanel.setBounds(30, 40, 850, 560);
        loansCompletedPanel.setVisible(false);

        loanStatementPanel.setBackground(java.awt.SystemColor.activeCaption);
        loanStatementPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanStatementPanel.setLayout(null);

        jPanel8.setBackground(java.awt.SystemColor.activeCaption);
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(null);

        jCheckBox2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox2.setText("Select Running Loan");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel8.add(jCheckBox2);
        jCheckBox2.setBounds(10, 10, 170, 25);

        jCheckBox3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox3.setText("Select Closed Loan");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel8.add(jCheckBox3);
        jCheckBox3.setBounds(190, 10, 160, 25);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("A/C  NUMBER");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jLabel13);
        jLabel13.setBounds(10, 50, 150, 30);

        JBTrnSqNo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        JBTrnSqNo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTrnSqNo3ActionPerformed(evt);
            }
        });
        jPanel8.add(JBTrnSqNo3);
        JBTrnSqNo3.setBounds(160, 50, 20, 30);

        debitAccountField2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        debitAccountField2.setForeground(new java.awt.Color(0, 51, 255));
        try {
            debitAccountField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-#######-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        debitAccountField2.setValue(null);
        debitAccountField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debitAccountField2ActionPerformed(evt);
            }
        });
        debitAccountField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                debitAccountField2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                debitAccountField2KeyReleased(evt);
            }
        });
        jPanel8.add(debitAccountField2);
        debitAccountField2.setBounds(180, 50, 110, 30);

        jPanel5.setBackground(java.awt.SystemColor.activeCaption);
        jPanel5.setLayout(null);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable2);

        jPanel5.add(jScrollPane6);
        jScrollPane6.setBounds(0, 0, 340, 100);

        jPanel8.add(jPanel5);
        jPanel5.setBounds(10, 80, 340, 100);
        jPanel5.setVisible(false);

        jButton48.setForeground(new java.awt.Color(0, 153, 153));
        jButton48.setText("SUBMIT");
        jButton48.setAutoscrolls(true);
        jButton48.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton48);
        jButton48.setBounds(20, 190, 130, 30);

        jButton47.setForeground(new java.awt.Color(0, 153, 153));
        jButton47.setText("SEND EMAIL");
        jButton47.setAutoscrolls(true);
        jButton47.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton47);
        jButton47.setBounds(170, 190, 120, 30);

        loanStatementPanel.add(jPanel8);
        jPanel8.setBounds(220, 120, 360, 240);

        loanReportsPanel.add(loanStatementPanel);
        loanStatementPanel.setBounds(30, 40, 850, 560);
        loanStatementPanel.setVisible(false);

        loansAppliedPanel.setBackground(java.awt.SystemColor.activeCaption);
        loansAppliedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansAppliedPanel.setLayout(null);

        jPanel13.setBackground(java.awt.SystemColor.activeCaption);
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setLayout(null);
        jPanel13.add(jTextField15);
        jTextField15.setBounds(120, 70, 90, 30);
        jPanel13.add(jTextField16);
        jTextField16.setBounds(120, 30, 90, 30);

        jLabel41.setText("START DATE");
        jLabel41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.add(jLabel41);
        jLabel41.setBounds(10, 70, 100, 30);

        jLabel42.setText("START DATE");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.add(jLabel42);
        jLabel42.setBounds(10, 30, 100, 30);

        jButton86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton86ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton86);
        jButton86.setBounds(210, 70, 20, 30);

        jButton95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton95ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton95);
        jButton95.setBounds(210, 30, 20, 30);

        jButton56.setText("CREATE LOANS APPLIED ");
        jButton56.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton56);
        jButton56.setBounds(10, 110, 220, 40);

        loansAppliedPanel.add(jPanel13);
        jPanel13.setBounds(20, 460, 130, 70);
        jPanel13.setVisible(false);

        jButton227.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton227.setText("LOAN STATUS REPORT");
        jButton227.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton227ActionPerformed(evt);
            }
        });
        loansAppliedPanel.add(jButton227);
        jButton227.setBounds(230, 70, 350, 50);

        jButton234.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton234.setText("PDF");
        jButton234.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton234ActionPerformed(evt);
            }
        });
        loansAppliedPanel.add(jButton234);
        jButton234.setBounds(230, 130, 180, 50);
        jButton234.setVisible(false);

        jButton235.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton235.setText("EXCEL");
        jButton235.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton235ActionPerformed(evt);
            }
        });
        loansAppliedPanel.add(jButton235);
        jButton235.setBounds(420, 130, 160, 50);
        jButton235.setVisible(false);

        loanReportsPanel.add(loansAppliedPanel);
        loansAppliedPanel.setBounds(30, 40, 850, 560);
        loansAppliedPanel.setVisible(false);

        tablePanel.setBackground(java.awt.SystemColor.activeCaption);
        tablePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablePanel.setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        tablePanel.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 780, 480);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("SEARCH HERE");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablePanel.add(jLabel24);
        jLabel24.setBounds(370, 500, 140, 30);

        jTextField21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tablePanel.add(jTextField21);
        jTextField21.setBounds(510, 500, 280, 30);

        loanReportsPanel.add(tablePanel);
        tablePanel.setBounds(30, 40, 850, 560);
        tablePanel.setVisible(false);

        loanDisbursementSchedule.setBackground(java.awt.SystemColor.activeCaption);
        loanDisbursementSchedule.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanDisbursementSchedule.setLayout(null);

        jPanel24.setBackground(java.awt.SystemColor.activeCaption);
        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel24.setLayout(null);
        jPanel24.add(jTextField61);
        jTextField61.setBounds(130, 70, 100, 30);
        jPanel24.add(jTextField62);
        jTextField62.setBounds(130, 30, 100, 30);

        jLabel103.setText("START DATE");
        jLabel103.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel24.add(jLabel103);
        jLabel103.setBounds(10, 70, 110, 30);

        jLabel104.setText("START DATE");
        jLabel104.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel24.add(jLabel104);
        jLabel104.setBounds(10, 30, 110, 30);

        jButton239.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton239.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton239ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton239);
        jButton239.setBounds(230, 70, 20, 30);

        jButton240.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton240.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton240ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton240);
        jButton240.setBounds(230, 30, 20, 30);

        jButton241.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton241.setText("CREATE LOANS APPROVED REPORT");
        jButton241.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton241ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton241);
        jButton241.setBounds(10, 110, 310, 40);

        loanDisbursementSchedule.add(jPanel24);
        jPanel24.setBounds(270, 170, 330, 180);

        loanReportsPanel.add(loanDisbursementSchedule);
        loanDisbursementSchedule.setBounds(30, 40, 850, 560);
        loanDisbursementSchedule.setVisible(false);

        BOGAPanel1.setBackground(java.awt.SystemColor.activeCaption);
        BOGAPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BOGAPanel1.setLayout(null);

        jButton319.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton319.setText("LOAN AGING ANALYSIS ");
        jButton319.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton319ActionPerformed(evt);
            }
        });
        BOGAPanel1.add(jButton319);
        jButton319.setBounds(230, 230, 440, 40);

        jCheckBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox1.setText("INCLUDE GUARANTORS");
        BOGAPanel1.add(jCheckBox1);
        jCheckBox1.setBounds(310, 280, 220, 30);

        loanReportsPanel.add(BOGAPanel1);
        BOGAPanel1.setBounds(30, 40, 850, 560);
        BOGAPanel1.setVisible(false);

        loansApprovedPanel.setBackground(java.awt.SystemColor.activeCaption);
        loansApprovedPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansApprovedPanel.setLayout(null);

        jPanel16.setBackground(java.awt.SystemColor.activeCaption);
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.setLayout(null);

        jButton57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton57.setText("CREATE LOAN PAYMENT STATMENT");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton57);
        jButton57.setBounds(10, 70, 310, 40);

        loansApprovedPanel.add(jPanel16);
        jPanel16.setBounds(230, 190, 330, 180);

        loanReportsPanel.add(loansApprovedPanel);
        loansApprovedPanel.setBounds(30, 40, 850, 560);
        loansApprovedPanel.setVisible(false);

        loansDue.setBackground(java.awt.SystemColor.activeCaption);
        loansDue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDue.setLayout(null);

        jButton59.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton59.setText("CREATE MONEY LENDER'S INTEREST");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });
        loansDue.add(jButton59);
        jButton59.setBounds(300, 380, 340, 40);

        jPanel51.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel51.setLayout(null);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane12.setViewportView(jTable7);

        jPanel51.add(jScrollPane12);
        jScrollPane12.setBounds(20, 10, 790, 280);

        loansDue.add(jPanel51);
        jPanel51.setBounds(20, 10, 820, 310);
        jPanel51.setVisible(false);

        jLabel48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel48.setText("SELECT BORROWER");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDue.add(jLabel48);
        jLabel48.setBounds(110, 330, 180, 40);

        jTextField22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loansDue.add(jTextField22);
        jTextField22.setBounds(290, 330, 480, 40);

        jButton309.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton309.setText("EXCEL");
        jButton309.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton309ActionPerformed(evt);
            }
        });
        loansDue.add(jButton309);
        jButton309.setBounds(300, 430, 120, 40);

        jButton310.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton310.setText("PDF");
        jButton310.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton310ActionPerformed(evt);
            }
        });
        loansDue.add(jButton310);
        jButton310.setBounds(530, 430, 110, 40);

        loanReportsPanel.add(loansDue);
        loansDue.setBounds(30, 40, 850, 560);
        loansDue.setVisible(false);

        loansInArrears.setBackground(java.awt.SystemColor.activeCaption);
        loansInArrears.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansInArrears.setLayout(null);

        jPanel10.setBackground(java.awt.SystemColor.activeCaption);
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setFocusCycleRoot(true);
        jPanel10.setLayout(null);

        jLabel31.setText("START DATE");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel31);
        jLabel31.setBounds(20, 20, 110, 30);
        jPanel10.add(jTextField5);
        jTextField5.setBounds(130, 20, 100, 30);

        jButton84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton84ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton84);
        jButton84.setBounds(230, 20, 20, 30);

        jLabel32.setText("END  DATE");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel32);
        jLabel32.setBounds(20, 50, 110, 30);
        jPanel10.add(jTextField3);
        jTextField3.setBounds(130, 50, 100, 30);

        jButton80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton80ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton80);
        jButton80.setBounds(230, 50, 20, 30);

        jButton79.setText("CREATE LOANS IN ARREARS");
        jButton79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton79ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton79);
        jButton79.setBounds(10, 80, 240, 40);

        loansInArrears.add(jPanel10);
        jPanel10.setBounds(260, 90, 260, 140);

        jPanel11.setBackground(new java.awt.Color(0, 51, 51));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setLayout(null);

        jButton67.setText("LOAN ARREARS ANALYSIS");
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton67);
        jButton67.setBounds(10, 10, 290, 40);

        jLabel29.setBackground(java.awt.SystemColor.activeCaption);
        jLabel29.setText("DETAILS OF INDIVIDUAL");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.add(jLabel29);
        jLabel29.setBounds(10, 50, 160, 30);

        jButton62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton62);
        jButton62.setBounds(170, 50, 20, 30);

        debitAccountField3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        debitAccountField3.setForeground(new java.awt.Color(0, 51, 255));
        try {
            debitAccountField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-#######-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        debitAccountField3.setValue(null);
        debitAccountField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debitAccountField3ActionPerformed(evt);
            }
        });
        debitAccountField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                debitAccountField3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                debitAccountField3KeyReleased(evt);
            }
        });
        jPanel11.add(debitAccountField3);
        debitAccountField3.setBounds(190, 50, 110, 30);

        jButton68.setText("SUBMIT");
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton68);
        jButton68.setBounds(10, 80, 150, 30);

        jButton69.setText("CANCEL");
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton69);
        jButton69.setBounds(170, 80, 130, 30);

        loansInArrears.add(jPanel11);
        jPanel11.setBounds(130, 350, 310, 120);

        jLabel55.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel55.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel55.setText("Loan Status Report");
        jLabel55.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansInArrears.add(jLabel55);
        jLabel55.setBounds(330, 240, 150, 30);

        jLabel56.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel56.setText("Loan Arrears Report");
        loansInArrears.add(jLabel56);
        jLabel56.setBounds(320, 40, 150, 30);

        jLabel57.setText("START DATE");
        jLabel57.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansInArrears.add(jLabel57);
        jLabel57.setBounds(170, 290, 110, 30);

        jLabel58.setText("END  DATE");
        jLabel58.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansInArrears.add(jLabel58);
        jLabel58.setBounds(170, 320, 110, 30);
        loansInArrears.add(jTextField24);
        jTextField24.setBounds(280, 290, 100, 30);
        loansInArrears.add(jTextField25);
        jTextField25.setBounds(280, 320, 100, 30);

        jButton121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton121ActionPerformed(evt);
            }
        });
        loansInArrears.add(jButton121);
        jButton121.setBounds(380, 290, 20, 30);

        jButton122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton122.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton122ActionPerformed(evt);
            }
        });
        loansInArrears.add(jButton122);
        jButton122.setBounds(380, 320, 20, 30);

        loanReportsPanel.add(loansInArrears);
        loansInArrears.setBounds(30, 40, 850, 560);
        loansInArrears.setVisible(false);

        loansToStaffMembers.setBackground(java.awt.SystemColor.activeCaption);
        loansToStaffMembers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansToStaffMembers.setLayout(null);

        jButton66.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton66.setText("CREATE LOANS TO STAFF REPORT");
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });
        loansToStaffMembers.add(jButton66);
        jButton66.setBounds(280, 280, 300, 40);

        loanReportsPanel.add(loansToStaffMembers);
        loansToStaffMembers.setBounds(30, 40, 850, 560);
        loansToStaffMembers.setVisible(false);

        summuryOnLoans.setBackground(java.awt.SystemColor.activeCaption);
        summuryOnLoans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        summuryOnLoans.setLayout(null);

        jButton49.setText("CREATE THE SUMMURY np");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton49);
        jButton49.setBounds(250, 40, 230, 40);
        jButton49 .setVisible(false);
        summuryOnLoans.add(jTextField1);
        jTextField1.setBounds(340, 0, 110, 30);
        jTextField1 .setVisible(false);

        jLabel15.setText("END DATE");
        summuryOnLoans.add(jLabel15);
        jLabel15.setBounds(200, 0, 70, 30);
        jLabel15 .setVisible(false);

        jLabel16.setText("NON PERIODIC LOAN SUMMURY");
        jLabel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.SystemColor.activeCaption, null, null));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel16MousePressed(evt);
            }
        });
        summuryOnLoans.add(jLabel16);
        jLabel16.setBounds(10, 0, 190, 30);
        jLabel16.setVisible(false);
        summuryOnLoans.add(jTextField2);
        jTextField2.setBounds(330, 0, 110, 30);
        jTextField2 .setVisible(false);

        jLabel17.setText("START DATE");
        summuryOnLoans.add(jLabel17);
        jLabel17.setBounds(200, 0, 90, 30);
        jLabel17.setVisible(false);

        jLabel18.setText("PERIOD LOAN SUMMURY");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.SystemColor.activeCaption, null, null));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel18MousePressed(evt);
            }
        });
        summuryOnLoans.add(jLabel18);
        jLabel18.setBounds(10, 0, 190, 30);
        jLabel18.setVisible(false);

        jButton61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton61);
        jButton61.setBounds(300, 0, 30, 30);
        jButton61 .setVisible(false);

        jButton70.setText("CREATE THE SUMMURY p");
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton70);
        jButton70.setBounds(250, 30, 230, 40);
        jButton70 .setVisible(false);

        jButton71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton71);
        jButton71.setBounds(290, 0, 30, 30);
        jButton71 .setVisible(false);

        jButton123.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton123.setText("Loans Due");
        jButton123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton123ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton123);
        jButton123.setBounds(180, 210, 170, 40);

        jButton124.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton124.setText("Quick Summury");
        jButton124.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton124ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton124);
        jButton124.setBounds(180, 290, 170, 40);
        jButton124.setVisible(false);

        jButton208.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton208.setText("Loan Register");
        jButton208.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton208ActionPerformed(evt);
            }
        });
        summuryOnLoans.add(jButton208);
        jButton208.setBounds(180, 250, 170, 40);
        jButton208.setVisible(false);

        jPanel21.setBackground(java.awt.SystemColor.activeCaption);
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.setLayout(null);

        jLabel60.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel60.setText("Start Date");
        jLabel60.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.add(jLabel60);
        jLabel60.setBounds(10, 80, 100, 30);

        jLabel91.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel91.setText("Start Date");
        jLabel91.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.add(jLabel91);
        jLabel91.setBounds(10, 30, 100, 30);
        jPanel21.add(jTextField51);
        jTextField51.setBounds(120, 80, 100, 30);
        jPanel21.add(jTextField52);
        jTextField52.setBounds(120, 30, 100, 30);

        jButton209.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton209.setText("Submit");
        jButton209.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton209ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton209);
        jButton209.setBounds(80, 130, 108, 30);

        jButton126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton126.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton126ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton126);
        jButton126.setBounds(220, 80, 30, 30);

        jButton127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton127.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton127ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton127);
        jButton127.setBounds(220, 30, 30, 30);

        summuryOnLoans.add(jPanel21);
        jPanel21.setBounds(350, 210, 270, 170);
        jPanel21.setVisible(false);

        loanReportsPanel.add(summuryOnLoans);
        summuryOnLoans.setBounds(30, 40, 850, 560);
        summuryOnLoans.setVisible(false);

        BOGAPanel2.setBackground(java.awt.SystemColor.activeCaption);
        BOGAPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BOGAPanel2.setLayout(null);

        jButton322.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton322.setText("LOAN PORFOLIO LIST BY OFFICER");
        jButton322.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton322ActionPerformed(evt);
            }
        });
        BOGAPanel2.add(jButton322);
        jButton322.setBounds(210, 390, 350, 40);
        jButton322.setEnabled(false);

        jPanel40.setLayout(null);

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane13.setViewportView(jTable8);

        jPanel40.add(jScrollPane13);
        jScrollPane13.setBounds(12, 20, 590, 250);

        BOGAPanel2.add(jPanel40);
        jPanel40.setBounds(100, 60, 610, 290);

        jCheckBox4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCheckBox4.setText("INCLUDE GAURANTORS");
        BOGAPanel2.add(jCheckBox4);
        jCheckBox4.setBounds(280, 440, 190, 40);

        loanReportsPanel.add(BOGAPanel2);
        BOGAPanel2.setBounds(30, 40, 850, 560);
        BOGAPanel2.setVisible(false);

        loansDisbursedPanel1.setBackground(java.awt.SystemColor.activeCaption);
        loansDisbursedPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDisbursedPanel1.setLayout(null);

        jPanel41.setBackground(java.awt.SystemColor.activeCaption);
        jPanel41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41.setLayout(null);
        jPanel41.add(jTextField89);
        jTextField89.setBounds(160, 70, 100, 30);
        jPanel41.add(jTextField90);
        jTextField90.setBounds(160, 30, 100, 30);

        jLabel144.setText("START DATE");
        jLabel144.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41.add(jLabel144);
        jLabel144.setBounds(30, 70, 120, 30);

        jLabel145.setText("START DATE");
        jLabel145.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41.add(jLabel145);
        jLabel145.setBounds(30, 30, 120, 30);

        jButton328.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton328.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton328ActionPerformed(evt);
            }
        });
        jPanel41.add(jButton328);
        jButton328.setBounds(260, 70, 20, 30);

        jButton329.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton329.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton329ActionPerformed(evt);
            }
        });
        jPanel41.add(jButton329);
        jButton329.setBounds(260, 30, 20, 30);

        jButton330.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton330.setText("CREATE LOANS DISBURSED REPORT");
        jButton330.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton330ActionPerformed(evt);
            }
        });
        jPanel41.add(jButton330);
        jButton330.setBounds(20, 110, 300, 40);

        loansDisbursedPanel1.add(jPanel41);
        jPanel41.setBounds(260, 180, 340, 170);

        loanReportsPanel.add(loansDisbursedPanel1);
        loansDisbursedPanel1.setBounds(30, 40, 850, 560);
        loansDisbursedPanel.setVisible(false);

        loansDisbursedPanel2.setBackground(java.awt.SystemColor.activeCaption);
        loansDisbursedPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loansDisbursedPanel2.setLayout(null);

        jPanel52.setBackground(java.awt.SystemColor.activeCaption);
        jPanel52.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel52.setLayout(null);
        jPanel52.add(jTextField91);
        jTextField91.setBounds(180, 140, 100, 30);
        jPanel52.add(jTextField92);
        jTextField92.setBounds(180, 90, 100, 30);

        jLabel146.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel146.setText("START DATE");
        jLabel146.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel52.add(jLabel146);
        jLabel146.setBounds(50, 140, 120, 30);

        jLabel147.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel147.setText("START DATE");
        jLabel147.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel52.add(jLabel147);
        jLabel147.setBounds(50, 90, 120, 30);

        jButton331.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton331.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton331ActionPerformed(evt);
            }
        });
        jPanel52.add(jButton331);
        jButton331.setBounds(290, 140, 20, 30);

        jButton332.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton332.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton332ActionPerformed(evt);
            }
        });
        jPanel52.add(jButton332);
        jButton332.setBounds(290, 90, 20, 30);

        jButton333.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton333.setText("PDF");
        jButton333.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton333ActionPerformed(evt);
            }
        });
        jPanel52.add(jButton333);
        jButton333.setBounds(60, 220, 150, 40);

        jButton334.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton334.setText("EXCEL");
        jButton334.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton334ActionPerformed(evt);
            }
        });
        jPanel52.add(jButton334);
        jButton334.setBounds(210, 220, 140, 40);

        jButton335.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton335.setText("SUBMIT");
        jButton335.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton335.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton335ActionPerformed(evt);
            }
        });
        jPanel52.add(jButton335);
        jButton335.setBounds(130, 180, 150, 40);

        jLabel148.setBackground(new java.awt.Color(255, 0, 51));
        jLabel148.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(0, 153, 153));
        jLabel148.setText("CREATE STATEMENT OF LOAN STATUS");
        jLabel148.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel52.add(jLabel148);
        jLabel148.setBounds(50, 30, 300, 40);

        loansDisbursedPanel2.add(jPanel52);
        jPanel52.setBounds(220, 60, 400, 290);

        loanReportsPanel.add(loansDisbursedPanel2);
        loansDisbursedPanel2.setBounds(30, 40, 850, 560);
        loansDisbursedPanel2.setVisible(false);

        jPanel31.add(loanReportsPanel);
        loanReportsPanel.setBounds(290, 30, 920, 620);

        jPanel28.add(jPanel31);
        jPanel31.setBounds(0, 0, 1290, 660);

        jTabbedPane1.addTab("LOAN REPORTS", jPanel28);

        jPanel32.setLayout(null);

        jPanel33.setBackground(java.awt.SystemColor.activeCaption);
        jPanel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel33.setLayout(null);

        OtherReportsSubPanel.setBackground(java.awt.SystemColor.activeCaption);
        OtherReportsSubPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        OtherReportsSubPanel.setLayout(null);

        jButton12.setForeground(new java.awt.Color(0, 0, 153));
        jButton12.setText("EXPENSES ACCOUNTS");
        jButton12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(jButton12);
        jButton12.setBounds(10, 340, 240, 30);

        jButton17.setForeground(new java.awt.Color(0, 0, 153));
        jButton17.setText("REVENUE ACCOUNTS");
        jButton17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(jButton17);
        jButton17.setBounds(10, 310, 240, 30);

        jButton9.setForeground(new java.awt.Color(0, 0, 153));
        jButton9.setText("EQUITY ACCOUNTS");
        jButton9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(jButton9);
        jButton9.setBounds(10, 280, 240, 30);

        liabilitiesButton1.setForeground(new java.awt.Color(0, 0, 153));
        liabilitiesButton1.setText("LIABILITIES ACCOUNTS");
        liabilitiesButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        liabilitiesButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesButton1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(liabilitiesButton1);
        liabilitiesButton1.setBounds(10, 250, 240, 30);

        assetsButto1.setForeground(new java.awt.Color(0, 0, 153));
        assetsButto1.setText("ASSETS ACCOUNTS");
        assetsButto1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        assetsButto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsButto1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(assetsButto1);
        assetsButto1.setBounds(10, 220, 240, 30);

        cashFlowButton1.setForeground(new java.awt.Color(0, 0, 153));
        cashFlowButton1.setText("CUSTOMERS BY DEMORGRAPHY");
        cashFlowButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cashFlowButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashFlowButton1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(cashFlowButton1);
        cashFlowButton1.setBounds(10, 40, 240, 30);

        incomeStatementButton1.setForeground(new java.awt.Color(0, 0, 153));
        incomeStatementButton1.setText("CUSTOMERS BY BALANCES");
        incomeStatementButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        incomeStatementButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeStatementButton1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(incomeStatementButton1);
        incomeStatementButton1.setBounds(10, 100, 240, 30);

        trialBalanceButton1.setForeground(new java.awt.Color(0, 0, 153));
        trialBalanceButton1.setText("BUDGET ESTIMATES");
        trialBalanceButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trialBalanceButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trialBalanceButton1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(trialBalanceButton1);
        trialBalanceButton1.setBounds(10, 190, 240, 30);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("SELECT OTHER  REPORTS");
        OtherReportsSubPanel.add(jLabel4);
        jLabel4.setBounds(60, 0, 180, 40);

        accountStatement1.setForeground(new java.awt.Color(0, 0, 153));
        accountStatement1.setText("STAFF MEMBERS");
        accountStatement1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accountStatement1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountStatement1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(accountStatement1);
        accountStatement1.setBounds(10, 130, 240, 30);

        balanceSheetButton1.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton1.setText("CUSTOMERS BY SHARES");
        balanceSheetButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton1ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton1);
        balanceSheetButton1.setBounds(10, 160, 240, 30);

        balanceSheetButton3.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton3.setText("OVERDRAWN ACCOUNTS");
        balanceSheetButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton3ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton3);
        balanceSheetButton3.setBounds(10, 370, 240, 30);

        jButton15.setForeground(new java.awt.Color(0, 0, 153));
        jButton15.setText("CUSTOMER CONTACT DETAILS");
        jButton15.setAutoscrolls(true);
        jButton15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(jButton15);
        jButton15.setBounds(10, 70, 240, 30);

        balanceSheetButton6.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton6.setText("MONTHLY INDIVIDUAL TXN SUMMURY");
        balanceSheetButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton6ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton6);
        balanceSheetButton6.setBounds(10, 550, 240, 30);
        balanceSheetButton6.setVisible(false);

        balanceSheetButton7.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton7.setText("RETURN ON INVESTMENT");
        balanceSheetButton7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton7ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton7);
        balanceSheetButton7.setBounds(10, 490, 240, 30);
        //balanceSheetButton7.setVisible(false);

        balanceSheetButton8.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton8.setText("PROVISIONED ITEMS");
        balanceSheetButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton8ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton8);
        balanceSheetButton8.setBounds(10, 400, 240, 30);

        balanceSheetButton9.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton9.setText("SHARES CONTRIBUTION");
        balanceSheetButton9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton9ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton9);
        balanceSheetButton9.setBounds(10, 430, 240, 30);

        balanceSheetButton10.setForeground(new java.awt.Color(0, 0, 153));
        balanceSheetButton10.setText("SAVINGS CONTRIBUTION");
        balanceSheetButton10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        balanceSheetButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceSheetButton10ActionPerformed(evt);
            }
        });
        OtherReportsSubPanel.add(balanceSheetButton10);
        balanceSheetButton10.setBounds(10, 460, 240, 30);

        jPanel33.add(OtherReportsSubPanel);
        OtherReportsSubPanel.setBounds(0, 0, 260, 640);

        OtherReportsPanel.setBackground(java.awt.SystemColor.activeCaption);
        OtherReportsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        OtherReportsPanel.setLayout(null);

        LoginsPan.setBackground(java.awt.SystemColor.activeCaption);
        LoginsPan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LoginsPan.setEnabled(false);
        LoginsPan.setLayout(null);

        jComboBox11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entire Period", "Date Range" }));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        LoginsPan.add(jComboBox11);
        jComboBox11.setBounds(270, 140, 220, 40);
        jComboBox11.setVisible(false);

        jLabel102.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel102.setText("START DATE");
        jLabel102.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LoginsPan.add(jLabel102);
        jLabel102.setBounds(220, 200, 100, 30);
        jLabel102.setVisible(false);

        jLabel101.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel101.setText("END DATE");
        jLabel101.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LoginsPan.add(jLabel101);
        jLabel101.setBounds(220, 240, 100, 30);
        jLabel101.setVisible(false);

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setText("SUBMIT");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        LoginsPan.add(jButton7);
        jButton7.setBounds(310, 280, 160, 40);

        jTextField59.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        LoginsPan.add(jTextField59);
        jTextField59.setBounds(330, 240, 180, 30);
        jTextField59.setVisible(false);

        jTextField60.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        LoginsPan.add(jTextField60);
        jTextField60.setBounds(330, 200, 180, 30);
        jTextField60.setVisible(false);

        jButton237.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton237.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton237ActionPerformed(evt);
            }
        });
        LoginsPan.add(jButton237);
        jButton237.setBounds(520, 200, 30, 30);
        jButton237.setVisible(false);

        jButton236.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton236.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton236ActionPerformed(evt);
            }
        });
        LoginsPan.add(jButton236);
        jButton236.setBounds(520, 240, 30, 30);
        jButton236.setVisible(false);

        jLabel108.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(0, 0, 153));
        jLabel108.setText("CREATE BUDGET ESTIMATES");
        jLabel108.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LoginsPan.add(jLabel108);
        jLabel108.setBounds(270, 90, 220, 40);

        OtherReportsPanel.add(LoginsPan);
        LoginsPan.setBounds(30, 40, 850, 550);
        LoginsPan  .setVisible(false);

        CustomersDemographyPanel.setBackground(java.awt.SystemColor.activeCaption);
        CustomersDemographyPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CustomersDemographyPanel.setLayout(null);

        jButton2.setText("CREATE DEMOGRAPHICS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        CustomersDemographyPanel.add(jButton2);
        jButton2.setBounds(150, 160, 300, 30);

        jButton6.setText("Send Email");
        CustomersDemographyPanel.add(jButton6);
        jButton6.setBounds(220, 280, 150, 30);

        jButton107.setText("Export PDF");
        jButton107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton107ActionPerformed(evt);
            }
        });
        CustomersDemographyPanel.add(jButton107);
        jButton107.setBounds(150, 230, 150, 30);

        jButton108.setText("Export Excel");
        CustomersDemographyPanel.add(jButton108);
        jButton108.setBounds(300, 230, 150, 30);

        OtherReportsPanel.add(CustomersDemographyPanel);
        CustomersDemographyPanel.setBounds(30, 40, 850, 550);
        CustomersDemographyPanel .setVisible(false);

        customerByBalancePanel.setBackground(java.awt.SystemColor.activeCaption);
        customerByBalancePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerByBalancePanel.setLayout(null);

        jButton11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        customerByBalancePanel.add(jButton11);
        jButton11.setBounds(170, 280, 120, 40);

        jLabel97.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel97.setText("VALUE DATE");
        jLabel97.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerByBalancePanel.add(jLabel97);
        jLabel97.setBounds(120, 110, 100, 30);

        jLabel98.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel98.setText("CREATE CUSTOMER BY LEDGER BALANCES");
        jLabel98.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerByBalancePanel.add(jLabel98);
        jLabel98.setBounds(130, 70, 330, 30);

        jTextField57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        customerByBalancePanel.add(jTextField57);
        jTextField57.setBounds(230, 100, 160, 30);

        jButton215.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton215.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton215ActionPerformed(evt);
            }
        });
        customerByBalancePanel.add(jButton215);
        jButton215.setBounds(390, 100, 30, 30);

        jButton228.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton228.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton228.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton228ActionPerformed(evt);
            }
        });
        customerByBalancePanel.add(jButton228);
        jButton228.setBounds(310, 280, 120, 40);

        jButton229.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton229.setText("SUBMIT");
        jButton229.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton229ActionPerformed(evt);
            }
        });
        customerByBalancePanel.add(jButton229);
        jButton229.setBounds(230, 220, 130, 40);

        OtherReportsPanel.add(customerByBalancePanel);
        customerByBalancePanel.setBounds(30, 40, 850, 550);
        customerByBalancePanel.setVisible(false);

        customerBySharesPanel.setBackground(java.awt.SystemColor.activeCaption);
        customerBySharesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerBySharesPanel.setLayout(null);

        jButton14.setText("CREATE CUSTOMER SHARES");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        customerBySharesPanel.add(jButton14);
        jButton14.setBounds(260, 240, 290, 30);

        OtherReportsPanel.add(customerBySharesPanel);
        customerBySharesPanel.setBounds(30, 40, 850, 550);
        customerBySharesPanel .setVisible(false);

        customerByContactDetailsPanel.setBackground(java.awt.SystemColor.activeCaption);
        customerByContactDetailsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        customerByContactDetailsPanel.setLayout(null);

        jButton16.setText("CREATE STAFF MEMBERS");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        customerByContactDetailsPanel.add(jButton16);
        jButton16.setBounds(160, 240, 260, 30);

        OtherReportsPanel.add(customerByContactDetailsPanel);
        customerByContactDetailsPanel.setBounds(30, 40, 850, 550);
        customerByContactDetailsPanel .setVisible(false);

        OverDrawnPostionAccounts.setBackground(java.awt.SystemColor.activeCaption);
        OverDrawnPostionAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        OverDrawnPostionAccounts.setLayout(null);

        jButton43.setText(" CREATE STAFF MEMBERS");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        OverDrawnPostionAccounts.add(jButton43);
        jButton43.setBounds(150, 220, 260, 30);

        OtherReportsPanel.add(OverDrawnPostionAccounts);
        OverDrawnPostionAccounts.setBounds(30, 40, 850, 550);
        OverDrawnPostionAccounts .setVisible(false);

        ExpensesAccounts.setBackground(java.awt.SystemColor.activeCaption);
        ExpensesAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ExpensesAccounts.setLayout(null);

        jButton42.setText("CREATE EXPENSES ACCOUNTS");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        ExpensesAccounts.add(jButton42);
        jButton42.setBounds(230, 210, 260, 30);

        OtherReportsPanel.add(ExpensesAccounts);
        ExpensesAccounts.setBounds(30, 40, 850, 550);
        ExpensesAccounts.setVisible(false);

        EquityAccounts.setBackground(java.awt.SystemColor.activeCaption);
        EquityAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EquityAccounts.setLayout(null);

        jButton36.setText("CREATE EQUITY ACCOUNTS");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        EquityAccounts.add(jButton36);
        jButton36.setBounds(200, 280, 260, 30);

        OtherReportsPanel.add(EquityAccounts);
        EquityAccounts.setBounds(30, 40, 850, 550);
        EquityAccounts.setVisible(false);

        AssetAccounts.setBackground(java.awt.SystemColor.activeCaption);
        AssetAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AssetAccounts.setLayout(null);

        jButton31.setText("CREATE ASSET ACCOUNTS");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        AssetAccounts.add(jButton31);
        jButton31.setBounds(230, 240, 260, 30);

        OtherReportsPanel.add(AssetAccounts);
        AssetAccounts.setBounds(30, 40, 850, 550);
        AssetAccounts.setVisible(false);

        LiabilitiesAccounts.setBackground(java.awt.SystemColor.activeCaption);
        LiabilitiesAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LiabilitiesAccounts.setLayout(null);

        jButton35.setText("CREATE LIABILITIES ACCOUNTS");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        LiabilitiesAccounts.add(jButton35);
        jButton35.setBounds(230, 270, 260, 30);

        OtherReportsPanel.add(LiabilitiesAccounts);
        LiabilitiesAccounts.setBounds(30, 40, 850, 550);
        LiabilitiesAccounts.setVisible(false);

        IncomeAccounts.setBackground(java.awt.SystemColor.activeCaption);
        IncomeAccounts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        IncomeAccounts.setLayout(null);

        jButton37.setText(" CREATE REVENUE ACCOUNTS");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        IncomeAccounts.add(jButton37);
        jButton37.setBounds(230, 290, 260, 30);

        OtherReportsPanel.add(IncomeAccounts);
        IncomeAccounts.setBounds(30, 40, 850, 550);
        IncomeAccounts.setVisible(false);

        StaffMembersPanel.setBackground(java.awt.SystemColor.activeCaption);
        StaffMembersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        StaffMembersPanel.setLayout(null);

        jButton1.setText("CREATE STAFF MEMBERS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        StaffMembersPanel.add(jButton1);
        jButton1.setBounds(240, 280, 260, 30);

        OtherReportsPanel.add(StaffMembersPanel);
        StaffMembersPanel.setBounds(30, 40, 850, 550);
        StaffMembersPanel .setVisible(false);

        loanSavingsToday.setBackground(java.awt.SystemColor.activeCaption);
        loanSavingsToday.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanSavingsToday.setLayout(null);

        jButton96.setText("CREATE LOAN AND SAVINGS");
        jButton96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton96ActionPerformed(evt);
            }
        });
        loanSavingsToday.add(jButton96);
        jButton96.setBounds(180, 260, 300, 30);

        OtherReportsPanel.add(loanSavingsToday);
        loanSavingsToday.setBounds(30, 40, 850, 550);
        loanSavingsToday  .setVisible(false);

        jPanel19.setBackground(java.awt.SystemColor.activeCaption);
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel19.setForeground(java.awt.SystemColor.controlLtHighlight);
        jPanel19.setLayout(null);

        totalLoanShares.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares.setLayout(null);

        jLabel51.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 153));
        jLabel51.setText("ENDING MONTH");
        jLabel51.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares.add(jLabel51);
        jLabel51.setBounds(10, 70, 160, 40);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Create Total  Loan & Savings");
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        totalLoanShares.add(jButton3);
        jButton3.setBounds(220, 120, 260, 40);

        jButton110.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton110.setText("Export PDF");
        jButton110.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton110ActionPerformed(evt);
            }
        });
        totalLoanShares.add(jButton110);
        jButton110.setBounds(190, 160, 130, 40);

        jButton111.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton111.setText("Export Excel");
        jButton111.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton111ActionPerformed(evt);
            }
        });
        totalLoanShares.add(jButton111);
        jButton111.setBounds(340, 160, 130, 40);

        jLabel52.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 153));
        jLabel52.setText("STARTING MONTH");
        jLabel52.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares.add(jLabel52);
        jLabel52.setBounds(10, 20, 160, 40);

        totalLoanSavingsCombo2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanSavingsCombo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalLoanSavingsCombo2ActionPerformed(evt);
            }
        });
        totalLoanShares.add(totalLoanSavingsCombo2);
        totalLoanSavingsCombo2.setBounds(210, 70, 300, 40);

        totalLoanSavingsCombo1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares.add(totalLoanSavingsCombo1);
        totalLoanSavingsCombo1.setBounds(210, 20, 300, 40);

        jButton112.setText("Send Email");
        jButton112.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton112ActionPerformed(evt);
            }
        });
        totalLoanShares.add(jButton112);
        jButton112.setBounds(270, 220, 120, 40);

        jPanel19.add(totalLoanShares);
        totalLoanShares.setBounds(10, 120, 530, 360);
        totalLoanShares.setVisible(false);

        jComboBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total Savings & Loans" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel19.add(jComboBox5);
        jComboBox5.setBounds(190, 60, 350, 50);
        jComboBox5.setVisible(false);

        jButton109.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton109.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton109.setText("Individual Monthly Summury");
        jButton109.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton109ActionPerformed(evt);
            }
        });
        jPanel19.add(jButton109);
        jButton109.setBounds(10, 60, 180, 50);

        jButton113.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton113.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton113.setText("Total Monthly Summury");
        jButton113.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton113ActionPerformed(evt);
            }
        });
        jPanel19.add(jButton113);
        jButton113.setBounds(10, 10, 180, 50);

        jComboBox6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loan Payments", "Savings", "Total Loan & Savings", "Total Loan & Shares", "Total Loan,Savings & Shares", "Total Princimpal,Interest, Savings ", "Total Princimpal,Interest,Savings &  Shares ", "Total SavingsWithdraw & SharesWithdraw", "Total SavingsWithdraw & Savings" }));
        jComboBox6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jPanel19.add(jComboBox6);
        jComboBox6.setBounds(190, 10, 350, 50);
        jComboBox6.setVisible(false);

        loanPayments.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPayments.setLayout(null);

        jLabel83.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 153));
        jLabel83.setText("STARTING MONTH");
        jLabel83.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPayments.add(jLabel83);
        jLabel83.setBounds(40, 30, 190, 40);

        totalLoanSavingsCombo5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPayments.add(totalLoanSavingsCombo5);
        totalLoanSavingsCombo5.setBounds(250, 30, 220, 40);

        jLabel84.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 153));
        jLabel84.setText("ENDING MONTH");
        jLabel84.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPayments.add(jLabel84);
        jLabel84.setBounds(40, 80, 190, 40);

        totalLoanSavingsCombo6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loanPayments.add(totalLoanSavingsCombo6);
        totalLoanSavingsCombo6.setBounds(250, 80, 220, 40);

        jButton195.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton195.setText("Create Total  Loan Payments");
        jButton195.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton195.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton195ActionPerformed(evt);
            }
        });
        loanPayments.add(jButton195);
        jButton195.setBounds(150, 140, 250, 40);

        jButton196.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton196.setText("Export Excel");
        jButton196.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton196.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton196ActionPerformed(evt);
            }
        });
        loanPayments.add(jButton196);
        jButton196.setBounds(280, 200, 120, 40);

        jButton197.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton197.setText("Export PDF");
        jButton197.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton197.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton197ActionPerformed(evt);
            }
        });
        loanPayments.add(jButton197);
        jButton197.setBounds(150, 200, 130, 40);

        jPanel19.add(loanPayments);
        loanPayments.setBounds(10, 120, 530, 360);
        loanPayments.setVisible(false);

        savingsContributions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsContributions.setLayout(null);

        jLabel81.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 153));
        jLabel81.setText("STARTING MONTH");
        jLabel81.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsContributions.add(jLabel81);
        jLabel81.setBounds(70, 30, 150, 40);

        totalLoanSavingsCombo4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsContributions.add(totalLoanSavingsCombo4);
        totalLoanSavingsCombo4.setBounds(230, 30, 200, 40);

        jLabel82.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 153));
        jLabel82.setText("ENDING MONTH");
        jLabel82.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsContributions.add(jLabel82);
        jLabel82.setBounds(70, 80, 150, 40);

        totalLoanSavingsCombo3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanSavingsCombo3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsContributions.add(totalLoanSavingsCombo3);
        totalLoanSavingsCombo3.setBounds(230, 80, 200, 40);

        jButton193.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton193.setText("Create Total  Savings Contributed");
        jButton193.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton193.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton193ActionPerformed(evt);
            }
        });
        savingsContributions.add(jButton193);
        jButton193.setBounds(140, 140, 270, 40);

        jButton194.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton194.setText("Export Excel");
        jButton194.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton194.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton194ActionPerformed(evt);
            }
        });
        savingsContributions.add(jButton194);
        jButton194.setBounds(280, 190, 130, 40);

        jButton198.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton198.setText("Export PDF");
        jButton198.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton198.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton198ActionPerformed(evt);
            }
        });
        savingsContributions.add(jButton198);
        jButton198.setBounds(140, 190, 130, 40);

        jPanel19.add(savingsContributions);
        savingsContributions.setBounds(10, 120, 530, 360);
        savingsContributions.setVisible(false);

        OtherReportsPanel.add(jPanel19);
        jPanel19.setBounds(30, 40, 850, 550);

        returnOnInvestmentPanel.setBackground(java.awt.SystemColor.activeCaption);
        returnOnInvestmentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        returnOnInvestmentPanel.setLayout(null);

        jButton129.setBackground(java.awt.SystemColor.activeCaption);
        jButton129.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton129.setForeground(java.awt.SystemColor.controlLtHighlight);
        jButton129.setText("SELECT ROI SUMMERY");
        jButton129.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton129.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton129ActionPerformed(evt);
            }
        });
        returnOnInvestmentPanel.add(jButton129);
        jButton129.setBounds(30, 40, 180, 50);

        jComboBox4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily Individual ROI", "Daily Group ROI", "Monthly Individual ROI", "Monthly Group ROI", " " }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        returnOnInvestmentPanel.add(jComboBox4);
        jComboBox4.setBounds(210, 40, 330, 50);

        totalLoanShares2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares2.setLayout(null);

        jLabel63.setForeground(new java.awt.Color(0, 0, 153));
        jLabel63.setText("ENDING DATE");
        jLabel63.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares2.add(jLabel63);
        jLabel63.setBounds(10, 70, 160, 40);

        jButton134.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton134.setText("Submit");
        jButton134.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton134.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton134ActionPerformed(evt);
            }
        });
        totalLoanShares2.add(jButton134);
        jButton134.setBounds(170, 120, 250, 40);

        jButton135.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton135.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton135.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton135ActionPerformed(evt);
            }
        });
        totalLoanShares2.add(jButton135);
        jButton135.setBounds(390, 20, 30, 40);

        jButton136.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton136.setText("Export Excel");
        jButton136.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton136.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton136ActionPerformed(evt);
            }
        });
        totalLoanShares2.add(jButton136);
        jButton136.setBounds(310, 170, 110, 40);

        jLabel64.setForeground(new java.awt.Color(0, 0, 153));
        jLabel64.setText("STARTING DATE");
        jLabel64.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares2.add(jLabel64);
        jLabel64.setBounds(10, 20, 160, 40);

        jTextField30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares2.add(jTextField30);
        jTextField30.setBounds(170, 70, 220, 40);

        jTextField31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares2.add(jTextField31);
        jTextField31.setBounds(170, 20, 220, 40);

        jButton137.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton137.setText("Export PDF");
        jButton137.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton137.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton137ActionPerformed(evt);
            }
        });
        totalLoanShares2.add(jButton137);
        jButton137.setBounds(170, 170, 130, 40);

        jButton147.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton147.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton147.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton147ActionPerformed(evt);
            }
        });
        totalLoanShares2.add(jButton147);
        jButton147.setBounds(390, 70, 30, 40);

        returnOnInvestmentPanel.add(totalLoanShares2);
        totalLoanShares2.setBounds(30, 90, 510, 330);
        totalLoanShares2.setVisible(false);

        totalLoanShares3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares3.setLayout(null);

        jLabel65.setForeground(new java.awt.Color(0, 0, 153));
        jLabel65.setText("ENDING DATE");
        jLabel65.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares3.add(jLabel65);
        jLabel65.setBounds(10, 110, 160, 40);

        jButton138.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton138.setText("Submit");
        jButton138.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton138ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton138);
        jButton138.setBounds(170, 150, 250, 40);

        jButton139.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton139.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton139.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton139ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton139);
        jButton139.setBounds(390, 60, 30, 40);

        jButton140.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton140.setText("Export Excel");
        jButton140.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton140.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton140ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton140);
        jButton140.setBounds(310, 200, 110, 40);

        jLabel66.setForeground(new java.awt.Color(0, 0, 153));
        jLabel66.setText("STARTING DATE");
        jLabel66.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares3.add(jLabel66);
        jLabel66.setBounds(10, 60, 160, 40);

        jTextField32.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totalLoanShares3.add(jTextField32);
        jTextField32.setBounds(170, 110, 220, 40);

        jTextField33.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totalLoanShares3.add(jTextField33);
        jTextField33.setBounds(170, 60, 220, 40);

        jButton141.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton141.setText("Export PDF");
        jButton141.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton141.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton141ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton141);
        jButton141.setBounds(170, 200, 130, 40);

        jButton148.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton148.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton148.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton148ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton148);
        jButton148.setBounds(390, 110, 30, 40);

        jLabel75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(51, 0, 153));
        jLabel75.setText("THE INDIVIDUAL");
        jLabel75.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares3.add(jLabel75);
        jLabel75.setBounds(10, 14, 160, 40);

        jTextField42.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totalLoanShares3.add(jTextField42);
        jTextField42.setBounds(170, 14, 220, 40);

        jButton182.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton182.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton182.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton182.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton182ActionPerformed(evt);
            }
        });
        totalLoanShares3.add(jButton182);
        jButton182.setBounds(390, 14, 30, 40);

        returnOnInvestmentPanel.add(totalLoanShares3);
        totalLoanShares3.setBounds(30, 90, 510, 330);
        totalLoanShares3.setVisible(false);

        totalLoanShares4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares4.setLayout(null);

        jLabel67.setForeground(new java.awt.Color(0, 0, 153));
        jLabel67.setText("ENDING DATE");
        jLabel67.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares4.add(jLabel67);
        jLabel67.setBounds(10, 70, 160, 40);

        jButton142.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton142.setText("Submit");
        jButton142.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton142.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton142ActionPerformed(evt);
            }
        });
        totalLoanShares4.add(jButton142);
        jButton142.setBounds(170, 120, 250, 40);

        jButton143.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton143.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton143.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton143ActionPerformed(evt);
            }
        });
        totalLoanShares4.add(jButton143);
        jButton143.setBounds(390, 20, 30, 40);

        jButton144.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton144.setText("Export Excel");
        jButton144.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton144.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton144ActionPerformed(evt);
            }
        });
        totalLoanShares4.add(jButton144);
        jButton144.setBounds(310, 170, 110, 40);

        jLabel68.setForeground(new java.awt.Color(0, 0, 153));
        jLabel68.setText("STARTING DATE");
        jLabel68.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares4.add(jLabel68);
        jLabel68.setBounds(10, 20, 160, 40);

        jTextField34.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totalLoanShares4.add(jTextField34);
        jTextField34.setBounds(170, 70, 220, 40);

        jTextField35.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totalLoanShares4.add(jTextField35);
        jTextField35.setBounds(170, 20, 220, 40);

        jButton145.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton145.setText("Export PDF");
        jButton145.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton145.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton145ActionPerformed(evt);
            }
        });
        totalLoanShares4.add(jButton145);
        jButton145.setBounds(170, 170, 130, 40);

        jButton149.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton149.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton149.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton149ActionPerformed(evt);
            }
        });
        totalLoanShares4.add(jButton149);
        jButton149.setBounds(390, 70, 30, 40);

        returnOnInvestmentPanel.add(totalLoanShares4);
        totalLoanShares4.setBounds(30, 90, 510, 330);
        totalLoanShares4.setVisible(false);

        totalLoanShares1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares1.setLayout(null);

        jButton130.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton130.setText("Submit");
        jButton130.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton130.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton130ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton130);
        jButton130.setBounds(170, 160, 250, 40);

        jButton131.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton131.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton131.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton131ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton131);
        jButton131.setBounds(390, 70, 30, 40);

        jButton132.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton132.setText("Export Excel");
        jButton132.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton132.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton132ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton132);
        jButton132.setBounds(310, 210, 110, 40);

        jLabel62.setForeground(new java.awt.Color(0, 0, 153));
        jLabel62.setText("SELECT THE INDIVIDUAL");
        jLabel62.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares1.add(jLabel62);
        jLabel62.setBounds(10, 20, 160, 40);

        jTextField28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares1.add(jTextField28);
        jTextField28.setBounds(170, 120, 220, 40);

        jTextField29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares1.add(jTextField29);
        jTextField29.setBounds(170, 20, 220, 40);

        jButton133.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton133.setText("Export PDF");
        jButton133.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton133.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton133ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton133);
        jButton133.setBounds(170, 210, 130, 40);

        jButton146.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton146.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton146.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton146ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton146);
        jButton146.setBounds(390, 120, 30, 40);

        jLabel71.setForeground(new java.awt.Color(0, 0, 153));
        jLabel71.setText("STARTING DATE");
        jLabel71.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares1.add(jLabel71);
        jLabel71.setBounds(10, 70, 160, 40);

        jButton155.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton155.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton155.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton155ActionPerformed(evt);
            }
        });
        totalLoanShares1.add(jButton155);
        jButton155.setBounds(390, 20, 30, 40);

        jTextField38.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totalLoanShares1.add(jTextField38);
        jTextField38.setBounds(170, 70, 220, 40);

        jLabel72.setForeground(new java.awt.Color(0, 0, 153));
        jLabel72.setText("ENDING DATE");
        jLabel72.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalLoanShares1.add(jLabel72);
        jLabel72.setBounds(10, 120, 160, 40);

        returnOnInvestmentPanel.add(totalLoanShares1);
        totalLoanShares1.setBounds(30, 90, 510, 330);
        totalLoanShares1.setVisible(false);

        OtherReportsPanel.add(returnOnInvestmentPanel);
        returnOnInvestmentPanel.setBounds(30, 40, 850, 550);
        returnOnInvestmentPanel.setVisible(false);

        provisionForBadLoansPanel.setBackground(java.awt.SystemColor.activeCaption);
        provisionForBadLoansPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        provisionForBadLoansPanel.setLayout(null);

        jPanel26.setLayout(null);

        jLabel73.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel73.setText("START DATE");
        jLabel73.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel26.add(jLabel73);
        jLabel73.setBounds(40, 110, 120, 30);

        jButton179.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton179.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton179.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton179ActionPerformed(evt);
            }
        });
        jPanel26.add(jButton179);
        jButton179.setBounds(320, 110, 20, 30);

        jTextField40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel26.add(jTextField40);
        jTextField40.setBounds(170, 110, 150, 30);

        jButton180.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton180.setText("SUBMIT");
        jButton180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton180ActionPerformed(evt);
            }
        });
        jPanel26.add(jButton180);
        jButton180.setBounds(170, 150, 160, 40);

        jLabel74.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel74.setText("START DATE");
        jLabel74.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel26.add(jLabel74);
        jLabel74.setBounds(40, 64, 120, 30);

        jTextField41.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel26.add(jTextField41);
        jTextField41.setBounds(170, 64, 150, 30);

        jButton181.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton181.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton181ActionPerformed(evt);
            }
        });
        jPanel26.add(jButton181);
        jButton181.setBounds(320, 64, 20, 30);

        provisionForBadLoansPanel.add(jPanel26);
        jPanel26.setBounds(60, 60, 470, 350);

        OtherReportsPanel.add(provisionForBadLoansPanel);
        provisionForBadLoansPanel.setBounds(30, 40, 850, 550);
        provisionForBadLoansPanel.setVisible(false);

        sharesSummuryPanel.setBackground(java.awt.SystemColor.activeCaption);
        sharesSummuryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sharesSummuryPanel.setLayout(null);

        jComboBox3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Individual Shares", "Group Shares" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        sharesSummuryPanel.add(jComboBox3);
        jComboBox3.setBounds(160, 50, 340, 40);

        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.setLayout(null);

        jLabel70.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel70.setText("THE INDIVIDUAL");
        jLabel70.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.add(jLabel70);
        jLabel70.setBounds(20, 20, 140, 40);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel69.setText("END DATE:");
        jLabel69.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.add(jLabel69);
        jLabel69.setBounds(20, 140, 160, 40);

        jTextField37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel29.add(jTextField37);
        jTextField37.setBounds(200, 90, 160, 40);

        jTextField36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel29.add(jTextField36);
        jTextField36.setBounds(200, 140, 160, 40);

        jButton151.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton151.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton151ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton151);
        jButton151.setBounds(360, 90, 30, 40);

        jButton152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton152.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton152ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton152);
        jButton152.setBounds(360, 140, 30, 40);

        jButton185.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton185.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton185.setText("PDF");
        jButton185.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton185ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton185);
        jButton185.setBounds(60, 230, 130, 40);

        jButton150.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton150.setText("EXCEL");
        jPanel29.add(jButton150);
        jButton150.setBounds(280, 230, 130, 40);

        jButton184.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton184.setText("Submit");
        jButton184.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton184ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton184);
        jButton184.setBounds(170, 190, 130, 40);

        jLabel80.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel80.setText("START DATE");
        jLabel80.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.add(jLabel80);
        jLabel80.setBounds(20, 90, 160, 40);

        jTextField47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel29.add(jTextField47);
        jTextField47.setBounds(170, 20, 190, 40);

        jButton192.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton192.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton192.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton192ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton192);
        jButton192.setBounds(360, 20, 30, 40);

        sharesSummuryPanel.add(jPanel29);
        jPanel29.setBounds(40, 90, 460, 350);
        jPanel29.setVisible(false);

        jButton186.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton186.setText("SHARES CAT");
        sharesSummuryPanel.add(jButton186);
        jButton186.setBounds(40, 50, 120, 40);

        jPanel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel30.setLayout(null);

        jLabel78.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel78.setText("TYPE");
        jLabel78.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel30.add(jLabel78);
        jLabel78.setBounds(30, 30, 120, 40);

        jLabel79.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel79.setText("END DATE:");
        jLabel79.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel30.add(jLabel79);
        jLabel79.setBounds(30, 140, 100, 40);

        jTextField45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel30.add(jTextField45);
        jTextField45.setBounds(140, 80, 160, 40);

        jTextField46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel30.add(jTextField46);
        jTextField46.setBounds(140, 140, 160, 40);

        jButton187.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton187.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton187ActionPerformed(evt);
            }
        });
        jPanel30.add(jButton187);
        jButton187.setBounds(310, 80, 30, 40);

        jButton188.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton188.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton188ActionPerformed(evt);
            }
        });
        jPanel30.add(jButton188);
        jButton188.setBounds(310, 140, 30, 40);

        jButton189.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton189.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton189.setText("PDF");
        jButton189.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton189ActionPerformed(evt);
            }
        });
        jPanel30.add(jButton189);
        jButton189.setBounds(80, 240, 130, 40);

        jButton190.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton190.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton190.setText("EXCEL");
        jPanel30.add(jButton190);
        jButton190.setBounds(230, 240, 130, 40);

        jButton191.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton191.setText("Submit");
        jButton191.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton191ActionPerformed(evt);
            }
        });
        jPanel30.add(jButton191);
        jButton191.setBounds(160, 190, 130, 40);

        jLabel85.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel85.setText("START DATE");
        jLabel85.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel30.add(jLabel85);
        jLabel85.setBounds(30, 80, 100, 40);

        jComboBox7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Members With Shares Only", "All Members" }));
        jPanel30.add(jComboBox7);
        jComboBox7.setBounds(140, 20, 230, 40);

        sharesSummuryPanel.add(jPanel30);
        jPanel30.setBounds(40, 90, 460, 350);
        jPanel30.setVisible(false);

        OtherReportsPanel.add(sharesSummuryPanel);
        sharesSummuryPanel.setBounds(30, 40, 850, 550);
        sharesSummuryPanel.setVisible(false);

        savingsSummuryPanel.setBackground(java.awt.SystemColor.activeCaption);
        savingsSummuryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        savingsSummuryPanel.setLayout(null);

        individualSavings.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        individualSavings.setLayout(null);

        jLabel77.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel77.setText("START DATE");
        jLabel77.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        individualSavings.add(jLabel77);
        jLabel77.setBounds(30, 60, 130, 40);

        jLabel76.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel76.setText("END DATE:");
        jLabel76.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        individualSavings.add(jLabel76);
        jLabel76.setBounds(30, 110, 130, 40);

        jTextField44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        individualSavings.add(jTextField44);
        jTextField44.setBounds(170, 60, 190, 40);

        jTextField43.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        individualSavings.add(jTextField43);
        jTextField43.setBounds(170, 110, 190, 40);

        jButton154.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton154.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton154ActionPerformed(evt);
            }
        });
        individualSavings.add(jButton154);
        jButton154.setBounds(370, 60, 30, 40);

        jButton183.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton183.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton183ActionPerformed(evt);
            }
        });
        individualSavings.add(jButton183);
        jButton183.setBounds(370, 110, 30, 40);

        jButton201.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton201.setText("Submit");
        jButton201.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton201ActionPerformed(evt);
            }
        });
        individualSavings.add(jButton201);
        jButton201.setBounds(150, 160, 170, 40);

        jButton202.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton202.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton202.setText("PDF");
        jButton202.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton202ActionPerformed(evt);
            }
        });
        individualSavings.add(jButton202);
        jButton202.setBounds(100, 210, 130, 40);

        jButton203.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton203.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton203.setText("EXCEL");
        individualSavings.add(jButton203);
        jButton203.setBounds(240, 210, 130, 40);

        jLabel88.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel88.setText("THE INDIVIDUAL");
        jLabel88.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        individualSavings.add(jLabel88);
        jLabel88.setBounds(30, 10, 130, 40);

        jTextField50.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        individualSavings.add(jTextField50);
        jTextField50.setBounds(170, 10, 190, 40);

        jButton204.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton204.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton204.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton204ActionPerformed(evt);
            }
        });
        individualSavings.add(jButton204);
        jButton204.setBounds(370, 10, 30, 40);

        savingsSummuryPanel.add(individualSavings);
        individualSavings.setBounds(30, 80, 460, 320);
        individualSavings.setVisible(false);

        groupSavingsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        groupSavingsPanel.setLayout(null);

        jLabel86.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel86.setText("START DATE");
        jLabel86.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        groupSavingsPanel.add(jLabel86);
        jLabel86.setBounds(50, 70, 100, 40);

        jLabel87.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel87.setText("END DATE:");
        jLabel87.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        groupSavingsPanel.add(jLabel87);
        jLabel87.setBounds(50, 130, 100, 40);

        jTextField48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        groupSavingsPanel.add(jTextField48);
        jTextField48.setBounds(170, 70, 160, 40);

        jTextField49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        groupSavingsPanel.add(jTextField49);
        jTextField49.setBounds(170, 130, 160, 40);

        jButton199.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton199.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton199ActionPerformed(evt);
            }
        });
        groupSavingsPanel.add(jButton199);
        jButton199.setBounds(330, 70, 30, 40);

        jButton200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton200ActionPerformed(evt);
            }
        });
        groupSavingsPanel.add(jButton200);
        jButton200.setBounds(330, 130, 30, 40);

        jButton205.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton205.setText("Submit");
        jButton205.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton205ActionPerformed(evt);
            }
        });
        groupSavingsPanel.add(jButton205);
        jButton205.setBounds(140, 180, 170, 40);

        jButton206.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton206.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/pdf.jpg"))); // NOI18N
        jButton206.setText("PDF");
        jButton206.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton206ActionPerformed(evt);
            }
        });
        groupSavingsPanel.add(jButton206);
        jButton206.setBounds(90, 230, 130, 40);

        jButton207.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton207.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/excel.png"))); // NOI18N
        jButton207.setText("EXCEL");
        groupSavingsPanel.add(jButton207);
        jButton207.setBounds(240, 230, 130, 40);

        jComboBox9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Members With Savings Only", "All Members" }));
        groupSavingsPanel.add(jComboBox9);
        jComboBox9.setBounds(170, 0, 290, 40);

        jLabel89.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel89.setText("TYPE");
        jLabel89.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        groupSavingsPanel.add(jLabel89);
        jLabel89.setBounds(50, 0, 120, 40);

        savingsSummuryPanel.add(groupSavingsPanel);
        groupSavingsPanel.setBounds(30, 80, 460, 320);
        groupSavingsPanel.setVisible(false);

        jComboBox8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Individual Savings", "Group Savings" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        savingsSummuryPanel.add(jComboBox8);
        jComboBox8.setBounds(200, 40, 290, 40);

        jButton153.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton153.setText("SAVINGS CAT:");
        savingsSummuryPanel.add(jButton153);
        jButton153.setBounds(30, 40, 170, 40);

        OtherReportsPanel.add(savingsSummuryPanel);
        savingsSummuryPanel.setBounds(30, 40, 850, 550);

        membersSelectionPanel.setBackground(java.awt.SystemColor.activeCaption);
        membersSelectionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        membersSelectionPanel.setLayout(null);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable4);
        jScrollPane7.setVisible(false);

        membersSelectionPanel.add(jScrollPane7);
        jScrollPane7.setBounds(140, 10, 430, 490);
        membersSelectionPanel.add(jTextField39);
        jTextField39.setBounds(290, 510, 250, 30);

        jLabel61.setForeground(new java.awt.Color(0, 0, 153));
        jLabel61.setText("SEARCH HERE");
        jLabel61.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        membersSelectionPanel.add(jLabel61);
        jLabel61.setBounds(140, 510, 150, 30);

        OtherReportsPanel.add(membersSelectionPanel);
        membersSelectionPanel.setBounds(30, 40, 770, 550);
        membersSelectionPanel.setVisible(false);

        jPanel33.add(OtherReportsPanel);
        OtherReportsPanel.setBounds(260, 10, 930, 630);

        jPanel32.add(jPanel33);
        jPanel33.setBounds(0, 0, 1290, 660);

        jTabbedPane1.addTab("OTHER REPORTS", jPanel32);

        jPanel34.setPreferredSize(new java.awt.Dimension(32767, 32767));
        jPanel34.setLayout(null);

        jPanel35.setBackground(java.awt.SystemColor.activeCaption);
        jPanel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel35.setForeground(java.awt.SystemColor.control);
        jPanel35.setLayout(null);

        jPanel36.setBackground(java.awt.SystemColor.activeCaption);
        jPanel36.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel36.setLayout(null);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setForeground(java.awt.SystemColor.control);
        jLabel25.setText("SELECT SUMMURY REPORT");
        jLabel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel36.add(jLabel25);
        jLabel25.setBounds(10, 10, 230, 40);

        returnOnInvestmentsButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        returnOnInvestmentsButton.setForeground(new java.awt.Color(0, 0, 255));
        returnOnInvestmentsButton.setText("RETURN ON INVESTMENTS");
        returnOnInvestmentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnOnInvestmentsButtonActionPerformed(evt);
            }
        });
        jPanel36.add(returnOnInvestmentsButton);
        returnOnInvestmentsButton.setBounds(0, 270, 250, 40);
        //returnOnInvestmentsButton.setVisible(false);

        jButton244.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton244.setForeground(new java.awt.Color(0, 0, 255));
        jButton244.setText("GENERAL SUMMURY REPORT");
        jButton244.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton244ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton244);
        jButton244.setBounds(0, 80, 250, 40);

        jButton245.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton245.setForeground(new java.awt.Color(0, 0, 255));
        jButton245.setText("SPECIFIC SUMMURY REPORT");
        jButton245.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton245ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton245);
        jButton245.setBounds(0, 120, 250, 40);

        returnOnInvestmentsButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        returnOnInvestmentsButton1.setForeground(new java.awt.Color(0, 0, 255));
        returnOnInvestmentsButton1.setText("DAILY SUMMURY REPORT");
        returnOnInvestmentsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnOnInvestmentsButton1ActionPerformed(evt);
            }
        });
        jPanel36.add(returnOnInvestmentsButton1);
        returnOnInvestmentsButton1.setBounds(0, 200, 250, 40);

        jPanel39.setBackground(java.awt.SystemColor.activeCaption);
        jPanel39.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel39.setLayout(null);
        jPanel36.add(jPanel39);
        jPanel39.setBounds(90, 560, 50, 40);

        jButton246.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton246.setForeground(new java.awt.Color(0, 0, 255));
        jButton246.setText("DAILY LOAN COLLECTIONS ");
        jButton246.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton246ActionPerformed(evt);
            }
        });
        jPanel36.add(jButton246);
        jButton246.setBounds(0, 160, 250, 40);

        jPanel35.add(jPanel36);
        jPanel36.setBounds(0, 0, 270, 640);

        jPanel37.setBackground(java.awt.SystemColor.activeCaption);
        jPanel37.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel37.setLayout(null);

        DailyCollections.setBackground(java.awt.SystemColor.activeCaption);
        DailyCollections.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailyCollections.setLayout(null);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel38.setLayout(null);

        jButton102.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton102.setText("PDF");
        jButton102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton102ActionPerformed(evt);
            }
        });
        jPanel38.add(jButton102);
        jButton102.setBounds(460, 290, 240, 40);

        jButton248.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton248.setText("EXCEL");
        jPanel38.add(jButton248);
        jButton248.setBounds(210, 290, 240, 40);

        jLabel100.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 0, 153));
        jLabel100.setText("CREATE DAILY COLLECTION WITH ARREARS DETAILS");
        jLabel100.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel38.add(jLabel100);
        jLabel100.setBounds(290, 90, 400, 40);

        jLabel107.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel107.setText("COLLECTION DATE");
        jLabel107.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel38.add(jLabel107);
        jLabel107.setBounds(300, 160, 150, 30);
        jLabel102.setVisible(false);

        jTextField65.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel38.add(jTextField65);
        jTextField65.setBounds(450, 160, 180, 30);
        jTextField60.setVisible(false);

        jButton271.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton271.setText("SUMMIT");
        jButton271.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton271ActionPerformed(evt);
            }
        });
        jPanel38.add(jButton271);
        jButton271.setBounds(360, 220, 240, 40);

        jButton247.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton247.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton247ActionPerformed(evt);
            }
        });
        jPanel38.add(jButton247);
        jButton247.setBounds(630, 160, 30, 30);
        jButton237.setVisible(false);

        jTabbedPane2.addTab("COLLECTIONS", jPanel38);

        jPanel42.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel42.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel42.setLayout(null);

        jLabel120.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(0, 0, 153));
        jLabel120.setText("CREATE DAILY COLLECTION WITH ARREARS DETAILS");
        jLabel120.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel42.add(jLabel120);
        jLabel120.setBounds(300, 90, 400, 40);

        jLabel121.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel121.setText("COLLECTION DATE");
        jLabel121.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel42.add(jLabel121);
        jLabel121.setBounds(320, 150, 150, 30);
        jLabel102.setVisible(false);

        jTextField66.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel42.add(jTextField66);
        jTextField66.setBounds(470, 150, 180, 30);
        jTextField60.setVisible(false);

        jButton272.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton272.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton272ActionPerformed(evt);
            }
        });
        jPanel42.add(jButton272);
        jButton272.setBounds(650, 150, 30, 30);
        jButton237.setVisible(false);

        jButton273.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton273.setText("SUMMIT");
        jButton273.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton273ActionPerformed(evt);
            }
        });
        jPanel42.add(jButton273);
        jButton273.setBounds(390, 200, 240, 40);

        jButton274.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton274.setText("EXCEL");
        jPanel42.add(jButton274);
        jButton274.setBounds(270, 260, 240, 40);

        jButton275.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton275.setText("PDF");
        jPanel42.add(jButton275);
        jButton275.setBounds(510, 260, 240, 40);

        jTabbedPane2.addTab("COLLECTIONS ARREARS", jPanel42);

        jPanel43.setLayout(null);

        jLabel122.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(0, 0, 153));
        jLabel122.setText("CREATE DAILY PRINCIPAL COLLECTIONS DETAILS");
        jLabel122.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel43.add(jLabel122);
        jLabel122.setBounds(270, 90, 420, 40);

        jLabel123.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel123.setText("COLLECTION DATE");
        jLabel123.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel43.add(jLabel123);
        jLabel123.setBounds(300, 150, 150, 30);
        jLabel102.setVisible(false);

        jTextField78.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel43.add(jTextField78);
        jTextField78.setBounds(450, 150, 180, 30);
        jTextField60.setVisible(false);

        jButton276.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton276.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton276ActionPerformed(evt);
            }
        });
        jPanel43.add(jButton276);
        jButton276.setBounds(630, 150, 30, 30);
        jButton237.setVisible(false);

        jButton277.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton277.setText("SUMMIT");
        jButton277.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton277ActionPerformed(evt);
            }
        });
        jPanel43.add(jButton277);
        jButton277.setBounds(370, 200, 240, 40);

        jButton278.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton278.setText("EXCEL");
        jPanel43.add(jButton278);
        jButton278.setBounds(230, 250, 240, 40);

        jButton279.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton279.setText("PDF");
        jPanel43.add(jButton279);
        jButton279.setBounds(470, 250, 240, 40);

        jTabbedPane2.addTab("PRINCIPAL", jPanel43);

        jPanel44.setLayout(null);

        jLabel124.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(0, 0, 153));
        jLabel124.setText("CREATE DAILY PRINCIPAL COLLECTIONS WITH ARREARS DETAILS");
        jLabel124.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel44.add(jLabel124);
        jLabel124.setBounds(260, 100, 480, 40);

        jLabel125.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel125.setText("COLLECTION DATE");
        jLabel125.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel44.add(jLabel125);
        jLabel125.setBounds(320, 160, 150, 30);
        jLabel102.setVisible(false);

        jTextField79.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel44.add(jTextField79);
        jTextField79.setBounds(470, 160, 180, 30);
        jTextField60.setVisible(false);

        jButton280.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton280.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton280ActionPerformed(evt);
            }
        });
        jPanel44.add(jButton280);
        jButton280.setBounds(650, 160, 30, 30);
        jButton237.setVisible(false);

        jButton281.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton281.setText("SUMMIT");
        jButton281.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton281ActionPerformed(evt);
            }
        });
        jPanel44.add(jButton281);
        jButton281.setBounds(370, 200, 240, 40);

        jButton282.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton282.setText("EXCEL");
        jPanel44.add(jButton282);
        jButton282.setBounds(240, 250, 240, 40);

        jButton283.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton283.setText("PDF");
        jPanel44.add(jButton283);
        jButton283.setBounds(480, 250, 240, 40);

        jTabbedPane2.addTab("PRINCIPAL  ARREARS", jPanel44);

        jPanel45.setLayout(null);

        jButton284.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton284.setText("EXCEL");
        jPanel45.add(jButton284);
        jButton284.setBounds(230, 250, 240, 40);

        jButton285.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton285.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton285ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton285);
        jButton285.setBounds(600, 150, 30, 30);
        jButton237.setVisible(false);

        jButton286.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton286.setText("PDF");
        jPanel45.add(jButton286);
        jButton286.setBounds(470, 250, 240, 40);

        jLabel126.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(0, 0, 153));
        jLabel126.setText("CREATE DAILY INTEREST COLLECTION DETAILS");
        jLabel126.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel45.add(jLabel126);
        jLabel126.setBounds(260, 80, 450, 40);

        jButton287.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton287.setText("SUMMIT");
        jButton287.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton287ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton287);
        jButton287.setBounds(330, 200, 240, 40);

        jLabel127.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel127.setText("COLLECTION DATE");
        jLabel127.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel45.add(jLabel127);
        jLabel127.setBounds(270, 150, 150, 30);
        jLabel102.setVisible(false);

        jTextField80.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel45.add(jTextField80);
        jTextField80.setBounds(420, 150, 180, 30);
        jTextField60.setVisible(false);

        jTabbedPane2.addTab("INTEREST", jPanel45);

        jPanel46.setLayout(null);

        jTextField81.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel46.add(jTextField81);
        jTextField81.setBounds(450, 170, 180, 30);
        jTextField60.setVisible(false);

        jButton288.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton288.setText("EXCEL");
        jPanel46.add(jButton288);
        jButton288.setBounds(260, 280, 240, 40);

        jButton289.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton289.setText("SUMMIT");
        jButton289.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton289ActionPerformed(evt);
            }
        });
        jPanel46.add(jButton289);
        jButton289.setBounds(360, 220, 240, 40);

        jButton290.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton290.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton290ActionPerformed(evt);
            }
        });
        jPanel46.add(jButton290);
        jButton290.setBounds(630, 170, 30, 30);
        jButton237.setVisible(false);

        jButton291.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton291.setText("PDF");
        jPanel46.add(jButton291);
        jButton291.setBounds(500, 280, 240, 40);

        jLabel128.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(0, 0, 153));
        jLabel128.setText("CREATE DAILY INTEREST COLLECTIONS WITH ARREARS DETAILS");
        jLabel128.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel46.add(jLabel128);
        jLabel128.setBounds(260, 100, 470, 40);

        jLabel129.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel129.setText("COLLECTION DATE");
        jLabel129.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel46.add(jLabel129);
        jLabel129.setBounds(300, 170, 150, 30);
        jLabel102.setVisible(false);

        jTabbedPane2.addTab(" INTEREST ARREARS", jPanel46);

        jPanel47.setLayout(null);

        jTextField82.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel47.add(jTextField82);
        jTextField82.setBounds(490, 160, 180, 30);
        jTextField60.setVisible(false);

        jButton292.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton292.setText("EXCEL");
        jPanel47.add(jButton292);
        jButton292.setBounds(230, 260, 240, 40);

        jButton293.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton293.setText("SUMMIT");
        jPanel47.add(jButton293);
        jButton293.setBounds(410, 210, 240, 40);

        jButton294.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton294.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton294ActionPerformed(evt);
            }
        });
        jPanel47.add(jButton294);
        jButton294.setBounds(670, 160, 30, 30);
        jButton237.setVisible(false);

        jButton295.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton295.setText("PDF");
        jPanel47.add(jButton295);
        jButton295.setBounds(470, 260, 240, 40);

        jLabel130.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(0, 0, 153));
        jLabel130.setText("CREATE DAILY ACCUMULATED INTEREST COLLECTIONS DETAILS");
        jLabel130.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel47.add(jLabel130);
        jLabel130.setBounds(260, 100, 500, 40);

        jLabel131.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel131.setText("COLLECTION DATE");
        jLabel131.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel47.add(jLabel131);
        jLabel131.setBounds(340, 160, 150, 30);
        jLabel102.setVisible(false);

        jTabbedPane2.addTab("ACCUM INTEREST", jPanel47);

        jPanel48.setLayout(null);

        jTextField83.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel48.add(jTextField83);
        jTextField83.setBounds(500, 170, 180, 30);
        jTextField60.setVisible(false);

        jButton296.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton296.setText("EXCEL");
        jPanel48.add(jButton296);
        jButton296.setBounds(310, 260, 240, 40);

        jButton297.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton297.setText("SUMMIT");
        jPanel48.add(jButton297);
        jButton297.setBounds(420, 210, 240, 40);

        jButton298.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton298.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton298ActionPerformed(evt);
            }
        });
        jPanel48.add(jButton298);
        jButton298.setBounds(680, 170, 30, 30);
        jButton237.setVisible(false);

        jButton299.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton299.setText("PDF");
        jPanel48.add(jButton299);
        jButton299.setBounds(550, 260, 240, 40);

        jLabel132.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(0, 0, 153));
        jLabel132.setText("CREATE DAILY ACCUMULATED INTEREST COLLECTIONS WITH ARREARS DETAILS");
        jLabel132.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel48.add(jLabel132);
        jLabel132.setBounds(260, 100, 570, 40);

        jLabel133.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel133.setText("COLLECTION DATE");
        jLabel133.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel48.add(jLabel133);
        jLabel133.setBounds(350, 170, 150, 30);
        jLabel102.setVisible(false);

        jTabbedPane2.addTab("ACCUM INTEREST ARREARS", jPanel48);

        jPanel49.setLayout(null);

        jTextField84.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel49.add(jTextField84);
        jTextField84.setBounds(410, 160, 180, 30);
        jTextField60.setVisible(false);

        jButton300.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton300.setText("EXCEL");
        jPanel49.add(jButton300);
        jButton300.setBounds(250, 260, 240, 40);

        jButton301.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton301.setText("SUMMIT");
        jPanel49.add(jButton301);
        jButton301.setBounds(340, 200, 240, 40);

        jButton302.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton302.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton302ActionPerformed(evt);
            }
        });
        jPanel49.add(jButton302);
        jButton302.setBounds(590, 160, 30, 30);
        jButton237.setVisible(false);

        jButton303.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton303.setText("PDF");
        jPanel49.add(jButton303);
        jButton303.setBounds(490, 260, 240, 40);

        jLabel134.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(0, 0, 153));
        jLabel134.setText("CREATE DAILY PENALTY COLLECTION DETAILS");
        jLabel134.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel49.add(jLabel134);
        jLabel134.setBounds(260, 100, 400, 40);

        jLabel135.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel135.setText("COLLECTION DATE");
        jLabel135.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel49.add(jLabel135);
        jLabel135.setBounds(260, 160, 150, 30);
        jLabel102.setVisible(false);

        jTabbedPane2.addTab("PENALTY", jPanel49);

        jPanel50.setLayout(null);

        jLabel136.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(0, 0, 153));
        jLabel136.setText("CREATE DAILY PENALTY COLLECTION WITH ARREARS DETAILS");
        jLabel136.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel50.add(jLabel136);
        jLabel136.setBounds(260, 100, 480, 40);

        jLabel137.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel137.setText("COLLECTION DATE");
        jLabel137.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel50.add(jLabel137);
        jLabel137.setBounds(290, 160, 150, 30);
        jLabel102.setVisible(false);

        jTextField85.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel50.add(jTextField85);
        jTextField85.setBounds(440, 160, 180, 30);
        jTextField60.setVisible(false);

        jButton304.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton304.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton304ActionPerformed(evt);
            }
        });
        jPanel50.add(jButton304);
        jButton304.setBounds(620, 160, 30, 30);
        jButton237.setVisible(false);

        jButton305.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton305.setText("SUMMIT");
        jPanel50.add(jButton305);
        jButton305.setBounds(390, 210, 240, 40);

        jButton306.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton306.setText("EXCEL");
        jPanel50.add(jButton306);
        jButton306.setBounds(280, 260, 240, 40);

        jButton307.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton307.setText("PDF");
        jPanel50.add(jButton307);
        jButton307.setBounds(520, 260, 240, 40);

        jTabbedPane2.addTab("PENALTY ARREARS", jPanel50);

        DailyCollections.add(jTabbedPane2);
        jTabbedPane2.setBounds(10, 10, 940, 510);

        jPanel37.add(DailyCollections);
        DailyCollections.setBounds(30, 40, 960, 540);
        DailyCollections.setVisible(false);

        DailySummuryReport.setBackground(java.awt.SystemColor.activeCaption);
        DailySummuryReport.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailySummuryReport.setLayout(null);

        jLabel140.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(0, 0, 153));
        jLabel140.setText("CREATE DAILY COLLECTION WITH ARREARS DETAILS");
        jLabel140.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailySummuryReport.add(jLabel140);
        jLabel140.setBounds(270, 240, 400, 40);

        jLabel141.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel141.setText("COLLECTION DATE");
        jLabel141.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailySummuryReport.add(jLabel141);
        jLabel141.setBounds(270, 300, 150, 30);
        jLabel102.setVisible(false);

        jTextField88.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        DailySummuryReport.add(jTextField88);
        jTextField88.setBounds(420, 300, 180, 30);
        jTextField60.setVisible(false);

        jButton317.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton317.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton317ActionPerformed(evt);
            }
        });
        DailySummuryReport.add(jButton317);
        jButton317.setBounds(600, 300, 30, 30);
        jButton237.setVisible(false);

        jButton318.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton318.setText("SUMMIT");
        jButton318.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton318ActionPerformed(evt);
            }
        });
        DailySummuryReport.add(jButton318);
        jButton318.setBounds(360, 350, 240, 40);

        jPanel37.add(DailySummuryReport);
        DailySummuryReport.setBounds(30, 40, 950, 540);
        DailySummuryReport.setVisible(false);

        membersSelectionPanel1.setBackground(java.awt.SystemColor.activeCaption);
        membersSelectionPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        membersSelectionPanel1.setLayout(null);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable6.setFocusTraversalPolicyProvider(true);
        jScrollPane11.setViewportView(jTable6);
        jScrollPane7.setVisible(false);

        membersSelectionPanel1.add(jScrollPane11);
        jScrollPane11.setBounds(20, 10, 890, 490);
        membersSelectionPanel1.add(jTextField77);
        jTextField77.setBounds(290, 510, 250, 30);

        jLabel119.setForeground(new java.awt.Color(0, 0, 153));
        jLabel119.setText("SEARCH HERE");
        jLabel119.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        membersSelectionPanel1.add(jLabel119);
        jLabel119.setBounds(140, 510, 150, 30);

        jPanel37.add(membersSelectionPanel1);
        membersSelectionPanel1.setBounds(30, 40, 950, 540);
        membersSelectionPanel1.setVisible(false);

        jPanel41SpecificSummuryReport.setBackground(java.awt.SystemColor.activeCaption);
        jPanel41SpecificSummuryReport.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41SpecificSummuryReport.setLayout(null);

        jButton311.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton311.setForeground(new java.awt.Color(0, 153, 51));
        jButton311.setText("PDF");
        jButton311.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton311ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton311);
        jButton311.setBounds(270, 390, 160, 40);

        jLabel138.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(0, 0, 153));
        jLabel138.setText("STARTING DATE");
        jLabel138.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41SpecificSummuryReport.add(jLabel138);
        jLabel138.setBounds(230, 220, 160, 40);

        jLabel139.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(0, 0, 153));
        jLabel139.setText("ENDING DATE");
        jLabel139.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41SpecificSummuryReport.add(jLabel139);
        jLabel139.setBounds(230, 290, 160, 40);

        jTextField87.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel41SpecificSummuryReport.add(jTextField87);
        jTextField87.setBounds(390, 220, 220, 40);

        jButton312.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton312.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton312.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton312.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton312ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton312);
        jButton312.setBounds(610, 220, 30, 40);

        jTextField86.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel41SpecificSummuryReport.add(jTextField86);
        jTextField86.setBounds(390, 290, 220, 40);

        jButton313.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton313.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton313.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton313.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton313ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton313);
        jButton313.setBounds(610, 290, 30, 40);

        jButton314.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton314.setForeground(new java.awt.Color(0, 153, 51));
        jButton314.setText("SELECT SUMMURY DETAILS");
        jButton314.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton314ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton314);
        jButton314.setBounds(250, 130, 370, 40);

        jButton315.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton315.setForeground(new java.awt.Color(0, 153, 51));
        jButton315.setText("EXCEL");
        jButton315.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton315ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton315);
        jButton315.setBounds(430, 390, 160, 40);

        jButton316.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton316.setForeground(new java.awt.Color(0, 153, 51));
        jButton316.setText("SUBMIT");
        jButton316.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton316ActionPerformed(evt);
            }
        });
        jPanel41SpecificSummuryReport.add(jButton316);
        jButton316.setBounds(350, 340, 160, 40);

        jPanel37.add(jPanel41SpecificSummuryReport);
        jPanel41SpecificSummuryReport.setBounds(30, 40, 950, 540);
        jPanel41SpecificSummuryReport.setVisible(false);

        returnOnInvestmentPanels.setBackground(java.awt.SystemColor.activeCaption);
        returnOnInvestmentPanels.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        returnOnInvestmentPanels.setLayout(null);

        jComboBox12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox12.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT ROI SUMMERY", "Daily Individual ROI", "Monthly Individual ROI", "Group ROI", "Group Savings And Shares", " ", " " }));
        jComboBox12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        returnOnInvestmentPanels.add(jComboBox12);
        jComboBox12.setBounds(70, 90, 530, 50);
        jComboBox12.setVisible(false);

        GroupROI.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupROI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        GroupROI.setLayout(null);

        jLabel109.setForeground(new java.awt.Color(0, 0, 153));
        jLabel109.setText("ENDING DATE");
        jLabel109.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupROI.add(jLabel109);
        jLabel109.setBounds(10, 70, 160, 40);

        jButton249.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton249.setText("Submit");
        jButton249.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton249.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton249ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton249);
        jButton249.setBounds(170, 120, 250, 40);

        jButton250.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton250.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton250.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton250ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton250);
        jButton250.setBounds(390, 20, 30, 40);

        jButton251.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton251.setText("Group ROI");
        jButton251.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton251.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton251ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton251);
        jButton251.setBounds(20, 150, 110, 40);

        jLabel110.setForeground(new java.awt.Color(0, 0, 153));
        jLabel110.setText("STARTING DATE");
        jLabel110.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupROI.add(jLabel110);
        jLabel110.setBounds(10, 20, 160, 40);

        jTextField67.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        GroupROI.add(jTextField67);
        jTextField67.setBounds(170, 70, 220, 40);

        jTextField68.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        GroupROI.add(jTextField68);
        jTextField68.setBounds(170, 20, 220, 40);

        jButton252.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton252.setText("Export PDF");
        jButton252.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton252.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton252ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton252);
        jButton252.setBounds(170, 170, 130, 40);

        jButton253.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton253.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton253.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton253.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton253ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton253);
        jButton253.setBounds(390, 70, 30, 40);

        jButton320.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton320.setText("Export Excel");
        jButton320.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton320.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton320ActionPerformed(evt);
            }
        });
        GroupROI.add(jButton320);
        jButton320.setBounds(310, 170, 110, 40);

        returnOnInvestmentPanels.add(GroupROI);
        GroupROI.setBounds(70, 140, 530, 260);
        GroupROI.setVisible(false);

        MonthlyIndividualROI.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MonthlyIndividualROI.setLayout(null);

        jLabel111.setForeground(new java.awt.Color(0, 0, 153));
        jLabel111.setText("ENDING DATE");
        jLabel111.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MonthlyIndividualROI.add(jLabel111);
        jLabel111.setBounds(10, 110, 160, 40);

        jButton254.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton254.setText("Submit");
        jButton254.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton254.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton254ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton254);
        jButton254.setBounds(170, 150, 250, 40);

        jButton255.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton255.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton255.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton255.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton255ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton255);
        jButton255.setBounds(390, 60, 30, 40);

        jButton256.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton256.setText("Export Excel");
        jButton256.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton256.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton256ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton256);
        jButton256.setBounds(310, 200, 110, 40);

        jLabel112.setForeground(new java.awt.Color(0, 0, 153));
        jLabel112.setText("STARTING DATE");
        jLabel112.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MonthlyIndividualROI.add(jLabel112);
        jLabel112.setBounds(10, 60, 160, 40);

        jTextField69.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        MonthlyIndividualROI.add(jTextField69);
        jTextField69.setBounds(170, 110, 220, 40);

        jTextField70.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        MonthlyIndividualROI.add(jTextField70);
        jTextField70.setBounds(170, 60, 220, 40);

        jButton257.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton257.setText("Export PDF");
        jButton257.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton257.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton257ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton257);
        jButton257.setBounds(170, 200, 130, 40);

        jButton258.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton258.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton258.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton258.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton258ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton258);
        jButton258.setBounds(390, 110, 30, 40);

        jLabel113.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(51, 0, 153));
        jLabel113.setText("THE INDIVIDUAL");
        jLabel113.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MonthlyIndividualROI.add(jLabel113);
        jLabel113.setBounds(10, 14, 160, 40);

        jTextField71.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        MonthlyIndividualROI.add(jTextField71);
        jTextField71.setBounds(170, 14, 220, 40);

        jButton259.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton259.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton259.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton259.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton259ActionPerformed(evt);
            }
        });
        MonthlyIndividualROI.add(jButton259);
        jButton259.setBounds(390, 14, 30, 40);

        returnOnInvestmentPanels.add(MonthlyIndividualROI);
        MonthlyIndividualROI.setBounds(100, 430, 70, 60);
        MonthlyIndividualROI.setVisible(false);

        GroupSavingsAndShares.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupSavingsAndShares.setLayout(null);

        jLabel114.setForeground(new java.awt.Color(0, 0, 153));
        jLabel114.setText("ENDING DATE");
        jLabel114.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupSavingsAndShares.add(jLabel114);
        jLabel114.setBounds(10, 70, 160, 40);

        jButton260.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton260.setText("Submit");
        jButton260.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton260.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton260ActionPerformed(evt);
            }
        });
        GroupSavingsAndShares.add(jButton260);
        jButton260.setBounds(170, 120, 250, 40);

        jButton261.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton261.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton261.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton261.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton261ActionPerformed(evt);
            }
        });
        GroupSavingsAndShares.add(jButton261);
        jButton261.setBounds(390, 20, 30, 40);

        jButton262.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton262.setText("Export Excel");
        jButton262.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton262.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton262ActionPerformed(evt);
            }
        });
        GroupSavingsAndShares.add(jButton262);
        jButton262.setBounds(310, 170, 110, 40);

        jLabel115.setForeground(new java.awt.Color(0, 0, 153));
        jLabel115.setText("STARTING DATE");
        jLabel115.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GroupSavingsAndShares.add(jLabel115);
        jLabel115.setBounds(10, 20, 160, 40);

        jTextField72.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        GroupSavingsAndShares.add(jTextField72);
        jTextField72.setBounds(170, 70, 220, 40);

        jTextField73.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        GroupSavingsAndShares.add(jTextField73);
        jTextField73.setBounds(170, 20, 220, 40);

        jButton263.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton263.setText("Export PDF");
        jButton263.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton263.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton263ActionPerformed(evt);
            }
        });
        GroupSavingsAndShares.add(jButton263);
        jButton263.setBounds(170, 170, 130, 40);

        jButton264.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton264.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton264.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton264.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton264ActionPerformed(evt);
            }
        });
        GroupSavingsAndShares.add(jButton264);
        jButton264.setBounds(390, 70, 30, 40);

        returnOnInvestmentPanels.add(GroupSavingsAndShares);
        GroupSavingsAndShares.setBounds(580, 410, 60, 70);
        GroupSavingsAndShares.setVisible(false);

        DailyIndividualROI.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailyIndividualROI.setLayout(null);

        jButton265.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton265.setText("Submit");
        jButton265.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton265.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton265ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton265);
        jButton265.setBounds(170, 160, 250, 40);

        jButton266.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton266.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton266.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton266.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton266ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton266);
        jButton266.setBounds(390, 70, 30, 40);

        jButton267.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton267.setText("INDIVIDUAL");
        jButton267.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton267.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton267ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton267);
        jButton267.setBounds(20, 180, 110, 40);

        jLabel116.setForeground(new java.awt.Color(0, 0, 153));
        jLabel116.setText("SELECT THE INDIVIDUAL");
        jLabel116.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailyIndividualROI.add(jLabel116);
        jLabel116.setBounds(10, 20, 160, 40);

        jTextField74.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        DailyIndividualROI.add(jTextField74);
        jTextField74.setBounds(170, 120, 220, 40);

        jTextField75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        DailyIndividualROI.add(jTextField75);
        jTextField75.setBounds(170, 20, 220, 40);

        jButton268.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton268.setText("Export PDF");
        jButton268.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton268.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton268ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton268);
        jButton268.setBounds(170, 210, 130, 40);

        jButton269.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton269.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton269.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton269.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton269ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton269);
        jButton269.setBounds(390, 120, 30, 40);

        jLabel117.setForeground(new java.awt.Color(0, 0, 153));
        jLabel117.setText("STARTING DATE");
        jLabel117.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailyIndividualROI.add(jLabel117);
        jLabel117.setBounds(10, 70, 160, 40);

        jButton270.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton270.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/find-icon.png"))); // NOI18N
        jButton270.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton270.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton270ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton270);
        jButton270.setBounds(390, 20, 30, 40);

        jTextField76.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        DailyIndividualROI.add(jTextField76);
        jTextField76.setBounds(170, 70, 220, 40);

        jLabel118.setForeground(new java.awt.Color(0, 0, 153));
        jLabel118.setText("ENDING DATE");
        jLabel118.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DailyIndividualROI.add(jLabel118);
        jLabel118.setBounds(10, 120, 160, 40);

        jButton321.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton321.setText("Export Excel");
        jButton321.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton321.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton321ActionPerformed(evt);
            }
        });
        DailyIndividualROI.add(jButton321);
        jButton321.setBounds(310, 210, 110, 40);

        returnOnInvestmentPanels.add(DailyIndividualROI);
        DailyIndividualROI.setBounds(70, 140, 530, 260);
        DailyIndividualROI.setVisible(false);

        jComboBox13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox13.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Report Category", "On Shares", "On Savings" }));
        jComboBox13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        returnOnInvestmentPanels.add(jComboBox13);
        jComboBox13.setBounds(130, 40, 330, 50);

        jPanel37.add(returnOnInvestmentPanels);
        returnOnInvestmentPanels.setBounds(30, 40, 950, 540);
        returnOnInvestmentPanels.setVisible(false);

        summuryReportJPanel.setBackground(java.awt.SystemColor.activeCaption);
        summuryReportJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        summuryReportJPanel.setLayout(null);

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 47, 113));
        jLabel47.setText("CREATE SUMMURY REPORT");
        jLabel47.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        summuryReportJPanel.add(jLabel47);
        jLabel47.setBounds(260, 60, 320, 50);

        jLabel105.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel105.setText("START DATE");
        jLabel105.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        summuryReportJPanel.add(jLabel105);
        jLabel105.setBounds(260, 140, 100, 30);
        jLabel102.setVisible(false);

        jLabel106.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel106.setText("END DATE");
        jLabel106.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        summuryReportJPanel.add(jLabel106);
        jLabel106.setBounds(260, 180, 100, 30);
        jLabel106.setVisible(false);

        jTextField64.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        summuryReportJPanel.add(jTextField64);
        jTextField64.setBounds(370, 140, 180, 30);
        jTextField60.setVisible(false);

        jTextField63.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        summuryReportJPanel.add(jTextField63);
        jTextField63.setBounds(370, 180, 180, 30);
        jTextField63.setVisible(false);

        jButton243.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton243.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton243ActionPerformed(evt);
            }
        });
        summuryReportJPanel.add(jButton243);
        jButton243.setBounds(560, 180, 30, 30);
        jButton243.setVisible(false);

        jButton242.setIcon(new javax.swing.ImageIcon(getClass().getResource("/googo/pmms/project2/images/calender.png"))); // NOI18N
        jButton242.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton242ActionPerformed(evt);
            }
        });
        summuryReportJPanel.add(jButton242);
        jButton242.setBounds(560, 140, 30, 30);
        jButton237.setVisible(false);

        jButton103.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton103.setText("SUBMIT");
        jButton103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton103ActionPerformed(evt);
            }
        });
        summuryReportJPanel.add(jButton103);
        jButton103.setBounds(350, 220, 160, 40);

        jPanel37.add(summuryReportJPanel);
        summuryReportJPanel.setBounds(30, 40, 950, 540);
        summuryReportJPanel.setVisible(false);

        jPanel35.add(jPanel37);
        jPanel37.setBounds(280, 10, 1010, 630);

        jPanel34.add(jPanel35);
        jPanel35.setBounds(0, 0, 1280, 650);

        jTabbedPane1.addTab("SUMMURY REPORTS", jPanel34);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 1300, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cashFlowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButtonActionPerformed
    Integer z4=104;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z4.toString());       
    }//GEN-LAST:event_cashFlowButtonActionPerformed

    private void incomeStatementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeStatementButtonActionPerformed
     
       
         trialBalancePanel1.setVisible(false);
        jLabel23.setText("\t\t"+"SELECT FROM CALENDAR THE EFFECTIVE P&L ENDING PERIOD AND SUBMIT");  
        generalLedgerPanel2.setVisible(false);
         GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
         pAndLPanel.setVisible(true);
        
        balanceSheetPanel.setVisible(false);
    }//GEN-LAST:event_incomeStatementButtonActionPerformed

    private void balanceSheetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButtonActionPerformed
    
        
    trialBalancePanel1.setVisible(false);
        generalLedgerPanel2.setVisible(false);
         GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(true);
         pAndLPanel.setVisible(false);
        jLabel20.setText("\t\t"+"SELECT FROM CALENDAR THE EFFECTIVE BALANCE SHEET DATE AND SUBMIT");  
  
    }//GEN-LAST:event_balanceSheetButtonActionPerformed

    private void trialBalanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trialBalanceButtonActionPerformed
         trialBalancePanel1.setVisible(true); 
         generalLedgerPanel2.setVisible(false);
         GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
         pAndLPanel.setVisible(false);
        Integer z3=103;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z3.toString());         
    }//GEN-LAST:event_trialBalanceButtonActionPerformed

    private void accountStatementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountStatementActionPerformed
               
        trialBalancePanel1.setVisible(false);
        pAndLPanel.setVisible(false);
        accountStatementPanal.setVisible(true);
      generalLedgerPanel2.setVisible(false);
         GeneralLedgerPanel1.setVisible(false);

        balanceSheetPanel.setVisible(false);

    }//GEN-LAST:event_accountStatementActionPerformed

    private void cashFlowButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton2ActionPerformed

        jPanel51.setVisible(false);
            loansDisbursedPanel1.setVisible(false); 
             loansDisbursedPanel2 .setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
              BOGAPanel1.setVisible(false);
//        agingReport.setVisible(false);
           loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
      BOGAPanel2.setVisible(false);
          jPanel40.setVisible(false);
   loanPortFolioPanel.setVisible(false);
   loansAppliedPanel.setVisible(false);
   loansApprovedPanel.setVisible(false);
   loansDisbursedPanel.setVisible(true);
   loansDue.setVisible(false);
      loansInArrears.setVisible(false);
       loansDueForWriteOff.setVisible(false);
        portfolioAtRisk.setVisible(false);
//        performingLoanPortfolio.setVisible(false);
         loansToStaffMembers.setVisible(false);
           summuryOnLoans .setVisible(false);
            loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_cashFlowButton2ActionPerformed

    private void loanApprovedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanApprovedButtonActionPerformed

         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
     jPanel51.setVisible(false);
         loansDisbursedPanel1.setVisible(false); 
          loansDisbursedPanel2 .setVisible(false);
                  BOGAPanel2.setVisible(false);
                    jPanel40.setVisible(false);
         loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
    BOGAPanel1.setVisible(true);
        jPanel51.setVisible(false);
    BOGAPanel2.setVisible(false);
   loanPortFolioPanel.setVisible(false);
     loansAppliedPanel.setVisible(false);
     loansApprovedPanel.setVisible(false);
     loansDisbursedPanel.setVisible(false);
     loansDue.setVisible(false);
        loansInArrears.setVisible(false);
         loansDueForWriteOff.setVisible(false);
          portfolioAtRisk.setVisible(false);
//          performingLoanPortfolio.setVisible(false);
           loansToStaffMembers.setVisible(false);
             summuryOnLoans .setVisible(false);
                loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_loanApprovedButtonActionPerformed

    private void loanAppliedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanAppliedButtonActionPerformed
    BOGAPanel1.setVisible(false);
        loansDisbursedPanel1.setVisible(false); 
         loansDisbursedPanel2 .setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        BOGAPanel1.setVisible(false);    
 loanDisbursementSchedule.setVisible(false);
 jPanel51.setVisible(false);
        loansDue1   .setVisible(false);
            jPanel40.setVisible(false);
          BOGAPanel2.setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
   loansAppliedPanel.setVisible(true);
   loansApprovedPanel.setVisible(false);
   loansDisbursedPanel.setVisible(false);
   loansDue.setVisible(false);
      loansInArrears.setVisible(false);
       loansDueForWriteOff.setVisible(false);
        portfolioAtRisk.setVisible(false);
//        performingLoanPortfolio.setVisible(false);
         loansToStaffMembers.setVisible(false);
           summuryOnLoans .setVisible(false);
            loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_loanAppliedButtonActionPerformed

    private void loanPortFolioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanPortFolioButtonActionPerformed
       BOGAPanel1.setVisible(false);
           loansDisbursedPanel1.setVisible(false); 
            loansDisbursedPanel2 .setVisible(false);
         BOGAPanel2.setVisible(false);
             jPanel40.setVisible(false);
         jPanel51.setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(true);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
            summuryOnLoans .setVisible(false);
       loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_loanPortFolioButtonActionPerformed

    private void loanStatementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanStatementActionPerformed
    BOGAPanel1.setVisible(false);
    loansDisbursedPanel1.setVisible(false);
         loansDisbursedPanel2 .setVisible(false);
        loanDisbursementSchedule.setVisible(false);
          BOGAPanel2.setVisible(false);
              jPanel51.setVisible(false);
        loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(true);
            jPanel40.setVisible(false);
    BOGAPanel.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
    loansDueForWriteOff.setVisible(false);
//    performingLoanPortfolio.setVisible(false);
    loansToStaffMembers.setVisible(false);
    summuryOnLoans .setVisible(false);
//    agingReport.setVisible(false);
    portfolioAtRisk.setVisible(false);
    loansCompletedPanel.setVisible(false);
      fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");   
    }//GEN-LAST:event_loanStatementActionPerformed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
 Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());        
       financialSupportPanel1.setVisible(true);
             FinancialReportsPanel.setVisible(true);
             loanReportsSubPanel.setVisible(false); 
             loanReportsPanel.setVisible(false); 
//             OtherReportsSubPanel.setVisible(false); 
             OtherReportsPanel.setVisible(false); 
             BalancesReportSubPanel.setVisible(false); 
             BalancesPanel.setVisible(false); 
QuickSummuryPanel.setVisible(false); 
        jLabel11.setVisible(false);

    jLabel1.setVisible(true);
    jLabel2.setVisible(false);
    jLabel6.setVisible(false);

   
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
QuickSummuryPanel.setVisible(false);          
        FinancialReportsPanel .setVisible(false); 
         OtherReportsPanel .setVisible(false); 
        loanReportsSubPanel.setVisible(true);
//    financialSupportPanel1.setVisible(false);
      jLabel1.setVisible(false);
    jLabel2.setVisible(false);
    jLabel6.setVisible(true);
//      OtherReportsSubPanel.setVisible(false);
      jLabel11.setVisible(false);
    BalancesReportSubPanel.setVisible(false);
    loanReportsPanel.setVisible(true);
     Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());
    }//GEN-LAST:event_jLabel1MousePressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

         summuryReportDetails=new ArrayList();
       dbq.fillMeWithAllAdminDetails(jTable8);
        dbq.fillMeWithAllLoanAmounts(jTable7);
   


  HeaderRenderer header = new HeaderRenderer(jTable7.getTableHeader().getDefaultRenderer()); 
          int hckxf=0;
 
jTable7.setShowHorizontalLines(true);
jTable7.setShowGrid(true);
        jTable7.getColumnModel().getColumns().nextElement().setResizable(false);
        while(hckxf<jTable7.getColumnModel().getColumnCount()){
        jTable7.getColumnModel().getColumn(hckxf).setHeaderRenderer(header);

        if(hckxf==0){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(80);

        }
        if(hckxf==1){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(120);
        }
        if(hckxf==2){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(180);
        }if(hckxf==3){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }if(hckxf==4){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }if(hckxf==5){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }if(hckxf==6){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }if(hckxf==7){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }if(hckxf==8){
        jTable7.getColumnModel().getColumn(hckxf).setMinWidth(0);
        jTable7.getColumnModel().getColumn(hckxf).setMaxWidth(1500);
        jTable7.getColumnModel().getColumn(hckxf).setPreferredWidth(100);
        }
        
        
        hckxf++;

        }
   sortTable(jTable7,jTextField22);  
  

       jTable7.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
          
        this.setHorizontalAlignment(JLabel.LEFT);


       
        this.setFont(new Font("Arial",Font.PLAIN,12));
        
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
        }else if(col==8){
         text = fmt.fromDatabaseWithDashSeperatorBeginningWithYear(value.toString());
         this.setText(text);
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

        
//          SwingWorker<Void,Void> updateInterest = new SwingWorker(){     
//        @Override
//        protected Object doInBackground() throws Exception {
//            
//       loan.updateInterestRepaymentsInterest(Reportx.this); 
//       
//
//  
//        if(loan.getThePenaltyStatus(Reportx.this)==1){
//          loan.updateAmdaPenalty(Reportx.this); 
//               }
//        
//        return null;
//        } };
//        updateInterest.execute();
        
        
        jTabbedPane1.setSelectedIndex(fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "windowChange1"+this.userId+".txt")));
    
        fios.intFileWriterReplace(fios.createFileName("postingEntry", "generalTrn", "budgetEstimateNo.txt"), rdb.getnumberOfBudgetPeriods()+"");
//        dbq.feelMonthYearComberBudgetNew(jComboBox11);
        QuickSummuryPanel.setVisible(false);
         switch(Math.round(Math.random()*20)+""){

                case "1":
                 jButton158.setBackground(jButton174.getBackground());
                    break;
                case "2":
                    jButton158.setBackground(jButton178.getBackground()); 
                    
                    break;
                case "3":
                     jButton158.setBackground(jButton173.getBackground());
                    break;
                case "4":
                    jButton158.setBackground(jButton172.getBackground()); 
                    break;
                case "5":
                    jButton158.setBackground(jButton175.getBackground()); 
                    break;
                case "6":
                  jButton158.setBackground(jButton168.getBackground());   
                    break;
                case "7":
                   jButton158.setBackground(jButton171.getBackground());  
                    break;
                case "8":
                  jButton158.setBackground(jButton177.getBackground());   
                    break;
                case "9":
                   jButton158.setBackground(jButton167.getBackground());  
                    break;  
                case "10":
                    jButton158.setBackground(jButton170.getBackground()); 
                    break;
                case "11":
                     jButton158.setBackground(jButton176.getBackground());
                    break;
                case "12":
                    jButton158.setBackground(jButton166.getBackground()); 
                    break; 
                case "13":
                     jButton158.setBackground(jButton169.getBackground());
                    break;  
                case "14":
                   jButton158.setBackground(jButton165.getBackground());  
                    break; 
                case "15":
                     jButton158.setBackground(jButton164.getBackground());
                    break;
                case "16":
                  jButton158.setBackground(jButton163.getBackground());   
                    break;  
                case "17":
                     jButton158.setBackground(jButton160.getBackground());
                    break;
                case "18":
                     jButton158.setBackground(jButton161.getBackground());
                    break; 
                case "19":
                     jButton158.setBackground(jButton162.getBackground());
                    break;
                case "20":
                  jButton158.setBackground(jButton159.getBackground());   
                    break;  
             
                        
                    

}
      
//        if( fios.intFileReader(fios.createFileName("logOut", "completeShutDown", "rights"+fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserIdOfficer.txt"))+".txt"))==98){
//jButton30.setVisible(false);
//balanceSheetButton.setVisible(false);
//incomeStatementButton.setVisible(false);
//trialBalanceButton.setVisible(false);
//cashFlowButton.setVisible(false);
//balanceSheetButton3.setVisible(false);
//jButton12.setVisible(false);
//jButton17.setVisible(false);
//jButton9.setVisible(false);
//liabilitiesButton1.setVisible(false);
//assetsButto1.setVisible(false);
//trialBalanceButton1.setVisible(false);
//accountStatement1.setVisible(false);
//bogaStatementButton.setVisible(false);
//loanPortFolioButton.setVisible(false);
//loanAppliedButton.setVisible(false);
//loanApprovedButton.setVisible(false);
//cashFlowButton2.setVisible(false);
//jButton8.setVisible(false);
//jButton19.setVisible(false);
//jButton18.setVisible(false);
//assetsButto2.setVisible(false);
//jButton51.setVisible(false);
//balanceSheetButton1.setVisible(false);
//
//}
        
  


if( fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"))==27){
//      trialBalanceButton2
//            FinancialReportsPanel .setVisible(false); 
//         OtherReportsPanel .setVisible(false); 
        loanReportsSubPanel.setVisible(true);
//   financialSupportPanel1.setVisible(false);
      jLabel1.setVisible(false);
    jLabel2.setVisible(false);
    jLabel6.setVisible(false);
//      OtherReportsSubPanel.setVisible(false);
      jLabel11.setVisible(false);
//    BalancesReportSubPanel.setVisible(false);
    loanReportsPanel.setVisible(true);
	jCheckBox2.setSelected(true);
	 loanDisbursementSchedule.setVisible(false);
        jPanel51.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
       BOGAPanel2.setVisible(false);
         jPanel51.setVisible(false);
         jPanel40.setVisible(false);
           BOGAPanel1.setVisible(false);
   loanPortFolioPanel.setVisible(false);
     loansAppliedPanel.setVisible(false);
     loansApprovedPanel.setVisible(false);
     loansDisbursedPanel.setVisible(false);
     loansDue.setVisible(false);
        loansInArrears.setVisible(false);
         loansDueForWriteOff.setVisible(false);
          portfolioAtRisk.setVisible(false);
//          performingLoanPortfolio.setVisible(false);
           loansToStaffMembers.setVisible(false);
//             summuryOnLoans .setVisible(false);
                loansCompletedPanel.setVisible(false);
	   loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(true);
//    BOGAPanel.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
    loansDueForWriteOff.setVisible(false);
    portfolioAtRisk.setVisible(false);
//    performingLoanPortfolio.setVisible(false);
    loansToStaffMembers.setVisible(false);
    summuryOnLoans .setVisible(false);
//    agingReport.setVisible(false);
      
        
        } else if( fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"))==37){
        
          FinancialReportsPanel .setVisible(false); 
         OtherReportsPanel .setVisible(false); 
        loanReportsSubPanel.setVisible(true);
//   financialSupportPanel1.setVisible(false);
      jLabel1.setVisible(false);
    jLabel2.setVisible(false);
    jLabel6.setVisible(false);
//      OtherReportsSubPanel.setVisible(false);
      jLabel11.setVisible(false);
    BalancesReportSubPanel.setVisible(false);
    loanReportsPanel.setVisible(true);
	
	jCheckBox3.setSelected(true);
	   loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(true);
    BOGAPanel.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
    loansDueForWriteOff.setVisible(false);
    portfolioAtRisk.setVisible(false);
//    performingLoanPortfolio.setVisible(false);
    loansToStaffMembers.setVisible(false);
//    summuryOnLoans .setVisible(false);
//    agingReport.setVisible(false); 
        
        
        }
        
        
        if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
  if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){
  bogaStatementButton.setEnabled(true);
  }
 
 }   
    }//GEN-LAST:event_formWindowOpened

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

        jPanel51.setVisible(false);
            loansDisbursedPanel1.setVisible(false); 
             loansDisbursedPanel2 .setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//         agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
            BOGAPanel.setVisible(false);
              BOGAPanel2.setVisible(false);
                BOGAPanel1.setVisible(false);
                    jPanel40.setVisible(false);
            loanPortFolioPanel.setVisible(false);
            loansAppliedPanel.setVisible(false);
            loansApprovedPanel.setVisible(false);
            loansDisbursedPanel.setVisible(false);
            loansDue.setVisible(false);
            loansInArrears.setVisible(false);
            loansDueForWriteOff.setVisible(false);
            portfolioAtRisk.setVisible(false);
//            performingLoanPortfolio.setVisible(false);
            loansToStaffMembers.setVisible(true);
              summuryOnLoans .setVisible(false);
              loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
loanDisbursementSchedule.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
     loansDueForWriteOff.setVisible(false);
      portfolioAtRisk.setVisible(false);
//      performingLoanPortfolio.setVisible(true);
       loansToStaffMembers.setVisible(false);
          loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void loansInArrearsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loansInArrearsButtonActionPerformed
loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//         agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(true);
     loansDueForWriteOff.setVisible(false);
      portfolioAtRisk.setVisible(false);
//      performingLoanPortfolio.setVisible(false);
       loansToStaffMembers.setVisible(false);
         summuryOnLoans .setVisible(false);
          loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_loansInArrearsButtonActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
             loansDisbursedPanel2 .setVisible(false);
                 loansDisbursedPanel1.setVisible(false); 
        jPanel51.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
       BOGAPanel2.setVisible(false);
         jPanel51.setVisible(false);
         jPanel40.setVisible(false);
           BOGAPanel1.setVisible(false);
   loanPortFolioPanel.setVisible(false);
     loansAppliedPanel.setVisible(false);
     loansApprovedPanel.setVisible(false);
     loansDisbursedPanel.setVisible(false);
     loansDue.setVisible(false);
        loansInArrears.setVisible(false);
         loansDueForWriteOff.setVisible(false);
          portfolioAtRisk.setVisible(false);
//          performingLoanPortfolio.setVisible(false);
           loansToStaffMembers.setVisible(false);
             summuryOnLoans .setVisible(false);
                loansCompletedPanel.setVisible(false);
                loansDueForWriteOff.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
                 StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(true);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);      
          loanSavingsToday.setVisible(false); 
           jPanel19.setVisible(false); 
             returnOnInvestmentPanel.setVisible(false); 
              provisionForBadLoansPanel.setVisible(false);  
                   membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(true);
                OverDrawnPostionAccounts.setVisible(false);  
         loanSavingsToday.setVisible(false); 
           jPanel19.setVisible(false);  
             returnOnInvestmentPanel.setVisible(false); 
      provisionForBadLoansPanel.setVisible(false);  
           membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(true);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);    
   loanSavingsToday.setVisible(false); 
    jPanel19.setVisible(false); 
      returnOnInvestmentPanel.setVisible(false); 
 provisionForBadLoansPanel.setVisible(false);  
      membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_jButton9ActionPerformed

    private void liabilitiesButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesButton1ActionPerformed
                 StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(true);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
          loanSavingsToday.setVisible(false); 
           jPanel19.setVisible(false); 
             returnOnInvestmentPanel.setVisible(false); 
              provisionForBadLoansPanel.setVisible(false);  
                   membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_liabilitiesButton1ActionPerformed

    private void assetsButto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsButto1ActionPerformed
                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(true);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);    
         loanSavingsToday.setVisible(false); 
         jPanel19.setVisible(false); 
           returnOnInvestmentPanel.setVisible(false); 
            provisionForBadLoansPanel.setVisible(false);  
                 membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
        
       // TODO add your handling code here:
    }//GEN-LAST:event_assetsButto1ActionPerformed

    private void cashFlowButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton1ActionPerformed
               StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(true);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false); 
          jPanel19.setVisible(false); 
          returnOnInvestmentPanel.setVisible(false); 
    provisionForBadLoansPanel.setVisible(false);  
     membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_cashFlowButton1ActionPerformed

    private void incomeStatementButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeStatementButton1ActionPerformed
                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(true);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);    
          loanSavingsToday.setVisible(false);  
           loanSavingsToday.setVisible(false); 
            jPanel19.setVisible(false); 
              returnOnInvestmentPanel.setVisible(false); 
               provisionForBadLoansPanel.setVisible(false);  
                    membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_incomeStatementButton1ActionPerformed

    private void balanceSheetButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton1ActionPerformed
                 StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(true);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);  
                loanSavingsToday.setVisible(false); 
          jPanel19.setVisible(false); 
            returnOnInvestmentPanel.setVisible(false); 
             provisionForBadLoansPanel.setVisible(false);  
             
                  membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_balanceSheetButton1ActionPerformed

    private void trialBalanceButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trialBalanceButton1ActionPerformed

                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(true);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
             
                OverDrawnPostionAccounts.setVisible(false);
         loanSavingsToday.setVisible(false); 
         jPanel19.setVisible(false); 
              returnOnInvestmentPanel.setVisible(false);     
               provisionForBadLoansPanel.setVisible(false);  
                    membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_trialBalanceButton1ActionPerformed

    private void accountStatement1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountStatement1ActionPerformed

                StaffMembersPanel.setVisible(true);
                
                
                LoginsPan.setVisible(false);
                
                
                CustomersDemographyPanel.setVisible(false);
                
                
                customerByBalancePanel.setVisible(false);
                
                customerBySharesPanel.setVisible(false);
                
                customerByContactDetailsPanel.setVisible(false);
                
                AssetAccounts.setVisible(false);
                
                LiabilitiesAccounts.setVisible(false);
                
                EquityAccounts.setVisible(false);
                
                IncomeAccounts.setVisible(false);
                
                ExpensesAccounts.setVisible(false);
                
                
                OverDrawnPostionAccounts.setVisible(false);
     
              loanSavingsToday.setVisible(false); 
              
               jPanel19.setVisible(false); 
                 returnOnInvestmentPanel.setVisible(false);
                 
                  provisionForBadLoansPanel.setVisible(false);  
                  
                       membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_accountStatement1ActionPerformed

    private void jLabel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MousePressed
QuickSummuryPanel.setVisible(false); 
        FinancialReportsPanel.setVisible(false); 
        OtherReportsPanel .setVisible(true);        
        OtherReportsSubPanel.setVisible(true);
     loanReportsSubPanel.setVisible(false);
//   financialSupportPanel1.setVisible(false);
     jLabel1.setVisible(false);
    jLabel2.setVisible(false);
    jLabel6.setVisible(false);
    jLabel11.setVisible(false);
    BalancesReportSubPanel.setVisible(false);
    jLabel11.setVisible(true);
    loanReportsPanel.setVisible(false);
     Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());
    }//GEN-LAST:event_jLabel6MousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       int i=1;
     while(i<=5){
     
     JOptionPane.showMessageDialog(this, "SYSTEM LOG OUT ERROR!!!!!!!!!");}
    }//GEN-LAST:event_formWindowClosing

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed

        jLabel10.setText("\t\t"+"THE CHART OF ACCOUNTS");
           quary. populateTree(jTree3,"Debits");
           quary. populateTree(jTree2,"Credits");
          generalLedgerPanel2.setVisible(true);
         GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
        pAndLPanel.setVisible(false);
        trialBalancePanel1.setVisible(false);
         
    }//GEN-LAST:event_jButton30ActionPerformed

    private void balanceSheetButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton3ActionPerformed
 
                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(true);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false); 
                   returnOnInvestmentPanel.setVisible(false); 
                    provisionForBadLoansPanel.setVisible(false);  
                         membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_balanceSheetButton3ActionPerformed

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
 Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());
        loanReportsPanel.setVisible(false);
        
//       financialSupportPanel1.setVisible(false);
        OtherReportsPanel .setVisible(false); 
        jLabel2.setVisible(true); 
//        BalancesReportSubPanel.setVisible(true); 
         jLabel11.setVisible(false);
        loanReportsSubPanel.setVisible(false);
//     OtherReportsSubPanel.setVisible(false);
    jLabel1.setVisible(false);
    jLabel6.setVisible(false);
    FinancialReportsPanel.setVisible(false);
   QuickSummuryPanel.setVisible(false);  
    }//GEN-LAST:event_jLabel11MousePressed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void liabilitiesButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liabilitiesButton3ActionPerformed

    private void assetsButto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsButto3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assetsButto3ActionPerformed

    private void cashFlowButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashFlowButton3ActionPerformed

    private void incomeStatementButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeStatementButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_incomeStatementButton3ActionPerformed

    private void trialBalanceButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trialBalanceButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trialBalanceButton3ActionPerformed

    private void balanceSheetButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balanceSheetButton4ActionPerformed

    private void balanceSheetButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balanceSheetButton5ActionPerformed

    private void accountStatement3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountStatement3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountStatement3ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
 StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(true);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);    
          loanSavingsToday.setVisible(false); 
           jPanel19.setVisible(false); 
  returnOnInvestmentPanel.setVisible(false); 
   provisionForBadLoansPanel.setVisible(false);  
        membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        if(other.createStaffMembers("staff_members")){
        
       generateFile("staff_members"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      List empty =new ArrayList();
        ReportView f1 = new ReportView(userId,"DemographicDetails",empty);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed


            
               List budgetEstimates= new ArrayList();
               
         if(!jComboBox11.getSelectedItem().toString().equalsIgnoreCase("Select Period")){
             
             switch(jComboBox11.getSelectedItem().toString()){
                 case "Entire Period":
            
                
         budgetEstimates.add("SDate");  
          budgetEstimates.add("EDate");
          budgetEstimates.add("Entire Period");
        
          if(dbq.isBudgetNotEmpty()){
          
              
              
          
            JOptionPane.showMessageDialog(this, "Wait while the budget estimates Report is beeing created!!!");
            
                SwingWorker<budgetEstimatesModel,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected budgetEstimatesModel doInBackground() throws Exception {
                    
                return  otherLoans.budgetEstimates(budgetEstimates,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                budgetResult = (budgetEstimatesModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                budgetEstimates.add(budgetResult);
                
         ReportView f1 = new ReportView(userId,"budgetEstimates",budgetEstimates);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
              }else{
              
              
              
              
                JOptionPane.showMessageDialog(this, "The budget Estimate cannot be created without the budget!!");
              }
          

                     
                     break;
                     case "Date Range":
                         
         int startYear=parseInt(fmt.yearOnlyFromDateString(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField60.getText())));
   int endYear=parseInt(fmt.yearOnlyFromDateString(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField59.getText())));
        int startMonth=parseInt(fmt.monthOnlyFromDateString(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField60.getText())));
        int endMonth=parseInt(fmt.monthOnlyFromDateString(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField59.getText())));                 
                         
                         
                      if (jTextField60.getText().equalsIgnoreCase("")||jTextField59.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if ((startYear<fios.intFileReader(fios.createFileName("accountManagement", "masterBalances", "startingEffectiveYear.txt")))) {
         JOptionPane.showMessageDialog(this, "The selected date should be with in the budgeted period");
         return;
        } else{
                
          budgetEstimates.add(jTextField60.getText().trim());  
          budgetEstimates.add(jTextField59.getText().trim());
          budgetEstimates.add("Date Range");
      
        
          if(dbq.isBudgetNotEmpty()){
          
              
              
          
            JOptionPane.showMessageDialog(this, "Wait while the budget estimates Report is beeing created!!!");
            
                SwingWorker<budgetEstimatesModel,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected budgetEstimatesModel doInBackground() throws Exception {
                    
                return  otherLoans.budgetEstimates(budgetEstimates,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                budgetResult = (budgetEstimatesModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                budgetEstimates.add(budgetResult);
                
         ReportView f1 = new ReportView(userId,"accountabalances",budgetEstimates);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
              }else{
              
              
              
              
                JOptionPane.showMessageDialog(this, "The budget Estimate cannot be created without the budget!!");
              }
          }
   
                         
                         
                         break;
             }
             
        
        }else{
          
        
         JOptionPane.showMessageDialog(this, "Please first select the period for the budget estimate!!"); 
          }
        
       
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
               
          if(other.createCustomerBal("customer_balance")){
        
       generateFile( "customer_balance"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }
         
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
             
            if(other.createCustomerShares("customer_Shares")){
        
      generateFile("customer_Shares");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }
        
        
        
               
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
            if(other.createCustomerContact("customer_conacts")){
        
      generateFile("customer_conacts"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }    
        
        
        
                
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
     
            if(other.createAssets("assets")){
        
      generateFile( "assets");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }   
        
      
        
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
   
            if(other.createLiabilities("liabilities")){
        
        generateFile("liabilities");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        }   
        
        
      
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
     
              if(other.createEquity("equity")){
        
       generateFile("equity");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        } 
        
       
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
     
               if(  other.createRevenue("revenue")){
        
       generateFile("revenue"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        
        } 
        
      
        
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
     
  if(  other.createExpense("expense_Report")){
 generateFile("expense_Report");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 

    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
       
       if( other.createOverDrawn("overDrawn_accounts")){
 generateFile("overDrawn_accounts");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }   
 
  


    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed

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
            LoanApprovals fx = new LoanApprovals (userId);
        fx.setVisible(true);
        Dimension screenx = Toolkit.getDefaultToolkit().getScreenSize();
        fx.setSize(screenx.getSize());
        fx.pack();
        this.dispose();
                break;
                case "Cashier":
       PostingEntryWindow fg = new  PostingEntryWindow(userId,amortDetails);
        fg.setVisible(true);
        Dimension screeng = Toolkit.getDefaultToolkit().getScreenSize();
        fg.setSize(screeng.getSize());
        fg.pack();
        this.dispose();
                break;
                  case "Director":
       Login fgb = new  Login();
        fgb.setVisible(true);
        Dimension screengb = Toolkit.getDefaultToolkit().getScreenSize();
        fgb.setSize(screengb.getSize());
        fgb.pack();
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
        
     
    }//GEN-LAST:event_jButton58ActionPerformed

    private void debitAccountField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debitAccountField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField2ActionPerformed

    private void debitAccountField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField2KeyPressed

    private void debitAccountField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField2KeyReleased

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
         if(debitAccountField2.getValue()==null){
         JOptionPane.showMessageDialog(this, "Please input a Valid Account Number");return;
         }else if(jCheckBox2.isSelected()){
             
             if(loan.loanExists(fmt.formatAccountWithSeperators(debitAccountField2.getValue().toString()))==false){JOptionPane.showMessageDialog(this, "Loan does not exist");return;}else{
       
                 if(lstat.createRunningLoanStatement(fmt.formatAccountWithSeperators(debitAccountField2.getValue().toString()),"running_loanStatement")){
     
        generateFile("running_loanStatement");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
             
             
             
    
         }}else if(jCheckBox3.isSelected()){
    if(lstat.createRunningLoanStatementclosed(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanid.txt")),"running_loanStatement")){
     
        generateFile("running_loanStatement");
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
         }
         
    }//GEN-LAST:event_jButton48ActionPerformed

    private void bogaStatementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bogaStatementButtonActionPerformed

         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
             loansDisbursedPanel2 .setVisible(false);
                 loansDisbursedPanel1.setVisible(false); 
        jPanel51.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(true);
       BOGAPanel2.setVisible(false);
         jPanel51.setVisible(false);
         jPanel40.setVisible(false);
           BOGAPanel1.setVisible(false);
   loanPortFolioPanel.setVisible(false);
     loansAppliedPanel.setVisible(false);
     loansApprovedPanel.setVisible(false);
     loansDisbursedPanel.setVisible(false);
     loansDue.setVisible(false);
        loansInArrears.setVisible(false);
         loansDueForWriteOff.setVisible(false);
          portfolioAtRisk.setVisible(false);
//          performingLoanPortfolio.setVisible(false);
           loansToStaffMembers.setVisible(false);
             summuryOnLoans .setVisible(false);
                loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_bogaStatementButtonActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
          
       if(boga.createOfficersPortFolio("portfolio_byofficers")){
           
     generateFile("portfolio_byofficers"); 
     
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
        
        
    
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
   
        if(jCheckBox2.isSelected()){
        
            jCheckBox3.setSelected(false);
            jPanel5.setVisible(false);
            BOGAPanel1.setVisible(false);
            debitAccountField2.setValue(null);
   BOGAPanel2.setVisible(false);
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"27");        
          Reportx f = new Reportx(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose();   
        } else if(!jCheckBox2.isSelected()){
       Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"2");  
        jCheckBox2.setSelected(false);
          debitAccountField2.setValue(null);
          jPanel5.setVisible(false);
           BOGAPanel1.setVisible(false);
            Reportx f = new Reportx(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 
        }
        
        
        
        
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
     BOGAPanel1.setVisible(false);
             BOGAPanel2.setVisible(false);
        if(jCheckBox3.isSelected()){
           BOGAPanel2.setVisible(false);
            jCheckBox2.setSelected(false);
             BOGAPanel1.setVisible(false);
         Integer x=37;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());
        Reportx f = new Reportx(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 
     } else if(!jCheckBox3.isSelected()){
          BOGAPanel1.setVisible(false);
             BOGAPanel2.setVisible(false);
         Integer x=2;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),x.toString());
        jCheckBox3.setSelected(false);
        
        }
     
     
     BOGAPanel1.setVisible(false);
             BOGAPanel2.setVisible(false);
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void JBTrnSqNo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTrnSqNo3ActionPerformed
       
         
        if(jCheckBox2.isSelected()){
        
        dbq.fillMeWithAllAccountsBx(jTable1,this);
              sortTable(jTable1,jTextField21);
          
      HeaderRenderer header = new HeaderRenderer(jTable1.getTableHeader().getDefaultRenderer());   
        
        int h1=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable1.getColumnModel().getColumnCount()){
        jTable1.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(50);

        }
       
       if(h1==1){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(350);

        }
       
       if(h1==2){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(150);

        }
       if(h1==3){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(130);

        }
        if(h1==4){
        jTable1.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable1.getColumnModel().getColumn(h1).setPreferredWidth(200);

        }
        h1++;

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

        if(col==4){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton158.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton157.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
        
        
        loanStatementPanel.setVisible(false);
       Integer x=27;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanArrearsWindow.txt"),x.toString());
          tablePanel.setVisible(true);
          
    }else if(jCheckBox3.isSelected()){
    
           dbq.fillMeWithAllAccountsBClosed(jTable1,this);
                 sortTable(jTable1,jTextField21); 
        loanStatementPanel.setVisible(false);
       Integer x=27;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanArrearsWindow.txt"),x.toString());
          tablePanel.setVisible(true);
   
    
    }else if(!jCheckBox2.isSelected()||!jCheckBox3.isSelected()){
    
    JOptionPane.showMessageDialog(this, "Please select the category of loan report to run");
    }
    }//GEN-LAST:event_JBTrnSqNo3ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
       
               email f = new email (userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
     
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        if(otherLoans.createLoanPortfolioReport("loan_portfolio")){
     generateFile("loan_portfolio"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed

    if (jTextField16.getText().equalsIgnoreCase("")||jTextField15.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField16.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField15.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createLoanApplied(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField16.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField15 .getText().trim()),"loan_appliedReport")){
  
     generateFile("loan_appliedReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }



//           if(otherLoans.createLoanAppliedReport("loan_applied")){
//     generateFile("loan_applied"); 
//        } else{
//        
//        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
//       
//        } 
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed

        SelectedAccountDetails theNames=new SelectedAccountDetails(this.userId,"LStatmentView","hh");

        
       theNames.setVisible(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        theNames.setSize(screen.getSize());
        
        theNames.pack();    
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed

JOptionPane.showMessageDialog(this, theMlAccount);

List mlInterest= new ArrayList();
        
       if (theMlAccount.equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please first select the borrower from the table");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
        
         mlInterest.add(theMlAccount);  
         
        
          
            JOptionPane.showMessageDialog(this, "Wait while the money Lender's loan report is beeing created!!!");
                SwingWorker<ListDataModelPortfolioReport1,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModelPortfolioReport1 doInBackground() throws Exception {
                return  other.moneyLenderIntest(mlInterest,comp);
                }

                @Override
                protected void done() {
                try {
                porfolio1 = (ListDataModelPortfolioReport1) get();
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                mlInterest.add(porfolio1);
                theMlAccount="";
         ReportView f1 = new ReportView(userId,"moneyLendersInterest",mlInterest);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }     
          
        
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed

        
    if (jTextField20.getText().equalsIgnoreCase("")||jTextField19.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField20.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField19.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createLoanDisbursed(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField20.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField19 .getText().trim()),"loan_disbursedReport")){
  
     generateFile("loan_disbursedReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
        
        
        
//        if(otherLoans.createLoansDisbursedReport("loan_disbursed")){
//     generateFile("loan_disbursed"); 
//        } else{
//        
//        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
//       
//        } 
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
  dbq.fillMeWithAllAccountsD(jTable1,this);
        sortTable(jTable1,jTextField21); 
        loansInArrears.setVisible(false);
    
          tablePanel.setVisible(true);  
          
           Integer x=26;
       fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanArrearsWindow.txt"),x.toString());
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
//       
//               
//       if(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).equalsIgnoreCase("1")){
//       JOptionPane.showMessageDialog(this, "Please first set the threshold for a loan writeoff");
//       }else{ 
//           if (jTextField7.getText().equalsIgnoreCase("")||jTextField4.getText().equalsIgnoreCase("")) {
//        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
//        return;
//        }else if (fmt.convertTdate(jTextField7.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField4.getText().trim()).after(fmt.getTodayDate())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
//         return;
//        }
//            
//     if(otherLoans.createLoanDueWriteOff(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField7.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField4 .getText().trim()),"loanDue_WriteOff")){
//  
//     generateFile("loanDue_WriteOff"); 
//        } else{
//        
//        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
//       
//        }
//       } 
//        
//        
        
        
//       if(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).equalsIgnoreCase("1")){
//       JOptionPane.showMessageDialog(this, "Please first set the threshold for a loan writeoff");
//       }else{ 
//        
//        if(otherLoans.createLoanDueWriteOffReport("loan_dueWriteOff")){
//     generateFile("loan_dueWriteOff"); 
//        } else{
//        
//        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
//       
//        } 
//       }
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
          if (jTextField11.getText().equalsIgnoreCase("")||jTextField6.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField11.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField6.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createPortfolioAtRisk(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField11.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField6 .getText().trim()),"portfolio_atRisk")){
  
     generateFile("portfolio_atRisk"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
    BOGAPanel1.setVisible(false);
        loansDisbursedPanel1.setVisible(false); 
         loansDisbursedPanel2 .setVisible(false);
         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
    jPanel51.setVisible(false);
      BOGAPanel2.setVisible(false);
          jPanel40.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
     loansDueForWriteOff.setVisible(false);
      portfolioAtRisk.setVisible(false);
//      performingLoanPortfolio.setVisible(false);
       loansToStaffMembers.setVisible(false);
         summuryOnLoans .setVisible(false);
          loansCompletedPanel.setVisible(true);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        if(otherLoans.createLoansToStaffReport("loan_toStaff")){
     generateFile("loan_toStaff"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
          
        if (jTextField24.getText().equalsIgnoreCase("")||jTextField25.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField24.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField25.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createLoanArrearsReport(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField24.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField25.getText().trim()),"loan_arrears")){
  
     generateFile("loan_arrears"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
      
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
if(debitAccountField3.getValue()==null){
         JOptionPane.showMessageDialog(this, "Please input a Valid Account Number");return;
         }else{
        
        if(otherLoans.createLoanArrearsReportDetails("loan_arrearsDetails",fmt.formatAccountWithSeperators(debitAccountField3.getValue().toString()),dbq.AccountName(fmt.formatAccountWithSeperators(debitAccountField3.getValue().toString())))){
     generateFile("loan_arrearsDetails"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } }
    }//GEN-LAST:event_jButton68ActionPerformed

    private void debitAccountField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debitAccountField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField3ActionPerformed

    private void debitAccountField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField3KeyPressed
        // TODO add your handling code here: 
    }//GEN-LAST:event_debitAccountField3KeyPressed

    private void debitAccountField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField3KeyReleased

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
     jButton67.setEnabled(true);
     debitAccountField3.setValue(null);
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
    BOGAPanel1.setVisible(false);
        loansDisbursedPanel1.setVisible(false); 
         loansDisbursedPanel2 .setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//         agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
        jPanel51.setVisible(false);
            jPanel40.setVisible(false);
    BOGAPanel.setVisible(false);
      BOGAPanel2.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
     loansDueForWriteOff.setVisible(false);
      portfolioAtRisk.setVisible(false);
//      performingLoanPortfolio.setVisible(false);
       loansToStaffMembers.setVisible(false);
         summuryOnLoans .setVisible(true);
            loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void assetsButto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsButto2ActionPerformed
jPanel51.setVisible(false);
    loansDisbursedPanel1.setVisible(false); 
     loansDisbursedPanel2 .setVisible(false);
        BOGAPanel1.setVisible(false);
         BOGAPanel2.setVisible(false);
             jPanel51.setVisible(true);
                 jPanel40.setVisible(false);
         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loanStatementPanel.setVisible(false);
//         agingReport.setVisible(false);
        BOGAPanel.setVisible(false);
        loanPortFolioPanel.setVisible(false);
        loansAppliedPanel.setVisible(false);
        loansApprovedPanel.setVisible(false);
        loansDisbursedPanel.setVisible(false);
        loansDue.setVisible(true);
        loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
        portfolioAtRisk.setVisible(false);
//        performingLoanPortfolio.setVisible(false);
        loansToStaffMembers.setVisible(false);
        summuryOnLoans .setVisible(false);
           loansDue1   .setVisible(false);
              loansCompletedPanel.setVisible(false);
     
    }//GEN-LAST:event_assetsButto2ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed

         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(true);
//             agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
        BOGAPanel.setVisible(false);
        loanPortFolioPanel.setVisible(false);
        loansAppliedPanel.setVisible(false);
        loansApprovedPanel.setVisible(false);
        loansDisbursedPanel.setVisible(false);
        loansDue.setVisible(false);
        loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
        portfolioAtRisk.setVisible(false);
//        performingLoanPortfolio.setVisible(false);
        loansToStaffMembers.setVisible(false);
//        summuryOnLoans .setVisible(false);// TODO add your handling code here:
         loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
       if(otherLoans.createLoansDueReport1("loan_due1")){
     generateFile("loan_due1"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
    BOGAPanel1.setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
         loanDisbursementSchedule.setVisible(false);
//        agingReport.setVisible(false);
          BOGAPanel2.setVisible(false);
                 loansDue1   .setVisible(false);
                 jPanel51.setVisible(false);
                     jPanel40.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
//            summuryOnLoans .setVisible(false);
             loansCompletedPanel.setVisible(false);
//              dbq.fillmWithUserNames(jComboBox2);
//                    jComboBox2.setSelectedIndex(0);
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton79ActionPerformed
 if (jTextField5.getText().equalsIgnoreCase("")||jTextField3.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField5.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField3.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createLoanInAreearsReport(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField5.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField3.getText().trim()),"loans_in_arrears")){
  
     generateFile("loans_in_arrears"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
    }//GEN-LAST:event_jButton79ActionPerformed

    private void jButton80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton80ActionPerformed
 Integer z2=480;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton80ActionPerformed

    private void jButton81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton81ActionPerformed
   Integer z1=500;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton81ActionPerformed

    private void jButton82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton82ActionPerformed
loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(true);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
//            summuryOnLoans .setVisible(false);
          loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton82ActionPerformed

    private void jButton83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton83ActionPerformed

         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(true);
          loansToStaffMembers.setVisible(false);
            summuryOnLoans .setVisible(false);
           loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_jButton83ActionPerformed

    private void jButton84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton84ActionPerformed
          Integer z2=470;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton84ActionPerformed

    private void jButton85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton85ActionPerformed
 Integer z2=450;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton85ActionPerformed

    private void jButton86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton86ActionPerformed
    Integer z1=520;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton86ActionPerformed

    private void jButton88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton88ActionPerformed
Integer z2=410;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton88ActionPerformed

    private void jButton90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton90ActionPerformed
       Integer z2=440;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton90ActionPerformed

    private void jButton92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton92ActionPerformed
 Integer z2=400;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton92ActionPerformed

    private void jButton94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton94ActionPerformed
       Integer z1=490;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton94ActionPerformed

    private void jButton95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton95ActionPerformed
         Integer z1=510;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton95ActionPerformed

    private void jButton99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton99ActionPerformed
   Integer z1=560;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton99ActionPerformed

    private void jButton100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton100ActionPerformed
         Integer z1=550;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton100ActionPerformed

    private void jButton93ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton93ActionPerformed

        
    if (jTextField12.getText().equalsIgnoreCase("")||jTextField14.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField12.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField14.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createLoanCompleted(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField12.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField14 .getText().trim()),"loan_completedReport")){
  
     generateFile("loan_completedReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
    }//GEN-LAST:event_jButton93ActionPerformed

    private void jButton101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton101ActionPerformed
    Integer z1=580;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton101ActionPerformed

    private void jButton104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton104ActionPerformed
         Integer z1=570;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton104ActionPerformed

    private void jButton105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton105ActionPerformed
     if (jTextField13.getText().equalsIgnoreCase("")||jTextField9.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField13.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField9.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createGrossPortfolio(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField13.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField9.getText().trim()),"gross_portfolio")){
  
     generateFile("gross_portfolio"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
        
        
       
       
    }//GEN-LAST:event_jButton105ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton53ActionPerformed

    private void balanceSheetButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton6ActionPerformed
      
                StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(true);  
                   returnOnInvestmentPanel.setVisible(false); 
                    provisionForBadLoansPanel.setVisible(false);  
                         membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
//         returnOnInvestmentPanel
    }//GEN-LAST:event_balanceSheetButton6ActionPerformed

    private void jButton96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton96ActionPerformed
      if(other.createloanSavings("loanInstalment_savings")){
        
       generateFile("loanInstalment_savings");
       
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }
    }//GEN-LAST:event_jButton96ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed



        int today=cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH);
        
       int select1=parseInt(totalLoanSavingsCombo1.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo1.getSelectedItem().toString().split("\\s")[0]);
        
        int select2=parseInt(totalLoanSavingsCombo2.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo2.getSelectedItem().toString().split("\\s")[0]);
        
        if(select2>today) {
         JOptionPane.showMessageDialog(this, "The selected Month should not be greater than the System Month");
         return;
        }else{
        List TotalLoanSavingsDetails=new ArrayList();

        TotalLoanSavingsDetails.add(select1);
        
        TotalLoanSavingsDetails.add(select2);
        
        TotalLoanSavingsDetails.add(totalLoanSavingsCombo1.getSelectedItem().toString());
        
        TotalLoanSavingsDetails.add(totalLoanSavingsCombo2.getSelectedItem().toString()); 
        
        ReportView f1 = new ReportView(userId,"TotalLoanSavingsReport",TotalLoanSavingsDetails);
        
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
       
         
        }
       
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton107ActionPerformed
        if(other.createCustomerDemograph("customer_demography")){
        
                
                
       generateFile("customer_demography");
       
       
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }
    }//GEN-LAST:event_jButton107ActionPerformed

    private void jButton109ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton109ActionPerformed
        jComboBox5.setVisible(true);
           jComboBox6.setVisible(false);
    }//GEN-LAST:event_jButton109ActionPerformed

    private void jButton110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton110ActionPerformed
 
        int today=cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH);
        
       int select1=parseInt(totalLoanSavingsCombo1.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo1.getSelectedItem().toString().split("\\s")[0]);
        
        int select2=parseInt(totalLoanSavingsCombo2.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo2.getSelectedItem().toString().split("\\s")[0]);
        
        if(select2>today) {
            
         JOptionPane.showMessageDialog(this, "The selected Month should not be greater than the System Month");
         return;
        }else{
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1.txt"), select1+"");
         fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2.txt"), select2+"");
           fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1A.txt"), totalLoanSavingsCombo1.getSelectedItem().toString());
         fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2A.txt"), totalLoanSavingsCombo2.getSelectedItem().toString());
            
                 if(other.createLoanSavingsReport(totalLoanSavingsCombo1.getSelectedItem().toString(),totalLoanSavingsCombo2.getSelectedItem().toString(),select1,select2,"TotalSavingLoanReport")){
        
       generateFile("TotalSavingLoanReport");
       
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }
       
         
        }
        
    }//GEN-LAST:event_jButton110ActionPerformed

    private void jButton111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton111ActionPerformed
 int today=cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH);
        
       int select1=parseInt(totalLoanSavingsCombo1.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo1.getSelectedItem().toString().split("\\s")[0]);
        
        int select2=parseInt(totalLoanSavingsCombo2.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo2.getSelectedItem().toString().split("\\s")[0]);
        
        if(select2>today) {
            
         JOptionPane.showMessageDialog(this, "The selected Month should not be greater than the System Month");
         return;
        }else{
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1.txt"), select1+"");
         fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2.txt"), select2+"");
           fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "dateCarrier1A.txt"), totalLoanSavingsCombo1.getSelectedItem().toString());
         fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "dateCarrier2A.txt"), totalLoanSavingsCombo2.getSelectedItem().toString());
        
        if(writeExcel.createSheet4TheBook( loanSaveShare.createLoanSaveExcel(), "LoanSavingsReport", "loan_Savings")){
        
       generateFileExcel("loan_Savings");
       
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened Excel File");

        }}
    }//GEN-LAST:event_jButton111ActionPerformed

    private void jButton112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton112ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton112ActionPerformed

    private void jButton113ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton113ActionPerformed
         jComboBox5.setVisible(false);
           jComboBox6.setVisible(true);
    }//GEN-LAST:event_jButton113ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
      switch(jComboBox4.getSelectedItem().toString()){

        case "Daily Individual ROI":

        totalLoanShares1.setVisible(true);
            totalLoanShares2.setVisible(false);
        totalLoanShares3.setVisible(false);
        totalLoanShares4.setVisible(false);
//        totalLoanShares5.setVisible(false);

        break;
        case "Daily Group ROI":
        totalLoanShares1.setVisible(false);
        totalLoanShares2.setVisible(true);
        totalLoanShares3.setVisible(false);
        totalLoanShares4.setVisible(false);
//        totalLoanShares5.setVisible(false);

        break;
        case "Monthly Individual ROI":
        totalLoanShares1.setVisible(false);
        totalLoanShares2.setVisible(false);
        totalLoanShares3.setVisible(true);
        totalLoanShares4.setVisible(false);
//        totalLoanShares5.setVisible(false);
        break;
        case "Monthly Group ROI":
        totalLoanShares1.setVisible(false);
        totalLoanShares2.setVisible(false);
        totalLoanShares3.setVisible(false);
        totalLoanShares4.setVisible(true);
//        totalLoanShares5.setVisible(false);
        break; 
        case "General Montly Group RIO ":
        totalLoanShares1.setVisible(false);
        totalLoanShares2.setVisible(false);
        totalLoanShares3.setVisible(false);
        totalLoanShares4.setVisible(false);
//        totalLoanShares5.setVisible(true);
        break; 

      
      }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton120ActionPerformed
    
        Object[] optionsSGS = {"Continue",  "Cancel"};
        int nSGS = JOptionPane.showOptionDialog(this,  "Work may not be saved!!!\n"+"Do you want to  continue ?",
        "SAVE PLEASE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,optionsSGS, optionsSGS[0]);
        if(nSGS==JOptionPane.YES_OPTION){
        
            Reportx f1 = new Reportx(userId);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
           this.dispose();  
        }
        
        else if (nSGS==JOptionPane.NO_OPTION){ this.setVisible(true);} 
        
    }//GEN-LAST:event_jButton120ActionPerformed

    private void jButton121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton121ActionPerformed
        Integer z2=670;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton121ActionPerformed

    private void jButton122ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton122ActionPerformed
      Integer z2=680;
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());   
        
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton122ActionPerformed

    private void balanceSheetButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton7ActionPerformed
       StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(true);
                   provisionForBadLoansPanel.setVisible(false);  
                        membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_balanceSheetButton7ActionPerformed

    private void jButton129ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton129ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton129ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
          switch(jComboBox6.getSelectedItem().toString()){
         case "Loan Payments":
        totalLoanShares.setVisible(false);
        savingsContributions.setVisible(false);
              dbq.feelMonthYearComber(totalLoanSavingsCombo5);
               dbq.feelMonthYearComber(totalLoanSavingsCombo6);
       loanPayments.setVisible(true);
       break;
          case "Savings":
          totalLoanShares.setVisible(false);
        savingsContributions.setVisible(true);
              dbq.feelMonthYearComber(totalLoanSavingsCombo4);
               dbq.feelMonthYearComber(totalLoanSavingsCombo3);
       loanPayments.setVisible(false);
       
       break;
       

          case "Total Loan & Savings":
              
              totalLoanShares.setVisible(true);
              dbq.feelMonthYearComber(totalLoanSavingsCombo1);
               dbq.feelMonthYearComber(totalLoanSavingsCombo2);
                 loanPayments.setVisible(false);
                  savingsContributions.setVisible(false);
              break;
   case "Total Loan & Shares":
       
       
       break;
   case "Total Loan,Savings & Shares":
       
       break;
   case "Total Princimpal,Interest, Savings":
       
       break; 
   case "Total Princimpal,Interest,Savings &  Shares":
       
       break; 
   case "Total SavingsWithdraw & SharesWithdraw":
       
       
       break;
   case "Total SavingsWithdraw & Savings":
       
       
       break;
      
      }
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jButton130ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton130ActionPerformed
     
        List rioStore= new ArrayList();
        
       if (jTextField28.getText().equalsIgnoreCase("")||jTextField38.getText().equalsIgnoreCase("")||jTextField29.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and Member before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
         rioStore.add(theAccount); 
         rioStore.add(jTextField38.getText().trim());  
          rioStore.add(jTextField28.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while projected payments report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.returnOnInvestment(rioStore);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                rioStore.add(result);
                
         ReportView f1 = new ReportView(userId,"roInvestment",rioStore);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }      
    }//GEN-LAST:event_jButton130ActionPerformed

    private void jButton131ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton131ActionPerformed
    
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"687");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton131ActionPerformed

    private void jButton132ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton132ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton132ActionPerformed

    private void jButton133ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton133ActionPerformed
        
    if (jTextField29.getText().equalsIgnoreCase("")||jTextField38.getText().equalsIgnoreCase("")||jTextField28.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField38.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField28.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createReturnOnInvestmentDailyIndividual(theAccount,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField38.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField28.getText().trim()),"return_DailyIndividual")){
  
     generateFile("return_DailyIndividual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
     
     
    }//GEN-LAST:event_jButton133ActionPerformed

    private void jButton146ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton146ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"688");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton146ActionPerformed

    private void jButton134ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton134ActionPerformed
        
        List rioStoreg= new ArrayList();
        
       if (jTextField31.getText().equalsIgnoreCase("")||jTextField30.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
       
         rioStoreg.add(jTextField31.getText().trim());  
          rioStoreg.add(jTextField30.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while general return on investment report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.generalReturnOnInvestment(rioStoreg);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                rioStoreg.add(result);
                
         ReportView f1 = new ReportView(userId,"roInvestmentDG",rioStoreg);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }     
    }//GEN-LAST:event_jButton134ActionPerformed

    private void jButton135ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton135ActionPerformed
     fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"689");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton135ActionPerformed

    private void jButton136ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton136ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton136ActionPerformed

    private void jButton137ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton137ActionPerformed
          
    if (jTextField31.getText().equalsIgnoreCase("")||jTextField30.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField31.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField30.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.groupDailyReturnOnInvestemnt(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField31.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField30 .getText().trim()),"return_dailyGroupReport")){
  
     generateFile("return_dailyGroupReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
    }//GEN-LAST:event_jButton137ActionPerformed

    private void jButton147ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton147ActionPerformed
       fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"690");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton147ActionPerformed

    private void jButton138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton138ActionPerformed
           
        List rioStoregMontly= new ArrayList();
        
       if (jTextField33.getText().equalsIgnoreCase("")||jTextField32.getText().equalsIgnoreCase("")||jTextField42.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
         
           rioStoregMontly.add(theAccount);  
         rioStoregMontly.add(jTextField33.getText().trim());  
          rioStoregMontly.add(jTextField32.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while general return on investment report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    
                return  otherLoans.individualMontlyROI(rioStoregMontly);
                
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                rioStoregMontly.add(result);
                
         ReportView f1 = new ReportView(userId,"individualMontlyReturnOnInvestment",rioStoregMontly);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }   
    }//GEN-LAST:event_jButton138ActionPerformed

    private void jButton139ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton139ActionPerformed
     fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"691");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton139ActionPerformed

    private void jButton140ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton140ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton140ActionPerformed

    private void jButton141ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton141ActionPerformed
        
    if (jTextField42.getText().equalsIgnoreCase("")||jTextField33.getText().equalsIgnoreCase("")||jTextField32.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField33.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField32.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.individualMontlyROIpdf(theAccount,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField33.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField32 .getText().trim()),"return_MonthlyIndividualReport")){
  
     generateFile("return_MonthlyIndividualReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
    }//GEN-LAST:event_jButton141ActionPerformed

    private void jButton148ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton148ActionPerformed
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"692");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton148ActionPerformed

    private void jButton142ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton142ActionPerformed
                
        List groupMontlyROI= new ArrayList();
        
       if (jTextField35.getText().equalsIgnoreCase("")||jTextField34.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
        
         groupMontlyROI.add(jTextField35.getText().trim());  
          groupMontlyROI.add(jTextField34.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while group montly return on investement report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.groupMontlyROI(groupMontlyROI);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                groupMontlyROI.add(result);
                
         ReportView f1 = new ReportView(userId,"groupMontlyReturnOnInvestment",groupMontlyROI);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }   
    }//GEN-LAST:event_jButton142ActionPerformed

    private void jButton143ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton143ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"695");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton143ActionPerformed

    private void jButton144ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton144ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton144ActionPerformed

    private void jButton145ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton145ActionPerformed
   
    if (jTextField35.getText().equalsIgnoreCase("")||jTextField34.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else if (fmt.convertTdate(jTextField35.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField34.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.createReturnOnInvestmentMonthlyGroup(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField35.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField34 .getText().trim()),"return_MonthlyGroupReport")){
  
     generateFile("return_MonthlyGroupReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }

    }//GEN-LAST:event_jButton145ActionPerformed

    private void jButton149ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton149ActionPerformed
           fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"696");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton149ActionPerformed

    private void jButton155ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton155ActionPerformed
   feelmeSweetSusan();
   switchOffRIO();
        totalLoanShares1.setVisible(false);
   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "suzanlove");
    }//GEN-LAST:event_jButton155ActionPerformed

    private void balanceSheetButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton8ActionPerformed
     StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(true);  
                        membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_balanceSheetButton8ActionPerformed

    private void jButton181ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton181ActionPerformed
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"693");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton181ActionPerformed

    private void jButton179ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton179ActionPerformed
          fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"694");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton179ActionPerformed

    private void jButton180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton180ActionPerformed
       List provisionedItems= new ArrayList();
        
       if (jTextField41.getText().equalsIgnoreCase("")||jTextField40.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
       
         provisionedItems.add(jTextField41.getText().trim());  
          provisionedItems.add(jTextField40.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while statement of provisioned items is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  other.provisionedItems(provisionedItems);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                provisionedItems.add(result);
                
         ReportView f1 = new ReportView(userId,"provisionedItemsNew",provisionedItems);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
       }
    }//GEN-LAST:event_jButton180ActionPerformed

    private void jButton182ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton182ActionPerformed
switchOffRIO();
        feelmeSweetSusan();
              totalLoanShares3.setVisible(false);
   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "susanmylove");
    }//GEN-LAST:event_jButton182ActionPerformed

    private void balanceSheetButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton9ActionPerformed
StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(false);  
                        membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(true); 
    }//GEN-LAST:event_balanceSheetButton9ActionPerformed

    private void balanceSheetButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton10ActionPerformed
StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(false);  
                        membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(true); 
         sharesSummuryPanel.setVisible(false); 
    }//GEN-LAST:event_balanceSheetButton10ActionPerformed

    private void jButton151ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton151ActionPerformed
     
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"697"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton151ActionPerformed

    private void jButton152ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton152ActionPerformed
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"698"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton152ActionPerformed

    private void jButton184ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton184ActionPerformed
             
        List shareCont= new ArrayList();
        
       if (jTextField37.getText().equalsIgnoreCase("")||jTextField36.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
       shareCont.add(theAccount); 
         shareCont.add(jTextField37.getText().trim());  
          shareCont.add(jTextField36.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while shares contribution report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.sharesContributed(shareCont);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                shareCont.add(result);
                
         ReportView f1 = new ReportView(userId,"sharesContributed",shareCont);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        }   
    }//GEN-LAST:event_jButton184ActionPerformed

    private void jButton187ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton187ActionPerformed
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"700"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton187ActionPerformed

    private void jButton188ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton188ActionPerformed
          fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"701"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton188ActionPerformed

    private void jButton191ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton191ActionPerformed

        
        switch(jComboBox7.getSelectedItem().toString()){
         case "Members With Shares Only":
             
                 
        List groupShareCont= new ArrayList();
        
       if (jTextField45.getText().equalsIgnoreCase("")||jTextField46.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
  
         groupShareCont.add(jTextField45.getText().trim());  
          groupShareCont.add(jTextField46.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while List of Group shares contribution report is beeing created!!!");
                SwingWorker<ListDataModel,Void>groupSharesContribution=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.groupSharesContributed(groupShareCont);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                groupShareCont.add(result);
                
         ReportView f1 = new ReportView(userId,"listGroupSharesContributed",groupShareCont);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                groupSharesContribution.execute();
        
          
           
//            jButton116.setEnabled(false);
        }   
        
             break;
       case "All Members":
           
            List groupShareContAll= new ArrayList();
        
       if (jTextField45.getText().equalsIgnoreCase("")||jTextField46.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
  
         groupShareContAll.add(jTextField45.getText().trim());  
          groupShareContAll.add(jTextField46.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while List of Group shares contribution report is beeing created!!!");
                SwingWorker<ListDataModel,Void>groupSharesContribution=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.groupSharesContributedAll(groupShareContAll);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                groupShareContAll.add(result);
                
         ReportView f1 = new ReportView(userId,"listGroupSharesContributed",groupShareContAll);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                groupSharesContribution.execute();
        
          
           
//            jButton116.setEnabled(false);
        }   
        
             
           
           
           break;
     
     
     }
    }//GEN-LAST:event_jButton191ActionPerformed

    private void jButton192ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton192ActionPerformed
     
        feelmeSweetSusan();
      
      switchOffRIO();
      
              totalLoanShares3.setVisible(false);
              
   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "mercylove");
   
    }//GEN-LAST:event_jButton192ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
     switch(jComboBox3.getSelectedItem().toString()){
         case "Individual Shares":
          jPanel29.setVisible(true);
           jPanel30  .setVisible(false);
             
             break;
        case "Group Shares":
             jPanel29.setVisible(false);
           jPanel30  .setVisible(true);

            break;
     
     

     }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton185ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton185ActionPerformed
      
              
                 
    if (jTextField47.getText().equalsIgnoreCase("")||jTextField37.getText().equalsIgnoreCase("")||jTextField36.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField37.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField36.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.individualSharesContributions(theAccount,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField37.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField36 .getText().trim()),"return_MonthlyIndividualReport")){
  
     generateFile("return_MonthlyIndividualReport"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }     
              
              
    }//GEN-LAST:event_jButton185ActionPerformed

    private void jButton193ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton193ActionPerformed
   int today=cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH);
        
       int select1=parseInt(totalLoanSavingsCombo4.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo4.getSelectedItem().toString().split("\\s")[0]);
        
        int select2=parseInt(totalLoanSavingsCombo3.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo3.getSelectedItem().toString().split("\\s")[0]);
        
        if(select2>today) {
         JOptionPane.showMessageDialog(this, "The selected Month should not be greater than the System Month");
         return;
        }else{
        List TotalLoanDetails=new ArrayList();

        TotalLoanDetails.add(select1);
        
        TotalLoanDetails.add(select2);
        
        TotalLoanDetails.add(totalLoanSavingsCombo4.getSelectedItem().toString());
        
        TotalLoanDetails.add(totalLoanSavingsCombo3.getSelectedItem().toString()); 
        
        ReportView f1 = new ReportView(userId,"TotalSavingsPaymentReport",TotalLoanDetails);
        
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
       
         
        }
    }//GEN-LAST:event_jButton193ActionPerformed

    private void jButton194ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton194ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton194ActionPerformed

    private void totalLoanSavingsCombo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalLoanSavingsCombo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalLoanSavingsCombo2ActionPerformed

    private void jButton195ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton195ActionPerformed
       
        int today=cal.get(GregorianCalendar.YEAR)+cal.get(GregorianCalendar.MONTH);
        
       int select1=parseInt(totalLoanSavingsCombo5.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo5.getSelectedItem().toString().split("\\s")[0]);
        
        int select2=parseInt(totalLoanSavingsCombo6.getSelectedItem().toString().trim().split("\\s")[1])+fmt.monthNumber(totalLoanSavingsCombo6.getSelectedItem().toString().split("\\s")[0]);
        
        if(select2>today) {
         JOptionPane.showMessageDialog(this, "The selected Month should not be greater than the System Month");
         return;
        }else{
        List TotalLoanDetails=new ArrayList();

        TotalLoanDetails.add(select1);
        
        TotalLoanDetails.add(select2);
        
        TotalLoanDetails.add(totalLoanSavingsCombo5.getSelectedItem().toString());
        
        TotalLoanDetails.add(totalLoanSavingsCombo6.getSelectedItem().toString()); 
        
        ReportView f1 = new ReportView(userId,"TotalLoanPaymentReport",TotalLoanDetails);
        
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
       
         
        }
    }//GEN-LAST:event_jButton195ActionPerformed

    private void jButton196ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton196ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton196ActionPerformed

    private void jButton197ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton197ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton197ActionPerformed

    private void jButton198ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton198ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton198ActionPerformed

    private void jButton189ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton189ActionPerformed

        
         switch(jComboBox7.getSelectedItem().toString()){
        
             case "Members With Shares Only":
             
               if (jTextField45.getText().equalsIgnoreCase("")||jTextField46.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField45.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField46.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.listGroupSharesContributions(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField45.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField46 .getText().trim()),"sharesAvailable_Group")){
  
     generateFile("sharesAvailable_Group"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
                 
                 
                 
             break;
             
            case "All Members":
             
                
                  if (jTextField45.getText().equalsIgnoreCase("")||jTextField46.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField45.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField46.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.listGroupSharesContributionsAll(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField45.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField46 .getText().trim()),"sharesAvailable_Group")){
  
     generateFile("sharesAvailable_Group"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
                   
                
                
                
                
                
                
             break;
         }
        
              
                 
      
    }//GEN-LAST:event_jButton189ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
     switch(jComboBox8.getSelectedItem().toString()){
         case "Individual Savings":
          individualSavings.setVisible(true);
           groupSavingsPanel  .setVisible(false);
             
             break;
        case "Group Savings":
             individualSavings.setVisible(false);
           groupSavingsPanel  .setVisible(true);

            break;
     
     

     }
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jButton202ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton202ActionPerformed
            
    if (jTextField50.getText().equalsIgnoreCase("")||jTextField44.getText().equalsIgnoreCase("")||jTextField43.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField44.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField43.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.individualSavingsContributions(theAccount,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField44.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField43 .getText().trim()),"memberSavings_individual")){
  
     generateFile("memberSavings_individual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }     
     
     
     
     
    }//GEN-LAST:event_jButton202ActionPerformed

    private void jButton204ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton204ActionPerformed
    
        feelmeSweetSusan();
      
      switchOffRIO();
      
              totalLoanShares3.setVisible(false);
              
   fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "mercyMylove");
    }//GEN-LAST:event_jButton204ActionPerformed

    private void jButton154ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton154ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"702"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton154ActionPerformed

    private void jButton183ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton183ActionPerformed
         fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"703"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton183ActionPerformed

    private void jButton201ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton201ActionPerformed
                   
        List savingsCont= new ArrayList();
        
       if (jTextField44.getText().equalsIgnoreCase("")||jTextField43.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
       savingsCont.add(theAccount); 
         savingsCont.add(jTextField44.getText().trim());  
          savingsCont.add(jTextField43.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while savings report is beeing created!!!");
                SwingWorker<ListDataModel,Void>savingsReport=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.savingsContributed(savingsCont);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                savingsCont.add(result);
                
         ReportView f1 = new ReportView(userId,"savingsContributed",savingsCont);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                savingsReport.execute();
        
          
           
//            jButton116.setEnabled(false);
        } 
    }//GEN-LAST:event_jButton201ActionPerformed

    private void jButton205ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton205ActionPerformed

     switch(jComboBox9.getSelectedItem().toString()){
      
          case "Members With Savings Only":
              
               List groupSavingsCont= new ArrayList();
        
       if (jTextField48.getText().equalsIgnoreCase("")||jTextField49.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
  
         groupSavingsCont.add(jTextField48.getText().trim());  
          groupSavingsCont.add(jTextField49.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while List of Group shares contribution report is beeing created!!!");
                SwingWorker<ListDataModel,Void>groupSharesContribution=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.groupSavingsContributed(groupSavingsCont);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                groupSavingsCont.add(result);
                
         ReportView f1 = new ReportView(userId,"listGroupSavingsContributed",groupSavingsCont);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                groupSharesContribution.execute();
       }
              break;
      case "All Members":
             
               List groupSavingsContAll= new ArrayList();
        
       if (jTextField48.getText().equalsIgnoreCase("")||jTextField49.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
  
         groupSavingsContAll.add(jTextField48.getText().trim());  
          groupSavingsContAll.add(jTextField49.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while List of Group shares contribution report is beeing created!!!");
                SwingWorker<ListDataModel,Void>groupSharesContribution=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.groupSavingsContributedAll(groupSavingsContAll);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                groupSavingsContAll.add(result);
                
         ReportView f1 = new ReportView(userId,"listGroupSavingsContributedAll",groupSavingsContAll);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                groupSharesContribution.execute();
       }
          
          
          break;
      
      }
    }//GEN-LAST:event_jButton205ActionPerformed

    private void jButton206ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton206ActionPerformed
    
        
           switch(jComboBox9.getSelectedItem().toString()){
      
          case "Members With Savings Only":
             
               if (jTextField48.getText().equalsIgnoreCase("")||jTextField49.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField48.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField49.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.listGroupSavingsContributions(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField48.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField49 .getText().trim()),"savingsAvailable_Group")){
  
     generateFile("savingsAvailable_Group"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
                 
                 
                 
             break;
             
            case "All Members":
             
                
                  if (jTextField48.getText().equalsIgnoreCase("")||jTextField49.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField48.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField49.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.listGroupSavingsContributionsAll(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField48.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField49 .getText().trim()),"savingsAvailable_GroupAll")){
  
     generateFile("savingsAvailable_GroupAll"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
                   
                
                
                
                
                
                
             break;
         }
        
              
           
    }//GEN-LAST:event_jButton206ActionPerformed

    private void jButton199ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton199ActionPerformed
               fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"704"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton199ActionPerformed

    private void jButton200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton200ActionPerformed
    fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"705"); 
        
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton200ActionPerformed

    private void jButton215ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton215ActionPerformed
       fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"801");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton215ActionPerformed

    private void jButton228ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton228ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton228ActionPerformed

    private void jButton229ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton229ActionPerformed
        List savingsBalances= new ArrayList();
        
       if (jTextField57.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
      
          savingsBalances.add(jTextField57.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, "Wait while general return on investment report is beeing created!!!");
                SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.customersByBalances(savingsBalances);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                savingsBalances.add(result);
                
         ReportView f1 = new ReportView(userId,"accountabalances",savingsBalances);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
        } 
    }//GEN-LAST:event_jButton229ActionPerformed

    private void jButton227ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton227ActionPerformed
//  OtherLoanReports otherstusPdf=new OtherLoanReports();
//        otherstusPdf.LoanStatusReport(this);
        
        List theOtherDetailsBalanceSheet= new ArrayList();
       
//         theOtherDetailsBalanceSheet.add(valueDate8.getText().trim());  
         
comp=this;

          OtherLoanReports otherstusPdf=new OtherLoanReports();
          
            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....Loan Status Report Is Being Created!!!");
                SwingWorker<ReportsModelStatusReport,Void>statusReportWorker=new SwingWorker() {
                @Override
                protected ReportsModelStatusReport doInBackground() throws Exception {
                return  otherstusPdf.LoanStatusReport(comp);
                }

                @Override
                protected void done() {
                try {
                statusModel = (ReportsModelStatusReport) get();
                } catch (InterruptedException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                theOtherDetailsBalanceSheet.add(statusModel);
         ReportView f1 = new ReportView(userId,"loanStatusReport",theOtherDetailsBalanceSheet);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                statusReportWorker.execute();
        
          
           
            jButton116.setEnabled(false);
                    
          
                
          
          
          
            
    
        
         
        
  
        
    }//GEN-LAST:event_jButton227ActionPerformed

    private void jButton235ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton235ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton235ActionPerformed

    private void jButton234ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton234ActionPerformed

        if(otherLoans.createLoanPortfolioReport("loan_status")){
            generateFile("loan_status");
        } else{

            JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }
    }//GEN-LAST:event_jButton234ActionPerformed

    private void jButton236ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton236ActionPerformed
    fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"803");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton236ActionPerformed

    private void jButton237ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton237ActionPerformed
     fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"802");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton237ActionPerformed

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed

        
        if(!dbq.isBudgetNotEmpty()){
        
        
        JOptionPane.showMessageDialog(this, "NO BUDGET DEFINED YET!!!");
        jComboBox11.setSelectedIndex(0);
        return;
        
        }else{
        
        switch(jComboBox11.getSelectedItem().toString()){
                 case "Entire Period":
                  jLabel102.setVisible(false);
                  jTextField60 .setVisible(false);  
                  jButton237 .setVisible(false);  
                  jLabel101 .setVisible(false);  
                  jTextField59.setVisible(false);
                  jButton236  .setVisible(false);      
                     break;
         case "Date Range":
            jLabel102.setVisible(true);
            jTextField60 .setVisible(true);  
            jButton237 .setVisible(true);  
            jLabel101 .setVisible(true);  
            jTextField59.setVisible(true);
            jButton236  .setVisible(true);    
             
             
             break;
       
       }}
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
   loanDisbursementSchedule.setVisible(true);
//     agingReport.setVisible(false);
                 loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
            summuryOnLoans .setVisible(false);
             loansCompletedPanel.setVisible(false);
             
             
             
             
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton238ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton238ActionPerformed
    BOGAPanel1.setVisible(false);
         loansDisbursedPanel2 .setVisible(false);
           loansDisbursedPanel1.setVisible(false);
         loanDisbursementSchedule.setVisible(false);
         jPanel51.setVisible(false);
//        agingReport.setVisible(false);
            jPanel40.setVisible(false);
          BOGAPanel2.setVisible(false);
                 loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(true);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
            summuryOnLoans .setVisible(false);
             loansCompletedPanel.setVisible(false);
//             loansApprovedPanel
             
             
    }//GEN-LAST:event_jButton238ActionPerformed

    private void jButton239ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton239ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton239ActionPerformed

    private void jButton240ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton240ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton240ActionPerformed

    private void jButton241ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton241ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton241ActionPerformed

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
      fios.intFileWriterReplace(fios.createFileName("logOut", "completeShutDown", "windowChange1"+this.userId+".txt"), jTabbedPane1.getSelectedIndex()+"");
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jButton242ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton242ActionPerformed
           fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"804");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton242ActionPerformed

    private void jButton243ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton243ActionPerformed
       fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"805");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton243ActionPerformed

    private void jButton103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton103ActionPerformed
     
               List theOtherDetailsBalanceSheet= new ArrayList();
        
       if(jTextField64.getText().equalsIgnoreCase("")){JOptionPane.showMessageDialog(this, "Please input the Value date");return;} else 
            if (fmt.convertTdate(jTextField64.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
            } else{
        
         theOtherDetailsBalanceSheet.add(jTextField64.getText().trim());  
  
          
            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....The summury Report  is beeing created!!!");
            
                SwingWorker<ListDataModel,Void>pAnLWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.quickSummuryReport(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField64.getText().trim()),c);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                theOtherDetailsBalanceSheet.add(result);
         ReportView f1 = new ReportView(userId,"summuryStats",theOtherDetailsBalanceSheet);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                pAnLWorker.execute();
        
          
           
//            jButton116.setEnabled(false);
                    
          
                
          
          
          
            
    
        }
    }//GEN-LAST:event_jButton103ActionPerformed

    private void jButton244ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton244ActionPerformed
       summuryReportJPanel.setVisible(true);
       returnOnInvestmentPanels.setVisible(false);
       membersSelectionPanel1.setVisible(false);
       DailyCollections.setVisible(false);
         jPanel41SpecificSummuryReport.setVisible(false);
         DailySummuryReport.setVisible(false);
    }//GEN-LAST:event_jButton244ActionPerformed

    private void returnOnInvestmentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnOnInvestmentsButtonActionPerformed
summuryReportJPanel.setVisible(false);
       returnOnInvestmentPanels.setVisible(true);
       membersSelectionPanel1.setVisible(false);
        DailyCollections.setVisible(false);
        jPanel41SpecificSummuryReport.setVisible(false);
    }//GEN-LAST:event_returnOnInvestmentsButtonActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
        
        





switch(jComboBox12.getSelectedItem().toString()){
    case "SELECT ROI SUMMERY":
        
        JOptionPane.showMessageDialog(this, "Please make a valid selection!!!");
        break;
    case "Daily Individual ROI":
        DailyIndividualROI.setVisible(true);
        GroupROI.setVisible(false);
        MonthlyIndividualROI.setVisible(false);
        GroupSavingsAndShares.setVisible(false);        
        break;
    case "Monthly Individual ROI":
        
        DailyIndividualROI.setVisible(false);
        GroupROI.setVisible(false);
        MonthlyIndividualROI.setVisible(true);
        GroupSavingsAndShares.setVisible(false);
        
        
        break;
    case "Group ROI":
         DailyIndividualROI.setVisible(false);
        GroupROI.setVisible(true);
        MonthlyIndividualROI.setVisible(false);
        GroupSavingsAndShares.setVisible(false);
        
        
        break;
    case "Group Savings And Shares":
         DailyIndividualROI.setVisible(false);
        GroupROI.setVisible(false);
        MonthlyIndividualROI.setVisible(false);
        GroupSavingsAndShares.setVisible(true);
        
        
        break;

}
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jButton249ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton249ActionPerformed

        c=this; 
        
        switch(jComboBox13.getSelectedItem().toString()){
            
            case "Select Report Category":
                
                JOptionPane.showMessageDialog(this, "Please first select a valid category");
                
                break;
                
             case "On Shares":
                        List sharesDetails= new ArrayList();
        
                 if (jTextField68.getText().equalsIgnoreCase("")||jTextField67.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField68.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField67.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
        
         sharesDetails.add(jTextField68.getText().trim());  
   sharesDetails.add(jTextField67.getText().trim());  
          
            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....The return on investment on shares  is beeing created!!!");
            
                SwingWorker<ListDataModel,Void>pAnLWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.returnOnInvestmentShares(sharesDetails,c);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                sharesDetails.add(result);
         ReportView f1 = new ReportView(userId,"returnShares",sharesDetails);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                pAnLWorker.execute();

                 break;
                 
                 
              case "On Savings":
                  List sharesDetails1= new ArrayList();
        
                 if (jTextField68.getText().equalsIgnoreCase("")||jTextField67.getText().equalsIgnoreCase("")) {
                     
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        
        return;
        
        }else if (fmt.convertTdate(jTextField68.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField67.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
        
         sharesDetails1.add(jTextField68.getText().trim());  
         
         sharesDetails1.add(jTextField67.getText().trim());  
          
        JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....The return on investment on Savings  is beeing created!!!");
     
                SwingWorker<ListDataModel,Void>pAnLWorker1=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
//                    JOptionPane.showMessageDialog(c, "In");
                return  otherLoans.returnOnInvestmentSavings(sharesDetails1,c,jTextField67);
                }

                @Override
                protected void done() {
                try {
                result = (ListDataModel) get();
                } catch (InterruptedException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                sharesDetails1.add(result);
         ReportView f1 = new ReportView(userId,"returnShares1",sharesDetails1);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                pAnLWorker1.execute();
        

                  break;

        }
        
        
    }//GEN-LAST:event_jButton249ActionPerformed

    private void jButton250ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton250ActionPerformed
           fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"806");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton250ActionPerformed

    private void jButton251ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton251ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton251ActionPerformed

    private void jButton252ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton252ActionPerformed
      
        switch(jComboBox13.getSelectedItem().toString()){
            case "Select Report Category":
                
                JOptionPane.showMessageDialog(this, "Please first select a valid category");
                break;
             case "On Shares":
                    if (jTextField68.getText().equalsIgnoreCase("")||jTextField67.getText().equalsIgnoreCase("")) {
                        
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField68.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField67.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
//            JOption
     if(otherLoans.groupReturnSharesDaily(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField68.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField67 .getText().trim()),"ROI_Group_Shares",this)){
  
     generateFile("ROI_Group_Shares"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 
                 
                 break;
              case "On Savings":
                  if (jTextField68.getText().equalsIgnoreCase("")||jTextField67.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField68.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField67.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.groupReturnSavingsDaily(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField68.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField67 .getText().trim()),"ROI_Group_Savings",this,jTextField67)){
  
     generateFile("ROI_Group_Savings"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }    
                  
                  
                  break;
        
        


        
        
        }
        
        
    }//GEN-LAST:event_jButton252ActionPerformed

    private void jButton253ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton253ActionPerformed
   fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"807");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton253ActionPerformed

    private void jButton254ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton254ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton254ActionPerformed

    private void jButton255ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton255ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton255ActionPerformed

    private void jButton256ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton256ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton256ActionPerformed

    private void jButton257ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton257ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton257ActionPerformed

    private void jButton258ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton258ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton258ActionPerformed

    private void jButton259ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton259ActionPerformed
        feelmeNewSweetSusan();
//      switchOffRIONew
      switchOffRIONew();
 fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "mercyMylove2");   
    }//GEN-LAST:event_jButton259ActionPerformed

    private void jButton260ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton260ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton260ActionPerformed

    private void jButton261ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton261ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton261ActionPerformed

    private void jButton262ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton262ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton262ActionPerformed

    private void jButton263ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton263ActionPerformed
    
        switch(jComboBox13.getSelectedItem().toString()){
            case "Select Report Category":
                
                JOptionPane.showMessageDialog(this, "Please first select a valid category");
                break;
             case "On Shares":
                    if (jTextField75.getText().equalsIgnoreCase("")||jTextField76.getText().equalsIgnoreCase("")||jTextField74.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField76.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField74.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
//            JOption
     if(otherLoans.individualReturnSharesDaily(theAccountcn,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField76.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField74 .getText().trim()),"dailyROI_Individual",this)){
  
     generateFile("dailyROI_Individual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 
                 
                 break;
              case "On Savings":
                  if (jTextField75.getText().equalsIgnoreCase("")||jTextField76.getText().equalsIgnoreCase("")||jTextField74.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField76.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField74.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.individualReturnSavingsDaily(theAccountcn,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField76.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField74 .getText().trim()),"dailyROI_Individual")){
  
     generateFile("dailyROI_Individual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }    
                  
                  
                  break;
        
        


        
        
        }
        
        
    }//GEN-LAST:event_jButton263ActionPerformed

    private void jButton264ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton264ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton264ActionPerformed

    private void jButton265ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton265ActionPerformed
                   String statement="";
       switch(jComboBox13.getSelectedItem().toString()){
           case "On Shares":
               
 List indvDailyRoi= new ArrayList();
        
       if (jTextField76.getText().equalsIgnoreCase("")||jTextField74.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
        return;
        }
//       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
//         return;
//        } 
       else{
       indvDailyRoi.add(theAccountcn); 
         indvDailyRoi.add(jTextField76.getText().trim());  
          indvDailyRoi.add(jTextField74.getText().trim());  
        
          
            JOptionPane.showMessageDialog(this, statement);
            
                SwingWorker<ListDataModel,Void>savingsReport=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                return  otherLoans.sharesInterestPayDaily(indvDailyRoi,comp);
                }

                @Override
                protected void done() {
                try {
                    
                result = (ListDataModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                indvDailyRoi.add(result);
                
         ReportView f1 = new ReportView(userId,"indiDailyRoiShares",indvDailyRoi);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                savingsReport.execute();
       }
            break; 
         case "On Savings":
             
         
             break; 
        }
        
       
        
          
           
//            jButton116.setEnabled(false);
        
    }//GEN-LAST:event_jButton265ActionPerformed

    private void jButton266ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton266ActionPerformed
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"808");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton266ActionPerformed

    private void jButton267ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton267ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton267ActionPerformed

    private void jButton268ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton268ActionPerformed

        switch(jComboBox13.getSelectedItem().toString()){
            case "Select Report Category":
                
                JOptionPane.showMessageDialog(this, "Please first select a valid category");
                break;
             case "On Shares":
                    if (jTextField75.getText().equalsIgnoreCase("")||jTextField76.getText().equalsIgnoreCase("")||jTextField74.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField76.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField74.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
//            JOption
     if(otherLoans.individualReturnSharesDaily(theAccountcn,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField76.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField74 .getText().trim()),"dailyROI_Individual",this)){
  
     generateFile("dailyROI_Individual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        } 
                 
                 break;
              case "On Savings":
                  if (jTextField75.getText().equalsIgnoreCase("")||jTextField76.getText().equalsIgnoreCase("")||jTextField74.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date and account name from the calender");
        return;
        }else if (fmt.convertTdate(jTextField76.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField74.getText().trim()).after(fmt.getTodayDate())) {
         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
         return;
        }
            
     if(otherLoans.individualReturnSavingsDaily(theAccountcn,fmt.forDatabaseWithFullYearBeginningWithDate(jTextField76.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(jTextField74 .getText().trim()),"dailyROI_Individual")){
  
     generateFile("dailyROI_Individual"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }    
                  
                  
                  break;
        
        


        
        
        }
        
        
     
     
    }//GEN-LAST:event_jButton268ActionPerformed

    private void jButton269ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton269ActionPerformed
fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"809");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton269ActionPerformed

    private void jButton270ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton270ActionPerformed
        feelmeNewSweetSusan();
//      switchOffRIONew
      switchOffRIONew();
 fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "mercyMylove1");     
      
    }//GEN-LAST:event_jButton270ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
     switch(jComboBox13.getSelectedItem().toString()){
         case "On Shares":
          
             jComboBox12 .setVisible(true);
             break;
          case "On Savings":
              
             
             jComboBox12 .setVisible(true);
              break;
     

     }
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jButton245ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton245ActionPerformed
          summuryReportJPanel.setVisible(false);
       returnOnInvestmentPanels.setVisible(false);
       membersSelectionPanel1.setVisible(false);
        DailyCollections.setVisible(false);
           jPanel41SpecificSummuryReport.setVisible(true);
            DailySummuryReport.setVisible(false);
    }//GEN-LAST:event_jButton245ActionPerformed

    private void returnOnInvestmentsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnOnInvestmentsButton1ActionPerformed
      jPanel41SpecificSummuryReport.setVisible(false);
            summuryReportJPanel.setVisible(false);
       returnOnInvestmentPanels.setVisible(false);
       membersSelectionPanel1.setVisible(false);
        DailyCollections.setVisible(false);
           jPanel41SpecificSummuryReport.setVisible(false);
            DailySummuryReport.setVisible(true);
    }//GEN-LAST:event_returnOnInvestmentsButton1ActionPerformed

    private void jButton246ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton246ActionPerformed
      summuryReportJPanel.setVisible(false);
       returnOnInvestmentPanels.setVisible(false);
       membersSelectionPanel1.setVisible(false);
        DailyCollections.setVisible(true);
           jPanel41SpecificSummuryReport.setVisible(false);
            DailySummuryReport.setVisible(false);
    }//GEN-LAST:event_jButton246ActionPerformed

    private void jButton247ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton247ActionPerformed
          fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"900");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton247ActionPerformed

    private void jButton272ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton272ActionPerformed
                fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"901");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton272ActionPerformed

    private void jButton276ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton276ActionPerformed
      fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"902");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton276ActionPerformed

    private void jButton280ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton280ActionPerformed
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"903");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton280ActionPerformed

    private void jButton285ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton285ActionPerformed
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"904");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton285ActionPerformed

    private void jButton290ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton290ActionPerformed
  fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"909");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton290ActionPerformed

    private void jButton294ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton294ActionPerformed
  fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"908");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton294ActionPerformed

    private void jButton298ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton298ActionPerformed
  fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"907");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton298ActionPerformed

    private void jButton302ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton302ActionPerformed
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"906");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton302ActionPerformed

    private void jButton304ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton304ActionPerformed
            fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"905");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton304ActionPerformed

    private void jButton271ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton271ActionPerformed


            
               List dailyTotalCollections= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField65.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             
             return;
             
         }else{
        
         dailyTotalCollections.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField65.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the collection Report is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyTotalCollection(dailyTotalCollections,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                dailyTotalCollections.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsTotal",dailyTotalCollections);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
             
          
    }//GEN-LAST:event_jButton271ActionPerformed

    private void jButton273ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton273ActionPerformed


            
               List dailyTotalCollectionsArrears= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField66.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             return;
         }else{
        
         dailyTotalCollectionsArrears.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField66.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the collection Report with Arrears is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyTotalCollectionWithArrears(dailyTotalCollectionsArrears,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                dailyTotalCollectionsArrears.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsTotalArrear",dailyTotalCollectionsArrears);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton273ActionPerformed

    private void jButton308ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton308ActionPerformed


            
               List dailyTotalCollections= new ArrayList();
               
//         if("".equalsIgnoreCase(jTextField65.getText())){
//             
//             
//             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
//             return;
//         }else{
        
//         dailyTotalCollections.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField65.getText()));  
//                
          
            JOptionPane.showMessageDialog(this, "Wait while the gross portfolio Report is beeing created!!!");
            
                SwingWorker<ListDataModelPortfolioReport,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelPortfolioReport doInBackground() throws Exception {
                    
                return  other.grossLoanPorfolio(Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                porfolio = (ListDataModelPortfolioReport) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                dailyTotalCollections.add(porfolio);
                
         ReportView f1 = new ReportView(userId,"grossLoanPortFolioAnly",dailyTotalCollections);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
//         }
    }//GEN-LAST:event_jButton308ActionPerformed

    private void jButton277ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton277ActionPerformed


            
               List principalCol= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField78.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             return;
         }else{
        
         principalCol.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField78.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the collection Report with Arrears is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyPrincimpalCollection(principalCol,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                principalCol.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsPrincipal",principalCol);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton277ActionPerformed

    private void jButton281ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton281ActionPerformed

        
        

            
               List principalColArrears= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField79.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             return;
         }else{
        
         principalColArrears.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField79.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the collection Report with Arrears is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyPrincimpalCollectionArrears(principalColArrears,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                principalColArrears.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsPrincipalArrears",principalColArrears);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton281ActionPerformed

    private void jButton287ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton287ActionPerformed

        
        

            
               List interestCollect= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField80.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             return;
         }else{
        
         interestCollect.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField80.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the interest collection Report is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyInterestCollection(interestCollect,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                interestCollect.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsInterest",interestCollect);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton287ActionPerformed

    private void jButton289ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton289ActionPerformed

        
        

            
               List interestCollectArrears= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField81.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the collection date from the calader!!!");
             return;
         }else{
        
         interestCollectArrears.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField81.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the interest with arrears collection Report is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect doInBackground() throws Exception {
                    
                return  other.dailyInterestCollectionArrears(interestCollectArrears,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol = (ListDataModelDailyCollect) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                interestCollectArrears.add(dailyCol);
                
         ReportView f1 = new ReportView(userId,"dailyCollectionsInterestWithArrears",interestCollectArrears);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton289ActionPerformed

    private void jButton309ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton309ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton309ActionPerformed

    private void jButton310ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton310ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton310ActionPerformed

    private void jButton312ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton312ActionPerformed
     fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"911");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton312ActionPerformed

    private void jButton313ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton313ActionPerformed
         fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"912");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton313ActionPerformed

    private void jButton314ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton314ActionPerformed
       List interestCollectArrears= new ArrayList();
               SummuryReportDetails f1 = new SummuryReportDetails(userId,this,interestCollectArrears);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
    }//GEN-LAST:event_jButton314ActionPerformed

    private void jButton311ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton311ActionPerformed
if(summuryReportDetails.isEmpty()){
        
        JOptionPane.showMessageDialog(this, "Please first select the items to include in the summury report!!!");
        return;
        
        }else{
         if (jTextField87.getText().equalsIgnoreCase("")||jTextField86.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else{
             
         List sumRepo=new ArrayList();
         sumRepo.add(summuryReportText);
          sumRepo.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField87.getText()));  
            sumRepo.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField86.getText()));       
            sumRepo.add(counter);
            sumRepo.add("summury_ReportPdf");
              if(otherLoans.createActuallyReport(sumRepo,this,jTextField87)){
  
     generateFile("summury_ReportPdf"); 
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }
         }
}
        
        
        
        
        
        
        
//        
//        if (jTextField20.getText().equalsIgnoreCase("")||jTextField19.getText().equalsIgnoreCase("")) {
//        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
//        return;
//        }else if (fmt.convertTdate(jTextField20.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField19.getText().trim()).after(fmt.getTodayDate())) {
//         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
//         return;
//        }
            
   
        
    }//GEN-LAST:event_jButton311ActionPerformed

    private void jButton315ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton315ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton315ActionPerformed

    private void jButton316ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton316ActionPerformed
    
        
        if(summuryReportDetails.isEmpty()){
        
        JOptionPane.showMessageDialog(this, "Please first select the items to include in the summury report!!!");
        return;
        
        }else{
         if (jTextField87.getText().equalsIgnoreCase("")||jTextField86.getText().equalsIgnoreCase("")) {
        JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
        return;
        }else{
             
         List sumRepo=new ArrayList();
         sumRepo.add(summuryReportText);
          sumRepo.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField87.getText()));  
            sumRepo.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField86.getText()));       
            sumRepo.add(counter);
            JOptionPane.showMessageDialog(this, "Wait while the summury Report is beeing created!!!");
            
                SwingWorker<ListTableModel,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListTableModel doInBackground() throws Exception {
                    
                return  other.summuryReportOneByOne(sumRepo,Reportx.this,jTextField87);
                
                }

                @Override
                protected void done() {
                try {
                    
                sumDRepo = (ListTableModel) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                sumRepo.add(sumDRepo);
                
         ReportView f1 = new ReportView(userId,"summuryReportWithOneByOneSelections",sumRepo);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
        
        
             
             
         }
        
        }
        
   
    }//GEN-LAST:event_jButton316ActionPerformed

    private void jButton317ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton317ActionPerformed
                 fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),"913");  
      Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton317ActionPerformed

    private void jButton318ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton318ActionPerformed
   

            
               List generalSummuryReport= new ArrayList();
               
         if("".equalsIgnoreCase(jTextField88.getText())){
             
             
             JOptionPane.showMessageDialog(this, "Please first select the Summury date from the calader!!!");
             
             return;
             
         }else{
        
         generalSummuryReport.add(fmt.forDatabaseWithFullYearBeginningWithDate(jTextField88.getText()));  
                
          
            JOptionPane.showMessageDialog(this, "Wait while the dailySummury Report is beeing created!!!");
            
                SwingWorker<ListDataModelDailyCollect1,Void>projectedWorker=new SwingWorker() {
                    
                @Override
                
                protected ListDataModelDailyCollect1 doInBackground() throws Exception {
                    
                return  other.dailySummuryReport(generalSummuryReport,Reportx.this);
                
                }

                @Override
                protected void done() {
                try {
                    
                dailyCol1 = (ListDataModelDailyCollect1) get();
                
                } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }
                generalSummuryReport.add(dailyCol1);
                
         ReportView f1 = new ReportView(userId,"dailySummuryReport",generalSummuryReport);
        f1.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setSize(screen.getSize());
        f1.pack();
                }
                };
                projectedWorker.execute();
               
                  
              
         }
    }//GEN-LAST:event_jButton318ActionPerformed

    private void jButton319ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton319ActionPerformed
        
        if(jCheckBox1.isSelected()){
        if(boga.loanagingAnalysisG("aging_analysisG",this)){
           
     generateFile("aging_analysisG"); 
     
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
        }
        
        
         if(!jCheckBox1.isSelected()){
        
             if(boga.loanagingAnalysis("aging_analysis",this)){
           
     generateFile("aging_analysis"); 
     
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
        }
       
        
    }//GEN-LAST:event_jButton319ActionPerformed

    private void jButton320ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton320ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton320ActionPerformed

    private void jButton321ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton321ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton321ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        switch(jComboBox10.getSelectedItem().toString()){
            case "LIST ALL POSTED TXNS":

            jPanel25ByLedger .setVisible(false);
            jPanel27All.setVisible(true);
            jPanel24Table.setVisible(false);
            break;
            case "LIST TXNS FROM SPECIFIC LEDGER":
            jPanel25ByLedger .setVisible(true);
            jPanel27All.setVisible(false);
            jPanel24Table.setVisible(false);

            break;

        }
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jButton219ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton219ActionPerformed
        Integer z1=708;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton219ActionPerformed

    private void jButton221ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton221ActionPerformed

        List genLAllPTxns= new ArrayList();

        if (jTextField55.getText().equalsIgnoreCase("")||jTextField26.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date  before proceeding");
            return;
        }
        else if (fmt.convertTdate(jTextField55.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField26.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }
        else{

            genLAllPTxns.add(jTextField55.getText().trim());
            genLAllPTxns.add(jTextField26.getText().trim());

            JOptionPane.showMessageDialog(this, "Wait while general ledger report for all txns is beeing created!!!");
            SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  otherLoans.generalLedgerForAllTxns(genLAllPTxns);
                }

                @Override
                protected void done() {
                    try {

                        result = (ListDataModel) get();

                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    genLAllPTxns.add(result);

                    ReportView f1 = new ReportView(userId,"generalLedgerAll",genLAllPTxns);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            projectedWorker.execute();

            //            jButton116.setEnabled(false);
        }
    }//GEN-LAST:event_jButton221ActionPerformed

    private void jButton224ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton224ActionPerformed
        Integer z1=709;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton224ActionPerformed

    private void jButton217ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton217ActionPerformed
        feelmeSweetMercyMyLove();
    }//GEN-LAST:event_jButton217ActionPerformed

    private void jButton218ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton218ActionPerformed
        Integer z1=707;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton218ActionPerformed

    private void jButton220ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton220ActionPerformed
        Integer z1=706;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton220ActionPerformed

    private void jButton222ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton222ActionPerformed
        List genSpecificPTxns= new ArrayList();

        if (jTextField56.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid start date  before proceeding");
            return;
        }else{

            if (jTextField54.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please Select a valid end date  before proceeding");
                return;
            }else{

                if (jTextField54.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(this, "Please Select a valid account  before proceeding");
                    return;
                }else{

                    if (fmt.convertTdate(jTextField56.getText().trim()).after(fmt.getTodayDate())){
                        JOptionPane.showMessageDialog(this, "The Start Date cannot be greater than today's date!!");
                        return;
                    }else{

                        if (fmt.convertTdate(jTextField54.getText().trim()).after(fmt.getTodayDate())){
                            JOptionPane.showMessageDialog(this, "The End Date cannot be greater than today's date!!");
                            return;
                        }else{

                            genSpecificPTxns.add(theAccount);
                            genSpecificPTxns.add(jTextField56.getText().trim());
                            genSpecificPTxns.add(jTextField54.getText().trim());

                            JOptionPane.showMessageDialog(this, "Wait while general ledger report for  "+jTextField53.getText()+"'s txns is beeing created!!!");

                            SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                                @Override
                                protected ListDataModel doInBackground() throws Exception {
                                    return  otherLoans.generalLedgerForAccountTxns(genSpecificPTxns);
                                }

                                @Override
                                protected void done() {
                                    try {

                                        result = (ListDataModel) get();

                                    } catch (InterruptedException | ExecutionException ex) {
                                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    genSpecificPTxns.add(result);

                                    ReportView f1 = new ReportView(userId,"generalLedgerAccount",genSpecificPTxns);
                                    f1.setVisible(true);
                                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                                    f1.setSize(screen.getSize());
                                    f1.pack();
                                }
                            };
                            projectedWorker.execute();

                            //            jButton116.setEnabled(false);

                        }

                    }
                }
            }  }
    }//GEN-LAST:event_jButton222ActionPerformed

    private void cashFlowButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton4ActionPerformed
        Integer z4=104;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z4.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_cashFlowButton4ActionPerformed

    private void incomeStatementButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeStatementButton2ActionPerformed
        budgetVarianceAnalysis.setVisible(false);
        trialBalancePanel1.setVisible(false);
        jLabel23.setText("\t\t"+"SELECT FROM CALENDAR THE EFFECTIVE P&L ENDING PERIOD AND SUBMIT");
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
        pAndLPanel.setVisible(true);
        AssetRegister.setVisible(false);
        balanceSheetPanel.setVisible(false);
    }//GEN-LAST:event_incomeStatementButton2ActionPerformed

    private void balanceSheetButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceSheetButton2ActionPerformed
        budgetVarianceAnalysis.setVisible(false);
        trialBalancePanel1.setVisible(false);
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(true);
        pAndLPanel.setVisible(false);
        AssetRegister.setVisible(false);
        jLabel20.setText("\t\t"+"SELECT FROM CALENDAR THE EFFECTIVE BALANCE SHEET DATE AND SUBMIT");
    }//GEN-LAST:event_balanceSheetButton2ActionPerformed

    private void trialBalanceButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trialBalanceButton2ActionPerformed
        budgetVarianceAnalysis.setVisible(false);
        trialBalancePanel1.setVisible(true);
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
        pAndLPanel.setVisible(false);
        AssetRegister.setVisible(false);
        Integer z3=103;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z3.toString());
    }//GEN-LAST:event_trialBalanceButton2ActionPerformed

    private void accountStatement2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountStatement2ActionPerformed
        AssetRegister.setVisible(false);
        trialBalancePanel1.setVisible(false);
        pAndLPanel.setVisible(false);
        accountStatementPanal.setVisible(true);
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        budgetVarianceAnalysis.setVisible(false);
        balanceSheetPanel.setVisible(false);
    }//GEN-LAST:event_accountStatement2ActionPerformed

    private void jButton91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton91ActionPerformed
        budgetVarianceAnalysis.setVisible(false);
        //        jLabel10.setText("\t\t"+"THE CHART OF ACCOUNTS");
        //        quary. populateTree(jTree3,"Debits");
        //        quary. populateTree(jTree2,"Credits");
        generalLedgerPanel2.setVisible(true);
        GeneralLedgerPanel1.setVisible(false);
        jPanel23.setVisible(true);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
        pAndLPanel.setVisible(false);
        trialBalancePanel1.setVisible(false);
        AssetRegister.setVisible(false);
    }//GEN-LAST:event_jButton91ActionPerformed

    private void cashFlowButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton5ActionPerformed
        budgetVarianceAnalysis.setVisible(true);
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);
        pAndLPanel.setVisible(false);
        trialBalancePanel1.setVisible(false);
        AssetRegister.setVisible(false);
    }//GEN-LAST:event_cashFlowButton5ActionPerformed

    private void cashFlowButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashFlowButton6ActionPerformed
        AssetRegister.setVisible(true);
        trialBalancePanel1.setVisible(false);
        pAndLPanel.setVisible(false);
        accountStatementPanal.setVisible(false);
        generalLedgerPanel2.setVisible(false);
        GeneralLedgerPanel1.setVisible(false);
        budgetVarianceAnalysis.setVisible(false);
        balanceSheetPanel.setVisible(false);
    }//GEN-LAST:event_cashFlowButton6ActionPerformed

    private void valueDate8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate8ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        Integer z1=101;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z1.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed

        List theOtherDetailsBalanceSheet= new ArrayList();

        if(valueDate8.getText().equalsIgnoreCase("")){JOptionPane.showMessageDialog(this, "Please input the Value date");return;} else
        if (fmt.convertTdate(valueDate8.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        } else{

            theOtherDetailsBalanceSheet.add(valueDate8.getText().trim());
            TheBalanceSheet finPosPnL=new TheBalanceSheet(valueDate8.getText().trim());

            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....Balance sheet is beeing created!!!");
            SwingWorker<ListDataModel,Void>pAnLWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  finPosPnL.createBalanceSheet(Reportx.this);
                }

                @Override
                protected void done() {
                    try {
                        result = (ListDataModel) get();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    theOtherDetailsBalanceSheet.add(result);
                    JOptionPane.showMessageDialog(comp, userId);
                    ReportView f1 = new ReportView(userId,"BalanceSheet",theOtherDetailsBalanceSheet);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            pAnLWorker.execute();

            //            jButton116.setEnabled(false);

        }

        //        if(valueDate8.getText().equalsIgnoreCase("")){JOptionPane.showMessageDialog(this, "Please input the Value date");return;} else
        //            if (fmt.convertTdate(valueDate8.getText().trim()).after(fmt.getTodayDate())) {
            //         JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            //         return;
            //            } else{
            //                List theOtherDetailsBalanceSheet= new ArrayList();
            //         theOtherDetailsBalanceSheet.add(valueDate8.getText().trim());
            //            ReportView f1 = new ReportView(userId,"BalanceSheet",theOtherDetailsBalanceSheet);
            //        f1.setVisible(true);
            //        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            //        f1.setSize(screen.getSize());
            //        f1.pack();
            //    JOptionPane.showMessageDialog(this, "Please wait while the balanceSheet is being created...");
            //
            //        }

    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

        if(valueDate8.getText().equalsIgnoreCase("")){JOptionPane.showMessageDialog(this, "Please input the Value date");return;} else
        if (fmt.convertTdate(valueDate8.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        } else{
            List balanceSheet=sheet.balanceSheetAggregator("",fmt.forDatabaseWithFullYearBeginningWithDate(valueDate8.getText().trim()),Reportx.this);

            if(writeExcel.createBalanceSheetTheBook(balanceSheet,"Balance Sheet","balanceSheet")){

                generateFileExcel("balanceSheet");
            } else{

                JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

            }

        }

    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton76ActionPerformed
        JOptionPane.showMessageDialog(this, netprofits.theNetProfits(fmt.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt")).trim()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate8.getText()),Reportx.this));
    }//GEN-LAST:event_jButton76ActionPerformed

    private void jButton115ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton115ActionPerformed
        if(valueDate8.getText().equalsIgnoreCase("")){JOptionPane.showMessageDialog(this, "Please input the Value date");return;} else
        if (fmt.convertTdate(valueDate8.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        } else{

            if(sheet.createBalanceSheet(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate8.getText()),"balanceSheet",Reportx.this)){

                generateFile("balanceSheet");
            } else{

                JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

            }

        }
    }//GEN-LAST:event_jButton115ActionPerformed

    private void valueDate9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate9ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed

        Integer z2=102;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);

    }//GEN-LAST:event_jButton39ActionPerformed

    private void valueDate13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate13ActionPerformed

    private void jButton77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton77ActionPerformed

        Integer z2=115;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton77ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed

        if (valueDate9.getText().equalsIgnoreCase("")||valueDate13.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate9.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate13.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }

        if(pAndL.createPAndL(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate9.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate13.getText().trim()),"pAndL",Reportx.this)){

            generateFile("pAndL");
        } else{

            JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }

    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton78ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton78ActionPerformed

    private void jButton116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton116ActionPerformed

        List theOtheProfitAndLoss= new ArrayList();

        if (valueDate9.getText().equalsIgnoreCase("")||valueDate13.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate9.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate13.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        } else{

            theOtheProfitAndLoss.add(valueDate9.getText().trim());
            theOtheProfitAndLoss.add(valueDate13.getText().trim());
            ProfitAndLossNew finPosPnL=new ProfitAndLossNew(theOtheProfitAndLoss);
            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....P&L is beeing created!!!");
            SwingWorker<ListDataModel,Void>pAnLWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  finPosPnL.createProfitAndLoss(Reportx.this);
                }

                @Override
                protected void done() {
                    try {
                        result = (ListDataModel) get();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    theOtheProfitAndLoss.add(result);
                    ReportView f1 = new ReportView(userId,"ProfitAndLoss",theOtheProfitAndLoss);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            pAnLWorker.execute();

            //            jButton116.setEnabled(false);

        }

    }//GEN-LAST:event_jButton116ActionPerformed

    private void valueDate12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate12ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        Integer z2=110;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed

        String balSheetProfit="";

        if (valueDate12.getText().equalsIgnoreCase("")||valueDate14.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate12.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate14.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }
        balSheetProfit=( parseDouble(netprofits.theNetProfits(fmt.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("persistence", "operatingCycle", "YEARstartDate.txt"))), fmt.forDatabaseWithFullYearBeginningWithDate(valueDate14.getText().trim()),Reportx.this))-parseDouble(netprofits.theNetProfits(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate12.getText().trim()), fmt.forDatabaseWithFullYearBeginningWithDate(valueDate14.getText().trim()),Reportx.this)) )+"";

        fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "netprofit.txt"), balSheetProfit);

        if(trialB.createTrialBalance(fmt.forDatabaseWithFullYearBeginningWithDate(valueDate12.getText().trim()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate14.getText().trim()),"trialBalance",Reportx.this)){

            generateFile("trialBalance");
        } else{

            JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }

        //        }
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton97ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton97ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton97ActionPerformed

    private void valueDate14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate14ActionPerformed

    private void jButton98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton98ActionPerformed
        Integer z2=610;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton98ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        jLabel10.setText("\t\t"+"THE CHART OF ACCOUNTS");
        quary. populateTree(jTree3,"Debits");
        quary. populateTree(jTree2,"Credits");
        generalLedgerPanel2.setVisible(true);
        GeneralLedgerPanel1.setVisible(false);
        accountStatementPanal.setVisible(false);
        balanceSheetPanel.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTextField17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyPressed
        //   switch(jComboBox1.getSelectedItem().toString()){
            //            case "Member Accounts":
            //                 dbq.fillMeWithAllAccountsCc(accountTable,jTextField17.getText());
            //                break;
            //            case "Asset Accounts":
            //                dbq.fillMeWithAllAccountsAssetsc(accountTable,jTextField17.getText());
            //
            //                break;
            //            case "Liabilities Accounts":
            //                     dbq.fillMeWithAllAccountsLiabilitiesc(accountTable,jTextField17.getText());
            //                break;
            //            case "Expenses Accounts":
            //
            //                  dbq.fillMeWithAllAccountsExpensesc(accountTable,jTextField17.getText());
            //
            //                break;
            //            case "Equity Accounts":
            //
            //                  dbq.fillMeWithAllAccountsEquityc(accountTable,jTextField17.getText());
            //                break;
            //            case "Revenue Accounts":
            //
            //                dbq.fillMeWithAllAccountsRevenuec(accountTable,jTextField17.getText());
            //                break;
            //
            //            default:
            //
            // dbq.fillMeWithAllAccountsC(accountTable);
            //
            //
            //
            //           }
        //
    }//GEN-LAST:event_jTextField17KeyPressed

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        //     switch(jComboBox1.getSelectedItem().toString()){
            //            case "Member Accounts":
            //                 dbq.fillMeWithAllAccountsCc(accountTable,jTextField17.getText());
            //                break;
            //            case "Asset Accounts":
            //                dbq.fillMeWithAllAccountsAssetsc(accountTable,jTextField17.getText());
            //
            //                break;
            //            case "Liabilities Accounts":
            //                     dbq.fillMeWithAllAccountsLiabilitiesc(accountTable,jTextField17.getText());
            //                break;
            //            case "Expenses Accounts":
            //
            //                  dbq.fillMeWithAllAccountsExpensesc(accountTable,jTextField17.getText());
            //
            //                break;
            //            case "Equity Accounts":
            //
            //                  dbq.fillMeWithAllAccountsEquityc(accountTable,jTextField17.getText());
            //                break;
            //            case "Revenue Accounts":
            //
            //                dbq.fillMeWithAllAccountsRevenuec(accountTable,jTextField17.getText());
            //                break;
            //
            //            default:
            //
            // dbq.fillMeWithAllAccountsC(accountTable);
            //
            //
            //
            //           }
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jButton230ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton230ActionPerformed

    }//GEN-LAST:event_jButton230ActionPerformed

    private void jButton231ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton231ActionPerformed

        String theCounter=getCounter();

        ObjectTableModel original = (ObjectTableModel)jTable2.getModel();
        List<List>  d=new ArrayList();  List c=new ArrayList();

        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

        d.add(c);

        for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {

            if(!original.getRow(targetRow).isEmpty()){
                if(jTable2.isCellSelected(targetRow, 0)){
                    d.add(original.getRow(jTable2.convertRowIndexToModel(targetRow)));
                }
                ////                else{
                    ////     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
                    ////
                    ////           }
            }
        }

        writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

        generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton231ActionPerformed

    private void jButton232ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton232ActionPerformed
        String titles="";

        switch(jComboBox1.getSelectedItem().toString()){
            case "Member/Customer Ledgers":
            titles="CUSTOMERS BY BALANCES AS AT  ";
            break;
            case "Asset Ledgers":
            titles="ASSETS BY BALANCES AS AT  ";
            break;
            case "Fixed Assets Ledgers":

            titles="FIXED ASSETS BY BALANCES AS AT  ";

            break;
            case "Quick Assets Ledgers":

            titles="CURRENT ASSETS BY BALANCES AS AT  ";
            break;
            case "Receivable Ledgers":
            titles="RECEIVABLES BY BALANCES AS AT  ";

            break;
            case "Banks Ledgers":

            titles="BANKS BY BALANCES AS AT   ";
            break;
            case "Cash Ledgers":
            titles="CASH BY BALANCES AS AT   ";

            break;
            case "Loan Ledgers":
            titles="LOANS BY BALANCES AS AT   ";
            break;
            case "Liabilities Ledgers":
            titles="LIABILITIES BY BALANCES AS AT   ";

            break;
            case "Expense Ledgers":

            titles="EXPENSES BY BALANCES AS AT   ";
            break;
            case "Equity Ledgers":

            titles="EQUITY BY BALANCES AS AT   ";
            break;
            case "Revenue Ledgers":

            titles="REVENUE BY BALANCES AS AT   ";

            break;

        }
        printTable(accountTable,titles+fmt.dateConverterForNormalDate(System.currentTimeMillis()));
    }//GEN-LAST:event_jButton232ActionPerformed

    private void jButton233ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton233ActionPerformed

        String theCounter=getCounter();
        ReportsModel original = (ReportsModel)accountTable.getModel();

        List<List>  d=new ArrayList();  List c=new ArrayList();

        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}

        d.add(c);

        for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {

            if(!original.getRow(targetRow).isEmpty()){
                if(accountTable.isCellSelected(targetRow, 0)){
                    d.add(original.getRow(accountTable.convertRowIndexToModel(targetRow)));
                }
                ////                else{
                    ////     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
                    ////
                    ////           }
            }
        }

        writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);

        generateFileExcelq("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
    }//GEN-LAST:event_jButton233ActionPerformed

    private void JBTrnSqNo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBTrnSqNo4ActionPerformed

        accountStatementPanal1.setVisible(true);
        accountStatementPanal.setVisible(false);

        switch(jComboBox1.getSelectedItem().toString()){
            case "Member/Customer Ledgers":
            dbq.fillMeWithAllAccountsCustomerReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Asset Ledgers":
            dbq.fillMeWithAllAccountsAssetReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Fixed Assets Ledgers":
            dbq.fillMeWithAllAccountsFixedAssetReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Current Assets Ledgers":
            dbq.fillMeWithAllAccountsCurrentAssetReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Receivable Ledgers":
            dbq.fillMeWithAllAccountsReceivableReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Bank Ledgers":
            dbq.fillMeWithAllAccountsBankReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            case "Cash Ledgers":
            dbq.fillMeWithAllAccountsCashReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;
            case "Loan Ledgers":
            dbq.fillMeWithAllAccountsLoanReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;
            case "Liabilities Ledgers":
            dbq.fillMeWithAllAccountsLiabilitiesReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;
            case "Expense Ledgers":
            dbq.fillMeWithAllAccountsExpensesReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);

            break;
            case "Equity Ledgers":
            dbq.fillMeWithAllAccountsEquityReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;
            case "Revenue Ledgers":
            dbq.fillMeWithAllAccountsRevenueReports(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);
            break;

            default:

            dbq.fillMeWithAllAccountsC(accountTable);
            this.sortTable(accountTable, jTextField17);
            this.renderTheTable(accountTable, 2);

        }
    }//GEN-LAST:event_JBTrnSqNo4ActionPerformed

    private void debitAccountField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debitAccountField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField1ActionPerformed

    private void debitAccountField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField1KeyPressed

    private void debitAccountField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debitAccountField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_debitAccountField1KeyReleased

    private void valueDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDate1ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        Integer z5=105;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z5.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void valueDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueDateActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed

        Integer z6=106;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z6.toString());
        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton28ActionPerformed

    private void amountFieldDebit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldDebit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldDebit2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(debitAccountField1.getValue()==null){JOptionPane.showMessageDialog(this, "Please Select a Valid Account Number"); return;}
        else if(dbq.testTable("bsanca"+fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()))==false){JOptionPane.showMessageDialog(this, "Account  does not exist");return;}

        else if (valueDate1.getText().equalsIgnoreCase("")||valueDate.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate1.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }else{

            if(amountFieldDebit2.getValue()==null){
                List theOtherDetailsStatement= new ArrayList();
                theOtherDetailsStatement.add(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()));
                theOtherDetailsStatement.add(valueDate1.getText());
                theOtherDetailsStatement.add(valueDate.getText().trim());
                ReportView f1 = new ReportView(userId,"CustomerStatment",theOtherDetailsStatement);
                f1.setVisible(true);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                f1.setSize(screen.getSize());
                f1.pack();

            }else{

                List theOtherDetailsStatements= new ArrayList();

                theOtherDetailsStatements.add(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()));
                theOtherDetailsStatements.add(valueDate1.getText());
                theOtherDetailsStatements.add(valueDate.getText().trim());
                theOtherDetailsStatements.add(amountFieldDebit2.getValue().toString());
                amountFieldDebit2.setValue(null);
                ReportView f1 = new ReportView(userId,"customerStatementAmnt",theOtherDetailsStatements);
                f1.setVisible(true);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                f1.setSize(screen.getSize());
                f1.pack();

            }

    }//GEN-LAST:event_jButton4ActionPerformed
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if(debitAccountField1.getValue()==null){JOptionPane.showMessageDialog(this, "Please Select a Valid Account Number"); return;}
        else if(dbq.testTable("bsanca"+fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()))==false){JOptionPane.showMessageDialog(this, "Account  does not exist");return;}

        else if (valueDate1.getText().equalsIgnoreCase("")||valueDate.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate1.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }else{
            if(amountFieldDebit2.getValue()==null){
                List cusstState=pdf.createExcelCustomerTable(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate1.getText()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate.getText().trim()));

                if(writeExcel.createSheet3TheBook(cusstState,"Customer Statement","customerStatement")){

                    generateFileExcel("customerStatement");
                } else{

                    JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

                }

            }else{
                List custStatementAmnt=pdf.createExcelCustomerTableAmnt(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate1.getText()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate.getText().trim()),amountFieldDebit2.getValue().toString());

                if(writeExcel.createSheet3TheBook(custStatementAmnt,"Customer Statement","customerStatementAmnt")){

                    generateFileExcel("customerStatementAmnt");
                    amountFieldDebit2.setValue(null);
                } else{

                    JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton75ActionPerformed

    private void jButton114ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton114ActionPerformed

        if(debitAccountField1.getValue()==null){JOptionPane.showMessageDialog(this, "Please Select a Valid Account Number"); return;}
        else if(dbq.testTable("bsanca"+fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()))==false){JOptionPane.showMessageDialog(this, "Account  does not exist");return;}

        else if (valueDate1.getText().equalsIgnoreCase("")||valueDate.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(valueDate1.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(valueDate.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }else{
            if(amountFieldDebit2.getValue()==null){

                if(pdf.createStatement(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate1.getText().trim()), fmt.forDatabaseWithFullYearBeginningWithDate(valueDate.getText().trim()),"customer_Statement")){

                    generateFile("customer_Statement");
                } else{

                    JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

                }

            }else{

                if(pdf.createStatementAmount(fmt.formatAccountWithSeperators(debitAccountField1.getValue().toString()),fmt.forDatabaseWithFullYearBeginningWithDate(valueDate1.getText().trim()), fmt.forDatabaseWithFullYearBeginningWithDate(valueDate.getText().trim()),"customer_Statement",amountFieldDebit2.getValue().toString())){

                    generateFile("customer_Statement");
                    amountFieldDebit2.setValue(null);
                } else{

                    JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

                }
            }
        }
    }//GEN-LAST:event_jButton114ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        JBTrnSqNo4.setEnabled(true);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton117ActionPerformed

        List theBudgetReport= new ArrayList();

        if (jTextField23.getText().equalsIgnoreCase("")||jTextField18.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(jTextField23.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField18.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        } else{

            theBudgetReport.add(jTextField23.getText().trim());
            theBudgetReport.add(jTextField18.getText().trim());
            BudgetVarianceAnalysisReport budget=new BudgetVarianceAnalysisReport(theBudgetReport);
            SwingWorker<ListDataModel,Void>budgetWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  budget.createBudgetVarianceAnalysis(Reportx.this);
                }

                @Override
                protected void done() {
                    try {
                        result = (ListDataModel) get();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    theBudgetReport.add(result);
                    ReportView f1 = new ReportView(userId,"BudgetVarianceAnalysisReport",theBudgetReport);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            budgetWorker.execute();
            JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....P&L is beeing created!!!");

            jButton117.setEnabled(false);

        }

    }//GEN-LAST:event_jButton117ActionPerformed

    private void jButton118ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton118ActionPerformed
        Integer z2=620;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton118ActionPerformed

    private void jButton119ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton119ActionPerformed
        Integer z2=630;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton119ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed

        List AssetRegisterDetails= new ArrayList();

        comp=this;

        OtherLoanReports otherstusPdf=new OtherLoanReports();

        JOptionPane.showMessageDialog(this, "Wait!!! Wait!!....The Asset Register is being created!!!");

        SwingWorker<AssetRegisterModel,Void>assetRegister=new SwingWorker() {
            @Override
            protected AssetRegisterModel doInBackground() throws Exception {
                return  otherstusPdf.assetRegisterReport(comp);
            }

            @Override
            protected void done() {
                try {
                    assetRegisterModel1 = (AssetRegisterModel) get();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                }

                AssetRegisterDetails.add(assetRegisterModel1);

                ReportView f1 = new ReportView(userId,"assetRegisterReport",AssetRegisterDetails);
                f1.setVisible(true);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                f1.setSize(screen.getSize());
                f1.pack();
            }
        };
        assetRegister.execute();

    }//GEN-LAST:event_jButton40ActionPerformed

    private void bogaStatementButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bogaStatementButton1ActionPerformed
//BOGAPanel2     
         fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
             loansDisbursedPanel2 .setVisible(false);
                 loansDisbursedPanel1.setVisible(false); 
        loansDue1   .setVisible(false);
//    agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
        BOGAPanel1.setVisible(false);
       BOGAPanel2.setVisible(true);
       jPanel51.setVisible(false);
             jPanel40.setVisible(true);
   loanPortFolioPanel.setVisible(false);
     loansAppliedPanel.setVisible(false);
     loansApprovedPanel.setVisible(false);
     loansDisbursedPanel.setVisible(false);
     loansDue.setVisible(false);
        loansInArrears.setVisible(false);
         loansDueForWriteOff.setVisible(false);
          portfolioAtRisk.setVisible(false);
//          performingLoanPortfolio.setVisible(false);
           loansToStaffMembers.setVisible(false);
             summuryOnLoans .setVisible(false);
                loansCompletedPanel.setVisible(false);
    }//GEN-LAST:event_bogaStatementButton1ActionPerformed

    private void jButton322ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton322ActionPerformed

        
        if(jCheckBox4.isSelected()){
        
        
        if(boga.loanagingAnalysisOfficerG("aging_analysisOfficerG",idNow,this)){
           
     generateFile("aging_analysisOfficerG"); 
     jButton322.setEnabled(false);
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
        
        
        
        }
        
             if(!jCheckBox4.isSelected()){
             
             
             if(boga.loanagingAnalysisOfficer("aging_analysisOfficer",idNow,this)){
           
     generateFile("aging_analysisOfficer"); 
     jButton322.setEnabled(false);
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
             
             
             }
        
       
    }//GEN-LAST:event_jButton322ActionPerformed

    private void jButton323ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton323ActionPerformed
               
       if(boga.createGrossLoanPortfolioPdf("portfolio_gross",this)){
           
     generateFile("portfolio_gross"); 
     
        } else{
        
        JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");
       
        }  
        
    }//GEN-LAST:event_jButton323ActionPerformed

    private void jButton324ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton324ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton324ActionPerformed

    private void jButton325ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton325ActionPerformed
  List<List>  d=new ArrayList();  List c=new ArrayList();      
        
        
//        if(jCheckBox2.isSelected()){
//            
//     loanManagementModel original = (loanManagementModel)jTable2.getModel();
//     
//        for (int col = 0; col < original.getColumnCount(); col++) {  c.add(original.getColumnName(col));}
//
//d.add(c);
//
//for (int targetRow = 0; targetRow < original.getRowCount(); targetRow++) {
//    if(!original.getRow(targetRow).isEmpty()){
//            if(jTable2.isCellSelected(targetRow, 0)){
//    d.add(original.getRow(jTable2.convertRowIndexToModel(targetRow)));
//            }
//            else{
//     JOptionPane.showMessageDialog(this, "Please first select the rows to export to excel");
//           
//           }
//    }}
//         String theCounter=getCounter();     
//        
//writeExcel.creatExcelFromTable(d,"Recon"+sdk.format(new Date(System.currentTimeMillis()))+theCounter,"table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
//  
//generateFile("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter); 
//        
//        }else if(jCheckBox1.isSelected()){
        
     loanManagementModel1 original = loan.fullLoanDetailsForUpate1();
        
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
  
generateFileExcelq("table"+sdk.format(new Date(System.currentTimeMillis()))+theCounter);
        
//        }else{
//        
//        JOptionPane.showMessageDialog(this, "Please select the approproate export option from the check box options!!!!");
//        
//        }
        
        
        
               
                  
              
    }//GEN-LAST:event_jButton325ActionPerformed

    private void jButton102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton102ActionPerformed
   
    }//GEN-LAST:event_jButton102ActionPerformed

    private void jButton326ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton326ActionPerformed
  loansDisbursedPanel1.setVisible(true);  
//    loansDisbursedPanel1.setVisible(false); 
     BOGAPanel1.setVisible(false);
         loansDisbursedPanel2 .setVisible(false);
//           loansDisbursedPanel1.setVisible(false);
         loanDisbursementSchedule.setVisible(false);
         jPanel51.setVisible(false);
//        agingReport.setVisible(false);
            jPanel40.setVisible(false);
          BOGAPanel2.setVisible(false);
                 loansDue1   .setVisible(false);
        loanStatementPanel.setVisible(false);
    BOGAPanel.setVisible(false);
   loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
       loansInArrears.setVisible(false);
        loansDueForWriteOff.setVisible(false);
         portfolioAtRisk.setVisible(false);
//         performingLoanPortfolio.setVisible(false);
          loansToStaffMembers.setVisible(false);
            summuryOnLoans .setVisible(false);
             loansCompletedPanel.setVisible(false);
//             loansApprovedPanel
             
           
    }//GEN-LAST:event_jButton326ActionPerformed

    private void jButton327ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton327ActionPerformed
       BOGAPanel1.setVisible(false);
        fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "loanStatements.txt"),"3");
        loanDisbursementSchedule.setVisible(false);
        loansDue1   .setVisible(false);
//         agingReport.setVisible(false);
        loanStatementPanel.setVisible(false);
        jPanel51.setVisible(false);
            jPanel40.setVisible(false);
    BOGAPanel.setVisible(false);
      BOGAPanel2.setVisible(false);
    loanPortFolioPanel.setVisible(false);
    loansAppliedPanel.setVisible(false);
    loansApprovedPanel.setVisible(false);
    loansDisbursedPanel.setVisible(false);
    loansDue.setVisible(false);
    loansInArrears.setVisible(false);
     loansDueForWriteOff.setVisible(false);
      portfolioAtRisk.setVisible(false);
//      performingLoanPortfolio.setVisible(false);
       loansToStaffMembers.setVisible(false);
         summuryOnLoans .setVisible(false);
            loansCompletedPanel.setVisible(false);
            loansDisbursedPanel2 .setVisible(true);
            loansDisbursedPanel1.setVisible(false);
    }//GEN-LAST:event_jButton327ActionPerformed

    private void jButton328ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton328ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton328ActionPerformed

    private void jButton329ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton329ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton329ActionPerformed

    private void jButton330ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton330ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton330ActionPerformed

    private void jButton331ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton331ActionPerformed
       Integer z2=915;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton331ActionPerformed

    private void jButton332ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton332ActionPerformed
       Integer z2=914;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton332ActionPerformed

    private void jButton333ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton333ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton333ActionPerformed

    private void jButton334ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton334ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton334ActionPerformed

    private void jButton335ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton335ActionPerformed
             List loanStatusStatment= new ArrayList();

        if (jTextField92.getText().equalsIgnoreCase("")||jTextField91.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }
        //       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
            //         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
            //         return;
            //        }
        else{

            loanStatusStatment.add(jTextField92.getText().trim());
            loanStatusStatment.add(jTextField91.getText().trim());

            JOptionPane.showMessageDialog(this, "Wait statement of loan status is beeing created!!!");
            SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  otherLoans.projectedPayments(loanStatusStatment);
                }

                @Override
                protected void done() {
                    try {
                        result = (ListDataModel) get();
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loanStatusStatment.add(result);

                    ReportView f1 = new ReportView(userId,"ProjectedPaymens",loanStatusStatment);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            projectedWorker.execute();

            //            jButton116.setEnabled(false);
        }

    }//GEN-LAST:event_jButton335ActionPerformed

    private void jButton127ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton127ActionPerformed
        Integer z2=685;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton127ActionPerformed

    private void jButton126ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton126ActionPerformed
        Integer z2=686;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton126ActionPerformed

    private void jButton209ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton209ActionPerformed
        List projectedPaymentStore= new ArrayList();

        if (jTextField52.getText().equalsIgnoreCase("")||jTextField51.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }
        //       else if (fmt.convertTdate(jTextField27.getText().trim()).before(fmt.getTodayDateLess1())||fmt.convertTdate(jTextField26.getText().trim()).before(fmt.getTodayDateLess1())) {
            //         JOptionPane.showMessageDialog(this, "The selected date should not be less than today");
            //         return;
            //        }
        else{

            projectedPaymentStore.add(jTextField52.getText().trim());
            projectedPaymentStore.add(jTextField51.getText().trim());

            JOptionPane.showMessageDialog(this, "Wait while projected payments report is beeing created!!!");
            SwingWorker<ListDataModel,Void>projectedWorker=new SwingWorker() {
                @Override
                protected ListDataModel doInBackground() throws Exception {
                    return  otherLoans.projectedPayments(projectedPaymentStore);
                }

                @Override
                protected void done() {
                    try {
                        result = (ListDataModel) get();
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    projectedPaymentStore.add(result);

                    ReportView f1 = new ReportView(userId,"ProjectedPaymens",projectedPaymentStore);
                    f1.setVisible(true);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    f1.setSize(screen.getSize());
                    f1.pack();
                }
            };
            projectedWorker.execute();

            //            jButton116.setEnabled(false);
        }

    }//GEN-LAST:event_jButton209ActionPerformed

    private void jButton208ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton208ActionPerformed
        jPanel21.setVisible(false);

//        jPanel20.setVisible(true);
//
//        jPanel22.setVisible(false);
    }//GEN-LAST:event_jButton208ActionPerformed

    private void jButton124ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton124ActionPerformed
        jPanel21.setVisible(false);

//        jPanel20.setVisible(false);
//
//        jPanel22.setVisible(true);
    }//GEN-LAST:event_jButton124ActionPerformed

    private void jButton123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton123ActionPerformed
        jPanel21.setVisible(true);

//        jPanel20.setVisible(false);
//
//        jPanel22.setVisible(false);
    }//GEN-LAST:event_jButton123ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
        Integer z2=311;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        if (jTextField2.getText().equalsIgnoreCase("")||jTextField1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please Select a valid date from the calender");
            return;
        }else if (fmt.convertTdate(jTextField2.getText().trim()).after(fmt.getTodayDate())||fmt.convertTdate(jTextField1.getText().trim()).after(fmt.getTodayDate())) {
            JOptionPane.showMessageDialog(this, "The selected date should not be greater than today");
            return;
        }else{

            if(otherLoans.createPeriodicSummury(jTextField2.getText(),jTextField1.getText(),"p_summury")){

                generateFile("p_summury");
            } else{

                JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

            }

        }
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        Integer z2=310;
        fios.intFileWriterReplace(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"),z2.toString());

        Calender frmMain = new Calender();
        frmMain.setIUpdateText(this);  // assuming 'this' is an IUpdateText interface compliant
        frmMain.pack();
        frmMain.setVisible(true);
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jLabel18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MousePressed
        jLabel18.setVisible(false);
        jLabel16.setVisible(true);
        jLabel17.setVisible(true);
        jLabel15.setVisible(true);
        jButton61.setVisible(true);
        jButton71.setVisible(true);
        jTextField2.setVisible(true);
        jTextField1.setVisible(true);
        jButton70.setVisible(true);

        jButton49.setVisible(false);
    }//GEN-LAST:event_jLabel18MousePressed

    private void jLabel16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MousePressed
        jLabel18.setVisible(true);
        jLabel16.setVisible(false);
        jButton49.setVisible(true);

        jLabel17.setVisible(false);
        jLabel15.setVisible(false);
        jButton61.setVisible(false);
        jButton71.setVisible(false);
        jTextField2.setVisible(false);
        jTextField1.setVisible(false);
        jButton70.setVisible(false);
    }//GEN-LAST:event_jLabel16MousePressed

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered

    }//GEN-LAST:event_jLabel16MouseEntered

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        if(otherLoans.createNonPeriodicSummury("np_summury")){
            generateFile("np_summury");
        } else{

            JOptionPane.showMessageDialog(this, "First Close the Opened pdf File");

        }
    }//GEN-LAST:event_jButton49ActionPerformed

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
//            java.util.logging.Logger.getLogger(Reportx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Reportx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Reportx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Reportx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Reportx().setVisible(true);
//            }
//        });
//    }
  private void generateFile(String fileName){
  JOptionPane.showMessageDialog(this, fileName);  
   
     if (Desktop.isDesktopSupported()) {
      try {
          // no application registered for PDFs
          
          File myFile = new File(fios.createFileName("PMMS_Statements", "PMMS_Statements1", fileName+".pdf"));
        
          Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
          Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
      }
     }
    
  }
       private void generateFileExcel(String fileName){
  JOptionPane.showMessageDialog(this, fileName);  
   
     if (Desktop.isDesktopSupported()) {
      try {
          // no application registered for PDFs
          
          File myFile = new File(fileName+".xlsx");
        
          Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
          Logger.getLogger(Reportx.class.getName()).log(Level.SEVERE, null, ex);
      }
     }}
       
 private void generateFileExcelq(String fileName){
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
         private void feelmeSweetSusan(){
             
 jScrollPane7.setVisible(true);
     membersSelectionPanel.setVisible(true);
     jButton129.setVisible(false);
     jComboBox4.setVisible(false);
     dbq.fillMeWithAllAccountsCSpecial(jTable4);
     sortTable(jTable4,jTextField39);   
      HeaderRenderer header = new HeaderRenderer(jTable4.getTableHeader().getDefaultRenderer());   
        
        int h1=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable4.getColumnModel().getColumnCount()){
        jTable4.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable4.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable4.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable4.getColumnModel().getColumn(h1).setPreferredWidth(50);

        }
       
       if(h1==1){
        jTable4.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable4.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable4.getColumnModel().getColumn(h1).setPreferredWidth(270);

        }
       
       if(h1==2){
        jTable4.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable4.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable4.getColumnModel().getColumn(h1).setPreferredWidth(150);

        }
       if(h1==3){
        jTable4.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable4.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable4.getColumnModel().getColumn(h1).setPreferredWidth(130);

        }
        h1++;

        }
        jTable4.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }

        if(col==3){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton158.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton157.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });

}
     private void feelmeSweetMercyMyLove(){
             
  
         jPanel24Table.setVisible(true);
        jPanel25ByLedger.setVisible(false);
        jPanel27All.setVisible(false);
     jLabel90.setVisible(false);
     jComboBox10.setVisible(false);
     dbq.fillMeWithAllAccountsSpec(jTable5);
     sortTable(jTable5,jTextField27);   
    
this.renderTheTable(jTable5, 1);
}        
       
//     private void switchOffRIONew(){
//         
//              StaffMembersPanel.setVisible(false);
//                LoginsPan.setVisible(false);
//                CustomersDemographyPanel.setVisible(false);
//                customerByBalancePanel.setVisible(false);
//                customerBySharesPanel.setVisible(false);
//                customerByContactDetailsPanel.setVisible(false);
//                AssetAccounts.setVisible(false);
//                LiabilitiesAccounts.setVisible(false);
//                EquityAccounts.setVisible(false);
//                IncomeAccounts.setVisible(false);
//                ExpensesAccounts.setVisible(false);
//                OverDrawnPostionAccounts.setVisible(false);   
//                 loanSavingsToday.setVisible(false); 
//                 jPanel19.setVisible(false);  
//                   returnOnInvestmentPanel.setVisible(false);
//                   provisionForBadLoansPanel.setVisible(false); 
//                    membersSelectionPanel.setVisible(true);
//         savingsSummuryPanel.setVisible(false); 
//         sharesSummuryPanel.setVisible(false); 
//         
//         }
//     
     
     
     
     
         private void switchOffRIO(){
         
              StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(false); 
                    membersSelectionPanel.setVisible(true);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
         
         }
         
          private void switchOnRIO(){
         
              StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(true);
                   provisionForBadLoansPanel.setVisible(false); 
                    membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(false); 
         
         }
          
           private void switchOnRIONew(){
          
                 membersSelectionPanel1.setVisible(false); 
                 summuryReportJPanel.setVisible(false);  
                   returnOnInvestmentPanels.setVisible(true);
                  
         }
          
            private void switchOffRIONew(){
          
                 membersSelectionPanel1.setVisible(true); 
                 summuryReportJPanel.setVisible(false);  
                   returnOnInvestmentPanels.setVisible(false);
                  
         }
          
          
          
          
                  
                    private void switchOnShares(){
         
              StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(false); 
                    membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(false); 
         sharesSummuryPanel.setVisible(true); 
         
         }
                    
     private void switchOnSavings(){
         
              StaffMembersPanel.setVisible(false);
                LoginsPan.setVisible(false);
                CustomersDemographyPanel.setVisible(false);
                customerByBalancePanel.setVisible(false);
                customerBySharesPanel.setVisible(false);
                customerByContactDetailsPanel.setVisible(false);
                AssetAccounts.setVisible(false);
                LiabilitiesAccounts.setVisible(false);
                EquityAccounts.setVisible(false);
                IncomeAccounts.setVisible(false);
                ExpensesAccounts.setVisible(false);
                OverDrawnPostionAccounts.setVisible(false);   
                 loanSavingsToday.setVisible(false); 
                 jPanel19.setVisible(false);  
                   returnOnInvestmentPanel.setVisible(false);
                   provisionForBadLoansPanel.setVisible(false); 
                    membersSelectionPanel.setVisible(false);
         savingsSummuryPanel.setVisible(true); 
         sharesSummuryPanel.setVisible(false); 
         
         }      
     
     
     
         private void feelmeNewSweetSusan(){
             
 jScrollPane11.setVisible(true);
     membersSelectionPanel1.setVisible(true);
//     jButton271.setVisible(false);
     jComboBox13.setVisible(false);
     jComboBox12.setVisible(false);
     dbq.fillMeWithAllAccountsCSpecial(jTable6);
     sortTable(jTable6,jTextField77);   
      HeaderRenderer header = new HeaderRenderer(jTable6.getTableHeader().getDefaultRenderer());   
        
        int h1=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<jTable6.getColumnModel().getColumnCount()){
        jTable6.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(50);

        }
       
       if(h1==1){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(270);

        }
       
       if(h1==2){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(150);

        }
       if(h1==3){
        jTable6.getColumnModel().getColumn(h1).setMinWidth(0);
        jTable6.getColumnModel().getColumn(h1).setMaxWidth(700);
        jTable6.getColumnModel().getColumn(h1).setPreferredWidth(130);

        }
        h1++;

        }
        jTable6.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


        if(row==table.getRowCount()-1){
        this.setFont(new Font("Arial",Font.BOLD,20));
        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
        }

        if(col==3){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton158.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton157.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });

}
     
     private void printTable(JTable theTable,String heading) {
        /* Fetch printing properties from the GUI components */

        MessageFormat header = null;
        
        /* if we should print a header */
       
            /* create a MessageFormat around the header text */
            header = new MessageFormat(heading);
        
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
            boolean complete = theTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);

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
     
      public String getCounter(){
          
String thecounter="";
fios.forceFileExistanceHundred(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"));

thecounter =""+(parseInt(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt")))+1);

fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "otherCounters.txt"), thecounter);

return thecounter;
}   
     
        public void initialiseSummuryR(){

 summuryReportDetails=new ArrayList();
  
  } 
      
   public void addTheDetailsOfSummuryReport(String newDetails){

  this.summuryReportDetails.add(newDetails);
  
  }    
   
    public void removeTheDetailsOfSummuryReport(String newDetails){

  this.summuryReportDetails.remove(newDetails);
  
  }
   
    public void resetCounter(){
    counter=1;
    }
    
    public void concatSumReText(){
    
     
        if(!summuryReportDetails.isEmpty()){
    
            for(Object txt:summuryReportDetails){
              
                if(counter==1){
                summuryReportText=txt.toString();
                }else{
                  summuryReportText=summuryReportText.concat(";"); 
                   summuryReportText=summuryReportText.concat(txt.toString());
                }
                
           
           
             counter++;
            }
    
            }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AssetAccounts;
    private javax.swing.JPanel AssetAccounts1;
    private javax.swing.JPanel AssetRegister;
    private javax.swing.JPanel BOGAPanel;
    private javax.swing.JPanel BOGAPanel1;
    private javax.swing.JPanel BOGAPanel2;
    private javax.swing.JPanel BalancesPanel;
    private javax.swing.JPanel BalancesReportSubPanel;
    private javax.swing.JPanel CustomersDemographyPanel;
    private javax.swing.JPanel CustomersDemographyPanel1;
    private javax.swing.JPanel DailyCollections;
    private javax.swing.JPanel DailyIndividualROI;
    private javax.swing.JPanel DailySummuryReport;
    private javax.swing.JPanel EquityAccounts;
    private javax.swing.JPanel EquityAccounts1;
    private javax.swing.JPanel ExpensesAccounts;
    private javax.swing.JPanel ExpensesAccounts1;
    private javax.swing.JPanel FinancialReportsPanel;
    private javax.swing.JPanel GeneralLedgerPanel1;
    private javax.swing.JPanel GroupROI;
    private javax.swing.JPanel GroupSavingsAndShares;
    private javax.swing.JPanel IncomeAccounts;
    private javax.swing.JPanel IncomeAccounts1;
    private javax.swing.JButton JBTrnSqNo3;
    private javax.swing.JButton JBTrnSqNo4;
    private javax.swing.JPanel LiabilitiesAccounts;
    private javax.swing.JPanel LiabilitiesAccounts1;
    private javax.swing.JPanel LoginsPan;
    private javax.swing.JPanel LoginsPan1;
    private javax.swing.JPanel MonthlyIndividualROI;
    private javax.swing.JPanel OtherReportsPanel;
    private javax.swing.JPanel OtherReportsSubPanel;
    private javax.swing.JPanel OverDrawnAccounts1;
    private javax.swing.JPanel OverDrawnPostionAccounts;
    private javax.swing.JPanel QuickSummuryPanel;
    private javax.swing.JPanel StaffMembersPanel;
    private javax.swing.JPanel StaffMembersPanel1;
    private javax.swing.JPanel StaffMembersPanel21;
    private javax.swing.JPanel StaffMembersPanel32;
    private javax.swing.JPanel StaffMembersPanel33;
    private javax.swing.JPanel StaffMembersPanel34;
    private javax.swing.JPanel StaffMembersPanel35;
    private javax.swing.JPanel StaffMembersPanel36;
    private javax.swing.JPanel StaffMembersPanel37;
    private javax.swing.JPanel StaffMembersPanel38;
    private javax.swing.JPanel StaffMembersPanel39;
    private javax.swing.JButton accountStatement;
    private javax.swing.JButton accountStatement1;
    private javax.swing.JButton accountStatement2;
    private javax.swing.JButton accountStatement3;
    private javax.swing.JPanel accountStatementPanal;
    private javax.swing.JPanel accountStatementPanal1;
    private javax.swing.JTable accountTable;
    private javax.swing.JFormattedTextField amountFieldDebit2;
    private javax.swing.JButton assetsButto1;
    private javax.swing.JButton assetsButto2;
    private javax.swing.JButton assetsButto3;
    private javax.swing.JButton balanceSheetButton;
    private javax.swing.JButton balanceSheetButton1;
    private javax.swing.JButton balanceSheetButton10;
    private javax.swing.JButton balanceSheetButton2;
    private javax.swing.JButton balanceSheetButton3;
    private javax.swing.JButton balanceSheetButton4;
    private javax.swing.JButton balanceSheetButton5;
    private javax.swing.JButton balanceSheetButton6;
    private javax.swing.JButton balanceSheetButton7;
    private javax.swing.JButton balanceSheetButton8;
    private javax.swing.JButton balanceSheetButton9;
    private javax.swing.JPanel balanceSheetPanel;
    private javax.swing.JButton bogaStatementButton;
    private javax.swing.JButton bogaStatementButton1;
    private javax.swing.JPanel budgetVarianceAnalysis;
    private javax.swing.JButton cashFlowButton;
    private javax.swing.JButton cashFlowButton1;
    private javax.swing.JButton cashFlowButton2;
    private javax.swing.JButton cashFlowButton3;
    private javax.swing.JButton cashFlowButton4;
    private javax.swing.JButton cashFlowButton5;
    private javax.swing.JButton cashFlowButton6;
    private javax.swing.JPanel currentAssetsAccounts1;
    private javax.swing.JPanel currentLiabilitiesAccounts1;
    private javax.swing.JPanel customerAccounts1;
    private javax.swing.JPanel customerByBalance1;
    private javax.swing.JPanel customerByBalancePanel;
    private javax.swing.JPanel customerByContactDetailsPanel;
    private javax.swing.JPanel customerBySharesPanel;
    private javax.swing.JFormattedTextField debitAccountField1;
    private javax.swing.JFormattedTextField debitAccountField2;
    private javax.swing.JFormattedTextField debitAccountField3;
    private javax.swing.JPanel financialSupportPanel;
    private javax.swing.JPanel financialSupportPanel1;
    private javax.swing.JPanel generalLedgerPanel2;
    private javax.swing.JPanel groupSavingsPanel;
    private javax.swing.JButton incomeStatementButton;
    private javax.swing.JButton incomeStatementButton1;
    private javax.swing.JButton incomeStatementButton2;
    private javax.swing.JButton incomeStatementButton3;
    private javax.swing.JPanel individualSavings;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton100;
    private javax.swing.JButton jButton101;
    private javax.swing.JButton jButton102;
    private javax.swing.JButton jButton103;
    private javax.swing.JButton jButton104;
    private javax.swing.JButton jButton105;
    private javax.swing.JButton jButton107;
    private javax.swing.JButton jButton108;
    private javax.swing.JButton jButton109;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton110;
    private javax.swing.JButton jButton111;
    private javax.swing.JButton jButton112;
    private javax.swing.JButton jButton113;
    private javax.swing.JButton jButton114;
    private javax.swing.JButton jButton115;
    private javax.swing.JButton jButton116;
    private javax.swing.JButton jButton117;
    private javax.swing.JButton jButton118;
    private javax.swing.JButton jButton119;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton120;
    private javax.swing.JButton jButton121;
    private javax.swing.JButton jButton122;
    private javax.swing.JButton jButton123;
    private javax.swing.JButton jButton124;
    private javax.swing.JButton jButton126;
    private javax.swing.JButton jButton127;
    private javax.swing.JButton jButton129;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton130;
    private javax.swing.JButton jButton131;
    private javax.swing.JButton jButton132;
    private javax.swing.JButton jButton133;
    private javax.swing.JButton jButton134;
    private javax.swing.JButton jButton135;
    private javax.swing.JButton jButton136;
    private javax.swing.JButton jButton137;
    private javax.swing.JButton jButton138;
    private javax.swing.JButton jButton139;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton140;
    private javax.swing.JButton jButton141;
    private javax.swing.JButton jButton142;
    private javax.swing.JButton jButton143;
    private javax.swing.JButton jButton144;
    private javax.swing.JButton jButton145;
    private javax.swing.JButton jButton146;
    private javax.swing.JButton jButton147;
    private javax.swing.JButton jButton148;
    private javax.swing.JButton jButton149;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton150;
    private javax.swing.JButton jButton151;
    private javax.swing.JButton jButton152;
    private javax.swing.JButton jButton153;
    private javax.swing.JButton jButton154;
    private javax.swing.JButton jButton155;
    private javax.swing.JButton jButton156;
    private javax.swing.JButton jButton157;
    private javax.swing.JButton jButton158;
    private javax.swing.JButton jButton159;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton160;
    private javax.swing.JButton jButton161;
    private javax.swing.JButton jButton162;
    private javax.swing.JButton jButton163;
    private javax.swing.JButton jButton164;
    private javax.swing.JButton jButton165;
    private javax.swing.JButton jButton166;
    private javax.swing.JButton jButton167;
    private javax.swing.JButton jButton168;
    private javax.swing.JButton jButton169;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton170;
    private javax.swing.JButton jButton171;
    private javax.swing.JButton jButton172;
    private javax.swing.JButton jButton173;
    private javax.swing.JButton jButton174;
    private javax.swing.JButton jButton175;
    private javax.swing.JButton jButton176;
    private javax.swing.JButton jButton177;
    private javax.swing.JButton jButton178;
    private javax.swing.JButton jButton179;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton180;
    private javax.swing.JButton jButton181;
    private javax.swing.JButton jButton182;
    private javax.swing.JButton jButton183;
    private javax.swing.JButton jButton184;
    private javax.swing.JButton jButton185;
    private javax.swing.JButton jButton186;
    private javax.swing.JButton jButton187;
    private javax.swing.JButton jButton188;
    private javax.swing.JButton jButton189;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton190;
    private javax.swing.JButton jButton191;
    private javax.swing.JButton jButton192;
    private javax.swing.JButton jButton193;
    private javax.swing.JButton jButton194;
    private javax.swing.JButton jButton195;
    private javax.swing.JButton jButton196;
    private javax.swing.JButton jButton197;
    private javax.swing.JButton jButton198;
    private javax.swing.JButton jButton199;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton200;
    private javax.swing.JButton jButton201;
    private javax.swing.JButton jButton202;
    private javax.swing.JButton jButton203;
    private javax.swing.JButton jButton204;
    private javax.swing.JButton jButton205;
    private javax.swing.JButton jButton206;
    private javax.swing.JButton jButton207;
    private javax.swing.JButton jButton208;
    private javax.swing.JButton jButton209;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton215;
    private javax.swing.JButton jButton216;
    private javax.swing.JButton jButton217;
    private javax.swing.JButton jButton218;
    private javax.swing.JButton jButton219;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton220;
    private javax.swing.JButton jButton221;
    private javax.swing.JButton jButton222;
    private javax.swing.JButton jButton223;
    private javax.swing.JButton jButton224;
    private javax.swing.JButton jButton225;
    private javax.swing.JButton jButton226;
    private javax.swing.JButton jButton227;
    private javax.swing.JButton jButton228;
    private javax.swing.JButton jButton229;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton230;
    private javax.swing.JButton jButton231;
    private javax.swing.JButton jButton232;
    private javax.swing.JButton jButton233;
    private javax.swing.JButton jButton234;
    private javax.swing.JButton jButton235;
    private javax.swing.JButton jButton236;
    private javax.swing.JButton jButton237;
    private javax.swing.JButton jButton238;
    private javax.swing.JButton jButton239;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton240;
    private javax.swing.JButton jButton241;
    private javax.swing.JButton jButton242;
    private javax.swing.JButton jButton243;
    private javax.swing.JButton jButton244;
    private javax.swing.JButton jButton245;
    private javax.swing.JButton jButton246;
    private javax.swing.JButton jButton247;
    private javax.swing.JButton jButton248;
    private javax.swing.JButton jButton249;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton250;
    private javax.swing.JButton jButton251;
    private javax.swing.JButton jButton252;
    private javax.swing.JButton jButton253;
    private javax.swing.JButton jButton254;
    private javax.swing.JButton jButton255;
    private javax.swing.JButton jButton256;
    private javax.swing.JButton jButton257;
    private javax.swing.JButton jButton258;
    private javax.swing.JButton jButton259;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton260;
    private javax.swing.JButton jButton261;
    private javax.swing.JButton jButton262;
    private javax.swing.JButton jButton263;
    private javax.swing.JButton jButton264;
    private javax.swing.JButton jButton265;
    private javax.swing.JButton jButton266;
    private javax.swing.JButton jButton267;
    private javax.swing.JButton jButton268;
    private javax.swing.JButton jButton269;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton270;
    private javax.swing.JButton jButton271;
    private javax.swing.JButton jButton272;
    private javax.swing.JButton jButton273;
    private javax.swing.JButton jButton274;
    private javax.swing.JButton jButton275;
    private javax.swing.JButton jButton276;
    private javax.swing.JButton jButton277;
    private javax.swing.JButton jButton278;
    private javax.swing.JButton jButton279;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton280;
    private javax.swing.JButton jButton281;
    private javax.swing.JButton jButton282;
    private javax.swing.JButton jButton283;
    private javax.swing.JButton jButton284;
    private javax.swing.JButton jButton285;
    private javax.swing.JButton jButton286;
    private javax.swing.JButton jButton287;
    private javax.swing.JButton jButton288;
    private javax.swing.JButton jButton289;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton290;
    private javax.swing.JButton jButton291;
    private javax.swing.JButton jButton292;
    private javax.swing.JButton jButton293;
    private javax.swing.JButton jButton294;
    private javax.swing.JButton jButton295;
    private javax.swing.JButton jButton296;
    private javax.swing.JButton jButton297;
    private javax.swing.JButton jButton298;
    private javax.swing.JButton jButton299;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton300;
    private javax.swing.JButton jButton301;
    private javax.swing.JButton jButton302;
    private javax.swing.JButton jButton303;
    private javax.swing.JButton jButton304;
    private javax.swing.JButton jButton305;
    private javax.swing.JButton jButton306;
    private javax.swing.JButton jButton307;
    private javax.swing.JButton jButton308;
    private javax.swing.JButton jButton309;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton310;
    private javax.swing.JButton jButton311;
    private javax.swing.JButton jButton312;
    private javax.swing.JButton jButton313;
    private javax.swing.JButton jButton314;
    private javax.swing.JButton jButton315;
    private javax.swing.JButton jButton316;
    private javax.swing.JButton jButton317;
    private javax.swing.JButton jButton318;
    private javax.swing.JButton jButton319;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton320;
    private javax.swing.JButton jButton321;
    private javax.swing.JButton jButton322;
    private javax.swing.JButton jButton323;
    private javax.swing.JButton jButton324;
    private javax.swing.JButton jButton325;
    private javax.swing.JButton jButton326;
    private javax.swing.JButton jButton327;
    private javax.swing.JButton jButton328;
    private javax.swing.JButton jButton329;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton330;
    private javax.swing.JButton jButton331;
    private javax.swing.JButton jButton332;
    private javax.swing.JButton jButton333;
    private javax.swing.JButton jButton334;
    private javax.swing.JButton jButton335;
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
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton79;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton80;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JButton jButton83;
    private javax.swing.JButton jButton84;
    private javax.swing.JButton jButton85;
    private javax.swing.JButton jButton86;
    private javax.swing.JButton jButton88;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton90;
    private javax.swing.JButton jButton91;
    private javax.swing.JButton jButton92;
    private javax.swing.JButton jButton93;
    private javax.swing.JButton jButton94;
    private javax.swing.JButton jButton95;
    private javax.swing.JButton jButton96;
    private javax.swing.JButton jButton97;
    private javax.swing.JButton jButton98;
    private javax.swing.JButton jButton99;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
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
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel24Table;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel25ByLedger;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel27All;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel41SpecificSummuryReport;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTree jTree2;
    private javax.swing.JTree jTree3;
    private javax.swing.JButton liabilitiesButton1;
    private javax.swing.JButton liabilitiesButton3;
    private javax.swing.JButton loanAppliedButton;
    private javax.swing.JButton loanApprovedButton;
    private javax.swing.JPanel loanDisbursementSchedule;
    private javax.swing.JPanel loanPayments;
    private javax.swing.JButton loanPortFolioButton;
    private javax.swing.JPanel loanPortFolioPanel;
    private javax.swing.JPanel loanReportsPanel;
    private javax.swing.JPanel loanReportsSubPanel;
    private javax.swing.JPanel loanSavingsToday;
    private javax.swing.JButton loanStatement;
    private javax.swing.JPanel loanStatementPanel;
    private javax.swing.JPanel loansAppliedPanel;
    private javax.swing.JPanel loansApprovedPanel;
    private javax.swing.JPanel loansCompletedPanel;
    private javax.swing.JPanel loansDisbursedPanel;
    private javax.swing.JPanel loansDisbursedPanel1;
    private javax.swing.JPanel loansDisbursedPanel2;
    private javax.swing.JPanel loansDue;
    private javax.swing.JPanel loansDue1;
    private javax.swing.JPanel loansDueForWriteOff;
    private javax.swing.JPanel loansInArrears;
    private javax.swing.JButton loansInArrearsButton;
    private javax.swing.JPanel loansToStaffMembers;
    private javax.swing.JPanel membersSelectionPanel;
    private javax.swing.JPanel membersSelectionPanel1;
    private javax.swing.JPanel nonCurrentAssetsAccounts1;
    private javax.swing.JPanel nonCurrentLiablitiesAccounts1;
    private javax.swing.JPanel pAndLPanel;
    private javax.swing.JPanel portfolioAtRisk;
    private javax.swing.JPanel provisionForBadLoansPanel;
    private javax.swing.JTable quickSumTable;
    private javax.swing.JPanel returnOnInvestmentPanel;
    private javax.swing.JPanel returnOnInvestmentPanels;
    private javax.swing.JButton returnOnInvestmentsButton;
    private javax.swing.JButton returnOnInvestmentsButton1;
    private javax.swing.JPanel savingsContributions;
    private javax.swing.JPanel savingsSummuryPanel;
    private javax.swing.JPanel sharesSummuryPanel;
    private javax.swing.JPanel summuryOnLoans;
    private javax.swing.JPanel summuryReportJPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo1;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo2;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo3;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo4;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo5;
    private javax.swing.JComboBox<String> totalLoanSavingsCombo6;
    private javax.swing.JPanel totalLoanShares;
    private javax.swing.JPanel totalLoanShares1;
    private javax.swing.JPanel totalLoanShares2;
    private javax.swing.JPanel totalLoanShares3;
    private javax.swing.JPanel totalLoanShares4;
    private javax.swing.JButton trialBalanceButton;
    private javax.swing.JButton trialBalanceButton1;
    private javax.swing.JButton trialBalanceButton2;
    private javax.swing.JButton trialBalanceButton3;
    private javax.swing.JPanel trialBalancePanel1;
    private javax.swing.JTextField valueDate;
    private javax.swing.JTextField valueDate1;
    private javax.swing.JTextField valueDate12;
    private javax.swing.JTextField valueDate13;
    private javax.swing.JTextField valueDate14;
    private javax.swing.JTextField valueDate8;
    private javax.swing.JTextField valueDate9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateText(String text) {
     switch( fios.intFileReader(fios.createFileName("PMMS_Statements", "reports", "calSelection.txt"))){ 
                case 101:valueDate8.setText(text);break;    
                case 102:valueDate9.setText(text);break; 
                case 106:valueDate.setText(text);break;   
                case 105:valueDate1.setText(text);break;   
//                case 107:valueDate10.setText(text);break;    
//                case 108:valueDate11.setText(text);break;    
                case 110:valueDate12.setText(text);break;    
//                case 310:jTextField2.setText(text);break; 
//                case 311:jTextField1.setText(text);break;   
                case 115:valueDate13.setText(text);break;   
                case 400 :jTextField13.setText(text);break;   
                case 410 :jTextField9.setText(text);break;    
//                case 420 :jTextField8.setText(text);break;   
//                case 430 :jTextField10.setText(text);break;      
                case 440 :jTextField11.setText(text);break;  
                case 450 :jTextField6.setText(text);break;    
                case 470 :jTextField5.setText(text);break; 
                case 480 :jTextField3.setText(text);break;   
                case 490 :jTextField7.setText(text);break;   
                case 500 :jTextField4.setText(text);break;    
                case 510 :jTextField16.setText(text);break;   
                case 520 :jTextField15.setText(text);break; 
                case 530 :jTextField22.setText(text);break;   
                case 540 :jTextField21.setText(text);break;
                case 550 :jTextField20.setText(text);break;   
                case 560 :jTextField19.setText(text);break;
                case 570 :jTextField12.setText(text);break;   
                case 580 :jTextField14.setText(text);break;
                case 610 :valueDate14.setText(text);break;
                case 620 :jTextField23.setText(text);break;
                case 630 :jTextField18.setText(text);break;
                case 670 :jTextField24.setText(text);break;
                case 680 :jTextField25.setText(text);break;
                case 685 :jTextField52.setText(text);break;
                case 686 :jTextField51.setText(text);break;
                case 687 :jTextField38.setText(text);break;
                case 688 :jTextField28.setText(text);break;
                case 689 :jTextField31.setText(text);break;
                case 690 :jTextField30.setText(text);break;
                case 691 :jTextField33.setText(text);break;
                case 692 :jTextField32.setText(text);break;
                case 693 :jTextField41.setText(text);break;
                case 694 :jTextField40.setText(text);break;
                case 695 :jTextField35.setText(text);break;
                case 696 :jTextField34.setText(text);break;
                case 697:jTextField37.setText(text);break;
                case 698:jTextField36.setText(text);break;
                case 700:jTextField45.setText(text);break;
                case 701:jTextField46.setText(text);break;
                case 702:jTextField44.setText(text);break;
                case 703:jTextField43.setText(text);break;
                  case 704:jTextField48.setText(text);break;
                case 705:jTextField49.setText(text);break;
                
                
                  case 706:jTextField56.setText(text);break;
                case 707:jTextField54.setText(text);break;
                  case 708:jTextField55.setText(text);break;
                case 709:jTextField26.setText(text);break;
              
                  case 801:jTextField57.setText(text);break;
                  case 802:jTextField60.setText(text);break;
                  case 803:jTextField59.setText(text);break;
                  
                  
                   case 804:jTextField64.setText(text);break;
                  case 805:jTextField63.setText(text);break;
                  
                   case 806:jTextField68.setText(text);break;
                    case 807:jTextField67.setText(text);break;
                     case 808:jTextField76.setText(text);break;
                      case 809:jTextField74.setText(text);break;
//                       case 900:jButton269.setText(text);break;
           
                      
                       case 900:jTextField65.setText(text);break;
                  case 901:jTextField66.setText(text);break;
                  case 902:jTextField78.setText(text);break;
                  
                  
                   case 903:jTextField79.setText(text);break;
                  case 904:jTextField80.setText(text);break;
                  
                   case 905:jTextField85.setText(text);break;
                    case 906:jTextField84.setText(text);break;
                     case 907:jTextField83.setText(text);break;
                      case 908:jTextField82.setText(text);break;
                      
                        case 909:jTextField81.setText(text);break;
                     case 910:jTextField83.setText(text);break;
                      case 911:jTextField87.setText(text);break;
                       case 912:jTextField86.setText(text);break;
                         case 913:jTextField88.setText(text);break;
                           case 914:jTextField92.setText(text);break;
                         case 915:jTextField91.setText(text);break;
                     
                    
                
     }
    
    } 
    
    

    @Override
    public void mouseClicked(MouseEvent me) {
    
        if (me.getClickCount()==2){
    
    if(me.getSource()==jTree2){
    DefaultMutableTreeNode node1 = (DefaultMutableTreeNode)jTree2.getLastSelectedPathComponent();
   if(node1.isLeaf()==true){
    String window1 =node1.toString();
    generalLedgerPanel2.setVisible(false);
     GeneralLedgerPanel1.setVisible(true);
    quary.populateMaster(jTable3,window1);
   }
    }
     if(me.getSource()==jTree3){
    DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)jTree3.getLastSelectedPathComponent();
     if(node2.isLeaf()==true){
  String window2 =node2.toString();
     generalLedgerPanel2.setVisible(false);
     GeneralLedgerPanel1.setVisible(true);
    quary.populateMaster(jTable3,window2);
     }
     }
     if(me.getSource()==jTable3){
     int selectedRow =jTable3.getSelectedRow();
                    int selectedColumn =jTable3.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable3.getModel().getValueAt(jTable3.convertRowIndexToModel(selectedRow), 1);
          
          String accountNumber = cvalue.toString();
          
        quary.populateAccounts(jTable3,accountNumber);
     }
    
    }
     
     if(me.getSource()==jTable6){
          
     int selectedRow =jTable6.getSelectedRow();
                    int selectedColumn =jTable6.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable6.getModel().getValueAt(jTable6.convertRowIndexToModel(selectedRow), 1);
         
          Object cvalue1 = jTable6.getModel().getValueAt(jTable6.convertRowIndexToModel(selectedRow), 2); 
//           fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "suzanlove");
         theAccountcn = cvalue1.toString();
           if(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercyMylove1")){
        jTextField75.setText(cvalue.toString());  
         jScrollPane11.setVisible(false);
     membersSelectionPanel1.setVisible(false);
//     jButton271.setVisible(true);
     jComboBox13.setVisible(true);
      jComboBox12.setVisible(true);

     switchOnRIONew();
         }else if(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercyMylove2")){
           jTextField71.setText(cvalue.toString());  
         jScrollPane11.setVisible(false);
     membersSelectionPanel1.setVisible(false);
//     jButton271.setVisible(true);
     jComboBox13.setVisible(true);
      jComboBox12.setVisible(true);
//     totalLoanShares3.setVisible(true);
       switchOnRIONew();   
         
         } 
           }}
     
     
     
     
      if(me.getSource()==jTable4){
          
     int selectedRow =jTable4.getSelectedRow();
                    int selectedColumn =jTable4.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable4.getModel().getValueAt(jTable4.convertRowIndexToModel(selectedRow), 1);
         
          Object cvalue1 = jTable4.getModel().getValueAt(jTable4.convertRowIndexToModel(selectedRow), 2); 
//           fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "suzanlove");
         theAccount = cvalue1.toString();
           if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("susanmylove")){
        jTextField42.setText(cvalue.toString());  
         jScrollPane7.setVisible(false);
     membersSelectionPanel.setVisible(false);
     jButton129.setVisible(true);
     jComboBox4.setVisible(true);
     totalLoanShares3.setVisible(true);
     switchOnRIO();
         }  
         if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("suzanlove")){
        jTextField29.setText(cvalue.toString());
        
         jScrollPane7.setVisible(false);
     membersSelectionPanel.setVisible(false);
     jButton129.setVisible(true);
     jComboBox4.setVisible(true);
     totalLoanShares1.setVisible(true);
     switchOnRIO();
         }
        
       if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercylove")){
           
        jTextField47.setText(cvalue.toString());
       
     switchOnShares();
         }  
          if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercyMylove")){
           
        jTextField50.setText(cvalue.toString());
       
     switchOnSavings();
         } 
     }
    
    }
     if(me.getSource()==jTable5){
          
     int selectedRow =jTable5.getSelectedRow();
                    int selectedColumn =jTable5.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable5.getModel().getValueAt(jTable4.convertRowIndexToModel(selectedRow), 1);
         
          Object cvalue1 = jTable5.getModel().getValueAt(jTable4.convertRowIndexToModel(selectedRow), 2); 
//           fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt"), "suzanlove");
         theAccount = cvalue1.toString();
//           if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("susanmylove")){
        jTextField53.setText(cvalue.toString());  
         jPanel24Table.setVisible(false);
          jPanel27All.setVisible(false);
     jPanel25ByLedger.setVisible(true);
     jLabel90.setVisible(true);
     jComboBox10.setVisible(true);
//     totalLoanShares3.setVisible(true);
//     switchOnRIO();
//         }  
//         if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("suzanlove")){
//        jTextField29.setText(cvalue.toString());
//        
//         jScrollPane7.setVisible(false);
//     membersSelectionPanel.setVisible(false);
//     jButton129.setVisible(true);
//     jComboBox4.setVisible(true);
//     totalLoanShares1.setVisible(true);
//     switchOnRIO();
//         }
        
//       if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercylove")){
//           
//        jTextField47.setText(cvalue.toString());
//       
//     switchOnShares();
//         }  
//          if( fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "sweetieSusanLove.txt")).equalsIgnoreCase("mercyMylove")){
//           
//        jTextField50.setText(cvalue.toString());
//       
//     switchOnSavings();
//         } 
     }
    
    }  
     
     
     
      if(me.getSource() == jTable1){  
                
                   String accountNumber="";
               
                    int selectedRow =jTable1.getSelectedRow();
                    int selectedColumn =jTable1.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	 
         Object cvalue = jTable1.getModel().getValueAt( jTable1.convertRowIndexToModel(selectedRow), 2);
         
         accountNumber = cvalue.toString();
                   }
            
               if(ancdb.testTable("BSANCA"+accountNumber)==true){
          if(   fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "loanArrearsWindow.txt"))==27){
  debitAccountField2.setValue(form.putSeparatorsOnNormaAccount(accountNumber));
  loanStatementPanel  .setVisible(true); 
 loan.loanManagementTableFil(jTable2,accountNumber);
  jPanel5.setVisible(true);
  tablePanel.setVisible(false); 
          }else if(  fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "loanArrearsWindow.txt"))==26){
         debitAccountField3 .setValue(form.putSeparatorsOnNormaAccount(accountNumber));
          loansInArrears .setVisible(true); 
                  tablePanel.setVisible(false); 
                  jButton67.setEnabled(false);
          }
           }else{
           
            JOptionPane.showMessageDialog(rootPane, "ACCOUNT NOT ACTIVE");  
           
           }
   

           if(jCheckBox2.isSelected()){
            jPanel5.setVisible(false);
              }
        
     }
      
       if(me.getSource() == accountTable){  
                
                   String accountNumber="";
               
                    int selectedRow =accountTable.getSelectedRow();
                    int selectedColumn =accountTable.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	 
         Object cvalue = accountTable.getModel().getValueAt( accountTable.convertRowIndexToModel(selectedRow), 2);
         
         accountNumber = cvalue.toString();
                   }
            if(accountNumber.isEmpty()){
            
              Reportx f = new Reportx(userId);
        f.setVisible(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screen.getSize());
        f.pack();
        this.dispose(); 
            
            }else{
               if(ancdb.testTable("BSANCA"+accountNumber)==true){
          
  debitAccountField1.setValue(form.putSeparatorsOnNormaAccount(accountNumber));
           }else{
           
                   
                   
            JOptionPane.showMessageDialog(rootPane, "ACCOUNT NOT ACTIVE");  
           
           }

            }
   
            
         
       accountStatementPanal1.setVisible(false);
           accountStatementPanal.setVisible(true);           
           

           
        
     }
        }
    
    
    
    
    }   
        
              
    

    @Override
    public void mousePressed(MouseEvent me) {
    if(me.getSource() ==jTable2){
    
        
           String loanID="";
               
                    int selectedRow =jTable2.getSelectedRow();
                    int selectedColumn =jTable2.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	 
         Object cvalue = jTable2.getModel().getValueAt( jTable2.convertRowIndexToModel(selectedRow), 1);
         
         loanID = cvalue.toString();
                   }
            
       fios.stringFileWriter(fios.createFileName("loanApplication", "amortValues", "loanid.txt"), loanID);
    
   
    }else if(me.getSource() ==jTable7){
    
    
         int selectedRow =jTable7.getSelectedRow();
                    int selectedColumn =jTable7.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1){
	   
	 
         Object cvalue = jTable7.getModel().getValueAt( jTable7.convertRowIndexToModel(selectedRow), 1);
         
         theMlAccount = cvalue.toString();
                   }
    }
       if(me.getSource()==jTable8){
     int selectedRow =jTable8.getSelectedRow();
                    int selectedColumn =jTable8.getSelectedColumn();
		   if (selectedRow > -1&&selectedColumn>-1)
	   {
	 
         Object cvalue = jTable8.getModel().getValueAt(jTable8.convertRowIndexToModel(selectedRow), 0);
          
           idNow = cvalue.toString();
          
        jButton322.setEnabled(true);
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
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
// if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//      switch(jComboBox1.getSelectedItem().toString()){
//            case "Member Accounts":
////                dbq.fillMeWithAllAccountsC(accountTable);
//                this.sortTable(accountTable, jTextField17);
//                break;   
//            case "Asset Accounts":
////                  dbq.fillMeWithAllAccountsAsset(accountTable);
//                  this.sortTable(accountTable, jTextField17);
//                break;  
//            case "Liabilities Accounts":
////                 dbq.fillMeWithAllAccountsLiabilities(accountTable);
//                 this.sortTable(accountTable, jTextField17);
//                break;  
//            case "Expenses Accounts":
//              
////                    dbq.fillMeWithAllAccountsExpenses(accountTable);
//                    this.sortTable(accountTable, jTextField17);
//                
//                break;  
//            case "Equity Accounts":
//                   
////                    dbq.fillMeWithAllAccountsEquity(accountTable);
//                    this.sortTable(accountTable, jTextField17);
//                break;  
//            case "Revenue Accounts":
////                   dbq.fillMeWithAllAccountsRevenue(accountTable);
//                   this.sortTable(accountTable, jTextField17);
//                
//                break;  
//           
//            default:
//                
//// dbq.fillMeWithAllAccountsC(accountTable);
// this.sortTable(accountTable, jTextField17);
//    }
// }
    }
    
    
    private void renderTheTable(JTable theTable, int cat){
    
    switch(cat){
    
        case 1:
            
            
              HeaderRenderer header = new HeaderRenderer(theTable.getTableHeader().getDefaultRenderer());   
        
        int h1=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(h1).setHeaderRenderer(header);

        if(h1==0){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(50);

        }
       
       if(h1==1){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(270);

        }
       
       if(h1==2){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(150);

        }
       if(h1==3){
        theTable.getColumnModel().getColumn(h1).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1).setPreferredWidth(130);

        }
        h1++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


      
        this.setFont(new Font("Arial",Font.PLAIN,18));

        if(col==3){
        Number c = (Number)parseDouble(value.toString());
        String text = NumberFormat.format(c );
        this.setText(text);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton158.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton157.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            
            
            
            
            break;
    
        case 2:
          
              HeaderRenderer headerx = new HeaderRenderer(theTable.getTableHeader().getDefaultRenderer());   
        
        int h1x=0;


//        jTable3.getColumnModel().getColumns().nextElement().setResizable(false);
        while(h1x<theTable.getColumnModel().getColumnCount()){
        theTable.getColumnModel().getColumn(h1x).setHeaderRenderer(headerx);

        if(h1x==0){
        theTable.getColumnModel().getColumn(h1x).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1x).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1x).setPreferredWidth(60);

        }
       
       if(h1x==1){
        theTable.getColumnModel().getColumn(h1x).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1x).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1x).setPreferredWidth(200);

        }
       
       if(h1x==2){
        theTable.getColumnModel().getColumn(h1x).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1x).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1x).setPreferredWidth(130);

        }
       if(h1x==3){
        theTable.getColumnModel().getColumn(h1x).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1x).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1x).setPreferredWidth(150);

        }
        if(h1x==4){
        theTable.getColumnModel().getColumn(h1x).setMinWidth(0);
        theTable.getColumnModel().getColumn(h1x).setMaxWidth(700);
        theTable.getColumnModel().getColumn(h1x).setPreferredWidth(130);

        }
        h1x++;

        }
        theTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        this.setHorizontalAlignment(JLabel.LEFT);


//        if(row==table.getRowCount()-1){
//        this.setFont(new Font("Arial",Font.BOLD,20));
//        }else{
        this.setFont(new Font("Arial",Font.PLAIN,18));
//        }

        if(col==4){
              String text=""; 
             this.setHorizontalAlignment(JLabel.RIGHT);
        Double g=    parseDouble(value.toString().replace(",", "")); 
        Number c = (Number)parseDouble(value.toString().replace(",", ""));
        
//        if(g<0.0){
//        
//         text = "("+NumberFormat.format(c )+")";
//        }else{
       text = NumberFormat.format(c );
//        }
        this.setText(text);
        }else{
        this.setText(value.toString());
        }
        if (row%2==0) {
        setBackground(jButton158.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        } else {
        setBackground(jButton157.getBackground());
        setForeground(jButton156.getBackground());
        if(isSelected){setBackground(Color.CYAN);}
        }  


        return this;
        }   
        });
            
              
            
            
            
            
            
            break;
    
    
    
    
    }
    
    
    
    }
    
    
}

