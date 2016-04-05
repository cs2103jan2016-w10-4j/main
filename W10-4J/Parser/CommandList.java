//@@author A0140114A
package Parser;

import java.util.ArrayList;
import main.Constants;

public class CommandList {
	private ArrayList<String> addCommandList = new ArrayList<>();
	private ArrayList<String> deleteCommandList = new ArrayList<>();
	private ArrayList<String> editCommandList = new ArrayList<>();
	private ArrayList<String> doneCommandList = new ArrayList<>();
	private ArrayList<String> displayCommandList = new ArrayList<>();
	private ArrayList<String> searchCommandList = new ArrayList<>();
	private ArrayList<String> setdirCommandList = new ArrayList<>();
	private ArrayList<String> retrieveCommandList = new ArrayList<>();
	private ArrayList<String> recurrenceCommandList = new ArrayList<>();
	private ArrayList<String> undoCommandList = new ArrayList<>();
	private ArrayList<String> exitCommandList = new ArrayList<>();
	private ArrayList<String> helpCommandList = new ArrayList<>();
	private ArrayList<String> aliasCommandList = new ArrayList<>();

	private ArrayList<String> addArgumentList = new ArrayList<>();
	private ArrayList<String> editArgumentList = new ArrayList<>();
	private ArrayList<String> displayArgumentList = new ArrayList<>();
	private ArrayList<String> searchArgumentList = new ArrayList<>();
	private ArrayList<String> recurrenceArgumentList = new ArrayList<>();
	private ArrayList<String> helpArgumentList = new ArrayList<>();

	private ArrayList<String> startDateArgumentList = new ArrayList<>();
	private ArrayList<String> endDateArgumentList = new ArrayList<>();
	private ArrayList<String> startTimeArgumentList = new ArrayList<>();
	private ArrayList<String> endTimeArgumentList = new ArrayList<>();
	private ArrayList<String> detailsArgumentList = new ArrayList<>();
	private ArrayList<String> repeatArgumentList = new ArrayList<>();

	private static CommandList instance = null;
	private static IOxml ioxml_ = null;

	public static CommandList getInstance() {
		if (instance == null) {
			instance = new CommandList();
		}
		return instance;
	}

	private CommandList() {
		ioxml_ = new IOxml();
		generateCommandList();
	}

