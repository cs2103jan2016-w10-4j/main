package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Retrieve {
	private String success = "Success";
	private String failure = "Failure";
	
	public ArrayList<ArrayList<Task>> retrieveTaskListFromFile(String filename) {
		try {
			ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
			
			// Check if file and directory exists before proceeding on to read
			// the content in the file
			String outcome = checkFileExists(filename);
			if (outcome.equals(failure)) {
				ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
				ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
				taskList.add(readToDoTaskList);
				taskList.add(readDoneTaskList);
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				Read read = new Read();
				taskList = read.readFromFile(reader);
				reader.close();
			}
			
			return taskList;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}
	
	private String checkFileExists(String filename) {
		try{
			String outcome;
			String test = "\"" + filename + "\"";
			File file = new File(test);
			System.out.println(test);
			if (file.exists()) {
				outcome = success;
			} else {
				outcome = failure;
			}
			
			return outcome;
		} catch (Exception e) {
			return null;
		}
	}
}
