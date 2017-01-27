CREATE DATABASE IF NOT EXISTS banco;

CREATE TABLE IF NOT EXISTS `banco`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NULL,
  `f_nac` DATE NULL,
  `direccion` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `banco`.`cuenta` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `saldo` INT(11) NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `banco`.`titular` (
  `id_cuenta` INT(11) NOT NULL,
  `id_cliente` INT NOT NULL,
  PRIMARY KEY (`id_cuenta`, `id_cliente`),
  INDEX `fk_cuenta_has_cliente_cliente1_idx` (`id_cliente` ASC),
  INDEX `fk_cuenta_has_cliente_cuenta1_idx` (`id_cuenta` ASC),
  CONSTRAINT `fk_cuenta_has_cliente_cuenta1`
    FOREIGN KEY (`id_cuenta`)
    REFERENCES `banco`.`cuenta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuenta_has_cliente_cliente1`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `banco`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `banco`.`movimiento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `f_h` DATETIME NOT NULL,
  `importe` INT(11) NOT NULL,
  `id_cuenta` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_movimiento_cuenta_idx` (`id_cuenta` ASC),
  CONSTRAINT `fk_movimiento_cuenta`
    FOREIGN KEY (`id_cuenta`)
    REFERENCES `banco`.`cuenta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `banco`.`sucursal` (
  `idsucursal` INT NOT NULL AUTO_INCREMENT,
  `direccion` VARCHAR(100) NULL,
  `cp` VARCHAR(5) NULL,
  PRIMARY KEY (`idsucursal`))
ENGINE = InnoDB;