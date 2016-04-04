/*
 * This is the class that will be executed when user enter display done 
 * As the name suggests, it display all the tasks that are done
 * 
 * @@author A0126129J
 */
package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;

public class DisplayDone {	
	public static String displayDoneFormat(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		String output = "";
		int taskIDForRecentTask = CommonFunctionInDisplay.checkRecentUpdatedTaskID(sortedList, previousInput);
		
		if (sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYDONE_NOTASKDONE 
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG;
		} else {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYDONE_HEADER 
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG + Constants.MESSAGE_DISPLAY_SPACING 
					+ Constants.MESSAGE_DISPLAY_TABLEANDHEADER;

			for (int i = 0; i < sortedList.size(); i++) {
				Task task = sortedList.get(i);
				output += getTask(task, taskIDForRecentTask);
			}

			output += Constants.MESSAGE_DISPLAY_TABLECLOSETAG;
		}
		return output;
	}

	private static String getTask(Task task, int taskIDForRecentTask) {
		String color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		return CommonFunctionInDisplay.getTaskDetails(task, color, repeat, taskIDForRecentTask, Constants.MESSAGE_DISPLAYDONE_DONE);
	}
}