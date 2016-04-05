//@@author A0140114A
package Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class IOxml {
	private static final String filename = ".\\alias.xml";
	private static final String[] commandlist = { "add", "delete", "edit", "done", "display", "search", "setdir",
			"retrieve", "recurrence", "undo", "exit", "help" };
	private ArrayList<String> alias_ = new ArrayList<>();

	public IOxml() {
		for (int i = 0; i < commandlist.length; i++) {
			String s = commandlist[i];
			String aliaslist = read(s, filename);
			if (aliaslist == null) {
				this.alias_.add("");
			} else {
				this.alias_.add(aliaslist);
			}
		}
		write();
	}

	private String read(String action, String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals("")) {
				return "";
			} else {
				return details;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void write() {
		Properties properties = new Properties();
		for (int i = 0; i < commandlist.length; i++) {
			properties.setProperty(commandlist[i], alias_.get(i));
		}

		File file = new File(filename);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "alias");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAlias(int index, String argument) {
		String current = alias_.get(index);
		if (current == "") {
			alias_.set(index, argument);
		} else {
			alias_.set(index, alias_.get(index) + "," + argument);
		}
		write();
	}

	public ArrayList<String> getSpecificAlias(int index) {
		alias_.get(index).split(",");
		String[] argument = alias_.get(index).split(",");
		ArrayList<String> output = new ArrayList<>();
		for (int i = 0; i < argument.length; i++) {
			output.add(argument[i]);
		}
		return output;
	}
}
