-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `answering`
--

DROP TABLE IF EXISTS `answering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answering` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `qid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `answer` text,
  `atime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `qid` (`qid`),
  KEY `pid` (`pid`),
  KEY `fk_users` (`uid`),
  CONSTRAINT `answering_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`id`),
  CONSTRAINT `answering_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `testpaper` (`id`),
  CONSTRAINT `answering_ibfk_3` FOREIGN KEY (`uid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answering`
--

LOCK TABLES `answering` WRITE;
/*!40000 ALTER TABLE `answering` DISABLE KEYS */;
/*!40000 ALTER TABLE `answering` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pqconnection`
--

DROP TABLE IF EXISTS `pqconnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pqconnection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qid` int(11) DEFAULT NULL,
  `qscore` int(11) DEFAULT '0',
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pqIDUnique` (`qid`,`pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `pqconnection_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`id`),
  CONSTRAINT `pqconnection_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `testpaper` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pqconnection`
--

LOCK TABLES `pqconnection` WRITE;
/*!40000 ALTER TABLE `pqconnection` DISABLE KEYS */;
INSERT INTO `pqconnection` VALUES (4,173,3,2),(5,174,3,2),(6,171,3,2),(7,161,3,2),(8,170,3,2),(9,165,3,2);
/*!40000 ALTER TABLE `pqconnection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` enum('1','2','3','4','5','6','7','8','9','10','11','12') DEFAULT NULL,
  `type` enum('1','2','3','4','5','6','7','8','9','10') DEFAULT NULL,
  `stem` text,
  `answer` text,
  `difdeg` enum('1','2','3','4','5','10') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (155,'10','1','Timlong同志的学习经历A、Guanmiao中心小学B、Guanmiao初级中学C、江苏省SYZXD、NUIST','答案：D','1'),(156,'10','1','关于生物安保与生物安全的描述，错误的是？A  均需要通过风险评估，确定安保、安全的级别。B  均要求管理的过程以记录的形式呈现并保留存档。C  均为防止病原体或毒素的无意失误使用。D  均要高度重视生物材料在机构内及机构间的转移和运输。','答案：C','1'),(157,'10','1','1.       关于生物安保计划，正确的是 A 需做风险评估B 需进行培训和应急演练C 必要时与当地公安，消防机构联系D 以上都是','答案：D','1'),(158,'10','1','Timlong同志的学习经历A、Guanmiao中心小学B、Guanmiao初级中学C、江苏省SYZXD、NUIST','答案：D','1'),(159,'10','1','关于生物安保与生物安全的描述，错误的是？A  均需要通过风险评估，确定安保、安全的级别。B  均要求管理的过程以记录的形式呈现并保留存档。C  均为防止病原体或毒素的无意失误使用。D  均要高度重视生物材料在机构内及机构间的转移和运输。','答案：C','1'),(160,'10','1','1.       关于生物安保计划，正确的是 A 需做风险评估B 需进行培训和应急演练C 必要时与当地公安，消防机构联系D 以上都是','答案：D','1'),(161,'10','1','Timlong同志的学习经历A、Guanmiao中心小学B、Guanmiao初级中学C、江苏省SYZXD、NUIST','答案：D','1'),(162,'10','1','关于生物安保与生物安全的描述，错误的是？A  均需要通过风险评估，确定安保、安全的级别。B  均要求管理的过程以记录的形式呈现并保留存档。C  均为防止病原体或毒素的无意失误使用。D  均要高度重视生物材料在机构内及机构间的转移和运输。','答案：C','1'),(163,'10','1','1.       关于生物安保计划，正确的是 A 需做风险评估B 需进行培训和应急演练C 必要时与当地公安，消防机构联系D 以上都是','答案：D','1'),(164,'10','1','Timlong同志的学习经历A、Guanmiao中心小学B、Guanmiao初级中学C、江苏省SYZXD、NUIST','答案：D','1'),(165,'10','1','关于生物安保与生物安全的描述，错误的是？A  均需要通过风险评估，确定安保、安全的级别。B  均要求管理的过程以记录的形式呈现并保留存档。C  均为防止病原体或毒素的无意失误使用。D  均要高度重视生物材料在机构内及机构间的转移和运输。','答案：C','1'),(166,'10','1','1.       关于生物安保计划，正确的是 A 需做风险评估B 需进行培训和应急演练C 必要时与当地公安，消防机构联系D 以上都是','答案：D','1'),(167,'10','1','Timlong同志的学习经历A、Guanmiao中心小学B、Guanmiao初级中学C、江苏省SYZXD、NUIST','答案：D','1'),(168,'10','1','关于生物安保与生物安全的描述，错误的是？A  均需要通过风险评估，确定安保、安全的级别。B  均要求管理的过程以记录的形式呈现并保留存档。C  均为防止病原体或毒素的无意失误使用。D  均要高度重视生物材料在机构内及机构间的转移和运输。','答案：C','1'),(169,'10','1','1.       关于生物安保计划，正确的是 A 需做风险评估B 需进行培训和应急演练C 必要时与当地公安，消防机构联系D 以上都是','答案：D','1'),(170,'9','3','《朝花夕拾》原名《_________》,是鲁迅的回忆性散文集,请简介一下其中的一篇（课内学过的除外）的主要内容 ：___________________________________.','《旧事重提》、示例：《二十四孝图》主要内容：童年时代的我和我的伙伴实在没有什么好画册可看.我拥有的最早一本画图本子只是《二十四孝图》.其中最使我不解,甚至于发生反感的,是\"老莱娱亲\"和\"郭巨埋儿\"两 件事.','3'),(171,'10','2','《鲁滨逊漂流记》的作者是英国作家________。','丹尼尔· 迪福','2'),(172,'10','2','在荒岛上鲁滨逊觉得_______最重要。','水、食物','3'),(173,'10','2','电路仿真模式实现方式有\n非结构化模式和结构化模式两种，时钟恢复方式有\n___________两种。','差分和自适应','4'),(174,'10','3','简述现浇肋梁楼盖的组成及荷载传递途径。','答：现浇肋梁楼盖由板、次梁和主梁组成，荷载的传递途径为荷载作用到板上，由板传递到次梁，由次梁传递到主梁，由主梁传递到柱或墙，再由柱或墙传递到基础，最后由基础传递到地基。','4'),(175,'10','3','什么是钢筋混凝土超静定结构的塑性内力重分布？','答：在混凝土超静定结构中，当某截面出现塑性铰后，引起结构内力的重分布，使结构中内力的分布规律与一般力学计算方法得到的内力(弹性理论得到的内力)不同。这种由于塑性铰的形成与开展而造成的超静定结构中的内力重新分布称为钢筋混凝土超静定结构的塑性内力重分布。','4'),(176,'10','3','单向板和双向板是如何区分的？','答：两对边支承的板为单向板。对于四边支承的板，当长边与短边长度之比小于或等于2.0时，按双向板考虑；当长边与短边长度之比大于2.0但小于3.0时，宜按双向板考虑，也可按单向板计算，但按沿短边方向受力的单向板计算时，应沿长边方向布置足够数量的构造钢筋；当长边与短边长度之比大于或等于3.0时，可按沿短边方向受力的单向板考虑。','5');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testpaper`
--

DROP TABLE IF EXISTS `testpaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` enum('1','2','3','4','5','6','7','8','9','10') DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `examtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testpaper`
--

LOCK TABLES `testpaper` WRITE;
/*!40000 ALTER TABLE `testpaper` DISABLE KEYS */;
INSERT INTO `testpaper` VALUES (2,'Nuist','2',0,'2018-03-06 04:12:00');
/*!40000 ALTER TABLE `testpaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `ACTIVE` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Timlong','123456',1),(3,'BigBoss','635241',1),(4,'大湿','mrdashi',1),(5,NULL,'236589',1),(8,'monkey_SUN','8888888',0),(9,'徐文龙','456456',0),(10,'tianlong','456456',0),(11,'xuwenlong','456456',0),(12,'wuwenlong','456456',0),(13,'jdskjf','456321',0),(14,'kvk','456321',0),(16,'hdjasdhf','456987',0),(36,'凡萨科技风你看完','456456',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-30 12:29:49
