-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: PlanMan
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Table structure for table `ConsumeRecordInfo`
--

DROP TABLE IF EXISTS `ConsumeRecordInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ConsumeRecordInfo` (
  `consumeRecordId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `consumeRecordTime` varchar(20) DEFAULT NULL,
  `consumeCC` double DEFAULT NULL,
  `consumeRecordType` int(11) DEFAULT NULL,
  `consumeRecordContent` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`consumeRecordId`,`userId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `UserInfo` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10019 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ConsumeRecordInfo`
--

LOCK TABLES `ConsumeRecordInfo` WRITE;
/*!40000 ALTER TABLE `ConsumeRecordInfo` DISABLE KEYS */;
INSERT INTO `ConsumeRecordInfo` VALUES (10007,10001,'1456571693',197.84,1,'[{\"aloneCC\":64.0,\"gram\":100.0,\"slidingName\":\"DAIRYSTAR???????1L\",\"slidingTime\":\"??\",\"totalCC\":64.0},{\"aloneCC\":133.84,\"gram\":100.0,\"slidingName\":\" ??LAWSON???????\",\"slidingTime\":\"??\",\"totalCC\":133.84}]'),(10014,10001,'1456587065',50,1,'[{\"slidingTime\":\"??\",\"slidingName\":\"UFC 100%???\",\"gram\":100.0,\"aloneCC\":50.0,\"totalCC\":50.0}]'),(10015,10001,'1456587067',50,1,'[{\"slidingTime\":\"??\",\"slidingName\":\"UFC 100%???\",\"gram\":100.0,\"aloneCC\":50.0,\"totalCC\":50.0}]'),(10016,10001,'1456587066',50,1,'[{\"slidingTime\":\"??\",\"slidingName\":\"UFC 100%???\",\"gram\":100.0,\"aloneCC\":50.0,\"totalCC\":50.0}]'),(10017,10001,'1456635430',0,1,'[{\"aloneCC\":15.0,\"gram\":0.0,\"slidingName\":\"?????250????????\",\"slidingTime\":\"10??\",\"totalCC\":0.0},{\"aloneCC\":15.0,\"gram\":0.0,\"slidingName\":\"????200????????\",\"slidingTime\":\"10??\",\"totalCC\":0.0},{\"aloneCC\":13.0,\"gram\":0.0,\"slidingName\":\"?????200??????\",\"slidingTime\":\"10??\",\"totalCC\":0.0}]'),(10018,10001,'1457352911',137.7,1,'[{\"slidingTime\":\"??\",\"slidingName\":\"TAMEK???????????????\",\"gram\":100.0,\"aloneCC\":46.85,\"totalCC\":46.85},{\"slidingTime\":\"??\",\"slidingName\":\"Glocken???\",\"gram\":100.0,\"aloneCC\":44.0,\"totalCC\":44.0},{\"slidingTime\":\"??\",\"slidingName\":\"TAMEK???????????????\",\"gram\":100.0,\"aloneCC\":46.85,\"totalCC\":46.85}]');
/*!40000 ALTER TABLE `ConsumeRecordInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-25 19:17:36
