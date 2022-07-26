-- CREATE DATABASE `algobattle` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
create table if not exists algorithm
(
    id                  bigint auto_increment
    primary key,
    complexity          int          null,
    cpp_initial_code    varchar(255) null,
    description         text         null,
    func_name           varchar(255) null,
    java_initial_code   varchar(255) null,
    memory_limit        int          null,
    python_initial_code varchar(255) null,
    short_description   text         null,
    time_limit          int          null,
    time_to_solve       int          null,
    wording             varchar(255) null
    );

create table if not exists algorithm_case
(
    id              bigint auto_increment
    primary key,
    algo_id         bigint       null,
    expected_output text         null,
    name            varchar(255) null,
    output_type     varchar(255) null
    );

create table if not exists case_input
(
    id      bigint auto_increment
    primary key,
    case_id bigint null,
    value   text   null
);

create table if not exists game
(
    id         bigint auto_increment
    primary key,
    created_at datetime             null,
    is_over    tinyint(1) default 0 not null
    );

create table if not exists player
(
    game_id                 bigint not null,
    user_id                 bigint not null,
    remaining_health_points int    null,
    won                     bit    null,
    primary key (game_id, user_id)
    );

create table if not exists resolution
(
    algo_id         bigint   not null,
    user_id         bigint   not null,
    game_id         bigint   null,
    linter_errors   int      null,
    resolution_time bigint   null,
    solved          bit      null,
    started_time    datetime null,
    primary key (algo_id, user_id)
    );

create table if not exists user
(
    id       bigint auto_increment
    primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null,
    level    int          null
    );

INSERT INTO `game` (`id`, `created_at`, `is_over`)
VALUES (1, '2020-12-12 12:12:00', false),
       (2, '2020-12-12 12:13:00', true),
       (3, '2020-12-12 12:14:00', false);

INSERT INTO `algorithm` (`id`, `complexity`, `cpp_initial_code`, `description`, `func_name`, `java_initial_code`,
                         `memory_limit`, `python_initial_code`, `short_description`, `time_limit`, `time_to_solve`,
                         `wording`)
