/*=========================================================DAVIDED PAYMENT AND RUNNING BALANCE ON SHARES==================================================*/

 DROP PROCEDURE IF EXISTS creatingRunningBalancesOfShares;
 
 	DELIMITER //


 CREATE PROCEDURE creatingRunningBalancesOfShares( ) READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30);

 DECLARE TrnId INT DEFAULT 0;

DECLARE l_done INT DEFAULT 0;

 DECLARE NoOfSharesRemoved INT DEFAULT 0;
 DECLARE NoOfSharesAdded INT DEFAULT 0;
 DECLARE ValueOfSharesRemoved INT DEFAULT 0;
 DECLARE ValueOfSharesAdded INT DEFAULT 0;
 DECLARE RunningBalanceNumberShares INT DEFAULT 0;
 DECLARE RunningBalanceValueOfShares INT DEFAULT 0;



 DECLARE accountNumbersShares CURSOR FOR SELECT DISTINCT account_number  FROM pmms.shares_run_bal;

 DECLARE trnIds CURSOR FOR SELECT trn_id   FROM  pmms.shares_run_bal WHERE account_number=accountNumber;

 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

SET l_done=0;

 OPEN accountNumbersShares;

accounts_loop: LOOP 

 FETCH accountNumbersShares into accountNumber;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
/*
DECLARE l_done INT DEFAULT 0;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
*/
 OPEN trnIds;



trnIds_loop: LOOP

 FETCH trnIds INTO TrnId;

 IF l_done=1 THEN

LEAVE trnIds_loop;

 END IF;

 SELECT number_shares_contributed,number_shares_removed,value_shares_contributed,value_shares_removed INTO NoOfSharesAdded,NoOfSharesRemoved,ValueOfSharesAdded,ValueOfSharesRemoved FROM pmms.shares_run_bal WHERE trn_id=TrnId;

SET RunningBalanceNumberShares=RunningBalanceNumberShares+NoOfSharesAdded-NoOfSharesRemoved;

SET RunningBalanceValueOfShares=RunningBalanceValueOfShares+ValueOfSharesAdded-ValueOfSharesRemoved;

UPDATE pmms.shares_run_bal SET running_balance_n_shares=RunningBalanceNumberShares,running_balance_v_shares=RunningBalanceValueOfShares WHERE trn_id=TrnId;
/*SELECT CONCAT('Account ', accountNumber,' has ', TrnId,' id');*/

 END LOOP trnIds_loop;
SET l_done=0;
 CLOSE trnIds;

SET l_done=0;
SET RunningBalanceNumberShares=0;

SET RunningBalanceValueOfShares=0;


 END LOOP accounts_loop;
SET l_done=0;
 CLOSE accountNumbersShares;

END//

 DELIMITER ;






/*=======DEVIDEND PAYMENT =====================*/





CREATE TABLE `SavingsInterestPaymentDaily` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,

  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
     `InterestComputedRuningBalance` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




CREATE TABLE `SavingsInterestPaymentMonthly` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
   `InterestComputedRuningBalance` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `SavingsInterestPaymentAnnually` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `SavingsAndSharesInterestPaymentSummury` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `sharesinterestpaymentannually` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `sharesinterestpaymentdaily` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `InterestComputedRuningBalance` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `sharesinterestpaymentmonthly` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `InterestComputedRuningBalance` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS interestComputed;

CREATE TABLE `interestComputed` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `loanId` varchar(45) DEFAULT '0',
  `DueDate` date NOT NULL DEFAULT '1970-01-01',
  `PrincimpalInvolved`  double DEFAULT NULL,
  `InterestInvolved`  double DEFAULT NULL,
  `loanStatusI` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
DROP PROCEDURE IF EXISTS pmms.devidendPaymentOnSavings;
 
 	DELIMITER //


 CREATE PROCEDURE devidendPaymentOnSavings() READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30);DECLARE theAccountDate1 DATE; DECLARE anyDateInYear DATE;DECLARE rateUsed INTEGER;

 DECLARE ledgerBalance1 INTEGER;DECLARE amountComputed INTEGER;DECLARE monthlySummations INTEGER;DECLARE lastDate DATE;DECLARE TerminatiOnDate DATE;
 
 DECLARE monthlyTotals INTEGER DEFAULT 0; DECLARE l_done INTEGER DEFAULT 0;DECLARE finalTotals INTEGER DEFAULT 0;

 DECLARE forSelectingAccountNumbers CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '05502%10';


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



SELECT SavingsStartDate,SavingsRateUsed INTO anyDateInYear,rateUsed from SavingsSharesComputationParameters;

 OPEN forSelectingAccountNumbers;


accounts_loop: LOOP 

 FETCH forSelectingAccountNumbers into accountNumber;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SET @theAccountDate=MAKEDATE(year(anyDateInYear),1);


Date_loop: LOOP 


SET lastDate=LAST_DAY(DATE_ADD(@theAccountDate, INTERVAL 12-MONTH(@theAccountDate) MONTH));


IF @theAccountDate=lastDate THEN
SELECT @theAccountDate,lastDate,accountNumber;
SET @tableName=CONCAT('bsanca',accountNumber);

CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST('SELECT ledger_balance INTO @ledgerBalance from  ' AS CHAR CHARACTER SET utf8),@tableName,CAST('  WHERE trn_date= 'AS CHAR CHARACTER SET utf8),@theAccountDate, CAST(' ORDER BY trn_id DESC LIMIT 1'AS CHAR CHARACTER SET utf8));


  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

