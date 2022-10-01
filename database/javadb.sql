-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 01, 2022 at 07:12 AM
-- Server version: 10.5.16-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javadb`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `kode` varchar(100) NOT NULL,
  `nama_akun` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`kode`, `nama_akun`, `createdAt`, `updatedAt`, `deletedAt`) VALUES
('1', 'qwe', '2022-09-26 15:07:25', '2022-09-26 15:07:25', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `angkatan`
--

CREATE TABLE `angkatan` (
  `tahun` int(4) NOT NULL,
  `createdAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `angkatan`
--

INSERT INTO `angkatan` (`tahun`, `createdAt`) VALUES
(2020, '2022-09-29 18:53:39');

-- --------------------------------------------------------

--
-- Table structure for table `biaya_lain`
--

CREATE TABLE `biaya_lain` (
  `id` bigint(16) NOT NULL,
  `akun_id` int(8) NOT NULL,
  `jurusan` bigint(16) NOT NULL,
  `nama_biaya` varchar(255) NOT NULL,
  `jumlah` double NOT NULL,
  `status` int(8) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `biaya_pmb`
--

CREATE TABLE `biaya_pmb` (
  `id` bigint(16) NOT NULL,
  `akun_id` int(8) NOT NULL,
  `nama_biaya` varchar(255) NOT NULL,
  `jumlah` double NOT NULL,
  `tahun_angkatan` int(8) NOT NULL,
  `gelombang` varchar(255) NOT NULL,
  `jurusan` bigint(16) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `biaya_tetap`
--

CREATE TABLE `biaya_tetap` (
  `id` bigint(16) NOT NULL,
  `akun_id` int(8) NOT NULL,
  `nama_biaya` varchar(255) NOT NULL,
  `jumlah` double NOT NULL,
  `jurusan` bigint(16) NOT NULL,
  `smt` int(8) NOT NULL,
  `tahun_angkatan` int(8) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jurusan`
--

CREATE TABLE `jurusan` (
  `id` bigint(16) NOT NULL,
  `nama_jurusan` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jurusan`
--

INSERT INTO `jurusan` (`id`, `nama_jurusan`, `createdAt`, `updatedAt`, `deletedAt`) VALUES
(1313, 'asd asdasd', '2022-09-29 19:02:11', '2022-09-29 19:03:27', NULL),
(14124, 'adasda', '2022-09-30 20:32:59', '2022-09-30 20:32:59', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id` varchar(100) NOT NULL,
  `kode_kelas` varchar(50) NOT NULL,
  `jurusan` bigint(16) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id`, `kode_kelas`, `jurusan`, `createdAt`, `updatedAt`, `deletedAt`) VALUES
('23', '14124', 1313, '2022-09-30 20:56:17', '2022-10-01 10:34:32', NULL),
('3', '123', 1313, '2022-09-30 20:44:30', '2022-10-01 10:24:04', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `id` varchar(15) NOT NULL,
  `nis` varchar(20) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `angkatan` int(11) NOT NULL,
  `jurusan` bigint(16) NOT NULL,
  `kelas` varchar(255) NOT NULL,
  `no_hp` varchar(255) NOT NULL,
  `du` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL,
  `deletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`id`, `nis`, `nama`, `angkatan`, `jurusan`, `kelas`, `no_hp`, `du`, `password`, `status`, `createdAt`, `updatedAt`, `deletedAt`) VALUES
('123', '123', '123', 2020, 14124, '123', '123', 'null', '12312', 'TIDAK AKTIF', '2022-09-29 19:55:48', '2022-10-01 13:46:43', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`) VALUES
('admin', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `angkatan`
--
ALTER TABLE `angkatan`
  ADD PRIMARY KEY (`tahun`);

--
-- Indexes for table `biaya_lain`
--
ALTER TABLE `biaya_lain`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `biaya_pmb`
--
ALTER TABLE `biaya_pmb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `biaya_tetap`
--
ALTER TABLE `biaya_tetap`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jurusan`
--
ALTER TABLE `jurusan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
