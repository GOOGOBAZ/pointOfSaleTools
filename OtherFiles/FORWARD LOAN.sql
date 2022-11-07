

-- -----------------------------------------------------
-- forwardTheBranchApprovalLoans

/*  */

-- -----------------------------------------------------


DROP PROCEDURE IF EXISTS forwardTheBranchApprovalLoans;

DELIMITER $$

CREATE PROCEDURE forwardTheBranchApprovalLoans(IN data JSON) BEGIN 

DECLARE theLoanType VARCHAR(60);
DECLARE theResponseStage VARCHAR(100);
DECLARE theLoanAmount DOUBLE;
DECLARE theLoanActionId,currentBranch,theCurrentDefferedStage,theDefferedPathId,otherPathExists INT;

SELECT loanAction.loanActionId,loanAction.fkBranchIdLoanAction INTO theLoanActionId ,currentBranch FROM loan INNER JOIN loanAction ON loanAction.fkLoanIdLoanAction=loan.loanId WHERE loan.loanId=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId'));

SELECT defferedLoansPathId,defferedLoansPathStageId INTO theDefferedPathId, theCurrentDefferedStage FROM defferedloanspath INNER JOIN loan ON defferedloanspath.fkLoanIdDefferedLoansPath=loan.loanId WHERE loan.loanId=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId')) AND defferedloanspath.defferedLoansPathStatus=1 ORDER BY defferedloanspath.defferedLoansPathId DESC LIMIT 1;


SELECT COUNT(defferedLoansPathId) INTO otherPathExists FROM defferedloanspath INNER JOIN loan ON defferedloanspath.fkLoanIdDefferedLoansPath=loan.loanId WHERE loan.loanId=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId')) AND defferedloanspath.defferedLoansPathStatus=1;

 -- loanActionMovementStatus =1=CREATED,2=FOWARDED,3=RECEIVED,4=DISBURSED,5=DEFFERED

 --  loanActionActionStatus =0=NONE,1=APPROVED,2=DEFFERED,3=REJECTED,4=RECTIFIED
  
 IF otherPathExists<=0 THEN

UPDATE loanaction INNER JOIN loan ON loanaction.fkLoanIdLoanAction=loan.loanId SET loanaction.loanActionMovementStatus=2,  loanaction.loanActionMovementBy=JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),loanaction.loanActionMovementAt=CURRENT_TIMESTAMP,loanaction.loanActionComment=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanComment')),loanaction.fkRoleIdLoanAction=theCurrentDefferedStage WHERE loan.loanId=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId'));


INSERT INTO loanActionDetails VALUES(NULL,2,JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),CURRENT_TIMESTAMP,0,JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),CURRENT_TIMESTAMP,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanComment')),currentBranch,theCurrentDefferedStage,theLoanActionId);

ELSEIF otherPathExists>0 THEN

UPDATE loanaction INNER JOIN loan ON loanaction.fkLoanIdLoanAction=loan.loanId SET loanaction.loanActionMovementStatus=2,  loanaction.loanActionMovementBy=JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),loanaction.loanActionMovementAt=CURRENT_TIMESTAMP,loanaction.loanActionComment=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanComment')),loanaction.fkRoleIdLoanAction=theCurrentDefferedStage WHERE loan.loanId=JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanId'));


INSERT INTO loanActionDetails VALUES(NULL,2,JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),CURRENT_TIMESTAMP,0,JSON_UNQUOTE(JSON_EXTRACT(data,'$.userId')),CURRENT_TIMESTAMP,JSON_UNQUOTE(JSON_EXTRACT(data,'$.loanComment')),currentBranch,theCurrentDefferedStage,theLoanActionId);


END IF;

SET theResponseStage='Loan sent successfully to BRANCH EXIT';


SELECT theResponseStage;

END $$

DELIMITER ;


