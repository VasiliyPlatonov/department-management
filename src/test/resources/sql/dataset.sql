/*Create database*/
# CREATE SCHEMA IF NOT EXISTS `department_management_test` DEFAULT CHARACTER SET utf8 ;

-- Create 'departments' table
CREATE TABLE `department_management_test`.`departments` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`parent_id` INT NULL,
PRIMARY KEY (`id`));

-- Fill 'departments' table
INSERT INTO `department_management_test`.`departments` (`NAME`)               VALUES ('marketing');
INSERT INTO `department_management_test`.`departments` (`NAME`, `parent_id`)  VALUES ('sales', '1');
INSERT INTO `department_management_test`.`departments` (`NAME`)               VALUES ('development');
INSERT INTO `department_management_test`.`departments` (`NAME`, `parent_id`)  VALUES ('testing', '3');
INSERT INTO `department_management_test`.`departments` (`NAME`, `parent_id`)  VALUES ('design', '3');
INSERT INTO `department_management_test`.`departments` (`NAME`)               VALUES ('human resources');

-- Drop 'departments' table
DROP TABLE `department_management_test`.`departments`;



-- Create 'employees' table
CREATE TABLE `department_management_test`.`employees` (
`id` INT NOT NULL AUTO_INCREMENT,
`first_name` VARCHAR(45) NULL,
`last_name` VARCHAR(45) NULL,
`department_id` INT NOT NULL,
PRIMARY KEY (`id`))
CONSTRAINT `employees_departments_fk`
FOREIGN KEY (`department_id`)
REFERENCES `department_management`.`departments` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

-- Fill 'employees' table