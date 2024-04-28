package dataManipulation;
import dataContainers.DatedSyllabusEntities;
import java.util.List;

public class CalendarResponse implements IntentResponse{

    private List<DatedSyllabusEntities> calendarData;


    public CalendarResponse(List<DatedSyllabusEntities> calendarData) {
        this.calendarData = calendarData;
    }


    @Override
    public String generateResponse() {
        // Generate response text based on calendarData
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Calendar Events:\n");
        for (DatedSyllabusEntities event : calendarData) {
            responseBuilder.append("- ").append(event.getDueDate()).append(": ").append(event.getDescription()).append("\n");
        }
        return responseBuilder.toString();
    }

}
