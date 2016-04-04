package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayToday {
	static String beforeTheDateDisplay = Constants.MESSAGE_DISPLAYTODAY_TODAY + ", ";
	static String todayOutput;
	static String output;
	
	public static String displayToday(Sorting sort, ArrayList<Task> notDoneYetStorage, ArrayList<PreviousInput> previousInput) {
		/*
		 *  Within DisplayStartDate, there will be a returnTodayTasks method
		 *  that will return the output for those task that falls on today's date
		 */
		todayOutput = null;
		DisplayStartDate.displayFormat(sort, notDoneYetStorage, previousInput);
		output = Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG + Constants.MESSAGE_DISPLAYTODAY_HEADER + Constants.MESSAGE_DISPLAY_SPACING;
		
		if(todayOutput == null || todayOutput.equals(beforeTheDateDisplay)) {
			output += Constants.MESSAGE_DISPLAYTODAY;
		} else {
			// Remove "Today, " from the return value
			todayOutput = todayOutput.substring(beforeTheDateDisplay.length(), todayOutput.length());
			output += Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG_TABLE + todayOutput + Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG_TABLE;
		}
		
		output += Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG;
		return output;
	}
	
	// Get today's tasks from DisplayStartDate
	public static void getTodayTasks(String tasks) {
		todayOutput = tasks;
	}
}