
  DROP PROCEDURE IF EXISTS CreateAccount;
 
 	DELIMITER //

   CREATE PROCEDURE  CreateAccount (IN cat1 VARCHAR(200),IN cat2 VARCHAR(200),IN cat3 VARCHAR(200),IN cat4 VARCHAR(200),IN accountName VARCHAR(200),IN UserId VARCHAR(80) ) BEGIN
  

  
  IF cat2='Assets' THEN
  
  SELECT TemplateIdAssets INTO @originalId FROM  AccountsIdGenerators  ORDER BY TemplateIdAssets DESC Limit 1;
  
  IF @originalId<19999 THEN
  
  SET @originalId1=@originalId+1;
    UPDATE accountsidgenerators SET TemplateIdAssets=@originalId1; 
  END IF;
  
  ELSEIF cat2='Expense' THEN
  
    SELECT TemplateIdExpenses INTO @originalId FROM  AccountsIdGenerators ORDER BY TemplateIdExpenses DESC Limit 1;
	
  IF @originalId<29999 THEN
  
   SET @originalId1=@originalId+1;

  UPDATE accountsidgenerators SET TemplateIdExpenses=@originalId1;
  
  END IF;

  
    ELSEIF cat2='Revenues' THEN
  SELECT TemplateIdIncomes INTO @originalId FROM  AccountsIdGenerators ORDER BY TemplateIdIncomes DESC Limit 1;	
	IF @originalId<39999 THEN
  
   SET @originalId1=@originalId+1;
    UPDATE accountsidgenerators SET TemplateIdIncomes=@originalId1; 
  END IF;
	
    ELSEIF cat2='Liabilities' THEN
  SELECT TemplateIdLiabilities INTO @originalId FROM  AccountsIdGenerators ORDER BY TemplateIdLiabilities DESC Limit 1;	
	IF @originalId<59999 THEN
  
   SET @originalId1=@originalId+1;
  UPDATE accountsidgenerators SET TemplateIdLiabilities=@originalId1; 
  END IF;
	
	 ELSEIF cat2='Equity/Capital' THEN
	 
	  SELECT TemplateIdEquity INTO @originalId FROM  AccountsIdGenerators ORDER BY TemplateIdEquity DESC Limit 1; 
	  
	 IF @originalId<49999 THEN
  
   SET @originalId1=@originalId+1;
  UPDATE accountsidgenerators SET TemplateIdEquity=@originalId1;
  END IF;
  
  
  
  END IF;
  
  
  CALL SystemDate(@dateNow);
  CALL postingDate(@postDate);
  
  INSERT INTO AccountsTemplate VALUES(null,@dateNow,accountName,@originalId1,cat1,cat2,cat3,cat4,'Active',UserId,'NA','NA','NA');
  
   INSERT INTO balancesdb VALUES(null,@dateNow,CURDATE(),@originalId1,accountName,'0.0',userId,'NA','NA','NA');
  
  /*  SELECT TemplateId INTO @ledgerId FROM AccountsTemplate ORDER BY TemplateId DESC Limit;*/
  
  
     END//
 DELIMITER ;
 
 
 DROP PROCEDURE IF EXISTS SystemDate;
 
 	DELIMITER //
	

   CREATE PROCEDURE  SystemDate (OUT originalDate DATE  ) BEGIN
  

    SELECT SystemDate INTO originalDate FROM DatesForSystem ;

      END//
 DELIMITER ;

DROP PROCEDURE IF EXISTS postingDate;
 
 	DELIMITER //
	

   CREATE PROCEDURE  postingDate (OUT originalDate DATE  ) BEGIN
  

    SELECT PostingmDate INTO originalDate FROM  DatesForSystem;
  
  
     END//
 DELIMITER ;


