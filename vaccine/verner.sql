DROP DATABASE Test;


CREATE DATABASE  Test;
USE Test;

#Dropper  vores tables hvis de eksistere
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Lokation;
DROP TABLE IF EXISTS Medarbejder;
DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Certifikation;
DROP TABLE IF EXISTS Vagtplan;
DROP TABLE IF EXISTS VaccineInfo;
DROP TABLE IF EXISTS Varelager;


#Init tables.
CREATE TABLE Patient
	(cpr_nr			VARCHAR(10),
	 Navn       	VARCHAR(45),
	 PRIMARY KEY(Cpr_Nr)
	);

CREATE TABLE patient_log
(Log_id        INT AUTO_INCREMENT NOT NULL,
 Tidspunkt    TIME,
 Dato        DATE,
 cprnr    varchar(10),
 PRIMARY KEY(Log_id)
);

CREATE TRIGGER Patient_Trig_insert
    AFTER INSERT
    ON Patient FOR EACH ROW
    INSERT INTO patient_log (Tidspunkt, Dato, cprnr)
    VALUES (current_time(),current_date(), NEW.cpr_nr);

INSERT Patient VALUES
    ('1312814435','Niels Holm'),
    ('0708614815','Palle Paul Hansen-Jensen'),
    ('0709614029', 'Adam Jakobsen'),
    ('0901414025', 'Arne Henriksen'),
    ('0705614857', 'Tage Olsen'),
    ('0109664847', 'Karl Peter Billeskov'),
    ('2507847776', 'Tusnilda Kamma Olsen')
;

CREATE TABLE Vacciner
(Vaccine_Type   VARCHAR(45),
 Pris			DECIMAL(8,2),
 PRIMARY KEY(Vaccine_Type)
);

INSERT Vacciner VALUES 
     ("Covaxx",39.99),
     ("Divoc",29.99),
     ("Blast3000",14.99),
     ("Aspera",17.99);


CREATE TABLE Certifikation
(Cert_ID		INT AUTO_INCREMENT,
 Vaccine_Type    VARCHAR(45),
 PRIMARY KEY(Cert_ID),
 FOREIGN KEY(Vaccine_Type) REFERENCES Vacciner(Vaccine_Type) ON DELETE SET NULL
);

INSERT INTO Certifikation(Vaccine_Type) SELECT Vaccine_Type FROM Vacciner;

CREATE TABLE Lokation
(Lokation_ID      INT AUTO_INCREMENT,
 Lokation_Name	  VARCHAR(45),
 PRIMARY KEY(Lokation_ID)
);

INSERT Lokation(Lokation_Name) VALUES
     ("KBH"),
     ("HILL"),
     ("AARHUS"),
     ("KOLDING"),
     ("NAKSKOV"),
     ("ODENSE");


CREATE TABLE Varelager
(Item_ID            INT AUTO_INCREMENT,
 Vaccine_type        VARCHAR(45),
 Stock               INT,
 Lokation_ID         INT,
 PRIMARY KEY(Item_ID),
 FOREIGN KEY(Lokation_ID) REFERENCES Lokation(Lokation_ID) ON DELETE SET NULL,
 FOREIGN KEY(Vaccine_Type) REFERENCES Vacciner(Vaccine_Type) ON DELETE SET NULL
);

INSERT INTO Varelager(Vaccine_Type, Stock, Lokation_ID) VALUES
("Covaxx",400,1),
("Divoc",300,1),
("Blast3000",400,1),
("Aspera",200,1),
("Covaxx",400,2),
("Divoc",300,2),
("Blast3000",400,2),
("Aspera",200,2),
("Covaxx",400,3),
("Divoc",300,3),
("Blast3000",400,3),
("Aspera",200,3),
("Covaxx",400,4),
("Divoc",300,4),
("Blast3000",400,4),
("Aspera",200,4),
("Covaxx",400,5),
("Divoc",300,5),
("Blast3000",400,5),
("Aspera",200,5),
("Covaxx",400,6),
("Divoc",300,6),
("Blast3000",400,6),
("Aspera",200,6);

CREATE TABLE Medarbejder_Info
(Title         VARCHAR(45),
 Løn           DECIMAL(10.2),
      PRIMARY KEY (Title)
      );

INSERT Medarbejder_Info(Title, Løn) VALUES
     ('Sygeplejerske',30000),
     ('læge',39000),
     ('Social og sundhedsassisten',29000);

CREATE TABLE Medarbejder
(MedarbejderID	INT AUTO_INCREMENT,
 Navn			Varchar(45),
 Cert_ID		INT, -- Her kan der eventuelt laves en ny tabel som er en liste over hvilke certifikationer hvilken medarbejder har, da de ellers kun kan have én.
 Title			VARCHAR(45),
 PRIMARY KEY(MedarbejderID),
 FOREIGN KEY(Cert_ID) REFERENCES Certifikation(Cert_ID) ON DELETE SET NULL,
 FOREIGN KEY(Title) REFERENCES Medarbejder_Info(Title) ON DELETE SET NULL
);

INSERT Medarbejder(Cert_ID,Navn,Title) VALUES
     (1,'Lene Andersen','Sygeplejerske'),
     (1, 'Adam Nilsen','Læge'),
	 (2, 'Jesper Pedersen','Læge'),
     (2,'Ali  Olsen','Sygeplejerske'),
     (3, 'Pjevrot A. Terkelsen','Læge'),
     (3, 'Jans Thomasen','Social og sundhedsassisten'),
     (4, 'Arne Olsen','Social og sundhedsassisten'),
     (4, 'Ida Holm','Sygeplejerske');

