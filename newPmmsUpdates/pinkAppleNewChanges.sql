






-- -----------------------------------------------------
-- amortizeLoan

/* This is for selecting the items that will be requiring approval*/

/* The item specified here will be linked to the actual workflow. Here we specify the levels of approval the the locations for approval */

/* Basically they will be three locations for approval and that is Town,Area and Central management */
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS amortizeLoan;

DELIMITER $$

CREATE PROCEDURE amortizeLoan(IN data JSON) BEGIN

  -- SELECT data; 

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=1 THEN

CALL flatAmortzation(data);

END IF;

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=2 THEN
CALL reducingBalanceReducingInstalmentAmort(data);
END IF;

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=3 THEN

CALL reducingBalanceFixedInstalmentAmort(data);
END IF;

END $$

DELIMITER ;







-- -----------------------------------------------------
-- flatAmortzation

/* This is for selecting the items that will be requiring approval*/

/* The item specified here will be linked to the actual workflow. Here we specify the levels of approval the the locations for approval */

/* Basically they will be three locations for approval and that is Town,Area and Central management */
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS flatAmortzation;

DELIMITER $$

CREATE PROCEDURE flatAmortzation(IN data JSON) BEGIN
DECLARE counter,counterX INT;
DECLARE thePrincipalIntalmentX,theInterestInstalmentX DOUBLE;
DECLARE totalPrincipalX,totalInterestX DOUBLE;
-- select data;
-- SELECT JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')) ,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed')) ;
SET counter=0;
SET counterX=1;
SET totalPrincipalX=0.0,totalInterestX=0.0;
IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =3 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =6 OR JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')) =8 THEN
SET counterX=2; 
END IF;

SET @dueDate=DATE(NOW());

SET thePrincipalIntalmentX=ROUND(flatPrincipalInstalment(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))),0);

SET theInterestInstalmentX=ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0);

-- SELECT thePrincipalIntalmentX,theInterestInstalmentX;

REPEAT 

SET @period= thePeriod(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')));

SET @dueDate = concat(CAST("SELECT " AS CHAR CHARACTER SET utf8), CAST("DATE_ADD('" AS CHAR CHARACTER SET utf8),@dueDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST("," AS CHAR CHARACTER SET utf8),CAST("INTERVAL " AS CHAR CHARACTER SET utf8),counterX,  CAST(" " AS CHAR CHARACTER SET utf8),@period,CAST(" )" AS CHAR CHARACTER SET utf8),CAST(" INTO @dueDate" AS CHAR CHARACTER SET utf8));
--  select  @dueDate;
  PREPARE stmt2 FROM @dueDate;
  EXECUTE stmt2;
DROP PREPARE stmt2;

SET counter=counter+1;

SET totalPrincipalX=totalPrincipalX+thePrincipalIntalmentX,totalInterestX=totalInterestX+theInterestInstalmentX;


IF counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) THEN

SET thePrincipalIntalmentX=(thePrincipalIntalmentX+(JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount'))-totalPrincipalX)),theInterestInstalmentX=(theInterestInstalmentX+((ROUND(interestComputedY( JSON_UNQUOTE(JSON_EXTRACT(data,'$.txnAmount')),(JSON_UNQUOTE(JSON_EXTRACT(data,'$.interestRateUsed'))),numberOfYears(JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerLoanTenure')),JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortCyle')))),0))-totalInterestX));

END IF;

-- SELECT counter,@dueDate,@dueDate,ROUND(thePrincipalIntalmentX,0),0.0,0.0,0.0,ROUND(thePrincipalIntalmentX,0),ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND((thePrincipalIntalmentX+theInterestInstalmentX),0),0.0,ROUND((thePrincipalIntalmentX+theInterestInstalmentX),0),1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId'));

