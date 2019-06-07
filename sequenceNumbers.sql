
DROP TABLE IF EXISTS `sequenceNumbers`;

CREATE TABLE `sequenceNumbers` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupNumber` int(11) DEFAULT 10000,
  `trnSequencyNumber` int(11) DEFAULT 1,
  `batchNumber` int(11) DEFAULT 30000,
  `budgetEstimateNumber` int(11) DEFAULT 40000,
  `otherNumbers1` int(11) DEFAULT 50000,
  `otherNumbers2` int(11) DEFAULT 60000,
  `otherNumbers3` int(11) DEFAULT 70000,
  `otherNumbers4` int(11) DEFAULT 80000,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO sequenceNumbers VALUES(null,10000,1,30000,40000,50000,60000,70000,80000);


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




DROP PROCEDURE IF EXISTS TheTrnSequencyNumber;

DELIMITER //

CREATE PROCEDURE TheTrnSequencyNumber() READS SQL DATA BEGIN
 
 
 SELECT   trnSequencyNumber  INTO @theTxnSqueNumber FROM sequenceNumbers;
 
 SET @theTxnSqueNumber=@theTxnSqueNumber+1;
 
 IF @theTxnSqueNumber<10 THEN
   SET   @act= concat(CAST("000" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
   
   ELSEIF @theTxnSqueNumber>=10 AND @theTxnSqueNumber<100 THEN
   
    SET   @act= concat(CAST("00" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
     
     ELSEIF @theTxnSqueNumber>=100 AND @theTxnSqueNumber<1000 THEN
	 
	  SET   @act= concat(CAST("0" AS CHAR CHARACTER SET utf8),@theTxnSqueNumber);
     
     ELSEIF @theTxnSqueNumber>=1000 THEN
	 
       SET   @act= @theTxnSqueNumber;
	   
   END IF;
 


 UPDATE sequenceNumbers SET  trnSequencyNumber=@act;

SELECT @act ;

END //

DELIMITER ;

CALL TheTrnSequencyNumber();




DROP PROCEDURE IF EXISTS resetTxnSeqNumber;

DELIMITER //

CREATE PROCEDURE resetTxnSeqNumber() READS SQL DATA BEGIN
 

 UPDATE sequenceNumbers SET  trnSequencyNumber=1;

END //

DELIMITER ;
CALL TheTrnSequencyNumber();
CALL resetTxnSeqNumber();
CALL TheTrnSequencyNumber();