package main;

public class Constants {
	public enum COMMAND_TYPE {
		ADD, DELETE, EDIT, DONE, DISPLAY, SEARCH, SETDIR, RETRIEVE, RECURRENCE, UNDO, EXIT, INVALID, HELP
	};

	public enum COMMAND_STATE {
		FAILED, PASS, DELETEDONETASK, DELETEUNDONETASK, RECURRINGDONE, NONRECURRINGDONE, UNDOADD, UNDODELETE, UNDOEDIT, UNDODONE, UNDOUNDO
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

	public static final String[] addDefaultArgumentList = { "date", "start", "end", "details", "repeat" };
	public static final String[] editDefaultArgumentList = { "rename", "date", "start", "end", "details", "repeat" };
	public static final String[] displayDefaultArgumentList = { "id", "name", "starttime", "endtime", "date", "tasks",
			"done" };
	public static final String[] searchDefaultArgumentList = { "excl", "exclude" };
	public static final String[] recurrenceDefaultArgumentList = { "day", "week", "month", "year" };
	public static final String[] helpDefaultArgumentList = { "add", "delete", "edit", "done", "display", "search", "storage", "undo"};

	public static final String[] dateDefaultArgumentList = { "date", "d" };
	public static final String[] startDefaultArgumentList = { "start", "starttime", "s", "time" };
	public static final String[] endDefaultArgumentList = { "end", "endtime", "e" };
	public static final String[] detailsDefaultArgumentList = { "details", "details", "d" };
	public static final String[] repeatDefaultArgumentList = { "repeat", "recur", "r", "recurrence" };

	public static final String[] month = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep",
			"oct", "nov", "dec", "january", "february", "march", "april", "may", "june", "july", "august", "september",
			"october", "november", "december" };
	public static final String[] day = new String[] { "mon", "tues", "wed", "thurs", "fri", "sat", "sun", "m", "t", "w",
			"f", "s", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };

	public static final String MESSAGE_UNRECOGNÝZED_COMMAND = ("Unrecognized command type");
	public static final String MESSAGE_INVALID_FORMAT = ("Invalid command format");
	public static final String MESSAGE_INVALID_DATE = ("Invalid date format");
	public static final String MESSAGE_INVALID_TIME = ("Invalid time format");

	public static final String MESSAGE_ADD_PASS = ("%1$s has been added.");
	public static final String MESSAGE_DELETE_PASS = ("%1$s has been deleted.");
	public static final String MESSAGE_DELETE_FAIL = ("Task cannot be deleted.");
	public static final String MESSAGE_EDIT_PASS = ("%1$s has been edited.");
	public static final String MESSAGE_EDIT_FAIL = ("Task to be edited does not exist.");
	public static final String MESSAGE_DONE_PASS = ("%1$s has been set to done.");
	public static final String MESSAGE_DONE_FAIL = ("%1$s cannot be set to done.");
	public static final String MESSAGE_SEARCH_PASS = ("Search successful.");
	public static final String MESSAGE_SEARCH_FAIL = ("Search unsuccessful.");
	public static final String MESSAGE_UNDO_PASS = ("Undo successful.");
	public static final String MESSAGE_UNDO_FAIL = ("Nothing to undo");
	public static final String MESSAGE_RETRIEVE_PASS = ("Retrieve successful.");
	public static final String MESSAGE_RETRIEVE_FAIL = ("Retrieve unsuccessful.");
	public static final String MESSAGE_RECURRENCE_PASS = ("Recurrence set successfully");
	public static final String MESSAGE_RECURRENCE_FAIL = ("Task does not exist");
	public static final String MESSAGE_SETDIR_PASS = ("Set directory successful.");
	public static final String MESSAGE_SETDIR_FAIL = ("Set directory unsuccessful.");
	public static final String MESSAGE_TIME_FAIL = ("Start time must be before end time.");

	public static final String MESSAGE_ADD_ACTION_DATE = ("date");
	public static final String MESSAGE_ADD_ACTION_START = ("start");
	public static final String MESSAGE_ADD_ACTION_END = ("end");
	public static final String MESSAGE_ADD_ACTION_DETAILS = ("details");
	public static final String MESSAGE_ADD_ACTION_REPEAT = ("repeat");

