package Handler;

import main.*;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static HandlerMemory handlerMemory;

	public Handler() {
		handlerMemory = new HandlerMemory();
	}

	//@@author A0149174Y
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			String toBeReturned = cmd.execute(task, HandlerMemory.getTaskID());
			if (command != COMMAND_TYPE.INVALID) {
				HandlerMemory.updateMemory(cmd, command);
			}
			return toBeReturned;
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNĘZED_COMMAND;
		}
	}

	private Command createCommand(COMMAND_TYPE command, String[] task)
			throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			HandlerMemory.setTaskID(HandlerMemory.getTaskID() + 1);
			return new Add(handlerMemory);
		case EDIT:
			return new Edit(handlerMemory);
		case DELETE:
			return new Delete(handlerMemory);
		case DONE:
			return new Done(handlerMemory);
		case DISPLAY:
			return new Display(handlerMemory);
		case SEARCH:
			return new Search(handlerMemory);
		case SETDIR:
			return new SetDir(handlerMemory);
		case RETRIEVE:
			return new Retrieve(handlerMemory);
		case RECURRENCE:
			return new Recurrence(handlerMemory);
		case UNDO:
			return new Undo(handlerMemory);
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
}