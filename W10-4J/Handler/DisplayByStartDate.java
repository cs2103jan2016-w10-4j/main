package Handler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import main.Constants;
import main.Task;
import Handler.CommonFunctionInDisplay;

public class DisplayByStartDate {
	static String output;
	static String currentDate;
	static ArrayList<Task> noStartDateList;
	static ArrayList<Task> haveStartDateList;
	static ArrayList<Task> multiDayTaskList;
	static ArrayList<Integer> taskToBeRemoved;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	static int counter = 0; //-1: overdue, 1: today
	
	public static String displayFormat(Sorting sort, ArrayList<Task> sortedList) {
		output = "";
		noStartDateList = new ArrayList<Task>();
		haveStartDateList = new ArrayList<Task>();
		multiDayTaskList = new ArrayList<Task>();
		taskToBeRemoved = new ArrayList<Integer>();
		
		if(sortedList.size() == 0) {
			output += Constants.MESSAGE_ALLDISPLAYS_NOTASKONHAND;
		} else {
			seperateToRespectiveArrayList(sortedList);
		
			while(!(haveStartDateList.isEmpty())) {
				sortByStartDate(haveStartDateList);
				displayingTasks();
			}
		
			if(!(noStartDateList.isEmpty())) {
				sort.sortByName(noStartDateList);
				displayingNoStartDateTasks();
			}
		}
		
		return output;
	}
	
	private static void seperateToRespectiveArrayList(ArrayList<Task> taskList) {
		for(int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if(task.getStartDate() == null) {
				noStartDateList.add(task);
			} else {
				haveStartDateList.add(task);
			}
		}
	}
	
	private static void displayingTasks() {
		getHeader(haveStartDateList);
		createTable();
		
		int index = getTaskDetails(haveStartDateList);	
		if(index != -1) {
			haveStartDateList = removeTaskAlreadyDisplay(index);
			addMultiDayTaskToList();
			sortByStartDate(haveStartDateList);
		} else if (index == -1 && multiDayTaskList.size() != 0){
			haveStartDateList.clear();
			addMultiDayTaskToList();
			sortByStartDate(haveStartDateList);
		}
		else {
			haveStartDateList.clear();
		}
		
		multiDayTaskList.clear();
		output += "</table>";
		
		if(counter == 1) {
			String test = output.substring(output.indexOf("Today, "), output.length());
			DisplayToday.getTodayTasks(test);
		} else if (counter == -1) {
			DisplayOverdue.getOverdueTasks(output);
		}
	}
	
	private static void displayingNoStartDateTasks() {
		getHeader(noStartDateList);
		createTable();
		getTaskDetails(noStartDateList);
		output += "</table>";
	}
	
	private static void getHeader(ArrayList<Task> taskList) {
		counter = 0;
		output += "<font face = \"Helvetica\" size = \"6\"><b>";
		if(taskList.size() != 0) {
			currentDate = taskList.get(0).getStartDate();
			if(currentDate != null) {
				if(currentDate.equals(getYesterdayDate())) {
					output += Constants.MESSAGE_DISPLAYFORMAT_YESTERDAY + ", ";
					counter = -1;
				} else if(currentDate.equals(getTodayDate())) {
					DisplayOverdue.getOverdueTasks(output);
					output += Constants.MESSAGE_DISPLAYFORMAT_TODAY + ", ";
					counter = 1;
				} else if(currentDate.equals(getTomorrowDate())) {
					String test = output.substring(output.indexOf("Today, "), output.length());
					DisplayToday.getTodayTasks(test);
					output += Constants.MESSAGE_DISPLAYFORMAT_TOMORROW + ", ";
				} else {
					try {
						output += getCurrentDay(currentDate) + ", ";
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if(currentDate.compareTo(getYesterdayDate()) < 0) {
						counter = -1;
					}
				}			
				output += currentDate + "</b></font>";
			} else {
				output += Constants.MESSAGE_DISPLAYFORMAT_NOSTARTDATE;
			}
		}
	}
	
	private static void createTable() {
		output += "<table width=\"100%\" style=\"margin-top:5px; margin-bottom:15px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"><font face = \"Arial\" size = \"5\"><b> Event <b></th><th style=\"width:15%;\" align=\"left\"><font face = \"Arial\" size = \"5\"><b> Start Time <b></th><th style=\"width:15%;\" align=\"left\"><font face = \"Arial\" size = \"5\"><b> End Time <b></th><th style=\"width:25%;\" align=\"left\"><font face = \"Arial\" size = \"5\"><b> Details </b></th><th style=\"width:15%;\" align=\"left\"><font face = \"Arial\" size = \"5\"><b> Repeat <b></th></ltr>";
	}
	
	private static int getTaskDetails(ArrayList<Task> taskList) {		
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if(task.getStartDate() == null) {
				getTask(task);
			} else if (currentDate.equals(task.getStartDate())) {
				if(task.getEndDate() != null) {
					if(task.getEndDate().equals(task.getStartDate())) {
						getTask(task);
					} else {
						multiDayTaskList = createSeveralTasks(task);
					}
				} else {				
					getTask(task);
				}
			} else {
				return i;
			}
		}
		return -1;
	}
	
