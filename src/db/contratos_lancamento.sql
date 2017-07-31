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
-- Table structure for table `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lancamento` (
  `id_lancamento` int(11) NOT NULL AUTO_INCREMENT,
  `aditivo_n` int(11) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `numero_nota_fiscal` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `valor` float NOT NULL,
  `valor_aditivo` float NOT NULL,
  `lancamento_id_contrato` int(11) DEFAULT NULL,
  `lancamento_id_processo` int(11) DEFAULT NULL,
  `possui_aditivo` bit(1) NOT NULL,
  `tipoaditivo` varchar(255) DEFAULT NULL,
  `liquidado` bit(1) NOT NULL,
  `dataliquidacao` datetime DEFAULT NULL,
  `prorrogacao` datetime DEFAULT NULL,
  `meses_prorrogacao` int(11) DEFAULT NULL,
  `saldo_contrato` float NOT NULL,
  PRIMARY KEY (`id_lancamento`),
  KEY `FK_nx7loybpud922ql8a6ihp9wew` (`lancamento_id_contrato`),
  KEY `FK_nqscr60psgfr3mi41p7k0l2vj` (`lancamento_id_processo`),
  CONSTRAINT `FK_nqscr60psgfr3mi41p7k0l2vj` FOREIGN KEY (`lancamento_id_processo`) REFERENCES `processo` (`id_processo`),
  CONSTRAINT `FK_nx7loybpud922ql8a6ihp9wew` FOREIGN KEY (`lancamento_id_contrato`) REFERENCES `contrato` (`id_contrato`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
INSERT INTO `lancamento` VALUES (13,NULL,'2014-01-04','1234','aaaaa',1398.64,0,10,2,'\0',NULL,'\0','2016-10-04 00:00:00',NULL,NULL,23016.2),(14,NULL,'2014-03-04','123','123',1644.36,0,10,2,'\0',NULL,'\0','2016-10-04 00:00:00',NULL,NULL,21371.8),(15,NULL,'2014-03-05','123','132',1648.92,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,19722.9),(16,NULL,'2014-03-17','123','123',1477.46,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,18245.4),(17,NULL,'2014-03-21','123','',1399.21,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,16846.2),(18,NULL,'2014-05-04','212','2121',1641.43,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,15204.8),(19,NULL,'2014-06-04','','',1560.06,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,13644.7),(20,NULL,'2014-07-04','','',1316.57,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,12328.1),(21,NULL,'2014-08-04','','',1802.17,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,10525.9),(22,NULL,'2014-09-04','','',1640.12,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,8885.78),(23,NULL,'2014-10-04','','',1645.02,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,7240.76),(24,NULL,'2014-11-04','','',1808.77,0,10,2,'\0',NULL,'\0',NULL,NULL,NULL,5431.99),(25,1,'2014-12-04','','',658.17,25365.1,10,2,'',NULL,'\0',NULL,NULL,5,30138.9),(26,NULL,'2015-01-05','45698','Blablabla',1200,0,10,2,'\0',NULL,'','2015-11-15 00:00:00',NULL,NULL,28938.9),(27,NULL,'2015-02-10','88547','hjkl',1450,0,10,2,'\0',NULL,'','2016-11-09 00:00:00',NULL,NULL,27488.9),(28,NULL,'2015-03-24','89745','oopopopo',960,0,10,2,'\0',NULL,'\0','2016-11-08 00:00:00',NULL,NULL,26528.9),(29,NULL,'2015-04-13','87454','blablabla',897.65,0,10,2,'\0',NULL,'','2016-11-08 00:00:00',NULL,NULL,25631.2),(30,NULL,'2015-06-23','7896654','rrerere',1230,0,10,2,'\0',NULL,'\0','2016-11-08 00:00:00',NULL,NULL,24401.2),(31,NULL,'2015-07-15','qwqwqwq','asasasa',1230,0,10,2,'\0',NULL,'\0','2016-11-22 00:00:00',NULL,NULL,23171.2);
/*!40000 ALTER TABLE `lancamento` ENABLE KEYS */;
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
