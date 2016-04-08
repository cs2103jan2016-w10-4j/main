//@@author A0140114A
package Parser;

import java.util.ArrayList;

import main.Constants;

public class NaturalLanguage {
	private CommandList commandList_;
	private NaturalDate ndate_;
	private NaturalTime ntime_;
	private String taskID__;
	private String name_;
	private String startdate_;
	private String starttime_;
	private String enddate_;
	private String endtime_;
	private String details_;
	private String recurtype_;
	private int recurCount_;
	private ArrayList<String> token_;

	public NaturalLanguage(CommandList commandList) {
		this.commandList_ = commandList;
		this.ndate_ = new NaturalDate();
		this.ntime_ = new NaturalTime();
	}

	public String[] interpretAddArguments(String[] token) {
		token_ = new ArrayList<>();
		for(String s : token){
			token_.add(s);
		}
		taskID__ = null;
		name_ = null;
		startdate_ = null;
		starttime_ = null;
		enddate_ = null;
		endtime_ = null;
		details_ = null;
		recurtype_ = null;
		recurCount_ = -1;

		isolateRecurrance();
		isolateDate();
		isolateTime();
		isolateNameAndDetails();
		return generateOutputAdd();
	}

	public String[] interpretEditArguments(ArrayList<String> token) {
		token_ = token;
		name_ = null;
		startdate_ = null;
		starttime_ = null;
		enddate_ = null;
		endtime_ = null;
		details_ = null;
		recurtype_ = null;
		recurCount_ = -1;

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
			taskID__ = taskid;
			token_.set(0, null);
		}
	}

	public void isolateRecurrance() {
		for (int i = 1; i < token_.size(); i++) {
			String s = token_.get(i);
			if (commandList_.getRecurrenceArgumentList().contains(s)) {
				try {
					recurCount_ = Integer.parseInt(token_.get(i + 1));
					if (recurCount_ <= 1) {
						throw new NumberFormatException();
					} else {
						recurtype_ = s;
						token_.set(i, null);
						token_.set(i + 1, null);
					}
				} catch (NumberFormatException e) {
					recurCount_ = -1;
					recurtype_ = null;
				} catch (IndexOutOfBoundsException e) {
					recurCount_ = -1;
					recurtype_ = null;
				}
			}
		}
	}

	public void isolateDate() {
		for (int i = 1; i <= token_.size(); i++) {
			ArrayList<String> temptoken_ = new ArrayList<>();
			for (int j = 1; j < token_.size(); j++) {
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
					if (startdate_ == null) {
						startdate_ = date;
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
					} else if (enddate_ == null) {
						enddate_ = date;
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
			for (int j = 1; j < token_.size(); j++) {
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
					if (starttime_ == null) {
						starttime_ = time;
						for (String str : s.split(Constants.WHITESPACE)) {
							token_.set(token_.indexOf(str), null);
						}
					} else if (endtime_ == null) {
						endtime_ = time;
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
			details_ = temptoken_.get(1);
		case 1:
			name_ = temptoken_.get(0);
			break;
		}
	}

	public String[] generateOutputAdd() {
		ArrayList<String> output = new ArrayList<>();
		if (name_ != null) {
			output.add(name_);
		} else {
			return null;
		}
		if (startdate_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_STARTDATE);
			output.add(startdate_);
		}
		if (enddate_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_ENDDATE);
			output.add(enddate_);
		}
		if (starttime_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_START);
			output.add(starttime_);
		}
		if (endtime_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_END);
			output.add(endtime_);
		}
		if (details_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_DETAILS);
			output.add(details_);
		}
		if (recurCount_ > 1 && recurtype_ != null) {
			output.add(Constants.MESSAGE_ADD_ACTION_REPEAT);
			output.add(recurtype_ + Constants.WHITESPACE + Integer.toString(recurCount_));
		}
		String[] out = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		return out;
	}

	public String[] generateOutputEdit() {
		ArrayList<String> output = new ArrayList<>();

		if (taskID__ != null) {
			output.add(taskID__);
		} else {
			return null;
		}
		if (name_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_RENAME);
			output.add(name_);
		}
		if (startdate_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_STARTDATE);
			output.add(startdate_);
		}
		if (enddate_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_ENDDATE);
			output.add(enddate_);
		}
		if (starttime_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_START);
			output.add(starttime_);
		}
		if (endtime_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_END);
			output.add(endtime_);
		}
		if (details_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_DETAILS);
			output.add(details_);
		}
		if (recurCount_ > 1 && recurtype_ != null) {
			output.add(Constants.MESSAGE_EDIT_ACTION_REPEAT);
			output.add(recurtype_ + Constants.WHITESPACE + Integer.toString(recurCount_));
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
