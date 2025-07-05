ALTER TABLE arch_new_loan_appstore DROP PRIMARY KEY;
ALTER TABLE arch_new_loan_appstoreamort DROP PRIMARY KEY;
ALTER TABLE arch_new_loan_appstore1 DROP PRIMARY KEY;
ALTER TABLE pmms.arch_loandisburserepaystatement DROP PRIMARY KEY;


DROP PROCEDURE IF EXISTS archive_completed_loans_cascade;
DROP TEMPORARY TABLE IF EXISTS tmp_completed_trn_ids;

DELIMITER $$

CREATE PROCEDURE archive_completed_loans_cascade()
BEGIN
    -- Error handler to rollback in case of any failure
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'ERROR' AS theMessage, 'Archiving failed' AS MESSAGE;
    END;

    START TRANSACTION;

    -- 1. Identify completed loans
    CREATE TEMPORARY TABLE tmp_completed_trn_ids AS
        SELECT trn_id FROM new_loan_appstore WHERE loan_cycle_status = 'Completed';

    -- 2. Archive amortization schedules
    INSERT INTO arch_new_loan_appstoreamort
    SELECT a.*
      FROM new_loan_appstoreamort a
      JOIN tmp_completed_trn_ids t ON a.master1_id = t.trn_id;

    -- 3. Archive master loan records
    INSERT INTO arch_new_loan_appstore
    SELECT * FROM new_loan_appstore
    WHERE trn_id IN (SELECT trn_id FROM tmp_completed_trn_ids);

    -- 4. Archive master loan records from new_loan_appstore1 if applicable
    INSERT INTO arch_new_loan_appstore1
    SELECT * FROM new_loan_appstore1
    WHERE loan_cycle_status = 'Completed';

    -- 5. Delete master loan records from new_loan_appstore1 if applicable
    DELETE FROM new_loan_appstore1
    WHERE loan_cycle_status = 'Completed';

    -- 6. Archive repayment/disbursement statements
    INSERT INTO pmms.arch_loandisburserepaystatement
    SELECT s.*
      FROM pmms.loandisburserepaystatement s
      JOIN tmp_completed_trn_ids t ON s.loanTrnId = t.trn_id;

    -- 7. Delete amortization schedules
    DELETE a FROM new_loan_appstoreamort a
      JOIN tmp_completed_trn_ids t ON a.master1_id = t.trn_id;

    -- 8. Delete repayment/disbursement statements
    DELETE s FROM pmms.loandisburserepaystatement s
      JOIN tmp_completed_trn_ids t ON s.loanTrnId = t.trn_id;

    -- 9. Delete master loan records
    DELETE FROM new_loan_appstore
    WHERE trn_id IN (SELECT trn_id FROM tmp_completed_trn_ids);

    -- 10. Return affected trn_ids for audit
    SELECT trn_id AS archived_trn_id FROM tmp_completed_trn_ids;

    DROP TEMPORARY TABLE IF EXISTS tmp_completed_trn_ids;

    COMMIT;
END$$

DELIMITER ;


CALL archive_completed_loans_cascade();
