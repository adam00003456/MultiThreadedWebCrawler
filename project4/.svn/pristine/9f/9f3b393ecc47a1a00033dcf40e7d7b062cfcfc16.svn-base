Project 2 Partial Search
=================================================

For this project, you will extend your previous project to support partial search. In addition to meeting the previous project requirements, your code must return a *sorted* list of results from your inverted index that start with the provided query word(s).

For example, suppose your inverted index contains the words: `after`, `apple`, `application`, and `happen`. If the query word is `app`, your code should return results for both `apple` and `application` but *not* `happen`.

Your search results must be sorted such that the most relevant search result is listed first, and the least relevant search result is listed last. You will determine relevancy based on both the frequency of the query word and position.

## Functionality ##

For this project, your code must pass all of the previous project requirements and support the following additional functionality:

- Process additional command-line parameters to determine the file to parse for queries. See the **Execution** section below for specifics.

- Efficiently return partial search results from your inverted index, such that any word in your inverted index that *starts with* a query word is returned.

- Sort the search results using a simple metric for relevancy (see details below).

- Support multiple word search queries, where search results from individual query words are combined together. For example, a file should only appear *once* in the search results even if it contains multiple words from a query.

- Output the sorted search results to a text file if the proper command-line parameters are provided. See the **Output** section below for specifics.

More details are provided next for how to read and parse queries and sort the search results.

### Query File Parsing ###

Queries will be provided in a text file. The input query file will consist of one multi-word search query per line. The query file may contain symbols and mixed-case words. You will need to perform the necessary transformations to match the queries with your inverted index. A sample query file may look like:

```
1918
a-coming
you
irrelphant
WH
```

There are five different search queries in this example. The first query `1918` consists of only one search word. The second query `a-coming` should have the special character replaced to form a two-word query `a` and `coming`. The last query `WH` should be made all lowercase as the query `wh` instead.

### Result Sorting ###

To rank your search results, use the following criteria:

- **Frequency:** Locations where the query word(s) are more frequent should be ranked above others. For example, suppose `a.txt` has 5 partial matches to the query and `b.txt` has 10 partial matches. In that case, `b.txt` should appear in the results before `a.txt`.

- **Position:** For locations that have the same frequency of query word(s), locations where the words appear in earlier positions should be ranked above others. For example, suppose `a.txt` and `b.txt` both have the same number of partial matches. If `a.txt` has the first partial match in position 10 and `b.txt` has the first partial match in position 5, then `b.txt` should appear in the results before `a.txt`.

- **Location:** For locations that have the same frequency and position, the results should be sorted by path in case-insensitive order. For example, suppose `a.txt` and `b.txt` both have the same number of partial matches and positions. If `a.txt` and `b.txt` are in the same directory, then `a.txt` should appear in the results before `b.txt`.

