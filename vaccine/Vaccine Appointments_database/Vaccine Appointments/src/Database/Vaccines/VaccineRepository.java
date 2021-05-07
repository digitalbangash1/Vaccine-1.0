package Database.Vaccines;
import Database.Connector;
//import Database.Vagt.Vagt;

import java.math.BigDecimal;
import java.sql.*;

public class VaccineRepository {
    private Connection connection = Connector.getConnection();

    private PreparedStatement patientStatement = null;

    private PreparedStatement getVaccine() {
        if (patientStatement == null) {
            try {
                patientStatement = connection.prepareStatement(
                        "SELECT * FROM Vacciner WHERE Vaccine_Type = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return patientStatement;
    }

    public void createIfNotExists(VaccineInfo vaccineInfo) throws SQLException {
        PreparedStatement ps = getVaccine();
        ps.setString(1, vaccineInfo.getType());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(vaccineInfo);
        }
    }

    private PreparedStatement existingVaccineStatement = null;
    private PreparedStatement getExistingVaccine() {
        if (existingVaccineStatement == null) {
            try {
                existingVaccineStatement = connection.prepareStatement(
                        "SELECT * FROM Vacciner LIMIT 1",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingVaccineStatement;
    }

    public VaccineInfo getOrCreate(VaccineInfo vaccineInfo) throws SQLException {
        PreparedStatement ps = getExistingVaccine();
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(vaccineInfo);
            return vaccineInfo;
        }
        String vaccineType = rs.getString("Vaccine_Type");
        BigDecimal pris = rs.getBigDecimal("Pris");
        VaccineInfo existingVaccine = new VaccineInfo(vaccineType, pris);
        return existingVaccine;
    }

    private PreparedStatement insertVaccine = null;
    private PreparedStatement getInsertVaccineStatement() throws SQLException {
        if (insertVaccine == null) {
            insertVaccine = connection.prepareStatement(
                    "INSERT INTO Vacciner(Vaccine_Type, Pris) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertVaccine;
    }

    private void create(VaccineInfo vaccineInfo) throws SQLException {
        PreparedStatement ps = getInsertVaccineStatement();
        ps.setString(1, vaccineInfo.getType());
        ps.setBigDecimal(2, vaccineInfo.getPrice());
        ps.executeUpdate();
    }
}
