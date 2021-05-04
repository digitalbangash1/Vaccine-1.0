import Services.AftalerDatabaseCreator;

import java.io.File;

public class IndlaesAftalerEksempel {

	public static void main(String[] args) {

		File file = new File("vaccine/Vaccine Appointments_database/Vaccine Appointments/src/vaccinationsaftaler.csv");
		AftalerDatabaseCreator creator = new AftalerDatabaseCreator();
		creator.create(file);

	}
}