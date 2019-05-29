
DROP PROCEDURE IF EXISTS dailyCollectionInstalmentStatement;

DELIMITER //



CREATE PROCEDURE dailyCollectionInstalmentStatement(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
/*  SELECT accountName,startDate,endDate; */
 


/*  
IF @runningBalance IS NULL THEN */
/* SET @runningBalance=0.0; */
/* END IF; */

/* SELECT @runningBalance; */

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));






 OPEN forSelectingTxnIds;



accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedAmountToday,AmountCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
 SET Ids=Ids+1;
   
   SET @Varince=@actualToday-@ExpectedAm;
   
   IF @Varince>=0.0 THEN
   
   SET @Vstatus='+Ve';
   
   SET @Varince=0.0;
   
   END IF;
   
   IF @Varince<0.0 THEN
   
   SET @Vstatus='-Ve';
   
   SET @Varince=@Varince*-1;
 
   END IF;
   
   

 INSERT INTO temp_dailycollection VALUES(Ids,@narration,@ExpectedAm,@actualToday,@Varince,@Vstatus);


 



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;
 


SELECT id ,temp_NarrationC,temp_ExpectedCollection ,temp_ActualCollection,temp_BalColl,temp_Variance  FROM temp_dailycollection;


END //

DELIMITER ;

CALL dailyCollectionInstalmentStatement('2019-05-23');


DROP PROCEDURE IF EXISTS groupNumber;

DELIMITER //

CREATE PROCEDURE groupNumber() READS SQL DATA BEGIN
 
 
 SELECT  groupNumber  INTO @theGroupNumber FROM sequenceNumbers;
 
 SET @theGroupNumber=@theGroupNumber+1;

 UPDATE sequenceNumbers SET groupNumber=@theGroupNumber;

SELECT @theGroupNumber ;

END //

DELIMITER ;

CALL groupNumber();



DROP PROCEDURE IF EXISTS batchNumber;

DELIMITER //

CREATE PROCEDURE batchNumber() READS SQL DATA BEGIN
 
 
 SELECT   batchNumber  INTO @theGroupNumber FROM sequenceNumbers;
 
 SET @theGroupNumber=@theGroupNumber+1;

 UPDATE sequenceNumbers SET  batchNumber=@theGroupNumber;

SELECT @theGroupNumber ;

END //

DELIMITER ;

CALL batchNumber();



DROP PROCEDURE IF EXISTS grossLoanPortfolio;

DELIMITER //



CREATE PROCEDURE grossLoanPortfolio() READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
/*  SELECT accountName,startDate,endDate; */
 


/*  
IF @runningBalance IS NULL THEN */
/* SET @runningBalance=0.0; */
/* END IF; */

/* SELECT @runningBalance; */

DROP TABLE IF EXISTS temp_grossPortFolio;

CREATE  TABLE temp_grossPortFolio(id INTEGER,temp_Borrower VARCHAR(200),temp_outStandingPrici DOUBLE,temp_OutStandingInterst DOUBLE,temp_OutStandingAccum DOUBLE,temp_OutStandingPenalty VARCHAR(200),temp_OutStandingTotal VARCHAR(200));






 OPEN forSelectingTxnIds;



accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  applicant_account_name, TotalPrincipalRemaining,TotalInterestRemaining,TotalAccumulatedInterestRemaining,TotalLoanPenaltyRemaining  INTO @borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty FROM new_loan_appstore WHERE trn_id=txnIdS;
 
 SET Ids=Ids+1;
   
   SET @totalAll=@remPrinc+@remInt+@remAccumI+@remaPenalty;
   
  
   
   

 INSERT INTO temp_grossPortFolio VALUES(Ids,@borrower,@remPrinc,@remInt,@remAccumI,@remaPenalty,@totalAll);


 



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;
 


SELECT id ,temp_NarrationC,temp_ExpectedCollection ,temp_ActualCollection,temp_BalColl,temp_Variance  FROM temp_dailycollection;


END //

DELIMITER ;

CALL dailyCollectionInstalmentStatement('2019-05-23');
