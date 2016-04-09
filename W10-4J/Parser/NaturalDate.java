//@@author A0140114A
package Parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Constants;
import main.Date;

public class NaturalDate {
	private final Logger LOGGER = Logger.getLogger(NaturalDate.class.getName());

	public String getDate(String input) {
		input = input.trim();
		String special = specialDate(input);
		if (special != null) {
			return special;
		}
		String[] result = splitStringByCharType(input);
		if (result.length > 3 || result.length < 2) {
			return null;
		}
		boolean confirmMonth = false;
		boolean confirmYear = false;
		int month = -1;
		int day = -1;
		int year = -1;
		for (int i = 0; i < result.length; i++) {
			String s = result[i];
			if (!isInteger(s)) {
				if (!confirmMonth) {
					int monthValue = monthValue(s);
					if (monthValue == -1) {
						return null;
					} else {
						confirmMonth = true;
						month = monthValue;
					}
				} else {
					return null;
				}
			} else {
				int temp = Integer.parseInt(s);
				switch (i) {
				case 0:
					if (isDay(temp)) {
						day = temp;
					} else if (!confirmYear) {
						confirmYear = true;
						year = temp;
					} else {
						return null;
					}
					break;
				default:
					if (isMonth(temp) && !confirmMonth) {
						month = temp;
						confirmMonth = true;
					} else if (isDay(temp) && !confirmMonth && isMonth(day)) {
						month = day;
						day = temp;
					} else if (isDay(temp) && day == -1) {
						day = temp;
					} else if (!confirmYear) {
						confirmYear = true;
						year = temp;
					} else {
						return null;
					}
					break;
				}
			}
		}
		return finaliseDate(year, month, day);
	}

	private String specialDate(String input) {
		for (int i = 0; i < Constants.todayArgumentList.length; i++) {
			if (Constants.todayArgumentList[i].equals(input)) {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				int day = Calendar.getInstance().get(Calendar.DATE);
				String output = String.format(Constants.DATE_FORMAT, year, month, day);
				return output;
			}
		}

		for (int i = 0; i < Constants.tomorrowArgumentList.length; i++) {
			if (Constants.tomorrowArgumentList[i].equals(input)) {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				int day = Calendar.getInstance().get(Calendar.DATE) + 1;
				String output = String.format(Constants.DATE_FORMAT, year, month, day);
				return output;
			}
		}

		for (int i = 0; i < Constants.day.length; i++) {
			if (Constants.day[i].equals(input)) {
				Calendar c = Calendar.getInstance();
				int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				int newDayOfWeek = i % 7 + 1;
				if (currentDayOfWeek >= newDayOfWeek) {
					newDayOfWeek += 7;
				}
				int daysToIncrease = newDayOfWeek - currentDayOfWeek;
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				int day = Calendar.getInstance().get(Calendar.DATE);
				String output = String.format(Constants.DATE_FORMAT, year, month, day);
				for (int j = 0; j < daysToIncrease; j++) {
					output = Date.addDay(output);
				}
				return output;
			}
		}

		return null;
	}

	private String finaliseDate(int year, int month, int day) {
		if (year == -1) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		if (year < 1000) {
			year += 2000;
		}
		String output = String.format(Constants.DATE_FORMAT, year, month, day);
		if (Date.isLegalDate(output)) {
			LOGGER.log(Level.INFO, "Natural Date: " + output);
			return output;
		} else {
			LOGGER.log(Level.INFO, "Date cannot be interpreted");
			return null;
		}
	}

	public String[] splitStringByCharType(String input) {
		char[] str = input.toCharArray();
		ArrayList<String> list = new ArrayList<String>();
		String hold = Constants.EMPTY_STRING;
		int holdType = -1;

		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			int type = getCharType(c);
			if (type != holdType) {
				if (!hold.equals(Constants.EMPTY_STRING)) {
					list.add(hold);
				}
				if (type != 0) {
					hold = Constants.EMPTY_STRING + c;
				} else {
					hold = Constants.EMPTY_STRING;
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

	private int monthValue(String s) {
		int i = 0;
		for (i = 0; i < Constants.month.length; i++) {
			if (s.equalsIgnoreCase(Constants.month[i])) {
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
}