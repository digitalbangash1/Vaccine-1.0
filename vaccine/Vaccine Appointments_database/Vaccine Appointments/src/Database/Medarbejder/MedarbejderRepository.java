package Database.Medarbejder;

import Database.Certification.Certification;
import Database.Connector;

import java.math.BigDecimal;
import java.sql.*;

public class MedarbejderRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement medarbejderStatement = null;

    private PreparedStatement getMedarbejder() {
        if (medarbejderStatement == null) {
            try {
                medarbejderStatement = connection.prepareStatement(
                        "SELECT * FROM Medarbejder WHERE MedarbejderID = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return medarbejderStatement;
    }

    public void createIfNotExists(Medarbejder medarbejder) throws SQLException {
        PreparedStatement ps = getMedarbejder();
        ps.setInt(1, medarbejder.getMedarbejderID());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(medarbejder);
        }
    }

    private PreparedStatement existingMedarbejderStatement = null;
    private PreparedStatement getExistingMedarbejder() {
        if (existingMedarbejderStatement == null) {
            try {
                existingMedarbejderStatement = connection.prepareStatement(
                        "SELECT * FROM Medarbejder LIMIT 1",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingMedarbejderStatement;
    }

    public Medarbejder getOrCreate(Medarbejder medarbejder) throws SQLException {
        PreparedStatement ps = getExistingMedarbejder();
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(medarbejder);
            return medarbejder;
        }
        int medarbejderId = rs.getInt("MedarbejderID");
        int vagtId = rs.getInt("VagtID");
        int certId = rs.getInt("CertId");
        String navn = rs.getString("Navn");
        String titel = rs.getString("Titel");
        BigDecimal lon = rs.getBigDecimal("Løn");
        String muligLok = rs.getString("muligeLok");

        Medarbejder existingMedarbejder = new Medarbejder(vagtId,certId,navn,titel,lon,muligLok);
        existingMedarbejder.setMedarbejderID(medarbejderId);
        return existingMedarbejder;
    }

    private PreparedStatement insertMedarbejder = null;

    private PreparedStatement getInsertMedarbejderStatement() throws SQLException {
        if (insertMedarbejder == null) {
            insertMedarbejder = connection.prepareStatement(
                    "INSERT INTO Medarbejder(VagtID, CertID, Navn, Titel, Løn, muligeLok) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertMedarbejder;
    }

    private void create(Medarbejder medarbejder) throws SQLException {
        PreparedStatement ps = getInsertMedarbejderStatement();
        ps.setInt(1, medarbejder.getVagtID());
        ps.setInt(2, medarbejder.getCertificationID());
        ps.setString(3, medarbejder.getNavn());
        ps.setString(4, medarbejder.getTitel());
        ps.setBigDecimal(5, medarbejder.getLøn());
        ps.setString(6, medarbejder.getMuligLok());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        int medarbejderId = generatedKeys.getInt(1);
        medarbejder.setMedarbejderID(medarbejderId);
        generatedKeys.close();
    }

}
