

-- ALTER TABLE sequencenumbers modify column  trn_id enum('1') NOT NULL;


-- -----------------------------------------------------
-- createdRenewedLoan

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createdRenewedLoan;

DELIMITER $$

CREATE PROCEDURE createdRenewedLoan(IN accountNumber VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN theLastTransactionDate DATE,IN periodTypeNumber DOUBLE,IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN initialDisburseDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT,IN renewals INT,IN theDisId INT) BEGIN
DECLARE theLoanTxnId INT;
DECLARE theLoanId VARCHAR(60);

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;
 
START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;


CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,DATE(NOW()),thePeriodSet,theCompuM,@totalInterestComputed);

SET @period= thePeriod(periodTypeNumber);

SET @startDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @startDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),DATE(NOW()),CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),2,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));
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

INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,DATE(NOW()),theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),initialDisburseDate,@nextDate,@endDate,rate,@accountName,'Renewed',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theDisId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,renewals+1,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');

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
  @nextDate,
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





-- -----------------------------------------------------
-- createNewLoan

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createNewLoan;

DELIMITER $$

CREATE PROCEDURE createNewLoan(IN accountNumber VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN currentDate DATE,IN periodTypeNumber DOUBLE,IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN amortizationDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT,IN batchNumber VARCHAR(30),IN buzId INT) BEGIN
DECLARE theLoanTxnId INT;
DECLARE theLoanId VARCHAR(60);

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;
 
START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;


CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,amortizationDate,thePeriodSet,theCompuM,@totalInterestComputed);

SET @period= thePeriod(periodTypeNumber);

SET @startDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),amortizationDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),1,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @startDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @startDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @nextDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),amortizationDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),2,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @nextDate" AS CHAR CHARACTER SET utf8));
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
INSERT INTO  new_loan_appstore VALUES(theLoanTxnId,amortizationDate,theLoanId,tenure,tenure,amount,@totalInterestComputed,(amount+@totalInterestComputed),(amount+@totalInterestComputed),amortizationDate,@nextDate,@endDate,rate,@accountName,'Disbursed',TIME(CURRENT_TIMESTAMP),'0.0',((amount+@totalInterestComputed)/tenure),tenure,accountNumber,userId,theLoanTxnId,loanOfficerId,'G0001','GROUP1','1;NA;NA',buzId,0,batchNumber,'0.0',@totalInterestComputed,'0.0',amount,'0.0','0.0','0.0','0.0','0.0','0.0');


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
  @nextDate,
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



COMMIT;

SELECT 1  AS theMessage;

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

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;



IF counter=tenure THEN

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

      WHEN 2 THEN 
       
       
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
           

   

      WHEN 3 THEN 

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

SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX;

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

REPEAT 

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;

IF counter=tenure THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)))-totalInterestX));
END IF;

SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX;

SET theInterestInstalmentX=interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)); 


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








-- DROP PROCEDURE IF EXISTS actualTotalInterestReducingSimple;

-- DELIMITER $$

-- CREATE PROCEDURE actualTotalInterestReducingSimple(amount DOUBLE,rate DOUBLE,periodTypeNumber DOUBLE,tenure DOUBLE) 

-- BEGIN
--  DECLARE counter,counterX INT;
-- DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount  DOUBLE;      
-- SET counter=0;
-- SET counterX=1;
-- SET totalPrincipalX=0.0,totalInterestX=0.0,actualTotalInterest=0.0;
-- IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
-- SET counterX=2; 
-- END IF;

-- SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(amount,tenure),0);

-- SET theInterestInstalmentX=ROUND(interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber)),0);   

-- SET theTxnAmount=amount;

-- REPEAT 

-- SET counter=counter+1;

-- SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;
-- SELECT counter,theInterestInstalmentX,totalInterestX,totalPrincipalX,theTxnAmount;
-- IF counter=tenure THEN

-- SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)))-totalInterestX));
-- END IF;
-- SET theTxnAmount=ROUND((theTxnAmount-thePrincipalIntalmentX),0);

-- SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)),0); 


-- UNTIL counter=tenure END REPEAT;

-- SELECT ROUND(totalInterestX,0);

-- END $$
-- DELIMITER ;




-- -- EPI Equal Period Instalment?


-- DROP PROCEDURE IF EXISTS instalmentCalculatedEPI;

-- DELIMITER $$

-- CREATE PROCEDURE instalmentCalculatedEPI(thePrincipalAmount DOUBLE,rateUsed DOUBLE,tenure DOUBLE,loanCycle DOUBLE) 
-- BEGIN

-- DECLARE pInstalment,denomi,numi,thePower,theARate DOUBLE;
-- SELECT thePrincipalAmount,rateUsed ,tenure ,loanCycle ;

