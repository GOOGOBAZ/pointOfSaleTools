/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.topupLoan;

import static java.lang.Double.parseDouble;



/**
 *
 * @author Stanchart
 */
public class DecisionTopUpImp extends ExistingBalanceImp implements DecisionTopUp{
    String accountNumber;

//ExistingBalanceImp bal =new ExistingBalanceImp(accountNumber);
   
public DecisionTopUpImp(String accountNumber) {
         
//        this.accountNumber=accountNumber;
       
          super(accountNumber);
      
    }

    @Override
    public boolean isQualifyingForTopUp(String accountNumber) {
        boolean qualifies=false;
        
      switch(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "topUpCriteria.txt"))){
    case 1:
       switch(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "useNumberInstalmentsSelect.txt"))){
           
           case 1: if(numberOfInstalmentsPaid()>0){  qualifies=true;  }break;
           
           case 2: if(numberOfInstalmentsPaid()>halfNumberOfInstalments()){qualifies=true;} break;
           
            case 3:if(numberOfInstalmentsPaid()>=fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "topUpsIfSetNumberOfInstalmentsPaid.txt"))){qualifies=true;} break;   
       
       } 
        break;
    case 2:
        switch(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "useAmountPaidSelect.txt"))){
        
        case 1:if(instalmentAmountPaid()>0.0){qualifies=true;}break;
           case 2:if(instalmentAmountPaid()>=halfloanAmount()){qualifies=true;}break;
            case 3:if(instalmentAmountPaid()>=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "topUpsSetUp", "amountPaid.txt")))){qualifies=true;} break; 
        
        }
        
        
        
        break;
    case 3:
       switch(fios.intFileReader(fios.createFileName("persistence", "topUpsSetUp", "useAmountRemainingSelect.txt"))){
       
         case 1: if(instalmentAmountPaid()>=0.0){qualifies=true;} break;
           case 2:if(instalmentAmountPaid()>=halfloanAmount()){qualifies=true;}break;
            case 3:if(instalmentAmountPaid()>=parseDouble(fios.stringFileReader(fios.createFileName("persistence", "topUpsSetUp", "amountRemaining.txt")))){qualifies=true;} break; 

       } 
        
        
        
        
        break;

    }
  return qualifies; 
}









}