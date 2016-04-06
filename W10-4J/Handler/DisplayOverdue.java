/*
 * This is the class that will be executed when user enter display overdue 
 * As the name suggests, it display all the tasks that are overdue
 * 
 * @@author A0126129J
 */
package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayOverdue {
	static String overdueOutput;
	
	public static String displayOverdue(Sorting sort, ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		overdueOutput = null;
		
		// Within DisplayStartDate, there will be returnOutputToTheCorrectClass() method that will return overdue tasks
		DisplayStartDate.displayFormat(sort, sortedList, previousInput);
		String output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYOVERDUE_HEADER 
				+ Constants.MESSAGE_DISPLAY_SPACING;
		
		if(overdueOutput == null) {
			output += Constants.MESSAGE_DISPLAYOVERDUE_NOOVERDUETASK;
		} else {
			output += overdueOutput;
		}
		output += Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG;
		return output;
	}
	
	// Get the overdue tasks from DisplayStartDate
	public static void getOverdueTasks(String tasks) {
		overdueOutput = tasks;
	}
}