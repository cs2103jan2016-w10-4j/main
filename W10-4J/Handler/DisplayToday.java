package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayToday {
	static String header = "<h1><b>Today's Task</b></h1>";
	static String beforeHeader = Constants.MESSAGE_DISPLAYFORMAT_TODAY + ", ";
	static String subHeaderFont = "<h1><b>";
	static String collated;
	static String output;
	
	public static String displayToday(Sorting sort, ArrayList<Task> notDoneYetStorage, ArrayList<PreviousInput> previousInput) {
		DisplayByStartDate.displayFormat(sort, notDoneYetStorage, previousInput);
		output = header + subHeaderFont;
		
		if(collated == null || collated.equals(beforeHeader)) {
			output += Constants.MESSAGE_DISPLAYTODAY;
		} else {
			collated = collated.substring(beforeHeader.length(), collated.length());
			output += collated;
		}
		output += "</b></h1>";
		return output;
	}
	
	public static void getTodayTasks(String tasks){
		collated = tasks;
	}
}