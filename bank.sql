-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 09, 2024 at 01:48 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `ac_id` int(11) NOT NULL,
  `ac_no` varchar(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `gender` char(1) DEFAULT NULL CHECK (`gender` in ('M','F','O','m','f','o')),
  `phone_no` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `aadhar_no` varchar(12) NOT NULL,
  `dob` date DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`ac_id`, `ac_no`, `name`, `gender`, `phone_no`, `address`, `aadhar_no`, `dob`, `amount`) VALUES
(0, 'AC10035', 'KaluRam', 'M', '3654108894', 'Balotara', '451278457521', '1998-10-01', 10000.00),
(1, 'AC10001', 'Aarav Sharma', 'M', '9876543210', '123 MG Road, Mumbai', '123456789012', '1990-05-15', 62000.00),
(2, 'AC10002', 'Vivaan Singh', 'M', '9876543211', '456 Brigade Road, Bangalore', '234567890123', '1985-08-25', 75000.00),
(3, 'AC10003', 'Aditya Kumar', 'M', '9876543212', '789 Link Road, Delhi', '345678901234', '1992-11-30', 60000.00),
(4, 'AC10004', 'Vihaan Jain', 'M', '9876543213', '123 Residency Road, Chennai', '456789012345', '1988-07-10', 82000.00),
(5, 'AC10005', 'Arjun Gupta', 'M', '9876543214', '456 LBS Road, Kolkata', '567890123456', '1995-03-22', 45000.00),
(6, 'AC10006', 'Sai Patel', 'M', '9876543215', '789 Hill Road, Pune', '678901234567', '1991-06-15', 90000.00),
(7, 'AC10007', 'Reyansh Mehta', 'M', '9876543216', '123 Park Street, Hyderabad', '789012345678', '1987-02-18', 47000.00),
(8, 'AC10008', 'Ayaan Verma', 'M', '9876543217', '456 MG Road, Jaipur', '890123456789', '1990-09-25', 62000.00),
(9, 'AC10009', 'Krishna Nair', 'M', '9876543218', '789 Brigade Road, Lucknow', '901234567890', '1989-12-12', 53000.00),
(10, 'AC10010', 'Ishaan Reddy', 'M', '9876543219', '123 Residency Road, Patna', '012345678901', '1993-04-30', 48000.00),
(11, 'AC10011', 'Ananya Sharma', 'F', '9876543220', '456 Link Road, Bhopal', '123450987612', '1987-01-19', 78000.00),
(12, 'AC10012', 'Diya Singh', 'F', '9876543221', '789 Hill Road, Chandigarh', '234561098723', '1992-03-22', 67000.00),
(13, 'AC10013', 'Aadhya Kumar', 'F', '9876543222', '123 MG Road, Ahmedabad', '345672109834', '1988-06-13', 71000.00),
(14, 'AC10014', 'Priya Jain', 'F', '9876543223', '456 Brigade Road, Surat', '456783210945', '1991-10-28', 59000.00),
(15, 'AC10015', 'Sara Gupta', 'F', '9876543224', '789 Park Street, Coimbatore', '567894321056', '1994-12-31', 65000.00),
(16, 'AC10016', 'Ira Patel', 'F', '9876543225', '123 Residency Road, Kochi', '678905432167', '1989-07-20', 56000.00),
(17, 'AC10017', 'Aaradhya Mehta', 'F', '9876543226', '456 MG Road, Indore', '789016543278', '1993-09-25', 85000.00),
(18, 'AC10018', 'Meera Verma', 'F', '9876543227', '789 Link Road, Nagpur', '890127654389', '1985-11-13', 43000.00),
(19, 'AC10019', 'Anika Nair', 'F', '9876543228', '123 Brigade Road, Vishakhapatnam', '901238765490', '1992-05-17', 47000.00),
(20, 'AC10020', 'Riya Reddy', 'F', '9876543229', '456 Park Street, Ludhiana', '012349876501', '1990-10-05', 88000.00),
(21, 'AC10021', 'Vivaan Kaur', 'M', '9876543230', '789 Residency Road, Madurai', '123451234567', '1993-01-16', 52000.00),
(22, 'AC10022', 'Arjun Raj', 'M', '9876543231', '123 MG Road, Raipur', '234561234678', '1987-04-23', 64000.00),
(23, 'AC10023', 'Kabir Sharma', 'M', '9876543232', '456 Brigade Road, Kanpur', '345671234789', '1991-02-25', 67000.00),
(24, 'AC10024', 'Aryan Kumar', 'M', '9876543233', '789 Link Road, Thane', '456781234890', '1988-08-12', 73000.00),
(25, 'AC10025', 'Reyansh Singh', 'M', '9876543234', '123 Residency Road, Nashik', '567891234901', '1995-07-25', 45000.00),
(26, 'AC10026', 'Krishna Mehta', 'M', '9876543235', '456 MG Road, Faridabad', '678901234012', '1990-11-09', 49000.00),
(27, 'AC10027', 'Vihaan Patel', 'M', '9876543236', '789 Hill Road, Amritsar', '789012345123', '1992-05-30', 86000.00),
(28, 'AC10028', 'Ishaan Verma', 'M', '9876543237', '123 Park Street, Dhanbad', '890123456234', '1986-03-12', 47000.00),
(29, 'AC10029', 'Arjun Nair', 'M', '9876543238', '456 Residency Road, Varanasi', '901234567345', '1993-08-21', 52000.00),
(30, 'AC10030', 'Sai Reddy', 'M', '9876543239', '789 Brigade Road, Jodhpur', '012345678456', '1989-12-01', 57000.00),
(190, 'AC10033', 'Kamlesh', 'M', '4512003127', 'Kalu Ki Haveli, Ajmer', '784510001241', '2001-01-21', 52000.00),
(451, 'AC10039', 'Radhika', 'F', '8745361210', 'Jaipur', '451236789451', '1995-01-12', 5000.00),
(470, 'AC10034', 'Miro Soni', 'F', '5001234580', 'Baytu, Barmer', '400179456186', '2000-05-05', 25000.00),
(500, 'AC10038', 'Rajesh', 'M', '4536978120', 'Khanpura, Ajmer', '45369781245', '1990-10-10', 12000.00);

