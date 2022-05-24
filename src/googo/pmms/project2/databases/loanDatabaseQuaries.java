/*
    * To change this license header, choose License Headers in Project Properties.
    * To change this template file, choose Tools | Templates
    * and open the template in the editor.
    */
    package googo.pmms.project2.databases;
 

    import googo.pmms.project2.databaseConnectors.JdbcConnector;
    import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.PostingMain;
    import googo.pmms.project2.accountsHelper.camputationMath;
    import googo.pmms.project2.frameHelper.MyComboBoxModel;
    import googo.pmms.project2.frameHelper.ObjectTableModel;
    import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
    import googo.pmms.project2.databaseConnectors.JdbcConnector1;
    import googo.pmms.project2.frameHelper.ListTableModel;
import googo.pmms.project2.frameHelper.groupSummuryModel;
import googo.pmms.project2.frameHelper.loanAmortDetailsModel;
import googo.pmms.project2.frameHelper.loanAmortDetailsModelb;
import googo.pmms.project2.frameHelper.loanManagementModel;
import googo.pmms.project2.frameHelper.loanManagementModel1;
import googo.pmms.project2.frames.PostingEntryWindow;
import java.awt.Component;
    import static java.lang.Double.parseDouble;
    import static java.lang.Integer.parseInt;
    import java.sql.Connection;
    import java.sql.DatabaseMetaData;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.text.DecimalFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.GregorianCalendar;
import java.util.HashMap;
    import java.util.Map;
    import javax.swing.JComboBox;
import javax.swing.JOptionPane;
    import javax.swing.JTable;
import javax.swing.SwingWorker;
    import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mortbay.log.Log;


    /**
    *
    * @author Stanchart
    */
    public class loanDatabaseQuaries {
    SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfS =new SimpleDateFormat("yyyy-MM-dd");
    camputationMath compute =new camputationMath(); 

    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    GregorianCalendar cal = new GregorianCalendar(); 

    ArrayList<ArrayList<String>> data7;

    Formartter fmt = new Formartter();

    DatabaseMetaData metadata = null;
    ArrayList<String> data8, column1,data;
    ArrayList<String> data3;
    ArrayList<ArrayList<String>> data6;
    ArrayList<Object> data4;
    DecimalFormat df2 = new DecimalFormat("#");

    DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    ArrayList<ArrayList<Object>> data5;
    MyComboBoxModel modelcombo,modelcombo1;
    ObjectTableModel  model;
loanManagementModel1 mod;
    DatabaseQuaries dbq= new DatabaseQuaries();

    JdbcConnector quaryObj = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    JdbcConnector1 loancon= new   JdbcConnector1(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "LoanDetails.txt")));
      String userId;
      
