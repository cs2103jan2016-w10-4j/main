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
		help = read("welcome");
		switch (task){
		case Constants.MESSAGE_ACTION_ADD:
			help += read("add");
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += read("delete");
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += read("edit");
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += read("done");
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += read("display");
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += read("search");
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += read("store");
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += read("undo");
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = read("welcome");
		help += read("function");
		help += read("add");
		help += read("delete");
		help += read("edit");
		help += read("done");
		help += read("display");
		help += read("search");
		help += read("store");
		help += read("undo");
		help += read("natural");
		help += read("keyboard");
		return help;
	}
	
	public static String read(String action){
		InputStream fileName = Help.class.getResourceAsStream("/resources/help.xml");
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