-- set theARate=actualRateUsed(rateUsed,loanCycle);
-- SET thePower=POW((1+(theARate/100)),tenure);
-- set denomi=(thePower-1);
-- set numi=(thePrincipalAmount*(theARate/100))*thePower;

-- SELECT denomi,numi,thePower,theARate;

-- IF denomi>0 THEN

-- SET pInstalment= numi/denomi;

-- -- SET pInstalment=1000/tenure;

-- ELSE 

-- SET pInstalment=thePrincipalAmount/tenure;

-- END IF;

-- SELECT  pInstalment;

-- END $$
-- DELIMITER ;






-- DROP PROCEDURE IF EXISTS actualTotalInterestReducingCompound;

-- DELIMITER $$

-- CREATE PROCEDURE actualTotalInterestReducingCompound(amount DOUBLE,rate DOUBLE,periodTypeNumber DOUBLE,tenure DOUBLE) 

-- BEGIN
--  DECLARE counter,counterX INT;
-- DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount  DOUBLE;      
-- SET counter=0;
-- SET counterX=1;
-- SET totalPrincipalX=0.0,totalInterestX=0.0,actualTotalInterest=0.0;
-- IF periodTypeNumber =3 OR periodTypeNumber =6 OR periodTypeNumber=8 THEN
-- SET counterX=2; 
-- END IF;



-- SET theInterestInstalmentX=interestComputedY(amount,rate,numberOfYears(1,periodTypeNumber));  

-- SET theInitialInstalmentX=instalmentCalculatedEPI(amount,rate ,tenure,periodTypeNumber) ;

-- SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;

-- SET theTxnAmount=amount;

-- REPEAT 

-- SET counter=counter+1;

-- SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;
-- SELECT counter,theInterestInstalmentX,theInitialInstalmentX,thePrincipalIntalmentX,theTxnAmount;
-- IF counter=tenure THEN

-- SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(amount-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((interestComputedY( amount,rate,numberOfYears(tenure,periodTypeNumber)))-totalInterestX));
-- END IF;
-- SET theTxnAmount=(theTxnAmount-thePrincipalIntalmentX);

-- -- SET theInitialInstalmentX=instalmentCalculatedEPI(theTxnAmount,rate ,tenure,periodTypeNumber) ;
-- SET theInterestInstalmentX=interestComputedY(theTxnAmount,rate,numberOfYears(1,periodTypeNumber)); 
-- SET thePrincipalIntalmentX=theInitialInstalmentX-theInterestInstalmentX;




-- UNTIL counter=tenure END REPEAT;

-- SELECT ROUND(totalInterestX,2);

-- END $$
-- DELIMITER ;







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
DECLARE amountTxed,currentIntestX,totalInsterestX, currentPenaltyX,totalPenaltyX,currentAccumulatedInterestX,totalAccumulatedInterestX,currentPrincipalX,totalPrincipalX,theCPrincipalBalance,theCInterestBalance,theCAccumulatedInterestBalance,theCLoanPenaltyBalance,theCLoanBalance,balanceCdue,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid,amountRemain,amountDiff DOUBLE;

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   ROLLBACK;
  
-- END;

-- START TRANSACTION;
SET amountTxed=AmountPaid;

-- SELECT accountNumber;
SELECT theLoanTxnId(CONCAT("newloan",accountNumber)) INTO theLoanId;
-- SELECT theLoanId;
SELECT balance_due,loan_cycle_status INTO amountRemain,loanCycleStatus from new_loan_appstore where trn_id=theLoanId;
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

IF currentIntestX>1 THEN

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



 SELECT PrincipalRemaining INTO currentPrincipalX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;


--  SELECT currentPrincipalX; 

IF ISNULL(currentPrincipalX) THEN
SET currentPrincipalX=0;
END IF;
 
IF currentPrincipalX>1 THEN
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



 SELECT LoanPenaltyRemaining INTO currentPenaltyX FROM new_loan_appstoreamort  WHERE master1_id=theLoanId AND instalment_no=runningInstalmentId;

IF ISNULL(currentPenaltyX) THEN
SET currentPenaltyX=0;
END IF;

IF currentPenaltyX>1 THEN

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

IF amountTxed<1 THEN
LEAVE label1;
END IF;

END IF;



UNTIL amountTxed<=1  END REPEAT label1;




SELECT balance_due,instalments_paid,TotalInterestPaid,TotalInterestRemaining,TotalPrincipalPaid,TotalPrincipalRemaining,TotalAccumulatedInterestPaid,TotalAccumulatedInterestRemaining,TotalLoanPenaltyPaid,TotalLoanPenaltyRemaining,TotalAccruedInterestRemaining,TotalAccruedInterestPaid INTO balanceCdue,instalmentsCpaid,TotalInterestCPaid,TotalInterestCRemaining,TotalPrincipalCPaid,TotalPrincipalCRemaining,TotalAccumulatedInterestCPaid,TotalAccumulatedInterestCRemaining,TotalLoanPenaltyCPaid,TotalLoanPenaltyCRemaining,TotalAccruedInterestCRemaining,TotalAccruedInterestCPaid  FROM new_loan_appstore WHERE trn_id=theLoanId;

