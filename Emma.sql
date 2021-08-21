


DROP PROCEDURE `smsSummuryReport`;
DELIMITER $$
CREATE  PROCEDURE `smsSummuryReport`()
    READS SQL DATA
BEGIN
 
    


DROP TABLE IF EXISTS smsSummury;

CREATE  TEMPORARY TABLE smsSummury(itemName VARCHAR(200),itemValue VARCHAR(200));



CALL totalNumberOfActiveCustomers(@activeCustomers);

IF @activeCustomers>0 THEN

INSERT INTO smsSummury VALUES("No.Custom:",@activeCustomers);

  END IF;


CALL totalNumberOfCustomersPaid(@activeCustomersPaid);

IF @activeCustomersPaid>0 THEN

INSERT INTO smsSummury VALUES("No.CustomP:",@activeCustomersPaid);

  END IF;


CALL totalNumberOfCustomersSaving(@activeCustomersSave);

IF @activeCustomersSave>0 THEN

INSERT INTO smsSummury VALUES("No.CustomS:",@activeCustomersSave);

  END IF;

  CALL countNumberOfDisbursements(@numberOfDibusements);

IF @numberOfDibusements>0 THEN

INSERT INTO smsSummury VALUES("No.LoanOut:",@numberOfDibusements);

  END IF;
CALL princimpalLoanRepaymentsMade(DATE(NOW()),@princimpalRepaymentsMade);
CALL InterestRecover(DATE(NOW()),@InterestR);
SET @ActualTotalAmountCollectedToday=@princimpalRepaymentsMade+@InterestR;
IF @ActualTotalAmountCollectedToday>0 THEN

INSERT INTO smsSummury VALUES("TC:",@ActualTotalAmountCollectedToday);

  END IF;
  
  
CALL sumDisbursements(@totalDisbursement);

IF @totalDisbursement>0 THEN

INSERT INTO smsSummury VALUES("TLoanOut:",@totalDisbursement);

  END IF;
  




CALL OpeningCashBalance(DATE(NOW()),@OpeningCahdBala);



INSERT INTO smsSummury VALUES("OP:",@OpeningCahdBala);

CALL princimpalLoanRepaymentsMade(DATE(NOW()),@princimpalRepaymentsMade);
 


SET @OpeningCahdBala=@OpeningCahdBala+@princimpalRepaymentsMade;

IF @princimpalRepaymentsMade>0 THEN 
INSERT INTO smsSummury VALUES("PCollect:",@princimpalRepaymentsMade);
END IF;
CALL InterestRecover(DATE(NOW()),@InterestR);



SET @OpeningCahdBala=@OpeningCahdBala+@InterestR;

IF @InterestR>0 THEN 
INSERT INTO smsSummury VALUES("ICollect:",@InterestR);
END IF;


CALL ProcessingFeesCollected(DATE(NOW()),@processingFees);



SET @OpeningCahdBala=@OpeningCahdBala+@processingFees;

IF @processingFees>0 THEN 

INSERT INTO smsSummury VALUES("ProFees:",@processingFees);
END IF;


CALL LedgerFees(DATE(NOW()),@ledgerFessCol);


SET @OpeningCahdBala=@OpeningCahdBala+@ledgerFessCol;

IF @ledgerFessCol>0 THEN 
INSERT INTO smsSummury VALUES("LedgerFees:",@ledgerFessCol);
END IF;


CALL MembershipFees(DATE(NOW()),@memberShipFessCol);

SET @OpeningCahdBala=@OpeningCahdBala+@memberShipFessCol;

IF @memberShipFessCol>0 THEN 
INSERT INTO smsSummury VALUES("MembershipFees:",@memberShipFessCol);
END IF;

