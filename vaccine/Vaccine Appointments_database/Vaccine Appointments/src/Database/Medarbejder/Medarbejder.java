package Database.Medarbejder;

import java.math.BigDecimal;

public class Medarbejder {

    private int medarbejderID;
    private int certificationID;
    private String navn;
    private String titel;

    public Medarbejder(
            int certificationID,
            String navn,
            String titel
    ){
        this.certificationID = certificationID;
        this.navn = navn;
        this.titel = titel;
    }

    public int getMedarbejderID() {
        return medarbejderID;
    }

    public void setMedarbejderID(int medarbejderID) {
        this.medarbejderID = medarbejderID;
    }

    public int getCertificationID() {
        return certificationID;
    }

    public String getNavn() {
        return navn;
    }

    public String getTitel() {
        return titel;
    }

}
