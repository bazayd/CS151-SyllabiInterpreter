package com.app.cs151synter.dataContainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Syllabus {
    private static class ListWithRetainedOrder <T extends Comparable<T>> extends ArrayList<T> {
        @Override
        public boolean add (T o) {
            int index = Collections.binarySearch(this, o);
            if (index < 0) {
                index = -(index + 1);
            }
            super.add(index, o);
            return true;
        }
        @Override
        public String toString () {
            String s = "";
            for (T o: this) {
                s += o + ", "; // not bothering with the extra comma
            }
            return s;
        }
    }
    private final String professorName; // in the format 'first last', lowercase
        // will be used for .equals() and hashcode()

    private List<Assignment> assignments = new ListWithRetainedOrder<>();
    private List<Test> allTests = new ListWithRetainedOrder<>();
    private List<Test> finals = new ListWithRetainedOrder<>();
    private List<Test> midterms = new ListWithRetainedOrder<>();
    private List<Test> quizzes = new ListWithRetainedOrder<>();
    private Textbook textbook;
    private OfficeHours officeHours;
    private String classroomLocation;
    private ContactInformation contactInformation;
    private CourseDescription courseDescription;
    private ClassroomProtocols classroomProtocols;


    public Syllabus (String professorName) {
        this.professorName = professorName.toLowerCase();
    }
    @Override
    public int hashCode () {
        return professorName.hashCode();
    }
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Syllabus))
            return false;
        return o.hashCode() == hashCode();
    }

    public String getProfName () {
        return professorName;
    }

    public void addTest (Test test) {
        if (test == null) {
            return;
        }
        TestType type = test.getType();
        if (type == TestType.FINAL) {
            finals.add(test);
        } else if (type == TestType.MIDTERM) {
            midterms.add(test);
        } else if (type == TestType.QUIZ) {
            quizzes.add(test);
        }
        allTests.add(test);
    }
    public List<Test> getTests (TestType type) {
        if (type == TestType.FINAL)
            return new ArrayList<>(finals); // just doing this so we don't get errors later
        else if (type == TestType.MIDTERM)
            return new ArrayList<>(midterms);
        else if (type == TestType.QUIZ)
            return new ArrayList<>(quizzes);
        return new ArrayList<>(allTests);
    }

    public void printAllTests () {
        System.out.println("Following Exams: ");
        for (Test test : allTests) {
            System.out.println(Arrays.toString(test.getInfo()));
        }
        for (Test test: finals) {
            System.out.println(Arrays.toString(test.getInfo()));
        }
        for (Test test : midterms) {
            System.out.println(Arrays.toString(test.getInfo()));
        }
        for (Test test: quizzes) {
            System.out.println(Arrays.toString(test.getInfo()));
        }
    }

    // Adds new assignments
    public void addAssignment (Assignment assignment) {
        if (assignment == null)
            return;
        assignments.add(assignment);
    }
    public void addDatedSyllabusEntities (List<DatedSyllabusEntity> entities) {
        for (DatedSyllabusEntity entity: entities) {
            if (entity == null)
                continue;
            if (entity instanceof Test)
                addTest((Test) entity);
            if (entity instanceof Assignment)
                addAssignment((Assignment) entity);
        }
    }

    public List<DatedSyllabusEntity> getDatedSyllabusEntites() {
        List<DatedSyllabusEntity> allDatedEntities = new ArrayList<>();

        // Add all assignments to the list of dated entities
        allDatedEntities.addAll(assignments);

        // Add all tests (including finals, midterms, and quizzes) to the list of dated entities
        allDatedEntities.addAll(allTests);

        return allDatedEntities;
    }
    public void addAssignments (List<Assignment> assignments) {
        for (Assignment a: assignments) {
            if (a != null)
                this.assignments.add(a);
        }
    }

    public void printAssignments() {
        for (Assignment assignment : assignments) {
            System.out.println(assignment);
        }
    }

    public String extractPolicy (Policy policy) {
        return Arrays.toString(policy.getInfo()) + policy.getTitle();
    }
    public String textbookRequired(Textbook textbook) {
        return Arrays.toString(textbook.getInfo() )+ textbook.getTitle();
    }

    public void setOfficeHours(OfficeHours officeHours) {
        if (officeHours != null)
            this.officeHours = officeHours;
    }

    public void setContactInformation (ContactInformation contactInformation) {
        if (contactInformation != null)
            this.contactInformation = contactInformation;
    }
    public void setCourseDescription (CourseDescription courseDescription) {
        if (courseDescription !=  null)
            this.courseDescription = courseDescription;
    }
    public void setTextbook (Textbook textbook) {
        if (textbook != null)
            this.textbook = textbook;
    }
    public void setClassroomProtocols (ClassroomProtocols classroomProtocols) {
        if (classroomProtocols != null)
            this.classroomProtocols = classroomProtocols;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Test> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<Test> allTests) {
        this.allTests = allTests;
    }

    public List<Test> getFinals() {
        return finals;
    }

    public void setFinals(List<Test> finals) {
        this.finals = finals;
    }

    public List<Test> getMidterms() {
        return midterms;
    }

    public void setMidterms(List<Test> midterms) {
        this.midterms = midterms;
    }

    public List<Test> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Test> quizzes) {
        this.quizzes = quizzes;
    }

    public Textbook getTextbook() {
        return textbook;
    }

    public String getClassroomLocation() {
        return classroomLocation;
    }

    public void setClassroomLocation(String classroomLocation) {
        this.classroomLocation = classroomLocation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public CourseDescription getCourseDescription() {
        return courseDescription;
    }

    public ClassroomProtocols getClassroomProtocols() {
        return classroomProtocols;
    }

    public String getProfessorName() {
        return professorName;
    }

    public OfficeHours getOfficeHours() {
        return officeHours;
    }

    @Override
    public String toString () {
        return "PROF: " + professorName +
                "\n\nFINALS: " + finals +
                "\n\nMIDTERMS: " + midterms +
                "\n\nQUIZZES: " + quizzes +
                "\n\nASSIGNMENTS: " + assignments +
                "\n\nCONTACT INFO: " + contactInformation +
                "\n\nOFFICE HOURS: " + officeHours +
                "\n\nCOURSE DESCRIPTION: " + courseDescription +
                "\n\nCLASSROOM PROTOCOLS: " + classroomProtocols +
                "\n\nTEXTBOOK: " + textbook;
    }
}
