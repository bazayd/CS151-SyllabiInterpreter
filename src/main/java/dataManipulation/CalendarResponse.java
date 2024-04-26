package dataManipulation;

import dataContainers.DatedSyllabusEntities;
import dataContainers.SyllabusEntities;

import java.util.List;

public class CalendarResponse implements IntentResponse{
    List<DatedSyllabusEntities> list;
    public static void main(String[] args) {

    }
    public CalendarResponse (List<DatedSyllabusEntities> list) {
        this.list = list;
    }
}
