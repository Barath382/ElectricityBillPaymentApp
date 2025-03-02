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
-- Table structure for table `payment_transaction`
--

DROP TABLE IF EXISTS `payment_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_transaction` (
  `pid` bigint NOT NULL AUTO_INCREMENT,
  `card_cvv` int NOT NULL,
  `card_expire_date` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `discount` int NOT NULL,
  `transaction_amount` double NOT NULL,
  `transaction_date_time` datetime(6) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `transaction_message` varchar(255) DEFAULT NULL,
  `transaction_method` enum('CARD','CASH','Wallet') DEFAULT NULL,
  `transaction_status` enum('FAILED','PROCESS','SUCCESS') DEFAULT NULL,
  `bill_id` bigint NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `UKjr94jx7t9fa525sd76cgf7jta` (`transaction_id`),
  KEY `FKntwxy7u29f8hqnejgfh3p8157` (`bill_id`),
  CONSTRAINT `FKntwxy7u29f8hqnejgfh3p8157` FOREIGN KEY (`bill_id`) REFERENCES `electricity_bill` (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_transaction`
--

LOCK TABLES `payment_transaction` WRITE;
/*!40000 ALTER TABLE `payment_transaction` DISABLE KEYS */;
INSERT INTO `payment_transaction` VALUES (1,123,'12/2026','1234567812345678',10,1048.705,'2024-10-05 17:37:03.929502','TXN_1728130023927','Payment successful','CARD','SUCCESS',3),(2,0,NULL,NULL,5,985.63,'2024-10-05 17:39:41.195420','TXN_1728130181195','Cash Payment Success','CASH','SUCCESS',1),(3,0,NULL,NULL,10,898.8899999999999,'2024-10-05 23:10:12.297324','TXN_1728150012295','Payment successful','Wallet','SUCCESS',2),(4,123,'12/2025','1436494623946453',10,861.43625,'2024-10-06 19:09:39.707390','TXN_1728221979703','Invalid card details','CARD','FAILED',20),(5,123,'12/2026','1234567890123456',10,861.43625,'2024-10-06 19:11:10.496167','TXN_1728222070496','Invalid OTP','CARD','FAILED',20),(6,0,' ',' ',10,861.43625,'2024-10-06 19:12:12.747291','TXN_1728222132747','Invalid card details','CARD','FAILED',20),(7,123,'12/2026','1234567890123456',10,861.43625,'2024-10-06 19:12:31.528982','TXN_1728222151528','Payment successful','CARD','SUCCESS',20),(8,123,'12/2026','1234234234123423',10,936.34375,'2024-10-06 19:21:36.718053','TXN_1728222696718','Invalid card details','CARD','FAILED',22);
/*!40000 ALTER TABLE `payment_transaction` ENABLE KEYS */;
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

-- Dump completed on 2024-10-06 19:30:13
