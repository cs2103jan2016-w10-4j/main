package main;

public class Task {
	private String name_;
	private String date_;
	private String startTime_;
	private String endTime_;
	private String details_;
	
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
