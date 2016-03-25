package Handler;

import main.Constants;

public class Help implements Command {

	private static String help;

	private static final String helpWelcome = "<style>table{border: 1px solid black; background-color: black;} td, th{background-color:white;}</style><h1>Welcome to the Docket Help Manual</h1>";
	private static final String helpFunction = "For help on specific commands, use the following commands:"
			+ "<ul><li> help add</li>" + "<li> help delete</li>" + "<li> help edit</li>" + "<li> help done</li>"
			+ "<li> help display</li>" + "<li> help search</li>"
			+ "<li> help storage (Functions retrieve and set directory is documented here)</li>"
			+ "<li> help undo</li></ul>";
	private static final String helpAdd = "<h2> Add Task </h2>Different types of tasks can be added into Task Manager, for example, events and deadlines. <br>"
			+ "To add events with date, start time and end time into Task Manager, use this command: <br>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> &#60your task&#62 <b>date</b> &#60date&#62 <b>start</b> &#60start time&#62 <b>end</b> &#60end time&#62 <b>details</b> &#60task details&#62</span><br>"
			+ "<ul><li><p>Example:</p>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>add</b> Meeting <b>date</b> 12/2/16 <b>start</b> 12am <b>end</b> 1am <b>details</b> Meeting to complete V0.0</span></li></ul>"
			+ "<p>To add deadlines with date and time into Task Manager, use this command:</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> &#60deadline&#62 <b>date</b> &#60date&#62 <b>time</b> &#60time&#62<b>details</b> &#60task details&#62</span><br>"
			+ "<ul><li><p>Example:</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> V0.0 <b>date</b> 14/2/16 <b>time</b> 23.59pm <b>details</b> Project Manual</span><br></ul></li>"
			+ "<p>To add a floating task without specific times, simply omit the date or time.</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> floating task</span><br>";
	private static final String helpDelete = "<h2> Delete Task </h2>If there is a task on hand that you wish to remove from Task Manager, simply enter the following command: "
			+ "<span style=\"background-color:#E5E4E2\">delete &#60taskID&#62</span>. "
			+ "Once user press enter, all task related information will be erased.";
	private static final String helpEdit = "<h2>Edit Task</h2>Details of the tasks added to the Task Manager can be edited. "
			+ "Features of each task that can be edited includes the event name, its date, its start and end times as well as its details.<br>"
			+ "<span style=\"background-color:#E5E4E2\"><b>edit</b> &#60taskID&#62 <b>rename</b> &#60new name&#62 <b>date</b> &#60new date&#62 <b>start</b> &#60new start time&#62 <b>end</b> &#60new end time&#62 <b>details</b> &#60new details&#62 </span><br>"
			+ "Examples:" + "<ul><li>To change an event name in the Task Manager, use the following command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>edit</b> &#60taskID&#62 <b>rename</b> &#60new name&#62</span></li>"
			+ "<li>To change an event date in the Task Manager, use the following command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>edit</b> &#60taskID&#62 <b>date</b> &#60new date&#62</span></li>"
			+ "<li>To change an event start time in the Task Manager, use the following command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>edit</b> &#60taskID&#62 <b>start</b> &#60new start time&#62</span></li>"
			+ "<li>To change an event end time in the Task Manager, use the following command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>edit</b> &#60taskID&#62 <b>end</b> &#60new end time&#62</span></li"
			+ "<li>To change an event details in the Task Manager, use the following command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>edit</b> &#60taskID&#62 <b>details</b> &#60new details&#62</span></li></ul>";
	private static final String helpDone = "<h2> Done Task</h2>To specify a task is done, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>done</b> &#60taskID&#62</span>";
	private static final String helpDisplay = "<h2>Display Task</h2>To keep track of all the tasks you have on hand, and to list tasks that you have done, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display by</b> &#60sorting term&#62</span>"
			+ "<ul><li>To display tasks alphabetically based on the name of the task, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display by</b> alphabetical order</span></li>"
			+ "<li>To display tasks based on the start time, task with unspecified start time will be placed at the bottom, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display by</b> starttime</span></li>"
			+ "<li>To display tasks based on the end time of the task, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display by</b> endtime</span></li>"
			+ "<li>To display tasks based on the date of the task, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display by</b> date</span></li>"
			+ "<li>To keep track of only the tasks that you have on hand, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display</b> tasks</span></li>"
			+ "<li>To keep track of tasks that you have done, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>display</b> done</span></li></ul>";
	private static final String helpSearch = "<h2> Simple Search </h2> A simple text search for finding an item if the user remembers some keywords from the item description."
			+ " To search for a keyword and excluding another, use this command: <br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>search</b> &#60keyword&#62 <b>excl</b> &#60exclude keyword&#62</span>"
			+ "<ul><li>Examples:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>search</b> task <b>excl</b> home</span></li></ul>"
			+ "To search for a keyword, use this command:<br> "
			+ "<span style=\"background-color:#E5E4E2;\"><b>search</b> &#60keyword&#62</span>" + "<ul><li>Examples:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>search</b> task </span></li></ul>";
	private static final String helpStore = "<h2> Storage </h2> To specify a particular path to the text file to retrieve the task list from, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>retrieve</b> &#60datapath&#62 </span><br>"
			+ "This will add the tasks in the text file specified to the default file in Docket."
			+ "<ul><li>Examples:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>retrieve</b> C:\\Users\\User\\Desktop\\mytextfile.txt </span></li></ul>"
			+ "To specify a particular folder to store the task list in, use this command:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>set directory</b> &#60datapath&#62</span><br>"
			+ "In using this command, future uses of Docket will be on this new datapath. If the file does not exist, a new empty text file will be created. To move the tasks from the default text file in Docket to the new path, use <b>retrieve</b>."
			+ "<ul><li>Examples:<br>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>set directory</b> C:\\Users\\User\\Desktop</span></li></ul>";
	private static final String helpUndo = "<h2> Undo Operations </h2>User can enter the <span style=\"background-color:#E5E4E2;\"><b>undo</b></span> command and the program will undo the most recent action command entered by the user, such as add, delete, edit, done.";
	private static final String helpNatural = "<h2> Natural Commands </h2> Users can enter commands that sounds like something you would naturally say, in your own language, rather than some thing you would say only because you’re talking to a computer.<br>"
			+ "Valid Natural Commands:<br>" + "<table>" + "<tr><th align=\"left\">Function</th>"
			+ "<th align=\"left\">Natural Commands</th></tr>" + "<tr><td>add</td>" + "<td>new, + </td></tr>"
			+ "<tr><td>delete</td>" + "<td>del, remove, rm, bin, trash, -</td></tr>" + "<tr><td>edit</td>"
			+ "<td>change, edittask</td></tr>" + "<tr><td>done</td>" + "<td>finish, complete</td></tr>"
			+ "<tr><td>display</td>" + "<td>ls, list, show, print</td></tr>" + "<tr><td>search</td>"
			+ "<td>find, contains</td></tr>" + "<tr><td>storage</td>" + "<td>get, open, grab, grep</td></tr>"
			+ "<tr><td>undo</td>" + "<td>whoops, mb</td></tr>" + "<tr><td>exit</td>" + "<td>quit</td></tr>"
			+ "</table>";
	private static final String helpKeyboard = "<h2> Keyboard Shortcuts </h2> To help you use Docket more efficiently, some keyboard shortcuts have been implemented."
			+ "<table>" + "<tr><th align=\"left\">Keyboard Shortcut</th>" + "<th align=\"left\">Description</th></tr>"
			+ "<tr><td>Ctrl + Shift + \"=\"</td>" + "<td>Increases font size in the top white display</td></tr>"
			+ "<tr><td>Ctrl + Shift + \"-\"</td>" + "<td>Decreases font size in the top white display</td></tr>"
			+ "<tr><td>Arrow Up / Arrow Down</td>" + "<td>Returns the previous commands in the command field</td></tr>"
			+ "<tr><td>PgUp / PgDn</td>" + "<td>Scrolls through the top white display</td></tr>" + "</table>";

	public Help() {
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if (task.length == 0) {
			return helpFullString();
		} else {
			return helpSpecific(task[0]);
		}
	}

	public String helpSpecific(String task) {
		help = helpWelcome;
		switch (task) {
		case Constants.MESSAGE_ACTION_ADD:
			help += helpAdd;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			help += helpDelete;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			help += helpEdit;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			help += helpDone;
			break;
		case Constants.MESSAGE_ACTION_DISPLAY:
			help += helpDisplay;
			break;
		case Constants.MESSAGE_ACTION_SEARCH:
			help += helpSearch;
			break;
		case Constants.MESSAGE_ACTION_STORAGE:
			help += helpStore;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			help += helpUndo;
			break;
		}
		return help;
	}

	public String helpFullString() {
		help = helpWelcome;
		help += helpFunction;
		help += helpAdd;
		help += helpDelete;
		help += helpEdit;
		help += helpDone;
		help += helpDisplay;
		help += helpSearch;
		help += helpStore;
		help += helpUndo;
		help += helpNatural;
		help += helpKeyboard;
		return help;
	}
}