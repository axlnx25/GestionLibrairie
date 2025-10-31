-- Désactiver les contraintes de clés étrangères pour l'import
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Utilisateur`
-- ----------------------------
DROP TABLE IF EXISTS `Utilisateur`;
CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(500) NOT NULL,
  `role_utilisateur` varchar(500) NOT NULL,
  `mot_de_passe` varchar(500) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Utilisateur` (`id_utilisateur`,`nom_utilisateur`,`role_utilisateur`,`mot_de_passe`) VALUES
(1,'Admin','Admin','12345'),
(3,'Mounira','Admin','442');

-- ----------------------------
-- Table structure for `Type_Article`
-- ----------------------------
DROP TABLE IF EXISTS `Type_Article`;
CREATE TABLE `Type_Article` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `type` varchar(500) NOT NULL,
  `code_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Type_Article` (`id_type`,`type`,`code_utilisateur`) VALUES
(1,'principale',1),
(2,'secondaire',1),
(3,'type-exemple',1);

-- ----------------------------
-- Table structure for `Article`
-- ----------------------------
DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `designation_article` varchar(500) NOT NULL,
  `quantite_article` int NOT NULL,
  `prix_vente_article` int NOT NULL,
  `code_utilisateur` int NOT NULL,
  `type_article` int NOT NULL,
  PRIMARY KEY (`id_article`),
  KEY `FK_Type` (`type_article`),
  CONSTRAINT `FK_Type` FOREIGN KEY (`type_article`) REFERENCES `Type_Article` (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Article` (`id_article`,`designation_article`,`quantite_article`,`prix_vente_article`,`code_utilisateur`,`type_article`) VALUES
(1,'bic',158,200,1,1),
(2,'stylo',37,400,1,1),
(3,'gomme',2,100,1,2),
(4,'cahier',10,1000,1,1),
(5,'cahier300P',10,800,1,1);

-- ----------------------------
-- Table structure for `Approvisionnement`
-- ----------------------------
DROP TABLE IF EXISTS `Approvisionnement`;
CREATE TABLE `Approvisionnement` (
  `id_approvisionnement` int NOT NULL AUTO_INCREMENT,
  `stock_origine` varchar(500) NOT NULL,
  `quantite_approvisionnement` int NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `montant_approvisionnement` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  `numero_article` int NOT NULL,
  PRIMARY KEY (`id_approvisionnement`),
  KEY `FK_CLES` (`numero_utilisateur`),
  KEY `FK_CLES2` (`numero_article`),
  CONSTRAINT `FK_CLES` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`),
  CONSTRAINT `FK_CLES2` FOREIGN KEY (`numero_article`) REFERENCES `Article` (`id_article`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Approvisionnement` (`id_approvisionnement`,`stock_origine`,`quantite_approvisionnement`,`date_approvisionnement`,`montant_approvisionnement`,`numero_utilisateur`,`numero_article`) VALUES
(1,'EAS',20,'2025-10-12',10,1,1),
(2,'EAS',10,'2025-10-12',100,1,1),
(3,'Autres',12,'2025-10-12',2000,1,3),
(4,'EAS',2,'2025-10-12',1000,1,5);

-- ----------------------------
-- Table structure for `Depense`
-- ----------------------------
DROP TABLE IF EXISTS `Depense`;
CREATE TABLE `Depense` (
  `id_depense` int NOT NULL AUTO_INCREMENT,
  `designation_depense` varchar(500) NOT NULL,
  `montant_depense` int NOT NULL,
  `date_depense` date NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_depense`),
  KEY `numero_utilisateur` (`numero_utilisateur`),
  CONSTRAINT `Depense_ibfk_1` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Depense` (`id_depense`,`designation_depense`,`montant_depense`,`date_depense`,`numero_utilisateur`) VALUES
(1,'courant',100,'2025-10-12',1),
(2,'payement essence',2000,'2025-10-12',1);

-- ----------------------------
-- Table structure for `Vente`
-- ----------------------------
DROP TABLE IF EXISTS `Vente`;
CREATE TABLE `Vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `quantite_vendu` int NOT NULL,
  `date_vente` date NOT NULL,
  `remise_vente` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `Vente_Utilisateur_FK` (`numero_utilisateur`),
  CONSTRAINT `Vente_Utilisateur_FK` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Vente` (`id_vente`,`quantite_vendu`,`date_vente`,`remise_vente`,`numero_utilisateur`) VALUES
(1,0,'2025-10-12',0,1),
(2,0,'2025-10-12',0,1),
(3,0,'2025-10-12',100,1),
(4,0,'2025-10-12',0,1),
(5,0,'2025-10-12',0,1),
(6,0,'2025-10-12',0,1),
(7,0,'2025-10-12',0,1),
(8,0,'2025-10-12',100,1);

-- ----------------------------
-- Table structure for `Facture`
-- ----------------------------
DROP TABLE IF EXISTS `Facture`;
CREATE TABLE `Facture` (
  `id_vente` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite_vendu` int NOT NULL,
  PRIMARY KEY (`id_vente`,`id_article`),
  KEY `fk_article` (`id_article`),
  CONSTRAINT `fk_article` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`),
  CONSTRAINT `fk_vente` FOREIGN KEY (`id_vente`) REFERENCES `Vente` (`id_vente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Facture` (`id_vente`,`id_article`,`quantite_vendu`) VALUES
(1,1,2),
(2,1,5),
(3,2,50),
(4,1,10),
(5,2,3),
(6,3,12),
(6,4,2),
(7,1,30),
(8,1,2),
(8,5,2);

-- Réactiver les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS=1;
-- MariaDB dump compatible
-- GestionLibrairie
-- Generated for MariaDB 10.11

-- Désactiver temporairement les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS=0;

-- Table structure for `Utilisateur`
DROP TABLE IF EXISTS `Utilisateur`;
CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(500) NOT NULL,
  `role_utilisateur` varchar(500) NOT NULL,
  `mot_de_passe` varchar(500) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Utilisateur` VALUES
