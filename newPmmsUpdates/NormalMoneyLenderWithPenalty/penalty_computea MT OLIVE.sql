
-- update new_loan_appstore SET balance_due=(TotalPrincipalRemaining+TotalInterestRemaining),total_loanAmount=(princimpal_amount+total_interest),TotalLoanPenaltyRemaining=0;

-- update new_loan_appstore1 SET balance_due=(TotalPrincipalRemaining+TotalInterestRemaining),total_loanAmount=(princimpal_amount+total_interest),TotalLoanPenaltyRemaining=0;


-- update new_loan_appstoreamort SET  instalment_amount=( princimpal_amount+interest_amount),
--  InstalmentRemaining=(PrincipalRemaining+InterestRemaing),
--   LoanPenalty=0.0,
-- LoanPenaltyRemaining= 0.0;

-- UPDATE pmms.loandisburserepaystatement  SET LoanPenaltyBalance=0.0,ExpectedTotalAmount=(AmountDisbursed+ExpectedInterest),LoanBalance=(PrincipalBalance+InterestBalance);




-- DROP PROCEDURE IF EXISTS sortTheInstalment;
-- DELIMITER //
-- CREATE PROCEDURE sortTheInstalment() READS SQL DATA 

-- BEGIN

-- DECLARE lDone1 INTEGER DEFAULT 0;

-- DECLARE loanIdZ,numberOfDays,numberOfDays1,numberOfDays2,n,n1,n2,n3,runningInstalmentId,theIdNow INT; 
-- DECLARE originalDueDate,lastDate,nextDate,endDate DATE;

-- DECLARE forSelectingLoanIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore  WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed')  AND  NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') ;


--  DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1=1;
 

-- OPEN forSelectingLoanIds; 

-- LoanIdLoop: LOOP /* Loop through the due dates since the last duedate*/

-- FETCH forSelectingLoanIds into loanIdZ; /* Pick the loan id into the variable loanIdz */

-- IF lDone1=1 THEN /* check whether the cusor sitll holds more values and if not terminate loop */
-- SELECT loanIdZ;
-- LEAVE LoanIdLoop; /* */
--  END IF;  /* */


-- SELECT currentInstalmentNow(loanIdZ) INTO runningInstalmentId;

-- SELECT runningInstalmentId;
-- SELECT trn_id INTO theIdNow FROM new_loan_appstoreamort WHERE master1_id=loanIdZ AND instalment_no=runningInstalmentId;
-- SELECT InstalmentRemaining INTO @theRemainAmount FROM new_loan_appstoreamort WHERE trn_id = theIdNow;

-- IF @theRemainAmount<10 THEN
-- SELECT loanIdZ;
-- UPDATE new_loan_appstoreamort SET instalment_status = 'P',instalment_paid_date=DATE(NOW()),InstalmentRemaining=0.0 WHERE trn_id = theIdNow;
-- END IF;

-- SET lDone1=0;
-- END LOOP LoanIdLoop;

-- CLOSE forSelectingLoanIds;
-- END //
-- DELIMITER ;








-- DROP PROCEDURE IF EXISTS reculculateBalances;

-- DELIMITER ##

-- CREATE PROCEDURE reculculateBalances()     READS SQL DATA

-- OUTER_BLOCK: BEGIN
-- DECLARE theId,counter INTEGER;
-- DECLARE theLoanTxnId VARCHAR(20);
-- DECLARE outerNotFound, c INTEGER DEFAULT 0;
-- DECLARE totalPenaltyX,totalAccumlatedInterestX,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal DOUBLE;
-- DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from pmms_loans.new_loan_appstore;

-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

-- -- Balance on christmas party Processed

-- OPEN forLoanTxnId; 

-- LOANTXN_LOOP: LOOP 

-- FETCH forLoanTxnId into theLoanTxnId;


--  IF outerNotFound=1 THEN
-- LEAVE LOANTXN_LOOP;
--  END IF;
 
-- INNER_BLOCK: BEGIN
-- DECLARE AmountPaidX,PrincipalPaidX,InterestPaidX,AccumulatedInterestPaidX,LoanPenaltyPaidX,PrincipalBalanceX,InterestBalanceX,AccumulatedInterestBalanceX,LoanPenaltyBalanceX,LoanBalanceX,
-- AmountPaidXX,PrincipalPaidXX,InterestPaidXX,AccumulatedInterestPaidXX,LoanPenaltyPaidXX,PrincipalBalanceXX,InterestBalanceXX,AccumulatedInterestBalanceXX,LoanPenaltyBalanceXX,LoanBalanceXX
--  DOUBLE; 
-- DECLARE innerNotFound,theBatchNoS,TrnIdX INTEGER DEFAULT 0; 

