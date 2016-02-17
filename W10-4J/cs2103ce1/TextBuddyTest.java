package cs2103ce1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testMain() {
		String args[] = { "mytextfile.txt" };
		TextBuddy.retrieveFileName(args);
		TextBuddy.retrieveContents();
		String input;
		
		input = "clear";
		assertEquals(input,"all content deleted from mytextfile.txt\n", TextBuddy.executeCommand(input));
		
		input = "add 4";
		assertEquals(input,"added to mytextfile.txt: \"4\"\n", TextBuddy.executeCommand(input));
		
		input = "display";
		assertEquals(input,"1. 4\n", TextBuddy.executeCommand(input));
		
		input = "delete 1";
		assertEquals(input,"deleted from mytextfile.txt: \"4\"\n", TextBuddy.executeCommand(input));

		input = "display";
		assertEquals(input,"mytextfile.txt is empty\n", TextBuddy.executeCommand(input));
		
		//input = "exit";
		//assertEquals("", TextBuddy.executeCommand(input));
	}
	
	@Test 
	public void testAdd(){
		String args[] = { "mytextfile.txt" };
		TextBuddy.retrieveFileName(args);
		TextBuddy.retrieveContents();
		
		String input = "4";
		assertEquals(input,"added to mytextfile.txt: \"4\"\n", TextBuddy.add(input));
	}
}
