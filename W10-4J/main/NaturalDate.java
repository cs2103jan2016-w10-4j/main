package main;

import java.util.ArrayList;

public class NaturalDate {

	static String[] month = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov",
			"dec", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october",
			"november", "december" };

	public String getDate(String input) {
		input = input.trim();
		String[] result = charTypeSplit(input);
		if (result.length > 3) {
			return null;
		}

		ArrayList<Integer> day = new ArrayList<Integer>();
		ArrayList<Integer> month = new ArrayList<Integer>();
		int year = -1;
		boolean confirmMonth = false, confirmYear = false;

		for (String s : result) {
			if (isInteger(s)) {
				int temp = Integer.parseInt(s);
				if (isDay(temp)) {
					day.add(temp);
				} else if (isMonth(temp) && !confirmMonth) {
					month.add(temp);
				} else if (!confirmYear){
					year = temp;
					confirmYear = true;
				} else{
					return null;
				}
			} else {
				if (!confirmMonth) {
					int temp = monthValue(s);
					if (temp == -1) {
						return null;
					} else {
						month.clear();
						month.add(temp);
						confirmMonth = true;
					}
				} else {
					return null;
				}
			}
		}
		return confirmDate(day,month,year,confirmMonth);
	}

	public String confirmDate(ArrayList<Integer> day, ArrayList<Integer> month, int year,
			boolean confirmMonth) {
		int finalDay, finalMonth, finalYear;

		if (confirmMonth) {
			finalMonth = month.get(0);
			day.add(year);
			finalDay = day.get(0);
			finalYear = day.get(1);
		} else {
			day.addAll(month);
			day.add(year);
			finalDay = day.get(0);
			finalMonth = day.get(1);
			finalYear = day.get(2);
		}
		return String.format("%04d/%02d/%02d", finalYear, finalMonth, finalDay);
	}

	public String[] charTypeSplit(String input) {
		char[] str = input.toCharArray();
		ArrayList<String> list = new ArrayList<String>();
		String hold = "";
		int holdType = -1;

		for (char c : str) {
			int type = getCharType(c);
			if (type != holdType) {
				if (!hold.equals("")) {
					list.add(hold);
				}
				if (type != 0) {
					hold = "" + c;
				} else {
					hold = "";
				}
				holdType = type;
			} else {
				if (type != 0) {
					hold += c;
				}
			}
		}
		list.add(hold);
		return list.toArray(new String[list.size()]);
	}

	private int getCharType(char c) {
		if (Character.isDigit(c)) {
			return 1;
		} else if (Character.isLetter(c)) {
			return 2;
		} else {
			return 0;
		}
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private int monthValue(String s) {
		int i = 0;
		for (i = 0; i < month.length; i++) {
			if (s.equalsIgnoreCase(month[i])) {
				return (i + 1) % 12;
			}
		}
		return -1;
	}

	private boolean isDay(int s) {
		return (s > 0 && s < 32);
	}

	private boolean isMonth(int s) {
		return (s > 0 && s < 13);
	}
}
