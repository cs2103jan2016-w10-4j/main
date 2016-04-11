package test;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import UserInterface.UserInterface;

//@@author A0149174Y
public class SystemTest {

	// @Test
	// public void testRobot() {
	// String[] output;
	// String userInput;
	//
	// userInput = "setdir C:\\";
	// output = UserInterface.initComponents(userInput);
	//
	// userInput = "";
	// output = UserInterface.initComponents(userInput);
	//
	// System.out.println(output[0]);
	// System.out.println(output[1]);
	//
	// }
	
	@Before
	public void Before() {
		try {
			File f = new File("mytextfile.txt");
			f.delete();
			f = new File("a.txt");
			f.delete();
		} catch (Exception e) {
		}
	}
	
	@After
	public void After() {
		try {
			File f = new File("mytextfile.txt");
			f.delete();
			f = new File("a.txt");
			f.delete();
		} catch (Exception e) {
		}
	}

	@Test
	public void test1() throws InterruptedException {
		String userInput;
		String[] output;

		userInput = "setdir a.txt";
		output = UserInterface.initComponents(userInput);
		assertEquals(output[0],
				"<html>\n  <head>\n    <style type=\"text/css\">\n      <!--\n        #underline { border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 3px }\n      -->\n    </style>\n    \n  </head>\n  <body>\n    <h1>\n      <b>No tasks on hand!</b>\n    </h1>\n  </body>\n</html>\n");
		assertEquals(output[1], "> setdir a.txt\n> Set directory successful.\n");
		Thread.sleep(500);

		userInput = "add a";
		output = UserInterface.initComponents(userInput);
		assertEquals(output[0],
				"<html>\n  <head>\n    <style type=\"text/css\">\n      <!--\n        #underline { border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 3px }\n      -->\n    </style>\n    \n  </head>\n  <body>\n    <table id=\"underline\">\n      <tr>\n        <th>\n          Floating Tasks\n        </th>\n      </tr>\n    </table>\n    <table width=\"100%\" style=\"margin-bottom: 10px\">\n      <tr style=\"border-bottom-color: #B6B6B4; border-bottom-style: solid; border-bottom-width: 1px\">\n        <th style=\"width: 3%\">\n          \n        </th>\n        <th align=\"left\" style=\"width: 20%\">\n          <h2>\n            <b>Event </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Start Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>End Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 25%\">\n          <h2>\n            <b>Details </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Repeat </b>\n          </h2>\n        </th>\n        </ltr>\n        \n      </tr>\n      <tr bgcolor=\"#FFFF00\" style=\"border-bottom-color: #E5E4E2; border-bottom-style: solid; border-bottom-width: 1px\">\n        <td align=\"right\">\n          <h3>\n            <font color=\"#000000\">1)</font>\n          </h3>\n        </td>\n        <td>\n          <font color=\"#000000\">a</font>\n        </td>\n        <td>\n          \n        </td>\n        <td>\n          \n        </td>\n      </tr>\n    </table>\n  </body>\n</html>\n");
		assertEquals(output[1], "> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n");
		Thread.sleep(500);

		userInput = "add V0.44 today 8am tmr 7pm week";
		output = UserInterface.initComponents(userInput);
		assertEquals(output[0],
				"<html>\n  <head>\n    <style type=\"text/css\">\n      <!--\n        #underline { border-bottom-color: black; border-bottom-style: solid; border-bottom-width: 3px }\n      -->\n    </style>\n    \n  </head>\n  <body>\n    <table id=\"underline\">\n      <tr>\n        <th>\n          Today, 2016/04/11\n        </th>\n      </tr>\n    </table>\n    <table width=\"100%\" style=\"margin-bottom: 10px\">\n      <tr style=\"border-bottom-color: #B6B6B4; border-bottom-style: solid; border-bottom-width: 1px\">\n        <th style=\"width: 3%\">\n          \n        </th>\n        <th align=\"left\" style=\"width: 20%\">\n          <h2>\n            <b>Event </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Start Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>End Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 25%\">\n          <h2>\n            <b>Details </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Repeat </b>\n          </h2>\n        </th>\n        </ltr>\n        \n      </tr>\n      <tr bgcolor=\"#FFFF00\" style=\"border-bottom-color: #E5E4E2; border-bottom-style: solid; border-bottom-width: 1px\">\n        <td align=\"right\">\n          <h3>\n            <font color=\"#0000FF\">1)</font>\n          </h3>\n        </td>\n        <td>\n          <font color=\"#0000FF\">V0.44</font>\n        </td>\n        <td>\n          <font color=\"#0000FF\">08:00</font>\n        </td>\n        <td>\n          <font color=\"#0000FF\">-</font>\n        </td>\n        <td>\n          \n        </td>\n        <td>\n          <font color=\"#0000FF\">Every Week</font>\n        </td>\n      </tr>\n    </table>\n    <table id=\"underline\">\n      <tr>\n        <th>\n          Tomorrow, 2016/04/12\n        </th>\n      </tr>\n    </table>\n    <table width=\"100%\" style=\"margin-bottom: 10px\">\n      <tr style=\"border-bottom-color: #B6B6B4; border-bottom-style: solid; border-bottom-width: 1px\">\n        <th style=\"width: 3%\">\n          \n        </th>\n        <th align=\"left\" style=\"width: 20%\">\n          <h2>\n            <b>Event </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Start Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>End Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 25%\">\n          <h2>\n            <b>Details </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Repeat </b>\n          </h2>\n        </th>\n        </ltr>\n        \n      </tr>\n      <tr bgcolor=\"#FFFF00\" style=\"border-bottom-color: #E5E4E2; border-bottom-style: solid; border-bottom-width: 1px\">\n        <td align=\"right\">\n          <h3>\n            <font color=\"#0000FF\">1)</font>\n          </h3>\n        </td>\n        <td>\n          <font color=\"#0000FF\">V0.44</font>\n        </td>\n        <td>\n          <font color=\"#0000FF\">-</font>\n        </td>\n        <td>\n          <font color=\"#0000FF\">19:00</font>\n        </td>\n        <td>\n          \n        </td>\n        <td>\n          <font color=\"#0000FF\">Every Week</font>\n        </td>\n      </tr>\n    </table>\n    <table id=\"underline\">\n      <tr>\n        <th>\n          Floating Tasks\n        </th>\n      </tr>\n    </table>\n    <table width=\"100%\" style=\"margin-bottom: 10px\">\n      <tr style=\"border-bottom-color: #B6B6B4; border-bottom-style: solid; border-bottom-width: 1px\">\n        <th style=\"width: 3%\">\n          \n        </th>\n        <th align=\"left\" style=\"width: 20%\">\n          <h2>\n            <b>Event </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Start Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>End Time </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 25%\">\n          <h2>\n            <b>Details </b>\n          </h2>\n        </th>\n        <th align=\"left\" style=\"width: 15%\">\n          <h2>\n            <b>Repeat </b>\n          </h2>\n        </th>\n        </ltr>\n        \n      </tr>\n      <tr style=\"border-bottom-color: #E5E4E2; border-bottom-style: solid; border-bottom-width: 1px\">\n        <td align=\"right\">\n          <h3>\n            <font color=\"#000000\">2)</font>\n          </h3>\n        </td>\n        <td>\n          <font color=\"#000000\">a</font>\n        </td>\n        <td>\n          \n        </td>\n        <td>\n          \n        </td>\n      </tr>\n    </table>\n  </body>\n</html>\n");
		assertEquals(output[1],
				"> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n> add V0.44 today 8am tmr 7pm week\n> V0.44 has been added.\n");
		Thread.sleep(500);

		userInput = "adder";
		output = UserInterface.initComponents(userInput);
		// assertEquals(output[0],"");
		assertEquals(output[1],
				"> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n> add V0.44 today 8am tmr 7pm week\n> V0.44 has been added.\n> adder\n> Unrecognized command type\n");
		Thread.sleep(500);

		userInput = "delete 10";
		output = UserInterface.initComponents(userInput);
		// assertEquals(output[0],);
		assertEquals(output[1],
				"> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n> add V0.44 today 8am tmr 7pm week\n> V0.44 has been added.\n> adder\n> Unrecognized command type\n> delete 10\n> Task cannot be deleted.\n");
		Thread.sleep(500);

		userInput = "delete 1";
		output = UserInterface.initComponents(userInput);
		// assertEquals(output[0],"<html>\n <head>\n <style type=\"text/css\">\n
		// <!--\n #underline { border-bottom-color: black; border-bottom-style:
		// solid; border-bottom-width: 3px }\n -->\n </style>\n \n </head>\n
		// <body>\n <center>\n <b>Welcome to Docket! </b>\n </center>\n <br>\n
		// \n\n <center>\n To start, enter a task in the command field below.\n
		// </center>\n <br>\n \n\n <center>\n For help, enter <b>help</b> in the
		// command field below.\n </center>\n <br>\n \n\n <center>\n
		// <b>Tips</b>\n </center>\n <br>\n \n\n <center>\n Use Ctrl + Shift +
		// &quot;+&quot; or Ctrl + Shift + &quot;-&quot; to increase or decrease
		// \n font size\n </center>\n </body>\n</html>");
		assertEquals(output[1],
				"> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n> add V0.44 today 8am tmr 7pm week\n> V0.44 has been added.\n> adder\n> Unrecognized command type\n> delete 10\n> Task cannot be deleted.\n> delete 1\n> a has been deleted.\n");
		Thread.sleep(500);

		userInput = "setdir mytextfile.txt";
		output = UserInterface.initComponents(userInput);
		assertEquals(output[1],
				"> setdir a.txt\n> Set directory successful.\n> add a\n> a has been added.\n> add V0.44 today 8am tmr 7pm week\n> V0.44 has been added.\n> adder\n> Unrecognized command type\n> delete 10\n> Task cannot be deleted.\n> delete 1\n> a has been deleted.\n> setdir mytextfile.txt\n> Set directory successful.\n");
		Thread.sleep(500);

		// userInput = "add V0.4";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "add V0.5 Manual date 11.04.2016 endtime 23:59";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "add V0.5 Demo date 13.04.2016 starttime 10";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "search v";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "search demo";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "add overdue task date 11.04.2015";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "display overdue";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "done 1";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "display done";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "display today";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "display";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "edit";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "edit 2 details wear smart casual";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "undo";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "help";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "alias add a";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "a check mail repeat day";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "setdir D:\\";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "get C:\\dirdefaultfile.txt";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);
		//
		// userInput = "ls";
		// output = UserInterface.initComponents(userInput);
		// // assertEquals(output[0],);
		// // assertEquals(output[1],);

	}
}