VALUES (1, 1, 'int sum(int *numbers) {}', 'Addition de nombres', 'sum', 'public int sum(List<int> numbers) {}', 400,
        'def sum(numbers):', NULL, 180, 180,
        'Complétez la fonction sum afin de renvoyer la somme des nombres passés en paramètre'),
       (2, 1, 'int substract(int *numbers) {}', 'Soustraction de nombres', 'substract',
        'public int substract(List<int> numbers) {}', 400, 'def substract(numbers):', NULL, 500, 34,
        'Complétez la fonction substract afin de renvoyer la soustraction des nombres passés en paramètre'),
       (3, 1, 'int multiply(int *numbers) {}', 'Produit de nombres\r\n', 'multiply',
        'public int multiply(List<int> numbers) {}', 400, 'def multiply(numbers):', NULL, 10, 180,
        'Complétez la fonction multiply afin de retourner le produit des nombres passés en paramètres'),
       (4, 1, 'int divide(int a, int b) {}', 'Division entre deux nombres', 'divide',
        'public int divide(int a, int b) {}', 400, 'def divide(a, b):', NULL, 20, 160,
        'Complétez la fonction multiply afin de retourner la division de a par b'),
       (5, 1, 'bool isPrimary(int number) { }', 'Est-ce un nombre premier ?', 'isPrimary',
        'public boolean isPrimary(int number) {}', 400, 'def isPrimary(number):', NULL, 20, 160,
        'Complétez la fonction isPrimary pour retourner si le nombre passé en paramètre est un nombre premier ou pas'),
       (6, 1, 'char[] fizzbuzz(int number){}', 'FizzBuzz', 'fizzbuzz', 'public String fizzbuzz(int number){}', 400,
        'def fizzbuzz(number):', NULL, 20, 160,
        'Complétez la fonction fizzbuzz pour retourner \"fizz\" si le nombre est divisible par 3, \"buzz\" s il est divisible par 5, \"fizzbuzz\" s il est divisible par 3 et 5, le nombre lui-même sinon'),
       (7, 1, 'int duplicateWords(char[] sentence){}', 'Nombre de mots duppliqués', 'duplicateWords',
        'public int duplicateWords(String sentence) {}', 400, 'def duplicateWords(sentence):', NULL, 20, 160,
        'Complétez la fonction duplicateWords pour qu elle retourne le nombre de mots en double dans la phrase passée
        en paramètre '),
       (8, 1, ' int fibonacci(int rank){} ', ' Suite de Fibonacci ', ' fibonacci ', ' public int fibonacci(int rank){}
        ', 400, ' def fibonacci(rank) : ', NULL, 20, 160,
        ' Complétez la fonction fibonacci pour qu elle retourne le nombre de cette suite au rang passé en paramètre'),
       (9, 1, 'char mostUsedChar(char[] sentence) {}', 'Lettre la plus utilisée', 'mostUsedChar',
        'public char mostUsedChar(String sentence) {}', 400, 'def mostUsedChar(sentence):', NULL, 20, 160,
        'Complétez la fonction mostUsedChar pour retourner la lettre la plus employée dans la phrase passée en paramètre'),
       (10, 1, 'bool isPalyndrome(char[] word) {}', 'Est-ce un palyndrome ?', 'isPalyndrome',
        'public boolean isPalyndrome(String word){}', 400, 'def isPalyndrome(word):', NULL, 20, 160,
        'Complétez la fonction isPalyndrome pour qu elle retourne si le mot passé en paramètre est un palyndrome ou pas');



INSERT INTO `algorithm_case` (`id`, `algo_id`, `expected_output`, `name`, `output_type`)
VALUES (1, 1, '9', 'Avec nombres positifs', 'int'),
       (2, 1, '15', 'Avec nombres négatifs', 'int'),
       (3, 1, '-5', 'Aléatoire', 'int'),
       (4, 2, '5', 'Avec nombres positifs', 'int'),
       (5, 2, '37', 'Avec nombres négatifs', 'int'),
       (6, 2, '-5', 'Aléatoire', 'int'),
       (7, 3, '9', 'Avec nombres positifs', 'int'),
       (8, 3, '27', 'Avec nombres négatifs', 'int'),
       (9, 3, '0', 'Avec 0', 'int'),
       (10, 4, '8', 'Avec nombres positifs', 'int'),
       (11, 4, '24', 'Avec nombres négatifs', 'int'),
       (12, 4, '0', 'Avec 0', 'int'),
       (13, 5, '1', 'Petit nombre premier', 'boolean'),
       (14, 5, '1', 'Autre nombre premier', 'boolean'),
       (15, 5, '0', 'Grand nombre non premier', 'boolean'),
       (16, 6, 'fizz', 'Nombre divisible par 3', 'String'),
       (17, 6, '7', 'Nombre non divisible par 3 ou 5', 'String'),
       (18, 6, 'fizzbuzz', 'Nombre divisble par 3 et 5', 'String'),
       (19, 7, '1', 'Rang aléatoire', 'int'),
       (20, 7, '8', 'Rang aléatoire', 'int'),
       (21, 7, '55', 'Rang aléatoire', 'int'),
       (22, 8, '1', 'Un mot duppliqué', 'int'),
       (23, 8, '0', 'Pas de doublon', 'int'),
       (24, 8, '0', 'Mot inclus dans un autre', 'int'),
       (25, 9, 'n', 'Lettre classique qui ressort le plus', 'char'),
       (26, 9, 'o', 'Lettre classique qui ressort le plus', 'char'),
       (27, 9, '', 'Deux lettres ressortent autant', 'char'),
       (28, 10, '1', 'Palyndrome', 'boolean'),
       (29, 10, '0', 'Mêmes lettres', 'boolean'),
       (30, 10, '1', 'Palyndrome', 'boolean');

INSERT INTO `case_input` (`id`, `case_id`, `value`)
VALUES (1, 1, '[5, 2, 2]'),
       (2, 2, '[20, -5]'),
       (3, 3, '[-2, -3]'),
       (4, 4, '[9, -4]'),
       (5, 5, '[45, 8]'),
       (6, 6, '[2, 7]'),
       (7, 7, '[3, 3]'),
       (8, 8, '[-7, -4]'),
       (9, 9, '[9, -4, 0]'),
       (10, 10, '32'),
       (11, 10, '4'),
       (12, 11, '-48'),
       (13, 11, '-2'),
       (14, 12, '99'),
       (15, 12, '0'),
       (16, 13, '1'),
       (17, 14, '7'),
       (18, 15, '102'),
       (19, 16, 'fizz'),
       (20, 17, '7'),
       (21, 18, 'buzz'),
       (22, 19, '3'),
       (23, 20, '7'),
       (24, 21, '12'),
       (25, 22, 'bonjour à tous les amis à moi'),
       (26, 23, 'je ne sais pas'),
       (27, 24, 'c est bon les bonbons'),
       (28, 25, 'enchanté de faire votre connaissance Nico'),
       (29, 26, 'bonjour'),
       (30, 27, 'kayak'),
       (31, 28, 'kayak'),
       (32, 29, 'bonbon'),
       (33, 30, 'snobons');


INSERT INTO `player` (`game_id`, `user_id`, `remaining_health_points`, `won`)
VALUES (1, 1, 100, false),
       (1, 2, 100, false),
       (2, 1, -28, false),
       (2, 2, 50, true),
       (3, 2, 100, false);
