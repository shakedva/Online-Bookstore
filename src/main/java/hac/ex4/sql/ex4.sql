-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2022 at 08:59 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ex4`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL,
  `discount` double NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `discount`, `image`, `name`, `price`, `quantity`) VALUES
(1, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/3985/9781398515697.jpg', 'Seven Husbands of Evelyn Hugo', 42.1, 10),
(2, 10, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/0084/9780008412937.jpg', 'This Winter', 77, 5),
(3, 5, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4711/9781471154621.jpg', 'November 9', 45, 6),
(4, 30, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4749/9781474979061.jpg', 'The Thief Who Sang Storms', 40.5, 18),
(5, 13, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0083/9780008389666.jpg', 'Nick and Charlie', 50, 24),
(6, 10, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0082/9780008244095.jpg', 'I Was Born for This', 75, 31),
(7, 5, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0075/9780007559220.jpg', 'Solitaire', 49.3, 19),
(8, 15, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4091/9781409188506.jpg', 'Cat And Mouse', 68, 10),
(9, 20, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4814/9781481431934.jpg', 'Chain of Thorns', 74.5, 13),
(10, 50, 'https://m.media-amazon.com/images/I/514BEKIC-yL.jpg', 'Quick and Easy Gluten Free', 53.6, 26);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(11);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
