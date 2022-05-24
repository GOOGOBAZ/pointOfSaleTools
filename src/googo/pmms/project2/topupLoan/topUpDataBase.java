/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.topupLoan;

import java.util.Date;

/**
 *
 * @googobazi
 */
public interface topUpDataBase {

  public Double getPrincipalInstalment(int trnID);
    public Double getInterestInstalment(int trnID);
    public Double getInstalmentAmount(int trnID);
     public Double getBeginningBal(int trnID);
     public Double getClosingBal(int trnID);
    public Date getInstalmentDueDate(int trnID);
    public Date getInstalmentPaidDate(int trnID);
    
    public String getTrnId();
 public Date getTrnDate();
public String getLoanId(); 
public String getTotalNumberIntalments();
 public String getRemainingInstalments();
//  public int getInstalmentsPaid();
 public Double getTotalPrincimpalAmount(); 
   public Double getTotalInterestAmount();
   
 public String getBalanceAmountDue();
 
 public Double getTheInstalmentAmount();
  public String getInstalmentStartDate();
 public String getInstalmentNextDueDate();
public String getInstalmentEndDate();
public String getInterestRate();
public String getLoanTenure();
public String getBorrowerName(); 
public String getLoanCycleStatus(); 
public String getStaffMakerId(); 
public String getStaffCheckerId(); 

public Double getTotalPrincimpalPaid();
 public Double getTotalInterestPaid();
 public Double getArrearsAmount();
 public String getNumberInstalmentInArrears();
 
 public Double outStandingChargesNow();
 public Double outStandingAccumulatedInterestNow();
 public Double outStandingInterestNow();

}
