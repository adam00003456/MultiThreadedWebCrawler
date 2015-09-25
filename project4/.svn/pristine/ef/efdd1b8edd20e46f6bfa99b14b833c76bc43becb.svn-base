Project 4 Web Crawler
=================================================

For this project, you will extend your previous project to support building your index from the web instead of a directory of text files. You should not need to change your inverted index data structure or searching code for this project.

The input and output requirements of this project are identical to the previous project, except everywhere you had absolute file paths you will have absolute links.

## Functionality ##

For this project, your code must pass all of the previous project requirements and support the following additional functionality:

- Add support to build the inverted index from a seed URL instead of a directory. Specifically, build a web crawler that does the following:

  - Open a socket and download the webpage specified by the URL using HTTP.

  - For each link found on the downloaded web page, add a new job to a work queue to crawl that webpage. Track the number of URLs carefully to make sure you do not parse the same link twice and only parse up to a **maximum of 50 pages**.

  - Strip all of the HTML tags from the webpage (ideally line by line).

  - Parse the resulting text (ideally line by line) to populate the inverted index.

- The building of the index should still be multithreaded, whether from a directory or a seed URL. In the case of using a seed URL, each worker thread should parse a single webpage.

- The inverted index must still be thread-safe, and support placing URLs instead of file paths into the index.

- Your web crawler should support crawling relative links within a webpage, and should only consider the non-fragment portions of the URL when crawling.

- The partial search functionality should remain unchanged.

- Your program should still support building your index from a directory if provided.

- *Try not read in more of the webpage than necessary if possible. You will have multiple pages being parsed at once and a very large inverted index, so you should try to limit your memory usage. (Optional)*

Details regarding how to handle relative links and unique links for this project are below.

### Relative Links ###

The majority of links on webpages are relative (i.e. specified relative to the current webpage url). You will need to convert those relative links into an absolute link. For this, you may use the `java.net.URL` class. For example, consider the following:

```
URL base = new URL("http://www.cs.usfca.edu/~sjengle/cs212/");
URL absolute = new URL(base, "../index.html");

// outputs http://www.cs.usfca.edu/~sjengle/index.html
System.out.println(absolute);
```

You must still use sockets in your web crawler. Do NOT use the `getContent()` or `openConnection()` methods in the `URL` class.

### Unique Links ###

You should store a set of unique links to make sure you do not crawl the same link twice. The fragment portion should be disregarded in this comparison. For example, the link [`http://docs.python.org/2/library/string.html`](http://docs.python.org/2/library/string.html) is equivalent to the following:

```
http://docs.python.org/2/library/string.html#string.digits
http://docs.python.org/2/library/string.html#string.find
```

URLs that are not properly encoded further complicate the matter. The first link below is not encoded, but the second link is properly encoded:

```
http://foo.com/hello world/
http://foo.com/hello%20world/
```

The two links above lead to the same web page.

Thankfully, the `java.net.URI` will handle this encoding, and has methods that will allow you to ignore the fragment portions of URLs. You are allowed to use this class for that purpose.

## Execution ##

Your `main` method must be placed in a class named `Driver`. The `Driver` class should accept the following **additional** command-line arguments:

- `-u seed` where `-u` indicates the next argument is the seed URL that must be processed and added to an inverted index data structure.
  
The command-line flag/value pairs may be provided in any order. Your code should support all of the command-line arguments from the previous project as well.

The `Driver` class should be the only file that is not generalized and specific to the project. If the proper command-line arguments are not provided, the `Driver` class should output a user-friendly error message to the console and exit gracefully.

## Output ##

The output of your inverted index should be the same from the previous project. However, you should only generate output files if the necessary flags are provided.

## Hints ##

If you have been keeping up with homework assignments, you will have already created much of the code necessary for this project. This project will require you to use the concepts covered in the "Sockets and HTTP" and "Dynamic HTML" sections.

It is important to develop the project **iteratively** and to **test your code as you go**. The JUnit tests provided only test the entire project as a whole, not the individual parts. You are responsible for testing the individual parts themselves.

:bulb: These hints may or may _not_ be useful depending on your approach. Do not be overly concerned if you do not find these hints helpful for your approach for this project.

## Submission ##

You must commit your project to your SVN repository using Eclipse at:

```
https://www.cs.usfca.edu/svn/username/cs212/project4
```

where `username` should be replaced with your CS username. Verify that your repository includes the `src` directory with all of the `*.java` files necessary to compile your program.

:bulb: See the **[Project README](README.md)** for additional details on testing, submission, and code review.
