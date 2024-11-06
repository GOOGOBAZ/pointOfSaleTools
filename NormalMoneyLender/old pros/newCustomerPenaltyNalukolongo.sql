DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;
DELIMITER //

CREATE PROCEDURE theAMDAPenaltyComputation()
BEGIN
  DECLARE lDone1 INT DEFAULT 0;
  DECLARE trnId VARCHAR(20);
  DECLARE instalmentNo INT;
  DECLARE computedPenalty DOUBLE;
  DECLARE oldBalance DOUBLE;
  DECLARE newBalance DOUBLE;
  DECLARE oldPenaltyRemaining DOUBLE;
  DECLARE newPenaltyRemaining DOUBLE;
  DECLARE oldInstalmentAmount DOUBLE;
  DECLARE newInstalmentAmount DOUBLE;
  DECLARE oldLoanPenalty DOUBLE;
  DECLARE newLoanPenalty DOUBLE;
  DECLARE oldLoanPenaltyRemaining, oldInstalmentRemaining, newInstalmentRemaining, newLoanBalance, newLoanPenaltyBalance DOUBLE;
  DECLARE newLoanPenaltyRemaining DOUBLE;
  DECLARE theInstalmentNo INT;
  DECLARE oldInstalmnetAmount DOUBLE;
  DECLARE exists_check INT;

  DECLARE curLoanTrnId CURSOR FOR 
    SELECT trn_id 
    FROM new_loan_appstore 
    WHERE (loan_cycle_status = 'Disbursed' OR loan_cycle_status = 'Renewed')
      AND NOT (loan_tenure = '1.0 MONTHS' OR loan_tenure = '1 MONTHS');

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1 = 1;

  OPEN curLoanTrnId;

  loanTrnIdLoop: LOOP
    FETCH curLoanTrnId INTO trnId;

    IF lDone1 THEN
      LEAVE loanTrnIdLoop;
    END IF;

    -- Debugging point: Output the current loan transaction ID
    SELECT 'Processing loan transaction ID:', trnId;

    -- Inner block for handling the overdue instalments cursor
    BEGIN
      DECLARE lDone2 INT DEFAULT 0;

      DECLARE curInstalments CURSOR FOR 
        SELECT instalment_no 
        FROM new_loan_appstoreamort 
        WHERE instalment_due_date <= NOW()
          AND instalment_status <> 'P' 
          AND master1_id = trnId;

      DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone2 = 1;

      OPEN curInstalments;

      instalmentLoop: LOOP
        FETCH curInstalments INTO instalmentNo;

        IF lDone2 THEN
          LEAVE instalmentLoop;
        END IF;

        -- Debugging point: Output the current instalment number
        SELECT 'Processing instalment number:', instalmentNo;

        -- Check if penalty is already computed for this instalment using a prepared statement
        SET @exists_check := 0;
        SET @sql := 'SELECT COUNT(*) INTO @exists_check FROM amdapenaltycomputenowdetails WHERE loanTrnId = ? AND instalmentNo = ?';
        PREPARE stmt FROM @sql;
        SET @trnId := trnId;
        SET @instalmentNo := instalmentNo;
        EXECUTE stmt USING @trnId, @instalmentNo;
        DEALLOCATE PREPARE stmt;

        -- Debugging point: Output the result of the existence check
        SELECT 'Existence check result:', @exists_check;

        IF @exists_check = 0 THEN
          -- Debugging point: Output when the penalty computation is starting
          SELECT 'Computing penalty for instalment number:', instalmentNo;

          -- Calculate the penalty for the overdue instalment using prepared statements
          SET @oldBalance := 0;
          SET @oldPenaltyRemaining := 0;
          SET @oldInstalmentAmount := 0;
          SET @sql := 'SELECT balance_due, TotalLoanPenaltyRemaining, instalment_amount, total_loanAmount INTO @oldBalance, @oldPenaltyRemaining, @oldInstalmentAmount, @totalLoanAmount FROM new_loan_appstore WHERE trn_id = ?';
          PREPARE stmt2 FROM @sql;
          SET @trnId := trnId;
          EXECUTE stmt2 USING @trnId;
          DEALLOCATE PREPARE stmt2;

          SET @theInstalmentNo := 0;
          SET @oldLoanPenalty := 0;
          SET @oldLoanPenaltyRemaining := 0;
          SET @oldInstalmnetAmount := 0;
          SET @oldInstalmentRemaining := 0;
          SET @sql := 'SELECT instalment_no, LoanPenalty, LoanPenaltyRemaining, instalment_amount, InstalmentRemaining INTO @theInstalmentNo, @oldLoanPenalty, @oldLoanPenaltyRemaining, @oldInstalmnetAmount, @oldInstalmentRemaining FROM new_loan_appstoreamort WHERE master1_id = ? AND instalment_no = ?';
          PREPARE stmt3 FROM @sql;
          SET @trnId := trnId;
          SET @instalmentNo := instalmentNo;
          EXECUTE stmt3 USING @trnId, @instalmentNo;
          DEALLOCATE PREPARE stmt3;

          SELECT @theInstalmentNo, @oldLoanPenalty, @oldLoanPenaltyRemaining, @oldInstalmnetAmount, @oldInstalmentRemaining;

          SET @theTrnId := 0;
          SET @LoanBalance := 0;
          SET @LoanPenaltyBalance := 0;
          SET @sql := 'SELECT TrnId, LoanBalance, LoanPenaltyBalance INTO @theTrnId, @LoanBalance, @LoanPenaltyBalance FROM pmms.loandisburserepaystatement WHERE loanTrnId = ? ORDER BY TrnId DESC LIMIT 1';
          PREPARE stmt4 FROM @sql;
          SET @trnId := trnId;
          EXECUTE stmt4 USING @trnId;
          DEALLOCATE PREPARE stmt4;

          SELECT @theTrnId, @LoanBalance, @LoanPenaltyBalance;

          SET computedPenalty = (@oldInstalmentRemaining * 0.1);

          SET newBalance = @oldBalance + computedPenalty;
          SET newInstalmentRemaining = @oldInstalmentRemaining + computedPenalty;
          SET newPenaltyRemaining = @oldPenaltyRemaining + computedPenalty;
          SET newInstalmentAmount = @oldInstalmentAmount + computedPenalty;
          SET newLoanPenalty = @oldLoanPenalty + computedPenalty;
          SET newLoanPenaltyRemaining = @oldLoanPenaltyRemaining + computedPenalty;
          SET newLoanBalance = @LoanBalance + computedPenalty;
          SET newLoanPenaltyBalance = @LoanPenaltyBalance + computedPenalty;

          SELECT newInstalmentAmount;

          -- Debugging point: Output computed values
          SELECT 'Computed values:', newBalance, newPenaltyRemaining, newInstalmentAmount, newLoanPenalty, newLoanPenaltyRemaining;

          -- Insert penalty computation record
          INSERT INTO amdapenaltycomputenowdetails (loanTrnId, instalmentNo, instalmentComputeStatus, loanComputeStatus, dateComputed)
          VALUES (trnId, instalmentNo, 2, 2, DATE(NOW()));

          -- Update loan summary balance with computed penalty
          UPDATE new_loan_appstore 
          SET balance_due = IFNULL(newBalance, 0), 
              TotalLoanPenaltyRemaining = IFNULL(newPenaltyRemaining, 0), 
              instalment_amount = IFNULL(newInstalmentAmount, 0) 
          WHERE trn_id = trnId;

          -- Update loan summary balance in new_loan_appstore1 with computed penalty
          UPDATE new_loan_appstore1 
          SET balance_due = IFNULL(newBalance, 0), 
              TotalLoanPenaltyRemaining = IFNULL(newPenaltyRemaining, 0), 
              instalment_amount = IFNULL(newInstalmentAmount, 0) 
          WHERE trn_id = trnId;

          -- Update specific instalment with computed penalty
          UPDATE new_loan_appstoreamort 
          SET LoanPenalty = IFNULL(newLoanPenalty, 0), 
              LoanPenaltyRemaining = IFNULL(newLoanPenaltyRemaining, 0), 
              InstalmentRemaining = IFNULL(newInstalmentRemaining, 0)
          WHERE instalment_no = instalmentNo AND master1_id = trnId;

          SELECT @theTrnId, @LoanBalance, @LoanPenaltyBalance;

          -- Update specific instalment with computed penalty
          SET @sql = 'UPDATE pmms.loandisburserepaystatement SET LoanPenaltyBalance = IFNULL(?, 0), LoanBalance = IFNULL(?, 0) WHERE TrnId = ?';
          PREPARE stmt5 FROM @sql;
          SET @newLoanPenalty = newLoanPenalty;
          SET @newLoanBalance = newBalance;
          SET @theTrnId := @theTrnId; -- use @theTrnId instead of trnId
          EXECUTE stmt5 USING @newLoanPenalty, @newLoanBalance, @theTrnId;
          DEALLOCATE PREPARE stmt5;

          -- Debugging point: Output when the penalty computation is completed
          SELECT 'Penalty computed for instalment number:', instalmentNo;
        ELSE
          -- Debugging point: Output if penalty is already computed
          SELECT 'Penalty already computed for instalment number:', instalmentNo;
        END IF;

        -- Reinitialize variables for the next iteration
        SET computedPenalty = NULL;
        SET oldBalance = NULL;
        SET newBalance = NULL;
        SET oldPenaltyRemaining = NULL;
        SET newPenaltyRemaining = NULL;
        SET oldInstalmentAmount = NULL;
        SET newInstalmentAmount = NULL;
        SET oldLoanPenalty = NULL;
        SET newLoanPenalty = NULL;
        SET oldLoanPenaltyRemaining = NULL;
        SET newLoanPenaltyRemaining = NULL;
        SET instalmentNo = NULL;
        SET oldInstalmnetAmount = NULL;

      END LOOP instalmentLoop;

      CLOSE curInstalments;

      -- Reinitialize lDone2 for the next loop
      SET lDone2 = 0;
    END;

    -- Reinitialize variables for the next iteration
    SET lDone1 = 0;
    SET trnId = NULL;
    SET instalmentNo = NULL;
  END LOOP loanTrnIdLoop;

  CLOSE curLoanTrnId;

END //
DELIMITER ;

-- Testing table creation
DROP TABLE IF EXISTS amdapenaltycomputenowdetails;
CREATE TABLE IF NOT EXISTS amdapenaltycomputenowdetails (
  id INT(11) NOT NULL AUTO_INCREMENT,
  loanTrnId VARCHAR(20),
  instalmentNo INT(11) NOT NULL,
  instalmentComputeStatus INT(11) NULL, -- 1=ONGOING, 2=STOPPED
  loanComputeStatus INT(11) NULL, -- 1=ONGOING, 2=STOPPED
  dateComputed DATE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Call the procedure for testing
CALL theAMDAPenaltyComputation();
