package dataManipulation;
import dataContainers.DatedSyllabusEntity;

import java.util.List;

public class CalendarResponse implements IntentResponse{

    private List<DatedSyllabusEntity> calendarData;


    public CalendarResponse(List<DatedSyllabusEntity> calendarData) {
        this.calendarData = calendarData;
    }


    @Override
    public String generateResponse() {
        // Generate response text based on calendarData
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Calendar Events:\n");
        for (DatedSyllabusEntity event : calendarData) {
            responseBuilder.append("- ").append(event.getDueDate()).append(": ").append(event.getDescription()).append("\n");
        }
        return responseBuilder.toString();
    }

}
