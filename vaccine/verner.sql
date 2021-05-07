#Dropper  vores tables hvis de eksistere 

DROP TABLE IF EXISTS Rapport;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Lokation;
DROP TABLE IF EXISTS Medarbejder;
DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Vagtplan;
DROP TABLE IF EXISTS Certifikation;
DROP TABLE IF EXISTS VaccineInfo;


#Init tables.
CREATE TABLE Patient
	(cprnr			varchar(15),
	 Navn			VARCHAR(45),
	 PRIMARY KEY(CPRNR)
	);
    
    CREATE TABLE Vagtplan
	(VagtID			int auto_increment,
     Dato			datetime,
	 PRIMARY KEY(VagtID)
	);
    
CREATE TABLE VaccineInfo
	(VaccineType	Varchar(45),
	 Pris			Decimal(8,0),
	 PRIMARY KEY(VaccineType)
	);
    
CREATE TABLE Lokation
	(VaccinationsSted	Varchar(45),
	 VaccineType		VARCHAR(45),
     VagtID			int,
     Antal			Decimal(8,0),
	 PRIMARY KEY(VaccinationsSted),
     FOREIGN KEY(VaccineType) REFERENCES VaccineInfo(VaccineType) ON DELETE SET NULL,
     FOREIGN KEY(VagtID) REFERENCES Vagtplan(VagtID) ON DELETE SET NULL
	);

CREATE TABLE Certifikation
	(certID			int auto_increment,
	 VaccineType	Varchar(45),		
	 PRIMARY KEY(certID),
     FOREIGN KEY(VaccineType) REFERENCES VaccineInfo(VaccineType) ON DELETE SET NULL
	);
    
CREATE TABLE Medarbejder 
	(MedarbejderID	int auto_increment,
     VagtID			int,
     CertID			int,
     Navn			Varchar(45),
     Titel			varchar(45),
     Løn			Decimal(8,0),
     muligeLok		Varchar(45),
	 PRIMARY KEY(MedarbejderID),
     FOREIGN KEY(VagtID) REFERENCES Vagtplan(VagtID) ON DELETE SET NULL,
     FOREIGN KEY(CertID) REFERENCES Certifikation(CertID) ON DELETE SET NULL
	);
		    
CREATE TABLE Booking
	(BookingID		int auto_increment,
	 CPRNR			varchar(15),
     MedarbejderID	int,
     VaccinationsSted	Varchar(45),
     vacinationsTid		datetime,
     VaccineType Varchar(45),
	 PRIMARY KEY(BookingID),
     FOREIGN KEY(CPRNR) REFERENCES Patient(CPRNR) ON DELETE SET NULL,
     FOREIGN KEY(MedarbejderID) REFERENCES Medarbejder(MedarbejderID) ON DELETE SET NULL,
     FOREIGN KEY(VaccinationsSted) REFERENCES Lokation(VaccinationsSted) ON DELETE SET NULL,
     FOREIGN KEY(VaccineType) REFERENCES VaccineInfo(vaccineType) ON DELETE SET NULL
	);
    
CREATE TABLE Rapport
	(RapportID		DECIMAL(8,0),
	 BookingID		int,
     VaccineType		Varchar(45),
	 PRIMARY KEY(RapportID),
     FOREIGN KEY(BookingID) REFERENCES Booking(BookingID) ON DELETE SET NULL,
	 FOREIGN KEY(VaccineType) REFERENCES VaccineInfo(VaccineType) ON DELETE SET NULL
	);
    