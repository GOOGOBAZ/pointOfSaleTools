


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


SELECT applicant_account_name INTO @borrower  from new_loan_appstore where loan_id=loanIdCC;


SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining),COUNT(trn_id) 
INTO @princimpaArrears,@interestArrears,@accumInterestArrears,@penaltyArrears,@instalmentArrears,@instalmentsArreas
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
 
SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) INTO 

@princimpal,@interest,@accumInterest,@loanPenalty,@instalmentAmount from new_loan_appstoreamort 
WHERE instalment_due_date=CURDATE() AND NOT instalment_status='P' AND master2_id=loanIdCC;


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

 SELECT SUM(PrincipalRemaining), SUM(InterestRemaing),SUM(AccumulatedInterestRemaining),SUM(LoanPenaltyRemaining),SUM(InstalmentRemaining) 
 INTO @totalprincimpal,@totalinterest,@totalAccuminterest,@totalPenalty,@totalInstalmnets
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


INSERT INTO DailyCollection VALUES(null,CURDATE(),@borrower,loanIdCC,@princimpaArrears,@princimpal,@totalprincimpal,'0.0',@interestArrears,@interest,@totalinterest,'0.0',@accumInterestArrears,@accumInterest,@totalAccuminterest,'0.0',
@penaltyArrears,@loanPenalty,@totalPenalty,'0.0',@instalmentArrears,@instalmentAmount,@totalInstalmnets,'0.0',@instalmentsArreas,'0','NA','NA','NA');

SET @interestArrears=null,@princimpaArrears=null,@accumInterestArrears=null,@penaltyArrears=null,@instalmentArrears=null,@instalmentsArreas=null;

SET  @interest=null,@princimpal=null,@accumInterest=null,@loanPenalty=null,@instalmentAmount=null;

SET  @totalprincimpal=null,@totalinterest=null,@totalAccuminterest=null,@totalPenalty=null,@totalInstalmnets=null;



END //

DELIMITER ;

/*  CALL prepareDailyReport();
 */



DROP PROCEDURE IF EXISTS dailyCollectionInstalmentStatement;

DELIMITER //



