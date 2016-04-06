//@@author A0135779M

package Handler;


import main.Constants;
import main.Task;

public class Undo implements Command{
	ArraylistStorage arraylistStorage_;
	
	public Undo(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] notUsedInThisCommand) {
		String actionToBeUndone;
		try{
			actionToBeUndone = arraylistStorage_.getPreviousInputAction();
		} catch(IndexOutOfBoundsException e){
			return Constants.MESSAGE_UNDO_FAIL;
		}
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		switch (actionToBeUndone) {
		
		case Constants.MESSAGE_ACTION_BASICOP:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.setNewStorages();
			break;
		
//		case Constants.MESSAGE_ACTION_ADD:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DELETE);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_DELETE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_ADD);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_EDIT:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_EDIT);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_DONE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNDO);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_UNDO:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DONE);
//			arraylistStorage_.setNewStorages();
//			break;
//		
//		case Constants.MESSAGE_ACTION_RETRIEVE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNRETRIEVE);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_UNRETRIEVE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_RETRIEVE);
//			arraylistStorage_.setNewStorages();
//			break;
			
		case Constants.MESSAGE_ACTION_SETDIR:
			arraylistStorage_.rememberOldDirectory();
			arraylistStorage_.getNewDirectory();
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
			arraylistStorage_.setNewDirectory();
			arraylistStorage_.setNewStorages();
			break;
			
//		case Constants.MESSAGE_ACTION_UNSETDIR:
//			arraylistStorage_.rememberOldDirectory();
//			arraylistStorage_.getNewDirectory();
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
//			arraylistStorage_.setNewDirectory();
//			arraylistStorage_.setNewStorages();
//			break;
//			
		}
		// write to mainStorage
		arraylistStorage_.writeToStorage();
		return Constants.MESSAGE_UNDO_PASS;
	}
}
