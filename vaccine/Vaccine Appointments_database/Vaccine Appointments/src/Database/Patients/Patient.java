package Database.Patients;

public class Patient {

    private String cpr;
    private String name;

    public Patient(String cpr, String name){
        this.cpr = cpr;
        this.name = name;
    }

    public String getCpr() {
        return cpr;
    }

    public String getName() {
        return name;
    }
}
