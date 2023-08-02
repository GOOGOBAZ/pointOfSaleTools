 /*=========================================================DAVIDED PAYMENT AND RUNNING BALANCE ON SHARES==================================================*/

 DROP PROCEDURE IF EXISTS creatingRunningBalancesOfShares;
 
 	DELIMITER //


 CREATE PROCEDURE creatingRunningBalancesOfShares() READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30);

 DECLARE TrnId INT DEFAULT 0;

DECLARE l_done INT DEFAULT 0;

 DECLARE NoOfSharesRemoved INT DEFAULT 0;
 DECLARE NoOfSharesAdded INT DEFAULT 0;
 DECLARE ValueOfSharesRemoved INT DEFAULT 0;
 DECLARE ValueOfSharesAdded INT DEFAULT 0;
 DECLARE RunningBalanceNumberShares INT DEFAULT 0;
 DECLARE RunningBalanceValueOfShares INT DEFAULT 0;

-- SELECT * FROM pmms.INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE = 'PROCEDURE';

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

CALL creatingRunningBalancesOfShares();


-- DROP TABLE IF EXISTS `interestComputed`;

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


/*=======DEVIDEND PAYMENT =====================*/

/* 

DROP TABLE IF EXISTS `SavingsInterestPaymentDaily`; */

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




-- DROP TABLE IF EXISTS `SavingsInterestPaymentMonthly`;

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


-- DROP TABLE IF EXISTS `SavingsInterestPaymentAnnually`;

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


-- DROP TABLE IF EXISTS `SavingsAndSharesInterestPaymentSummury`;

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


-- DROP TABLE IF EXISTS `sharesinterestpaymentannually`;

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


-- DROP TABLE IF EXISTS `sharesinterestpaymentdaily`;

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

-- DROP TABLE IF EXISTS `sharesinterestpaymentmonthly`;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1; */



-- DROP TABLE IF EXISTS `savingssharescomputationparameters`;

CREATE TABLE `savingssharescomputationparameters` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `SavingsStartDate` date NOT NULL DEFAULT '1970-01-01',
  `ShareStartDate` date NOT NULL DEFAULT '1970-01-01',
  `SharesRateUsed` double DEFAULT NULL,
  `SavingsRateUsed` double DEFAULT NULL,
  `SharesIncludUsed` int(11) DEFAULT NULL,
  `ShareExclude` int(11) DEFAULT NULL,
  `SavingsIncludUsed` int(11) DEFAULT NULL,
  `SavingsExclude` int(11) DEFAULT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--  Shares 14% = 20,202,000/=
-- Savings 5% = 9,484,656/=
-- Totaling to 29,684,656/=
-- Leaves a balance of 315,344/= 
-- I know when u run the reports it will have a plus or minus. The amount agreed is 30m
-- 31/12/2021	02277000110	Return On Investment Expense	Return On Investment Expense	0.0	Active

-- INSERT INTO  savingssharescomputationparameters VALUES(1,	'2019-02-23'	,'2019-03-23',	10.67,	5.33	,1	,1	,1	,1	,'NA',	'NA'	,'NA');


DROP PROCEDURE IF EXISTS pmms.devidendPaymentOnSavings;
 
 	DELIMITER //

 CREATE PROCEDURE devidendPaymentOnSavings() READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30);
 DECLARE theAccountDate1, anyDateInYear,lastDate,TerminatiOnDate DATE;

 DECLARE ledgerBalance1,amountComputed,monthlySummations INTEGER;
 DECLARE rateUsed DOUBLE;
 DECLARE monthlyTotals INTEGER DEFAULT 0;

 DECLARE l_done INTEGER DEFAULT 0;DECLARE finalTotals INTEGER DEFAULT 0;

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
-- SELECT @theAccountDate,lastDate,accountNumber;
-- SET @tableName=CONCAT('bsanca',accountNumber);

CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST("SELECT SavingsRunningBalance INTO @ledgerBalance from  newsavingsmembers" AS CHAR CHARACTER SET utf8), CAST("  WHERE TrnDate<=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate,CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND AccountNumber=" AS CHAR CHARACTER SET utf8), accountNumber,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
--  SELECT @sql_text1;

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

-- SET @sql_text1 = concat(CAST("SELECT ledger_balance INTO @ledgerBalance from  " AS CHAR CHARACTER SET utf8),@tableName,CAST("  WHERE trn_date<=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate,CAST("'" AS CHAR CHARACTER SET utf8), CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));


--   PREPARE stmt1 FROM @sql_text1;
--   EXECUTE stmt1;
-- DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;


SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;


 INSERT INTO SavingsInterestPaymentDaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO SavingsInterestPaymentMonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;

SET theAccountDate1=@theAccountDate;

SET @ledgerBalance=NULL;
LEAVE Date_loop;

END IF;

-- SET @tableName=CONCAT('bsanca',accountNumber);
-- SELECT @tableName;
CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST("SELECT SavingsRunningBalance INTO @ledgerBalance from  newsavingsmembers" AS CHAR CHARACTER SET utf8), CAST("  WHERE TrnDate<=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate,CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND AccountNumber=" AS CHAR CHARACTER SET utf8), accountNumber,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
--  SELECT @sql_text1;
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

-- SET @sql_text1 = concat(CAST("SELECT ledger_balance INTO @ledgerBalance from  " AS CHAR CHARACTER SET utf8),@tableName,CAST("  WHERE trn_date<= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate,CAST("'" AS CHAR CHARACTER SET utf8), CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

-- SELECT @sql_text1;
--   PREPARE stmt1 FROM @sql_text1;
--   EXECUTE stmt1;
-- DROP PREPARE stmt1;

-- SELECT @ledgerBalance, @theAccountDate,@tableName;
IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;

IF @ledgerBalance>0 THEN 
 INSERT INTO SavingsInterestPaymentDaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 END IF;

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO SavingsInterestPaymentMonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;


SET theAccountDate1=@theAccountDate;

SET ledgerBalance1=@ledgerBalance;

SET @theAccountDate=DATE_ADD(@theAccountDate, INTERVAL 1 DAY);
 

SET @ledgerBalance=NULL;

 END LOOP Date_loop;

-- IF ledgerBalance1 >0 THEN 
INSERT INTO SavingsInterestPaymentAnnually VALUES(null,theAccountDate1,MONTHNAME(theAccountDate1),YEAR(theAccountDate1),@accountName,accountNumber,ledgerBalance1,1,rateUsed,finalTotals,'Not Yet','NA','NA','NA');

-- END IF;
SET @ledgerBalance=NULL;
SET finalTotals=0;

SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingAccountNumbers;

END//

 DELIMITER ;

-- CALL devidendPaymentOnSavings();
 



-- UPDATE savingssharescomputationparameters SET  SavingsStartDate= '2022-02-23',  ShareStartDate= '2022-03-23', SharesRateUsed=4.1,  SavingsRateUsed= 2.5;



DROP PROCEDURE IF EXISTS pmms.devidendPaymentOnShares;
 
 	DELIMITER //


 CREATE PROCEDURE devidendPaymentOnShares() READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30); 

 DECLARE theAccountDate1 DATE;
  DECLARE anyDateInYear DATE;
  DECLARE rateUsed DOUBLE;

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
-- SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST("SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date<= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate, CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND account_number=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),accountNumber,CAST("'" AS CHAR CHARACTER SET utf8),CAST(' ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));

 -- SELECT @sql_text1;

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

-- SELECT @ledgerBalance,@accountName;

IF @ledgerBalance>0 THEN

SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;



 INSERT INTO sharesinterestpaymentdaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO sharesinterestpaymentmonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');


SET monthlyTotals=0;

 END IF;
END IF;

SET theAccountDate1=@theAccountDate;
SET @ledgerBalance=NULL;
LEAVE Date_loop;

END IF;




-- SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);



SET @sql_text1 = concat(CAST("SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date<= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate, CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND account_number=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),accountNumber,CAST("'" AS CHAR CHARACTER SET utf8),CAST(' ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));

-- SELECT @sql_text1;

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

-- SELECT @ledgerBalance,@accountName;

IF @ledgerBalance>0 THEN

SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));


 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;

-- SELECT @theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals;

 INSERT INTO sharesinterestpaymentdaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO sharesinterestpaymentmonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;
END IF;



SET theAccountDate1=@theAccountDate;
SET ledgerBalance1=@ledgerBalance;
SET @theAccountDate=DATE_ADD(@theAccountDate, INTERVAL 1 DAY);

 SET @ledgerBalance=NULL;

 END LOOP Date_loop;

  INSERT INTO sharesinterestpaymentannually VALUES(null,theAccountDate1,MONTHNAME(theAccountDate1),YEAR(theAccountDate1),@accountName,accountNumber,ledgerBalance1,1,rateUsed,finalTotals,'Not Yet','NA','NA','NA');

SET finalTotals=0;

SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingAccountNumbers;



END //

 DELIMITER ;
 

UPDATE savingssharescomputationparameters set  SharesRateUsed=1.13,SavingsRateUsed= .61;
UPDATE savingssharescomputationparameters set  SavingsStartDate= '2022-02-23',ShareStartDate= '2022-03-23';
--  CALL devidendPaymentOnShares();


DROP PROCEDURE IF EXISTS pmms.devidendPaymentOnSharesAccount;
 
 	DELIMITER //


 CREATE PROCEDURE devidendPaymentOnSharesAccount(IN accountNumberX VARCHAR(100)) READS SQL DATA BEGIN

 DECLARE accountNumber VARCHAR(30); 

 DECLARE theAccountDate1 DATE;
  DECLARE anyDateInYear DATE;
  DECLARE rateUsed DOUBLE;

 DECLARE ledgerBalance1 INTEGER;
 DECLARE amountComputed INTEGER;
 DECLARE monthlySummations INTEGER;
 DECLARE lastDate DATE;
 DECLARE TerminatiOnDate DATE;
 
 DECLARE monthlyTotals INTEGER DEFAULT 0;
  DECLARE l_done INTEGER DEFAULT 0;
  DECLARE finalTotals INTEGER DEFAULT 0;

 DECLARE forSelectingAccountNumbers CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number =accountNumberX;
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
-- SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);


SET @sql_text1 = concat(CAST("SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date<= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate, CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND account_number=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),accountNumber,CAST("'" AS CHAR CHARACTER SET utf8),CAST(' ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));

 -- SELECT @sql_text1;

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

-- SELECT @ledgerBalance,@accountName;

IF @ledgerBalance>0 THEN

SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));
 
 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;



 INSERT INTO sharesinterestpaymentdaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO sharesinterestpaymentmonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');


SET monthlyTotals=0;

 END IF;
END IF;

SET theAccountDate1=@theAccountDate;
SET @ledgerBalance=NULL;
LEAVE Date_loop;

END IF;




-- SELECT @theAccountDate,lastDate,accountNumber;
CALL accountNma(accountNumber,@accountName);



