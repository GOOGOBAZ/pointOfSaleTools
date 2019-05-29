


DROP TABLE IF EXISTS `DailyCollection`;

CREATE TABLE `DailyCollection` (
  `CollectionId` int(11) NOT NULL AUTO_INCREMENT,
   `CollectionDate` date NOT NULL DEFAULT '1970-01-01',
  `accountName` varchar(100) DEFAULT '0',
  `loanID` varchar(100) DEFAULT '0',
  
   `ExpectedPrincipalArrears` varchar(100) DEFAULT '0',
    `ExpectedPrincipalToday` varchar(100) DEFAULT '0',
	`ExpectedTotalPrincimpalToday` varchar(100) DEFAULT '0',
	`PrincimpalCollectedToday` varchar(100) DEFAULT '0',
  
      `ExpectedInterestArrears` varchar(100) DEFAULT '0',
  `ExpectedInterestToday` varchar(100) DEFAULT '0',
`ExpectedTotalInterestToday` varchar(100) DEFAULT '0',
`InterestCollectedToday` varchar(100) DEFAULT '0',

   `ExpectedAccumInterestArrears` varchar(100) DEFAULT '0',
	 `ExpectedAccumInterestToday` varchar(100) DEFAULT '0',
`ExpectedTotalIAccumnterestToday` varchar(100) DEFAULT '0',
`AccumInterestCollectedToday` varchar(100) DEFAULT '0',
	
	`ExpectedPenaltyArrears` varchar(100) DEFAULT '0',
	 `ExpectedPenaltyToday` varchar(100) DEFAULT '0',
`ExpectedTotalIPenaltyToday` varchar(100) DEFAULT '0',
`PenaltyCollectedToday` varchar(100) DEFAULT '0',
	
	
	
  `ExpectedAmountInArrears` varchar(100) DEFAULT '0',  
  `ExpectedAmountToday` varchar(100) DEFAULT '0',
  `ExpectedTotalAmountToday` varchar(100) DEFAULT '0',
  `AmountCollectedToday` varchar(100) DEFAULT '0',
  
   `NumberOfInstalmentsInArrears` varchar(100) DEFAULT '0',
  `VarianceCollection` varchar(100) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`CollectionId`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;




DROP PROCEDURE IF EXISTS prepareDailyReport;

DELIMITER //

CREATE PROCEDURE prepareDailyReport() READS SQL DATA BEGIN

  DECLARE l_done INTEGER DEFAULT 0;
  
  DECLARE ledgerIds VARCHAR(30);

 DECLARE selectTrnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

 OPEN selectTrnIds;

SET @N=0;
LedgerIds_loop: LOOP 

SELECT ledgerIds;

 FETCH selectTrnIds into ledgerIds;

 IF l_done=1 THEN

LEAVE LedgerIds_loop;

 END IF;
 
/*  SELECT ledgerIds;
 */
SELECT applicant_account_name,loan_id INTO @borrower,@loanId from new_loan_appstore where trn_id=ledgerIds;

/* SELECT @borrower,@loanId; */

SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining),COUNT(trn_id) INTO @princimpaArrears,@interestArrears,@accumInterestArrears,@penaltyArrears,@instalmentArrears,@instalmentsArreas
 from new_loan_appstoreamort WHERE instalment_due_date<CURDATE() AND NOT instalment_status='P' AND master1_id=ledgerIds;
 

 IF @interestArrears IS NULL THEN
SET @interestArrears=0.0;
 END IF;
 
 IF @princimpaArrears IS NULL THEN
SET @princimpaArrears=0.0;
 END IF;
 
 IF @accumInterestArrears IS NULL THEN
SET @accumInterestArrears=0.0;
 END IF;
 
 
 IF @penaltyArrears IS NULL THEN
SET @penaltyArrears=0.0;
 END IF;
 
 
 IF @instalmentArrears IS NULL THEN
SET @instalmentArrears=0.0;
 END IF;
 
 IF @instalmentsArreas IS NULL THEN
SET @instalmentsArreas=0.0;
 END IF;
 
/* SELECT  @interestArrears,@princimpaArrears,@instalmentArrears,@instalmentsArreas; */

SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) INTO @princimpal,@interest,@accumInterest,@loanPenalty,@instalmentAmount from new_loan_appstoreamort WHERE instalment_due_date=CURDATE() AND NOT instalment_status='P' AND master1_id=ledgerIds;


 IF @interest IS NULL THEN
SET @interest=0.0;
 END IF;
 
  IF @princimpal IS NULL THEN
SET @princimpal=0.0;
 END IF;
 
 IF @accumInterest IS NULL THEN
SET @accumInterest=0.0;
 END IF;
 
 IF @loanPenalty IS NULL THEN
SET @loanPenalty=0.0;
 END IF;
 
 
   IF @instalmentAmount IS NULL THEN
SET @instalmentAmount=0.0;
 END IF;
/* SELECT  @interest,@princimpal,@instalmentAmount; */

 SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) INTO @totalprincimpal,@totalinterest,@totalAccuminterest,@totalPenalty,@totalInstalmnets
 from new_loan_appstoreamort WHERE instalment_due_date<=CURDATE() AND NOT instalment_status='P' AND master1_id=ledgerIds;

 IF @totalprincimpal IS NULL THEN
SET @totalprincimpal=0.0;
 END IF;
  IF @totalinterest IS NULL THEN
SET @totalinterest=0.0;
 END IF;
 
   IF @totalAccuminterest IS NULL THEN
SET @totalAccuminterest=0.0;
 END IF;


 IF @totalPenalty IS NULL THEN
SET @totalPenalty=0.0;
 END IF;
 
  IF @totalInstalmnets IS NULL THEN
SET @totalInstalmnets=0.0;
 END IF;
/* SELECT  @totalinterestArrears,@totalprincimpaArrears,@totalinstalmentArrears; */
    

INSERT INTO DailyCollection VALUES(null,CURDATE(),@borrower,@loanId,@princimpaArrears,@princimpal,@totalprincimpal,'0.0',@interestArrears,@interest,@totalinterest,'0.0',@accumInterestArrears,@accumInterest,@totalAccuminterest,'0.0',
@penaltyArrears,@loanPenalty,@totalPenalty,'0.0',@instalmentArrears,@instalmentAmount,@totalInstalmnets,'0.0',@instalmentsArreas,'0','NA','NA','NA');

SET @interestArrears=null,@princimpaArrears=null,@accumInterestArrears=null,@penaltyArrears=null,@instalmentArrears=null,@instalmentsArreas=null;

SET  @interest=null,@princimpal=null,@accumInterest=null,@loanPenalty=null,@instalmentAmount=null;

SET  @totalprincimpal=null,@totalinterest=null,@totalAccuminterest=null,@totalPenalty=null,@totalInstalmnets=null;

SET @N=@N+1;
SELECT @N;

SET l_done=0;

 END LOOP LedgerIds_loop;



 CLOSE selectTrnIds;
 
 
 
INSERT INTO DailyCollection VALUES(null,CURDATE(),'Direct Cash Transactions','newloan01123000110','0.0','0,0','0,0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0',
'0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0','NA','NA','NA');

 
INSERT INTO DailyCollection VALUES(null,CURDATE(),'Direct Bank Transactions','newloan01122000110','0.0','0,0','0,0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0',
'0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0','NA','NA','NA');


END //

DELIMITER ;

 CALL prepareDailyReport(); 

/* SELECT CollectionId, accountName,loanID FROM DailyCollection; */


