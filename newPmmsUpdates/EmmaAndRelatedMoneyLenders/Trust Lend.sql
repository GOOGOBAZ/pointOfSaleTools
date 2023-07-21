

DROP PROCEDURE IF EXISTS InterestRecover;
DELIMITER //
CREATE PROCEDURE InterestRecover(IN theDate DATE,OUT InterestR VARCHAR(100)) READS SQL DATA 

BEGIN
-- SELECT  SUM(credit) INTO InterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no LIKE '03301%' OR debit_account_no LIKE '03322%');

SELECT  SUM(InterestPaid) INTO InterestR FROM pmms.loandisburserepaystatement WHERE  TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';


IF ISNULL(InterestR) THEN
SET InterestR=0.0;
END IF;

END //
DELIMITER ;



DROP PROCEDURE IF EXISTS princimpalLoanRepaymentsMade;

DELIMITER //

CREATE PROCEDURE princimpalLoanRepaymentsMade(IN theDate DATE,OUT princimpalRepaymentsMade VARCHAR(100)) READS SQL DATA 


BEGIN
-- SELECT  SUM(credit) INTO princimpalRepaymentsMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01128%';
SELECT  SUM(PrincipalPaid) INTO princimpalRepaymentsMade FROM pmms.loandisburserepaystatement WHERE  TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';
IF ISNULL(princimpalRepaymentsMade) THEN
SET princimpalRepaymentsMade=0.0;
END IF;


END //
DELIMITER ;




DROP PROCEDURE IF EXISTS collectionsMade;

DELIMITER //

CREATE PROCEDURE collectionsMade(IN theDate DATE,OUT theCollectionsMade VARCHAR(100)) READS SQL DATA 


BEGIN
-- SELECT  SUM(credit) INTO princimpalRepaymentsMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01128%';
SELECT  SUM(AmountPaid) INTO theCollectionsMade FROM pmms.loandisburserepaystatement WHERE  TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';
IF ISNULL(theCollectionsMade) THEN
SET theCollectionsMade=0.0;
END IF;


END //
DELIMITER ;


DROP PROCEDURE IF EXISTS totalPrincimpalBalance;
DELIMITER $$
CREATE  PROCEDURE totalPrincimpalBalance(OUT princimpalBalance INT)
BEGIN

SELECT SUM(TotalPrincipalRemaining) INTO princimpalBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

END $$
DELIMITER ;




DROP PROCEDURE `smsSummuryReport`;
DELIMITER $$
CREATE  PROCEDURE `smsSummuryReport`()
    READS SQL DATA
BEGIN
 
    


DROP TABLE IF EXISTS smsSummury;

CREATE  TEMPORARY TABLE smsSummury(itemName VARCHAR(200),itemValue VARCHAR(200));



CALL totalPrincimpalBalance(@princimpalBalance);

IF @princimpalBalance>0 THEN

INSERT INTO smsSummury VALUES("Princ.Stck:",@princimpalBalance);

  END IF;


  CALL totalInterestBalance(@interestBalance);

IF @interestBalance>0 THEN

INSERT INTO smsSummury VALUES("Interes.Stck:",@interestBalance);

  END IF;

IF @interestBalance>0 OR @princimpalBalance>0 THEN

INSERT INTO smsSummury VALUES("Total.Stck:",@interestBalance+@princimpalBalance);

  END IF;






CALL totalNumberOfActiveCustomers(@activeCustomers);

IF @activeCustomers>0 THEN

INSERT INTO smsSummury VALUES("No.Custom:",@activeCustomers);

  END IF;


CALL totalNumberOfCustomersPaid(@activeCustomersPaid);

IF @activeCustomersPaid>0 THEN

INSERT INTO smsSummury VALUES("No.CustomP:",@activeCustomersPaid);

  END IF;



CALL princimpalLoanRepaymentsMade(DATE(NOW()),@princimpalRepaymentsMade);
CALL InterestRecover(DATE(NOW()),@InterestR);
CALL collectionsMade(DATE(NOW()),@theCollectionsMade);
SET @ActualTotalAmountCollectedToday=@theCollectionsMade;



