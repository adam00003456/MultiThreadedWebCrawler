import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Driver {

	/**
	 * argmap object will store all the arguments that are read in from the
	 * command line
	 * 
	 * invertedIndex object will structure itself to have string words as flags
	 * and treemaps as values
	 * 
	 * files arraylist object will be an arraylist object full of strings which
	 * will be of filenames
	 */
	public static final Logger logger = LogManager.getLogger(Driver.class.getName());

	public static void main(String[] args) throws IOException {
		ArgumentParser argmap = new ArgumentParser(args);
		QueryParser q = new QueryParser();
		InvertedIndex invertedIndex = new InvertedIndex();
		if ((!argmap.hasFlag("-d") && !argmap.hasFlag("-u"))) {
			logger.debug("put(): buffer now has {} elements.");
			logger.debug("Sorry but you did not input a -d flag.");
			return;
		}

		if ((!argmap.hasFlag("-d") && !argmap.hasFlag("-i") && !argmap.hasFlag("-u"))
				&& (!argmap.hasFlag("-q") && (!argmap.hasFlag("-r")))) {
			logger.debug("Sorry but you have entered the wrong commands, exiting will commence now.");
			return;
		}
		/**
		 * driver class will take in an array of string arguments
		 */

		if (argmap.hasFlag("-t") == false) {
			if ((argmap.hasFlag("-d") || argmap.hasFlag("-i"))
					&& (!argmap.hasFlag("-q") && (!argmap.hasFlag("-r")))) {
				if (argmap.hasValue("-d")) {
					try {
						InvertedIndexBuilder.buildIndex(invertedIndex,
								Paths.get(argmap.get("-d")));
					} catch (IOException e) {
						logger.debug("No directory");
					}
				}

				if (argmap.hasFlag("-i")) {
					if (argmap.hasValue("-i")) {
						invertedIndex.output(argmap.get("-i"));
					} else {
						invertedIndex.output("index.txt");
					}

				}
			}
			if ((argmap.hasFlag("-d") || argmap.hasFlag("-i"))
					&& (argmap.hasFlag("-q") || argmap.hasFlag("-r"))) {
				if (argmap.hasValue("-d")) {
					try {
						InvertedIndexBuilder.buildIndex(invertedIndex,
								Paths.get(argmap.get("-d")));
					} catch (IOException e) {
						logger.debug("No directory");
					}
				}

				if (argmap.hasFlag("-i")) {
					if (argmap.hasValue("-i")) {
						invertedIndex.output(argmap.get("-i"));
					} else {
						invertedIndex.output("index.txt");
					}

				}
				if (argmap.get("-q") != null) {
					if (argmap.get("-q").endsWith(".txt")) {
						q.parser(invertedIndex, argmap.get("-q"));
					}
				}

				if (argmap.get("-r") != null) {
					q.searchoutput(argmap.get("-r"));
				} else if (argmap.hasFlag("-r") && argmap.get("-r") == null) {
					q.searchoutput("results.txt");
				}
			}
		} else {
			if (argmap.hasFlag("-t")) {
				int threadamount;
				try {
					threadamount = Integer.parseInt(argmap.getValue("-t"));
				} catch (NumberFormatException e) {
					threadamount = 5;
				}

				MultiThreadedInvertedIndex index = new MultiThreadedInvertedIndex();
				if (threadamount > 0) {
					WorkQueue workers = new WorkQueue(threadamount);
					MultiThreadedInvertedIndexBuilder traversor = new MultiThreadedInvertedIndexBuilder(
							index, workers);
					MultiThreadedPartialSearch queryindex = new MultiThreadedPartialSearch(
							index, workers);
					if (argmap.hasFlag("-u")) {
						System.out.println("I GOT HIT");
						WebCrawler webcrawl = new WebCrawler(index, workers);
						String url = argmap.getValue("-u");
						URL urls = new URL(url);
						System.out.println(argmap);
						System.out.println("URL    " + urls);
						webcrawl.traverseurl(urls.toString());
						webcrawl.shutdown();
					}
					if (argmap.hasFlag("-d")) {
						String directory = argmap.getValue("-d");
						Path path = Paths.get(directory);
						traversor.traverse(path);
						traversor.shutdown();

					}
					if (argmap.hasFlag("-i")) {
						if (argmap.hasValue("-i")) {
							index.output(argmap.get("-i"));
						} else {
							index.output("index.txt");
						}

					}
					if (argmap.get("-q") != null) {
						workers = new WorkQueue(threadamount);
						if (argmap.get("-q").endsWith(".txt")) {
							queryindex = new MultiThreadedPartialSearch(index,
									workers);
							queryindex.MultiThreadedSearch(index,
									argmap.get("-q"));
							queryindex.shutdown();
						}
					}
					if (argmap.get("-r") != null) {
						System.out.println("R GETS HIT");
						queryindex.searchoutput(argmap.get("-r"));
					} else if (argmap.hasFlag("-r") && argmap.get("-r") == null) {
						queryindex.searchoutput("results.txt");
					}
					if (argmap.get("-p") !=null) {
						// Setup the handler component
						WebCrawlPartialSearch webcrawl = new WebCrawlPartialSearch(index);
						webcrawl.protinit(Integer.parseInt(argmap.get("-p")));
						
						
					}
				}

			}
		}
	}
}
