

DROP PROCEDURE IF EXISTS LedgerFees;
DELIMITER //
CREATE PROCEDURE LedgerFees(IN theDate DATE,OUT ledgerFessCol VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM account_created_store WHERE account_number LIKE '03304%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
ACCOUNTS_LOOP: LOOP 
FETCH cursor_forSelectingProcessingFeesAccounts into processindFeesAccountRep;


 IF outer_done=1 THEN
LEAVE ACCOUNTS_LOOP;
 END IF;
 





INNER_BLOCK: BEGIN

DECLARE batchNumbersReps VARCHAR(30); 
DECLARE inner_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingBatchNumbers CURSOR FOR SELECT * FROM account_view;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;


SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

IF @amount<>'-' OR @amount<>0 THEN
SET @cash=@cash+@amount;

END IF;

END IF;




END LOOP BATCH_LOOP; 

SET inner_done=0;

CLOSE cursor_forSelectingBatchNumbers; 

END INNER_BLOCK;


DROP TABLE account_view;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
SET ledgerFessCol=@cash;
END OUTER_BLOCK//

DELIMITER ;



CALL LedgerFees('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol;
 WriteOffProcessingFees
                WriteOffProcessingFeesBank
 List loanPytDetails1=new ArrayList();    
        loanPytDetails1.add(counter);
        loanPytDetails1.add("LProcessFees");    
         loanPytDetails1.add("Fees");
         loanPytDetails1.add(fmt.formatAccountWithSeperators(creditAccountField72.getValue().toString()));
         loanPytDetails1.add("03315000110");
         loanPytDetails1.add("Loan processing fees from"+" "+dbq.AccountName(fmt.formatAccountWithSeperators(creditAccountField72.getValue().toString())));
            loanPytDetails1.add(fmt.formatForStatementNumbers(amountCredit2.getValue().toString())); 
               loanPytDetails1.add("0"); 
              loanPytDetails1.add("0");
               loanPytDetails1.add("0");
                  if(!jTextField23.getText().equalsIgnoreCase("")){
              
              fios.stri
			  
			  
			  
			  
			  
			  
			  
			  jButton105.setVisible(true);
			  jButton102.setVisible(true);
			  jButton103.setVisible(true);
			  jButton106.setVisible(true);
			  jButton104.setVisible(true);
			  
			  
			  
			  
			  
			  
			  