# A0135779Munused
###### Handler\Sorting.java
``` java
			case Constants.MESSAGE_DISPLAY_FIELD_STARTTIME:
			for (Task task : taskArray) {
				if (task.getStartTime() == null) {
					result.add(task);
				}
			}
			break;
			case Constants.MESSAGE_DISPLAY_FIELD_ENDTIME:
			for (Task task : taskArray) {
				if (task.getEndTime() == null) {
					result.add(task);
				}
			}
			break;
			case Constants.MESSAGE_DISPLAY_FIELD_ENDDATE:
				for (Task task : taskArray) {
					if (task.getEndDate() == null) {
						result.add(task);
					}
				}
			break;
			
```
###### Handler\Sorting.java
``` java
	private void sortByStart(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_STARTTIME);
		Collections.sort(clonenotDoneYetStorage, Task.taskStarttimeComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
	
	private void sortByEnd(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_ENDTIME);
		Collections.sort(clonenotDoneYetStorage, Task.taskEndtimeComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
	 
	private void sortByEndDate(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_ENDDATE);
		Collections.sort(clonenotDoneYetStorage, Task.taskEndDateComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
```
###### Handler\Undo.java
``` java
		case Constants.MESSAGE_ACTION_ADD:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DELETE);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_DELETE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_ADD);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_EDIT:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_EDIT);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_DONE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNDO);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_UNDO:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DONE);
			arraylistStorage_.setNewStorages();
			break;
		
		case Constants.MESSAGE_ACTION_RETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNRETRIEVE);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_UNRETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_RETRIEVE);
			arraylistStorage_.setNewStorages();
			break;
			
```
###### Handler\Undo.java
``` java
		case Constants.MESSAGE_ACTION_UNSETDIR:
			arraylistStorage_.rememberOldDirectory();
			arraylistStorage_.getNewDirectory();
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
			arraylistStorage_.setNewDirectory();
			arraylistStorage_.setNewStorages();
			break;
```
