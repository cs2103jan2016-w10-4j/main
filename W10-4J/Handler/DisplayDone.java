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
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		String output = "";
		ArrayList<Integer> taskIDForRecentTask;
		
		if (previousInput.size() != 0) {
			taskIDForRecentTask = CommonFunctionsInDisplay.generateChanges(sortedList, previousInput);
		} else {
			taskIDForRecentTask = new ArrayList<>();
		}
		
		if (sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYDONE_NOTASKDONE
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG;
		} else {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYDONE_HEADER
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG + Constants.MESSAGE_DISPLAY_SPACING
					+ Constants.MESSAGE_DISPLAY_TABLEANDHEADER;

			for (int i = 0; i < sortedList.size(); i++) {
				Task task = sortedList.get(i);
				output += getTask(i, task, taskIDForRecentTask);
			}

			output += Constants.MESSAGE_DISPLAY_TABLECLOSETAG;
		}
		return output;
	}

	private static String getTask(int index, Task task, ArrayList<Integer> taskIDForRecentTask) {
		String color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
		String repeat = CommonFunctionsInDisplay.assignRepeat(task);
		return CommonFunctionsInDisplay.getTaskDetails(index, task, color, repeat, taskIDForRecentTask,
				Constants.MESSAGE_DISPLAYDONE_DONE);
	}
}