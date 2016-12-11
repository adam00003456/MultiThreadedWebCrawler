import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains several methods for parsing text into words. Assumes words are
 * separated by whitespace.
 * 
 */
public class WordParser {

	/**
	 * Converts text into a consistent format by converting text to lower- case,
	 * replacing non-word characters and underscores with a single space, and
	 * finally removing leading and trailing whitespace. (See the {@link String}
	 * class for several helpful methods.)
	 * 
	 * @param text
	 *            - original text
	 * @return text without special characters and leading or trailing spaces
	 */
	public static String cleanText(String text) {
		String text1;
		text1 = text.toLowerCase();
		text1 = text1.replaceAll("\\W", " ");
		text1 = text1.replaceAll("\\_", " ");
		text1 = text1.replaceAll("\\s+", " ");
		text1 = text1.trim();
		return text1;
	}

	/**
	 * Splits text into words by whitespaces, cleans the resulting words using
	 * {@link #cleanText(String)} so that they are in a consistent format, and
	 * adds non-empty words to an {@link ArrayList}.
	 * 
	 * <p>
	 * <em>
	 * You must use the {@link #cleanText(String)} method and an enhanced
	 * for loop to receive full credit for this method.
	 * </em>
	 * </p>
	 * 
	 * @param text
	 *            - original text
	 * @return list of cleaned words
	 */
	public static List<String> parseText(String text) {
		text = cleanText(text);
		if (text.length() == 0) {
			return new ArrayList<String>();
		}

		String[] words = text.split("\\s");

		ArrayList<String> myList = new ArrayList<String>();

		Collections.addAll(myList, words);

		return myList;

	}

	/**
	 * Reads a file line-by-line and parses the resulting line into words using
	 * the {@link #parseText(String)} method. Adds the parsed words to a master
	 * list of words, which is returned at the end.
	 * 
	 * <p>
	 * <em>
	 * You must use the {@link #parseText(String)}, a try-with-resources
	 * block, and read and store only one line at a time to receive full
	 * credit for this method.
	 * </em>
	 * </p>
	 * 
	 * @param path
	 *            - file path to open
	 * @return list of cleaned words
	 * @throws IOException
	 */
	public static List<String> parseFile(Path path) throws IOException {
		List<String> myList;
		List<String> masterList = new ArrayList<String>();
		try (BufferedReader br = Files.newBufferedReader(path,
				Charset.forName("UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				myList = parseText(line);
				for (String element : myList) {
					if (!element.isEmpty()) {
						masterList.add(element);
					} else {
						continue;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Sorry there is a problem with the file:"
					+ path.toString());
			throw e;
		}

		return masterList;
	}

}