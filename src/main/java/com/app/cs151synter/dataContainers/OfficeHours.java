package com.app.cs151synter.dataContainers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        ZonedDateTime a = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return "DATE: " + a.getMonthValue()  + "/" + a.getDayOfMonth() + " At: " + getLocation() + "\n";
    }

    public String[] getInfo() {
        return new String[0];
    }
    public String getTitle() {
        return "OFFICE HOURS";
    }
    public String getLocation () {
        return "SJSU";
    }
    @Override
    public String toString () {
        ZonedDateTime a = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return "OFFICE HOURS \n DATE: " + a.getMonthValue()  + "/" + a.getDayOfMonth();
    }

}
