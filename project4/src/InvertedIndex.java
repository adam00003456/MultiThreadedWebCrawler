import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * this class will make an InvertedIndex object that will use an overall TreeMap
 * data structure with String objects as keys and nested TreeMap values, these
 * nested TreeMaps will have String keys (filenames) and ArrayLists (full of
 * integer objects) as inner values for those treemaps
 */
public class InvertedIndex {

	private final TreeMap<String, TreeMap<String, ArrayList<Integer>>> invertedIndex;

	public InvertedIndex() {
		this.invertedIndex = new TreeMap<>();

	}

	/**
	 * Method will take in an arraylist of filenames and go through each text
	 * file
	 * 
	 * @param an
	 *            array of string objects named files
	 * 
	 */

	public void add(String word, String path, int position) {
		word = word.toLowerCase();
		word = word.trim();
		if (!invertedIndex.containsKey(word)) {
			TreeMap<String, ArrayList<Integer>> filename = new TreeMap<>();
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(position);
			filename.put(path, list);
			invertedIndex.put(word, filename);
		} else {
			if (invertedIndex.get(word).get(path) != null) {
				invertedIndex.get(word).get(path).add(position);
			} else {
				TreeMap<String, ArrayList<Integer>> wordz = new TreeMap<>();
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(position);
				wordz.put(path, list);
				invertedIndex.get(word).put(path, list);
			}
		}

	}

	public ArrayList<SearchResult> search(List<String> QueryWords) {
		ArrayList<SearchResult> querywords = new ArrayList<SearchResult>();
		HashMap<String, SearchResult> resultmap = new HashMap<String, SearchResult>();
		for (String queryword : QueryWords) {
			for (String key = invertedIndex.ceilingKey(queryword); key != null; key = invertedIndex
					.higherKey(key)) {
				if (key.startsWith(queryword)) {
					for (String filename : invertedIndex.get(key).keySet()) {
						if (resultmap.containsKey(filename) == false) {
							resultmap.put(filename,
									new SearchResult(filename, invertedIndex
											.get(key).get(filename).size(),
											invertedIndex.get(key)
													.get(filename).get(0)));
						} else {
							resultmap.get(filename)
									.update(invertedIndex.get(key)
											.get(filename).size(),
											invertedIndex.get(key)
													.get(filename).get(0));
						}

					}
				} else {
					break;
				}
			}

		}

		querywords = new ArrayList<SearchResult>(resultmap.values());
		Collections.sort(querywords);
		return querywords;
	}

	public void addAll(InvertedIndex ii) {
		for (String key : ii.invertedIndex.keySet()) {
			if (this.invertedIndex.containsKey(key)) {
				for (String filename : ii.invertedIndex.get(key).keySet()) {
					if (this.invertedIndex.get(key).containsKey(filename)) {
						this.invertedIndex
								.get(key)
								.get(filename)
								.addAll(ii.invertedIndex.get(key).get(filename));
					} else {
						this.invertedIndex.get(key).put(filename,
								ii.invertedIndex.get(key).get(filename));
					}
				}

			} else {
				this.invertedIndex.put(key, ii.invertedIndex.get(key));

			}
		}
	}

	/**
	 * 
	 * Method will take in a string filename object and turn it into a file in
	 * which, the invertedIndex object can have all of it's contents written out
	 * to file.
	 * 
	 * @param string
	 *            key object
	 * @return null if there is no file to have it's results written to when
	 *         this method is called
	 */

	public void output(String filename) {
		Path newFile = Paths.get(filename);
		try (BufferedWriter writer = Files.newBufferedWriter(newFile,
				Charset.forName("UTF-8"))) {

			for (String word : invertedIndex.keySet()) {
				writer.append(word);
				writer.newLine();
				for (String str : invertedIndex.get(word).keySet()) {
					writer.append("\"" + str + "\"");
					for (Object i : invertedIndex.get(word).get(str)) {
						writer.append(", " + i);
					}
					writer.newLine();
				}
				writer.newLine();
				writer.flush();
			}
		} catch (IOException e) {
			System.out.println("Bad file: " + filename);
		}
	}

	public void debug() {
		System.out.println(invertedIndex.keySet());
	}

	public void debugger() {
		System.out.println(invertedIndex.values());
	}
	

}
