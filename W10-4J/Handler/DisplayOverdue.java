package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayOverdue {
	static String overdueOutput;
	static String output;
	
	public static String displayOverdue(Sorting sort, ArrayList<Task> notDoneYetStorage, ArrayList<PreviousInput> previousInput) {
		DisplayStartDate.displayFormat(sort, notDoneYetStorage, previousInput);
		output = Constants.MESSAGE_DISPLAY_HEADER_OVERDUE;
		
		if(overdueOutput == null || overdueOutput.equals(Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG)) {
			output += Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG + Constants.MESSAGE_DISPLAYOVERDUE + Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG;
		} else {
			output += overdueOutput;
		}
		return output;
	}
	
	// Get the overdue tasks from DisplayStartDate
	public static void getOverdueTasks(String tasks) {
		overdueOutput = tasks;
	}
}