DROP PROCEDURE IF EXISTS updatdateNormalDailyInterest;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyInterest(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT InterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected+interest;

SET @instalmentAmountCollected=@instalmentAmountCollected+interest;

UPDATE DailyCollection SET InterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyInterestWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyInterestWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT InterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;

IF @interestCollected>0 THEN 
UPDATE DailyCollection SET InterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;
END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyAccumInterest;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyAccumInterest(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT AccumInterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected+interest;

SET @instalmentAmountCollected=@instalmentAmountCollected+interest;

UPDATE DailyCollection SET AccumInterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;



DROP PROCEDURE IF EXISTS updatdateNormalDailyAccumInterestWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyAccumInterestWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT AccumInterestCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;


IF @interestCollected>0 THEN 
UPDATE DailyCollection SET AccumInterestCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;
END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;



DROP PROCEDURE IF EXISTS updatdateNormalDailyPenalty;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPenalty(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT PenaltyCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected+interest;

SET @instalmentAmountCollected=@instalmentAmountCollected+interest;

UPDATE DailyCollection SET PenaltyCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyPenaltyWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPenaltyWriteOff(IN  loanIdCC VARCHAR(100),IN  interest VARCHAR(100)) READS SQL DATA BEGIN


SELECT PenaltyCollectedToday,AmountCollectedToday,CollectionId INTO @interestCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @interestCollected IS NULL THEN
SET @interestCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;



SET @interestCollected=@interestCollected-interest;

SET @instalmentAmountCollected=@instalmentAmountCollected-interest;

IF @interestCollected>0 THEN 

UPDATE DailyCollection SET PenaltyCollectedToday=@interestCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

END IF;

SET @interestCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyPrincipal;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPrincipal(IN loanIdCC VARCHAR(100) ,IN  principal VARCHAR(100)) READS SQL DATA BEGIN

SELECT PrincimpalCollectedToday,AmountCollectedToday,CollectionId INTO @principalCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @principalCollected IS NULL THEN
SET @principalCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;

SET @principalCollected=@principalCollected+principal;

SET @instalmentAmountCollected=@instalmentAmountCollected+principal;

UPDATE DailyCollection SET PrincimpalCollectedToday=@principalCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

SET @principalCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updatdateNormalDailyPrincipalWriteOff;

DELIMITER //

CREATE PROCEDURE updatdateNormalDailyPrincipalWriteOff(IN loanIdCC VARCHAR(100) ,IN  principal VARCHAR(100)) READS SQL DATA BEGIN

SELECT PrincimpalCollectedToday,AmountCollectedToday,CollectionId INTO @principalCollected,@instalmentAmountCollected,@Id from DailyCollection WHERE CollectionDate=CURDATE() AND  loanID=loanIdCC ORDER BY CollectionId DESC Limit 1;


IF @principalCollected IS NULL THEN
SET @principalCollected=0.0;
END IF;

IF @instalmentAmountCollected IS NULL THEN
SET @instalmentAmountCollected=0.0;
END IF;

SET @principalCollected=@principalCollected-principal;

SET @instalmentAmountCollected=@instalmentAmountCollected-principal;

IF @principalCollected>0 THEN 

UPDATE DailyCollection SET PrincimpalCollectedToday=@principalCollected,AmountCollectedToday=@instalmentAmountCollected WHERE CollectionId=@Id;

END IF;

SET @principalCollected=NULL;
SET @instalmentAmountCollected=NULL;
SET @Id=NULL;


END //

DELIMITER ;



DROP PROCEDURE IF EXISTS CollectionOnDisbursement;

DELIMITER //

CREATE PROCEDURE CollectionOnDisbursement(IN loanIdCC VARCHAR(100)) READS SQL DATA BEGIN

 
 
/*  SELECT ledgerIds;
 */
SELECT applicant_account_name INTO @borrower  from new_loan_appstore where loan_id=loanIdCC;

/* SELECT @borrower,@loanId; */

SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining),COUNT(trn_id) INTO @princimpaArrears,@interestArrears,@accumInterestArrears,@penaltyArrears,@instalmentArrears,@instalmentsArreas
 from new_loan_appstoreamort WHERE instalment_due_date<CURDATE() AND NOT instalment_status='P' AND master2_id=loanIdCC;
 

 IF @interestArrears IS NULL THEN
SET @interestArrears=0.0;
 END IF;
 
 IF @princimpaArrears IS NULL THEN
SET @princimpaArrears=0.0;
 END IF;
 
 IF @accumInterestArrears IS NULL THEN
SET @accumInterestArrears=0.0;
 END IF;
 
 
 IF @penaltyArrears IS NULL THEN
SET @penaltyArrears=0.0;
 END IF;
 
 
 IF @instalmentArrears IS NULL THEN
SET @instalmentArrears=0.0;
 END IF;
 
 IF @instalmentsArreas IS NULL THEN
SET @instalmentsArreas=0.0;
 END IF;
 
/* SELECT  @interestArrears,@princimpaArrears,@instalmentArrears,@instalmentsArreas; */

SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) INTO @princimpal,@interest,@accumInterest,@loanPenalty,@instalmentAmount from new_loan_appstoreamort WHERE instalment_due_date=CURDATE() AND NOT instalment_status='P' AND master2_id=loanIdCC;


 IF @interest IS NULL THEN
SET @interest=0.0;
 END IF;
 
  IF @princimpal IS NULL THEN
SET @princimpal=0.0;
 END IF;
 
 IF @accumInterest IS NULL THEN
SET @accumInterest=0.0;
 END IF;
 
 IF @loanPenalty IS NULL THEN
SET @loanPenalty=0.0;
 END IF;
 
 
   IF @instalmentAmount IS NULL THEN
SET @instalmentAmount=0.0;
 END IF;
/* SELECT  @interest,@princimpal,@instalmentAmount; */

 SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) INTO @totalprincimpal,@totalinterest,@totalAccuminterest,@totalPenalty,@totalInstalmnets
 from new_loan_appstoreamort WHERE instalment_due_date<=CURDATE() AND NOT instalment_status='P' AND master2_id=loanIdCC;

 IF @totalprincimpal IS NULL THEN
