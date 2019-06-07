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

 DECLARE loanIdZ VARCHAR(30);

  DECLARE numberOfIds INTEGER;

 DECLARE l_done INTEGER DEFAULT 0;

 DECLARE lastDate,originalDueDate DATE;

  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,totalCompuInteresRemaing Double;

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 OPEN forSelectingLoanIds;


accounts_loop: LOOP 

 FETCH forSelectingLoanIds into loanIdZ;
 


/*  SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdZ; */

 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ;
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


IF @theTenure='1.0 MONTHS' THEN

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdZ;

SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;

SELECT interestinvoRemaining INTO @totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;

 SELECT @totalInterest;


SET @counter=0;



Date_loop: LOOP 



IF lastDate>=current_date() THEN

CALL updateAccountsAfter(loanIdZ,lastDate,@totalInterest,princinpalRemaining);

LEAVE Date_loop;

END IF;


/* IF @counter<=1 THEN



SET interestInveolved=@ExistingInterest;

SET @totalInterest=@totalInterest+@ExistingInterest;

ELSEIF  @counter>1 THEN
 */
SET interestInveolved=((InterestRate*princinpalRemaining)/1200);

SET @totalInterest=@totalInterest+interestInveolved;

/* END IF; */


SELECT @counter;

SELECT @totalInterest;

SET @counter=@counter+1;

SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;

/* SET totalComputedInterest=((@counter*InterestRate*princinpalRemaining)/1200); */


/* 
SELECT lastDate,InterestRate,originalDueDate,princinpalRemaining,loanId,interestInveolved; */


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,princinpalRemaining,interestInveolved,0.0,interestInveolved,@totalInterest,0.0,@totalInterest,'Pending'); 




END LOOP Date_loop;

END IF;






SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingLoanIds;

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


DROP PROCEDURE IF EXISTS updateAccountsAfter;

DELIMITER //

CREATE PROCEDURE updateAccountsAfter(IN loanId VARCHAR(30),IN newDateDue DATE,IN interestComputed Double,IN princimpalRemaining Double) READS SQL DATA BEGIN


SET @sql_text1 = concat(CAST("SELECT interest_amount, InstalmentRemaining,InterestRemaing, instalment_amount INTO @interestAmount, @instalmentRemaining,@interestRemaining,@InstalmentAmount FROM new_loan_appstoreamort WHERE master2_id='" AS CHAR CHARACTER SET utf8),loanId, CAST("'" AS CHAR CHARACTER SET utf8));

/* SELECT @sql_text1; */
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


SELECT  total_interest, balance_due,TotalInterestRemaining INTO @totalInterestAmount,@totalInstalmentsRemaining, @totalInterestRemaining FROM new_loan_appstore WHERE loan_id=loanId;
/* 
SELECT @instalmentRemaining,@interestRemaining,@interestAmount,@InstalmentAmount,loanId; */

IF @instalmentRemaining IS NULL THEN
SET @instalmentRemaining=0;
END IF;

IF @interestRemaining IS NULL THEN
SET @interestRemaining=0;
END IF;

IF @totalInstalmentsRemaining IS NULL THEN
SET @totalInstalmentsRemaining=0;
END IF;

IF @totalInterestRemaining IS NULL THEN
SET @totalInterestRemaining=0;
END IF;

SET @instalmentRemaining=princimpalRemaining+interestComputed,@interestRemaining=interestComputed,@interestAmount=interestComputed,@InstalmentAmount=@InstalmentAmount+interestComputed;

SET @totalInstalmentsRemaining=princimpalRemaining+interestComputed,@totalInterestRemaining=interestComputed,@totalInterestAmount=@totalInterestAmount+interestComputed;


/* SELECT @instalmentRemaining,@interestRemaining,@totalInstalmentsRemaining,@totalInterestRemaining,@interestAmount,@totalInterestAmount,loanId;
 */
UPDATE new_loan_appstoreamort SET InstalmentRemaining=@instalmentRemaining,InterestRemaing=@interestRemaining,interest_amount=@interestAmount,instalment_amount=@InstalmentAmount WHERE master2_id=loanId;
UPDATE new_loan_appstore SET balance_due=@totalInstalmentsRemaining,TotalInterestRemaining=@totalInterestRemaining,instalment_next_due_date=newDateDue,total_interest=@totalInterestAmount WHERE loan_id=loanId;
UPDATE new_loan_appstore1 SET balance_due=@totalInstalmentsRemaining,TotalInterestRemaining=@totalInterestRemaining,instalment_next_due_date=newDateDue,total_interest=@totalInterestAmount WHERE loan_id=loanId;

END //

DELIMITER ;





DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN

DECLARE numberOfIds INTEGER;

/* SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed; */

 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdUsed;

IF @theTenure='1.0 MONTHS' THEN

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SET @interest=((@InterestRate*@princinpalRemaining)/1200);

interestPayment_Loop: LOOP


SELECT TrnId,InterestInvoRemaining, InterestPaidIn,totalInterstInvRemaing,totalInterstInvPaid,totalInterstInv INTO @theId,@interestRemaining,@interestPaidnow,@totalInteInvoRemain,@totalInterestInvPaid,@totalInterestInveo FROM interestcomputed WHERE loanId=loanIdUsed AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;


SET InterestPaid=InterestPaid-@interest;

CALL newDateConverted(@originalDueDate);


IF InterestPaid<@interest THEN

SELECT InterestPaid,@interest;

IF InterestPaid<@interestRemaining THEN

SET @inteDiff=@interestRemaining-InterestPaid;

SET @interestPaidnow=@interestPaidnow+InterestPaid;

SET @totalP=@totalInterestInveo-InterestPaid;

UPDATE interestComputed SET InterestInvoRemaining=@inteDiff,InterestPaidIn=@interestPaidnow,totalInterstInvRemaing=@interest,totalInterstInvPaid=@totalP WHERE TrnId=@theId;

END IF;


IF InterestPaid>=@interestRemaining THEN

UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@interest,totalInterstInvRemaing=0.0,totalInterstInvPaid=@totalInterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;


END IF;


END IF;



IF InterestPaid>=@interest THEN

UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@interest,totalInterstInvRemaing=0.0,totalInterstInvPaid=@totalInterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;



END IF;







IF InterestPaid<@interest THEN


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

