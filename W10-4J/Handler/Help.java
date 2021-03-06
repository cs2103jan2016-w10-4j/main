/* This is the help class for Docket. It reads from help.xml and displays it to the display output.
 *
 * @@author A0113761M
 */
package Handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Help implements Command {
///////UNUSED////////
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
///////UNUSED////////
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
		case Constants.MESSAGE_ACTION_RECURRENCE:
			help += read(Constants.MESSAGE_ACTION_RECURRENCE);
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
		help += read(Constants.MESSAGE_ACTION_RECURRENCE);
		help += read(Constants.MESSAGE_ACTION_DONE);
		help += read(Constants.MESSAGE_ACTION_DISPLAY);
		help += read(Constants.MESSAGE_ACTION_SEARCH);
		help += read(Constants.MESSAGE_ACTION_STORAGE);
		help += read(Constants.MESSAGE_ACTION_UNDO);
		help += read(Constants.MESSAGE_ACTION_ALIAS);
		help += read(Constants.MESSAGE_ACTION_NATURAL);
		help += read(Constants.MESSAGE_ACTION_KEYBOARD);
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