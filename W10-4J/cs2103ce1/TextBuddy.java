package cs2103ce1;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author KwokJunKiat A0140114A
 */
public class TextBuddy {
    
    private static final String MESSAGE_ADD = "added to %1$s: \"%2$s\"\n";
    private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"\n";
    private static final String MESSAGE_CLEAR = "all content deleted from %1$s\n";
    private static final String MESSAGE_DISPLAY = "%1$s is empty\n";
    private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use\n";

    private static final String ERROR_MESSAGE_INVALID = "Invalid Command\n";
    private static final String ERROR_MESSAGE_DELETE = "Unable to delete as file does not contain line %1$s\n";

    private static String fileName;
    private static ArrayList<String> contents;

    private static Scanner s = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        retrieveFileName(args);
        retrieveContents();
        for (;;) {
            showToUser("command: ");
            showToUser(executeCommand(s.nextLine()));
        }
    }

    public static String executeCommand(String input) {
        String command = input.split(" ")[0];
        String parameter = input.substring(input.indexOf(" ") + 1);
        switch (command) {
            case "add":
                return add(parameter);
            case "display":
                return display();
            case "delete":
                try {
                    return delete(Integer.parseInt(parameter));
                } catch (Exception e) {
                	e.printStackTrace();
                    return ERROR_MESSAGE_INVALID;
                }
            case "clear":
                return clear();
            case "exit":
                System.exit(0);
        }
        return ERROR_MESSAGE_INVALID;
    }

    public static void retrieveFileName(String[] args) {
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid argument");
            System.exit(0);
        }
    }

    public static void retrieveContents() {
        contents = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.printf(WELCOME_MESSAGE, fileName);
    }

    public static String add(String line) {
        contents.add(line);
        writeToFile(line);
        return String.format(MESSAGE_ADD, fileName, line);
    }

    public static String display() {
    	String output = "";
        if (contents.isEmpty()) {
            return String.format(MESSAGE_DISPLAY, fileName);
        } else {
            int i = 1;
            for (String line : contents) {
                output += String.format(i + ". " + line+"\n");
                i++;
            }
            return output;
        }
    }

    public static String delete(int i) {
        if (contents.size() >= i && i>0) {
            writeToFile(contents);
            return String.format(MESSAGE_DELETE, fileName, contents.remove(i - 1));
        } else {
            return String.format(ERROR_MESSAGE_DELETE, i);
        }
    }

    public static String clear() {
        contents.clear();
        writeToFile();
        return String.format(MESSAGE_CLEAR, fileName);
    }
    
    private static void showToUser(String s){
        System.out.print(s);
    }

    private static void writeToFile(String line) {
        try (PrintWriter print = new PrintWriter(new FileWriter(fileName, true))) {
            print.write(line);
            print.println();
            print.close();
        } catch (IOException e) {
        }
    }

    private static void writeToFile(ArrayList<String> content) {
        try (PrintWriter print = new PrintWriter(new FileWriter(fileName))) {
            for (String line : content) {
                print.write(line);
                print.println();
            }
            print.close();
        } catch (IOException e) {
        }
    }

    private static void writeToFile() {
        try (PrintWriter print = new PrintWriter(new FileWriter(fileName))) {
            print.write("");
            print.close();
        } catch (IOException ex) {
        }
    }
}
