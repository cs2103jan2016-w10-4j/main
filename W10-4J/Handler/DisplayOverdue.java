package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayOverdue {
	static String overdueOutput;
	
	public static String displayOverdue(Sorting sort, ArrayList<Task> notDoneYetStorage, ArrayList<PreviousInput> previousInput) {
		overdueOutput = null;
		DisplayStartDate.displayFormat(sort, notDoneYetStorage, previousInput);
		String output = Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG + Constants.MESSAGE_DISPLAYOVERDUE_HEADER + Constants.MESSAGE_DISPLAY_SPACING;
		
		if(overdueOutput == null || overdueOutput.equals(Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG)) {
			output += Constants.MESSAGE_DISPLAYOVERDUE_NOTASK;
		} else {
			output += overdueOutput;
		}
		output += Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG;
		return output;
	}
	
	// Get the overdue tasks from DisplayStartDate
	public static void getOverdueTasks(String tasks) {
		overdueOutput = tasks;
	}
}