-- --------------------------------------------------------

--
-- Table structure for table `delete_ac`
--

CREATE TABLE `delete_ac` (
  `id` int(11) NOT NULL,
  `ac_id` int(11) NOT NULL,
  `ac_no` varchar(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `gender` char(1) DEFAULT NULL CHECK (`gender` in ('M','F','O','m','f','o')),
  `phone_no` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `aadhar_no` varchar(12) NOT NULL,
  `dob` date DEFAULT NULL,
  `amount` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `delete_ac`
--

INSERT INTO `delete_ac` (`id`, `ac_id`, `ac_no`, `name`, `gender`, `phone_no`, `address`, `aadhar_no`, `dob`, `amount`) VALUES
(414, 37, 'AC10031', 'Renu', 'F', '5300123485', 'Rani Ganjh, Jaipur', '200134521718', '1999-12-30', 0.00),
(821, 184, 'AC10032', 'Rajesh', 'M', '4500123078', 'Rampur, Karoli', '780012453656', '1998-06-08', 18000.00);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `ac_id` int(11) DEFAULT NULL,
  `transaction_type` varchar(50) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `transaction_date` date NOT NULL,
  `transaction_time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `ac_id`, `transaction_type`, `amount`, `transaction_date`, `transaction_time`) VALUES
(1, 1, 'Deposit', 5000.00, '2022-02-01', '09:16:12'),
(2, 2, 'Withdrawal', 2000.00, '2022-02-02', '01:39:21'),
(3, 3, 'Deposit', 1500.00, '2022-02-03', '08:08:22'),
(4, 4, 'Withdrawal', 1000.00, '2022-02-04', '06:01:47'),
(5, 5, 'Deposit', 2500.00, '2022-02-05', '09:36:10'),
(6, 6, 'Withdrawal', 3000.00, '2022-02-06', '10:18:15'),
(7, 7, 'Deposit', 4000.00, '2022-02-07', '01:39:01'),
(8, 8, 'Withdrawal', 3500.00, '2022-02-08', '08:05:06'),
(9, 9, 'Deposit', 4500.00, '2022-02-09', '03:23:37'),
(10, 10, 'Withdrawal', 5000.00, '2022-02-10', '10:18:02'),
(11, 11, 'Deposit', 5500.00, '2022-02-11', '12:47:06'),
(12, 12, 'Withdrawal', 6000.00, '2022-02-12', '15:08:04'),
(13, 13, 'Deposit', 6500.00, '2022-02-13', '18:29:33'),
(14, 14, 'Withdrawal', 7000.00, '2022-02-14', '15:01:11'),
(15, 15, 'Deposit', 7500.00, '2022-02-15', '19:15:45'),
(16, 16, 'Withdrawal', 8000.00, '2022-02-16', '01:28:18'),
(17, 17, 'Deposit', 8500.00, '2022-02-17', '18:32:10'),
(18, 18, 'Withdrawal', 9000.00, '2022-02-18', '19:15:08'),
(19, 19, 'Deposit', 9500.00, '2022-02-19', '03:47:18'),
(20, 20, 'Withdrawal', 10000.00, '2022-02-20', '04:38:45'),
(21, 21, 'Deposit', 11000.00, '2022-02-21', '10:14:27'),
(22, 22, 'Withdrawal', 12000.00, '2022-02-22', '00:05:22'),
(23, 23, 'Deposit', 13000.00, '2022-02-23', '06:02:45'),
(24, 24, 'Withdrawal', 14000.00, '2022-02-24', '15:21:22'),
(25, 25, 'Deposit', 15000.00, '2022-02-25', '16:45:35'),
(26, 26, 'Withdrawal', 16000.00, '2022-02-26', '06:45:33'),
(27, 27, 'Deposit', 17000.00, '2022-02-27', '01:47:26'),
(28, 28, 'Withdrawal', 18000.00, '2022-02-28', '18:07:47'),
(29, 29, 'Deposit', 19000.00, '2022-02-28', '15:33:14'),
(30, 30, 'Withdrawal', 20000.00, '2024-02-28', '06:21:16'),
(31, 1, 'Deposit', 5000.00, '2022-03-01', '07:05:27'),
(32, 2, 'Withdrawal', 2000.00, '2022-04-02', '04:47:40'),
(33, 3, 'Deposit', 1500.00, '2022-04-03', '07:29:06'),
(34, 4, 'Withdrawal', 1000.00, '2022-04-04', '09:26:19'),
(35, 5, 'Deposit', 2500.00, '2022-05-05', '03:18:02'),
(36, 6, 'Withdrawal', 3000.00, '2022-05-06', '04:26:28'),
(37, 7, 'Deposit', 4000.00, '2022-05-07', '01:38:37'),
(38, 8, 'Withdrawal', 3500.00, '2022-05-08', '11:26:02'),
(39, 9, 'Deposit', 4500.00, '2022-05-09', '01:23:20'),
(40, 10, 'Withdrawal', 5000.00, '2022-05-10', '02:09:04'),
(41, 11, 'Deposit', 5500.00, '2022-05-11', '03:07:05'),
(42, 12, 'Withdrawal', 6000.00, '2022-05-12', '14:27:28'),
(43, 13, 'Deposit', 6500.00, '2022-05-13', '04:03:15'),
(44, 14, 'Withdrawal', 7000.00, '2022-05-14', '04:16:31'),
(45, 15, 'Deposit', 7500.00, '2022-05-15', '15:10:43'),
(46, 16, 'Withdrawal', 8000.00, '2022-05-16', '03:00:16'),
(47, 17, 'Deposit', 8500.00, '2022-05-17', '12:18:37'),
(48, 18, 'Withdrawal', 9000.00, '2022-05-18', '13:20:36'),
(49, 19, 'Deposit', 9500.00, '2022-05-19', '03:29:44'),
(50, 20, 'Withdrawal', 10000.00, '2022-05-20', '04:17:03'),
(51, 21, 'Deposit', 11000.00, '2022-05-21', '00:25:41'),
(52, 22, 'Withdrawal', 12000.00, '2022-05-22', '09:44:29'),
(53, 23, 'Deposit', 13000.00, '2022-05-23', '08:04:47'),
(54, 24, 'Withdrawal', 14000.00, '2022-05-24', '19:18:23'),
(55, 25, 'Deposit', 15000.00, '2022-05-25', '04:23:31'),
(56, 26, 'Withdrawal', 16000.00, '2022-05-26', '19:06:40'),
(57, 27, 'Deposit', 17000.00, '2022-05-27', '09:09:25'),
(58, 28, 'Withdrawal', 18000.00, '2022-05-28', '14:21:12'),
(59, 29, 'Deposit', 19000.00, '2022-06-15', '07:02:09'),
(60, 30, 'Withdrawal', 20000.00, '2022-06-20', '09:18:20'),
(61, 470, 'Deposit', 2000.00, '2024-05-22', '16:21:47'),
(122, 1, 'Withdrawal', 1000.00, '2024-08-07', '00:34:46'),
(168, 470, 'Deposit', 5000.00, '2024-05-25', '03:36:04'),
(182, 470, 'Deposit', 3000.00, '2024-05-25', '06:19:30'),
(189, 1, 'Deposit', 5000.00, '2024-08-07', '00:01:45'),
(233, 1, 'Deposit', 5000.00, '2024-08-07', '01:30:19'),
(276, 470, 'Deposit', 5000.00, '2024-05-25', '19:26:09'),
(332, 190, 'Deposit', 2000.00, '2024-08-09', '12:12:00'),
(425, 1, 'Deposit', 1000.00, '2024-08-07', '03:08:30'),
(479, 1, 'Deposit', 1000.00, '2024-08-07', '17:22:29'),
(556, 1, 'Deposit', 5000.00, '2024-08-07', '15:47:07'),
(573, 470, 'Deposit', 6000.00, '2024-05-25', '07:17:24'),
(615, 190, 'Withdrawal', 50000.00, '2024-08-09', '12:19:55'),
(661, 1, 'Deposit', 200.00, '2024-08-07', '03:18:42'),
(697, 190, 'Deposit', 50000.00, '2024-08-09', '12:12:00'),
(703, 470, 'Withdrawal', 6000.00, '2024-05-25', '04:38:12'),
(749, 1, 'Deposit', 800.00, '2024-08-07', '13:05:30'),
(843, 470, 'Deposit', 3000.00, '2024-05-25', '16:36:08'),
(853, 470, 'Withdrawal', 6000.00, '2024-05-25', '04:08:29'),
(918, 470, 'Deposit', 5000.00, '2024-05-25', '15:29:46'),
(939, 470, 'Withdrawal', 8000.00, '2024-05-25', '05:45:29');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `ac_id` int(11) DEFAULT NULL,
  `user_password` varchar(255) NOT NULL,
  `super_user` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `ac_id`, `user_password`, `super_user`) VALUES
(0, NULL, 'nitin123', 'Nitin'),
(1, 1, 'password123', NULL),
(2, 2, 'password123', NULL),
(3, 3, 'password123', NULL),
(4, 4, 'password123', NULL),
(5, 5, 'password123', NULL),
(6, 6, 'password123', NULL),
(7, 7, 'password123', NULL),
(8, 8, 'password123', NULL),
(9, 9, 'password123', NULL),
(10, 10, 'password123', NULL),
(11, 11, 'password123', NULL),
(12, 12, 'password123', NULL),
(13, 13, 'password123', NULL),
(14, 14, 'password123', NULL),
(15, 15, 'password123', NULL),
(16, 16, 'password123', NULL),
(17, 17, 'password123', NULL),
(18, 18, 'password123', NULL),
(19, 19, 'password123', NULL),
(20, 20, 'password123', NULL),
(21, 21, 'password123', NULL),
(22, 22, 'password123', NULL),
(23, 23, 'password123', NULL),
(24, 24, 'password123', NULL),
(25, 25, 'password123', NULL),
(26, 26, 'password123', NULL),
(27, 27, 'password123', NULL),
(28, 28, 'password123', NULL),
(29, 29, 'password123', NULL),
(30, 30, 'password123', NULL),
(45, NULL, 'nitin123', 'Nitin'),
(120, NULL, 'mani123', 'Mani Kul'),
(271, 451, 'radhika123', NULL),
(297, 470, 'password123', NULL),
(464, 190, 'kamlesh123', NULL),
(1762, 500, '12345', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`ac_id`),
  ADD UNIQUE KEY `aadhar_no` (`aadhar_no`);

--
-- Indexes for table `delete_ac`
--
ALTER TABLE `delete_ac`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `a_c_id` (`ac_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `a_c_id` (`ac_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`ac_id`) REFERENCES `accounts` (`ac_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`ac_id`) REFERENCES `accounts` (`ac_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
