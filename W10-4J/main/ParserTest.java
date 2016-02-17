package main;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Constants.COMMAND_TYPE;

public class ParserTest {

	@Test
	public void testGetArguments() {
		Parser p = new Parser();
		assertArrayEquals(new String[]{"filetwo.txt", "some", "other", "things here"}, p.getArguments("\"file one.txt\" filetwo.txt some other \"things here\""));
	}
	
	@Test
	public void testGetAction(){
		Parser p = new Parser();
		String command;
		String[] arguments;
		
		command = "add";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "add 123";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.ADD,p.getAction(p.getFirstWord(command), arguments));
		
		command = "add 123 test";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "add 123 date";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "edit";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "edit 123";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.EDIT,p.getAction(p.getFirstWord(command), arguments));
		
		command = "edit 123 test";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "display";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.DISPLAY,p.getAction(p.getFirstWord(command), arguments));
		
		command = "display 123";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.INVALID,p.getAction(p.getFirstWord(command), arguments));
		
		command = "display by";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.DISPLAY,p.getAction(p.getFirstWord(command), arguments));
		
		command = "display done";
		arguments = p.getArguments(command);
		assertEquals(COMMAND_TYPE.DISPLAY,p.getAction(p.getFirstWord(command), arguments));
	}

}
