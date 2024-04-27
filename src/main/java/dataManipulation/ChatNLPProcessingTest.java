package dataManipulation;

import dataContainers.Syllabus;

public class ChatNLPProcessingTest {

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

        String userIput = "What textbook to use?";
        IntentResponse response = chatBot.getIntentResponse(userIput);


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
    }
}