SET balanceCdue=balanceCdue-AmountPaid,instalmentsCpaid=instalmentsCpaid+AmountPaid,TotalInterestCPaid=TotalInterestCPaid+totalInsterestX,TotalInterestCRemaining=TotalInterestCRemaining-totalInsterestX,TotalPrincipalCPaid=TotalPrincipalCPaid+totalPrincipalX,TotalPrincipalCRemaining=TotalPrincipalCRemaining-totalPrincipalX,TotalAccumulatedInterestCPaid=TotalAccumulatedInterestCPaid+totalAccumulatedInterestX,TotalAccumulatedInterestCRemaining=TotalAccumulatedInterestCRemaining-totalAccumulatedInterestX,TotalLoanPenaltyCPaid=TotalLoanPenaltyCPaid+totalPenaltyX,TotalLoanPenaltyCRemaining=TotalLoanPenaltyCRemaining-totalPenaltyX;



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

IF balanceCdue<1 THEN

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


-- -- IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount) THEN 
-- -- SET @closedAccount1=CONCAT('closedloan',cycles+2,accountNumber);
-- -- SET @closedAccount=@closedAccount1;
-- -- END IF;



-- -- IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount1) THEN 

-- -- SET @closedAccount2=CONCAT('closedloan',cycles+3,accountNumber);
-- -- SET @closedAccount=@closedAccount2;
-- -- END IF;



-- -- IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount2) THEN 

-- -- SET @closedAccount3=CONCAT('closedloan',cycles+4,accountNumber);
-- -- SET @closedAccount=@closedAccount3;
-- -- END IF;



-- -- IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount3) THEN 

-- -- SET @closedAccount4=CONCAT('closedloan',cycles+5,accountNumber);
-- -- SET @closedAccount=@closedAccount4;
-- -- END IF;

-- IF EXISTS(SELECT * FROM new_loan_appstore WHERE loan_id=@closedAccount4) THEN 

-- SET @closedAccount5=CONCAT('closedloan',cycles+6,accountNumber);
-- SET @closedAccount=@closedAccount5;
-- END IF;

UPDATE new_loan_appstore SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstore1 SET loan_id=@closedAccount,loan_cycle_status='Completed',trn_date=instalmentPaidDate WHERE trn_id=theLoanId;
UPDATE new_loan_appstoreamort SET instalment_status='P',master2_id=@closedAccount,instalment_paid_date=instalmentPaidDate WHERE master1_id=theLoanId;

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

SELECT totalPrincipalX,totalInsterestX,totalAccumulatedInterestX,totalPenaltyX,completionStatus,loanCycleStatus;

-- COMMIT;

END//

 DELIMITER ;





