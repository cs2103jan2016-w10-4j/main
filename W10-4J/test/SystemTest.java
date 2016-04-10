package test;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import org.junit.Test;
import Parser.Parser;
import UserInterface.UserInterface;
//@@author A0149174Y
public class SystemTest {	
	
	@Test
	public void testRobot(){
		String []output;
		String userInput;
		
		userInput="setdir C:\\";
		output=UserInterface.initComponents(userInput);
		
		userInput = "";
		output=UserInterface.initComponents(userInput);
	
	System.out.println(output[0]);
	System.out.println(output[1]);
	
}

@Test
public void test1() {
	String userInput;
	String []output;
	
	userInput="setdir C:\\";
	output=UserInterface.initComponents(userInput);
	assertEquals(output[0],"<html>\n  <head>\n    <style type=\"text/css\">\n      <!--\n        #underline { border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 3px }\n      -->\n    </style>\n    \n  </head>\n  <body>\n    <h1>\n      <b>No tasks on hand!</b>\n    </h1>\n  </body>\n</html>\n");
	assertEquals(output[1],"> setdir C:\\\n> Set directory successful.\n");
	
	userInput = "";
	output=UserInterface.initComponents(userInput);
	assertEquals(output[0],"<html>\n  <head>\n    <style type=\"text/css\">\n      <!--\n        #underline { border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 3px }\n      -->\n    </style>\n    \n  </head>\n  <body>\n    <center>\n      <b>Welcome to Docket! </b>\n    </center>\n    <br>\n    \n\n    <center>\n      To start, enter a task in the command field below.\n    </center>\n    <br>\n    \n\n    <center>\n      For help, enter <b>help</b> in the command field below.\n    </center>\n    <br>\n    \n\n    <center>\n      <b>Tips</b>\n    </center>\n    <br>\n    \n\n    <center>\n      Use Arrow Up or Arrow Down key to scroll through previous commands \n      entered\n    </center>\n  </body>\n</html>\n");
	assertEquals(output[1],"> setdir C:\\\n> Set directory successful.\n\n> Invalid command format\n");
	
	userInput = "add";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput="add V0.44";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="delete 10";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="delete 1";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="add V0.4";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="add V0.5 Manual date 11.04.2016 endtime 23:59";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="add V0.5 Demo date 13.04.2016 starttime 10";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="search v";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
			//assertEquals(output[1],);
	
	userInput="search demo";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
			//assertEquals(output[1],);
	
	userInput="add overdue task date 11.04.2015";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
			//assertEquals(output[1],);
	
	userInput = "display overdue";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput="done 1";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput = "display done";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput = "display today";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput = "display";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput = "edit";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput="edit 2 details wear smart casual";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	userInput="undo";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="help";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="alias add a";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="a check mail repeat day";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="setdir D:\\";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput="get C:\\dirdefaultfile.txt";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
		//assertEquals(output[1],);
	
	userInput = "ls";
	output=UserInterface.initComponents(userInput);
	//assertEquals(output[0],);
	//assertEquals(output[1],);
	
	} 
}