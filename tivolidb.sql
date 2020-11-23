-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2020 at 02:10 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tivolidb`
--

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`id`, `name`) VALUES
(1, 'ABC'),
(2, 'XYZ');

-- --------------------------------------------------------

--
-- Table structure for table `dispatch`
--

CREATE TABLE `dispatch` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `purchaseorder_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dispatch`
--

INSERT INTO `dispatch` (`id`, `date`, `itemDesc`, `price`, `quantity`, `company_id`, `item_id`, `purchaseorder_id`) VALUES
(1, NULL, 'JKLO', NULL, 100, 1, 2, 1),
(2, NULL, 'ABCD', NULL, 200, 2, 1, 2),
(3, NULL, 'ABCD', NULL, 100, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `dispatchnote`
--

CREATE TABLE `dispatchnote` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `purchaseorder_id` int(11) DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dispatchnote`
--

INSERT INTO `dispatchnote` (`id`, `date`, `itemDesc`, `quantity`, `company_id`, `item_id`, `purchaseorder_id`, `vehicle_id`) VALUES
(1, NULL, 'JKLO', 100, 1, 2, 1, 1),
(2, NULL, 'ABCD', 100, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `itemCode` varchar(255) DEFAULT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `unitPrice` decimal(19,2) DEFAULT NULL,
  `unitWeight` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `itemCode`, `itemDesc`, `unitPrice`, `unitWeight`) VALUES
(1, 'EFT3456', 'ABCD', '2.00', 3),
(2, 'GHY7567', 'JKLO', '4.00', 5);

-- --------------------------------------------------------

--
-- Table structure for table `onprogress`
--

CREATE TABLE `onprogress` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `poitem_id` int(11) DEFAULT NULL,
  `purchaseorder_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Triggers `onprogress`
--
DELIMITER $$
CREATE TRIGGER `change_manufactured` AFTER INSERT ON `onprogress` FOR EACH ROW update poitem
set manufactured = manufactured + new.quantity
where id = new.poitem_id
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `change_tobe` AFTER INSERT ON `onprogress` FOR EACH ROW update poitem
set tobeMan = tobeMan - new.quantity
where id = new.poitem_id
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `poitem`
--

CREATE TABLE `poitem` (
  `id` int(11) NOT NULL,
  `itemDesc` varchar(255) DEFAULT NULL,
  `lineTotal` decimal(19,2) DEFAULT NULL,
  `lineWeight` int(11) DEFAULT NULL,
  `manufactured` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `tobeMan` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `onprogress_id` int(11) DEFAULT NULL,
  `purchaseorder_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `poitem`
--

INSERT INTO `poitem` (`id`, `itemDesc`, `lineTotal`, `lineWeight`, `manufactured`, `quantity`, `tobeMan`, `company_id`, `item_id`, `onprogress_id`, `purchaseorder_id`) VALUES
(1, 'ABCD', '200.00', 300, 0, 100, 100, 1, 1, NULL, 1),
(2, 'JKLO', '800.00', 1000, 0, 200, 200, 1, 2, NULL, 1),
(3, 'ABCD', '600.00', 900, 0, 300, 300, 2, 1, NULL, 2),
(4, 'JKLO', '800.00', 1000, 0, 400, 400, 2, 2, NULL, 2),
(5, 'ABCD', '400.00', 600, 0, 200, 200, 1, 1, NULL, 3),
(6, 'JKLO', '800.00', 1000, 0, 200, 200, 1, 2, NULL, 3);

-- --------------------------------------------------------

--
-- Table structure for table `purchaseorder`
--

CREATE TABLE `purchaseorder` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `poNumber` varchar(255) DEFAULT NULL,
  `totalPrice` decimal(19,2) DEFAULT NULL,
  `totalWeight` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchaseorder`
--

INSERT INTO `purchaseorder` (`id`, `date`, `poNumber`, `totalPrice`, `totalWeight`, `company_id`) VALUES
(1, '2020-11-21 00:00:00', '400400', '1000.00', NULL, 1),
(2, '2020-11-21 00:00:00', '400401', '1600.00', NULL, 2),
(3, '2020-11-22 00:00:00', '400420', '1200.00', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`id`, `name`) VALUES
(1, 'WP HK 6578');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dispatch`
--
ALTER TABLE `dispatch`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ggybic8ov2rlvtu5m1wja17n` (`company_id`),
  ADD KEY `FK_fuj56ypt4244rj5qp7a600yoq` (`item_id`),
  ADD KEY `FK_r99vt2u4r0vt4h4jy8cib0b2s` (`purchaseorder_id`);

--
-- Indexes for table `dispatchnote`
--
ALTER TABLE `dispatchnote`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_8xdlsbrjngrgj9ljvu5mj6jig` (`company_id`),
  ADD KEY `FK_l36xvsy8njlek6ba2dlrfb837` (`item_id`),
  ADD KEY `FK_d6kqsx5lywpvu11lve7afs683` (`purchaseorder_id`),
  ADD KEY `FK_2iiddmk37kahhimpjnef4gq3e` (`vehicle_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `onprogress`
--
ALTER TABLE `onprogress`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_teteiqrabvx2c8hkujwgn5718` (`company_id`),
  ADD KEY `FK_jg2y0uixp1qlnanbw4h4am2vf` (`item_id`),
  ADD KEY `FK_13otmkbr0mbewf9cop2m900m` (`poitem_id`),
  ADD KEY `FK_t338qrlumefyrxxvprmoy163f` (`purchaseorder_id`);

--
-- Indexes for table `poitem`
--
ALTER TABLE `poitem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_7rbepmnptx30ojueplxp890ld` (`company_id`),
  ADD KEY `FK_omf9ct2vwx08juweuhyto0498` (`item_id`),
  ADD KEY `FK_dcmfh4mtka0vcxxq7csdekj4h` (`onprogress_id`),
  ADD KEY `FK_1epveq5y07fed8kiac16fofd4` (`purchaseorder_id`);

--
-- Indexes for table `purchaseorder`
--
ALTER TABLE `purchaseorder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_kmkdf9n3a09tjg29f3exlf9u2` (`company_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `dispatch`
--
ALTER TABLE `dispatch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `dispatchnote`
--
ALTER TABLE `dispatchnote`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `onprogress`
--
ALTER TABLE `onprogress`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `poitem`
--
ALTER TABLE `poitem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `purchaseorder`
--
ALTER TABLE `purchaseorder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
