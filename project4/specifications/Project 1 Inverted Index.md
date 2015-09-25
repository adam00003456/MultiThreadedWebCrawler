Project 1 Inverted Index
=================================================

For this project, you will write a Java program that recursively processes all text files in a directory and builds an inverted index to store the mapping from words to the documents (and position within those documents) where those words were found. For example, suppose we have the following mapping stored in our inverted index:

```
capybara
"mammals.txt", 11

platypus
"mammals.txt", 3, 8
"venomous.txt", 2
```

This indicates that the word `capybara` is found in the file `mammals.txt` in position 11. The word `platypus` is found in two files, `mammals.txt` and `venomous.txt`. In the file `mammals.txt`, the word `platypus` appears twice in positions 3 and 8. In file `venomous.txt`, the word `platypus` is in position 2 in the file.

## Functionality ##

For this project, your code must traverse a directory and process all text files found in that directory (including all subdirectories). For each text file, you must parse each line into words. For each word, you must store a mapping of the word to the file and position that word was found in a custom inverted index data structure.

Specifically, the core functionality of your project must satisfy the following requirements:

- Create an custom **inverted index** data structure that stores a mapping from a word to the file(s) the word was found, and the position(s) in that file the word is located. *This will require nesting multiple built-in data structures.*

- Store the normalized absolute path for file locations as a `String` object.

- Positions stored in your inverted index should start at 1. For example, if a file has words "ant bat cat", then "ant" is in position 1, "bat" is in position 2, and "cat" is in position 3.

- Traverse a directory and its subdirectories and parse all of the text files found. Any file ending in the `.txt` extension (case-insensitive) should be considered a text file.

- Use the `UTF-8` character encoding when parsing the provided test files.

- Process each line from a text file into words. Separate words by any whitespace character, including spaces, tabs, and newline characters.

- Words must be case-insensitive. For example, the words APPLE, Apple, apple, and aPpLe should all be seen as the same word.

- Replace all characters except letters and digits with a space. This includes the `_` underscore character as well. For example, the word `age_long` should be seen as `age long` since the `_` underscore will be replaced with a ` ` space.

- Process command-line parameters to determine the directory to parse and output to produce. See the **Execution** section below for specifics.

- Output the inverted index to a text file if the proper command-line parameters are provided. See the **Output** section below for specifics.

You must satisfy the core functionality prior to submitting your project for code review. See the **[Project README](README.md)** for specifics.

## Design ##

In addition to the design requirements outlined in the **Project Submission** guide, you should consider the following when designing your project:

- Your program should support large text files. As a result, you should **not** read the entire file into memory at once. Instead, read a single line from the file into memory at a time.

- Your program should be object-oriented. For example, you should separate directory traversing, file parsing, and data maintenance into different classes.

- You program should protect the integrity of your inverted index, making sure all private data members are properly encapsulated. For example, do not return a reference to a private data member in a public method.

These requirements are **not** necessary to be eligible for code review. You should only worry about these requirements once your core functionality is complete.

## Execution ##

Your `main` method must be placed in a class named `Driver`. The `Driver` class should accept the following command-line arguments:

- `-d directory` where `-d` indicates the next argument is a directory, and `directory` is the directory of text files that must be processed

- `-i indexpath` where `-i` is an *optional* flag that indicates the next argument is a file path, and `indexpath` is the path to the file to use for the inverted index output file. If the `indexpath` argument is not provided, use `index.txt` as the default output filename. If the `-i` flag is not provided, do not produce an output file.

The `Driver` class should be the only file that is not generalized and specific to the project. If the proper command-line arguments are not provided, the `Driver` class should output a user-friendly error message to the console and exit gracefully.

## Output ##

The output of your program should be a file that contains the contents of your inverted index in alphabetically sorted order using the following output format:

```
capybara
"/absolute/path/to/mammals.txt", 11

platypus
"/absolute/path/to/mammals.txt", 3, 8
"/absolute/path/to/venomous.txt", 2
```

The word is listed alone on a single line, followed by lines with the absolute file path, and a comma-separated list of locations. An empty line should separate entries. The words should be output in sorted order, and the files should be sorted by the absolute path name.

## Hints ##

It is important to develop the project **iteratively**. In fact, you may already have certain components complete thanks to the homework assignments. One possible breakdown of tasks are:

- Create code that handles parsing command-line arguments, so that you can easily retreive the directory containing the text files to parse. Test your code.

- Create code that is able to traverse a directory and return a list of all the text files found within that directory. Test your code.

- Create code that handles parsing a single text file into words. Test your code.

- Create code that handles storing a word, file path, and location into an inverted index data structure. Test your code.

- Integrate your code. 
 
    - Make your code that parses a text file into words store words in an inverted index data structure.
    
    - Make your code that traverses a directory work with the code that parses a text file and stores words into an inverted index data structure.
    
    - Make your code that parses command-line arugments work with the code that traverses a directory, and so on.

The important part will be to **test your code as you go**. The JUnit tests provided only test the entire project as a whole, not the individual parts. You are responsible for testing the individual parts themselves.

:bulb: These hints may or may _not_ be useful depending on your approach. Do not be overly concerned if you do not find these hints helpful for your approach for this project.

## Submission ##

You must commit your project to your SVN repository using Eclipse at:

```
https://www.cs.usfca.edu/svn/username/cs212/project1
```

where `username` should be replaced with your CS username. Verify that your repository includes the `src` directory with all of the `*.java` files necessary to compile your program.

:bulb: See the **[Project README](README.md)** for additional details on testing, submission, and code review.


