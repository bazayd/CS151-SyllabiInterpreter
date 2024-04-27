package dataManipulation;

import dataContainers.DatedSyllabusEntities;
import dataContainers.Syllabus;
import dataContainers.SyllabusEntities;
import javafx.geometry.Pos;
import ui.StudentRawInfo;
import edu.stanford.nlp.simple.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatNLPProcessing {
    // key is professor last name
    private Map<String, Syllabus> syllabi = new HashMap<>();
    private StudentRawInfo studentRawInfo; // add a listener to this


    // the method that the other packages classes would be invoking
    public IntentResponse getIntentResponse (String input) {
        PossibleIntents intentCategory = findGeneralIntent(input);

        if (intentCategory == PossibleIntents.SEE_DATES) {
            // i know the <..> is redundant but i'm writing it to avoid confusion
            return new CalendarResponse(new ArrayList<DatedSyllabusEntities>());
        } else if (intentCategory == PossibleIntents.GENERATE_TODO_LIST) {
            return new TodoListResponse(new ArrayList<DatedSyllabusEntities>());
        } else if (intentCategory == PossibleIntents.GENERATE_LIST) {
            return new ListResponse(new ArrayList<SyllabusEntities>());
        }else if (intentCategory == PossibleIntents.RECOMMEND_TEXTBOOK) {
            return new ParagraphResponse("Textbook");
        }
        return new ParagraphResponse("Response");

    }

    private List<String> findProfessors (String input) {
        return new LinkedList<>();
    }


    /*
    Gets multiple keywords and then sorts through them based on what the user inputs.
    If user inputs something related to test or exams it will generate the test schedule to show
    the user all upcoming tests.
     */
    public PossibleIntents findGeneralIntent(String input) {
        Pattern keywordsPattern = Pattern.compile("\\b(?:date|schedule|test|exam|midterm|office hours|textbook|professor|location|grading policy|syllabus overview)\\b");
        Matcher matcher = keywordsPattern.matcher(input.toLowerCase());

        while (matcher.find()) {
            String matchedKeyword = matcher.group();
            switch (matchedKeyword) {
                case "date":
                case "schedule":
                    return PossibleIntents.SEE_DATES;
                case "test":
                case "exam":
                case "midterm":
                    return PossibleIntents.GENERATE_TEST_SCHEDULE;
                case "office hours":
                    return PossibleIntents.OFFICE_HOUR_TIMES;
                case "textbook":
                    return PossibleIntents.RECOMMEND_TEXTBOOK;
                case "professor":
                    return PossibleIntents.GET_PROFESSOR_INFO;
                case "location":
                    return PossibleIntents.GET_LOCATION;
                case "grading policy":
                    return PossibleIntents.GET_GRADING_POLICY;
                case "syllabus overview":
                    return PossibleIntents.SYLLABUS_OVERVIEW;
            }
        }
        return PossibleIntents.UNKNOWN;
    }


    /*
    *  Following methods allow for adding a Syllabus, getting access to syllabus,
    * updating Syllabus, and removing syllabus.
    * */
    public void addSyllabus (String profName, Syllabus syllabus) {
        syllabi.put (profName, syllabus);
    }

    public Syllabus getSyllabus(String profLastName) {
        return syllabi.get(profLastName);
    }

    public void updateSyllabus(String profLastName, Syllabus updatedSyllabus) {
        if (syllabi.containsKey(profLastName)) {
            syllabi.put(profLastName, updatedSyllabus);
        } else {
            System.out.println("Syllabus for given professor does not exist.");
        }
    }

    public void removeSyllabus(String profLastName) {
        syllabi.remove(profLastName);
    }
}
