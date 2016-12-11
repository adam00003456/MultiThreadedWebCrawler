import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.Test;

public class ProjectTest {

	/**
	 * Directory of files on the lab computer. Do not modify this variable!
	 */
	public static final String CSLAB_DIR = "/home/public/cs212";

	/**
	 * Gets the base directory to use for testing purposes. Either the CS Lab
	 * directory or the current project directory.
	 * 
	 * @return path of base directory
	 */
	public static String getBaseDirectory() {
		String base = isLabComputer() ? CSLAB_DIR : ".";

		try {
			base = Paths.get(base).toRealPath().toString();
		} catch (Exception ex) {
			base = ".";
		}

		return base;
	}

	/**
	 * Tries to determine if the tests are being run on a lab computer by
	 * looking at the hostname and ip address.
	 * 
	 * @return true if it appears the tests are being run on a lab computer
	 */
	public static boolean isLabComputer() {
		boolean lab = false;

		try {
			Path base = Paths.get(CSLAB_DIR);
			String addr = InetAddress.getLocalHost().getHostAddress();
			String name = InetAddress.getLocalHost().getCanonicalHostName();

			if (Files.isReadable(base) && addr.startsWith("138.202.171.")
					&& name.endsWith(".cs.usfca.edu")) {
				lab = true;
			} else {
				lab = false;
			}
		} catch (Exception ex) {
			lab = false;
		}

		return lab;
	}

	/**
	 * Checks whether environment setup is correct, with a input and output
	 * directory located within the base directory.
	 */
	public static boolean isEnvironmentSetup() {
		String base = getBaseDirectory();
		Path input = Paths.get(base, "input");
		Path output = Paths.get(base, "output");
		Path result = Paths.get("result");

		try {
			Files.createDirectories(result);
		} catch (Exception e) {
			return false;
		}

		return Files.isReadable(input) && Files.isReadable(output);
	}

	/**
	 * Checks line-by-line if two files are equal. If one file contains extra
	 * blank lines at the end of the file, the two are still considered equal.
	 * 
	 * This test will pass even if the files are generated from different base
	 * directories, and hence have slightly different absolute paths.
	 * 
	 * @param path1
	 *            - path to first file to compare with
	 * @param path2
	 *            - path to second file to compare with
	 * @return positive value if two files are equal, negative value if not
	 * 
	 * @throws IOException
	 */
	public static int checkFiles(Path path1, Path path2) throws IOException {
		Charset charset = java.nio.charset.StandardCharsets.UTF_8;

		// used to output line mismatch
		int count = 0;

		try (BufferedReader reader1 = Files.newBufferedReader(path1, charset);
				BufferedReader reader2 = Files
						.newBufferedReader(path2, charset);) {
			String line1 = reader1.readLine();
			String line2 = reader2.readLine();

			// used to remove base directory from paths
			String pattern = Matcher.quoteReplacement(getBaseDirectory());

			while (true) {
				count++;

				// compare lines until we hit a null (i.e. end of file)
				if (line1 != null && line2 != null) {
					// remove trailing spaces
					line1 = line1.trim();
					line2 = line2.trim();

					// remove base directory if necessary
					if (!line1.equals(line2)) {
						// replace base directory with CS_LAB directory
						line1 = line1.replaceFirst(pattern, CSLAB_DIR);
						line2 = line2.replaceFirst(pattern, CSLAB_DIR);

						// use consistent path separators
						line1 = line1.replaceAll(
								Matcher.quoteReplacement(File.separator), "/");
						line2 = line2.replaceAll(
								Matcher.quoteReplacement(File.separator), "/");

						// now compare lines again
						if (!line1.equals(line2)) {
							return -count;
						}
					}

					// read next lines if we get this far
					line1 = reader1.readLine();
					line2 = reader2.readLine();
				} else {
					// discard extra blank lines at end of reader1
					while (line1 != null && line1.trim().isEmpty()) {
						line1 = reader1.readLine();
					}

					// discard extra blank lines at end of reader2
					while (line2 != null && line2.trim().isEmpty()) {
						line2 = reader2.readLine();
					}

					if (line1 == line2) {
						// only true if both are null, otherwise one file had
						// extra non-empty lines
						return count;
					} else {
						// extra blank lines found in one file
						return -count;
					}
				}
			}
		}
	}

	/**
	 * Checks whether {@link Driver} will run without generating any exceptions.
	 * Will print the stack trace if an exception occurs. Designed to be used
	 * within an unit test.
	 * 
	 * @param testName
	 *            - name of test for debugging
	 * @param args
	 *            - arguments to pass to {@link Driver}
	 */
	public static void checkExceptions(String testName, String[] args) {
		try {
			Driver.main(args);
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));

			Assert.fail(String.format("%n" + "Test Case: %s%n"
					+ "Exception: %s%n", testName, writer.toString()));
		}
	}

	/**
	 * Checks whether {@link Driver} generates the expected output without any
	 * exceptions. Will print the stack trace if an exception occurs. Designed
	 * to be used within an unit test.
	 * 
	 * @param testName
	 *            - name of test for debugging
	 * @param args
	 *            - arguments to pass to {@link Driver}
	 */
	public static void checkProjectOutput(String testName, String[] args,
			Path actual, Path expected) {
		try {
			// Remove actual result file if it already exists
			Files.deleteIfExists(actual);

			// Check if parent directories need to be created
			if (Files.notExists(actual.getParent())) {
				Files.createDirectories(actual.getParent());
			}
			Driver.main(args);

			int count = checkFiles(actual, expected);

			if (count <= 0) {
				Assert.fail(String.format("%n" + "Test Case: %s%n"
						+ " Mismatched Line: %d%n", testName, -count));
			}
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));

			Assert.fail(String.format("%n" + "Test Case: %s%n"
					+ "Exception: %s%n", testName, writer.toString()));
		}
	}

	/**
	 * This test will fail unless you are running from the lab computers.
	 */
	@Test
	public void testLabComputer() {
		try {
			// output system information for debugging purposes
			System.out.println("Host Address: "
					+ InetAddress.getLocalHost().getHostAddress());

			System.out.println("   Host Name: "
					+ InetAddress.getLocalHost().getCanonicalHostName());

			System.out.println("   Base Path: " + getBaseDirectory());
		} catch (Exception ex) {
			System.out.println("Unable to determine host information");
		}

		String errorMessage = String.format("%n" + "Test Case: %s%n" + "%s%n",
				"Lab Computer", "You must be testing from the CS lab "
						+ "computers to pass this test");

		Assert.assertTrue(errorMessage, isLabComputer());
	}

	/**
	 * Tests whether environment setup is correct.
	 */
	@Test
	public void testEnvironment() {
		String errorMessage = String.format("%n" + "Test Case: %s%n" + "%s%n",
				"Environment Setup",
				"Check that you have a readable input, result, and output "
						+ "directories in your base directory.");

		Assert.assertTrue(errorMessage, isEnvironmentSetup());
	}

	/**
	 * This test checks if Driver runs without exceptions when given no
	 * arguments.
	 */
	@Test
	public void testNoArguments() {
		checkExceptions("No Arguments", new String[] {});
	}

	/**
	 * This test checks if Driver runs without exceptions when given bad
	 * arguments.
	 */
	@Test
	public void testBadArguments() {
		checkExceptions("Bad Arguments", new String[] { "hello", "world" });
	}
}
