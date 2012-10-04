CREATE DATABASE IF NOT EXISTS Timeshares;

USE Timeshares;

DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Unit;

CREATE TABLE Customer (
	CustomerID int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	FirstName varchar(50),
	LastName varchar(50),
	PhoneNumber varchar(50)
);

CREATE TABLE Unit ( 
	UnitID int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	UnitName varchar(50),
	UntiNumber varchar(5),
	MaintenanceCost int(11),
	MaxWeeks int(11),
	AnualMaintenenceCost int(11),
	MaintenenceShare int(11)
);

CREATE TABLE Schedule (
	UnitID int(11) NOT NULL UNIQUE,
	CustomerID int(11) NOT NULL UNIQUE,
	Week int(11)
);
