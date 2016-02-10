package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
	private PrintWriter print;
	private BufferedReader read;

	public Storage() {
		try {
			read = new BufferedReader(new FileReader(Constants.fileName));
			print = new PrintWriter(new FileWriter(Constants.fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(String task) {

	}

	public String retrieve(String task) {
		return "";
	}

	public boolean delete(String task) {
		return true;
	}
}