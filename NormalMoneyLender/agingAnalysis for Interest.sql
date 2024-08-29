DROP PROCEDURE IF EXISTS `agingAnalysis`;
DELIMITER //
CREATE  PROCEDURE `agingAnalysis`()
    READS SQL DATA
BEGIN
   
 DECLARE l_done,ID,arrears,numberOfGaurantors INT;

 DECLARE loanPort,paidport,remainport,prince,princepaid,princeremain,p_remain,i_remain,interestRem DOUBLE;

 DECLARE customerContactNumber,loanId,customerName,TrnDate,DisDate,theLoanStatus,gaurantorName1,gaurantorContact1,gaurantorContact2,gaurantorName2 VARCHAR(45);

DECLARE forSelectingLoanIds CURSOR FOR SELECT loan_id   FROM new_loan_appstore WHERE loan_cycle_status='Disbursed' OR loan_cycle_status='Renewed' ;
 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
SET ID =0;


DROP TABLE IF EXISTS aging_loan_analysis1x;

CREATE TEMPORARY  TABLE aging_loan_analysis1x(id_1x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_1x));

DROP TABLE IF EXISTS aging_loan_analysis1;

CREATE TEMPORARY  TABLE aging_loan_analysis1(id_1 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT, loan_deadline VARCHAR(60),PRIMARY KEY (id_1));

DROP TABLE IF EXISTS aging_loan_analysis2;

CREATE TEMPORARY  TABLE aging_loan_analysis2(id_2 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_2))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3;

CREATE TEMPORARY  TABLE aging_loan_analysis3(id_3 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_3))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4;

CREATE TEMPORARY  TABLE aging_loan_analysis4(id_4 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_4))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5;

CREATE TEMPORARY  TABLE aging_loan_analysis5(id_5 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_5))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6;

