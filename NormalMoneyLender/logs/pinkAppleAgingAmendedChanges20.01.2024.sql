-- 05/02/2023	01121000210	Airtel Money 0709800476	Mobile Money	140000.0	Active
-- 05/06/2022	05/06/2022	LWANGA IBRAH 0759775579s Account Deposit for Loan Payment
  -- Dated 05/06/2022	159331.0	-	921531.0

-- 70624	TUMWINE ROGDERS 0757537197	BODA BODA	240	200000	0	0	0	0	30 DAYS	25/09/2022	10006	Completed
-- -----------------------------------------------------
-- createdRenewedLoan

-- -----------------------------------------------------

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

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;F
  -- Debits	Airtel Money(1221109)	01121000310	Mobile Money	2.43012E7
-- END;


--  CALL createdRenewedLoan(
--   '05502013710',
--   417910,
--   180,
--   30,
--   '2022-11-30',
--   1,
--   'DAYS',10001,1,'2022-10-24',10001,0,1,'BT034293',1,2,70449);
START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;


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
DECLARE theLoanTxnId,timeP,timeP1 INT;
DECLARE theLoanId VARCHAR(60);

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;
 
START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;


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



-- CALL RepayTheLoanNow('05502000210',80000.0,'BTN57231',10001,'2023-02-19',10001);




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
SET @closedAccount=CONCAT('closedloan',cycles+1,accountNumber);
END IF;

IF EXISTS(SELECT * FROM new_loan_appstore1 WHERE loan_id=@closedAccount) THEN
SELECT SUBSTRING(@closedAccount, 11, 2) INTO cycles;
-- SELECT cycles;
SET @closedAccount=CONCAT('closedloan',cycles+1,accountNumber);
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




-- Credits	Kyomuhendo Morine 0785264462	05502008810	Customer Deposits	0
-- Credits	Tumwebaze Peter 0775215732	05502039810	Customer Deposits	0

-- CALL RepayTheLoanNow('05502039810',1050000.0,'BTN39158',10001,'2023-02-15',10001);


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
 ELSE

UPDATE  new_loan_appstoreamort   SET 
 instalment_status='PP',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

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

 ELSE

UPDATE  new_loan_appstoreamort   SET 
 instalment_status='PP',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

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

 ELSE

UPDATE  new_loan_appstoreamort   SET 
 instalment_status='PP',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

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

  
 ELSE

UPDATE  new_loan_appstoreamort   SET 
 instalment_status='PP',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

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

SELECT instalment_paid,InstalmentRemaining,InterestPaid,InterestRemaing INTO  amountPaid,amountRemaining,InterestPaid,InterestRemaining FROM  new_loan_appstoreamort  WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
    
-- SELECT  amountPaid,amountRemaining,InterestPaid,InterestRemaining;
    
  SET @dueDate = concat(CAST("SELECT instalment_paid, InstalmentRemaining,InterestPaid,InterestRemaing,master2_id INTO @amountPaid,@amountRemaining,@InterestPaid,@InterestRemaining,@theLoanId FROM new_loan_appstoreamort  WHERE instalment_no=" AS CHAR CHARACTER SET utf8),theRunningInstalmentId, CAST(" AND master1_id=" AS CHAR CHARACTER SET utf8),theLoanId);
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT amountPaid,amountRemaining,InterestPaid,InterestRemaining;

  SET amountPaid=@amountPaid+currentInterestX,
  amountRemaining=@amountRemaining-currentInterestX,
  InterestPaid=@InterestPaid+currentInterestX,
  InterestRemaining=@InterestRemaining-currentInterestX;

--  SELECT 'iNTEREST',amountRemaining,theLoanId,amountPaid,amountRemaining,InterestPaid,InterestRemaining,theRunningInstalmentId,theLoanId;
UPDATE  new_loan_appstoreamort    SET 
  instalment_paid=amountPaid,
  InstalmentRemaining=amountRemaining,
  InterestPaid=InterestPaid,
  InterestRemaing=InterestRemaining   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
