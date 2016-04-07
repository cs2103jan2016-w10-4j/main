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
			return "0" + helpFullString();
		} else {
			return "0" + helpSpecific(task[0]);
		}
	}

	public String helpSpecific(String task) {
		help = read(Constants.WELCOME_STRING);
		switch (task){
		case Constants.MESSAGE_ACTION_ADD:
			help += read(Constants.MESSAGE_ACTION_ADD);
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += read(Constants.MESSAGE_ACTION_DELETE);
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += read(Constants.MESSAGE_ACTION_EDIT);
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += read(Constants.MESSAGE_ACTION_DONE);
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += read(Constants.MESSAGE_ACTION_DISPLAY);
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += read(Constants.MESSAGE_ACTION_SEARCH);
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += read(Constants.MESSAGE_ACTION_STORAGE);
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += read(Constants.MESSAGE_ACTION_UNDO);
			break;
		case Constants.MESSAGE_ACTION_ALIAS:
			help += read(Constants.MESSAGE_ACTION_ALIAS);
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = read(Constants.WELCOME_STRING);
		help += read(Constants.FUNCTION);
		help += read(Constants.MESSAGE_ACTION_ADD);
		help += read(Constants.MESSAGE_ACTION_DELETE);
		help += read(Constants.MESSAGE_ACTION_EDIT);
		help += read(Constants.MESSAGE_ACTION_DONE);
		help += read(Constants.MESSAGE_ACTION_DISPLAY);
		help += read(Constants.MESSAGE_ACTION_SEARCH);
		help += read(Constants.MESSAGE_ACTION_STORAGE);
		help += read(Constants.MESSAGE_ACTION_UNDO);
		help += read(Constants.MESSAGE_ACTION_NATURAL);
		help += read(Constants.MESSAGE_ACTION_KEYBOARD);
		help += read(Constants.MESSAGE_ACTION_ALIAS);
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