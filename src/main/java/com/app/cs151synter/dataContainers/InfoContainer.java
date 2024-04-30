package com.app.cs151synter.dataContainers;

import java.util.ArrayList;
import java.util.List;

// singleton
public class InfoContainer {
    private List<Syllabus> syllabi;
    private static InfoContainer instance;

    private InfoContainer () {
        syllabi = new ArrayList<>();
    }

    private static InfoContainer getInstance () {
        if (instance == null)
            instance = new InfoContainer();
        return instance;
    }

    public static void addSyllabus(Syllabus syllabus) {
        if (syllabus != null)
            InfoContainer.getInstance().getSyllabi().add(syllabus);
    }

    private List<Syllabus> getSyllabi() {
        return syllabi;
    }
}
