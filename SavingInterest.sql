CREATE TABLE `savingsinterestpaymentdaily` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `savingsinterestpaymentmonthly` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `savingsinterestpaymentannually` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `savingsandsharesinterestpaymentsummury` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;














DROP TABLE IF EXISTS `savingsandsharesinterestpaymentsummury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingsandsharesinterestpaymentsummury`
--

LOCK TABLES `savingsandsharesinterestpaymentsummury` WRITE;
/*!40000 ALTER TABLE `savingsandsharesinterestpaymentsummury` DISABLE KEYS */;
/*!40000 ALTER TABLE `savingsandsharesinterestpaymentsummury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savingsinterestpaymentannually`
--

DROP TABLE IF EXISTS `savingsinterestpaymentannually`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingsinterestpaymentannually`
--

LOCK TABLES `savingsinterestpaymentannually` WRITE;
/*!40000 ALTER TABLE `savingsinterestpaymentannually` DISABLE KEYS */;
/*!40000 ALTER TABLE `savingsinterestpaymentannually` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savingsinterestpaymentdaily`
--

DROP TABLE IF EXISTS `savingsinterestpaymentdaily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingsinterestpaymentdaily`
--

LOCK TABLES `savingsinterestpaymentdaily` WRITE;
/*!40000 ALTER TABLE `savingsinterestpaymentdaily` DISABLE KEYS */;
/*!40000 ALTER TABLE `savingsinterestpaymentdaily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savingsinterestpaymentmonthly`
--

DROP TABLE IF EXISTS `savingsinterestpaymentmonthly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savingsinterestpaymentmonthly`
--

LOCK TABLES `savingsinterestpaymentmonthly` WRITE;
/*!40000 ALTER TABLE `savingsinterestpaymentmonthly` DISABLE KEYS */;
/*!40000 ALTER TABLE `savingsinterestpaymentmonthly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savingssharescomputationparameters`
--

DROP TABLE IF EXISTS `savingssharescomputationparameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `savingssharescomputationparameters` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `SavingsStartDate` date NOT NULL DEFAULT '1970-01-01',
  `ShareStartDate` date NOT NULL DEFAULT '1970-01-01',
  `SharesRateUsed` int(11) DEFAULT NULL,
  `SavingsRateUsed` int(11) DEFAULT NULL,
  `SharesIncludUsed` int(11) DEFAULT NULL,
  `ShareExclude` int(11) DEFAULT NULL,
  `SavingsIncludUsed` int(11) DEFAULT NULL,
  `SavingsExclude` int(11) DEFAULT NULL,
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
