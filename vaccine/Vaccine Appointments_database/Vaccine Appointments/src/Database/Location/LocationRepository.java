package Database.Location;

import Database.Vaccines.VaccineInfo;
import Database.Connector;

import java.math.BigDecimal;
import java.sql.*;

public class LocationRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement locationStatement = null;

    private PreparedStatement getLocation() {
        if (locationStatement == null) {
            try {
                locationStatement = connection.prepareStatement(
                        "SELECT * FROM Lokation WHERE Lokation_Name = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return locationStatement;
    }

    public Location getOrCreate(Location location) throws SQLException {
        PreparedStatement ps = getLocation();
        ps.setString(1, location.getName());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(location);
            return location;
        }
        int id = rs.getInt("Lokation_ID");
        String name = rs.getString("Lokation_Name");
        Location exitsingLocation = new Location(name);
        exitsingLocation.setId(id);
        return exitsingLocation;
    }

    public void createIfNotExists(Location location) throws SQLException {
        PreparedStatement ps = getLocation();
        ps.setString(1, location.getName());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(location);
        }
    }

    private PreparedStatement insertLocation = null;
    private PreparedStatement getInsertLocationStatement() throws SQLException {
        if (insertLocation == null) {
            insertLocation = connection.prepareStatement(
                    "INSERT INTO Lokation(Lokation_Name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertLocation;
    }

    private void create(Location location) throws SQLException {
        PreparedStatement ps = getInsertLocationStatement();
        ps.setString(1, location.getName());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        int locationId = generatedKeys.getInt(1);
        location.setId(locationId);
        generatedKeys.close();
    }

}
