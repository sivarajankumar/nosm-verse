# Sequel Pro dump
# Version 1191
# http://code.google.com/p/sequel-pro
#
# Host: 127.0.0.1 (MySQL 5.1.39)
# Database: ariadne
# Generation Time: 2009-11-09 02:01:51 -0500
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table assetmapnode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `assetmapnode`;

CREATE TABLE `assetmapnode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mnodeid` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `name` mediumtext,
  `target` mediumtext,
  `val` text,
  `ui_pairs` longtext,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;

LOCK TABLES `assetmapnode` WRITE;
/*!40000 ALTER TABLE `assetmapnode` DISABLE KEYS */;
INSERT INTO `assetmapnode` (`id`,`mnodeid`,`type_id`,`name`,`target`,`val`,`ui_pairs`)
VALUES
	(1,8336,1,'shapeChoice','0','run scene shapeChoice',''),
	(5,8336,3,'master','8336','shapeChoice,spasm',''),
	(54,8336,4,'shrug','sanchorelaxo Algoma','1~loop~1~',''),
	
/*!40000 ALTER TABLE `assetmapnode` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table assettype
# ------------------------------------------------------------

DROP TABLE IF EXISTS `assettype`;

CREATE TABLE `assettype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aname` tinytext NOT NULL,
  `adesc` mediumtext,
  `ui_help_url` mediumtext,
  `ui_help_desc` mediumtext,
  `ui_cat` tinytext,
  `sl_inv_code` int(2) DEFAULT '6',
  `parcel_media` tinyint(1) DEFAULT '0',
  `loop` tinyint(1) DEFAULT '0',
  `duration` tinyint(1) DEFAULT '0',
  `prim` tinyint(1) DEFAULT '0',
  `object` tinyint(1) DEFAULT '0',
  `media` tinyint(1) DEFAULT '0',
  `action` tinyint(1) DEFAULT '0',
  `text` tinyint(1) DEFAULT '0',
  `mut_excl` tinyint(1) DEFAULT '0',
  `persist` tinyint(1) DEFAULT '0',
  `chat` tinyint(1) DEFAULT '0',
  `clickable` tinyint(1) DEFAULT '0',
  `controllable` tinyint(1) DEFAULT '0',
  `restrainable` tinyint(1) DEFAULT '0',
  `url` tinyint(1) DEFAULT '0',
  `mime` tinyint(1) DEFAULT '0',
  `system` tinyint(1) DEFAULT '0',
  `hidden` tinyint(1) DEFAULT '0',
  `admin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

LOCK TABLES `assettype` WRITE;
/*!40000 ALTER TABLE `assettype` DISABLE KEYS */;
INSERT INTO `assettype` (`id`,`aname`,`adesc`,`ui_help_url`,`ui_help_desc`,`ui_cat`,`sl_inv_code`,`parcel_media`,`loop`,`duration`,`prim`,`object`,`media`,`action`,`text`,`mut_excl`,`persist`,`chat`,`clickable`,`controllable`,`restrainable`,`url`,`mime`,`system`,`hidden`,`admin`)
VALUES
	(1,'SLChat','Use a chat channel (talk to in-world objects)',NULL,NULL,'comm',-1,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0),
	(2,'VPDText','Show text on parcel viewer(s)',NULL,NULL,'parcel',-1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0),
	(3,'SLInnerSequence','Used to sequence assets',NULL,NULL,'',-1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0),
	(4,'SLAnimation','Animate the avatar(s)',NULL,NULL,'action',20,0,1,1,1,0,1,1,0,0,1,0,0,1,0,0,0,0,0,0),
	(5,'SLBodypart','Give avatar(s) a body part',NULL,NULL,'inv',13,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(6,'SLHud','Give avatar(s) a HUD',NULL,NULL,'inv',90,0,0,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0),
	(7,'SLIM','Send an Instant Message (IM)',NULL,NULL,'comm',-1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0),
	(8,'SLExtFeedObject','Trigger an external game',NULL,NULL,'misc',92,1,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0),
	(9,'SLLandmark','Give avatar(s) a landmark',NULL,NULL,'inv',3,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(10,'SLMove','Move avatar(s)',NULL,NULL,'action',-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0),
	(11,'SLNotecard','Give avatar(s) a notecard',NULL,NULL,'inv',7,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(12,'SLObject','Give avatar(s) an object',NULL,NULL,'inv',6,0,0,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0),
	(13,'SLPackage','Give avatar(s) a package (crate)',NULL,NULL,'inv',91,0,0,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0),
	(14,'SLParticleSystem','Trigger a particle manifestation',NULL,NULL,'misc',-1,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0),
	(16,'SLSound','Play a sound',NULL,NULL,'action',1,0,1,1,1,0,1,1,0,0,1,0,0,1,0,0,0,0,0,0),
	(17,'SLTexture','Give avatar(s) a texture',NULL,NULL,'inv',0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(18,'VPDImage','Show a single frame of a DICOM set on parcel viewer(s)',NULL,NULL,'parcel',-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0),
	(19,'VPDMedia','Run a DICOM set sequence on parcel viewer(s)',NULL,NULL,'parcel',-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0),
	(20,'SLSnapshot','Give avatar(s) a snapshot object',NULL,NULL,'inv',-1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(21,'SLScript','Give avatar(s) a script object',NULL,NULL,'inv',10,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0),
	(22,'SLClothing','Give avatar(s) some item of clothing',NULL,NULL,'inv',5,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0),
	(23,'SLGoogleAPIImage','Show a Google Chart image on parcel viewer(s)',NULL,NULL,'parcel',-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0),
	(24,'SLGoogleAPIHTML','Show a Google Map on parcel viewer(s)',NULL,NULL,'parcel',-1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0),
	(25,'SLAmazonMapImage','Show a Second Life region map on parcel viewer(s)',NULL,NULL,'parcel',-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0),
	(28,'VPMannequin','program mannequin for response',NULL,NULL,'misc',92,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0),
	(31,'SLAudio',NULL,NULL,NULL,NULL,6,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0);

/*!40000 ALTER TABLE `assettype` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table assettypeattribs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `assettypeattribs`;

CREATE TABLE `assettypeattribs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `name` tinytext,
  `val` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

LOCK TABLES `assettypeattribs` WRITE;
/*!40000 ALTER TABLE `assettypeattribs` DISABLE KEYS */;
INSERT INTO `assettypeattribs` (`id`,`type_id`,`name`,`val`)
VALUES
	(1,4,'spasm','spasm'),
	(4,1,'Aquarium','9993'),
	(5,1,'Asylum','9993'),
	(6,1,'Barnyard','9993'),
	(7,1,'Bus Station','9993'),
	(8,1,'coal','9993'),
	(9,1,'door','9993'),
	(10,1,'finish','9993'),
	(11,1,'Hideaway','9993'),
	(12,1,'Hotel Lobby','9993'),
	(13,1,'molecules','9993'),
	(14,1,'Movie Theater','9993'),
	(15,1,'Old Mill','9993'),
	(16,1,'Panocube15','9993'),
	(17,1,'Panocube30','9993'),
	(18,1,'Panocube50','9993'),
	(19,1,'salt','9993'),
	(20,1,'screens','9993'),
	(21,1,'shapechoice','9993'),
	(22,1,'SJ Maze','9993'),
	(23,1,'Stage','9993'),
	(24,1,'Stonehenge','9993'),
	(25,1,'water','9993'),
	(26,1,'SHELL black','9993'),
	(27,1,'SHELL blue','9993'),
	(28,1,'SHELL Build Platform','9993'),
	(29,1,'SHELL Function Room','9993'),
	(30,1,'SHELL Grass Platform','9993'),
	(31,1,'SHELL Holodeck 15','9993'),
	(32,1,'SHELL Holodeck 20','9993'),
	(33,1,'SHELL Holodeck 40','9993'),
	(34,1,'SHELL Industrial','9993'),
	(35,1,'SHELL Modern','9993'),
	(36,1,'SHELL red','9993'),
	(37,1,'SHELL TARDIS','9993'),
	(38,1,'SHELL white','9993'),
	(39,11,'again',''),
	(40,5,'eyes2',''),
	(41,22,'crapShirt',''),
	(42,22,'hair01',''),
	(43,13,'Tester',''),
	(44,22,'jacket01',''),
	(45,6,'Tester',''),
	(46,17,'maple3',''),
	(47,21,'New Script',''),
	(48,11,'noteTest01',''),
	(49,22,'oldsocks',''),
	(50,17,'papaya2',''),
	(51,12,'TestObject','');

/*!40000 ALTER TABLE `assettypeattribs` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sl_first_name` tinytext NOT NULL,
  `sl_last_name` tinytext NOT NULL,
  `rl_first_name` tinytext,
  `rl_last_name` tinytext,
  `sl_player_key` mediumtext,
  `is_super` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`sl_first_name`,`sl_last_name`,`rl_first_name`,`rl_last_name`,`sl_player_key`,`is_super`)
VALUES
	(1,'sanchorelaxo','Algoma','Roger','Sanche','XXXXXXXX-8ada-400b-8790-XXXXXXXXXXXX',1),
	(2,'rache','Helendale','Rachel','Ellaway','XXXXXXXX-0614-4f8c-9a2d-XXXXXXXXXXXX',0),

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