DROP PROCEDURE IF EXISTS postTxn;
 
 	DELIMITER //

   CREATE PROCEDURE  postTxn (
     IN amount VARCHAR(100),
    IN narration VARCHAR(300),
   IN txnTyp VARCHAR(100),
   IN accountType VARCHAR(100),
   IN accountNumber VARCHAR(100), 
   IN BatchNumber VARCHAR(30),
   IN UserId VARCHAR(30)
   ) BEGIN
 /*DECLARE accountNamen VARCHAR(200);*/

 /*SET @accountNamen=NULL,@accountBalance='0.0';*/

  CALL postingDate(@postingDate);

     
      IF txnTyp='DEBIT' THEN

      SET @sql_text4 = concat(CAST("SELECT AccountName  FROM `accountstemplate` WHERE AccountNumber=" AS CHAR CHARACTER SET utf8),accountNumber,CAST(" INTO @accountNamenDr" AS CHAR CHARACTER SET utf8));
 
 SELECT @sql_text4,@postingDate;
  PREPARE stmt4 FROM @sql_text4;
  EXECUTE stmt4;
DROP PREPARE stmt4;
  
/*
IF @accountNamenDr IS null THEN

SET @accountNamenDr="MISSING";

    END IF;*/
       
 SET @sql_text1 = concat(CAST("SELECT Balance INTO @accountBalanceDr FROM balancesdb WHERE   accountNumber=" AS CHAR CHARACTER SET utf8),accountNumber,CAST(" AND postDate<=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@postingDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY TxnId DESC Limit 1" AS CHAR CHARACTER SET utf8));
 
 SELECT @sql_text1,@postingDate;
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

  SELECT @accountNamenDr,@accountBalanceDr;


       IF accountType='ASSET' THEN
       CALL debitAssetExpense(amount,@accountBalanceDr,@newBalanceDr); 
       CALL  updateLedgersBalancesDEBIT (accountNumber,@accountNamenDr,narration,txnTyp, BatchNumber,amount,@newBalanceDr, UserId);
       END IF;

        IF accountType='EXPENSE' THEN
        CALL debitAssetExpense(amount,@accountBalanceDr,@newBalanceDr);
   CALL  updateLedgersBalancesDEBIT (accountNumber,@accountNamenDr,narration,txnTyp, BatchNumber,amount,@newBalanceDr, UserId);
       END IF;
        
          IF accountType='LIABILITY' THEN
         CALL debitLiabilityEquityRevenue(amount,@accountBalanceDr,@newBalanceDr);
CALL  updateLedgersBalancesDEBIT (accountNumber, @accountNamenDr,narration,txnTyp, BatchNumber,amount,@newBalanceDr, UserId);

       END IF;

         IF accountType='REVENUE' THEN
        CALL debitLiabilityEquityRevenue(amount,@accountBalanceDr,@newBalanceDr);
CALL  updateLedgersBalancesDEBIT (accountNumber,@accountNamenDr,narration,txnTyp, BatchNumber,amount,@newBalanceDr, UserId);

       END IF;

         IF accountType='EQUITY' THEN

        CALL debitLiabilityEquityRevenue(amount,@accountBalanceDr,@newBalanceDr);
CALL  updateLedgersBalancesDEBIT (accountNumber, @accountNamenDr,narration,txnTyp, BatchNumber,amount,@newBalanceDr, UserId);


       END IF;

      END IF;

       



       IF txnTyp='CREDIT' THEN

      SET @sql_text4 = concat(CAST("SELECT AccountName  FROM `accountstemplate` WHERE AccountNumber=" AS CHAR CHARACTER SET utf8),accountNumber,CAST(" INTO @accountNamenCr" AS CHAR CHARACTER SET utf8));
 
 SELECT @sql_text4,@postingDate;
  PREPARE stmt4 FROM @sql_text4;
  EXECUTE stmt4;
DROP PREPARE stmt4;



 SET @sql_text1 = concat(CAST("SELECT Balance INTO @accountBalanceCr FROM balancesdb WHERE   accountNumber=" AS CHAR CHARACTER SET utf8),accountNumber,CAST(" AND postDate<=" AS CHAR CHARACTER SET utf8),CAST("'" AS CHAR CHARACTER SET utf8),@postingDate,CAST("'" AS CHAR CHARACTER SET utf8),CAST(" ORDER BY TxnId DESC Limit 1" AS CHAR CHARACTER SET utf8));
 
 SELECT @sql_text1,@postingDate;
  PREPARE stmt1 FROM @sql_text1;
  EXECUTE stmt1;
