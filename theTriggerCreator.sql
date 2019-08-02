DROP PROCEDURE IF EXISTS triggerCreatoPro;
 DELIMITER //

 CREATE PROCEDURE triggerCreatoPro(IN accountNumber VARCHAR(120)) READS SQL DATA BEGIN
 /*
 
 SET @sql_text2 = "DELIMITER $$";

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;
 */

 SET @sql_text9 = concat(CAST("CREATE TRIGGER TSANCA" AS CHAR CHARACTER SET utf8),accountNumber,CAST("  BEFORE INSERT ON  BSANCA" AS CHAR CHARACTER SET utf8),accountNumber,CAST("  FOR EACH ROW BEGIN

IF(@SEVariable IS NULL) THEN     

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



UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;" AS CHAR CHARACTER SET utf8),

CAST("CALL   updateMaster" AS CHAR CHARACTER SET utf8),accountNumber,CAST("(  NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 


    
ELSEIF(@SEVariable =1) THEN 


SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);

END IF;    
         
        
END " AS CHAR CHARACTER SET utf8));

PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;

 /*SET @sql_text1 = concat(CAST(" DELIMITER ;" AS CHAR CHARACTER SET utf8));

  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;  */

 END //
 DELIMITER ;