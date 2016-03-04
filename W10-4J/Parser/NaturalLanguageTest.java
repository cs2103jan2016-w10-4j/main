package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class NaturalLanguageTest {

	@Test
	public void testGetDate() {
		NaturalLanguage n = new NaturalLanguage();
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
	}

	@Test
	public void testCharTypeSplit() {
		NaturalLanguage n = new NaturalLanguage();
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
	public void testGetTime(){
		NaturalLanguage n = new NaturalLanguage();
		String input;

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
