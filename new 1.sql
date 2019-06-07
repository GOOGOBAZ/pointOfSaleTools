

DROP PROCEDURE IF EXISTS changeNextDueDate;

DELIMITER //

CREATE PROCEDURE changeNextDueDate() READS SQL DATA BEGIN

  DECLARE l_done INTEGER DEFAULT 0;
  
  DECLARE ledgerIds INTEGER DEFAULT 1;

 DECLARE selectTrnIds CURSOR FOR SELECT  master1_id FROM new_loan_appstoreamort  WHERE  NOT instalment_status='P';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
 DROP TABLE IF EXISTS TTbb;
 
 CREATE TEMPORARY  TABLE TTbb(temp_PostDate DATE);


 OPEN selectTrnIds;


LedgerIds_loop: LOOP 

 FETCH selectTrnIds into ledgerIds;

 IF l_done=1 THEN

LEAVE LedgerIds_loop;

 END IF;
 

SELECT instalment_due_date INTO @deueDate from new_loan_appstoreamort WHERE instalment_no=ledgerIds;

SELECT  @deueDate;

INSERT INTO TTbb VALUES(@deueDate);



SET l_done=0;

 END LOOP LedgerIds_loop;



 CLOSE selectTrnIds;


SELECT * FROM TTbb;

END //

DELIMITER ;

CALL changeNextDueDate();