CS 212 Projects
=================================================

This repository contains the project files for CS 212 Software Development at the University of San Francisco. This includes test files, input files, and expected output files.

## Project Specification ##

Please see the **[Project Specification](specifications/)** directory for project specifications, including details on how to verify and submit the projects.

## Project Setup ##

We recommend you checkout this repository as a new Java project in Eclipse for the first time you start working on a project. This will help you get all of the input and test files at once, with the proper subdirectory setup.

#### Retreive Files Via SVN ####

First, you must retrieve all of the necessary files for this project, including the input test files, the expected test output files, and the test source code.

1.  If you haven't already, add the following repository URL to your "SVN Repository Exploring" perspective in Eclipse:

    > https://github.com/cs212/projects/trunk

2.  In the "SVN Repository Exploring" perspective, right-click the project repository path and select "Checkout..." from the menu.

3.  Make sure "Check out as a new project configured using the New Project Wizard" option is selected, and then click the "Finish" button.

4.  On the next dialog window, select "Java Project" and click the "Next" button.

5.  Give your project a meaningful name, and click the "Finish" button.

At this point you have all the files, but will not be able to run the JUnit tests yet.

#### Fix Eclipse Project Setup ####

Next, you need to fix the build path for your Java project in Eclipse to run the JUnit tests.

1.  Right-click the root directory for your new Java project and select "Build Path" and "Add Libraries" from the menu.

2.  Select "JUnit" in the dialog window and click the "Next" button.

3.  Make sure "JUnit 4" is selected and click the "Finish" button.

4.  Right-click the `test` subdirectory and select "Build Path" then "Use as Source Folder".

5. Open one of the JUnit tests and run. Make sure the tests run (but will likely fail) before moving on.

At this point, you can run the tests, but not properly submit your project. 

#### Fix Eclipse SVN Setup ####

Finally, you must reconnect this Java project to your own SVN repository. 

1.  Right-click the root directory for your Java project, and select "Team" and "Disconnect" from the menu. 

    :warning: Make sure you select "Also delete the SVN meta information from the file system" from the window that appears and click the "Yes" button.

2.  Next, right-click the root directory again and select "Team" and "Share" from the menu. Select "SVN" on the dialog window and click the "Next" button. Select the SVN repository for your CS account.

3. When prompted, make sure you specify the correct folder name. If you are working on Project 1 the folder name should be `cs212/project1/` and for Project 2 the folder name should be `cs212/project2/` and so on.

4. Commit your project by right-clicking the root directory, and select "Team" and "Commit" from the menu. At this point, your project is all setup.

5. Double-check your submission by viewing to your SVN repository in a web browser. The URL will be:

    ```
    https://www.cs.usfca.edu/svn/sjengle/
    ```

    where `username` is your CS account username. You will be prompted to login with your CS account username and password. From there, you can click on the folders to make sure your setup looks correct.

At this point, you are all setup. You have all of the necessary test code, and your project is properly linked to your SVN repository.

## Updating Files ##

The project files may be periodically updated as the semester progresses. An announcement will be posted on the course website whenver these changes occur.

You must make sure you always have the latest version of these files when testing your project. You have several options for obtaining the latest files:

- You may download a zip file of this entire repository and replace your old files. The zip file is located at:

  > https://github.com/cs212/projects/archive/master.zip

- You may use `svn export` to export an individual subdirectory within this repository.

  ```
  svn export https://github.com/cs212/projects/trunk/subdirectory
  ```
  where `subdirectory` is the subdirectory to export. For example, the following will export just the files in the `specifications` subdirectory:

  ```
  svn export https://github.com/cs212/projects/trunk/specifications
  ```

- You may download a single small-sized file by viewing that file on GitHub and clicking the "Raw" button and saving the file through your browser. For larger files, you need to use the `svn export` approach above.

Once you have downloaded the latest version of the file(s), you can drag-and-drop the files into your Eclipse project.

Alternatively, you can use the steps illustrated by the [GitHub Help Pages](https://help.github.com/articles/downloading-files-from-the-command-line) for downloading files. 

:octocat: Of course, you can use `git` to update your files as well. However, you must use SVN for submission for the time being.
