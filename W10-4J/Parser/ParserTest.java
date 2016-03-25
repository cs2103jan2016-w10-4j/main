package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Constants.COMMAND_TYPE;

public class ParserTest {

	@Test
	public void testGetArguments() {
		Parser p = new Parser();
		String command, commandTypeString;
		COMMAND_TYPE commandType;
		
		command = "add 123";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"123"},p.getArguments(commandType, command));
		
		command = "add new task date 17 jan";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"new task","date","17 jan"},p.getArguments(commandType, command));
		
		command = "add this day by sun details anytime anywhere";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"this day by sun","details","anytime anywhere"},p.getArguments(commandType, command));
		
		command = "delete";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{},p.getArguments(commandType, command));
		
		command = "del wadever";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"wadever"},p.getArguments(commandType, command));
		
		command = "edit";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{},p.getArguments(commandType, command));
		
		command = "done";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{},p.getArguments(commandType, command));
		
		command = "done 17";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"17"},p.getArguments(commandType, command));
		
		command = "display";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{},p.getArguments(commandType, command));
		
		command = "display by done";
		commandTypeString = p.getFirstWord(command);
		commandType = p.getAction(commandTypeString);
		assertArrayEquals(new String[]{"by","done"},p.getArguments(commandType, command));
	}
	
	@Test
	public void testGetAction(){
		Parser p = new Parser();
		String command;
		
		command = "add";
		assertEquals(COMMAND_TYPE.ADD,p.getAction(p.getFirstWord(command)));
		
		command = "new";
		assertEquals(COMMAND_TYPE.ADD,p.getAction(p.getFirstWord(command)));
		
		command = "+";
		assertEquals(COMMAND_TYPE.ADD,p.getAction(p.getFirstWord(command)));
		
		command = "del";
		assertEquals(COMMAND_TYPE.DELETE,p.getAction(p.getFirstWord(command)));
		
		command = "rm";
		assertEquals(COMMAND_TYPE.DELETE,p.getAction(p.getFirstWord(command)));
		
		command = "bin";
		assertEquals(COMMAND_TYPE.DELETE,p.getAction(p.getFirstWord(command)));
		
		command = "e";
		assertEquals(COMMAND_TYPE.EDIT,p.getAction(p.getFirstWord(command)));
		
		command = "edit";
		assertEquals(COMMAND_TYPE.EDIT,p.getAction(p.getFirstWord(command)));
		
		command = "change";
		assertEquals(COMMAND_TYPE.EDIT,p.getAction(p.getFirstWord(command)));
	}

}
