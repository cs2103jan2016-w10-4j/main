/*
 * JUnit test for Write class
 * @@author A0126129J
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Constants;
import main.Task;
import Storage.Storage;
import Storage.Write;

public class WriteTest {
	ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
	Write taskWriter = Write.getInstance();
	
	@Before
	public void setUpBefore() throws Exception {
		Task taskOne = new Task("Task 1");
		taskOne.setStartDate("1/10/2016");
		
		Task taskTwo = new Task("Task 2");
		taskTwo.setStartDate("2/10/2016");
		taskTwo.setEndDate("2/10/2016");
		
		Task taskThree = new Task("Task 3");		
		taskThree.setStartDate("2/10/2016");
		taskThree.setEndDate("5/10/2016");
		taskThree.setStartTime("14:00");
		taskThree.setEndTime("18:00");
		
		Task taskFour = new Task("Task 4");
		taskFour.setStartDate("2/10/2016");
		taskFour.setWeek(true);
		
		Task taskFive = new Task("Task 5");
		taskFive.setStartDate("2/10/2016");
		taskFive.setEndDate("5/10/2016");
		taskFive.setStartTime("15:00");
		taskFive.setEndTime("17:00");
		taskFive.setWeek(true);
		
		toDoTaskList.add(taskOne);
		toDoTaskList.add(taskTwo);
		toDoTaskList.add(taskThree);
		toDoTaskList.add(taskFour);
		doneTaskList.add(taskFive);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);
		
		// Write to testFile.txt
		PrintWriter print = new PrintWriter(new FileWriter("testFile.txt"));
		print.println("Tasks on hand:");
		print.println("1. Event: Task 1");
		print.println("Start Date: 1/10/2016");
		print.println("2. Event: Task 2");
		print.println("Start Date: 2/10/2016");
		print.println("End Date: 2/10/2016");
		print.println("3. Event: Task 3");
		print.println("Start Date: 2/10/2016");
		print.println("End Date: 5/10/2016");
		print.println("Start Time: 14:00");
		print.println("End Time: 18:00");
		print.println("4. Event: Task 4");
		print.println("Start Date: 2/10/2016");
		print.println("Week: true");
		print.println("Tasks that are done:");
		print.println("1. Event: Task 5");
		print.println("Start Date: 2/10/2016");
		print.println("End Date: 5/10/2016");
		print.println("Start Time: 15:00");
		print.println("End Time: 17:00");
		print.println("Week: true");
		print.close();	
	}	

	@After
	public void setUpAfter() {
		try {
			File file = new File("mytextfile.txt");
			file.delete();
			file = new File("testFile.txt");
			file.delete();
			file = new File("taskList.txt");
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteToDefaultFile() throws NoSuchAlgorithmException, IOException {
		Storage.filename = "mytextfile.txt";
		taskWriter.writeToFile(toDoTaskList, doneTaskList);

		String testFile = "testFile.txt";
		String actualFile = Constants.DEFAULT_FILENAME;
		byte[] digestTest = computeCheckSum(testFile);
		byte[] digestActual = computeCheckSum(actualFile);

		assertEquals(true, checkSumEqual(digestTest, digestActual));
	}

	@Test
	public void testWriteToOtherFileWithUpdatePath() throws NoSuchAlgorithmException, IOException {
		taskWriter.writeToFile(toDoTaskList, doneTaskList);
		
		boolean isSameFile = false;
		Storage.filename = "taskList.txt";
		taskWriter.writeToFile("taskList.txt", toDoTaskList, doneTaskList);

		String testFile = "testFile.txt";
		String actualFile = "taskList.txt";
		byte[] digestTest = computeCheckSum(testFile);
		byte[] digestActual = computeCheckSum(actualFile);

		String pathContent = getPathContentFromDefaultFile();
		if (checkSumEqual(digestTest, digestActual) && pathContent.equals(actualFile)) {
			isSameFile = true;
		}
		assertEquals(true, isSameFile);
	}

	byte[] computeCheckSum(String file) throws NoSuchAlgorithmException, IOException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(Files.readAllBytes(Paths.get(file)));
		byte[] digest = messageDigest.digest();
		return digest;
	}

	boolean checkSumEqual(byte[] digestTest, byte[] digestReturn) {
		boolean isSameCheckSum = false;
		isSameCheckSum = Arrays.equals(digestTest, digestReturn);
		return isSameCheckSum;
	}

	String getPathContentFromDefaultFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(Constants.DEFAULT_FILENAME));
		String content = reader.readLine();
		String pathContent = null;
		if (content != null) {
			String path = content.substring(0, content.indexOf(" "));
			if (path.equals("PATH:")) {
				pathContent = content.substring(content.indexOf(" ") + 1, content.length());
			}
		}
		reader.close();
		return pathContent;
	}
}