CALL annualSubFees(DATE(NOW()),@annualFeesRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@annualFeesRecovered;

IF @annualFeesRecovered>0 THEN 
INSERT INTO smsSummury VALUES("AnnualFees:",@annualFeesRecovered);
END IF;


CALL BadDebtsRecovered(DATE(NOW()),@badDebtsRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@badDebtsRecovered;

IF @badDebtsRecovered>0 THEN 
INSERT INTO smsSummury VALUES("BadDebts:",@badDebtsRecovered);
END IF;





CALL accumulatedInterestR(DATE(NOW()),@accumulatedInterestR);

SET @OpeningCahdBala=@OpeningCahdBala+@accumulatedInterestR;

IF @accumulatedInterestR>0 THEN 
INSERT INTO smsSummury VALUES("AccumInte:",@accumulatedInterestR);
END IF;

CALL loanPenaltyRecov(DATE(NOW()),@loanPenaltyRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@loanPenaltyRecovered;

IF @loanPenaltyRecovered>0 THEN 
INSERT INTO smsSummury VALUES("LoanPenalty:",@loanPenaltyRecovered);
END IF;

CALL otherIncomesCollected(DATE(NOW()),@otherIncomesCollectedX);

SET @OpeningCahdBala=@OpeningCahdBala+@otherIncomesCollectedX;

IF @otherIncomesCollectedX>0 THEN 
INSERT INTO smsSummury VALUES("OtherIncomes:",@otherIncomesCollectedX);
END IF;
 

 CALL SavingsDepositsMade(DATE(NOW()),@savingsC); 

SET @OpeningCahdBala=@OpeningCahdBala+@savingsC;

IF @savingsC>0 THEN 
INSERT INTO smsSummury VALUES("SavingsAndDeposits:",@savingsC);
END IF;


CALL PayablesCreated(DATE(NOW()),@payableCreated);


SET @OpeningCahdBala=@OpeningCahdBala+@payableCreated;

IF @payableCreated>0 THEN 
INSERT INTO smsSummury VALUES("Payables:",@payableCreated);
END IF;


CALL InsurancePayableMade(DATE(NOW()),@insurancePayMade);

SET @OpeningCahdBala=@OpeningCahdBala+@insurancePayMade;

IF @insurancePayMade>0 THEN 
INSERT INTO smsSummury VALUES("Insurance:",@insurancePayMade);
END IF;



CALL otherLiabilitiesAndProvisionsMade(DATE(NOW()),@OtherLiabilities);

SET @OpeningCahdBala=@OpeningCahdBala+@OtherLiabilities;

IF @OtherLiabilities>0 THEN 
INSERT INTO smsSummury VALUES("Liabilities:",@OtherLiabilities);
END IF;



CALL capitalisationMade(DATE(NOW()),@capitalPayments);

SET @OpeningCahdBala=@OpeningCahdBala+@capitalPayments;

IF @capitalPayments>0 THEN 
INSERT INTO smsSummury VALUES("Capital:",@capitalPayments);
END IF;

CALL OtherCapitalisationsAndReservesMade(DATE(NOW()),@otheCapsAndReserversMade);

SET @OpeningCahdBala=@OpeningCahdBala+@otheCapsAndReserversMade;

IF @otheCapsAndReserversMade>0 THEN 
INSERT INTO smsSummury VALUES("OtherCapital:",@otheCapsAndReserversMade);
END IF;

CALL RecevablesRefunded(DATE(NOW()),@Refundreceiavale);

SET @OpeningCahdBala=@OpeningCahdBala+@Refundreceiavale;

IF @Refundreceiavale>0 THEN 
INSERT INTO smsSummury VALUES("ReceivablesRefunded:",@Refundreceiavale);
END IF;

CALL OtherReceivablesAndPrepaymentsRefunded(DATE(NOW()),@otherReceiAndPrepaymentRend);

SET @OpeningCahdBala=@OpeningCahdBala+@otherReceiAndPrepaymentRend;

IF @otherReceiAndPrepaymentRend>0 THEN 
INSERT INTO smsSummury VALUES("OtherReceivablesRefunded:",@otherReceiAndPrepaymentRend);
END IF;


CALL BankWithdrawsMade(DATE(NOW()),@BankWithdrws);


SET @OpeningCahdBala=@OpeningCahdBala+@BankWithdrws;

IF @BankWithdrws>0 THEN 
INSERT INTO smsSummury VALUES("BankW:",@BankWithdrws);
END IF;


CALL refundFromMobileMoneyPayableMade(DATE(NOW()),@mobileMoneyRefund);

SET @OpeningCahdBala=@OpeningCahdBala+@mobileMoneyRefund;

IF @mobileMoneyRefund>0 THEN 
INSERT INTO smsSummury VALUES("PCollect:",@mobileMoneyRefund);
END IF;

CALL fixedAssetsAndInvestmentsDisposedOff(DATE(NOW()),@fixedAssetsAndInvestmentDisp);

SET @OpeningCahdBala=@OpeningCahdBala+@fixedAssetsAndInvestmentDisp;

IF @fixedAssetsAndInvestmentDisp>0 THEN 
INSERT INTO smsSummury VALUES("FixedAssets:",@fixedAssetsAndInvestmentDisp);
END IF;

CALL ExpensesMade(DATE(NOW()),@ExpensesMa);

SET @OpeningCahdBala=@OpeningCahdBala-@ExpensesMa;

IF @ExpensesMa>0 THEN 
INSERT INTO smsSummury VALUES("TotalExpenses:",@ExpensesMa);
END IF;


CALL LoanDisbursementsMade(DATE(NOW()),@loansDisbursed);

SET @OpeningCahdBala=@OpeningCahdBala-@loansDisbursed;

IF @loansDisbursed>0 THEN 
INSERT INTO smsSummury VALUES("LoanOut:",@loansDisbursed);
END IF;


CALL InterestWrittenOff(DATE(NOW()),@interWriteOff);

SET @OpeningCahdBala=@OpeningCahdBala-@interWriteOff;

IF @interWriteOff>0 THEN 
INSERT INTO smsSummury VALUES("InterestWrittenOff:",@interWriteOff);
END IF;

CALL accumuInteresWrittenOff(DATE(NOW()),@accumuIntereWrittenOff);

SET @OpeningCahdBala=@OpeningCahdBala-@accumuIntereWrittenOff;

IF @accumuIntereWrittenOff>0 THEN 
INSERT INTO smsSummury VALUES("AccumulatedInterestWrittenOff:",@accumuIntereWrittenOff);
END IF;


CALL processingFeesWrittenOff(DATE(NOW()),@processFeesWriteOff);

SET @OpeningCahdBala=@OpeningCahdBala-@processFeesWriteOff;

IF @processFeesWriteOff>0 THEN 
INSERT INTO smsSummury VALUES("ProcessingFeesWrittenOff:",@processFeesWriteOff);
END IF;

CALL OtherIncomesWrittenOff(DATE(NOW()),@otherIncomesWrOff);

SET @OpeningCahdBala=@OpeningCahdBala-@otherIncomesWrOff;

IF @otherIncomesWrOff>0 THEN 
INSERT INTO smsSummury VALUES("OtherIncomesWrittenOff:",@otherIncomesWrOff);
END IF;

CALL ReceivablesCreated(DATE(NOW()),@receiavale);

SET @OpeningCahdBala=@OpeningCahdBala-@receiavale;

IF @receiavale>0 THEN 
INSERT INTO smsSummury VALUES("ReceivablesCreated:",@receiavale);
END IF;

CALL MobileMoneyReceivableCreated(DATE(NOW()),@mobileMoney);

SET @OpeningCahdBala=@OpeningCahdBala-@mobileMoney;

IF @mobileMoney>0 THEN 
INSERT INTO smsSummury VALUES("MobileMoneyReceivableMade:",@mobileMoney);
END IF;

CALL OtherReceivablesAndPrepaymentsCreated(DATE(NOW()),@otherRecePreMade);

SET @OpeningCahdBala=@OpeningCahdBala-@otherRecePreMade;

IF @otherRecePreMade>0 THEN 
INSERT INTO smsSummury VALUES("OtherReceivablesAndPrepaymentsMade:",@otherRecePreMade);
END IF;


CALL BankDepositsMade(DATE(NOW()),@bankDepositMade);

SET @OpeningCahdBala=@OpeningCahdBala-@bankDepositMade;

IF @bankDepositMade>0 THEN 
INSERT INTO smsSummury VALUES("BankD:",@bankDepositMade);
END IF;


CALL fixedAssetsAndInvestmentsAquired(DATE(NOW()),@fixedAssetsAndInvestmentAquired);

SET @OpeningCahdBala=@OpeningCahdBala-@fixedAssetsAndInvestmentAquired;

IF @fixedAssetsAndInvestmentAquired>0 THEN 
INSERT INTO smsSummury VALUES("FixedAssetsAndInvestments:",@fixedAssetsAndInvestmentAquired);
END IF;

CALL PayablesRefunded(DATE(NOW()),@RefundPayable);

SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayable;

IF @RefundPayable>0 THEN 
INSERT INTO smsSummury VALUES("PayablesRefunded:",@RefundPayable);
END IF;

CALL PayablesOtherLiabilitiesAndProvisionsRefunded(DATE(NOW()),@RefundPayableOtherLiabilProvis);

SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayableOtherLiabilProvis;

IF @RefundPayableOtherLiabilProvis>0 THEN 
INSERT INTO smsSummury VALUES("OtherPayablesRefundedAndOtherLiabilitiesCleared:",@RefundPayableOtherLiabilProvis);
END IF;

CALL InsurancePayableCleared(DATE(NOW()),@insurancePayableCleared);

SET @OpeningCahdBala=@OpeningCahdBala-@insurancePayableCleared;

IF @insurancePayableCleared>0 THEN 
INSERT INTO smsSummury VALUES("InsurancePayablesCleared:",@insurancePayableCleared);
END IF;

CALL DrawingsMade(DATE(NOW()),@DrawingM);

SET @OpeningCahdBala=@OpeningCahdBala-@DrawingM;

IF @DrawingM>0 THEN 
INSERT INTO smsSummury VALUES("DrawingsMade:",@DrawingM);
END IF;

CALL DecapitalisationsMade(DATE(NOW()),@Decapitlise);

SET @OpeningCahdBala=@OpeningCahdBala-@Decapitlise;



IF @Decapitlise>0 THEN 
INSERT INTO smsSummury VALUES("CapitalRemoved:",@Decapitlise);
END IF;

CALL OtherEquitiesAndReservesRemoved(DATE(NOW()),@equitiesReservesRemoved); 

SET @OpeningCahdBala=@OpeningCahdBala-@equitiesReservesRemoved;

IF @equitiesReservesRemoved>0 THEN 
INSERT INTO smsSummury VALUES("OtherEquitiesAndReservesRemoved:",@equitiesReservesRemoved);
END IF;

 CALL SavingsDepositsWithdrawn(DATE(NOW()),@savingDepositWith); 

SET @OpeningCahdBala=@OpeningCahdBala-@savingDepositWith;

IF @savingDepositWith>0 THEN 
INSERT INTO smsSummury VALUES("SavingsWithdraws:",@savingDepositWith);
END IF;

INSERT INTO smsSummury VALUES("CC:",@OpeningCahdBala);


SELECT itemName,FORMAT(itemValue,0)  AS itemValue FROM smsSummury;


DROP TABLE smsSummury;

END $$
DELIMITER ;


CALL  smsSummuryReport() ;
