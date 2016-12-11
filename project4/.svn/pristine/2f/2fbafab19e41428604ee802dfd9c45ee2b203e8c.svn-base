Project Specifications
=================================================

See the following links for the specification document for each of the projects required by this course.

- [Project 1 Inverted Index](Project 1 Inverted Index.md)
- [Project 2 Partial Search](Project 2 Partial Search.md)
- [Project 3 Multithreading](Project 3 Multithreading.md)
- [Project 4 Web Crawler](Project 4 Web Crawler.md)
- Project 5 Search Engine

See below for additional details regarding how to test and submit projects.

## Setup ##

See the [Project Setup README](../README.md) for details on how to setup Eclipse, your SVN repository, and the provided JUnit system tests. This guide assumes you already have your Eclipse project properly setup.

## Testing ##

You should perform your own testing while you develop the project. The test code provided only tests your final result, and is not suitable for testing your project during development. 

## Verification ##

You may not sign up for code review until you are passing all of the provided JUnit tests for your project from the lab computers.

We have provided a script on the lab computers to help verify your project is properly submitted and passing the necessary tests. This script will checkout your project from your SVN repository into a temporary directory, compile your Java source code, copy fresh unaltered JUnit tests into the temporary directory, run the JUnit tests, and remove the temporary directory when complete.