You can use the [`String.CASE_INSENSITIVE_ORDER`](http://docs.oracle.com/javase/7/docs/api/java/lang/String.html#CASE_INSENSITIVE_ORDER) comparator for comparing absolute paths and [`Integer.compare(int, int)`](http://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#compare(int, int)) or [`Integer.compareTo(Integer)`](http://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#compareTo(java.lang.Integer)) to compare frequencies and positions.

### Multiple Word Queries ###

For multiple-word queries, you should add the count for each word together and use the earliest position any of the query words appear. 

For example, suppose the query words are `apple banana`. Let `file1.txt` have 15 results for `apple` and let `file2.txt` have 5 results for `apple` and 5 results for `banana`. Then, `file1.txt` will be ranked higher than `file2.txt` since the total count of 15 for `file1.txt` is greater than that of 10 for `file2.txt` (even though `file2.txt` has both of the search terms). If `apple` first appears in position 3 in `file2.txt` and `banana` first appears in position 14, then 3 should be used as the position for sorting.

## Design ##

In addition to the design requirements outlined in the **Project Submission** guide, you should consider the following when designing your project:

- Your program should support large text files. As a result, you should **not** read the entire file into memory at once. Instead, read a single line from the file into memory at a time.

- Your program should be object-oriented. For example, you should separate directory traversing, file parsing, and data maintenance into different classes.

- You program should protect the integrity of your inverted index, making sure all private data members are properly encapsulated. For example, do not return a reference to a private data member in a public method.

These requirements are **not** necessary to be eligible for code review. You should only worry about these requirements once your core functionality is complete.

## Execution ##

Your `main` method must be placed in a class named `Driver`. The `Driver` class should accept the following **additional** command-line arguments:

- `-q query` where `-q` indicates the next argument is a path to a text file of queries. If this flag is not provided, then no search should be performed. In this case, your code should behave exactly the same as the previous project.

- `-r resultpath` where `-r` is an *optional* flag that indicates the next argument is a file path, and `resultpath` is the path to the file to use for the search results output file. If the `resultpath` argument is not provided, use `results.txt` as the default output filename. If the `-r` flag is not provided, do not produce an output file of search results.

The command-line flag/value pairs may be provided in any order. Your code should support all of the command-line arguments from the previous project as well.

The `Driver` class should be the only file that is not generalized and specific to the project. If the proper command-line arguments are not provided, the `Driver` class should output a user-friendly error message to the console and exit gracefully.

### Execution Examples ###

The following are a few examples (non-comprehensive) to illustrate the usage of the command-line arguments. Consider the following example:

```
java Driver -d /home/public/cs212/input/index
            -q /home/public/cs212/input/search/queries-simple.txt
            -i index-all.txt
            -r results-index-simple.txt
```

In this case your program should build an inverted index from all of the files in the `index` directory. It should then build search results for all the queries located in the file `queries-simple.txt`. The inverted index should be output to the file `index-all.txt` in the current working directory, and the search results should be output to a file `results-index-simple.txt`.

Next, consider the following example:

```
java Driver -d /home/public/cs212/input/index
            -q /home/public/cs212/input/search/queries-simple.txt
            -r
```

Your program should execute the same as before, except that it will not output the inverted index to file, and will output the search results to the file `results.txt`. Note that in this case, you must still build the inverted index even though you do not output it to a file.

Next, consider the following example:

```
java Driver -d /home/public/cs212/input/index
            -q /home/public/cs212/input/search/queries-simple.txt
```

In this case, your program still executes as before but produces no output files. In general, if the flags `-i` or `-r` are not provided, then your program should not produce any output files. **Your code must still, however, build the inverted index and perform the partial search!** This is useful later for testing the efficiency of your code.

## Output ##

The output of your inverted index should be the same from the previous project. The output of your search results should use the following format:

```
1918
"/home/public/cs212/input/index/simple/pg22014c.txt", 1, 51
"/home/public/cs212/input/index/simple/pg22014b.txt", 1, 62
"/home/public/cs212/input/index/simple/pg22014a.txt", 1, 93

a-coming
"/home/public/cs212/input/index/simple/pg22014a.txt", 1, 36

you
"/home/public/cs212/input/index/simple/pg22014c.txt", 2, 11

irrelphant

WH
"/home/public/cs212/input/index/simple/pg22014a.txt", 3, 13
"/home/public/cs212/input/index/simple/pg22014b.txt", 1, 5
```

The **original** query (including all words if it is a multiple word query) is listed alone on a single line, followed by lines with the search results. Each search result is the absolute path in quotes, followed by the frequency the query word(s) appear in that file, followed by the first position the query word(s) appear. An empty line should separate entries. In the example above, the word `irrelphant` has no search results, but should still be included in the output.

Note that while the search results for any query are sorted, the queries out output in the same order they were read from the original query file.

## Hints ##

It is important to develop the project **iteratively**. One possible breakdown of tasks are:

- Create a class that stores a single search result and implements the [`Comparable`](http://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html) interface. You will need data members for each of the sort criteria (frequency, initial position, and path).

- Add a partial search method to your inverted index data structure that takes already parsed words from a single query, and returns a sorted list of search results. 

- Use lists and [`Collections.sort(List)`](http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List)) to sort search results, not a [`TreeSet`] data structure. *Custom mutable objects do not behave well when used in sets or as keys in maps.*

- Create a query helper class, that is able to parse the query file into lines and lines into either an array or list of cleaned words. For each query parsed, use the search method for your inverted index to get the results and save them to be output to a file later.

- Do not worry about efficient partial search until *after* you are getting correct results.

These code demos may be useful for this project:

- [`SortDemo.java`](https://github.com/cs212/demos/blob/master/Inheritance/src/SortDemo.java)
- [`FindDemo.java`](https://github.com/cs212/demos/blob/master/Data%20Structures/src/FindDemo.java)

The important part will be to **test your code as you go**. The JUnit tests provided only test the entire project as a whole, not the individual parts. You are responsible for testing the individual parts themselves.

:bulb: These hints may or may _not_ be useful depending on your approach. Do not be overly concerned if you do not find these hints helpful for your approach for this project.

## Submission ##

You must commit your project to your SVN repository using Eclipse at:

```
https://www.cs.usfca.edu/svn/username/cs212/project2
```

where `username` should be replaced with your CS username. Verify that your repository includes the `src` directory with all of the `*.java` files necessary to compile your program.

:bulb: See the **[Project README](README.md)** for additional details on testing, submission, and code review.