-- SELECT @theLoanId;

CALL DateManagementForLenders(currentInterestX,@theLoanId);

    -- SELECT 'iNTEREST',amountRemaining,theLoanId;
    IF amountRemaining<1 THEN
      
     
UPDATE  new_loan_appstoreamort   SET 
 instalment_status='P',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;
 UPDATE new_loan_appstore SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;
 UPDATE new_loan_appstore1 SET remaining_instalments=(remaining_instalments-1) WHERE trn_id=theLoanId;

 ELSE

UPDATE  new_loan_appstoreamort   SET 
 instalment_status='PP',
  instalment_paid_date=instalmentPaidDate,
  instalment_paid_variance=DATEDIFF(instalment_due_date, instalment_paid_date)   WHERE instalment_no=theRunningInstalmentId AND master1_id=theLoanId;

 END IF;

-- SELECT 'iNSIDE IT';

END//

 DELIMITER ;




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



-- CALL RepayTheLoanNow('05502001310',35000.0,'BTN34249',10001,'2022-04-19',10001)


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
CREATE PROCEDURE createTheRenewalDetails(IN theRenewalStatus INT,IN theRenewalDeadline INT,theDeadlineMeasure VARCHAR(30),IN theRenewalRate DOUBLE,IN thePeriodUsed INT,IN theRenewalTimes INT,IN theRateTypeUsedNow INT) READS SQL DATA 
BEGIN


IF EXISTS(SELECT * FROM autoRenewalSettings) THEN
UPDATE autoRenewalSettings SET renewalStatus=theRenewalStatus,renewalDeadline=theRenewalDeadline,renewalMeasure=theDeadlineMeasure,renewalRate=theRenewalRate,periodUsed=thePeriodUsed,renewalTimes=theRenewalTimes,rateTypeUsed=theRateTypeUsedNow;
ELSE
INSERT INTO autoRenewalSettings VALUES("",theRenewalStatus,theRenewalDeadline,theDeadlineMeasure,theRenewalRate,thePeriodUsed,theRenewalTimes,theRateTypeUsedNow);

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
 PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


 ALTER TABLE autoRenewalSettings modify column  id enum('1') NOT NULL;

 

 
DROP PROCEDURE IF EXISTS getTheRenewalDetails;
DELIMITER //
CREATE PROCEDURE getTheRenewalDetails() READS SQL DATA 
BEGIN

IF EXISTS(SELECT * FROM autoRenewalSettings) THEN

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed FROM  autoRenewalSettings;

ELSE
INSERT INTO autoRenewalSettings VALUES("",0,2,'DAYS',240.0,0,3,1);

SELECT renewalStatus,renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed FROM  autoRenewalSettings;


END IF;



END//

 DELIMITER ;







 
DROP PROCEDURE IF EXISTS theLoansForRenewal;
DELIMITER //
CREATE PROCEDURE theLoansForRenewal() READS SQL DATA 
BEGIN
DECLARE theRenewalRate DOUBLE;
DECLARE theDeadline,numberOfRenewals,theRateTypeUsed,periodSet,l_done,loanId INT;
DECLARE theDealineMeeasure,theLoanTenurez VARCHAR(30);
   
DECLARE forloanId CURSOR FOR SELECT new_loan_appstore.trn_id   FROM pmms_loans.new_loan_appstore INNER JOIN pmms_loans.new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id where (new_loan_appstore.loan_cycle_status='Disbursed' OR new_loan_appstore.loan_cycle_status='Renewed') AND NOT (new_loan_appstore1.loan_tenure='1 MONTHS' OR new_loan_appstore1.loan_tenure='1.0 MONTHS' );
 
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
  theNRateTypeUsed INT,
   thePeriodTypeInt INT,
      thePeriodTypeString VARCHAR(60)
  );


 
 OPEN forloanId;

