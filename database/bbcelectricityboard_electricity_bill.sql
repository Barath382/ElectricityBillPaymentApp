-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: bbcelectricityboard.cfecguoc2m4q.us-east-1.rds.amazonaws.com    Database: bbcelectricityboard
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `electricity_bill`
--

DROP TABLE IF EXISTS `electricity_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `electricity_bill` (
  `bill_id` bigint NOT NULL AUTO_INCREMENT,
  `bill_date` date DEFAULT NULL,
  `bill_type` enum('CORPORATE','RESIDENTIAL') DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `bill_duration` varchar(255) DEFAULT NULL,
  `bill_status` enum('DUE','PAID') DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `units` int DEFAULT NULL,
  `customer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `FK7ewi1u71o7b1nex51t7kx8vph` (`customer_id`),
  CONSTRAINT `FK7ewi1u71o7b1nex51t7kx8vph` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `electricity_bill`
--

LOCK TABLES `electricity_bill` WRITE;
/*!40000 ALTER TABLE `electricity_bill` DISABLE KEYS */;
INSERT INTO `electricity_bill` VALUES (1,'2024-10-04','RESIDENTIAL','2024-10-29','2024-09-02 - 2024-10-03','PAID',1037.5,25,'BAR8445'),(2,'2024-11-04','RESIDENTIAL','2024-12-02','2024-10-03 - 2024-11-03','PAID',996,24,'BAR8445'),(3,'2024-12-04','RESIDENTIAL','2025-01-04','2024-11-05 - 2024-12-03','PAID',1162,28,'BAR8445'),(8,'2024-10-01','RESIDENTIAL','2024-11-01','2024-08-27 - 2024-09-30','DUE',1079,26,'ASR5689'),(9,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',954.5,23,'DHA3211'),(10,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1162,28,'DHA6745'),(11,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1120.5,27,'MAN7878'),(12,'2024-10-01','RESIDENTIAL','2024-11-01','2024-08-27 - 2024-09-30','DUE',1079,26,'GOK5689'),(13,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',954.5,23,'SAN6709'),(14,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1162,28,'VIJ1245'),(15,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1120.5,27,'SAN6745'),(16,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1203.5,29,'ARY6789'),(17,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',871.5,21,'SAN8123'),(18,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',913,22,'GUM4534'),(19,'2024-10-05','RESIDENTIAL','2024-11-05','2024-08-27 - 2024-09-30','DUE',1203.5,29,'HAR2378'),(20,'2024-10-05','RESIDENTIAL','2024-10-30','2024-09-04 - 2024-10-04','PAID',954.5,23,'BAR8445'),(21,'2024-09-05','RESIDENTIAL','2024-09-29','2024-08-04 - 2024-09-04','DUE',1203.5,29,'BAR8445'),(22,'2024-10-06','RESIDENTIAL','2024-10-30','2024-09-04 - 2024-10-03','DUE',1037.5,25,'BAR8445');
/*!40000 ALTER TABLE `electricity_bill` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-06 19:30:22
