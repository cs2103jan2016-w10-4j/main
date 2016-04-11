/*
 * This is the class that consist of functions that are usable by 
 * DisplayDone, DisplayTableFormat, DisplayStartDate, DisplayToday and DisplayOverdue
 * 
 * @@author A0126129J
 */
package Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Constants;
import main.Task;

public class CommonFunctionsInDisplay {
	//@@author A0126129J-unused
	/* This method is implemented previously for highlights when 
	 * <PreviousInput> store task object instead of the state
	 */
	public static int checkRecentUpdatedTaskID(ArrayList<Task> currentList, ArrayList<PreviousInput> previousList) {
		int taskID = Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST;

		// Applicable if user calls retrieve method
		if (previousList.size() == 1
				&& previousList.get(0).getAction().equals(Constants.COMMONFUNCTIONS_RETRIEVE)) {
			return Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST;
		}

		// When user first add a task into the empty file
		if (previousList.size() == 1 && currentList.size() == 1) {
			Task previousTask = previousList.get(0).getTask();
			Task currentTask = currentList.get(0);
			if (previousTask.getTaskID() == currentTask.getTaskID()) {
				return previousTask.getTaskID();
			}
		}

		for (int i = 0; i < previousList.size(); i++) {
			Task previousTask = previousList.get(i).getTask();

			for (int h = 0; h < currentList.size(); h++) {
				Task currentTask = currentList.get(h);
				if (previousTask.getTaskID() == currentTask.getTaskID()) {
					boolean isTwoTasksTheSame = compareTasks(previousTask, currentTask);
					if (!(isTwoTasksTheSame)) {
						taskID = previousTask.getTaskID();
					}
				} else {
					taskID = previousTask.getTaskID();
				}
			}

			if (taskID != Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST) {
				return taskID;
			}
		}
		return taskID;
	}
	
	//@@author A0126129J
	// Determine which task is not in the previousList when comparing with currentList
	public static ArrayList<Integer> checkRecentUpdatedTask(ArrayList<Task> currentList, ArrayList<PreviousInput> previousInput, String status) {
		ArrayList<Task> previousList = new ArrayList<Task>();
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		if(status.equals(Constants.DISPLAY_NOT_DISPLAYDONE)) {
			previousList = previousInput.get(0).getPreviousNotDoneStorage();
		} else {
			previousList = previousInput.get(0).getPreviousDoneStorage();
		}
		
		if (previousList != null && previousList.size() != 0) {
			output = addNotFoundTaskIDToArraylist(currentList, previousList);
		} else {
			// When user first add a task into the empty file
			for (int i = 0; i < currentList.size(); i++) {
				output.add(currentList.get(i).getTaskID());
			}
		}
		return output;
	}

	private static ArrayList<Integer> addNotFoundTaskIDToArraylist(ArrayList<Task> currentList, ArrayList<Task> previousList) {
		ArrayList<Integer> output = new ArrayList<Integer>();

		for (int i = 0; i < currentList.size(); i++) {
			boolean isTwoTasksTheSame = false;
			Task currentTask = currentList.get(i);
			for (int j = 0; j < previousList.size(); j++) {
				Task previousTask = previousList.get(j);
				isTwoTasksTheSame = compareTasks(previousTask, currentTask);	
				if(isTwoTasksTheSame) {
					break;
				}
			}
			
			if (!(isTwoTasksTheSame)) {
				output.add(currentTask.getTaskID());
				break;
			} 
		}
		return output;
	}
	
