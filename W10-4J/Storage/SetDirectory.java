package Storage;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetDirectory {
	private String success = "Success";
	private String failure = "Failure";

	public boolean setDirectory(String filePathName) {
		File file = new File(filePathName);
		Path path = FileSystems.getDefault().getPath(filePathName);
		try {
			if (file.isDirectory()) {
				Files.createFile(path);
			} else {
				String excludeFileName = filePathName.substring(0, filePathName.lastIndexOf("/") + 1);
				Path pathWithoutFileName = Paths.get(excludeFileName);
				Files.createDirectories(pathWithoutFileName);
				Files.createFile(path);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
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
		File file = new File(filename);

		//String absoluteFileName = "\"" + filename + "\"";
		//File file = new File(absoluteFileName);
		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		return outcome;
	}
}