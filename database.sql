DROP DATABASE IF EXISTS pay_my_buddy;

CREATE DATABASE pay_my_buddy;
USE pay_my_buddy;


CREATE TABLE user
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`email` VARCHAR(50) NOT NULL UNIQUE,
	`password` VARCHAR(64) NOT NULL,
	`solde` FLOAT NOT NULL,
	`is_admin` BOOL DEFAULT false
);

INSERT INTO user (email, password, solde) 
VALUES
('admin@paymybuddy.fr', '$2y$10$pKegOjaJWhZm6pv4ZnRLeeWpQboYkqKulK/lem2xVUSH9P7WThUQ6', 37.63), /* admin */
('cindy.sup@email.freu', '$2y$10$n4X65aYYYjLOT6LdBelqvuzgZLalj1iN6PZyFrxnAKE6DBn6EtBfe', 0), /* user1 */
('antonin.dada@email.freu', '$2y$10$zH91lv9X5i9Dg8U0kMlYzel5lBbM3J3Op5QPGNkGWgPDRMuQe28Ly', 78546.12), /* pass1 */
('amedee.dada@email.freu', '$2y$10$MNwnX0acQcBa2J1iK.fjg.1BsNZkVDNh3J/JmTnPfFTkS6HNIKJoO', 457.00), /* pass1 */
('barbara@gmail.com', '$2y$10$B12V3K8fV.YfQpK3enNNcuBtAAKA5U7IWbrfPMP6tgfQk0PsjYnFi', 5614.27), /* pass1 */
('didier@gmail.com', '$2y$10$ObgoacTijtKkoLfs98mpSebE4Hh5I0qGk8SfS3/JG2utVhP7l6aLS', 965.12), /* pass1 */
('jean.marc@gmail.com', '$2y$10$FVEBJx29j6Xaj77pAWGI3OUvh1OQLwKk64rlUF1Bzo7dk5QvX3ANm', 57.9), /* pass1 */
('dede@gmail.com', '$2y$10$mgY9GL7Pj4yzPJ19gTKnZupBjg42Sd5XxGaw9GV.hs6oEcXQj/bPu', 10.00), /* pass1 */
('christ-alain@gmail.com', '$2y$10$j2YKHm3WXWZ5WoORXgtCMutVNq22QZzvBCDU6NR52Kcb4BeQdPfti', 9874.1), /* pass1 */
('gatien@gmail.com', '$2y$10$qxDSRNq/m.Jz3/PIAXcnPetsBa8Wu8EN/4DDDuaR7Z8ArbTniRn6y', 128.60); /* pass1 */

UPDATE user SET is_admin = true WHERE id = 1;


CREATE TABLE contact
(
	`user1_id` INT NOT NULL,
	`user2_id` INT NOT NULL,
	FOREIGN KEY (user1_id) REFERENCES `user` (id) ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY (user2_id) REFERENCES `user` (id) ON DELETE RESTRICT ON UPDATE CASCADE,
	PRIMARY KEY (user1_id, user2_id),
	UNIQUE (user1_id, user2_id)
);

DELIMITER //

CREATE TRIGGER before_insert_contact
BEFORE INSERT ON contact
FOR EACH ROW
BEGIN
   	DECLARE temp_user1_id INT;
    DECLARE temp_user2_id INT;
    
    SET temp_user1_id = LEAST(NEW.user1_id, NEW.user2_id);
    SET temp_user2_id = GREATEST(NEW.user1_id, NEW.user2_id);
    
    SET NEW.user1_id = temp_user1_id;
    SET NEW.user2_id = temp_user2_id;
END;
//

DELIMITER ;

INSERT INTO contact (user1_id, user2_id) 
VALUES
(1,2),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),
(2,3),(2,4),(2,5),(2,8),
(3,4),(3,10),(3,1),
(4,7),(4,9),(4,10),
(5,6);

/* SELECT u.email, u.solde, cu.email, cu.solde FROM user u JOIN contact c ON u.id = c.user1_id JOIN user cu ON c.user2_id = cu.id; */

CREATE TABLE bank_account
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(30) NOT NULL,
	`rib` VARCHAR(50) NOT NULL,
	`iban` VARCHAR(50) NOT NULL,
	`user_id` INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO bank_account (rib, iban, user_id, name) 