	private static boolean compareTasks(Task previousTask, Task currentTask) {
		boolean isSameName = compareName(previousTask, currentTask);
		boolean isSameStartDate = compareStartDate(previousTask, currentTask);
		boolean isSameEndDate = compareEndDate(previousTask, currentTask);
		boolean isSameStartTime = compareStartTime(previousTask, currentTask);
		boolean isSameEndTime = compareEndTime(previousTask, currentTask);
		boolean isSameDetails = compareDetails(previousTask, currentTask);
		boolean isSameTaskID = compareTaskID(previousTask, currentTask);
		boolean isSameDay = compareDay(previousTask, currentTask);
		boolean isSameWeek = compareWeek(previousTask, currentTask);
		boolean isSameMonth = compareMonth(previousTask, currentTask);
		boolean isSameYear = compareYear(previousTask, currentTask);

		if (isSameName && isSameStartDate && isSameEndDate && isSameStartTime 
				&& isSameEndTime && isSameDetails && isSameTaskID 
				&& isSameDay && isSameWeek && isSameMonth && isSameYear) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareName(Task previousTask, Task currentTask) {
		if (previousTask.getName() == null && currentTask.getName() == null) {
			return true;
		} else if (previousTask.getName() != null && currentTask.getName() == null) {
			return false;
		} else if (previousTask.getName() == null && currentTask.getName() != null) {
			return false;
		} else if (previousTask.getName().equals(currentTask.getName())) { 
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareStartDate(Task previousTask, Task currentTask) {
		if (previousTask.getStartDate() == null && currentTask.getStartDate() == null) {
			return true;
		} else if (previousTask.getStartDate() == null && currentTask.getStartDate() != null) {
			return false;
		} else if (previousTask.getStartDate() != null && currentTask.getStartDate() == null) {
			return false;
		} else if (previousTask.getStartDate().equals(currentTask.getStartDate())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareEndDate(Task previousTask, Task currentTask) {
		if (previousTask.getEndDate() == null && currentTask.getEndDate() == null) {
			return true;
		} else if (previousTask.getEndDate() == null && currentTask.getEndDate() != null) {
			return false;
		} else if (previousTask.getEndDate() != null && currentTask.getEndDate() == null) {
			return false;
		} else if (previousTask.getEndDate().equals(currentTask.getEndDate())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareStartTime(Task previousTask, Task currentTask) {
		if (previousTask.getStartTime() == null && currentTask.getStartTime() == null) {
			return true;
		} else if (previousTask.getStartTime() == null && currentTask.getStartTime() != null) {
			return false;
		} else if (previousTask.getStartTime() != null && currentTask.getStartTime() == null) {
			return false;
		} else if (previousTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH)
				&& currentTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH)) {
			return true;
		} else if (previousTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH) 
				&& !(currentTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH))) {
			return false;
		} else if (!(previousTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH)) 
				&& currentTask.getStartTime().equals(Constants.COMMONFUNCTIONS_DASH)) {
			return false;
		} else if (previousTask.getStartTime().equals(currentTask.getStartTime())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareEndTime(Task previousTask, Task currentTask) {
		if (previousTask.getEndTime() == null && currentTask.getEndTime() == null) {
			return true;
		} else if (previousTask.getEndTime() == null && currentTask.getEndTime() != null) {
			return false;
		} else if (previousTask.getEndTime() != null && currentTask.getEndTime() == null) {
			return false;
		} else if (previousTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH)
				&& currentTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH)) {
			return true;
		} else if (previousTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH) 
				&& !(currentTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH))) {
			return false;
		} else if (!(previousTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH)) 
				&& currentTask.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH)) {
			return false;
		} else if (previousTask.getEndTime().equals(currentTask.getEndTime())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareDetails(Task previousTask, Task currentTask) {
		if (previousTask.getDetails() == null && currentTask.getDetails() == null) {
			return true;
		} else if (previousTask.getDetails() == null && currentTask.getDetails() != null) {
			return false;
		} else if (previousTask.getDetails() != null && currentTask.getDetails() == null) {
			return false;
		} else if (previousTask.getDetails().equals(currentTask.getDetails())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareTaskID(Task previousTask, Task currentTask) {
		return previousTask.getTaskID() == currentTask.getTaskID();
	}
	
	private static boolean compareDay(Task readFromFileTask, Task task) {
		if (readFromFileTask.getDay() == false && task.getDay() == false) {
			return true;
		} else if (readFromFileTask.getDay() == false && task.getDay() == true) {
			return false;
		} else if (readFromFileTask.getDay() == true && task.getDay() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean compareWeek(Task readFromFileTask, Task task) {
		if (readFromFileTask.getWeek() == false && task.getWeek() == false) {
			return true;
		} else if (readFromFileTask.getWeek() == false && task.getWeek() == true) {
			return false;
		} else if (readFromFileTask.getWeek() == true && task.getWeek() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean compareMonth(Task readFromFileTask, Task task) {
		if (readFromFileTask.getMonth() == false && task.getMonth() == false) {
			return true;
		} else if (readFromFileTask.getMonth() == false && task.getMonth() == true) {
			return false;
		} else if (readFromFileTask.getMonth() == true && task.getMonth() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean compareYear(Task readFromFileTask, Task task) {
		if (readFromFileTask.getYear() == false && task.getYear() == false) {
			return true;
		} else if (readFromFileTask.getYear() == false && task.getYear() == true) {
			return false;
		}  else if (readFromFileTask.getYear() == true && task.getYear() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static String determineColor(Task t) {
		String color = Constants.DISPLAY_COLOR_BLACK;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		// Determine which color to display
		if (t.getStartDate() != null && (t.getEndTime() == null || t.getEndTime().equals(Constants.COMMONFUNCTIONS_DASH))) {
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.DISPLAY_COLOR_RED;
			} else if (t.isMultiDay()) {
				color = Constants.DISPLAY_COLOR_BLUE;
			} else {
				color = Constants.DISPLAY_COLOR_BLACK;
			}
		} else if (t.getEndTime() != null && t.getStartDate() != null) {
			Date time = new Date();
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.DISPLAY_COLOR_RED;
			} else if (t.getEndTime().trim().compareTo(timeFormat.format(time)) < 0
					&& t.getStartDate().compareTo(dateFormat.format(date)) == 0) {
				color = Constants.DISPLAY_COLOR_RED;
			} else if (t.isMultiDay()) {
				color = Constants.DISPLAY_COLOR_BLUE;
			} else {
				color = Constants.DISPLAY_COLOR_BLACK;
			}
		}

		return color;
	}

	public static String assignRepeat(Task t) {
		String repeat;
		if (t.getDay()) {
			repeat = Constants.COMMONFUNCTIONS_REPEAT_DAY;
		} else if (t.getMonth()) {
			repeat = Constants.COMMONFUNCTIONS_REPEAT_MONTH;
		} else if (t.getWeek()) {
			repeat = Constants.COMMONFUNCTIONS_REPEAT_WEEK;
		} else if (t.getYear()) {
			repeat = Constants.COMMONFUNCTIONS_REPEAT_YEAR;
		} else {
			repeat = null;
		}
		return repeat;
	}

	public static String getTaskDetails(int index, Task t, String color, String repeat, 
			ArrayList<Integer> taskIDForRecentTask, String action) {
		String output = Constants.COMMONFUNCTIONS_EMPTY_STRING;

		// Highlight the row if its the recent task
		if (taskIDForRecentTask != null && taskIDForRecentTask.contains(t.getTaskID())) {
			output = Constants.COMMONFUNCTIONS_TRHIGHLIGHT_OPENTAG + Constants.COMMONFUNCTIONS_TD_ALIGN
					+ Constants.COMMONFUNCTIONS_HEADER_OPENTAG + color + index + ")"
					+ Constants.COMMONFUNCTIONS_TD_CLOSETAG + Constants.COMMONFUNCTIONS_TD_OPENTAG + color
					+ t.getName() + Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		} else {
			output = Constants.COMMONFUNCTIONS_TR_OPENTAG + Constants.COMMONFUNCTIONS_TD_ALIGN
					+ Constants.COMMONFUNCTIONS_HEADER_OPENTAG + color + index + ")"
					+ Constants.COMMONFUNCTIONS_TD_CLOSETAG + Constants.COMMONFUNCTIONS_TD_OPENTAG + color
					+ t.getName() + Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		}

		// Check if its called from DisplayDone, DisplayTableFormat
		if (action.equals(Constants.COMMONFUNCTIONS_DONE)
				|| action.equals(Constants.COMMONFUNCTIONS_TABLE)) {
			if (t.getStartDate() != null) {
				output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + t.getStartDate()
				+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
			} else {
				output += Constants.COMMONFUNCTIONS_TD_OPENCLOSETAG;
			}

			if (t.getEndDate() != null) {
				output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + t.getEndDate()
				+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
			} else {
				output += Constants.COMMONFUNCTIONS_TD_OPENCLOSETAG;
			}
		}

		if (t.getStartTime() != null) {
			output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + t.getStartTime()
			+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		} else {
			output += Constants.COMMONFUNCTIONS_TD_OPENCLOSETAG;
		}

		if (t.getEndTime() != null) {
			output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + t.getEndTime()
			+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		} else {
			output += Constants.COMMONFUNCTIONS_TD_OPENCLOSETAG;
		}

		if (t.getDetails() != null) {
			output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + t.getDetails()
			+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		} else {
			output += Constants.COMMONFUNCTIONS_TD_OPENCLOSETAG;
		}

		if (repeat != null) {
			output += Constants.COMMONFUNCTIONS_TD_OPENTAG + color + repeat 
					+ Constants.COMMONFUNCTIONS_TD_CLOSETAG;
		}

		output += Constants.COMMONFUNCTIONS_HEADER_CLOSETAG + Constants.COMMONFUNCTIONS_TR_CLOSETAG;
		return output;
	}
}