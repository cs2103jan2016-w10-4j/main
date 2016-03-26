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
	@Test
	public void testIsDisplay(){
		String displayString = "0This is display";
		String nonDisplayString = "1This is not display";
		String falseDisplayString = "This is a false display string. Return false.";
		
		assertTrue(UIController.isDisplay(displayString));
		assertFalse(UIController.isDisplay(nonDisplayString));
		assertFalse(UIController.isDisplay(falseDisplayString));
	}
	
	public void testIsPageUpTrue(){
		int notTrueValue = -1;
		int trueValue = 1;
		
		/* When using PgUp, the minimum scroll is 0. Therefore, pageUpTrue returns true only if the integer passed in is >= 0.*/
		assertTrue(UIController.pageUpTrue(trueValue));
		assertFalse(UIController.pageUpTrue(notTrueValue));
	}
}
