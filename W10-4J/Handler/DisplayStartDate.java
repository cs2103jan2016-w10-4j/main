/*
 * This is the class that will be executed when user enter display 
 * In this class, the tasks are being displayed in terms of their start date
 * 
 * @@author A0126129J
 */
package Handler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.Constants;
import main.Task;
import Handler.CommonFunctionInDisplay;

public class DisplayStartDate {
	static ArrayList<Integer> taskIDForRecentTask;
	static String output;
	static String currentDate;
	static String overdueOrToday;
	static ArrayList<Task> taskWithNoStartDateList;
	static ArrayList<Task> taskWithStartDateList;
	static ArrayList<Task> multiDayTaskList;
	static ArrayList<Integer> taskToBeRemoved;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static String displayFormat(Sorting sort, ArrayList<Task> sortedList, ArrayList<PreviousInput> previousInput) {
		initializeVariables();
		
		if (sortedList.size() == 0) {
			output = Constants.MESSAGE_DISPLAY_HEADER_OPENTAG + Constants.MESSAGE_DISPLAYSTARTDATE_NOTASKONHAND 
					+ Constants.MESSAGE_DISPLAY_HEADER_CLOSETAG;
		} else {
			//taskIDForRecentTask = CommonFunctionInDisplay.checkRecentUpdatedTaskID(sortedList, previousInput);
			if(previousInput.size()!=0){
				taskIDForRecentTask = CommonFunctionInDisplay.generateChanges(sortedList, previousInput);
			} else{
				taskIDForRecentTask = new ArrayList<>();
			}
			seperateToRespectiveArrayList(sortedList);
		
			while (!(taskWithStartDateList.isEmpty())) {
				sort.sortByStartDateAndName(taskWithStartDateList);
				displayStartDateTasks();
				sort.sortByStartDateAndName(taskWithStartDateList);
			}
		
			if (!(taskWithNoStartDateList.isEmpty())) {
				sort.sortByName(taskWithNoStartDateList);
				displayNoStartDateTasks();
				sort.sortByName(taskWithNoStartDateList);
			}
		}
		return output;
	}
	
	private static void initializeVariables() {
		output = "";
		overdueOrToday = "";
		taskWithNoStartDateList = new ArrayList<Task>();
		taskWithStartDateList = new ArrayList<Task>();
		multiDayTaskList = new ArrayList<Task>();
		taskToBeRemoved = new ArrayList<Integer>();
		taskIDForRecentTask = new ArrayList<>();
	}
	
