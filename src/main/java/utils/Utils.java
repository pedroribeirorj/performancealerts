package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static double doubleComDuasCasasDecimais(double a) {
		String s = Double.toString(a);
		if (s.lastIndexOf(".") != -1 && s.length() > s.lastIndexOf(".") + 3)
			s = s.substring(0, s.lastIndexOf(".") + 3);
		return Double.parseDouble(s);
	}
	public static String getDateTime() { 
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		Date date = new Date(); 
		return dateFormat.format(date); 
	}
}
