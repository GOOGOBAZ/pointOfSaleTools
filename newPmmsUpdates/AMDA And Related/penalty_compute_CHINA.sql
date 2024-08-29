

DROP PROCEDURE IF EXISTS theAMDAPenaltyComputation;
DELIMITER //
CREATE PROCEDURE theAMDAPenaltyComputation() READS SQL DATA 

BEGIN

DECLARE lDone1 INTEGER DEFAULT 0;

DECLARE instalmentNo,theInstalmentNo,existsIn1,instalmentStatus,theInstalmentStatus,counter,firstInstalment,idExists ,loanTrnId,firstTimeComputed INT; 

DECLARE newBalance,oldBalance,computedPenalty,newPenaltyRemaining,oldPenaltyRemaining,newInstalmentAmount,oldInstalmentAmount,newLoanPenalty,oldLoanPenalty,newLoanPenaltyRemaining,oldLoanPenaltyRemaining,newInstalmnetAmount,oldInstalmnetAmount,oldTotalAmount,oldTotalAmortAmt,newTotalAmount,newTotalAmortAmt DOUBLE;

DECLARE penaltyStatusN,thePenaltyS,numberOfItems,daysOverdue,weeksOverdue  INTEGER;
DECLARE endDate,instalmentDueDate,lastPenaltyDate DATE;
DECLARE trnId VARCHAR(20);
DECLARE forselectingLoanTrnId CURSOR FOR SELECT trn_id  FROM new_loan_appstore  WHERE loan_cycle_status='Disbursed' AND  NOT (loan_tenure='1.0 MONTHS' OR  loan_tenure='1 MONTHS') ;


 DECLARE CONTINUE HANDLER FOR NOT FOUND SET lDone1=1;
 

OPEN forselectingLoanTrnId; 

loanTrnIdLoop: LOOP 

FETCH forselectingLoanTrnId INTO trnId;

 IF lDone1=1 THEN
LEAVE loanTrnIdLoop;
 END IF;
 
SELECT penaltyStatus INTO penaltyStatusN FROM loanarrearssettings LIMIT 1;
 
 IF penaltyStatusN=1 THEN 

--  SELECT instalment_end_date INTO endDate FROM new_loan_appstore WHERE trn_id=trnId;
-- SET thePenaltyS=NULL;
-- SELECT penaltyStatus INTO thePenaltyS FROM amdapenaltycomputenow WHERE loanTrnId=trnId LIMIT 1;
 


-- IF ISNULL(thePenaltyS) THEN
-- SET thePenaltyS=1;
-- END IF;
  
-- IF endDate>=(NOW()-INTERVAL 1 DAY) THEN



 SELECT COUNT(instalment_no) INTO numberOfItems FROM new_loan_appstoreamort WHERE instalment_due_date<=NOW() AND NOT instalment_status='P' AND master1_id=trnId;



 SELECT instalment_no INTO firstInstalment FROM new_loan_appstoreamort WHERE instalment_due_date<=NOW() AND NOT instalment_status='P' AND master1_id=trnId ORDER BY instalment_no ASC LIMIT 1;

SET counter=0;

IF numberOfItems>0 THEN

instalmentNoLoop:REPEAT 
SET counter=counter+1;  


 -- Find the first overdue instalment
    SELECT  instalment_due_date INTO  instalmentDueDate
    FROM new_loan_appstoreamort
    WHERE instalment_due_date <= NOW() AND NOT instalment_status = 'P' AND master1_id = trnId
    ORDER BY instalment_no ASC
    LIMIT 1;


      -- Retrieve the last penalty application date and first-time computation flag
        SELECT MAX(dateComputed), MAX(penaltyApplied) INTO lastPenaltyDate, firstTimeComputed FROM amdapenaltycomputenowdetails WHERE strn_id = trnId AND instalmentNo = firstInstalment;

 -- Calculate days overdue and determine if penalty should be applied
        IF ISNULL(lastPenaltyDate) THEN
            SET daysOverdue = DATEDIFF(NOW(), instalmentDueDate),firstTimeComputed=0,lastPenaltyDate=NOW();
        ELSE
            SET daysOverdue = DATEDIFF(NOW(), lastPenaltyDate);
        END IF;
