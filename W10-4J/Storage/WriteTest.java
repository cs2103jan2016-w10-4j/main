package Storage;

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

import org.junit.BeforeClass;
import org.junit.Test;

import main.Constants;
import main.Task;

public class WriteTest extends Write {
	static String success = "Success";
	static String failure = "Failure";
	static ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	static ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	static ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Set up write class
		Task a = new Task("A");
		Task b = new Task("B");
		toDoTaskList.add(a);
		toDoTaskList.add(b);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);
		
		// Write to testFile.txt
		PrintWriter print = new PrintWriter(new FileWriter("testFile.txt"));
		print.println("Tasks on hand:");
		print.println("1. Event: A");
		print.println("2. Event: B");
		print.println("No tasks are done!");
		print.close();
		
		// Write to list.txt
		PrintWriter printWriter = new PrintWriter(new FileWriter("list.txt"));
		printWriter.println("Tasks on hand:");
		printWriter.println("1. Event: A");
		printWriter.println("2. Event: B");
		printWriter.println("No tasks are done!");
		printWriter.close();
	}	
	
	@Test
	public void testWriteToDefaultFile() throws NoSuchAlgorithmException, IOException {
		writeToFile(toDoTaskList, doneTaskList);
		
		// Check the md5 checksum
		String testFile = "testFile.txt";
		MessageDigest messageDigestTest = MessageDigest.getInstance("MD5");
		messageDigestTest.update(Files.readAllBytes(Paths.get(testFile)));
		byte[] digestTest = messageDigestTest.digest();
		
		String returnFile = Constants.fileName;
		MessageDigest messageDigestReturn = MessageDigest.getInstance("MD5");
		messageDigestReturn.update(Files.readAllBytes(Paths.get(returnFile)));
		byte[] digestReturn = messageDigestReturn.digest();
		
		assertEquals(true, Arrays.equals(digestTest, digestReturn));
	}
	
	@Test
	public void testWriteToOtherFileWithUpdatePath() throws NoSuchAlgorithmException, IOException {
		boolean returnValue = false;
		writeToFile("list.txt", toDoTaskList, doneTaskList);
		
		// Check the md5 checksum
		String testFile = "testFile.txt";
		MessageDigest messageDigestTest = MessageDigest.getInstance("MD5");
		messageDigestTest.update(Files.readAllBytes(Paths.get(testFile)));
		byte[] digestTest = messageDigestTest.digest();
		
		String returnFile = "list.txt";
		MessageDigest messageDigestReturn = MessageDigest.getInstance("MD5");
		messageDigestReturn.update(Files.readAllBytes(Paths.get(returnFile)));
		byte[] digestReturn = messageDigestReturn.digest();
		
		BufferedReader reader = new BufferedReader(new FileReader(Constants.fileName));
		String content = reader.readLine();
		String pathContent = null;
		if (content != null) {
			String path = content.substring(0, content.indexOf(" "));
			if(path.equals("PATH:")) {
				pathContent = content.substring(content.indexOf(" ") + 1, content.length());
			}
		}
		reader.close();
		
		if(Arrays.equals(digestTest, digestReturn) && pathContent.equals("list.txt")) {
			returnValue = true;
		}
		
		assertEquals(true, returnValue);
	}
	
	@Test
	public void testWriteToOtherFileWithoutUpdatePath() throws NoSuchAlgorithmException, IOException {
		writeToFile(Storage.filename, taskList);
		
		// Check the md5 checksum
		String testFile = "testFile.txt";
		MessageDigest messageDigestTest = MessageDigest.getInstance("MD5");
		messageDigestTest.update(Files.readAllBytes(Paths.get(testFile)));
		byte[] digestTest = messageDigestTest.digest();
		
		String returnFile = Storage.filename;
		MessageDigest messageDigestReturn = MessageDigest.getInstance("MD5");
		messageDigestReturn.update(Files.readAllBytes(Paths.get(returnFile)));
		byte[] digestReturn = messageDigestReturn.digest();
		
		assertEquals(true, Arrays.equals(digestTest, digestReturn));
	}
}
