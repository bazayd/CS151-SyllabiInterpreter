package dataContainers;

import java.util.*;

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
    }
    private final String professorName; // in the format 'first last', lowercase
        // will be used for .equals() and hashcode()

    private List<Assignment> assignments = new ListWithRetainedOrder<>();
    private List<Test> allTests = new ListWithRetainedOrder<>();
    private List<Test> finals = new ListWithRetainedOrder<>();
    private List<Test> midterms = new ListWithRetainedOrder<>();
    private List<Test> quizzes = new ListWithRetainedOrder<>();
    private Policy policy;
    private Textbook textbook;
    private OfficeHours officeHours;
    private String classroomLocation;

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
    public void addDatedSyllabusEntities (List<DatedSyllabusEntities> entities) {
        for (DatedSyllabusEntities entity: entities) {
            if (entity == null)
                continue;
            if (entity instanceof Test)
                addTest((Test) entity);
            if (entity instanceof Assignment)
                addAssignment((Assignment) entity);
        }
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
        this.officeHours = officeHours;
    }

}