SET amountComputed=@ledgerBalance*(rateUsed/100);
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;


 INSERT INTO SavingsInterestPaymentDaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO SavingsInterestPaymentMonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;

SET theAccountDate1=@theAccountDate;

LEAVE Date_loop;

END IF;

SET @tableName=CONCAT('bsanca',accountNumber);

CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST('SELECT ledger_balance INTO @ledgerBalance from  ' AS CHAR CHARACTER SET utf8),@tableName,CAST('  WHERE trn_date= 'AS CHAR CHARACTER SET utf8),@theAccountDate, CAST(' ORDER BY trn_id DESC LIMIT 1'AS CHAR CHARACTER SET utf8));


  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

SET amountComputed=@ledgerBalance*(rateUsed/100);
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;


 INSERT INTO SavingsInterestPaymentDaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO SavingsInterestPaymentMonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;


SET theAccountDate1=@theAccountDate;
SET ledgerBalance1=@ledgerBalance;
SET @theAccountDate=DATE_ADD(@theAccountDate, INTERVAL 1 DAY);
 


 END LOOP Date_loop;

  INSERT INTO SavingsInterestPaymentAnnually VALUES(null,theAccountDate1,MONTHNAME(theAccountDate1),YEAR(theAccountDate1),@accountName,accountNumber,ledgerBalance1,1,rateUsed,finalTotals,'Not Yet','NA','NA','NA');

SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingAccountNumbers;



END//

 DELIMITER ;
CALL devidendPaymentOnSavings();
 


DROP PROCEDURE IF EXISTS pmms.devidendPaymentOnShares;
 
 	DELIMITER //


 CREATE PROCEDURE devidendPaymentOnShares() READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30);
 DECLARE theAccountDate1 DATE;
  DECLARE anyDateInYear DATE;
  DECLARE rateUsed INTEGER;

 DECLARE ledgerBalance1 INTEGER;
 DECLARE amountComputed INTEGER;
 DECLARE monthlySummations INTEGER;
 DECLARE lastDate DATE;
 DECLARE TerminatiOnDate DATE;
 
 DECLARE monthlyTotals INTEGER DEFAULT 0;
  DECLARE l_done INTEGER DEFAULT 0;
  DECLARE finalTotals INTEGER DEFAULT 0;

 DECLARE forSelectingAccountNumbers CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '05502%10';
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



SELECT ShareStartDate,SharesRateUsed INTO anyDateInYear,rateUsed from SavingsSharesComputationParameters;

 OPEN forSelectingAccountNumbers;


accounts_loop: LOOP 

 FETCH forSelectingAccountNumbers into accountNumber;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SET @theAccountDate=MAKEDATE(year(anyDateInYear),1);


Date_loop: LOOP 


SET lastDate=LAST_DAY(DATE_ADD(@theAccountDate, INTERVAL 12-MONTH(@theAccountDate) MONTH));


IF @theAccountDate=lastDate THEN
SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST('SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date= 'AS CHAR CHARACTER SET utf8),@theAccountDate, CAST(' ORDER BY trn_id DESC LIMIT 1'AS CHAR CHARACTER SET utf8));


  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

IF @ledgerBalance>0 THEN

SET amountComputed=@ledgerBalance*(rateUsed/100);
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;


 INSERT INTO SavingsInterestPaymentDaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO SavingsInterestPaymentMonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;
END IF;

SET theAccountDate1=@theAccountDate;

LEAVE Date_loop;

END IF;

SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST('SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date= 'AS CHAR CHARACTER SET utf8),@theAccountDate, CAST(' ORDER BY trn_id DESC LIMIT 1'AS CHAR CHARACTER SET utf8));


  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

IF @ledgerBalance>0 THEN

SET amountComputed=@ledgerBalance*(rateUsed/100);
 
 SET amountComputed=amountComputed/DAY(LAST_DAY(@theAccountDate));

 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;


 INSERT INTO sharesinterestpaymentdaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO sharesinterestpaymentmonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;
END IF;



SET theAccountDate1=@theAccountDate;
SET ledgerBalance1=@ledgerBalance;
SET @theAccountDate=DATE_ADD(@theAccountDate, INTERVAL 1 DAY);
 


 END LOOP Date_loop;

  INSERT INTO sharesinterestpaymentannually VALUES(null,theAccountDate1,MONTHNAME(theAccountDate1),YEAR(theAccountDate1),@accountName,accountNumber,ledgerBalance1,1,rateUsed,finalTotals,'Not Yet','NA','NA','NA');

SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingAccountNumbers;



END//

 DELIMITER ;
 
 
 
 /*============================================================GENERAL SUMMURY REPORT====================================================================================*/
 
 
			
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


DROP PROCEDURE IF EXISTS statsTracking;
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
			
DROP PROCEDURE IF EXISTS updateCountStatsCustomers;
	DELIMITER //
 CREATE PROCEDURE updateCountStatsCustomers( )
 BEGIN
 
DECLARE existingAccounts INTEGER; DECLARE ItemIdu INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT COUNT(account_number) INTO existingAccounts FROM account_created_store WHERE  account_number like '05502%10';
UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;	




DROP PROCEDURE IF EXISTS 	countNumberValueOfActiveSavings;	
DELIMITER //
CREATE PROCEDURE countNumberValueOfActiveSavings( )
BEGIN
 
DECLARE ItemIdu INTEGER;DECLARE totalValueSaving INTEGER;DECLARE totalNumberOfSavings INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT SUM(running_balance),COUNT(running_balance) INTO totalValueSaving,totalNumberOfSavings FROM account_created_store WHERE  account_number like '05502%';
UPDATE summurystats SET TotalValueOfSavings=totalValueSaving WHERE ItemId=ItemIdu;