CREATE TABLE Booking
(Booking_ID		INT AUTO_INCREMENT,
 Lokation_ID		INT,
 Medarbejder_ID		INT,
 cpr_nr				VARCHAR(10),
 VacType			VARCHAR(45),
 Vaccinations_Tid 	TIME,
 Vaccinations_Dato	DATE,
 PRIMARY KEY (Booking_ID),
 FOREIGN KEY(Lokation_ID) REFERENCES Lokation(Lokation_ID),
 FOREIGN KEY(Medarbejder_ID) REFERENCES Medarbejder(MedarbejderID),
 FOREIGN KEY(cpr_nr) REFERENCES Patient(cpr_nr),
 FOREIGN KEY(VacType) REFERENCES Vacciner(Vaccine_Type)
);

INSERT INTO Booking(Lokation_ID, Medarbejder_ID, cpr_nr, VacType, Vaccinations_Tid, Vaccinations_Dato) VALUES
(1, 1, '1312814435', 'Covaxx', '10:00', '20210412'),
(2, 1, '0708614815', 'Divoc', '10:15', '20210413'),
(3, 2, '0709614029', 'Blast3000', '10:20', '20210415'),
(2, 3, '0901414025', 'Aspera', '09:45', '20210412'),
(3, 3, '0705614857', 'Covaxx', '13:00', '20210503'),
(1, 4, '0109664847', 'Covaxx', '10:00', '20210412'),
(3, 3, '2507847776', 'Blast3000', '10:00', '20210512')
;

-- Section 6
UPDATE booking SET VacType='Blast3000' WHERE cpr_nr='1312814435';
DELETE FROM booking WHERE cpr_nr='0708614815';

-- Section 7
SELECT * FROM Booking ORDER BY Vaccinations_Tid ASC; -- Booking tid sorteret på tid
SELECT COUNT(1), Title FROM Medarbejder GROUP BY Title; -- Hvor mange personer har hver titel
SELECT DISTINCT MedarbejderID, Navn, Cert_ID, Medarbejder.Title, Løn FROM Medarbejder INNER JOIN Medarbejder_Info ON Medarbejder_Info.Title = Medarbejder.Title;
SELECT COUNT(1), Vaccinations_Dato FROM Booking GROUP BY Vaccinations_Dato; -- Hvor mange som har fået vacciner per dag
SELECT COUNT(1), MONTHNAME(Vaccinations_Dato) FROM Booking GROUP BY Vaccinations_Dato >= '20210401' AND Vaccinations_Dato < '20210501'; -- Hvor mange som har fået vacciner per måned
-- Økonomisk rapport over hver vaccine per måned
SELECT COUNT(1), VacType, COUNT(1)*(SELECT Pris FROM Vacciner WHERE Vaccine_Type = VacType) AS Price_Total, MONTHNAME(Vaccinations_Dato) AS Month
FROM Booking
GROUP BY Vaccinations_Dato >= '20210401' AND Vaccinations_Dato < '20210501', VacType
ORDER BY Month ASC
;

-- Section 8
-- Transaction example
SET autocommit = 0;
START TRANSACTION;
INSERT INTO Patient VALUES ('6669996669','HAHAHA EVIL NAME');
ROLLBACK;
-- COMMIT;
SELECT * FROM Patient;
SET autocommit = 1;

-- Procedure
    DELIMITER //
CREATE PROCEDURE ShowTable(IN insertCommand VARCHAR(255))
BEGIN
    DECLARE command VARCHAR(255);
    SET @command =CONCAT('Select * FROM ', insertCommand);
PREPARE exeCom FROM @command;
EXECUTE exeCom;
END //
    DELIMITER ;

CALL ShowTable('Patient');

-- Function
DELIMITER //
CREATE FUNCTION ThisIsHowYouMakeAFunction(num1 INT, num2 INT)
    RETURNS INT DETERMINISTIC
BEGIN
    DECLARE result int;
    SET RESULT = num1 + num2;
RETURN RESULT;
END //
DELIMITER ;

SELECT ThisIsHowYouMakeAFunction(5, 2);

-- Her er et eksempel på hvordan en medarbejder kan have en liste af lokationer som han kan være på.
CREATE TABLE arbejder_lokation
(
    Arbejder_lokation_ID	INT AUTO_INCREMENT PRIMARY KEY,
    MedarbejderID			INT,
    Lokation_ID				INT,
    FOREIGN KEY(MedarbejderID) REFERENCES Medarbejder(MedarbejderID),
    FOREIGN KEY(Lokation_ID) REFERENCES Lokation(Lokation_ID)
);

INSERT INTO arbejder_lokation(MedarbejderID, Lokation_ID) VALUES
(1, 1),
(1, 2),
(3, 1),
(3, 5)
;

SELECT * FROM arbejder_lokation;

SELECT Lokation_Name FROM Lokation WHERE Lokation_ID IN (
    SELECT Lokation_ID FROM arbejder_lokation WHERE MedarbejderID=(
        SELECT MedarbejderID FROM Medarbejder WHERE navn='Lene Andersen'));


SELECT * FROM Vacciner;
SELECT * FROM Certifikation;
SELECT * FROM Patient;
SELECT * FROM Medarbejder;
SELECT * FROM Medarbejder_Info;
SELECT * FROM Lokation;
SELECT * FROM Booking;
SELECT * FROM Varelager;