SELECT renewalDeadline,renewalMeasure,renewalRate,periodUsed,renewalTimes,rateTypeUsed INTO theDeadline,theDealineMeeasure,theRenewalRate,periodSet,numberOfRenewals,theRateTypeUsed FROM 
autoRenewalSettings;
loanId_loop: LOOP 

 SET loanId=NULL, @accountNumber=NULL,@loansOfficer=NULL, @balanceDueN=NULL,@trnDate=NULL,@initialDisDate=NULL,@loanTenure=NULL,@theBuzId=NULL,@numberRenewals=NULL,@previousId =NULL ;
 FETCH forloanId into loanId;
--  SELECT loanId;
 SELECT loan_tenure INTO theLoanTenurez FROM new_loan_appstore1 WHERE trn_id=loanId;
 IF l_done=1 THEN

LEAVE loanId_loop;

 END IF;
 
 
IF theLoanTenurez<>'1 MONTHS' OR theLoanTenurez<>'1.0 MONTHS' THEN

SET @dueDateX = concat(CAST("SELECT new_loan_appstore.applicant_account_number,new_loan_appstore.gruop_id, new_loan_appstore.balance_due,new_loan_appstore.trn_date,new_loan_appstore.instalment_start_date,new_loan_appstore.loan_tenure,new_loan_appstore.OtherGroups2,new_loan_appstore.OtherGroups3,new_loan_appstore.authoriser_id,new_loan_appstore.interest_rate,new_loan_appstore1.loan_tenure INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId,@originalRate,@originalTenure  FROM new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id WHERE DATE(NOW())>=" AS CHAR CHARACTER SET utf8),CAST("(SELECT DATE_ADD(instalment_end_date, INTERVAL " AS CHAR CHARACTER SET utf8),theDeadline, CAST(" " AS CHAR CHARACTER SET utf8),theDeadilineReturn(theDealineMeeasure),CAST(" ) FROM new_loan_appstore WHERE trn_id=" AS CHAR CHARACTER SET utf8),loanId,CAST(")  AND new_loan_appstore.OtherGroups3<" AS CHAR CHARACTER SET utf8),numberOfRenewals,CAST(" AND new_loan_appstore.trn_id=" AS CHAR CHARACTER SET utf8),loanId);
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

    SET l_done=0;
 END LOOP loanId_loop;
 CLOSE forloanId;

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
 

IF theLoanTenurez<>'1 MONTHS' OR theLoanTenurez<>'1.0 MONTHS' THEN

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




-- IF theLoanTenurez<>'1 MONTHS' OR theLoanTenurez<>'1.0 MONTHS' THEN

-- SET @dueDateX = concat(CAST("SELECT applicant_account_number,gruop_id, balance_due,trn_date,instalment_start_date,loan_tenure,OtherGroups2,OtherGroups3,authoriser_id INTO @accountNumber,@loansOfficer, @balanceDueN,@trnDate,@initialDisDate,@loanTenure,@theBuzId,@numberRenewals,@previousId  FROM new_loan_appstore WHERE trn_id=" AS CHAR CHARACTER SET utf8),theLoanTrnId);
--   -- select  @dueDateX;
--   PREPARE stmt2 FROM @dueDateX;
--   EXECUTE stmt2;
-- DROP PREPARE stmt2;
-- -- SELECT "ssssssssssssssssss",@accountNumber;
-- IF NOT ISNULL(@accountNumber) THEN
-- INSERT INTO theRenewalLoanDetailsN VALUES(@accountNumber,@loansOfficer,@balanceDueN,theRenewalRate,@trnDate,@initialDisDate,@loanTenure,periodSet,@theBuzId,@numberRenewals,@previousId,theRateTypeUsed);
-- END IF;


-- END IF;

--     SET l_done=0;
--  END LOOP loanId_loop;
--  CLOSE forloanId;

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





DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN /*One of the most important idea to note is that when a payment interest instalment is made, the interest due date has to be shifted one date ahead*/

DECLARE numberOfIds INTEGER;


 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore1 WHERE loan_id=loanIdUsed;

IF @theTenure= '1.0 MONTHS' OR @theTenure= '1 MONTHS' THEN

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;


interestPayment_Loop: LOOP


SELECT TrnId,InterestInvoRemaining, InterestPaidIn,InterestInvolved INTO 
@theId,
@interestRemaining,
@interestPaidnow,
@InterestInveo 
FROM interestcomputed WHERE loanId=loanIdUsed AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;


 SET @inteDiff=InterestPaid-@interestRemaining;
 
 IF @inteDiff<=0 THEN 
 

 
 
 IF @inteDiff=0 THEN
 
/*  SET @A=10;
  SELECT @A; */
 CALL newDateConverted(@originalDueDate);
 
 UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

 SET InterestPaid=0.0;
 
 ELSEIF @inteDiff<0 THEN
/*  SET @B=10;
  SELECT @B; */
 SET @IntRemai=@interestRemaining-InterestPaid;
 SET @intPai=@interestPaidnow+InterestPaid;
  
 UPDATE interestComputed SET InterestInvoRemaining=@IntRemai,InterestPaidIn=@intPai WHERE TrnId=@theId;

 SET InterestPaid=0.0;
 
 END IF;
 
 ELSEIF @inteDiff>0 THEN
 /* SET @C=10;
  SELECT @C; */
 SET  @IntRemai=@interestRemaining-InterestPaid;
 SET @intPai=@interestPaidnow+InterestPaid;
  
 UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

 SET InterestPaid=InterestPaid-@interestRemaining;
 CALL newDateConverted(@originalDueDate);
 
 END IF;






IF InterestPaid<=0.0 THEN

/* 
  SELECT @originalDueDate; */
UPDATE new_loan_appstoreamort SET instalment_due_date=@originalDueDate WHERE master2_id=loanIdUsed;

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



-- DROP TABLE IF EXISTS oneTimeUpdate;
CREATE  TABLE  IF NOT EXISTS   oneTimeUpdate(
id INTEGER NOT NULL,
 PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;  

-- INSERT INTO oneTimeUpdate VALUES(1);





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




/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysis;

DELIMITER ##

CREATE PROCEDURE   agingAnalysis()
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors,TrnId INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus,gaurantorName1,gaurantorContact1,gaurantorContact2,gaurantorName2 VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1x));

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


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8x))ENGINE = InnoDB
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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
 "-" ,
      "-" ,
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

-- CALL agingAnalysis()\G






/*  aging Analysis Staff */
DROP PROCEDURE IF EXISTS agingAnalysisStaff;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisStaff(IN staffId INT)
BEGIN
   
   
 DECLARE l_done,ID,arrears,numberOfGaurantors,TrnId INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus,gaurantorName1,gaurantorContact1,gaurantorContact2,gaurantorName2 VARCHAR(45); 

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE (loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed') AND gruop_id=staffId  ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1x));

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


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining  VARCHAR(60),principal_remaining  VARCHAR(60),interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8x))ENGINE = InnoDB
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
 
--  SELECT loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SELECT pl.applicant_account_name,m.mobile1,pl.trn_id,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.loan_cycle_status INTO customerName, customerContactNumber,TrnId,TrnDate,remainport,princeremain,interestRem,p_remain,theLoanStatus FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';

-- SELECT p_remain,i_remain,arrears;




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

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;


IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,gaurantorName1,gaurantorContact1,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;

