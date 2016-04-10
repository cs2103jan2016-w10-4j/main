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
	public static String displayDoneFormat(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput, int notDoneStorageSize) {
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		String output = Constants.DISPLAYDONE_EMPTY_STRING;
		ArrayList<Integer> taskIDForRecentTask;
		
		if (previousInput.size() != 0) {
			taskIDForRecentTask = CommonFunctionsInDisplay.checkRecentUpdatedTask(sortedList, previousInput, Constants.DISPLAY_DISPLAYDONE);
		} else {
			taskIDForRecentTask = new ArrayList<>();
		}
		
		if (sortedList.size() == 0) {
			output = Constants.DISPLAY_HEADER_OPENTAG + Constants.DISPLAYDONE_NO_TASK_DONE
					+ Constants.DISPLAY_HEADER_CLOSETAG;
		} else {
			output = Constants.DISPLAY_HEADER_OPENTAG + Constants.DISPLAYDONE_HEADER
					+ Constants.DISPLAY_HEADER_CLOSETAG + Constants.DISPLAY_SPACING
					+ Constants.DISPLAY_TABLE_AND_HEADER;

			output = displayToOutput(output, sortedList, taskIDForRecentTask, notDoneStorageSize);
			output += Constants.DISPLAY_TABLE_CLOSETAG;
		}
		return output;
	}

	private static String displayToOutput(String output, ArrayList<Task> sortedList, 
			ArrayList<Integer> taskIDForRecentTask, int notDoneStorageSize) {
		for (int i = 0; i < sortedList.size(); i++) {
			int displayIndex = notDoneStorageSize + i + 1;
			Task task = sortedList.get(i);
			output += getTask(displayIndex, task, taskIDForRecentTask);
		}
		return output;
	}
	
	private static String getTask(int index, Task task, ArrayList<Integer> taskIDForRecentTask) {
		String color = Constants.DISPLAY_COLOR_BLACK;
		String repeat = CommonFunctionsInDisplay.assignRepeat(task);
		return CommonFunctionsInDisplay.getTaskDetails(index, task, color, repeat, taskIDForRecentTask,
				Constants.DISPLAYDONE_DONE);
	}
}