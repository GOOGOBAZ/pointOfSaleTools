/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.topupLoan;

import googo.pmms.project2.accountsHelper.fileInputOutPutStreams;
import static java.lang.Integer.parseInt;

/**
 *
 * @author Stanchart
 */
public class ExistingBalanceImp implements ExistingBalance {
fileInputOutPutStreams fios= new fileInputOutPutStreams();
    private final String accountNumberx;
   
    
    public ExistingBalanceImp(String accountNumber){
        
    this.accountNumberx=accountNumber;
    
    }

    @Override
    public double initialTotalLoanAmount() {
        
        
    return (initialTotalPrincimpal()+initialTotalInterest());
    
    }

    @Override
    public double initialTotalPrincimpal() {
        
        return  new topUpDatabaseImp(accountNumberx).getTotalPrincimpalAmount();
        
    }

    @Override
    public double initialTotalInterest() {
        
         return  new topUpDatabaseImp(accountNumberx).getTotalInterestAmount();
    }

    @Override
    public double remainingTotalLoanAmount() {
        
      return (remainingTotalPrincimpal()+remainingTotalInterest());  
    }

    @Override
    public double remainingTotalPrincimpal() {
    return ( (new topUpDatabaseImp(accountNumberx).getTotalPrincimpalAmount())- (new topUpDatabaseImp(accountNumberx).getTotalPrincimpalPaid()));
    }

    @Override
    public double remainingTotalInterest() {
    return ( (new topUpDatabaseImp(accountNumberx).getTotalInterestAmount())- (new topUpDatabaseImp(accountNumberx).getTotalInterestPaid()));    
    }

    @Override
    public double princimpalAmountPaid() {
        return new topUpDatabaseImp(accountNumberx).getTotalPrincimpalPaid();
    }

    @Override
    public int remainingNumberOfInstalments() {
         return parseInt(new topUpDatabaseImp(accountNumberx).getRemainingInstalments());
    }

    @Override
    public int numberOfInstalmentsPaid() {
//        fios.stringFileWriter(fios.createFileName("test", "testit", "tested.txt"), (new topUpDatabaseImp(accountNumberx).getTotalNumberIntalments())+","+(remainingNumberOfInstalments())+accountNumberx);
        return (parseInt((new topUpDatabaseImp(accountNumberx).getTotalNumberIntalments()))-(remainingNumberOfInstalments()));
        
    }

    @Override
    public double amountInArrears() {
      return  new topUpDatabaseImp(accountNumberx).getArrearsAmount();
    }

    @Override
    public int numberOfInstalmentsInArrears() {
        return  parseInt(new topUpDatabaseImp(accountNumberx).getNumberInstalmentInArrears());
    }

    @Override
    public double currentInstalment() {
   return  new topUpDatabaseImp(accountNumberx).getCurrentInstalment();
    }

    @Override
    public int totalNumberOfInstalments() {
     return parseInt(new topUpDatabaseImp(accountNumberx).getTotalNumberIntalments());
    }

    @Override
    public int halfNumberOfInstalments() {
       return totalNumberOfInstalments()/2;
    }

    @Override
    public double instalmentAmountPaid() {
     return (this.princimpalAmountPaid()+this.totalInterestPaid());
    }

    @Override
    public double totalInterestPaid() {
     return  new topUpDatabaseImp(accountNumberx).getTotalInterestPaid();
    }
 @Override
    public double outStandingInterest() {
     return  new topUpDatabaseImp(accountNumberx).outStandingInterestNow();
    }
     @Override
    public double outStandingCharges() {
     return  new topUpDatabaseImp(accountNumberx).outStandingChargesNow();
    }
     @Override
    public double outStandingAccumulatedInterest() {
     return  new topUpDatabaseImp(accountNumberx).outStandingAccumulatedInterestNow();
    }
    @Override
    public double halfloanAmount() {
   return initialTotalLoanAmount()/2;
    }
    
    
    
    
    
}
