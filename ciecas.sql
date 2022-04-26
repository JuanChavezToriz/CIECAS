-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.28 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para ciecas
DROP DATABASE IF EXISTS `ciecas`;
CREATE DATABASE IF NOT EXISTS `ciecas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ciecas`;

-- Volcando estructura para tabla ciecas.antecedentes academico
DROP TABLE IF EXISTS `antecedentes academico`;
CREATE TABLE IF NOT EXISTS `antecedentes academico` (
  `ID_AnteA` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT NULL,
  PRIMARY KEY (`ID_AnteA`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la colección de registros de los antecedentes academicos de los solicitantes.\r\n';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.autorizacion federal
DROP TABLE IF EXISTS `autorizacion federal`;
CREATE TABLE IF NOT EXISTS `autorizacion federal` (
  `Num_Auto` mediumint NOT NULL DEFAULT '0',
  `Clv_Prog` mediumint DEFAULT '0',
  `INO_Auto` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FCH_Auto` date DEFAULT NULL,
  `ET_Auto` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Num_Auto`),
  KEY `Clv_Prog` (`Clv_Prog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información relacionada a la autorización federal de cada programa academico.\r\n';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.caracteristicas programa
DROP TABLE IF EXISTS `caracteristicas programa`;
CREATE TABLE IF NOT EXISTS `caracteristicas programa` (
  `ID_Carac` mediumint NOT NULL AUTO_INCREMENT,
  `Clv_Prog` mediumint DEFAULT NULL,
  `Req_Carac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FCC_Carac` date DEFAULT NULL,
  `FCA_Carac` date DEFAULT NULL,
  `Dur_Carac` mediumint DEFAULT NULL,
  `Est_Carac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `TC_Carac` mediumint DEFAULT NULL,
  `ID_Peri` smallint DEFAULT '0',
  PRIMARY KEY (`ID_Carac`),
  KEY `Clv_Prog` (`Clv_Prog`),
  KEY `ID_Peri` (`ID_Peri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información relacionada con las características de cada programa academico.\r\n';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.contacto
DROP TABLE IF EXISTS `contacto`;
CREATE TABLE IF NOT EXISTS `contacto` (
  `ID_Conta` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT NULL,
  `TEC_Conta` int DEFAULT NULL,
  `TEM_Conta` int DEFAULT NULL,
  `C1_Conta` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `C2_Conta` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Conta`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Se almacena la información de contacto de los solicitantes.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.discapacidad
DROP TABLE IF EXISTS `discapacidad`;
CREATE TABLE IF NOT EXISTS `discapacidad` (
  `ID_Discap` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT NULL,
  `Tip_Discap` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  PRIMARY KEY (`ID_Discap`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacen la información de alguna discapacidad del solicitante.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.domicilio
DROP TABLE IF EXISTS `domicilio`;
CREATE TABLE IF NOT EXISTS `domicilio` (
  `ID_Domic` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT '0',
  `Call_Domic` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `NE_Domic` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `NI_Domic` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Col_Domic` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `MD_Domic` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `CP_Domic` mediumint DEFAULT NULL,
  `Est_Domic` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Pai_Domic` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Domic`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la dirección e información de los solicitantes.\r\n';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.egresados
DROP TABLE IF EXISTS `egresados`;
CREATE TABLE IF NOT EXISTS `egresados` (
  `ID_Egre` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT NULL,
  `ID_Peri` smallint DEFAULT NULL,
  PRIMARY KEY (`ID_Egre`),
  KEY `ID_Solic` (`ID_Solic`),
  KEY `ID_Peri` (`ID_Peri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información de los egresados en referencia con sus datos de solicitud.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.inscritos
DROP TABLE IF EXISTS `inscritos`;
CREATE TABLE IF NOT EXISTS `inscritos` (
  `Ins_Matri` mediumint NOT NULL AUTO_INCREMENT,
  `ID_Solic` mediumint DEFAULT NULL,
  PRIMARY KEY (`Ins_Matri`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información de los egresados en relación a los datos de su solicitud.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.lengua indigena
DROP TABLE IF EXISTS `lengua indigena`;
CREATE TABLE IF NOT EXISTS `lengua indigena` (
  `ID_Lengu` mediumint NOT NULL,
  `ID_Solic` mediumint DEFAULT NULL,
  `Tip_Lengu` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Lengu`),
  KEY `ID_Solic` (`ID_Solic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la informació relacionada con alguna lengua indigena que hable el solicitante.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.movilidad
DROP TABLE IF EXISTS `movilidad`;
CREATE TABLE IF NOT EXISTS `movilidad` (
  `ID_Movi` mediumint NOT NULL AUTO_INCREMENT,
  `Matri` mediumint DEFAULT NULL,
  `ID_Peri` smallint DEFAULT '0',
  `POE_Movi` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Fim_Movi` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `VC_Movi` bit(1) DEFAULT NULL,
  `EOR_Movi` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID_Movi`),
  KEY `Matri` (`Matri`),
  KEY `ID_Peri` (`ID_Peri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información relacionada la movilidad de los alumnos inscritos.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.periodos
DROP TABLE IF EXISTS `periodos`;
CREATE TABLE IF NOT EXISTS `periodos` (
  `ID_Peri` smallint NOT NULL AUTO_INCREMENT,
  `Nom_Peri` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FCI_Peri` date DEFAULT NULL,
  `FHT_Peri` date DEFAULT NULL,
  `LO_Peri` mediumint DEFAULT NULL,
  PRIMARY KEY (`ID_Peri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información referente a cada periodo escolar.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.programa academico
DROP TABLE IF EXISTS `programa academico`;
CREATE TABLE IF NOT EXISTS `programa academico` (
  `Clv_Prog` mediumint NOT NULL AUTO_INCREMENT,
  `Nom_Prog` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Mod_Prog` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`Clv_Prog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información relacionada con un programa academico del Posgrado.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.registro antecendentes academicos
DROP TABLE IF EXISTS `registro antecendentes academicos`;
CREATE TABLE IF NOT EXISTS `registro antecendentes academicos` (
  `ID_RegisA` mediumint NOT NULL AUTO_INCREMENT,
  `ID_AnteA` mediumint DEFAULT NULL,
  `Niv_RegisA` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `PAC_RegisA` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Ins_RegisA` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `EOP_RegisA` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FG_RegisA` date DEFAULT NULL,
  PRIMARY KEY (`ID_RegisA`),
  KEY `ID_AnteA` (`ID_AnteA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena la información de cada antecedente academico de un solicitante.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.rvoe
DROP TABLE IF EXISTS `rvoe`;
CREATE TABLE IF NOT EXISTS `rvoe` (
  `Num_RVOE` mediumint NOT NULL,
  `Clv_Prog` mediumint DEFAULT NULL,
  `DPO_RVOE` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FE_RVOE` date DEFAULT NULL,
  `ET_RVOE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Num_RVOE`),
  KEY `Clv_Prog` (`Clv_Prog`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.solicitante
DROP TABLE IF EXISTS `solicitante`;
CREATE TABLE IF NOT EXISTS `solicitante` (
  `ID_Solic` mediumint NOT NULL AUTO_INCREMENT,
  `Nom_Solic` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `AP_Solic` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `AM_Solic` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `FN_Solic` date DEFAULT NULL,
  `Sexo_Solic` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID_Solic`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Entidad que almacena la información de los Solicitantes a Postgrado.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.tipo de usuario
DROP TABLE IF EXISTS `tipo de usuario`;
CREATE TABLE IF NOT EXISTS `tipo de usuario` (
  `ID_TUR` smallint NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_TUR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Almacena los tipos de Usuario\r\n';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla ciecas.usuarios
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `ID_Usr` int NOT NULL AUTO_INCREMENT,
  `Nomb_Usr` varchar(50) DEFAULT NULL,
  `Contr_Usr` varchar(50) DEFAULT NULL,
  `US_Usr` datetime DEFAULT NULL,
  `ID_TUR` smallint DEFAULT NULL,
  PRIMARY KEY (`ID_Usr`),
  KEY `ID_TUR` (`ID_TUR`),
  CONSTRAINT `FK_usuarios_tipo de usuario` FOREIGN KEY (`ID_TUR`) REFERENCES `tipo de usuario` (`ID_TUR`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabla para almacenar los distintos Usuarios del Sistema\r\n';

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