END//
DELIMITER ;



 DROP PROCEDURE IF EXISTS 	countNumberValueOfActiveSavings2;	
DELIMITER //
CREATE PROCEDURE countNumberValueOfActiveSavings2( )
BEGIN
 
DECLARE ItemIdu INTEGER;DECLARE totalValueSaving INTEGER;DECLARE totalNumberOfSavings INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT SUM(running_balance),COUNT(running_balance) INTO totalValueSaving,totalNumberOfSavings FROM account_created_store WHERE running_balance>0 AND  account_number like '05502%';
UPDATE summurystats SET TotalNumberOfActiveSavingsCustomers=totalNumberOfSavings WHERE ItemId=ItemIdu;

END//
DELIMITER ;
 
 
 
  DROP PROCEDURE IF EXISTS updateSavingsMade;
 
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
 

DROP PROCEDURE IF EXISTS updateSavingsRemoved;

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
 
 DECLARE accountNumbersShare CURSOR FOR SELECT DISTINCT account_number FROM shares_run_bal;

 DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreAccounts=1;


SET noMoreAccounts=0;

OPEN accountNumbersShare;

accountsLoop:REPEAT

 FETCH accountNumbersShare INTO memberAccountNumber;

 IF noMoreAccounts=0 THEN

 SELECT running_balance_n_shares,running_balance_v_shares INTO existingNumberOfShares,existingValueOfShares FROM shares_run_bal WHERE account_number= memberAccountNumber ORDER BY trn_id DESC LIMIT 1;


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






 DROP PROCEDURE IF EXISTS countNumberValueOfActiveDeposits;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfActiveDeposits( )
 BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueDeposits INTEGER;
 
 DECLARE totalNumberOfDeposits INTEGER;
 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(running_balance) INTO totalValueDeposits FROM account_created_store WHERE  account_number like '05502%';
  
  SELECT COUNT(running_balance) INTO totalNumberOfDeposits FROM account_created_store WHERE running_balance>0 AND account_number like '05502%';

UPDATE summurystats SET TotalValueOfDeposits=totalValueDeposits WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;


 DROP PROCEDURE IF EXISTS countNumberValueOfActiveDeposits2;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfActiveDeposits2( )
 BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE totalValueDeposits INTEGER;
 
 DECLARE totalNumberOfDeposits INTEGER;
 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
  
  SELECT SUM(running_balance) INTO totalValueDeposits FROM account_created_store WHERE running_balance>0 AND account_number like '05502%';
  
  SELECT COUNT(running_balance) INTO totalNumberOfDeposits FROM account_created_store WHERE running_balance>0 AND account_number like '05502%';

UPDATE summurystats SET TotalNumberOfCustomersWithDeposits=totalNumberOfDeposits WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;



 DROP PROCEDURE IF EXISTS countNumberValueOfActiveLoans;
 
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
 

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;

  
  SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoans, totalValueLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed';
    SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle1, totalValueLoansCycle1 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle1';
     SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle2, totalValueLoansCycle2 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle2';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle3, totalValueLoansCycle3 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle3';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle4, totalValueLoansCycle4 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle4';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle5, totalValueLoansCycle5 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle5';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle6, totalValueLoansCycle6 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle6';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycle7, totalValueLoansCycle7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle7';
 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfLoansCycleAbove7, totalValueLoansCycleAbove7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND NOT (LoanCycle='Cycle1' OR LoanCycle='Cycle2' OR LoanCycle='Cycle3' OR LoanCycle='Cycle4' OR LoanCycle='Cycle5' OR LoanCycle='Cycle6' OR LoanCycle='Cycle7');


 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoans, totalValueCustomerLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND loan_id LIKE 'newloan05502%10';

 SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle1, totalValueCustomerLoansCycle1 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle1' AND loan_id LIKE 'newloan0552%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle2, totalValueCustomerLoansCycle2 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle2' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle3, totalValueCustomerLoansCycle3 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle3' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle4, totalValueCustomerLoansCycle4 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle4' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle5, totalValueCustomerLoansCycle5 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle5' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle6, totalValueCustomerLoansCycle6 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle6' AND loan_id LIKE 'newloan05502%10';

SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycle7, totalValueCustomerLoansCycle7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND LoanCycle='Cycle7' AND loan_id LIKE 'newloan05502%10';
SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfCustomerLoansCycleAbove7, totalValueCustomerLoansCycleAbove7 FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Disbursed' AND NOT (LoanCycle='Cycle1' OR LoanCycle='Cycle2' OR LoanCycle='Cycle3' OR LoanCycle='Cycle4' OR LoanCycle='Cycle5' OR LoanCycle='Cycle6' OR LoanCycle='Cycle7') AND loan_id LIKE 'newloan05502%10';


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


DROP PROCEDURE IF EXISTS updateLoanAmountNumberDisbursements;

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

SELECT ItemId,TotalNumberOfIndividualLoansDisbursed,TotalValueOfIndividualLoansDisbursed INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfIndividualLoansDisbursed=existingNumberOfLoans,TotalValueOfIndividualLoansDisbursed=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;

END IF;

 IF LoanCycleS='Cycle1' THEN 
 
SELECT ItemId,TotalNumberOfLoansDisbursedCycle1,TotalValueOfLoansDisbursedCycle1 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
SET existingNumberOfLoans=existingNumberOfLoans+1;
SET existingValueOfLoans=existingValueOfLoans+principalAmount;

