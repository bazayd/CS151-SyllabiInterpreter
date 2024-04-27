package dataContainers;

public interface DatedSyllabusEntities extends SyllabusEntities {
    String getDueDate();
    void setDueDate(String date);
}