	public static final String MESSAGE_DISPLAY_FIELD_ID = ("id");
	public static final String MESSAGE_DISPLAY_FIELD_NAME = ("name");
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
	public static final String MESSAGE_EDIT_ACTION_REPEAT = ("repeat");

	public static final String MESSAGE_ACTION_ADD = ("add");
	public static final String MESSAGE_ACTION_DELETE = ("delete");
	public static final String MESSAGE_ACTION_EDIT = ("edit");
	public static final String MESSAGE_ACTION_DONE = ("done");
	public static final String MESSAGE_ACTION_DISPLAY = ("display");
	public static final String MESSAGE_ACTION_SEARCH = ("search");
	public static final String MESSAGE_ACTION_STORAGE = ("storage");
	public static final String MESSAGE_ACTION_UNDO = ("undo");
	public static final String MESSAGE_ACTION_READ = ("read");
	public static final String MESSAGE_ACTION_RETRIEVE = ("retrieve");
	public static final String MESSAGE_ACTION_WRITE = ("write");

	public static final String MESSAGE_STORAGE_SUCCESS = ("Success");
	public static final String MESSAGE_STORAGE_FAILURE = ("Failure");
	public static final String MESSAGE_STORAGE_PATH = ("PATH:");

	public static final String MESSAGE_READ_EVENT = ("Event");
	public static final String MESSAGE_READ_DATE = ("Date");
	public static final String MESSAGE_READ_STARTTIME = ("Start Time");
	public static final String MESSAGE_READ_ENDTIME = ("End Time");
	public static final String MESSAGE_READ_DETAILS = ("Details");
	public static final String MESSAGE_READ_DAY = ("Day");
	public static final String MESSAGE_READ_WEEK = ("Week");
	public static final String MESSAGE_READ_MONTH = ("Month");

	public static final String MESSAGE_WRITE_EVENT = ("%d. Event: %s");
	public static final String MESSAGE_WRITE_DATE = ("Date: %s");
	public static final String MESSAGE_WRITE_STARTTIME = ("Start Time: %s");
	public static final String MESSAGE_WRITE_ENDTIME = ("End Time: %s");
	public static final String MESSAGE_WRITE_DETAILS = ("Details: %s");
	public static final String MESSAGE_WRITE_DAY = ("Day: %s");
	public static final String MESSAGE_WRITE_WEEK = ("Week: %s");
	public static final String MESSAGE_WRITE_MONTH = ("Month: %s");
	public static final String MESSAGE_WRITE_YEAR = ("Year: %s");

	public static final String MESSAGE_REPEAT_DAY = ("day");
	public static final String MESSAGE_REPEAT_WEEK = ("week");
	public static final String MESSAGE_REPEAT_MONTH = ("month");
	public static final String MESSAGE_REPEAT_YEAR = ("year");

	// Use in both Write and Read class
	public static final String MESSAGE_WRITE_READ_PATH = ("PATH:");
	public static final String MESSAGE_WRITE_READ_TASKONHAND = ("Tasks on hand:");
	public static final String MESSAGE_WRITE_READ_TASKDONE = ("Tasks that are done:");
	public static final String MESSAGE_WRITE_READ_NOTASKONHAND = ("No tasks on hand!");
	public static final String MESSAGE_WRITE_READ_NOTASKDONE = ("No tasks are done!");

	public static final String ASSERT_TASK_EXISTENCE = ("Task does not exist");
	public static final String ASSERT_TASKLIST_EXISTENCE = ("Task List does not exist");
	public static final String ASSERT_ACTION_EXISTENCE = ("Action does not exist");
	public static final String ASSERT_TASKID_EXISTENCE = ("Task ID does not exist");
	public static final String ASSERT_FIELD_EXISTENCE = ("Field does not exist");
	public static final String ASSERT_TASKNAME_EXISTENCE = ("Task name does not exist");
	public static final String ASSERT_TASKDETAILS_EXISTENCE = ("Task details does not exist");
}