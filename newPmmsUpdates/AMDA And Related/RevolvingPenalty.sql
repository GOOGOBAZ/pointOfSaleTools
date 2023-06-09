
DROP PROCEDURE IF EXISTS getPenaltyStatus;

DELIMITER //

CREATE PROCEDURE getPenaltyStatus() READS SQL DATA BEGIN

SELECT  penaltyStatus FROM loanarrearssettings;

END //

DELIMITER ;


update new_loan_appstore SET balance_due=(TotalPrincipalRemaining+TotalInterestRemaining),total_loanAmount=(princimpal_amount+total_interest),TotalLoanPenaltyRemaining=0;

update new_loan_appstore1 SET balance_due=(TotalPrincipalRemaining+TotalInterestRemaining),total_loanAmount=(princimpal_amount+total_interest),TotalLoanPenaltyRemaining=0;


update new_loan_appstoreamort SET  instalment_amount=( princimpal_amount+interest_amount),
 InstalmentRemaining=(PrincipalRemaining+InterestRemaing),
  LoanPenalty=0.0,
LoanPenaltyRemaining= 0.0;

UPDATE pmms.loandisburserepaystatement  SET LoanPenaltyBalance=0.0,ExpectedTotalAmount=(AmountDisbursed+ExpectedInterest),LoanBalance=(PrincipalBalance+InterestBalance);

-- Credits	NAMATOVU JOWERIA 0752576006	05502039910	Customer Deposits	135000.0

CREATE TABLE sp_log (
  id INT AUTO_INCREMENT PRIMARY KEY,
  log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  trn_id INT,
  amort_trn_id INT,
  log_message VARCHAR(255)
);


CREATE TABLE penalty_percentage_table (
  percentage INT
);


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


CREATE TABLE penalty_audit (
  audit_id INT AUTO_INCREMENT PRIMARY KEY,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  action VARCHAR(20),
  instalment_id INT,
  loan_id INT,
  prev_due_date DATE,
  new_due_date DATE,
  prev_balance_due DECIMAL(12,4),
  new_balance_due DECIMAL(12,4),
  prev_total_loan_amount DECIMAL(12,4),
  new_total_loan_amount DECIMAL(12,4),
  prev_instalment_amount DECIMAL(12,4),
  new_instalment_amount DECIMAL(12,4),
  prev_penalty_amount DECIMAL(12,4),
  new_penalty_amount DECIMAL(12,4),
  prev_instalment_remaining DECIMAL(12,4),
  new_instalment_remaining DECIMAL(12,4),
  prev_loan_penalty_remaining DECIMAL(12,4),
  new_loan_penalty_remaining DECIMAL(12,4)
);



CREATE TABLE sp_log (
  id INT AUTO_INCREMENT PRIMARY KEY,
  log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  trn_id INT,
  amort_trn_id INT,
  log_message VARCHAR(255)
);
DELETE FROM sp_log;

CREATE TABLE penalty_percentage_table (
  percentage INT
);

DROP FUNCTION IF EXISTS get_updated_due_date;

DELIMITER ##

CREATE FUNCTION get_updated_due_date(instalment_due_date DATE, loan_tenure VARCHAR(20))
RETURNS DATE
BEGIN
  DECLARE updated_due_date DATE;

  IF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? DAYS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 1 DAY);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? WEEKS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 1 WEEK);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? FORTNIGHTS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 2 WEEK);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? MONTHS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 1 MONTH);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? QUARTERS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 3 MONTH);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? HALF YEARS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 6 MONTH);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? YEARS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 1 YEAR);
  ELSEIF loan_tenure REGEXP '^[0-9]+(\\.[0-9]+)? BIENNIALS$' THEN
    SET updated_due_date = DATE_ADD(instalment_due_date, INTERVAL 2 YEAR);
  END IF;

  RETURN updated_due_date;
END ##
DELIMITER ;


DROP FUNCTION IF EXISTS get_penalty_percentage;

DELIMITER ##
CREATE FUNCTION get_penalty_percentage()
RETURNS DECIMAL(5,2)
BEGIN
  DECLARE penalty_percentage DECIMAL(5,2);
  SELECT percentage INTO penalty_percentage FROM penalty_percentage_table LIMIT 1; -- Replace with your actual table and column names
  RETURN penalty_percentage;
END ##
DELIMITER ;



-- Delete existing penalty details
DELETE FROM amdaPenaltyDetails;

-- Drop the existing stored procedure
DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;

-- Change the delimiter to create the new stored procedure
DELIMITER $$

