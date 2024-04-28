package dataManipulation;
import dataContainers.DatedSyllabusEntity;
import dataContainers.Syllabus;
import dataContainers.SyllabusEntities;
import ui.StudentRawInfo;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatNLPProcessing {
    // key is professor last name
    private Map<String, Syllabus> syllabi = new HashMap<>();
    private StudentRawInfo studentRawInfo; // add a listener to th

    public static void main(String[] args) {

        ChatNLPProcessing chatBot = new ChatNLPProcessing();

        Syllabus syllabus = new Syllabus("prof");

        chatBot.addSyllabus("prof",syllabus);


        Syllabus retrieved = new Syllabus("prof");
        if (retrieved != null) {
            System.out.println("Syllabus retrieved successfully");
        }else {
            System.out.println("Syllabus not found for following name.");
        }

        Syllabus updatedSyllabus = new Syllabus("professor1");
        // Add updated assignments, tests, etc. if needed
        chatBot.updateSyllabus("prof1", updatedSyllabus);
        // Verify that the syllabus has been updated by retrieving it again and comparing with the updated version

        // Test removing the syllabus
        chatBot.removeSyllabus("prof1");
        // Verify that the syllabus has been removed by attempting to retrieve it again
        Syllabus removedSyllabus = chatBot.getSyllabus("prof1");
        if (removedSyllabus == null) {
            System.out.println("Syllabus successfully removed for professor prof1.");
        } else {
            System.out.println("Failed to remove syllabus for professor prof1.");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Speak to chat bot (x to quit): ");

        while (scanner.hasNext()) {
            String userInput = scanner.next();


            if (userInput.equals("x")) {
                break;
            }

            IntentResponse response = chatBot.getIntentResponse(userInput);

            if (response instanceof ParagraphResponse) {
                System.out.println(((ParagraphResponse) response).generateResponse());
            } else if (response instanceof CalendarResponse) {
                System.out.println(((CalendarResponse) response).generateResponse());
            } else if (response instanceof TodoListResponse) {
                System.out.println(((TodoListResponse) response).generateResponse());
            } else if (response instanceof ListResponse) {
                System.out.println(((ListResponse) response).generateResponse());
            } else {
                System.out.println("Other response..");
            }

            System.out.println("Speak to chat bot (x to quit): ");

        }

    }

    // the method that the other packages classes would be invoking
    public IntentResponse getIntentResponse (String input) {
        PossibleIntents intentCategory = findGeneralIntent(input);


        if (intentCategory == PossibleIntents.SEE_DATES) {
            return new CalendarResponse(new ArrayList<DatedSyllabusEntity>());
        } else if (intentCategory == PossibleIntents.GENERATE_TEST_SCHEDULE) {
            return new CalendarResponse(new ArrayList<DatedSyllabusEntity>());
        } else if (intentCategory == PossibleIntents.OFFICE_HOUR_TIMES) {
            return new ParagraphResponse("Office Hour Times: ... ");
        } else if (intentCategory == PossibleIntents.GET_LOCATION) {
            return new ParagraphResponse("Classroom Location: ");
        } else if (intentCategory == PossibleIntents.GET_GRADING_POLICY) {
            /*
            return list of grading policies
            (Ex: - Homeworking Grade 20%, - Midterms 25% each, - Final 30%)
             */
            return new ListResponse(new ArrayList<SyllabusEntities>());
        } else if (intentCategory == PossibleIntents.RECOMMEND_TEXTBOOK) {
            // Returns recommended Textbook/s from extracted syllabus
            return new ParagraphResponse("Recommended Textbook: ");
        } else if (intentCategory == PossibleIntents.SYLLABUS_OVERVIEW) {
            // returns list of important syllabus information
            return new ListResponse(new ArrayList<SyllabusEntities>());

        }

        return null;
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
