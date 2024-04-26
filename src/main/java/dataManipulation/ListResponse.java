package dataManipulation;

import dataContainers.SyllabusEntities;

import java.util.List;

public class ListResponse implements IntentResponse {
    List<SyllabusEntities> syllabusEntities;
    public static void main(String[] args) {

    }
    public ListResponse (List<SyllabusEntities> syllabusEntities) {
        this.syllabusEntities = syllabusEntities;
    }
}
