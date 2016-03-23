package Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Task;

public class DisplayFormat {
	
	public static String displayFormat(ArrayList<Task> sortedList) {
		int counter = 1;
		String output = "<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr>";
		
		/*
		 * Red - Exceed the stipulated date and endtime
		 * Green - Have yet to exceed the stipulated date and endtime
		 * Black - Default color
		 */
		String red = "<font color=#ff0000>";
		String green = "<font color=#00FF00>";
		String black = "<font color=#000000>";
		String color = black;
		
		for (Task t : sortedList) {
			// Determine which color to display
			if(t.getDate() != null && t.getEndTime() == null) {
				Date date = new Date();
			    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/mm/dd");
			    
			    if(t.getDate().trim().compareTo(dateFormat.format(date)) < 0) {
					color = red;
				} else {
					color = green;
				}
			} else if(t.getEndTime() != null && t.getDate() != null) {
				Date time = new Date();
			    SimpleDateFormat timeFormat = new SimpleDateFormat ("HH:mm");
				
			    Date date = new Date();
			    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd");
			    
			    if(t.getDate().trim().compareTo(dateFormat.format(date)) < 0) {
					color = red;
				} else if (t.getEndTime().trim().compareTo(timeFormat.format(time)) > 0 && t.getDate().compareTo(dateFormat.format(date)) == 0 ) {
					color = red;
				} else {
					color = green;
				} 
			}
			
			String repeat;
			if (t.getDay()){
				repeat = "Every Day";
			} else if (t.getMonth()){
				repeat = "Every Month";
			} else if (t.getWeek()){
				repeat = "Every Week";
			} else if (t.getYear()){
				repeat = "Every Year";
			} else {
				repeat = null;
			}
			
			output += "<tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\">" + color + counter + ")</font></td><td>" + color + t.getName() + "</font></td>";
			if (t.getDate() != null){
				output += "<td>" + color + t.getDate() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getStartTime() != null){
				output += "<td>" + color + t.getStartTime() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getEndTime() != null){
				output += "<td>" + color + t.getEndTime() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getDetails() != null){
				output += "<td>" + color + t.getDetails() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (repeat != null){
				output += "<td>" + color + repeat + "</font></td>";
			}
			counter++;
		}

		return output;
	}
}
