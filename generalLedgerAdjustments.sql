

 DROP PROCEDURE IF EXISTS adjustTrnIdS;
 
 	DELIMITER //

   CREATE PROCEDURE  adjustTrnIdS (IN bsancaAccountNumber VARCHAR(30),IN dateInQuestion DATE,OUT trId INTEGER) BEGIN
   
     SET @sql_text9 = concat(CAST(" SELECT  trn_id INTO @trIdV FROM  " AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST("  WHERE trn_date='"AS CHAR CHARACTER SET utf8),dateInQuestion,CAST("'  ORDER BY trn_id DESC LIMIT 1" AS CHAR CHARACTER SET utf8));
SELECT @sql_text9;
PREPARE stmt9 FROM @sql_text9;
  EXECUTE stmt9;
DROP PREPARE stmt9;
   
       SET @sql_text = concat(CAST(' SELECT  trn_id INTO @LasttrId FROM  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  ORDER BY trn_id DESC LIMIT 1' AS CHAR CHARACTER SET utf8));
PREPARE stmt FROM @sql_text;
  EXECUTE stmt;
DROP PREPARE stmt;

SELECT @trIdV ,@LasttrId ;

SET trId=@trIdV;
	  
	  TxIdsXX:LOOP
	  
	  
	/*   SELECT @LasttrId; */
	  
	SET   @oldIdC=@LasttrId+1;
	  
	 SET    @newIdC=@LasttrId-1;
	  
	     SET @sql_text = concat(CAST('UPDATE  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  SET trn_id= ' AS CHAR CHARACTER SET utf8),@oldIdC,CAST('  WHERE trn_id=  ' AS CHAR CHARACTER SET utf8),@LasttrId);
       PREPARE stmt FROM @sql_text;
       EXECUTE stmt;
     DROP PREPARE stmt;
	    SET @sql_text = concat(CAST('UPDATE  ' AS CHAR CHARACTER SET utf8),bsancaAccountNumber,CAST('  SET trn_id=  ' AS CHAR CHARACTER SET utf8),@LasttrId,CAST(' WHERE trn_id=  ' AS CHAR CHARACTER SET utf8),@newIdC);
	 
       PREPARE stmt FROM @sql_text;
       EXECUTE stmt;
     DROP PREPARE stmt;
	  
	
		
		SET @LasttrId=@newIdC;
		
		/*   SELECT @LasttrId; */
	  
	  IF @newIdC=trId OR @newIdC=1000 THEN
	  
	  LEAVE TxIdsXX;
	  
	  END IF;
	  
	  
	  END LOOP TxIdsXX;
   


     END//
 DELIMITER ;
 
 
 