DROP PREPARE stmt1;

  SELECT @accountNamenCr,@accountBalanceCr;


        
         IF accountType='ASSET' THEN
CALL creditAssetExpense(amount,@accountBalanceCr,@newBalanceCr);
CALL  updateLedgersBalancesCREDIT (accountNumber, @accountNamenCr,narration,txnTyp, BatchNumber,amount,@newBalanceCr, UserId);

       END IF;

        IF accountType='EXPENSE' THEN
        CALL creditAssetExpense(amount,@accountBalanceCr,@newBalanceCr);
CALL  updateLedgersBalancesCREDIT (accountNumber, @accountNamenCr,narration,txnTyp, BatchNumber,amount,@newBalanceCr, UserId);

       END IF;
        
          IF accountType='LIABILITY' THEN
         CALL creditLiabilityEquityRevenue(amount,@accountBalanceCr,@newBalanceCr);
         CALL  updateLedgersBalancesCREDIT (accountNumber, @accountNamenCr,narration,txnTyp, BatchNumber,amount,@newBalanceCr, UserId);
       END IF;

         IF accountType='REVENUE' THEN
CALL creditLiabilityEquityRevenue(amount,@accountBalanceCr,@newBalanceCr);
CALL  updateLedgersBalancesCREDIT (accountNumber, @accountNamenCr,narration,txnTyp, BatchNumber,amount,@newBalanceCr, UserId);
       END IF;

         IF accountType='EQUITY' THEN
CALL creditLiabilityEquityRevenue(amount,@accountBalanceCr,@newBalanceCr);
CALL  updateLedgersBalancesCREDIT (accountNumber, @accountNamenCr,narration,txnTyp, BatchNumber,amount,@newBalanceCr, UserId);
       END IF;



      END IF;

SET @accountBalanceCr=0.0;
SET @newBalanceCr=0.0;

SET @accountBalanceDr=0.0;
SET @newBalanceDr=0.0;
     END//
 DELIMITER ;

 DROP PROCEDURE IF EXISTS debitAssetExpense;
 
 	DELIMITER //

   CREATE PROCEDURE  debitAssetExpense (IN amount DOUBLE,IN originalBalance DOUBLE,OUT computedBalance DOUBLE) BEGIN
  
  SET computedBalance=originalBalance+amount;
  
  
     END//
 DELIMITER ;

  DROP PROCEDURE IF EXISTS debitLiabilityEquityRevenue;
 
 	DELIMITER //

   CREATE PROCEDURE  debitLiabilityEquityRevenue (IN amount DOUBLE,IN originalBalance DOUBLE,OUT computedBalance DOUBLE) BEGIN
  
  SET computedBalance=originalBalance-amount;
  
     END//
 DELIMITER ;

 DROP PROCEDURE IF EXISTS creditAssetExpense;
 
 	DELIMITER //

   CREATE PROCEDURE  creditAssetExpense (IN amount DOUBLE,IN originalBalance DOUBLE,OUT computedBalance DOUBLE) BEGIN
  
  SET computedBalance=originalBalance-amount;
  
     END//
 DELIMITER ;

  DROP PROCEDURE IF EXISTS creditLiabilityEquityRevenue;
 
 	DELIMITER //

   CREATE PROCEDURE  creditLiabilityEquityRevenue (IN amount DOUBLE,IN originalBalance DOUBLE,OUT computedBalance DOUBLE) BEGIN
  
  SET computedBalance=originalBalance+amount;
  
     END//
 DELIMITER ;

 DROP PROCEDURE IF EXISTS updateLedgersBalancesDEBIT;
 
 	DELIMITER //

   CREATE PROCEDURE  updateLedgersBalancesDEBIT (
     IN accountNumberX VARCHAR(100),
     IN accountName VARCHAR(150),
   IN narration VARCHAR(200),
   IN txnType VARCHAR(100),
   IN batchNumber VARCHAR(100),
   IN amountTxn DOUBLE,
   IN newBalance DOUBLE,
   IN userId VARCHAR(80)
   ) BEGIN
   
    CALL postingDate(@postingDate);

   /* SELECT PostingmDate INTO originalDate FROM  DatesForSystem;*/

    SELECT TxnId,postDate INTO @trnId, @previousPostDate FROM balancesdb WHERE accountNumber=accountNumberX AND postDate<=@postingDate ORDER BY TxnId DESC Limit 1;
   
     CALL accountNumberExists(accountNumberX,@theAccountExists);

    IF @theAccountExists>0 THEN

  
  IF amountTxn>0 THEN

  INSERT INTO generalledger VALUES(null,CURDATE(),CURDATE(),@postingDate,batchNumber,txnType,narration,accountName,accountNumberX,amountTxn,'0.0',userId,'NA','NA','NA');
  
  IF @postingDate>@previousPostDate THEN

  INSERT INTO balancesdb VALUES(null,@postingDate,CURDATE(),accountNumberX,accountName,newBalance,userId,'NA','NA','NA');
  
  END IF;

   
    IF @postingDate<=@previousPostDate THEN


