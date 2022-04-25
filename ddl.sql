DROP DATABASE IF EXISTS api_test;
CREATE DATABASE api_test;

USE api_test;

DROP TABLE IF EXISTS person;
CREATE TABLE `person` (
  `dni` varchar(45) NOT NULL,
  `nombre` varchar(56) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `edad` int(11) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;