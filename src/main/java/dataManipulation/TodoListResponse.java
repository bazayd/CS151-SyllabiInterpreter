package dataManipulation;

import dataContainers.DatedSyllabusEntities;

import java.util.ArrayList;
import java.util.List;

public class TodoListResponse implements IntentResponse {
    List<DatedSyllabusEntities> datedSyllabusEntities;
    public TodoListResponse(ArrayList<DatedSyllabusEntities> datedSyllabusEntities) {
        this.datedSyllabusEntities = datedSyllabusEntities;
    }
    public static void main(String[] args) {

    }
}
