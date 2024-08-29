CREATE INDEX idx_Account_SavingsBalance ON pmms.newsavingsmembers (AccountNumber, SavingsRunningBalance);



DROP PROCEDURE IF EXISTS totalPrincimpalBalance;
DELIMITER $$
CREATE  PROCEDURE totalPrincimpalBalance(OUT princimpalBalance INT)
BEGIN

-- SELECT SUM(TotalPrincipalRemaining) INTO princimpalBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

-- SELECT SUM(newsavingsmembers.SavingsRunningBalance) INTO princimpalBalance
-- FROM (
--   SELECT t1.AccountNumber
--   FROM pmms.newsavingsmembers t1
--   LEFT JOIN pmms.newsavingsmembers t2 ON t1.AccountNumber = t2.AccountNumber AND t1.TrnId < t2.TrnId
--   WHERE t2.AccountNumber IS NULL
--     AND t1.SavingsRunningBalance > 0
-- ) AS subquery;


SELECT SUM(LastBalance) INTO princimpalBalance
FROM (
  SELECT SavingsRunningBalance AS LastBalance
  FROM pmms.newsavingsmembers 
  WHERE TrnId IN (
    SELECT MAX(TrnId)
    FROM pmms.newsavingsmembers 
    GROUP BY AccountNumber
  )
) AS subquery
WHERE LastBalance > 0;


END $$
DELIMITER ;




DROP PROCEDURE IF EXISTS totalNumberOfActiveCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfActiveCustomers(OUT activeCustomers INT)
BEGIN

SELECT COUNT(*) AS NumberOfCustomers INTO activeCustomers
FROM (
  SELECT t1.AccountNumber
  FROM pmms.newsavingsmembers t1
  LEFT JOIN pmms.newsavingsmembers t2 ON t1.AccountNumber = t2.AccountNumber AND t1.TrnId < t2.TrnId
  WHERE t2.AccountNumber IS NULL
    AND t1.SavingsRunningBalance > 0
) AS subquery;


END $$
DELIMITER ;





DROP PROCEDURE IF EXISTS totalNumberOfCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfCustomers(OUT totalCustomers INT)
BEGIN

SELECT COUNT(DISTINCT AccountNumber)  INTO totalCustomers
FROM pmms.newsavingsmembers;


END $$
DELIMITER ;





DROP PROCEDURE IF EXISTS totalNumberOfActiveNewCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfActiveNewCustomers(OUT activeCustomers INT)
BEGIN

SELECT DISTINCT COUNT(newsavingsmembers.TrnId) INTO activeCustomers FROM pmms.newsavingsmembers INNER JOIN pmms.account_created_store ON newsavingsmembers.AccountNumber=account_created_store.account_number WHERE  account_created_store.creation_date=DATE(NOW());

END $$
DELIMITER ;


DROP PROCEDURE smsSummuryReport;
DELIMITER $$
CREATE  PROCEDURE smsSummuryReport()
    READS SQL DATA
BEGIN
 
    


DROP TABLE IF EXISTS smsSummury;

CREATE  TEMPORARY TABLE smsSummury(itemName VARCHAR(200),itemValue VARCHAR(200));


CALL totalNumberOfCustomers(@totalCustomers);
-- SELECT @activeCustomers;
IF @totalCustomers>0 THEN
-- SELECT @activeCustomers;
INSERT INTO smsSummury VALUES("TotalNumberOfSavers:",FORMAT(@totalCustomers,0));

  END IF;




CALL totalNumberOfActiveCustomers(@activeCustomers);
-- SELECT @activeCustomers;
IF @activeCustomers>0 THEN
-- SELECT @activeCustomers;
INSERT INTO smsSummury VALUES("No.OfActiveSavers:",FORMAT(@activeCustomers,0));

  END IF;


CALL totalNumberOfCustomersPaid(@activeCustomersPaid);

-- SELECT @activeCustomersPaid;

IF @activeCustomersPaid>0 THEN
-- SELECT @activeCustomersPaid;
INSERT INTO smsSummury VALUES("No.OfCustomersPaid:",FORMAT(@activeCustomersPaid,0));
SET @rate=ROUND(((@activeCustomersPaid/@activeCustomers)*100),0);
SELECT CONCAT(@rate,'%') INTO @rate;
INSERT INTO smsSummury VALUES("CollectionRate:",@rate);
  END IF;


