package noesis.performancealerts.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Utils {
	private Utils() {

	}

	public static double doubleComDuasCasasDecimais(double a) {
		String s = Double.toString(a);
		if (s.lastIndexOf('.') != -1 && s.length() > s.lastIndexOf('.') + 3)
			s = s.substring(0, s.lastIndexOf('.') + 3);
		else
			s = s + "0";
		return Double.parseDouble(s);
	}

	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getListaString(List<Integer> list) {
		if (list.isEmpty())
			return "()";
		String l = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			l += integer + ",";
		}
		return "(" + l.substring(0, l.lastIndexOf(',')) + ")";
	}
}