UPDATE summurystats SET TotalNumberOfLoansDisbursedCycle1=existingNumberOfLoans,TotalValueOfLoansDisbursedCycle1=existingValueOfLoans WHERE ItemId=ItemIdu;

SET existingNumberOfLoans=0;
SET existingValueOfLoans=0;


 IF BorrowingCategoryS='Group' THEN
SELECT ItemId,TotalNumberOfGroupLoansDisbursedCycle1,TotalValueOfGroupLoansDisbursedCycle1 INTO ItemIdu,existingNumberOfLoans,existingValueOfLoans FROM summurystats  ORDER BY ItemId DESC Limit 1;
 
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



 DROP PROCEDURE IF EXISTS countNumberValueOfCompletedWrittenOffLoans;
 
 	DELIMITER //
 CREATE PROCEDURE countNumberValueOfCompletedWrittenOffLoans() BEGIN
 
 DECLARE ItemIdu INTEGER;DECLARE totalValueCompletedLoans INTEGER;DECLARE totalNumberOfCompltedLoans INTEGER;

  DECLARE totalValueWrittenOffLoans INTEGER;DECLARE totalNumberOfWrittenOffLoans INTEGER;

  SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;

  
  SELECT COUNT(princimpal_amount),SUM(princimpal_amount) INTO totalNumberOfCompltedLoans, totalValueCompletedLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Completed';

   SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfWrittenOffLoans, totalValueWrittenOffLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='WrittenOff';
   

UPDATE summurystats SET TotalNumberOfLoansCompleted=totalNumberOfCompltedLoans,TotalValueOfLoansCompleted=totalValueCompletedLoans,TotalNumberOfLoansWrittenOff=totalNumberOfWrittenOffLoans,TotalValueOfLoansWrittenOff=totalValueWrittenOffLoans WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;







 DROP PROCEDURE IF EXISTS loanRepaymentsUpdatesAll;
 
 	DELIMITER //
 CREATE PROCEDURE loanRepaymentsUpdatesAll(IN typOfRepayment VARCHAR(100),IN loanId VARCHAR(100),IN InstalmentNo INTEGER,IN amountPAI INTEGER) BEGIN
 
 DECLARE ItemIdu INTEGER;
 
 DECLARE InstalmentDueDate DATE;

 DECLARE ExistingNumber INTEGER;

  DECLARE ExistingValue INTEGER;


 SELECT instalment_due_date INTO InstalmentDueDate  FROM pmms_loans.new_loan_appstoreamort WHERE instalment_no=InstalmentNo AND master2_id=loanId;


IF typOfRepayment='updateNewLoanPrincipalNow' THEN 


  SELECT ItemId,TotalNumberOfAllPrincipalLoanRepayments,TotalValueOfAllPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfAllPrincipalLoanRepayments=ExistingNumber,TotalValueOfAllPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

IF InstalmentDueDate=CURDATE() THEN 

  SELECT ItemId,TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly,TotalValueOfPrincipalLoanRepaymentsDueLoansOnly INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly=ExistingNumber,TotalValueOfPrincipalLoanRepaymentsDueLoansOnly=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

IF InstalmentDueDate<CURDATE() THEN

 SELECT ItemId,TotalNumberOfArrearsPrincipalLoanRepayments,TotalValueOfArrearsPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfArrearsPrincipalLoanRepayments=ExistingNumber,TotalValueOfArrearsPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
 SET   ExistingValue=0;
END IF;


IF InstalmentDueDate>CURDATE() THEN 

  SELECT ItemId,TotalNumberOfEarlyPrincipalLoanRepayments,TotalValueOfEarlyPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfEarlyPrincipalLoanRepayments=ExistingNumber,TotalValueOfEarlyPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;
END IF;

IF InstalmentDueDate>=CURDATE() THEN 

  SELECT ItemId,TotalNumberOfLoanRepaymentsMinusArrears,TotalValueOfLoanRepaymentsMinusArrears INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
 SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfLoanRepaymentsMinusArrears=ExistingNumber,TotalValueOfLoanRepaymentsMinusArrears=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
 SET   ExistingValue=0;
END IF;

END IF;




IF typOfRepayment='updateNewInterestNow' THEN 


 SELECT ItemId,TotalNumberOfAllInterestPayments,TotalValueOfInterestReceived INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfAllInterestPayments=ExistingNumber,TotalValueOfInterestReceived=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;



IF InstalmentDueDate=CURDATE() THEN 
 SELECT ItemId,TotalNumberOfInterestPaymentsDueLoansOnly,TotalValueOfInterestPaymentsDueLoansOnly INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfInterestPaymentsDueLoansOnly=ExistingNumber,TotalValueOfInterestPaymentsDueLoansOnly=ExistingValue WHERE ItemId=ItemIdu;
 SET   ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

IF InstalmentDueDate<CURDATE() THEN 

 SELECT ItemId,TotalNumberOfArrearsInterestPayments,TotalValueOfArrearsInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfArrearsInterestPayments=ExistingNumber,TotalValueOfArrearsInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

IF InstalmentDueDate>CURDATE() THEN 
 SELECT ItemId,TotalNumberOfEarlyInterestPayments,TotalValueOfEarlyInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET   ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfEarlyInterestPayments=ExistingNumber,TotalValueOfEarlyInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;


END IF;

END IF;



IF typOfRepayment='updateNewLoanPenaltyNow' THEN 

SELECT ItemId,TotalNumberOfAllLoanPenaltyPayments,TotalValueOfAllLoanPenaltyPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

  SET  ExistingNumber=ExistingNumber+1;
  SET  ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfAllLoanPenaltyPayments=ExistingNumber,TotalValueOfAllLoanPenaltyPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

 
