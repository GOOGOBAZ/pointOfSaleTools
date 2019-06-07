
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
 


 SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;

 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ;
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


IF @theTenure='1.0 MONTHS' THEN

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdZ;

SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;


SET @counter=0;

SET @totalInterest=0.0;

Date_loop: LOOP 



IF lastDate>current_date() THEN
CALL updateAccountsAfter(loanIdZ,lastDate,@totalInterest);
LEAVE Date_loop;
END IF;


IF @counter<=1 THEN

SELECT interestinvoRemaining INTO @ExistingInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;

SET interestInveolved=@ExistingInterest;

SET @totalInterest=@totalInterest+@ExistingInterest;

ELSEIF  @counter>1 THEN

SET interestInveolved=((InterestRate*princinpalRemaining)/1200);

SET @totalInterest=@totalInterest+interestInveolved;

END IF;


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