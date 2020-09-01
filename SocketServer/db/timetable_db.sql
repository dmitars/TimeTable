-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3307
-- Время создания: Авг 20 2020 г., 14:26
-- Версия сервера: 10.3.22-MariaDB
-- Версия PHP: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `timetable_db`
--

-- --------------------------------------------------------

--
-- Структура таблицы `disciplines`
--

CREATE TABLE `disciplines` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `disciplines`
--

INSERT INTO `disciplines` (`id`, `name`) VALUES
(1, 'Программирование'),
(2, 'Теория алгоритмов'),
(3, 'Теория графов'),
(4, 'Математический анализ'),
(5, 'Физ.к.и зд.'),
(6, 'ДМиМЛ'),
(7, 'Криптография'),
(8, 'Методы вычислений');

-- --------------------------------------------------------

--
-- Структура таблицы `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `course_number` int(11) NOT NULL,
  `group_number` int(11) NOT NULL
) ;

--
-- Дамп данных таблицы `groups`
--

INSERT INTO `groups` (`id`, `course_number`, `group_number`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11),
(12, 1, 12),
(13, 1, 13),
(14, 1, 14),
(15, 2, 1),
(16, 2, 2),
(17, 2, 3),
(18, 2, 4),
(19, 2, 5),
(20, 2, 6),
(21, 2, 7),
(22, 2, 8),
(23, 2, 9),
(24, 2, 10),
(25, 2, 11),
(26, 2, 12),
(27, 2, 13),
(28, 2, 14);

-- --------------------------------------------------------

--
-- Структура таблицы `lecturers`
--

CREATE TABLE `lecturers` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `lecturers`
--

INSERT INTO `lecturers` (`id`, `name`) VALUES
(1, 'Кашкевич С.И.'),
(2, 'Новичкова Д.А.'),
(3, 'Соболевская Е.П.'),
(4, 'Лебедев А.Ю.'),
(5, 'Орлович Ю.Л.'),
(6, 'Васильков Д.М.');

-- --------------------------------------------------------

--
-- Структура таблицы `lessons`
--

CREATE TABLE `lessons` (
  `id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `lecturer_id` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `room` text NOT NULL,
  `discipline_id` int(11) NOT NULL,
  `time_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL
) ;

--
-- Дамп данных таблицы `lessons`
--

INSERT INTO `lessons` (`id`, `group_id`, `lecturer_id`, `day`, `room`, `discipline_id`, `time_id`, `type_id`) VALUES
(1, 1, 1, 2, '522', 1, 1, 1),
(2, 1, 2, 2, '517', 4, 2, 1),
(3, 1, 2, 2, '517', 4, 3, 2),
(4, 1, 1, 2, '513', 1, 4, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `lesson_types`
--

CREATE TABLE `lesson_types` (
  `id` int(11) NOT NULL,
  `type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `lesson_types`
--

INSERT INTO `lesson_types` (`id`, `type`) VALUES
(1, 'Лекция'),
(2, 'Практика');

-- --------------------------------------------------------

--
-- Структура таблицы `time`
--

CREATE TABLE `time` (
  `id` int(11) NOT NULL,
  `start_hour` int(11) NOT NULL,
  `start_minute` int(11) NOT NULL,
  `length_in_minutes` int(11) NOT NULL
) ;

--
-- Дамп данных таблицы `time`
--

INSERT INTO `time` (`id`, `start_hour`, `start_minute`, `length_in_minutes`) VALUES
(1, 8, 15, 120),
(2, 9, 45, 120),
(3, 11, 15, 120),
(4, 13, 0, 120),
(5, 14, 30, 120);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `disciplines`
--
ALTER TABLE `disciplines`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `lessons`
--
ALTER TABLE `lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lessons_disciplines` (`discipline_id`),
  ADD KEY `lessons_groups` (`group_id`),
  ADD KEY `lessons_lecturers` (`lecturer_id`),
  ADD KEY `lessons_lesson_types` (`type_id`),
  ADD KEY `lessons_time` (`time_id`);

--
-- Индексы таблицы `lesson_types`
--
ALTER TABLE `lesson_types`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `time`
--
ALTER TABLE `time`
  ADD PRIMARY KEY (`id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `lessons`
--
ALTER TABLE `lessons`
  ADD CONSTRAINT `lessons_disciplines` FOREIGN KEY (`discipline_id`) REFERENCES `disciplines` (`id`),
  ADD CONSTRAINT `lessons_groups` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `lessons_lecturers` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`id`),
  ADD CONSTRAINT `lessons_lesson_types` FOREIGN KEY (`type_id`) REFERENCES `lesson_types` (`id`),
  ADD CONSTRAINT `lessons_time` FOREIGN KEY (`time_id`) REFERENCES `time` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
