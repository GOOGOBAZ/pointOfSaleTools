-- MySQL dump 10.13  Distrib 5.5.48, for Win64 (x86)
--
-- Host: localhost    Database: pmms
-- ------------------------------------------------------
-- Server version	5.5.48

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_created_store`
--

DROP TABLE IF EXISTS `account_created_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_created_store` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` varchar(100) DEFAULT NULL,
  `account_master` varchar(100) NOT NULL,
  `account_purpose` varchar(100) DEFAULT NULL,
  `account_name` varchar(100) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `account_l1` varchar(100) DEFAULT NULL,
  `account_l2` varchar(100) DEFAULT NULL,
  `account_l3` varchar(100) DEFAULT NULL,
  `account_l4` varchar(100) DEFAULT NULL,
  `account_l5` varchar(100) DEFAULT NULL,
  `running_balance` varchar(100) DEFAULT NULL,
  `account_status` varchar(30) DEFAULT NULL,
  `ProductCode` varchar(50) NOT NULL DEFAULT '10',
  `ProductName` varchar(200) NOT NULL DEFAULT 'Toto Account',
  `creation_user_id` varchar(30) DEFAULT NULL,
  `creation_date` varchar(100) DEFAULT NULL,
  `creation_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=975 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_created_store`
--

LOCK TABLES `account_created_store` WRITE;
/*!40000 ALTER TABLE `account_created_store` DISABLE KEYS */;
INSERT INTO `account_created_store` VALUES (860,'2018-12-13','5020001','For holding Customer/Member account balance','Nanyonga Sandra','05502000110','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-12','B'),(861,'2018-12-13','5020002','For holding Customer/Member account balance','Namatovu Josephine','05502000210','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-12','B'),(862,'2018-12-13','5020003','For holding Customer/Member account balance','Kalibala Francis','05502000310','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-12','B'),(863,'2018-12-13','5020004','For holding Customer/Member account balance','Kalibbala, Matovu  Francis/William','05502000410','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(864,'2018-12-13','5020005','For holding Customer/Member account balance','Kutosi Francis Mabonga','05502000510','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(865,'2018-12-12','5020006','For holding Customer/Member account balance','Ssebana Kizito','05502000610','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(866,'2018-12-13','5020007','For holding Customer/Member account balance','Jaggwe Ronald','05502000710','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(867,'2018-12-12','5020008','For holding Customer/Member account balance','Nsubuga Ronald','05502000810','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0.0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(868,'2018-12-13','5020009','For holding Customer/Member account balance','Nsubuga Musoke James','05502000910','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(869,'2018-12-12','5020010','For holding Customer/Member account balance','Kalibbala Vicent','05502001010','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0.0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(870,'2018-12-12','5020011','For holding Customer/Member account balance','Mulindwa Sam','05502001110','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0.0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(871,'2018-12-12','5020012','For holding Customer/Member account balance','Magembe Nansubuga  Beatrice','05502001210','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(872,'2018-12-12','5020013','For holding Customer/Member account balance','Bazira Dan','05502001310','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0.0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(873,'2018-12-12','5020014','For holding Customer/Member account balance','Wakabi Walter Wales','05502001410','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(874,'2018-12-12','5020015','For holding Customer/Member account balance','Kalanda Umar','05502001510','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(875,'2018-12-12','5020016','For holding Customer/Member account balance','Rugaba Maxime','05502001610','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(876,'2018-12-12','5020017','For holding Customer/Member account balance','Kalisa John','05502001710','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0.0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(877,'2018-12-12','5020018','For holding Customer/Member account balance','Kateregga John','05502001810','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(878,'2018-12-12','5020019','For holding Customer/Member account balance','Ssewakilyanga Brian','05502001910','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(879,'2018-12-12','5020020','For holding Customer/Member account balance','Kalumba Joyce','05502002010','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(880,'2018-12-12','5020021','For holding Customer/Member account balance','Murungi Lewis Lennox','05502002110','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(881,'2018-12-12','5020022','For holding Customer/Member account balance','Senyonjo Fred','05502002210','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(882,'2018-12-12','5020023','For holding Customer/Member account balance','Tenywa Joseph','05502002310','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(883,'2018-12-12','5020024','For holding Customer/Member account balance','Lumala Mark William','05502002410','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(884,'2018-12-13','5020025','For holding Customer/Member account balance','Kyasa Francis Xavier','05502002510','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(885,'2018-12-12','5020026','For holding Customer/Member account balance','Tegule Fred','05502002610','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(886,'2018-12-13','5020027','For holding Customer/Member account balance','Kazibwe Eliam','05502002710','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(887,'2018-12-13','5020028','For holding Customer/Member account balance','Ssejjemba Emmanuel','05502002810','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','NB'),(888,'2018-12-12','5020029','For holding Customer/Member account balance','Ssali Kafeero  Michael','05502002910','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(889,'2018-12-12','5020030','For holding Customer/Member account balance','Sserujongi Derrick','05502003010','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(890,'2018-12-12','5020031','For holding Customer/Member account balance','Katula James','05502003110','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(891,'2018-12-12','5020032','For holding Customer/Member account balance','Mutumba Jimmy','05502003210','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(892,'2018-12-12','5020033','For holding Customer/Member account balance','Mwere Paul','05502003310','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(893,'2018-12-13','5020034','For holding Customer/Member account balance','Businge Rusoke  Victoria','05502003410','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(894,'2018-12-12','2200001','Advertisement Expense1','Advertisement Expense1','02220000110','Debits','Expenses','Operating Expenses','Operating Expense','Advertisement Expense','0','Active','10','Advertisement Expense','10002','2018-12-12','NB'),(895,'2018-12-12','5020035','For holding Customer/Member account balance','Mwanje Fred','05502003510','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(896,'2018-12-12','2210001','Audit Expenses1','Audit Expenses1','02221000110','Debits','Expenses','Operating Expenses','Operating Expense','Audit Expenses','0','Active','10','Audit Expenses','10002','2018-12-12','NB'),(897,'2018-12-12','2220001','Bad Debts Written Off1','Bad Debts Written Off1','02222000110','Debits','Expenses','Operating Expenses','Operating Expense','Bad Debts Written Off','0','Active','10','Bad Debts Written Off','10002','2018-12-12','NB'),(898,'2018-12-12','2230001','Commission1','Commission1','02223000110','Debits','Expenses','Operating Expenses','Operating Expense','Commission','0','Active','10','Commission','10002','2018-12-12','NB'),(899,'2018-12-12','5020036','For holding Customer/Member account balance','Luggya Habib','05502003610','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(900,'2018-12-12','2240001','Computer Soft And Hardware Expenses1','Computer Soft And Hardware Expenses1','02224000110','Debits','Expenses','Operating Expenses','Operating Expense','Computer Soft And Hardware Expenses','0','Active','10','Computer Soft And Hardware Expenses','10002','2018-12-12','NB'),(901,'2018-12-12','2250001','Maintainance of Office Equipment1','Maintainance of Office Equipment1','02225000110','Debits','Expenses','Operating Expenses','Operating Expense','Maintainance of Office Equipment','0','Active','10','Maintainance of Office Equipment','10002','2018-12-12','NB'),(902,'2018-12-12','2260001','News Papers And Periodicals1','News Papers And Periodicals1','02226000110','Debits','Expenses','Operating Expenses','Operating Expense','News Papers And Periodicals','0','Active','10','News Papers And Periodicals','10002','2018-12-12','NB'),(903,'2018-12-12','2270001','Donations1','Donations1','02227000110','Debits','Expenses','Operating Expenses','Operating Expense','Donations','0','Active','10','Donations','10002','2018-12-12','NB'),(904,'2018-12-12','2280001','Entertainment And Refreshments1','Entertainment And Refreshments1','02228000110','Debits','Expenses','Operating Expenses','Operating Expense','Entertainment And Refreshments','0','Active','10','Entertainment And Refreshments','10002','2018-12-12','NB'),(905,'2018-12-12','2290001','Freight And Transport1','Freight And Transport1','02229000110','Debits','Expenses','Operating Expenses','Operating Expense','Freight And Transport','0','Active','10','Freight And Transport','10002','2018-12-12','NB'),(906,'2018-12-12','2300001','Air Time Allowance1','Air Time Allowance1','02230000110','Debits','Expenses','Operating Expenses','Operating Expense','Air Time Allowance','0','Active','10','Air Time Allowance','10002','2018-12-12','NB'),(907,'2018-12-12','2310001','Hotel, Boarding And Lodging Expenses1','Hotel, Boarding And Lodging Expenses1','02231000110','Debits','Expenses','Operating Expenses','Operating Expense','Hotel, Boarding And Lodging Expenses','0','Active','10','Hotel, Boarding And Lodging Expenses','10002','2018-12-12','NB'),(908,'2018-12-12','2320001','Legal Expenses1','Legal Expenses1','02232000110','Debits','Expenses','Operating Expenses','Operating Expense','Legal Expenses','0','Active','10','Legal Expenses','10002','2018-12-12','NB'),(909,'2018-12-12','2330001','Fuel1','Fuel1','02233000110','Debits','Expenses','Operating Expenses','Operating Expense','Fuel','0','Active','10','Fuel','10002','2018-12-12','NB'),(910,'2018-12-12','2340001','Utilities Expenses1','Utilities Expenses1','02234000110','Debits','Expenses','Operating Expenses','Operating Expense','Utilities Expenses','0','Active','10','Utilities Expenses','10002','2018-12-12','NB'),(911,'2018-12-12','2350001','Provision For Bad And Doubtful Debts Expense1','Provision For Bad And Doubtful Debts Expense1','02235000110','Debits','Expenses','Operating Expenses','Operating Expense','Provision For Bad And Doubtful Debts Expense','0','Active','10','Provision For Bad And Doubtful Debts Expense','10002','2018-12-12','NB'),(912,'2018-12-12','2360001','Rent Expense1','Rent Expense1','02236000110','Debits','Expenses','Operating Expenses','Operating Expense','Rent Expense','0','Active','10','Rent Expense','10002','2018-12-12','NB'),(913,'2018-12-12','2370001','Bad Debts Expense1','Bad Debts Expense1','02237000110','Debits','Expenses','Operating Expenses','Operating Expense','Bad Debts Expense','0','Active','10','Bad Debts Expense','10002','2018-12-12','NB'),(914,'2018-12-12','2380001','Car Maintainance And Repairs1','Car Maintainance And Repairs1','02238000110','Debits','Expenses','Operating Expenses','Operating Expense','Car Maintainance And Repairs','0','Active','10','Car Maintainance And Repairs','10002','2018-12-12','NB'),(915,'2018-12-12','2390001','Provision for Depreciation1','Provision for Depreciation1','02239000110','Debits','Expenses','Operating Expenses','Operating Expense','Provision for Depreciation','0','Active','10','Provision for Depreciation','10002','2018-12-12','NB'),(916,'2018-12-12','2400001','Office Maintainance Expense1','Office Maintainance Expense1','02240000110','Debits','Expenses','Operating Expenses','Operating Expense','Office Maintainance Expense','0','Active','10','Office Maintainance Expense','10002','2018-12-12','NB'),(917,'2018-12-12','2410001','Consultancy1','Consultancy1','02241000110','Debits','Expenses','Operating Expenses','Operating Expense','Consultancy','0','Active','10','Consultancy','10002','2018-12-12','NB'),(918,'2018-12-12','2420001','Loan Recovery Expenses1','Loan Recovery Expenses1','02242000110','Debits','Expenses','Operating Expenses','Operating Expense','Loan Recovery Expenses','0','Active','10','Loan Recovery Expenses','10002','2018-12-12','NB'),(919,'2018-12-13','2430001','Stationery And Printing1','Stationery And Printing1','02243000110','Debits','Expenses','Operating Expenses','Operating Expense','Stationery And Printing','600000.0','Active','10','Stationery And Printing','10002','2018-12-12','NB'),(920,'2018-12-12','2440001','Lunch Allowance1','Lunch Allowance1','02244000110','Debits','Expenses','Operating Expenses','Operating Expense','Lunch Allowance','0','Active','10','Lunch Allowance','10002','2018-12-12','NB'),(921,'2018-12-12','2450001','Telephone And Internet1','Telephone And Internet1','02245000110','Debits','Expenses','Operating Expenses','Operating Expense','Telephone And Internet','0','Active','10','Telephone And Internet','10002','2018-12-12','NB'),(922,'2018-12-12','2460001','Training Expenditure1','Training Expenditure1','02246000110','Debits','Expenses','Operating Expenses','Operating Expense','Training Expenditure','0','Active','10','Training Expenditure','10002','2018-12-12','NB'),(923,'2018-12-12','2470001','Lincenses And Permits1','Lincenses And Permits1','02247000110','Debits','Expenses','Operating Expenses','Operating Expense','Lincenses And Permits','0','Active','10','Lincenses And Permits','10002','2018-12-12','NB'),(924,'2018-12-12','2480001','Parking And Security1','Parking And Security1','02248000110','Debits','Expenses','Operating Expenses','Operating Expense','Parking And Security','0','Active','10','Parking And Security','10002','2018-12-12','NB'),(925,'2018-12-12','2790001','Compliance And Regulatory Expenses1','Compliance And Regulatory Expenses1','02279000110','Debits','Expenses','Operating Expenses','Operating Expense','Compliance And Regulatory Expenses','0','Active','10','Compliance And Regulatory Expenses','10002','2018-12-12','NB'),(926,'2018-12-12','2820001','Caveat Charges1','Caveat Charges1','02282000110','Debits','Expenses','Operating Expenses','Operating Expense','Caveat Charges','0','Active','10','Caveat Charges','10002','2018-12-12','NB'),(927,'2018-12-12','2530001','Salaries And Wages1','Salaries And Wages1','02253000110','Debits','Expenses','Administrative Expenses','Employment Expense','Salaries And Wages','0','Active','10','Salaries And Wages','10002','2018-12-12','NB'),(928,'2018-12-12','2540001','Bonus1','Bonus1','02254000110','Debits','Expenses','Administrative Expenses','Employment Expense','Bonus','0','Active','10','Bonus','10002','2018-12-12','NB'),(929,'2018-12-12','2550001','PAYEE1','PAYEE1','02255000110','Debits','Expenses','Administrative Expenses','Employment Expense','PAYEE','0','Active','10','PAYEE','10002','2018-12-12','NB'),(930,'2018-12-12','2560001','Board Sitting Allowances1','Board Sitting Allowances1','02256000110','Debits','Expenses','Administrative Expenses','Employment Expense','Board Sitting Allowances','0','Active','10','Board Sitting Allowances','10002','2018-12-12','NB'),(931,'2018-12-12','2570001','Employee Allowances1','Employee Allowances1','02257000110','Debits','Expenses','Administrative Expenses','Employment Expense','Employee Allowances','0','Active','10','Employee Allowances','10002','2018-12-12','NB'),(932,'2018-12-12','2580001','Directors Allowances1','Directors Allowances1','02258000110','Debits','Expenses','Administrative Expenses','Employment Expense','Directors Allowances','0','Active','10','Directors Allowances','10002','2018-12-12','NB'),(933,'2018-12-12','2590001','Contribution To Retirement Fund1','Contribution To Retirement Fund1','02259000110','Debits','Expenses','Administrative Expenses','Employment Expense','Contribution To Retirement Fund','0','Active','10','Contribution To Retirement Fund','10002','2018-12-12','NB'),(934,'2018-12-12','2600001','Medical Allowance1','Medical Allowance1','02260000110','Debits','Expenses','Administrative Expenses','Employment Expense','Medical Allowance','0','Active','10','Medical Allowance','10002','2018-12-12','NB'),(935,'2018-12-12','2610001','NSSF Contributions1','NSSF Contributions1','02261000110','Debits','Expenses','Administrative Expenses','Employment Expense','NSSF Contributions','0','Active','10','NSSF Contributions','10002','2018-12-12','NB'),(936,'2018-12-12','2800001','Festive Allowance1','Festive Allowance1','02280000110','Debits','Expenses','Administrative Expenses','Employment Expense','Festive Allowance','0','Active','10','Festive Allowance','10002','2018-12-12','NB'),(937,'2018-12-12','2810001','Employee Welfare1','Employee Welfare1','02281000110','Debits','Expenses','Administrative Expenses','Employment Expense','Employee Welfare','0','Active','10','Employee Welfare','10002','2018-12-12','NB'),(938,'2018-12-13','3010001','Gross Interest Income1','Gross Interest Income1','03301000110','Credits','Revenues','Revenue/Income','Main Income','Gross Interest Income','4.6155E7','Active','10','Gross Interest Income','10002','2018-12-12','NB'),(939,'2018-12-12','3040001','Ledger Fee1','Ledger Fee1','03304000110','Credits','Revenues','Revenue/Income','Other Income','Ledger Fee','0','Active','10','Ledger Fee','10002','2018-12-12','NB'),(943,'2018-12-12','3080001','Charges1','Charges1','03308000110','Credits','Revenues','Revenue/Income','Other Income','Charges','0','Active','10','Charges','10002','2018-12-12','NB'),(944,'2018-12-12','3100001','Loan Insurance Charges1','Loan Insurance Charges1','03310000110','Credits','Revenues','Revenue/Income','Other Income','Loan Insurance Charges','0','Active','10','Loan Insurance Charges','10002','2018-12-12','NB'),(946,'2018-12-13','3110001','Accumulated Interest Income1','Accumulated Interest Income1','03311000110','Credits','Revenues','Revenue/Income','Other Income','Accumulated Interest Income','1950000.0','Active','10','Accumulated Interest Income','10002','2018-12-12','NB'),(947,'2018-12-12','3120001','Loan Surcharge1','Loan Surcharge1','03312000110','Credits','Revenues','Revenue/Income','Other Income','Loan Surcharge','0','Active','10','Loan Surcharge','10002','2018-12-12','NB'),(948,'2018-12-12','3130001','Commission And Fees1','Commission And Fees1','03313000110','Credits','Revenues','Revenue/Income','Other Income','Commission And Fees','0','Active','10','Commission And Fees','10002','2018-12-12','NB'),(949,'2018-12-12','3150001','Loan Processing Fees1','Loan Processing Fees1','03315000110','Credits','Revenues','Revenue/Income','Other Income','Loan Processing Fees','0','Active','10','Loan Processing Fees','10002','2018-12-12','NB'),(950,'2018-12-12','3180001','Bad Debts Recovered1','Bad Debts Recovered1','03318000110','Credits','Revenues','Revenue/Income','Other Income','Bad Debts Recovered','0','Active','10','Bad Debts Recovered','10002','2018-12-12','NB'),(951,'2018-12-12','3240001','Savings Penalties1','Savings Penalties1','03324000110','Credits','Revenues','Revenue/Income','Other Income','Savings Penalties','0','Active','10','Savings Penalties','10002','2018-12-12','NB'),(952,'2018-12-12','3250001','Other Incomes1','Other Incomes1','03325000110','Credits','Revenues','Revenue/Income','Other Income','Other Incomes','0','Active','10','Other Incomes','10002','2018-12-12','NB'),(953,'2018-12-13','1220001','Bank Balance','DFCU Bank','01122000110','Debits','Assets','Current Assets, Loans And Advances','Cash And Bank Balance','Bank Balance','300000.0','Active','DFCU Bank','10002','10','2018-12-12','NB'),(954,'2018-12-13','1230001','Cash At Hand','Cash At Hand','01123000110','Debits','Assets','Current Assets, Loans And Advances','Cash And Bank Balance','Cash At Hand','3.7088471E7','Active','Cash At Hand','10002','10','2018-12-12','NB'),(955,'2018-12-12','1200001','Prepayments','Prepaid Accounts','01120000110','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Prepayments','0','Active','Prepaid Accounts','10002','10','2018-12-12','NB'),(956,'2018-12-12','1200002','Prepayments','Prepaid Rent','01120000210','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Prepayments','0','Active','Prepaid Rent','10002','10','2018-12-12','NB'),(957,'2018-12-13','1280001','Loans To Customers','Loans To Customers','01128000110','Debits','Assets','Current Assets, Loans And Advances','Loans And Advances','Loans To Customers','3.43832778E8','Active','Loans To Customers','10002','10','2018-12-12','NB'),(958,'2018-12-12','2630001','Bank Charges','Bank Charges','02263000110','Debits','Expenses','Financing Expenses','Financing Expense','Bank Charges','0','Active','Bank Charges','10002','10','2018-12-12','NB'),(959,'2018-12-12','5000001','Accounts Payable','Accounts Payables','05500000110','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Accounts Payable','0','Active','Accounts Payables','10002','10','2018-12-12','NB'),(960,'2018-12-12','4000001','Issued, Subscribed And Paid Up Capital','Paid Up Capital','04400000110','Credits','Equity/Capital','Equity','Share Capital','Issued, Subscribed And Paid Up Capital','5000000.0','Active','Paid Up Capital','10002','10','2018-12-12','NB'),(961,'2018-12-12','4080001','Retained Earnings','Retained Earnings','04408000110','Credits','Equity/Capital','Equity','Reserves And Surplus','Retained Earnings','3.28716249E8','Active','Retained Earnings','10002','10','2018-12-12','NB'),(962,'2018-12-12','1310001','Accounts Receivable','KALIBBALA FRANCIS/ MATOVU WILLIAM','01131000110','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','KALIBBALA FRANCIS/ MATOVU WILLIAM','10002','10','2018-12-12','NB'),(963,'2018-12-12','1310002','Accounts Receivable','ELASU CHRISTINE ','01131000210','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','ELASU CHRISTINE ','10002','10','2018-12-12','NB'),(964,'2018-12-12','1310003','Accounts Receivable','KUTOSI FRANCIS','01131000310','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','KUTOSI FRANCIS','10002','10','2018-12-12','NB'),(965,'2018-12-12','1310004','Accounts Receivable','JAGGWE RONALD','01131000410','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','JAGGWE RONALD','10002','10','2018-12-12','NB'),(966,'2018-12-12','1310005','Accounts Receivable','NSUBUGA RONALD','01131000510','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','NSUBUGA RONALD','10002','10','2018-12-12','NB'),(967,'2018-12-12','1310006','Accounts Receivable','BAZIRA DAN','01131000610','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','BAZIRA DAN','10002','10','2018-12-12','NB'),(968,'2018-12-12','1310007','Accounts Receivable','KALIBBALA VINCENT','01131000710','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','KALIBBALA VINCENT','10002','10','2018-12-12','NB'),(969,'2018-12-12','1310008','Accounts Receivable','KALIISA JOHN','01131000810','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','KALIISA JOHN','10002','10','2018-12-12','NB'),(970,'2018-12-12','1310009','Accounts Receivable','MULINDWA SAM','01131000910','Debits','Assets','Current Assets, Loans And Advances','Accounts Receivables/Debtors','Accounts Receivable','0','Active','MULINDWA SAM','10002','10','2018-12-12','NB'),(971,'2018-12-12','5020037','For holding Customer/Member account balance','Ssozi Joram','05502003710','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10002','2018-12-12','B'),(972,'2018-12-13','5020038','For holding Customer/Member account balance','Bazirake Augustine','05502003810','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-13','NB'),(973,'2018-12-13','5020039','For holding Customer/Member account balance','Terisa Mariah','05502003910','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-13','B'),(974,'2018-12-13','5020040','For holding Customer/Member account balance','Gilbert Mukama','05502004010','Credits','Liabilities','Current Liabilities & Provisions','Current Liabilities','Customer Deposits','0','Active','10','Transaction Deposit','10001','2018-12-13','B');
/*!40000 ALTER TABLE `account_created_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_master_store`
--

DROP TABLE IF EXISTS `account_master_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_master_store` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` varchar(100) DEFAULT NULL,
  `value_date` date NOT NULL,
  `account_name` varchar(100) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `running_balance` varchar(100) DEFAULT NULL,
  `creation_user_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_master_store`
--

LOCK TABLES `account_master_store` WRITE;
/*!40000 ALTER TABLE `account_master_store` DISABLE KEYS */;
INSERT INTO `account_master_store` VALUES (81,'2018-12-12','2018-12-12','Customer_Deposits','05502000010','0.0','10001'),(82,'2018-12-12','2018-12-12','Advertisement_Expense','02220000010','0.0','10002'),(83,'2018-12-12','2018-12-12','Audit_Expenses','02221000010','0.0','10002'),(84,'2018-12-12','2018-12-12','Bad_Debts_Written_Off','02222000010','0.0','10002'),(85,'2018-12-12','2018-12-12','Commission','02223000010','0.0','10002'),(86,'2018-12-12','2018-12-12','Computer_Soft_And_Hardware_Expenses','02224000010','0.0','10002'),(87,'2018-12-12','2018-12-12','Maintainance_of_Office_Equipment','02225000010','0.0','10002'),(88,'2018-12-12','2018-12-12','News_Papers_And_Periodicals','02226000010','0.0','10002'),(89,'2018-12-12','2018-12-12','Donations','02227000010','0.0','10002'),(90,'2018-12-12','2018-12-12','Entertainment_And_Refreshments','02228000010','0.0','10002'),(91,'2018-12-12','2018-12-12','Freight_And_Transport','02229000010','0.0','10002'),(92,'2018-12-12','2018-12-12','Air_Time_Allowance','02230000010','0.0','10002'),(93,'2018-12-12','2018-12-12','Hotel,_Boarding_And_Lodging_Expenses','02231000010','0.0','10002'),(94,'2018-12-12','2018-12-12','Legal_Expenses','02232000010','0.0','10002'),(95,'2018-12-12','2018-12-12','Fuel','02233000010','0.0','10002'),(96,'2018-12-12','2018-12-12','Utilities_Expenses','02234000010','0.0','10002'),(97,'2018-12-12','2018-12-12','Provision_For_Bad_And_Doubtful_Debts_Expense','02235000010','0.0','10002'),(98,'2018-12-12','2018-12-12','Rent_Expense','02236000010','0.0','10002'),(99,'2018-12-12','2018-12-12','Bad_Debts_Expense','02237000010','0.0','10002'),(100,'2018-12-12','2018-12-12','Car_Maintainance_And_Repairs','02238000010','0.0','10002'),(101,'2018-12-12','2018-12-12','Provision_for_Depreciation','02239000010','0.0','10002'),(102,'2018-12-12','2018-12-12','Office_Maintainance_Expense','02240000010','0.0','10002'),(103,'2018-12-12','2018-12-12','Consultancy','02241000010','0.0','10002'),(104,'2018-12-12','2018-12-12','Loan_Recovery_Expenses','02242000010','0.0','10002'),(105,'2018-12-12','2018-12-12','Stationery_And_Printing','02243000010','0.0','10002'),(106,'2018-12-12','2018-12-12','Lunch_Allowance','02244000010','0.0','10002'),(107,'2018-12-12','2018-12-12','Telephone_And_Internet','02245000010','0.0','10002'),(108,'2018-12-12','2018-12-12','Training_Expenditure','02246000010','0.0','10002'),(109,'2018-12-12','2018-12-12','Lincenses_And_Permits','02247000010','0.0','10002'),(110,'2018-12-12','2018-12-12','Parking_And_Security','02248000010','0.0','10002'),(111,'2018-12-12','2018-12-12','Compliance_And_Regulatory_Expenses','02279000010','0.0','10002'),(112,'2018-12-12','2018-12-12','Caveat_Charges','02282000010','0.0','10002'),(113,'2018-12-12','2018-12-12','Salaries_And_Wages','02253000010','0.0','10002'),(114,'2018-12-12','2018-12-12','Bonus','02254000010','0.0','10002'),(115,'2018-12-12','2018-12-12','PAYEE','02255000010','0.0','10002'),(116,'2018-12-12','2018-12-12','Board_Sitting_Allowances','02256000010','0.0','10002'),(117,'2018-12-12','2018-12-12','Employee_Allowances','02257000010','0.0','10002'),(118,'2018-12-12','2018-12-12','Directors_Allowances','02258000010','0.0','10002'),(119,'2018-12-12','2018-12-12','Contribution_To_Retirement_Fund','02259000010','0.0','10002'),(120,'2018-12-12','2018-12-12','Medical_Allowance','02260000010','0.0','10002'),(121,'2018-12-12','2018-12-12','NSSF_Contributions','02261000010','0.0','10002'),(122,'2018-12-12','2018-12-12','Festive_Allowance','02280000010','0.0','10002'),(123,'2018-12-12','2018-12-12','Employee_Welfare','02281000010','0.0','10002'),(124,'2018-12-12','2018-12-12','Gross_Interest_Income','03301000010','0.0','10002'),(125,'2018-12-12','2018-12-12','Ledger_Fee','03304000010','0.0','10002'),(126,'2018-12-12','2018-12-12','Savings_Withdraw_Charges','03305000010','0.0','10002'),(127,'2018-12-12','2018-12-12','Dividends','03306000010','0.0','10002'),(128,'2018-12-12','2018-12-12','Admin_Costs','03307000010','0.0','10002'),(129,'2018-12-12','2018-12-12','Charges','03308000010','0.0','10002'),(130,'2018-12-12','2018-12-12','Loan_Insurance_Charges','03310000010','0.0','10002'),(131,'2018-12-12','2018-12-12','Membership_Fees','03309000010','0.0','10002'),(132,'2018-12-12','2018-12-12','Accumulated_Interest_Income','03311000010','0.0','10002'),(133,'2018-12-12','2018-12-12','Loan_Surcharge','03312000010','0.0','10002'),(134,'2018-12-12','2018-12-12','Commission_And_Fees','03313000010','0.0','10002'),(135,'2018-12-12','2018-12-12','Loan_Processing_Fees','03315000010','0.0','10002'),(136,'2018-12-12','2018-12-12','Bad_Debts_Recovered','03318000010','0.0','10002'),(137,'2018-12-12','2018-12-12','Savings_Penalties','03324000010','0.0','10002'),(138,'2018-12-12','2018-12-12','Other_Incomes','03325000010','0.0','10002'),(139,'2018-12-12','2018-12-12','Furniture,_Fixtures_Equipment','01103000010','0.0','10002'),(140,'2018-12-12','2018-12-12','Bank_Balance','01122000010','0.0','10002'),(141,'2018-12-12','2018-12-12','Cash_At_Hand','01123000010','0.0','10002'),(142,'2018-12-12','2018-12-12','Prepayments','01120000010','0.0','10002'),(143,'2018-12-12','2018-12-12','Other_Fixed_Assets','01105000010','0.0','10002'),(144,'2018-12-12','2018-12-12','Loans_To_Customers','01128000010','0.0','10002'),(145,'2018-12-12','2018-12-12','Bank_Charges','02263000010','0.0','10002'),(146,'2018-12-12','2018-12-12','Accounts_Payable','05500000010','0.0','10002'),(147,'2018-12-12','2018-12-12','Issued,_Subscribed_And_Paid_Up_Capital','04400000010','0.0','10002'),(148,'2018-12-12','2018-12-12','Additional_Issued_Share_Capital','04401000010','0.0','10002'),(149,'2018-12-12','2018-12-12','Retained_Earnings','04408000010','0.0','10002'),(150,'2018-12-12','2018-12-12','Accounts_Receivable','01131000010','0.0','10002');
/*!40000 ALTER TABLE `account_master_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bankpostingbulk`
--

DROP TABLE IF EXISTS `bankpostingbulk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankpostingbulk` (
  `BankId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnId` varchar(100) DEFAULT '1',
  `account_number` varchar(100) DEFAULT '01122000010',
  `OtherOne1` varchar(100) DEFAULT 'NA',
  `OtherOne2` varchar(100) DEFAULT 'NA',
  `OtherOne3` varchar(100) DEFAULT 'NA',
  `OtherOne4` varchar(100) DEFAULT 'NA',
  `OtherOne5` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`BankId`),
  UNIQUE KEY `s_id` (`BankId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankpostingbulk`
--

LOCK TABLES `bankpostingbulk` WRITE;
/*!40000 ALTER TABLE `bankpostingbulk` DISABLE KEYS */;
/*!40000 ALTER TABLE `bankpostingbulk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batchpostingdetails`
--

DROP TABLE IF EXISTS `batchpostingdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchpostingdetails` (
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `ItemDate` date NOT NULL DEFAULT '1970-01-01',
  `BatchId` int(11) NOT NULL DEFAULT '1',
  `AccountName` varchar(100) NOT NULL DEFAULT 'Bazirake Googo',
  `AccountNumber` varchar(200) NOT NULL DEFAULT '01101100010',
  `Savings` varchar(100) NOT NULL DEFAULT '0.0',
  `Shares` varchar(100) NOT NULL DEFAULT '0.0',
  `Interest` varchar(100) NOT NULL DEFAULT '0.0',
  `AccumulatedInterest` varchar(100) NOT NULL DEFAULT '0.0',
  `PenaltyLoan` varchar(30) NOT NULL DEFAULT '0.0',
  `Principal` varchar(100) NOT NULL DEFAULT '0.0',
  `TotalAmount` varchar(100) NOT NULL DEFAULT '0.0',
  `BatchOther1` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther2` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther3` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther4` varchar(100) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`ItemId`),
  UNIQUE KEY `ItemId` (`ItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batchpostingdetails`
--

LOCK TABLES `batchpostingdetails` WRITE;
/*!40000 ALTER TABLE `batchpostingdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `batchpostingdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batchpostingsummury`
--

DROP TABLE IF EXISTS `batchpostingsummury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchpostingsummury` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `BatchId` int(11) NOT NULL DEFAULT '1',
  `BatchType` varchar(20) NOT NULL DEFAULT 'Loan',
  `NumberEntries` varchar(50) NOT NULL DEFAULT '30',
  `TotalAmount` varchar(100) NOT NULL DEFAULT '0.0',
  `BatchStatus` varchar(20) NOT NULL DEFAULT 'Created',
  `BatchOther1` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther2` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther3` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther4` varchar(100) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batchpostingsummury`
--

LOCK TABLES `batchpostingsummury` WRITE;
/*!40000 ALTER TABLE `batchpostingsummury` DISABLE KEYS */;
/*!40000 ALTER TABLE `batchpostingsummury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biodata`
--

DROP TABLE IF EXISTS `biodata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biodata` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(100) NOT NULL DEFAULT 'Googo',
  `Last_Name` varchar(100) NOT NULL DEFAULT 'Bazi',
  `account_name` varchar(100) NOT NULL DEFAULT 'Googo Bazi',
  `account_number` varchar(100) NOT NULL DEFAULT '01123000010',
  `Home_District` varchar(200) NOT NULL DEFAULT '0.0',
  `Kampala_Residance` varchar(200) NOT NULL DEFAULT '0.0',
  `Mobile` varchar(200) NOT NULL DEFAULT '0.0',
  `Phone` varchar(200) NOT NULL DEFAULT '0.0',
  `Date_Of_Birth` date NOT NULL DEFAULT '1970-01-01',
  `Sex` varchar(50) NOT NULL DEFAULT '0.0',
  `Next_Of_Kin` varchar(200) NOT NULL DEFAULT '0.0',
  `Kin_Contact` varchar(200) NOT NULL DEFAULT '0.0',
  `Kin_Email` varchar(200) NOT NULL DEFAULT '0.0',
  `Kin_Relationship` varchar(200) NOT NULL DEFAULT '0.0',
  `Type_Of_Busisness` varchar(200) NOT NULL DEFAULT '0.0',
  `Business_Loacation` varchar(200) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biodata`
--

LOCK TABLES `biodata` WRITE;
/*!40000 ALTER TABLE `biodata` DISABLE KEYS */;
/*!40000 ALTER TABLE `biodata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biodatametadata`
--

DROP TABLE IF EXISTS `biodatametadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biodatametadata` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` date NOT NULL DEFAULT '1970-01-01',
  `bio_name` varchar(100) NOT NULL DEFAULT 'Sex',
  `bio_data` varchar(100) NOT NULL DEFAULT 'Sex',
  `bio_status` varchar(100) NOT NULL DEFAULT 'Active',
  `other_one` varchar(100) NOT NULL DEFAULT 'NA',
  `other_two` varchar(100) NOT NULL DEFAULT 'NA',
  `other_three` varchar(100) NOT NULL DEFAULT 'NA',
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biodatametadata`
--

LOCK TABLES `biodatametadata` WRITE;
/*!40000 ALTER TABLE `biodatametadata` DISABLE KEYS */;
/*!40000 ALTER TABLE `biodatametadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_guarantee`
--

DROP TABLE IF EXISTS `borrow_guarantee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrow_guarantee` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date DEFAULT NULL,
  `account_name` varchar(100) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `number_shares` varchar(100) DEFAULT NULL,
  `value_shares` varchar(100) DEFAULT NULL,
  `borrow_qalified` varchar(100) DEFAULT NULL,
  `guarantee_qalified` varchar(100) DEFAULT NULL,
  `borrowing_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`s_id`),
  UNIQUE KEY `s_id` (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=820 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_guarantee`
--

LOCK TABLES `borrow_guarantee` WRITE;
/*!40000 ALTER TABLE `borrow_guarantee` DISABLE KEYS */;
INSERT INTO `borrow_guarantee` VALUES (780,'2018-12-12','Nanyonga Sandra','05502000110','0','0','0','0','NB'),(781,'2018-12-12','Namatovu Josephine','05502000210','0','0','0','0','NB'),(782,'2018-12-12','Kalibala Francis','05502000310','0','0','0','0','NB'),(783,'2018-12-12','Kalibbala, Matovu  Francis/William','05502000410','0','0','0','0','NB'),(784,'2018-12-12','Kutosi Francis Mabonga','05502000510','0','0','0','0','NB'),(785,'2018-12-12','Ssebana Kizito','05502000610','0','0','0','0','NB'),(786,'2018-12-12','Jaggwe Ronald','05502000710','0','0','0','0','NB'),(787,'2018-12-12','Nsubuga Ronald','05502000810','0','0','0','0','NB'),(788,'2018-12-12','Nsubuga Musoke James','05502000910','0','0','0','0','NB'),(789,'2018-12-12','Kalibbala Vicent','05502001010','0','0','0','0','NB'),(790,'2018-12-12','Mulindwa Sam','05502001110','0','0','0','0','NB'),(791,'2018-12-12','Magembe Nansubuga  Beatrice','05502001210','0','0','0','0','NB'),(792,'2018-12-12','Bazira Dan','05502001310','0','0','0','0','NB'),(793,'2018-12-12','Wakabi Walter Wales','05502001410','0','0','0','0','NB'),(794,'2018-12-12','Kalanda Umar','05502001510','0','0','0','0','NB'),(795,'2018-12-12','Rugaba Maxime','05502001610','0','0','0','0','NB'),(796,'2018-12-12','Kalisa John','05502001710','0','0','0','0','NB'),(797,'2018-12-12','Kateregga John','05502001810','0','0','0','0','NB'),(798,'2018-12-12','Ssewakilyanga Brian','05502001910','0','0','0','0','NB'),(799,'2018-12-12','Kalumba Joyce','05502002010','0','0','0','0','NB'),(800,'2018-12-12','Murungi Lewis Lennox','05502002110','0','0','0','0','NB'),(801,'2018-12-12','Senyonjo Fred','05502002210','0','0','0','0','NB'),(802,'2018-12-12','Tenywa Joseph','05502002310','0','0','0','0','NB'),(803,'2018-12-12','Lumala Mark William','05502002410','0','0','0','0','NB'),(804,'2018-12-12','Kyasa Francis Xavier','05502002510','0','0','0','0','NB'),(805,'2018-12-12','Tegule Fred','05502002610','0','0','0','0','NB'),(806,'2018-12-12','Kazibwe Eliam','05502002710','0','0','0','0','NB'),(807,'2018-12-12','Ssejjemba Emmanuel','05502002810','0','0','0','0','NB'),(808,'2018-12-12','Ssali Kafeero  Michael','05502002910','0','0','0','0','NB'),(809,'2018-12-12','Sserujongi Derrick','05502003010','0','0','0','0','NB'),(810,'2018-12-12','Katula James','05502003110','0','0','0','0','NB'),(811,'2018-12-12','Mutumba Jimmy','05502003210','0','0','0','0','NB'),(812,'2018-12-12','Mwere Paul','05502003310','0','0','0','0','NB'),(813,'2018-12-12','Businge Rusoke  Victoria','05502003410','0','0','0','0','NB'),(814,'2018-12-12','Mwanje Fred','05502003510','0','0','0','0','NB'),(815,'2018-12-12','Luggya Habib','05502003610','0','0','0','0','NB'),(816,'2018-12-12','Ssozi Joram','05502003710','0','0','0','0','NB'),(817,'2018-12-13','Bazirake Augustine','05502003810','0','0','0','0','NB'),(818,'2018-12-13','Terisa Mariah','05502003910','0','0','0','0','NB'),(819,'2018-12-13','Gilbert Mukama','05502004010','0','0','0','0','NB');
/*!40000 ALTER TABLE `borrow_guarantee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bsanca01103000010`
--



--
-- Table structure for table `bsanca05502003510`
--

DROP TABLE IF EXISTS `bsanca05502003510`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502003510` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502003510`
--

LOCK TABLES `bsanca05502003510` WRITE;
/*!40000 ALTER TABLE `bsanca05502003510` DISABLE KEYS */;
INSERT INTO `bsanca05502003510` VALUES (1000,'2018-12-12','Opening Balance','2018-12-12','-','-','0.0','-','-','-','-','-','-','11:33:54','-','-','-','NA','NA','NA'),(1001,'2018-12-12','Mwanje Fred Loan Disbursed Processed on 12/12/2018\n  On Mwanje Fred','2018-12-12','-','3.0E7','3.0E7','01128000110','Loans To Customers','0002','BTN18305','Gen','10002','13:57:51','0092','05502003510','05502000010','Cr','NA','NA'),(1002,'2018-12-12','Mwanje\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3.0E7','-','0','01123000110','Cash At Hand','000zib','BTN18305','Gen','10002','13:57:51','0093','05502003510','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502003510` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502003510 BEFORE INSERT ON BSANCA05502003510 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502003510(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bsanca05502003610`
--

DROP TABLE IF EXISTS `bsanca05502003610`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502003610` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502003610`
--

LOCK TABLES `bsanca05502003610` WRITE;
/*!40000 ALTER TABLE `bsanca05502003610` DISABLE KEYS */;
INSERT INTO `bsanca05502003610` VALUES (1000,'2018-12-12','Opening Balance','2018-12-12','-','-','0.0','-','-','-','-','-','-','11:33:54','-','-','-','NA','NA','NA'),(1001,'2018-12-12','Luggya Habib Loan Disbursed Processed on 12/12/2018\n  On Luggya Habib','2018-12-12','-','1500000.0','1500000.0','01128000110','Loans To Customers','0002','BTN18300','Gen','10002','13:57:41','0072','05502003610','05502000010','Cr','NA','NA'),(1002,'2018-12-12','Luggya\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1500000.0','-','0','01123000110','Cash At Hand','000zib','BTN18300','Gen','10002','13:57:41','0073','05502003610','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502003610` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502003610 BEFORE INSERT ON BSANCA05502003610 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502003610(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bsanca05502003710`
--

DROP TABLE IF EXISTS `bsanca05502003710`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502003710` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502003710`
--

LOCK TABLES `bsanca05502003710` WRITE;
/*!40000 ALTER TABLE `bsanca05502003710` DISABLE KEYS */;
INSERT INTO `bsanca05502003710` VALUES (1000,'2018-12-12','Opening Balance','2018-12-12','-','-','0.0','-','-','-','-','-','-','14:32:32','-','-','-','NA','NA','NA'),(1001,'2018-12-12','Ssozi Joram\'s loan disbursement Processed on 12/12/2018\n  On Ssozi Joram','2018-12-12','-','3000000.0','3000000.0','01128000110','Loans To Customers','0002','BTN18317','Gen','10002','14:33:53','0140','05502003710','05502000010','Cr','NA','NA'),(1002,'2018-12-12','Ssozi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','0','01123000110','Cash At Hand','000zib','BTN18317','Gen','10002','14:33:53','0141','05502003710','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502003710` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502003710 BEFORE INSERT ON BSANCA05502003710 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502003710(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bsanca05502003810`
--

DROP TABLE IF EXISTS `bsanca05502003810`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502003810` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502003810`
--

LOCK TABLES `bsanca05502003810` WRITE;
/*!40000 ALTER TABLE `bsanca05502003810` DISABLE KEYS */;
INSERT INTO `bsanca05502003810` VALUES (1000,'2018-12-13','Opening Balance','2018-12-13','-','-','0.0','-','-','-','-','-','-','08:28:07','-','-','-','NA','NA','NA'),(1001,'2018-12-13','Bazirake Augustine\'s loan disbursement Processed on 13/12/2018\n  On Bazirake Augustine','2018-12-13','-','3000000.0','3000000.0','01128000110','Loans To Customers','0002','BTN18322','Gen','10001','08:29:29','0025','05502003810','05502000010','Cr','NA','NA'),(1002,'2018-12-13','Bazirake\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','0','01123000110','Cash At Hand','000zib','BTN18322','Gen','10001','08:29:29','0026','05502003810','05502000010','Dr','NA','NA'),(1003,'2018-12-13','Bazirake\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','3900000.0','3900000.0','01123000110','Cash At Hand','000zib','BTN18323','LoanR','10001','08:30:49','0031','05502003810','05502000010','Cr','NA','NA'),(1004,'2018-12-13','Bazirake Augustine\'s LOAN COMPLETEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','3900000.0','-','0','01128000110','Loans To Customers','0002','BTN18323','LoanR','10001','08:30:49','0028','05502003810','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502003810` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502003810 BEFORE INSERT ON BSANCA05502003810 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502003810(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bsanca05502003910`
--

DROP TABLE IF EXISTS `bsanca05502003910`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502003910` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502003910`
--

LOCK TABLES `bsanca05502003910` WRITE;
/*!40000 ALTER TABLE `bsanca05502003910` DISABLE KEYS */;
INSERT INTO `bsanca05502003910` VALUES (1000,'2018-12-13','Opening Balance','2018-12-13','-','-','0.0','-','-','-','-','-','-','08:36:38','-','-','-','NA','NA','NA'),(1001,'2018-12-13','Terisa Mariah\'s loan disbursement Processed on 13/12/2018\n  On Terisa Mariah','2018-12-13','-','3000000.0','3000000.0','01128000110','Loans To Customers','0002','BTN18324','Gen','10001','08:37:44','0035','05502003910','05502000010','Cr','NA','NA'),(1002,'2018-12-13','Terisa\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','0','01123000110','Cash At Hand','000zib','BTN18324','Gen','10001','08:37:44','0036','05502003910','05502000010','Dr','NA','NA'),(1003,'2018-12-13','Terisa\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','300000.0','300000.0','01122000110','DFCU Bank','000zib','BTN18325','LoanR','10001','08:38:50','0041','05502003910','05502000010','Cr','NA','NA'),(1004,'2018-12-13','Terisa Mariah\'s Principal and Interest PaymentDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','300000.0','-','0','01128000110','Loans To Customers','0002','BTN18325','LoanR','10001','08:38:50','0038','05502003910','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502003910` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502003910 BEFORE INSERT ON BSANCA05502003910 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502003910(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bsanca05502004010`
--

DROP TABLE IF EXISTS `bsanca05502004010`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bsanca05502004010` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) NOT NULL DEFAULT '0.0',
  `credit` varchar(50) NOT NULL DEFAULT '0.0',
  `ledger_balance` varchar(50) NOT NULL DEFAULT '0.0',
  `credit_account_no` varchar(50) NOT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(10) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT '05502000110',
  `master_number` varchar(100) DEFAULT '05502000110',
  `other_one` varchar(100) DEFAULT 'NA',
  `other_two` varchar(100) DEFAULT 'NA',
  `other_three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bsanca05502004010`
--

LOCK TABLES `bsanca05502004010` WRITE;
/*!40000 ALTER TABLE `bsanca05502004010` DISABLE KEYS */;
INSERT INTO `bsanca05502004010` VALUES (1000,'2018-12-13','Opening Balance','2018-12-13','-','-','0.0','-','-','-','-','-','-','11:29:24','-','-','-','NA','NA','NA'),(1001,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  On Gilbert Mukama','2018-12-13','-','5000000.0','5000000.0','01128000110','Loans To Customers','0002','BTN18328','Gen','10001','11:33:44','0052','05502004010','05502000010','Cr','NA','NA'),(1002,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','0','01123000110','Cash At Hand','000zib','BTN18328','Gen','10001','11:33:44','0053','05502004010','05502000010','Dr','NA','NA'),(1003,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  On Gilbert Mukama','2018-12-13','-','5000000.0','5000000.0','01128000110','Loans To Customers','0002','BTN18341','Gen','10001','08:45:02','0108','05502004010','05502000010','Cr','NA','NA'),(1004,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','0','01123000110','Cash At Hand','000zib','BTN18341','Gen','10001','08:45:02','0109','05502004010','05502000010','Dr','NA','NA');
/*!40000 ALTER TABLE `bsanca05502004010` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER TSANCA05502004010 BEFORE INSERT ON BSANCA05502004010 FOR EACH ROW BEGIN  IF(NEW.other_one LIKE '%Cr%') THEN 
SET @creditAccount=NEW.account_number;
 SET @debitAccount=NEW.credit_account_no;
CALL accountNma(@creditAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @creditAccount,@debitAccount,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
 END IF;
IF(NEW.other_one LIKE '%Dr%') THEN 
SET @creditAccount=NEW.credit_account_no;
SET @debitAccount=NEW.account_number;
CALL accountNma(@debitAccount,@accountName);
INSERT INTO general_ledger (trn_id ,trn_date ,narration ,value_date ,debit ,credit,debit_account_no,credit_account_no,credit_account_name,tra_ref_number ,chq_number ,trn_type ,staff_id ,trn_time ,trn_sq_no)
 VALUES (null, NEW.trn_date, NEW.narration, NEW.value_date,NEW.debit, NEW.credit, @debitAccount ,@creditAccount ,@accountName,NEW.tra_ref_number ,NEW.chq_number ,NEW.trn_type ,NEW.staff_id ,NEW.trn_time ,NEW.trn_sq_no);
END IF;
UPDATE account_created_store SET running_balance=NEW.ledger_balance,trn_date=NEW.trn_date  WHERE account_number=NEW.account_number;
 CALL   updateMaster05502004010(NEW.trn_date,NEW.account_number,NEW.ledger_balance,NEW.staff_id); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `budgetstore`
--

DROP TABLE IF EXISTS `budgetstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budgetstore` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `AccountType` varchar(100) NOT NULL DEFAULT 'Expense Account',
  `AccountMaster` varchar(100) NOT NULL DEFAULT 'Bad Debts Written Off',
  `BYear` int(11) DEFAULT '2017',
  `BMonthWord` varchar(100) NOT NULL DEFAULT 'January',
  `BMonthNumber` int(11) DEFAULT '1',
  `BAmount` varchar(100) NOT NULL DEFAULT '0.0',
  `AAmount` varchar(100) NOT NULL DEFAULT '0.0',
  `BOther2` varchar(100) NOT NULL DEFAULT 'N/A',
  `BOther3` varchar(100) NOT NULL DEFAULT 'N/A',
  `BOther4` varchar(100) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budgetstore`
--

LOCK TABLES `budgetstore` WRITE;
/*!40000 ALTER TABLE `budgetstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `budgetstore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bulkuploads`
--

DROP TABLE IF EXISTS `bulkuploads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bulkuploads` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `Narration` varchar(500) DEFAULT 'Bank withdraw',
  `TrnType` varchar(45) DEFAULT 'Deposit',
  `TrnCode` varchar(45) DEFAULT 'Dis',
  `Debit` varchar(45) DEFAULT '0',
  `Credit` varchar(45) DEFAULT '0',
  `InterestRate` varchar(45) DEFAULT '0',
  `Tenure` varchar(45) DEFAULT '0',
  `interestRegime` varchar(45) DEFAULT '1',
  `PeriodType` varchar(45) DEFAULT '1',
  `RunningBalance` varchar(45) DEFAULT '0',
  `debitAccount` varchar(45) DEFAULT '01111000010',
  `creditAccount` varchar(45) DEFAULT '01111000010',
  `OtherThree1` varchar(45) DEFAULT 'N/A',
  `OtherThree2` varchar(45) DEFAULT 'N/A',
  `OtherThree3` varchar(45) DEFAULT 'N/A',
  `OtherThree4` varchar(45) DEFAULT 'N/A',
  `OtherThree5` varchar(45) DEFAULT 'N/A',
  `OtherThree6` varchar(45) DEFAULT 'N/A',
  `OtherThree7` varchar(45) DEFAULT 'N/A',
  `OtherThree` varchar(45) DEFAULT 'N/A',
  `OtherFour` varchar(45) DEFAULT 'N/A',
  `OtherFive` varchar(45) DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bulkuploads`
--

LOCK TABLES `bulkuploads` WRITE;
/*!40000 ALTER TABLE `bulkuploads` DISABLE KEYS */;
/*!40000 ALTER TABLE `bulkuploads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bulkuploadsland`
--

DROP TABLE IF EXISTS `bulkuploadsland`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bulkuploadsland` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `Narration` varchar(500) DEFAULT 'Bank withdraw',
  `TrnType` varchar(45) DEFAULT 'Deposit',
  `TrnCode` varchar(45) DEFAULT 'Deposit',
  `Debit` varchar(45) DEFAULT '0',
  `Credit` varchar(45) DEFAULT '0',
  `InterestRate` varchar(45) DEFAULT '0',
  `Tenure` varchar(45) DEFAULT '0',
  `interestRegime` varchar(45) DEFAULT '1',
  `PeriodType` varchar(45) DEFAULT '1',
  `RunningBalance` varchar(45) DEFAULT '0',
  `StatusOfAction` varchar(45) DEFAULT '0',
  `OtherOne1` varchar(45) DEFAULT '0',
  `OtherOne2` varchar(45) DEFAULT '0',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bulkuploadsland`
--

LOCK TABLES `bulkuploadsland` WRITE;
/*!40000 ALTER TABLE `bulkuploadsland` DISABLE KEYS */;
/*!40000 ALTER TABLE `bulkuploadsland` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `close_day`
--

DROP TABLE IF EXISTS `close_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `close_day` (
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `close_day`
--

LOCK TABLES `close_day` WRITE;
/*!40000 ALTER TABLE `close_day` DISABLE KEYS */;
/*!40000 ALTER TABLE `close_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `depreciationschedule`
--

DROP TABLE IF EXISTS `depreciationschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `depreciationschedule` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `Period` int(11) NOT NULL DEFAULT '2',
  `DepriDate` date NOT NULL DEFAULT '1970-01-01',
  `DepMonth` varchar(60) NOT NULL DEFAULT 'January',
  `DepYear` varchar(30) NOT NULL DEFAULT '2017',
  `AssetId` int(11) NOT NULL,
  `AssetAccountNumber` varchar(45) DEFAULT '055020000010',
  `OpeningAssetValue` varchar(45) DEFAULT '0.0',
  `AcccumulatedDepreciation` varchar(45) NOT NULL DEFAULT '0.0',
  `Depreciation` varchar(45) DEFAULT '0.0',
  `ClosingAssetValue` varchar(45) DEFAULT '0.0',
  `DepreciationStatus` varchar(45) DEFAULT 'Other',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depreciationschedule`
--

LOCK TABLES `depreciationschedule` WRITE;
/*!40000 ALTER TABLE `depreciationschedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `depreciationschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `depreciationstore`
--

DROP TABLE IF EXISTS `depreciationstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `depreciationstore` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `PurchaseDate` date NOT NULL DEFAULT '1970-01-01',
  `NextDepriDate` date NOT NULL DEFAULT '1970-01-01',
  `DepriMethod` varchar(45) DEFAULT '0',
  `AssetAccountNumber` varchar(45) DEFAULT '055020000010',
  `AccumDepriAccountNumber` varchar(45) DEFAULT '055020000010',
  `PurchasePrice` varchar(45) DEFAULT '0.0',
  `accumDep` varchar(45) NOT NULL DEFAULT '0.0',
  `SalvageValue` varchar(45) DEFAULT '0.0',
  `UsefulLife` varchar(45) DEFAULT '0.0',
  `DepreciationStatus` varchar(45) DEFAULT 'Other',
  `OtherThree` varchar(45) DEFAULT 'NCO',
  `OtherFour` varchar(45) DEFAULT 'NCO',
  `OtherFive` varchar(45) DEFAULT 'NCO',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depreciationstore`
--

LOCK TABLES `depreciationstore` WRITE;
/*!40000 ALTER TABLE `depreciationstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `depreciationstore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_ledger`
--

DROP TABLE IF EXISTS `general_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `general_ledger` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `value_date` date NOT NULL,
  `debit` varchar(50) DEFAULT '0.0',
  `credit` varchar(50) DEFAULT '0.0',
  `debit_account_no` varchar(50) DEFAULT NULL,
  `credit_account_no` varchar(50) DEFAULT NULL,
  `credit_account_name` varchar(50) DEFAULT NULL,
  `tra_ref_number` varchar(50) DEFAULT NULL,
  `chq_number` varchar(50) DEFAULT NULL,
  `trn_type` varchar(50) DEFAULT NULL,
  `staff_id` varchar(10) DEFAULT NULL,
  `trn_time` time DEFAULT NULL,
  `trn_sq_no` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_ledger`
--

LOCK TABLES `general_ledger` WRITE;
/*!40000 ALTER TABLE `general_ledger` DISABLE KEYS */;
INSERT INTO `general_ledger` VALUES (38276,'2018-12-12','Start Up Capital Processed on 12/12/2018\n  From Paid Up Capital','2018-12-12','5000000.0','-','01123000110','04400000110','Cash At Hand','0002','BTN18290','Gen','10002','13:49:29','0037'),(38277,'2018-12-12','Start Up Capital Processed on 12/12/2018\n  From Paid Up Capital','2018-12-12','-','5000000.0','04400000110','01123000110','Paid Up Capital','0002','BTN18290','Gen','10002','13:49:29','0038'),(38278,'2018-12-12','Retained Earnings as At 31/12/2018 Processed on 12/12/2018\n  From Retained Earnings','2018-12-12','1.7819154E8','-','01123000110','04408000110','Cash At Hand','0002','BTN18291','Gen','10002','13:51:23','0039'),(38279,'2018-12-12','Retained Earnings as At 31/12/2018 Processed on 12/12/2018\n  From Retained Earnings','2018-12-12','-','1.7819154E8','04408000110','01123000110','Retained Earnings','0002','BTN18291','Gen','10002','13:51:23','0040'),(38280,'2018-12-12','Retained Earnings as at 30/11/2018 Processed on 12/12/2018\n  From Retained Earnings','2018-12-12','1.50524709E8','-','01123000110','04408000110','Cash At Hand','0002','BTN18292','Gen','10002','13:54:40','0041'),(38281,'2018-12-12','Retained Earnings as at 30/11/2018 Processed on 12/12/2018\n  From Retained Earnings','2018-12-12','-','1.50524709E8','04408000110','01123000110','Retained Earnings','0002','BTN18292','Gen','10002','13:54:40','0042'),(38282,'2018-12-12','Businge Rusoke  Victoria Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','6500000.0','-','01128000110','05502003410','Loans To Customers','0002','BTN18293','Gen','10002','13:57:27','0043'),(38283,'2018-12-12','Businge Rusoke  Victoria Loan Disbursed Processed on 12/12/2018\n  On Businge Rusoke  Victoria','2018-12-12','-','6500000.0','05502003410','01128000110','Businge Rusoke  Victoria','0002','BTN18293','Gen','10002','13:57:27','0044'),(38284,'2018-12-12','Businge\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','6500000.0','-','05502003410','01123000110','Businge Rusoke  Victoria','000zib','BTN18293','Gen','10002','13:57:27','0045'),(38285,'2018-12-12','Businge\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','6500000.0','01123000110','05502003410','Cash At Hand','000zib','BTN18293','Gen','10002','13:57:27','0046'),(38286,'2018-12-12','Kalanda Umar Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','1.05E7','-','01128000110','05502001510','Loans To Customers','0002','BTN18294','Gen','10002','13:57:29','0047'),(38287,'2018-12-12','Kalanda Umar Loan Disbursed Processed on 12/12/2018\n  On Kalanda Umar','2018-12-12','-','1.05E7','05502001510','01128000110','Kalanda Umar','0002','BTN18294','Gen','10002','13:57:29','0048'),(38288,'2018-12-12','Kalanda\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1.05E7','-','05502001510','01123000110','Kalanda Umar','000zib','BTN18294','Gen','10002','13:57:29','0049'),(38289,'2018-12-12','Kalanda\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','1.05E7','01123000110','05502001510','Cash At Hand','000zib','BTN18294','Gen','10002','13:57:29','0050'),(38290,'2018-12-12','Kalumba Joyce Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502002010','Loans To Customers','0002','BTN18295','Gen','10002','13:57:31','0051'),(38291,'2018-12-12','Kalumba Joyce Loan Disbursed Processed on 12/12/2018\n  On Kalumba Joyce','2018-12-12','-','3000000.0','05502002010','01128000110','Kalumba Joyce','0002','BTN18295','Gen','10002','13:57:31','0052'),(38292,'2018-12-12','Kalumba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502002010','01123000110','Kalumba Joyce','000zib','BTN18295','Gen','10002','13:57:31','0053'),(38293,'2018-12-12','Kalumba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502002010','Cash At Hand','000zib','BTN18295','Gen','10002','13:57:31','0054'),(38294,'2018-12-12','Kateregga John Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','1.3E7','-','01128000110','05502001810','Loans To Customers','0002','BTN18296','Gen','10002','13:57:33','0055'),(38295,'2018-12-12','Kateregga John Loan Disbursed Processed on 12/12/2018\n  On Kateregga John','2018-12-12','-','1.3E7','05502001810','01128000110','Kateregga John','0002','BTN18296','Gen','10002','13:57:33','0056'),(38296,'2018-12-12','Kateregga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1.3E7','-','05502001810','01123000110','Kateregga John','000zib','BTN18296','Gen','10002','13:57:33','0057'),(38297,'2018-12-12','Kateregga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','1.3E7','01123000110','05502001810','Cash At Hand','000zib','BTN18296','Gen','10002','13:57:33','0058'),(38298,'2018-12-12','Katula James Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502003110','Loans To Customers','0002','BTN18297','Gen','10002','13:57:35','0059'),(38299,'2018-12-12','Katula James Loan Disbursed Processed on 12/12/2018\n  On Katula James','2018-12-12','-','3000000.0','05502003110','01128000110','Katula James','0002','BTN18297','Gen','10002','13:57:35','0060'),(38300,'2018-12-12','Katula\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502003110','01123000110','Katula James','000zib','BTN18297','Gen','10002','13:57:35','0061'),(38301,'2018-12-12','Katula\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502003110','Cash At Hand','000zib','BTN18297','Gen','10002','13:57:35','0062'),(38302,'2018-12-12','Kazibwe Eliam Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','2000000.0','-','01128000110','05502002710','Loans To Customers','0002','BTN18298','Gen','10002','13:57:36','0063'),(38303,'2018-12-12','Kazibwe Eliam Loan Disbursed Processed on 12/12/2018\n  On Kazibwe Eliam','2018-12-12','-','2000000.0','05502002710','01128000110','Kazibwe Eliam','0002','BTN18298','Gen','10002','13:57:36','0064'),(38304,'2018-12-12','Kazibwe\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','2000000.0','-','05502002710','01123000110','Kazibwe Eliam','000zib','BTN18298','Gen','10002','13:57:36','0065'),(38305,'2018-12-12','Kazibwe\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','2000000.0','01123000110','05502002710','Cash At Hand','000zib','BTN18298','Gen','10002','13:57:36','0066'),(38306,'2018-12-12','Kyasa Francis Xavier Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502002510','Loans To Customers','0002','BTN18299','Gen','10002','13:57:40','0067'),(38307,'2018-12-12','Kyasa Francis Xavier Loan Disbursed Processed on 12/12/2018\n  On Kyasa Francis Xavier','2018-12-12','-','3000000.0','05502002510','01128000110','Kyasa Francis Xavier','0002','BTN18299','Gen','10002','13:57:40','0068'),(38308,'2018-12-12','Kyasa\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502002510','01123000110','Kyasa Francis Xavier','000zib','BTN18299','Gen','10002','13:57:40','0069'),(38309,'2018-12-12','Kyasa\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502002510','Cash At Hand','000zib','BTN18299','Gen','10002','13:57:40','0070'),(38310,'2018-12-12','Luggya Habib Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','1500000.0','-','01128000110','05502003610','Loans To Customers','0002','BTN18300','Gen','10002','13:57:41','0071'),(38311,'2018-12-12','Luggya Habib Loan Disbursed Processed on 12/12/2018\n  On Luggya Habib','2018-12-12','-','1500000.0','05502003610','01128000110','Luggya Habib','0002','BTN18300','Gen','10002','13:57:41','0072'),(38312,'2018-12-12','Luggya\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1500000.0','-','05502003610','01123000110','Luggya Habib','000zib','BTN18300','Gen','10002','13:57:41','0073'),(38313,'2018-12-12','Luggya\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','1500000.0','01123000110','05502003610','Cash At Hand','000zib','BTN18300','Gen','10002','13:57:41','0074'),(38314,'2018-12-12','Lumala Mark William Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502002410','Loans To Customers','0002','BTN18301','Gen','10002','13:57:43','0075'),(38315,'2018-12-12','Lumala Mark William Loan Disbursed Processed on 12/12/2018\n  On Lumala Mark William','2018-12-12','-','3000000.0','05502002410','01128000110','Lumala Mark William','0002','BTN18301','Gen','10002','13:57:43','0076'),(38316,'2018-12-12','Lumala\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502002410','01123000110','Lumala Mark William','000zib','BTN18301','Gen','10002','13:57:43','0077'),(38317,'2018-12-12','Lumala\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502002410','Cash At Hand','000zib','BTN18301','Gen','10002','13:57:43','0078'),(38318,'2018-12-12','Magembe Nansubuga  Beatrice Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','5.5E7','-','01128000110','05502001210','Loans To Customers','0002','BTN18302','Gen','10002','13:57:45','0079'),(38319,'2018-12-12','Magembe Nansubuga  Beatrice Loan Disbursed Processed on 12/12/2018\n  On Magembe Nansubuga  Beatrice','2018-12-12','-','5.5E7','05502001210','01128000110','Magembe Nansubuga  Beatrice','0002','BTN18302','Gen','10002','13:57:45','0080'),(38320,'2018-12-12','Magembe\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','5.5E7','-','05502001210','01123000110','Magembe Nansubuga  Beatrice','000zib','BTN18302','Gen','10002','13:57:45','0081'),(38321,'2018-12-12','Magembe\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','5.5E7','01123000110','05502001210','Cash At Hand','000zib','BTN18302','Gen','10002','13:57:45','0082'),(38322,'2018-12-12','Murungi Lewis Lennox Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3.0E7','-','01128000110','05502002110','Loans To Customers','0002','BTN18303','Gen','10002','13:57:47','0083'),(38323,'2018-12-12','Murungi Lewis Lennox Loan Disbursed Processed on 12/12/2018\n  On Murungi Lewis Lennox','2018-12-12','-','3.0E7','05502002110','01128000110','Murungi Lewis Lennox','0002','BTN18303','Gen','10002','13:57:47','0084'),(38324,'2018-12-12','Murungi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3.0E7','-','05502002110','01123000110','Murungi Lewis Lennox','000zib','BTN18303','Gen','10002','13:57:47','0085'),(38325,'2018-12-12','Murungi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3.0E7','01123000110','05502002110','Cash At Hand','000zib','BTN18303','Gen','10002','13:57:47','0086'),(38326,'2018-12-12','Mutumba Jimmy Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502003210','Loans To Customers','0002','BTN18304','Gen','10002','13:57:48','0087'),(38327,'2018-12-12','Mutumba Jimmy Loan Disbursed Processed on 12/12/2018\n  On Mutumba Jimmy','2018-12-12','-','3000000.0','05502003210','01128000110','Mutumba Jimmy','0002','BTN18304','Gen','10002','13:57:48','0088'),(38328,'2018-12-12','Mutumba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502003210','01123000110','Mutumba Jimmy','000zib','BTN18304','Gen','10002','13:57:48','0089'),(38329,'2018-12-12','Mutumba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502003210','Cash At Hand','000zib','BTN18304','Gen','10002','13:57:48','0090'),(38330,'2018-12-12','Mwanje Fred Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3.0E7','-','01128000110','05502003510','Loans To Customers','0002','BTN18305','Gen','10002','13:57:51','0091'),(38331,'2018-12-12','Mwanje Fred Loan Disbursed Processed on 12/12/2018\n  On Mwanje Fred','2018-12-12','-','3.0E7','05502003510','01128000110','Mwanje Fred','0002','BTN18305','Gen','10002','13:57:51','0092'),(38332,'2018-12-12','Mwanje\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3.0E7','-','05502003510','01123000110','Mwanje Fred','000zib','BTN18305','Gen','10002','13:57:51','0093'),(38333,'2018-12-12','Mwanje\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3.0E7','01123000110','05502003510','Cash At Hand','000zib','BTN18305','Gen','10002','13:57:51','0094'),(38334,'2018-12-12','Mwere Paul Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502003310','Loans To Customers','0002','BTN18306','Gen','10002','13:57:53','0095'),(38335,'2018-12-12','Mwere Paul Loan Disbursed Processed on 12/12/2018\n  On Mwere Paul','2018-12-12','-','3000000.0','05502003310','01128000110','Mwere Paul','0002','BTN18306','Gen','10002','13:57:53','0096'),(38336,'2018-12-12','Mwere\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502003310','01123000110','Mwere Paul','000zib','BTN18306','Gen','10002','13:57:53','0097'),(38337,'2018-12-12','Mwere\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502003310','Cash At Hand','000zib','BTN18306','Gen','10002','13:57:53','0098'),(38338,'2018-12-12','Nsubuga Musoke James Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502000910','Loans To Customers','0002','BTN18307','Gen','10002','13:57:55','0099'),(38339,'2018-12-12','Nsubuga Musoke James Loan Disbursed Processed on 12/12/2018\n  On Nsubuga Musoke James','2018-12-12','-','3000000.0','05502000910','01128000110','Nsubuga Musoke James','0002','BTN18307','Gen','10002','13:57:55','0100'),(38340,'2018-12-12','Nsubuga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502000910','01123000110','Nsubuga Musoke James','000zib','BTN18307','Gen','10002','13:57:55','0101'),(38341,'2018-12-12','Nsubuga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502000910','Cash At Hand','000zib','BTN18307','Gen','10002','13:57:55','0102'),(38342,'2018-12-12','Rugaba Maxime Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','1.5E7','-','01128000110','05502001610','Loans To Customers','0002','BTN18308','Gen','10002','13:57:57','0103'),(38343,'2018-12-12','Rugaba Maxime Loan Disbursed Processed on 12/12/2018\n  On Rugaba Maxime','2018-12-12','-','1.5E7','05502001610','01128000110','Rugaba Maxime','0002','BTN18308','Gen','10002','13:57:57','0104'),(38344,'2018-12-12','Rugaba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1.5E7','-','05502001610','01123000110','Rugaba Maxime','000zib','BTN18308','Gen','10002','13:57:57','0105'),(38345,'2018-12-12','Rugaba\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','1.5E7','01123000110','05502001610','Cash At Hand','000zib','BTN18308','Gen','10002','13:57:57','0106'),(38346,'2018-12-12','Senyonjo Fred Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502002210','Loans To Customers','0002','BTN18309','Gen','10002','13:57:59','0107'),(38347,'2018-12-12','Senyonjo Fred Loan Disbursed Processed on 12/12/2018\n  On Senyonjo Fred','2018-12-12','-','3000000.0','05502002210','01128000110','Senyonjo Fred','0002','BTN18309','Gen','10002','13:57:59','0108'),(38348,'2018-12-12','Senyonjo\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502002210','01123000110','Senyonjo Fred','000zib','BTN18309','Gen','10002','13:57:59','0109'),(38349,'2018-12-12','Senyonjo\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502002210','Cash At Hand','000zib','BTN18309','Gen','10002','13:57:59','0110'),(38350,'2018-12-12','Ssali Kafeero  Michael Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','6500000.0','-','01128000110','05502002910','Loans To Customers','0002','BTN18310','Gen','10002','13:58:00','0111'),(38351,'2018-12-12','Ssali Kafeero  Michael Loan Disbursed Processed on 12/12/2018\n  On Ssali Kafeero  Michael','2018-12-12','-','6500000.0','05502002910','01128000110','Ssali Kafeero  Michael','0002','BTN18310','Gen','10002','13:58:00','0112'),(38352,'2018-12-12','Ssali\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','6500000.0','-','05502002910','01123000110','Ssali Kafeero  Michael','000zib','BTN18310','Gen','10002','13:58:00','0113'),(38353,'2018-12-12','Ssali\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','6500000.0','01123000110','05502002910','Cash At Hand','000zib','BTN18310','Gen','10002','13:58:00','0114'),(38354,'2018-12-12','Ssebana Kizito Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','4.5E7','-','01128000110','05502000610','Loans To Customers','0002','BTN18311','Gen','10002','13:58:03','0115'),(38355,'2018-12-12','Ssebana Kizito Loan Disbursed Processed on 12/12/2018\n  On Ssebana Kizito','2018-12-12','-','4.5E7','05502000610','01128000110','Ssebana Kizito','0002','BTN18311','Gen','10002','13:58:03','0116'),(38356,'2018-12-12','Ssebana\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','4.5E7','-','05502000610','01123000110','Ssebana Kizito','000zib','BTN18311','Gen','10002','13:58:03','0117'),(38357,'2018-12-12','Ssebana\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','4.5E7','01123000110','05502000610','Cash At Hand','000zib','BTN18311','Gen','10002','13:58:03','0118'),(38358,'2018-12-12','Sserujongi Derrick Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502003010','Loans To Customers','0002','BTN18312','Gen','10002','13:58:05','0119'),(38359,'2018-12-12','Sserujongi Derrick Loan Disbursed Processed on 12/12/2018\n  On Sserujongi Derrick','2018-12-12','-','3000000.0','05502003010','01128000110','Sserujongi Derrick','0002','BTN18312','Gen','10002','13:58:05','0120'),(38360,'2018-12-12','Sserujongi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502003010','01123000110','Sserujongi Derrick','000zib','BTN18312','Gen','10002','13:58:05','0121'),(38361,'2018-12-12','Sserujongi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502003010','Cash At Hand','000zib','BTN18312','Gen','10002','13:58:05','0122'),(38362,'2018-12-12','Ssewakilyanga Brian Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','6500000.0','-','01128000110','05502001910','Loans To Customers','0002','BTN18313','Gen','10002','13:58:07','0123'),(38363,'2018-12-12','Ssewakilyanga Brian Loan Disbursed Processed on 12/12/2018\n  On Ssewakilyanga Brian','2018-12-12','-','6500000.0','05502001910','01128000110','Ssewakilyanga Brian','0002','BTN18313','Gen','10002','13:58:07','0124'),(38364,'2018-12-12','Ssewakilyanga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','6500000.0','-','05502001910','01123000110','Ssewakilyanga Brian','000zib','BTN18313','Gen','10002','13:58:07','0125'),(38365,'2018-12-12','Ssewakilyanga\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','6500000.0','01123000110','05502001910','Cash At Hand','000zib','BTN18313','Gen','10002','13:58:07','0126'),(38366,'2018-12-12','Tegule Fred Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','1.5E7','-','01128000110','05502002610','Loans To Customers','0002','BTN18314','Gen','10002','13:58:08','0127'),(38367,'2018-12-12','Tegule Fred Loan Disbursed Processed on 12/12/2018\n  On Tegule Fred','2018-12-12','-','1.5E7','05502002610','01128000110','Tegule Fred','0002','BTN18314','Gen','10002','13:58:08','0128'),(38368,'2018-12-12','Tegule\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','1.5E7','-','05502002610','01123000110','Tegule Fred','000zib','BTN18314','Gen','10002','13:58:08','0129'),(38369,'2018-12-12','Tegule\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','1.5E7','01123000110','05502002610','Cash At Hand','000zib','BTN18314','Gen','10002','13:58:08','0130'),(38370,'2018-12-12','Tenywa Joseph Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','7000000.0','-','01128000110','05502002310','Loans To Customers','0002','BTN18315','Gen','10002','13:58:10','0131'),(38371,'2018-12-12','Tenywa Joseph Loan Disbursed Processed on 12/12/2018\n  On Tenywa Joseph','2018-12-12','-','7000000.0','05502002310','01128000110','Tenywa Joseph','0002','BTN18315','Gen','10002','13:58:10','0132'),(38372,'2018-12-12','Tenywa\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','7000000.0','-','05502002310','01123000110','Tenywa Joseph','000zib','BTN18315','Gen','10002','13:58:10','0133'),(38373,'2018-12-12','Tenywa\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','7000000.0','01123000110','05502002310','Cash At Hand','000zib','BTN18315','Gen','10002','13:58:10','0134'),(38374,'2018-12-12','Wakabi Walter Wales Loan Disbursed Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3.0E7','-','01128000110','05502001410','Loans To Customers','0002','BTN18316','Gen','10002','13:58:12','0135'),(38375,'2018-12-12','Wakabi Walter Wales Loan Disbursed Processed on 12/12/2018\n  On Wakabi Walter Wales','2018-12-12','-','3.0E7','05502001410','01128000110','Wakabi Walter Wales','0002','BTN18316','Gen','10002','13:58:12','0136'),(38376,'2018-12-12','Wakabi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3.0E7','-','05502001410','01123000110','Wakabi Walter Wales','000zib','BTN18316','Gen','10002','13:58:12','0137'),(38377,'2018-12-12','Wakabi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3.0E7','01123000110','05502001410','Cash At Hand','000zib','BTN18316','Gen','10002','13:58:12','0138'),(38378,'2018-12-12','Ssozi Joram\'s loan disbursement Processed on 12/12/2018\n  From Loans To Customers','2018-12-12','3000000.0','-','01128000110','05502003710','Loans To Customers','0002','BTN18317','Gen','10002','14:33:53','0139'),(38379,'2018-12-12','Ssozi Joram\'s loan disbursement Processed on 12/12/2018\n  On Ssozi Joram','2018-12-12','-','3000000.0','05502003710','01128000110','Ssozi Joram','0002','BTN18317','Gen','10002','14:33:53','0140'),(38380,'2018-12-12','Ssozi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','3000000.0','-','05502003710','01123000110','Ssozi Joram','000zib','BTN18317','Gen','10002','14:33:53','0141'),(38381,'2018-12-12','Ssozi\'s loan Cashed out\n  Dated 12/12/2018','2018-12-12','-','3000000.0','01123000110','05502003710','Cash At Hand','000zib','BTN18317','Gen','10002','14:33:53','0142'),(38382,'2018-12-13','Nsubuga\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','900000.0','-','01123000110','05502000910','Cash At Hand','000zib','BTN18318','LoanR','10001','08:23:01','0004'),(38383,'2018-12-13','Nsubuga\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','900000.0','05502000910','01123000110','Nsubuga Musoke James','000zib','BTN18318','LoanR','10001','08:23:01','0005'),(38384,'2018-12-13','Nsubuga Musoke James\'s Interest PaymentDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','900000.0','-','05502000910','01128000110','Nsubuga Musoke James','0002','BTN18318','LoanR','10001','08:23:01','0002'),(38385,'2018-12-13','Interest from Nsubuga Musoke James\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','900000.0','03301000110','05502000910','Gross Interest Income1','asms0001','BTN18318','LoanR','10001','08:23:01','0006'),(38386,'2018-12-13','Kyasa\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','3900000.0','-','01123000110','05502002510','Cash At Hand','000zib','BTN18319','LoanR','10001','08:25:41','0009'),(38387,'2018-12-13','Kyasa\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','3900000.0','05502002510','01123000110','Kyasa Francis Xavier','000zib','BTN18319','LoanR','10001','08:25:41','0010'),(38388,'2018-12-13','Kyasa Francis Xavier\'s LOAN CLOSEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','3900000.0','-','05502002510','01128000110','Kyasa Francis Xavier','0002','BTN18319','LoanR','10001','08:25:41','0007'),(38389,'2018-12-13','Interest from Kyasa Francis Xavier\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','2700000.0','03301000110','05502002510','Gross Interest Income1','asms0001','BTN18319','LoanR','10001','08:25:41','0011'),(38390,'2018-12-13','Loan Payment from Kyasa Francis Xavier\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','1200000.0','01128000110','05502002510','Loans To Customers','asms0001','BTN18319','LoanR','10001','08:25:41','0012'),(38391,'2018-12-13','Kazibwe\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','2600000.0','-','01123000110','05502002710','Cash At Hand','000zib','BTN18320','LoanR','10001','08:26:55','0015'),(38392,'2018-12-13','Kazibwe\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','2600000.0','05502002710','01123000110','Kazibwe Eliam','000zib','BTN18320','LoanR','10001','08:26:55','0016'),(38393,'2018-12-13','Kazibwe Eliam\'s LOAN CLOSEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','2600000.0','-','05502002710','01128000110','Kazibwe Eliam','0002','BTN18320','LoanR','10001','08:26:55','0013'),(38394,'2018-12-13','Interest from Kazibwe Eliam\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','1800000.0','03301000110','05502002710','Gross Interest Income1','asms0001','BTN18320','LoanR','10001','08:26:55','0017'),(38395,'2018-12-13','Loan Payment from Kazibwe Eliam\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','800000.0','01128000110','05502002710','Loans To Customers','asms0001','BTN18320','LoanR','10001','08:26:55','0018'),(38396,'2018-12-13','Kazibwe\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','222222.0','-','01123000110','05502002710','Cash At Hand','000zib','BTN18321','LoanR','10001','08:27:47','0021'),(38397,'2018-12-13','Kazibwe\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','222222.0','05502002710','01123000110','Kazibwe Eliam','000zib','BTN18321','LoanR','10001','08:27:47','0022'),(38398,'2018-12-13','Kazibwe Eliam\'s Principal and Interest PaymentDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','222222.0','-','05502002710','01128000110','Kazibwe Eliam','0002','BTN18321','LoanR','10001','08:27:47','0019'),(38399,'2018-12-13','Loan Payment from Kazibwe Eliam\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','222222.0','01128000110','05502002710','Loans To Customers','asms0001','BTN18321','LoanR','10001','08:27:47','0023'),(38400,'2018-12-13','Bazirake Augustine\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','3000000.0','-','01128000110','05502003810','Loans To Customers','0002','BTN18322','Gen','10001','08:29:29','0024'),(38401,'2018-12-13','Bazirake Augustine\'s loan disbursement Processed on 13/12/2018\n  On Bazirake Augustine','2018-12-13','-','3000000.0','05502003810','01128000110','Bazirake Augustine','0002','BTN18322','Gen','10001','08:29:29','0025'),(38402,'2018-12-13','Bazirake\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','05502003810','01123000110','Bazirake Augustine','000zib','BTN18322','Gen','10001','08:29:29','0026'),(38403,'2018-12-13','Bazirake\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','3000000.0','01123000110','05502003810','Cash At Hand','000zib','BTN18322','Gen','10001','08:29:29','0027'),(38404,'2018-12-13','Bazirake\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','3900000.0','-','01123000110','05502003810','Cash At Hand','000zib','BTN18323','LoanR','10001','08:30:49','0030'),(38405,'2018-12-13','Bazirake\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','3900000.0','05502003810','01123000110','Bazirake Augustine','000zib','BTN18323','LoanR','10001','08:30:49','0031'),(38406,'2018-12-13','Bazirake Augustine\'s LOAN COMPLETEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','3900000.0','-','05502003810','01128000110','Bazirake Augustine','0002','BTN18323','LoanR','10001','08:30:49','0028'),(38407,'2018-12-13','Interest from Bazirake Augustine\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','900000.0','03301000110','05502003810','Gross Interest Income1','asms0001','BTN18323','LoanR','10001','08:30:49','0032'),(38408,'2018-12-13','Loan Payment from Bazirake Augustine\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','3000000.0','01128000110','05502003810','Loans To Customers','asms0001','BTN18323','LoanR','10001','08:30:49','0033'),(38409,'2018-12-13','Terisa Mariah\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','3000000.0','-','01128000110','05502003910','Loans To Customers','0002','BTN18324','Gen','10001','08:37:44','0034'),(38410,'2018-12-13','Terisa Mariah\'s loan disbursement Processed on 13/12/2018\n  On Terisa Mariah','2018-12-13','-','3000000.0','05502003910','01128000110','Terisa Mariah','0002','BTN18324','Gen','10001','08:37:44','0035'),(38411,'2018-12-13','Terisa\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','05502003910','01123000110','Terisa Mariah','000zib','BTN18324','Gen','10001','08:37:44','0036'),(38412,'2018-12-13','Terisa\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','3000000.0','01123000110','05502003910','Cash At Hand','000zib','BTN18324','Gen','10001','08:37:44','0037'),(38413,'2018-12-13','Terisa\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','300000.0','-','01122000110','05502003910','DFCU Bank','000zib','BTN18325','LoanR','10001','08:38:50','0040'),(38414,'2018-12-13','Terisa\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','300000.0','05502003910','01122000110','Terisa Mariah','000zib','BTN18325','LoanR','10001','08:38:50','0041'),(38415,'2018-12-13','Terisa Mariah\'s Principal and Interest PaymentDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','300000.0','-','05502003910','01128000110','Terisa Mariah','0002','BTN18325','LoanR','10001','08:38:50','0038'),(38416,'2018-12-13','Interest from Terisa Mariah\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','300000.0','03301000110','05502003910','Gross Interest Income1','asms0001','BTN18325','LoanR','10001','08:38:50','0042'),(38417,'2018-12-13','Nanyonga Sandra\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','3000000.0','-','01128000110','05502000110','Loans To Customers','0002','BTN18326','Gen','10001','10:13:47','0043'),(38418,'2018-12-13','Nanyonga Sandra\'s loan disbursement Processed on 13/12/2018\n  On Nanyonga Sandra','2018-12-13','-','3000000.0','05502000110','01128000110','Nanyonga Sandra','0002','BTN18326','Gen','10001','10:13:47','0044'),(38419,'2018-12-13','Nanyonga\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','05502000110','01123000110','Nanyonga Sandra','000zib','BTN18326','Gen','10001','10:13:47','0045'),(38420,'2018-12-13','Nanyonga\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','3000000.0','01123000110','05502000110','Cash At Hand','000zib','BTN18326','Gen','10001','10:13:47','0046'),(38421,'2018-12-13','Kalibala Francis\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','5000000.0','-','01128000110','05502000310','Loans To Customers','0002','BTN18327','Gen','10001','10:35:28','0047'),(38422,'2018-12-13','Kalibala Francis\'s loan disbursement Processed on 13/12/2018\n  On Kalibala Francis','2018-12-13','-','5000000.0','05502000310','01128000110','Kalibala Francis','0002','BTN18327','Gen','10001','10:35:28','0048'),(38423,'2018-12-13','Kalibala\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','05502000310','01123000110','Kalibala Francis','000zib','BTN18327','Gen','10001','10:35:28','0049'),(38424,'2018-12-13','Kalibala\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','5000000.0','01123000110','05502000310','Cash At Hand','000zib','BTN18327','Gen','10001','10:35:28','0050'),(38425,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','5000000.0','-','01128000110','05502004010','Loans To Customers','0002','BTN18328','Gen','10001','11:33:44','0051'),(38426,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  On Gilbert Mukama','2018-12-13','-','5000000.0','05502004010','01128000110','Gilbert Mukama','0002','BTN18328','Gen','10001','11:33:44','0052'),(38427,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','05502004010','01123000110','Gilbert Mukama','000zib','BTN18328','Gen','10001','11:33:44','0053'),(38428,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','5000000.0','01123000110','05502004010','Cash At Hand','000zib','BTN18328','Gen','10001','11:33:44','0054'),(38429,'2018-12-13','Namatovu Josephine\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','5000000.0','-','01128000110','05502000210','Loans To Customers','0002','BTN18329','Gen','10001','11:54:59','0055'),(38430,'2018-12-13','Namatovu Josephine\'s loan disbursement Processed on 13/12/2018\n  On Namatovu Josephine','2018-12-13','-','5000000.0','05502000210','01128000110','Namatovu Josephine','0002','BTN18329','Gen','10001','11:54:59','0056'),(38431,'2018-12-13','Namatovu\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','05502000210','01123000110','Namatovu Josephine','000zib','BTN18329','Gen','10001','11:54:59','0057'),(38432,'2018-12-13','Namatovu\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','5000000.0','01123000110','05502000210','Cash At Hand','000zib','BTN18329','Gen','10001','11:54:59','0058'),(38433,'2018-12-13','Kalibbala, Matovu  Francis/William\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','1000000.0','-','01128000110','05502000410','Loans To Customers','0002','BTN18330','Gen','10001','13:01:52','0059'),(38434,'2018-12-13','Kalibbala, Matovu  Francis/William\'s loan disbursement Processed on 13/12/2018\n  On Kalibbala, Matovu  Francis/William','2018-12-13','-','1000000.0','05502000410','01128000110','Kalibbala, Matovu  Francis/Wil','0002','BTN18330','Gen','10001','13:01:52','0060'),(38435,'2018-12-13','Kalibbala,\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','1000000.0','-','05502000410','01123000110','Kalibbala, Matovu  Francis/Wil','000zib','BTN18330','Gen','10001','13:01:52','0061'),(38436,'2018-12-13','Kalibbala,\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','1000000.0','01123000110','05502000410','Cash At Hand','000zib','BTN18330','Gen','10001','13:01:52','0062'),(38437,'2018-12-13','Kutosi Francis Mabonga\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','3000000.0','-','01128000110','05502000510','Loans To Customers','0002','BTN18331','Gen','10001','13:09:07','0063'),(38438,'2018-12-13','Kutosi Francis Mabonga\'s loan disbursement Processed on 13/12/2018\n  On Kutosi Francis Mabonga','2018-12-13','-','3000000.0','05502000510','01128000110','Kutosi Francis Mabonga','0002','BTN18331','Gen','10001','13:09:07','0064'),(38439,'2018-12-13','Kutosi\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','05502000510','01123000110','Kutosi Francis Mabonga','000zib','BTN18331','Gen','10001','13:09:07','0065'),(38440,'2018-12-13','Kutosi\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','3000000.0','01123000110','05502000510','Cash At Hand','000zib','BTN18331','Gen','10001','13:09:07','0066'),(38441,'2018-12-13','Jaggwe Ronald\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','3000000.0','-','01128000110','05502000710','Loans To Customers','0002','BTN18332','Gen','10001','13:23:21','0067'),(38442,'2018-12-13','Jaggwe Ronald\'s loan disbursement Processed on 13/12/2018\n  On Jaggwe Ronald','2018-12-13','-','3000000.0','05502000710','01128000110','Jaggwe Ronald','0002','BTN18332','Gen','10001','13:23:21','0068'),(38443,'2018-12-13','Jaggwe\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','3000000.0','-','05502000710','01123000110','Jaggwe Ronald','000zib','BTN18332','Gen','10001','13:23:21','0069'),(38444,'2018-12-13','Jaggwe\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','3000000.0','01123000110','05502000710','Cash At Hand','000zib','BTN18332','Gen','10001','13:23:21','0070'),(38445,'2018-12-13','Stationery And Printing1\'s Expense Payment Processed on 13/12/2018\n  From Stationery And Printing1','2018-12-13','600000.0','-','02243000110','01123000110','Stationery And Printing1','0002','BTN18333','Gen','10001','13:41:40','0071'),(38446,'2018-12-13','Stationery And Printing1\'s Expense Payment Processed on 13/12/2018\n  On Cash At Hand','2018-12-13','-','600000.0','01123000110','02243000110','Cash At Hand','0002','BTN18333','Gen','10001','13:41:40','0072'),(38447,'2018-12-13','Businge Rusoke  Victoria\'s Accumulated Interest  PaymentDATED 13/12/2018 Processed on 13/12/2018\n  Dated 13/12/2018','2018-12-13','1950000.0','-','01123000110','05502003410','Cash At Hand','000zib','BTN18334','Gen','10001','23:40:08','0075'),(38448,'2018-12-13','Businge Rusoke  Victoria\'s Accumulated Interest  PaymentDATED 13/12/2018 Processed on 13/12/2018\n  Dated 13/12/2018','2018-12-13','-','1950000.0','05502003410','01123000110','Businge Rusoke  Victoria','000zib','BTN18334','Gen','10001','23:40:08','0076'),(38449,'2018-12-13','Businge Rusoke  Victoria\'s Accumulated Interest  PaymentDATED 13/12/2018 Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','1950000.0','-','05502003410','03311000110','Businge Rusoke  Victoria','0002','BTN18334','Gen','10001','23:40:08','0073'),(38450,'2018-12-13','Businge Rusoke  Victoria\'s Accumulated Interest  PaymentDATED 13/12/2018 Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','-','1950000.0','03311000110','05502003410','Accumulated Interest Income1','0002','BTN18334','Gen','10001','23:40:08','0074'),(38451,'2018-12-13','Businge\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','1.235E7','-','01123000110','05502003410','Cash At Hand','000zib','BTN18334','LoanR','10001','23:40:08','0079'),(38452,'2018-12-13','Businge\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','1.235E7','05502003410','01123000110','Businge Rusoke  Victoria','000zib','BTN18334','LoanR','10001','23:40:08','0080'),(38453,'2018-12-13','Businge Rusoke  Victoria\'s LOAN COMPLETEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','1.235E7','-','05502003410','01128000110','Businge Rusoke  Victoria','0002','BTN18334','LoanR','10001','23:40:08','0077'),(38454,'2018-12-13','Interest from Businge Rusoke  Victoria\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','5850000.0','03301000110','05502003410','Gross Interest Income1','asms0001','BTN18334','LoanR','10001','23:40:08','0081'),(38455,'2018-12-13','Loan Payment from Businge Rusoke  Victoria\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','6500000.0','01128000110','05502003410','Loans To Customers','asms0001','BTN18334','LoanR','10001','23:40:08','0082'),(38456,'2018-12-13','Businge Rusoke  Victoria\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','1.235E7','-','01128000110','05502003410','Loans To Customers','0002','BTN18335','Gen','10001','23:40:52','0083'),(38457,'2018-12-13','Businge Rusoke  Victoria\'s loan disbursement Processed on 13/12/2018\n  On Businge Rusoke  Victoria','2018-12-13','-','1.235E7','05502003410','01128000110','Businge Rusoke  Victoria','0002','BTN18335','Gen','10001','23:40:52','0084'),(38458,'2018-12-13','Businge\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','1.235E7','-','05502003410','01123000110','Businge Rusoke  Victoria','000zib','BTN18335','Gen','10001','23:40:52','0085'),(38459,'2018-12-13','Businge\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','1.235E7','01123000110','05502003410','Cash At Hand','000zib','BTN18335','Gen','10001','23:40:52','0086'),(38460,'2018-12-13','Businge\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','1.6055E7','-','01122000110','05502003410','DFCU Bank','000zib','BTN18336','LoanR','10001','23:41:39','0089'),(38461,'2018-12-13','Businge\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','1.6055E7','05502003410','01122000110','Businge Rusoke  Victoria','000zib','BTN18336','LoanR','10001','23:41:39','0090'),(38462,'2018-12-13','Businge Rusoke  Victoria\'s LOAN COMPLETEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','1.6055E7','-','05502003410','01128000110','Businge Rusoke  Victoria','0002','BTN18336','LoanR','10001','23:41:39','0087'),(38463,'2018-12-13','Interest from Businge Rusoke  Victoria\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','3705000.0','03301000110','05502003410','Gross Interest Income1','asms0001','BTN18336','LoanR','10001','23:41:39','0091'),(38464,'2018-12-13','Loan Payment from Businge Rusoke  Victoria\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','1.235E7','01128000110','05502003410','Loans To Customers','asms0001','BTN18336','LoanR','10001','23:41:39','0092'),(38465,'2018-12-13','Businge Rusoke  Victoria\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','1.6055E7','-','01128000110','05502003410','Loans To Customers','0002','BTN18337','Gen','10001','23:43:25','0093'),(38466,'2018-12-13','Businge Rusoke  Victoria\'s loan disbursement Processed on 13/12/2018\n  On Businge Rusoke  Victoria','2018-12-13','-','1.6055E7','05502003410','01128000110','Businge Rusoke  Victoria','0002','BTN18337','Gen','10001','23:43:25','0094'),(38467,'2018-12-13','Businge\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','1.6055E7','-','05502003410','01122000110','Businge Rusoke  Victoria','000zib','BTN18337','Gen','10001','23:43:25','0095'),(38468,'2018-12-13','Businge\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','1.6055E7','01122000110','05502003410','DFCU Bank','000zib','BTN18337','Gen','10001','23:43:25','0096'),(38469,'2018-12-13','Loan disbursed  Ssejjemba Emmanuel\n  Disbursed on  13/12/2018','2018-12-13','1.0E8','-','01128000110','05502002810','Loans To Customers','asms0001','BTN18339','00025','10001','06:18:38','0097'),(38470,'2018-12-13','Loan disbursed to you\n  Disbursed on 13/12/2018','2018-12-13','-','1.0E8','05502002810','01128000110','Ssejjemba Emmanuel','asms0001','BTN18339','00025','10001','06:18:38','0098'),(38471,'2018-12-13','Ssejjemba\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','1.0E8','-','05502002810','01123000110','Ssejjemba Emmanuel','000zib','BTN18339','00025','10001','06:18:38','0099'),(38472,'2018-12-13','Ssejjemba\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','1.0E8','01123000110','05502002810','Cash At Hand','000zib','BTN18339','00025','10001','06:18:38','0100'),(38473,'2018-12-13','Ssejjemba\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','1.3E8','-','01123000110','05502002810','Cash At Hand','000zib','BTN18340','LoanR','10001','06:19:39','0103'),(38474,'2018-12-13','Ssejjemba\'s Savings for Loan Payment\n  Dated 13/12/2018','2018-12-13','-','1.3E8','05502002810','01123000110','Ssejjemba Emmanuel','000zib','BTN18340','LoanR','10001','06:19:39','0104'),(38475,'2018-12-13','Ssejjemba Emmanuel\'s LOAN COMPLETEDDATED 13/12/2018\n  LOAN PAYMENT','2018-12-13','1.3E8','-','05502002810','01128000110','Ssejjemba Emmanuel','0002','BTN18340','LoanR','10001','06:19:39','0101'),(38476,'2018-12-13','Interest from Ssejjemba Emmanuel\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','3.0E7','03301000110','05502002810','Gross Interest Income1','asms0001','BTN18340','LoanR','10001','06:19:39','0105'),(38477,'2018-12-13','Loan Payment from Ssejjemba Emmanuel\'s   loan repayment.\n  Paid on 13/12/2018','2018-12-13','-','1.0E8','01128000110','05502002810','Loans To Customers','asms0001','BTN18340','LoanR','10001','06:19:39','0106'),(38478,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  From Loans To Customers','2018-12-13','5000000.0','-','01128000110','05502004010','Loans To Customers','0002','BTN18341','Gen','10001','08:45:02','0107'),(38479,'2018-12-13','Gilbert Mukama\'s loan disbursement Processed on 13/12/2018\n  On Gilbert Mukama','2018-12-13','-','5000000.0','05502004010','01128000110','Gilbert Mukama','0002','BTN18341','Gen','10001','08:45:02','0108'),(38480,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','5000000.0','-','05502004010','01123000110','Gilbert Mukama','000zib','BTN18341','Gen','10001','08:45:02','0109'),(38481,'2018-12-13','Gilbert\'s loan Cashed out\n  Dated 13/12/2018','2018-12-13','-','5000000.0','01123000110','05502004010','Cash At Hand','000zib','BTN18341','Gen','10001','08:45:02','0110');
/*!40000 ALTER TABLE `general_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ledgerproduct`
--

DROP TABLE IF EXISTS `ledgerproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ledgerproduct` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `ProductName` varchar(100) NOT NULL DEFAULT 'Toto Account',
  `ProductCode` int(11) NOT NULL DEFAULT '99',
  `MinmumLedgerBalance` double NOT NULL DEFAULT '10000',
  `MinmumBalanceForWithdraw` double NOT NULL DEFAULT '10000',
  `MinmumBalanceToBorrow` double NOT NULL DEFAULT '10000',
  `MinmumMonthForWithdraw` int(11) NOT NULL DEFAULT '4',
  `MinmumMonthBorrow` int(11) NOT NULL DEFAULT '6',
  `InterestGainedAmount` double NOT NULL DEFAULT '1000',
  `InterestGainedPercent` int(11) NOT NULL DEFAULT '3',
  `FrequencyOfInterstGain` varchar(50) NOT NULL DEFAULT 'Monthly',
  `InterestChargedAmount` double NOT NULL DEFAULT '10000',
  `InterestChargedPercent` int(11) NOT NULL DEFAULT '3',
  `FrequencyOfInterstCharged` varchar(50) NOT NULL DEFAULT 'Monthly',
  `OtherProductDetails1` varchar(50) NOT NULL DEFAULT 'N/A',
  `OtherProductDetails2` varchar(50) NOT NULL DEFAULT 'N/A',
  `OtherProductDetails3` varchar(50) NOT NULL DEFAULT 'N/A',
  `OtherProductDetails4` varchar(50) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `ProductCode` (`ProductCode`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ledgerproduct`
--

LOCK TABLES `ledgerproduct` WRITE;
/*!40000 ALTER TABLE `ledgerproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `ledgerproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_savings_shares`
--

DROP TABLE IF EXISTS `loan_savings_shares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan_savings_shares` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date NOT NULL DEFAULT '1970-01-01',
  `account_number` varchar(50) NOT NULL DEFAULT '05502000110',
  `account_name` varchar(200) NOT NULL DEFAULT 'Augustine Googo',
  `savings` varchar(50) NOT NULL DEFAULT '0.0',
  `cashWithdraws` varchar(50) NOT NULL DEFAULT '0.0',
  `shares` varchar(50) NOT NULL DEFAULT '0.0',
  `reduceShares` varchar(50) NOT NULL DEFAULT '0.0',
  `loanDisbursements` varchar(50) NOT NULL DEFAULT '0.0',
  `loan_amount` varchar(50) NOT NULL DEFAULT '0.0',
  `interestPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `principalPaid` varchar(50) NOT NULL DEFAULT '0.0',
  `otherCharges` varchar(50) NOT NULL DEFAULT '0.0',
  `monthNow` varchar(50) NOT NULL DEFAULT 'January',
  `yearNow` varchar(50) NOT NULL DEFAULT '1970',
  `MonthYearNumber` int(11) NOT NULL DEFAULT '0',
  `creation_user_id` varchar(50) NOT NULL DEFAULT '10000',
  `last_date` date NOT NULL DEFAULT '1970-01-01',
  `last_time` time NOT NULL DEFAULT '00:00:00',
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_savings_shares`
--

LOCK TABLES `loan_savings_shares` WRITE;
/*!40000 ALTER TABLE `loan_savings_shares` DISABLE KEYS */;
INSERT INTO `loan_savings_shares` VALUES (2832,'2018-12-12','05502000110','Nanyonga Sandra','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'googobazimariaterisabazirake2015','2018-12-13','10:13:47'),(2833,'2018-12-12','05502000210','Namatovu Josephine','0.0','0.0','0.0','0.0','5000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'googobazimariaterisabazirake2015','2018-12-13','11:54:59'),(2834,'2018-12-12','05502000310','Kalibala Francis','0.0','0.0','0.0','0.0','5000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'googobazimariaterisabazirake2015','2018-12-13','10:35:28'),(2835,'2018-12-12','05502000410','Kalibbala, Matovu  Francis/William','0.0','0.0','0.0','0.0','1000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','13:01:52'),(2836,'2018-12-12','05502000510','Kutosi Francis Mabonga','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','13:09:07'),(2837,'2018-12-12','05502000610','Ssebana Kizito','0.0','0.0','0.0','0.0','45000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:03'),(2838,'2018-12-12','05502000710','Jaggwe Ronald','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','13:23:21'),(2839,'2018-12-12','05502000810','Nsubuga Ronald','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','11:20:55'),(2840,'2018-12-12','05502000910','Nsubuga Musoke James','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','08:23:01'),(2841,'2018-12-12','05502001010','Kalibbala Vicent','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','11:20:57'),(2842,'2018-12-12','05502001110','Mulindwa Sam','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','11:20:58'),(2843,'2018-12-12','05502001210','Magembe Nansubuga  Beatrice','0.0','0.0','0.0','0.0','55000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:45'),(2844,'2018-12-12','05502001310','Bazira Dan','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','11:21:00'),(2845,'2018-12-12','05502001410','Wakabi Walter Wales','0.0','0.0','0.0','0.0','30000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:12'),(2846,'2018-12-12','05502001510','Kalanda Umar','0.0','0.0','0.0','0.0','10500000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:29'),(2847,'2018-12-12','05502001610','Rugaba Maxime','0.0','0.0','0.0','0.0','15000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:57'),(2848,'2018-12-12','05502001710','Kalisa John','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','11:21:04'),(2849,'2018-12-12','05502001810','Kateregga John','0.0','0.0','0.0','0.0','13000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:33'),(2850,'2018-12-12','05502001910','Ssewakilyanga Brian','0.0','0.0','0.0','0.0','6500000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:07'),(2851,'2018-12-12','05502002010','Kalumba Joyce','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:31'),(2852,'2018-12-12','05502002110','Murungi Lewis Lennox','0.0','0.0','0.0','0.0','30000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:47'),(2853,'2018-12-12','05502002210','Senyonjo Fred','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:59'),(2854,'2018-12-12','05502002310','Tenywa Joseph','0.0','0.0','0.0','0.0','7000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:10'),(2855,'2018-12-12','05502002410','Lumala Mark William','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:43'),(2856,'2018-12-12','05502002510','Kyasa Francis Xavier','0.0','0.0','0.0','0.0','3000000.0','3900000.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','08:25:41'),(2857,'2018-12-12','05502002610','Tegule Fred','0.0','0.0','0.0','0.0','15000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:08'),(2858,'2018-12-12','05502002710','Kazibwe Eliam','0.0','0.0','0.0','0.0','2000000.0','2822222.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','08:27:47'),(2859,'2018-12-12','05502002810','Ssejjemba Emmanuel','0.0','0.0','0.0','0.0','100000000','130000000','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','06:19:39'),(2860,'2018-12-12','05502002910','Ssali Kafeero  Michael','0.0','0.0','0.0','0.0','6500000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:00'),(2861,'2018-12-12','05502003010','Sserujongi Derrick','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:58:05'),(2862,'2018-12-12','05502003110','Katula James','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:35'),(2863,'2018-12-12','05502003210','Mutumba Jimmy','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:48'),(2864,'2018-12-12','05502003310','Mwere Paul','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:53'),(2865,'2018-12-12','05502003410','Businge Rusoke  Victoria','0.0','0.0','0.0','0.0','34905000','28405000','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-13','23:43:25'),(2866,'2018-12-12','05502003510','Mwanje Fred','0.0','0.0','0.0','0.0','30000000','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:51'),(2867,'2018-12-12','05502003610','Luggya Habib','0.0','0.0','0.0','0.0','1500000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','13:57:41'),(2868,'2018-12-12','05502003710','Ssozi Joram','0.0','0.0','0.0','0.0','3000000.0','0.0','0.0','0.0','0.0','December','2018',2029,'10002','2018-12-12','14:33:53'),(2869,'2018-12-13','05502003810','Bazirake Augustine','0.0','0.0','0.0','0.0','3000000.0','3900000.0','0.0','0.0','0.0','December','2018',2029,'10001','2018-12-13','08:30:49'),(2870,'2018-12-13','05502003910','Terisa Mariah','0.0','0.0','0.0','0.0','3000000.0','300000.0','0.0','0.0','0.0','December','2018',2029,'10001','2018-12-13','08:38:50'),(2871,'2018-12-13','05502004010','Gilbert Mukama','0.0','0.0','0.0','0.0','10000000','0.0','0.0','0.0','0.0','December','2018',2029,'10001','2018-12-13','08:45:02');
/*!40000 ALTER TABLE `loan_savings_shares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loandisburserepaystatement`
--

DROP TABLE IF EXISTS `loandisburserepaystatement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loandisburserepaystatement` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date DEFAULT '1970-01-01',
  `MonthPaid` varchar(100) DEFAULT 'January',
  `YearPaid` varchar(100) DEFAULT '2017',
  `loanTrnId` varchar(100) DEFAULT 'NL001',
  `LoanId` varchar(100) DEFAULT 'newloan05502000110',
  `AccountNumber` varchar(100) DEFAULT 'newloan05502000110',
  `BatchCode` varchar(100) DEFAULT 'B002',
  `AmountDisbursed` varchar(100) DEFAULT '0.0',
  `ExpectedInterest` varchar(100) DEFAULT '0.0',
  `ExpectedTotalAmount` varchar(100) DEFAULT '0.0',
  `InterestRate` varchar(100) DEFAULT '0',
  `AmountPaid` varchar(100) DEFAULT '0.0',
  `PrincipalPaid` varchar(100) DEFAULT '0.0',
  `InterestPaid` varchar(100) DEFAULT '0.0',
  `AccumulatedInterestPaid` varchar(100) DEFAULT '0.0',
  `LoanPenaltyPaid` varchar(100) DEFAULT '0.0',
  `PrincipalBalance` varchar(100) DEFAULT '0.0',
  `InterestBalance` varchar(100) DEFAULT '0.0',
  `AccumulatedInterestBalance` varchar(100) DEFAULT '0.0',
  `LoanPenaltyBalance` varchar(100) DEFAULT '0.0',
  `LoanBalance` varchar(100) DEFAULT '0.0',
  `LoanStatusReport` varchar(100) DEFAULT 'Running',
  `OtherOne` varchar(100) DEFAULT 'NA',
  `OtherTwo` varchar(100) DEFAULT 'NA',
  `OtherThree` varchar(100) DEFAULT 'NA',
  `OtherFour` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loandisburserepaystatement`
--

LOCK TABLES `loandisburserepaystatement` WRITE;
/*!40000 ALTER TABLE `loandisburserepaystatement` DISABLE KEYS */;
INSERT INTO `loandisburserepaystatement` VALUES (2075,'2018-12-12','','','NL0022','closedloan105502003410','05502003410','BTN18293','6500000.0','1950000.0','8450000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','6500000.0','1950000.0','0.0','0.0','8450000.0','Completed','NA','NA','NA','NA'),(2076,'2018-12-12','','','NL0005','newloan05502001510','05502001510','BTN18294','1.05E7','3150000.0','1.365E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','1.05E7','3150000.0','0.0','0.0','1.365E7','Running','NA','NA','NA','NA'),(2077,'2018-12-12','','','NL0009','newloan05502002010','05502002010','BTN18295','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2078,'2018-12-12','','','NL0007','newloan05502001810','05502001810','BTN18296','1.3E7','3900000.0','1.69E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','1.3E7','3900000.0','0.0','0.0','1.69E7','Running','NA','NA','NA','NA'),(2079,'2018-12-12','','','NL0019','newloan05502003110','05502003110','BTN18297','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2080,'2018-12-12','','','NL0016','newloan05502002710','05502002710','BTN18298','2000000.0','600000.0','2600000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','2000000.0','600000.0','0.0','0.0','2600000.0','Running','NA','NA','NA','NA'),(2081,'2018-12-12','','','NL0014','newloan05502002510','05502002510','BTN18299','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2082,'2018-12-12','','','NL0024','newloan05502003610','05502003610','BTN18300','1500000.0','450000.0','1950000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','1500000.0','450000.0','0.0','0.0','1950000.0','Running','NA','NA','NA','NA'),(2083,'2018-12-12','','','NL0013','newloan05502002410','05502002410','BTN18301','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2084,'2018-12-12','','','NL0003','newloan05502001210','05502001210','BTN18302','5.5E7','8250000.0','6.325E7','60.00000000000001','0.0','0.0','0.0','0.0','0.0','5.5E7','8250000.0','0.0','0.0','6.325E7','Running','NA','NA','NA','NA'),(2085,'2018-12-12','','','NL0010','newloan05502002110','05502002110','BTN18303','3.0E7','9000000.0','3.9E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3.0E7','9000000.0','0.0','0.0','3.9E7','Running','NA','NA','NA','NA'),(2086,'2018-12-12','','','NL0020','newloan05502003210','05502003210','BTN18304','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2087,'2018-12-12','','','NL0023','newloan05502003510','05502003510','BTN18305','3.0E7','9000000.0','3.9E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3.0E7','9000000.0','0.0','0.0','3.9E7','Running','NA','NA','NA','NA'),(2088,'2018-12-12','','','NL0021','newloan05502003310','05502003310','BTN18306','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2089,'2018-12-12','','','NL0002','newloan05502000910','05502000910','BTN18307','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2090,'2018-12-12','','','NL0006','newloan05502001610','05502001610','BTN18308','1.5E7','2250000.0','1.725E7','60.00000000000001','0.0','0.0','0.0','0.0','0.0','1.5E7','2250000.0','0.0','0.0','1.725E7','Running','NA','NA','NA','NA'),(2091,'2018-12-12','','','NL0011','newloan05502002210','05502002210','BTN18309','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2092,'2018-12-12','','','NL0017','newloan05502002910','05502002910','BTN18310','6500000.0','975000.0','7475000.0','60.00000000000001','0.0','0.0','0.0','0.0','0.0','6500000.0','975000.0','0.0','0.0','7475000.0','Running','NA','NA','NA','NA'),(2093,'2018-12-12','','','NL0001','newloan05502000610','05502000610','BTN18311','4.5E7','1.35E7','5.85E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','4.5E7','1.35E7','0.0','0.0','5.85E7','Running','NA','NA','NA','NA'),(2094,'2018-12-12','','','NL0018','newloan05502003010','05502003010','BTN18312','3000000.0','900000.0','3900000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2095,'2018-12-12','','','NL0008','newloan05502001910','05502001910','BTN18313','6500000.0','975000.0','7475000.0','60.00000000000001','0.0','0.0','0.0','0.0','0.0','6500000.0','975000.0','0.0','0.0','7475000.0','Running','NA','NA','NA','NA'),(2096,'2018-12-12','','','NL0015','newloan05502002610','05502002610','BTN18314','1.5E7','4500000.0','1.95E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','1.5E7','4500000.0','0.0','0.0','1.95E7','Running','NA','NA','NA','NA'),(2097,'2018-12-12','','','NL0012','newloan05502002310','05502002310','BTN18315','7000000.0','2100000.0','9100000.0','120.00000000000001','0.0','0.0','0.0','0.0','0.0','7000000.0','2100000.0','0.0','0.0','9100000.0','Running','NA','NA','NA','NA'),(2098,'2018-12-12','','','NL0004','newloan05502001410','05502001410','BTN18316','3.0E7','9000000.0','3.9E7','120.00000000000001','0.0','0.0','0.0','0.0','0.0','3.0E7','9000000.0','0.0','0.0','3.9E7','Running','NA','NA','NA','NA'),(2099,'2018-12-12','','','NL0025','newloan05502003710','05502003710','BTN18317','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2100,'2018-12-13','DECEMBER','2018','NL0002','newloan05502000910','05502000910','BTN18318','0.0','0.0','0.0','0','900000.0','0.0','900000.0','0.0','0.0','3000000.0','0.0','0.0','0.0','3000000.0','Running','NA','NA','NA','NA'),(2101,'2018-12-13','DECEMBER','2018','NL0014','newloan05502002510','05502002510','BTN18319','0.0','0.0','0.0','0','3900000.0','1200000.0','2700000.0','0.0','0.0','1800000.0','-1800000.0','0.0','0.0','0.0','Running','NA','NA','NA','NA'),(2102,'2018-12-13','DECEMBER','2018','NL0016','newloan05502002710','05502002710','BTN18320','0.0','0.0','0.0','0','2600000.0','800000.0','1800000.0','0.0','0.0','1200000.0','-1200000.0','0.0','0.0','0.0','Running','NA','NA','NA','NA'),(2103,'2018-12-13','DECEMBER','2018','NL0016','newloan05502002710','05502002710','BTN18321','0.0','0.0','0.0','0','222222.0','222222.0','0.0','0.0','0.0','977778.0','-1200000.0','0.0','0.0','-222222.0','Running','NA','NA','NA','NA'),(2104,'2018-12-13','','','NL0026','closedloan105502003810','05502003810','BTN18322','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Completed','NA','NA','NA','NA'),(2105,'2018-12-13','DECEMBER','2018','NL0026','closedloan105502003810','05502003810','BTN18323','0.0','0.0','0.0','0','3900000.0','3000000.0','900000.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','Completed','NA','NA','NA','NA'),(2106,'2018-12-13','','','NL0027','newloan05502003910','05502003910','BTN18324','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2107,'2018-12-13','DECEMBER','2018','NL0027','newloan05502003910','05502003910','BTN18325','0.0','0.0','0.0','0','300000.0','0.0','300000.0','0.0','0.0','3000000.0','600000.0','0.0','0.0','3600000.0','Running','NA','NA','NA','NA'),(2108,'2018-12-13','','','NL0028','newloan05502000110','05502000110','BTN18326','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2109,'2018-12-13','','','NL0029','newloan05502000310','05502000310','BTN18327','5000000.0','1500000.0','6500000.0','120.0','0.0','0.0','0.0','0.0','0.0','5000000.0','1500000.0','0.0','0.0','6500000.0','Running','NA','NA','NA','NA'),(2110,'2018-12-13','','','NL0030','newloan05502004010','05502004010','BTN18328','5000000.0','1500000.0','6500000.0','120.0','0.0','0.0','0.0','0.0','0.0','5000000.0','1500000.0','0.0','0.0','6500000.0','Running','NA','NA','NA','NA'),(2111,'2018-12-13','','','NL0031','newloan05502000210','05502000210','BTN18329','5000000.0','1500000.0','6500000.0','120.0','0.0','0.0','0.0','0.0','0.0','5000000.0','1500000.0','0.0','0.0','6500000.0','Running','NA','NA','NA','NA'),(2112,'2018-12-13','','','NL0032','newloan05502000410','05502000410','BTN18330','1000000.0','300000.0','1300000.0','120.0','0.0','0.0','0.0','0.0','0.0','1000000.0','300000.0','0.0','0.0','1300000.0','Running','NA','NA','NA','NA'),(2113,'2018-12-13','','','NL0033','newloan05502000510','05502000510','BTN18331','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2114,'2018-12-13','','','NL0034','newloan05502000710','05502000710','BTN18332','3000000.0','900000.0','3900000.0','120.0','0.0','0.0','0.0','0.0','0.0','3000000.0','900000.0','0.0','0.0','3900000.0','Running','NA','NA','NA','NA'),(2115,'2018-12-13','DECEMBER','2018','NL0022','closedloan105502003410','05502003410','BTN18334','0.0','0.0','0.0','0','1.235E7','6500000.0','5850000.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','Completed','NA','NA','NA','NA'),(2116,'2018-12-13','','','NL0035','newloan05502003410','05502003410','BTN18335','1.235E7','3705000.0','1.6055E7','120.0','0.0','0.0','0.0','0.0','0.0','1.235E7','3705000.0','0.0','0.0','1.6055E7','Running','NA','NA','NA','NA'),(2117,'2018-12-13','DECEMBER','2018','NL0035','newloan05502003410','05502003410','BTN18336','0.0','0.0','0.0','0','1.6055E7','1.235E7','3705000.0','0.0','0.0','0.0','0.0','0.0','0.0','0.0','Running','NA','NA','NA','NA'),(2118,'2018-12-13','','','NL0036','newloan05502003410','05502003410','BTN18337','1.6055E7','4816500.0','2.08715E7','120.0','0.0','0.0','0.0','0.0','0.0','1.6055E7','4816500.0','0.0','0.0','2.08715E7','Running','NA','NA','NA','NA'),(2119,'2018-12-13','DECEMBER','2018','NL0040','closedloan105502002810','05502002810','BTN18340','0.0','0.0','0.0','0','1.3E8','1.0E8','3.0E7','0.0','0.0','0.0','0.0','0.0','0.0','0.0','Completed','NA','NA','NA','NA'),(2120,'2018-12-13','','','NL0045','newloan05502004010','05502004010','BTN18341','5000000.0','1500000.0','6500000.0','120.0','0.0','0.0','0.0','0.0','0.0','5000000.0','1500000.0','0.0','0.0','6500000.0','Running','NA','NA','NA','NA');
/*!40000 ALTER TABLE `loandisburserepaystatement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_in`
--

DROP TABLE IF EXISTS `log_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_in` (
  `trn_date` date DEFAULT NULL,
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_word_login` varchar(30) NOT NULL,
  `account_number` varchar(30) DEFAULT NULL,
  `account_name` varchar(30) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `first_name` varchar(60) DEFAULT NULL,
  `last_name` varchar(60) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `recruitement_date` date DEFAULT NULL,
  `line_manager` varchar(60) DEFAULT NULL,
  `former_employment` varchar(60) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  `creation_time` time DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_in`
--

LOCK TABLES `log_in` WRITE;
/*!40000 ALTER TABLE `log_in` DISABLE KEYS */;
INSERT INTO `log_in` VALUES ('2018-12-12',10000,'a','05502000310','Kalibala Francis','Supervisor','Francis','Kalibala','2018-12-12','2018-12-12','XXXX0','Student','Internee','11:14:54'),('2018-12-12',10001,'aa','05502000110','Nanyonga Sandra','Supervisor','Sandra','Nanyonga','2018-12-12','2018-12-12','Kalibala Francis','Student','Internee','11:15:07'),('2018-12-12',10002,'aa','05502000210','Namatovu Josephine','Accountant','Josephine','Namatovu','2018-12-12','2018-12-12','Kalibala Francis','Student','Internee','11:15:19'),('2018-12-12',10003,'aa','05502000310','Kalibala Francis','System Admin','Francis','Kalibala','2018-12-12','2018-12-12','Kalibala Francis','Student','Internee','11:15:30');
/*!40000 ALTER TABLE `log_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logged_in`
--

DROP TABLE IF EXISTS `logged_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logged_in` (
  `user_id` varchar(20) NOT NULL,
  `p_word` varchar(15) DEFAULT NULL,
  `log_date` date DEFAULT NULL,
  `log_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logged_in`
--

LOCK TABLES `logged_in` WRITE;
/*!40000 ALTER TABLE `logged_in` DISABLE KEYS */;
/*!40000 ALTER TABLE `logged_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master`
--

DROP TABLE IF EXISTS `master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master` (
  `TrnId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trn_date` date DEFAULT NULL,
  `account_master` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(6) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `sir_name` varchar(100) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `marital_status` varchar(50) DEFAULT NULL,
  `highest_educ_level` varchar(100) DEFAULT NULL,
  `home_parish` varchar(100) DEFAULT NULL,
  `centre` varchar(100) DEFAULT NULL,
  `hiika` varchar(100) DEFAULT NULL,
  `mobile1` varchar(100) DEFAULT NULL,
  `mobile2` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `kampala_residence` varchar(100) DEFAULT NULL,
  `occupation` varchar(100) DEFAULT NULL,
  `employer` varchar(100) DEFAULT NULL,
  `category_of_membership` varchar(100) DEFAULT NULL,
  `value_of_shares` varchar(30) DEFAULT NULL,
  `number_of_shares` varchar(30) DEFAULT NULL,
  `kin_first_name` varchar(100) DEFAULT NULL,
  `kin_sir_name` varchar(100) DEFAULT NULL,
  `kin_mobile_1` varchar(100) DEFAULT NULL,
  `kin_mobile_2` varchar(100) DEFAULT NULL,
  `kin_email` varchar(100) DEFAULT NULL,
  `notes` varchar(200) DEFAULT NULL,
  `introducing_capacity` varchar(100) DEFAULT NULL,
  `intro_first_name` varchar(100) DEFAULT NULL,
  `intro_sir_name` varchar(100) DEFAULT NULL,
  `intro_sacco_status` varchar(100) DEFAULT NULL,
  `intro_mob_1` varchar(100) DEFAULT NULL,
  `intro_mob_2` varchar(100) DEFAULT NULL,
  `intro_sacco_member_since` varchar(100) DEFAULT NULL,
  `approval_status` varchar(30) DEFAULT NULL,
  `approval_date` date DEFAULT NULL,
  `approved_by` varchar(45) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `account_name` varchar(100) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `last_updated_date` date DEFAULT NULL,
  `last_updated_time` varchar(30) DEFAULT NULL,
  `updated_approval` varchar(30) DEFAULT NULL,
  `UserPhoto` mediumblob,
  PRIMARY KEY (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=820 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master`
--

LOCK TABLES `master` WRITE;
/*!40000 ALTER TABLE `master` DISABLE KEYS */;
INSERT INTO `master` VALUES (780,'2018-12-12','5020001','Mr.','Sandra','Nanyonga','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000110','Nanyonga Sandra','11:13:43','2018-12-12','11:13:43','10001','ÿØÿÛ\0C\0'),(781,'2018-12-12','5020002','Mr.','Josephine','Namatovu','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000210','Namatovu Josephine','11:14:04','2018-12-12','11:14:04','10001','ÿØÿÛ\0C\0'),(782,'2018-12-12','5020003','Mr.','Francis','Kalibala','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000310','Kalibala Francis','11:14:26','2018-12-12','11:14:26','10001','ÿØÿÛ\0C\0'),(783,'2018-12-12','5020004','Mr.','Matovu  Francis/William','Kalibbala,','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000410','Kalibbala, Matovu  Francis/William','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(784,'2018-12-12','5020005','Mr.','Francis Mabonga','Kutosi','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000510','Kutosi Francis Mabonga','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(785,'2018-12-12','5020006','Mr.','Kizito','Ssebana','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000610','Ssebana Kizito','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(786,'2018-12-12','5020007','Mr.','Ronald','Jaggwe','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000710','Jaggwe Ronald','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(787,'2018-12-12','5020008','Mr.','Ronald','Nsubuga','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000810','Nsubuga Ronald','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(788,'2018-12-12','5020009','Mr.','Musoke James','Nsubuga','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502000910','Nsubuga Musoke James','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(789,'2018-12-12','5020010','Mr.','Vicent','Kalibbala','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001010','Kalibbala Vicent','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(790,'2018-12-12','5020011','Mr.','Sam','Mulindwa','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001110','Mulindwa Sam','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(791,'2018-12-12','5020012','Mr.','Nansubuga  Beatrice','Magembe','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001210','Magembe Nansubuga  Beatrice','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(792,'2018-12-12','5020013','Mr.','Dan','Bazira','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001310','Bazira Dan','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(793,'2018-12-12','5020014','Mr.','Walter Wales','Wakabi','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001410','Wakabi Walter Wales','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(794,'2018-12-12','5020015','Mr.','Umar','Kalanda','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001510','Kalanda Umar','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(795,'2018-12-12','5020016','Mr.','Maxime','Rugaba','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001610','Rugaba Maxime','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(796,'2018-12-12','5020017','Mr.','John','Kalisa','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001710','Kalisa John','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(797,'2018-12-12','5020018','Mr.','John','Kateregga','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001810','Kateregga John','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(798,'2018-12-12','5020019','Mr.','Brian','Ssewakilyanga','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502001910','Ssewakilyanga Brian','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(799,'2018-12-12','5020020','Mr.','Joyce','Kalumba','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002010','Kalumba Joyce','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(800,'2018-12-12','5020021','Mr.','Lewis Lennox','Murungi','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002110','Murungi Lewis Lennox','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(801,'2018-12-12','5020022','Mr.','Fred','Senyonjo','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002210','Senyonjo Fred','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(802,'2018-12-12','5020023','Mr.','Joseph','Tenywa','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002310','Tenywa Joseph','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(803,'2018-12-12','5020024','Mr.','Mark William','Lumala','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002410','Lumala Mark William','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(804,'2018-12-12','5020025','Mr.','Francis Xavier','Kyasa','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002510','Kyasa Francis Xavier','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(805,'2018-12-12','5020026','Mr.','Fred','Tegule','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002610','Tegule Fred','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(806,'2018-12-12','5020027','Mr.','Eliam','Kazibwe','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002710','Kazibwe Eliam','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(807,'2018-12-12','5020028','Mr.','Emmanuel','Ssejjemba','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002810','Ssejjemba Emmanuel','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(808,'2018-12-12','5020029','Mr.','Kafeero  Michael','Ssali','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502002910','Ssali Kafeero  Michael','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(809,'2018-12-12','5020030','Mr.','Derrick','Sserujongi','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003010','Sserujongi Derrick','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(810,'2018-12-12','5020031','Mr.','James','Katula','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003110','Katula James','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(811,'2018-12-12','5020032','Mr.','Jimmy','Mutumba','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003210','Mutumba Jimmy','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(812,'2018-12-12','5020033','Mr.','Paul','Mwere','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003310','Mwere Paul','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(813,'2018-12-12','5020034','Mr.','Rusoke  Victoria','Businge','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003410','Businge Rusoke  Victoria','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(814,'2018-12-12','5020035','Mr.','Fred','Mwanje','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003510','Mwanje Fred','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(815,'2018-12-12','5020036','Mr.','Habib','Luggya','Select','2018-12-12','Select','Select','Select','','','','','','','','','Select','0.0','0.0','','','','','','','Select','','','Select','','','12/12/2018','Select','2018-12-12','','05502003610','Luggya Habib','11:20:37','2018-12-12','11:20:37','10002','ÿØÿÛ\0C\0'),(816,'2018-12-12','5020037','','Joram','Ssozi','','2018-12-12','','','','','','','','','','','','','0.0','0','','','','','','','','','','','','','','','2018-12-12','','05502003710','Ssozi Joram','14:32:32','2018-12-12','14:32:32','10002','ÿØÿÛ\0C\0'),(817,'2018-12-13','5020038','','Augustine','Bazirake','','2018-12-13','','','','','','','','','','','','','0.0','0','','','','','','','','','','','','','','','2018-12-13','','05502003810','Bazirake Augustine','08:28:07','2018-12-13','08:28:07','10001','ÿØÿÛ\0C\0'),(818,'2018-12-13','5020039','','Mariah','Terisa','','2018-12-13','','','','','','','','','','','','','0.0','0','','','','','','','','','','','','','','','2018-12-13','','05502003910','Terisa Mariah','08:36:38','2018-12-13','08:36:38','10001','ÿØÿÛ\0C\0'),(819,'2018-12-13','5020040','','Mukama','Gilbert','','2018-12-13','','','','','','','','','','','','','0.0','0','','','','','','','','','','','','','','','2018-12-13','','05502004010','Gilbert Mukama','11:29:24','2018-12-13','11:29:24','10001','ÿØÿÛ\0C\0');
/*!40000 ALTER TABLE `master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsavingsmembers`
--

DROP TABLE IF EXISTS `newsavingsmembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newsavingsmembers` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `AccountNumber` varchar(100) DEFAULT '055020000010',
  `AccountName` varchar(100) DEFAULT 'Bazirake Augustine',
  `SavingsMonth` varchar(100) DEFAULT 'December',
  `SavingsYear` varchar(100) DEFAULT '2016',
  `SavingsAdded` varchar(100) DEFAULT '0.0',
  `SavingsRemoved` varchar(100) DEFAULT '0.0',
  `SavingsRunningBalance` varchar(100) DEFAULT '0.0',
  `OtherOne` varchar(100) DEFAULT 'NA',
  `OtherTwo` varchar(100) DEFAULT 'NA',
  `OTherThree` varchar(100) DEFAULT 'NA',
  `OtherFour` varchar(100) DEFAULT 'NA',
  `OtherFive` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newsavingsmembers`
--

LOCK TABLES `newsavingsmembers` WRITE;
/*!40000 ALTER TABLE `newsavingsmembers` DISABLE KEYS */;
/*!40000 ALTER TABLE `newsavingsmembers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `open_day`
--

DROP TABLE IF EXISTS `open_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `open_day` (
  `value_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `open_day`
--

LOCK TABLES `open_day` WRITE;
/*!40000 ALTER TABLE `open_day` DISABLE KEYS */;
/*!40000 ALTER TABLE `open_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operatingperiod`
--

DROP TABLE IF EXISTS `operatingperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operatingperiod` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `StartDate` date NOT NULL DEFAULT '1970-01-01',
  `EndDate` date NOT NULL DEFAULT '1970-01-01',
  `StatusCharge` varchar(15) DEFAULT NULL,
  `TheCharge` varchar(15) DEFAULT NULL,
  `Others3` varchar(100) DEFAULT 'N/A',
  `Others4` varchar(100) DEFAULT 'N/A',
  `Others5` varchar(100) DEFAULT 'N/A',
  `Others6` varchar(100) DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operatingperiod`
--

LOCK TABLES `operatingperiod` WRITE;
/*!40000 ALTER TABLE `operatingperiod` DISABLE KEYS */;
INSERT INTO `operatingperiod` VALUES (1,'2017-01-01','2017-01-01','0','1','NA','NA','NA','NA');
/*!40000 ALTER TABLE `operatingperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payrollemployeerewarddeductionstore`
--

DROP TABLE IF EXISTS `payrollemployeerewarddeductionstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payrollemployeerewarddeductionstore` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `ItemGroup` varchar(45) DEFAULT 'Employee Reward',
  `ItemCategory` varchar(45) DEFAULT 'Allowance',
  `ItemName` varchar(45) DEFAULT 'Basic Salary',
  `AccrualMethod` varchar(45) DEFAULT 'Rate',
  `AccrualFrequency` varchar(45) DEFAULT 'Static',
  `Exceptional` varchar(45) DEFAULT 'NO',
  `CreationalStatus` varchar(45) DEFAULT 'New',
  `UsageStatus` varchar(45) DEFAULT 'Not Used',
  `OtherOne` varchar(45) DEFAULT 'NA',
  `OtherTwo` varchar(45) DEFAULT 'NA',
  `OtherThree` varchar(45) DEFAULT 'NA',
  `OtherFour` varchar(45) DEFAULT 'NA',
  `OtherFive` varchar(45) DEFAULT 'NA',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payrollemployeerewarddeductionstore`
--

LOCK TABLES `payrollemployeerewarddeductionstore` WRITE;
/*!40000 ALTER TABLE `payrollemployeerewarddeductionstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `payrollemployeerewarddeductionstore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payrollitemsparameterforformulars`
--

DROP TABLE IF EXISTS `payrollitemsparameterforformulars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payrollitemsparameterforformulars` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TrnDate` date NOT NULL DEFAULT '1970-01-01',
  `payRollItemId` int(11) DEFAULT '1',
  `ItemName` varchar(45) DEFAULT 'Basic Salary',
  `RangeLevel` int(11) DEFAULT '1',
  `PercentageInvolved` varchar(45) DEFAULT '10',
  `LowerLimitAmount` varchar(45) DEFAULT '10',
  `UpperLimitAmount` varchar(45) DEFAULT '30',
  `UsageStatus` varchar(45) DEFAULT 'Not Used',
  `OtherOne` varchar(45) DEFAULT 'NA',
  `OtherTwo` varchar(45) DEFAULT 'NA',
  `OtherThree` varchar(45) DEFAULT 'NA',
  `OtherFour` varchar(45) DEFAULT 'NA',
  `OtherFive` varchar(45) DEFAULT 'NA',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId_UNIQUE` (`TrnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payrollitemsparameterforformulars`
--

LOCK TABLES `payrollitemsparameterforformulars` WRITE;
/*!40000 ALTER TABLE `payrollitemsparameterforformulars` DISABLE KEYS */;
/*!40000 ALTER TABLE `payrollitemsparameterforformulars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shares_run_bal`
--

DROP TABLE IF EXISTS `shares_run_bal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shares_run_bal` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` varchar(100) DEFAULT NULL,
  `account_name` varchar(100) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `narrations` varchar(100) DEFAULT NULL,
  `number_shares_contributed` varchar(100) DEFAULT NULL,
  `number_shares_removed` varchar(100) DEFAULT NULL,
  `value_shares_contributed` varchar(100) DEFAULT NULL,
  `value_shares_removed` varchar(100) DEFAULT NULL,
  `running_balance_n_shares` varchar(100) DEFAULT NULL,
  `running_balance_v_shares` varchar(100) DEFAULT NULL,
  `trn_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shares_run_bal`
--

LOCK TABLES `shares_run_bal` WRITE;
/*!40000 ALTER TABLE `shares_run_bal` DISABLE KEYS */;
/*!40000 ALTER TABLE `shares_run_bal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharesreturnoninvestment`
--

DROP TABLE IF EXISTS `sharesreturnoninvestment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sharesreturnoninvestment` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date DEFAULT '2016-12-31',
  `value_date` date DEFAULT '2016-12-31',
  `value_month` varchar(100) DEFAULT 'DECEMBER',
  `value_year` varchar(100) DEFAULT '2016',
  `account_name` varchar(100) DEFAULT 'Googo',
  `account_number` varchar(100) DEFAULT '05502000010',
  `Balance_Shares` varchar(100) DEFAULT '10000.0',
  `Daily_Return` varchar(100) DEFAULT '10000.0',
  `Monthly_return` varchar(100) DEFAULT '10000.0',
  `Return_Run_Bal` varchar(100) DEFAULT '10000.0',
  `Other_One` varchar(100) DEFAULT 'NA',
  `Other_Two` varchar(100) DEFAULT 'NA',
  `Other_Three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id_UNIQUE` (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharesreturnoninvestment`
--

LOCK TABLES `sharesreturnoninvestment` WRITE;
/*!40000 ALTER TABLE `sharesreturnoninvestment` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharesreturnoninvestment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smsmanagement`
--

DROP TABLE IF EXISTS `smsmanagement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smsmanagement` (
  `SmsId` int(11) NOT NULL AUTO_INCREMENT,
  `SmsDate` date NOT NULL DEFAULT '1970-01-01',
  `SmsSenToName` varchar(100) NOT NULL DEFAULT 'Bazirake Augustine',
  `SmsSenToNumber` varchar(100) NOT NULL DEFAULT '0782231039',
  `SmsMessage` varchar(200) NOT NULL DEFAULT 'Hello Augustine',
  `SmsSentStatus` varchar(100) NOT NULL DEFAULT 'Sent',
  `SmsSentReason` varchar(100) NOT NULL DEFAULT 'Sent',
  `SmsDeliveryStatus` varchar(100) NOT NULL DEFAULT 'Pending',
  `SmsDeliveryReason` varchar(100) NOT NULL DEFAULT 'Pending',
  `SmsTime` time NOT NULL DEFAULT '00:00:00',
  `SmsOriginator` varchar(100) NOT NULL DEFAULT 'Savings',
  `SmsOther2` varchar(100) NOT NULL DEFAULT 'N/A',
  `SmsOther3` varchar(100) NOT NULL DEFAULT 'N/A',
  `SmsOther4` varchar(100) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`SmsId`),
  UNIQUE KEY `SmsId` (`SmsId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smsmanagement`
--

LOCK TABLES `smsmanagement` WRITE;
/*!40000 ALTER TABLE `smsmanagement` DISABLE KEYS */;
/*!40000 ALTER TABLE `smsmanagement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialgroups`
--

DROP TABLE IF EXISTS `specialgroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specialgroups` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn_date` date DEFAULT '2016-12-31',
  `group_id` int(11) NOT NULL DEFAULT '0',
  `group_name` varchar(100) DEFAULT 'Googo',
  `account_name` varchar(100) DEFAULT 'Googo',
  `account_number` varchar(100) DEFAULT '05502000010',
  `contact_one` varchar(100) DEFAULT '10000.0',
  `contact_two` varchar(100) DEFAULT '10000.0',
  `email_one` varchar(100) DEFAULT '10000.0',
  `email_two` varchar(100) DEFAULT '10000.0',
  `Other_One` varchar(100) DEFAULT 'NA',
  `Other_Two` varchar(100) DEFAULT 'NA',
  `Other_Three` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id_UNIQUE` (`trn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialgroups`
--

LOCK TABLES `specialgroups` WRITE;
/*!40000 ALTER TABLE `specialgroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialgroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `summurystats`
--

DROP TABLE IF EXISTS `summurystats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `summurystats` (
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `ItemDate` date NOT NULL DEFAULT '1970-01-01',
  `TotalNumberOfCustomers` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveSavingsCustomers` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfActiveSavingsCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberSavingsWithdraws` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberSavingsWithdraws` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfSavingsMade` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfSavingsMade` int(11) NOT NULL DEFAULT '0',
  `TotalValueOfSavings` double NOT NULL DEFAULT '0',
  `TotalNumberOfActiveSharesCustomers` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfActiveSharesCustomers` int(11) NOT NULL DEFAULT '0',
  `NumberOfNewCustomers` int(11) NOT NULL DEFAULT '0',
  `CummNumberOfNewCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfCapitalisations` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfCapitalisations` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfDecapitalisations` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfDecapitalisations` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfDepositsMade` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfDepositsMade` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfDepositWithdrawsMade` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfDepositWithdrawsMade` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfActiveLoanCustomers` int(11) NOT NULL DEFAULT '0',
  `CummTotalNumberOfActiveLoanCustomers` int(11) NOT NULL DEFAULT '0',
  `TotalNumberOfAccounts` int(11) NOT NULL DEFAULT '0',
  `AccountName` varchar(100) NOT NULL DEFAULT 'Bazirake Googo',
  
  
TotalValueOfDeposits  double NOT NULL DEFAULT '0',

CummTotalValueOfDeposits double NOT NULL DEFAULT '0',

TotalValueOfDepositsMade double NOT NULL DEFAULT '0',
CummTotalValueOfDepositsMade double NOT NULL DEFAULT '0',

TotalValueOfDepositWithdrawsMade double NOT NULL DEFAULT '0',

CummTotalValueOfDepositWithdrawsMade double NOT NULL DEFAULT '0',

TotalNumberOfActiveSharesCustomers  int(11) NOT NULL DEFAULT '0',

CummTotalNumberOfActiveSharesCustomers  int(11) NOT NULL DEFAULT '0',

TotalNumberOfCapitalisations  int(11) NOT NULL DEFAULT '0',

CummTotalNumberOfCapitalisations  int(11) NOT NULL DEFAULT '0',

TotalNumberOfDecapitalisations  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfDecapitalisations  int(11) NOT NULL DEFAULT '0',


TotalValueOfShares  double NOT NULL DEFAULT '0',


CummTotalValueOfShares  double NOT NULL DEFAULT '0',


TotalValueOfSharesBought  double NOT NULL DEFAULT '0',
CummTotalValueOfSharesBought  double NOT NULL DEFAULT '0',
TotalValueOfSharesRemoved  double NOT NULL DEFAULT '0',
CummTotalValueOfSharesRemoved   double NOT NULL DEFAULT '0',

TotalNumberOfActiveLoanCustomers  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfActiveLoanCustomers  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle1  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle2  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle3  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle4  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle5  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle6  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycle7  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoanCustomersCycleAboveCycle7  int(11) NOT NULL DEFAULT '0',


TotalNumberOfActiveLoans  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfActiveLoans  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursed   int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfLoansDisbursed  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycle1  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycle2  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle3  int(11) NOT NULL DEFAULT '0',

TotalNumberOfLoansDisbursedCycle4  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle5  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle6  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle7  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycleAbove7  int(11) NOT NULL DEFAULT '0',



TotalNumberOfGroupLoansDisbursed  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfGroupLoansDisbursed  int(11) NOT NULL DEFAULT '0',


TotalNumberOfIndividualLoansDisbursed  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfIndividualLoansDisbursed  int(11) NOT NULL DEFAULT '0',

 


TotalNumberOfLoansCompleted  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfLoansCompleted  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansWrittenOff  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfLoansWrittenOff  int(11) NOT NULL DEFAULT '0',



TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly  int(11) NOT NULL DEFAULT '0',



TotalNumberOfEarlyPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',


TotalNumberOfArrearsPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',


TotalNumberOfAllPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfAllInterestAndPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansInArrears  int(11) NOT NULL DEFAULT '0',



TotalValueOfActiveLoans  double NOT NULL DEFAULT '0',

CummTotalValueOfActiveLoans  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle1  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle2  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle3  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle4  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle5  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle6  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle7  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycleAboveCycle7  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursed  double NOT NULL DEFAULT '0',

CummTotalValueOfLoansDisbursed  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycle1  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle2  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle3  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle4  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycle5  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycle6  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycle7  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycleAbove7  double NOT NULL DEFAULT '0',



TotalValueOfGroupLoansDisbursed   double NOT NULL DEFAULT '0',


CummTotalValueOfGroupLoansDisbursed  double NOT NULL DEFAULT '0',


TotalValueOfIndividualLoansDisbursed  double NOT NULL DEFAULT '0',


CummTotalValueOfIndividualLoansDisbursed  double NOT NULL DEFAULT '0',


TotalValueOfLoansCompleted  double NOT NULL DEFAULT '0',

 


CummTotalValueOfLoansCompleted  double NOT NULL DEFAULT '0',


TotalValueOfLoansWrittenOff  double NOT NULL DEFAULT '0',



CummTotalValueOfLoansWrittenOff  double NOT NULL DEFAULT '0',


TotalValueOfLoanRepaymentsMinusArrears  double NOT NULL DEFAULT '0',


TotalValueOfPrincipalOutStandingArrears  double NOT NULL DEFAULT '0',


TotalValueOfInterestOutStandingArrears  double NOT NULL DEFAULT '0',

TotalValueOfInterestReceived  double NOT NULL DEFAULT '0',



0TotalValueOfPrincipalLoanRepaymentsDueLoansOnly  double NOT NULL DEFAULT '0',


TotalValueOfEarlyPrincipalLoanRepayments  double NOT NULL DEFAULT '0',


TotalValueOfArrearsPrincipalLoanRepayments  double NOT NULL DEFAULT '0',


TotalValueOfAllPrincipalLoanRepayments  double NOT NULL DEFAULT '0',


TotalValueOfAllInterestAndPrincipalLoanRepayments  double NOT NULL DEFAULT '0',

 

TotalValueOfLoansInArrears  double NOT NULL DEFAULT '0',

                           

TotalValueOfLoanBook  double NOT NULL DEFAULT '0',


TotalValueOfCashBalances  double NOT NULL DEFAULT '0',



TotalValueOfBankBalances  double NOT NULL DEFAULT '0',



TotalValueOfAssets  double NOT NULL DEFAULT '0',

TotalValueOfReceivables  double NOT NULL DEFAULT '0',



TotalValueOfPayables  double NOT NULL DEFAULT '0',



TotalValueOfFixedAssets  double NOT NULL DEFAULT '0',

TotalValueOfCurrentAssetsIncludingInterestReceivable  double NOT NULL DEFAULT '0',



TotalValueOfCurrentAssetsMinusInterestReceivable  double NOT NULL DEFAULT '0',



TotalValueOfMainIncome  double NOT NULL DEFAULT '0',


TotalValueOfOtherIncome  double NOT NULL DEFAULT '0',



TotalValueOfIncome  double NOT NULL DEFAULT '0',


TotalValueOfExpenses  double NOT NULL DEFAULT '0',


TotalValueOfLiabilities  double NOT NULL DEFAULT '0',


TotalValueOfCapital  double NOT NULL DEFAULT '0',



TotalNumberOfCustomers  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfCustomers  int(11) NOT NULL DEFAULT '0',



NumberOfNewCustomers  int(11) NOT NULL DEFAULT '0',



CummNumberOfNewCustomers  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveSavingsCustomers  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfActiveSavingsCustomers  int(11) NOT NULL DEFAULT '0',



TotalNumberSavingsWithdraws  int(11) NOT NULL DEFAULT '0',



CummTotalNumberSavingsWithdraws  int(11) NOT NULL DEFAULT '0',



TotalNumberOfSavingsMade  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfSavingsMade  int(11) NOT NULL DEFAULT '0',



TotalValueOfSavings  double NOT NULL DEFAULT '0',



CummTotalValueOfSavings  double NOT NULL DEFAULT '0',


TotalValueOfSavingsMade  double NOT NULL DEFAULT '0',



CummTotalValueOfSavingsMade  double NOT NULL DEFAULT '0',



TotalValueOfSavingsWithdrawsMade  double NOT NULL DEFAULT '0',




CummTotalValueOfSavingsWithdrawsMade  double NOT NULL DEFAULT '0',



TotalNumberOfDepositsMade  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfDepositsMade  int(11) NOT NULL DEFAULT '0',



TotalNumberOfDepositWithdrawsMade  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfDepositWithdrawsMade  int(11) NOT NULL DEFAULT '0',



TotalValueOfDeposits  double NOT NULL DEFAULT '0',



CummTotalValueOfDeposits  double NOT NULL DEFAULT '0',



TotalValueOfDepositsMade  double NOT NULL DEFAULT '0',



CummTotalValueOfDepositsMade  double NOT NULL DEFAULT '0',



TotalValueOfDepositWithdrawsMade  double NOT NULL DEFAULT '0',



CummTotalValueOfDepositWithdrawsMade  double NOT NULL DEFAULT '0',



TotalNumberOfActiveSharesCustomers  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfActiveSharesCustomers  int(11) NOT NULL DEFAULT '0',


TotalNumberOfCapitalisations  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfCapitalisations  int(11) NOT NULL DEFAULT '0',



TotalNumberOfDecapitalisations  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfDecapitalisations  int(11) NOT NULL DEFAULT '0',



TotalValueOfShares  double NOT NULL DEFAULT '0',


CummTotalValueOfShares  double NOT NULL DEFAULT '0',



TotalValueOfSharesBought  double NOT NULL DEFAULT '0',




CummTotalValueOfSharesBought  double NOT NULL DEFAULT '0',



TotalValueOfSharesRemoved  double NOT NULL DEFAULT '0',



CummTotalValueOfSharesRemoved  double NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomers  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfActiveLoanCustomers  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycle1  int(11) NOT NULL DEFAULT '0',




TotalNumberOfActiveLoanCustomersCycle2  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycle3  int(11) NOT NULL DEFAULT '0',




TotalNumberOfActiveLoanCustomersCycle4  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycle5  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycle6  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycle7  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoanCustomersCycleAboveCycle7  int(11) NOT NULL DEFAULT '0',



TotalNumberOfActiveLoans  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfActiveLoans  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursed  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfLoansDisbursed  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle1  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle2  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle3  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycle4  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle5  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansDisbursedCycle6  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycle7  int(11) NOT NULL DEFAULT '0',


TotalNumberOfLoansDisbursedCycleAbove7  int(11) NOT NULL DEFAULT '0',



TotalNumberOfGroupLoansDisbursed  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfGroupLoansDisbursed  int(11) NOT NULL DEFAULT '0',




TotalNumberOfIndividualLoansDisbursed  int(11) NOT NULL DEFAULT '0',


CummTotalNumberOfIndividualLoansDisbursed  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansCompleted  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfLoansCompleted  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansWrittenOff  int(11) NOT NULL DEFAULT '0',



CummTotalNumberOfLoansWrittenOff  int(11) NOT NULL DEFAULT '0',



TotalNumberOfPrincipalLoanRepaymentsDueLoansOnly  int(11) NOT NULL DEFAULT '0',



TotalNumberOfEarlyPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfArrearsPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfAllPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfAllInterestAndPrincipalLoanRepayments  int(11) NOT NULL DEFAULT '0',



TotalNumberOfLoansInArrears   int(11) NOT NULL DEFAULT '0',

TotalValueOfActiveLoans   double NOT NULL DEFAULT '0',


CummTotalValueOfActiveLoans  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle1  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle2  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle3  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle4  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle5  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle6  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycle7  double NOT NULL DEFAULT '0',



TotalValueOfActiveLoanCustomersCycleAboveCycle7  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursed  double NOT NULL DEFAULT '0',



CummTotalValueOfLoansDisbursed  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle1  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle2  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle3  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle4  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle5  double NOT NULL DEFAULT '0',



TotalValueOfLoansDisbursedCycle6  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycle7  double NOT NULL DEFAULT '0',


TotalValueOfLoansDisbursedCycleAbove7  double NOT NULL DEFAULT '0',



TotalValueOfGroupLoansDisbursed  double NOT NULL DEFAULT '0',



CummTotalValueOfGroupLoansDisbursed  double NOT NULL DEFAULT '0',


TotalValueOfIndividualLoansDisbursed  double NOT NULL DEFAULT '0',



CummTotalValueOfIndividualLoansDisbursed  double NOT NULL DEFAULT '0',


TotalValueOfLoansCompleted  double NOT NULL DEFAULT '0',



CummTotalValueOfLoansCompleted  double NOT NULL DEFAULT '0',


TotalValueOfLoansWrittenOff  double NOT NULL DEFAULT '0',



CummTotalValueOfLoansWrittenOff  double NOT NULL DEFAULT '0',



TotalValueOfLoanRepaymentsMinusArrears  double NOT NULL DEFAULT '0',



TotalValueOfPrincipalOutStandingArrears  double NOT NULL DEFAULT '0',



TotalValueOfInterestOutStandingArrears  double NOT NULL DEFAULT '0',



TotalValueOfInterestReceived  double NOT NULL DEFAULT '0',



TotalValueOfPrincipalLoanRepaymentsDueLoansOnly  double NOT NULL DEFAULT '0',



TotalValueOfEarlyPrincipalLoanRepayments  double NOT NULL DEFAULT '0',



TotalValueOfArrearsPrincipalLoanRepayments  double NOT NULL DEFAULT '0',



TotalValueOfAllPrincipalLoanRepayments  double NOT NULL DEFAULT '0',



TotalValueOfAllInterestAndPrincipalLoanRepayments  double NOT NULL DEFAULT '0',


TotalValueOfLoansInArrearsÃ¿TotalValueOfLoanBook  double NOT NULL DEFAULT '0',



TotalValueOfCashBalancesÃ¿TotalValueOfBankBalances  double NOT NULL DEFAULT '0',



TotalValueOfAssetsÃ¿TotalValueOfReceivables  double NOT NULL DEFAULT '0',



TotalValueOfPayablesÃ¿TotalValueOfFixedAssets  double NOT NULL DEFAULT '0',



TotalValueOfCurrentAssetsIncludingInterestReceivable  double NOT NULL DEFAULT '0',



TotalValueOfCurrentAssetsMinusInterestReceivable  double NOT NULL DEFAULT '0',



TotalValueOfMainIncomeÃ¿TotalValueOfOtherIncome  double NOT NULL DEFAULT '0',



TotalValueOfIncomeÃ¿TotalValueOfExpenses  double NOT NULL DEFAULT '0',



TotalValueOfLiabilitiesÃ¿TotalValueOfCapital  double NOT NULL DEFAULT '0',



  
  
  PRIMARY KEY (`ItemId`),
  UNIQUE KEY `ItemId` (`ItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `summurystats`
--

LOCK TABLES `summurystats` WRITE;
/*!40000 ALTER TABLE `summurystats` DISABLE KEYS */;
/*!40000 ALTER TABLE `summurystats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trn_sqn`
--

DROP TABLE IF EXISTS `trn_sqn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trn_sqn` (
  `value` int(11) NOT NULL AUTO_INCREMENT,
  `amount` varchar(20) NOT NULL,
  PRIMARY KEY (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trn_sqn`
--

LOCK TABLES `trn_sqn` WRITE;
/*!40000 ALTER TABLE `trn_sqn` DISABLE KEYS */;
/*!40000 ALTER TABLE `trn_sqn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `txnpostingtype`
--

DROP TABLE IF EXISTS `txnpostingtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txnpostingtype` (
  `TrnId` int(11) NOT NULL AUTO_INCREMENT,
  `TxnType` varchar(100) NOT NULL DEFAULT 'N/A',
  `TxnDescription` varchar(300) NOT NULL DEFAULT 'Bazirake Googo',
  `TxnTypeOut` varchar(100) NOT NULL DEFAULT 'N/A',
  `TxnTypeStatus` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther1` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther2` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther3` varchar(100) NOT NULL DEFAULT 'N/A',
  `BatchOther4` varchar(100) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`TrnId`),
  UNIQUE KEY `TrnId` (`TrnId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txnpostingtype`
--

LOCK TABLES `txnpostingtype` WRITE;
/*!40000 ALTER TABLE `txnpostingtype` DISABLE KEYS */;
INSERT INTO `txnpostingtype` VALUES (102,'ExpenseCash','Expense Using Cash','Expense','In','NA','NA','NA','NA'),(105,'LoanPaymentBank','Loan Payment Using Bank','LoanPytBank','In','NA','NA','NA','NA'),(106,'LoanPaymentCash','Loan Payment Using Cash','LoanPyt','In','NA','NA','NA','NA'),(107,'LoanPaymentDepositedPartPayment','Loan Payment Using Deposited Part Payments','LoanPytDepositPartPay','In','NA','NA','NA','NA'),(108,'LoanPaymentSavings','Loan Payment Using Savings','LoanPytSavings','Out','NA','NA','NA','NA'),(109,'BankWithdraw','Withdraw Cash From Bank','Withdrawal','In','NA','NA','NA','NA'),(110,'BankDeposit','Deposit Money To Bank','Deposit','In','NA','NA','NA','NA'),(111,'PayableCash','Create A Payable Using Cash','Payable','In','NA','NA','NA','NA'),(112,'RefundPayableCash','Refund A Payable Using Cash','ClearPayable','In','NA','NA','NA','NA'),(113,'NewLoanCash','New Loan Disbursement Using Cash','NewLoan','In','NA','NA','NA','NA'),(114,'ReceivableCash','Receivable Using Cash','Receivable','In','NA','NA','NA','NA'),(115,'RefundReceivableCash','Refund Receivable Using Cash','ClearReceivable','In','NA','NA','NA','NA'),(116,'SalaryCash','Pay Salary Using Cash','Salary','In','NA','NA','NA','NA'),(117,'DepositPartPayment','Deposit Part Payment On Customer Ledger','DepositPartPay','Out','NA','NA','NA','NA'),(118,'WirhdrawPartPayment','Withdraw Part Payment From Customer Ledger','WithdrawDepositPartPay','Out','NA','NA','NA','NA'),(119,'SavingsCash','Savings Using Cash','Savings','Out','NA','NA','NA','NA'),(120,'WithdrawSavingsCash','Withdraw Savings Using Cash','WithdrawSavings','Out','NA','NA','NA','NA'),(121,'CapitalisationCash','Capitalise Using Cash','Capitalisation','Out','NA','NA','NA','NA'),(122,'DecapitalisationCash','Decapitalise Using Cash','Decaptalisation','Out','NA','NA','NA','NA'),(123,'RecoverInterestCash','Recover Interest Using Cash','RecoverInterest','In','NA','NA','NA','NA'),(124,'WriteOffInterestCash','Write Off Interest Using Cash','WriteOffInterest','In','NA','NA','NA','NA'),(125,'RecoverLoanPenaltyCash','Recover Loan Penalty Using Cash','RecoverPenalty','In','NA','NA','NA','NA'),(126,'WriteOffPenaltyCash','Write Off Loan Penalty Using Cash','WriteOffPenalty','In','NA','NA','NA','NA'),(127,'RecoverAccumulateInterestCash','Recover Accumulated Interest Using Cash','RecoverAccumulatedInterest','In','NA','NA','NA','NA'),(128,'WriteOffAccumulatedInterestCash','Write Off Accumulated Interest Using Cash','WriteOffAccumulatedInterest','In','NA','NA','NA','NA'),(129,'BadLoanRecoveredCash','Recover Bad Loans Using Cash','BadLoansRecovered','In','NA','NA','NA','NA'),(130,'DirectorsDrawingCash','Directors\' Drawings Using Cash','Drawing','In','NA','NA','NA','NA'),(131,'Dividends','Return On Investment','HEFROI','Out','NA','NA','NA','NA'),(132,'LoanProcessingFeesBank','Loan Processing Fees Using Bank','LProcessFeesBank','In','NA','NA','NA','NA'),(133,'ExpenseBank','Expense Using Bank','ExpenseBank','In','NA','NA','NA','NA'),(134,'NewLoanBank','New Loan Disbursement Using Bank','NewLoanBank','In','NA','NA','NA','NA'),(135,'Accounts Payables Using Bank','Accounts Payables Using Bank','PayableBank','In','NA','NA','NA','NA'),(136,'SaveBank','Save Using Bank','SavingsBank','Out','NA','NA','NA','NA'),(138,'CapitaliseSavings','Capitalise Using Savings','CapitalisationSavings','In','NA','NA','NA','NA'),(139,'CapitalisationBank','Capitalise Using Bank','CapitalisationBank1','Out','NA','NA','NA','NA'),(140,'MembershipCash','Pay Membership Fees Using Cash','MembershipFees','Out','NA','NA','NA','NA'),(141,'Receivable Using Bank','Receivable Using Bank','ReceivableBank','In','NA','NA','NA','NA'),(142,'Clear Receivable Using Bank','Clear Receivable Using Bank','ClearReceivableBank','In','NA','NA','NA','NA'),(143,'General','General Transaction ','General','In','NA','NA','NA','NA'),(144,'LoanProcessingFeesCash','Loan Processing Fees Using Cash','LProcessFees','In','NA','NA','NA','NA'),(145,'AssetDepreciation','Depreciation of Assets like Furniture,computer etc','accumDepreciation','In','NA','NA','NA','NA');
/*!40000 ALTER TABLE `txnpostingtype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-16 14:39:08