To run this script, login to a lab computer (locally or remotely) and run the following command. See the [CS Tutoring Center Resources](http://tutoringcenter.cs.usfca.edu/resources/) if you are unfamiliar with how to login to a lab computer remotely.

```
/home/public/cs212/test/verify username #
```

You must replace `username` with your CS username and `#` with the number of the project you wish you verify. For example, the following command will verify Project 1 for the user `sjengle`:

```
/home/public/cs212/test/verify sjengle 1
```

If the script states "Your project is ready for code review" then everything passed and you may sign up code review.

If your project does not pass verification, there is either an issue with your submission or with your project functionality. You must address these issues before signing up for code review. Note that the `verify` script is not meant for debugging!

:bulb: **If you sign up for code review before verifying your project using the steps above, your project score will be docked 5 points.**

## Code Review ##

Once you have verified your project is properly submitted and passing the necessary JUnit tests, you may sign up for code review.

1. Fill out the [**Project Submission Form**](https://docs.google.com/a/cs.usfca.edu/forms/d/1LlC6LnH0_xGULGlnDC7LqIL-0zjlwrXvaLs6V5Ueqqw/viewform) indicating you want to submit your project for code review. This form is used to track your submissions for each project. If you fill out this form before your project passes verification or multiple times for a single review request, you risk having your project score docked 5 points per erroneous submission.

  > [**:memo: Submit Project Now**](https://docs.google.com/a/cs.usfca.edu/forms/d/1LlC6LnH0_xGULGlnDC7LqIL-0zjlwrXvaLs6V5Ueqqw/viewform)

2. Wait for the instructor or teacher assistant to respond with additional instructions. You should receive a response within 1 or 2 days of submission. During this time, we confirm you are submitting the correct project (i.e. are not submitting project 2 before you passed project 1) and run the `verify` command.

  - For interactive code review, you will be sent a Google Calendar link that you may use to signup for a code review appointment. Please double-check the time zone is set to Pacific Standard Time. Otherwise, the times may not be displayed properly.

  - For offline code review, you will be sent an email confirmation that the instructor will inspect your code offline. Offline code reviews must be pre-approved by the instructor. Please note that it may take up to 1 week for offline code reviews.

During the code review, the instructor will discuss your code design with you and make suggestions for improvement. Any discussion from the code review will be embedded in your code within `// TODO` comments and committed to your repository.

After the review, your project will be given assigned a `PASS`, `WARN`, or `FAIL` status:

- The `PASS` status signifies you passed the code review and may move on to the next project. Congratulations!

- The `WARN` status signifies you passed the code review and may move on to the next project, but must address some lingering issues (given in `// TODO` comments) within the next project. If you fail to address those issues, your project score will be docked 5 points.

- The `FAIL` status signifies you did not pass the code review. Do not fret! This is expected to happen at least once per project. You need to address all of the `// TODO` comments in your code and resubmit your project. You may remove the `// TODO` comments after you have addressed them. (We have a record of the comments in your SVN history.)

Each code review is 20 minutes. If we do not have a chance to review your entire project within this time frame, it is likely you will need another code review. As such, make sure you address any easy fixes such as proper formatting and commenting prior to the code reveiw to avoid wasting time.

To resubmit your project, you must re-verify it is passing the necessary JUnit tests and follow the same submission process above. Frivolous resubmissions will result in a 5 point deduction per submission. For example, if you submit poorly formatted code twice in a row without fixing it, you will be docked 5 points. For the most part, however, must resubmissions are free.

:bulb: **Everyone is expected to resubmit at least once per project. The code review and resubmission process is meant to help you learn how to improve the design of your code!**

## Code Design ##

Exactly what we cover in each code review will depend on your approach and the project in question. However, to be considered complete, every project must eventually satisfy the following design requirements:

- Your program should conform to [Java Code Conventions](http://www.oracle.com/technetwork/java/codeconv-138413.html) for naming, formatting, and code style. Slight deviations from these conventions are allowable as long as they are consistent.

- Your program should use [Javadoc](http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html) comments for all classes and methods. The comments should be *meaningful* and provide context for how to use the class or method. See the [Java 7 API](http://docs.oracle.com/javase/7/docs/api/) for example comments.

- You must create object-oriented code that is **generalized** and **encapsulated**. This means any reusable code has been separated into its own class (and reused where possible in your project), and that your code protects the data integrity of all private data members.

- You must perform proper error and exception handling, and display a user-friendly error message when issues occur. The user should never see the Java exception stack trace.

- Your code must be reasonably efficient (both from the execution time and memory usage perspectives). For example, avoid looping through data more times than necessary, and read and process files one line at a time when possible.

You should not be overly concerned about these design requirements prior to your code review. The code review is meant to help you understand proper code design, and is most meaningful when we are able to discuss your mistakes and how to improve them.

## Benchmark ##

Some projects may also require you to benchmark your project. This involves running your project code multiple times on a single test case, and averaging the execution time over all runs.

To run the benchmark script, login to a lab computer (locally or remotely) and run the following command:

```
/home/public/cs212/test/benchmark username #
```

Please note that this script is running your code on a large test case multiple times, and may take a long time to complete. You do not want to run this command on non-functional code.

:bulb: **The benchmark command is optional for most projects, but you are encouraged to run it anway and see how your times stack up with the rest of class!**

## Workflow ##

The suggested workflow for each project is as follows:

1. Setup your Eclipse project and SVN repository, complete with a `Driver` class, all provided input files, output files, and JUnit test code. See the **[Project Setup README](../README.md)** for details.

2. Start working on the core functionality until you pass the provided JUnit tests. Do not be overly concerned with design at this stage.

3. After passing all of the provided JUnit tests, clean up the formatting and style of your code. Make sure all classes and methods are properly commented using [Javadoc](http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html) notation.

4. Commit the latest version of your fully functional project to your SVN repository.

5. Log on to one of the lab computers (locally or remotely) and run the `verify` command. See the **[Verification](#verification)** section for details.

6. If the `verify` command indicates your code is ready for review, sign up for a code review appointment with the instructor. See the **[Code Review](#code-review)** section for details.

7. Refactor your project following the code review to improve your project design. Make sure you re-verify your code is passing the provided JUnit tests using the steps above before resubmitting your project and signing up for another code review.

It is important that you seek help if you get stuck. We are here to help you! Post a question on Piazza, stop by office hours for the instructor or teacher assistant, and seek help from the [CS Tutoring Center](http://tutoringcenter.cs.usfca.edu/). Sometimes all it takes is a fresh perspective.

:bulb: **We _will_ look at your code and help you even if your project is not ready for code review!**

