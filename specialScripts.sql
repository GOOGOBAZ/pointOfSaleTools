
DROP PROCEDURE IF EXISTS migrateDateNewOnes;
 DELIMITER //

 CREATE PROCEDURE migrateDateNewOnes() READS SQL DATA BEGIN

DECLARE TrnId VARCHAR(40);

DECLARE loanId VARCHAR(40);

DECLARE l_done INTEGER;


DECLARE forselectingLoanId CURSOR FOR SELECT trn_id,loan_id  FROM pmms_loans.new_loan_appstore;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;




SET @sql_text2 = concat(CAST("DROP TABLE IF EXISTS new_loan_appstoreamort" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @sql_text3 = concat(CAST(
  
  "CREATE TABLE `new_loan_appstoreamort` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `instalment_no` int(11) NOT NULL DEFAULT '1',
  `princimpal_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `princimpal_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `interest_amount` varchar(50) NOT NULL DEFAULT '10000.0',
  `interest_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_paid` varchar(50) NOT NULL DEFAULT '0.0',
  `beginning_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `closing_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_due_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_status` varchar(50) NOT NULL DEFAULT 'P',
  `instalment_paid_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_paid_variance` varchar(50) NOT NULL DEFAULT '2 Days',
  `LoanPenalty` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `InstalmentRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `OtherOne` varchar(50) NOT NULL DEFAULT 'NYA',
  `OtherTwo` date NOT NULL DEFAULT '1979-01-01',
  `OtherThree` date NOT NULL DEFAULT '1979-01-01',
  `master1_id` varchar(50) NOT NULL DEFAULT '0.0',
  `master2_id` varchar(50) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt3 FROM @sql_text3;
  EXECUTE stmt3;
DROP PREPARE stmt3;



SET @sql_text4 = concat(CAST('DROP TABLE IF EXISTS `tempT`' AS CHAR CHARACTER SET utf8));

  PREPARE stmt4 FROM @sql_text4;
  EXECUTE stmt4;
DROP PREPARE stmt4;



SET @sql_text5 = concat(CAST("CREATE TABLE `tempT` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `instalment_no` int(11) NOT NULL DEFAULT 1,
  `princimpal_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `princimpal_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `interest_amount` varchar(50) NOT NULL DEFAULT '10000.0',
  `interest_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_paid` varchar(50) NOT NULL DEFAULT '0.0',
  `beginning_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `closing_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_due_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_status` varchar(50) NOT NULL DEFAULT 'P',
  `instalment_paid_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_paid_variance` varchar(50) NOT NULL DEFAULT '2 Days',
  `LoanPenalty` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `InstalmentRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `OtherOne` varchar(50) NOT NULL DEFAULT 'NYA',
  `OtherTwo` date NOT NULL DEFAULT '1979-01-01',
  `OtherThree` date NOT NULL DEFAULT '1979-01-01',
  `master1_id` varchar(50) NOT NULL DEFAULT '0.0',
  `master2_id` varchar(50) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt5 FROM @sql_text5;
  EXECUTE stmt5;
DROP PREPARE stmt5;


 OPEN forselectingLoanId;


loanIds_loop: LOOP 

 FETCH forselectingLoanId into TrnId, loanId;

SELECT TrnId;

SELECT loanId;

 IF l_done=1 THEN

LEAVE loanIds_loop;

 END IF;

SET @tableName=concat(loanId);



SET @sql_text1 = concat(CAST('INSERT INTO tempT ( 
  `instalment_no`,
  `princimpal_amount` ,
  `princimpal_amount_run_bal` ,
  `interest_amount` ,
  `interest_amount_run_bal` ,
  `instalment_amount` ,
  `instalment_paid` ,
  `beginning_bal` ,
  `closing_bal` ,
  `instalment_due_date` ,
  `instalment_status`,
  `instalment_paid_date` ,
  `instalment_paid_variance` ,
  `LoanPenalty` ,
  `AccruedInterest` ,
  `InstalmentRemaining` ,
  `PrincipalPaid` ,
  `PrincipalRemaining` ,
  `InterestPaid` ,
  `InterestRemaing` ,
  `LoanPenaltyPaid` ,
  `LoanPenaltyRemaining` ,
  `AccruedInterestPaid` ,
  `AccruedInterestRemaing` ,
  `AccumulatedInterest` ,
  `AccumulatedInterestPaid` ,
  `AccumulatedInterestRemaining` ,
  `OtherOne`,
  `OtherTwo` ,
  `OtherThree`) SELECT 
  `instalment_no`,
  `princimpal_amount` ,
  `princimpal_amount_run_bal` ,
  `interest_amount` ,
  `interest_amount_run_bal` ,
  `instalment_amount` ,
  `instalment_paid` ,
  `beginning_bal` ,
  `closing_bal` ,
  `instalment_due_date` ,
  `instalment_status`,
  `instalment_paid_date` ,
  `instalment_paid_variance` ,
  `LoanPenalty` ,
  `AccruedInterest` ,
  `InstalmentRemaining` ,
  `PrincipalPaid` ,
  `PrincipalRemaining` ,
  `InterestPaid` ,
  `InterestRemaing` ,
  `LoanPenaltyPaid` ,
  `LoanPenaltyRemaining` ,
  `AccruedInterestPaid` ,
  `AccruedInterestRemaing` ,
  `AccumulatedInterest` ,
  `AccumulatedInterestPaid` ,
  `AccumulatedInterestRemaining` ,
  `OtherOne`,
  `OtherTwo` ,
  `OtherThree` FROM  ' AS CHAR CHARACTER SET utf8),@tableName);
  
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

SET @Tid=concat(CAST("'" AS CHAR CHARACTER SET utf8),TrnId,CAST("'" AS CHAR CHARACTER SET utf8));
SET @Lid=concat(CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8));

  SET @sql_text9 = concat(CAST('UPDATE tempT SET master1_id=' AS CHAR CHARACTER SET utf8),@Tid,CAST(',  master2_id='AS CHAR CHARACTER SET utf8),@Lid);
PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;

  SET @sql_text7 = concat(CAST('INSERT INTO  pmms_loans.new_loan_appstoreamort( 
  `instalment_no`,
  `princimpal_amount` ,
  `princimpal_amount_run_bal` ,
  `interest_amount` ,
  `interest_amount_run_bal` ,
  `instalment_amount` ,
  `instalment_paid` ,
  `beginning_bal` ,
  `closing_bal` ,
  `instalment_due_date` ,
  `instalment_status`,
  `instalment_paid_date` ,
  `instalment_paid_variance` ,
  `LoanPenalty` ,
  `AccruedInterest` ,
  `InstalmentRemaining` ,
  `PrincipalPaid` ,
  `PrincipalRemaining` ,
  `InterestPaid` ,
  `InterestRemaing` ,
  `LoanPenaltyPaid` ,
  `LoanPenaltyRemaining` ,
  `AccruedInterestPaid` ,
  `AccruedInterestRemaing` ,
  `AccumulatedInterest` ,
  `AccumulatedInterestPaid` ,
  `AccumulatedInterestRemaining` ,
  `OtherOne`,
  `OtherTwo` ,
  `OtherThree`,
  `master1_id`,
  `master2_id`
  ) SELECT 
  `instalment_no`,
  `princimpal_amount` ,
  `princimpal_amount_run_bal` ,
  `interest_amount` ,
  `interest_amount_run_bal` ,
  `instalment_amount` ,
  `instalment_paid` ,
  `beginning_bal` ,
  `closing_bal` ,
  `instalment_due_date` ,
  `instalment_status`,
  `instalment_paid_date` ,
  `instalment_paid_variance` ,
  `LoanPenalty` ,
  `AccruedInterest` ,
  `InstalmentRemaining` ,
  `PrincipalPaid` ,
  `PrincipalRemaining` ,
  `InterestPaid` ,
  `InterestRemaing` ,
  `LoanPenaltyPaid` ,
  `LoanPenaltyRemaining` ,
  `AccruedInterestPaid` ,
  `AccruedInterestRemaing` ,
  `AccumulatedInterest` ,
  `AccumulatedInterestPaid` ,
  `AccumulatedInterestRemaining` ,
  `OtherOne`,
  `OtherTwo` ,
  `OtherThree`,
  `master1_id`,
  `master2_id` FROM pmms_loans.tempT ' AS CHAR CHARACTER SET utf8));
  PREPARE stmt7 FROM @sql_text7;
  EXECUTE stmt7;
DROP PREPARE stmt7;




  DELETE FROM pmms_loans.tempT;

 

SET l_done=0;

 END LOOP loanIds_loop;



 CLOSE forselectingLoanId;


  DROP TABLE pmms_loans.tempT;

 END //
 DELIMITER ;



 
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