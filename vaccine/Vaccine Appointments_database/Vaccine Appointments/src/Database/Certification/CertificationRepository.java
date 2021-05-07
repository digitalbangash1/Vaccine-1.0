package Database.Certification;

import Database.Connector;

import java.sql.*;

public class CertificationRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement certStatement = null;
    private PreparedStatement getVagt() {
        if (certStatement == null) {
            try {
                certStatement = connection.prepareStatement(
                        "SELECT * FROM Certifikation WHERE Cert_ID = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return certStatement;
    }

    public void createIfNotExists(Certification certification) throws SQLException {
        PreparedStatement ps = getVagt();
        ps.setInt(1, certification.getCertId());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(certification);
        }
    }

    private PreparedStatement existingCertStatement = null;
    private PreparedStatement getExistingVagt() {
        if (existingCertStatement == null) {
            try {
                existingCertStatement = connection.prepareStatement(
                        "SELECT * FROM Certifikation LIMIT 1",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingCertStatement;
    }

    public Certification getOrCreate(Certification certification) throws SQLException {
        PreparedStatement ps = getExistingVagt();
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(certification);
            return certification;
        }
        int certId = rs.getInt("Cert_ID");
        String vaccineTYpe = rs.getString("Vaccine_Type");
        Certification existingCert = new Certification(vaccineTYpe);
        existingCert.setCertId(certId);
        return existingCert;
    }

    private PreparedStatement insertCert = null;
    private PreparedStatement getInsertCertificationStatement() throws SQLException {
        if (insertCert == null) {
            insertCert = connection.prepareStatement(
                    "INSERT INTO Certifikation(Vaccine_Type) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertCert;
    }

    private void create(Certification cert) throws SQLException {
        PreparedStatement ps = getInsertCertificationStatement();
        ps.setString(1, cert.getVaccineType());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        int Cert_ID = generatedKeys.getInt(1);
        cert.setCertId(Cert_ID);
        generatedKeys.close();
    }

}
