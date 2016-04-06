/* This is the help class for Docket. It reads from help.xml and displays it to the display output.*/
package Handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.Constants;

public class Help implements Command {
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
		help = read(Constants.WELCOME_STRING);
		switch (task){
		case Constants.MESSAGE_ACTION_ADD:
			help += read(Constants.ADD_STRING);
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += read(Constants.DELETE_STRING);
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += read(Constants.EDIT_STRING);
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += read(Constants.DONE_STRING);
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += read(Constants.DISPLAY_STRING);
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += read(Constants.SEARCH_STRING);
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += read(Constants.STORE_STRING);
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += read(Constants.UNDO_STRING);
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = read(Constants.WELCOME_STRING);
		help += read(Constants.FUNCTION);
		help += read(Constants.ADD_STRING);
		help += read(Constants.DELETE_STRING);
		help += read(Constants.EDIT_STRING);
		help += read(Constants.DONE_STRING);
		help += read(Constants.DISPLAY_STRING);
		help += read(Constants.SEARCH_STRING);
		help += read(Constants.STORE_STRING);
		help += read(Constants.UNDO_STRING);
		help += read(Constants.NATURAL_STRING);
		help += read(Constants.KEYBOARD_STRING);
		return help;
	}
	
	public static String read(String action){
		InputStream fileName = Help.class.getResourceAsStream(Constants.HELP_FILE_PATH);
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