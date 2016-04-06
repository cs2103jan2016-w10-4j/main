/*
 * JUnit test for Write class
 * @@author A0126129J
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
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
		// Set up write class
		Task a = new Task("A");
		a.setStartDate("1/4/2016");
		
		Task b = new Task("B");
		b.setStartDate("2/4/2016");
		b.setEndDate("2/4/2016");
		
		Task c = new Task("C");		
		c.setStartDate("2/4/2016");
		c.setEndDate("5/4/2016");
		c.setStartTime("14:00");
		c.setEndTime("18:00");
		
		Task d = new Task("D");
		d.setStartDate("2/4/2016");
		d.setWeek(true);
		
		Task e = new Task("E");
		e.setStartDate("2/4/2016");
		e.setEndDate("5/4/2016");
		e.setStartTime("15:00");
		e.setEndTime("17:00");
		e.setWeek(true);
		
		toDoTaskList.add(a);
		toDoTaskList.add(b);
		toDoTaskList.add(c);
		toDoTaskList.add(d);
		doneTaskList.add(e);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);

		// Write to testFile.txt
		PrintWriter print = new PrintWriter(new FileWriter("testFile.txt"));
		print.println("Tasks on hand:");
		print.println("1. Event: A");
		print.println("Start Date: 1/4/2016");
		print.println("2. Event: B");
		print.println("Start Date: 2/4/2016");
		print.println("End Date: 2/4/2016");
		print.println("3. Event: C");
		print.println("Start Date: 2/4/2016");
		print.println("End Date: 5/4/2016");
		print.println("Start Time: 14:00");
		print.println("End Time: 18:00");
		print.println("4. Event: D");
		print.println("Start Date: 2/4/2016");
		print.println("Week: true");
		print.println("Tasks that are done:");
		print.println("1. Event: E");
		print.println("Start Date: 2/4/2016");
		print.println("End Date: 5/4/2016");
		print.println("Start Time: 15:00");
		print.println("End Time: 17:00");
		print.println("Week: true");
		print.close();	
	}	

	@Test
	public void testWrite() throws NoSuchAlgorithmException, IOException {
		testWriteToDefaultFile();
		testWriteToOtherFileWithUpdatePath();
	}

	public void testWriteToDefaultFile() throws NoSuchAlgorithmException, IOException {
		taskWriter.writeToFile(toDoTaskList, doneTaskList);

		String testFile = "testFile.txt";
		String actualFile = Constants.DEFAULT_FILENAME;
		byte[] digestTest = computeCheckSum(testFile);
		byte[] digestActual = computeCheckSum(actualFile);

		assertEquals(true, checkSumEqual(digestTest, digestActual));
	}

	public void testWriteToOtherFileWithUpdatePath() throws NoSuchAlgorithmException, IOException {
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