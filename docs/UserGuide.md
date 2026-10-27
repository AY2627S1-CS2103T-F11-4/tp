---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TutorBroPro User Guide

TutorBroPro is a **desktop application for tutors to manage student profiles and lesson records**, optimized for use through a Command Line Interface (CLI) while retaining the benefits of a Graphical User Interface (GUI).

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure that Java `25` or later is installed on your computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2627S1-CS2103T-F11-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TutorBroPro.

1. Open a terminal, `cd` to the folder containing the JAR file, and run `java -jar addressbook.jar`.<br>
   A GUI similar to the one below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it. For example, type **`help`** and press Enter to open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `addstudent n/John Doe lvl/Sec 2 s/Math pn/91234567 pe/johnd@example.com` : Adds a student named `John Doe`.

   * `view 1` : Shows the full profile of the 1st student shown in the current list.

   * `addnote 1 d/2026-10-01 s/Math note/Revised simultaneous equations.` : Adds a lesson note.

   * `history 1` : Shows the lesson history of the 1st student shown in the current list.

   * `deletestudent 3` : Deletes the 3rd student shown in the current list.

   * `deletenote 1 ln/2` : Deletes the 2nd lesson note of the 1st student.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) section below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  For example, in `addstudent n/NAME`, replace `NAME` with a value such as `John Doe`.

* Items followed by `...` can appear zero or more times.<br>
  For example, `s/SUBJECT...` may be written as `s/Math` or `s/Math s/Science`.

* Parameters can be in any order where the command supports multiple prefixed fields.<br>
  For example, `pn/PARENT_PHONE pe/PARENT_EMAIL n/NAME` is also acceptable for `addstudent`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Adding a student: `addstudent`

Adds a student profile with the student's name, academic level, subjects, and parent contact details.

Format: `addstudent n/NAME lvl/LEVEL s/SUBJECT... pn/PARENT_PHONE pe/PARENT_EMAIL`

* `NAME`, `LEVEL`, at least one `SUBJECT`, `PARENT_PHONE`, and `PARENT_EMAIL` are compulsory.
* `NAME` may contain alphanumeric characters, spaces, `-`, and `/`.
* `LEVEL` must be one of `Pri 1` to `Pri 6`, `Sec 1` to `Sec 5`, or `JC 1` to `JC 2`.
* `SUBJECT` must contain only letters and spaces and must be 2 to 30 characters long.
* Duplicate subjects are merged.
* Duplicate students are not allowed. A student is considered a duplicate only when both the name and parent phone number match an existing student.
* `PARENT_PHONE` must contain exactly 8 digits and start with `8` or `9`.
* `PARENT_EMAIL` must be a valid email address in the form `example@domain`.

Example:
* `addstudent n/John Doe lvl/Sec 2 s/Math s/Science pn/91234567 pe/johnd@example.com`

### Editing a student: `edit`

Edits an existing student in the address book.

Format: `edit INDEX [n/NAME] [lvl/LEVEL] [s/SUBJECT]... [pn/PARENT_PHONE] [pe/PARENT_EMAIL]`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Supplied fields are updated; omitted fields remain unchanged.
* Invalid edits leave all student profiles and lesson notes unchanged.
* An edit cannot create another student with the same name and parent phone number.
* After a successful edit, the full student list is displayed.
* When editing subjects, all of the student's existing subjects are replaced; adding subjects is not cumulative. To replace them, list every subject you want the student to keep.
* The edited student must retain at least one subject. Empty subject entries such as `edit 1 s/` are rejected.
* Each supplied field follows the same validation rules as `addstudent`.
* TEditing a student's profile does not change or delete existing lesson notes. Removing a subject from the profile does not remove historical notes recorded for that subject.

Examples:
* `edit 1 pn/91234567 pe/johndoe@example.com` Edits the parent phone and parent email of the 1st student.
* `edit 2 n/Betsy Crower s/Math s/Science` Edits the name of the 2nd student and replaces their subjects with `Math` and `Science`.

### Viewing a student's profile: `view`