-- Create the new stored procedure
CREATE PROCEDURE theAMDAPenaltyComputation()
BEGIN
  -- Variables
  DECLARE done INT DEFAULT 0;
  DECLARE cur_trn_id INT;
  DECLARE cur_due_date DATE;
  DECLARE cur_amort_trn_id INT;
  DECLARE cur_instalment_remaining DECIMAL(12,4);
  DECLARE penalty_amount DECIMAL(12,4);
  DECLARE cur_loan_tenure VARCHAR(20);
  DECLARE penalty_percentage DECIMAL(5,2);
  DECLARE cur_new_due_date DATE;
  
  -- Variables to store previous values
  DECLARE prev_balance_due DECIMAL(12,4);
  DECLARE prev_total_loan_amount DECIMAL(12,4);
  DECLARE prev_instalment_amount DECIMAL(12,4);
  DECLARE prev_TotalLoanPenaltyRemaining DECIMAL(12,4);
  DECLARE prev_amort_InstalmentRemaining DECIMAL(12,4);
  DECLARE prev_amort_LoanPenaltyRemaining DECIMAL(12,4);
  DECLARE prev_amort_instalment_amount DECIMAL(12,4);
  DECLARE prev_amort_LoanPenalty DECIMAL(12,4);

  -- Cursors
  DECLARE read_cursor CURSOR FOR
    SELECT new_loan_appstore.trn_id, new_loan_appstore1.loan_tenure
    FROM new_loan_appstore INNER JOIN new_loan_appstore1 ON new_loan_appstore.trn_id=new_loan_appstore1.trn_id
    WHERE (new_loan_appstore.loan_cycle_status='Disbursed' OR new_loan_appstore.loan_cycle_status='Renewed')
      AND NOT (new_loan_appstore1.loan_tenure='1 MONTHS' OR new_loan_appstore1.loan_tenure='1.0 MONTHS');

  DECLARE instalment_cursor CURSOR FOR
    SELECT trn_id, instalment_due_date, InstalmentRemaining
    FROM new_loan_appstoreamort
    WHERE master1_id = cur_trn_id AND NOT instalment_status='P';

  -- Handlers
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
  
  INSERT INTO sp_log (log_message) VALUES ('Starting theAMDAPenaltyComputation');

  OPEN read_cursor;
  read_loop: LOOP
    FETCH read_cursor INTO cur_trn_id, cur_loan_tenure;
    IF done THEN
      LEAVE read_loop;
    END IF;

    SET done = 0;
    OPEN instalment_cursor;
    instalment_loop: LOOP
      FETCH instalment_cursor INTO cur_amort_trn_id, cur_due_date, cur_instalment_remaining;
      IF done THEN
        CLOSE instalment_cursor;
        SET done = 0;
        LEAVE instalment_loop;
      END IF;

      SET penalty_percentage = get_penalty_percentage();

      IF EXISTS (SELECT 1 FROM amdaPenaltyDetails WHERE instalment_id = cur_amort_trn_id) THEN
        SELECT new_due_date INTO cur_new_due_date FROM amdaPenaltyDetails WHERE instalment_id = cur_amort_trn_id;

        IF DATE_ADD(cur_new_due_date, INTERVAL 3 DAY)  < CURDATE() THEN
        
          SET penalty_amount = cur_instalment_remaining * (penalty_percentage / 100);

          -- Store previous values
      -- Store previous values before updating
      SELECT balance_due, total_loanAmount, instalment_amount, TotalLoanPenaltyRemaining
      INTO prev_balance_due, prev_total_loan_amount, prev_instalment_amount, prev_TotalLoanPenaltyRemaining
      FROM new_loan_appstore WHERE trn_id = cur_trn_id;

      -- Update loan details
      UPDATE new_loan_appstore
      SET balance_due = balance_due + penalty_amount,
          total_loanAmount = total_loanAmount + penalty_amount,
          instalment_amount = instalment_amount + penalty_amount,
          TotalLoanPenaltyRemaining = TotalLoanPenaltyRemaining + penalty_amount
      WHERE trn_id = cur_trn_id;

      -- Insert audit trail for loan details update
      INSERT INTO penalty_audit (
        action, instalment_id, loan_id, prev_balance_due, new_balance_due,
        prev_total_loan_amount, new_total_loan_amount, prev_instalment_amount, new_instalment_amount,
        prev_penalty_amount, new_penalty_amount, prev_instalment_remaining, new_instalment_remaining,
        prev_loan_penalty_remaining, new_loan_penalty_remaining
      )
      VALUES (
        'UPDATE', cur_amort_trn_id, cur_trn_id, prev_balance_due, prev_balance_due + penalty_amount,
        prev_total_loan_amount, prev_total_loan_amount + penalty_amount, prev_instalment_amount, prev_instalment_amount + penalty_amount,
        0, penalty_amount, cur_instalment_remaining, cur_instalment_remaining + penalty_amount,
        prev_TotalLoanPenaltyRemaining, prev_TotalLoanPenaltyRemaining + penalty_amount
      );

      -- Store previous values before updating
      SELECT InstalmentRemaining, LoanPenaltyRemaining, instalment_amount, LoanPenalty
      INTO prev_amort_InstalmentRemaining, prev_amort_LoanPenaltyRemaining, prev_amort_instalment_amount, prev_amort_LoanPenalty
      FROM new_loan_appstoreamort WHERE trn_id = cur_amort_trn_id;

      -- Update amortization details
      UPDATE new_loan_appstoreamort
      SET InstalmentRemaining = InstalmentRemaining + penalty_amount,
          LoanPenaltyRemaining = LoanPenaltyRemaining + penalty_amount,
          instalment_amount = instalment_amount + penalty_amount,
          LoanPenalty = LoanPenalty + penalty_amount
      WHERE trn_id = cur_amort_trn_id;

      -- Insert audit trail for amortization details update
      INSERT INTO penalty_audit (
        action, instalment_id, loan_id, prev_instalment_remaining, new_instalment_remaining,
        prev_loan_penalty_remaining, new_loan_penalty_remaining, prev_instalment_amount, new_instalment_amount,
        prev_penalty_amount, new_penalty_amount
      )
      VALUES (
        'UPDATE', cur_amort_trn_id, cur_trn_id, prev_amort_InstalmentRemaining, prev_amort_InstalmentRemaining + penalty_amount,
        prev_amort_LoanPenaltyRemaining, prev_amort_LoanPenaltyRemaining + penalty_amount, prev_amort_instalment_amount, prev_amort_instalment_amount + penalty_amount,
        prev_amort_LoanPenalty, prev_amort_LoanPenalty + penalty_amount
      );

      UPDATE amdaPenaltyDetails
      SET new_due_date = get_updated_due_date
      (new_due_date, cur_loan_tenure)
      WHERE instalment_id = cur_amort_trn_id;

    END IF;
  ELSE
  IF DATE_ADD(cur_due_date, INTERVAL 3 DAY)  < CURDATE() THEN

      SET penalty_amount = cur_instalment_remaining * (penalty_percentage / 100);

      -- Store previous values before updating
      SELECT balance_due, total_loanAmount, instalment_amount, TotalLoanPenaltyRemaining
      INTO prev_balance_due, prev_total_loan_amount, prev_instalment_amount, prev_TotalLoanPenaltyRemaining
      FROM new_loan_appstore WHERE trn_id = cur_trn_id;

      -- Update loan details
      UPDATE new_loan_appstore
      SET balance_due = balance_due + penalty_amount,
          total_loanAmount = total_loanAmount + penalty_amount,
          instalment_amount = instalment_amount + penalty_amount,
          TotalLoanPenaltyRemaining = TotalLoanPenaltyRemaining + penalty_amount
      WHERE trn_id = cur_trn_id;

      -- Insert audit trail for loan details update
      INSERT INTO penalty_audit (
        action, instalment_id, loan_id, prev_balance_due, new_balance_due,
        prev_total_loan_amount, new_total_loan_amount, prev_instalment_amount, new_instalment_amount,
        prev_penalty_amount, new_penalty_amount, prev_instalment_remaining, new_instalment_remaining,
        prev_loan_penalty_remaining, new_loan_penalty_remaining
      )
      VALUES (
        'INSERT', cur_amort_trn_id, cur_trn_id, prev_balance_due, prev_balance_due + penalty_amount,
        prev_total_loan_amount, prev_total_loan_amount + penalty_amount, prev_instalment_amount, prev_instalment_amount + penalty_amount,
        0, penalty_amount, cur_instalment_remaining, cur_instalment_remaining + penalty_amount,
        prev_TotalLoanPenaltyRemaining, prev_TotalLoanPenaltyRemaining + penalty_amount
      );

      -- Store previous values before updating
      SELECT InstalmentRemaining, LoanPenaltyRemaining, instalment_amount, LoanPenalty
      INTO prev_amort_InstalmentRemaining, prev_amort_LoanPenaltyRemaining, prev_amort_instalment_amount, prev_amort_LoanPenalty
      FROM new_loan_appstoreamort WHERE trn_id = cur_amort_trn_id;

      -- Update amortization details
      UPDATE new_loan_appstoreamort
      SET InstalmentRemaining = InstalmentRemaining + penalty_amount,
          LoanPenaltyRemaining = LoanPenaltyRemaining + penalty_amount,
          instalment_amount = instalment_amount + penalty_amount,
          LoanPenalty = LoanPenalty + penalty_amount
      WHERE trn_id = cur_amort_trn_id;

      -- Insert audit trail for amortization details update
      INSERT INTO penalty_audit (
        action, instalment_id, loan_id, prev_instalment_remaining, new_instalment_remaining,
        prev_loan_penalty_remaining, new_loan_penalty_remaining, prev_instalment_amount, new_instalment_amount,
        prev_penalty_amount, new_penalty_amount
      )
      VALUES (
        'INSERT', cur_amort_trn_id, cur_trn_id, prev_amort_InstalmentRemaining, prev_amort_InstalmentRemaining + penalty_amount,
        prev_amort_LoanPenaltyRemaining, prev_amort_LoanPenaltyRemaining + penalty_amount, prev_amort_instalment_amount, prev_amort_instalment_amount + penalty_amount,
        prev_amort_LoanPenalty, prev_amort_LoanPenalty + penalty_amount
      );

      INSERT INTO amdaPenaltyDetails (instalment_id, loan_id, new_due_date)
      VALUES (cur_amort_trn_id, cur_trn_id, get_updated_due_date(cur_due_date, cur_loan_tenure));
    END IF;
  END IF;
END LOOP instalment_loop;

END LOOP read_loop;

INSERT INTO sp_log (log_message) VALUES ('Finished theAMDAPenaltyComputation');

CLOSE read_cursor;

END $$

-- Reset the delimiter
DELIMITER ;

