
DROP TABLE IF EXISTS `sequenceNumbers`;

CREATE TABLE `sequenceNumbers` (
  `trn_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupNumber` int(11) DEFAULT 10000,
  `trnSequencyNumber` int(11) DEFAULT 20000,
  `batchNumber` int(11) DEFAULT 30000,
  `budgetEstimateNumber` int(11) DEFAULT 40000,
  `otherNumbers1` int(11) DEFAULT 50000,
  `otherNumbers2` int(11) DEFAULT 60000,
  `otherNumbers3` int(11) DEFAULT 70000,
  `otherNumbers4` int(11) DEFAULT 80000,
  PRIMARY KEY (`trn_id`),
  UNIQUE KEY `trn_id` (`trn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO sequenceNumbers VALUES(null,10000,20000,30000,40000,50000,60000,70000,80000);