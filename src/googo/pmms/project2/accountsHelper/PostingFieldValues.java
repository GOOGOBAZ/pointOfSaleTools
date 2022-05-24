/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

/**
 *
 * @author Stanchart
 */
public interface PostingFieldValues {
    public String getTransactionSequenceNumber();
    public String getUseId();
    public String getLogInTime();
    public String getTransactionDate();
    public String getTransactionType();
    public String getValueDate();
    public String getChequeNumber();
    public String getDrReferenceNumber();
    public String getDrAccountNumber();
    public String getDrAccountName();
    public String getNumberOfShares();
    public String getValueOfShares();
    public String getDrAmount();
    public String getDrNarrative1();
    public String getDrNarrative2();
    public String getCrAccountNumber();
    public String getCrAccountName();
    public String getCrAmount();
    public String getCrNarrative1();
    public String getCrNarrative2();
    public String getCrReferenceNumber();
    public void setTransactionSequenceNumber(String a);
    public void setUserId(String a);
    public void setLogInTime(String a);
    public void setTransactionDate(String a);
    public void setTransactionType(String a);
    public void setValueDate(String a);
     public String getBatchNumber();
    public void setChequeNumber(String a);
    public void setDrReferenceNumber(String a);
    public void setDrAccountNumber(String a);
    public void setDrAccountName(String a);
    public void setNumberOfShares(int a);
    public void setValueOfShares(double a);
    public void setDrAmount(double a);
    public void setDrNarrative1(String a);
    public void setDrNarrative2(String a);
    public void setCrAccountNumber(String a);
    public void setCrAccountName(String a);
    public void setCrAmount(double a);
    public void setCrNarrative1(String a);
    public void setCrNarrative2(String a);
    public void setCrReferenceNumber(String a);
     public void setBatchNumber();
}