DROP PROCEDURE IF EXISTS updateThePrincipalComponent;
DELIMITER //
CREATE PROCEDURE updateThePrincipalComponent(IN theRunningInstalmentId INT,IN currentPrincipalX DOUBLE,theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid, totalPrincipalRemaining,amountPaid,amountRemaining,princimpalPaid,principalRemaining  DOUBLE;
SET  totalAmountPaid=NULL,totatAmountRemaianing=NULL,totalPrincipalPaid=NULL,totalPrincipalRemaining=NULL;
-- select theRunningInstalmentId,theLoanId,currentPrincipalX;


 
SELECT instalment_paid, InstalmentRemaining,PrincipalPaid,PrincipalRemaining INTO  totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid,totalPrincipalRemaining FROM new_loan_appstoreamort  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,PrincipalPaid,PrincipalRemaining INTO @totalAmountPaid,@totatAmountRemaianing,@totalPrincipalPaid,@totalPrincipalRemaining FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;



-- SELECT totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid,totalPrincipalRemaining;

  SET totalAmountPaid=@totalAmountPaid+currentPrincipalX,
  totatAmountRemaianing=@totatAmountRemaianing-currentPrincipalX,
  totalPrincipalPaid=@totalPrincipalPaid+currentPrincipalX, totalPrincipalRemaining=@totalPrincipalRemaining-currentPrincipalX;

-- SELECT totalAmountPaid,totatAmountRemaianing,totalPrincipalPaid,totalPrincipalRemaining;

UPDATE new_loan_appstoreamort SET  instalment_paid=totalAmountPaid, InstalmentRemaining=totatAmountRemaianing,PrincipalPaid=totalPrincipalPaid,PrincipalRemaining=totalPrincipalRemaining WHERE  instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

        -- SELECT 'PRINCIPAL',amountRemaining; 
    IF totatAmountRemaianing<1 THEN
      
     
UPDATE  new_loan_appstoreamort  SET 
  instalment_status='P',
 instalment_paid_date=instalmentPaidDate,
 instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
    END IF;

END//

 DELIMITER ;





DROP PROCEDURE IF EXISTS updateThePenaltyComponent;
DELIMITER //
CREATE PROCEDURE updateThePenaltyComponent(IN theRunningInstalmentId INT,IN currentPenaltyX DOUBLE,theLoanId INT,instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE amountPaid,amountRemaining,penaltyPaid,penaltyRemaining  DOUBLE DEFAULT 0.0;





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



DROP PROCEDURE IF EXISTS updateTheAccumulatedInterestComponentSpecial;
DELIMITER //
CREATE PROCEDURE updateTheAccumulatedInterestComponentSpecial(IN theRunningInstalmentId INT,IN currentAccumulatedInterestX DOUBLE,theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId,thId INT;

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
  -- amountRemaining=@amountRemaining-currentAccumulatedInterestX,
  AccumulatedInterestPaidX=@AccumulatedInterestPaidX+currentAccumulatedInterestX;

  -- AccumulatedInterestRemainingX=@AccumulatedInterestRemainingX-currentAccumulatedInterestX;


UPDATE   new_loan_appstoreamort   SET 
  instalment_paid=amountPaid,
  -- InstalmentRemaining=amountRemaining,
  AccumulatedInterestPaid=AccumulatedInterestPaidX
  -- AccumulatedInterestRemaining=AccumulatedInterestRemainingX   
  
  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;



  
SELECT instalments_paid, TotalAccruedInterestPaid INTO @totalInstalPaid,@totaAccumuPaid FROM new_loan_appstore WHERE trn_id=theLoanId;

SET @totalInstalPaid=@totalInstalPaid+currentAccumulatedInterestX,@totaAccumuPaid=@totaAccumuPaid+currentAccumulatedInterestX;

      
UPDATE new_loan_appstore SET instalments_paid=@totalInstalPaid, TotalAccruedInterestPaid=@totaAccumuPaid, balance_due=0.0 WHERE  trn_id=theLoanId;


SELECT AccumulatedInterestPaid,TrnId INTO @totalInstalPaid,thId FROM pmms.loandisburserepaystatement WHERE loanTrnId=theLoanId ORDER BY TrnId DESC LIMIT 1;

SET @totalInstalPaid=@totalInstalPaid+currentAccumulatedInterestX;

      
UPDATE pmms.loandisburserepaystatement SET AccumulatedInterestPaid=@totalInstalPaid, LoanBalance=0.0 WHERE  TrnId=thId;


    IF amountRemaining<1 THEN
      
     
UPDATE  new_loan_appstoreamort     SET 
  instalment_status='P',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

    END IF;



END//

 DELIMITER ;

   
   


DROP PROCEDURE IF EXISTS updateTheAccumulatedInterestComponent;
DELIMITER //
CREATE PROCEDURE updateTheAccumulatedInterestComponent(IN theRunningInstalmentId INT,IN currentAccumulatedInterestX DOUBLE,theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
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



END//

 DELIMITER ;






DROP PROCEDURE IF EXISTS updateTheInterestComponent;
DELIMITER //
CREATE PROCEDURE updateTheInterestComponent(IN theRunningInstalmentId INT,IN currentInterestX DOUBLE,theLoanId INT,IN instalmentPaidDate DATE) READS SQL DATA 
BEGIN

DECLARE runningInstalmentId INT;

DECLARE amountPaid,amountRemaining,InterestPaid,InterestRemaining  DOUBLE DEFAULT 0.0;
-- SELECT theRunningInstalmentId,theLoanId,currentInterestX,instalmentPaidDate;

SELECT instalment_paid,
  InstalmentRemaining,
  InterestPaid,
  InterestRemaing INTO  amountPaid,amountRemaining,InterestPaid,InterestRemaining FROM  new_loan_appstoreamort  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
    

    
  SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,InterestPaid,InterestRemaing INTO @amountPaid,@amountRemaining,@InterestPaid,@InterestRemaining FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT amountPaid,amountRemaining,InterestPaid,InterestRemaining;

  SET amountPaid=@amountPaid+currentInterestX,
  amountRemaining=@amountRemaining-currentInterestX,
  InterestPaid=@InterestPaid+currentInterestX,
  InterestRemaining=@InterestRemaining-currentInterestX;


UPDATE  new_loan_appstoreamort    SET 
  instalment_paid=amountPaid,
  InstalmentRemaining=amountRemaining,
  InterestPaid=InterestPaid,
  InterestRemaing=InterestRemaining   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

    -- SELECT 'iNTEREST',amountRemaining,theLoanId;
    IF amountRemaining<1 THEN
      
     
UPDATE  new_loan_appstoreamort   SET 
 instalment_status='P',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

    END IF;



END//

 DELIMITER ;








DROP FUNCTION IF EXISTS currentInstalmentNow;
DELIMITER ##
CREATE FUNCTION currentInstalmentNow(theLoanId INT) 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE theInstalmentNo INT;

SELECT instalment_no INTO theInstalmentNo FROM new_loan_appstoreamort WHERE  master1_id=theLoanId AND NOT instalment_status='P' ORDER BY trn_id ASC LIMIT 1;

RETURN theInstalmentNo;
END ##
DELIMITER ;








/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS theLoanTxnId;
DELIMITER ##
CREATE FUNCTION theLoanTxnId(theLoanId VARCHAR(100)) 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE theIdLoan INT;

SELECT trn_id INTO theIdLoan FROM new_loan_appstore WHERE  loan_id=theLoanId AND (loan_cycle_status='Disbursed'  OR loan_cycle_status='Renewed');

RETURN theIdLoan;
END ##
DELIMITER ;












DROP PROCEDURE IF EXISTS createTheRenewalDetails;
DELIMITER //
CREATE PROCEDURE createTheRenewalDetails(IN theRenewalStatus INT,IN theRenewalDeadline INT,theDeadlineMeasure VARCHAR(30),IN theRenewalRate DOUBLE,IN thePeriodUsed INT,IN theRenewalTimes INT) READS SQL DATA 
BEGIN


IF EXISTS(SELECT * FROM autoRenewalSettings) THEN
UPDATE autoRenewalSettings SET renewalStatus=theRenewalStatus,renewalDeadline=theRenewalDeadline,renewalMeasure=theDeadlineMeasure,renewalRate=theRenewalRate,periodUsed=thePeriodUsed,renewalTimes=theRenewalTimes;
ELSE
INSERT INTO autoRenewalSettings VALUES("",theRenewalStatus,theRenewalDeadline,theDeadlineMeasure,theRenewalRate,thePeriodUsed,theRenewalTimes);

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
 PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


 ALTER TABLE autoRenewalSettings modify column  id enum('1') NOT NULL;

 

 
DROP PROCEDURE IF EXISTS getTheRenewalDetails;
DELIMITER //
CREATE PROCEDURE getTheRenewalDetails() READS SQL DATA 
BEGIN

IF EXISTS(SELECT * FROM autoRenewalSettings) THEN

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes FROM  autoRenewalSettings;

ELSE
INSERT INTO autoRenewalSettings VALUES("",0,2,'DAYS',240.0,0,3);

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes FROM  autoRenewalSettings;


END IF;



END//

 DELIMITER ;







 
DROP PROCEDURE IF EXISTS theLoansForRenewal;
DELIMITER //
CREATE PROCEDURE theLoansForRenewal() READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,periodSet,l_done,loanId INT;
DECLARE theDealineMeeasure VARCHAR(30);
   
DECLARE forloanId CURSOR FOR SELECT trn_id   FROM pmms_loans.new_loan_appstore where loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';
 
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
   theDidId INT
  );


 
 OPEN forloanId;
SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals FROM 
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
INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId);
END IF;




    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;

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

 UPDATE pmms.loandisburserepaystatement SET loanTrnId= @theIdDS,LoanStatusReport='Disbursed' where loanTrnId=loanId;

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
   
DECLARE forloanId CURSOR FOR SELECT trn_id   FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed';
 
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


 INSERT INTO oneTimeUpdate VALUES(1);


DROP PROCEDURE IF EXISTS grossLoanPortfolio;

DELIMITER //

-- Credits	MAKANGA CHARLES 0702696171		Customer Deposits	0
-- Debits	MOMO PAY	01122000610	Bank Balance	.0
CREATE PROCEDURE grossLoanPortfolio() READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';
 
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
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';
 
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

-- CALL grossLoanPortfolioPdf();



-- CURRENT



/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysis;

DELIMITER ##

CREATE PROCEDURE   agingAnalysis()
BEGIN
   
 DECLARE l_done,ID,arrears INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, loan_deadline VARCHAR(60),PRIMARY KEY (id_1));

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60),loan_deadline VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS aging_loan_analysis2x;

CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3x;

CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4x;

CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;

CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;

CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis9;

CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_9))ENGINE = InnoDB
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

-- SELECT p_remain,i_remain,arrears;

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

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;


IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1; 
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,  "-" FROM aging_loan_analysis1x; 

    
END IF;

    IF @port0 >0 OR   @port0x >0 THEN   
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
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  (@port0+@port0x),
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( ot.loans_remaining) ,
  SUM(ot.principal_remaining) ,
  SUM(ot.interest_remaining),
  SUM(ot.principal_inarrears) ,
  SUM(ot.interest_inarrears) ,
 "-" ,"-" FROM (SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1 UNION ALL SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1x) ot;  
 END IF;

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
  number_of_days_in_arrears  ,loan_deadline
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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis2;

 
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
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port1,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" , "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

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
  number_of_days_in_arrears   ,loan_deadline
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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis3;
    
    
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
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port2,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

  IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-",
 "-" );

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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis4;
  
  
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
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port3,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-",
 "-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
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
  number_of_days_in_arrears  ,loan_deadline
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
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis5;

  
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
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port4,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis6;
  
  
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis8;


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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis3x;
    
    
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis5x;

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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis6x;
  
  
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360; 

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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis8x;

 
IF @port0x >0 OR @port0 >0 THEN
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis9;
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

