package Database.Bookings;

import Database.Connector;
import Database.DateFormatter;

import java.sql.*;

public class BookingRepository {

    private Connection connection = Connector.getConnection();

    private PreparedStatement bookingStatement = null;

    private PreparedStatement getBooking() {
        if (bookingStatement == null) {
            try {
                bookingStatement = connection.prepareStatement(
                        "SELECT * FROM Booking WHERE BookingID = ?",
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookingStatement;
    }

    public void createIfNotExists(Booking booking) throws SQLException {
        PreparedStatement ps = getBooking();
        ps.setInt(1, booking.getId());
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            create(booking);
        }
    }

    private PreparedStatement insertBooking = null;

    private PreparedStatement getInsertBookingStatement() throws SQLException {
        if (insertBooking == null) {
            insertBooking = connection.prepareStatement(
                    "INSERT INTO Booking(CPRNR, MedarbejderID, vacinationsTid, vaccineType, Lokation_ID) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
        }
        return insertBooking;
    }

    private void create(Booking booking) throws SQLException {
        PreparedStatement ps = getInsertBookingStatement();
        ps.setString(1, booking.getCprnr());
        ps.setInt(2, booking.getMedarbejderID());
        ps.setString(3, DateFormatter.format(booking.getVacinationsTid()));
        ps.setString(4, booking.getVaccineType());
        ps.setInt(5, booking.getLocationId());
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        int bookingId = generatedKeys.getInt(1);
        booking.setId(bookingId);
        generatedKeys.close();
    }

}