CALL totalNumberOfActiveNewCustomers(@activeNewCustomers);
-- SELECT @activeCustomers;
IF @activeNewCustomers>0 THEN
-- SELECT @activeCustomers;
INSERT INTO smsSummury VALUES("NewCustomers:",FORMAT(@activeNewCustomers,0));

  END IF;


-- SELECT @princimpalRepaymentsMade;
  -- SELECT @princimpalRepaymentsMade;
  CALL sumRenewals(@totalRenewals);
  -- select @totalRenewals;
CALL collectionsMade(DATE(NOW()),@princimpalRepaymentsMade);
-- CALL InterestRecover(DATE(NOW()),@InterestR);

-- CALL InterestRenewed(DATE(NOW()),@InterestRenew);
-- SELECT @princimpalRepaymentsMade,@InterestR;
SET @ActualTotalAmountCollectedToday=(@princimpalRepaymentsMade);

-- select @ActualTotalAmountCollectedToday;
IF @ActualTotalAmountCollectedToday>0 THEN
  -- SELECT @ActualTotalAmountCollectedToday;
INSERT INTO smsSummury VALUES("TotalCollections:",FORMAT(@ActualTotalAmountCollectedToday,0));

  END IF;



CALL countNumberOfRenewedPaid(@numberOfRenewalsPaid);

IF @numberOfRenewalsPaid>0 THEN
-- SELECT @numberOfDibusements;
INSERT INTO smsSummury VALUES("No.OfRenewedPaid:",FORMAT(@numberOfRenewalsPaid,0));

  END IF;


    -- SELECT @totalDisbursement;
CALL sumRenewalsPaid(@totalRenewalsPaid);

IF @totalRenewalsPaid>0 THEN
  
    -- SELECT @totalDisbursement;
INSERT INTO smsSummury VALUES("TotalAmntRenewedPaid:",FORMAT(@totalRenewalsPaid,0));

  END IF;



  CALL countNumberOfDisbursements(@numberOfDibusements);

IF @numberOfDibusements>0 THEN
-- SELECT @numberOfDibusements;
INSERT INTO smsSummury VALUES("No.OfLoansDisbursed:",FORMAT(@numberOfDibusements,0));

  END IF;

  
    -- SELECT @totalDisbursement;
CALL sumDisbursements(@totalDisbursement);

IF @totalDisbursement>0 THEN
  
    -- SELECT @totalDisbursement;
INSERT INTO smsSummury VALUES("TotalAmntDisbursed:",FORMAT(@totalDisbursement,0));

  END IF;


CALL countNumberOfRenewals(@numberOfRenewals);

IF @numberOfRenewals>0 THEN
-- SELECT @numberOfDibusements;
INSERT INTO smsSummury VALUES("No.OfLoansRenewed:",FORMAT(@numberOfRenewals,0));

  END IF;


    -- SELECT @totalDisbursement;


IF @totalRenewals>0 THEN
  
    -- SELECT @totalDisbursement;
INSERT INTO smsSummury VALUES("TotalAmntRenewed:",FORMAT(@totalRenewals,0));

  END IF;
  

CALL totalPrincimpalBalance(@princimpalBalance);

IF @princimpalBalance>0 THEN

INSERT INTO smsSummury VALUES("TotalSavingsBalance:",FORMAT(@princimpalBalance,0));

  END IF;


  CALL totalInterestBalance(@interestBalance);

IF @interestBalance>0 THEN

INSERT INTO smsSummury VALUES("InterestBalance:",FORMAT(@interestBalance,0));

  END IF;

IF @interestBalance>0 THEN

INSERT INTO smsSummury VALUES("TotalLoanPortfolio:",FORMAT(@interestBalance+@princimpalBalance,0));

  END IF;




CALL totalNumberOfSavingDeposited(@activeCustomersSave);

IF @activeCustomersSave>0 THEN
-- SELECT @activeCustomersSave;
INSERT INTO smsSummury VALUES("No.OfSavingAdded:",FORMAT(@activeCustomersSave,0));

  END IF;

 CALL totalNumberOfSavingWithdraw(@activeCustomersSave);