SELECT firstTimeComputed,daysOverdue,lastPenaltyDate;

     -- Apply penalty if it's the first computation or at least 7 days have passed since the last penalty
     IF firstTimeComputed = 0 OR (daysOverdue >= 7 AND DATE(lastPenaltyDate) < CURRENT_DATE()) THEN

SELECT balance_due,TotalLoanPenaltyRemaining,instalment_amount,total_loanAmount INTO oldBalance,oldPenaltyRemaining,oldInstalmentAmount,oldTotalAmount FROM new_loan_appstore WHERE trn_id=trnId;

SELECT LoanPenalty, LoanPenaltyRemaining, InstalmentRemaining,instalment_amount INTO oldLoanPenalty,oldLoanPenaltyRemaining,oldInstalmnetAmount,oldTotalAmortAmt FROM new_loan_appstoreamort WHERE master1_id=trnId AND instalment_no=firstInstalment;



SET computedPenalty=(oldInstalmnetAmount*.02);



SET newBalance=oldBalance+computedPenalty,newPenaltyRemaining=oldPenaltyRemaining+computedPenalty,newInstalmentAmount=oldInstalmentAmount+computedPenalty,newLoanPenalty=oldLoanPenalty+computedPenalty,newLoanPenaltyRemaining=oldLoanPenaltyRemaining+computedPenalty,newInstalmnetAmount=oldInstalmnetAmount+computedPenalty,newTotalAmount=oldTotalAmount+computedPenalty,newTotalAmortAmt=oldTotalAmortAmt+computedPenalty;


--  SELECT COUNT(id) INTO idExists FROM amdapenaltycomputenowdetails WHERE loanTrnId=trnId AND instalmentNo=firstInstalment;

-- IF idExists<=0 THEN 

-- Record the penalty application
            INSERT INTO amdapenaltycomputenowdetails(strn_id, instalmentNo, amountComputed,
            dateComputed, 
            penaltyApplied)
            VALUES (trnId, firstInstalment, computedPenalty, NOW(), IF(ISNULL(lastPenaltyDate), 1, 0));

-- END IF;

UPDATE new_loan_appstore SET balance_due=newBalance,TotalLoanPenaltyRemaining=newPenaltyRemaining,instalment_amount=newInstalmentAmount,total_loanAmount=newTotalAmount WHERE trn_id=trnId;
UPDATE new_loan_appstore1 SET balance_due=newBalance,TotalLoanPenaltyRemaining=newPenaltyRemaining,instalment_amount=newInstalmentAmount,total_loanAmount=newTotalAmount WHERE trn_id=trnId;

SELECT newLoanPenalty,newLoanPenaltyRemaining,newInstalmnetAmount,newTotalAmortAmt,firstInstalment,trnId;
UPDATE new_loan_appstoreamort SET LoanPenalty=newLoanPenalty, LoanPenaltyRemaining=newLoanPenaltyRemaining, InstalmentRemaining=newInstalmnetAmount,instalment_amount=newTotalAmortAmt WHERE instalment_no=firstInstalment AND master1_id=trnId;



 END IF;
    SET firstInstalment=firstInstalment+1;
UNTIL counter >= numberOfItems END REPEAT; -- Corrected the UNTIL condition
 
END IF;

  
END IF;

SET lDone1=0;
END LOOP loanTrnIdLoop;
CLOSE forselectingLoanTrnId;

END //
DELIMITER ;

DROP TABLE IF EXISTS amdapenaltycomputenowdetails;
CREATE TABLE `amdapenaltycomputenowdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `strn_id` varchar(10) DEFAULT NULL,
  `instalmentNo` int(11) NOT NULL,
  `amountComputed` double DEFAULT NULL,
  `dateComputed` date DEFAULT NULL,
  `penaltyApplied` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

 CALL theAMDAPenaltyComputation();