SET @totalprincimpal=0.0;
 END IF;
  IF @totalinterest IS NULL THEN
SET @totalinterest=0.0;
 END IF;
 
   IF @totalAccuminterest IS NULL THEN
SET @totalAccuminterest=0.0;
 END IF;


 IF @totalPenalty IS NULL THEN
SET @totalPenalty=0.0;
 END IF;
 
  IF @totalInstalmnets IS NULL THEN
SET @totalInstalmnets=0.0;
 END IF;
/* SELECT  @totalinterestArrears,@totalprincimpaArrears,@totalinstalmentArrears; */
    

INSERT INTO DailyCollection VALUES(null,CURDATE(),@borrower,loanIdCC,@princimpaArrears,@princimpal,@totalprincimpal,'0.0',@interestArrears,@interest,@totalinterest,'0.0',@accumInterestArrears,@accumInterest,@totalAccuminterest,'0.0',
@penaltyArrears,@loanPenalty,@totalPenalty,'0.0',@instalmentArrears,@instalmentAmount,@totalInstalmnets,'0.0',@instalmentsArreas,'0','NA','NA','NA');

SET @interestArrears=null,@princimpaArrears=null,@accumInterestArrears=null,@penaltyArrears=null,@instalmentArrears=null,@instalmentsArreas=null;

SET  @interest=null,@princimpal=null,@accumInterest=null,@loanPenalty=null,@instalmentAmount=null;

SET  @totalprincimpal=null,@totalinterest=null,@totalAccuminterest=null,@totalPenalty=null,@totalInstalmnets=null;



END //

DELIMITER ;

/*  CALL prepareDailyReport();
 */

DROP TABLE IF EXISTS `sequenceNumbers`;

CREATE TABLE `sequenceNumbers` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupNumber` int(11) DEFAULT 10000,
  `trnSequencyNumber` int(11) DEFAULT 20000,
  `batchNumber` int(11) DEFAULT 30000,
  `budgetEstimateNumber` int(11) DEFAULT 40000,
  `otherNumbers1` int(11) DEFAULT 50000,
  `otherNumbers2` int(11) DEFAULT 60000,
  `otherNumbers3` int(11) DEFAULT 70000,
  `otherNumbers4` int(11) DEFAULT 80000,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO sequenceNumbers VALUES(null,10000,20000,30000,40000,50000,60000,70000,80000);


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
 


SELECT id,temp_Borrower,temp_outStandingPrici,temp_OutStandingInterst,temp_OutStandingAccum,temp_OutStandingPenalty,temp_OutStandingTotal  FROM temp_grossPortFolio;


END //

DELIMITER ;

CALL grossLoanPortfolio();


