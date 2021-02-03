-- MariaDB dump 10.18  Distrib 10.4.17-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: groenify
-- ------------------------------------------------------
-- Server version	10.4.17-MariaDB-1:10.4.17+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company`
(
    `id`            bigint(20)                  NOT NULL AUTO_INCREMENT,
    `name`          mediumtext COLLATE utf8_bin NOT NULL,
    `creation_date` timestamp                   NOT NULL DEFAULT current_timestamp(),
    `url`           longtext COLLATE utf8_bin            DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `company_UN` (`name`) USING HASH
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='Contains the companies, delivering electric poles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company`
    DISABLE KEYS */;
INSERT INTO `company`
VALUES (1, 'Vattenfall', '2020-12-28 00:43:12',
        'https://incharge.vattenfall.nl/openbare-laadpalen/'),
       (2, 'Vandebron', '2020-12-28 00:43:56',
        'https://vandebron.nl/elektrisch-rijden/laadpalen/groenladen'),
       (3, 'CoolBlue', '2020-12-28 00:43:56', 'https://laadpalen.coolblue.nl/');
/*!40000 ALTER TABLE `company`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_to_epole`
--

DROP TABLE IF EXISTS `company_to_epole`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_to_epole`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `company_id` bigint(20)   NOT NULL,
    `epole_id`   bigint(20)   NOT NULL,
    `base_price` double(6, 2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `company_to_epole_UN` (`company_id`, `epole_id`),
    KEY `company_to_epole_FK_1` (`epole_id`),
    CONSTRAINT `company_to_epole_FK` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `company_to_epole_FK_1` FOREIGN KEY (`epole_id`) REFERENCES `epole` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_to_epole`
--

LOCK TABLES `company_to_epole` WRITE;
/*!40000 ALTER TABLE `company_to_epole`
    DISABLE KEYS */;
INSERT INTO `company_to_epole`
VALUES (1, 1, 1, 1240.00),
       (2, 2, 1, 450.00),
       (3, 3, 1, 1249.00),
       (4, 3, 2, 699.00),
       (5, 3, 3, 849.00),
       (6, 3, 4, 1129.00);
/*!40000 ALTER TABLE `company_to_epole`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_to_epole_to_factor_answer`
--

DROP TABLE IF EXISTS `company_to_epole_to_factor_answer`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_to_epole_to_factor_answer`
(
    `id`                  bigint(20)   NOT NULL AUTO_INCREMENT,
    `company_to_epole_id` bigint(20)   NOT NULL,
    `factor_answer_id`    bigint(20)   NOT NULL,
    `price`               double(6, 2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `company_to_epole_to_factor_answer_UN` (`factor_answer_id`, `company_to_epole_id`),
    KEY `company_to_epole_to_factor_answer_FK` (`company_to_epole_id`),
    CONSTRAINT `company_to_epole_to_factor_answer_FK` FOREIGN KEY (`company_to_epole_id`) REFERENCES `company_to_epole` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `company_to_epole_to_factor_answer_FK_1` FOREIGN KEY (`factor_answer_id`) REFERENCES `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_to_epole_to_factor_answer`
--

LOCK TABLES `company_to_epole_to_factor_answer` WRITE;
/*!40000 ALTER TABLE `company_to_epole_to_factor_answer`
    DISABLE KEYS */;
INSERT INTO `company_to_epole_to_factor_answer`
VALUES (1, 1, 1, 20.00),
       (2, 1, 2, 0.00);
/*!40000 ALTER TABLE `company_to_epole_to_factor_answer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `epole`
--

DROP TABLE IF EXISTS `epole`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `epole`
(
    `id`          bigint(20)                  NOT NULL AUTO_INCREMENT,
    `brand`       bigint(20)                  NOT NULL,
    `type`        mediumtext COLLATE utf8_bin NOT NULL,
    `description` longtext COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `epole_FK` (`brand`),
    CONSTRAINT `epole_FK` FOREIGN KEY (`brand`) REFERENCES `epole_brand` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='Contains the avaliable electrical poles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `epole`
--

LOCK TABLES `epole` WRITE;
/*!40000 ALTER TABLE `epole`
    DISABLE KEYS */;
INSERT INTO `epole`
VALUES (1, 1, 'Eve Single Pro Line', NULL),
       (2, 1, 'Eve Single S-Line', NULL),
       (3, 2, 'Elvi Compleet', NULL),
       (4, 2, 'Elvi Basis', NULL);
