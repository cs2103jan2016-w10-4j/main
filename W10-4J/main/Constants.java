package main;

public class Constants {
	// @@author A0140114A
	public enum COMMAND_TYPE {
		ADD, DELETE, EDIT, DONE, DISPLAY, SEARCH, SETDIR, RETRIEVE, RECURRENCE, UNDO, EXIT, INVALID, HELP, ALIAS
	};

	public static final String DEFAULT_FILENAME = "mytextfile.txt";
	public static final String setDirFileName = "dirdefaultfile.txt";
	public static final String[] addDefaultCommandList = { "add", "new", "+", "a" };
	public static final String[] deleteDefaultCommandList = { "delete", "del", "remove", "rm", "bin", "thrash", "-" };
	public static final String[] editDefaultCommandList = { "edit", "change", "edittask", "e" };
	public static final String[] doneDefaultCommandList = { "done", "finish", "complete" };
	public static final String[] displayDefaultCommandList = { "display", "ls", "list", "show", "print" };
	public static final String[] searchDefaultCommandList = { "search", "find", "contains" };
	public static final String[] setdirDefaultCommandList = { "setdir", "cd", "setdirectory", "set directory" };
	public static final String[] retrieveDefaultCommandList = { "storage", "get", "open", "grab", "grep", "retrieve" };
	public static final String[] recurrenceDefaultCommandList = { "recurrence", "recur", "repeat", "r" };
	public static final String[] undoDefaultCommandList = { "undo", "whoops", "mb", "redo" };
	public static final String[] exitDefaultCommandList = { "exit", "quit", "q" };
	public static final String[] helpDefaultCommandList = { "help", "h", "?" };
	public static final String[] aliasDefaultCommandList = { "alias", "set", "bind", "link" };

	public static final String[] addDefaultArgumentList = { "startdate", "enddate", "starttime", "endtime", "details",
			"repeat" };
	public static final String[] editDefaultArgumentList = { "rename", "startdate", "enddate", "starttime", "endtime",
			"details", "repeat" };
	public static final String[] displayDefaultArgumentList = { "overdue", "name", "done", "today" };
	public static final String[] searchDefaultArgumentList = { "excl", "exclude" };
	public static final String[] recurrenceDefaultArgumentList = { "day", "week", "month", "year" };
	public static final String[] helpDefaultArgumentList = { "add", "delete", "edit", "done", "display", "search",
			"storage", "undo", "alias" };

	public static final String[] startDateDefaultArgumentList = { "startdate", "date" };
	public static final String[] endDateDefaultArgumentList = { "enddate", "by", "due" };
	public static final String[] startTimeDefaultArgumentList = { "start", "starttime", "time" };
	public static final String[] endTimeDefaultArgumentList = { "end", "endtime", "e" };
	public static final String[] detailsDefaultArgumentList = { "details", "details" };
	public static final String[] repeatDefaultArgumentList = { "repeat", "recur", "r", "recurrence" };
	public static final String[] todayArgumentList = { "today", "later" };
	public static final String[] tomorrowArgumentList = { "tomorrow", "tmr" };

