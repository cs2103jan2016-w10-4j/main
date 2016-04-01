package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayOverdue {
	static String header = "<h1><b>Overdue Tasks<b></h1>";
	static String subHeaderFont = "<h1><b>";
	static String collated;
	static String output;
	
	public static String displayOverdue(Sorting sort, ArrayList<Task> notDoneYetStorage, ArrayList<PreviousInput> previousInput) {
		DisplayByStartDate.displayFormat(sort, notDoneYetStorage, previousInput);
		output = header + subHeaderFont;
		
		if(collated == null || collated.equals(subHeaderFont)) {
			output += Constants.MESSAGE_DISPLAYOVERDUE;
		} else {
			output += collated;
		}
		output += "</b></h1>";
		return output;
	}
	
	public static void getOverdueTasks(String tasks){
		collated = tasks;
	}
}