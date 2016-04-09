# A0126129Junused
###### W10-4J\Handler\CommonFunctionsInDisplay.java
``` java
	/* This method is implemented previously for highlights when 
	 * <PreviousInput> store task object instead of the state
	 */
	public static int checkRecentUpdatedTaskID(ArrayList<Task> currentList, ArrayList<PreviousInput> previousList) {
		int taskID = Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST;

		// Applicable if user calls retrieve method
		if (previousList.size() == 1
				&& previousList.get(0).getAction().equals(Constants.MESSAGE_COMMONFUNCTION_RETRIEVE)) {
			return Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST;
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

			if (taskID != Constants.MESSAGE_COMMONFUNCTION_NOTINARRAYLLIST) {
				return taskID;
			}
		}
		return taskID;
	}

	private static boolean compareTasks(Task previousTask, Task currentTask) {
		boolean isSameName = compareName(previousTask, currentTask);
		boolean isSameStartDate = compareStartDate(previousTask, currentTask);
		boolean isSameEndDate = compareEndDate(previousTask, currentTask);
		boolean isSameStartTime = compareStartTime(previousTask, currentTask);
		boolean isSameEndTime = compareEndTime(previousTask, currentTask);
		boolean isSameDetails = compareDetails(previousTask, currentTask);
		boolean isSameTaskID = compareTaskID(previousTask, currentTask);

		if (isSameName && isSameStartDate && isSameEndDate && isSameStartTime 
				&& isSameEndTime && isSameDetails && isSameTaskID) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareName(Task previousTask, Task currentTask) {
		if (previousTask.getName() == null && currentTask.getName() == null) {
			return true;
		} else if (previousTask.getName() != null && currentTask.getName() == null) {
			return false;
		} else if (previousTask.getName() == null && currentTask.getName() != null) {
			return false;
		} else if (previousTask.getName().equals(currentTask.getName())) { 
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareStartDate(Task previousTask, Task currentTask) {
		if (previousTask.getStartDate() == null && currentTask.getStartDate() == null) {
			return true;
		} else if (previousTask.getStartDate() == null && currentTask.getStartDate() != null) {
			return false;
		} else if (previousTask.getStartDate() != null && currentTask.getStartDate() == null) {
			return false;
		} else if (previousTask.getStartDate().equals(currentTask.getStartDate())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareEndDate(Task previousTask, Task currentTask) {
		if (previousTask.getEndDate() == null && currentTask.getEndDate() == null) {
			return true;
		} else if (previousTask.getEndDate() == null && currentTask.getEndDate() != null) {
			return false;
		} else if (previousTask.getEndDate() != null && currentTask.getEndDate() == null) {
			return false;
		} else if (previousTask.getEndDate().equals(currentTask.getEndDate())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareStartTime(Task previousTask, Task currentTask) {
		if (previousTask.getStartTime() == null && currentTask.getStartTime() == null) {
			return true;
		} else if (previousTask.getStartTime() == null && currentTask.getStartTime() != null) {
			return false;
		} else if (previousTask.getStartTime() != null && currentTask.getStartTime() == null) {
			return false;
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)
				&& currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) 
				&& !(currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH))) {
			return false;
		} else if (!(previousTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) 
				&& currentTask.getStartTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return false;
		} else if (previousTask.getStartTime().equals(currentTask.getStartTime())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareEndTime(Task previousTask, Task currentTask) {
		if (previousTask.getEndTime() == null && currentTask.getEndTime() == null) {
			return true;
		} else if (previousTask.getEndTime() == null && currentTask.getEndTime() != null) {
			return false;
		} else if (previousTask.getEndTime() != null && currentTask.getEndTime() == null) {
			return false;
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)
				&& currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return true;
		} else if (previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH) 
				&& !(currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH))) {
			return false;
		} else if (!(previousTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) 
				&& currentTask.getEndTime().equals(Constants.MESSAGE_COMMONFUNCTION_DASH)) {
			return false;
		} else if (previousTask.getEndTime().equals(currentTask.getEndTime())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareDetails(Task previousTask, Task currentTask) {
		if (previousTask.getDetails() == null && currentTask.getDetails() == null) {
			return true;
		} else if (previousTask.getDetails() == null && currentTask.getDetails() != null) {
			return false;
		} else if (previousTask.getDetails() != null && currentTask.getDetails() == null) {
			return false;
		} else if (previousTask.getDetails().equals(currentTask.getDetails())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean compareTaskID(Task previousTask, Task currentTask) {
		return previousTask.getTaskID() == currentTask.getTaskID();
	}

```
