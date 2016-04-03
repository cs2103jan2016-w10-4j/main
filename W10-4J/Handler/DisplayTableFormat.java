package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;

public class DisplayTableFormat {	
	public static String displayTableFormat(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		String output = "";
		
		if(sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_SUBHEADER_OPENTAG + Constants.MESSAGE_DISPLAYTABLEFORMAT_NOTASKONHAND + Constants.MESSAGE_DISPLAY_SUBHEADER_CLOSETAG;
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
		String color = CommonFunctionInDisplay.determineColor(task);
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		return CommonFunctionInDisplay.getTaskDetailsForTableFormat(task, color, repeat);
	}
}