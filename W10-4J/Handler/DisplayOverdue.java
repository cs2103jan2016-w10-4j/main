package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import Handler.Sorting;

public class DisplayOverdue {
	static String header = "<font face = \"Helvetica\" size = \"10\"><b>Overdue Task<b>";
	static String subHeaderFont = "<font face = \"Helvetica\" size = \"6\"><b>";
	static String collated;
	static String output;
	
	public static String displayOverdue(Sorting sort, ArrayList<Task> notDoneYetStorage) {
		DisplayByStartDate.displayFormat(sort, notDoneYetStorage);
		output = header + "<p>" + subHeaderFont;
		
		if(collated == null || collated.equals(subHeaderFont)) {
			output += Constants.MESSAGE_DISPLAYOVERDUE;
		} else {
			output += collated;
		}
		
		return output;
	}
	
	public static void getOverdueTasks(String tasks){
		collated = tasks;
	}
}