//      String GroupId=this.groupNumber();
      
    public synchronized void setUserID(String userid){
this.userId=userid;
}
    
     public  synchronized void updateLoanStoreAll(String userID, String userAccount, String Status,String loanId,String trnId){

    if(checkIfAccountClosed(userAccount)==false){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
  
    String UpdateQuary3 = "UPDATE new_loan_appstore SET trn_date=?, authoriser_id=?,loan_cycle_status=?,loan_id=?"
    +"WHERE (applicant_account_number=? AND  trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary3)) {
    pstool.setObject(1, getDBCurrentDate());
    pstool.setObject(2, userID);
    pstool.setObject(3, Status);
      pstool.setObject(4, loanId);
    pstool.setObject(5, userAccount);
    pstool.setObject(6, trnId);
    pstool.execute();
    }      

    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
  
    String UpdateQuary31 = "UPDATE new_loan_appstore1 SET trn_date=?, authoriser_id=?,loan_cycle_status=?,loan_id=?"
    +"WHERE (applicant_account_number=? AND  trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary31)) {
    pstool.setObject(1, getDBCurrentDate());
    pstool.setObject(2, userID);
    pstool.setObject(3, Status);
      pstool.setObject(4, loanId);
    pstool.setObject(5, userAccount);
    pstool.setObject(6, trnId);
    pstool.execute();
    }      

    cq.setAutoCommit(true);
    
    cq.setAutoCommit(false); 
  
   String theProcedure ="CALL createTheIndividualMethod("+"'"+loanId+"'"+")";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();

    cq.setAutoCommit(true);
    
    
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }    
    }
     
     public void createTheIndividualM(String loanId){
     


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
  
    

  
   String theProcedure ="CALL createTheIndividualMethod("+"'"+loanId+"'"+")";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();

    cq.setAutoCommit(true);
    
    
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
     
     }
     
     public void loanDetailsForGroup(JTable table,String groupType,String SelectCri,Component c){
     
int n=1; 
 List<List> data5x= new ArrayList<>();
List loanDetails=null;

//JOptionPane.showConfirmDialog(c, selectCri);
           
      try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          String query="";
          switch(SelectCri){
              case "ALL":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupName="+"'"+groupType+"'";
         break;
                case "DISBURSED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupName="+"'"+groupType+"' AND loan_cycle_status='Disbursed'";
         break;
               case "COMPLETED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupName="+"'"+groupType+"' AND loan_cycle_status='Completed'";
         break;
               case "APPLIED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupName="+"'"+groupType+"' AND loan_cycle_status='Applied'";
         break;
               case "APPROVED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupName="+"'"+groupType+"' AND loan_cycle_status='Approved'";
         break;

          }
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
        
           loanDetails=new ArrayList();
           
            
    loanDetails.add(n);
//  JOptionPane.showMessageDialog(c, "n"+n);
loanDetails.add(rsxmt.getString("applicant_account_name"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"applicant_account_name\")"+rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("LoanGroupId"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"LoanGroupId\")"+rsxmt.getString("LoanGroupId"));
loanDetails.add(rsxmt.getString("LoanGroupName"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"LoanGroupName\")"+rsxmt.getString("LoanGroupName"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"princimpal_amount\")"+rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("TotalPrincipalPaid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalPrincipalPaid\")"+rsxmt.getString("TotalPrincipalPaid"));
loanDetails.add(rsxmt.getString("TotalPrincipalRemaining"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalPrincipalRemaining\")"+rsxmt.getString("TotalPrincipalRemaining"));
loanDetails.add(rsxmt.getString("total_interest"));
//  JOptionPane.showMessageDialog(c,"rsxmt.getString(\"total_interest\")"+rsxmt.getString("total_interest"));
//loanDetails.add(rsxmt.getString("TotalInterestPaid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalInterestPaid\")"+rsxmt.getString("TotalInterestPaid"));
//loanDetails.add(rsxmt.getString("TotalInterestRemaining"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalInterestRemaining\")"+rsxmt.getString("TotalInterestRemaining"));
//loanDetails.add(rsxmt.getString("total_loanAmount"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"total_loanAmount\")"+rsxmt.getString("total_loanAmount"));
  loanDetails.add(rsxmt.getString("instalments_paid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"instalments_paid\")"+rsxmt.getString("instalments_paid"));
loanDetails.add(rsxmt.getString("balance_due"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"balance_due\")"+rsxmt.getString("balance_due"));

        loanDetails.add(rsxmt.getString("loan_cycle_status"));
//          JOptionPane.showMessageDialog(c, rsxmt.getString("loan_cycle_status"));
  loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date"))); 
//  JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
//loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));
//  JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));

data5x.add(loanDetails);
n++;
         }
 
//      JOptionPane.showMessageDialog(c, loanDetails.size());
      
 
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
       
       List  column1x= new ArrayList<>();
            column1x.add("S/n");
            column1x.add("Name");
            column1x.add("GroupId");
            column1x.add("GroupName");
            column1x.add("Princi");
            column1x.add("PrinciPaid");
            column1x.add("PrinciO/S");
            column1x.add("Inter");
            column1x.add("InterPaid");
            column1x.add("InterO/S");
//            column1x.add("TotalLoan");
//            column1x.add("TotalPaid");
//            column1x.add("TotalO/S");
            column1x.add("LoanStatus");
            column1x.add("ActionDate");
//            column1x.add("NextDueDate");
            
         groupSummuryModel      models= new groupSummuryModel( data5x, column1x);
           table.setModel(models);
           
       TableRowSorter<groupSummuryModel> sorter = new TableRowSorter<>(models);
       
      table.setRowSorter(sorter);  
       
       
    


}
     
     
      public void loanDetailsForIndividual(JTable table,String accountnUMBER,String SelectCri,Component c){
//          JOptionPane.showMessageDialog(c, accountnUMBER);
     List theLoanGroupId=groupNameIdividualList(accountnUMBER,c);

 List<List> data5x= new ArrayList<>();


for(Object groupName:theLoanGroupId){
      data5x=     theGroupDetails(groupName.toString(),SelectCri,data5x);


}
//      JOptionPane.showMessageDialog(c, loanDetails.size());
      
 
   

       
       List  column1x= new ArrayList<>();
            column1x.add("S/n");
            column1x.add("Name");
            column1x.add("GroupId");
            column1x.add("GroupName");
            column1x.add("Principal");
            column1x.add("PrincipalPaid");
            column1x.add("PrincipalO/S");
            column1x.add("Interest");
            column1x.add("InterestPaid");
            column1x.add("InterestO/S");
//            column1x.add("TotalLoan");
//            column1x.add("TotalPaid");
//            column1x.add("TotalO/S");
            column1x.add("LoanStatus");
            column1x.add("ActionDate");
//            column1x.add("NextDueDate");
            
         groupSummuryModel      models= new groupSummuryModel( data5x, column1x);
           table.setModel(models);
           
       TableRowSorter<groupSummuryModel> sorter = new TableRowSorter<>(models);
       
      table.setRowSorter(sorter);  
       
       
    


}
      
public List<List> theGroupDetails(String groupType,String SelectCri,List<List> theMon){
List loanDetails=null;int n=1; 
try {
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          String query="";
          switch(SelectCri){
              case "ALL":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupId="+"'"+groupType+"'";
         break;
                case "DISBURSED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupId="+"'"+groupType+"' AND loan_cycle_status='Disbursed'";
         break;
               case "COMPLETED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupId="+"'"+groupType+"' AND loan_cycle_status='Completed'";
         break;
               case "APPLIED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupId="+"'"+groupType+"' AND loan_cycle_status='Applied'";
         break;
               case "APPROVED":
  query = "SELECT applicant_account_name, LoanGroupId, LoanGroupName, princimpal_amount, TotalPrincipalPaid,"
           + "TotalPrincipalRemaining, total_interest,TotalInterestPaid,TotalInterestRemaining,"
           + "total_loanAmount,instalments_paid,balance_due,instalments_paid,instalment_next_due_date,trn_date,applicant_account_number,loan_cycle_status FROM "
           + "new_loan_appstore1 WHERE  LoanGroupId="+"'"+groupType+"' AND loan_cycle_status='Approved'";
         break;

          }
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
      while(rsxmt.next()){
        
           loanDetails=new ArrayList();
           
            
    loanDetails.add(n);
//  JOptionPane.showMessageDialog(c, "n"+n);
loanDetails.add(rsxmt.getString("applicant_account_name"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"applicant_account_name\")"+rsxmt.getString("applicant_account_name"));
loanDetails.add(rsxmt.getString("LoanGroupId"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"LoanGroupId\")"+rsxmt.getString("LoanGroupId"));
loanDetails.add(rsxmt.getString("LoanGroupName"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"LoanGroupName\")"+rsxmt.getString("LoanGroupName"));
loanDetails.add(rsxmt.getString("princimpal_amount"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"princimpal_amount\")"+rsxmt.getString("princimpal_amount"));
loanDetails.add(rsxmt.getString("TotalPrincipalPaid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalPrincipalPaid\")"+rsxmt.getString("TotalPrincipalPaid"));
loanDetails.add(rsxmt.getString("TotalPrincipalRemaining"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalPrincipalRemaining\")"+rsxmt.getString("TotalPrincipalRemaining"));
loanDetails.add(rsxmt.getString("total_interest"));
//  JOptionPane.showMessageDialog(c,"rsxmt.getString(\"total_interest\")"+rsxmt.getString("total_interest"));
//loanDetails.add(rsxmt.getString("TotalInterestPaid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalInterestPaid\")"+rsxmt.getString("TotalInterestPaid"));
//loanDetails.add(rsxmt.getString("TotalInterestRemaining"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"TotalInterestRemaining\")"+rsxmt.getString("TotalInterestRemaining"));
//loanDetails.add(rsxmt.getString("total_loanAmount"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"total_loanAmount\")"+rsxmt.getString("total_loanAmount"));
  loanDetails.add(rsxmt.getString("instalments_paid"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"instalments_paid\")"+rsxmt.getString("instalments_paid"));
loanDetails.add(rsxmt.getString("balance_due"));
//  JOptionPane.showMessageDialog(c, "rsxmt.getString(\"balance_due\")"+rsxmt.getString("balance_due"));

        loanDetails.add(rsxmt.getString("loan_cycle_status"));
//          JOptionPane.showMessageDialog(c, rsxmt.getString("loan_cycle_status"));
  loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date"))); 
//  JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("trn_date")));
//loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));
//  JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(instalmentDueDate(rsxmt.getString("applicant_account_number"))));
theMon.add(loanDetails);
n++;
         }
 
//      JOptionPane.showMessageDialog(c, loanDetails.size());
      
 
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
       
return theMon;
}      
      
      
   public String instalmentDueDate(String aAccountNumber){
 
 String theDueDate="";

 
  try {
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+aAccountNumber+"'"+"AND  NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
            
theDueDate=rsxmt.getString("instalment_due_date");

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
 

 
 return theDueDate;
 
 
 }
 
 
  public String AinstalmentDueDate(String aAccountNumber){
  
      String theDueDate="";
      
      try {
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+aAccountNumber+"'";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
            
theDueDate=rsxmt.getString("instalment_due_date");

          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
  return theDueDate;
  }
   
       public  synchronized void updateLoanStoreAllDetails(List loanDetails,Component c){
           
boolean d=false; String loancycle="",accountNumber="";

loancycle="'"+loanDetails.get(4).toString()+"'";

accountNumber="'"+loanDetails.get(27).toString()+"'";


//JOptionPane.showMessageDialog(c, loanDetails.get(26).toString());
try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
      String UpdateQuary3 = "UPDATE new_loan_appstore1 SET ClientExistanceCat=?, BorrowingCategory=?,LoanGroupId=?,LoanGroupName=?,LoanCycle=?,LoanProduct=?,MarketingChannel=?,TenureType=?,LoanPurpose=?,"
           + "PaymentHistory=?,ClientLoyalty=?,SecurityUsage=?,NumberOfSecurity=?,EconomicWelbeingLevel=?,PaymentSource=?,AmountTakenCategory=?,"
           + "IncomeLevel=?,EmploymentCat=?,Occupation=?,Sex=?,MaritalStatus=?,AgeGroup=?,EducationLevel=?,PreferedContactChannel=?,Email=?,BorrowerCharacter=?,"
           + "PhoneNumber=?  WHERE applicant_account_number=? AND  LoanCycle=?";
  
    PreparedStatement pstool = cq.prepareStatement(UpdateQuary3);
    
   pstool.setObject(1, loanDetails.get(0));
    pstool.setObject(2, loanDetails.get(1));
    pstool.setObject(3, loanDetails.get(2));
      pstool.setObject(4, loanDetails.get(3));
    pstool.setObject(5, loanDetails.get(4));
    pstool.setObject(6, loanDetails.get(5));
    
     pstool.setObject(7, loanDetails.get(6));
    pstool.setObject(8, loanDetails.get(7));
    pstool.setObject(9, loanDetails.get(8));
      pstool.setObject(10, loanDetails.get(9));
    pstool.setObject(11, loanDetails.get(10));
    pstool.setObject(12, loanDetails.get(11));
    
    
     pstool.setObject(13, loanDetails.get(12));
    pstool.setObject(14, loanDetails.get(13));
    pstool.setObject(15, loanDetails.get(14));
      pstool.setObject(16, loanDetails.get(15));
    pstool.setObject(17, loanDetails.get(16));
    pstool.setObject(18, loanDetails.get(17));
    
     pstool.setObject(19, loanDetails.get(18));
    pstool.setObject(20, loanDetails.get(19));
    pstool.setObject(21, loanDetails.get(20));
      pstool.setObject(22, loanDetails.get(21));
    pstool.setObject(23, loanDetails.get(22));
    pstool.setObject(24, loanDetails.get(23));
      pstool.setObject(25, loanDetails.get(24));
         pstool.setObject(26, loanDetails.get(25));
          pstool.setObject(27, loanDetails.get(26));
     pstool.setObject(28, loanDetails.get(27).toString());
       pstool.setString(29, loanDetails.get(4).toString());
       
   d= pstool.execute();
   
//JOptionPane.showMessageDialog(c,  d);

    cq.setAutoCommit(true);     
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
//JOptionPane.showMessageDialog(c,  loanDetails.get(4).toString());
    
    }
      public void otherLoanDetailsForUpate1(JTable table,String AccoutnNumber){
       
       
         
               ListTableModel model=null;
    List<List> data5x= new ArrayList<>();
     List column1x= null;
        List theDetails=null;
    try {

    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
   String query = "SELECT BorrowingCategory,LoanGroupId,LoanGroupName,LoanCycle,LoanProduct,MarketingChannel,TenureType,LoanPurpose,"
           + "PaymentHistory,ClientLoyalty,SecurityUsage,NumberOfSecurity,EconomicWelbeingLevel,PaymentSource,AmountTakenCategory,"
           + "IncomeLevel,EmploymentCat,Occupation,Sex,MaritalStatus,AgeGroup,EducationLevel,PreferedContactChannel,Email,BorrowerCharacter,"
           + "PhoneNumber FROM  new_loan_appstore1 WHERE  applicant_account_number="+"'"+AccoutnNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

   while (rst3.next()){
theDetails=new ArrayList();
  
    theDetails.add(rst3.getString("BorrowingCategory"));
    theDetails.add(rst3.getString("LoanGroupId"));
      theDetails.add(rst3.getString("LoanGroupName"));
      theDetails.add(rst3.getString("LoanCycle"));
    theDetails.add(rst3.getString("LoanProduct"));
    theDetails.add(rst3.getString("MarketingChannel"));
    theDetails.add(rst3.getString("TenureType"));
    theDetails.add(rst3.getString("LoanPurpose"));
    theDetails.add(rst3.getString("PaymentHistory"));
    theDetails.add(rst3.getString("ClientLoyalty"));
    
    theDetails.add(rst3.getString("SecurityUsage"));
    theDetails.add(rst3.getString("NumberOfSecurity"));
    theDetails.add(rst3.getString("EconomicWelbeingLevel"));
    theDetails.add(rst3.getString("PaymentSource"));
    theDetails.add(rst3.getString("AmountTakenCategory"));
    
    theDetails.add(rst3.getString("IncomeLevel"));
    theDetails.add(rst3.getString("EmploymentCat"));
    theDetails.add(rst3.getString("Occupation"));
    theDetails.add(rst3.getString("Sex"));
    
        theDetails.add(rst3.getString("MaritalStatus"));
    theDetails.add(rst3.getString("AgeGroup"));
    theDetails.add(rst3.getString("EducationLevel"));
    theDetails.add(rst3.getString("PreferedContactChannel"));
    theDetails.add(rst3.getString("Email"));
    theDetails.add(rst3.getString("BorrowerCharacter"));
    theDetails.add(rst3.getString("PhoneNumber"));
 data5x.add(theDetails);
    }

   
           

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 column1x= new ArrayList<>();
            column1x.add("BorrowingCategory");
            column1x.add("LoanGroupId");
            column1x.add("LoanGroupName");
            column1x.add("CycleNumber");
            column1x.add("LoanProduct");
            column1x.add("MarketingChannel");
            column1x.add("TenureType");
            column1x.add("LoanPurpose");
            column1x.add("PaymentHistory");
            column1x.add("ClientLoyalty");
            column1x.add("SecurityUsage");
            column1x.add("NumberOfSecurity");
            column1x.add("EconomicWelbeingLevel");
            column1x.add("PaymentSource");
            column1x.add("AmountTakenCategory");
            column1x.add("IncomeLevel");
            column1x.add("EmploymentCat");
            column1x.add("Occupation");
            column1x.add("Sex");
            column1x.add("MaritalStatus");
            column1x.add("AgeGroup");
            column1x.add("EducationLevel");
            column1x.add("PreferedContactChannel");
            column1x.add("Email");
            column1x.add("BorrowerCharacter");
            column1x.add("PhoneNumber");
               model= new ListTableModel( data5x, column1x);
           table.setModel(model);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);

    } 
    
     public List otherLoanDetailsForUpate(String AccoutnNumber){
       
         List theDetails=new ArrayList();
         
         
         
    try {

    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
   String query = "SELECT BorrowingCategory,LoanGroupId,LoanGroupName,LoanProduct,MarketingChannel,TenureType,LoanPurpose,"
           + "PaymentHistory,ClientLoyalty,SecurityUsage,NumberOfSecurity,EconomicWelbeingLevel,PaymentSource,AmountTakenCategory,"
           + "IncomeLevel,EmploymentCat,Occupation,Sex,MaritalStatus,AgeGroup,EducationLevel,PreferedContactChannel,Email,BorrowerCharacter,"
           + "PhoneNumber FROM  new_loan_appstore1 WHERE  applicant_account_number="+"'"+AccoutnNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){

  
    theDetails.add(rst3.getString("BorrowingCategory"));
    theDetails.add(rst3.getString("LoanGroupId"));
      theDetails.add(rst3.getString("LoanGroupName"));
      theDetails.add("Cycle"+this.cycleNumber(AccoutnNumber));
    theDetails.add(rst3.getString("LoanProduct"));
    theDetails.add(rst3.getString("MarketingChannel"));
    theDetails.add(rst3.getString("TenureType"));
    theDetails.add(rst3.getString("LoanPurpose"));
    theDetails.add(rst3.getString("PaymentHistory"));
    theDetails.add(rst3.getString("ClientLoyalty"));
    
    theDetails.add(rst3.getString("SecurityUsage"));
    theDetails.add(rst3.getString("NumberOfSecurity"));
    theDetails.add(rst3.getString("EconomicWelbeingLevel"));
    theDetails.add(rst3.getString("PaymentSource"));
    theDetails.add(rst3.getString("AmountTakenCategory"));
    theDetails.add(rst3.getString("IncomeLevel"));
    theDetails.add(rst3.getString("EmploymentCat"));
    theDetails.add(rst3.getString("Occupation"));
    theDetails.add(rst3.getString("Sex"));
    
        theDetails.add(rst3.getString("MaritalStatus"));
    theDetails.add(rst3.getString("AgeGroup"));
    theDetails.add(rst3.getString("EducationLevel"));
    theDetails.add(rst3.getString("PreferedContactChannel"));
    theDetails.add(rst3.getString("Email"));
    theDetails.add(rst3.getString("BorrowerCharacter"));
    theDetails.add(rst3.getString("PhoneNumber"));
 
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return theDetails;

    } 
     
  public List otherLoanDetailsForUpatex(String AccoutnNumber,String loanCycle){
       
         List theDetails=new ArrayList();
         
         
         
    try {

    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
   String query = "SELECT BorrowingCategory,LoanGroupId,LoanGroupName,LoanProduct,MarketingChannel,TenureType,LoanPurpose,"
           + "PaymentHistory,ClientLoyalty,SecurityUsage,NumberOfSecurity,EconomicWelbeingLevel,PaymentSource,AmountTakenCategory,"
           + "IncomeLevel,EmploymentCat,Occupation,Sex,MaritalStatus,AgeGroup,EducationLevel,PreferedContactChannel,Email,BorrowerCharacter,"
           + "PhoneNumber FROM  new_loan_appstore1 WHERE  applicant_account_number="+"'"+AccoutnNumber+"'  AND LoanCycle="+"'"+loanCycle+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){

  
    theDetails.add(rst3.getString("BorrowingCategory"));
    theDetails.add(rst3.getString("LoanGroupId"));
      theDetails.add(rst3.getString("LoanGroupName"));
      theDetails.add("Cycle"+this.cycleNumber(AccoutnNumber));
    theDetails.add(rst3.getString("LoanProduct"));
    theDetails.add(rst3.getString("MarketingChannel"));
    theDetails.add(rst3.getString("TenureType"));
    theDetails.add(rst3.getString("LoanPurpose"));
    theDetails.add(rst3.getString("PaymentHistory"));
    theDetails.add(rst3.getString("ClientLoyalty"));
    
    theDetails.add(rst3.getString("SecurityUsage"));
    theDetails.add(rst3.getString("NumberOfSecurity"));
    theDetails.add(rst3.getString("EconomicWelbeingLevel"));
    theDetails.add(rst3.getString("PaymentSource"));
    theDetails.add(rst3.getString("AmountTakenCategory"));
    theDetails.add(rst3.getString("IncomeLevel"));
    theDetails.add(rst3.getString("EmploymentCat"));
    theDetails.add(rst3.getString("Occupation"));
    theDetails.add(rst3.getString("Sex"));
    
        theDetails.add(rst3.getString("MaritalStatus"));
    theDetails.add(rst3.getString("AgeGroup"));
    theDetails.add(rst3.getString("EducationLevel"));
    theDetails.add(rst3.getString("PreferedContactChannel"));
    theDetails.add(rst3.getString("Email"));
    theDetails.add(rst3.getString("BorrowerCharacter"));
    theDetails.add(rst3.getString("PhoneNumber"));
 
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return theDetails;

    }    
     
   public loanManagementModel1 fullLoanDetailsForUpate(){
       List headers=new ArrayList();
       headers.add("Id");
headers.add("Borrower Account Name");
headers.add("Borrower Account Number");
headers.add("Phone Number");
headers.add("Loan Id");
headers.add("Installment Start Date");
headers.add("Installment Next Due Date");
headers.add("Instilment End Date");
headers.add("Loan Tenure");
headers.add("Annual Interest Rate Used");
headers.add("Loan Stage Status");
headers.add("Total Expected Loan Amount");
headers.add("Installment Size");
headers.add("Total Loan Amount Paid");
headers.add("Outstanding Loan Amount");
headers.add("Original Principal Amount");
headers.add("Total Principal Paid");
headers.add("Total Principal Remaining");
headers.add("Total Expected Interest");
headers.add("Total Interest Paid");
headers.add("Total Interest Remaining");
headers.add("Total Accumulated Interest Paid");
headers.add("Total Accumulated Interest Remaining");
headers.add("Total Loan Penalty Paid");
headers.add("Total Loan Penalty Remaining");
headers.add("Total Installments");
headers.add("Remaining Installments");
headers.add("Existence Status");
headers.add("Borrowing Category");
headers.add("Group Id");
headers.add("Group Name");
headers.add("Loan Cycle");
headers.add("Loan Product");
headers.add("Marketing Channel");
headers.add("Tenure Type");
headers.add("Loan Purpose");
headers.add("Payment History");
headers.add("Client Loyalty");
headers.add("Security Usage");
headers.add("Number Of Securities Used");
headers.add("Security/Collateral Used");
headers.add("Economic Wellbeing Level");
headers.add("Payment Source");
headers.add("Amount Taken Category");
headers.add("Income Level");
headers.add("Employment Category");
headers.add("Occupation");
headers.add("Sex");
headers.add("Marital Status");
headers.add("Age Group");

headers.add("Education Level");
headers.add("Preferred Contact Channel");
headers.add("Email");
headers.add("Borrower Character");
headers.add("Portfolio Owner");
headers.add("Txn Date");
headers.add("Txn Time");
headers.add("Inputter Id");
headers.add("Authorizer Id");

         List theDetails=null;
          List<List> theDetailsMaster=new ArrayList();
         
         
    try {

    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
   String query = "SELECT  `trn_id` ,`applicant_account_name` ,`applicant_account_number` ,`PhoneNumber`,`loan_id` ,`instalment_start_date` ,"
           + "`instalment_next_due_date` ,`instalment_end_date` ,`loan_tenure` ,`interest_rate` ,`loan_cycle_status` ,`total_loanAmount` ,"
           + "`instalment_amount` ,`instalments_paid` ,`balance_due` ,`princimpal_amount` ,`TotalPrincipalPaid`,`TotalPrincipalRemaining`,"
           + "`total_interest` ,`TotalInterestPaid`,`TotalInterestRemaining`,`TotalAccumulatedInterestPaid`,`TotalAccumulatedInterestRemaining`,"
           + "`TotalLoanPenaltyPaid`,`TotalLoanPenaltyRemaining`,`total_instalments` ,`remaining_instalments` ,`ClientExistanceCat`,"
           + "`BorrowingCategory` ,`LoanGroupId` ,`LoanGroupName` ,`LoanCycle` ,`LoanProduct` ,`MarketingChannel` ,`TenureType` ,`LoanPurpose` ,"
           + "`PaymentHistory` ,`ClientLoyalty` ,`SecurityUsage`,`NumberOfSecurity`,`SecurityLoan` ,`EconomicWelbeingLevel`,"
           + "`PaymentSource`,`AmountTakenCategory` ,`IncomeLevel`,`EmploymentCat` ,`Occupation` ,`Sex` ,`MaritalStatus`,`AgeGroup` ,`EducationLevel` ,"
           + "`PreferedContactChannel`,`Email` ,`BorrowerCharacter` ,`gruop_id`,`trn_date` ,	`trn_time` ,`inputter_id` ,`authoriser_id`  FROM  new_loan_appstore1";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
  theDetails=new ArrayList();
    theDetails.add(rst3.getString("trn_id"));
    theDetails.add(rst3.getString("applicant_account_name"));
      theDetails.add(rst3.getString("applicant_account_number"));
      theDetails.add(rst3.getString("PhoneNumber"));
    theDetails.add(rst3.getString("loan_id"));
    theDetails.add(rst3.getString("instalment_start_date"));
    theDetails.add(rst3.getString("instalment_next_due_date"));
    theDetails.add(rst3.getString("instalment_end_date"));
    theDetails.add(rst3.getString("loan_tenure"));
    theDetails.add(rst3.getString("interest_rate"));
    theDetails.add(rst3.getString("loan_cycle_status"));
    theDetails.add(rst3.getString("total_loanAmount"));
    theDetails.add(rst3.getString("instalment_amount"));
    theDetails.add(rst3.getString("instalments_paid"));
    theDetails.add(rst3.getString("balance_due"));
    theDetails.add(rst3.getString("princimpal_amount"));
    theDetails.add(rst3.getString("TotalPrincipalPaid"));
    theDetails.add(rst3.getString("TotalPrincipalRemaining"));
    theDetails.add(rst3.getString("total_interest"));  
        theDetails.add(rst3.getString("TotalInterestPaid"));
    theDetails.add(rst3.getString("TotalInterestRemaining"));
    theDetails.add(rst3.getString("TotalAccumulatedInterestPaid"));
    theDetails.add(rst3.getString("TotalAccumulatedInterestRemaining"));
    theDetails.add(rst3.getString("TotalLoanPenaltyPaid"));
    theDetails.add(rst3.getString("TotalLoanPenaltyRemaining"));
    theDetails.add(rst3.getString("total_instalments"));
    
    
     theDetails.add(rst3.getString("remaining_instalments"));
    theDetails.add(rst3.getString("ClientExistanceCat"));
    theDetails.add(rst3.getString("BorrowingCategory"));
    theDetails.add(rst3.getString("LoanGroupId"));
    theDetails.add(rst3.getString("LoanGroupName"));  
        theDetails.add(rst3.getString("LoanCycle"));
    theDetails.add(rst3.getString("LoanProduct"));
    theDetails.add(rst3.getString("MarketingChannel"));
    theDetails.add(rst3.getString("TenureType"));
    theDetails.add(rst3.getString("LoanPurpose"));
    theDetails.add(rst3.getString("PaymentHistory"));
    theDetails.add(rst3.getString("ClientLoyalty"));
    
    
     theDetails.add(rst3.getString("SecurityUsage"));
    theDetails.add(rst3.getString("NumberOfSecurity"));
    theDetails.add(rst3.getString("SecurityLoan"));
    theDetails.add(rst3.getString("EconomicWelbeingLevel"));
    theDetails.add(rst3.getString("PaymentSource"));  
        theDetails.add(rst3.getString("AmountTakenCategory"));
    theDetails.add(rst3.getString("IncomeLevel"));
    theDetails.add(rst3.getString("EmploymentCat"));
    theDetails.add(rst3.getString("Occupation"));
    theDetails.add(rst3.getString("Sex"));
    theDetails.add(rst3.getString("MaritalStatus"));
    theDetails.add(rst3.getString("AgeGroup"));
    
    theDetails.add(rst3.getString("EducationLevel"));
    theDetails.add(rst3.getString("PreferedContactChannel"));
    
     theDetails.add(rst3.getString("Email"));
    theDetails.add(rst3.getString("BorrowerCharacter"));
    theDetails.add(dbq.getUserName(rst3.getString("gruop_id")));
    theDetails.add(rst3.getString("trn_date"));
    
       theDetails.add(rst3.getString("trn_time"));
    theDetails.add(rst3.getString("inputter_id"));
    theDetails.add(rst3.getString("authoriser_id"));
  
 theDetailsMaster.add(theDetails);
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
mod=  new loanManagementModel1( theDetailsMaster, headers);
    return mod;

    }   
     
  
   
   
   
   public loanManagementModel1 fullLoanDetailsForUpate1(){
       List headers=new ArrayList();
       headers.add("Id");
headers.add("Borrower Account Name");
headers.add("Borrower Account Number");
headers.add("Phone Number");
headers.add("Loan Id");
headers.add("Installment Start Date");
headers.add("Installment Next Due Date");
headers.add("Instilment End Date");
headers.add("Loan Tenure");
headers.add("Annual Interest Rate Used");
headers.add("Loan Stage Status");
headers.add("Total Expected Loan Amount");
headers.add("Installment Size");
headers.add("Total Loan Amount Paid");
headers.add("Outstanding Loan Amount");
headers.add("Original Principal Amount");
headers.add("Total Principal Paid");
headers.add("Total Principal Remaining");
headers.add("Total Expected Interest");
headers.add("Total Interest Paid");
headers.add("Total Interest Remaining");
headers.add("Total Accumulated Interest Paid");
headers.add("Total Accumulated Interest Remaining");
headers.add("Total Loan Penalty Paid");
headers.add("Total Loan Penalty Remaining");
headers.add("Total Installments");
headers.add("Remaining Installments");
headers.add("Existence Status");
headers.add("Borrowing Category");
headers.add("Group Id");
headers.add("Group Name");
headers.add("Loan Cycle");
headers.add("Loan Product");
headers.add("Client Location");
headers.add("Security Type");
headers.add("Security Location");
headers.add("Payment History");
headers.add("Client Loyalty");
headers.add("Security Usage");
headers.add("Number Of Securities Used");
headers.add("Security/Collateral Used");
headers.add("Economic Wellbeing Level");
headers.add("Payment Source");
headers.add("Amount Taken Category");
headers.add("Income Level");
headers.add("Employment Category");
headers.add("Occupation");
headers.add("Sex");
headers.add("Marital Status");
headers.add("Age Group");

headers.add("Education Level");
headers.add("Preferred Contact Channel");
headers.add("Email");
headers.add("Borrower Character");
headers.add("Portfolio Owner");
headers.add("Txn Date");
headers.add("Txn Time");
headers.add("Inputter Id");
headers.add("Authorizer Id");

         List theDetails=null;
          List<List> theDetailsMaster=new ArrayList();
         
         
    try {

    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
   String query = "SELECT  `trn_id` ,`applicant_account_name` ,`applicant_account_number` ,`PhoneNumber`,`loan_id` ,`instalment_start_date` ,"
           + "`instalment_next_due_date` ,`instalment_end_date` ,`loan_tenure` ,`interest_rate` ,`loan_cycle_status` ,`total_loanAmount` ,"
           + "`instalment_amount` ,`instalments_paid` ,`balance_due` ,`princimpal_amount` ,`TotalPrincipalPaid`,`TotalPrincipalRemaining`,"
           + "`total_interest` ,`TotalInterestPaid`,`TotalInterestRemaining`,`TotalAccumulatedInterestPaid`,`TotalAccumulatedInterestRemaining`,"
           + "`TotalLoanPenaltyPaid`,`TotalLoanPenaltyRemaining`,`total_instalments` ,`remaining_instalments` ,`ClientExistanceCat`,"
           + "`BorrowingCategory` ,`LoanGroupId` ,`LoanGroupName` ,`LoanCycle` ,`LoanProduct` ,`MarketingChannel` ,`TenureType` ,`LoanPurpose` ,"
           + "`PaymentHistory` ,`ClientLoyalty` ,`SecurityUsage`,`NumberOfSecurity`,`SecurityLoan` ,`EconomicWelbeingLevel`,"
           + "`PaymentSource`,`AmountTakenCategory` ,`IncomeLevel`,`EmploymentCat` ,`Occupation` ,`Sex` ,`MaritalStatus`,`AgeGroup` ,`EducationLevel` ,"
           + "`PreferedContactChannel`,`Email` ,`BorrowerCharacter` ,`gruop_id`,`trn_date` ,	`trn_time` ,`inputter_id` ,`authoriser_id`  FROM  new_loan_appstore1 WHERE loan_cycle_status='Disbursed'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
  theDetails=new ArrayList();
    theDetails.add(rst3.getString("trn_id"));
    theDetails.add(rst3.getString("applicant_account_name"));
      theDetails.add(rst3.getString("applicant_account_number"));
      theDetails.add(rst3.getString("PhoneNumber"));
    theDetails.add(rst3.getString("loan_id"));
    theDetails.add(rst3.getString("instalment_start_date"));
    theDetails.add(rst3.getString("instalment_next_due_date"));
    theDetails.add(rst3.getString("instalment_end_date"));
    theDetails.add(rst3.getString("loan_tenure"));
    theDetails.add(rst3.getString("interest_rate"));
    theDetails.add(rst3.getString("loan_cycle_status"));
    theDetails.add(rst3.getString("total_loanAmount"));
    theDetails.add(rst3.getString("instalment_amount"));
    theDetails.add(rst3.getString("instalments_paid"));
    theDetails.add(rst3.getString("balance_due"));
    theDetails.add(rst3.getString("princimpal_amount"));
    theDetails.add(rst3.getString("TotalPrincipalPaid"));
    theDetails.add(rst3.getString("TotalPrincipalRemaining"));
    theDetails.add(rst3.getString("total_interest"));  
        theDetails.add(rst3.getString("TotalInterestPaid"));
    theDetails.add(rst3.getString("TotalInterestRemaining"));
    theDetails.add(rst3.getString("TotalAccumulatedInterestPaid"));
    theDetails.add(rst3.getString("TotalAccumulatedInterestRemaining"));
    theDetails.add(rst3.getString("TotalLoanPenaltyPaid"));
    theDetails.add(rst3.getString("TotalLoanPenaltyRemaining"));
    theDetails.add(rst3.getString("total_instalments"));
    
    
     theDetails.add(rst3.getString("remaining_instalments"));
    theDetails.add(rst3.getString("ClientExistanceCat"));
    theDetails.add(rst3.getString("BorrowingCategory"));
    theDetails.add(rst3.getString("LoanGroupId"));
    theDetails.add(rst3.getString("LoanGroupName"));  
        theDetails.add(rst3.getString("LoanCycle"));
    theDetails.add(rst3.getString("LoanProduct"));
    theDetails.add(rst3.getString("MarketingChannel"));
    theDetails.add(rst3.getString("TenureType"));
    theDetails.add(rst3.getString("LoanPurpose"));
    theDetails.add(rst3.getString("PaymentHistory"));
    theDetails.add(rst3.getString("ClientLoyalty"));
    
    
     theDetails.add(rst3.getString("SecurityUsage"));
    theDetails.add(rst3.getString("NumberOfSecurity"));
    theDetails.add(rst3.getString("SecurityLoan"));
    theDetails.add(rst3.getString("EconomicWelbeingLevel"));
    theDetails.add(rst3.getString("PaymentSource"));  
        theDetails.add(rst3.getString("AmountTakenCategory"));
    theDetails.add(rst3.getString("IncomeLevel"));
    theDetails.add(rst3.getString("EmploymentCat"));
    theDetails.add(rst3.getString("Occupation"));
    theDetails.add(rst3.getString("Sex"));
    theDetails.add(rst3.getString("MaritalStatus"));
    theDetails.add(rst3.getString("AgeGroup"));
    
    theDetails.add(rst3.getString("EducationLevel"));
    theDetails.add(rst3.getString("PreferedContactChannel"));
    
     theDetails.add(rst3.getString("Email"));
    theDetails.add(rst3.getString("BorrowerCharacter"));
    theDetails.add(dbq.getUserName(rst3.getString("gruop_id")));
    theDetails.add(rst3.getString("trn_date"));
    
       theDetails.add(rst3.getString("trn_time"));
    theDetails.add(rst3.getString("inputter_id"));
    theDetails.add(rst3.getString("authoriser_id"));
  
 theDetailsMaster.add(theDetails);
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
mod=  new loanManagementModel1( theDetailsMaster, headers);
    return mod;

    }   
     
    public synchronized void updateSubmited(String accountNumber,String statusCycle){
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
 
    String UpdateQuary = "UPDATE new_loan_appstore SET loan_cycle_status=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, statusCycle);
    pstool.setObject(2, "newloan"+accountNumber);
    pstool.execute();
    }  

    
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
 
    String UpdateQuary1 = "UPDATE new_loan_appstore1 SET loan_cycle_status=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, statusCycle);
    pstool.setObject(2, "newloan"+accountNumber);
    pstool.execute();
    }  

    
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    
    }
    
    
    public synchronized boolean testTable(String tableName) {

    boolean testT=true;
    try {
    testT= loancon.createConnection().getMetaData().getTables(null, null, tableName,  null).next();
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    return  testT;

    }

    public boolean testColumn(String tableName,String columnName) {
    boolean testC=true;
    try {
        
    testC=  loancon.createConnection().getMetaData().getColumns(null, null, tableName, columnName).next();
    
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    return testC;
    }

  public double getLoanInstalmentChargeOn(String accountNumber,String instalmentNumber){
      
  double theItems=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    
    String query = "SELECT  InstalmentRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instalmentNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 while(rst3.next()){
  theItems=rst3.getDouble("InstalmentRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return theItems;
  }
    
 public double  piRemain(String accountNumber){
 
 double pRemain=0.0,iRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(PrincipalRemaining) AS princ,SUM(InterestRemaing) AS inter FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  pRemain=rst3.getDouble("princ");
  iRemain=rst3.getDouble("inter");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return (pRemain+iRemain);
 
 
 }
 
 
 
  public double  pLpRemain(String accountNumber){
 
 double pRemain=0.0,iRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(PrincipalRemaining) AS princ,SUM(LoanPenaltyRemaining) AS inter FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  pRemain=rst3.getDouble("princ");
  iRemain=rst3.getDouble("inter");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return (pRemain+iRemain);
 
 
 }
 public double  pLpRemainA(String accountNumber){
 
 double pRemain=0.0,iRemain=0.0,lpR=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(PrincipalRemaining) AS princ,SUM(LoanPenaltyRemaining) AS lp,SUM(InterestRemaing) AS inter FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  pRemain=rst3.getDouble("princ");
  iRemain=rst3.getDouble("inter");
  lpR=rst3.getDouble("lp");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return (pRemain+iRemain+lpR);
 
 
 }
 public double  pRemain(String accountNumber){
 
 double pRemain=0.0,iRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(PrincipalRemaining) AS princ FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  pRemain=rst3.getDouble("princ");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return pRemain;
 
 
 }
 
 public double  iRemain(String accountNumber){
 
 double pRemain=0.0,iRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(InterestRemaing) AS inter FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  iRemain=rst3.getDouble("inter");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return iRemain;
 
 
 }
 
 public double  AIRemain(String accountNumber){
 
 double AIRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(AccumulatedInterestRemaining) AS AI FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  AIRemain=rst3.getDouble("AI");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return AIRemain;
 
 
 }
 
 public double  lPRemain(String accountNumber){
 
 double pRemain=0.0,lpRemain=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(LoanPenaltyRemaining) AS lp FROM  new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if(rst3.next()){
  lpRemain=rst3.getDouble("lp");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return lpRemain;
 
 
 }
 
  public double    theExistingLoanCharge(String accountNumber,String instalmentNumber){
    
     double theItems=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  LoanPenaltyRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instalmentNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 while(rst3.next()){
  theItems=rst3.getDouble("LoanPenaltyRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return theItems;
    
    }
    
     public double    theLoanCharges(String accountNumber,String instalmentNumber){
     
 double theItems=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  LoanPenalty FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instalmentNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 while(rst3.next()){
  theItems=rst3.getDouble("LoanPenalty");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return theItems;
     
     }
     
     
     public synchronized void updateArrearReport(String accountNumber){
     List value=getprincimpalInArrears(accountNumber);
     if(!value.isEmpty()){
     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loan_arrears_tracker VALUES(?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, this.getDBCurrentDate());
    ps.setObject(3, value.get(0));
    ps.setObject(4, value.get(1));
    ps.setObject(5,dbq.AccountName(accountNumber));
    ps.setObject(6, accountNumber);
   
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
     
     
     }
     }
     }
     
     public synchronized void updateLoansDueWriteOff(String accountNumber){
     List value=getitemsDueWriteOff(accountNumber);
     if(!value.isEmpty()){
     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loan_due_writeoff VALUES(?,?,?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, this.getDBCurrentDate());
    ps.setObject(3, value.get(0));
    ps.setObject(4, value.get(1));
     ps.setObject(5, value.get(2));
     ps.setObject(6, value.get(3));
    ps.setObject(7,dbq.AccountName(accountNumber));
    ps.setObject(8, accountNumber);
   
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
     
     
     }}
     }
 private List<String> getprincimpalInArrears(String accountNumber){
    
List theItems=new ArrayList();

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(princimpal_amount)as princimpalArrears,instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_status="+" "+"'"+"NP"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
    theItems.add(rst3.getString("princimpalArrears"));
    theItems.add(rst3.getString("instalment_due_date")); 
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return theItems;
}
 
 private List<String> getitemsDueWriteOff(String accountNumber){
    
List theItems=new ArrayList();List theItemsR=null;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(princimpal_amount)as princimpalwriteOff,SUM(interest_amount)as interest,instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
   theItemsR= new ArrayList();
   theItemsR.add(rst3.getString("princimpalwriteOff"));
    theItemsR.add(rst3.getString("interest")); 
     theItemsR.add(rst3.getString("instalment_due_date"));
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
if(!theItemsR.isEmpty()){
theItems.add(theItemsR.get(0));
    theItems.add(theItemsR.get(1)); 
     theItems.add(theItemsR.get(2)); 
    theItems.add(Math.abs(fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(theItemsR.get(2).toString()), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()))));

}
   
return theItems;
}
//    public synchronized void createNewLoanAmort(String accountNumber,Component c){
//    try {
//
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);
//
//    String createLoan =  "CREATE TABLE IF NOT EXISTS newloan"+accountNumber+ "("
//    + " `trn_id` INT NOT NULL AUTO_INCREMENT,"
//    + " `instalment_no` INT NOT NULL DEFAULT 1,"
//    + " `princimpal_amount` VARCHAR(50) NOT NULL DEFAULT '0.0',"
//    + " `princimpal_amount_run_bal` VARCHAR(50) NOT NULL DEFAULT '0.0',"
//    + "`interest_amount` VARCHAR(50) NOT NULL DEFAULT '10000.0',"
//    + "  `interest_amount_run_bal` VARCHAR(50) NOT NULL DEFAULT '0.0',"
//    + " `instalment_amount` VARCHAR(50) NOT NULL DEFAULT '0.0',"
//    + " `instalment_paid` VARCHAR(50) NOT NULL DEFAULT '0.0', "
//    + " `beginning_bal` VARCHAR(50) NOT  NULL DEFAULT '0.0',"
//    + " `closing_bal` VARCHAR(50) NOT  NULL DEFAULT '0.0',"
//    + " `instalment_due_date` DATE  NOT NULL DEFAULT '1979-01-01',"
//    + " `instalment_status` VARCHAR(50) NOT  NULL DEFAULT 'P',"
//     + " `instalment_paid_date` DATE  NOT  NULL DEFAULT '1979-01-01',"        
//    + " `instalment_paid_variance` VARCHAR(50)  NOT NULL DEFAULT '2 Days',"
//             + " `LoanPenalty` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccruedInterest` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `InstalmentRemaining` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `PrincipalPaid` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `PrincipalRemaining` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `InterestPaid` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `InterestRemaing` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `LoanPenaltyPaid` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `LoanPenaltyRemaining` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccruedInterestPaid` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccruedInterestRemaing` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccumulatedInterest` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccumulatedInterestPaid` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `AccumulatedInterestRemaining` VARCHAR(50) NOT NULL DEFAULT '0.0'," 
//             + " `OtherOne` VARCHAR(50) NOT NULL DEFAULT 'NYA'," 
//             + " `OtherTwo`  DATE  NOT  NULL DEFAULT '1979-01-01'," 
//             + " `OtherThree`  DATE  NOT  NULL DEFAULT '1979-01-01'," 
//            
//            
//    + " PRIMARY KEY (`trn_id`)"
//    + ")"
//    +"ENGINE = InnoDB"+" AUTO_INCREMENT = 00001";
//
//    PreparedStatement psx1 = cq.prepareStatement(createLoan);
//    psx1.execute();
//
//    cq.setAutoCommit(true); 
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) {
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    }
//
//    
//    
//    



    public synchronized void insertNewLoanAmort(ArrayList value, String accountNumber,String loanId,String loanId2){

        double instalmentAmount=parseDouble(value.get(1).toString())+parseDouble(value.get(3).toString());
        
    if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCRBAsARequirementForNewBorrowersOnly.txt"))==1){    
    try {
    Connection cq=loancon.createConnection(); 
    
    
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO new_loan_appstoreamort VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    ps.setObject(1, null);  //   trn_id// Transaction Id:1
    
    ps.setObject(2, value.get(0));//instalment_no//Instalment Number:2   //Instalment Number:0
    
    ps.setObject(3, value.get(1));//princimpal_amount//Princimpal amount:3    //Principal Amount:1
    
    ps.setObject(4, value.get(2));//princimpal_amount_run_bal//Princimpal amount running balance:4   //Running Principal amount:2
    
    ps.setObject(5,value.get(3));//interest_amount//Interest amount:5    //Interest Amount:3
    
    ps.setObject(6, value.get(4));//interest_amount_run_bal//Interest running balance:6 //Running Interest Amount:4
    
    ps.setObject(7, instalmentAmount);//instalment_amount//Instalment amount:7  //Instalment Amount:5
    
    ps.setObject(8, "0.0"); //instalment_paid//instalment paid:8
    
    ps.setObject(9, value.get(6));//beginning_bal//beginning balance:9 //Starting total Amount:6
    
    ps.setObject(10,value.get(7));//closing_bal//closing balance:10   //Ending total Amount after Instalment:7
    
    
    ps.setObject(11, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString())); //instalment_due_date:11  //Instalment due date:8
    
    ps.setObject(12,"NY");  //instalment_status//instalment status:12
    
    ps.setObject(13, getDBCurrentDate());//instalment_paid_date//instalment paid date:13
    
    ps.setObject(14,"NY");//instalment_paid_variance//instalment paid Variance:14
    
    ps.setObject(15, "0.0"); //loan_penalty//loan penalty:15
    
    ps.setObject(16, "0.0");//AccruedInterest//Accrued Interest:16
    
    ps.setObject(17,instalmentAmount);//InstalmentRemaining//Instalment remaining:17//1  //Remaining instalment Amount:9
    
    
    ps.setObject(18, "0.0"); //PrincipalPaid// Principal Paid:18  
    
    ps.setObject(19,value.get(1));  //PrincipalRemaining// Principal Remaining:19//2      //Remaining principal Amount:10
    
    ps.setObject(20, "0.0");//InterestPaid//Interest Paid:20
    
    ps.setObject(21,value.get(3));//InterestRemaing//Interest Remaining:21//3   //Remaining Interest Amount:11
    
    ps.setObject(22, "0.0"); //LoanPenaltyPaid//Loan penalty paid:22
    
    ps.setObject(23, "0.0");//LoanPenaltyRemaining//Loan penalty remaining:23
    
    ps.setObject(24,"0.0");//AccruedInterestPaid//Accrued interest paid:24
    
    
    ps.setObject(25, "0.0"); //AccruedInterestRemaing//Accrued interest remaining:25
    
    ps.setObject(26,"0.0");  //AccumulatedInterest//Accumulated interest:26
   
    ps.setObject(27, "0.0"); //AccumulatedInterestPaid//Accumulated interest paid:27
    
    ps.setObject(28,"0.0");//AccumulatedInterestRemaining//Accumulated interest remaining:28
    
      ps.setObject(29,"NYA");  //OtherOne//Other one paid:29
    
    ps.setObject(30, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString()));//OtherTwo//Other two paid:30
    
    ps.setObject(31, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString()));//OtherThree//Other three paid:31
    
       ps.setObject(32, loanId);//OtherTwo//Other two paid:30
    
    ps.setObject(33, loanId2);//OtherThree//Other three paid:31
  
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    }else if(fios.intFileReader(fios.createFileName("persistence", "useCRB", "useCrb.txt"))==1){
    
   fios.stringFileWriter(fios.createFileName("loanApplication", "loanProcessing", "accrueingStatus"+accountNumber+".txt"),"NeverAccrued");   
        
    try {
    Connection cq=loancon.createConnection(); 
    
    
    cq.setAutoCommit(false);
    
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO new_loan_appstoreamort VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    ps.setObject(1, null);  //   trn_id// Transaction Id:1
    
    ps.setObject(2, value.get(0));//instalment_no//Instalment Number:2   //Instalment Number:0
    
    ps.setObject(3, value.get(1));//princimpal_amount//Princimpal amount:3    //Principal Amount:1
    
    ps.setObject(4, value.get(2));//princimpal_amount_run_bal//Princimpal amount running balance:4   //Running Principal amount:2
    
    ps.setObject(5,value.get(3));//interest_amount//Interest amount:5    //Interest Amount:3
    
    ps.setObject(6, value.get(4));//interest_amount_run_bal//Interest running balance:6 //Running Interest Amount:4
    
    ps.setObject(7, instalmentAmount);//instalment_amount//Instalment amount:7  //Instalment Amount:5
    
    ps.setObject(8, "0.0"); //instalment_paid//instalment paid:8
    
    ps.setObject(9, value.get(6));//beginning_bal//beginning balance:9 //Starting total Amount:6
    
    ps.setObject(10,value.get(7));//closing_bal//closing balance:10   //Ending total Amount after Instalment:7
    
    
    ps.setObject(11, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString())); //instalment_due_date//instalment due date:11    //Instalment due date:8
    
    ps.setObject(12,"NY");  //instalment_status//instalment status:12
    
    ps.setObject(13, getDBCurrentDate());//instalment_paid_date//instalment paid date:13
    
    ps.setObject(14,"NY");//instalment_paid_variance//instalment paid Variance:14
    
    ps.setObject(15, "0.0"); //loan_penalty//loan penalty:15
    
    ps.setObject(16, value.get(3));//AccruedInterest//Accrued Interest:16
    
    ps.setObject(17,instalmentAmount);//InstalmentRemaining//Instalment remaining:17//1  //Remaining instalment Amount:9
    
    
    ps.setObject(18, "0.0"); //PrincipalPaid// Principal Paid:18  
    
    ps.setObject(19,value.get(1));  //PrincipalRemaining// Principal Remaining:19//2      //Remaining principal Amount:10
    
    ps.setObject(20, "0.0");//InterestPaid//Interest Paid:20
    
    ps.setObject(21,"0.0");//InterestRemaing//Interest Remaining:21//3   //Remaining Interest Amount:11
    
    ps.setObject(22, "0.0"); //LoanPenaltyPaid//Loan penalty paid:22
    
    ps.setObject(23, "0.0");//LoanPenaltyRemaining//Loan penalty remaining:23
    
    ps.setObject(24,"0.0");//AccruedInterestPaid//Accrued interest paid:24
    
    
    ps.setObject(25, "0.0"); //AccruedInterestRemaing//Accrued interest remaining:25
    
    ps.setObject(26,"0.0");  //AccumulatedInterest//Accumulated interest:26
   
    ps.setObject(27, "0.0"); //AccumulatedInterestPaid//Accumulated interest paid:27
    
    ps.setObject(28,"0.0");//AccumulatedInterestRemaining//Accumulated interest remaining:28
    
      ps.setObject(29,"NA");  //OtherOne//Other one paid:29
    
    ps.setObject(30, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString()));//OtherTwo//Other two paid:30
    
    ps.setObject(31,fmt.forDatabaseWithFullYearBeginningWithDate(value.get(8).toString()));//OtherTwo//Other two paid:30
    
     ps.setObject(32, loanId);//OtherTwo//Other two paid:30
    
    ps.setObject(33, loanId);//OtherThree//Other three paid:31
    
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }
    }
    public void interseIn(){
      try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO impactLearnng VALUES(?,?)");

    ps.setObject(1, null);// trn_id   :1//Transaction Id:14

    ps.setObject(2, "UU");// trn_date    :2

    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    
    public synchronized void insertLoanAppStore(ArrayList value,List value1,String disDate,Component c){
//JOptionPane.showMessageDialog(c, "Inside"+value1.size());
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO new_loan_appstore VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    ps.setObject(1, value.get(14));// trn_id   :1//Transaction Id:14

    ps.setObject(2,  fmt.forDatabaseWithFullYearBeginningWithDate(disDate));// trn_date    :2

    ps.setObject(3, value.get(9));// loan_id    :3//Loan Id:9

    ps.setObject(4, value.get(0));// total_instalments   :4//Total Instalments:0

    ps.setObject(5,value.get(0));// remaining_instalments  :5//Total Instalments:0

    ps.setObject(6, value.get(1));// princimpal_amount   :6//Total Principal Amount:1

    ps.setObject(7, value.get(2));// total_interest     :7//Total interest Amount:2

    ps.setObject(8, value.get(3));// total_loanAmount :8//Total loan Amount:3

    ps.setObject(9, value.get(3));// balance_due  :9//Total balance remaining Amount:3

    ps.setObject(10, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(4).toString()));// instalment_start_date :10//Instalment start date:4

    ps.setObject(11,fmt.forDatabaseWithFullYearBeginningWithDate(value.get(4).toString()));// instalment_next_due_date:11//Instalment next date:4

    ps.setObject(12, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(5).toString()));// instalment_end_date :12//Instalment end date:5

    ps.setObject(13, value.get(7));// interest_rate   :13//Interest Rate:7
    
    ps.setObject(14,value.get(10));// applicant_account_name :14 //Applicant Account Name:10
    
    ps.setObject(15,value.get(12) );// loan_cycle_status :15//Loan cycle status:12
    
    ps.setObject(16, this.getDBCurrentTime());// trn_time  :16
    
    ps.setObject(17,"0.0");// instalments_paid    :17
    
    ps.setObject(18, value.get(6));// instalment_amount    :18//Instalment Amount:6
    
    ps.setObject(19, value.get(8));// loan_tenure   :19 // Loan Tenure :8
    
    ps.setObject(20,  value.get(11));// applicant_account_number  :20//Applicant Account Name:11
    
    ps.setObject(21,  value.get(13));// inputter_id  :21//Inputter id:13
    
    ps.setObject(22,  "10000");// authoriser_id :22
    
    ps.setObject(23,  value.get(15));// gruop_id   :23//Portfolio owner:15
    
    ps.setObject(24, value.get(16));// GroupId  :24  //Group Id:16
    
    ps.setObject(25, value.get(17));// GroupName  :25 //Group Name:17
    
    ps.setObject(26, "NA");// SecurityLoan  :26
    
    ps.setObject(27, value.get(18));// OtherGroups2  :27//Source of repayment:18
    
    ps.setObject(28, value.get(19));// OtherGroups3   :28//Reason for borrowing:19
    
    ps.setObject(29, "NA");// OtherGroups4  :29
    
    ps.setObject(30, "0.0");// TotalInterestPaid :30
    
    ps.setObject(31, value.get(2));// TotalInterestRemaining :31
    
    ps.setObject(32, "0.0");// TotalPrincipalPaid :32
    
    ps.setObject(33, value.get(1));// TotalPrincipalRemaining:33
    
    ps.setObject(34, "0.0");// TotalAccumulatedInterestPaid:34
    
    ps.setObject(35, "0.0");// TotalAccumulatedInterestRemaining :35
    
    ps.setObject(36, "0.0");// TotalLoanPenaltyPaid  :36
    
    ps.setObject(37, "0.0");// TotalLoanPenaltyRemaining  :37
    
    ps.setObject(38, "0.0");// TotalAccruedInterestRemaining :38
    
    ps.setObject(39, "0.0");//  TotalAccruedInterestPaid  :39

             
        
    ps.execute();
    
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false);
     
    PreparedStatement ps1 =  cq.prepareStatement("INSERT INTO new_loan_appstore1 VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    ps1.setObject(1, value.get(14));// trn_id   :1//Transaction Id:14

    ps1.setObject(2, dbq.getDBCurrentDate());// trn_date    :2

    ps1.setObject(3, value.get(9));// loan_id    :3//Loan Id:9

    ps1.setObject(4, value.get(0));// total_instalments   :4//Total Instalments:0

    ps1.setObject(5,value.get(0));// remaining_instalments  :5//Total Instalments:0

    ps1.setObject(6, value.get(1));// princimpal_amount   :6//Total Principal Amount:1

    ps1.setObject(7, value.get(2));// total_interest     :7//Total interest Amount:2

    ps1.setObject(8, value.get(3));// total_loanAmount :8//Total loan Amount:3

    ps1.setObject(9, value.get(3));// balance_due  :9//Total balance remaining Amount:3

    ps1.setObject(10, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(4).toString()));// instalment_start_date :10//Instalment start date:4

    ps1.setObject(11,fmt.forDatabaseWithFullYearBeginningWithDate(value.get(4).toString()));// instalment_next_due_date:11//Instalment next date:4

    ps1.setObject(12, fmt.forDatabaseWithFullYearBeginningWithDate(value.get(5).toString()));// instalment_end_date :12//Instalment end date:5

    ps1.setObject(13, value.get(7));// interest_rate   :13//Interest Rate:7
    
    ps1.setObject(14,value.get(10));// applicant_account_name :14 //Applicant Account Name:10
    
    ps1.setObject(15,value.get(12) );// loan_cycle_status :15//Loan cycle status:12
    
    ps1.setObject(16, this.getDBCurrentTime());// trn_time  :16
    
    ps1.setObject(17,"0.0");// instalments_paid    :17
    
    ps1.setObject(18, value.get(6));// instalment_amount    :18//Instalment Amount:6
    
    ps1.setObject(19, value.get(8));// loan_tenure   :19 // Loan Tenure :8
    
    ps1.setObject(20,  value.get(11));// applicant_account_number  :20//Applicant Account Name:11
    
    ps1.setObject(21,  value.get(13));// inputter_id  :21//Inputter id:13
    
    ps1.setObject(22,  "10000");// authoriser_id :22
    
    ps1.setObject(23,  value.get(15));// gruop_id   :23//Portfolio owner:15
    
    ps1.setObject(24, value.get(16));// GroupId  :24  //Group Id:16
    
    ps1.setObject(25, value.get(17));// GroupName  :25 //Group Name:17
    
    ps1.setObject(26, "NA");// SecurityLoan  :26
    
    ps1.setObject(27, value.get(18));// OtherGroups2  :27//Source of repayment:18
    
    ps1.setObject(28, value.get(19));// OtherGroups3   :28//Reason for borrowing:19
    
    ps1.setObject(29, "NA");// OtherGroups4  :29
    
    ps1.setObject(30, "0.0");// TotalInterestPaid :30
    
    ps1.setObject(31, value.get(2));// TotalInterestRemaining :31
    
    ps1.setObject(32, "0.0");// TotalPrincipalPaid :32
    
    ps1.setObject(33, value.get(1));// TotalPrincipalRemaining:33
    
    ps1.setObject(34, "0.0");// TotalAccumulatedInterestPaid:34
    
    ps1.setObject(35, "0.0");// TotalAccumulatedInterestRemaining :35
    
    ps1.setObject(36, "0.0");// TotalLoanPenaltyPaid  :36
    
    ps1.setObject(37, "0.0");// TotalLoanPenaltyRemaining  :37
    
    ps1.setObject(38, "0.0");// TotalAccruedInterestRemaining :38
    
    ps1.setObject(39, "0.0");//  TotalAccruedInterestPaid  :39

     ps1.setObject(40, value1.get(0));// ClientExistanceCat :0
     
    ps1.setObject(41, value1.get(1));// BorrowingCategory :1

    ps1.setObject(42, value1.get(2));// LoanGroupId :2

    ps1.setObject(43, value1.get(3));// LoanGroupName :3

    ps1.setObject(44, value1.get(4));// LoanCycle :4

    ps1.setObject(45, value1.get(5));// LoanProduct :5

    ps1.setObject(46, value1.get(6));// MarketingChannel :6

    ps1.setObject(47, value1.get(7));// TenureType :7


    ps1.setObject(48, value1.get(8));// LoanPurpose :8
    
     ps1.setObject(49, value1.get(9));// PaymentHistory :9    
     
     
    ps1.setObject(50, value1.get(10));// ClientLoyalty :10

    ps1.setObject(51, value1.get(11));// SecurityUsage :11

    ps1.setObject(52, value1.get(12));// NumberOfSecurity :12

    ps1.setObject(53, value1.get(13));// EconomicWelbeingLevel :13

    ps1.setObject(54, value1.get(14));// PaymentSource :14

    ps1.setObject(55, value1.get(15));// AmountTakenCategory :15

    ps1.setObject(56, value1.get(16));// IncomeLevel :16


    ps1.setObject(57, value1.get(17));// EmploymentCat :17
    
    
     ps1.setObject(58, value1.get(18));// Occupation :18
     
    ps1.setObject(59, value1.get(19));// Sex :19

    ps1.setObject(60, value1.get(20));// MaritalStatus :20

    ps1.setObject(61, value1.get(21));// AgeGroup :21

    ps1.setObject(62, value1.get(22));// EducationLevel :22

    ps1.setObject(63, value1.get(23));// PreferedContactChannel :23

    ps1.setObject(64, value1.get(24));// Email :24

    ps1.setObject(65, value1.get(25));// BorrowerCharacter :25


    ps1.setObject(66, value1.get(26));// PhoneNumber :26
    
      ps1.setObject(67, "1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;Not Specified:4;Top Ups;Not Specified:5;Others (Please Specify);Not Specified");// OtherOne :31

    ps1.setObject(68, "NA");// OtherTwo :31

    ps1.setObject(69, "NA");// OtherThree :31

    ps1.setObject(70, "NA");// OtherFour :31

    ps1.setObject(71, "NA");// OtherFive :31
    
    ps1.execute();
    cq.setAutoCommit(true); 
    
//     cq.setAutoCommit(false); 
//     
//     
//     
//     
//      cq.setAutoCommit(true); 
    
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

//    JOptionPane.showMessageDialog(c, value.get(8).toString());
    
    if("1.0 MONTHS".equalsIgnoreCase(value.get(8).toString())){
    
    this.createInitialInterestML(value.get(9).toString(), value.get(4).toString(), value.get(1).toString(), value.get(2).toString(),c);
    
    }
    
    
    
    
    }
    
    
    
//    private void updateMaster(){
//    
//    
//    
//    }
        
   public synchronized void  updateClosingNotes(String accountNumber,String closingNotes,Component c){
 
//       JOptionPane.showMessageDialog(c, closingNotes);
//        JOptionPane.showMessageDialog(c, "newloan"+accountNumber);
   
//         String UpdateQuary = "UPDATE new_loan_appstore1 SET commentsClosing=? WHERE loan_id=?";  
         try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE new_loan_appstore1 SET commentsClosing=? WHERE loan_id=?";  
    PreparedStatement pstool = cq.prepareStatement(UpdateQuary);
    pstool.setObject(1,closingNotes);
    pstool.setObject(2,"newloan"+accountNumber);
    pstool.execute();

    cq.setAutoCommit(true);     
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  
   }
   
   
     public String getTheClosingNotes(String accountNumber,Component c){
// JOptionPane.showMessageDialog(c, accountNumber);
         String closingNotes="1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;Not Specified:4;Top Ups;Not Specified:5;Others (Please Specify);Not Specified";
         
         try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT commentsClosing FROM  new_loan_appstore1 WHERE loan_id="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  
    while (rst3.next()){
   closingNotes=rst3.getString("commentsClosing");
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
//JOptionPane.showMessageDialog(c, closingNotes);
    return closingNotes;

     
     }
    public ObjectTableModel loanManagementTableFillt(JTable table, ArrayList<String> headers){

    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT trn_id,loan_id,applicant_account_name,princimpal_amount, TotalPrincipalRemaining,TotalInterestRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining,balance_due,loan_cycle_status,trn_date,gruop_id FROM  new_loan_appstore ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("applicant_account_name"));
    data4.add(3, df2.format(rst3.getDouble("princimpal_amount")));
     data4.add(4, df2.format(rst3.getDouble("TotalPrincipalRemaining")));
    data4.add(5, df2.format(rst3.getDouble("TotalInterestRemaining")));
      data4.add(6, df2.format(rst3.getDouble("TotalAccumulatedInterestRemaining")));
    data4.add(7, df2.format(rst3.getDouble("TotalLoanPenaltyRemaining")));
    data4.add(8, df2.format(rst3.getDouble("balance_due")));
    data4.add(9, rst3.getString("loan_cycle_status"));
    data4.add(10, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(11, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }


    model= new ObjectTableModel( data5, headers);

    table.setModel(model);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return model;

    } 
    
  public ObjectTableModel loanManagementTableFilltWrittenOffLoans(JTable table, ArrayList<String> headers){

    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT trn_id,loan_id,applicant_account_name,princimpal_amount, TotalPrincipalRemaining,TotalInterestRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining,balance_due,loan_cycle_status,trn_date,gruop_id FROM  new_loan_appstore WHERE loan_cycle_status="+"'"+"WrittenOff"+"'"+" ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("applicant_account_name"));
    data4.add(3, df2.format(rst3.getDouble("princimpal_amount")));
     data4.add(4, df2.format(rst3.getDouble("TotalPrincipalRemaining")));
    data4.add(5, df2.format(rst3.getDouble("TotalInterestRemaining")));
      data4.add(6, df2.format(rst3.getDouble("TotalAccumulatedInterestRemaining")));
    data4.add(7, df2.format(rst3.getDouble("TotalLoanPenaltyRemaining")));
    data4.add(8, df2.format(rst3.getDouble("balance_due")));
    data4.add(9, rst3.getString("loan_cycle_status"));
    data4.add(10, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(11, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }


    model= new ObjectTableModel( data5, headers);

    table.setModel(model);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return model;

    }   
    
    
     public loanManagementModel loanManagementTableFilltActiveLoans(JTable table, ArrayList<String> headers,Component c){
loanManagementModel    modelM=null;
    try {
List    data5M= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT new_loan_appstore.trn_id,"
             + "new_loan_appstore.applicant_account_name,"
           + "new_loan_appstore.OtherGroups2,"
              + "new_loan_appstore.interest_rate,"
           + "new_loan_appstore.princimpal_amount, "
           + "new_loan_appstore.TotalPrincipalRemaining,"
           + "new_loan_appstore.TotalInterestRemaining,"
           + "new_loan_appstore.TotalLoanPenaltyRemaining,"
           + "new_loan_appstore.balance_due,"
           + "new_loan_appstore1.loan_tenure,"
           + "new_loan_appstore.trn_date,new_loan_appstore.gruop_id,new_loan_appstore.loan_cycle_status FROM  new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE (new_loan_appstore.loan_cycle_status="+"'"+"Disbursed"+"'"+" OR new_loan_appstore.loan_cycle_status="+"'"+"Applied"+"'"+" OR new_loan_appstore.loan_cycle_status="+"'"+"Renewed"+"')  ORDER BY new_loan_appstore.trn_id DESC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
 List   data4m    =new ArrayList();
    data4m.add(0, rst3.getString("trn_id"));
       data4m.add(1, rst3.getString("applicant_account_name"));
    data4m.add(2, rst3.getString("OtherGroups2"));
      data4m.add(3, df2.format(rst3.getDouble("interest_rate")));
    data4m.add(4, df2.format(rst3.getDouble("princimpal_amount")));
     data4m.add(5, df2.format(rst3.getDouble("TotalPrincipalRemaining")));
    data4m.add(6, df2.format(rst3.getDouble("TotalInterestRemaining"))); 
    data4m.add(7, df2.format(rst3.getDouble("TotalLoanPenaltyRemaining")));
    data4m.add(8, df2.format(rst3.getDouble("balance_due")));
    data4m.add(9, rst3.getString("loan_tenure"));
    data4m.add(10, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4m.add(11, rst3.getString("gruop_id"));
      data4m.add(12, rst3.getString("loan_cycle_status"));
//      JOptionPane.showMessageDialog(c, "size"+data4m.size());
    data5M.add(k, data4m);   
    k++;
    }


modelM= new loanManagementModel( data5M, headers);

    table.setModel(modelM);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return modelM;

    } 
     
    public ObjectTableModel loanManagementTableFilltClosedLoans(JTable table, ArrayList<String> headers){

    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT new_loan_appstore.trn_id,"
             + "new_loan_appstore.applicant_account_name,"
           + "new_loan_appstore.OtherGroups2,"
              + "new_loan_appstore.interest_rate,"
           + "new_loan_appstore.princimpal_amount, "
           + "new_loan_appstore.TotalPrincipalRemaining,"
           + "new_loan_appstore.TotalInterestRemaining,"
           + "new_loan_appstore.TotalLoanPenaltyRemaining,"
           + "new_loan_appstore.balance_due,"
           + "new_loan_appstore1.loan_tenure,"
           + "new_loan_appstore.trn_date,new_loan_appstore.gruop_id,new_loan_appstore.loan_cycle_status FROM  new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE new_loan_appstore.loan_cycle_status="+"'"+"Completed"+"'"+"ORDER BY new_loan_appstore.trn_id DESC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
       data4.add(1, rst3.getString("applicant_account_name"));
    data4.add(2, rst3.getString("OtherGroups2"));
      data4.add(3, df2.format(rst3.getDouble("interest_rate")));
    data4.add(4, df2.format(rst3.getDouble("princimpal_amount")));
     data4.add(5, df2.format(rst3.getDouble("TotalPrincipalRemaining")));
    data4.add(6, df2.format(rst3.getDouble("TotalInterestRemaining"))); 
    data4.add(7, df2.format(rst3.getDouble("TotalLoanPenaltyRemaining")));
    data4.add(8, df2.format(rst3.getDouble("balance_due")));
    data4.add(9, rst3.getString("loan_tenure"));
    data4.add(10, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(11, rst3.getString("gruop_id"));
      data4.add(12, rst3.getString("loan_cycle_status"));
    data5.add(k, data4);   
    k++;
    }


    model= new ObjectTableModel( data5, headers);

    table.setModel(model);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return model;

    } 
    

    
    
    public ObjectTableModel loanManagementTableFilltIndividual(JTable table, ArrayList<String> headers,String accountName){

    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore  Where applicant_account_name="+"'"+accountName+"'"+"  "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }


    model= new ObjectTableModel( data5, headers);

    table.setModel(model);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return model;

    } 
    
     public synchronized void loanManagementTableFil(JTable table, String accountNumber){
 
         column1= new ArrayList<>();
         
      column1.add("Trn Id");
      column1.add("Loan Id");
      column1.add("Princimpal Amount");
      column1.add("Last Trn Date");
 
    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT trn_id,loan_id,princimpal_amount,trn_date FROM new_loan_appstore  WHERE (applicant_account_number="+accountNumber+" "+"AND loan_cycle_status="+"'"+"Completed"+"'"+")"+" "+"ORDER BY trn_id ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
    data4.add(2, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(3, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));

    data5.add(k, data4);   
    k++;
    }

      model= new ObjectTableModel( data5, column1);
      
           table.setModel(model);
           
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }



    }
    
    public boolean loanDoesNotExist(String accountNumber){
 
 boolean doesnotExist=false; int theAccounts=0;
 
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT COUNT(trn_id) AS loans FROM new_loan_appstore  WHERE applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 
    while (rst3.next()){
  
   theAccounts=parseInt(rst3.getString("loans"));
   
    }


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
if(theAccounts==0){

doesnotExist=true;
}else{
doesnotExist=false;
}

return  doesnotExist;
    }
    
    public synchronized void deleteLoan(String account){
    dropClosedLoan(account);
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("DELETE FROM new_loan_appstore WHERE (applicant_account_number ="+account+" "+"AND"+" "+"loan_cycle_status="+"'"+"Completed"+"'"+")");

    ps.execute();

    cq.setAutoCommit(true);
    
      cq.setAutoCommit(false);
    PreparedStatement ps1 =  cq.prepareStatement("DELETE FROM new_loan_appstore1 WHERE (applicant_account_number ="+account+" "+"AND"+" "+"loan_cycle_status="+"'"+"Completed"+"'"+")");

    ps1.execute();

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    }


    public synchronized void deleteLoanAppStore(String account){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("DELETE FROM new_loan_appstore WHERE applicant_account_number ="+account);

    ps.execute();

    cq.setAutoCommit(true);
      cq.setAutoCommit(false);
    PreparedStatement ps1 =  cq.prepareStatement("DELETE FROM new_loan_appstore1 WHERE applicant_account_number ="+account);

    ps1.execute();

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    }

    public synchronized void dropClosedLoan(String account1){
    int n=1;while(true){

    if(testTable("closedloan"+n+account1)){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("DROP TABLE"+" "+"closedloan"+n+account1);

    ps.execute();

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }}else{break;}
    n++;}
    }

//    public synchronized void dropTableLoan(String account1){
//
//    try {
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);
//    PreparedStatement ps = cq.prepareStatement("DROP TABLE"+" "+"newloan"+account1);
//
//    ps.execute();
//
//    cq.setAutoCommit(true);
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) {
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    }

    public synchronized void comboFillWithUserID(JComboBox jComboBox1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT trn_id FROM new_loan_appstore ORDER BY trn_id ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("trn_id"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);




    }
    public synchronized void comboFillWithLoanID(JComboBox jComboBox1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT loan_id FROM new_loan_appstore ORDER BY loan_id DESC";
    PreparedStatement ps =  cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("loan_id"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);




    }



    public synchronized void comboFillWithBorrowName(JComboBox jComboBox1){
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT applicant_account_name FROM new_loan_appstore ORDER BY applicant_account_name ASC";
    PreparedStatement ps =  cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("applicant_account_name"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);



    }


    public synchronized void  comboFillWithAccountNumber(JComboBox jComboBox1){
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT applicant_account_number FROM new_loan_appstore  ORDER BY applicant_account_number ASC";
    PreparedStatement ps =  cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("applicant_account_number"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);



    }


    public synchronized void  comboFillWithDisburseStatus(JComboBox jComboBox1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT loan_cycle_status FROM new_loan_appstore ORDER BY loan_cycle_status ASC";
    PreparedStatement ps =  cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("loan_cycle_status"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);


    }



    public synchronized void   comboFillWithDisburseDate(JComboBox jComboBox1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT trn_date FROM new_loan_appstore ORDER BY trn_date ASC";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);


    }
 public synchronized void   comboFillWithPortfolioOwner(JComboBox jComboBox1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT DISTINCT gruop_id FROM new_loan_appstore ORDER BY gruop_id ASC";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    data3 = new ArrayList<>();
    int i=0;
    while(rst3.next()){

    data3.add(i, rst3.getString("gruop_id"));

    i++;
    }
    rst3.close();
    ps.close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    modelcombo = new MyComboBoxModel(data3);
    jComboBox1.setModel(modelcombo);


    }

    public ObjectTableModel loanManagementTableFillSpecific(JTable table, ArrayList<String> headers,String selectItem,String whereItem){

   
        switch(selectItem){

    case "SR.NO":

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    data5= new ArrayList<>();

    String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE trn_id LIKE"+"'"+whereItem+"%"+"'"+"or trn_id LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }

if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    break;

    case "LOAN ID":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);    
    data5= new ArrayList<>();

    String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE loan_id LIKE"+"'"+whereItem+"%"+"'"+"or loan_id LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }
if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    


    break;

    case "BORROWER'S A/C NAME":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    data5= new ArrayList<>();

     String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE applicant_account_name LIKE"+"'"+whereItem+"%"+"'"+"or applicant_account_name LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }

if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    
    break;

    case "PRINCIPAL AMOUNT":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    data5= new ArrayList<>();

     String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE princimpal_amount LIKE"+"'"+whereItem+"%"+"'"+"or princimpal_amount LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }

if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    
    break; 


    case "LOAN CYCLE STATUS":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    data5= new ArrayList<>();

     String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE loan_cycle_status LIKE"+"'"+whereItem+"%"+"'"+"or loan_cycle_status LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
    data4    =new ArrayList();
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }


if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    
    break;

    case "LAST MODIFIED DATE":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    data5= new ArrayList<>();

     String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE trn_date LIKE"+"'"+whereItem+"%"+"'"+"or trn_date LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }


   if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    
    break;
     case "PORTFOLIO OWNER":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);    
    data5= new ArrayList<>();

     String query = "SELECT trn_id,loan_id,GroupId,applicant_account_name,GroupName,princimpal_amount,loan_cycle_status,trn_date,gruop_id FROM new_loan_appstore   WHERE gruop_id LIKE"+"'"+whereItem+"%"+"'"+"or gruop_id LIKE"+"'"+"%"+whereItem+"'"+" "+"ORDER BY applicant_account_name ASC";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){
     data4    =new ArrayList();
    data4.add(0, rst3.getString("trn_id"));
    data4.add(1, rst3.getString("loan_id"));
     data4.add(2, rst3.getString("GroupId"));
    data4.add(3, rst3.getString("applicant_account_name"));
     data4.add(4, rst3.getString("GroupName"));
    data4.add(5, parseDouble(df2.format(rst3.getDouble("princimpal_amount"))) );
    data4.add(6, rst3.getString("loan_cycle_status"));
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
     data4.add(8, rst3.getString("gruop_id"));
    data5.add(k, data4);   
    k++;
    }
 if( data5.isEmpty()){
       
      this.loanManagementTableFillt(table, headers);
       
       }else{
      model= new ObjectTableModel( data5, headers);
    table.setModel(model);
   
       TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
       }

   


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    


    break;
    }      
return model;
    }




  
    
    
    
public List getExistingInstalments(String accountNumber){
     List  eixisinfInstal= new ArrayList<>();
try{
 Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);    
 

     String query = "SELECT instalment_no FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  
    while (rst3.next()){
   
    eixisinfInstal.add(rst3.getInt("instalment_no"));   

    }
 if( eixisinfInstal.isEmpty()){
       
       eixisinfInstal.add(rst3.getInt(999)); 
       
       }
       


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }    


return eixisinfInstal;
}
        public boolean  waiveAllInterestAndAccumulatedInterest(String userAccount){
 long nextDueDateAaP = 0, todayDatezAaP = 0;boolean done=false;
List existindInstalments=getExistingInstalments(userAccount);

   
if(parseInt((existindInstalments.get(0).toString()))!=999){    


    for(Object instalment:existindInstalments){
    
double existingRemainingInstalmentxapP=remainingInstalmentNow(userAccount,parseInt(instalment.toString()));



    }
    done=true;
}      
//   
    return done;
    }
    
    
    public String getDBCurrentTime(){


    String thistime=null;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    thistime= sdf.format(cal.getTime()) ; 

    return thistime;
    }

    public String getDBCurrentDate() {


    String thistime=" ";
    thistime=fmt.dateConverterForDatabase(System.currentTimeMillis( ));

    return thistime;
    }

    public String testAmortizabilityRepayment(String accountNumber, String amount){
    String amort=""; Double addme=0.0;


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT instalment_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ("+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


    addme =addme+parseDouble(rst3.getString("instalment_amount").replace(",", ""));     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    amort=addme.toString();
    if(parseDouble(amount)<=parseDouble(amort)){
    amort =0.0+"";
    }

    return amort;
    }
    
    public String getRemainingAmountRepayment(String accountNumber){
    String amort=""; Double addme=0.0;


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT instalment_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ("+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


    addme =addme+parseDouble(rst3.getString("instalment_amount").replace(",", ""));     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    amort=addme.toString();
   

    return amort;
    }
    
    
    public String loanInstalment(String accountNumber,String instalmentNumber){


    String intalment="";

    if(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","partlyPa"+accountNumber+".txt")).equalsIgnoreCase("PPED")){


    intalment= fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "newLoanInstalment"+accountNumber+".txt"));

    }else{
    intalment=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumber+".txt")).split("[,]", 15)[6]; 
    }

    return intalment;

    }

    public String loanInstalment(String accountNumber){


    String intalment="";
    if(!fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumber+".txt")).equalsIgnoreCase("1")){

    intalment=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumber+".txt")).split("[,]", 15)[6]; 
    }
    return intalment;

    }
    public String interestInstalment(String accountNumber,String trn){

    String intalment=""; 
//            principal="", instalmentAount="";Double difference=0.0;

//    if(fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan1.txt"))==1){
//
//    intalment=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "interestInstalment"+accountNumber+ ".txt"));//The interest instalment created during the loan amortization process
//
//
//    }else if(fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan3.txt"))==1||fios.intFileReader(fios.createFileName("persistence", "interestPlan", "flatInterestPlan2.txt"))==1){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  


    String query = "SELECT interest_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+trn;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

   while(rst3.next()){

    intalment  =rst3.getString("interest_amount");
    
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

//    difference=parseDouble(instalmentAount)-parseDouble(principal);
//
//    intalment=  difference.toString();

//    }
//fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),intalment+";"+trn);
    return intalment;

    }
    public String princimpalInstalment(String accountNumber){

    String intalment="";
    fios.forceFileExistanceZero(fios.createFileName("loanApplication", "amortValues", "princimpalInstalment"+accountNumber+ ".txt"));
    intalment=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "princimpalInstalment"+accountNumber+ ".txt")); 
    return intalment;
    }
    
     public String princimpalInstalmentExact(String accountNumber,String trnId){
//      this.instalmentNextDueDateNow(accountNumber, trnId)
    String intalment="";
  
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 


    String query = "SELECT princimpal_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+trnId;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.first()){
    intalment =rst3.getString("princimpal_amount");  


    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  

    return intalment;
    }
    public String trnId(String accountNumber){
    String intalment="";

    intalment=fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumber+".txt")).split("[,]", 16)[14].replace(",", "");
   
    return intalment;
   
    }


    public synchronized void setLoanInstament(String accountNumber,String amount, String trn){

    fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "instalment"+accountNumber+trn), loanInstalmentExact(accountNumber,trn).replace(",", ""));

    }

    public String checkforSubtractability(String accountNumber, String tid){
    String status="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 

    String query = "SELECT instalment_status FROM"+" "+"newloan"+accountNumber+" "+"WHERE instalment_no="+tid; 

    try (PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery()) {
    if(rst3.first()){
    status =rst3.getString("instalment_status");


    }
    }           cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    return status;
    }

    public String loanInstalmentExact(String accountNumber,String tid) {


    String status="";
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 


    String query = "SELECT instalment_amount FROM"+" "+"newloan"+accountNumber+" "+"WHERE instalment_no="+tid;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.first()){
    status =rst3.getString("instalment_amount");  


    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    return status;




    }


    public synchronized void  updateInstalment(String accountNumber, String amount, String trn){


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE"+" "+"newloan"+accountNumber+" "+"SET instalment_amount=? WHERE instalment_no=? ";
    PreparedStatement pstool = cq.prepareStatement(UpdateQuary);
    pstool.setObject(1,amount.replace(",", ""));
    pstool.setObject(2, trn);
    pstool.execute();

    cq.setAutoCommit(true);     
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    }

    public String loanInstalmentPaidExact(String accountNumber,String tid) {

    String status="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  


    String query = "SELECT instalment_paid FROM"+" "+"newloan"+accountNumber+" "+"WHERE instalment_no="+tid;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.first()){
    status =rst3.getString("instalment_paid");  


    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    return status;




    } 


    public String remaininBalance(String accountNumber){

    String amort="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(instalment_amount)AS total FROM new_loan_appstoreamort WHERE (master2_id="+"'"+"newloan"+accountNumber+"'"+ "AND NOT instalment_status="+"'P')";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    amort =rst3.getString("total");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    return amort; 

    }

    public String closeLoanAccount(String accountNumber){
        
         dbq.updateBorrowingStatus(accountNumber,"NB");
    Integer n= fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "closedAccount"+accountNumber));

    String theitem="closedloan"+n+accountNumber;
