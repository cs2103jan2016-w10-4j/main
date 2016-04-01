package Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Constants;
import main.Task;

public class CommonFunctionInDisplay {
	public static int checkRecentUpdatedTaskID(ArrayList<Task> currentList, ArrayList<PreviousInput> previousList) {
		int taskID = -1;

		for(int i = 0; i < previousList.size(); i++) {
			Task task = previousList.get(i).getTask();
			
			for(int h = 0; h < currentList.size(); h++) {
				Task currentTask = currentList.get(h);
				
				if(task.getTaskID() == currentTask.getTaskID()) {
					break;
				} else {
					taskID = task.getTaskID();
				}
			}

			if(taskID != -1) {
				return taskID;
			}
		}
		return taskID;
	}

	public static String determineColor(Task t) {
		String color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		if(t.isMultiDay()) {
			return Constants.MESSAGE_DISPLAY_COLOR_BROWN;
		}
		
		// Determine which color to display
		if (t.getStartDate() != null && t.getEndTime() == null) {
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			}
		} else if (t.getEndTime() != null && t.getStartDate() != null) {
			Date time = new Date();
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			} else if (t.getEndTime().trim().compareTo(timeFormat.format(time)) < 0
					&& t.getStartDate().compareTo(dateFormat.format(date)) == 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			}
		}
		return color;
	}

	public static String assignRepeat(Task t) {
		String repeat;
		if (t.getDay()) {
			repeat = "Every Day";
		} else if (t.getMonth()) {
			repeat = "Every Month";
		} else if (t.getWeek()) {
			repeat = "Every Week";
		} else if (t.getYear()) {
			repeat = "Every Year";
		} else {
			repeat = null;
		}
		return repeat;
	}

	// Use by DisplayStartDate, DisplayOverdue and DisplayToday
	public static String getTaskDetails(Task t, String color, String repeat, int taskIDForRecentTask) {
		String output = "";
		
		// Highlight the row if its the recent task
		if(taskIDForRecentTask == t.getTaskID()) {
			output = "<tr style=\"border-bottom:1px solid #E5E4E2\" bgcolor= #FFFF00><td align=\"right\"><h3>" + color + t.getTaskID()
			+ ")</h3></td><td><h3>" + color + t.getName() + "</h3></td>";
		} else {
			output = "<tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><h3>" + color + t.getTaskID()
			+ ")</h3></td><td><h3>" + color + t.getName() + "</h3></td>";
		}

		if (t.getStartTime() != null) {
			output += "<td><h3>" + color + t.getStartTime() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getEndTime() != null) {
			output += "<td><h3>" + color + t.getEndTime() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getDetails() != null) {
			output += "<td><h3>" + color + t.getDetails() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (repeat != null) {
			output += "<td><h3>" + color + repeat + "</h3></td>";
		}

		output += "</tr>";
		return output;
	}

	// Use by DisplayDefault and DisplayDone
	public static String getTaskDetailsForTableFormat(Task t, String color, String repeat) {
		String output = "<tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><h3>" + color + t.getTaskID()
		+ ")</h3></td><td><h3>" + color + t.getName() + "</h3></td>";

		if (t.getStartDate() != null) {
			output += "<td><h3>" + color + t.getStartDate() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getEndDate() != null) {
			output += "<td><h3>" + color + t.getEndDate() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getStartTime() != null) {
			output += "<td><h3>" + color + t.getStartTime() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getEndTime() != null) {
			output += "<td><h3>" + color + t.getEndTime() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (t.getDetails() != null) {
			output += "<td><h3>" + color + t.getDetails() + "</h3></td>";
		} else {
			output += "<td></td>";
		}

		if (repeat != null) {
			output += "<td><h3>" + color + repeat + "</h3></td>";
		}

		return output;
	}
}