(1,'Admin','Admin','12345'),
(3,'Mounira','Admin','442');

-- Table structure for `Type_Article`
DROP TABLE IF EXISTS `Type_Article`;
CREATE TABLE `Type_Article` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `type` varchar(500) NOT NULL,
  `code_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Type_Article` VALUES
(1,'principale',1),
(2,'secondaire',1),
(3,'type-exemple',1);

-- Table structure for `Article`
DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `designation_article` varchar(500) NOT NULL,
  `quantite_article` int NOT NULL,
  `prix_vente_article` int NOT NULL,
  `code_utilisateur` int NOT NULL,
  `type_article` int NOT NULL,
  PRIMARY KEY (`id_article`),
  KEY `FK_Type` (`type_article`),
  CONSTRAINT `FK_Type` FOREIGN KEY (`type_article`) REFERENCES `Type_Article` (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Article` VALUES
(1,'bic',158,200,1,1),
(2,'stylo',37,400,1,1),
(3,'gomme',2,100,1,2),
(4,'cahier',10,1000,1,1),
(5,'cahier300P',10,800,1,1);

-- Table structure for `Vente`
DROP TABLE IF EXISTS `Vente`;
CREATE TABLE `Vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `quantite_vendu` int NOT NULL,
  `date_vente` date NOT NULL,
  `remise_vente` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `Vente_Utilisateur_FK` (`numero_utilisateur`),
  CONSTRAINT `Vente_Utilisateur_FK` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Vente` VALUES
(1,0,'2025-10-12',0,1),
(2,0,'2025-10-12',0,1),
(3,0,'2025-10-12',100,1),
(4,0,'2025-10-12',0,1),
(5,0,'2025-10-12',0,1),
(6,0,'2025-10-12',0,1),
(7,0,'2025-10-12',0,1),
(8,0,'2025-10-12',100,1);

-- Table structure for `Approvisionnement`
DROP TABLE IF EXISTS `Approvisionnement`;
CREATE TABLE `Approvisionnement` (
  `id_approvisionnement` int NOT NULL AUTO_INCREMENT,
  `stock_origine` varchar(500) NOT NULL,
  `quantite_approvisionnement` int NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `montant_approvisionnement` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  `numero_article` int NOT NULL,
  PRIMARY KEY (`id_approvisionnement`),
  KEY `FK_CLES` (`numero_utilisateur`),
  KEY `FK_CLES2` (`numero_article`),
  CONSTRAINT `FK_CLES` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`),
  CONSTRAINT `FK_CLES2` FOREIGN KEY (`numero_article`) REFERENCES `Article` (`id_article`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Approvisionnement` VALUES
(1,'EAS',20,'2025-10-12',10,1,1),
(2,'EAS',10,'2025-10-12',100,1,1),
(3,'Autres',12,'2025-10-12',2000,1,3),
(4,'EAS',2,'2025-10-12',1000,1,5);

-- Table structure for `Depense`
DROP TABLE IF EXISTS `Depense`;
CREATE TABLE `Depense` (
  `id_depense` int NOT NULL AUTO_INCREMENT,
  `designation_depense` varchar(500) NOT NULL,
  `montant_depense` int NOT NULL,
  `date_depense` date NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_depense`),
  KEY `numero_utilisateur` (`numero_utilisateur`),
  CONSTRAINT `Depense_ibfk_1` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Depense` VALUES
(1,'courant',100,'2025-10-12',1),
(2,'payement essence',2000,'2025-10-12',1);

-- Table structure for `Facture`
DROP TABLE IF EXISTS `Facture`;
CREATE TABLE `Facture` (
  `id_vente` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite_vendu` int NOT NULL,
  PRIMARY KEY (`id_vente`,`id_article`),
  KEY `fk_article` (`id_article`),
  CONSTRAINT `fk_article` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`),
  CO
-- MariaDB dump compatible

CREATE DATABASE IF NOT EXISTS GestionLibrairie;
USE GestionLibrairie;

-- ===============================
-- Table: Utilisateur
-- ===============================
DROP TABLE IF EXISTS `Utilisateur`;
CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(500) NOT NULL,
  `role_utilisateur` varchar(500) NOT NULL,
  `mot_de_passe` varchar(500) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Utilisateur` VALUES
