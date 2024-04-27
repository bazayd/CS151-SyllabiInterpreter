package dataManipulation;
import dataContainers.DatedSyllabusEntity;

import java.util.ArrayList;
import java.util.List;

public class TodoListResponse implements IntentResponse {
    List<DatedSyllabusEntity> datedSyllabusEntities;
    public TodoListResponse(ArrayList<DatedSyllabusEntity> datedSyllabusEntities) {
        this.datedSyllabusEntities = datedSyllabusEntities;
    }
    public static void main(String[] args) {

    }
}
