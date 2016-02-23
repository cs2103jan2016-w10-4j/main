package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class NaturalDateTest {

	@Test
	public void test() {
		NaturalDate n = new NaturalDate();
		String input;

		input = "16jan2016";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "2016 january 16";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "16 JANUARY 2016";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "16 1 2016";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "16/01/16";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "16/16/01";
		assertEquals(input, "2016/01/16", n.getDate(input));

		input = "16/16/01";
		assertEquals(input, "2016/01/16", n.getDate(input));
		
		input = "23.2.2016";
		assertEquals(input, "2016/02/23", n.getDate(input));
		
		input = "23feb2016";
		assertEquals(input, "2016/02/23", n.getDate(input));
		
		input = "16 jan 17";
		assertEquals(input, "2017/01/16", n.getDate(input));
		
		input = "jan 16";
		assertEquals(input, "2016/01/16", n.getDate(input));
		
		input = "16 jan";
		assertEquals(input, "2016/01/16", n.getDate(input));
	}

	@Test
	public void testCharTypeSplit() {
		NaturalDate n = new NaturalDate();
		String input;

		input = "16jan2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.charTypeSplit(input));

		input = "16 jan 2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.charTypeSplit(input));

		input = "16  jan  2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.charTypeSplit(input));

		input = "16 ? jan ? 2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.charTypeSplit(input));
	}

}
