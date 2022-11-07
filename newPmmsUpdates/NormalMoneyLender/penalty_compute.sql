
DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;
DELIMITER //
CREATE PROCEDURE theAMDAPenaltyComputation() READS SQL DATA 

BEGIN

DECLARE lDone1 INTEGER DEFAULT 0;

DECLARE instalmentNo,theInstalmentNo,existsIn1,instalmentStatus,theInstalmentStatus,counter,firstInstalment,idExists INT; 

DECLARE newBalance,oldBalance,computedPenalty,newPenaltyRemaining,oldPenaltyRemaining,newInstalmentAmount,oldInstalmentAmount,newLoanPenalty,oldLoanPenalty,newLoanPenaltyRemaining,oldLoanPenaltyRemaining,newInstalmnetAmount,oldInstalmnetAmount DOUBLE;

DECLARE penaltyStatusN,thePenaltyS,numberOfItems  INTEGER;
DECLARE endDate DATE;
DECLARE trnId VARCHAR(20);
DECLARE forselectingLoanTrnId CURSOR FOR SELECT trn_id  FROM new_loan_appstore  WHERE loan_cycle_status='Disbursed' AND  NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') ;

/* AND isInArrearsAMDA(trn_id)>0 AND typeOfInstalment(trn_id)>0  */
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1=1;
 

OPEN forselectingLoanTrnId; 

loanTrnIdLoop: LOOP 

FETCH forselectingLoanTrnId INTO trnId;
--  SELECT trnId;  
 IF lDone1=1 THEN
LEAVE loanTrnIdLoop;
 END IF;
 
SELECT penaltyStatus INTO penaltyStatusN FROM loanarrearssettings;
 
 IF penaltyStatusN=1 THEN 
 
 SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=trnId;
SET thePenaltyS=NULL;
SELECT penaltyStatus INTO thePenaltyS FROM amdapenaltycomputenow WHERE loanTrnId=trnId;
 
-- SELECT thePenaltyS,trnId;

IF ISNULL(thePenaltyS) THEN
SET thePenaltyS=1;
END IF;
  -- SELECT endDate,thePenaltyS,trnId; 
IF endDate>=(NOW()-INTERVAL 1 DAY) THEN

-- SELECT trnId,endDate;

 SELECT COUNT(instalment_no) INTO numberOfItems FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId;

--  SELECT numberOfItems; 

 SELECT instalment_no INTO firstInstalment FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId ORDER BY instalment_no ASC LIMIT 1;

SET counter=0;

IF numberOfItems>0 THEN
 
instalmentNoLoop:REPEAT 
SET counter=counter+1;  
-- SELECT 'AM IN N';
SELECT instalmentComputeStatus INTO theInstalmentStatus FROM amdapenaltycomputenowdetails WHERE loanTrnId=trnId AND instalmentNo=firstInstalment LIMIT 1;



SET @theInstalmentStatus=NULL;
   SET @theData = CONCAT(CAST("SELECT instalmentComputeStatus INTO @theInstalmentStatus FROM amdapenaltycomputenowdetails WHERE loanTrnId= " AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),trnId,CAST("'" AS CHAR CHARACTER SET utf8),CAST("AND instalmentNo=" AS CHAR CHARACTER SET utf8),firstInstalment);
-- select @theData; 
  PREPARE stmt2 FROM @theData;
  EXECUTE stmt2;
DROP PREPARE stmt2;

-- SELECT trnId,firstInstalment,@theInstalmentStatus,"2",counter;

IF ISNULL(@theInstalmentStatus) THEN 
SET @theInstalmentStatus=1;
END IF;
--  SELECT trnId,instalmentNo,@theInstalmentStatus,"3";
 IF @theInstalmentStatus=1 THEN 


-- SELECT theInstalmentStatus,"4"; 
SELECT balance_due,TotalLoanPenaltyRemaining,instalment_amount INTO oldBalance,oldPenaltyRemaining,oldInstalmentAmount FROM new_loan_appstore WHERE trn_id=trnId;

SELECT LoanPenalty, LoanPenaltyRemaining, InstalmentRemaining INTO oldLoanPenalty,oldLoanPenaltyRemaining,oldInstalmnetAmount FROM new_loan_appstoreamort WHERE master1_id=trnId AND instalment_no=firstInstalment;

--  SELECT oldInstalmnetAmount,"5"; 

SET computedPenalty=(oldInstalmnetAmount*.0112);

-- SELECT computedPenalty,trnId,instalmentNo,"6"; 

SET newBalance=oldBalance+computedPenalty,newPenaltyRemaining=oldPenaltyRemaining+computedPenalty,newInstalmentAmount=oldInstalmentAmount+computedPenalty,newLoanPenalty=oldLoanPenalty+computedPenalty,newLoanPenaltyRemaining=oldLoanPenaltyRemaining+computedPenalty,newInstalmnetAmount=oldInstalmnetAmount+computedPenalty;

