







CREATE TABLE `SavingsInterestPaymentDaily` (
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
     `InterestComputedRuningBalance` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;




CREATE TABLE `SavingsInterestPaymentMonthly` (
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
   `InterestComputedRuningBalance` Double,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `SavingsInterestPaymentAnnually` (
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



CREATE TABLE `SavingsAndSharesInterestPaymentSummury` (
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


CREATE TABLE `sharesinterestpaymentannually` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `sharesinterestpaymentdaily` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `InterestComputedRuningBalance` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `sharesinterestpaymentmonthly` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `TxnMonth` varchar(45) DEFAULT '0',
  `TxnYear` varchar(45) DEFAULT '0',
  `AccountNmae` varchar(45) DEFAULT '0',
  `AccountNumber` varchar(45) DEFAULT '055020000010',
  `LedgerBalance` double DEFAULT NULL,
  `ComputationCycle` int(11) DEFAULT NULL,
  `RateUsed` int(11) DEFAULT NULL,
  `InterestComputed` double DEFAULT NULL,
  `InterestComputedRuningBalance` double DEFAULT NULL,
  `PaymentStatus` varchar(45) DEFAULT '0',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS interestComputed;

CREATE TABLE `interestComputed` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `loanId` varchar(45) DEFAULT '0',
  `DueDate` date NOT NULL DEFAULT '1970-01-01',
  `PrincimpalInvolved`  double DEFAULT NULL,
  `InterestInvolved`  double DEFAULT NULL,
  `loanStatusI` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;