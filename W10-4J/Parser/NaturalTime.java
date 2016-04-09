//@@author A0140114A
package Parser;

import java.util.ArrayList;
import main.Constants;

public class NaturalTime {
	private boolean isAm;
	private boolean isPm;
	private int hour;
	private int minute;
	String input_;

	public String getTime(String input) {
		isAm = false;
		isPm = false;
		hour = -1;
		minute = -1;
		input_ = input.trim();
		containsAmOrPm();
		System.out.println(input_);
		ArrayList<Integer> result;
		try {
			result = splitStringByInt(input_);
		} catch (NumberFormatException e) {
			return null;
		}
		return determineTime(result);
	}

	private void containsAmOrPm() {
		try {
			String lastTwoChar = input_.substring(input_.length() - 2);
			if (lastTwoChar.equals("am")) {
				isAm = true;
				input_ = input_.substring(0, input_.length() - 2);
			} else if (lastTwoChar.equals("pm")) {
				isPm = true;
				input_ = input_.substring(0, input_.length() - 2);
			}
		} catch (IndexOutOfBoundsException e) {
		}
	}

	public String determineTime(ArrayList<Integer> result) {
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
		if (isTimeValid()) {
			return String.format(Constants.TIME_FORMAT, hour, minute);
		} else {
			return null;
		}
	}

	public ArrayList<Integer> splitStringByInt(String input) throws NumberFormatException {
		char[] str = input.toCharArray();
		ArrayList<Integer> list = new ArrayList<Integer>();
		String hold = Constants.EMPTY_STRING;
		int holdType = -1;
		int type;

		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			if (Character.isDigit(c)) {
				type = 1;
			} else if (Character.isAlphabetic(c)) {
				throw new NumberFormatException();
			} else {
				type = 0;
			}
			if (type != holdType) {
				if (!hold.equals(Constants.EMPTY_STRING)) {
					list.add(Integer.parseInt(hold));
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
		list.add(Integer.parseInt(hold));
		return list;
	}

	private static boolean isHour(int s) {
		return (s >= 0 && s < 24);
	}

	private static boolean isMinute(int s) {
		return (s >= 0 && s < 60);
	}

	private boolean isTimeValid() {
		if (isAm) {
			if (hour > 12 || hour < 1) {
				return false;
			} else {
				if (hour == 12) {
					hour = 0;
				}
				return true;
			}
		} else if (isPm) {
			if (hour > 12 || hour < 1) {
				return false;
			} else {
				if (hour != 12) {
					hour += 12;
				}
				return true;
			}
		} else {
			return isHour(hour) && isMinute(minute);
		}
	}
}
