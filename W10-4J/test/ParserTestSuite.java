
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NaturalLanguageTest.class, ParserTest.class, NaturalDateTest.class, NaturalTimeTest.class })
public class ParserTestSuite {

}
