CREATE TABLE `rent` (
  `rentId` int(11) NOT NULL AUTO_INCREMENT,
  `carWinCode` varchar(16) NOT NULL,
  `personPersonalNumber` varchar(16) NOT NULL,
  `insuranceId` int(11) NOT NULL,
  `rentCost` decimal(10,0) NOT NULL DEFAULT '0',
  `rentStartDate` date NOT NULL,
  `rentEndDate` date NOT NULL,
  `rentActive` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`rentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