-- CALL agingAnalysis();







/*  aging Analysis Staff */
DROP PROCEDURE IF EXISTS agingAnalysisStaff;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisStaff(IN staffId INT)
BEGIN
   
   
 DECLARE l_done,ID,arrears INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed') AND gruop_id=staffId  ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_1));

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60),loan_deadline VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS aging_loan_analysis2x;

CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3x;

CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4x;

CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;

CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;

CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, loan_deadline VARCHAR(60), PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis9;

CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),  PRIMARY KEY (id_9))ENGINE = InnoDB
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

-- SELECT p_remain,i_remain,arrears;

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

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;


IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1; 
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( ot.loans_remaining) ,
  SUM(ot.principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
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

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1x));

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


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8x))ENGINE = InnoDB
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
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x; 

    
END IF;

    IF @port0 >0 OR   @port0x >0 THEN   
INSERT INTO  aging_loan_analysis9( 
  id_9,
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
  (@port0+@port0x),
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( ot.loans_remaining) ,
  SUM(ot.principal_remaining) ,
  SUM(ot.interest_remaining),
  SUM(ot.principal_inarrears) ,
  SUM(ot.interest_inarrears) ,
 "-"  FROM (SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1 UNION ALL SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1x) ot;  
 END IF;

 IF @port0 >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","ACTIVE LOANS","-","-","-","-","-","-");
END IF;
IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","PERFORMING PORTFOLIO","-","-","-","-","-","-","-");
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


 IF @port0x >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","RENEWED LOANS","-","-","-","-","-","-");
END IF;

IF @port1x >0 THEN

 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","RENEWED PERFORMING PORTFOLIO","-","-","-","-","-","-","-");
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

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId VARCHAR(100);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed') AND  gruop_id=staffId  ;
 
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

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId VARCHAR(100);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed') AND  gruop_id=staffId  ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_1x));

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


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),gaurantor1_name VARCHAR(100),gaurantor1_contact VARCHAR(100),gaurantor2_name VARCHAR(100),gaurantor2_contact VARCHAR(100),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_8x))ENGINE = InnoDB
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
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1x; 

    
END IF;

    IF @port0 >0 OR   @port0x >0 THEN   
INSERT INTO  aging_loan_analysis9( 
  id_9,
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
  (@port0+@port0x),
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
        "-" ,
          "-" ,
            "-" ,
              "-" ,
 SUM( ot.loans_remaining) ,
  SUM(ot.principal_remaining) ,
  SUM(ot.interest_remaining),
  SUM(ot.principal_inarrears) ,
  SUM(ot.interest_inarrears) ,
 "-"  FROM (SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1 UNION ALL SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1x) ot;  
 END IF;

 IF @port0 >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","-","-","-","-","ACTIVE LOANS","-","-","-","-","-","-");
END IF;
IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","-","-","-","-","PERFORMING PORTFOLIO","-","-","-","-","-","-","-");
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
SELECT COUNT(trn_id) INTO activeCustomers FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';

END $$
DELIMITER ;




DROP PROCEDURE IF EXISTS totalNumberOfCustomersPaid;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfCustomersPaid(OUT activeCustomersPaid INT)
BEGIN

SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW());


SELECT  COUNT( DISTINCT master1_id) INTO @totalPaidRenewed from new_loan_appstoreamort WHERE instalment_status='P' AND instalment_paid_date=DATE(NOW());


SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

END $$
DELIMITER ;



-- DROP PROCEDURE IF EXISTS princimpalLoanRepaymentsMade;

-- DELIMITER //

-- CREATE PROCEDURE princimpalLoanRepaymentsMade(IN theDate DATE,OUT princimpalRepaymentsMade VARCHAR(100)) READS SQL DATA 




-- END $$
-- DELIMITER ;


 DROP PROCEDURE IF EXISTS closingCash;   
DELIMITER $$
CREATE  PROCEDURE closingCash(OUT closingCashBal INT)
BEGIN

