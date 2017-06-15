CREATE TABLE `booking` (
  `bookingId` int(11) NOT NULL AUTO_INCREMENT,
  `bookingDate` date DEFAULT NULL,
  `bookingCost` double NOT NULL DEFAULT '0',
  `carWinCode` varchar(16) NOT NULL,
  `personPersonalNumber` varchar(14) NOT NULL,
  `bookingCode` varchar(10) NOT NULL,
  `bookingActive` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`bookingId`),
  UNIQUE KEY `booking_code_UNIQUE` (`bookingCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
