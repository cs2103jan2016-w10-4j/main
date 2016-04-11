//@@author A0140114A
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import Parser.CommandList;
import Parser.NaturalLanguage;

public class NaturalLanguageTest {
	@Test
	public void testinterpretAddArguments() {
		NaturalLanguage p = new NaturalLanguage(CommandList.getInstance());
		String command;
		String[] commandSplit;

		command = "cs2103 10/4 1700 24 apr 1900 meet earlier";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));

		command = "cs2103 1700 10/4 1900 24 apr meet earlier";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));

		command = "cs2103 1700 10/4 meet earlier 1900 24 apr ";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));
	}

	@Test
	public void testinterpretEditArguments() {
		NaturalLanguage p = new NaturalLanguage(CommandList.getInstance());
		String command;
		String[] commandSplit;

		command = "cs2103 10/4 1700 24 apr 1900 meet earlier";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));

		command = "cs2103 1700 10/4 1900 24 apr meet earlier";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));

		command = "cs2103 1700 10/4 meet earlier 1900 24 apr ";
		commandSplit = command.split(" ");
		assertEquals(new String[] { "cs2103", "startdate", "2016/04/10", "enddate", "2016/04/24", "starttime", "17:00",
				"endtime", "19:00", "details", "meet earlier" }, p.interpretAddArguments(commandSplit));
	}
}