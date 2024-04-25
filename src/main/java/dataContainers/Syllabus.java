package dataContainers;

import org.ejml.equation.Macro;

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
    }
    private List<Assignment> assignments = new ListWithRetainedOrder<>();
    private List<Test> tests = new ListWithRetainedOrder<>();
    private List<Test> finals = new ListWithRetainedOrder<>();
    private List<Test> midterms = new ListWithRetainedOrder<>();
    private List<Test> quizzes = new ListWithRetainedOrder<>();
    private Policy policy;
    private Textbook textbook;


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
        }else {
            tests.add(test);
        }
    }


    public void accessTests() {
        System.out.println("Following Exams: ");
        for (Test test : tests) {
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
        if (assignment == null) {
            return;
        }else {
            assignments.add(assignment);
        }
    }

    public void accessAssignments() {
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
}