UPDATE balancesdb SET Balance=newBalance WHERE TxnId=@trnId;


  END IF;


  END IF;
  
  END IF;

     END//
 DELIMITER ;



 DROP PROCEDURE IF EXISTS accountNumberExists;
 
 	DELIMITER //
	

   CREATE PROCEDURE  accountNumberExists (IN theAccount VARCHAR(30),OUT accountNumbers INTEGER  ) BEGIN
  

    SELECT COUNT(TemplateId) AS accounts  FROM accountstemplate WHERE AccountNumber=theAccount INTO accountNumbers;

/*SELECT accountNumbers;*/

      END//

 DELIMITER ;
 DELETE FROM accountstemplate;
 DELETE FROM balancesdb;
 DELETE FROM generalledger;
 DELETE FROM stock;

  DELETE FROM salesledger;
  DELETE FROM salestracker;
  DELETE FROM specialaccountsetup;

   DELETE FROM txnscategories;
   DELETE FROM txnsprocess;

 DROP PROCEDURE IF EXISTS updateLedgersBalancesCREDIT;
 
 	DELIMITER //

   CREATE PROCEDURE  updateLedgersBalancesCREDIT (
     IN accountNumberx VARCHAR(100),
     IN accountName VARCHAR(150),
   IN narration VARCHAR(200),
   IN txnType VARCHAR(100),
   IN batchNumber VARCHAR(100),
   IN amountTxn DOUBLE,
   IN newBalance DOUBLE,
   IN userId VARCHAR(80)
   ) BEGIN
   
    CALL postingDate(@postingDate);

   /* SELECT PostingmDate INTO originalDate FROM  DatesForSystem;*/

    SELECT TxnId,postDate INTO @trnId, @previousPostDate FROM balancesdb WHERE accountNumber=accountNumberx AND postDate<=@postingDate ORDER BY TxnId DESC Limit 1;
   
     CALL accountNumberExists(accountNumberx,@theAccountExists);

    IF @theAccountExists>0 THEN

  
  IF amountTxn>0 THEN

  INSERT INTO generalledger VALUES(null,CURDATE(),CURDATE(),@postingDate,batchNumber,txnType,narration,accountName,accountNumberx,'0.0',amountTxn,userId,'NA','NA','NA');
  
  IF @postingDate>@previousPostDate THEN

  INSERT INTO balancesdb VALUES(null,@postingDate,CURDATE(),accountNumberx,accountName,newBalance,userId,'NA','NA','NA');
  
  END IF;

   
    IF @postingDate<=@previousPostDate THEN


UPDATE balancesdb SET Balance=newBalance WHERE TxnId=@trnId;


  END IF;


  END IF;
  
  END IF;

     END//
 DELIMITER ;


