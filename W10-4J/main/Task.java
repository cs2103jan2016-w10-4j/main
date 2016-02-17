package main;

public class Task {
	private String name_;
	private String date_;
	private String startTime_;
	private String endTime_;
	
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
}
