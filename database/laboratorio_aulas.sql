-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 21-10-2026 a las 23:08:52
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `laboratorio_aulas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aulas`
--

CREATE TABLE `aulas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `edificio` varchar(80) NOT NULL,
  `planta` int(11) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `tipo_aula_id` int(11) NOT NULL,
  `responsable` varchar(150) NOT NULL,
  `estado_aula_id` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `aulas`
--

INSERT INTO `aulas` (`id`, `nombre`, `edificio`, `planta`, `capacidad`, `tipo_aula_id`, `responsable`, `estado_aula_id`) VALUES
(1, 'B-204', 'Edificio B', 2, 30, 3, 'Jaime Garza', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados_aula`
--

CREATE TABLE `estados_aula` (
  `id` int(11) NOT NULL,
  `nombre` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `estados_aula`
--

INSERT INTO `estados_aula` (`id`, `nombre`) VALUES
(2, 'Asignada'),
(3, 'Asignada temporalmente'),
(1, 'Disponible'),
(4, 'En mantenimiento');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plantas`
--

CREATE TABLE `plantas` (
  `numero` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `plantas`
--

INSERT INTO `plantas` (`numero`) VALUES
(1),
(2),
(3),
(4),
(5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_aula`
--

CREATE TABLE `tipos_aula` (
  `id` int(11) NOT NULL,
  `nombre` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `tipos_aula`
--

INSERT INTO `tipos_aula` (`id`, `nombre`) VALUES
(2, 'Centro de cómputo'),
(4, 'Especial'),
(3, 'Laboratorio'),
(1, 'Normal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `clave_hash` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `correo`, `clave_hash`) VALUES
(1, 'admin@ufg.edu.sv', '600000:qwoV3OCNVnoIW5XQwntNVQ==:R8fFFQkWYOVRwsgy+ynlgai+cqXaFdt1+50ndH9iaq8=');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aulas`
--
ALTER TABLE `aulas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_aula_edificio` (`nombre`,`edificio`),
  ADD KEY `fk_aula_planta` (`planta`),
  ADD KEY `fk_aula_tipo` (`tipo_aula_id`),
  ADD KEY `fk_aula_estado` (`estado_aula_id`);

--
-- Indices de la tabla `estados_aula`
--
ALTER TABLE `estados_aula`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`numero`);

--
-- Indices de la tabla `tipos_aula`
--
ALTER TABLE `tipos_aula`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aulas`
--
ALTER TABLE `aulas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estados_aula`
--
ALTER TABLE `estados_aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tipos_aula`
--
ALTER TABLE `tipos_aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aulas`
--
ALTER TABLE `aulas`
  ADD CONSTRAINT `fk_aula_estado` FOREIGN KEY (`estado_aula_id`) REFERENCES `estados_aula` (`id`),
  ADD CONSTRAINT `fk_aula_planta` FOREIGN KEY (`planta`) REFERENCES `plantas` (`numero`),
  ADD CONSTRAINT `fk_aula_tipo` FOREIGN KEY (`tipo_aula_id`) REFERENCES `tipos_aula` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
