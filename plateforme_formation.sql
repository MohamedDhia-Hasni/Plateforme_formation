-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 13 déc. 2024 à 00:50
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `plateforme_formation`
--

-- --------------------------------------------------------

--
-- Structure de la table `formations`
--

CREATE TABLE `formations` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `formateurId` int(11) DEFAULT NULL,
  `prix` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `inscriptions`
--

CREATE TABLE `inscriptions` (
  `etudiantId` int(11) NOT NULL,
  `formationId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `motDePasse` varchar(255) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `formations`
--
ALTER TABLE `formations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `formateurId` (`formateurId`);

--
-- Index pour la table `inscriptions`
--
ALTER TABLE `inscriptions`
  ADD PRIMARY KEY (`etudiantId`,`formationId`),
  ADD KEY `formationId` (`formationId`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `formations`
--
ALTER TABLE `formations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `formations`
--
ALTER TABLE `formations`
  ADD CONSTRAINT `formations_ibfk_1` FOREIGN KEY (`formateurId`) REFERENCES `utilisateurs` (`id`);

--
-- Contraintes pour la table `inscriptions`
--
ALTER TABLE `inscriptions`
  ADD CONSTRAINT `inscriptions_ibfk_1` FOREIGN KEY (`etudiantId`) REFERENCES `utilisateurs` (`id`),
  ADD CONSTRAINT `inscriptions_ibfk_2` FOREIGN KEY (`formationId`) REFERENCES `formations` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
