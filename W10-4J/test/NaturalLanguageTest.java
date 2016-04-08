//@@author A0140114A
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Parser.CommandList;
import Parser.NaturalDate;
import Parser.NaturalLanguage;
import Parser.NaturalTime;

public class NaturalLanguageTest {
	@Test
	public void testSpecial() {
		NaturalLanguage p = new NaturalLanguage(CommandList.getInstance());

		String command = "cs2103 tmr 1700 24 apr 1900 meet earlier";
		ArrayList<String> token = new ArrayList<>();
		String[] commandSplit = command.split(" ");
		for (String s : commandSplit) {
			token.add(s);
		}
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/09", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));
	}

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
	}

}