CREATE PROCEDURE dailyCollectionInstalmentStatement(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


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



DROP PROCEDURE IF EXISTS grossLoanPortfolio;

DELIMITER //



CREATE PROCEDURE grossLoanPortfolio() READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE loan_cycle_status='Disbursed';
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 


DROP TABLE IF EXISTS temp_grossPortFolio;

CREATE TEMPORARY  TABLE temp_grossPortFolio(id INTEGER,temp_Borrower VARCHAR(200),temp_outStandingPrici DOUBLE,temp_OutStandingInterst DOUBLE,temp_OutStandingAccum DOUBLE,temp_OutStandingPenalty VARCHAR(200),temp_OutStandingTotal VARCHAR(200));


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


DROP PROCEDURE IF EXISTS dailyCollectionInstalmentStatementArrears;

DELIMITER //


CREATE PROCEDURE dailyCollectionInstalmentStatementArrears(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedTotalAmountToday,AmountCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
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

CALL dailyCollectionInstalmentStatementArrears('2019-05-23');




DROP PROCEDURE IF EXISTS dailyCollectionPrincipal;

DELIMITER //


CREATE PROCEDURE dailyCollectionPrincipal(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedPrincipalToday,PrincimpalCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
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

CALL dailyCollectionPrincipal('2019-05-23');



DROP PROCEDURE IF EXISTS dailyCollectionPrincipalArrears;

DELIMITER //


CREATE PROCEDURE dailyCollectionPrincipalArrears(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedTotalPrincimpalToday,PrincimpalCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
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

CALL dailyCollectionPrincipalArrears('2019-06-05');





DROP PROCEDURE IF EXISTS dailyCollectionInterest;

DELIMITER //


CREATE PROCEDURE dailyCollectionInterest(IN theDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT CollectionId  FROM dailycollection WHERE CollectionDate=theDate;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 

DROP TABLE IF EXISTS temp_dailycollection;

CREATE  TEMPORARY TABLE temp_dailycollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection DOUBLE,temp_ActualCollection DOUBLE,temp_BalColl DOUBLE,temp_Variance VARCHAR(200));


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT  accountName, ExpectedInterestToday,InterestCollectedToday  INTO @narration,@ExpectedAm,@actualToday FROM dailycollection WHERE CollectionId=txnIdS;
 
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

CALL dailyCollectionPrincipalArrears('2019-06-05');



/*  */

DROP PROCEDURE IF EXISTS grandSummryDailyReport;

DELIMITER //

/* The grand summury daily report will generate a report of total loan collections and general cash items by collecting data from different tables */
/* The expected summuries should include both loan and cash collection summuries */
CREATE PROCEDURE grandSummryDailyReport(IN theDate DATE) READS SQL DATA BEGIN

SELECT SUM(`ExpectedPrincipalArrears`),SUM(`ExpectedPrincipalToday`),SUM(`ExpectedTotalPrincimpalToday`),SUM(`PrincimpalCollectedToday`),
SUM(`ExpectedInterestArrears`),SUM(`ExpectedInterestToday`),SUM(`ExpectedTotalInterestToday`),(`InterestCollectedToday`),SUM(`ExpectedAccumInterestArrears`),
SUM(`ExpectedAccumInterestToday`),SUM(`ExpectedTotalIAccumnterestToday`),SUM(`AccumInterestCollectedToday`),SUM(`ExpectedPenaltyArrears`),SUM(`ExpectedPenaltyToday`) ,
SUM(`ExpectedTotalIPenaltyToday`),SUM(`PenaltyCollectedToday`),SUM(`ExpectedAmountInArrears`),SUM(`ExpectedAmountToday`),SUM(`ExpectedTotalAmountToday`) ,
SUM(`AmountCollectedToday`) INTO

 @ExpectedPrincinpaWithArrears, /*This is the  princimpal amount expected for only instalments in arrears*/
@ExpectedPricimpalToday,/* This is the princimpal amount excluding those payments in arrears*/
@ExpectedTotalPricimpalToday,/* This is the princimpal amount without arrears plus the princimpal amount with arrears expected today*/
@ActualPrincimpalCollectedToday, /* This is total princimpal amount actually collected today*/

 @ExpectedInterestWithArrears, /*This is the  interest amount expected for only instalments in arrears*/
@ExpectedInterstToday,/* This is the interest amount excluding those payments in arrears*/
@ExpectedTotalInterestToday,/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
@ActualInterestCollectedToday, /* This is total interest amount actually collected today*/

 @ExpectedAccumInterestWithArrears, /*This is the  interest amount expected for only instalments in arrears*/
@ExpectedAccumInterstToday,/* This is the interest amount excluding those payments in arrears*/
@ExpectedTotalAccumInterestToday,/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
@ActualAccumInterestCollectedToday, /* This is total interest amount actually collected today*/

 @ExpectedPenaltyWithArrears, /*This is the  interest amount expected for only instalments in arrears*/
@ExpectedPenaltyToday,/* This is the interest amount excluding those payments in arrears*/
@ExpectedTotalPenaltyToday,/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
@ActualPenaltyCollectedToday, /* This is total interest amount actually collected today*/

 @ExpectedTotalAmountWithArrears, /*This is the  total expected amount including princimpal and interest expected but for only payments in arrears*/
@ExpectedTotalAmountToday,/* This is the total amount expected excludint the instalments in arrears*/
@ExpectedOverallTotalAmountToday,/* This is the total amount expected overall including interest and princimpa in arrears*/
@ActualTotalAmountCollectedToday /* This is total amount actually collected today*/

FROM dailycollection WHERE CollectionDate=theDate;


DROP TABLE IF EXISTS temp_dailySummuryCollection;

CREATE  TEMPORARY TABLE temp_dailySummuryCollection(id INTEGER,temp_NarrationC VARCHAR(200),temp_ExpectedCollection VARCHAR(200),temp_ActualCollection VARCHAR(200),temp_BalColl VARCHAR(200),temp_Variance VARCHAR(200));

INSERT INTO temp_dailySummuryCollection VALUES(1,"LOAN SUMMURIES","","","","");
INSERT INTO temp_dailySummuryCollection VALUES(1,"",0,0,0,0);

/*Total loan amount*/

   
   SET @VarinceOT=@ActualTotalAmountCollectedToday-@ExpectedOverallTotalAmountToday;
   
   IF @VarinceOT>=0.0 THEN
   
   SET @Vstatus1='+Ve';
   
   SET @VarinceOT=0.0;
   
   END IF;
   
   IF @VarinceOT<0.0 THEN
   
   SET @Vstatus1='-Ve';
   
   SET @VarinceOT=@Varince*-1;
 
   END IF;

INSERT INTO temp_dailySummuryCollection VALUES(null,"Total Loan Amount",@ExpectedOverallTotalAmountToday,@ActualTotalAmountCollectedToday,@VarinceOT,@Vstatus1);
  
   SET @Varince2=@ExpectedOverallTotalAmountToday-@ExpectedTotalAmountWithArrears;
   
   IF @Varince2>=0.0 THEN
   
   SET @Vstatus2='+Ve';
   
   SET @Varince2=0.0;
   
   END IF;
   
   IF @Varince2<0.0 THEN
   
   SET @Vstatus2='-Ve';
   
   SET @Varince2=@Varince2*-1;
 
   END IF;

INSERT INTO temp_dailySummuryCollection VALUES(null,"Loan Amounts In Arreas Only",@ExpectedTotalAmountWithArrears,@ActualTotalAmountCollectedToday,@Varince2,@Vstatus2);

   SET @VarinceArrears=@ExpectedOverallTotalAmountToday-@ExpectedTotalAmountWithArrears;
   
   IF @VarinceArrears>=0.0 THEN
   
   SET @Vstatus2='+Ve';
   
   SET @VarinceArrears=0.0;
   
   END IF;
   
   IF @Varince3<0.0 THEN
   
   SET @Vstatus3='-Ve';
   
   SET @Varince3=@Varince3*-1;
 
   END IF;

INSERT INTO temp_dailySummuryCollection VALUES(null,"Loan Amount Due Today Only",@ExpectedTotalAmountToday,@ActualTotalAmountCollectedToday,@Varince3,@Vstatus3);


CALL OpeningCashBalance(theDate,@OpeningCahdBala);

CALL ProcessingFeesCollected(theDate,@processingFees);

CALL LedgerFees(theDate,@ledgerFessCol);
CALL BadDebtsRecovered(theDate,@badDebtsRecovered);

CALL MembershipFees(theDate,@memberShipFessCol);

CALL annualSubFees(theDate,@annualFeesRecovered);

CALL accumulatedInterestR(theDate,@accumulatedInterestR);

CALL loanPenaltyRecov(theDate,@loanPenaltyRecovered);


CALL otherIncomesCollected(theDate,@otherIncomesCollectedX);

CALL SavingsDepositsMade(theDate,@savingsC);

CALL PayablesCreated(theDate,@payableCreated);

CALL InsurancePayableMade(theDate,@insurancePayMade);

CALL otherLiabilitiesAndProvisionsMade(theDate,@OtherLiabilities);

CALL capitalisationMade(theDate,@capitalPayments);

CALL OtherCapitalisationsAndReservesMade(theDate,@otheCapsAndReserversMade);

CALL RecevablesRefunded(theDate,@Refundreceiavale);

CALL OtherReceivablesAndPrepaymentsRefunded(theDate,@otherReceiAndPrepaymentRend);


CALL BankWithdrawsMade(theDate,@BankWithdrws);


CALL princimpalLoanRepaymentsMade(theDate,@princimpalRepaymentsMade);



CALL refundFromMobileMoneyPayableMade(theDate,@BankWithdrws);


CALL fixedAssetsAndInvestmentsDisposedOff(theDate,@fixedAssetsAndInvestmentDisp);




CALL ExpensesMade(theDate,@ExpensesMa);


CALL InterestWrittenOff(theDate,@interWriteOff);


CALL accumuInteresWrittenOff(theDate,@accumuIntereWrittenOff);

CALL processingFeesWrittenOff(theDate,@processFeesWriteOff);


CALL OtherIncomesWrittenOff(theDate,@otherIncomesWrOff);

CALL ReceivablesCreated(theDate,@receiavale);

CALL MobileMoneyReceivableCreated(theDate,@mobileMoney);

CALL OtherReceivablesAndPrepaymentsCreated(theDate,@otherRecePreMade);

CALL BankDepositsMade(theDate,@bankDepositMade);

CALL LoanDisbursementsMade(theDate,@loansDisbursed);

CALL fixedAssetsAndInvestmentsAquired(theDate,@fixedAssetsAndInvestmentAquired);

CALL PayablesRefunded(theDate,@RefundPayable);

CALL PayablesOtherLiabilitiesAndProvisionsRefunded(theDate,@RefundPayableOtherLiabilProvis);

CALL InsurancePayableCleared(theDate,@insurancePayableCleared);


CALL DrawingsMade(theDate,@DrawingM);

CALL DecapitalisationsMade(theDate,@Decapitlise);

CALL OtherEquitiesAndReservesRemoved(theDate,@equitiesReservesRemoved);

CALL SavingsDepositsWithdrawn(theDate,@savingDepositWith);


SELECT * FROM temp_dailySummuryCollection;

DROP TABLE temp_dailySummuryCollection;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS OpeningCashBalance;

DELIMITER //

/* The grand summury daily report will generate a report of total loan collections and general cash items by collecting data from different tables */
/* The expected summuries should include both loan and cash collection summuries */
CREATE PROCEDURE OpeningCashBalance(IN theDate DATE,OUT OpeningCahdBala VARCHAR(100)) READS SQL DATA BEGIN


 DECLARE cashAccountRep VARCHAR(30); /* This is the unique itentfier for an active loan */


 DECLARE l_done INTEGER DEFAULT 0; /*  Variable controlling the cusor*/

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/



 DECLARE forSelectingCashAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01123%'; /* cursor for iterating through each borrower's account*/
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;/*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingCashAccounts; /* Open the cursor holding loan ids for each customer*/

SET @cashBal=0;
accounts_loop: LOOP /*Loop through the loanIds */

 FETCH forSelectingCashAccounts into cashAccountRep;/*Pick the loan id into the variable loanIdz */


 IF l_done=1 THEN/*check whether the cusor sitll holds more values and if not terminate loop*/

LEAVE accounts_loop;

 END IF;

   SET @sql_text = concat(CAST(" SELECT  ledger_balance INTO @openBalance FROM    pmms.bsanca" AS CHAR CHARACTER SET utf8),cashAccountRep,CAST(" WHERE  trn_date<'" AS CHAR CHARACTER SET utf8),theDate,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
SELECT @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;
SELECT @openBalance;
IF ISNULL(@openBalance) THEN

SET @openBalance=0;

END IF;


SET @cashBal=@cashBal+@openBalance;

IF ISNULL(@cashBal) THEN

SET @cashBal=0;

END IF;

SET l_done=0;
SET @openBalance=0;
SET @closingBalance=0;
 END LOOP accounts_loop;
 
 SET OpeningCahdBala=@cashBal;

 CLOSE forSelectingCashAccounts;

END //

DELIMITER ;







DROP PROCEDURE IF EXISTS ProcessingFeesCollected;
DELIMITER //
CREATE PROCEDURE ProcessingFeesCollected(IN theDate DATE,OUT processingFees VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


 SET @cash=0;
OPEN cursor_forSelectingProcessingFeesAccounts; 

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


SET @qr=CONCAT(CAST("CREATE VIEW account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @qr;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

/* SELECT batchNumbersReps; */

 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;

CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);

IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

IF @amount<>'-' OR @amount<>0 THEN
SET @cash=@cash+@amount;

END IF;

END IF;



SET inner_done=0;
END LOOP BATCH_LOOP; 
CLOSE cursor_forSelectingBatchNumbers; 
END INNER_BLOCK;

DROP VIEW account_view;


SET outer_done=0;
 END LOOP ACCOUNTS_LOOP;
CLOSE cursor_forSelectingProcessingFeesAccounts;
SELECT @cash;
SET processingFees=@cash;
END OUTER_BLOCK//

DELIMITER ;


CALL ProcessingFeesCollected('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol;





DROP PROCEDURE IF EXISTS LedgerFees;
DELIMITER //
CREATE PROCEDURE LedgerFees(IN theDate DATE,OUT ledgerFessCol VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03304%';
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
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

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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







DROP PROCEDURE IF EXISTS BadDebtsRecovered;
DELIMITER //
CREATE PROCEDURE BadDebtsRecovered(IN theDate DATE,OUT badDebtsRecover VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03318%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 SET badDebtsRecover='0';
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;

SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
 SELECT @cash;
 IF @cash IS NULL THEN
SET badDebtsRecover='0';
ELSE
SET badDebtsRecover=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL BadDebtsRecovered('2019-06-21',@badDebtsRecover1);

SELECT @badDebtsRecover1;





DROP PROCEDURE IF EXISTS MembershipFees;
DELIMITER //
CREATE PROCEDURE MembershipFees(IN theDate DATE,OUT memberShipFessCol VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03309%';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;

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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;

SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
 SELECT @cash;
 IF @cash IS NULL THEN
SET memberShipFessCol='0';
ELSE
SET memberShipFessCol=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL MembershipFees('2019-06-21',@memberShipFessCol);

SELECT @memberShipFessCol;






DROP PROCEDURE IF EXISTS annualSubFees;
DELIMITER //
CREATE PROCEDURE annualSubFees(IN theDate DATE,OUT annualFeesRecovered VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03316%'; 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;

SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
 SELECT @cash;
 IF @cash IS NULL THEN
SET annualFeesRecovered='0';
ELSE
SET annualFeesRecovered=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL annualSubFees('2019-06-21',@annualFeesRecovered);

SELECT @annualFeesRecovered;





DROP PROCEDURE IF EXISTS accumulatedInterestR;
DELIMITER //
CREATE PROCEDURE accumulatedInterestR(IN theDate DATE,OUT accumulatedInterestR VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03311%'; 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;

SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
 SELECT @cash;
 IF @cash IS NULL THEN
SET accumulatedInterestR='0';
ELSE
SET accumulatedInterestR=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR;










DROP PROCEDURE IF EXISTS loanPenaltyRecov;
DELIMITER //
CREATE PROCEDURE loanPenaltyRecov(IN theDate DATE,OUT loanPenaltyRecovered VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03312%'; 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
SELECT @qr;
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;

IF batchNumbersReps IS NULL THEN
SET batchNumbersReps=0;
END IF;

SELECT processindFeesAccountRep;
SELECT batchNumbersReps;
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
 SELECT @cash;
 IF @cash IS NULL THEN
SET loanPenaltyRecovered='0';
ELSE
SET loanPenaltyRecovered=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL loanPenaltyRecov('2019-06-21',@loanPenaltyRecovered);

SELECT @loanPenaltyRecovered;









DROP PROCEDURE IF EXISTS otherIncomesCollected;
DELIMITER //
CREATE PROCEDURE otherIncomesCollected(IN theDate DATE,OUT otherIncomesCollectedX VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%'  OR account_number like  '03310%'  OR account_number like  '03313%' OR account_number like '03314%'  OR account_number like '03317%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
DECLARE CONTINUE HANDLER FOR NOT FOUND SET outer_done=1;


OPEN cursor_forSelectingProcessingFeesAccounts; 
 SET @cash=0;
 
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

SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
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

SELECT processindFeesAccountRep;
/* SELECT batchNumbersReps; */
 IF inner_done=1 THEN
LEAVE BATCH_LOOP;
 END IF;
 
 CALL cashAccountWasDebited(batchNumbersReps,@cashDebited);
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
END IF;

 
IF @cashDebited>0 THEN

SET @quary=CONCAT(CAST("SELECT credit INTO @amount FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE chq_number='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
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
 IF @amount IS NULL THEN
SET @amount=0;
END IF;
SET @amount=0;

 END LOOP ACCOUNTS_LOOP;
 
 
SET outer_done=0;
 
CLOSE cursor_forSelectingProcessingFeesAccounts;
/*  SELECT @cash; */
 IF @cash IS NULL THEN
SET otherIncomesCollectedX='0';
ELSE
SET otherIncomesCollectedX=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL otherIncomesCollected('2019-06-21',@otherIncomesCollectedX);

SELECT @otherIncomesCollectedX;







DROP PROCEDURE IF EXISTS SavingsDepositsMade;

DELIMITER //

CREATE PROCEDURE SavingsDepositsMade(IN theDate DATE,OUT savingsDepositC VARCHAR(100)) READS SQL DATA 

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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit-@cashDebit;


 IF @cash IS NULL THEN
SET savingsDepositC='0';
ELSE

SET savingsDepositC=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL SavingsDepositsMade('2019-06-22',@savingsDepositC);

SELECT @savingsDepositC;









DROP PROCEDURE IF EXISTS PayablesCreated;

DELIMITER //

CREATE PROCEDURE PayablesCreated(IN theDate DATE,OUT payableCreatedFinal VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '05500%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET payableCreatedFinal='0';
ELSE

SET payableCreatedFinal=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL PayablesCreated('2019-06-22',@payableCreatedFinal);

SELECT @payableCreatedFinal;










DROP PROCEDURE IF EXISTS InsurancePayableMade;

DELIMITER //


CREATE PROCEDURE InsurancePayableMade(IN theDate DATE,OUT insurancePayableMadev VARCHAR(100)) READS SQL DATA BEGIN


 DECLARE insurancePayableAccountRep VARCHAR(30); 


 DECLARE l_done INTEGER DEFAULT 0; 

 DECLARE lastDate,originalDueDate DATE; 



 DECLARE forSelectingInsurancePayableCreatedAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number like '05524%' ;
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 OPEN forSelectingInsurancePayableCreatedAccounts; 

SET @cashBal=0;
accounts_loop: LOOP 

 FETCH forSelectingInsurancePayableCreatedAccounts into insurancePayableAccountRep;


 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

   SET @sql_text = concat(CAST(" SELECT  ledger_balance INTO @openBalance FROM    pmms.bsanca" AS CHAR CHARACTER SET utf8),insurancePayableAccountRep,CAST(" WHERE  trn_date<'" AS CHAR CHARACTER SET utf8),theDate,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
/* SELECT @sql_text; */
  EXECUTE stmt;
DROP PREPARE stmt;

 SET @sql_text = concat(CAST(" SELECT  ledger_balance INTO @closingBalance FROM    pmms.bsanca" AS CHAR CHARACTER SET utf8),insurancePayableAccountRep,CAST(" WHERE  trn_date<='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
/* SELECT @sql_text; */
  EXECUTE stmt;
DROP PREPARE stmt;

SELECT @openBalance;
SELECT @closingBalance;

IF ISNULL(@openBalance) THEN

SET @openBalance=0;

END IF;

IF ISNULL(@closingBalance) THEN

SET @closingBalance=0;

END IF;


SET @actualBalance=@closingBalance-@openBalance;

IF @actualBalance>0 THEN 

SET @cashBal=@cashBal+@actualBalance;

END IF;

SELECT @cashBal;


IF ISNULL(@cashBal) THEN

SET @cashBal=0;

END IF;

SET l_done=0;


SET @openBalance=0;
SET @closingBalance=0;
 END LOOP accounts_loop;
 
 IF ISNULL(@cashBal) THEN

SET @cashBal=0;

END IF;
 
 SET insurancePayableMadev=@cashBal;

 CLOSE forSelectingInsurancePayableCreatedAccounts;

END //

DELIMITER ;


CALL InsurancePayableMade('2019-04-09',@insurancePayableMadeX);

SELECT @insurancePayableMade;




DROP PROCEDURE IF EXISTS otherLiabilitiesAndProvisionsMade;

DELIMITER //

CREATE PROCEDURE otherLiabilitiesAndProvisionsMade(IN theDate DATE,OUT liabilityProvi VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number like '05504%' 
 OR account_number like '05501%' OR account_number like '05503%' OR account_number like '05505%' OR account_number like '05506%' OR account_number like '05507%'
 OR account_number like '05522%' OR account_number like '05525%' OR account_number like '05527%' OR account_number like '05526%' OR account_number like '05528%'
 OR account_number like '05523%' OR account_number like '05523%' OR account_number like '05508%' OR account_number like '05510%' 
 OR account_number like '05511%' OR account_number like '05512%' OR account_number like '05513%' OR account_number like '05514%' OR account_number like '05515%'  
 OR account_number like '05516%'  OR account_number like '05517%'  OR account_number like '05518%'  OR account_number like '05519%'  OR account_number like '05520%'  OR account_number like '05521%');
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET liabilityProvi='0';
ELSE

SET liabilityProvi=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL otherLiabilitiesAndProvisionsMade('2019-06-22',@liabilityProvi);

SELECT @liabilityProvi;







DROP PROCEDURE IF EXISTS capitalisationMade;

DELIMITER //

CREATE PROCEDURE capitalisationMade(IN theDate DATE,OUT someEquity VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04400%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET someEquity='0';
ELSE

SET someEquity=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL capitalisationMade('2019-06-22',@someEquity);

SELECT @someEquity;






DROP PROCEDURE IF EXISTS OtherCapitalisationsAndReservesMade;

DELIMITER //

CREATE PROCEDURE OtherCapitalisationsAndReservesMade(IN theDate DATE,OUT otheCapsAndReserversMade VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number>='044010000110' AND account_number<='04430999999';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET otheCapsAndReserversMade='0';
ELSE

SET otheCapsAndReserversMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;

CALL OtherCapitalisationsAndReservesMade('2019-06-22',@otheCapsAndReserversMade);


SELECT @otheCapsAndReserversMade;






DROP PROCEDURE IF EXISTS RecevablesRefunded;

DELIMITER //

CREATE PROCEDURE RecevablesRefunded(IN theDate DATE,OUT Refundreceiavale VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01131%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET Refundreceiavale='0';
ELSE

SET Refundreceiavale=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL RecevablesRefunded('2019-06-23',@Refundreceiavale);

SELECT @Refundreceiavale;




DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsRefunded;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsRefunded(IN theDate DATE,OUT otherReceiAndPrepaymentRend VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number LIKE '01117%' OR account_number LIKE '01118%' OR account_number LIKE '01119%'
 OR account_number LIKE '01132%'  OR account_number LIKE '01133%'  OR account_number LIKE '01134%'  OR account_number LIKE '01135%'  OR account_number LIKE '01120%');
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET otherReceiAndPrepaymentRend='0';
ELSE

SET otherReceiAndPrepaymentRend=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL OtherReceivablesAndPrepaymentsRefunded('2019-06-23',@otherReceiAndPrepaymentRend);

SELECT @otherReceiAndPrepaymentRend;




DROP PROCEDURE IF EXISTS BankWithdrawsMade;

DELIMITER //

CREATE PROCEDURE BankWithdrawsMade(IN theDate DATE,OUT BankWithdrws VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01122%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET BankWithdrws='0';
ELSE

SET BankWithdrws=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

SELECT @BankWithdrws;








DROP PROCEDURE IF EXISTS BankWithdrawsMade;

DELIMITER //

CREATE PROCEDURE BankWithdrawsMade(IN theDate DATE,OUT BankWithdrws VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01122%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET BankWithdrws='0';
ELSE

SET BankWithdrws=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

SELECT @BankWithdrws;



DROP PROCEDURE IF EXISTS princimpalLoanRepaymentsMade;

DELIMITER //

CREATE PROCEDURE princimpalLoanRepaymentsMade(IN theDate DATE,OUT princimpalRepaymentsMade VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01128%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET princimpalRepaymentsMade='0';
ELSE

SET princimpalRepaymentsMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL princimpalLoanRepaymentsMade('2019-06-23',@princimpalRepaymentsMade);

SELECT @princimpalRepaymentsMade;





DROP PROCEDURE IF EXISTS refundFromMobileMoneyPayableMade;

DELIMITER //

CREATE PROCEDURE refundFromMobileMoneyPayableMade(IN theDate DATE,OUT mobileMoneyRefund VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '01121%';
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET mobileMoneyRefund='0';
ELSE

SET mobileMoneyRefund=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL refundFromMobileMoneyPayableMade('2019-06-23',@mobileMoneyRefund);

SELECT @mobileMoneyRefund;




DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsDisposedOff;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsDisposedOff(IN theDate DATE,OUT fixedAssetsAndInvestmentDisp VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number LIKE '01100%' OR account_number LIKE '01101%'
OR account_number LIKE '01102%' OR account_number LIKE '01103%' OR account_number LIKE '01104%' OR account_number LIKE '01105%' OR account_number LIKE '01106%'  OR account_number LIKE '01108%' OR account_number LIKE '01109%'
OR account_number LIKE '01110%' OR account_number LIKE '01111%'  OR account_number LIKE '01112%'  OR account_number LIKE '01136%');
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


 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
 
 
 IF @cashDebited IS NULL THEN
SET @cashDebited=0;
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




IF @cashCredit>0 THEN

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;

END IF;

IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

SELECT batchNumbersReps;

IF @amountDebit<>'-' OR @amountDebit<>0 THEN

SET @cashDebit=@cashDebit+@amountDebit;

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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET fixedAssetsAndInvestmentDisp='0';
ELSE

SET fixedAssetsAndInvestmentDisp=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL fixedAssetsAndInvestmentsDisposedOff('2019-06-23',@fixedAssetsAndInvestmentDisp);

SELECT @fixedAssetsAndInvestmentDisp;





DROP PROCEDURE IF EXISTS ExpensesMade;

DELIMITER //

CREATE PROCEDURE ExpensesMade(IN theDate DATE,OUT ExpensesMa VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '022%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET ExpensesMa='0';
ELSE

SET ExpensesMa=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL ExpensesMade('2019-06-23',@ExpensesMa);

SELECT @ExpensesMa;






DROP PROCEDURE IF EXISTS InterestWrittenOff;

DELIMITER //

CREATE PROCEDURE InterestWrittenOff(IN theDate DATE,OUT interWriteOff VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03301%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET interWriteOff='0';
ELSE

SET interWriteOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;




CALL InterestWrittenOff('2019-06-24',@interWriteOff);

SELECT @interWriteOff;



DROP PROCEDURE IF EXISTS accumuInteresWrittenOff;

DELIMITER //

CREATE PROCEDURE accumuInteresWrittenOff(IN theDate DATE,OUT accumuIntereWrittenOff VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03311%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET accumuIntereWrittenOff='0';
ELSE

SET accumuIntereWrittenOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;




CALL accumuInteresWrittenOff('2019-06-24',@accumuIntereWrittenOff);

SELECT @accumuIntereWrittenOff;





DROP PROCEDURE IF EXISTS processingFeesWrittenOff;

DELIMITER //

CREATE PROCEDURE processingFeesWrittenOff(IN theDate DATE,OUT processFeesWriteOff VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03315%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET processFeesWriteOff='0';
ELSE

SET processFeesWriteOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;




CALL processingFeesWrittenOff('2019-06-24',@processFeesWriteOff);

SELECT @processFeesWriteOff;






DROP PROCEDURE IF EXISTS OtherIncomesWrittenOff;

DELIMITER //

CREATE PROCEDURE OtherIncomesWrittenOff(IN theDate DATE,OUT otherIncomesWrOff VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '03312%' OR account_number LIKE '03316%' OR account_number LIKE '03309%' OR account_number LIKE '03318%' OR account_number LIKE '03304%' OR account_number like '03305%' OR account_number like  '03306%' OR account_number like  '03307%' OR account_number like '03308%'  OR account_number like  '03310%'  OR account_number like  '03313%' OR account_number like '03314%'  OR account_number like '03317%' OR account_number like  '03319%' OR account_number like '03320%' OR account_number like  '03321%' OR account_number like  '03322%' OR account_number like '03323%' OR account_number like  '03324%' OR account_number like  '03325%');
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET otherIncomesWrOff='0';
ELSE

SET otherIncomesWrOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;




CALL OtherIncomesWrittenOff('2019-06-24',@otherIncomesWrOff);

SELECT @otherIncomesWrOff;





DROP PROCEDURE IF EXISTS ReceivablesCreated;

DELIMITER //

CREATE PROCEDURE ReceivablesCreated(IN theDate DATE,OUT receiavale VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01131%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET receiavale='0';
ELSE

SET receiavale=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL ReceivablesCreated('2019-06-24',@receiavale);

SELECT @receiavale;




DROP PROCEDURE IF EXISTS MobileMoneyReceivableCreated;

DELIMITER //

CREATE PROCEDURE MobileMoneyReceivableCreated(IN theDate DATE,OUT mobileMoneyc VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01121%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET mobileMoneyc='0';
ELSE

SET mobileMoneyc=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL MobileMoneyReceivableCreated('2019-06-24',@mobileMoneyc);

SELECT @mobileMoneyc;







DROP PROCEDURE IF EXISTS OtherReceivablesAndPrepaymentsCreated;

DELIMITER //

CREATE PROCEDURE OtherReceivablesAndPrepaymentsCreated(IN theDate DATE,OUT otherRecePreMade VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '01117%' OR account_number LIKE '01118%' OR account_number LIKE '01119%'
 OR account_number LIKE '01132%'  OR account_number LIKE '01133%'  OR account_number LIKE '01134%'  OR account_number LIKE '01135%'  OR account_number LIKE '01120%');
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET otherRecePreMade='0';
ELSE

SET otherRecePreMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL OtherReceivablesAndPrepaymentsCreated('2019-06-24',@otherRecePreMade);

SELECT @otherRecePreMade;








DROP PROCEDURE IF EXISTS BankDepositsMade;

DELIMITER //

CREATE PROCEDURE BankDepositsMade(IN theDate DATE,OUT bankDepositMade VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01122%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET bankDepositMade='0';
ELSE

SET bankDepositMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL BankDepositsMade('2019-06-24',@bankDepositMade);

SELECT @bankDepositMade;





DROP PROCEDURE IF EXISTS LoanDisbursementsMade;

DELIMITER //

CREATE PROCEDURE LoanDisbursementsMade(IN theDate DATE,OUT loansDisbursed VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '01128%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET loansDisbursed='0';
ELSE

SET loansDisbursed=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL LoanDisbursementsMade('2019-06-24',@loansDisbursed);

SELECT @loansDisbursed;




DROP PROCEDURE IF EXISTS fixedAssetsAndInvestmentsAquired;

DELIMITER //

CREATE PROCEDURE fixedAssetsAndInvestmentsAquired(IN theDate DATE,OUT fixedAssetsAndInvestmentAquired VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number LIKE '01100%' OR account_number LIKE '01101%'
OR account_number LIKE '01102%' OR account_number LIKE '01103%' OR account_number LIKE '01104%' OR account_number LIKE '01105%' OR account_number LIKE '01106%'  OR account_number LIKE '01108%' OR account_number LIKE '01109%'
OR account_number LIKE '01110%' OR account_number LIKE '01111%'  OR account_number LIKE '01112%'  OR account_number LIKE '01136%');
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET fixedAssetsAndInvestmentAquired='0';
ELSE

SET fixedAssetsAndInvestmentAquired=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL fixedAssetsAndInvestmentsAquired('2019-06-24',@fixedAssetsAndInvestmentAquired);

SELECT @fixedAssetsAndInvestmentAquired;




DROP PROCEDURE IF EXISTS PayablesRefunded;

DELIMITER //

CREATE PROCEDURE PayablesRefunded(IN theDate DATE,OUT RefundPayable VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  account_number LIKE '05500%' ;
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET RefundPayable='0';
ELSE

SET RefundPayable=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL PayablesRefunded('2019-06-24',@RefundPayable);

SELECT @RefundPayable;






DROP PROCEDURE IF EXISTS PayablesOtherLiabilitiesAndProvisionsRefunded;

DELIMITER //

CREATE PROCEDURE PayablesOtherLiabilitiesAndProvisionsRefunded(IN theDate DATE,OUT RefundPayableOtherLiabilProvis VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE  (account_number like '05504%' 
 OR account_number like '05501%' OR account_number like '05503%' OR account_number like '05505%' OR account_number like '05506%' OR account_number like '05507%'
 OR account_number like '05522%' OR account_number like '05525%' OR account_number like '05527%' OR account_number like '05526%' OR account_number like '05528%'
 OR account_number like '05523%' OR account_number like '05523%' OR account_number like '05508%' OR account_number like '05510%' 
 OR account_number like '05511%' OR account_number like '05512%' OR account_number like '05513%' OR account_number like '05514%' OR account_number like '05515%'  
 OR account_number like '05516%'  OR account_number like '05517%'  OR account_number like '05518%'  OR account_number like '05519%'  OR account_number like '05520%'  OR account_number like '05521%');
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET RefundPayableOtherLiabilProvis='0';
ELSE

SET RefundPayableOtherLiabilProvis=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL PayablesOtherLiabilitiesAndProvisionsRefunded('2019-06-24',@RefundPayableOtherLiabilProvis);

SELECT @RefundPayableOtherLiabilProvis;







DROP PROCEDURE IF EXISTS InsurancePayableCleared;

DELIMITER //

CREATE PROCEDURE InsurancePayableCleared(IN theDate DATE,OUT insurancePayCleared VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '05524%' ;
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET insurancePayCleared='0';
ELSE

SET insurancePayCleared=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL InsurancePayableCleared('2019-06-24',@insurancePayCleared);

SELECT @insurancePayCleared;






DROP PROCEDURE IF EXISTS DrawingsMade;

DELIMITER //

CREATE PROCEDURE DrawingsMade(IN theDate DATE,OUT DrawingM VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04408%' ;
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET DrawingM='0';
ELSE

SET DrawingM=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL DrawingsMade('2019-06-24',@DrawingM);

SELECT @DrawingM;





DROP PROCEDURE IF EXISTS DecapitalisationsMade;

DELIMITER //

CREATE PROCEDURE DecapitalisationsMade(IN theDate DATE,OUT Decapitlise VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number like '04400%';
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET Decapitlise='0';
ELSE

SET Decapitlise=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL DecapitalisationsMade('2019-06-24',@Decapitlise);

SELECT @Decapitlise;






DROP PROCEDURE IF EXISTS OtherEquitiesAndReservesRemoved;

DELIMITER //

CREATE PROCEDURE OtherEquitiesAndReservesRemoved(IN theDate DATE,OUT equitiesReservesRemoved VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE ((account_number>='044010000110' AND account_number<='04430999999') AND NOT account_number='04408000110');
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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET equitiesReservesRemoved='0';
ELSE

SET equitiesReservesRemoved=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL OtherEquitiesAndReservesRemoved('2019-06-24',@equitiesReservesRemoved);

SELECT @equitiesReservesRemoved;





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

SELECT @batch;

 CALL cashAccountWasDebited(@batch,@cashDebited);
 
 
  CALL cashAccountWasCredited(@batch,@cashCredited);
  
  SELECT @cashDebited,@amountCredit;
 
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


SELECT batchNumbersReps;

SELECT processindFeesAccountRep,@amountDebit,@cashDebit;




IF @amountCredit>0 THEN

SELECT processindFeesAccountRep,@amountCredit,@cashCredit;

END IF;



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
SELECT @cashCredit,@cashDebit;
SET @cash =@cashDebit-@cashCredit;


 IF @cash IS NULL THEN
SET savingDepositWith='0';
ELSE

SET savingDepositWith=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL SavingsDepositsWithdrawn('2019-06-24',@savingDepositWith);

SELECT @savingDepositWith;



DROP PROCEDURE IF EXISTS cashAccountWasDebited;
DELIMITER //
CREATE PROCEDURE cashAccountWasDebited(IN batchNumber VARCHAR(30),OUT stat INTEGER) BEGIN

SELECT COUNT(trn_id) INTO stat  from pmms.general_ledger where chq_number=batchNumber AND debit_account_no LIKE '01123%';
IF stat IS NULL THEN
SET stat=0;
END IF;
END//

DELIMITER ;

DROP PROCEDURE IF EXISTS cashAccountWasCredited;
DELIMITER //
CREATE PROCEDURE cashAccountWasCredited(IN batchNumber VARCHAR(30),OUT stat INTEGER) BEGIN

SELECT COUNT(trn_id) INTO stat  from pmms.general_ledger where chq_number=batchNumber AND credit_account_no LIKE '01123%';
IF stat IS NULL THEN
SET stat=0;
END IF;
END//

DELIMITER ;

