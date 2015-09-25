import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CrawlTest.ExceptionTester.class, CrawlTest.LocalTester.class })
public class CrawlTest {

	public static final int TIMEOUT = 60000;
	public static final int THREADS = 5;

	/**
	 * This class tests whether {@link Driver} runs without generating an
	 * exception.
	 * 
	 * You can right-click this class to run just these set of tests!
	 */
	public static class ExceptionTester {

		@Test
		public void testOnlyURLFlag() {
			String[] args = new String[] { "-u" };
			ProjectTest.checkExceptions("Only URL Flag", args);
		}

		@Test
		public void testJustURL() {
			String[] args = new String[] { "-u",
					"http://www.cs.usfca.edu/~sjengle/cs212/crawl/birds.html" };
			ProjectTest.checkExceptions("Only URL", args);
		}

		@Test
		public void testBadURL() {
			String[] args = new String[] { "-u",
					"http://www.cs.usfca.edu/~sjengle/nowhere.html" };
			ProjectTest.checkExceptions("Invalid URL", args);
		}
	}

	/**
	 * This class tests whether {@link Driver} produces the correct inverted
	 * index and search result output for different LOCAL seed urls. Test these
	 * BEFORE running the external seeds to avoid spamming those web servers.
	 * 
	 * You can right-click this class to run just these set of tests!
	 */
	public static class LocalTester {

		@Test
		public void testIndexBirds() {
			String test = "birds";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/birds.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchBirds() {
			String test = "birds";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/birds.html";

			testCrawlSearchOutput(test, link);
		}

		@Test
		public void testIndexYellow() {
			String test = "yellow";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/yellowthroat.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchYellow() {
			String test = "yellow";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/yellowthroat.html";

			testCrawlSearchOutput(test, link);
		}

		@Test
		public void testIndexHolmes() {
			String test = "holmes";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/gutenberg/1661-h.htm";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchHolmes() {
			String test = "holmes";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/gutenberg/1661-h.htm";

			testCrawlSearchOutput(test, link);
		}

		@Test
		public void testIndexGuten() {
			String test = "gutenweb";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/gutenberg.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchGuten() {
			String test = "gutenweb";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/crawl/gutenberg.html";

			testCrawlSearchOutput(test, link);
		}

		@Test
		public void testIndexRecurse() {
			String test = "recurse";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/recurse/link01.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchRecurse() {
			String test = "recurse";
			String link = "http://www.cs.usfca.edu/~sjengle/cs212/recurse/link01.html";

			testCrawlSearchOutput(test, link);
		}
	}

	/**
	 * This class tests whether {@link Driver} produces the correct inverted
	 * index and search result output for different EXTERNAL seed urls. Test
	 * these AFTER running the local seeds to avoid spamming those web servers.
	 * 
	 * WARNING: This class is NOT run by default by this tester class. Instead,
	 * it is run by the {@link Project4} test suite. If you want to run these
	 * tests ONLY, please right-click the class and "Run-As" a Junit test.
	 */
	public static class ExternalTester {

		@Test
		public void testIndexHTMLHelp() {
			String test = "htmlhelp";
			String link = "http://htmlhelp.com/reference/html40/olist.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchHTMLHelp() {
			String test = "htmlhelp";
			String link = "http://htmlhelp.com/reference/html40/olist.html";

			testCrawlSearchOutput(test, link);
		}

		@Test
		public void testIndexLog4j() {
			String test = "log4j";
			String link = "http://logging.apache.org/log4j/1.2/apidocs/allclasses-noframe.html";

			testCrawlIndexOutput(test, link);
		}

		@Test
		public void testSearchLog4j() {
			String test = "log4j";
			String link = "http://logging.apache.org/log4j/1.2/apidocs/allclasses-noframe.html";

			testCrawlSearchOutput(test, link);
		}
	}

	private static void testCrawlIndexOutput(String test, String seed) {
		String base = ProjectTest.getBaseDirectory();

		String expect = "index-" + test + ".txt";
		String actual = "index-" + test + ".txt";

		Path output = Paths.get(base, "output", expect);
		Path result = Paths.get("result", actual);

		String[] args = new String[] { "-u", seed, "-i",
				result.toAbsolutePath().normalize().toString(), "-t",
				String.valueOf(THREADS) };
		// System.out.println("NUM OF THREADS");
		// System.out.println(args[5]);
		ProjectTest.checkProjectOutput("Index: " + test, args, result, output);
	}

	private static void testCrawlSearchOutput(String test, String seed) {
		String base = ProjectTest.getBaseDirectory();

		String expect = "search-" + test + ".txt";
		String actual = "search-" + test + ".txt";

		Path output = Paths.get(base, "output", expect);
		Path result = Paths.get("result", actual);

		Path query = Paths.get(base, "input", "queries", "complex.txt");

		String[] args = new String[] { "-u", seed, "-q",
				query.toAbsolutePath().normalize().toString(), "-r",
				result.toAbsolutePath().normalize().toString(), "-t",
				String.valueOf(THREADS) };

		ProjectTest.checkProjectOutput("Index: " + test, args, result, output);
	}

}