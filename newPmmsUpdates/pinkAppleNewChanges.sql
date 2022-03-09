


/* R*P*(1+R)N/(1+R)N-1 */

-- -----------------------------------------------------
-- reducingBalanceFixedInstalmentAmort

/* This is for selecting the items that will be requiring approval*/

/* The item specified here will be linked to the actual workflow. Here we specify the levels of approval the the locations for approval */

/* Basically they will be three locations for approval and that is Town,Area and Central management */
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS reducingBalanceFixedInstalmentAmort;

DELIMITER $$

CREATE PROCEDURE reducingBalanceFixedInstalmentAmort(IN data JSON) BEGIN

DECLARE counter,counterX INT;

DECLARE thePrincipalIntalmentX,theInterestInstalmentX,theInitialInstalmentX,theTxnAmount,thePrincipalIntalmentX1 DOUBLE;
DECLARE totalPrincipalX,totalInterestX DOUBLE;
--  select data;
-- SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed')) ;
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0;
IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =3 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =6 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =8 THEN
SET counterX=2; 
END IF;

SET @dueDate=DATE(NOW());



SET theInterestInstalmentX=ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0);

-- SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'));


-- SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure'));

SET theInitialInstalmentX=ROUND(instalmentCalculated(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),0);
-- SELECT theInitialInstalmentX;
SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX,theTxnAmount=JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount'));


/* SET thePrincipalIntalmentX=thePrincipalIntalmentX1;                                */
REPEAT 

SET @period= thePeriod(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')));




SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX1,totalInterestX=totalInterestX+theInterestInstalmentX;



/* 
IF counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount'))-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0))-totalInterestX));


END IF; */


/* SELECT theInitialInstalmentX,theInterestInstalmentX,thePrincipalIntalmentX; */
SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX;
/* SELECT theInitialInstalmentX,theInterestInstalmentX,thePrincipalIntalmentX; */
SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX1;




INSERT INTO microLoanAmortShedule VALUES(NULL,counter,@dueDate,@dueDate,ROUND(thePrincipalIntalmentX1,0),0.0,0.0,0.0,ROUND(thePrincipalIntalmentX1,0),ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND((theInitialInstalmentX),0),0.0,ROUND((theInitialInstalmentX),0),1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId')));


SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0);

-- SET theInitialInstalmentX=ROUND(instalmentCalculated(theTxnAmount,(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),0);

UNTIL counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) END REPEAT;


END $$

DELIMITER ;




-- -----------------------------------------------------
-- reducingBalanceReducingInstalmentAmort

/* This is for selecting the items that will be requiring approval*/

/* The item specified here will be linked to the actual workflow. Here we specify the levels of approval the the locations for approval */

/* Basically they will be three locations for approval and that is Town,Area and Central management */
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS reducingBalanceReducingInstalmentAmort;

DELIMITER $$

CREATE PROCEDURE reducingBalanceReducingInstalmentAmort(IN data JSON) BEGIN

DECLARE counter,counterX INT;

DECLARE thePrincipalIntalmentX,theInterestInstalmentX,theInitialInstalmentX,theTxnAmount,thePrincipalIntalmentX1 DOUBLE;
DECLARE totalPrincipalX,totalInterestX DOUBLE;
--  select data;
-- SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed')) ;
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0;
IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =3 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =6 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =8 THEN
SET counterX=2; 
END IF;

SET @dueDate=DATE(NOW());
 
SET theInterestInstalmentX=ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0);
-- SELECT theInterestInstalmentX;
--  SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'));


--  SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure'));

SET theInitialInstalmentX=ROUND(instalmentCalculated(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),0);
--  SELECT theInitialInstalmentX;
SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX,theTxnAmount=JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount'));


/* SET thePrincipalIntalmentX=thePrincipalIntalmentX1;                                */
REPEAT 

SET @period= thePeriod(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')));




SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX1,totalInterestX=totalInterestX+theInterestInstalmentX;




IF counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) THEN

