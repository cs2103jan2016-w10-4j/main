# A0126129Junused
###### Handler\CommonFunctionsInDisplay.java
``` java
	/* This method is implemented previously for highlights when 
	 * <PreviousInput> store task object instead of the state
	 */
	public static int checkRecentUpdatedTaskID(ArrayList<Task> currentList, ArrayList<PreviousInput> previousList) {
		int taskID = Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST;

		// Applicable if user calls retrieve method
		if (previousList.size() == 1
				&& previousList.get(0).getAction().equals(Constants.COMMONFUNCTIONS_RETRIEVE)) {
			return Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST;
		}

		// When user first add a task into the empty file
		if (previousList.size() == 1 && currentList.size() == 1) {
			Task previousTask = previousList.get(0).getTask();
			Task currentTask = currentList.get(0);
			if (previousTask.getTaskID() == currentTask.getTaskID()) {
				return previousTask.getTaskID();
			}
		}

		for (int i = 0; i < previousList.size(); i++) {
			Task previousTask = previousList.get(i).getTask();

			for (int h = 0; h < currentList.size(); h++) {
				Task currentTask = currentList.get(h);
				if (previousTask.getTaskID() == currentTask.getTaskID()) {
					boolean isTwoTasksTheSame = compareTasks(previousTask, currentTask);
					if (!(isTwoTasksTheSame)) {
						taskID = previousTask.getTaskID();
					}
				} else {
					taskID = previousTask.getTaskID();
				}
			}

			if (taskID != Constants.COMMONFUNCTIONS_NOT_IN_ARRAYLIST) {
				return taskID;
			}
		}
		return taskID;
	}
	
```