clearData(accountNumber);
//    try {
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);  
//    String query = "ALTER TABLE"+" "+"newloan"+accountNumber+" "+"RENAME"+" "+theitem;
//    PreparedStatement ps = cq.prepareStatement(query);
//    ps.execute();
//    cq.setAutoCommit(true);
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }
    n=n+1;
    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "closedAccount"+accountNumber), n.toString()); 
    return theitem;
    }

     public String closeRejectedLoanAccount(String accountNumber){
         
    Integer n= fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "closedRejectedAccount"+accountNumber));

    String theitem="rejectedloan"+n+accountNumber;

//    try {
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);  
//    String query = "ALTER TABLE"+" "+"newloan"+accountNumber+" "+"RENAME"+" "+theitem;
//    PreparedStatement ps = cq.prepareStatement(query);
//    ps.execute();
//    cq.setAutoCommit(true);
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }
    n=n+1;
    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "closedRejectedAccount"+accountNumber), n.toString()); 
    return theitem;
    }
    
     public String closeWrittenOffLoanAccount(String accountNumber){
         
//    Integer n= fios.intFileReader(fios.createFileName("loanRepayment", "partlyPaid", "closedWrittenAccount"+accountNumber));

    String theitem="writtenOffloan"+accountNumber;

//    try {
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);  
//    String query = "ALTER TABLE"+" "+"newloan"+accountNumber+" "+"RENAME"+" "+theitem;
//    PreparedStatement ps = cq.prepareStatement(query);
//    ps.execute();
//    cq.setAutoCommit(true);
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }
//    n=n+1;
//    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "closedWrittenAccount"+accountNumber), n.toString()); 
    return theitem;
    }
    
    public boolean checkIfCompleted(String accountNumber){

    boolean itemThere=true;
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 

    String query = "SELECT instalment_amount FROM new_loan_appstoreamort WHERE (master2_id="+"'"+"newloan"+accountNumber+"'"+ "AND NOT (instalment_status="+"'P'"+" OR instalment_status="+"'TP'"+"))";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst=ps.executeQuery();


    if (rst.next()==true)
    {
    itemThere =false;

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);


    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    return  itemThere;

    }
    
public boolean loanCompleted(String accountNumber){

    boolean itemThere=false; double theRemain=0.0;
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 

    String query = "SELECT SUM(InstalmentRemaining) AS instalmentSata FROM new_loan_appstoreamort WHERE (master2_id="+"'"+"newloan"+accountNumber+"'"+ "AND NOT instalment_status="+"'P')";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst=ps.executeQuery();


    if (rst.next())
    {
    theRemain =rst.getDouble("instalmentSata");

    }
if(theRemain<2){

itemThere=true;

}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);


    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return  itemThere;

    }
    public boolean  checkIfAccountClosed(String userAccount){
    boolean closed=false;String itemThere="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT loan_cycle_status FROM"+" "+"new_loan_appstore"+" "+"WHERE applicant_account_number="+userAccount;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst=ps.executeQuery();


    while (rst.next())
    {
    itemThere =rst.getString("loan_cycle_status");

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);


    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    if( itemThere.equals("Completed")){closed=true;}else{closed=false;}

    return  closed;

    }

    public synchronized void loanClosedTableFill(JTable table, ArrayList<String> headers,String closedLoanAccount){
String instalmentPaid="0.0";
    try {
  List<List>  data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT * FROM new_loan_appstoreamort WHERE master1_id="+"'"+closedLoanAccount+"'";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){

instalmentPaid=rst3.getString("instalment_paid").replace(",", "");
if(instalmentPaid.equalsIgnoreCase("NY")){


instalmentPaid="0.0";

}

  List  data4    =new ArrayList();
    data4.add(0, rst3.getString("instalment_no"));
    data4.add(1, df2.format(parseDouble(rst3.getString("princimpal_amount").replace(",", ""))));
    data4.add(2, df2.format(parseDouble(rst3.getString("interest_amount").replace(",", ""))));
    data4.add(3, df2.format(parseDouble(rst3.getString("instalment_amount").replace(",", ""))));  

    data4.add(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rst3.getString("instalment_due_date")));
    data4.add(5, rst3.getString("instalment_status"));
    data4.add(6, instalmentPaid);
    data4.add(7, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_paid_date")));
    data4.add(8, rst3.getString("instalment_paid_variance"));
    data5.add(k, data4);   
    k++;
    }


loanAmortDetailsModel    modelh= new loanAmortDetailsModel( data5, headers);
    table.setModel(modelh);


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 

     public synchronized void loanRunningLoanFill(JTable table, ArrayList<String> headers,String closedLoanAccount){
     
         String instalmentPaid="0.0";
    try {
   List<List> data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT * FROM new_loan_appstoreamort WHERE master1_id="+"'"+closedLoanAccount+"'";
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){

instalmentPaid=rst3.getString("instalment_paid").replace(",", "");

if(instalmentPaid.equalsIgnoreCase("NY")){


instalmentPaid="0.0";

}
//    column1.add("INST.NO");
//       column1.add("P AMT");
//       column1.add("P PD");
//       column1.add("I AMT");
//       column1.add("I PD");
//        column1.add("ACCI AMT");
//       column1.add("ACCI PD");
//       column1.add("LP AMT");
//       column1.add("LP LP");
//       column1.add("INST AMT");
//       column1.add("OINST AMT");
//        column1.add("INST. P");
//       column1.add("INST. DD");
//       column1.add("INST. PS"); 
//      column1.add("INST. PD");
//      column1.add("PV (DAYS)");
   List data4    =new ArrayList();
    data4.add(0, rst3.getString("instalment_no"));
    data4.add(1, df2.format(parseDouble(rst3.getString("princimpal_amount").replace(",", ""))));
    data4.add(2, df2.format(parseDouble(rst3.getString("PrincipalPaid").replace(",", ""))));
    data4.add(3, df2.format(parseDouble(rst3.getString("interest_amount").replace(",", ""))));
    data4.add(4, df2.format(parseDouble(rst3.getString("InterestPaid").replace(",", ""))));
     data4.add(5, df2.format(parseDouble(rst3.getString("AccumulatedInterest").replace(",", ""))));
    data4.add(6, df2.format(parseDouble(rst3.getString("AccumulatedInterestPaid").replace(",", ""))));
    data4.add(7, df2.format(parseDouble(rst3.getString("LoanPenalty").replace(",", ""))));
    data4.add(8, df2.format(parseDouble(rst3.getString("LoanPenaltyPaid").replace(",", ""))));
    data4.add(9, df2.format(parseDouble(rst3.getString("instalment_amount").replace(",", ""))));  
    data4.add(10, df2.format(parseDouble(rst3.getString("InstalmentRemaining").replace(",", ""))));
    data4.add(11, instalmentPaid);
    data4.add(12,fmt.fromDatabaseWithDashSeperatorBeginningWithYear( rst3.getString("instalment_due_date")));
    data4.add(13, rst3.getString("instalment_status"));
    data4.add(14, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_paid_date")));
    data4.add(15, rst3.getString("instalment_paid_variance"));
    data5.add(k, data4);   
    k++;
    }


  loanAmortDetailsModelb  modelb= new loanAmortDetailsModelb( data5, headers);
    table.setModel(modelb);


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
    
    
    
     public synchronized void loanWrittenOffLoanFill(JTable table, ArrayList<String> headers,String writtenOffLoanxx){
      
    try {
    data5= new ArrayList<>();
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT * FROM"+" "+writtenOffLoanxx;
    PreparedStatement ps =cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    int k=0;
    while (rst3.next()){


    data4    =new ArrayList();
    data4.add(0, rst3.getString("instalment_no"));
    data4.add(1, fmt.formatForStatementNumbers(rst3.getString("PrincipalRemaining").replace(",", "")));
    data4.add(2, fmt.formatForStatementNumbers(rst3.getString("InterestRemaing").replace(",", "")));
    data4.add(3, fmt.formatForStatementNumbers(rst3.getString("AccumulatedInterestRemaining").replace(",", "")));
    data4.add(4, fmt.formatForStatementNumbers(rst3.getString("LoanPenaltyRemaining").replace(",", "")));
     data4.add(5, fmt.formatForStatementNumbers(rst3.getString("InstalmentRemaining").replace(",", "")));
    data4.add(6,fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_due_date")));
    data4.add(7, fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
    data5.add(k, data4);   
    k++;
    }


    model= new ObjectTableModel( data5, headers);
    table.setModel(model);


    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
    
    public String getAccountNameClosedLoans(String loanID) {
    String accountName="";

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT applicant_account_name FROM  new_loan_appstore WHERE loan_id="+"'"+loanID+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3=ps.executeQuery();
    if (rst3.next()){
    accountName=rst3.getString("applicant_account_name");
    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    return  accountName;
    }   

public synchronized void waiveInterest(String accountNumber,Component c){
    
   fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", "throughWaive.txt"), "77");   
    
List<List> pendingInterest=this.getDetailsOfInterestToWaive(accountNumber);
for(List theDetaisl:pendingInterest){

updateNewInterestNow(accountNumber,parseInt(theDetaisl.get(0).toString()),parseDouble(theDetaisl.get(1).toString()),c);

}

if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}

}
public synchronized void waiveAccumulatedInterest(String accountNumber,Component c){
    
      fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", "throughWaive.txt"), "77");
List<List> pendingInterest=this.getDetailsOfAccumulatedInterestToWaive(accountNumber);
for(List theDetaisl:pendingInterest){

updateNewAccumulatedInterestNow(accountNumber,parseInt(theDetaisl.get(0).toString()),parseDouble(theDetaisl.get(1).toString()),c);

}


if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}


}
public synchronized void waiveLoanPenalty(String accountNumber,Component c){
     fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", "throughWaive.txt"), "77");
List<List> pendingInterest=this.getDetailsOfLoanPenaltyToWaive(accountNumber);
for(List theDetaisl:pendingInterest){

updateNewLoanPenaltyNow(accountNumber,parseInt(theDetaisl.get(0).toString()),parseDouble(theDetaisl.get(1).toString()),c);

}


if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}


}
public synchronized void waiveInterestAccumPenalAsTable(List<List> waiveDetails,String type,Component c){
    
int instalmentNo=0,t=0;

double interest=0.0,penalty=0.0,accumulatedInterest=0.0;

String accountNumber=fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt"));

switch(type){
    case "all":
for(List wave2:waiveDetails){

if(wave2.size()>0){
    
    instalmentNo=parseInt(wave2.get(0).toString());
    
    interest=parseDouble(wave2.get(1).toString());
    
    penalty=parseDouble(wave2.get(2).toString());
    
    accumulatedInterest=parseDouble(wave2.get(3).toString());
            
            if(penalty>0){
                
updateNewLoanPenaltyNow(accountNumber,instalmentNo,penalty,c);

if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
}
            
                 if(interest>0){
                     
 
  updateNewInterestNow(accountNumber,instalmentNo,interest,c);
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   
//    String trnId=trnId(accountNumber);

    clearData(accountNumber);
  
   
   
}
                 }
                 
                  if(accumulatedInterest>0){
                      
 updateNewAccumulatedInterestNow(accountNumber,instalmentNo,accumulatedInterest,c);  
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                  }
                  
}
}
    
    break;
    case "interestOnly":
        
        for(List wave2:waiveDetails){

if(wave2.size()>0){
    
  instalmentNo=parseInt(wave2.get(0).toString());
    
    interest=parseDouble(wave2.get(1).toString());
    
   
     
                 if(interest>0){
                     
 
  updateNewInterestNow(accountNumber,instalmentNo,interest,c);
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                 }
                                   
}
}
        
        break;
        
    case "acuuminterestOnly":
         for(List wave2:waiveDetails){

if(wave2.size()>0){
    
  instalmentNo=parseInt(wave2.get(0).toString());

    
    accumulatedInterest=parseDouble(wave2.get(3).toString());
    
        if(accumulatedInterest>0){
                      
 updateNewAccumulatedInterestNow(accountNumber,instalmentNo,accumulatedInterest,c);  
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                  }
                                   
}
}

        break;
        
        
    case "loanPenaltyOnly":
        for(List wave2:waiveDetails){

if(wave2.size()>0){
    
 instalmentNo=parseInt(wave2.get(0).toString());
    
    
    penalty=parseDouble(wave2.get(2).toString());
 
    
            if(penalty>0){
                
updateNewLoanPenaltyNow(accountNumber,instalmentNo,penalty,c);

if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
}
                                   
}
}
        
        
        break;   
        
    case "interestAndAccumulatedOnly":
    for(List wave2:waiveDetails){

if(wave2.size()>0){
    
    instalmentNo=parseInt(wave2.get(0).toString());
    
    interest=parseDouble(wave2.get(1).toString());
    
    
    accumulatedInterest=parseDouble(wave2.get(3).toString());
  
            
                 if(interest>0){
                     
 
  updateNewInterestNow(accountNumber,instalmentNo,interest,c);
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                 }
                 
                  if(accumulatedInterest>0){
                      
 updateNewAccumulatedInterestNow(accountNumber,instalmentNo,accumulatedInterest,c);  
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                  }
                  
}
}    
        
        
        break;
        
    case "AccumulatedAndLoanPenaltyOnly":
     for(List wave2:waiveDetails){

if(wave2.size()>0){
    
    instalmentNo=parseInt(wave2.get(0).toString());
    
    
    penalty=parseDouble(wave2.get(2).toString());
    
    accumulatedInterest=parseDouble(wave2.get(3).toString());
            
            if(penalty>0){
                
updateNewLoanPenaltyNow(accountNumber,instalmentNo,penalty,c);

if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
}
            
                 
                  if(accumulatedInterest>0){
                      
 updateNewAccumulatedInterestNow(accountNumber,instalmentNo,accumulatedInterest,c);  
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                  }
                  
}
}   

        break;
        
    case "interestAndPenaltyOnly":
        for(List wave2:waiveDetails){

if(wave2.size()>0){
    
    instalmentNo=parseInt(wave2.get(0).toString());
    
    interest=parseDouble(wave2.get(1).toString());
    
    penalty=parseDouble(wave2.get(2).toString());
    
            
            if(penalty>0){
                
updateNewLoanPenaltyNow(accountNumber,instalmentNo,penalty,c);

if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
}
            
                 if(interest>0){
                     
 
  updateNewInterestNow(accountNumber,instalmentNo,interest,c);
 
 if(this.loanCompleted(accountNumber)){
    
 String newLoanId=   closeLoanAccount(accountNumber); 

        List closedDetails=new ArrayList();
        closedDetails.add(newLoanId);//New loan Id:0
        closedDetails.add("Completed");//Loan cycle status:1
        closedDetails.add(accountNumber);//Borrower Account Number:1
   updateClosedLoan(closedDetails);
   clearData(accountNumber);
}
                 }
                 
        
                  
}
}
        
        break;
    
    }
}

public void createTheInterest(List<List> theDetails,Component c){

for(List itDer:theDetails){

int instalmentNumber=parseInt(itDer.get(0).toString());

  double amount=parseDouble(itDer.get(1).toString());  
  
  String dueDate=itDer.get(2).toString();
    
   createInterest2(fios.stringFileReader(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt")),amount,dueDate,instalmentNumber,c);
    
    
    

}



}


private void clearData(String accountNumber){
Integer ed=256;

    fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "endLoan"+accountNumber+".txt"), ed.toString());
    fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "amortizeStatus"+accountNumber+".txt"));     
    fios.deleteFileExistance(fios.createFileName("loanApplication","amortValues","amortizeStatus"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation","collDisplay"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanApplication" ,"collateralSDocumentation","colls"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanApplication", "collateralSDocumentation","docDisplay"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanApplication" ,"collateralSDocumentation","docs"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfCollateral"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfDocumentation"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfLoanApprovals"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfLoandetails"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanApplication","fromLoanApprovals","statusOfMovementDoc"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedCollateral"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedDocement"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","authorisedLoanDetails"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","collateral"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","document"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","loandetails"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","loanInitialisation"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","displayWindow","rejectedComments"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","userIdDetails","currentHolder"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("loanAuthorisation","loanDetails","loanDetailsValues"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "readMeOnce"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "newLoanInstalment"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")); 
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"));
    fios.deleteFileExistance(fios.createFileName("loanApplication", "amortValues", "rejectedDovCol"+accountNumber+".txt"));


}

public synchronized List<List> getDetailsOfInterestToWaive(String accountNumber){
    
List<List> theDetailsOfInterest= new ArrayList();

    try {
        
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   


    String query = "SELECT instalment_no,InterestRemaing FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        List theDs=new ArrayList();
   theDs.add(rst3.getString("instalment_no"));
   theDs.add(rst3.getString("InterestRemaing"));
theDetailsOfInterest.add(theDs);
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

return theDetailsOfInterest;

}

public synchronized List<List> getDetailsOfAccumulatedInterestToWaive(String accountNumber){
List<List> theDetailsOfInterest= new ArrayList();

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   


    String query = "SELECT instalment_no,AccumulatedInterestRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        List theDs=new ArrayList();
   theDs.add(rst3.getString("instalment_no"));
   theDs.add(rst3.getString("AccumulatedInterestRemaining"));
theDetailsOfInterest.add(theDs);
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

return theDetailsOfInterest;
}



public synchronized List<List> getDetailsOfLoanPenaltyToWaive(String accountNumber){
List<List> theDetailsOfInterest= new ArrayList();

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   


    String query = "SELECT instalment_no,LoanPenaltyRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        List theDs=new ArrayList();
   theDs.add(rst3.getString("instalment_no"));
   theDs.add(rst3.getString("LoanPenaltyRemaining"));
theDetailsOfInterest.add(theDs);
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

return theDetailsOfInterest;
}



    public synchronized void updateLoanStore(List de){
//fios.stringFileWriter(fios.createFileName("test", "testit", "status.txt"), de.get(8).toString()+de.get(2).toString());
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE"+" "+"new_loan_appstore"+" "+"SET trn_date=?,loan_id=?,remaining_instalments=?,balance_due=?,instalment_next_due_date=?,trn_time=?,instalments_paid=? WHERE(applicant_account_number=? AND trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, de.get(1));
    pstool.setObject(2, de.get(2));
    pstool.setObject(3, de.get(3));
    pstool.setObject(4, de.get(4));
    pstool.setObject(5, de.get(5));
    pstool.setObject(6, de.get(6));
    pstool.setObject(7, de.get(7));
    pstool.setObject(8, de.get(0));
    pstool.setObject(9, de.get(8));
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE"+" "+"new_loan_appstore1"+" "+"SET trn_date=?,loan_id=?,remaining_instalments=?,balance_due=?,instalment_next_due_date=?,trn_time=?,instalments_paid=? WHERE(applicant_account_number=? AND trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, de.get(1));
    pstool.setObject(2, de.get(2));
    pstool.setObject(3, de.get(3));
    pstool.setObject(4, de.get(4));
    pstool.setObject(5, de.get(5));
    pstool.setObject(6, de.get(6));
    pstool.setObject(7, de.get(7));
    pstool.setObject(8, de.get(0));
    pstool.setObject(9, de.get(8));
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    cq.setAutoCommit(true); 
    cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE"+" "+"new_loan_appstoreamort"+" "+"SET master2_id=? WHERE instalment_no=?";
    
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, de.get(2));
    pstool.setObject(2, de.get(8));
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    }

    public synchronized void updateLoanStores(Map de){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE"+" "+"new_loan_appstore"+" "+"SET trn_date=?,remaining_instalments=?,balance_due=?,instalment_next_due_date=?,trn_time=?,instalments_paid=? WHERE(applicant_account_number=? AND trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, de.get(1));//transaction date
    pstool.setObject(2, de.get(2));//remaining instalments
    pstool.setObject(3, de.get(3));//remaining balance
    pstool.setObject(4, de.get(4));//instalment next due date
    pstool.setObject(5, de.get(5));//transaction time
    pstool.setObject(6, de.get(6).toString().replace(",", ""));//instalments paid
    pstool.setObject(7, de.get(0));//applicant account number
    pstool.setObject(8, de.get(7));//transaction id
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
      cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE"+" "+"new_loan_appstore1"+" "+"SET trn_date=?,remaining_instalments=?,balance_due=?,instalment_next_due_date=?,trn_time=?,instalments_paid=? WHERE(applicant_account_number=? AND trn_id=?)";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, de.get(1));//transaction date
    pstool.setObject(2, de.get(2));//remaining instalments
    pstool.setObject(3, de.get(3));//remaining balance
    pstool.setObject(4, de.get(4));//instalment next due date
    pstool.setObject(5, de.get(5));//transaction time
    pstool.setObject(6, de.get(6).toString().replace(",", ""));//instalments paid
    pstool.setObject(7, de.get(0));//applicant account number
    pstool.setObject(8, de.get(7));//transaction id
    pstool.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }



    }
    public String instalmentDueDate(String accountNumber,String trn){

    String status="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
//FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+"'"+"P"+"'";

    String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+trn;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
    status =fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_due_date"));  


    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
    return status;

    }

    public synchronized void  updateInterest(String accountNumber,String trn){

    Integer zw=45;
    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "interestPending"+accountNumber), zw.toString());
    fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+accountNumber));   
    String amountai= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+accountNumber));
//    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),accountNumber+";"+trn);
    double total=parseDouble(amountai)+parseDouble(interestInstalment(accountNumber,trn));
    fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalInterest"+accountNumber), total+"");



    }

    public synchronized void  updatePrincipal(String accountNumber){

    if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){

    if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){ 

    Integer zw=45;

    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "principalPending"+accountNumber), zw.toString());

    fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+accountNumber));   

    String amountai= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+accountNumber));

    double total=parseDouble(amountai)+parseDouble(princimpalInstalment(accountNumber));

    fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal"+accountNumber), total+"");

    }


    }

    }

     public synchronized void  updatePrincipal2(String accountNumber){

//    if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "allowUseOfGuarantors.txt"))==1){
//
//    if(fios.intFileReader(fios.createFileName("persistence", "gaurantors", "gaurantorsSharesTagged.txt"))==1){ 

    Integer zw=45;

    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "principalPending2"+accountNumber), zw.toString());

    fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal2"+accountNumber));   

    String amountai= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal2"+accountNumber));

    double total=parseDouble(amountai)+parseDouble(princimpalInstalment(accountNumber));

    fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalprincipal2"+accountNumber), total+"");

