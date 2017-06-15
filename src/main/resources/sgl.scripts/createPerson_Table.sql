CREATE TABLE `person` (
  `personalNumber` varchar(14) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `birthDate` date NOT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `address` text,
  `licenseNumber` varchar(45) NOT NULL,
  `licenseObtainingDate` date NOT NULL,
  `licenseExpiringDate` date NOT NULL,
  PRIMARY KEY (`personalNumber`),
  UNIQUE KEY `licenseNumber_UNIQUE` (`licenseNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
