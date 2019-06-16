DROP TABLE IF EXISTS `interestcomputed`;

CREATE TABLE `interestcomputed` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `loanId` varchar(45) DEFAULT '0',
  `DueDate` date NOT NULL DEFAULT '1970-01-01',
  `PrincimpalInvolved` double DEFAULT NULL,
  `InterestInvolved` double DEFAULT NULL,
  `InterestPaidIn` double DEFAULT NULL,
  `InterestInvoRemaining` double DEFAULT NULL,
    `totalInterstInv` double DEFAULT NULL,
	`totalInterstInvPaid` double DEFAULT NULL,
	`totalInterstInvRemaing` double DEFAULT NULL,
  `loanStatusI` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


DROP PROCEDURE IF EXISTS InterestManagementForLenders;

DELIMITER //

CREATE PROCEDURE InterestManagementForLenders() READS SQL DATA BEGIN

 DECLARE loanIdZ VARCHAR(30); /* This is the unique itentfier for an active loan */


 DECLARE l_done INTEGER DEFAULT 0; /*  Variable controlling the cusor*/

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/

  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,
  totalCompuInteresRemaing Double; /*  InterestRate is the rate used---*/

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed'; /* cursor for iterating through each borrower's account*/
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;/*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingLoanIds; /* Open the cursor holding loan ids for each customer*/


accounts_loop: LOOP /*Loop through the loanIds */

 FETCH forSelectingLoanIds into loanIdZ;/*Pick the loan id into the variable loanIdz */

 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ;/*Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 IF l_done=1 THEN/*check whether the cusor sitll holds more values and if not terminate loop*/

LEAVE accounts_loop;

 END IF;


IF @theTenure='1.0 MONTHS' THEN /*  Only single monthly isntalment loans should be considered*/

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO 
lastDate,
InterestRate,
princinpalRemaining
FROM new_loan_appstore WHERE loan_id=loanIdZ; /* The due date since the last instalment is stored in the instalment_next_due_date column*/

SELECT instalment_due_date INTO
 originalDueDate
 FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;/* The instalment due date is the last due date*/

SELECT interestinvoRemaining INTO 
@totalInterest 
FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;/* The last interest computed*/



Date_loop: LOOP /* Loop through the due dates since the last duedate*/
SET @Ended=0;
/*  SELECT lastDate;  *//*Testing*/


IF lastDate>=current_date() THEN /* Test whether the arrears last date is more than today's date*/

SET @Ended=1;

LEAVE Date_loop;

END IF;


IF @Ended=0 THEN 

SET interestInveolved=((InterestRate*princinpalRemaining)/1200);/* Compute the insterest using the remaining princimpal amount*/

SET @totalInterest=@totalInterest+interestInveolved; /* Compute the total interest*/





SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;


CALL updateAccountsAfter(loanIdZ,lastDate,interestInveolved,princinpalRemaining);/* Update the original loan schedule*/


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,princinpalRemaining,interestInveolved,0.0,interestInveolved,@totalInterest,0.0,@totalInterest,'Pending'); 

END IF;

END LOOP Date_loop;

END IF;



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

END //

DELIMITER ;







DROP PROCEDURE IF EXISTS updateAccountsAfter;

DELIMITER //

/* This procedure updates the original loan schedule*/

CREATE PROCEDURE updateAccountsAfter(IN loanId VARCHAR(30),IN newDateDue DATE,IN interestComputed Double,IN princimpalRemaining Double) READS SQL DATA BEGIN

/* IN loanId VARCHAR(30)=This is the loan id that identifies each active loan uniquely */
/* IN newDateDue DATE=This is the Due date for the newly created interest instalment */
/* IN interestComputed Double=This is the interest generated for the new due date */
/* IN princimpalRemaining Double=This is the remaining principal amount */

 SELECT loanId,interestComputed,princimpalRemaining,newDateDue;/* For testing purposes to see the value of interestcomputed passed*/

/* The idea is to get the already existing values for the initial interest instalment,the actual instalment remaining,interest remaining,intial instalmet then we change these values with the interest computed */


