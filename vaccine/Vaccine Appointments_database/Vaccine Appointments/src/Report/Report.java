package Report;

import Database.Connector;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Report {
    public static void main(String[] args) {

        try {
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\digit\\Documents\\Database1.txt "));
            StringBuilder sb = new StringBuilder();

            Connection connection = Connector.getConnection();
            ResultSet rs = null;

            // String query="SELECT CPRNR, MedarbejderID,VaccinationsSted,VaccineType,vacinationsTid from booking";
            //String query = "SELECT * from Booking";
            String query ="select * from Booking a INNER JOIN Patient b on a.CPRNR = b.cprnr ";
            PreparedStatement ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("BookingID"));
                sb.append(",");
//                sb.append(rs.getString("CPRNR"));
//                sb.append(",");
                sb.append(rs.getString("Name"));
                sb.append(",");
                sb.append(rs.getString("MedarbejderID"));
                sb.append(",");
                sb.append(rs.getString("VaccinationsSted"));
                sb.append(",");
                sb.append(rs.getString("vacinationsTid"));
                sb.append(",");
                sb.append(rs.getString("VaccineType"));
                sb.append("\r\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("finished");

        } catch (Exception e) {

        }
    }
}