	private static void sortByStartDate(ArrayList<Task> taskList){
		Collections.sort(taskList, Task.taskStartDateNameComparator);
	}
	
	private static ArrayList<Task> removeTaskAlreadyDisplay(int index) {
		for(int i = 0; i < index; i++) {
			haveStartDateList.remove(0);
		}
		return haveStartDateList;
	}
	
	private static void addMultiDayTaskToList(){
		for(int h = 0; h < multiDayTaskList.size(); h++) {
			Task t = multiDayTaskList.get(h);
			haveStartDateList.add(t);
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
		
		// The day of the week spelled out completely
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
		String currentDay = simpleDateFormat.format(date);
		return currentDay;
	}

	private static void getTask(Task task) {
		String color = CommonFunctionInDisplay.determineColor(task);
		String repeat = CommonFunctionInDisplay.assignRepeat(task);
		output += CommonFunctionInDisplay.getTaskDetails(task, color, repeat);
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
        String taskName = task.getName();
        String taskStartDate = task.getStartDate();
        String taskStartTime = task.getStartTime();
        int taskID = task.getTaskID();
        Task newTask = new Task(taskName);
        newTask.setTaskID(taskID);
        newTask.setStartDate(taskStartDate);
        newTask.setDetails(Constants.MESSAGE_DISPLAYFORMAT_MULTIDAYTASK);
        
        if(taskStartTime != null) {
            newTask.setStartTime(taskStartTime);
        }        
        getTask(newTask);
	}
	
	private static ArrayList<Task> createMiddleTasks(Date startDate, Date endDate, ArrayList<Task> taskList, Task task) throws ParseException {
		String taskName = task.getName();
        String taskEndDate = dateFormat.format(endDate);
        int taskID = task.getTaskID();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
    	calendar.add(Calendar.DATE, +1);

        boolean flag = true;
        
        while (flag) {      
            Date date = calendar.getTime();
            String dateValue = dateFormat.format(date);
            startDate = dateFormat.parse(dateValue);
            calendar.setTime(startDate);
        	calendar.add(Calendar.DATE, +1);

            if(!dateValue.equals(taskEndDate)) {
            	Task newTask = new Task(taskName);
                newTask.setTaskID(taskID);
                newTask.setStartDate(dateValue);
                newTask.setDetails(Constants.MESSAGE_DISPLAYFORMAT_MULTIDAYTASK);
                taskList.add(newTask);
            } else {
            	flag = false;
            }
        }
        return taskList;
	}
	
	private static ArrayList<Task> createEndingTasks(Date startDate, Date endDate, ArrayList<Task> taskList, Task task) {
        String taskName = task.getName();
        String taskEndDate = task.getEndDate();
        String taskEndTime = task.getEndTime();
        int taskID = task.getTaskID();
        Task newTask = new Task(taskName);
        newTask.setTaskID(taskID);
        newTask.setStartDate(taskEndDate);
        newTask.setEndDate(taskEndDate);
        newTask.setDetails(Constants.MESSAGE_DISPLAYFORMAT_MULTIDAYTASK);
        
        if(taskEndTime != null) {
            newTask.setEndTime(taskEndTime);
        }
        taskList.add(newTask);
        return taskList;
	}
}