Shows the full profile of a student, including their academic level, subjects, parent contact details, and number of recorded lessons.

Format: `view INDEX`

* `INDEX` refers to the student number shown in the displayed student list and must be a positive integer.

Example:
* `view 1` shows the full profile of the 1st student.

### Listing all students: `list`

Shows a list of all students in the address book, clearing any filter applied by `find` or `filter`.

Format: `list`

### Finding students: `find`

Searches all students by name and displays the matches with new index numbers.

Format: `find KEYWORD [MORE_KEYWORDS]...`

* Matching is case-insensitive and includes students whose names contain any of the keywords as whole words.
* If there are no matches, an empty student list is displayed.
* Use `list` to display all students again.

Example:
* `find Alex`

### Filtering students: `filter`

Filters all students by academic level, subject, or both, and displays the matches with new index numbers.

Format: `filter [s/SUBJECT]... [lvl/LEVEL]`

* At least one `s/` or `lvl/` criterion must be provided.
* Subject and academic-level matching is case-insensitive.
* A student must match the specified academic level, if one is provided.
* When multiple subjects are specified, a student must take every specified subject.
* Students may take additional subjects not specified in the command.
* If there are no matches, an empty student list is displayed.
* Use `list` to display all students again.

Examples:

* `filter s/Math` displays students who take Math.
* `filter lvl/Sec 2` displays students in Secondary 2.
* `filter s/Math s/Science lvl/Sec 2` displays Secondary 2 students who take both Math and Science.

### Adding a lesson note: `addnote`

Adds a lesson note to an existing student in the address book.

Format: `addnote INDEX d/DATE s/SUBJECT note/NOTE_TEXT`

* Adds the lesson note to the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, ...
* All three of `d/`, `s/` and `note/` are compulsory.
* `DATE` must be a real calendar date in `YYYY-MM-DD` format and cannot be a future date.
* `SUBJECT` must contain only letters and spaces and must be 2 to 30 characters long.
* `NOTE_TEXT` cannot be blank, must not exceed 500 characters, and must be on a single line.
* A student cannot have two lesson notes with the same date, subject and note text.
* Lesson notes are kept sorted from the most recent date to the earliest.

Examples:
* `addnote 1 d/2026-10-01 s/Math note/Revised simultaneous equations.`
* `addnote 2 d/2026-09-20 s/Science note/Covered photosynthesis, needs more practice on diagrams.`

### Editing a lesson note: `editnote`

Edits an existing lesson note of a student in the address book.

Format: `editnote INDEX ln/LESSON_INDEX [d/DATE] [s/SUBJECT] [note/NOTE_TEXT]`

* Edits a lesson note of the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list.
* `LESSON_INDEX` refers to the number shown next to the note in the output of `history INDEX`.
* Both indexes **must be positive integers** 1, 2, 3, ...
* At least one of `d/`, `s/` and `note/` must be provided.
* Only the fields you provide are changed; the rest of the note is left as it was.
* `DATE`, `SUBJECT` and `NOTE_TEXT` follow the same rules as in `addnote`.
* Changing the date re-sorts the student's notes, so a note's number may change. Run `history INDEX` again before editing or deleting another note, replacing `INDEX` with the student's current displayed index.

Examples:
* `editnote 1 ln/2 note/Recapped photosynthesis.` rewrites the text of the 2nd lesson note of the 1st student.
* `editnote 1 ln/1 d/2026-09-28 s/Science` changes the date and subject of the most recent lesson note of the 1st student.

### Viewing lesson history: `history`

Shows all lesson notes recorded for a student, from the most recent date to the earliest.

Format: `history INDEX`

* Shows the lesson notes of the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, ...
* Each note is numbered. Use that number as `LESSON_INDEX` in `editnote` or `deletenote`.
* `INDEX` selects a student from the currently displayed student list; 
`LESSON_INDEX` selects a note from that student's history.
* If the student has no lesson notes, a message saying so is shown instead.

Examples:
* `history 1` shows the lesson history of the 1st student, for example:

```
Lesson history for John Doe:
1. 2026-10-01 | Math | Revised simultaneous equations.
2. 2026-09-20 | Science | Covered photosynthesis.
```

### Deleting a lesson note: `deletenote`

Deletes one lesson note from an existing student in the address book.

Format: `deletenote INDEX ln/LESSON_INDEX`

* Deletes a lesson note from the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list.
* `LESSON_INDEX` refers to the number shown next to the note in the output of `history INDEX`.
* Both indexes **must be positive integers** 1, 2, 3, ...
* Deleting a note shifts subsequent notes up by one position. Run `history INDEX` again before editing or deleting another note; do not reuse note numbers from an earlier history output.

Examples:
* `deletenote 1 ln/2` deletes the 2nd lesson note of the 1st student.
* `deletenote 3 ln/1` deletes the most recent lesson note of the 3rd student.

### Deleting a student: `deletestudent`

Deletes the specified student and all their associated lesson notes from the address book.

Format: `deletestudent INDEX`

* Deletes the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `deletestudent 2` deletes the 2nd student in the displayed student list.

The success message reports the student name and the number of lesson notes removed, for example:
`Deleted student: Alex Yeoh, and their 3 lesson note(s).`
A student with no notes can also be deleted; the reported count is `0`.

Deletion is immediate, without a confirmation prompt, and cannot be undone. Other students and their notes remain unchanged.

### Clearing all entries: `clear`

Removes every student, together with all their lesson notes, from the address book.

Format: `clear`

This command does not accept an index or other arguments. Inputs such as `clear 1` are rejected without changing any data.

<box type="warning" seamless>

**Caution:** This cannot be undone, and you are not asked to confirm. The change is saved to the data file immediately.
</box>

### Viewing help: `help`

Opens a window with a link to this user guide.

Format: `help`

### Exiting the program: `exit`

Exits the application. Your data is already saved, so nothing is lost.

Format: `exit`

### Saving the data

TutorBroPro automatically saves data after every command. You do not need to save manually.

### Editing the data file

TutorBroPro data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes make the data file invalid, TutorBroPro starts with an empty address book at the next run. The invalid file remains on disk until you run a command (TutorBroPro saves after every command). Still, we recommend backing up the file before editing it.<br>
Furthermore, certain edits can cause TutorBroPro to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the data file it creates with the data file from your previous TutorBroPro home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add student** | `addstudent n/NAME lvl/LEVEL s/SUBJECT... pn/PARENT_PHONE pe/PARENT_EMAIL`<br> e.g., `addstudent n/James Ho lvl/Sec 2 s/Math s/Science pn/91234567 pe/jamesho@example.com`
**Edit student** | `edit INDEX [n/NAME] [lvl/LEVEL] [s/SUBJECT]... [pn/PARENT_PHONE] [pe/PARENT_EMAIL]`<br> e.g., `edit 2 n/James Lee pe/jameslee@example.com`
**View profile** | `view INDEX`<br> e.g., `view 1`
**List students** | `list`
**Find students** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Filter students** | `filter [s/SUBJECT]... [lvl/LEVEL]`<br> e.g., `filter s/Math s/Science lvl/Sec 2`
**Add lesson note** | `addnote INDEX d/DATE s/SUBJECT note/NOTE_TEXT`<br> e.g., `addnote 1 d/2026-10-01 s/Math note/Revised simultaneous equations.`
**Edit lesson note** | `editnote INDEX ln/LESSON_INDEX [d/DATE] [s/SUBJECT] [note/NOTE_TEXT]`<br> e.g., `editnote 1 ln/2 note/Recapped photosynthesis.`
**View lesson history** | `history INDEX`<br> e.g., `history 1`
**Delete lesson note** | `deletenote INDEX ln/LESSON_INDEX`<br> e.g., `deletenote 1 ln/2`
**Delete student** | `deletestudent INDEX`<br> e.g., `deletestudent 3`
**Clear** | `clear`
**Help** | `help`
**Exit** | `exit`
