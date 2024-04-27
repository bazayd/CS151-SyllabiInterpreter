package dataContainers;

public class Assignment implements DatedSyllabusEntities, Comparable<Assignment> {
    public static void main(String[] args) {

    }

    public String[] getInfo() {
        return new String[0];
    }

    public String getTitle() {
        return null;
    }

    public String getDueDate() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }

    public int compareTo(Assignment o) {
        return 0;
    }
}
