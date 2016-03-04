package main;

import java.io.File;

public class SetDirectory {
	private String success = "Success";
	private String failure = "Failure";
			
	public String setDirectory(String filePathName) {
		if(checkDirectory(filePathName)) {
			Storage.filename = filePathName;
			Write write = new Write();
			write.updatePathSentence(filePathName);
			return success;
		} else {
			return failure;
		}
	}
	
	private boolean checkDirectory(String filePathName) {
		String outcome = checkFileExists(filePathName);
		if (outcome.equals(failure)) {
			return false;
		} else {
			return true;
		}
	}
	
	private String checkFileExists(String filename) {
		String outcome;
		//File file = new File(filename);
		
		String absoluteFileName = "\"" + filename + "\"";
		File file = new File(absoluteFileName);
		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		return outcome;
	}
}