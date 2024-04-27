package dataManipulation;
import dataContainers.DatedSyllabusEntity;

import java.util.List;

public class CalendarResponse implements IntentResponse{
    List<DatedSyllabusEntity> list;
    public static void main(String[] args) {

    }
    public CalendarResponse (List<DatedSyllabusEntity> list) {
        this.list = list;
    }
}
