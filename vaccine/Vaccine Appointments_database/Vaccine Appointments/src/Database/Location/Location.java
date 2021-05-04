package Database.Location;

public class Location {

    private String vaccinationsSted;
    private String vaccineType;
    private int vagtID;
    private int antal;

    public  Location(String vaccinationsSted, String vaccineType, int vagtID, int antal){
        this.vaccinationsSted = vaccinationsSted;
        this.vaccineType = vaccineType;
        this.vagtID = vagtID;
        this.antal = antal;
    }

    public String getVaccinationsSted() {
        return vaccinationsSted;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public int getVagtID() {
        return vagtID;
    }

    public int getAntal() {
        return antal;
    }
}
