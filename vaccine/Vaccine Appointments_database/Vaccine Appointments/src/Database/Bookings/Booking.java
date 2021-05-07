package Database.Bookings;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.Date;

public class Booking {

    private int id;
    private String cprnr;
    private int medarbejderID;
    private String vaccinationsSted;
    private Date vacinationsTid;
    private String vaccineType;
    private int locationId;

    public Booking(
            String cprnr,
            int medarbejderID,
            String vaccinationsSted,
            Date vacinationsTid,
            String vaccineType,
            int locationId
    ){
        this.cprnr = cprnr;
        this.medarbejderID = medarbejderID;
        this.vaccinationsSted = vaccinationsSted;
        this.vacinationsTid = vacinationsTid;
        this.vaccineType = vaccineType;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getCprnr() {
        return cprnr;
    }

    public int getMedarbejderID() {
        return medarbejderID;
    }

    public String getVaccinationsSted() {
        return vaccinationsSted;
    }

    public Date getVacinationsTid() {
        return vacinationsTid;
    }

    public String getVaccineType() {
        return vaccineType;
    }
}
