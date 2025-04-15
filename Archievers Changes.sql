

-- pmms

-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS callAllBalanceProcedures;

-- Create a new procedure
DELIMITER $$
CREATE PROCEDURE callAllBalanceProcedures(IN trnId INT)
BEGIN
    -- Calling the first procedure
    CALL normaliseIndividualBalanceBank(trnId);

    -- Calling the second procedure
    CALL normaliseIndividualBalanceCash(trnId);

    -- Calling the third procedure
UPDATE loandisburserepaystatement SET LoanBalance=PrincipalBalance+InterestBalance where loanTrnId=trnId;
END$$

-- Reset the delimiter to its default value
DELIMITER ;


-- CALL callAllBalanceProcedures(71707);





-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS normaliseIndividualBalanceBank;

-- Change the delimiter to avoid conflicts with semicolon in the procedure
DELIMITER $$

-- Create a new procedure
CREATE PROCEDURE normaliseIndividualBalanceBank(IN trnIdInd INT)
READS SQL DATA
BEGIN
    -- Declaration of variables
    DECLARE theBatchNoS VARCHAR(60);
    DECLARE theOpeningBal, theBal, thePaid, InterestPaid, principalPaid, 
            AccumInterestPaid, PenaltyPaid, priBal, intBal, accumIntBal, 
            loanPenBal, loanBal DOUBLE; 
    DECLARE innerNotFound INTEGER DEFAULT 0; 

    -- Cursor for selecting BatchCode from a table based on the input transaction ID
    DECLARE forBatchNos CURSOR FOR 
        SELECT BatchCode 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = trnIdInd ;

    -- Continue handler for cursor not finding a row
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound = 1;

    -- Open the cursor
    OPEN forBatchNos; 

    -- Initialize local variables
    SELECT COUNT(ExpectedTotalAmount) INTO @No 
    FROM loandisburserepaystatement 
    WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

    -- Check if there is exactly one row with ExpectedTotalAmount > 0
    IF @No = 1 THEN
        SELECT ExpectedTotalAmount INTO theOpeningBal 
        FROM loandisburserepaystatement 
        WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

        -- Loop for processing batch numbers
        TXNIDS_LOOP: LOOP
            FETCH forBatchNos INTO theBatchNoS;
            SET thePaid = NULL, InterestPaid = NULL, principalPaid = NULL;
SELECT  theBatchNoS;
            -- Exit the loop if no more rows found
            IF innerNotFound = 1 THEN
                LEAVE TXNIDS_LOOP;
            END IF;

            -- Fetch payments, interest and principal from different accounts
            SELECT debit INTO thePaid 
            FROM bsanca01122000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO InterestPaid 
            FROM bsanca03301000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO principalPaid 
            FROM bsanca01128000110 
            WHERE chq_number = theBatchNoS;

            -- Check and set default values for null or unset variables
            IF thePaid = '-' OR ISNULL(thePaid) THEN
                SET thePaid = 0.0;
            END IF;

            IF InterestPaid = '-' OR ISNULL(InterestPaid) THEN
                SET InterestPaid = 0.0;
            END IF;

            IF principalPaid = '-' OR ISNULL(principalPaid) THEN
                SET principalPaid = 0.0;
            END IF;


                   IF thePaid>0 THEN 
            -- Update the statement with calculated values
            UPDATE loandisburserepaystatement 
            SET AmountPaid = thePaid, PrincipalPaid = principalPaid, 
                InterestPaid = InterestPaid, LoanBalance = theOpeningBal - thePaid 
            WHERE BatchCode = theBatchNoS;

            -- Adjust the opening balance for the next iteration
            SET theOpeningBal = theOpeningBal - thePaid;
END IF;

            -- Reset the not found handler flag
            SET innerNotFound = 0;
        END LOOP TXNIDS_LOOP; 
    END IF;

    -- Close the cursor
    CLOSE forBatchNos; 

END $$

-- Reset the delimiter to its default value
DELIMITER ;




-- Drop the existing procedure if it already exists
DROP PROCEDURE IF EXISTS normaliseIndividualBalanceCash;

-- Change the delimiter to avoid conflicts with semicolon in the procedure
DELIMITER $$

-- Create a new procedure
CREATE PROCEDURE normaliseIndividualBalanceCash(IN trnIdInd INT)
READS SQL DATA
BEGIN
    -- Declaration of variables
    DECLARE theBatchNoS VARCHAR(60);
    DECLARE theOpeningBal, theBal, thePaid, InterestPaid, principalPaid, 
            AccumInterestPaid, PenaltyPaid, priBal, intBal, accumIntBal, 
            loanPenBal, loanBal DOUBLE; 
    DECLARE innerNotFound INTEGER DEFAULT 0; 

    -- Cursor for selecting BatchCode from a table based on the input transaction ID
    DECLARE forBatchNos CURSOR FOR 
        SELECT BatchCode 
        FROM loandisburserepaystatement 
        WHERE loanTrnId = trnIdInd ;

    -- Continue handler for cursor not finding a row
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET innerNotFound = 1;

    -- Open the cursor
    OPEN forBatchNos; 

    -- Initialize local variables
    SELECT COUNT(ExpectedTotalAmount) INTO @No 
    FROM loandisburserepaystatement 
    WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

    -- Check if there is exactly one row with ExpectedTotalAmount > 0
    IF @No = 1 THEN
        SELECT ExpectedTotalAmount INTO theOpeningBal 
        FROM loandisburserepaystatement 
        WHERE ExpectedTotalAmount > 0 AND loanTrnId = trnIdInd;

        -- Loop for processing batch numbers
        TXNIDS_LOOP: LOOP
            FETCH forBatchNos INTO theBatchNoS;
            SET thePaid = NULL, InterestPaid = NULL, principalPaid = NULL;
SELECT  theBatchNoS;
            -- Exit the loop if no more rows found
            IF innerNotFound = 1 THEN
                LEAVE TXNIDS_LOOP;
            END IF;

            -- Fetch payments, interest and principal from different accounts
            SELECT debit INTO thePaid 
            FROM bsanca01123000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO InterestPaid 
            FROM bsanca03301000110 
            WHERE chq_number = theBatchNoS;

            SELECT credit INTO principalPaid 
            FROM bsanca01128000110 
            WHERE chq_number = theBatchNoS;

            -- Check and set default values for null or unset variables
            IF thePaid = '-' OR ISNULL(thePaid) THEN
                SET thePaid = 0.0;
            END IF;

            IF InterestPaid = '-' OR ISNULL(InterestPaid) THEN
                SET InterestPaid = 0.0;
            END IF;

            IF principalPaid = '-' OR ISNULL(principalPaid) THEN
                SET principalPaid = 0.0;
            END IF;


                   IF thePaid>0 THEN 
            -- Update the statement with calculated values
            UPDATE loandisburserepaystatement 
            SET AmountPaid = thePaid, PrincipalPaid = principalPaid, 
                InterestPaid = InterestPaid, LoanBalance = theOpeningBal - thePaid 
            WHERE BatchCode = theBatchNoS;

            -- Adjust the opening balance for the next iteration
            SET theOpeningBal = theOpeningBal - thePaid;
END IF;

            -- Reset the not found handler flag
            SET innerNotFound = 0;
        END LOOP TXNIDS_LOOP; 
    END IF;

    -- Close the cursor
    CLOSE forBatchNos; 

END $$

-- Reset the delimiter to its default value
DELIMITER ;