IF typOfRepayment='updateNewAccumulatedInterestNow' THEN 
SELECT ItemId,TotalNumberOfAllAccumulatedInterestPayments,TotalValueOfAllAccumulatedInterestPayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

   SET ExistingNumber=ExistingNumber+1;
   SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfAllAccumulatedInterestPayments=ExistingNumber,TotalValueOfAllAccumulatedInterestPayments=ExistingValue WHERE ItemId=ItemIdu;
  SET  ExistingNumber=0;
  SET  ExistingValue=0;

END IF;

SELECT ItemId,TotalNumberOfAllInterestAndPrincipalLoanRepayments,TotalValueOfAllInterestAndPrincipalLoanRepayments INTO ItemIdu,ExistingNumber,ExistingValue  FROM summurystats ORDER BY ItemId DESC Limit 1;

 SET ExistingNumber=ExistingNumber+1;
   SET ExistingValue=ExistingValue+amountPAI;

UPDATE summurystats SET TotalNumberOfAllInterestAndPrincipalLoanRepayments=ExistingNumber,TotalValueOfAllInterestAndPrincipalLoanRepayments=ExistingValue WHERE ItemId=ItemIdu;
   SET ExistingNumber=0;
  SET  ExistingValue=0;

 
 END//
 DELIMITER ;




 DROP PROCEDURE IF EXISTS creatingArrearsLoanSummury;
 
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
 

 DECLARE ForSelectingIds CURSOR FOR SELECT DISTINCT master2_id  FROM pmms_loans.new_loan_appstoreamort WHERE instalment_due_date<CURDATE()  AND NOT instalment_status='P' AND master2_id LIKE 'newloan%';


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
UPDATE summurystats SET TotalValueOfPrincipalOutStandingArrears=ExistingPrinOnlyArrears,TotalValueOfInterestOutStandingArrears=ExistingIntnOnlyArrears,TotalNumberOfLoansInArrears=ExistingNumberArrears,TotalValueOfPrincipalLoansInArrears=ExistingTotalPrinInArrears,TotalValueOfInterestInArrears=ExistingTotalIntInArrears WHERE ItemId=ItemIdu;

SET l_done=0;

 OPEN ForSelectingIds;


accounts_loop: LOOP 

 FETCH ForSelectingIds into loanIds;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
SELECT loanIds;

SELECT SUM(PrincipalRemaining),SUM(InterestRemaing) INTO principalInArrears,interestInArrears FROM pmms_loans.new_loan_appstoreamort WHERE instalment_due_date<CURDATE() AND master2_id=loanIds AND NOT instalment_status='P';

SELECT SUM(PrincipalRemaining),SUM(InterestRemaing) INTO TotalprincipalInArrears,TotalinterestInArrears FROM pmms_loans.new_loan_appstoreamort WHERE master2_id=loanIds AND NOT instalment_status='P';
 
 SELECT TotalValueOfPrincipalOutStandingArrears,TotalValueOfInterestOutStandingArrears,TotalNumberOfLoansInArrears,TotalValueOfPrincipalLoansInArrears,TotalValueOfInterestInArrears INTO ExistingPrinOnlyArrears,ExistingIntnOnlyArrears,ExistingNumberArrears,ExistingTotalPrinInArrears,ExistingTotalIntInArrears  FROM summurystats ORDER BY ItemId DESC Limit 1;



SET @NowExistingPrinOnlyArrears=ExistingPrinOnlyArrears+principalInArrears;

SET @NowExistingIntnOnlyArrears=ExistingIntnOnlyArrears+interestInArrears;

SET @NowExistingNumberArrears=ExistingNumberArrears+1;


SET @NowExistingTotalPrinInArrears=ExistingTotalPrinInArrears+TotalprincipalInArrears;

SET @NowExistingTotalIntInArrears=ExistingTotalIntInArrears+TotalinterestInArrears;

/* SELECT  @NowExistingPrinOnlyArrears,@NowExistingIntnOnlyArrears,@NowExistingNumberArrears; */
UPDATE summurystats SET TotalValueOfPrincipalOutStandingArrears=@NowExistingPrinOnlyArrears,TotalValueOfInterestOutStandingArrears=@NowExistingIntnOnlyArrears,TotalNumberOfLoansInArrears=@NowExistingNumberArrears,TotalValueOfPrincipalLoansInArrears=@NowExistingTotalPrinInArrears,TotalValueOfInterestInArrears=@NowExistingTotalIntInArrears WHERE ItemId=ItemIdu;

