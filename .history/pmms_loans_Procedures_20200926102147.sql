


/*===========================================================MIGRATION FROM OLD TO NEW DB==============================================================================*/


DROP PROCEDURE IF EXISTS migrateDateNewOnes;
 DELIMITER //

 CREATE PROCEDURE migrateDateNewOnes() READS SQL DATA BEGIN

DECLARE TrnId VARCHAR(40);

DECLARE loanId VARCHAR(40);

DECLARE l_done INTEGER;


DECLARE forselectingLoanId CURSOR FOR SELECT trn_id,loan_id  FROM pmms_loans.new_loan_appstore;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;




SET @sql_text2 = concat(CAST("DROP TABLE IF EXISTS new_loan_appstoreamort" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;


SET @sql_text3 = concat(CAST(
  
  "CREATE TABLE new_loan_appstoreamort (
  trn_id int(11) NOT NULL AUTO_INCREMENT,
  instalment_no int(11) NOT NULL DEFAULT '1',
  princimpal_amount varchar(50) NOT NULL DEFAULT '0.0',
  princimpal_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
  interest_amount varchar(50) NOT NULL DEFAULT '10000.0',
  interest_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
  instalment_amount varchar(50) NOT NULL DEFAULT '0.0',
  instalment_paid varchar(50) NOT NULL DEFAULT '0.0',
  beginning_bal varchar(50) NOT NULL DEFAULT '0.0',
  closing_bal varchar(50) NOT NULL DEFAULT '0.0',
  instalment_due_date date NOT NULL DEFAULT '1979-01-01',
  instalment_status varchar(50) NOT NULL DEFAULT 'P',
  instalment_paid_date date NOT NULL DEFAULT '1979-01-01',
  instalment_paid_variance varchar(50) NOT NULL DEFAULT '2 Days',
  LoanPenalty varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterest varchar(50) NOT NULL DEFAULT '0.0',
  InstalmentRemaining varchar(50) NOT NULL DEFAULT '0.0',
  PrincipalPaid varchar(50) NOT NULL DEFAULT '0.0',
  PrincipalRemaining varchar(50) NOT NULL DEFAULT '0.0',
  InterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  InterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
  LoanPenaltyPaid varchar(50) NOT NULL DEFAULT '0.0',
  LoanPenaltyRemaining varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterest varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterestRemaining varchar(50) NOT NULL DEFAULT '0.0',
  OtherOne varchar(50) NOT NULL DEFAULT 'NYA',
  OtherTwo date NOT NULL DEFAULT '1979-01-01',
  OtherThree date NOT NULL DEFAULT '1979-01-01',
  master1_id varchar(50) NOT NULL DEFAULT '0.0',
  master2_id varchar(50) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (trn_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt3 FROM @sql_text3;
  EXECUTE stmt3;
DROP PREPARE stmt3;



SET @sql_text4 = concat(CAST('DROP TABLE IF EXISTS tempT' AS CHAR CHARACTER SET utf8));

  PREPARE stmt4 FROM @sql_text4;
  EXECUTE stmt4;
DROP PREPARE stmt4;



SET @sql_text5 = concat(CAST("CREATE TABLE tempT (
  trn_id int(11) NOT NULL AUTO_INCREMENT,
  instalment_no int(11) NOT NULL DEFAULT 1,
  princimpal_amount varchar(50) NOT NULL DEFAULT '0.0',
  princimpal_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
  interest_amount varchar(50) NOT NULL DEFAULT '10000.0',
  interest_amount_run_bal varchar(50) NOT NULL DEFAULT '0.0',
  instalment_amount varchar(50) NOT NULL DEFAULT '0.0',
  instalment_paid varchar(50) NOT NULL DEFAULT '0.0',
  beginning_bal varchar(50) NOT NULL DEFAULT '0.0',
  closing_bal varchar(50) NOT NULL DEFAULT '0.0',
  instalment_due_date date NOT NULL DEFAULT '1979-01-01',
  instalment_status varchar(50) NOT NULL DEFAULT 'P',
  instalment_paid_date date NOT NULL DEFAULT '1979-01-01',
  instalment_paid_variance varchar(50) NOT NULL DEFAULT '2 Days',
  LoanPenalty varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterest varchar(50) NOT NULL DEFAULT '0.0',
  InstalmentRemaining varchar(50) NOT NULL DEFAULT '0.0',
  PrincipalPaid varchar(50) NOT NULL DEFAULT '0.0',
  PrincipalRemaining varchar(50) NOT NULL DEFAULT '0.0',
  InterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  InterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
  LoanPenaltyPaid varchar(50) NOT NULL DEFAULT '0.0',
  LoanPenaltyRemaining varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  AccruedInterestRemaing varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterest varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterestPaid varchar(50) NOT NULL DEFAULT '0.0',
  AccumulatedInterestRemaining varchar(50) NOT NULL DEFAULT '0.0',
  OtherOne varchar(50) NOT NULL DEFAULT 'NYA',
  OtherTwo date NOT NULL DEFAULT '1979-01-01',
  OtherThree date NOT NULL DEFAULT '1979-01-01',
  master1_id varchar(50) NOT NULL DEFAULT '0.0',
  master2_id varchar(50) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (trn_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt5 FROM @sql_text5;
  EXECUTE stmt5;
DROP PREPARE stmt5;


 OPEN forselectingLoanId;


loanIds_loop: LOOP 

 FETCH forselectingLoanId into TrnId, loanId;

/* SELECT TrnId;

SELECT loanId; */

 IF l_done=1 THEN

LEAVE loanIds_loop;

 END IF;

SET @tableName=concat(loanId);



SET @sql_text1 = concat(CAST('INSERT INTO tempT ( 
  instalment_no,
  princimpal_amount ,
  princimpal_amount_run_bal ,
  interest_amount ,
  interest_amount_run_bal ,
  instalment_amount ,
  instalment_paid ,
  beginning_bal ,
  closing_bal ,
  instalment_due_date ,
  instalment_status,
  instalment_paid_date ,
  instalment_paid_variance ,
  LoanPenalty ,
  AccruedInterest ,
  InstalmentRemaining ,
  PrincipalPaid ,
  PrincipalRemaining ,
  InterestPaid ,
  InterestRemaing ,
  LoanPenaltyPaid ,
  LoanPenaltyRemaining ,
  AccruedInterestPaid ,
  AccruedInterestRemaing ,
  AccumulatedInterest ,
  AccumulatedInterestPaid ,
  AccumulatedInterestRemaining ,
  OtherOne,
  OtherTwo ,
  OtherThree) SELECT 
  instalment_no,
  princimpal_amount ,
  princimpal_amount_run_bal ,
  interest_amount ,
  interest_amount_run_bal ,
  instalment_amount ,
  instalment_paid ,
  beginning_bal ,
  closing_bal ,
  instalment_due_date ,
  instalment_status,
  instalment_paid_date ,
  instalment_paid_variance ,
  LoanPenalty ,
  AccruedInterest ,
  InstalmentRemaining ,
  PrincipalPaid ,
  PrincipalRemaining ,
  InterestPaid ,
  InterestRemaing ,
  LoanPenaltyPaid ,
  LoanPenaltyRemaining ,
  AccruedInterestPaid ,
  AccruedInterestRemaing ,
  AccumulatedInterest ,
  AccumulatedInterestPaid ,
  AccumulatedInterestRemaining ,
  OtherOne,
  OtherTwo ,
  OtherThree FROM  ' AS CHAR CHARACTER SET utf8),@tableName);
  
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

SET @Tid=concat(CAST("'" AS CHAR CHARACTER SET utf8),TrnId,CAST("'" AS CHAR CHARACTER SET utf8));
SET @Lid=concat(CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8));

  SET @sql_text9 = concat(CAST('UPDATE tempT SET master1_id=' AS CHAR CHARACTER SET utf8),@Tid,CAST(',  master2_id='AS CHAR CHARACTER SET utf8),@Lid);
PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;

  SET @sql_text7 = concat(CAST('INSERT INTO  pmms_loans.new_loan_appstoreamort( 
  instalment_no,
  princimpal_amount ,
  princimpal_amount_run_bal ,
  interest_amount ,
  interest_amount_run_bal ,
  instalment_amount ,
  instalment_paid ,
  beginning_bal ,
  closing_bal ,
  instalment_due_date ,
  instalment_status,
  instalment_paid_date ,
  instalment_paid_variance ,
  LoanPenalty ,
  AccruedInterest ,
  InstalmentRemaining ,
  PrincipalPaid ,
  PrincipalRemaining ,
  InterestPaid ,
  InterestRemaing ,
  LoanPenaltyPaid ,
  LoanPenaltyRemaining ,
  AccruedInterestPaid ,
  AccruedInterestRemaing ,
  AccumulatedInterest ,
  AccumulatedInterestPaid ,
  AccumulatedInterestRemaining ,
  OtherOne,
  OtherTwo ,
  OtherThree,
  master1_id,
  master2_id
  ) SELECT 
  instalment_no,
  princimpal_amount ,
  princimpal_amount_run_bal ,
  interest_amount ,
  interest_amount_run_bal ,
  instalment_amount ,
  instalment_paid ,
  beginning_bal ,
  closing_bal ,
  instalment_due_date ,
  instalment_status,
  instalment_paid_date ,
  instalment_paid_variance ,
  LoanPenalty ,
  AccruedInterest ,
  InstalmentRemaining ,
  PrincipalPaid ,
  PrincipalRemaining ,
  InterestPaid ,
  InterestRemaing ,
  LoanPenaltyPaid ,
  LoanPenaltyRemaining ,
  AccruedInterestPaid ,
  AccruedInterestRemaing ,
  AccumulatedInterest ,
  AccumulatedInterestPaid ,
  AccumulatedInterestRemaining ,
  OtherOne,
  OtherTwo ,
  OtherThree,
  master1_id,
  master2_id FROM pmms_loans.tempT ' AS CHAR CHARACTER SET utf8));
  PREPARE stmt7 FROM @sql_text7;
  EXECUTE stmt7;
DROP PREPARE stmt7;




  DELETE FROM pmms_loans.tempT;

 

SET l_done=0;

 END LOOP loanIds_loop;



 CLOSE forselectingLoanId;


  DROP TABLE pmms_loans.tempT;

 END //
 DELIMITER ;



 CALL migrateDateNewOnes();



/*============================================================DAILY SUMMURY REPORTS AND PROCEDURES===========================================================================*/




DROP TABLE IF EXISTS DailyCollection;

CREATE TABLE DailyCollection (
  CollectionId int(11) NOT NULL AUTO_INCREMENT,
   CollectionDate date NOT NULL DEFAULT '1970-01-01',
  accountName varchar(100) DEFAULT '0',
  loanID varchar(100) DEFAULT '0',
  
   ExpectedPrincipalArrears varchar(100) DEFAULT '0',
    ExpectedPrincipalToday varchar(100) DEFAULT '0',
	ExpectedTotalPrincimpalToday varchar(100) DEFAULT '0',
	PrincimpalCollectedToday varchar(100) DEFAULT '0',
  
      ExpectedInterestArrears varchar(100) DEFAULT '0',
  ExpectedInterestToday varchar(100) DEFAULT '0',
ExpectedTotalInterestToday varchar(100) DEFAULT '0',
InterestCollectedToday varchar(100) DEFAULT '0',

   ExpectedAccumInterestArrears varchar(100) DEFAULT '0',
	 ExpectedAccumInterestToday varchar(100) DEFAULT '0',
ExpectedTotalIAccumnterestToday varchar(100) DEFAULT '0',
AccumInterestCollectedToday varchar(100) DEFAULT '0',
	
	ExpectedPenaltyArrears varchar(100) DEFAULT '0',
	 ExpectedPenaltyToday varchar(100) DEFAULT '0',
ExpectedTotalIPenaltyToday varchar(100) DEFAULT '0',
PenaltyCollectedToday varchar(100) DEFAULT '0',
	
	
	
  ExpectedAmountInArrears varchar(100) DEFAULT '0',  
  ExpectedAmountToday varchar(100) DEFAULT '0',
  ExpectedTotalAmountToday varchar(100) DEFAULT '0',
  AmountCollectedToday varchar(100) DEFAULT '0',
  
   NumberOfInstalmentsInArrears varchar(100) DEFAULT '0',
   
  VarianceCollection varchar(100) DEFAULT '0',
  OtherThree varchar(45) DEFAULT 'NCO',
  OtherFour varchar(45) DEFAULT 'NCO',
  OtherFive varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (CollectionId)
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






/*===========================================Grand Summury Report===================================================================*/
/*  */

DROP PROCEDURE IF EXISTS grandSummryDailyReport;

DELIMITER //

/* The grand summury daily report will generate a report of total loan collections and general cash items by collecting data from different tables */
/* The expected summuries should include both loan and cash collection summuries */
CREATE PROCEDURE grandSummryDailyReport(IN theDate DATE) READS SQL DATA BEGIN
  SET @Vstatus2='-Ve'; 
  SET @Vstatus1='-Ve'; 
  SET @VarinceOT=0;  
  SET @VarinceArrears=0;
    SET @Varince3=0;  
  SET @Varince2=0;
  
    SET @ExpectedPrincinpaWithArrears=0; /*This is the  princimpal amount expected for only instalments in arrears*/
  SET @ExpectedPricimpalToday=0;/* This is the princimpal amount excluding those payments in arrears*/
  SET @ExpectedTotalPricimpalToday=0;/* This is the princimpal amount without arrears plus the princimpal amount with arrears expected today*/
  SET @ActualPrincimpalCollectedToday=0; /* This is total princimpal amount actually collected today*/

   SET @ExpectedInterestWithArrears=0; /*This is the  interest amount expected for only instalments in arrears*/
  SET @ExpectedInterstToday=0;/* This is the interest amount excluding those payments in arrears*/
  SET @ExpectedTotalInterestToday=0;/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
  SET @ActualInterestCollectedToday=0; /* This is total interest amount actually collected today*/

   SET @ExpectedAccumInterestWithArrears=0; /*This is the  interest amount expected for only instalments in arrears*/
  SET @ExpectedAccumInterstToday=0;/* This is the interest amount excluding those payments in arrears*/
  SET @ExpectedTotalAccumInterestToday=0;/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
  SET @ActualAccumInterestCollectedToday=0; /* This is total interest amount actually collected today*/

   SET @ExpectedPenaltyWithArrears=0; /*This is the  interest amount expected for only instalments in arrears*/
  SET @ExpectedPenaltyToday=0;/* This is the interest amount excluding those payments in arrears*/
  SET @ExpectedTotalPenaltyToday=0;/* This is the interest amount without arrears plus the interest amount with arrears expected today*/
  SET @ActualPenaltyCollectedToday=0; /* This is total interest amount actually collected today*/

   SET @ExpectedTotalAmountWithArrears=0; /*This is the  total expected amount including princimpal and interest expected but for only payments in arrears*/
  SET @ExpectedTotalAmountToday=0;/* This is the total amount expected excludint the instalments in arrears*/
  SET @ExpectedOverallTotalAmountToday=0;/* This is the total amount expected overall including interest and princimpa in arrears*/
  SET @ActualTotalAmountCollectedToday=0; /* T*/

SELECT SUM(ExpectedPrincipalArrears),SUM(ExpectedPrincipalToday),SUM(ExpectedTotalPrincimpalToday),SUM(PrincimpalCollectedToday),
SUM(ExpectedInterestArrears),SUM(ExpectedInterestToday),SUM(ExpectedTotalInterestToday),(InterestCollectedToday),SUM(ExpectedAccumInterestArrears),
SUM(ExpectedAccumInterestToday),SUM(ExpectedTotalIAccumnterestToday),SUM(AccumInterestCollectedToday),SUM(ExpectedPenaltyArrears),SUM(ExpectedPenaltyToday) ,
SUM(ExpectedTotalIPenaltyToday),SUM(PenaltyCollectedToday),SUM(ExpectedAmountInArrears),SUM(ExpectedAmountToday),SUM(ExpectedTotalAmountToday) ,
SUM(AmountCollectedToday) INTO

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

INSERT INTO temp_dailySummuryCollection VALUES(1,"LOAN SUMMURIES",0,0,0,0);


/*Total loan amount*/

   
   SET @Varince2=@ActualTotalAmountCollectedToday-@ExpectedTotalAmountWithArrears;
 /*   SELECT @Varince2; */
   IF @Varince2>=0.0 THEN
   
   SET @Vstatus2='+Ve';
   
   SET @Varince2=0.0;
   
   END IF;
   
   IF @Varince2<0.0 THEN
   
   SET @Vstatus2='-Ve';
   
   SET @Varince2=@Varince2*-1;
 
   END IF;
   
 IF @Vstatus2 IS NULL THEN  SET @Vstatus2='-Ve';   END IF;
  IF @Vstatus1 IS NULL THEN  SET @Vstatus1='-Ve';   END IF;
  IF @VarinceOT IS NULL THEN  SET @VarinceOT=0;   END IF;
    IF @Varince2 IS NULL THEN  SET @Varince2=0;   END IF;
	
	IF @ExpectedTotalAmountWithArrears>0 THEN
	
INSERT INTO temp_dailySummuryCollection VALUES(1.2,"Loan Amounts In Arreas Only",@ExpectedTotalAmountWithArrears,@ActualTotalAmountCollectedToday,@Varince2,@Vstatus2);

END IF;


   SET @VarinceArrears=@ActualTotalAmountCollectedToday-@ExpectedTotalAmountToday;
   
/*     SELECT @VarinceArrears;  */
   IF @VarinceArrears>=0.0 THEN
   
   SET @Vstatus3='+Ve';
   
   SET @VarinceArrears=0.0;
   
   END IF;
   
   IF @VarinceArrears<0.0 THEN
   
   SET @Vstatus3='-Ve';
   
   SET @VarinceArrears=@VarinceArrears*-1;
 
   END IF;
   
   IF @Vstatus2 IS NULL THEN  SET @Vstatus2='-Ve';   END IF;
  IF @Vstatus3 IS NULL THEN  SET @Vstatus3='-Ve';   END IF;
  IF @VarinceArrears IS NULL THEN  SET @VarinceArrears=0;   END IF;
    IF @Varince3 IS NULL THEN  SET @Varince3=0;   END IF;
	
	IF @ExpectedTotalAmountToday>0 THEN

INSERT INTO temp_dailySummuryCollection VALUES(1.3,"Loan Amount Due Today Only",@ExpectedTotalAmountToday,@ActualTotalAmountCollectedToday,@VarinceArrears,@Vstatus3);

END IF;


   SET @VarinceOT=@ActualTotalAmountCollectedToday-@ExpectedOverallTotalAmountToday;
   /*    SELECT @VarinceOT; */
   IF @VarinceOT>=0.0 THEN
   
   SET @Vstatus1='+Ve';
   
   SET @VarinceOT=0.0;
   
   END IF;
   
   IF @VarinceOT<0.0 THEN
   
   SET @Vstatus1='-Ve';
   
   SET @VarinceOT=@VarinceOT*-1;
 
   END IF;
 IF @Vstatus2 IS NULL THEN  SET @Vstatus2='-Ve';   END IF;
  IF @Vstatus1 IS NULL THEN  SET @Vstatus1='-Ve';   END IF;
  IF @VarinceOT IS NULL THEN  SET @VarinceOT=0;   END IF;
    IF @Varince2 IS NULL THEN  SET @Varince2=0;   END IF;
	
		IF @ExpectedOverallTotalAmountToday>0 THEN
		
INSERT INTO temp_dailySummuryCollection VALUES(1.1,"Total Loan Collections",@ExpectedOverallTotalAmountToday,@ActualTotalAmountCollectedToday,@VarinceOT,@Vstatus1);

  END IF;
  
  
INSERT INTO temp_dailySummuryCollection VALUES(2,0,0,0,0,0);
INSERT INTO temp_dailySummuryCollection VALUES(2,"ACCOUNT SUMMURIES",0,0,0,0);



CALL OpeningCashBalance(theDate,@OpeningCahdBala);


INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Opening Cash Balance",0,0,@OpeningCahdBala,0);

CALL princimpalLoanRepaymentsMade(theDate,@princimpalRepaymentsMade);
/* 
SELECT @princimpalRepaymentsMade; */

SET @OpeningCahdBala=@OpeningCahdBala+@princimpalRepaymentsMade;

IF @princimpalRepaymentsMade>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Principal Loan Payments Collected",@princimpalRepaymentsMade,0,@OpeningCahdBala,0);
END IF;
CALL InterestRecover(theDate,@InterestR);

/* SELECT @InterestR; */

SET @OpeningCahdBala=@OpeningCahdBala+@InterestR;

IF @InterestR>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Interest Collected",@InterestR,0,@OpeningCahdBala,0);
END IF;


CALL ProcessingFeesCollected(theDate,@processingFees);

/* SELECT @processingFees; */

SET @OpeningCahdBala=@OpeningCahdBala+@processingFees;

IF @processingFees>0 THEN 

INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Processing Fees Collected",@processingFees,0,@OpeningCahdBala,0);
END IF;


CALL LedgerFees(theDate,@ledgerFessCol);
/* SELECT @ledgerFessCol; */

SET @OpeningCahdBala=@OpeningCahdBala+@ledgerFessCol;

IF @ledgerFessCol>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Ledger Fees Collected",@ledgerFessCol,0,@OpeningCahdBala,0);
END IF;





CALL MembershipFees(theDate,@memberShipFessCol);
/* SELECT @memberShipFessCol; */
SET @OpeningCahdBala=@OpeningCahdBala+@memberShipFessCol;

IF @memberShipFessCol>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Membership Fees Collected",@memberShipFessCol,0,@OpeningCahdBala,0);
END IF;

CALL annualSubFees(theDate,@annualFeesRecovered);



SET @OpeningCahdBala=@OpeningCahdBala+@annualFeesRecovered;

IF @annualFeesRecovered>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Annual Subscription Fees Collected",@annualFeesRecovered,0,@OpeningCahdBala,0);
END IF;


CALL BadDebtsRecovered(theDate,@badDebtsRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@badDebtsRecovered;

IF @badDebtsRecovered>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Bad Debts Recovered",@badDebtsRecovered,0,@OpeningCahdBala,0);
END IF;





CALL accumulatedInterestR(theDate,@accumulatedInterestR);

SET @OpeningCahdBala=@OpeningCahdBala+@accumulatedInterestR;

IF @accumulatedInterestR>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Accumulated Interest Collected",@accumulatedInterestR,0,@OpeningCahdBala,0);
END IF;

CALL loanPenaltyRecov(theDate,@loanPenaltyRecovered);

SET @OpeningCahdBala=@OpeningCahdBala+@loanPenaltyRecovered;

IF @loanPenaltyRecovered>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Loan Penalty Collected",@loanPenaltyRecovered,0,@OpeningCahdBala,0);
END IF;

CALL otherIncomesCollected(theDate,@otherIncomesCollectedX);

SET @OpeningCahdBala=@OpeningCahdBala+@otherIncomesCollectedX;

IF @otherIncomesCollectedX>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"All Other  Incomes Collected",@otherIncomesCollectedX,0,@OpeningCahdBala,0);
END IF;
 



 CALL SavingsDepositsMade(theDate,@savingsC); 

SET @OpeningCahdBala=@OpeningCahdBala+@savingsC;

IF @savingsC>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Savings/Deposits Made",@savingsC,0,@OpeningCahdBala,0);
END IF;


CALL PayablesCreated(theDate,@payableCreated);


SET @OpeningCahdBala=@OpeningCahdBala+@payableCreated;

IF @payableCreated>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"General Payables Received",@payableCreated,0,@OpeningCahdBala,0);
END IF;


CALL InsurancePayableMade(theDate,@insurancePayMade);

SET @OpeningCahdBala=@OpeningCahdBala+@insurancePayMade;

IF @insurancePayMade>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Insurance Payables Received",@insurancePayMade,0,@OpeningCahdBala,0);
END IF;



CALL otherLiabilitiesAndProvisionsMade(theDate,@OtherLiabilities);

SET @OpeningCahdBala=@OpeningCahdBala+@OtherLiabilities;

IF @OtherLiabilities>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Liabilities And Provisions Received",@OtherLiabilities,0,@OpeningCahdBala,0);
END IF;



CALL capitalisationMade(theDate,@capitalPayments);

SET @OpeningCahdBala=@OpeningCahdBala+@capitalPayments;

IF @capitalPayments>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Capital Contributions Made",@capitalPayments,0,@OpeningCahdBala,0);
END IF;

CALL OtherCapitalisationsAndReservesMade(theDate,@otheCapsAndReserversMade);

SET @OpeningCahdBala=@OpeningCahdBala+@otheCapsAndReserversMade;

IF @otheCapsAndReserversMade>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Capitalisations And Reserves Made",@otheCapsAndReserversMade,0,@OpeningCahdBala,0);
END IF;

CALL RecevablesRefunded(theDate,@Refundreceiavale);

SET @OpeningCahdBala=@OpeningCahdBala+@Refundreceiavale;

IF @Refundreceiavale>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Receivables Refunded",@Refundreceiavale,0,@OpeningCahdBala,0);
END IF;

CALL OtherReceivablesAndPrepaymentsRefunded(theDate,@otherReceiAndPrepaymentRend);

SET @OpeningCahdBala=@OpeningCahdBala+@otherReceiAndPrepaymentRend;

IF @otherReceiAndPrepaymentRend>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Receivables And Prepayments Refunded",@otherReceiAndPrepaymentRend,0,@OpeningCahdBala,0);
END IF;


CALL BankWithdrawsMade(theDate,@BankWithdrws);


SET @OpeningCahdBala=@OpeningCahdBala+@BankWithdrws;

IF @BankWithdrws>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Bank Withdraws Made",@BankWithdrws,0,@OpeningCahdBala,0);
END IF;


CALL refundFromMobileMoneyPayableMade(theDate,@mobileMoneyRefund);

SET @OpeningCahdBala=@OpeningCahdBala+@mobileMoneyRefund;

IF @mobileMoneyRefund>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Principal Loan Payments Collected",@mobileMoneyRefund,0,@OpeningCahdBala,0);
END IF;

CALL fixedAssetsAndInvestmentsDisposedOff(theDate,@fixedAssetsAndInvestmentDisp);

SET @OpeningCahdBala=@OpeningCahdBala+@fixedAssetsAndInvestmentDisp;

IF @fixedAssetsAndInvestmentDisp>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Fixed Assets Bought And Investments Made",@fixedAssetsAndInvestmentDisp,0,@OpeningCahdBala,0);
END IF;




CALL ExpensesMade(theDate,@ExpensesMa);

SET @OpeningCahdBala=@OpeningCahdBala-@ExpensesMa;

IF @ExpensesMa>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Total Expenses Made",0,@ExpensesMa,@OpeningCahdBala,0);
END IF;


CALL LoanDisbursementsMade(theDate,@loansDisbursed);

SET @OpeningCahdBala=@OpeningCahdBala-@loansDisbursed;

IF @loansDisbursed>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Loan Disbursements Made",0,@loansDisbursed,@OpeningCahdBala,0);
END IF;


CALL InterestWrittenOff(theDate,@interWriteOff);

SET @OpeningCahdBala=@OpeningCahdBala-@interWriteOff;

IF @interWriteOff>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Interest Written Off",0,@interWriteOff,@OpeningCahdBala,0);
END IF;

CALL accumuInteresWrittenOff(theDate,@accumuIntereWrittenOff);

SET @OpeningCahdBala=@OpeningCahdBala-@accumuIntereWrittenOff;

IF @accumuIntereWrittenOff>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Accumulated Interest Written Off",0,@accumuIntereWrittenOff,@OpeningCahdBala,0);
END IF;

CALL processingFeesWrittenOff(theDate,@processFeesWriteOff);

SET @OpeningCahdBala=@OpeningCahdBala-@processFeesWriteOff;

IF @processFeesWriteOff>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Processing Fees Written Off",0,@processFeesWriteOff,@OpeningCahdBala,0);
END IF;

CALL OtherIncomesWrittenOff(theDate,@otherIncomesWrOff);

SET @OpeningCahdBala=@OpeningCahdBala-@otherIncomesWrOff;

IF @otherIncomesWrOff>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Incomes Written Off",0,@otherIncomesWrOff,@OpeningCahdBala,0);
END IF;

CALL ReceivablesCreated(theDate,@receiavale);

SET @OpeningCahdBala=@OpeningCahdBala-@receiavale;

IF @receiavale>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Receivables Created",0,@receiavale,@OpeningCahdBala,0);
END IF;

CALL MobileMoneyReceivableCreated(theDate,@mobileMoney);

SET @OpeningCahdBala=@OpeningCahdBala-@mobileMoney;

IF @mobileMoney>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Mobile Money Receivable Made",0,@mobileMoney,@OpeningCahdBala,0);
END IF;

CALL OtherReceivablesAndPrepaymentsCreated(theDate,@otherRecePreMade);

SET @OpeningCahdBala=@OpeningCahdBala-@otherRecePreMade;

IF @otherRecePreMade>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Receivables And Prepayments Made",0,@otherRecePreMade,@OpeningCahdBala,0);
END IF;


CALL BankDepositsMade(theDate,@bankDepositMade);

SET @OpeningCahdBala=@OpeningCahdBala-@bankDepositMade;

IF @bankDepositMade>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Bank Deposits Made",0,@bankDepositMade,@OpeningCahdBala,0);
END IF;


CALL fixedAssetsAndInvestmentsAquired(theDate,@fixedAssetsAndInvestmentAquired);

SET @OpeningCahdBala=@OpeningCahdBala-@fixedAssetsAndInvestmentAquired;

IF @fixedAssetsAndInvestmentAquired>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Fixed Assets And Investments Aquired",0,@fixedAssetsAndInvestmentAquired,@OpeningCahdBala,0);
END IF;

CALL PayablesRefunded(theDate,@RefundPayable);

SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayable;

IF @RefundPayable>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Payables Refunded",0,@RefundPayable,@OpeningCahdBala,0);
END IF;

CALL PayablesOtherLiabilitiesAndProvisionsRefunded(theDate,@RefundPayableOtherLiabilProvis);

SET @OpeningCahdBala=@OpeningCahdBala-@RefundPayableOtherLiabilProvis;

IF @RefundPayableOtherLiabilProvis>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Payables Refunded And Other Liabilities And Provisions Cleared",0,@RefundPayableOtherLiabilProvis,@OpeningCahdBala,0);
END IF;

CALL InsurancePayableCleared(theDate,@insurancePayableCleared);

SET @OpeningCahdBala=@OpeningCahdBala-@insurancePayableCleared;

IF @insurancePayableCleared>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Insurance Payables Cleared",0,@insurancePayableCleared,@OpeningCahdBala,0);
END IF;

CALL DrawingsMade(theDate,@DrawingM);

SET @OpeningCahdBala=@OpeningCahdBala-@DrawingM;

IF @DrawingM>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Drawings Made",0,@DrawingM,@OpeningCahdBala,0);
END IF;

CALL DecapitalisationsMade(theDate,@Decapitlise);

SET @OpeningCahdBala=@OpeningCahdBala-@Decapitlise;

IF @Decapitlise>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Capital Removed",0,@Decapitlise,@OpeningCahdBala,0);
END IF;

CALL OtherEquitiesAndReservesRemoved(theDate,@equitiesReservesRemoved); 

SET @OpeningCahdBala=@OpeningCahdBala-@equitiesReservesRemoved;

IF @equitiesReservesRemoved>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Other Equities And Reserves Removed",0,@equitiesReservesRemoved,@OpeningCahdBala,0);
END IF;

 CALL SavingsDepositsWithdrawn(theDate,@savingDepositWith); 

SET @OpeningCahdBala=@OpeningCahdBala-@savingDepositWith;

IF @savingDepositWith>0 THEN 
INSERT INTO temp_dailySummuryCollection VALUES(2.1,"Savings Withdraws Made",0,@savingDepositWith,@OpeningCahdBala,0);
END IF;

SELECT temp_NarrationC ,temp_ExpectedCollection ,temp_ActualCollection ,temp_BalColl,temp_Variance FROM temp_dailySummuryCollection;


DROP TABLE temp_dailySummuryCollection;

END //

DELIMITER ;


CALL  grandSummryDailyReport('2019-07-01') ;





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
/* SELECT @sql_text; */
  EXECUTE stmt;
DROP PREPARE stmt;
/* SELECT @openBalance; */
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


SET @qr=CONCAT(CAST("CREATE TEMPORARY TABLE account_view AS SELECT chq_number FROM  pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_date='" AS CHAR CHARACTER SET utf8),theDate,CAST("'  AND other_one='Cr'" AS CHAR CHARACTER SET utf8));
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

DROP TABLE account_view;


SET outer_done=0;
 END LOOP ACCOUNTS_LOOP;
CLOSE cursor_forSelectingProcessingFeesAccounts;
/* SELECT @cash; */
SET processingFees=@cash;
END OUTER_BLOCK//

DELIMITER ;


/* CALL ProcessingFeesCollected('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */





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
/* SELECT @qr; */
PREPARE stmt FROM @qr;
EXECUTE stmt;
DROP PREPARE stmt;

OPEN cursor_forSelectingBatchNumbers; 
 
BATCH_LOOP:LOOP

FETCH cursor_forSelectingBatchNumbers INTO batchNumbersReps;


/* SELECT processindFeesAccountRep; */
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


/* 
CALL LedgerFees('2019-06-19',@ledgerFessCol);

SELECT @ledgerFessCol; */







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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET badDebtsRecover='0';
ELSE
SET badDebtsRecover=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL BadDebtsRecovered('2019-06-21',@badDebtsRecover1);

SELECT @badDebtsRecover1; */





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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET memberShipFessCol='0';
ELSE
SET memberShipFessCol=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL MembershipFees('2019-06-21',@memberShipFessCol);

SELECT @memberShipFessCol; */






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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET annualFeesRecovered='0';
ELSE
SET annualFeesRecovered=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL annualSubFees('2019-06-21',@annualFeesRecovered);

SELECT @annualFeesRecovered; */







DROP PROCEDURE IF EXISTS InterestRecover;
DELIMITER //
CREATE PROCEDURE InterestRecover(IN theDate DATE,OUT InterestR VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN


DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE account_number LIKE '03301%'; 
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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET InterestR='0';
ELSE
SET InterestR=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;




/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */


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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET accumulatedInterestR='0';
ELSE
SET accumulatedInterestR=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL accumulatedInterestR('2019-06-21',@accumulatedInterestR);

SELECT @accumulatedInterestR; */








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

/* SELECT processindFeesAccountRep;
SELECT batchNumbersReps; */
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
SET loanPenaltyRecovered='0';
ELSE
SET loanPenaltyRecovered=@cash;
END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL loanPenaltyRecov('2019-06-21',@loanPenaltyRecovered);

SELECT @loanPenaltyRecovered; */









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

/* SELECT processindFeesAccountRep; */
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

/* 
CALL otherIncomesCollected('2019-06-21',@otherIncomesCollectedX);

SELECT @otherIncomesCollectedX; */







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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/*END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/*END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit-@cashDebit;


 IF @cash IS NULL THEN
SET savingsDepositC='0';
ELSE

SET savingsDepositC=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL SavingsDepositsMade('2019-06-22',@savingsDepositC);

SELECT @savingsDepositC; */









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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/*END IF; */

/* IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/*END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET payableCreatedFinal='0';
ELSE

SET payableCreatedFinal=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL PayablesCreated('2019-06-22',@payableCreatedFinal);

SELECT @payableCreatedFinal; */










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

/* SELECT @openBalance;
SELECT @closingBalance; */

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
/* 
SELECT @cashBal;
 */

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


/* CALL InsurancePayableMade('2019-04-09',@insurancePayableMadeX);

SELECT @insurancePayableMade; */




DROP PROCEDURE IF EXISTS otherLiabilitiesAndProvisionsMade;

DELIMITER //

CREATE PROCEDURE otherLiabilitiesAndProvisionsMade(IN theDate DATE,OUT liabilityProvi VARCHAR(100)) READS SQL DATA 

OUTER_BLOCK: BEGIN

DECLARE processindFeesAccountRep VARCHAR(30); 
DECLARE outer_done INTEGER DEFAULT 0; 
DECLARE cursor_forSelectingProcessingFeesAccounts CURSOR FOR SELECT account_number  FROM pmms.account_created_store WHERE (account_number like '05504%' 
 OR account_number like '05501%' OR account_number like '05503%' OR account_number like '05505%'  OR account_number like '05509%' OR account_number like '05506%' OR account_number like '05507%'
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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/*/*END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/*END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET liabilityProvi='0';
ELSE

SET liabilityProvi=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL otherLiabilitiesAndProvisionsMade('2019-06-22',@liabilityProvi);

SELECT @liabilityProvi; */







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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET someEquity='0';
ELSE

SET someEquity=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL capitalisationMade('2019-06-22',@someEquity);

SELECT @someEquity; */






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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET otheCapsAndReserversMade='0';
ELSE

SET otheCapsAndReserversMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;

/* CALL OtherCapitalisationsAndReservesMade('2019-06-22',@otheCapsAndReserversMade);


SELECT @otheCapsAndReserversMade; */






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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;
/* 
SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET Refundreceiavale='0';
ELSE

SET Refundreceiavale=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;
/* 

CALL RecevablesRefunded('2019-06-23',@Refundreceiavale); */

/* SELECT @Refundreceiavale; */




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



/* 
IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET otherReceiAndPrepaymentRend='0';
ELSE

SET otherReceiAndPrepaymentRend=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsRefunded('2019-06-23',@otherReceiAndPrepaymentRend); */

/* SELECT @otherReceiAndPrepaymentRend; */




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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET BankWithdrws='0';
ELSE

SET BankWithdrws=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

/* SELECT @BankWithdrws; */








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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET BankWithdrws='0';
ELSE

SET BankWithdrws=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL BankWithdrawsMade('2019-06-23',@BankWithdrws);

SELECT @BankWithdrws; */



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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET princimpalRepaymentsMade='0';
ELSE

SET princimpalRepaymentsMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL princimpalLoanRepaymentsMade('2019-06-23',@princimpalRepaymentsMade);

SELECT @princimpalRepaymentsMade; */





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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET mobileMoneyRefund='0';
ELSE

SET mobileMoneyRefund=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL refundFromMobileMoneyPayableMade('2019-06-23',@mobileMoneyRefund);

SELECT @mobileMoneyRefund; */




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




/* IF @cashCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */

/* END IF;

IF @amountCredit>0 THEN

/* SELECT processindFeesAccountRep,@amountCredit,@cashCredit; */

/* END IF; */

SET @quary=CONCAT(CAST("SELECT debit INTO @amountDebit FROM pmms.bsanca" AS CHAR CHARACTER SET utf8),processindFeesAccountRep,CAST("  WHERE trn_id='" AS CHAR CHARACTER SET utf8),batchNumbersReps,CAST("'" AS CHAR CHARACTER SET utf8));
PREPARE stm FROM @quary;
EXECUTE stm;
DEALLOCATE PREPARE stm;

/* SELECT batchNumbersReps; */

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
/* SELECT @cashCredit,@cashDebit; */
SET @cash =@cashCredit;


 IF @cash IS NULL THEN
SET fixedAssetsAndInvestmentDisp='0';
ELSE

SET fixedAssetsAndInvestmentDisp=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL fixedAssetsAndInvestmentsDisposedOff('2019-06-23',@fixedAssetsAndInvestmentDisp);

SELECT @fixedAssetsAndInvestmentDisp; */





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

SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */




/* IF @amountCredit>0 THEN

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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET ExpensesMa='0';
ELSE

SET ExpensesMa=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL ExpensesMade('2019-06-23',@ExpensesMa);

SELECT @ExpensesMa; */






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

SELECT processindFeesAccountRep,@amountDebit,@cashDebit; */



/* 
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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET interWriteOff='0';
ELSE

SET interWriteOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;




/* CALL InterestWrittenOff('2019-06-24',@interWriteOff);

SELECT @interWriteOff; */



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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET accumuIntereWrittenOff='0';
ELSE

SET accumuIntereWrittenOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;



/* 
CALL accumuInteresWrittenOff('2019-06-24',@accumuIntereWrittenOff);

SELECT @accumuIntereWrittenOff; */





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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET processFeesWriteOff='0';
ELSE

SET processFeesWriteOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* 

CALL processingFeesWrittenOff('2019-06-24',@processFeesWriteOff);

SELECT @processFeesWriteOff; */






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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET otherIncomesWrOff='0';
ELSE

SET otherIncomesWrOff=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;



/* 
CALL OtherIncomesWrittenOff('2019-06-24',@otherIncomesWrOff);

SELECT @otherIncomesWrOff; */





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
/* 
SELECT @batch; */

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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET receiavale='0';
ELSE

SET receiavale=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL ReceivablesCreated('2019-06-24',@receiavale);

SELECT @receiavale; */




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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET mobileMoneyc='0';
ELSE

SET mobileMoneyc=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL MobileMoneyReceivableCreated('2019-06-24',@mobileMoneyc);

SELECT @mobileMoneyc; */







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

END IF;
 */


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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET otherRecePreMade='0';
ELSE

SET otherRecePreMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL OtherReceivablesAndPrepaymentsCreated('2019-06-24',@otherRecePreMade);

SELECT @otherRecePreMade; */








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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET bankDepositMade='0';
ELSE

SET bankDepositMade=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL BankDepositsMade('2019-06-24',@bankDepositMade);

SELECT @bankDepositMade; */





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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET loansDisbursed='0';
ELSE

SET loansDisbursed=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL LoanDisbursementsMade('2019-06-24',@loansDisbursed);

SELECT @loansDisbursed; */




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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET fixedAssetsAndInvestmentAquired='0';
ELSE

SET fixedAssetsAndInvestmentAquired=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL fixedAssetsAndInvestmentsAquired('2019-06-24',@fixedAssetsAndInvestmentAquired);

SELECT @fixedAssetsAndInvestmentAquired; */




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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET RefundPayable='0';
ELSE

SET RefundPayable=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL PayablesRefunded('2019-06-24',@RefundPayable);

SELECT @RefundPayable; */






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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET RefundPayableOtherLiabilProvis='0';
ELSE

SET RefundPayableOtherLiabilProvis=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL PayablesOtherLiabilitiesAndProvisionsRefunded('2019-06-24',@RefundPayableOtherLiabilProvis);

SELECT @RefundPayableOtherLiabilProvis; */







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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET insurancePayCleared='0';
ELSE

SET insurancePayCleared=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL InsurancePayableCleared('2019-06-24',@insurancePayCleared);

SELECT @insurancePayCleared; */






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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET DrawingM='0';
ELSE

SET DrawingM=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL DrawingsMade('2019-06-24',@DrawingM);

SELECT @DrawingM; */





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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET Decapitlise='0';
ELSE

SET Decapitlise=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;

/* 
CALL DecapitalisationsMade('2019-06-24',@Decapitlise);

SELECT @Decapitlise; */






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
SET @cash =@cashDebit;


 IF @cash IS NULL THEN
SET equitiesReservesRemoved='0';
ELSE

SET equitiesReservesRemoved=@cash;


END IF;


END OUTER_BLOCK//

DELIMITER ;


/* CALL OtherEquitiesAndReservesRemoved('2019-06-24',@equitiesReservesRemoved);

SELECT @equitiesReservesRemoved; */





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


END OUTER_BLOCK //

DELIMITER ;


/* CALL SavingsDepositsWithdrawn('2019-06-24',@savingDepositWith);

SELECT @savingDepositWith; */



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



/*==============================================================================MONEY LENDER'S REPORTS AND STATEMENTS=======================================================*/

/*==================================CHANGING THE DUE DATE=====================================================*/

/* 
CREATE TABLE interestcomputed (
  TrnId int(11) NOT NULL AUTO_INCREMENT,
  loanId varchar(45) DEFAULT '0',
  DueDate date NOT NULL DEFAULT '1970-01-01',
  PrincimpalInvolved double DEFAULT NULL,
  InterestInvolved double DEFAULT NULL,
  loanStatusI varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (TrnId),
  UNIQUE KEY TrnId_UNIQUE (TrnId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1; */





/*============================GENERATING THE MONEY LENDER'S REPORT=======================================================*/




DROP TABLE IF EXISTS interestcomputed;

CREATE TABLE interestcomputed (
  TrnId int(11) NOT NULL AUTO_INCREMENT,
  loanId varchar(45) DEFAULT '0',
  DueDate date NOT NULL DEFAULT '1970-01-01',
  PrincimpalInvolved double DEFAULT NULL,
  InterestInvolved double DEFAULT NULL,
  InterestPaidIn double DEFAULT NULL,
  InterestInvoRemaining double DEFAULT NULL,
    totalInterstInv double DEFAULT NULL,
	totalInterstInvPaid double DEFAULT NULL,
	totalInterstInvRemaing double DEFAULT NULL,
  loanStatusI varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (TrnId),
  UNIQUE KEY TrnId_UNIQUE (TrnId)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;





DROP PROCEDURE IF EXISTS changeDueDate;

DELIMITER //

CREATE PROCEDURE changeDueDate() READS SQL DATA BEGIN

 DECLARE loanId,theTenure VARCHAR(30);

  /* DECLARE numberOfIds INTEGER; */

 DECLARE l_done INTEGER DEFAULT 0;

 DECLARE originalDueDate DATE;
/* lastDate, */
  /* DECLARE InterestRate,princinpalRemaining,totalComputedInterest Double; */

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed';
 

 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;


 OPEN forSelectingLoanIds;


accounts_loop: LOOP 

 FETCH forSelectingLoanIds into loanId;

 /* SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanId; */
SELECT loan_tenure INTO theTenure FROM new_loan_appstore WHERE loan_id=loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;


IF theTenure= '1.0 MONTHS' THEN 

SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanId;

/* SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanId; */


UPDATE new_loan_appstore SET instalment_next_due_date=originalDueDate WHERE loan_id=loanId;

UPDATE new_loan_appstore1 SET instalment_next_due_date=originalDueDate WHERE loan_id=loanId;

END IF;


SET l_done=0;

 END LOOP accounts_loop;



 CLOSE forSelectingLoanIds;

END //

DELIMITER ;

CALL changeDueDate();








DROP PROCEDURE IF EXISTS InterestManagementForLendersNonCompounded;

DELIMITER //

CREATE PROCEDURE InterestManagementForLendersNonCompounded() READS SQL DATA BEGIN

 DECLARE loanIdZ,theTenure VARCHAR(30); /* This is the unique itentfier for an active loan and the tenure type which helps us to select only single instalment loans to be managed*/


 DECLARE l_done INTEGER DEFAULT 0; /*  Variable controlling the cusor*/

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/
/*  The variable originalDueDate holds the value of the original due date since the last transaction*/
  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,totalInterest,
  totalCompuInteresRemaing Double; /*  InterestRate is the rate used---*/

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed'; /* cursor for iterating through each borrower's account*/
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;/*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingLoanIds; /* Open the cursor holding loan ids for each customer*/


accounts_loop: LOOP /*Loop through the loanIds */

 FETCH forSelectingLoanIds into loanIdZ;/*Pick the loan id into the variable loanIdz */
-- SELECT loanIdZ;
 SELECT loan_tenure INTO theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ;/*Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 IF l_done=1 THEN/*check whether the cusor sitll holds more values and if not terminate loop*/

LEAVE accounts_loop;

 END IF;


IF theTenure= '1.0 MONTHS' THEN /*  Only single monthly isntalment loans should be considered*/

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdZ; /* The due date since the last instalment is stored in the instalment_next_due_date column*/
-- SELECT  lastDate,InterestRate,princinpalRemaining;
SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;/* The instalment due date is the last due date*/

SELECT interestinvoRemaining INTO totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;/* The last interest computed*/

SELECT totalInterest ,loanIdZ;

Date_loop: LOOP /* Loop through the due dates since the last duedate*/
SET @Ended=0;
/*  SELECT lastDate;  *//*Testing*/


IF lastDate>=current_date() THEN /* Test whether the arrears last date is more than today's date*/

SET @Ended=1;

LEAVE Date_loop;

END IF;


IF @Ended=0 THEN 

SET interestInveolved=((InterestRate*princinpalRemaining)/1200);/* Compute the insterest using the remaining princimpal amount*/

SET totalInterest=totalInterest+interestInveolved; /* Compute the total interest*/





SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;


CALL updateAccountsAfter(loanIdZ,lastDate,interestInveolved,princinpalRemaining);/* Update the original loan schedule*/


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,princinpalRemaining,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest,'Pending'); 

END IF;

END LOOP Date_loop;

END IF;



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

END //

DELIMITER ;

/* 

DROP EVENT IF EXISTS `change_daily_interest`;

CREATE  EVENT `change_daily_interest` ON SCHEDULE EVERY 10 SECOND STARTS CURRENT_TIMESTAMP() ON COMPLETION NOT PRESERVE ENABLE DO CALL InterestManagementForLendersCompounded(); */

DROP PROCEDURE IF EXISTS InterestManagementForLenders;


DROP PROCEDURE IF EXISTS InterestManagementForLendersCompounded;

DELIMITER //

CREATE PROCEDURE InterestManagementForLendersCompounded() READS SQL DATA BEGIN

 DECLARE loanIdZ VARCHAR(30); /* This is the unique itentfier for an active loan */


 DECLARE l_done INTEGER DEFAULT 0; /*  Variable controlling the cusor*/

 DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction*/

  DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,TotalprincinpalRemainingX,TotalinterestRemainingX,NEWTotalprincinpalRemainingX,totalInterest,
  totalCompuInteresRemaing Double; /*  InterestRate is the rate used---*/

 DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed'; /* cursor for iterating through each borrower's account*/
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;/*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingLoanIds; /* Open the cursor holding loan ids for each customer*/


accounts_loop: LOOP /*Loop through the loanIds */

 FETCH forSelectingLoanIds into loanIdZ;/*Pick the loan id into the variable loanIdz */
-- SELECT loanIdZ;
 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ;/*Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 IF l_done=1 THEN/*check whether the cusor sitll holds more values and if not terminate loop*/

LEAVE accounts_loop;

 END IF;


IF @theTenure= '1.0 MONTHS' THEN /*  Only single monthly isntalment loans should be considered*/



Date_loop: LOOP /* Loop through the due dates since the last duedate*/
SET @Ended=0;
/*  SELECT lastDate;  *//*Testing*/

SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining,TotalInterestRemaining INTO lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX FROM new_loan_appstore WHERE loan_id=loanIdZ; /* The due date since the last instalment is stored in the instalment_next_due_date column*/
-- SELECT  lastDate,InterestRate,TotalprincinpalRemaining;
SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;/* The instalment due date is the last due date*/

SELECT interestinvoRemaining INTO totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;/* The last interest computed*/

SELECT totalInterest ,loanIdZ,lastDate,InterestRate,TotalprincinpalRemainingX,TotalinterestRemainingX;

IF lastDate>=current_date() THEN /* Test whether the arrears last date is more than today's date*/



SET @Ended=1;

LEAVE Date_loop;

END IF;


INSERT INTO new_loan_appstore2 SELECT * FROM new_loan_appstore WHERE loan_id=loanIdZ;

INSERT INTO new_loan_appstoreamort2 SELECT * FROM new_loan_appstoreamort WHERE master2_id=loanIdZ;

IF @Ended=0 THEN 

SET @computableAmount=TotalprincinpalRemainingX+TotalinterestRemainingX;

SET interestInveolved=((InterestRate*@computableAmount)/1200); /* Compute the insterest using the remaining princimpal amount*/



SET totalInterest=totalInterest+interestInveolved; /* Compute the total interest*/


-- SET NEWTotalprincinpalRemainingX=@computableAmount;


SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;


CALL updateAccountsAfterCompounded(loanIdZ,lastDate,interestInveolved,TotalprincinpalRemainingX,TotalinterestRemainingX);/* Update the original loan schedule*/


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,TotalprincinpalRemainingX,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest,'Pending'); 

END IF;

END LOOP Date_loop;

END IF;



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

END //

DELIMITER ;



-- DROP EVENT IF EXISTS `change_daily_balances`;
-- CREATE  EVENT `change_daily_balances` ON SCHEDULE EVERY 24 HOUR STARTS CURRENT_TIMESTAMP() ON COMPLETION NOT PRESERVE ENABLE DO CALL changeBalance(CURRENT_TIMESTAMP());

-- DROP EVENT IF EXISTS `manage_interest`;
-- CREATE  EVENT `manage_interest` ON SCHEDULE EVERY 20 MINUTE STARTS CURRENT_TIMESTAMP() ON COMPLETION NOT PRESERVE ENABLE DO CALL manage_interest(CURRENT_TIMESTAMP());






DROP PROCEDURE IF EXISTS updateAccountsAfterCompounded;

DELIMITER //

/* This procedure updates the original loan schedule*/

CREATE PROCEDURE updateAccountsAfterCompounded(IN loanId VARCHAR(30),IN newDateDue DATE,IN interestComputed Double,IN princimpalRemaining Double,IN originalInterest Double) READS SQL DATA BEGIN

/* IN loanId VARCHAR(30)=This is the loan id that identifies each active loan uniquely */
/* IN newDateDue DATE=This is the Due date for the newly created interest instalment */
/* IN interestComputed Double=This is the interest generated for the new due date */
/* IN princimpalRemaining Double=This is the remaining principal amount */

 /* SELECT loanId,interestComputed,princimpalRemaining,newDateDue; For testing purposes to see the value of interestcomputed passed*/

/* The idea is to get the already existing values for the initial interest instalment,the actual instalment remaining,interest remaining,intial instalmet then we change these values with the interest computed */

SELECT 'AM IN=',loanId,interestComputed;




SET @sql_text1 = concat(CAST("SELECT princimpal_amount,interest_amount,instalment_amount,InterestRemaing,PrincipalRemaining,InstalmentRemaining INTO
 @principalAmount,
 @interestAmount, 
   @instalmentAmount,
 @interestRemaining,

   @principalRemaining,
    @instalmentRemaining
 FROM new_loan_appstoreamort WHERE master2_id='" AS CHAR CHARACTER SET utf8),loanId, CAST("'" AS CHAR CHARACTER SET utf8));
/* interest_amount=Initial interest instalment created at disbursement =@interestAmount*/
/* InstalmentRemaining=The total instalment remaining after paying off the loan instalment=@instalmentRemaining */
/*InterestRemaing=  The remaining interest when the instalment is paid,mostly when partly paid=@interestRemaining*/
/*instalment_amount=  The initial instalment amount */
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

/* Also we need to change the total interest,total instalment amount,totalinterest remaining */


SELECT princimpal_amount,total_interest, total_loanAmount,balance_due,instalment_amount,TotalInterestRemaining,TotalPrincipalRemaining INTO 
@TotalprincipalAmount,
 @totalInterestAmount,
   @totalLoanAmount,
   @balanceDue,
   @instalmentAmount,
  
@totalInterestRemaining ,
@totalPrincipalRemaining

FROM new_loan_appstore WHERE loan_id=loanId;

SELECT TrnId,InterestBalance, LoanBalance INTO @idL, @intBal,@lBalance FROM pmms.loandisburserepaystatement WHERE LoanId=loanId AND LoanStatusReport='Running' ORDER BY TrnId DESC LIMIT 1;


/* Normally if the result set is empty, mysql will return an empty set with null values for each element,

 to avoid null pointers we initalise our variables with expected default values */
 
IF  ISNULL (@principalAmount) THEN 

SET @principalAmount=0; 

END IF;/*/ */

IF   ISNULL(@interestAmount) THEN SET  @interestAmount=0; END IF;/* /*/

IF   ISNULL(@instalmentAmount) THEN SET   @instalmentAmount=0; END IF;/*/ */

IF   ISNULL(@interestRemaining) THEN SET  @interestRemaining=0; END IF;/* /*/

IF ISNULL(@principalRemaining) THEN SET @principalRemaining0; END IF;/* /*/

IF  ISNULL(@instalmentRemaining) THEN SET @instalmentRemaining=0; END IF;/*/ */

IF  ISNULL(@TotalprincipalAmount) THEN SET @TotalprincipalAmount=0; END IF;/* /*/

IF  ISNULL(@totalInterestAmount) THEN SET @totalInterestAmount=0; END IF;/* /*/

IF  ISNULL(@totalLoanAmount) THEN SET  @totalLoanAmount=0; END IF;/* /*/

IF  ISNULL(@balanceDue) THEN SET  @balanceDue=0; END IF;/*/ */

IF   ISNULL(@instalmentAmount) THEN SET  @instalmentAmount=0; END IF;/*/ */


IF   ISNULL(@totalInterestRemaining) THEN SET  @totalInterestRemaining=0; END IF;/* */

IF   ISNULL(@totalInterestRemaining) THEN SET  @totalPrincipalRemaining=0; END IF;/* */

IF ISNULL(@intBal) THEN SET @intBal=0; END IF;

IF ISNULL(@lBalance) THEN SET @lBalance=0; END IF;

-- @principalAmount = @principalAmount + originalInterest,
-- + originalInterest
SET @principalAmount = @principalAmount ,

@interestAmount = @interestAmount + interestComputed,

 @instalmentAmount= princimpalRemaining + interestComputed+originalInterest,

@interestRemaining = @interestRemaining + interestComputed,

-- @interestRemaining =  interestComputed,

@principalRemaining = princimpalRemaining,

@instalmentRemaining = princimpalRemaining + interestComputed+originalInterest,

-- @instalmentRemaining = princimpalRemaining + interestComputed,
-- + originalInterest
@TotalprincipalAmount = @TotalprincipalAmount ,

@totalInterestAmount = @totalInterestAmount + interestComputed,

-- @totalInterestAmount =  interestComputed,

 @totalLoanAmount = @totalLoanAmount + originalInterest + interestComputed,

--  @totalLoanAmount = @totalLoanAmount + interestComputed,

 
@balanceDue = princimpalRemaining + interestComputed+originalInterest,

-- @balanceDue = princimpalRemaining + interestComputed,

@instalmentAmount=@instalmentRemaining,

@totalInterestRemaining =  interestComputed+ originalInterest,

@totalPrincipalRemaining = princimpalRemaining,

@intBal = @intBal +,@lBalance=;

/* SET @totalInstalmentsRemaining=@totalInstalmentsRemaining+interestComputed,
@totalInterestRemaining=@totalInterestRemaining+interestComputed,
@totalInterestAmount=@totalInterestAmount+interestComputed; */


 SELECT @principalAmount,@interestAmount, @instalmentAmount,@interestRemaining,@principalRemaining,@instalmentRemaining,@TotalprincipalAmount,@totalInterestAmount, @totalLoanAmount,@balanceDue,@instalmentAmount ,@totalInterestRemaining,@totalPrincipalRemaining ;

-- princimpal_amount=@principalAmount,
UPDATE new_loan_appstoreamort SET 

interest_amount=@interestAmount,
instalment_amount=@instalmentAmount,
InterestRemaing=@interestRemaining,

InstalmentRemaining=@instalmentRemaining 
 WHERE master2_id=loanId;

-- princimpal_amount=@TotalprincipalAmount,
-- total_interest=@totalInterestAmount,
-- PrincipalRemaining=@principalRemaining,
UPDATE new_loan_appstore SET 
total_interest=@totalInterestAmount,
instalment_next_due_date=newDateDue,
total_loanAmount=@totalLoanAmount,
balance_due=@balanceDue,
instalment_amount=@instalmentAmount,
TotalInterestRemaining =@totalInterestRemaining


 WHERE loan_id=loanId;

-- TotalPrincipalRemaining=@totalPrincipalRemaining


-- princimpal_amount=@TotalprincipalAmount,
-- total_interest=@totalInterestAmount,

UPDATE new_loan_appstore1 SET 

instalment_next_due_date=newDateDue,
total_loanAmount=@totalLoanAmount,
balance_due=@balanceDue,
instalment_amount=@instalmentAmount,
TotalInterestRemaining =@totalInterestRemaining,
total_interest=@totalInterestAmount
 WHERE loan_id=loanId;

-- TotalPrincipalRemaining=@totalPrincipalRemaining

END //

DELIMITER ;







DROP PROCEDURE IF EXISTS newDateConverted;

DELIMITER //

CREATE PROCEDURE newDateConverted(INOUT  oldNewDate DATE) READS SQL DATA BEGIN
DECLARE D1,D2,D3,convertedDate VARCHAR(10);


SET D1=SUBSTRING(oldNewDate,1,4);
SET D2=SUBSTRING(oldNewDate,6,2);
SET D3=SUBSTRING(oldNewDate,9,2);
/* SELECT oldNewDate; */
IF D3>=28 THEN
SET D3=28;
END IF;

IF D2=12 THEN
SET D1=D1+1;
SET D2='01';
SET convertedDate=CONCAT(D1,'-',D2,'-',D3);
SET oldNewDate=CAST(convertedDate AS DATE);
ELSE
SET D2=D2+1;
SET convertedDate=CONCAT(D1,'-',D2,'-',D3);
SET oldNewDate=CAST(convertedDate AS DATE);
END IF;


/* SELECT oldNewDate; */
END //

DELIMITER ;



DROP PROCEDURE IF EXISTS DateManagementForLenders;

DELIMITER //

CREATE PROCEDURE DateManagementForLenders(IN InterestPaid Double,IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN /*One of the most important idea to note is that when a payment interest instalment is made, the interest due date has to be shifted one date ahead*/

DECLARE numberOfIds INTEGER;


 SELECT loan_tenure INTO @theTenure FROM new_loan_appstore WHERE loan_id=loanIdUsed;

IF @theTenure= '1.0 MONTHS' THEN

SELECT interest_rate,TotalPrincipalRemaining INTO @InterestRate,@princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdUsed;

SELECT instalment_due_date INTO @originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;


interestPayment_Loop: LOOP


SELECT TrnId,InterestInvoRemaining, InterestPaidIn,InterestInvolved INTO 
@theId,
@interestRemaining,
@interestPaidnow,
@InterestInveo 
FROM interestcomputed WHERE loanId=loanIdUsed AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1;


 SET @inteDiff=InterestPaid-@interestRemaining;
 
 IF @inteDiff<=0 THEN 
 

 
 
 IF @inteDiff=0 THEN
 
/*  SET @A=10;
  SELECT @A; */
 CALL newDateConverted(@originalDueDate);
 
 UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

 SET InterestPaid=0.0;
 
 ELSEIF @inteDiff<0 THEN
/*  SET @B=10;
  SELECT @B; */
 SET @IntRemai=@interestRemaining-InterestPaid;
 SET @intPai=@interestPaidnow+InterestPaid;
  
 UPDATE interestComputed SET InterestInvoRemaining=@IntRemai,InterestPaidIn=@intPai WHERE TrnId=@theId;

 SET InterestPaid=0.0;
 
 END IF;
 
 ELSEIF @inteDiff>0 THEN
 /* SET @C=10;
  SELECT @C; */
 SET  @IntRemai=@interestRemaining-InterestPaid;
 SET @intPai=@interestPaidnow+InterestPaid;
  
 UPDATE interestComputed SET InterestInvoRemaining=0.0,InterestPaidIn=@InterestInveo,loanStatusI='Paid' WHERE TrnId=@theId;

 SET InterestPaid=InterestPaid-@interestRemaining;
 CALL newDateConverted(@originalDueDate);
 
 END IF;






IF InterestPaid<=0.0 THEN

/* 
  SELECT @originalDueDate; */
UPDATE new_loan_appstoreamort SET instalment_due_date=@originalDueDate WHERE master2_id=loanIdUsed;

LEAVE interestPayment_Loop;

END IF;


END LOOP interestPayment_Loop;

END IF;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS stopInterestRate;

DELIMITER //

CREATE PROCEDURE stopInterestRate(IN loanIdUsed VARCHAR(30)) READS SQL DATA BEGIN

DECLARE numberOfIds INTEGER;

SELECT COUNT(instalment_no)  INTO numberOfIds FROM new_loan_appstoreamort WHERE master2_id=loanIdUsed;

IF numberOfIds=1 THEN



UPDATE new_loan_appstore SET interest_rate= '0.0' WHERE loan_id=loanIdUsed;
UPDATE new_loan_appstore1 SET interest_rate= '0.0' WHERE loan_id=loanIdUsed;

END IF;


END //

DELIMITER ;



DROP PROCEDURE IF EXISTS createFirstInterestIntalment;

DELIMITER //

CREATE PROCEDURE createFirstInterestIntalment(IN loanIdUsed VARCHAR(30),IN dueDate DATE,IN princinpalRemaining DOUBLE,IN interestInvo DOUBLE) READS SQL DATA BEGIN

INSERT INTO interestComputed VALUES(null,loanIdUsed,dueDate,princinpalRemaining,interestInvo,0.0,interestInvo,interestInvo,0.0,interestInvo,'Pending'); 


END //

DELIMITER ;








DROP PROCEDURE IF EXISTS selectctionInterestComputed;

DELIMITER //

CREATE PROCEDURE selectctionInterestComputed(IN loanIdUsed VARCHAR(30)) READS SQL DATA 

OUTER_BLOCK: BEGIN

SELECT  instalment_start_date INTO @theDueDate FROM new_loan_appstore where loan_id=loanIdUsed;

DROP TABLE IF EXISTS temp_computedInterestWhole;

CREATE  TEMPORARY TABLE temp_computedInterestWhole(id INTEGER,temp_DueDate DATE,temp_princimpalInvolved DOUBLE,temp_InterestInvo DOUBLE,temp_InterestPaid DOUBLE,temp_InterestRemain DOUBLE,temp_RuningBalInterest DOUBLE,temp_RuningBalLoanAmount DOUBLE);
 

INNER_BLOCK: BEGIN

 DECLARE inner_done INTEGER DEFAULT 0; 
 
  DECLARE theIds INTEGER DEFAULT 0; 
  
DECLARE cursor_For_Ids CURSOR FOR SELECT TrnId FROM interestcomputed WHERE (DueDate>=@theDueDate AND loanId=loanIdUsed);
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET inner_done=1;

SELECT COUNT(TrnId) INTO @numberOfIds FROM interestcomputed WHERE (DueDate>=@theDueDate AND loanId=loanIdUsed);

OPEN cursor_For_Ids; 
 
 
 SET @n=0;
  SET @runningAmount=0;
  SET  @runningInterst=0;
ID_LOOP:LOOP

FETCH cursor_For_Ids INTO theIds;


SET @n=@n+1;

IF theIds IS NULL THEN
SET theIds=0;
END IF;


/* SELECT batchNumbersReps; */
 IF inner_done=1 THEN
LEAVE ID_LOOP;
 END IF;
 
 SELECT DueDate,PrincimpalInvolved,InterestInvolved,InterestPaidIn,InterestInvoRemaining INTO
@Dated,
@PriInvo,
@InteresInvo,
@interePaid,
@intRemai FROM interestComputed WHERE TrnId=theIds;

 IF @PriInvo IS NULL THEN
SET @PriInvo=0;
END IF;

 IF @InteresInvo IS NULL THEN
SET @InteresInvo=0;
END IF;

 IF @interePaid IS NULL THEN
SET @interePaid=0;
END IF;

 IF @intRemai IS NULL THEN
SET @intRemai=0;
END IF;

/* SELECT @n,@numberOfIds; */
IF @n=@numberOfIds THEN 

SET @runningAmount=@runningAmount+@intRemai+@PriInvo;

ELSE

SET @PriInvo=0;

SET @runningAmount=@runningAmount+@intRemai+@PriInvo;

END IF;


SET @runningInterst=@runningInterst+@intRemai;


INSERT INTO temp_computedInterestWhole VALUES(@n,@Dated,@PriInvo,@InteresInvo,@interePaid,@intRemai,@runningInterst,@runningAmount);

END LOOP ID_LOOP; 

SET inner_done=0;

CLOSE cursor_For_Ids; 

END INNER_BLOCK;

SELECT * FROM temp_computedInterestWhole;
DROP TABLE temp_computedInterestWhole;
END OUTER_BLOCK //

DELIMITER ;


CALL  selectctionInterestComputed('newloan05502000110');






--  INSERT INTO the_company_datails VALUES(NULL,'EDU TRINITY INVESTMENTS SMC LIMITED','MPERERWE BRANCH','P.O BOX 28886 KAMPALA UGANDA',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolioPaid;

DELIMITER ##

CREATE FUNCTION loanPortfolioPaid(userId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select SUM(instalments_paid) INTO portFolio FROM new_loan_appstore where gruop_id=userId AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;





/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS userName;
DELIMITER ##
CREATE FUNCTION userName(userId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE userNameNow VARCHAR(40);

SELECT account_name INTO userNameNow FROM pmms.log_in WHERE  user_id=userId ;

IF ISNULL(userNameNow) THEN 
SET userNameNow="MISSING";
END IF;

RETURN userNameNow;
END ##
DELIMITER ;





/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolio;

DELIMITER ##

CREATE FUNCTION loanPortfolio(userId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select SUM(total_loanAmount) INTO portFolio FROM new_loan_appstore where gruop_id=userId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolioRemaining;

DELIMITER ##

CREATE FUNCTION loanPortfolioRemaining(userId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select SUM(balance_due) INTO portFolio FROM new_loan_appstore where gruop_id=userId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS customerName;
DELIMITER ##
CREATE FUNCTION customerName(loanId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE customerNameNow VARCHAR(40);

SELECT applicant_account_name INTO customerNameNow FROM new_loan_appstore WHERE  loan_id=loanId  AND loan_cycle_status='Disbursed';

RETURN customerNameNow;
END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS customeAccount;
DELIMITER ##
CREATE FUNCTION customeAccount(loanId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE customerAccountNow VARCHAR(40);

SELECT applicant_account_number INTO customerAccountNow FROM new_loan_appstore WHERE  loan_id=userId  AND loan_cycle_status='Disbursed';

RETURN customerAccountNow;
END ##
DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolioId;

DELIMITER ##

CREATE FUNCTION loanPortfolioId(loanId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select total_loanAmount INTO portFolio FROM new_loan_appstore where loan_id=loanId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;





/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolioPaidId;

DELIMITER ##

CREATE FUNCTION loanPortfolioPaidId(loanId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select instalments_paid INTO portFolio FROM new_loan_appstore where gruop_id=loanId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS loanPortfolioRemainingId;

DELIMITER ##

CREATE FUNCTION loanPortfolioRemainingId(loanId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC

BEGIN

DECLARE portFolio DOUBLE;

select balance_due INTO portFolio FROM new_loan_appstore where gruop_id=loanId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS numberOfLoans;

DELIMITER ##

CREATE FUNCTION numberOfLoans(userId INT) 
RETURNS INT
DETERMINISTIC

BEGIN

DECLARE portFolio INT;

select COUNT(balance_due) INTO portFolio FROM new_loan_appstore where gruop_id=userId  AND loan_cycle_status='Disbursed';

RETURN portFolio;

END ##
DELIMITER ;



/* NUMBER OF DAYS IN ARREARS FUNCTION */
DROP FUNCTION IF EXISTS numberOfDayInArrears;

DELIMITER ##

CREATE FUNCTION numberOfDayInArrears( loanId VARCHAR(45)) 
RETURNS INT
DETERMINISTIC

BEGIN

DECLARE lastDueDate DATE;

DECLARE dayInArrears INT;

SELECT la.instalment_due_date INTO  lastDueDate FROM new_loan_appstoreamort la INNER JOIN new_loan_appstore nl ON la.master2_id=nl.loan_id WHERE NOT la.instalment_status='P' AND nl.loan_id=loanId ORDER BY la.trn_id ASC LIMIT 1;

SET dayInArrears=DATEDIFF(DATE(NOW()),lastDueDate);
IF dayInArrears<0 THEN
SET dayInArrears=0;
ELSE
SET dayInArrears=dayInArrears+1;
END IF;
RETURN dayInArrears;

END ##
DELIMITER ;




/* NUMBER OF DAYS IN ARREARS FUNCTION */
DROP FUNCTION IF EXISTS instalmentDueDate;

DELIMITER ##

CREATE FUNCTION instalmentDueDate( loanId VARCHAR(45)) 
RETURNS VARCHAR(45)
DETERMINISTIC

BEGIN

DECLARE lastDueDate DATE;



SELECT instalment_due_date INTO  lastDueDate FROM new_loan_appstoreamort  WHERE NOT instalment_status='P' AND master2_id=loanId ORDER BY trn_id ASC LIMIT 1;


RETURN lastDueDate;

END ##
DELIMITER ;



/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS arrearRangeManger;

DELIMITER ##

CREATE FUNCTION arrearRangeManger( loanId VARCHAR(45)) 
RETURNS VARCHAR(30)
DETERMINISTIC

BEGIN

DECLARE theRange VARCHAR(30);

IF numberOfDayInArrears(loanId)>=1 AND numberOfDayInArrears(loanId)<30 THEN 

SET theRange='0-29';

ELSEIF numberOfDayInArrears(loanId)>=30 AND numberOfDayInArrears(loanId)<60 THEN

SET theRange='30-59';

ELSEIF numberOfDayInArrears(loanId)>=60 AND numberOfDayInArrears(loanId)<90 THEN

SET theRange='60-89';

ELSEIF  numberOfDayInArrears(loanId)>=90 AND numberOfDayInArrears(loanId)<360 THEN

SET theRange='90-359';

ELSEIF  numberOfDayInArrears(loanId)>=360 THEN

SET theRange='360 And Above';

END IF;



RETURN theRange;

END ##
DELIMITER ;



/* ALL CONTINENTAL REGIONS */
DROP PROCEDURE IF EXISTS officerPortfolio;

DELIMITER ##

CREATE PROCEDURE   officerPortfolio()
BEGIN
   
 DECLARE l_done, staffIds,ID INT;
 
DECLARE forSelectingStaffIds CURSOR FOR SELECT DISTINCT(gruop_id)   FROM new_loan_appstore where loan_cycle_status='Disbursed';
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 


DROP TABLE IF EXISTS loan_port_report;

CREATE TEMPORARY  TABLE loan_port_report(id_1 INTEGER,officer_id INT,officer_name VARCHAR(60),number_customers INT,loan_porfolio DOUBLE,loans_paid DOUBLE,loans_remaining DOUBLE,loans_0_30 DOUBLE,loans_31_60 DOUBLE,loans_61_90 DOUBLE,loans_91_360 DOUBLE,above_360 DOUBLE);


DROP TABLE IF EXISTS loan_port_report2;

CREATE TEMPORARY  TABLE loan_port_report2(id_2 INTEGER,officer_id INT,officer_name VARCHAR(60),number_customers INT,loan_porfolio DOUBLE,loans_paid DOUBLE,loans_remaining DOUBLE,loans_0_30 DOUBLE,loans_31_60 DOUBLE,loans_61_90 DOUBLE,loans_91_360 DOUBLE,above_360 DOUBLE);

SET ID=0;

 OPEN forSelectingStaffIds;



accounts_loop: LOOP 



 FETCH forSelectingStaffIds into staffIds;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 SET ID=ID+1;

-- SELECT staffIds;

CALL portFolioCategory (staffIds,'0-29', @portfolio029);
CALL portFolioCategory (staffIds,'30-59', @portfolio3059);
CALL portFolioCategory (staffIds,'60-89', @portfolio6059);
CALL portFolioCategory (staffIds,'90-359', @portfolio90359);
CALL portFolioCategory (staffIds,'360 And Above', @portfolio360AndAbove);


-- SELECT  @portfolio029,@portfolio3059,@portfolio6059,@portfolio90359,@portfolio360AndAbove;
IF ISNULL(ID) THEN 

SET ID=0;
END IF;

IF ISNULL(staffIds) THEN 

SET ID=10000;
END IF;

IF ISNULL(@portfolio029) THEN 

SET @portfolio029=0;
END IF;

IF ISNULL(@portfolio3059) THEN 

SET @portfolio3059=0;
END IF;

IF ISNULL(@portfolio3059) THEN 

SET @portfolio3059=0;
END IF;

IF ISNULL(@portfolio6059) THEN 

SET @portfolio6059=0;
END IF;

IF ISNULL(@portfolio90359) THEN 

SET @portfolio90359=0;
END IF;

IF ISNULL(@portfolio360AndAbove) THEN 

SET @portfolio360AndAbove=0;
END IF;
SELECT userName(staffIds) INTO @staffName;

IF ISNULL(@staffName) THEN
SET @staffName='MISSING';
END IF;

INSERT INTO loan_port_report VALUES(ID,staffIds,@staffName,numberOfLoans(staffIds),loanPortfolio(staffIds),loanPortfolioPaid(staffIds),loanPortfolioRemaining(staffIds),@portfolio029,@portfolio3059,@portfolio6059,@portfolio90359,@portfolio360AndAbove);


    SET l_done=0;
 END LOOP accounts_loop;



 CLOSE forSelectingStaffIds;

INSERT INTO  loan_port_report2( 
  id_2,
  officer_id ,
  officer_name ,
  number_customers ,
  loan_porfolio ,
  loans_paid ,
  loans_remaining ,
  loans_0_30,
loans_31_60,
loans_61_90,
loans_91_360,
above_360
  ) SELECT 
   id_1,
  officer_id ,
  officer_name ,
  number_customers ,
  loan_porfolio ,
  loans_paid ,
  loans_remaining ,
  loans_0_30,
loans_31_60,
loans_61_90,
loans_91_360,
above_360  FROM loan_port_report;

INSERT INTO  loan_port_report2( 
  id_2,
  officer_id ,
  officer_name ,
  number_customers ,
  loan_porfolio ,
  loans_paid ,
  loans_remaining ,
  loans_0_30,
loans_31_60,
loans_61_90,
loans_91_360,
above_360
  ) SELECT 
   "-"L,
 "-" ,
"TOTAL" ,
  SUM(number_customers) ,
 SUM(loan_porfolio ),
 SUM(loans_paid) ,
 SUM( loans_remaining ),
 SUM(  loans_0_30),
 SUM(loans_31_60),
 SUM(loans_61_90),
 SUM(loans_91_360),
SUM(above_360) FROM loan_port_report;

 SELECT * FROM loan_port_report2;

END

##
DELIMITER ;


call officerPortfolio();






/* ALL CONTINENTAL REGIONS */
DROP PROCEDURE IF EXISTS portFolioCategory;

DELIMITER ##

CREATE PROCEDURE  portFolioCategory (IN staffId INT,IN arrearsRangeComp VARCHAR(30), OUT portFolio1 DOUBLE)
BEGIN
   
 DECLARE l_done INT;

 DECLARE balanceDue DOUBLE;

 DECLARE arrearsRange,loanIds VARCHAR(30);
 
DECLARE forSelectingStaffIds CURSOR FOR SELECT loan_id FROM new_loan_appstore WHERE gruop_id=staffId AND loan_cycle_status='Disbursed';

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET portFolio1=0;

OPEN forSelectingStaffIds;
accounts_loop: LOOP 



 FETCH forSelectingStaffIds into loanIds;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

--  SELECT loanIds,staffId;
 
 SELECT arrearRangeManger(loanIds) INTO arrearsRange;

-- SELECT arrearsRange;

IF arrearsRange=arrearsRangeComp THEN

SELECT balance_due INTO balanceDue FROM new_loan_appstore WHERE loan_id=loanIds;

SET portFolio1=portFolio1+balanceDue;

END IF;


SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingStaffIds;

--  SELECT portFolio1;
 
END
##
DELIMITER ;






/* AGING ANYLYSIS */

DROP PROCEDURE IF EXISTS agingAnalysis;

DELIMITER ##

CREATE PROCEDURE   agingAnalysis()
BEGIN
   
 DECLARE l_done,ID,arrears INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT);

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 INTEGER,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;

accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;
 
--  SELECT loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
SELECT pl.applicant_account_name,m.mobile1,pl.trn_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT SUM(PrincipalRemaining),SUM(InterestRemaing),numberOfDayInArrears(loanId) INTO p_remain,i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';

-- SELECT p_remain,i_remain,arrears;

 SET ID=ID+1;

 IF ISNULL(remainport) THEN
SET remainport=0;
 END IF;

  IF ISNULL(princeremain) THEN
SET princeremain=0;
 END IF;

 IF ISNULL(interestRem) THEN
SET interestRem=0;
 END IF;

  IF ISNULL(p_remain) THEN
SET p_remain=0;
 END IF;

  IF ISNULL(i_remain) THEN
SET i_remain=0;
 END IF;

   IF ISNULL(arrears) THEN
SET arrears=0;
 END IF;
 

SELECT DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y') INTO @INST;

-- SELECT @INST;

  IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
 END IF;


INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears);


    SET l_done=0;
 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;




IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis2 VALUES(0,'1-30','PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
  date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

  --  INSERT INTO aging_loan_analysis2 VALUES(0,"-","-","-","-","-","-","-","-");
END IF;

IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis3 VALUES(0,'30-60','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");
  
  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis4 VALUES(0,'60-90','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");

    
    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

  --  INSERT INTO aging_loan_analysis4 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis5 VALUES(0,'90-360','NON PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");
    
   
    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

  --  INSERT INTO aging_loan_analysis5 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis6 VALUES(0,'360 AND Above','PORTFOLIO DUE FOR WRITE OFF',"-","-","-","-","-","-","-","-");

   
    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

END IF;

   
INSERT INTO  aging_loan_analysis8( 
  id_8,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1; 

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4;
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5;

  
  
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6;
  
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_8,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis8;

SELECT  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears FROM aging_loan_analysis7;
  
END

##
DELIMITER ;

CALL agingAnalysis();




/* AGING ANYLYSIS */
DROP PROCEDURE IF EXISTS agingAnalysisStaff;

DELIMITER ##

CREATE PROCEDURE   agingAnalysisStaff(IN staffId INT)
BEGIN
   
 DECLARE l_done,ID,arrears INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' AND gruop_id=staffId ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT);

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 INTEGER,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;

accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;
 
--  SELECT loanId;

 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
SELECT pl.applicant_account_name,m.mobile1,pl.trn_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;
-- SELECT customerContactNumber,loanPort,paidport,remainport,prince,princepaid,princeremain;
SELECT SUM(PrincipalRemaining),SUM(InterestRemaing),numberOfDayInArrears(loanId) INTO p_remain,i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';

-- SELECT p_remain,i_remain,arrears;

 SET ID=ID+1;

 IF ISNULL(remainport) THEN
SET remainport=0;
 END IF;

  IF ISNULL(princeremain) THEN
SET princeremain=0;
 END IF;

 IF ISNULL(interestRem) THEN
SET interestRem=0;
 END IF;

  IF ISNULL(p_remain) THEN
SET p_remain=0;
 END IF;

  IF ISNULL(i_remain) THEN
SET i_remain=0;
 END IF;

   IF ISNULL(arrears) THEN
SET arrears=0;
 END IF;

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),DATE_FORMAT(instalmentDueDate(loanId),'%d/%m/%Y'),remainport,princeremain,interestRem,p_remain,i_remain,arrears);


    SET l_done=0;
 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;




IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis2 VALUES(0,'0-30','PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
  date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

  --  INSERT INTO aging_loan_analysis2 VALUES(0,"-","-","-","-","-","-","-","-");
END IF;

IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis3 VALUES(0,'30-60','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");
  
  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  --  INSERT INTO aging_loan_analysis3 VALUES(0,"-","-","-","-","-","-","-","-");

END IF;

IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis4 VALUES(0,'60-90','PORTFOLIO AT RISK',"-","-","-","-","-","-","-","-");

    
    INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis4( 
  id_4,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

  --  INSERT INTO aging_loan_analysis4 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis5 VALUES(0,'90-360','NON PERFORMING PORTFOLIO',"-","-","-","-","-","-","-","-");
    
   
    INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

  --  INSERT INTO aging_loan_analysis5 VALUES(0,"-","-","-","-","-","-","-","-");


END IF;

IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis6 VALUES(0,'360 AND Above','PORTFOLIO DUE FOR WRITE OFF',"-","-","-","-","-","-","-","-");

   
    INSERT INTO  aging_loan_analysis6( 
  id_6,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
   
INSERT INTO  aging_loan_analysis5( 
  id_5,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
  "-",
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_2,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis2;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_3,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis3;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_4,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis4;
  
  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_5,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis5;

  
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears 
  ) SELECT 
   id_6,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  FROM aging_loan_analysis6;

SELECT  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears FROM aging_loan_analysis7;
END

##
DELIMITER ;

CALL agingAnalysisStaff(10022);






/* CURRENT SHIFT FUNCTION */

DROP FUNCTION IF EXISTS customerNameL;
DELIMITER ##
CREATE FUNCTION customerNameL(accounNumber VARCHAR(30)) 
RETURNS VARCHAR(60)
DETERMINISTIC
BEGIN
DECLARE customerNameNow VARCHAR(40);

SELECT account_name INTO customerNameNow FROM account_created_store WHERE  account_number =accounNumber;
IF ISNULL(customerNameNow) THEN
 SET customerNameNow='MISSING';
 END IF;
RETURN customerNameNow;
END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS staffName;
DELIMITER ##
CREATE FUNCTION staffName(staffId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE staffNameNow VARCHAR(40);

SELECT account_name INTO staffNameNow FROM log_in WHERE  user_id=staffId;
 IF ISNULL(staffNameNow) THEN
 SET staffNameNow='MISSING';
 END IF;
RETURN staffNameNow;
END ##
DELIMITER ;


/* CURRENT SHIFT FUNCTION */
DROP FUNCTION IF EXISTS staffName1;
DELIMITER ##
CREATE FUNCTION staffName1(staffId INT) 
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN
DECLARE staffNameNow VARCHAR(40);

SELECT account_name INTO staffNameNow FROM pmms.log_in WHERE  user_id=staffId;
 IF ISNULL(staffNameNow) THEN
 SET staffNameNow='MISSING';
 END IF;
RETURN staffNameNow;
END ##
DELIMITER ;



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

--  SELECT  SUM(temp_outStandingPrici) ,SUM(temp_OutStandingInterst),SUM(temp_OutStandingAccum) ,SUM(temp_OutStandingPenalty) ,SUM(temp_OutStandingTotal) INTO @TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll FROM temp_grossPortFolio;
 
-- INSERT INTO temp_grossPortFolio VALUES(0,'Total',@TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll);

SELECT id,temp_Borrower,temp_outStandingPrici,temp_OutStandingInterst,temp_OutStandingAccum,temp_OutStandingPenalty,temp_OutStandingTotal  FROM temp_grossPortFolio;


END //

DELIMITER ;

CALL grossLoanPortfolio();





DROP PROCEDURE IF EXISTS grossLoanPortfolioPdf;

DELIMITER //



CREATE PROCEDURE grossLoanPortfolioPdf() READS SQL DATA BEGIN

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

 SELECT  SUM(temp_outStandingPrici) ,SUM(temp_OutStandingInterst),SUM(temp_OutStandingAccum) ,SUM(temp_OutStandingPenalty) ,SUM(temp_OutStandingTotal) INTO @TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll FROM temp_grossPortFolio;
 
INSERT INTO temp_grossPortFolio VALUES(0,'Total',@TremPrinc,@TremInt,@TremAccumI,@TremaPenalty,@TtotalAll);

SELECT id,temp_Borrower,temp_outStandingPrici,temp_OutStandingInterst,temp_OutStandingAccum,temp_OutStandingPenalty,temp_OutStandingTotal  FROM temp_grossPortFolio;


END //

DELIMITER ;

CALL grossLoanPortfolioPdf();



DROP PROCEDURE IF EXISTS deleteLoans;

DELIMITER //



CREATE PROCEDURE deleteLoans(IN accountNumber VARCHAR(45)) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;
 
 
DECLARE forSelectingTxnIds CURSOR FOR SELECT trn_id  FROM new_loan_appstore WHERE applicant_account_number=accountNumber;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 


 OPEN forSelectingTxnIds;


accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
DELETE FROM new_loan_appstore WHERE trn_id=txnIdS;
DELETE FROM new_loan_appstore1 WHERE trn_id=txnIdS;
DELETE FROM new_loan_appstoreamort WHERE master1_id=txnIdS;
 
SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;

DELETE FROM loanprocessingstore WHERE account_number=accountNumber;
END //

DELIMITER ;



/*===========================================================PENALTY CALCULATIONS==============================================================================*/





DROP PROCEDURE IF EXISTS updateLoanPenaltyStatus;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyStatus(IN status INT(11)) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET penaltyStatus=status;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updateLoanPenaltyCycleDaysPro;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyCycleDaysPro(IN cycleD INT(11)) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET recoeryPeriod=cycleD;

END //

DELIMITER ;





DROP PROCEDURE IF EXISTS updateLoanPenaltyChargeOn;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyChargeOn(IN onCharge INT(11)) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET chargeOnLoan=onCharge;

END //

DELIMITER ;



DROP PROCEDURE IF EXISTS updateLoanPenaltyChargeAs;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyChargeAs(IN asCharge INT(11)) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET chargedAs=asCharge;

END //

DELIMITER ;




DROP PROCEDURE IF EXISTS updateLoanPenaltyTheActualCharge;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyTheActualCharge(IN theACharge DOUBLE) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET theActuaCharge=theACharge;

END //

DELIMITER ;






DROP PROCEDURE IF EXISTS updateLoanPenaltyDeadlineExv;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyDeadlineExv(IN theDealine INT) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET deadlineForPenalty=theDealine;

END //

DELIMITER ;


DROP PROCEDURE IF EXISTS updateLoanPenaltyDeadlineMeasure;

DELIMITER //

CREATE PROCEDURE updateLoanPenaltyDeadlineMeasure(IN theDealineM INT) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET deadlineMeasuredIn=theDealineM;

END //

DELIMITER ;



DROP PROCEDURE IF EXISTS recoverInterestNumberTimes;

DELIMITER //

CREATE PROCEDURE recoverInterestNumberTimes(IN theDealineM INT) READS SQL DATA BEGIN

UPDATE loanarrearssettings SET accruePenaltyTimes=theDealineM;

END //

DELIMITER ;


DROP EVENT IF EXISTS `manage_penalty`;

CREATE  EVENT IF NOT EXISTS  `manage_penalty` ON SCHEDULE EVERY 20 MINUTE STARTS CURRENT_TIMESTAMP() ON COMPLETION NOT PRESERVE ENABLE DO CALL compute_penalty();



DROP PROCEDURE IF EXISTS compute_penalty;

DELIMITER //

CREATE PROCEDURE compute_penalty() READS SQL DATA 


DECLARE pStatus,pRecoverPeriod,pChargeOn,pChargeAs,pDeadlineForP,pDeadlineMeasureIn,pAccruePOnceRecurrent,accruePenaltyTimesX INT;
/* The pStatus stands for penalty status, this indicates whether the application is set for charging penalty or not */
/* The pRecoverPeriod stands for whether penalty can be charged daily, weekly monthly etc */
/* The pChargeOn stands for whether penalty can be charged on the outstanding principal,interest or loanamount */
/* The pChargeAs stands for whether penalty can be charged as franction fixed figure or percentage which is most common */

DECLARE pTheActualCharge DOUBLE; 

SELECT  penaltyStatus , recoeryPeriod , chargeOnLoan , chargedAs , theActuaCharge , deadlineForPenalty , deadlineMeasuredIn , accruePenaltyTimes INTO pStatus,pRecoverPeriod,pChargeOn,pChargeAs,pTheActualCharge,pDeadlineForP, pDeadlineMeasureIn,accruePenaltyTimesX FROM loanarrearssettings

IF pStatus=1 THEN

IF accruePenaltyTimesX=1 THEN
UPDATE allocations_total AS ft, (SELECT alloTotalComp(amount,branch.branch_id,userId,'ALLOCTOTALMADE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS totalAllocation,alloTotalComp(amount,branch.branch_id,userId,'ALLOCBALANCE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS balanceAllocation,branch_id as idX  FROM branch INNER JOIN allocations_total ON  branch.branch_id=allocations_total.fk_branch_id_allocations_total) AS b SET ft.allocations_total_made=b.totalAllocation,ft.allocations_total_balance=b.balanceAllocation WHERE ft.fk_branch_id_allocations_total=b.idX;

UPDATE new_loan_appstore AS nla, (SELECT computePenaltyOnce(trn_id,loan_id,pRecoverPeriod,pChargeOn,pChargeAs,pTheActualCharge,pDeadlineForP, pDeadlineMeasureIn) AS computedPenalty,trn_id AS trn_idX,loan_id AS loan_idX  FROM new_loan_appstore  WHERE nla.loan_cycle_status='Disbursed') AS innerQ SET nla.;

END IF;


IF accruePenaltyTimesX=2 THEN

UPDATE new_loan_appstore AS nla, (SELECT computePenalty(amount,branch.branch_id,userId,'ALLOCTOTALMADE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS totalAllocation,alloTotalComp(amount,branch.branch_id,userId,'ALLOCBALANCE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS balanceAllocation,branch_id as idX  FROM branch INNER JOIN allocations_total ON  branch.branch_id=allocations_total.fk_branch_id_allocations_total) AS b SET ft.allocations_total_made=b.totalAllocation,ft.allocations_total_balance=b.balanceAllocation WHERE ft.fk_branch_id_allocations_total=b.idX;

END IF;

IF accruePenaltyTimesX=3 THEN

UPDATE new_loan_appstore AS nla, (SELECT computePenalty(amount,branch.branch_id,userId,'ALLOCTOTALMADE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS totalAllocation,alloTotalComp(amount,branch.branch_id,userId,'ALLOCBALANCE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS balanceAllocation,branch_id as idX  FROM branch INNER JOIN allocations_total ON  branch.branch_id=allocations_total.fk_branch_id_allocations_total) AS b SET ft.allocations_total_made=b.totalAllocation,ft.allocations_total_balance=b.balanceAllocation WHERE ft.fk_branch_id_allocations_total=b.idX;

END IF;

END IF;


END //

DELIMITER ;


-- DROP TABLE IF EXISTS loanArrearsSettings;
CREATE TABLE loanArrearsSettings (
   id INT(11) NOT NULL AUTO_INCREMENT,
  penaltyStatus INT(11) NOT NULL, -- ALLOWED=1,NOT_ALLOWED=0
  recoeryPeriod INT(11) NOT NULL DEFAULT 1, -- DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  chargeOnLoan INT(11) NOT NULL DEFAULT 1, --  INSTALMENT=1,PRINCIMPAL_INSTALMENT=2,INTEREST_INSTALMENT=3,REMAINING_AMOUNT=4 
  chargedAs INT(11) NOT NULL DEFAULT 1, --  PERCENTAGE=1,FRANCTION=2,FIXED_FIGURE=3
  theActuaCharge DOUBLE NOT NULL DEFAULT 0,
  deadlineForPenalty INT(11) NOT NULL DEFAULT 0,
  deadlineMeasuredIn INT(11) NOT NULL DEFAULT 1, -- DAYS=1,WEEKS=2,MONTHS=3,QUATERS=4,YEARS=5
   accruePenaltyTimes INT(11) NOT NULL DEFAULT 1, -- ONCE=1, RECYCLE=2
  
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=2 DEFAULT CHARACTER SET=utf8;


--  INSERT INTO loanArrearsSettings VALUES (NULL,0,4,1,1,10.0,2,1,1);
-- DROP TABLE IF EXISTS loanArrearsIndividualSettings;

CREATE TABLE loanArrearsIndividualSettings (
   id INT(11) NOT NULL AUTO_INCREMENT,
  penaltyStopped INT(11) NOT NULL, -- ALLOWED=1,NOT_ALLOWED=0
  lastAccrued INT(11) TIMESTAMP, -- DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  chargeOnLoan INT(11) NOT NULL DEFAULT 1, --  INSTALMENT=1,PRINCIMPAL_INSTALMENT=2,INTEREST_INSTALMENT=3,REMAINING_AMOUNT=4 
  chargedAs INT(11) NOT NULL DEFAULT 1, --  PERCENTAGE=1,FRANCTION=2,FIXED_FIGURE=3
  theActuaCharge DOUBLE NOT NULL DEFAULT 0,
  deadlineForPenalty INT(11) NOT NULL DEFAULT 0,
  deadlineMeasuredIn INT(11) NOT NULL DEFAULT 1, -- DAYS=1,WEEKS=2,MONTHS=3,QUATERS=4,YEARS=5
   accruePenaltyTimes INT(11) NOT NULL DEFAULT 1, -- ONCE=1, RECYCLE=2
  
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=2 DEFAULT CHARACTER SET=utf8;


        
        
       
       
DROP TABLE IF EXISTS `new_loan_appstore2`;

CREATE TABLE `new_loan_appstore2` (
  `trn_id` varchar(10) NOT NULL,
  `trn_date` date NOT NULL,
  `loan_id` varchar(45) DEFAULT NULL,
  `total_instalments` varchar(45) DEFAULT NULL,
  `remaining_instalments` varchar(45) DEFAULT NULL,
  `princimpal_amount` varchar(45) DEFAULT NULL,
  `total_interest` varchar(45) DEFAULT NULL,
  `total_loanAmount` varchar(45) DEFAULT NULL,
  `balance_due` varchar(45) DEFAULT NULL,
  `instalment_start_date` date NOT NULL,
  `instalment_next_due_date` date NOT NULL,
  `instalment_end_date` date NOT NULL,
  `interest_rate` varchar(45) DEFAULT NULL,
  `applicant_account_name` varchar(45) DEFAULT NULL,
  `loan_cycle_status` varchar(45) DEFAULT NULL,
  `trn_time` time NOT NULL,
  `instalments_paid` varchar(45) DEFAULT NULL,
  `instalment_amount` varchar(45) DEFAULT NULL,
  `loan_tenure` varchar(45) DEFAULT NULL,
  `applicant_account_number` varchar(45) DEFAULT NULL,
  `inputter_id` varchar(10) DEFAULT NULL,
  `authoriser_id` varchar(10) DEFAULT NULL,
  `gruop_id` varchar(30) DEFAULT NULL,
  `GroupId` varchar(50) NOT NULL DEFAULT 'G0001',
  `GroupName` varchar(100) NOT NULL DEFAULT 'GROUP1',
  `SecurityLoan` varchar(500) DEFAULT NULL,
  `OtherGroups2` varchar(100) NOT NULL DEFAULT 'Other2',
  `OtherGroups3` varchar(100) NOT NULL DEFAULT 'Other3',
  `OtherGroups4` varchar(100) NOT NULL DEFAULT 'Other4',
  `TotalInterestPaid` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalInterestRemaining` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalPrincipalPaid` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalPrincipalRemaining` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalAccumulatedInterestPaid` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalAccumulatedInterestRemaining` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalLoanPenaltyPaid` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalLoanPenaltyRemaining` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalAccruedInterestRemaining` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalAccruedInterestPaid` varchar(100) NOT NULL DEFAULT '0.0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `new_loan_appstoreamort2`;

CREATE TABLE `new_loan_appstoreamort2` (
  `trn_id` int(11) NOT NULL ,
  `instalment_no` int(11) NOT NULL DEFAULT '1',
  `princimpal_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `princimpal_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `interest_amount` varchar(50) NOT NULL DEFAULT '10000.0',
  `interest_amount_run_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_paid` varchar(50) NOT NULL DEFAULT '0.0',
  `beginning_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `closing_bal` varchar(50) NOT NULL DEFAULT '0.0',
  `instalment_due_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_status` varchar(50) NOT NULL DEFAULT 'P',
  `instalment_paid_date` date NOT NULL DEFAULT '1979-01-01',
  `instalment_paid_variance` varchar(50) NOT NULL DEFAULT '2 Days',
  `LoanPenalty` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `InstalmentRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `PrincipalRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `InterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `LoanPenaltyRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccruedInterestRemaing` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterest` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `AccumulatedInterestRemaining` varchar(50) NOT NULL DEFAULT '0.0',
  `OtherOne` varchar(50) NOT NULL DEFAULT 'NYA',
  `OtherTwo` date NOT NULL DEFAULT '1979-01-01',
  `OtherThree` date NOT NULL DEFAULT '1979-01-01',
  `master1_id` varchar(50) NOT NULL DEFAULT '0.0',
  `master2_id` varchar(50) NOT NULL DEFAULT '0.0'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;