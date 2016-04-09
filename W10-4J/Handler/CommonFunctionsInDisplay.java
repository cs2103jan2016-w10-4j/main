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
		int taskID = Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST;

		// Applicable if user calls retrieve method
		if (previousList.size() == 1
				&& previousList.get(0).getAction().equals(Constants.MESSAGE_COMMONFUNCTION_RETRIEVE)) {
			return Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST;
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

			if (taskID != Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST) {
				return taskID;
			}
		}
		return taskID;
	}

	private static boolean compareTasks(Task previousTask, Task currentTask) {
		boolean isSameName = compareName(previousTask, currentTask);
		boolean isSameStartDate = compareStartDate(previousTask, currentTask);
		boolean isSameEndDate = compareEndDate(previousTask, currentTask);
		boolean isSameStartTime = compareStartTime(previousTask, currentTask);
		boolean isSameEndTime = compareEndTime(previousTask, currentTask);
		boolean isSameDetails = compareDetails(previousTask, currentTask);
		boolean isSameTaskID = compareTaskID(previousTask, currentTask);

		if (isSameName && isSameStartDate && isSameEndDate && isSameStartTime 
				&& isSameEndTime && isSameDetails && isSameTaskID) {
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
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)
				&& currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) 
				&& !(currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH))) {
			return false;
		} else if (!(previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) 
				&& currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
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
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)
				&& currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) 
				&& !(currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH))) {
			return false;
		} else if (!(previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) 
				&& currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
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

	//@@author A0126129J
	public static String determineColor(Task t) {
		String color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		// Determine which color to display
		if (t.getStartDate() != null && (t.getEndTime() == null || t.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH))) {
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			} else if (t.isMultiDay()) {
				color = Constants.MESSAGE_DISPLAY_COLOR_BLUE;
			} else {
				color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
			}
		} else if (t.getEndTime() != null && t.getStartDate() != null) {
			Date time = new Date();
			Date date = new Date();
			if (t.getStartDate().trim().compareTo(dateFormat.format(date)) < 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			} else if (t.getEndTime().trim().compareTo(timeFormat.format(time)) < 0
					&& t.getStartDate().compareTo(dateFormat.format(date)) == 0) {
				color = Constants.MESSAGE_DISPLAY_COLOR_RED;
			} else if (t.isMultiDay()) {
				color = Constants.MESSAGE_DISPLAY_COLOR_BLUE;
			} else {
				color = Constants.MESSAGE_DISPLAY_COLOR_BLACK;
			}
		}
		
		return color;
	}

	public static String assignRepeat(Task t) {
		String repeat;
		if (t.getDay()) {
			repeat = Constants.MESSAGE_COMMONFUNCTION_REPEATDAY;
		} else if (t.getMonth()) {
			repeat = Constants.MESSAGE_COMMONFUNCTION_REPEATMONTH;
		} else if (t.getWeek()) {
			repeat = Constants.MESSAGE_COMMONFUNCTION_REPEATWEEK;
		} else if (t.getYear()) {
			repeat = Constants.MESSAGE_COMMONFUNCTION_REPEATYEAR;
		} else {
			repeat = null;
		}
		return repeat;
	}

	public static String getTaskDetails(int index, Task t, String color, String repeat, 
			ArrayList<Integer> taskIDForRecentTask, String action) {
		String output = "";

		// Highlight the row if its the recent task
		if (taskIDForRecentTask != null && taskIDForRecentTask.contains(t.getTaskID())) {
			output = Constants.MESSAGE_COMMONFUNCTION_TRHIGHLIGHT_OPENTAG + Constants.MESSAGE_COMMONFUNCTION_TD_ALIGN
					+ Constants.MESSAGE_COMMONFUNCTION_HEADER_OPENTAG + color + index + ")"
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color
					+ t.getName() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output = Constants.MESSAGE_COMMONFUNCTION_TR_OPENTAG + Constants.MESSAGE_COMMONFUNCTION_TD_ALIGN
					+ Constants.MESSAGE_COMMONFUNCTION_HEADER_OPENTAG + color + index + ")"
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color
					+ t.getName() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		}

		// Check if its called from DisplayDone, DisplayTableFormat
		if (action.equals(Constants.MESSAGE_COMMONFUNCTION_DONE)
				|| action.equals(Constants.MESSAGE_COMMONFUNCTION_TABLE)) {
			if (t.getStartDate() != null) {
				output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getStartDate()
						+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
			} else {
				output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
			}

			if (t.getEndDate() != null) {
				output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getEndDate()
						+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
			} else {
				output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
			}
		}

		if (t.getStartTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getStartTime()
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getEndTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getEndTime()
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getDetails() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getDetails()
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (repeat != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + repeat
					+ Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		}

		output += Constants.MESSAGE_COMMONFUNCTION_HEADER_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TR_CLOSETAG;
		return output;
	}

	public static ArrayList<Integer> generateChanges(ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		ArrayList<Task> previousList = previousInput.get(0).getPreviousNotDoneStorage();
		ArrayList<Integer> output = new ArrayList<>();
		
		// When user first add a task into the empty file
		if (previousList.size() != 0) {
			output = addNotFoundTaskToArraylist(sortedList, previousList);
		} else {
			for (int i = 0; i < sortedList.size(); i++) {
				output.add(sortedList.get(i).getTaskID());
			}
		}
		return output;
	}

	private static ArrayList<Integer> addNotFoundTaskToArraylist(ArrayList<Task> sortedList, ArrayList<Task> previousList) {
		ArrayList<Integer> output = new ArrayList<>();
		
		for (int i = 0; i < sortedList.size(); i++) {
			boolean found = false;
			for (int j = 0; j < previousList.size(); j++) {
				if (compareTasks(previousList.get(j), sortedList.get(i))) {
					found = true;
					continue;
				}
			}
			
			if (!found) {
				output.add(sortedList.get(i).getTaskID());
			}
		}
		return output;
	}
}