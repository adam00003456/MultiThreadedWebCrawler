import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;



/**
 * 
 * @see WebCrawler
 * @see HTMLCleanerTest
 */
public class WebCrawlPartialSearch {
	private final static Logger logger = LogManager.getLogger(WebCrawlPartialSearch.class.getName());
	private static LinkedHashMap<String, ArrayList<SearchResult>> querysearchMap;
	private static MultiThreadedInvertedIndex mi = new MultiThreadedInvertedIndex();
	private static HashMap<String, Timestamp> cookielist = new HashMap<String, Timestamp>();
	private static String temp = "";
	static Timestamp currentTimestamp;
	static long elapsedTime;
	static int numberofsearches = 0;

	/**
	 * Constructor
	 */
	public WebCrawlPartialSearch(MultiThreadedInvertedIndex multiindex) {
		WebCrawlPartialSearch.mi = multiindex;
		//CONTAINS ALL THE SEARCH RESULTS AND SEARCH WORDS
		WebCrawlPartialSearch.querysearchMap = new LinkedHashMap<String, ArrayList<SearchResult>>();

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
	public void multiinvertedindexsetter(MultiThreadedInvertedIndex multiindex) {
		WebCrawlPartialSearch.mi = multiindex;
	}

	public void protinit(int PORT) {
		Server server = new Server(PORT);
		// Setup the handler component

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(new ServletHolder(new HeaderServlet()),
				"/home");
		handler.addServletWithMapping(new ServletHolder(
				new ResultsServlet()), "/results");
		handler.addServletWithMapping(new ServletHolder(
				new ResultsWipeServlet()), "/config");
		server.setHandler(handler);

		// Start the server (it is a thread) and wait for it to complete
		try {
			server.start();
		} catch (Exception e) {
			System.out.println("The server had problems starting.");
			System.out.println(e.getMessage());
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			System.out.println("The server had problems joining.");
		}
	}

	public static String multiBuildIndex(String querywordline)
			throws IOException {
		logger.debug("1beforeInvertedIndex" +  mi);
		ArrayList<SearchResult> qw;
		List<String> myname;
		myname = WordParser.parseText(querywordline);
		System.out.println("MYNAME  " + myname.get(0));
		qw = mi.searchlister(myname);
		querysearchMap.put(querywordline, qw);
		temp = querywordline;
		logger.debug("1afterInvertedIndex" +  mi);
		querywordline = HTMLprinter(querysearchMap, querywordline);
		return querywordline;
	}

	public String multiintializer(String querywordline) throws IOException {
		querysearchMap.put(querywordline, null);
		return querywordline;
	}

	public LinkedHashMap<String, ArrayList<SearchResult>> getresults() {
		return WebCrawlPartialSearch.querysearchMap;
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
				System.out.println("Word  " + word);
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

	public static class HeaderServlet extends HttpServlet {
		/**
		 * Displays a form where users can enter a URL to check. When the button
		 * is pressed, submits the URL back to /home as a GET request.
		 * 
		 * If a URL was included as a parameter in the GET request, fetch and
		 * display the HTTP headers of that URL.
		 */
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,
				IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n",
					"Welcome to the Matrix");
			out.println("<body style='background-color:#0C090A'>");
			out.println("<div style='position: absolute; z-index:"
					+ " -99; width: 100%; height: 100%'>");
			out.println("<iframe title= \"Youtube Video Player\" width=\"100%\" "
					+ "height=\"100%\" src=\"//www.youtube.com/embed/kqUR3KtWbTk?version=3&autoplay=1"
					+ "&loop=1\""
					+ "&playlist=kqUR3KtWbTk\""
					+"&allowfullscreen></iframe>");
			out.println("</div>");
			printForm(request, response);
			if (request.getParameter("queryword") != null) {
				String queryword = request.getParameter("queryword");
				if (queryword.equals("HungryHamster")) {
					out.println("<iframe title= \"Youtube Video Player\" width=\"560\" "
							+ "height=\"315\" src=\"//www.youtube.com/embed/JOCtdw9FG-s\""
							+ " frameborder=\"0\" allowfullscreen></iframe>");
				}

				long startTime = System.currentTimeMillis();
				out.printf("<pre><font color='white'><font size='6'>Results \n"
				+ multiBuildIndex(queryword) + "</font></font></pre>");
				currentTimestamp = new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime());
				cookielist.put(temp, currentTimestamp);
				long elapsedTime = System.currentTimeMillis() - startTime;
				out.printf("<pre><font color='green'><font size='10'>Elapsed Time:  " + elapsedTime + "</font></font></pre>");
				out.printf("<pre><font color='green'><font size='10'>Number of searches:  "  + numberofsearches + "</font></font></pre>");
			}

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);

		}
	}

	public static String HTMLprinter(
		LinkedHashMap<String, ArrayList<SearchResult>> map, String queryword) {
		numberofsearches++;
		StringBuilder headers = new StringBuilder();
			headers.append("SearchWord: " + queryword);
			headers.append("\n");
			for (SearchResult searchResult: map.get(queryword)) {
				headers.append(searchResult.toString());
				headers.append("\n");
			}
			headers.append("\n");
		
		System.out.println("Number of results  " + numberofsearches);
		System.out.println("The time it took:  " + elapsedTime);
		System.out.println("The results are coming from HTMLprinter from PartialSearch");
		System.out.println("RESULTS  " + headers);
		return headers.toString();
	}

	private static void printForm(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.printf("<form method=\"get\", action=\"%s\">%n",
				request.getServletPath());
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><center>&nbsp;</center></h2>%n");
		out.printf("<h2><font color='green'><center><font style ='font-size:100px'; font face='verdana'>Enter the Matrix!</font></center></font>"
				+ "</h2>%n%n");
		out.printf("<h2><center><input type=\"text\" name=\"queryword\" maxlength=\"100\" size=\"100\"></center>"
				+ "</h2>%n");
		out.printf("<p><center>"
				+ "<input style='color:green' type=\"submit\" value=\"Get the Queryword!\"</center>>"
				+ "</p>\n%n");
		out.printf("<h2><center><font style ='font-size:25px'; font face='verdana'>"
				+ "<a style='color:green' href='http://localhost:8080/results'>View Results</a>"
				+ "</font></center></font>"
				+ "</h2>");
		out.printf("<h2><center><font style ='font-size:25px'; font face='verdana'>"
				+ "<a style='color:green' href='http://localhost:8080/config'>Delete Results</a>"
				+ "</font></center></font>"
				+ "</h2>");
		out.printf("</form>\n%n");
	}

	public static class ResultsServlet extends HttpServlet {

		
		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,
				IOException {

			//log.info("GET " + request.getRequestURL().toString());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.printf("<p>");

			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			out.println("<body style='background-color:#0C090A'>");
			out.printf("<p style='color:green'>");
			for (String word : cookielist.keySet()) {
				out.printf("<pre><font color='green'><font size='10'>" + word + "    " + cookielist.get(word) +  "</font></font></pre>");
				out.append("\n");
			}
			out.printf("</p>");
			response.setStatus(HttpServletResponse.SC_OK);

		}
	}
		
		public static class ResultsWipeServlet extends HttpServlet {
			@Override
			protected void doGet(HttpServletRequest request,
					HttpServletResponse response) throws ServletException,
					IOException {

				
				//prepareResponse("Configure", response);
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter out = response.getWriter();
				out.println("<body style='background-color:#0C090A'>");
				out.printf("<p style='color:green'>Cookies are cleared.</p>%n");
				out.printf("%n");
				cookielist.clear();
				//finishResponse(request, response);
				
			}
		
		}
	

	
	
	
}
