CREATE DATABASE IF NOT EXISTS `dice`;
USE dice;

CREATE USER IF NOT EXISTS `dice`@`localhost` IDENTIFIED BY 'backend';
CREATE USER `dice`@`%` IDENTIFIED BY 'backend';

GRANT all privileges ON `dice`.* TO `dice`@`localhost`;
GRANT all privileges ON `dice`.* TO `dice`@`%`;