(1,'Admin','Admin','12345'),
(3,'Mounira','Admin','442');

-- ===============================
-- Table: Type_Article
-- ===============================
DROP TABLE IF EXISTS `Type_Article`;
CREATE TABLE `Type_Article` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `type` varchar(500) NOT NULL,
  `code_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Type_Article` VALUES
(1,'principale',1),
(2,'secondaire',1),
(3,'type-exemple',1);

-- ===============================
-- Table: Article
-- ===============================
DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `designation_article` varchar(500) NOT NULL,
  `quantite_article` int NOT NULL,
  `prix_vente_article` int NOT NULL,
  `code_utilisateur` int NOT NULL,
  `type_article` int NOT NULL,
  PRIMARY KEY (`id_article`),
  KEY `FK_Type` (`type_article`),
  CONSTRAINT `FK_Type` FOREIGN KEY (`type_article`) REFERENCES `Type_Article` (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Article` VALUES
(1,'bic',158,200,1,1),
(2,'stylo',37,400,1,1),
(3,'gomme',2,100,1,2),
(4,'cahier',10,1000,1,1),
(5,'cahier300P',10,800,1,1);

-- ===============================
-- Table: Approvisionnement
-- ===============================
DROP TABLE IF EXISTS `Approvisionnement`;
CREATE TABLE `Approvisionnement` (
  `id_approvisionnement` int NOT NULL AUTO_INCREMENT,
  `stock_origine` varchar(500) NOT NULL,
  `quantite_approvisionnement` int NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `montant_approvisionnement` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  `numero_article` int NOT NULL,
  PRIMARY KEY (`id_approvisionnement`),
  KEY `FK_CLES` (`numero_utilisateur`),
  KEY `FK_CLES2` (`numero_article`),
  CONSTRAINT `FK_CLES` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`),
  CONSTRAINT `FK_CLES2` FOREIGN KEY (`numero_article`) REFERENCES `Article` (`id_article`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Approvisionnement` VALUES
(1,'EAS',20,'2025-10-12',10,1,1),
(2,'EAS',10,'2025-10-12',100,1,1),
(3,'Autres',12,'2025-10-12',2000,1,3),
(4,'EAS',2,'2025-10-12',1000,1,5);

-- ===============================
-- Table: Depense
-- ===============================
DROP TABLE IF EXISTS `Depense`;
CREATE TABLE `Depense` (
  `id_depense` int NOT NULL AUTO_INCREMENT,
  `designation_depense` varchar(500) NOT NULL,
  `montant_depense` int NOT NULL,
  `date_depense` date NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_depense`),
  KEY `numero_utilisateur` (`numero_utilisateur`),
  CONSTRAINT `Depense_ibfk_1` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Depense` VALUES
(1,'courant',100,'2025-10-12',1),
(2,'payement essence',2000,'2025-10-12',1);

-- ===============================
-- Table: Vente
-- ===============================
DROP TABLE IF EXISTS `Vente`;
CREATE TABLE `Vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `quantite_vendu` int NOT NULL,
  `date_vente` date NOT NULL,
  `remise_vente` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `Vente_Utilisateur_FK` (`numero_utilisateur`),
  CONSTRAINT `Vente_Utilisateur_FK` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Vente` VALUES
(1,0,'2025-10-12',0,1),
(2,0,'2025-10-12',0,1),
(3,0,'2025-10-12',100,1),
(4,0,'2025-10-12',0,1),
(5,0,'2025-10-12',0,1),
(6,0,'2025-10-12',0,1),
(7,0,'2025-10-12',0,1),
(8,0,'2025-10-12',100,1);

-- ===============================
-- Table: Facture
-- ===============================
DROP TABLE IF EXISTS `Facture`;
CREATE TABLE `Facture` (
  `id_vente` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite_vendu` int NOT NULL,
  PRIMARY KEY (`id_vente`,`id_article`),
  KEY `fk_article` (`id_article`),
  CONSTRAINT `fk_article` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`),
  CONSTRAINT `fk_vente` FOREIGN KEY (`id_vente`) REFERENCES `Vente` (`id_vente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Facture` VALUES
(1,1,2),(2,1,5),(3,2,50),(4,1,10),(5,2,3),(6,3,12),(6,4,2),(7,1,30),(8,1,2),(8,5,2);
-- MySQL/MariaDB dump compatible MariaDB 10.11
-- Database: GestionLibrairie

CREATE DATABASE IF NOT EXISTS GestionLibrairie;
USE GestionLibrairie;

-- Table structure for table `Approvisionnement`
DROP TABLE IF EXISTS `Approvisionnement`;
CREATE TABLE `Approvisionnement` (
  `id_approvisionnement` int NOT NULL AUTO_INCREMENT,
  `stock_origine` varchar(500) NOT NULL,
  `quantite_approvisionnement` int NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `montant_approvisionnement` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  `numero_article` int NOT NULL,
  PRIMARY KEY (`id_approvisionnement`),
  KEY `FK_CLES` (`numero_utilisateur`),
  KEY `FK_CLES2` (`numero_article`),
  CONSTRAINT `FK_CLES` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`),
  CONSTRAINT `FK_CLES2` FOREIGN KEY (`numero_article`) REFERENCES `Article` (`id_article`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `Approvisionnement`
INSERT INTO `Approvisionnement` VALUES 
(1,'EAS',20,'2025-10-12',10,1,1),
(2,'EAS',10,'2025-10-12',100,1,1),
(3,'Autres',12,'2025-10-12',2000,1,3),
(4,'EAS',2,'2025-10-12',1000,1,5);

-- Table structure for table `Article`
DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `designation_article` varchar(500) NOT NULL,
  `quantite_article` int NOT NULL,
  `prix_vente_article` int NOT NULL,
  `code_utilisateur` int NOT NULL,
  `type_article` int NOT NULL,
  PRIMARY KEY (`id_article`),
  KEY `FK_Type` (`type_article`),
  CONSTRAINT `FK_Type` FOREIGN KEY (`type_article`) REFERENCES `Type_Article` (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Article` VALUES 
(1,'bic',158,200,1,1),
(2,'stylo',37,400,1,1),
(3,'gomme',2,100,1,2),
(4,'cahier',10,1000,1,1),
(5,'cahier300P',10,800,1,1);

-- Table structure for table `Depense`
DROP TABLE IF EXISTS `Depense`;
CREATE TABLE `Depense` (
  `id_depense` int NOT NULL AUTO_INCREMENT,
  `designation_depense` varchar(500) NOT NULL,
  `montant_depense` int NOT NULL,
  `date_depense` date NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_depense`),
  KEY `numero_utilisateur` (`numero_utilisateur`),
  CONSTRAINT `Depense_ibfk_1` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Depense` VALUES 
(1,'courant',100,'2025-10-12',1),
(2,'payement essence',2000,'2025-10-12',1);

-- Table structure for table `Facture`
DROP TABLE IF EXISTS `Facture`;
CREATE TABLE `Facture` (
  `id_vente` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite_vendu` int NOT NULL,
  PRIMARY KEY (`id_vente`,`id_article`),
  KEY `fk_article` (`id_article`),
  CONSTRAINT `fk_article` FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`),
  CONSTRAINT `fk_vente` FOREIGN KEY (`id_vente`) REFERENCES `Vente` (`id_vente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Facture` VALUES 
(1,1,2),(2,1,5),(3,2,50),(4,1,10),(5,2,3),
(6,3,12),(6,4,2),(7,1,30),(8,1,2),(8,5,2);

-- Table structure for table `Type_Article`
DROP TABLE IF EXISTS `Type_Article`;
CREATE TABLE `Type_Article` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `type` varchar(500) NOT NULL,
  `code_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Type_Article` VALUES 
(1,'principale',1),
(2,'secondaire',1),
(3,'type-exemple',1);

-- Table structure for table `Utilisateur`
DROP TABLE IF EXISTS `Utilisateur`;
CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(500) NOT NULL,
  `role_utilisateur` varchar(500) NOT NULL,
  `mot_de_passe` varchar(500) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Utilisateur` VALUES 
(1,'Admin','Admin','12345'),
(3,'Mounira','Admin','442');

-- Table structure for table `Vente`
DROP TABLE IF EXISTS `Vente`;
CREATE TABLE `Vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `quantite_vendu` int NOT NULL,
  `date_vente` date NOT NULL,
  `remise_vente` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `Vente_Utilisateur_FK` (`numero_utilisateur`),
  CONSTRAINT `Vente_Utilisateur_FK` FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Vente` VALUES 
(1,0,'2025-10-12',0,1),(2,0,'2025-10-12',0,1),
(3,0,'2025-10-12',100,1),(4,0,'2025-10-12',0,1),
(5,0,'2025-10-12',0,1),(6,0,'2025-10-12',0,1),
(7,0,'2025-10-12',0,1),(8,0,'2025-10-12',100,1);
-- Base de données : GestionLibrairie
USE GestionLibrairie;

-- --------------------------------------------------------
-- Table structure for `Utilisateur`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `Utilisateur`;
CREATE TABLE `Utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(100) NOT NULL,
  `prenom_utilisateur` varchar(100) NOT NULL,
  `email_utilisateur` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for `Article`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `Article`;
CREATE TABLE `Article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) NOT NULL,
  `auteur` varchar(200) NOT NULL,
  `prix` int NOT NULL,
  `quantite_stock` int NOT NULL,
  PRIMARY KEY (`id_article`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for `Approvisionnement`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `Approvisionnement`;
CREATE TABLE `Approvisionnement` (
  `id_approvisionnement` int NOT NULL AUTO_INCREMENT,
  `stock_origine` varchar(500) NOT NULL,
  `quantite_approvisionnement` int NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `montant_approvisionnement` int NOT NULL,
  `numero_utilisateur` int NOT NULL,
  `numero_article` int NOT NULL,
  PRIMARY KEY (`id_approvisionnement`),
  FOREIGN KEY (`numero_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`numero_article`) REFERENCES `Article` (`id_article`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for `Vente`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `Vente`;
CREATE TABLE `Vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `total_vente` int NOT NULL,
  `date_vente` date NOT NULL,
  `montant_recu` int NOT NULL,
  `id_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  FOREIGN KEY (`id_utilisateur`) REFERENCES `Utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for `Facture`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `Facture`;
CREATE TABLE `Facture` (
  `id_vente` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite_vendu` int NOT NULL,
  PRIMARY KEY (`id_vente`,`id_article`),
  FOREIGN KEY (`id_article`) REFERENCES `Article` (`id_article`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`id_vente`) REFERENCES `Vente` (`id_vente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Données exemple pour Vente
-- --------------------------------------------------------
INSERT INTO `Vente` (`id_vente`, `total_vente`, `date_vente`, `montant_recu`, `id_utilisateur`) VALUES
(1, 0, '2025-10-12', 0, 1),
(2, 0, '2025-10-12', 0, 1),
(3, 0, '2025-10-12', 100, 1),
(4, 0, '2025-10-12', 0, 1),
(5, 0, '2025-10-12', 0, 1),
(6, 0, '2025-10-12', 0, 1),
(7, 0, '2025-10-12', 0, 1),
(8, 0, '2025-10-12', 100, 1);

