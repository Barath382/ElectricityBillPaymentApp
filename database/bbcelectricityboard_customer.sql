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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wallet` double NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `customer_generated_date` datetime(6) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjt63q2suy91q2uch0ll9wcxx5` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,611.1100000000001,'No.104, Bharath st., Pune, Maharashtra.','2024-10-05 17:13:43.195108','BAR8445','Barath','barathvr382@gmail.com','6379308445'),(7,0,'123 Main St','2024-10-05 19:08:39.563052','ASR5689','Asrar','asrar@example.com','8978565689'),(8,0,'456 Elm St','2024-10-05 19:08:43.877700','DHA3211','Dhanush','dhanush@example.com','9876543211'),(9,0,'789 Oak St','2024-10-05 19:08:46.947814','DHA6745','Dhaya','dhayaj@example.com','8990786745'),(10,0,'Chennai','2024-10-05 19:08:49.719996','MAN7878','Mani','mani@example.com','7887787878'),(11,0,'12345 MainStram St','2024-10-06 18:42:59.471521','GOK5689','Gokul','gokul@gmail.com','8990785689'),(12,0,'456 Elm w St','2024-10-06 18:43:04.850049','SAN6709','Santhosh','santhosh@gmail.com','8909786709'),(13,0,'789 Oaktree St','2024-10-06 18:43:08.325609','VIJ1245','Vijayragavn','vijayaragavan@gmail.com','6778561245'),(14,0,'Chennai','2024-10-06 18:43:12.215851','SAN6745','Sankar','Sankar@gmail.com','9012346745'),(15,0,'Chennai','2024-10-06 18:43:16.831250','ARY6789','Aryan','Aryan@gmail.com','6712456789'),(16,0,'Chennai','2024-10-06 18:43:21.119098','SAN8123','Sankar','Sankar@gmail.com','9876458123'),(17,0,'Chennai','2024-10-06 18:43:24.501845','GUM4534','Guman','guman@gmail.com','6778904534'),(18,0,'Covai','2024-10-06 18:43:28.915778','HAR2378','Hari','hrai@gmail.com','8967432378');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
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

-- Dump completed on 2024-10-06 19:30:36
