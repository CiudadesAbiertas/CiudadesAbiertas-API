DROP USER 'ciudadesAbiertas'@'%';

DROP DATABASE `ciudadesAbiertas`;

CREATE USER 'ciudadesAbiertas'@'%' IDENTIFIED BY 'ciudadesAbiertas';

GRANT USAGE ON *.* TO 'ciudadesAbiertas'@'%';

CREATE DATABASE IF NOT EXISTS `ciudadesAbiertas`;GRANT ALL PRIVILEGES ON `ciudadesAbiertas`.* TO 'ciudadesAbiertas'@'%';


