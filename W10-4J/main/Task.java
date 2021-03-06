//@@author A0135779M

package main;

import java.util.Calendar;
import java.util.Comparator;

public class Task {
	private String name_;
	private String startdate_;
	private String enddate_;
	private String startTime_;
	private String endTime_;
	private String details_;
	private int taskID_;
	private boolean recurring_, day_, week_, month_, year_;
	private boolean multiday_;

	// use trim method to ensure all strings are compared properly
	public static Comparator<Task> taskIDComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			return (task1.getTaskID() - task2.getTaskID());
		}
	};
	public static Comparator<Task> taskNameComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			return (task1.getName().toLowerCase().trim().compareTo(task2.getName().toLowerCase().trim()));
		}
	};
	public static Comparator<Task> taskDetailsComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			return (task1.getDetails().toLowerCase().compareTo(task2.getDetails().toLowerCase()));
		}
	};
	public static Comparator<Task> taskStarttimeComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			String[] startTime1 = task1.getStartTime().split(":");
			String[] startTime2 = task2.getStartTime().split(":");
			int hour1 = Integer.parseInt(startTime1[0].trim());
			int hour2 = Integer.parseInt(startTime2[0].trim());
			int min1 = Integer.parseInt(startTime1[1].trim());
			int min2 = Integer.parseInt(startTime2[1].trim());
			if (hour1 == hour2) {
				return min1 - min2;
			} else {
				return hour1 - hour2;
			}
		}
	};
	public static Comparator<Task> taskEndtimeComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			String[] endTime1 = task1.getEndTime().split(":");
			String[] endTime2 = task2.getEndTime().split(":");
			int hour1 = Integer.parseInt(endTime1[0].trim());
			int hour2 = Integer.parseInt(endTime2[0].trim());
			int min1 = Integer.parseInt(endTime1[1].trim());
			int min2 = Integer.parseInt(endTime2[1].trim());
			if (hour1 == hour2) {
				return min1 - min2;
			} else {
				return hour1 - hour2;
			}
		}
	};
	public static Comparator<Task> taskStartDateComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			return (splitStartDateObject(task1).compareTo(splitStartDateObject(task2)));
		}
	};
	public static Comparator<Task> taskEndDateComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			return (splitEndDateObject(task1).compareTo(splitEndDateObject(task2)));
		}
	};
	public static Comparator<Task> taskStartDateAndNameComparator = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			int startDateValue = taskStartDateComparator.compare(task1, task2);
			int nameValue = taskNameComparator.compare(task1, task2);
			if (startDateValue == 0) {
				return nameValue;
			} else {
				return startDateValue;
			}
		}
	};

	private static Calendar splitStartDateObject(Task task) {
		// assumes that the date is in yyyy/mm/dd format
		String[] date = task.getStartDate().split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(date[0].trim()), Integer.parseInt(date[1].trim()),
				Integer.parseInt(date[2].trim()));
		return calendar;
	}

	private static Calendar splitEndDateObject(Task task) {
		// assumes that the date is in yyyy/mm/dd format
		String[] date = task.getEndDate().split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(date[0].trim()), Integer.parseInt(date[1].trim()),
				Integer.parseInt(date[2].trim()));
		return calendar;
	}

	// @@author A0140114A
	public Task(String name) {
		name_ = name;
	}

	public void nextStartDate() {
		if (startdate_ != null) {
			if (day_) {
				startdate_ = Date.addDay(startdate_);
			} else if (week_) {
				for (int i = 0; i < 7; i++) {
					startdate_ = Date.addDay(startdate_);
				}
			} else if (month_) {
				startdate_ = Date.addMonth(startdate_);
			} else if (year_) {
				startdate_ = Date.addYear(startdate_);
			}
		}
	}

	public void nextEndDate() {
		if (enddate_ != null) {
			if (day_) {
				enddate_ = Date.addDay(enddate_);
			} else if (week_) {
				for (int i = 0; i < 7; i++) {
					enddate_ = Date.addDay(enddate_);
				}
			} else if (month_) {
				enddate_ = Date.addMonth(enddate_);
			} else if (year_) {
				enddate_ = Date.addYear(enddate_);
			}
		}
	}

	public void resetRecursion() {
		recurring_ = false;
		day_ = false;
		week_ = false;
		month_ = false;
		year_ = false;
	}

	public void setName(String name) {
		name_ = name;
	}

	public void setStartDate(String startdate) {
		startdate_ = startdate;
	}

	public void setEndDate(String enddate) {
		enddate_ = enddate;
	}

	public void setStartTime(String startTime) {
		startTime_ = startTime;
	}

	public void setEndTime(String endTime) {
		endTime_ = endTime;
	}

	public void setDetails(String details) {
		details_ = details;
	}

	public void setTaskID(int taskID) {
		taskID_ = taskID;
	}

	public void setDay(boolean day) {
		if (day) {
			recurring_ = true;
			day_ = day;
			week_ = false;
			month_ = false;
			year_ = false;
		} else {
			day_ = day;
		}
	}

	public void setWeek(boolean week) {
		if (week) {
			recurring_ = true;
			day_ = false;
			week_ = week;
			month_ = false;
			year_ = false;
		} else {
			week_ = week;
		}
	}

	public void setMonth(boolean month) {
		if (month) {
			recurring_ = true;
			day_ = false;
			week_ = false;
			month_ = month;
			year_ = false;
		} else {
			month_ = month;
		}
	}

	public void setYear(boolean year) {
		if (year) {
			recurring_ = true;
			day_ = false;
			week_ = false;
			month_ = false;
			year_ = year;
		} else {
			year_ = year;
		}
	}

	public void setMultiDay(boolean multiDay) {
		multiday_ = multiDay;
	}

	public String getName() {
		return name_;
	}

	public String getStartDate() {
		return startdate_;
	}

	public String getEndDate() {
		return enddate_;
	}

	public String getStartTime() {
		return startTime_;
	}

	public int getStartTimeInt() {
		try {
			String output = startTime_.split(":")[0] + startTime_.split(":")[1];
			return Integer.parseInt(output);
		} catch (Exception e) {
			return -1;
		}
	}

	public String getEndTime() {
		return endTime_;
	}

	public int getEndTimeInt() {
		try {
			String output = endTime_.split(":")[0] + endTime_.split(":")[1];
			return Integer.parseInt(output);
		} catch (Exception e) {
			return -1;
		}
	}

	public String getDetails() {
		return details_;
	}

	public int getTaskID() {
		return taskID_;
	}

	public boolean getDay() {
		return day_;
	}

	public boolean getWeek() {
		return week_;
	}

	public boolean getMonth() {
		return month_;
	}

	public boolean getYear() {
		return year_;
	}

	public boolean isRecurring() {
		return recurring_;
	}

	public boolean isMultiDay() {
		return multiday_;
	}

	public int isDateValid() {
		if (startdate_ == null || enddate_ == null) {
			return 1;
		}
		int startYear = Integer.parseInt(startdate_.split("/")[0]);
		int startMonth = Integer.parseInt(startdate_.split("/")[1]);
		int startDay = Integer.parseInt(startdate_.split("/")[2]);
		int endYear = Integer.parseInt(enddate_.split("/")[0]);
		int endMonth = Integer.parseInt(enddate_.split("/")[1]);
		int endDay = Integer.parseInt(enddate_.split("/")[2]);
		if (startYear > endYear) {
			return -1;
		} else if (startYear < endYear) {
			return 1;
		} else {
			if (startMonth > endMonth) {
				return -1;
			} else if (startMonth < endMonth) {
				return 1;
			} else {
				if (startMonth > endMonth) {
					return -1;
				} else {
					if (startDay > endDay) {
						return -1;
					} else if (startDay < endDay) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}
}