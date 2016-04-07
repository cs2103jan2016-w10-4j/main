//package Storage;
//
//import static org.junit.Assert.*;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import main.Constants;
//import main.Task;
//
//public class WriteTest extends Write {
//	static ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
//	static ArrayList<Task> doneTaskList = new ArrayList<Task> ();
//	static ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
//		
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		// Set up write class
//		Task a = new Task("A");
//		Task b = new Task("B");
//		a.setTaskID(1);
//		b.setTaskID(2);
//		toDoTaskList.add(a);
//		toDoTaskList.add(b);
//		taskList.add(toDoTaskList);
//		taskList.add(doneTaskList);
//		
//		// Write to testFile.txt
//		PrintWriter print = new PrintWriter(new FileWriter("testFile.txt"));
//		print.println("Tasks on hand:");
//		print.println("1. Event: A");
//		print.println("2. Event: B");
//		print.println("No tasks are done!");
//		print.close();
//	}	
//	
//	@Test
//	public void testWriteToDefaultFile() throws NoSuchAlgorithmException, IOException {
//		writeToFile(toDoTaskList, doneTaskList);
//		
//		String testFile = "testFile.txt";
//		String returnFile = Constants.DEFAULT_FILENAME;
//		byte[] digestTest = computeCheckSum(testFile);
//		byte[] digestReturn = computeCheckSum(returnFile);
//		
//		assertEquals(true, checkSumEqual(digestTest, digestReturn));
//	}
//	
//	@Test
//	public void testWriteToOtherFileWithUpdatePath() throws NoSuchAlgorithmException, IOException {
//		boolean returnValue = false;
//		writeToFile("list.txt", toDoTaskList, doneTaskList);
//		
//		String testFile = "testFile.txt";
//		String returnFile = "list.txt";
//		byte[] digestTest = computeCheckSum(testFile);
//		byte[] digestReturn = computeCheckSum(returnFile);
//		
//		String pathContent = getPathContentFromDefaultFile();
//
//		if (checkSumEqual(digestTest, digestReturn) && pathContent.equals("list.txt")) {
//			returnValue = true;
//		}
//		
//		assertEquals(true, returnValue);
//	}
//
//	byte[] computeCheckSum(String file) throws NoSuchAlgorithmException, IOException {
//		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//		messageDigest.update(Files.readAllBytes(Paths.get(file)));
//		byte[] digest = messageDigest.digest();
//		return digest;
//	}
//	
//	boolean checkSumEqual(byte[] digestTest, byte[] digestReturn) {
//		boolean returnValue = false;
//		returnValue = Arrays.equals(digestTest, digestReturn);
//		return returnValue;
//	}
//	
//	String getPathContentFromDefaultFile() throws IOException {
//		BufferedReader reader = new BufferedReader(new FileReader(Constants.DEFAULT_FILENAME));
//		String content = reader.readLine();
//		String pathContent = null;
//		if (content != null) {
//			String path = content.substring(0, content.indexOf(" "));
//			if (path.equals("PATH:")) {
//				pathContent = content.substring(content.indexOf(" ") + 1, content.length());
//			}
//		}
//		reader.close();
//		return pathContent;
//	}
//}