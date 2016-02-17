package main;

public class Constants {
	enum COMMAND_TYPE {
		ADD, DELETE, EDIT, DONE, DISPLAY, SEARCH, RETRIEVE, UNDO, EXIT, INVALID
	};
	public static final String MESSAGE_INVALID_FORMAT = "invalid command format";
	public static final String fileName = ".txt";
	public static final String[] addDefaultCommandList = {"add","new","+"};
	public static final String[] deleteDefaultCommandList = {"del","remove","rm","bin","thrash","-"};
	public static final String[] editDefaultCommandList = {"edit","change","edittask"};
	public static final String[] doneDefaultCommandList = {"done","finish","complete"};
	public static final String[] displayDefaultCommandList = {"display","ls","list","show","print"};
	public static final String[] searchDefaultCommandList = {"search","find","contains"};
	public static final String[] retrieveDefaultCommandList = {"storage","get","open","grab","grep"};
	public static final String[] undoDefaultCommandList = {"undo","whoops","mb"};
	public static final String[] exitDefaultCommandList = {"exit","quit"};
	
	public static final String[] addDefaultArgumentList = {"date","start","end","details"};
	public static final String[] editDefaultArgumentList = {"rename","date","start","end","details"};
	public static final String[] displayDefaultArgumentList = {"by","task","done"};
	public static final String[] searchDefaultArgumentList = {"excl","exclude"};
}
