package com.app.cs151synter.dataContainers;

import java.util.Calendar;

public interface DatedSyllabusEntity extends SyllabusEntity {
    Calendar getDueDate();
    void setDueDate(Calendar date);
    String getDescription();

}