SET customerName=NULL, customerContactNumber=NULL,TrnId=NULL,TrnDate=NULL,remainport=NULL,princeremain=NULL,interestRem=NULL,p_remain=NULL,theLoanStatus=NULL;

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
  "-" ,
      "-" ,
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
   
 DECLARE l_done,ID,arrears,numberOfGaurantors,numberOfGaurantors INT;

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

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId,theLoanStatus VARCHAR(100);

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

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,gaurantorName1,gaurantorContact1,gaurantorName2,gaurantorContact2,theTrnId, theLoanStatus VARCHAR(100);

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
SELECT COUNT(trn_id) INTO activeCustomers FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';

END $$
DELIMITER ;



DROP PROCEDURE IF EXISTS totalNumberOfActiveNewCustomers;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfActiveNewCustomers(OUT activeCustomers INT)
BEGIN
SELECT DISTINCT COUNT(new_loan_appstore.trn_id) INTO activeCustomers FROM pmms_loans.new_loan_appstore INNER JOIN pmms.account_created_store ON new_loan_appstore.applicant_account_number=account_created_store.account_number WHERE (new_loan_appstore.loan_cycle_status='Disbursed') AND account_created_store.creation_date=DATE(NOW());

END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS totalNumberOfCustomersPaid;
DELIMITER $$
CREATE  PROCEDURE totalNumberOfCustomersPaid(OUT activeCustomersPaid INT)
BEGIN

SELECT COUNT(trn_id) INTO @numberRenewed FROM new_loan_appstore WHERE loan_cycle_status='Renewed' AND trn_date=DATE(NOW()) AND (balance_due=(princimpal_amount+total_interest));


SELECT  COUNT( DISTINCT master1_id) INTO @totalPaidRenewed from new_loan_appstoreamort WHERE (instalment_status='P' OR instalment_status='PP')  AND instalment_paid_date=DATE(NOW());


SELECT (@totalPaidRenewed-@numberRenewed) INTO activeCustomersPaid;

END $$
DELIMITER ;




DROP PROCEDURE IF EXISTS totalPrincimpalBalance;
DELIMITER $$
CREATE  PROCEDURE totalPrincimpalBalance(OUT princimpalBalance INT)
BEGIN

SELECT SUM(TotalPrincipalRemaining) INTO princimpalBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

END $$
DELIMITER ;




DROP PROCEDURE IF EXISTS totalInterestBalance;
DELIMITER $$
CREATE  PROCEDURE totalInterestBalance(OUT interestBalance INT)
BEGIN

SELECT SUM(TotalInterestRemaining) INTO interestBalance FROM new_loan_appstore WHERE loan_cycle_status='Renewed' OR loan_cycle_status='Disbursed' ;

END $$
DELIMITER ;





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

SELECT SUM(InterestPaid) INTO InterestR FROM pmms.loandisburserepaystatement WHERE TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';


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
-- SELECT  SUM(credit) INTO princimpalRepaymentsMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01128%';
SELECT SUM(PrincipalPaid) INTO princimpalRepaymentsMade FROM pmms.loandisburserepaystatement WHERE TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';
IF ISNULL(princimpalRepaymentsMade) THEN
SET princimpalRepaymentsMade=0.0;
END IF;


END //
DELIMITER ;




DROP PROCEDURE IF EXISTS collectionsMade;

DELIMITER //

CREATE PROCEDURE collectionsMade(IN theDate DATE,OUT theCollectionsMade VARCHAR(100)) READS SQL DATA 


BEGIN
-- SELECT  SUM(credit) INTO princimpalRepaymentsMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01128%';
SELECT SUM(AmountPaid) INTO theCollectionsMade FROM pmms.loandisburserepaystatement WHERE TrnDate=theDate AND  loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed';
IF ISNULL(theCollectionsMade) THEN
SET theCollectionsMade=0.0;
END IF;


END //
DELIMITER ;



/* CALL princimpalLoanRepaymentsMade('2019-06-23',@princimpalRepaymentsMade);

SELECT @princimpalRepaymentsMade; */





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


/* CALL refundFromMobileMoneyPayableMade('2019-06-23',@mobileMoneyRefund);

SELECT @mobileMoneyRefund; */




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


