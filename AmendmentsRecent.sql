







DROP PROCEDURE IF EXISTS InterestManagementForLenders;

DELIMITER //

CREATE PROCEDURE InterestManagementForLenders() READS SQL DATA BEGIN

 DECLARE loanId VARCHAR(30);

  DECLARE numberOfIds INTEGER;

 DECLARE l_done INTEGER DEFAULT 0;

 DECLARE lastDate,originalDueDate DATE;

  DECLARE InterestRate,princinpalRemaining,totalComputedInterest Double;

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 OPEN forSelectingLoanIds;


accounts_loop: LOOP 

 FETCH forSelectingLoanIds into loanId;

 SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanId;


 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


IF numberOfIds=1 THEN

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanId;

SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanId;


SET @counter=0;

Date_loop: LOOP 

IF lastDate>current_date() THEN


SET totalComputedInterest=((@counter*InterestRate*princinpalRemaining)/1200);

SELECT lastDate,InterestRate,originalDueDate,princinpalRemaining,loanId;

SELECT @counter;

SELECT ((InterestRate*princinpalRemaining)/1200);

SELECT totalComputedInterest,lastDate,loanId;
CALL updateAccountsAfter(loanId,lastDate,totalComputedInterest);

LEAVE Date_loop;

END IF;
SET @counter=@counter+1;
SET @pureDate=lastDate;
SET totalComputedInterest=((@counter*InterestRate*princinpalRemaining)/1200);

INSERT INTO interestComputed VALUES(null,loanId,@pureDate,princinpalRemaining,totalComputedInterest,'Pending'); 


CALL newDateConverted(@pureDate);
SET lastDate= @pureDate;
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
SELECT oldNewDate;
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


SELECT oldNewDate;
END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updateAccountsAfter;

DELIMITER //

CREATE PROCEDURE updateAccountsAfter(IN loanId VARCHAR(30),IN newDateDue DATE,IN interestComputed Double) READS SQL DATA BEGIN


SET @sql_text1 = concat(CAST("SELECT interest_amount, InstalmentRemaining,InterestRemaing, instalment_amount INTO @interestAmount, @instalmentRemaining,@interestRemaining,@InstalmentAmount FROM new_loan_appstoreamort WHERE master2_id='" AS CHAR CHARACTER SET utf8),loanId, CAST("'" AS CHAR CHARACTER SET utf8));

SELECT @sql_text1;
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;


SELECT  total_interest, balance_due,TotalInterestRemaining INTO @totalInterestAmount,@totalInstalmentsRemaining, @totalInterestRemaining FROM new_loan_appstore WHERE loan_id=loanId;

SELECT @instalmentRemaining,@interestRemaining,@interestAmount,@InstalmentAmount,loanId;

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
SET @instalmentRemaining=@instalmentRemaining+interestComputed,@interestRemaining=@interestRemaining+interestComputed,@interestAmount=@interestAmount+interestComputed,@InstalmentAmount=@InstalmentAmount+interestComputed;

SET @totalInstalmentsRemaining=@totalInstalmentsRemaining+interestComputed,@totalInterestRemaining=@totalInterestRemaining+interestComputed,@totalInterestAmount=@totalInterestAmount+interestComputed;


SELECT @instalmentRemaining,@interestRemaining,@totalInstalmentsRemaining,@totalInterestRemaining,@interestAmount,@totalInterestAmount,loanId;
UPDATE new_loan_appstoreamort SET InstalmentRemaining=@instalmentRemaining,InterestRemaing=@interestRemaining,interest_amount=@interestAmount,instalment_amount=@InstalmentAmount WHERE master2_id=loanId;
UPDATE new_loan_appstore SET balance_due=@totalInstalmentsRemaining,TotalInterestRemaining=@totalInterestRemaining,instalment_next_due_date=newDateDue,total_interest=@totalInterestAmount WHERE loan_id=loanId;
UPDATE new_loan_appstore1 SET balance_due=@totalInstalmentsRemaining,TotalInterestRemaining=@totalInterestRemaining,instalment_next_due_date=newDateDue,total_interest=@totalInterestAmount WHERE loan_id=loanId;

END //

DELIMITER ;





DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN

DECLARE numberOfIds INTEGER;

SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;

IF numberOfIds=1 THEN

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SET @interest=((@InterestRate*@princinpalRemaining)/1200);

interestPayment_Loop: LOOP

IF InterestPaid<@interest THEN


UPDATE new_loan_appstoreamort SET instalment_due_date=@originalDueDate WHERE master2_id=loanIdUsed;

LEAVE interestPayment_Loop;

END IF;

SET InterestPaid=InterestPaid-@interest;

CALL newDateConverted(@originalDueDate);

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

