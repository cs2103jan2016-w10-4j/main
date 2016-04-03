package Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Constants;
import main.Task;

public class CommonFunctionInDisplay {
	public static int checkRecentUpdatedTaskID(ArrayList<Task> currentList, ArrayList<PreviousInput> previousList) {
		int taskID = -1;
		
		if(previousList.size() == 1 && previousList.get(0).getAction().equals(Constants.MESSAGE_COMMONFUNCTION_RETRIEVE)) {
			return -1;
		}
		
		// When user first add a task into the empty file
		if(previousList.size() == 1 && currentList.size() == 1) {
			Task previousTask = previousList.get(0).getTask();
			Task currentTask = currentList.get(0);
			if(previousTask.getTaskID() == currentTask.getTaskID()) {
				return previousTask.getTaskID();
			}
		}
		
		for(int i = 0; i < previousList.size(); i++) {
			Task previousTask = previousList.get(i).getTask();
			
			for(int h = 0; h < currentList.size(); h++) {
				Task currentTask = currentList.get(h);
				
				if(previousTask.getTaskID() == currentTask.getTaskID()) {
					boolean isTwoTasksTheSame = compareTasks(previousTask, currentTask);
					if(!(isTwoTasksTheSame)) {
						taskID = previousTask.getTaskID();
					}
				} else {
					taskID = previousTask.getTaskID();
				}
			}

			if(taskID != -1) {
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
		
		if(isSameName && isSameStartDate && isSameEndDate && isSameStartTime && isSameEndTime && isSameDetails) {
			return true;
		}
		return false;
	}
	
	private static boolean compareName(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getName() == null && currentTask.getName() == null) {
			return true;
		} else if (previousTask.getName() != null || currentTask.getName() != null) {
			return false;
		} else { 
			compareValue = Task.taskNameComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean compareStartDate(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getStartDate() == null && currentTask.getStartDate() == null) {
			return true;
		} else if (previousTask.getStartDate() != null || currentTask.getStartDate() != null) {
			return false;
		} else {
			compareValue = Task.taskStartDateComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean compareEndDate(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getEndDate() == null && currentTask.getEndDate() == null) {
			return true;
		} else if (previousTask.getEndDate() != null || currentTask.getEndDate() != null) {
			return false;
		} else {
			compareValue = Task.taskEndDateComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean compareStartTime(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getStartTime() == null && currentTask.getStartTime() == null) {
			return true;
		} else if(previousTask.getStartTime() != null || currentTask.getStartTime() != null) {
			return false;
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) && currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) || currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return false;
		} else {
			compareValue = Task.taskStarttimeComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean compareEndTime(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getEndTime() == null && currentTask.getEndTime() == null) {
			return true;
		} else if(previousTask.getEndTime() != null || currentTask.getEndTime() != null) {
			return false;
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) && currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) || currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return false;
		} else {
			compareValue = Task.taskEndtimeComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean compareDetails(Task previousTask, Task currentTask) {
		int compareValue;
		if (previousTask.getDetails() == null && currentTask.getDetails() == null) {
			return true;
		} else if (previousTask.getDetails() != null || currentTask.getDetails() != null) {
			return false;
		} else {
			compareValue = Task.taskDetailsComparator.compare(previousTask, currentTask);
			if(compareValue == 0) {
				return true;
			} else {
				return false;
			}
		}
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
			output = Constants.MESSAGE_COMMONFUNCTION_TRHIGHLIGHT_OPENTAG + Constants.MESSAGE_COMMONFUNCTION_TD_ALIGN + Constants.MESSAGE_COMMONFUNCTION_HEADER_OPENTAG + color + t.getTaskID()
			+ ")" + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getName() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output = Constants.MESSAGE_COMMONFUNCTION_TR_OPENTAG + Constants.MESSAGE_COMMONFUNCTION_TD_ALIGN + Constants.MESSAGE_COMMONFUNCTION_HEADER_OPENTAG + color + t.getTaskID()
			+ ")" + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getName() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		}

		if (t.getStartTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getStartTime() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getEndTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getEndTime() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getDetails() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getDetails() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (repeat != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + repeat + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		}

		output += Constants.MESSAGE_COMMONFUNCTION_HEADER_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TR_CLOSETAG;
		return output;
	}

	// Use by DisplayDefault and DisplayDone
	public static String getTaskDetailsForTableFormat(Task t, String color, String repeat) {
		String output = Constants.MESSAGE_COMMONFUNCTION_TR_OPENTAG + Constants.MESSAGE_COMMONFUNCTION_TD_ALIGN + Constants.MESSAGE_COMMONFUNCTION_HEADER_OPENTAG + color + t.getTaskID()
		+ ")" + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getName() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;

		if (t.getStartDate() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getStartDate() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getEndDate() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getEndDate() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getStartTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getStartTime() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getEndTime() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getEndTime() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (t.getDetails() != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + t.getDetails() + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		} else {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG;
		}

		if (repeat != null) {
			output += Constants.MESSAGE_COMMONFUNCTION_TD_OPENTAG + color + repeat + Constants.MESSAGE_COMMONFUNCTION_TD_CLOSETAG;
		}

		output += Constants.MESSAGE_COMMONFUNCTION_HEADER_CLOSETAG + Constants.MESSAGE_COMMONFUNCTION_TR_CLOSETAG;
		return output;
	}
}