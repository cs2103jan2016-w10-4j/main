
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddTest.class, DeleteTest.class, DoneTest.class, EditTest.class, UndoTest.class })
public class HandlerTestSuite {

}
