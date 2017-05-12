-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2017 at 01:08 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ini`
--

-- --------------------------------------------------------

--
-- Table structure for table `curhat`
--

CREATE TABLE `curhat` (
  `id` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `judul_curhat` varchar(255) DEFAULT NULL,
  `isi_curhat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `curhat`
--

INSERT INTO `curhat` (`id`, `userID`, `judul_curhat`, `isi_curhat`) VALUES
(1, 1, 'BAJAK AH~', 'update aja bisa masa add gk bisa'),
(2, 2, 'test2', 'test yang ke dua, sungguh aneh tapi nyata'),
(3, 2, 'test inser', 'inset ett typo'),
(4, 2, 'ea gk bisa', 'ea Ea eA');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(1, '59106e81809926.21645961', 'Dha', 'dhadotid@gmail.com', 'v+UFuqXBgIYFSFKu4/4CdYBGHBNmMmM0NjNlOTJh', 'f2c463e92a', '2017-05-08 20:11:29', NULL),
(2, '591435228f22a1.79789738', 'Yudha Ganteng', 'yudhapurbajagad@gmail.com', 'MgpPxu2p6uVt8v2iwwwdY5he11IzOGJkMDRhMzUz', '38bd04a353', '2017-05-11 16:55:46', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `curhat`
--
ALTER TABLE `curhat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`unique_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `curhat`
--
ALTER TABLE `curhat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `curhat`
--
ALTER TABLE `curhat`
  ADD CONSTRAINT `curhat_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