DROP PROCEDURE IF EXISTS createTxnPosting;
 
 	DELIMITER //

   CREATE PROCEDURE  createTxnPosting (IN txnName VARCHAR(200),IN drAccount VARCHAR(200),IN crAccount VARCHAR(200)) BEGIN
  
    SELECT AccountNumber INTO @drAccountNumber FROM accountstemplate WHERE AccountName=drAccount; 
    
     SELECT AccountNumber INTO @crAccountNumber FROM accountstemplate WHERE AccountName=crAccount; 

 CALL SystemDate(@dateNow);
  
  INSERT INTO TxnsProcess VALUES(null,txnName,@drAccountNumber,drAccount,@crAccountNumber,crAccount,@dateNow,'NA','NA','NA');
  

     END//
 DELIMITER ;



DROP PROCEDURE IF EXISTS doubleEntryManager;
 
 	DELIMITER //

   CREATE PROCEDURE  doubleEntryManager (IN TABLEID VARCHAR(10),IN txnType VARCHAR(80),IN txnCode VARCHAR(80),IN drAccountName VARCHAR(200),IN amount VARCHAR(100),IN Narration VARCHAR(300),IN batchCode VARCHAR(30),IN userId VARCHAR(30)) BEGIN
  

    IF txnType='EXPENSE' THEN


     SELECT AccountNumber INTO @drAccountNumber FROM accountstemplate WHERE AccountName=drAccountName; 

     SELECT  setUpAccountNumber INTO @cashAccount FROM SpecialAccountSetUp WHERE setUpName='CASH';


CALL postTxn (amount,Narration,'DEBIT','EXPENSE',@drAccountNumber,  batchCode,userId);

CALL postTxn (amount,Narration,'CREDIT','ASSET',@cashAccount, batchCode,userId);


    END IF;

IF txnType='GENERAL JOURNAL' THEN

     SELECT AccountNumber INTO @drAccountNumber FROM accountstemplate WHERE AccountName=drAccountName; 

     SELECT  journalAccountName INTO @crAccountName FROM GeneralJournalEntry WHERE PostingIdBatch=TABLEID ORDER BY JournalId DESC Limit 1;

 SELECT AccountNumber INTO @crAccountNumber FROM accountstemplate WHERE AccountName=@crAccountName; 



CALL txnTyp (@drAccountNumber,@theTxnTypeDr);
 CALL txnTyp (@crAccountNumber,@theTxnTypeCr);

CALL postTxn (amount,Narration,'DEBIT',@theTxnTypeDr,@drAccountNumber,  batchCode,userId);

CALL postTxn (amount,Narration,'CREDIT',@theTxnTypeCr,@crAccountNumber, batchCode,userId);


    END IF;

  IF txnType='COMMON' THEN
  
 SELECT DrAccountNumber INTO @drAccountNumber FROM TxnsProcess WHERE TxnName=txnCode; 


 SELECT CrAccountNumber INTO @crAccountNumber FROM TxnsProcess WHERE TxnName=txnCode; 



CALL txnTyp (@drAccountNumber,@theTxnTypeDr);
 CALL txnTyp (@crAccountNumber,@theTxnTypeCr);

 SELECT @theTxnTypeDr,@theTxnTypeCr;

CALL postTxn (amount,Narration,'DEBIT',@theTxnTypeDr,@drAccountNumber,  batchCode,userId);

CALL postTxn (amount,Narration,'CREDIT',@theTxnTypeCr,@crAccountNumber, batchCode,userId);




    END IF;

     /* IF txnType='BATCHED' THEN

    END IF;*/

    END//
 DELIMITER ;


 DROP PROCEDURE IF EXISTS txnTyp;
 
 	DELIMITER //
	

   CREATE PROCEDURE  txnTyp (IN theAccountNumber VARCHAR(30),OUT theTxnType VARCHAR(100)  ) BEGIN
  

    SELECT AccountCategory2  FROM accountstemplate WHERE AccountNumber=theAccountNumber INTO @theAccountCategory;


    IF @theAccountCategory="Assets" THEN

    SET theTxnType='ASSET';

    ELSEIF @theAccountCategory="Expense" THEN

 SET theTxnType='EXPENSE';

       ELSEIF @theAccountCategory="Revenues" THEN
           
        SET theTxnType='REVENUE';
        ELSEIF @theAccountCategory="Liabilities" THEN     
           
            SET theTxnType='LIABILITY';
           
 ELSEIF @theAccountCategory="Equity/Capital" THEN  

