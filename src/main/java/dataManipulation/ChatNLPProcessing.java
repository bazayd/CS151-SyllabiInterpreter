package dataManipulation;

import dataContainers.DatedSyllabusEntities;
import dataContainers.Syllabus;
import dataContainers.SyllabusEntities;
import ui.StudentRawInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        }
        return new ParagraphResponse ("dfs");
    }


    private PossibleIntents findGeneralIntent (String input) {
        return PossibleIntents.UNKNOWN;
    }
    public void addSyllabus (String profLastname, Syllabus syllabus) {
        syllabi.put (profLastname, syllabus);
    }
}
