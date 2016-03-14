package Parser;

import java.util.ArrayList;

public class NaturalTime {

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

	private static boolean isHour(int s) {
		return (s >= 0 && s < 24);
	}

	private static boolean isMinute(int s) {
		return (s >= 0 && s < 60);
	}

}