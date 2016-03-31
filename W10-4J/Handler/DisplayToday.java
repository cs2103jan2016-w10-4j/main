package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayToday {
	static String header = "<font face = \"Helvetica\" size = \"10\"><b>Today's Task<b>";
	static String beforeHeader = "<font face = \"Helvetica\" size = \"6\"><b>" + Constants.MESSAGE_DISPLAYFORMAT_TODAY + ", ";
	static String subHeaderFont = "<font face = \"Helvetica\" size = \"6\"><b>";
	static String collated;
	static String output;
	
	public static String displayToday(Sorting sort, ArrayList<Task> notDoneYetStorage) {
		DisplayByStartDate.displayFormat(sort, notDoneYetStorage);
		output = header + "<p>" + subHeaderFont;
		
		if(collated == null || collated.equals(beforeHeader)) {
			output += Constants.MESSAGE_DISPLAYTODAY;
		} else {
			collated = collated.substring(beforeHeader.length(), collated.length());
			output += collated;
		}
		
		return output;
	}
	
	public static void getTodayTasks(String tasks){
		collated = tasks;
	}
}