--  ( SELECT 'trn_id','trn_date','loan_id','total_instalments','remaining_instalments','princimpal_amount','total_interest','total_loanAmount','balance_due','instalment_start_date','instalment_next_due_date','instalment_end_date','interest_rate','applicant_account_name','loan_cycle_status','trn_time','instalments_paid','instalment_amount','loan_tenure','applicant_account_number','inputter_id','authoriser_id','gruop_id','GroupId','GroupName','SecurityLoan','OtherGroups2','OtherGroups3','OtherGroups4','TotalInterestPaid','TotalInterestRemaining','TotalPrincipalPaid','TotalPrincipalRemaining','TotalAccumulatedInterestPaid','TotalAccumulatedInterestRemaining','TotalLoanPenaltyPaid','TotalLoanPenaltyRemaining','TotalAccruedInterestRemaining','TotalAccruedInterestPaid','TrnId','trn_date','account_master','title','first_name','sir_name','sex','birth_date','marital_status','highest_educ_level','home_parish','centre','hiika','mobile1','mobile2','email','kampala_residence','occupation','employer','category_of_membership','value_of_shares','number_of_shares','kin_first_name','kin_sir_name','kin_mobile_1','kin_mobile_2','kin_email','notes','introducing_capacity','intro_first_name','intro_sir_name','intro_sacco_status','intro_mob_1','intro_mob_2','intro_sacco_member_since','approval_status','approval_date','approved_by','account_number','account_name','time','last_updated_date','last_updated_time','updated_approval','UserPhoto')  UNION ALL (select * from pmms_loans.new_loan_appstore AS loan INNER JOIN pmms.master AS account ON loan.applicant_account_number=account.account_number WHERE loan.loan_cycle_status='Disbursed' INTO OUTFILE 'aOverAll1.sql' FIELDS TERMINATED BY '#'  ENCLOSED BY '"' LINES TERMINATED BY '\n');
 SELECT COUNT(id) INTO idExists FROM amdapenaltycomputenowdetails WHERE loanTrnId=trnId AND instalmentNo=firstInstalment;

IF idExists<=0 THEN 
-- SELECT trnId,firstInstalment,@theInstalmentStatus,"2",counter;
INSERT INTO  amdapenaltycomputenowdetails VALUES(NULL,trnId,firstInstalment,2,2,DATE(NOW()));

END IF;
/* SET theInstalmentStatus=1; */
UPDATE new_loan_appstore SET balance_due=newBalance,TotalLoanPenaltyRemaining=newPenaltyRemaining,instalment_amount=newInstalmentAmount WHERE trn_id=trnId;


/* SELECT newLoanPenalty,newLoanPenaltyRemaining,newInstalmnetAmount,instalmentNo,trnId; */
UPDATE new_loan_appstoreamort SET LoanPenalty=newLoanPenalty, LoanPenaltyRemaining=newLoanPenaltyRemaining, InstalmentRemaining=newInstalmnetAmount WHERE instalment_no=firstInstalment AND master1_id=trnId;

END IF;

    
SET firstInstalment=firstInstalment+1;

 
    --  select     counter,numberOfItems,firstInstalment; 
UNTIL counter=(numberOfItems) END REPEAT instalmentNoLoop;
END IF;

ELSEIF thePenaltyS=1 THEN
-- SELECT 'tHEpNEA';
SELECT COUNT(id) INTO existsIn1 FROM amdaPenaltyComputeNow WHERE loanTrnId=trnId;


--  SELECT existsIn1; 

IF existsIn1 <=0 THEN

INSERT INTO amdapenaltycomputenow VALUES(NULL,trnId,1,NOW());

END IF;

 SELECT balance_due,TotalLoanPenaltyRemaining,instalment_amount INTO oldBalance,oldPenaltyRemaining,oldInstalmentAmount FROM new_loan_appstore WHERE trn_id=trnId; 

SELECT instalment_no, LoanPenalty, LoanPenaltyRemaining, instalment_amount INTO theInstalmentNo,oldLoanPenalty,oldLoanPenaltyRemaining,oldInstalmnetAmount FROM new_loan_appstoreamort WHERE master1_id=trnId AND NOT instalment_status='P' ORDER BY instalment_no ASC LIMIT 1; 
/* SELECT theInstalmentNo,oldLoanPenalty,oldLoanPenaltyRemaining,oldInstalmnetAmount,trnId; */
 SELECT (oldBalance*.1) INTO computedPenalty; 

SET newBalance=oldBalance+computedPenalty,newPenaltyRemaining=oldPenaltyRemaining+computedPenalty,newInstalmentAmount=oldInstalmentAmount+computedPenalty,newLoanPenalty=oldLoanPenalty+computedPenalty,newLoanPenaltyRemaining=oldLoanPenaltyRemaining+computedPenalty,newInstalmnetAmount=oldInstalmnetAmount+computedPenalty;

UPDATE amdapenaltycomputenow SET penaltyStatus=2;

UPDATE new_loan_appstore SET balance_due=newBalance,TotalLoanPenaltyRemaining=newPenaltyRemaining,instalment_amount=newInstalmentAmount WHERE trn_id=trnId;


