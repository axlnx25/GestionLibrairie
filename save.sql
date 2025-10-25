-- ===========================================
--  SAUVEGARDE DE LA BASE GestionLibrairie
--  Auteur : Faïssale OUATTARA
--  Version corrigée et nettoyée
-- ===========================================

-- Création de la base
CREATE DATABASE IF NOT EXISTS GestionLibrairie;
USE GestionLibrairie;

-- Désactivation temporaire des contraintes
SET FOREIGN_KEY_CHECKS=0;

-- ===========================================
-- TABLE : Utilisateur
-- ===========================================
DROP TABLE IF EXISTS Utilisateur;
CREATE TABLE Utilisateur (
  id_utilisateur INT NOT NULL AUTO_INCREMENT,
  nom_utilisateur VARCHAR(100) NOT NULL,
  role_utilisateur VARCHAR(100) NOT NULL,
  mot_de_passe VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_utilisateur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Utilisateur VALUES
(1,'Admin','Admin','12345'),
(2,'Mounira','Admin','442');

-- ===========================================
-- TABLE : Type_Article
-- ===========================================
DROP TABLE IF EXISTS Type_Article;
CREATE TABLE Type_Article (
  id_type INT NOT NULL AUTO_INCREMENT,
  type VARCHAR(100) NOT NULL,
  code_utilisateur INT NOT NULL,
  PRIMARY KEY (id_type),
  FOREIGN KEY (code_utilisateur) REFERENCES Utilisateur(id_utilisateur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Type_Article VALUES
(1,'principale',1),
(2,'secondaire',1),
(3,'type-exemple',1);

-- ===========================================
-- TABLE : Article
-- ===========================================
DROP TABLE IF EXISTS Article;
CREATE TABLE Article (
  id_article INT NOT NULL AUTO_INCREMENT,
  designation_article VARCHAR(200) NOT NULL,
  quantite_article INT NOT NULL,
  prix_vente_article INT NOT NULL,
  code_utilisateur INT NOT NULL,
  type_article INT NOT NULL,
  PRIMARY KEY (id_article),
  FOREIGN KEY (code_utilisateur) REFERENCES Utilisateur(id_utilisateur),
  FOREIGN KEY (type_article) REFERENCES Type_Article(id_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Article VALUES
(1,'bic',158,200,1,1),
(2,'stylo',37,400,1,1),
(3,'gomme',2,100,1,2),
(4,'cahier',10,1000,1,1),
(5,'cahier300P',10,800,1,1);

-- ===========================================
-- TABLE : Approvisionnement
-- ===========================================
DROP TABLE IF EXISTS Approvisionnement;
CREATE TABLE Approvisionnement (
  id_approvisionnement INT NOT NULL AUTO_INCREMENT,
  stock_origine VARCHAR(100) NOT NULL,
  quantite_approvisionnement INT NOT NULL,
  date_approvisionnement DATE NOT NULL,
  montant_approvisionnement INT NOT NULL,
  numero_utilisateur INT NOT NULL,
  numero_article INT NOT NULL,
  PRIMARY KEY (id_approvisionnement),
  FOREIGN KEY (numero_utilisateur) REFERENCES Utilisateur(id_utilisateur),
  FOREIGN KEY (numero_article) REFERENCES Article(id_article)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Approvisionnement VALUES
(1,'EAS',20,'2025-10-12',10,1,1),
(2,'EAS',10,'2025-10-12',100,1,1),
(3,'Autres',12,'2025-10-12',2000,1,3),
(4,'EAS',2,'2025-10-12',1000,1,5);

-- ===========================================
-- TABLE : Depense
-- ===========================================
DROP TABLE IF EXISTS Depense;
CREATE TABLE Depense (
  id_depense INT NOT NULL AUTO_INCREMENT,
  designation_depense VARCHAR(200) NOT NULL,
  montant_depense INT NOT NULL,
  date_depense DATE NOT NULL,
  numero_utilisateur INT NOT NULL,
  PRIMARY KEY (id_depense),
  FOREIGN KEY (numero_utilisateur) REFERENCES Utilisateur(id_utilisateur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Depense VALUES
(1,'courant',100,'2025-10-12',1),
(2,'payement essence',2000,'2025-10-12',1);

-- ===========================================
-- TABLE : Vente
-- ===========================================
DROP TABLE IF EXISTS Vente;
CREATE TABLE Vente (
  id_vente INT NOT NULL AUTO_INCREMENT,
  quantite_vendu INT NOT NULL,
  date_vente DATE NOT NULL,
  remise_vente INT NOT NULL,
  numero_utilisateur INT NOT NULL,
  PRIMARY KEY (id_vente),
  FOREIGN KEY (numero_utilisateur) REFERENCES Utilisateur(id_utilisateur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Vente VALUES
(1,0,'2025-10-12',0,1),
(2,0,'2025-10-12',0,1),
(3,0,'2025-10-12',100,1),
(4,0,'2025-10-12',0,1),
(5,0,'2025-10-12',0,1),
(6,0,'2025-10-12',0,1),
(7,0,'2025-10-12',0,1),
(8,0,'2025-10-12',100,1);

-- ===========================================
-- TABLE : Facture
-- ===========================================
DROP TABLE IF EXISTS Facture;
CREATE TABLE Facture (
  id_vente INT NOT NULL,
  id_article INT NOT NULL,
  quantite_vendu INT NOT NULL,
  PRIMARY KEY (id_vente, id_article),
  FOREIGN KEY (id_vente) REFERENCES Vente(id_vente),
  FOREIGN KEY (id_article) REFERENCES Article(id_article)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO Facture VALUES
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

-- Réactivation des contraintes
SET FOREIGN_KEY_CHECKS=1;

-- ===========================================
-- FIN DU SCRIPT
-- ===========================================
