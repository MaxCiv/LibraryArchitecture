-- MySQL Script generated by MySQL Workbench
-- Tue May 29 18:43:14 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema librarydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema librarydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `librarydb` DEFAULT CHARACTER SET utf8 ;
USE `librarydb` ;

-- -----------------------------------------------------
-- Table `librarydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `librarydb`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` CHAR(40) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `role` INT NOT NULL COMMENT 'Три роли:\n0 librarian\n1 reader \n2 supplier',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarydb`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `librarydb`.`book` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(100) NOT NULL,
  `publisher` VARCHAR(100) NOT NULL,
  `publish_year` INT NOT NULL,
  `status` INT NOT NULL COMMENT 'Три статуса:\n0 - library\n1 - exchange\n2 - order',
  `condition` INT NOT NULL COMMENT 'Состояния:\n0 - in_library\n1 - in_reading\n2 - not_available\n3 - returned_to_owner\n4 - on_exchange\n5 - order_in_progress',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarydb`.`orderbooks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `librarydb`.`orderbooks` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` INT UNSIGNED NOT NULL,
  `supplier_id` INT UNSIGNED NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_OrderBooks_Book1_idx` (`book_id` ASC),
  INDEX `fk_OrderBooks_User1_idx` (`supplier_id` ASC),
  CONSTRAINT `fk_OrderBooks_Book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `librarydb`.`book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderBooks_User1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `librarydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarydb`.`librarybooks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `librarydb`.`librarybooks` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` INT UNSIGNED NOT NULL,
  `reader_id` INT UNSIGNED NOT NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_LibraryBooks_Book1_idx` (`book_id` ASC),
  INDEX `fk_LibraryBooks_User1_idx` (`reader_id` ASC),
  CONSTRAINT `fk_LibraryBooks_Book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `librarydb`.`book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LibraryBooks_User1`
    FOREIGN KEY (`reader_id`)
    REFERENCES `librarydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarydb`.`exchangebooks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `librarydb`.`exchangebooks` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` INT UNSIGNED NOT NULL,
  `owner_id` INT UNSIGNED NOT NULL,
  `open_exchange_date` DATETIME NULL,
  `reader_id` INT UNSIGNED NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ExchangeBooks_Book1_idx` (`book_id` ASC),
  INDEX `fk_ExchangeBooks_User1_idx` (`owner_id` ASC),
  INDEX `fk_ExchangeBooks_User2_idx` (`reader_id` ASC),
  CONSTRAINT `fk_ExchangeBooks_Book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `librarydb`.`book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ExchangeBooks_User1`
    FOREIGN KEY (`owner_id`)
    REFERENCES `librarydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ExchangeBooks_User2`
    FOREIGN KEY (`reader_id`)
    REFERENCES `librarydb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `librarydb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `librarydb`;
INSERT INTO `librarydb`.`user` (`id`, `login`, `password`, `name`, `role`) VALUES (DEFAULT, 'root', '011c945f30ce2cbafc452f39840f025693339c42', 'Oleynik Maxim', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `librarydb`.`book`
-- -----------------------------------------------------
START TRANSACTION;
USE `librarydb`;
INSERT INTO `librarydb`.`book` (`id`, `title`, `author`, `publisher`, `publish_year`, `status`, `condition`) VALUES (DEFAULT, 'Good Book 01', 'Author 01', 'Publisher 01', 1991, 0, 0);
INSERT INTO `librarydb`.`book` (`id`, `title`, `author`, `publisher`, `publish_year`, `status`, `condition`) VALUES (DEFAULT, 'Book 01', 'Author 02', 'Publisher 02', 1965, 0, 0);

COMMIT;

