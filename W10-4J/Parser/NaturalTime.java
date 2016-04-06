//@@author A0140114A
package Parser;

import java.util.ArrayList;
import main.Constants;

public class NaturalTime {

	public String getTime(String input) {
		input = input.trim();
		ArrayList<Integer> result;
		try {
			result = splitStringByInt(input);
		} catch (NumberFormatException e) {
			return null;
		}
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
		return String.format(Constants.TIME_FORMAT, hour, minute);
	}

	public ArrayList<Integer> splitStringByInt(String input) throws NumberFormatException{
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
}
