DROP TABLE IF EXISTS loan_products;

CREATE TABLE loan_products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255),
    product_notes TEXT,
    product_type  ENUM('LOAN_PRODUCT', 'DEPOSIT_PRODUCT') NOT NULL
);




DROP TABLE IF EXISTS penalty_config;

CREATE TABLE penalty_config (
    config_id INT AUTO_INCREMENT PRIMARY KEY,
    loan_product_id INT,  -- Link to specific loan products
    penalty_calculation_method ENUM('OVERDUE_PRINCIPAL_INSTALLMENT', 'OUTSTANDING_PRINCIPAL_AMOUNT', 'OVERDUE_PRINCIPAL_INTEREST_INSTALLMENT', 'OVERDUE_OUTSTANDING_PRINCIPAL_INTEREST_AMOUNT', 'OVERDUE_INSTALLMENT', 'OUTSTANDING_BALANCE') NOT NULL,
    penalty_type ENUM('RECURRENT', 'ONE_OFF') NOT NULL,
    is_fixed_amount BOOLEAN NOT NULL DEFAULT FALSE, -- Indicates if the penalty is a fixed amount
    fixed_penalty_amount DECIMAL(10, 2), -- Specifies the fixed amount if is_fixed_amount is TRUE
    default_penalty_rate DECIMAL(10, 2), -- Used if is_fixed_amount is FALSE
    min_penalty_rate DECIMAL(10, 2), -- Minimum penalty rate
    max_penalty_rate DECIMAL(10, 2), -- Maximum penalty rate
    penalty_rate_frequency ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    arrears_tolerance_period INT, -- In days
    penalty_tolerance_period INT, -- In days
    penalty_cap DECIMAL(10, 2), -- Maximum penalty amount
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    FOREIGN KEY (loan_product_id) REFERENCES loan_products(product_id)
);















DROP PROCEDURE IF EXISTS calculate_loan_penalty;
DELIMITER //
CREATE PROCEDURE calculate_loan_penalty() READS SQL DATA 

OUTER_BLOCK: BEGIN
    DECLARE theLoanTxnId VARCHAR(20); 
    DECLARE current_date_now DATE;
    DECLARE last_penalty_date DATE;
    DECLARE next_penalty_date DATE;
    DECLARE penaltyRate DECIMAL(10, 2);
    DECLARE minPenaltyRate DECIMAL(10, 2);
    DECLARE maxPenaltyRate DECIMAL(10, 2);
    DECLARE penaltyType VARCHAR(50);
    DECLARE penaltyCalcMethod VARCHAR(50);
    DECLARE penaltyFrequency VARCHAR(50);
    DECLARE arrearsTolerancePeriod INT;
    DECLARE penaltyTolerancePeriod INT;
    DECLARE overallPenaltyCap DECIMAL(10, 2);
    DECLARE currentInstalmentAmount DECIMAL(10, 2);
    DECLARE currentPrincipalAmount DECIMAL(10, 2);
    DECLARE currentInterestAmount DECIMAL(10, 2);
    DECLARE penaltyApplied BOOLEAN;
    DECLARE daysLate INT;
    DECLARE penaltyAmount DECIMAL(10, 2) DEFAULT 0.0;
    DECLARE totalPenalty DECIMAL(10, 2) DEFAULT 0.0;
      DECLARE done INT DEFAULT FALSE;
DECLARE outerNotFound, c INTEGER DEFAULT 0; 
  DECLARE cur CURSOR FOR 
        SELECT  DISTINCT
            master1_id 
        FROM 
            new_loan_appstoreamort 
        WHERE 
            instalment_status = 'NY'
            AND (penalty_computation_date IS NULL OR penalty_computation_date < CURRENT_DATE());
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outerNotFound=1;

    SET current_date_now = CURDATE();


OPEN forLoanTxnId; 

LOANTXN_LOOP: LOOP 

FETCH forLoanTxnId into theLoanTxnId;

 IF outerNotFound=1 THEN
LEAVE LOANTXN_LOOP;
 END IF;
 

IF theLoanTxnId=10001 THEN

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
END IF;

SET outerNotFound=0;
 END LOOP LOANTXN_LOOP;
CLOSE forLoanTxnId;
END OUTER_BLOCK//

DELIMITER ;













DROP PROCEDURE IF EXISTS calculate_loan_penalty;
DELIMITER //

