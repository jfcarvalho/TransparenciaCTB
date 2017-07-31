-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: contratos
-- ------------------------------------------------------
-- Server version	5.7.9-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contrato` (
  `id_contrato` int(11) NOT NULL AUTO_INCREMENT,
  `cpf_responsavel` varchar(255) DEFAULT NULL,
  `data_assinatura` date DEFAULT NULL,
  `data_vencimento` date DEFAULT NULL,
  `duracao_meses` int(11) DEFAULT NULL,
  `fonte` varchar(255) DEFAULT NULL,
  `meses_vencimento` int(11) DEFAULT NULL,
  `nome_responsavel` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `objeto` varchar(255) DEFAULT NULL,
  `recurso` varchar(255) DEFAULT NULL,
  `saldo_contrato` float NOT NULL,
  `ultima_atualizacao` date DEFAULT NULL,
  `uso` varchar(255) DEFAULT NULL,
  `vencimento_garantia` int(11) DEFAULT NULL,
  `valor_contrato` float NOT NULL,
  `contrato_id_contrato` int(11) DEFAULT NULL,
  `contrato_id_fiscal` int(11) DEFAULT NULL,
  `contrato_id_gestor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_contrato`),
  KEY `FK_rfhooi2wklvxavip3rs7pmurg` (`contrato_id_contrato`),
  KEY `FK_40nstffu4p05es0j0mhms52vv` (`contrato_id_fiscal`),
  KEY `FK_l9tr6ikjvhiddtca45dipu8vu` (`contrato_id_gestor`),
  CONSTRAINT `FK_40nstffu4p05es0j0mhms52vv` FOREIGN KEY (`contrato_id_fiscal`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FK_l9tr6ikjvhiddtca45dipu8vu` FOREIGN KEY (`contrato_id_gestor`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FK_rfhooi2wklvxavip3rs7pmurg` FOREIGN KEY (`contrato_id_contrato`) REFERENCES `contratado` (`id_contratado`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato`
--

LOCK TABLES `contrato` WRITE;
/*!40000 ALTER TABLE `contrato` DISABLE KEYS */;
INSERT INTO `contrato` VALUES (10,'1234','2016-10-04','2017-03-04',17,'Custeio',17,'blablabla','007/2013','Coleta e transporte de valores','Convenio',23171.2,'2016-10-04','Continuo',12,24414.8,1,3,3),(11,'123456','2016-11-08','2016-11-08',11,'Investimento',11,'Blbalbal','6969','Alguma coisa','Convenio',1800,'2016-11-08','Continuo',12,1800,2,3,3),(12,'123456','2016-11-08','2016-11-08',12,'Investimento',12,'Blbalbal','007/2014','Alguma coisa','Convenio',1800,'2016-11-08','Continuo',12,1800,2,3,3);
/*!40000 ALTER TABLE `contrato` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-16 17:37:37
