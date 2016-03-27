package main;

public class Date {

	public static String addDay(String s) {
		int year = Integer.parseInt(s.split("/")[0]);
		int month = Integer.parseInt(s.split("/")[1]);
		int day = Integer.parseInt(s.split("/")[2]);
		day++;
		s = makeDate(year, month, day);
		if (isLegalDate(s)) {
			return s;
		}
		day = 1;
		month++;
		s = makeDate(year, month, day);
		if (isLegalDate(s)) {
			return s;
		}
		day = 1;
		month = 1;
		year++;
		return s;
	}

	public static String addMonth(String s) {
		int year = Integer.parseInt(s.split("/")[0]);
		int month = Integer.parseInt(s.split("/")[1]);
		int day = Integer.parseInt(s.split("/")[2]);

		month++;
		s = makeDate(year, month, day);
		if (isLegalDate(s)) {
			return s;
		}
		day = 1;
		month = 1;
		year++;
		s = makeDate(year, month, day);
		return s;
	}

	public static String addYear(String s) {
		int year = Integer.parseInt(s.split("/")[0]);
		int month = Integer.parseInt(s.split("/")[1]);
		int day = Integer.parseInt(s.split("/")[2]);
		year++;
		return makeDate(year, month, day);
	}

	public static boolean isLegalDate(String dateString) {
		int year = Integer.parseInt(dateString.split("/")[0]);
		int month = Integer.parseInt(dateString.split("/")[1]);
		int day = Integer.parseInt(dateString.split("/")[2]);
	    boolean yearOk = (year >= 1581) && (year <= 2500);
	    boolean monthOk = (month >= 1) && (month <= 12);
	    boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));
	    return (yearOk && monthOk && dayOk);
	}
	
	private static int daysInMonth(int year, int month) {
	    int daysInMonth;
	    switch (month) {
	        case 1: 
	        case 3:
	        case 5: 
	        case 7: 
	        case 8: 
	        case 10:
	        case 12:
	            daysInMonth = 31;
	            break;
	        case 2:
	            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
	                daysInMonth = 29;
	            } else {
	                daysInMonth = 28;
	            }
	            break;
	        default:
	            daysInMonth = 30;
	    }
	    return daysInMonth;
	}

	private static String makeDate(int year, int month, int day) {
		String s = year + "/";
		if (month < 10) {
			s += 0 + "" + month + "/";
		} else {
			s += month + "/";
		}
		if (day < 10) {
			s += 0 + "" + day;
		} else {
			s += day;
		}
		return s;
	}
}
