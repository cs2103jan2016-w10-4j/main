package main;
import java.util.Comparator;
import java.util.Calendar;

public class Task {
	private String name_;
	private String date_;
	private String startTime_;
	private String endTime_;
	private String details_;
	
	public static Comparator<Task> taskNameComparator = new Comparator<Task>(){
		public int compare(Task task1, Task task2){
			return (task1.getName().toLowerCase().compareTo(task2.getName().toLowerCase()));
		}
	};
	public static Comparator<Task> taskStarttimeComparator = new Comparator<Task>(){
		public int compare(Task task1, Task task2){
			String[] startTime1 = task1.getStartTime().split(":");
			String[] startTime2 = task2.getStartTime().split(":");
			int hour1 = Integer.parseInt(startTime1[0]);
			int hour2 = Integer.parseInt(startTime2[0]);
			int min1 = Integer.parseInt(startTime1[1]);
			int min2 = Integer.parseInt(startTime2[1]);
			if (hour1==hour2){
				return min1-min2;
			} else {
				return hour1-hour2;
			}
		}
	};
	public static Comparator<Task> taskEndtimeComparator = new Comparator<Task>(){
		public int compare(Task task1, Task task2){
			String[] endTime1 = task1.getEndTime().split(":");
			String[] endTime2 = task2.getEndTime().split(":");
			int hour1 = Integer.parseInt(endTime1[0]);
			int hour2 = Integer.parseInt(endTime2[0]);
			int min1 = Integer.parseInt(endTime1[1]);
			int min2 = Integer.parseInt(endTime2[1]);
			if (hour1==hour2){
				return min1-min2;
			} else {
				return hour1-hour2;
			}
		}
	};
	public static Comparator<Task> taskDateComparator = new Comparator<Task>(){
		public int compare(Task task1, Task task2){
			return (splitDateObject(task1).compareTo(splitDateObject(task2)));
		}
	};
	private static Calendar splitDateObject(Task task){
		// assumes that the date is in yyyy/mm/dd format
		String[] date = task.getDate().split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		return calendar;
	}
	
	public Task(String name){
		name_ = name;
	}
	
	public void setName(String name){
		name_ = name;
	}
	
	public void setDate(String date){
		date_ = date;
	}
	
	public void setStartTime(String startTime){
		startTime_ = startTime;
	}
	
	public void setEndTime(String endTime){
		endTime_ = endTime;
	}
	
	public void setDetails(String details){
		details_ = details;
	}
	
	public String getName(){
		return name_;
	}
	
	public String getDate(){
		return date_;
	}
	
	public String getStartTime(){
		return startTime_;
	}
	
	public String getEndTime(){
		return endTime_;
	}
	
	public String getDetails(String details){
		return details_;
	}
}
