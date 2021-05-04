package Database.Medarbejder;

import java.math.BigDecimal;

public class Medarbejder {

    private int medarbejderID;
    private int vagtID;
    private int certificationID;
    private String navn;
    private String titel;
    private BigDecimal lon;
    private String muligLok;

    public Medarbejder(
            int vagtID,
            int certificationID,
            String navn,
            String titel,
            BigDecimal lon,
            String muligLok
    ){
        this.vagtID = vagtID;
        this.certificationID = certificationID;
        this.navn = navn;
        this.titel = titel;
        this.lon = lon;
        this.muligLok = muligLok;
    }

    public int getMedarbejderID() {
        return medarbejderID;
    }

    public void setMedarbejderID(int medarbejderID) {
        this.medarbejderID = medarbejderID;
    }

    public int getVagtID() {
        return vagtID;
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

    public BigDecimal getLøn() {
        return lon;
    }

    public String getMuligLok() {
        return muligLok;
    }
}