SET thePrincipalIntalmentX1=(thePrincipalIntalmentX1+(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount'))-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0))-totalInterestX));


END IF; 


/* SELECT theInitialInstalmentX,theInterestInstalmentX,thePrincipalIntalmentX; */
-- SET thePrincipalIntalmentX1=theInitialInstalmentX-theInterestInstalmentX;
/* SELECT theInitialInstalmentX,theInterestInstalmentX,thePrincipalIntalmentX; */
SET theTxnAmount=theTxnAmount-thePrincipalIntalmentX1;




INSERT INTO microLoanAmortShedule VALUES(NULL,counter,@dueDate,@dueDate,ROUND(thePrincipalIntalmentX1,0),0.0,0.0,0.0,ROUND(thePrincipalIntalmentX1,0),ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND((thePrincipalIntalmentX1+theInterestInstalmentX),0),0.0,ROUND((thePrincipalIntalmentX1+theInterestInstalmentX),0),1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId')));


SET theInterestInstalmentX=ROUND(interestComputedY(theTxnAmount,(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0);

-- SET theInitialInstalmentX=ROUND(instalmentCalculated(theTxnAmount,(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle'))),0);

UNTIL counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) END REPEAT;


END $$

DELIMITER ;









-- -----------------------------------------------------
-- createNewLoan

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createNewLoan;

DELIMITER $$

CREATE PROCEDURE createNewLoan(IN accountNumber VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN currentDate DATE,IN periodTypeNumber DOUBLE,IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime DOUBLE,IN amortizationDate DATE,IN loanOfficerId INT,IN thePeriodSet DOUBLE,IN theCompuM INT) BEGIN
DECLARE theLoanTxnId INT;
DECLARE theLoanId VARCHAR(60);

--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;
 
START TRANSACTION;

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;

-- SELECT theLoanTxnId,theLoanId;
-- SELECT DATE_FORMAT(currentDate, '%Y-%m-%d');
CALL createAmortzationSchedule(theLoanTxnId,theLoanId,amount,rate,tenure,periodTypeNumber,interestRegime,amortizationDate,thePeriodSet,theCompuM,@totalInterestComputed);

-- IN theLoanTxnId INT, IN theLoanId VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure INT,IN periodTypeNumber INT,IN interestRegime INT,IN amortizationDate DATE,IN thePeriodSet INT, OUT totalInterestComputed DOUBLE
-- INSERT INTO new_loan_appstore VALUES(theLoanTxnId,amortizationDate,theLoanId,);


-- trn_id                            | varchar(50)  | NO   | PRI | NULL    |       |
-- | trn_date                          | date         | NO   |     | NULL    |       |
-- | loan_id                           | varchar(100) | NO   | UNI | NULL    |       |
-- | total_instalments                 | varchar(45)  | YES  |     | NULL    |       |
-- | remaining_instalments             | varchar(45)  | YES  |     | NULL    |       |
-- | princimpal_amount                 | varchar(45)  | YES  |     | NULL    |       |
-- | total_interest                    | varchar(45)  | YES  |     | NULL    |       |
-- | total_loanAmount                  | varchar(45)  | YES  |     | NULL    |       |
-- | balance_due                       | varchar(45)  | YES  |     | NULL    |       |
-- | instalment_start_date             | date         | NO   |     | NULL    |       |
-- | instalment_next_due_date          | date         | NO   |     | NULL    |       |
-- | instalment_end_date               | date         | NO   |     | NULL    |       |
-- | interest_rate                     | varchar(45)  | YES  |     | NULL    |       |
-- | applicant_account_name            | varchar(200) | NO   |     | NULL    |       |
-- | loan_cycle_status                 | varchar(45)  | YES  |     | NULL    |       |
-- | trn_time                          | time         | NO   |     | NULL    |       |
-- | instalments_paid                  | varchar(45)  | YES  |     | NULL    |       |
-- | instalment_amount                 | varchar(45)  | YES  |     | NULL    |       |
-- | loan_tenure                       | varchar(45)  | YES  |     | NULL    |       |
-- | applicant_account_number          | varchar(45)  | YES  |     | NULL    |       |
-- | inputter_id                       | varchar(10)  | YES  |     | NULL    |       |
-- | authoriser_id                     | varchar(10)  | YES  |     | NULL    |       |
-- | gruop_id                          | varchar(30)  | YES  |     | NULL    |       |
-- | GroupId                           | varchar(50)  | NO   |     | G0001   |       |
-- | GroupName                         | varchar(100) | NO   |     | GROUP1  |       |
-- | SecurityLoan                      | varchar(500) | YES  |     | NULL    |       |
-- | OtherGroups2                      | varchar(100) | NO   |     | Other2  |       |
-- | OtherGroups3                      | varchar(100) | NO   |     | Other3  |       |
-- | OtherGroups4                      | varchar(100) | NO   |     | Other4  |       |
-- | TotalInterestPaid                 | varchar(100) | NO   |     | 0.0     |       |
-- | TotalInterestRemaining            | varchar(100) | NO   |     | 0.0     |       |
-- | TotalPrincipalPaid                | varchar(100) | NO   |     | 0.0     |       |
-- | TotalPrincipalRemaining           | varchar(100) | NO   |     | 0.0     |       |
-- | TotalAccumulatedInterestPaid      | varchar(100) | NO   |     | 0.0     |       |
-- | TotalAccumulatedInterestRemaining | varchar(100) | NO   |     | 0.0     |       |
-- | TotalLoanPenaltyPaid              | varchar(100) | NO   |     | 0.0     |       |
-- | TotalLoanPenaltyRemaining         | varchar(100) | NO   |     | 0.0     |       |
-- | TotalAccruedInterestRemaining     | varchar(100) | NO   |     | 0.0     |       |
-- | TotalAccruedInterestPaid          | varchar(100) | NO   |     | 0.0     |



COMMIT;

SELECT 1  AS theMessage;

END $$

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


-- ALTER TABLE sequencenumbers modify column  trn_id enum('1') NOT NULL;





-- -----------------------------------------------------
-- createAmortzationSchedule

-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS createAmortzationSchedule;

DELIMITER $$

CREATE PROCEDURE createAmortzationSchedule(IN theLoanTxnId INT, IN theLoanId VARCHAR(60),IN amount DOUBLE,IN rate DOUBLE,IN tenure DOUBLE,IN periodTypeNumber DOUBLE,IN interestRegime DOUBLE,IN amortizationDate DATE,IN thePeriodSet DOUBLE,IN theCompuM INT, OUT totalInterestComputed DOUBLE) BEGIN

       DECLARE counter,counterX INT;
DECLARE thePrincipalIntalmentX,theInterestInstalmentX,totalPrincipalX,totalInterestX,actualTotalInterest,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal,theInitialInstalmentX,theTxnAmount,thePrincipalIntalmentX1  DOUBLE;



--  DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--   SELECT 0 AS theMessage;
--   ROLLBACK;
  
-- END;
 
START TRANSACTION;

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


  SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;

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


SELECT counter,theInterestInstalmentX,thePrincipalIntalmentX;
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

  SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;
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


  SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


IF counter=tenure THEN
SELECT amount,totalPrincipalX;
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


  SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


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


  SELECT counter, thePrincipalIntalmentX,theInterestInstalmentX,openingPrincimpalBal,closingPrincipalBal,openingInterestBal,closingInterestBal;


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


COMMIT;

SELECT 1  AS theMessage;




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

/* ONLY one company should be permited and to prevent this we have to put this"*/


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
