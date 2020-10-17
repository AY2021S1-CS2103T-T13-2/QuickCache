---
layout: page
title: User Guide
---

QuickCache is a **desktop app for managing flashcards, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, QuickCache can get your flashcard management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `quickcache.jar` from [here](https://github.com/AY2021S1-CS2103T-T13-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your QuickCache.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:
   
   * **`list`** : Lists all FlashCards.
   
   * **`open`**`1` : Opens the 1st question shown in the current list.

   * **`add`**`q/Plants give out ___ when they photosynthesise? ans/Oxygen t/Biology` :  Adds a open ended question `Plants give out ___ when they photosynthesise?` with answer `Oxygen` and tagged to `Biology`. 
   
   * **`addmcq`**`q/Plants give out ___ when they photosynthesise? ans/1 c/Oxygen c/Carbon c/Carbon dioxide` :  Adds a multiple choice question `Plants give out ___ when they photosynthesise?` with 3 options `Oxygen`, `Carbon`, `Carbon dioxide` and with answer `Oxygen`.
   
   * **`test`**`1 ans/Example answer` : Tests the 1st question shown in the current list with `Example answer` as the answer.
   
   * **`find`**`Biology` : Finds all Flashcards tagged to the tag `Biology`.
   
   * **`stats`**`1` : Show stats of the 1st question shown in the current list.
   
   * **`delete`**`3` : Deletes the 3rd flashcard shown in the current list.

   * **`clear`** : Deletes all FlashCards.

   * **`clearstats`**`1` : Clears the statistics of the 1st flashcard shown in the current list.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/ANSWER`, `ANSWER` is a parameter which can be used as `add n/Oxygen`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/QUESTION p/ANSWER`, `p/ANSWER n/QUESTION` is also acceptable.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Open a flashcard: `open`

Opens a specified flashcard from the list .

Format: `open INDEX`

* Opens the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `open 2` opens the 2nd flashcard in the list.

### Adding a flashcard with open ended question: `add`

Adds a new flashcard to the application.

Format: `add q/QUESTION ans/ANSWER`

Examples:
* `add q/Plants give out ___ when they photosynthesise? ans/Oxygen`

### Adding a flashcard with multiple choice question: `addmcq`

Adds a new flashcard to the application.

Format: `addmcq q/QUESTION ans/ANSWER c/first choice c/second choice ..`

Examples:
* `addmcq q/Plants give out ___ when they photosynthesise? ans/1 c/Oxygen c/Carbon c/Carbon dioxide`

### Edit a flashcard: `edit`

Edit a flashcard.

Format: `edit INDEX q/QUESTION ans/ANSWER c/first choice c/second choice ..`

Examples:
* `edit 1 q/Plants give out ___ when they photosynthesise? ans/2 c/Oxygen c/Carbon c/Carbon dioxide`

### Testing a flashcard : `test`

Tests a specified flashcard from the list.

#### Containing an open-ended question

Format: `test INDEX ans/ANSWER`

* Tests the flashcard at the specified `INDEX`
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer**  1, 2, 3, …
* The `ANSWER` is case-insensitive.

Examples:
* `list` followed by `test 1 a/Example answer` tests the 1st flashcard in the list with `Example answer` as the answer.

#### Containing a multiple choice question

Format: `test INDEX o/OPTION` 

* Tests the flashcard at the specified `INDEX`
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer**  1, 2, 3, …
* `CHOICE`(s) are displayed in the displayed choices list of the flashcard after `open INDEX` command is performed.
* The `OPTION` refers to the index number of the specified `CHOICE`.
* The `OPTION` **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `test 1 o/2` tests the 1st flashcard in the list with `OPTION 2` corresponding to the 2nd choice in the choices of the multiple choice question as the answer.

### Displaying statistics for a Flashcard: `stats`

Shows the Bar Chart for a specified Flashcard in the list.

Statistics include:

* The number of times the user answers the question associated with the flashcard correctly.
* The number of times the user attempted the question associated with the flashcard.

Format: `stats INDEX`

* Displays the statistics of a flashcard at the specified `INDEX`.
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer** 1, 2, 3, …

### Listing all flashcards : `list`

Shows a list of all flashcards currently created.

Format: `list`

### Finding Flashcards by their tags: `find`

Finds all Flashcards based on their tags.

Format: `find TAGS`

* Tags should be seperated by a whitespace between

Example: `find CS2100 MCQ` where `CS2100` and `MCQ` are tags.

### Deleting a flashcard : `delete`

Deletes the specified flashcard from the list.

Format: `delete INDEX`

* Deletes the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `delete 2` deletes the 2nd flashcard in the list.

### Clearing a flashcard's statistics : `clearstats`

Clears the specified flashcard's statistics.

Format: `clearstats INDEX`

* Clears the statistics of the flashcard at the specified `INDEX`.
* The index refers to the index number shown in the displayed flashcard list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `clearstats 2` clears the statistics of the 2nd flashcard in the list.

### Clearing all entries : `clear`

Clears all entries from QuickCache.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

QuickCache data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous QuickCache home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Open**| `open INDEX` <br> e.g., `open 3`
**Add** | `add q/QUESTION ans/ANSWER` <br> e.g., `add q/Plants give out ___ when they photosynthesise? ans/Oxygen`
**Addmcq** | `addmcq q/Plants give out ___ when they photosynthesise? ans/1 c/Oxygen c/Carbon c/Carbon dioxide`
**Test** | `test INDEX ans/ANSWER` (open-ended question)<br> e.g., `test 2 a/lorem ipsum` <br> `test INDEX o/OPTION` (multiple choice question)<br> e.g., `test 3 o/1`
**Clear** | `clear`
**ClearStats** | `clearstats INDEX`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**List** | `list`
**Find** | `find KEYWORDS` <br> e.g., `find CS2100 MCQ`
**Help** | `help`
**Exit** | `exit`
