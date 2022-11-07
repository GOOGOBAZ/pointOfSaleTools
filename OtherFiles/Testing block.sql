


DROP PROCEDURE IF EXISTS SavingsDepositsWithdrawn;

DELIMITER //

CREATE PROCEDURE SavingsDepositsWithdrawn(IN theDate DATE,OUT savingDepositWith VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '05502%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 SET @cashCredit=0;
SET @cashDebit=0;
 
ACCOUNTS_LOOP: LOOP 
FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

IF processindFeesAccountRep IS NULL THEN
SET processindFeesAccountRep=0;
END IF;
 IF outer_done=1 THEN
LEAVE ACCOUNTS_LOOP;
 END IF;
 

INNER_BLOCK: BEGIN

DECLARE batchNumbersReps VARCHAR(30); 
DECLARE inner_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
/* SELECT @qr; */
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;


/* SELECT batchNumbersReps; */
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 
 SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

PREPARE stm FROM @quary;

EXECUTE stm;

DEALLOCATE PREPARE stm;

/* SELECT @batch; */

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
/*   SELECT @cashDebited,@amountCredit; */
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
 IF @amountCredit IS NULL THEN
SET @amountCredit=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

PREPARE stm FROM @quary;

EXECUTE stm;

DEALLOCATE PREPARE stm;



IF @amountCredit<>'-' OR @amountCredit<>0 THEN
SET @cashCredit=@cashCredit+@amountCredit;

END IF;

END IF;


IF @cashCredited>0 THEN


SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;


/* SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF; */



IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

END IF;




END IF;



END LOOP BATCH_LOOP; 

SET inner_done=0;

CLOSE cursor_forSelectingBatchNumbers; 

END INNER_BLOCK;


DROP TABLE account_view;
 IF @amountCredit IS NULL THEN
SET @amountCredit=0;
END IF;

 IF @amountDebit IS NULL THEN
SET @amountDebit=0;
END IF;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashDebit-@cashCredit;


 IF @cash IS NULL THEN
SET savingDepositWith='0';
ELSE

SET savingDepositWith=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;