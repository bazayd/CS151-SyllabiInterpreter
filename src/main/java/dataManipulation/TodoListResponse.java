package dataManipulation;

import dataContainers.DatedSyllabusEntity;

import java.util.ArrayList;
import java.util.List;

public class TodoListResponse implements IntentResponse {

    private List<DatedSyllabusEntity> todoListData;

    public TodoListResponse(ArrayList<DatedSyllabusEntity> todoListData) {

        this.todoListData = todoListData;
    }


    @Override
    public String generateResponse() {
        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append("Todo List:\n");
        for (DatedSyllabusEntity event : todoListData) {
            responseBuilder.append("- ").append(event.getDueDate()).append(": ").append(event.getDescription()).append("\n");
        }
        return responseBuilder.toString();
    }
}