	private static void seperateToRespectiveArrayList(ArrayList<Task> taskList) {
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getStartDate() == null) {
				taskWithNoStartDateList.add(task);
			} else {
				taskWithStartDateList.add(task);
			}
		}
	}
	
	private static void displayStartDateTasks() {
		displayHeader(taskWithStartDateList);
		createTable();
		
		int index = getLatestIndexOfTaskIncludeInDisplay(taskWithStartDateList);	
		if (index != -1) {
			taskWithStartDateList = removeTaskAlreadyInDisplay(index);
			addMultiDayTaskToList();
		} else if (index == -1 && multiDayTaskList.size() != 0) {
			// User enter a multiday task immediately after opening the program or when the list is empty
			taskWithStartDateList.clear();
			addMultiDayTaskToList();
		} else {
			taskWithStartDateList.clear();
		}
		
		multiDayTaskList.clear();
		output += Constants.MESSAGE_DISPLAYSTARTDATE_TABLECLOSETAG;
		returnOutputToTheCorrectClass();
	}
	
	private static void displayNoStartDateTasks() {
		displayHeader(taskWithNoStartDateList);
		createTable();
		getLatestIndexOfTaskIncludeInDisplay(taskWithNoStartDateList);
		output += Constants.MESSAGE_DISPLAYSTARTDATE_TABLECLOSETAG;
	}
	
	// Use by both startDateTasks and noStartDateTasks
	private static void displayHeader(ArrayList<Task> taskList) {
		overdueOrToday = "";
		output += Constants.MESSAGE_DISPLAY_HEADERTABLE_OPENTAG;
		if (taskList.size() != 0) {
			currentDate = taskList.get(0).getStartDate();
			if(currentDate != null) {
				displayAppropriateDay();
			} else {
				output += Constants.MESSAGE_DISPLAYSTARTDATE_FLOATINGTASKS;
			}
		}
		output += Constants.MESSAGE_DISPLAY_HEADERTABLE_CLOSETAG;
	}
	
	private static void displayAppropriateDay() {
		if (currentDate.equals(getYesterdayDate())) {
			output += Constants.MESSAGE_DISPLAYSTARTDATE_YESTERDAY + ", ";
			overdueOrToday = Constants.MESSAGE_DISPLAYSTARTDATE_OVERDUE;
		} else if (currentDate.equals(getTodayDate())) {
			output += Constants.MESSAGE_DISPLAYSTARTDATE_TODAY + ", ";
			overdueOrToday = Constants.MESSAGE_DISPLAYSTARTDATE_TODAY;
		} else if (currentDate.equals(getTomorrowDate())) {
			output += Constants.MESSAGE_DISPLAYSTARTDATE_TOMORROW + ", ";
		} else {
			try {
				output += getCurrentDay(currentDate) + ", ";
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (currentDate.compareTo(getYesterdayDate()) < 0) {
				overdueOrToday = Constants.MESSAGE_DISPLAYSTARTDATE_OVERDUE;
			}
		}			
		output += currentDate;
	}
	
	private static void createTable() {
		output += Constants.MESSAGE_DISPLAYSTARTDATE_TABLEOPENTAG;
	}
	
	private static int getLatestIndexOfTaskIncludeInDisplay(ArrayList<Task> taskList) {		
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			
			if (task.getStartDate() == null) {
				getTaskDetails(task);
			} else if (currentDate.equals(task.getStartDate())) {
				boolean isMultiDayTask = checkIfItsMultiDayTask(task);
				if (isMultiDayTask) {
					multiDayTaskList = createSeveralTasks(task);
				} else {
					getTaskDetails(task);
				}
			} else {
				return i;
			}
		}
		return -1;
	}
	
	private static boolean checkIfItsMultiDayTask(Task task) {
		if (task.getEndDate() != null) {
			if (!(task.getEndDate().equals(task.getStartDate()))) {
				return true;
			} 
		}
		return false;
	}
	
	// Return the correct output to DisplayToday or DisplayOverdue
	private static void returnOutputToTheCorrectClass() {
		if (overdueOrToday.equals(Constants.MESSAGE_DISPLAYSTARTDATE_TODAY)) {
			String todayOutput = output.substring(output.indexOf("Today, "), output.length());
			DisplayToday.getTodayTasks(todayOutput);
		} else if (overdueOrToday.equals(Constants.MESSAGE_DISPLAYSTARTDATE_OVERDUE)) {
			DisplayOverdue.getOverdueTasks(output);
		}
	}
	
	private static String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);    
        return dateFormat.format(calendar.getTime());
	}
	
	private static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);    
        return dateFormat.format(calendar.getTime());
	}
	
	private static String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);    
        return dateFormat.format(calendar.getTime());
	}

	private static String getCurrentDay(String currentDate) throws ParseException {
		Date date = dateFormat.parse(currentDate);
		
		// The day of the week will be spelled out completely
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		String currentDay = simpleDateFormat.format(date);
		return currentDay;
	}
	
	private static void getTaskDetails(Task task) {
		String color = CommonFunctionInDisplay.determineColor(task);
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		output += CommonFunctionInDisplay.getTaskDetails(task, color, repeat, taskIDForRecentTask, Constants.MESSAGE_DISPLAYSTARTDATE_STARTDATE);
	}
	
	// Use by only startDateTasks
	private static ArrayList<Task> removeTaskAlreadyInDisplay(int index) {
		for (int i = 0; i < index; i++) {
			taskWithStartDateList.remove(0);
		}
		return taskWithStartDateList;
	}
	
	private static void addMultiDayTaskToList() {
		for (int h = 0; h < multiDayTaskList.size(); h++) {
			Task task = multiDayTaskList.get(h);
			taskWithStartDateList.add(task);
		}
	}
	
	private static ArrayList<Task> createSeveralTasks(Task task) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		try {
			Date startDate = dateFormat.parse(task.getStartDate());
			Date endDate = dateFormat.parse(task.getEndDate());
			createStartingTasks(startDate, endDate, task);
			taskList = createMiddleTasks(startDate, endDate, taskList, task);
			taskList = createEndingTasks(startDate, endDate, taskList, task);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	private static void createStartingTasks(Date startDate, Date endDate, Task task) {
		int taskID = task.getTaskID();
		String taskName = task.getName();
        String taskStartDate = task.getStartDate();
        String taskStartTime = task.getStartTime();
        
        Task newTask = new Task(taskName);
        newTask.setTaskID(taskID);
        newTask.setStartDate(taskStartDate);
        newTask.setEndTime("-");
        newTask.setDetails(task.getDetails());
        newTask.setMultiDay(true);
        
        if (taskStartTime != null) {
            newTask.setStartTime(taskStartTime);
        }
        
        getTaskDetails(newTask);
	}
	
	private static ArrayList<Task> createMiddleTasks(Date startDate, Date endDate, ArrayList<Task> taskList, Task task) throws ParseException {
        int taskID = task.getTaskID();
		String taskName = task.getName();
        String taskEndDate = dateFormat.format(endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
    	calendar.add(Calendar.DATE, +1);

        boolean flag = true;
        while (flag) {      
            Date date = calendar.getTime();
            String dateValue = dateFormat.format(date);
            startDate = dateFormat.parse(dateValue);
            calendar.setTime(startDate);
        	// Increment startDate by 1
        	calendar.add(Calendar.DATE, +1);

            if (!dateValue.equals(taskEndDate)) {
            	Task newTask = new Task(taskName);
                newTask.setTaskID(taskID);
                newTask.setStartDate(dateValue);
                newTask.setStartTime("-");
                newTask.setEndTime("-");
                newTask.setDetails(task.getDetails());
                newTask.setMultiDay(true);
                taskList.add(newTask);
            } else {
            	flag = false;
            }
        }
        return taskList;
	}
	
	private static ArrayList<Task> createEndingTasks(Date startDate, Date endDate, ArrayList<Task> taskList, Task task) {
	    int taskID = task.getTaskID();
		String taskName = task.getName();
        String taskEndDate = task.getEndDate();
        String taskEndTime = task.getEndTime();
 
        Task newTask = new Task(taskName);
        newTask.setTaskID(taskID);
        newTask.setStartDate(taskEndDate);
        newTask.setEndDate(taskEndDate);
        newTask.setStartTime("-");
        newTask.setDetails(task.getDetails());
        newTask.setMultiDay(true);
        
        if (taskEndTime != null) {
            newTask.setEndTime(taskEndTime);
        }
        
        taskList.add(newTask);
        return taskList;
	}
}