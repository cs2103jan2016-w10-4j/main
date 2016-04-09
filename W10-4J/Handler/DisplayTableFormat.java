/*
 * This is the class that will be executed when user enter display name or display id 
 * In this class, the tasks are being sorted in terms of name or id
 * 
 * @@author A0126129J
 */
package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
 
public class DisplayTableFormat {	
	static ArrayList<Integer> taskIDForRecentTask;
	public static String displayTableFormat(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		String output = Constants.DISPLAYTABLEFORMAT_EMPTY_STRING;
		if (previousInput.size() != 0) {
			taskIDForRecentTask = CommonFunctionsInDisplay.checkRecentUpdatedTask(sortedList, previousInput);
		} else{
			taskIDForRecentTask = new ArrayList<>();
		}
		
		if (sortedList.size() == 0) {
			output = Constants.DISPLAY_HEADER_OPENTAG + Constants.DISPLAYTABLEFORMAT_NO_TASK_ON_HAND 
					+ Constants.DISPLAY_HEADER_CLOSETAG;
		} else {
			output = Constants.DISPLAY_TABLE_AND_HEADER;
			output = displayToOutput(output, sortedList, taskIDForRecentTask);
			output += Constants.DISPLAY_TABLE_CLOSETAG;
		}
		return output;
	}
	
	private static String displayToOutput(String output, ArrayList<Task> sortedList, 
			ArrayList<Integer> taskIDForRecentTask) {
		
		for (int i = 0; i < sortedList.size(); i++) {
			int displayIndex = i + 1;
			Task task = sortedList.get(i);
			output += getTask(displayIndex, task, taskIDForRecentTask);
		}
		return output;
	}
	
	private static String getTask (int index, Task task, ArrayList<Integer> taskIDForRecentTask) {
		String color = CommonFunctionsInDisplay.determineColor(task);
		String repeat = CommonFunctionsInDisplay.assignRepeat(task);
		return CommonFunctionsInDisplay.getTaskDetails(index, task, color, repeat, taskIDForRecentTask, Constants.COMMONFUNCTIONS_TABLE);
	}
}