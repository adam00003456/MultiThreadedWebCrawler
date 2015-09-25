import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IndexTest.IndexArgumentTest.class,
		IndexTest.IndexOutputTest.class })
public class IndexTest {

	public static class IndexArgumentTest {

		@Test
		public void testNoDirectory() {
			Path output = Paths.get("result", "index-nodir.txt");
			String[] args = { "-i",
					output.toAbsolutePath().normalize().toString() };
			ProjectTest.checkExceptions("No Directory", args);
		}

		@Test
		public void testBadDirectory() {
			String name = Long.toHexString(Double.doubleToLongBits(Math
					.random()));

			Path input = Paths.get(name);
			Path output = Paths.get("result", "index-baddir.txt");

			String[] args = { "-d",
					input.toAbsolutePath().normalize().toString(), "-i",
					output.toAbsolutePath().normalize().toString() };

			ProjectTest.checkExceptions("Bad Directory", args);
		}

		@Test
		public void testDefaultOutput() throws IOException {
			String base = ProjectTest.getBaseDirectory();
			Path input = Paths.get(base, "input", "index", "simple");
			Path output = Paths.get("index.txt");

			String[] args = { "-d",
					input.toAbsolutePath().normalize().toString(), "-i" };

			Files.deleteIfExists(output);
			ProjectTest.checkExceptions("Default Output", args);

			String errorMessage = String.format("%n" + "Test Case: %s%n"
					+ "%s%n", "Default Output",
					"Output to index.txt if output path not provided");

			Assert.assertTrue(errorMessage, Files.isReadable(output));
		}

		@Test
		public void testNoOutput() throws IOException {
			String base = ProjectTest.getBaseDirectory();
			Path input = Paths.get(base, "input", "index", "simple");
			Path output = Paths.get("index.txt");

			String[] args = { "-d",
					input.toAbsolutePath().normalize().toString() };

			Files.deleteIfExists(output);
			ProjectTest.checkExceptions("No Output", args);

			String errorMessage = String.format("%n" + "Test Case: %s%n"
					+ "%s%n", "No Output",
					"Do not create index.txt unless proper flag provided");

			Assert.assertFalse(errorMessage, Files.isReadable(output));
		}
	}

	public static class IndexOutputTest {

		@Test
		public void testIndexSimple() {
			String base = ProjectTest.getBaseDirectory();
			String name = "index-simple.txt";

			Path input = Paths.get(base, "input", "index", "simple");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] { "-d",
					input.toAbsolutePath().normalize().toString(), "-i",
					result.toAbsolutePath().normalize().toString() };

			ProjectTest
					.checkProjectOutput("Index Simple", args, result, output);
		}

		@Test
		public void testIndexSimpleReversed() {
			String base = ProjectTest.getBaseDirectory();
			String name = "index-simple-reversed.txt";

			Path input = Paths.get(base, "input", "index", "simple");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] { "-i",
					result.toAbsolutePath().normalize().toString(), "-d",
					input.toAbsolutePath().normalize().toString() };

			ProjectTest.checkProjectOutput("Index Simple Reversed", args,
					result, output);
		}

		@Test
		public void testIndexRFCs() {
			String base = ProjectTest.getBaseDirectory();
			String name = "index-rfcs.txt";

			Path input = Paths.get(base, "input", "index", "rfcs");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] { "-d",
					input.toAbsolutePath().normalize().toString(), "-i",
					result.toAbsolutePath().normalize().toString() };

			ProjectTest.checkProjectOutput("Index RFCs", args, result, output);
		}

		@Test
		public void testIndexGutenberg() {
			String base = ProjectTest.getBaseDirectory();
			String name = "index-guten.txt";

			Path input = Paths.get(base, "input", "index", "gutenberg");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] { "-d",
					input.toAbsolutePath().normalize().toString(), "-i",
					result.toAbsolutePath().normalize().toString() };

			ProjectTest.checkProjectOutput("Index Gutenberg", args, result,
					output);
		}

		@Test
		public void testIndexAll() {
			String base = ProjectTest.getBaseDirectory();
			String name = "index-all.txt";

			Path input = Paths.get(base, "input", "index");
			Path output = Paths.get(base, "output", name);
			Path result = Paths.get("result", name);

			String[] args = new String[] { "-d",
					input.toAbsolutePath().normalize().toString(), "-i",
					result.toAbsolutePath().normalize().toString() };

			ProjectTest.checkProjectOutput("Index All", args, result, output);
		}
	}
}