SELECT ledger_balance INTO closingCashBal from pmms.bsanca01123000110 where trn_date=DATE(NOW()) ORDER BY trn_id DESC LIMIT 1;

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


SELECT  COUNT( DISTINCT master1_id) INTO renewedCustomersPaid from new_loan_appstoreamort INNER JOIN new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE instalment_status='P' AND instalment_paid_date=DATE(NOW()) and loan_cycle_status='Renewed';


-- SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

END $$
DELIMITER ;





DROP PROCEDURE IF EXISTS sumRenewalsPaid;
DELIMITER $$
CREATE  PROCEDURE sumRenewalsPaid(OUT sumRenewalsPaid INT)
BEGIN

-- SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW());


SELECT  SUM(instalment_paid) INTO sumRenewalsPaid from new_loan_appstoreamort INNER JOIN new_loan_appstore ON new_loan_appstore.trn_id=new_loan_appstoreamort.master1_id WHERE instalment_status='P' AND instalment_paid_date=DATE(NOW()) and loan_cycle_status='Renewed';


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



DROP PROCEDURE `smsSummuryReport`;
DELIMITER $$
CREATE  PROCEDURE `smsSummuryReport`()
    READS SQL DATA
BEGIN
 
    


DROP TABLE IF EXISTS smsSummury;

CREATE  TEMPORARY TABLE smsSummury(itemName VARCHAR(200),itemValue VARCHAR(200));



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
SET @ActualTotalAmountCollectedToday=@princimpalRepaymentsMade+@InterestR;
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
INSERT INTO smsSummury VALUES("No.CustomSDeposited:",@activeCustomersSave);

  END IF;


 CALL totalNumberOfSavingWithdraw(@activeCustomersSave);

IF @activeCustomersSave>0 THEN
-- SELECT @activeCustomersSave;
INSERT INTO smsSummury VALUES("No.CustomSWithdrawn:",@activeCustomersSave);

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
INSERT INTO smsSummury VALUES("SavingsAndDeposits:",@savingsC);
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
INSERT INTO smsSummury VALUES("SavingsWithdraws:",@savingDepositWith);
END IF;

INSERT INTO smsSummury VALUES("CC:",@OpeningCahdBala);


SELECT itemName,FORMAT(itemValue,0)  AS itemValue FROM smsSummury;


DROP TABLE smsSummury;

END $$
DELIMITER ;


CALL  smsSummuryReport() ;


-- PMMS

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



-- MobileMoneyDeposit
-- MobileMoneyWithdraw
-- CreateUnknownMobileMoney
-- LoanPytMobileMoney
-- LoanPytUnknownMobileMoney

-- OTHER CHANGES TO NOTE:
-- OtherGroups2=Business Id
-- OtherGroups3=Number of Renewals
-- OtherGroups4=BatchNumber
-- authoriser_id=DisbursedLoanId



/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanPrintingDetailsRenewed;

DELIMITER ##

CREATE PROCEDURE   loanPrintingDetailsRenewed(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 DECLARE l_done,noOfRenewals,loanTrnIdL,disLoanTrnIdL INT;
 DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL,loanRenewedL,arrearsAmount,amountRenewed VARCHAR(60);
  DECLARE companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,theTxnTime VARCHAR(60);
DECLARE date_takenL,theTrn_date,dateLastRenewed DATE;

DROP TABLE IF EXISTS loanPrintDetailsRenew;

CREATE TEMPORARY  TABLE loanPrintDetailsRenew(
id_1 INTEGER, -- 0
company_name VARCHAR(60),-- 1
company_branch VARCHAR(60),-- 2
company_box_number VARCHAR(60),-- 3
customer_name VARCHAR(60),-- 4
staff_name VARCHAR(60),-- 5
loan_taken VARCHAR(60),-- 6
date_taken DATE,-- 7
loan_renewed  VARCHAR(60),-- 8
no_of_renewals  INT,-- 9
date_last_renewed DATE,-- 10
loans_paid VARCHAR(60),-- 11
loan_remaining VARCHAR(60),-- 12
amount_arrears VARCHAR(60),-- 13
batchNumber  VARCHAR(60),-- 14
loanID  VARCHAR(60),-- 15
trn_date VARCHAR(60),-- 16
trn_time VARCHAR(30),-- 17
LoanStatus VARCHAR(60),-- 18
princimpal_amount VARCHAR(60),-- 19
interest_amount VARCHAR(60),-- 20
office_number VARCHAR(60),-- 21
amount_renewed VARCHAR(60));-- 21

-- loanDetails.add(rsxmt.getString("loan_taken"));
-- loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("date_taken")));

-- loanDetails.add(rsxmt.getString("loan_renewed"));
-- loanDetails.add(rsxmt.getString("no_of_renewals"));
-- loanDetails.add(fmt.fromDatabaseWithDashSeperatorBeginningWithYear(rsxmt.getString("date_last_renewed")));