VALUES
('17499 12345 12345678901 53', 'FR7617499123451234567890153', 2, 'BNP Paribas'),
('14889 12345 12345678901 28', 'FR7614889123451234567890128', 3, 'Compte courant'),
('17939 12345 12345678901 81', 'FR7617939123451234567890181', 4, 'Credit Mutuel'),
('14158 12345 12345678901 97', 'FR7614158123451234567890197', 5, 'Societe Generale'),
('18319 12345 12345678901 17', 'FR7618319123451234567890117', 6, 'Credit Agricole'),
('01234 09999 01234567890 46', 'FR7601234099990123456789046', 7, 'Credit Mutuel'),
('12345 12345 01234567890 06', 'FR7612345123450123456789006', 8, 'Mon Compte'),
('12345 12345 64987451324 83', 'FR0512345123451649875421683', 9, 'Un nom de compte'),
('10278 00111 00200076803 44', 'FR7610278001110002007680344', 10, 'Epargne');


CREATE TABLE transaction
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`sender_id` INT NOT NULL,
	`receiver_id` INT NOT NULL,
	`date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`amount` FLOAT NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`fee` FLOAT NOT NULL,
	FOREIGN KEY (sender_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY (receiver_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO transaction (sender_id, receiver_id, amount, date, description, fee)
VALUES
(2, 4, 9.99, "1988-04-01 18:24:13", "Golf gang", 0.5),
(2, 6, 57.28, "2002-03-03 17:45:11", "Resto", 0.5),
(3, 5, 6485.1, "2005-04-07 08:17:17", "Bowling", 0.5),
(10, 4, 6.35, "2007-03-26 04:24:54", "Test", 0.5),
(8, 7, 9.99, "2012-06-12 12:34:34", "Netflix", 0.5),
(5, 7, 12.5, "1754-07-15 22:18:13", "Longue description en plusieurs mots", 0.5),
(3, 9, 98.45, "2147-09-21 11:46:31", "Brico truc", 0.5),
(4, 6, 19.99, "1984-10-04 16:17:24", "Prétexte", 0.5),
(9, 8, 16.79, "2020-12-13 16:02:12", "Lavomatic", 0.5),
(4, 9, 15.78, "2020-11-07 15:46:11", "Un truc", 0.5),
(9, 5, 600.52, "2022-07-04 20:31:16", "Loyer", 0.5),
(9, 7, 78.59, "2020-06-07 11:52:54", "Courses", 0.5),
(9, 2, 13.01, "2021-06-17 16:18:52", "Steam", 0.5),
(9, 3, 102, "2024-12-25 13:54:", "Problèmes techniques", 0.5);

/* SELECT t.id, u.email AS "Sender", ru.email AS "Receiver", t.date, t.amount, t.description, t.fee FROM transaction t JOIN user u ON t.sender_id = u.id JOIN user ru ON t.receiver_id = ru.id ORDER BY date;*/


CREATE TABLE bank_operation
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`account_id` INT NOT NULL,
	`date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`amount` FLOAT NOT NULL,
	FOREIGN KEY (account_id) REFERENCES bank_account (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO bank_operation (account_id, date, amount)
VALUES
(1, "2023-09-01 17:45:25", 50), (1, "2023-12-01 16:45:25", -20), (1, "2024-01-01 17:54:25", 19.99), (1, "2024-03-02 17:44:25", 24.2),
(2, "2022-02-01 14:44:42", 60), (2, "2024-01-02 16:44:42", 45), (2, "2024-02-03 17:45:12", 32),
(3, "2024-04-01 15:45:31", 98.25), (3, "2022-05-04 16:45:31", 62), (3, "2023-01-01 17:45:31", -99.99), (3, "2020-01-01 12:12:31", 10),
(4, "2022-03-01 15:46:13", 12.21), (4, "2021-04-27 14:42:10", 27),
(5, "2021-01-01 13:46:11", 78.12),
(6, "2020-01-01 22:21:10", 94.21), (6, "2024-02-28 19:45:09", -22.67),
(7, "2024-02-01 07:53:51", 999.99),
(8, "2024-01-01 05:10:52", 100), (8, "2024-01-13 07:09:47", -84),
(9, "2024-03-01 15:20:54", 87.28);

/* SELECT u.id, u.email, a.name AS "account", o.date, o.amount FROM bank_operation o JOIN bank_account a ON o.account_id = a.id JOIN user u ON u.id = a.user_id ORDER BY date;*/