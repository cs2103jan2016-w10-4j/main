//@@author A0140114A
package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Parser.NaturalTime;

public class NaturalTimeTest {

	@Test
	public void testGetTime() {
		NaturalTime n = new NaturalTime();
		String input;

		input = "code";
		assertEquals(input, null, n.getTime(input));

		input = "7:30";
		assertEquals(input, "07:30", n.getTime(input));

		input = "07:30";
		assertEquals(input, "07:30", n.getTime(input));

		input = "7";
		assertEquals(input, "07:00", n.getTime(input));

		input = "07";
		assertEquals(input, "07:00", n.getTime(input));

		input = "730";
		assertEquals(input, "07:30", n.getTime(input));

		input = "0730";
		assertEquals(input, "07:30", n.getTime(input));

		input = "1230";
		assertEquals(input, "12:30", n.getTime(input));

		input = "123";
		assertEquals(input, "01:23", n.getTime(input));
	}
}
