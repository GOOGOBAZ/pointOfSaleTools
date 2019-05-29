
CREATE TABLE `AdminCostsComputationParameters` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `SavingsStartDate` DATE NOT NULL DEFAULT '1970-01-01',
   `ShareStartDate` DATE NOT NULL DEFAULT '1970-01-01',
  `Year` INTEGER,
  `YearChanged` INTEGER,
  `AdminCostFees` DOUBLE,
  `ShareExclude` INTEGER,
   `SavingsIncludUsed` INTEGER,
  `SavingsExclude` INTEGER,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `AdminCostIndividualTrackerAll` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `AdminCostIndividualTrackerStatic` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
   `TxnMonth` varchar(45) DEFAULT '0',
   `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` Double,
  `ComputationCycle` INTEGER,
  `RateUsed` INTEGER,
  `InterestComputed` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;