SELECT loanTrnId,AmountPaid,LoanBalance,AccountNumber,LoanStatusReport,TrnDate  INTO loanTrnIdL,AmountPaidL,AmountRemainL,accountNumberL ,LoanStatus ,theTrn_date FROM   pmms.loandisburserepaystatement WHERE BatchCode=batchNumber;

SELECT trn_time INTO theTxnTime FROM pmms.general_ledger WHERE chq_number=batchNumber LIMIT 1;

SELECT authoriser_id,OtherGroups3,princimpal_amount INTO  disLoanTrnIdL,noOfRenewals,amountRenewed FROM pmms_loans.new_loan_appstore WHERE trn_id=loanTrnIdL;

-- SELECT SUM(InstalmentRemaining) INTO arrearsAmount FROM pmms_loans.new_loan_appstoreamort WHERE master1_id=loanTrnIdL AND NOT instalment_status='P' AND instalment_due_date<=DATE(NOW());

SELECT total_loanAmount,instalment_start_date INTO  loan_takenL,date_takenL FROM  pmms_loans.new_loan_appstore WHERE trn_id=disLoanTrnIdL;

SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

SELECT ExpectedTotalAmount,TrnDate,AmountDisbursed,ExpectedInterest INTO loanRenewedL,dateLastRenewed,princimpalL,interestL  FROM loandisburserepaystatement WHERE loanTrnId=loanTrnIdL ORDER BY TrnId ASC LIMIT 1;

select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;
-- select AmountRemainL;
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

  IF ISNULL(date_takenL) THEN
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









/* LOAN RECEIPT PRINTING */
DROP PROCEDURE IF EXISTS loanPrintingDetails;

DELIMITER ##

CREATE PROCEDURE   loanPrintingDetails(IN batchNumber VARCHAR(45),IN staffId VARCHAR(45))
BEGIN

 DECLARE l_done INT;
 DECLARE AmountPaidL,AmountRemainL,loan_takenL,princimpalL,interestL DOUBLE;
  DECLARE loanTrnIdL,companyName,companyBranch,companyBoxNumber,accountNumberL,LoanStatus,officeNumber,arrearsAmount VARCHAR(60);
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
office_number VARCHAR(60),-- 17
amount_arrears VARCHAR(60));-- 18

SELECT loanTrnId,AmountPaid,LoanBalance,AccountNumber,LoanStatusReport,TrnDate  INTO loanTrnIdL,AmountPaidL,AmountRemainL,accountNumberL ,LoanStatus ,theTrn_date FROM   loandisburserepaystatement WHERE BatchCode=batchNumber;

SELECT SUM(InstalmentRemaining) INTO arrearsAmount FROM pmms_loans.new_loan_appstoreamort WHERE master1_id=loanTrnIdL AND NOT instalment_status='P' AND instalment_due_date<=DATE(NOW());

SELECT the_company_name, the_company_branch,the_company_box_number,office_number INTO companyName,companyBranch, companyBoxNumber,officeNumber FROM the_company_datails;

SELECT ExpectedTotalAmount,TrnDate,AmountDisbursed,ExpectedInterest INTO loan_takenL,date_takenL,princimpalL,interestL  FROM loandisburserepaystatement WHERE loanTrnId=loanTrnIdL ORDER BY TrnId ASC LIMIT 1;

select trn_id into l_done from general_ledger where chq_number=batchNumber ORDER BY trn_id ASC LIMIT 1;
-- select AmountRemainL;

IF ISNULL(arrearsAmount) THEN
SET arrearsAmount=0;
END IF;

INSERT INTO loanPrintDetails VALUES (l_done,companyName,companyBranch,companyBoxNumber,customerNameL(accountNumberL),staffName(staffId),FORMAT(loan_takenL,0),date_takenL,FORMAT(AmountPaidL,0),FORMAT(AmountRemainL,0),batchNumber,loanTrnIdL,DATE_FORMAT(theTrn_date,'%d/%m/%Y'),TIME(NOW()),LoanStatus,FORMAT(princimpalL,0),FORMAT(interestL,0),officeNumber,FORMAT(arrearsAmount,0));


   SELECT * FROM loanPrintDetails;

END

##
DELIMITER ;










 
DROP PROCEDURE IF EXISTS updatingBalances;
DELIMITER //
CREATE PROCEDURE updatingBalances() READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,periodSet,l_done,loanId INT;
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
   theDidId INT
  );


 
 OPEN forloanId;
SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals FROM 
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
INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId);
END IF;


-- 06/04/2022	06/04/2022	Centenary Banks Bank Deposit Processed on 06/04/2022
--   From Cash At Hand	-	3295205.0	Cash At Hand

    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;

SELECT * FROM theRenewalLoanDetailsN;

END//

 DELIMITER ;