SET @sql_text1 = concat(CAST("SELECT running_balance_v_shares INTO @ledgerBalance from  shares_run_bal  WHERE trn_date<= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@theAccountDate, CAST("'" AS CHAR CHARACTER SET utf8), CAST(" AND account_number=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),accountNumber,CAST("'" AS CHAR CHARACTER SET utf8),CAST(' ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));

-- SELECT @sql_text1;

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


IF (@ledgerBalance IS NULL) THEN

SET @ledgerBalance=0;

END IF;

-- SELECT @ledgerBalance,@accountName;

IF @ledgerBalance>0 THEN

SET amountComputed=(@ledgerBalance*(rateUsed/100))/DAY(LAST_DAY(@theAccountDate));


 SET monthlyTotals=monthlyTotals+amountComputed;

 SET finalTotals=finalTotals+amountComputed;

-- SELECT @theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals;

 INSERT INTO sharesinterestpaymentdaily VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,amountComputed,finalTotals,'Not Yet','NA','NA','NA');

 IF @theAccountDate=LAST_DAY(@theAccountDate) THEN
 
 INSERT INTO sharesinterestpaymentmonthly VALUES(null,@theAccountDate,MONTHNAME(@theAccountDate),YEAR(@theAccountDate),@accountName,accountNumber,@ledgerBalance,1,rateUsed,monthlyTotals,finalTotals,'Not Yet','NA','NA','NA');

SET monthlyTotals=0;

 END IF;
END IF;



SET theAccountDate1=@theAccountDate;
SET ledgerBalance1=@ledgerBalance;
SET @theAccountDate=DATE_ADD(@theAccountDate, INTERVAL 1 DAY);

 SET @ledgerBalance=NULL;

 END LOOP Date_loop;

  INSERT INTO sharesinterestpaymentannually VALUES(null,theAccountDate1,MONTHNAME(theAccountDate1),YEAR(theAccountDate1),@accountName,accountNumber,ledgerBalance1,1,rateUsed,finalTotals,'Not Yet','NA','NA','NA');

SET finalTotals=0;

SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingAccountNumbers;



END //

 DELIMITER ;
 
 
--  114	05502047010	Muhereza Peace 0755014478	0	10,000,000	200	10,000,000
-- 115	05502049610	Mugume James 0783671109	0	4,000,000	80	4,000,000
-- 116	05502050110	Nyiringabo Dan 0779201221	0	2,800,000	56	2,800,000
-- 117	05502056710	Ntambara Geoffrey 0787968873	0	500,000	10	500,000
 
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


 END //
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
                    
            END //
			
			DELIMITER ;
			
			
			
DROP PROCEDURE IF EXISTS updateCountStatsCustomers;

	DELIMITER //
	
 CREATE PROCEDURE updateCountStatsCustomers( )
 BEGIN
 
DECLARE existingAccounts INTEGER; DECLARE ItemIdu INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT COUNT(account_number) INTO existingAccounts FROM account_created_store WHERE  account_number like '05502%10';
UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END //
 
 DELIMITER ;	




DROP PROCEDURE IF EXISTS 	countNumberValueOfActiveSavings;	

DELIMITER //

CREATE PROCEDURE countNumberValueOfActiveSavings( )
BEGIN
 
DECLARE ItemIdu INTEGER;DECLARE totalValueSaving INTEGER;DECLARE totalNumberOfSavings INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT SUM(running_balance),COUNT(running_balance) INTO totalValueSaving,totalNumberOfSavings FROM account_created_store WHERE  account_number like '05502%';
UPDATE summurystats SET TotalValueOfSavings=totalValueSaving WHERE ItemId=ItemIdu;

END //

DELIMITER ;



 DROP PROCEDURE IF EXISTS 	countNumberValueOfActiveSavings2;	
 
DELIMITER //

CREATE PROCEDURE countNumberValueOfActiveSavings2( )
BEGIN
 
DECLARE ItemIdu INTEGER;DECLARE totalValueSaving INTEGER;DECLARE totalNumberOfSavings INTEGER;
 
SELECT ItemId INTO ItemIdu FROM summurystats ORDER BY ItemId DESC Limit 1;
SELECT SUM(running_balance),COUNT(running_balance) INTO totalValueSaving,totalNumberOfSavings FROM account_created_store WHERE running_balance>0 AND  account_number like '05502%';
UPDATE summurystats SET TotalNumberOfActiveSavingsCustomers=totalNumberOfSavings WHERE ItemId=ItemIdu;

END //

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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 END //
 
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

 
 END //
 
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

END //

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

 END //
 
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
  
  SELECT SUM(running_balance) INTO totalValueOfCash FROM account_created_store WHERE  account_number like '01123%';
  
  SELECT SUM(running_balance) INTO totalValueOfBankBalance FROM account_created_store WHERE  account_number like '01122%';
   
    SELECT SUM(running_balance) INTO totalValueOfAssets FROM account_created_store WHERE  account_number like '011%';
  
  SELECT SUM(running_balance) INTO totalValueOfReceivables FROM account_created_store WHERE   account_number like '01131%';
   
     SELECT SUM(running_balance) INTO totalValueOfPayables FROM account_created_store WHERE  account_number like '05500%';
   
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

 END //
 
 DELIMITER ;



DROP PROCEDURE IF EXISTS InterestReceivable;
 
 	DELIMITER //

   CREATE PROCEDURE  InterestReceivable (OUT interestReceivable VARCHAR(30)) BEGIN

SELECT SUM(TotalInterestRemaining) INTO interestReceivable FROM pmms_loans.new_loan_appstore1 WHERE  loan_cycle_status='Disbursed';

IF (interestReceivable IS NULL) THEN

SET interestReceivable=0.0;
END IF;

     END //
	 
 DELIMITER ;



 DROP PROCEDURE IF EXISTS currentAssets;
 
 	DELIMITER //

   CREATE PROCEDURE  currentAssets (OUT currentAssets VARCHAR(30)) BEGIN

SELECT SUM(running_balance) INTO currentAssets FROM account_created_store WHERE (account_number like '01113%' OR account_number like  '01114%' OR account_number like   '01115%' OR account_number like   '01116%' OR account_number like   '01117%' OR  account_number like  '01118%' OR account_number like   '01119%' OR  account_number like  '01120%' OR  account_number like  '01121%' OR  account_number like  '01122%' OR  account_number like  '01123%' OR  account_number like  '01124%' OR  account_number like  '01125%' OR  account_number like  '01126%' OR  account_number like  '01127%' OR  account_number like  '01128%' OR  account_number like  '01129%' OR  account_number like  '01130%' OR  account_number like  '01131%' OR  account_number like  '01132%' OR  account_number like  '01133%' OR  account_number like  '01134%' OR  account_number like  '01135%');

IF (currentAssets IS NULL) THEN

SET currentAssets=0.0;
END IF;

     END //
	 
 DELIMITER ;





DROP PROCEDURE IF EXISTS updateCountStatsCustomersReduce;

DELIMITER //

 CREATE PROCEDURE updateCountStatsCustomersReduce() BEGIN

DECLARE existingAccounts INTEGER;
 
 DECLARE ItemIdu INTEGER;

 SELECT ItemId,TotalNumberOfCustomers INTO ItemIdu,existingAccounts FROM summurystats ORDER BY ItemId DESC Limit 1;

SET existingAccounts=existingAccounts-1;

UPDATE summurystats SET TotalNumberOfCustomers=existingAccounts WHERE ItemId=ItemIdu;

 END //

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

END //
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

END //


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

END //
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

END //
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







DROP PROCEDURE IF EXISTS countNumberValueOfCompletedWrittenOffLoans;
 
 	DELIMITER //
	
 CREATE PROCEDURE countNumberValueOfCompletedWrittenOffLoans() BEGIN

 DECLARE totalValueCompletedLoans INTEGER;
 
 DECLARE totalNumberOfCompltedLoans INTEGER;

  DECLARE totalValueWrittenOffLoans INTEGER;
  
  DECLARE totalNumberOfWrittenOffLoans INTEGER;

  SELECT ItemId INTO @ItemIdux FROM summurystats ORDER BY ItemId DESC Limit 1;
  
    SELECT COUNT(princimpal_amount),SUM(princimpal_amount) INTO totalNumberOfCompltedLoans, totalValueCompletedLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='Completed';

   SELECT COUNT(TotalPrincipalRemaining),SUM(TotalPrincipalRemaining) INTO totalNumberOfWrittenOffLoans, totalValueWrittenOffLoans FROM pmms_loans.new_loan_appstore1  WHERE loan_cycle_status='WrittenOff';
   
   IF totalValueCompletedLoans IS NULL THEN
   SET totalValueCompletedLoans=0;
   END IF;
   
     IF totalValueWrittenOffLoans IS NULL THEN
      SET totalValueWrittenOffLoans=0;
   END IF;
   
SELECT totalNumberOfCompltedLoans,totalValueCompletedLoans,totalNumberOfWrittenOffLoans,totalValueWrittenOffLoans;

UPDATE summurystats SET TotalNumberOfLoansCompleted=totalNumberOfCompltedLoans,TotalValueOfLoansCompleted=totalValueCompletedLoans,TotalNumberOfLoansWrittenOff=totalNumberOfWrittenOffLoans,TotalValueOfLoansWrittenOff=totalValueWrittenOffLoans WHERE ItemId=@ItemIdux;


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








/*==============================Posting Enntry Stuff=========================================================================*/



 DROP PROCEDURE IF EXISTS adjustTrnIdS;
 
 	DELIMITER //

   CREATE PROCEDURE  adjustTrnIdS (IN bsancaAccountNumber VARCHAR(30),IN dateInQuestion DATE,OUT trId INTEGER, OUT lastTrnId INTEGER) BEGIN
   
     SET @sql_text9 = concat(CAST(" SELECT  trn_id INTO @trIdV FROM  " AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST("  WHERE trn_date<='"AS CHAR CHARACTER SET utf8),dateInQuestion,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

/* SELECT @sql_text9; */
PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;
   
       SET @sql_text = concat(CAST(' SELECT  trn_id INTO @LasttrId FROM  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;

/* SELECT @trIdV ,@LasttrId ; */

IF @trIdV<>@LasttrId THEN
SET @trIdV=@trIdV+1;
END IF;

IF @trIdV IS NULL THEN 

SET @trIdV=@LasttrId;

END IF;

SET trId=@trIdV;

SET lastTrnId=@LasttrId ;	 
 
	  TxIdsXX:LOOP
	  
	  
IF @trIdV=@LasttrId THEN
		
		SET trId=@trIdV+1;
		
		  LEAVE TxIdsXX;
	  
	  END IF;
	  
	  
	  
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
   



     END //
	 
       DELIMITER ;
	   

--  (1013,'2021-06-21','Provision for bad loans ,\n  Dated  21/06/2021','2021-06-21','3093225.7940000296','-','3.6965227214299995E8','05509000110','Provision for Bad Debts','000zib','BTN8576','System','10000','10:09:29','20','02235000110','02235000010','Dr','Main','NA')

-- CALL postingTxnsX(NULL,'2022-07-21','Loan processing fees from MUSOGA JAMAWA Processed on 21/07/2022\n  Dated 21/07/2022','2022-07-21','-','15000.0','3605800.0','01123000110','MUSOGA JAMAWA','000zib','BTN35716','Gen','10001','11:40:07','45','03315000110','03315000010','Cr','Main','NA');

-- CALL postingTxnsX(NULL,'2022-07-31','reversal nssf 5% Processed on 31/07/2022\n  From Accounts Payable','2022-07-31','-','219615.0','287741.0','05523000210','Accounts Payable','0002','BTN44551','Gen','10001','10:25:35','2','05501000110','05501000010','Cr','Main','NA');

-- select  trn_id,trn_date,debit,credit, ledger_balance from   bsanca01122000110 WHERE trn_date>='2021-06-01' INTO OUTFILE 'aadesk1.sql' FIELDS TERMINATED BY '#' LINES TERMINATED BY '\n';
-- select * from bsanca01122000010 INTO OUTFILE 'AAFIE.sql'  FIELDS TERMINATED BY '#' LINES TERMINATED BY '\n';




-- CALL postingTxnsX(NULL,'2022-08-30','KOMUJUNI MONIC 0706838684s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','83333.0','5804000.0','05502003410','KOMUJUNI MONIC 0706838684','000zib','BTN36890','LoanR','10001','16:34:38','40','01128000110','01128000010','Cr','Main','NA');

-- CALL postingTxnsX(NULL,'2022-08-30','KOMUJUNI MONIC 0706838684s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','30167.0','5804000.0','05502003410','KOMUJUNI MONIC 0706838684','000zib','BTN36890','LoanR','10001','16:34:38','40','03301000110','03301000010','Cr','Main','NA');

-- CALL postingTxnsX(NULL,'2022-08-30','MAUDAH  JACENTAH 0750468649s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','191667','6768700.0','05502020610','MAUDAH  JACENTAH 0750468649','000zib','BTN36897','LoanR','10001','17:55:07','56','01128000110','01128000010','Cr','Main','NA');																											

-- CALL postingTxnsX(NULL,'2022-08-30','MAUDAH  JACENTAH 0750468649s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','69033','6768700.0','05502020610','MAUDAH  JACENTAH 0750468649','000zib','BTN36897','LoanR','10001','17:55:07','56','03301000110','03301000010','Cr','Main','NA');	



-- CALL postingTxnsX(NULL,'2022-08-30','ATUHAIRE OLIVIOUS 0742940856s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','166667','6995400.0','05502020710','ATUHAIRE OLIVIOUS 0742940856','000zib','BTN36898','LoanR','10001','17:56:53','60','01128000110','01128000010','Cr','Main','NA');																											

-- CALL postingTxnsX(NULL,'2022-08-30','ATUHAIRE OLIVIOUS 0742940856s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','60033','6995400.0','05502020710','ATUHAIRE OLIVIOUS 0742940856','000zib','BTN36898','LoanR','10001','17:56:53','60','03301000110','03301000010','Cr','Main','NA');


-- CALL postingTxnsX(NULL,'2022-08-30','NTIZIBWE ALEX 0752458511s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','125000','5594400.0','05502020110','NTIZIBWE ALEX 0752458511','000zib','BTN36911','LoanR','10001','18:29:00','88','01128000110','01128000010','Cr','Main','NA');																										
																											

-- CALL postingTxnsX(NULL,'2022-08-30','NTIZIBWE ALEX 0752458511s Account Deposit for Loan Payment\n  Dated 30/08/2022','2022-08-30','-','45000','5594400.0','05502020110','NTIZIBWE ALEX 0752458511','000zib','BTN36911','LoanR','10001','18:29:00','88','03301000110','03301000010','Cr','Main','NA');

-- CALL postingTxnsX(NULL,'2022-09-02','Loan Insurance fees from AKABAFUNZAKI ANNET NIGHT 0755952577 Processed on 02/09/2022\n  Dated 02/09/2022','2022-09-02','-','36000.0','2803000.0','05502001310','AKABAFUNZAKI ANNET NIGHT 0755952577','000zib','BTN36980','Gen','10001','14:57:19','34','03310000110','03310000010','Cr','Main','NA');


-- CALL postingTxnsX(NULL,'2022-05-17','mortgage fee from muhumuza Processed on 17/05/2022\n  From Accounts Payable','2022-05-17','-','600000.0','3000000.0','05502019610','Accounts Payable','0002','BTN43488','Gen','10001','06:07:21','23','05501000110','05501000010','Cr','Main','NA');





-- 30/08/2022	8	2022	0.0	0.0	0.0	0	260700	191667	69033	0	0	2487800
        DROP PROCEDURE IF EXISTS postingTxnsX;


        DELIMITER //


        CREATE PROCEDURE  postingTxnsX(IN trn_idX INT(11), IN trn_dateX DATE, IN narrationX VARCHAR(200), IN value_dateX  DATE, IN  debitX VARCHAR(50), IN creditX VARCHAR(50) ,IN ledger_balanceX VARCHAR(50),IN credit_account_noX VARCHAR(50),IN credit_account_nameX VARCHAR(200) ,IN tra_ref_numberX VARCHAR(50),IN  chq_numberX VARCHAR(50), IN trn_typeX VARCHAR(50) ,IN staff_idX  VARCHAR(50), IN trn_timeX TIME, IN trn_sq_noX VARCHAR(10), IN account_numberX VARCHAR(100),IN  master_numberX VARCHAR(100),IN other_oneX VARCHAR(100),IN other_twoX VARCHAR(100) ,IN other_threeX VARCHAR(100) )  BEGIN


        IF trn_typeX <>'GenXX' THEN 


        IF(other_oneX LIKE '%Cr%') THEN 

        SET @creditAccount=account_numberX;

        SET @debitAccount=credit_account_noX;

        CALL accountNma(@creditAccount,@accountName);


        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @creditAccount,@debitAccount,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);


        SET @qryA = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@creditAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));
        SELECT @qryA;

        PREPARE stm FROM @qryA;
        EXECUTE stm;
        DROP PREPARE stm;

        END IF;



        IF(other_oneX LIKE '%Dr%') THEN 

        SET @creditAccount=credit_account_noX;

        SET @debitAccount=account_numberX;

        CALL accountNma(@debitAccount,@accountName);

        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @debitAccount ,@creditAccount ,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);    


        SET @qryB = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@debitAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));

        SELECT @qryB;
        PREPARE stm FROM @qryB;
        EXECUTE stm;
        DROP PREPARE stm;

        END IF;



        UPDATE account_created_store SET running_balance=ledger_balanceX,trn_date=trn_dateX  WHERE account_number=account_numberX;

        CALL   updateMasterX(trn_dateX,account_numberX,ledger_balanceX,staff_idX);





        ELSE




        IF(other_oneX LIKE '%Cr%') THEN 
		
SET @trnId=NULL;SET @lastId=NULL;

        SET @creditAccount=account_numberX;

        SET @debitAccount=credit_account_noX;

        CALL accountNma(@creditAccount,@accountName);
/* SELECT @creditAccount,@debitAccount; */
 SET @bsancaAccountCr=CONCAT(CAST("bsanca" AS CHAR CHARACTER SET utf8),CAST("" AS CHAR CHARACTER SET utf8),@creditAccount,CAST("" AS CHAR CHARACTER SET utf8));


    /*       SELECT @bsancaAccountCr,trn_dateX,@trnId,@lastId;
		   */
		  
        CALL  adjustTrnIdS (@bsancaAccountCr,trn_dateX,@trnId,@lastId);

       /*  SELECT @trnId,@lastId; */
		
		SET @qryB = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@creditAccount,CAST(" VALUES(" AS CHAR CHARACTER SET utf8),@trnId,CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));

       /*  SELECT @qryB; */
        PREPARE stm FROM @qryB;
        EXECUTE stm;
        DROP PREPARE stm;

        CALL adjustAccountBalances(@creditAccount,@bsancaAccountCr,creditX,@trnId,@lastId);
		
	
		        /* UPDATE account_created_store SET running_balance=ledger_balanceX,trn_date=trn_dateX  WHERE account_number=account_numberX; */

               CALL updateAccountCreatedStoreBalance(@creditAccount);

      

         SET @trnId=NULL;SET @lastId=NULL;
		 
        END IF;



       IF(other_oneX LIKE '%Dr%') THEN 
		
SET @trnId=NULL;SET @lastId=NULL;

        SET @creditAccount=account_numberX;

        SET @debitAccount=credit_account_noX;

        CALL accountNma(@creditAccount,@accountName);
SELECT @creditAccount,@creditAccount;
 SET @bsancaAccountDr=CONCAT(CAST("bsanca" AS CHAR CHARACTER SET utf8),CAST("" AS CHAR CHARACTER SET utf8),@creditAccount,CAST("" AS CHAR CHARACTER SET utf8));


          SELECT @bsancaAccountDr,trn_dateX,@trnId,@lastId;
		  
		  
        CALL  adjustTrnIdS (@bsancaAccountDr,trn_dateX,@trnId,@lastId);

        SELECT @trnId,@lastId;
		-- @⁨+256 781 331616⁩  Ayaa Patricia is for 22nd ,Ashaba cathy and kafeero Godfrey are both for 17 this September kindly make updates
		SET @qryB = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@creditAccount,CAST(" VALUES(" AS CHAR CHARACTER SET utf8),@trnId,CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));

       /*  SELECT @qryB; */
        PREPARE stm FROM @qryB;
        EXECUTE stm;
        DROP PREPARE stm;

        CALL adjustAccountBalances(@creditAccount,@bsancaAccountDr,creditX,@trnId,@lastId);
		
        
         SET @trnId=NULL;SET @lastId=NULL;
		 
        END IF;



        END IF;





        END //

        DELIMITER ;


DROP PROCEDURE IF EXISTS accountNma;

DELIMITER //

    CREATE PROCEDURE accountNma(IN accountNumber VARCHAR(30),OUT accountName VARCHAR(30))
             BEGIN

            SELECT account_name FROM pmms.account_created_store where account_number=accountNumber INTO accountName;
                    
            END //
			
			DELIMITER ;

        DROP PROCEDURE IF EXISTS accountMasterX;

        DELIMITER //

        CREATE PROCEDURE accountMasterX(IN accountNumber VARCHAR(30),OUT accountMaster VARCHAR(30)) BEGIN

         

        SET accountMaster=CAST(CONCAT(SUBSTR(accountNumber,1,5),'000010') AS CHAR CHARACTER SET utf8);

        END //

        DELIMITER ;



        DROP PROCEDURE IF EXISTS priviousBalanceX;

        DELIMITER //


        CREATE PROCEDURE  priviousBalanceX(IN accountNumberIn VARCHAR(30),OUT priviousBal VARCHAR(30))
        BEGIN

        CALL accountMasterX(accountNumberIn,@accountMaster);
