package main;

public class Constants {
	public enum COMMAND_TYPE {
		ADD, DELETE, EDIT, DONE, DISPLAY, SEARCH, SETDIR, RETRIEVE, UNDO, EXIT, INVALID
	};
	public static final String MESSAGE_INVALID_FORMAT = "invalid command format";
	public static final String fileName = "mytextfile.txt";
	public static final String[] addDefaultCommandList = {"add","new","+"};
	public static final String[] deleteDefaultCommandList = {"delete","del","remove","rm","bin","thrash","-"};
	public static final String[] editDefaultCommandList = {"edit","change","edittask","e"};
	public static final String[] doneDefaultCommandList = {"done","finish","complete"};
	public static final String[] displayDefaultCommandList = {"display","ls","list","show","print"};
	public static final String[] searchDefaultCommandList = {"search","find","contains"};
	public static final String[] setdirDefaultCommandList = {"setdir","cd","setdirectory","set directory"};
	public static final String[] retrieveDefaultCommandList = {"storage","get","open","grab","grep","retrieve"};
	public static final String[] undoDefaultCommandList = {"undo","whoops","mb"};
	public static final String[] exitDefaultCommandList = {"exit","quit"};
	
	public static final String[] addDefaultArgumentList = {"date","start","end","details","by","at"};
	public static final String[] editDefaultArgumentList = {"rename","date","start","end","details","by","at"};
	public static final String[] displayDefaultArgumentList = {"by","alphabetical order","name","starttime","endtime","date","tasks","done"};
	public static final String[] searchDefaultArgumentList = {"excl","exclude"};

	public static final String MESSAGE_ADD_PASS = ("Task has been added.");
	public static final String MESSAGE_DELETE_PASS = ("Task has been deleted.");
	public static final String MESSAGE_DELETE_FAIL = ("Task cannot be deleted.");
	public static final String MESSAGE_EDIT_PASS = ("Task has been edited.");
	public static final String MESSAGE_DONE_PASS = ("Task has been set to done.");
	public static final String MESSAGE_DONE_FAIL = ("Task cannot be set to done.");
	public static final String MESSAGE_SEARCH_PASS = ("Search successful.");
	public static final String MESSAGE_SEARCH_FAIL = ("Search unsuccessful.");
	public static final String MESSAGE_UNDO_PASS = ("Undo successful.");
	public static final String MESSAGE_RETRIEVE_PASS = ("Retrieve successful.");
	public static final String MESSAGE_RETRIEVE_FAIL = ("Retrieve unsuccessful.");
}
