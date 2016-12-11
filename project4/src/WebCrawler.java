import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 * @see WebCrawler
 * @see HTMLCleanerTest
 */
public class WebCrawler {

	private static final Logger logger = LogManager.getLogger(WebCrawler.class.getName());
	private final WorkQueue workers;
	private int pending;
	private final MultiThreadedInvertedIndex multiinvertedIndex;
	private final int PORT = 80;
	private Set<String> urlchecker = new TreeSet<String>();
	private String urlstring = "";

	public WebCrawler(MultiThreadedInvertedIndex index, WorkQueue workers) {
		this.multiinvertedIndex = index;
		this.workers = workers;
		pending = 0;
		new MultiReaderLock();

	}

	/**
	 * Helper method, that helps a thread wait until all of the current work is
	 * done. This is useful for resetting the counters or shutting down the work
	 * queue.
	 */
	public synchronized void finish() {
		try {
			while (pending > 0) {
				this.wait();
			}
		} catch (InterruptedException e) {
			logger.debug("Finish interrupted", e);
		}
	}

	/**
	 * Will shutdown the work queue after all the current pending work is
	 * finished. Necessary to prevent our code from running forever in the
	 * background.
	 */
	public synchronized void shutdown() {
		logger.debug("Shutting down");
		finish();
		workers.shutdown();
	}

	/**
	 * Traverse the path given and given every .txt file, create a new worker
	 * thread that will go through the file and create a local invertedIndex
	 * structure.
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public void traverseurl(String urls) throws IOException {
		System.out.println("URLS    " + urls);
		urlstring = urls;

		workers.execute(new WebMinion(urls));
	}

	/**
	 * Handles per-directory parsing. If a subdirectory is encountered, a new
	 * {@link WebMinion} is created to handle that subdirectory.
	 */
	private class WebMinion implements Runnable {
		private URL url = null;
		private String stringurl = "";
		ArrayList<String> localurllist;
		InvertedIndex local = new InvertedIndex();

		public WebMinion(String urls) {

			urlchecker.add(urls);
			this.localurllist = new ArrayList<String>();
			logger.debug(urls);
			try {
				URL base = new URL(urlstring);
				URL absolute = new URL(base, urls);
				logger.debug("Absolute" + absolute);
				this.url = absolute;
				urls = "http://www.cs.usfca.edu/~sjengle/cs212/" + urls;
				this.stringurl = absolute.toString();
			} catch (MalformedURLException e) {
				logger.debug("Bad url.");

				// / URL u = new URL(FULL URL HERE, next url);
				// ex: FULL URL =
				// http://www.cs.usfca.edu/~sjengle/cs212/recurse/link01.html
				// next = link02.html
				// u =
				// https://www.cs.usfca.edu/~sjengle/cs212/recurse/link02.html
			}
			// populate the work queue with threads waiting to do work
			incrementPending();
		}

		@Override
		public void run() {
			StringBuilder html = new StringBuilder();
			System.out.println("RUN");
			try (Socket socket = new Socket(url.getHost(), PORT);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(
							socket.getOutputStream());

			) {
				HTMLFetcher fetcher = new HTMLFetcher(stringurl);
				String request = fetcher.craftRequest();
				System.out.println("HTTP: " + request);

				writer.println(request);
				writer.flush();

				String line = reader.readLine();
				while (!line.trim().isEmpty()) {
					line = reader.readLine();
				}
				line = reader.readLine();
				while ((line = reader.readLine()) != null) {
					html.append(line);
					html.append("\n");
				}
				String htmlstring = html.toString();
				logger.debug("CONTENT" + htmlstring);
				// at this point content is one full line
				// match url regex to the line to find all urls
				localurllist = HTMLLinkParser.listLinks(htmlstring);
				// arraylist now has all the urls in it from the webpage
				// content will be listed here and no html code
				line = HTMLCleaner.cleanHTML(htmlstring);
				line = line.toLowerCase().replaceAll("[\\W_]+", " ").trim();
				ArrayList<String> words = new ArrayList<String>();
				for (String word : line.split("\\W+")) {
					if (!word.isEmpty()) {
						words.add(word.trim());
					}
				}
				
				InvertedIndexBuilder.webbuildIndex(local, stringurl, words);
			
				// at the end go through the arraylist of links
				// and call a new thread to check that link
				// giant line read into it
				multiinvertedIndex.addAll(local);

				for (int i = 0; i < localurllist.size(); i++) {
					if (urlchecker.size() < 50
							&& (!urlchecker.contains(localurllist.get(i)))) {
						if (!localurllist.get(i).contains("#")) {
							workers.execute(new WebMinion(localurllist.get(i)));
						}
						urlchecker.add(localurllist.get(i));
					}
				}
				// AT THIS POINT THIS HAPPENED
			} catch (UnknownHostException e1) {
				System.out.println("Unknown Host");
			} catch (IOException e1) {
				System.out.println("Bad url is being used.");
			}

			decrementPending();
		}
	}

