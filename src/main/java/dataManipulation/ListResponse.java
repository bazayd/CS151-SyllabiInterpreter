package dataManipulation;

import dataContainers.SyllabusEntity;

import java.util.List;

public class ListResponse implements IntentResponse {
    List<SyllabusEntity> syllabusEntities;
    public static void main(String[] args) {

    }
    public ListResponse (List<SyllabusEntity> syllabusEntities) {
        this.syllabusEntities = syllabusEntities;
    }
}
