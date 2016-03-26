package Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import main.Constants;

public class Help implements Command {
	private static String fileName = ".\\Handler\\help.xml";
	private static String help;
	public Help() {
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if (task.length == 0) {
			return helpFullString();
		} else {
			return helpSpecific(task[0]);
		}
	}

	public String helpSpecific(String task) {
		help = read("welcome", fileName);
		switch (task){
		case Constants.MESSAGE_ACTION_ADD:
			help += read("add", fileName);
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += read("delete", fileName);
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += read("edit", fileName);
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += read("done", fileName);
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += read("display", fileName);
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += read("search", fileName);
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += read("store", fileName);
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += read("undo", fileName);
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = read("welcome", fileName);
		help += read("function", fileName);
		help += read("add", fileName);
		help += read("delete", fileName);
		help += read("edit", fileName);
		help += read("done", fileName);
		help += read("display", fileName);
		help += read("search", fileName);
		help += read("store", fileName);
		help += read("undo", fileName);
		help += read("natural", fileName);
		help += read("keyboard", fileName);
		return help;
	}
	
	public static String read(String action, String fileName){
		try{
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
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