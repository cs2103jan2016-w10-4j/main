package Handler;

import main.*;
import java.util.ArrayList;
import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static ArrayList<Task> handlerMemory;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	private int taskID;
	Storage mainStorage = new Storage();

	public Handler() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read("Read", Constants.fileName);
		handlerMemory = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
		taskID = getTaskId();
	}
//@@author Berkin
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try{
			Command cmd = createCommand(command,task);
			return cmd.execute(task,taskID);
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNÝZED_COMMAND;
			}
		}
	private Command createCommand(COMMAND_TYPE command, String[] task) throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			return new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case EDIT:
			return new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DELETE:
			return new Delete(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DONE:
			return new Done(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DISPLAY:
			return new Display(handlerMemory, doneStorage, mainStorage);
		case SEARCH:
			return new Search(handlerMemory, mainStorage);
		case SETDIR:
			return new SetDir(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case RETRIEVE:
			return new Retrieve(handlerMemory, doneStorage, mainStorage);
		case RECURRENCE:
			return new Recurrence(handlerMemory,doneStorage,previousInputStorage,mainStorage);
		case UNDO:
			return new Undo(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case EXIT:
			System.exit(0);
		case HELP:
			return new Help();
		case INVALID:
			throw new IllegalArgumentException();
		default:
			throw new IllegalStateException();
		}
	}
//@@author
	
	private int getTaskId(){
		int id = 0;
		for(int i = 0 ;i <handlerMemory.size();i++){
			int currentId = handlerMemory.get(i).getTaskID();
			if(currentId>id){
				id = currentId;
			}
		}
		for(int i = 0 ;i <doneStorage.size();i++){
			int currentId = doneStorage.get(i).getTaskID();
			if(currentId>id){
				id = currentId;
			}
		}
		return ++id;
	}
}