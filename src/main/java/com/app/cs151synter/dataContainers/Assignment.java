package com.app.cs151synter.dataContainers;

import java.util.Calendar;

public class Assignment implements DatedSyllabusEntity, Comparable<Assignment> {
    private Calendar date;
    private String title;
//    private LocalDate date;

    public static void main(String[] args) {

    }

    public Assignment (String title, Calendar date) {
        this.title = title;
        this.date = date;
    }

    public String[] getInfo() {
        return new String[0];
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDueDate() {
        return null;
    }

    public void setDueDate(Calendar date) {
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