CREATE TEMPORARY  TABLE aging_loan_analysis6(id_6 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis7;

CREATE TEMPORARY  TABLE aging_loan_analysis7(id_7 VARCHAR(60),customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining VARCHAR(60),principal_remaining VARCHAR(60),interest_remaining VARCHAR(60),principal_inarrears VARCHAR(60),interest_inarrears VARCHAR(60),number_of_days_in_arrears VARCHAR(60),loan_deadline VARCHAR(60))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS aging_loan_analysis2x;

CREATE TEMPORARY  TABLE aging_loan_analysis2x(id_2x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_2x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis3x;

CREATE TEMPORARY  TABLE aging_loan_analysis3x(id_3x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_3x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis4x;

CREATE TEMPORARY  TABLE aging_loan_analysis4x(id_4x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_4x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis5x;

CREATE TEMPORARY  TABLE aging_loan_analysis5x(id_5x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60),PRIMARY KEY (id_5x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis6x;

CREATE TEMPORARY  TABLE aging_loan_analysis6x(id_6x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_6x))ENGINE = InnoDB
AUTO_INCREMENT =0
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis8x;

CREATE TEMPORARY  TABLE aging_loan_analysis8x(id_8x INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8x))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS aging_loan_analysis8;

CREATE TEMPORARY  TABLE aging_loan_analysis8(id_8 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_8))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


DROP TABLE IF EXISTS aging_loan_analysis9;

CREATE TEMPORARY  TABLE aging_loan_analysis9(id_9 INTEGER NOT NULL AUTO_INCREMENT,customer_name VARCHAR(60),customer_contact VARCHAR(60),date_taken VARCHAR(60),due_date VARCHAR(60),loans_remaining DOUBLE,principal_remaining DOUBLE,interest_remaining DOUBLE,principal_inarrears DOUBLE,interest_inarrears DOUBLE,number_of_days_in_arrears INT,loan_deadline VARCHAR(60), PRIMARY KEY (id_9))ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

 OPEN forSelectingLoanIds;

accounts_loop: LOOP 



 FETCH forSelectingLoanIds into loanId;
 


 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;

SELECT pl.applicant_account_name,m.mobile1,pl.instalment_start_date,pl.princimpal_amount,pl.TotalPrincipalRemaining,pl.TotalInterestRemaining,(pl.TotalPrincipalRemaining+pl.TotalInterestRemaining),pl.loan_cycle_status INTO customerName, customerContactNumber,TrnDate,remainport,princeremain,interestRem,p_remain,theLoanStatus FROM pmms.master m INNER JOIN pmms_loans.new_loan_appstore pl ON pl.applicant_account_number=m.account_number WHERE  pl.loan_id=loanId;



SELECT (SUM(PrincipalRemaining)+SUM(InterestRemaing)),numberOfDayInArrears(loanId) INTO i_remain,arrears FROM new_loan_appstoreamort WHERE master2_id=loanId AND instalment_due_date<=DATE(NOW()) AND NOT instalment_status='P';


SELECT COUNT(id) INTO numberOfGaurantors FROM gaurantors WHERE loanTrnId=loanId;

IF numberOfGaurantors=0 THEN

SET gaurantorName1='-',gaurantorContact1='-',gaurantorName2='-',gaurantorContact2='-';
END IF;

IF numberOfGaurantors=1 THEN
SELECT gaurantorsName,gaurantorsContact1 INTO gaurantorName1,gaurantorContact1 FROM gaurantors WHERE loanTrnId=theTrnId;

SET gaurantorName2='-',gaurantorContact2='-';
END IF;



IF numberOfGaurantors=2 THEN

SET @sql_text2 = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName1,@gaurantorContact1 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id ASC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2 FROM @sql_text2;
  EXECUTE stmt2;
DROP PREPARE stmt2;






SET @sql_text2X = concat(CAST("SELECT gaurantorsName,gaurantorsContact1 INTO @gaurantorName2,@gaurantorContact2 FROM gaurantors WHERE loanTrnId=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),loanId,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));

  PREPARE stmt2X FROM @sql_text2X;
  EXECUTE stmt2X;
DROP PREPARE stmt2X;


SELECT @gaurantorName1,@gaurantorContact1 INTO gaurantorName1,gaurantorContact1;
SELECT @gaurantorName2,@gaurantorContact2 INTO gaurantorName2,gaurantorContact2;
END IF;

 SET ID=ID+1;

 IF ISNULL(customerContactNumber) THEN
SET customerContactNumber="-";
 END IF;

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



  IF ISNULL(@INST) THEN
SET @INST=DATE_FORMAT(NOW(),'%d/%m/%Y');
 END IF;

IF theLoanStatus='Disbursed' THEN

INSERT INTO aging_loan_analysis1 VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;


IF theLoanStatus='Renewed' THEN

INSERT INTO aging_loan_analysis1x VALUES (ID,customerName,customerContactNumber,DATE_FORMAT(TrnDate,'%d/%m/%Y'),@INST,remainport,princeremain,interestRem,p_remain,i_remain,arrears,DATE_FORMAT(DATE_ADD(TrnDate,INTERVAL 30 DAY),'%d/%m/%Y'));

END IF;



    SET l_done=0;
 END LOOP accounts_loop;

 CLOSE forSelectingLoanIds;

SELECT COUNT(id_1) INTO @port0  FROM aging_loan_analysis1 ;
 SELECT COUNT(id_1) INTO @port1  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;
 SELECT COUNT(id_1) INTO @port2  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
  SELECT COUNT(id_1) INTO @port3  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
   SELECT COUNT(id_1) INTO @port4  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1) INTO @port5  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360;



 
IF @port1 >0 THEN

  INSERT INTO  aging_loan_analysis2( 
  id_2,
  customer_name ,
  customer_contact ,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,
  loan_deadline
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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

  
END IF;

IF @port2 >0 THEN

  INSERT INTO  aging_loan_analysis3( 
  id_3,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline
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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
 
END IF;

IF @port3 >0 THEN


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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port4 >0 THEN

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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;

IF @port5 >0 THEN

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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 
END IF;


SELECT COUNT(id_1x) INTO @port0x  FROM aging_loan_analysis1x;
 SELECT COUNT(id_1x) INTO @port1x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

 SELECT COUNT(id_1x) INTO @port2x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  SELECT COUNT(id_1x) INTO @port3x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;

   SELECT COUNT(id_1x) INTO @port4x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

 SELECT COUNT(id_1x) INTO @port5x  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360;


IF @port1x >0 THEN

  INSERT INTO  aging_loan_analysis2x( 
  id_2x,
  customer_name ,
  customer_contact ,
  date_taken,
  due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30 ORDER BY number_of_days_in_arrears ASC;
   

END IF;

IF @port2x >0 THEN

  
  INSERT INTO  aging_loan_analysis3x( 
  id_3x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port3x >0 THEN
 
    INSERT INTO  aging_loan_analysis4x( 
  id_4x,
  customer_name ,
  customer_contact ,
     date_taken,
     due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ORDER BY number_of_days_in_arrears ASC;
 
END IF;



IF @port4x >0 THEN

    INSERT INTO  aging_loan_analysis5x( 
  id_5x,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ORDER BY number_of_days_in_arrears ASC;
  
END IF;



IF @port5x >0 THEN

    INSERT INTO  aging_loan_analysis6x( 
  id_6x,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360 ORDER BY number_of_days_in_arrears ASC;
 

END IF;
 IF @port0 >0 THEN 
   
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port0,
  'TOTAL ACTIVE LOANS' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1; 
END IF;
 IF @port0x >0 THEN 
   
INSERT INTO  aging_loan_analysis8x( 
  id_8x,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
 @port0x,
  'TOTAL RENEWED LOANS' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,  "-" FROM aging_loan_analysis1x; 

    
END IF;

    IF @port0 >0 OR   @port0x >0 THEN   
INSERT INTO  aging_loan_analysis9( 
  id_9,
  customer_name ,
  customer_contact ,
    date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  (@port0+@port0x),
  'OVERALL TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( ot.loans_remaining) ,
  SUM(ot.principal_remaining) ,
  SUM(ot.interest_remaining),
  SUM(ot.principal_inarrears) ,
  SUM(ot.interest_inarrears) ,
 "-" ,"-" FROM (SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1 UNION ALL SELECT loans_remaining,principal_remaining,interest_remaining,principal_inarrears,interest_inarrears FROM aging_loan_analysis1x) ot;  
 END IF;

 IF @port0 >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","ACTIVE LOANS","-","-","-","-","-","-","-");
END IF;
IF @port1 >0 THEN
 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
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
  number_of_days_in_arrears  ,loan_deadline
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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis2;

 
IF @port1 >0 THEN  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port1,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" , "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears<30;

END IF;


  
IF @port2 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");
  
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
  number_of_days_in_arrears   ,loan_deadline
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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis3;
    
    
IF @port2 >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port2,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,
 "-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;

  

END IF;

  IF @port3 >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","PORTFOLIO AT RISK","-","-","-","-","-","-","-",
 "-" );

END IF;

   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
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
  number_of_days_in_arrears   ,loan_deadline FROM aging_loan_analysis4;
  
  
IF @port3 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears   ,loan_deadline
  ) SELECT 
  @port3,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-",
 "-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;

  IF @port4 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
    
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
  number_of_days_in_arrears  ,loan_deadline
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
  number_of_days_in_arrears  ,loan_deadline FROM aging_loan_analysis5;

  
IF @port4 >0 THEN
   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears  ,loan_deadline
  ) SELECT 
  @port4,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-" FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5 >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-","-");
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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis6;
  
  
IF @port5 >0 THEN
 
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port5,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-" ,"-"  FROM aging_loan_analysis1 WHERE number_of_days_in_arrears>=360; 

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
  number_of_days_in_arrears ,loan_deadline
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
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis8;


 IF @port0x >0 THEN 
 INSERT INTO aging_loan_analysis7 VALUES("-","-","-","-","RENEWED LOANS","-","-","-","-","-","-","-");
END IF;

IF @port1x >0 THEN

 INSERT INTO aging_loan_analysis7 VALUES("-","1-30","-","RENEWED PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_2x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis2x;
    
    IF @port1x >0 THEN
  
  INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port1x,
  'TOTAL' ,
  "-" ,
  "-" ,
  "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears<30;

END IF;
  
  
IF @port2x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","30-60","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");

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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_3x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis3x;
    
    
IF @port2x >0 THEN  
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port2x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=30 AND number_of_days_in_arrears<60 ;
END IF;
  
  IF @port3x >0 THEN

   INSERT INTO aging_loan_analysis7 VALUES("-","60-90","-","RENEWED PORTFOLIO AT RISK","-","-","-","-","-","-","-","-");
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_4x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis4x;
  
  IF @port3x >0 THEN

   
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port3x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=60 AND number_of_days_in_arrears<90 ;
END IF;
  
IF @port4x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","90-360","-","RENEWED NON PERFORMING PORTFOLIO","-","-","-","-","-","-","-","-");

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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_5x,
  customer_name ,
  customer_contact ,
      date_taken,due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis5x;

  IF @port4x >0 THEN
INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
    date_taken,
    due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port4x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=90 AND number_of_days_in_arrears<360 ;

END IF;
  
  
  IF @port5x >0 THEN
   INSERT INTO aging_loan_analysis7 VALUES("-","360 AND Above","-","RENEWED PORTFOLIO DUE FOR WRITE OFF","-","-","-","-","-","-","-","-");
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_6x,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis6x;
  
  
IF @port5x >0 THEN
   
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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
  @port5x,
  'TOTAL' ,
  "-" ,
    "-" ,
      "-" ,
 SUM( loans_remaining) ,
  SUM(principal_remaining) ,
  SUM(interest_remaining),
  SUM(principal_inarrears) ,
  SUM(interest_inarrears) ,
 "-","-"  FROM aging_loan_analysis1x WHERE number_of_days_in_arrears>=360; 

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
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_8x,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears,loan_deadline  FROM aging_loan_analysis8x;

 
IF @port0x >0 OR @port0 >0 THEN
   INSERT INTO  aging_loan_analysis7( 
  id_7,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
    principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline
  ) SELECT 
   id_9,
  customer_name ,
  customer_contact ,
      date_taken,
      due_date,
  loans_remaining ,
  principal_remaining ,
  interest_remaining,
  principal_inarrears ,
  interest_inarrears ,
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis9;
END IF;

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
  number_of_days_in_arrears ,loan_deadline FROM aging_loan_analysis7;
  
END //
DELIMITER ;