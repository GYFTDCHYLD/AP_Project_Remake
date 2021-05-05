-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 04, 2021 at 01:32 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `microstar`
--

-- --------------------------------------------------------

--
-- Table structure for table `Billing`
--

CREATE TABLE `Billing` (
  `userId` varchar(10) NOT NULL,
  `status` varchar(20) NOT NULL,
  `amountDue` float DEFAULT NULL,
  `interest` float DEFAULT NULL,
  `dueDate` varchar(10) DEFAULT NULL,
  `paidDate` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Billing`
--

INSERT INTO `Billing` (`userId`, `status`, `amountDue`, `interest`, `dueDate`, `paidDate`) VALUES
('A121', 'due', 15000, NULL, '20/05/2021', NULL),
('N346', 'Due', 15000, NULL, '30/05/2021', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Complain`
--

CREATE TABLE `Complain` (
  `id` int(10) NOT NULL,
  `custId` varchar(10) NOT NULL,
  `type` varchar(20) NOT NULL,
  `message` varchar(200) NOT NULL,
  `repId` varchar(10) DEFAULT NULL,
  `techId` varchar(10) DEFAULT NULL,
  `visitDate` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `Email`
--

CREATE TABLE `Email` (
  `userId` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Email`
--

INSERT INTO `Email` (`userId`, `email`) VALUES
('A121', 'Willbrugh@yahoo.com'),
('C123', 'Reid@yahoo.com'),
('D111', 'Holness@yahoo.com'),
('D112', 'Dixon@yahoo.com'),
('N346', 'nesh@yahoo.com'),
('S122', 'Jones@yahoo.com');

-- --------------------------------------------------------

--
-- Table structure for table `Phone`
--

CREATE TABLE `Phone` (
  `userId` varchar(10) NOT NULL,
  `phoneNumber` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Phone`
--

INSERT INTO `Phone` (`userId`, `phoneNumber`) VALUES
('A121', 8769855764),
('C123', 8769852764),
('D111', 8769885764),
('D112', 8769857643),
('N346', 8763452746),
('S122', 8769387463);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `userId` varchar(10) NOT NULL,
  `nameTitle` varchar(3) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `password` varchar(65) NOT NULL,
  `jobTitle` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`userId`, `nameTitle`, `firstName`, `lastName`, `password`, `jobTitle`) VALUES
('', '', '', '', '', ''),
('A121', 'Ms', 'Akielia', 'Willbrugh', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', ''),
('C123', 'Mr', 'Craig', 'Reid', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Technician'),
('D111', 'Ms', 'Dahlia', 'Holness', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Representative'),
('D112', 'Ms', 'Danielle', 'Dixon', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Representative'),
('N346', 'Ms', 'Neshkafay', 'Johnson', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', ''),
('S122', 'Ms', 'Shericka', 'Jones', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Representative');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Billing`
--
ALTER TABLE `Billing`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `Complain`
--
ALTER TABLE `Complain`
  ADD PRIMARY KEY (`id`),
  ADD KEY `custId` (`custId`),
  ADD KEY `repId` (`repId`),
  ADD KEY `techId` (`techId`);

--
-- Indexes for table `Email`
--
ALTER TABLE `Email`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `Phone`
--
ALTER TABLE `Phone`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`userId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Complain`
--
ALTER TABLE `Complain`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Billing`
--
ALTER TABLE `Billing`
  ADD CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`);

--
-- Constraints for table `Complain`
--
ALTER TABLE `Complain`
  ADD CONSTRAINT `custId` FOREIGN KEY (`custId`) REFERENCES `User` (`userId`),
  ADD CONSTRAINT `repId` FOREIGN KEY (`repId`) REFERENCES `User` (`userId`),
  ADD CONSTRAINT `techId` FOREIGN KEY (`techId`) REFERENCES `User` (`userId`);

--
-- Constraints for table `Email`
--
ALTER TABLE `Email`
  ADD CONSTRAINT `emailAddress` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`);

--
-- Constraints for table `Phone`
--
ALTER TABLE `Phone`
  ADD CONSTRAINT `Contact` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
