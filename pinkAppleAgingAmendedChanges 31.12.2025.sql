-- 05/02/2023	01121000210	Airtel Money 0709800476	Mobile Money	140000.0	Active
-- 05/06/2022	05/06/2022	LWANGA IBRAH 0759775579s Account Deposit for Loan Payment
  -- Dated 05/06/2022	159331.0	-	921531.0
-- 1	LoanPyt	P&I	05502019310	01128000110	NAKASAGGA ASIA 0759547742's Loan Payment	30,000	0	0	08/05/2024
-- 70624	TUMWINE ROGDERS 0757537197	BODA BODA	240	200000	0	0	0	0	30 DAYS	25/09/2022	10006	Completed
-- -----------------------------------------------------
-- createdRenewedLoan

-- -----------------------------------------------------
-- Credits	Turyamureba Vincent		Customer Deposits	1660150.0
DROP PROCEDURE IF EXISTS createdRenewedLoan;

DELIMITER $$

CREATE PROCEDURE createdRenewedLoan(
  IN accountNumber VARCHAR(60),
  IN amount DOUBLE,
  IN rate DOUBLE,
  IN tenure DOUBLE,
  IN theLastTransactionDate DATE,
  IN periodTypeNumber DOUBLE,
  IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN initialDisburseDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT,IN renewals INT,IN theDisId INT) BEGIN
DECLARE theLoanTxnId,timeP,timeP1 INT;
DECLARE theLoanId VARCHAR(60);
   DECLARE previousLoanTrnId INT;

    DECLARE v_account_num VARCHAR(60) DEFAULT accountNumber;
    DECLARE v_active_g_id INT DEFAULT NULL;
    DECLARE v_arch_g_archive_id INT DEFAULT NULL;
    DECLARE v_new_guarantor_id INT DEFAULT NULL;
    DECLARE v_carried_from VARCHAR(10) DEFAULT NULL; -- 'ACTIVE' or 'ARCHIVE'
--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;F
  -- Debits	Airtel Money(1221109)	01121000310	Mobile Money	2.43012E7
-- END;
 
START TRANSACTION;



SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;

SELECT trn_id INTO previousLoanTrnId from new_loan_appstore where applicant_account_number=accountNumber ORDER BY trn_id DESC LIMIT 1;

CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,initialDisburseDate,thePeriodSet,theCompuM,@totalInterestComputed);

SET @period= thePeriod(periodTypeNumber),timeP=1,timeP1=2;


IF periodTypeNumber=3 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


IF periodTypeNumber=6 THEN
SET timeP=6,timeP1=12,tenure=(tenure*6);
END IF;

IF periodTypeNumber=8 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


SET @startDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @startDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @nextDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @endDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),tenure,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @endDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @endDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT @startDate,@nextDate,@endDate;
-- OTHER CHANGES TO NOTE:
-- OtherGroups2=Business Id
-- OtherGroups3=Number of Renewals
-- OtherGroups4=BatchNumber
-- authoriser_id=DisbursedLoanId

 CALL accountNma(accountNumber,@accountName);

INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,DATE(NOW()),theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),initialDisburseDate,@startDate,@endDate,rate,@accountName,'Renewed',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theDisId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,renewals+1,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');

INSERT INTO loanprocessingstore VALUES(NULL,theLastTransactionDate,amount,@totalInterestComputed,@nextDate,@endDate,tenure,'Renewed',@accountName,accountNumber);

INSERT INTO  new_loan_appstore1 VALUES(
  theLoanTxnId,
  DATE(NOW()),
  theLoanId,
  tenure,
  tenure,
  amount,
  @totalInterestComputed,
  (amount+@totalInterestComputed),
  (amount+@totalInterestComputed),
  initialDisburseDate,
  @startDate,
  @endDate,
  rate,
  @accountName,
  'Renewed',
  TIME(CURRENT_TIMESTAMP),
  '0.0',
  ((amount+@totalInterestComputed)/tenure),
  CONCAT(tenure," ",periodTypeString),
  accountNumber,
  userId,
  theDisId,
  loanOfficerId,
  'G0001',
  'GROUP1',
  '1;NA;NA',
  buzId,
  renewals+1,
  batchNumber,
  '0.0',
  @totalInterestComputed,
  '0.0',
  amount,
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  'Existing Borrower',
   'Individual',
   'Group 1',
    'Group 1',
    'Cycle1',
    'Salary Loan',
    'Friends',
    'Single Instalment Loan',
    'Business Financing',
    'No History',
    'Cant Tell',
    'NO',
    0,
    'Level 1',
     'Monthly Income',
     '100,000-200,000',
     'Very Low',
      'Self Employment',
      'Agriculture',
      'Male',
      'Single',
      'Minor',
      'No Education',
      'Email',
      'augbazi@mail.com',
      'Excellent',
      'Excellent',
       '1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;',
        'NA',
         'NA',
          'NA',
           'NA'
  );


INSERT INTO  pmms.loandisburserepaystatement VALUES(NULL,DATE(NOW()),MONTH(DATE(NOW())),YEAR(DATE(NOW())),theLoanTxnId,theLoanId,accountNumber,batchNumber,amount,@totalInterestComputed,(amount+@totalInterestComputed),rate,'0.0','0.0','0.0','0.0','0.0',amount,@totalInterestComputed,'0.0','0.0',(amount+@totalInterestComputed),'Renewed',userId,loanOfficerId,'NA','NA');



-- SELECT previousLoanTrnId;

-- IF EXISTS(SELECT * FROM gaurantors WHERE loanTrnId=previousLoanTrnId) THEN

--     -- Insert guarantors for the new loan
--          INSERT INTO gaurantors (gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness, loanTrnId) SELECT gaurantorsName,gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber, gaurantorsRelationWithBorrower,gaurantorsHomeArea, gaurantorsBusiness, theLoanTxnId FROM gaurantors WHERE loanTrnId = previousLoanTrnId;

-- END IF;

/* ==========================================================
       Carry forward the MOST RECENT guarantor BY ACCOUNT NUMBER
       Priority 1: latest active guarantor in `gaurantors` (id DESC)
       Priority 2: latest archived guarantor in `gaurantors_archive`
                   (archived_at DESC, archive_id DESC)
    =========================================================== */

    IF v_account_num IS NOT NULL THEN
        -- Try latest active guarantor for this account
        SELECT g.id
          INTO v_active_g_id
          FROM gaurantors g
         WHERE g.accountNumber = v_account_num
         ORDER BY g.id DESC
         LIMIT 1;

        IF v_active_g_id IS NOT NULL THEN
            INSERT INTO gaurantors (
                gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,
                gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness,
                accountNumber, loanTrnId
            )
            SELECT
                g.gaurantorsName, g.gaurantorsContact1, g.gaurantorsContact2, g.gaurantorsIdNumber,
                g.gaurantorsRelationWithBorrower, g.gaurantorsHomeArea, g.gaurantorsBusiness,
                v_account_num, theLoanTxnId
            FROM gaurantors g
            WHERE g.id = v_active_g_id;

            SET v_new_guarantor_id = LAST_INSERT_ID();
            SET v_carried_from = 'ACTIVE';

        ELSE
            -- Fallback: latest archived guarantor for this account
            SELECT ga.archive_id
              INTO v_arch_g_archive_id
              FROM gaurantors_archive ga
             WHERE ga.accountNumber = v_account_num
             ORDER BY ga.archived_at DESC, ga.archive_id DESC
             LIMIT 1;

            IF v_arch_g_archive_id IS NOT NULL THEN
                INSERT INTO gaurantors (
                    gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,
                    gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness,
                    accountNumber, loanTrnId
                )
                SELECT
                    ga.gaurantorsName, ga.gaurantorsContact1, ga.gaurantorsContact2, ga.gaurantorsIdNumber,
                    ga.gaurantorsRelationWithBorrower, ga.gaurantorsHomeArea, ga.gaurantorsBusiness,
                    v_account_num, theLoanTxnId
                FROM gaurantors_archive ga
                WHERE ga.archive_id = v_arch_g_archive_id;

                SET v_new_guarantor_id = LAST_INSERT_ID();
                SET v_carried_from = 'ARCHIVE';
            END IF;
        END IF;
    END IF;

COMMIT;

SELECT 1  AS theMessage;

END $$

DELIMITER ;





-- delete from new_loan_appstore where applicant_account_number='05502119610';
-- delete from new_loan_appstore1 where applicant_account_number='05502119610';
-- delete from new_loan_appstoreamort where master2_id='newloan05502119610';
-- CALL createdCombinedLoan('05502109610',247316.0,0.0,30,'2025-09-13',1.0,'DAYS',10001,1.0,'2025-09-13',10001,0,1,'BTN188856',5,4,77099);

DROP PROCEDURE IF EXISTS createdCombinedLoan;

DELIMITER $$

CREATE PROCEDURE createdCombinedLoan(
  IN accountNumber VARCHAR(60),
  IN amount DOUBLE,
  IN rate DOUBLE,
  IN tenure DOUBLE,
  IN theLastTransactionDate DATE,
  IN periodTypeNumber DOUBLE,
  IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN initialDisburseDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT,IN renewals INT,IN theDisId INT) BEGIN
DECLARE theLoanTxnId,timeP,timeP1 INT;
DECLARE theLoanId VARCHAR(60);
   DECLARE previousLoanTrnId INT;

    DECLARE v_account_num VARCHAR(60) DEFAULT accountNumber;
    DECLARE v_active_g_id INT DEFAULT NULL;
    DECLARE v_arch_g_archive_id INT DEFAULT NULL;
    DECLARE v_new_guarantor_id INT DEFAULT NULL;
    DECLARE v_carried_from VARCHAR(10) DEFAULT NULL; -- 'ACTIVE' or 'ARCHIVE'
--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;F
  -- Debits	Airtel Money(1221109)	01121000310	Mobile Money	2.43012E7
-- END;
 
START TRANSACTION;



SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;

SELECT trn_id INTO previousLoanTrnId from new_loan_appstore where applicant_account_number=accountNumber ORDER BY trn_id DESC LIMIT 1;

CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,0.0,tenure,periodTypeNumber,interestRegime,initialDisburseDate,thePeriodSet,theCompuM,@totalInterestComputed);

SET @period= thePeriod(periodTypeNumber),timeP=1,timeP1=2;


IF periodTypeNumber=3 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


IF periodTypeNumber=6 THEN
SET timeP=6,timeP1=12,tenure=(tenure*6);
END IF;

IF periodTypeNumber=8 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


SET @startDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),initialDisburseDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @startDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),initialDisburseDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @nextDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @endDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),initialDisburseDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),tenure,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @endDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @endDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT @startDate,@nextDate,@endDate;
-- OTHER CHANGES TO NOTE:
-- OtherGroups2=Business Id
-- OtherGroups3=Number of Renewals
-- OtherGroups4=BatchNumber
-- authoriser_id=DisbursedLoanId

 CALL accountNma(accountNumber,@accountName);

INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,initialDisburseDate,theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),initialDisburseDate,@startDate,@endDate,rate,@accountName,'Combined',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theDisId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,renewals+1,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');

INSERT INTO loanprocessingstore VALUES(NULL,theLastTransactionDate,amount,@totalInterestComputed,@nextDate,@endDate,tenure,'Combined',@accountName,accountNumber);

INSERT INTO  new_loan_appstore1 VALUES(
  theLoanTxnId,
  initialDisburseDate,
  theLoanId,
  tenure,
  tenure,
  amount,
  @totalInterestComputed,
  (amount+@totalInterestComputed),
  (amount+@totalInterestComputed),
  initialDisburseDate,
  @startDate,
  @endDate,
  rate,
  @accountName,
  'Combined',
  TIME(CURRENT_TIMESTAMP),
  '0.0',
  ((amount+@totalInterestComputed)/tenure),
  CONCAT(tenure," ",periodTypeString),
  accountNumber,
  userId,
  theDisId,
  loanOfficerId,
  'G0001',
  'GROUP1',
  '1;NA;NA',
  buzId,
  renewals+1,
  batchNumber,
  '0.0',
  @totalInterestComputed,
  '0.0',
  amount,
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  'Existing Borrower',
   'Individual',
   'Group 1',
    'Group 1',
    'Cycle1',
    'Salary Loan',
    'Friends',
    'Single Instalment Loan',
    'Business Financing',
    'No History',
    'Cant Tell',
    'NO',
    0,
    'Level 1',
     'Monthly Income',
     '100,000-200,000',
     'Very Low',
      'Self Employment',
      'Agriculture',
      'Male',
      'Single',
      'Minor',
      'No Education',
      'Email',
      'augbazi@mail.com',
      'Excellent',
      'Excellent',
       '1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;',
        'NA',
         'NA',
          'NA',
           'NA'
  );


INSERT INTO  pmms.loandisburserepaystatement VALUES(NULL,initialDisburseDate,MONTH(initialDisburseDate),YEAR(initialDisburseDate),theLoanTxnId,theLoanId,accountNumber,batchNumber,amount,@totalInterestComputed,(amount+@totalInterestComputed),rate,'0.0','0.0','0.0','0.0','0.0',amount,@totalInterestComputed,'0.0','0.0',(amount+@totalInterestComputed),'Combined',userId,loanOfficerId,'NA','NA');



-- SELECT previousLoanTrnId;

-- IF EXISTS(SELECT * FROM gaurantors WHERE loanTrnId=previousLoanTrnId) THEN

--     -- Insert guarantors for the new loan
--          INSERT INTO gaurantors (gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness, loanTrnId) SELECT gaurantorsName,gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber, gaurantorsRelationWithBorrower,gaurantorsHomeArea, gaurantorsBusiness, theLoanTxnId FROM gaurantors WHERE loanTrnId = previousLoanTrnId;

-- END IF;

/* ==========================================================
       Carry forward the MOST RECENT guarantor BY ACCOUNT NUMBER
       Priority 1: latest active guarantor in `gaurantors` (id DESC)
       Priority 2: latest archived guarantor in `gaurantors_archive`
                   (archived_at DESC, archive_id DESC)
    =========================================================== */

    IF v_account_num IS NOT NULL THEN
        -- Try latest active guarantor for this account
        SELECT g.id
          INTO v_active_g_id
          FROM gaurantors g
         WHERE g.accountNumber = v_account_num
         ORDER BY g.id DESC
         LIMIT 1;

        IF v_active_g_id IS NOT NULL THEN
            INSERT INTO gaurantors (
                gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,
                gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness,
                accountNumber, loanTrnId
            )
            SELECT
                g.gaurantorsName, g.gaurantorsContact1, g.gaurantorsContact2, g.gaurantorsIdNumber,
                g.gaurantorsRelationWithBorrower, g.gaurantorsHomeArea, g.gaurantorsBusiness,
                v_account_num, theLoanTxnId
            FROM gaurantors g
            WHERE g.id = v_active_g_id;

            SET v_new_guarantor_id = LAST_INSERT_ID();
            SET v_carried_from = 'ACTIVE';

        ELSE
            -- Fallback: latest archived guarantor for this account
            SELECT ga.archive_id
              INTO v_arch_g_archive_id
              FROM gaurantors_archive ga
             WHERE ga.accountNumber = v_account_num
             ORDER BY ga.archived_at DESC, ga.archive_id DESC
             LIMIT 1;

            IF v_arch_g_archive_id IS NOT NULL THEN
                INSERT INTO gaurantors (
                    gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,
                    gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness,
                    accountNumber, loanTrnId
                )
                SELECT
                    ga.gaurantorsName, ga.gaurantorsContact1, ga.gaurantorsContact2, ga.gaurantorsIdNumber,
                    ga.gaurantorsRelationWithBorrower, ga.gaurantorsHomeArea, ga.gaurantorsBusiness,
                    v_account_num, theLoanTxnId
                FROM gaurantors_archive ga
                WHERE ga.archive_id = v_arch_g_archive_id;

                SET v_new_guarantor_id = LAST_INSERT_ID();
                SET v_carried_from = 'ARCHIVE';
            END IF;
        END IF;
    END IF;

COMMIT;

SELECT 1  AS theMessage;

END $$

DELIMITER ;





DROP PROCEDURE IF EXISTS createNewLoan;

DELIMITER $$

CREATE PROCEDURE createNewLoan(IN accountNumber VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN currentDate DATE,IN periodTypeNumber DOUBLE,IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN amortizationDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT) createNewLoan: BEGIN
DECLARE theLoanTxnId,timeP,timeP1 INT;
DECLARE theLoanId VARCHAR(60);

   DECLARE previousLoanTrnId INT;

  DECLARE v_allowed INT DEFAULT 1;
  DECLARE v_reason_code VARCHAR(40) DEFAULT '';
  DECLARE v_block_message VARCHAR(255) DEFAULT '';

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;

CALL check_account_disbursement(accountNumber, currentDate, v_allowed, v_reason_code, v_block_message);
 IF v_allowed = 0 THEN
   SELECT 0 AS theMessage, v_reason_code AS reason_code, v_block_message AS message;
   LEAVE createNewLoan;
 END IF;

 START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;

SELECT trn_id INTO previousLoanTrnId from new_loan_appstore where applicant_account_number=accountNumber ORDER BY trn_id DESC LIMIT 1;


CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,amortizationDate,thePeriodSet,theCompuM,@totalInterestComputed);

SET @period= thePeriod(periodTypeNumber),timeP=1,timeP1=2;


IF periodTypeNumber=3 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


IF periodTypeNumber=6 THEN
SET timeP=6,timeP1=12,tenure=(tenure*6);
END IF;

IF periodTypeNumber=8 THEN
SET timeP=2,timeP1=4,tenure=(tenure*2);
END IF;


SET @startDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),amortizationDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @startDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),amortizationDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @nextDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @endDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),amortizationDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),tenure,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @endDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @endDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT @startDate,@nextDate,@endDate;


 CALL accountNma(accountNumber,@accountName);
-- SELECT buzId;
INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,amortizationDate,theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),amortizationDate,@startDate,@endDate,rate,@accountName,'Disbursed',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theLoanTxnId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,0,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');

INSERT INTO loanprocessingstore VALUES(NULL,amortizationDate,amount,@totalInterestComputed,@startDate,@endDate,tenure,'Disbursed',@accountName,accountNumber);

INSERT INTO  new_loan_appstore1 VALUES(
  theLoanTxnId,
  amortizationDate,
  theLoanId,tenure,
  tenure,
  amount,
  @totalInterestComputed,
  (amount+@totalInterestComputed),
  (amount+@totalInterestComputed),
  amortizationDate,
  @startDate,
  @endDate,
  rate,
  @accountName,
  'Disbursed',
  TIME(CURRENT_TIMESTAMP),
  '0.0',
  ((amount+@totalInterestComputed)/tenure),
  CONCAT(tenure," ",periodTypeString),
  accountNumber,
  userId,
  theLoanTxnId,
  loanOfficerId,
  'G0001',
  'GROUP1',
  '1;NA;NA',
  buzId,
  0,
  batchNumber,
  '0.0',
  @totalInterestComputed,
  '0.0',
  amount,
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  '0.0',
  'Existing Borrower',
   'Individual',
   'Group 1',
    'Group 1',
    'Cycle1',
    'Salary Loan',
    'Friends',
    'Single Instalment Loan',
    'Business Financing',
    'No History',
    'Cant Tell',
    'NO',
    0,
    'Level 1',
     'Monthly Income',
     '100,000-200,000',
     'Very Low',
      'Self Employment',
      'Agriculture',
      'Male',
      'Single',
      'Minor',
      'No Education',
      'Email',
      'augbazi@mail.com',
      'Excellent',
      'Excellent',
       '1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;',
        'NA',
         'NA',
          'NA',
           'NA'
  );


INSERT INTO  pmms.loandisburserepaystatement VALUES(NULL,amortizationDate,MONTH(amortizationDate),YEAR(amortizationDate),theLoanTxnId,theLoanId,accountNumber,batchNumber,amount,@totalInterestComputed,(amount+@totalInterestComputed),rate,'0.0','0.0','0.0','0.0','0.0',amount,@totalInterestComputed,'0.0','0.0',(amount+@totalInterestComputed),'Disbursed',userId,loanOfficerId,'NA','NA');

SET @Month=  CONCAT(tenure," ",periodTypeString);
 IF @Month='1 MONTHS' OR @Month='1.O MONTHS' THEN 
 CALL createFirstInterestIntalment(theLoanId,@nextDate,amount,@totalInterestComputed);
 CALL createTheIndividualMethod(theLoanId);
END IF;





-- IF EXISTS(SELECT * FROM gaurantors WHERE loanTrnId=previousLoanTrnId) THEN

--     -- Insert guarantors for the new loan
--         INSERT INTO gaurantors (gaurantorsName, gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber,gaurantorsRelationWithBorrower, gaurantorsHomeArea, gaurantorsBusiness, loanTrnId) SELECT gaurantorsName,gaurantorsContact1, gaurantorsContact2, gaurantorsIdNumber, gaurantorsRelationWithBorrower,gaurantorsHomeArea, gaurantorsBusiness, theLoanTxnId FROM gaurantors WHERE loanTrnId = previousLoanTrnId;

-- END IF;


COMMIT;

SELECT 1  AS theMessage, '' AS reason_code, '' AS message;

END $$

DELIMITER ;





DROP PROCEDURE IF EXISTS accountNma;

DELIMITER //

    CREATE PROCEDURE accountNma(IN accountNumber VARCHAR(30),OUT accountName VARCHAR(30))
             BEGIN

            SELECT account_name FROM pmms.account_created_store where account_number=accountNumber INTO accountName;
                    
            END //
			
			DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS theLoanId;

DELIMITER ##

CREATE FUNCTION theLoanId() 
RETURNS INT
DETERMINISTIC

BEGIN

DECLARE loanTheId INT;

SELECT  otherNumbers3+1 INTO loanTheId FROM pmms.sequencenumbers;
UPDATE pmms.sequencenumbers SET otherNumbers3=loanTheId;
RETURN loanTheId;

END ##
DELIMITER ;








-- -----------------------------------------------------
-- createAmortzationSchedule

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createAmortzationSchedule;

DELIMITER $$

CREATE PROCEDURE createAmortzationSchedule(IN theLoanTxnId INT, IN theLoanId VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN periodTypeNumber DOUBLE,IN interestRegime DOUBLE,IN amortizationDate DATE,IN thePeriodSet DOUBLE,IN theCompuM INT, OUT totalInterestComputed DOUBLE) BEGIN

       DECLARE counter,counterX INT;
DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount,thePrincipalIntalmentX1  DOUBLE;




CASE interestRegime
      WHEN 1 THEN   -- Flat interest regime
      
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,actualTotalInterest=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;

SET @dueDate=amortizationDate;





SET actualTotalInterest=ROUND(interestComputedY(amount,rate,numberOfYears(tenure,periodTypeNumber)),0);



      IF thePeriodSet=0 THEN
SET theInterestInstalmentX=ROUND(interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber)),0);      
SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(amount,tenure),0);

SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;

-- select amount,thePrincipalIntalmentX,actualTotalInterest,theInterestInstalmentX;

REPEAT 

SET @period= thePeriod(periodTypeNumber);

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


   IF EXISTS (SELECT 1 FROM pmms.sequencenumbers WHERE otherNumbers4 = 1) THEN
   -- Check if the computed date falls on a Sunday
    IF DAYOFWEEK(@dueDate) = 1 THEN
        -- If it's Sunday, add one more day to make it Monday
        SET @dueDate = DATE_ADD(@dueDate, INTERVAL 1 DAY);
    END IF;
END IF;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;
IF counter=tenure THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((ROUND(interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)),0))-totalInterestX));

END IF;



SET @INTER=thePeriod(periodTypeNumber);

INSERT INTO new_loan_appstoreamort VALUES(
  NULL,
  counter,
  ROUND(thePrincipalIntalmentX,0),
  openingPrincimpalBal,
  ROUND(theInterestInstalmentX,0),
  openingInterestBal,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(openingPrincimpalBal+openingInterestBal,0),
  ROUND(closingPrincipalBal+closingInterestBal,0),
  @dueDate,
  'NY',
  @dueDate,
  DATEDIFF(instalment_due_date,instalment_paid_date),
  0,
  0,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(thePrincipalIntalmentX,0),
  0,
  ROUND(theInterestInstalmentX,0),
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  'NYA',
  DATE(NOW()),
  DATE(NOW()),
  theLoanTxnId,
  theLoanId
  );


-- SELECT counter,theInterestInstalmentX,thePrincipalIntalmentX;
SET openingPrincimpalBal=openingPrincimpalBal-thePrincipalIntalmentX,closingPrincipalBal=closingPrincipalBal-thePrincipalIntalmentX,openingInterestBal=openingInterestBal-theInterestInstalmentX,closingInterestBal=closingInterestBal-theInterestInstalmentX;

UNTIL counter=tenure END REPEAT;



      ELSE
SET theInterestInstalmentX=ROUND(actualTotalInterest/thePeriodSet,0);
SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(amount,thePeriodSet),0);

SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;

-- select amount,thePrincipalIntalmentX,actualTotalInterest,theInterestInstalmentX;

REPEAT 

SET @period= thePeriod(periodTypeNumber);

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;



   IF EXISTS (SELECT 1 FROM pmms.sequencenumbers WHERE otherNumbers4 = 1) THEN
   -- Check if the computed date falls on a Sunday
    IF DAYOFWEEK(@dueDate) = 1 THEN
        -- If it's Sunday, add one more day to make it Monday
        SET @dueDate = DATE_ADD(@dueDate, INTERVAL 1 DAY);
    END IF;
END IF;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;



IF counter=thePeriodSet THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((ROUND(interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)),0))-totalInterestX));

END IF;

  -- SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;
SET @INTER=thePeriod(periodTypeNumber);

INSERT INTO new_loan_appstoreamort VALUES(
  NULL,
  counter,
  ROUND(thePrincipalIntalmentX,0),
  openingPrincimpalBal,
  ROUND(theInterestInstalmentX,0),
  openingInterestBal,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(openingPrincimpalBal+openingInterestBal,0),
  ROUND(closingPrincipalBal+closingInterestBal,0),
  @dueDate,
  'NY',
  @dueDate,
  DATEDIFF(instalment_due_date,instalment_paid_date),
  0,
  0,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(thePrincipalIntalmentX,0),
  0,
  ROUND(theInterestInstalmentX,0),
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  'NYA',
  DATE(NOW()),
  DATE(NOW()),
  theLoanTxnId,
  theLoanId
  );

SET openingPrincimpalBal=openingPrincimpalBal-thePrincipalIntalmentX,closingPrincipalBal=closingPrincipalBal-thePrincipalIntalmentX,openingInterestBal=openingInterestBal-theInterestInstalmentX,closingInterestBal=closingInterestBal-theInterestInstalmentX;

UNTIL counter=thePeriodSet END REPEAT;



      END IF;

      WHEN 3 THEN 
       
       
       SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,closingPrincipalBal=0.0,closingInterestBal=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;

SET @dueDate=amortizationDate;


SET theTxnAmount=amount;

SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));
SELECT actualTotalInterestReducingCompound(amount,rate,periodTypeNumber,tenure) INTO actualTotalInterest;
SET theInitialInstalmentX=instalmentCalculatedEPI(amount,rate,tenure,periodTypeNumber);
SET thePrincipalIntalmentX=ROUND(theInitialInstalmentX-theInterestInstalmentX);

SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;


REPEAT 

SET @period= thePeriod(periodTypeNumber);

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;



   IF EXISTS (SELECT 1 FROM pmms.sequencenumbers WHERE otherNumbers4 = 1) THEN
   -- Check if the computed date falls on a Sunday
    IF DAYOFWEEK(@dueDate) = 1 THEN
        -- If it's Sunday, add one more day to make it Monday
        SET @dueDate = DATE_ADD(@dueDate, INTERVAL 1 DAY);
    END IF;
END IF;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;


  -- SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


IF counter=tenure THEN
-- SELECT amount,totalPrincipalX;
SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX));

END IF; 



INSERT INTO new_loan_appstoreamort VALUES(
  NULL,
  counter,
  ROUND(thePrincipalIntalmentX,0),
  ROUND(openingPrincimpalBal,0),
  ROUND(theInterestInstalmentX,0),
  ROUND(openingInterestBal,0),
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(openingPrincimpalBal+openingInterestBal,0),
  ROUND(closingPrincipalBal+closingInterestBal,0),
  @dueDate,
  'NY',
  @dueDate,
  DATEDIFF(instalment_due_date,instalment_paid_date),
  0,
  0,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(thePrincipalIntalmentX,0),
  0,
  ROUND(theInterestInstalmentX,0),
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  'NYA',
  DATE(NOW()),
  DATE(NOW()),
  theLoanTxnId,
  theLoanId
  );

SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX;


SET openingPrincimpalBal=openingPrincimpalBal-thePrincipalIntalmentX,closingPrincipalBal=closingPrincipalBal-thePrincipalIntalmentX,openingInterestBal=openingInterestBal-theInterestInstalmentX;
SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)),0);
SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;
SET closingInterestBal=closingInterestBal-theInterestInstalmentX;




UNTIL counter=tenure END REPEAT;
           

   

      WHEN 2 THEN 

IF theCompuM=1 THEN

       SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,closingPrincipalBal=0.0,closingInterestBal=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;

SET @dueDate=amortizationDate;


SET theTxnAmount=amount;

SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));

  SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(amount,tenure),0);

SELECT actualTotalInterestReducing(amount,rate,periodTypeNumber,tenure) INTO actualTotalInterest;

SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;


REPEAT 

SET @period= thePeriod(periodTypeNumber);

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

   IF EXISTS (SELECT 1 FROM pmms.sequencenumbers WHERE otherNumbers4 = 1) THEN
   -- Check if the computed date falls on a Sunday
    IF DAYOFWEEK(@dueDate) = 1 THEN
        -- If it's Sunday, add one more day to make it Monday
        SET @dueDate = DATE_ADD(@dueDate, INTERVAL 1 DAY);
    END IF;
END IF;



SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;


  -- SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


IF counter=tenure THEN
-- SELECT amount,totalPrincipalX;
SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX));

END IF; 



INSERT INTO new_loan_appstoreamort VALUES(
  NULL,
  counter,
  ROUND(thePrincipalIntalmentX,0),
  ROUND(openingPrincimpalBal,0),
  ROUND(theInterestInstalmentX,0),
  ROUND(openingInterestBal,0),
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(openingPrincimpalBal+openingInterestBal,0),
  ROUND(closingPrincipalBal+closingInterestBal,0),
  @dueDate,
  'NY',
  @dueDate,
  DATEDIFF(instalment_due_date,instalment_paid_date),
  0,
  0,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(thePrincipalIntalmentX,0),
  0,
  ROUND(theInterestInstalmentX,0),
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  'NYA',
  DATE(NOW()),
  DATE(NOW()),
  theLoanTxnId,
  theLoanId
  );

SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX;


SET openingPrincimpalBal=openingPrincimpalBal-thePrincipalIntalmentX,closingPrincipalBal=closingPrincipalBal-thePrincipalIntalmentX,openingInterestBal=openingInterestBal-theInterestInstalmentX;
SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)),0);
SET closingInterestBal=closingInterestBal-theInterestInstalmentX;


UNTIL counter=tenure END REPEAT;
           

   

ELSEIF theCompuM=2 THEN

   SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,closingPrincipalBal=0.0,closingInterestBal=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;

SET @dueDate=amortizationDate;

SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(amount,tenure),0);

SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));  

SET theInitialInstalmentX=instalmentCalculatedEPI(amount,rate ,tenure,periodTypeNumber) ;

SET theTxnAmount=amount;

SELECT actualTotalInterestReducingCompound(amount,rate,periodTypeNumber,tenure) INTO actualTotalInterest;



SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;


REPEAT 

SET @period= thePeriod(periodTypeNumber);

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


   IF EXISTS (SELECT 1 FROM pmms.sequencenumbers WHERE otherNumbers4 = 1) THEN
   -- Check if the computed date falls on a Sunday
    IF DAYOFWEEK(@dueDate) = 1 THEN
        -- If it's Sunday, add one more day to make it Monday
        SET @dueDate = DATE_ADD(@dueDate, INTERVAL 1 DAY);
    END IF;
END IF;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;


  -- SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


IF counter=tenure THEN
-- SELECT amount,totalPrincipalX;
SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX));

END IF; 



INSERT INTO new_loan_appstoreamort VALUES(
  NULL,
  counter,
  ROUND(thePrincipalIntalmentX,0),
  ROUND(openingPrincimpalBal,0),
  ROUND(theInterestInstalmentX,0),
  ROUND(openingInterestBal,0),
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(openingPrincimpalBal+openingInterestBal,0),
  ROUND(closingPrincipalBal+closingInterestBal,0),
  @dueDate,
  'NY',
  @dueDate,
  DATEDIFF(instalment_due_date,instalment_paid_date),
  0,
  0,
  ROUND(thePrincipalIntalmentX+theInterestInstalmentX,0),
  0,
  ROUND(thePrincipalIntalmentX,0),
  0,
  ROUND(theInterestInstalmentX,0),
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  'NYA',
  DATE(NOW()),
  DATE(NOW()),
  theLoanTxnId,
  theLoanId
  );

SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX1;


-- SET theInterestInstalmentX=interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber));  



SET openingPrincimpalBal=openingPrincimpalBal-thePrincipalIntalmentX,closingPrincipalBal=closingPrincipalBal-thePrincipalIntalmentX,openingInterestBal=openingInterestBal-theInterestInstalmentX;
SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)),0);
SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX;
SET closingInterestBal=closingInterestBal-theInterestInstalmentX;



UNTIL counter=tenure END REPEAT;
           

   

END IF;


  
    END CASE;

-- SELECT actualTotalInterest;
SELECT actualTotalInterest INTO totalInterestComputed;

END $$

DELIMITER ;






DROP FUNCTION IF EXISTS flatPrincipalInstalment;

DELIMITER $$

CREATE FUNCTION flatPrincipalInstalment(thePrincipalAmount DOUBLE,tenure DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
DECLARE pInstalment DOUBLE;
SET pInstalment= thePrincipalAmount/tenure;

IF ISNULL(pInstalment) THEN
SET pInstalment=0;

END IF;
RETURN pInstalment;
END $$
DELIMITER ;






-- -----------------------------------------------------
-- interestComputed

/* This is a function for testing whether the company has already been registered */

/* ONLY one ompany should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS interestComputedY;

DELIMITER $$

CREATE FUNCTION interestComputedY( principal DOUBLE,rate DOUBLE,theTime DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
DECLARE interest DOUBLE;
SET interest= (principal*rate*theTime)/100;

IF ISNULL(interest) THEN
SET interest=0.0;
END IF;

RETURN interest;
END $$
DELIMITER ;

																																																																													

-- -----------------------------------------------------
-- theAnnualRate

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS theAnnualRate;

DELIMITER $$

CREATE FUNCTION theAnnualRate(theRate DOUBLE,amortCycle DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN

DECLARE theAnnualRate DOUBLE;

IF amortCycle=1 THEN
SET theAnnualRate=theRate*360;
END IF;

IF amortCycle=2 THEN
SET theAnnualRate=theRate*48;
END IF;


IF amortCycle=3 THEN
SET theAnnualRate=theRate*24;
END IF;


IF amortCycle=4 THEN
SET theAnnualRate=theRate*12;
END IF;


IF amortCycle=5 THEN
SET theAnnualRate=theRate*4;
END IF;


IF amortCycle=6 THEN
SET theAnnualRate=theRate*2;
END IF;



IF amortCycle=7 THEN
SET theAnnualRate=theRate*1;
END IF;

IF amortCycle=8 THEN
SET theAnnualRate=theRate*.5;
END IF;

IF ISNULL(theAnnualRate) THEN

SET theAnnualRate=0.0;
END IF;

RETURN theAnnualRate;
END $$
DELIMITER ;




-- -----------------------------------------------------
-- numberOfYears

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS numberOfYears;

DELIMITER $$

CREATE FUNCTION numberOfYears( tenure DOUBLE,amortCycle DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN

DECLARE yearsUsed DOUBLE;

IF amortCycle=1 THEN
SET yearsUsed=tenure/360;
END IF;

IF amortCycle=2 THEN
SET yearsUsed=tenure/48;
END IF;


IF amortCycle=3 THEN
SET yearsUsed=tenure/24;
END IF;


IF amortCycle=4 THEN
SET yearsUsed=tenure/12;
END IF;


IF amortCycle=5 THEN
SET yearsUsed=tenure/4;
END IF;


IF amortCycle=6 THEN
SET yearsUsed=tenure/2;
END IF;



IF amortCycle=7 THEN
SET yearsUsed=tenure/1;
END IF;

IF amortCycle=8 THEN
SET yearsUsed=tenure/.5;
END IF;

IF ISNULL(yearsUsed) THEN

SET yearsUsed=0.0;
END IF;

RETURN yearsUsed;
END $$
DELIMITER ;



-- -----------------------------------------------------
-- numberOfYears

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS thePeriod;

DELIMITER $$

CREATE FUNCTION thePeriod(amortCycle DOUBLE) 
RETURNS VARCHAR(60)
DETERMINISTIC
BEGIN

DECLARE yearsUsed VARCHAR(60);

IF amortCycle=1 THEN
SET yearsUsed='DAY';
END IF;

IF amortCycle=2 THEN
SET yearsUsed='WEEK';
END IF;


IF amortCycle=3 THEN
SET yearsUsed='WEEK';
END IF;


IF amortCycle=4 THEN
SET yearsUsed='MONTH';
END IF;


IF amortCycle=5 THEN
SET yearsUsed='QUARTER';
END IF;


IF amortCycle=6 THEN
SET yearsUsed='MONTH';
END IF;



IF amortCycle=7 THEN
SET yearsUsed='YEAR';
END IF;

IF amortCycle=8 THEN
SET yearsUsed='YEAR';
END IF;
 
 IF ISNULL(yearsUsed) THEN
 SET yearsUsed='DAY';
 END IF;

RETURN yearsUsed;
END $$
DELIMITER ;



-- EPI Equal Period Instalment?


DROP FUNCTION IF EXISTS instalmentCalculatedEPI;

DELIMITER $$

CREATE FUNCTION instalmentCalculatedEPI(thePrincipalAmount DOUBLE,rateUsed DOUBLE,tenure DOUBLE,loanCycle DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN

DECLARE pInstalment,denomi,numi DOUBLE;

set denomi=(POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure)-1);
set numi=(thePrincipalAmount*((actualRateUsed(rateUsed,loanCycle))/100))*POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure);
IF denomi>0 THEN

SET pInstalment= numi/denomi;

-- SET pInstalment=1000/tenure;

ELSE 

SET pInstalment=thePrincipalAmount/tenure;

END IF;

RETURN pInstalment;

END $$
DELIMITER ;


DROP FUNCTION IF EXISTS actualTotalInterestReducingCompound;

DELIMITER $$

CREATE FUNCTION actualTotalInterestReducingCompound(amount DOUBLE,rate DOUBLE,periodTypeNumber DOUBLE,tenure DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
 DECLARE counter,counterX INT;
DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount  DOUBLE;      
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,actualTotalInterest=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;



SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));  

SET theInitialInstalmentX=instalmentCalculatedEPI(amount,rate ,tenure,periodTypeNumber) ;

SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;

SET theTxnAmount=amount;

REPEAT 

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;
-- SELECT counter,theInterestInstalmentX,theInitialInstalmentX,thePrincipalIntalmentX,theTxnAmount;
IF counter=tenure THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)))-totalInterestX));
END IF;
SET theTxnAmount=(theTxnAmount-thePrincipalIntalmentX);

-- SET theInitialInstalmentX=instalmentCalculatedEPI(theTxnAmount,rate ,tenure,periodTypeNumber) ;
SET theInterestInstalmentX=interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)); 
SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;




UNTIL counter=tenure END REPEAT;

RETURN ROUND(totalInterestX,0);

END $$
DELIMITER ;





DROP FUNCTION IF EXISTS actualTotalInterestReducing;

DELIMITER $$

CREATE FUNCTION actualTotalInterestReducing(amount DOUBLE,rate DOUBLE,periodTypeNumber DOUBLE,tenure DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN
 DECLARE counter,counterX INT;
DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount  DOUBLE;      
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0,actualTotalInterest=0.0;
IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
SET counterX=2; 
END IF;

SET thePrincipalIntalmentX=flatPrincipalInstalment(amount,tenure);

SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));   

SET theTxnAmount=amount;

SELECT actualTotalInterestReducingCompound(amount,rate,periodTypeNumber,tenure) INTO actualTotalInterest;
SET theInitialInstalmentX=instalmentCalculatedEPI(amount,rate,tenure,periodTypeNumber);
SET thePrincipalIntalmentX=ROUND(theInitialInstalmentX-theInterestInstalmentX);

SET openingPrincimpalBal=amount,closingPrincipalBal=amount-thePrincipalIntalmentX,openingInterestBal=actualTotalInterest,closingInterestBal=actualTotalInterest-theInterestInstalmentX;


REPEAT 

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;
-- SELECT counter,theInterestInstalmentX,theInitialInstalmentX,thePrincipalIntalmentX,theTxnAmount;
IF counter=tenure THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)))-totalInterestX));
END IF;
SET theTxnAmount=(theTxnAmount-thePrincipalIntalmentX);

-- SET theInitialInstalmentX=instalmentCalculatedEPI(theTxnAmount,rate ,tenure,periodTypeNumber) ;
SET theInterestInstalmentX=interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)); 
SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;




UNTIL counter=tenure END REPEAT;

RETURN ROUND(totalInterestX,0);

END $$
DELIMITER ;





-- -----------------------------------------------------
-- actualRateUsed

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS actualRateUsed;

DELIMITER $$

CREATE FUNCTION actualRateUsed(theInterestRate DOUBLE, amortCycle DOUBLE) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN

DECLARE theRateNow DOUBLE;

IF amortCycle=1 THEN
SET theRateNow=theInterestRate/360;
END IF;

IF amortCycle=2 THEN
SET theRateNow=theInterestRate/48;
END IF;


IF amortCycle=3 THEN
SET theRateNow=theInterestRate/24;
END IF;


IF amortCycle=4 THEN
SET theRateNow=theInterestRate/12;
END IF;


IF amortCycle=5 THEN
SET theRateNow=theInterestRate/4;
END IF;


IF amortCycle=6 THEN
SET theRateNow=theInterestRate/2;
END IF;



IF amortCycle=7 THEN
SET theRateNow=theInterestRate/1;
END IF;

IF amortCycle=8 THEN
SET theRateNow=theInterestRate/.5;
END IF;

IF ISNULL(theInterestRate) THEN

SET theRateNow=0.0;
END IF;

RETURN theRateNow;
END $$
DELIMITER ;



UPDATE new_loan_appstoreamort SET instalment_status='P'  WHERE instalment_status='WF' AND InstalmentRemaining<1;


DROP PROCEDURE IF EXISTS RepayTheLoanNowWrittenOff;
DELIMITER //
CREATE PROCEDURE RepayTheLoanNowWrittenOff(
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

END//

DELIMITER ;

-- DROP PROCEDURE IF EXISTS RepayTheLoanNow;
-- DELIMITER //
-- CREATE PROCEDURE RepayTheLoanNow(
--   IN accountNumber VARCHAR(60),
--   IN AmountPaid DOUBLE,
--   IN batchNumber VARCHAR(30),
--   IN userId INT,
--   IN instalmentPaidDate DATE,
--   IN loanOfficerId INT) READS SQL DATA 
-- BEGIN

--  DECLARE v_errno INT;
--  DECLARE v_message_text VARCHAR(255);

-- DECLARE runningInstalmentId,theLoanTxnId,theLoanId,remainingCinstalments,cycles,completionStatus INT;
-- DECLARE loanCycleStatus,theExistingLoanId VARCHAR(60);
-- DECLARE v_loan_key VARCHAR(100);
-- DECLARE amountTxed,currentIntestX,totalInsterestX, currentPenaltyX,totalPenaltyX,currentAccumulatedInterestX,totalAccumulatedInterestX,currentPrincipalX,totalPrincipalX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,balanceCdue,balanceCdue2,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid,amountRemain,amountRemain2,amountDiff DOUBLE;
--   DECLARE continueProcessing,loanExists INT DEFAULT 1;

--   DECLARE EXIT HANDLER FOR 1452
--   BEGIN
--     ROLLBACK;
--     SELECT 'REF_ER' AS theMessage, 'Referenced field does not exist' AS MESSAGE;
--   END;

--   DECLARE EXIT HANDLER FOR 1062
--   BEGIN
--     ROLLBACK;
--     SELECT 'ER_DUP_ENTRY' AS theMessage, 'Duplicate entry not permitted' AS MESSAGE;
--   END;

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--     ROLLBACK;
--     SELECT 'OTHER_ER' AS theMessage, 'SQL exception occurred' AS MESSAGE;
--   END;

--  START TRANSACTION;
-- SET amountTxed=AmountPaid;

--  SET totalPrincipalX=0;
--  SET totalInsterestX=0;
--  SET totalAccumulatedInterestX=0;
--  SET totalPenaltyX=0;
--  SET completionStatus=0;
--  SET loanCycleStatus=NULL;

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK00_PARAMS' AS step, accountNumber AS account_number, AmountPaid AS amount_paid, batchNumber AS batch_number, userId AS user_id, instalmentPaidDate AS instalment_paid_date, loanOfficerId AS loan_officer_id;
--  END IF;

-- -- SELECT accountNumber;
-- SET v_loan_key=CONCAT('newloan',accountNumber);
-- SELECT theLoanTxnId(v_loan_key) INTO theLoanId;
-- -- SELECT theLoanId;

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK01_LOAN_KEY' AS step, v_loan_key AS loan_key, theLoanId AS loan_trn_id;
--  END IF;

-- -- Check if the loanId exists
--   IF theLoanId IS NULL THEN
--     SET loanExists=0;
--     SET completionStatus=-10;
--     SET loanCycleStatus='LOAN_TRN_ID_NULL';
--   ELSE
--     SELECT COUNT(*) INTO loanExists
--     FROM new_loan_appstore
--     WHERE trn_id = theLoanId;
--   END IF;

--   IF loanExists = 0 AND completionStatus = 0 THEN
--     SET completionStatus=-11;
--     SET loanCycleStatus='LOAN_NOT_FOUND';
--   END IF;

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK02_LOAN_EXISTS' AS step, loanExists AS loan_exists;
--  END IF;

--   -- If the loan ID does not exist, exit the procedure
--   IF loanExists <> 0 THEN





-- IF EXISTS(SELECT * FROM  new_loan_appstoreamort WHERE master1_id=theLoanId AND NOT instalment_status='P') THEN

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK03_AMORT_EXISTS' AS step, 1 AS amort_exists;
--  END IF;

-- -- SELECT loan_cycle_status INTO loanCycleStatus from new_loan_appstore where trn_id=theLoanId;
-- SELECT balance_due,loan_cycle_status INTO amountRemain2,loanCycleStatus from new_loan_appstore where trn_id=theLoanId;
-- SELECT SUM(InstalmentRemaining) INTO amountRemain from new_loan_appstoreamort WHERE master1_id=theLoanId AND NOT instalment_status='P';

-- -- SELECT amountRemain;
-- SET amountDiff=AmountPaid-amountRemain;

-- IF amountDiff>0 THEN
-- SET amountTxed=AmountPaid-amountDiff;
-- END IF;

-- label1:REPEAT
-- -- SELECT theLoanId;
-- SELECT currentInstalmentNow(theLoanId) INTO runningInstalmentId;
-- -- SELECT runningInstalmentId;

-- SELECT  InterestRemaing INTO currentIntestX FROM new_loan_appstoreamort WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;
-- --  SELECT currentIntestX; 
-- IF ISNULL(currentIntestX) THEN
-- SET currentIntestX=0;
-- END IF;

-- -- SELECT currentIntestX; 

-- IF ISNULL(totalInsterestX) THEN
-- SET totalInsterestX=0;
-- END IF;

-- IF currentIntestX>0 THEN
-- -- SELECT amountTxed;
-- IF currentIntestX>=amountTxed THEN
-- SET currentIntestX=amountTxed;
-- END IF;

-- -- SELECT runningInstalmentId;
-- -- SELECT runningInstalmentId,currentIntestX,theLoanId,instalmentPaidDate;
-- CALL updateTheInterestComponent(runningInstalmentId,currentIntestX,theLoanId,instalmentPaidDate);

-- SET totalInsterestX=totalInsterestX+currentIntestX;

-- SET amountTxed=amountTxed-currentIntestX;

-- IF amountTxed<1 THEN
-- LEAVE label1;
-- END IF;

-- END IF;



--  SELECT PrincipalRemaining INTO currentPrincipalX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;


-- --  SELECT currentPrincipalX; 

-- IF ISNULL(currentPrincipalX) THEN
-- SET currentPrincipalX=0;
-- END IF;
 
-- IF currentPrincipalX>0 THEN
-- -- SELECT currentPrincipalX;
-- IF currentPrincipalX>=amountTxed THEN
-- SET currentPrincipalX=amountTxed;
-- END IF;

-- CALL updateThePrincipalComponent(runningInstalmentId,currentPrincipalX,theLoanId,instalmentPaidDate);
-- IF ISNULL(totalPrincipalX) THEN
-- SET totalPrincipalX=0;
-- END IF;
-- SET totalPrincipalX=totalPrincipalX+currentPrincipalX;
-- SET amountTxed=amountTxed-currentPrincipalX;
-- -- SELECT amountTxed; 
-- IF amountTxed<1 THEN
-- LEAVE label1;
-- END IF;

-- END IF;



--  SELECT LoanPenaltyRemaining INTO currentPenaltyX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

-- IF ISNULL(currentPenaltyX) THEN
-- SET currentPenaltyX=0;
-- END IF;

-- IF currentPenaltyX>0 THEN

-- IF currentPenaltyX>=amountTxed THEN
-- SET currentPenaltyX=amountTxed;
-- END IF;

-- CALL updateThePenaltyComponent(runningInstalmentId,currentPenaltyX,theLoanId,instalmentPaidDate);
-- IF ISNULL(totalPenaltyX) THEN
-- SET totalPenaltyX=0;
-- END IF;
-- SET totalPenaltyX=totalPenaltyX+currentPenaltyX;
-- SET amountTxed=amountTxed-currentPenaltyX;

-- IF amountTxed<1 THEN
-- LEAVE label1;
-- END IF;

-- END IF;

-- -- 07/10/2022	07/10/2022	Regular Savings for Fuel1s Savings Processed on 07/10/2022
--   -- From Fuel1	-	20000.0	96000.0	BTN88666

--  SELECT AccumulatedInterestRemaining INTO currentAccumulatedInterestX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

-- IF ISNULL(currentAccumulatedInterestX) THEN
-- SET currentAccumulatedInterestX=0;
-- END IF;


-- IF currentAccumulatedInterestX>0 THEN
-- -- SELECT currentAccumulatedInterestX,amountTxed;
-- IF currentAccumulatedInterestX>=amountTxed THEN
-- SET currentAccumulatedInterestX=amountTxed;
-- END IF;

-- CALL updateTheAccumulatedInterestComponent(runningInstalmentId,currentAccumulatedInterestX,theLoanId,instalmentPaidDate);
-- IF ISNULL(totalAccumulatedInterestX) THEN
-- SET totalAccumulatedInterestX=0;
-- END IF;
-- SET totalAccumulatedInterestX=totalAccumulatedInterestX+currentAccumulatedInterestX;
-- SET amountTxed=amountTxed-currentAccumulatedInterestX;

-- IF amountTxed<1 THEN
-- LEAVE label1;
-- END IF;

-- END IF;

-- IF (currentIntestX + currentPenaltyX + currentAccumulatedInterestX + currentPrincipalX) = 0 THEN
--     -- Mark the instalment as fully paid

--     SELECT currentIntestX + currentPenaltyX + currentAccumulatedInterestX + currentPrincipalX;
--     UPDATE new_loan_appstoreamort
--     SET
--         instalment_status='P',
--         instalment_paid_date=instalmentPaidDate,
--         instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=runningInstalmentId AND master1_id=theLoanId;

--     -- Optionally decrement remaining_instalments in new_loan_appstore if needed:
--     UPDATE new_loan_appstore
--     SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
--     UPDATE new_loan_appstore1
--     SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

--     -- Now set the leftover amount to 0 and exit
--     -- SET amountTxed = 0;
--     -- LEAVE label1;
-- END IF;

-- UNTIL amountTxed<=1 END REPEAT label1;
-- ELSE
-- SET balanceCdue=0,@balanceCdue=0,AmountPaid=0;

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK03_AMORT_EXISTS' AS step, 0 AS amort_exists;
--  END IF;

-- END IF;

-- SET @theTotalLoan = concat(CAST("SELECT balance_due,instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid INTO @balanceCdue,@instalmentsCpaid,@TotalInterestCPaid,@TotalInterestCRemaining,@TotalPrincipalCPaid,@TotalPrincipalCRemaining,@TotalAccumulatedInterestCPaid,@TotalAccumulatedInterestCRemaining,@TotalLoanPenaltyCPaid,@TotalLoanPenaltyCRemaining,@TotalAccruedInterestCRemaining,@TotalAccruedInterestCPaid FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
-- --  select  @theTotalLoan;
--   PREPARE stmt2 FROM @theTotalLoan;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;

-- -- ... (unchanged code)

-- END IF;

--  IF IFNULL(@debug_repay,0)=1 THEN
--  SELECT 'CK99_BEFORE_COMMIT' AS step, totalPrincipalX AS total_principal_x, totalInsterestX AS total_interest_x, totalAccumulatedInterestX AS total_accumulated_interest_x, totalPenaltyX AS total_penalty_x, completionStatus AS completion_status, loanCycleStatus AS loan_cycle_status, theLoanId AS the_loan_id;
--  END IF;

--  -- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;

--  COMMIT;

-- SELECT totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,completionStatus,loanCycleStatus,theLoanId;

-- -- COMMIT;

-- END//
--  DELIMITER ;


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
  DECLARE continueProcessing,loanExists INT DEFAULT 1;
--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   ROLLBACK;
  
-- END;

-- START TRANSACTION;
SET amountTxed=AmountPaid;

-- SELECT accountNumber;
SELECT theLoanTxnId(CONCAT("newloan",accountNumber)) INTO theLoanId;
-- SELECT theLoanId;

-- Check if the loanId exists
  SELECT COUNT(*) INTO loanExists
  FROM new_loan_appstore
  WHERE trn_id = theLoanId;

  -- If the loan ID does not exist, exit the procedure
  IF loanExists <> 0 THEN





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

SELECT  InterestRemaing, PrincipalRemaining, LoanPenaltyRemaining, AccumulatedInterestRemaining
INTO currentIntestX, currentPrincipalX, currentPenaltyX, currentAccumulatedInterestX
FROM new_loan_appstoreamort
WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;
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

IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;



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
IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;



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

-- 07/10/2022	07/10/2022	Regular Savings for Fuel1s Savings Processed on 07/10/2022
  -- From Fuel1	-	20000.0	96000.0	BTN88666

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

IF (currentIntestX + currentPenaltyX + currentAccumulatedInterestX + currentPrincipalX) = 0 THEN
    -- Mark the instalment as fully paid

    SELECT currentIntestX + currentPenaltyX + currentAccumulatedInterestX + currentPrincipalX;
    UPDATE new_loan_appstoreamort
    SET
        instalment_status = 'P',
        instalment_paid_date = instalmentPaidDate,
        instalment_paid = instalment_amount,  -- or however you track "fully paid"
        InstalmentRemaining = 0
    WHERE instalment_no = runningInstalmentId
      AND master1_id = theLoanId;

    -- Optionally decrement remaining_instalments in new_loan_appstore if needed:
    UPDATE new_loan_appstore
    SET remaining_instalments = remaining_instalments - 1
    WHERE trn_id = theLoanId;

    -- Now set the leftover amount to 0 and exit
    -- SET amountTxed = 0;
    -- LEAVE label1;
END IF;


UNTIL amountTxed<=1 END REPEAT label1;
ELSE
SET balanceCdue=0,@balanceCdue=0,AmountPaid=0;

END IF;

SET @theTotalLoan = concat(CAST("SELECT balance_due,instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid INTO @balanceCdue,@instalmentsCpaid,@TotalInterestCPaid,@TotalInterestCRemaining,@TotalPrincipalCPaid,@TotalPrincipalCRemaining,@TotalAccumulatedInterestCPaid,@TotalAccumulatedInterestCRemaining,@TotalLoanPenaltyCPaid,@TotalLoanPenaltyCRemaining,@TotalAccruedInterestCRemaining,@TotalAccruedInterestCPaid FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
 --  select  @theTotalLoan;
 SELECT balance_due,instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid
 INTO @balanceCdue,@instalmentsCpaid,@TotalInterestCPaid,@TotalInterestCRemaining,@TotalPrincipalCPaid,@TotalPrincipalCRemaining,@TotalAccumulatedInterestCPaid,@TotalAccumulatedInterestCRemaining,@TotalLoanPenaltyCPaid,@TotalLoanPenaltyCRemaining,@TotalAccruedInterestCRemaining,@TotalAccruedInterestCPaid
 FROM new_loan_appstore
 WHERE trn_id=theLoanId;

-- ... (unchanged code)

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
 SELECT PrincipalBalance,InterestBalance,AccumulatedInterestBalance,LoanPenaltyBalance,LoanBalance
 INTO @theCPrincipalBalance,@theCInterestBalance,@theCAccumulatedInterestBalance,@theCLoanPenaltyBalance,@theCLoanBalance
 FROM pmms.loandisburserepaystatement
 WHERE loanTrnId=theLoanId
ORDER BY trnId DESC
LIMIT 1;

SET theCPrincipalBalance=@theCPrincipalBalance-totalPrincipalX,theCInterestBalance=@theCInterestBalance-totalInsterestX,theCAccumulatedInterestBalance=@theCAccumulatedInterestBalance-totalAccumulatedInterestX,theCLoanPenaltyBalance=@theCLoanPenaltyBalance-totalPenaltyX,theCLoanBalance=@theCLoanBalance-AmountPaid;

-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;

INSERT INTO pmms.loandisburserepaystatement VALUES(NULL,instalmentPaidDate,MONTH(instalmentPaidDate),YEAR(instalmentPaidDate),theLoanId,CONCAT("newloan",accountNumber),accountNumber,batchNumber,0.0,0.0,0.0,0.0,AmountPaid,totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,loanCycleStatus,userId,loanOfficerId,'NA','NA');

-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;
-- SELECT ;
IF ISNULL(balanceCdue) THEN
SET balanceCdue=0;
END IF;

IF balanceCdue<=1 THEN

SELECT loan_id INTO theExistingLoanId FROM arch_new_loan_appstore where applicant_account_number=accountNumber AND loan_cycle_status='Completed' ORDER BY trn_id DESC LIMIT 1;

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


IF EXISTS(SELECT * FROM arch_new_loan_appstore WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',cycles+10,accountNumber);
END IF;

IF EXISTS(SELECT * FROM arch_new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',cycles+10,accountNumber);
END IF;



IF EXISTS(SELECT * FROM arch_new_loan_appstore WHERE loan_id=@closedAccount) THEN

SELECT SUBSTRING(@closedAccount, 11, 2) INTO cycles;
-- SELECT cycles;
SET @closedAccount=CONCAT('closedloan',cycles+1,accountNumber);
END IF;

IF EXISTS(SELECT * FROM arch_new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SELECT SUBSTRING(@closedAccount, 11, 2) INTO cycles;
-- SELECT cycles;
SET @closedAccount=CONCAT('closedloan',cycles+1,accountNumber);
END IF;


-- SELECT theLoanId;
-- CALL MoveLoanFileAttach(theLoanId);



-- SELECT theLoanId;
CALL MoveClosedLoan(theLoanId);

-- SELECT theLoanId;


/* NEW – archive the guarantors of this loan */
CALL archive_guarantors_by_loan_proc(theLoanId);



UPDATE arch_new_loan_appstore SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE arch_new_loan_appstore1 SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE arch_new_loan_appstoreamort SET instalment_status='P',master2_id=@closedAccount,instalment_paid_date=instalmentPaidDate WHERE master1_id=theLoanId;

UPDATE interestcomputed SET loanId=@closedAccount WHERE loanId=CONCAT("newloan",accountNumber);


UPDATE pmms.arch_loandisburserepaystatement SET LoanStatusReport='Completed',LoanId=@closedAccount WHERE loanTrnId=theLoanId;

SET completionStatus=2;

END IF;

IF ISNULL(completionStatus) THEN
SET completionStatus=1;
END IF;

IF amountDiff>0 THEN
SET totalAccumulatedInterestX=totalAccumulatedInterestX+amountDiff;

CALL updateTheAccumulatedInterestComponentSpecial(runningInstalmentId,amountDiff,theLoanId,instalmentPaidDate);
END IF;


  END IF;


-- SELECT totalAccumulatedInterestX,totalPenaltyX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,theLoanId;

SELECT totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,completionStatus,loanCycleStatus,theLoanId;

-- COMMIT;

END//

 DELIMITER ;



-- ... (unchanged code)

DROP FUNCTION IF EXISTS theLoanTxnId;
DELIMITER ##
CREATE FUNCTION theLoanTxnId(theLoanId VARCHAR(100)) 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE theIdLoan INT;

SELECT trn_id INTO theIdLoan
FROM new_loan_appstore
WHERE loan_id = theLoanId
  AND loan_cycle_status IN ('Disbursed','Renewed','Combined','WrittenOff')
ORDER BY trn_id DESC
LIMIT 1;

RETURN theIdLoan;
END ##
DELIMITER ;





DROP PROCEDURE IF EXISTS createTheRenewalDetails;
DELIMITER //
CREATE PROCEDURE createTheRenewalDetails(IN theRenewalStatus INT,IN theRenewalDeadline INT,theDeadlineMeasure VARCHAR(30),IN theRenewalRate DOUBLE,IN thePeriodUsed INT,IN theRenewalTimes INT,IN theRateTypeUsedNow INT) READS SQL DATA 
BEGIN

IF EXISTS(SELECT * FROM autoRenewalSettings) THEN
UPDATE autoRenewalSettings SET renewalStatus=theRenewalStatus,renewalDeadline=theRenewalDeadline,renewalMeasure=theDeadlineMeasure,renewalRate=theRenewalRate,periodUsed=thePeriodUsed,renewalTimes=theRenewalTimes,rateTypeUsed=theRateTypeUsedNow;
ELSE
INSERT INTO autoRenewalSettings (id,renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed) VALUES("",theRenewalStatus,theRenewalDeadline,theDeadlineMeasure,theRenewalRate,thePeriodUsed,theRenewalTimes,theRateTypeUsedNow);

END IF;

END//

DELIMITER ;


-- SELECT SloanTrnId;

-- DROP TABLE IF EXISTS autoRenewalSettings;

CREATE  TABLE autoRenewalSettings(
  id INTEGER NOT NULL, -- 0
  renewalStatus INT,-- 4
  renewalDeadline INT,-- 4
  renewalMeasure VARCHAR(30),-- 4
  renewalRate DOUBLE,-- 4
  periodUsed INT,
  renewalTimes INT,-- 4
  rateTypeUsed INT,
  renew_only_active INT DEFAULT 0,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE autoRenewalSettings modify column  id enum('1') NOT NULL;

 ALTER TABLE autoRenewalSettings ADD COLUMN renew_only_active INT DEFAULT 0;

DROP PROCEDURE IF EXISTS getTheRenewalDetails;
DELIMITER //
CREATE PROCEDURE getTheRenewalDetails() READS SQL DATA 
BEGIN

IF EXISTS(SELECT * FROM autoRenewalSettings) THEN

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed,renew_only_active FROM  autoRenewalSettings;

ELSE
INSERT INTO autoRenewalSettings (id,renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed) VALUES("",0,2,'DAYS',240.0,0,3,1);

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed,renew_only_active FROM  autoRenewalSettings;

END IF;

END//

-- ... (unchanged code)

DROP PROCEDURE IF EXISTS theLoansForRenewal;
DELIMITER //
CREATE PROCEDURE theLoansForRenewal() READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,theRateTypeUsed,periodSet,l_done,loanId INT;
DECLARE theDealineMeeasure,theLoanTenurez VARCHAR(30);
DECLARE v_renew_only_active INT DEFAULT 0;
   
DECLARE forloanId CURSOR FOR SELECT new_loan_appstore.trn_id   FROM pmms_loans.new_loan_appstore INNER JOIN pmms_loans.new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id where (new_loan_appstore.loan_cycle_status='Disbursed' OR new_loan_appstore.loan_cycle_status='Renewed') AND NOT (new_loan_appstore1.loan_tenure='1 MONTHS' OR new_loan_appstore1.loan_tenure='1.0 MONTHS' );
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

DROP TABLE IF EXISTS theRenewalLoanDetailsN;

CREATE TEMPORARY TABLE theRenewalLoanDetailsN(
  accountNumberN VARCHAR(60),
  officerIdN INT,
  balanceDueN DOUBLE,
  interestRateUsed DOUBLE,
  trnDateUsed DATE,
  initialDisDateUsed DATE,
  tenureUsed INT,
  periodUsed INT,
  theBusinessId INT,
  theNumberOfRenewals INT,
  theDidId INT,
  theNRateTypeUsed INT,
  thePeriodTypeInt INT,
  thePeriodTypeString VARCHAR(60)
);

OPEN forloanId;

SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed,renew_only_active INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals,theRateTypeUsed,v_renew_only_active FROM 
autoRenewalSettings;
loanId_loop: LOOP 

SET loanId=NULL, @accountNumber=NULL,@loansOfficer=NULL, @balanceDueN=NULL,@trnDate=NULL,@initialDisDate=NULL,@loanTenure=NULL,@theBuzId=NULL,@numberRenewals=NULL,@previousId =NULL ;
FETCH forloanId into loanId;
SELECT loan_tenure INTO theLoanTenurez FROM new_loan_appstore1 WHERE trn_id=loanId;
IF l_done=1 THEN
LEAVE loanId_loop;
END IF;

IF theLoanTenurez<>'1 MONTHS' AND theLoanTenurez<>'1.0 MONTHS' THEN

SET @dueDateX = concat(CAST("SELECT new_loan_appstore.applicant_account_number,new_loan_appstore.gruop_id, new_loan_appstore.balance_due,new_loan_appstore.trn_date,new_loan_appstore.instalment_start_date,new_loan_appstore.loan_tenure,new_loan_appstore.OtherGroups2,new_loan_appstore.OtherGroups3,new_loan_appstore.authoriser_id,new_loan_appstore.interest_rate,new_loan_appstore1.loan_tenure INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId,@originalRate,@originalTenure  FROM new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE DATE(NOW())>=" AS CHAR CHARACTER SET utf8),CAST("(SELECT DATE_ADD(instalment_end_date, INTERVAL " AS CHAR CHARACTER SET utf8),theDeadline, CAST(" " AS CHAR CHARACTER SET utf8),theDeadilineReturn(theDealineMeeasure),CAST(" ) FROM new_loan_appstore WHERE trn_id=" AS CHAR CHARACTER SET utf8),loanId,CAST(")  AND new_loan_appstore.OtherGroups3<" AS CHAR CHARACTER SET utf8),numberOfRenewals,CAST(" AND new_loan_appstore.trn_id=" AS CHAR CHARACTER SET utf8),loanId);
IF v_renew_only_active=1 THEN
SET @dueDateX = concat(@dueDateX, CAST(" AND NOT EXISTS (SELECT 1 FROM new_loan_appstoreamort a WHERE a.master1_id = new_loan_appstore.trn_id AND a.instalment_status <> 'P' AND a.instalment_due_date < DATE_SUB(CURDATE(), INTERVAL 30 DAY))" AS CHAR CHARACTER SET utf8));
END IF;
PREPARE stmt2 FROM @dueDateX;
EXECUTE stmt2;
DROP PREPARE stmt2;
IF NOT ISNULL(@accountNumber) THEN
IF theRateTypeUsed=1 THEN
INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
ELSE
INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,@originalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
END IF;
END IF;
END IF;
SET l_done=0;
END LOOP loanId_loop;
CLOSE forloanId;
SELECT * FROM theRenewalLoanDetailsN;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS theLoansForRenewalByTrnId;
DELIMITER //
CREATE PROCEDURE theLoansForRenewalByTrnId(IN input_trn_id INT) READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline, numberOfRenewals, theRateTypeUsed, periodSet, loanId INT;
DECLARE theDealineMeeasure, theLoanTenurez VARCHAR(30);
DECLARE v_renew_only_active INT DEFAULT 0;
   
DECLARE CONTINUE HANDLER FOR NOT FOUND SET loanId = NULL;

    DROP TABLE IF EXISTS theRenewalLoanDetailsN;

    
    CREATE TEMPORARY TABLE theRenewalLoanDetailsN(
        accountNumberN VARCHAR(60),
        officerIdN INT,
        balanceDueN DOUBLE,
        interestRateUsed DOUBLE,
        trnDateUsed DATE,
        initialDisDateUsed DATE,
        tenureUsed INT,
        periodUsed INT,
        theBusinessId INT,
        theNumberOfRenewals INT,
        theDidId INT,
        theNRateTypeUsed INT,
        thePeriodTypeInt INT,
        thePeriodTypeString VARCHAR(60)
    );

    SET loanId = input_trn_id;

    SELECT renewalDeadline, renewalMeasure, renewalRate, periodUsed, renewalTimes, rateTypeUsed, renew_only_active 
    INTO theDeadline, theDealineMeeasure, theRenewalRate, periodSet, numberOfRenewals, theRateTypeUsed, v_renew_only_active 
    FROM autoRenewalSettings;
    
    IF loanId IS NOT NULL THEN
        SELECT loan_tenure INTO theLoanTenurez 
        FROM new_loan_appstore1 
        WHERE trn_id = loanId;

      IF theLoanTenurez<>'1 MONTHS' AND theLoanTenurez<>'1.0 MONTHS' THEN

 SET @dueDateX = concat(CAST("SELECT new_loan_appstore.applicant_account_number,new_loan_appstore.gruop_id, new_loan_appstore.balance_due,new_loan_appstore.trn_date,new_loan_appstore.instalment_start_date,new_loan_appstore.loan_tenure,new_loan_appstore.OtherGroups2,new_loan_appstore.OtherGroups3,new_loan_appstore.authoriser_id,new_loan_appstore.interest_rate,new_loan_appstore1.loan_tenure INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId,@originalRate,@originalTenure  FROM new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE DATE(NOW())>=" AS CHAR CHARACTER SET utf8),CAST("(SELECT DATE_ADD(instalment_end_date, INTERVAL " AS CHAR CHARACTER SET utf8),theDeadline, CAST(" " AS CHAR CHARACTER SET utf8),theDeadilineReturn(theDealineMeeasure),CAST(" ) FROM new_loan_appstore WHERE trn_id=" AS CHAR CHARACTER SET utf8),loanId,CAST(")  AND new_loan_appstore.OtherGroups3<" AS CHAR CHARACTER SET utf8),numberOfRenewals,CAST(" AND new_loan_appstore.trn_id=" AS CHAR CHARACTER SET utf8),loanId);
 IF v_renew_only_active=1 THEN
 SET @dueDateX = concat(@dueDateX, CAST(" AND NOT EXISTS (SELECT 1 FROM new_loan_appstoreamort a WHERE a.master1_id = new_loan_appstore.trn_id AND a.instalment_status <> 'P' AND a.instalment_due_date < DATE_SUB(CURDATE(), INTERVAL 30 DAY))" AS CHAR CHARACTER SET utf8));
 END IF;
  -- select  @dueDateX;
  PREPARE stmt2 FROM @dueDateX;
  EXECUTE stmt2;
DROP PREPARE stmt2;
-- SELECT thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
IF NOT ISNULL(@accountNumber) THEN
IF theRateTypeUsed=1 THEN

INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
ELSE

INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,@originalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));

END IF;
END IF;


END IF;
    END IF;

    SELECT * FROM theRenewalLoanDetailsN;
END//
DELIMITER ;



-- -----------------------------------------------------
-- numberOfYears

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS thePeriodDecoded;

DELIMITER $$

CREATE FUNCTION thePeriodDecoded(amortCycle VARCHAR(60)) 
RETURNS INT
DETERMINISTIC
BEGIN

DECLARE yearsUsed INT;

IF amortCycle='DAYS' THEN
SET yearsUsed=1;
END IF;

IF amortCycle='WEEKS' THEN
SET yearsUsed=2;
END IF;


IF amortCycle='FORTNIGHTS' THEN
SET yearsUsed=3;
END IF;


IF amortCycle='MONTHS' THEN
SET yearsUsed=4;
END IF;


IF amortCycle='QUARTERS' THEN
SET yearsUsed=5;
END IF;


IF amortCycle='HALF YEARS' THEN
SET yearsUsed=6;
END IF;

IF amortCycle='YEARS' THEN
SET yearsUsed=7;
END IF;

IF amortCycle='BIENNIALS' THEN
SET yearsUsed=8;
END IF;
 
 IF ISNULL(yearsUsed) THEN
 SET yearsUsed='DAY';
 END IF;

RETURN yearsUsed;
END $$
DELIMITER ;


-- 70179	AIDA JAMILA  0774-134336	BODA BODA	240	388800	126672	23328	0	150000	30 MONTHS	05/12/2021	10001	Disbursed

 
DROP PROCEDURE IF EXISTS theLoansForRenewalIndividual;
DELIMITER //
CREATE PROCEDURE theLoansForRenewalIndividual(IN theLoanTrnId INT) READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,theRateTypeUsed,periodSet,l_done,loanId INT;
DECLARE theDealineMeeasure,theLoanTenurez VARCHAR(30);
   
-- DECLARE forloanId CURSOR FOR SELECT trn_id   FROM pmms_loans.new_loan_appstore where loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';
--  
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


DROP TABLE IF EXISTS theRenewalLoanDetailsN;

CREATE TEMPORARY  TABLE theRenewalLoanDetailsN(
  accountNumberN VARCHAR(60),
  officerIdN INT,
  balanceDueN DOUBLE,
  interestRateUsed DOUBLE,
  trnDateUsed DATE,
  initialDisDateUsed DATE,
  tenureUsed INT,
   periodUsed INT,
  theBusinessId INT,
  theNumberOfRenewals INT,
   theDidId INT,
  theNRateTypeUsed INT,
   thePeriodTypeInt INT,
   thePeriodTypeString VARCHAR(30)
  );

 
--  OPEN forloanId;

SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals,theRateTypeUsed FROM 
autoRenewalSettings;
-- loanId_loop: LOOP 

 SET loanId=NULL, @accountNumber=NULL,@loansOfficer=NULL, @balanceDueN=NULL,@trnDate=NULL,@initialDisDate=NULL,@loanTenure=NULL,@theBuzId=NULL,@numberRenewals=NULL,@previousId =NULL ;
--  FETCH forloanId into loanId;
--  SELECT loanId;
 SELECT loan_tenure INTO theLoanTenurez FROM new_loan_appstore1 WHERE trn_id=theLoanTrnId;
--  IF l_done=1 THEN

-- LEAVE loanId_loop;

--  END IF;
 

IF theLoanTenurez<>'1 MONTHS' AND theLoanTenurez<>'1.0 MONTHS' THEN

SET @dueDateX = concat(CAST("SELECT new_loan_appstore.applicant_account_number,new_loan_appstore.gruop_id, new_loan_appstore.balance_due,new_loan_appstore.trn_date,new_loan_appstore.instalment_start_date,new_loan_appstore.loan_tenure,new_loan_appstore.OtherGroups2,new_loan_appstore.OtherGroups3,new_loan_appstore.authoriser_id,new_loan_appstore.interest_rate,new_loan_appstore1.loan_tenure INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId,@originalRate,@originalTenure  FROM new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE  new_loan_appstore.trn_id=" AS CHAR CHARACTER SET utf8),theLoanTrnId);

  -- select  @dueDateX;
  PREPARE stmt2 FROM @dueDateX;
  EXECUTE stmt2;
DROP PREPARE stmt2;
-- SELECT thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
IF NOT ISNULL(@accountNumber) THEN
IF @originalRate=1 THEN

INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));
ELSE

INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,@originalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed,thePeriodDecoded(TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1))),TRIM(SUBSTRING_INDEX(@originalTenure,' ',-1)));

END IF;
END IF;


END IF;



SELECT * FROM theRenewalLoanDetailsN;

END//

 DELIMITER ;






-- 25/03/2022		MOMO 3	Mobile Money	0.0	Active25/03/2022	01121000310	MOMO 3	Mobile Money	0.0	Active
-- 

 
-- -----------------------------------------------------
-- theDeadilineReturn

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS theDeadilineReturn;

DELIMITER $$

CREATE FUNCTION theDeadilineReturn(amortCycle VARCHAR(30)) 
RETURNS VARCHAR(30)
DETERMINISTIC
BEGIN

DECLARE yearsUsed VARCHAR(60);

IF amortCycle='DAYS' THEN
SET yearsUsed='DAY';
END IF;

IF amortCycle='WEEKS' THEN
SET yearsUsed='WEEK';
END IF;


IF amortCycle='MONTHS' THEN
SET yearsUsed='MONTH';
END IF;


IF amortCycle='YEARS' THEN
SET yearsUsed='YEAR';
END IF;


RETURN yearsUsed;
END $$
DELIMITER ;








 
DROP PROCEDURE IF EXISTS implementingChanges;
DELIMITER //
CREATE PROCEDURE implementingChanges() READS SQL DATA 
BEGIN

DECLARE l_done INT;
DECLARE loanId VARCHAR(30);

   
DECLARE forloanId CURSOR FOR SELECT trn_id   FROM pmms_loans.new_loan_appstore;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;




ALTER TABLE pmms.sequencenumbers modify column  trn_id enum('1') NOT NULL;


 OPEN forloanId;

loanId_loop: LOOP 
 FETCH forloanId into loanId;

 SELECT theLoanId() INTO @theIdDS;

 IF l_done=1 THEN

LEAVE loanId_loop;

 END IF;

SELECT loanId;


 UPDATE pmms_loans.new_loan_appstore SET OtherGroups2=1,  trn_id= @theIdDS,authoriser_id=@theIdDS,instalment_start_date=trn_date where trn_id=loanId;

 UPDATE pmms_loans.new_loan_appstore1 SET OtherGroups2=1, trn_id= @theIdDS,authoriser_id=@theIdDS,instalment_start_date=trn_date  where trn_id=loanId;

 UPDATE pmms_loans.new_loan_appstoreamort SET master1_id= @theIdDS where master1_id=loanId;

 UPDATE pmms.loandisburserepaystatement SET loanTrnId= @theIdDS where loanTrnId=loanId;

  UPDATE pmms.loandisburserepaystatement SET LoanStatusReport='Disbursed' where LoanStatusReport='Running';

    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;

alter table pmms_loans.new_loan_appstore modify column trn_id int unique NOT NULL  ;
alter table pmms_loans.new_loan_appstore1 modify column trn_id int unique NOT NULL ;
alter table pmms_loans.new_loan_appstoreamort modify column master1_id int NOT NULL;
alter table pmms.loandisburserepaystatement modify column loanTrnId int  NOT NULL;



alter table pmms_loans.new_loan_appstore modify column OtherGroups2 int  NOT NULL  ;
alter table pmms_loans.new_loan_appstore1 modify column OtherGroups2 int  NOT NULL ;
END//

 DELIMITER ;


-- CALL implementingChanges();







 
DROP PROCEDURE IF EXISTS implementingChangesPostChange;
DELIMITER //
CREATE PROCEDURE implementingChangesPostChange() READS SQL DATA 
BEGIN

DECLARE l_done,privTrnId INT;
DECLARE loanId,theStatus VARCHAR(30);
DECLARE theTrnDate DATE;
   
DECLARE forloanId CURSOR FOR SELECT trn_id   FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' OR loan_cycle_status='Combined';
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;




-- ALTER TABLE pmms.sequencenumbers modify column  trn_id enum('1') NOT NULL;


 OPEN forloanId;

loanId_loop: LOOP 
 FETCH forloanId into loanId;

SELECT loan_cycle_status INTO theStatus FROM new_loan_appstore WHERE trn_id=loanId;

IF theStatus='Renewed' THEN

SELECT trn_id,DATE_SUB(instalment_start_date,INTERVAL 1 DAY) INTO privTrnId,theTrnDate FROM new_loan_appstore WHERE applicant_account_number=(SELECT applicant_account_number FROM new_loan_appstore WHERE trn_id=loanId  LIMIT 1) AND loan_cycle_status='Completed' ORDER BY trn_id DESC LIMIT 1;

END IF;

IF theStatus='Disbursed' THEN

SELECT trn_id,DATE_SUB(instalment_start_date,INTERVAL 1 DAY) INTO privTrnId,theTrnDate FROM new_loan_appstore WHERE trn_id=loanId;

END IF;

 IF l_done=1 THEN

LEAVE loanId_loop;

 END IF;

SELECT privTrnId,loanId,theTrnDate;


 UPDATE pmms_loans.new_loan_appstore SET authoriser_id=privTrnId,instalment_start_date=theTrnDate where trn_id=loanId;

 UPDATE pmms_loans.new_loan_appstore1 SET  authoriser_id=privTrnId,instalment_start_date=theTrnDate  where trn_id=loanId;



    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;


END//

 DELIMITER ;


-- CALL implementingChangesPostChange();






DROP PROCEDURE IF EXISTS changeDueDate;

DELIMITER //

CREATE PROCEDURE changeDueDate() READS SQL DATA BEGIN

 DECLARE loanId,theTenure VARCHAR(30);

  /* DECLARE numberOfIds INTEGER; */

 DECLARE l_done INTEGER DEFAULT 0;

 DECLARE originalDueDate DATE;
/* lastDate, */
  /* DECLARE InterestRate,princinpalRemaining,totalComputedInterest Double; */

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';
 

 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 OPEN forSelectingLoanIds;


accounts_loop: LOOP 

 FETCH forSelectingLoanIds into loanId;

 /* SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanId; */
SELECT loan_tenure INTO theTenure FROM new_loan_appstore1 WHERE loan_id=loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


IF theTenure= '1.0 MONTHS' THEN 

SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanId;

/* SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanId; */


UPDATE new_loan_appstore SET instalment_next_due_date=originalDueDate WHERE loan_id=loanId;

UPDATE new_loan_appstore1 SET instalment_next_due_date=originalDueDate WHERE loan_id=loanId;

END IF;


SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingLoanIds;

END //

DELIMITER ;

-- CALL changeDueDate();



 DROP PROCEDURE IF EXISTS getMoneyLendMethod;   
DELIMITER $$
CREATE  PROCEDURE getMoneyLendMethod()
BEGIN
DECLARE theMethod INT;

SELECT methodStatus INTO theMethod  FROM interestMethod ;

IF ISNULL(theMethod) THEN
SET theMethod=0;
END IF;

SELECT theMethod;
END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS InitialInterestManagement;

DELIMITER //

CREATE PROCEDURE InitialInterestManagement() READS SQL DATA BEGIN
DECLARE loanIdZ VARCHAR(30); /*This is the unique itentfier for an active loan */

DECLARE theActualMethod INT;

DECLARE l_done INTEGER DEFAULT 0;  /* Variable controlling the cusor */


DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore1 WHERE loan_cycle_status='Disbursed' AND ( loan_tenure='1.0 MONTHS' OR loan_tenure= '1 MONTHS' ); /*  cursor for iterating through each borrower's account */
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1; /*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingLoanIds; /* Open the cursor holding loan ids for each customer */


accounts_loop: LOOP  /*Loop through the loanIds */

FETCH forSelectingLoanIds into loanIdZ; /* Pick the loan id into the variable loanIdz */
-- SELECT loanIdZ;
IF l_done=1 THEN /* check whether the cusor sitll holds more values and if not terminate loop */
LEAVE accounts_loop; /* */
 END IF;  /* */
SELECT loanIdZ;

SELECT IndividualMethod INTO theActualMethod FROM individualMethod WHERE loanIdX=loanIdZ;

SELECT theActualMethod;

IF theActualMethod=2 THEN
CALL InterestManagementForLendersCompounded(loanIdZ);
END IF;


IF theActualMethod=1 THEN

CALL InterestManagementForLendersNonCompounded(loanIdZ);

END IF;

SET l_done=0;

END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;
END //

DELIMITER ;



-- DROP TABLE individualMethod;
-- -----------------------------------------------------
-- Table individualMethod
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS individualMethod (
 individualMethodId INT NOT NULL AUTO_INCREMENT,
 
  IndividualMethod  INT (11) NOT NULL DEFAULT 1,
  
   loanIdX varchar(50) NOT NULL DEFAULT '0.0',

  PRIMARY KEY (individualMethodId)
  
) ENGINE = InnoDB AUTO_INCREMENT = 1200 DEFAULT CHARACTER SET = utf8;



DROP PROCEDURE IF EXISTS createTheIndividualMethod;

DELIMITER //

CREATE PROCEDURE createTheIndividualMethod(IN loanIdx1 VARCHAR(60)) READS SQL DATA BEGIN
DECLARE idThere,theMethodX INT;

SELECT COUNT(IndividualMethod) INTO idThere FROM individualMethod WHERE loanIdX=loanIdx1;
SELECT  methodStatus INTO theMethodX FROM interestmethod;

IF idThere>0 THEN
UPDATE individualMethod SET IndividualMethod=theMethodX WHERE loanIdX= loanIdx1;
END IF;

IF idThere<=0 THEN
INSERT INTO individualMethod VALUES(NULL,theMethodX,loanIdx1);

END IF;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS InterestManagementForLendersNonCompounded;

DELIMITER //

CREATE PROCEDURE InterestManagementForLendersNonCompounded(IN loanIdZ VARCHAR(60)) READS SQL DATA BEGIN

 /* DECLARE loanIdZ VARCHAR(30); This is the unique itentfier for an active loan */


 /* DECLARE l_done INTEGER DEFAULT 0;  Variable controlling the cusor */

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/

  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,TotalprincinpalRemainingX,TotalinterestRemainingX,NEWTotalprincinpalRemainingX,totalInterest,
  totalCompuInteresRemaing Double; /*  InterestRate is the rate used---*/

 /* DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed'; cursor for iterating through each borrower's account */
 


 /* DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;Decclare the variable for testing whether the cursor has ended */
-- 1	LoanPyt	P&I	05502012410	01128000110	NAKATO FAUSTA RITAH NALWADDA 0708872755's Loan Payment	57,200	0	0	01/10/2022

 /* OPEN forSelectingLoanIds; Open the cursor holding loan ids for each customer */


/* accounts_loop: LOOP Loop through the loanIds */

 /* FETCH forSelectingLoanIds into loanIdZ;Pick the loan id into the variable loanIdz */
--  SELECT loanIdZ; 
 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore1 WHERE loan_id=loanIdZ;/*Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 /* IF l_done=1 THENcheck whether the cusor sitll holds more values and if not terminate loop */

/* LEAVE accounts_loop; */

 /* END IF; */

-- SELECT loanIdZ,@theTenure;

IF @theTenure= '1.0 MONTHS' OR @theTenure= '1 MONTHS' THEN /*  Only single monthly isntalment loans should be considered*/

-- SELECT 'AM IN';

Date_loop: LOOP /* Loop through the due dates since the last duedate*/
SET @Ended=0;
/*  SELECT lastDate;  *//*Testing*/

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining,TotalInterestRemaining INTO lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX FROM new_loan_appstore WHERE loan_id=loanIdZ; /* The due date since the last instalment is stored in the instalment_next_due_date column*/
 SELECT  lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX; 
SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;/* The instalment due date is the last due date*/

SELECT interestinvoRemaining INTO totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;/* The last interest computed*/

-- SELECT totalInterest ,loanIdZ,lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX;
-- select lastDate;
IF lastDate>=current_date() THEN /* Test whether the arrears last date is more than today's date*/

SET @Ended=1;

LEAVE Date_loop;

END IF;
-- 70045	05502000910	RUTH KIGOZI 0772397777	422.5072455050587	0	0	21125.362275252934	21547.86952075799	2022-10-08

/* INSERT INTO new_loan_appstore2 SELECT * FROM new_loan_appstore WHERE loan_id=loanIdZ;

INSERT INTO new_loan_appstoreamort2 SELECT * FROM new_loan_appstoreamort WHERE master2_id=loanIdZ; */

IF @Ended=0 THEN 

SET @computableAmount=TotalprincinpalRemainingX;
/* +TotalinterestRemainingX */
SET interestInveolved=((InterestRate*@computableAmount)/1200); /* Compute the insterest using the remaining princimpal amount*/



SET totalInterest=totalInterest+interestInveolved; /* Compute the total interest*/


SET NEWTotalprincinpalRemainingX=@computableAmount;


SET @pureDate=lastDate;  
 
 SELECT @pureDat;

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;


CALL updateAccountsAfterCompounded(loanIdZ,lastDate,interestInveolved,TotalprincinpalRemainingX,TotalinterestRemainingX);/* Update the original loan schedule*/
-- SELECT 'AFTER',loanIdZ,@pureDate,TotalprincinpalRemainingX,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest;

INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,TotalprincinpalRemainingX,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest,'Pending'); 

-- SET loanIdZ=NULL,lastDate=NULL,interestInveolved=NULL,TotalprincinpalRemainingX=NULL,TotalinterestRemainingX=NULL;

END IF;

END LOOP Date_loop;

END IF;



/* SET l_done=0; */

 /* END LOOP accounts_loop; */

 /* CLOSE forSelectingLoanIds; */

END //

DELIMITER ;

/* 

DROP EVENT IF EXISTS `change_daily_interest`;

CREATE  EVENT `change_daily_interest` ON SCHEDULE EVERY 10 SECOND STARTS CURRENT_TIMESTAMP() ON COMPLETION NOT PRESERVE ENABLE DO CALL InterestManagementForLendersCompounded(); */

/* DROP PROCEDURE IF EXISTS InterestManagementForLenders; */


DROP PROCEDURE IF EXISTS InterestManagementForLendersCompounded;

DELIMITER //

CREATE PROCEDURE InterestManagementForLendersCompounded(IN loanIdZ VARCHAR(60)) READS SQL DATA BEGIN

 /* DECLARE loanIdZ VARCHAR(30); This is the unique itentfier for an active loan */


 /* DECLARE l_done INTEGER DEFAULT 0;  Variable controlling the cusor */

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/

  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,TotalprincinpalRemainingX,TotalinterestRemainingX,NEWTotalprincinpalRemainingX,totalInterest,
  totalCompuInteresRemaing Double; /*  InterestRate is the rate used---*/

 /* DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';  */
 
 /* cursor for iterating through each borrower's account*/
 


 /* DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1; */
 /*Decclare the variable for testing whether the cursor has ended */


 /* OPEN forSelectingLoanIds;  */
 /* Open the cursor holding loan ids for each customer*/


/* accounts_loop: LOOP  */
/*Loop through the loanIds */

 /* FETCH forSelectingLoanIds into loanIdZ; */
 /*Pick the loan id into the variable loanIdz */
-- SELECT loanIdZ;
 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore1 WHERE loan_id=loanIdZ;/*Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 /* IF l_done=1 THEN */
 /*check whether the cusor sitll holds more values and if not terminate loop*/

/* LEAVE accounts_loop; */

 /* END IF; */


IF @theTenure=  '1.0 MONTHS' OR @theTenure= '1 MONTHS'  THEN /*  Only single monthly isntalment loans should be considered*/



Date_loop: LOOP /* Loop through the due dates since the last duedate*/
SET @Ended=0;
/*  SELECT lastDate;  *//*Testing*/

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining,TotalInterestRemaining INTO lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX FROM new_loan_appstore WHERE loan_id=loanIdZ; /* The due date since the last instalment is stored in the instalment_next_due_date column*/
-- SELECT  lastDate,InterestRate,TotalprincinpalRemaining;
SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;/* The instalment due date is the last due date*/

SELECT interestinvoRemaining INTO totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;/* The last interest computed*/

SELECT totalInterest ,loanIdZ,lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX;

IF lastDate>=current_date() THEN /* Test whether the arrears last date is more than today's date*/



SET @Ended=1;

LEAVE Date_loop;

END IF;


/* INSERT INTO new_loan_appstore2 SELECT * FROM new_loan_appstore WHERE loan_id=loanIdZ;

INSERT INTO new_loan_appstoreamort2 SELECT * FROM new_loan_appstoreamort WHERE master2_id=loanIdZ; */

IF @Ended=0 THEN 

SET @computableAmount=TotalprincinpalRemainingX+TotalinterestRemainingX;

SET interestInveolved=((InterestRate*@computableAmount)/1200); /* Compute the insterest using the remaining princimpal amount*/



SET totalInterest=totalInterest+interestInveolved; /* Compute the total interest*/


-- SET NEWTotalprincinpalRemainingX=@computableAmount;


SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;


CALL updateAccountsAfterCompounded(loanIdZ,lastDate,interestInveolved,TotalprincinpalRemainingX,TotalinterestRemainingX);/* Update the original loan schedule*/


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,TotalprincinpalRemainingX,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest,'Pending'); 

END IF;

END LOOP Date_loop;

END IF;



/* SET l_done=0; */

 /* END LOOP accounts_loop; */

 /* CLOSE forSelectingLoanIds; */

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updateAccountsAfterCompounded;

DELIMITER //

/* This procedure updates the original loan schedule*/

CREATE PROCEDURE updateAccountsAfterCompounded(IN loanId VARCHAR(30),IN newDateDue DATE,IN interestComputed Double,IN princimpalRemaining Double,IN originalInterest Double) READS SQL DATA BEGIN

/* IN loanId VARCHAR(30)=This is the loan id that identifies each active loan uniquely */
/* IN newDateDue DATE=This is the Due date for the newly created interest instalment */
/* IN interestComputed Double=This is the interest generated for the new due date */
/* IN princimpalRemaining Double=This is the remaining principal amount */

 /* SELECT loanId,interestComputed,princimpalRemaining,newDateDue; For testing purposes to see the value of interestcomputed passed*/

/* The idea is to get the already existing values for the initial interest instalment,the actual instalment remaining,interest remaining,intial instalmet then we change these values with the interest computed */

--  SELECT 'AM IN=',loanId,interestComputed; 




SET @sql_text1 = concat(CAST("SELECT princimpal_amount,interest_amount,instalment_amount,InterestRemaing,PrincipalRemaining,InstalmentRemaining INTO
 @principalAmount,
 @interestAmount, 
   @instalmentAmount,
 @interestRemaining,

   @principalRemaining,
    @instalmentRemaining
 FROM new_loan_appstoreamort WHERE master2_id='" AS CHAR CHARACTER SET utf8),loanId, CAST("'" AS CHAR CHARACTER SET utf8));
/* interest_amount=Initial interest instalment created at disbursement =@interestAmount*/
/* InstalmentRemaining=The total instalment remaining after paying off the loan instalment=@instalmentRemaining */
/*InterestRemaing=  The remaining interest when the instalment is paid,mostly when partly paid=@interestRemaining*/
/*instalment_amount=  The initial instalment amount */
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

/* Also we need to change the total interest,total instalment amount,totalinterest remaining */


SELECT princimpal_amount,total_interest, total_loanAmount,balance_due,instalment_amount,TotalInterestRemaining,TotalPrincipalRemaining INTO 
@TotalprincipalAmount,
 @totalInterestAmount,
   @totalLoanAmount,
   @balanceDue,
   @instalmentAmount,
  
@totalInterestRemaining ,
@totalPrincipalRemaining

FROM new_loan_appstore WHERE loan_id=loanId;




SET @sql_textX = concat(CAST("SELECT TrnId,ExpectedInterest,ExpectedTotalAmount,InterestBalance, LoanBalance INTO @idL,@ExpectedInterest,@ExpectedTotalAmount, @intBal,@lBalance FROM pmms.loandisburserepaystatement WHERE LoanId='" AS CHAR CHARACTER SET utf8),loanId, CAST("' AND LoanStatusReport='Disbursed' ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
/* interest_amount=Initial interest instalment created at disbursement =@interestAmount*/
/* InstalmentRemaining=The total instalment remaining after paying off the loan instalment=@instalmentRemaining */
/*InterestRemaing=  The remaining interest when the instalment is paid,mostly when partly paid=@interestRemaining*/
/*instalment_amount=  The initial instalment amount */
  PREPARE stmtX FROM @sql_textX;
  EXECUTE stmtX;
DROP PREPARE stmtX;


-- SELECT TrnId,InterestBalance, LoanBalance INTO @idL, @intBal,@lBalance FROM pmms.loandisburserepaystatement WHERE LoanId=loanId AND LoanStatusReport='Disbursed' ORDER BY TrnId DESC LIMIT 1;

-- SELECT @idL, @intBal,@lBalance;
/* Normally if the result set is empty, mysql will return an empty set with null values for each element,

 to avoid null pointers we initalise our variables with expected default values */
 
IF  ISNULL (@principalAmount) THEN 

SET @principalAmount=0; 

END IF;

IF   ISNULL(@interestAmount) THEN SET  @interestAmount=0; END IF;

IF   ISNULL(@instalmentAmount) THEN SET   @instalmentAmount=0; END IF;

IF   ISNULL(@interestRemaining) THEN SET  @interestRemaining=0; END IF;

IF ISNULL(@principalRemaining) THEN SET @principalRemaining=0; END IF;

IF  ISNULL(@instalmentRemaining) THEN SET @instalmentRemaining=0; END IF;

IF  ISNULL(@TotalprincipalAmount) THEN SET @TotalprincipalAmount=0; END IF;

IF  ISNULL(@totalInterestAmount) THEN SET @totalInterestAmount=0; END IF;

IF  ISNULL(@totalLoanAmount) THEN SET  @totalLoanAmount=0; END IF;

IF  ISNULL(@balanceDue) THEN SET  @balanceDue=0; END IF;

IF   ISNULL(@instalmentAmount) THEN SET  @instalmentAmount=0; END IF;


IF   ISNULL(@totalInterestRemaining) THEN SET  @totalInterestRemaining=0; END IF;

IF   ISNULL(@totalInterestRemaining) THEN SET  @totalPrincipalRemaining=0; END IF;

IF ISNULL(@intBal) THEN SET @intBal=0; END IF;

IF ISNULL(@lBalance) THEN SET @lBalance=0; END IF;

IF ISNULL(@lBalance) THEN SET @lBalance=0; END IF;

IF ISNULL(@ExpectedInterest) THEN SET @ExpectedInterest=0; END IF;

IF ISNULL(@ExpectedTotalAmount) THEN SET @ExpectedTotalAmount=0; END IF;

-- @principalAmount = @principalAmount + originalInterest,
-- + originalInterest
SET @principalAmount = @principalAmount ,

@interestAmount = @interestAmount + interestComputed,

 @instalmentAmount= princimpalRemaining + interestComputed+originalInterest,

@interestRemaining = @interestRemaining + interestComputed,

-- @interestRemaining =  interestComputed,

@principalRemaining = princimpalRemaining,

@instalmentRemaining = princimpalRemaining + interestComputed+originalInterest,

-- @instalmentRemaining = princimpalRemaining + interestComputed,
-- + originalInterest
@TotalprincipalAmount = @TotalprincipalAmount ,

@totalInterestAmount = @totalInterestAmount + interestComputed,

-- @totalInterestAmount =  interestComputed,

 @totalLoanAmount = @totalLoanAmount + originalInterest + interestComputed,

--  @totalLoanAmount = @totalLoanAmount + interestComputed,

 
@balanceDue = princimpalRemaining + interestComputed+originalInterest,

-- @balanceDue = princimpalRemaining + interestComputed,

@instalmentAmount=@instalmentRemaining,

@totalInterestRemaining =  interestComputed+ originalInterest,

@totalPrincipalRemaining = princimpalRemaining,

@intBal = @intBal +interestComputed, @lBalance = @lBalance + interestComputed;




/* SET @totalInstalmentsRemaining=@totalInstalmentsRemaining+interestComputed,
@totalInterestRemaining=@totalInterestRemaining+interestComputed,
@totalInterestAmount=@totalInterestAmount+interestComputed; */


--  SELECT @principalAmount,@interestAmount, @instalmentAmount,@interestRemaining,@principalRemaining,@instalmentRemaining,@TotalprincipalAmount,@totalInterestAmount, @totalLoanAmount,@balanceDue,@instalmentAmount ,@totalInterestRemaining,@totalPrincipalRemaining,@intBal, @lBalance,@idL;

-- princimpal_amount=@principalAmount,
UPDATE new_loan_appstoreamort SET 

interest_amount=@interestAmount,
instalment_amount=@instalmentAmount,
InterestRemaing=@interestRemaining,

InstalmentRemaining=@instalmentRemaining 
 WHERE master2_id=loanId;

-- princimpal_amount=@TotalprincipalAmount,
-- total_interest=@totalInterestAmount,
-- PrincipalRemaining=@principalRemaining,
UPDATE new_loan_appstore SET 
total_interest=@totalInterestAmount,
instalment_next_due_date=newDateDue,
total_loanAmount=@totalLoanAmount,
balance_due=@balanceDue,
instalment_amount=@instalmentAmount,
TotalInterestRemaining =@totalInterestRemaining


 WHERE loan_id=loanId;

-- TotalPrincipalRemaining=@totalPrincipalRemaining


-- princimpal_amount=@TotalprincipalAmount,
-- total_interest=@totalInterestAmount,

UPDATE new_loan_appstore1 SET 

instalment_next_due_date=newDateDue,
total_loanAmount=@totalLoanAmount,
balance_due=@balanceDue,
instalment_amount=@instalmentAmount,
TotalInterestRemaining =@totalInterestRemaining,
total_interest=@totalInterestAmount
 WHERE loan_id=loanId;

-- ExpectedInterest,ExpectedTotalAmount

IF @ExpectedTotalAmount>0.0 THEN

SET @ExpectedTotalAmount=@ExpectedTotalAmount+ interestComputed,@ExpectedInterest=@ExpectedInterest+interestComputed;

SET @sql_textXA1 = concat(CAST(" UPDATE pmms.loandisburserepaystatement SET 
ExpectedInterest= " AS CHAR CHARACTER SET utf8),@ExpectedInterest,
CAST(",ExpectedTotalAmount= " AS CHAR CHARACTER SET utf8),@ExpectedTotalAmount,
CAST(" WHERE TrnId= " AS CHAR CHARACTER SET utf8),@idL);
-- SELECT @sql_textXA1 ;
  PREPARE stmtXA1 FROM @sql_textXA1;
  EXECUTE stmtXA1;
DROP PREPARE stmtXA1;
END IF;

SET @sql_textXA = concat(CAST(" UPDATE pmms.loandisburserepaystatement SET 
InterestBalance= " AS CHAR CHARACTER SET utf8),@intBal,
CAST(",LoanBalance= " AS CHAR CHARACTER SET utf8),@lBalance,
CAST(" WHERE TrnId= " AS CHAR CHARACTER SET utf8),@idL);
-- SELECT @sql_textXA ;
  PREPARE stmtXA FROM @sql_textXA;
  EXECUTE stmtXA;
DROP PREPARE stmtXA;

-- SELECT @intBal,@lBalance,@idL;

-- SET @intBal=NULL,@lBalance=NULL,@idL=NULL;

END //

DELIMITER ;





-- DROP PROCEDURE IF EXISTS DateManagementForLenders;

-- DELIMITER //

-- CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN /*One of the most important idea to note is that when a payment interest instalment is made, the interest due date has to be shifted one date ahead*/

-- DECLARE numberOfIds INTEGER;


--  SELECT loan_tenure INTO @theTenure FROM new_loan_appstore1 WHERE loan_id=loanIdUsed;

-- IF @theTenure= '1.0 MONTHS' OR @theTenure= '1 MONTHS' THEN

-- SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

-- SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;


-- interestPayment_Loop: LOOP


-- SELECT TrnId,InterestInvoRemaining, InterestPaidIn,InterestInvolved INTO 
-- @theId,
-- @interestRemaining,
-- @interestPaidnow,
-- @InterestInveo 
-- FROM interestcomputed WHERE loanId=loanIdUsed AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;


--  SET @inteDiff=InterestPaid-@interestRemaining;
 
--  IF @inteDiff<=0 THEN 
 

 
 
--  IF @inteDiff=0 THEN
 
-- /*  SET @A=10;
--   SELECT @A; */
--  CALL newDateConverted(@originalDueDate);
 
--  UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

--  SET InterestPaid=0.0;
 
--  ELSEIF @inteDiff<0 THEN
-- /*  SET @B=10;
--   SELECT @B; */
--  SET @IntRemai=@interestRemaining-InterestPaid;
--  SET @intPai=@interestPaidnow+InterestPaid;
  
--  UPDATE interestComputed SET InterestInvoRemaining=@IntRemai,InterestPaidIn=@intPai WHERE TrnId=@theId;

--  SET InterestPaid=0.0;
 
--  END IF;
 
--  ELSEIF @inteDiff>0 THEN
--  /* SET @C=10;
--   SELECT @C; */
--  SET  @IntRemai=@interestRemaining-InterestPaid;
--  SET @intPai=@interestPaidnow+InterestPaid;
  
--  UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

--  SET InterestPaid=InterestPaid-@interestRemaining;
--  CALL newDateConverted(@originalDueDate);
 
--  END IF;






-- IF InterestPaid<=0.0 THEN

-- /* 
--   SELECT @originalDueDate; */
-- UPDATE new_loan_appstoreamort SET instalment_due_date=@originalDueDate WHERE master2_id=loanIdUsed;

-- LEAVE interestPayment_Loop;

-- END IF;


-- END LOOP interestPayment_Loop;

-- END IF;

-- END //

-- DELIMITER ;



DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid DOUBLE, IN loanIdUsed VARCHAR(30))
READS SQL DATA
BEGIN
    DECLARE numberOfIds INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE loop_counter INT DEFAULT 0;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    SELECT loan_tenure INTO @theTenure FROM new_loan_appstore1 WHERE loan_id = loanIdUsed;

    IF @theTenure = '1.0 MONTHS' OR @theTenure = '1 MONTHS' THEN
        SELECT interest_rate, TotalPrincipalRemaining INTO @InterestRate, @princinpalRemaining 
        FROM new_loan_appstore WHERE loan_id = loanIdUsed;

        SELECT instalment_due_date INTO @originalDueDate 
        FROM new_loan_appstoreamort WHERE master2_id = loanIdUsed;

        interestPayment_Loop: LOOP
            SET loop_counter = loop_counter + 1;
            IF loop_counter > 1000 THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Infinite loop detected in DateManagementForLenders';
            END IF;

            SELECT TrnId, InterestInvoRemaining, InterestPaidIn, InterestInvolved
            INTO @theId, @interestRemaining, @interestPaidnow, @InterestInveo
            FROM interestcomputed
            WHERE loanId = loanIdUsed AND loanStatusI = 'Pending'
            ORDER BY TrnId ASC LIMIT 1;

            IF done THEN
                LEAVE interestPayment_Loop;
            END IF;

            SET @inteDiff = InterestPaid - @interestRemaining;

            IF @inteDiff <= 0 THEN
                IF @inteDiff = 0 THEN
                    CALL newDateConverted(@originalDueDate);
                    UPDATE interestComputed 
                    SET InterestInvoRemaining = 0.0, InterestPaidIn = @InterestInveo, loanStatusI = 'Paid' 
                    WHERE TrnId = @theId;
                    SET InterestPaid = 0.0;
                ELSEIF @inteDiff < 0 THEN
                    SET @IntRemai = @interestRemaining - InterestPaid;
                    SET @intPai = @interestPaidnow + InterestPaid;
                    UPDATE interestComputed 
                    SET InterestInvoRemaining = @IntRemai, InterestPaidIn = @intPai 
                    WHERE TrnId = @theId;
                    SET InterestPaid = 0.0;
                END IF;
            ELSE
                SET @IntRemai = @interestRemaining - InterestPaid;
                SET @intPai = @interestPaidnow + InterestPaid;
                UPDATE interestComputed 
                SET InterestInvoRemaining = 0.0, InterestPaidIn = @InterestInveo, loanStatusI = 'Paid' 
                WHERE TrnId = @theId;
                SET InterestPaid = InterestPaid - @interestRemaining;
                CALL newDateConverted(@originalDueDate);
            END IF;

            IF InterestPaid <= 0.0 THEN
                UPDATE new_loan_appstoreamort 
                SET instalment_due_date = @originalDueDate 
                WHERE master2_id = loanIdUsed;
                LEAVE interestPayment_Loop;
            END IF;
        END LOOP interestPayment_Loop;
    END IF;
END //

DELIMITER ;



DROP PROCEDURE IF EXISTS createFirstInterestIntalment;

DELIMITER //

CREATE PROCEDURE createFirstInterestIntalment(IN loanIdUsed VARCHAR(30),IN dueDate DATE,IN princinpalRemaining DOUBLE,IN interestInvo DOUBLE) READS SQL DATA BEGIN

INSERT INTO interestComputed VALUES(null,loanIdUsed,dueDate,princinpalRemaining,interestInvo,0.0,interestInvo,interestInvo,0.0,interestInvo,'Pending'); 

END //

DELIMITER ;



-- *VNC IP & Port:* 161.97.152.187:63125

-- *Server Username:* Application Support
-- *Password:* Feel@H0me





-- vHBD3FUN


-- SELECT SloanTrnId;

-- DROP TABLE IF EXISTS businessDetails;

CREATE  TABLE IF NOT EXISTS  businessDetails(
id INTEGER NOT NULL AUTO_INCREMENT,
businessName VARCHAR(100) NOT NULL,
businessDescribe VARCHAR(30) NULL,
 PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO businessDetails VALUES(null,"BODA BODA","CUSTOMERS INVOLVED IN RIDING BODABODAS");



DROP TABLE IF EXISTS oneTimeUpdate;
CREATE  TABLE  IF NOT EXISTS   oneTimeUpdate(
id INTEGER NOT NULL,
 PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;  

INSERT INTO oneTimeUpdate VALUES(2);


-- 1	LoanPyt	P&I	05502003910	01128000110	ASASIRA MICHEAL 0700802107's Loan Payment	4,000	0	0	09/04/2025
-- 2	Savings	P&I	01123000110	05502001810	MUKIMBIRI GODFREY's Savings	10,000	0	0	0


DROP PROCEDURE IF EXISTS grossLoanPortfolio;

DELIMITER //

-- Credits	MAKANGA CHARLES 0702696171		Customer Deposits	0
-- Debits	MOMO PAY	01122000610	Bank Balance	.0
CREATE PROCEDURE grossLoanPortfolio() READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 


DROP TABLE IF EXISTS temp_grossPortFolio;

CREATE TEMPORARY  TABLE temp_grossPortFolio(id INTEGER,temp_Borrower VARCHAR(200),temp_outStandingPrici DOUBLE,temp_OutStandingInterst DOUBLE,temp_OutStandingAccum DOUBLE,temp_OutStandingPenalty VARCHAR(200),temp_OutStandingTotal VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 SELECT  applicant_account_name, TotalPrincipalRemaining,TotalInterestRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining  INTO @borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty FROM new_loan_appstore WHERE trn_id=txnIdS;
 
 SET Ids=Ids+1;
   
   SET @totalAll=@remPrinc+@remInt+@remAccumI+@remaPenalty;
   
 INSERT INTO temp_grossPortFolio VALUES(Ids,@borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty,@totalAll);

 
SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;

--  SELECT  SUM(temp_outStandingPrici) ,SUM(temp_OutStandingInterst),SUM(temp_OutStandingAccum) ,SUM(temp_OutStandingPenalty) ,SUM(temp_OutStandingTotal) INTO @TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll FROM temp_grossPortFolio;
 
-- INSERT INTO temp_grossPortFolio VALUES(0,'Total',@TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll);

SELECT id,temp_Borrower,temp_outStandingPrici,temp_OutStandingInterst,temp_OutStandingAccum,temp_OutStandingPenalty,temp_OutStandingTotal  FROM temp_grossPortFolio ORDER BY id, temp_Borrower;


END //

DELIMITER ;

-- CALL grossLoanPortfolio();





DROP PROCEDURE IF EXISTS grossLoanPortfolioPdf;

DELIMITER //



CREATE PROCEDURE grossLoanPortfolioPdf() READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 


DROP TABLE IF EXISTS temp_grossPortFolio;

CREATE TEMPORARY  TABLE temp_grossPortFolio(id INTEGER,temp_Borrower VARCHAR(200),temp_outStandingPrici DOUBLE,temp_OutStandingInterst DOUBLE,temp_OutStandingAccum DOUBLE,temp_OutStandingPenalty VARCHAR(200),temp_OutStandingTotal VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 SELECT  applicant_account_name, TotalPrincipalRemaining,TotalInterestRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining  INTO @borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty FROM new_loan_appstore WHERE trn_id=txnIdS;
 
 SET Ids=Ids+1;
   
   SET @totalAll=@remPrinc+@remInt+@remAccumI+@remaPenalty;
   
 INSERT INTO temp_grossPortFolio VALUES(Ids,@borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty,@totalAll);

 
SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;

 SELECT  SUM(temp_outStandingPrici) ,SUM(temp_OutStandingInterst),SUM(temp_OutStandingAccum) ,SUM(temp_OutStandingPenalty) ,SUM(temp_OutStandingTotal) INTO @TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll FROM temp_grossPortFolio;
 
INSERT INTO temp_grossPortFolio VALUES(0,'Total',@TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll);

SELECT id,temp_Borrower,temp_outStandingPrici,temp_OutStandingInterst,temp_OutStandingAccum,temp_OutStandingPenalty,temp_OutStandingTotal  FROM temp_grossPortFolio ORDER BY id, temp_Borrower;


END //

DELIMITER ;




/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysis;

DELIMITER ##

CREATE PROCEDURE   agingAnalysis()
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors,TrnId INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus,gaurantorName1,gaurantorContact1,gaurantorContact2,gaurantorName2 VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis1y(id_1y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1y));

DROP TABLE IF EXISTS aging_loan_analysis1;
 
CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, loan_deadline VARCHAR(60),PRIMARY KEY (id_1));

DROP TABLE IF EXISTS aging_loan_analysis2;
 
CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3;
 
CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4;
 
CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;
 
CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;
 
CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;
 
CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60),loan_deadline VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis2x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis2y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis2y(id_2y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_2y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis3y(id_3y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_3y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis4y(id_4y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_4y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis5y(id_5y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_5y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis6y(id_6y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8x;
 
CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8y;
 
CREATE TEMPORARY  TABLE aging_loan_analysis8y(id_8y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8y))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;
 
CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis9;
 
CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_9))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;
 
 accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;

--  IF loanId="newloan05502002010" THEN
--  SELECT loanId;
-- END IF;


 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
-- SELECT loanId;
SELECT pl.applicant_account_name,m.mobile1,pl.trn_id,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.loan_cycle_status INTO customerName, customerContactNumber,TrnId,TrnDate,remainport,princeremain,interestRem,p_remain,theLoanStatus FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;

-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain,loanId;

SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';


SELECT COUNT(id) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId=TrnId;
-- SELECT loanId,numberOfGaurantors;
IF numberOfGaurantors=0 THEN

SET gaurantorName1='-',gaurantorContact1='-',gaurantorName2='-',gaurantorContact2='-';
END IF;

IF numberOfGaurantors=1 THEN
SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=TrnId;

SET gaurantorName2='-',gaurantorContact2='-';
END IF;



IF numberOfGaurantors=2 THEN
-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id ASC LIMIT 1;
SET @sql_text2 = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName1,@gaurantorContact1 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),TrnId,CAST(" ORDER BY id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;



-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName2,gaurantorContact2 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id DESC LIMIT 1;


SET @sql_text2X = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName2,@gaurantorContact2 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),TrnId,CAST(" ORDER BY id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X FROM @sql_text2X;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


SELECT @gaurantorName1,@gaurantorContact1 INTO gaurantorName1,gaurantorContact1;
SELECT @gaurantorName2,@gaurantorContact2 INTO gaurantorName2,gaurantorContact2;
END IF;
-- SELECT @gaurantorName1,@gaurantorContact1,@gaurantorName2,@gaurantorContact2;
 SET ID=ID+1;

 IF ISNULL(customerContactNumber) THEN
SET customerContactNumber="-";
 END IF;

 IF ISNULL(remainport) THEN
SET remainport=0;
 END IF;

  IF ISNULL(princeremain) THEN
SET princeremain=0;
 END IF;

 IF ISNULL(interestRem) THEN
SET interestRem=0;
 END IF;

  IF ISNULL(p_remain) THEN
SET p_remain=0;
 END IF;

  IF ISNULL(i_remain) THEN
SET i_remain=0;
 END IF;

   IF ISNULL(arrears) THEN
SET arrears=0;
 END IF;
 

SELECT DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y') INTO @INST;

-- SELECT @INST;

  IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
 END IF;

IF theLoanStatus='Disbursed' THEN

--  IF customerName="MUTEBI BASHIRU 0773591734" THEN
--  SELECT ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'),loanId;
-- END IF;

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;


IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;

IF theLoanStatus='Combined' THEN

INSERT INTO aging_loan_analysis1y VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;

     SET l_done=0;
 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

SELECT COUNT(id_1) INTO @port0  FROM aging_loan_analysis1 ;
 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;



 
IF @port1 >0 THEN

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,
  loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
END IF;

IF @port2 >0 THEN

  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
 
END IF;

IF @port3 >0 THEN


    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port4 >0 THEN

    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;

IF @port5 >0 THEN

    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
END IF;


SELECT COUNT(id_1x) INTO @port0x  FROM aging_loan_analysis1x;
 SELECT COUNT(id_1x) INTO @port1x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

 SELECT COUNT(id_1x) INTO @port2x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  SELECT COUNT(id_1x) INTO @port3x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

   SELECT COUNT(id_1x) INTO @port4x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1x) INTO @port5x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360;


IF @port1x >0 THEN

  INSERT INTO  aging_loan_analysis2x( 
  id_2x,
  customer_name ,
  customer_contact ,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port2x >0 THEN

  
  INSERT INTO  aging_loan_analysis3x( 
  id_3x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port3x >0 THEN
 
    INSERT INTO  aging_loan_analysis4x( 
  id_4x,
  customer_name ,
  customer_contact ,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
END IF;



IF @port4x >0 THEN

    INSERT INTO  aging_loan_analysis5x( 
  id_5x,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
  
 END IF;

 IF @port5x >0 THEN
 
     INSERT INTO  aging_loan_analysis6x( 
   id_6x,
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
 interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 SELECT COUNT(id_1y) INTO @port0y  FROM aging_loan_analysis1y;
 SELECT COUNT(id_1y) INTO @port1y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1y) INTO @port2y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
 SELECT COUNT(id_1y) INTO @port3y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
 SELECT COUNT(id_1y) INTO @port4y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;
 SELECT COUNT(id_1y) INTO @port5y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=360;

 IF @port1y >0 THEN
 
   INSERT INTO  aging_loan_analysis2y( 
   id_2y,
   customer_name ,
   customer_contact ,
   date_taken,
   due_date,
   loans_remaining ,
   principal_remaining ,
 interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears,
   loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
     date_taken,
     due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 IF @port2y >0 THEN
 
   INSERT INTO  aging_loan_analysis3y( 
   id_3y,
   customer_name ,
   customer_contact ,
       date_taken,
       due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears,loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
       date_taken,
       due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 IF @port3y >0 THEN
 
   INSERT INTO  aging_loan_analysis4y( 
   id_4y,
   customer_name ,
   customer_contact ,
      date_taken,
      due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
       date_taken,
       due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 IF @port4y >0 THEN
 
   INSERT INTO  aging_loan_analysis5y( 
   id_5y,
   customer_name ,
   customer_contact ,
     date_taken,
     due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
     date_taken,
     due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 IF @port5y >0 THEN
 
   INSERT INTO  aging_loan_analysis6y( 
   id_6y,
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
 interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   "-",
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
 END IF;

 IF @port0 >0 THEN 
   
 INSERT INTO  aging_loan_analysis8( 
   id_8,
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   @port0,
   'TOTAL ACTIVE LOANS' ,
   "-" ,
     "-" ,
       "-" ,
  "-" ,
       "-" ,
   SUM(interest_remaining),
   SUM(principal_inarrears) ,
   SUM(interest_inarrears) ,
  "-","-"  FROM aging_loan_analysis1; 
 END IF;

 IF @port0y >0 THEN 
   
 INSERT INTO  aging_loan_analysis8y( 
   id_8y,
   customer_name ,
   customer_contact ,
     date_taken,due_date,
   loans_remaining ,
   principal_remaining ,
   interest_remaining,
   principal_inarrears ,
   interest_inarrears ,
   number_of_days_in_arrears ,loan_deadline
   ) SELECT 
   @port0y,
   'TOTAL COMBINED LOANS' ,
   "-" ,
     "-" ,
       "-" ,
  "-" ,
       "-" ,
   SUM(interest_remaining),
   SUM(principal_inarrears) ,
   SUM(interest_inarrears) ,
  "-","-"  FROM aging_loan_analysis1y; 
 END IF;

 IF @port0x >0 THEN 
   
 INSERT INTO  aging_loan_analysis8x( 
  id_8x,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
 @port0x,
  'TOTAL RENEWED LOANS' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-"  FROM aging_loan_analysis1x; 

    
END IF;

   
INSERT INTO  aging_loan_analysis9( 
  id_9,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  (@port0+@port0x),
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(ot.interest_remaining),
  SUM(ot.principal_inarrears) ,
  SUM(ot.interest_inarrears) ,
 "-" ,"-" FROM (SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1 UNION ALL SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1x) ot;  
 

 IF @port0 >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","ACTIVE LOANS","-","-","-","-","-","-","-");
END IF;
IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
END IF;
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_2,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis2;

 
IF @port1 >0 THEN  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port1,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-",
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

END IF;


  
IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");
  
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis3;
    
    
IF @port2 >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port2,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

  IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_4,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis4;
  
  
IF @port3 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port3,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;

  IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
    
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_5,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis5;

  
IF @port4 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port4,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-","-");
END IF;
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_6,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis6;
  
  
IF @port5 >0 THEN
 
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port5,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" , "-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_8,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis8;

 IF @port0y >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","COMBINED LOANS","-","-","-","-","-","-","-");
 END IF;

 IF @port1y >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","COMBINED LOANS","-","-","-","-","-","-","-","-");
 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
   id_2y,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis2y;

 IF @port1y >0 THEN  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port1y,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" , "-"  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30;

 END IF;

 IF @port2y >0 THEN
  INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","COMBINED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");

 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
   id_3y,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis3y;

 IF @port2y >0 THEN  
 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port2y,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,
 "-"  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
 END IF;

 IF @port3y >0 THEN
  INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","COMBINED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");

 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
   id_4y,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis4y;

 IF @port3y >0 THEN
 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port3y,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-",
 "-" FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
 END IF;

 IF @port4y >0 THEN
  INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","COMBINED NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");

 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
   id_5y,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis5y;

 IF @port4y >0 THEN
 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port4y,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;
 END IF;

 IF @port5y >0 THEN
  INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","COMBINED PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-","-");
 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_6y,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis6y;

 IF @port5y >0 THEN
 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port5y,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-"  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=360; 
 END IF;

 INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_8y,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis8y;

 IF @port0x >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","RENEWED LOANS","-","-","-","-","-","-","-");
 END IF;

IF @port1x >0 THEN

 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","RENEWED PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_2x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis2x;
    
    IF @port1x >0 THEN
  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port1x,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

END IF;
  
  
IF @port2x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");

END IF;


   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_3x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis3x;
    
    
IF @port2x >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port2x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
END IF;
  
  IF @port3x >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");
END IF;





   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_4x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis4x;
  
  IF @port3x >0 THEN

   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
  @port3x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;
  
IF @port4x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","RENEWED NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
   id_5x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline  FROM aging_loan_analysis5x;

  IF @port4x >0 THEN
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
  @port4x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","RENEWED PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-","-");
   END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline 
  ) SELECT 
   id_6x,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline  FROM aging_loan_analysis6x;
  
  
IF @port5x >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
  @port5x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
  "-" ,
      "-" ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-", "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360; 

END IF;
  
IF @port0 >0 OR @port0x >0 THEN

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
   id_8x,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline  FROM aging_loan_analysis8x;
END IF;
IF @port0 >0 OR @port0x >0 THEN
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
    principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline 
  ) SELECT 
   id_9,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis9;
END IF;
SELECT  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis7;
  
END

##
DELIMITER ;

-- CALL agingAnalysisStaff();







/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysisG;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisG()
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus,gaurantorName1,gaurantorContact1,gaurantorContact2,gaurantorName2 VARCHAR(60);


DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1y;

CREATE TEMPORARY  TABLE aging_loan_analysis1y(id_1y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1y));

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1));


DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis2x;

CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3x;

CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4x;

CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;

CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;

CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis2y;

CREATE TEMPORARY  TABLE aging_loan_analysis2y(id_2y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_2y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3y;

CREATE TEMPORARY  TABLE aging_loan_analysis3y(id_3y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_3y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4y;

CREATE TEMPORARY  TABLE aging_loan_analysis4y(id_4y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_4y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5y;

CREATE TEMPORARY  TABLE aging_loan_analysis5y(id_5y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_5y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6y;

CREATE TEMPORARY  TABLE aging_loan_analysis6y(id_6y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8y;

CREATE TEMPORARY  TABLE aging_loan_analysis8y(id_8y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8y))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis9;

CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_9))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

OPEN forSelectingLoanIds;

accounts_loop: LOOP 



FETCH forSelectingLoanIds into loanId;
 
--  SELECT loanId;

IF l_done=1 THEN

LEAVE accounts_loop;

END IF;

SELECT pl.applicant_account_name,m.mobile1,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.loan_cycle_status INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem,p_remain,theLoanStatus FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';


SELECT COUNT(id) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId=loanId;
-- SELECT loanId,numberOfGaurantors;
IF numberOfGaurantors=0 THEN

SET gaurantorName1='-',gaurantorContact1='-',gaurantorName2='-',gaurantorContact2='-';
END IF;

IF numberOfGaurantors=1 THEN
SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId;

SET gaurantorName2='-',gaurantorContact2='-';
END IF;



IF numberOfGaurantors=2 THEN
-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id ASC LIMIT 1;
SET @sql_text2 = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName1,@gaurantorContact1 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;



-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName2,gaurantorContact2 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id DESC LIMIT 1;


SET @sql_text2X = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName2,@gaurantorContact2 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X FROM @sql_text2X;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


SELECT @gaurantorName1,@gaurantorContact1 INTO gaurantorName1,gaurantorContact1;
SELECT @gaurantorName2,@gaurantorContact2 INTO gaurantorName2,gaurantorContact2;
END IF;

SET ID=ID+1;

IF ISNULL(remainport) THEN
SET remainport=0;
END IF;

IF ISNULL(princeremain) THEN
SET princeremain=0;
END IF;

IF ISNULL(interestRem) THEN
SET interestRem=0;
END IF;

IF ISNULL(p_remain) THEN
SET p_remain=0;
END IF;

IF ISNULL(i_remain) THEN
SET i_remain=0;
END IF;

IF ISNULL(arrears) THEN
SET arrears=0;
END IF;
 

SELECT DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y') INTO @INST;

-- SELECT @INST;

IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
END IF;

IF theLoanStatus='Disbursed' THEN

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;

IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;

IF theLoanStatus='Combined' THEN

INSERT INTO aging_loan_analysis1y VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;


 CLOSE forSelectingLoanIds;
SELECT COUNT(id_1) INTO @port0  FROM aging_loan_analysis1 ;
 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;



 
IF @port1 >0 THEN

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
END IF;

IF @port2 >0 THEN

  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
 
END IF;

IF @port3 >0 THEN


    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port4 >0 THEN

    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;

IF @port5 >0 THEN

    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
END IF;


SELECT COUNT(id_1x) INTO @port0x  FROM aging_loan_analysis1x;
 SELECT COUNT(id_1x) INTO @port1x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

 SELECT COUNT(id_1x) INTO @port2x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  SELECT COUNT(id_1x) INTO @port3x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

   SELECT COUNT(id_1x) INTO @port4x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1x) INTO @port5x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360;


IF @port1x >0 THEN

  INSERT INTO  aging_loan_analysis2x( 
  id_2x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port2x >0 THEN

  
  INSERT INTO  aging_loan_analysis3x( 
  id_3x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port3x >0 THEN
 
    INSERT INTO  aging_loan_analysis4x( 
  id_4x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
END IF;



IF @port4x >0 THEN

    INSERT INTO  aging_loan_analysis5x( 
  id_5x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port5x >0 THEN

    INSERT INTO  aging_loan_analysis6x( 
  id_6x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
 interest_remaining,
 principal_inarrears ,
 interest_inarrears ,
 number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
 
 END IF;
 
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2x;
    
    IF @port1x >0 THEN
  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port1x,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

END IF;
  
  
IF @port2x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","-","-","-","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-");

END IF;


   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3x;
    
    
IF @port2x >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port2x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
END IF;
  
  IF @port3x >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","-","-","-","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-");
END IF;





   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4x;
  
  IF @port3x >0 THEN

   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port3x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
         "-" ,
            "-" ,
               "-" ,
                  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;
  
IF @port4x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","-","-","-","-","RENEWED NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-");

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5x;

  IF @port4x >0 THEN
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port4x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","-","-","-","-","RENEWED PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-");
   END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6x;
  
  
IF @port5x >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port5x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
       "-" ,
        "-" ,
         "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360; 

END IF;
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8x,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8x;

 
IF @port0x >0 OR @port0 >0 THEN
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
    principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_9,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis9;
END IF;

SELECT  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears FROM aging_loan_analysis7;
  
END

##
DELIMITER ;

-- CALL agingAnalysisG();








 

 



/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysisStaffG;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisStaffG(IN staffId INT)
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId,theLoanStatus VARCHAR(100);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined') AND  gruop_id=staffId  ;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT);

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 INTEGER,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER,customer_name VARCHAR(100),customer_contact VARCHAR(100),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(100),due_date VARCHAR(100),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;

accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;
 
--  SELECT loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
SELECT pl.applicant_account_name,m.mobile1,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.trn_id INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem,p_remain,theTrnId FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';

-- SELECT p_remain,i_remain,arrears;

SELECT COUNT(id) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId=theTrnId;
-- SELECT loanId,numberOfGaurantors;
IF numberOfGaurantors=0 THEN

SET gaurantorName1='MISSING',gaurantorContact1='MISSING',gaurantorName2='MISSING',gaurantorContact2='MISSING';
END IF;

IF numberOfGaurantors=1 THEN
SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId;

SET gaurantorName2='MISSING',gaurantorContact2='MISSING';
END IF;



IF numberOfGaurantors=2 THEN
-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id ASC LIMIT 1;
SET @sql_text2 = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName1,@gaurantorContact1 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),theTrnId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;



-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName2,gaurantorContact2 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id DESC LIMIT 1;


SET @sql_text2X = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName2,@gaurantorContact2 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),theTrnId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X FROM @sql_text2X;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


SELECT @gaurantorName1,@gaurantorContact1 INTO gaurantorName1,gaurantorContact1;
SELECT @gaurantorName2,@gaurantorContact2 INTO gaurantorName2,gaurantorContact2;
END IF;

 SET ID=ID+1;

 IF ISNULL(remainport) THEN
SET remainport=0;
 END IF;

  IF ISNULL(princeremain) THEN
SET princeremain=0;
 END IF;

 IF ISNULL(interestRem) THEN
SET interestRem=0;
 END IF;

  IF ISNULL(p_remain) THEN
SET p_remain=0;
 END IF;

  IF ISNULL(i_remain) THEN
SET i_remain=0;
 END IF;

   IF ISNULL(arrears) THEN
SET arrears=0;
 END IF;
 

SELECT DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y') INTO @INST;

-- SELECT @INST;

  IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
 END IF;


INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);


    SET l_done=0;
 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;




IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis2 VALUES(0,'1-30',"-","-","-","-",'PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
  gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
   "-" ,
  "-" ,
   "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

  --  INSERT INTO aging_loan_analysis2 VALUES(0,"-","-","-","-","-","-","-","-");
END IF;

IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-",'30-60','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");
  
  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
   "-" ,
      "-" ,
       "-" ,
      "-" ,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis4 VALUES(0,"-","-","-","-",'60-90','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");

    
    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
   "-" ,
      "-" ,
       "-" ,
      "-" ,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

  --  INSERT INTO aging_loan_analysis4 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis5 VALUES(0,"-","-","-","-",'90-360','NON PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");
    
   
    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
  gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
  gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
  gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
   "-" ,
      "-" ,
       "-" ,
      "-" ,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

  --  INSERT INTO aging_loan_analysis5 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis6 VALUES(0,"-","-","-","-",'360 AND Above','PORTFOLIO DUE FOR WRITE OFF',"-","-","-","-","-","-","-","-");

   
    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
   "-" ,
      "-" ,
       "-" ,
      "-" ,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

END IF;

   
INSERT INTO  aging_loan_analysis8( 
  id_8,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
   "-" ,
    "-" ,
     "-" ,
    "-" ,
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1; 

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4;
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5;

  
  
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6;
  
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8;

SELECT  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears FROM aging_loan_analysis7;
  
END

##
DELIMITER ;





 

 



/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysisStaffG;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisStaffG(IN staffId INT)
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId, theLoanStatus VARCHAR(100);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined') AND  gruop_id=staffId  ;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1y;

CREATE TEMPORARY  TABLE aging_loan_analysis1y(id_1y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1y));

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1));

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis2x;

CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3x;

CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4x;

CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;

CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;

CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis2y;

CREATE TEMPORARY  TABLE aging_loan_analysis2y(id_2y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_2y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis3y;

CREATE TEMPORARY  TABLE aging_loan_analysis3y(id_3y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_3y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis4y;

CREATE TEMPORARY  TABLE aging_loan_analysis4y(id_4y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_4y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5y;

CREATE TEMPORARY  TABLE aging_loan_analysis5y(id_5y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,PRIMARY KEY (id_5y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6y;

CREATE TEMPORARY  TABLE aging_loan_analysis6y(id_6y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6y))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8y;

CREATE TEMPORARY  TABLE aging_loan_analysis8y(id_8y INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8y))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis9;

CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_9))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;

accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;
 
--  SELECT loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SELECT pl.applicant_account_name,m.mobile1,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.loan_cycle_status INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem,p_remain,theLoanStatus FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';


SELECT COUNT(id) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId=loanId;
-- SELECT loanId,numberOfGaurantors;
IF numberOfGaurantors=0 THEN

SET gaurantorName1='-',gaurantorContact1='-',gaurantorName2='-',gaurantorContact2='-';
END IF;

IF numberOfGaurantors=1 THEN
SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId;

SET gaurantorName2='-',gaurantorContact2='-';
END IF;



IF numberOfGaurantors=2 THEN
-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id ASC LIMIT 1;
SET @sql_text2 = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName1,@gaurantorContact1 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;



-- SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName2,gaurantorContact2 FROM gaurantors WHERE loanTrnId=theTrnId  ORDER BY id DESC LIMIT 1;


SET @sql_text2X = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName2,@gaurantorContact2 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X FROM @sql_text2X;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


SELECT @gaurantorName1,@gaurantorContact1 INTO gaurantorName1,gaurantorContact1;
SELECT @gaurantorName2,@gaurantorContact2 INTO gaurantorName2,gaurantorContact2;
END IF;

 SET ID=ID+1;

 IF ISNULL(remainport) THEN
SET remainport=0;
 END IF;

  IF ISNULL(princeremain) THEN
SET princeremain=0;
 END IF;

 IF ISNULL(interestRem) THEN
SET interestRem=0;
 END IF;

  IF ISNULL(p_remain) THEN
SET p_remain=0;
 END IF;

  IF ISNULL(i_remain) THEN
SET i_remain=0;
 END IF;

   IF ISNULL(arrears) THEN
SET arrears=0;
 END IF;
 

SELECT DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y') INTO @INST;

-- SELECT @INST;

  IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
 END IF;

IF theLoanStatus='Disbursed' THEN

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;

IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;

IF theLoanStatus='Combined' THEN

INSERT INTO aging_loan_analysis1y VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);

END IF;

     SET l_done=0;
  END LOOP accounts_loop;
 
  CLOSE forSelectingLoanIds;
 SELECT COUNT(id_1) INTO @port0  FROM aging_loan_analysis1 ;
  SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
  SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
   SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
    SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;
 
  SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;
 
 
 
  
 IF @port1 >0 THEN

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
END IF;

IF @port2 >0 THEN

  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
 
END IF;

IF @port3 >0 THEN


    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port4 >0 THEN

    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;

IF @port5 >0 THEN

    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
END IF;


SELECT COUNT(id_1x) INTO @port0x  FROM aging_loan_analysis1x;
 SELECT COUNT(id_1x) INTO @port1x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

 SELECT COUNT(id_1x) INTO @port2x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  SELECT COUNT(id_1x) INTO @port3x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

   SELECT COUNT(id_1x) INTO @port4x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1x) INTO @port5x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360;


IF @port1x >0 THEN

  INSERT INTO  aging_loan_analysis2x( 
  id_2x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port2x >0 THEN

  
  INSERT INTO  aging_loan_analysis3x( 
  id_3x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port3x >0 THEN
 
    INSERT INTO  aging_loan_analysis4x( 
  id_4,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
END IF;



IF @port4x >0 THEN

    INSERT INTO  aging_loan_analysis5x( 
  id_5x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port5x >0 THEN

    INSERT INTO  aging_loan_analysis6x( 
  id_6x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;

SELECT COUNT(id_1y) INTO @port0y  FROM aging_loan_analysis1y;
  SELECT COUNT(id_1y) INTO @port1y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30;
 
  SELECT COUNT(id_1y) INTO @port2y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
 
   SELECT COUNT(id_1y) INTO @port3y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
 
    SELECT COUNT(id_1y) INTO @port4y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;
 
  SELECT COUNT(id_1y) INTO @port5y  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=360;

IF @port1y >0 THEN
 
  INSERT INTO  aging_loan_analysis2y( 
  id_2y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
 interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   
 
 END IF;

 IF @port2y >0 THEN
 
  
  INSERT INTO  aging_loan_analysis3y( 
  id_3y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
  
 END IF;

 IF @port3y >0 THEN
 
 
    INSERT INTO  aging_loan_analysis4y( 
  id_4y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   
 
 END IF;

 IF @port4y >0 THEN
 
 
    INSERT INTO  aging_loan_analysis5y( 
  id_5y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 
 
 END IF;

 IF @port5y >0 THEN
 
 
    INSERT INTO  aging_loan_analysis6y( 
  id_6y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
 
 END IF;

 IF @port0 >0 THEN 
  
 INSERT INTO  aging_loan_analysis8( 
  id_8,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port0,
  'TOTAL ACTIVE LOANS' ,
  "-" ,
    "-" ,
      "-" ,
         "-" ,
            "-" ,
               "-" ,
                  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1; 
END IF;

 IF @port0y >0 THEN 
  
 INSERT INTO  aging_loan_analysis8y( 
  id_8y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port0y,
  'TOTAL COMBINED LOANS' ,
  "-" ,
    "-" ,
      "-" ,
         "-" ,
            "-" ,
               "-" ,
                  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1y; 
 END IF;

 IF @port0x >0 THEN 
   
INSERT INTO  aging_loan_analysis8x( 
  id_8x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
 @port0x,
  'TOTAL RENEWED LOANS' ,
  "-" ,
    "-" ,
      "-" ,
      "-" ,
      "-" ,
      "-" ,
 "-"  FROM aging_loan_analysis1x; 

    
END IF;

 IF @port0 >0 THEN 
  
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","ACTIVE LOANS","-","-","-","-","-","-","-");
END IF;
IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
END IF;
   INSERT INTO  aging_loan_analysis7( 
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2;

 
IF @port1 >0 THEN  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port1,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

END IF;

IF @port0y >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","COMBINED LOANS","-","-","-","-","-","-");
END IF;

IF @port1y >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","COMBINED PERFORMING PORTFOLIO","-","-","-","-","-","-","-");
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2y;

IF @port1y >0 THEN  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port1y,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1y WHERE number_of_days_in_arrears<30;

END IF;

IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","-","-","-","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-");
  
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3;
    
    
IF @port2 >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port2,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
            "-" ,
                  "-" ,
                        "-" ,
                              "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

  IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-");

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4;
  
  
IF @port3 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port3,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
           "-" ,
                "-" ,
                     "-" ,
                          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;

  IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","-" ,"-" , "-" ,"-" ,"NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-");
    
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5;

  
IF @port4 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port4,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
          "-" ,
              "-" ,
                  "-" ,
                      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","-","-","-","-","PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-");
END IF;
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6;
  
  
IF @port5 >0 THEN
 
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port5,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
       "-" ,
        "-" ,
         "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8y,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8y;

 IF @port0x >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","RENEWED LOANS","-","-","-","-","-","-");
END IF;

IF @port1x >0 THEN

 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","PERFORMING PORTFOLIO RENEWED","-","-","-","-","-","-","-");
END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2x;
    
    IF @port1x >0 THEN
  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port1x,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

END IF;
  
  
IF @port2x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","-","-","-","-","PORTFOLIO AT RISK RENEWED ","-","-","-","-","-","-","-");

END IF;


   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3x;
    
    
IF @port2x >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port2x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
END IF;
  
  IF @port3x >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","-","-","-","-","PORTFOLIO AT RISK RENEWED ","-","-","-","-","-","-","-");
END IF;





   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4x;
  
  IF @port3x >0 THEN

   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port3x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
         "-" ,
            "-" ,
               "-" ,
                  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;
  
IF @port4x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","-","-","-","-","NON PERFORMING PORTFOLIO RENEWED ","-","-","-","-","-","-","-");

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5x,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5x;

  IF @port4x >0 THEN
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
   gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port4x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","-","-","-","-","PORTFOLIO DUE FOR WRITE OFF RENEWED ","-","-","-","-","-","-","-");
   END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6x,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6x;
  
  
IF @port5x >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  @port5x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
       "-" ,
        "-" ,
         "-" ,
          "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360; 

END IF;
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,due_date,
  loans_remaining ,
     principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8x,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8x;

 
IF @port0x >0 OR @port0 >0 THEN
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
    principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_9,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis9;
END IF;

SELECT  id_7,
  customer_name ,
  customer_contact ,
     gaurantor1_name,
  gaurantor1_contact,
  gaurantor2_name,
  gaurantor2_contact,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears FROM aging_loan_analysis7;
  
END

##
DELIMITER ;

-- CALL agingAnalysisStaffG();


-- Cash At Hand	.0



DROP PROCEDURE IF EXISTS normaliseDueDatesMaintainDisburseDate;
DELIMITER //
CREATE PROCEDURE normaliseDueDatesMaintainDisburseDate() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId, instalmentNo INT;
DECLARE startingDate DATE;
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from new_loan_appstore WHERE loan_cycle_status='Renewed';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
 SELECT instalment_start_date INTO startingDate FROM new_loan_appstore WHERE trn_id=theLoanTxnId; 

SELECT theLoanTxnId,startingDate;

INNER_BLOCK: BEGIN

DECLARE instalmentNos INT;
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT instalment_no FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 


TXNIDS_LOOP:LOOP

FETCH forBatchNos INTO instalmentNo;
SET startingDate=DATE_ADD(startingDate, INTERVAL 1 DAY);
SELECT theLoanTxnId,instalmentNo,startingDate;
UPDATE new_loan_appstoreamort SET instalment_due_date=startingDate WHERE instalment_no=instalmentNo AND master1_id=theLoanTxnId;

 IF innerNotFound=1 THEN
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

-- CALL normaliseDueDatesMaintainDisburseDate();

DROP PROCEDURE IF EXISTS normaliseDueDatesMaintainRenewalDate;
DELIMITER //
CREATE PROCEDURE normaliseDueDatesMaintainRenewalDate() READS SQL DATA 

OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId, instalmentNo INT;
DECLARE startingDate DATE;
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT trn_id from new_loan_appstore WHERE loan_cycle_status='Renewed';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 
 SELECT trn_date INTO startingDate FROM new_loan_appstore WHERE trn_id=theLoanTxnId; 

SELECT theLoanTxnId,startingDate;

INNER_BLOCK: BEGIN

DECLARE instalmentNos INT;
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT instalment_no FROM new_loan_appstoreamort WHERE master1_id=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos; 


TXNIDS_LOOP:LOOP

FETCH forBatchNos INTO instalmentNo;
SET startingDate=DATE_ADD(startingDate, INTERVAL 1 DAY);
SELECT theLoanTxnId,instalmentNo,startingDate;
UPDATE new_loan_appstoreamort SET instalment_due_date=startingDate WHERE instalment_no=instalmentNo AND master1_id=theLoanTxnId;

 IF innerNotFound=1 THEN
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



DROP PROCEDURE IF EXISTS totalNumberOfActiveCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfActiveCustomers(OUT activeCustomers INT)
BEGIN
SELECT COUNT(trn_id) INTO activeCustomers FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined';

END $$
DELIMITER ;

-- CREATE PROCEDURE totalNumberOfActiveCustomersOnly(OUT activeCustomersOnly INT)
-- BEGIN
--   SELECT COUNT(trn_id)
--     INTO activeCustomersOnly
--     FROM new_loan_appstore
--     WHERE (loan_cycle_status = 'Disbursed' OR loan_cycle_status = 'Renewed')
--       AND ABS(DATEDIFF(instalment_end_date, CURDATE())) <= 30;
-- END $$
-- DELIMITER ;

-- DROP PROCEDURE IF EXISTS totalNumberOfActiveCustomersOnly;
-- DELIMITER $$
-- CREATE PROCEDURE totalNumberOfActiveCustomersOnly(OUT activeCustomersOnly INT)
-- BEGIN
--   SELECT COUNT(trn_id)
--     INTO activeCustomersOnly
--     FROM new_loan_appstore
--     WHERE loan_cycle_status = 'Disbursed'
--       AND ABS(DATEDIFF(instalment_end_date, CURDATE())) <=30;
-- END $$
-- DELIMITER ;


-- DROP PROCEDURE IF EXISTS totalNumberOfActiveCustomersOnly;
-- DELIMITER $$
-- CREATE PROCEDURE totalNumberOfActiveCustomersOnly(OUT activeCustomersOnly INT)
-- BEGIN
--   -- start a transaction boundary (even though this is a read, for consistency)
--   START TRANSACTION;

--   SELECT
--     COUNT(*) 
--     INTO activeCustomersOnly
--   FROM
--     new_loan_appstore AS nla
--     JOIN (
--       -- derive the last instalment_due_date per loan
--       SELECT
--         master1_id,
--         MAX(instalment_due_date) AS last_due_date
--       FROM
--         new_loan_appstoreamort
--       GROUP BY master1_id
--     ) AS amort
--       ON amort.master1_id = nla.trn_id
--   WHERE
--     nla.loan_cycle_status = 'Disbursed'
--     AND ABS(DATEDIFF(amort.last_due_date, CURDATE())) <= 30;

--   COMMIT;
-- END $$
-- DELIMITER ;

DROP PROCEDURE IF EXISTS totalNumberOfActiveCustomersOnly;
DELIMITER $$
CREATE PROCEDURE totalNumberOfActiveCustomersOnly(OUT activeCustomersOnly INT)
BEGIN
  START TRANSACTION;
  
  SELECT
    COUNT(*) 
    INTO activeCustomersOnly
  FROM
    new_loan_appstore AS nla
  WHERE
    nla.loan_cycle_status = 'Disbursed'
    AND numberOfDayInArrears(nla.loan_id) <= 30;
  
  COMMIT;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS totalNumberOfActiveNewCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfActiveNewCustomers(OUT activeCustomers INT)
BEGIN
SELECT DISTINCT COUNT(new_loan_appstore.trn_id) INTO activeCustomers FROM pmms_loans.new_loan_appstore INNER JOIN pmms.account_created_store ON new_loan_appstore.applicant_account_number=account_created_store.account_number WHERE (new_loan_appstore.loan_cycle_status='Disbursed') AND account_created_store.creation_date=DATE(NOW());

END $$
DELIMITER ;


-- DROP PROCEDURE IF EXISTS totalNumberOfCustomersPaid;
-- DELIMITER $$
-- CREATE  PROCEDURE totalNumberOfCustomersPaid(OUT activeCustomersPaid INT)
-- BEGIN

-- SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW()) AND (balance_due=(princimpal_amount+total_interest));


-- SELECT  COUNT( DISTINCT master1_id) INTO @totalPaidRenewed from new_loan_appstoreamort WHERE (instalment_status='P' OR instalment_status='PP')  AND instalment_paid_date=DATE(NOW());


-- SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

-- END $$
-- DELIMITER ;

DROP PROCEDURE IF EXISTS totalNumberOfCustomersPaid;
DELIMITER $$

CREATE PROCEDURE totalNumberOfCustomersPaid(OUT activeCustomersPaid INT)
BEGIN
    DECLARE numberRenewed INT DEFAULT 0;
    DECLARE totalPaidRenewed INT DEFAULT 0;

    -- Calculate the number of renewed loans paid today from main and archived tables
    SELECT COUNT(trn_id) 
    INTO numberRenewed
    FROM (
        SELECT trn_id, princimpal_amount, total_interest, balance_due, loan_cycle_status, trn_date
        FROM new_loan_appstore
        WHERE loan_cycle_status = 'Renewed' 
          AND trn_date = DATE(NOW()) 
          AND balance_due = (princimpal_amount + total_interest)
        
        UNION ALL
        
        SELECT trn_id, princimpal_amount, total_interest, balance_due, loan_cycle_status, trn_date
        FROM arch_new_loan_appstore
        WHERE loan_cycle_status = 'Renewed' 
          AND trn_date = DATE(NOW()) 
          AND balance_due = (princimpal_amount + total_interest)
    ) AS combined_renewed;

    -- Calculate the total number of distinct customers with payments made today from main and archived tables
    SELECT COUNT(DISTINCT master1_id) 
    INTO totalPaidRenewed
    FROM (
        SELECT master1_id, instalment_status, instalment_paid_date
        FROM new_loan_appstoreamort
        WHERE (instalment_status = 'P' OR instalment_status = 'PP')  
          AND instalment_paid_date = DATE(NOW())
        
        UNION ALL
        
        SELECT master1_id, instalment_status, instalment_paid_date
        FROM arch_new_loan_appstoreamort
        WHERE (instalment_status = 'P' OR instalment_status = 'PP')  
          AND instalment_paid_date = DATE(NOW())
    ) AS combined_paid;

    -- Calculate active customers paid
    SET activeCustomersPaid = totalPaidRenewed - numberRenewed;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS totalPrincimpalBalance;
DELIMITER $$
CREATE  PROCEDURE totalPrincimpalBalance(OUT princimpalBalance INT)
BEGIN

SELECT SUM(TotalPrincipalRemaining) INTO princimpalBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' OR loan_cycle_status='Combined' ;

END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS totalInterestBalance;
DELIMITER $$
CREATE  PROCEDURE totalInterestBalance(OUT interestBalance INT)
BEGIN

SELECT SUM(TotalInterestRemaining) INTO interestBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' OR loan_cycle_status='Combined' ;

END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS closingCash;   
 DROP PROCEDURE IF EXISTS closingCash;   
DELIMITER $$
CREATE  PROCEDURE closingCash(OUT closingCashBal INT)
BEGIN

SELECT ledger_balance INTO closingCashBal from pmms.bsanca01123000110 where trn_date=DATE(NOW()) ORDER BY trn_id DESC LIMIT 1;

IF ISNULL(closingCashBal) THEN
SET closingCashBal=0.0;
END IF;

END $$
DELIMITER ;


 DROP PROCEDURE IF EXISTS momoBalance;   
DELIMITER $$
CREATE  PROCEDURE momoBalance(OUT TheMomoBalance INT)
BEGIN

SELECT master_balance INTO TheMomoBalance from pmms.bsanca01121000010 where trn_date<=DATE(NOW()) ORDER BY trn_id DESC LIMIT 1;

IF ISNULL(TheMomoBalance) THEN
SET TheMomoBalance=0.0;

END IF;

END $$
DELIMITER ;



--  DROP PROCEDURE IF EXISTS merchantBalance;   
-- DELIMITER $$
-- CREATE  PROCEDURE merchantBalance(OUT TheMerchantBalance INT)
-- BEGIN

-- SELECT master_balance INTO TheMerchantBalance from pmms.bsanca01117000010 where trn_date<=DATE(NOW()) ORDER BY trn_id DESC LIMIT 1;

-- IF ISNULL(TheMerchantBalance) THEN
-- SET TheMerchantBalance=0.0;

-- END IF;

-- END $$
-- DELIMITER ;

DROP PROCEDURE IF EXISTS merchantBalance;
DELIMITER $$
CREATE PROCEDURE merchantBalance(OUT TheMerchantBalance DECIMAL(10,2))
BEGIN
    DECLARE table_exists INT DEFAULT 0;

    -- Check if the table exists
    SELECT COUNT(*)
    INTO table_exists
    FROM information_schema.tables 
    WHERE table_schema = 'pmms'
    AND table_name = 'bsanca01117000010';

    IF table_exists = 0 THEN
        -- If the table does not exist, set TheMerchantBalance to 0.0
        SET TheMerchantBalance = 0.0;
    ELSE
        -- If the table exists, get the master balance
        SELECT master_balance 
        INTO TheMerchantBalance 
        FROM pmms.bsanca01117000010 
        WHERE trn_date <= DATE(NOW()) 
        ORDER BY trn_id DESC 
        LIMIT 1;

        -- If the balance is NULL, set TheMerchantBalance to 0.0
        IF ISNULL(TheMerchantBalance) THEN
            SET TheMerchantBalance = 0.0;
        END IF;
    END IF;
END $$
DELIMITER ;






 DROP PROCEDURE IF EXISTS countNumberOfRenewals;   
DELIMITER $$
CREATE  PROCEDURE countNumberOfRenewals(OUT numberOfRenewals INT)
BEGIN
SELECT COUNT(trn_id) INTO numberOfRenewals FROM new_loan_appstore WHERE trn_date=DATE(NOW()) AND  loan_cycle_status='Renewed';

END $$
DELIMITER ;


 DROP PROCEDURE IF EXISTS sumRenewals;   
DELIMITER $$
CREATE  PROCEDURE sumRenewals(OUT totalRenewals INT)
BEGIN
SELECT SUM(princimpal_amount) INTO totalRenewals  FROM new_loan_appstore WHERE trn_date=DATE(NOW()) AND loan_cycle_status='Renewed';

IF ISNULL(totalRenewals) THEN
SET totalRenewals=0.0;
END IF;

END $$
DELIMITER ;



 DROP PROCEDURE IF EXISTS countNumberOfDisbursements;   
DELIMITER $$
CREATE  PROCEDURE countNumberOfDisbursements(OUT numberOfDibusements INT)
BEGIN
SELECT COUNT(trn_id) INTO numberOfDibusements FROM new_loan_appstore WHERE trn_date=DATE(NOW()) AND loan_cycle_status='Disbursed' ;

END $$
DELIMITER ;




DROP PROCEDURE IF EXISTS countNumberOfRenewedPaid;
DELIMITER $$
CREATE  PROCEDURE countNumberOfRenewedPaid(OUT renewedCustomersPaid INT)
BEGIN

-- SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW());


-- SELECT  COUNT( DISTINCT master1_id) INTO renewedCustomersPaid from new_loan_appstoreamort INNER JOIN new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE instalment_status='P' AND instalment_paid_date=DATE(NOW()) and loan_cycle_status='Renewed';

SELECT COUNT(trnId) INTO renewedCustomersPaid FROM pmms.loandisburserepaystatement  WHERE TrnDate=CURDATE() AND LoanStatusReport='Renewed' AND ExpectedTotalAmount<=0.0;

-- SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

END $$
DELIMITER ;





DROP PROCEDURE IF EXISTS sumRenewalsPaid;
DELIMITER $$
CREATE  PROCEDURE sumRenewalsPaid(OUT sumRenewalsPaid INT)
BEGIN

-- SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW());


-- SELECT  SUM(instalment_paid) INTO sumRenewalsPaid from new_loan_appstoreamort INNER JOIN new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE instalment_status='P' AND instalment_paid_date=DATE(NOW()) and loan_cycle_status='Renewed';

SELECT SUM(AmountPaid) INTO sumRenewalsPaid FROM pmms.loandisburserepaystatement  WHERE TrnDate=CURDATE() AND LoanStatusReport='Renewed' AND ExpectedTotalAmount<=0.0;

-- SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

END $$
DELIMITER ;



 DROP PROCEDURE IF EXISTS totalNumberOfSavingDeposited;         
DELIMITER $$
CREATE  PROCEDURE totalNumberOfSavingDeposited(OUT activeCustomersSave INT)
BEGIN

SELECT COUNT(TrnId) INTO activeCustomersSave FROM pmms.newsavingsmembers WHERE TrnDate=DATE(NOW()) AND SavingsAdded>0;

END $$
DELIMITER ;

 DROP PROCEDURE IF EXISTS totalNumberOfSavingWithdraw;         
DELIMITER $$
CREATE  PROCEDURE totalNumberOfSavingWithdraw(OUT activeCustomersSave INT)
BEGIN

SELECT COUNT(TrnId) INTO activeCustomersSave FROM pmms.newsavingsmembers WHERE TrnDate=DATE(NOW()) AND SavingsRemoved>0;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS ProcessingFeesCollected;
DELIMITER //
CREATE PROCEDURE ProcessingFeesCollected(IN theDate DATE,OUT processingFees VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO processingFees FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03315%';
IF ISNULL(processingFees) THEN
SET processingFees=0.0;
END IF;


        END //

        DELIMITER ;


/* CALL ProcessingFeesCollected('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */





DROP PROCEDURE IF EXISTS LedgerFees;
DELIMITER //
CREATE PROCEDURE LedgerFees(IN theDate DATE,OUT ledgerFessCol VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO ledgerFessCol FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03304%';
IF ISNULL(ledgerFessCol) THEN
SET ledgerFessCol=0.0;
END IF;


END //

DELIMITER ;


/* 
CALL LedgerFees('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */







DROP PROCEDURE IF EXISTS BadDebtsRecovered;
DELIMITER //
CREATE PROCEDURE BadDebtsRecovered(IN theDate DATE,OUT badDebtsRecover VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO badDebtsRecover FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03318%';
IF ISNULL(badDebtsRecover) THEN
SET badDebtsRecover=0.0;
END IF;

END //
DELIMITER ;


/* CALL BadDebtsRecovered('2019-06-21',@badDebtsRecover1);

SELECT @badDebtsRecover1; */





DROP PROCEDURE IF EXISTS MembershipFees;
DELIMITER //
CREATE PROCEDURE MembershipFees(IN theDate DATE,OUT memberShipFessCol VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO memberShipFessCol FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03309%';
IF ISNULL(memberShipFessCol) THEN
SET memberShipFessCol=0.0;
END IF;

END //
DELIMITER ;


/* CALL MembershipFees('2019-06-21',@memberShipFessCol);

SELECT @memberShipFessCol; */






DROP PROCEDURE IF EXISTS annualSubFees;
DELIMITER //
CREATE PROCEDURE annualSubFees(IN theDate DATE,OUT annualFeesRecovered VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO annualFeesRecovered FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03316%';
IF ISNULL(annualFeesRecovered) THEN
SET annualFeesRecovered=0.0;
END IF;

END //
DELIMITER ;


/* CALL annualSubFees('2019-06-21',@annualFeesRecovered);

SELECT @annualFeesRecovered; */




DROP PROCEDURE IF EXISTS InterestRenewed;
DELIMITER //
CREATE PROCEDURE InterestRenewed(IN theDate DATE,OUT InterestR VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO InterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND  debit_account_no LIKE '03322%';
IF ISNULL(InterestR) THEN
SET InterestR=0.0;
END IF;

END //
DELIMITER ;





DROP PROCEDURE IF EXISTS InterestRecover;
DELIMITER //
CREATE PROCEDURE InterestRecover(IN theDate DATE,OUT InterestR VARCHAR(100)) READS SQL DATA 

BEGIN
-- SELECT  SUM(credit) INTO InterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no LIKE '03301%' OR debit_account_no LIKE '03322%');

-- SELECT SUM(InterestPaid) INTO InterestR FROM pmms.loandisburserepaystatement WHERE TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';


SELECT SUM(InterestPaid)
INTO InterestR
FROM (
    SELECT InterestPaid
    FROM pmms.loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
    UNION ALL
    SELECT InterestPaid
    FROM pmms.arch_loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
) AS combined;


-- IF ISNULL(princimpalRepaymentsMade) THEN
-- SET princimpalRepaymentsMade=0.0;
-- END IF;


IF ISNULL(InterestR) THEN
SET InterestR=0.0;
END IF;

END //
DELIMITER ;



/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */


DROP PROCEDURE IF EXISTS accumulatedInterestR;
DELIMITER //
CREATE PROCEDURE accumulatedInterestR(IN theDate DATE,OUT accumulatedInterestR VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO accumulatedInterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03311%';
IF ISNULL(accumulatedInterestR) THEN
SET accumulatedInterestR=0.0;
END IF;

END //
DELIMITER ;


/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */








DROP PROCEDURE IF EXISTS loanPenaltyRecov;
DELIMITER //
CREATE PROCEDURE loanPenaltyRecov(IN theDate DATE,OUT loanPenaltyRecovered VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO loanPenaltyRecovered FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03312%';
IF ISNULL(loanPenaltyRecovered) THEN
SET loanPenaltyRecovered=0.0;
END IF;


END //
DELIMITER ;


/* CALL loanPenaltyRecov('2019-06-21',@loanPenaltyRecovered);

SELECT @loanPenaltyRecovered; */






-- 16/09/2022	16/09/2022	Henry deposit Processed on 16/09/2022
--   From Equity float account	1000000.0	-	427486.0	BTN165955


DROP PROCEDURE IF EXISTS otherIncomesCollected;
DELIMITER //
CREATE PROCEDURE otherIncomesCollected(IN theDate DATE,OUT otherIncomesCollectedX VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO otherIncomesCollectedX FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%');

END //
DELIMITER ;

/* 
CALL otherIncomesCollected('2019-06-21',@otherIncomesCollectedX);

SELECT @otherIncomesCollectedX; */













DROP PROCEDURE IF EXISTS PayablesCreated;

DELIMITER //

CREATE PROCEDURE PayablesCreated(IN theDate DATE,OUT payableCreatedFinal VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO payableCreatedFinal FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05500%';
IF ISNULL(payableCreatedFinal) THEN
SET payableCreatedFinal=0.0;
END IF;

END //
DELIMITER ;


/* CALL PayablesCreated('2019-06-22',@payableCreatedFinal);

SELECT @payableCreatedFinal; */










DROP PROCEDURE IF EXISTS InsurancePayableMade;

DELIMITER //


CREATE PROCEDURE InsurancePayableMade(IN theDate DATE,OUT insurancePayableMadev VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO insurancePayableMadev FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05524%';
IF ISNULL(insurancePayableMadev) THEN
SET insurancePayableMadev=0.0;
END IF;



END //

DELIMITER ;


/* CALL InsurancePayableMade('2019-04-09',@insurancePayableMadeX);

SELECT @insurancePayableMade; */




DROP PROCEDURE IF EXISTS otherLiabilitiesAndProvisionsMade;

DELIMITER //

CREATE PROCEDURE otherLiabilitiesAndProvisionsMade(IN theDate DATE,OUT liabilityProvi VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO liabilityProvi FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%'  OR debit_account_no like '05509%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%') ;

IF ISNULL(liabilityProvi) THEN
SET liabilityProvi=0.0;
END IF;


END //

DELIMITER ;


/* CALL otherLiabilitiesAndProvisionsMade('2019-06-22',@liabilityProvi);

SELECT @liabilityProvi; */







DROP PROCEDURE IF EXISTS capitalisationMade;

DELIMITER //

CREATE PROCEDURE capitalisationMade(IN theDate DATE,OUT someEquity VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO someEquity FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04400%';
IF ISNULL(someEquity) THEN
SET someEquity=0.0;
END IF;




END //

DELIMITER ;


/* CALL capitalisationMade('2019-06-22',@someEquity);

SELECT @someEquity; */






DROP PROCEDURE IF EXISTS OtherCapitalisationsAndReservesMade;

DELIMITER //

CREATE PROCEDURE OtherCapitalisationsAndReservesMade(IN theDate DATE,OUT otheCapsAndReserversMade VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO otheCapsAndReserversMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no>='044010000110' AND debit_account_no<='04430999999';
IF ISNULL(otheCapsAndReserversMade) THEN
SET otheCapsAndReserversMade=0.0;
END IF;



END //
DELIMITER ;

/* CALL OtherCapitalisationsAndReservesMade('2019-06-22',@otheCapsAndReserversMade);


SELECT @otheCapsAndReserversMade; */






DROP PROCEDURE IF EXISTS RecevablesRefunded;

DELIMITER //

CREATE PROCEDURE RecevablesRefunded(IN theDate DATE,OUT Refundreceiavale VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO Refundreceiavale FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01131%';
IF ISNULL(Refundreceiavale) THEN
SET Refundreceiavale=0.0;
END IF;




END   //
DELIMITER ;
/* 

CALL RecevablesRefunded('2019-06-23',@Refundreceiavale); */

/* SELECT @Refundreceiavale; */




DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsRefunded;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsRefunded(IN theDate DATE,OUT otherReceiAndPrepaymentRend VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO otherReceiAndPrepaymentRend FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01118%';
IF ISNULL(otherReceiAndPrepaymentRend) THEN
SET otherReceiAndPrepaymentRend=0.0;
END IF;


END //
DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsRefunded('2019-06-23',@otherReceiAndPrepaymentRend); */

/* SELECT @otherReceiAndPrepaymentRend; */




DROP PROCEDURE IF EXISTS BankWithdrawsMade;

DELIMITER //

CREATE PROCEDURE BankWithdrawsMade(IN theDate DATE,OUT BankWithdrws VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO BankWithdrws FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01122%';
IF ISNULL(BankWithdrws) THEN
SET BankWithdrws=0.0;
END IF;




END //
DELIMITER ;


CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);



DROP PROCEDURE IF EXISTS princimpalLoanRepaymentsMade;

DELIMITER //

CREATE PROCEDURE princimpalLoanRepaymentsMade(IN theDate DATE,OUT princimpalRepaymentsMade VARCHAR(100)) READS SQL DATA 


BEGIN

SELECT SUM(PrincipalPaid)
INTO princimpalRepaymentsMade
FROM (
    SELECT PrincipalPaid
    FROM pmms.loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
    UNION ALL
    SELECT PrincipalPaid
    FROM pmms.arch_loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
) AS combined;


IF ISNULL(princimpalRepaymentsMade) THEN
SET princimpalRepaymentsMade=0.0;
END IF;

END //
DELIMITER ;




DROP PROCEDURE IF EXISTS collectionsMade;

DELIMITER //

CREATE PROCEDURE collectionsMade(IN theDate DATE,OUT theCollectionsMade VARCHAR(100)) READS SQL DATA 


BEGIN



SELECT SUM(AmountPaid)
INTO theCollectionsMade
FROM (
    SELECT AmountPaid
    FROM pmms.loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
    UNION ALL
    SELECT AmountPaid
    FROM pmms.arch_loandisburserepaystatement
    WHERE TrnDate = theDate
      AND AmountPaid > 0.0
      AND NOT LoanStatusReport = 'RenewedClosed'
) AS combined;


IF ISNULL(theCollectionsMade) THEN
SET theCollectionsMade=0.0;
END IF;



END //
DELIMITER ;







/* CALL princimpalLoanRepaymentsMade('2019-06-23',@princimpalRepaymentsMade);

SELECT @princimpalRepaymentsMade; */


DROP PROCEDURE IF EXISTS merchantWithdrawReport;

DELIMITER //

CREATE PROCEDURE merchantWithdrawReport(IN theDate DATE,OUT merchantWithdraw VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO merchantWithdraw FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01117%';
IF ISNULL(merchantWithdraw) THEN
SET merchantWithdraw=0.0;
END IF;



END //
DELIMITER ;



DROP PROCEDURE IF EXISTS refundFromMobileMoneyPayableMade;

DELIMITER //

CREATE PROCEDURE refundFromMobileMoneyPayableMade(IN theDate DATE,OUT mobileMoneyRefund VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO mobileMoneyRefund FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01121%';
IF ISNULL(mobileMoneyRefund) THEN
SET mobileMoneyRefund=0.0;
END IF;



END //
DELIMITER ;






DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsDisposedOff;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsDisposedOff(IN theDate DATE,OUT fixedAssetsAndInvestmentDisp VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO fixedAssetsAndInvestmentDisp FROM pmms.general_ledger WHERE  trn_date=theDate AND   (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%');

IF ISNULL(fixedAssetsAndInvestmentDisp) THEN
SET fixedAssetsAndInvestmentDisp=0.0;
END IF;


END //
DELIMITER ;


/* CALL fixedAssetsAndInvestmentsDisposedOff('2019-06-23',@fixedAssetsAndInvestmentDisp);

SELECT @fixedAssetsAndInvestmentDisp; */





DROP PROCEDURE IF EXISTS ExpensesMade;

DELIMITER //

CREATE PROCEDURE ExpensesMade(IN theDate DATE,OUT ExpensesMa VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO ExpensesMa FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '022%';
IF ISNULL(ExpensesMa) THEN
SET ExpensesMa=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '022%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */




-- /* IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET ExpensesMa='0';
-- ELSE

-- SET ExpensesMa=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL ExpensesMade('2019-06-23',@ExpensesMa);

SELECT @ExpensesMa; */






DROP PROCEDURE IF EXISTS InterestWrittenOff;

DELIMITER //

CREATE PROCEDURE InterestWrittenOff(IN theDate DATE,OUT interWriteOff VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO interWriteOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03301%';
IF ISNULL(interWriteOff) THEN
SET interWriteOff=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03301%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */



-- /* 
-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET interWriteOff='0';
-- ELSE

-- SET interWriteOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;




/* CALL InterestWrittenOff('2019-06-24',@interWriteOff);

SELECT @interWriteOff; */



DROP PROCEDURE IF EXISTS accumuInteresWrittenOff;

DELIMITER //

CREATE PROCEDURE accumuInteresWrittenOff(IN theDate DATE,OUT accumuIntereWrittenOff VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO accumuIntereWrittenOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03311%';
IF ISNULL(accumuIntereWrittenOff) THEN
SET accumuIntereWrittenOff=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03311%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET accumuIntereWrittenOff='0';
-- ELSE

-- SET accumuIntereWrittenOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;



/* 
CALL accumuInteresWrittenOff('2019-06-24',@accumuIntereWrittenOff);

SELECT @accumuIntereWrittenOff; */





DROP PROCEDURE IF EXISTS processingFeesWrittenOff;

DELIMITER //

CREATE PROCEDURE processingFeesWrittenOff(IN theDate DATE,OUT processFeesWriteOff VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO processFeesWriteOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03315%';
IF ISNULL(processFeesWriteOff) THEN
SET processFeesWriteOff=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET processFeesWriteOff='0';
-- ELSE

-- SET processFeesWriteOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* 

CALL processingFeesWrittenOff('2019-06-24',@processFeesWriteOff);

SELECT @processFeesWriteOff; */






DROP PROCEDURE IF EXISTS OtherIncomesWrittenOff;

DELIMITER //

CREATE PROCEDURE OtherIncomesWrittenOff(IN theDate DATE,OUT otherIncomesWrOff VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO otherIncomesWrOff FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no LIKE '03312%' OR debit_account_no LIKE '03316%' OR debit_account_no LIKE '03309%' OR debit_account_no LIKE '03318%' OR debit_account_no LIKE '03304%' OR debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%');
IF ISNULL(otherIncomesWrOff) THEN
SET otherIncomesWrOff=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '03312%' OR account_number LIKE '03316%' OR account_number LIKE '03309%' OR account_number LIKE '03318%' OR account_number LIKE '03304%' OR account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%'  OR account_number like  '03310%'  OR account_number like  '03313%' OR account_number like '03314%'  OR account_number like '03317%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET otherIncomesWrOff='0';
-- ELSE

-- SET otherIncomesWrOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;



/* 
CALL OtherIncomesWrittenOff('2019-06-24',@otherIncomesWrOff);

SELECT @otherIncomesWrOff; */





DROP PROCEDURE IF EXISTS ReceivablesCreated;

DELIMITER //

CREATE PROCEDURE ReceivablesCreated(IN theDate DATE,OUT receiavale VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO receiavale FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01131%';
IF ISNULL(receiavale) THEN
SET receiavale=0.0;
END IF;


END //
DELIMITER ;


/* CALL ReceivablesCreated('2019-06-24',@receiavale);

SELECT @receiavale; */




DROP PROCEDURE IF EXISTS MobileMoneyReceivableCreated;

DELIMITER //

CREATE PROCEDURE MobileMoneyReceivableCreated(IN theDate DATE,OUT mobileMoneyc VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO mobileMoneyc FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01121%';
IF ISNULL(mobileMoneyc) THEN
SET mobileMoneyc=0.0;
END IF;

END //
DELIMITER ;






DROP PROCEDURE IF EXISTS merchantDepositsReport;

DELIMITER //

CREATE PROCEDURE merchantDepositsReport(IN theDate DATE,OUT merchantDeposit VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO merchantDeposit FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01117%';
IF ISNULL(merchantDeposit) THEN
SET merchantDeposit=0.0;
END IF;

END //
DELIMITER ;






DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsCreated;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsCreated(IN theDate DATE,OUT otherRecePreMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO otherRecePreMade FROM pmms.general_ledger WHERE  trn_date=theDate AND  ( debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
 OR debit_account_no LIKE '01132%'  OR debit_account_no LIKE '01133%'  OR debit_account_no LIKE '01134%'  OR debit_account_no LIKE '01135%'  OR debit_account_no LIKE '01120%');
IF ISNULL(otherRecePreMade) THEN
SET otherRecePreMade=0.0;
END IF;



END //
DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsCreated('2019-06-24',@otherRecePreMade);

SELECT @otherRecePreMade; */








DROP PROCEDURE IF EXISTS BankDepositsMade;

DELIMITER //

CREATE PROCEDURE BankDepositsMade(IN theDate DATE,OUT bankDepositMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO bankDepositMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01122%';
IF ISNULL(bankDepositMade) THEN
SET bankDepositMade=0.0;
END IF;


END //
DELIMITER ;


/* CALL BankDepositsMade('2019-06-24',@bankDepositMade);

SELECT @bankDepositMade; */





DROP PROCEDURE IF EXISTS LoanDisbursementsMade;

DELIMITER //

CREATE PROCEDURE LoanDisbursementsMade(IN theDate DATE,OUT loansDisbursed VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO loansDisbursed FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no ='01128000110';
IF ISNULL(loansDisbursed) THEN
SET loansDisbursed=0.0;
END IF;


END //
DELIMITER ;


/* CALL LoanDisbursementsMade('2019-06-24',@loansDisbursed);

SELECT @loansDisbursed; */




DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsAquired;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsAquired(IN theDate DATE,OUT fixedAssetsAndInvestmentAquired VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO fixedAssetsAndInvestmentAquired FROM pmms.general_ledger WHERE  trn_date=theDate AND  (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%');
IF ISNULL(fixedAssetsAndInvestmentAquired) THEN
SET fixedAssetsAndInvestmentAquired=0.0;
END IF;

END //
DELIMITER ;


/* CALL fixedAssetsAndInvestmentsAquired('2019-06-24',@fixedAssetsAndInvestmentAquired);

SELECT @fixedAssetsAndInvestmentAquired; */




DROP PROCEDURE IF EXISTS PayablesRefunded;

DELIMITER //

CREATE PROCEDURE PayablesRefunded(IN theDate DATE,OUT RefundPayable VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO RefundPayable FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05500%';
IF ISNULL(RefundPayable) THEN
SET RefundPayable=0.0;
END IF;



END //
DELIMITER ;


/* CALL PayablesRefunded('2022-06-24',@RefundPayable);

SELECT @RefundPayable; */






DROP PROCEDURE IF EXISTS PayablesOtherLiabilitiesAndProvisionsRefunded;

DELIMITER //

CREATE PROCEDURE PayablesOtherLiabilitiesAndProvisionsRefunded(IN theDate DATE,OUT RefundPayableOtherLiabilProvis VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO RefundPayableOtherLiabilProvis FROM pmms.general_ledger WHERE  trn_date=theDate AND   (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%');
IF ISNULL(RefundPayableOtherLiabilProvis) THEN
SET RefundPayableOtherLiabilProvis=0.0;
END IF;


END //
DELIMITER ;


/* CALL PayablesOtherLiabilitiesAndProvisionsRefunded('2019-06-24',@RefundPayableOtherLiabilProvis);

SELECT @RefundPayableOtherLiabilProvis; */







DROP PROCEDURE IF EXISTS InsurancePayableCleared;

DELIMITER //

CREATE PROCEDURE InsurancePayableCleared(IN theDate DATE,OUT insurancePayCleared VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO insurancePayCleared FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05524%';
IF ISNULL(insurancePayCleared) THEN
SET insurancePayCleared=0.0;
END IF;


END //
DELIMITER ;


/* CALL InsurancePayableCleared('2019-06-24',@insurancePayCleared);

SELECT @insurancePayCleared; */






DROP PROCEDURE IF EXISTS DrawingsMade;

DELIMITER //

CREATE PROCEDURE DrawingsMade(IN theDate DATE,OUT DrawingM VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO DrawingM FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04408%';
IF ISNULL(DrawingM) THEN
SET DrawingM=0.0;
END IF;


END //
DELIMITER ;


/* CALL DrawingsMade('2019-06-24',@DrawingM);

SELECT @DrawingM; */





DROP PROCEDURE IF EXISTS DecapitalisationsMade;

DELIMITER //

CREATE PROCEDURE DecapitalisationsMade(IN theDate DATE,OUT Decapitlise VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO Decapitlise FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04400%';
IF ISNULL(Decapitlise) THEN
SET Decapitlise=0.0;
END IF;


END //
DELIMITER ;

/* 
CALL DecapitalisationsMade('2019-06-24',@Decapitlise);

SELECT @Decapitlise; */






DROP PROCEDURE IF EXISTS OtherEquitiesAndReservesRemoved;

DELIMITER //

CREATE PROCEDURE OtherEquitiesAndReservesRemoved(IN theDate DATE,OUT equitiesReservesRemoved VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO equitiesReservesRemoved FROM pmms.general_ledger WHERE  trn_date=theDate AND  ((debit_account_no>='044010000110' AND debit_account_no<='04430999999') AND NOT debit_account_no='04408000110');
IF ISNULL(equitiesReservesRemoved) THEN
SET equitiesReservesRemoved=0.0;
END IF;


END //
DELIMITER ;


/* CALL OtherEquitiesAndReservesRemoved('2019-06-24',@equitiesReservesRemoved);

SELECT @equitiesReservesRemoved; */



/* CALL SavingsDepositsWithdrawn('2019-06-24',@savingDepositWith);

SELECT @savingDepositWith; */

-- PORTFOLIO SUMMARY CATEGORY, DESCRIPTION, NO_OF_LOANS, TOTAL_AMOUNT_DISBURSED, OUTSTANDING_TOTAL, ARREARS ------------------------------------------------------------------------------------------------------------
--  1-30, PERFORMING PORTFOLIO, 0, 0, 0, 0 
--  TOTAL, -, 50, 22,300,000, 18,143,000, 8,643,000
--   TOTAL ACTIVE LOANS, -, 50, 22,300,000, 18,143,000, 8,643,000 30-60, RENEWED PORTFOLIO AT RISK, 0, 0, 0, 0 TOTAL, -, 30, 4,541,900, 4,935,880, 4,935,880 60-90, RENEWED PORTFOLIO AT RISK, 0, 0, 0, 0 TOTAL, -, 13, 3,493,500, 2,785,500, 2,785,500 90-360, RENEWED NON PERFORMING PORTFOLIO, 0, 0, 0, 0 TOTAL, -, 184, 68,673,145, 74,504,769, 74,504,769 360 AND Above, RENEWED PORTFOLIO DUE FOR WRITE OFF, 0, 0, 0, 0 TOTAL, -, 29, 14,061,023, 16,733,727, 16,733,727 TOTAL RENEWED LOANS, -, 256, 90,769,568, 98,959,876, 98,959,876 OVERALL TOTAL, -, 306, 113,069,568, 117,102,876, 107,602,876 



DROP PROCEDURE smsSummuryReport;
DELIMITER $$
CREATE  PROCEDURE smsSummuryReport()
    READS SQL DATA
BEGIN
 
    


DROP TABLE IF EXISTS smsSummury;

CREATE  TEMPORARY TABLE smsSummury(itemName VARCHAR(200),itemValue VARCHAR(200));







CALL totalNumberOfActiveCustomers(@activeCustomers);
-- SELECT @activeCustomers;
IF @activeCustomers>0 THEN
-- SELECT @activeCustomers;
INSERT INTO smsSummury VALUES("Total No.OfCustomers:",FORMAT(@activeCustomers,0));

  END IF;





  CALL totalNumberOfActiveCustomersOnly(@activeCustomersOnly);
-- SELECT @activeCustomers;
IF @activeCustomers>0 THEN
-- SELECT @activeCustomers;
INSERT INTO smsSummury VALUES("No.OfActiveCustomers:",FORMAT(@activeCustomersOnly,0));

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




CALL collectionsMade(DATE(NOW()),@theCollectionsMade);
SET @ActualTotalAmountCollectedToday=@theCollectionsMade;



IF @ActualTotalAmountCollectedToday>0 THEN

INSERT INTO smsSummury VALUES("TotalCollections:",FORMAT(@ActualTotalAmountCollectedToday,0));

  END IF;




-- SELECT @princimpalRepaymentsMade;
  -- SELECT @princimpalRepaymentsMade;
  CALL sumRenewals(@totalRenewals);
  -- select @totalRenewals;
-- CALL collectionsMade(DATE(NOW()),@princimpalRepaymentsMade);
-- CALL InterestRecover(DATE(NOW()),@InterestR);

CALL InterestRenewed(DATE(NOW()),@InterestRenew);
-- SELECT @princimpalRepaymentsMade,@InterestR;
-- SET @ActualTotalAmountCollectedToday=(@princimpalRepaymentsMade);

-- select @ActualTotalAmountCollectedToday;
-- IF @ActualTotalAmountCollectedToday>0 THEN
--   -- SELECT @ActualTotalAmountCollectedToday;
-- INSERT INTO smsSummury VALUES("TotalCollections:",FORMAT(@ActualTotalAmountCollectedToday,0));

--   END IF;



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

INSERT INTO smsSummury VALUES("PrincipalBalance:",FORMAT(@princimpalBalance,0));

  END IF;


  CALL totalInterestBalance(@interestBalance);

IF @interestBalance>0 THEN

INSERT INTO smsSummury VALUES("InterestBalance:",FORMAT(@interestBalance,0));

  END IF;

IF @interestBalance>0 OR @princimpalBalance>0 THEN

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

-- IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01128%') THEN

CALL princimpalLoanRepaymentsMade(DATE(NOW()),@princimpalRepaymentsMade);

  -- END IF;

IF ISNULL(@princimpalRepaymentsMade) THEN
SET @princimpalRepaymentsMade=0;
END IF;
-- SELECT CONCAT("PrincipalCollected:=",@princimpalRepaymentsMade);
SET @OpeningCahdBala=@OpeningCahdBala+@princimpalRepaymentsMade;

IF @princimpalRepaymentsMade>0 THEN 
-- SET @PC=(@princimpalRepaymentsMade-(@totalRenewals-@InterestRenew));
-- SELECT @PC,@princimpalRepaymentsMade,@totalRenewals,@InterestRenew;

INSERT INTO smsSummury VALUES("PrincipalCollected:",FORMAT(@princimpalRepaymentsMade,0));
END IF;
-- IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '03301%') THEN
CALL InterestRecover(DATE(NOW()),@InterestR);
-- END IF;
-- IF ISNULL(@InterestR) THEN
-- SET @InterestR=0;
-- END IF;
-- SELECT CONCAT("InterestCollected:=",@InterestR);
SET @OpeningCahdBala=@OpeningCahdBala+@InterestR;

IF @InterestR>0 THEN 
INSERT INTO smsSummury VALUES("InterestCollected:",FORMAT(@InterestR,0));
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

IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND   (debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
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




IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01117%') THEN
CALL merchantDepositsReport(DATE(NOW()),@merchantDeposits);
END IF;

IF ISNULL(@merchantDeposits) THEN
SET @merchantDeposits=0;
END IF;

SET @OpeningCahdBala=@OpeningCahdBala-@merchantDeposits;

IF @merchantDeposits>0 THEN 
INSERT INTO smsSummury VALUES("MerchantDeposits:",FORMAT(@merchantDeposits,0));
END IF;






IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  debit_account_no LIKE '01117%') THEN
CALL merchantWithdrawReport(DATE(NOW()),@merchantWithdraw);
END IF;


IF ISNULL(@merchantWithdraw) THEN
SET @merchantWithdraw=0;
END IF;
-- SELECT CONCAT("MomoWithdraws:=",@mobileMoneyRefund);
SET @OpeningCahdBala=@OpeningCahdBala+@merchantWithdraw;

IF @merchantWithdraw>0 THEN 
INSERT INTO smsSummury VALUES("MerchantWithdraws:",FORMAT(@merchantWithdraw,0));
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


IF EXISTS(SELECT DISTINCT debit_account_no from pmms.general_ledger  where trn_date=DATE(NOW()) AND  (debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
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


IF ISNULL(@TheMomoBalance) THEN 
SET @TheMomoBalance=0;
END IF;

 IF @TheMomoBalance>0 THEN 
INSERT INTO smsSummury VALUES("MoMoBalance:",FORMAT(@TheMomoBalance,0) );
END IF;


CALL merchantBalance(@TheMerchantBalance);

IF ISNULL(@TheMerchantBalance) THEN 
SET @TheMerchantBalance=0;
END IF;

 IF @TheMerchantBalance>0 THEN 
INSERT INTO smsSummury VALUES("MerchantBalance:",FORMAT(@TheMerchantBalance,0) );
END IF;




 IF @TheMomoBalance>0 OR @TheMerchantBalance>0  THEN 
INSERT INTO smsSummury VALUES("TotalCashMomoAndMerchant:",FORMAT((@TheMomoBalance+@closingCashBal+@TheMerchantBalance),0));
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


DROP FUNCTION IF EXISTS NumberOfCustomersPerOfficer;

DELIMITER $$
CREATE FUNCTION NumberOfCustomersPerOfficer(loanOfficeId INT) RETURNS INT
BEGIN
    DECLARE activeCustomers INT;

    SELECT COUNT(trn_id) INTO activeCustomers 
    FROM new_loan_appstore 
    WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined') AND gruop_id=loanOfficeId;

    RETURN activeCustomers;
END$$
DELIMITER ;


DROP FUNCTION IF EXISTS PaidCustomersPerOfficer;

DELIMITER $$

CREATE FUNCTION PaidCustomersPerOfficer(loanOfficerId INT) RETURNS INT
BEGIN
    DECLARE totalPaidRenewed INT;

    -- Calculate the distinct count of paid or partially paid installments today for the specified loan officer
    SELECT COUNT(DISTINCT master1_id) INTO totalPaidRenewed 
    FROM (
        -- Current loan records
        SELECT 
            master1_id
        FROM new_loan_appstoreamort
        WHERE (instalment_status = 'P' OR instalment_status = 'PP') 
          AND instalment_paid_date = DATE(NOW())
          AND master1_id IN (
              SELECT trn_id 
              FROM new_loan_appstore 
              WHERE NOT loan_cycle_status = 'RenewedClosed' 
                AND gruop_id = loanOfficerId
          )
        
        UNION ALL
        
        -- Archived loan records
        SELECT 
            master1_id
        FROM arch_new_loan_appstoreamort
        WHERE (instalment_status = 'P' OR instalment_status = 'PP') 
          AND instalment_paid_date = DATE(NOW())
          AND master1_id IN (
              SELECT trn_id 
              FROM arch_new_loan_appstore 
              WHERE NOT loan_cycle_status = 'RenewedClosed' 
                AND gruop_id = loanOfficerId
          )
    ) AS PaidCustomers;

    RETURN totalPaidRenewed;
END$$

DELIMITER ;



-- DROP FUNCTION IF EXISTS PaidCustomersPerOfficer;

-- DELIMITER $$
-- CREATE FUNCTION PaidCustomersPerOfficer(loanOfficerId INT) RETURNS INT
-- BEGIN
--     DECLARE numberRenewed INT;
--     DECLARE totalPaidRenewed INT;
--     DECLARE activeCustomersPaid INT;


--     -- Calculate the distinct count of paid or partially paid instalments today for the specified loan office
--     SELECT COUNT(DISTINCT master1_id) INTO totalPaidRenewed 
--     FROM new_loan_appstoreamort 
--     WHERE (instalment_status = 'P' OR instalment_status = 'PP') 
--       AND instalment_paid_date = DATE(NOW())
--       AND master1_id IN (SELECT trn_id FROM new_loan_appstore WHERE NOT loan_cycle_status='RenewedClosed' AND  gruop_id = loanOfficerId);

--     -- Calculate the difference to get active paid customers for the specified loan office
--     -- SET activeCustomersPaid = totalPaidRenewed - numberRenewed;

--     RETURN totalPaidRenewed;
-- END$$
-- DELIMITER ;



DROP FUNCTION IF EXISTS PercentPaidCustomersPerOfficer;

DELIMITER $$
CREATE FUNCTION PercentPaidCustomersPerOfficer(loanOfficerId INT) RETURNS DECIMAL(5,2)
BEGIN
    DECLARE totalCustomers INT;
    DECLARE paidCustomers INT;
    DECLARE percentPaid DECIMAL(5,2);

    -- Get the total number of customers for the loan officer
    SET totalCustomers = NumberOfCustomersPerOfficer(loanOfficerId);

    -- Get the number of paid customers for the loan officer
    SET paidCustomers = PaidCustomersPerOfficer(loanOfficerId);

    -- Calculate the percentage of paid customers
    IF totalCustomers > 0 THEN
        SET percentPaid = (paidCustomers / totalCustomers) * 100;
    ELSE
        SET percentPaid = 0;
    END IF;

    RETURN percentPaid;
END$$
DELIMITER ;





-- DROP PROCEDURE IF EXISTS totalPrincimpalBalancePerOfficer;
-- DELIMITER $$
-- CREATE  PROCEDURE totalPrincimpalBalancePerOfficer(loanOfficerId INT)
-- BEGIN

-- SELECT SUM(TotalPrincipalRemaining) INTO princimpalBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

-- END $$
-- DELIMITER ;




-- DROP PROCEDURE IF EXISTS totalInterestBalancePerOfficer;
-- DELIMITER $$
-- CREATE  PROCEDURE totalInterestBalancePerOfficer(loanOfficerId INT)
-- BEGIN

-- SELECT SUM(TotalInterestRemaining) INTO interestBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

-- END $$
-- DELIMITER ;


DROP FUNCTION IF EXISTS TotalPrincipalBalancePerOfficer;

DELIMITER $$
CREATE FUNCTION TotalPrincipalBalancePerOfficer(loanOfficerId INT) RETURNS DECIMAL(20,2)
BEGIN
    DECLARE principalBalance DECIMAL(20,2);

    SELECT SUM(CAST(TotalPrincipalRemaining AS DECIMAL(20,2))) INTO principalBalance
    FROM new_loan_appstore 
    WHERE (loan_cycle_status = 'Renewed' OR loan_cycle_status = 'Disbursed') 
      AND gruop_id = loanOfficerId;

    RETURN principalBalance;
END$$
DELIMITER ;



DROP FUNCTION IF EXISTS TotalInterestBalancePerOfficer;

DELIMITER $$
CREATE FUNCTION TotalInterestBalancePerOfficer(loanOfficerId INT) RETURNS DECIMAL(20,2)
BEGIN
    DECLARE interestBalance DECIMAL(20,2);

    SELECT SUM(CAST(TotalInterestRemaining AS DECIMAL(20,2))) INTO interestBalance
    FROM new_loan_appstore 
    WHERE (loan_cycle_status = 'Renewed' OR loan_cycle_status = 'Disbursed') 
      AND gruop_id = loanOfficerId;

    RETURN interestBalance;
END$$
DELIMITER ;


DROP FUNCTION IF EXISTS PortfolioAmountPerOfficer;

DELIMITER $$
CREATE FUNCTION PortfolioAmountPerOfficer(loanOfficerId INT) RETURNS DECIMAL(20,2)
BEGIN
    DECLARE principalBalance DECIMAL(20,2);
    DECLARE interestBalance DECIMAL(20,2);
    DECLARE portfolioAmount DECIMAL(20,2);

    -- Get the total principal balance for the loan officer
    SET principalBalance = TotalPrincipalBalancePerOfficer(loanOfficerId);

    -- Get the total interest balance for the loan officer
    SET interestBalance = TotalInterestBalancePerOfficer(loanOfficerId);

    -- Calculate the total portfolio amount
    SET portfolioAmount = principalBalance + interestBalance;

    RETURN portfolioAmount;
END$$
DELIMITER ;





DROP FUNCTION IF EXISTS CollectionsMadePerOfficer;

DELIMITER $$

CREATE FUNCTION CollectionsMadePerOfficer(theDate DATE, loanOfficerId INT) RETURNS DECIMAL(20,2)
BEGIN
    DECLARE theCollectionsMade DECIMAL(20,2);

    -- Calculate the total collections made for the specified loan officer on the given date
    SELECT SUM(CAST(AmountPaid AS DECIMAL(20,2))) INTO theCollectionsMade
    FROM (
        -- Current loan records
        SELECT 
            lrs.AmountPaid
        FROM pmms.loandisburserepaystatement lrs
        JOIN pmms_loans.new_loan_appstore nls 
            ON lrs.loanTrnId = nls.trn_id
        WHERE lrs.TrnDate = theDate 
          AND CAST(lrs.AmountPaid AS DECIMAL(20,2)) > 0.0
          AND lrs.LoanStatusReport <> 'RenewedClosed'
          AND nls.gruop_id = loanOfficerId

        UNION ALL

        -- Archived loan records
        SELECT 
            alrs.AmountPaid
        FROM pmms.arch_loandisburserepaystatement alrs
        JOIN pmms_loans.arch_new_loan_appstore anls 
            ON alrs.loanTrnId = anls.trn_id
        WHERE alrs.TrnDate = theDate 
          AND CAST(alrs.AmountPaid AS DECIMAL(20,2)) > 0.0
          AND alrs.LoanStatusReport <> 'RenewedClosed'
          AND anls.gruop_id = loanOfficerId
    ) AS CombinedCollections;

    -- Handle NULL case
    IF ISNULL(theCollectionsMade) THEN
        SET theCollectionsMade = 0.0;
    END IF;

    RETURN theCollectionsMade;
END$$

DELIMITER ;




DROP FUNCTION IF EXISTS NewCustomersPerOfficer;

DELIMITER $$
CREATE FUNCTION NewCustomersPerOfficer(loanOfficerId INT) RETURNS INT
BEGIN
    DECLARE activeCustomers INT;

    -- Calculate the number of new customers for the specified loan officer for today
    SELECT COUNT(DISTINCT nls.trn_id) INTO activeCustomers
    FROM pmms_loans.new_loan_appstore nls
    INNER JOIN pmms.account_created_store acs ON nls.applicant_account_number = acs.account_number
    WHERE nls.loan_cycle_status = 'Disbursed'
      AND acs.creation_date = DATE(NOW())
      AND nls.gruop_id = loanOfficerId;

    RETURN activeCustomers;
END$$
DELIMITER ;


DROP FUNCTION IF EXISTS `staffName`;
DELIMITER $$
CREATE  FUNCTION `staffName`(staffId INT) RETURNS varchar(40) CHARSET latin1
    DETERMINISTIC
BEGIN
DECLARE staffNameNow VARCHAR(40);

SELECT account_name INTO staffNameNow FROM pmms.log_in WHERE  user_id=staffId;
 IF ISNULL(staffNameNow) THEN
 SET staffNameNow='MISSING';
 END IF;
RETURN staffNameNow;
END $$
DELIMITER ;



DROP FUNCTION IF EXISTS CountNumberOfDisbursementsByOfficer;

DELIMITER $$
CREATE FUNCTION CountNumberOfDisbursementsByOfficer(loanOfficerId INT) RETURNS INT
BEGIN
    DECLARE numberOfDisbursements INT;

    -- Calculate the number of disbursements for the specified loan officer for today
    SELECT COUNT(trn_id) INTO numberOfDisbursements
    FROM new_loan_appstore 
    WHERE trn_date = DATE(NOW()) 
      AND loan_cycle_status = 'Disbursed'
      AND gruop_id = loanOfficerId;

    RETURN numberOfDisbursements;
END$$
DELIMITER ;


DROP FUNCTION IF EXISTS SumDisbursementsByOfficer;

DELIMITER $$
CREATE FUNCTION SumDisbursementsByOfficer(loanOfficerId INT) RETURNS DOUBLE
BEGIN
    DECLARE totalDisbursement DOUBLE;

    -- Calculate the sum of disbursements for the specified loan officer for today
    SELECT SUM(CAST(princimpal_amount AS DECIMAL(20,2))) INTO totalDisbursement
    FROM new_loan_appstore 
    WHERE trn_date = DATE(NOW()) 
      AND loan_cycle_status = 'Disbursed'
      AND gruop_id = loanOfficerId;

    -- Handle NULL case
    IF ISNULL(totalDisbursement) THEN
        SET totalDisbursement = 0.0;
    END IF;

    RETURN totalDisbursement;
END$$
DELIMITER ;



DROP PROCEDURE IF EXISTS smsSummuryLoansOfficerReport;
DELIMITER $$
CREATE PROCEDURE smsSummuryLoansOfficerReport()
    READS SQL DATA
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE loanOfficerId INT;
    DECLARE theNoOfCustomer INT;
    DECLARE curLoanOfficer CURSOR FOR 
        SELECT DISTINCT user_id FROM pmms.log_in; -- Replace loansTable with the actual table name containing loanOfficerId
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    DROP TABLE IF EXISTS smsSummuryOfficer;

    CREATE TEMPORARY TABLE smsSummuryOfficer(
        officerId INT,
        officerName VARCHAR(200), 
        noOfCustomer VARCHAR(200), 
        paidC VARCHAR(200), 
        percentPaid VARCHAR(200), 
        portfolioAmount VARCHAR(200), 
        amountPaid VARCHAR(200),
        noOfLoansDisbursed VARCHAR(200),
        amountLoanDisbursed VARCHAR(200),
        newCustomers VARCHAR(200)
    );

    OPEN curLoanOfficer;
    
    read_loop: LOOP
        FETCH curLoanOfficer INTO loanOfficerId;
        IF done THEN
            LEAVE read_loop;
        END IF;
-- SELECT loanOfficerId;
        -- Get the number of customers for the current loan officer
        SET theNoOfCustomer = NumberOfCustomersPerOfficer(loanOfficerId);
        
        -- Insert only if noOfCustomer is greater than 0
        IF theNoOfCustomer > 0 THEN
            INSERT INTO smsSummuryOfficer (officerId, officerName, noOfCustomer, paidC, percentPaid, portfolioAmount, amountPaid,noOfLoansDisbursed, amountLoanDisbursed,newCustomers)
            SELECT 
                loanOfficerId AS officerId,
                 CONCAT('OfficerName: ', staffName(loanOfficerId)) AS officerName,
                CONCAT('NoOfCustomers: ', theNoOfCustomer) AS noOfCustomer,
                CONCAT('NoOfCustomersPaid: ', IFNULL(PaidCustomersPerOfficer(loanOfficerId), '0')) AS paidC,
                CONCAT('PercentPaid: ', IFNULL(CONCAT(ROUND(PercentPaidCustomersPerOfficer(loanOfficerId),0), '%'), '0%')) AS percentPaid,
                CONCAT('PortfolioAmount: ', FORMAT(IFNULL(PortfolioAmountPerOfficer(loanOfficerId), '0'),0)) AS portfolioAmount,
                CONCAT('CollectionsMade: ', FORMAT(IFNULL(CollectionsMadePerOfficer(DATE(NOW()), loanOfficerId), '0'),0)) AS amountPaid,
                CONCAT('NoOfLoansDisbursed: ', CountNumberOfDisbursementsByOfficer(loanOfficerId)) AS noOfDisbursements,
                  CONCAT('TotalamountDisbursed: ', FORMAT(IFNULL(SumDisbursementsByOfficer(loanOfficerId), '0'),0)) AS amountDisbursed,
                CONCAT('NewCustomers: ', IFNULL(NewCustomersPerOfficer(loanOfficerId), '0')) AS newCustomers;
        END IF;
    END LOOP;

    CLOSE curLoanOfficer;

    -- Fetch and display the summary data
    SELECT officerName, noOfCustomer, paidC, percentPaid, portfolioAmount, amountPaid, noOfLoansDisbursed, amountLoanDisbursed,newCustomers FROM smsSummuryOfficer;

    DROP TABLE IF EXISTS smsSummuryOfficer;
END $$
DELIMITER ;




-- CALL  smsSummuryReport() ;

-- update new_Loan_appstore SET TotalPrincipalRemaining=( princimpal_amount-TotalPrincipalPaid);

-- update new_Loan_appstore1 SET TotalPrincipalRemaining=( princimpal_amount-TotalPrincipalPaid);


DROP PROCEDURE IF EXISTS runSMSReportFirst;
DELIMITER //
CREATE PROCEDURE runSMSReportFirst(IN dQn DATE) READS SQL DATA 
BEGIN
    DECLARE recordCount INT DEFAULT 0;
    -- If the input date is the current date
    IF dQn = CURDATE() THEN 
 
           CALL smsSummuryReport();

    END IF;
END//
DELIMITER ;


DROP PROCEDURE IF EXISTS smsSummuryReportResend;
DELIMITER //
CREATE PROCEDURE smsSummuryReportResend(IN dQn DATE) READS SQL DATA 
BEGIN
   SELECT reprtItemName,reportItemValue FROM theSmsSummuryReport WHERE reportDate=dQn;
END//
DELIMITER ;

DROP FUNCTION IF EXISTS accountNma;

DELIMITER //

CREATE FUNCTION accountNma(accountNumber VARCHAR(30)) RETURNS VARCHAR(30) READS SQL DATA
BEGIN
    DECLARE accountName VARCHAR(30) DEFAULT 'N/A'; -- Initialize with 'N/A'

    -- Attempt to find an account name; the variable remains 'N/A' if no row is found
    SELECT account_name INTO accountName
    FROM pmms.account_created_store
    WHERE account_number = accountNumber
    LIMIT 1; -- Ensures only one record is processed, good practice if multiple could exist

    -- Return the fetched account name or 'N/A' if no matching row was found
    RETURN accountName;
END //

DELIMITER ;



DROP PROCEDURE IF EXISTS collectionStatement;

DELIMITER //
CREATE PROCEDURE collectionStatement(IN startDate DATE, IN endDate DATE) READS SQL DATA
BEGIN

    SELECT
        TrnId,
        loanTrnId,
        TrnDate,
        AccountName,
        AmountPaid
    FROM (

        SELECT
            TrnId,
            loanTrnId,
            TrnDate,
            accountNma(AccountNumber) AS AccountName,
            AmountPaid
        FROM pmms.loandisburserepaystatement
        WHERE (TrnDate >= startDate AND TrnDate <= endDate)
          AND ExpectedTotalAmount = 0.0
          AND NOT LoanStatusReport = 'RenewedClosed'

        UNION ALL

        SELECT
            TrnId,
            loanTrnId,
            TrnDate,
            accountNma(AccountNumber) AS AccountName,
            AmountPaid
        FROM pmms.arch_loandisburserepaystatement
        WHERE (TrnDate >= startDate AND TrnDate <= endDate)
          AND ExpectedTotalAmount = 0.0
          AND NOT LoanStatusReport = 'RenewedClosed'

        UNION ALL


        SELECT
            '-' AS TrnId,
            '-' AS loanTrnId,
            '-' AS TrnDate,
            'OVERALL TOTAL' AS AccountName,
            SUM(AmountPaid) AS AmountPaid
        FROM (
            SELECT AmountPaid
            FROM pmms.loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND NOT LoanStatusReport = 'RenewedClosed'

            UNION ALL

            SELECT AmountPaid
            FROM pmms.arch_loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND NOT LoanStatusReport = 'RenewedClosed'
        ) AS total_combined
    ) AS final_combined
    ORDER BY
        CASE WHEN TrnId = '-' THEN 1 ELSE 0 END,
        TrnDate;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS collectionStatementPerDay;

DELIMITER //
CREATE PROCEDURE collectionStatementPerDay(IN startDate DATE, IN endDate DATE) READS SQL DATA
BEGIN
 

    SELECT TrnId, TrnDate, AmountPaid
    FROM (

        SELECT
            REPLACE(DATE_FORMAT(TrnDate, '%Y%m%d'), '-', '') AS TrnId,
            TrnDate,
            SUM(AmountPaid) AS AmountPaid
        FROM (
            SELECT TrnDate, AmountPaid
            FROM pmms.loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND  LoanStatusReport <>'RenewedClosed'
            UNION ALL
            SELECT TrnDate, AmountPaid
            FROM pmms.arch_loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND  LoanStatusReport <> 'RenewedClosed'
        ) AS daily_combined
        GROUP BY DATE(TrnDate)

        UNION ALL


        SELECT
            '-' AS TrnId,
            '-' AS TrnDate,
            SUM(AmountPaid) AS AmountPaid
        FROM (
            SELECT AmountPaid
            FROM pmms.loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND  LoanStatusReport <> 'RenewedClosed'
            UNION ALL
            SELECT AmountPaid
            FROM pmms.arch_loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = 0.0
              AND  LoanStatusReport <> 'RenewedClosed'
        ) AS total_combined
    ) AS results
    ORDER BY TrnDate;

END //
DELIMITER ;

CALL collectionStatementPerDay('2024-01-01','2024-01-31');


-- UniqueID | StandardMonth | YearPaid | TotalAmountPaid

DROP PROCEDURE IF EXISTS collectionStatementPerMonth;

DELIMITER //
CREATE PROCEDURE collectionStatementPerMonth(IN startDate DATE, IN endDate DATE) READS SQL DATA
BEGIN
  

    SET @rownum := 0;


    SELECT
        @rownum := @rownum + 1 AS UniqueID,
        results.StandardMonth,
        results.YearPaid,
        results.TotalAmountPaid
    FROM (
        SELECT
            CASE
                WHEN MonthPaid IN ('1', '01') THEN 'JANUARY'
                WHEN MonthPaid IN ('2', '02') THEN 'FEBRUARY'
                WHEN MonthPaid IN ('3', '03') THEN 'MARCH'
                WHEN MonthPaid IN ('4', '04') THEN 'APRIL'
                WHEN MonthPaid IN ('5', '05') THEN 'MAY'
                WHEN MonthPaid IN ('6', '06') THEN 'JUNE'
                WHEN MonthPaid IN ('7', '07') THEN 'JULY'
                WHEN MonthPaid IN ('8', '08') THEN 'AUGUST'
                WHEN MonthPaid IN ('9', '09') THEN 'SEPTEMBER'
                WHEN MonthPaid = '10' THEN 'OCTOBER'
                WHEN MonthPaid = '11' THEN 'NOVEMBER'
                WHEN MonthPaid = '12' THEN 'DECEMBER'
                ELSE UPPER(MonthPaid)
            END AS StandardMonth,
            YearPaid,
            SUM(CAST(AmountPaid AS DECIMAL(10,2))) AS TotalAmountPaid
        FROM (
            SELECT MonthPaid, YearPaid, AmountPaid
            FROM pmms.loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = '0.0'
              AND NOT LoanStatusReport = 'RenewedClosed'

            UNION ALL

            SELECT MonthPaid, YearPaid, AmountPaid
            FROM pmms.arch_loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = '0.0'
              AND NOT LoanStatusReport = 'RenewedClosed'
        ) AS combined
        GROUP BY StandardMonth, YearPaid

        UNION ALL

        SELECT '-', '-', SUM(CAST(AmountPaid AS DECIMAL(10,2)))
        FROM (
            SELECT AmountPaid
            FROM pmms.loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = '0.0'
              AND NOT LoanStatusReport = 'RenewedClosed'

            UNION ALL

            SELECT AmountPaid
            FROM pmms.arch_loandisburserepaystatement
            WHERE (TrnDate >= startDate AND TrnDate <= endDate)
              AND ExpectedTotalAmount = '0.0'
              AND NOT LoanStatusReport = 'RenewedClosed'
        ) AS total_combined
    ) AS results
    ORDER BY
        CASE WHEN results.StandardMonth = '-' THEN 1 ELSE 0 END,
        CASE
            WHEN results.StandardMonth = 'JANUARY' THEN 1
            WHEN results.StandardMonth = 'FEBRUARY' THEN 2
            WHEN results.StandardMonth = 'MARCH' THEN 3
            WHEN results.StandardMonth = 'APRIL' THEN 4
            WHEN results.StandardMonth = 'MAY' THEN 5
            WHEN results.StandardMonth = 'JUNE' THEN 6
            WHEN results.StandardMonth = 'JULY' THEN 7
            WHEN results.StandardMonth = 'AUGUST' THEN 8
            WHEN results.StandardMonth = 'SEPTEMBER' THEN 9
            WHEN results.StandardMonth = 'OCTOBER' THEN 10
            WHEN results.StandardMonth = 'NOVEMBER' THEN 11
            WHEN results.StandardMonth = 'DECEMBER' THEN 12
            ELSE 13
        END;
END //
DELIMITER ;




CALL collectionStatementPerMonth('2024-01-01','2024-05-31');


-- DROP TABLE theSmsSummuryReport;
-- -----------------------------------------------------
-- Table theSmsSummuryReport
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS theSmsSummuryReport (
 reportId INT NOT NULL AUTO_INCREMENT,
 
  reportDate  DATE NOT NULL,
  
   reprtItemName varchar(200) NOT NULL,

      reportItemValue varchar(200) NOT NULL,

  PRIMARY KEY (reportId)
  
) ENGINE = InnoDB AUTO_INCREMENT = 1200 DEFAULT CHARACTER SET = utf8;




DROP PROCEDURE IF EXISTS WaveThePenaltyComponent;
DELIMITER //
CREATE PROCEDURE WaveThePenaltyComponent(IN theRunningInstalmentId INT,IN currentPenaltyX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE amountPaid,amountRemaining,penaltyPaid,penaltyRemaining  DOUBLE DEFAULT 0.0;

DECLARE TotalAmountPaid,TotalAmountRemaining,TotalPenaltyPaid,tpenaltyRemaining  DOUBLE DEFAULT 0.0;

DECLARE StatementAmountPaid,StatementAmountRemaining,StatementPenaltyPaid,StatementPenaltyRemaining  DOUBLE DEFAULT 0.0;

SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,LoanPenaltyPaid,LoanPenaltyRemaining INTO @amountPaid,@amountRemaining,@penaltyPaid,@penaltyRemaining FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT 
-- instalment_paid,
  -- InstalmentRemaining,
--  LoanPenaltyPaid,
-- LoanPenaltyRemaining INTO amountPaid,amountRemaining,penaltyPaid,penaltyRemaining FROM  new_loan_appstoreamort  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;



  SET amountPaid=@amountPaid+currentPenaltyX,
  amountRemaining=@amountRemaining-currentPenaltyX,
  penaltyPaid=@penaltyPaid+currentPenaltyX,penaltyRemaining=@penaltyRemaining-currentPenaltyX;


UPDATE new_loan_appstoreamort   SET 
  instalment_paid=amountPaid,
 InstalmentRemaining=amountRemaining,
 LoanPenaltyPaid=penaltyPaid,
LoanPenaltyRemaining=  penaltyRemaining  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

      -- SELECT 'PENALTY',amountRemaining; 
    IF amountRemaining<1 THEN  
UPDATE new_loan_appstoreamort   SET 
  instalment_status='P',
   instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date,instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

    END IF;


END//

 DELIMITER ;





DROP PROCEDURE IF EXISTS waveAccumulatedInterestComponent;
DELIMITER //
CREATE PROCEDURE waveAccumulatedInterestComponent(IN theRunningInstalmentId INT,IN currentAccumulatedInterestX DOUBLE,theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE amountPaid,amountRemaining,AccumulatedInterestPaidX,AccumulatedInterestRemainingX  DOUBLE DEFAULT 0.0;

-- SELECT 
--  instalment_paid,
--  InstalmentRemaining,
--  AccumulatedInterestPaid,
--   AccumulatedInterestRemaining INTO  amountPaid,amountRemaining,AccumulatedInterestPaidX,AccumulatedInterestRemainingX FROM new_loan_appstoreamort WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

  SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,AccumulatedInterestPaid,AccumulatedInterestRemaining INTO @amountPaid,@amountRemaining,@AccumulatedInterestPaidX,@AccumulatedInterestRemainingX FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


  SET amountPaid=@amountPaid+currentAccumulatedInterestX,
  amountRemaining=@amountRemaining-currentAccumulatedInterestX,
  AccumulatedInterestPaidX=@AccumulatedInterestPaidX+currentAccumulatedInterestX,AccumulatedInterestRemainingX=@AccumulatedInterestRemainingX-currentAccumulatedInterestX;


UPDATE   new_loan_appstoreamort   SET 
  instalment_paid=amountPaid,
  InstalmentRemaining=amountRemaining,
  AccumulatedInterestPaid=AccumulatedInterestPaidX,
  AccumulatedInterestRemaining=AccumulatedInterestRemainingX   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

    IF amountRemaining<1 THEN
     
UPDATE  new_loan_appstoreamort     SET 
  instalment_status='P',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

    END IF;



END //

 DELIMITER ;






DROP PROCEDURE IF EXISTS waveTheInterestComponent;
DELIMITER //
CREATE PROCEDURE waveTheInterestComponent(IN theAccountNumber VARCHAR(60),IN theRunningInstalmentId INT,IN currentInterestX DOUBLE,IN theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId,cycles INT;

DECLARE totalamountPaid,amountRemaining,InterestPaid,InterestRemaining,TotalAmountPaidT,TotalAmountRemainingT,TotalInterestPaidT,TotalInterestRemainingT,StatementExpecteTotal,StatementExpectedInterest,StatementAmountPaid,StatementAmountRemaining,StatementInterestPaid,StatementInterestRemaining   DOUBLE DEFAULT 0.0;
DECLARE theExistingLoanId VARCHAR(100);


    
  SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,InterestPaid,InterestRemaing,master2_id INTO @amountPaid,@amountRemaining,@InterestPaid,@InterestRemaining,@theLoanId FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

 SET @dueDateX = concat(CAST("SELECT instalments_paid, balance_due,TotalInterestPaid,TotalInterestRemaining INTO @TotalamountPaid,@TotalamountRemaining,@TotalInterestPaid,@TotalInterestRemaining FROM new_loan_appstore  WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
 select  @dueDate;
  PREPARE stmt2X FROM @dueDateX;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


 SET @dueDateXS = concat(CAST("SELECT loan_tenure INTO @tenure FROM new_loan_appstore1 WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanId);
 select  @dueDate;
  PREPARE stmt2XS FROM @dueDateXS;
  EXECUTE stmt2XS;
DROP PREPARE stmt2XS;

 SET @dueDateX1 = concat(CAST("SELECT ExpectedInterest,ExpectedTotalAmount, AmountPaid, LoanBalance,InterestPaid,InterestBalance,TrnId INTO @StateExpectedInterest,@StateExpectedTotalAmount,@StateTotalamountPaid,@StateTotalamountRemaining,@StateTotalInterestPaid,@StateTotalInterestRemaining,@theATrnId FROM pmms.loandisburserepaystatement  WHERE loanTrnId= " AS CHAR CHARACTER SET utf8),theLoanId,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
 select  @dueDate;
  PREPARE stmt2X1 FROM @dueDateX1;
  EXECUTE stmt2X1;
DROP PREPARE stmt2X1;



  SET totalamountPaid=@amountPaid+currentInterestX,
  amountRemaining=@amountRemaining-currentInterestX,
  InterestPaid=@InterestPaid+currentInterestX,
  InterestRemaining=@InterestRemaining-currentInterestX;

SELECT @TotalamountPaid,@TotalamountRemaining,@TotalInterestPaid,@TotalInterestRemaining;

  SET TotalAmountPaidT=@TotalamountPaid+currentInterestX,TotalAmountRemainingT=@TotalamountRemaining-currentInterestX,TotalInterestPaidT=@TotalInterestPaid+currentInterestX,TotalInterestRemainingT=@TotalInterestRemaining-currentInterestX;

SELECT @StateExpectedInterest,@StateExpectedTotalAmount,@StateTotalamountPaid,@StateTotalamountRemaining,@StateTotalInterestPaid,@StateTotalInterestRemaining,@theATrnId;

  SET StatementExpecteTotal=@StateExpectedTotalAmount-currentInterestX,StatementExpectedInterest=@StateExpectedInterest-currentInterestX,StatementAmountPaid=@StateTotalamountPaid+currentInterestX,StatementAmountRemaining=@StateTotalamountRemaining-currentInterestX,StatementInterestPaid=@StateTotalInterestPaid+currentInterestX,StatementInterestRemaining=@StateTotalInterestRemaining-currentInterestX;




UPDATE  new_loan_appstoreamort    SET 
  instalment_paid=totalamountPaid,
  InstalmentRemaining=amountRemaining,
  InterestPaid=InterestPaid,
  InterestRemaing=InterestRemaining   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

UPDATE new_loan_appstore SET instalments_paid=TotalAmountPaidT, balance_due=TotalAmountRemainingT,TotalInterestPaid=TotalInterestPaidT,TotalInterestRemaining=TotalInterestRemainingT WHERE trn_id=theLoanId;
  
 IF @StateExpectedTotalAmount>0 THEN

UPDATE pmms.loandisburserepaystatement SET ExpectedInterest=StatementExpectedInterest,ExpectedTotalAmount=StatementExpecteTotal,InterestBalance=StatementInterestRemaining,LoanBalance=StatementAmountRemaining WHERE TrnId=@theATrnId;

 ELSEIF @StateExpectedTotalAmount<=0 THEN

UPDATE pmms.loandisburserepaystatement SET AmountPaid=StatementAmountPaid,InterestPaid=StatementInterestPaid,InterestBalance=StatementInterestRemaining,LoanBalance=StatementAmountRemaining WHERE TrnId=@theATrnId;


 END IF;


IF @tenure='1.O MONTHS' OR @tenure='1 MONTHS' THEN
CALL DateManagementForLenders(currentInterestX,@theLoanId);
END IF;



    IF amountRemaining<1 THEN
UPDATE  new_loan_appstoreamort   SET 
 instalment_status='P',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

    END IF;



IF TotalAmountRemainingT<=2 THEN

SELECT loan_id INTO theExistingLoanId FROM new_loan_appstore where applicant_account_number=theAccountNumber AND loan_cycle_status='Completed' ORDER BY trn_id DESC LIMIT 1;

IF ISNULL(theExistingLoanId) THEN
SET cycles=1;
ELSEIF CHAR_LENGTH(theExistingLoanId)=22 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 1) INTO cycles;


ELSEIF CHAR_LENGTH(theExistingLoanId)=23 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 2) INTO cycles;

ELSEIF CHAR_LENGTH(theExistingLoanId)=24 THEN

SELECT SUBSTRING(theExistingLoanId, 11, 3) INTO cycles;

END IF;

SET @closedAccount=CONCAT('closedloan',cycles+1,theAccountNumber);


IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',51,theAccountNumber);
END IF;

IF EXISTS(SELECT * FROM new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SET @closedAccount=CONCAT('closedloan',51,theAccountNumber);
END IF;

UPDATE new_loan_appstore SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstore1 SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstoreamort SET instalment_status='P',master2_id=@closedAccount,instalment_paid_date=instalmentPaidDate WHERE master1_id=theLoanId;

UPDATE interestcomputed SET loanId=@closedAccount WHERE loanId=CONCAT("newloan",accountNumber);


UPDATE pmms.loandisburserepaystatement SET LoanStatusReport='Completed',LoanId=@closedAccount WHERE loanTrnId=theLoanId;


END IF;


END //

 DELIMITER ;




-- /* LOAN RECEIPT PRINTING */

-- DROP PROCEDURE IF EXISTS loanStatementDetails;

-- DELIMITER ##

-- CREATE PROCEDURE   loanStatementDetails(IN SloanTrnId VARCHAR(45))
-- BEGIN

-- DROP TABLE IF EXISTS loanStatementtDetailsTable;

-- CREATE TEMPORARY  TABLE loanStatementtDetailsTable(
-- `id` INTEGER NOT NULL AUTO_INCREMENT, 
-- `trn_date` DATE,
-- `amount_paid` VARCHAR(60),
-- `princimpal_paid` VARCHAR(60),
-- `interest_paid` VARCHAR(60),
-- `amount_remaining` VARCHAR(60),
-- `princimpal_remaining`  VARCHAR(60),
-- `interest_remaining`  VARCHAR(60),
--  PRIMARY KEY (`id`))
-- ENGINE = InnoDB
-- AUTO_INCREMENT =0
-- DEFAULT CHARACTER SET = utf8;

  

-- INSERT INTO  loanStatementtDetailsTable( 
--   `id` ,
--   `trn_date` ,
--       `amount_paid`,
--      `princimpal_paid`,
--   `interest_paid`,
--         `amount_remaining`,
--           `princimpal_remaining`,
--           `interest_remaining`
--   ) SELECT  null,`TrnDate` ,FORMAT(`AmountPaid`,0) ,  FORMAT(`PrincipalPaid`,0) ,  FORMAT(`InterestPaid`,0) ,  FORMAT(`LoanBalance`,0) ,  FORMAT(`PrincipalBalance`,0) ,  FORMAT(`InterestBalance`,0)  FROM loandisburserepaystatement WHERE loanTrnId=SloanTrnId LIMIT 1,20000;


--    SELECT * FROM loanStatementtDetailsTable;

-- END  ##

--  DELIMITER ;



DROP PROCEDURE IF EXISTS loanStatementDetails;
DELIMITER ##
CREATE PROCEDURE loanStatementDetails(IN SloanTrnId VARCHAR(45))
BEGIN

    DROP TABLE IF EXISTS loanStatementtDetailsTable;

    CREATE TEMPORARY TABLE loanStatementtDetailsTable (
      id                    INTEGER NOT NULL AUTO_INCREMENT, 
      trn_date              DATE,
      amount_paid           VARCHAR(60),
      princimpal_paid       VARCHAR(60),
      interest_paid         VARCHAR(60),
      amount_remaining      VARCHAR(60),
      princimpal_remaining  VARCHAR(60),
      interest_remaining    VARCHAR(60),
      PRIMARY KEY (id)
    )
    ENGINE = InnoDB
    AUTO_INCREMENT = 0
    DEFAULT CHARACTER SET = utf8;

    INSERT INTO loanStatementtDetailsTable (
      id,
      trn_date,
      amount_paid,
      princimpal_paid,
      interest_paid,
      amount_remaining,
      princimpal_remaining,
      interest_remaining
    )
    SELECT 
      null,
      TrnDate,
      FORMAT(AmountPaid, 0),
      FORMAT(PrincipalPaid, 0),
      FORMAT(InterestPaid, 0),
      FORMAT(LoanBalance, 0),
      FORMAT(PrincipalBalance, 0),
      FORMAT(InterestBalance, 0)
    FROM (
      SELECT TrnDate, AmountPaid, PrincipalPaid, InterestPaid, LoanBalance, PrincipalBalance, InterestBalance
      FROM loandisburserepaystatement
      WHERE loanTrnId = SloanTrnId
      UNION ALL
      SELECT TrnDate, AmountPaid, PrincipalPaid, InterestPaid, LoanBalance, PrincipalBalance, InterestBalance
      FROM arch_loandisburserepaystatement
      WHERE loanTrnId = SloanTrnId
    ) AS combined
    LIMIT 1, 20000;

    SELECT * FROM loanStatementtDetailsTable;

END ##
DELIMITER ;


DROP PROCEDURE IF EXISTS loanStatementDetailsExpected;
DELIMITER //
CREATE PROCEDURE loanStatementDetailsExpected(IN accountNumber VARCHAR(60)) READS SQL DATA 
BEGIN

DECLARE StateExpectedInterest,StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalInterestPaid,StateTotalInterestRemaining,theATrnId DOUBLE;

 SET @dueDateX1 = concat(CAST("SELECT  ExpectedInterest,ExpectedTotalAmount, AmountPaid, LoanBalance,InterestPaid,InterestBalance,TrnId INTO @StateExpectedInterest, @StateExpectedTotalAmount,@StateTotalamountPaid,@StateTotalamountRemaining,@StateTotalInterestPaid,@StateTotalInterestRemaining,@theATrnId FROM pmms.loandisburserepaystatement  WHERE AccountNumber= " AS CHAR CHARACTER SET utf8),accountNumber,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
--  select  @dueDateX1;
  PREPARE stmt2X1 FROM @dueDateX1;
  EXECUTE stmt2X1;
DROP PREPARE stmt2X1;

 SET StateExpectedInterest=@StateExpectedInterest, StateExpectedTotalAmount=@StateExpectedTotalAmount,StateTotalamountPaid=@StateTotalamountPaid,StateTotalamountRemaining=@StateTotalamountRemaining,StateTotalInterestPaid=@StateTotalInterestPaid,StateTotalInterestRemaining=@StateTotalInterestRemaining,theATrnId=@theATrnId;

SELECT StateExpectedInterest, StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalInterestPaid,StateTotalInterestRemaining,theATrnId;


END//

 DELIMITER ;

CALL loanStatementDetailsExpected('05502062210')\G






DROP PROCEDURE IF EXISTS accumuStatementDetails;
DELIMITER //
CREATE PROCEDURE accumuStatementDetails(IN accountNumber VARCHAR(60)) READS SQL DATA 
BEGIN

DECLARE StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalAccumInterestPaid,StateTotalAccumInterestRemaining,theATrnId DOUBLE;

 SET @dueDateX1 = concat(CAST("SELECT ExpectedTotalAmount, AmountPaid, LoanBalance,AccumulatedInterestPaid,AccumulatedInterestBalance,TrnId INTO @StateExpectedTotalAmount,@StateTotalamountPaid,@StateTotalamountRemaining,@StateTotalAccumInterestPaid,@StateTotalAccumInterestRemaining,@theATrnId FROM pmms.loandisburserepaystatement  WHERE AccountNumber= " AS CHAR CHARACTER SET utf8),accountNumber,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
--  select  @dueDateX1;
  PREPARE stmt2X1 FROM @dueDateX1;
  EXECUTE stmt2X1;
DROP PREPARE stmt2X1;

 SET StateExpectedTotalAmount=@StateExpectedTotalAmount,StateTotalamountPaid=@StateTotalamountPaid,StateTotalamountRemaining=@StateTotalamountRemaining,StateTotalAccumInterestPaid=@StateTotalAccumInterestPaid,StateTotalAccumInterestRemaining=@StateTotalAccumInterestRemaining,theATrnId=@theATrnId;

SELECT StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalAccumInterestPaid,StateTotalAccumInterestRemaining,theATrnId;


END//

 DELIMITER ;

CALL accumuStatementDetails('05502062210')\G







DROP PROCEDURE IF EXISTS penaltyStatementDetails;
DELIMITER //
CREATE PROCEDURE penaltyStatementDetails(IN accountNumber VARCHAR(60)) READS SQL DATA 
BEGIN
            
                                     
DECLARE StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalPenaltyPaid,StateTotalPenaltyRemaining,theATrnId DOUBLE;

 SET @dueDateX1 = concat(CAST("SELECT ExpectedTotalAmount, AmountPaid, LoanBalance,LoanPenaltyPaid,LoanPenaltyBalance,TrnId INTO @StateExpectedTotalAmount,@StateTotalamountPaid,@StateTotalamountRemaining,@StateTotalPenaltyPaid,@StateTotalPenaltyRemaining,@theATrnId FROM pmms.loandisburserepaystatement  WHERE AccountNumber= " AS CHAR CHARACTER SET utf8),accountNumber,CAST(" ORDER BY TrnId DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X1 FROM @dueDateX1;
  EXECUTE stmt2X1;
DROP PREPARE stmt2X1;

 SET StateExpectedTotalAmount=@StateExpectedTotalAmount,StateTotalamountPaid=@StateTotalamountPaid,StateTotalamountRemaining=@StateTotalamountRemaining,StateTotalPenaltyPaid=@StateTotalPenaltyPaid,StateTotalPenaltyRemaining=@StateTotalPenaltyRemaining,theATrnId=@theATrnId;

SELECT StateExpectedTotalAmount,StateTotalamountPaid,StateTotalamountRemaining,StateTotalPenaltyPaid,StateTotalPenaltyRemaining,theATrnId;


END//

 DELIMITER ;

CALL penaltyStatementDetails('05502062210')\G


 ALTER TABLE new_loan_appstore MODIFY COLUMN  loan_id VARCHAR(100) NOT NULL UNIQUE;
   ALTER TABLE new_loan_appstore1 MODIFY COLUMN  loan_id VARCHAR(100) NOT NULL UNIQUE;
       ALTER TABLE new_loan_appstore MODIFY COLUMN applicant_account_name VARCHAR(200) NOT NULL;
       UPDATE  new_loan_appstore SET loan_tenure='30 DAYS' WHERE loan_tenure ='30 MONTHS';
       UPDATE  new_loan_appstore1 SET loan_tenure='30 DAYS' WHERE loan_tenure ='30 MONTHS';


       
-- DROP PROCEDURE IF EXISTS theGaurantorDetails;
-- DELIMITER //
-- CREATE PROCEDURE theGaurantorDetails(IN theLoanTrnId INT) READS SQL DATA 
-- BEGIN


-- SELECT * FROM gaurantors WHERE loanTrnId=theLoanTrnId;

-- END//

--  DELIMITER ;

/* rebuild safely */
DROP PROCEDURE IF EXISTS theGaurantorDetails;
DELIMITER $$

CREATE PROCEDURE theGaurantorDetails (IN pLoanTrnId INT)
READS SQL DATA
BEGIN
    /* ----------------------------------------------------------
       1.  Get the borrower’s account number for that loan
    ---------------------------------------------------------- */
    DECLARE vAccountNumber VARCHAR(200);

    /* live loan first … */
    SELECT applicant_account_number
      INTO vAccountNumber
      FROM new_loan_appstore
     WHERE trn_id = pLoanTrnId
     LIMIT 1;

    /* … if not found, try the archive loan table */
    IF vAccountNumber IS NULL THEN
        SELECT applicant_account_number
          INTO vAccountNumber
          FROM arch_new_loan_appstore
         WHERE trn_id = pLoanTrnId
         LIMIT 1;
    END IF;

    /* ----------------------------------------------------------
       2.  Return all guarantors for this borrower
           • live table + archive table
           • no accountNumber column in the result
    ---------------------------------------------------------- */
    SELECT
        id                                 AS id,
        gaurantorsName                     AS gaurantorsName,
        gaurantorsContact1                 AS gaurantorsContact1,
        gaurantorsContact2                 AS gaurantorsContact2,
        gaurantorsIdNumber                 AS gaurantorsIdNumber,
        gaurantorsRelationWithBorrower     AS gaurantorsRelationWithBorrower,
        gaurantorsHomeArea                 AS gaurantorsHomeArea,
        gaurantorsBusiness                 AS gaurantorsBusiness,
        loanTrnId                          AS loanTrnId
    FROM gaurantors
    WHERE accountNumber = vAccountNumber

    UNION ALL

    SELECT
        original_id                        AS id,
        gaurantorsName,
        gaurantorsContact1,
        gaurantorsContact2,
        gaurantorsIdNumber,
        gaurantorsRelationWithBorrower,
        gaurantorsHomeArea,
        gaurantorsBusiness,
        loanTrnId
    FROM gaurantors_archive
    WHERE accountNumber = vAccountNumber;
END$$
DELIMITER ;


--  SELECT applicant_account_number FROM new_loan_appstore WHERE trn_id = 79928 LIMIT 1;

--  SELECT
--         id                                 AS id,
--         gaurantorsName                     AS gaurantorsName,
--         gaurantorsContact1                 AS gaurantorsContact1,
--         gaurantorsContact2                 AS gaurantorsContact2,
--         gaurantorsIdNumber                 AS gaurantorsIdNumber,
--         gaurantorsRelationWithBorrower     AS gaurantorsRelationWithBorrower,
--         gaurantorsHomeArea                 AS gaurantorsHomeArea,
--         gaurantorsBusiness                 AS gaurantorsBusiness,
--         loanTrnId                          AS loanTrnId
--     FROM gaurantors
--     WHERE accountNumber = '05502024110'

--     UNION ALL

--     SELECT
--         original_id                        AS id,
--         gaurantorsName,
--         gaurantorsContact1,
--         gaurantorsContact2,
--         gaurantorsIdNumber,
--         gaurantorsRelationWithBorrower,
--         gaurantorsHomeArea,
--         gaurantorsBusiness,
--         loanTrnId
--     FROM gaurantors_archive
--     WHERE accountNumber ='05502024110';

-- Debits	Return on Investment Expense	02277000110	Return On Investment Expense	0.1290310025215149

-- DROP TABLE processingFeesRanges;
-- -----------------------------------------------------
-- Table processingFeesRanges
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS processingFeesRanges (

 id INT NOT NULL AUTO_INCREMENT,
 
  lowerRange  DOUBLE NOT NULL DEFAULT 20000.0,
  
  upperRange  DOUBLE NOT NULL DEFAULT 20000.0,

  processingFees  DOUBLE NOT NULL DEFAULT 20000.0,

    computeType  VARCHAR(50) NOT NULL,

  PRIMARY KEY (id)
  
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8;

-- alter table processingFeesRanges modify column  computeType VARCHAR(50);
-- UPDATE processingFeesRanges SET computeType= 'FIXED FEES';



DROP TABLE allowCompOfProFees;
-- -----------------------------------------------------
-- Table allowCompOfProFees
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS allowCompOfProFees (

 id INT NOT NULL,
 id1 INT NOT NULL 
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

INSERT INTO allowCompOfProFees (id,id1) VALUES(1,1);



-- DROP TABLE allowSavingsWithdraws;
-- -----------------------------------------------------
-- Table allowSavingsWithdraws
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS allowSavingsWithdraws (

 id INT NOT NULL 
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- DROP TABLE controlPenaltyCompute;
-- -----------------------------------------------------
-- Table controlPenaltyCompute
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS controlPenaltyCompute (

 id INT NOT NULL ,
inOutx INT NOT NULL ,
 NoofDays INT NOT NULL 
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

ALTER TABLE pmms_loans.controlPenaltyCompute modify column  id enum('1') NOT NULL;



-- DROP TABLE amdaPenaltyDetails;
-- -----------------------------------------------------
-- Table amdaPenaltyDetails
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS amdaPenaltyDetails (

 id INT NOT NULL ,
instalment_id INT NOT NULL ,
loan_id INT NOT NULL ,
 new_due_date DATE NOT NULL 
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- ALTER TABLE pmms_loans.controlPenaltyCompute modify column  id enum('1') NOT NULL;



-- ("+"'"+processRangeDetails.get(0).toString()+"'"+","+processRangeDetails.get(1).toString()+"'"+","+"'"+processRangeDetails.get(2).toString()+"'"+","+processRangeDetails.get(3).toString()+")";

ALTER TABLE `processingfeesranges`
ADD COLUMN `processType` ENUM('PROCESS_FEES', 'SAVING_WITHDRAW') DEFAULT 'PROCESS_FEES';


-- -----------------------------------------------------
-- createdRenewedLoan

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createLoanFeesRanges;

DELIMITER $$

CREATE PROCEDURE createLoanFeesRanges(
  IN lowerPrincipalRange DOUBLE,
   IN upperPrincipalRange DOUBLE,
  IN feesValue DOUBLE,
  IN computeType VARCHAR(50)
 ) BEGIN
  DECLARE the_type ENUM('PROCESS_FEES', 'SAVING_WITHDRAW');
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
  SELECT 0 AS theMessage;
  ROLLBACK;
END;
 
-- DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   GET DIAGNOSTICS CONDITION 1
--   -- @ERRNO=MYSQL_ERRNO,@MESSAGE_TEXT=MESSAGE_TEXT,@STATE=RETURNED_SQLSTATE;
--   SELECT 'DATABASE ERROR' AS theMessage,@MESSAGE_TEXT AS MESSAGE;
--   ROLLBACK;
  
-- END;


 
START TRANSACTION;


IF computeType='PERCENT OF PRINCIPAL' OR computeType='FIXED FEES' THEN
SET the_type='PROCESS_FEES';
ELSE
SET the_type='SAVING_WITHDRAW';
END IF;





INSERT INTO  processingFeesRanges VALUES(
  NULL,
  lowerPrincipalRange,
  upperPrincipalRange,
  feesValue,
  computeType,
  the_type
  );

COMMIT;

SELECT 1  AS theMessage;

END $$

DELIMITER ;

-- delete from loanArrearsSettings;
--   INSERT INTO loanArrearsSettings VALUES (NULL,0,4,1,1,10.0,2,1,1);
SELECT (CASE WHEN NOT EXISTS (SELECT processingFees FROM processingFeesRanges WHERE lowerRange <= 1000000 AND upperRange >= 1000000) THEN 100 ELSE (SELECT processingFees FROM processingFeesRanges WHERE lowerRange <=1000000 AND upperRange >= 1000000) END) as processingFees;




-- -----------------------------------------------------
-- updateLoanFeesRanges

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS updateLoanFeesRanges;

DELIMITER $$

CREATE PROCEDURE updateLoanFeesRanges(
  IN theLowerPrincipalRange DOUBLE,
   IN theUpperPrincipalRange DOUBLE,
  IN theFeesValue DOUBLE,
  IN theComputeType VARCHAR(50),
   IN theId INT
 ) BEGIN
  DECLARE the_type ENUM('PROCESS_FEES', 'SAVING_WITHDRAW');
  
--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
-- END;
 
-- DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   GET DIAGNOSTICS CONDITION 1
--   -- @ERRNO=MYSQL_ERRNO,@MESSAGE_TEXT=MESSAGE_TEXT,@STATE=RETURNED_SQLSTATE;
--   SELECT 'DATABASE ERROR' AS theMessage,@MESSAGE_TEXT AS MESSAGE;
--   ROLLBACK;
  
-- END;
 
-- START TRANSACTION;

IF theComputeType='PERCENT OF PRINCIPAL' OR theComputeType='FIXED FEES' THEN
SET the_type='PROCESS_FEES';
ELSE
SET the_type='SAVING_WITHDRAW';
END IF;


SELECT theLowerPrincipalRange,theUpperPrincipalRange,theFeesValue,theComputeType,the_type,theId;

UPDATE    processingFeesRanges SET 
   lowerRange=theLowerPrincipalRange,
  upperRange=theUpperPrincipalRange,
  processingFees=theFeesValue,
  computeType=theComputeType,processType=the_type WHERE id=theId
  ;

-- COMMIT;

SELECT 1  AS theMessage;

END $$

DELIMITER ;


update processingFeesRanges SET processType='SAVING_WITHDRAW' WHERE computeType='SAVING_FIXED' OR computeType='SAVING_PERCENT_BALANCE';

-- -----------------------------------------------------
-- theClosedRenewedLoan

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS theClosedRenewedLoan;

DELIMITER $$

CREATE PROCEDURE theClosedRenewedLoan( IN theLoanId INT) BEGIN

  
UPDATE pmms_loans.arch_new_loan_appstore SET loan_cycle_status='RenewedClosed' WHERE trn_id=theLoanId;
UPDATE pmms_loans.arch_new_loan_appstore1 SET loan_cycle_status='RenewedClosed' WHERE trn_id=theLoanId;
UPDATE pmms.arch_loandisburserepaystatement SET LoanStatusReport='RenewedClosed' WHERE loanTrnId=theLoanId;

END $$

DELIMITER ;



ALTER TABLE new_loan_appstoreamort ADD INDEX idx_master1_id (master1_id);
ALTER TABLE new_loan_appstoreamort ADD INDEX idx_master2_id (master2_id);
ALTER TABLE new_loan_appstoreamort ADD INDEX idx_trn_id (trn_id);
ALTER TABLE new_loan_appstore ADD INDEX idx_loan_id (loan_id);
ALTER TABLE new_loan_appstore ADD INDEX idx_applicant_account_number (applicant_account_number);
ALTER TABLE new_loan_appstore ADD INDEX idx_trn_id (trn_id);
CREATE INDEX idx_loan_id ON new_loan_appstore1 (loan_id);
CREATE INDEX idx_applicant_account_number ON new_loan_appstore1 (applicant_account_number);
CREATE INDEX idx_trn_id ON new_loan_appstore1 (trn_id);
CREATE INDEX idx_trn_date ON new_loan_appstore (trn_date);
CREATE INDEX idx_instalment_start_date ON new_loan_appstore (instalment_start_date);
CREATE INDEX idx_instalment_next_due_date ON new_loan_appstore (instalment_next_due_date);
CREATE INDEX idx_instalment_end_date ON new_loan_appstore (instalment_end_date);
CREATE INDEX idx_applicant_account_number ON new_loan_appstore (applicant_account_number);
CREATE INDEX idx_loan_cycle_status ON new_loan_appstore (loan_cycle_status);
CREATE INDEX idx_instalment_due_date ON new_loan_appstoreamort (instalment_due_date);
CREATE INDEX idx_instalment_paid_date ON new_loan_appstoreamort (instalment_paid_date);
CREATE INDEX idx_instalment_status ON new_loan_appstoreamort (instalment_status);
CREATE INDEX idx_master1_id_instalment_due_date ON new_loan_appstoreamort (master1_id, instalment_due_date);
CREATE INDEX idx_trn_date1 ON new_loan_appstore1 (trn_date);
CREATE INDEX idx_instalment_start_date1 ON new_loan_appstore1 (instalment_start_date);
CREATE INDEX idx_instalment_next_due_date1 ON new_loan_appstore1 (instalment_next_due_date);
CREATE INDEX idx_instalment_end_date1 ON new_loan_appstore1 (instalment_end_date);
CREATE INDEX idx_applicant_account_number1 ON new_loan_appstore1 (applicant_account_number);
CREATE INDEX idx_loan_cycle_status1 ON new_loan_appstore1 (loan_cycle_status);





DROP PROCEDURE IF EXISTS dailyCollectionInstalmentStatementActiveCustomers;

DELIMITER //



CREATE PROCEDURE dailyCollectionInstalmentStatementActiveCustomers(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedAmountToday,AmountCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
 SET Ids=Ids+1;
   
   SET @Varince=@actualToday-@ExpectedAm;
   
   IF @Varince>=0.0 THEN
   
   SET @Vstatus='+Ve';
   
   SET @Varince=0.0;
   
   END IF;
   
   IF @Varince<0.0 THEN
   
   SET @Vstatus='-Ve';
   
   SET @Varince=@Varince*-1;
 
   END IF;
   
   

 INSERT INTO temp_dailycollection VALUES(Ids,@narration,@ExpectedAm,@actualToday,@Varince,@Vstatus);


SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;
 


SELECT id ,temp_NarrationC,temp_ExpectedCollection ,temp_ActualCollection,temp_BalColl,temp_Variance  FROM temp_dailycollection;


END //

DELIMITER ;

-- CALL dailyCollectionInstalmentStatementActiveCustomers('2019-05-23');

-- DELETE new_loan_appstore,new_loan_appstoreamort from new_loan_appstore INNER JOIN new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.loan_cycle_status='Completed' AND new_loan_appstore.trn_date<='2023-08-31';

-- DELETE pmms_loans.new_loan_appstore1,pmms.loandisburserepaystatement from pmms_loans.new_loan_appstore1 INNER JOIN pmms.loandisburserepaystatement ON new_loan_appstore1.trn_id=loandisburserepaystatement.loanTrnId WHERE new_loan_appstore1.loan_cycle_status='Completed' AND new_loan_appstore1.trn_date<='2023-08-31';

-- DROP TABLE IF EXISTS DailyCollection;

CREATE TABLE DailyCollection (
  CollectionId int(11) NOT NULL AUTO_INCREMENT,
  CollectionDate date NOT NULL,
  accountName varchar(100) DEFAULT '0',
  accountNumber varchar(100) DEFAULT '0',
  PhoneNumber varchar(100) DEFAULT '0',
  loanID varchar(100) DEFAULT '0',
  trnId INT,
  loansOfficerId INT,
  loanStatus varchar(100) DEFAULT '0',
  disbursementDate date,
  gourantorName varchar(100) DEFAULT '0',
  gourantorPhone varchar(100) DEFAULT '0',
  princimpalDisbursed varchar(100) DEFAULT '0',
  totalExpectedInterest varchar(100) DEFAULT '0',
  totalAmountPaid varchar(100) DEFAULT '0',
  totalAmountRemaining varchar(100) DEFAULT '0',
  ExpectedPrincipalArrears varchar(100) DEFAULT '0',
  ExpectedPrincipalToday varchar(100) DEFAULT '0',
  ExpectedTotalPrincimpalToday varchar(100) DEFAULT '0',
  PrincimpalCollectedToday varchar(100) DEFAULT '0',
  ExpectedInterestArrears varchar(100) DEFAULT '0',
  ExpectedInterestToday varchar(100) DEFAULT '0',
  ExpectedTotalInterestToday varchar(100) DEFAULT '0',
  InterestCollectedToday varchar(100) DEFAULT '0',
  ExpectedAccumInterestArrears varchar(100) DEFAULT '0',
  ExpectedAccumInterestToday varchar(100) DEFAULT '0',
  ExpectedTotalIAccumnterestToday varchar(100) DEFAULT '0',
  AccumInterestCollectedToday varchar(100) DEFAULT '0',
  ExpectedPenaltyArrears varchar(100) DEFAULT '0',
  ExpectedPenaltyToday varchar(100) DEFAULT '0',
  ExpectedTotalIPenaltyToday varchar(100) DEFAULT '0',
  PenaltyCollectedToday varchar(100) DEFAULT '0',
  ExpectedAmountInArrears varchar(100) DEFAULT '0',
  ExpectedAmountToday varchar(100) DEFAULT '0',
  ExpectedTotalAmountToday varchar(100) DEFAULT '0',
  AmountCollectedToday varchar(100) DEFAULT '0',
  NumberOfInstalmentsInArrears varchar(100) DEFAULT '0',
  LastPayementDate date,
  NumberOfDaysSinceLastPayment INT,
  NumberOfDaysInArrears INT,
  VarianceCollection varchar(100) DEFAULT '0',
  typeOfLoanTransaction ENUM('PAYMENT', 'DISBURSEMENT') DEFAULT 'PAYMENT',
  typeOfDisbursement ENUM('NEW', 'OLD','NCO') DEFAULT 'NCO',
  OtherFive varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (CollectionId)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;

DELIMITER //

CREATE TRIGGER before_insert_daily_collection
BEFORE INSERT ON DailyCollection
FOR EACH ROW
BEGIN
  IF NEW.disbursementDate IS NULL THEN
    SET NEW.disbursementDate = CURDATE();
  END IF;
  IF NEW.LastPayementDate IS NULL THEN
    SET NEW.LastPayementDate = CURDATE();
  END IF;
END //

DELIMITER ;



-- DROP PROCEDURE IF EXISTS prepareDailyReport;

-- DELIMITER //

-- CREATE PROCEDURE prepareDailyReport()
-- READS SQL DATA
-- BEGIN

--   DECLARE l_done INTEGER DEFAULT 0;
--   DECLARE ledger_id,loanOfficerId INT;
--   DECLARE borrower_name VARCHAR(200);
--   DECLARE the_account_number VARCHAR(200);
--   DECLARE phone1 VARCHAR(200);
--   DECLARE trnDateNow DATE;
--   DECLARE lastTransactedDate DATE;
--   DECLARE arrearsDate DATE;
--   DECLARE guarantor_name VARCHAR(100);
--   DECLARE guarantor_contact1 VARCHAR(100);
--   DECLARE the_loan_id VARCHAR(100);
--   DECLARE the_loan_status VARCHAR(100);

--   DECLARE select_trn_ids CURSOR FOR 
--     SELECT trn_id FROM new_loan_appstore WHERE NOT ( loan_cycle_status = 'RenewedClosed' OR loan_cycle_status = 'WrittenOff' OR loan_cycle_status = 'Completed');
  
--   DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

--   prepare_daily_report: BEGIN
--     -- Purge DailyCollection records that are 8 months old or older
--     DELETE FROM DailyCollection
--     WHERE CollectionDate <= DATE_SUB(CURDATE(), INTERVAL 8 MONTH);

--     OPEN select_trn_ids;

--     SET @N = 0;
--     LedgerIds_loop: LOOP 

--       FETCH select_trn_ids INTO ledger_id;

--       IF l_done = 1 THEN
--         LEAVE LedgerIds_loop;
--       END IF;

--       -- Skip this trnId if it already has any DailyCollection row for today
--       IF EXISTS (
--         SELECT 1
--         FROM DailyCollection
--         WHERE CollectionDate = CURDATE()
--           AND trnId = ledger_id
--       ) THEN
--         ITERATE LedgerIds_loop;
--       END IF;

--       -- Select main details from new_loan_appstore
--             SET @query = CONCAT(
--                 'SELECT applicant_account_name, applicant_account_number, loan_id, loan_cycle_status, trn_date, gruop_id ',
--                 'INTO @borrower_name, @the_account_number, @the_loan_id, @the_loan_status, @trnDateNow, @loanOfficerId ',
--                 'FROM new_loan_appstore WHERE trn_id = ', ledger_id
--             );

--             PREPARE stmt FROM @query;
--             EXECUTE stmt;
--             DEALLOCATE PREPARE stmt;

--       SET borrower_name = @borrower_name;
--       SET the_account_number = @the_account_number;
--       SET the_loan_id = @the_loan_id;
--       SET the_loan_status = @the_loan_status;
--       SET trnDateNow = @trnDateNow;
--       SET loanOfficerId=@loanOfficerId;

--       SELECT COALESCE(mobile1, '-') INTO phone1 
--       FROM pmms.master 
--       WHERE account_number = the_account_number 
--       ORDER BY TrnId ASC 
--       LIMIT 1;

--       SELECT COALESCE(gaurantorsName, '-'), COALESCE(gaurantorsContact1, '-') 
--       INTO guarantor_name, guarantor_contact1 
--       FROM gaurantors 
--       WHERE loanTrnId = ledger_id 
--       ORDER BY id ASC 
--       LIMIT 1;

--       SELECT 
--         COALESCE(SUM(princimpal_amount), '0.0'), 
--         COALESCE(SUM(interest_amount), '0.0')
--       INTO 
--         @princimpalDisbursed, 
--         @totalExpectedInterest
--       FROM new_loan_appstoreamort 
--       WHERE master1_id = ledger_id;

--       SELECT 
--         COALESCE(SUM(instalment_paid), '0.0'), 
--         COALESCE(SUM(InstalmentRemaining), '0.0')
--       INTO 
--         @totalAmountPaid, 
--         @totalAmountRemaining 
--       FROM new_loan_appstoreamort 
--       WHERE master1_id = ledger_id;

--       SELECT 
--         COALESCE(SUM(PrincipalRemaining), '0.0'), 
--         COALESCE(SUM(InterestRemaing), '0.0'), 
--         COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
--         COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
--         COALESCE(SUM(InstalmentRemaining), '0.0'), 
--         COALESCE(COUNT(trn_id), '0.0') 
--       INTO 
--         @principal_arrears, 
--         @interest_arrears, 
--         @accum_interest_arrears, 
--         @penalty_arrears, 
--         @instalment_arrears, 
--         @instalments_arrears 
--       FROM new_loan_appstoreamort 
--       WHERE instalment_due_date < CURDATE() 
--         AND instalment_status <> 'P' 
--         AND master1_id = ledger_id;

--       SELECT 
--         COALESCE(SUM(PrincipalRemaining), '0.0'), 
--         COALESCE(SUM(InterestRemaing), '0.0'), 
--         COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
--         COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
--         COALESCE(SUM(InstalmentRemaining), '0.0') 
--       INTO 
--         @principal_due_today, 
--         @interest_due_today, 
--         @accum_interest_due_today, 
--         @penalty_due_today, 
--         @instalment_due_today 
--       FROM new_loan_appstoreamort 
--       WHERE instalment_due_date = CURDATE() 
--         AND instalment_status <> 'P' 
--         AND master1_id = ledger_id;

--       SELECT 
--         COALESCE(SUM(PrincipalRemaining), '0.0'), 
--         COALESCE(SUM(InterestRemaing), '0.0'), 
--         COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
--         COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
--         COALESCE(SUM(InstalmentRemaining), '0.0') 
--       INTO 
--         @total_principal_due, 
--         @total_interest_due, 
--         @total_accum_interest_due, 
--         @total_penalty_due, 
--         @total_instalments_due 
--       FROM new_loan_appstoreamort 
--       WHERE instalment_due_date <= CURDATE() 
--         AND instalment_status <> 'P' 
--         AND master1_id = ledger_id;

--       SET @sql_query = CONCAT(
--         'SELECT COALESCE(instalment_paid_date, ''', trnDateNow, ''') INTO @lastTransactedDate ',
--         'FROM new_loan_appstoreamort ',
--         'WHERE master1_id = ', ledger_id, ' ',
--         'AND (instalment_status = ''P'' OR instalment_status = ''PP'') ',
--         'ORDER BY trn_id DESC LIMIT 1'
--       );

--       PREPARE stmt FROM @sql_query;
--       EXECUTE stmt;
--       DEALLOCATE PREPARE stmt;

--       SET lastTransactedDate = @lastTransactedDate;

--       SELECT instalment_due_date INTO @arrearsDate
--       FROM new_loan_appstoreamort
--       WHERE instalment_due_date <= CURDATE()
--         AND master1_id = ledger_id
--       ORDER BY trn_id ASC
--       LIMIT 1;

--       SET @number_of_days_in_arrears = DATEDIFF(CURDATE(), @arrearsDate);

--       INSERT INTO DailyCollection (
--         CollectionId,
--         CollectionDate,
--         accountName,
--         accountNumber,
--         PhoneNumber,
--         loanID,
--         trnId,
--         loansOfficerId,
--         loanStatus,
--         disbursementDate,
--         gourantorName,
--         gourantorPhone,
--         princimpalDisbursed,
--         totalExpectedInterest,
--         totalAmountPaid,
--         totalAmountRemaining,
--         ExpectedPrincipalArrears,
--         ExpectedPrincipalToday,
--         ExpectedTotalPrincimpalToday,
--         PrincimpalCollectedToday,
--         ExpectedInterestArrears,
--         ExpectedInterestToday,
--         ExpectedTotalInterestToday,
--         InterestCollectedToday,
--         ExpectedAccumInterestArrears,
--         ExpectedAccumInterestToday,
--         ExpectedTotalIAccumnterestToday,
--         AccumInterestCollectedToday,
--         ExpectedPenaltyArrears,
--         ExpectedPenaltyToday,
--         ExpectedTotalIPenaltyToday,
--         PenaltyCollectedToday,
--         ExpectedAmountInArrears,
--         ExpectedAmountToday,
--         ExpectedTotalAmountToday,
--         AmountCollectedToday,
--         NumberOfInstalmentsInArrears,
--         LastPayementDate,
--         NumberOfDaysSinceLastPayment,
--         NumberOfDaysInArrears,
--         VarianceCollection,
--         typeOfLoanTransaction,
--         typeOfDisbursement,
--         OtherFive
--       ) VALUES (
--         NULL, 
--         CURDATE(), 
--         borrower_name, 
--         the_account_number, 
--         phone1, 
--         the_loan_id, 
--         ledger_id,
--         loanOfficerId, 
--         the_loan_status,
--         trnDateNow,   
--         guarantor_name, 
--         guarantor_contact1, 
--         @princimpalDisbursed, 
--         @totalExpectedInterest, 
--         @totalAmountPaid, 
--         @totalAmountRemaining, 
--         @principal_arrears, 
--         @principal_due_today, 
--         @total_principal_due, 
--         '0.0', 
--         @interest_arrears, 
--         @interest_due_today, 
--         @total_interest_due, 
--         '0.0', 
--         @accum_interest_arrears, 
--         @accum_interest_due_today, 
--         @total_accum_interest_due, 
--         '0.0', 
--         @penalty_arrears, 
--         @penalty_due_today, 
--         @total_penalty_due, 
--         '0.0', 
--         @instalment_arrears, 
--         @instalment_due_today, 
--         @total_instalments_due, 
--         '0.0', 
--         @instalments_arrears, 
--         lastTransactedDate, 
--         DATEDIFF(CURDATE(), lastTransactedDate), 
--         @number_of_days_in_arrears, 
--         '0',
--         'PAYMENT', 
--         'NCO',  
--         'NCO'
--       );

--       SET 
--         @principal_arrears = NULL, 
--         @interest_arrears = NULL, 
--         @accum_interest_arrears = NULL, 
--         @penalty_arrears = NULL, 
--         @instalment_arrears = NULL, 
--         @instalments_arrears = NULL, 
--         @principal_due_today = NULL, 
--         @interest_due_today = NULL, 
--         @accum_interest_due_today = NULL, 
--         @penalty_due_today = NULL, 
--         @instalment_due_today = NULL, 
--         @total_principal_due = NULL, 
--         @total_interest_due = NULL, 
--         @total_accum_interest_due = NULL, 
--         @total_penalty_due = NULL, 
--         @total_instalments_due = NULL, 
--         guarantor_name = NULL, 
--         guarantor_contact1 = NULL, 
--         phone1 = NULL, 
--         the_loan_id = NULL, 
--         the_loan_status = NULL,
--         lastTransactedDate = NULL,
--         trnDateNow = NULL, 
--         @princimpalDisbursed = NULL, 
--         @totalExpectedInterest = NULL, 
--         @totalAmountPaid = NULL, 
--         @totalAmountRemaining = NULL,
--         arrearsDate = NULL,
--         @loanOfficerId = NULL,
--         @number_of_days_in_arrears = NULL;

--       SET @N = @N + 1;
--       SET l_done = 0;

--     END LOOP LedgerIds_loop;

--     CLOSE select_trn_ids;

--   END prepare_daily_report;

-- END //

-- DELIMITER ;

-- CALL prepareDailyReport();


DROP PROCEDURE IF EXISTS prepareDailyReport;

DELIMITER //

CREATE PROCEDURE prepareDailyReport()
READS SQL DATA
BEGIN
  DECLARE l_done INT DEFAULT 0;

  DECLARE ledger_id INT;
  DECLARE loanOfficerId INT;

  DECLARE borrower_name VARCHAR(200);
  DECLARE the_account_number VARCHAR(200);
  DECLARE phone1 VARCHAR(200);

  DECLARE trnDateNow DATE;
  DECLARE lastTransactedDate DATE;
  DECLARE arrearsDate DATE;

  DECLARE guarantor_name VARCHAR(100);
  DECLARE guarantor_contact1 VARCHAR(100);

  DECLARE the_loan_id VARCHAR(100);
  DECLARE the_loan_status VARCHAR(100);

  DECLARE v_last_paid_date DATE;
  DECLARE v_last_paid_trn_id INT;
  DECLARE v_first_due_trn_id INT;

  DECLARE v_princimpalDisbursed DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_totalExpectedInterest DECIMAL(20,6) DEFAULT 0.000000;

  DECLARE v_totalAmountPaid DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_totalAmountRemaining DECIMAL(20,6) DEFAULT 0.000000;

  DECLARE v_principal_arrears DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_interest_arrears DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_accum_interest_arrears DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_penalty_arrears DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_instalment_arrears DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_instalments_arrears INT DEFAULT 0;

  DECLARE v_principal_due_today DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_interest_due_today DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_accum_interest_due_today DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_penalty_due_today DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_instalment_due_today DECIMAL(20,6) DEFAULT 0.000000;

  DECLARE v_total_principal_due DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_total_interest_due DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_total_accum_interest_due DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_total_penalty_due DECIMAL(20,6) DEFAULT 0.000000;
  DECLARE v_total_instalments_due DECIMAL(20,6) DEFAULT 0.000000;

  DECLARE v_number_of_days_in_arrears INT;

  DECLARE select_trn_ids CURSOR FOR
    SELECT trn_id
    FROM new_loan_appstore
    WHERE NOT (loan_cycle_status = 'RenewedClosed'
           OR loan_cycle_status = 'WrittenOff'
           OR loan_cycle_status = 'Completed');

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

  DELETE FROM DailyCollection
  WHERE CollectionDate <= DATE_SUB(CURDATE(), INTERVAL 8 MONTH);

  OPEN select_trn_ids;

  LedgerIds_loop: LOOP
    FETCH select_trn_ids INTO ledger_id;

    IF l_done = 1 THEN
      LEAVE LedgerIds_loop;
    END IF;

    IF EXISTS (
      SELECT 1
      FROM DailyCollection
      WHERE CollectionDate = CURDATE()
        AND trnId = ledger_id
      LIMIT 1
    ) THEN
      ITERATE LedgerIds_loop;
    END IF;

    SELECT
      applicant_account_name,
      applicant_account_number,
      loan_id,
      loan_cycle_status,
      trn_date,
      gruop_id
    INTO
      borrower_name,
      the_account_number,
      the_loan_id,
      the_loan_status,
      trnDateNow,
      loanOfficerId
    FROM new_loan_appstore
    WHERE trn_id = ledger_id
    LIMIT 1;

    SELECT COALESCE((
      SELECT mobile1
      FROM pmms.master
      WHERE account_number = the_account_number
      ORDER BY TrnId ASC
      LIMIT 1
    ), '-') INTO phone1;

    SELECT
      COALESCE((
        SELECT gaurantorsName
        FROM gaurantors
        WHERE loanTrnId = ledger_id
        ORDER BY id ASC
        LIMIT 1
      ), '-'),
      COALESCE((
        SELECT gaurantorsContact1
        FROM gaurantors
        WHERE loanTrnId = ledger_id
        ORDER BY id ASC
        LIMIT 1
      ), '-')
    INTO
      guarantor_name,
      guarantor_contact1;

    SELECT
      COALESCE(SUM(princimpal_amount), 0.0),
      COALESCE(SUM(interest_amount), 0.0),

      COALESCE(SUM(instalment_paid), 0.0),
      COALESCE(SUM(InstalmentRemaining), 0.0),

      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN PrincipalRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN InterestRemaing
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN AccumulatedInterestRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN LoanPenaltyRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN InstalmentRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date < CURDATE() AND instalment_status <> 'P' THEN 1
        ELSE 0 END), 0),

      COALESCE(SUM(CASE
        WHEN instalment_due_date = CURDATE() AND instalment_status <> 'P' THEN PrincipalRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date = CURDATE() AND instalment_status <> 'P' THEN InterestRemaing
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date = CURDATE() AND instalment_status <> 'P' THEN AccumulatedInterestRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date = CURDATE() AND instalment_status <> 'P' THEN LoanPenaltyRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date = CURDATE() AND instalment_status <> 'P' THEN InstalmentRemaining
        ELSE 0 END), 0.0),

      COALESCE(SUM(CASE
        WHEN instalment_due_date <= CURDATE() AND instalment_status <> 'P' THEN PrincipalRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date <= CURDATE() AND instalment_status <> 'P' THEN InterestRemaing
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date <= CURDATE() AND instalment_status <> 'P' THEN AccumulatedInterestRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date <= CURDATE() AND instalment_status <> 'P' THEN LoanPenaltyRemaining
        ELSE 0 END), 0.0),
      COALESCE(SUM(CASE
        WHEN instalment_due_date <= CURDATE() AND instalment_status <> 'P' THEN InstalmentRemaining
        ELSE 0 END), 0.0),

      MAX(CASE WHEN instalment_status IN ('P','PP') THEN trn_id ELSE NULL END),
      MIN(CASE WHEN instalment_due_date <= CURDATE() THEN trn_id ELSE NULL END)
    INTO
      v_princimpalDisbursed,
      v_totalExpectedInterest,

      v_totalAmountPaid,
      v_totalAmountRemaining,

      v_principal_arrears,
      v_interest_arrears,
      v_accum_interest_arrears,
      v_penalty_arrears,
      v_instalment_arrears,
      v_instalments_arrears,

      v_principal_due_today,
      v_interest_due_today,
      v_accum_interest_due_today,
      v_penalty_due_today,
      v_instalment_due_today,

      v_total_principal_due,
      v_total_interest_due,
      v_total_accum_interest_due,
      v_total_penalty_due,
      v_total_instalments_due,

      v_last_paid_trn_id,
      v_first_due_trn_id
    FROM new_loan_appstoreamort
    WHERE master1_id = ledger_id;

    SELECT COALESCE((
      SELECT COALESCE(instalment_paid_date, trnDateNow)
      FROM new_loan_appstoreamort
      WHERE master1_id = ledger_id
        AND instalment_status IN ('P','PP')
      ORDER BY trn_id DESC
      LIMIT 1
    ), trnDateNow) INTO lastTransactedDate;

    SELECT (
      SELECT instalment_due_date
      FROM new_loan_appstoreamort
      WHERE master1_id = ledger_id
        AND instalment_due_date <= CURDATE()
      ORDER BY trn_id ASC
      LIMIT 1
    ) INTO arrearsDate;

    SET v_number_of_days_in_arrears = DATEDIFF(CURDATE(), arrearsDate);

    INSERT INTO DailyCollection (
      CollectionId,
      CollectionDate,
      accountName,
      accountNumber,
      PhoneNumber,
      loanID,
      trnId,
      loansOfficerId,
      loanStatus,
      disbursementDate,
      gourantorName,
      gourantorPhone,
      princimpalDisbursed,
      totalExpectedInterest,
      totalAmountPaid,
      totalAmountRemaining,
      ExpectedPrincipalArrears,
      ExpectedPrincipalToday,
      ExpectedTotalPrincimpalToday,
      PrincimpalCollectedToday,
      ExpectedInterestArrears,
      ExpectedInterestToday,
      ExpectedTotalInterestToday,
      InterestCollectedToday,
      ExpectedAccumInterestArrears,
      ExpectedAccumInterestToday,
      ExpectedTotalIAccumnterestToday,
      AccumInterestCollectedToday,
      ExpectedPenaltyArrears,
      ExpectedPenaltyToday,
      ExpectedTotalIPenaltyToday,
      PenaltyCollectedToday,
      ExpectedAmountInArrears,
      ExpectedAmountToday,
      ExpectedTotalAmountToday,
      AmountCollectedToday,
      NumberOfInstalmentsInArrears,
      LastPayementDate,
      NumberOfDaysSinceLastPayment,
      NumberOfDaysInArrears,
      VarianceCollection,
      typeOfLoanTransaction,
      typeOfDisbursement,
      OtherFive
    ) VALUES (
      NULL,
      CURDATE(),
      borrower_name,
      the_account_number,
      phone1,
      the_loan_id,
      ledger_id,
      loanOfficerId,
      the_loan_status,
      trnDateNow,
      guarantor_name,
      guarantor_contact1,
      v_princimpalDisbursed,
      v_totalExpectedInterest,
      v_totalAmountPaid,
      v_totalAmountRemaining,
      v_principal_arrears,
      v_principal_due_today,
      v_total_principal_due,
      '0.0',
      v_interest_arrears,
      v_interest_due_today,
      v_total_interest_due,
      '0.0',
      v_accum_interest_arrears,
      v_accum_interest_due_today,
      v_total_accum_interest_due,
      '0.0',
      v_penalty_arrears,
      v_penalty_due_today,
      v_total_penalty_due,
      '0.0',
      v_instalment_arrears,
      v_instalment_due_today,
      v_total_instalments_due,
      '0.0',
      v_instalments_arrears,
      lastTransactedDate,
      DATEDIFF(CURDATE(), lastTransactedDate),
      v_number_of_days_in_arrears,
      '0',
      'PAYMENT',
      'NCO',
      'NCO'
    );

    SET l_done = 0;
  END LOOP;

  CLOSE select_trn_ids;
END //

DELIMITER ;

CALL prepareDailyReport();

ALTER TABLE DailyCollection
  ADD INDEX idx_dailycollection_date (CollectionDate),
  ADD INDEX idx_dailycollection_date_trnid (CollectionDate, trnId);

ALTER TABLE new_loan_appstore
  ADD INDEX idx_loan_appstore_status (loan_cycle_status),
  ADD INDEX idx_loan_appstore_trnid (trn_id);

ALTER TABLE new_loan_appstoreamort
  ADD INDEX idx_amort_master (master1_id),
  ADD INDEX idx_amort_master_trnid (master1_id, trn_id),
  ADD INDEX idx_amort_master_due_status (master1_id, instalment_due_date, instalment_status);

ALTER TABLE pmms.master
  ADD INDEX idx_master_acc_trnid (account_number, TrnId);

ALTER TABLE gaurantors
  ADD INDEX idx_gaurantors_loantrnid_id (loanTrnId, id);



 DROP PROCEDURE IF EXISTS CollectionOnDisbursement;

DELIMITER //

CREATE PROCEDURE CollectionOnDisbursement(IN loanIdCC VARCHAR(100))
READS SQL DATA
BEGIN
  
  DECLARE l_done INTEGER DEFAULT 0;
  DECLARE ledger_id, loanOfficerId INT;
  DECLARE borrower_name, disburseType VARCHAR(200);
  DECLARE the_account_number VARCHAR(200);
  DECLARE phone1 VARCHAR(200);
  DECLARE trnDateNow DATE;
  DECLARE lastTransactedDate DATE;
  DECLARE arrearsDate DATE;
  DECLARE guarantor_name VARCHAR(100);
  DECLARE guarantor_contact1 VARCHAR(100);
  DECLARE the_loan_id VARCHAR(100);
  DECLARE the_loan_status VARCHAR(100);

  DECLARE principal_arrears, principal_due_today, total_principal_due DECIMAL(10,2) DEFAULT 0.0;
  DECLARE interest_arrears, interest_due_today, total_interest_due DECIMAL(10,2) DEFAULT 0.0;
  DECLARE accum_interest_arrears, accum_interest_due_today, total_accum_interest_due DECIMAL(10,2) DEFAULT 0.0;
  DECLARE penalty_arrears, penalty_due_today, total_penalty_due DECIMAL(10,2) DEFAULT 0.0;
  DECLARE instalment_arrears, instalment_due_today, total_instalments_due DECIMAL(10,2) DEFAULT 0.0;
  DECLARE instalments_arrears INT DEFAULT 0;
  DECLARE princimpalDisbursed, totalExpectedInterest, totalAmountPaid, totalAmountRemaining DECIMAL(10,2) DEFAULT 0.0;
  DECLARE number_of_days_in_arrears INT;

  DECLARE accountDate DATE;

  SELECT trn_id INTO ledger_id FROM new_loan_appstore WHERE loan_id = loanIdCC ORDER BY trn_id DESC LIMIT 1;

  -- Select main details from new_loan_appstore
  SET @query = CONCAT(
    'SELECT applicant_account_name, applicant_account_number, loan_id, loan_cycle_status, trn_date, gruop_id ',
    'INTO @borrower_name, @the_account_number, @the_loan_id, @the_loan_status, @trnDateNow, @loanOfficerId ',
    'FROM new_loan_appstore WHERE trn_id = ', ledger_id
  );

  PREPARE stmt FROM @query;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  SET borrower_name = @borrower_name;
  SET the_account_number = @the_account_number;
  SET the_loan_id = @the_loan_id;
  SET the_loan_status = @the_loan_status;
  SET trnDateNow = @trnDateNow;
  SET loanOfficerId = @loanOfficerId;

  SELECT COALESCE(mobile1, '-') INTO phone1 
  FROM pmms.master 
  WHERE account_number = the_account_number 
  ORDER BY TrnId ASC 
  LIMIT 1;

  SELECT COALESCE(gaurantorsName, '-'), COALESCE(gaurantorsContact1, '-') 
  INTO guarantor_name, guarantor_contact1 
  FROM gaurantors 
  WHERE loanTrnId = ledger_id 
  ORDER BY id ASC 
  LIMIT 1;

  SELECT 
    COALESCE(SUM(princimpal_amount), '0.0'), 
    COALESCE(SUM(interest_amount), '0.0')
  INTO 
    princimpalDisbursed, 
    totalExpectedInterest
  FROM new_loan_appstoreamort 
  WHERE master1_id = ledger_id;

  SELECT 
    COALESCE(SUM(instalment_paid), '0.0'), 
    COALESCE(SUM(InstalmentRemaining), '0.0')
  INTO 
    totalAmountPaid, 
    totalAmountRemaining 
  FROM new_loan_appstoreamort 
  WHERE master1_id = ledger_id;

  SELECT 
    COALESCE(SUM(PrincipalRemaining), '0.0'), 
    COALESCE(SUM(InterestRemaing), '0.0'), 
    COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
    COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
    COALESCE(SUM(InstalmentRemaining), '0.0'), 
    COALESCE(COUNT(trn_id), '0.0') 
  INTO 
    principal_arrears, 
    interest_arrears, 
    accum_interest_arrears, 
    penalty_arrears, 
    instalment_arrears, 
    instalments_arrears 
  FROM new_loan_appstoreamort 
  WHERE instalment_due_date < CURDATE() 
    AND instalment_status <> 'P' 
    AND master1_id = ledger_id;

  SELECT 
    COALESCE(SUM(PrincipalRemaining), '0.0'), 
    COALESCE(SUM(InterestRemaing), '0.0'), 
    COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
    COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
    COALESCE(SUM(InstalmentRemaining), '0.0') 
  INTO 
    principal_due_today, 
    interest_due_today, 
    accum_interest_due_today, 
    penalty_due_today, 
    instalment_due_today 
  FROM new_loan_appstoreamort 
  WHERE instalment_due_date = CURDATE() 
    AND instalment_status <> 'P' 
    AND master1_id = ledger_id;

  SELECT 
    COALESCE(SUM(PrincipalRemaining), '0.0'), 
    COALESCE(SUM(InterestRemaing), '0.0'), 
    COALESCE(SUM(AccumulatedInterestRemaining), '0.0'), 
    COALESCE(SUM(LoanPenaltyRemaining), '0.0'), 
    COALESCE(SUM(InstalmentRemaining), '0.0') 
  INTO 
    total_principal_due, 
    total_interest_due, 
    total_accum_interest_due, 
    total_penalty_due, 
    total_instalments_due 
  FROM new_loan_appstoreamort 
  WHERE instalment_due_date <= CURDATE() 
    AND instalment_status <> 'P' 
    AND master1_id = ledger_id;

  SET @sql_query = CONCAT(
    'SELECT COALESCE(instalment_paid_date, ''', trnDateNow, ''') INTO @lastTransactedDate ',
    'FROM new_loan_appstoreamort ',
    'WHERE master1_id = ', ledger_id, ' ',
    'AND (instalment_status = ''P'' OR instalment_status = ''PP'') ',
    'ORDER BY trn_id DESC LIMIT 1'
  );

  PREPARE stmt FROM @sql_query;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  SET lastTransactedDate = COALESCE(@lastTransactedDate, DATE(NOW())); -- Default date to avoid null

  SELECT instalment_due_date INTO @arrearsDate
  FROM new_loan_appstoreamort
  WHERE instalment_due_date <= CURDATE()
    AND master1_id = ledger_id
  ORDER BY trn_id ASC
  LIMIT 1;

  SET number_of_days_in_arrears = DATEDIFF(CURDATE(), @arrearsDate);

  SET @sql_query = CONCAT(
    'SELECT COALESCE(creation_date, ''', trnDateNow, ''') INTO @accountDate ',
    'FROM pmms.account_created_store ',
    'WHERE account_number = ''', the_account_number, ''' ',
    'ORDER BY trn_id DESC LIMIT 1'
  );
SELECT @sql_query;
  PREPARE stmt FROM @sql_query;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  SET accountDate = COALESCE(@accountDate, CURDATE()); -- Default date to avoid null

  IF accountDate = DATE(NOW()) THEN
    SET disburseType = 'NEW';
  ELSE
    SET disburseType = 'OLD';
  END IF;

  INSERT INTO DailyCollection (
    CollectionId,
    CollectionDate,
    accountName,
    accountNumber,
    PhoneNumber,
    loanID,
    trnId,
    loansOfficerId,
    loanStatus,
    disbursementDate,
    gourantorName,
    gourantorPhone,
    princimpalDisbursed,
    totalExpectedInterest,
    totalAmountPaid,
    totalAmountRemaining,
    ExpectedPrincipalArrears,
    ExpectedPrincipalToday,
    ExpectedTotalPrincimpalToday,
    PrincimpalCollectedToday,
    ExpectedInterestArrears,
    ExpectedInterestToday,
    ExpectedTotalInterestToday,
    InterestCollectedToday,
    ExpectedAccumInterestArrears,
    ExpectedAccumInterestToday,
    ExpectedTotalIAccumnterestToday,
    AccumInterestCollectedToday,
    ExpectedPenaltyArrears,
    ExpectedPenaltyToday,
    ExpectedTotalIPenaltyToday,
    PenaltyCollectedToday,
    ExpectedAmountInArrears,
    ExpectedAmountToday,
    ExpectedTotalAmountToday,
    AmountCollectedToday,
    NumberOfInstalmentsInArrears,
    LastPayementDate,
    NumberOfDaysSinceLastPayment,
    NumberOfDaysInArrears,
    VarianceCollection,
    typeOfLoanTransaction,
    typeOfDisbursement,
    OtherFive
  ) VALUES (
    NULL, 
    CURDATE(), 
    borrower_name, 
    the_account_number, 
    phone1, 
    the_loan_id, 
    ledger_id,
    loanOfficerId, 
    the_loan_status,
    trnDateNow,   
    guarantor_name, 
    guarantor_contact1, 
    princimpalDisbursed, 
    totalExpectedInterest, 
    totalAmountPaid, 
    totalAmountRemaining, 
    principal_arrears, 
    principal_due_today, 
    total_principal_due, 
    '0.0', 
    interest_arrears, 
    interest_due_today, 
    total_interest_due, 
    '0.0', 
    accum_interest_arrears, 
    accum_interest_due_today, 
    total_accum_interest_due, 
    '0.0', 
    penalty_arrears, 
    penalty_due_today, 
    total_penalty_due, 
    '0.0', 
    instalment_arrears, 
    instalment_due_today, 
    total_instalments_due, 
    '0.0', 
    instalments_arrears, 
    lastTransactedDate, 
    DATEDIFF(CURDATE(), lastTransactedDate), 
    number_of_days_in_arrears, 
    '0',
    'DISBURSEMENT', 
    disburseType, 
    'NCO'
  );

  SET 
    principal_arrears = NULL, 
    interest_arrears = NULL, 
    accum_interest_arrears = NULL, 
    penalty_arrears = NULL, 
    instalment_arrears = NULL, 
    instalments_arrears = NULL, 
    principal_due_today = NULL, 
    interest_due_today = NULL, 
    accum_interest_due_today = NULL, 
    penalty_due_today = NULL, 
    instalment_due_today = NULL, 
    total_principal_due = NULL, 
    total_interest_due = NULL, 
    total_accum_interest_due = NULL, 
    total_penalty_due = NULL, 
    total_instalments_due = NULL, 
    guarantor_name = NULL, 
    guarantor_contact1 = NULL, 
    phone1 = NULL, 
    the_loan_id = NULL, 
    the_loan_status = NULL,
    lastTransactedDate = NULL,
    trnDateNow = NULL, 
    princimpalDisbursed = NULL, 
    totalExpectedInterest = NULL, 
    totalAmountPaid = NULL, 
    totalAmountRemaining = NULL,
    arrearsDate = NULL,
    loanOfficerId = NULL,
    number_of_days_in_arrears = NULL;

END //

DELIMITER ;

-- CALL CollectionOnDisbursement('newloan05502044310');

-- import java.text.NumberFormat;
-- import java.time.LocalDateTime;
-- import java.time.format.DateTimeFormatter;
-- import java.util.Locale;

-- public class LoanPaymentMessage {

--     public static void main(String[] args) {
--         String nameLP = "John Doe";
--         String entityLP = "XYZ Bank";
--         String amountInvolved = "50000";
--         String accountNumber = "ACC123456";
--         Loan loan = new Loan();

--         // Format the amount involved
--         double amount = Double.parseDouble(amountInvolved.replace(",", ""));
--         String formattedAmountInvolved = NumberFormat.getNumberInstance(Locale.US).format(amount).replace(",", "%2C");

--         // Fetch the remaining balance
--         double remainingBalance = Double.parseDouble(loan.remainingTotalLoanBalance(accountNumber).replace(",", ""));
--         String formattedRemainingBalance = NumberFormat.getNumberInstance(Locale.US).format(remainingBalance).replace(",", "%2C");

--         // Get current date and time
--         LocalDateTime now = LocalDateTime.now();
--         DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
--         String formattedDate = now.format(dateFormatter).replace(" ", "%20").replace(",", "%2C").replace(":", "%3A").replace(".", "%2E");

--         // Construct the message
--         String MessageLP = "Dear" + "%20" + nameLP + "%2C" + "%0A" + "%20" + entityLP + "%20" + "has" + "%20" + "received" + "%20" + "your" + "%20" + "Loan" + "%20" + "payment" + "%20" + "worth" + "%20" + "Ugx" + "%20" + formattedAmountInvolved + "." + "%20" + "Your" + "%20" + "loan" + "%20" + "balance" + "%20" + "is" + "%20" + "%3A" + "%20" + formattedRemainingBalance + "." + "%20" + "On" + "%20" + formattedDate + "%20" + "from" + "%20" + entityLP + "." + "%20" + "Thank" + "%20" + "you" + "%2E";

--         System.out.println(MessageLP);
--     }
-- }

-- // Mock Loan class for the purpose of this example
-- class Loan {
--     public String remainingTotalLoanBalance(String accountNumber) {
--         // Mocked method to return a remaining balance
--         return "12345.67"; // Replace with actual logic to get the remaining balance
--     }
-- }


DROP PROCEDURE IF EXISTS updatdateNormalDailyPrincipal;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPrincipal(IN loanIdCC VARCHAR(100), IN principal VARCHAR(100))
READS SQL DATA
BEGIN
    -- Declare variables
    DECLARE totalPaid DECIMAL(10,2);
    DECLARE totalRemaining DECIMAL(10,2);
    DECLARE principalCollected DECIMAL(10,2);
    DECLARE instalmentAmountCollected DECIMAL(10,2);
    DECLARE Id,number_of_days_in_arrears1 INT;
    DECLARE LastPayementDate DATE;
    
    -- Select relevant values from DailyCollection
    SELECT 
        COALESCE(totalAmountPaid, 0.0), 
        COALESCE(totalAmountRemaining, 0.0), 
        COALESCE(PrincimpalCollectedToday, 0.0), 
        COALESCE(AmountCollectedToday, 0.0), 
        CollectionId
    INTO 
        totalPaid,
        totalRemaining, 
        principalCollected, 
        instalmentAmountCollected, 
        Id
    FROM 
        DailyCollection 
    WHERE 
        CollectionDate = CURDATE() 
        AND loanID = loanIdCC 
    ORDER BY 
        CollectionId DESC 
    LIMIT 1;

    -- Update principal and instalment amounts
    SET principalCollected = principalCollected + principal,
        totalPaid = totalPaid + principal, 
        totalRemaining = totalRemaining - principal, 
        instalmentAmountCollected = instalmentAmountCollected + principal;




      -- SELECT instalment_due_date INTO @arrearsDate
      -- FROM new_loan_appstoreamort
      -- WHERE instalment_due_date <= CURDATE()
      --   AND master1_id = loanIdCC  AND NOT instalment_status='P'
      -- ORDER BY trn_id ASC
      -- LIMIT 1;

      -- SET number_of_days_in_arrears1 = DATEDIFF(CURDATE(), @arrearsDate);
-- Construct the SQL statement with concatenation
    SET @sql_text = CONCAT('SELECT instalment_due_date INTO @arrearsDate ',
                           'FROM new_loan_appstoreamort ',
                           'WHERE instalment_due_date <= CURDATE() ',
                           'AND master1_id = "', loanIdCC, '" ',
                           'AND NOT instalment_status="P" ',
                           'ORDER BY trn_id ASC ',
                           'LIMIT 1');

    -- Prepare and execute the statement
    PREPARE stmt FROM @sql_text;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- Use the result for further calculations
    SET number_of_days_in_arrears1 = DATEDIFF(CURDATE(), @arrearsDate);


    SELECT number_of_days_in_arrears1;

    -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
        totalAmountPaid = totalPaid,
        totalAmountRemaining = totalRemaining,
        PrincimpalCollectedToday = principalCollected, 
        AmountCollectedToday = instalmentAmountCollected,
          NumberOfDaysSinceLastPayment=0,

        LastPayementDate = CURDATE()
    WHERE 
        CollectionId = Id;



         -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
    NumberOfDaysInArrears=number_of_days_in_arrears1
    WHERE 
        CollectionId = Id;

    -- Reset variables
    SET principalCollected = NULL;
    SET instalmentAmountCollected = NULL;
    SET Id = NULL;
    SET LastPayementDate = NULL;
    SET number_of_days_in_arrears1=NULL;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updatdateNormalDailyInterest;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyInterest(IN loanIdCC VARCHAR(100), IN interest VARCHAR(100))
READS SQL DATA
BEGIN
    -- Declare variables
    DECLARE interestCollected DECIMAL(10,2);
    DECLARE instalmentAmountCollected DECIMAL(10,2);
    DECLARE totalPaid DECIMAL(10,2);
    DECLARE totalRemaining DECIMAL(10,2);
    DECLARE Id,number_of_days_in_arrears1 INT;

    -- Select relevant values from DailyCollection
    SELECT 
        COALESCE(InterestCollectedToday, 0.0), 
        COALESCE(AmountCollectedToday, 0.0), 
        COALESCE(totalAmountPaid, 0.0), 
        COALESCE(totalAmountRemaining, 0.0), 
        CollectionId
    INTO 
        interestCollected, 
        instalmentAmountCollected, 
        totalPaid, 
        totalRemaining, 
        Id
    FROM 
        DailyCollection 
    WHERE 
        CollectionDate = CURDATE() 
        AND loanID = loanIdCC 
    ORDER BY 
        CollectionId DESC 
    LIMIT 1;

    -- Update interest and instalment amounts
    SET interestCollected = interestCollected + interest,
        instalmentAmountCollected = instalmentAmountCollected + interest,
        totalPaid = totalPaid + interest,
        totalRemaining = totalRemaining - interest;


      --    SELECT instalment_due_date INTO @arrearsDate
      -- FROM new_loan_appstoreamort
      -- WHERE instalment_due_date <= CURDATE()
      --   AND master1_id = loanIdCC  AND NOT instalment_status='P'
      -- ORDER BY trn_id ASC
      -- LIMIT 1;

      -- SET number_of_days_in_arrears1 = DATEDIFF(CURDATE(), @arrearsDate);

-- Construct the SQL statement with concatenation
    SET @sql_text = CONCAT('SELECT instalment_due_date INTO @arrearsDate ',
                           'FROM new_loan_appstoreamort ',
                           'WHERE instalment_due_date <= CURDATE() ',
                           'AND master1_id = "', loanIdCC, '" ',
                           'AND NOT instalment_status="P" ',
                           'ORDER BY trn_id ASC ',
                           'LIMIT 1');

    -- Prepare and execute the statement
    PREPARE stmt FROM @sql_text;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- Use the result for further calculations
    SET number_of_days_in_arrears1 = DATEDIFF(CURDATE(), @arrearsDate);

    SELECT number_of_days_in_arrears1;

    -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
        InterestCollectedToday = interestCollected, 
        AmountCollectedToday = instalmentAmountCollected,
        totalAmountPaid = totalPaid,
        totalAmountRemaining = totalRemaining,
             NumberOfDaysSinceLastPayment=0,
        LastPayementDate = CURDATE()
    WHERE 
        CollectionId = Id;


        
    -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
       
    NumberOfDaysInArrears=number_of_days_in_arrears1
    WHERE 
        CollectionId = Id;

    -- Reset variables
    SET interestCollected = NULL;
    SET instalmentAmountCollected = NULL;
    SET totalPaid = NULL;
    SET totalRemaining = NULL;
    SET Id = NULL;
    SET number_of_days_in_arrears1= NULL;

END //

DELIMITER ;



DROP PROCEDURE IF EXISTS updatdateNormalDailyAccumInterest;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyAccumInterest(IN loanIdCC VARCHAR(100), IN interest VARCHAR(100))
READS SQL DATA
BEGIN
    -- Declare variables
    DECLARE interestCollected DECIMAL(10,2);
    DECLARE instalmentAmountCollected DECIMAL(10,2);
    DECLARE totalPaid DECIMAL(10,2);
    DECLARE totalRemaining DECIMAL(10,2);
    DECLARE Id INT;

    -- Select relevant values from DailyCollection
    SELECT 
        COALESCE(AccumInterestCollectedToday, 0.0), 
        COALESCE(AmountCollectedToday, 0.0), 
        COALESCE(totalAmountPaid, 0.0), 
        COALESCE(totalAmountRemaining, 0.0), 
        CollectionId
    INTO 
        interestCollected, 
        instalmentAmountCollected, 
        totalPaid, 
        totalRemaining, 
        Id
    FROM 
        DailyCollection 
    WHERE 
        CollectionDate = CURDATE() 
        AND loanID = loanIdCC 
    ORDER BY 
        CollectionId DESC 
    LIMIT 1;

    -- Update interest and instalment amounts
    SET interestCollected = interestCollected + interest,
        instalmentAmountCollected = instalmentAmountCollected + interest,
        totalPaid = totalPaid + interest,
        totalRemaining = totalRemaining - interest;

         SELECT instalment_due_date INTO @arrearsDate
      FROM new_loan_appstoreamort
      WHERE instalment_due_date <= CURDATE()
        AND master1_id = loanIdCC  AND NOT instalment_status='P'
      ORDER BY trn_id ASC
      LIMIT 1;

      SET @number_of_days_in_arrears = DATEDIFF(CURDATE(), @arrearsDate);


    -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
        AccumInterestCollectedToday = interestCollected, 
        AmountCollectedToday = instalmentAmountCollected,
        totalAmountPaid = totalPaid,
        totalAmountRemaining = totalRemaining,
     NumberOfDaysSinceLastPayment=0,
    NumberOfDaysInArrears=@number_of_days_in_arrears,
        LastPayementDate = CURDATE()
    WHERE 
        CollectionId = Id;

    -- Reset variables
    SET interestCollected = NULL;
    SET instalmentAmountCollected = NULL;
    SET totalPaid = NULL;
    SET totalRemaining = NULL;
    SET Id = NULL;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updatdateNormalDailyPenalty;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPenalty(IN loanIdCC VARCHAR(100), IN interest VARCHAR(100))
READS SQL DATA
BEGIN
    -- Declare variables
    DECLARE interestCollected DECIMAL(10,2);
    DECLARE instalmentAmountCollected DECIMAL(10,2);
    DECLARE totalPaid DECIMAL(10,2);
    DECLARE totalRemaining DECIMAL(10,2);
    DECLARE Id INT;

    -- Select relevant values from DailyCollection
    SELECT 
        COALESCE(PenaltyCollectedToday, 0.0), 
        COALESCE(AmountCollectedToday, 0.0), 
        COALESCE(totalAmountPaid, 0.0), 
        COALESCE(totalAmountRemaining, 0.0), 
        CollectionId
    INTO 
        interestCollected, 
        instalmentAmountCollected, 
        totalPaid, 
        totalRemaining, 
        Id
    FROM 
        DailyCollection 
    WHERE 
        CollectionDate = CURDATE() 
        AND loanID = loanIdCC 
    ORDER BY 
        CollectionId DESC 
    LIMIT 1;

    -- Update interest and instalment amounts
    SET interestCollected = interestCollected + interest,
        instalmentAmountCollected = instalmentAmountCollected + interest,
        totalPaid = totalPaid + interest,
        totalRemaining = totalRemaining - interest;

           SELECT instalment_due_date INTO @arrearsDate
      FROM new_loan_appstoreamort
      WHERE instalment_due_date <= CURDATE()
        AND master1_id = loanIdCC  AND NOT instalment_status='P'
      ORDER BY trn_id ASC
      LIMIT 1;

      SET @number_of_days_in_arrears = DATEDIFF(CURDATE(), @arrearsDate);




    -- Update DailyCollection with the new values
    UPDATE DailyCollection 
    SET 
        PenaltyCollectedToday = interestCollected, 
        AmountCollectedToday = instalmentAmountCollected,
        totalAmountPaid = totalPaid,
        totalAmountRemaining = totalRemaining,
             NumberOfDaysSinceLastPayment=0,
    NumberOfDaysInArrears=@number_of_days_in_arrears,
        LastPayementDate = CURDATE()
    WHERE 
        CollectionId = Id;

    -- Reset variables
    SET interestCollected = NULL;
    SET instalmentAmountCollected = NULL;
    SET totalPaid = NULL;
    SET totalRemaining = NULL;
    SET Id = NULL;

END //

DELIMITER ;






DROP PROCEDURE IF EXISTS updatdateNormalDailyInterestWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyInterestWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT InterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;

IF @interestCollected>0 THEN 
UPDATE DailyCollection SET InterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;
END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updatdateNormalDailyPenaltyWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPenaltyWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT PenaltyCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;

IF @interestCollected>0 THEN 

UPDATE DailyCollection SET PenaltyCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updatdateNormalDailyAccumInterestWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyAccumInterestWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT AccumInterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;


IF @interestCollected>0 THEN 
UPDATE DailyCollection SET AccumInterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;
END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyPrincipalWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPrincipalWriteOff(IN loanIdCC VARCHAR(100) ,IN  principal VARCHAR(100)) READS SQL DATA BEGIN

SELECT PrincimpalCollectedToday,AmountCollectedToday,CollectionId INTO @principalCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @principalCollected IS NULL THEN
SET @principalCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;

SET @principalCollected=@principalCollected-principal;

SET @instalmentAmountCollected=@instalmentAmountCollected-principal;

IF @principalCollected>0 THEN 

UPDATE DailyCollection SET PrincimpalCollectedToday=@principalCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

END IF;

SET @principalCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;





DROP PROCEDURE IF EXISTS dailyCollectionAllUnpaidCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionAllUnpaidCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionAllUnpaidCustomers('2025-05-19')\G




DROP PROCEDURE IF EXISTS dailyCollectionAllPaidCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionAllPaidCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday > 0
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, (@princimpalDisbursed+@totalExpectedInterest), @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionAllPaidCustomers('2024-05-22')\G





DROP PROCEDURE IF EXISTS dailyCollectionAllUnpaidNonPerformingCustomersCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionAllUnpaidNonPerformingCustomersCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND NumberOfDaysInArrears>90
          AND loanStatus='Disbursed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionAllUnpaidNonPerformingCustomersCustomers('2024-05-22')\G






DROP PROCEDURE IF EXISTS dailyCollectionAllUnpaidPerformingCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionAllUnpaidPerformingCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND NumberOfDaysInArrears<30
          AND loanStatus='Disbursed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionAllUnpaidPerformingCustomers('2024-05-22')\G






DROP PROCEDURE IF EXISTS dailyCollectionUnpaidRenewedPerformingCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionUnpaidRenewedPerformingCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND NumberOfDaysInArrears<30
          AND loanStatus='Renewed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionUnpaidRenewedPerformingCustomers('2024-05-22')\G




DROP PROCEDURE IF EXISTS dailyCollectionAllUnpaidAtRiskCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionAllUnpaidAtRiskCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND (NumberOfDaysInArrears>=30  AND NumberOfDaysInArrears<90)
          AND loanStatus='Disbursed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionAllUnpaidAtRiskCustomers('2024-05-22')\G






DROP PROCEDURE IF EXISTS dailyCollectionOnlyActivePaidCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionOnlyActivePaidCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday > 0
           AND loanStatus='Disbursed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, (@princimpalDisbursed+@totalExpectedInterest), @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionOnlyActivePaidCustomers('2024-05-22')\G


--  SELECT CollectionId FROM dailycollection WHERE CollectionDate = CURDATE() AND AmountCollectedToday > 0 AND loanStatus='Renewed' AND typeOfLoanTransaction = 'PAYMENT';


DROP PROCEDURE IF EXISTS dailyCollectionOnlyRenewedPaidCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionOnlyRenewedPaidCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday > 0
           AND loanStatus='Renewed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, (@princimpalDisbursed+@totalExpectedInterest), @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionOnlyRenewedPaidCustomers('2024-05-23')\G




DROP PROCEDURE IF EXISTS dailyCollectionUnpaidRenewedNonPerformingCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionUnpaidRenewedNonPerformingCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND NumberOfDaysInArrears>90
          AND loanStatus='Renewed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionUnpaidRenewedNonPerformingCustomers('2024-05-23')\G



DROP PROCEDURE IF EXISTS dailyCollectionARenewedUnpaidAtRiskCustomers;

DELIMITER //

CREATE PROCEDURE dailyCollectionARenewedUnpaidAtRiskCustomers(IN theDate DATE)
READS SQL DATA
BEGIN
    DECLARE txnIdS VARCHAR(30);
    DECLARE l_done INTEGER DEFAULT 0;
    DECLARE Ids INTEGER DEFAULT 0;

    DECLARE forSelectingTxnIds CURSOR FOR
        SELECT CollectionId
        FROM dailycollection
        WHERE CollectionDate = theDate
          AND AmountCollectedToday <= 0
          AND (NumberOfDaysInArrears>=30  AND NumberOfDaysInArrears<90)
          AND loanStatus='Renewed'
          AND typeOfLoanTransaction = 'PAYMENT';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Start a transaction
    START TRANSACTION;

    DROP TEMPORARY TABLE IF EXISTS temp_dailycollectionUnpaidCustomers;

    CREATE TEMPORARY TABLE temp_dailycollectionUnpaidCustomers (
        id INTEGER,
        account_Name VARCHAR(100),
        account_Number VARCHAR(100),
        Phone_Number VARCHAR(100),
        loan_ID VARCHAR(100),
        trn_Id INT(11),
        loans_Officer_Id INT(11),
        loan_Status VARCHAR(100),
        disbursement_Date DATE,
        gourantor_Name VARCHAR(100),
        gourantor_Phone VARCHAR(100),
        princimpal_Disbursed VARCHAR(100),
        total_Amount_Paid VARCHAR(100),
        total_Amount_Remaining VARCHAR(100),
        temp_ExpectedCollection DOUBLE,
        temp_ActualCollection DOUBLE,
        temp_BalColl DOUBLE,
        Last_Payement_Date DATE,
        Number_Of_Days_Since_Last_Payment INT(11),
        Number_Of_Days_In_Arrears INT(11),
        Deadline DATE,
        temp_Variance VARCHAR(200)
    );

    OPEN forSelectingTxnIds;

    accounts_loop: LOOP
        FETCH forSelectingTxnIds INTO txnIdS;

        IF l_done = 1 THEN
            LEAVE accounts_loop;
        END IF;

        SELECT IFNULL(accountName, ''), IFNULL(accountNumber, ''), IFNULL(PhoneNumber, ''), IFNULL(loanID, ''), IFNULL(trnId, 0), IFNULL(loansOfficerId, 0), IFNULL(loanStatus, ''), IFNULL(disbursementDate, '0000-00-00'), IFNULL(gourantorName, ''), IFNULL(gourantorPhone, ''), IFNULL(princimpalDisbursed, '0'), IFNULL(totalExpectedInterest, '0'), IFNULL(totalAmountPaid, '0'), IFNULL(LastPayementDate, '0000-00-00'), IFNULL(NumberOfDaysSinceLastPayment, 0), IFNULL(NumberOfDaysInArrears, 0), IFNULL(ExpectedTotalAmountToday, '0'), IFNULL(AmountCollectedToday, '0')
        INTO @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalExpectedInterest, @totalAmountPaid, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, @ExpectedInterestToday, @InterestCollectedToday
        FROM dailycollection
        WHERE CollectionId = txnIdS;

        SET Ids = Ids + 1;
        SET @Varince = CAST(IFNULL(@InterestCollectedToday, '0') AS DECIMAL(10,2)) - CAST(IFNULL(@ExpectedInterestToday, '0') AS DECIMAL(10,2));
        SET @total_Amount_Remaining = (CAST(IFNULL(@princimpalDisbursed, '0') AS DECIMAL(10,2)) + CAST(IFNULL(@totalExpectedInterest, '0') AS DECIMAL(10,2))) - CAST(IFNULL(@totalAmountPaid, '0') AS DECIMAL(10,2));

        IF @Varince >= 0.0 THEN
            SET @Vstatus = '+Ve';
            SET @Varince = 0.0;
        ELSE
            SET @Vstatus = '-Ve';
            SET @Varince = @Varince * -1;
        END IF;

        INSERT INTO temp_dailycollectionUnpaidCustomers (
            id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
        ) VALUES (
            Ids, @accountName, @accountNumber, @PhoneNumber, @loanID, @trnId, @loansOfficerId, @loanStatus, @disbursementDate, @gourantorName, @gourantorPhone, @princimpalDisbursed, @totalAmountPaid, @total_Amount_Remaining, @ExpectedInterestToday, @InterestCollectedToday, @Varince, @LastPayementDate, @NumberOfDaysSinceLastPayment, @NumberOfDaysInArrears, DATE_ADD(@disbursementDate, INTERVAL 30 DAY), @Vstatus
        );

        SET l_done = 0;
    END LOOP accounts_loop;

    CLOSE forSelectingTxnIds;

    -- Commit the transaction
    COMMIT;

    SELECT id, account_Name, account_Number, Phone_Number, loan_ID, trn_Id, loans_Officer_Id, loan_Status, disbursement_Date, gourantor_Name, gourantor_Phone, princimpal_Disbursed, total_Amount_Paid, total_Amount_Remaining, temp_ExpectedCollection, temp_ActualCollection, temp_BalColl, Last_Payement_Date, Number_Of_Days_Since_Last_Payment, Number_Of_Days_In_Arrears, Deadline, temp_Variance
    FROM temp_dailycollectionUnpaidCustomers;
END //

DELIMITER ;

CALL dailyCollectionARenewedUnpaidAtRiskCustomers('2024-05-23')\G



DELIMITER $$

CREATE PROCEDURE updateGaurantor(
    IN p_gaurantorsName VARCHAR(255),
    IN p_gaurantorsContact1 VARCHAR(255),
    IN p_gaurantorsContact2 VARCHAR(255),
    IN p_gaurantorsIdNumber VARCHAR(255),
    IN p_gaurantorsRelationWithBorrower VARCHAR(255),
    IN p_gaurantorsHomeArea VARCHAR(255),
    IN p_gaurantorsBusiness VARCHAR(255),
    IN p_loanTrnId INT,
    IN p_gourantorPhone VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        -- Rollback on any error
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Update gaurantors table
    UPDATE gaurantors 
    SET
        gaurantorsName = p_gaurantorsName,
        gaurantorsContact1 = p_gaurantorsContact1,
        gaurantorsContact2 = p_gaurantorsContact2,
        gaurantorsIdNumber = p_gaurantorsIdNumber,
        gaurantorsRelationWithBorrower = p_gaurantorsRelationWithBorrower,
        gaurantorsHomeArea = p_gaurantorsHomeArea,
        gaurantorsBusiness = p_gaurantorsBusiness
    WHERE
        loanTrnId = p_loanTrnId;

    -- Update dailycollection table
    UPDATE dailycollection
    SET
        gourantorName = p_gaurantorsName,
        gourantorPhone = p_gourantorPhone
    WHERE
        trnId = p_loanTrnId;

    COMMIT;
END$$

DELIMITER ;




DROP PROCEDURE IF EXISTS agingAnalysisXXK;

DELIMITER $$

CREATE PROCEDURE agingAnalysisXXK()
BEGIN
    DECLARE l_done INT DEFAULT 0;
    DECLARE loanId VARCHAR(45);
    DECLARE customerName VARCHAR(60);
    DECLARE customerContactNumber VARCHAR(60);
    DECLARE TrnDate DATE;
    DECLARE princeremain DOUBLE DEFAULT 0;
    DECLARE interestRem DOUBLE DEFAULT 0;
    DECLARE arrears DOUBLE DEFAULT 0;
    DECLARE loanCycleStatus VARCHAR(45);
    DECLARE numberOfGaurantors INT DEFAULT 0;
    DECLARE gaurantorName1 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorContact1 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorName2 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorContact2 VARCHAR(60) DEFAULT '-';

    -- Cursor Declaration
    DECLARE loan_cursor CURSOR FOR
        SELECT loan_id 
        FROM new_loan_appstore 
        WHERE loan_cycle_status IN ('Disbursed', 'Renewed');

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Temporary Table Creation
    DROP TEMPORARY TABLE IF EXISTS aging_loan_analysis;
    CREATE TEMPORARY TABLE aging_loan_analysis (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        customer_name VARCHAR(60),
        customer_contact VARCHAR(60),
        date_taken DATE,
        loans_remaining DOUBLE,
        principal_remaining DOUBLE,
        interest_remaining DOUBLE,
        principal_inarrears DOUBLE DEFAULT 0,
        interest_inarrears DOUBLE DEFAULT 0,
        number_of_days_in_arrears INT,
        loan_deadline DATE,
        gaurantor_name1 VARCHAR(60),
        gaurantor_contact1 VARCHAR(60),
        gaurantor_name2 VARCHAR(60),
        gaurantor_contact2 VARCHAR(60),
        loan_status VARCHAR(45)
    );

    -- Open Cursor
    OPEN loan_cursor;

    loan_loop: LOOP
        FETCH loan_cursor INTO loanId;
        IF l_done THEN
            LEAVE loan_loop;
        END IF;

        -- Fetch Loan Details
        SELECT 
            pl.applicant_account_name, 
            m.mobile1, 
            pl.instalment_start_date, 
            pl.TotalPrincipalRemaining, 
            pl.TotalInterestRemaining, 
            pl.loan_cycle_status
        INTO 
            customerName, 
            customerContactNumber, 
            TrnDate, 
            princeremain, 
            interestRem, 
            loanCycleStatus
        FROM pmms.master m
        INNER JOIN pmms_loans.new_loan_appstore pl
            ON m.account_number = pl.applicant_account_number
        WHERE pl.loan_id = loanId;

        -- Calculate Arrears
        SELECT 
            SUM(PrincipalRemaining) + SUM(InterestRemaing),
            COUNT(*)
        INTO 
            arrears, numberOfGaurantors
        FROM new_loan_appstoreamort 
        WHERE master2_id = loanId 
          AND instalment_due_date <= CURDATE() 
          AND instalment_status != 'P';

        -- Fetch Gaurantor Details
        SELECT COUNT(*) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId = loanId;

        IF numberOfGaurantors = 1 THEN
            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName1, gaurantorContact1 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            LIMIT 1;
        ELSEIF numberOfGaurantors = 2 THEN
            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName1, gaurantorContact1 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            ORDER BY id ASC 
            LIMIT 1;

            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName2, gaurantorContact2 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            ORDER BY id DESC 
            LIMIT 1;
        ELSE
            SET gaurantorName1 = '-', gaurantorContact1 = '-', gaurantorName2 = '-', gaurantorContact2 = '-';
        END IF;

        -- Insert into Temporary Table
        INSERT INTO aging_loan_analysis (
            customer_name,
            customer_contact,
            date_taken,
            loans_remaining,
            principal_remaining,
            interest_remaining,
            principal_inarrears,
            interest_inarrears,
            number_of_days_in_arrears,
            loan_deadline,
            gaurantor_name1,
            gaurantor_contact1,
            gaurantor_name2,
            gaurantor_contact2,
            loan_status
        )
        VALUES (
            customerName,
            customerContactNumber,
            TrnDate,
            princeremain + interestRem,
            princeremain,
            interestRem,
            0, -- principal_inarrears (calculated later)
            0, -- interest_inarrears (calculated later)
            arrears,
            DATE_ADD(TrnDate, INTERVAL 30 DAY), -- loan_deadline
            gaurantorName1,
            gaurantorContact1,
            gaurantorName2,
            gaurantorContact2,
            loanCycleStatus
        );
    END LOOP;

    CLOSE loan_cursor;

    -- Categorization and Final Output
    SELECT 
        id,
        customer_name,
        customer_contact,
        date_taken,
        loans_remaining,
        principal_remaining,
        interest_remaining,
        principal_inarrears,
        interest_inarrears,
        number_of_days_in_arrears,
        loan_deadline,
        gaurantor_name1,
        gaurantor_contact1,
        gaurantor_name2,
        gaurantor_contact2,
        CASE
            WHEN number_of_days_in_arrears < 30 THEN 'Performing Portfolio'
            WHEN number_of_days_in_arrears BETWEEN 30 AND 59 THEN 'Portfolio at Risk (30-60 days)'
            WHEN number_of_days_in_arrears BETWEEN 60 AND 89 THEN 'Portfolio at Risk (60-90 days)'
            WHEN number_of_days_in_arrears BETWEEN 90 AND 359 THEN 'Non-Performing Portfolio'
            ELSE 'Portfolio Due for Write-Off'
        END AS classification
    FROM aging_loan_analysis
    ORDER BY number_of_days_in_arrears ASC;
END$$

DELIMITER ;


-- DROP PROCEDURE IF EXISTS agingAnalysisSimple;

-- DELIMITER ##

-- CREATE PROCEDURE agingAnalysisSimple()
-- BEGIN
--     DECLARE l_done INT;
--     DECLARE TrnId INT;
--     DECLARE LoanId VARCHAR(20);
--     DECLARE customerName VARCHAR(60);
--     DECLARE customerContactNumber VARCHAR(60);
--     DECLARE theLoanStatus VARCHAR(20);
--     DECLARE gaurantorName1 VARCHAR(100);
--     DECLARE gaurantorContact1 VARCHAR(100);
--     DECLARE gaurantorName2 VARCHAR(100);
--     DECLARE gaurantorContact2 VARCHAR(100);
--     DECLARE remainport DOUBLE;
--     DECLARE princeremain DOUBLE;
--     DECLARE interestRem DOUBLE;
--     DECLARE p_remain,loanTaken,totalRem,amount_arrears,P,I DOUBLE;
--     DECLARE i_remain DOUBLE;
--     DECLARE arrears INT;
--     DECLARE TrnDate DATE;

--     -- Cursor for loan IDs with status 'Disbursed' or 'Renewed'
--     DECLARE forSelectingLoanIds CURSOR FOR
--         SELECT DISTINCT trn_id
--         FROM new_loan_appstore
--         WHERE loan_cycle_status IN ('Disbursed', 'Renewed');
--     DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

--     -- Temporary table for aging analysis
--     DROP TABLE IF EXISTS aging_loan_analysis;
--     CREATE TEMPORARY TABLE aging_loan_analysis (
--         id INT NOT NULL AUTO_INCREMENT,
--         trn_id INT,
--         loan_id VARCHAR(20),
--         customer_name VARCHAR(60),
--         customer_contact VARCHAR(60),
--         gaurantor1_name VARCHAR(100),
--         gaurantor1_contact VARCHAR(100),
--         gaurantor2_name VARCHAR(100),
--         gaurantor2_contact VARCHAR(100),
--         date_taken DATE,
--         due_date DATE,
--         loan_taken DOUBLE,
--         principal_remaining DOUBLE,
--         interest_remaining DOUBLE,
--         total_remaining DOUBLE,
--         total_inarrears DOUBLE,
--         number_of_days_in_arrears INT,
--         loan_status VARCHAR(20),
--         PRIMARY KEY (id)
--     ) ENGINE = InnoDB DEFAULT CHARSET = utf8;

--     -- Open cursor and start loop
--     OPEN forSelectingLoanIds;
--     accounts_loop: LOOP
--         FETCH forSelectingLoanIds INTO TrnId;
--         IF l_done THEN
--             LEAVE accounts_loop;
--         END IF;

--         -- Reset variables for each loan
--         SET customerName = NULL, customerContactNumber = NULL, theLoanStatus = NULL;
--         SET gaurantorName1 = NULL, gaurantorContact1 = NULL, gaurantorName2 = NULL, gaurantorContact2 = NULL;
--         SET remainport = 0, princeremain = 0, interestRem = 0, p_remain = 0, i_remain = 0, arrears = 0;

--         -- Fetch main loan details
--         SELECT pl.loan_id, applicant_account_name, m.mobile1, pl.trn_date,
--                pl.princimpal_amount, pl.TotalPrincipalRemaining, pl.TotalInterestRemaining,
--               pl.balance_due, pl.loan_cycle_status
--         INTO LoanId, customerName, customerContactNumber, TrnDate, loanTaken,
--              princeremain, interestRem, totalRem, theLoanStatus
--         FROM pmms.master m
--         INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number = m.account_number
--         WHERE pl.trn_id = TrnId;

--         -- Calculate remaining amounts and arrears
--         SELECT SUM(PrincipalRemaining) ,SUM(InterestRemaing),(SUM(PrincipalRemaining) + SUM(InterestRemaing)), numberOfDayInArrears(LoanId)
--         INTO P,I, amount_arrears, arrears
--         FROM new_loan_appstoreamort
--         WHERE master1_id = TrnId AND instalment_due_date <= DATE(NOW()) AND NOT instalment_status = 'P';
-- /* SELECT P,I,  amount_arrears; */
--         -- Fetch guarantors
--         SELECT gaurantorsName, gaurantorsContact1 INTO gaurantorName1, gaurantorContact1
--         FROM gaurantors
--         WHERE loanTrnId = LoanId
--         ORDER BY id ASC
--         LIMIT 1;

--         SELECT gaurantorsName, gaurantorsContact1 INTO gaurantorName2, gaurantorContact2
--         FROM gaurantors
--         WHERE loanTrnId = LoanId
--         ORDER BY id DESC
--         LIMIT 1;

--         -- Insert data into consolidated table
--         INSERT INTO aging_loan_analysis (
--             trn_id,loan_id, customer_name, customer_contact, gaurantor1_name, gaurantor1_contact, 
--             gaurantor2_name, gaurantor2_contact, date_taken, due_date, loan_taken, 
--             principal_remaining, interest_remaining,total_remaining,total_inarrears,number_of_days_in_arrears, loan_status
--         )
--         VALUES (
--             TrnId,LoanId, customerName, customerContactNumber, gaurantorName1, gaurantorContact1,
--             gaurantorName2, gaurantorContact2, TrnDate, DATE_ADD(TrnDate, INTERVAL 30 DAY),
--             loanTaken, princeremain, interestRem, totalRem, amount_arrears, arrears, theLoanStatus
--         );

--         SET l_done = 0;
--     END LOOP;

--     CLOSE forSelectingLoanIds;

--     -- Select data categorized by aging period
--     SELECT * FROM aging_loan_analysis ORDER BY loan_status, number_of_days_in_arrears;

-- END ##

-- -- 17/03/2025	17/03/2025	Twesiime Paul cleared with 12.3 Processed on 17/03/2025
-- --   From Cash At Hand	-	2700000.0	Cash At Hand

-- DELIMITER ;

DROP PROCEDURE IF EXISTS agingAnalysisSimple;

DELIMITER ##

CREATE PROCEDURE agingAnalysisSimple()
BEGIN
    DECLARE l_done INT DEFAULT 0;
    DECLARE TrnId INT;
    DECLARE LoanId VARCHAR(20);
    DECLARE customerName VARCHAR(60);
    DECLARE customerContactNumber VARCHAR(60);
    DECLARE theLoanStatus VARCHAR(20);
    DECLARE gaurantorName1 VARCHAR(100);
    DECLARE gaurantorContact1 VARCHAR(100);
    DECLARE gaurantorName2 VARCHAR(100);
    DECLARE gaurantorContact2 VARCHAR(100);
    DECLARE remainport DOUBLE;
    DECLARE princeremain DOUBLE;
    DECLARE interestRem DOUBLE;
    DECLARE p_remain DOUBLE;
    DECLARE loanTaken DOUBLE;
    DECLARE totalRem DOUBLE;
    DECLARE amount_arrears DOUBLE;
    DECLARE P DOUBLE;
    DECLARE I DOUBLE;
    DECLARE i_remain DOUBLE;
    DECLARE arrears INT;
    DECLARE TrnDate DATE;
    DECLARE userId VARCHAR(50);
    DECLARE comp_name VARCHAR(100);
    DECLARE branch_name VARCHAR(100);

    -- Cursor for loan IDs with status 'Disbursed' or 'Renewed'
    DECLARE forSelectingLoanIds CURSOR FOR
        SELECT DISTINCT trn_id
        FROM new_loan_appstore
        WHERE loan_cycle_status IN ('Disbursed', 'Renewed');
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Temporary table for aging analysis with additional company and user details
    DROP TABLE IF EXISTS aging_loan_analysis;
    CREATE TEMPORARY TABLE aging_loan_analysis (
        id INT NOT NULL AUTO_INCREMENT,
        trn_id INT,
        loan_id VARCHAR(20),
        customer_name VARCHAR(60),
        customer_contact VARCHAR(60),
        gaurantor1_name VARCHAR(100),
        gaurantor1_contact VARCHAR(100),
        gaurantor2_name VARCHAR(100),
        gaurantor2_contact VARCHAR(100),
        date_taken DATE,
        due_date DATE,
        loan_taken DOUBLE,
        principal_remaining DOUBLE,
        interest_remaining DOUBLE,
        total_remaining DOUBLE,
        total_inarrears DOUBLE,
        number_of_days_in_arrears INT,
        loan_status VARCHAR(20),
        company_name VARCHAR(100),
        branch_name VARCHAR(100),
        user_id VARCHAR(50),
        PRIMARY KEY (id)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8;

    -- Retrieve company details (only one row exists)
    SELECT the_company_name, the_company_branch
      INTO comp_name, branch_name
      FROM pmms.the_company_datails
      LIMIT 1;

    -- Open cursor and process each loan record
    OPEN forSelectingLoanIds;
    accounts_loop: LOOP
        FETCH forSelectingLoanIds INTO TrnId;
        IF l_done THEN
            LEAVE accounts_loop;
        END IF;

        -- Reset variables for each iteration
        SET customerName = NULL, customerContactNumber = NULL, theLoanStatus = NULL;
        SET gaurantorName1 = NULL, gaurantorContact1 = NULL, gaurantorName2 = NULL, gaurantorContact2 = NULL;
        SET remainport = 0, princeremain = 0, interestRem = 0, p_remain = 0, i_remain = 0, arrears = 0, userId = NULL;

        -- Fetch main loan details including gruop_id as userId
        SELECT pl.loan_id, applicant_account_name, m.mobile1, pl.trn_date,
               pl.princimpal_amount, pl.TotalPrincipalRemaining, pl.TotalInterestRemaining,
               pl.balance_due, pl.loan_cycle_status, pl.gruop_id
          INTO LoanId, customerName, customerContactNumber, TrnDate, loanTaken,
               princeremain, interestRem, totalRem, theLoanStatus, userId
          FROM pmms.master m
          INNER JOIN pmms_loans.new_loan_appstore pl 
             ON pl.applicant_account_number = m.account_number
         WHERE pl.trn_id = TrnId;

        -- Calculate arrears details from amortization table
        SELECT SUM(PrincipalRemaining), SUM(InterestRemaing), 
               (SUM(PrincipalRemaining) + SUM(InterestRemaing)), numberOfDayInArrears(LoanId)
          INTO P, I, amount_arrears, arrears
          FROM new_loan_appstoreamort
         WHERE master1_id = TrnId 
           AND instalment_due_date <= DATE(NOW()) 
           AND NOT instalment_status = 'P';

        -- Fetch guarantors (first and last if available)
        SELECT gaurantorsName, gaurantorsContact1 
          INTO gaurantorName1, gaurantorContact1
          FROM gaurantors
         WHERE loanTrnId = LoanId
         ORDER BY id ASC
         LIMIT 1;

        SELECT gaurantorsName, gaurantorsContact1 
          INTO gaurantorName2, gaurantorContact2
          FROM gaurantors
         WHERE loanTrnId = LoanId
         ORDER BY id DESC
         LIMIT 1;

        -- Insert data into temporary aging analysis table including company and user details
        INSERT INTO aging_loan_analysis (
            trn_id, loan_id, customer_name, customer_contact, 
            gaurantor1_name, gaurantor1_contact, 
            gaurantor2_name, gaurantor2_contact, 
            date_taken, due_date, loan_taken, 
            principal_remaining, interest_remaining, total_remaining, 
            total_inarrears, number_of_days_in_arrears, loan_status, 
            company_name, branch_name, user_id
        )
        VALUES (
            TrnId, LoanId, customerName, customerContactNumber, 
            gaurantorName1, gaurantorContact1,
            gaurantorName2, gaurantorContact2, 
            TrnDate, DATE_ADD(TrnDate, INTERVAL 30 DAY),
            loanTaken, princeremain, interestRem, totalRem, 
            amount_arrears, arrears, theLoanStatus,
            comp_name, branch_name, userId
        );

        SET l_done = 0;
    END LOOP;

    CLOSE forSelectingLoanIds;

    -- Return the aging analysis results ordered by loan status and days in arrears
    SELECT * FROM aging_loan_analysis ORDER BY loan_status, number_of_days_in_arrears;
    
END ##
DELIMITER ;

-- DROP TABLE IF EXISTS loan_files;
-- CREATE TABLE loan_files (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     loan_id INT NOT NULL,
--     file_path VARCHAR(255) NOT NULL,
--     file_name VARCHAR(255) NOT NULL,
--     upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (loan_id) REFERENCES new_loan_appstore(trn_id)
-- );
-- DROP TABLE IF EXISTS loan_files;

CREATE TABLE loan_files (
  id INT AUTO_INCREMENT PRIMARY KEY,
  loan_id VARCHAR(50) NOT NULL,
  file_path VARCHAR(255) NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_loan_files_loan
    FOREIGN KEY (loan_id)
    REFERENCES new_loan_appstore(trn_id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE daily_reports (
    report_date DATE NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    report_text TEXT NOT NULL,
    PRIMARY KEY (report_date, contact_number)
);




CREATE TABLE arch_new_loan_appstore (
    trn_id int(11) NOT NULL,
    trn_date date NOT NULL,
    loan_id varchar(100) NOT NULL ,
    total_instalments varchar(45) DEFAULT NULL,
    remaining_instalments varchar(45) DEFAULT NULL,
    princimpal_amount varchar(45) DEFAULT NULL,
    total_interest varchar(45) DEFAULT NULL,
    total_loanAmount varchar(45) DEFAULT NULL,
    balance_due varchar(45) DEFAULT NULL,
    instalment_start_date date NOT NULL,
    instalment_next_due_date date NOT NULL,
    instalment_end_date date NOT NULL,
    interest_rate varchar(45) DEFAULT NULL,
    applicant_account_name varchar(200) NOT NULL,
    loan_cycle_status varchar(45) DEFAULT NULL,
    trn_time time NOT NULL,
    instalments_paid varchar(45) DEFAULT NULL,
    instalment_amount varchar(45) DEFAULT NULL,
    loan_tenure varchar(45) DEFAULT NULL,
    applicant_account_number varchar(45) DEFAULT NULL,
    inputter_id varchar(10) DEFAULT NULL,
    authoriser_id varchar(10) DEFAULT NULL,
    gruop_id varchar(30) DEFAULT NULL,
    GroupId varchar(50) NOT NULL DEFAULT 'G0001',
    GroupName varchar(100) NOT NULL DEFAULT 'GROUP1',
    SecurityLoan varchar(500) DEFAULT NULL,
    OtherGroups2 int(11) NOT NULL,
    OtherGroups3 varchar(100) NOT NULL DEFAULT 'Other3',
    OtherGroups4 varchar(100) NOT NULL DEFAULT 'Other4',
    TotalInterestPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalPrincipalPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalPrincipalRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccumulatedInterestPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccumulatedInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalLoanPenaltyPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalLoanPenaltyRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccruedInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccruedInterestPaid varchar(100) NOT NULL DEFAULT '0.0'
);


CREATE TABLE arch_new_loan_appstore1 (
    trn_id int(11) NOT NULL,
    trn_date date NOT NULL,
    loan_id varchar(100) NOT NULL,
    total_instalments varchar(45) DEFAULT NULL,
    remaining_instalments varchar(45) DEFAULT NULL,
    princimpal_amount varchar(45) DEFAULT NULL,
    total_interest varchar(45) DEFAULT NULL,
    total_loanAmount varchar(45) DEFAULT NULL,
    balance_due varchar(45) DEFAULT NULL,
    instalment_start_date date NOT NULL,
    instalment_next_due_date date NOT NULL,
    instalment_end_date date NOT NULL,
    interest_rate varchar(45) DEFAULT NULL,
    applicant_account_name varchar(45) DEFAULT NULL,
    loan_cycle_status varchar(45) DEFAULT NULL,
    trn_time time NOT NULL,
    instalments_paid varchar(45) DEFAULT NULL,
    instalment_amount varchar(45) DEFAULT NULL,
    loan_tenure varchar(45) DEFAULT NULL,
    applicant_account_number varchar(45) DEFAULT NULL,
    inputter_id varchar(10) DEFAULT NULL,
    authoriser_id varchar(10) DEFAULT NULL,
    gruop_id varchar(30) DEFAULT NULL,
    GroupId varchar(50) NOT NULL DEFAULT 'G0001',
    GroupName varchar(100) NOT NULL DEFAULT 'GROUP1',
    SecurityLoan varchar(500) DEFAULT NULL,
    OtherGroups2 int(11) NOT NULL,
    OtherGroups3 varchar(100) NOT NULL DEFAULT 'Other3',
    OtherGroups4 varchar(100) NOT NULL DEFAULT 'Other4',
    TotalInterestPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalPrincipalPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalPrincipalRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccumulatedInterestPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccumulatedInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalLoanPenaltyPaid varchar(100) NOT NULL DEFAULT '0.0',
    TotalLoanPenaltyRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccruedInterestRemaining varchar(100) NOT NULL DEFAULT '0.0',
    TotalAccruedInterestPaid varchar(100) NOT NULL DEFAULT '0.0',
    ClientExistanceCat varchar(100) NOT NULL DEFAULT 'First Time Borrower',
    BorrowingCategory varchar(100) NOT NULL DEFAULT 'Group',
    LoanGroupId varchar(100) NOT NULL DEFAULT 'Kanama',
    LoanGroupName varchar(100) NOT NULL DEFAULT 'Kanama',
    LoanCycle varchar(100) NOT NULL DEFAULT 'Cycle1',
    LoanProduct varchar(100) NOT NULL DEFAULT 'Salary Loan',
    MarketingChannel varchar(100) NOT NULL DEFAULT 'Internet',
    TenureType varchar(100) NOT NULL DEFAULT 'Amortized Loan',
    LoanPurpose varchar(100) NOT NULL DEFAULT 'Personal Effect',
    PaymentHistory varchar(100) NOT NULL DEFAULT 'No History',
    ClientLoyalty varchar(100) NOT NULL DEFAULT 'Cant Tell',
    SecurityUsage varchar(100) NOT NULL DEFAULT 'No',
    NumberOfSecurity varchar(100) NOT NULL DEFAULT '0.0',
    EconomicWelbeingLevel varchar(100) NOT NULL DEFAULT 'Level 1',
    PaymentSource varchar(100) NOT NULL DEFAULT 'Salary',
    AmountTakenCategory varchar(100) NOT NULL DEFAULT 'Above 100M',
    IncomeLevel varchar(100) NOT NULL DEFAULT 'Very Low',
    EmploymentCat varchar(100) NOT NULL DEFAULT 'Formally Employed',
    Occupation varchar(100) NOT NULL DEFAULT 'Agriculture',
    Sex varchar(100) NOT NULL DEFAULT 'Male',
    MaritalStatus varchar(100) NOT NULL DEFAULT 'Single',
    AgeGroup varchar(100) NOT NULL DEFAULT 'Between 18-25Yrs',
    EducationLevel varchar(100) NOT NULL DEFAULT 'Not Educatted',
    PreferedContactChannel varchar(100) NOT NULL DEFAULT 'Email',
    Email varchar(100) NOT NULL DEFAULT 'augbazi@gmail.com',
    BorrowerCharacter varchar(100) NOT NULL DEFAULT 'Daughtful',
    PhoneNumber varchar(100) NOT NULL DEFAULT '0782231039',
    commentsClosing varchar(600) NOT NULL DEFAULT '1;General Comments;Not Specified:2;Payment promptness;Not Specified:3;Self Reminding;Not Specified:4;Top Ups;Not Specified:5;Others (Please Specify);Not Specified',
    OtherTwo varchar(100) NOT NULL DEFAULT 'NA',
    OtherThree varchar(100) NOT NULL DEFAULT 'NA',
    OtherFour varchar(100) NOT NULL DEFAULT 'NA',
    OtherFive varchar(100) NOT NULL DEFAULT 'NA'
);

CREATE TABLE arch_new_loan_appstoreamort (
    trn_id int(11) NOT NULL ,
    instalment_no int(11) NOT NULL DEFAULT '1',
    princimpal_amount varchar(50) NOT NULL DEFAULT '0.0',
    princimpal_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
    interest_amount varchar(50) NOT NULL DEFAULT '10000.0',
    interest_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
    instalment_amount varchar(50) NOT NULL DEFAULT '0.0',
    instalment_paid varchar(50) NOT NULL DEFAULT '0.0',
    beginning_bal varchar(50) NOT NULL DEFAULT '0.0',
    closing_bal varchar(50) NOT NULL DEFAULT '0.0',
    instalment_due_date date NOT NULL DEFAULT '1979-01-01',
    instalment_status varchar(50) NOT NULL DEFAULT 'P',
    instalment_paid_date date NOT NULL DEFAULT '1979-01-01',
    instalment_paid_variance varchar(50) NOT NULL DEFAULT '2 Days',
    LoanPenalty varchar(50) NOT NULL DEFAULT '0.0',
    AccruedInterest varchar(50) NOT NULL DEFAULT '0.0',
    InstalmentRemaining varchar(50) NOT NULL DEFAULT '0.0',
    PrincipalPaid varchar(50) NOT NULL DEFAULT '0.0',
    PrincipalRemaining varchar(50) NOT NULL DEFAULT '0.0',
    InterestPaid varchar(50) NOT NULL DEFAULT '0.0',
    InterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
    LoanPenaltyPaid varchar(50) NOT NULL DEFAULT '0.0',
    LoanPenaltyRemaining varchar(50) NOT NULL DEFAULT '0.0',
    AccruedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
    AccruedInterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
    AccumulatedInterest varchar(50) NOT NULL DEFAULT '0.0',
    AccumulatedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
    AccumulatedInterestRemaining varchar(50) NOT NULL DEFAULT '0.0',
    OtherOne varchar(50) NOT NULL DEFAULT 'NYA',
    OtherTwo date NOT NULL DEFAULT '1979-01-01',
    OtherThree date NOT NULL DEFAULT '1979-01-01',
    master1_id int(11) NOT NULL,
    master2_id varchar(50) NOT NULL DEFAULT '0.0'
);

CREATE TABLE pmms.arch_loandisburserepaystatement (
    TrnId int(11) NOT NULL,
    TrnDate date DEFAULT '1970-01-01',
    MonthPaid varchar(100) DEFAULT 'January',
    YearPaid varchar(100) DEFAULT '2017',
    loanTrnId int(11) NOT NULL,
    LoanId varchar(100) DEFAULT 'newloan05502000110',
    AccountNumber varchar(100) DEFAULT 'newloan05502000110',
    BatchCode varchar(100) DEFAULT 'B002',
    AmountDisbursed varchar(100) DEFAULT '0.0',
    ExpectedInterest varchar(100) DEFAULT '0.0',
    ExpectedTotalAmount varchar(100) DEFAULT '0.0',
    InterestRate varchar(100) DEFAULT '0',
    AmountPaid varchar(100) DEFAULT '0.0',
    PrincipalPaid varchar(100) DEFAULT '0.0',
    InterestPaid varchar(100) DEFAULT '0.0',
    AccumulatedInterestPaid varchar(100) DEFAULT '0.0',
    LoanPenaltyPaid varchar(100) DEFAULT '0.0',
    PrincipalBalance varchar(100) DEFAULT '0.0',
    InterestBalance varchar(100) DEFAULT '0.0',
    AccumulatedInterestBalance varchar(100) DEFAULT '0.0',
    LoanPenaltyBalance varchar(100) DEFAULT '0.0',
    LoanBalance varchar(100) DEFAULT '0.0',
    LoanStatusReport varchar(100) DEFAULT 'Running',
    OtherOne varchar(100) DEFAULT 'NA',
    OtherTwo varchar(100) DEFAULT 'NA',
    OtherThree varchar(100) DEFAULT 'NA',
    OtherFour varchar(100) DEFAULT 'NA'
);

-- -- Step 1: Drop the existing primary key on trn_id
-- ALTER TABLE arch_new_loan_appstore
-- DROP PRIMARY KEY;

-- -- Step 2: Add a new auto-increment column 'id'
-- ALTER TABLE arch_new_loan_appstore
-- ADD COLUMN id INT(11) NOT NULL AUTO_INCREMENT FIRST;

-- -- Step 3: Add a composite primary key (auto_increment column must be the leftmost part)
-- ALTER TABLE arch_new_loan_appstore
-- ADD PRIMARY KEY (id, trn_id);

-- -- (Optional) Step 4: Create a non-unique index on trn_id if needed for lookups
-- ALTER TABLE arch_new_loan_appstore
-- ADD INDEX idx_trn_id (trn_id);


DROP PROCEDURE InitialArchiveCompletedLoans;
 DELIMITER $$

CREATE PROCEDURE InitialArchiveCompletedLoans()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE loanId VARCHAR(100);
    DECLARE completionStatus INT;
    DECLARE loanCursor CURSOR FOR 
        SELECT trn_id, loan_cycle_status
        FROM new_loan_appstore
        WHERE loan_cycle_status = 'Completed' OR loan_cycle_status = 'RenewedClosed' OR loan_cycle_status = 'WrittenOff';
        
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN loanCursor;

    loanLoop: LOOP
        FETCH loanCursor INTO loanId, completionStatus;
        IF done THEN
            LEAVE loanLoop;
        END IF;

        -- SELECT loanId, completionStatus;

        -- Insert into archive tables
        INSERT INTO arch_new_loan_appstore
        SELECT * FROM new_loan_appstore WHERE trn_id = loanId;

        INSERT INTO arch_new_loan_appstore1
        SELECT * FROM new_loan_appstore1 WHERE trn_id = loanId;

        INSERT INTO arch_new_loan_appstoreamort
        SELECT * FROM new_loan_appstoreamort WHERE master1_id = loanId;

        INSERT INTO pmms.arch_loandisburserepaystatement
        SELECT * FROM pmms.loandisburserepaystatement WHERE loanTrnId = loanId;

        -- Delete from active tables
        DELETE FROM new_loan_appstore WHERE trn_id = loanId;
        DELETE FROM new_loan_appstore1 WHERE trn_id = loanId;
        DELETE FROM new_loan_appstoreamort WHERE master1_id = loanId;
        DELETE FROM pmms.loandisburserepaystatement WHERE loanTrnId = loanId;

    END LOOP;

    CLOSE loanCursor;

    -- Commit the transaction to make sure all changes are saved
    COMMIT;
END$$

DELIMITER ;



CALL  InitialArchiveCompletedLoans();




DROP PROCEDURE ReturnWrittenOffLoans;
 DELIMITER $$

CREATE PROCEDURE ReturnWrittenOffLoans()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE loanId VARCHAR(100);
    DECLARE completionStatus INT;
    DECLARE loanCursor CURSOR FOR 
        SELECT trn_id, loan_cycle_status
        FROM arch_new_loan_appstore
        WHERE loan_cycle_status = 'WrittenOff';
        
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN loanCursor;

    loanLoop: LOOP
        FETCH loanCursor INTO loanId, completionStatus;
        IF done THEN
            LEAVE loanLoop;
        END IF;

        -- SELECT loanId, completionStatus;

        -- Insert into archive tables
        INSERT INTO new_loan_appstore
        SELECT * FROM arch_new_loan_appstore  WHERE trn_id = loanId;

        INSERT INTO new_loan_appstore1
        SELECT * FROM  arch_new_loan_appstore1 WHERE trn_id = loanId;

        INSERT INTO new_loan_appstoreamort
        SELECT * FROM  arch_new_loan_appstoreamort WHERE master1_id = loanId;

        INSERT INTO pmms.loandisburserepaystatement
        SELECT * FROM  pmms.arch_loandisburserepaystatement WHERE loanTrnId = loanId;

        -- Delete from active tables
        DELETE FROM arch_new_loan_appstore WHERE trn_id = loanId;
        DELETE FROM arch_new_loan_appstore1 WHERE trn_id = loanId;
        DELETE FROM arch_new_loan_appstoreamort WHERE master1_id = loanId;
        DELETE FROM  pmms.arch_loandisburserepaystatement WHERE loanTrnId = loanId;

    END LOOP;

    CLOSE loanCursor;

    -- Commit the transaction to make sure all changes are saved
    COMMIT;
END$$

DELIMITER ;



CALL  ReturnWrittenOffLoans();



ALTER TABLE loan_files
  ADD CONSTRAINT fk_loan_files_to_newloan
  FOREIGN KEY (loan_id) REFERENCES new_loan_appstore(trn_id)
  ON DELETE CASCADE;


DROP PROCEDURE IF EXISTS MoveClosedLoan;

DELIMITER $$

CREATE PROCEDURE MoveClosedLoan(IN p_loanId INT)
BEGIN
    -- DECLARE EXIT HANDLER FOR SQLEXCEPTION
    -- BEGIN
    --     ROLLBACK;
    -- END;

    -- START TRANSACTION;

    -- -----------------------------------------------------------------
    -- Step 1: Archive Parent Records
    -- -----------------------------------------------------------------
    INSERT INTO arch_new_loan_appstore
    SELECT * FROM new_loan_appstore WHERE trn_id = p_loanId;

    INSERT INTO arch_new_loan_appstore1
    SELECT * FROM new_loan_appstore1 WHERE trn_id = p_loanId;

    INSERT INTO arch_new_loan_appstoreamort
    SELECT * FROM new_loan_appstoreamort WHERE master1_id = p_loanId;

    INSERT INTO pmms.arch_loandisburserepaystatement
    SELECT * FROM pmms.loandisburserepaystatement WHERE loanTrnId = p_loanId;

    -- -----------------------------------------------------------------
    -- Step 2: Archive Child Records (after parents exist)
    -- -----------------------------------------------------------------
    INSERT INTO archive_loan_files
    SELECT * FROM loan_files WHERE loan_id = p_loanId;

    -- -----------------------------------------------------------------
    -- Step 3: Delete Children from Live Tables
    -- -----------------------------------------------------------------
    DELETE FROM loan_files WHERE loan_id = p_loanId;
    DELETE FROM pmms.loandisburserepaystatement WHERE loanTrnId = p_loanId;
    DELETE FROM new_loan_appstoreamort WHERE master1_id = p_loanId;
    DELETE FROM new_loan_appstore1 WHERE trn_id = p_loanId;

    -- -----------------------------------------------------------------
    -- Step 4: Delete Parent from Live Table
    -- -----------------------------------------------------------------
    DELETE FROM new_loan_appstore WHERE trn_id = p_loanId;

    -- COMMIT;
END$$

DELIMITER ;

    -- DELETE FROM loan_files WHERE loan_id = 70187;
    -- DELETE FROM pmms.loandisburserepaystatement WHERE loanTrnId = 70187;
    -- DELETE FROM new_loan_appstoreamort WHERE master1_id = 70187;
    -- DELETE FROM new_loan_appstore1 WHERE trn_id = 70187;
    -- DELETE FROM new_loan_appstore WHERE trn_id = 70187;
    --     DELETE FROM archive_loan_files WHERE loan_id = 70187;
    -- DELETE FROM pmms.arch_loandisburserepaystatement WHERE loanTrnId = 70187;
    -- DELETE FROM arch_new_loan_appstoreamort WHERE master1_id = 70187;
    -- DELETE FROM arch_new_loan_appstore1 WHERE trn_id = 70187;
    -- DELETE FROM arch_new_loan_appstore WHERE trn_id = 70187;

-- DROP PROCEDURE IF EXISTS MoveLoanFileAttach;

-- DELIMITER $$

-- CREATE PROCEDURE MoveLoanFileAttach(
--     IN loanId VARCHAR(100)
-- )
-- BEGIN
--    -- Insert into archive tables
--         INSERT INTO archive_loan_files
--         SELECT * FROM loan_files WHERE loan_id = loanId;


--         -- Delete from active tables
--         DELETE FROM loan_files WHERE loan_id = loanId;


-- END$$

-- DELIMITER ;



DROP PROCEDURE IF EXISTS `loanRepaymentsUpdatesAll`;
DELIMITER //
CREATE  PROCEDURE `loanRepaymentsUpdatesAll`(IN typOfRepayment VARCHAR(30),IN loanId VARCHAR(30),IN InstalmentNo INTEGER,IN amountPAI INTEGER)
BEGIN
 
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

 
 END //
DELIMITER ;




/* ──────────────────────────────────────────────────────────
   Portfolio summary ‑ counts, amounts, % of total
   Amount = Principal + Interest + Penalty remaining
   MySQL 5.5 compatible
────────────────────────────────────────────────────────── */
DROP PROCEDURE IF EXISTS sp_portfolio_summary;
DELIMITER $$

CREATE PROCEDURE sp_portfolio_summary()
BEGIN
    /* 1. Working variables */
    DECLARE v_grand_loans   INT;
    DECLARE v_grand_amount  DECIMAL(22,2);

    /* 2. Prepare temporary table */
    DROP TEMPORARY TABLE IF EXISTS tmp_portfolio_summary;
    CREATE TEMPORARY TABLE tmp_portfolio_summary (
        loan_status       VARCHAR(20),
        category          VARCHAR(100),
        total_loans       INT,
        amount_remaining  DECIMAL(22,2),
        percent_of_total  DECIMAL(6,2)
    );

    START TRANSACTION;

    /* ─────────────────────────────────────────────────────
       A) DISBURSED LOANS
    ───────────────────────────────────────────────────── */
    --  A1) Compute grand totals
    SELECT
      COUNT(*) AS cnt,
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ) AS amt
    INTO
      v_grand_loans,
      v_grand_amount
    FROM new_loan_appstore
    WHERE loan_cycle_status = 'Disbursed';

    IF v_grand_amount IS NULL OR v_grand_amount = 0 THEN
      SET v_grand_amount = 1;  /* avoid division by zero */
    END IF;

    --  A2) Aging buckets for Disbursed
    INSERT INTO tmp_portfolio_summary
    SELECT
      'Disbursed',
      'Active (0–30 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Disbursed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 0 AND 30;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Disbursed',
      'At Risk (31–90 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Disbursed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 31 AND 90;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Disbursed',
      'Non‑Performing (91–360 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Disbursed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 91 AND 360;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Disbursed',
      'Due‑For‑Write‑Off (>360 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Disbursed'
      AND numberOfDayInArrears(nla.loan_id) > 360;

    --  A3) Grand total row for Disbursed
    INSERT INTO tmp_portfolio_summary
    VALUES
      ('Disbursed','Grand Total', v_grand_loans, v_grand_amount, 100.00);


    /* ─────────────────────────────────────────────────────
       B) RENEWED LOANS
    ───────────────────────────────────────────────────── */
    --  B1) Compute grand totals
    SELECT
      COUNT(*) AS cnt,
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ) AS amt
    INTO
      v_grand_loans,
      v_grand_amount
    FROM new_loan_appstore
    WHERE loan_cycle_status = 'Renewed';

    IF v_grand_amount IS NULL OR v_grand_amount = 0 THEN
      SET v_grand_amount = 1;
    END IF;

    --  B2) Aging buckets for Renewed
    INSERT INTO tmp_portfolio_summary
    SELECT
      'Renewed',
      'Active (0–30 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Renewed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 0 AND 30;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Renewed',
      'At Risk (31–90 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Renewed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 31 AND 90;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Renewed',
      'Non‑Performing (91–360 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Renewed'
      AND numberOfDayInArrears(nla.loan_id) BETWEEN 91 AND 360;

    INSERT INTO tmp_portfolio_summary
    SELECT
      'Renewed',
      'Due‑For‑Write‑Off (>360 days)',
      COUNT(*),
      SUM(
        CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
        CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
      ),
      ROUND(
        SUM(
          CAST(COALESCE(TotalInterestRemaining ,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalPrincipalRemaining,0)  AS DECIMAL(22,2)) +
          CAST(COALESCE(TotalLoanPenaltyRemaining,0) AS DECIMAL(22,2))
        ) / v_grand_amount * 100
      ,2)
    FROM new_loan_appstore AS nla
    WHERE
      nla.loan_cycle_status = 'Renewed'
      AND numberOfDayInArrears(nla.loan_id) > 360;

    --  B3) Grand total row for Renewed
    INSERT INTO tmp_portfolio_summary
    VALUES
      ('Renewed','Grand Total', v_grand_loans, v_grand_amount, 100.00);

    COMMIT;

    /* 3. Final output */
    SELECT
      loan_status,
      category,
      total_loans,
      FORMAT(amount_remaining,0)        AS amount_remaining,
      CONCAT(ROUND(percent_of_total,0),'%') AS percent_of_total
    FROM tmp_portfolio_summary
    ORDER BY
      loan_status,
      FIELD(category,
        'Active (0–30 days)',
        'At Risk (31–90 days)',
        'Non‑Performing (91–360 days)',
        'Due‑For‑Write‑Off (>360 days)',
        'Grand Total'
      );
END $$
DELIMITER ;

-- To execute:
CALL sp_portfolio_summary();


-- Run it
CALL sp_portfolio_summary();



DROP PROCEDURE IF EXISTS smsExpensesSummaryReport;
DELIMITER $$

CREATE PROCEDURE smsExpensesSummaryReport()
    READS SQL DATA
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE vAccNo VARCHAR(100);
    DECLARE vAccName VARCHAR(200);
    DECLARE vTbl VARCHAR(130);
    DECLARE vTotal DECIMAL(22,2);

    DECLARE cur CURSOR FOR
        SELECT account_number, account_name
        FROM   pmms.account_created_store
        WHERE  account_l2 = 'Expenses';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    DROP TEMPORARY TABLE IF EXISTS tmp_expenses;
    CREATE TEMPORARY TABLE tmp_expenses (
        accountName VARCHAR(200),
        narration   VARCHAR(200),
        amount      DECIMAL(22,2)
    );

    OPEN cur;
    fetch_loop: LOOP
        FETCH cur INTO vAccNo, vAccName;
        IF done THEN LEAVE fetch_loop; END IF;

        SET vTbl = CONCAT('pmms.bsanca', vAccNo);

        IF EXISTS (
            SELECT 1 FROM information_schema.tables
            WHERE  table_schema = 'pmms'
              AND  table_name   = CONCAT('bsanca', vAccNo)
        ) THEN
            /* embed the account name, escaping single quotes */
            SET @sql = CONCAT(
              'INSERT INTO tmp_expenses (accountName,narration,amount) ',
              'SELECT ''',
              REPLACE(vAccName, '''', ''''''),
              ''', ',
              'TRIM(TRAILING '' '' FROM SUBSTRING_INDEX(narration,''Processed on'',1)), ',
              'CAST(debit AS DECIMAL(22,2)) ',
              'FROM ', vTbl,
              ' WHERE trn_date = CURDATE() ',
              '  AND CAST(debit AS DECIMAL(22,2)) > 0'
            );

            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;
    END LOOP;
    CLOSE cur;

    SELECT IFNULL(SUM(amount),0) INTO vTotal FROM tmp_expenses;
    INSERT INTO tmp_expenses VALUES ('Total','Total Expenses',vTotal);

    SELECT  accountName AS account_name,
            narration,
            FORMAT(amount,0) AS debit
    FROM    tmp_expenses;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS smsLoansDisbursedSummaryReport;
DELIMITER $$

CREATE PROCEDURE smsLoansDisbursedSummaryReport()
    READS SQL DATA
BEGIN
    DECLARE vTotal DECIMAL(22,2);

    -- Drop and create temporary table
    DROP TEMPORARY TABLE IF EXISTS tmp_loans_out;
    CREATE TEMPORARY TABLE tmp_loans_out (
        accountName       VARCHAR(100),
        phone             VARCHAR(50),
        businessName      VARCHAR(100),
        officerName       VARCHAR(100),
        amountDisbursed   DECIMAL(22,2),
        guarantorName     VARCHAR(100),
        guarantorContact  VARCHAR(100),
        clientType        VARCHAR(10)
    );

    -- Populate the temp table with only ONE guarantor per loan (the "first" one)
    INSERT INTO tmp_loans_out (accountName, phone, businessName, officerName,
                               amountDisbursed, guarantorName,
                               guarantorContact, clientType)
    SELECT  
        lps.account_name,
        COALESCE(m.mobile1,'')                                       AS phone,
        COALESCE(bd.businessName,'')                                 AS businessName,
        COALESCE(staffName(nls.gruop_id),'')                         AS officerName,
        CAST(lps.princimpal_amount AS DECIMAL(22,2))                AS amountDisbursed,
        COALESCE(g.gaurantorsName,'')                                AS guarantorName,
        COALESCE(g.gaurantorsContact1,'')                            AS guarantorContact,
        CASE WHEN DATE(acs.creation_date) = CURDATE()
            THEN 'NEW' ELSE 'OLD' END                                AS clientType
    FROM    loanprocessingstore AS lps
    LEFT JOIN new_loan_appstore AS nls
        ON nls.loan_id = CONCAT('newloan', lps.account_number)
    LEFT JOIN businessdetails AS bd
        ON bd.id = nls.OtherGroups2
    -- This subquery ensures only one row per loanTrnId (lowest gaurantorsName, change to MIN(id) if you have PK)
    LEFT JOIN (
        SELECT
            loanTrnId,
            MIN(gaurantorsName) AS gaurantorsName,
            MIN(gaurantorsContact1) AS gaurantorsContact1
        FROM gaurantors
        GROUP BY loanTrnId
    ) AS g ON g.loanTrnId = nls.trn_id
    LEFT JOIN pmms.account_created_store AS acs
        ON acs.account_number = lps.account_number
    LEFT JOIN pmms.`master` AS m
        ON m.account_number  = lps.account_number
    WHERE lps.trn_date = CURDATE()
      AND IFNULL(lps.loan_cycle_status,'') = 'Disbursed';

    -- Grand total
    SELECT IFNULL(SUM(amountDisbursed),0) INTO vTotal FROM tmp_loans_out;
    INSERT INTO tmp_loans_out
        VALUES ('Total', '', '', '', vTotal, '', '', '');

    -- Final output – officer_name last
    SELECT  accountName                       AS account_name,
            phone,
            businessName                      AS business_name,
            FORMAT(amountDisbursed,0)         AS amount_disbursed,
            guarantorName                     AS guarantor_name,
            guarantorContact                  AS guarantor_contact,
            clientType                        AS client_type,
            officerName                       AS officer_name
    FROM    tmp_loans_out;
END $$
DELIMITER ;



-- Run:
CALL smsLoansDisbursedSummaryReport();


ALTER TABLE `daily_reports`
  -- 1) add the new column (an ENUM of your four types)
  ADD COLUMN `report_type` 
    ENUM('expense','officer','loans_disbursed','portfolio_summary') 
    NOT NULL 
    AFTER `contact_number`,
  -- 2) drop the old PK
  DROP PRIMARY KEY,
  -- 3) make the PK include report_type as well
  ADD PRIMARY KEY (`report_date`,`contact_number`,`report_type`);

update daily_reports set report_type='officer';


/* Phase 1 – allow NULLs while we populate the data */
ALTER TABLE gaurantors
  ADD COLUMN accountNumber VARCHAR(200) NULL
  AFTER gaurantorsBusiness;

/* Optional – an index helps later look-ups */
CREATE INDEX idx_gaurantors_accountNumber ON gaurantors (accountNumber);


/* -------------------------------------------------------------
   Clean build-up
------------------------------------------------------------- */
DROP PROCEDURE IF EXISTS update_gaurantors_accountNumber_proc;
DELIMITER $$

CREATE PROCEDURE update_gaurantors_accountNumber_proc()
BEGIN
    /* ---------- error safety ---------- */
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    /* ---------- 1. fill from live store ---------- */
    UPDATE gaurantors AS g
    JOIN   new_loan_appstore AS n  ON n.trn_id = g.loanTrnId
       SET g.accountNumber = n.applicant_account_number
     WHERE g.accountNumber IS NULL OR g.accountNumber = '';

    /* ---------- 2. fill from archive (still empty rows) ---------- */
    UPDATE gaurantors AS g
    JOIN   arch_new_loan_appstore AS a  ON a.trn_id = g.loanTrnId
       SET g.accountNumber = a.applicant_account_number
     WHERE g.accountNumber IS NULL OR g.accountNumber = '';

    /* ---------- 3. show every row we managed to update ---------- */
    SELECT
        'updated'  AS action,
        id,
        loanTrnId,
        accountNumber
    FROM gaurantors
    WHERE accountNumber IS NOT NULL
      AND accountNumber <> '';

    /* ---------- 4. capture and delete the orphans  ---------- */
    DROP TEMPORARY TABLE IF EXISTS _tmp_deleted;
    CREATE TEMPORARY TABLE _tmp_deleted
        (id INT, loanTrnId VARCHAR(60));

    INSERT INTO _tmp_deleted (id, loanTrnId)
    SELECT id, loanTrnId
    FROM   gaurantors
    WHERE  accountNumber IS NULL OR accountNumber = '';

    DELETE
    FROM   gaurantors
    WHERE  accountNumber IS NULL OR accountNumber = '';

    /* ---------- 5. show what we deleted ---------- */
    SELECT
        'deleted' AS action,
        id,
        loanTrnId
    FROM _tmp_deleted;

    COMMIT;                     -- atomic change set
    DROP TEMPORARY TABLE _tmp_deleted;
END$$
DELIMITER ;


CALL update_gaurantors_accountNumber_proc();


/* One-time build – MySQL 5.5.6 compatible */
CREATE TABLE gaurantors_archive (
    archive_id  INT(11) NOT NULL AUTO_INCREMENT,
    original_id INT(11) NOT NULL,                -- gaurantors.id

    gaurantorsName                VARCHAR(100),
    gaurantorsContact1            VARCHAR(100),
    gaurantorsContact2            VARCHAR(100),
    gaurantorsIdNumber            VARCHAR(100),
    gaurantorsRelationWithBorrower VARCHAR(100),
    gaurantorsHomeArea            VARCHAR(500),
    gaurantorsBusiness            VARCHAR(500),
    accountNumber                 VARCHAR(200) NOT NULL,
    loanTrnId                     VARCHAR(60),

    /* FIRST (and only) TIMESTAMP ⇒ DEFAULT CURRENT_TIMESTAMP works */
    archived_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (archive_id),
    UNIQUE KEY uq_original_id (original_id),
    INDEX      idx_loanTrnId    (loanTrnId)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8;



DROP PROCEDURE IF EXISTS archive_gaurantors_proc;
DELIMITER $$

CREATE PROCEDURE archive_gaurantors_proc()
BEGIN
    /* ———— safety net ———— */
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    /* -----------------------------------------------------------------
       1.  Move guarantors linked to CLOSED loans
           • in arch_new_loan_appstore
           • NOT present in new_loan_appstore
    ------------------------------------------------------------------ */
    DROP TEMPORARY TABLE IF EXISTS _tmp_to_archive;
    CREATE TEMPORARY TABLE _tmp_to_archive
          (PRIMARY KEY (id))          -- keeps the list we’ll delete
    SELECT  g.id,
            g.gaurantorsName,
            g.gaurantorsContact1,
            g.gaurantorsContact2,
            g.gaurantorsIdNumber,
            g.gaurantorsRelationWithBorrower,
            g.gaurantorsHomeArea,
            g.gaurantorsBusiness,
            g.accountNumber,
            g.loanTrnId
    FROM    gaurantors AS g
    /* closed loans … */
    JOIN    arch_new_loan_appstore  a  ON a.trn_id = g.loanTrnId
    /* … but NOT still running anywhere */
    LEFT JOIN new_loan_appstore     n  ON n.trn_id = g.loanTrnId
    WHERE   n.trn_id IS NULL;      -- NULL  ⇒  not a running loan

    /* -----------------------------------------------------------------
       2.  Insert them into the archive table (idempotent)
    ------------------------------------------------------------------ */
    INSERT IGNORE INTO gaurantors_archive
            (original_id,
             gaurantorsName, gaurantorsContact1, gaurantorsContact2,
             gaurantorsIdNumber, gaurantorsRelationWithBorrower,
             gaurantorsHomeArea, gaurantorsBusiness,
             accountNumber, loanTrnId)
    SELECT  id,
            gaurantorsName, gaurantorsContact1, gaurantorsContact2,
            gaurantorsIdNumber, gaurantorsRelationWithBorrower,
            gaurantorsHomeArea, gaurantorsBusiness,
            accountNumber, loanTrnId
    FROM _tmp_to_archive;

    /* -----------------------------------------------------------------
       3.  Delete them from the live table
    ------------------------------------------------------------------ */
    DELETE g
    FROM   gaurantors g
    JOIN   _tmp_to_archive t USING (id);

    /* -----------------------------------------------------------------
       4.  Return the rows we just archived  ➜  caller sees the effect
    ------------------------------------------------------------------ */
    SELECT
        'archived'  AS action,
        original_id AS id,
        loanTrnId,
        accountNumber,
        archived_at
    FROM   gaurantors_archive
    WHERE  original_id IN (SELECT id FROM _tmp_to_archive);

    COMMIT;
    DROP TEMPORARY TABLE _tmp_to_archive;
END$$
DELIMITER ;


CALL archive_gaurantors_proc();



/* =======================================================================
   NEW-client loans disbursed between two dates – now returns disbursed_date
   =======================================================================*/
DROP PROCEDURE IF EXISTS smsLoansDisbursedSummaryReport_NewByRange;
DELIMITER $$

CREATE PROCEDURE smsLoansDisbursedSummaryReport_NewByRange (
    IN  p_start DATE,      -- inclusive
    IN  p_end   DATE       -- inclusive
)
READS SQL DATA
BEGIN
    DECLARE vTotal DECIMAL(22,2);

    /* 1. scratch table ------------------------------------------------- */
    DROP TEMPORARY TABLE IF EXISTS tmp_loans_out;
    CREATE TEMPORARY TABLE tmp_loans_out (
        accountName       VARCHAR(100),
        phone             VARCHAR(50),
        businessName      VARCHAR(100),
        disbursedDate     DATE,
        officerName       VARCHAR(100),
        amountDisbursed   DECIMAL(22,2),
        guarantorName     VARCHAR(100),
        guarantorContact  VARCHAR(100),
        clientType        VARCHAR(10)
    );

    /* 2. populate ------------------------------------------------------ */
    INSERT INTO tmp_loans_out (accountName, phone, businessName, disbursedDate,
                               officerName, amountDisbursed, guarantorName,
                               guarantorContact, clientType)
    SELECT  lps.account_name,
            COALESCE(m.mobile1,''),
            COALESCE(bd.businessName,''),
            lps.trn_date,
            COALESCE(staffName(nls.gruop_id),''),
            CAST(lps.princimpal_amount AS DECIMAL(22,2)),
            COALESCE(g.gaurantorsName,''),
            COALESCE(g.gaurantorsContact1,''),
            'NEW'
    FROM    loanprocessingstore           AS lps
    LEFT JOIN new_loan_appstore           AS nls ON nls.loan_id   = CONCAT('newloan', lps.account_number)
    LEFT JOIN businessdetails             AS bd  ON bd.id         = nls.OtherGroups2
    LEFT JOIN gaurantors                  AS g   ON g.loanTrnId   = nls.trn_id
    LEFT JOIN pmms.account_created_store  AS acs ON acs.account_number = lps.account_number
    LEFT JOIN pmms.`master`               AS m   ON m.account_number   = lps.account_number
    WHERE   lps.trn_date BETWEEN p_start AND p_end
      AND   IFNULL(lps.loan_cycle_status,'') = 'Disbursed'
      AND   DATE(acs.creation_date) = DATE(lps.trn_date);      -- «NEW»

    /* 3. grand total ----------------------------------------------------*/
    SELECT IFNULL(SUM(amountDisbursed),0) INTO vTotal FROM tmp_loans_out;
    INSERT INTO tmp_loans_out
        VALUES ('Total','','',NULL,'',vTotal,'','','');

    /* 4. final result set ----------------------------------------------*/
    SELECT  accountName                       AS account_name,
            phone,
            businessName                      AS business_name,
            DATE_FORMAT(disbursedDate,'%Y-%m-%d') AS disbursed_date,
            FORMAT(amountDisbursed,0)         AS amount_disbursed,
            guarantorName                     AS guarantor_name,
            guarantorContact                  AS guarantor_contact,
            clientType                        AS client_type,
            officerName                       AS officer_name
    FROM    tmp_loans_out;
END$$
DELIMITER ;


-- If it exists from partial attempts
DROP TABLE IF EXISTS `archive_loan_files`;

CREATE TABLE `archive_loan_files` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_id` INT NOT NULL,                 -- logical link to arch_new_loan_appstore.trn_id (no FK)
  `file_path` VARCHAR(255) NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `upload_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_loan_id` (`loan_id`),          -- keeps lookups fast even without FK
  KEY `idx_upload_date` (`upload_date`)   -- handy for recent-file queries
) ENGINE=InnoDB DEFAULT CHARSET=latin1;






-- account_block
CREATE TABLE account_block (
  id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  account_number     VARCHAR(100) NOT NULL,
  reason_code        VARCHAR(40)  NOT NULL,   -- e.g. INACTIVE_6M, COMBINED_LOAN, AML_HOLD
  message            VARCHAR(255) NOT NULL,
  requires_approval  TINYINT(1)   NOT NULL DEFAULT 1,
  created_at         TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by         VARCHAR(50),
  expires_at         DATETIME NULL,
  cleared            TINYINT(1)   NOT NULL DEFAULT 0,
  cleared_at         DATETIME NULL,
  cleared_by         VARCHAR(50),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE INDEX idx_block_active
  ON account_block (account_number, cleared, reason_code);

-- account_approval_override
CREATE TABLE account_approval_override (
  id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  account_number  VARCHAR(100) NOT NULL,
  reason_code     VARCHAR(40)  NOT NULL,
  approved_by     VARCHAR(50)  NOT NULL,
  approved_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at      DATETIME NULL,
  notes           VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE INDEX idx_override
  ON account_approval_override (account_number, reason_code);

DROP PROCEDURE IF EXISTS ensure_account_block;

DELIMITER $$

CREATE PROCEDURE ensure_account_block(
  IN p_account_number VARCHAR(100),
  IN p_reason_code VARCHAR(40),
  IN p_message VARCHAR(255),
  IN p_created_by VARCHAR(50)
)
BEGIN
  DECLARE v_existing_id BIGINT UNSIGNED DEFAULT NULL;

  SELECT id
    INTO v_existing_id
    FROM account_block
   WHERE account_number = p_account_number
     AND reason_code = p_reason_code
     AND cleared = 0
     AND (expires_at IS NULL OR expires_at > NOW())
   ORDER BY id DESC
   LIMIT 1;

  IF v_existing_id IS NULL THEN
    INSERT INTO account_block(
      account_number, reason_code, message, requires_approval,
      created_at, created_by, expires_at,
      cleared, cleared_at, cleared_by
    ) VALUES (
      p_account_number, p_reason_code, p_message, 1,
      CURRENT_TIMESTAMP, p_created_by, NULL,
      0, NULL, NULL
    );
  END IF;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS block_account_combined_out;

DELIMITER $$

CREATE PROCEDURE block_account_combined_out(
  IN p_account_number VARCHAR(100),
  IN p_created_by VARCHAR(50)
)
BEGIN
  CALL ensure_account_block(
    p_account_number,
    'COMBINED_LOAN',
    'Account was combined out. Supervisor clearance required.',
    p_created_by
  );

  SELECT 1 AS theMessage;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS check_account_disbursement;

DELIMITER $$

CREATE PROCEDURE check_account_disbursement(
  IN p_account_number VARCHAR(100),
  IN p_as_of_date DATE,
  OUT o_allowed INT,
  OUT o_reason_code VARCHAR(40),
  OUT o_message VARCHAR(255)
)
check_account_disbursement: BEGIN
  DECLARE v_block_id BIGINT UNSIGNED DEFAULT NULL;
  DECLARE v_block_reason VARCHAR(40) DEFAULT NULL;
  DECLARE v_block_message VARCHAR(255) DEFAULT NULL;
  DECLARE v_override_id BIGINT UNSIGNED DEFAULT NULL;
  DECLARE v_last_completed DATE DEFAULT NULL;
  DECLARE v_days INT DEFAULT 0;
  DECLARE v_active_count INT DEFAULT 0;

  SET o_allowed = 1;
  SET o_reason_code = '';
  SET o_message = '';

  IF p_account_number IS NULL OR TRIM(p_account_number) = '' THEN
    SET o_allowed = 0;
    SET o_reason_code = 'INVALID_ACCOUNT';
    SET o_message = 'Invalid account number.';
    LEAVE check_account_disbursement;
  END IF;

  SELECT id, reason_code, message
    INTO v_block_id, v_block_reason, v_block_message
    FROM account_block
   WHERE account_number = p_account_number
     AND cleared = 0
     AND requires_approval = 1
     AND (expires_at IS NULL OR expires_at > p_as_of_date)
   ORDER BY id DESC
   LIMIT 1;

  IF v_block_id IS NOT NULL THEN
    SELECT id
      INTO v_override_id
      FROM account_approval_override
     WHERE account_number = p_account_number
       AND reason_code = v_block_reason
       AND (expires_at IS NULL OR expires_at > p_as_of_date)
     ORDER BY id DESC
     LIMIT 1;

    IF v_override_id IS NULL THEN
      SET o_allowed = 0;
      SET o_reason_code = v_block_reason;
      SET o_message = v_block_message;
      LEAVE check_account_disbursement;
    END IF;
  END IF;

  SELECT COUNT(*)
    INTO v_active_count
    FROM new_loan_appstore
   WHERE applicant_account_number = p_account_number
     AND loan_cycle_status IN ('Disbursed','Renewed','Combined');

  IF v_active_count > 0 THEN
    SET o_allowed = 1;
    LEAVE check_account_disbursement;
  END IF;

  SELECT MAX(trn_date)
    INTO v_last_completed
    FROM (
      SELECT trn_date
        FROM new_loan_appstore
       WHERE applicant_account_number = p_account_number
         AND loan_cycle_status = 'Completed'
      UNION ALL
      SELECT trn_date
        FROM arch_new_loan_appstore
       WHERE applicant_account_number = p_account_number
         AND loan_cycle_status = 'Completed'
    ) t;

  IF v_last_completed IS NULL THEN
    SET o_allowed = 1;
    LEAVE check_account_disbursement;
  END IF;

  SET v_days = DATEDIFF(p_as_of_date, v_last_completed);

  IF v_days >= 90 THEN
    SELECT id
      INTO v_override_id
      FROM account_approval_override
     WHERE account_number = p_account_number
       AND reason_code = 'INACTIVE_3M'
       AND (expires_at IS NULL OR expires_at > p_as_of_date)
     ORDER BY id DESC
     LIMIT 1;

    IF v_override_id IS NULL THEN
      SET o_allowed = 0;
      SET o_reason_code = 'INACTIVE_3M';
      SET o_message = CONCAT('Account inactive for ', v_days, ' days since last completed loan on ', DATE_FORMAT(v_last_completed,'%Y-%m-%d'), '. Supervisor clearance required.');
      CALL ensure_account_block(p_account_number, 'INACTIVE_3M', o_message, 'SYSTEM');
      LEAVE check_account_disbursement;
    END IF;
  END IF;

  SET o_allowed = 1;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS check_account_disbursement_rs;

DELIMITER $$

CREATE PROCEDURE check_account_disbursement_rs(
  IN p_account_number VARCHAR(100),
  IN p_as_of_date DATE
)
BEGIN
  DECLARE v_allowed INT DEFAULT 1;
  DECLARE v_reason_code VARCHAR(40) DEFAULT '';
  DECLARE v_message VARCHAR(255) DEFAULT '';

  CALL check_account_disbursement(p_account_number, p_as_of_date, v_allowed, v_reason_code, v_message);
  SELECT v_allowed AS allowed, v_reason_code AS reason_code, v_message AS message;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS approve_account_block;

DELIMITER $$

CREATE PROCEDURE approve_account_block(
  IN p_account_number VARCHAR(100),
  IN p_reason_code VARCHAR(40),
  IN p_approved_by VARCHAR(50),
  IN p_notes VARCHAR(255)
)
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 0 AS theMessage;
  END;

  START TRANSACTION;

  INSERT INTO account_approval_override(account_number, reason_code, approved_by, expires_at, notes)
  VALUES(p_account_number, p_reason_code, p_approved_by, NULL, p_notes);

  UPDATE account_block
     SET cleared = 1,
         cleared_at = CURRENT_TIMESTAMP,
         cleared_by = p_approved_by
   WHERE account_number = p_account_number
     AND reason_code = p_reason_code
     AND cleared = 0;

  COMMIT;
  SELECT 1 AS theMessage;
END $$

DELIMITER ;


DROP PROCEDURE IF EXISTS clear_account_block;

DELIMITER $$

CREATE PROCEDURE clear_account_block(
  IN p_account_number VARCHAR(100),
  IN p_reason_code VARCHAR(40),
  IN p_cleared_by VARCHAR(50),
  IN p_notes VARCHAR(255)
)
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 0 AS theMessage;
  END;

  START TRANSACTION;

  INSERT INTO account_approval_override(account_number, reason_code, approved_by, expires_at, notes)
  VALUES(p_account_number, p_reason_code, p_cleared_by, DATE_ADD(NOW(), INTERVAL 1 DAY), p_notes);

  UPDATE account_block
     SET cleared = 1,
         cleared_at = CURRENT_TIMESTAMP,
         cleared_by = p_cleared_by
   WHERE account_number = p_account_number
     AND reason_code = p_reason_code
     AND cleared = 0;

  COMMIT;
  SELECT 1 AS theMessage;
END $$

DELIMITER ;


ALTER TABLE pmms_loans.new_loan_appstore
  ADD INDEX idx_loan_acct_status (applicant_account_number, loan_cycle_status);

ALTER TABLE pmms_loans.new_loan_appstore
  ADD INDEX idx_loan_acct_trndate (applicant_account_number, trn_date);

ALTER TABLE pmms_loans.new_loan_appstore
  ADD INDEX idx_loan_status_acct (loan_cycle_status, applicant_account_number);

ALTER TABLE pmms_loans.arch_new_loan_appstore
  ADD INDEX idx_arch_loan_status_acct (loan_cycle_status, applicant_account_number);


-- PMMS

--    DROP DATABASE PMMS;
-- CREATE DATABASE PMMS;
-- \q
-- mysql -u root -p pmms<aacheckingNow.sql
-- PRINCE?;=2020
-- mysql -u root -p
-- PRINCE?;=2020
-- USE pmms; 

ALTER TABLE pmms_loans.new_loan_appstore
  ADD INDEX idx_loan_status_acct (loan_cycle_status, applicant_account_number);

  ALTER TABLE pmms_loans.arch_new_loan_appstore
  ADD INDEX idx_arch_loan_status_acct (loan_cycle_status, applicant_account_number);

  CREATE INDEX idx_lds_acc_trn ON loandisburserepaystatement (AccountNumber, trnId);
CREATE INDEX idx_arch_lds_acc_trn ON arch_loandisburserepaystatement (AccountNumber, trnId);


DROP PROCEDURE IF EXISTS runningLoansDetails;
DELIMITER ##

CREATE PROCEDURE runningLoansDetails() BEGIN


DROP TABLE IF EXISTS runningLoanAnalysis;

CREATE TEMPORARY TABLE  runningLoanAnalysis(id INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_account VARCHAR(60), PRIMARY KEY (`id`), UNIQUE KEY uk_runningLoanAnalysis_customer_account (customer_account))ENGINE = MEMORY
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


  INSERT IGNORE INTO runningLoanAnalysis (customer_account)
  SELECT applicant_account_number
  FROM pmms_loans.new_loan_appstore FORCE INDEX (idx_loan_status_acct)
  WHERE loan_cycle_status IN ('Disbursed','Renewed','Combined','Completed')
    AND applicant_account_number IS NOT NULL AND applicant_account_number <> ''
  GROUP BY loan_cycle_status, applicant_account_number;

  INSERT IGNORE INTO runningLoanAnalysis (customer_account)
  SELECT applicant_account_number
  FROM pmms_loans.arch_new_loan_appstore FORCE INDEX (idx_arch_loan_status_acct)
  WHERE loan_cycle_status IN ('Disbursed','Renewed','Combined','Completed')
    AND applicant_account_number IS NOT NULL AND applicant_account_number <> ''
  GROUP BY loan_cycle_status, applicant_account_number;

  SELECT
    id,
    customer_account
  FROM runningLoanAnalysis;
 



END ##
DELIMITER ;
CALL runningLoansDetails();




-- MobileMoneyDeposit
-- MobileMoneyWithdraw
-- CreateUnknownMobileMoney
-- LoanPytMobileMoney
-- LoanPytUnknownMobileMoney

-- RecoverOtherIncomes
  --  "SavingsWithdrawChargesCash"  
  --                    "SavingsWithdrawChargesBank"     
  --                "SavingsWithdrawChargesSavings"
  --                "StationaryAndPrintingCash"     
  --                  "StationaryAndPrintingBank"    
  --            "StationaryAndPrintingSavings"

-- OTHER CHANGES TO NOTE:
-- OtherGroups2=Business Id
-- OtherGroups3=Number of Renewals
-- OtherGroups4=BatchNumber
-- authoriser_id=DisbursedLoanId

-- Debits	Airtel Money(1221109)	01122000210	Bank Balance	2.43012E7

/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanPrintingDetailsRenewed;

DELIMITER ##

CREATE PROCEDURE   loanPrintingDetailsRenewed(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 
 DECLARE l_done,noOfRenewals,loanTrnIdL,disLoanTrnIdL INT;
 DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL,loanRenewedL,arrearsAmount,amountRenewed,date_takenL VARCHAR(60);
  DECLARE companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,theTxnTime VARCHAR(60);
DECLARE theTrn_date,dateLastRenewed DATE;

DROP TABLE IF EXISTS loanPrintDetailsRenew;

CREATE TEMPORARY  TABLE loanPrintDetailsRenew(
id_1 INTEGER,
company_name VARCHAR(60),
company_branch VARCHAR(60),
company_box_number VARCHAR(60),
customer_name VARCHAR(60),
staff_name VARCHAR(60),
loan_taken VARCHAR(60),
date_taken  VARCHAR(60),
loan_renewed  VARCHAR(60),
no_of_renewals  INT,
date_last_renewed DATE,
loans_paid VARCHAR(60),
loan_remaining VARCHAR(60),
amount_arrears VARCHAR(60),
batchNumber  VARCHAR(60),
loanID  VARCHAR(60),
trn_date VARCHAR(60),
trn_time VARCHAR(30),
LoanStatus VARCHAR(60),
princimpal_amount VARCHAR(60),
interest_amount VARCHAR(60),
office_number VARCHAR(60),
amount_renewed VARCHAR(60));



SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
INTO loanTrnIdL, AmountPaidL, AmountRemainL, accountNumberL, LoanStatus, theTrn_date
FROM (
    SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
    FROM pmms.loandisburserepaystatement
    WHERE BatchCode = batchNumber

    UNION ALL

    SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
    FROM pmms.arch_loandisburserepaystatement
    WHERE BatchCode = batchNumber
) AS combined_data;

SELECT trn_time INTO theTxnTime FROM pmms.general_ledger WHERE chq_number=batchNumber LIMIT 1;



SELECT authoriser_id, OtherGroups3, princimpal_amount
INTO disLoanTrnIdL, noOfRenewals, amountRenewed
FROM (
    SELECT authoriser_id, OtherGroups3, princimpal_amount
    FROM pmms_loans.new_loan_appstore
    WHERE trn_id = loanTrnIdL

    UNION ALL

    SELECT authoriser_id, OtherGroups3, princimpal_amount
    FROM pmms_loans.arch_new_loan_appstore
    WHERE trn_id = loanTrnIdL
) AS combined_data;


SELECT total_loanAmount, instalment_start_date
INTO loan_takenL, date_takenL
FROM (
    SELECT total_loanAmount, instalment_start_date
    FROM pmms_loans.new_loan_appstore
    WHERE trn_id = disLoanTrnIdL

    UNION ALL

    SELECT total_loanAmount, instalment_start_date
    FROM pmms_loans.arch_new_loan_appstore
    WHERE trn_id = disLoanTrnIdL
) AS combined_data;

SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;


SELECT ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
INTO loanRenewedL, dateLastRenewed, princimpalL, interestL
FROM (
    SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
    FROM loandisburserepaystatement
    WHERE loanTrnId = loanTrnIdL

    UNION ALL

    SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
    FROM arch_loandisburserepaystatement
    WHERE loanTrnId = loanTrnIdL
) AS combined_data
ORDER BY TrnId ASC
LIMIT 1;



select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;

SET arrearsAmount=AmountRemainL;

IF ISNULL(arrearsAmount) THEN
SET arrearsAmount=0;
END IF;

IF ISNULL(companyBranch) THEN
SET companyBranch="-";
END IF;

IF ISNULL(companyBranch) THEN
SET companyBranch="-";
END IF;

IF ISNULL(companyBoxNumber) THEN
SET companyBoxNumber="-";
END IF;

IF ISNULL(accountNumberL) THEN
SET accountNumberL="-";
END IF;

  IF ISNULL(staffId) THEN
SET staffId=10001;
END IF;

  IF ISNULL(date_takenL)  THEN
SET date_takenL=DATE_FORMAT(DATE(NOW()),'%d/%m/%Y');
END IF;

  IF ISNULL(loanRenewedL) THEN
SET loanRenewedL=0;
END IF;

  IF ISNULL(noOfRenewals) THEN
SET noOfRenewals=0;
END IF;

  IF ISNULL(dateLastRenewed) THEN
SET dateLastRenewed=DATE_FORMAT(DATE(NOW()),'%d/%m/%Y');
END IF;

  IF ISNULL(loan_takenL) THEN
SET loan_takenL=loanRenewedL;
END IF;


INSERT INTO loanPrintDetailsRenew VALUES (
  l_done,
  companyName,
  companyBranch,
  companyBoxNumber,
  customerNameL(accountNumberL),
  staffName(staffId),
  FORMAT(loan_takenL,0),
  date_takenL,
  FORMAT(loanRenewedL,0),
     noOfRenewals,
    dateLastRenewed,
  FORMAT(AmountPaidL,0),
  FORMAT(AmountRemainL,0),
    FORMAT(arrearsAmount,0),
  batchNumber,
  loanTrnIdL,
  DATE_FORMAT(theTrn_date,'%d/%m/%Y'),
  theTxnTime,
  LoanStatus,
  FORMAT(princimpalL,0),
  FORMAT(interestL,0),
  officeNumber,
    FORMAT(amountRenewed,0)
  );


   SELECT * FROM loanPrintDetailsRenew;

END

##
DELIMITER ;

CALL loanPrintingDetailsRenewed('BTN70877',10000)\G 









-- /* LOAN RECEIPT PRINTING */

-- DROP PROCEDURE IF EXISTS loanPrintingDetails;

-- DELIMITER ##

-- CREATE PROCEDURE   loanPrintingDetails(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
-- BEGIN
-- DECLARE l_done,noOfRenewals,loanTrnIdL,disLoanTrnIdL INT;
--  DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL,loanRenewedL,arrearsAmount,amountRenewed,date_takenL VARCHAR(60);
--   DECLARE companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,theTxnTime VARCHAR(60);
-- DECLARE theTrn_date,dateLastRenewed DATE;

-- DROP TABLE IF EXISTS loanPrintDetailsRenew;

-- CREATE TEMPORARY  TABLE loanPrintDetailsRenew(
-- id_1 INTEGER,
-- company_name VARCHAR(60),
-- company_branch VARCHAR(60),
-- company_box_number VARCHAR(60),
-- customer_name VARCHAR(60),
-- staff_name VARCHAR(60),
-- loan_taken VARCHAR(60),
-- date_taken  VARCHAR(60),
-- loan_renewed  VARCHAR(60),
-- no_of_renewals  INT,
-- date_last_renewed DATE,
-- loans_paid VARCHAR(60),
-- loan_remaining VARCHAR(60),
-- amount_arrears VARCHAR(60),
-- batchNumber  VARCHAR(60),
-- loanID  VARCHAR(60),
-- trn_date VARCHAR(60),
-- trn_time VARCHAR(30),
-- LoanStatus VARCHAR(60),
-- princimpal_amount VARCHAR(60),
-- interest_amount VARCHAR(60),
-- office_number VARCHAR(60),
-- amount_renewed VARCHAR(60));










-- SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
-- INTO loanTrnIdL, AmountPaidL, AmountRemainL, accountNumberL, LoanStatus, theTrn_date
-- FROM (
--     SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
--     FROM pmms.loandisburserepaystatement
--     WHERE BatchCode = batchNumber

--     UNION ALL

--     SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
--     FROM pmms.arch_loandisburserepaystatement
--     WHERE BatchCode = batchNumber
-- ) AS combined_data;

-- SELECT trn_time INTO theTxnTime FROM pmms.general_ledger WHERE chq_number=batchNumber LIMIT 1;



-- SELECT authoriser_id, OtherGroups3, princimpal_amount
-- INTO disLoanTrnIdL, noOfRenewals, amountRenewed
-- FROM (
--     SELECT authoriser_id, OtherGroups3, princimpal_amount
--     FROM pmms_loans.new_loan_appstore
--     WHERE trn_id = loanTrnIdL

--     UNION ALL

--     SELECT authoriser_id, OtherGroups3, princimpal_amount
--     FROM pmms_loans.arch_new_loan_appstore
--     WHERE trn_id = loanTrnIdL
-- ) AS combined_data;


-- SELECT total_loanAmount, instalment_start_date
-- INTO loan_takenL, date_takenL
-- FROM (
--     SELECT total_loanAmount, instalment_start_date
--     FROM pmms_loans.new_loan_appstore
--     WHERE trn_id = disLoanTrnIdL

--     UNION ALL

--     SELECT total_loanAmount, instalment_start_date
--     FROM pmms_loans.arch_new_loan_appstore
--     WHERE trn_id = disLoanTrnIdL
-- ) AS combined_data;

-- SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;


-- SELECT ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
-- INTO loanRenewedL, dateLastRenewed, princimpalL, interestL
-- FROM (
--     SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
--     FROM loandisburserepaystatement
--     WHERE loanTrnId = loanTrnIdL

--     UNION ALL

--     SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
--     FROM arch_loandisburserepaystatement
--     WHERE loanTrnId = loanTrnIdL
-- ) AS combined_data
-- ORDER BY TrnId ASC
-- LIMIT 1;



-- select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;

-- SET arrearsAmount=AmountRemainL;

-- IF ISNULL(arrearsAmount) THEN
-- SET arrearsAmount=0;
-- END IF;

-- IF ISNULL(companyBranch) THEN
-- SET companyBranch="-";
-- END IF;

-- IF ISNULL(companyBranch) THEN
-- SET companyBranch="-";
-- END IF;

-- IF ISNULL(companyBoxNumber) THEN
-- SET companyBoxNumber="-";
-- END IF;

-- IF ISNULL(accountNumberL) THEN
-- SET accountNumberL="-";
-- END IF;

--   IF ISNULL(staffId) THEN
-- SET staffId=10001;
-- END IF;

--   IF ISNULL(date_takenL)  THEN
-- SET date_takenL=DATE_FORMAT(DATE(NOW()),'%d/%m/%Y');
-- END IF;

--   IF ISNULL(loanRenewedL) THEN
-- SET loanRenewedL=0;
-- END IF;

--   IF ISNULL(noOfRenewals) THEN
-- SET noOfRenewals=0;
-- END IF;

--   IF ISNULL(dateLastRenewed) THEN
-- SET dateLastRenewed=DATE_FORMAT(DATE(NOW()),'%d/%m/%Y');
-- END IF;

--   IF ISNULL(loan_takenL) THEN
-- SET loan_takenL=loanRenewedL;
-- END IF;


-- INSERT INTO loanPrintDetailsRenew VALUES (
--   l_done,
--   companyName,
--   companyBranch,
--   companyBoxNumber,
--   customerNameL(accountNumberL),
--   staffName(staffId),
--   FORMAT(loan_takenL,0),
--   date_takenL,
--   FORMAT(loanRenewedL,0),
--      noOfRenewals,
--     dateLastRenewed,
--   FORMAT(AmountPaidL,0),
--   FORMAT(AmountRemainL,0),
--     FORMAT(arrearsAmount,0),
--   batchNumber,
--   loanTrnIdL,
--   DATE_FORMAT(theTrn_date,'%d/%m/%Y'),
--   theTxnTime,
--   LoanStatus,
--   FORMAT(princimpalL,0),
--   FORMAT(interestL,0),
--   officeNumber,
--     FORMAT(amountRenewed,0)
--   );


--    SELECT * FROM loanPrintDetailsRenew;



-- END  ##

--  DELIMITER ;




/* LOAN RECEIPT PRINTING */

DROP PROCEDURE IF EXISTS loanPrintingDetails;

DELIMITER ##

CREATE PROCEDURE   loanPrintingDetails(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 DECLARE l_done INT;
 DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL DOUBLE;
  DECLARE loanTrnIdL,companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,arrearsAmount VARCHAR(60);
DECLARE date_takenL,theTrn_date DATE;

DROP TABLE IF EXISTS loanPrintDetails;

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
office_number VARCHAR(60),-- 17
amount_arrears VARCHAR(60));-- 18

SELECT loanTrnId,AmountPaid,LoanBalance,AccountNumber,LoanStatusReport,TrnDate  INTO loanTrnIdL,AmountPaidL,AmountRemainL,accountNumberL ,LoanStatus ,theTrn_date FROM   loandisburserepaystatement WHERE BatchCode=batchNumber;
SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
INTO loanTrnIdL, AmountPaidL, AmountRemainL, accountNumberL, LoanStatus, theTrn_date
FROM (
    SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
    FROM pmms.loandisburserepaystatement
    WHERE BatchCode = batchNumber

    UNION ALL

    SELECT loanTrnId, AmountPaid, LoanBalance, AccountNumber, LoanStatusReport, TrnDate
    FROM pmms.arch_loandisburserepaystatement
    WHERE BatchCode = batchNumber
) AS combined_data;

-- SELECT trn_time INTO theTxnTime FROM pmms.general_ledger WHERE chq_number=batchNumber LIMIT 1;

SELECT SUM(InstalmentRemaining) INTO arrearsAmount FROM pmms_loans.new_loan_appstoreamort WHERE master1_id=loanTrnIdL AND NOT instalment_status='P' AND instalment_due_date<=DATE(NOW());

SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

SELECT ExpectedTotalAmount,TrnDate,AmountDisbursed,ExpectedInterest INTO loan_takenL,date_takenL,princimpalL,interestL  FROM loandisburserepaystatement WHERE loanTrnId=loanTrnIdL ORDER BY TrnId ASC LIMIT 1;

SELECT ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
INTO loan_takenL,date_takenL,princimpalL,interestL 
FROM (
    SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
    FROM loandisburserepaystatement
    WHERE loanTrnId = loanTrnIdL

    UNION ALL

    SELECT TrnId, ExpectedTotalAmount, TrnDate, AmountDisbursed, ExpectedInterest
    FROM arch_loandisburserepaystatement
    WHERE loanTrnId = loanTrnIdL
) AS combined_data
ORDER BY TrnId ASC
LIMIT 1;


select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;
-- select AmountRemainL;

IF ISNULL(arrearsAmount) THEN
SET arrearsAmount=0;
END IF;

INSERT INTO loanPrintDetails VALUES (l_done,companyName,companyBranch,companyBoxNumber,customerNameL(accountNumberL),staffName(staffId),FORMAT(loan_takenL,0),date_takenL,FORMAT(AmountPaidL,0),FORMAT(AmountRemainL,0),batchNumber,loanTrnIdL,DATE_FORMAT(theTrn_date,'%d/%m/%Y'),TIME(NOW()),LoanStatus,FORMAT(princimpalL,0),FORMAT(interestL,0),officeNumber,FORMAT(arrearsAmount,0));


   SELECT * FROM loanPrintDetails;

END ##
DELIMITER ;



 
DROP PROCEDURE IF EXISTS updatingBalances;
DELIMITER //
CREATE PROCEDURE updatingBalances() READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,periodSet,theRateTypeUsed,l_done,loanId INT;
DECLARE theDealineMeeasure VARCHAR(30);
   
DECLARE forloanId CURSOR FOR SELECT account_number   FROM pmms.account_created_store where account_l5= 'Customer Deposits';
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



DROP TABLE IF EXISTS theRenewalLoanDetailsN;

CREATE TEMPORARY  TABLE theRenewalLoanDetailsN(
  accountNumberN VARCHAR(60),
  officerIdN INT,
  balanceDueN DOUBLE,
  interestRateUsed DOUBLE,
  trnDateUsed DATE,
  initialDisDateUsed DATE,
  tenureUsed INT,
   periodUsed INT,
  theBusinessId INT,
  theNumberOfRenewals INT,
   theDidId INT,
   theRNateTypeUsed  INT
  );


 
 OPEN forloanId;
SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals,theRateTypeUsed FROM 
autoRenewalSettings;
loanId_loop: LOOP 

 SET loanId=NULL, @accountNumber=NULL,@loansOfficer=NULL, @balanceDueN=NULL,@trnDate=NULL,@initialDisDate=NULL,@loanTenure=NULL,@theBuzId=NULL,@numberRenewals=NULL,@previousId =NULL ;
 FETCH forloanId into loanId;
--  SELECT loanId;
 
 IF l_done=1 THEN

LEAVE loanId_loop;

 END IF;
 
SET @dueDateX = concat(CAST("SELECT applicant_account_number,gruop_id, balance_due,trn_date,instalment_start_date,loan_tenure,OtherGroups2,OtherGroups3,authoriser_id INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId  FROM new_loan_appstore WHERE DATE(NOW())>=" AS CHAR CHARACTER SET utf8),CAST("(SELECT DATE_ADD(instalment_end_date, INTERVAL " AS CHAR CHARACTER SET utf8),theDeadline, CAST(" " AS CHAR CHARACTER SET utf8),theDeadilineReturn(theDealineMeeasure),CAST(" ) FROM new_loan_appstore WHERE trn_id=" AS CHAR CHARACTER SET utf8),loanId,CAST(")  AND OtherGroups3<" AS CHAR CHARACTER SET utf8),numberOfRenewals,CAST(" AND trn_id=" AS CHAR CHARACTER SET utf8),loanId);
  -- select  @dueDateX;
  PREPARE stmt2 FROM @dueDateX;
  EXECUTE stmt2;
DROP PREPARE stmt2;
-- SELECT "ssssssssssssssssss",@accountNumber;
IF NOT ISNULL(@accountNumber) THEN
INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed);
END IF;


-- 06/04/2022	06/04/2022	Centenary Banks Bank Deposit Processed on 06/04/2022
--   From Cash At Hand	-	3295205.0	Cash At Hand

    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;

SELECT * FROM theRenewalLoanDetailsN;

END//

 DELIMITER ;

UPDATE loandisburserepaystatement SET  LoanBalance=ExpectedTotalAmount WHERE ExpectedTotalAmount>0;



-- DROP TABLE backupEmailRecepients;
-- -----------------------------------------------------
-- Table backupEmailRecepients
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS backupEmailRecepients (

 id INT NOT NULL AUTO_INCREMENT,
 
  enabled  INT (11) NOT NULL DEFAULT 1,
  
   name varchar(300) NOT NULL DEFAULT '0.0',

     email varchar(50) NOT NULL DEFAULT '0.0',

  PRIMARY KEY (id)
  
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8;








DROP PROCEDURE IF EXISTS TransactedAccountsNumbers;
DELIMITER //
CREATE PROCEDURE TransactedAccountsNumbers() READS SQL DATA 
BEGIN
DECLARE l_done INT;
DECLARE accountNumber VARCHAR(200);
   
DECLARE forAccounts CURSOR FOR SELECT account_number   FROM pmms.account_created_store where account_l2= 'Expenses' OR account_l5= 'Accounts Payable' OR account_l4='Accounts Receivables/Debtors' OR account_l4='Cash And Bank Balance' OR account_l4='Equity';
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;



DROP TABLE IF EXISTS theAccounts;

CREATE TEMPORARY  TABLE theAccounts(
  accountNumberN VARCHAR(200),
  accountCategory5  VARCHAR(200),
  accountCategory2  VARCHAR(200)
  );


 
 OPEN forAccounts;

account_loop: LOOP 


 FETCH forAccounts into accountNumber;

 
 IF l_done=1 THEN

LEAVE account_loop;

 END IF;
--  WHERE trn_date>=DATE_SUB(DATE(NOW()), INTERVAL (DAYOFMONTH(DATE(NOW()))-1) DAY) AND trn_date<=DATE(NOW())
SET @dueDateX = concat(CAST("SELECT COUNT(trn_id) INTO @itemsExist  FROM bsanca" AS CHAR CHARACTER SET utf8),accountNumber,CAST(" WHERE trn_date=DATE(NOW())" AS CHAR CHARACTER SET utf8));
  -- select  @dueDateX;
  PREPARE stmt2 FROM @dueDateX;
  EXECUTE stmt2;
DROP PREPARE stmt2;

IF  @itemsExist>0 THEN
INSERT INTO theAccounts SELECT  account_number,account_l5,account_l2 FROM pmms.account_created_store WHERE account_number= accountNumber;
END IF;



    SET l_done=0;
 END LOOP account_loop;
 CLOSE forAccounts;

SELECT * FROM theAccounts;

END//

 DELIMITER ;



DROP PROCEDURE IF EXISTS normaliseBalance;
DELIMITER //
CREATE PROCEDURE normaliseBalance() READS SQL DATA 
OUTER_BLOCK: BEGIN
DECLARE theLoanTxnId VARCHAR(20);
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
DECLARE forLoanTxnId CURSOR FOR SELECT DISTINCT(loanTrnId) from loandisburserepaystatement WHERE  LoanStatusReport='Disbursed' OR LoanStatusReport='Renewed';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 





INNER_BLOCK: BEGIN

DECLARE theBatchNoS VARCHAR(60);
DECLARE theOpeningBal,theBal,thePaid,InterestPaid,principalPaid,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal DOUBLE; 
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos CURSOR FOR SELECT BatchCode FROM loandisburserepaystatement WHERE loanTrnId=theLoanTxnId LIMIT 1;
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


SELECT  debit INTO thePaid FROM bsanca01122000110  WHERE chq_number=theBatchNoS;

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
END OUTER_BLOCK //


 DELIMITER ;


-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS normaliseIndividualBalanceBank;

-- Change the delimiter to avoid conflicts with semicolon in the procedure
DELIMITER $$

-- Create a new procedure
CREATE PROCEDURE normaliseIndividualBalanceBank(IN trnIdInd INT)
READS SQL DATA
BEGIN
    -- Declaration of variables
    DECLARE theBatchNoS VARCHAR(60);
    DECLARE theOpeningBal, theBal, thePaid, InterestPaid, principalPaid, 
            AccumInterestPaid, PenaltyPaid, priBal, intBal, accumIntBal, 
            loanPenBal, loanBal DOUBLE; 
    DECLARE innerNotFound INTEGER DEFAULT 0; 

    -- Cursor for selecting BatchCode from a table based on the input transaction ID
    DECLARE forBatchNos CURSOR FOR 
        SELECT BatchCode 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = trnIdInd ;

    -- Continue handler for cursor not finding a row
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound = 1;

    -- Open the cursor
    OPEN forBatchNos; 

    -- Initialize local variables
    SELECT COUNT(ExpectedTotalAmount) INTO @No 
    FROM loandisburserepaystatement 
    WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

    -- Check if there is exactly one row with ExpectedTotalAmount > 0
    IF @No = 1 THEN
        SELECT ExpectedTotalAmount INTO theOpeningBal 
        FROM loandisburserepaystatement 
        WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

        -- Loop for processing batch numbers
        TXNIDS_LOOP: LOOP
            FETCH forBatchNos INTO theBatchNoS;
            SET thePaid = NULL, InterestPaid = NULL, principalPaid = NULL;
SELECT  theBatchNoS;
            -- Exit the loop if no more rows found
            IF innerNotFound = 1 THEN
                LEAVE TXNIDS_LOOP;
            END IF;

            -- Fetch payments, interest and principal from different accounts
            SELECT debit INTO thePaid 
            FROM bsanca01122000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO InterestPaid 
            FROM bsanca03301000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO principalPaid 
            FROM bsanca01128000110 
            WHERE chq_number = theBatchNoS;

            -- Check and set default values for null or unset variables
            IF thePaid = '-' OR ISNULL(thePaid) THEN
                SET thePaid = 0.0;
            END IF;

            IF InterestPaid = '-' OR ISNULL(InterestPaid) THEN
                SET InterestPaid = 0.0;
            END IF;

            IF principalPaid = '-' OR ISNULL(principalPaid) THEN
                SET principalPaid = 0.0;
            END IF;


                   IF thePaid>0 THEN 
            -- Update the statement with calculated values
            UPDATE loandisburserepaystatement 
            SET AmountPaid = thePaid, PrincipalPaid = principalPaid, 
                InterestPaid = InterestPaid, LoanBalance = theOpeningBal - thePaid 
            WHERE BatchCode = theBatchNoS;

            -- Adjust the opening balance for the next iteration
            SET theOpeningBal = theOpeningBal - thePaid;
END IF;

            -- Reset the not found handler flag
            SET innerNotFound = 0;
        END LOOP TXNIDS_LOOP; 
    END IF;

    -- Close the cursor
    CLOSE forBatchNos; 

END $$

-- Reset the delimiter to its default value
DELIMITER ;



        DROP PROCEDURE IF EXISTS reverseTxnsX;
        DELIMITER //
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
SET @trn_id = (SELECT trn_id FROM pmms_loans.loanprocessingstore WHERE account_number=the_contra_account_no ORDER BY trn_id DESC LIMIT 1);
DELETE FROM pmms_loans.loanprocessingstore WHERE trn_id = @trn_id;


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

DROP PROCEDURE IF EXISTS reverseTxnBackDated;
        DELIMITER //
        CREATE PROCEDURE  reverseTxnBackDated(IN batchNumber VARCHAR(100) )  proc: BEGIN

     DECLARE l_done INT DEFAULT 0;
     DECLARE txnId,loanTrnIdL INT ;
     DECLARE txnDate,txnValueDate DATE;
     DECLARE the_narration VARCHAR(500);

     DECLARE the_debit,the_credit,the_account_no,the_contra_account_no,the_trn_type,DrCr,txnAmount  VARCHAR(100);
     DECLARE v_account_number VARCHAR(100);
     DECLARE v_batch_trn_date DATE;
     DECLARE v_batch_amount_disbursed VARCHAR(100);
     DECLARE v_batch_expected_total VARCHAR(100);
     DECLARE v_deleted_rows INT DEFAULT 0;
     DECLARE v_is_loan_batch INT DEFAULT 0;
     DECLARE v_loan_row_count INT DEFAULT 0;

     DECLARE fortheTxnId CURSOR FOR SELECT trn_id FROM general_ledger WHERE chq_number=batchNumber AND trn_type<>'Reversal' AND narration NOT LIKE 'REVERSAL OF %' ORDER BY trn_id DESC;
     DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

     DECLARE EXIT HANDLER FOR SQLEXCEPTION
     BEGIN
       ROLLBACK;
       SELECT 'OTHER_ER' AS theMessage, 'SQL exception occurred' AS MESSAGE;
     END;

     START TRANSACTION;

     IF EXISTS(SELECT 1 FROM general_ledger WHERE chq_number=batchNumber AND tra_ref_number='0002' AND narration LIKE 'REVERSAL OF %' LIMIT 1) THEN
       ROLLBACK;
       SELECT 'ALREADY_REVERSED' AS theMessage, 'Batch already reversed' AS MESSAGE;
       LEAVE proc;
     END IF;

     IF NOT EXISTS(SELECT 1 FROM general_ledger WHERE chq_number=batchNumber AND trn_type<>'Reversal' AND narration NOT LIKE 'REVERSAL OF %' LIMIT 1) THEN
       ROLLBACK;
       SELECT 'NOT_FOUND' AS theMessage, 'Batch not found in general_ledger' AS MESSAGE;
       LEAVE proc;
     END IF;

     SELECT COUNT(*) INTO v_loan_row_count FROM loandisburserepaystatement WHERE BatchCode=batchNumber;
     IF v_loan_row_count > 0 THEN
       SET v_is_loan_batch=1;
     END IF;

     IF v_is_loan_batch=1 THEN

       SELECT loanTrnId,AccountNumber,TrnDate,AmountDisbursed,ExpectedTotalAmount
       INTO loanTrnIdL,v_account_number,v_batch_trn_date,v_batch_amount_disbursed,v_batch_expected_total
       FROM loandisburserepaystatement WHERE BatchCode=batchNumber ORDER BY TrnDate ASC,TrnId ASC LIMIT 1;

       IF loanTrnIdL IS NULL THEN
         ROLLBACK;
         SELECT 'NOT_FOUND' AS theMessage, 'Batch not found in loan statement' AS MESSAGE;
         LEAVE proc;
       END IF;

       IF (IFNULL(CAST(v_batch_amount_disbursed AS DECIMAL(20,2)),0) > 0 OR IFNULL(CAST(v_batch_expected_total AS DECIMAL(20,2)),0) > 0) THEN
         ROLLBACK;
         SELECT 'NOT_ALLOWED' AS theMessage, 'Backdated reversal is not allowed for loan disbursement batches' AS MESSAGE;
         LEAVE proc;
       END IF;

       IF EXISTS(SELECT 1 FROM pmms_loans.arch_new_loan_appstore WHERE trn_id=loanTrnIdL AND loan_cycle_status='Completed' LIMIT 1)
          OR EXISTS(SELECT 1 FROM pmms_loans.arch_new_loan_appstore1 WHERE trn_id=loanTrnIdL AND loan_cycle_status='Completed' LIMIT 1)
          OR EXISTS(SELECT 1 FROM pmms_loans.new_loan_appstore WHERE trn_id=loanTrnIdL AND loan_cycle_status='Completed' LIMIT 1)
          OR EXISTS(SELECT 1 FROM pmms_loans.new_loan_appstore1 WHERE trn_id=loanTrnIdL AND loan_cycle_status='Completed' LIMIT 1) THEN
         ROLLBACK;
         SELECT 'LOAN_ARCHIVED' AS theMessage, 'Cannot reverse transactions for archived/completed loans' AS MESSAGE;
         LEAVE proc;
       END IF;

       DELETE FROM loandisburserepaystatement WHERE BatchCode=batchNumber;
       SET v_deleted_rows = ROW_COUNT();

     END IF;

     OPEN fortheTxnId;
     txnId_loop: LOOP

       FETCH fortheTxnId into txnId;

       IF l_done=1 THEN
         LEAVE txnId_loop;
       END IF;

       SELECT trn_date,value_date, narration,debit,credit,debit_account_no,credit_account_no,trn_type
       INTO txnDate,txnValueDate, the_narration,the_debit,the_credit,the_account_no,the_contra_account_no,the_trn_type
       FROM general_ledger WHERE trn_id=txnId;

       IF the_debit='-' THEN
         SET DrCr='Dr',txnAmount=the_credit;
       ELSE
         SET DrCr='Cr',txnAmount=the_debit;
       END IF;

       IF DrCr='Dr' THEN

         IF (SUBSTRING(the_account_no,2,2)='11' OR SUBSTRING(the_account_no,2,2)='22') THEN
 
           SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));
 
           SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalPrev FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" WHERE trn_date<='" AS CHAR CHARACTER SET utf8),txnDate,CAST("' ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
           PREPARE stmt1 FROM @sql_text1;
           EXECUTE stmt1;
           DROP PREPARE stmt1;
 
           IF ISNULL(@ledgerBalPrev) THEN
             SET @ledgerBalPrev=0;
           END IF;
 
           IF the_account_no='01107000110' THEN
             SET  @ledgerBalNow=@ledgerBalPrev-txnAmount;
           ELSE
             SET  @ledgerBalNow=@ledgerBalPrev+txnAmount;
           END IF;
           SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
           CALL accountNma(the_contra_account_no,@accountName);
 
           CALL postingTxnsX(NULL,txnDate,the_narration,txnValueDate,txnAmount,'-',@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"GenXX",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');
 
         END IF;

         IF (SUBSTRING(the_account_no,2,2)='33' OR SUBSTRING(the_account_no,2,2)='44' OR SUBSTRING(the_account_no,2,2)='55') THEN
 
           SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));
 
           SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalPrev FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" WHERE trn_date<='" AS CHAR CHARACTER SET utf8),txnDate,CAST("' ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
           PREPARE stmt1 FROM @sql_text1;
           EXECUTE stmt1;
           DROP PREPARE stmt1;
 
           IF ISNULL(@ledgerBalPrev) THEN
             SET @ledgerBalPrev=0;
           END IF;
 
           SET  @ledgerBalNow=@ledgerBalPrev-txnAmount;
           SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
           CALL accountNma(the_contra_account_no,@accountName);
 
           CALL postingTxnsX(NULL,txnDate,the_narration,txnValueDate,txnAmount,'-',@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"GenXX",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');
 
         END IF;

       END IF;

       IF DrCr='Cr' THEN

         IF (SUBSTRING(the_account_no,2,2)='11' OR SUBSTRING(the_account_no,2,2)='22') THEN
 
           SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));
 
           SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalPrev FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" WHERE trn_date<='" AS CHAR CHARACTER SET utf8),txnDate,CAST("' ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
           PREPARE stmt1 FROM @sql_text1;
           EXECUTE stmt1;
           DROP PREPARE stmt1;
 
           IF ISNULL(@ledgerBalPrev) THEN
             SET @ledgerBalPrev=0;
           END IF;
 
           IF the_account_no='01107000110' THEN
             SET  @ledgerBalNow=@ledgerBalPrev+txnAmount;
           ELSE
             SET  @ledgerBalNow=@ledgerBalPrev-txnAmount;
           END IF;
           SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
           CALL accountNma(the_contra_account_no,@accountName);
 
           CALL postingTxnsX(NULL,txnDate,the_narration,txnValueDate,'-',txnAmount,@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"GenXX",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');
 
         END IF;

         IF (SUBSTRING(the_account_no,2,2)='33' OR SUBSTRING(the_account_no,2,2)='44' OR SUBSTRING(the_account_no,2,2)='55') THEN
 
           SET @masterAccount=CONCAT(CAST(SUBSTRING(the_account_no,1,5) AS CHAR CHARACTER SET utf8),"0000",SUBSTRING(the_account_no,-2,2));
 
           SET @sql_text1=  CONCAT(CAST("SELECT ledger_balance INTO @ledgerBalPrev FROM bsanca" AS CHAR CHARACTER SET utf8),the_account_no,CAST(" WHERE trn_date<='" AS CHAR CHARACTER SET utf8),txnDate,CAST("' ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
           PREPARE stmt1 FROM @sql_text1;
           EXECUTE stmt1;
           DROP PREPARE stmt1;
 
           IF ISNULL(@ledgerBalPrev) THEN
             SET @ledgerBalPrev=0;
           END IF;
 
           SET  @ledgerBalNow=@ledgerBalPrev+txnAmount;
           SET the_narration=CONCAT(CAST("REVERSAL OF " AS CHAR CHARACTER SET utf8),the_narration);
           CALL accountNma(the_contra_account_no,@accountName);
 
           CALL postingTxnsX(NULL,txnDate,the_narration,txnValueDate,'-',txnAmount,@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"GenXX",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');
 
         END IF;

       END IF;

     END LOOP txnId_loop;
     SET l_done=0;
     CLOSE fortheTxnId;

     IF v_is_loan_batch=1 THEN

     BALANCES_BLOCK: BEGIN
       DECLARE b_done INT DEFAULT 0;
       DECLARE v_trn_id INT;
       DECLARE v_trn_date DATE;
       DECLARE v_amount_disbursed VARCHAR(100);
       DECLARE v_expected_total_amount VARCHAR(100);

       DECLARE v_amount_paid VARCHAR(100);
       DECLARE v_principal_paid VARCHAR(100);
       DECLARE v_interest_paid VARCHAR(100);
       DECLARE v_accumulated_interest_paid VARCHAR(100);
       DECLARE v_loan_penalty_paid VARCHAR(100);
       DECLARE v_principal_balance VARCHAR(100);
       DECLARE v_interest_balance VARCHAR(100);
       DECLARE v_accumulated_interest_balance VARCHAR(100);
       DECLARE v_loan_penalty_balance VARCHAR(100);

       DECLARE r_principal DOUBLE;
       DECLARE r_interest DOUBLE;
       DECLARE r_accum DOUBLE;
       DECLARE r_penalty DOUBLE;
       DECLARE r_loan DOUBLE;

       DECLARE d_amount_paid DOUBLE;
       DECLARE d_principal_paid DOUBLE;
       DECLARE d_interest_paid DOUBLE;
       DECLARE d_accum_paid DOUBLE;
       DECLARE d_penalty_paid DOUBLE;
       DECLARE d_amount_disbursed DOUBLE;
       DECLARE d_expected_total DOUBLE;

       DECLARE cur_stmt CURSOR FOR
         SELECT TrnId,TrnDate,AmountDisbursed,ExpectedTotalAmount,AmountPaid,PrincipalPaid,InterestPaid,AccumulatedInterestPaid,LoanPenaltyPaid,
                PrincipalBalance,InterestBalance,AccumulatedInterestBalance,LoanPenaltyBalance
         FROM loandisburserepaystatement
         WHERE loanTrnId=loanTrnIdL
         ORDER BY TrnDate ASC,TrnId ASC;

       DECLARE CONTINUE HANDLER FOR NOT FOUND SET b_done=1;

       SET r_principal=NULL;
       SET r_interest=NULL;
       SET r_accum=NULL;
       SET r_penalty=NULL;

       OPEN cur_stmt;
       stmt_loop: LOOP

         FETCH cur_stmt INTO v_trn_id,v_trn_date,v_amount_disbursed,v_expected_total_amount,v_amount_paid,v_principal_paid,v_interest_paid,v_accumulated_interest_paid,v_loan_penalty_paid,
                              v_principal_balance,v_interest_balance,v_accumulated_interest_balance,v_loan_penalty_balance;

         IF b_done=1 THEN
           LEAVE stmt_loop;
         END IF;

         SET d_amount_paid=IFNULL(CAST(v_amount_paid AS DECIMAL(20,2)),0);
         SET d_principal_paid=IFNULL(CAST(v_principal_paid AS DECIMAL(20,2)),0);
         SET d_interest_paid=IFNULL(CAST(v_interest_paid AS DECIMAL(20,2)),0);
         SET d_accum_paid=IFNULL(CAST(v_accumulated_interest_paid AS DECIMAL(20,2)),0);
         SET d_penalty_paid=IFNULL(CAST(v_loan_penalty_paid AS DECIMAL(20,2)),0);
         SET d_amount_disbursed=IFNULL(CAST(v_amount_disbursed AS DECIMAL(20,2)),0);
         SET d_expected_total=IFNULL(CAST(v_expected_total_amount AS DECIMAL(20,2)),0);

         IF r_principal IS NULL OR d_amount_disbursed>0 OR d_expected_total>0 THEN

           SET r_principal=IFNULL(CAST(v_principal_balance AS DECIMAL(20,2)),0);
           SET r_interest=IFNULL(CAST(v_interest_balance AS DECIMAL(20,2)),0);
           SET r_accum=IFNULL(CAST(v_accumulated_interest_balance AS DECIMAL(20,2)),0);
           SET r_penalty=IFNULL(CAST(v_loan_penalty_balance AS DECIMAL(20,2)),0);
           SET r_loan=r_principal+r_interest;

           UPDATE loandisburserepaystatement
           SET LoanBalance=r_loan
           WHERE TrnId=v_trn_id;

         ELSE

           SET r_principal=r_principal-d_principal_paid;
           SET r_interest=r_interest-d_interest_paid;
           SET r_accum=r_accum-d_accum_paid;
           SET r_penalty=r_penalty-d_penalty_paid;
           SET r_loan=r_principal+r_interest;

           UPDATE loandisburserepaystatement
           SET PrincipalBalance=r_principal,
               InterestBalance=r_interest,
               AccumulatedInterestBalance=r_accum,
               LoanPenaltyBalance=r_penalty,
               LoanBalance=r_loan
           WHERE TrnId=v_trn_id;

         END IF;

         SET b_done=0;

       END LOOP stmt_loop;
       CLOSE cur_stmt;
     END BALANCES_BLOCK;

     END IF;

     COMMIT;

     IF v_is_loan_batch=1 THEN
       SELECT 'SUCCESS' AS theMessage, CONCAT('Reversed batch ',batchNumber,' and rebuilt balances for loan_trn_id ',loanTrnIdL) AS MESSAGE;
     ELSE
       SELECT 'SUCCESS' AS theMessage, CONCAT('Reversed batch ',batchNumber) AS MESSAGE;
     END IF;

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

 DECLARE fortheTxnId CURSOR FOR SELECT new_loan_appstoreamort.trn_id FROM pmms_loans.new_loan_appstoreamort INNER JOIN pmms_loans.new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.applicant_account_number= accountNumber AND(new_loan_appstore.loan_cycle_status='Disbursed' OR new_loan_appstore.loan_cycle_status='Renewed'OR new_loan_appstore.loan_cycle_status='Combined') AND new_loan_appstoreamort.instalment_paid_date=DATE(NOW())  ORDER BY new_loan_appstoreamort.trn_id DESC;


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

-- 110657	SSEKIZIYIVU  JULIUS 0755271248	05502186210
        END //

        DELIMITER ;

  DROP PROCEDURE IF EXISTS updateAccountMaster; 

        DELIMITER //

-- SABIITI GEORGE 0706398854
-- SSENGOZI FRED 0757807020
-- OYUGI  IVAN 0703110169
-- SSENYONJO FRANK 0759542512
-- KIGOZI HENRY 070423174


CREATE PROCEDURE updateAccountMaster()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE space_pos INT;
    DECLARE accountnumber VARCHAR(255);
    DECLARE cur CURSOR FOR SELECT applicant_account_number FROM pmms_loans.new_Loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' OR loan_cycle_status='Combined';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO accountnumber;
 

-- IF accountnumber='05502185010' THEN
--  SELECT accountnumber;
-- END IF;

        IF done THEN
            LEAVE read_loop;
        END IF;
        IF NOT EXISTS (SELECT account_number FROM pmms.master WHERE account_number = accountnumber) THEN
SELECT DISTINCT applicant_account_name INTO @accountName  FROM pmms_loans.new_Loan_appstore WHERE applicant_account_number=accountnumber LIMIT 1;
SET space_pos = LOCATE(' ', @accountName);
SELECT SUBSTR(@accountName, 1, space_pos-1) , SUBSTR(@accountName, space_pos+1) INTO @lastName, @firstName;
            INSERT INTO pmms.master  VALUES (NULL,DATE(NOW()),SUBSTR(accountnumber, 3, 7),'Mr',@firstName,@lastName,'',DATE(NOW()),'','','','','','','','','','','','','','','','','','','','','','','','','','','','',DATE(NOW()),'',accountnumber,@accountName,TIME(NOW()),DATE(NOW()),TIME(NOW()),'','');
        END IF;

      SET accountnumber=NULL;  
    END LOOP;
    CLOSE cur;
  END //

        DELIMITER ;

CALL updateAccountMaster();

ALTER TABLE master MODIFY COLUMN UserPhoto VARCHAR(300);
UPDATE master SET UserPhoto='./avaImage.jpg';



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


        

      DROP PROCEDURE IF EXISTS accountMasterX;

        DELIMITER //

        CREATE PROCEDURE accountMasterX(IN accountNumber VARCHAR(30),OUT accountMaster VARCHAR(30)) BEGIN

         

        SET accountMaster=CAST(CONCAT(SUBSTR(accountNumber,1,6),'00010') AS CHAR CHARACTER SET utf8);

        END //

        DELIMITER ;



       -- DROP TABLE backupPath;
-- -----------------------------------------------------
-- Table backupPath
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS backupPath (

 id INT NOT NULL AUTO_INCREMENT,
 
  
   mysqlBinPath varchar(300) NOT NULL DEFAULT '0.0',
   theBackupPath varchar(300) NOT NULL DEFAULT '0.0',

  PRIMARY KEY (id)
  
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8;

-- DROP TABLE smsTable;
-- -----------------------------------------------------
-- Table smsTable
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS smsTable (

 id INT NOT NULL ,
 
  smsNumber  INT NOT NULL,

    smsPassword  VARCHAR(50) NOT NULL,

  PRIMARY KEY (id)
  
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


ALTER TABLE pmms.smsTable modify column  id enum('1') NOT NULL;

insert into smsTable VALUES(1,20,'Feel@H0me');




DROP TABLE IF EXISTS backupRestrict;

CREATE TABLE IF NOT EXISTS backupRestrict (
  id INT NOT NULL AUTO_INCREMENT,
  backupDate DATE,
  backupStatus INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

DELETE FROM backupRestrict;



CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `stock_in` int(11) DEFAULT '0',
  `stock_out` int(11) DEFAULT '0',
  `stock_level` int(11) NOT NULL,
  `refill_threshold` int(11) DEFAULT NULL,
  `safety_stock_level` int(11) DEFAULT NULL,
  `last_order_date` date DEFAULT NULL,
  `last_receipt_date` date DEFAULT NULL,
  `last_sale_date` date DEFAULT NULL,
  `lead_time` int(11) DEFAULT NULL,
  `lead_time_unit_of_measure`  varchar(255) DEFAULT NULL,
  `minimum_order_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `stock_details` (
  `stock_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_id` int(11) NOT NULL,
  `stock_details_in` int(11) DEFAULT '0',
  `stock_details_out` int(11) DEFAULT '0',
  `stock_details_level` int(11) NOT NULL,
  `location_bin_number` varchar(255) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `serial_batch_number` varchar(255) DEFAULT NULL,
  `cost_price` decimal(10,2) DEFAULT NULL, -- New addition
  `selling_price` decimal(10,2) DEFAULT NULL, -- New addition
   `stock_details_quantity` decimal(10,2) DEFAULT NULL, -- New addition
     `stock_details_amount` decimal(10,2) DEFAULT NULL, -- New addition
  PRIMARY KEY (`stock_details_id`),
  KEY `stock_id` (`stock_id`),
  CONSTRAINT `stock_details_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `stock_transactions` (
  `stock_transactions_id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_details_id` int(11) NOT NULL,
  `transaction_type` enum('PURCHASE','SALE', 'RETURN') DEFAULT NULL,
  `transaction_quantity` int(11) DEFAULT NULL,
  `transaction_date` date DEFAULT NULL,
  
  `list_price_per_item` decimal(10,2) DEFAULT NULL, -- New addition
  `actual_price_per_item` decimal(10,2) DEFAULT NULL, -- New addition
  `cost_price` decimal(10,2) DEFAULT NULL, -- For tracking the cost of items
  
  `expected_transaction_amount` decimal(10,2) DEFAULT NULL, -- New addition
  `actual_transaction_amount` decimal(10,2) DEFAULT NULL, -- Existing field, re-purposed
  `discount_amount` decimal(10,2) DEFAULT NULL, -- Existing field
  `discount_percentage` decimal(5,2) DEFAULT NULL, -- Existing field
  
  `original_transaction_id` int(11) DEFAULT NULL, -- For linking returns to their original transaction
  PRIMARY KEY (`stock_transactions_id`),
  KEY `stock_details_id` (`stock_details_id`),
  KEY `original_transaction_id` (`original_transaction_id`),
  CONSTRAINT `fk_original_transaction` FOREIGN KEY (`original_transaction_id`) REFERENCES `stock_transactions` (`stock_transactions_id`),
  CONSTRAINT `stock_transactions_ibfk_1` FOREIGN KEY (`stock_details_id`) REFERENCES `stock_details` (`stock_details_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `stock_returns` (
  `return_id` int(11) NOT NULL AUTO_INCREMENT,
  `original_transaction_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `returned_quantity` int(11) NOT NULL,
  `return_date` date NOT NULL,
  `return_condition` varchar(255) DEFAULT NULL,
  `action_taken` enum('restocked', 'repaired', 'disposed', 'other') DEFAULT NULL,
  `reason_for_return` varchar(255) DEFAULT NULL,
  `financial_adjustments` decimal(10,2) DEFAULT NULL,
  `refunded_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`return_id`),
  KEY `item_id` (`item_id`),
  KEY `original_transaction_id` (`original_transaction_id`),
  CONSTRAINT `returns_fk0` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `returns_fk1` FOREIGN KEY (`original_transaction_id`) REFERENCES `stock_transactions` (`stock_transactions_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP PROCEDURE IF EXISTS CreateOrUpdateStock;
DELIMITER //

CREATE PROCEDURE CreateOrUpdateStock(
    IN _item_id INT,
    IN _quantity INT,
    IN _cost_price DECIMAL(10,2),
    IN _location_bin_number VARCHAR(255),
    IN _expiration_date DATE,
    IN _list_price_per_item DECIMAL(10,2)
)
BEGIN
    DECLARE _stock_id INT;
    DECLARE _current_stock_level INT;
    DECLARE _transaction_date DATE;
    DECLARE _serial_batch_number VARCHAR(255);
    DECLARE _stock_details_amount DECIMAL(10,2);

    -- Set the transaction date to the current date
    SET _transaction_date = CURRENT_DATE();

    -- Generate a serial/batch number
    SET _serial_batch_number = CONCAT('BATCH-', _item_id, '-', REPLACE(_transaction_date, '-', ''), '-', LPAD(FLOOR(RAND() * 1000), 4, '0'));

    -- Calculate the stock_details_amount
    SET _stock_details_amount = _quantity * _cost_price;

    -- Check if the item already exists in stock
    SELECT stock_id, stock_level INTO _stock_id, _current_stock_level 
    FROM stock 
    WHERE item_id = _item_id;

    IF _stock_id IS NOT NULL THEN
        -- Update existing stock record
        UPDATE stock
        SET stock_in = stock_in + _quantity,
            stock_level = stock_level + _quantity,
            last_receipt_date = _transaction_date
        WHERE stock_id = _stock_id;
    ELSE
        -- Create new stock record
        INSERT INTO stock (item_id, stock_in, stock_out, stock_level, last_receipt_date)
        VALUES (_item_id, _quantity, 0, _quantity, _transaction_date);

        SET _stock_id = LAST_INSERT_ID();
    END IF;

    -- Insert into stock_details table
    INSERT INTO stock_details (stock_id, stock_details_in, stock_details_out, stock_details_level, location_bin_number, expiration_date, serial_batch_number, cost_price, stock_details_quantity, stock_details_amount)
    VALUES (_stock_id, _quantity, 0, _quantity, _location_bin_number, _expiration_date, _serial_batch_number, _cost_price, _quantity, _stock_details_amount);

    -- Insert into stock_transactions table
    INSERT INTO stock_transactions (stock_details_id, transaction_type, transaction_quantity, transaction_date, cost_price, actual_transaction_amount, list_price_per_item)
    VALUES (LAST_INSERT_ID(), 'PURCHASE', _quantity, _transaction_date, _cost_price, _stock_details_amount, _list_price_per_item);

    -- Update cost_price in items table
    UPDATE items
    SET cost_price = _cost_price
    WHERE item_id = _item_id;
END //

DELIMITER ;





DROP PROCEDURE IF EXISTS RecordSale;
DELIMITER //
CREATE PROCEDURE RecordSale(
    IN _item_id INT,
    IN _total_quantity INT,
    IN _selling_price DECIMAL(10,2),
    IN _discount_amount DECIMAL(10,2),
    OUT _total_cost_value DECIMAL(10,2)
)
BEGIN
    DECLARE _exit_strategy VARCHAR(20);
    DECLARE _available_stock INT;
    DECLARE _error_message VARCHAR(255);
    DECLARE _batch_cost DECIMAL(10,2) DEFAULT 0.0;

    START TRANSACTION;

    SELECT exit_order INTO _exit_strategy FROM items WHERE item_id = _item_id;

    SELECT SUM(stock_details_level) INTO _available_stock
    FROM stock_details
    WHERE stock_id IN (SELECT stock_id FROM stock WHERE item_id = _item_id);

    IF _available_stock < _total_quantity THEN
        ROLLBACK;
        SET _error_message = 'Insufficient stock available for sale.';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = _error_message;
    ELSE
        IF _exit_strategy = 'FIFO' THEN
            CALL ProcessSaleBatchFIFO(_item_id, _total_quantity, _selling_price, _discount_amount, _batch_cost);
        ELSEIF _exit_strategy = 'LIFO' THEN
            CALL ProcessSaleBatchLIFO(_item_id, _total_quantity, _selling_price, _discount_amount, _batch_cost);
        ELSE
            CALL ProcessSaleBatchExpiration(_item_id, _total_quantity, _selling_price, _discount_amount, _batch_cost);
        END IF;

        UPDATE stock
        SET stock_out = stock_out + _total_quantity,
            stock_level = stock_level - _total_quantity,
            last_sale_date = CURRENT_DATE()
        WHERE item_id = _item_id;

        SET _total_cost_value = _batch_cost;
        COMMIT;
    END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS ProcessSaleBatchExpiration;
DELIMITER //
CREATE PROCEDURE ProcessSaleBatchExpiration(
    IN _item_id INT,
    IN _total_quantity INT,
    IN _selling_price DECIMAL(10,2),
    IN _discount_amount DECIMAL(10,2),
    OUT _total_value_cost DECIMAL(10,2)
)
BEGIN
    DECLARE _remaining_quantity INT DEFAULT _total_quantity;
    DECLARE _batch_quantity INT;
    DECLARE _stock_details_id INT;
    DECLARE _cost_price DECIMAL(10,2);
    DECLARE _actual_transaction_amount DECIMAL(10,2);
    DECLARE done INT DEFAULT FALSE;
    DECLARE batch_cursor CURSOR FOR
        SELECT stock_details_id, stock_details_level, cost_price
        FROM stock_details
        WHERE stock_id IN (SELECT stock_id FROM stock WHERE item_id = _item_id) AND stock_details_level > 0
        ORDER BY expiration_date ASC;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN batch_cursor;
    SET _total_value_cost = 0.0;

    read_loop: LOOP
        FETCH batch_cursor INTO _stock_details_id, _batch_quantity, _cost_price;
        IF done THEN
            LEAVE read_loop;
        END IF;

        IF _batch_quantity >= _remaining_quantity THEN
            SET _batch_quantity = _remaining_quantity;
            SET _remaining_quantity = 0;
        ELSE
            SET _remaining_quantity = _remaining_quantity - _batch_quantity;
        END IF;

        SET _actual_transaction_amount = (_batch_quantity * _selling_price) - (_discount_amount * _batch_quantity / _total_quantity);
        SET _total_value_cost = _total_value_cost + (_batch_quantity * _cost_price);

        UPDATE stock_details
        SET stock_details_out = stock_details_out + _batch_quantity,
            stock_details_level = stock_details_level - _batch_quantity,
            selling_price = _selling_price -- Updating selling price in stock_details
        WHERE stock_details_id = _stock_details_id;

        INSERT INTO stock_transactions (stock_details_id, transaction_type, transaction_quantity, transaction_date, list_price_per_item, actual_transaction_amount, discount_amount)
        VALUES (_stock_details_id, 'SALE', _batch_quantity, CURRENT_DATE(), _selling_price, _actual_transaction_amount, _discount_amount * _batch_quantity / _total_quantity);

    END LOOP;

    CLOSE batch_cursor;

    UPDATE stock
    SET stock_out = stock_out + _total_quantity,
        stock_level = stock_level - _total_quantity,
        last_sale_date = CURRENT_DATE()
    WHERE item_id = _item_id;

    -- Updating selling price in items
    UPDATE items
    SET price = _selling_price
    WHERE item_id = _item_id;

    SELECT _total_value_cost;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS ProcessSaleBatchFIFO;
DELIMITER //
CREATE PROCEDURE ProcessSaleBatchFIFO(
    IN _item_id INT,
    IN _total_quantity INT,
    IN _selling_price DECIMAL(10,2),
    IN _discount_amount DECIMAL(10,2),
    OUT _total_value_cost DECIMAL(10,2)
)
BEGIN
    DECLARE _remaining_quantity INT DEFAULT _total_quantity;
    DECLARE _batch_quantity INT;
    DECLARE _stock_details_id INT;
    DECLARE _cost_price DECIMAL(10,2);
    DECLARE _actual_transaction_amount DECIMAL(10,2);
    DECLARE done INT DEFAULT FALSE;
  DECLARE batch_cursor CURSOR FOR 
        SELECT sd.stock_details_id, sd.stock_details_level ,sd.cost_price
                FROM stock_details sd
                JOIN stock s ON sd.stock_id = s.stock_id
                WHERE s.item_id = _item_id AND sd.stock_details_level > 0
                ORDER BY s.last_receipt_date ASC, sd.stock_details_id ASC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN batch_cursor;
    SET _total_value_cost = 0.0;

    read_loop: LOOP
        FETCH batch_cursor INTO _stock_details_id, _batch_quantity, _cost_price;
        IF done THEN
            LEAVE read_loop;
        END IF;

        IF _batch_quantity >= _remaining_quantity THEN
            SET _batch_quantity = _remaining_quantity;
            SET _remaining_quantity = 0;
        ELSE
            SET _remaining_quantity = _remaining_quantity - _batch_quantity;
        END IF;

        SET _actual_transaction_amount = (_batch_quantity * _selling_price) - (_discount_amount * _batch_quantity / _total_quantity);
        SET _total_value_cost = _total_value_cost + (_batch_quantity * _cost_price);

        UPDATE stock_details
        SET stock_details_out = stock_details_out + _batch_quantity,
            stock_details_level = stock_details_level - _batch_quantity,
                selling_price = _selling_price -- Updating selling price in stock_details
        WHERE stock_details_id = _stock_details_id;

        INSERT INTO stock_transactions (stock_details_id, transaction_type, transaction_quantity, transaction_date, list_price_per_item, actual_transaction_amount, discount_amount)
        VALUES (_stock_details_id, 'SALE', _batch_quantity, CURRENT_DATE(), _selling_price, _actual_transaction_amount, _discount_amount * _batch_quantity / _total_quantity);

    END LOOP;

    CLOSE batch_cursor;

    UPDATE stock
    SET stock_out = stock_out + _total_quantity,
        stock_level = stock_level - _total_quantity,
        last_sale_date = CURRENT_DATE()
     -- (Previous code)
    WHERE item_id = _item_id;

       -- Updating selling price in items
    UPDATE items
    SET price = _selling_price
    WHERE item_id = _item_id;

    SELECT _total_value_cost;
END //
DELIMITER ;




DROP PROCEDURE IF EXISTS ProcessSaleBatchLIFO;
DELIMITER //
CREATE PROCEDURE ProcessSaleBatchLIFO(
    IN _item_id INT,
    IN _total_quantity INT,
    IN _selling_price DECIMAL(10,2),
    IN _discount_amount DECIMAL(10,2),
    OUT _total_value_cost DECIMAL(10,2)
)
BEGIN
    DECLARE _remaining_quantity INT DEFAULT _total_quantity;
    DECLARE _batch_quantity INT;
    DECLARE _stock_details_id INT;
    DECLARE _cost_price DECIMAL(10,2);
    DECLARE _actual_transaction_amount DECIMAL(10,2);
    DECLARE done INT DEFAULT FALSE;
  DECLARE batch_cursor CURSOR FOR 
          SELECT sd.stock_details_id, sd.stock_details_level ,sd.cost_price
                FROM stock_details sd
                JOIN stock s ON sd.stock_id = s.stock_id
                WHERE s.item_id = _item_id AND sd.stock_details_level > 0
                ORDER BY s.last_receipt_date DESC, sd.stock_details_id DESC;



    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN batch_cursor;
    SET _total_value_cost = 0.0;

    read_loop: LOOP
        FETCH batch_cursor INTO _stock_details_id, _batch_quantity, _cost_price;
        IF done THEN
            LEAVE read_loop;
        END IF;

        IF _batch_quantity >= _remaining_quantity THEN
            SET _batch_quantity = _remaining_quantity;
            SET _remaining_quantity = 0;
        ELSE
            SET _remaining_quantity = _remaining_quantity - _batch_quantity;
        END IF;

        SET _actual_transaction_amount = (_batch_quantity * _selling_price) - (_discount_amount * _batch_quantity / _total_quantity);
        SET _total_value_cost = _total_value_cost + (_batch_quantity * _cost_price);

        UPDATE stock_details
        SET stock_details_out = stock_details_out + _batch_quantity,
            stock_details_level = stock_details_level - _batch_quantity,
                selling_price = _selling_price -- Updating selling price in stock_details
        WHERE stock_details_id = _stock_details_id;

        INSERT INTO stock_transactions (stock_details_id, transaction_type, transaction_quantity, transaction_date, list_price_per_item, actual_transaction_amount, discount_amount)
        VALUES (_stock_details_id, 'SALE', _batch_quantity, CURRENT_DATE(), _selling_price, _actual_transaction_amount, _discount_amount * _batch_quantity / _total_quantity);

    END LOOP;

    CLOSE batch_cursor;

    UPDATE stock
    SET stock_out = stock_out + _total_quantity,
        stock_level = stock_level - _total_quantity,
        last_sale_date = CURRENT_DATE()
     -- (Previous code)
    WHERE item_id = _item_id;

      -- Updating selling price in items
    UPDATE items
    SET price = _selling_price
    WHERE item_id = _item_id;

    SELECT _total_value_cost;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getAllSendingDetailsLimit2;
DELIMITER ##
CREATE PROCEDURE getAllSendingDetailsLimit2() BEGIN

SELECT contactName1 AS name,backUpContactNotification1 AS contact,TIME(CURRENT_TIMESTAMP) AS theTime,DATE_FORMAT(DATE(CURRENT_TIMESTAMP),"%d/%m/%Y") AS theDate FROM  backupcontact WHERE enabled=2;

END ##
DELIMITER ;

CALL getAllSendingDetailsLimit2();


DROP PROCEDURE IF EXISTS accountMasterX;

        DELIMITER //

        CREATE PROCEDURE accountMasterX(IN accountNumber VARCHAR(30),OUT accountMaster VARCHAR(30)) BEGIN

         
-- SELECT accountNumber;
        SET accountMaster=CAST(CONCAT(SUBSTR(accountNumber,1,5),'000010') AS CHAR CHARACTER SET utf8);
-- SELECT accountMaster;
        END //

        DELIMITER ;



        DELIMITER ;
CREATE INDEX idx_trn_id ON bsanca01128000110 (trn_id);
CREATE INDEX idx_trn_date ON bsanca01128000110 (trn_date);
CREATE INDEX idx_ledger_balance ON bsanca01128000110 (ledger_balance);
CREATE INDEX idx_account_number ON bsanca01128000110 (account_number);
CREATE INDEX idx_trn_id ON bsanca01128000010 (trn_id);
CREATE INDEX idx_trn_date ON bsanca01128000010 (trn_date);
CREATE INDEX idx_account_number ON bsanca01128000010 (account_number);
CREATE INDEX idx_account_balance ON bsanca01128000010 (account_balance);
CREATE INDEX idx_master_balance ON bsanca01128000010 (master_balance);
CREATE INDEX idx_trn_id ON bsanca01123000010 (trn_id);
CREATE INDEX idx_trn_date ON bsanca01123000010 (trn_date);
CREATE INDEX idx_account_number ON bsanca01123000010 (account_number);
CREATE INDEX idx_account_balance ON bsanca01123000010 (account_balance);
CREATE INDEX idx_master_balance ON bsanca01123000010 (master_balance);
ALTER TABLE bsanca01123000110 ADD INDEX idx_trn_id (trn_id);
ALTER TABLE bsanca01123000110 ADD INDEX idx_trn_date (trn_date);
ALTER TABLE bsanca01123000110 ADD INDEX idx_ledger_balance (ledger_balance);
ALTER TABLE bsanca01123000110 ADD INDEX idx_account_number (account_number);
ALTER TABLE loandisburserepaystatement ADD INDEX idx_trn_id (TrnId);
ALTER TABLE loandisburserepaystatement ADD INDEX idx_loan_trn_id (loanTrnId);
ALTER TABLE loandisburserepaystatement ADD INDEX idx_loan_id (LoanId);
ALTER TABLE loandisburserepaystatement ADD INDEX idx_account_number (AccountNumber);
ALTER TABLE loandisburserepaystatement ADD INDEX idx_batch_code (BatchCode);

ALTER TABLE bsanca05502000010 ADD INDEX idx_trn_date_customer (trn_date);
ALTER TABLE bsanca05502000010 ADD INDEX idx_value_date_customer (value_date);
ALTER TABLE bsanca05502000010 ADD INDEX idx_account_number_customer (account_number);
ALTER TABLE bsanca05502000010 ADD INDEX idx_account_name_customer (account_name(50));
ALTER TABLE bsanca05502000010 ADD INDEX idx_staff_id_customer (staff_id);

ALTER TABLE bsanca01128000010 ADD INDEX idx_trn_date_loan (trn_date);
ALTER TABLE bsanca01128000010 ADD INDEX idx_value_date_loan (value_date);
ALTER TABLE bsanca01128000010 ADD INDEX idx_account_number_loan (account_number);
ALTER TABLE bsanca01128000010 ADD INDEX idx_account_name_loan (account_name(50));
ALTER TABLE bsanca01128000010 ADD INDEX idx_staff_id_loan (staff_id);
ALTER TABLE general_ledger ADD INDEX idx_trn_id (trn_id);
CREATE INDEX idx_trn_date ON general_ledger (trn_date);
CREATE INDEX idx_value_date ON general_ledger (value_date);
CREATE INDEX idx_debit_account_no ON general_ledger (debit_account_no);
CREATE INDEX idx_credit_account_no ON general_ledger (credit_account_no);
CREATE INDEX idx_trn_type ON general_ledger (trn_type);
CREATE INDEX idx_staff_id ON general_ledger (staff_id);
CREATE INDEX idx_trn_date_debit_account_no ON general_ledger (trn_date, debit_account_no);
CREATE INDEX idx_trn_date_credit_account_no ON general_ledger (trn_date, credit_account_no);



CREATE TABLE reportsManagement (
  id INT NOT NULL PRIMARY KEY,
    cash int(11) DEFAULT 1,
      banks int(11) DEFAULT 1,
        summury int(11) DEFAULT 1,
          agingAnalysis int(11) DEFAULT 1,
            loanStatement int(11) DEFAULT 1,
              loanPortfolio int(11) DEFAULT 1,
                compoundInterest int(11) DEFAULT 1,
  nonCompoundInterest int(11) DEFAULT 1,
      otherOne int(11) DEFAULT 1,
  otherTwo int(11) DEFAULT 1
) ENGINE=InnoDB  DEFAULT CHARACTER SET = utf8;

ALTER TABLE pmms.reportsManagement modify column  id enum('1') NOT NULL;


 DROP PROCEDURE IF EXISTS postingTxnsX;


        DELIMITER //


        CREATE PROCEDURE  postingTxnsX(IN trn_idX INT(11), IN trn_dateX DATE, IN narrationX VARCHAR(200), IN value_dateX  DATE, IN  debitX VARCHAR(50), IN creditX VARCHAR(50) ,IN ledger_balanceX VARCHAR(50),IN credit_account_noX VARCHAR(50),IN credit_account_nameX VARCHAR(200) ,IN tra_ref_numberX VARCHAR(50),IN  chq_numberX VARCHAR(50), IN trn_typeX VARCHAR(50) ,IN staff_idX  VARCHAR(50), IN trn_timeX TIME, IN trn_sq_noX VARCHAR(10), IN account_numberX VARCHAR(100),IN  master_numberX VARCHAR(100),IN other_oneX VARCHAR(100),IN other_twoX VARCHAR(100) ,IN other_threeX VARCHAR(100) )  BEGIN

        DECLARE adjustment VARCHAR(50);
          


           IF debitX='-' THEN
           SET adjustment=creditX;
           ELSE 
        SET adjustment=debitX;
        END IF;

        IF trn_typeX <>'GenXX' THEN 


        IF(other_oneX LIKE '%Cr%') THEN 

        SET @creditAccount=account_numberX;

        SET @debitAccount=credit_account_noX;

        CALL accountNma(@creditAccount,@accountName);


        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @creditAccount,@debitAccount,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);

        SELECT @creditAccount,trn_dateX,narrationX,value_dateX,debitX,creditX,ledger_balanceX,credit_account_noX,credit_account_nameX,chq_numberX,trn_typeX,staff_idX,trn_timeX,trn_sq_noX,account_numberX,master_numberX,other_oneX,other_twoX,other_threeX;


        SET @qryA = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@creditAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));
        SELECT @qryA;
    IF @qryA IS NOT NULL THEN
        PREPARE stmx FROM @qryA;
        EXECUTE stmx;
        -- DROP PREPARE stmx;
         DEALLOCATE PREPARE stmx;
        ELSE
            SELECT 'Error: Statement preparation failed.';
        END IF;

        END IF;



        IF(other_oneX LIKE '%Dr%') THEN 

        SET @creditAccount=credit_account_noX;

        SET @debitAccount=account_numberX;

        CALL accountNma(@debitAccount,@accountName);

        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @debitAccount ,@creditAccount ,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);  

SELECT @debitAccount,trn_dateX,narrationX,value_dateX,debitX,creditX,ledger_balanceX,credit_account_noX,credit_account_nameX,chq_numberX,trn_typeX,staff_idX,trn_timeX,trn_sq_noX,account_numberX,master_numberX,other_oneX,other_twoX,other_threeX;

        SET @qryB = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@debitAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));

SELECT @qryB;
    IF @qryB IS NOT NULL THEN
        PREPARE stm1 FROM @qryB;
        EXECUTE stm1;
        -- DROP PREPARE stm1;
         DEALLOCATE PREPARE stm1;
        ELSE
            SELECT 'Error: Statement preparation failed.';
        END IF;


        END IF;



        UPDATE account_created_store SET running_balance=ledger_balanceX,trn_date=trn_dateX  WHERE account_number=account_numberX;
SELECT "eASEY";
        CALL   updateMasterX(trn_dateX,account_numberX,ledger_balanceX,staff_idX);

SELECT "eASEY BUT";



        ELSE


        IF(other_oneX LIKE '%Cr%') THEN 

        SET @creditAccount=account_numberX;

        SET @debitAccount=credit_account_noX;

        CALL accountNma(@creditAccount,@accountName);


        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @creditAccount,@debitAccount,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);

        SELECT @creditAccount,trn_dateX,narrationX,value_dateX,debitX,creditX,ledger_balanceX,credit_account_noX,credit_account_nameX,chq_numberX,trn_typeX,staff_idX,trn_timeX,trn_sq_noX,account_numberX,master_numberX,other_oneX,other_twoX,other_threeX;

        SET @qryA = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@creditAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));
        SELECT @qryA;

        PREPARE stmy FROM @qryA;
        EXECUTE stmy;
        -- DROP PREPARE stmy;
         DEALLOCATE PREPARE stmy;

        END IF;



        IF(other_oneX LIKE '%Dr%') THEN 

        SET @creditAccount=credit_account_noX;

        SET @debitAccount=account_numberX;

        CALL accountNma(@debitAccount,@accountName);

        INSERT INTO general_ledger  VALUES (null, trn_dateX, narrationX, value_dateX,debitX, creditX, @debitAccount ,@creditAccount ,@accountName,tra_ref_numberX ,chq_numberX ,trn_typeX ,staff_idX ,trn_timeX ,trn_sq_noX);    
SELECT @debitAccount,trn_dateX,narrationX,value_dateX,debitX,creditX,ledger_balanceX,credit_account_noX,credit_account_nameX,chq_numberX,trn_typeX,staff_idX,trn_timeX,trn_sq_noX,account_numberX,master_numberX,other_oneX,other_twoX,other_threeX;

        SET @qryB = concat(CAST("INSERT INTO BSANCA" AS CHAR CHARACTER SET utf8),@debitAccount,CAST(" VALUES( NULL"AS CHAR CHARACTER SET utf8),CAST(",'"AS CHAR CHARACTER SET utf8),trn_dateX,CAST("','"AS CHAR CHARACTER SET utf8),narrationX,CAST("','"AS CHAR CHARACTER SET utf8),value_dateX,CAST("','"AS CHAR CHARACTER SET utf8),debitX,CAST("','"AS CHAR CHARACTER SET utf8),creditX,CAST("','"AS CHAR CHARACTER SET utf8),ledger_balanceX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_noX,CAST("','"AS CHAR CHARACTER SET utf8),credit_account_nameX,CAST("','" AS CHAR CHARACTER SET utf8),tra_ref_numberX,CAST("','" AS CHAR CHARACTER SET utf8),chq_numberX,CAST("','"AS CHAR CHARACTER SET utf8),trn_typeX,CAST("','"AS CHAR CHARACTER SET utf8),staff_idX,CAST("','"AS CHAR CHARACTER SET utf8),trn_timeX,CAST("','"AS CHAR CHARACTER SET utf8),trn_sq_noX,CAST("','"AS CHAR CHARACTER SET utf8),account_numberX,CAST("','"AS CHAR CHARACTER SET utf8),master_numberX,CAST("','"AS CHAR CHARACTER SET utf8),other_oneX,CAST("','"AS CHAR CHARACTER SET utf8),other_twoX,CAST("','"AS CHAR CHARACTER SET utf8),other_threeX, CAST("')"AS CHAR CHARACTER SET utf8));

        SELECT @qryB;
        PREPARE stmz FROM @qryB;
        EXECUTE stmz;
        -- DROP PREPARE stmz;
         DEALLOCATE PREPARE stmz;

        END IF;



        UPDATE account_created_store SET running_balance=ledger_balanceX,trn_date=trn_dateX  WHERE account_number=account_numberX;
SELECT "eASEY";
        CALL   updateMasterX(trn_dateX,account_numberX,ledger_balanceX,staff_idX);

SELECT "eASEY BUT";



IF (other_oneX LIKE '%Dr%') THEN
        SET @tableName = CONCAT('BSANCA', @debitAccount);
        SET @stmt = CONCAT('SELECT MIN(trn_id) AS first_trn_id INTO @Id FROM ', @tableName, ' WHERE trn_date > ''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmt;  -- for debugging
        PREPARE stmt_query FROM @stmt;
        EXECUTE stmt_query;
        DEALLOCATE PREPARE stmt_query;
        -- SELECT @Id, @debitAccount;
                 IF @Id IS NOT NULL THEN
        CALL adjustIds(@Id, @debitAccount);
ELSE

   SET @stmt = CONCAT('SELECT MAX(trn_id) AS first_trn_id INTO @Id FROM ', @tableName, ' WHERE trn_date <=''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmt;  -- for debugging
        PREPARE stmt_query FROM @stmt;
        EXECUTE stmt_query;
        DEALLOCATE PREPARE stmt_query;

  END IF;


            CALL accountMasterX(@debitAccount,@accountMaster);
SET @MastertableName = CONCAT('BSANCA', @accountMaster);

        SET @stmtMaster = CONCAT('SELECT MIN(trn_id) AS first_trn_id INTO @IdMaster FROM ', @MastertableName, ' WHERE trn_date > ''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmtMaster, @MastertableName;  -- for debugging
        PREPARE Masterstmt_query FROM @stmtMaster;
        EXECUTE Masterstmt_query;
        DEALLOCATE PREPARE Masterstmt_query;
        -- SELECT @IdMaster, @accountMaster;
 IF @IdMaster IS NOT NULL THEN
        CALL adjustIds(@IdMaster, @accountMaster);

        ELSE
   SET @stmtMaster = CONCAT('SELECT MAX(trn_id) AS first_trn_id INTO @IdMaster FROM ', @MastertableName, ' WHERE trn_date <=''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmtMaster, @MastertableName;  -- for debugging
        PREPARE Masterstmt_query FROM @stmtMaster;
        EXECUTE Masterstmt_query;
        DEALLOCATE PREPARE Masterstmt_query;  
   END IF;
  CALL AdjustHistoricalRunningBalances(@Id,@IdMaster,@debitAccount,@accountMaster,adjustment,'Dr');     
    ELSEIF (other_oneX LIKE '%Cr%') THEN
        SET @tableName = CONCAT('BSANCA', @creditAccount);
        SET @stmt = CONCAT('SELECT MIN(trn_id) AS first_trn_id INTO @Id FROM ', @tableName, ' WHERE trn_date > ''', trn_dateX, ''' LIMIT 1');
        SELECT @stmt, @tableName;  -- for debugging
        PREPARE stmt_query FROM @stmt;
        EXECUTE stmt_query;
        DEALLOCATE PREPARE stmt_query;
        SELECT @Id, @creditAccount;
            IF @Id IS NOT NULL THEN
        CALL adjustIds(@Id, @creditAccount);
        ELSE 
        
   SET @stmt = CONCAT('SELECT MAX(trn_id) AS first_trn_id INTO @Id FROM ', @tableName, ' WHERE trn_date <=''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmt;  -- for debugging
        PREPARE stmt_query FROM @stmt;
        EXECUTE stmt_query;
        DEALLOCATE PREPARE stmt_query;
   END IF;
    CALL accountMasterX(@creditAccount,@accountMaster);
SET @MastertableName = CONCAT('BSANCA', @accountMaster);
        SET @stmtMaster = CONCAT('SELECT MIN(trn_id) AS first_trn_id INTO @IdMaster FROM ', @MastertableName, ' WHERE trn_date > ''', trn_dateX, ''' LIMIT 1');
        SELECT @stmtMaster, @MastertableName;  -- for debugging
        PREPARE Masterstmt_query FROM @stmtMaster;
        EXECUTE Masterstmt_query;
        DEALLOCATE PREPARE Masterstmt_query;
        SELECT @IdMaster, @accountMaster;
 IF @IdMaster IS NOT NULL THEN
        CALL adjustIds(@IdMaster, @accountMaster);

        ELSE
 SET @stmtMaster = CONCAT('SELECT MAX(trn_id) AS first_trn_id INTO @IdMaster FROM ', @MastertableName, ' WHERE trn_date <=''', trn_dateX, ''' LIMIT 1');
        -- SELECT @stmtMaster, @MastertableName;  -- for debugging
        PREPARE Masterstmt_query FROM @stmtMaster;
        EXECUTE Masterstmt_query;
        DEALLOCATE PREPARE Masterstmt_query;        
   END IF;
     
   CALL AdjustHistoricalRunningBalances(@Id,@IdMaster,@creditAccount,@accountMaster,adjustment,'Cr');


    END IF;

 END IF;
        END  //

        DELIMITER ;









DELIMITER $$

DROP PROCEDURE IF EXISTS AdjustHistoricalRunningBalances$$

CREATE PROCEDURE AdjustHistoricalRunningBalances(
    IN startingId INT,
    IN startingMasterId INT,
    IN accountNumber VARCHAR(200),
    IN accountMasterNumber VARCHAR(200),
    IN adjustment DECIMAL(10,2),
    IN txnType VARCHAR(20)
)
BEGIN
    DECLARE lastId,counterAccount,counterMaster,lastMasterId  INT DEFAULT 0;
      DECLARE accountCat VARCHAR(20);
    DECLARE accountTable VARCHAR(255);
    DECLARE masterTable VARCHAR(1000);
        DECLARE txnTypeIn VARCHAR(20);
 DECLARE ledger_balanceNow,initial_ledgerBalance,initial_previousBal,currentadjustment VARCHAR(1000);
  DECLARE master_balanceNow VARCHAR(1000);
    SET accountTable = CONCAT('bsanca', accountNumber);
    SET masterTable = CONCAT('bsanca', accountMasterNumber);


    SELECT startingId,startingMasterId,accountNumber,accountMasterNumber,adjustment,txnType;

    -- Get the last transaction ID for looping condition
    SET @sql = CONCAT('SELECT MAX(trn_id) INTO @lastId FROM ', accountTable);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    SET lastId = @lastId,counterAccount=1;

    -- Loop through the account table records without cursor
    WHILE startingId <= lastId DO
        SET @ledger = CONCAT('SELECT ledger_balance INTO @previousBalance FROM ', accountTable, ' WHERE trn_id = (SELECT MAX(trn_id) FROM ', accountTable, ' WHERE trn_id < ', startingId, ')');
        PREPARE stmt FROM @ledger;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

   


        SET @ledger = CONCAT('SELECT debit,credit INTO @debit,@credit FROM ', accountTable, ' WHERE trn_id = ', startingId);
        PREPARE stmt FROM @ledger;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;


        IF @debit ='-' THEN

--          IF accountNumber='01107000110' THEN 
-- SET @debit =0.0,currentadjustment=@credit,txnType='Dr';
--          ELSE
SET @debit =0.0,currentadjustment=@credit,txnTypeIn='Cr';
-- END IF;
        END IF;

           IF @credit ='-' THEN
           
--          IF accountNumber='01107000110' THEN 
-- SET @credit =0.0,currentadjustment=@debit,txnType='Cr';
--          ELSE
SET @credit =0.0,currentadjustment=@debit,txnTypeIn='Dr';
        -- END IF;
    END IF;
        SET accountCat = SUBSTR(accountNumber, 2, 2);

        IF txnTypeIn LIKE '%Dr%' THEN
            CASE 
                WHEN accountCat = '11' OR accountCat = '22' THEN

                IF accountNumber='01107000110' THEN 
  SET ledger_balanceNow = @previousBalance - currentadjustment;
                ELSE

                    SET ledger_balanceNow = @previousBalance + currentadjustment;

END IF;
                WHEN accountCat = '33' OR accountCat = '44'OR accountCat = '55' THEN
              SET ledger_balanceNow = @previousBalance - currentadjustment;
            END CASE;
        ELSEIF txnTypeIn LIKE '%Cr%' THEN
            CASE 
                WHEN accountCat = '11' OR accountCat = '22' THEN
    IF accountNumber='01107000110' THEN 
  SET ledger_balanceNow = @previousBalance + currentadjustment;
    ELSE

                SET ledger_balanceNow = @previousBalance - currentadjustment;

                END IF;
              WHEN accountCat = '33' OR accountCat = '44'OR accountCat = '55' THEN
           SET ledger_balanceNow = @previousBalance + currentadjustment;
  
            END CASE;
        END IF;

        

        SET @UPDATEledger = CONCAT('UPDATE ', accountTable, ' SET ledger_balance = ', ledger_balanceNow, ' WHERE trn_id = ', startingId);
        PREPARE stmt FROM @UPDATEledger;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        IF counterAccount=1 THEN
SET initial_ledgerBalance=ledger_balanceNow,initial_previousBal=@previousBalance;
        END IF;


        SELECT accountNumber, currentadjustment, @previousBalance, ledger_balanceNow,initial_ledgerBalance,initial_previousBal, startingId;

        IF counterAccount=lastId THEN
UPDATE account_created_store SET running_balance=ledger_balanceNow WHERE account_number=accountNumber;
        END IF;


        SET startingId = startingId + 1,counterAccount=counterAccount+1;

        -- SET  @previousBalance=NULL;
    END WHILE;


 -- Get the last transaction ID for master table
    SET @sql = CONCAT('SELECT MAX(trn_id) INTO @lastMasterId FROM ', masterTable);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    SET lastMasterId = @lastMasterId;
    SET counterMaster=1;
    -- Loop through the master table records without cursor
    WHILE startingMasterId <= lastMasterId DO
       

        SET @master = CONCAT('SELECT master_balance INTO @previousMaster FROM ', masterTable, ' WHERE trn_id = (SELECT MAX(trn_id) FROM ', masterTable, ' WHERE trn_id < ', startingMasterId, ')');
        -- select  @master;
        PREPARE stmt FROM @master;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;



               SET @currentmaster = CONCAT('SELECT master_balance INTO @currentMaster FROM ', masterTable, ' WHERE trn_id = ', startingMasterId);
              --  select @currentmaster;
        PREPARE stmt FROM @currentmaster;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;


        

        SET accountCat = SUBSTR(accountNumber, 2, 2);

        SELECT accountNumber,accountCat,txnType;
IF counterMaster=1 THEN

IF txnType LIKE '%Dr%' THEN
            CASE 
                WHEN accountCat = '11' OR accountCat = '22' THEN
                       IF accountNumber='01107000110' THEN 
SET adjustment=-adjustment;
                       ELSE
SET adjustment=adjustment;
END IF;
                WHEN accountCat = '33' OR accountCat = '44'OR accountCat = '55' THEN
            
SET adjustment=-adjustment;
            END CASE;

        ELSEIF txnType LIKE '%Cr%' THEN
            CASE 
                WHEN accountCat = '11' OR accountCat = '22' THEN
                 IF accountNumber='01107000110' THEN 
             SET adjustment=adjustment;
             ELSE 
   SET adjustment=-adjustment;
             END IF;
              WHEN accountCat = '33' OR accountCat = '44'OR accountCat = '55' THEN
       SET adjustment=adjustment;
  
            END CASE;
        END IF;


        SET master_balanceNow = (@previousMaster - initial_previousBal) + initial_ledgerBalance;
ELSE
 SET master_balanceNow = @currentMaster+ adjustment;

END IF;

        SET @UPDATEmaster = CONCAT('UPDATE ', masterTable, ' SET account_balance = ', ledger_balanceNow, ', master_balance = ', master_balanceNow, ' WHERE trn_id = ', startingMasterId);
        PREPARE stmt FROM @UPDATEmaster;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        SELECT accountMasterNumber, adjustment,@previousMaster,@currentMaster,master_balanceNow, startingMasterId;

 

        SET @previousMaster=NULL;
        SET startingMasterId = startingMasterId + 1,counterMaster=counterMaster+1;
    END WHILE;


END$$

DELIMITER ;




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

	   




DROP TABLE IF EXISTS sms_disburse_messages;

CREATE TABLE sms_disburse_messages (
    message_content TEXT NOT NULL,
    message_status ENUM("1","2") NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO sms_disburse_messages (message_content, message_status) VALUES
('Hello, your loan has been disbursed. Thank you for choosing us.', '1');



-- CALL postingTxnsX(NULL,'2024-04-11','staff facilitation for april 2024 Processed on 11/04/2024 From Air Time Allowance1','2024-04-11','-','150000.0','5.853427876427996E7','02230000110',' AIR TIME','0002','BTN0','GenXX','10000','11:51:53','2','01123000110','01123000010','Cr','Main','NA');


-- CALL postingTxnsX(NULL,'2024-04-11','Loan payament from Rweheyo Crispus  Processed on 11/04/2024\n  From Cash At Hand','2024-04-11','-','2941186.0','5.853427876427996E7','05502035410',' RWEHAYO CHRISPUS','0002','BTN45016','GenXX','10000','11:51:53','2','01128000110','01128000010','Cr','Main','NA');


-- CALL postingTxnsX(NULL,'2024-04-09','Centenary Banks Bank Deposit Processed on 09/04/2024\n  From Cash At Hand','2024-04-09','-','300000.0','5.853427876427996E7','01122000110','Cash At Hand','0002','BTN45003','GenXX','10000','11:51:53','2','01123000110','01123000010','Cr','Main','NA');



-- CALL postingTxnsX(NULL,'2023-12-31','Being an adjustment to correct overstated depreciation for PPE Processed on 31/12/2023
--   From Retained Earnings','2023-12-31','1390000.0','-','2.393192E7','04408000110','Retained Earnings','0002','BTN44958','GenXX','10000','13:44:46','8','01107000110','01107000010','Dr','Main','NA');

-- CALL postingTxnsX(NULL,'2023-12-31','Being an adjustment to correct overstated depreciation for PPE Processed on 31/12/2023
--   From Retained Earnings','2023-12-31','-','1390000.0','5.65199574E8','01107000110','Accumulated Depreciation Assets','0002','BTN44958','GenXX','10000','13:44:46','9','04408000110','04408000010','Cr','Main','NA');



--  CALL postingTxnsX(NULL,'2023-12-31','Retained Earnings Dated29/04/2024 Processed on 31/12/2023
--   From Revenue Reserves','2023-12-31','2.9424757E7','-','5.35774817E8','04408000210','Revenue Reserves','0002','BTN44959','GenXX','10002','07:02:42','2','04408000110','04408000010','Dr','Main','NA');

--   CALL postingTxnsX(NULL,'2023-12-31','Retained Earnings Dated29/04/2024 Processed on 31/12/2023
--   From Revenue Reserves','2023-12-31','-','2.9424757E7','2.9424757E7','04408000110','Retained Earnings','0002','BTN44959','GenXX','10002','07:02:42','3','04408000210','04408000010','Cr','Main','NA');

-- CALL postingTxnsX(NULL,'2024-12-12',' Loan Payment from ARINAITWE DIDASs   loan repayment. Paid on 12/12/2024','2024-12-12','-','2','0','05502002810','ARINAITWE DIDAS','0002','BTN42114','GenXX','10006','14:28:43','551','01128000110','01128000010','Cr','Main','NA');



-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS normaliseIndividualBalanceBank;

-- Change the delimiter to avoid conflicts with semicolon in the procedure
DELIMITER $$

-- Create a new procedure
CREATE PROCEDURE normaliseIndividualBalanceBank(IN trnIdInd INT)
READS SQL DATA
BEGIN
    -- Declaration of variables
    DECLARE theBatchNoS VARCHAR(60);
    DECLARE theOpeningBal, theBal, thePaid, InterestPaid, principalPaid, 
            AccumInterestPaid, PenaltyPaid, priBal, intBal, accumIntBal, 
            loanPenBal, loanBal DOUBLE; 
    DECLARE innerNotFound INTEGER DEFAULT 0; 

    -- Cursor for selecting BatchCode from a table based on the input transaction ID
    DECLARE forBatchNos CURSOR FOR 
        SELECT BatchCode 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = trnIdInd ;

    -- Continue handler for cursor not finding a row
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound = 1;

    -- Open the cursor
    OPEN forBatchNos; 

    -- Initialize local variables
    SELECT COUNT(ExpectedTotalAmount) INTO @No 
    FROM loandisburserepaystatement 
    WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

    -- Check if there is exactly one row with ExpectedTotalAmount > 0
    IF @No = 1 THEN
        SELECT ExpectedTotalAmount INTO theOpeningBal 
        FROM loandisburserepaystatement 
        WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

        -- Loop for processing batch numbers
        TXNIDS_LOOP: LOOP
            FETCH forBatchNos INTO theBatchNoS;
            SET thePaid = NULL, InterestPaid = NULL, principalPaid = NULL;
SELECT  theBatchNoS;
            -- Exit the loop if no more rows found
            IF innerNotFound = 1 THEN
                LEAVE TXNIDS_LOOP;
            END IF;

            -- Fetch payments, interest and principal from different accounts
            SELECT debit INTO thePaid 
            FROM bsanca01122000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO InterestPaid 
            FROM bsanca03301000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO principalPaid 
            FROM bsanca01128000110 
            WHERE chq_number = theBatchNoS;

            -- Check and set default values for null or unset variables
            IF thePaid = '-' OR ISNULL(thePaid) THEN
                SET thePaid = 0.0;
            END IF;

            IF InterestPaid = '-' OR ISNULL(InterestPaid) THEN
                SET InterestPaid = 0.0;
            END IF;

            IF principalPaid = '-' OR ISNULL(principalPaid) THEN
                SET principalPaid = 0.0;
            END IF;


                   IF thePaid>0 THEN 
            -- Update the statement with calculated values
            UPDATE loandisburserepaystatement 
            SET AmountPaid = thePaid, PrincipalPaid = principalPaid, 
                InterestPaid = InterestPaid, LoanBalance = theOpeningBal - thePaid 
            WHERE BatchCode = theBatchNoS;

            -- Adjust the opening balance for the next iteration
            SET theOpeningBal = theOpeningBal - thePaid;
END IF;

            -- Reset the not found handler flag
            SET innerNotFound = 0;
        END LOOP TXNIDS_LOOP; 
    END IF;

    -- Close the cursor
    CLOSE forBatchNos; 

END $$

-- Reset the delimiter to its default value
DELIMITER ;



-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS normaliseIndividualBalanceCash;

-- Change the delimiter to avoid conflicts with semicolon in the procedure
DELIMITER $$

-- Create a new procedure
CREATE PROCEDURE normaliseIndividualBalanceCash(IN trnIdInd INT)
MOFIFIES SQL DATA
BEGIN
    -- Declaration of variables
    DECLARE theBatchNoS VARCHAR(60);
    DECLARE theOpeningBal, theBal, thePaid, InterestPaid, principalPaid, 
            AccumInterestPaid, PenaltyPaid, priBal, intBal, accumIntBal, 
            loanPenBal, loanBal DOUBLE; 
    DECLARE innerNotFound INTEGER DEFAULT 0; 

    -- Cursor for selecting BatchCode from a table based on the input transaction ID
    DECLARE forBatchNos CURSOR FOR 
        SELECT BatchCode 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = trnIdInd ;

    -- Continue handler for cursor not finding a row
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound = 1;

    -- Open the cursor
    OPEN forBatchNos; 

    -- Initialize local variables
    SELECT COUNT(ExpectedTotalAmount) INTO @No 
    FROM loandisburserepaystatement 
    WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

    -- Check if there is exactly one row with ExpectedTotalAmount > 0
    IF @No = 1 THEN
        SELECT ExpectedTotalAmount INTO theOpeningBal 
        FROM loandisburserepaystatement 
        WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

        -- Loop for processing batch numbers
        TXNIDS_LOOP: LOOP
            FETCH forBatchNos INTO theBatchNoS;
            SET thePaid = NULL, InterestPaid = NULL, principalPaid = NULL;
SELECT  theBatchNoS;
            -- Exit the loop if no more rows found
            IF innerNotFound = 1 THEN
                LEAVE TXNIDS_LOOP;
            END IF;

            -- Fetch payments, interest and principal from different accounts
            SELECT debit INTO thePaid 
            FROM bsanca01123000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO InterestPaid 
            FROM bsanca03301000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO principalPaid 
            FROM bsanca01128000110 
            WHERE chq_number = theBatchNoS;

            -- Check and set default values for null or unset variables
            IF thePaid = '-' OR ISNULL(thePaid) THEN
                SET thePaid = 0.0;
            END IF;

            IF InterestPaid = '-' OR ISNULL(InterestPaid) THEN
                SET InterestPaid = 0.0;
            END IF;

            IF principalPaid = '-' OR ISNULL(principalPaid) THEN
                SET principalPaid = 0.0;
            END IF;


                   IF thePaid>0 THEN 
            -- Update the statement with calculated values
            UPDATE loandisburserepaystatement 
            SET AmountPaid = thePaid, PrincipalPaid = principalPaid, 
                InterestPaid = InterestPaid, LoanBalance = theOpeningBal - thePaid 
            WHERE BatchCode = theBatchNoS;

            -- Adjust the opening balance for the next iteration
            SET theOpeningBal = theOpeningBal - thePaid;
END IF;

            -- Reset the not found handler flag
            SET innerNotFound = 0;
        END LOOP TXNIDS_LOOP; 
    END IF;

    -- Close the cursor
    CLOSE forBatchNos; 

END $$

-- Reset the delimiter to its default value
DELIMITER ;





-- CALL normaliseIndividualBalance(71706);




DROP PROCEDURE IF EXISTS normaliseBalancesOnly;
DELIMITER $$

CREATE PROCEDURE normaliseBalancesOnly(IN theLoanTrnId INT)
BEGIN
    DECLARE l_done INT DEFAULT 0;
    DECLARE theTrnIdNow INT;
    DECLARE previousBalance DECIMAL(10,2);
    DECLARE theAmountPaid DECIMAL(10,2);
    DECLARE newBalance DECIMAL(10,2);

    DECLARE forTrnId CURSOR FOR 
        SELECT TrnId 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = theLoanTrnId 
        ORDER BY TrnId ASC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    START TRANSACTION;

    -- Get the initial previous balance from the first row
    SELECT LoanBalance 
    INTO previousBalance 
    FROM loandisburserepaystatement 
    WHERE loanTrnId = theLoanTrnId 
    ORDER BY TrnId ASC 
    LIMIT 1;

    OPEN forTrnId;

    -- Skip the first row as it should not be updated
    FETCH forTrnId INTO theTrnIdNow;

    loanId_loop: LOOP
        FETCH forTrnId INTO theTrnIdNow;

        IF l_done = 1 THEN
            LEAVE loanId_loop;
        END IF;

        -- Get the amount paid for the current transaction ID
        SELECT CAST(AmountPaid AS DECIMAL(10,2)) 
        INTO theAmountPaid 
        FROM loandisburserepaystatement 
        WHERE TrnId = theTrnIdNow;

        -- Calculate the new balance
        SET newBalance = previousBalance - theAmountPaid;

        -- Update the loan balance with the new balance
        UPDATE loandisburserepaystatement 
        SET LoanBalance = newBalance 
        WHERE TrnId = theTrnIdNow;

        -- Update the previous balance for the next iteration
        SET previousBalance = newBalance;
    END LOOP loanId_loop;

    CLOSE forTrnId;

    COMMIT;

    -- Optional: Select the updated table for verification
    SELECT * FROM loandisburserepaystatement WHERE loanTrnId = theLoanTrnId;

END$$

DELIMITER ;


-- CALL normaliseIndividualBalanceBank(71706);
-- CALL normaliseIndividualBalanceBank(71706);
-- CALL normaliseBalancesOnly(71706);



-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS callAllBalanceProcedures;

-- Create a new procedure
DELIMITER $$
CREATE PROCEDURE callAllBalanceProcedures(IN trnId INT)
BEGIN
    -- Calling the first procedure
    CALL normaliseIndividualBalanceBank(trnId);

    -- Calling the second procedure
    CALL normaliseIndividualBalanceCash(trnId);

    -- Calling the third procedure
UPDATE loandisburserepaystatement SET LoanBalance=PrincipalBalance+InterestBalance where loanTrnId=trnId;
END$$

-- Reset the delimiter to its default value
DELIMITER ;


-- CALL callAllBalanceProcedures(71707);







DROP PROCEDURE IF EXISTS normaliseIncompleteRenewedLoans;
DELIMITER $$

CREATE PROCEDURE normaliseIncompleteRenewedLoans(IN theLoanTrnId INT)
BEGIN
    DECLARE l_done INT DEFAULT 0;
    DECLARE theTrnIdNow INT;
    DECLARE previousBalance DECIMAL(10,2);
    DECLARE theAmountPaid DECIMAL(10,2);
    DECLARE newBalance DECIMAL(10,2);

    DECLARE forTrnId CURSOR FOR 
        SELECT TrnId 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = theLoanTrnId 
        ORDER BY TrnId ASC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    START TRANSACTION;

    -- Get the initial previous balance from the first row
    SELECT LoanBalance 
    INTO previousBalance 
    FROM loandisburserepaystatement 
    WHERE loanTrnId = theLoanTrnId 
    ORDER BY TrnId ASC 
    LIMIT 1;

    OPEN forTrnId;

    -- Skip the first row as it should not be updated
    FETCH forTrnId INTO theTrnIdNow;

    loanId_loop: LOOP
        FETCH forTrnId INTO theTrnIdNow;

        IF l_done = 1 THEN
            LEAVE loanId_loop;
        END IF;

        -- Get the amount paid for the current transaction ID
        SELECT CAST(AmountPaid AS DECIMAL(10,2)) 
        INTO theAmountPaid 
        FROM loandisburserepaystatement 
        WHERE TrnId = theTrnIdNow;

        -- Calculate the new balance
        SET newBalance = previousBalance - theAmountPaid;

        -- Update the loan balance with the new balance
        UPDATE loandisburserepaystatement 
        SET LoanBalance = newBalance 
        WHERE TrnId = theTrnIdNow;

        -- Update the previous balance for the next iteration
        SET previousBalance = newBalance;
    END LOOP loanId_loop;

    CLOSE forTrnId;

    COMMIT;

    -- Optional: Select the updated table for verification
    SELECT * FROM loandisburserepaystatement WHERE loanTrnId = theLoanTrnId;

END$$

-- Reset the delimiter to its default value
DELIMITER ;




ALTER TABLE sequencenumbers
ADD COLUMN officerReportingNo int(11) DEFAULT 11;



ALTER TABLE sequencenumbers
ADD COLUMN optionalGaurantorCapture int(11) DEFAULT 11;




ALTER TABLE sequencenumbers ADD COLUMN remoteCollectionsWithTerminal int(11) DEFAULT 11;



 CREATE TABLE `sequencenumbersnew` (
  `trn_id` enum('1') NOT NULL,
   `remoteCollectionsWithTerminal` int(11) DEFAULT '11',
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO sequencenumbersnew (trn_id, remoteCollectionsWithTerminal)
VALUES ('1', 0);


ALTER TABLE sequencenumbersnew
ADD COLUMN sendPortfolioSummuryReport int(11) DEFAULT 11;

ALTER TABLE sequencenumbersnew
ADD COLUMN sendExpensesReport int(11) DEFAULT 11;

ALTER TABLE sequencenumbersnew
ADD COLUMN sendLoansDisbursedReport int(11) DEFAULT 11;



DELETE FROM specialgroups;

INSERT INTO specialgroups (
  trn_date, group_id, group_name, account_name, account_number, contact_one, contact_two, 
  email_one, email_two, Other_One, Other_Two, Other_Three
) VALUES (
  '2024-09-26', 1, 'Sample Group', 'Sample Account', '123456789', 'Contact1', 'Contact2', 
  'email1@example.com', 'email2@example.com', 'Other1', 'Other2', 'Other3'
);


UPDATE specialgroups SET group_id=750496605;



SELECT * FROM specialgroups;


/* 

SELECT 
    nl.trn_id,
    nl.loan_id,
    nl.applicant_account_name,
    g.id AS gaurantor_id,
    g.gaurantorsName,
    g.gaurantorsContact1,
    g.gaurantorsContact2,
    g.gaurantorsIdNumber,
    g.gaurantorsRelationWithBorrower,
    g.gaurantorsHomeArea,
    g.gaurantorsBusiness,
    g.loanTrnId
FROM 
    new_loan_appstore nl
LEFT JOIN 
    gaurantors g 
ON 
    nl.trn_id = g.loanTrnId
WHERE 
    nl.loan_cycle_status IN ('Disbursed', 'Renewed') INTO OUTFILE 'loansWithG.sql' FIELDS TERMINATED BY '#' LINES TERMINATED BY '\n';
 */




-- DELETE new_loan_appstore,new_loan_appstoreamort from new_loan_appstore INNER JOIN new_loan_appstoreamort ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE new_loan_appstore.loan_cycle_status='Completed' AND new_loan_appstore.trn_date<='2024-03-31';

-- DELETE pmms_loans.new_loan_appstore1,pmms.loandisburserepaystatement from pmms_loans.new_loan_appstore1 INNER JOIN pmms.loandisburserepaystatement ON new_loan_appstore1.trn_id=loandisburserepaystatement.loanTrnId WHERE new_loan_appstore1.loan_cycle_status='Completed' AND new_loan_appstore1.trn_date<='2024-03-31';





-- Help me with a comprehensive structure/modules for a simple enterprise resource planning web and mobile application that has a production, planning and control module and accounting module. Also help me with the necessary workflows involved in each module and the accounting entries required at each stage


-- I want an ERP boss not just PPC AND ACCOUNTING SYSTEM ONLY

-- Also help me with the necessary workflows involved in each module and the accounting entries required at each stage


-- List for me the transaction documents involved in each module, e.g invoice scm and the reports expected


-- Give me a more comprehensive List for me the transaction documents involved in each module, e.g invoice scm and the reports expected


-- I kindly request for more comprehensive, kindly steadily but slowyly take your time and give me  more comprehensive List for me the transaction documents involved in each module, e.g invoice scm and the reports expected


-- What could be the data captured for each module, kindly give me comprehensive list


-- Other than the normal forms, what could be the other data sources?


-- Finally give me a detailed data model with the relevant data relationship , sql based

-- Debits	Merchant Payments		Merchant Payments	0



DROP PROCEDURE IF EXISTS agingAnalysisXX;

DELIMITER $$

CREATE PROCEDURE agingAnalysisXX()
BEGIN
    DECLARE l_done INT DEFAULT 0;
    DECLARE loanId VARCHAR(45);
    DECLARE customerName VARCHAR(60);
    DECLARE customerContactNumber VARCHAR(60);
    DECLARE TrnDate DATE;
    DECLARE princeremain DOUBLE DEFAULT 0;
    DECLARE interestRem DOUBLE DEFAULT 0;
    DECLARE arrears INT DEFAULT 0;
    DECLARE loanCycleStatus VARCHAR(45);
    DECLARE numberOfGaurantors INT DEFAULT 0;
    DECLARE gaurantorName1 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorContact1 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorName2 VARCHAR(60) DEFAULT '-';
    DECLARE gaurantorContact2 VARCHAR(60) DEFAULT '-';

    -- Cursor Declaration
    DECLARE loan_cursor CURSOR FOR
        SELECT loan_id 
        FROM new_loan_appstore 
        WHERE loan_cycle_status IN ('Disbursed', 'Renewed');

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done = 1;

    -- Temporary Table Creation
    DROP TEMPORARY TABLE IF EXISTS aging_loan_analysis;
    CREATE TEMPORARY TABLE aging_loan_analysis (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        customer_name VARCHAR(60),
        customer_contact VARCHAR(60),
        date_taken DATE,
        loans_remaining DOUBLE,
        principal_remaining DOUBLE,
        interest_remaining DOUBLE,
        principal_inarrears DOUBLE DEFAULT 0,
        interest_inarrears DOUBLE DEFAULT 0,
        number_of_days_in_arrears INT,
        loan_deadline DATE,
        gaurantor_name1 VARCHAR(60),
        gaurantor_contact1 VARCHAR(60),
        gaurantor_name2 VARCHAR(60),
        gaurantor_contact2 VARCHAR(60),
        loan_status VARCHAR(45)
    );

    -- Open Cursor
    OPEN loan_cursor;

    loan_loop: LOOP
        FETCH loan_cursor INTO loanId;
        IF l_done THEN
            LEAVE loan_loop;
        END IF;

        -- Fetch Loan Details
        SELECT 
            pl.applicant_account_name, 
            m.mobile1, 
            pl.instalment_start_date, 
            pl.TotalPrincipalRemaining, 
            pl.TotalInterestRemaining, 
            pl.loan_cycle_status
        INTO 
            customerName, 
            customerContactNumber, 
            TrnDate, 
            princeremain, 
            interestRem, 
            loanCycleStatus
        FROM pmms.master m
        INNER JOIN pmms_loans.new_loan_appstore pl
            ON m.account_number = pl.applicant_account_number
        WHERE pl.loan_id = loanId;

        -- Calculate Arrears
        SELECT 
            SUM(PrincipalRemaining + InterestRemaining), 
            COUNT(*)
        INTO 
            arrears
        FROM new_loan_appstoreamort 
        WHERE master2_id = loanId 
          AND instalment_due_date <= CURDATE() 
          AND instalment_status != 'P';

        -- Fetch Gaurantor Details
        SELECT COUNT(*) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId = loanId;

        IF numberOfGaurantors = 1 THEN
            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName1, gaurantorContact1 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            LIMIT 1;
        ELSEIF numberOfGaurantors = 2 THEN
            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName1, gaurantorContact1 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            ORDER BY id ASC 
            LIMIT 1;

            SELECT gaurantorsName, gaurantorsContact1 
            INTO gaurantorName2, gaurantorContact2 
            FROM gaurantors 
            WHERE loanTrnId = loanId 
            ORDER BY id DESC 
            LIMIT 1;
        ELSE
            SET gaurantorName1 = '-', gaurantorContact1 = '-', gaurantorName2 = '-', gaurantorContact2 = '-';
        END IF;

        -- Insert into Temporary Table
        INSERT INTO aging_loan_analysis (
            customer_name,
            customer_contact,
            date_taken,
            loans_remaining,
            principal_remaining,
            interest_remaining,
            principal_inarrears,
            interest_inarrears,
            number_of_days_in_arrears,
            loan_deadline,
            gaurantor_name1,
            gaurantor_contact1,
            gaurantor_name2,
            gaurantor_contact2,
            loan_status
        ) VALUES (
            customerName,
            customerContactNumber,
            TrnDate,
            princeremain + interestRem,
            princeremain,
            interestRem,
            0, -- principal_inarrears (calculated later)
            0, -- interest_inarrears (calculated later)
            arrears,
            DATE_ADD(TrnDate, INTERVAL 30 DAY), -- loan_deadline
            gaurantorName1,
            gaurantorContact1,
            gaurantorName2,
            gaurantorContact2,
            loanCycleStatus
        );
    END LOOP;

    CLOSE loan_cursor;

    -- Categorization and Final Output
    SELECT 
        id,
        customer_name,
        customer_contact,
        date_taken,
        loans_remaining,
        principal_remaining,
        interest_remaining,
        principal_inarrears,
        interest_inarrears,
        number_of_days_in_arrears,
        loan_deadline,
        gaurantor_name1,
        gaurantor_contact1,
        gaurantor_name2,
        gaurantor_contact2,
        CASE
            WHEN number_of_days_in_arrears < 30 THEN 'Performing Portfolio'
            WHEN number_of_days_in_arrears BETWEEN 30 AND 59 THEN 'Portfolio at Risk (30-60 days)'
            WHEN number_of_days_in_arrears BETWEEN 60 AND 89 THEN 'Portfolio at Risk (60-90 days)'
            WHEN number_of_days_in_arrears BETWEEN 90 AND 359 THEN 'Non-Performing Portfolio'
            ELSE 'Portfolio Due for Write-Off'
        END AS classification
    FROM aging_loan_analysis
    ORDER BY number_of_days_in_arrears ASC;
END$$

DELIMITER ;





ALTER TABLE backupcontact
  ADD COLUMN notify_expense_report         TINYINT(1) NOT NULL DEFAULT 0,
  ADD COLUMN notify_officer_report         TINYINT(1) NOT NULL DEFAULT 0,
  ADD COLUMN notify_loans_disbursed_report TINYINT(1) NOT NULL DEFAULT 0,
  ADD COLUMN notify_portfolio_summary      TINYINT(1) NOT NULL DEFAULT 0;


  UPDATE backupcontact
  SET notify_expense_report = 1, notify_officer_report = 1,notify_loans_disbursed_report = 1, notify_portfolio_summary=1;


CREATE TABLE `smstable_deposit_log` (
  `deposit_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `password_used` varchar(10) NOT NULL,
  `logged_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`deposit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE smstable_deposit_log
ADD COLUMN password_used VARCHAR(10) NOT NULL AFTER quantity;



/* remember the month (YYYYMM) we last verified payment */
ALTER TABLE the_company_datails
  ADD COLUMN licence_verified_ym CHAR(6) NULL
      COMMENT 'YYYYMM of the most recent PAID verification';
UPDATE the_company_datails SET licence_verified_ym= 202505;



-- 1) Switch table to utf8mb4 (full Unicode)
ALTER TABLE smsmanagement CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 2) Widen message + reasons (or use TEXT if you prefer)
ALTER TABLE smsmanagement
  MODIFY SmsMessage        VARCHAR(700)  NOT NULL,
  MODIFY SmsSentReason     VARCHAR(255)  NOT NULL,
  MODIFY SmsDeliveryReason VARCHAR(255)  NOT NULL,
  MODIFY SmsOther2         VARCHAR(255)  NOT NULL,
  MODIFY SmsOther3         VARCHAR(255)  NOT NULL,
  MODIFY SmsOther4         VARCHAR(255)  NOT NULL;

-- (Optional) remove odd defaults so you don't accidentally store placeholder text
ALTER TABLE smsmanagement
  ALTER SmsSenToName DROP DEFAULT,
  ALTER SmsSenToNumber DROP DEFAULT,
  ALTER SmsMessage DROP DEFAULT,
  ALTER SmsSentStatus DROP DEFAULT,
  ALTER SmsSentReason DROP DEFAULT,
  ALTER SmsDeliveryStatus DROP DEFAULT,
  ALTER SmsDeliveryReason DROP DEFAULT,
  ALTER SmsOriginator DROP DEFAULT,
  ALTER SmsOther2 DROP DEFAULT,
  ALTER SmsOther3 DROP DEFAULT,
  ALTER SmsOther4 DROP DEFAULT;


CREATE TABLE roles (
  role_id INT AUTO_INCREMENT PRIMARY KEY,
  role_code VARCHAR(64) UNIQUE NOT NULL,
  role_name VARCHAR(128) NOT NULL,
  is_active TINYINT(1) DEFAULT 1
);

CREATE TABLE permissions (
  permission_id INT AUTO_INCREMENT PRIMARY KEY,
  permission_code VARCHAR(128) UNIQUE NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE role_permissions (
  role_id INT NOT NULL,
  permission_id INT NOT NULL,
  is_allowed TINYINT(1) DEFAULT 1,
  PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user_roles (
  user_id VARCHAR(64) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id)
);
INSERT INTO permissions (permission_code, description) VALUES
('can_start_day_auto','Auto SOD from login'),
('can_manual_start_day','Manual SOD from CreateNewStaff');

-- AUTO SOD users
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.role_id, p.permission_id
FROM roles r, permissions p
WHERE p.permission_code='can_start_day_auto'
AND r.role_code IN ('ACCOUNTANT','CASHIER','SUPERVISOR');

-- MANUAL SOD users
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.role_id, p.permission_id
FROM roles r, permissions p
WHERE p.permission_code='can_manual_start_day'
AND r.role_code IN ('SYSTEM_ADMIN','DATE_STARTER');
DELIMITER $$

CREATE PROCEDURE get_user_permissions(IN p_user_id VARCHAR(64))
BEGIN
  START TRANSACTION;

  SELECT p.permission_code
  FROM user_roles ur
  JOIN role_permissions rp ON ur.role_id = rp.role_id AND rp.is_allowed = 1
  JOIN permissions p ON p.permission_id = rp.permission_id
  WHERE ur.user_id = p_user_id;

  COMMIT;
END$$

DELIMITER ;




CALL savingsStatementDetails(
  '05502008510',
  '2025-01-01',
  '2025-11-30'
)\G;

DROP PROCEDURE IF EXISTS savingsPrintingDetails;
DELIMITER ##

CREATE PROCEDURE savingsPrintingDetails(
    IN p_account_number VARCHAR(100),
    IN p_from_date DATE,
    IN p_to_date DATE
)
BEGIN
    DECLARE v_opening_balance     DECIMAL(18,2) DEFAULT 0;
    DECLARE v_closing_balance     DECIMAL(18,2) DEFAULT 0;
    DECLARE v_total_deposits      DECIMAL(18,2) DEFAULT 0;
    DECLARE v_total_withdrawals   DECIMAL(18,2) DEFAULT 0;

    DECLARE v_company_name        VARCHAR(60);
    DECLARE v_company_branch      VARCHAR(60);
    DECLARE v_company_box_number  VARCHAR(60);
    DECLARE v_office_number       VARCHAR(60);
    DECLARE v_customer_name       VARCHAR(60);

    DECLARE v_message_text VARCHAR(255);

    /* Basic error handling for MySQL 5.5 */
    DECLARE EXIT HANDLER FOR 1452
    BEGIN
        ROLLBACK;
        SELECT 'REF_ER' AS theMessage,
               'Referenced field does not exist (1452) in savingsPrintingDetails' AS MESSAGE;
    END;

    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        ROLLBACK;
        SELECT 'ER_DUP_ENTRY' AS theMessage,
               'Duplicate entry not permitted (1062) in savingsPrintingDetails' AS MESSAGE;
    END;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'OTHER_ER' AS theMessage,
               'Unexpected error in savingsPrintingDetails' AS MESSAGE;
    END;

    START TRANSACTION;

    /* Company header (adapt table/column names if different) */
    SELECT the_company_name,
           the_company_branch,
           the_company_box_number,
           office_number
    INTO  v_company_name,
          v_company_branch,
          v_company_box_number,
          v_office_number
    FROM the_company_datails
    LIMIT 1;

    /* Customer name – use your existing helper or member table */
    SET v_customer_name = customerNameL(p_account_number);

    /* Opening balance before from_date (cast from VARCHAR to DECIMAL) */
    SELECT IFNULL(CAST(SavingsRunningBalance AS DECIMAL(18,2)),0)
    INTO   v_opening_balance
    FROM   newsavingsmembers
    WHERE  AccountNumber = p_account_number
      AND  TrnDate < p_from_date
    ORDER BY TrnDate DESC, TrnId DESC
    LIMIT 1;

    /* Totals in [from_date, to_date] (cast to DECIMAL) */
    SELECT
        IFNULL(SUM(CAST(SavingsAdded   AS DECIMAL(18,2))),0),
        IFNULL(SUM(CAST(SavingsRemoved AS DECIMAL(18,2))),0)
    INTO
        v_total_deposits,
        v_total_withdrawals
    FROM newsavingsmembers
    WHERE AccountNumber = p_account_number
      AND TrnDate BETWEEN p_from_date AND p_to_date;

    /* Closing balance derived from opening + flows */
    SET v_closing_balance = v_opening_balance + v_total_deposits - v_total_withdrawals;

    /* Normalise NULLs */
    IF v_company_branch IS NULL THEN SET v_company_branch = '-'; END IF;
    IF v_company_box_number IS NULL THEN SET v_company_box_number = '-'; END IF;
    IF v_customer_name IS NULL THEN SET v_customer_name = '-'; END IF;

    /* Temp table for print header (similar idea to loanPrintDetailsRenew) */
    DROP TEMPORARY TABLE IF EXISTS savingsPrintDetails;

    CREATE TEMPORARY TABLE savingsPrintDetails(
        id_1              INT,
        company_name      VARCHAR(60),
        company_branch    VARCHAR(60),
        company_box_number VARCHAR(60),
        customer_name     VARCHAR(60),
        opening_balance   VARCHAR(60),
        closing_balance   VARCHAR(60),
        total_deposits    VARCHAR(60),
        total_withdrawals VARCHAR(60),
        account_number    VARCHAR(60),
        from_date         VARCHAR(20),
        to_date           VARCHAR(20),
        office_number     VARCHAR(60)
    );

    INSERT INTO savingsPrintDetails VALUES (
        1,
        v_company_name,
        v_company_branch,
        v_company_box_number,
        v_customer_name,
        FORMAT(v_opening_balance,0),
        FORMAT(v_closing_balance,0),
        FORMAT(v_total_deposits,0),
        FORMAT(v_total_withdrawals,0),
        p_account_number,
        DATE_FORMAT(p_from_date,'%d/%m/%Y'),
        DATE_FORMAT(p_to_date,'%d/%m/%Y'),
        v_office_number
    );

    /* Header result set */
    SELECT * FROM savingsPrintDetails;

    COMMIT;
END
##
DELIMITER ;

CALL savingsPrintingDetails(
  '05502008510',
  '2025-01-01',
  '2025-11-30'
)\G;

DROP PROCEDURE IF EXISTS savingsStatementDetails;
DELIMITER ##

CREATE PROCEDURE savingsStatementDetails(
    IN p_account_number VARCHAR(100),
    IN p_from_date DATE,
    IN p_to_date DATE
)
BEGIN
    DECLARE v_message_text VARCHAR(255);

    DECLARE EXIT HANDLER FOR 1452
    BEGIN
        ROLLBACK;
        SELECT 'REF_ER' AS theMessage,
               'Referenced field does not exist (1452) in savingsStatementDetails' AS MESSAGE;
    END;

    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        ROLLBACK;
        SELECT 'ER_DUP_ENTRY' AS theMessage,
               'Duplicate entry not permitted (1062) in savingsStatementDetails' AS MESSAGE;
    END;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'OTHER_ER' AS theMessage,
               'Unexpected error in savingsStatementDetails' AS MESSAGE;
    END;

    START TRANSACTION;

    SELECT
        TrnId                                   AS trn_id,
        DATE_FORMAT(TrnDate,'%d/%m/%Y')        AS trn_date,
        FORMAT(CAST(SavingsAdded   AS DECIMAL(18,2)),0)   AS savings_added,
        FORMAT(CAST(SavingsRemoved AS DECIMAL(18,2)),0)   AS savings_removed,
        FORMAT(CAST(SavingsRunningBalance AS DECIMAL(18,2)),0) AS savings_running_balance,
        SavingsMonth,
        SavingsYear
    FROM newsavingsmembers
    WHERE AccountNumber = p_account_number
      AND TrnDate BETWEEN p_from_date AND p_to_date
    ORDER BY TrnDate, TrnId;

    COMMIT;
END
##
DELIMITER ;


CALL savingsStatementDetails(
  '05502008510',
  '2025-01-01',
  '2025-11-30'
)\G;


CREATE TABLE savings_minimum_config (
  config_id           INT NOT NULL AUTO_INCREMENT,
  is_active           TINYINT(1) NOT NULL DEFAULT 1,
  base_amount         DECIMAL(18,2) NOT NULL,          -- e.g. 10000.00
  increment_type      ENUM('FIXED','PERCENT') NOT NULL DEFAULT 'FIXED',
  increment_value     DECIMAL(18,4) NOT NULL,          -- e.g. 5000.00 or 5.00 (percent)
  schedule_mode       ENUM('CALENDAR','PER_MEMBER') NOT NULL DEFAULT 'CALENDAR',
  start_year          INT NOT NULL,                    -- e.g. 2025
  start_month         TINYINT(2) NOT NULL,             -- 1–12
  PRIMARY KEY (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE savings_minimum_schedule (
  schedule_id     INT NOT NULL AUTO_INCREMENT,
  period_year     INT NOT NULL,
  period_month    TINYINT(2) NOT NULL,
  required_amount DECIMAL(18,2) NOT NULL,
  generated_at    DATETIME NOT NULL,
  PRIMARY KEY (schedule_id),
  UNIQUE KEY uq_savings_minimum_period (period_year, period_month)
);



INSERT INTO savings_minimum_config (
  is_active,
  base_amount,
  increment_type,
  increment_value,
  schedule_mode,
  start_year,
  start_month
) VALUES (
  1,              -- active
  10000.00,       -- USER-DEFINED base minimum (UGX)
  'FIXED',        -- today: fixed increment
  5000.00,        -- USER-DEFINED monthly increment (UGX)
  'CALENDAR',     -- today: calendar-based
  2025,           -- USER-DEFINED start year
  1               -- USER-DEFINED start month (1=Jan)
);


CREATE EVENT ev_update_savings_minimum
ON SCHEDULE EVERY 1 MONTH
STARTS '2025-01-01 00:05:00'         -- adjust start date/time
DO
  CALL update_savings_minimum_schedule();

  SELECT
  n.AccountNumber,
  n.AccountName,
  n.SavingsYear,
  n.SavingsMonth,
  CAST(n.SavingsAdded AS DECIMAL(18,2)) AS actual_amount,
  s.required_amount,
  CAST(n.SavingsAdded AS DECIMAL(18,2)) - s.required_amount AS variance_month,
  CASE
    WHEN CAST(n.SavingsAdded AS DECIMAL(18,2)) < s.required_amount
      THEN 'BELOW'
    WHEN CAST(n.SavingsAdded AS DECIMAL(18,2)) = s.required_amount
      THEN 'AT'
    ELSE 'ABOVE'
  END AS status_month
FROM newsavingsmembers n
JOIN savings_minimum_schedule s
  ON s.period_year  = CAST(n.SavingsYear AS SIGNED)
 AND s.period_month = MONTH(STR_TO_DATE(n.SavingsMonth, '%M'));


 DELIMITER $$

DROP PROCEDURE IF EXISTS update_savings_minimum_schedule $$
CREATE PROCEDURE update_savings_minimum_schedule()
proc_main: BEGIN

  DECLARE v_base_amount       DECIMAL(18,2);
  DECLARE v_increment_type    VARCHAR(10);
  DECLARE v_increment_value   DECIMAL(18,4);
  DECLARE v_start_year        INT;
  DECLARE v_start_month       TINYINT;

  DECLARE v_last_year         INT;
  DECLARE v_last_month        TINYINT;
  DECLARE v_last_amount       DECIMAL(18,2);

  DECLARE v_next_year         INT;
  DECLARE v_next_month        TINYINT;
  DECLARE v_required_amount   DECIMAL(18,2);

  DECLARE v_current_year      INT;
  DECLARE v_current_month     TINYINT;

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'OTHER_ER' AS theMessage, 'A database error occurred' AS MESSAGE;
  END;

  START TRANSACTION;

  -- 1) Load configuration
  SELECT
    base_amount,
    increment_type,
    increment_value,
    start_year,
    start_month
  INTO
    v_base_amount,
    v_increment_type,
    v_increment_value,
    v_start_year,
    v_start_month
  FROM savings_minimum_config
  WHERE is_active = 1
  ORDER BY config_id DESC
  LIMIT 1;

  IF v_base_amount IS NULL THEN
    ROLLBACK;
    SELECT 'CONFIG_MISSING' AS theMessage, 'No active savings_minimum_config found' AS MESSAGE;
    LEAVE proc_main;
  END IF;

  SET v_current_year  = YEAR(CURDATE());
  SET v_current_month = MONTH(CURDATE());

  -- 2) Determine last schedule row
  SELECT
    period_year,
    period_month,
    required_amount
  INTO
    v_last_year,
    v_last_month,
    v_last_amount
  FROM savings_minimum_schedule
  ORDER BY period_year DESC, period_month DESC
  LIMIT 1;

  IF v_last_year IS NULL THEN
    SET v_next_year       = v_start_year;
    SET v_next_month      = v_start_month;
    SET v_required_amount = v_base_amount;
  ELSE
    SET v_next_year  = v_last_year;
    SET v_next_month = v_last_month;

    IF v_next_month = 12 THEN
      SET v_next_month = 1;
      SET v_next_year  = v_next_year + 1;
    ELSE
      SET v_next_month = v_next_month + 1;
    END IF;

    IF v_increment_type = 'FIXED' THEN
      SET v_required_amount = v_last_amount + v_increment_value;
    ELSE
      SET v_required_amount = v_last_amount * (1 + (v_increment_value / 100));
    END IF;
  END IF;

  -- 3) Insert months up to current month
  savings_min_loop: WHILE
    (v_next_year < v_current_year)
      OR (v_next_year = v_current_year AND v_next_month <= v_current_month)
  DO
    INSERT INTO savings_minimum_schedule (
      period_year,
      period_month,
      required_amount,
      generated_at
    ) VALUES (
      v_next_year,
      v_next_month,
      v_required_amount,
      NOW()
    );

    IF v_next_month = 12 THEN
      SET v_next_month = 1;
      SET v_next_year  = v_next_year + 1;
    ELSE
      SET v_next_month = v_next_month + 1;
    END IF;

    IF v_increment_type = 'FIXED' THEN
      SET v_required_amount = v_required_amount + v_increment_value;
    ELSE
      SET v_required_amount = v_required_amount * (1 + (v_increment_value / 100));
    END IF;

  END WHILE;

  COMMIT;
  SELECT 'SUCCESS' AS theMessage, 'Schedule updated' AS MESSAGE;

END $$

DELIMITER ;
CALL update_savings_minimum_schedule();


DELIMITER $$

DROP PROCEDURE IF EXISTS get_savings_monthly_variance $$
CREATE PROCEDURE get_savings_monthly_variance(
  IN p_year  INT,
  IN p_month TINYINT
)
proc_main: BEGIN
  DECLARE v_year  INT;
  DECLARE v_month TINYINT;

  -- Simple error handler for MySQL 5.5
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    SELECT 'OTHER_ER' AS theMessage,
           'A database error occurred while computing savings variance' AS MESSAGE;
  END;

  -- Default to current year/month if not provided or 0
  IF p_year IS NULL OR p_year = 0 THEN
    SET v_year = YEAR(CURDATE());
  ELSE
    SET v_year = p_year;
  END IF;

  IF p_month IS NULL OR p_month = 0 THEN
    SET v_month = MONTH(CURDATE());
  ELSE
    SET v_month = p_month;
  END IF;

  -- Feature flag: if there is no active config, treat as disabled/optional
  IF NOT EXISTS (
    SELECT 1
    FROM savings_minimum_config
    WHERE is_active = 1
  ) THEN
    SELECT 'FEATURE_DISABLED' AS theMessage,
           'Floating minimum savings tracking is disabled (no active config)' AS MESSAGE;
    LEAVE proc_main;
  END IF;

  -- Main result set: one row per member for that month
  SELECT
    n.AccountNumber                             AS account_number,
    n.AccountName                               AS account_name,
    n.SavingsYear                               AS savings_year,
    n.SavingsMonth                              AS savings_month,
    CAST(n.SavingsAdded AS DECIMAL(18,2))       AS actual_amount,
    s.required_amount,
    CAST(n.SavingsAdded AS DECIMAL(18,2)) - s.required_amount AS variance_month,
    CASE
      WHEN CAST(n.SavingsAdded AS DECIMAL(18,2)) < s.required_amount THEN 'BELOW'
      WHEN CAST(n.SavingsAdded AS DECIMAL(18,2)) = s.required_amount THEN 'AT'
      ELSE 'ABOVE'
    END AS status_month
  FROM newsavingsmembers n
  JOIN savings_minimum_schedule s
    ON s.period_year  = CAST(n.SavingsYear AS SIGNED)
   AND s.period_month = MONTH(STR_TO_DATE(n.SavingsMonth, '%M'))
  WHERE CAST(n.SavingsYear AS SIGNED) = v_year
    AND MONTH(STR_TO_DATE(n.SavingsMonth, '%M')) = v_month
  ORDER BY n.AccountNumber;

END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS update_savings_minimum_schedule;
DELIMITER $$

CREATE PROCEDURE update_savings_minimum_schedule()
proc_end: BEGIN
  DECLARE v_not_found TINYINT DEFAULT 0;

  DECLARE v_config_id INT;
  DECLARE v_base_amount DECIMAL(18,2);
  DECLARE v_increment_type VARCHAR(20);
  DECLARE v_increment_value DECIMAL(18,4);
  DECLARE v_schedule_mode VARCHAR(20);
  DECLARE v_start_year INT;
  DECLARE v_start_month INT;

  DECLARE v_i INT DEFAULT 0;
  DECLARE v_current_year INT;
  DECLARE v_current_month INT;
  DECLARE v_required_amount DECIMAL(18,2);

  /* Error handlers without GET DIAGNOSTICS */
  DECLARE EXIT HANDLER FOR 1452
  BEGIN
    ROLLBACK;
    SELECT 'REF_ER' AS theMessage,
           'Referenced field does not exist while updating savings minimum schedule.' AS MESSAGE;
  END;

  DECLARE EXIT HANDLER FOR 1062
  BEGIN
    ROLLBACK;
    SELECT 'ER_DUP_ENTRY' AS theMessage,
           'Duplicate entry not permitted while updating savings minimum schedule.' AS MESSAGE;
  END;

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SELECT 'OTHER_ER' AS theMessage,
           'Unexpected error while updating savings minimum schedule.' AS MESSAGE;
  END;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_not_found = 1;

  START TRANSACTION;

  SET v_not_found = 0;
  SELECT  config_id,
          base_amount,
          increment_type,
          increment_value,
          schedule_mode,
          start_year,
          start_month
    INTO  v_config_id,
          v_base_amount,
          v_increment_type,
          v_increment_value,
          v_schedule_mode,
          v_start_year,
          v_start_month
  FROM savings_minimum_config
  WHERE is_active = 1
  ORDER BY config_id DESC
  LIMIT 1
  FOR UPDATE;

  IF v_not_found = 1 OR v_config_id IS NULL THEN
    ROLLBACK;
    SELECT 'NO_ACTIVE' AS theMessage,
           'No active savings minimum config found.' AS MESSAGE;
    LEAVE proc_end;
  END IF;

  /* Rebuild schedule from scratch */
  DELETE FROM savings_minimum_schedule;

  SET v_current_year  = v_start_year;
  SET v_current_month = v_start_month;
  SET v_i = 0;

  /* Generate 12 months; adjust if you want more/less */
  WHILE v_i < 12 DO

    IF UPPER(v_increment_type) = 'FIXED' THEN
      -- month 0: base; month 1: base + inc; month 2: base + 2*inc; ...
      SET v_required_amount = v_base_amount + v_increment_value * v_i;

    ELSEIF UPPER(v_increment_type) = 'PERCENT' THEN
      -- Compounding percentage growth
      SET v_required_amount = v_base_amount * POW(1 + (v_increment_value / 100), v_i);

    ELSE
      -- Fallback: no increment
      SET v_required_amount = v_base_amount;
    END IF;

    INSERT INTO savings_minimum_schedule
            (period_year, period_month, required_amount, generated_at)
    VALUES  (v_current_year, v_current_month, v_required_amount, NOW());

    SET v_i = v_i + 1;
    SET v_current_month = v_current_month + 1;

    IF v_current_month > 12 THEN
      SET v_current_month = 1;
      SET v_current_year = v_current_year + 1;
    END IF;

  END WHILE;

  COMMIT;

  SELECT 'OK' AS theMessage, 'Schedule updated' AS MESSAGE;

END proc_end$$
DELIMITER ;



-- CALL postingTxnsX(NULL,'2025-12-31','Dividends Paid during period Dated31/12/2025 Processed on 31/12/2025\n  From Accounts Payable','2025-12-31','-','2.80463759E8','2.80463759E8','02274000110','Accounts Payable','0002','BTN41693','Gen','10002','08:03:22','14','05522000110','05522000010','Cr','Main','NA');



-- CALL postingTxnsX(NULL,DATE(NOW()),the_narration,DATE(NOW()),txnAmount,'-',@ledgerBalNow,the_contra_account_no,@accountName,'0002',batchNumber,"Reversal",'10001',TIME(NOW()),'2',the_account_no,@masterAccount,DrCr,'Main','NA');