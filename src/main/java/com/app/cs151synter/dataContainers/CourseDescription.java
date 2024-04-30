package com.app.cs151synter.dataContainers;

public class CourseDescription implements SyllabusEntity {
    String desc;
    public static void main(String[] args) {

    }
    public CourseDescription (String desc) {
        this.desc = desc;
    }

    public String[] getInfo() {
        return new String[]{desc};
    }

    public String getTitle() {
        return desc;
    }
}
