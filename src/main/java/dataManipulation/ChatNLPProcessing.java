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

    public static void main(String[] args) {


    }

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
        } else if (intentCategory == PossibleIntents.MISC_PARAGRAPH) {
            return new ParagraphResponse("junk");
        }
        return new ParagraphResponse ("dfs");
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
        Pattern datesPattern = Pattern.compile ("date|schedule|test|exam|midterm|office hours|textbook|professor|location|grading policy|syllabus overview");
        Matcher matcher = datesPattern.matcher(input.toLowerCase());

        if (matcher.find()) {
            String matchedKeyword = matcher.group();
            if (matchedKeyword.contains("date") || matchedKeyword.contains("schedule")) {
                return PossibleIntents.SEE_DATES;
            } else if (matchedKeyword.contains("test")) {
                return PossibleIntents.GENERATE_TEST_SCHEDULE;
            }else if (matchedKeyword.contains("exam")) {
                return PossibleIntents.GENERATE_TEST_SCHEDULE;
            }else if (matchedKeyword.contains("midterm")) {
                return PossibleIntents.GENERATE_TEST_SCHEDULE;
            } else if (matchedKeyword.contains("office hours")) {
                return PossibleIntents.OFFICE_HOUR_TIMES;
            } else if (matchedKeyword.contains("textbook")) {
                return PossibleIntents.RECOMMEND_TEXTBOOK;
            } else if (matchedKeyword.contains("professor")) {
                return PossibleIntents.GET_PROFESSOR_INFO;
            } else if (matchedKeyword.contains("location")) {
                return PossibleIntents.GET_LOCATION;
            } else if (matchedKeyword.contains("grading policy")) {
                return PossibleIntents.GET_GRADING_POLICY;
            } else if (matchedKeyword.contains("syllabus overview")) {
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