/* SELECT newLoanPenalty,newLoanPenaltyRemaining,newInstalmnetAmount,theInstalmentNo,trnId; */
UPDATE new_loan_appstoreamort SET LoanPenalty=newLoanPenalty, LoanPenaltyRemaining=newLoanPenaltyRemaining, instalment_amount=newInstalmnetAmount WHERE instalment_no=theInstalmentNo AND master1_id=trnId;

END IF;
/* SET theInstalmentStatus=NULL; */

  
END IF;

SET lDone1=0;
END LOOP loanTrnIdLoop;
CLOSE forselectingLoanTrnId;
END //
DELIMITER ;



 /* DROP TABLE IF EXISTS amdaPenaltyComputeNow;
  DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails;
   DROP TABLE IF EXISTS amdaPenaltyComputeInstalmentNow; */

CREATE TABLE amdaPenaltyComputeNow (
   id INT(11) NOT NULL AUTO_INCREMENT,
   loanTrnId VARCHAR(20),
  penaltyStatus INT(11) NOT NULL, -- ONGOING=1,STOPPED=2
  lastAccrued DATE,--  DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=30 DEFAULT CHARACTER SET=utf8;

  /* DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails; */
CREATE TABLE amdaPenaltyComputeNowDetails (
   id INT(11) NOT NULL AUTO_INCREMENT,
   loanTrnId VARCHAR(20),
   instalmentNo INT(11) NOT NULL, 
  instalmentComputeStatus INT(11)  NULL, -- 1=ONGOING,2=STOPPED
  loanComputeStatus INT(11)  NULL,  -- 1=ONGOING,2=STOPPED
  dateComputed DATE, 
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=20 DEFAULT CHARACTER SET=utf8;



CREATE TABLE amdaPenaltyComputeInstalmentNow (
   id INT(11) NOT NULL AUTO_INCREMENT,
   strn_id VARCHAR(10),
  instalmentNo INT(11) NOT NULL, 
  lastAccrued DATE, --  DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=2 DEFAULT CHARACTER SET=utf8;








DROP PROCEDURE IF EXISTS computeAMDAPenalty;

DELIMITER ##

CREATE PROCEDURE computeAMDAPenalty(IN trnId VARCHAR(10),OUT computedPenalty DOUBLE) 


BEGIN

DECLARE thePenalty,theBalance  DOUBLE;

DECLARE theLooper,thePenaltyS,existsIn1,existsIn2,existsIn3 INT;

DECLARE endDate DATE;


SELECT COUNT(id) INTO existsIn1 FROM amdaPenaltyComputeNow WHERE strn_id=trnId;


SELECT existsIn1;

IF existsIn1 <=0 THEN

INSERT INTO amdapenaltycomputenow VALUES(NULL,trnId,1,NOW());

END IF;

SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=trnId;

SELECT penaltyStatus INTO thePenaltyS FROM amdapenaltycomputenow WHERE strn_id=trnId;

IF endDate>=(NOW()-INTERVAL 1 DAY) THEN

/* SELECT outStandingInstalmentsAMDA(trnId),firstInstalmentsAMDA(trnId) INTO @noInstalments,@firstIntalment; */

SELECT instalment_no INTO @firstIntalment FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId ORDER BY trn_id ASC LIMIT 1;

SELECT COUNT(trn_id)  INTO @noInstalments FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId;
SET theLooper=1,thePenalty=0;
 REPEAT
 
IF ISNULL(@firstIntalment) THEN
SET @firstIntalment=0;
END IF;
 
SELECT COUNT(id) INTO existsIn2 FROM amdaPenaltyComputeInstalmentNow WHERE strn_id=trnId AND instalmentNo=@firstIntalment;
 SELECT @firstIntalment;
 IF existsIn2<=0 THEN

INSERT INTO amdaPenaltyComputeInstalmentNow VALUES(NULL,trnId,@firstIntalment,NOW());

END IF;


SELECT COUNT(id) INTO existsIn3 FROM amdapenaltycomputeinstalmentnow WHERE strn_id=trnId AND instalmentNo=@firstIntalment AND lastAccrued<=NOW();

  IF existsIn3>0 THEN
 SELECT trnId,@firstIntalment, "HELP ME";
   SELECT computeThePenaltyNowAMDA(trnId,@firstIntalment) INTO @thePenaltyComputed;
   
    SET thePenalty=thePenalty+@thePenaltyComputed;
    
    END IF;
    
SELECT  (@firstIntalment+1) INTO @firstIntalment;
   
  SET theLooper=theLooper+1;
   SELECT theLooper, @firstIntalment, @noInstalments;

  UNTIL theLooper=@noInstalments OR @noInstalments=0 END REPEAT;
  
  SET @noInstalments=0,@firstIntalment=0;

ELSEIF thePenaltyS=1 THEN

SELECT balance_due INTO theBalance FROM new_loan_appstore WHERE trn_id=trnId;

SELECT (theBalance*.1) INTO computedPenalty;

UPDATE amdapenaltycomputenow SET penaltyStatus=2;

END IF;

/* SELECT thePenalty; */

END ##
DELIMITER ;



DROP FUNCTION IF EXISTS changeBalanceDue;

DELIMITER ##

CREATE FUNCTION changeBalanceDue(penalty DOUBLE,trnId VARCHAR(10)) 

RETURNS DOUBLE

DETERMINISTIC

BEGIN

DECLARE balance,newBal DOUBLE ;



SELECT balance_due INTO balance FROM new_loan_appstore WHERE trn_id=trnId;

SET newBal=balance+penalty;

RETURN newBal;

END ##
DELIMITER ;




DROP FUNCTION IF EXISTS changeInstalmentAmount;

DELIMITER ##

CREATE FUNCTION changeInstalmentAmount(penalty DOUBLE,trnId VARCHAR(10)) 

RETURNS DOUBLE

DETERMINISTIC

BEGIN

DECLARE balance,newBal DOUBLE ;



SELECT instalment_amount INTO balance FROM new_loan_appstore WHERE trn_id=trnId;

SET newBal=balance+penalty;

RETURN newBal;

END ##
DELIMITER ;




DROP FUNCTION IF EXISTS changePenaltyComputed;

DELIMITER ##

CREATE FUNCTION changePenaltyComputed(penalty DOUBLE,trnId VARCHAR(10)) 

RETURNS DOUBLE

DETERMINISTIC

BEGIN

DECLARE balance,newBal DOUBLE ;


SELECT TotalLoanPenaltyRemaining INTO balance FROM new_loan_appstore WHERE trn_id=trnId;

SET newBal=balance+penalty;

RETURN newBal;

END ##

DELIMITER ;






DROP FUNCTION IF EXISTS checkWhetherItemExistsIn;

DELIMITER ##

CREATE FUNCTION checkWhetherItemExistsIn(trnId VARCHAR(10),theInstalment INT) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE existsIn INT;



SELECT COUNT(instalment_no) INTO existsIn FROM loanarrearsindividualsettings WHERE instalment_no=theInstalment AND strn_id=trnId;


RETURN existsIn;

END ##
DELIMITER ;

UPDATE allocations_total AS ft, (SELECT alloTotalComp(amount,branch.branch_id,userId,'ALLOCTOTALMADE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS totalAllocation,alloTotalComp(amount,branch.branch_id,userId,'ALLOCBALANCE',lastAmountAllocatedId,allocations_total.allocations_total_id) AS balanceAllocation,branch_id as idX  FROM branch INNER JOIN allocations_total ON  branch.branch_id=allocations_total.fk_branch_id_allocations_total) AS b SET ft.allocations_total_made=b.totalAllocation,ft.allocations_total_balance=b.balanceAllocation WHERE ft.fk_branch_id_allocations_total=b.idX;


-- UPDATE new_loan_appstore AS nla,(SELECT SUM(new_loan_appstoreamort.InstalmentRemaining) AS bDue,SUM(new_loan_appstoreamort.PrincipalRemaining) AS pDue,SUM(new_loan_appstoreamort.InterestRemaing) AS iDue, SUM(new_loan_appstoreamort.LoanPenaltyRemaining) AS peDue,new_loan_appstoreamort.master1_id as theId FROM new_loan_appstoreamort INNER JOIN new_loan_appstore ON new_loan_appstoreamort.master1_id=new_loan_appstore.trn_id WHERE NOT new_loan_appstoreamort.instalment_status='P'  GROUP BY new_loan_appstoreamort.master1_id ) AS nlaam SET nla.balance_due=nlaam.bDue, nla.TotalPrincipalRemaining=nlaam.pDue,nla.TotalInterestRemaining=nlaam.iDue,nla.TotalLoanPenaltyRemaining=nlaam.peDue WHERE nla.loan_cycle_status='Disbursed' AND nla.trn_id=nlaam.theId;

DROP FUNCTION IF EXISTS isInArrearsAMDA;

DELIMITER ##

CREATE FUNCTION isInArrearsAMDA(trnId VARCHAR(10)) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE existsIn INT;


SELECT DISTINCT COUNT(master1_id) INTO existsIn FROM new_loan_appstoreamort WHERE master1_id=trnId AND instalment_due_date<NOW() AND NOT instalment_status='P';


RETURN existsIn;

END ##
DELIMITER ;



DROP FUNCTION IF EXISTS typeOfInstalment;

DELIMITER ##

CREATE FUNCTION typeOfInstalment(trnId VARCHAR(10)) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE existsIn1 INT;


SELECT DISTINCT COUNT(trn_id) INTO existsIn1 FROM new_loan_appstore WHERE trn_id=trnId AND NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') AND loan_cycle_status='Disbursed';


RETURN existsIn1;

END ##
DELIMITER ;











DROP PROCEDURE IF EXISTS computeThePenaltyNowAMDA;

DELIMITER ##

CREATE PROCEDURE computeThePenaltyNowAMDA(IN trnId VARCHAR(10),IN TfirstIntalment INT,OUT thePenaltyComputed DOUBLE) BEGIN 


DECLARE penaltyN,penalryNRemain,instalmentAmount,instalmentRemain,computeInstalment,computedPenalty DOUBLE ;

SELECT LoanPenalty, LoanPenaltyRemaining,instalment_amount,InstalmentRemaining,(InterestRemaing+PrincipalRemaining) INTO penaltyN,penalryNRemain,instalmentAmount,instalmentRemain,computeInstalment FROM new_loan_appstoreamort WHERE instalment_no=TfirstIntalment AND master1_id=trnId;
SELECT penaltyN,penalryNRemain,instalmentAmount,instalmentRemain,computeInstalment ;

SET computedPenalty=computeInstalment*.01; 

SET  penaltyN=penaltyN+computedPenalty,penalryNRemain=penalryNRemain+computedPenalty,instalmentAmount=instalmentAmount+computedPenalty,instalmentRemain=instalmentRemain+computedPenalty;

SELECT trnId,TfirstIntalment,computedPenalty,NOW();
UPDATE new_loan_appstoreamort SET LoanPenalty=penaltyN, LoanPenaltyRemaining=penalryNRemain, instalment_amount=instalmentAmount,InstalmentRemaining=instalmentRemain WHERE instalment_no=TfirstIntalment AND master1_id=trnId;



UPDATE amdapenaltycomputeinstalmentnow SET lastAccrued=(NOW()+INTERVAL 30 DAY) WHERE strn_id=trnId AND instalmentNo=TfirstIntalment;

INSERT INTO amdaPenaltyComputeNowDetails VALUES(NULL,trnId,TfirstIntalment,computedPenalty,NOW());

SELECT computedPenalty;

END ##
DELIMITER ; 



DROP FUNCTION IF EXISTS computeThePenaltyNowAMDA;

DELIMITER ##

CREATE FUNCTION computeThePenaltyNowAMDA(trnId VARCHAR(10),TfirstIntalment INT) 

RETURNS INT

DETERMINISTIC

BEGIN


DECLARE penaltyN,penalryNRemain,instalmentAmount,instalmentRemain,computeInstalment,computedPenalty DOUBLE ;

SELECT LoanPenalty, LoanPenaltyRemaining,instalment_amount,InstalmentRemaining,(InterestRemaing+PrincipalRemaining) INTO penaltyN,penalryNRemain,instalmentAmount,instalmentRemain,computeInstalment FROM new_loan_appstoreamort WHERE instalment_no=TfirstIntalment AND master1_id=trnId;


SET computedPenalty=computeInstalment*.01; SET  penaltyN=penaltyN+computedPenalty,penalryNRemain=penalryNRemain+computedPenalty,instalmentAmount=instalmentAmount+computedPenalty,instalmentRemain=instalmentRemain+computedPenalty;


UPDATE new_loan_appstoreamort SET LoanPenalty=penaltyN, LoanPenaltyRemaining=penalryNRemain, instalment_amount=instalmentAmount,InstalmentRemaining=instalmentRemain WHERE instalment_no=TfirstIntalment AND master1_id=trnId;



UPDATE amdapenaltycomputeinstalmentnow SET lastAccrued=(NOW()+INTERVAL 30 DAY) WHERE strn_id=trnId AND instalmentNo=TfirstIntalment;

INSERT INTO amdaPenaltyComputeNowDetails VALUES(NULL,trnId,TfirstIntalment,computedPenalty,NOW());


RETURN computedPenalty;

END ##
DELIMITER ;




DROP FUNCTION IF EXISTS outStandingInstalmentsAMDA;

DELIMITER ##

CREATE FUNCTION outStandingInstalmentsAMDA(trnId VARCHAR (10)) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE noInstalments INT;


SELECT COUNT(trn_id)  INTO noInstalments FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId;


RETURN noInstalments;

END ##
DELIMITER ;



DROP FUNCTION IF EXISTS firstInstalmentsAMDA;

DELIMITER ##

CREATE FUNCTION firstInstalmentsAMDA(trnId VARCHAR (10)) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE firstIntalment INT;


SELECT instalment_no INTO firstIntalment FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId ORDER BY trn_id ASC LIMIT 1;


RETURN firstIntalment;

END ##
DELIMITER ;





DROP FUNCTION IF EXISTS checkWhetherItemExistsInAMDA;

DELIMITER ##

CREATE FUNCTION checkWhetherItemExistsInAMDA(trnId VARCHAR (10)) 

RETURNS INT

/* DETERMINISTIC */

BEGIN

DECLARE existsIn INT;


SELECT COUNT(id) INTO existsIn FROM amdaPenaltyComputeNow WHERE strn_id=trnId;


RETURN existsIn;

END ##
DELIMITER ;





DROP FUNCTION IF EXISTS checkWhetherInstalmentExistsAMDA;

DELIMITER ##

CREATE FUNCTION checkWhetherInstalmentExistsAMDA(trnId VARCHAR (10),instalmentNoNow INT) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE existsIn INT;


SELECT COUNT(id) INTO existsIn FROM amdaPenaltyComputeInstalmentNow WHERE strn_id=trnId AND instalmentNo=instalmentNoNow;


RETURN existsIn;

END ##
DELIMITER ;





DROP FUNCTION IF EXISTS instalmentIsDueForCompute;

DELIMITER ##

CREATE FUNCTION instalmentIsDueForCompute(trnId VARCHAR (10),instalmentNoNow INT) 

RETURNS INT

DETERMINISTIC

BEGIN

DECLARE existsIn INT;


SELECT COUNT(id) INTO existsIn FROM amdapenaltycomputeinstalmentnow WHERE strn_id=trnId AND instalmentNo=instalmentNoNow AND lastAccrued<=NOW();




RETURN existsIn;

END ##
DELIMITER ;

 INSERT INTO loanArrearsSettings VALUES (NULL,0,4,1,1,10.0,2,1,1);

 /* DROP TABLE IF EXISTS amdaPenaltyComputeNow;
  DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails;
   DROP TABLE IF EXISTS amdaPenaltyComputeInstalmentNow; */

CREATE TABLE amdaPenaltyComputeNow (
   id INT(11) NOT NULL AUTO_INCREMENT,
   loanTrnId VARCHAR(20),
  penaltyStatus INT(11) NOT NULL, -- ONGOING=1,STOPPED=2
  lastAccrued DATE,--  DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=30 DEFAULT CHARACTER SET=utf8;

  /* DROP TABLE IF EXISTS amdaPenaltyComputeNowDetails; */
CREATE TABLE amdaPenaltyComputeNowDetails (
   id INT(11) NOT NULL AUTO_INCREMENT,
   loanTrnId VARCHAR(20),
   instalmentNo INT(11) NOT NULL, 
  instalmentComputeStatus INT(11)  NULL, -- 1=ONGOING,2=STOPPED
  loanComputeStatus INT(11)  NULL,  -- 1=ONGOING,2=STOPPED
  dateComputed DATE, 
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=20 DEFAULT CHARACTER SET=utf8;



CREATE TABLE amdaPenaltyComputeInstalmentNow (
   id INT(11) NOT NULL AUTO_INCREMENT,
   strn_id VARCHAR(10),
  instalmentNo INT(11) NOT NULL, 
  lastAccrued DATE, --  DAILY=1,WEEKILY=2,FORTINIGHLY=3,MONTHLY=4,QUATERLY=5,HALFYEARLY=6,ANNUALLY=7,BIENNIALLY=8
  PRIMARY KEY(id)
)

ENGINE=innoDB AUTO_INCREMENT=2 DEFAULT CHARACTER SET=utf8;






DROP PROCEDURE  IF EXISTS computeAMDAPenalty;

DELIMITER ##

CREATE PROCEDURE computeAMDAPenalty(IN trnId VARCHAR(10),IN theLoanId VARCHAR(10)) 


BEGIN

DECLARE thePenalty,theBalance  DOUBLE;

DECLARE theLooper,thePenaltyS,existsIn1,existsIn2,existsIn3 INT;

DECLARE endDate DATE;


SELECT COUNT(id) INTO existsIn1 FROM amdaPenaltyComputeNow WHERE strn_id=trnId;

IF existsIn1 <=0 THEN

INSERT INTO amdapenaltycomputenow VALUES(NULL,trnId,1,NOW());

END IF;

SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=trnId;

SELECT penaltyStatus INTO thePenaltyS FROM amdapenaltycomputenow WHERE strn_id=trnId;

IF endDate>=(NOW()-INTERVAL 1 DAY) THEN

 SELECT outStandingInstalmentsAMDA(trnId),firstInstalmentsAMDA(trnId) INTO @noInstalments,@firstIntalment; 

 SELECT instalment_no INTO @firstIntalment FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId ORDER BY trn_id ASC LIMIT 1;

SELECT COUNT(trn_id)  INTO @noInstalments FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId;
SET theLooper=1,thePenalty=0;
 REPEAT 
 
  SELECT 'IN',@firstIntalment; 
 
SELECT COUNT(id) INTO existsIn2 FROM amdaPenaltyComputeInstalmentNow WHERE strn_id=trnId AND instalmentNo=@firstIntalment;
 
 IF existsIn2<=0 THEN

INSERT INTO amdaPenaltyComputeInstalmentNow VALUES(NULL,trnId,@firstIntalment,NOW());

END IF;


SELECT COUNT(id) INTO existsIn3 FROM amdapenaltycomputeinstalmentnow WHERE strn_id=trnId AND instalmentNo=@firstIntalment AND lastAccrued<=NOW();

  IF existsIn3>0 THEN
 
   SELECT computeThePenaltyNowAMDA(trnId,@firstIntalment) INTO @thePenaltyComputed;
   SELECT @thePenaltyComputed;
    SET thePenalty=thePenalty+@thePenaltyComputed;
    
    END IF;
    
  SET @firstIntalment=@firstIntalment+1;
   
  SET theLooper=theLooper+1;
   
  UNTIL theLooper>@noInstalments END REPEAT;
  
  SET @noInstalments=0,@firstIntalment=0;

ELSEIF thePenaltyS=1 THEN

SELECT balance_due INTO theBalance FROM new_loan_appstore WHERE trn_id=trnId;

SET thePenalty=(theBalance*.1);

UPDATE amdapenaltycomputenow SET penaltyStatus=2;

END IF;

SELECT  thePenalty;

END ##
DELIMITER ;  




DROP PROCEDURE IF EXISTS InterestManagementForLendersNonCompounded;

DELIMITER //

CREATE PROCEDURE InterestManagementForLendersNonCompounded() READS SQL DATA BEGIN

 DECLARE loanIdZ,theTenure VARCHAR(30); /* This is the unique itentfier for an active loan and the tenure type which helps us to select only single instalment loans to be managed*/


 DECLARE l_done INTEGER DEFAULT 0; /*  Variable controlling the cusor*/

DECLARE lastDate,originalDueDate DATE; /*  The variable lastDate holds the value of the last due date since the last transaction */
/*  The variable originalDueDate holds the value of the original due date since the last transaction*/
 DECLARE InterestRate,princinpalRemaining,interestInveolved,interestInvPaid,InterestInveRemaining,totalComputedInterest,totalCompuInteresPaid,totalInterest, 
  totalCompuInteresRemaing Double;/*   InterestRate is the rate used--- */

  DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id  FROM pmms_loans.new_loan_appstore WHERE loan_cycle_status='Disbursed'; /* cursor for iterating through each borrower's account */
 


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1; /*Decclare the variable for testing whether the cursor has ended */


 OPEN forSelectingLoanIds; /* Open the cursor holding loan ids for each customer */


accounts_loop: LOOP  /*Loop through the loanIds */

 FETCH forSelectingLoanIds into loanIdZ; /*Pick the loan id into the variable loanIdz */
-- SELECT loanIdZ;
SELECT loan_tenure INTO theTenure FROM new_loan_appstore WHERE loan_id=loanIdZ; /* Select the loan tenure which will help us to ensure that we process only single monthly instalment loans */
 
 IF l_done=1 THEN  /*check whether the cusor sitll holds more values and if not terminate loop */

LEAVE accounts_loop;

 END IF;


IF theTenure= '1.0 MONTHS' THEN   /*Only single monthly isntalment loans should be considered */

 SELECT instalment_next_due_date, interest_rate,TotalPrincipalRemaining INTO lastDate,InterestRate,princinpalRemaining FROM new_loan_appstore WHERE loan_id=loanIdZ; /*The due date since the last instalment is stored in the instalment_next_due_date column */
SELECT  lastDate,InterestRate,princinpalRemaining;
SELECT instalment_due_date INTO originalDueDate FROM new_loan_appstoreamort WHERE master2_id=loanIdZ; /* The instalment due date is the last due date */

 SELECT interestinvoRemaining INTO totalInterest FROM interestcomputed WHERE loanId=loanIdZ AND loanStatusI='Pending' ORDER BY TrnId ASC Limit 1; 
 
 /*  The last interest computed */

/* SELECT totalInterest ,loanIdZ; */

 Date_loop: LOOP /*Loop through the due dates since the last duedate */
 SET @Ended=0; 
 /* SELECT lastDate;  *//*Testing*/


 IF lastDate>=current_date() THEN /*Test whether the arrears last date is more than today's date */

 SET @Ended=1;

LEAVE Date_loop;

END IF;


IF @Ended=0 THEN  /**/
 SET interestInveolved=((InterestRate*princinpalRemaining)/1200);
/*Compute the insterest using the remaining princimpal amount */

SET totalInterest=totalInterest+interestInveolved; /* Compute the total interest */





SET @pureDate=lastDate;  

CALL newDateConverted(@pureDate);

SET lastDate= @pureDate;  /*  */


 CALL updateAccountsAfter(loanIdZ,lastDate,interestInveolved,princinpalRemaining);/*Update the original loan schedule */


INSERT INTO interestComputed VALUES(null,loanIdZ,@pureDate,princinpalRemaining,interestInveolved,0.0,interestInveolved,totalInterest,0.0,totalInterest,'Pending'); 

END IF;

END LOOP Date_loop;

END IF;



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

END //

DELIMITER ; /* */ 




DROP FUNCTION IF EXISTS computeAMDAPenalty;

DELIMITER ##

CREATE FUNCTION computeAMDAPenalty(trnId VARCHAR(10)) 

RETURNS DOUBLE 

DETERMINISTIC

BEGIN

DECLARE thePenalty,theBalance  DOUBLE;

DECLARE theLooper,thePenaltyS,existsIn1,existsIn2,existsIn3 INT;

DECLARE endDate DATE;


SELECT COUNT(id) INTO existsIn1 FROM amdaPenaltyComputeNow WHERE strn_id=trnId;


/* SELECT existsIn1; */

IF existsIn1 <=0 THEN

INSERT INTO amdapenaltycomputenow VALUES(NULL,trnId,1,NOW());

END IF;

SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=trnId;

SELECT penaltyStatus INTO thePenaltyS FROM amdapenaltycomputenow WHERE strn_id=trnId;

IF endDate>=(NOW()-INTERVAL 1 DAY) THEN

/* SELECT outStandingInstalmentsAMDA(trnId),firstInstalmentsAMDA(trnId) INTO @noInstalments,@firstIntalment; */

SELECT instalment_no INTO @firstIntalment FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId ORDER BY trn_id ASC LIMIT 1;

SELECT COUNT(trn_id)  INTO @noInstalments FROM new_loan_appstoreamort WHERE instalment_due_date<=(NOW()-INTERVAL 1 DAY) AND NOT instalment_status='P' AND master1_id=trnId;
SET theLooper=1,thePenalty=0;
 REPEAT
 
IF ISNULL(@firstIntalment) THEN
SET @firstIntalment=0;
END IF;
 
SELECT COUNT(id) INTO existsIn2 FROM amdaPenaltyComputeInstalmentNow WHERE strn_id=trnId AND instalmentNo=@firstIntalment;
 /* SELECT existsIn2; */
 IF existsIn2<=0 THEN

INSERT INTO amdaPenaltyComputeInstalmentNow VALUES(NULL,trnId,@firstIntalment,NOW());

END IF;


SELECT COUNT(id) INTO existsIn3 FROM amdapenaltycomputeinstalmentnow WHERE strn_id=trnId AND instalmentNo=@firstIntalment AND lastAccrued<=NOW();

  IF existsIn3>0 THEN
 /* SELECT trnId,@firstIntalment; */
   SELECT computeThePenaltyNowAMDA(trnId,@firstIntalment) INTO @thePenaltyComputed;
   
    SET thePenalty=thePenalty+@thePenaltyComputed;
    
    END IF;
    
  SET @firstIntalment=@firstIntalment+1;
   
  SET theLooper=theLooper+1;
   /* SELECT theLooper, @firstIntalment, @noInstalments; */

  UNTIL theLooper=@noInstalments OR @noInstalments=0 END REPEAT;
  
  SET @noInstalments=0,@firstIntalment=0;

ELSEIF thePenaltyS=1 THEN

SELECT balance_due INTO theBalance FROM new_loan_appstore WHERE trn_id=trnId;

SET thePenalty=(theBalance*.1);

UPDATE amdapenaltycomputenow SET penaltyStatus=2;

END IF;

RETURN thePenalty;

END ##
DELIMITER ;




DROP PROCEDURE IF EXISTS theAMDAPenaltyAdd;
DELIMITER //
CREATE PROCEDURE theAMDAPenaltyAdd() READS SQL DATA 

BEGIN

DECLARE lDone1 INTEGER DEFAULT 0;

DECLARE instalmentNo,theInstalmentNo,existsIn1,instalmentStatus,theInstalmentStatus,counter,firstInstalment,idExists INT; 

DECLARE newBalance,oldBalance,computedPenalty,newPenaltyRemaining,oldPenaltyRemaining,newInstalmentAmount,oldInstalmentAmount,newLoanPenalty,oldLoanPenalty,newLoanPenaltyRemaining,oldLoanPenaltyRemaining,newInstalmnetAmount,oldInstalmnetAmount DOUBLE;

DECLARE penaltyStatusN,thePenaltyS,numberOfItems  INTEGER;
DECLARE endDate DATE;
DECLARE trnId VARCHAR(20);
DECLARE forselectingLoanTrnId CURSOR FOR SELECT trn_id  FROM new_loan_appstore  WHERE loan_cycle_status='WrittenOff' AND  NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') ;

/* AND isInArrearsAMDA(trn_id)>0 AND typeOfInstalment(trn_id)>0  */
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1=1;
 

OPEN forselectingLoanTrnId; 

loanTrnIdLoop: LOOP 

FETCH forselectingLoanTrnId INTO trnId;
/* SELECT trnId;  */
 IF lDone1=1 THEN
LEAVE loanTrnIdLoop;
 END IF;
 

 SELECT instalment_no INTO firstInstalment FROM new_loan_appstoreamort WHERE NOT instalment_status='P' AND master1_id=trnId ORDER BY instalment_no DESC LIMIT 1;


SELECT TotalLoanPenaltyRemaining INTO oldPenaltyRemaining FROM new_loan_appstore WHERE trn_id=trnId;


SELECT  InstalmentRemaining INTO oldInstalmnetAmount FROM new_loan_appstoreamort WHERE master1_id=trnId AND instalment_no=firstInstalment;

SET newInstalmnetAmount=oldInstalmnetAmount+oldPenaltyRemaining;

UPDATE new_loan_appstoreamort SET LoanPenalty=oldPenaltyRemaining, LoanPenaltyRemaining=oldPenaltyRemaining, InstalmentRemaining=newInstalmnetAmount WHERE instalment_no=firstInstalment AND master1_id=trnId;



SET lDone1=0;
END LOOP loanTrnIdLoop;
CLOSE forselectingLoanTrnId;
END //
DELIMITER ;