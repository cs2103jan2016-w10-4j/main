package Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetDirectory {
	public boolean setDirectory(String filePathName) {
		try {
			File file = new File(filePathName);
			if(!(checkDirectory(file))) {
				Path path = FileSystems.getDefault().getPath(filePathName);
				String excludeFileName = filePathName.substring(0, filePathName.lastIndexOf("/") + 1);
				Path pathWithoutFileName = Paths.get(excludeFileName);
				Files.createDirectories(pathWithoutFileName);
				Files.createFile(path);
			} 
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private boolean checkDirectory(File file) {
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}
}