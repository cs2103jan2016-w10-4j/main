package main;

public class Help {
	
	private static String help;
	
	private static final String helpWelcome = "<h1>Welcome to the Docket Help Manual</h1>";
	private static final String helpFunction = "For help on specific commands, use the following commands:"
			+ "<ul><li> help add</li>"
			+ "<li> help delete</li>"
			+ "<li> help edit</li>"
			+ "<li> help done</li>"
			+ "<li> help display</li>"
			+ "<li> help search</li>"
			+ "<li> help storage</li>"
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
			+ "Examples:"
			+ "<ul><li>To change an event name in the Task Manager, use the following command:<br>"
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
	public Help(){
	}
	
	public String helpSpecific(String task){
		help = helpWelcome;
		switch (task){
		case "add":
			help += helpAdd;
			break;
		case "delete":
			help += helpDelete;
			break;
		case "edit":
			help += helpEdit;
			break;
		case "done":
			help += helpDone;
			break;
		case "display":
			help += helpDisplay;
			break;
			
			
		}
		return help;
	}
	public String helpFullString(){
		help = helpWelcome;
		help += helpFunction;
		help += helpAdd;
		help += helpDelete;
		help += helpEdit;
		help += helpDone;
		help += helpDisplay;
		return help;
	}
}