IF @activeCustomersSave>0 THEN
-- SELECT @activeCustomersSave;
INSERT INTO smsSummury VALUES("No.OfSavingRemoved:",FORMAT(@activeCustomersSave,0));

  END IF;




  



CALL OpeningCashBalance(DATE(NOW()),@OpeningCahdBala);


  
    -- SELECT @OpeningCahdBala;
INSERT INTO smsSummury VALUES("OpeningCash:",FORMAT(@OpeningCahdBala,0));

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01128%') THEN

CALL princimpalLoanRepaymentsMade(DATE(NOW()),@princimpalRepaymentsMade);

  END IF;

IF ISNULL(@princimpalRepaymentsMade) THEN
SET @princimpalRepaymentsMade=0;
END IF;
-- SELECT CONCAT("PrincipalCollected:=",@princimpalRepaymentsMade);
SET @OpeningCahdBala=@OpeningCahdBala+@princimpalRepaymentsMade;

IF @princimpalRepaymentsMade>0 THEN 
SET @PC=(@princimpalRepaymentsMade-(@totalRenewals-@InterestRenew));
-- SELECT @PC,@princimpalRepaymentsMade,@totalRenewals,@InterestRenew;

INSERT INTO smsSummury VALUES("PrincipalCollected:",FORMAT(@PC,0));
END IF;
IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03301%') THEN
CALL InterestRecover(DATE(NOW()),@InterestR);
END IF;
IF ISNULL(@InterestR) THEN
SET @InterestR=0;
END IF;
-- SELECT CONCAT("InterestCollected:=",@InterestR);
SET @OpeningCahdBala=@OpeningCahdBala+@InterestR;