/*!40000 ALTER TABLE `epole`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `epole_brand`
--

DROP TABLE IF EXISTS `epole_brand`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `epole_brand`
(
    `id`   bigint(20)                  NOT NULL AUTO_INCREMENT,
    `name` mediumtext COLLATE utf8_bin NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `epole_brand_UN` (`name`) USING HASH
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `epole_brand`
--

LOCK TABLES `epole_brand` WRITE;
/*!40000 ALTER TABLE `epole_brand`
    DISABLE KEYS */;
INSERT INTO `epole_brand`
VALUES (1, 'Alfen'),
       (2, 'EVBox');
/*!40000 ALTER TABLE `epole_brand`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor`
--

DROP TABLE IF EXISTS `factor`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor`
(
    `id`               bigint(20)                  NOT NULL AUTO_INCREMENT,
    `name`             mediumtext COLLATE utf8_bin NOT NULL,
    `question`         mediumtext COLLATE utf8_bin NOT NULL,
    `factor_type`      bigint(20)                  NOT NULL,
    `description`      longtext COLLATE utf8_bin DEFAULT NULL,
    `required`         tinyint(1)                DEFAULT 0,
    `factor_answer_id` bigint(20)                DEFAULT NULL,

    PRIMARY KEY (`id`),
    UNIQUE KEY `factor_UN` (`name`) USING HASH,
    KEY `factor_FK` (`factor_type`),
    CONSTRAINT `factor_FK` FOREIGN KEY (`factor_type`) REFERENCES `factor_type`
        (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_FK_1` FOREIGN KEY (`factor_answer_id`) REFERENCES
        `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor`
--

LOCK TABLES `factor` WRITE;
/*!40000 ALTER TABLE `factor`
    DISABLE KEYS */;
INSERT INTO `factor`
VALUES (1, 'soort_laadpunt', 'Welk soort laadpunt wilt u hebben?', 2, NULL, 1,
        NULL),
       (2, 'vaste_laadkabel', 'Wilt u een vaste laadkabel?', 1, NULL, 0, NULL),
       (3, 'lengte_laadkabel', 'Welke lengte zou u de laadkabel willen?', 3,
        NULL, 0, NULL),
       (4, 'installatie_pakket',
        'Wat is de lengte van uw meterkast tot aan de laadpaal? En hoeveel meter is ondergronds?',
        4, NULL, 1, NULL),
       (5, 'dynamic_load_balancing', 'Wilt u Dynamic Load Balancing?', 1,
        NULL, 0, NULL),
       (6, 'verzwaren', 'Wilt u de meterkast verzwaren?', 1, NULL, 0, NULL);
/*!40000 ALTER TABLE `factor`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_answer`
--

DROP TABLE IF EXISTS `factor_answer`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_answer`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `factor_id`   bigint(20) NOT NULL,
    `factor_type` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `factor_answer_UN` (`id`, `factor_id`),
    KEY `factor_answer_FK` (`factor_id`),
    KEY `factor_answer_FK_1` (`factor_type`),
    CONSTRAINT `factor_answer_FK` FOREIGN KEY (`factor_id`) REFERENCES `factor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_answer_FK_1` FOREIGN KEY (`factor_type`) REFERENCES `factor_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_answer`
--

LOCK TABLES `factor_answer` WRITE;
/*!40000 ALTER TABLE `factor_answer`
    DISABLE KEYS */;
INSERT INTO `factor_answer`
VALUES (1, 2, 1),
       (2, 2, 1);
/*!40000 ALTER TABLE `factor_answer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_answer_boolean`
--

DROP TABLE IF EXISTS `factor_answer_boolean`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_answer_boolean`
(
    `factor_answer_id`        bigint(20) NOT NULL,
    `factor_answer_factor_id` bigint(20) NOT NULL,
    `answer_boolean`          tinyint(1) NOT NULL,
    PRIMARY KEY (`factor_answer_id`),
    UNIQUE KEY `factor_answer_boolean_UN` (`factor_answer_factor_id`,
                                           `answer_boolean`),
    CONSTRAINT `factor_answer_boolean_FK` FOREIGN KEY (`factor_answer_id`) REFERENCES `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_answer_boolean_FK_1` FOREIGN KEY (`factor_answer_id`, `factor_answer_factor_id`) REFERENCES `factor_answer` (`id`, `factor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_answer_boolean`
--

LOCK TABLES `factor_answer_boolean` WRITE;
/*!40000 ALTER TABLE `factor_answer_boolean`
    DISABLE KEYS */;
INSERT INTO `factor_answer_boolean`
VALUES (1, 2, 1),
       (2, 2, 0);