/* SET l_done=0; */
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
  
  SELECT SUM(running_balance) INTO totalValueOfCash FROM account_created_store WHERE  account_number like '0112300%';
  
  SELECT SUM(running_balance) INTO totalValueOfBankBalance FROM account_created_store WHERE  account_number like '0112200%';
   
    SELECT SUM(running_balance) INTO totalValueOfAssets FROM account_created_store WHERE  account_number like '011%';
  
  SELECT SUM(running_balance) INTO totalValueOfReceivables FROM account_created_store WHERE   account_number like '0113100%';
   
     SELECT SUM(running_balance) INTO totalValueOfPayables FROM account_created_store WHERE  account_number like '055000%';
   
    SELECT SUM(running_balance) INTO totalValueOfFixedAssets FROM account_created_store WHERE  (account_number like '01100%' OR account_number like  '01101%' OR account_number like   '01102%' OR account_number like   '01103%' OR account_number like   '01104%' OR  account_number like  '01105%' OR account_number like   '01106%' OR  account_number like  '01136%');
    
    CALL InterestReceivable(@interestReceivable);
    CALL currentAssets(@totalCurrentAssets);
 SET totalCurrentAssetsIncludingInterestR=@interestReceivable+@totalCurrentAssets;
 
  SELECT SUM(running_balance) INTO totalValueOfMainIncome FROM account_created_store WHERE  (account_number like '03300%' OR account_number like  '03301%' OR account_number like '03302%' OR account_number like  '03303%');

   SELECT SUM(running_balance) INTO totalValueOfOtherIncome FROM account_created_store WHERE  ( account_number like  '03304%' OR account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%' OR account_number like  '03309%' OR account_number like  '03310%' OR account_number like '03311%' OR account_number like  '03312%' OR account_number like  '03313%' OR account_number like '03314%' OR account_number like  '03315%' OR account_number like  '03316%' OR account_number like '03317%' OR account_number like  '03318%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
    
 SELECT SUM(running_balance) INTO totalValueOfIncome FROM account_created_store WHERE  account_number like '033%';
  
  SELECT SUM(running_balance) INTO totalValueOfExpenses FROM account_created_store WHERE  account_number like '022%';
  
    SELECT SUM(running_balance) INTO totalValueOfLiabilities FROM account_created_store WHERE account_number like '055%';
  
 SELECT SUM(running_balance) INTO totalValueOfCapital FROM account_created_store WHERE  account_number like '044%';
  
UPDATE summurystats SET TotalValueOfCashBalances=totalValueOfCash,TotalValueOfBankBalances=totalValueOfBankBalance,TotalValueOfAssets=totalValueOfAssets,TotalValueOfReceivables=totalValueOfReceivables,TotalValueOfPayables=totalValueOfPayables,TotalValueOfFixedAssets=totalValueOfFixedAssets,TotalValueOfCurrentAssetsIncludingInterestReceivable=totalCurrentAssetsIncludingInterestR,TotalValueOfCurrentAssetsMinusInterestReceivable=@totalCurrentAssets,
TotalValueOfMainIncome=totalValueOfMainIncome,TotalValueOfOtherIncome=totalValueOfOtherIncome,TotalValueOfIncome=totalValueOfIncome,TotalValueOfExpenses=totalValueOfExpenses,TotalValueOfLiabilities=totalValueOfLiabilities,TotalValueOfCapital=totalValueOfCapital WHERE ItemId=ItemIdu;

 END//
 DELIMITER ;



DROP PROCEDURE IF EXISTS InterestReceivable;
 
 	DELIMITER //

   CREATE PROCEDURE  InterestReceivable (OUT interestReceivable VARCHAR(30)) BEGIN

SELECT SUM(TotalInterestRemaining) INTO interestReceivable FROM pmms_loans.new_loan_appstore1 WHERE  loan_cycle_status='Disbursed';

IF (interestReceivable IS NULL) THEN

SET interestReceivable=0.0;
END IF;

     END//
 DELIMITER ;



 DROP PROCEDURE IF EXISTS currentAssets;
 
 	DELIMITER //

   CREATE PROCEDURE  currentAssets (OUT currentAssets VARCHAR(30)) BEGIN

SELECT SUM(running_balance) INTO currentAssets FROM account_created_store WHERE (account_number like '01113%' OR account_number like  '01114%' OR account_number like   '01115%' OR account_number like   '01116%' OR account_number like   '01117%' OR  account_number like  '01118%' OR account_number like   '01119%' OR  account_number like  '01120%' OR  account_number like  '01121%' OR  account_number like  '01122%' OR  account_number like  '01123%' OR  account_number like  '01124%' OR  account_number like  '01125%' OR  account_number like  '01126%' OR  account_number like  '01127%' OR  account_number like  '01128%' OR  account_number like  '01129%' OR  account_number like  '01130%' OR  account_number like  '01131%' OR  account_number like  '01132%' OR  account_number like  '01133%' OR  account_number like  '01134%' OR  account_number like  '01135%');

IF (currentAssets IS NULL) THEN

SET currentAssets=0.0;
END IF;

     END//
 DELIMITER ;





DROP PROCEDURE IF EXISTS updateCountStatsCustomersReduce;

DELIMITER //

 CREATE PROCEDURE updateCountStatsCustomersReduce() BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;

 SELECT ItemId,TotalNumberOfCustomers INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END//

 DELIMITER ;



 DROP PROCEDURE IF EXISTS updateCountStatsAccountsReduce;

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




 DROP PROCEDURE IF EXISTS updateMoreLoanDetails;

DELIMITER //

 CREATE PROCEDURE updateMoreLoanDetails( ) BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;
 SELECT ItemId,TotalNumberOfAccounts INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfAccounts=existingAccounts WHERE ItemId=ItemIdu;

END //

DELIMITER ;



DROP PROCEDURE IF EXISTS deleteFromMaster;		
DELIMITER //

CREATE PROCEDURE deleteFromMaster(IN accountNumber VARCHAR(30)) BEGIN

DELETE FROM master WHERE account_number=accountNumber;

END //
DELIMITER ;




DROP PROCEDURE IF EXISTS SummuryReportGenerator;

DELIMITER //

 CREATE PROCEDURE SummuryReportGenerator(IN theStringGeneral MEDIUMTEXT,IN startDate DATE,IN endDate DATE,IN numStrng INT ) BEGIN
DROP TABLE IF EXISTS temp_summuryReport;
CREATE  TEMPORARY TABLE temp_summuryReport(temp_id VARCHAR(10),temp_NarrationC VARCHAR(200),temp_StartinFigure DOUBLE,temp_Difference DOUBLE,temp_FinalFigure DOUBLE,temp_Comment VARCHAR(200));


  SET @n=1;
  STRINGLOOP:LOOP
    SET numStrng=numStrng-1;
  IF numStrng<=0 THEN
  
  LEAVE STRINGLOOP;
  
  END IF;
  
  SET @A=-(@n);
/*   SELECT @A; */
 SET @mainStrng= SUBSTRING_INDEX(SUBSTRING_INDEX(theStringGeneral, ';', @n), ';',-1);
 
  SET @selectString=SUBSTRING_INDEX(@mainStrng, ':', 1);
  
   SET @summuryString=SUBSTRING_INDEX(@mainStrng, ':', -1);
  
/*   SELECT @mainStrng,@selectString,@summuryString; */
  
  
  
  SET @sql_text2 = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8),@selectString ,CAST(" INTO @startingValue FROM summurystats WHERE ItemDate='" AS CHAR CHARACTER SET utf8),startDate,CAST("'" AS CHAR CHARACTER SET utf8));
