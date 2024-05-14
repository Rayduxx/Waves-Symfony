-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2024 at 07:03 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `waves`
--

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `idc` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idItem` int(11) NOT NULL,
  `total` float NOT NULL,
  `dateC` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`idc`, `idUser`, `idItem`, `total`, `dateC`) VALUES
(58, 31, 2, 0, '2024-03-04 13:48:35'),
(59, 31, 2, 0, '2024-03-04 13:48:35'),
(60, 31, 2, 0, '2024-03-04 13:48:35'),
(61, 31, 3, 0, '2024-03-05 14:07:23');

-- --------------------------------------------------------

--
-- Table structure for table `commentaire`
--

CREATE TABLE `commentaire` (
  `id_Poste` int(11) DEFAULT NULL,
  `idComm` int(11) NOT NULL,
  `id` int(11) DEFAULT NULL,
  `comment` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `id_cours` int(11) NOT NULL,
  `titre_cours` varchar(255) NOT NULL,
  `duree_cours` varchar(255) NOT NULL,
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cours`
--

INSERT INTO `cours` (`id_cours`, `titre_cours`, `duree_cours`, `id`) VALUES
(2, 'iyed', '2 heures', 43),
(3, 'iyed', '3heures', 43),
(5, 'aaaaaaaaaaaaaa', '1heure', 43);

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `Eid` int(11) NOT NULL,
  `nomE` varchar(50) NOT NULL,
  `adrE` varchar(50) NOT NULL,
  `desc` varchar(50) NOT NULL,
  `date` varchar(20) NOT NULL,
  `image` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`Eid`, `nomE`, `adrE`, `desc`, `date`, `image`) VALUES
(6, 'WOOOW', 'Tunisssss', '24/02/2025', 'POPp', ''),
(7, 'Euphoric', 'Tozeuuuuur', '28/02/2024', 'AFRO', ''),
(23, 'AAAAAAAAA', 'centre urbain nord', 'DEMAIN MATIN', '20/20/20/20', '');

-- --------------------------------------------------------

--
-- Table structure for table `formation`
--

CREATE TABLE `formation` (
  `id` int(255) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `affiche` varchar(255) NOT NULL,
  `video` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `formation`
--

INSERT INTO `formation` (`id`, `titre`, `description`, `affiche`, `video`) VALUES
(43, 'mixage', 'Le mixage consiste à faire ressortir le meilleur d\'un enregistrement', 'http://127.0.0.1/img/4020137.jpg', 'lien'),
(46, 'soundengiiring', 'iyediyediyed', 'http://127.0.0.1/img/344477330_762103062322978_8140703151360853827_n.png', 'zzzzzzzzzzzz');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `itemID` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `auteur` varchar(255) NOT NULL,
  `prix` float NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`itemID`, `titre`, `description`, `auteur`, `prix`) VALUES
(1, 'Night after night', 'edit', 'Fideles', 25),
(2, 'Invisible (Remix)', 'Piano Cover', 'NTO', 26),
(3, 'Gamma', 'new release', 'NTO', 35),
(5, 'In Between', 'live at Munich audio', 'Jan Blomqvist', 10),
(1013, 'Strobe', 'new release', 'DeadMau5', 20),
(1014, 'Ghosts N Stuff', 'topline stem', 'DeadMau5', 5);

-- --------------------------------------------------------

--
-- Table structure for table `poste`
--

