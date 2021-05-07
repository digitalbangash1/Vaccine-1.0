//package Database.Vagt;
//
//import Database.Connector;
//import Database.DateFormatter;
//import Database.Medarbejder.Medarbejder;
//
//import java.math.BigDecimal;
//import java.sql.*;
//
//public class VagtRepository {
//
//    private Connection connection = Connector.getConnection();
//
//    private PreparedStatement vagtStatement = null;
//
//    private PreparedStatement getVagt() {
//        if (vagtStatement == null) {
//            try {
//                vagtStatement = connection.prepareStatement(
//                        "SELECT * FROM Vagtplan WHERE VagtID = ?",
//                        ResultSet.TYPE_FORWARD_ONLY,
//                        ResultSet.CONCUR_UPDATABLE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return vagtStatement;
//    }
//
//    public void createIfNotExists(Vagt vagt) throws SQLException {
//        PreparedStatement ps = getVagt();
//        ps.setInt(1, vagt.getVagtID());
//        ResultSet rs = ps.executeQuery();
//        if (!rs.next()) {
//            create(vagt);
//        }
//    }
//
//    private PreparedStatement existingVagtStatement = null;
//    private PreparedStatement getExistingVagt() {
//        if (existingVagtStatement == null) {
//            try {
//                existingVagtStatement = connection.prepareStatement(
//                        "SELECT * FROM Vagtplan LIMIT 1",
//                        ResultSet.TYPE_FORWARD_ONLY,
//                        ResultSet.CONCUR_UPDATABLE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return existingVagtStatement;
//    }
//
//    public Vagt getOrCreate(Vagt vagt) throws SQLException {
//        PreparedStatement ps = getExistingVagt();
//        ResultSet rs = ps.executeQuery();
//        if (!rs.next()) {
//            create(vagt);
//            return vagt;
//        }
//        int vagtId = rs.getInt("VagtID");
//        Date dato = rs.getDate("Dato");
//
//        Vagt existingVagt = new Vagt(new java.util.Date(dato.getTime()));
//        existingVagt.setVagtID(vagtId);
//        return existingVagt;
//    }
//
//    private PreparedStatement insertVaccine = null;
//    private PreparedStatement getInsertVagtStatement() throws SQLException {
//        if (insertVaccine == null) {
//            insertVaccine = connection.prepareStatement(
//                    "INSERT INTO Vagtplan(Dato) VALUES (?)",
//                    Statement.RETURN_GENERATED_KEYS);
//        }
//        return insertVaccine;
//    }
//
//    private void create(Vagt vagt) throws SQLException {
//        PreparedStatement ps = getInsertVagtStatement();
//        ps.setString(1, DateFormatter.format(vagt.getDate()));
//        ps.executeUpdate();
//
//        ResultSet generatedKeys = ps.getGeneratedKeys();
//        generatedKeys.next();
//        int vagtId = generatedKeys.getInt(1);
//        vagt.setVagtID(vagtId);
//        generatedKeys.close();
//    }
//
//}