SET theTxnType='EQUITY';

END IF;
       END//
 DELIMITER ;


 
 DROP PROCEDURE IF EXISTS salesTxnProcessing;
 
 	DELIMITER //
	

   CREATE PROCEDURE  salesTxnProcessing (IN tableId VARCHAR(30),IN stockIdx VARCHAR(100),IN productName VARCHAR(100),IN NoOfItems VARCHAR(80),IN RetailPrice VARCHAR(100),IN batchDode VARCHAR(100),IN userId VARCHAR(100)) BEGIN

  SELECT tableId,stockIdx,productName,NoOfItems,RetailPrice,batchDode,userId;

   SELECT DrAccountName INTO @drAccountNameStock FROM TxnsProcess WHERE TxnName='stockSales'; 

SELECT @drAccountNameStock ;

    SELECT DrAccountName INTO @drAccountNameRevenue FROM TxnsProcess WHERE TxnName='revenueSales'; 

SELECT @drAccountNameRevenue;
  
  
   SELECT ItemsRemaining,ItemsSold,RetailCostPrice,RetailSellingPrice INTO @numberOfItem,@SoldItems,@costP,@actualSell FROM stock WHERE StockId=stockIdx;
  
  
  SELECT  @numberOfItem,@SoldItems,@costP,@actualSell;

   SET @WholeSale=@costP*NoOfItems;

   SET @ActualWhole=@actualSell*NoOfItems;
   
   SET @MarkUp=RetailPrice-@WholeSale;

   SET @PercentMark=((@MarkUp/@WholeSale)*100);

  SET @Discount=@ActualWhole-RetailPrice;

  SET @remainingStock=@numberOfItem-NoOfItems;

  SET @itemsSold=@SoldItems+NoOfItems;

  SET @PercentDiscount=((@Discount/@ActualWhole)*100);


   CALL  doubleEntryManager (tableId,'COMMON','stockSales',@drAccountNameStock,@WholeSale,productName,batchDode,userId);

  CALL  doubleEntryManager (tableId,'COMMON','revenueSales',@drAccountNameRevenue,@MarkUp,productName,batchDode,userId);

   CALL postingDate(@postingDate);

    CALL SystemDate(@dateNow);
    
 UPDATE stock SET ItemsSold=@itemsSold,ItemsRemaining=@remainingStock WHERE StockId=stockIdx;
SELECT stockIdx;

    INSERT INTO salesTracker VALUES(null,@dateNow,@dateNow,@postingDate,stockIdx,productName,NoOfItems,RetailPrice,userId,'NA','NA','NA');
 
       INSERT INTO SalesLedger VALUES(null,@dateNow,@dateNow,@postingDate,stockIdx,productName,NoOfItems,RetailPrice,@MarkUp,@PercentMark,userId,'NA','NA','NA');

      

       IF @Discount<0 THEN

        INSERT INTO dicountedSales VALUES(null,@dateNow,@dateNow,@postingDate,stockIdx,productName,
        @ActualWhole,RetailPrice,@Discount,@PercentDiscount,userId,'NA','NA','NA');

       END IF;

       
       IF @Discount>0 THEN

         INSERT INTO hickedSales VALUES(null,@dateNow,@dateNow,@postingDate,stockIdx,productName,

         @ActualWhole,RetailPrice,@Discount,@PercentDiscount,userId,'NA','NA','NA');


       END IF;
SET @ActualWhole=0.0;
SET @Discount=0.0;
SET @PercentDiscount=0.0;
SET @MarkUp=0.0;
SET @PercentMark=0.0;
SET @itemsSold=0.0;
SET @costP=0.0;
SET @actualSell=0.0;





    END//
 DELIMITER ;
 
 
 
 
 
 DROP PROCEDURE IF EXISTS increaseStock;