IF @ActualTotalAmountCollectedToday>0 THEN

INSERT INTO smsSummury VALUES("TC:",@ActualTotalAmountCollectedToday);

  END IF;


CALL countNumberOfRenewedPaid(@numberOfRenewalsPaid);

IF @numberOfRenewalsPaid>0 THEN
-- SELECT @numberOfDibusements;
INSERT INTO smsSummury VALUES("No.RenewedPaid:",@numberOfRenewalsPaid);

  END IF;


    -- SELECT @totalDisbursement;
CALL sumRenewalsPaid(@totalRenewalsPaid);

IF @totalRenewalsPaid>0 THEN
  
    -- SELECT @totalDisbursement;
INSERT INTO smsSummury VALUES("AmtRenewedPaid:",@totalRenewalsPaid);

  END IF;


  CALL countNumberOfDisbursements(@numberOfDibusements);

IF @numberOfDibusements>0 THEN

INSERT INTO smsSummury VALUES("No.LoanOut:",@numberOfDibusements);

  END IF;
  
  
  
CALL sumDisbursements(@totalDisbursement);

IF @totalDisbursement>0 THEN

INSERT INTO smsSummury VALUES("TLoanOut:",@totalDisbursement);

  END IF;
  

CALL countNumberOfRenewals(@numberOfRenewals);

IF @numberOfRenewals>0 THEN
-- SELECT @numberOfDibusements;
INSERT INTO smsSummury VALUES("No.Renewed:",@numberOfRenewals);

  END IF;


    -- SELECT @totalDisbursement;
CALL sumRenewals(@totalRenewals);

IF @totalRenewals>0 THEN
  
    -- SELECT @totalDisbursement;
INSERT INTO smsSummury VALUES("AmntRenewed:",@totalRenewals);

  END IF;
  

CALL totalNumberOfSavingDeposited(@activeCustomersSave);

IF @activeCustomersSave>0 THEN
-- SELECT @activeCustomersSave;
INSERT INTO smsSummury VALUES("No.FuelDeposit:",@activeCustomersSave);

  END IF;


 CALL totalNumberOfSavingWithdraw(@activeCustomersSave);

IF @activeCustomersSave>0 THEN
-- SELECT @activeCustomersSave;
INSERT INTO smsSummury VALUES("No.FuelWithdrawn:",@activeCustomersSave);

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
INSERT INTO smsSummury VALUES("FuelDeposit:",@savingsC);
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
INSERT INTO smsSummury VALUES("UnknownMomoMade:",@OtherLiabilities);
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
INSERT INTO smsSummury VALUES("MomoW:",@mobileMoneyRefund);
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
INSERT INTO smsSummury VALUES("MomoD:",@mobileMoney);
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
INSERT INTO smsSummury VALUES("UnkownMomoCleared:",@RefundPayableOtherLiabilProvis);
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
INSERT INTO smsSummury VALUES("FuelWithdraw:",@savingDepositWith);
END IF;

-- INSERT INTO smsSummury VALUES("CC:",@OpeningCahdBala);

CALL closingCash(@closingCashBal); 

INSERT INTO smsSummury VALUES("CC:", @closingCashBal);


CALL momoBalance(@TheMomoBalance);

 IF @TheMomoBalance>0 THEN 
INSERT INTO smsSummury VALUES("MO BAL:",@TheMomoBalance);
END IF;

 IF @TheMomoBalance>0 THEN 
INSERT INTO smsSummury VALUES("MO+CC:",@TheMomoBalance+@closingCashBal);
END IF;


START TRANSACTION;

DELETE FROM theSmsSummuryReport WHERE reportDate=DATE(NOW());

INSERT INTO theSmsSummuryReport (reportId, reportDate,reprtItemName,reportItemValue)
SELECT  NULL,DATE(NOW()),itemName,FORMAT(itemValue,0)  AS itemValue  FROM smsSummury;

COMMIT;

SELECT itemName,FORMAT(itemValue,0)  AS itemValue FROM smsSummury;


DROP TABLE smsSummury;

END $$
DELIMITER ;


CALL  smsSummuryReport() ;