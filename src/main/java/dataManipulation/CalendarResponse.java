package dataManipulation;

import dataContainers.SyllabusEntities;

import java.util.List;

public class CalendarResponse {
    List<SyllabusEntities> list;
    public static void main(String[] args) {

    }
    public CalendarResponse (List<SyllabusEntities> list) {
        this.list = list;
    }
}