DELIMITER //

CREATE PROCEDURE increaseStock(IN StockIds INTEGER, IN numberOfStock DOUBLE,IN unitCostPrice DOUBLE,
IN unitSelling DOUBLE,IN wholesaleSellingPriceS DOUBLE,IN wholesellcostprice DOUBLE) READS SQL DATA BEGIN

SELECT NumberOfItems,ItemsRemaining INTO @nstock,@nItemRe  FROM Stock WHERE StockId=StockIds;

SET @newStock=numberOfStock+@nstock;

SET @newnItemRe=numberOfStock+@nItemRe;


UPDATE stock SET NumberOfItems=@newStock,ItemsRemaining=@newnItemRe,RetailCostPrice=unitCostPrice,WholeSaleCostPrice=wholesellcostprice,
RetailSellingPrice=unitSelling,WholeSaleSellingPrice=wholesaleSellingPriceS WHERE stockid=StockIds;

END //

DELIMITER ;



DROP PROCEDURE IF EXISTS computeInterestPercent;

DELIMITER //

CREATE PROCEDURE computeInterestPercent() READS SQL DATA BEGIN

  DECLARE l_done INTEGER DEFAULT 0;
  
  DECLARE ledgerIds INTEGER DEFAULT 1;

 DECLARE selectTrnIds CURSOR FOR SELECT SalesLedgerId  FROM salesledger;
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;

 OPEN selectTrnIds;


LedgerIds_loop: LOOP 

 FETCH selectTrnIds into ledgerIds;

 IF l_done=1 THEN

LEAVE LedgerIds_loop;

 END IF;
 

SELECT MarkUpAmount,NumberOfItemsSold,StockId INTO @markUp,@NoSold,@id from salesledger WHERE SalesLedgerId=ledgerIds;

SELECT RetailCostPrice INTO @costPrice from stock where stockid=@id;

 SET @percentMarkUp=((@markUp/(@NoSold*@costPrice))*100);

UPDATE salesledger SET MarkUpPecentage=@percentMarkUp WHERE SalesLedgerId=ledgerIds;

SET l_done=0;

 END LOOP LedgerIds_loop;



 CLOSE selectTrnIds;




END //

DELIMITER ;

CALL computeInterestPercent();

    
DROP PROCEDURE IF EXISTS increaseStock;

DELIMITER //

CREATE PROCEDURE increaseStock(IN StockIds INTEGER, IN numberOfStock DOUBLE,IN unitCostPrice DOUBLE,
IN unitSelling DOUBLE,IN wholesaleSellingPriceS DOUBLE,IN wholesellcostprice DOUBLE) READS SQL DATA BEGIN

SELECT NumberOfItems,ItemsRemaining INTO @nstock,@nItemRe  FROM Stock WHERE StockId=StockId;

SET @newStock=numberOfStock+@nstock;

SET @newnItemRe=ItemsRemaining+@nItemRe;


UPDATE stock SET NumberOfItems=@newStock,ItemsRemaining=@newnItemRe,RetailCostPrice=unitCostPrice,WholeSaleCostPrice=wholesellcostprice,
RetailSellingPrice=unitSelling,WholeSaleSellingPrice=wholesaleSellingPriceS WHERE stockid=StockIds;

END //

DELIMITER ;

CALL computeInterestPercent();




DROP PROCEDURE IF EXISTS computeBalancesStatement;

DELIMITER //

CREATE PROCEDURE computeBalancesStatement(IN accountNamexx VARCHAR(100), IN startDatexx DATE,IN endDatexx DATE) READS SQL DATA BEGIN

CALL previousAccountBalance(startDatexx, @theRunBalTwo);
CALL accountNumberByAccountName(accountNamexx,@accountN);

CALL generalLedgerStatement(@accountN, @theRunBalTwo, startDatexx,endDatexx);

END //

DELIMITER ;





DROP PROCEDURE IF EXISTS accountNumberByAccountName;

DELIMITER //

