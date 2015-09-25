import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Traverses directories for text files.
 */
public class DirectoryTraverser {

	/**
	 * takes a path object as a parameter in order to form a new DirectoryStream
	 */

	/**
	 * The following method will return an list of filepathes.
	 * 
	 * 
	 * 
	 * @param String
	 *            object that will be used to traverse the directory and create
	 *            an arraylist of the filenames found
	 * @return the finishd arraylist full of strings
	 */
	public static ArrayList<String> traverse(Path path) {
		ArrayList<String> files = new ArrayList<>();
		traverse(path, files);
		return files;
	}

	public static ArrayList<String> traverse(Path path, ArrayList<String> files) {
		if (path.toString().toLowerCase().endsWith(".txt")) {
			files.add(path.toAbsolutePath().toString());
			return files;
		}
		try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
			for (Path file : listing) {
				if (file.toString().toLowerCase().endsWith(".txt")) {
					files.add(file.toAbsolutePath().toString());
				}

				if (Files.isDirectory(file)) {
					traverse(file, files);
				}
			}
		} catch (IOException e) {
			System.out.println("Bad Directory" + path);
		}

		return files;
	}

}