-- DECLARE forBatchNos CURSOR FOR SELECT  TrnId FROM pmms.loandisburserepaystatement WHERE loanTrnId= theLoanTxnId;

-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;


-- SET counter=0;
--  SET  @PrincipalBalanceX=NULL,@InterestBalanceX=NULL,@AccumulatedInterestBalanceX=NULL,@LoanPenaltyBalanceX=NULL,@LoanBalanceX=NULL;

-- OPEN forBatchNos; 

-- TXNIDS_LOOP:LOOP

-- FETCH forBatchNos INTO theBatchNoS;

-- IF counter=0 THEN

-- SET @dueDateX1 = concat(CAST("SELECT AmountDisbursed,ExpectedInterest,AccumulatedInterestBalance,LoanPenaltyBalance,ExpectedTotalAmount INTO @PrincipalBalanceX,@InterestBalanceX,@AccumulatedInterestBalanceX,@LoanPenaltyBalanceX,@LoanBalanceX FROM pmms.loandisburserepaystatement WHERE TrnId=" AS CHAR CHARACTER SET utf8),theBatchNoS,CAST(" AND ExpectedTotalAmount>0" AS CHAR CHARACTER SET utf8));

--   PREPARE stmt21 FROM @dueDateX1;
--   EXECUTE stmt21;
-- DROP PREPARE stmt21;

-- END IF;


--  IF innerNotFound=1 THEN
-- LEAVE TXNIDS_LOOP;
--  END IF;



-- SET @dueDateX = concat(CAST("SELECT AmountPaid,PrincipalPaid,InterestPaid,AccumulatedInterestPaid,LoanPenaltyPaid INTO @AmountPaidX,@PrincipalPaidX,@InterestPaidX,@AccumulatedInterestPaidX,@LoanPenaltyPaidX FROM pmms.loandisburserepaystatement WHERE TrnId=" AS CHAR CHARACTER SET utf8),theBatchNoS);

--   PREPARE stmt2 FROM @dueDateX;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;



--  SELECT @AmountPaidX,@PrincipalPaidX,@InterestPaidX,@AccumulatedInterestPaidX,@LoanPenaltyPaidX ,theBatchNoS;



-- SELECT @PrincipalBalanceX,@InterestBalanceX,@AccumulatedInterestBalanceX,@LoanPenaltyBalanceX,@LoanBalanceX;

-- SET @PrincipalBalanceX=@PrincipalBalanceX-@PrincipalPaidX,@InterestBalanceX=@InterestBalanceX-@InterestPaidX,@AccumulatedInterestBalanceX=@AccumulatedInterestBalanceX-@AccumulatedInterestPaidX,@LoanPenaltyBalanceX=@LoanPenaltyBalanceX-@LoanPenaltyPaidX,@LoanBalanceX=@LoanBalanceX-@AmountPaidX;


-- IF ISNULL(@PrincipalBalanceX) THEN
-- SET @PrincipalBalanceX=0.0;
-- END IF;

-- IF ISNULL(@InterestBalanceX) THEN
-- SET @InterestBalanceX=0.0;
-- END IF;

-- IF ISNULL(@AccumulatedInterestBalanceX) THEN
-- SET @AccumulatedInterestBalanceX=0.0;
-- END IF;

-- IF ISNULL(@LoanPenaltyBalanceX) THEN
-- SET @LoanPenaltyBalanceX=0.0;
-- END IF;

-- IF ISNULL(@LoanBalanceX) THEN
-- SET @LoanBalanceX=0.0;
-- END IF;

-- SELECT @PrincipalBalanceX,@InterestBalanceX,@AccumulatedInterestBalanceX,@LoanPenaltyBalanceX,@LoanBalanceX,theBatchNoS;

-- UPDATE pmms.loandisburserepaystatement SET PrincipalBalance=@PrincipalBalanceX,InterestBalance=@InterestBalanceX,AccumulatedInterestBalance=@AccumulatedInterestBalanceX,LoanPenaltyBalance=@LoanPenaltyBalanceX,LoanBalance=@LoanBalanceX WHERE  TrnId=theBatchNoS;



