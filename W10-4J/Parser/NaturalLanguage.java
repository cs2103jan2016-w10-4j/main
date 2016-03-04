package Parser;

import java.util.ArrayList;
import java.util.Calendar;

public class NaturalLanguage {

	static String[] month = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov",
			"dec", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october",
			"november", "december" };

	public String getDate(String input) {
		input = input.trim();
		String[] result = splitStringByCharType(input);
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
				if (isMonth(temp) && !confirmMonth) {
					month.add(temp);
				} else if (isDay(temp)) {
					day.add(temp);
				} else if (!confirmYear) {
					year = temp;
					confirmYear = true;
				} else {
					return null;
				}
			} else {
				if (!confirmMonth) {
					int temp = monthValue(s);
					if (temp == -1) {
						return null;
					} else {
						//month.clear();
						while(!month.isEmpty()){
							int hold = month.remove(0);
							if(isDay(hold)){
								day.add(hold);
							} else if (!confirmYear) {
								year = temp;
								confirmYear = true;
							} else {
								return null;
							}
						}
						month.add(temp);
						confirmMonth = true;
					}
				} else {
					return null;
				}
			}
		}
		return confirmDate(day, month, year, confirmMonth);
	}

	public String getTime(String input) {
		input = input.trim();
		ArrayList<Integer> result = splitStringByInt(input);
		int hour, minute;
		if (result.size() == 1) {
			minute = 0;
			hour = result.get(0);
			if (hour > 100) {
				minute = hour % 100;
				hour /= 100;
			}
			if (!isHour(hour)) {
				return null;
			}
		} else if (result.size() == 2) {
			hour = result.get(0);
			if (!isHour(hour)) {
				return null;
			}
			minute = result.get(1);
			if (!isMinute(minute)) {
				return null;
			}
		} else {
			return null;
		}
		return String.format("%02d:%02d", hour, minute);
	}

	public String[] splitStringByCharType(String input) {
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

	public ArrayList<Integer> splitStringByInt(String input) {
		char[] str = input.toCharArray();
		ArrayList<Integer> list = new ArrayList<Integer>();
		String hold = "";
		int holdType = -1;
		int type;
		for (char c : str) {
			if (Character.isDigit(c)) {
				type = 1;
			} else {
				type = 0;
			}

			if (type != holdType) {
				if (!hold.equals("")) {
					list.add(Integer.parseInt(hold));
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
		list.add(Integer.parseInt(hold));
		return list;
	}

	public String confirmDate(ArrayList<Integer> day, ArrayList<Integer> month, int year, boolean confirmMonth) {
		int finalDay, finalMonth, finalYear;
		if (confirmMonth) {
			finalMonth = month.get(0);
			day.add(year);
			finalDay = day.get(0);
			finalYear = day.get(1);
		} else {
			month.addAll(day);
			month.add(year);
			finalMonth = month.get(0);
			finalDay = month.get(1);
			finalYear = month.get(2);
		}
		if (finalYear == -1) {
			finalYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		if (finalYear < 1000) {
			finalYear += 2000;
		}
		return String.format("%04d/%02d/%02d", finalYear, finalMonth, finalDay);
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

	private int monthValue(String s) {
		int i = 0;
		for (i = 0; i < month.length; i++) {
			if (s.equalsIgnoreCase(month[i])) {
				return (i + 1) % 12;
			}
		}
		return -1;
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean isDay(int s) {
		return (s > 0 && s < 32);
	}

	private static boolean isMonth(int s) {
		return (s > 0 && s < 13);
	}

	private static boolean isHour(int s) {
		return (s >= 0 && s < 24);
	}

	private static boolean isMinute(int s) {
		return (s >= 0 && s < 60);
	}

}
