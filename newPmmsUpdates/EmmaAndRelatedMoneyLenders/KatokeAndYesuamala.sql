

DROP PROCEDURE IF EXISTS ProcessingFeesCollected;
DELIMITER //
CREATE PROCEDURE ProcessingFeesCollected(IN theDate DATE,OUT processingFees VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO processingFees FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03315%';
IF ISNULL(processingFees) THEN
SET processingFees=0.0;
END IF;

-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


--  SET @cash=0;
-- OPEN cursor_forSelectingProcessingFeesAccounts; 

-- ACCOUNTS_LOOP: LOOP 

-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 


-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DEALLOCATE PREPARE stmt;


-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- /* SELECT batchNumbersReps; */

--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;

-- CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);

-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;



-- SET inner_done=0;
-- END LOOP BATCH_LOOP; 
-- CLOSE cursor_forSelectingBatchNumbers; 
-- END INNER_BLOCK;

-- DROP TABLE account_view;


-- SET outer_done=0;
--  END LOOP ACCOUNTS_LOOP;
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cash; */
-- SET processingFees=@cash;
-- END OUTER_BLOCK//


        END //

        DELIMITER ;


/* CALL ProcessingFeesCollected('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */





DROP PROCEDURE IF EXISTS LedgerFees;
DELIMITER //
CREATE PROCEDURE LedgerFees(IN theDate DATE,OUT ledgerFessCol VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO ledgerFessCol FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03304%';
IF ISNULL(ledgerFessCol) THEN
SET ledgerFessCol=0.0;
END IF;
-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03304%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;


--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;


-- /* SELECT processindFeesAccountRep; */
-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- SET ledgerFessCol=@cash;
-- END OUTER_BLOCK//

END //

DELIMITER ;


/* 
CALL LedgerFees('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */







DROP PROCEDURE IF EXISTS BadDebtsRecovered;
DELIMITER //
CREATE PROCEDURE BadDebtsRecovered(IN theDate DATE,OUT badDebtsRecover VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO badDebtsRecover FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03318%';
IF ISNULL(badDebtsRecover) THEN
SET badDebtsRecover=0.0;
END IF;

-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03318%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET badDebtsRecover='0';
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET badDebtsRecover='0';
-- ELSE
-- SET badDebtsRecover=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL BadDebtsRecovered('2019-06-21',@badDebtsRecover1);

SELECT @badDebtsRecover1; */





DROP PROCEDURE IF EXISTS MembershipFees;
DELIMITER //
CREATE PROCEDURE MembershipFees(IN theDate DATE,OUT memberShipFessCol VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO memberShipFessCol FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03309%';
IF ISNULL(memberShipFessCol) THEN
SET memberShipFessCol=0.0;
END IF;

-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03309%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;

-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET memberShipFessCol='0';
-- ELSE
-- SET memberShipFessCol=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL MembershipFees('2019-06-21',@memberShipFessCol);

SELECT @memberShipFessCol; */






DROP PROCEDURE IF EXISTS annualSubFees;
DELIMITER //
CREATE PROCEDURE annualSubFees(IN theDate DATE,OUT annualFeesRecovered VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO annualFeesRecovered FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03316%';
IF ISNULL(annualFeesRecovered) THEN
SET annualFeesRecovered=0.0;
END IF;

-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03316%'; 
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET annualFeesRecovered='0';
-- ELSE
-- SET annualFeesRecovered=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL annualSubFees('2019-06-21',@annualFeesRecovered);

SELECT @annualFeesRecovered; */







DROP PROCEDURE IF EXISTS InterestRecover;
DELIMITER //
CREATE PROCEDURE InterestRecover(IN theDate DATE,OUT InterestR VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO InterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03301%';
IF ISNULL(InterestR) THEN
SET InterestR=0.0;
END IF;

-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03301%'; 
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;
-- -- select processindFeesAccountRep;
-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;
-- -- SELECT batchNumbersReps;
-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET InterestR='0';
-- ELSE
-- SET InterestR=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;




/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */


DROP PROCEDURE IF EXISTS accumulatedInterestR;
DELIMITER //
CREATE PROCEDURE accumulatedInterestR(IN theDate DATE,OUT accumulatedInterestR VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO accumulatedInterestR FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03311%';
IF ISNULL(accumulatedInterestR) THEN
SET accumulatedInterestR=0.0;
END IF;

-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03311%'; 
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET accumulatedInterestR='0';
-- ELSE
-- SET accumulatedInterestR=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */








DROP PROCEDURE IF EXISTS loanPenaltyRecov;
DELIMITER //
CREATE PROCEDURE loanPenaltyRecov(IN theDate DATE,OUT loanPenaltyRecovered VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO loanPenaltyRecovered FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03312%';
IF ISNULL(loanPenaltyRecovered) THEN
SET loanPenaltyRecovered=0.0;
END IF;


-- OUTER_BLOCK: BEGIN


-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03312%'; 
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep;
-- SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET loanPenaltyRecovered='0';
-- ELSE
-- SET loanPenaltyRecovered=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL loanPenaltyRecov('2019-06-21',@loanPenaltyRecovered);

SELECT @loanPenaltyRecovered; */






-- 16/09/2022	16/09/2022	Henry deposit Processed on 16/09/2022
--   From Equity float account	1000000.0	-	427486.0	BTN165955


DROP PROCEDURE IF EXISTS otherIncomesCollected;
DELIMITER //
CREATE PROCEDURE otherIncomesCollected(IN theDate DATE,OUT otherIncomesCollectedX VARCHAR(100)) READS SQL DATA 
BEGIN
SELECT  SUM(credit) INTO otherIncomesCollectedX FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%');

-- IF ISNULL(processingFees) THEN
-- SET processingFees=0.0;
-- END IF;



-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%'  OR account_number like  '03310%'  OR account_number like  '03313%' OR account_number like '03314%'  OR account_number like '03317%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 





-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;

-- /* SELECT processindFeesAccountRep; */
-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
--  CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;



-- IF @amount<>'-' OR @amount<>0 THEN
-- SET @cash=@cash+@amount;

-- END IF;

-- END IF;




-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amount IS NULL THEN
-- SET @amount=0;
-- END IF;
-- SET @amount=0;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /*  SELECT @cash; */
--  IF @cash IS NULL THEN
-- SET otherIncomesCollectedX='0';
-- ELSE
-- SET otherIncomesCollectedX=@cash;
-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;

/* 
CALL otherIncomesCollected('2019-06-21',@otherIncomesCollectedX);

SELECT @otherIncomesCollectedX; */













DROP PROCEDURE IF EXISTS PayablesCreated;

DELIMITER //

CREATE PROCEDURE PayablesCreated(IN theDate DATE,OUT payableCreatedFinal VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO payableCreatedFinal FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05500%';
IF ISNULL(payableCreatedFinal) THEN
SET payableCreatedFinal=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '05500%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /*END IF; */

-- /* IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /*END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET payableCreatedFinal='0';
-- ELSE

-- SET payableCreatedFinal=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL PayablesCreated('2019-06-22',@payableCreatedFinal);

SELECT @payableCreatedFinal; */










DROP PROCEDURE IF EXISTS InsurancePayableMade;

DELIMITER //


CREATE PROCEDURE InsurancePayableMade(IN theDate DATE,OUT insurancePayableMadev VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO insurancePayableMadev FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05524%';
IF ISNULL(insurancePayableMadev) THEN
SET insurancePayableMadev=0.0;
END IF;


--  DECLARE insurancePayableAccountRep VARCHAR(30); 


--  DECLARE l_done INTEGER DEFAULT 0; 

--  DECLARE lastDate,originalDueDate DATE; 



--  DECLARE forSelectingInsurancePayableCreatedAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number like '05524%' ;
 


--  DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


--  OPEN forSelectingInsurancePayableCreatedAccounts; 

-- SET @cashBal=0;
-- accounts_loop: LOOP 

--  FETCH forSelectingInsurancePayableCreatedAccounts into insurancePayableAccountRep;


--  IF l_done=1 THEN

-- LEAVE accounts_loop;

--  END IF;

--    SET @sql_text = concat(CAST(" SELECT  ledger_balance INTO @openBalance FROM    pmms.bsanca" AS CHAR CHARACTER SET utf8),insurancePayableAccountRep,CAST(" WHERE  trn_date<'" AS CHAR CHARACTER SET utf8),theDate,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
-- PREPARE stmt FROM @sql_text;
-- /* SELECT @sql_text; */
--   EXECUTE stmt;
-- DROP PREPARE stmt;

--  SET @sql_text = concat(CAST(" SELECT  ledger_balance INTO @closingBalance FROM    pmms.bsanca" AS CHAR CHARACTER SET utf8),insurancePayableAccountRep,CAST(" WHERE  trn_date<='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
-- PREPARE stmt FROM @sql_text;
-- /* SELECT @sql_text; */
--   EXECUTE stmt;
-- DROP PREPARE stmt;

-- /* SELECT @openBalance;
-- SELECT @closingBalance; */

-- IF ISNULL(@openBalance) THEN

-- SET @openBalance=0;

-- END IF;

-- IF ISNULL(@closingBalance) THEN

-- SET @closingBalance=0;

-- END IF;


-- SET @actualBalance=@closingBalance-@openBalance;

-- IF @actualBalance>0 THEN 

-- SET @cashBal=@cashBal+@actualBalance;

-- END IF;
-- /* 
-- SELECT @cashBal;
--  */

-- IF ISNULL(@cashBal) THEN

-- SET @cashBal=0;

-- END IF;

-- SET l_done=0;


-- SET @openBalance=0;
-- SET @closingBalance=0;
--  END LOOP accounts_loop;
 
--  IF ISNULL(@cashBal) THEN

-- SET @cashBal=0;

-- END IF;
 
--  SET insurancePayableMadev=@cashBal;

--  CLOSE forSelectingInsurancePayableCreatedAccounts;

END //

DELIMITER ;


/* CALL InsurancePayableMade('2019-04-09',@insurancePayableMadeX);

SELECT @insurancePayableMade; */




DROP PROCEDURE IF EXISTS otherLiabilitiesAndProvisionsMade;

DELIMITER //

CREATE PROCEDURE otherLiabilitiesAndProvisionsMade(IN theDate DATE,OUT liabilityProvi VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO liabilityProvi FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%'  OR debit_account_no like '05509%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%') ;

IF ISNULL(liabilityProvi) THEN
SET liabilityProvi=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number like '05504%' 
--  OR account_number like '05501%' OR account_number like '05503%' OR account_number like '05505%'  OR account_number like '05509%' OR account_number like '05506%' OR account_number like '05507%'
--  OR account_number like '05522%' OR account_number like '05525%' OR account_number like '05527%' OR account_number like '05526%' OR account_number like '05528%'
--  OR account_number like '05523%' OR account_number like '05523%' OR account_number like '05508%' OR account_number like '05510%' 
--  OR account_number like '05511%' OR account_number like '05512%' OR account_number like '05513%' OR account_number like '05514%' OR account_number like '05515%'  
--  OR account_number like '05516%'  OR account_number like '05517%'  OR account_number like '05518%'  OR account_number like '05519%'  OR account_number like '05520%'  OR account_number like '05521%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /*/*END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /*END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET liabilityProvi='0';
-- ELSE

-- SET liabilityProvi=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //

DELIMITER ;


/* CALL otherLiabilitiesAndProvisionsMade('2019-06-22',@liabilityProvi);

SELECT @liabilityProvi; */







DROP PROCEDURE IF EXISTS capitalisationMade;

DELIMITER //

CREATE PROCEDURE capitalisationMade(IN theDate DATE,OUT someEquity VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO someEquity FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04400%';
IF ISNULL(someEquity) THEN
SET someEquity=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04400%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET someEquity='0';
-- ELSE

-- SET someEquity=@cash;


-- END IF;


-- END OUTER_BLOCK//

END //

DELIMITER ;


/* CALL capitalisationMade('2019-06-22',@someEquity);

SELECT @someEquity; */






DROP PROCEDURE IF EXISTS OtherCapitalisationsAndReservesMade;

DELIMITER //

CREATE PROCEDURE OtherCapitalisationsAndReservesMade(IN theDate DATE,OUT otheCapsAndReserversMade VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO otheCapsAndReserversMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no>='044010000110' AND debit_account_no<='04430999999';
IF ISNULL(otheCapsAndReserversMade) THEN
SET otheCapsAndReserversMade=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number>='044010000110' AND account_number<='04430999999';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET otheCapsAndReserversMade='0';
-- ELSE

-- SET otheCapsAndReserversMade=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;

/* CALL OtherCapitalisationsAndReservesMade('2019-06-22',@otheCapsAndReserversMade);


SELECT @otheCapsAndReserversMade; */






DROP PROCEDURE IF EXISTS RecevablesRefunded;

DELIMITER //

CREATE PROCEDURE RecevablesRefunded(IN theDate DATE,OUT Refundreceiavale VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(credit) INTO Refundreceiavale FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01131%';
IF ISNULL(Refundreceiavale) THEN
SET Refundreceiavale=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01131%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;
-- /* 
-- SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET Refundreceiavale='0';
-- ELSE

-- SET Refundreceiavale=@cash;


-- END IF;


-- END OUTER_BLOCK//
END   //
DELIMITER ;
/* 

CALL RecevablesRefunded('2019-06-23',@Refundreceiavale); */

/* SELECT @Refundreceiavale; */




DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsRefunded;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsRefunded(IN theDate DATE,OUT otherReceiAndPrepaymentRend VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO otherReceiAndPrepaymentRend FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01118%';
IF ISNULL(otherReceiAndPrepaymentRend) THEN
SET otherReceiAndPrepaymentRend=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number LIKE '01117%' OR account_number LIKE '01118%' OR account_number LIKE '01119%'
--  OR account_number LIKE '01132%'  OR account_number LIKE '01133%'  OR account_number LIKE '01134%'  OR account_number LIKE '01135%'  OR account_number LIKE '01120%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;



-- /* 
-- IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET otherReceiAndPrepaymentRend='0';
-- ELSE

-- SET otherReceiAndPrepaymentRend=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsRefunded('2019-06-23',@otherReceiAndPrepaymentRend); */

/* SELECT @otherReceiAndPrepaymentRend; */




DROP PROCEDURE IF EXISTS BankWithdrawsMade;

DELIMITER //

CREATE PROCEDURE BankWithdrawsMade(IN theDate DATE,OUT BankWithdrws VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO BankWithdrws FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01122%';
IF ISNULL(BankWithdrws) THEN
SET BankWithdrws=0.0;
END IF;





-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01122%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET BankWithdrws='0';
-- ELSE

-- SET BankWithdrws=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

/* SELECT @BankWithdrws; */








-- DROP PROCEDURE IF EXISTS BankWithdrawsMade;

-- DELIMITER //

-- CREATE PROCEDURE BankWithdrawsMade(IN theDate DATE,OUT BankWithdrws VARCHAR(100)) READS SQL DATA 

-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01122%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET BankWithdrws='0';
-- ELSE

-- SET BankWithdrws=@cash;


-- END IF;


-- END OUTER_BLOCK//

-- DELIMITER ;


-- /* CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

-- SELECT @BankWithdrws; */



DROP PROCEDURE IF EXISTS princimpalLoanRepaymentsMade;

DELIMITER //

CREATE PROCEDURE princimpalLoanRepaymentsMade(IN theDate DATE,OUT princimpalRepaymentsMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO princimpalRepaymentsMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01128%';
IF ISNULL(princimpalRepaymentsMade) THEN
SET princimpalRepaymentsMade=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01128%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET princimpalRepaymentsMade='0';
-- ELSE

-- SET princimpalRepaymentsMade=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL princimpalLoanRepaymentsMade('2019-06-23',@princimpalRepaymentsMade);

SELECT @princimpalRepaymentsMade; */





DROP PROCEDURE IF EXISTS refundFromMobileMoneyPayableMade;

DELIMITER //

CREATE PROCEDURE refundFromMobileMoneyPayableMade(IN theDate DATE,OUT mobileMoneyRefund VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO mobileMoneyRefund FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01121%';
IF ISNULL(mobileMoneyRefund) THEN
SET mobileMoneyRefund=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01121%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET mobileMoneyRefund='0';
-- ELSE

-- SET mobileMoneyRefund=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL refundFromMobileMoneyPayableMade('2019-06-23',@mobileMoneyRefund);

SELECT @mobileMoneyRefund; */




DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsDisposedOff;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsDisposedOff(IN theDate DATE,OUT fixedAssetsAndInvestmentDisp VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(credit) INTO fixedAssetsAndInvestmentDisp FROM pmms.general_ledger WHERE  trn_date=theDate AND   (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%');

IF ISNULL(fixedAssetsAndInvestmentDisp) THEN
SET fixedAssetsAndInvestmentDisp=0.0;
END IF;


-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number LIKE '01100%' OR account_number LIKE '01101%'
-- OR account_number LIKE '01102%' OR account_number LIKE '01103%' OR account_number LIKE '01104%' OR account_number LIKE '01105%' OR account_number LIKE '01106%'  OR account_number LIKE '01108%' OR account_number LIKE '01109%'
-- OR account_number LIKE '01110%' OR account_number LIKE '01111%'  OR account_number LIKE '01112%'  OR account_number LIKE '01136%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;


--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;




-- /* IF @cashCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

-- /* END IF;

-- IF @amountCredit>0 THEN

-- /* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

-- /* END IF; */

-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;

-- /* SELECT batchNumbersReps; */

-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;
-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashCredit;


--  IF @cash IS NULL THEN
-- SET fixedAssetsAndInvestmentDisp='0';
-- ELSE

-- SET fixedAssetsAndInvestmentDisp=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL fixedAssetsAndInvestmentsDisposedOff('2019-06-23',@fixedAssetsAndInvestmentDisp);

SELECT @fixedAssetsAndInvestmentDisp; */





DROP PROCEDURE IF EXISTS ExpensesMade;

DELIMITER //

CREATE PROCEDURE ExpensesMade(IN theDate DATE,OUT ExpensesMa VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO ExpensesMa FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '022%';
IF ISNULL(ExpensesMa) THEN
SET ExpensesMa=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '022%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */




-- /* IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET ExpensesMa='0';
-- ELSE

-- SET ExpensesMa=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL ExpensesMade('2019-06-23',@ExpensesMa);

SELECT @ExpensesMa; */






DROP PROCEDURE IF EXISTS InterestWrittenOff;

DELIMITER //

CREATE PROCEDURE InterestWrittenOff(IN theDate DATE,OUT interWriteOff VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO interWriteOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03301%';
IF ISNULL(interWriteOff) THEN
SET interWriteOff=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03301%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */



-- /* 
-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET interWriteOff='0';
-- ELSE

-- SET interWriteOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;




/* CALL InterestWrittenOff('2019-06-24',@interWriteOff);

SELECT @interWriteOff; */



DROP PROCEDURE IF EXISTS accumuInteresWrittenOff;

DELIMITER //

CREATE PROCEDURE accumuInteresWrittenOff(IN theDate DATE,OUT accumuIntereWrittenOff VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO accumuIntereWrittenOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03311%';
IF ISNULL(accumuIntereWrittenOff) THEN
SET accumuIntereWrittenOff=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03311%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET accumuIntereWrittenOff='0';
-- ELSE

-- SET accumuIntereWrittenOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;



/* 
CALL accumuInteresWrittenOff('2019-06-24',@accumuIntereWrittenOff);

SELECT @accumuIntereWrittenOff; */





DROP PROCEDURE IF EXISTS processingFeesWrittenOff;

DELIMITER //

CREATE PROCEDURE processingFeesWrittenOff(IN theDate DATE,OUT processFeesWriteOff VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO processFeesWriteOff FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '03315%';
IF ISNULL(processFeesWriteOff) THEN
SET processFeesWriteOff=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET processFeesWriteOff='0';
-- ELSE

-- SET processFeesWriteOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* 

CALL processingFeesWrittenOff('2019-06-24',@processFeesWriteOff);

SELECT @processFeesWriteOff; */






DROP PROCEDURE IF EXISTS OtherIncomesWrittenOff;

DELIMITER //

CREATE PROCEDURE OtherIncomesWrittenOff(IN theDate DATE,OUT otherIncomesWrOff VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO otherIncomesWrOff FROM pmms.general_ledger WHERE  trn_date=theDate AND (debit_account_no LIKE '03312%' OR debit_account_no LIKE '03316%' OR debit_account_no LIKE '03309%' OR debit_account_no LIKE '03318%' OR debit_account_no LIKE '03304%' OR debit_account_no like '03305%' OR debit_account_no like  '03306%' OR debit_account_no like  '03307%' OR debit_account_no like '03308%'  OR debit_account_no like  '03310%'  OR debit_account_no like  '03313%' OR debit_account_no like '03314%'  OR debit_account_no like '03317%' OR debit_account_no like  '03319%' OR debit_account_no like '03320%' OR debit_account_no like  '03321%' OR debit_account_no like  '03322%' OR debit_account_no like '03323%' OR debit_account_no like  '03324%' OR debit_account_no like  '03325%');
IF ISNULL(otherIncomesWrOff) THEN
SET otherIncomesWrOff=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '03312%' OR account_number LIKE '03316%' OR account_number LIKE '03309%' OR account_number LIKE '03318%' OR account_number LIKE '03304%' OR account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%'  OR account_number like  '03310%'  OR account_number like  '03313%' OR account_number like '03314%'  OR account_number like '03317%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET otherIncomesWrOff='0';
-- ELSE

-- SET otherIncomesWrOff=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;



/* 
CALL OtherIncomesWrittenOff('2019-06-24',@otherIncomesWrOff);

SELECT @otherIncomesWrOff; */





DROP PROCEDURE IF EXISTS ReceivablesCreated;

DELIMITER //

CREATE PROCEDURE ReceivablesCreated(IN theDate DATE,OUT receiavale VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO receiavale FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01131%';
IF ISNULL(receiavale) THEN
SET receiavale=0.0;
END IF;


-- OUTER_BLOCK: BEGIN
-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01131%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;
-- /* 
-- SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET receiavale='0';
-- ELSE

-- SET receiavale=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL ReceivablesCreated('2019-06-24',@receiavale);

SELECT @receiavale; */




DROP PROCEDURE IF EXISTS MobileMoneyReceivableCreated;

DELIMITER //

CREATE PROCEDURE MobileMoneyReceivableCreated(IN theDate DATE,OUT mobileMoneyc VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO mobileMoneyc FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01121%';
IF ISNULL(mobileMoneyc) THEN
SET mobileMoneyc=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01121%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET mobileMoneyc='0';
-- ELSE

-- SET mobileMoneyc=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL MobileMoneyReceivableCreated('2019-06-24',@mobileMoneyc);

SELECT @mobileMoneyc; */







DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsCreated;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsCreated(IN theDate DATE,OUT otherRecePreMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO otherRecePreMade FROM pmms.general_ledger WHERE  trn_date=theDate AND  (debit_account_no LIKE '01117%' OR debit_account_no LIKE '01118%' OR debit_account_no LIKE '01119%'
 OR debit_account_no LIKE '01132%'  OR debit_account_no LIKE '01133%'  OR debit_account_no LIKE '01134%'  OR debit_account_no LIKE '01135%'  OR debit_account_no LIKE '01120%');
IF ISNULL(otherRecePreMade) THEN
SET otherRecePreMade=0.0;
END IF;





-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '01117%' OR account_number LIKE '01118%' OR account_number LIKE '01119%'
--  OR account_number LIKE '01132%'  OR account_number LIKE '01133%'  OR account_number LIKE '01134%'  OR account_number LIKE '01135%'  OR account_number LIKE '01120%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF;
--  */


-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET otherRecePreMade='0';
-- ELSE

-- SET otherRecePreMade=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsCreated('2019-06-24',@otherRecePreMade);

SELECT @otherRecePreMade; */








DROP PROCEDURE IF EXISTS BankDepositsMade;

DELIMITER //

CREATE PROCEDURE BankDepositsMade(IN theDate DATE,OUT bankDepositMade VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO bankDepositMade FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '01122%';
IF ISNULL(bankDepositMade) THEN
SET bankDepositMade=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01122%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET bankDepositMade='0';
-- ELSE

-- SET bankDepositMade=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL BankDepositsMade('2019-06-24',@bankDepositMade);

SELECT @bankDepositMade; */





DROP PROCEDURE IF EXISTS LoanDisbursementsMade;

DELIMITER //

CREATE PROCEDURE LoanDisbursementsMade(IN theDate DATE,OUT loansDisbursed VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO loansDisbursed FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no ='01128000110';
IF ISNULL(loansDisbursed) THEN
SET loansDisbursed=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01128%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET loansDisbursed='0';
-- ELSE

-- SET loansDisbursed=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL LoanDisbursementsMade('2019-06-24',@loansDisbursed);

SELECT @loansDisbursed; */




DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsAquired;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsAquired(IN theDate DATE,OUT fixedAssetsAndInvestmentAquired VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO fixedAssetsAndInvestmentAquired FROM pmms.general_ledger WHERE  trn_date=theDate AND  (debit_account_no LIKE '01100%' OR debit_account_no LIKE '01101%'
OR debit_account_no LIKE '01102%' OR debit_account_no LIKE '01103%' OR debit_account_no LIKE '01104%' OR debit_account_no LIKE '01105%' OR debit_account_no LIKE '01106%'  OR debit_account_no LIKE '01108%' OR debit_account_no LIKE '01109%'
OR debit_account_no LIKE '01110%' OR debit_account_no LIKE '01111%'  OR debit_account_no LIKE '01112%'  OR debit_account_no LIKE '01136%');
IF ISNULL(fixedAssetsAndInvestmentAquired) THEN
SET fixedAssetsAndInvestmentAquired=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '01100%' OR account_number LIKE '01101%'
-- OR account_number LIKE '01102%' OR account_number LIKE '01103%' OR account_number LIKE '01104%' OR account_number LIKE '01105%' OR account_number LIKE '01106%'  OR account_number LIKE '01108%' OR account_number LIKE '01109%'
-- OR account_number LIKE '01110%' OR account_number LIKE '01111%'  OR account_number LIKE '01112%'  OR account_number LIKE '01136%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET fixedAssetsAndInvestmentAquired='0';
-- ELSE

-- SET fixedAssetsAndInvestmentAquired=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL fixedAssetsAndInvestmentsAquired('2019-06-24',@fixedAssetsAndInvestmentAquired);

SELECT @fixedAssetsAndInvestmentAquired; */




DROP PROCEDURE IF EXISTS PayablesRefunded;

DELIMITER //

CREATE PROCEDURE PayablesRefunded(IN theDate DATE,OUT RefundPayable VARCHAR(100)) READS SQL DATA 



BEGIN
SELECT  SUM(debit) INTO RefundPayable FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05500%';
IF ISNULL(RefundPayable) THEN
SET RefundPayable=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '05500%' ;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET RefundPayable='0';
-- ELSE

-- SET RefundPayable=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL PayablesRefunded('2022-06-24',@RefundPayable);

SELECT @RefundPayable; */






DROP PROCEDURE IF EXISTS PayablesOtherLiabilitiesAndProvisionsRefunded;

DELIMITER //

CREATE PROCEDURE PayablesOtherLiabilitiesAndProvisionsRefunded(IN theDate DATE,OUT RefundPayableOtherLiabilProvis VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO RefundPayableOtherLiabilProvis FROM pmms.general_ledger WHERE  trn_date=theDate AND   (debit_account_no like '05504%' 
 OR debit_account_no like '05501%' OR debit_account_no like '05503%' OR debit_account_no like '05505%' OR debit_account_no like '05506%' OR debit_account_no like '05507%'
 OR debit_account_no like '05522%' OR debit_account_no like '05525%' OR debit_account_no like '05527%' OR debit_account_no like '05526%' OR debit_account_no like '05528%'
 OR debit_account_no like '05523%' OR debit_account_no like '05523%' OR debit_account_no like '05508%' OR debit_account_no like '05510%' 
 OR debit_account_no like '05511%' OR debit_account_no like '05512%' OR debit_account_no like '05513%' OR debit_account_no like '05514%' OR debit_account_no like '05515%'  
 OR debit_account_no like '05516%'  OR debit_account_no like '05517%'  OR debit_account_no like '05518%'  OR debit_account_no like '05519%'  OR debit_account_no like '05520%'  OR debit_account_no like '05521%');
IF ISNULL(RefundPayableOtherLiabilProvis) THEN
SET RefundPayableOtherLiabilProvis=0.0;
END IF;


-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number like '05504%' 
--  OR account_number like '05501%' OR account_number like '05503%' OR account_number like '05505%' OR account_number like '05506%' OR account_number like '05507%'
--  OR account_number like '05522%' OR account_number like '05525%' OR account_number like '05527%' OR account_number like '05526%' OR account_number like '05528%'
--  OR account_number like '05523%' OR account_number like '05523%' OR account_number like '05508%' OR account_number like '05510%' 
--  OR account_number like '05511%' OR account_number like '05512%' OR account_number like '05513%' OR account_number like '05514%' OR account_number like '05515%'  
--  OR account_number like '05516%'  OR account_number like '05517%'  OR account_number like '05518%'  OR account_number like '05519%'  OR account_number like '05520%'  OR account_number like '05521%');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET RefundPayableOtherLiabilProvis='0';
-- ELSE

-- SET RefundPayableOtherLiabilProvis=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL PayablesOtherLiabilitiesAndProvisionsRefunded('2019-06-24',@RefundPayableOtherLiabilProvis);

SELECT @RefundPayableOtherLiabilProvis; */







DROP PROCEDURE IF EXISTS InsurancePayableCleared;

DELIMITER //

CREATE PROCEDURE InsurancePayableCleared(IN theDate DATE,OUT insurancePayCleared VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO insurancePayCleared FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '05524%';
IF ISNULL(insurancePayCleared) THEN
SET insurancePayCleared=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '05524%' ;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET insurancePayCleared='0';
-- ELSE

-- SET insurancePayCleared=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL InsurancePayableCleared('2019-06-24',@insurancePayCleared);

SELECT @insurancePayCleared; */






DROP PROCEDURE IF EXISTS DrawingsMade;

DELIMITER //

CREATE PROCEDURE DrawingsMade(IN theDate DATE,OUT DrawingM VARCHAR(100)) READS SQL DATA 


BEGIN
SELECT  SUM(debit) INTO DrawingM FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04408%';
IF ISNULL(DrawingM) THEN
SET DrawingM=0.0;
END IF;




-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04408%' ;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET DrawingM='0';
-- ELSE

-- SET DrawingM=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;


/* CALL DrawingsMade('2019-06-24',@DrawingM);

SELECT @DrawingM; */





DROP PROCEDURE IF EXISTS DecapitalisationsMade;

DELIMITER //

CREATE PROCEDURE DecapitalisationsMade(IN theDate DATE,OUT Decapitlise VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO Decapitlise FROM pmms.general_ledger WHERE  trn_date=theDate AND debit_account_no LIKE '04400%';
IF ISNULL(Decapitlise) THEN
SET Decapitlise=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04400%';
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET Decapitlise='0';
-- ELSE

-- SET Decapitlise=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;

/* 
CALL DecapitalisationsMade('2019-06-24',@Decapitlise);

SELECT @Decapitlise; */






DROP PROCEDURE IF EXISTS OtherEquitiesAndReservesRemoved;

DELIMITER //

CREATE PROCEDURE OtherEquitiesAndReservesRemoved(IN theDate DATE,OUT equitiesReservesRemoved VARCHAR(100)) READS SQL DATA 

BEGIN
SELECT  SUM(debit) INTO equitiesReservesRemoved FROM pmms.general_ledger WHERE  trn_date=theDate AND  ((debit_account_no>='044010000110' AND debit_account_no<='04430999999') AND NOT debit_account_no='04408000110');
IF ISNULL(equitiesReservesRemoved) THEN
SET equitiesReservesRemoved=0.0;
END IF;



-- OUTER_BLOCK: BEGIN

-- DECLARE processindFeesAccountRep VARCHAR(30); 
-- DECLARE outer_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE ((account_number>='044010000110' AND account_number<='04430999999') AND NOT account_number='04408000110');
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


-- OPEN cursor_forSelectingProcessingFeesAccounts; 
--  SET @cash=0;
--  SET @cashCredit=0;
-- SET @cashDebit=0;
 
-- ACCOUNTS_LOOP: LOOP 
-- FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;

-- IF processindFeesAccountRep IS NULL THEN
-- SET processindFeesAccountRep=0;
-- END IF;
--  IF outer_done=1 THEN
-- LEAVE ACCOUNTS_LOOP;
--  END IF;
 

-- INNER_BLOCK: BEGIN

-- DECLARE batchNumbersReps VARCHAR(30); 
-- DECLARE inner_done INTEGER DEFAULT 0; 
-- DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;


-- SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT trn_id FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'" AS CHAR CHARACTER SET utf8));
-- /* SELECT @qr; */
-- PREPARE stmt FROM @qr;
-- EXECUTE stmt;
-- DROP PREPARE stmt;

-- OPEN cursor_forSelectingBatchNumbers; 
 
-- BATCH_LOOP:LOOP

-- FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

-- IF batchNumbersReps IS NULL THEN
-- SET batchNumbersReps=0;
-- END IF;


-- /* SELECT batchNumbersReps; */
--  IF inner_done=1 THEN
-- LEAVE BATCH_LOOP;
--  END IF;
 
 
--  SET @quary=CONCAT(CAST("SELECT chq_number INTO @batch FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;

-- /* SELECT @batch; */

--  CALL cashAccountWasDebited(@batch,@cashDebited);
 

--   CALL cashAccountWasCredited(@batch,@cashCredited);
  
-- /*   SELECT @cashDebited,@amountCredit; */
 
--  IF @cashDebited IS NULL THEN
-- SET @cashDebited=0;
-- END IF;

 
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

 
-- IF @cashDebited>0 THEN

-- SET @quary=CONCAT(CAST("SELECT credit INTO @amountCredit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));

-- PREPARE stm FROM @quary;

-- EXECUTE stm;

-- DEALLOCATE PREPARE stm;



-- IF @amountCredit<>'-' OR @amountCredit<>0 THEN
-- SET @cashCredit=@cashCredit+@amountCredit;

-- END IF;

-- END IF;


-- IF @cashCredited>0 THEN


-- SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
-- PREPARE stm FROM @quary;
-- EXECUTE stm;
-- DEALLOCATE PREPARE stm;


-- /* SELECT batchNumbersReps;

-- SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




-- IF @amountCredit>0 THEN

-- SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

-- END IF; */



-- IF @amountDebit<>'-' OR @amountDebit<>0 THEN

-- SET @cashDebit=@cashDebit+@amountDebit;

-- END IF;




-- END IF;



-- END LOOP BATCH_LOOP; 

-- SET inner_done=0;

-- CLOSE cursor_forSelectingBatchNumbers; 

-- END INNER_BLOCK;


-- DROP TABLE account_view;
--  IF @amountCredit IS NULL THEN
-- SET @amountCredit=0;
-- END IF;

--  IF @amountDebit IS NULL THEN
-- SET @amountDebit=0;
-- END IF;

--  END LOOP ACCOUNTS_LOOP;
 
 
-- SET outer_done=0;
 
-- CLOSE cursor_forSelectingProcessingFeesAccounts;
-- /* SELECT @cashCredit,@cashDebit; */
-- SET @cash =@cashDebit;


--  IF @cash IS NULL THEN
-- SET equitiesReservesRemoved='0';
-- ELSE

-- SET equitiesReservesRemoved=@cash;


-- END IF;


-- END OUTER_BLOCK//
END //
DELIMITER ;