/*!40000 ALTER TABLE `factor_answer_boolean`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_answer_multiple_choice`
--

DROP TABLE IF EXISTS `factor_answer_multiple_choice`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_answer_multiple_choice`
(
    `factor_answer_id`        bigint(20)                    NOT NULL,
    `factor_answer_factor_id` bigint(20)                    NOT NULL,
    `answer_multiple`         mediumtext COLLATE utf8_bin   NOT NULL,
    `lower_answer_hash`       varchar(512) COLLATE utf8_bin NOT NULL,
    PRIMARY KEY (`factor_answer_id`),
    UNIQUE KEY `factor_answer_multiple_choice_UN` (`factor_answer_factor_id`,
                                                   `lower_answer_hash`),
    KEY `factor_answer_multiple_choice_FK_1` (`factor_answer_id`, `factor_answer_factor_id`),
    CONSTRAINT `factor_answer_multiple_choice_FK` FOREIGN KEY (`factor_answer_id`) REFERENCES `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_answer_multiple_choice_FK_1` FOREIGN KEY (`factor_answer_id`, `factor_answer_factor_id`) REFERENCES `factor_answer` (`id`, `factor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_answer_multiple_choice`
--

LOCK TABLES `factor_answer_multiple_choice` WRITE;
/*!40000 ALTER TABLE `factor_answer_multiple_choice`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `factor_answer_multiple_choice`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_answer_number`
--

DROP TABLE IF EXISTS `factor_answer_number`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_answer_number`
(
    `factor_answer_id`        bigint(20) NOT NULL,
    `factor_answer_factor_id` bigint(20) NOT NULL,
    `min_number`              double DEFAULT NULL,
    `max_number`              double DEFAULT NULL,
    PRIMARY KEY (`factor_answer_id`),
    UNIQUE KEY `factor_answer_number_UN` (`max_number`, `min_number`,
                                          `factor_answer_factor_id`),
    KEY `factor_answer_number_FK_1` (`factor_answer_id`, `factor_answer_factor_id`),
    CONSTRAINT `factor_answer_number_FK` FOREIGN KEY (`factor_answer_id`) REFERENCES `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_answer_number_FK_1` FOREIGN KEY (`factor_answer_id`, `factor_answer_factor_id`) REFERENCES `factor_answer` (`id`, `factor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_answer_number`
--

LOCK TABLES `factor_answer_number` WRITE;
/*!40000 ALTER TABLE `factor_answer_number`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `factor_answer_number`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_answer_double_number`
--

DROP TABLE IF EXISTS `factor_answer_double_number`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_answer_double_number`
(
    `factor_answer_id`        bigint(20) NOT NULL,
    `factor_answer_factor_id` bigint(20) NOT NULL,
    `min_number_a`            double DEFAULT NULL,
    `max_number_a`            double DEFAULT NULL,
    `min_number_b`            double DEFAULT NULL,
    `max_number_b`            double DEFAULT NULL,
    PRIMARY KEY (`factor_answer_id`),
    UNIQUE KEY `factor_answer_double_number_UN` (`max_number_a`, `min_number_a`,
                                                 `max_number_b`, min_number_b,
                                                 `factor_answer_factor_id`),
    KEY `factor_answer_double_number_FK_1` (`factor_answer_id`, `factor_answer_factor_id`),
    CONSTRAINT `factor_answer_double_number_FK` FOREIGN KEY (`factor_answer_id`) REFERENCES `factor_answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `factor_answer_double_number_FK_1` FOREIGN KEY (`factor_answer_id`, `factor_answer_factor_id`) REFERENCES `factor_answer` (`id`, `factor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_answer_double_number`
--

LOCK TABLES `factor_answer_double_number` WRITE;
/*!40000 ALTER TABLE `factor_answer_double_number`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `factor_answer_double_number`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_type`
--

DROP TABLE IF EXISTS `factor_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factor_type`
(
    `id`          bigint(20)                    NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) COLLATE utf8_bin NOT NULL,
    `description` longtext COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `factor_type_UN` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_type`
--

LOCK TABLES `factor_type` WRITE;
/*!40000 ALTER TABLE `factor_type`
    DISABLE KEYS */;
INSERT INTO `factor_type`
VALUES (1, 'BOOLEAN_QUESTION', NULL),
       (2, 'MULTIPLE_CHOICE', NULL),
       (3, 'NUMBER', NULL),
       (4, 'DOUBLE_NUMBER', NULL);
/*!40000 ALTER TABLE `factor_type`
    ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-01-28  8:50:21