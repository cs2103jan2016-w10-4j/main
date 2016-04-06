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
	public static String displayTableFormat(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		assert sortedList != null && previousInput != null : Constants.ASSERT_DISPLAY_ARRAYLISTS;
		
		String output = "";
		ArrayList<Integer> taskIDForRecentTask = CommonFunctionsInDisplay.generateChanges(sortedList, previousInput);
		
		if (sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYTABLEFORMAT_NOTASKONHAND 
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG;
		} else {
			output = Constants.MESSAGE_DISPLAY_TABLEANDHEADER;
			
			for (int i = 0; i < sortedList.size(); i++) {
				Task task = sortedList.get(i);
				output += getTask(i, task, taskIDForRecentTask);
			}
			output += Constants.MESSAGE_DISPLAY_TABLECLOSETAG;
		}
		return output;
	}
	
	private static String getTask (int index, Task task, ArrayList<Integer> taskIDForRecentTask) {
		String color = CommonFunctionsInDisplay.determineColor(task);
		String repeat = CommonFunctionsInDisplay.assignRepeat(task);
		return CommonFunctionsInDisplay.getTaskDetails(index, task, color, repeat, taskIDForRecentTask, Constants.MESSAGE_COMMONFUNCTION_TABLE);
	}
}