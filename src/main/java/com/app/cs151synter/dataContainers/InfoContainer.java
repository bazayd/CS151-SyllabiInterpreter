package com.app.cs151synter.dataContainers;

import java.util.ArrayList;
import java.util.List;

public class InfoContainer {
    private List<Syllabus> syllabi;
    private List<DatedSyllabusEntity> allDatedSyllabusEntities;

    private static InfoContainer instance = null;

    private InfoContainer () {
        syllabi = new ArrayList<>();
        allDatedSyllabusEntities = new ArrayList<>();
    }
    private static InfoContainer getInstance() {
        if (instance == null)
            instance = new InfoContainer();
        return instance;
    }

    public static void addSyllabus (Syllabus syllabus) {
        if (syllabus == null)
            return;

        getInstance().syllabi.add(syllabus);
    }
    public static List<DatedSyllabusEntity> getAllDatedSyllabusEntities () {
        getInstance().allDatedSyllabusEntities = new ArrayList<>();

        for (Syllabus s: getInstance().syllabi) {
            getInstance().allDatedSyllabusEntities.addAll(s.getDatedSyllabusEntites());
        }

        return getInstance().allDatedSyllabusEntities;
    }
//    public
}