CREATE TABLE `poste` (
  `id_Poste` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `artiste` varchar(255) NOT NULL,
  `genre` enum('Tech House','Funky Techno','Minimal','Acid Techno') NOT NULL,
  `image` varchar(255) NOT NULL,
  `morceau` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `poste`
--

INSERT INTO `poste` (`id_Poste`, `titre`, `artiste`, `genre`, `image`, `morceau`, `description`) VALUES
(4, 'YURUBA', 'BENJEE', 'Tech House', 'http://127.0.0.1/img/376552818_6661904407223990_5046354086051299983_n.jpg', 'ajhsjsjznzk', 'it so lighthhzhszkjkszkjszk');

-- --------------------------------------------------------

--
-- Table structure for table `production`
--

CREATE TABLE `production` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `moodtag` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `production`
--

INSERT INTO `production` (`id`, `nom`, `genre`, `description`, `moodtag`) VALUES
(2, 'Euphoric', 'Afro-Tech', 'kugqjdhkjbnq sjghgdxbvn ;jxjghgbsdwn kxhxfjg, cv', 'Energie'),
(3, 'flenn', 'benflou', 'dsggdgds', 'Energie'),
(4, 'flenn', 'benflou', 'dsggdgds', 'Energie'),
(5, 'flenn', 'benflou', 'dsggdgds', 'Energie');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `id_utilisateur` int(11) DEFAULT NULL,
  `date_reservation` varchar(50) NOT NULL,
  `statut` varchar(50) DEFAULT NULL,
  `Eid` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id`, `id_utilisateur`, `date_reservation`, `statut`, `Eid`) VALUES
(1, NULL, 'aa', 'aa', NULL),
(2, NULL, 'aa', 'aa', NULL),
(3, NULL, 'BB', 'aBBa', NULL),
(4, NULL, 'ahmed', 'ahmed', NULL),
(5, NULL, 'AA', 'AA', NULL),
(6, NULL, 'AA', 'AA', NULL),
(13, NULL, '03/03/2024', 'Reservé', NULL),
(14, NULL, 'aaa', 'aaa', NULL),
(15, NULL, 'aa', '0', NULL),
(16, NULL, 'aa', '0', NULL),
(17, NULL, 'aa', 'OO', NULL),
(18, NULL, 'aa', 'aaaa', NULL),
(19, NULL, '20/20/20', 'AHMED', 0),
(20, NULL, 'aa', 'annulé', 7),
(21, NULL, 'aziz', 'reservé', 7),
(22, NULL, '20/20/2024', 'RESERVE', 6),
(23, NULL, '20/20/2024', 'Reservé', 7),
(24, NULL, '20/20/2024', 'Reservé', 7),
(25, NULL, 'Deamin', 'Reserve', 7),
(26, NULL, 'aBBBb', 'AAAAAA', 7),
(27, NULL, '2222', '1111', 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `numtel` int(11) NOT NULL,
  `role` varchar(255) NOT NULL DEFAULT 'User',
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `password`, `numtel`, `role`, `image`) VALUES
(48, 'Ala', 'Moussa', 'moussa.ala@esprit.tn', '123', 58409876, 'Admin', 'src\\main\\resources\\assets\\uploads\\86752492-5bad-4f6c-a597-bef4727e3c5d_profileAla.png'),
(71, 'Ahmed', 'Dhouioui', 'ahmed.douioui@esprit.tn', '123', 12345678, 'User', 'src\\main\\resources\\assets\\uploads\\94788b36-a49c-4d6f-9b45-8f8eee6c8031_376552818_6661904407223990_5046354086051299983_n.jpg'),
(72, 'Aziz', 'Salmi', 'aziz.salmi@esprit.tn', '1234', 12345678, 'User', ''),
(73, 'Hamza', 'Ben Jemia', 'hamza.benjemia@esprit.tn', '123', 12345678, 'Admin', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idc`),
  ADD KEY `fk_user` (`idUser`),
  ADD KEY `fk_item` (`idItem`);

--
-- Indexes for table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`idComm`),
  ADD KEY `FK_User` (`id`),
  ADD KEY `FK_Poste` (`id_Poste`);

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id_cours`),
  ADD KEY `fk_cours` (`id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`Eid`);

--
-- Indexes for table `formation`
--
ALTER TABLE `formation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`itemID`);

--
-- Indexes for table `poste`
--
ALTER TABLE `poste`
  ADD PRIMARY KEY (`id_Poste`);

--
-- Indexes for table `production`
--
ALTER TABLE `production`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_utilisateur` (`id_utilisateur`),
  ADD KEY `fk_eventId` (`Eid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `idc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `idComm` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cours`
--
ALTER TABLE `cours`
  MODIFY `id_cours` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `Eid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `formation`
--
ALTER TABLE `formation`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `itemID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1015;

--
-- AUTO_INCREMENT for table `poste`
--
ALTER TABLE `poste`
  MODIFY `id_Poste` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `production`
--
ALTER TABLE `production`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK_Poste` FOREIGN KEY (`id_Poste`) REFERENCES `poste` (`id_Poste`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_User` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_cours` FOREIGN KEY (`id`) REFERENCES `formation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