//    }
//
//
//    }

    }
    
    
    

    public synchronized void  recoverCharges(String accountNumber,String charges){

        if(parseDouble(charges)>0.0){
    Integer zw=450;
    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "partlyPaid", "chargesPending"+accountNumber), zw.toString());

    fios.forceFileExistanceZero(fios.createFileName("loanRepayment", "partlyPaid", "totalCharges"+accountNumber)); 

    String amountai= fios.stringFileReader(fios.createFileName("loanRepayment", "partlyPaid", "totalCharges"+accountNumber));

    double total=parseDouble(amountai)+parseDouble(charges);

    fios.stringFileWriter(fios.createFileName("loanRepayment", "partlyPaid", "totalCharges"+accountNumber), total+"");

        }

    }

    public synchronized void updateOutStandingAco(){

    fios.deleteFileExistance(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered"));

    String accountNumber="";

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    String query = "SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

    PreparedStatement ps = cq.prepareStatement(query);

    ResultSet rst3=ps.executeQuery();

    while(rst3.next()){

    accountNumber=rst3.getString("applicant_account_number");

    fios.stringFileWriterAppend(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered"), accountNumber);
    fios.stringFileWriterAppend(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered"), ";");

    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }



    }
    
    
    public synchronized void updateLoanReportATable(List aad){
     
           try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loanprocessingstore VALUES(?,?,?,?,?,?,?,?,?,?)");
    ps.setObject(1, null);//Trn Id:1
    ps.setObject(2, this.getDBCurrentDate());// Trn Date
    ps.setObject(3, aad.get(0));//Principal Amount:3 //Original principal Amount:0
    ps.setObject(4, aad.get(1));//Interest Rate:4//Original Interst Rate:1
    ps.setObject(5,fmt.forDatabaseWithFullYearBeginningWithDate(aad.get(2).toString()));//Intalment start Date:5//Original instalment start date:2
     ps.setObject(6,fmt.forDatabaseWithFullYearBeginningWithDate(aad.get(3).toString()));//Intalment end Date:6//Original instalment end date:3
      ps.setObject(7, aad.get(4));//Loan Tenure:7//Loan Tenure:4
    ps.setObject(8, aad.get(5));//Loan Cycle Status:8//Loan Cycle status:5
   ps.setObject(9, aad.get(6));//Account Name:9//Account Name:6
    ps.setObject(10, aad.get(7));//Account Number:10//Account Number: 7
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
     }
    
    }
    
 public synchronized List updateOutStandingAcof(){

   List accountsConsidered= new ArrayList();
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    String query = "SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

    PreparedStatement ps = cq.prepareStatement(query);

    ResultSet rst3=ps.executeQuery();

    while(rst3.next()){

accountsConsidered.add(rst3.getString("applicant_account_number"));

    

    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

return accountsConsidered;

    }
 
 public synchronized List updateOutStandingAcof2(){

   List accountsConsidered= new ArrayList();
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    String query = "SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

    PreparedStatement ps = cq.prepareStatement(query);

    ResultSet rst3=ps.executeQuery();

    while(rst3.next()){

accountsConsidered.add(rst3.getString("applicant_account_number"));

    

    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

return accountsConsidered;

    }
 
  public synchronized List updateOutStandingAcof3(){

   List accountsConsidered= new ArrayList();
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    String query = "SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

    PreparedStatement ps = cq.prepareStatement(query);

    ResultSet rst3=ps.executeQuery();

    while(rst3.next()){

accountsConsidered.add(rst3.getString("applicant_account_number"));

    

    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

return accountsConsidered;

    }
  
  public synchronized List updateloanWriteOff(){

   List accountsConsidered= new ArrayList();
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);  
    String query = "SELECT applicant_account_number FROM  new_loan_appstore WHERE loan_cycle_status ="+"'"+"Disbursed"+"'";

    PreparedStatement ps = cq.prepareStatement(query);

    ResultSet rst3=ps.executeQuery();

    while(rst3.next()){

accountsConsidered.add(rst3.getString("applicant_account_number"));

    

    }
    rst3.close();
    ps .close();
    cq.setAutoCommit(true);
    loancon.closeConnection(cq); 

    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

return accountsConsidered;

    }
    

    public synchronized void  prepareNonRecurrentArrears(String accountNumber){

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 



    String query = "SELECT instalment_no, instalment_amount,instalment_due_date, instalment_status FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND  (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){

    if(!instalmentExists(accountNumber,rst3.getString("instalment_no"))){
    String [] instalments=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")).split("[;]");   

    if(instalments[0].equalsIgnoreCase("10000")){
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    fios.forceFileExistanceAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    }
    fios.forceFileExistanceZero(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    Integer n=fios.intFileReader(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), ";");
    n=n+1;
    fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"),n.toString());   

    }

    if(rst3.getString("instalment_status").equalsIgnoreCase("pp")){

    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "newLoanInstalment"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    }   



    if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"testInstalmentP"+accountNumber+".txt"))!=62){

    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_due_date"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), ";");

    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }





    }  
    
    public synchronized void  prepareRecurrentArrears(String accountNumber){

    long instalmentStartDate=1L;

    long today=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(this.getDBCurrentDate()));      

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT instalment_no, instalment_amount,instalment_due_date, instalment_status FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND  (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){

    if(!instalmentExists(accountNumber,rst3.getString("instalment_no"))){

    String [] instalments=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")).split("[;]");   

    if(instalments[0].equalsIgnoreCase("10000")){
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    fios.forceFileExistanceAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    }
    fios.forceFileExistanceZero(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    Integer n=fios.intFileReader(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), rst3.getString("instalment_no"));


    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), ";");


    n=n+1;
    fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"),n.toString());   

    }

    if(rst3.getString("instalment_status").equalsIgnoreCase("pp")){

    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "newLoanInstalment"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    }   

    if(!fios.stringFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"dateRecycleDetails"+accountNumber+".txt")).equalsIgnoreCase("1")){    

    instalmentStartDate=fmt.convertTimeToMillseconds(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"dateRecycleDetails"+accountNumber+".txt")))+fmt.convertMillsAsNTs(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt")));

    }

    if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"testInstalmentP"+accountNumber+".txt"))!=62){

    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_due_date"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), ";");

    }
    else if(today>=instalmentStartDate){


    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_due_date"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), ";");


    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }}

 public synchronized void  prepareRecurrentCompoundArrears(String accountNumber){

    long instalmentStartDate=1L;

    long today=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(this.getDBCurrentDate()));      

    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT instalment_no, instalment_amount,instalment_due_date, instalment_status  FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){

    if(!instalmentExists(accountNumber,rst3.getString("instalment_no"))){

    String [] instalments=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")).split("[;]");   

    if(instalments[0].equalsIgnoreCase("10000")){
        
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    fios.forceFileExistanceAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    }
    fios.forceFileExistanceZero(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    Integer n=fios.intFileReader(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), rst3.getString("instalment_no"));


    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"), ";");


    n=n+1;
    fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears","repayNumber"+accountNumber+".txt"),n.toString());   

    }

    if(rst3.getString("instalment_status").equalsIgnoreCase("pp")){

    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "newLoanInstalment"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    }   

    if(!fios.stringFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"dateRecycleDetails"+accountNumber+".txt")).equalsIgnoreCase("1")){    

    instalmentStartDate=fmt.convertTimeToMillseconds(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"dateRecycleDetails"+accountNumber+".txt")))+fmt.convertMillsAsNTs(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt")));

    }

    if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"testInstalmentP"+accountNumber+".txt"))!=62){

    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_due_date"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), ";");

    }
    else if(today>=instalmentStartDate){


    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_amount"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), rst3.getString("instalment_due_date"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", rst3.getString("instalment_no")+"repayDetailArrears"+accountNumber+".txt"), ";");

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), rst3.getString("instalment_no"));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbers"+accountNumber+".txt"), ";");


    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }



    }
    

    public String remainingBalance(String accountNumber, String instamentNumber){
    String balance="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 

    String query = "SELECT beginning_bal FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+"'"+instamentNumber+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.next()){
    balance=rst3.getString("beginning_bal");
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    return balance;
    }


    


   public synchronized void actualLoanUpdate1(String accountNumber,String instalmentNumber,String theLoanPenalty){
//   fios.stringFileWriter(fios.createFileName("test", "testit", accountNumber+"trdyr.txt"),theLoanPenalty);
       int theInstalmentNumber=parseInt(instalmentNumber);
 
    double theActualLoanPenalty=parseDouble(theLoanPenalty);
    
double existingRemainingloanPenalty=this.currentLoanPenaltyNow(accountNumber,theInstalmentNumber);

 double theLoanPenaltyActual=this.theActualLoanPenaltyNow(accountNumber,theInstalmentNumber);

//double existingInstalmentAmount=theInstalmentNow(accountNumber,theInstalmentNumber);

double existingRemainingInstalment=theRemainingInstalmentNow(accountNumber,theInstalmentNumber);

double existintTotalLoanPenalty=this.totalCurrentRemainingLoanPenaltyNow(accountNumber);

double existintTotalLoanInstalmentRemaining=this.totalInstalmentRemainingNow(accountNumber);

//double existintTotalLoanInstalment=this.totalInstalmentNow(accountNumber);

//double existintTotalLoanAmount=this.totalLoanAmountNow(accountNumber);

existingRemainingloanPenalty+=theActualLoanPenalty;//Remaining instalment on the amortization schedule

theLoanPenaltyActual+=theActualLoanPenalty;//instalment paid on the amortization schedule

//existingInstalmentAmount+=theActualLoanPenalty;//instalment_amount


existingRemainingInstalment+=theActualLoanPenalty;// InstalmentRemaining



existintTotalLoanPenalty+=theActualLoanPenalty;//interest remaining on the amortization schedule

existintTotalLoanInstalmentRemaining+=theActualLoanPenalty;//interest remaining on the amortization schedule


//existintTotalLoanInstalment+=theActualLoanPenalty;//interest remaining on the amortization schedule

//existintTotalLoanAmount+=theActualLoanPenalty;//interest remaining on the amortization schedule

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET  LoanPenaltyRemaining=?, LoanPenalty=?, InstalmentRemaining=?, instalment_status=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingloanPenalty);
    
    pstool.setObject(2, theLoanPenaltyActual);

//    pstool.setObject(3, existingInstalmentAmount);
    
       pstool.setObject(3, existingRemainingInstalment);
       
          pstool.setObject(4, "NP");
       
        pstool.setObject(5, theInstalmentNumber);
        
        pstool.setObject(6, "newloan"+accountNumber);
        
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalLoanPenaltyRemaining=?,balance_due=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existintTotalLoanPenalty);
    
     pstool2.setObject(2, existintTotalLoanInstalmentRemaining);
     
     
    pstool2.setObject(3, "newloan"+accountNumber);

    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalLoanPenaltyRemaining=?,balance_due=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existintTotalLoanPenalty);
    
     pstool2.setObject(2, existintTotalLoanInstalmentRemaining);
     
     
    pstool2.setObject(3, "newloan"+accountNumber);

    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }



  
    }  
    
    
    
    
    public boolean instalmentExists(String accountNumber,String instament){

    boolean itsIn=false;int v=0;
        
    fios.forceFileExistanceHundred(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
    
    if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "readMeOnce"+accountNumber+".txt"))!=75){

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"),";");  

    Integer g=75;
    fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears", "readMeOnce"+accountNumber+".txt"), g.toString());
    }




    String [] instalments=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")).split("[;]");     

    while(v<=instalments.length-1){

    if(parseInt(instalments[v].replace(";", ""))==parseInt(instament.replace(";", ""))){

    itsIn=true;

    break;
    }

    v++;    
    }

    return itsIn;   
    }

//
//    public synchronized void knockOffTheInstalment(String accountNumber){
//    int k=0;
//    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "instalmentNumbersNN"+accountNumber+".txt"), fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt")));
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"));
//    String[] newInstalments= fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentNumbersNN"+accountNumber+".txt")).split("[;]");
//
//    while(k<=newInstalments.length-2){
//
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"),newInstalments[k+1].replace(";", " "));
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentNumbersN"+accountNumber+".txt"),";");
//    k++;
//    }
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentNumbersNN"+accountNumber+".txt"));
//
//    }
//
//    public synchronized void knockOffTheInstalmentAmount(String accountNumber){
//    int k=0;
//    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"), fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt")));
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"));
//    String[] newInstalments= fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt")).split("[;]");
//
//    while(k<=newInstalments.length-2){
//
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),newInstalments[k+1].replace(";", " "));
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),";");
//    k++;
//    }
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"));
//
//    }
//    public synchronized void  replaceInstalmentFirst(String accountNumber,String amountRemaining){
//
//    int k=0;
//    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"), fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt")));
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"));
//    String[] newInstalments= fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt")).split("[;]");
//
//    while(k<=newInstalments.length-1){
//
//    if(k==0){
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),amountRemaining);
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),";");
//    }else{
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),newInstalments[k].replace(";", " "));
//    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),";");
//    }
//    k++;
//
//    }
//
//
//    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"));
//
//    }



    public String remainingInstalments(String accountNumber){
    String instal="";


    int inst= fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "remainingInstalements"+accountNumber+".txt")); 

    inst=inst-1;

    fios.intFileWriterReplace(fios.createFileName("loanApplication", "amortValues", "remainingInstalements"+accountNumber+".txt"), inst+""); 
    instal=inst+"";
    return instal;

    }



    public String instalmentNextDueDate(String accountNumber,String trn){
    int newtrn=0;String newDate="";

    if(trn.equalsIgnoreCase(fios.stringFileReader(fios.createFileName("loanApplication", "amortValues", "loanStore"+accountNumber+".txt")).split("[,]", 15)[0])){

    newtrn=parseInt(trn);

    } else{

    newtrn=parseInt(trn)+1;

    }
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 

    String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+newtrn; // Using the account number, select from the amortization schedule for the account number any recently unpaid instalment ID

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.next()==rst3.last()){

    newDate =rst3.getString("instalment_due_date");


    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }


    return   newDate;





    }



    public String remainingInstalmentsPP(String accountNumber){

    return fios.intFileReader(fios.createFileName("loanApplication", "amortValues", "remainingInstalements"+accountNumber+".txt"))+""; 

    }


    public synchronized void  replaceInstalmentAny(String accountNumber,String amountRemaining){


    int k=0;
    fios.stringFileWriter(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"), fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt")));
    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"));
    String[] newInstalments= fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt")).split("[;]");

    while(k<=newInstalments.length-1){

    if(k==0){
    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),amountRemaining);
    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),";");
    }else{
    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),newInstalments[k].replace(";", " "));
    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"),";");
    }
    k++;

    }


    fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", "instalmentAmountNM"+accountNumber+".txt"));

    }


    public synchronized void addNewInstalment(String accountNumber,String amount){

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"), fmt.formatForStatementNumbers(amount).replace(",", ""));

    fios.stringFileWriterAppend(fios.createFileName("persistence", "loanArrears", "instalmentAmount"+accountNumber+".txt"), ";");   


    }
    public synchronized void  prepareForReducing(String accountNumber,String amount){

    fios.stringFileWriter(fios.createFileName("loanRepayment", "updateArrears", "amountToReduce"+accountNumber+".txt"), amount);
    Integer kl=253;
    fios.intFileWriterReplace(fios.createFileName("loanRepayment", "updateArrears", "toBeReduced"+accountNumber+".txt"), kl.toString());
    }


    public String currentInstalmentDue(String accountNumber){
    String instalmentAmount="";
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 


    String query = "SELECT InstalmentRemaining FROM"+" "+"newloan"+accountNumber+" "+"WHERE instalment_status='NP' OR instalment_status='PP' OR instalment_status='NY'"; // Using the account number, select from the amortization schedule for the account number any recently unpaid instalment ID

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if(rst3.first()){

    instalmentAmount =rst3.getString("InstalmentRemaining");


    }else{

    instalmentAmount="0";


    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }


    return   instalmentAmount;



    }

 private String accountName(String accountNumber){
        
String accountName="";

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  applicant_account_name FROM new_loan_appstore WHERE applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
   accountName=(rst3.getString("applicant_account_name"));
   
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return accountName;
    }

public boolean updateloanPortfolio(String accountNumber){
boolean updated=false;
List value=getprincimpalportfolio(accountNumber);
if(!value.isEmpty()){
     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loan_portfolio VALUES(?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, this.getDBCurrentDate());
    ps.setObject(3, value.get(0));
    ps.setObject(4, value.get(1));
    ps.setObject(5,accountName(accountNumber));
    ps.setObject(6, accountNumber);
   
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
     
     
     }
}


    
return updated;    
}

public boolean updateloanPortfolioperforming(String accountNumber){
boolean updated=false;
List value=getprincimpalportfolio(accountNumber);
if(!value.isEmpty()){
     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO performing_loan_portfolio VALUES(?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, this.getDBCurrentDate());
    ps.setObject(3, value.get(0));
    ps.setObject(4, value.get(1));
    ps.setObject(5,accountName(accountNumber));
    ps.setObject(6, accountNumber);
   
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
     
     
     }
}


    
return updated;    
}

public boolean updateloanPortfolioAtRisk (String accountNumber){
boolean updated=false;
List value=getprincimpalportfolio(accountNumber);
if(!value.isEmpty()){
     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loan_portfolio_atrisk VALUES(?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, this.getDBCurrentDate());
    ps.setObject(3, value.get(0));
    ps.setObject(4, value.get(1));
    ps.setObject(5,accountName(accountNumber));
    ps.setObject(6, accountNumber);
   
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
     
     
     }
}


    
return updated; 

}
 private List<String> getprincimpalportfolio(String accountNumber){
    
List theItems=new ArrayList();

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  princimpal_amount_run_bal,instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
    theItems.add(rst3.getString("princimpal_amount_run_bal"));
    theItems.add(rst3.getString("instalment_due_date")); 
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   
return theItems;
}

  public List updatePerformingLoanPoert(){
int z=0;List theperformingAccounts=new ArrayList();
   List accountsConsidered= this.updateOutStandingAcof2();
   
   while(z<=accountsConsidered.size()-1){
   if(accountIsPerforming(accountsConsidered.get(z).toString())){
   
   
   theperformingAccounts.add(accountsConsidered.get(z));
   }
   
   z++;
   }
  
return theperformingAccounts;

    }
 
  public List updateLoanPoertAtRisk(Component c){
  
  int z=0;List thepAtRiskAccounts=new ArrayList();
   List accountsConsidered= this.updateOutStandingAcof3();
   
   while(z<=accountsConsidered.size()-1){
   if(accountIsAtRisk(accountsConsidered.get(z).toString(),c)){
   
   
   thepAtRiskAccounts.add(accountsConsidered.get(z));
   }
   
   z++;
   }
  
return thepAtRiskAccounts;
  }

  
  
  
  public List updateLoanDueWriteOff(){
  
  int z=0;List thedueWriteOffAccounts=new ArrayList();
   List accountsConsidered= this.updateloanWriteOff();
   
   while(z<=accountsConsidered.size()-1){
   if(accountIsDueForWriteOff(accountsConsidered.get(z).toString())){
   
   
   thedueWriteOffAccounts.add(accountsConsidered.get(z));
   }
   
   z++;
   }
  
return thedueWriteOffAccounts;
  }
  
 public boolean accountIsPerforming(String accountNumber){
 boolean thatAccount=true; String sate="";
 
 
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
   sate=rst3.getString("instalment_due_date");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 
 long count=fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sate), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));
 if(count<-30){
 
 thatAccount=false;
 }
 
 return thatAccount;
 }
 
 public boolean accountIsAtRisk(String accountNumber,Component c){
     
 boolean thatAccount=true; String sate="";
 
//int k= fios.intFileReader(fios.createFileName("emailDetails", "boxNumber", "CounterLoanPayment2.txt"));
// k=k+1;
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
        if(instalmentDateIsThere(accountNumber)){
   sate=rst3.getString("instalment_due_date");
        }
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
// JOptionPane.showMessageDialog(c, "newloan"+k);
//  JOptionPane.showMessageDialog(c, "newloan"+accountNumber);
// JOptionPane.showMessageDialog(c, sate);
// 
// JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sate));
//
// JOptionPane.showMessageDialog(c, getDBCurrentDate());
//  JOptionPane.showMessageDialog(c, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));

// long count=fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sate), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));
 
// if(count>=-1){
// 
// thatAccount=false;
// }
// fios.intFileWriterReplace(fios.createFileName("emailDetails", "boxNumber", "CounterLoanPayment2.txt"), k+"");
 return thatAccount;
 }

private boolean instalmentDateIsThere(String accountNumber){
boolean theInstalmenDate=false; int sate=0;

//  if(instalmentDateIsThere(accountNumber)){
      
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  COUNT(instalment_due_date) AS instalmnets FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND  NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
      
   sate=rst3.getInt("instalmnets");
        
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 if(sate>0){
 
 theInstalmenDate=true;
 }
// }
 return theInstalmenDate;
}

public boolean accountIsDueForWriteOff(String accountNumber){
 boolean thatAccount=true; String sate="";
 
 
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
   sate=rst3.getString("instalment_due_date");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 
 long count=fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sate), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));
 
 if(count>=parseInt(fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "writeOff.txt")).split("[,]", 2)[0])){
 
 thatAccount=false;
 
 }
 
 return thatAccount;
 }

public boolean closeTopUpLoan(String accountNumber){
    
List trnIds=new ArrayList();boolean successfullyClosed=false; 


if(loanExists(accountNumber)){
try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);  

    String query = "SELECT instalment_no FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT" +"(instalment_status="+"'P')"; // Using the account number, select from the amortization schedule for the account number any recently unpaid instalment ID

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

   while(rst3.next()){
       
   trnIds.add(rst3.getString("instalment_no"));

   //Send the accountNumber, Picked instalment and it's id for further processing.

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

 successfullyClosed=updateLoanLoanTopUpsActually(accountNumber,  trnIds);
}
return successfullyClosed;
}





public boolean updateLoanLoanTopUpsActually(String accountNumber,List ids){

boolean successfullyClosed=false; int z=0;

while(z<ids.size()){
  
 try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET instalment_paid=?, instalment_status=?,instalment_paid_date=?, instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, fmt.formatForStatementNumbers(this.princimpalInstalmentExact(accountNumber,ids.get(z).toString())).replace(",", ""));
    
    pstool.setObject(2, "TP");
    
    pstool.setObject(3, fmt.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("loanRepayment", "valueDate", "paymentDate.txt"))));
    
    pstool.setObject(4, fmt.diffDays(instalmentDueDate(accountNumber,ids.get(z).toString()), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
    pstool.setObject(5, ids.get(z).toString());
    
    pstool.setObject(6, "newloan"+accountNumber);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

 if(checkIfCompleted(accountNumber)==true){//if all instalments are completed, change the loan name to closed
   
         String loandetails =fios.stringFileReader(fios.createFileName("loanApplication", "amortValues","loanStore"+accountNumber+".txt"));       
        List loanDetails= new ArrayList();
       loanDetails.add(loandetails.split("[,]", 15)[1]);//Princimpal amount
         loanDetails.add(loandetails.split("[,]", 15)[2]);//interest amount
  loanDetails.add(loandetails.split("[,]", 15)[4]);//instalment start date
  loanDetails.add(loandetails.split("[,]", 15)[5]);//instalment start date
    loanDetails.add(loandetails.split("[,]", 15)[0]);//instalment start date
   loanDetails.add("Completed");//loan cycle status
    loanDetails.add(dbq.AccountName(accountNumber));//loan cycle status
    loanDetails.add(accountNumber);//loan cycle status
  updateLoanReportATable(loanDetails);  
        
        
        List <String>details=new ArrayList();
    String newLoanID=closeLoanAccount(accountNumber);
    String trnId=trnId(accountNumber);

    clearData(accountNumber);
    updateLoanStoreAll(fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt")), accountNumber, "Completed",newLoanID,trnId);

    details.add(accountNumber);
    details.add(fmt.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("loanRepayment", "valueDate", "paymentDate.txt"))));
    details.add(newLoanID);
    details.add("0");
    details.add("0");
    details.add(fmt.forDatabaseWithFullYearBeginningWithDate(fios.stringFileReader(fios.createFileName("loanRepayment", "valueDate", "paymentDate.txt"))));
    details.add(this.getDBCurrentTime());
    details.add("0");
    details.add(trnId);

    updateLoanStore(details);
successfullyClosed=true;
    }   

z++;
}



return successfullyClosed;

}