	public static final String[] month = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep",
			"oct", "nov", "dec", "january", "february", "march", "april", "may", "june", "july", "august", "september",
			"october", "november", "december" };
	public static final String[] day = new String[] { "sun", "mon", "tues", "wed", "thurs", "fri", "sat", "sunday",
			"monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

	public static final char QUOTE_CHAR = '"';
	public static final char EMPTY_CHAR = ' ';
	public static final String WHITESPACE = " ";
	public static final String COMMA = ",";
	public static final String DASH = "-";
	public static final String ALIAS_COMMENT_TAG = "alias";
	public static final String TIME_AM = "am";
	public static final String TIME_PM = "pm";
	public static final String DATE_FORMAT = "%04d/%02d/%02d";
	public static final String TIME_FORMAT = "%02d:%02d";

	public static final String ALIAS_FILENAME = ".\\alias.xml";
	public static final String[] COMMAND_LIST = { "add", "delete", "edit", "done", "display", "search", "setdir",
			"retrieve", "recurrence", "undo", "exit", "help" };

	public static final String MESSAGE_UNRECOGNISED_COMMAND = ("2Unrecognized command type");
	public static final String MESSAGE_INVALID_FORMAT = ("2Invalid command format");
	public static final String MESSAGE_INVALID_DATE = ("2Invalid date format");
	public static final String MESSAGE_INVALID_TIME = ("2Invalid time format");

	// @@author A0135779M
	public static final String MESSAGE_ADD_PASS = ("1%1$s has been added.");
	public static final String MESSAGE_DELETE_PASS = ("1%1$s has been deleted.");
	public static final String MESSAGE_DELETE_FAIL = ("2Task cannot be deleted.");
	public static final String MESSAGE_EDIT_PASS = ("1%1$s has been edited.");
	public static final String MESSAGE_EDIT_FAIL = ("2Task to be edited does not exist.");
	public static final String MESSAGE_DONE_PASS = ("1%1$s has been set to done.");
	public static final String MESSAGE_DONE_FAIL = ("2%1$s cannot be set to done.");
	public static final String MESSAGE_SEARCH_PASS = ("1Search successful.");
	public static final String MESSAGE_SEARCH_FAIL = ("2Search unsuccessful.");
	public static final String MESSAGE_UNDO_PASS = ("1Undo successful.");
	public static final String MESSAGE_UNDO_FAIL = ("2Nothing to undo");
	public static final String MESSAGE_RETRIEVE_PASS = ("1Retrieve successful.");
	public static final String MESSAGE_RETRIEVE_FAIL = ("2Retrieve unsuccessful.");
	public static final String MESSAGE_SETDIR_PASS = ("1Set directory successful.");
	public static final String MESSAGE_SETDIR_FAIL = ("2Set directory unsuccessful.");
	public static final String MESSAGE_TIME_FAIL = ("2Start time must be before end time.");
	public static final String MESSAGE_RECUR_FAIL = ("2Start date must exist for recurrance to occur.");
	public static final String MESSAGE_ALIAS_PASS = ("1Alias set successfully.");

	public static final String MESSAGE_ADD_ACTION_STARTDATE = ("startdate");
	public static final String MESSAGE_ADD_ACTION_ENDDATE = ("enddate");
	public static final String MESSAGE_ADD_ACTION_START = ("starttime");
	public static final String MESSAGE_ADD_ACTION_END = ("endtime");
	public static final String MESSAGE_ADD_ACTION_DETAILS = ("details");
	public static final String MESSAGE_ADD_ACTION_REPEAT = ("repeat");

	public static final String MESSAGE_DISPLAY_FIELD_ID = ("id");
	public static final String MESSAGE_DISPLAY_FIELD_NAME = ("name");
	public static final String MESSAGE_DISPLAY_FIELD_OVERDUE = ("overdue");
	public static final String MESSAGE_DISPLAY_FIELD_TODAY = ("today");
	public static final String MESSAGE_DISPLAY_FIELD_STARTDATE = ("startdate");
	public static final String MESSAGE_DISPLAY_FIELD_TASKS = ("tasks");
	public static final String MESSAGE_DISPLAY_FIELD_DONE = ("done");

	/*
	 * Red - Exceed the stipulated date and endtime Black - Default color
	 */
	public static final String MESSAGE_DISPLAY_COLOR_RED = ("<font color=#E3170D>");
	public static final String MESSAGE_DISPLAY_COLOR_BLACK = ("<font color=#000000>");
	public static final String MESSAGE_DISPLAY_COLOR_BLUE = ("<font color=#0000FF>");

	// Use commonly throughout all types of Display
	public static final String MESSAGE_DISPLAY_HEADER_OPENTAG = ("<style>#underline{border-bottom: 3px solid black;}</style><h1><b>");
	public static final String MESSAGE_DISPLAY_HEADER_CLOSETAG = ("</b></h1>");

	// Use only by DisplayDone and DisplayTableFormat
	public static final String MESSAGE_DISPLAY_TABLEANDHEADER = ("<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\">Start Date </th><th style=\"width:15%;\" align=\"left\">End Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr>");
	public static final String MESSAGE_DISPLAY_TABLECLOSETAG = ("</table>");

	// Use by DisplayToday and DisplayOverdue
	public static final String MESSAGE_DISPLAY_SPACING = ("<br><br>");

	// Use by DisplayStart and DisplayToday
	public static final String MESSAGE_DISPLAY_HEADERTABLE_OPENTAG = ("<table id=\"underline\"><th style=\"font-size:120%\">");
	public static final String MESSAGE_DISPLAY_HEADERTABLE_CLOSETAG = ("</th></table>");

	public static final String MESSAGE_DISPLAYTABLEFORMAT_NOTASKONHAND = ("No tasks on hand!");
	public static final String MESSAGE_DISPLAYTABLEFORMAT_TABLE = ("display in table format");

	public static final String MESSAGE_DISPLAYOVERDUE_HEADER = ("Overdue Tasks");
	public static final String MESSAGE_DISPLAYOVERDUE_NOOVERDUETASK = ("There is no overdue task.");

	public static final String MESSAGE_DISPLAYTODAY_HEADER = ("Today's Tasks");
	public static final String MESSAGE_DISPLAYTODAY_NOTASK = ("There is no task today.");
	public static final String MESSAGE_DISPLAYTODAY_TODAY = ("Today");

	public static final String MESSAGE_DISPLAYDONE_HEADER = ("Done Tasks");
	public static final String MESSAGE_DISPLAYDONE_NOTASKDONE = ("No tasks are done!");
	public static final String MESSAGE_DISPLAYDONE_DONE = ("display done");

	public static final String MESSAGE_DISPLAYSTARTDATE_OVERDUE = ("Overdue");
	public static final String MESSAGE_DISPLAYSTARTDATE_TODAY = ("Today");
	public static final String MESSAGE_DISPLAYSTARTDATE_YESTERDAY = ("Yesterday");
	public static final String MESSAGE_DISPLAYSTARTDATE_TOMORROW = ("Tomorrow");
	public static final String MESSAGE_DISPLAYSTARTDATE_FLOATINGTASKS = ("Floating Tasks");
	public static final String MESSAGE_DISPLAYSTARTDATE_NOTASKONHAND = ("No tasks on hand!");
	public static final String MESSAGE_DISPLAYSTARTDATE_STARTDATE = ("display with startdate");
	public static final String MESSAGE_DISPLAYSTARTDATE_TABLEOPENTAG = ("<table width=\"100%\" style=\"margin-bottom:10px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"><h2><b> Event <b></h2></th><th style=\"width:15%;\" align=\"left\"><h2><b> Start Time </h2><b></th><th style=\"width:15%;\" align=\"left\"><h2><b> End Time </h2><b></th><th style=\"width:25%;\" align=\"left\"><h2><b> Details </h2></b></th><th style=\"width:15%;\" align=\"left\"><h2><b> Repeat </h2><b></th></ltr>");
	public static final String MESSAGE_DISPLAYSTARTDATE_TABLECLOSETAG = ("</table>");

	public static final int MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST = (-1);
	public static final String MESSAGE_COMMONFUNCTION_REPEATDAY = ("Every Day");
	public static final String MESSAGE_COMMONFUNCTION_REPEATMONTH = ("Every Month");
	public static final String MESSAGE_COMMONFUNCTION_REPEATWEEK = ("Every Week");
	public static final String MESSAGE_COMMONFUNCTION_REPEATYEAR = ("Every Year");
	public static final String MESSAGE_COMMONFUNCTION_RETRIEVE = ("retrieve");
	public static final String MESSAGE_COMMONFUNCTION_DONE = ("display done");
	public static final String MESSAGE_COMMONFUNCTION_TABLE = ("display in table format");
	public static final String MESSAGE_COMMONFUNCTION_STARTDATE = ("display with startdate");
	public static final String MESSAGE_COMMONFUNCTION_DASH = ("-");
	public static final String MESSAGE_COMMONFUNCTION_TD_OPENTAG = ("<td>");
	public static final String MESSAGE_COMMONFUNCTION_TD_CLOSETAG = ("</td>");
	public static final String MESSAGE_COMMONFUNCTION_TD_OPENCLOSETAG = ("<td></td>");
	public static final String MESSAGE_COMMONFUNCTION_TD_ALIGN = ("<td align=\"right\">");
	public static final String MESSAGE_COMMONFUNCTION_TR_OPENTAG = ("<tr style=\"border-bottom:1px solid #E5E4E2\">");
	public static final String MESSAGE_COMMONFUNCTION_TRHIGHLIGHT_OPENTAG = ("<tr style=\"border-bottom:1px solid #E5E4E2\" bgcolor= #FFFF00>");
	public static final String MESSAGE_COMMONFUNCTION_TR_CLOSETAG = ("</tr>");
	public static final String MESSAGE_COMMONFUNCTION_HEADER_OPENTAG = ("<h3>");
	public static final String MESSAGE_COMMONFUNCTION_HEADER_CLOSETAG = ("</h3>");

	public static final String MESSAGE_EDIT_ACTION_RENAME = ("rename");
	public static final String MESSAGE_EDIT_ACTION_START = ("starttime");
	public static final String MESSAGE_EDIT_ACTION_END = ("endtime");
	public static final String MESSAGE_EDIT_ACTION_STARTDATE = ("startdate");
	public static final String MESSAGE_EDIT_ACTION_ENDDATE = ("enddate");
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
	public static final String MESSAGE_ACTION_NATURAL = ("natural");
	public static final String MESSAGE_ACTION_KEYBOARD = ("keyboard");
	public static final String MESSAGE_ACTION_ALIAS = ("alias");
	public static final String MESSAGE_ACTION_RETRIEVE = ("retrieve");
	public static final String MESSAGE_ACTION_UNRETRIEVE = ("unretrieve");
	public static final String MESSAGE_ACTION_SETDIR = ("set directory");
	public static final String MESSAGE_ACTION_UNSETDIR = ("unset directory");
	public static final String MESSAGE_ACTION_WRITE = ("write");
	public static final String MESSAGE_ACTION_BASICOP = ("basic operations");

	public static final String MESSAGE_STORAGE_SUCCESS = ("Success");
	public static final String MESSAGE_STORAGE_FAILURE = ("Failure");
	public static final String MESSAGE_STORAGE_PATH = ("PATH:");

	public static final String MESSAGE_READ_EVENT = ("Event");
	public static final String MESSAGE_READ_STARTDATE = ("Start Date");
	public static final String MESSAGE_READ_ENDDATE = ("End Date");
	public static final String MESSAGE_READ_STARTTIME = ("Start Time");
	public static final String MESSAGE_READ_ENDTIME = ("End Time");
	public static final String MESSAGE_READ_DETAILS = ("Details");
	public static final String MESSAGE_READ_DAY = ("Day");
	public static final String MESSAGE_READ_WEEK = ("Week");
	public static final String MESSAGE_READ_MONTH = ("Month");

	public static final String MESSAGE_WRITE_EVENT = ("%d. Event: %s");
	public static final String MESSAGE_WRITE_STARTDATE = ("Start Date: %s");
	public static final String MESSAGE_WRITE_ENDDATE = ("End Date: %s");
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

	public static final String MESSAGE_SETDIR_TEXTFILEEXT = (".txt");

	public static final String ASSERT_TASK_EXISTENCE = ("Task does not exist");
	public static final String ASSERT_TASKLIST_EXISTENCE = ("Task List does not exist");
	public static final String ASSERT_ACTION_EXISTENCE = ("Action does not exist");
	public static final String ASSERT_TASKID_EXISTENCE = ("Task ID does not exist");
	public static final String ASSERT_FIELD_EXISTENCE = ("Field does not exist");
	public static final String ASSERT_TASKNAME_EXISTENCE = ("Task name does not exist");
	public static final String ASSERT_TASKDETAILS_EXISTENCE = ("Task details does not exist");

	public static final String ASSERT_NULL_COMMAND = "Null command received";
	public static final String ASSERT_VALID_TIME = "Invalid time returned by NaturalTime";
	public static final String ASSERT_VALID_DATE = "Invalid date returned by NaturalDate";

	public static final String ASSERT_SETDIRECTORY_FILENAME_EMPTY = "Filename cannot be empty for set directory";
	public static final String ASSERT_WRITE_ARRAYLISTS = "toDoTaskList and doneTaskList cannot be null";
	public static final String ASSERT_READ_WRONGMETHOD_FILENAME_EMPTYNULL = "Method is not read or retrieve and Filename cannot be empty or null";

	public static final String ASSERT_DISPLAY_ARRAYLISTS = "sortedList and previousInput cannot be null";

	// These constants are used in UserInterface package.
	public static final String EMPTY_STRING = "";
	public static final String CMD_ENTRY_PLACEHOLDER_TEXT = "Enter commands here";
	public static final String COMMAND_LABEL_TEXT = " Command: ";

	public static final String GUI_PREFERENCES_TOOLTIP = "Gui Preferences";
	public static final String HELP_TOOLTIP = "Help";
	public static final String ALL_TASKS_TOOLTIP = "All Tasks";
	public static final String DONE_TASKS_TOOLTIP = "Done Tasks";
	public static final String OVERDUE_TASKS_TOOLTIP = "Overdue Tasks";
	public static final String HOME_TOOLTIP = "Home";

	public static final String DOCKET_ICON_PATH = "/main/icon/d.png";
	public static final String SETTINGS_ICON_PATH = "/main/icon/settings.png";
	public static final String HELP_ICON_PATH = "/main/icon/help.png";
	public static final String ALL_ICON_PATH = "/main/icon/all.png";
	public static final String DONE_ICON_PATH = "/main/icon/done.png";
	public static final String OVERDUE_ICON_PATH = "/main/icon/overdue.png";
	public static final String HOME_ICON_PATH = "/main/icon/home.png";

	public static final String IS_DISPLAY_FLAG = "0";
	public static final String CMD_DISPLAY_FLAG = "1";
	public static final String INVALID_MESSAGE_FLAG = "2";
	public static final String DISPLAY_OVERDUE_COMMAND = "display overdue";
	public static final String HELP_COMMAND = "help";
	public static final String DISPLAY_TODAY_COMMAND = "display today";
	public static final String DISPLAY_DONE_COMMAND = "display done";
	public static final String DISPLAY_COMMAND = "display";

	public static final String BLACK = "#000000 r:0, g:0, b:0";

	public static final String DEFAULT_FONT_SIZE = "14";
	public static final String DEFAULT_FONT_FAMILY = "Times New Roman";

	public static final String SETTINGS_TEXT = "Settings";
	public static final String FONT_TEXT = "Font";
	public static final String FONT_SIZE_TEXT = "Font Size";
	public static final String SAVE_BUTTON_TEXT = "Save";
	public static final String CANCEL_BUTTON_TEXT = "Cancel";

	public static final String OPTION_1_TEXT = "Option 1";
	public static final String OPTION_2_TEXT = "Option 2";
	public static final String OPTION_3_TEXT = "Option 3";
	public static final String OPTION_4_TEXT = "Option 4";
	public static final String DEFAULT_COLOR_TEXT = "Default Colour";

	public static final String PROPERTIES_FILE_NAME = ".\\properties.xml";

	public static final String COLOR_OPTION_KEY = "colorOption";
	public static final String TOP_BG_KEY = "topBg";
	public static final String BOTTOM_BG_KEY = "bottomBg";
	public static final String FONT_SIZE_KEY = "fontSize";
	public static final String FONT_FAMILY_KEY = "fontFamily";
	public static final String BUTTONS_COLOR_KEY = "buttonsColor";

	public static final String[] PROPERTIES_KEYS = { COLOR_OPTION_KEY, TOP_BG_KEY, BOTTOM_BG_KEY, FONT_SIZE_KEY,
			FONT_FAMILY_KEY, BUTTONS_COLOR_KEY };

	public static final String SET_1 = "button r:218, g:216, b:167 topBg r:255, g:158, b:157 bottomBg r:127, g:199, b:175";
	public static final String SET_2 = "button r:241, g:243, b:206 topBg r:206, g:223, b:243 bottomBg r:243, g:220, b:206";
	public static final String SET_3 = "button r:217, g:206, b:178 topBg r:213, g:222, b:217 bottomBg r:148, g:140, b:117";
	public static final String SET_4 = "button r:254, g:109, b:93  topBg r:254, g:216, b:93  bottomBg r:93, g:211, b:254";
	public static final String SET_5 = "button r:176, g:176, b:176 topBg r:255, g:255, b:255 bottomBg r:255, g:198, b:30";
	//public static final String SET_5 = "button r:25, g:25, b:112 topBg r:123, g:104, b:238 bottomBg r:92, g:81, b:159";
	// These are constants used in help.java
	public static final String HELP_FILE_PATH = "/resources/help.xml";
	public static final String WELCOME_STRING = "welcome";
	public static final String FUNCTION = "function";
}