CREATE PROCEDURE calculate_loan_penalty(IN loanID VARCHAR(100))

OUTER_BLOCK: BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE current_date_now DATE;
    DECLARE last_penalty_date DATE;
    DECLARE next_penalty_date DATE;
    DECLARE penaltyRate DECIMAL(10, 2);
    DECLARE minPenaltyRate DECIMAL(10, 2);
    DECLARE maxPenaltyRate DECIMAL(10, 2);
    DECLARE penaltyType VARCHAR(50);
    DECLARE penaltyCalcMethod VARCHAR(50);
    DECLARE penaltyFrequency VARCHAR(50);
    DECLARE arrearsTolerancePeriod INT;
    DECLARE penaltyTolerancePeriod INT;
    DECLARE overallPenaltyCap DECIMAL(10, 2);
    DECLARE currentInstalmentAmount DECIMAL(10, 2);
    DECLARE currentPrincipalAmount DECIMAL(10, 2);
    DECLARE currentInterestAmount DECIMAL(10, 2);
    DECLARE penaltyApplied BOOLEAN;
    DECLARE daysLate INT;
    DECLARE penaltyAmount DECIMAL(10, 2) DEFAULT 0.0;
    DECLARE totalPenalty DECIMAL(10, 2) DEFAULT 0.0;
    DECLARE outerNotFound, c INTEGER DEFAULT 0; 
    DECLARE cur CURSOR FOR 
        SELECT  DISTINCT
            master1_id 
        FROM 
            new_loan_appstoreamort 
        WHERE 
            instalment_status = 'NY'
            AND instalment_due_date <= c
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    SET current_date_now = CURDATE();

    -- Retrieve penalty configuration for the loan
    SELECT default_penalty_rate, min_penalty_rate, max_penalty_rate, penalty_type, penalty_calculation_method, penalty_rate_frequency, arrears_tolerance_period, penalty_tolerance_period, penalty_cap INTO penaltyRate, minPenaltyRate, maxPenaltyRate, penaltyType, penaltyCalcMethod, penaltyFrequency, arrearsTolerancePeriod, penaltyTolerancePeriod, overallPenaltyCap FROM penalty_config WHERE loan_product_id = (SELECT loan_product_id FROM new_loan_appstore WHERE loan_id = loanID);

    OPEN cur;

    TXNIDS_LOOP: LOOP
        FETCH cur INTO currentInstalmentAmount, currentPrincipalAmount, currentInterestAmount, daysLate;
        IF done THEN
            LEAVE TXNIDS_LOOP;
        END IF;

        -- Check if the arrears are within tolerance period
        IF daysLate > arrearsTolerancePeriod THEN

            -- Calculate the next penalty date based on the frequency
            CASE penaltyFrequency
                WHEN 'DAILY' THEN
                    SET next_penalty_date = DATE_ADD(last_penalty_date, INTERVAL 1 DAY);
                WHEN 'WEEKLY' THEN
                    SET next_penalty_date = DATE_ADD(last_penalty_date, INTERVAL 1 WEEK);
                WHEN 'MONTHLY' THEN
                    SET next_penalty_date = DATE_ADD(last_penalty_date, INTERVAL 1 MONTH);
            END CASE;

            -- Check if the current date is on or after the next penalty date
            IF current_date >= next_penalty_date THEN

                -- Calculate the penalty based on the days late and tolerance period
                IF daysLate > penaltyTolerancePeriod THEN

                    -- Penalty calculation based on the type and method
                    IF penaltyType = 'RECURRENT' THEN
                        CASE penaltyCalcMethod
                            WHEN 'OVERDUE_PRINCIPAL_INSTALLMENT' THEN
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

 
TXNIDSx_LOOP:LOOP

FETCH forBatchNos INTO theBatchNoS;
SET thePaid=NULL,InterestPaid=NULL,principalPaid=NULL;


 IF innerNotFound=1 THEN
LEAVE TXNIDSx_LOOP;
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
-- Recurrent penalty logic for overdue principal installment
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

SET innerNotFound=0;
END LOOP TXNIDSx_LOOP; 
END IF;
CLOSE forBatchNos; 
END INNER_BLOCK;
                                
                            WHEN 'OUTSTANDING_PRINCIPAL_AMOUNT' THEN
                             OUTSTANDING_PRINCIPAL_AMOUNT_BLOCK: BEGIN
                            DECLARE theBatchNoS VARCHAR(60);