-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number LIKE '01100%' OR account_number LIKE '01101%'
-- OR account_number LIKE '01102%' OR account_number LIKE '01103%' OR account_number LIKE '01104%' OR account_number LIKE '01105%' OR account_number LIKE '01106%'  OR account_number LIKE '01108%' OR account_number LIKE '01109%'
-- OR account_number LIKE '01110%' OR account_number LIKE '01111%'  OR account_number LIKE '01112%'  OR account_number LIKE '01136%');
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


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
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




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

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
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET fixedAssetsAndInvestmentDisp='0';
-- ELSE

-- SET fixedAssetsAndInvestmentDisp=@cash;


-- END IF;


-- END OUTER_BLOCK//
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


/* CALL MobileMoneyReceivableCreated('2019-06-24',@mobileMoneyc);

SELECT @mobileMoneyc; */







DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsCreated;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsCreated(IN theDate DATE,OUT otherRecePreMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO otherRecePreMade FROM pmms.general_ledger WHERE  trn_date=theDate AND  (debit_account_no LIKE '01117%' OR debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
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
INSERT INTO smsSummury VALUES("No.OfActiveLoans:",FORMAT(@activeCustomers,0));

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






-- CALL  smsSummuryReport() ;

update new_Loan_appstore SET TotalPrincipalRemaining=( princimpal_amount-TotalPrincipalPaid);

update new_Loan_appstore1 SET TotalPrincipalRemaining=( princimpal_amount-TotalPrincipalPaid);


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





                DROP PROCEDURE IF EXISTS collectionStatement;
DELIMITER //
CREATE PROCEDURE collectionStatement(IN dQn DATE) READS SQL DATA 
BEGIN

SET @counter = 0;
SELECT * FROM (
    SELECT (@counter:=@counter + 1) as counter, new_loan_appstore.applicant_account_name, new_loan_appstore.trn_id, SUM(loandisburserepaystatement.AmountPaid) AS collections
               FROM pmms.loandisburserepaystatement INNER JOIN pmms_loans.new_loan_appstore ON loandisburserepaystatement.loanTrnId = new_loan_appstore.trn_id WHERE loandisburserepaystatement.trnDate=dQn AND loandisburserepaystatement.AmountPaid > 0.0  AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed'
                GROUP BY new_loan_appstore.trn_id
                 UNION ALL
                SELECT "-", 'OVERALL TOTAL', '-', SUM(loandisburserepaystatement.AmountPaid) AS collections
               FROM pmms.loandisburserepaystatement INNER JOIN pmms_loans.new_loan_appstore ON loandisburserepaystatement.loanTrnId = new_loan_appstore.trn_id
                WHERE  loandisburserepaystatement.TrnDate = dQn AND loandisburserepaystatement.AmountPaid > 0.0 AND NOT loandisburserepaystatement.LoanStatusReport='RenewedClosed'
) as result
ORDER BY counter ASC;
END//
DELIMITER ;


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



-- 1	71137	newloan05502062210	Disbursed


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


       
DROP PROCEDURE IF EXISTS theGaurantorDetails;
DELIMITER //
CREATE PROCEDURE theGaurantorDetails(IN theLoanTrnId INT) READS SQL DATA 
BEGIN


SELECT * FROM gaurantors WHERE loanTrnId=theLoanTrnId;

END//

 DELIMITER ;


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

  
UPDATE pmms_loans.new_loan_appstore SET loan_cycle_status='RenewedClosed' WHERE trn_id=theLoanId;
UPDATE pmms_loans.new_loan_appstore1 SET loan_cycle_status='RenewedClosed' WHERE trn_id=theLoanId;
UPDATE pmms.loandisburserepaystatement SET LoanStatusReport='RenewedClosed' WHERE loanTrnId=theLoanId;
END $$

DELIMITER ;



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
id_1 INTEGER, -- 0
company_name VARCHAR(60),-- 1
company_branch VARCHAR(60),-- 2
company_box_number VARCHAR(60),-- 3
customer_name VARCHAR(60),-- 4
staff_name VARCHAR(60),-- 5
loan_taken VARCHAR(60),-- 6
date_taken  VARCHAR(60),-- 7
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
-- select date_takenL;
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

-- select date_takenL;
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
-- bc1q5qnzng4uk64gqcqvfsra9euf6z7t7dgzlcpfe2

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

END ##
DELIMITER ;



/* LOAN RECEIPT PRINTING */

DROP PROCEDURE IF EXISTS loanStatementDetails;

DELIMITER ##

CREATE PROCEDURE   loanStatementDetails(IN SloanTrnId VARCHAR(45))
BEGIN

DROP TABLE IF EXISTS loanStatementtDetailsTable;

CREATE TEMPORARY  TABLE loanStatementtDetailsTable(
`id` INTEGER NOT NULL AUTO_INCREMENT, 
`trn_date` DATE,
`amount_paid` VARCHAR(60),
`princimpal_paid` VARCHAR(60),
`interest_paid` VARCHAR(60),
`amount_remaining` VARCHAR(60),
`princimpal_remaining`  VARCHAR(60),
`interest_remaining`  VARCHAR(60),
 PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

  

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

END  ##

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
END OUTER_BLOCK //


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
    DECLARE cur CURSOR FOR SELECT applicant_account_number FROM pmms_loans.new_Loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed';
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

-- 21/01/2023	21/01/2023	Loan processing fees from BYAMUGISHA BRIGHT 0702337171 Processed on 21/01/2023
--   Dated 21/01/2023	20000.0	-	545200.0

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

3127	KAGOYE CHARLES 0751325861	05502011610

127087	KARUHANGA  GEOFREY  0751225049	05502090310
-- CALL createNewLoan('05502090310',250000,240,30,'2024-03-11',1,'DAYS',10001,1.0,'2024-03-11',10004,0,1,'BTN34249',1) ;


--  CALL RepayTheLoanNow('05502090310',238000.0,'BTN34249',10001,'2024-04-07',10001);
79916	KARUHANGA  GEOFREY  0751225049	BODA BODA	240	250000	52008	9992	0	62000	30 DAYS	11/03/2024	10011	Disbursed
-- CALL RepayTheLoanNow('05502147210',11500.0,'BTN34249',10001,'2023-03-31',10001);
-- CALL RepayTheLoanNow('05502147210',12000.0,'BTN34249',10001,'2023-04-05',10001);


-- CALL RepayTheLoanNow('05502147210',6000.0,'BTN34249',10001,'2023-04-19',10001);
-- CALL RepayTheLoanNow('05502147210',5000.0,'BTN34249',10001,'2023-04-27',10001);
-- CALL RepayTheLoanNow('05502147210',5000.0,'BTN34249',10001,'2023-04-29',10001);

-- CALL RepayTheLoanNow('05502147210',5000.0,'BTN34249',10001,'2023-04-30',10001);

-- CALL RepayTheLoanNow('05502147210',5000.0,'BTN34249',10001,'2023-05-02',10001);

-- CALL RepayTheLoanNow('05502007710',200000.0,'BTN34249',10001,'2023-06-02',10001);

-- CALL createNewLoan('05502040910',300000,24,12,'2023-03-09',4,'MONTHS',10001,1.0,'2023-03-09',10001,0,1,'BTN34249',1) ;

1250	TUMUKUNDE GLADYS 0753074849	05502007710


 CALL RepayTheLoanNow('05502006310',2350000.0,'BTN34249',10001,'2025-02-25',10001);
 CALL createNewLoan('05502006310',2000000,36,12,'2024-07-20',4,'MONTHS',10001,1.0,'2024-07-20',10001,0,1,'BTN34249',1) ;