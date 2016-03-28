
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ReadTest.class, SetDirectoryTest.class, WriteTest.class })
public class StorageTestSuite {

}
