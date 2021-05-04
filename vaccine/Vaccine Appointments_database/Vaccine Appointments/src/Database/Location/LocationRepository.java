package Database.Location;

import Database.Connector;

import java.sql.*;

public class LocationRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement locationStatement = null;

    private PreparedStatement getLocation() {
        if (locationStatement == null) {
            try {
                locationStatement = connection.prepareStatement(
                        "SELECT * FROM Lokation WHERE VaccinationsSted = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return locationStatement;
    }

    public void createIfNotExists(Location location) throws SQLException {
        PreparedStatement ps = getLocation();
        ps.setString(1, location.getVaccinationsSted());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(location);
        }
    }

    private PreparedStatement insertLocation = null;
    private PreparedStatement getInsertLocationStatement() throws SQLException {
        if (insertLocation == null) {
            insertLocation = connection.prepareStatement(
                    "INSERT INTO Lokation(VaccinationsSted, VaccineType, VagtID, Antal) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertLocation;
    }

    private void create(Location location) throws SQLException {
        PreparedStatement ps = getInsertLocationStatement();
        ps.setString(1, location.getVaccinationsSted());
        ps.setString(2, location.getVaccineType());
        ps.setInt(3, location.getVagtID());
        ps.setInt(4, location.getAntal());
        ps.executeUpdate();
    }

}
