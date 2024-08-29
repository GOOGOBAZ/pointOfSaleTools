DROP PROCEDURE IF EXISTS createdRenewedLoan;

DELIMITER $$

CREATE  PROCEDURE createdRenewedLoan (
  IN accountNumber VARCHAR(60),
  IN amount DOUBLE,
  IN rate DOUBLE,
  IN tenure DOUBLE,
  IN theLastTransactionDate DATE,
  IN periodTypeNumber DOUBLE,
  IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN initialDisburseDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT,IN renewals INT,IN theDisId INT)
BEGIN
DECLARE theLoanTxnId,timeP,timeP1 INT;
DECLARE theLoanId VARCHAR(60);








START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;


CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,DATE(NOW()),thePeriodSet,theCompuM,@totalInterestComputed);

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

  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),timeP1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @nextDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @endDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),tenure,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @endDate" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @endDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;








 CALL accountNma(accountNumber,@accountName);

INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,DATE(NOW()),theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),DATE(NOW()),@startDate,@endDate,rate,@accountName,'Renewed',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theDisId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,renewals+1,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');

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



COMMIT;

SELECT 1  AS theMessage;


END $$

DELIMITER ;
