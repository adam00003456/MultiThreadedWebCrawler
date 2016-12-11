import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class QueryParser {
	private static final Logger logger = LogManager.getLogger(QueryParser.class.getName());
	private MultiReaderLock queryLock = new MultiReaderLock();
	private LinkedHashMap<String, ArrayList<SearchResult>> querysearchMap;

	/**
	 * Constructor
	 */
	public QueryParser() {
		this.querysearchMap = new LinkedHashMap<>();

	}

	/**
	 * Read through the querywords file line by line and put the words into a
	 * LinkedHashMap data structure if the queryword is not already stored as a
	 * key value within it.
	 * 
	 * @param invertedIndex
	 * @param string
	 * @throws IOException
	 */
	public void parser(InvertedIndex invertedIndex, String string)
			throws IOException {
		ArrayList<SearchResult> qw = new ArrayList<SearchResult>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(string),
				Charset.forName("UTF-8"))) {
			List<String> myList;
			String line;
			while ((line = br.readLine()) != null) {
				myList = WordParser.parseText(line);
				qw = invertedIndex.search(myList);
				this.querysearchMap.put(line, qw);
			}
		}
	}

	public void multiBuildIndex(MultiThreadedInvertedIndex multiindex,
			String querywordline) throws IOException {
		logger.debug("1beforeInvertedIndex" + multiindex);
		queryLock.lockWrite();
		ArrayList<SearchResult> qw = new ArrayList<SearchResult>();
		List<String> myname;
		myname = WordParser.parseText(querywordline);
		qw = multiindex.search(myname);
		this.querysearchMap.put(querywordline, qw);
		queryLock.unlockWrite();
		logger.debug("1afterInvertedIndex" + multiindex);
	}

	public void multiintializer(String querywordline) throws IOException {
		querysearchMap.put(querywordline, null);
	}

	/**
	 * Get the filename from argmap following the -r tag and output the contents
	 * of the LinkedHashMap structure.
	 * 
	 * @param filename
	 * @return
	 */
	public String searchoutput(String filename) {
		Path newFile = Paths.get(filename);
		try (BufferedWriter writer = Files.newBufferedWriter(newFile,
				Charset.forName("UTF-8"))) {
			for (String word : querysearchMap.keySet()) {
				writer.append(word);
				writer.newLine();
				for (SearchResult qq : querysearchMap.get(word)) {
					String str = qq.toString();
					writer.append(str);
					writer.newLine();
				}
				writer.newLine();

			}
			writer.flush();
			return filename;

		} catch (IOException e) {
			System.out.println("Bad file   " + filename);
		}
		return filename;
	}
}
