[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/se-edu/addressbook-level3/actions)
[![codecov](https://codecov.io/gh/AY2627S1-CS2103T-F11-4/tp/graph/badge.svg?token=CB27POSDL7)](https://codecov.io/gh/AY2627S1-CS2103T-F11-4/tp)

![Ui](docs/images/Ui.png)

**TutorBroPro is a desktop app for freelance private tutors to manage their students and lesson records.** It is optimised for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

* **Purpose**: TutorBroPro keeps each student's profile and lesson history in one place, so a tutor can look up a student and review past lessons before the next one.
* **Target users**: Freelance private tutors who teach many students across different academic levels and subjects, can type fast, and prefer a lightweight, offline app.
* **Problem solved**: Tutors often keep student details, parent contacts and lesson notes scattered across chat messages, notebooks and memory, which makes it hard to recall what was covered with each student or to reach a parent quickly.
* **Value proposition**: TutorBroPro lets tutors manage students and their associated information faster and more intuitively than with scattered notes or a typical mouse-driven app.

## **Key Features**

Use the commands below to manage student profiles and lesson records.

| Feature                                                                               | Command | Example |
|---------------------------------------------------------------------------------------|---|---|
| Add a student profile with name, academic level, subjects, and parent contact details | `addstudent n/NAME lvl/LEVEL s/SUBJECT... pn/PARENT_PHONE pe/PARENT_EMAIL` | `addstudent n/Alex Yeoh lvl/Sec 2 s/Math s/Science pn/91234567 pe/parent_alex@example.com` |
| Delete a student and their lesson notes                                               | `deletestudent INDEX` | `deletestudent 3` |
| View a student's full profile                                                         | `view INDEX` | `view 2` |
| Add a dated lesson note                                                               | `addnote INDEX d/DATE subj/SUBJECT note/NOTE_TEXT` | `addnote 1 d/2026-09-18 subj/Math note/Covered quadratic equations` |
| Delete a lesson note                                                                  | `deletenote INDEX ln/LESSON_INDEX` | `deletenote 1 ln/2` |
| View a student's lesson history                                                       | `history INDEX` | `history 1` |

## **Documentation and Resources**

- [User Guide](https://ay2627s1-cs2103t-f11-4.github.io/tp/UserGuide.html)
- [Developer Guide](https://ay2627s1-cs2103t-f11-4.github.io/tp/DeveloperGuide.html)
- [About Us](https://ay2627s1-cs2103t-f11-4.github.io/tp/AboutUs.html)
