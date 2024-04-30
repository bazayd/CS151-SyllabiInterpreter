package com.app.cs151synter.dataContainers;

public class Assignment implements DatedSyllabusEntity, Comparable<Assignment> {
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
        return "a";
    }

    public String getDueDate() {
        return null;
    }

    public void setDueDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return "a";
    }

    @Override
    public String toString () {
        return "DATE: " + date  +
                "\nTITLE: " + title;
    }

    @Override
    public int compareTo(Assignment o) {
        return 0;
    }
}