SET @sql_text1 = concat(CAST("SELECT interest_amount,InterestRemaing, instalment_amount,InstalmentRemaining INTO
 @interestAmount, 
 @interestRemaining,
 @InstalmentAmount,
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

SELECT  total_interest, balance_due,TotalInterestRemaining INTO 
@totalInterestAmount,
@totalInstalmentsRemaining, 
@totalInterestRemaining 
FROM new_loan_appstore WHERE loan_id=loanId;


/* Normally if the result set is empty, mysql will return an empty set with null values for each element,

 to avoid null pointers we initalise our variables with expected default values */
 
IF @interestAmount IS NULL THEN SET @interestAmount=0; END IF;

IF @interestRemaining IS NULL THEN SET @interestRemaining=0; END IF;

IF @InstalmentAmount IS NULL THEN SET @InstalmentAmount=0; END IF;

IF @instalmentRemaining IS NULL THEN SET @instalmentRemaining=0; END IF;

IF @instalmentRemaining IS NULL THEN SET @instalmentRemaining=0; END IF;

IF @interestRemaining IS NULL THEN SET @interestRemaining=0; END IF;

IF @totalInstalmentsRemaining IS NULL THEN SET @totalInstalmentsRemaining=0; END IF;

IF @totalInterestRemaining IS NULL THEN SET @totalInterestRemaining=0; END IF;




SET @instalmentRemaining=@instalmentRemaining+interestComputed,
@interestRemaining=@interestRemaining+interestComputed,
@interestAmount=@interestAmount+interestComputed,
@InstalmentAmount=@InstalmentAmount+interestComputed;

SET @totalInstalmentsRemaining=@totalInstalmentsRemaining+interestComputed,
@totalInterestRemaining=@totalInterestRemaining+interestComputed,
@totalInterestAmount=@totalInterestAmount+interestComputed;


/* SELECT @instalmentRemaining,@interestRemaining,@totalInstalmentsRemaining,@totalInterestRemaining,@interestAmount,@totalInterestAmount,loanId; */

UPDATE new_loan_appstoreamort SET 
InstalmentRemaining=@instalmentRemaining,
InterestRemaing=@interestRemaining,
interest_amount=@interestAmount,
instalment_amount=@InstalmentAmount
 WHERE master2_id=loanId;

UPDATE new_loan_appstore SET 
balance_due=@totalInstalmentsRemaining,
TotalInterestRemaining=@totalInterestRemaining,
instalment_next_due_date=newDateDue,
total_interest=@totalInterestAmount
 WHERE loan_id=loanId;


UPDATE new_loan_appstore1 SET 
balance_due=@totalInstalmentsRemaining,
TotalInterestRemaining=@totalInterestRemaining,
instalment_next_due_date=newDateDue,
total_interest=@totalInterestAmount
 WHERE loan_id=loanId;



END //

DELIMITER ;







DROP PROCEDURE IF EXISTS newDateConverted;

DELIMITER //

CREATE PROCEDURE newDateConverted(INOUT  oldNewDate DATE) READS SQL DATA BEGIN
DECLARE D1,D2,D3,convertedDate VARCHAR(10);


SET D1=SUBSTRING(oldNewDate,1,4);
SET D2=SUBSTRING(oldNewDate,6,2);
SET D3=SUBSTRING(oldNewDate,9,2);
/* SELECT oldNewDate; */
IF D3>=28 THEN
SET D3=28;
END IF;

IF D2=12 THEN
SET D1=D1+1;
SET D2='01';
SET convertedDate=CONCAT(D1,'-',D2,'-',D3);
SET oldNewDate=CAST(convertedDate AS DATE);
ELSE
SET D2=D2+1;
SET convertedDate=CONCAT(D1,'-',D2,'-',D3);
SET oldNewDate=CAST(convertedDate AS DATE);
END IF;


/* SELECT oldNewDate; */
END //

DELIMITER ;



DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN /*One of the most important idea to note is that when a payment interest instalment is made, the interest due date has to be shifted one date ahead*/

DECLARE numberOfIds INTEGER;


 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdUsed;

IF @theTenure='1.0 MONTHS' THEN

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


  SELECT @originalDueDate;
UPDATE new_loan_appstoreamort SET instalment_due_date=@originalDueDate WHERE master2_id=loanIdUsed;

LEAVE interestPayment_Loop;

END IF;


END LOOP interestPayment_Loop;

END IF;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS stopInterestRate;

DELIMITER //

CREATE PROCEDURE stopInterestRate(IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN

DECLARE numberOfIds INTEGER;

SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;

IF numberOfIds=1 THEN



UPDATE new_loan_appstore SET interest_rate='0.0' WHERE loan_id=loanIdUsed;
UPDATE new_loan_appstore1 SET interest_rate='0.0' WHERE loan_id=loanIdUsed;

END IF;


END //

DELIMITER ;



DROP PROCEDURE IF EXISTS createFirstInterestIntalment;

DELIMITER //

CREATE PROCEDURE createFirstInterestIntalment(IN loanIdUsed VARCHAR(30),IN dueDate DATE,IN princinpalRemaining DOUBLE,IN interestInvo DOUBLE) READS SQL DATA BEGIN

INSERT INTO interestComputed VALUES(null,loanIdUsed,dueDate,princinpalRemaining,interestInvo,0.0,interestInvo,interestInvo,0.0,interestInvo,'Pending'); 


END //

DELIMITER ;

