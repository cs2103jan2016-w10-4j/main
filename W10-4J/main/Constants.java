package main;

public class Constants {
	public enum COMMAND_TYPE {
		ADD, DELETE, EDIT, DONE, DISPLAY, SEARCH, SETDIR, RETRIEVE, RECURRENCE, UNDO, EXIT, INVALID, HELP
	};

	public static final String fileName = "mytextfile.txt";
	public static final String[] addDefaultCommandList = { "add", "new", "+", "a" };
	public static final String[] deleteDefaultCommandList = { "delete", "del", "remove", "rm", "bin", "thrash", "-" };
	public static final String[] editDefaultCommandList = { "edit", "change", "edittask", "e" };
	public static final String[] doneDefaultCommandList = { "done", "finish", "complete" };
	public static final String[] displayDefaultCommandList = { "display", "ls", "list", "show", "print" };
	public static final String[] searchDefaultCommandList = { "search", "find", "contains" };
	public static final String[] setdirDefaultCommandList = { "setdir", "cd", "setdirectory", "set directory" };
	public static final String[] retrieveDefaultCommandList = { "storage", "get", "open", "grab", "grep", "retrieve" };
	public static final String[] recurrenceDefaultCommandList = { "recurrence", "recur", "repeat", "r" };
	public static final String[] undoDefaultCommandList = { "undo", "whoops", "mb" };
	public static final String[] exitDefaultCommandList = { "exit", "quit", "q" };
	public static final String[] helpDefaultCommandList = { "help", "h", "?" };

	public static final String[] addDefaultArgumentList = { "date", "start", "end", "details", "by", "at" };
	public static final String[] editDefaultArgumentList = { "rename", "date", "start", "end", "details", "by", "at" };
	public static final String[] displayDefaultArgumentList = { "alphabetical order", "name", "starttime", "endtime",
			"date", "tasks", "done" };
	public static final String[] searchDefaultArgumentList = { "excl", "exclude" };
	public static final String[] recurrenceDefaultArgumentList = { "day", "week", "month", "year" };
	public static final String[] helpDefaultArgumentList = { "add", "new", "+", "delete", "del", "remove", "rm", "bin",
			"thrash", "-", "edit", "change", "edittask", "e", "done", "finish", "complete", "display", "ls", "list",
			"show", "print", "search", "find", "contains", "setdir", "cd", "setdirectory", "set directory", "storage",
			"get", "open", "grab", "grep", "retrieve", "undo", "whoops", "mb" };

	public static final String[] month = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep",
			"oct", "nov", "dec", "january", "february", "march", "april", "may", "june", "july", "august", "september",
			"october", "november", "december" };
	public static final String[] day = new String[] { "mon", "tues", "wed", "thurs", "fri", "sat", "sun", "m", "t", "w",
			"f", "s", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };

	public static final String MESSAGE_INVALID_FORMAT = ("invalid command format");
	public static final String MESSAGE_ADD_PASS = ("%1$s has been added.");
	public static final String MESSAGE_DELETE_PASS = ("%1$s has been deleted.");
	public static final String MESSAGE_DELETE_FAIL = ("Task cannot be deleted.");
	public static final String MESSAGE_EDIT_PASS = ("%1$s has been edited.");
	public static final String MESSAGE_DONE_PASS = ("%1$s has been set to done.");
	public static final String MESSAGE_DONE_FAIL = ("%1$s cannot be set to done.");
	public static final String MESSAGE_SEARCH_PASS = ("Search successful.");
	public static final String MESSAGE_SEARCH_FAIL = ("Search unsuccessful.");
	public static final String MESSAGE_UNDO_PASS = ("Undo successful.");
	public static final String MESSAGE_RETRIEVE_PASS = ("Retrieve successful.");
	public static final String MESSAGE_RETRIEVE_FAIL = ("Retrieve unsuccessful.");
	public static final String MESSAGE_SETDIR_PASS = ("Set directory successful.");
	public static final String MESSAGE_SETDIR_FAIL = ("Set directory unsuccessful.");
	
	public static final String MESSAGE_ADD_ACTION_DATE = ("date");
	public static final String MESSAGE_ADD_ACTION_START = ("start");
	public static final String MESSAGE_ADD_ACTION_END = ("end");
	public static final String MESSAGE_ADD_ACTION_TIME = ("time");
	public static final String MESSAGE_ADD_ACTION_DETAILS = ("details");
	
	public static final String MESSAGE_DISPLAY_FIELD_NAME = ("alphabetical order");
	public static final String MESSAGE_DISPLAY_FIELD_START = ("starttime");
	public static final String MESSAGE_DISPLAY_FIELD_END = ("endtime");
	public static final String MESSAGE_DISPLAY_FIELD_DATE = ("date");
	public static final String MESSAGE_DISPLAY_FIELD_TASKS = ("tasks");
	public static final String MESSAGE_DISPLAY_FIELD_DONE = ("done");
	

	public static final String MESSAGE_EDIT_ACTION_RENAME = ("rename");
	public static final String MESSAGE_EDIT_ACTION_START = ("start");
	public static final String MESSAGE_EDIT_ACTION_END = ("end");
	public static final String MESSAGE_EDIT_ACTION_DATE = ("date");
	public static final String MESSAGE_EDIT_ACTION_DETAILS = ("details");
	
	public static final String MESSAGE_ACTION_ADD = ("add");
	public static final String MESSAGE_ACTION_DELETE = ("delete");
	public static final String MESSAGE_ACTION_EDIT = ("edit");
	public static final String MESSAGE_ACTION_DONE = ("done");
	public static final String MESSAGE_ACTION_DISPLAY = ("display");
	public static final String MESSAGE_ACTION_SEARCH = ("search");
	public static final String MESSAGE_ACTION_STORAGE = ("storage");
	public static final String MESSAGE_ACTION_UNDO = ("undo");
	
	public static final String ASSERT_TASK_EXISTENCE = ("Task does not exist");
	public static final String ASSERT_ACTION_EXISTENCE = ("Action does not exist");
	public static final String ASSERT_TASKID_EXISTENCE = ("Task ID does not exist");
	public static final String ASSERT_FIELD_EXISTENCE = ("Field does not exist");
	public static final String ASSERT_TASKNAME_EXISTENCE = ("Field does not exist");
	public static final String ASSERT_TASKDETAILS_EXISTENCE = ("Task details does not exist");
}
