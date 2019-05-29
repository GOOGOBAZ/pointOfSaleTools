DROP TRIGGER IF EXISTS countStatAccounts; 
DROP TRIGGER IF EXISTS countStatAccountsRedece;	
DROP TRIGGER IF EXISTS UpdateSharesAddRemove;
DROP TRIGGER IF EXISTS pmms.UpdateSharesAddRemove;
DROP TRIGGER IF EXISTS pmms.UpdateSavingsWithDraws;
 
  DROP PROCEDURE IF EXISTS statsTracking;
DROP PROCEDURE IF EXISTS updateCountStatsCustomers;
DROP PROCEDURE IF EXISTS deleteFromMaster;
DROP PROCEDURE IF EXISTS countNumberValueOfActiveSavings;
DROP PROCEDURE IF EXISTS pmms.countNumberValueOfActiveSavings;
DROP PROCEDURE IF EXISTS pmms.countNumberValueOfCompletedWrittenOffLoans;
DROP PROCEDURE IF EXISTS pmms.creatingArrearsLoanSummury;
DROP PROCEDURE IF EXISTS pmms.creatingRunningBalancesOfShares;
DROP PROCEDURE IF EXISTS pmms.currentAssets;
DROP PROCEDURE IF EXISTS pmms.deleteFromMaster;
DROP PROCEDURE IF EXISTS pmms.InterestReceivable;
DROP PROCEDURE IF EXISTS pmms.loanRepaymentsUpdatesAll;
DROP PROCEDURE IF EXISTS pmms.statsTracking;
DROP PROCEDURE IF EXISTS pmms.totalNumberValueOfShares;
DROP PROCEDURE IF EXISTS pmms.totalValueOfLoanBook;
DROP PROCEDURE IF EXISTS pmms.updateCountStatsAccounts;
DROP PROCEDURE IF EXISTS pmms.updateCountStatsAccountsReduce1;
DROP PROCEDURE IF EXISTS pmms.updateCountStatsCustomers;
DROP PROCEDURE IF EXISTS pmms.updateLoanAmountNumberDisbursements;
DROP PROCEDURE IF EXISTS pmms.updateSavingsMade;
DROP PROCEDURE IF EXISTS pmms.updateSavingsRemoved;
DROP PROCEDURE IF EXISTS pmms.updateSharesAdded;
DROP PROCEDURE IF EXISTS pmms.updateSharesRemoved;
 
			
 DROP PROCEDURE IF EXISTS createSummuryStat;
 
 	DELIMITER //
 CREATE PROCEDURE createSummuryStat()
 BEGIN

