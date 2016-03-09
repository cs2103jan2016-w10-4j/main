package Storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Read {
	private BufferedReader read;

	public String[] readFromFile() {
		try {
			String content;
			ArrayList<String> arrayList = new ArrayList<String>();
			read = new BufferedReader(new FileReader(Storage.filename));
			
			while ((content = read.readLine()) != null) {
				arrayList.add(content);
			}
			
			String [] readTaskList = new String [arrayList.size()];
			for(int i = 0; i < arrayList.size(); i++) {
				readTaskList[i] = arrayList.get(i);
			}
			
			read.close();
			return readTaskList;
		} catch (IOException e) {
			return null;
		}
	}

	// Method overloading for read()
	public String [] readFromFile(BufferedReader reader) {
		try {
			String content;
			ArrayList<String> arrayList = new ArrayList<String>();
			
			while ((content = reader.readLine()) != null) {
				arrayList.add(content);
			}
			
			String [] readTaskList = new String [arrayList.size()];
			for(int i = 0; i < arrayList.size(); i++) {
				readTaskList[i] = arrayList.get(i);
			}
			
			reader.close();
			return readTaskList;
		} catch (IOException e) {
			return null;
		}
	}
}
