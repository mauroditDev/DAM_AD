DROP DATABASE banco;

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

use banco;

insert into cliente (
	nombre,
	f_nac,
	direccion
) values (
	"pancracio",
	'1986-05-11',
	"mi propia casa"
),(
	"gilberto",
	'1975-11-08',
	"calle falsa,123"
),(
	"norbundia",
	'1968-10-14',
	"Guanajuato"
),(
	"palretina",
	'1980-02-23',
	"transaminasa, 27 3ºd"
),(
	"minervo",
	'1966-04-08',
	"Madrid"
),(
	"atilana",
	'1955-03-02',
	"Palencia"
),(
	"sintetoide",
	'2000-01-08',
	"León"
)
;

insert into cuenta (
	saldo
) values (0),(200),(150),(8),(74),(22),(44);

insert into titular(
	id_cuenta,
	id_cliente
) values (
	1,1
),(
	2,1
),(
	2,2
),(
	3,3
),(
	4,4
),(
	5,5
),(
	6,6
),(
	7,6
);

insert into sucursal(
	direccion,
	cp
) values (
	"sucursal uno",
	'12123'
);

insert into movimiento(
	f_h,
	importe,
	id_cuenta
) values (
	'2000-01-01T10:00:00',
	15,
	1
),(
	'2000-01-02T10:20:00',
	200,
	2
),(
	'2014-05-21T10:00:00',
	500,
	3
),(
	'2015-02-02T10:30:00',
	10,
	4
),(
	'2003-04-16T21:00:00',
	105,
	5
),(
	'2003-04-16T18:00:00',
	125,
	5
),(
	'1966-01-02T10:20:00',
	2000,
	6
);
