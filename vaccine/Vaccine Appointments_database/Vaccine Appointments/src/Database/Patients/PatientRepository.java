package Database.Patients;

import Database.Connector;

import java.sql.*;

public class PatientRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement patientStatement = null;

    private PreparedStatement getPatient() {
        if (patientStatement == null) {
            try {
                patientStatement = connection.prepareStatement(
                        "SELECT * FROM Patient WHERE cprnr = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return patientStatement;
    }

    public void createIfNotExists(Database.Patients.Patient patient) throws SQLException {
        PreparedStatement ps = getPatient();
        ps.setString(1, patient.getCpr());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(patient);
        }
    }


    private PreparedStatement insertPatient = null;
    private PreparedStatement getInsertPatientStatement() throws SQLException {
        if (insertPatient == null) {
            insertPatient = connection.prepareStatement(
                    "INSERT INTO Patient(cprnr, name) VALUES (?, ?)",
                    Statement.NO_GENERATED_KEYS);
        }
        return insertPatient;
    }

    private void create(Patient patient) throws SQLException {
        PreparedStatement ps = getInsertPatientStatement();
        ps.setString(1, patient.getCpr());
        ps.setString(2, patient.getName());
        ps.executeUpdate();
    }
}