IF @InterestR>0 THEN 
INSERT INTO smsSummury VALUES("InterestCollected:",FORMAT((@InterestR-@InterestRenew),0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03315%') THEN
CALL ProcessingFeesCollected(DATE(NOW()),@processingFees);
END IF;
IF ISNULL(@processingFees) THEN
SET @processingFees=0;
END IF;
-- SELECT CONCAT("ProcessingFees:=",@processingFees);
SET @OpeningCahdBala=@OpeningCahdBala+@processingFees;

IF @processingFees>0 THEN 

INSERT INTO smsSummury VALUES("ProcessingFees:",FORMAT(@processingFees,0));
END IF;
 -- SELECT @ledgerFessCol;
IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03304%') THEN
CALL LedgerFees(DATE(NOW()),@ledgerFessCol);
END IF;
IF ISNULL(@ledgerFessCol) THEN
SET @ledgerFessCol=0;
END IF;
-- SELECT CONCAT("LedgerFees:=",@ledgerFessCol);
SET @OpeningCahdBala=@OpeningCahdBala+@ledgerFessCol;

IF @ledgerFessCol>0 THEN 

 -- SELECT "LedgerFees:", @ledgerFessCol;
INSERT INTO smsSummury VALUES("LedgerFees:",FORMAT(@ledgerFessCol,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03309%') THEN
CALL MembershipFees(DATE(NOW()),@memberShipFessCol);
END IF;
IF ISNULL(@memberShipFessCol) THEN
SET @memberShipFessCol=0;
END IF;
-- SELECT CONCAT("MembershipFees:=",@memberShipFessCol);
SET @OpeningCahdBala=@OpeningCahdBala+@memberShipFessCol;

IF @memberShipFessCol>0 THEN 
INSERT INTO smsSummury VALUES("MembershipFees:",FORMAT(@memberShipFessCol,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03316%') THEN
CALL annualSubFees(DATE(NOW()),@annualFeesRecovered);
END IF;
IF ISNULL(@annualFeesRecovered) THEN
SET @annualFeesRecovered=0;
END IF;
-- SELECT CONCAT("AnnualFees:=",@annualFeesRecovered);
SET @OpeningCahdBala=@OpeningCahdBala+@annualFeesRecovered;

IF @annualFeesRecovered>0 THEN 
INSERT INTO smsSummury VALUES("AnnualFees:",FORMAT(@annualFeesRecovered,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03318%') THEN
CALL BadDebtsRecovered(DATE(NOW()),@badDebtsRecovered);
END IF;
IF ISNULL(@badDebtsRecovered) THEN
SET @badDebtsRecovered=0;
END IF;
-- SELECT CONCAT("BadDebts:=",@badDebtsRecovered);
SET @OpeningCahdBala=@OpeningCahdBala+@badDebtsRecovered;

IF @badDebtsRecovered>0 THEN 
INSERT INTO smsSummury VALUES("BadDebts:",FORMAT(@badDebtsRecovered,0));
END IF;




IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03311%') THEN
CALL accumulatedInterestR(DATE(NOW()),@accumulatedInterestR);
END IF;

IF ISNULL(@accumulatedInterestR) THEN
SET @accumulatedInterestR=0;
END IF;

-- SELECT CONCAT("AccumulatedInterest:=",@accumulatedInterestR);

SET @OpeningCahdBala=@OpeningCahdBala+@accumulatedInterestR;

IF @accumulatedInterestR>0 THEN 
INSERT INTO smsSummury VALUES("AccumulatedInterest:",FORMAT(@accumulatedInterestR,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03312%') THEN
CALL loanPenaltyRecov(DATE(NOW()),@loanPenaltyRecovered);
END IF;
IF ISNULL(@loanPenaltyRecovered) THEN
SET @loanPenaltyRecovered=0;
END IF;
-- SELECT CONCAT("LoanPenalty:=",@loanPenaltyRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@loanPenaltyRecovered;

IF @loanPenaltyRecovered>0 THEN 
INSERT INTO smsSummury VALUES("LoanPenalty:",FORMAT(@loanPenaltyRecovered,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND   (debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%')) THEN
CALL otherIncomesCollected(DATE(NOW()),@otherIncomesCollectedX);
END IF;


IF ISNULL(@otherIncomesCollectedX) THEN
SET @otherIncomesCollectedX=0;
END IF;
-- SELECT CONCAT("OtherIncomes:=",@otherIncomesCollectedX);
SET @OpeningCahdBala=@OpeningCahdBala+@otherIncomesCollectedX;

IF @otherIncomesCollectedX>0 THEN 
INSERT INTO smsSummury VALUES("UnrealisedInterestIncome:",FORMAT(@otherIncomesCollectedX,0));
END IF;
 

 CALL SavingsDepositsMade(DATE(NOW()),@savingsC); 
-- SELECT CONCAT("SavingsAndDeposits:=",@savingsC);
SET @OpeningCahdBala=@OpeningCahdBala+@savingsC;

IF @savingsC>0 THEN 
INSERT INTO smsSummury VALUES("SavingsAndDeposits:",FORMAT(@savingsC,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '05500%') THEN
CALL PayablesCreated(DATE(NOW()),@payableCreated);
END IF;


IF ISNULL(@payableCreated) THEN
SET @payableCreated=0;
END IF;
-- SELECT CONCAT("Payables:=",@payableCreated);
SET @OpeningCahdBala=@OpeningCahdBala+@payableCreated;

IF @payableCreated>0 THEN 
INSERT INTO smsSummury VALUES("Payables:",FORMAT(@payableCreated,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '05524%') THEN
CALL InsurancePayableMade(DATE(NOW()),@insurancePayMade);
END IF;

IF ISNULL(@insurancePayMade) THEN
SET @insurancePayMade=0;
END IF;
-- SELECT CONCAT("Insurance:=",@insurancePayMade);
SET @OpeningCahdBala=@OpeningCahdBala+@insurancePayMade;

IF @insurancePayMade>0 THEN 
INSERT INTO smsSummury VALUES("Insurance:",FORMAT(@insurancePayMade,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%'  OR debit_account_no like '05509%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%')) THEN
CALL otherLiabilitiesAndProvisionsMade(DATE(NOW()),@OtherLiabilities);
END IF;

IF ISNULL(@OtherLiabilities) THEN
SET @OtherLiabilities=0;
END IF;
-- SELECT CONCAT("UnknownMobileMoneyCreated:=",@OtherLiabilities);
SET @OpeningCahdBala=@OpeningCahdBala+@OtherLiabilities;

IF @OtherLiabilities>0 THEN 
INSERT INTO smsSummury VALUES("UnknownMobileMoneyCreated:",FORMAT(@OtherLiabilities,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '04400%') THEN
CALL capitalisationMade(DATE(NOW()),@capitalPayments);
END IF;


IF ISNULL(@capitalPayments) THEN
SET @capitalPayments=0;
END IF;
-- SELECT CONCAT("Capital:=",@capitalPayments);
SET @OpeningCahdBala=@OpeningCahdBala+@capitalPayments;

IF @capitalPayments>0 THEN 
INSERT INTO smsSummury VALUES("Capital:",FORMAT(@capitalPayments,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no>='044010000110' AND debit_account_no<='04430999999') THEN
CALL OtherCapitalisationsAndReservesMade(DATE(NOW()),@otheCapsAndReserversMade);
END IF;


IF ISNULL(@otheCapsAndReserversMade) THEN
SET @otheCapsAndReserversMade=0;
END IF;
-- SELECT CONCAT("OtherCapital:=",@otheCapsAndReserversMade);
SET @OpeningCahdBala=@OpeningCahdBala+@otheCapsAndReserversMade;

IF @otheCapsAndReserversMade>0 THEN 
INSERT INTO smsSummury VALUES("OtherCapital:",FORMAT(@otheCapsAndReserversMade,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01131%') THEN
CALL RecevablesRefunded(DATE(NOW()),@Refundreceiavale);
END IF;


IF ISNULL(@Refundreceiavale) THEN
SET @Refundreceiavale=0;
END IF;
-- SELECT CONCAT("ReceivablesRefunded:=",@Refundreceiavale);
SET @OpeningCahdBala=@OpeningCahdBala+@Refundreceiavale;

IF @Refundreceiavale>0 THEN 
INSERT INTO smsSummury VALUES("ReceivablesRefunded:",FORMAT(@Refundreceiavale,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND   (debit_account_no LIKE '01117%' OR debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
 OR debit_account_no LIKE '01132%'  OR debit_account_no LIKE '01133%'  OR debit_account_no LIKE '01134%'  OR debit_account_no LIKE '01135%'  OR debit_account_no LIKE '01120%')) THEN
CALL OtherReceivablesAndPrepaymentsRefunded(DATE(NOW()),@otherReceiAndPrepaymentRend);
END IF;

IF ISNULL(@otherReceiAndPrepaymentRend) THEN
SET @otherReceiAndPrepaymentRend=0;
END IF;
-- SELECT CONCAT("OtherReceivablesRefunded:=",@otherReceiAndPrepaymentRend);
SET @OpeningCahdBala=@OpeningCahdBala+@otherReceiAndPrepaymentRend;

IF @otherReceiAndPrepaymentRend>0 THEN 
INSERT INTO smsSummury VALUES("OtherReceivablesRefunded:",FORMAT(@otherReceiAndPrepaymentRend,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01122%') THEN
CALL BankDepositsMade(DATE(NOW()),@bankDepositMade);
END IF;

IF ISNULL(@bankDepositMade) THEN
SET @bankDepositMade=0;
END IF;
-- SELECT CONCAT("BankDeposits:=",@bankDepositMade);
SET @OpeningCahdBala=@OpeningCahdBala-@bankDepositMade;

IF @bankDepositMade>0 THEN 
INSERT INTO smsSummury VALUES("BankDeposits:",FORMAT(@bankDepositMade,0));
END IF;



IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01122%') THEN
CALL BankWithdrawsMade(DATE(NOW()),@BankWithdrws);
END IF;

IF ISNULL(@BankWithdrws) THEN
SET @BankWithdrws=0;
END IF;
-- SELECT CONCAT("BankWithdraws:=",@BankWithdrws);
SET @OpeningCahdBala=@OpeningCahdBala+@BankWithdrws;


IF @BankWithdrws>0 THEN 
INSERT INTO smsSummury VALUES("BankWithdraws:",FORMAT(@BankWithdrws,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01121%') THEN
CALL refundFromMobileMoneyPayableMade(DATE(NOW()),@mobileMoneyRefund);
END IF;


IF ISNULL(@mobileMoneyRefund) THEN
SET @mobileMoneyRefund=0;
END IF;
-- SELECT CONCAT("MomoWithdraws:=",@mobileMoneyRefund);
SET @OpeningCahdBala=@OpeningCahdBala+@mobileMoneyRefund;

IF @mobileMoneyRefund>0 THEN 
INSERT INTO smsSummury VALUES("MomoWithdraws:",FORMAT(@mobileMoneyRefund,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01121%') THEN
CALL MobileMoneyReceivableCreated(DATE(NOW()),@mobileMoney);
END IF;

IF ISNULL(@mobileMoney) THEN
SET @mobileMoney=0;
END IF;

SET @OpeningCahdBala=@OpeningCahdBala-@mobileMoney;

IF @mobileMoney>0 THEN 
INSERT INTO smsSummury VALUES("MomoDeposits:",FORMAT(@mobileMoney,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%')) THEN
CALL fixedAssetsAndInvestmentsDisposedOff(DATE(NOW()),@fixedAssetsAndInvestmentDisp);
END IF;

IF ISNULL(@fixedAssetsAndInvestmentDisp) THEN
SET @fixedAssetsAndInvestmentDisp=0;
END IF;
-- SELECT CONCAT("FixedAssets:=",@fixedAssetsAndInvestmentDisp);
SET @OpeningCahdBala=@OpeningCahdBala+@fixedAssetsAndInvestmentDisp;

IF @fixedAssetsAndInvestmentDisp>0 THEN 
INSERT INTO smsSummury VALUES("FixedAssets:",FORMAT(@fixedAssetsAndInvestmentDisp,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '022%') THEN
CALL ExpensesMade(DATE(NOW()),@ExpensesMa);
END IF;

IF ISNULL(@ExpensesMa) THEN
SET @ExpensesMa=0;
END IF;
-- SELECT CONCAT("TotalExpenses:=",@ExpensesMa);
SET @OpeningCahdBala=@OpeningCahdBala-@ExpensesMa;

IF @ExpensesMa>0 THEN 
INSERT INTO smsSummury VALUES("TotalExpenses:",FORMAT(@ExpensesMa,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01128%') THEN
CALL LoanDisbursementsMade(DATE(NOW()),@loansDisbursed);
END IF;

IF ISNULL(@loansDisbursed) THEN
SET @loansDisbursed=0;
END IF;
-- SELECT CONCAT("LoanDisbursements:=",@loansDisbursed);
SET @OpeningCahdBala=@OpeningCahdBala-@loansDisbursed;

IF @loansDisbursed>0 THEN 
INSERT INTO smsSummury VALUES("LoanDisbursements:",FORMAT(@loansDisbursed,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03301%') THEN
CALL InterestWrittenOff(DATE(NOW()),@interWriteOff);
END IF;

IF ISNULL(@interWriteOff) THEN
SET @interWriteOff=0;
END IF;
-- SELECT CONCAT("InterestWrittenOff:=",@interWriteOff);
SET @OpeningCahdBala=@OpeningCahdBala-@interWriteOff;

IF @interWriteOff>0 THEN 
INSERT INTO smsSummury VALUES("InterestWrittenOff:",FORMAT(@interWriteOff,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03311%') THEN
CALL accumuInteresWrittenOff(DATE(NOW()),@accumuIntereWrittenOff);
END IF;

IF ISNULL(@accumuIntereWrittenOff) THEN
SET @accumuIntereWrittenOff=0;
END IF;
-- SELECT CONCAT("AccumulatedInterestWrittenOff:=",@accumuIntereWrittenOff);
SET @OpeningCahdBala=@OpeningCahdBala-@accumuIntereWrittenOff;

IF @accumuIntereWrittenOff>0 THEN 
INSERT INTO smsSummury VALUES("AccumulatedInterestWrittenOff:",FORMAT(@accumuIntereWrittenOff,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03315%') THEN
CALL processingFeesWrittenOff(DATE(NOW()),@processFeesWriteOff);
END IF;

IF ISNULL(@processFeesWriteOff) THEN
SET @processFeesWriteOff=0;
END IF;
-- SELECT CONCAT("ProcessingFeesWrittenOff:=",@processFeesWriteOff);
SET @OpeningCahdBala=@OpeningCahdBala-@processFeesWriteOff;

IF @processFeesWriteOff>0 THEN 
INSERT INTO smsSummury VALUES("ProcessingFeesWrittenOff:",FORMAT(@processFeesWriteOff,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no LIKE '03312%' OR debit_account_no LIKE '03316%' OR debit_account_no LIKE '03309%' OR debit_account_no LIKE '03318%' OR debit_account_no LIKE '03304%' OR debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%')) THEN
CALL OtherIncomesWrittenOff(DATE(NOW()),@otherIncomesWrOff);
END IF;

IF ISNULL(@otherIncomesWrOff) THEN
SET @otherIncomesWrOff=0;
END IF;
-- SELECT CONCAT("OtherIncomesWrittenOff:=",@otherIncomesWrOff);
SET @OpeningCahdBala=@OpeningCahdBala-@otherIncomesWrOff;

IF @otherIncomesWrOff>0 THEN 
INSERT INTO smsSummury VALUES("OtherIncomesWrittenOff:",FORMAT(@otherIncomesWrOff,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01131%') THEN
CALL ReceivablesCreated(DATE(NOW()),@receiavale);
END IF;

IF ISNULL(@receiavale) THEN
SET @receiavale=0;
END IF;
-- SELECT CONCAT("ReceivablesCreated:=",@receiavale);
SET @OpeningCahdBala=@OpeningCahdBala-@receiavale;

IF @receiavale>0 THEN 
INSERT INTO smsSummury VALUES("ReceivablesCreated:",FORMAT(@receiavale,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no LIKE '01117%' OR debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
 OR debit_account_no LIKE '01132%'  OR debit_account_no LIKE '01133%'  OR debit_account_no LIKE '01134%'  OR debit_account_no LIKE '01135%'  OR debit_account_no LIKE '01120%')) THEN
CALL OtherReceivablesAndPrepaymentsCreated(DATE(NOW()),@otherRecePreMade);
END IF;

IF ISNULL(@otherRecePreMade) THEN
SET @otherRecePreMade=0;
END IF;
-- SELECT CONCAT("OtherReceivablesAndPrepaymentsMade:=",@otherRecePreMade);
SET @OpeningCahdBala=@OpeningCahdBala-@otherRecePreMade;

IF @otherRecePreMade>0 THEN 
INSERT INTO smsSummury VALUES("OtherReceivablesAndPrepaymentsMade:",FORMAT(@otherRecePreMade,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND   (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%')) THEN
CALL fixedAssetsAndInvestmentsAquired(DATE(NOW()),@fixedAssetsAndInvestmentAquired);
END IF;

IF ISNULL(@fixedAssetsAndInvestmentAquired) THEN
SET @fixedAssetsAndInvestmentAquired=0;
END IF;
-- SELECT CONCAT("FixedAssetsAndInvestments:=",@fixedAssetsAndInvestmentAquired);
SET @OpeningCahdBala=@OpeningCahdBala-@fixedAssetsAndInvestmentAquired;

IF @fixedAssetsAndInvestmentAquired>0 THEN 
INSERT INTO smsSummury VALUES("FixedAssetsAndInvestments:",FORMAT(@fixedAssetsAndInvestmentAquired,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '05500%') THEN
CALL PayablesRefunded(DATE(NOW()),@RefundPayable);
END IF;


IF ISNULL(@RefundPayable) THEN
SET @RefundPayable=0;
END IF;
-- SELECT CONCAT("PayablesRefunded:=",@RefundPayable);
SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayable;

IF @RefundPayable>0 THEN 
INSERT INTO smsSummury VALUES("PayablesRefunded:",FORMAT(@RefundPayable,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%')) THEN
CALL PayablesOtherLiabilitiesAndProvisionsRefunded(DATE(NOW()),@RefundPayableOtherLiabilProvis);
END IF;

IF ISNULL(@RefundPayableOtherLiabilProvis) THEN
SET @RefundPayableOtherLiabilProvis=0;
END IF;
-- SELECT CONCAT("UnknownMoMoCleared:=",@RefundPayableOtherLiabilProvis);
SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayableOtherLiabilProvis;

IF @RefundPayableOtherLiabilProvis>0 THEN 
INSERT INTO smsSummury VALUES("UnknownMoMoCleared:",FORMAT(@RefundPayableOtherLiabilProvis,0));
END IF;

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '05524%') THEN
CALL InsurancePayableCleared(DATE(NOW()),@insurancePayableCleared);
END IF;

IF ISNULL(@insurancePayableCleared) THEN
SET @insurancePayableCleared=0;
END IF;

-- SELECT CONCAT("InsurancePayablesCleared:=",@insurancePayableCleared);
SET @OpeningCahdBala=@OpeningCahdBala-@insurancePayableCleared;

IF @insurancePayableCleared>0 THEN 
INSERT INTO smsSummury VALUES("InsurancePayablesCleared:",FORMAT(@insurancePayableCleared,0));
END IF;


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '04408%') THEN
CALL DrawingsMade(DATE(NOW()),@DrawingM);
END IF;

IF ISNULL(@DrawingM) THEN
SET @DrawingM=0;
END IF;

-- SELECT CONCAT("DrawingsMade:=",@DrawingM);
SET @OpeningCahdBala=@OpeningCahdBala-@DrawingM;

IF @DrawingM>0 THEN 
INSERT INTO smsSummury VALUES("DrawingsMade:",FORMAT(@DrawingM,0));
END IF;
IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '04400%') THEN
CALL DecapitalisationsMade(DATE(NOW()),@Decapitlise);
END IF;

IF ISNULL(@Decapitlise) THEN
SET @Decapitlise=0;
END IF;
-- SELECT CONCAT("CapitalRemoved:=",@Decapitlise);
SET @OpeningCahdBala=@OpeningCahdBala-@Decapitlise;



IF @Decapitlise>0 THEN 
INSERT INTO smsSummury VALUES("CapitalRemoved:",FORMAT(@Decapitlise,0));
END IF;
IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND (debit_account_no>='044010000110' AND debit_account_no<='04430999999') AND NOT debit_account_no='04408000110') THEN
CALL OtherEquitiesAndReservesRemoved(DATE(NOW()),@equitiesReservesRemoved); 
END IF;
IF ISNULL(@equitiesReservesRemoved) THEN
SET @equitiesReservesRemoved=0;
END IF;

-- SELECT CONCAT("OtherEquitiesAndReservesRemoved:=",@equitiesReservesRemoved);

SET @OpeningCahdBala=@OpeningCahdBala-@equitiesReservesRemoved;

IF @equitiesReservesRemoved>0 THEN 
INSERT INTO smsSummury VALUES("OtherEquitiesAndReservesRemoved:",FORMAT(@equitiesReservesRemoved,0));
END IF;

 CALL SavingsDepositsWithdrawn(DATE(NOW()),@savingDepositWith); 

SET @OpeningCahdBala=@OpeningCahdBala-@savingDepositWith;

IF @savingDepositWith>0 THEN 
INSERT INTO smsSummury VALUES("SavingsWithdraws:",FORMAT(@savingDepositWith,0));
END IF;


CALL closingCash(@closingCashBal); 

INSERT INTO smsSummury VALUES("ClosingCash:",FORMAT( @closingCashBal,0) );



CALL momoBalance(@TheMomoBalance);

 IF @TheMomoBalance>0 THEN 
INSERT INTO smsSummury VALUES("MoMoBalance:",FORMAT(@TheMomoBalance,0) );
END IF;

 IF @TheMomoBalance>0 THEN 
INSERT INTO smsSummury VALUES("TotalCashAndMoMo:",FORMAT((@TheMomoBalance+@closingCashBal),0));
END IF;

--   INSERT INTO  aging_loan_analysis2x( 
--   id_2x,
--   customer_name ,
--   customer_contact ,
--   date_taken,
--   due_date,
--   loans_remaining ,
--   principal_remaining ,
-- interest_remaining,
--   principal_inarrears ,
--   interest_inarrears ,
--   number_of_days_in_arrears ,loan_deadline
--   ) SELECT 
START TRANSACTION;

DELETE FROM theSmsSummuryReport WHERE reportDate=DATE(NOW());

INSERT INTO theSmsSummuryReport (reportId, reportDate,reprtItemName,reportItemValue)
SELECT  NULL,DATE(NOW()),itemName,itemValue  FROM smsSummury;

COMMIT;



SELECT itemName,itemValue  AS itemValue FROM smsSummury;


DROP TABLE smsSummury;

END $$
DELIMITER ;



