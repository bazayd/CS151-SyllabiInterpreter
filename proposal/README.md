# CS151-Syllabi Interpreter/Chatbot

# [Team 11: Brandon Arriaga, Vaishnavi Kouru, Ace Lavilla,Anhduy Phan]



## [Proposal/ Issue (What our project is mainly about)]: 

Different classes have different late policies, due dates, class expectations, etc. 
Students can’t always strictly rely on the Canvas calendar to keep track of their work 
Most professors don’t immediately release all assignments/quizzes on Canvas (which is understandable) 
Some professors use different platforms for homework, which will mean their due dates will not show up on Canvas 
Instead of navigating through each syllabus/class individually, it’d be nice if they were conglomerated into one organized, easily navigable application 

Professors can deal with less emails from students who clearly haven’t read the syllabus, and 
there will be a decrease in the time/effort students spend on keeping track of their course schedules/needs and the amount of mistakes they’ll make. 

## [previous work done, with references if possible]:

https://www.syllabuddy.com/ <- This generates a calendar based on a single syllabus that can be exported to google calendar 
https://www.coursehack.it/ <- This also generates a calendar, but based on multiple syllabi 
http://guilfordgeo.com/semcal/ <- Just thought it’s cool - not that related 
[If applicable, describe assumptions / operating environments / intended usage]
Possibilities: 
Desktop app <- probably best 
Web application (could still be local)  
Website (this could be sort of hard) 



Intended usage:
Intended to be used as a way for students to keep track of their classes’ midterms, assignments, and finals. It is intended to help students stay on top of assigned units and how it correlates to assignments and exams. This will also help students know policies for late work, make up exams and quizzes, etc. It is simply a tool to help college students spend more time on studying rather than trying to understand all of their classes’ syllabus!

## [High level description of our solution and can include our plan and approach]:

These would be our overall concerns: 
Extract information from syllabi using NLP (Identify course names, professor names, assignments, exams, quizzes, etc.)
Process and clean the text  
Train a Named Entity Recognition model so that it can correctly recognize the things mentioned above
https://docs.opensyllabus.org/index.html <- We could use this to train the thing 
Could also use Part-Of-Speech tagging to catch words like ‘submit,’ ‘attend,’ etc.

Store that information and analyze it 
We’d need to decide where this would run
We could write it so that even if we change our minds, it shouldn’t have much of an effect on the rest of the code. 
Generate a calendar 
Design the chatbot 

Plan and Approach based off concerns (Step by step):

- [ ] First, we will begin our application by designing our user interface and making it user friendly for all students. Not everyone will be tech savvy and people will want their interpreter to be easy to use. 
- [ ] Then, We will design a UML diagram for keeping track of all the possible classes and files. This will include classes necessary for running programs, databases, etc.
- [ ] Next, we will begin the coding process and address our concerns and implement solutions.
We will begin with implementing our user interface and making it come to life in order to have a shell to work with. Then, we will create a place to store our information and where it will analyze it. To store data that may be long term and accessed often we can keep track of that through a database and keep track of short term data through variables, etc.
- [ ] Finally, we will go over all of our concerns at the end and verify them through user scenarios and Operations.

> [!NOTE]
> Steps have been simplified but will include a deeper planning process.

## [Functionality: describe how your solution tackles the issues]:

Again, all the information from a student’s syllabi will be conglomerated into a single application that handles: generating calendars, possibly sending reminders, and answering questions that are answered in those documents. 
[Operations: List operations for each intended user (in list format).  Be precise and specific. Specific user scenarios for certain operations]

1.Student:
Configuration:
Upload the syllabi 
Provide additional information that may not have been included 
Manage notifications on/off 
Use the calendar 
Organize by week or month 
Talk to a bot that will answer questions like “What is professor X’s late policy?” 
Receive/mute notifications

2.Professor:
Configuration:
Upload syllabi 
Provide additional information
Creates a calendar, adds any other important information related to their class.
Organized by week or month
Have syllabi for students to review, with all information verified by the professor.
