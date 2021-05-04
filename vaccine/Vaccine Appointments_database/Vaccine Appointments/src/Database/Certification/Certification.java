package Database.Certification;

public class Certification {

    private int certId;
    private String vaccineType;

    public Certification(String vaccineType){
        this.vaccineType = vaccineType;
    }

    public int getCertId() {
        return certId;
    }

    public void setCertId(int certId) {
        this.certId = certId;
    }

    public String getVaccineType() {
        return vaccineType;
    }
}