-- SET @AmountPaidX=NULL,@PrincipalPaidX=NULL,@InterestPaidX=NULL,@AccumulatedInterestPaidX=NULL,@LoanPenaltyPaidX=NULL;
-- SET counter=counter+1;
-- SET innerNotFound=0;

-- END LOOP TXNIDS_LOOP;
--  CLOSE forBatchNos; 
-- END INNER_BLOCK;




-- SET outerNotFound=0;
--  END LOOP LOANTXN_LOOP;
-- CLOSE forLoanTxnId;
-- END OUTER_BLOCK ##
-- DELIMITER ;

--  CALL reculculateBalances() ;

-- 1	MembershipFees	Fees	05502000210	03309000110	Membership Fees from NABAKOOZA RITAH	6,000	0	0	0
-- 1	AnnualSubscriptionFees	Fees	05502000310	03316000110	Annual Subscription Fees from AKELLO MERCY	10,000	0	0	0


DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;
DELIMITER $$
CREATE PROCEDURE theAMDAPenaltyComputation() READS SQL DATA
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE v_trn_id VARCHAR(50);
  DECLARE v_instalment_due_date DATE;
  DECLARE v_instalment_remaining VARCHAR(50);
  DECLARE v_last_computed_date DATE;

  -- Declare the first cursor
  DECLARE cur CURSOR FOR
    SELECT trn_id
    FROM new_loan_appstore
    WHERE (loan_cycle_status = 'Disbursed' OR loan_cycle_status = 'Renewed')
    AND EXISTS (
      SELECT 1
      FROM loanarrearssettings
      WHERE penaltyStatus = 1
    );

  -- Handler for the first cursor
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur;

  read_loop: LOOP
    FETCH cur INTO v_trn_id;
    IF done THEN
      LEAVE read_loop;
    END IF;

    BEGIN
      DECLARE done_amort INT DEFAULT FALSE;

      -- Declare the second cursor
      DECLARE cur_amort CURSOR FOR
        SELECT a.instalment_due_date, a.InstalmentRemaining, b.last_computed_date
        FROM new_loan_appstoreamort a
        LEFT JOIN last_computed_dates b ON a.master1_id = b.trn_id AND a.instalment_due_date = b.instalment_due_date
        WHERE a.master1_id = v_trn_id
        AND a.instalment_status != 'P';

      -- Handler for the second cursor
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_amort = TRUE;

      OPEN cur_amort;

      amort_loop: LOOP
        FETCH cur_amort INTO v_instalment_due_date, v_instalment_remaining, v_last_computed_date;
        IF done_amort THEN
          LEAVE amort_loop;
        END IF;

        -- Check if 15 days have passed since the last computed date or instalment due date (whichever is later)
        IF DATEDIFF(CURDATE(), IFNULL(v_last_computed_date, v_instalment_due_date)) >= 15 THEN
          -- Update new_loan_appstoreamort fields based on the computed penalty
          UPDATE new_loan_appstoreamort
          SET 
            LoanPenaltyRemaining = LoanPenaltyRemaining + 0.05 * v_instalment_remaining,
            InstalmentRemaining = InstalmentRemaining * 1.05,
            instalment_amount = instalment_amount * 1.05
          WHERE master1_id = v_trn_id
          AND instalment_due_date = v_instalment_due_date
          AND instalment_status != 'P';

          -- Update new_loan_appstore fields based on the computed penalty
          UPDATE new_loan_appstore
          SET
            balance_due = balance_due + 0.05 * v_instalment_remaining,
            instalment_amount = instalment_amount * 1.05,
            TotalLoanPenaltyRemaining = TotalLoanPenaltyRemaining + 0.05 * v_instalment_remaining
          WHERE trn_id = v_trn_id;

          -- Update or insert the last computed date
          INSERT INTO last_computed_dates (trn_id, instalment_due_date, last_computed_date)
          VALUES (v_trn_id, v_instalment_due_date, CURDATE())
          ON DUPLICATE KEY UPDATE
          last_computed_date = CURDATE();
        END IF;
      END
  LOOP amort_loop;

  CLOSE cur_amort;
END; -- End of inner BEGIN block for the second cursor
END LOOP read_loop;

CLOSE cur;
END$$
DELIMITER ;
           




