import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SearchTest.SearchArgumentTest.class,
	SearchTest.SearchOutputTest.class
})
public class SearchTest {

	public static class SearchArgumentTest {

		@Test
		public void testMissingQueryPath() {
			String base = ProjectTest.getBaseDirectory();
			Path input  = Paths.get(base, "input", "index", "simple");

			String[] args = {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q"
			};

			ProjectTest.checkExceptions("Missing Query Path", args);
		}

		@Test
		public void testBadQueryPath() {
			String base = ProjectTest.getBaseDirectory();
			String name = Long.toHexString(Double.doubleToLongBits(Math.random()));

			Path input = Paths.get(base, "input", "index", "simple");
			Path query = Paths.get(name);

			String[] args = {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkExceptions("Bad Query Path", args);
		}

		@Test
		public void testDefaultOutput() throws IOException {
			String base = ProjectTest.getBaseDirectory();
			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");
			Path result = Paths.get("results.txt");

			String[] args = {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r"
			};

			Files.deleteIfExists(result);
			ProjectTest.checkExceptions("Default Output", args);

			String errorMessage = String.format(
					"%n" + "Test Case: %s%n" + "%s%n",
					"Default Output",
					"Output to results.txt if output path not provided");

			Assert.assertTrue(errorMessage, Files.isReadable(result));
		}

		/**
		 * Tests that no output file is generated when the output flag is not
		 * present. Might also fail if {@link Files#deleteIfExists(Path)}
		 * throws an exception.
		 *
		 * @throws IOException if unable to delete results.txt file if it exists
		 */
		@Test
		public void testNoOutput() throws IOException {
			String base = ProjectTest.getBaseDirectory();
			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");
			Path result = Paths.get("results.txt");

			String[] args = {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString()
			};

			Files.deleteIfExists(result);
			ProjectTest.checkExceptions("No Output", args);

			String errorMessage = String.format(
					"%n" + "Test Case: %s%n" + "%s%n",
					"No Output",
					"Do not create results.txt unless proper flag provided");

			Assert.assertFalse(errorMessage, Files.isReadable(result));
		}

		/**
		 * Tests whether program fails gracefully when attempt to write to
		 * a directory instead of a file path.
		 */
		@Test
		public void testNoWriteDirectory() {
			String base = ProjectTest.getBaseDirectory();
			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");

			try {
				Path path = Files.createTempDirectory(Paths.get(".").normalize(), "temp");
				path.toFile().deleteOnExit();

				String[] args = new String[] {
						"-d", input.toAbsolutePath().toString(),
						"-q", query.toAbsolutePath().toString(),
						"-r", path.toAbsolutePath().toString()};

				Driver.main(args);
			}
			catch (Exception e) {
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));

				Assert.fail(String.format(
						"%n" + "Test Case: %s%n" + "Exception: %s%n",
						"No Write Directory", writer.toString()));
			}
		}
	}

	public static class SearchOutputTest {

		@Test
		public void testSearchSimple() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-simple-simple.txt";

			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Simple/Simple", args, result, output);
		}

		@Test
		public void testSearchSimpleReversed() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-simple-reversed.txt";

			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-r", result.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-d", input.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Simple/Simple Reversed", args, result, output);
		}

		@Test
		public void testSearchSimpleAlphabet() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-simple-alpha.txt";

			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "alphabet.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Simple/Alpha", args, result, output);
		}

		@Test
		public void testSearchSimpleComplex() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-simple-complex.txt";

			Path input  = Paths.get(base, "input", "index", "simple");
			Path query  = Paths.get(base, "input", "queries", "complex.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Simple/Complex", args, result, output);
		}

		@Test
		public void testSearchIndexSimple() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-index-simple.txt";

			Path input  = Paths.get(base, "input", "index");
			Path query  = Paths.get(base, "input", "queries", "simple.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Index/Simple", args, result, output);
		}

		@Test
		public void testSearchIndexAlphabet() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-index-alpha.txt";

			Path input  = Paths.get(base, "input", "index");
			Path query  = Paths.get(base, "input", "queries", "alphabet.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Index/Alpha", args, result, output);
		}

		@Test
		public void testSearchIndexComplex() {
			String base = ProjectTest.getBaseDirectory();
			String name = "search-index-complex.txt";

			Path input  = Paths.get(base, "input", "index");
			Path query  = Paths.get(base, "input", "queries", "complex.txt");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] {
					"-d", input.toAbsolutePath().normalize().toString(),
					"-q", query.toAbsolutePath().normalize().toString(),
					"-r", result.toAbsolutePath().normalize().toString()
			};

			ProjectTest.checkProjectOutput("Search Index/Complex", args, result, output);
		}
	}
}
