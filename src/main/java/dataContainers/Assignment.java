package dataContainers;

import java.time.LocalDate;

public class Assignment implements DatedSyllabusEntities, Comparable<Assignment> {
    private String date;
    private String title;
//    private LocalDate date;

    public static void main(String[] args) {

    }

    public Assignment (String title, String date) {
        this.title = title;
        this.date = date;
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


    public void setDueDate(String date) {
        this.date = date;
    }
    @Override
    public String toString () {
        return "DATE: " + date  +
                "\nTITLE: " + title;
    }

    @Override
    public String getDescription() {
        return "";
    }


    public int compareTo(Assignment o) {
        return 0;
    }
}