DROP PROCEDURE IF EXISTS ProcessingFeesCollected;
DELIMITER //
CREATE PROCEDURE ProcessingFeesCollected(IN theDate DATE,OUT processingFees VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO processingFees FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03315%';
IF ISNULL(processingFees) THEN
SET processingFees=0.0;
END IF;

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;



OPEN cursor_forSelectingProcessingFeesAccounts; 

ACCOUNTS_LOOP: LOOP 

FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

 IF outer_done=1 THEN
LEAVE ACCOUNTS_LOOP;
 END IF;
 


INNER_BLOCK: BEGIN

DECLARE batchNumbersReps VARCHAR(30); 
DECLARE inner_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;





OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;


 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;





SET inner_done=0;
END LOOP BATCH_LOOP; 
CLOSE cursor_forSelectingBatchNumbers; 
END INNER_BLOCK;


SET outer_done=0;
 END LOOP ACCOUNTS_LOOP;
CLOSE cursor_forSelectingProcessingFeesAccounts;

END OUTER_BLOCK//


        END //

        DELIMITER ;





-- DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;
-- DELIMITER //
-- CREATE PROCEDURE theAMDAPenaltyComputation() READS SQL DATA 

-- BEGIN

-- DECLARE lDone1 INTEGER DEFAULT 0;

-- DECLARE loanIdZ,numberOfDays,numberOfDays1,numberOfDays2,n,n1,n2,n3,nx INT; 
-- DECLARE originalDueDate,lastDate,nextDate,endDate DATE;

-- DECLARE forSelectingLoanIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore  WHERE loan_cycle_status='Disbursed'  AND  NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') ;


--  DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1=1;
 

-- OPEN forSelectingLoanIds; 

-- LoanIdLoop: LOOP /* Loop through the due dates since the last duedate*/

-- FETCH forSelectingLoanIds into loanIdZ; /* Pick the loan id into the variable loanIdz */
-- SET nx=8;
-- IF lDone1=1 THEN /* check whether the cusor sitll holds more values and if not terminate loop */
-- -- SELECT loanIdZ;
-- LEAVE LoanIdLoop; /* */
--  END IF;  /* */

-- SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=loanIdZ;

-- -- SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE instalment_due_date<DATE(NOW()) AND NOT instalment_status='P' AND  master1_id=loanIdZ ORDER BY instalment_due_date ASC LIMIT 1;/* The instalment due date is the last due date*/

-- SET @theLoansNowX = concat(CAST("SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE instalment_due_date<='" AS CHAR CHARACTER SET utf8),DATE(NOW()), CAST("' AND  master1_id=" AS CHAR CHARACTER SET utf8),loanIdZ,  CAST(" AND NOT instalment_status='P' ORDER BY trn_id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));
-- -- IF loanIdZ=70387 THEN
-- --  select  @theLoansNowX;
-- -- END IF;
--   PREPARE stmt22X FROM @theLoansNowX;
--   EXECUTE stmt22X;
-- DROP PREPARE stmt22X;


-- IF ISNULL(@originalDueDate) THEN 

-- SET @originalDueDate=DATE(NOW());
-- END IF;

-- SELECT DATEDIFF(DATE(NOW()),@originalDueDate) INTO numberOfDays;
-- -- IF loanIdZ=70387 THEN

-- -- SELECT @originalDueDate,numberOfDays,loanIdZ;
-- -- END IF;
-- IF endDate>DATE(NOW()) THEN /* IF 0*/
-- -- SELECT 'Yes';
-- IF numberOfDays>=nx THEN /* IF 1*/
-- -- SELECT 'Yes2';
-- -- IF loanIdZ=70387 THEN
-- -- SELECT numberOfDays,loanIdZ,@originalDueDate;
-- -- END IF;
-- IF EXISTS (SELECT * FROM amdaPenaltyComputeNow WHERE loanTrnId = loanIdZ) THEN   /* IF 2*/
-- -- SELECT 'Yes3';
-- SELECT lastAccrued INTO lastDate FROM amdaPenaltyComputeNow WHERE loanTrnId = loanIdZ ORDER BY id DESC LIMIT 1;


-- IF lastDate<@originalDueDate THEN   /* IF 3*/

-- SET n=TRUNCATE((numberOfDays/nx),0);

-- SET nextDate=@originalDueDate;

-- REPEAT 
-- -- SELECT loanIdZ,"1";
-- CALL computeAndApplyTheAMDAPenalty(loanIdZ);
-- SET n=n-1;
-- SET nextDate=DATE_ADD(nextDate,INTERVAL nx DAY);
-- UNTIL n=0 END REPEAT;
-- INSERT INTO amdaPenaltyComputeNow VALUES(NULL,loanIdZ,1,nextDate);

-- ELSE /* IF 3*/
-- SELECT DATEDIFF(DATE(NOW()),lastDate) INTO numberOfDays1;
-- IF numberOfDays1>=nx THEN /* IF 5*/
-- SET n1=TRUNCATE((numberOfDays1/7),0);
-- SET nextDate=lastDate;
-- REPEAT 
-- -- SELECT loanIdZ,"2";
-- CALL computeAndApplyTheAMDAPenalty(loanIdZ);
-- SET n1=n1-1;
-- SET nextDate=DATE_ADD(nextDate,INTERVAL nx DAY);
-- UNTIL n1=0 END REPEAT;
-- INSERT INTO amdaPenaltyComputeNow VALUES(NULL,loanIdZ,1,nextDate);
-- END IF;/* IF 5*/
-- END IF; /* IF 3*/

-- ELSE   /* IF 2*/
-- -- SELECT 'Yes4';
-- IF numberOfDays=5 THEN /* IF 4*/
-- -- SELECT loanIdZ,"3";
-- CALL computeAndApplyTheAMDAPenalty(loanIdZ);
-- SET nextDate=DATE_ADD(@originalDueDate,INTERVAL nx DAY);
-- INSERT INTO amdaPenaltyComputeNow VALUES(NULL,loanIdZ,1,nextDate);
-- ELSE /* IF 4*/
-- SELECT DATEDIFF(DATE(NOW()),@originalDueDate) INTO numberOfDays2;
-- -- SELECT numberOfDays2;
-- SET n2=TRUNCATE((numberOfDays2/nx),0);
-- SET nextDate=@originalDueDate;
-- REPEAT 
-- -- SELECT n2,nextDate,loanIdZ;
-- -- SELECT loanIdZ,"4";
-- CALL computeAndApplyTheAMDAPenalty(loanIdZ);
-- SET n2=n2-1;
-- SET nextDate=DATE_ADD(nextDate,INTERVAL nx DAY);
-- UNTIL n2=0 END REPEAT;
-- INSERT INTO amdaPenaltyComputeNow VALUES(NULL,loanIdZ,1,nextDate);
-- END IF; /* IF 4*/



-- END IF;  /* IF 2*/



-- END IF;/* IF 1*/

-- END IF;/* IF 0*/
-- SET @originalDueDate=NULL;
-- SET lDone1=0;
-- END LOOP LoanIdLoop;

-- CLOSE forSelectingLoanIds;
-- END //
-- DELIMITER ;








-- --  DROP TABLE IF EXISTS amdaPenaltyComputeNow;
-- --   DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails;
-- --    DROP TABLE IF EXISTS amdaPenaltyComputeInstalmentNow; 

-- CREATE TABLE amdaPenaltyComputeNow (
--    id INT(11) NOT NULL AUTO_INCREMENT,
--    loanTrnId INT(11) NOT NULL,
--   penaltyStatus INT(11) NOT NULL, 
--   lastAccrued DATE,
--   PRIMARY KEY(id)
-- )

-- ENGINE=innoDB AUTO_INCREMENT=30 DEFAULT CHARACTER SET=utf8;

--   /* DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails; */
-- CREATE TABLE amdaPenaltyComputeNowDetails (
--    id INT(11) NOT NULL AUTO_INCREMENT,
--    loanTrnId VARCHAR(20),
--    instalmentNo INT(11) NOT NULL, 
--   instalmentComputeStatus INT(11)  NULL, -- 1=ONGOING,2=STOPPED
--   loanComputeStatus INT(11)  NULL,  -- 1=ONGOING,2=STOPPED
--   dateComputed DATE, 
--   PRIMARY KEY(id)
-- )

-- ENGINE=innoDB AUTO_INCREMENT=20 DEFAULT CHARACTER SET=utf8;



-- CREATE TABLE amdaPenaltyComputeInstalmentNow (
--    id INT(11) NOT NULL AUTO_INCREMENT,
--    strn_id VARCHAR(10),
--   instalmentNo INT(11) NOT NULL, 
--   lastAccrued DATE, --  DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
--   PRIMARY KEY(id)
-- )

-- ENGINE=innoDB AUTO_INCREMENT=2 DEFAULT CHARACTER SET=utf8;








-- DROP PROCEDURE IF EXISTS computeAndApplyTheAMDAPenalty;

-- DELIMITER ##

-- CREATE PROCEDURE computeAndApplyTheAMDAPenalty(IN trnId INT) 


-- BEGIN

-- DECLARE thePenalty,theBalance,thePenaltyActual  DOUBLE;

-- SET thePenaltyActual=10000;
-- SET @totalLoanAmount=NULL,@balanceCdue=NULL,@totalLoanPenalty=NULL,@instalmentAmount=NULL,@instalmentRemaining=NULL,@loanPenalty=NULL,@loanPenaltyRemaining=NULL,@totalLoanAmount=NULL,@NewExpectedTotalAmount=NULL,@penaltyBal=NULL,@lBalance=NULL;
-- SET @theTotalLoan = CONCAT(CAST("SELECT total_loanAmount, balance_due,TotalLoanPenaltyRemaining INTO @totalLoanAmount, @balanceCdue,@totalLoanPenalty FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),trnId);
-- --  select  @theTotalLoan;
--   PREPARE stmt2 FROM @theTotalLoan;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;

-- SET @theTotalLoanAmort = CONCAT(CAST("SELECT trn_id, instalment_amount,InstalmentRemaining, LoanPenalty,LoanPenaltyRemaining INTO @theIdNow, @instalmentAmount, @instalmentRemaining,@loanPenalty,@loanPenaltyRemaining FROM new_loan_appstoreamort  WHERE instalment_due_date<DATE(NOW()) AND NOT instalment_status='P' AND  master1_id=" AS CHAR CHARACTER SET utf8),trnId,CAST(" ORDER BY instalment_due_date ASC LIMIT 1" AS CHAR CHARACTER SET utf8));
-- --  select  @theTotalLoanAmort;
--   PREPARE stmt23 FROM @theTotalLoanAmort;
--   EXECUTE stmt23;
-- DROP PREPARE stmt23;

-- SET @theLoansNow = concat(CAST("SELECT TrnId,ExpectedInterest,ExpectedTotalAmount,LoanPenaltyBalance, LoanBalance INTO @idL,@ExpectedInterest,@ExpectedTotalAmount, @penaltyBal,@lBalance FROM pmms.loandisburserepaystatement WHERE LoanTrnId='" AS CHAR CHARACTER SET utf8),trnId, CAST("' AND (LoanStatusReport='Disbursed' OR LoanStatusReport='Renewed') ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
-- --  select  @theLoansNow;
--   PREPARE stmt22 FROM @theLoansNow;
--   EXECUTE stmt22;
-- DROP PREPARE stmt22;


-- -- SELECT trnId, @totalLoanAmount, @balanceCdue,@totalLoanPenalty, @instalmentAmount, @instalmentRemaining,@loanPenalty,@loanPenaltyRemaining,@theCPrincipalBalance,@theCInterestBalance,@theCAccumulatedInterestBalance,@theCLoanPenaltyBalance,@theCLoanBalance;

-- IF ISNULL(@totalLoanAmount) THEN
-- SET @totalLoanAmount=0.0;
-- END IF;

-- IF ISNULL(@balanceCdue) THEN
-- SET @balanceCduet=0.0;
-- END IF;

-- IF ISNULL(@totalLoanPenalty) THEN
-- SET @totalLoanPenalty=0.0;
-- END IF;


-- IF ISNULL(@instalmentAmount) THEN
-- SET @instalmentAmount=0.0;
-- END IF;

-- IF ISNULL(@instalmentRemaining) THEN
-- SET @instalmentRemaining=0.0;
-- END IF;

-- IF ISNULL(@loanPenalty) THEN
-- SET @loanPenalty=0.0;
-- END IF;

-- IF ISNULL(@loanPenaltyRemaining) THEN
-- SET @loanPenaltyRemaining =0.0;
-- END IF;

-- IF ISNULL(@ExpectedInterest) THEN
-- SET @ExpectedInterest =0.0;
-- END IF;
-- IF ISNULL(@ExpectedTotalAmount) THEN
-- SET @ExpectedTotalAmount =0.0;
-- END IF;
-- IF ISNULL(@penaltyBal) THEN
-- SET @penaltyBal =0.0;
-- END IF;
-- IF ISNULL(@lBalance) THEN
-- SET @lBalance =0.0;
-- END IF;


-- SET @totalLoanAmount=@totalLoanAmount+thePenaltyActual,@balanceCdue=@balanceCdue+thePenaltyActual,@totalLoanPenalty=@totalLoanPenalty+thePenaltyActual,@instalmentAmount=@instalmentAmount+thePenaltyActual,@instalmentRemaining=@instalmentRemaining+thePenaltyActual,@loanPenalty=@loanPenalty+thePenaltyActual,@loanPenaltyRemaining=@loanPenaltyRemaining+thePenaltyActual,@totalLoanAmount=@totalLoanAmount+thePenaltyActual,@NewExpectedTotalAmount=@ExpectedTotalAmount+thePenaltyActual,@penaltyBal=@penaltyBal+thePenaltyActual,@lBalance=@lBalance+thePenaltyActual;
-- -- IF trnId=70387 THEN
-- -- SELECT @balanceCdue,@totalLoanAmount,@totalLoanPenalty,trnId;
-- -- END IF;

-- UPDATE new_loan_appstore SET balance_due=@balanceCdue,total_loanAmount=@totalLoanAmount,TotalLoanPenaltyRemaining=@totalLoanPenalty WHERE trn_id=trnId;


-- UPDATE new_loan_appstore1 SET balance_due=@balanceCdue,total_loanAmount=@totalLoanAmount,TotalLoanPenaltyRemaining=@totalLoanPenalty WHERE trn_id=trnId;


-- -- IF trnId=70387 THEN
-- -- SELECT @instalmentAmount,@instalmentRemaining,@loanPenalty,@loanPenaltyRemaining,@theId,trnId;
-- -- END IF;

-- -- UPDATE new_loan_appstoreamort   SET 
--   -- instalment_amount=@instalmentAmount,
-- --  InstalmentRemaining=@instalmentRemaining,
--   -- LoanPenalty=@loanPenalty,
-- -- LoanPenaltyRemaining=  @loanPenaltyRemaining  WHERE trn_id=@theId;

-- SET @sql_te = CONCAT(CAST(" UPDATE new_loan_appstoreamort SET 
-- instalment_amount= " AS CHAR CHARACTER SET utf8),@instalmentAmount,
-- CAST(",InstalmentRemaining= " AS CHAR CHARACTER SET utf8),@instalmentRemaining,
-- CAST(",LoanPenalty= " AS CHAR CHARACTER SET utf8),@loanPenalty,
-- CAST(",LoanPenaltyRemaining= " AS CHAR CHARACTER SET utf8),@loanPenaltyRemaining,
-- CAST(" WHERE trn_id= " AS CHAR CHARACTER SET utf8),@theIdNow);

-- -- IF trnId=70387 THEN
-- -- SELECT @sql_te ;
-- -- END IF;

--   PREPARE stmt FROM @sql_te;
--   EXECUTE stmt;
-- DROP PREPARE stmt;



-- IF @ExpectedTotalAmount>0.0 THEN


-- SET @sql_textXA1 = CONCAT(CAST(" UPDATE pmms.loandisburserepaystatement SET 
-- LoanPenaltyBalance= " AS CHAR CHARACTER SET utf8),@penaltyBal,
-- CAST(",ExpectedTotalAmount= " AS CHAR CHARACTER SET utf8),@NewExpectedTotalAmount,
-- CAST(",LoanBalance= " AS CHAR CHARACTER SET utf8),@lBalance,
-- CAST(" WHERE TrnId= " AS CHAR CHARACTER SET utf8),@idL);
-- -- SELECT @sql_textXA1 ;
--   PREPARE stmtXA1 FROM @sql_textXA1;
--   EXECUTE stmtXA1;
-- DROP PREPARE stmtXA1;
-- END IF;
 
-- --  SELECT @penaltyBal,@lBalance,@idL;
-- SET @sql_textXA = CONCAT(CAST(" UPDATE pmms.loandisburserepaystatement SET 
-- LoanPenaltyBalance= " AS CHAR CHARACTER SET utf8),@penaltyBal,
-- CAST(",LoanBalance= " AS CHAR CHARACTER SET utf8),@lBalance,
-- CAST(" WHERE TrnId= " AS CHAR CHARACTER SET utf8),@idL);
-- -- SELECT @sql_textXA ;
--   PREPARE stmtXA FROM @sql_textXA;
--   EXECUTE stmtXA;
-- DROP PREPARE stmtXA;



-- END ##
-- DELIMITER ;


-- -- CALL theAMDAPenaltyComputation();


