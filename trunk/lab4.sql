/* Creates the databse if it does not exist */
CREATE DATABASE IF NOT EXISTS Timeshares;

USE Timeshares;

/* Drops any tables that exist */
DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Unit;

/* Creates the databases */
CREATE TABLE Customer (
	CustomerID int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	FirstName varchar(50),
	LastName varchar(50),
	PhoneNumber varchar(50)
);

CREATE TABLE Unit ( 
	UnitID int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	UnitName varchar(50),
	UnitNumber varchar(5),
	MaxWeeks int(11),
	AnualMaintenenceCost int(11),
	MaintenenceShare int(11)
);

CREATE TABLE Schedule (
	UnitID int(11) NOT NULL UNIQUE,
	CustomerID int(11) NOT NULL UNIQUE,
	Week int(11)
);


ALTER TABLE `Schedule`
  ADD CONSTRAINT `Schedule_ibfk_1` FOREIGN KEY (`UnitID`) REFERENCES `Unit` (`UnitID`),
  ADD CONSTRAINT `Schedule_ibfk_2` FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`);

/* Adds a generic user for the application to use *
CREATE USER 'TimeshareUser'@'localhost' IDENTIFIED BY 'R78933jGtCKAWczM';
GRANT ALL PRIVILEGES ON * . * TO 'TimeshareUser'@'localhost' IDENTIFIED BY 'R78933jGtCKAWczM' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON `Timeshares` . * TO 'TimeshareUser'@'localhost';
/* */