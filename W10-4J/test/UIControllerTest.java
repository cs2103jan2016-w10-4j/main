/*
 * JUnit test for UIController class.
 * 
 * @@author A0113761M
 */
package test;

import static org.junit.Assert.*;
import org.junit.Test;

import UserInterface.UIController;

public class UIControllerTest {
	String displayString = "0This is display";
	String cmdDisplayString = "1This is not display";
	String invalidMessageString = "2This is an invalid message, e.g Invalid command entered";
	String falseDisplayString = "This is a false display string. Return false.";
	
	@Test
	public void testIsDisplay(){
		assertTrue(UIController.isDisplay(displayString));
		assertFalse(UIController.isDisplay(cmdDisplayString));
		assertFalse(UIController.isDisplay(invalidMessageString));
		assertFalse(UIController.isDisplay(falseDisplayString));
	}
	
	@Test
	public void testIsCmdDisplay(){
		assertFalse(UIController.isCmdDisplay(displayString));
		assertTrue(UIController.isCmdDisplay(cmdDisplayString));
		assertFalse(UIController.isCmdDisplay(invalidMessageString));
		assertFalse(UIController.isCmdDisplay(falseDisplayString));
	}
	
	@Test
	public void testIsInvalidMessage(){
		assertFalse(UIController.isInvalidMessages(displayString));
		assertFalse(UIController.isInvalidMessages(cmdDisplayString));
		assertTrue(UIController.isInvalidMessages(invalidMessageString));
		assertFalse(UIController.isInvalidMessages(falseDisplayString));
	}
	
	public void testIsPageUpTrue(){
		int notTrueValue = -1;
		int trueValue = 1;
		
		/* When using PgUp, the minimum scroll is 0. Therefore, pageUpTrue returns true only if the integer passed in is >= 0.*/
		assertTrue(UIController.pageUpTrue(trueValue));
		assertFalse(UIController.pageUpTrue(notTrueValue));
	}
}