SET @sql_text2 = concat(CAST("DROP TABLE IF EXISTS summurystats" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @sql_text3 = concat(CAST(
  
  "CREATE TABLE `summurystats` (
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `ItemDate` date NOT NULL DEFAULT '1970-01-01',
  `TotalNumberOfAccounts` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveSavingsCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberSavingsWithdraws` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfSavingsMade` int(11) NOT NULL DEFAULT '0',
  `TotalValueSavingsWithdraws` double NOT NULL DEFAULT '0',
  `TotalValueOfSavings` double NOT NULL DEFAULT '0',
  `TotalValueOfSavingsMade` double NOT NULL DEFAULT '0',
  `TotalNumberOfActiveSharesCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfCapitalisations` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfDecapitalisations` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfSharesBought` double NOT NULL DEFAULT '0',
  `TotalValueOfSharesRemoved` double NOT NULL DEFAULT '0',
  `TotalValueOfShares` double NOT NULL DEFAULT '0',
  `TotalNumberOfShares` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfCustomersWithDeposits` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfDeposits` double NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoans` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle1` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle2` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle3` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle4` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle5` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle6` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoansCycleAbove7` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoans` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle1` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle2` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle3` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle4` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle5` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle6` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycle7` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoansCycleAbove7` double NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle1` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle2` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle3` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle4` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle5` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle6` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomersCycleAboveCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomers` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle1` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle2` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle3` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle4` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle5` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle6` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycle7` double NOT NULL DEFAULT '0',
  `TotalValueOfActiveLoanCustomersCycleAboveCycle7` double NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursed` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle1` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle2` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle3` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle4` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle5` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle6` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfLoansDisbursedCycleAbove7` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursed` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle1` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle2` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle3` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle4` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle5` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle6` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycle7` double NOT NULL DEFAULT '0',
  `TotalValueOfLoansDisbursedCycleAbove7` double NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursed` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle1` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle2` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle3` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle4` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle5` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle6` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfGroupLoansDisbursedCycleAbove7` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursed` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle1` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle2` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle3` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle4` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle5` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle6` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycle7` double NOT NULL DEFAULT '0',
  `TotalValueOfGroupLoansDisbursedCycleAbove7` double NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursed` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle1` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle2` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle3` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle4` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle5` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle6` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycle7` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfIndividualLoansDisbursedCycleAbove7` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursed` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle1` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle2` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle3` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle4` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle5` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle6` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycle7` double NOT NULL DEFAULT '0',
  `TotalValueOfIndividualLoansDisbursedCycleAbove7` double NOT NULL DEFAULT '0',
  `TotalNumberOfLoansCompleted` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfLoansCompleted` double NOT NULL DEFAULT '0',
  `TotalNumberOfLoansWrittenOff` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfLoansWrittenOff` double NOT NULL DEFAULT '0',
  `TotalNumberOfAllPrincipalLoanRepayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfAllPrincipalLoanRepayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfPrincipalLoanRepaymentsDueLoansOnly` double NOT NULL DEFAULT '0',
  `TotalNumberOfEarlyPrincipalLoanRepayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfEarlyPrincipalLoanRepayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfArrearsPrincipalLoanRepayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfArrearsPrincipalLoanRepayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfLoanRepaymentsMinusArrears` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfLoanRepaymentsMinusArrears` double NOT NULL DEFAULT '0',
  `TotalNumberOfAllInterestPayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfInterestReceived` double NOT NULL DEFAULT '0',
  `TotalNumberOfInterestPaymentsDueLoansOnly` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfInterestPaymentsDueLoansOnly` double NOT NULL DEFAULT '0',
  `TotalNumberOfEarlyInterestPayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfEarlyInterestPayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfArrearsInterestPayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfArrearsInterestPayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfAllAccumulatedInterestPayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfAllAccumulatedInterestPayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfAllLoanPenaltyPayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfAllLoanPenaltyPayments` double NOT NULL DEFAULT '0',
  `TotalNumberOfAllInterestAndPrincipalLoanRepayments` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfAllInterestAndPrincipalLoanRepayments` double NOT NULL DEFAULT '0',
  `TotalValueOfPrincipalOutStandingArrears` double NOT NULL DEFAULT '0',
  `TotalValueOfInterestOutStandingArrears` double NOT NULL DEFAULT '0',
  `TotalNumberOfLoansInArrears` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfPrincipalLoansInArrears` double NOT NULL DEFAULT '0',
  `TotalValueOfInterestInArrears` double NOT NULL DEFAULT '0',
  `TotalValueOfLoanBook` double NOT NULL DEFAULT '0',
  `TotalValueOfCashBalances` double NOT NULL DEFAULT '0',
  `TotalValueOfBankBalances` double NOT NULL DEFAULT '0',
  `TotalValueOfAssets` double NOT NULL DEFAULT '0',
  `TotalValueOfReceivables` double NOT NULL DEFAULT '0',
  `TotalValueOfPayables` double NOT NULL DEFAULT '0',
  `TotalValueOfFixedAssets` double NOT NULL DEFAULT '0',
  `TotalValueOfCurrentAssetsIncludingInterestReceivable` double NOT NULL DEFAULT '0',
  `TotalValueOfCurrentAssetsMinusInterestReceivable` double NOT NULL DEFAULT '0',
  `TotalValueOfMainIncome` double NOT NULL DEFAULT '0',
  `TotalValueOfOtherIncome` double NOT NULL DEFAULT '0',
  `TotalValueOfIncome` double NOT NULL DEFAULT '0',
  `TotalValueOfExpenses` double NOT NULL DEFAULT '0',
  `TotalValueOfLiabilities` double NOT NULL DEFAULT '0',
  `TotalValueOfCapital` double NOT NULL DEFAULT '0',
  `TotalValueOf16` double NOT NULL DEFAULT '0',
  `TotalValueOf17` double NOT NULL DEFAULT '0',
  `TotalValueOf18` double NOT NULL DEFAULT '0',
  `TotalNumberOf19` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf20` double NOT NULL DEFAULT '0',
  `TotalValueOf21` double NOT NULL DEFAULT '0',
  `TotalValueOf22` double NOT NULL DEFAULT '0',
  `TotalNumberOf23` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf24` double NOT NULL DEFAULT '0',
  `TotalValueOf25` double NOT NULL DEFAULT '0',
  `TotalValueOf26` double NOT NULL DEFAULT '0',
  `TotalNumberOf27` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf28` double NOT NULL DEFAULT '0',
  `TotalValueOf29` double NOT NULL DEFAULT '0',
  `TotalValueOf30` double NOT NULL DEFAULT '0',
  `TotalNumberOf31` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf32` double NOT NULL DEFAULT '0',
  `TotalValueOf33` double NOT NULL DEFAULT '0',
  `TotalValueOf34` double NOT NULL DEFAULT '0',
  `TotalNumberOf35` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf36` double NOT NULL DEFAULT '0',
  `TotalValueOf37` double NOT NULL DEFAULT '0',
  `TotalValueOf38` double NOT NULL DEFAULT '0',
  `TotalNumberOf39` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf40` double NOT NULL DEFAULT '0',
  `TotalValueOf41` double NOT NULL DEFAULT '0',
  `TotalValueOf42` double NOT NULL DEFAULT '0',
  `TotalNumberOf43` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf44` double NOT NULL DEFAULT '0',
  `TotalValueOf45` double NOT NULL DEFAULT '0',
  `TotalValueOf46` double NOT NULL DEFAULT '0',
  `TotalNumberOf47` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf48` double NOT NULL DEFAULT '0',
  `TotalValueOf49` double NOT NULL DEFAULT '0',
  `TotalValueOf50` double NOT NULL DEFAULT '0',
  `TotalNumberOf51` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf52` double NOT NULL DEFAULT '0',
  `TotalValueOf53` double NOT NULL DEFAULT '0',
  `TotalValueOf54` double NOT NULL DEFAULT '0',
  `TotalNumberOf55` int(11) NOT NULL DEFAULT '0',
  `TotalValueOf56` double NOT NULL DEFAULT '0',
  `TotalValueOf57` double NOT NULL DEFAULT '0',
  `TotalValueOf58` double NOT NULL DEFAULT '0',
  `TotalNumberOf59` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ItemId`),
  UNIQUE KEY `ItemId` (`ItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt3 FROM @sql_text3;
  EXECUTE stmt3;
DROP PREPARE stmt3;



SET @sql_text4 = concat(CAST('DROP TABLE IF EXISTS `operatingperiod1`' AS CHAR CHARACTER SET utf8));

  PREPARE stmt4 FROM @sql_text4;
  EXECUTE stmt4;
DROP PREPARE stmt4;



SET @sql_text5 = concat(CAST("CREATE TABLE `operatingperiod1` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `StartDate` date NOT NULL DEFAULT '1970-01-01',
  `EndDate` date NOT NULL DEFAULT '1970-01-01',
  `StatusCharge` varchar(15) DEFAULT NULL,
  `TheCharge` varchar(15) DEFAULT NULL,
  `Others3` varchar(100) DEFAULT 'N/A',
  `Others4` varchar(100) DEFAULT 'N/A',
  `Others5` varchar(100) DEFAULT 'N/A',
  `Others6` varchar(100) DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1"

 AS CHAR CHARACTER SET utf8));

  PREPARE stmt5 FROM @sql_text5;
  EXECUTE stmt5;
DROP PREPARE stmt5;


SET @sql_text1 = concat(CAST('INSERT INTO operatingperiod1 ( 
  `TrnId`,
  `StartDate` ,
  `EndDate` ,
  `StatusCharge` ,
  `TheCharge` ,
  `Others3` ,
  `Others4` ,
  `Others5` ,
  `Others6`
  ) SELECT 
   `TrnId`,
  `StartDate` ,
  `EndDate` ,
  `StatusCharge` ,
  `TheCharge` ,
  `Others3` ,
  `Others4` ,
  `Others5` ,
  `Others6` FROM operatingperiod' AS CHAR CHARACTER SET utf8));
  
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


 END//
 DELIMITER ;

CALL createSummuryStat();



DELIMITER //
CREATE PROCEDURE  statsTracking()
            BEGIN



INSERT INTO summurystats (
`ItemDate`,
  `TotalNumberOfAccounts` ,
  `TotalNumberOfCustomers` ,
  `TotalNumberOfActiveSavingsCustomers` ,
  `TotalNumberSavingsWithdraws` ,
  `TotalNumberOfSavingsMade` ,
  `TotalValueSavingsWithdraws`,
  `TotalValueOfSavings`,
  `TotalValueOfSavingsMade`,
  `TotalNumberOfActiveSharesCustomers` ,
  `TotalNumberOfCapitalisations` ,
  `TotalNumberOfDecapitalisations` ,
  `TotalValueOfSharesBought`,
  `TotalValueOfSharesRemoved`,
  `TotalValueOfShares`,
  `TotalNumberOfShares` ,
  `TotalNumberOfCustomersWithDeposits` ,
  `TotalValueOfDeposits`,
  `TotalNumberOfActiveLoans` ,
  `TotalNumberOfActiveLoansCycle1` ,
  `TotalNumberOfActiveLoansCycle2` ,
  `TotalNumberOfActiveLoansCycle3` ,
  `TotalNumberOfActiveLoansCycle4` ,
  `TotalNumberOfActiveLoansCycle5` ,
  `TotalNumberOfActiveLoansCycle6` ,
  `TotalNumberOfActiveLoansCycle7` ,
  `TotalNumberOfActiveLoansCycleAbove7` ,
  `TotalValueOfActiveLoans`,
  `TotalValueOfActiveLoansCycle1`,
  `TotalValueOfActiveLoansCycle2`,
  `TotalValueOfActiveLoansCycle3`,
  `TotalValueOfActiveLoansCycle4`,
  `TotalValueOfActiveLoansCycle5`,
  `TotalValueOfActiveLoansCycle6`,
  `TotalValueOfActiveLoansCycle7`,
  `TotalValueOfActiveLoansCycleAbove7`,
  `TotalNumberOfActiveLoanCustomers` ,
  `TotalNumberOfActiveLoanCustomersCycle1` ,
  `TotalNumberOfActiveLoanCustomersCycle2` ,
  `TotalNumberOfActiveLoanCustomersCycle3` ,
  `TotalNumberOfActiveLoanCustomersCycle4` ,
  `TotalNumberOfActiveLoanCustomersCycle5` ,
  `TotalNumberOfActiveLoanCustomersCycle6` ,
  `TotalNumberOfActiveLoanCustomersCycle7` ,
  `TotalNumberOfActiveLoanCustomersCycleAboveCycle7` ,
  `TotalValueOfActiveLoanCustomers`,
  `TotalValueOfActiveLoanCustomersCycle1`,
  `TotalValueOfActiveLoanCustomersCycle2`,
  `TotalValueOfActiveLoanCustomersCycle3`,
  `TotalValueOfActiveLoanCustomersCycle4`,
  `TotalValueOfActiveLoanCustomersCycle5`,
  `TotalValueOfActiveLoanCustomersCycle6`,
  `TotalValueOfActiveLoanCustomersCycle7`,
  `TotalValueOfActiveLoanCustomersCycleAboveCycle7`,
  `TotalNumberOfLoansDisbursed` ,
  `TotalNumberOfLoansDisbursedCycle1` ,
  `TotalNumberOfLoansDisbursedCycle2` ,
  `TotalNumberOfLoansDisbursedCycle3` ,
  `TotalNumberOfLoansDisbursedCycle4` ,
  `TotalNumberOfLoansDisbursedCycle5` ,
  `TotalNumberOfLoansDisbursedCycle6` ,
  `TotalNumberOfLoansDisbursedCycle7` ,
  `TotalNumberOfLoansDisbursedCycleAbove7` ,
  `TotalValueOfLoansDisbursed`,
  `TotalValueOfLoansDisbursedCycle1`,
  `TotalValueOfLoansDisbursedCycle2`,
  `TotalValueOfLoansDisbursedCycle3`,
  `TotalValueOfLoansDisbursedCycle4`,
  `TotalValueOfLoansDisbursedCycle5`,
  `TotalValueOfLoansDisbursedCycle6`,
  `TotalValueOfLoansDisbursedCycle7`,
  `TotalValueOfLoansDisbursedCycleAbove7`,
  `TotalNumberOfGroupLoansDisbursed` ,
  `TotalNumberOfGroupLoansDisbursedCycle1` ,
  `TotalNumberOfGroupLoansDisbursedCycle2` ,
  `TotalNumberOfGroupLoansDisbursedCycle3` ,
  `TotalNumberOfGroupLoansDisbursedCycle4` ,
  `TotalNumberOfGroupLoansDisbursedCycle5` ,
  `TotalNumberOfGroupLoansDisbursedCycle6` ,
  `TotalNumberOfGroupLoansDisbursedCycle7` ,
  `TotalNumberOfGroupLoansDisbursedCycleAbove7` ,
  `TotalValueOfGroupLoansDisbursed`,
  `TotalValueOfGroupLoansDisbursedCycle1`,
  `TotalValueOfGroupLoansDisbursedCycle2`,
  `TotalValueOfGroupLoansDisbursedCycle3`,
  `TotalValueOfGroupLoansDisbursedCycle4`,
  `TotalValueOfGroupLoansDisbursedCycle5`,
  `TotalValueOfGroupLoansDisbursedCycle6`,
  `TotalValueOfGroupLoansDisbursedCycle7`,
  `TotalValueOfGroupLoansDisbursedCycleAbove7`,
  `TotalNumberOfIndividualLoansDisbursed` ,
  `TotalNumberOfIndividualLoansDisbursedCycle1` ,
  `TotalNumberOfIndividualLoansDisbursedCycle2` ,
  `TotalNumberOfIndividualLoansDisbursedCycle3` ,
  `TotalNumberOfIndividualLoansDisbursedCycle4` ,
  `TotalNumberOfIndividualLoansDisbursedCycle5` ,
  `TotalNumberOfIndividualLoansDisbursedCycle6` ,
  `TotalNumberOfIndividualLoansDisbursedCycle7` ,
  `TotalNumberOfIndividualLoansDisbursedCycleAbove7` ,
  `TotalValueOfIndividualLoansDisbursed`,
  `TotalValueOfIndividualLoansDisbursedCycle1`,
  `TotalValueOfIndividualLoansDisbursedCycle2`,
  `TotalValueOfIndividualLoansDisbursedCycle3`,
  `TotalValueOfIndividualLoansDisbursedCycle4`,
  `TotalValueOfIndividualLoansDisbursedCycle5`,
  `TotalValueOfIndividualLoansDisbursedCycle6`,
  `TotalValueOfIndividualLoansDisbursedCycle7`,
  `TotalValueOfIndividualLoansDisbursedCycleAbove7`,
  `TotalNumberOfLoansCompleted` ,
  `TotalValueOfLoansCompleted`,
  `TotalNumberOfLoansWrittenOff` ,
  `TotalValueOfLoansWrittenOff`,
  `TotalNumberOfAllPrincipalLoanRepayments` ,
  `TotalValueOfAllPrincipalLoanRepayments`,
  `TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly` ,
  `TotalValueOfPrincipalLoanRepaymentsDueLoansOnly`,
  `TotalNumberOfEarlyPrincipalLoanRepayments` ,
  `TotalValueOfEarlyPrincipalLoanRepayments`,
  `TotalNumberOfArrearsPrincipalLoanRepayments` ,
  `TotalValueOfArrearsPrincipalLoanRepayments`,
  `TotalNumberOfLoanRepaymentsMinusArrears` ,
  `TotalValueOfLoanRepaymentsMinusArrears`,
  `TotalNumberOfAllInterestPayments` ,
  `TotalValueOfInterestReceived`,
  `TotalNumberOfInterestPaymentsDueLoansOnly` ,
  `TotalValueOfInterestPaymentsDueLoansOnly`,
  `TotalNumberOfEarlyInterestPayments` ,
  `TotalValueOfEarlyInterestPayments`,
  `TotalNumberOfArrearsInterestPayments` ,
  `TotalValueOfArrearsInterestPayments`,
  `TotalNumberOfAllAccumulatedInterestPayments` ,
  `TotalValueOfAllAccumulatedInterestPayments`,
  `TotalNumberOfAllLoanPenaltyPayments` ,
  `TotalValueOfAllLoanPenaltyPayments`,
  `TotalNumberOfAllInterestAndPrincipalLoanRepayments` ,
  `TotalValueOfAllInterestAndPrincipalLoanRepayments`,
  `TotalValueOfPrincipalOutStandingArrears`,
  `TotalValueOfInterestOutStandingArrears`,
  `TotalNumberOfLoansInArrears` ,
  `TotalValueOfPrincipalLoansInArrears`,
  `TotalValueOfInterestInArrears`,
  `TotalValueOfLoanBook`,
  `TotalValueOfCashBalances`,
  `TotalValueOfBankBalances`,
  `TotalValueOfAssets`,
  `TotalValueOfReceivables`,
  `TotalValueOfPayables`,
  `TotalValueOfFixedAssets`,
  `TotalValueOfCurrentAssetsIncludingInterestReceivable`,
  `TotalValueOfCurrentAssetsMinusInterestReceivable`,
  `TotalValueOfMainIncome`,
  `TotalValueOfOtherIncome`,
  `TotalValueOfIncome`,
  `TotalValueOfExpenses`,
  `TotalValueOfLiabilities`,
  `TotalValueOfCapital`,
  `TotalValueOf16`,
  `TotalValueOf17`,
  `TotalValueOf18`,
  `TotalNumberOf19` ,
  `TotalValueOf20`,
  `TotalValueOf21`,
  `TotalValueOf22`,
  `TotalNumberOf23` ,
  `TotalValueOf24`,
  `TotalValueOf25`,
  `TotalValueOf26`,
  `TotalNumberOf27` ,
  `TotalValueOf28`,
  `TotalValueOf29`,
  `TotalValueOf30`,
  `TotalNumberOf31` ,
  `TotalValueOf32`,
  `TotalValueOf33`,
  `TotalValueOf34`,
  `TotalNumberOf35` ,
  `TotalValueOf36`,
  `TotalValueOf37`,
  `TotalValueOf38`,
  `TotalNumberOf39` ,
  `TotalValueOf40`,
  `TotalValueOf41`,
  `TotalValueOf42`,
  `TotalNumberOf43` ,
  `TotalValueOf44`,
  `TotalValueOf45`,
  `TotalValueOf46`,
  `TotalNumberOf47` ,
  `TotalValueOf48`,
  `TotalValueOf49`,
  `TotalValueOf50`,
  `TotalNumberOf51` ,
  `TotalValueOf52`,
  `TotalValueOf53`,
  `TotalValueOf54`,
  `TotalNumberOf55` ,
  `TotalValueOf56`,
  `TotalValueOf57`,
  `TotalValueOf58`,
  `TotalNumberOf59`
  
  ) SELECT
      CURDATE(),
  `TotalNumberOfAccounts` ,
  `TotalNumberOfCustomers` ,
  `TotalNumberOfActiveSavingsCustomers` ,
  `TotalNumberSavingsWithdraws` ,
  `TotalNumberOfSavingsMade` ,
  `TotalValueSavingsWithdraws`,
  `TotalValueOfSavings`,
  `TotalValueOfSavingsMade`,
  `TotalNumberOfActiveSharesCustomers` ,
  `TotalNumberOfCapitalisations` ,
  `TotalNumberOfDecapitalisations` ,
  `TotalValueOfSharesBought`,
  `TotalValueOfSharesRemoved`,
  `TotalValueOfShares`,
  `TotalNumberOfShares` ,
  `TotalNumberOfCustomersWithDeposits` ,
  `TotalValueOfDeposits`,
  `TotalNumberOfActiveLoans` ,
  `TotalNumberOfActiveLoansCycle1` ,
  `TotalNumberOfActiveLoansCycle2` ,
  `TotalNumberOfActiveLoansCycle3` ,
  `TotalNumberOfActiveLoansCycle4` ,
  `TotalNumberOfActiveLoansCycle5` ,
  `TotalNumberOfActiveLoansCycle6` ,
  `TotalNumberOfActiveLoansCycle7` ,
  `TotalNumberOfActiveLoansCycleAbove7` ,
  `TotalValueOfActiveLoans`,
  `TotalValueOfActiveLoansCycle1`,
  `TotalValueOfActiveLoansCycle2`,
  `TotalValueOfActiveLoansCycle3`,
  `TotalValueOfActiveLoansCycle4`,
  `TotalValueOfActiveLoansCycle5`,
  `TotalValueOfActiveLoansCycle6`,
  `TotalValueOfActiveLoansCycle7`,
  `TotalValueOfActiveLoansCycleAbove7`,
  `TotalNumberOfActiveLoanCustomers` ,
  `TotalNumberOfActiveLoanCustomersCycle1` ,
  `TotalNumberOfActiveLoanCustomersCycle2` ,
  `TotalNumberOfActiveLoanCustomersCycle3` ,
  `TotalNumberOfActiveLoanCustomersCycle4` ,
  `TotalNumberOfActiveLoanCustomersCycle5` ,
  `TotalNumberOfActiveLoanCustomersCycle6` ,
  `TotalNumberOfActiveLoanCustomersCycle7` ,
  `TotalNumberOfActiveLoanCustomersCycleAboveCycle7` ,
  `TotalValueOfActiveLoanCustomers`,
  `TotalValueOfActiveLoanCustomersCycle1`,
  `TotalValueOfActiveLoanCustomersCycle2`,
  `TotalValueOfActiveLoanCustomersCycle3`,
  `TotalValueOfActiveLoanCustomersCycle4`,
  `TotalValueOfActiveLoanCustomersCycle5`,
  `TotalValueOfActiveLoanCustomersCycle6`,
  `TotalValueOfActiveLoanCustomersCycle7`,
  `TotalValueOfActiveLoanCustomersCycleAboveCycle7`,
  `TotalNumberOfLoansDisbursed` ,
  `TotalNumberOfLoansDisbursedCycle1` ,
  `TotalNumberOfLoansDisbursedCycle2` ,
  `TotalNumberOfLoansDisbursedCycle3` ,
  `TotalNumberOfLoansDisbursedCycle4` ,
  `TotalNumberOfLoansDisbursedCycle5` ,
  `TotalNumberOfLoansDisbursedCycle6` ,
  `TotalNumberOfLoansDisbursedCycle7` ,
  `TotalNumberOfLoansDisbursedCycleAbove7` ,
  `TotalValueOfLoansDisbursed`,
  `TotalValueOfLoansDisbursedCycle1`,
  `TotalValueOfLoansDisbursedCycle2`,
  `TotalValueOfLoansDisbursedCycle3`,
  `TotalValueOfLoansDisbursedCycle4`,
  `TotalValueOfLoansDisbursedCycle5`,
  `TotalValueOfLoansDisbursedCycle6`,
  `TotalValueOfLoansDisbursedCycle7`,
  `TotalValueOfLoansDisbursedCycleAbove7`,
  `TotalNumberOfGroupLoansDisbursed` ,
  `TotalNumberOfGroupLoansDisbursedCycle1` ,
  `TotalNumberOfGroupLoansDisbursedCycle2` ,
  `TotalNumberOfGroupLoansDisbursedCycle3` ,
  `TotalNumberOfGroupLoansDisbursedCycle4` ,
  `TotalNumberOfGroupLoansDisbursedCycle5` ,
  `TotalNumberOfGroupLoansDisbursedCycle6` ,
  `TotalNumberOfGroupLoansDisbursedCycle7` ,
  `TotalNumberOfGroupLoansDisbursedCycleAbove7` ,
  `TotalValueOfGroupLoansDisbursed`,
  `TotalValueOfGroupLoansDisbursedCycle1`,
  `TotalValueOfGroupLoansDisbursedCycle2`,
  `TotalValueOfGroupLoansDisbursedCycle3`,
  `TotalValueOfGroupLoansDisbursedCycle4`,
  `TotalValueOfGroupLoansDisbursedCycle5`,
  `TotalValueOfGroupLoansDisbursedCycle6`,
  `TotalValueOfGroupLoansDisbursedCycle7`,
  `TotalValueOfGroupLoansDisbursedCycleAbove7`,
  `TotalNumberOfIndividualLoansDisbursed` ,
  `TotalNumberOfIndividualLoansDisbursedCycle1` ,
  `TotalNumberOfIndividualLoansDisbursedCycle2` ,
  `TotalNumberOfIndividualLoansDisbursedCycle3` ,
  `TotalNumberOfIndividualLoansDisbursedCycle4` ,
  `TotalNumberOfIndividualLoansDisbursedCycle5` ,
  `TotalNumberOfIndividualLoansDisbursedCycle6` ,
  `TotalNumberOfIndividualLoansDisbursedCycle7` ,
  `TotalNumberOfIndividualLoansDisbursedCycleAbove7` ,
  `TotalValueOfIndividualLoansDisbursed`,
  `TotalValueOfIndividualLoansDisbursedCycle1`,
  `TotalValueOfIndividualLoansDisbursedCycle2`,
  `TotalValueOfIndividualLoansDisbursedCycle3`,
  `TotalValueOfIndividualLoansDisbursedCycle4`,
  `TotalValueOfIndividualLoansDisbursedCycle5`,
  `TotalValueOfIndividualLoansDisbursedCycle6`,
  `TotalValueOfIndividualLoansDisbursedCycle7`,
  `TotalValueOfIndividualLoansDisbursedCycleAbove7`,
  `TotalNumberOfLoansCompleted` ,
  `TotalValueOfLoansCompleted`,
  `TotalNumberOfLoansWrittenOff` ,
  `TotalValueOfLoansWrittenOff`,
  `TotalNumberOfAllPrincipalLoanRepayments` ,
  `TotalValueOfAllPrincipalLoanRepayments`,
  `TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly` ,
  `TotalValueOfPrincipalLoanRepaymentsDueLoansOnly`,
  `TotalNumberOfEarlyPrincipalLoanRepayments` ,
  `TotalValueOfEarlyPrincipalLoanRepayments`,
  `TotalNumberOfArrearsPrincipalLoanRepayments` ,
  `TotalValueOfArrearsPrincipalLoanRepayments`,
  `TotalNumberOfLoanRepaymentsMinusArrears` ,
  `TotalValueOfLoanRepaymentsMinusArrears`,
  `TotalNumberOfAllInterestPayments` ,
  `TotalValueOfInterestReceived`,
  `TotalNumberOfInterestPaymentsDueLoansOnly` ,
  `TotalValueOfInterestPaymentsDueLoansOnly`,
  `TotalNumberOfEarlyInterestPayments` ,
  `TotalValueOfEarlyInterestPayments`,
  `TotalNumberOfArrearsInterestPayments` ,
  `TotalValueOfArrearsInterestPayments`,
  `TotalNumberOfAllAccumulatedInterestPayments` ,
  `TotalValueOfAllAccumulatedInterestPayments`,
  `TotalNumberOfAllLoanPenaltyPayments` ,
  `TotalValueOfAllLoanPenaltyPayments`,
  `TotalNumberOfAllInterestAndPrincipalLoanRepayments` ,
  `TotalValueOfAllInterestAndPrincipalLoanRepayments`,
  `TotalValueOfPrincipalOutStandingArrears`,
  `TotalValueOfInterestOutStandingArrears`,
  `TotalNumberOfLoansInArrears` ,
  `TotalValueOfPrincipalLoansInArrears`,
  `TotalValueOfInterestInArrears`,
  `TotalValueOfLoanBook`,
  `TotalValueOfCashBalances`,
  `TotalValueOfBankBalances`,
  `TotalValueOfAssets`,
  `TotalValueOfReceivables`,
  `TotalValueOfPayables`,
  `TotalValueOfFixedAssets`,
  `TotalValueOfCurrentAssetsIncludingInterestReceivable`,
  `TotalValueOfCurrentAssetsMinusInterestReceivable`,
  `TotalValueOfMainIncome`,
  `TotalValueOfOtherIncome`,
  `TotalValueOfIncome`,
  `TotalValueOfExpenses`,
  `TotalValueOfLiabilities`,
  `TotalValueOfCapital`,
  `TotalValueOf16`,
  `TotalValueOf17`,
  `TotalValueOf18`,
  `TotalNumberOf19` ,
  `TotalValueOf20`,
  `TotalValueOf21`,
  `TotalValueOf22`,
  `TotalNumberOf23` ,
  `TotalValueOf24`,
  `TotalValueOf25`,
  `TotalValueOf26`,
  `TotalNumberOf27` ,
  `TotalValueOf28`,
  `TotalValueOf29`,
  `TotalValueOf30`,
  `TotalNumberOf31` ,
  `TotalValueOf32`,
  `TotalValueOf33`,
  `TotalValueOf34`,
  `TotalNumberOf35` ,
  `TotalValueOf36`,
  `TotalValueOf37`,
  `TotalValueOf38`,
  `TotalNumberOf39` ,
  `TotalValueOf40`,
  `TotalValueOf41`,
  `TotalValueOf42`,
  `TotalNumberOf43` ,
  `TotalValueOf44`,
  `TotalValueOf45`,
  `TotalValueOf46`,
  `TotalNumberOf47` ,
  `TotalValueOf48`,
  `TotalValueOf49`,
  `TotalValueOf50`,
  `TotalNumberOf51` ,
  `TotalValueOf52`,
  `TotalValueOf53`,
  `TotalValueOf54`,
  `TotalNumberOf55` ,
  `TotalValueOf56`,
  `TotalValueOf57`,
  `TotalValueOf58`,
  `TotalNumberOf59`   FROM summurystats ORDER BY ItemId DESC LIMIT 1;
                    
            END//
			DELIMITER ;

	DELIMITER //
 CREATE PROCEDURE updateCountStatsCustomers( )
 BEGIN
 
DECLARE existingAccounts INTEGER;DECLARE ItemIdu INTEGER;
 
SELECT TotalNumberOfCustomers,ItemId INTO existingAccounts,ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT COUNT(account_number) INTO existingAccounts FROM account_created_store WHERE  account_number like '05502%10';
UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;	


		
DELIMITER //

CREATE PROCEDURE deleteFromMaster(IN accountNumber VARCHAR(30)) BEGIN

DELETE FROM master WHERE account_number=accountNumber;

END //
DELIMITER ;

		
DELIMITER //
CREATE PROCEDURE countNumberValueOfActiveSavings( )
BEGIN
 
DECLARE ItemIdu INTEGER;DECLARE totalValueSaving INTEGER;DECLARE totalNumberOfSavings INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT SUM(running_balance),COUNT(running_balance) INTO totalValueSaving,totalNumberOfSavings FROM account_created_store WHERE running_balance>0 AND account_number like '0550200%';
UPDATE summurystats SET TotalNumberOfActiveSavingsCustomers=totalNumberOfSavings,TotalValueOfSavings=totalValueSaving WHERE ItemId=ItemIdu;

END//
DELIMITER ;

 
 
  
 
 	DELIMITER //
 CREATE PROCEDURE updateSavingsMade(IN  SavingsAdded INTEGER)
 BEGIN
 
DECLARE existingNumberSavingsMade INTEGER;DECLARE existingValueSavingsMade INTEGER;DECLARE ItemIdu INTEGER;
 
SELECT TotalNumberOfSavingsMade,TotalValueOfSavingsMade,ItemId INTO existingNumberSavingsMade,existingValueSavingsMade,ItemIdu FROM summurystats  ORDER BY ItemId DESC Limit 1;
 

SET existingNumberSavingsMade=existingNumberSavingsMade+1;

SET existingValueSavingsMade=existingValueSavingsMade+SavingsAdded;

UPDATE summurystats SET TotalNumberOfSavingsMade=existingNumberSavingsMade,TotalValueOfSavingsMade=existingValueSavingsMade WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;
 



 	DELIMITER //
 CREATE PROCEDURE updateSavingsRemoved(IN  SavingsRemoved INTEGER)
 BEGIN
 
 DECLARE existingNumberSavingsRemoved INTEGER;
 
  DECLARE existingValueSavingsRemoved INTEGER;
 
 DECLARE ItemIdu INTEGER;
 
 
 SELECT TotalNumberSavingsWithdraws INTO existingNumberSavingsRemoved FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
  SELECT TotalValueSavingsWithdraws INTO existingValueSavingsRemoved FROM summurystats  ORDER BY ItemId DESC Limit 1;

  SELECT ItemId INTO ItemIdu FROM summurystats  ORDER BY ItemId DESC Limit 1;

SET existingNumberSavingsRemoved=existingNumberSavingsRemoved+1;

SET existingValueSavingsRemoved=existingValueSavingsRemoved+SavingsRemoved;

UPDATE summurystats SET TotalNumberSavingsWithdraws=existingNumberSavingsRemoved,TotalValueSavingsWithdraws=existingValueSavingsRemoved WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;
 


DROP PROCEDURE IF EXISTS totalNumberValueOfShares;

		DELIMITER //
 CREATE PROCEDURE totalNumberValueOfShares() BEGIN
 
 DECLARE memberAccountNumber VARCHAR(30);
 
  DECLARE existingValueOfShares INTEGER;

   DECLARE existingNumberOfShares INTEGER;

   DECLARE TotalValueOfSharesS INTEGER DEFAULT 0;

   DECLARE TotalNumberOfSharesS INTEGER DEFAULT 0;

   DECLARE activeNumberOfCustomers INTEGER DEFAULT 0;

     DECLARE noMoreAccounts INTEGER DEFAULT 0;
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE accountNumbersShare CURSOR FOR SELECT DISTINCT account_number FROM pmms.shares_run_bal;

 DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreAccounts=1;


SET noMoreAccounts=0;

OPEN accountNumbersShare;

accountsLoop:REPEAT

 FETCH accountNumbersShare INTO memberAccountNumber;

 IF noMoreAccounts=0 THEN

 SELECT running_balance_n_shares,running_balance_v_shares INTO existingNumberOfShares,existingValueOfShares FROM pmms.shares_run_bal WHERE account_number= memberAccountNumber ORDER BY trn_id DESC LIMIT 1;


IF existingValueOfShares>0 THEN

SET activeNumberOfCustomers=activeNumberOfCustomers + 1;

SET TotalValueOfSharesS=TotalValueOfSharesS+existingValueOfShares;

SET TotalNumberOfSharesS=TotalNumberOfSharesS+existingNumberOfShares;

END IF;

 END IF;

UNTIL noMoreAccounts

END REPEAT accountsLoop;

CLOSE accountNumbersShare;

SET noMoreAccounts=0;

 SELECT ItemId INTO ItemIdu FROM summurystats  ORDER BY ItemId DESC Limit 1;

UPDATE summurystats SET TotalNumberOfActiveSharesCustomers=activeNumberOfCustomers,TotalValueOfShares=TotalValueOfSharesS,TotalNumberOfShares=TotalNumberOfSharesS WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;
 





 
 
 DROP PROCEDURE IF EXISTS updateSharesAdded;
 
 	DELIMITER //
 CREATE PROCEDURE updateSharesAdded(IN  SharesAdded INTEGER)
 BEGIN
 
 DECLARE existingNumberSharesMade INTEGER;
 
  DECLARE existingValueSharesMade INTEGER;
 
 DECLARE ItemIdu INTEGER;
 
 
 SELECT TotalNumberOfCapitalisations INTO existingNumberSharesMade FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
  SELECT TotalValueOfSharesBought INTO existingValueSharesMade FROM summurystats  ORDER BY ItemId DESC Limit 1;

  SELECT ItemId INTO ItemIdu FROM summurystats  ORDER BY ItemId DESC Limit 1;

SET existingNumberSharesMade=existingNumberSharesMade+1;

SET existingValueSharesMade=existingValueSharesMade+SharesAdded;

UPDATE summurystats SET TotalNumberOfCapitalisations=existingNumberSharesMade,TotalValueOfSharesBought=existingValueSharesMade WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;
 

 DROP PROCEDURE IF EXISTS updateSharesRemoved;

 	DELIMITER //
 CREATE PROCEDURE updateSharesRemoved(IN  SharedRemoved INTEGER)
 BEGIN
 
 DECLARE existingNumberSharesRemoved INTEGER;
 
  DECLARE existingValueSharesRemoved INTEGER;
 
 DECLARE ItemIdu INTEGER;
 
 
 SELECT TotalNumberOfDecapitalisations INTO existingNumberSharesRemoved FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
  SELECT TotalValueOfSharesRemoved INTO existingValueSharesRemoved FROM summurystats  ORDER BY ItemId DESC Limit 1;

  SELECT ItemId INTO ItemIdu FROM summurystats  ORDER BY ItemId DESC Limit 1;

SET existingNumberSharesRemoved=existingNumberSharesRemoved+1;

SET existingValueSharesRemoved=existingValueSharesRemoved+SharedRemoved;

UPDATE summurystats SET TotalNumberOfDecapitalisations=existingNumberSharesRemoved,TotalValueOfSharesRemoved=existingValueSharesRemoved WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;



  DROP PROCEDURE IF EXISTS countNumberValueOfActiveDeposits;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfActiveDeposits( )
 BEGIN
 
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueSaving INTEGER;
 
 DECLARE totalNumberOfSavings INTEGER;
 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(running_balance) INTO totalValueSaving FROM account_created_store WHERE running_balance>0 AND account_number like '0550200%';
  
  SELECT COUNT(running_balance) INTO totalNumberOfSavings FROM account_created_store WHERE running_balance>0 AND account_number like '0550200%';

UPDATE summurystats SET TotalNumberOfActiveSavingsCustomers=totalNumberOfSavings,TotalValueOfSavings=totalValueSaving WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;






 DROP PROCEDURE IF EXISTS pmms.countNumberValueOfActiveDeposits;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfActiveDeposits( )
 BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueDeposits INTEGER;
 
 DECLARE totalNumberOfDeposits INTEGER;
 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(running_balance) INTO totalValueDeposits FROM account_created_store WHERE running_balance>0 AND account_number like '0550200%';
  
  SELECT COUNT(running_balance) INTO totalNumberOfDeposits FROM account_created_store WHERE running_balance>0 AND account_number like '0550200%';

UPDATE summurystats SET TotalNumberOfCustomersWithDeposits=totalNumberOfDeposits,TotalValueOfDeposits=totalValueDeposits WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;

 



 DROP PROCEDURE IF EXISTS pmms.countNumberValueOfActiveLoans;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfActiveLoans( ) BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueLoans INTEGER;
 DECLARE totalNumberOfLoans INTEGER;

 DECLARE totalValueLoansCycle1 INTEGER;
 DECLARE totalNumberOfLoansCycle1 INTEGER;

  DECLARE totalValueLoansCycle2 INTEGER;
 DECLARE totalNumberOfLoansCycle2 INTEGER;

  DECLARE totalValueLoansCycle3 INTEGER;
 DECLARE totalNumberOfLoansCycle3 INTEGER;

  DECLARE totalValueLoansCycle4 INTEGER;
 DECLARE totalNumberOfLoansCycle4 INTEGER;

  DECLARE totalValueLoansCycle5 INTEGER;
 DECLARE totalNumberOfLoansCycle5 INTEGER;

  DECLARE totalValueLoansCycle6 INTEGER;
 DECLARE totalNumberOfLoansCycle6 INTEGER;

  DECLARE totalValueLoansCycle7 INTEGER;
 DECLARE totalNumberOfLoansCycle7 INTEGER;

  DECLARE totalValueLoansCycleAbove7 INTEGER;
 DECLARE totalNumberOfLoansCycleAbove7 INTEGER;


 DECLARE totalValueCustomerLoans INTEGER;
 DECLARE totalNumberOfCustomerLoans INTEGER;

 DECLARE totalValueCustomerLoansCycle1 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle1 INTEGER;

  DECLARE totalValueCustomerLoansCycle2 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle2 INTEGER;

  DECLARE totalValueCustomerLoansCycle3 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle3 INTEGER;

  DECLARE totalValueCustomerLoansCycle4 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle4 INTEGER;

  DECLARE totalValueCustomerLoansCycle5 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle5 INTEGER;

  DECLARE totalValueCustomerLoansCycle6 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle6 INTEGER;

  DECLARE totalValueCustomerLoansCycle7 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycle7 INTEGER;

  DECLARE totalValueCustomerLoansCycleAbove7 INTEGER;
 DECLARE totalNumberOfCustomerLoansCycleAbove7 INTEGER;
 

  SELECT ItemId INTO ItemIdu FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  
  SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoans, totalValueLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed';
    SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle1, totalValueLoansCycle1 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle1';
     SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle2, totalValueLoansCycle2 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle2';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle3, totalValueLoansCycle3 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle3';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle4, totalValueLoansCycle4 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle4';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle5, totalValueLoansCycle5 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle5';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle6, totalValueLoansCycle6 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle6';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle7, totalValueLoansCycle7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle7';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycleAbove7, totalValueLoansCycleAbove7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND NOT LoanCycle='Cycle1' OR 'Cycle2' OR 'Cycle3' OR 'Cycle4' OR 'Cycle5' OR 'Cycle6' OR 'Cycle7';


 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoans, totalValueCustomerLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND loan_id LIKE 'newloan05502%10';

 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle1, totalValueCustomerLoansCycle1 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle1' AND loan_id LIKE 'newloan055200%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle2, totalValueCustomerLoansCycle2 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle2' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle3, totalValueCustomerLoansCycle3 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle3' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle4, totalValueCustomerLoansCycle4 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle4' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle5, totalValueCustomerLoansCycle5 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle5' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle6, totalValueCustomerLoansCycle6 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle6' AND loan_id LIKE 'newloan05502%10';

SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle7, totalValueCustomerLoansCycle7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle7' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycleAbove7, totalValueCustomerLoansCycleAbove7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND NOT LoanCycle='Cycle1' OR 'Cycle2' OR 'Cycle3' OR 'Cycle4' OR 'Cycle5' OR 'Cycle6' OR 'Cycle7' AND loan_id LIKE 'newloan05502%10';


UPDATE pmms.summurystats SET TotalNumberOfActiveLoans=totalNumberOfLoans,TotalValueOfActiveLoans=totalValueLoans,TotalNumberOfActiveLoansCycle1=totalNumberOfLoansCycle1,TotalValueOfActiveLoansCycle1=totalValueLoansCycle1,
 TotalNumberOfActiveLoansCycle2=totalNumberOfLoansCycle2,TotalValueOfActiveLoansCycle2=totalValueLoansCycle2,
 TotalNumberOfActiveLoansCycle3=totalNumberOfLoansCycle3,TotalValueOfActiveLoansCycle3=totalValueLoansCycle3,
 TotalNumberOfActiveLoansCycle4=totalNumberOfLoansCycle4,TotalValueOfActiveLoansCycle4=totalValueLoansCycle4,
TotalNumberOfActiveLoansCycle5=totalNumberOfLoansCycle5,TotalValueOfActiveLoansCycle5=totalValueLoansCycle5,
 TotalNumberOfActiveLoansCycle6=totalNumberOfLoansCycle6,TotalValueOfActiveLoansCycle6=totalValueLoansCycle6,
 TotalNumberOfActiveLoansCycle7=totalNumberOfLoansCycle7,TotalValueOfActiveLoansCycle7=totalValueLoansCycle7,
 TotalNumberOfActiveLoansCycleAbove7=totalNumberOfLoansCycleAbove7,TotalValueOfActiveLoansCycleAbove7=totalValueLoansCycleAbove7,
 TotalNumberOfActiveLoanCustomers=totalNumberOfCustomerLoans,TotalValueOfActiveLoanCustomers=totalValueCustomerLoans,
 TotalNumberOfActiveLoanCustomersCycle1=totalNumberOfCustomerLoansCycle1,TotalValueOfActiveLoanCustomersCycle1=totalValueCustomerLoansCycle1,
 TotalNumberOfActiveLoanCustomersCycle2=totalNumberOfCustomerLoansCycle2,TotalValueOfActiveLoanCustomersCycle2=totalValueCustomerLoansCycle2,
 TotalNumberOfActiveLoanCustomersCycle3=totalNumberOfCustomerLoansCycle3,TotalValueOfActiveLoanCustomersCycle3=totalValueCustomerLoansCycle3,
 TotalNumberOfActiveLoanCustomersCycle4=totalNumberOfCustomerLoansCycle4,TotalValueOfActiveLoanCustomersCycle4=totalValueCustomerLoansCycle4,
 TotalNumberOfActiveLoanCustomersCycle5=totalNumberOfCustomerLoansCycle5,TotalValueOfActiveLoanCustomersCycle5=totalValueCustomerLoansCycle5,
 TotalNumberOfActiveLoanCustomersCycle6=totalNumberOfCustomerLoansCycle6,TotalValueOfActiveLoanCustomersCycle6=totalValueCustomerLoansCycle6,
 TotalNumberOfActiveLoanCustomersCycle7=totalNumberOfCustomerLoansCycle7,TotalValueOfActiveLoanCustomersCycle7=totalValueCustomerLoansCycle7,
 TotalNumberOfActiveLoanCustomersCycleAboveCycle7=totalNumberOfCustomerLoansCycleAbove7,TotalValueOfActiveLoanCustomersCycleAboveCycle7=totalValueCustomerLoansCycleAbove7 WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;


DROP PROCEDURE IF EXISTS pmms.updateLoanAmountNumberDisbursements;

 	DELIMITER //
 CREATE PROCEDURE updateLoanAmountNumberDisbursements(IN loanId VARCHAR(30), IN  principalAmount DOUBLE) BEGIN
   
 
 DECLARE LoanCycleS VARCHAR(30);
 
  DECLARE BorrowingCategoryS VARCHAR(30);
 
DECLARE existingNumberOfLoans INTEGER;

DECLARE existingValueOfLoans INTEGER;

DECLARE ItemIdu INTEGER;
 
 
 SELECT ItemId,TotalNumberOfLoansDisbursed,TotalValueOfLoansDisbursed INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursed=existingNumberOfLoans,TotalValueOfLoansDisbursed=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

SELECT LoanCycle,BorrowingCategory INTO LoanCycleS,BorrowingCategoryS FROM pmms_loans.new_loan_appstore1   WHERE loan_id=loanId;
 

 IF BorrowingCategoryS='Group' THEN

SELECT ItemId,TotalNumberOfGroupLoansDisbursed,TotalValueOfGroupLoansDisbursed INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursed=existingNumberOfLoans,TotalValueOfGroupLoansDisbursed=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

END IF;


 /*IF BorrowingCategoryS='Group' THEN

SELECT ItemId,TotalNumberOfGroupLoansDisbursed,TotalValueOfGroupLoansDisbursed INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursed=existingNumberOfLoans,TotalValueOfGroupLoansDisbursed=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

END IF;*/


 IF BorrowingCategoryS='Individual' THEN

SELECT ItemId,TotalNumberOfIndividualLoansDisbursed,TotalValueOfIndividualLoansDisbursed INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursed=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursed=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

END IF;

 IF LoanCycleS='Cycle1' THEN 
 
SELECT ItemId,TotalNumberOfLoansDisbursedCycle1,TotalValueOfLoansDisbursedCycle1 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle1=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle1=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle1,TotalValueOfGroupLoansDisbursedCycle1 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle1=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle1=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;

IF BorrowingCategoryS='Individual' THEN

SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle1,TotalValueOfIndividualLoansDisbursedCycle1 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM pmms.summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle1=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle1=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;


 END IF;



  IF LoanCycleS='Cycle2' THEN 
 SELECT ItemId,TotalNumberOfLoansDisbursedCycle2,TotalValueOfLoansDisbursedCycle2 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle2=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle2=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


  IF BorrowingCategoryS='Group' THEN

SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle2,TotalValueOfGroupLoansDisbursedCycle2 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle2=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle2=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;

 IF BorrowingCategoryS='Individual' THEN

SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle2,TotalValueOfIndividualLoansDisbursedCycle2 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle2=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle2=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;

 END IF;

  IF LoanCycleS='Cycle3' THEN 

   SELECT ItemId,TotalNumberOfLoansDisbursedCycle3,TotalValueOfLoansDisbursedCycle3 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle3=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle3=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;
  IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle3,TotalValueOfGroupLoansDisbursedCycle3 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle3=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle3=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 
 IF BorrowingCategoryS='Individual' THEN
SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle3,TotalValueOfIndividualLoansDisbursedCycle3 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle3=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle3=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 END IF;

  IF LoanCycleS='Cycle4' THEN 

   SELECT ItemId,TotalNumberOfLoansDisbursedCycle4,TotalValueOfLoansDisbursedCycle4 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle4=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle4=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

  IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle4,TotalValueOfGroupLoansDisbursedCycle4 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle4=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle4=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 IF BorrowingCategoryS='Individual' THEN
SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle4,TotalValueOfIndividualLoansDisbursedCycle4 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle4=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle4=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 END IF;

  IF LoanCycleS='Cycle5' THEN 
 SELECT ItemId,TotalNumberOfLoansDisbursedCycle5,TotalValueOfLoansDisbursedCycle5 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle5=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle5=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

  IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle5,TotalValueOfGroupLoansDisbursedCycle5 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle5=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle5=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 IF BorrowingCategoryS='Individual' THEN
SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle5,TotalValueOfIndividualLoansDisbursedCycle5 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle5=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle5=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 END IF;

 IF LoanCycleS='Cycle6' THEN 

 SELECT ItemId,TotalNumberOfLoansDisbursedCycle6,TotalValueOfLoansDisbursedCycle6 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle6=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle6=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

  IF BorrowingCategoryS='Group' THEN

SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle6,TotalValueOfGroupLoansDisbursedCycle6 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle6=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle6=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;
 IF BorrowingCategoryS='Individual' THEN
SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle6,TotalValueOfIndividualLoansDisbursedCycle6 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle6=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle6=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 END IF;

 IF LoanCycleS='Cycle7' THEN 

  SELECT ItemId,TotalNumberOfLoansDisbursedCycle7,TotalValueOfLoansDisbursedCycle7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle7=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;
  IF BorrowingCategoryS='Group' THEN

SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle7,TotalValueOfGroupLoansDisbursedCycle7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycle7=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycle7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;
 IF BorrowingCategoryS='Individual' THEN

SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycle7,TotalValueOfIndividualLoansDisbursedCycle7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycle7=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycle7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 END IF;
 END IF;

  IF NOT (LoanCycleS='Cycle1' OR LoanCycleS='Cycle2' OR LoanCycleS='Cycle3' OR LoanCycleS='Cycle4' OR LoanCycleS='Cycle5' OR LoanCycleS='Cycle6' OR LoanCycleS='Cycle7') THEN 
 
  SELECT ItemId,TotalNumberOfLoansDisbursedCycleAbove7,TotalValueOfLoansDisbursedCycleAbove7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycleAbove7=existingNumberOfLoans,TotalValueOfLoansDisbursedCycleAbove7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;
 
  IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycleAbove7,TotalValueOfGroupLoansDisbursedCycleAbove7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfGroupLoansDisbursedCycleAbove7=existingNumberOfLoans,TotalValueOfGroupLoansDisbursedCycleAbove7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 IF BorrowingCategoryS='Individual' THEN
SELECT ItemId,TotalNumberOfIndividualLoansDisbursedCycleAbove7,TotalValueOfIndividualLoansDisbursedCycleAbove7 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursedCycleAbove7=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursedCycleAbove7=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;



 END IF;
 END IF;

 END//
 DELIMITER ;




DROP PROCEDURE IF EXISTS updateCountStatsAccounts;	

	DELIMITER //

 CREATE PROCEDURE updateCountStatsAccounts()
 BEGIN
 
 DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;
 SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;

 /*
 SELECT ItemId, TotalNumberOfAccounts INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts+1;

*/
SELECT COUNT(account_number) INTO existingAccounts FROM account_created_store WHERE  account_number like '05502%';


UPDATE summurystats SET TotalNumberOfAccounts=existingAccounts WHERE ItemId=ItemIdu;

 END //
 DELIMITER ;	



 DROP PROCEDURE IF EXISTS pmms.countNumberValueOfCompletedWrittenOffLoans;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfCompletedWrittenOffLoans() BEGIN
 
 DECLARE ItemIdu INTEGER;DECLARE totalValueCompletedLoans INTEGER;DECLARE totalNumberOfCompltedLoans INTEGER;

  DECLARE totalValueWrittenOffLoans INTEGER;DECLARE totalNumberOfWrittenOffLoans INTEGER;

  SELECT ItemId INTO ItemIdu FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  
  SELECT COUNT(princimpal_amount),SUM(princimpal_amount) INTO totalNumberOfCompltedLoans, totalValueCompletedLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Completed';

   SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfWrittenOffLoans, totalValueWrittenOffLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='WrittenOff';
   

UPDATE pmms.summurystats SET TotalNumberOfLoansCompleted=totalNumberOfCompltedLoans,TotalValueOfLoansCompleted=totalValueCompletedLoans,TotalNumberOfLoansWrittenOff=totalNumberOfWrittenOffLoans,TotalValueOfLoansWrittenOff=totalValueWrittenOffLoans WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;







 DROP PROCEDURE IF EXISTS pmms.loanRepaymentsUpdatesAll;
 
 	DELIMITER //
 CREATE PROCEDURE loanRepaymentsUpdatesAll(IN typOfRepayment VARCHAR(100),IN loanId VARCHAR(100),IN InstalmentNo INTEGER,IN amountPAI INTEGER) BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE InstalmentDueDate DATE;

 DECLARE ExistingNumber INTEGER;

  DECLARE ExistingValue INTEGER;


 SELECT instalment_due_date INTO InstalmentDueDate  FROM pmms_loans.new_loan_appstoreamort WHERE instalment_no=InstalmentNo AND master2_id=loanId;


IF typOfRepayment='updateNewLoanPrincipalNow' THEN 


  SELECT ItemId,TotalNumberOfAllPrincipalLoanRepayments,TotalValueOfAllPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfAllPrincipalLoanRepayments=ExistingNumber,TotalValueOfAllPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

IF InstalmentDueDate=CURDATE() THEN 

  SELECT ItemId,TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly,TotalValueOfPrincipalLoanRepaymentsDueLoansOnly INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly=ExistingNumber,TotalValueOfPrincipalLoanRepaymentsDueLoansOnly=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

IF InstalmentDueDate<CURDATE() THEN

 SELECT ItemId,TotalNumberOfArrearsPrincipalLoanRepayments,TotalValueOfArrearsPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfArrearsPrincipalLoanRepayments=ExistingNumber,TotalValueOfArrearsPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
 SET   ExistingValue=0;
END IF;


IF InstalmentDueDate>CURDATE() THEN 

  SELECT ItemId,TotalNumberOfEarlyPrincipalLoanRepayments,TotalValueOfEarlyPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfEarlyPrincipalLoanRepayments=ExistingNumber,TotalValueOfEarlyPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;
END IF;

IF InstalmentDueDate>=CURDATE() THEN 

  SELECT ItemId,TotalNumberOfLoanRepaymentsMinusArrears,TotalValueOfLoanRepaymentsMinusArrears INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfLoanRepaymentsMinusArrears=ExistingNumber,TotalValueOfLoanRepaymentsMinusArrears=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
 SET   ExistingValue=0;
END IF;

END IF;




IF typOfRepayment='updateNewInterestNow' THEN 


 SELECT ItemId,TotalNumberOfAllInterestPayments,TotalValueOfInterestReceived INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfAllInterestPayments=ExistingNumber,TotalValueOfInterestReceived=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;



IF InstalmentDueDate=CURDATE() THEN 
 SELECT ItemId,TotalNumberOfInterestPaymentsDueLoansOnly,TotalValueOfInterestPaymentsDueLoansOnly INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfInterestPaymentsDueLoansOnly=ExistingNumber,TotalValueOfInterestPaymentsDueLoansOnly=ExistingValue WHERE ItemId=ItemIdu;
 SET   ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

IF InstalmentDueDate<CURDATE() THEN 

 SELECT ItemId,TotalNumberOfArrearsInterestPayments,TotalValueOfArrearsInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfArrearsInterestPayments=ExistingNumber,TotalValueOfArrearsInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

IF InstalmentDueDate>CURDATE() THEN 
 SELECT ItemId,TotalNumberOfEarlyInterestPayments,TotalValueOfEarlyInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET   ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfEarlyInterestPayments=ExistingNumber,TotalValueOfEarlyInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

END IF;



IF typOfRepayment='updateNewLoanPenaltyNow' THEN 

SELECT ItemId,TotalNumberOfAllLoanPenaltyPayments,TotalValueOfAllLoanPenaltyPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfAllLoanPenaltyPayments=ExistingNumber,TotalValueOfAllLoanPenaltyPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

 
IF typOfRepayment='updateNewAccumulatedInterestNow' THEN 
SELECT ItemId,TotalNumberOfAllAccumulatedInterestPayments,TotalValueOfAllAccumulatedInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

   SET ExistingNumber=ExistingNumber+1;
   SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfAllAccumulatedInterestPayments=ExistingNumber,TotalValueOfAllAccumulatedInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

SELECT ItemId,TotalNumberOfAllInterestAndPrincipalLoanRepayments,TotalValueOfAllInterestAndPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
   SET ExistingValue=ExistingValue+amountPAI;

UPDATE pmms.summurystats SET TotalNumberOfAllInterestAndPrincipalLoanRepayments=ExistingNumber,TotalValueOfAllInterestAndPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
   SET ExistingNumber=0;
  SET  ExistingValue=0;

 
 END//
 DELIMITER ;




 DROP PROCEDURE IF EXISTS pmms.creatingArrearsLoanSummury;
 
 	DELIMITER //


 CREATE PROCEDURE creatingArrearsLoanSummury( ) READS SQL DATA BEGIN

 DECLARE loanIds VARCHAR(30);


 DECLARE ItemIdu INTEGER;

DECLARE l_done INT DEFAULT 0;
 DECLARE ExistingPrinOnlyArrears INTEGER DEFAULT 0;
  DECLARE principalInArrears INTEGER DEFAULT 0;
   DECLARE ExistingIntnOnlyArrears INTEGER DEFAULT 0;
    DECLARE interestInArrears INTEGER DEFAULT 0;
     DECLARE ExistingNumberArrears INTEGER DEFAULT 0;
      DECLARE ExistingTotalPrinInArrears INTEGER DEFAULT 0;
       DECLARE TotalprincipalInArrears INTEGER DEFAULT 0;
        DECLARE ExistingTotalIntInArrears INTEGER DEFAULT 0;
         DECLARE TotalinterestInArrears INTEGER DEFAULT 0;
 

 DECLARE ForSelectingIds CURSOR FOR SELECT DISTINCT master2_id  FROM pmms_loans.new_loan_appstoreamort WHERE instalment_due_date<CURDATE()  AND NOT instalment_status='P';


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
SELECT ItemId INTO ItemIdu FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;
UPDATE pmms.summurystats SET TotalValueOfPrincipalOutStandingArrears=ExistingPrinOnlyArrears,TotalValueOfInterestOutStandingArrears=ExistingIntnOnlyArrears,TotalNumberOfLoansInArrears=ExistingNumberArrears,TotalValueOfPrincipalLoansInArrears=ExistingTotalPrinInArrears,TotalValueOfInterestInArrears=ExistingTotalIntInArrears WHERE ItemId=ItemIdu;

SET l_done=0;

 OPEN ForSelectingIds;


accounts_loop: LOOP 

 FETCH ForSelectingIds into loanIds;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


SELECT SUM(PrincipalRemaining),SUM(InterestRemaing) INTO principalInArrears,interestInArrears FROM pmms_loans.new_loan_appstoreamort WHERE instalment_due_date<CURDATE() AND master2_id=loanIds AND NOT instalment_status='P';

SELECT SUM(PrincipalRemaining),SUM(InterestRemaing) INTO TotalprincipalInArrears,TotalinterestInArrears FROM pmms_loans.new_loan_appstoreamort WHERE master2_id=loanIds AND NOT instalment_status='P';
 
 SELECT TotalValueOfPrincipalOutStandingArrears,TotalValueOfInterestOutStandingArrears,TotalNumberOfLoansInArrears,TotalValueOfPrincipalLoansInArrears,TotalValueOfInterestInArrears INTO ExistingPrinOnlyArrears,ExistingIntnOnlyArrears,ExistingNumberArrears,ExistingTotalPrinInArrears,ExistingTotalIntInArrears  FROM pmms.summurystats ORDER BY ItemId DESC Limit 1;



SET ExistingPrinOnlyArrears=ExistingPrinOnlyArrears+principalInArrears;

SET ExistingIntnOnlyArrears=ExistingIntnOnlyArrears+interestInArrears;

SET ExistingNumberArrears=ExistingNumberArrears+1;


SET ExistingTotalPrinInArrears=ExistingTotalPrinInArrears+TotalprincipalInArrears;

SET ExistingTotalIntInArrears=ExistingTotalIntInArrears+TotalinterestInArrears;


UPDATE pmms.summurystats SET TotalValueOfPrincipalOutStandingArrears=ExistingPrinOnlyArrears,TotalValueOfInterestOutStandingArrears=ExistingIntnOnlyArrears,TotalNumberOfLoansInArrears=ExistingNumberArrears,TotalValueOfPrincipalLoansInArrears=ExistingTotalPrinInArrears,TotalValueOfInterestInArrears=ExistingTotalIntInArrears WHERE ItemId=ItemIdu;

SET l_done=0;
SET ExistingPrinOnlyArrears=0;

SET ExistingIntnOnlyArrears=0;
SET ExistingNumberArrears=0;
SET ExistingTotalPrinInArrears=0;
SET ExistingTotalIntInArrears=0;

 END LOOP accounts_loop;
SET l_done=0;
 CLOSE ForSelectingIds;

END//

 DELIMITER ;


 DROP PROCEDURE IF EXISTS totalValueOfLoanBook;
 
 	DELIMITER //
 CREATE PROCEDURE totalValueOfLoanBook( )
 BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueOfLoanBook INTEGER; 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(TotalPrincipalRemaining) INTO totalValueOfLoanBook FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';
  

UPDATE summurystats SET TotalValueOfLoanBook=totalValueOfLoanBook WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;





 DROP PROCEDURE IF EXISTS accountSummuryStatsAccounts;
 
 	DELIMITER //
 CREATE PROCEDURE accountSummuryStatsAccounts( )
 BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueOfCash INTEGER;
 DECLARE totalValueOfBankBalance INTEGER;
  DECLARE totalValueOfAssets INTEGER;
  DECLARE totalValueOfReceivables INTEGER;
 DECLARE totalValueOfPayables INTEGER;
  DECLARE totalValueOfFixedAssets INTEGER;
  
  DECLARE totalCurrentAssetsIncludingInterestR INTEGER;
  DECLARE totalValueOfMainIncome INTEGER;

  DECLARE totalValueOfOtherIncome INTEGER;
 DECLARE totalValueOfIncome INTEGER;
DECLARE totalValueOfExpenses INTEGER;
DECLARE totalValueOfCapital INTEGER;

DECLARE totalValueOfLiabilities INTEGER;

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(running_balance) INTO totalValueOfCash FROM account_created_store WHERE running_balance>0 AND account_number like '0112300%';
  
  SELECT COUNT(running_balance) INTO totalValueOfBankBalance FROM account_created_store WHERE running_balance>0 AND account_number like '0112200%';
   
    SELECT SUM(running_balance) INTO totalValueOfAssets FROM account_created_store WHERE running_balance>0 AND account_number like '011%';
  
  SELECT COUNT(running_balance) INTO totalValueOfReceivables FROM account_created_store WHERE running_balance>0 AND account_number like '0113100%';
   
     SELECT COUNT(running_balance) INTO totalValueOfPayables FROM account_created_store WHERE running_balance>0 AND account_number like '055000%';
   
    SELECT SUM(running_balance) INTO totalValueOfFixedAssets FROM account_created_store WHERE  running_balance>0 AND (account_number like '01100%' OR account_number like  '01101%' OR account_number like   '01102%' OR account_number like   '01103%' OR account_number like   '01104%' OR  account_number like  '01105%' OR account_number like   '01106%' OR  account_number like  '01136%');
    
    CALL InterestReceivable(@interestReceivable);
    CALL currentAssets(@totalCurrentAssets);
 SET totalCurrentAssetsIncludingInterestR=@interestReceivable+@totalCurrentAssets;
 
  SELECT COUNT(running_balance) INTO totalValueOfMainIncome FROM account_created_store WHERE running_balance>0 AND (account_number like '03300%' OR account_number like  '03301%' OR account_number like '03302%' OR account_number like  '03303%');

   SELECT COUNT(running_balance) INTO totalValueOfOtherIncome FROM account_created_store WHERE running_balance>0 AND ( account_number like  '03304%' OR account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%' OR account_number like  '03309%' OR account_number like  '03310%' OR account_number like '03311%' OR account_number like  '03312%' OR account_number like  '03313%' OR account_number like '03314%' OR account_number like  '03315%' OR account_number like  '03316%' OR account_number like '03317%' OR account_number like  '03318%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
    
 SELECT SUM(running_balance) INTO totalValueOfIncome FROM account_created_store WHERE running_balance>0 AND account_number like '033%';
  
  SELECT SUM(running_balance) INTO totalValueOfExpenses FROM account_created_store WHERE running_balance>0 AND account_number like '022%';
  
    SELECT SUM(running_balance) INTO totalValueOfLiabilities FROM account_created_store WHERE running_balance>0 AND account_number like '055%';
  
 SELECT SUM(running_balance) INTO totalValueOfCapital FROM account_created_store WHERE running_balance>0 AND account_number like '044%';
  
UPDATE summurystats SET TotalValueOfCashBalances=totalValueOfCash,TotalValueOfBankBalances=totalValueOfBankBalance,TotalValueOfAssets=totalValueOfAssets,TotalValueOfReceivables=totalValueOfReceivables,TotalValueOfPayables=totalValueOfPayables,TotalValueOfFixedAssets=totalValueOfFixedAssets,TotalValueOfCurrentAssetsIncludingInterestReceivable=totalCurrentAssetsIncludingInterestR,TotalValueOfCurrentAssetsMinusInterestReceivable=@totalCurrentAssets,
TotalValueOfMainIncome=totalValueOfMainIncome,TotalValueOfOtherIncome=totalValueOfOtherIncome,TotalValueOfIncome=totalValueOfIncome,TotalValueOfExpenses=totalValueOfExpenses,TotalValueOfLiabilities=totalValueOfLiabilities,TotalValueOfCapital=totalValueOfCapital WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;



DROP PROCEDURE IF EXISTS pmms.InterestReceivable;
 
 	DELIMITER //

   CREATE PROCEDURE  InterestReceivable (OUT interestReceivable VARCHAR(30)) BEGIN

SELECT SUM(TotalInterestRemaining) INTO interestReceivable FROM pmms_loans.new_loan_appstore1 WHERE  loan_cycle_status='Disbursed';

IF (interestReceivable IS NULL) THEN

SET interestReceivable=0.0;
END IF;

     END//
 DELIMITER ;



 DROP PROCEDURE IF EXISTS pmms.currentAssets;
 
 	DELIMITER //

   CREATE PROCEDURE  currentAssets (OUT currentAssets VARCHAR(30)) BEGIN

SELECT SUM(running_balance) INTO currentAssets FROM account_created_store WHERE  running_balance>0 AND (account_number like '01113%' OR account_number like  '01114%' OR account_number like   '01115%' OR account_number like   '01116%' OR account_number like   '01117%' OR  account_number like  '01118%' OR account_number like   '01119%' OR  account_number like  '01120%' OR  account_number like  '01121%' OR  account_number like  '01122%' OR  account_number like  '01123%' OR  account_number like  '01124%' OR  account_number like  '01125%' OR  account_number like  '01126%' OR  account_number like  '01127%' OR  account_number like  '01128%' OR  account_number like  '01129%' OR  account_number like  '01130%' OR  account_number like  '01131%' OR  account_number like  '01132%' OR  account_number like  '01133%' OR  account_number like  '01134%' OR  account_number like  '01135%');

IF (currentAssets IS NULL) THEN

SET currentAssets=0.0;
END IF;

     END//
 DELIMITER ;





DROP PROCEDURE IF EXISTS pmms.updateCountStatsCustomersReduce;

DELIMITER //

 CREATE PROCEDURE updateCountStatsCustomersReduce() BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;

 SELECT ItemId,TotalNumberOfCustomers INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END//

 DELIMITER ;



 DROP PROCEDURE IF EXISTS pmms.updateCountStatsAccountsReduce;

DELIMITER //

 CREATE PROCEDURE updateCountStatsAccountsReduce() BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;
 SELECT ItemId,TotalNumberOfAccounts INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfAccounts=existingAccounts WHERE ItemId=ItemIdu;

END //

DELIMITER ;


DROP TRIGGER countStatAccountsRedece;
			DELIMITER //
			
			CREATE TRIGGER countStatAccountsRedece AFTER DELETE ON account_created_store FOR EACH ROW BEGIN

/*IF(OLD.account_number LIKE '0550200%') THEN
        
CALL updateCountStatsAccountsReduce();

END IF;

IF(OLD.account_number LIKE '0550200%10') THEN
        
CALL updateCountStatsCustomersReduce();

END IF;*/

CALL deleteFromMaster(OLD.account_number);

END//
		DELIMITER ;



DROP TRIGGER IF EXISTS countStatAccounts;

    DELIMITER //
	CREATE TRIGGER countStatAccounts BEFORE INSERT ON account_created_store FOR EACH ROW BEGIN

IF(NEW.account_number LIKE '0550200%10') THEN
        
CALL updateCountStatsCustomers();

END IF;

IF(NEW.account_number LIKE '0550200%') THEN
        
CALL updateCountStatsAccounts();

END IF;

END//


		DELIMITER ;

DROP TRIGGER IF EXISTS UpdateSharesAddRemove;

 DELIMITER //


			CREATE TRIGGER UpdateSharesAddRemove BEFORE INSERT ON shares_run_bal FOR EACH ROW BEGIN

IF(NEW.value_shares_contributed>0) THEN
        
CALL updateSharesAdded(NEW.value_shares_contributed);

END IF;

IF(NEW.value_shares_removed>0) THEN
        
CALL updateSharesRemoved(NEW.value_shares_removed);

END IF;

END//
		DELIMITER ;


DROP TRIGGER IF EXISTS UpdateSavingsWithDraws;
    	
DELIMITER //
			CREATE TRIGGER UpdateSavingsWithDraws BEFORE INSERT ON newsavingsmembers FOR EACH ROW BEGIN

IF(NEW.SavingsAdded>0) THEN
        
CALL updateSavingsMade(NEW.SavingsAdded);

END IF;

IF(NEW.SavingsRemoved>0) THEN
        
CALL updateSavingsRemoved(NEW.SavingsRemoved);

END IF;

END//
		DELIMITER ;




 DROP PROCEDURE IF EXISTS pmms_loans.updateMoreLoanDetails;

DELIMITER //

 CREATE PROCEDURE updateMoreLoanDetails( ) BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;
 SELECT ItemId,TotalNumberOfAccounts INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfAccounts=existingAccounts WHERE ItemId=ItemIdu;

END //

DELIMITER ;


                                                                                                                                                      