	public void setAlias(Constants.COMMAND_TYPE commandType, String argument) {
		switch (commandType) {
		case ADD:
			addCommandList.add(argument);
			ioxml_.setAlias(0, argument);
			break;
		case DELETE:
			deleteCommandList.add(argument);
			ioxml_.setAlias(1, argument);
			break;
		case EDIT:
			editCommandList.add(argument);
			ioxml_.setAlias(2, argument);
			break;
		case DONE:
			doneCommandList.add(argument);
			ioxml_.setAlias(3, argument);
			break;
		case DISPLAY:
			displayCommandList.add(argument);
			ioxml_.setAlias(4, argument);
			break;
		case SEARCH:
			searchCommandList.add(argument);
			ioxml_.setAlias(5, argument);
			break;
		case SETDIR:
			setdirCommandList.add(argument);
			ioxml_.setAlias(6, argument);
			break;
		case RETRIEVE:
			retrieveCommandList.add(argument);
			ioxml_.setAlias(7, argument);
			break;
		case RECURRENCE:
			recurrenceCommandList.add(argument);
			ioxml_.setAlias(8, argument);
			break;
		case UNDO:
			undoCommandList.add(argument);
			ioxml_.setAlias(9, argument);
			break;
		case EXIT:
			exitCommandList.add(argument);
			ioxml_.setAlias(10, argument);
			break;
		case HELP:
			helpCommandList.add(argument);
			ioxml_.setAlias(11, argument);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void generateCommandList() {
		for (int i = 0; i < Constants.addDefaultCommandList.length; i++) {
			addCommandList.add(Constants.addDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(0));
		assert addCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.deleteDefaultCommandList.length; i++) {
			deleteCommandList.add(Constants.deleteDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(1));
		assert deleteCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.editDefaultCommandList.length; i++) {
			editCommandList.add(Constants.editDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(2));
		assert editCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.doneDefaultCommandList.length; i++) {
			doneCommandList.add(Constants.doneDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(3));
		assert doneCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.displayDefaultCommandList.length; i++) {
			displayCommandList.add(Constants.displayDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(4));
		assert displayCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.searchDefaultCommandList.length; i++) {
			searchCommandList.add(Constants.searchDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(5));
		assert searchCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.setdirDefaultCommandList.length; i++) {
			setdirCommandList.add(Constants.setdirDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(6));
		assert setdirCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.retrieveDefaultCommandList.length; i++) {
			retrieveCommandList.add(Constants.retrieveDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(7));
		assert retrieveCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.recurrenceDefaultCommandList.length; i++) {
			recurrenceCommandList.add(Constants.recurrenceDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(8));
		assert recurrenceCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.undoDefaultCommandList.length; i++) {
			undoCommandList.add(Constants.undoDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(9));
		assert undoCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.exitDefaultCommandList.length; i++) {
			exitCommandList.add(Constants.exitDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(10));
		assert exitCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.helpDefaultCommandList.length; i++) {
			helpCommandList.add(Constants.helpDefaultCommandList[i]);
		}
		addCommandList.addAll(ioxml_.getSpecificAlias(11));
		assert helpCommandList.size() >= 1;
		
		for (int i = 0; i < Constants.aliasDefaultCommandList.length; i++) {
			aliasCommandList.add(Constants.aliasDefaultCommandList[i]);
		}
		assert aliasCommandList.size() >= 1;

		for (int i = 0; i < Constants.addDefaultArgumentList.length; i++) {
			addArgumentList.add(Constants.addDefaultArgumentList[i]);
		}
		assert addArgumentList.size() >= 1;
		for (int i = 0; i < Constants.editDefaultArgumentList.length; i++) {
			editArgumentList.add(Constants.editDefaultArgumentList[i]);
		}
		assert editArgumentList.size() >= 1;
		for (int i = 0; i < Constants.displayDefaultArgumentList.length; i++) {
			displayArgumentList.add(Constants.displayDefaultArgumentList[i]);
		}
		assert displayArgumentList.size() >= 1;
		for (int i = 0; i < Constants.searchDefaultArgumentList.length; i++) {
			searchArgumentList.add(Constants.searchDefaultArgumentList[i]);
		}
		assert searchArgumentList.size() >= 1;
		for (int i = 0; i < Constants.recurrenceDefaultArgumentList.length; i++) {
			recurrenceArgumentList.add(Constants.recurrenceDefaultArgumentList[i]);
		}
		assert recurrenceArgumentList.size() >= 1;
		for (int i = 0; i < Constants.helpDefaultArgumentList.length; i++) {
			helpArgumentList.add(Constants.helpDefaultArgumentList[i]);
		}
		assert helpArgumentList.size() >= 1;

		for (int i = 0; i < Constants.startDateDefaultArgumentList.length; i++) {
			startDateArgumentList.add(Constants.startDateDefaultArgumentList[i]);
		}
		assert startDateArgumentList.size() >= 1;
		for (int i = 0; i < Constants.endDateDefaultArgumentList.length; i++) {
			endDateArgumentList.add(Constants.endDateDefaultArgumentList[i]);
		}
		assert endDateArgumentList.size() >= 1;
		for (int i = 0; i < Constants.startTimeDefaultArgumentList.length; i++) {
			startTimeArgumentList.add(Constants.startTimeDefaultArgumentList[i]);
		}
		assert startTimeArgumentList.size() >= 1;
		for (int i = 0; i < Constants.endTimeDefaultArgumentList.length; i++) {
			endTimeArgumentList.add(Constants.endTimeDefaultArgumentList[i]);
		}
		assert endTimeArgumentList.size() >= 1;
		for (int i = 0; i < Constants.detailsDefaultArgumentList.length; i++) {
			detailsArgumentList.add(Constants.detailsDefaultArgumentList[i]);
		}
		assert detailsArgumentList.size() >= 1;
		for (int i = 0; i < Constants.repeatDefaultArgumentList.length; i++) {
			repeatArgumentList.add(Constants.repeatDefaultArgumentList[i]);
		}
		assert repeatArgumentList.size() >= 1;
	}

	public ArrayList<String> getAddCommandList() {
		return addCommandList;
	}

	public ArrayList<String> getDeleteCommandList() {
		return deleteCommandList;
	}

	public ArrayList<String> getEditCommandList() {
		return editCommandList;
	}

	public ArrayList<String> getDoneCommandList() {
		return doneCommandList;
	}

	public ArrayList<String> getDisplayCommandList() {
		return displayCommandList;
	}

	public ArrayList<String> getSearchCommandList() {
		return searchCommandList;
	}

	public ArrayList<String> getSetdirCommandList() {
		return setdirCommandList;
	}

	public ArrayList<String> getRecurrenceCommandList() {
		return recurrenceCommandList;
	}

	public ArrayList<String> getRetrieveCommandList() {
		return retrieveCommandList;
	}

	public ArrayList<String> getUndoCommandList() {
		return undoCommandList;
	}

	public ArrayList<String> getHelpCommandList() {
		return helpCommandList;
	}

	public ArrayList<String> getAliasCommandList() {
		return aliasCommandList;
	}

	public ArrayList<String> getExitCommandList() {
		return exitCommandList;
	}

	public ArrayList<String> getAddArgumentList() {
		return addArgumentList;
	}

	public ArrayList<String> getEditArgumentList() {
		return editArgumentList;
	}

	public ArrayList<String> getDisplayArgumentList() {
		return displayArgumentList;
	}

	public ArrayList<String> getSearchArgumentList() {
		return searchArgumentList;
	}

	public ArrayList<String> getRecurrenceArgumentList() {
		return recurrenceArgumentList;
	}

	public ArrayList<String> getHelpArgumentList() {
		return helpArgumentList;
	}

	public ArrayList<String> getStartDateArgumentList() {
		return startDateArgumentList;
	}

	public ArrayList<String> getEndDateArgumentList() {
		return endDateArgumentList;
	}

	public ArrayList<String> getStartArgumentList() {
		return startTimeArgumentList;
	}

	public ArrayList<String> getEndArgumentList() {
		return endTimeArgumentList;
	}

	public ArrayList<String> getDetailsArgumentList() {
		return detailsArgumentList;
	}

	public ArrayList<String> getRepeatArgumentList() {
		return repeatArgumentList;
	}
}
