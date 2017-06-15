CREATE TABLE `insurance` (
  `insuranceId` int(11) NOT NULL AUTO_INCREMENT,
  `insuranceCost` double NOT NULL DEFAULT '0',
  `carWinCode` varchar(16) NOT NULL,
  `personPersonalNumber` varchar(14) NOT NULL,
  PRIMARY KEY (`insuranceId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;