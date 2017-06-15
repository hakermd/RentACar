CREATE TABLE `car` (
  `winCode` varchar(16) NOT NULL,
  `carManufacturer` varchar(45) NOT NULL,
  `carModel` varchar(45) NOT NULL,
  `carType` varchar(45) DEFAULT NULL,
  `yearOfProduction` varchar(45) DEFAULT NULL,
  `registrationNumber` varchar(45) NOT NULL,
  `engineVolume` varchar(45) DEFAULT NULL,
  `automaticTransmission` varchar(45) NOT NULL DEFAULT '0',
  `economyClass` varchar(45) DEFAULT NULL,
  `complectation` varchar(45) DEFAULT NULL,
  `carAvailability` varchar(45) NOT NULL,
  PRIMARY KEY (`winCode`),
  UNIQUE KEY `registration_number_UNIQUE` (`registrationNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
