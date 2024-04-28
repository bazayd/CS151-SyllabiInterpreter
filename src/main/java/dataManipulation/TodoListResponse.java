package dataManipulation;
import dataContainers.DatedSyllabusEntities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListResponse implements IntentResponse {

    private List<DatedSyllabusEntities> todoListData;

    public TodoListResponse(ArrayList<DatedSyllabusEntities> todoListData) {
        this.todoListData = todoListData;
    }


    @Override
    public String generateResponse() {
        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append("Todo List:\n");
        for (DatedSyllabusEntities event : todoListData) {
            responseBuilder.append("- ").append(event.getDueDate()).append(": ").append(event.getDescription()).append("\n");
        }
        return responseBuilder.toString();
    }
}
