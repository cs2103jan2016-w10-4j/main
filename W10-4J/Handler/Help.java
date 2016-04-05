/* This is the help class for Docket. It reads from help.xml and displays it to the display output.*/
package Handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.Constants;

public class Help implements Command {
	private static final String HELP_FILE_PATH = "/resources/help.xml";
	private static final String KEYBOARD_STRING = "keyboard";
	private static final String NATURAL_STRING = "natural";
	private static final String UNDO_STRING = "undo";
	private static final String STORE_STRING = "store";
	private static final String SEARCH_STRING = "search";
	private static final String DISPLAY_STRING = "display";
	private static final String DONE_STRING = "done";
	private static final String EDIT_STRING = "edit";
	private static final String DELETE_STRING = "delete";
	private static final String ADD_STRING = "add";
	private static final String WELCOME_STRING = "welcome";
	private static String help;
	public Help() {
	}

	public String execute(String[] task) {
		if (task.length == 0) {
			return helpFullString();
		} else {
			return helpSpecific(task[0]);
		}
	}

	public String helpSpecific(String task) {
		help = read(WELCOME_STRING);
		switch (task){
		case Constants.MESSAGE_ACTION_ADD:
			help += read(ADD_STRING);
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += read(DELETE_STRING);
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += read(EDIT_STRING);
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += read(DONE_STRING);
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += read(DISPLAY_STRING);
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += read(SEARCH_STRING);
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += read(STORE_STRING);
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += read(UNDO_STRING);
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = read(WELCOME_STRING);
		help += read("function");
		help += read(ADD_STRING);
		help += read(DELETE_STRING);
		help += read(EDIT_STRING);
		help += read(DONE_STRING);
		help += read(DISPLAY_STRING);
		help += read(SEARCH_STRING);
		help += read(STORE_STRING);
		help += read(UNDO_STRING);
		help += read(NATURAL_STRING);
		help += read(KEYBOARD_STRING);
		return help;
	}
	
	public static String read(String action){
		InputStream fileName = Help.class.getResourceAsStream(HELP_FILE_PATH);
		try{
			Properties prop = new Properties();
			prop.loadFromXML(fileName);
			String details = prop.getProperty(action);
			return details;
		}catch (FileNotFoundException e){
			return null;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
}