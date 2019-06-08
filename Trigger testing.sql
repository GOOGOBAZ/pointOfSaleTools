
DROP TRIGGER IF EXISTS TSANCA01123000110;

DELIMITER //

CREATE TRIGGER  TSANCA01123000110 BEFORE INSERT ON BSANCA01123000110 FOR EACH ROW BEGIN

IF(NEW.other_one LIKE '%Cr%') THEN 
        
SET @creditAccount=NEW.account_number;
        
 SET @debitAccount=NEW.credit_account_no;

CALL accountNma(@creditAccount,@accountName);

INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)

VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
        
 END IF;
        
IF(NEW.other_one LIKE '%Dr%') THEN 

SET @creditAccount=NEW.credit_account_no;

SET @debitAccount=NEW.account_number;
        
CALL accountNma(@debitAccount,@accountName);

INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)

 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);    

END IF;



UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;

CALL   updateMaster"+AccountNumber+"(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id);

END;

DELIMITER ;