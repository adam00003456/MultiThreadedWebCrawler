import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ProjectTest.class, IndexTest.class, SearchTest.class,
	ThreadTest.class, ThreadStressTest.class})
public class Project3Test {
	/*
	 * To receive a 100% on this project, you must pass this test
	 * suite on the lab computers. However, you are eligible for
	 * code review if you pass everything EXCEPT ThreadStressTest.
	 * If you are not passing ThreadStressTest, go ahead and still
	 * sign up for code review.
	 *
	 * When testing your code on your personal computer or laptop,
	 * you may run just the ThreadTest suite of tests.
	 */
}
