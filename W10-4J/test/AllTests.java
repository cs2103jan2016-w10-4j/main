//@@author Berkin
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HandlerTestSuite.class, ParserTestSuite.class, StorageTestSuite.class, UITestSuite.class })
public class AllTests {

}
//@@author 