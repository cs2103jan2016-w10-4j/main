//@@author A0140114A
package Parser;

import java.util.ArrayList;

import main.Constants;

public class NaturalLanguage {
	private CommandList commandList_;
	private NaturalDate ndate_;
	private NaturalTime ntime_;
	private ArrayList<String> token_;

	public NaturalLanguage(CommandList commandList) {
		this.commandList_ = commandList;
		this.ndate_ = new NaturalDate();
		this.ntime_ = new NaturalTime();
	}

	public String[] interpretAddArguments(ArrayList<String> token) {
		token_ = token;

		isolateRecurrance();
		isolateDate();
		isolateTime();
		isolateNameAndDetails();
		return generateOutputAdd();
	}

	public String[] interpretEditArguments(ArrayList<String> token) {
		token_ = token;

		isolateTaskID();
		isolateRecurrance();
		isolateDate();
		isolateTime();
		isolateNameAndDetails();
		return generateOutputEdit();
	}

	public void isolateTaskID() {
		String taskid = token_.get(0);
		if (isInteger(taskid)) {
			token_.set(0, null);
		}
	}

	public void isolateRecurrance() {
		for (int i = 0; i < token_.size(); i++) {
			String s = token_.get(i);
			if (commandList_.getRecurrenceArgumentList().contains(s)) {
				try {
						throw new NumberFormatException();
					} else {
						token_.set(i, null);
						token_.set(i + 1, null);
					}
				} catch (NumberFormatException e) {
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
	}

	public void isolateDate() {
		for (int i = 1; i <= token_.size(); i++) {
			ArrayList<String> temptoken_ = new ArrayList<>();
			for (int j = 0; j < token_.size(); j++) {
				String tempString = Constants.EMPTY_STRING;
				for (int k = 0; k < i; k++) {
					try {
						if (tempString.equals(Constants.EMPTY_STRING)) {
							tempString += token_.get(j + k);
						} else {
							tempString += Constants.WHITESPACE + token_.get(j + k);
						}
					} catch (Exception e) {
						break;
					}
				}
				temptoken_.add(tempString);
			}
			for (String s : temptoken_) {
				String date = ndate_.getDate(s);
				if (date != null) {
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
					}
				}
			}
		}
	}

	public void isolateTime() {
		for (int i = 1; i <= token_.size(); i++) {
			ArrayList<String> temptoken_ = new ArrayList<>();
			for (int j = 0; j < token_.size(); j++) {
				String tempString = Constants.EMPTY_STRING;
				for (int k = 0; k < i; k++) {
					try {
						String toAdd = token_.get(j + k);
						if (toAdd == null) {
							break;
						}
						tempString += toAdd;
					} catch (Exception e) {
						break;
					}
				}
				if (tempString != Constants.EMPTY_STRING) {
					temptoken_.add(tempString);
				}
			}
			for (String s : temptoken_) {
				String time = ntime_.getTime(s);
				if (time != null) {
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
					}
				}
			}
		}
	}

	public void isolateNameAndDetails() {
		ArrayList<String> temptoken_ = new ArrayList<>();
		String tempString = Constants.EMPTY_STRING;
		for (int i = 0; i < token_.size(); i++) {
			String s = token_.get(i);
			if (s == null) {
				if (!tempString.equals(Constants.EMPTY_STRING)) {
					temptoken_.add(tempString);
					tempString = Constants.EMPTY_STRING;
				}
			} else {
				if (tempString.equals(Constants.EMPTY_STRING)) {
					tempString += s;
				} else {
					tempString += Constants.WHITESPACE + s;
				}
			}
		}
		if (!tempString.equals(Constants.EMPTY_STRING)) {
			temptoken_.add(tempString);
		}
		switch (temptoken_.size()) {
		case 2:
		case 1:
			break;
		}
	}

	public String[] generateOutputAdd() {
		ArrayList<String> output = new ArrayList<>();
		} else {
			return null;
		}
			output.add(Constants.MESSAGE_ADD_ACTION_STARTDATE);
		}
			output.add(Constants.MESSAGE_ADD_ACTION_ENDDATE);
		}
			output.add(Constants.MESSAGE_ADD_ACTION_START);
		}
			output.add(Constants.MESSAGE_ADD_ACTION_END);
		}
			output.add(Constants.MESSAGE_ADD_ACTION_DETAILS);
		}
			output.add(Constants.MESSAGE_ADD_ACTION_REPEAT);
		}
		String[] out = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		return out;
	}

	public String[] generateOutputEdit() {
		ArrayList<String> output = new ArrayList<>();

		} else {
			return null;
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_RENAME);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_STARTDATE);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_ENDDATE);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_START);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_END);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_DETAILS);
		}
			output.add(Constants.MESSAGE_EDIT_ACTION_REPEAT);
		}
		String[] out = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		return out;
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