/* select @sql_text2; */
  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;

    SET @sql_text1 = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8),@selectString ,CAST(" INTO @endingValueValue FROM summurystats WHERE ItemDate='" AS CHAR CHARACTER SET utf8),endDate,CAST("'" AS CHAR CHARACTER SET utf8));
/* 
select @sql_text1; */

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;
  

	

	IF ISNULL(@startingValue) THEN
	SET @startingValue=0;
	END IF;
	
	IF ISNULL(@endingValueValue ) THEN
	SET @endingValueValue=0;
	END IF;
	/* SELECT  @startingValue,@endingValueValue; */
	
	
	SET @differenceNu=@endingValueValue-@startingValue;
/* 	SELECT @differenceNu; */
	IF @differenceNu<0 THEN
	

	
	IF @startingValue=0 THEN
	SET @percentRe=100;
	ELSE
		SET @percentRe=ROUND((ABS(@differenceNu)/@startingValue*100),2);
	END IF;
	
	 SET @indicatorNu=concat(@percentRe,CAST('%  Reduction' AS CHAR CHARACTER SET utf8));
 
	
	
	
	ELSEIF @differenceNu>0 THEN
	
	
	
	IF @startingValue=0 THEN
	SET @percentRe=100;
	ELSE
	SET @percentRe=ROUND((ABS(@differenceNu)/@startingValue*100),2);
	END IF;
	
	
	
/* 	SELECT @percentRe; */
	 SET @indicatorNu=concat(@percentRe,CAST('%  Increment' AS CHAR CHARACTER SET utf8));
/*  SELECT @indicatorNu; */
	ELSEIF  @differenceNu=0 THEN
	
	 SET @indicatorNu='No Change';
 
	END IF;
	
	SET @n1=@n;
  SET @n=@n+1;

 
INSERT INTO temp_summuryReport VALUES(@n1,@summuryString,@startingValue,ABS(@differenceNu),@endingValueValue,@indicatorNu);
  
  END LOOP STRINGLOOP;




SET @startingValue=null;
SET @endingValueValue=null;
SELECT temp_id,temp_NarrationC,temp_StartinFigure,temp_Difference,temp_FinalFigure,temp_Comment FROM  temp_summuryReport;
END //

DELIMITER ;


                             

/*=====================ADMIN COSTS AND OTHERS==========================================================================*/


CREATE TABLE `AdminCostsComputationParameters` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `SavingsStartDate` DATE NOT NULL DEFAULT '1970-01-01',
   `ShareStartDate` DATE NOT NULL DEFAULT '1970-01-01',
  `Year` INTEGER,
  `YearChanged` INTEGER,
  `AdminCostFees` DOUBLE,
  `ShareExclude` INTEGER,
   `SavingsIncludUsed` INTEGER,
  `SavingsExclude` INTEGER,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `AdminCostIndividualTrackerAll` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `AdminCostIndividualTrackerStatic` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




