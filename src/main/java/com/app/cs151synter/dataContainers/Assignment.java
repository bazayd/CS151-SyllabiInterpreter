package com.app.cs151synter.dataContainers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        return date;
    }

    public void setDueDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return "Assignment Description\n";
    }

    @Override
    public String toString () {
        ZonedDateTime a = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return "DATE: " + a.getMonthValue()  + "/" + a.getDayOfMonth() +
                "\nTITLE: " + title;
    }

    @Override
    public int compareTo(Assignment o) {
        return 0;
    }
}