public boolean assignPortfolioOwner(String accountNumber,String userId){
    
    boolean successStory=false; int excuted=2;
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
 
    String UpdateQuary = "UPDATE new_loan_appstore SET gruop_id=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, userId);
    pstool.setObject(2, "newloan"+accountNumber);
   excuted= pstool.executeUpdate();
    }  

    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
 
    String UpdateQuary1 = "UPDATE new_loan_appstore1 SET gruop_id=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, userId);
    pstool.setObject(2, "newloan"+accountNumber);
   excuted= pstool.executeUpdate();
    }  

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    if(excuted==1){
    
    successStory=true;
    }
  fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "portfolioOwnerAccount.txt"), "NOT YET");
    return successStory;
   
}

                                 
        public synchronized void fillWithGroupNames(JComboBox userBox,Component c){
            String groupId=groupNumber(c);
        String accountName="";
        
        ArrayList <String> useNames=new ArrayList();

        useNames.add("Select Group Name");
        
        if(groupNameThere()){
            
        try {
            
        Connection cq=loancon.createConnection(); 
        
        cq.setAutoCommit(false);

        String query = "SELECT DISTINCT LoanGroupName FROM new_loan_appstore1";
        
        PreparedStatement ps = cq.prepareStatement(query);
        
        ResultSet rst3 = ps.executeQuery();

        while(rst3.next()){

        accountName=rst3.getString("LoanGroupName");
        
        useNames.add(accountName);

        }
     useNames.add(groupId);
     
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
        } catch (SQLException ex) {
        Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{

          useNames.add(groupId);

        }

        modelcombo = new MyComboBoxModel(useNames);
        
        userBox.setModel(modelcombo); 

        userBox.setSelectedIndex(0);

        }                     
           
        public synchronized String groupNameIdividual(String accountNumber,String loanCycle){
            
        String groupName="";
        
 
        
        if(groupNameIdividualExists(accountNumber)){
            
        try {
            
        Connection cq=loancon.createConnection(); 
        
        cq.setAutoCommit(false);

        String query = "SELECT  LoanGroupName FROM new_loan_appstore1 where applicant_account_number="+accountNumber+" AND LoanCycle="+"'"+loanCycle+"'";
        
        PreparedStatement ps = cq.prepareStatement(query);
        
        ResultSet rst3 = ps.executeQuery();

        while(rst3.next()){

        groupName=rst3.getString("LoanGroupName");
        
       

        }

     
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
        } catch (SQLException ex) {
        Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{

       groupName="Missing";

        }

      
        
       return groupName;

        }    
        
        
        
        public List groupNameIdividualList(String accountNumber,Component c){
            
        List groupName=new ArrayList();
        
// JOptionPane.showMessageDialog(c, accountNumber);
            
        try {
            
        Connection cq=loancon.createConnection(); 
        
        cq.setAutoCommit(false);

        String query = "SELECT DISTINCT LoanGroupId FROM new_loan_appstore1 where applicant_account_number="+"'"+accountNumber+"'";
        
        PreparedStatement ps = cq.prepareStatement(query);
        
        ResultSet rst3 = ps.executeQuery();

        while(rst3.next()){

        groupName.add(rst3.getString("LoanGroupId"));
        
       

        }

     
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
        } catch (SQLException ex) {
        Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
     

      
        
       return groupName;

        }  
        
        
        
        public synchronized void fillWithCycleStatus(JComboBox userBox,String accountNumber){
        
        String accountName="";

        ArrayList <String> useNames=new ArrayList();
 
        useNames.add("Select Loan Cycle");
        
          if(fillWithCycleStatusThere(accountNumber)){ 
               useNames.add("Cycle1");
           
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanCycle FROM new_loan_appstore1 WHERE NOT LoanCycle='Cycle1' AND applicant_account_number="+accountNumber;
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("LoanCycle");
           
            useNames.add(accountName);
            
             }
      
           
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Cycle1");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0); 
        
        
        }
     
        public boolean fillWithCycleStatusThere(String accountNumber){
        int cycles=0;boolean cyclesThere=false;
                 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  COUNT(LoanCycle) AS cycles FROM new_loan_appstore1 WHERE NOT LoanCycle='Cycle1'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           cycles=rst3.getInt("cycles");
       
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   
   if(cycles>0){
   cyclesThere=true;
   }
        return cyclesThere;
        }
        
        public synchronized boolean updateOnlyGroups(List groupDetails,Component c){
        
             boolean successStory=false; int excuted=2;
             
//             JOptionPane.showMessageDialog(c, groupDetails.get(0)+";"+groupDetails.get(1)+";"+groupDetails.get(2)+";"+groupDetails.get(3));
   
             
              Connection cq=loancon.createConnection(); 
        try {
            
           cq.setAutoCommit(false);
           
            String UpdateQuary = "UPDATE new_loan_appstore1 SET LoanGroupId=?, LoanGroupName=? WHERE (applicant_account_number=? AND LoanCycle=?)";
           
            PreparedStatement pstool=cq.prepareStatement(UpdateQuary);
            
        pstool.setObject(1,   groupDetails.get(0).toString());
    
     pstool.setObject(2,   groupDetails.get(1).toString());
     
    pstool.setObject(3, groupDetails.get(2).toString());
    
     pstool.setObject(4, groupDetails.get(3).toString());
    
   successStory= pstool.execute();
     
   successStory=!successStory;
           cq.setAutoCommit(true);
           loancon.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }      
            
 
// 
        return successStory;
        
        }
        
        
        
        
       public synchronized boolean groupNameIdividualExists(String accountNumber){
            
        int groupNamec=0;boolean there=false;
  
        try {
            
        Connection cq=loancon.createConnection(); 
        
        cq.setAutoCommit(false);

        String query = "SELECT  COUNT(LoanGroupName) AS loangroups FROM new_loan_appstore1 where applicant_account_number="+accountNumber;
        
        PreparedStatement ps = cq.prepareStatement(query);
        
        ResultSet rst3 = ps.executeQuery();

        while(rst3.next()){

        groupNamec=rst3.getInt("loangroups");
        
       

        }

     
        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
        } catch (SQLException ex) {
        Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
      if(groupNamec>0){
      there=true;
      }
      
        
       return there;

        }    
        
        
         public synchronized void   fillOtherDetailsComboOptionsNumberOfCollaterals(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
 
        useNames.add("Select NumberOfCollaterals");
        
          if(NumberOfLoansThere()){ 
               useNames.add("1");
             useNames.add("2");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  NumberOfSecurity FROM new_loan_appstore1 WHERE NOT (NumberOfSecurity='1' OR  NumberOfSecurity='2')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("NumberOfSecurity");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("1");
             useNames.add("2");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       }  
                
                
                
      public synchronized void   fillOtherDetailsComboOptionsLoanProduct(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
 
        useNames.add("Select Loan Product");
        
          if(LoanProductThere()){ 
               useNames.add("Salary Loan");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanProduct FROM new_loan_appstore1 WHERE NOT LoanProduct='Salary Loan'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("LoanProduct");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Salary Loan");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       }        
       
     
      public synchronized void   fillOtherDetailsComboOptionsLoanPurpose(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
       
          if(LoanPurposeThere()){ 
               useNames.add("Select Loan Purpose");
        useNames.add("Personal Effects");
         useNames.add("Business Financing");
         useNames.add("Asset Financing");
         useNames.add("Home Improvement");
          useNames.add("School Fees");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanPurpose FROM new_loan_appstore1 WHERE NOT (LoanPurpose='Personal Effects' OR LoanPurpose='Business Financing' OR  LoanPurpose='Asset Financing' OR  LoanPurpose='Home Improvement' OR  LoanPurpose='School Fees')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("LoanPurpose");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
           useNames.add("Select Loan Purpose");
        useNames.add("Personal Effects");
         useNames.add("Business Financing");
         useNames.add("Asset Financing");
         useNames.add("Home Improvement");
          useNames.add("School Fees");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
       public synchronized void   fillOtherDetailsComboOptionsMarketingSource(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        useNames.add("Select MarketingChannel");
       
          if(marketingSourceThere()){ 
               useNames.add("Friends");
             useNames.add("Internet");
               useNames.add("Radio");
                useNames.add("TV");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  MarketingChannel FROM new_loan_appstore1 WHERE NOT (MarketingChannel='Friends' OR MarketingChannel='Internet' OR  MarketingChannel='Radio' OR  MarketingChannel='TV')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("MarketingChannel");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Friends");
             useNames.add("Internet");
               useNames.add("Radio");
                useNames.add("TV");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
       public synchronized void   fillOtherDetailsComboOptionsEconomicWelbeingLevel(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        useNames.add("Select EconomicWelbeingLevel");
        
          if(economicWelbeingLevel()){ 
              
              useNames.add("Level 1");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  EconomicWelbeingLevel FROM new_loan_appstore1 WHERE NOT (EconomicWelbeingLevel='Level 1')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("EconomicWelbeingLevel");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Level 1");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
      
      public synchronized void   fillOtherDetailsComboOptionsAmountTakenCategory(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        useNames.add("Select amountTakenCat");
          if(amountTakenCat()){ 
              
               useNames.add("100,000-200,000");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  AmountTakenCategory FROM new_loan_appstore1 WHERE NOT AmountTakenCategory='100,000-200,000'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("AmountTakenCategory");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("100,000-200,000");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
      
      public synchronized void   fillOtherDetailsComboOptionsIncomeLevel(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        useNames.add("Select Income Level");
          if(incomeLevelCat()){ 
              useNames.add("Very Low");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  IncomeLevel FROM new_loan_appstore1 WHERE NOT IncomeLevel='Very Low'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("IncomeLevel");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Very Low");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
      
      public synchronized void   fillOtherDetailsComboOptionsCycleOccupation(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        
        useNames.add("Select Occupation");
        
          if(occupationCat()){ 
               useNames.add("Agriculture");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  Occupation FROM new_loan_appstore1 WHERE NOT Occupation='Agriculture'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("Occupation");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Agriculture");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
      
      
      
       public synchronized void   fillOtherDetailsComboOptionsRepaymentSource(JComboBox userBox){
       
          String accountName="";

        ArrayList <String> useNames=new ArrayList();
        useNames.add("Select RepaymentSource");
          if(repaymentSource()){ 
              useNames.add("Monthly Income");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  PaymentSource FROM new_loan_appstore1 WHERE NOT PaymentSource='Monthly Income'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("PaymentSource");
           
            useNames.add(accountName);
            
             }
      
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add("Monthly Income");
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);  
       } 
      
      public int cycleNumber(String accountNu){
      
           String accountName="";int outCome=1;

          if(LoanCycleThere(accountNu)){ 
              
//              JOptionPane.showMessageDialog(c, accountNu+"in");
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  loan_id FROM new_loan_appstore WHERE (loan_cycle_status='Completed' && applicant_account_number="+"'"+accountNu+"')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            if(rst3.last()){
               
           accountName=rst3.getString("loan_id");
           
//            useNames.add(accountName);
            
             }
outCome=parseInt(accountName.substring(10, 11))+1;
//     JOptionPane.showMessageDialog(c, accountNu+"in"+outCome);       
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
       outCome=1;
          }
 return outCome;
      
      }
      
      public boolean LoanProductThere(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(LoanProduct) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }
      
      
      public boolean LoanPurposeThere(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(LoanPurpose) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }
      
      public boolean marketingSourceThere(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(MarketingChannel) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }
       public boolean economicWelbeingLevel(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(EconomicWelbeingLevel) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }
       
      public boolean repaymentSource(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(PaymentSource) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }   
        public boolean amountTakenCat(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(AmountTakenCategory) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }   
        
 public boolean incomeLevelCat(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(IncomeLevel) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }          
        
       public boolean occupationCat(){
      
          boolean tested=false;int theIt=0; 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(Occupation) AS counted FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
           while(rst3.next()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      }          
         
      public boolean LoanCycleThere(String accountNu){
      
          boolean tested=false;int theIt=0;

       
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(loan_id) AS counted FROM new_loan_appstore WHERE (loan_cycle_status='Completed' && applicant_account_number="+"'"+accountNu+"')";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            if(rst3.last()){
               
           theIt=rst3.getInt("counted");
           
//            useNames.add(accountName);
            
             }
//outCome=accountName.substring(6, 7);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   if(theIt>0){
   tested=true;
   }
         
 return tested;
      
      } 
   public synchronized void fillWithGroupIds(JComboBox userBox,Component c){
String gId=groupNumber(c);
        String accountName="";

        ArrayList <String> useNames=new ArrayList();
 
        useNames.add("Select Group Id");
          if(groupIdsThere()){ 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanGroupId FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("LoanGroupId");
           
            useNames.add(accountName);
            
             }
           useNames.add(gId);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
            useNames.add(gId);
          }
            modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo); 
   userBox.setSelectedIndex(0);
 
 
 }   
    public synchronized void fillWithGroupIdsNames(JTable table){

        ListTableModel model=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     List column1x= null;
     
          if(groupIdsThere()){ 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanGroupId,LoanGroupName FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               data4x= new ArrayList<>();
           data4x.add(rst3.getString("LoanGroupId"));
           
          data4x.add(rst3.getString("LoanGroupName"));
           data5x.add(data4x);
             }
            
            column1x= new ArrayList<>();
            column1x.add("LoanGroupId");
                    column1x.add("LoanGroupName");
               model= new ListTableModel( data5x, column1x);
           table.setModel(model);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          
 }   
   
    }
    
    
   public synchronized void fillWithGroupIdsNames1(JTable table){

        ListTableModel model=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     List column1x= null;
     int t=1;
          if(groupIdsThere()){ 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  LoanGroupId,LoanGroupName FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               data4x= new ArrayList<>();
               data4x.add(t);
           data4x.add(rst3.getString("LoanGroupId"));
           
          data4x.add(rst3.getString("LoanGroupName"));
           data5x.add(data4x);
           t++;
             }
            
            column1x= new ArrayList<>();
              column1x.add("S/n");
            column1x.add("LoanGroupId");
                    column1x.add("LoanGroupName");
               model= new ListTableModel( data5x, column1x);
           table.setModel(model);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          
 }   
   
    }
   
   
   public synchronized void fillWithBorrowerDetails1(JTable table){

        ListTableModel model=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     List column1x= null;
     int t=1;
          if(groupIdsThere()){ 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT DISTINCT  applicant_account_name,applicant_account_number FROM new_loan_appstore1";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               data4x= new ArrayList<>();
               data4x.add(t);
           data4x.add(rst3.getString("applicant_account_name"));
           
          data4x.add(rst3.getString("applicant_account_number"));
           data5x.add(data4x);
           t++;
             }
            
            column1x= new ArrayList<>();
              column1x.add("S/n");
            column1x.add("AccountName");
                    column1x.add("AccountNumber");
               model= new ListTableModel( data5x, column1x);
           table.setModel(model);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(model);
      table.setRowSorter(sorter);
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          
 }   
   
    }
   
   
//   LoanCyclesThere
   public synchronized String getTheGroupIdsIndividual(String accountNumber,String loanCycle){

        String groupIdsIndividual="";

          if(getTheGroupIdsIndividualExists(accountNumber)){ 
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT   LoanGroupId FROM new_loan_appstore1 where applicant_account_number="+accountNumber +" AND LoanCycle="+"'"+loanCycle+"'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           groupIdsIndividual=rst3.getString("LoanGroupId");

            
             }
   
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
          }else{
          
         groupIdsIndividual="";
          }
          
          return groupIdsIndividual;
 
 
 } 
   
  public synchronized boolean getTheGroupIdsIndividualExists(String accountNumber){

        int groupIdsIndividual=0;boolean thereId=false;

 
 
      
        
   
   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT   COUNT(LoanGroupId) AS ids FROM new_loan_appstore1 where applicant_account_number="+accountNumber;
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           groupIdsIndividual=rst3.getInt("ids");
           
          
            
             }
         
            
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
   
   if(groupIdsIndividual>0){
   thereId=true;
   
   }
          return thereId;
 
 }  
   
   
   
   public boolean groupNameThere(){

        boolean theIds=false; int conf=0;

   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(LoanGroupName) AS GNames FROM new_loan_appstore1";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           conf=rst3.getInt("GNames");
   
            
             }
          
       if(conf>0){
       theIds=true;
       } 
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
 return theIds;
 
 
 }        
   
    public boolean NumberOfLoansThere(){

        boolean theIds=false; int conf=0;

   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(NumberOfSecurity) AS GNames FROM new_loan_appstore1";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           conf=rst3.getInt("GNames");
   
            
             }
          
       if(conf>0){
       theIds=true;
       } 
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
 return theIds;
 
 
 }      
   
   public boolean LoanCyclesThere(){

        boolean theIds=false; int conf=0;

   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(LoanCycle) AS GNames FROM new_loan_appstore1";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           conf=rst3.getInt("GNames");
   
            
             }
          
       if(conf>0){
       theIds=true;
       } 
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
 return theIds;
 
 
 }      
   
  public boolean groupIdsThere(){

        boolean theIds=false; int conf=0;

   try {
         Connection cq=loancon.createConnection(); 
         
          cq.setAutoCommit(false);
             
             String query = "SELECT  COUNT(LoanGroupId) AS theIds FROM new_loan_appstore1";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           conf=rst3.getInt("theIds");
   
            
             }
          
       if(conf>0){
       theIds=true;
       } 
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
 return theIds;
 
 
 }                                
   
  
  
  public String groupNumber(Component c){
        
        String Thebatch="100",batch="";
        
        int theNumber=0;

try {
          
           Connection cq=quaryObj.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL groupNumber()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){
       
            
  theNumber=rsxmt.getInt("@theGroupNumber");

         }
   
 
        cq.setAutoCommit(true);
        
         quaryObj.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }
        batch="G00"+(theNumber+"");
        
//         JOptionPane.showMessageDialog(c, batch);
        return batch; 
        } 
                                              
                                   
public synchronized void fillWithUserNames2(JComboBox userBox){
 String accountName="";ArrayList <String> useNames=new ArrayList();
 
useNames.add("Select Group Id");
   try {
         Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
             
             String query = "SELECT  LoanGroupId,LoanGroupName FROM new_loan_appstore1";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){

            accountName=rst3.getString("LoanGroupId")+" "+rst3.getString("LoanGroupName");
            
            useNames.add(accountName);
            
             }
          
             modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo);  
             cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
  userBox.setSelectedIndex(0);
 
 
 }

public boolean assignGroupId(String accountNumber,String groupId){
  
    boolean successStory=false; int excuted=2;
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
 
    String UpdateQuary = "UPDATE new_loan_appstore SET GroupId=?, GroupName=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1,   groupId.split("\\s+")[1].trim());
     pstool.setObject(2,   groupId.split("\\s+")[0].trim());
    pstool.setObject(3, "newloan"+accountNumber);
   successStory= pstool.execute();
    }  

    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
 
    String UpdateQuary1 = "UPDATE new_loan_appstore1 SET LoanGroupId=?, LoanGroupName=? WHERE loan_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1,   groupId.split("\\s+")[1].trim());
     pstool.setObject(2,   groupId.split("\\s+")[0].trim());
    pstool.setObject(3, "newloan"+accountNumber);
   excuted= pstool.executeUpdate();
    }  

    cq.setAutoCommit(true);
    
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    if(excuted==1){
    
    successStory=true;
    }
  fios.stringFileWriter(fios.createFileName("PMMS_Statements", "reports", "GrouPId.txt"), "NOT YET");
    return successStory;
   
}

public List<List>getTheNumberOfDetailsForSmsReminders(String theDate){
    
   List<List> smsDetails=new ArrayList();
  
try {
             Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT applicant_account_number,instalment_amount FROM  new_loan_appstore WHERE instalment_next_due_date="+"'"+theDate+"'";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
         List theItem=new ArrayList();
         
            theItem.add(rs.getString("applicant_account_number"));
                     theItem.add(rs.getString("instalment_amount"));  
               smsDetails.add(theItem);
                    }
             
                     cq.setAutoCommit(true);
             
           loancon.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

      
      
      
    return smsDetails;  
   
   
   
   }


public List<List>getTheNumberOfDetailsForSmsRemindersaArrears(){
    updateOutStandingAco();
   List<List> smsDetails=new ArrayList();
   if(!fios.stringFileReader(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered")).equalsIgnoreCase("1")){
 String [] accounts=fios.stringFileReader(fios.createFileName("loanRepayment", "updateArrears", "accountsConsidered")).split("[;]");
    int k=0;
    while(k<=accounts.length-1){
   if(accountInArrears(accounts[k])){
   List theDetails=new ArrayList();
   theDetails.add(accounts[k]);
   theDetails.add(getArrearsAmount(accounts[k]));
   smsDetails.add(theDetails);
   }
   
       
    k++;
    }
   } 
      
      
    return smsDetails;  
   
   
   
   }
private boolean accountInArrears(String accountNumber){
boolean isInArrears=false;
if(getArrearsAmount(accountNumber)>0){
isInArrears=true;
}
return isInArrears;
}

public Double getArrearsAmount(String accountNumber) {
     Double totalAmountArrears=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(instalment_amount)AS totalInstalmentsArrears  FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    totalAmountArrears =rst3.getDouble("totalInstalmentsArrears");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

   
    
    return totalAmountArrears;    
        
        
        
    }

public int getArrearsAmountTest(String accountNumber) {
     int totalAmountArrears=0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT COUNT(instalment_amount)AS totalInstalmentsArrears  FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    totalAmountArrears =rst3.getInt("totalInstalmentsArrears");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

   
    
    return totalAmountArrears;    
        
        
        
    }
  public synchronized void processSecurity(String accountNumber,String security,Component c){
//     JOptionPane.showMessageDialog(c, dbq.AccountName(accountNumber)); 
      
//JOptionPane.showMessageDialog(c, security);

   try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
  String UpdateQuary3 = "UPDATE new_loan_appstore SET SecurityLoan=? WHERE loan_id=?";
    PreparedStatement pstool3d = cq.prepareStatement(UpdateQuary3);
//    JOptionPane.showMessageDialog(c, security);
   pstool3d.setObject(1, security);
    pstool3d.setObject(2, "newloan"+accountNumber);
    
       
   pstool3d.execute();
   
//JOptionPane.showMessageDialog(c,  d);

    cq.setAutoCommit(true); 
  cq.setAutoCommit(false); 
  String UpdateQuary31 = "UPDATE new_loan_appstore1 SET SecurityLoan=? WHERE loan_id=?";
    PreparedStatement pstool1d = cq.prepareStatement(UpdateQuary31);
//       JOptionPane.showMessageDialog(c, security);
   pstool1d.setObject(1, security);
    pstool1d.setObject(2, "newloan"+accountNumber);
    
       
   pstool1d.execute();
   
//JOptionPane.showMessageDialog(c,  d);

    cq.setAutoCommit(true);     
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
   
   
   
   }

public ListTableModel loanAuthorisationDetails(JTable table1,List colDetails){

 ListTableModel model=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     List column1x= null;
      column1x= colDetails;
     try {
 Connection cq=loancon.createConnection();
   cq.setAutoCommit(false);
            String query = "SELECT trn_id, loan_id,applicant_account_name,princimpal_amount, total_interest,inputter_id FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Submitted"+"'";
            PreparedStatement ps =  cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
           while(rst3.next()){
         data4x=new ArrayList();
         data4x.add(rst3.getString("trn_id"));
         data4x.add(rst3.getString("loan_id"));
         data4x.add(rst3.getString("applicant_account_name"));  
         data4x.add(rst3.getString("princimpal_amount"));  
         data4x.add(rst3.getString("total_interest")); 
         data4x.add(rst3.getString("inputter_id"));  
      
         data5x.add(data4x);
            }
       
            model= new ListTableModel( data5x, column1x);
           table1.setModel(model);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(model);
      table1.setRowSorter(sorter);
       
         cq.setAutoCommit(true);
           loancon.closeConnection( cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

return model;


}
public ListTableModel loanAuthorisationDetails2(JTable table1,List colDetails){

 ListTableModel modelx=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     List column1x= null;
      column1x= colDetails;
     try {
 Connection cq=loancon.createConnection();
   cq.setAutoCommit(false);
            String query = "SELECT trn_id, loan_id,applicant_account_name,princimpal_amount, total_interest,inputter_id FROM new_loan_appstore WHERE loan_cycle_status="+"'"+"Authorised"+"'";
            PreparedStatement ps =  cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
           while(rst3.next()){
         data4x=new ArrayList();
         data4x.add(rst3.getString("trn_id"));
         data4x.add(rst3.getString("loan_id"));
         data4x.add(rst3.getString("applicant_account_name"));  
         data4x.add(rst3.getString("princimpal_amount"));  
         data4x.add(rst3.getString("total_interest")); 
         data4x.add(rst3.getString("inputter_id"));  
      
         data5x.add(data4x);
            }
       
            modelx= new ListTableModel( data5x, column1x);
           table1.setModel(modelx);
           
       TableRowSorter<ListTableModel> sorter = new TableRowSorter<>(modelx);
      table1.setRowSorter(sorter);
       
         cq.setAutoCommit(true);
           loancon.closeConnection( cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

return modelx;


}


        public String getActiveId(String accountNumber,Component c){
        String loanDetails="";
//JOptionPane.showMessageDialog(c, accountNumber+";"+this.accountName(accountNumber));

        try {
        Connection cq=loancon.createConnection(); 
        cq.setAutoCommit(false);

        String query = "SELECT trn_id from new_loan_appstore WHERE loan_id="+"'"+"newloan"+accountNumber+"'";

        PreparedStatement ps = cq.prepareStatement(query);

        ResultSet rst3 = ps.executeQuery();

        //0 No of loan instalments
        // 1 Loan Tenure
        //2 Interest Rate
        //3 Total Interest amount
        // 4 princimpal amount
        // 5 total loan amount
        //6 intalment start date
        //7 intalment end date
        //8 The instalment amount

        if(rst3.last()){

        loanDetails=         rst3.getString("trn_id");


        }

        cq.setAutoCommit(true);
        loancon.closeConnection(cq);
        } catch (SQLException ex) {
        Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

        return   loanDetails;
        }

        
        
public List getLoanDetails(String trnLoanId,Component c){
    
    
//    JOptionPane.showMessageDialog(c, trnLoanId);

 List loanDetails=new ArrayList();


   try {
       Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
             
             String query = "SELECT total_instalments,loan_tenure,interest_rate,total_interest, princimpal_amount, total_loanAmount, instalment_start_date,instalment_end_date,  instalment_amount from new_loan_appstore WHERE trn_id="+"'"+trnLoanId+"'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
          
             //0 No of loan instalments
             // 1 Loan Tenure
             //2 Interest Rate
             //3 Total Interest amount
             // 4 princimpal amount
             // 5 total loan amount
             //6 intalment start date
             //7 intalment end date
             //8 The instalment amount
             
           while(rst3.next()){
        
              loanDetails.add(rst3.getString("total_instalments"));//0 No of loan instalments
              
              loanDetails.add(rst3.getString("loan_tenure"));// 1 Loan Tenure
              
              loanDetails.add(rst3.getString("interest_rate")+""+"%");//2 Interest Rate
              
              loanDetails.add(rst3.getString("total_interest"));//3 Total Interest amount
         
              
              loanDetails.add(rst3.getString("princimpal_amount"));// 4 princimpal amount
              
              loanDetails.add(rst3.getString("total_loanAmount"));// 5 total loan amount
              
              loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_start_date")));//6 intalment start date
              
              loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_end_date")));//7 intalment end date
              
              loanDetails.add(rst3.getString("instalment_amount"));//8 The instalment amount
        
             }
            
           cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
 if(loanDetails.isEmpty()){
 
 loanDetails.add("Empty");
 }
  return   loanDetails;
 
 }

public void loanSecurity(JTable table1,String loanId,Component c){
String loanIDsE="";
 ListTableModel modelxN=null;
    List<List> data5x= new ArrayList<>();
     List data4x=null;
     
     List column1x= new ArrayList();
      column1x.add("S/N");
      column1x.add("SECURITY TYPE");
      column1x.add("SECURITY DESCRIPTION");
     try {
 Connection cq=loancon.createConnection();
   cq.setAutoCommit(false);
            String query = "SELECT  SecurityLoan FROM new_loan_appstore WHERE trn_id="+"'"+loanId+"'";
            PreparedStatement ps1 =  cq.prepareStatement(query);
            ResultSet rst31 = ps1.executeQuery();
           if(rst31.next()){
  
        loanIDsE=rst31.getString("SecurityLoan");
  
  
            }
//           JOptionPane.showMessageDialog(c, loanIDsE);
//     fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),loanIDsE);      
           
              int numberLo=parseInt(loanIDsE.split("[;]")[0].replace("'", ""));
       
       switch(numberLo){
          
            case 1:
                data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                  data5x.add(data4x);
                break;
             case 2:
                 int x=0;
                 while(x<2){
                     if(x==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }
                  
                  data5x.add(data4x);
                 x++;
                 }
                 break;
              case 3:
                int x1=0;
                 while(x1<3){
                     if(x1==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x1==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x1==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                  
                  data5x.add(data4x);
                 x1++;
                 }
                  
                  
                  break;
               case 4:
                   int x2=0;
                 while(x2<4){
                     if(x2==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x2==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x2==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                     else if(x2==3){
                     data4x=new ArrayList();
                 data4x.add("4");
                  data4x.add(loanIDsE.split("[;]")[7]);
                  data4x.add(loanIDsE.split("[;]")[8]);
                     
                     }
                  
                  data5x.add(data4x);
                 x2++;
                 }
                   break;
                case 5:
                     int x3=0;
                 while(x3<4){
                     if(x3==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x3==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x3==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                     else if(x3==3){
                     data4x=new ArrayList();
                 data4x.add("4");
                  data4x.add(loanIDsE.split("[;]")[7]);
                  data4x.add(loanIDsE.split("[;]")[8]);
                     
                     }
                   else if(x3==4){
                     data4x=new ArrayList();
                 data4x.add("5");
                  data4x.add(loanIDsE.split("[;]")[9]);
                  data4x.add(loanIDsE.split("[;]")[10]);
                     
                     }
                  data5x.add(data4x);
                 x3++;
                 }
                    break;
                 case 6:
                    int x4=0;
                 while(x4<4){
                     if(x4==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x4==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x4==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                     else if(x4==3){
                     data4x=new ArrayList();
                 data4x.add("4");
                  data4x.add(loanIDsE.split("[;]")[7]);
                  data4x.add(loanIDsE.split("[;]")[8]);
                     
                     }
                   else if(x4==4){
                     data4x=new ArrayList();
                 data4x.add("5");
                  data4x.add(loanIDsE.split("[;]")[9]);
                  data4x.add(loanIDsE.split("[;]")[10]);
                     
                     }
                      else if(x4==5){
                     data4x=new ArrayList();
                 data4x.add("6");
                  data4x.add(loanIDsE.split("[;]")[11]);
                  data4x.add(loanIDsE.split("[;]")[12]);
                     
                     }
                  data5x.add(data4x);
                 x4++;
                 }  
                     
                     break;
                  case 7:
                       int x5=0;
                 while(x5<4){
                     if(x5==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x5==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x5==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                     else if(x5==3){
                     data4x=new ArrayList();
                 data4x.add("4");
                  data4x.add(loanIDsE.split("[;]")[7]);
                  data4x.add(loanIDsE.split("[;]")[8]);
                     
                     }
                   else if(x5==4){
                     data4x=new ArrayList();
                 data4x.add("5");
                  data4x.add(loanIDsE.split("[;]")[9]);
                  data4x.add(loanIDsE.split("[;]")[10]);
                     
                     }
                      else if(x5==5){
                     data4x=new ArrayList();
                 data4x.add("6");
                  data4x.add(loanIDsE.split("[;]")[11]);
                  data4x.add(loanIDsE.split("[;]")[12]);
                     
                     }
                     
                      else if(x5==6){
                     data4x=new ArrayList();
                 data4x.add("7");
                  data4x.add(loanIDsE.split("[;]")[13]);
                  data4x.add(loanIDsE.split("[;]")[14]);
                     
                     }
                  data5x.add(data4x);
                 x5++;
                 }  
                     
                     break;
        case 8:
               int x6=0;
                 while(x6<4){
                     if(x6==0){
                 data4x=new ArrayList();
                 data4x.add("1");
                  data4x.add(loanIDsE.split("[;]")[1]);
                  data4x.add(loanIDsE.split("[;]")[2]);
                     } else if(x6==1){
                     data4x=new ArrayList();
                 data4x.add("2");
                  data4x.add(loanIDsE.split("[;]")[3]);
                  data4x.add(loanIDsE.split("[;]")[4]);
                     
                     }else if(x6==2){
                     data4x=new ArrayList();
                 data4x.add("3");
                  data4x.add(loanIDsE.split("[;]")[5]);
                  data4x.add(loanIDsE.split("[;]")[6]);
                     
                     }
                     else if(x6==3){
                     data4x=new ArrayList();
                 data4x.add("4");
                  data4x.add(loanIDsE.split("[;]")[7]);
                  data4x.add(loanIDsE.split("[;]")[8]);
                     
                     }
                   else if(x6==4){
                     data4x=new ArrayList();
                 data4x.add("5");
                  data4x.add(loanIDsE.split("[;]")[9]);
                  data4x.add(loanIDsE.split("[;]")[10]);
                     
                     }
                      else if(x6==5){
                     data4x=new ArrayList();
                 data4x.add("6");
                  data4x.add(loanIDsE.split("[;]")[11]);
                  data4x.add(loanIDsE.split("[;]")[12]);
                     
                     }
                     
                      else if(x6==6){
                     data4x=new ArrayList();
                 data4x.add("7");
                  data4x.add(loanIDsE.split("[;]")[13]);
                  data4x.add(loanIDsE.split("[;]")[14]);
                     
                     }
                      else if(x6==7){
                     data4x=new ArrayList();
                 data4x.add("8");
                  data4x.add(loanIDsE.split("[;]")[15]);
                  data4x.add(loanIDsE.split("[;]")[16]);
                     
                     }
                  data5x.add(data4x);
                 x6++;
                 }  
                      
               
               break;
       
       }
        modelxN= new ListTableModel( data5x, column1x);
           table1.setModel(modelxN);
           
       TableRowSorter<ListTableModel> sorterx = new TableRowSorter<>(modelxN);
      table1.setRowSorter(sorterx);
       
         cq.setAutoCommit(true);
           loancon.closeConnection( cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }


}

public synchronized void createRejectedLoan(List theReject){

     try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loanerrorqueue VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    ps.setObject(1, null);
    ps.setObject(2, theReject.get(0));//Loan Trn ID:0
    ps.setObject(3, theReject.get(1));//Loan ID:1
    ps.setObject(4, this.getDBCurrentDate());//Trn Date
    ps.setObject(5, theReject.get(2));//Princimpal Amount:2
    ps.setObject(6,theReject.get(3));//Interest Rate:3
    ps.setObject(7, theReject.get(4));//Loan Tenure:4
    ps.setObject(8, theReject.get(5));//Reason for Rejection:5
    ps.setObject(9,theReject.get(6));//Inputter Id:6
    ps.setObject(10, theReject.get(7));//Authorisor Id:7
      ps.setObject(11,theReject.get(8));//Borrower Name:8
    ps.setObject(12, theReject.get(9));//Borrower Account:9
    ps.setObject(13, 10000.0);
    ps.setObject(14,"NA");
    ps.setObject(15, 1);
     ps.setObject(16, 10000.0);
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
     
     }
     
     }

public synchronized String inputterId(String trnLoanId){

    String inputter="";
    
    try {
       Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
             
             String query = "SELECT inputter_id from new_loan_appstore WHERE trn_id="+"'"+trnLoanId+"'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
          
             //0 No of loan instalments
             // 1 Loan Tenure
             //2 Interest Rate
             //3 Total Interest amount
             // 4 princimpal amount
             // 5 total loan amount
             //6 intalment start date
             //7 intalment end date
             //8 The instalment amount
             
           while(rst3.next()){
        
          inputter=rst3.getString("inputter_id");//0 No of loan instalments
              
           
             }
            
           cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }

  return   inputter;

} 

public synchronized String authoriserId(String trnLoanId){

    String authoriser="";
    
    try {
       Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
             
             String query = "SELECT authoriser_id from new_loan_appstore WHERE trn_id="+"'"+trnLoanId+"'";
             
             PreparedStatement ps = cq.prepareStatement(query);
             
             ResultSet rst3 = ps.executeQuery();
          
             //0 No of loan instalments
             // 1 Loan Tenure
             //2 Interest Rate
             //3 Total Interest amount
             // 4 princimpal amount
             // 5 total loan amount
             //6 intalment start date
             //7 intalment end date
             //8 The instalment amount
             
           while(rst3.next()){
        
          authoriser=rst3.getString("authoriser_id");//0 No of loan instalments
              
           
             }
            
           cq.setAutoCommit(true);
            loancon.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }

  return   authoriser;

} 

public String remainingTotalLoanBalance(String accountNumber){
    
 String balanceDue="0.0";

if(remainingInstalmentsThere(accountNumber)){
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(InstalmentRemaining) AS remainingTotalAmount FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   balanceDue=rst3.getString("remainingTotalAmount");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
}

    return balanceDue;
    }



public boolean loanExists(String accountNumber){
    
 int theLoan=0;boolean exists=false;


    try {
        
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String query = "SELECT COUNT(instalment_no) AS loanThere FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
   
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   theLoan=rst3.getInt("loanThere");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
if(theLoan>0){

exists=true;
}

    return exists;
    }

public boolean borrowerExists(String accountNumber){
    
 int theLoan=0;boolean exists=false;


    try {
        
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String query = "SELECT COUNT(trn_id) AS loanThere FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
   
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   theLoan=rst3.getInt("loanThere");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
if(theLoan>0){

exists=true;
}

    return exists;
    }



private boolean remainingInstalmentsThere(String accountNumber){
boolean there=false; int nuInsal=0;
try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT COUNT(InstalmentRemaining) AS remainingTotalAmounts FROM  new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   nuInsal=rst3.getInt("remainingTotalAmounts");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

if(nuInsal>0){
there=true;
}

    return there;


}

public String remainingTotalPrincipal(String accountNumber){
 String balanceDue="0.0";


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(PrincipalRemaining) AS remainingTotalAmount FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   balanceDue=rst3.getString("remainingTotalAmount");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
   

    return balanceDue;
    }
public String remainingTotalInterest(String accountNumber){
 String balanceDue="0.0";


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(InterestRemaing) AS totalInterestRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   balanceDue=rst3.getString("totalInterestRemaining");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
   

    return balanceDue;
    }

public String remainingTotalLoanPenalty(String accountNumber){
 String balanceDue="0.0";


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(LoanPenaltyRemaining) AS remainingLoanPenalty FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){

   balanceDue=rst3.getString("remainingLoanPenalty");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
   

    return balanceDue;
    }
public String remainingTotalAccumulatedInterest(String accountNumber){
 String balanceDue="0.0";


    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(AccumulatedInterestRemaining) AS remainingTotalAccumulatedInterst FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){


   balanceDue=rst3.getString("remainingTotalAccumulatedInterst");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
   

    return balanceDue;
    }

public int currentLoanInstalmentNow(String accountNumber){
int theInstalmentNumber=0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalment_no FROM  new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInstalmentNumber=rst3.getInt("instalment_no");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInstalmentNumber;
}


public double currentInterestRemainNow(String accountNumber, int trnId){

    
    double theInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   InterestRemaing FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInterestNow=rst3.getDouble("InterestRemaing");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInterestNow;

}

public double currentLoanPenaltyNow(String accountNumber, int trnId){

    
    double theInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   LoanPenaltyRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInterestNow=rst3.getDouble("LoanPenaltyRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInterestNow;

}



        
        public double currentAccruedInterestNow(String accountNumber, int trnId){

    
    double theInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT    AccruedInterestRemaing FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
        
  theInterestNow=rst3.getDouble("AccruedInterestRemaing");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInterestNow;

}
     public double currentLoanPrincipalRNow(String accountNumber, int trnId){

    
    double thePrincipalNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT    PrincipalRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  thePrincipalNow=rst3.getDouble("PrincipalRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return thePrincipalNow;

}    
        
        
public double remainingInstalmentNow(String accountNumber, int trnId){

    
    double theInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   InstalmentRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInterestNow=rst3.getDouble("InstalmentRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInterestNow;

}

        
public double InstalmentPaidNow(String accountNumber, int trnId,Component c){

//    JOptionPane.showMessageDialog(c, accountNumber+" "+trnId);
    
    
    String theInterestNow="";

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalment_paid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theInterestNow=rst3.getString("instalment_paid");
// JOptionPane.showMessageDialog(c, theInterestNow);
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   if(theInterestNow.equalsIgnoreCase("NY")){
theInterestNow="0.0";

}

return parseDouble(theInterestNow);

}

   public double InterestPaidNow(String accountNumber, int trnId){

    
    double theInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   InterestPaid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInterestNow=rst3.getDouble("InterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInterestNow;

}     
 public double accruedInterestPaidNow(String accountNumber, int trnId){

    
    double theAccruedInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   AccruedInterestPaid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theAccruedInterestNow=rst3.getDouble("AccruedInterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theAccruedInterestNow;

}     
   public double accumulatedInterestPaidNow(String accountNumber, int trnId){

    
    double theAccumulatedInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   AccumulatedInterestPaid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theAccumulatedInterestNow=rst3.getDouble("AccumulatedInterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theAccumulatedInterestNow;

}     
  
   public double loanPenaltyPaidNow(String accountNumber, int trnId){

    
    double thePaidLoanPenalty=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   LoanPenaltyPaid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  thePaidLoanPenalty=rst3.getDouble("LoanPenaltyPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return thePaidLoanPenalty;

}   
   
   
   public double loanPrincipalPaidNow(String accountNumber, int trnId){

    
    double thePaidPrincipal=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   PrincipalPaid FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  thePaidPrincipal=rst3.getDouble("PrincipalPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return thePaidPrincipal;

}   
   
   
    public String instalmentNextDueDateNow(String accountNumber, int trnId){

    
    String theInstalmentDate="";

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalment_due_date FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theInstalmentDate=rst3.getString("instalment_due_date");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theInstalmentDate;

}  
   
   
   public double currentAccumulatedInterestNow(String accountNumber, int trnId){

    
    double theAInterestNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   AccumulatedInterestRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theAInterestNow=rst3.getDouble("AccumulatedInterestRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theAInterestNow;

}
  
        public double theActualLoanPenaltyNow(String accountNumber, int trnId){

    
    double theLoanPenaltyNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   LoanPenalty FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPenaltyNow=rst3.getDouble("LoanPenalty");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPenaltyNow;

}
        
   public double theActualAccumulatedInterestNow(String accountNumber, int trnId){

    
    double theLoanPenaltyNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   AccumulatedInterest FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPenaltyNow=rst3.getDouble("AccumulatedInterest");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPenaltyNow;

}
public double theActualInterestNow(String accountNumber, int trnId){

    
    double theLoanPenaltyNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   interest_amount FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPenaltyNow=rst3.getDouble("interest_amount");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPenaltyNow;

}    
   
   
        public double theInstalmentNow(String accountNumber, int trnId){

    
    double theLoanPinstalmentNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalment_amount FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPinstalmentNow=rst3.getDouble("instalment_amount");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPinstalmentNow;

}     
 public double theRemainingInstalmentNow(String accountNumber, int trnId){

    
    double theLoanPinstalmentNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   InstalmentRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPinstalmentNow=rst3.getDouble("InstalmentRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPinstalmentNow;

}   


         
        public double currentLoanPenaltytNow(String accountNumber, int trnId,Component c){

//    JOptionPane.showMessageDialog(c, "in");
    double theLoanPenaltyNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   LoanPenaltyRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPenaltyNow=rst3.getDouble("LoanPenaltyRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPenaltyNow;

}
   
         
    public double currentLoanPrincipalNow(String accountNumber, int trnId){

    
    double theLoanPrincipalNow=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   PrincipalRemaining FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND instalment_no="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
 
  theLoanPrincipalNow=rst3.getDouble("PrincipalRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theLoanPrincipalNow;

} 

  public double totalAccumulatedInterest(String accountNumber){

    
    double totalAccuInterst=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(AccumulatedInterest) As totalAccum FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  totalAccuInterst=rst3.getDouble("totalAccum");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return totalAccuInterst;

}    
  
        public double totalLoanPenalty(String accountNumber){

    
    double totalAccuInterst=0.0;

    if(this.loanExists(accountNumber)){
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  SUM(LoanPenalty) AS Lp FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  totalAccuInterst=rst3.getDouble("Lp");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
   

return totalAccuInterst;

}     
       
    public double totalInstalmentRemainingNow(String accountNumber){//The original existing total amount including principal,accumulated interest,penalty and interest that is due to be paid

    
    double theTotalInstalments=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   balance_due FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
while(rst3.next()){
 
  theTotalInstalments=rst3.getDouble("balance_due");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalInstalments;

}       

     public double totalInstalmentNow(String accountNumber){//The original existing total amount including principal,accumulated interest,penalty and interest that is due to be paid

    
    double theTotalInstalments=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalment_amount FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
while(rst3.next()){
 
  theTotalInstalments=rst3.getDouble("instalment_amount");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalInstalments;

}      
    
    public double totalLoanAmountNow(String accountNumber){//The original existing total amount including principal,accumulated interest,penalty and interest that is due to be paid

    
    double theTotalInstalments=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   total_loanAmount FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
while(rst3.next()){
 
  theTotalInstalments=rst3.getDouble("total_loanAmount");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalInstalments;

}    
public double totalInstalmentPaidNow(String accountNumber){//Total amount paid so far including interest, principal,accumulated interest and penalty

    
    double theTotalInstalmentsPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   instalments_paid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 while(rst3.next()){
 
  theTotalInstalmentsPaid=rst3.getDouble("instalments_paid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalInstalmentsPaid;

} 


    
        public double totalCurrentRemainingInterestNow(String accountNumber){

    
    double theTotalCurrentInterestRemaining=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalInterestRemaining FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
 
  theTotalCurrentInterestRemaining=rst3.getDouble("TotalInterestRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentInterestRemaining;

} 
    
  public double totalCurrentRemainingAccumulatedInterestNow(String accountNumber){

    double theTotalCurrentAccumulatedInterestRemaining=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalAccumulatedInterestRemaining FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
 
  theTotalCurrentAccumulatedInterestRemaining=rst3.getDouble("TotalAccumulatedInterestRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentAccumulatedInterestRemaining;

}      
        
  
  
  public double totalCurrentRemainingAccruedInterestNow(String accountNumber){

    
    double theTotalCurrentAccumulatedInterestRemaining=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalAccruedInterestRemaining FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
 
  theTotalCurrentAccumulatedInterestRemaining=rst3.getDouble("TotalAccruedInterestRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentAccumulatedInterestRemaining;

}     
  
  
  public double totalCurrentRemainingLoanPenaltyNow(String accountNumber){

    
    double theTotalRemainingLoanPenalty=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalLoanPenaltyRemaining FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
 
  theTotalRemainingLoanPenalty=rst3.getDouble("TotalLoanPenaltyRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalRemainingLoanPenalty;

}     
  public double totalCurrentRemainingPrincipalNow(String accountNumber){

    
    double theTotalRemainingLoanPrincipal=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalPrincipalRemaining FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
 
  theTotalRemainingLoanPrincipal=rst3.getDouble("TotalPrincipalRemaining");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalRemainingLoanPrincipal;

}     
        public double totalInterestPaidNow(String accountNumber){

    
    double theTotalCurrentInterestPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalInterestPaid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theTotalCurrentInterestPaid=rst3.getDouble("TotalInterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentInterestPaid;

} 
 
         
         
        
        public double totalAccumulatedInterestPaidNow(String accountNumber){

    
    double theTotalCurrentAccumulatedInterestPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalAccumulatedInterestPaid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theTotalCurrentAccumulatedInterestPaid=rst3.getDouble("TotalAccumulatedInterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentAccumulatedInterestPaid;

} 
        
  
              
        
        
    public double totalAccruedInterestPaidNow(String accountNumber){

    
    double theTotalCurrentAccumulatedInterestPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalAccruedInterestPaid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theTotalCurrentAccumulatedInterestPaid=rst3.getDouble("TotalAccruedInterestPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalCurrentAccumulatedInterestPaid;

}      
        
        
        
        
     public double totalLoanPenaltyPaidNow(String accountNumber){

    
    double theTotalLoanPenaltyPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalLoanPenaltyPaid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theTotalLoanPenaltyPaid=rst3.getDouble("TotalLoanPenaltyPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalLoanPenaltyPaid;

}         
        
 public double totalLoanPrincipalNow(String accountNumber){

    
    double theTotalPrincipalPaid=0.0;

 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   TotalPrincipalPaid FROM new_loan_appstore WHERE applicant_account_number="+"'"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
   while(rst3.next()){
 
  theTotalPrincipalPaid=rst3.getDouble("TotalPrincipalPaid");

    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

   

return theTotalPrincipalPaid;

}        
        

        private synchronized void updateAllRepaymentsReports(String type,String accountNumberNow,int currentInstalmentId,double amount,Component c){
        
            
//         JOptionPane.showMessageDialog(c, "type= "+type);
         
//            JOptionPane.showMessageDialog(c, ("type= "+type+"'"+","+"'newloan"+accountNumberNow+"'"+","+"currentInstalmentId= "+currentInstalmentId+","+"amount= "+amount));
            
         try {
                Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
String theProcedure ="CALL loanRepaymentsUpdatesAll("+"'"+type+"'"+","+"'newloan"+accountNumberNow+"'"+","+currentInstalmentId+","+amount+")";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    
        
        
        }
        
  private synchronized void updateInterestRepaymentsDates(double interestPaid,String accountNumberNow,Component c){
        
//          JOptionPane.showMessageDialog(c, "'"+interestPaid+"'"+","+"'newloan"+accountNumberNow+"'");
          
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
String theProcedure ="CALL DateManagementForLenders("+"'"+interestPaid+"'"+","+"'newloan"+accountNumberNow+"'"+")";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    
        
        
        }   
        
        
                
                 public synchronized void setMethod(int theM){
        
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
          
String theProcedure ="CALL setMoneyLendMethod("+theM+")";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    
        }  
                 
              
      public synchronized void updateInterestRepaymentsInterest(Component c){
        
          if(this.getMethod(c)==2){
          
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
          
String theProcedure ="CALL InitialInterestManagement()";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
          }else if(this.getMethod(c)==1){
          
           try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
          
String theProcedure ="CALL InterestManagementForLendersNonCompounded()";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          
          
          }
        }   
        
         
      public synchronized void updateDailyReport(){
        
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
String theProcedure ="CALL prepareDailyReport()";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    
        
        
        }  
        
      public synchronized void updateNewInterestNow(String accountNumberNow,int currentInstalmentId,double currentInterestNow,Component c){

   SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                                
                        updateAllRepaymentsReports("updateNewInterestNow",accountNumberNow,currentInstalmentId,currentInterestNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
   
    
                     
                  
                     
                     
                     
                     
                     
    
    long nextDueDate = 0, todayDatez = 0;
    
double existingRemainingInstalment=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaid=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingInterest=currentInterestRemainNow(accountNumberNow,currentInstalmentId);


double existingInterestPaid=InterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDate= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatez= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalment=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaid=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingInterest=totalCurrentRemainingInterestNow(accountNumberNow);

double existingTotalInterestPaid=totalInterestPaidNow(accountNumberNow);

existingRemainingInstalment-=currentInterestNow;//Remaining instalment on the amortization schedule


existingInstalmentPaid+=currentInterestNow;//instalment paid on the amortization schedule

existingRemainingInterest-=currentInterestNow;//interest remaining on the amortization schedule

existingInterestPaid+=currentInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalment-=currentInterestNow;

existingTotalInstalmentPaid+=currentInterestNow;

existingTotalRemainingInterest-=currentInterestNow;

existingTotalInterestPaid+=currentInterestNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,InterestRemaing=?, InterestPaid=? ,instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalment);
    
    pstool.setObject(2, existingInstalmentPaid);
    
    pstool.setObject(3, existingRemainingInterest);
    
    pstool.setObject(4, existingInterestPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
     pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalment);
    
    pstool2.setObject(2, existingTotalInstalmentPaid);
    
    pstool2.setObject(3, existingTotalRemainingInterest);
    
    pstool2.setObject(4, existingTotalInterestPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalment);
    
    pstool2.setObject(2, existingTotalInstalmentPaid);
    
    pstool2.setObject(3, existingTotalRemainingInterest);
    
    pstool2.setObject(4, existingTotalInterestPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalment<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDate<todayDatez){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDate>=todayDatez){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}
 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }
 
 
   SwingWorker<Void,Void> updateAllRepaymentsReports1=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                      updateInterestRepaymentsDates(currentInterestNow,accountNumberNow,c);
                                return null;
                            }  };
                     updateAllRepaymentsReports1.execute();  

}

      
public synchronized void updateNewInterestNowWrittenOff(String accountNumberNow,int currentInstalmentId,double currentInterestNow,Component c){

   SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewInterestNow",accountNumberNow,currentInstalmentId,currentInterestNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
   
    
                     
                  
                     
                     
                     
                     
                     
    
    long nextDueDate = 0, todayDatez = 0;
    
double existingRemainingInstalment=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaid=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingInterest=currentInterestRemainNow(accountNumberNow,currentInstalmentId);


double existingInterestPaid=InterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDate= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatez= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalment=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaid=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingInterest=totalCurrentRemainingInterestNow(accountNumberNow);

double existingTotalInterestPaid=totalInterestPaidNow(accountNumberNow);

existingRemainingInstalment-=currentInterestNow;//Remaining instalment on the amortization schedule


existingInstalmentPaid+=currentInterestNow;//instalment paid on the amortization schedule

existingRemainingInterest-=currentInterestNow;//interest remaining on the amortization schedule

existingInterestPaid+=currentInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalment-=currentInterestNow;

existingTotalInstalmentPaid+=currentInterestNow;

existingTotalRemainingInterest-=currentInterestNow;

existingTotalInterestPaid+=currentInterestNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,InterestRemaing=?, InterestPaid=? ,instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalment);
    
    pstool.setObject(2, existingInstalmentPaid);
    
    pstool.setObject(3, existingRemainingInterest);
    
    pstool.setObject(4, existingInterestPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
     pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalment);
    
    pstool2.setObject(2, existingTotalInstalmentPaid);
    
    pstool2.setObject(3, existingTotalRemainingInterest);
    
    pstool2.setObject(4, existingTotalInterestPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalment);
    
    pstool2.setObject(2, existingTotalInstalmentPaid);
    
    pstool2.setObject(3, existingTotalRemainingInterest);
    
    pstool2.setObject(4, existingTotalInterestPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalment<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDate<todayDatez){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDate>=todayDatez){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}
 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }
 
 
   SwingWorker<Void,Void> updateAllRepaymentsReports1=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                      updateInterestRepaymentsDates(currentInterestNow,accountNumberNow,c);
                                return null;
                            }  };
                     updateAllRepaymentsReports1.execute();  

}

//public synchronized void updateNewAccruedInterestNow(String accountNumberNow,int currentInstalmentId,double currentAccruedInterestNow){
// 
//    long nextDueDate = 0, todayDatez = 0;
//    
//double existingRemainingInstalment=remainingInstalmentNow(accountNumberNow,currentInstalmentId);
//
//
//double existingInstalmentPaid=InstalmentPaidNow(accountNumberNow,currentInstalmentId);
//
//
//double existingRemainingInterest=currentInterestRemainNow(accountNumberNow,currentInstalmentId);
//
//
//double existingInterestPaid=InterestPaidNow(accountNumberNow,currentInstalmentId);
//
//
//String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);
//
//        try {
//            nextDueDate= sdfS.parse(instalmentNextDueDate).getTime();
//             todayDatez= sdfS.parse(getDBCurrentDate()).getTime();
//        } catch (ParseException ex) {
//            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//        }
// 
//
//
//double existingTotalRemainingInstalment=totalInstalmentRemainingNow(accountNumberNow);
//
//double existingTotalInstalmentPaid=totalInstalmentPaidNow(accountNumberNow);
//
//double existingTotalRemainingInterest=totalCurrentRemainingInterestNow(accountNumberNow);
//
//double existingTotalInterestPaid=totalInterestPaidNow(accountNumberNow);
//
//existingRemainingInstalment-=currentInterestNow;//Remaining instalment on the amortization schedule
//
//existingInstalmentPaid+=currentInterestNow;//instalment paid on the amortization schedule
//
//existingRemainingInterest-=currentInterestNow;//interest remaining on the amortization schedule
//
//existingInterestPaid+=currentInterestNow;//interest remaining on the amortization schedule
//
//
//
//existingTotalRemainingInstalment-=currentInterestNow;
//
//existingTotalInstalmentPaid+=currentInterestNow;
//
//existingTotalRemainingInterest-=currentInterestNow;
//
//existingTotalInterestPaid+=currentInterestNow;
//
//try {
//    
//    Connection cq=loancon.createConnection(); 
//    
//    cq.setAutoCommit(false); 
//    
//    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,InterestRemaing=?, InterestPaid=? ,instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
//   
//    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
//        
//    pstool.setObject(1, existingRemainingInstalment);
//    
//    pstool.setObject(2, existingInstalmentPaid);
//    
//    pstool.setObject(3, existingRemainingInterest);
//    
//    pstool.setObject(4, existingInterestPaid);
//    
//     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
//    
//      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
//     
//    pstool.setObject(7, currentInstalmentId);

//    pstool.setObject(8, "newloan"+accountNumberNow);

 
//    
//    pstool.execute();
//    }
//    cq.setAutoCommit(true);
//    
//     cq.setAutoCommit(false); 
//    
//    String UpdateQuary2 = "UPDATE new_loan_appstore SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
//     
//    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
//        
//    pstool2.setObject(1, existingTotalRemainingInstalment);
//    
//    pstool2.setObject(2, existingTotalInstalmentPaid);
//    
//    pstool2.setObject(3, existingTotalRemainingInterest);
//    
//    pstool2.setObject(4, existingTotalInterestPaid);
//    
//    pstool2.setObject(5, "newloan"+accountNumberNow);
//    
//    pstool2.execute();
//    }
//    cq.setAutoCommit(true);

//     cq.setAutoCommit(false); 
//    
//    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET balance_due=?, instalments_paid=?,TotalInterestRemaining=?, TotalInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
//     
//    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
//        
//    pstool2.setObject(1, existingTotalRemainingInstalment);
//    
//    pstool2.setObject(2, existingTotalInstalmentPaid);
//    
//    pstool2.setObject(3, existingTotalRemainingInterest);
//    
//    pstool2.setObject(4, existingTotalInterestPaid);
//    
//    pstool2.setObject(5, "newloan"+accountNumberNow);
//    
//    pstool2.execute();
//    }
//    cq.setAutoCommit(true);
//    
//    loancon.closeConnection(cq);
//
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }
//
//
//if(existingRemainingInstalment<=0){
//    
//updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
//updateNumberInstalmentStatus(accountNumberNow);
//}else{
//    
//if(nextDueDate<todayDatez){
//updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
//}else if(nextDueDate>=todayDatez){
//updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");
//
//}
//
//}
//
//
//}



public synchronized void updateNewAccumulatedInterestNowWrittenOff(String accountNumberNow,int currentInstalmentId,double currentAccumulatedInterestNow,Component c){

    
     SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewAccumulatedInterestNow",accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
    
  long nextDueDateA = 0, todayDatezA = 0;
    
double existingRemainingInstalmentx=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidx=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingAccumulatedInterest=currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);


double existingAccumulatedInterestPaid=accumulatedInterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateA= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezA= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentx=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidx=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingAccumulatedInterest=totalCurrentRemainingAccumulatedInterestNow(accountNumberNow);

double existingTotalSAccumulatedInterestPaid=totalAccumulatedInterestPaidNow(accountNumberNow);
//     fios.stringFileWriter(fios.createFileName("test", "testit", accountNumberNow+"trdyr.txt"),currentAccumulatedInterestNow+"");  

existingRemainingInstalmentx-=currentAccumulatedInterestNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidx+=currentAccumulatedInterestNow;//instalment paid on the amortization schedule

existingRemainingAccumulatedInterest-=currentAccumulatedInterestNow;//interest remaining on the amortization schedule

existingAccumulatedInterestPaid+=currentAccumulatedInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentx-=currentAccumulatedInterestNow;

existingTotalInstalmentPaidx+=currentAccumulatedInterestNow;

existingTotalRemainingAccumulatedInterest-=currentAccumulatedInterestNow;

existingTotalSAccumulatedInterestPaid+=currentAccumulatedInterestNow;



try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,AccumulatedInterestRemaining=?, AccumulatedInterestPaid=?,instalment_paid_date=? ,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentx);
    
    pstool.setObject(2, existingInstalmentPaidx);
    
    pstool.setObject(3, existingRemainingAccumulatedInterest);
    
    pstool.setObject(4, existingAccumulatedInterestPaid);
    
  pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
    pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
  
    pstool.setObject(7, currentInstalmentId);
    
       pstool.setObject(8, "newloan"+accountNumberNow);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE    new_loan_appstore SET balance_due=?, instalments_paid=?, TotalAccumulatedInterestRemaining=?, TotalAccumulatedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentx);
    
    pstool2.setObject(2, existingTotalInstalmentPaidx);
    
    pstool2.setObject(3, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(4, existingTotalSAccumulatedInterestPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE    new_loan_appstore1 SET balance_due=?, instalments_paid=?, TotalAccumulatedInterestRemaining=?, TotalAccumulatedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentx);
    
    pstool2.setObject(2, existingTotalInstalmentPaidx);
    
    pstool2.setObject(3, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(4, existingTotalSAccumulatedInterestPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
//   fios.stringFileWriter(fios.createFileName("test", "testit", accountNumberNow+"trdyr2.txt"),existingRemainingInstalmentx+"");  

if(existingRemainingInstalmentx<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateA<todayDatezA){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateA>=todayDatezA){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}
 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }

}




public synchronized void updateNewAccumulatedInterestNow(String accountNumberNow,int currentInstalmentId,double currentAccumulatedInterestNow,Component c){

    
     SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewAccumulatedInterestNow",accountNumberNow,currentInstalmentId,currentAccumulatedInterestNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
    
  long nextDueDateA = 0, todayDatezA = 0;
    
double existingRemainingInstalmentx=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidx=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingAccumulatedInterest=currentAccumulatedInterestNow(accountNumberNow,currentInstalmentId);


double existingAccumulatedInterestPaid=accumulatedInterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateA= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezA= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentx=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidx=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingAccumulatedInterest=totalCurrentRemainingAccumulatedInterestNow(accountNumberNow);

double existingTotalSAccumulatedInterestPaid=totalAccumulatedInterestPaidNow(accountNumberNow);
//     fios.stringFileWriter(fios.createFileName("test", "testit", accountNumberNow+"trdyr.txt"),currentAccumulatedInterestNow+"");  

existingRemainingInstalmentx-=currentAccumulatedInterestNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidx+=currentAccumulatedInterestNow;//instalment paid on the amortization schedule

existingRemainingAccumulatedInterest-=currentAccumulatedInterestNow;//interest remaining on the amortization schedule

existingAccumulatedInterestPaid+=currentAccumulatedInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentx-=currentAccumulatedInterestNow;

existingTotalInstalmentPaidx+=currentAccumulatedInterestNow;

existingTotalRemainingAccumulatedInterest-=currentAccumulatedInterestNow;

existingTotalSAccumulatedInterestPaid+=currentAccumulatedInterestNow;



try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,AccumulatedInterestRemaining=?, AccumulatedInterestPaid=?,instalment_paid_date=? ,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentx);
    
    pstool.setObject(2, existingInstalmentPaidx);
    
    pstool.setObject(3, existingRemainingAccumulatedInterest);
    
    pstool.setObject(4, existingAccumulatedInterestPaid);
    
  pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
    pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
  
    pstool.setObject(7, currentInstalmentId);
    
       pstool.setObject(8, "newloan"+accountNumberNow);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE    new_loan_appstore SET balance_due=?, instalments_paid=?, TotalAccumulatedInterestRemaining=?, TotalAccumulatedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentx);
    
    pstool2.setObject(2, existingTotalInstalmentPaidx);
    
    pstool2.setObject(3, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(4, existingTotalSAccumulatedInterestPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE    new_loan_appstore1 SET balance_due=?, instalments_paid=?, TotalAccumulatedInterestRemaining=?, TotalAccumulatedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentx);
    
    pstool2.setObject(2, existingTotalInstalmentPaidx);
    
    pstool2.setObject(3, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(4, existingTotalSAccumulatedInterestPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
//   fios.stringFileWriter(fios.createFileName("test", "testit", accountNumberNow+"trdyr2.txt"),existingRemainingInstalmentx+"");  

if(existingRemainingInstalmentx<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateA<todayDatezA){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateA>=todayDatezA){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}
 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }

}

public synchronized void updateNewAccruedInterestNowWrittenOff(String accountNumberNow,int currentInstalmentId,double currentAccruedInterestNow,Component c){

long nextDueDateAa = 0, todayDatezAa = 0;
    
double existingRemainingInstalmentxa=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxa=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingAccruedInterest=currentAccruedInterestNow(accountNumberNow,currentInstalmentId);



double existingAccruedInterestPaid=accruedInterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAa= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAa= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxa=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxa=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingAccruedInterest=totalCurrentRemainingAccruedInterestNow(accountNumberNow);

double existingTotalSAccruedInterestPaid=totalAccruedInterestPaidNow(accountNumberNow);



existingRemainingInstalmentxa-=currentAccruedInterestNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxa+=currentAccruedInterestNow;//instalment paid on the amortization schedule

existingRemainingAccruedInterest-=currentAccruedInterestNow;//interest remaining on the amortization schedule

existingAccruedInterestPaid+=currentAccruedInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxa-=currentAccruedInterestNow;

existingTotalInstalmentPaidxa+=currentAccruedInterestNow;

existingTotalRemainingAccruedInterest-=currentAccruedInterestNow;

existingTotalSAccruedInterestPaid+=currentAccruedInterestNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
  
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,AccruedInterestRemaing=?, AccruedInterestPaid=?,instalment_paid_date=?,instalment_paid_variance=?  WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxa);
    
    pstool.setObject(2, existingInstalmentPaidxa);
    
    pstool.setObject(3, existingRemainingAccruedInterest);
    
    pstool.setObject(4, existingAccruedInterestPaid);
    
      pstool.setObject(5,getDBCurrentDate());//Loan cycle status
      
    pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
    
    pstool.setObject(7, currentInstalmentId);
    
    pstool.setObject(8, "newloan"+accountNumberNow);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE  new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalAccruedInterestRemaining=?,  TotalAccruedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxa);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxa);
    
    pstool2.setObject(3, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(4, existingTotalSAccruedInterestPaid);
    
     
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
   
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE  new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalAccruedInterestRemaining=?,  TotalAccruedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxa);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxa);
    
    pstool2.setObject(3, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(4, existingTotalSAccruedInterestPaid);
    
     
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
   
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxa<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAa<todayDatezAa){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");



}else if(nextDueDateAa>=todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

}



public synchronized void updateNewAccruedInterestNow(String accountNumberNow,int currentInstalmentId,double currentAccruedInterestNow,Component c){

long nextDueDateAa = 0, todayDatezAa = 0;
    
double existingRemainingInstalmentxa=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxa=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingAccruedInterest=currentAccruedInterestNow(accountNumberNow,currentInstalmentId);



double existingAccruedInterestPaid=accruedInterestPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAa= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAa= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxa=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxa=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingAccruedInterest=totalCurrentRemainingAccruedInterestNow(accountNumberNow);

double existingTotalSAccruedInterestPaid=totalAccruedInterestPaidNow(accountNumberNow);



existingRemainingInstalmentxa-=currentAccruedInterestNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxa+=currentAccruedInterestNow;//instalment paid on the amortization schedule

existingRemainingAccruedInterest-=currentAccruedInterestNow;//interest remaining on the amortization schedule

existingAccruedInterestPaid+=currentAccruedInterestNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxa-=currentAccruedInterestNow;

existingTotalInstalmentPaidxa+=currentAccruedInterestNow;

existingTotalRemainingAccruedInterest-=currentAccruedInterestNow;

existingTotalSAccruedInterestPaid+=currentAccruedInterestNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
  
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,AccruedInterestRemaing=?, AccruedInterestPaid=?,instalment_paid_date=?,instalment_paid_variance=?  WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxa);
    
    pstool.setObject(2, existingInstalmentPaidxa);
    
    pstool.setObject(3, existingRemainingAccruedInterest);
    
    pstool.setObject(4, existingAccruedInterestPaid);
    
      pstool.setObject(5,getDBCurrentDate());//Loan cycle status
      
    pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
    
    pstool.setObject(7, currentInstalmentId);
    
    pstool.setObject(8, "newloan"+accountNumberNow);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE  new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalAccruedInterestRemaining=?,  TotalAccruedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxa);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxa);
    
    pstool2.setObject(3, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(4, existingTotalSAccruedInterestPaid);
    
     
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
   
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE  new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalAccruedInterestRemaining=?,  TotalAccruedInterestPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxa);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxa);
    
    pstool2.setObject(3, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(4, existingTotalSAccruedInterestPaid);
    
     
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
   
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxa<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAa<todayDatezAa){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");



}else if(nextDueDateAa>=todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

}

public synchronized void updateNewLoanPenaltyNowWrittenOff(String accountNumberNow,int currentInstalmentId,double currentPenaltyNow,Component c){

       SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewLoanPenaltyNow",accountNumberNow,currentInstalmentId,currentPenaltyNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
long nextDueDateAa = 0, todayDatezAa = 0;
    
double existingRemainingInstalmentxap=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxap=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingLoanPenalty=currentLoanPenaltyNow(accountNumberNow,currentInstalmentId);



double existingLoanPenaltyPaid=loanPenaltyPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAa= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAa= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxap=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxap=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingLoanPenalty=totalCurrentRemainingLoanPenaltyNow(accountNumberNow);

double existingTotalLoanPenaltyPaid=totalLoanPenaltyPaidNow(accountNumberNow);



existingRemainingInstalmentxap-=currentPenaltyNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxap+=currentPenaltyNow;//instalment paid on the amortization schedule

existingRemainingLoanPenalty-=currentPenaltyNow;//interest remaining on the amortization schedule

existingLoanPenaltyPaid+=currentPenaltyNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxap-=currentPenaltyNow;

existingTotalInstalmentPaidxap+=currentPenaltyNow;

existingTotalRemainingLoanPenalty-=currentPenaltyNow;

existingTotalLoanPenaltyPaid+=currentPenaltyNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
     
             
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,LoanPenaltyRemaining=?, LoanPenaltyPaid=?,instalment_paid_date=?,instalment_paid_variance=?  WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxap);
    
    pstool.setObject(2, existingInstalmentPaidxap);
    
    pstool.setObject(3, existingRemainingLoanPenalty);
    
    pstool.setObject(4, existingLoanPenaltyPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
    pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE  new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalLoanPenaltyRemaining=?, TotalLoanPenaltyPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxap);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxap);
    
    pstool2.setObject(3, existingTotalRemainingLoanPenalty);
    
    pstool2.setObject(4, existingTotalLoanPenaltyPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE  new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalLoanPenaltyRemaining=?, TotalLoanPenaltyPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxap);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxap);
    
    pstool2.setObject(3, existingTotalRemainingLoanPenalty);
    
    pstool2.setObject(4, existingTotalLoanPenaltyPaid);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxap<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAa<todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateAa>=todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }
 
}





public synchronized void updateNewLoanPenaltyNow(String accountNumberNow,int currentInstalmentId,double currentPenaltyNow,Component c){

       SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewLoanPenaltyNow",accountNumberNow,currentInstalmentId,currentPenaltyNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
    
long nextDueDateAa = 0, todayDatezAa = 0;
    
double existingRemainingInstalmentxap=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxap=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingLoanPenalty=currentLoanPenaltyNow(accountNumberNow,currentInstalmentId);



double existingLoanPenaltyPaid=loanPenaltyPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAa= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAa= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxap=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxap=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingLoanPenalty=totalCurrentRemainingLoanPenaltyNow(accountNumberNow);

double existingTotalLoanPenaltyPaid=totalLoanPenaltyPaidNow(accountNumberNow);



existingRemainingInstalmentxap-=currentPenaltyNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxap+=currentPenaltyNow;//instalment paid on the amortization schedule

existingRemainingLoanPenalty-=currentPenaltyNow;//interest remaining on the amortization schedule

existingLoanPenaltyPaid+=currentPenaltyNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxap-=currentPenaltyNow;

existingTotalInstalmentPaidxap+=currentPenaltyNow;

existingTotalRemainingLoanPenalty-=currentPenaltyNow;

existingTotalLoanPenaltyPaid+=currentPenaltyNow;

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
     
             
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,LoanPenaltyRemaining=?, LoanPenaltyPaid=?,instalment_paid_date=?,instalment_paid_variance=?  WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxap);
    
    pstool.setObject(2, existingInstalmentPaidxap);
    
    pstool.setObject(3, existingRemainingLoanPenalty);
    
    pstool.setObject(4, existingLoanPenaltyPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
    pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE  new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalLoanPenaltyRemaining=?, TotalLoanPenaltyPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxap);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxap);
    
    pstool2.setObject(3, existingTotalRemainingLoanPenalty);
    
    pstool2.setObject(4, existingTotalLoanPenaltyPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE  new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalLoanPenaltyRemaining=?, TotalLoanPenaltyPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxap);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxap);
    
    pstool2.setObject(3, existingTotalRemainingLoanPenalty);
    
    pstool2.setObject(4, existingTotalLoanPenaltyPaid);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxap<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAa<todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateAa>=todayDatezAa){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 }
 
}



public synchronized void updateNewLoanPrincipalNowWrittenOff(String accountNumberNow,int currentInstalmentId,double currentPrincipalNow,Component c){

     
       SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewLoanPrincipalNow",accountNumberNow,currentInstalmentId,currentPrincipalNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
  
    
    long nextDueDateAaP = 0, todayDatezAaP = 0;
    
double existingRemainingInstalmentxapP=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxapP=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingPrincipal=currentLoanPrincipalRNow(accountNumberNow,currentInstalmentId);



double existingLoanPrincipalPaid=loanPrincipalPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAaP= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAaP= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxapP=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxapP=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingPrincipal=totalCurrentRemainingPrincipalNow(accountNumberNow);

double existingTotalLoanPrincipal=totalLoanPrincipalNow(accountNumberNow);


existingRemainingInstalmentxapP-=currentPrincipalNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxapP+=currentPrincipalNow;//instalment paid on the amortization schedule

existingRemainingPrincipal-=currentPrincipalNow;//interest remaining on the amortization schedule

existingLoanPrincipalPaid+=currentPrincipalNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxapP-=currentPrincipalNow;

existingTotalInstalmentPaidxapP+=currentPrincipalNow;

existingTotalRemainingPrincipal-=currentPrincipalNow;

existingTotalLoanPrincipal+=currentPrincipalNow;

try {
//    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr1.txt"),completeStatus);  
//    
//     fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr2.txt"),currentPrincipalNow+""); 
//     
//      fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr3.txt"),amountPaid+"");  
//           fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr4.txt"),totalPrincipalPaid+"");   
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
   
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,PrincipalRemaining=?,  PrincipalPaid=?,instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxapP);
    
    pstool.setObject(2, existingInstalmentPaidxapP);
    
    pstool.setObject(3, existingRemainingPrincipal);
    
    pstool.setObject(4, existingLoanPrincipalPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
     pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalPrincipalRemaining=?, TotalPrincipalPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxapP);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxapP);
    
    pstool2.setObject(3, existingTotalRemainingPrincipal);
    
    pstool2.setObject(4, existingTotalLoanPrincipal);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalPrincipalRemaining=?, TotalPrincipalPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxapP);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxapP);
    
    pstool2.setObject(3, existingTotalRemainingPrincipal);
    
    pstool2.setObject(4, existingTotalLoanPrincipal);
    
    pstool2.setObject(5, "writtenOffloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
        
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxapP<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAaP<todayDatezAaP){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateAaP>=todayDatezAaP){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
     if(!this.isTheNumberOfInstalmentsMoreThanOne(accountNumberNow)){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 
 }

 }
}


public synchronized void updateNewLoanPrincipalNow(String accountNumberNow,int currentInstalmentId,double currentPrincipalNow,Component c){

     
       SwingWorker<Void,Void> updateAllRepaymentsReports=new SwingWorker(){
                            @Override
                            protected Object doInBackground() throws Exception {   
                        updateAllRepaymentsReports("updateNewLoanPrincipalNow",accountNumberNow,currentInstalmentId,currentPrincipalNow,c); 
                                return null;
                            }  };
                     updateAllRepaymentsReports.execute();
  
    
    long nextDueDateAaP = 0, todayDatezAaP = 0;
    
double existingRemainingInstalmentxapP=remainingInstalmentNow(accountNumberNow,currentInstalmentId);


double existingInstalmentPaidxapP=InstalmentPaidNow(accountNumberNow,currentInstalmentId,c);


double existingRemainingPrincipal=currentLoanPrincipalRNow(accountNumberNow,currentInstalmentId);



double existingLoanPrincipalPaid=loanPrincipalPaidNow(accountNumberNow,currentInstalmentId);


String instalmentNextDueDate=instalmentNextDueDateNow(accountNumberNow,currentInstalmentId);

        try {
            nextDueDateAaP= sdfS.parse(instalmentNextDueDate).getTime();
             todayDatezAaP= sdfS.parse(getDBCurrentDate()).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
 


double existingTotalRemainingInstalmentxapP=totalInstalmentRemainingNow(accountNumberNow);

double existingTotalInstalmentPaidxapP=totalInstalmentPaidNow(accountNumberNow);

double existingTotalRemainingPrincipal=totalCurrentRemainingPrincipalNow(accountNumberNow);

double existingTotalLoanPrincipal=totalLoanPrincipalNow(accountNumberNow);


existingRemainingInstalmentxapP-=currentPrincipalNow;//Remaining instalment on the amortization schedule

existingInstalmentPaidxapP+=currentPrincipalNow;//instalment paid on the amortization schedule

existingRemainingPrincipal-=currentPrincipalNow;//interest remaining on the amortization schedule

existingLoanPrincipalPaid+=currentPrincipalNow;//interest remaining on the amortization schedule



existingTotalRemainingInstalmentxapP-=currentPrincipalNow;

existingTotalInstalmentPaidxapP+=currentPrincipalNow;

existingTotalRemainingPrincipal-=currentPrincipalNow;

existingTotalLoanPrincipal+=currentPrincipalNow;

try {
//    fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr1.txt"),completeStatus);  
//    
//     fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr2.txt"),currentPrincipalNow+""); 
//     
//      fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr3.txt"),amountPaid+"");  
//           fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr4.txt"),totalPrincipalPaid+"");   
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
   
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET InstalmentRemaining=?, instalment_paid=?,PrincipalRemaining=?,  PrincipalPaid=?,instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingInstalmentxapP);
    
    pstool.setObject(2, existingInstalmentPaidxapP);
    
    pstool.setObject(3, existingRemainingPrincipal);
    
    pstool.setObject(4, existingLoanPrincipalPaid);
    
     pstool.setObject(5,getDBCurrentDate());//Loan cycle status
    
      pstool.setObject(6, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(7, currentInstalmentId);
    
     pstool.setObject(8, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET balance_due=?, instalments_paid=?,  TotalPrincipalRemaining=?, TotalPrincipalPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxapP);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxapP);
    
    pstool2.setObject(3, existingTotalRemainingPrincipal);
    
    pstool2.setObject(4, existingTotalLoanPrincipal);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET balance_due=?, instalments_paid=?,  TotalPrincipalRemaining=?, TotalPrincipalPaid=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingInstalmentxapP);
    
    pstool2.setObject(2, existingTotalInstalmentPaidxapP);
    
    pstool2.setObject(3, existingTotalRemainingPrincipal);
    
    pstool2.setObject(4, existingTotalLoanPrincipal);
    
    pstool2.setObject(5, "newloan"+accountNumberNow);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
        
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


if(existingRemainingInstalmentxapP<1){
    
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"P");
updateNumberInstalmentStatus(accountNumberNow);
}else{
    
if(nextDueDateAaP<todayDatezAaP){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"NP");
}else if(nextDueDateAaP>=todayDatezAaP){
updateInstalmentStatus(accountNumberNow,currentInstalmentId,"PP");

}

}

 if(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "CompanyType.txt")).equalsIgnoreCase("Money Lender")){
     
     if(!this.isTheNumberOfInstalmentsMoreThanOne(accountNumberNow)){
     
   if(fios.intFileReader(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt")) !=77){
       
 shiftInstalmentDueDate(accountNumberNow,currentInstalmentId);
 
   }
 fios.intFileWriterReplace(fios.createFileName("emailDetails", "sendMail", accountNumberNow+"throughWaive"+currentInstalmentId+".txt"), "771");
 
 }

 }
}


public synchronized void shiftInstalmentDueDate(String accountNumberNow,int currentInstalmentId){

//    fios.stringFileWriter(fios.createFileName("test", "testit", "qtrdyr.txt"),getDBCurrentDate());
//    
//   fios.stringFileWriter(fios.createFileName("test", "testit", "q1trdyr.txt"),fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));
//       
//  fios.stringFileWriter(fios.createFileName("test", "testit", "q2trdyr.txt"),fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
//           
//      
//    fios.stringFileWriter(fios.createFileName("test", "testit", "q3trdyr.txt"),fmt.dateForDatabaseWithFullYearBeginningWithDate(fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()))));
//                  
//    String newInstalmentDate=fmt.forDatabaseWithFullYearBeginningWithDate(fmt.newInstalmentDate(this.instalmentDueDate(accountNumberNow, currentInstalmentId+"")));
//try {
//    
//    Connection cq=loancon.createConnection(); 
//    
//    cq.setAutoCommit(false); 
//     
//    String UpdateQuary = "UPDATE new_loan_appstoreamort SET instalment_due_date=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
//   
//    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
//        
//    pstool.setObject(1, newInstalmentDate);
//    
//      pstool.setObject(2, currentInstalmentId);
//      
//      pstool.setObject(3, "newloan"+accountNumberNow);
//      
//    pstool.execute();
//    }
//    cq.setAutoCommit(true);
//    
//    loancon.closeConnection(cq);
//
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }

}
//FROM new_loan_appstoreamort WHERE master2_id="+"'"+"newloan"+accountNumberNow+"'";"
public synchronized void updateOneInstalmenDueDate(String accountNumber,String theDate){

//  String newInstalmentDate=fmt.forDatabaseWithFullYearBeginningWithDate(fmt.newInstalmentDate(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(theDate)));
//try {
//    
//    Connection cq=loancon.createConnection(); 
//    
//    cq.setAutoCommit(false); 
//    
//    String UpdateQuary = "UPDATE"+" "+"newloan"+accountNumber+" "+"SET instalment_due_date=? WHERE instalment_no=?";//Update the instalment, change status to paid and quote the istalment paid date
//   
//    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
//        
//    pstool.setObject(1, newInstalmentDate);
//    
//      pstool.setObject(2, 1);
//    pstool.execute();
//    }
//    cq.setAutoCommit(true);
//    
//    loancon.closeConnection(cq);
//
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//
//    }


}

public void updateInstalmentStatus(String accountNumberNow,int currentInstalmentId,String loanPaymentStatus){
    switch(loanPaymentStatus){
    case "P":
    updateActuallyPaymentStatus(accountNumberNow,currentInstalmentId,loanPaymentStatus);            
    break;
        
    case "NP": 
        if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesOnArrears.txt"))!=1){//A decision on whther the charges will be on the instalment, principal, interest or the whole loan amount   
        if(fios.intFileReader( fios.createFileName("persistence", "loanArrears", "setChargesOnTheXy.txt"))!=1){
        if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", "setChargesOnArrearsOthersY.txt"))!=1){//Decesion on whether to set charges as either a percentage, fraction or single figure was made
        updateActuallyPaymentStatus(accountNumberNow,currentInstalmentId,loanPaymentStatus);   
        }}}
    break;
    case "PP":         
    updateActuallyPaymentStatus(accountNumberNow,currentInstalmentId,loanPaymentStatus);             
    break;
    }

}

private void updateActuallyPaymentStatus(String accountNumberNow,int currentInstalmentId,String loanPaymentStatus){


try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET instalment_status=?, instalment_paid_date=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, loanPaymentStatus);
    
    pstool.setObject(2,getDBCurrentDate());//Loan cycle status

     pstool.setObject(3, fmt.diffDays(instalmentDueDate(accountNumberNow,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
     
    pstool.setObject(4, currentInstalmentId);
    
    
     pstool.setObject(5, "newloan"+accountNumberNow);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
if(loanPaymentStatus.equalsIgnoreCase("P")){
 fios.deleteFileExistance(fios.createFileName("persistence", "loanArrears", accountNumberNow+"deadlineForCharges"+currentInstalmentId+".txt"));
}




}




public void updateInstalmentStatusWrittenOff(String accountNumberNow){

try {
  
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET instalment_status=?, instalment_paid_date=? AND master2_id=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, "WF");
    
    pstool.setObject(2,getDBCurrentDate());//Loan cycle status
    
     pstool.setObject(3,"newloan"+accountNumberNow);//Loan cycle status
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public synchronized void updateClosedLoanWrittenOff(List closedDetails){
try {
    
    Connection cq=loancon.createConnection(); 

     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, closedDetails.get(1).toString());//Loan cycle status
    
    
    pstool2.setObject(4, "writtenOffloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, closedDetails.get(1).toString());//Loan cycle status
    
    
    pstool2.setObject(4, "writtenOffloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary21f = "UPDATE new_loan_appstoreamort SET master2_id=? WHERE  master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21f)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, "newloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
}


public synchronized void updateClosedLoan(List closedDetails){
try {
    
    Connection cq=loancon.createConnection(); 

     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, closedDetails.get(1).toString());//Loan cycle status
    
    
    pstool2.setObject(4, "newloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, closedDetails.get(1).toString());//Loan cycle status
    
    
    pstool2.setObject(4, "newloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary21f = "UPDATE new_loan_appstoreamort SET master2_id=? WHERE  master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21f)) {
        
    pstool2.setObject(1, closedDetails.get(0).toString());//New loan id
    pstool2.setObject(2, "newloan"+closedDetails.get(2).toString());//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
}


public synchronized void updateWrittenOffLoanStore(String accountNumber){
try {
    
    Connection cq=loancon.createConnection(); 

     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, "writtenOffloan"+accountNumber);//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, "WrittenOff");//Loan cycle status
    
    
    pstool2.setObject(4, "newloan"+accountNumber);//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET loan_id=?,  trn_date=?, loan_cycle_status=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, "writtenOffloan"+accountNumber);//New loan id
    pstool2.setObject(2, this.getDBCurrentDate());//completion date
    pstool2.setObject(3, "WrittenOff");//Loan cycle status
    
    
    pstool2.setObject(4, "newloan"+accountNumber);//old loan id
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }



}
public List<List> getStartingLoanInstalmentForAccrual(String accountNumber){//yyyy-mm-dd

    List <List>currentInstalment=new ArrayList();
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT instalment_no,instalment_due_date FROM"+" "+"newloan"+accountNumber+" "+"WHERE (instalment_due_date<="+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
if(rst3.isBeforeFirst()){
   while(rst3.next()){
     List currentIn=new ArrayList();     
    currentIn.add(rst3.getInt("instalment_no"));   
    
 currentIn.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
 
currentInstalment.add(currentIn);
   }
}else{
 List currentIn=new ArrayList();    
currentIn.add("xx");   
    
 currentIn.add("xx"); 
 currentInstalment.add(currentIn);
 

}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


return currentInstalment;
}


public List getTheInstalmentForApprovalDetails(String accountNumber){
     

    List currentInstalment=new ArrayList();
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT instalment_no,instalment_due_date   FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ((instalment_due_date<="+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")"+" "+"AND OtherOne="+"'"+"NYA"+"'"+")";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
//if(rst3.isBeforeFirst()){
    if (rst3.first()){
        
    currentInstalment.add(rst3.getInt("instalment_no"));   
    
 currentInstalment.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
    }
//}else{
//currentInstalment.add("xx");   
//    
// currentInstalment.add("xx"); 
//
//}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


return currentInstalment;
}


public double getTheRemainingAmountForAccrual(List accrualDetails){
    
 double currentInstalment=0.0;
      
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT AccruedInterest FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accrualDetails.get(0).toString()+"'"+" AND instalment_no="+"'"+accrualDetails.get(1).toString()+"'";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

while (rst3.next()){
        
    currentInstalment=rst3.getDouble("AccruedInterest");   
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


return currentInstalment;

}



public double getTheAmountForAccrual(List accrualDetails){
    
 double currentInstalment=0.0,theAccruedAmount=0.0;
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT interest_amount  FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accrualDetails.get(0).toString()+"'"+" AND  instalment_no="+"'"+accrualDetails.get(1).toString()+"'";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

while (rst3.next()){
        
    currentInstalment=rst3.getDouble("interest_amount");   
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  
theAccruedAmount=currentInstalment/30;

return theAccruedAmount;

}

public synchronized void updateNumberInstalmentStatus(String accountNumber){
    
String remainingInstalments=getTheRemainingInstalments(accountNumber);

try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstore SET remaining_instalments=? WHERE  applicant_account_number=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, remainingInstalments);
    
    pstool.setObject(2, accountNumber);
   
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
      cq.setAutoCommit(false); 
    
    String UpdateQuary1 = "UPDATE new_loan_appstore1 SET remaining_instalments=? WHERE  applicant_account_number=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
        
    pstool.setObject(1, remainingInstalments);
    
    pstool.setObject(2, accountNumber);
   
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public String getTheRemainingInstalments(String thatAccountNumber){

    String theInstalments="";
    
 try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT COUNT(instalment_no) AS intalments FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+thatAccountNumber+"'"+" AND  NOT" +"(instalment_status="+"'P')";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

while (rst3.next()){
        
    theInstalments=rst3.getString("intalments");   
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  
//theAccruedAmount=currentInstalment/30;

return theInstalments;


}

public synchronized void updateLoanScheduleAccrual(List updateDetails){
    

int currentInstalmentId=parseInt(updateDetails.get(1).toString());

    String accountNumber=updateDetails.get(0).toString();
    
double existingRemainingAccruedInterest=currentAccruedInterestNow(accountNumber,parseInt(updateDetails.get(1).toString()));


double interestTobeAccrued=parseDouble(updateDetails.get(2).toString());



 double existingAccruedInterest=getTheRemainingAmountForAccrualNow(accountNumber,parseInt(updateDetails.get(1).toString()));


double existingTotalRemainingAccruedInterest=totalCurrentRemainingAccruedInterestNow(accountNumber);



existingRemainingAccruedInterest+=interestTobeAccrued;//Remaining instalment on the amortization schedule

existingAccruedInterest-=interestTobeAccrued;//instalment paid on the amortization schedule

existingTotalRemainingAccruedInterest+=interestTobeAccrued;//interest remaining on the amortization schedule


try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET AccruedInterestRemaing=?, AccruedInterest=?,OtherOne=?,instalment_paid_variance=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingAccruedInterest);
    
    pstool.setObject(2, existingAccruedInterest);
    
    pstool.setObject(3, updateDetails.get(3).toString());

    pstool.setObject(4, fmt.diffDays(instalmentDueDate(accountNumber,currentInstalmentId+""), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate())));
        
    pstool.setObject(5, currentInstalmentId);
    
    pstool.setObject(6, "newloan"+accountNumber);
    
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET TotalAccruedInterestRemaining=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(2, "newloan"+accountNumber);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
     cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET TotalAccruedInterestRemaining=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingAccruedInterest);
    
    pstool2.setObject(2, "newloan"+accountNumber);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


}

public double getTheRemainingAmountForAccrualNow(String accountNumber,int instalmentNumber){
 double currentInstalment=0.0;
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT AccruedInterest FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instalmentNumber;
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

while (rst3.next()){
        
    currentInstalment=rst3.getDouble("AccruedInterest");   
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


return currentInstalment;

}

public double getTheRemainingAmountForAccumulatedNow(String accountNumber,int instalmentNumber){
 double currentInstalment=0.0;
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT AccumulatedInterest FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND  instalment_no="+instalmentNumber;
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

while (rst3.next()){
        
    currentInstalment=rst3.getDouble("AccumulatedInterest");   
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }


return currentInstalment;

}


public List getTheInstalmentForAccumulatedInterestDetails(String accountNumber,String Signal){
    
  List currentInstalment=new ArrayList();
   
  
  switch(Signal){
      case "Once":
       
          
  if(isTheAccountInArrears(accountNumber)){
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT instalment_no FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND (OtherThree<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
//if(rst3.isBeforeFirst()){
   while (rst3.next()){
       
       if(fios.intFileReader(fios.createFileName("persistence", "loanArrears", accountNumber+"deadlineForCharges"+rst3.getInt("instalment_no")+".txt"))!=7){   
    
           currentInstalment.add(rst3.getInt("instalment_no"));   
    
    fios.intFileWriterReplace(fios.createFileName("persistence", "loanArrears", accountNumber+"deadlineForCharges"+rst3.getInt("instalment_no")+".txt"), "7");
       }
// currentInstalment.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
    }
//}else{
//currentInstalment.add("xx");   
//    
// currentInstalment.add("xx"); 
//
//}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  }else{
  
 currentInstalment.add("9999");
  }
  
  if(currentInstalment.isEmpty()){
      
  currentInstalment.add("9999");
  
  }
  
    break;
    
     case "Recycle":
  if(isTheAccountInArrears(accountNumber)){
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT instalment_no FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND (OtherThree<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
//if(rst3.isBeforeFirst()){
   while (rst3.next()){
        
    currentInstalment.add(rst3.getInt("instalment_no"));   
    
// currentInstalment.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
    }
//}else{
//currentInstalment.add("xx");   
//    
// currentInstalment.add("xx"); 
//
//}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
  }else{
  
 currentInstalment.add("9999");
  }
  
  if(currentInstalment.isEmpty()){
      
  currentInstalment.add("9999");
  
  }
    break;
    }
  

return currentInstalment;


}


public  boolean isTheAccountInArrears(String accountNumber){
 
    int currentInstalment=0; boolean isInArrears=false;
    
  try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT COUNT(instalment_no) AS arrears FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND  (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
//if(rst3.isBeforeFirst()){
   while (rst3.next()){
        
    currentInstalment=rst3.getInt("arrears");   
    
// currentInstalment.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
    }
//}else{
//currentInstalment.add("xx");   
//    
// currentInstalment.add("xx"); 
//
//}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }
if(currentInstalment>0){
isInArrears=true;
}

return isInArrears;


}

public synchronized List detailsOfInterestToAccumulate(List instalmentDatails){
   String date="";double interest=0.0,principal=0.0,remaiPri=0.0,returned=0;
    
List theDetails=new ArrayList();
try {
      
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);   
    
    String query = "SELECT OtherThree,interest_amount, princimpal_amount, PrincipalRemaining FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+instalmentDatails.get(0).toString()+"'"+" AND instalment_no="+instalmentDatails.get(1).toString();
    
    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();
//if(rst3.isBeforeFirst()){
   while (rst3.next()){
        
 date=rst3.getString("OtherThree");  //instalment due date
  interest=rst3.getDouble("interest_amount");
  principal=rst3.getDouble("princimpal_amount");
remaiPri=rst3.getDouble("PrincipalRemaining");

// currentInstalment.add(rst3.getString("instalment_due_date")); 
// fios.stringFileWriter(fios.createFileName("test", "testit", "trdyr.txt"),rst3.getInt("instalment_no")+";"+rst3.getString("instalment_due_date")); 
    }
//}else{
//currentInstalment.add("xx");   
//    
// currentInstalment.add("xx"); 
//
//}
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
returned=((interest/principal)*remaiPri);
theDetails.add(date);
theDetails.add(returned);

//  fios.stringFileWriter(fios.createFileName("test", "testit", instalmentDatails.get(0).toString()+"trdyr.txt"),interest+";"+principal+";"+remaiPri+";"+date);  

return theDetails;

}


public boolean accumulatedLessInterest(String accountNumber,double theInterestInstalment ){
    
boolean accumulatedLess=false;

    double existingRemainingAccumulatedInterest=currentAccumulatedInterestNow(accountNumber,1);
    
    if(existingRemainingAccumulatedInterest<theInterestInstalment){
    
    accumulatedLess=true;
    }
    
return accumulatedLess;
}

public void updateLoanScheduleAccumulated(List scheduleDetails){

int currentInstalmentId=parseInt(scheduleDetails.get(1).toString());

    String accountNumber=scheduleDetails.get(0).toString();
    
double existingRemainingAccumulatedInterest=currentAccumulatedInterestNow(accountNumber,currentInstalmentId);


double interestTobeAccumulated=parseDouble(scheduleDetails.get(2).toString());



 double existingAccumulatedInterest=getTheRemainingAmountForAccumulatedNow(accountNumber,currentInstalmentId);


double existingTotalRemainingAccumulatedInterest=totalCurrentRemainingAccumulatedInterestNow(accountNumber);



existingRemainingAccumulatedInterest+=interestTobeAccumulated;//Remaining instalment on the amortization schedule

existingAccumulatedInterest+=interestTobeAccumulated;//instalment paid on the amortization schedule

existingTotalRemainingAccumulatedInterest+=interestTobeAccumulated;//interest remaining on the amortization schedule


try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET AccumulatedInterestRemaining=?, AccumulatedInterest=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, existingRemainingAccumulatedInterest);
    
    pstool.setObject(2, existingAccumulatedInterest);

    pstool.setObject(3, currentInstalmentId);
    
    pstool.setObject(4, "newloan"+accountNumber);
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
     cq.setAutoCommit(false); 
    
    String UpdateQuary2 = "UPDATE new_loan_appstore SET TotalAccumulatedInterestRemaining=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary2)) {
        
    pstool2.setObject(1, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(2, "newloan"+accountNumber);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
      cq.setAutoCommit(false); 
    
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET TotalAccumulatedInterestRemaining=? WHERE  loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
     
    try (PreparedStatement pstool2 = cq.prepareStatement(UpdateQuary21)) {
        
    pstool2.setObject(1, existingTotalRemainingAccumulatedInterest);
    
    pstool2.setObject(2, "newloan"+accountNumber);
    
    pstool2.execute();
    }
    cq.setAutoCommit(true);
    
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    
    
    
}

public synchronized void createAccumulatedInterest(String accountNumberx,double amount){


String accountNumber=accountNumberx;
int instalmentNumber=1;


    

double accumulatedInterest=theActualAccumulatedInterestNow(accountNumber, instalmentNumber);
double accumulatedInterestRemaining=currentAccumulatedInterestNow(accountNumber, instalmentNumber);
double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalAccumulatedInterestRemaining=totalCurrentRemainingAccumulatedInterestNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

accumulatedInterest+=amount;

accumulatedInterestRemaining+=amount;

instalmentRemaining+=amount;

totalAccumulatedInterestRemaining+=amount;

        totalLoanAmountDue+=amount;
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 

  try {
    Connection cq=loancon.createConnection();   
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE new_loan_appstoreamort SET  AccumulatedInterest=?,AccumulatedInterestRemaining=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, accumulatedInterest);//The accumulated Interest
    pstool.setObject(2,accumulatedInterestRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
    pstool.setObject(5, "newloan"+accountNumber);//trn_id
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}


public synchronized void createInterest(String accountNumberx,double amount,int instalmentNumber){


String accountNumber=accountNumberx;

double Interest=theActualInterestNow(accountNumber, instalmentNumber);

double InterestRemaining=currentInterestRemainNow(accountNumber, instalmentNumber);

double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalInterestRemaining=totalCurrentRemainingInterestNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

Interest+=amount;

InterestRemaining+=amount;

instalmentRemaining+=amount;

totalInterestRemaining+=amount;

        totalLoanAmountDue+=amount;
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 

  try {
    Connection cq=loancon.createConnection();   
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE new_loan_appstoreamort SET  interest_amount=?,InterestRemaing=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, Interest);//The accumulated Interest
    pstool.setObject(2,InterestRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
       pstool.setObject(5, "newloan"+accountNumber);
    pstool.execute();
    }

     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public synchronized void createInterest2(String accountNumberx,double amount,String dueDate,int instalmentNumber,Component c){

//    JOptionPane.showMessageDialog(c, accountNumberx+"; "+amount+";"+dueDate+";"+instalmentNumber);

String accountNumber=accountNumberx;

double Interest=theActualInterestNow(accountNumber, instalmentNumber);

double InterestRemaining=currentInterestRemainNow(accountNumber, instalmentNumber);

double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalInterestRemaining=totalCurrentRemainingInterestNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

double instalmentAmount=this.theInstalmentNow(accountNumber, instalmentNumber);

Interest+=amount;

InterestRemaining+=amount;

instalmentRemaining+=amount;

totalInterestRemaining+=amount;

        totalLoanAmountDue+=amount;
        
        instalmentAmount+=amount;
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 
//JOptionPane.showMessageDialog(c, "Interest="+Interest+"InterestRemaining="+InterestRemaining+"; "+"instalmentRemaining="+instalmentRemaining+"; "+"InterestRemaining"+InterestRemaining+";"+dueDate+";"+instalmentNumber);
  try {
    Connection cq=loancon.createConnection();   
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE new_loan_appstoreamort SET  interest_amount=?,InterestRemaing=?,InstalmentRemaining=?,instalment_amount=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, Interest);//The accumulated Interest
    pstool.setObject(2,InterestRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    
             pstool.setObject(4, instalmentAmount);//InstalmentRemaining
    pstool.setObject(5, instalmentNumber);//trn_id
       pstool.setObject(6, "newloan"+accountNumber);
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary1d = "UPDATE new_loan_appstoreamort SET  instalment_due_date=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1d)) {
    pstool.setObject(1, fmt.forDatabaseWithFullYearBeginningWithDate(dueDate));//The accumulated Interest
    pstool.setObject(2, instalmentNumber);//trn_id
       pstool.setObject(3, "newloan"+accountNumber);
    pstool.execute();
    }
    cq.setAutoCommit(true);  
    
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}


public synchronized void createAccumulatedInterest(String accountNumberx,double amount,int instalmentNumber){


String accountNumber=accountNumberx;

double AccumulatedInterest=theActualAccumulatedInterestNow(accountNumber, instalmentNumber);

double AccumulatedInterestRemaining=currentAccumulatedInterestNow(accountNumber, instalmentNumber);

double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalAccumulatedInterestRemaining=totalCurrentRemainingAccumulatedInterestNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

AccumulatedInterest+=amount;

AccumulatedInterestRemaining+=amount;

instalmentRemaining+=amount;

totalAccumulatedInterestRemaining+=amount;

        totalLoanAmountDue+=amount;
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 

  try {
    Connection cq=loancon.createConnection();   
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE new_loan_appstoreamort SET  AccumulatedInterest=?,AccumulatedInterestRemaining=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, AccumulatedInterest);//The accumulated Interest
    pstool.setObject(2,AccumulatedInterestRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
     pstool.setObject(5, "newloan"+accountNumber);
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public synchronized void createLoanPenalty(String accountNumberx,double amount,int instalmentNumber,Component c){


String accountNumber=accountNumberx;

double loanPenalty=theActualLoanPenaltyNow(accountNumber, instalmentNumber);

double loanPenaltyRemaining=currentLoanPenaltytNow(accountNumber, instalmentNumber,c);

double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalLoanPenaltyRemaining=totalCurrentRemainingLoanPenaltyNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

loanPenalty+=amount;

loanPenaltyRemaining+=amount;

instalmentRemaining+=amount;

totalLoanPenaltyRemaining+=amount;

        totalLoanAmountDue+=amount;
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 

  try {
    Connection cq=loancon.createConnection();   
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE new_loan_appstoreamort SET  LoanPenalty=?,LoanPenaltyRemaining=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, loanPenalty);//The accumulated Interest
    pstool.setObject(2,loanPenaltyRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
        pstool.setObject(5, "newloan"+accountNumber);
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalLoanPenaltyRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalLoanPenaltyRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
     cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalLoanPenaltyRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalLoanPenaltyRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}



public List instalmentsDueForPenalty(String accountNumber, String chargeCycleType,Component c){
    

    List theInstalmentsDetails=new ArrayList();
    
    long instalmentStartDate=1L;

    long today=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(this.getDBCurrentDate()));  //Convert the date of today into millseconds
   
    fios.forceFileExistance(fios.createFileName("persistence", "loanArrears", "deadlineForCharges.txt"));    
  
    String truthhy=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "deadlineForCharges.txt"));

    String[] itsTruth=truthhy.split("[,]", 2);
    
    
    
   switch(chargeCycleType){
    
       case "ChargeOnce":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
 
    String query = "SELECT instalment_no, instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ((instalment_due_date<"+"'"+getDBCurrentDate()+"'"+")"+" "+"AND"+" "+"((instalment_status="+"'"+"PP"+"'"+")"+" OR "+"(instalment_status="+"'"+"NY"+"'"+")))";

    PreparedStatement ps = cq.prepareStatement(query);
    
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
   
        
instalmentStartDate=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_due_date")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", ""));

   if(instalmentStartDate<=today){

theInstalmentsDetails.add(rst3.getString("instalment_no"));

    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    }
  break;
  
       case "RecycleCharges":
           
          try {
              
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String query = "SELECT instalment_no, OtherTwo FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ((OtherTwo<"+"'"+getDBCurrentDate()+"'"+")"+" "+"AND"+" "+"(NOT instalment_status="+"'"+"P"+"'"+"))";

    PreparedStatement ps = cq.prepareStatement(query);
   
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
instalmentStartDate=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("OtherTwo")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", ""));

   if(instalmentStartDate<=today){

theInstalmentsDetails.add(rst3.getString("instalment_no"));

    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
           
           break;
   
       case "RecycleCompoundCharges":
           
               
          try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT instalment_no, OtherTwo FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND ((OtherTwo<"+"'"+getDBCurrentDate()+"'"+")"+" "+"AND"+" "+"(NOT instalment_status="+"'"+"P"+"'"+"))";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
instalmentStartDate=fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("OtherTwo")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", ""));

   if(instalmentStartDate<=today){

theInstalmentsDetails.add(rst3.getString("instalment_no"));

    }     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
       
           
           
           
           break;
   
   }
if(theInstalmentsDetails.isEmpty()){
theInstalmentsDetails.add("9999");
}

//JOptionPane.showMessageDialog(c, theInstalmentsDetails.get(0));
return theInstalmentsDetails;


}


public synchronized String theInstalmentStartDate(String accountNumber, String chargeCycleType,String instamentNumber){

String instalmentNextDueDate="";
    
 

    
    fios.forceFileExistance(fios.createFileName("persistence", "loanArrears", "deadlineForCharges.txt"));    
  String truthhy=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "deadlineForCharges.txt"));

    String[] itsTruth=truthhy.split("[,]", 2);
    
    
    
   switch(chargeCycleType){
    
       case "ChargeOnce":
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instamentNumber;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
instalmentNextDueDate=fmt.dateConverterForDatabase(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("instalment_due_date")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", "")));  

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
 

    }
  break;
  
       case "RecycleCharges":
           
          try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT OtherTwo FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instamentNumber;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
instalmentNextDueDate=fmt.dateConverterForDatabase(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("OtherTwo")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", "")));

     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
           
           break;
   
       case "RecycleCompoundCharges":
           
               
            try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT OtherTwo FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND instalment_no="+instamentNumber;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
instalmentNextDueDate=fmt.dateConverterForDatabase(fmt.convertTimeToMillseconds(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("OtherTwo")))+fmt.convertMillsAsNT(itsTruth[0], itsTruth[1].replaceAll(",", "")));

     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
       
           
           
           
           break;
   
   }


return instalmentNextDueDate;


}

public synchronized  void createNewChargeToRecover(List finalUpdate){
    
 //finalUpdate.add(futherDetails.get(1).toString());//Instalment Number:0  
        // finalUpdate.add(futherDetails.get(0).toString());//Account number:1
        // finalUpdate.add(futherDetails.get(4).toString());//Recover start date:3 YYYY-MM-DD:2
        // finalUpdate.add(instalmentNextDueDate);//Recover nextDue date:4 YYYY-MM-DD:3
        // finalUpdate.add(instalmentEndDate);//Recover end date:5 YYYY-MM-DD:4
       //  finalUpdate.add("30");//Recover Number of Instalments:5
       //  finalUpdate.add("30");//Recover Remaining Instalments:6    
       //  finalUpdate.add("0");//Recovered Instalments:7  
       //  finalUpdate.add(instalmentNextDueDate);//Instalment nextDue date:4 YYYY-MM-DD:8
       //   finalUpdate.add(futherDetails.get(2).toString());//Recover charge:9
       //    finalUpdate.add(futherDetails.get(2).toString());//Recover charge remaining:10
        //   finalUpdate.add("0.0");//Recover charge recovered:11
        //   finalUpdate.add("NCO");//Recover status1:12//NCO-Not yet Recovered
        //   finalUpdate.add(futherDetails.get(5).toString());//Recover method:13
        //    finalUpdate.add(instalmentSize);//Recover instalment size:14

try {
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO loanpenaltyrecoverstore VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    ps.setObject(1, null);  //     TrnId// Transaction Id:1
    
    ps.setObject(2, this.getDBCurrentDate());// TrnDate//The transaction date
    
    ps.setObject(3, finalUpdate.get(0)); // InstalmentNumber//Instalment Number:0 
    
    ps.setObject(4, finalUpdate.get(1));//  AccountNumber////Account number:1
    
    ps.setObject(5,finalUpdate.get(2));// RecoverStartDate   ////Recover start date:3 YYYY-MM-DD:2
    
    ps.setObject(6, finalUpdate.get(3));//// RecoverNextDueDate//Recover nextDue date:4 YYYY-MM-DD:3
    
    ps.setObject(7, finalUpdate.get(4));//  RecoverEndDate////Recover end date:5 YYYY-MM-DD:4
    
    ps.setObject(8, finalUpdate.get(5)); //  RecoverNumberInstalments//Recover Number of Instalments:5
    
    ps.setObject(9, finalUpdate.get(6)); // RecoverRemainingInstalments//Recover Remaining Instalments:6   
    
    ps.setObject(10,finalUpdate.get(7)); // RecoveredNumberInstalments//Recovered Instalments:7  
      
    ps.setObject(11, finalUpdate.get(8)); //  InstalmentNextDueDate//Instalment nextDue date:4 YYYY-MM-DD:8
    
    ps.setObject(12, finalUpdate.get(9));  // RecoverCharge //Recover charge:9
    
    ps.setObject(13, finalUpdate.get(10)); // RecoverChargeRemaining//Recover charge remaining:10
    
    ps.setObject(14,finalUpdate.get(11)); // ChargeRecovered//Recover charge recovered:11
    
    ps.setObject(15, finalUpdate.get(12));  // RecoverStatus1//Recover status1:12//NCO-Not yet Recovered
    
    ps.setObject(16, "NCO"); // RecoverStatus2
    
    ps.setObject(17,"NCO"); // RecoverStatus3
    
    
    ps.setObject(18, finalUpdate.get(13));  // RecoveryMethod//Recover method:13
    
    ps.setObject(19,finalUpdate.get(14));  // RecoveryInstalmentSize//Recover instalment size:14
    
    ps.setObject(20, "NA"); // OtherThree 
    
    ps.setObject(21,"NA");// OtherFour 
    
    ps.setObject(22, "NA"); // OtherFive
    
    
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }






}

public synchronized  void exclusiveUpdateOfLoan(List finalUpdate){
    
//  finalUpdateOther.add(futherDetails.get(0).toString());//Account number:0
          //      finalUpdateOther.add(futherDetails.get(1).toString());//Instalment Number:1
          //       finalUpdateOther.add("NP");//Instalment Status:2
   
try {
    
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET instalment_status=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
   
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
        
    pstool.setObject(1, finalUpdate.get(2).toString());
    
    pstool.setObject(2, finalUpdate.get(1).toString());
    
     pstool.setObject(2, finalUpdate.get(0).toString());
    
    pstool.execute();
    }
    cq.setAutoCommit(true);
    
    
    loancon.closeConnection(cq);

    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public List getTheLoansOutstandingChargeableIds(Component c){
    
List theIds=new ArrayList();
 try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT TrnId FROM loanpenaltyrecoverstore WHERE RecoverStatus1="+"'"+"NCO"+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
theIds.add(rst3.getInt("TrnId"));

     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
return theIds;

}


public synchronized List getThePenaltyChargeDetails(String cId,Component c){

List details=new ArrayList();
 try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT InstalmentNumber, AccountNumber,RecoverNextDueDate,  RecoverEndDate,RecoverNumberInstalments, RecoverRemainingInstalments, RecoverCharge, RecoverChargeRemaining, RecoveryMethod, RecoveryInstalmentSize FROM loanpenaltyrecoverstore WHERE TrnId="+cId;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0
details.add(rst3.getString("AccountNumber"));//Account Number:1
  details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
details.add(rst3.getInt("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
details.add(rst3.getInt("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
details.add(rst3.getDouble("RecoverCharge"));//Recover Charge Amount:6
details.add(rst3.getDouble("RecoverChargeRemaining"));//Recover remaining charge Amount:7
details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8
details.add(rst3.getDouble("RecoveryInstalmentSize"));//Recover Instalment size used:9
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
// 
// JOptionPane.showMessageDialog(c, "InstalmentNumber="+details.get(0).toString());
//  JOptionPane.showMessageDialog(c, "account Number="+details.get(1).toString());
//      JOptionPane.showMessageDialog(c, "account Name="+dbq.AccountName(details.get(1).toString()));
//       JOptionPane.showMessageDialog(c, "RecoverNextDueDate="+details.get(2).toString());
//        JOptionPane.showMessageDialog(c, "RecoverEndDate="+details.get(3).toString());
//         JOptionPane.showMessageDialog(c, "RecoverNumberInstalments="+details.get(4).toString());
//          JOptionPane.showMessageDialog(c, "RecoverRemainingInstalments="+details.get(5).toString());
//           JOptionPane.showMessageDialog(c, "RecoverCharge="+details.get(6).toString());
//            JOptionPane.showMessageDialog(c, "RecoverChargeRemaining="+details.get(7).toString());
//            JOptionPane.showMessageDialog(c, "RecoveryMethode="+details.get(8).toString());
//            JOptionPane.showMessageDialog(c, "RecoveryInstalmentSize="+details.get(9).toString());
return details;


}


public synchronized void updateLoanScheduleLoanPenalty(List updateDetails,Component c){
//JOptionPane.showMessageDialog(c, updateDetails.size()+"");
//     List loanPenaltyDetails=new ArrayList();
//        loanPenaltyDetails.add(Id);//Instalment Id:0
//        loanPenaltyDetails.add(theInstalmentNumber);//instalment number:1
//        loanPenaltyDetails.add(accountNumber);//Account number:2
//        loanPenaltyDetails.add(theFinalDetails.get(2).toString());//instalment due date:3
//        loanPenaltyDetails.add( theFinalDetails.get(3).toString());//Instalment End date:4
//        loanPenaltyDetails.add(theFinalDetails.get(4).toString());//Recover Nnumber of Instalments:5
//        loanPenaltyDetails.add(theFinalDetails.get(5).toString());//Recover Remaining instalments:5RecoverCharge:6
//        loanPenaltyDetails.add(theFinalDetails.get(6).toString());//Recover Charge Amount:7
//        loanPenaltyDetails.add(theFinalDetails.get(7).toString());//Recover remaining charge Amount:8
//        loanPenaltyDetails.add(theFinalDetails.get(9).toString());//Recover Instalment size used:9
   
//    JOptionPane.showMessageDialog(c, "updateDetails.get(0).toString()"+":"+updateDetails.get(0).toString()+"\n"+"updateDetails.get(1).toString()"+":"+updateDetails.get(1).toString()+"\n"+"updateDetails.get(2).toString()"+":"+updateDetails.get(2).toString()+"\n"+"updateDetails.get(3).toString()"+":"+updateDetails.get(3).toString()+"\n"+"updateDetails.get(4).toString()"+":"+updateDetails.get(4).toString()+"\n"+"updateDetails.get(5).toString()"+":"+updateDetails.get(5).toString()+"\n"+"\n"+"updateDetails.get(6).toString()"+":"+updateDetails.get(6).toString()+"\n"+"\n"+"updateDetails.get(7).toString()"+":"+updateDetails.get(7).toString()+"\n"+"\n"+"updateDetails.get(8).toString()"+":"+updateDetails.get(8).toString());
    
    
    
                         String instalmentStartDate="",intalmentNextDueDate="";
                         
              String accountNumber=updateDetails.get(2).toString();
              
 instalmentStartDate=fmt.fromDatabaseWithDashSeperatorBeginningWithYear(updateDetails.get(3).toString());//dd/mm/yyyy

 try {
        intalmentNextDueDate=sdf.format(fmt.getExDateIncreamented(sdf.parse(instalmentStartDate), this.reductionValue(accountNumber,c)));
        } catch (ParseException ex) {
        Logger.getLogger(PostingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
 
 

int instalmentId=parseInt(updateDetails.get(0).toString());
//    JOptionPane.showMessageDialog(c, "instalmentId=="+instalmentId);
int instalmentNumber=parseInt(updateDetails.get(1).toString());
// JOptionPane.showMessageDialog(c, "instalmentNumber=="+instalmentNumber);
int recoverPenaltyRemainingInstalments=parseInt(updateDetails.get(6).toString())-1;
// JOptionPane.showMessageDialog(c, "recoverPenaltyRemainingInstalments=="+recoverPenaltyRemainingInstalments);
int recoveredInstalments=parseInt(updateDetails.get(5).toString())-recoverPenaltyRemainingInstalments;
// JOptionPane.showMessageDialog(c, "recoveredInstalments=="+recoveredInstalments);
double loanPenalty=theActualLoanPenaltyNow(accountNumber, instalmentNumber);
// JOptionPane.showMessageDialog(c, "loanPenalty=="+loanPenalty);
double loanPenaltyRemaining=currentLoanPenaltyNow(accountNumber, instalmentNumber);
// JOptionPane.showMessageDialog(c, "loanPenaltyRemaining=="+loanPenaltyRemaining);
double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);
// JOptionPane.showMessageDialog(c, "instalmentRemaining=="+instalmentRemaining);
double totalLoanPenaltyRemain=totalCurrentRemainingLoanPenaltyNow(accountNumber);
// JOptionPane.showMessageDialog(c, "totalLoanPenaltyRemain=="+totalLoanPenaltyRemain);
double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);
// JOptionPane.showMessageDialog(c, "totalLoanAmountDue=="+totalLoanAmountDue);
loanPenalty+=parseDouble(updateDetails.get(7).toString());
// JOptionPane.showMessageDialog(c, "loanPenalty=="+loanPenalty);
loanPenaltyRemaining+=parseDouble(updateDetails.get(7).toString());
// JOptionPane.showMessageDialog(c, "loanPenaltyRemaining=="+loanPenaltyRemaining);
instalmentRemaining+=parseDouble(updateDetails.get(7).toString());
// JOptionPane.showMessageDialog(c, "instalmentRemaining=="+instalmentRemaining);
totalLoanPenaltyRemain+=parseDouble(updateDetails.get(7).toString());
// JOptionPane.showMessageDialog(c, "totalLoanPenaltyRemain=="+totalLoanPenaltyRemain);
        totalLoanAmountDue+=parseDouble(updateDetails.get(7).toString());
//     JOptionPane.showMessageDialog(c, "totalLoanAmountDue=="+totalLoanAmountDue);
         
  try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE"+" "+"loanpenaltyrecoverstore"+" "+"SET RecoverNextDueDate=?,RecoverRemainingInstalments=?, RecoveredNumberInstalments=?,RecoverChargeRemaining=?,RecoverStatus1=? WHERE TrnId=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, updateDetails.get(3).toString());//RecoverNextDueDate
    pstool.setObject(2, recoverPenaltyRemainingInstalments);//RecoverRemainingInstalments
    pstool.setObject(3, recoveredInstalments);//RecoveredNumberInstalments
    pstool.setObject(4, updateDetails.get(8).toString());//RecoverChargeRemaining
    pstool.setObject(5, updateDetails.get(10).toString());//RecoverStatus1
    pstool.setObject(6, instalmentId);//TrnId
     pstool.execute();
    }
    cq.setAutoCommit(true); 
     
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE  new_loan_appstoreamort SET LoanPenalty=?,LoanPenaltyRemaining=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, loanPenalty);//LoanPenalty
    pstool.setObject(2,loanPenaltyRemaining);//LoanPenaltyRemaining
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
     pstool.setObject(5, "newloan"+accountNumber);//trn_id
    
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalLoanPenaltyRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalLoanPenaltyRemain);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
    
      cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalLoanPenaltyRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalLoanPenaltyRemain);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}


public String reductionValue(String accountNumber,Component c){
String theCharge="";
  String accrualType=fios.stringFileReader(fios.createFileName("persistence", "loanArrears", "RecycleChargesDays.txt"));
  String tenureType=tenureType(accountNumber,c);

        switch(tenureType){
    case "DAYS":
    switch(accrualType){
    case "Daily":
       theCharge=  "1";
        break;
        case "Weekily":
           theCharge=  "1";  
            break;
            
            case "Fortnightly":
           theCharge=  "1";
            break;
                
        case "Monthly":
         theCharge=  "1";
            break;
        case "Quaterly":
              theCharge=  "1";
            
            break;
            
             case "Half Yearly":
            
                theCharge=  "1";
            break;
            
        case "Annually": 
                theCharge=  "1";
            break;
    case "Biennially": 
           theCharge=  "1"; 
            break;
    }
        break;
     case "WEEKS":
       switch(accrualType){
    case "Daily":
        theCharge= "1"; 
        break;
        case "Weekily":
             theCharge= "7";
            break;
            
            case "Fortnightly":
               theCharge= "7";
            break;
                
        case "Monthly":
              theCharge= "7";
            break;
        case "Quaterly":
             theCharge= "7";
            
            break;
            
             case "Half Yearly":
           theCharge= "7";
            
            break;
            
        case "Annually": 
          theCharge= "7";
            break;
    case "Biennially": 
             theCharge= "7";
            break;
    }
        break;
         case "FORTNIGHTS":
    switch(accrualType){
    case "Daily":
       theCharge= "1"; 
        break;
        case "Weekily":
            theCharge= "7"; 
            break;
            
            case "Fortnightly":
             theCharge= "15";
            break;
                
        case "Monthly":
            theCharge= "15";  
            break;
        case "Quaterly":
             theCharge= "15";  
            
            break;
            
             case "Half Yearly":
               theCharge= "15"; 
            
            break;
            
        case "Annually": 
             theCharge= "15"; 
            break;
    case "Biennially": 
         theCharge= "15";
            break;
    }
        break;
         case "MONTHS":
   switch(accrualType){
    case "Daily":
       theCharge= "1";
        break;
        case "Weekily":
             theCharge= "7";
            break;
            
            case "Fortnightly":
              theCharge= "15";
            break;
                
        case "Monthly":
              theCharge= "30";
            break;
        case "Quaterly":
              theCharge= "30";
            
            break;
            
             case "Half Yearly":
              theCharge= "30"; 
            
            break;
            
        case "Annually": 
              theCharge= "30";
            break;
    case "Biennially": 
            theCharge= "30"; 
            break;
    }
        break;
         case "QUARTERS":
switch(accrualType){
    case "Daily":
       theCharge= "1";
        break;
        case "Weekily":
             theCharge= "7";
            break;
            
            case "Fortnightly":
                theCharge= "15";
            break;
                
        case "Monthly":
           theCharge= "30";
            break;
        case "Quaterly":
            
              theCharge= "90";
            break;
            
             case "Half Yearly":
            
                 theCharge= "90";
            break;
            
        case "Annually": 
                 theCharge= "90";
            break;
    case "Biennially": 
            theCharge= "90";
            break;
    }
        break;
         case "HALF YEARS":
switch(accrualType){
    case "Daily":
        theCharge=  "1";
        break;
        case "Weekily":
         theCharge=  "7";   
            break;
            
            case "Fortnightly":
            theCharge= "15";
            break;
                
        case "Monthly":
           theCharge=  "30";
            break;
        case "Quaterly":
            
            theCharge=  "90";
            break;
            
             case "Half Yearly":
            
            theCharge=  "180";
            break;
            
        case "Annually": 
            theCharge=  "180";
            break;
    case "Biennially": 
            theCharge=  "180";
            break;
    }
        break;
     case "YEARS":
switch(accrualType){
    case "Daily":
         theCharge= "1";   
        break;
        case "Weekily":
                theCharge= "7";
            break;
            
            case "Fortnightly":
                theCharge=  "15";
            break;
                
        case "Monthly":
                theCharge= "30";
            break;
        case "Quaterly":
            
                theCharge=  "90";
            break;
            
             case "Half Yearly":
                theCharge= "180";
            
            break;
            
        case "Annually": 
                theCharge= "360";
            break;
    case "Biennially": 
                theCharge= "360";
            break;
    }
           
        break;
         case "BIENNIALS":
switch(accrualType){
    case "Daily":
           theCharge= "1";
        break;
        case "Weekily":
                theCharge= "7";
            break;
            
            case "Fortnightly":
                theCharge= "15";
            break;
                
        case "Monthly":
                theCharge= "30";
            break;
        case "Quaterly":
                theCharge= "90";
            
            break;
            
             case "Half Yearly":
                theCharge= "180";
            
            break;
            
        case "Annually": 
                theCharge= "360";
            break;
    case "Biennially": 
                theCharge= "720";
            break;
    }
         
        break;
        }
    

return theCharge;

}
public synchronized void accrueInsteresForAccumulation(List accrueAccumulatedInterestDetails){//The process for preparing the accumulated interest to be recovered ends here

//////    accrualDetailsFinal.add(intalmentNumber);//Instalment Number:0
//////     accrualDetailsFinal.add(accountNumber);//Account Number:1
//////     accrualDetailsFinal.add(interestToAccumulateDetails.get(1).toString());//Accumulated interest for the instalment:2   
//////   accrualDetailsFinal.add(instalmentStartDate);//instalment recovery start date:3   
//////    accrualDetailsFinal.add(instalmentNextDueDate);//instalment next due date:4    
//////   accrualDetailsFinal.add(instalmentEndDate);//instalment recovery end date:5
//////   accrualDetailsFinal.add(recoverNumberInstalments);//Total number of instalments to be recovered:6
//////  accrualDetailsFinal.add(instalmentSize);//instalment size:7
//////  accrualDetailsFinal.add("Accrual");//instalment recovery method:8
try {
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO accumulatedinterestrecoverstore VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    
    ps.setObject(1, null); // TrnId:1// Transaction Id:1
    
    ps.setObject(2, this.getDBCurrentDate());// TrnDate:2   //The transaction date
    
    ps.setObject(3, accrueAccumulatedInterestDetails.get(0)); // InstalmentNumber:3   //Instalment Number:0 
    
    ps.setObject(4, accrueAccumulatedInterestDetails.get(1));// AccountNumber:4  ////Account number:1
    
    ps.setObject(5,accrueAccumulatedInterestDetails.get(3));// RecoverStartDate:5  //instalment recovery start date:3 
    
    ps.setObject(6, accrueAccumulatedInterestDetails.get(4));// RecoverNextDueDate:6 //instalment next due date:4 
    
    ps.setObject(7, accrueAccumulatedInterestDetails.get(5));//  RecoverEndDate:7//instalment recovery end date:5
    
    ps.setObject(8, accrueAccumulatedInterestDetails.get(6)); // // RecoverNumberInstalments:8 //Total number of instalments to be recovered:6
    
    ps.setObject(9,accrueAccumulatedInterestDetails.get(6)); // RecoverRemainingInstalments:9 //Total number of instalments to be recovered:6(same) 
    
    ps.setObject(10,"0"); // RecoveredNumberInstalment:10    
      
    ps.setObject(11, "1970-01-01"); // InstalmentNextDueDate:11  (Redandant)
    
    ps.setObject(12, accrueAccumulatedInterestDetails.get(2));  // TotalAccumulatedInterest:12  //Accumulated interest for the instalment:2    
    
    ps.setObject(13, accrueAccumulatedInterestDetails.get(2)); // AccumulatedInterestRemaining:13 //Accumulated interest for the instalment:2  (same at the start)
    
    ps.setObject(14,"0.0"); // AccumulatedInterestRecovered:14    (Zero at the start)
    
    ps.setObject(15, accrueAccumulatedInterestDetails.get(7)); // RecoveryInstalmentSize:15  //instalment size:7 
    
    ps.setObject(16, accrueAccumulatedInterestDetails.get(8)); // RecoveryMethod:16  //instalment recovery method:8  
    
    ps.setObject(17,"NCO"); // RecoverStatus1:17 
    
    ps.setObject(18, "NCO");  // RecoverStatus2:18      

    
    ps.setObject(19,"NCO");  // RecoverStatus3:19      

    
    ps.setObject(20, "NA");// OtherThree:20    

    
    ps.setObject(21,"NA");// OtherFour:21       

    
    ps.setObject(22, "NA"); // OtherFive:22
    
    
    ps.execute();
    cq.setAutoCommit(true); 
     cq.setAutoCommit(false); 
    
    String UpdateQuary = "UPDATE new_loan_appstoreamort SET OtherThree=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to pai
    
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, accrueAccumulatedInterestDetails.get(9));//Instalment Next Due Date
    pstool.setObject(2, accrueAccumulatedInterestDetails.get(0));//remaining instalments
     pstool.setObject(3, "newloan"+accrueAccumulatedInterestDetails.get(1));//remaining instalments
    
    pstool.execute();
    }
     
     cq.setAutoCommit(true); 
    
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public List getTheAccumulatableInterestIds(){
List theIds=new ArrayList();
 try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT TrnId FROM accumulatedinterestrecoverstore WHERE RecoverStatus1="+"'"+"NCO"+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
theIds.add(rst3.getInt("TrnId"));

     

    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
return theIds;

}

public synchronized List getTheAccumulatableInterestDetails(String aId){

List details=new ArrayList();
 try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String query = "SELECT InstalmentNumber, AccountNumber,RecoverNextDueDate,  RecoverEndDate,RecoverNumberInstalments, RecoverRemainingInstalments,  TotalAccumulatedInterest,  AccumulatedInterestRemaining,  RecoveryMethod,  RecoveryInstalmentSize FROM accumulatedinterestrecoverstore WHERE TrnId="+aId;

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while(rst3.next()){
        
details.add(rst3.getString("InstalmentNumber"));//Instalment Number:0
details.add(rst3.getString("AccountNumber"));//Account Number:1
  details.add(rst3.getString("RecoverNextDueDate"));//Recover Next Due Date:2//YYYY-MM-DD   
details.add(rst3.getString("RecoverEndDate"));//Recover End Date:3//YYYY-MM-DD   
details.add(rst3.getInt("RecoverNumberInstalments"));//Recover Nnumber of Instalments:4
details.add(rst3.getInt("RecoverRemainingInstalments"));//Recover Remaining instalments:5RecoverCharge
details.add(rst3.getDouble("TotalAccumulatedInterest"));//Recover Accumulated Interest Amount:6
details.add(rst3.getDouble("AccumulatedInterestRemaining"));//Recover remaining Accumulated Interest Amount:7
details.add(rst3.getString("RecoveryMethod"));//Recover Method used:8
details.add(rst3.getDouble("RecoveryInstalmentSize"));//Recover Instalment size used:9
    }

    cq.setAutoCommit(true);

    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
           
return details;


}


public synchronized void updateLoanScheduleAccumulatedInterest(List updateDetails){
    
//detailsForAccumulatedSchedule.add(postingDetails.get(0).toString()); //Instalment Id:0
//
//        detailsForAccumulatedSchedule.add(postingDetails.get(1).toString());//instalment number:1
//
//        detailsForAccumulatedSchedule.add(accountNumber);//Account number:2
//
//        detailsForAccumulatedSchedule.add(postingDetails.get(3).toString());//Next instalment due date:3
//        
//          detailsForAccumulatedSchedule.add(postingDetails.get(4).toString());//Recover Instalments:4
//
//        detailsForAccumulatedSchedule.add(postingDetails.get(5).toString());//Recover Remaining instalments:5
//           
//          detailsForAccumulatedSchedule.add(postingDetails.get(7).toString());///Recovered instalment size:6
//          
//          detailsForAccumulatedSchedule.add(postingDetails.get(8).toString());//Accrual status:7
//          
//           detailsForAccumulatedSchedule.add(postingDetails.get(9).toString());//Recovered Times:8

String accountNumber=updateDetails.get(2).toString();
int instalmentId=parseInt(updateDetails.get(0).toString());
int instalmentNumber=parseInt(updateDetails.get(1).toString());
int recoveredInstalments=parseInt(updateDetails.get(9).toString());

    

double accumulatedInterest=theActualAccumulatedInterestNow(accountNumber, instalmentNumber);
double accumulatedInterestRemaining=currentAccumulatedInterestNow(accountNumber, instalmentNumber);
double instalmentRemaining=remainingInstalmentNow(accountNumber, instalmentNumber);

double totalAccumulatedInterestRemaining=totalCurrentRemainingAccumulatedInterestNow(accountNumber);

double totalLoanAmountDue=totalInstalmentRemainingNow(accountNumber);

accumulatedInterest+=parseDouble(updateDetails.get(7).toString());

accumulatedInterestRemaining+=parseDouble(updateDetails.get(7).toString());

instalmentRemaining+=parseDouble(updateDetails.get(7).toString());

totalAccumulatedInterestRemaining+=parseDouble(updateDetails.get(7).toString());

        totalLoanAmountDue+=parseDouble(updateDetails.get(7).toString());
        
//  fios.stringFileWriter(fios.createFileName("test", "testit", updateDetails.get(2).toString()+"trdyr.txt"),"postingDetails.get(0).toString():"+updateDetails.get(0).toString()+";"+"postingDetails.get(1).toString():"+updateDetails.get(1).toString()+";"+"postingDetails.get(2).toString():"+updateDetails.get(2).toString()+";"+"postingDetails.get(3).toString():"+updateDetails.get(3).toString()+";"+"postingDetails.get(4).toString():"+updateDetails.get(4).toString()+";"+"postingDetails.get(5).toString():"+updateDetails.get(5).toString()+";"+"postingDetails.get(6).toString():"+updateDetails.get(6).toString()+";"+"postingDetails.get(7).toString():"+updateDetails.get(7).toString()+";"+"postingDetails.get(8).toString():"+updateDetails.get(8).toString()+";");                 

  try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false); 
    String UpdateQuary = "UPDATE"+" "+" accumulatedinterestrecoverstore"+" "+"SET RecoverNextDueDate=?,RecoverRemainingInstalments=?, RecoveredNumberInstalments=?,AccumulatedInterestRemaining=?,RecoverStatus1=? WHERE TrnId=?";
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary)) {
    pstool.setObject(1, updateDetails.get(3).toString());//RecoverNextDueDate
    pstool.setObject(2, updateDetails.get(5).toString());//RecoverRemainingInstalments
    pstool.setObject(3, recoveredInstalments);//RecoveredNumberInstalments
    pstool.setObject(4, updateDetails.get(6).toString());//RecoverChargeRemaining
    pstool.setObject(5, updateDetails.get(8).toString());//RecoverStatus1
    pstool.setObject(6, instalmentId);//TrnId
     pstool.execute();
    }
    cq.setAutoCommit(true); 
     
    cq.setAutoCommit(false); 
    String UpdateQuary1 = "UPDATE  new_loan_appstoreamort SET  AccumulatedInterest=?,AccumulatedInterestRemaining=?,InstalmentRemaining=? WHERE instalment_no=? AND master2_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary1)) {
    pstool.setObject(1, accumulatedInterest);//The accumulated Interest
    pstool.setObject(2,accumulatedInterestRemaining);//The remaining accumulated interest
    pstool.setObject(3, instalmentRemaining);//InstalmentRemaining
    pstool.setObject(4, instalmentNumber);//trn_id
    pstool.setObject(5, "newloan"+accountNumber);//trn_id
    pstool.execute();
    }
    cq.setAutoCommit(true);   
    
     cq.setAutoCommit(false); 
    String UpdateQuary2 = "UPDATE new_loan_appstore SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary2)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    
    cq.setAutoCommit(false); 
    String UpdateQuary21 = "UPDATE new_loan_appstore1 SET  TotalAccumulatedInterestRemaining=?, balance_due=? WHERE loan_id=?";//Update the instalment, change status to paid and quote the istalment paid date
    try (PreparedStatement pstool = cq.prepareStatement(UpdateQuary21)) {
    pstool.setObject(1, totalAccumulatedInterestRemaining);//transaction date
    pstool.setObject(2, totalLoanAmountDue);//remaining instalments
    pstool.setObject(3, "newloan"+accountNumber);//remaining balance
    pstool.execute();
    }
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

}

public synchronized List getDeatailsForProvisionWriteOff(String accountNumber){
    
List theDetailsForWriteOffProvision =new ArrayList();Long numberOfDaysInArreas=1L;

try {
           Connection cq=loancon.createConnection();
          cq.setAutoCommit(false);
     
          
          
   String query = "SELECT instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT" +"(instalment_status="+"'P')";
          
                                                          
          
          PreparedStatement psxmt = cq.prepareStatement(query);
          ResultSet rsxmt = psxmt.executeQuery();
        if(rsxmt.first()){
    Long diff=fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("instalment_due_date")), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));         
       
    if(diff<0){
      numberOfDaysInArreas =Math.abs(diff);
       }else if(diff>0){
        numberOfDaysInArreas =0L;
       }   
    
    
theDetailsForWriteOffProvision.add(numberOfDaysInArreas);
theDetailsForWriteOffProvision.add(remainingTotalPrincipal(accountNumber));
theDetailsForWriteOffProvision.add(accountNumber);
          }
   
  
        rsxmt.close();
        psxmt.close();
        cq.setAutoCommit(true);
         loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }





return theDetailsForWriteOffProvision;

}

public synchronized void captureProvisioned(List provisionedDetails){
// provisionDetails.add(dbq.AccountName(theDetaills.get(2).toString()));//account Name:0
//        provisionDetails.add(theDetaills.get(2).toString());//account number:1
//        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionLower1.txt")));//range provisioned:2
//        provisionDetails.add(fios.stringFileReader(fios.createFileName("persistence", "loanSetUp", "provisionUpper1.txt")));//percentage provisioned:3
//        provisionDetails.add(theDetaills.get(0).toString());//Days in arrears:4
//         provisionDetails.add(theDetaills.get(1).toString());//outstanding princimpal:5
//        provisionDetails.add(theProvisionedInstalment);//amount provisioned:6

//fios.stringFileWriter(fios.createFileName("test", "testit", "ffff"+provisionedDetails.get(0).toString()+"trdyr.txt"),provisionedDetails.get(1).toString()+";"+provisionedDetails.get(2).toString()+";"+provisionedDetails.get(3).toString()+";"+provisionedDetails.get(4).toString());
try {
    Connection cq=loancon.createConnection(); 
    
    cq.setAutoCommit(false);
    PreparedStatement ps =  cq.prepareStatement("INSERT INTO provisionwriteofftracker VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
    
    ps.setObject(1, null);  //     TrnId// Transaction Id:1
    
    ps.setObject(2, this.getDBCurrentDate());// TrnDate//The transaction date
    
    ps.setObject(3, provisionedDetails.get(0)); // account_name//Account name:0 
    
    ps.setObject(4, provisionedDetails.get(1));//  account_number////Account number:1
    
    ps.setObject(5,provisionedDetails.get(2));// range_provisioned   ////Range Provisioned:2
    
    ps.setObject(6, provisionedDetails.get(3));//// percentage_provisioned//Percentage provisioned:3
    
    ps.setObject(7, provisionedDetails.get(4));//  days_in_arrears////Days in arrears:4
    
    ps.setObject(8, provisionedDetails.get(5)); //  princimpal_in_arrears//outstanding princimpal:5
    
    ps.setObject(9, provisionedDetails.get(6)); // amount_provisioned//amount provisioned:6 
    
    ps.setObject(10, "NA"); // OtherOne
    
    ps.setObject(11,"NA");// OtherTwo
    
    ps.setObject(12, "NA"); // OtherThree
    
    ps.execute();
    cq.setAutoCommit(true); 
    loancon.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
}


public synchronized void adjustStatusOfWriteOff(List instalment){

updateInstalmentStatusWrittenOff(instalment.get(1).toString());
closeWrittenOffLoanAccount(instalment.get(1).toString());
updateWrittenOffLoanStore(instalment.get(1).toString());




}

public long numberOfDaysInArrears(String accountNumber){
   String sate="";
 
 
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'"+" AND NOT instalment_status="+" "+"'"+"P"+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
    if(rst3.first()){
   sate=rst3.getString("instalment_due_date");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 
 return fmt.diffDays(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(sate), fmt.fromDatabaseWithDashSeperatorBeginningWithYear(getDBCurrentDate()));
 
 
 }

public boolean isTheNumberOfInstalmentsMoreThanOne(String accountNumber){
boolean itsIs=false;int numberOfInstalments=1;
 
 
 try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  COUNT(instalment_no) AS noOfInstalments FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
  while(rst3.next()){
   numberOfInstalments=rst3.getInt("noOfInstalments");
    }
    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
 
 if(numberOfInstalments>1){
 
 itsIs=true;
 }
 
 return itsIs;


}

public List loanTrnIdInterestRateWrittenOff(String accountNumberd){

List theDetails=new ArrayList();

if(loanExists(accountNumberd)){

 try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT trn_id, interest_rate FROM  new_loan_appstore WHERE Loan_id='writtenOffloan"+accountNumberd+"' AND loan_cycle_status='WrittenOff'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
theDetails.add(rst3.getString("trn_id"));
 theDetails.add(rst3.getString("interest_rate"));   
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
}else{
theDetails.add("0");
theDetails.add("0");
}
    return theDetails;

}

public List loanTrnIdInterestRate(String accountNumberd){

List theDetails=new ArrayList();

if(loanExists(accountNumberd)){

 try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT trn_id, interest_rate FROM  new_loan_appstore WHERE Loan_id='newloan"+accountNumberd+"' AND loan_cycle_status='Disbursed'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
theDetails.add(rst3.getString("trn_id"));
 theDetails.add(rst3.getString("interest_rate"));   
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
}else{
theDetails.add("0");
theDetails.add("0");
}
    return theDetails;

}

public List getThoseDetails(String accountNumber){
List thoseAcDetails=new ArrayList();
 try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT TotalInterestRemaining, TotalPrincipalRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining,balance_due FROM  new_loan_appstore WHERE Loan_id='newloan"+accountNumber+"' AND loan_cycle_status='Disbursed'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
thoseAcDetails.add(rst3.getString("TotalPrincipalRemaining"));
  thoseAcDetails.add(rst3.getString("TotalInterestRemaining"));
   thoseAcDetails.add(rst3.getString("TotalAccumulatedInterestRemaining"));
    thoseAcDetails.add(rst3.getString("TotalLoanPenaltyRemaining"));
     thoseAcDetails.add(rst3.getString("balance_due"));
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }


return thoseAcDetails;
}


public List loanTrnIdInterestRatePI(String accountNumberd){

List theDetails=new ArrayList();

 try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT trn_id, interest_rate,princimpal_amount,total_interest,total_loanAmount FROM  new_loan_appstore WHERE Loan_id='newloan"+accountNumberd+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
theDetails.add(rst3.getString("trn_id"));
  theDetails.add(rst3.getString("total_interest"));
   theDetails.add(rst3.getString("total_loanAmount"));
    theDetails.add(rst3.getString("interest_rate"));
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return theDetails;

}


public String tenureType(String accountNumber,Component c){
String loanTenure="",actualTenure="";

 try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT loan_tenure FROM  new_loan_appstore WHERE Loan_id='newloan"+accountNumber+"' AND loan_cycle_status='Disbursed'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){

    loanTenure= rst3.getString("loan_tenure");
    
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return loanTenure.split("\\s")[1].trim();

//this.groupNumber()
}



public void stopInterest(String accountNumberd){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL stopInterestRate("+"'"+"newloan"+accountNumberd+"'"+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}

  private synchronized void createInitialInterestML(String accountNumberNow,String dueDate,String principal,String  interest,Component c){
//      JOptionPane.showMessageDialog(c, "loanid= "+accountNumberNow+";"+"\n dueDate= "+dueDate+";"+"\n princimpal= "+principal+";"+"\n Interest"+interest);
      
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
String theProcedure ="CALL createFirstInterestIntalment("+"'"+accountNumberNow+"'"+",'"+fmt.forDatabaseWithFullYearBeginningWithDate(dueDate)+"',"+principal+","+interest+")";
   
//JOptionPane.showMessageDialog(c, theProcedure);

              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.execute();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }
    
  }
         
         
  public synchronized String staffName(String stafId){
//      JOptionPane.showMessageDialog(c, "loanid= "+accountNumberNow+";"+"\n dueDate= "+dueDate+";"+"\n princimpal= "+principal+";"+"\n Interest"+interest);
      String name="";
         try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);
//String theProcedure ="CALL staffName("+"'"+stafId+"'"+")";
   String query = "SELECT staffName1("+"'"+stafId+"'"+") AS name";  
 PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){

    name= rst3.getString("name");
    
    }
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }
    
       return   name;
  }
        
        






public void updateLoanArrearsStatus(int statusIn){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyStatus("+statusIn+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}


public void updateLoanArrearsCycleDays(int cycleDIn){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyCycleDaysPro("+cycleDIn+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}



public void updateLoanArrearsChargeOn(int chargeOnS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyChargeOn("+chargeOnS+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}




public void updateLoanArrearsTheCharge(String chargeOnS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyTheActualCharge("+parseDouble(chargeOnS)+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}





public void updateLoanPenaltyChargeAs(int chargeAS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyChargeAs("+chargeAS+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}


public void updateLoanPenaltyDeadlineForEx(int chargeAS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyDeadlineExv("+chargeAS+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}



public void updateLoanPenaltyDeadlineMeasureNow(int chargeAS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL updateLoanPenaltyDeadlineMeasure("+chargeAS+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}



public void updateLoanPenaltyPenaltyNoTimes(int chargeAS){


         try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL recoverInterestNumberTimes("+chargeAS+")";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }  

}





public int    getMethod(Component c){
     
           int theMethod=0;
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL getMoneyLendMethod()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){


  theMethod=rsxmt.getInt("theMethod");

         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

      
return theMethod;

}



public int    getThePenaltyStatus(Component c){
     
           int theMethod=0;
           
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);
     
          
          
  String query ="CALL getPenaltyStatus()";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(query);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
      while(rsxmt.next()){


  theMethod=rsxmt.getInt("penaltyStatus");

         }
   
 
        cq.setAutoCommit(true);
        
         loancon.closeConnection(cq);
         
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(c, ex.toString());
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.FINEST, ex.toString());
      }

      
return theMethod;

}




public void    updateAmdaPenalty(Component c){
     
            try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL theAMDAPenaltyComputation()";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          } 
}

  









public void    updateAmdaPenalty2(Component c){
     
            try {
                Connection cq=loancon.createConnection(); 
                
          cq.setAutoCommit(false);
          
String theProcedure ="CALL theAMDAPenaltyComputation()";

//         JOptionPane.showMessageDialog(c, theProcedure);
//filed.setText(theProcedure);
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          } 
}

//
//
//postedDetails.add(batch);//Batch Code batch 0
//
//postedDetails.add(tnxId);//Transanction Code  tnxId 1
//
//postedDetails.add(txnType);//Txn Type    txnType      2
//
//postedDetails.add(txnCode);//Txn Code   txnCode  3
//
//postedDetails.add(DrAccountNumber);//DrAccountNumber  4
//
//postedDetails.add(CrAccountNumber);//CrAccountNumber   5
//
//postedDetails.add(Narration);//Narration  6
//
//postedDetails.add(amount);//amount  7
//
//postedDetails.add(interestRate);//interestRate  8
//
//postedDetails.add(tenure);//tenure    9
//
//postedDetails.add(DateFetched);//DateFetched   10
//
//postedDetails.add(periodType);//periodType   11
//
//postedDetails.add(periodSubType);//periodSubType  12
//
//postedDetails.add(officerInCharge);//tenure    13
//
//postedDetails.add(interestRegime);//periodType   14
//
//postedDetails.add(amortizationDate);//periodSubType  15
//
//postedDetails.add(theStartDateObject);
//
// public ArrayList<ArrayList<Object>> theObjectedStatement(Component c){
// data5= new ArrayList<>();
//
// try {
// Connection cq=quaryObj.createConnection();
//   cq.setAutoCommit(false);
//    String query ="CALL runningLoansDetails()";
//     PreparedStatement psxmt = cq.prepareStatement(query);
//        
//         ResultSet rsxmt = psxmt.executeQuery();
//           while(rsxmt.next()){
//         data4=new ArrayList();
//         data4.add(rsxmt.getString("id"));
//         data4.add(rsxmt.getString("customer_name"));
//         data4.add(rsxmt.getString("customer_account"));
//         data5.add(data4);
//            }


  public synchronized boolean createNewLoan(List theLoanDetails,Component c){
      boolean success=false; 
      int results=0;
      String calThis="CALL createNewLoan("
        +"'"+theLoanDetails.get(5)//accountNumber
        +"'"+","+theLoanDetails.get(7) //amount
        +","+theLoanDetails.get(8)//anual rate
          +","+theLoanDetails.get(9)//tenure
        +","+"'"+fmt.forDatabaseWithFullYearBeginningWithDate(theLoanDetails.get(10).toString())//txnDate
        +"'"+","+theLoanDetails.get(11)//periodType-number
        +","+"'"+theLoanDetails.get(12)//periodType-String
        +"'"+","+theLoanDetails.get(13)//userId
         +","+theLoanDetails.get(14)//Interest Regime
         +","+"'"+fmt.forDatabaseWithFullYearBeginningWithDate(theLoanDetails.get(15).toString())//amortizationDate
         +"'"+","+theLoanDetails.get(17)//loanOfficerId
          +","+theLoanDetails.get(18)//thePeriodSet    
                  +","+theLoanDetails.get(19)//theInterestComputation    
                        +","+"'"+theLoanDetails.get(0)//batchNumber  
       +"'"+")";
//PostingEntryWindow xc=(PostingEntryWindow)c;

//xc.jTextField70.setText(calThis);
//JOptionPane.showMessageDialog(c, calThis);
   Log.info (calThis);
        try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);

        PreparedStatement psxmt = cq.prepareStatement(calThis);
//        
         ResultSet rsxmt = psxmt.executeQuery();
           while(rsxmt.next()){
           results=rsxmt.getInt("theMessage"); 
           }
              cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              JOptionPane.showMessageDialog(c, ex.toString());
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }
//        JOptionPane.showMessageDialog(c, results);
     if(results==1){
         success=true;
     }
     
     return success;
  }
      


  

  public synchronized boolean createdRenewedLoan(List theLoanDetails,Component c){
      boolean success=false; 
      int results=0;
      String calThis="CALL createdRenewedLoan("
        +"'"+theLoanDetails.get(5)//accountNumber
        +"'"+","+theLoanDetails.get(7) //amount
        +","+theLoanDetails.get(8)//anual rate
          +","+theLoanDetails.get(9)//tenure
        +","+"'"+fmt.forDatabaseWithFullYearBeginningWithDate(theLoanDetails.get(10).toString())//txnDate
        +"'"+","+theLoanDetails.get(11)//periodType-number
        +","+"'"+theLoanDetails.get(12)//periodType-String
        +"'"+","+theLoanDetails.get(13)//userId
         +","+theLoanDetails.get(14)//Interest Regime
         +","+"'"+fmt.forDatabaseWithFullYearBeginningWithDate(theLoanDetails.get(15).toString())//amortizationDate
         +"'"+","+theLoanDetails.get(17)//loanOfficerId
          +","+theLoanDetails.get(18)//thePeriodSet    
                  +","+theLoanDetails.get(19)//theInterestComputation    
                        +","+"'"+theLoanDetails.get(0)//batchNumber  
       +"'"+")";
//PostingEntryWindow xc=(PostingEntryWindow)c;

//xc.jTextField70.setText(calThis);
//JOptionPane.showMessageDialog(c, calThis);
   Log.info (calThis);
        try {
                Connection cq=loancon.createConnection(); 
          cq.setAutoCommit(false);

        PreparedStatement psxmt = cq.prepareStatement(calThis);
//        
         ResultSet rsxmt = psxmt.executeQuery();
           while(rsxmt.next()){
           results=rsxmt.getInt("theMessage"); 
           }
              cq.setAutoCommit(true);
              loancon.closeConnection(cq);
              
          } catch (SQLException ex) {
              JOptionPane.showMessageDialog(c, ex.toString());
              Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
          }
//        JOptionPane.showMessageDialog(c, results);
     if(results==1){
         success=true;
     }
     
     return success;
  }
  
  public synchronized List repayTheLoan(List theLoanDetails,Component c){
      List response=new ArrayList(); 
      int results=0;
      String calThis="CALL RepayTheLoanNow("
        +"'"+theLoanDetails.get(4)//accountNumber
        +"'"+","+theLoanDetails.get(7) //amount
        +","+"'"+theLoanDetails.get(0)//Batch Code
                  +"'"+","+theLoanDetails.get(13)//userId
         +","+"'"+fmt.forDatabaseWithFullYearBeginningWithDate(theLoanDetails.get(15).toString())//amortizationDate
         +"'"+","+theLoanDetails.get(17)//loanOfficerId
       +")";
//PostingEntryWindow xc=(PostingEntryWindow)c;

//xc.jTextField70.setText(calThis);
//JOptionPane.showMessageDialog(c, calThis);
   Log.info (calThis);
       


      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

//   String query ="CALL RepayTheLoanNow("+calThis+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(calThis);
          
          ResultSet rsxmt = psxmt.executeQuery();
          
          while (rsxmt.next()){
        response.add(rsxmt.getInt("completionStatus"));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         response.add(rsxmt.getDouble("totalPrincipalX"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          response .add(rsxmt.getDouble("totalInsterestX"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               response .add(rsxmt.getDouble("totalAccumulatedInterestX"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                    response .add(rsxmt.getDouble("totalPenaltyX"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
 response .add(rsxmt.getString("loanCycleStatus"));
         
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return response;
        
        
  }

  
  public void    createRenewalDetails(List theDetails,Component c){
     
             

 try {
 Connection cq=loancon.createConnection();
   cq.setAutoCommit(false);
    String query ="CALL createTheRenewalDetails("+theDetails.get(0)+","+theDetails.get(1)+","+"'"+theDetails.get(2)+"'"+","+theDetails.get(3)+","+theDetails.get(4)+","+theDetails.get(5)+")";
//  JOptionPane.showMessageDialog(c, query);
    PreparedStatement psxmt = cq.prepareStatement(query);
              psxmt.executeUpdate();       
         cq.setAutoCommit(true);
           loancon.closeConnection( cq);
        } catch (SQLException ex) { 
          
            JOptionPane.showMessageDialog(c, ex.toString());
        }
}
       
                 
    
  public synchronized List getTheRenewalDetails(Component c){
      List response=new ArrayList(); 

      String calThis="CALL getTheRenewalDetails()";

   Log.info (calThis);
       


      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

//   String query ="CALL RepayTheLoanNow("+calThis+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(calThis);
     
          ResultSet rsxmt = psxmt.executeQuery();
   
          while (rsxmt.next()){
        response.add(rsxmt.getInt("renewalStatus"));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         response.add(rsxmt.getInt("renewalDeadline"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          response .add(rsxmt.getString("renewalMeasure"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               response .add(rsxmt.getDouble("renewalRate"));
//                  JOptionPane.showMessageDialog(c, rsxmt.getString("date_taken"));
                     response .add(rsxmt.getInt("periodUsed"));
                    response .add(rsxmt.getDouble("renewalTimes"));
//           JOptionPane.showMessageDialog(c, rsxmt.getString("due_date"));
         
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return response;
        
        
  }
  

    
  
  public synchronized List<List>  getTheLoansForRenewal(Component c){
    
   List<List> response=new ArrayList(); 

   
   
      String calThis="CALL theLoansForRenewal()";

   Log.info (calThis);
 
      try {
          
           Connection cq=loancon.createConnection();
           
          cq.setAutoCommit(false);

//   String query ="CALL RepayTheLoanNow("+calThis+")";
                                           
//     JOptionPane.showMessageDialog(c, query);    
     
          PreparedStatement psxmt = cq.prepareStatement(calThis);
     
          ResultSet rsxmt = psxmt.executeQuery();
   
          while (rsxmt.next()){
           List theLoanDetail =new ArrayList();      
        theLoanDetail.add(rsxmt.getString("accountNumberN"));
//        JOptionPane.showMessageDialog(c, (parseInt(rsxmt.getString("id_7"))-1));
         theLoanDetail.add(rsxmt.getInt("officerIdN"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          theLoanDetail .add(rsxmt.getDouble("balanceDueN"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               theLoanDetail .add(rsxmt.getDouble("interestRateUsed"));

                theLoanDetail.add(rsxmt.getString("trnDateUsed"));
//      JOptionPane.showMessageDialog(c, rsxmt.getString("customer_name"));
          theLoanDetail .add(rsxmt.getInt("tenureUsed"));
//             JOptionPane.showMessageDialog(c, rsxmt.getString("customer_contact"));
               theLoanDetail .add(rsxmt.getInt("periodUsed"));
//                
         response.add(theLoanDetail);
          }
        rsxmt.close();
        psxmt.close();
          cq.setAutoCommit(true);
          loancon.closeConnection(cq);
      } catch (SQLException ex) {
          Logger.getLogger(ReportsDatabase.class.getName()).log(Level.SEVERE, null, ex);
      }
return response;
          

}

  
  
public void fillWithPortfolioNames(JComboBox userBox){
    
 String accountName="";ArrayList <String> useNames=new ArrayList();
 
useNames.add("Select Portfolio Owner");
   try {
         Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
             
             String query = "SELECT  account_name,user_id FROM log_in";
             PreparedStatement ps = cq.prepareStatement(query);
             ResultSet rst3 = ps.executeQuery();
             
            while(rst3.next()){
               
           accountName=rst3.getString("account_name")+":"+rst3.getString("user_id");
       useNames.add(accountName);
            
             }
          
             modelcombo = new MyComboBoxModel(useNames);
                    userBox.setModel(modelcombo);  
             cq.setAutoCommit(true);
            quaryObj.closeConnection(cq);
         } catch (SQLException ex) {
             Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
         }
  userBox.setSelectedIndex(0);
 
 
 }



    
    
     public String borrowerAccount(String trnId){
String    theAccount=null;
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT applicant_account_number FROM  new_loan_appstore  WHERE trn_id ="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.next()){
  theAccount=rst3.getString("applicant_account_number");
   
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return theAccount;

    } 

     
     
     
    
    
     public String borrowerLoanId(String trnId){
String    theAccount=null;
    try {

    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
   String query = "SELECT loan_Id FROM  new_loan_appstore  WHERE trn_id ="+trnId;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.next()){
  theAccount=rst3.getString("loan_Id");
   
    }



    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }

    return theAccount;

    }  
     
     
     
     
    }



  



































































































































































    