/*==============================GENERAL LEDGER ADJUSTMENTS=========================================================================*/



 DROP PROCEDURE IF EXISTS adjustTrnIdS;
 
 	DELIMITER //

   CREATE PROCEDURE  adjustTrnIdS (IN bsancaAccountNumber VARCHAR(30),IN dateInQuestion DATE,OUT trId INTEGER) BEGIN
   
     SET @sql_text9 = concat(CAST(" SELECT  trn_id INTO @trIdV FROM  " AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST("  WHERE trn_date='"AS CHAR CHARACTER SET utf8),dateInQuestion,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
SELECT @sql_text9;
PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;
   
       SET @sql_text = concat(CAST(' SELECT  trn_id INTO @LasttrId FROM  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;

SELECT @trIdV ,@LasttrId ;

SET trId=@trIdV;
	  
	  TxIdsXX:LOOP
	  
	  
	/*   SELECT @LasttrId; */
	  
	SET   @oldIdC=@LasttrId+1;
	  
	 SET    @newIdC=@LasttrId-1;
	  
	     SET @sql_text = concat(CAST('UPDATE  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  SET trn_id= ' AS CHAR CHARACTER SET utf8),@oldIdC,CAST('  WHERE trn_id=  ' AS CHAR CHARACTER SET utf8),@LasttrId);
       PREPARE stmt FROM @sql_text;
       EXECUTE stmt;
     DROP PREPARE stmt;
	    SET @sql_text = concat(CAST('UPDATE  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  SET trn_id=  ' AS CHAR CHARACTER SET utf8),@LasttrId,CAST(' WHERE trn_id=  ' AS CHAR CHARACTER SET utf8),@newIdC);
	 
       PREPARE stmt FROM @sql_text;
       EXECUTE stmt;
     DROP PREPARE stmt;
	  
	
		
		SET @LasttrId=@newIdC;
		
		/*   SELECT @LasttrId; */
	  
	  IF @newIdC=trId OR @newIdC=1000 THEN
	  
	  LEAVE TxIdsXX;
	  
	  END IF;
	  
	  
	  END LOOP TxIdsXX;
   


     END//
 DELIMITER ;
 
 
 /*===============TRIGGER TESTING======================================*/
 
 
DROP TRIGGER IF EXISTS TSANCA01123000110;

DELIMITER //

CREATE TRIGGER  TSANCA01123000110 BEFORE INSERT ON BSANCA01123000110 FOR EACH ROW BEGIN

IF(NEW.other_one LIKE '%Cr%') THEN 
        
SET @creditAccount=NEW.account_number;
        
 SET @debitAccount=NEW.credit_account_no;

CALL accountNma(@creditAccount,@accountName);

INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)

VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
        
 END IF;
        
IF(NEW.other_one LIKE '%Dr%') THEN 

SET @creditAccount=NEW.credit_account_no;

SET @debitAccount=NEW.account_number;
        
CALL accountNma(@debitAccount,@accountName);

INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)

 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);    

END IF;



UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;

CALL   updateMaster01123000110(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id);

END;

DELIMITER ;


CREATE PROCEDURE updateMaster01123000110(IN TrnDate DATE,IN accountNumber VARCHAR(20),IN NewLedgerBalance VARCHAR(20),IN StaffId VARCHAR(8))
BEGIN

CALL priviousBalance01123000110(accountNumber,@previouslyAdded);

CALL currentMasterBalance0112300010(@currentlAdded);

CALL accountNma(accountNumber,@accountName);
		
 SET @newMasterBalance=(@currentlAdded-@previouslyAdded)+NewLedgerBalance;

INSERT INTO BSANCA0112300010(trn_id,trn_date,value_date,account_name,account_number,account_balance,master_balance,staff_id) 

VALUES (null, TrnDate,TrnDate,@accountName,accountNumber,NewLedgerBalance,@newMasterBalance,StaffId); 
END;
 
 
 /*=========================================SEQUENCE NUMBERING SYSTEM======================================================*/



DROP TABLE IF EXISTS `sequenceNumbers`;

CREATE TABLE `sequenceNumbers` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupNumber` int(11) DEFAULT 10000,
  `trnSequencyNumber` int(11) DEFAULT 1,
  `batchNumber` int(11) DEFAULT 30000,
  `budgetEstimateNumber` int(11) DEFAULT 40000,
  `otherNumbers1` int(11) DEFAULT 50000,
  `otherNumbers2` int(11) DEFAULT 60000,
  `otherNumbers3` int(11) DEFAULT 70000,
  `otherNumbers4` int(11) DEFAULT 80000,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO sequenceNumbers VALUES(null,10000,1,30000,40000,50000,60000,70000,80000);


DROP PROCEDURE IF EXISTS groupNumber;

DELIMITER //

CREATE PROCEDURE groupNumber() READS SQL DATA BEGIN
 
 
 SELECT  groupNumber  INTO @theGroupNumber FROM sequenceNumbers;
 
 SET @theGroupNumber=@theGroupNumber+1;

 UPDATE sequenceNumbers SET groupNumber=@theGroupNumber;

SELECT @theGroupNumber ;

END //

DELIMITER ;

CALL groupNumber();






DROP PROCEDURE IF EXISTS batchNumber;

DELIMITER //

CREATE PROCEDURE batchNumber() READS SQL DATA BEGIN
 
 
 SELECT   batchNumber  INTO @theGroupNumber FROM sequenceNumbers;
 
 SET @theGroupNumber=@theGroupNumber+1;

 UPDATE sequenceNumbers SET  batchNumber=@theGroupNumber;

SELECT @theGroupNumber ;

END //

DELIMITER ;

CALL batchNumber();




DROP PROCEDURE IF EXISTS TheTrnSequencyNumber;

DELIMITER //

CREATE PROCEDURE TheTrnSequencyNumber() READS SQL DATA BEGIN
 
 
 SELECT   trnSequencyNumber  INTO @theTxnSqueNumber FROM sequenceNumbers;
 
 SET @theTxnSqueNumber=@theTxnSqueNumber+1;
 
 IF @theTxnSqueNumber<10 THEN
   SET   @act= concat(CAST("000" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
   
   ELSEIF @theTxnSqueNumber>=10 AND @theTxnSqueNumber<100 THEN
   
    SET   @act= concat(CAST("00" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
     
     ELSEIF @theTxnSqueNumber>=100 AND @theTxnSqueNumber<1000 THEN
	 
	  SET   @act= concat(CAST("0" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
     
     ELSEIF @theTxnSqueNumber>=1000 THEN
	 
       SET   @act= @theTxnSqueNumber;
	   
   END IF;
 


 UPDATE sequenceNumbers SET  trnSequencyNumber=@act;

SELECT @act ;

END //

DELIMITER ;

CALL TheTrnSequencyNumber();




DROP PROCEDURE IF EXISTS resetTxnSeqNumber;

DELIMITER //

CREATE PROCEDURE resetTxnSeqNumber() READS SQL DATA BEGIN
 

 UPDATE sequenceNumbers SET  trnSequencyNumber=1;

END //

DELIMITER ;
CALL TheTrnSequencyNumber();
CALL resetTxnSeqNumber();
CALL TheTrnSequencyNumber(); 
 
 