CREATE PROCEDURE accountNumberByAccountName(IN accountNamexxx VARCHAR(100),OUT accountNumberXX VARCHAR(100)) READS SQL DATA BEGIN


 SELECT AccountNumber INTO accountNumberXX FROM accountstemplate WHERE AccountName=accountNamexxx;
 

END //

DELIMITER ;


    
	DROP PROCEDURE IF EXISTS previousAccountBalance;

DELIMITER //

CREATE PROCEDURE previousAccountBalance(IN accountNumber,IN fistPostDate DATE, OUT theRunBal DOUBLE) READS SQL DATA BEGIN

 SELECT Balance INTO theRunBal FROM balancesdb WHERE postDate<=fistPostDate ORDER BY TxnId DESC Limit 1;
 
 IF theRunBal  IS NULL THEN
 
 SET theRunBal=0.0;
 
 END IF;

END //

DELIMITER ;
	
DROP PROCEDURE IF EXISTS generalLedgerStatement;

DELIMITER //



CREATE PROCEDURE generalLedgerStatement(IN accountNumbers VARCHAR(100), IN prevBal VARCHAR(100), IN startDate DATE,IN endDate DATE) READS SQL DATA BEGIN

 DECLARE txnIdS VARCHAR(30);

 DECLARE l_done INTEGER DEFAULT 0;DECLARE Ids INTEGER DEFAULT 0;
 
 DECLARE batch VARCHAR(30); DECLARE pdate DATE; DECLARE narr VARCHAR(200); DECLARE debit DOUBLE DEFAULT 0.0; DECLARE credit DOUBLE DEFAULT 0.0; DECLARE runningBalance DOUBLE DEFAULT 8000000.0; DECLARE userId VARCHAR(30);

DECLARE forSelectingTxnIds CURSOR FOR SELECT TxnId  FROM generalledger WHERE ((PostDate>=startDate AND PostDate<=endDate) AND AccountNumber=accountNumbers);
 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_done=1;
 
/*  SELECT accountName,startDate,endDate; */
 


/*  
IF @runningBalance IS NULL THEN */
/* SET @runningBalance=0.0; */
/* END IF; */

/* SELECT @runningBalance; */

DROP TABLE IF EXISTS temp_ledgerstatement;

CREATE TEMPORARY  TABLE temp_ledgerstatement(id INTEGER,temp_BatchNumber VARCHAR(30),temp_PostDate DATE,temp_Narration VARCHAR(200),temp_DEDITAmount DOUBLE,temp_CREDITAmount DOUBLE,temp_runBalance DOUBLE);






 OPEN forSelectingTxnIds;



accounts_loop: LOOP 



 FETCH forSelectingTxnIds into txnIdS;
 
 
 IF l_done=1 THEN

LEAVE accounts_loop;

 END IF;
 
 
 
 SELECT BatchNumber,PostDate,Narration,DEDITAmount,CREDITAmount  INTO batch,pdate,narr,debit,credit FROM generalledger WHERE TxnId=txnIdS;
 
 SET Ids=Ids+1;
   
   IF accountNumbers LIKE '1%' THEN
   
 SET prevBal=prevBal+debit-credit;
 
ELSEIF accountNumbers LIKE '2%' THEN
 SET prevBal=prevBal+debit-credit;
ELSEIF accountNumbers LIKE '3%' THEN
 SET prevBal=prevBal+credit-debit;
ELSEIF accountNumbers LIKE '4%' THEN
 SET prevBal=prevBal+credit-debit;
ELSEIF accountNumbers LIKE '5%' THEN
 SET prevBal=prevBal+credit-debit;
END IF;

 INSERT INTO temp_ledgerstatement VALUES(Ids,batch,pdate,narr,debit,credit,prevBal);


 



SET l_done=0;

 END LOOP accounts_loop;

 CLOSE forSelectingTxnIds;
 


SELECT Id,temp_BatchNumber,temp_PostDate,temp_Narration,temp_DEDITAmount ,temp_CREDITAmount ,temp_runBalance  FROM temp_ledgerstatement;


END //

DELIMITER ;

CALL computeBalancesStatement('Cash At Hand','2019-02-01','2019-05-18');

