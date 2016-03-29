package Handler;

import main.Constants;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Undo implements Command {

	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Undo(HandlerMemory handlerMemory) {
		this.handlerMemory = handlerMemory;
	}

	public String execute(String[] notUsedInThisCommand, int notUsedInThisCommand2) {
		if (HandlerMemory.getPreviousInputStorage().size() == 0) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_UNDO_FAIL;
		}
		String actionToBeUndone = HandlerMemory.getPreviousInputStorage().get(0).getAction();
		Task previousTask = HandlerMemory.getPreviousInputStorage().get(0).getTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		Task eachTask = null;
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			commandState = COMMAND_STATE.UNDOADD;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			commandState = COMMAND_STATE.UNDODELETE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			commandState = COMMAND_STATE.UNDOEDIT;
			forEachTask = previousTask;
			forOldTask = eachTask;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			commandState = COMMAND_STATE.UNDODONE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			commandState = COMMAND_STATE.UNDOUNDO;
			forEachTask = previousTask;
			break;
		}
		return Constants.MESSAGE_UNDO_PASS;
	}

	public Task returnEachTask() {
		return forEachTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
}
