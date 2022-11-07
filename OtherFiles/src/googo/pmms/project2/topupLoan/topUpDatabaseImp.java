/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.topupLoan;

import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.databaseConnectors.JdbcConnector;
import googo.pmms.project2.databaseConnectors.JdbcConnector1;
import googo.pmms.project2.databases.loanDatabaseQuaries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author StancharttopUpDataBase
 */
public class topUpDatabaseImp implements topUpDataBase {
    fileInputOutPutStreams fios= new fileInputOutPutStreams();
    JdbcConnector quaryObj = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt"))); ; 
    JdbcConnector1 loancon= new   JdbcConnector1(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "LoanDetails.txt")));
    List loanInstamentIds;
     Formartter fmt = new Formartter();
    String accountNumber;
    public topUpDatabaseImp(String accountNumber){
    this.accountNumber=accountNumber;
    }

    public List gettrnIdsForInstalments() {
        List data5= new ArrayList<>();
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT trn_id FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
   data5.add(rst3.getString("trn_id"));
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return data5;
    }

    @Override
    public Double getPrincipalInstalment(int trnID) {
      Double principalInstalment=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT princimpal_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 principalInstalment=rst3.getDouble("princimpal_amount");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return principalInstalment;
    
    }

    @Override
    public Double getInterestInstalment(int trnID) {
       Double interestInstalment=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT interest_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 interestInstalment=rst3.getDouble("interest_amount");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return interestInstalment;
    }
    @Override
    public Double getInstalmentAmount(int trnID) {
       Double instalmentAmount=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_amount FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 instalmentAmount=rst3.getDouble("instalment_amount");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentAmount;
    }

    @Override
    public Double getBeginningBal(int trnID) {
          Double beginningAmount=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  beginning_bal FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 beginningAmount=rst3.getDouble("beginning_bal");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return beginningAmount;
    }

    @Override
    public Double getClosingBal(int trnID) {
       Double closingAmount=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  closing_bal FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 closingAmount=rst3.getDouble("closing_bal");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return closingAmount;
    }

    @Override
    public Date getInstalmentDueDate(int trnID) {
       Date instalmentDueDate=null;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_due_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND  "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 instalmentDueDate=rst3.getDate("instalment_due_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentDueDate;
    }

    @Override
    public Date getInstalmentPaidDate(int trnID) {
        Date instalmentPaidDate=null;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_paid_date FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND "+"trn_id="+trnID;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
 instalmentPaidDate=rst3.getDate("instalment_paid_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentPaidDate;
    }

    @Override
    public String getTrnId() {
          String trnId="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   trn_id FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 trnId=rst3.getString("trn_id");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return trnId;
        
        
    }

    @Override
    public Date getTrnDate() {
       Date trnDate=null;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   trn_date FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

     if (rst3.last()){
 trnDate=rst3.getDate("trn_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return trnDate;
    }

    @Override
    public String getLoanId() {
       String loanId=null;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   loan_id FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 loanId=rst3.getString("loan_id");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return loanId;
    }

    @Override
    public String getTotalNumberIntalments() {
         String numberInstalment="0";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT    total_instalments FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 numberInstalment=rst3.getString("total_instalments");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return numberInstalment;
    }

    @Override
    public String getRemainingInstalments() {
      String remainingInstalment="0";
//        this.getNumberInstalmentInArrears()
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT    remaining_instalments FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 remainingInstalment=rst3.getString("remaining_instalments");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return remainingInstalment;
    }

//    public int getInstalmentsPaid() {
//       int remainingInstalment=0;
//        
//        try {
//  
//    Connection cq=loancon.createConnection(); 
//    cq.setAutoCommit(false);
//    String query = "SELECT    remaining_instalments FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
//    PreparedStatement ps = cq.prepareStatement(query);
//    ResultSet rst3 = ps.executeQuery();
//
//     if (rst3.last()){
// remainingInstalment=rst3.getInt("remaining_instalments");
//    }
//
//    cq.setAutoCommit(true);
//    loancon.closeConnection(cq);
//    } catch (SQLException ex) { 
//    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
//    }
//        
//        return remainingInstalment;
//    }

    @Override
    public Double getTotalPrincimpalAmount() {
      Double totalPrincimpalAmount=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT    princimpal_amount FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

   if (rst3.last()){
 totalPrincimpalAmount=rst3.getDouble("princimpal_amount");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return totalPrincimpalAmount;
    }

    @Override
    public Double getTotalInterestAmount() {
         Double totalInterestAmount=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT total_interest FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 totalInterestAmount=rst3.getDouble("total_interest");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return totalInterestAmount;
    }

   

    @Override
    public String getBalanceAmountDue() {
         String balanceAmountDue="0.0";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT balance_due FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 balanceAmountDue=rst3.getString("balance_due");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return balanceAmountDue;
    }

    @Override
    public Double getTheInstalmentAmount() {
         Double theInstalment=0.0;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_amount FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 theInstalment=rst3.getDouble("instalment_amount");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return theInstalment;
    }

    @Override
    public String getInstalmentStartDate() {
         String instalmentStaertDate="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_start_date FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

     if (rst3.last()){
 instalmentStaertDate=rst3.getString("instalment_start_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentStaertDate;
    }

    @Override
    public String getInstalmentNextDueDate() {
        String instalmentNextDueDate=null;
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_next_due_date FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 instalmentNextDueDate=rst3.getString("instalment_next_due_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentNextDueDate;
    }

    @Override
    public String getInstalmentEndDate() {
         String instalmentEndDate="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT  instalment_end_date FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 instalmentEndDate=rst3.getString("instalment_end_date");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return instalmentEndDate;
    }

    @Override
    public String getInterestRate() {
          String interest="0";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   interest_rate FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if (rst3.last()){
 interest=rst3.getString("interest_rate");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return interest;
    }

    @Override
    public String getLoanTenure() {
       String loanTenure="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   loan_tenure FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 loanTenure=rst3.getString("loan_tenure");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return loanTenure;
    }

    @Override
    public String getBorrowerName() {
        String borrowerName="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   applicant_account_name FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    if (rst3.last()){
 borrowerName=rst3.getString("applicant_account_name");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return borrowerName;
    }

    @Override
    public String getLoanCycleStatus() {
     String loanCycle="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   loan_cycle_status FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();
 if (rst3.last()){
 loanCycle=rst3.getString("loan_cycle_status");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return loanCycle;
    }

    @Override
    public String getStaffMakerId() {
       String maker="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   inputter_id FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

     if (rst3.last()){
 maker=rst3.getString("inputter_id");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return maker;
    }

    @Override
    public String getStaffCheckerId() {
       String checker="";
        
        try {
  
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);
    String query = "SELECT   authoriser_id FROM new_loan_appstore WHERE  applicant_account_number="+accountNumber;
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

     if (rst3.last()){
 checker=rst3.getString("authoriser_id");
    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        return checker;
    }

    @Override
    public Double getTotalPrincimpalPaid() {
       Double totalPrincimpalPaid=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM( PrincipalPaid)AS totalPrincimpal FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    totalPrincimpalPaid =rst3.getDouble("totalPrincimpal");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    return totalPrincimpalPaid;
    }

    @Override
    public Double getTotalInterestPaid() {
      Double totalInterestPaid=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(InterestPaid)AS totalInterest FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"'";

    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    totalInterestPaid =rst3.getDouble("totalInterest");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    return totalInterestPaid;
    }

    @Override
    public Double getArrearsAmount() {
     Double totalAmountArrears=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(instalment_amount)AS totalInstalmentsArrears FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
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

    @Override
    public String getNumberInstalmentInArrears() {
      String totalNumberOfInstalmentsInArrears="0";
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT COUNT(instalment_amount)AS totalInstalmentsInArrears FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND (instalment_due_date<"+"'"+getDBCurrentDate()+"'"+" "+"AND"+" "+"NOT instalment_status="+"'"+"P"+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    totalNumberOfInstalmentsInArrears =rst3.getString("totalInstalmentsInArrears");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    return totalNumberOfInstalmentsInArrears;    
        
    
    }

    public Double getCurrentInstalment() {
     
         double currentInstalment=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(instalment_amount)AS currentInstalment FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND (NOT instalment_status='P' AND instalment_due_date<="+"'"+this.getInstalmentNextDueDate()+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    currentInstalment =rst3.getDouble("currentInstalment");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    return currentInstalment;    
        
        
        
    }
    
    
    

            
             @Override
    public Double outStandingInterestNow() {
     
         double outStandingInterest=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(InterestRemaing)AS outStandingInterest FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND (NOT instalment_status='P' AND instalment_due_date<="+"'"+this.getDBCurrentDate()+"'"+")";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    outStandingInterest =rst3.getDouble("outStandingInterest");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    return outStandingInterest;    
        
        
        
    }
    
  @Override
    public Double outStandingAccumulatedInterestNow() {
     
         double outStandingAccumulatedInterest=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(AccumulatedInterestRemaining)AS accumulated FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT instalment_status='P'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    outStandingAccumulatedInterest =rst3.getDouble("accumulated");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    return outStandingAccumulatedInterest;    
        
        
        
    }
    
     @Override
    public Double outStandingChargesNow() {
     
         double outStandingCharges=0.0;
        
    try {
    Connection cq=loancon.createConnection(); 
    cq.setAutoCommit(false);   
    String query = "SELECT SUM(LoanPenaltyRemaining)AS loanPenalty FROM new_loan_appstoreamort  WHERE master2_id="+"'"+"newloan"+accountNumber+"' AND NOT instalment_status='P'";
    PreparedStatement ps = cq.prepareStatement(query);
    ResultSet rst3 = ps.executeQuery();

    while (rst3.next()){
    outStandingCharges =rst3.getDouble("loanPenalty");     

    }

    cq.setAutoCommit(true);
    loancon.closeConnection(cq);
    } catch (SQLException ex) { 
    Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

    }

    
    
    return outStandingCharges;    
        
        
        
    }
    
public String getDBCurrentDate() {
    String thistime=" ";
    thistime=fmt.dateConverterForDatabase(System.currentTimeMillis( ));

return thistime;
}
    








}