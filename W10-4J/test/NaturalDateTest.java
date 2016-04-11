//@@author A0140114A
package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Parser.NaturalDate;

public class NaturalDateTest {
	@Test
	public void testGetDate() {
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

		input = "mar 16";
		assertEquals(input, "2016/03/16", n.getDate(input));

		input = "12 mar";
		assertEquals(input, "2016/03/12", n.getDate(input));

		input = "12 mar b";
		assertEquals(input, null, n.getDate(input));

		for (int i = 1; i <= 28; i++) {
			for (int j = 1; j <= 12; j++) {
				input = String.format("%02d/%02d/%04d", i, j, 2016);
				assertEquals(input, String.format("%04d/%02d/%02d", 2016, j, i), n.getDate(input));
			}
		}
	}

	@Test
	public void testCharTypeSplit() {
		NaturalDate n = new NaturalDate();
		String input;

		input = "16jan2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.splitStringByCharType(input));

		input = "16 jan 2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.splitStringByCharType(input));

		input = "16  jan  2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.splitStringByCharType(input));

		input = "16 ? jan ? 2016";
		assertArrayEquals(input, new String[] { "16", "jan", "2016" }, n.splitStringByCharType(input));
	}
}
