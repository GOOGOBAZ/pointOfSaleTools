/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.topupLoan;

/**
 *
 * @author Stanchart
 */
public interface ExistingBalance {
   
    public double initialTotalLoanAmount();
     public double initialTotalPrincimpal();
      public double initialTotalInterest();
        public double remainingTotalLoanAmount();
     public double remainingTotalPrincimpal();
      public double remainingTotalInterest();
      public double princimpalAmountPaid();
      public int remainingNumberOfInstalments();
       public int numberOfInstalmentsPaid();
       public double amountInArrears();
      public int numberOfInstalmentsInArrears(); 
        public int totalNumberOfInstalments(); 
          public int halfNumberOfInstalments(); 
      public double currentInstalment();
       public double instalmentAmountPaid();
       public double totalInterestPaid();
        public double halfloanAmount();
        public double outStandingInterest();
        public double outStandingCharges();
        public double outStandingAccumulatedInterest();
        
}
