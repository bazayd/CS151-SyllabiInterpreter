package dataManipulation;

import dataContainers.DatedSyllabusEntities;
import dataContainers.Syllabus;
import dataContainers.SyllabusEntities;
import ui.StudentRawInfo;

import java.util.*;
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
    public PossibleIntents findGeneralIntent(String input) {
        Pattern datesPattern = Pattern.compile ("date|schedule");
        if (datesPattern.matcher(input.toLowerCase()).find())
            return PossibleIntents.SEE_DATES;
        return PossibleIntents.UNKNOWN;
    }
    public void addSyllabus (String profName, Syllabus syllabus) {
        syllabi.put (profName, syllabus);
    }
}
