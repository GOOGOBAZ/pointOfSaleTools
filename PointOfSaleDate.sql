
CREATE TABLE `CompanyDetails` (
  `CompanyId` int(11) NOT NULL AUTO_INCREMENT,
   `CompanyName` varchar(45) DEFAULT 'Stat Solutions',
      `DirectorName` varchar(45) DEFAULT 'Augustine',
 `AddressId` int(11) NOT NULL AUTO_INCREMENT,
  `LedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  PRIMARY KEY (`CompanyId`),
  UNIQUE KEY `CompanyId_UNIQUE` (`CompanyId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `BranchDetails` (
  `BranchId` int(11) NOT NULL AUTO_INCREMENT,
   `BranchName` varchar(45) DEFAULT 'Stat Solutions',
      `BranchType` varchar(45) DEFAULT 'Augustine',
 `AddressId` int(11) NOT NULL AUTO_INCREMENT,
  `LedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL AUTO_INCREMENT,
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  PRIMARY KEY (`CompanyId`),
  UNIQUE KEY `CompanyId_UNIQUE` (`CompanyId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `BusinessUnit` (
  `BusinessUnit` int(11) NOT NULL AUTO_INCREMENT,
   `BusinessUnitName` varchar(45) DEFAULT 'Stat Solutions',
      `BusinessUnitType` varchar(45) DEFAULT 'Augustine',
 `AddressId` int(11) NOT NULL AUTO_INCREMENT,
  `LedgerId` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL AUTO_INCREMENT,
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  PRIMARY KEY (`CompanyId`),
  UNIQUE KEY `CompanyId_UNIQUE` (`CompanyId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `AccountsTemplate`;
CREATE TABLE `AccountsTemplate` (
  `TemplateId` int(11) NOT NULL,
  `TemplateDate` date NOT NULL DEFAULT '1970-01-01',
  `AccountName`  varchar(100) DEFAULT '0',
  `AccountType`  varchar(100) DEFAULT '0',
	`EntityId` int(11) NOT NULL,
	`BusinessUnitId` int(11) NOT NULL,
	`BranchId` int(11) NOT NULL,
	UserId  int(11) NOT NULL,  
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* 6.GeneralLedger */
/*DROP TABLE IF EXISTS `GeneralLedger`;
CREATE TABLE `GeneralLedger` (
  `TxnId` int(11) NOT NULL,
  `AccountsApAmId`  int(11) NOT NULL,
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `Narration`  varchar(100) DEFAULT '0',
  `AmountIn`  varchar(100) DEFAULT '0',
  `AmountInBudger`  varchar(100) DEFAULT '0',
  `AmountOut`  varchar(100) DEFAULT '0',
  `AmountOutBudget`  varchar(100) DEFAULT '0',
	`EntityId` int(11) NOT NULL,
	`BusinessUnitId` int(11) NOT NULL,
	`BranchId` int(11) NOT NULL,
	UserId  int(11) NOT NULL,
	
  
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*/


CREATE TABLE `accountsidgenerators` (
  `TemplateIdAssets` int(11) DEFAULT '10000',
  `TemplateIdExpenses` int(11) DEFAULT '20000',
  `TemplateIdIncomes` int(11) DEFAULT '30000',
  `TemplateIdEquity` int(11) DEFAULT '40000',
  `TemplateIdLiabilities` int(11) DEFAULT '50000',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `accountstemplate` (
  `TemplateId` int(11) NOT NULL AUTO_INCREMENT,
  `TemplateDate` date NOT NULL DEFAULT '1970-01-01',
  `AccountName` varchar(200) DEFAULT '0',
  `AccountNumber` varchar(200) DEFAULT '0',
  `AccountCategory1` varchar(200) DEFAULT '0',
  `AccountCategory2` varchar(200) DEFAULT '0',
  `AccountCategory3` varchar(200) DEFAULT '0',
  `AccountCategory4` varchar(200) DEFAULT '0',
  `AccountStatus` varchar(100) DEFAULT 'Active',
  `UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TemplateId`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `balancesdb`;
CREATE TABLE `balancesdb` (
  `TxnId` int(11) NOT NULL AUTO_INCREMENT,
  `postDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `accountNumber` varchar(100) DEFAULT '0',
   `accountName` varchar(200) DEFAULT '0',
  `Balance` varchar(100) DEFAULT '0',
  `UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TxnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `generalledger`;
CREATE TABLE `generalledger` (
  `TxnId` int(11) NOT NULL,
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `SysDate` date NOT NULL DEFAULT '1970-01-01',
  `PostDate` date NOT NULL DEFAULT '1970-01-01',
  `BatchNumber` varchar(100) DEFAULT '0',
  `TxnType` varchar(100) DEFAULT '0',
  `Narration` varchar(300) DEFAULT '0',
  `AccountName` varchar(200) DEFAULT '0',
  `AccountNumber` varchar(100) DEFAULT '0',
  `DEDITAmount` varchar(100) DEFAULT '0',
  `CREDITAmount` varchar(100) DEFAULT '0',
  `UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `generalledger` ADD PRIMARY KEY (`TxnId`);

ALTER TABLE `generalledger` MODIFY `TxnId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;


CREATE TABLE `datesforsystem` (
  `DateID` int(11) NOT NULL AUTO_INCREMENT,
  `SystemDate` date NOT NULL DEFAULT '1970-01-01',
  `PostingmDate` date NOT NULL DEFAULT '1970-01-01',
  `ValueDate` date NOT NULL DEFAULT '1970-01-01',
  `OtherDate` date NOT NULL DEFAULT '1970-01-01',
  PRIMARY KEY (`DateID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `systemids` (
  `SyIdA1` int(11) DEFAULT '1',
  `SyIdA2` int(11) DEFAULT '20000',
  `SyIdA3` int(11) DEFAULT '30000',
  `SyIdA4` int(11) DEFAULT '40000',
  `SyIdA5` int(11) DEFAULT '50000',
  `SyIdA6` int(11) DEFAULT '60000',
  `SyIdA7` int(11) DEFAULT '70000',
  `SyIdA8` int(11) DEFAULT '80000',
  `SyIdA9` int(11) DEFAULT '90000',
  `SyIdA10` int(11) DEFAULT '100000',
  `SyIdA11` int(11) DEFAULT '110000',
  `SyIdA12` int(11) DEFAULT '120000',
  `SyIdA13` int(11) DEFAULT '130000',
  `SyIdA14` int(11) DEFAULT '140000',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `salesTracker`;

CREATE TABLE `salesTracker` (
  `TrackerId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `SysDate` date NOT NULL DEFAULT '1970-01-01',
  `PostDate` date NOT NULL DEFAULT '1970-01-01',
  `StockId` varchar(100) DEFAULT '0',
`productName` varchar(300) DEFAULT '0',
  `NumberOfItems` varchar(100) DEFAULT '0',
  `SellingPrice` varchar(100) DEFAULT '0',
   `UserId` varchar(45) DEFAULT 'NCO',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrackerId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=latin1;



CREATE TABLE `stock` (
  `StockId` int(11) NOT NULL AUTO_INCREMENT,
  `ProductCategory` varchar(100) DEFAULT '0',
  `ProductSubCategory` varchar(100) DEFAULT '0',
  `ProductType` varchar(100) DEFAULT '0',
  `ProductBrand` varchar(100) DEFAULT '0',
  `ProductSerialNumber` varchar(100) DEFAULT '0',
  `BarCode` varchar(100) DEFAULT '0',
  `ProductName` varchar(100) DEFAULT '0',
  `NumberOfItems` varchar(100) DEFAULT '0',
  `ItemsSold` varchar(100) DEFAULT '0',
  `ItemsRemaining` varchar(100) DEFAULT '0',
  `RetailCostPrice` varchar(100) DEFAULT '0',
  `WholeSaleCostPrice` varchar(100) DEFAULT '0',
  `RetailSellingPrice` varchar(100) DEFAULT '0',
  `WholeSaleSellingPrice` varchar(100) DEFAULT '0',
  `ExpectedMarkUp` varchar(100) DEFAULT '0',
  `ManufactureDate` date NOT NULL DEFAULT '1970-01-01',
  `PurchaseDate` date NOT NULL DEFAULT '1970-01-01',
  `ExpirelyDate` date NOT NULL DEFAULT '1970-01-01',
  `UserId` int(11) NOT NULL,
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`StockId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `SalesLedger`;

CREATE TABLE `SalesLedger` (
  `SalesLedgerId` int(11) NOT NULL AUTO_INCREMENT,
 `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `SysDate` date NOT NULL DEFAULT '1970-01-01',
  `PostDate` date NOT NULL DEFAULT '1970-01-01',
  `StockId` varchar(100) DEFAULT '0',
  `productName` varchar(300) DEFAULT '0',
  `NumberOfItemsSold` varchar(100) DEFAULT '0',
  `WholeSaleSellingPrice` varchar(100) DEFAULT '0',
  `MarkUpAmount` varchar(100) DEFAULT '0',
  `MarkUpPecentage` date NOT NULL DEFAULT '1970-01-01',
  `UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`SalesLedgerId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `TxnsProcess`;

CREATE TABLE `TxnsProcess` (
  `TxnsProcessId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnName` varchar(100) DEFAULT '0',
  `DrAccountNumber` varchar(100) DEFAULT '0',
  `DrAccountName` varchar(100) DEFAULT '0',
  `CrAccountNumber` varchar(100) DEFAULT '0',
  `CrAccountName`  varchar(100) DEFAULT '0',
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TxnsProcessId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;


CREATE TABLE `TxnsCategories` (
  `TxnsCatId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnCatName` varchar(100) DEFAULT '0',
  `TxnCatDescription` varchar(300) DEFAULT '0',
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TxnsCatId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `SpecialAccountSetUp`;
CREATE TABLE `SpecialAccountSetUp` (
  `setUpId` int(11) NOT NULL AUTO_INCREMENT,
  `setUpName` varchar(100) DEFAULT '0',
  `setUpAccountName` varchar(300) DEFAULT '0',
  `setUpAccountNumber` varchar(300) DEFAULT '0',
  `CreationDate` date NOT NULL DEFAULT '1970-01-01',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`setUpId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `GeneralJournalEntry`;
CREATE TABLE `GeneralJournalEntry` (
  `JournalId` int(11) NOT NULL AUTO_INCREMENT,
  `PostingIdBatch` varchar(100) DEFAULT '0',
  `journalAccountName` varchar(300) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`JournalId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `dicountedSales`;
CREATE TABLE `dicountedSales` (
  `discountedSalesId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `SysDate` date NOT NULL DEFAULT '1970-01-01',
  `PostDate` date NOT NULL DEFAULT '1970-01-01',
  `stockId` varchar(100) DEFAULT '0',
  `productName` varchar(300) DEFAULT '0',
 `ActualSellingPrice` varchar(100) DEFAULT '0',
  `DiscountedPrice` varchar(300) DEFAULT '0',
   `AmountDiscouted` varchar(100) DEFAULT '0',
  `percentageDiscounted` varchar(300) DEFAULT '0',
  `UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`discountedSalesId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `hickedSales`;
CREATE TABLE `hickedSales` (
  `hickedSalesId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnDate` date NOT NULL DEFAULT '1970-01-01',
  `SysDate` date NOT NULL DEFAULT '1970-01-01',
  `PostDate` date NOT NULL DEFAULT '1970-01-01',
  `stockId` varchar(100) DEFAULT '0',
  `productName` varchar(300) DEFAULT '0',

 `ActualSellingPrice` varchar(100) DEFAULT '0',
  `hickedSalesPrice` varchar(300) DEFAULT '0',
   `Amounthicked` varchar(100) DEFAULT '0',
  `percentagehicked` varchar(300) DEFAULT '0',



`UserId` int(11) NOT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`hickedSalesId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
 

 SELECT SalesLedger.SalesLedgerId,SalesLedger.SysDate,SalesLedger.PostDate,
           SalesLedger.productName,SalesLedger.NumberOfItemsSold, stock.ItemsRemaining , stock.RetailCostPrice,stock.RetailSellingPrice,
           SalesLedger.WholeSaleSellingPrice,SalesLedger.MarkUpAmount,SalesLedger.MarkUpPecentage FROM SalesLedger INNER JOIN stock ON SalesLedger.StockId=SalesLedger.StockId;
           
            Select generalledger.TxnId,generalledger.PostDate,generalledger.BatchNumber,generalledger.Narration,generalledger.DEDITAmount,generalledger.CREDITAmount, balancesdb.Balance FROM  generalledger where generalledger.AccountName='Cash At Hand' INNER JOIN balancesdb ON  generalledger.accountNumber=balancesdb.accountNumber;