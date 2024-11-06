

DROP PROCEDURE IF EXISTS RepayTheLoanNow;
DELIMITER //
CREATE PROCEDURE RepayTheLoanNow(
  IN accountNumber VARCHAR(60),
  IN AmountPaid DOUBLE,
  IN batchNumber VARCHAR(30),
  IN userId INT,
  IN instalmentPaidDate DATE,
  IN loanOfficerId INT) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId,theLoanTxnId,theLoanId,remainingCinstalments,cycles,completionStatus INT;
DECLARE loanCycleStatus,theExistingLoanId VARCHAR(60);
DECLARE amountTxed,currentIntestX,totalInsterestX, currentPenaltyX,totalPenaltyX,currentAccumulatedInterestX,totalAccumulatedInterestX,currentPrincipalX,totalPrincipalX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,balanceCdue,balanceCdue2,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid,amountRemain,amountRemain2,amountDiff DOUBLE;

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   ROLLBACK;
  
-- END;

-- START TRANSACTION;
SET amountTxed=AmountPaid;

-- SELECT accountNumber;
SELECT theLoanTxnId(CONCAT("newloan",accountNumber)) INTO theLoanId;
-- SELECT theLoanId;

IF EXISTS(SELECT * FROM  new_loan_appstoreamort WHERE master1_id=theLoanId AND NOT instalment_status='P') THEN

-- SELECT loan_cycle_status INTO loanCycleStatus from new_loan_appstore where trn_id=theLoanId;
SELECT balance_due,loan_cycle_status INTO amountRemain2,loanCycleStatus from new_loan_appstore where trn_id=theLoanId;
SELECT SUM(InstalmentRemaining) INTO amountRemain from new_loan_appstoreamort WHERE master1_id=theLoanId AND NOT instalment_status='P';

-- SELECT amountRemain;
SET amountDiff=AmountPaid-amountRemain;

IF amountDiff>0 THEN
SET amountTxed=AmountPaid-amountDiff;
END IF;

label1:REPEAT
-- SELECT theLoanId;
SELECT currentInstalmentNow(theLoanId) INTO runningInstalmentId;
-- SELECT runningInstalmentId;

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
-- SELECT amountTxed;
IF currentIntestX>=amountTxed THEN
SET currentIntestX=amountTxed;
END IF;

-- SELECT runningInstalmentId;
-- SELECT runningInstalmentId,currentIntestX,theLoanId,instalmentPaidDate;
CALL updateTheInterestComponent(runningInstalmentId,currentIntestX,theLoanId,instalmentPaidDate);

SET totalInsterestX=totalInsterestX+currentIntestX;

SET amountTxed=amountTxed-currentIntestX;

IF amountTxed<9 THEN
LEAVE label1;
END IF;

END IF;



 SELECT PrincipalRemaining INTO currentPrincipalX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;


--  SELECT currentPrincipalX; 

IF ISNULL(currentPrincipalX) THEN
SET currentPrincipalX=0;
END IF;
 
IF currentPrincipalX>0 THEN
-- SELECT currentPrincipalX;
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
IF amountTxed<9 THEN
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

IF amountTxed<2 THEN
LEAVE label1;
END IF;

END IF;

-- 07/10/2022	07/10/2022	Regular Savings for Fuel1s Savings Processed on 07/10/2022
  -- From Fuel1	-	20000.0	96000.0	BTN88666

 SELECT AccumulatedInterestRemaining INTO currentAccumulatedInterestX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

IF ISNULL(currentAccumulatedInterestX) THEN
SET currentAccumulatedInterestX=0;
END IF;


IF currentAccumulatedInterestX>0 THEN
-- SELECT currentAccumulatedInterestX,amountTxed;
IF currentAccumulatedInterestX>=amountTxed THEN
SET currentAccumulatedInterestX=amountTxed;
END IF;

