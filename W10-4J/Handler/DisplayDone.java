package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;

public class DisplayDone {	
	public static String displayDoneFormat(ArrayList<Task> sortedList) {
		String output = "";
		
		if(sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG + Constants.MESSAGE_DISPLAYDONE_NOTASKDONE + Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG;
		} else {
			output = Constants.MESSAGE_DISPLAY_TABLEANDHEADER;
			
			for(int i = 0; i < sortedList.size(); i++) {
				Task task = sortedList.get(i);
				output += getTask(task);
			}
			
			output += Constants.MESSAGE_DISPLAY_TABLECLOSETAG;
		}
		return output;
	}
	
	private static String getTask(Task task) {
		String color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		return CommonFunctionInDisplay.getTaskDetailsForTableFormat(task, color, repeat);
	}
}