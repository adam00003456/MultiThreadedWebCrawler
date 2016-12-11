import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Go through the text files and store words with corresponding filename and
 * location.
 * 
 * @author Adam
 * 
 */

public class InvertedIndexBuilder {

	/**
	 * Method that will traverse through a directory, if it find a file, it will
	 * read the file line by line and parse it,formatting the words that will be
	 * added into the invertedIndex
	 * 
	 * @param index
	 * @param root
	 * @throws IOException
	 */
	public static void buildIndex(InvertedIndex index, Path root)
			throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		files = DirectoryTraverser.traverse(root);
		for (String file : files) {
			try (BufferedReader br = Files.newBufferedReader(Paths.get(file),
					Charset.forName("UTF-8"))) {
				String line;
				int indexnum = 0;
				while ((line = br.readLine()) != null) {
					line = WordParser.cleanText(line);
					String[] words = line.split("\\s");

					for (String word : words) {
						if (!word.isEmpty()) {
							index.add(word, file, (indexnum + 1));
							indexnum++;
						} else {
							continue;
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Sorry there is a problem with the file: "
						+ Paths.get(file).toString());
				throw e;
			}
		}
	}

	public static void webbuildIndex(InvertedIndex local, String stringurl,
			ArrayList<String> words) {
		for (String word : words) {
			int indexnum = 0;

			for (String wordinarray : words) {
				if (!word.isEmpty()) {
					local.add(wordinarray, stringurl, (indexnum + 1));
					indexnum++;
				} else {
					continue;
				}
				// }
			}
			break;

		}
	}
}