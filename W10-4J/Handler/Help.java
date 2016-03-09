package Handler;

public class Help {

	private static String help;

	private static final String helpWelcome = "<h1>Welcome to the Docket Help Manual</h1>";
	private static final String helpFunction = "For help on specific commands, use the following commands:"
			+ "<ul><li> help add</li>" + "<li> help delete</li>" + "<li> help edit</li>" + "<li> help done</li>"
			+ "<li> help display</li>" + "<li> help search</li>" + "<li> help storage</li>"
			+ "<li> help undo</li></ul>";
	private static final String helpAdd = "<h2> Add Task </h2>Different types of tasks can be added into Task Manager, for example, events and deadlines. <br>"
			+ "To add events with date, start time and end time into Task Manager, use this command: <br>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> &#60your task&#62 <b>date</b> &#60date&#62 <b>start</b> &#60start time&#62 <b>end</b> &#60end time&#62 <b>details</b> &#60task details&#62</span><br>"
			+ "<p>Example:</p>"
			+ "<span style=\"background-color:#E5E4E2;\"><b>add</b> Meeting <b>date</b> 12/2/16 <b>start</b> 12am <b>end</b> 1am <b>details</b> Meeting to complete V0.0</span>"
			+ "<p>To add deadlines with date and time into Task Manager, use this command:</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> &#60deadline&#62 <b>date</b> &#60date&#62 <b>time</b> &#60time&#62<b>details</b> &#60task details&#62</span><br>"
			+ "<p>Example:</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> V0.0 <b>date</b> 14/2/16 <b>time</b> 23.59pm <b>details</b> Project Manual</span><br>"
			+ "<p>To add a floating task without specific times, simply omit the date or time.</p>"
			+ "<span style=\"background-color:#E5E4E2\"><b>add</b> floating task</span><br>";
	private static final String helpDelete = "<h2> Delete Task </h2>If there is a task on hand that you wish to remove from Task Manager, simply enter the following command: "
			+ "<span style=\"background-color:#E5E4E2\">delete &#60taskID&#62</span>. "
			+ "Once user press enter, all task related information will be erased.";

	public Help() {
	}

	public String helpFullString() {
		help = helpWelcome;
		help += helpFunction;
		help += helpAdd;
		help += helpDelete;
		return help;
	}

	public String helpSpecific(String command) {

		switch (command) {
		case "add":
			return helpAdd();
		case "edit":
		case "delete":
			return helpDelete();
		case "done":
		case "display":
		case "search":
		case "setdir":
		case "retrieve":
		case "undo":
		case "exit":
		case "help":
		default:
			throw new Error("Unrecognized command type");
		}
	}

	public String helpWelcome() {
		return helpWelcome;
	}

	public String helpAdd() {
		
		return helpWelcome + helpAdd;
	}

	public String helpDelete() {
		return helpWelcome + helpDelete;
	}
}