DECLARE theOpeningBal,theBal,thePaid,InterestPaid,principalPaid,AccumInterestPaid,PenaltyPaid,priBal,intBal,accumIntBal,loanPenBal,loanBal DOUBLE; 
DECLARE innerNotFound INTEGER DEFAULT 0; 
DECLARE forBatchNos1 CURSOR FOR SELECT BatchCode FROM loandisburserepaystatement WHERE loanTrnId=theLoanTxnId;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound=1;




OPEN forBatchNos1; 

SELECT COUNT(ExpectedTotalAmount) INTO @No FROM loandisburserepaystatement WHERE ExpectedTotalAmount>0 AND loanTrnId=theLoanTxnId ;

IF @No=1 THEN

SELECT ExpectedTotalAmount INTO theOpeningBal FROM loandisburserepaystatement WHERE ExpectedTotalAmount>0 AND loanTrnId=theLoanTxnId ;

 
TXNIDSx1_LOOP:LOOP

FETCH forBatchNos1 INTO theBatchNoS;
SET thePaid=NULL,InterestPaid=NULL,principalPaid=NULL;


 IF innerNotFound=1 THEN
LEAVE TXNIDSx1_LOOP;
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
  -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

SET innerNotFound=0;
END LOOP TXNIDSx1_LOOP; 
END IF;
CLOSE forBatchNos1; 
END OUTSTANDING_PRINCIPAL_AMOUNT_BLOCK;
                              

                            WHEN 'OVERDUE_PRINCIPAL_INTEREST_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                    WHEN 'OVERDUE_PRINCIPAL_INTEREST_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                    WHEN 'OVERDUE_OUTSTANDING_PRINCIPAL_INTEREST_AMOUNT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;
                                    WHEN 'OVERDUE_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                   WHEN 'OUTSTANDING_BALANCE' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                
                        END CASE;
                    ELSEIF penaltyType = 'ONE_OFF' THEN
                        -- Check if one-off penalty has already been applied
                        -- SELECT logic to determine if penaltyApplied
                        -- IF NOT penaltyApplied THEN
                            -- One-off penalty logic based on the method
                            CASE penaltyCalcMethod
                            WHEN 'OVERDUE_PRINCIPAL_INSTALLMENT' THEN
                                -- Recurrent penalty logic for overdue principal installment
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;
                            WHEN 'OUTSTANDING_PRINCIPAL_AMOUNT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                            WHEN 'OVERDUE_PRINCIPAL_INTEREST_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                    WHEN 'OVERDUE_PRINCIPAL_INTEREST_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                    WHEN 'OVERDUE_OUTSTANDING_PRINCIPAL_INTEREST_AMOUNT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;
                                    WHEN 'OVERDUE_INSTALLMENT' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                   WHEN 'OUTSTANDING_BALANCE' THEN
                                -- Recurrent penalty logic for outstanding principal amount
                                SET penaltyAmount = currentPrincipalAmount * penaltyRate / 100;

                                
                        END CASE;
                            -- Update tracking mechanism for one-off penalty
                        -- END IF;
                    END IF;

                    -- Apply min and max rate constraints
                    IF penaltyAmount < minPenaltyRate THEN
                        SET penaltyAmount = minPenaltyRate;
                    ELSEIF penaltyAmount > maxPenaltyRate THEN
                        SET penaltyAmount = maxPenaltyRate;
                    END IF;

                    -- Aggregate total penalty
                    SET totalPenalty = totalPenalty + penaltyAmount;

                    -- Update last penalty date to current date
                    SET last_penalty_date = current_date;

                    -- Apply overall cap
                    IF totalPenalty > overallPenaltyCap THEN
                        SET totalPenalty = overallPenaltyCap;
                        LEAVE TXNIDS_LOOP;
                    END IF;
                END IF;
            END IF;
        END IF;
   


    -- Update the loan record with the total penalty
    UPDATE new_loan_appstore SET TotalLoanPenaltyRemaining = TotalLoanPenaltyRemaining + totalPenalty WHERE loan_id = loanID;


SET outerNotFound=0;
 END LOOP TXNIDS_LOOP;
CLOSE cur;
END OUTER_BLOCK//

DELIMITER ;
