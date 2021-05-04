package Database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date){
        return simpleDateFormat.format(date);
    }

}