/* SELECT accountNumberIn,@accountMaster; */


        SET @sQr=CONCAT(CAST("SELECT account_balance INTO @priviousBalX FROM BSANCA" AS CHAR CHARACTER SET utf8),@accountMaster,CAST(" WHERE  account_number=" AS CHAR CHARACTER SET utf8),accountNumberIn,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
       /*  SELECT  @sQr; */
        PREPARE stm FROM @sQr;	 EXECUTE  stm;  DROP PREPARE stm;

            


        IF (@priviousBalX IS NULL) THEN

        SET @priviousBalX='0.0';

        END IF;
		
		/* SELECT @priviousBalX; */

SET priviousBal=@priviousBalX;

        END //

        DELIMITER ;
		
		

        DROP PROCEDURE IF EXISTS currentMasterBalanceX;

        DELIMITER //

        CREATE PROCEDURE  currentMasterBalanceX(IN accountNumberIn VARCHAR(30), OUT theBalance VARCHAR(30))
        BEGIN

        CALL accountMasterX(accountNumberIn,@accountMaster);

        SET @qr=CONCAT(CAST("SELECT  master_balance  INTO @theBalanceX  FROM BSANCA" AS CHAR CHARACTER SET utf8),@accountMaster,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
      
	  PREPARE st FROM @qr; EXECUTE st; DROP PREPARE st;

          
		   IF (@theBalanceX IS NULL) THEN

        SET @theBalanceX='0.0';

        END IF;
SET theBalance=@theBalanceX;

        END //

        DELIMITER ;




        DROP PROCEDURE IF EXISTS updateMasterX;

        DELIMITER //
		
        CREATE PROCEDURE updateMasterX(IN TrnDate DATE,IN accountNumber VARCHAR(20),IN NewLedgerBalance VARCHAR(20),IN StaffId VARCHAR(8))
        BEGIN

        CALL priviousBalanceX(accountNumber,@previouslyAdded);
/* SELECT @previouslyAdded; */
        CALL currentMasterBalanceX(accountNumber,@currentlAdded);
/* SELECT @currentlAdded; */
        CALL accountNma(accountNumber,@accountName);
/* SELECT @accountName; */
        SET @newMasterBalance=(@currentlAdded-@previouslyAdded)+NewLedgerBalance;
/* SELECT @newMasterBalance; */
         CALL accountMasterX(accountNumber,@accountMaster);
/*           SELECT @accountMaster; */
		  
		  SET @qry=CONCAT(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@accountMaster,CAST(" VALUES(NULL" AS CHAR CHARACTER SET utf8),CAST(",'" AS CHAR CHARACTER SET utf8),TrnDate,CAST("','" AS CHAR CHARACTER SET utf8),TrnDate,CAST("','" AS CHAR CHARACTER SET utf8),@accountName,CAST("','" AS CHAR CHARACTER SET utf8),accountNumber,CAST("','" AS CHAR CHARACTER SET utf8),NewLedgerBalance,CAST("','" AS CHAR CHARACTER SET utf8),@newMasterBalance,CAST("','" AS CHAR CHARACTER SET utf8),StaffId,CAST("')" AS CHAR CHARACTER SET utf8) );
/*         SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;

        END //

        DELIMITER ;



 DROP PROCEDURE IF EXISTS adjustAccountBalancesDR;

        DELIMITER //
		
        CREATE PROCEDURE adjustAccountBalancesDR(IN accountNumber VARCHAR(40),IN bsancaAccount VARCHAR(40),IN amountIn DOUBLE,IN trnId INT, IN lastId INT)
        BEGIN
          
		 /*  SELECT accountNumber,bsancaAccount,amountIn,trnId,lastId; */
		 
		 
		  SET @accountCat=SUBSTR(accountNumber,1,3);
		
		
		    SET @startId=trnId-1;
			
			
		   SET @qry=CONCAT(CAST("SELECT ledger_balance INTO @runngbal  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
		
		  IF @runngbal IS NULL THEN 
		SET @runngbal=0;
		END IF;
		 

SELECT @accountCat,@runngbal;
		  
		  
		  IF   @accountCat='011' OR  @accountCat='022' THEN
		  
		 
		CreditsRepeats:REPEAT
		
			SELECT "in1";
			
		SET @startId=@startId+1;
		
	   SET @qry=CONCAT(CAST("SELECT debit,credit INTO @runDebit,@runCredit  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
	
	       IF @runCredit='-' THEN
		   
		   SET @runCredit=0;
		   
		   END IF;
		   
		     IF @runDebit='-' THEN
		   
		   SET @runDebit=0;
		   
		   END IF;
	
	
		SET @runngbal=@runngbal-@runCredit+@runDebit;
		
		
		SET @qry=CONCAT(CAST("UPDATE " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" SET  ledger_balance=" AS CHAR CHARACTER SET utf8),@runngbal,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
                SET @runCredit=0;SET @runDebit=0;
		UNTIL @startId>lastId END REPEAT CreditsRepeats;
		
		   CALL   updateMasterBalancesDRasset(trn_dateX,account_numberX,ledger_balanceX,staff_idX);
		
		END IF;
		
		
		
			SELECT "in313";
		
		
		
		  IF  @accountCat='055' OR  @accountCat='033' OR  @accountCat='044' THEN 
		  
		  
		  
		  
		  
		  
		  DebitssRepeats:REPEAT
		  
			SET @startId=@startId+1;
			
		SELECT "in31";
		  SET @qry=CONCAT(CAST("SELECT debit,credit INTO @runDebit1,@runCredit1  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
	
		       IF @runCredit1='-' THEN
		   
		   SET @runCredit1=0;
		   
		   END IF;
		   
		     IF @runDebit='-' THEN
		   
		   SET @runDebit=0;
		   
		   END IF;
		   
		SET @runngbal=(@runngbal-@runDebit1)+@runCredit1;
		
		
		SET @qry=CONCAT(CAST("UPDATE " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" SET  ledger_balance=" AS CHAR CHARACTER SET utf8),@runngbal,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
		
		
		
		  SET @runCredit1=0;SET @runDebit1=0;
		  
		UNTIL @startId>lastId END REPEAT DebitssRepeats;

		    CALL   updateMasterBalancesDRliability(trn_dateX,account_numberX,ledger_balanceX,staff_idX);

		 END IF;
		 
		 
		 
		 

        END //

        DELIMITER ;








 DROP PROCEDURE IF EXISTS adjustAccountBalancesCR;

        DELIMITER //
		
        CREATE PROCEDURE adjustAccountBalancesCR(IN accountNumber VARCHAR(40),IN bsancaAccount VARCHAR(40),IN amountIn DOUBLE,IN trnId INT, IN lastId INT)
        BEGIN
          
		 /*  SELECT accountNumber,bsancaAccount,amountIn,trnId,lastId; */
		 
		  SET @accountCat=SUBSTR(accountNumber,1,3);
		
		
		    SET @startId=trnId-1;
			
			
		   SET @qry=CONCAT(CAST("SELECT ledger_balance INTO @runngbal  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
		
		  IF @runngbal IS NULL THEN 
		SET @runngbal=0;
		END IF;
		 

SELECT @accountCat,@runngbal;
		  
		  
		  IF   @accountCat='011' OR  @accountCat='022' THEN
		  
		 
		CreditsRepeats:REPEAT
		
			SELECT "in1";
			
		SET @startId=@startId+1;
		
	   SET @qry=CONCAT(CAST("SELECT debit,credit INTO @runDebit,@runCredit  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
	
	       IF @runCredit='-' THEN
		   
		   SET @runCredit=0;
		   
		   END IF;
		   
		     IF @runDebit='-' THEN
		   
		   SET @runDebit=0;
		   
		   END IF;
	
	
		SET @runngbal=@runngbal-@runCredit+@runDebit;
		
		
		SET @qry=CONCAT(CAST("UPDATE " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" SET  ledger_balance=" AS CHAR CHARACTER SET utf8),@runngbal,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
                SET @runCredit=0;SET @runDebit=0;
		UNTIL @startId>lastId END REPEAT CreditsRepeats;
		
		
		   CALL   updateMasterBalancesCRasset(trn_dateX,account_numberX,ledger_balanceX,staff_idX);
		   
		
		END IF;
		
		
		
			SELECT "in313";
		
		
		
		  IF  @accountCat='055' OR  @accountCat='033' OR  @accountCat='044' THEN 
		  
		  
		  
		  
		  
		  
		  DebitssRepeats:REPEAT
		  
			SET @startId=@startId+1;
			
		SELECT "in31";
		  SET @qry=CONCAT(CAST("SELECT debit,credit INTO @runDebit1,@runCredit1  FROM " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
	
		       IF @runCredit1='-' THEN
		   
		   SET @runCredit1=0;
		   
		   END IF;
		   
		     IF @runDebit='-' THEN
		   
		   SET @runDebit=0;
		   
		   END IF;
		   
		SET @runngbal=(@runngbal-@runDebit1)+@runCredit1;
		
		
		SET @qry=CONCAT(CAST("UPDATE " AS CHAR CHARACTER SET utf8),bsancaAccount,CAST(" SET  ledger_balance=" AS CHAR CHARACTER SET utf8),@runngbal,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),@startId);
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
		
		
		
		  SET @runCredit1=0;SET @runDebit1=0;
		  
		UNTIL @startId>lastId END REPEAT DebitssRepeats;

		   CALL   updateMasterBalancesCRliability(trn_dateX,account_numberX,ledger_balanceX,staff_idX);

		 END IF;
		 
		 
		 
		 

        END //

        DELIMITER ;






 DROP PROCEDURE IF EXISTS updateAccountCreatedStoreBalance;

        DELIMITER //
		
        CREATE PROCEDURE updateAccountCreatedStoreBalance(IN creditAccountnO VARCHAR(40))
        BEGIN
          
	 SET @bsancaAccount=CONCAT(CAST("bsanca" AS CHAR CHARACTER SET utf8),CAST("" AS CHAR CHARACTER SET utf8),creditAccountnO,CAST("" AS CHAR CHARACTER SET utf8));
	
	SET @qry=CONCAT(CAST("SELECT ledger_balance INTO @balL FROM " AS CHAR CHARACTER SET utf8),@bsancaAccount,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
        /* SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;
		 

      UPDATE account_created_store SET  running_balance=@balL  WHERE account_number=creditAccountnO;

        END //

        DELIMITER ;









--  DROP PROCEDURE IF EXISTS updateMasterBalancesCRliability;

--         DELIMITER //
		
--         CREATE PROCEDURE updateMasterBalancesCRliability(IN creditAccountnO VARCHAR(40)))
--         BEGIN
          
		
		--  READ UNCOMMITTED: The lowest level of isolation, often referred to as dirty read, which allows a transaction to read data that has not been committed, which may improve performance, but dirty read may not be what we want.
-- READ COMMITTED: Only records that have been committed are visible in a transaction. If the select in the session is still in the query and another session inserts a record at this time, the newly added data is not visible.
-- REPEATABLE READ: After a transaction starts, other session modifications to the database are not visible in this transaction until the transaction commits or rolls back. Repeating the result of a select in a transaction is the same, unless the database is updated in this transaction.
-- SERIALIZABLE: The highest level of isolation, allowing only transactions to be executed serially. In order to achieve this, the database will lock the records that have been read in each row. Other sessions cannot modify the data until the previous transaction ends, and the lock is released when the transaction commits or cancels.


--         END //

--         DELIMITER ;

-- 25/07/2022	25/07/2022	Loan processing fees from BANGA CHARLES 0757990172 Processed on 25/07/2022
  -- Dated 25/07/2022	-	50000.0	50000.0	BTN35773
-- START TRANSACTION: Start the transaction, autocommit is set to 0, if a transaction is already running, it will trigger a hidden COMMIT
-- COMMIT: commit the transaction, save the changes, release the lock
-- ROLLBACK: Roll back all changes to the database by this transaction, then end the transaction, release the lock
-- SAVEPOINT savepoint_name: Create a savepoint identifier to ROLLBACK TO SAVEPOINT
-- ROLLBACK TO SAVEPOINT savepoint_name: Roll back to all changes to the database starting from savepoint_name, which allows a part of the transaction to be rolled back, ensuring that a subset of the changes are committed
-- SET TRANSACTION: Allows you to set the isolation level of the transaction
-- LOCK TABLES: Allows explicit locking of one or more tables, implicitly closing the currently open transaction. It is recommended to explicitly commit or rollback before executing the LOCK TABLES statement. We generally do not use LOCK TABLES in transaction code.

-- SELECT Year, Product, SUM(Sale) AS Total_Sales FROM Sales GROUP BY Year ORDER BY Product;  

-- SELECT Year, Product, Sale, SUM(Sale) OVER(PARTITION BY Year) AS Total_Sales FROM Sales;  

-- CALL adjustIds(70,'03325000010');


-- CALL postingTxnsX(NULL,'2022-07-25','Loan processing fees from BANGA CHARLES 0757990172 Processed on 25/07/2022\n  Dated 25/07/2022','2022-07-25','-','50000.0','3695800.0','05502019310','BANGA CHARLES 0757990172','000zib','BTN35773','Gen','10001','11:08:08','20','03315000110','03315000010','Cr','Main','NA');


-- CALL postingTxnsX(NULL,'2022-08-31','paye reversal-wrong posting from lst month Processed on 31/08/2022\n  From Accounts Payable','2022-08-31','-','912269.0','3151509.0','01118000110','Accounts Payable','0002','BTN44977','Gen','10001','15:20:46','239','05501000110','05501000010','Cr','Main','NA');

 DROP PROCEDURE IF EXISTS adjustIds;

        DELIMITER //
		
        CREATE PROCEDURE adjustIds(IN startinId INT,IN theAccountNumber VARCHAR(100))
        BEGIN
        DECLARE numberOfIds,lastId,mostLast,oneX,twoX,counter INT;
          
        SET @qry1=CONCAT(CAST("SELECT COUNT(trn_id) INTO @numberOfIdsX  FROM bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber,CAST(" WHERE trn_id>=" AS CHAR CHARACTER SET utf8),startinId);
        SELECT @qry1;
         PREPARE stmt2 FROM @qry1;
  EXECUTE stmt2;
DROP PREPARE stmt2;   
		SELECT @numberOfIdsX;
		    SET @qry2=CONCAT(CAST("SELECT trn_id INTO @lastIdX  FROM bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber,CAST(" WHERE trn_id>=" AS CHAR CHARACTER SET utf8),startinId,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
 PREPARE stmt2 FROM @qry2;
  EXECUTE stmt2;
DROP PREPARE stmt2;   
        SET lastId=@lastIdX;
        SET counter=lastId;
        SET mostLast=lastId+1;
        SET onex=lastId-1;

        SELECT lastId,counter,mostLast,onex;

     SET @theData = concat(CAST("UPDATE  bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber,CAST(" SET trn_id=" AS CHAR CHARACTER SET utf8),mostLast,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),lastId);
/* select @theData; */
  PREPARE stmt2 FROM @theData;
  EXECUTE stmt2;
DROP PREPARE stmt2;   

       REPEAT 

SET @theData = concat(CAST("UPDATE  bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber,CAST(" SET trn_id=" AS CHAR CHARACTER SET utf8),lastId,CAST(" WHERE  trn_id=" AS CHAR CHARACTER SET utf8),onex);
/* select @theData; */
  PREPARE stmt2 FROM @theData;
  EXECUTE stmt2;
DROP PREPARE stmt2;

SET lastId=lastId-1;
  SET onex=onex-1;                
      
 SET counter=counter-1;       
        --  SELECT counter;
UNTIL counter=(startinId) END REPEAT;

  SET @theData = concat(CAST("UPDATE  bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber,CAST(" SET trn_id=" AS CHAR CHARACTER SET utf8),startinId,CAST(" WHERE trn_id=" AS CHAR CHARACTER SET utf8),mostLast);
/* select @theData; */
  PREPARE stmt2 FROM @theData;
  EXECUTE stmt2;
DROP PREPARE stmt2;   

        END //

        DELIMITER ;

	   

-- UPDATE  bsanca05502049110 SET trn_id=1025 WHERE trn_id= 1024;

-- CALL postingTxnsX(NULL,'2021-08-09','Provision for bad loans ,\n  Dated  09/08/2021','2021-08-09','-','241298.30100005865','4.5649760591100097E8','02235000110','Provision for Bad Debts','000zib','BTN8604','System','10000','09:37:53','111','05509000110','05509000010','Cr','Main','NA');

--  (22433,'2021-04-23','Kabagambe Angellas Savings Processed on 23/04/2021\n  From Kabagambe Angella','2021-04-23','200000.0','-','01122000110','05502043010','Centenary Bank','0002','BTN31944','Save2','10000','09:50:12','2')

-- CALL postingTxnsX(1022,'2021-04-23','Kabagambe Angellas Savings Processed on 23/04/2021\n  From Kabagambe Angella','2021-04-23','-','200000.0','2514500.0','01122000110','Centenary Bank','0002','BTN31944','Save2','10000','09:50:12','2','05502043010','05502000010','Cr','Main','NA');


-- (23599,'2021-05-31','Tusaasirwe Jonards Account Deposit for Loan Payment\n  Dated 31/05/2021','2021-05-31','780000.0','-','01123000110','05502029910','Cash At Hand','000zib','BTN32271','LoanR','10000','12:26:48','4')


-- CALL postingTxnsX(NULL,'2022-04-30','phionah ampulire Processed on 30/04/2022\n  From Accounts Payable','2022-04-30','-','600000.0','24000010','05502032810','Accounts Payable','0002','BTN43269','Gen','10001','10:49:16','160','05501000110','05501000010','Cr','Main','NA');

-- (1383,'2022-06-20','OMODING JOHN 0759385796s Savings for Loan Payment\n  Dated 20/06/2022','2022-06-20','150000.0','-','3.5399155E7','05502003910','OMODING JOHN 0759385796','000zib','BTN29294','LoanR','10001','08:56:30','26','01122000210','01122000010','Dr','Main','NA')



-- CALL postingTxnsX(NULL,'2022-07-31','reversal nssf Processed on 31/07/2022\n  From Accounts Payable','2022-07-31','-','219615.0','1707356','01122000210','Accounts Payable','0002','BTN44553','Gen','10001','10:43:44','6','05501000110','05501000010','Cr','Main','NA');


-- trn_id ,trn_date ,value_date ,debit ,credit ,ledger_balance ,credit_account_no ,credit_account_name ,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no ,account_number ,master_number ,other_one ,other_two ,other_three 

-- CALL adjustIds(1085,'05509000110');
-- 0776717700;0787848687;0755143791;0785044332;0785891477;0781331616
-- Customer waffe omulungi, Tukwebaza olwokubela owensonga gyetuli. Tukwagaliza Christmas Enungi ne famile yo nomwaka omujya ogwobuwanguzi okuva eli ffe aba Microbills
-- CALL adjustIds(86,'05509000010');

-- CALL postingTxnsX(NULL,'2021-10-06','Reversal of Excess Provision for bad loans,\n  Recognized on 06/10/2021','2021-10-06','4209779.509800136','-','4.9306077660685253E8','02235000110','Provision for Bad Debts','000zib','BTN8651','System','10000','13:56:56','74','05509000110','05509000010','Dr','Main','NA')

-- CALL adjustIds(1384,'05501000110');
-- CALL adjustIds(90,'05509000010');

 /*=========================================SEQUENCE NUMBERING SYSTEM======================================================*/


/* DROP TABLE IF EXISTS `sequenceNumbers`; */

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

-- INSERT INTO sequenceNumbers VALUES(null,10000,1,30000,40000,50000,60000,70000,80000);


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
 










/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanStatementDetails;

DELIMITER ##

CREATE PROCEDURE   loanStatementDetails(IN SloanTrnId VARCHAR(45))
BEGIN

-- SELECT SloanTrnId;

-- DROP TABLE IF EXISTS loanStatementtDetailsTable;

CREATE TEMPORARY  TABLE loanStatementtDetailsTable(
`id` INTEGER NOT NULL AUTO_INCREMENT, -- 0
`trn_date` DATE,-- 1
`amount_paid` VARCHAR(60),-- 4
`princimpal_paid` VARCHAR(60),-- 5
`interest_paid` VARCHAR(60),-- 6
`amount_remaining` VARCHAR(60),-- 9
`princimpal_remaining`  VARCHAR(60),-- 10
`interest_remaining`  VARCHAR(60),-- 11
 PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

   SELECT * FROM loanStatementtDetails;
SELECT SloanTrnId;
INSERT INTO  loanStatementtDetailsTable( 
  `id` ,
  `trn_date` ,
      `amount_paid`,
     `princimpal_paid`,
  `interest_paid`,
        `amount_remaining`,
          `princimpal_remaining`,
          `interest_remaining`
  ) SELECT  null,`TrnDate` ,FORMAT(`AmountPaid`,0) ,  FORMAT(`PrincipalPaid`,0) ,  FORMAT(`InterestPaid`,0) ,  FORMAT(`LoanBalance`,0) ,  FORMAT(`PrincipalBalance`,0) ,  FORMAT(`InterestBalance`,0)  FROM loandisburserepaystatement WHERE loanTrnId=SloanTrnId LIMIT 1,20000;


   SELECT * FROM loanStatementtDetailsTable;

END ##
DELIMITER ;

-- CALL loanStatementDetails('NL0378');







/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS customerNameL;
DELIMITER ##
CREATE FUNCTION customerNameL(accounNumber VARCHAR(30)) 
RETURNS VARCHAR(60)
DETERMINISTIC
BEGIN
DECLARE customerNameNow VARCHAR(200);

SELECT account_name INTO customerNameNow FROM account_created_store WHERE  account_number=accounNumber;
IF ISNULL(customerNameNow) THEN
 SET customerNameNow='MISSING';
 END IF;
RETURN customerNameNow;
END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS staffName;
DELIMITER ##
CREATE FUNCTION staffName(staffId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE staffNameNow VARCHAR(40);

SELECT account_name INTO staffNameNow FROM log_in WHERE  user_id=staffId;
 IF ISNULL(staffNameNow) THEN
 SET staffNameNow='MISSING';
 END IF;
RETURN staffNameNow;
END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS staffName1;
DELIMITER ##
CREATE FUNCTION staffName1(staffId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE staffNameNow VARCHAR(40);

SELECT account_name INTO staffNameNow FROM pmms.log_in WHERE  user_id=staffId;
 IF ISNULL(staffNameNow) THEN
 SET staffNameNow='MISSING';
 END IF;
RETURN staffNameNow;
END ##
DELIMITER ;
--  INSERT INTO the_company_datails VALUES(NULL,'UBOS SACCO LTD','STATISTICS HOUSE','P.O BOX 28886 KAMPALA UGANDA','0782602544',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


CREATE TABLE `the_company_datails` (
  `the_company_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `the_company_name` varchar(100) DEFAULT 'Edad Coin SMS-Ltd',
  `the_company_branch` varchar(100) DEFAULT 'Edad Coin SMS-Ltd',
  `the_company_box_number` varchar(100) DEFAULT 'Edad Coin SMS-Ltd',
  `office_number` varchar(100) DEFAULT '0782231039',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`the_company_details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16001 DEFAULT CHARSET=utf8;


/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS customerNameL;
DELIMITER ##
CREATE FUNCTION customerNameL(accounNumber VARCHAR(30)) 
RETURNS VARCHAR(60)
DETERMINISTIC
BEGIN
DECLARE customerNameNow VARCHAR(40);

SELECT account_name INTO customerNameNow FROM account_created_store WHERE  account_number=accounNumber;
IF ISNULL(customerNameNow) THEN
 SET customerNameNow='MISSING';
 END IF;
RETURN customerNameNow;
END ##
DELIMITER ;


DROP PROCEDURE IF EXISTS runningLoansDetails;
DELIMITER ##
CREATE PROCEDURE runningLoansDetails() BEGIN

DROP TABLE IF EXISTS runningLoanAnalysis;

CREATE TEMPORARY TABLE  runningLoanAnalysis(id INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_account VARCHAR(60), PRIMARY KEY (`id`))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

  --  INSERT INTO  runningLoanAnalysis( 
  -- `id` ,
  -- `customer_name` ,
  -- `customer_account`  
  -- )
   SELECT 
    trnId AS id,
  AccountNumber AS customer_account
       FROM loandisburserepaystatement GROUP BY AccountNumber;
       
      --  SELECT * FROM runningLoanAnalysis;
END ##
DELIMITER ;
CALL runningLoansDetails();






DROP PROCEDURE `savingsPrintingDetails`;

DELIMITER ##

CREATE  PROCEDURE `savingsPrintingDetails`(IN accountNumberlx VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 DECLARE l_done INT;
 DECLARE savingsMade,savingsWithdrawn,savingsRemaing DOUBLE;
  DECLARE loanTrnIdL,companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber  VARCHAR(60);
DECLARE date_takenL DATE;

DROP TABLE IF EXISTS savePrintDetails;

CREATE TEMPORARY  TABLE savePrintDetails(


id_1 INTEGER,
company_name VARCHAR(60),
company_branch VARCHAR(60),
company_box_number VARCHAR(60),
customer_name VARCHAR(60),
staff_name VARCHAR(60),
savings_made VARCHAR(60),
savings_withdrawn VARCHAR(60),
savings_remaining VARCHAR(60),
accountNumber1  VARCHAR(60),
trn_date VARCHAR(60),
trn_time TIME,
office_number VARCHAR(60));


SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

 SELECT SavingsAdded , SavingsRemoved , SavingsRunningBalance INTO savingsMade,savingsWithdrawn,savingsRemaing  FROM newsavingsmembers WHERE AccountNumber=accountNumberlx ORDER BY TrnId DESC LIMIT 1;

select trn_id into l_done from general_ledger  ORDER BY trn_id ASC LIMIT 1;




INSERT INTO savePrintDetails VALUES (l_done,companyName,companyBranch,companyBoxNumber,customerNameL(accountNumberlx),staffName(staffId),FORMAT(savingsMade,0),FORMAT(savingsWithdrawn,0),FORMAT(savingsRemaing,0),accountNumberlx,DATE_FORMAT(DATE(NOW()),'%d/%m/%Y'),TIME(NOW()),officeNumber);

   SELECT * FROM savePrintDetails;

END  ##
DELIMITER ;



use pmms

/* ALTER TABLE the_company_datails ADD COLUMN  `office_number`  varchar(100) DEFAULT '0782231039'; */
/* 
update the_company_datails SET  office_number ='0706221396/0787511344(Company Mobile)'; */





--  INSERT INTO loanArrearsSettings VALUES (NULL,0,4,1,1,10.0,2,1,1);
-- DROP TABLE IF EXISTS loanArrearsIndividualSettings;
/* DROP TABLE IF EXISTS backUpDetails; */
CREATE TABLE backUpDetails (
   id INT(11) NOT NULL AUTO_INCREMENT,
  backUpAccount VARCHAR(50) NOT NULL, 
  tTime  VARCHAR(50) NOT NULL, 
  tDate  VARCHAR(50) NOT NULL, 
  backUpMessage VARCHAR(500) NULL, 
  userId INT NOT NULL,
  backupTime timestamp,
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET=utf8;

/* DROP TABLE IF EXISTS backUpContact; */

CREATE TABLE backUpContact(
   id INT(11) NOT NULL AUTO_INCREMENT, 
   enabled INT NOT NULL,
   contactName1 VARCHAR(50)  NULL, 
  backUpContactNotification1 VARCHAR(50) NULL,
    PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=1 DEFAULT CHARACTER SET=utf8;


 /* INSERT INTO backUpContact VALUES(NULL,1, 'GODFREY','0778066146'); 1=false,2=true  */
 

 
 
 
DROP PROCEDURE IF EXISTS isItBackeup;
DELIMITER ##
CREATE PROCEDURE isItBackeup(IN account VARCHAR(50)) BEGIN

SELECT COUNT(id) AS backed FROM backUpDetails WHERE DATE(backUpTime)=DATE(NOW()) AND backUpAccount=account;

END ##
DELIMITER ;
/* CALL isItBackeup('pmms'); */




DROP PROCEDURE IF EXISTS disableSms;
DELIMITER ##
CREATE PROCEDURE disableSms(IN contact VARCHAR(50)) BEGIN

UPDATE backupcontact SET enabled=1 WHERE backUpContactNotification1=contact;

END ##
DELIMITER ;
/* CALL disableSms('0781331616'); */



DROP PROCEDURE IF EXISTS enableSms;
DELIMITER ##
CREATE PROCEDURE enableSms(IN contact VARCHAR(50)) BEGIN

UPDATE backupcontact SET enabled=2 WHERE backUpContactNotification1=contact;

END ##
DELIMITER ;
/* CALL enableSms('0773227895'); */






DROP PROCEDURE IF EXISTS getAllSendingDetails;
DELIMITER ##
CREATE PROCEDURE getAllSendingDetails() BEGIN

SELECT contactName1 AS name,backUpContactNotification1 AS contact,TIME(CURRENT_TIMESTAMP) AS theTime,DATE_FORMAT(DATE(CURRENT_TIMESTAMP),"%d/%m/%Y") AS theDate FROM  backupcontact WHERE enabled=2;

END ##
DELIMITER ;
CALL getAllSendingDetails();





DROP PROCEDURE IF EXISTS createBackUpRecord;
DELIMITER ##
CREATE PROCEDURE createBackUpRecord(IN backUpAccountIN VARCHAR(50),IN TIMEIN VARCHAR(50),IN DATEIN VARCHAR(50), IN THEMESSAGE VARCHAR(500),IN userId INT) BEGIN

INSERT INTO backUpDetails VALUES(NULL,backUpAccountIN,TIMEIN,DATEIN,THEMESSAGE,userId,CURRENT_TIMESTAMP);
END ##
DELIMITER ;




/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanStatementDetails;

DELIMITER ##

CREATE PROCEDURE   loanStatementDetails(IN SloanTrnId VARCHAR(45))
BEGIN

-- SELECT SloanTrnId;

DROP TABLE IF EXISTS loanStatementtDetailsTable;

CREATE TEMPORARY  TABLE loanStatementtDetailsTable(
`id` INTEGER NOT NULL AUTO_INCREMENT, -- 0
`trn_date` DATE,-- 1
`amount_paid` VARCHAR(60),-- 4
`princimpal_paid` VARCHAR(60),-- 5
`interest_paid` VARCHAR(60),-- 6
`amount_remaining` VARCHAR(60),-- 9
`princimpal_remaining`  VARCHAR(60),-- 10
`interest_remaining`  VARCHAR(60),-- 11
 PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

  --  SELECT * FROM loanStatementtDetails;
-- SELECT SloanTrnId;
INSERT INTO  loanStatementtDetailsTable( 
  `id` ,
  `trn_date` ,
      `amount_paid`,
     `princimpal_paid`,
  `interest_paid`,
        `amount_remaining`,
          `princimpal_remaining`,
          `interest_remaining`
  ) SELECT  null,`TrnDate` ,FORMAT(`AmountPaid`,0) ,  FORMAT(`PrincipalPaid`,0) ,  FORMAT(`InterestPaid`,0) ,  FORMAT(`LoanBalance`,0) ,  FORMAT(`PrincipalBalance`,0) ,  FORMAT(`InterestBalance`,0)  FROM loandisburserepaystatement WHERE loanTrnId=SloanTrnId LIMIT 1,20000;


   SELECT * FROM loanStatementtDetailsTable;

END ##
DELIMITER ;

/* CALL loanStatementDetails('NL0378'); */



/* ALL CONTINENTAL REGIONS */
DROP PROCEDURE IF EXISTS testingLoanPay;

DELIMITER ##

CREATE PROCEDURE   testingLoanPay()
BEGIN
   
 DECLARE l_done INT;
  DECLARE  staffIds  VARCHAR(50);
 
DECLARE forSelectingStaffIds CURSOR FOR SELECT DISTINCT(trn_id)   FROM pmms_loans.new_loan_appstore where loan_cycle_status='Disbursed';
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
 OPEN forSelectingStaffIds;

accounts_loop: LOOP 
 FETCH forSelectingStaffIds into staffIds;
 SELECT staffIds;
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
CALL loanStatementDetails(staffIds);

    SET l_done=0;
 END LOOP accounts_loop;
 CLOSE forSelectingStaffIds;


END

##
DELIMITER ;


-- DROP TABLE IF EXISTS accountNameController;

CREATE   TABLE accountNameController(
`id` INTEGER NOT NULL AUTO_INCREMENT, -- 0
`includePhone` INT,-- 1
 PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT =1
DEFAULT CHARACTER SET = utf8;



/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS statusExistsControl;
DELIMITER ##
CREATE FUNCTION statusExistsControl() 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE status INT;
SELECT COUNT(id) INTO status FROM accountNameController;
RETURN status;
END ##
DELIMITER ;



DROP PROCEDURE IF EXISTS controlNameStatus;
DELIMITER ##
CREATE PROCEDURE controlNameStatus(IN theStatus INT) BEGIN

IF statusExistsControl()>0 THEN
UPDATE accountNameController SET includePhone=theStatus;
END IF;

IF statusExistsControl()<=0 THEN
INSERT INTO accountNameController VALUES(NULL,theStatus);
END IF;

END ##
DELIMITER ;



DROP PROCEDURE IF EXISTS getTheNameStatus;
DELIMITER ##
CREATE PROCEDURE getTheNameStatus() BEGIN
DECLARE theStat INT;
SELECT includePhone INTO theStat FROM accountNameController;

IF ISNULL(theStat) THEN
SET theStat=0;
END IF;
SELECT theStat;
END ##
DELIMITER ;



DROP PROCEDURE IF EXISTS updateAccountName;
DELIMITER ##

CREATE PROCEDURE updateAccountName(IN theAccountNumber VARCHAR(100),In theAccountName VARCHAR(100)) BEGIN
DECLARE loanId VARCHAR(100);

SET loanId=concat(CAST("newloan" AS CHAR CHARACTER SET utf8),theAccountNumber);

UPDATE newsavingsmembers SET AccountName=theAccountName WHERE AccountNumber=theAccountNumber;

UPDATE shares_run_bal SET account_name=theAccountName WHERE account_number=theAccountNumber;

UPDATE sharesreturnoninvestment SET account_name=theAccountName WHERE account_number=theAccountNumber;

 UPDATE sharesinterestpaymentmonthly SET AccountNmae=theAccountName WHERE AccountNumber=theAccountNumber;
 
  UPDATE sharesinterestpaymentdaily SET AccountNmae=theAccountName WHERE AccountNumber=theAccountNumber;
 

  UPDATE savingsandsharesinterestpaymentsummury SET AccountNmae=theAccountName WHERE AccountNumber=theAccountNumber;

 UPDATE loan_savings_shares SET account_name=theAccountName WHERE account_number=theAccountNumber;
 
 
 
 
  UPDATE borrow_guarantee SET account_name=theAccountName WHERE  account_number=theAccountNumber;
  
  
  UPDATE account_created_store SET account_name=theAccountName WHERE account_number=theAccountNumber;
  
  
    UPDATE log_in SET account_name=theAccountName WHERE account_number=theAccountNumber;
    
    
UPDATE master SET account_name=theAccountName WHERE account_number=theAccountNumber;
    
 /* UPDATE pmms_loans.dailycollection SET accountName=theAccountName WHERE loanID=loanId; */
 
  UPDATE pmms_loans.loan_arrears_tracker SET account_name=theAccountName WHERE account_number=theAccountNumber;
   
UPDATE pmms_loans.loan_due_writeoff SET account_name=theAccountName WHERE account_number=theAccountNumber;
     
 UPDATE pmms_loans.loan_portfolio SET account_name=theAccountName WHERE account_number=theAccountNumber;
         
   UPDATE pmms_loans.loan_portfolio_atrisk SET account_name=theAccountName WHERE account_number=theAccountNumber;
   
      UPDATE pmms_loans.loanerrorqueue SET BorrowerName=theAccountName WHERE BorrowerAccount=theAccountNumber;
      
         UPDATE pmms_loans.loanprocessingstore SET account_name=theAccountName WHERE account_number=theAccountNumber;
         
              UPDATE pmms_loans.new_loan_appstore SET applicant_account_name=theAccountName WHERE applicant_account_number=theAccountNumber;
              
                UPDATE pmms_loans.new_loan_appstore1 SET applicant_account_name=theAccountName WHERE applicant_account_number=theAccountNumber;
                
                  UPDATE pmms_loans.new_loan_appstore2 SET applicant_account_name=theAccountName WHERE applicant_account_number=theAccountNumber;
                  
                   UPDATE pmms_loans.performing_loan_portfolio SET account_name=theAccountName WHERE account_number=theAccountNumber;

SELECT loanId;
END ##
DELIMITER ;

/* CALL updateAccountName('05502006110','GOOGO BAZI'); */




DROP PROCEDURE `savingsHistoricalPrintingDetails`;

DELIMITER ##

CREATE  PROCEDURE `savingsHistoricalPrintingDetails`(IN batchNumber VARCHAR(60),IN theAccountNumber VARCHAR(60),IN staffId VARCHAR(45))
BEGIN



 DECLARE l_done INT;
 DECLARE savingsMade,savingsWithdrawn,savingsRemaing DOUBLE;
  DECLARE loanTrnIdL,companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,theAccount  VARCHAR(60);
DECLARE date_takenL DATE;



SET theAccount=concat(CAST("bsanca" AS CHAR CHARACTER SET utf8),theAccountNumber);

DROP TABLE IF EXISTS savePrintDetails;

CREATE TEMPORARY  TABLE savePrintDetails(


id_1 INT,
company_name VARCHAR(60),
company_branch VARCHAR(60),
company_box_number VARCHAR(60),
customer_name VARCHAR(60),
staff_name VARCHAR(60),
savings_made VARCHAR(60),
savings_withdrawn VARCHAR(60),
savings_remaining VARCHAR(60),
accountNumber1  VARCHAR(60),
trn_date VARCHAR(60),
trn_time TIME,
office_number VARCHAR(60));


SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

 /* SELECT credit , debit , ledger_balance INTO savingsMade,savingsWithdrawn,savingsRemaing  FROM theAccount WHERE chq_number=batchNumber; */
 
   SET @sql_text = concat(CAST("SELECT  credit, debit,ledger_balance,trn_date,trn_time INTO @savingsMade,@savingsWithdrawn,@savingsRemaing,@trnDate,@trnTime FROM  " AS CHAR CHARACTER SET utf8),theAccount,CAST(" WHERE chq_number=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),batchNumber,CAST("'" AS CHAR CHARACTER SET utf8));
   /* SELECT @sql_text; */
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;

select trn_id into l_done from general_ledger  ORDER BY trn_id ASC LIMIT 1;

SET savingsMade=@savingsMade;
SET savingsWithdrawn=@savingsWithdrawn;
SET savingsRemaing=@savingsRemaing;

/* SELECT l_done; */

INSERT INTO savePrintDetails VALUES (l_done,companyName,companyBranch,companyBoxNumber,customerNameL(theAccountNumber),staffName(staffId),FORMAT(savingsMade,0),FORMAT(savingsWithdrawn,0),FORMAT(savingsRemaing,0),theAccountNumber,DATE_FORMAT(@trnDate,'%d/%m/%Y'),@trnTime,officeNumber);

   SELECT * FROM savePrintDetails;

END  ##
DELIMITER ;





/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanPrintingDetails;

DELIMITER ##

CREATE PROCEDURE   loanPrintingDetails(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 DECLARE l_done INT;
 DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL DOUBLE;
  DECLARE loanTrnIdL,companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber VARCHAR(60);
DECLARE date_takenL,theTrn_date DATE;

-- DROP TABLE IF EXISTS loanPrintDetails;

CREATE TEMPORARY  TABLE loanPrintDetails(
id_1 INTEGER, -- 0
company_name VARCHAR(60),-- 1
company_branch VARCHAR(60),-- 2
company_box_number VARCHAR(60),-- 3
customer_name VARCHAR(60),-- 4
staff_name VARCHAR(60),-- 5
loan_taken VARCHAR(60),-- 6
date_taken DATE,-- 7
loans_paid VARCHAR(60),-- 8
loan_remaining VARCHAR(60),-- 9
batchNumber  VARCHAR(60),-- 10
loanID  VARCHAR(60),-- 11
trn_date VARCHAR(60),-- 12
trn_time TIME,-- 13
LoanStatus VARCHAR(60),-- 14
princimpal_amount VARCHAR(60),-- 15
interest_amount VARCHAR(60),-- 16
office_number VARCHAR(60));-- 17


SELECT loanTrnId,AmountPaid,LoanBalance,AccountNumber,LoanStatusReport,TrnDate  INTO loanTrnIdL,AmountPaidL,AmountRemainL,accountNumberL ,LoanStatus ,theTrn_date FROM   loandisburserepaystatement WHERE BatchCode=batchNumber;

SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

SELECT ExpectedTotalAmount,TrnDate,AmountDisbursed,ExpectedInterest INTO loan_takenL,date_takenL,princimpalL,interestL  FROM loandisburserepaystatement WHERE loanTrnId=loanTrnIdL ORDER BY TrnId ASC LIMIT 1;

select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;
-- select AmountRemainL;
INSERT INTO loanPrintDetails VALUES (l_done,companyName,companyBranch,companyBoxNumber,customerNameL(accountNumberL),staffName(staffId),FORMAT(loan_takenL,0),date_takenL,FORMAT(AmountPaidL,0),FORMAT(AmountRemainL,0),batchNumber,loanTrnIdL,DATE_FORMAT(theTrn_date,'%d/%m/%Y'),TIME(NOW()),LoanStatus,FORMAT(princimpalL,0),FORMAT(interestL,0),officeNumber);


   SELECT * FROM loanPrintDetails;

END

##
DELIMITER ;

/* CALL loanPrintingDetails('BTN17003',10000)\G */

DROP PROCEDURE `phoneNumberExists`;
DELIMITER $$
CREATE  PROCEDURE `phoneNumberExists`(IN account VARCHAR(50))
BEGIN

SELECT COUNT(TrnId) AS phoneExists FROM master WHERE mobile1=account;

END $$
DELIMITER ;



DROP PROCEDURE `updatePhoneNumber`;
DELIMITER $$
CREATE  PROCEDURE `updatePhoneNumber`(IN account VARCHAR(50),IN phoneNumber VARCHAR(50))
BEGIN

UPDATE master SET mobile1=phoneNumber WHERE account_number=account;

END $$
DELIMITER ;





-- SELECT SloanTrnId;

-- DROP TABLE IF EXISTS printerDrivers;

CREATE  TABLE printerDrivers(
`id` INTEGER NOT NULL AUTO_INCREMENT, -- 0
`theDrivesers` VARCHAR(500),-- 1
`theDriverStatus` INT,-- 4
`stampStatus` INT,-- 4
 PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;



DROP PROCEDURE `getThePrintDrivers`;
DELIMITER $$
CREATE  PROCEDURE `getThePrintDrivers`()
BEGIN

SELECT theDrivesers FROM printerDrivers WHERE theDriverStatus=2;

END $$
DELIMITER ;



DROP PROCEDURE `getTheStampStatus`;
DELIMITER $$
CREATE  PROCEDURE `getTheStampStatus`()
BEGIN

SELECT stampStatus FROM printerDrivers WHERE theDriverStatus=2;

END $$
DELIMITER ;


/* 
INSERT INTO printerDrivers VALUES(NULL,'E-PoS printer driver',1,1),(NULL,'EPSON TM-U220 Receipt',1,1),(NULL,'EPSON TM-T20 ReceiptE4 (1)',1,1),(NULL,'XP-80C',2,2); */



DROP PROCEDURE IF EXISTS normaliseBalance;
DELIMITER //
CREATE PROCEDURE normaliseBalance() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId VARCHAR(20);
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT DISTINCT(loanTrnId) from loandisburserepaystatement WHERE  LoanStatusReport='Disbursed' AND NOT loanTrnId='0';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
/* SELECT theLoanTxnId; */

/* SET c=c+1; */
/* SELECT c; */

INNER_BLOCK: BEGIN

DECLARE theBatchNoS VARCHAR(60);
DECLARE theOpeningBal,theBal,thePaid,InterestPaid,principalPaid,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal DOUBLE; 
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT BatchCode FROM loandisburserepaystatement WHERE loanTrnId=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 

SELECT COUNT(ExpectedTotalAmount) INTO @No FROM loandisburserepaystatement WHERE ExpectedTotalAmount>0 AND loanTrnId=theLoanTxnId ;

IF @No=1 THEN

SELECT ExpectedTotalAmount INTO theOpeningBal FROM loandisburserepaystatement WHERE ExpectedTotalAmount>0 AND loanTrnId=theLoanTxnId ;

 
TXNIDS_LOOP:LOOP

FETCH forBatchNos INTO theBatchNoS;
SET thePaid=NULL,InterestPaid=NULL,principalPaid=NULL;


 IF innerNotFound=1 THEN
LEAVE TXNIDS_LOOP;
 END IF;


SELECT  debit INTO thePaid FROM bsanca01123000110  WHERE chq_number=theBatchNoS;

SELECT  credit INTO InterestPaid FROM bsanca03301000110 WHERE chq_number=theBatchNoS;

SELECT  credit INTO principalPaid FROM bsanca01128000110 WHERE chq_number=theBatchNoS;

SELECT theBatchNoS, theOpeningBal,thePaid,InterestPaid,principalPaid;

IF thePaid='-' Then
SET thePaid=0.0;
END IF;


IF InterestPaid='-' Then
SET InterestPaid=0.0;
END IF;

IF principalPaid='-' Then
SET principalPaid=0.0;
END IF;

IF ISNULL(thePaid) Then
SET thePaid=0.0;
END IF;

IF ISNULL(InterestPaid) Then
SET InterestPaid=0.0;
END IF;

IF ISNULL(principalPaid) Then
SET principalPaid=0.0;
END IF;
SELECT theBatchNoS, theOpeningBal,thePaid,InterestPaid,principalPaid;
UPDATE loandisburserepaystatement SET AmountPaid=thePaid, PrincipalPaid=principalPaid, InterestPaid=InterestPaid, LoanBalance=theOpeningBal-thePaid WHERE BatchCode=theBatchNoS;

SET theOpeningBal=theOpeningBal-thePaid;

SET innerNotFound=0;
END LOOP TXNIDS_LOOP; 
END IF;
CLOSE forBatchNos; 
END INNER_BLOCK;


SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;





DROP PROCEDURE IF EXISTS regenrateBalances;
DELIMITER //
CREATE PROCEDURE regenrateBalances() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theId INTEGER;
DECLARE theLoanTxnId VARCHAR(20);
DECLARE outerNotFound, c INTEGER DEFAULT 0;
DECLARE totalPenaltyX,totalAccumlatedInterestX,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal DOUBLE;
DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from new_loan_appstore WHERE loan_cycle_status='Disbursed';

DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

DROP TABLE IF EXISTS loandisburserepaystatement1;
CREATE TEMPORARY  TABLE loandisburserepaystatement1(
  id_7 INTEGER,
  TrnDate DATE,
  MonthPaid VARCHAR(100),
  YearPaid VARCHAR(100),
  loanTrnId VARCHAR(100),
  LoanId VARCHAR(100),
  AccountNumber  VARCHAR(100),
  BatchCode VARCHAR(100),
  AmountDisbursed  VARCHAR(100),
  ExpectedInterest VARCHAR(100),
  ExpectedTotalAmount VARCHAR(100),
 InterestRate  VARCHAR(100),              
 AmountPaid   VARCHAR(100),               
 PrincipalPaid  VARCHAR(100),             
 InterestPaid    VARCHAR(100),            
 AccumulatedInterestPaid   VARCHAR(100),  
 LoanPenaltyPaid   VARCHAR(100),          
 PrincipalBalance    VARCHAR(100),        
 InterestBalance   VARCHAR(100),          
 AccumulatedInterestBalance  VARCHAR(100),
 LoanPenaltyBalance  VARCHAR(100),        
 LoanBalance VARCHAR(100),                
 LoanStatusReport  VARCHAR(100),          
 OtherOne  VARCHAR(100),                  
 OtherTwo   VARCHAR(100),                 
 OtherThree   VARCHAR(100),               
 OtherFour VARCHAR(100)

  )ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;
SELECT outerNotFound,theLoanTxnId;
 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
SELECT bsanca01123000110.trn_id INTO theId FROM pmms.bsanca01123000110 INNER JOIN new_loan_appstore ON bsanca01123000110.credit_account_no=new_loan_appstore.applicant_account_number    WHERE new_loan_appstore.trn_id=theLoanTxnId AND bsanca01123000110.credit>0.0 ORDER BY bsanca01123000110.trn_id DESC LIMIT 1;
SELECT theId;
INSERT INTO loandisburserepaystatement1 SELECT TrnId,TrnDate,
  MonthPaid,
  YearPaid ,
  loanTrnId ,
  LoanId ,
  AccountNumber ,
  BatchCode ,
  AmountDisbursed  ,
  ExpectedInterest ,
  ExpectedTotalAmount,
 InterestRate  ,              
 AmountPaid  ,               
 PrincipalPaid ,             
 InterestPaid  ,            
 AccumulatedInterestPaid ,  
 LoanPenaltyPaid ,          
 PrincipalBalance  ,        
 InterestBalance  ,          
 AccumulatedInterestBalance ,
 LoanPenaltyBalance ,        
 LoanBalance ,                
 LoanStatusReport,          
 OtherOne ,                  
 OtherTwo ,                 
 OtherThree ,               
 OtherFour  FROM pmms.loandisburserepaystatement INNER JOIN new_loan_appstore ON loandisburserepaystatement. loanTrnId=new_loan_appstore.trn_id WHERE loandisburserepaystatement.TrnDate=new_loan_appstore.trn_date AND loandisburserepaystatement.ExpectedTotalAmount>0.0 AND new_loan_appstore.trn_id=theLoanTxnId;

DELETE FROM pmms.loandisburserepaystatement WHERE loandisburserepaystatement. loanTrnId=theLoanTxnId;

SELECT SUM(new_loan_appstoreamort.LoanPenalty),SUM(new_loan_appstoreamort.AccumulatedInterest) INTO totalPenaltyX,totalAccumlatedInterestX FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;

UPDATE new_loan_appstore INNER JOIN new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id SET 
new_loan_appstore.balance_due=new_loan_appstore.total_loanAmount,new_loan_appstore.instalments_paid=0.0,
new_loan_appstore.TotalInterestPaid=0.0,
new_loan_appstore.TotalInterestRemaining=new_loan_appstore.total_interest,new_loan_appstore.TotalPrincipalPaid=0.0,
new_loan_appstore.TotalPrincipalRemaining=new_loan_appstore.princimpal_amount,new_loan_appstore.TotalAccumulatedInterestPaid=0.0,
new_loan_appstore.TotalAccumulatedInterestRemaining=totalAccumlatedInterestX,
new_loan_appstore.TotalLoanPenaltyPaid=0.0,
new_loan_appstore.TotalLoanPenaltyRemaining=totalPenaltyX,
new_loan_appstoreamort.instalment_paid=0.0,
new_loan_appstoreamort.InstalmentRemaining=new_loan_appstoreamort.instalment_amount,
new_loan_appstoreamort.PrincipalPaid =0.0,             
 new_loan_appstoreamort.PrincipalRemaining=new_loan_appstoreamort.princimpal_amount,           
 new_loan_appstoreamort.InterestPaid =0.0,             
 new_loan_appstoreamort.InterestRemaing =new_loan_appstoreamort.interest_amount,             
 new_loan_appstoreamort.LoanPenaltyPaid =0.0,          
 new_loan_appstoreamort.LoanPenaltyRemaining =new_loan_appstoreamort.LoanPenalty,       
 new_loan_appstoreamort.AccumulatedInterestPaid =0.0,  
 new_loan_appstoreamort.AccumulatedInterestRemaining=new_loan_appstoreamort.AccumulatedInterest,
 new_loan_appstoreamort.instalment_status='NY' 
WHERE new_loan_appstore.trn_id=theLoanTxnId;
-- SELECT 'NY',theLoanTxnId,theDate;


INNER_BLOCK: BEGIN
DECLARE counter INT DEFAULT 0;
DECLARE theBatchNoS,theAccountNumber VARCHAR(100);
DECLARE amountPaid,amountDisbursed,InterestPaid,principalPaid DOUBLE; 
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE dateProcessed DATE;

DECLARE forBatchNos CURSOR FOR SELECT chq_number FROM pmms.bsanca01123000110 INNER JOIN new_loan_appstore ON bsanca01123000110.credit_account_no=new_loan_appstore.applicant_account_number ;
-- AND bsanca01123000110.trn_id>=theId
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;

--  SELECT 'NY2',theLoanTxnId,theDate;

OPEN forBatchNos; 


 
TXNIDS_LOOP:LOOP
SET counter=counter+1;
FETCH forBatchNos INTO theBatchNoS;

-- SELECT theBatchNoS;
SELECT trn_date,debit,credit,credit_account_no INTO dateProcessed, amountPaid,amountDisbursed,theAccountNumber FROM pmms.bsanca01123000110 WHERE  chq_number=theBatchNoS;

SELECT credit INTO principalPaid FROM pmms.bsanca01128000110 WHERE  chq_number=theBatchNoS;

SELECT credit INTO InterestPaid FROM pmms.bsanca03301000110 WHERE  chq_number=theBatchNoS;

SELECT credit INTO AccumInterestPaid FROM pmms.bsanca03311000110 WHERE  chq_number=theBatchNoS;

SELECT credit INTO PenaltyPaid FROM pmms.bsanca03312000110 WHERE  chq_number=theBatchNoS;


IF ISNULL(amountPaid) OR amountPaid='-' THEN
SET amountPaid=0.0;
END IF;

IF ISNULL(amountDisbursed) OR amountDisbursed='-' THEN
SET amountDisbursed=0.0;
END IF;

IF ISNULL(principalPaid) OR principalPaid='-' THEN
SET principalPaid=0.0;
END IF;

IF ISNULL(InterestPaid) OR InterestPaid='-' THEN
SET InterestPaid=0.0;
END IF;


IF ISNULL(AccumInterestPaid) OR AccumInterestPaid='-' THEN
SET AccumInterestPaid=0.0;
END IF;

IF ISNULL(PenaltyPaid) OR PenaltyPaid='-' THEN
SET PenaltyPaid=0.0;
END IF;


SELECT amountDisbursed;

-- IF counter<>1 AND amountDisbursed<=0.0 THEN

-- IF amountDisbursed>0.0  THEN

-- INSERT INTO pmms.loandisburserepaystatement SELECT  
-- NULL, 
-- TrnDate,                   
--  MonthPaid ,                 
--  YearPaid,                   
--  loanTrnId,                  
--  LoanId,                     
--  AccountNumber,              
--  BatchCode,                  
--  AmountDisbursed,            
--  ExpectedInterest,           
--  ExpectedTotalAmount,        
--  InterestRate,               
--  AmountPaid,                 
--  PrincipalPaid,              
--  InterestPaid,               
--  AccumulatedInterestPaid,    
--  LoanPenaltyPaid,            
--  PrincipalBalance,           
--  InterestBalance,            
--  AccumulatedInterestBalance, 
--  LoanPenaltyBalance,         
--  LoanBalance,                
--  LoanStatusReport,           
--  OtherOne,                   
--  OtherTwo,
--  OtherThree ,               
--  OtherFour FROM   loandisburserepaystatement1;                

-- DELETE FROM loandisburserepaystatement1;
-- ELSEIF amountDisbursed<=0.0 THEN

-- SELECT PrincipalBalance,InterestBalance,AccumulatedInterestBalance,LoanPenaltyBalance,LoanBalance INTO priBal,intBal,accumIntBal,loanPenBal,loanBal FROM pmms.loandisburserepaystatement WHERE loandisburserepaystatement.loanTrnId=theLoanTxnId ORDER BY loandisburserepaystatement.TrnId DESC LIMIT 1;

-- SET priBal=priBal-principalPaid,intBal=intBal-InterestPaid,accumIntBal=accumIntBal-AccumInterestPaid,loanPenBal=loanPenBal-PenaltyPaid,loanBal=loanBal-amountPaid;

-- SELECT InterestPaid,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal;

-- INSERT INTO pmms.loandisburserepaystatement VALUES(NULL,dateProcessed,MONTH(dateProcessed),YEAR(dateProcessed),theLoanTxnId,'newloan'+theAccountNumber,theAccountNumber,theBatchNoS,0.0,0.0,0.0,0,amountPaid,principalPaid,InterestPaid,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal,'Running','NA','NA','NA','NA');
-- SELECT amountPaid,theLoanTxnId,dateProcessed;
-- CALL updateTheLoanManagement(amountPaid,theLoanTxnId,dateProcessed);

-- END IF;
SELECT amountPaid,amountDisbursed,InterestPaid,principalPaid;

-- END IF;
SET amountPaid=0.0,amountDisbursed=0.0,InterestPaid=0.0,principalPaid=0.0;
SELECT counter,innerNotFound,theBatchNoS,theLoanTxnId;
 IF innerNotFound=1 THEN
SELECT 'EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEENNNNNNNNNNNNNNNNNNNNNNNNNNNNFFFFFFFFFFFFFFFFFFDDDDDDDDDD';
LEAVE TXNIDS_LOOP;
 END IF;


SET innerNotFound=0;

END LOOP TXNIDS_LOOP;
 CLOSE forBatchNos; 
END INNER_BLOCK;


SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;








DROP PROCEDURE IF EXISTS updateTheLoanManagement;
DELIMITER //
CREATE PROCEDURE updateTheLoanManagement(IN AmountPaid DOUBLE,IN theLoanId VARCHAR(60),instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE amountTxed,currentIntestX,totalInsterestX, currentPenaltyX,totalPenaltyX,currentAccumulatedInterestX,totalAccumulatedInterestX,currentPrincipalX,totalPrincipalX DOUBLE;

SET amountTxed=AmountPaid;

label1:REPEAT

SET runningInstalmentId=currentInstalmentNow(theLoanId);

SELECT  InterestRemaing INTO currentIntestX FROM new_loan_appstoreamort WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;
--  SELECT currentIntestX; 
IF ISNULL(currentIntestX) THEN
SET currentIntestX=0;
END IF;

-- SELECT currentIntestX; 

IF ISNULL(totalInsterestX) THEN
SET totalInsterestX=0;
END IF;

IF currentIntestX>0 THEN

IF currentIntestX>=amountTxed THEN
SET currentIntestX=amountTxed;
END IF;

CALL updateTheInterestComponent(runningInstalmentId,currentIntestX,theLoanId,instalmentPaidDate);

SET totalInsterestX=totalInsterestX+currentIntestX;

SET amountTxed=amountTxed-currentIntestX;

IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;





 SELECT LoanPenaltyRemaining INTO currentPenaltyX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

IF ISNULL(currentPenaltyX) THEN
SET currentPenaltyX=0;
END IF;

IF currentPenaltyX>0 THEN

IF currentPenaltyX>=amountTxed THEN
SET currentPenaltyX=amountTxed;
END IF;

CALL updateThePenaltyComponent(runningInstalmentId,currentPenaltyX,theLoanId,instalmentPaidDate);
IF ISNULL(totalPenaltyX) THEN
SET totalPenaltyX=0;
END IF;
SET totalPenaltyX=totalPenaltyX+currentPenaltyX;
SET amountTxed=amountTxed-currentPenaltyX;

IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;



 SELECT AccumulatedInterestRemaining INTO currentAccumulatedInterestX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

IF ISNULL(currentAccumulatedInterestX) THEN
SET currentAccumulatedInterestX=0;
END IF;

IF currentAccumulatedInterestX>0 THEN

IF currentAccumulatedInterestX>=amountTxed THEN
SET currentAccumulatedInterestX=amountTxed;
END IF;

CALL updateTheAccumulatedInterestComponent(runningInstalmentId,currentAccumulatedInterestX,theLoanId,instalmentPaidDate);
IF ISNULL(totalAccumulatedInterestX) THEN
SET totalAccumulatedInterestX=0;
END IF;
SET totalAccumulatedInterestX=totalAccumulatedInterestX+currentAccumulatedInterestX;
SET amountTxed=amountTxed-currentAccumulatedInterestX;

IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;





 SELECT PrincipalRemaining INTO currentPrincipalX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;


--  SELECT currentPrincipalX; 

IF ISNULL(currentPrincipalX) THEN
SET currentPrincipalX=0;
END IF;
-- SELECT currentPrincipalX; 
IF currentPrincipalX>0 THEN

IF currentPrincipalX>=amountTxed THEN
SET currentPrincipalX=amountTxed;
END IF;

CALL updateThePrincipalComponent(runningInstalmentId,currentPrincipalX,theLoanId,instalmentPaidDate);
IF ISNULL(totalPrincipalX) THEN
SET totalPrincipalX=0;
END IF;
SET totalPrincipalX=totalPrincipalX+currentPrincipalX;
SET amountTxed=amountTxed-currentPrincipalX;
-- SELECT amountTxed; 
IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;

UNTIL AmountPaid<=1  END REPEAT label1;

END//

 DELIMITER ;





DROP PROCEDURE IF EXISTS updateThePrincipalComponent;
DELIMITER //
CREATE PROCEDURE updateThePrincipalComponent(IN runningInstalmentId INT,IN currentPrincipalX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid, totalPrincipalRemaining,amountPaid,amountRemaining,princimpalPaid,principalRemaining  DOUBLE DEFAULT 0.0;

SELECT new_loan_appstore.instalments_paid,
new_loan_appstore.balance_due,
new_loan_appstore.TotalPrincipalPaid,
 new_loan_appstore.TotalPrincipalRemaining,
  new_loan_appstoreamort.instalment_paid,
  new_loan_appstoreamort.InstalmentRemaining,
  new_loan_appstoreamort.PrincipalPaid,
  new_loan_appstoreamort.PrincipalRemaining INTO  totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid,totalPrincipalRemaining,amountPaid,amountRemaining,princimpalPaid,principalRemaining FROM new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

  SET totalAmountPaid=totalAmountPaid+currentPrincipalX,totatAmountRemaianing=totatAmountRemaianing-currentPrincipalX,totalPrincipalPaid=totalPrincipalPaid+currentPrincipalX, totalPrincipalRemaining=totalPrincipalRemaining-currentPrincipalX,amountPaid=amountPaid+currentPrincipalX,
  amountRemaining=amountRemaining-currentPrincipalX,princimpalPaid=princimpalPaid+currentPrincipalX,principalRemaining=principalRemaining-currentPrincipalX;


UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET new_loan_appstore.instalments_paid=totalAmountPaid,
new_loan_appstore.balance_due=totatAmountRemaianing,
new_loan_appstore.TotalPrincipalPaid=totalPrincipalPaid,
 new_loan_appstore.TotalPrincipalRemaining=totalPrincipalRemaining,
  new_loan_appstoreamort.instalment_paid=amountPaid,
  new_loan_appstoreamort.InstalmentRemaining=amountRemaining,
  new_loan_appstoreamort.PrincipalPaid=princimpalPaid,
  new_loan_appstoreamort.PrincipalRemaining=principalRemaining   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

    
    IF amountRemaining=0.0 THEN
      
     
UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET 
  new_loan_appstoreamort.instalment_status='P',
  new_loan_appstoreamort. instalment_paid_date=instalmentPaidDate,
  new_loan_appstoreamort.instalment_paid_variance=DATEDIFF(new_loan_appstoreamort.instalment_due_date, new_loan_appstoreamort.instalment_paid_date)   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;


    END IF;

END//

 DELIMITER ;





DROP PROCEDURE IF EXISTS updateThePenaltyComponent;
DELIMITER //
CREATE PROCEDURE updateThePenaltyComponent(IN runningInstalmentId INT,IN currentPenaltyX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE totalAmountPaid,totatAmountRemaianing,totalPenaltyPaid, totalPenaltyRemaining,amountPaid,amountRemaining,penaltyPaid,penaltyRemaining  DOUBLE DEFAULT 0.0;

SELECT new_loan_appstore.instalments_paid,
new_loan_appstore.balance_due,
new_loan_appstore.TotalLoanPenaltyPaid,
 new_loan_appstore.TotalLoanPenaltyRemaining,
  new_loan_appstoreamort.instalment_paid,
  new_loan_appstoreamort.InstalmentRemaining,
  new_loan_appstoreamort.LoanPenaltyPaid,
  new_loan_appstoreamort.LoanPenaltyRemaining INTO  totalAmountPaid,totatAmountRemaianing,totalPenaltyPaid,totalPenaltyRemaining,amountPaid,amountRemaining,penaltyPaid,penaltyRemaining FROM new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

  SET totalAmountPaid=totalAmountPaid+currentPenaltyX,totatAmountRemaianing=totatAmountRemaianing-currentPenaltyX,totalPenaltyPaid=totalPenaltyPaid+currentPenaltyX, totalPenaltyRemaining=totalPenaltyRemaining-currentPenaltyX,amountPaid=amountPaid+currentPenaltyX,
  amountRemaining=amountRemaining-currentPenaltyX,
  penaltyPaid=penaltyPaid+currentPenaltyX,penaltyRemaining=penaltyRemaining-currentPenaltyX;


UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET new_loan_appstore.instalments_paid=totalAmountPaid,
new_loan_appstore.balance_due=totatAmountRemaianing,
new_loan_appstore.TotalLoanPenaltyPaid=totalPenaltyPaid,
 new_loan_appstore.TotalLoanPenaltyRemaining=totalPenaltyRemaining,
  new_loan_appstoreamort.instalment_paid=amountPaid,
  new_loan_appstoreamort.InstalmentRemaining=amountRemaining,

  new_loan_appstoreamort.LoanPenaltyPaid=penaltyPaid,
  new_loan_appstoreamort.penaltyRemaining=LoanPenaltyRemaining   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

    
    IF amountRemaining=0.0 THEN
      
     
UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET 
  new_loan_appstoreamort.instalment_status='P',
  new_loan_appstoreamort. instalment_paid_date=instalmentPaidDate,
  new_loan_appstoreamort.instalment_paid_variance=DATEDIFF(new_loan_appstoreamort.instalment_due_date, new_loan_appstoreamort.instalment_paid_date)   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;


    END IF;


END//

 DELIMITER ;



   
   


DROP PROCEDURE IF EXISTS updateTheAccumulatedInterestComponent;
DELIMITER //
CREATE PROCEDURE updateTheAccumulatedInterestComponent(IN runningInstalmentId INT,IN currentAccumulatedInterestX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE totalAmountPaid,totatAmountRemaianing,totalAccumulatedInterestPaid, totalAccumulatedInterestRemaining,amountPaid,amountRemaining,AccumulatedInterestPaid,AccumulatedInterestRemaining  DOUBLE DEFAULT 0.0;

SELECT new_loan_appstore.instalments_paid,
new_loan_appstore.balance_due,
new_loan_appstore.TotalLoanAccumulatedInterestPaid,
 new_loan_appstore.TotalLoanAccumulatedInterestRemaining,
  new_loan_appstoreamort.instalment_paid,
  new_loan_appstoreamort.InstalmentRemaining,
  new_loan_appstoreamort.LoanAccumulatedInterestPaid,
  new_loan_appstoreamort.LoanAccumulatedInterestRemaining INTO  totalAmountPaid,totatAmountRemaianing,totalAccumulatedInterestPaid,totalAccumulatedInterestRemaining,amountPaid,amountRemaining,AccumulatedInterestPaid,AccumulatedInterestRemaining FROM new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

  SET totalAmountPaid=totalAmountPaid+currentAccumulatedInterestX,totatAmountRemaianing=totatAmountRemaianing-currentAccumulatedInterestX,totalAccumulatedInterestPaid=totalAccumulatedInterestPaid+currentAccumulatedInterestX, totalAccumulatedInterestRemaining=totalAccumulatedInterestRemaining-currentAccumulatedInterestX,amountPaid=amountPaid+currentAccumulatedInterestX,
  amountRemaining=amountRemaining-currentAccumulatedInterestX,
  AccumulatedInterestPaid=AccumulatedInterestPaid+currentAccumulatedInterestX,AccumulatedInterestRemaining=AccumulatedInterestRemaining-currentAccumulatedInterestX;


UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET new_loan_appstore.instalments_paid=totalAmountPaid,
new_loan_appstore.balance_due=totatAmountRemaianing,
new_loan_appstore.TotalLoanAccumulatedInterestPaid=totalAccumulatedInterestPaid,
 new_loan_appstore.TotalLoanAccumulatedInterestRemaining=totalAccumulatedInterestRemaining,
  new_loan_appstoreamort.instalment_paid=amountPaid,
  new_loan_appstoreamort.InstalmentRemaining=amountRemaining,

  new_loan_appstoreamort.LoanAccumulatedInterestPaid=AccumulatedInterestPaid,
  new_loan_appstoreamort.AccumulatedInterestRemaining=LoanAccumulatedInterestRemaining   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

    
    IF amountRemaining=0.0 THEN
      
     
UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET 
  new_loan_appstoreamort.instalment_status='P',
  new_loan_appstoreamort. instalment_paid_date=instalmentPaidDate,
  new_loan_appstoreamort.instalment_paid_variance=DATEDIFF(new_loan_appstoreamort.instalment_due_date, new_loan_appstoreamort.instalment_paid_date)   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;


    END IF;



END//

 DELIMITER ;






DROP PROCEDURE IF EXISTS updateTheInterestComponent;
DELIMITER //
CREATE PROCEDURE updateTheInterestComponent(IN runningInstalmentId INT,IN currentInterestX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE totalAmountPaid,totatAmountRemaianing,totalInterestPaid, totalInterestRemaining,amountPaid,amountRemaining,InterestPaid,InterestRemaining  DOUBLE DEFAULT 0.0;

SELECT new_loan_appstore.instalments_paid,
new_loan_appstore.balance_due,
new_loan_appstore.TotalInterestPaid,
 new_loan_appstore.TotalInterestRemaining,
  new_loan_appstoreamort.instalment_paid,
  new_loan_appstoreamort.InstalmentRemaining,
  new_loan_appstoreamort.InterestPaid,
  new_loan_appstoreamort. InterestRemaing INTO  totalAmountPaid,totatAmountRemaianing,totalInterestPaid,totalInterestRemaining,amountPaid,amountRemaining,InterestPaid,InterestRemaining FROM new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

  SET totalAmountPaid=totalAmountPaid+currentInterestX,totatAmountRemaianing=totatAmountRemaianing-currentInterestX,totalInterestPaid=totalInterestPaid+currentInterestX, totalInterestRemaining=totalInterestRemaining-currentInterestX,amountPaid=amountPaid+currentInterestX,
  amountRemaining=amountRemaining-currentInterestX,
  InterestPaid=InterestPaid+currentInterestX,InterestRemaining=InterestRemaining-currentInterestX;


UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET new_loan_appstore.instalments_paid=totalAmountPaid,
new_loan_appstore.balance_due=totatAmountRemaianing,
new_loan_appstore.TotalInterestPaid=totalInterestPaid,
new_loan_appstore.TotalInterestRemaining=totalInterestRemaining,
  new_loan_appstoreamort.instalment_paid=amountPaid,
  new_loan_appstoreamort.InstalmentRemaining=amountRemaining,

 new_loan_appstoreamort.InterestPaid=InterestPaid,
new_loan_appstoreamort. InterestRemaing=InterestRemaining   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;

    
    IF amountRemaining=0.0 THEN
      
     
UPDATE new_loan_appstore INNER JOIN  new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id    SET 
  new_loan_appstoreamort.instalment_status='P',
  new_loan_appstoreamort. instalment_paid_date=instalmentPaidDate,
  new_loan_appstoreamort.instalment_paid_variance=DATEDIFF(new_loan_appstoreamort.instalment_due_date, new_loan_appstoreamort.instalment_paid_date)   WHERE new_loan_appstoreamort.instalment_no=runningInstalmentId AND new_loan_appstore.trn_id=theLoanId;


    END IF;



END//

 DELIMITER ;







/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS currentInstalmentNow;
DELIMITER ##
CREATE FUNCTION currentInstalmentNow(theLoanId VARCHAR(100)) 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE theInstalmentNo VARCHAR(200);

SELECT instalment_no INTO theInstalmentNo FROM new_loan_appstoreamort WHERE  master1_id=theLoanId AND NOT instalment_status='P' ORDER BY trn_id ASC LIMIT 1;

RETURN theInstalmentNo;
END ##
DELIMITER ;










DROP PROCEDURE IF EXISTS normaliseDueDates;
DELIMITER //
CREATE PROCEDURE normaliseDueDates() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId VARCHAR(20);
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from new_loan_appstore WHERE  loan_cycle_status='Disbursed';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
 SELECT theLoanTxnId; 

/* SET c=c+1; */
/* SELECT c; */

INNER_BLOCK: BEGIN

DECLARE theBatchNoS INT;
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT instalment_no FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 

SELECT instalment_due_date INTO @theDueDate FROM new_loan_appstoreamort where instalment_no=1 AND master1_id=theLoanTxnId;
 SELECT @theDueDate,theLoanTxnId;
TXNIDS_LOOP:LOOP
SET @theDueDate=DATE_ADD(@theDueDate, INTERVAL 1 DAY);
SELECT @theDueDate;
FETCH forBatchNos INTO theBatchNoS;
SELECT  theBatchNoS;
SET @nextInstal=theBatchNoS+1;
SELECT @nextInstal;
  SET @sql_text = concat(CAST("UPDATE new_loan_appstoreamort SET instalment_due_date='" AS CHAR CHARACTER SET utf8),@theDueDate ,CAST("' WHERE instalment_no=" AS CHAR CHARACTER SET utf8),@nextInstal,CAST(" AND master1_id='" AS CHAR CHARACTER SET utf8),theLoanTxnId,CAST("'" AS CHAR CHARACTER SET utf8));
    SELECT @sql_text; 
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;

 IF innerNotFound=1 THEN
 SET @nextInstal=NULL;
LEAVE TXNIDS_LOOP;
 END IF;



SET innerNotFound=0;
END LOOP TXNIDS_LOOP; 
CLOSE forBatchNos; 
END INNER_BLOCK;


SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;





DROP PROCEDURE IF EXISTS normaliseActualDueDates;
DELIMITER //
CREATE PROCEDURE normaliseActualDueDates() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId VARCHAR(20);
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from new_loan_appstore WHERE loan_cycle_status='Disbursed' AND trn_date='2021-10-31';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
 SELECT theLoanTxnId; 

/* SET c=c+1; */
/* SELECT c; */

INNER_BLOCK: BEGIN

DECLARE theBatchNoS INT;
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT instalment_no FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 

SELECT instalment_due_date INTO @theDueDate FROM new_loan_appstoreamort where instalment_no=1 AND master1_id=theLoanTxnId;
 SELECT @theDueDate,theLoanTxnId;
 SET @nextInstal=1;
TXNIDS_LOOP:LOOP
SET @theDueDate=DATE_ADD(@theDueDate, INTERVAL 1 DAY);
SELECT @theDueDate;
FETCH forBatchNos INTO theBatchNoS;
SELECT  theBatchNoS;

SELECT @nextInstal;
  SET @sql_text = concat(CAST("UPDATE new_loan_appstoreamort SET instalment_due_date='" AS CHAR CHARACTER SET utf8),@theDueDate ,CAST("' WHERE instalment_no=" AS CHAR CHARACTER SET utf8),@nextInstal,CAST(" AND master1_id='" AS CHAR CHARACTER SET utf8),theLoanTxnId,CAST("'" AS CHAR CHARACTER SET utf8));
    SELECT @sql_text; 
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;
SET @nextInstal=theBatchNoS+1;
 IF innerNotFound=1 THEN
 SET @nextInstal=NULL;
LEAVE TXNIDS_LOOP;
 END IF;



SET innerNotFound=0;
END LOOP TXNIDS_LOOP; 
CLOSE forBatchNos; 
END INNER_BLOCK;


SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;





        DROP PROCEDURE IF EXISTS updateMasterX;

        DELIMITER //
		
        CREATE PROCEDURE updateMasterX(IN TrnDate DATE,IN accountNumber VARCHAR(20),IN NewLedgerBalance VARCHAR(20),IN StaffId VARCHAR(8))
        BEGIN

        CALL priviousBalanceX(accountNumber,@previouslyAdded);
/* SELECT @previouslyAdded; */
        CALL currentMasterBalanceX(accountNumber,@currentlAdded);
/* SELECT @currentlAdded; */
        CALL accountNma(accountNumber,@accountName);
/* SELECT @accountName; */
        SET @newMasterBalance=(@currentlAdded-@previouslyAdded)+NewLedgerBalance;
/* SELECT @newMasterBalance; */
         CALL accountMasterX(accountNumber,@accountMaster);
/*           SELECT @accountMaster; */
		  
		  SET @qry=CONCAT(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@accountMaster,CAST(" VALUES(NULL" AS CHAR CHARACTER SET utf8),CAST(",'" AS CHAR CHARACTER SET utf8),TrnDate,CAST("','" AS CHAR CHARACTER SET utf8),TrnDate,CAST("','" AS CHAR CHARACTER SET utf8),@accountName,CAST("','" AS CHAR CHARACTER SET utf8),accountNumber,CAST("','" AS CHAR CHARACTER SET utf8),NewLedgerBalance,CAST("','" AS CHAR CHARACTER SET utf8),@newMasterBalance,CAST("','" AS CHAR CHARACTER SET utf8),StaffId,CAST("')" AS CHAR CHARACTER SET utf8) );
/*         SELECT @qry; */
		PREPARE st FROM @qry; EXECUTE st;DROP PREPARE st;

        END //

        DELIMITER ;










      DROP PROCEDURE IF EXISTS getStatementOfLoanStatus;

        DELIMITER //
		
        CREATE PROCEDURE getStatementOfLoanStatus(IN startDate VARCHAR(40),IN endDate VARCHAR(40))
        BEGIN
          
		   DECLARE thePrincipalBalance DOUBLE;


       DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;
 DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT DISTINCT AccountNumber  FROM loandisburserepaystatement WHERE TrnDate>=startDate AND TrnDate<=endDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
DROP TABLE IF EXISTS theStatementStatusDetails;

CREATE  TEMPORARY TABLE theStatementStatusDetails(
  id  INT,
  borrower_name VARCHAR(200),
  borrower_account VARCHAR(200),
  princimcipal_openingBal DOUBLE,
  princincipal_disbursed DOUBLE,
  principal_paid DOUBLE,
  closing_princiBal DOUBLE,
    interest_openingBal DOUBLE,
  expected_interest DOUBLE,
  interest_paid DOUBLE,
  closing_InterestBal DOUBLE,PRIMARY KEY (id))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
CALL accountNma(txnIdS,@accName);

 SELECT PrincipalBalance INTO thePrincipalBalance FROM loandisburserepaystatement WHERE AccountNumber=txnIdS AND TrnDate=startDate AND PrincipalBalance>0 ORDER BY  TrnId DESC LIMIT 1;
 
 SELECT @accName,txnIdS,thePrincipalBalance;

--  INSERT INTO temp_dailycollection VALUES(Ids,@narration,@ExpectedAm,@actualToday,@Varince,@Vstatus);

SET thePrincipalBalance=NULL;
SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;
 


-- SELECT id ,temp_NarrationC,temp_ExpectedCollection ,temp_ActualCollection,temp_BalColl,temp_Variance  FROM temp_dailycollection;


		 

        END //

        DELIMITER ;









  -- -----------------------------------------------------
-- theLoginStatus

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/

DROP FUNCTION IF EXISTS theBalanceLoan;

DELIMITER $$

CREATE FUNCTION theBalanceLoan(theLoanTrnId VARCHAR(20)) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
DECLARE princimpalBal DOUBLE;
SELECT PrincipalBalance INTO princimpalBal FROM loandisburserepaystatement WHERE loanTrnId=theLoanTrnId ORDER BY TrnId DESC LIMIT 1;

IF ISNULL(princimpalBal) THEN
SET princimpalBal=0;
END IF;

RETURN princimpalBal;
END $$
DELIMITER ;







  -- -----------------------------------------------------
-- previousMasterBalance

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/

DROP FUNCTION IF EXISTS previousCashMasterBalance;

DELIMITER $$

CREATE FUNCTION previousCashMasterBalance(trnId VARCHAR(20)) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
DECLARE priviousMasterBal DOUBLE;

SELECT master_balance INTO priviousMasterBal FROM bsanca05502000010 WHERE trn_id=trnId-1;

IF ISNULL(priviousMasterBal) THEN
SET priviousMasterBal=0;
END IF;

RETURN priviousMasterBal;
END $$
DELIMITER ;










  -- -----------------------------------------------------
-- ledgerBalance

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/

DROP PROCEDURE IF EXISTS ledgerBalance;

DELIMITER $$

CREATE PROCEDURE ledgerBalance() 
BEGIN
DECLARE ledgerBal,theAccountBal,privLedger DOUBLE;
DECLARE theAccountNumber VARCHAR(60);

DECLARE l_done, theId INTEGER DEFAULT 0;

 DECLARE forSelectingTrnId CURSOR FOR SELECT trn_id  FROM bsanca05502000010 WHERE trn_id>1500;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

 OPEN forSelectingTrnId;


accounts_loop: LOOP 

 FETCH forSelectingTrnId into theId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SELECT account_number INTO theAccountNumber from bsanca05502000010 where trn_id=theId;

 SELECT account_balance INTO  privLedger FROM bsanca05502000010 WHERE  account_number=theAccountNumber AND trn_id<theId ORDER BY trn_id DESC LIMIT 1;

SELECT account_balance INTO theAccountBal FROM bsanca05502000010 WHERE trn_id=theId;

IF ISNULL(privLedger) THEN
SET privLedger=0;
END IF;

SELECT theAccountNumber,theId,privLedger,theAccountBal;

SET ledgerBal=(previousCashMasterBalance(theId)-privLedger)+theAccountBal;

SELECT ledgerBal;

update  bsanca05502000010 set  master_balance=ledgerBal WHERE trn_id=theId;


SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTrnId;

END $$
DELIMITER ;





DROP PROCEDURE IF EXISTS deleteOldData;
DELIMITER //
CREATE PROCEDURE deleteOldData() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theAccount VARCHAR(60);
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forAccountNumbers CURSOR FOR SELECT account_number from account_created_store;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forAccountNumbers; 

ACCOUNTS_LOOP: LOOP 

FETCH forAccountNumbers INTO theAccount;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
 SELECT theAccount; 

/* SET c=c+1; */
/* SELECT c; */
	-- Total					1584000			
INNER_BLOCK: BEGIN

DECLARE theBatchNoS INT;
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT instalment_no FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 

SELECT instalment_due_date INTO @theDueDate FROM new_loan_appstoreamort where instalment_no=1 AND master1_id=theLoanTxnId;
 SELECT @theDueDate,theLoanTxnId;
 SET @nextInstal=1;
TXNIDS_LOOP:LOOP
SET @theDueDate=DATE_ADD(@theDueDate, INTERVAL 1 DAY);
SELECT @theDueDate;
FETCH forBatchNos INTO theBatchNoS;
SELECT  theBatchNoS;

SELECT @nextInstal;
  SET @sql_text = concat(CAST("UPDATE new_loan_appstoreamort SET instalment_due_date='" AS CHAR CHARACTER SET utf8),@theDueDate ,CAST("' WHERE instalment_no=" AS CHAR CHARACTER SET utf8),@nextInstal,CAST(" AND master1_id='" AS CHAR CHARACTER SET utf8),theLoanTxnId,CAST("'" AS CHAR CHARACTER SET utf8));
    SELECT @sql_text; 
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;
SET @nextInstal=theBatchNoS+1;
 IF innerNotFound=1 THEN
 SET @nextInstal=NULL;
LEAVE TXNIDS_LOOP;
 END IF;



SET innerNotFound=0;
END LOOP TXNIDS_LOOP; 
CLOSE forBatchNos; 
END INNER_BLOCK;


SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;


    

        DROP PROCEDURE IF EXISTS reverseTxnsX;


        DELIMITER //

-- 08/01/2023	08/01/2023	TURYAHIKAYO ARTHUR   0776088599s Account Deposit for Loan Payment
--   Dated 08/01/2023	300000.0	-	3814801.0	BTN216497
-- CALL reverseTxnsX('BTN216497');

        CREATE PROCEDURE  reverseTxnsX(IN batchNumber VARCHAR(100) )  BEGIN

     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,loanTrnIdL INT ;
     DECLARE txnDate DATE;
     DECLARE the_narration VARCHAR(500);
     DECLARE the_debit,the_credit,the_account_no,the_contra_account_no,the_trn_type,DrCr,txnAmount  VARCHAR(100);

 DECLARE fortheTxnId CURSOR FOR SELECT trn_id FROM general_ledger WHERE chq_number=batchNumber ORDER BY trn_id DESC;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



 OPEN fortheTxnId;

txnId_loop: LOOP 

 FETCH fortheTxnId into txnId;

 IF l_done=1 THEN

LEAVE txnId_loop;

 END IF;



SELECT trn_date, narration,debit,credit,debit_account_no,credit_account_no,trn_type INTO txnDate, the_narration,the_debit,the_credit,the_account_no,the_contra_account_no,the_trn_type FROM general_ledger WHERE trn_id=txnId;

IF txnDate=DATE(NOW()) THEN

IF the_debit='-' THEN
SET DrCr='Dr',txnAmount=the_credit;

ELSE
SET DrCr='Cr',txnAmount=the_debit;
END IF;



-- SELECT DrCr,txnAmount,the_debit,the_credit,the_account_no,the_trn_type;

IF DrCr='Dr' THEN

IF (SUBSTRING(the_account_no,2,2)='11' OR SUBSTRING(the_account_no,2,2)='22') THEN
-- SELECT DrCr;
-- SELECT SUBSTRING(the_account_no,2,2);

SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));

-- SELECT @masterAccount;

  SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalNow FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
  DROP PREPARE stmt1;
--  SELECT  @ledgerBalNow;
SET  @ledgerBalNow=@ledgerBalNow+txnAmount;
SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
CALL accountNma(the_contra_account_no,@accountName);

CALL postingTxnsX(NULL,DATE(NOW()),the_narration,DATE(NOW()),txnAmount,'-',@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"Reversal",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');

IF ((the_account_no LIKE '01128000%') AND (the_trn_type='LoanR')) THEN

CALL updateThePrincipalComp(the_contra_account_no,txnAmount);

IF EXISTS(SELECT * FROM  loandisburserepaystatement WHERE BatchCode=batchNumber) THEN
DELETE FROM loandisburserepaystatement WHERE  BatchCode=batchNumber;
END IF;

END IF;



END IF;


IF (SUBSTRING(the_account_no,2,2)='33' OR SUBSTRING(the_account_no,2,2)='44' OR SUBSTRING(the_account_no,2,2)='55') THEN

SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));

-- SELECT @masterAccount;

  SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalNow FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
  DROP PREPARE stmt1;
--  SELECT  @ledgerBalNow;
SET  @ledgerBalNow=@ledgerBalNow-txnAmount;
SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
CALL accountNma(the_contra_account_no,@accountName);

CALL postingTxnsX(NULL,DATE(NOW()),the_narration,DATE(NOW()),txnAmount,'-',@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"Reversal",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');

-- 05/01/2023	03301000110	Gross Interest Income1	Gross Interest Income	5.73585603E8	Active
-- 10/03/2020	03312000110	Loan Surcharge1	Loan Surcharge	0.0	Active
-- 23/08/2022	03311000110	Accumulated Interest Income1	Accumulated Interest Income	-678398.0	Active
-- Credits	Murungi Merab	05502020510	Customer Deposits	776871.0
IF ((the_account_no LIKE '03301000%') AND (the_trn_type='LoanR')) THEN

CALL updateTheInterestComp(the_contra_account_no,txnAmount);
IF EXISTS(SELECT * FROM  loandisburserepaystatement WHERE BatchCode=batchNumber) THEN
DELETE FROM loandisburserepaystatement WHERE  BatchCode=batchNumber;
END IF;

END IF;


IF ((the_account_no LIKE '03312000%') AND (the_trn_type='LoanR')) THEN

CALL updateThePenaltyComp(the_contra_account_no,txnAmount);
IF EXISTS(SELECT * FROM  loandisburserepaystatement WHERE BatchCode=batchNumber) THEN
DELETE FROM loandisburserepaystatement WHERE  BatchCode=batchNumber;
END IF;

END IF;


IF ((the_account_no LIKE '03311000%') AND (the_trn_type='LoanR')) THEN

CALL updateTheAccumInterComp(the_contra_account_no,txnAmount);

IF EXISTS(SELECT * FROM  loandisburserepaystatement WHERE BatchCode=batchNumber) THEN
DELETE FROM loandisburserepaystatement WHERE  BatchCode=batchNumber;
END IF;

END IF;



        
END IF;


END IF;


IF DrCr='Cr' THEN

IF (SUBSTRING(the_account_no,2,2)='11' OR SUBSTRING(the_account_no,2,2)='22') THEN

SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));

-- SELECT @masterAccount;

  SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalNow FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
  DROP PREPARE stmt1;
--  SELECT  @ledgerBalNow;
SET  @ledgerBalNow=@ledgerBalNow-txnAmount;
SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
CALL accountNma(the_contra_account_no,@accountName);

CALL postingTxnsX(NULL,DATE(NOW()),the_narration,DATE(NOW()),'-',txnAmount,@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"Reversal",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');

IF ((the_account_no LIKE '01128000%') AND (the_trn_type='Gen')) THEN

SELECT loanTrnId  INTO loanTrnIdL FROM   pmms.loandisburserepaystatement WHERE BatchCode=batchNumber;

IF EXISTS(SELECT * FROM  pmms_loans.new_loan_appstore WHERE trn_id=loanTrnIdL) THEN
DELETE FROM pmms_loans.new_loan_appstore WHERE trn_id=loanTrnIdL;
DELETE FROM pmms_loans.new_loan_appstore1 WHERE trn_id=loanTrnIdL;
DELETE FROM pmms_loans.new_loan_appstoreamort WHERE master1_id=loanTrnIdL;
END IF;

-- CALL reverseTxnsX('BTN15761');

IF EXISTS(SELECT * FROM  loandisburserepaystatement WHERE BatchCode=batchNumber) THEN
DELETE FROM loandisburserepaystatement WHERE  BatchCode=batchNumber;
END IF;

END IF;


END IF;


IF (SUBSTRING(the_account_no,2,2)='33' OR SUBSTRING(the_account_no,2,2)='44' OR SUBSTRING(the_account_no,2,2)='55') THEN

 SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));

-- SELECT @masterAccount;

  SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalNow FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
  DROP PREPARE stmt1;
--  SELECT  @ledgerBalNow;
SET  @ledgerBalNow=@ledgerBalNow+txnAmount;
SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
CALL accountNma(the_contra_account_no,@accountName);

CALL postingTxnsX(NULL,DATE(NOW()),the_narration,DATE(NOW()),'-',txnAmount,@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"Reversal",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');
END IF;

END IF;

END IF;

 END LOOP txnId_loop;
SET l_done=0;
 CLOSE fortheTxnId;



        END //

        DELIMITER ;
-- 08/01/2023	08/01/2023	NAMULI ALLET  0705052803s Account Deposit for Loan Payment
--   Dated 08/01/2023	400000.0	-	3914801.0	BTN216498

-- CALL reverseTxnsX('BTN216498');

         DROP PROCEDURE IF EXISTS updateTheInterestComp; 

        DELIMITER //
 
        CREATE PROCEDURE  updateTheInterestComp(IN accountNumber VARCHAR(100),IN txnAmount VARCHAR(100))  BEGIN



     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,theLoanId INT ;
 DECLARE theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing,theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining,theActualInterestPaid,theActualInstalmentsPaid DOUBLE;
 DECLARE theTrnId INT;

 DECLARE fortheTxnId CURSOR FOR SELECT new_loan_appstoreamort.trn_id FROM pmms_loans.new_loan_appstoreamort INNER JOIN pmms_loans.new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.applicant_account_number= accountNumber AND(new_loan_appstore.loan_cycle_status='Disbursed' OR new_loan_appstore.loan_cycle_status='Renewed') AND new_loan_appstoreamort.instalment_paid_date=DATE(NOW())  ORDER BY new_loan_appstoreamort.trn_id DESC;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

-- 06/01/2023	06/01/2023	Basheija Charles 0785109562s Account Deposit for Loan Payment
--   Dated 06/01/2023	100000.0	-	5.143899953333333E7	BTN15756

 OPEN fortheTxnId;

txnId_loop: LOOP 

 FETCH fortheTxnId into txnId;
-- SELECT txnId;

 SELECT master1_id,instalment_paid,InterestPaid, InstalmentRemaining,InterestRemaing INTO theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing FROM pmms_loans.new_loan_appstoreamort  WHERE trn_id=txnId;

-- SELECT theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing;
SET theActualInstalmentsPaid=theInstalmentPaid;


 SELECT balance_due,instalments_paid, TotalInterestPaid,TotalInterestRemaining INTO theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining FROM pmms_loans.new_loan_appstore WHERE trn_id=theLoanId;



 IF ISNULL(theInterestPaid) THEN
SET theInterestPaid=0.0;
 END IF;

 IF txnAmount<=1 OR l_done=1 THEN
LEAVE txnId_loop;
 END IF;

 IF txnAmount<theInterestPaid THEN
SET theActualInterestPaid=txnAmount;
ELSE
SET theActualInterestPaid=theInterestPaid;

END IF;
-- -- SELECT txnAmount,theInterestPaid;

-- -- select txnAmount

-- IF txnAmount<theInterestPaid THEN 
-- SET theInterestPaid=txnAmount;
-- END IF;

UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid=(theInstalmentPaid-theActualInterestPaid),InterestPaid=(theInterestPaid-theActualInterestPaid), InstalmentRemaining=(theInstalmentRemaining+theActualInterestPaid),InterestRemaing=(theInterestRemaing+theActualInterestPaid),instalment_status='NY' WHERE trn_id=txnId;
SET theActualInstalmentsPaid=theActualInstalmentsPaid-theActualInterestPaid;
UPDATE pmms_loans.new_loan_appstore SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalInterestPaid=(theTotalInterestPaid-theActualInterestPaid),TotalInterestRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

UPDATE pmms_loans.new_loan_appstore1 SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalInterestPaid=(theTotalInterestPaid-theActualInterestPaid),TotalInterestRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;


IF theActualInstalmentsPaid=0 THEN
UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid_date=instalment_due_date WHERE trn_id=txnId;
END IF;

-- SELECT "INTEREST RA" ,theActualInstalmentsPaid;

SET txnAmount=txnAmount-theActualInterestPaid;



 END LOOP txnId_loop;
SET l_done=0;
 CLOSE fortheTxnId;


        END //

        DELIMITER ;



-- 08/01/2023	08/01/2023	AKAMPUMUZA GILLIAN 0758066555s Account Deposit for Loan Payment
--   Dated 08/01/2023	500000.0	-	4014801.0	BTN216499

-- CALL reverseTxnsX('BTN216499');

         DROP PROCEDURE IF EXISTS updateTheAccumInterComp; 

        DELIMITER //
 
        CREATE PROCEDURE  updateTheAccumInterComp(IN accountNumber VARCHAR(100),IN txnAmount VARCHAR(100))  BEGIN



     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,theLoanId INT ;
 DECLARE theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing,theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining,theActualInterestPaid,theActualInstalmentsPaid DOUBLE;
 DECLARE theTrnId INT;

 DECLARE fortheTxnId CURSOR FOR SELECT new_loan_appstoreamort.trn_id FROM pmms_loans.new_loan_appstoreamort INNER JOIN pmms_loans.new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.applicant_account_number= accountNumber AND new_loan_appstoreamort.instalment_paid_date=DATE(NOW())  ORDER BY new_loan_appstoreamort.trn_id DESC;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



 OPEN fortheTxnId;

txnId_loop: LOOP 

 FETCH fortheTxnId into txnId;


 SELECT master1_id,instalment_paid,AccumulatedInterestPaid, InstalmentRemaining,AccumulatedInterestRemaining INTO theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing FROM pmms_loans.new_loan_appstoreamort  WHERE trn_id=txnId;

SET theActualInstalmentsPaid=theInstalmentPaid;

 SELECT balance_due,instalments_paid, TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining INTO theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining FROM pmms_loans.new_loan_appstore WHERE trn_id=theLoanId;



 IF ISNULL(theInterestPaid) THEN
SET theInterestPaid=0.0;
 END IF;

 IF txnAmount<=1 THEN
LEAVE txnId_loop;
 END IF;

--  IF txnAmount<theInterestPaid THEN
-- SET theInterestPaid=txnAmount;
-- END IF;
-- SELECT txnAmount,theInterestPaid;


-- IF txnAmount<theInterestPaid THEN 
-- SET theInterestPaid=txnAmount;
-- END IF;


IF txnAmount<theInterestPaid THEN
SET theActualInterestPaid=txnAmount;
ELSE
SET theActualInterestPaid=theInterestPaid;

END IF;


UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid=(theInstalmentPaid-theActualInterestPaid),AccumulatedInterestPaid=(theInterestPaid-theActualInterestPaid), InstalmentRemaining=(theInstalmentRemaining+theActualInterestPaid),AccumulatedInterestRemaining=(theInterestRemaing+theActualInterestPaid),instalment_status='NY' WHERE trn_id=txnId;
SET theActualInstalmentsPaid=theActualInstalmentsPaid-theActualInterestPaid;


UPDATE pmms_loans.new_loan_appstore SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalAccumulatedInterestPaid=(theTotalInterestPaid-theActualInterestPaid),TotalAccumulatedInterestRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

UPDATE pmms_loans.new_loan_appstore1 SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalAccumulatedInterestPaid=(theTotalInterestPaid-theActualInterestPaid),TotalAccumulatedInterestRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

IF theActualInstalmentsPaid=0 THEN
UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid_date=instalment_due_date WHERE trn_id=txnId;
END IF;

SET txnAmount=txnAmount-theActualInterestPaid;

-- SELECT "ACCUM INTEREST", theActualInstalmentsPaid;

 END LOOP txnId_loop;
SET l_done=0;
 CLOSE fortheTxnId;


        END //

        DELIMITER ;




         DROP PROCEDURE IF EXISTS updateThePenaltyComp; 

        DELIMITER //
 
        CREATE PROCEDURE  updateThePenaltyComp(IN accountNumber VARCHAR(100),IN txnAmount VARCHAR(100))  BEGIN



     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,theLoanId INT ;
 DECLARE theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing,theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining,theActualInterestPaid,theActualInstalmentsPaid DOUBLE;
 DECLARE theTrnId INT;

 DECLARE fortheTxnId CURSOR FOR SELECT new_loan_appstoreamort.trn_id FROM pmms_loans.new_loan_appstoreamort INNER JOIN pmms_loans.new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.applicant_account_number= accountNumber AND new_loan_appstoreamort.instalment_paid_date=DATE(NOW())  ORDER BY new_loan_appstoreamort.trn_id DESC;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



 OPEN fortheTxnId;

txnId_loop: LOOP 

 FETCH fortheTxnId into txnId;


 SELECT master1_id,instalment_paid,LoanPenaltyPaid, InstalmentRemaining,LoanPenaltyRemaining INTO theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing FROM pmms_loans.new_loan_appstoreamort  WHERE trn_id=txnId;

SET theActualInstalmentsPaid=theInstalmentPaid;

 SELECT balance_due,instalments_paid, TotalLoanPenaltyPaid, TotalLoanPenaltyRemaining INTO theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining FROM pmms_loans.new_loan_appstore WHERE trn_id=theLoanId;



 IF ISNULL(theInterestPaid) THEN
SET theInterestPaid=0.0;
 END IF;

 IF txnAmount<=1 THEN
LEAVE txnId_loop;
 END IF;

IF txnAmount<theInterestPaid THEN
SET theActualInterestPaid=txnAmount;
ELSE
SET theActualInterestPaid=theInterestPaid;

END IF;

-- SELECT "pENALTY COMP",theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing;
UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid=(theInstalmentPaid-theActualInterestPaid), LoanPenaltyPaid=(theInterestPaid-theActualInterestPaid), InstalmentRemaining=(theInstalmentRemaining+theActualInterestPaid),LoanPenaltyRemaining=(theInterestRemaing+theActualInterestPaid),instalment_status='NY' WHERE trn_id=txnId;

SET theActualInstalmentsPaid=theActualInstalmentsPaid-theActualInterestPaid;


UPDATE pmms_loans.new_loan_appstore SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalLoanPenaltyPaid=(theTotalInterestPaid-theActualInterestPaid),TotalLoanPenaltyRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

UPDATE pmms_loans.new_loan_appstore1 SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalLoanPenaltyPaid=(theTotalInterestPaid-theActualInterestPaid),TotalLoanPenaltyRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

IF theActualInstalmentsPaid=0 THEN
UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid_date=instalment_due_date WHERE trn_id=txnId;
END IF;

SET txnAmount=txnAmount-theActualInterestPaid;



 END LOOP txnId_loop;
SET l_done=0;
 CLOSE fortheTxnId;


        END //

        DELIMITER ;








         DROP PROCEDURE IF EXISTS updateThePrincipalComp; 

        DELIMITER //
 
        CREATE PROCEDURE  updateThePrincipalComp(IN accountNumber VARCHAR(100),IN txnAmount VARCHAR(100))  BEGIN



     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,theLoanId INT ;
 DECLARE theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing,theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining,theActualInterestPaid,theActualInstalmentsPaid DOUBLE;
 DECLARE theTrnId INT;

 DECLARE fortheTxnId CURSOR FOR SELECT new_loan_appstoreamort.trn_id FROM pmms_loans.new_loan_appstoreamort INNER JOIN pmms_loans.new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.applicant_account_number= accountNumber AND new_loan_appstoreamort.instalment_paid_date=DATE(NOW())  ORDER BY new_loan_appstoreamort.trn_id DESC;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



 OPEN fortheTxnId;

txnId_loop: LOOP 

 FETCH fortheTxnId into txnId;


 SELECT master1_id,instalment_paid,PrincipalPaid, InstalmentRemaining, PrincipalRemaining INTO theLoanId,theInstalmentPaid,theInterestPaid, theInstalmentRemaining,theInterestRemaing FROM pmms_loans.new_loan_appstoreamort  WHERE trn_id=txnId;

 SET theActualInstalmentsPaid=theInstalmentPaid;


 SELECT balance_due,instalments_paid, TotalPrincipalPaid,TotalPrincipalRemaining INTO theBalanceDue,theInstalmentsPaid,theTotalInterestPaid,theTotalInterestRemaining FROM pmms_loans.new_loan_appstore WHERE trn_id=theLoanId;



 IF ISNULL(theInterestPaid) THEN
SET theInterestPaid=0.0;
 END IF;

 IF txnAmount<=1 THEN
LEAVE txnId_loop;
 END IF;

 IF txnAmount<theInterestPaid THEN
SET theActualInterestPaid=txnAmount;
ELSE
SET theActualInterestPaid=theInterestPaid;

END IF;

UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid=(theInstalmentPaid-theActualInterestPaid),PrincipalPaid=(theInterestPaid-theActualInterestPaid), InstalmentRemaining=(theInstalmentRemaining+theActualInterestPaid),PrincipalRemaining=(theInterestRemaing+theActualInterestPaid),instalment_status='NY' WHERE trn_id=txnId;

SET theActualInstalmentsPaid=theActualInstalmentsPaid-theActualInterestPaid;



UPDATE pmms_loans.new_loan_appstore SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalPrincipalPaid=(theTotalInterestPaid-theActualInterestPaid),TotalPrincipalRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

UPDATE pmms_loans.new_loan_appstore1 SET balance_due=(theBalanceDue+theActualInterestPaid),instalments_paid=(theInstalmentsPaid-theActualInterestPaid), TotalPrincipalPaid=(theTotalInterestPaid-theActualInterestPaid),TotalPrincipalRemaining=(theTotalInterestRemaining+theActualInterestPaid) WHERE trn_id=theLoanId;

IF theActualInstalmentsPaid=0 THEN
UPDATE pmms_loans.new_loan_appstoreamort SET instalment_paid_date=instalment_due_date WHERE trn_id=txnId;
END IF;
SET txnAmount=txnAmount-theActualInterestPaid;

-- SELECT "PRINCIPAL", theActualInstalmentsPaid;

 END LOOP txnId_loop;
SET l_done=0;
 CLOSE fortheTxnId;


        END //

        DELIMITER ;
