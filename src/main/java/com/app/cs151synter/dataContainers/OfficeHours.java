package com.app.cs151synter.dataContainers;

import java.util.Calendar;

public class OfficeHours implements DatedSyllabusEntity{
    private Calendar date;
    public static void main(String[] args) {

    }
    public OfficeHours (String desc, Calendar date) {
        this.date = date;
    }

    public Calendar getDueDate() {
        return date;
    }


    public void setDueDate(Calendar date) {
        this.date = date;
    }
    @Override
    public String getDescription() {
        return "";
    }

    public String[] getInfo() {
        return new String[0];
    }
    public String getTitle() {
        return null;
    }
    public String getLocation () {
        return "somewhere";
    }
    @Override
    public String toString () {
        return date.toString();
    }
}