	/**
	 * Indicates that we now have additional "pending" work to wait for. We need
	 * this since we can no longer call join() on the threads. (The threads keep
	 * running forever in the background.)
	 * 
	 * We made this a synchronized method in the outer class, since locking on
	 * the "this" object within an inner class does not work.
	 */
	private synchronized void incrementPending() {
		pending++;
		logger.debug("Pending is incremented {}" +  pending);
	}

	/**
	 * Indicates that we now have one less "pending" work, and will notify any
	 * waiting threads if we no longer have any more pending work left.
	 */
	private synchronized void decrementPending() {
		pending--;
		logger.debug("Pending is decremented {}" +  pending);

		if (pending <= 0) {
			this.notifyAll();
		}
	}

	public static String fetchHTML(String urls) {
		HTMLFetcher hf = null;
		try {
			hf = new HTMLFetcher(urls);
			hf.fetch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("RESULT  " + hf.getter());
		return hf.getter();
	}

	/**
	 * Fetches the webpage at the provided URL, cleans up the HTML tags, and
	 * parses the resulting plain text into words.
	 * 
	 * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
	 * 
	 * @param url
	 *            - webpage to download
	 * @return list of parsed words
	 */
	public static ArrayList<String> fetchWords(String url) {
		String html = fetchHTML(url);
		//System.out.println("Result" + html);
		String text = cleanHTML(html);
		//System.out.println(text);
		return parseWords(text);
	}

	/**
	 * Parses the provided plain text (already cleaned of HTML tags) into
	 * individual words.
	 * 
	 * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
	 * 
	 * @param text
	 *            - plain text without html tags
	 * @return list of parsed words
	 */
	public static ArrayList<String> parseWords(String text) {
		ArrayList<String> words = new ArrayList<String>();

		for (String word : text.split("\\s+")) {
			word = word.toLowerCase().replaceAll("[\\W_]+", "").trim();

			if (!word.isEmpty()) {
				words.add(word);
			}
		}

		return words;
	}

	/**
	 * Removes all style and script tags (and any text in between those tags),
	 * all HTML tags, and all special characters/entities.
	 * 
	 * THIS METHOD IS PROVIDED FOR YOU. DO NOT MODIFY.
	 * 
	 * @param html
	 *            - html code to parse
	 * @return plain text
	 */
	public static String cleanHTML(String html) {
		String text = html;
		text = stripElement("script", text);
		text = stripElement("style", text);
		text = stripTags(text);
		text = stripEntities(text);
		System.out.println("TEXT" + text);
		return text;
	}

	/**
	 * Removes everything between the element tags, and the element tags
	 * themselves. For example, consider the html code:
	 * 
	 * <pre>
	 * &lt;style type="text/css"&gt;body { font-size: 10pt; }&lt;/style&gt;
	 * </pre>
	 * 
	 * If removing the "style" element, all of the above code will be removed,
	 * and replaced with the empty string.
	 * 
	 * @param name
	 *            - name of the element to strip, like style or script
	 * @param html
	 *            - html code to parse
	 * @return html code without the element specified
	 */
	public static String stripElement(String name, String html) {
		String text = "";
		Pattern p = Pattern.compile("<" + name + ".*?>(?s).*?</" + name + ">");
		Matcher m = p.matcher(html);
		while (m.find()) {
			text = m.replaceAll("");
		}

		return text;

	}

	/**
	 * Removes all HTML tags, which is essentially anything between the < and >
	 * symbols. The tag will be replaced by the empty string.
	 * 
	 * @param html
	 *            - html code to parse
	 * @return text without any html tags
	 */
	//GOOD
	public static String stripTags(String html) {
		// <[^;]*?>
		html = html.replaceAll("(?s)(<.+?>)", "");
		if (html.contains("DOCTYPE")) {
			System.out.println("HEY GIRL");
		}

		return html;
	}

	/**
	 * Replaces all HTML entities in the text with the empty string. For
	 * example, "2010&ndash;2012" will become "20102012".
	 * 
	 * @param html
	 *            - the text with html code being checked
	 * @return text with HTML entities replaced by a space
	 */
	public static String stripEntities(String html) {
		html = html.replaceAll("(&[^;]*;)", "");
		return html;
	}
}
