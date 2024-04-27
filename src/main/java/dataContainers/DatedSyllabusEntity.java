package dataContainers;

public interface DatedSyllabusEntity extends SyllabusEntity {
    String getDueDate();
    void setDueDate(String date);
}
