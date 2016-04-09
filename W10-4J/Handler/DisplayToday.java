/*
 * This is the class that will be executed when user enter display today
 * As the name suggests, it display all the tasks that whose start date is today's date
 * 
 * @@author A0126129J
 */
package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayToday {
	static String todayOutput;
	
	public static String displayToday(Sorting sort, ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		todayOutput = null;
		String beforeTheDateDisplay = Constants.DISPLAYTODAY_TODAY + Constants.DISPLAYTODAY_COMMA_AND_SPACE;

		// Within DisplayStartDate, there will be returnOutputToTheCorrectClass() method that will return today's tasks
		DisplayStartDate.displayFormat(sort, sortedList, previousInput);
		String output = Constants.DISPLAY_HEADER_OPENTAG + Constants.DISPLAYTODAY_HEADER + Constants.DISPLAY_SPACING;
		
		if (todayOutput == null) {
			output += Constants.DISPLAYTODAY_NOTASK;
		} else {
			// Remove "Today, " from the return value
			todayOutput = todayOutput.substring(beforeTheDateDisplay.length(), todayOutput.length());
			output += Constants.DISPLAY_HEADERTABLE_OPENTAG + todayOutput + Constants.DISPLAY_HEADERTABLE_CLOSETAG;
		}
		
		output += Constants.DISPLAY_HEADER_CLOSETAG;
		return output;
	}
	
	// Get today's tasks from DisplayStartDate
	public static void getTodayTasks(String tasks) {
		todayOutput = tasks;
	}
}