CALL updateTheAccumulatedInterestComponent(runningInstalmentId,currentAccumulatedInterestX,theLoanId,instalmentPaidDate);
IF ISNULL(totalAccumulatedInterestX) THEN
SET totalAccumulatedInterestX=0;
END IF;
SET totalAccumulatedInterestX=totalAccumulatedInterestX+currentAccumulatedInterestX;
SET amountTxed=amountTxed-currentAccumulatedInterestX;

IF amountTxed<2 THEN
LEAVE label1;
END IF;

END IF;



UNTIL amountTxed<=9  END REPEAT label1;
ELSE
SET balanceCdue=0,@balanceCdue=0,AmountPaid=0;

END IF;

SET @theTotalLoan = concat(CAST("SELECT balance_due,instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid INTO @balanceCdue,@instalmentsCpaid,@TotalInterestCPaid,@TotalInterestCRemaining,@TotalPrincipalCPaid,@TotalPrincipalCRemaining,@TotalAccumulatedInterestCPaid,@TotalAccumulatedInterestCRemaining,@TotalLoanPenaltyCPaid,@TotalLoanPenaltyCRemaining,@TotalAccruedInterestCRemaining,@TotalAccruedInterestCPaid FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @theTotalLoan;
  PREPARE stmt2 FROM @theTotalLoan;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SET @theTotalLoan = concat(CAST("SELECT instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid INTO @instalmentsCpaid,@TotalInterestCPaid,@TotalInterestCRemaining,@TotalPrincipalCPaid,@TotalPrincipalCRemaining,@TotalAccumulatedInterestCPaid,@TotalAccumulatedInterestCRemaining,@TotalLoanPenaltyCPaid,@TotalLoanPenaltyCRemaining,@TotalAccruedInterestCRemaining,@TotalAccruedInterestCPaid FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
-- --  select  @theTotalLoan;
--   PREPARE stmt2 FROM @theTotalLoan;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;



-- SET @theTotalLoan2 = concat(CAST("SELECT SUM(InstalmentRemaining) INTO @balanceCdue from new_loan_appstoreamort WHERE master1_id=" AS CHAR CHARACTER SET utf8),theLoanId,CAST(" AND NOT instalment_status='P'" AS CHAR CHARACTER SET utf8));
-- --  select  @theTotalLoan;
--   PREPARE stmt2 FROM @theTotalLoan2;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;


-- SELECT balanceCdue,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid;




IF ISNULL(balanceCdue) THEN
SET balanceCdue=0.0;
END IF;


IF ISNULL(instalmentsCpaid) THEN
SET instalmentsCpaid=0.0;
END IF;


IF ISNULL(TotalInterestCPaid) THEN
SET TotalInterestCPaid=0.0;
END IF;

IF ISNULL(TotalInterestCRemaining) THEN
SET TotalInterestCRemaining=0.0;
END IF;


IF ISNULL(TotalPrincipalCPaid) THEN
SET TotalPrincipalCPaid=0.0;
END IF;


IF ISNULL(TotalPrincipalCRemaining) THEN
SET TotalPrincipalCRemaining=0.0;
END IF;


IF ISNULL(TotalAccumulatedInterestCRemaining) THEN
SET TotalAccumulatedInterestCRemaining=0.0;
END IF;

IF ISNULL(TotalLoanPenaltyCPaid) THEN
SET TotalLoanPenaltyCPaid=0.0;
END IF;

IF ISNULL(TotalLoanPenaltyCRemaining) THEN
SET TotalLoanPenaltyCRemaining=0.0;
END IF;


IF ISNULL(totalPrincipalX) THEN
SET totalPrincipalX=0.0;
END IF;


IF ISNULL(totalInsterestX) THEN
SET totalInsterestX=0.0;
END IF;


IF ISNULL(totalAccumulatedInterestX) THEN
SET totalAccumulatedInterestX=0.0;
END IF;

IF ISNULL(totalPenaltyX) THEN
SET totalPenaltyX=0.0;
END IF;


-- SELECT totalPrincipalX;
SET balanceCdue=@balanceCdue-AmountPaid,instalmentsCpaid=@instalmentsCpaid+AmountPaid,TotalInterestCPaid=@TotalInterestCPaid+totalInsterestX,TotalInterestCRemaining=@TotalInterestCRemaining-totalInsterestX,TotalPrincipalCPaid=@TotalPrincipalCPaid+totalPrincipalX,TotalPrincipalCRemaining=@TotalPrincipalCRemaining-totalPrincipalX,TotalAccumulatedInterestCPaid=@TotalAccumulatedInterestCPaid+totalAccumulatedInterestX,TotalAccumulatedInterestCRemaining=@TotalAccumulatedInterestCRemaining-totalAccumulatedInterestX,TotalLoanPenaltyCPaid=@TotalLoanPenaltyCPaid+totalPenaltyX,TotalLoanPenaltyCRemaining=@TotalLoanPenaltyCRemaining-totalPenaltyX;

-- SELECT totalPrincipalX;
SET balanceCdue=@balanceCdue-AmountPaid,instalmentsCpaid=@instalmentsCpaid+AmountPaid,TotalInterestCPaid=@TotalInterestCPaid+totalInsterestX,TotalInterestCRemaining=@TotalInterestCRemaining-totalInsterestX,TotalPrincipalCPaid=@TotalPrincipalCPaid+totalPrincipalX,TotalPrincipalCRemaining=@TotalPrincipalCRemaining-totalPrincipalX,TotalAccumulatedInterestCPaid=@TotalAccumulatedInterestCPaid+totalAccumulatedInterestX,TotalAccumulatedInterestCRemaining=@TotalAccumulatedInterestCRemaining-totalAccumulatedInterestX,TotalLoanPenaltyCPaid=@TotalLoanPenaltyCPaid+totalPenaltyX,TotalLoanPenaltyCRemaining=@TotalLoanPenaltyCRemaining-totalPenaltyX;



-- SELECT balanceCdue,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid;


UPDATE new_loan_appstore SET balance_due=balanceCdue,instalments_paid=instalmentsCpaid,TotalInterestPaid=TotalInterestCPaid,TotalInterestRemaining=TotalInterestCRemaining,TotalPrincipalPaid=TotalPrincipalCPaid,TotalPrincipalRemaining=TotalPrincipalCRemaining,TotalAccumulatedInterestPaid=TotalAccumulatedInterestCPaid,TotalAccumulatedInterestRemaining=TotalAccumulatedInterestCRemaining,TotalLoanPenaltyPaid=TotalLoanPenaltyCPaid,TotalLoanPenaltyRemaining=TotalLoanPenaltyCRemaining WHERE trn_id=theLoanId;


UPDATE new_loan_appstore1 SET balance_due=balanceCdue,instalments_paid=instalmentsCpaid,TotalInterestPaid=TotalInterestCPaid,TotalInterestRemaining=TotalInterestCRemaining,TotalPrincipalPaid=TotalPrincipalCPaid,TotalPrincipalRemaining=TotalPrincipalCRemaining,TotalAccumulatedInterestPaid=TotalAccumulatedInterestCPaid,TotalAccumulatedInterestRemaining=TotalAccumulatedInterestCRemaining,TotalLoanPenaltyPaid=TotalLoanPenaltyCPaid,TotalLoanPenaltyRemaining=TotalLoanPenaltyCRemaining WHERE trn_id=theLoanId;

SET @theLoansNow = concat(CAST("SELECT PrincipalBalance,InterestBalance,AccumulatedInterestBalance,LoanPenaltyBalance,LoanBalance INTO @theCPrincipalBalance,@theCInterestBalance,@theCAccumulatedInterestBalance,@theCLoanPenaltyBalance,@theCLoanBalance FROM pmms.loandisburserepaystatement WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),theLoanId,CAST(" ORDER BY trnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
--  select  @theLoansNow;
  PREPARE stmt2 FROM @theLoansNow;
  EXECUTE stmt2;
DROP PREPARE stmt2;


-- SELECT PrincipalBalance,InterestBalance,AccumulatedInterestBalance,LoanPenaltyBalance,LoanBalance INTO theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance FROM pmms.loandisburserepaystatement WHERE loanTrnId=theLoanId ORDER BY trnId DESC LIMIT 1;

SET theCPrincipalBalance=@theCPrincipalBalance-totalPrincipalX,theCInterestBalance=@theCInterestBalance-totalInsterestX,theCAccumulatedInterestBalance=@theCAccumulatedInterestBalance-totalAccumulatedInterestX,theCLoanPenaltyBalance=@theCLoanPenaltyBalance-totalPenaltyX,theCLoanBalance=@theCLoanBalance-AmountPaid;

-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;

INSERT INTO pmms.loandisburserepaystatement VALUES(NULL,instalmentPaidDate,MONTH(instalmentPaidDate),YEAR(instalmentPaidDate),theLoanId,CONCAT("newloan",accountNumber),accountNumber,batchNumber,0.0,0.0,0.0,0.0,AmountPaid,totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,loanCycleStatus,userId,loanOfficerId,'NA','NA');

-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;
-- SELECT ;
IF ISNULL(balanceCdue) THEN
SET balanceCdue=0;
END IF;

IF balanceCdue<=9 THEN

SELECT loan_id INTO theExistingLoanId FROM new_loan_appstore where applicant_account_number=accountNumber AND loan_cycle_status='Completed' ORDER BY trn_id DESC LIMIT 1;

IF ISNULL(theExistingLoanId) THEN
SET cycles=1;
ELSEIF CHAR_LENGTH(theExistingLoanId)=22 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 1) INTO cycles;


ELSEIF CHAR_LENGTH(theExistingLoanId)=23 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 2) INTO cycles;

ELSEIF CHAR_LENGTH(theExistingLoanId)=24 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 3) INTO cycles;

END IF;

SET @closedAccount=CONCAT('closedloan',cycles+1,accountNumber);


IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',cycles+10,accountNumber);
END IF;

IF EXISTS(SELECT * FROM new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',cycles+10,accountNumber);
END IF;



IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount) THEN

SELECT SUBSTRING(@closedAccount, 11, 2) INTO cycles;
-- SELECT cycles;
SET @closedAccount=CONCAT('closedloan',133,accountNumber);
END IF;

IF EXISTS(SELECT * FROM new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SELECT SUBSTRING(@closedAccount, 11, 2) INTO cycles;
-- SELECT cycles;
SET @closedAccount=CONCAT('closedloan',133,accountNumber);
END IF;


UPDATE new_loan_appstore SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstore1 SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstoreamort SET instalment_status='P',master2_id=@closedAccount,instalment_paid_date=instalmentPaidDate WHERE master1_id=theLoanId;

UPDATE interestcomputed SET loanId=@closedAccount WHERE loanId=CONCAT("newloan",accountNumber);


UPDATE pmms.loandisburserepaystatement SET LoanStatusReport='Completed',LoanId=@closedAccount WHERE loanTrnId=theLoanId;

SET completionStatus=2;

END IF;

IF ISNULL(completionStatus) THEN
SET completionStatus=1;
END IF;

IF amountDiff>0 THEN
SET totalAccumulatedInterestX=totalAccumulatedInterestX+amountDiff;

CALL updateTheAccumulatedInterestComponentSpecial(runningInstalmentId,amountDiff,theLoanId,instalmentPaidDate);
END IF;


-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;

SELECT totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,completionStatus,loanCycleStatus,theLoanId;

-- COMMIT;

END//

 DELIMITER ;

