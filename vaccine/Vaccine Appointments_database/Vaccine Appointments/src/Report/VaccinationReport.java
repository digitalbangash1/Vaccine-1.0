package Report;

import Database.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VaccinationReport {
    public static void main(String[] args)
    {
        // Step 2: Making connection using
        // Connection type and inbulit function on
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        con= Connector.getConnection();

        // Try bock to catch exception/s
        try {

            // SQL command data stored in String datatype
           // String sql = "select * from booking";
            String sql = "select * from Booking a inner join Patient b on a.CPRNR = b.cprnr";
            p = con.prepareStatement(sql);
            rs = p.executeQuery();

            // Printing ID, name, email of customers
            // of the SQL command above
            System.out.println("bookingid\t\tcpr\t\tmedarbejderid\t\tvaccinationsted\t\tvacciantionstid\t\tvaccionsstype");

            // Condiion check
            while (rs.next()) {

                int BookingID = rs.getInt("BookingID");
                String cpr = rs.getString("cpr");
                String medarbejderid = rs.getString("medarbejder id");
                String vaccinationsted = rs.getString("vaccination sted");
                String vacciantionstid = rs.getString("vacciantions tid");
                String vaccionsstype = rs.getString("vacciantions type");


                System.out.println(BookingID + "\t\t" + medarbejderid
                        + "\t\t" + vaccinationsted+ "\t\t" + vacciantionstid +  "\t\t"+ vaccionsstype);
            }
        }

        // Catch block to handle exception
        catch (SQLException e) {

            // Print exception pop-up on scrreen
            System.out.println(e);
        }
    }
}