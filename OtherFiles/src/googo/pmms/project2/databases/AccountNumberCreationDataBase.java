/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databases;

import googo.pmms.project2.databaseConnectors.*;
import googo.pmms.project2.accountsHelper.Formartter;
import googo.pmms.project2.frameHelper.MyComboBoxModel;
import googo.pmms.project2.frameHelper.ObjectTableModel;
import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import googo.pmms.project2.frameHelper.ListDataModel_1111;
import googo.pmms.project2.frameHelper.listComboBoxModel;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Stanchart
 */
public class AccountNumberCreationDataBase {
      fileInputOutPutStreams fios= new fileInputOutPutStreams();
    GregorianCalendar cal = new GregorianCalendar(); 
    
    ArrayList<ArrayList<String>> data7;

    Formartter fmt = new Formartter();
   JdbcConnector quaryObj = new JdbcConnector(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "AccountDBDetails.txt")));  
 JdbcConnector1 loancon= new   JdbcConnector1(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "LoanDetails.txt")));
 
     DatabaseMetaData metadata = null;
      ArrayList<String> data8, column1,data;
      ArrayList<ArrayList<String>> data6;
    ArrayList<Object> data4;
DecimalFormat df2 = new DecimalFormat("#");

 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
 ArrayList<ArrayList<Object>> data5;
   MyComboBoxModel modelcombo,modelcombo1;
   ObjectTableModel  model;
   ListDataModel_1111 modelList;
    listComboBoxModel comboList;
   DatabaseQuaries dbq= new DatabaseQuaries();
   String userId;
   public void setUserID(String userid){
this.userId=userid;
}
    public synchronized  String createAccountNumberSubLedger(String selection3,String ledgerProductCode,String ledgerProductName){
    String accountNumber="";
    
    return accountNumber;
    }
    public String createAccountNumber(String selection3,String ledgerProductCode,String ledgerProductName,Component c){
        String accountNumber="";
   
    switch(selection3){
    
        case "Land & Building":
           accountNumber= "01"+recentAccountMaster("1000000","1009999","01",ledgerProductCode,"Land & Building",ledgerProductName,c)+ledgerProductCode; 
            
            break;
            
case "Office Equipment":
  
   accountNumber= "01"+recentAccountMaster("1010000","1019999","01",ledgerProductCode,"Office Equipment",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Motor Vehicles":
    
    accountNumber= "01"+recentAccountMaster("1020000","1029999","01",ledgerProductCode,"Motor Vehicles",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Furniture, Fixtures & Fittings":
    
    accountNumber= "01"+recentAccountMaster("1030000","1039999","01",ledgerProductCode,"Furniture, Fixtures & Fittings",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Computers And Accessories":
   accountNumber= "01"+recentAccountMaster("1040000","1049999","01",ledgerProductCode,"Computers And Accessories",ledgerProductName,c)+ledgerProductCode;  
    
    
    break;		
case "Other Fixed Assets":
   accountNumber= "01"+recentAccountMaster("1050000","1059999","01",ledgerProductCode,"Other Fixed Assets",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Intangible Assets":
    
     accountNumber= "01"+recentAccountMaster("1060000","1069999","01",ledgerProductCode,"Intangible Assets",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Accumulated Depreciation/Amortization":
    
     accountNumber= "01"+recentAccountMaster("1070000","1079999","01",ledgerProductCode,"Accumulated Depreciation/Amortization",ledgerProductName,c)+ledgerProductCode; 
    break;		
case "Shares":
    
    accountNumber= "01"+recentAccountMaster("1080000","1089999","01",ledgerProductCode,"Shares",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Debentures":
   accountNumber= "01"+recentAccountMaster("1090000","1099999","01",ledgerProductCode,"Debentures",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Fixed Deposits":
    
     accountNumber= "01"+recentAccountMaster("1100000","1109999","01",ledgerProductCode,"Fixed Deposits",ledgerProductName,c)+ledgerProductCode; 
    break;		
case "Government Securities":
    
     accountNumber= "01"+recentAccountMaster("1110000","1119999","01",ledgerProductCode,"Government Securities",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Other Investments":
     accountNumber= "01"+recentAccountMaster("1120000","1129999","01",ledgerProductCode,"Other Investments",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Stores/Consumables Including Packing Materials":
     accountNumber= "01"+recentAccountMaster("1130000","1139999","01",ledgerProductCode,"Stores/Consumables Including Packing Materials",ledgerProductName,c)+ledgerProductCode;      
    break;		
case "Raw Materials":
      accountNumber= "01"+recentAccountMaster("1140000","1149999","01",ledgerProductCode,"Raw Materials",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Work-In-Progress":
     accountNumber= "01"+recentAccountMaster("1150000","1159999","01",ledgerProductCode,"Work-In-Progress",ledgerProductName,c)+ledgerProductCode;   
    break;	
    
case "Finished Goods Or Trade Goods":
    
     accountNumber= "01"+recentAccountMaster("1160000","1169999","01",ledgerProductCode,"Finished Good Or Trade Goods",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Trade Receivables":
    accountNumber= "01"+recentAccountMaster("1170000","1179999","01",ledgerProductCode,"Trade Receivables",ledgerProductName,c)+ledgerProductCode;  
    
    break;	
 case "Debtors":
      accountNumber= "01"+recentAccountMaster("1180000","1189999","01",ledgerProductCode,"Debtors",ledgerProductName,c)+ledgerProductCode;    
     
     break;

      case "Computer Software And Hardware":
      accountNumber= "01"+recentAccountMaster("1360000","1369999","01",ledgerProductCode,"Computer Software And Hardware",ledgerProductName,c)+ledgerProductCode;    
     
     break;
     
     
case "Interest Receivable":
  accountNumber= "01"+recentAccountMaster("1190000","1199999","01",ledgerProductCode,"Interest Receivable",ledgerProductName,c)+ledgerProductCode;   
    break;
    case "Accounts Receivable":
  accountNumber= "01"+recentAccountMaster("1310000","1319999","01",ledgerProductCode,"Accounts Receivable",ledgerProductName,c)+ledgerProductCode;   
    break;
    case "Allowance for Doubtful Accounts":
  accountNumber= "01"+recentAccountMaster("1320000","1329999","01",ledgerProductCode,"Allowance for Doubtful Accounts",ledgerProductName,c)+ledgerProductCode;   
    break;
    case "Allowance for Doubtful Interest":
  accountNumber= "01"+recentAccountMaster("1330000","1339999","01",ledgerProductCode,"Allowance for Doubtful Interest",ledgerProductName,c)+ledgerProductCode;   
    break;
    
    case "Accumulated Interest Receivable":
  accountNumber= "01"+recentAccountMaster("1340000","1349999","01",ledgerProductCode,"Accumulated Interest Receivable",ledgerProductName,c)+ledgerProductCode;   
    break;
     case "Loan Penalty Receivable":
  accountNumber= "01"+recentAccountMaster("1350000","1359999","01",ledgerProductCode,"Loan Penalty Receivable",ledgerProductName,c)+ledgerProductCode;   
    break;
case "Prepayments":
    
   accountNumber= "01"+recentAccountMaster("1200000","1209999","01",ledgerProductCode,"Prepayments",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Mobile Money":
   accountNumber= "01"+recentAccountMaster("1210000","1219999","01",ledgerProductCode,"Mobile Money",ledgerProductName,c)+ledgerProductCode;     
    
    break;
    
case "Bank Balance":
   accountNumber= "01"+recentAccountMaster("1220000","1229999","01",ledgerProductCode,"Bank Balance",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Cash At Hand":
  accountNumber= "01"+recentAccountMaster("1230000","1239999","01",ledgerProductCode,"Cash At Hand",ledgerProductName,c)+ledgerProductCode;     
    
    break;		
case "Cash Equivalents":
   accountNumber= "01"+recentAccountMaster("1240000","1249999","01",ledgerProductCode,"Cash Equivalents",ledgerProductName,c)+ledgerProductCode;      
    break;		
 case "Loans To Related Parties":
  accountNumber= "01"+recentAccountMaster("1250000","1259999","01",ledgerProductCode,"Loans To Related Parties",ledgerProductName,c)+ledgerProductCode;     
     
     break;		
case "Advances To Staff":
    accountNumber= "01"+recentAccountMaster("1260000","1269999","01",ledgerProductCode,"Advances To Staff",ledgerProductName,c)+ledgerProductCode;  
    break;	
case "Deposits (Other Than Fixed Deposit)":
   accountNumber= "01"+recentAccountMaster("1270000","1279999","01",ledgerProductCode,"Deposits (Other Than Fixed Deposit)",ledgerProductName,c)+ledgerProductCode;    
    break;		
case "Loans To Customers":
    accountNumber= "01"+recentAccountMaster("1280000","1289999","01",ledgerProductCode,"Loans To Customers",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Balances With Tax Authority":
   accountNumber= "01"+recentAccountMaster("1290000","1299999","01",ledgerProductCode,"Balances With Tax Authority",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Deferred Tax Asset":
   accountNumber= "01"+recentAccountMaster("1300000","1309999","01",ledgerProductCode,"Deferred Tax Asset",ledgerProductName,c)+ledgerProductCode; 
    break;
    
case "Accounts Payable":
  accountNumber= "05"+recentAccountMaster("5000000","5009999","05",ledgerProductCode,"Accounts Payable",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Uknown Mobile Money":
 accountNumber= "05"+recentAccountMaster("5010000","5019999","05",ledgerProductCode,"Uknown Mobile Money",ledgerProductName,c)+ledgerProductCode;  
    break;	
    
   
    
case "Customer Deposits":
  accountNumber= "05"+recentAccountMaster("5020000","5029999","05",ledgerProductCode,"Customer Deposits",ledgerProductName,c)+ledgerProductCode;     
    break;
    
    
case "Liability For Leased Assets":
    
   accountNumber= "05"+recentAccountMaster("5030000","5039999","05",ledgerProductCode,"Liability For Leased Assets",ledgerProductName,c)+ledgerProductCode;    
    break;	
   
case "Accrued Interest":
   accountNumber= "05"+recentAccountMaster("5040000","5049999","05",ledgerProductCode,"Accrued Interest",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Amounts Due To Others":
  accountNumber= "05"+recentAccountMaster("5050000","5059999","05",ledgerProductCode,"Amounts Due To Others",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Unpaid Matured Debts":
    
    accountNumber= "05"+recentAccountMaster("5060000","5069999","05",ledgerProductCode,"Unpaid Matured Debts",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Other Current Liabilities":
     accountNumber= "05"+recentAccountMaster("5070000","5079999","05",ledgerProductCode,"Other Current Liabilities",ledgerProductName,c)+ledgerProductCode;     
    
    break;	
  case "Return On Investment Payable":
     accountNumber= "05"+recentAccountMaster("5220000","5229999","05",ledgerProductCode,"Return On Investment Payable",ledgerProductName,c)+ledgerProductCode;     
    
    break; 
    
    case "Insurance Payable":
     accountNumber= "05"+recentAccountMaster("5240000","5249999","05",ledgerProductCode,"Insurance Payable",ledgerProductName,c)+ledgerProductCode;     
    
    break; 
     case "Interest Payable":
     accountNumber= "05"+recentAccountMaster("5250000","5259999","05",ledgerProductCode,"Interest Payable",ledgerProductName,c)+ledgerProductCode;     
    
    break;
    
     case "Tax Payable":
     accountNumber= "05"+recentAccountMaster("5260000","5269999","05",ledgerProductCode,"Tax Payable",ledgerProductName,c)+ledgerProductCode;     
    
    break;
    
    case "PAYE Payable":
     accountNumber= "05"+recentAccountMaster("5270000","5279999","05",ledgerProductCode,"PAYE Payable",ledgerProductName,c)+ledgerProductCode;     
    
    break;
    case  "NSSF Payable":
     accountNumber= "05"+recentAccountMaster("5280000","5289999","05",ledgerProductCode, "NSSF Payable",ledgerProductName,c)+ledgerProductCode;     
     
    break;
   
     case "Unclassified Amounts":
     accountNumber= "05"+recentAccountMaster("5230000","5239999","05",ledgerProductCode,"Unclassified Amounts",ledgerProductName,c)+ledgerProductCode;     
    
    break;  
    
case "Provision For Income Tax":
    
       accountNumber= "05"+recentAccountMaster("5080000","5089999","05",ledgerProductCode,"Provision For Income Tax",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Provision For Bad Debts":
    
    accountNumber= "05"+recentAccountMaster("5090000","5099999","05",ledgerProductCode,"Provision For Bad Debts",ledgerProductName,c)+ledgerProductCode; 
    break;		
case "Proposed Dividends":
   accountNumber= "05"+recentAccountMaster("5100000","5109999","05",ledgerProductCode,"Proposed Dividends",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Other Provisions":
    accountNumber= "05"+recentAccountMaster("5110000","5119999","05",ledgerProductCode,"Other Provisions",ledgerProductName,c)+ledgerProductCode;      
    break;		
case "Secured Loan From Financial Institutions":
    
     accountNumber= "05"+recentAccountMaster("5120000","5129999","05",ledgerProductCode,"Secured Loan From Financial Institutions",ledgerProductName,c)+ledgerProductCode;    
    break;		
case "Secured Other Loans":
      accountNumber= "05"+recentAccountMaster("5130000","5139999","05",ledgerProductCode,"Secured Other Loans",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Debt Securities Issued":
    accountNumber= "05"+recentAccountMaster("5140000","5149999","05",ledgerProductCode,"Debt Securities Issued",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Secured Due To Related Parties":
    
     accountNumber= "05"+recentAccountMaster("5150000","5159999","05",ledgerProductCode,"Secured Due To Related Parties",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Unsecured Loan From Financial Institutions":
     accountNumber= "05"+recentAccountMaster("5160000","5169999","05",ledgerProductCode,"Unsecured Loan From Financial Institutions",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Unsecured Other Loans":
    
       accountNumber= "05"+recentAccountMaster("5170000","5179999","05",ledgerProductCode,"Unsecured Other Loans",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Payables For More Than One Operating Cycle":
    
    accountNumber= "05"+recentAccountMaster("5180000","5189999","05",ledgerProductCode,"Payables For More Than One Operating Cycle",ledgerProductName,c)+ledgerProductCode;  
    
    break;		
case "Creditors For More Than One Operating Cycle":
    
      accountNumber= "05"+recentAccountMaster("5190000","5199999","05",ledgerProductCode,"Creditors For More Than One Operating Cycle",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Unsecured Due To Related Parties":
    
   accountNumber= "05"+recentAccountMaster("5200000","5209999","05",ledgerProductCode,"Unsecured Due To Related Parties",ledgerProductName,c)+ledgerProductCode;  
    
    break;		
case "Deferred Tax Liability":
     accountNumber= "05"+recentAccountMaster("5210000" ,"55219999","05",ledgerProductCode,"Deferred Tax Liability",ledgerProductName,c)+ledgerProductCode;  
    
    break;	
case "Sales":
   accountNumber= "03"+recentAccountMaster("3000000" ,"3009999","03",ledgerProductCode,"Sales",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Gross Interest Income":
  accountNumber= "03"+recentAccountMaster("3010000" ,"3019999","03",ledgerProductCode,"Gross Interest Income",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Gross Receipt Of Profession Fees":
     accountNumber= "03"+recentAccountMaster("3020000" ,"3029999","03",ledgerProductCode,"Gross Receipt Of Profession Fees",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "Gross Receipt Of Consultancy Fees":
      accountNumber= "03"+recentAccountMaster("3030000" ,"3039999","03",ledgerProductCode,"Gross Receipt Of Consultancy Fees",ledgerProductName,c)+ledgerProductCode; 
    
    break;		
case "Ledger Fee":
    
      accountNumber= "03"+recentAccountMaster("3040000" ,"3049999","03",ledgerProductCode,"Ledger Fee",ledgerProductName,c)+ledgerProductCode; 
    break;		
case "Savings Withdraw Charges":
       accountNumber= "03"+recentAccountMaster("3050000" ,"3059999","03",ledgerProductCode,"Savings Withdraw Charges",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Bad Debts Interest Recovered":
        accountNumber= "03"+recentAccountMaster("3060000" ,"3069999","03",ledgerProductCode,"Bad Debts Interest Recovered",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Admin Costs":
    
        accountNumber= "03"+recentAccountMaster("3070000" ,"3079999","03",ledgerProductCode,"Admin Costs",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Charges":
      accountNumber= "03"+recentAccountMaster("3080000" ,"3089999","03",ledgerProductCode,"Charges",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Membership Fees":
   accountNumber= "03"+recentAccountMaster("3090000" ,"3099999","03",ledgerProductCode,"Membership Fees",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Loan Insurance Charges":
   accountNumber= "03"+recentAccountMaster("3100000" ,"3109999","03",ledgerProductCode,"Loan Insurance Charges",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Accumulated Interest Income":
    accountNumber= "03"+recentAccountMaster("3110000" ,"3119999","03",ledgerProductCode,"Accumulated Interest Income",ledgerProductName,c)+ledgerProductCode;   
    
    break;	
    
    
case "Loan Surcharge":
    
       accountNumber= "03"+recentAccountMaster("3120000" ,"3129999","03",ledgerProductCode,"Loan Surcharge",ledgerProductName,c)+ledgerProductCode;   break;		
case "Commission And Fees":
    
         accountNumber= "03"+recentAccountMaster("3130000" ,"3139999","03",ledgerProductCode,"Commission And Fees",ledgerProductName,c)+ledgerProductCode;  
    break;
   
    
case "Bad Debts Principal RecoveredX":
//     JOptionPane.showMessageDialog(c, ledgerProductCode);
    accountNumber= "03"+recentAccountMaster("3140000" ,"3149999","03",ledgerProductCode,"Bad Debts Principal RecoveredX",ledgerProductName,c)+ledgerProductCode;  
//    JOptionPane.showMessageDialog(c, accountNumber);
    break;	
    
    case "Bad Debts Principal Recovered":
//     JOptionPane.showMessageDialog(c, ledgerProductCode);
    accountNumber= "03"+recentAccountMaster("3260000" ,"3269999","03",ledgerProductCode,"Bad Debts Principal Recovered",ledgerProductName,c)+ledgerProductCode;  
//    JOptionPane.showMessageDialog(c, accountNumber);
    break;	
    
case "Loan Processing Fees":
  accountNumber= "03"+recentAccountMaster("3150000" ,"3159999","03",ledgerProductCode,"Loan Processing Fees",ledgerProductName,c)+ledgerProductCode;     
    break;
    
//case "Profit On Disposal Of Assets":
//    
//  accountNumber= "03"+recentAccountMaster("3160000" ,"3169999","03",ledgerProductCode,"Profit On Disposal Of Assets",ledgerProductName)+ledgerProductCode;    
//    
//    break;
    
    case "Annual Subscription Fees":
    
  accountNumber= "03"+recentAccountMaster("3160000" ,"3169999","03",ledgerProductCode,"Annual Subscription Fees",ledgerProductName,c)+ledgerProductCode;    
    
    break;
    
case "Provision For Bad And Doubtful Debts":
    
    accountNumber= "03"+recentAccountMaster("3170000" ,"3179999","03",ledgerProductCode,"Provision For Bad And Doubtful Debts",ledgerProductName,c)+ledgerProductCode;       
    break;		
case "Bad Debts Recovered":
  accountNumber= "03"+recentAccountMaster("3180000" ,"3189999","03",ledgerProductCode,"Bad Debts Recovered",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Gross Amount Received":
     accountNumber= "03"+recentAccountMaster("3190000" ,"3199999","03",ledgerProductCode,"Gross Amount Received",ledgerProductName,c)+ledgerProductCode;       
    
    break;		
case "Amount Recovered":
    accountNumber= "03"+recentAccountMaster("3200000" ,"3209999","03",ledgerProductCode,"Amount Recovered",ledgerProductName,c)+ledgerProductCode;       
    
    break;		
case "Reserve For Unexpired Risk Brought From Previous Year Of Income":
   accountNumber= "03"+recentAccountMaster("3210000" ,"3219999","03",ledgerProductCode,"Reserve For Unexpired Risk Brought From Previous Year Of Income",ledgerProductName,c)+ledgerProductCode;    
    break;		
case "Other Purely Short Term Insurance Income":
    accountNumber= "03"+recentAccountMaster("3220000" ,"3229999","03",ledgerProductCode,"Other Purely Short Term Insurance Income",ledgerProductName,c)+ledgerProductCode; 
    break;		
case "Any Other Income":
      accountNumber= "03"+recentAccountMaster("3230000" ,"3239999","03",ledgerProductCode,"Any Other Income",ledgerProductName,c)+ledgerProductCode; 
    
    break;
    case "Savings Penalties":
      accountNumber= "03"+recentAccountMaster("3240000" ,"3249999","03",ledgerProductCode,"Savings Penalties",ledgerProductName,c)+ledgerProductCode; 
    
    break;
    case "Other Incomes":
      accountNumber= "03"+recentAccountMaster("3250000" ,"3259999","03",ledgerProductCode,"Other Incomes",ledgerProductName,c)+ledgerProductCode; 
    
    break;
    
    
case "Issued, Subscribed And Paid Up Capital":
  accountNumber= "04"+recentAccountMaster("4000000" ,"4009999","04",ledgerProductCode,"Issued, Subscribed And Paid Up Capital",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Additional Issued Share Capital":
 accountNumber= "04"+recentAccountMaster("4010000" ,"4019999","04",ledgerProductCode,"Additional Issued Share Capital",ledgerProductName,c)+ledgerProductCode;   
    break;		
case "Education Fund 1%":
  accountNumber= "04"+recentAccountMaster("4020000" ,"4029999","04",ledgerProductCode,"Education Fund 1%",ledgerProductName,c)+ledgerProductCode;  
    
    break;		
case "Capital Reserve":
 accountNumber= "04"+recentAccountMaster("4030000" ,"4039999","04",ledgerProductCode,"Capital Reserve",ledgerProductName,c)+ledgerProductCode;      
    break;		
case "Education Fund":
   accountNumber= "04"+recentAccountMaster("4040000" ,"4049999","04",ledgerProductCode,"Education Fund",ledgerProductName,c)+ledgerProductCode;    
    break;		
case "Statutory Reserve Fund 10%":
    accountNumber= "04"+recentAccountMaster("4050000" ,"4059999","04",ledgerProductCode,"Statutory Reserve Fund 10%",ledgerProductName,c)+ledgerProductCode;     
    break;		
case "Revaluation Reserve":
    
     accountNumber= "04"+recentAccountMaster("4060000" ,"4069999","04",ledgerProductCode,"Revaluation Reserve",ledgerProductName,c)+ledgerProductCode;  
    break;		
case "Co-Operative Development Fund":
      accountNumber= "04"+recentAccountMaster("4070000" ,"4079999","04",ledgerProductCode,"Co-Operative Development Fund",ledgerProductName,c)+ledgerProductCode;   
    
    break;		
case "Retained Earnings":
      accountNumber= "04"+recentAccountMaster("4080000" ,"4089999","04",ledgerProductCode,"Retained Earnings",ledgerProductName,c)+ledgerProductCode;    
    
    
    break;		
case "Accumulated Profit Or Loss Or Surplus Or Deficit":
  accountNumber= "04"+recentAccountMaster("4090000" ,"4099999","04",ledgerProductCode,"Accumulated Profit Or Loss Or Surplus Or Deficit",ledgerProductName,c)+ledgerProductCode;    
    
    break;		
case "General Reserve":
   accountNumber= "04"+recentAccountMaster("4100000" ,"4109999","04",ledgerProductCode,"General Reserve",ledgerProductName,c)+ledgerProductCode;   
    break;
    
    
    
    
                case "Opening Stock Of Raw Materials":
        accountNumber= "02"+recentAccountMaster("2000000" ,"2009999","02",ledgerProductCode,"Opening Stock Of Raw Materials",ledgerProductName,c)+ledgerProductCode;                 
                    
                    break;		
              case "Opening Stock Of Work In Progress":
             accountNumber= "02"+recentAccountMaster("2010000" ,"2019999","02",ledgerProductCode,"Opening Stock Of Work In Progress",ledgerProductName,c)+ledgerProductCode;       
                  break;		
           case "Opening Stock Of Trading Goods":
     accountNumber= "02"+recentAccountMaster("2020000" ,"2029999","02",ledgerProductCode,"Opening Stock Of Trading Goods",ledgerProductName,c)+ledgerProductCode;                
               break;		
         case "Opening Stock Of Manufactured Goods":
     accountNumber= "02"+recentAccountMaster("2030000" ,"2039999","02",ledgerProductCode,"Opening Stock Of Manufactured Goods",ledgerProductName,c)+ledgerProductCode;            
             break;		
       case "Local Purchases Net Of Duties And Taxes":
       accountNumber= "02"+recentAccountMaster("2040000" ,"2049999","02",ledgerProductCode,"Local Purchases Net Of Duties And Taxes",ledgerProductName,c)+ledgerProductCode;    
           break;		
        case "Imports CIF Value And Taxes":
      accountNumber= "02"+recentAccountMaster("2050000" ,"2059999","02",ledgerProductCode,"Imports CIF Value And Taxes",ledgerProductName,c)+ledgerProductCode;          
            
            break;		
       case "Direct Wages":
   accountNumber= "02"+recentAccountMaster("2060000" ,"2069999","02",ledgerProductCode,"Direct Wages",ledgerProductName,c)+ledgerProductCode;            
           break;		
        case "Interest Expenses":
   accountNumber= "02"+recentAccountMaster("2070000" ,"2079999","02",ledgerProductCode,"Interest Expenses",ledgerProductName,c)+ledgerProductCode;          
            break;		
      case "Direct Expenses":
    accountNumber= "02"+recentAccountMaster("2080000" ,"2089999","02",ledgerProductCode,"Direct Expenses",ledgerProductName,c)+ledgerProductCode;       
          break;		
       case "Factory Rent":
  accountNumber= "02"+recentAccountMaster("2090000" ,"2099999","02",ledgerProductCode,"Factory Rent",ledgerProductName,c)+ledgerProductCode;           
           break;		
      case "Factory Rates":
     accountNumber= "02"+recentAccountMaster("2100000" ,"2109999","02",ledgerProductCode,"Factory Rates",ledgerProductName,c)+ledgerProductCode;          
          break;		
     case "Fuel And Power":
      accountNumber= "02"+recentAccountMaster("2110000" ,"2119999","02",ledgerProductCode,"Fuel And Power",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
               case "Indirect Wages":
          accountNumber= "02"+recentAccountMaster("2120000" ,"2129999","02",ledgerProductCode,"Indirect Wages",ledgerProductName,c)+ledgerProductCode;              
                   
                   break;		
               case "Consumables":
       accountNumber= "02"+recentAccountMaster("2130000" ,"2139999","02",ledgerProductCode,"Consumables",ledgerProductName,c)+ledgerProductCode;               
                   break;		
               case "Depreciation":
        accountNumber= "02"+recentAccountMaster("2140000" ,"2149999","02",ledgerProductCode,"Depreciation",ledgerProductName,c)+ledgerProductCode;            
                   
                   break;		
               case "Other Factory Overheads":
           accountNumber= "02"+recentAccountMaster("2150000" ,"2159999","02",ledgerProductCode,"Other Factory Overheads",ledgerProductName,c)+ledgerProductCode;          
                   break;		
               case "Closing Stock Of Raw Materials":
         accountNumber= "02"+recentAccountMaster("2160000" ,"2169999","02",ledgerProductCode,"Closing Stock Of Raw Materials",ledgerProductName,c)+ledgerProductCode;            
                   break;		
               case "Closing Stock Of Work In Progress":
   accountNumber= "02"+recentAccountMaster("2170000" ,"2179999","02",ledgerProductCode,"Closing Stock Of Work In Progress",ledgerProductName,c)+ledgerProductCode;                
                   break;		
               case "Closing Stock Of Manufactured Goods":
     accountNumber= "02"+recentAccountMaster("2180000" ,"2189999","02",ledgerProductCode,"Closing Stock Of Manufactured Goods",ledgerProductName,c)+ledgerProductCode;                
                   
                   break;		
               case "Closing Stock Of Trading Goods":
          accountNumber= "02"+recentAccountMaster("2190000" ,"2199999","02",ledgerProductCode,"Closing Stock Of Trading Goods",ledgerProductName,c)+ledgerProductCode;             
                   break;		
     case "Advertisement Expense":
             accountNumber= "02"+recentAccountMaster("2200000" ,"2209999","02",ledgerProductCode,"Advertisement Expense",ledgerProductName,c)+ledgerProductCode;       
         break;		
     case "Audit Expenses":
             accountNumber= "02"+recentAccountMaster("2210000" ,"2219999","02",ledgerProductCode,"Audit Expenses",ledgerProductName,c)+ledgerProductCode;    
         break;		
     case "Bad Debts Written Off":
        accountNumber= "02"+recentAccountMaster("2220000" ,"2229999","02",ledgerProductCode,"Bad Debts Written Off",ledgerProductName,c)+ledgerProductCode;          
         break;		
     case "Commission":
         accountNumber= "02"+recentAccountMaster("2230000" ,"2239999","02",ledgerProductCode,"Commission",ledgerProductName,c)+ledgerProductCode;   
         
         break;		
     case "Computer Soft And Hardware Expenses":
         
        accountNumber= "02"+recentAccountMaster("2240000" ,"2249999","02",ledgerProductCode,"Computer Soft And Hardware Expenses",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
     case "Maintainance of Office Equipment":
        accountNumber= "02"+recentAccountMaster("2250000" ,"2259999","02",ledgerProductCode,"Maintainance of Office Equipment",ledgerProductName,c)+ledgerProductCode;    
         
         break;		
     case "News Papers And Periodicals":
         
      accountNumber= "02"+recentAccountMaster("2260000" ,"2269999","02",ledgerProductCode,"News Papers And Periodicals",ledgerProductName,c)+ledgerProductCode;    
         break;		
     case "Donations":
         accountNumber= "02"+recentAccountMaster("2270000" ,"2279999","02",ledgerProductCode,"Donations",ledgerProductName,c)+ledgerProductCode;       
         
         break;	
     case "Entertainment And Refreshments":
         accountNumber= "02"+recentAccountMaster("2280000" ,"2289999","02",ledgerProductCode,"Entertainment And Refreshments",ledgerProductName,c)+ledgerProductCode;   
         break;		
     case "Freight And Transport":
         
           accountNumber= "02"+recentAccountMaster("2290000" ,"2299999","02",ledgerProductCode,"Freight And Transport",ledgerProductName,c)+ledgerProductCode;   
         break;		
     case "Air Time Allowance":
      accountNumber= "02"+recentAccountMaster("2300000" ,"2309999","02",ledgerProductCode,"Air Time Allowance",ledgerProductName,c)+ledgerProductCode;      
         
         break;		
     case "Hotel, Boarding And Lodging Expenses":
         
        accountNumber= "02"+recentAccountMaster("2310000" ,"2319999","02",ledgerProductCode,"Hotel, Boarding And Lodging Expenses",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
     case "Legal Expenses":
         
       accountNumber= "02"+recentAccountMaster("2320000" ,"2329999","02",ledgerProductCode,"Legal Expenses",ledgerProductName,c)+ledgerProductCode;     
         break;		
     case "Fuel":
            accountNumber= "02"+recentAccountMaster("2330000" ,"2339999","02",ledgerProductCode,"Fuel",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
     case "Utilities Expenses":
         
             accountNumber= "02"+recentAccountMaster("2340000" ,"2349999","02",ledgerProductCode,"Utilities Expenses",ledgerProductName,c)+ledgerProductCode; 
         
         break;		
     case "Provision For Bad And Doubtful Debts Expense":
         
         accountNumber= "02"+recentAccountMaster("2350000" ,"2359999","02",ledgerProductCode,"Provision For Bad And Doubtful Debts Expense",ledgerProductName,c)+ledgerProductCode;   
         
         break;		
     case "Rent Expense":
         
        accountNumber= "02"+recentAccountMaster("2360000" ,"2369999","02",ledgerProductCode,"Rent Expense",ledgerProductName,c)+ledgerProductCode;    
         break;		
     case "Bad Debts Expense":
         accountNumber= "02"+recentAccountMaster("2370000" ,"2379999","02",ledgerProductCode,"Bad Debts Expense",ledgerProductName,c)+ledgerProductCode;    
         
         break;		
     case "Car Maintainance And Repairs":
     accountNumber= "02"+recentAccountMaster("2380000" ,"2389999","02",ledgerProductCode,"Car Maintainance And Repairs",ledgerProductName,c)+ledgerProductCode;       
         
         break;		
     case "Provision for Depreciation":
        accountNumber= "02"+recentAccountMaster("2390000" ,"2399999","02",ledgerProductCode,"Provision for Depreciation",ledgerProductName,c)+ledgerProductCode;    
         
         break;		
     case "Office Maintainance Expense":
       accountNumber= "02"+recentAccountMaster("2400000" ,"2409999","02",ledgerProductCode,"Office Maintainance Expense",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
     case "Consultancy":
        accountNumber= "02"+recentAccountMaster("2410000" ,"2419999","02",ledgerProductCode,"Consultancy",ledgerProductName,c)+ledgerProductCode;   
         
         
         break;		
     case "Loan Recovery Expenses":
          accountNumber= "02"+recentAccountMaster("2420000" ,"2429999","02",ledgerProductCode,"Loan Recovery Expenses",ledgerProductName,c)+ledgerProductCode;    
         
         break;	
        

     case "Stationery And Printing":
         
      accountNumber= "02"+recentAccountMaster("2430000" ,"2439999","02",ledgerProductCode,"Stationery And Printing",ledgerProductName,c)+ledgerProductCode;      
         
         break;	
         
     case "Lunch Allowance":
      accountNumber= "02"+recentAccountMaster("2440000" ,"2449999","02",ledgerProductCode,"Lunch Allowance",ledgerProductName,c)+ledgerProductCode;     
         break;		
     case "Telephone And Internet":
         accountNumber= "02"+recentAccountMaster("2450000" ,"2459999","02",ledgerProductCode,"Telephone And Internet",ledgerProductName,c)+ledgerProductCode;   
         
         break;		
     case "Training Expenditure":
        accountNumber= "02"+recentAccountMaster("2460000" ,"2469999","02",ledgerProductCode,"Training Expenditure",ledgerProductName,c)+ledgerProductCode;   
         
         
         break;		
     case "Lincenses And Permits":
      accountNumber= "02"+recentAccountMaster("2470000" ,"2479999","02",ledgerProductCode,"Lincenses And Permits",ledgerProductName,c)+ledgerProductCode;    
         
         break;		
     case "Parking And Security":
         
          accountNumber= "02"+recentAccountMaster("2480000" ,"2489999","02",ledgerProductCode,"Parking And Security",ledgerProductName,c)+ledgerProductCode;   
         
         break;		
     case "Depreciation Expense":
         
        accountNumber= "02"+recentAccountMaster("2490000" ,"2499999","02",ledgerProductCode,"Depreciation Expense",ledgerProductName,c)+ledgerProductCode;     
         break;		
     case "Loss On Disposal Of Assets":
      accountNumber= "02"+recentAccountMaster("2500000" ,"2509999","02",ledgerProductCode,"Loss On Disposal Of Assets",ledgerProductName,c)+ledgerProductCode;     
         
         break;		
     case "Management Fees":
       accountNumber= "02"+recentAccountMaster("2510000" ,"2519999","02",ledgerProductCode,"Management Fees",ledgerProductName,c)+ledgerProductCode;  
         break;		
     case "AGM Expenses":
         
        accountNumber= "02"+recentAccountMaster("2520000" ,"2529999","02",ledgerProductCode,"AGM Expenses",ledgerProductName,c)+ledgerProductCode;     
         break;	
         
         
      case "Salaries And Wages":
        accountNumber= "02"+recentAccountMaster("2530000" ,"2539999","02",ledgerProductCode,"Salaries And Wages",ledgerProductName,c)+ledgerProductCode;      
          
          
          break;		
      case "Bonus":
         accountNumber= "02"+recentAccountMaster("2540000" ,"2549999","02",ledgerProductCode,"Bonus",ledgerProductName,c)+ledgerProductCode;     
          break;		
      case "PAYEE":
         accountNumber= "02"+recentAccountMaster("2550000" ,"2559999","02",ledgerProductCode,"PAYEE",ledgerProductName,c)+ledgerProductCode;      
          
          break;		
      case "Board Sitting Allowances":
          
       accountNumber= "02"+recentAccountMaster("2560000" ,"2569999","02",ledgerProductCode,"Board Sitting Allowances",ledgerProductName,c)+ledgerProductCode;     
          
          break;		
      case "Employee Allowances":
         accountNumber= "02"+recentAccountMaster("2570000" ,"2579999","02",ledgerProductCode,"Employee Allowances",ledgerProductName,c)+ledgerProductCode;    
          
          break;		
      case "Directors Allowances":
        accountNumber= "02"+recentAccountMaster("2580000" ,"2589999","02",ledgerProductCode,"Directors Allowances",ledgerProductName,c)+ledgerProductCode;    
          
          break;		
      case "Contribution To Retirement Fund":
         accountNumber= "02"+recentAccountMaster("2590000" ,"2599999","02",ledgerProductCode,"Contribution To Retirement Fund",ledgerProductName,c)+ledgerProductCode;     
          
          break;
          
      case "Medical Allowance":
                accountNumber= "02"+recentAccountMaster("2600000" ,"2609999","02",ledgerProductCode,"Medical Allowance",ledgerProductName,c)+ledgerProductCode;  
          
          break;		
      case "NSSF Contributions":
            accountNumber= "02"+recentAccountMaster("2610000" ,"2619999","02",ledgerProductCode,"NSSF Contributions",ledgerProductName,c)+ledgerProductCode; 
          break;		
    case "Interest Expense":
        accountNumber= "02"+recentAccountMaster("2620000" ,"2629999","02",ledgerProductCode,"Interest Expense",ledgerProductName,c)+ledgerProductCode;   
        
        break;		
    case "Bank Charges":
         accountNumber= "02"+recentAccountMaster("2630000" ,"2639999","02",ledgerProductCode,"Bank Charges",ledgerProductName,c)+ledgerProductCode;   
        
        break;	
        
    case "Start Up Expenses":
        accountNumber= "02"+recentAccountMaster("2640000" ,"2649999","02",ledgerProductCode,"Start Up Expenses",ledgerProductName,c)+ledgerProductCode;    
        
        break;	
        
        
    case "Lending License":
         accountNumber= "02"+recentAccountMaster("2650000" ,"2659999","02",ledgerProductCode,"Lending License",ledgerProductName,c)+ledgerProductCode;    
        break;		
    case "Company Stamps":
        
         accountNumber= "02"+recentAccountMaster("2660000" ,"2669999","02",ledgerProductCode,"Company Stamps",ledgerProductName,c)+ledgerProductCode;    
        break;		
    case "Unrealized Exchange Loss":
       accountNumber= "02"+recentAccountMaster("2670000" ,"2679999","02",ledgerProductCode,"Unrealized Exchange Loss",ledgerProductName,c)+ledgerProductCode;    
        
        break;	
         case "Return On Investment Expense":
       accountNumber= "02"+recentAccountMaster("2770000" ,"2779999","02",ledgerProductCode,"Return On Investment Expense",ledgerProductName,c)+ledgerProductCode;    
        
        break;	
    case "Claims Admitted During The Year":
     accountNumber= "02"+recentAccountMaster("2680000" ,"2689999","02",ledgerProductCode,"Claims Admitted During The Year",ledgerProductName,c)+ledgerProductCode;    
        
        break;		
    case "Premiums Returned To Insured":
      accountNumber= "02"+recentAccountMaster("2690000" ,"2699999","02",ledgerProductCode,"Premiums Returned To Insured",ledgerProductName,c)+ledgerProductCode;       
        
        
        break;		
    case "Reserves For Unexpired Risk Carried Forward":
        
        accountNumber= "02"+recentAccountMaster("2700000" ,"2709999","02",ledgerProductCode,"Reserves For Unexpired Risk Carried Forward",ledgerProductName,c)+ledgerProductCode;    
        break;		
    case "Agency Expenses":
        
       accountNumber= "02"+recentAccountMaster("2710000" ,"2719999","02",ledgerProductCode,"Agency Expenses",ledgerProductName,c)+ledgerProductCode;    
        
        break;		
    case "Other Expenses Related To Short Term Business":
      accountNumber= "02"+recentAccountMaster("2720000" ,"2729999","02",ledgerProductCode,"Other Expenses Related To Short Term Business",ledgerProductName,c)+ledgerProductCode;    
         
                    
            
            
        break;		
  case "Income Tax Expense":
       accountNumber= "02"+recentAccountMaster("2730000" ,"2739999","02",ledgerProductCode,"Income Tax Expense",ledgerProductName,c)+ledgerProductCode;   
      break;		
  case "Dividends paid during the Operating period":
      accountNumber= "02"+recentAccountMaster("2740000" ,"2749999","02",ledgerProductCode,"Dividends paid during the Operating period",ledgerProductName,c)+ledgerProductCode;     
      
      break;		
  case "Retained earnings at start of Operating period":
  accountNumber= "02"+recentAccountMaster("2750000" ,"2759999","02",ledgerProductCode,"Retained earnings at start of Operating period",ledgerProductName,c)+ledgerProductCode;     
      
      break;		
  case "General expense":
  accountNumber= "02"+recentAccountMaster("2760000" ,"2769999","02",ledgerProductCode,"General expense",ledgerProductName,c)+ledgerProductCode;        
      
      break;		
// case "Restatement of retained earnings for corrections of previous period errors":
//  accountNumber= "02"+recentAccountMaster("2770000" ,"2779999","02",ledgerProductCode,"Restatement of retained earnings for corrections of previous period errors",ledgerProductName)+ledgerProductCode;          
//      
//      break;
 case "Restatement of retained earnings for changes in accounting policies":
  accountNumber= "02"+recentAccountMaster("2780000" ,"2789999","02",ledgerProductCode,"Restatement of retained earnings for changes in accounting policies",ledgerProductName,c)+ledgerProductCode;        
      
      break;
    
     case "Compliance And Regulatory Expenses":
  accountNumber= "02"+recentAccountMaster("2790000" ,"2799999","02",ledgerProductCode,"Compliance And Regulatory Expenses",ledgerProductName,c)+ledgerProductCode;        
      
      break;
      
       case "Festive Allowance":
  accountNumber= "02"+recentAccountMaster("2800000" ,"2809999","02",ledgerProductCode,"Festive Allowance",ledgerProductName,c)+ledgerProductCode;        
      
      break;
      
       case "Employee Welfare":
  accountNumber= "02"+recentAccountMaster("2810000" ,"2819999","02",ledgerProductCode,"Employee Welfare",ledgerProductName,c)+ledgerProductCode;        
      
      break;
      
      case "Caveat Charges":
  accountNumber= "02"+recentAccountMaster("2820000" ,"2829999","02",ledgerProductCode,"Caveat Charges",ledgerProductName,c)+ledgerProductCode;        
      
      break;
      
       case "Loans Committee Expenses":
  accountNumber= "02"+recentAccountMaster("2830000" ,"2839999","02",ledgerProductCode,"Loans Committee Expenses",ledgerProductName,c)+ledgerProductCode;        
      
      break;
      
       case "Admin Expenses":
  accountNumber= "02"+recentAccountMaster("2840000" ,"2849999","02",ledgerProductCode,"Admin Expenses",ledgerProductName,c)+ledgerProductCode;        
      
      break;
    }
    
    
                    
                    
    
    
    return accountNumber;
    
    }
    
    
    private String recentAccountMaster(String lowerLedgrMasterCode, String upperMasterCode,String accountType,String ledgerProductCode,String accountMasterName,String ledgerProductName,Component c){
//             JOptionPane.showMessageDialog(c, accountType+lowerLedgrMasterCode+ledgerProductCode+accountMasterName +"2");
        String originalAccountName=accountMasterName;
        
        accountMasterName=accountMasterName.replace(" ", "&");
    
    accountMasterName=accountMasterName.replace("/", "&");
    accountMasterName=accountMasterName.replace("-", "&");
    accountMasterName=accountMasterName.replace("\\", "&");
    accountMasterName=accountMasterName.replace("(", "&");
    accountMasterName=accountMasterName.replace(")", "&");
     accountMasterName=accountMasterName.replace("&&&&", "&");  
     accountMasterName=accountMasterName.replace("&&&", "&"); 
     accountMasterName=accountMasterName.replace("&&", "&");
      accountMasterName=accountMasterName.replace("&", "_");
        int ledgerMasterCode=0;
         try {
 Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
 
 String query = "SELECT MAX(account_master) AS newledgerMaster FROM"+" "+"account_created_store"+" "+"WHERE(account_master>="+"'"+lowerLedgrMasterCode+"'"+"AND account_master<="+"'"+upperMasterCode+"'"+")";

 PreparedStatement ps = cq.prepareStatement(query);
 
            ResultSet rst3 = ps.executeQuery();
          
            while(rst3.next()){
                
               ledgerMasterCode =rst3.getInt("newledgerMaster");
 
             
            }
     
            cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
           
        } catch (SQLException ex) { 
            
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

        }
//           JOptionPane.showMessageDialog(c, accountType+lowerLedgrMasterCode+ledgerProductCode+accountMasterName+"ZZZZ");
//   fios.stringFileWriter(fios.createFileName("test", "testit", upperMasterCode+"trdyr.txt"),ledgerMasterCode+"");      
       
    if((ledgerMasterCode==0)){
        
//                JOptionPane.showMessageDialog(c, "BSANCA"+accountType+lowerLedgrMasterCode+ledgerProductCode);
    if(!testTable("BSANCA"+accountType+lowerLedgrMasterCode+ledgerProductCode)){
        
//        JOptionPane.showMessageDialog(c, accountType+lowerLedgrMasterCode+ledgerProductCode+accountMasterName);

     createMaterAccount(accountType+lowerLedgrMasterCode+ledgerProductCode, accountMasterName,c); 

// fios.stringFileWriter(fios.createFileName("accountManagement", "masterAccounts", accountMasterName+".txt"), accountType+lowerLedgrMasterCode+ledgerProductCode);
 
//  fios.stringFileWriter(fios.createFileName("accountManagement", "masterAccounts", "masterName"+accountType+lowerLedgrMasterCode+ledgerProductCode+".txt"), originalAccountName);
   
  ledgerMasterCode=(parseInt(lowerLedgrMasterCode)+1);
 
    }else{
    
     ledgerMasterCode=(parseInt(lowerLedgrMasterCode)+1);
     
    }  
    }else{
        
     ledgerMasterCode =(ledgerMasterCode+1);
     

    
    }
         
    return ledgerMasterCode+"";
    
    }

   public boolean createAccount(List ad){
   boolean excuted=false;
   fios.stringFileWriter(fios.createFileName("accountManagement", "accountNumber", "accountNumber"+ad.get(7)+".txt"), ad.get(5).toString());
    fios.stringFileWriter(fios.createFileName("accountManagement", "accountName", "accountName"+ad.get(5)+".txt"), ad.get(7).toString());
    
        try {
          Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
           PreparedStatement ps = cq.prepareStatement("INSERT INTO account_created_store VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setObject(1, null);//trn_id:1
            
            ps.setObject(2, getDBCurrentDate());//trn_date:2
            
            ps.setObject(3, ad.get(5).toString().substring(2, 9));//account_master:3

            ps.setObject(4, ad.get(8));//account_purpose:4   //Account Purpose:8

            ps.setObject(5,ad.get(7));//account_name:5  //Account Name:7

            ps.setObject(6, ad.get(5));//account_number:6  //Account Number:5

            ps.setObject(7, ad.get(0));//account_l1:7 //Account cat 1:0

            ps.setObject(8, ad.get(1));//account_l2:8 //Account cat 2:1

            ps.setObject(9, ad.get(2));//account_l3:9 //Account cat 3:2

            ps.setObject(10, ad.get(3));//account_l4:10 //Account cat 4:3

            ps.setObject(11,ad.get(4));//account_l5:11  //Account cat 5:4

            ps.setObject(12, ad.get(6));//running_balance:12  //Account balance:6 

            ps.setObject(13, "New");//account_status:13

            ps.setObject(14,ad.get(10));//ProductCode:14   //Product Code:10

            ps.setObject(15,ad.get(11));//ProductName:15   //Product Name:11

            ps.setObject(16, ad.get(9));//creation_user_id:16  //User Id:9

            ps.setObject(17,getDBCurrentDate());//creation_date:17

            ps.setObject(18, "NB");//creation_time:18

                  
         excuted=  ps.execute();
         cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   
   
   return excuted;
   }
    public String accountBalance(String AccountNumber){
     String balance="";
         try {
  Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
 
 String query = "SELECT running_balance FROM"+" "+"account_created_store"+" "+"WHERE account_number>="+"'"+AccountNumber+"'";
 PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
          
            if(rst3.last()){
                
              balance =rst3.getString("running_balance");
 
             
            }
     
         cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

        }
    
    return balance;
    
    }
    
    
   public String geatDBCurrentTime(){
     
 
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
    

public void feelInactiveAccounts(JTable table2, ArrayList<String> tableTitle){

 try {
     Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);  
        List<List<Object>>    data5c= new ArrayList<>();
            
            String query = "SELECT trn_date,account_name,account_number,account_l5,running_balance,account_status FROM account_created_store WHERE account_status='New'";
            PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
            int k=0;
            while (rst3.next()){
             List   data4x    =new ArrayList();
                data4x.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
                data4x.add(rst3.getString("account_number"));
                data4x.add(rst3.getString("account_name"));
                data4x.add(rst3.getString("account_l5") );
                        data4x.add(parseDouble(df2.format(rst3.getDouble("running_balance"))));
                        data4x.add(rst3.getString("account_status"));
                         data4x.add(false);
                         
                 data5c.add(data4x);   
               k++;
            }
            
            
            modelList= new ListDataModel_1111(data5c, tableTitle);
            
           table2.setModel(modelList);
            
            TableRowSorter<ListDataModel_1111> sorter = new TableRowSorter<>(modelList);
            
            table2.setRowSorter(sorter);
           cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public boolean activateAccount(String AccountNumber,Component c){
 boolean created=false;
 String ledger_balance=accountBalance(AccountNumber);
          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);  
              String createAccount =  "CREATE TABLE IF NOT EXISTS BSANCA"+AccountNumber+ "("
                      + " `trn_id` INT NOT NULL AUTO_INCREMENT, "
                      + " `trn_date` DATE NOT NULL, "
                      + " `narration` VARCHAR(200) NULL, "
                      + " `value_date` DATE NOT NULL, "
                      + " `debit` VARCHAR(50)  NOT NULL DEFAULT 0.0,"
                      + "`credit` VARCHAR(50)  NOT NULL DEFAULT 0.0,"
                      + "  `ledger_balance` VARCHAR(50) NOT NULL DEFAULT 0.0,"
                      + " `credit_account_no` VARCHAR(50) NOT NULL , "
                      + " `credit_account_name` VARCHAR(50) NULL, "
                      + " `tra_ref_number` VARCHAR(50) NULL, "
                      + " `chq_number` VARCHAR(50) NULL, "
                      + " `trn_type` VARCHAR(50) NULL, "
                      + " `staff_id` VARCHAR(50) NULL, "
                      + "`trn_time` TIME NULL,"
                      + "`trn_sq_no` VARCHAR(10) NULL,"
                       + "`account_number` VARCHAR(100) DEFAULT '05502000110',"
                        + "`master_number` VARCHAR(100) DEFAULT '05502000110',"
                      + "`other_one` VARCHAR(100) DEFAULT 'NA',"
                      + "`other_two` VARCHAR(100) DEFAULT 'NA',"
                      + "`other_three` VARCHAR(100) DEFAULT 'NA',"
                      + "PRIMARY KEY (`trn_id`)"
                      + ")"
                      +"ENGINE = InnoDB"+" AUTO_INCREMENT = 00001";
              
              PreparedStatement psx1 = cq.prepareStatement(createAccount);
              psx1.executeUpdate();
               cq.setAutoCommit(true);
                cq.setAutoCommit(false);
              PreparedStatement ps = cq.prepareStatement("INSERT INTO"+" "+"BSANCA"+AccountNumber+" "+"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
              ps.setObject(1, 1000);
              ps.setObject(2, getDBCurrentDate());
              ps.setObject(3, "Opening Balance");
              ps.setObject(4, getDBCurrentDate());
              ps.setObject(5,"-");
              ps.setObject(6, "-");
              ps.setObject(7, ledger_balance);
              ps.setObject(8, "-");
              ps.setObject(9, "-");
              ps.setObject(10,"-");
              ps.setObject(11, "-");
              ps.setObject(12,"-");
              ps.setObject(13, "-");
              ps.setObject(14, geatDBCurrentTime());
              ps.setObject(15, "-");
               ps.setObject(16, "-");
              ps.setObject(17,"-");
              ps.setObject(18, "NA");
              ps.setObject(19, "NA");
              ps.setObject(20, "NA");
             created= ps.execute();
             cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(c, ex.toString());
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
//   createProcedurePreviousBalance(AccountNumber);       
 return created;
} 

public synchronized void createProcedurePreviousBalance(String AccountNumber){
   
    String accountMaster=fmt.getMasterAccountNumber(AccountNumber);
    try {
    Connection cq=quaryObj.createConnection(); 
    cq.setAutoCommit(false);  
    String createAccount = "CREATE PROCEDURE  priviousBalance"+AccountNumber+" "+"(IN accountNumberIn VARCHAR(30),OUT priviousBal VARCHAR(30))\n" +
    " BEGIN\n" +
    " SELECT account_balance FROM BSANCA"+accountMaster+" "+"where account_number=accountNumberIn ORDER BY trn_id DESC LIMIT 1 INTO priviousBal;\n" +
    " IF (priviousBal IS NULL) THEN\n" +
    "SET priviousBal='0.0';\n" +
    " END IF;\n" +
    "END;";

    PreparedStatement psx1 = cq.prepareStatement(createAccount);
    psx1.executeUpdate();
    cq.setAutoCommit(true);
    quaryObj.closeConnection(cq);
    } catch (SQLException ex) {
    Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
    }

}

public boolean createTriggerAccount(String AccountNumber){
    
   String masterAccountNumber=fmt.getMasterAccountNumber(AccountNumber); 
   
     boolean created=false;
    
try {
    
Connection cq=quaryObj.createConnection();  

cq.setAutoCommit(false);


String createAccountTrigger ="CREATE TRIGGER"+" "+"TSANCA"+AccountNumber+" "+"BEFORE INSERT ON"+" "+"BSANCA"+AccountNumber +" "+"FOR EACH ROW BEGIN"+

" "+" IF(NEW.other_one LIKE '%Cr%') THEN \n" +
        
"SET @creditAccount=NEW.account_number;\n" +
        
" SET @debitAccount=NEW.credit_account_no;\n" +

"CALL accountNma(@creditAccount,@accountName);\n" +

"INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)\n" +

" VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);\n" +
        
" END IF;\n" +
        
"IF(NEW.other_one LIKE '%Dr%') THEN \n" +

"SET @creditAccount=NEW.credit_account_no;\n" +

"SET @debitAccount=NEW.account_number;\n" +
        
  "CALL accountNma(@debitAccount,@accountName);\n" +

"INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)\n" +

" VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);\n" +      

"END IF;\n" +



"UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;\n"+

" CALL   updateMaster"+AccountNumber+"(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); \n" +

"END;";



//    createProcedureUpdateMasterBalance(masterAccountNumber,AccountNumber);  
    
    
              PreparedStatement psx1 = cq.prepareStatement(createAccountTrigger);
              
              psx1.executeUpdate();
              
              cq.setAutoCommit(true);
              
          } catch (SQLException ex) {
              
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
              
          }

 return created;


}




public boolean updateStatus (String AccountNumber){

boolean updated=false; int upda=0;

          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String UpdateQuary = "UPDATE account_created_store SET account_status=?  WHERE account_number=?";
              PreparedStatement pstools =cq.prepareStatement(UpdateQuary);
              pstools.setObject(1, "Active");
              
              pstools.setObject(2, AccountNumber);
              
             upda= pstools.executeUpdate();
              
             
            cq.setAutoCommit(true);
        quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
if(upda!=0){
updated=true;
}

return updated;

}

public boolean testTable(String tableName) {
           boolean testT=true;
        try {
     testT= quaryObj.createConnection().getMetaData().getTables(null, null, tableName,  null).next();
        } catch (SQLException ex) {
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
      return  testT;
     
 }
public void feelActiveBudgetAccounts(JTable table2, ArrayList<String> tableTitle){
    int todayYear=cal.get(GregorianCalendar.YEAR);
  
try { 
       Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
            data5= new ArrayList<>();
            
            String query = "SELECT  AccountMaster,BMonthWord,BYear,BAmount from budgetstore WHERE  BYear>="+todayYear+" ORDER BY TrnId ASC";
            PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
            int k=0;
            while (rst3.next()){
                data4    =new ArrayList();
                data4.add(0, rst3.getString("AccountMaster"));
                data4.add(1, rst3.getString("BMonthWord"));
                data4.add(2, rst3.getString("BYear"));
                data4.add(3, rst3.getString("BAmount") );
               
                 data5.add(k, data4);   
               k++;
            }
            
            
            model= new ObjectTableModel( data5, tableTitle);
           table2.setModel(model);
            
            TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
            
            table2.setRowSorter(sorter);
       
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }



}

public void feelActiveAccounts(JTable table2, ArrayList<String> tableTitle){

 try { 
       Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
            data5= new ArrayList<>();
            
            String query = "SELECT trn_date,account_name,account_number,account_l5,running_balance,account_status FROM account_created_store WHERE account_status='Active'";
            PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
            int k=0;
            while (rst3.next()){
                data4    =new ArrayList();
                data4.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rst3.getString("trn_date")));
                data4.add(1, rst3.getString("account_number"));
                data4.add(2, rst3.getString("account_name"));
                data4.add(3, rst3.getString("account_l5") );
                        data4.add(4, parseDouble(df2.format(rst3.getDouble("running_balance"))));
                        data4.add(5, rst3.getString("account_status"));
               
                 data5.add(k, data4);   
               k++;
            }
            
            
            model= new ObjectTableModel( data5, tableTitle);
           table2.setModel(model);
            
            TableRowSorter<ObjectTableModel> sorter = new TableRowSorter<>(model);
            
            table2.setRowSorter(sorter);
       
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public boolean deleteAccount(String accNu){
boolean deleted=false;
fios.deleteFileExistance(fios.createFileName("accountManagement", "accountBalances", "accountRunningBalance"+accNu+".txt"));
fios.deleteFileExistance(fios.createFileName("accountManagement", "accountBalances", "previouslyAdded"+accNu+".txt"));
fios.deleteFileExistance(fios.createFileName("accountManagement", "accountName", "accountName"+accNu+".txt"));
fios.deleteFileExistance(fios.createFileName("accountManagement", "accountNumber", "accountNumber"+this.AccountNameDB(accNu)+".txt"));

          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              
              PreparedStatement psxmts = cq.prepareStatement("DELETE FROM account_created_store WHERE account_number="+accNu);
             deleted= psxmts.execute();
              cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
return deleted;
}
      




public boolean dropAccount(String accNu){
    boolean dropped=false;
    if(testTable("bsanca"+accNu)){

          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              PreparedStatement ps =cq.prepareStatement("DROP TABLE BSANCA"+accNu);
             dropped= ps.execute();
              cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
return dropped;

}
public String accountStatus(String account){
   String status="";
    try {
         Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
  String query = "SELECT account_status FROM account_created_store WHERE account_number="+account;
            PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
   
            while (rst3.next()){
     status=rst3.getString("account_status");

            }
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   
   
    
return status;
}

public boolean createCustomer(List ad) {
   boolean excuted=false; FileInputStream fis=null;
        try {
            Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
          fis=new FileInputStream(ad.get(39).toString());
           PreparedStatement ps =  cq.prepareStatement("INSERT INTO master VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
           ps.setObject(1, null);
           ps.setObject(2, getDBCurrentDate());
        ps.setObject(3, ad.get(0));
           ps.setObject(4,  ad.get(1));
         ps.setObject(5, ad.get(2));
         ps.setObject(6,ad.get(3));
         ps.setObject(7, ad.get(4));
         
         ps.setObject(8, fmt.forDatabaseWithFullYearBeginningWithDate(ad.get(5).toString()));
         
         ps.setObject(9, ad.get(6));
         
         ps.setObject(10, ad.get(7));
         
         
         ps.setObject(11, ad.get(8));
         
          ps.setObject(12,ad.get(9));
          
        ps.setObject(13, ad.get(10));
        
          ps.setObject(14, ad.get(11));
         
         ps.setObject(15, ad.get(12));
         
         ps.setObject(16, ad.get(13));
         
         ps.setObject(17, ad.get(14));
         
          ps.setObject(18,ad.get(15));
          
        ps.setObject(19, ad.get(16));
         ps.setObject(20, ad.get(17));
        ps.setObject(21, ad.get(18));
           ps.setObject(22,  ad.get(19));
         ps.setObject(23, ad.get(20));
         ps.setObject(24,ad.get(21));
         ps.setObject(25, ad.get(22));
         
         ps.setObject(26, ad.get(23));
         
         ps.setObject(27, ad.get(24));
         
         ps.setObject(28, ad.get(25));
         
         
         ps.setObject(29, ad.get(26));
         
          ps.setObject(30,ad.get(27));
          
        ps.setObject(31, ad.get(28));
        
          ps.setObject(32, ad.get(29));
         
         ps.setObject(33, ad.get(30));
         
         ps.setObject(34, ad.get(31));
         
         ps.setObject(35, ad.get(32));
         
          ps.setObject(36,ad.get(33));
          
        ps.setObject(37,fmt.forDatabaseWithFullYearBeginningWithDate( ad.get(34).toString()));
        ps.setObject(38, ad.get(35));
         
         ps.setObject(39, ad.get(36));
         
          ps.setObject(40,ad.get(37));
  
         ps.setObject(41, geatDBCurrentTime());
         
         ps.setObject(42, getDBCurrentDate());
         
        ps.setObject(43,geatDBCurrentTime());
        ps.setObject(44, ad.get(38));
          ps.setBinaryStream(45, (InputStream)fis, (int)ad.get(5).toString().length());         
         excuted=  ps.execute();
           
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }catch (FileNotFoundException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   
   
   return !excuted;
   }





public void searchItem1(JTable  table){

                data5 = new ArrayList<>();  
                column1 = new ArrayList<>();
                column1.add("Date Created");
                column1.add("Account number");
                column1.add("Account Name");
//                column1.add("Sex");
//                column1.add("Home Parish");
                column1.add("Mobile 1");
                column1.add("Email");
//                column1.add("Value of shares");
//                column1.add("Number of shares");
//                column1.add("Approval status");
            
            
            
            
          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT trn_date,account_number,account_name,sex,home_parish,mobile1,email,value_of_shares,number_of_shares,approval_status FROM master";
              PreparedStatement ps = cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              int k=0;
           while(rs.next()) {
                  data4 = new ArrayList<>();
                  data4.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rs.getString("trn_date")));
                  data4.add(1, rs.getString("account_number"));
                  data4.add(2, rs.getString("account_name"));
//                  data4.add(3, rs.getString("sex"));
//                  data4.add(4, rs.getString("home_parish"));
                  data4.add(3, rs.getString("mobile1"));
                  data4.add(4, rs.getString("email"));
//                  data4.add(7, rs.getString("value_of_shares"));
//                  data4.add(8, rs.getString("number_of_shares"));
//                  data4.add(9, rs.getString("approval_status"));
                  
                  data5.add(k, data4);
                  k++;
              }
              model = new ObjectTableModel(data5, column1);
              table.setModel(model);
              cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }


}










public void searchItem(JTable  table,String title, String searchItem){

                data5 = new ArrayList<>();  
                column1 = new ArrayList<>();
                column1.add("Date Created");
                column1.add("Account number");
                column1.add("Account Name");
                column1.add("Sex");
                column1.add("Home Parish");
                column1.add("Mobile 1");
                column1.add("Email");
                column1.add("Value of shares");
                column1.add("Number of shares");
                column1.add("Approval status");
            
            
            
            
          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT trn_date,account_number,account_name,sex,home_parish,mobile1,email,value_of_shares,number_of_shares,approval_status FROM master WHERE"+" "+title+" "+"="+"'"+searchItem+"'";
              PreparedStatement ps = cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              int k=0;
              if (rs.next()) {
                  data4 = new ArrayList<>();
                  data4.add(0, fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rs.getString("trn_date")));
                  data4.add(1, rs.getString("account_number"));
                  data4.add(2, rs.getString("account_name"));
                  data4.add(3, rs.getString("sex"));
                  data4.add(4, rs.getString("home_parish"));
                  data4.add(5, rs.getString("mobile1"));
                  data4.add(6, rs.getString("email"));
                  data4.add(7, rs.getString("value_of_shares"));
                  data4.add(8, rs.getString("number_of_shares"));
                  data4.add(9, rs.getString("approval_status"));
                  
                  data5.add(k, data4);
                  k++;
              }
              model = new ObjectTableModel(data5, column1);
              table.setModel(model);
              cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }


}

public void fillComboSearchItem(JComboBox   box, String searchItem){
                    
              data8 = new ArrayList<>();
          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT DISTINCT"+" "+searchItem+" "+"FROM master";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              int k=0;
             
              while (rs.next()) {
                       
                        data8.add(k, rs.getString(searchItem));
                       k++;  
                      
                    }
               if(k==0){data8.add(k, "Empty Record");} 
                    modelcombo = new MyComboBoxModel(data8);
                     box.setModel(modelcombo);
                     box.setSelectedIndex(0);
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }


}

public void fillComboBoxBudgetAccount(JComboBox   box, String searchItem){
    
                 int n=0; ArrayList finalItems=new ArrayList();   
             data8 = new ArrayList<>();
              
              data8=getAllExistingBudgetItems(searchItem);
              if(data8.isEmpty()){
              data8.add(0, "Empty Record");
            
              }else{
                  
                 while(n<data8.size()){
                 if(!budgetItemAlreadyExists(data8.get(n))){
                 
                 finalItems.add(data8.get(n));
                 
                 }
                n++; 
                 }
              
              }
               if(finalItems.isEmpty()){
              finalItems.add(0, "Empty Record");}
               modelcombo = new MyComboBoxModel(finalItems);
                     box.setModel(modelcombo);
                     box.setSelectedIndex(0);
}

public void fillComboBoxBudgetDatesExistingInBudget(JComboBox   box,String itemsearch){
    
int theNumbers=getTheStartingTrnId(itemsearch);  

ArrayList finalItems=getAllExistingMonths(itemsearch,theNumbers); 
    
   if(finalItems.isEmpty()){
              finalItems.add(0, "Empty Record");}
               modelcombo = new MyComboBoxModel(finalItems);
                     box.setModel(modelcombo);
                     box.setSelectedIndex(0);


}
public void fillComboBoxBudgetAccountExistingInBudget(JComboBox   box){
   
    List itemsFortThisYear=getAllItemsForThisYear();  ArrayList finalItems=new ArrayList(); 
    
    for(Object account:itemsFortThisYear){
        
   if(thisItemQualifies(account.toString())){
    
           
                  
               
                 
                 finalItems.add(account.toString());
                
              
              
   }   
    }
               if(finalItems.isEmpty()){
              finalItems.add(0, "Empty Record");}
               modelcombo = new MyComboBoxModel(finalItems);
                     box.setModel(modelcombo);
                     box.setSelectedIndex(0);
                     
}

public void fillComboBoxBudgetYeearsExistingInBudget(JComboBox   box,String itemsearch){

int theNumbers=getTheStartingTrnId(itemsearch);  

ArrayList finalItems=getAllExistingYears(itemsearch,theNumbers); 
    
   if(finalItems.isEmpty()){
              finalItems.add(0, "Empty Record");}
               modelcombo = new MyComboBoxModel(finalItems);
                     box.setModel(modelcombo);
                     box.setSelectedIndex(0);




}
public boolean thisItemQualifies(String theItem){

     int todayYear=cal.get(GregorianCalendar.YEAR);
     int todayMonth =cal.get(GregorianCalendar.MONTH)+1;
    boolean itemQualifies=false; int count=0;

try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "select COUNT(AccountMaster) AS numberItem from budgetStore where (( AccountMaster="+"'"+theItem+"'"+" ) AND ( BYear="+"'"+todayYear+"'"+ "AND  BMonthNumber="+"'"+todayMonth+"'"+ ") )";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
          
             
              while (rs.next()) {
                       
                    count=  rs.getInt("numberItem");
                   
                      
                    }
              
                    
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
if(count>0){
itemQualifies=true;
}



return itemQualifies;
}
private boolean budgetItemAlreadyExists(String theItem){
boolean itemAlreadyExists=false; int count=0;

try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "select COUNT(AccountMaster) AS numberItem from budgetStore where AccountMaster="+"'"+theItem+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              int k=0;
             
              while (rs.next()) {
                       
                    count=  rs.getInt("numberItem");
                       k++;  
                      
                    }
              
                    
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
if(count>0){
itemAlreadyExists=true;
}

return itemAlreadyExists;
}



public ArrayList getAllExistingBudgetItems(String searchItem){

      ArrayList  data8C = new ArrayList<>(); int k=0;
      if(accountStoreContaines(searchItem)){
try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "select distinct account_l5 from account_created_store where account_l2="+"'"+searchItem+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
             
             
              while (rs.next()) {
                       
                        data8C.add(k, rs.getString("account_l5"));
                       k++;  
                      
                    }
              
                    
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
      }else{
data8C.add(0, "Empty Record");
}

return data8C;

}

public boolean accountStoreContaines(String searchItem){
boolean in=false; int k=0;
try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "select COUNT(account_l5) accouts from account_created_store where account_l2="+"'"+searchItem+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
             
             
              if (rs.next()) {
                       
                   k= rs.getInt("accouts");
                  
                      
                    }
              
                    
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
if(k>0){
in=true;
}

return in;

}



public boolean updateCustomerInfo(List ad){
   boolean excuted=false;
        try {
            Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
           String UpdateQuary = "UPDATE master SET title=?,first_name=?,sir_name=?,sex=?,birth_date=?,marital_status=?,"
                    + "highest_educ_level=?,home_parish=?,centre=?,hiika=?,mobile1=?,"
                    + "mobile2=?,email=?,kampala_residence=?,occupation=?,employer=?,"
                    + "category_of_membership=?,"
                    + "kin_first_name=?,"
                    + "kin_sir_name=?,kin_mobile_1=?,kin_mobile_2=?,kin_email=?,"
                    + "notes=?,introducing_capacity=?,intro_first_name=?,"
                    + "intro_sir_name=?,intro_sacco_status=?,intro_mob_1=?,"
                    + "intro_mob_2=?,intro_sacco_member_since=?,approval_status=?,"
                    + "approval_date=?,approved_by=?,"
                    + "account_name=?,"
                    + "last_updated_date=?,last_updated_time=? WHERE account_number=?";
           
            PreparedStatement ps =cq.prepareStatement(UpdateQuary);
        ps.setObject(1, ad.get(0));
           ps.setObject(2,  ad.get(1));
         ps.setObject(3, ad.get(2));
         ps.setObject(4,ad.get(3));
         ps.setObject(5, fmt.forDatabaseWithFullYearBeginningWithDate(ad.get(4).toString()));
         
         ps.setObject(6, ad.get(5));
         
         ps.setObject(7, ad.get(6));
         
         ps.setObject(8, ad.get(7));
         
         
         ps.setObject(9, ad.get(8));
         
          ps.setObject(10,ad.get(9));
          
        ps.setObject(11, ad.get(10));
        
          ps.setObject(12, ad.get(11));
         
         ps.setObject(13, ad.get(12));
         
         ps.setObject(14, ad.get(13));
         
         ps.setObject(15, ad.get(14));
         
          ps.setObject(16,ad.get(15));
          
        ps.setObject(17, ad.get(16));
         ps.setObject(18, ad.get(17));
        ps.setObject(19, ad.get(18));
           ps.setObject(20,  ad.get(19));
         ps.setObject(21, ad.get(20));
         ps.setObject(22,ad.get(21));
         ps.setObject(23, ad.get(22));
         
         ps.setObject(24, ad.get(23));
         
         ps.setObject(25, ad.get(24));
         
         ps.setObject(26, ad.get(25));
         
         
         ps.setObject(27, ad.get(26));
         
          ps.setObject(28,ad.get(27));
          
        ps.setObject(29, ad.get(28));
        
          ps.setObject(30, ad.get(29));
         
         ps.setObject(31, ad.get(30));
         
         ps.setObject(32, fmt.forDatabaseWithFullYearBeginningWithDate(ad.get(31).toString()));
         
         ps.setObject(33, ad.get(32));
       
           ps.setObject(34,ad.get(34));

            ps.setObject(35, getDBCurrentDate());
         
        ps.setObject(36,geatDBCurrentTime());
        ps.setObject(37, ad.get(33));
                  
         excuted=  ps.execute();
           
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   updateAccountName(ad.get(33).toString(),ad.get(34).toString());
   
   return excuted;
   }
public Map<Integer, Object>updatedInfor(String accountNumber){

Map<Integer, Object> data=null;
 try {
     Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
            PreparedStatement psxmt =cq.prepareStatement("SELECT * FROM master WHERE account_number="+accountNumber);
            ResultSet rs = psxmt.executeQuery();
            if (rs.next()) {
                     data=new HashMap<>();
               data.put(0, rs.getString("title"));
               data.put(1,rs.getString("first_name"));
               data.put(2,rs.getString("sir_name"));
               data.put(3,rs.getString("sex"));
               data.put(4,fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rs.getString("birth_date")));
               data.put(5,rs.getString("marital_status"));
               data.put(6,rs.getString("highest_educ_level"));
               data.put(7,rs.getString("home_parish"));
               data.put(8,rs.getString("centre"));
               data.put(9,rs.getString("hiika"));
               data.put(10,rs.getString("mobile1"));
               data.put(11,rs.getString("mobile2"));
               data.put(12,rs.getString("email"));
               data.put(13,rs.getString("kampala_residence"));
               data.put(14,rs.getString("occupation"));
               data.put(15,rs.getString("employer"));
               data.put(16,rs.getString("category_of_membership"));
               data.put(17,rs.getString("value_of_shares"));
               data.put(18,rs.getString("number_of_shares"));
              data.put(19,rs.getString("kin_first_name"));
              data.put(20,rs.getString("kin_sir_name"));
              data.put(21,rs.getString("kin_mobile_1"));
              data.put(22,rs.getString("kin_mobile_2"));
              data.put(23,rs.getString("kin_email"));
              data.put(24,rs.getString("notes"));
              data.put(25,rs.getString("introducing_capacity"));
              data.put(26,rs.getString("intro_first_name"));
              data.put(27,rs.getString("intro_sir_name"));
              data.put(28,rs.getString("intro_sacco_status"));
              data.put(29,rs.getString("intro_mob_1"));
              data.put(30,rs.getString("intro_mob_2"));
              data.put(31,rs.getString("intro_sacco_member_since"));
              data.put(32,rs.getString("approval_status"));
              data.put(33,fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rs.getString("approval_date")));
              data.put(34,rs.getString("approved_by"));
              data.put(35,rs.getString("account_number"));
              data.put(36,rs.getString("account_name"));
                data.put(37,rs.getBlob("UserPhoto"));
    cq.setAutoCommit(true);
                quaryObj.closeConnection(cq);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }



return data;
}



public boolean updateAccountName(String accountNumber,String name){
   boolean excuted=false;
        try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
           String UpdateQuary = "UPDATE account_created_store SET account_name=?  WHERE account_number=?";
           
            PreparedStatement ps = cq.prepareStatement(UpdateQuary);
        ps.setObject(1, name);
        ps.setObject(2, accountNumber);
         excuted=  ps.execute();
           
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   
   
   return excuted;
   }

public boolean deletecustomer(String accNu){
boolean deleted=false;
          try {
            Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
              
              PreparedStatement psxmts = cq.prepareStatement("DELETE FROM master WHERE account_number="+accNu);
             deleted= psxmts.execute();
             cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

return deleted;
}

public synchronized void createMaterAccount(String accountNumber, String accountName,Component c){
    
//fios.stringFileWriter(fios.createFileName("test", "testit", accountNumber+"statusy.txt"), accountNumber+";"+accountName);
    data=new ArrayList<>();
data.add(0, accountName);
data.add(1, accountNumber);
data.add(2, "0.0");
data.add(3, fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt")));
          try {
                Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
              String createAccount =  "CREATE TABLE IF NOT EXISTS BSANCA"+accountNumber+ "("
                      + " `trn_id` INT NOT NULL AUTO_INCREMENT, "
                      + " `trn_date` DATE NOT NULL DEFAULT '1979-01-01',"
                      + " `value_date` DATE NOT NULL DEFAULT '1979-01-01',"
                      + " `account_name` VARCHAR(200) NOT NULL DEFAULT 'New Ledger'," 
                      + " `account_number` VARCHAR(50) NOT NULL DEFAULT '01100000010', "
                      + " `account_balance` VARCHAR(50) NOT NULL DEFAULT '0.0', "
                      + " `master_balance` VARCHAR(50) NOT NULL DEFAULT '0.0', "
                      + " `staff_id` VARCHAR(10) NOT NULL DEFAULT  '10000', "
                      + " PRIMARY KEY (`trn_id`)"
                      + ")"
                      +"ENGINE = InnoDB"+" AUTO_INCREMENT = 00001";
              
              PreparedStatement psx1 =cq.prepareStatement(createAccount);
              psx1.executeUpdate();
              
              PreparedStatement ps = cq.prepareStatement("INSERT INTO"+" "+"BSANCA"+accountNumber+" "+"VALUES(?,?,?,?,?,?,?,?)");
              ps.setObject(1, null);
              ps.setObject(2, getDBCurrentDate());
              ps.setObject(3, getDBCurrentDate());
              ps.setObject(4, accountName);
              ps.setObject(5, accountNumber);
              ps.setObject(6,"0.0");
              ps.setObject(7, "0.0");
              ps.setObject(8, fios.stringFileReader(fios.createFileName("logOut", "completeShutDown", "activeUserId.txt")));
              ps.execute();
              cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
//    create(accountNumber);      
          
createAccountMaster(data);

//createProcedureCurrentMasterBalance(accountNumber);


}

public synchronized void createProcedureCurrentMasterBalance(String masterAccount){

 try {
                Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
            String theProcedure ="CREATE PROCEDURE  currentMasterBalance"+masterAccount+"(OUT theBalance VARCHAR(30))\n" +
            " BEGIN\n" +

            " SELECT  master_balance FROM BSANCA"+masterAccount+" "+"ORDER BY trn_id DESC LIMIT 1 INTO theBalance;\n" +
                    
            "END;";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
  cq.setAutoCommit(false);
            String theProcedure2 ="CREATE PROCEDURE `accountNma`(IN accountNumber VARCHAR(30),OUT accountName VARCHAR(30))\n" +
            " BEGIN\n" +

            " SELECT account_name FROM account_created_store where account_number=accountNumber INTO accountName;\n" +
                    
            "END;";
              
              PreparedStatement psx1z =cq.prepareStatement(theProcedure2);
              psx1z.executeUpdate();
 cq.setAutoCommit(true);
 
// 
// DELIMITER ;;
//CREATE DEFINER=`root`@`localhost` PROCEDURE `accountNma`(IN accountNumber VARCHAR(30),OUT accountName VARCHAR(30))
//BEGIN
// SELECT account_name FROM account_created_store where account_number=accountNumber INTO accountName;
//     END ;;
//DELIMITER ;
              quaryObj.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

}

public synchronized void createProcedureUpdateMasterBalance(String masterAccount,String accountNumber){

try {
                Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
String theProcedure ="CREATE PROCEDURE updateMaster"+accountNumber+" "+"(IN TrnDate DATE,IN accountNumber VARCHAR(20),IN NewLedgerBalance VARCHAR(20),IN StaffId VARCHAR(8))\n" +
"BEGIN\n" +
"CALL priviousBalance"+accountNumber+" "+"(accountNumber,@previouslyAdded);\n" +

"CALL currentMasterBalance"+masterAccount+" "+"(@currentlAdded);\n" +

"CALL accountNma(accountNumber,@accountName);\n" +
		
" SET @newMasterBalance=(@currentlAdded-@previouslyAdded)+NewLedgerBalance;\n" +

"INSERT INTO BSANCA"+masterAccount+" "+"(trn_id,trn_date,value_date,account_name,account_number,account_balance,master_balance,staff_id) \n" +

"VALUES (null, TrnDate,TrnDate,@accountName,accountNumber,NewLedgerBalance,@newMasterBalance,StaffId); \n" +
"END;";
              
              PreparedStatement psx1 =cq.prepareStatement(theProcedure);
              psx1.executeUpdate();
 cq.setAutoCommit(true);
              quaryObj.closeConnection(cq);
              
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

}

 
public void createAccountMaster(List data){


   try {
           quaryObj.createConnection().setAutoCommit(false);
           PreparedStatement ps =  quaryObj.createConnection().prepareStatement("INSERT INTO account_master_store VALUES(?,?,?,?,?,?,?)");
         ps.setObject(1, null);
           ps.setObject(2, getDBCurrentDate());
             ps.setObject(3, getDBCurrentDate());
        ps.setObject(4, data.get(0));
           ps.setObject(5,  data.get(1));
       ps.setObject(6,  data.get(2));
          ps.setObject(7,  data.get(3));
       ps.execute();
           
           quaryObj.createConnection().setAutoCommit(true);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }






}


public String AccountMaster(String masterName){
     
 String master="";
 
         try {
   Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);
 
 String query = "SELECT account_number FROM account_master_store WHERE account_name="+"'"+masterName+"'";
 PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
          
            while(rst3.next()){
                
               master =rst3.getString("account_number");
 
             
            }
     
            cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

        }
    
      fios.stringFileWriter(fios.createFileName("sharesAvailable","shareValues", "jazz.txt"), master);
    return master;
    
    }






public String masterBalance(String masterAccount){
String accountMas="";
    try {
Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
 
 String query = "SELECT running_balance FROM account_master_store WHERE account_number="+masterAccount;
 PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
          
            while(rst3.next()){
                
            accountMas =rst3.getString("running_balance");
 
             
            }
     
               cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

        }

return accountMas;

}

public String masterDummyName2(String accountNumberMaster){
String accountMas="";
    try {
 Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false);  
 
 String query = "SELECT account_l5 FROM account_created_store WHERE account_number="+"'"+accountNumberMaster+"'";
 PreparedStatement ps = cq.prepareStatement(query);
            ResultSet rst3 = ps.executeQuery();
          
            while(rst3.next()){
                
            accountMas =rst3.getString("account_l5");
 
             
            }
     
         cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
        } catch (SQLException ex) { 
            Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);

        }

return accountMas;

}
public void adminNames(JComboBox box){
ArrayList data4g = new ArrayList<>();


        try {
        Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
            PreparedStatement ps =  cq.prepareStatement("SELECT  account_name FROM log_in");
            ResultSet rs = ps.executeQuery();
            int a = 0;
           
           
                    while (rs.next()) {
                         String accountName= rs.getString("account_name");
     
                          data4g.add(a, accountName);
                        a++;
                    }
            
            if(data4g.size()==0){data4g.add(a, "XXXX"+a);}
            
                    modelcombo = new MyComboBoxModel(data4g);
                    box.setModel(modelcombo);
             cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq);
        }catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
        }

}


 public String AccountNameDB(String accountNumber){

 String nam = "";
          try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT  account_name FROM  account_created_store WHERE account_number="+accountNumber;
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
                    nam= rs.getString("account_name");
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }

return nam;
 }

public   boolean createBudgetItem(List ad){
boolean created=false;
        try {

            Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
           PreparedStatement ps =  cq.prepareStatement("INSERT INTO budgetStore VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
         ps.setObject(1, null);
        ps.setObject(2, getDBCurrentDate());
           ps.setObject(3,  ad.get(0));
         ps.setObject(4, ad.get(1));
         ps.setObject(5, ad.get(2));
         
         ps.setObject(6, ad.get(3));
         
        ps.setObject(7,ad.get(4));
        
        ps.setObject(8,ad.get(5));
 
          
        ps.setObject(9, "N/A");
        
             ps.setObject(10, "N/A");
         
         ps.setObject(11, "N/A");
         
        ps.setObject(12,"N/A");
           ps.execute();
           created=true;
        cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   
  return created; 
  
   }


private List getAllItemsForThisYear(){
List items=new ArrayList();

int thisYear= cal.get(GregorianCalendar.YEAR);

try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT AccountMaster FROM  budgetstore WHERE BYear>="+thisYear;
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            items.add(rs.getString("AccountMaster"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }





return items;
}

public int getTheStartingTrnId(String theItem){

 int todayYear=cal.get(GregorianCalendar.YEAR);
     int todayMonth =cal.get(GregorianCalendar.MONTH)+1;
    int startingID=0;

try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "select TrnId from budgetStore where (( AccountMaster="+"'"+theItem+"'"+" ) AND ( BYear="+"'"+todayYear+"'"+ "AND  BMonthNumber="+"'"+todayMonth+"'"+ ") )";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
          
             
              while (rs.next()) {
                       
                    startingID=  rs.getInt("TrnId");
                   
                      
                    }
              
                    
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }



return startingID;
    
}
public ArrayList getAllExistingMonths(String accountMaster, int theNumbers){

ArrayList items=new ArrayList();



try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT BMonthWord FROM  budgetstore WHERE TrnId>="+theNumbers+" AND AccountMaster="+"'"+accountMaster+"'";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            items.add(rs.getString("BMonthWord"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }





return items;


}

public ArrayList getAllExistingYears(String accountMaster, int theNumbers){

ArrayList items=new ArrayList();



try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT DISTINCT BYear FROM  budgetstore WHERE TrnId>="+theNumbers+" AND AccountMaster="+"'"+accountMaster+"'";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              while (rs.next()) {
                       
            items.add(rs.getString("BYear"));
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }





return items;


}



public boolean updateBudgetOnAmonth(List ad){
   boolean excuted=false;
        try {
            Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
           String UpdateQuary = "UPDATE  budgetstore SET BAmount=? WHERE (AccountMaster=? AND  (BMonthWord=? AND BYear=?))";
            PreparedStatement ps =cq.prepareStatement(UpdateQuary);
        ps.setObject(1, ad.get(1));
           ps.setObject(2,  ad.get(0));
         ps.setObject(3, ad.get(2));
         ps.setObject(4,ad.get(3));
      
      ps.execute();
           excuted=true;
           cq.setAutoCommit(true);
           quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
 
   
   return excuted;
   }





public String getInitialAmount(List deatails){

String theAmount="";



try {
             Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
                   String query = "SELECT  BAmount FROM  budgetstore WHERE  AccountMaster="+"'"+deatails.get(0).toString()+"'"+"AND (BMonthWord="+"'"+deatails.get(1).toString()+"'"+" AND BYear="+deatails.get(2).toString()+")";
             
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
              
             
              if (rs.first()) {
                       
         theAmount=rs.getString("BAmount");
                      
                  
                    }
             
                     cq.setAutoCommit(true);
             
           quaryObj.closeConnection(cq); 
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }





return theAmount;


}
public void fillOComboProductNameO(JComboBox   box){
                    
             List data8c = new ArrayList<>();
             
          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT ProductName FROM ledgerproduct";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
            
             
              while (rs.next()) {
                       
                        data8c.add(rs.getString("ProductName"));
                     
                      
                    }
               
              
               if(data8c.isEmpty()){data8c.add("Empty Record");} 
                    comboList = new listComboBoxModel(data8c);
                     box.setModel(comboList);
                     box.setSelectedIndex(0);
                  
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
          
   

}


public void fillComboProductName(JComboBox   box, String LedgerName){
                    
             List data8c = new ArrayList<>();
             
          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT ProductName FROM ledgerproduct";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
            
             
              while (rs.next()) {
                       
                        data8c.add(rs.getString("ProductName"));
                     
                      
                    }
               List dData8c = new ArrayList<>();
               
               
               
               for(Object productItem:data8c){
               if(!productAlreadyExists(productItem.toString(),LedgerName)){
               dData8c.add(productItem);
               }
               
               }
              
               if(dData8c.isEmpty()){dData8c.add("Empty Record");} 
                    comboList = new listComboBoxModel(dData8c);
                     box.setModel(comboList);
                     box.setSelectedIndex(0);
                  
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
          
   

}

public String getProductName(String LedgerName){
                    
             List data8c = new ArrayList<>();
             
          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT ProductName FROM ledgerproduct";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
            
             
              while (rs.next()) {
                       
                        data8c.add(rs.getString("ProductName"));
                     
                      
                    }

                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
       List dData8c = new ArrayList<>();
               
               
               
               for(Object productItem:data8c){
               if(!productAlreadyExists(productItem.toString(),LedgerName)){
               dData8c.add(productItem);
               }
               
               }
              
               if(dData8c.isEmpty()){dData8c.add("Empty Record");}      
   
return dData8c.get(0).toString();
}



public void fillLedgerNames(JComboBox   box){
                    
             List data8c = new ArrayList<>();
          try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT account_name FROM account_created_store WHERE account_l5="+"'"+"Customer Deposits"+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
            
             
              while (rs.next()) {
                       
                        data8c.add(rs.getString("account_name"));
                     
                      
                    }
               if(data8c.isEmpty()){data8c.add("Empty Record");} 
                    comboList = new listComboBoxModel(data8c);
                     box.setModel(comboList);
                     box.setSelectedIndex(0);
                  
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }
          
   

}

private boolean productAlreadyExists(String productName,String ledgerName){
boolean isThere=false; int produc=0;
try {
              Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
              String query = "SELECT COUNT(ProductName) AS products FROM account_created_store WHERE ProductName="+"'"+productName+"'"+"  AND account_name="+"'"+ledgerName+"'";
              PreparedStatement ps =  cq.prepareStatement(query);
             
              ResultSet rs = ps.executeQuery();
            
             
              while (rs.next()) {
                       
           produc=rs.getInt("products");
                     
                      
                    }
               if(produc>0){isThere=true;} 
                 
                  
                     cq.setAutoCommit(true);
                     quaryObj.closeConnection(cq);
          } catch (SQLException ex) {
              Logger.getLogger(AccountNumberCreationDataBase.class.getName()).log(Level.SEVERE, null, ex);
          }



return isThere;

}















  




public boolean createLedgerProduct(List ad){
   boolean excuted=false;
 
    
        try {
          Connection cq=quaryObj.createConnection(); 
          cq.setAutoCommit(false); 
           PreparedStatement ps = cq.prepareStatement("INSERT INTO ledgerproduct VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
         ps.setObject(1, null); //TrnId:1
         
        ps.setObject(2, getDBCurrentDate()); //TrnDate:2
        
         ps.setObject(3, ad.get(0));//ProductName:3//Product Name:0
         
         ps.setObject(4, ad.get(1));// ProductCode:4//Product Code:1
         
         ps.setObject(5,ad.get(2));// MinmumLedgerBalance:5 //Min Ledger Bal:2
         
         ps.setObject(6, ad.get(3)); //MinmumBalanceForWithdraw:6//Min Ledger Bal Withdraw:3
         
         ps.setObject(7, ad.get(4)); //MinmumBalanceToBorrow:7//Min Ledger Bal Borrow:4 
         
         ps.setObject(8, ad.get(5)); //MinmumMonthForWithdraw:8 //Min Mnth Withdraw:5
         
         ps.setObject(9, ad.get(6));// MinmumMonthBorrow:9//Min Mnth Borrow:6
         
         ps.setObject(10, ad.get(7)); //InterestGainedAmount:10  //Interest Gained-Amnt:7
         
          ps.setObject(11,ad.get(8));// InterestGainedPercent:11 //Interest Gained(%):8
          
        ps.setObject(12, ad.get(9)); //FrequencyOfInterstGain:12 //Freq Gained:9

         ps.setObject(13,ad.get(10));// InterestChargedAmount:13 //Interest Charged-Amnt :10
        ps.setObject(14,ad.get(11)); //InterestChargedPercent:14//Interest Charged(%):11
        ps.setObject(15, ad.get(9));// FrequencyOfInterstCharged:15//Freq Charged:12
       ps.setObject(16,"N/A");
        ps.setObject(17, "N/A");
           ps.setObject(18, "N/A");  
            ps.setObject(19, "N/A");
         excuted=  ps.execute();
         cq.setAutoCommit(true);
         quaryObj.closeConnection(cq);
       } catch (SQLException ex) {
           Logger.getLogger(loanDatabaseQuaries.class.getName()).log(Level.SEVERE, null, ex);
       }
   
   
   return excuted;
   }













































































































 
 
 
 
 
 
 
 
















    
}