INSERT INTO microLoanAmortShedule VALUES(NULL,counter,@dueDate,@dueDate,ROUND(thePrincipalIntalmentX,0),0.0,0.0,0.0,ROUND(thePrincipalIntalmentX,0),ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND(theInterestInstalmentX,0),0.0,0.0,0.0,ROUND((thePrincipalIntalmentX+theInterestInstalmentX),0),0.0,ROUND((thePrincipalIntalmentX+theInterestInstalmentX),0),1,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId')));




UNTIL counter=(JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanTenureUsed'))) END REPEAT;


END $$

DELIMITER ;




-- -----------------------------------------------------
-- instalmentCalculated

/* This is a function for computing the princimpal instalment */

/* ONLY flat and reducting interest with reducing instalment will use this function"*/
-- RowDataPacket {
  -- data: '{"loanId": 900000010, "userId": 1000000015, "narration": "BORROW-MICROLOAN", "txnAmount": 5000000, "customerId": 40000023, "loanCycles": 0, "narration2": 754444444.0, "productCode": 400, "txnPostType": "CREDIT", "txnDetailsId": 2004, "loanTenureUsed": 7, "interestRateUsed": 2.0, "microLoanPurpose": "To buy a piece of land", "theStationLocationId": 1300, "microloanCustomerAmortCyle": 2, "microloanCustomerAmortType": 3, "microloanCustomerLoanLimit": 400000.0, "microloanCustomerLoanTenure": 7, "microloanCustomerAccrualDays": 2}'
-- }

DROP FUNCTION IF EXISTS instalmentCalculated;

DELIMITER $$

CREATE FUNCTION instalmentCalculated(thePrincipalAmount DOUBLE,rateUsed DOUBLE,tenure DOUBLE,loanCycle INT) 
RETURNS DOUBLE
DETERMINISTIC
BEGIN

DECLARE pInstalment,denomi DOUBLE;

set denomi=(POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure)-1);

IF denomi>0 THEN

SET pInstalment= ((thePrincipalAmount*((actualRateUsed(rateUsed,loanCycle))/100))*POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure))/(POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure)-1);

-- SET pInstalment=1000/tenure;

ELSE 

SET pInstalment=thePrincipalAmount/tenure;

END IF;

RETURN pInstalment;

END $$
DELIMITER ;



DROP PROCEDURE  IF EXISTS instalmentCalculated1;

DELIMITER $$

CREATE PROCEDURE  instalmentCalculated1(thePrincipalAmount DOUBLE,rateUsed DOUBLE,tenure DOUBLE,loanCycle INT) 
BEGIN

DECLARE pInstalment,denomi DOUBLE;

set denomi=(POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure)-1);

SELECT denomi;

IF denomi>0 THEN

SET pInstalment= ((thePrincipalAmount*((actualRateUsed(rateUsed,loanCycle))/100))*POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure))/(POW((1+((actualRateUsed(rateUsed,loanCycle))/100)),tenure)-1);

ELSE 

SET pInstalment=thePrincipalAmount/tenure;

END IF;

SELECT pInstalment;

END $$
DELIMITER ;







-- -----------------------------------------------------
-- actualRateUsed

/* This is a function for testing whether the company has already been registered */

/* ONLY one company should be permited and to prevent this we have to put this"*/


DROP FUNCTION IF EXISTS actualRateUsed;

DELIMITER $$

CREATE FUNCTION actualRateUsed(theInterestRate INT, amortCycle INT) 
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

CREATE PROCEDURE createNewLoan(IN accountNumber VARCHAR(60),IN amount DOUBLE,IN rate DOBULE,IN tenure INT,IN currentDate DATE,IN periodTypeNumber INT,IN periodTypeString VARCHAR(60),IN userId INT,IN interestRegime INT,IN amortizationDate DATE,IN loanOfficerId INT,OUT postingStatus VARCHAR(30)) BEGIN

DECLARE theLoanTxnId,theLoanId VARCHAR();

SELECT theLoanId(),CONCAT("newloan",accountNumber) INTO theLoanTxnId,theLoanId;

INSERT INTO new_loan_appstore VALUES(theLoanTxnId,amortizationDate,theLoanId,);


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

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=1 THEN

CALL flatAmortzation(data);

END IF;

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=2 THEN
CALL reducingBalanceReducingInstalmentAmort(data);
END IF;

IF JSON_UNQUOTE(JSON_EXTRACT(data,'$.microloanCustomerAmortType'))=3 THEN

CALL reducingBalanceFixedInstalmentAmort(data);
END IF;

END $$

DELIMITER ;
