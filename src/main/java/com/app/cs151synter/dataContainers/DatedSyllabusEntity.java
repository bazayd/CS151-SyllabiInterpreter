package com.app.cs151synter.dataContainers;

public interface DatedSyllabusEntity extends SyllabusEntity {
    String getDueDate();
    void setDueDate(String date);
    String getDescription();

}
