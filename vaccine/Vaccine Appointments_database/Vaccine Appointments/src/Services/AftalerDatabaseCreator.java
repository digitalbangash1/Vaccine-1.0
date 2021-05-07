package Services;

import Database.Bookings.Booking;
import Database.Bookings.BookingRepository;
import Database.Certification.Certification;
import Database.Certification.CertificationRepository;
import Database.Location.Location;
import Database.Location.LocationRepository;
import Database.Medarbejder.Medarbejder;
import Database.Medarbejder.MedarbejderRepository;
import Database.Patients.Patient;
import Database.Patients.PatientRepository;
import Database.Vaccines.VaccineInfo;
import Database.Vaccines.VaccineRepository;
//import Database.Vagt.Vagt;
//import Database.Vagt.VagtRepository;
import Models.VaccinationsAftale;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AftalerDatabaseCreator {

    public void create(File file) {

       // VagtRepository vagtRepository = new VagtRepository();
        BookingRepository bookingRepository = new BookingRepository();
        PatientRepository patientRepository = new PatientRepository();
        VaccineRepository vaccineRepository = new VaccineRepository();
        LocationRepository locationRepository = new LocationRepository();
        MedarbejderRepository medarbejderRepository = new MedarbejderRepository();
        CertificationRepository certificationRepository = new CertificationRepository();

        try {

            VaccineInfo testVaccine = vaccineRepository.getOrCreate(new VaccineInfo("abc", new BigDecimal(0)));
           // Vagt testVagt = vagtRepository.getOrCreate(new Vagt(new Date()));
            Certification testCert = certificationRepository.getOrCreate(new Certification(testVaccine.getType()));

            Medarbejder medarbejder = new Medarbejder(testCert.getCertId(), "Jens", "Medarbejder");
            Medarbejder testMedarbejder = medarbejderRepository.getOrCreate(medarbejder);

            AftalerFileReader reader = new AftalerFileReader();
            List<VaccinationsAftale> aftaler = reader.indlaesAftaler(file);

            for (VaccinationsAftale aftale : aftaler) {

                Patient patient = new Patient(aftale.getCprnr(), aftale.getNavn());
                patientRepository.createIfNotExists(patient);

                VaccineInfo vaccineInfo = new VaccineInfo(aftale.getVaccineType(), new BigDecimal(0));
                vaccineRepository.createIfNotExists(vaccineInfo);

                Location location = locationRepository.getOrCreate(new Location(aftale.getLokation()));

                Booking booking = new Booking(aftale.getCprnr(),testMedarbejder.getMedarbejderID(),aftale.getLokation(),aftale.getAftaltTidspunkt(),aftale.getVaccineType(),location.getId());
                bookingRepository.createIfNotExists(booking);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
//aftale.getCprnr(), testMedarbejder.getMedarbejderID(), aftale.getLokation(), aftale.getAftaltTidspunkt() , aftale.getVaccineType(),location.getId()