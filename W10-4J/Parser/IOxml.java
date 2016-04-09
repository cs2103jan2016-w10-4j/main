//@@author A0113761M
package Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Constants;

public class IOxml {
	private final Logger LOGGER = Logger.getLogger(IOxml.class.getName());
	private ArrayList<String> alias_ = new ArrayList<>();

	public IOxml() {
		try {
			for (int i = 0; i < Constants.COMMAND_LIST.length; i++) {
				String s = Constants.COMMAND_LIST[i];
				String aliaslist = read(s, Constants.ALIAS_FILENAME);
				if (aliaslist == null) {
					this.alias_.add(Constants.EMPTY_STRING);
				} else {
					this.alias_.add(aliaslist);
				}
			}
			LOGGER.log(Level.INFO, "Read from file successful");
		} catch (NullPointerException e) {
			LOGGER.log(Level.WARNING, "Alias.xml not found");
			for (int i = 0; i < Constants.COMMAND_LIST.length; i++) {
				this.alias_.add(Constants.EMPTY_STRING);
			}
		}
		write();
	}

	private String read(String action, String fileName) throws NullPointerException {
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals(Constants.EMPTY_STRING)) {
				return Constants.EMPTY_STRING;
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
		for (int i = 0; i < Constants.COMMAND_LIST.length; i++) {
			properties.setProperty(Constants.COMMAND_LIST[i], alias_.get(i));
		}

		File file = new File(Constants.ALIAS_FILENAME);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, Constants.ALIAS_COMMENT_TAG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAlias(int index, String argument) {
		String current = alias_.get(index);
		if (current == Constants.EMPTY_STRING) {
			alias_.set(index, argument);
		} else {
			alias_.set(index, alias_.get(index) + Constants.COMMA + argument);
		}
		write();

		LOGGER.log(Level.INFO, "Alias saved successfully");
	}

	public ArrayList<String> getSpecificAlias(int index) {
		alias_.get(index).split(Constants.COMMA);
		String[] argument = alias_.get(index).split(Constants.COMMA);
		ArrayList<String> output = new ArrayList<>();
		for (int i = 0; i < argument.length; i++) {
			output.add(argument[i]);
		}
		return output;
	}
}
