package dataContainers;

public interface DatedSyllabusEntity extends SyllabusEntities {
    String getDueDate();
    void setDueDate(String date);
}
