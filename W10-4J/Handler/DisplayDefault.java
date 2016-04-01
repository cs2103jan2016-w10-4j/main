package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;

public class DisplayDefault {	
	public static String displayDefaultFormat(ArrayList<Task> sortedList) {
		String output = "";
		if(sortedList.size() == 0) {
			output += Constants.MESSAGE_ALLDISPLAYS_NOTASKONHAND;
		} else {
			output = "<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\">Start Date </th><th style=\"width:15%;\" align=\"left\">End Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr>";
		
			for(int i = 0; i < sortedList.size(); i++) {
				Task task = sortedList.get(i);
				output += getTask(task);
			}
		}
		return output;
	}
	
	private static String getTask(Task task) {
		String color = CommonFunctionInDisplay.determineColor(task);
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		return CommonFunctionInDisplay.getTaskDetailsWithStartEndDate(task, color, repeat);
	}
}