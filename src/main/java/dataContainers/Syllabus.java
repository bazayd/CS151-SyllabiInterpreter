package dataContainers;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Syllabus {
    private class ListWithRetainedOrder <T extends Comparable<T>> extends ArrayList<T> {
        @Override
        public boolean add (T o) {
            super.add(Collections.binarySearch(this, o), o);
            return true;
        }
    }
    private List<Assignment> assignments = new ListWithRetainedOrder<>();
    private List<Test> tests = new ListWithRetainedOrder<>();
    private List<Test> finals = new ListWithRetainedOrder<>();
    private List<Test> midterms = new ListWithRetainedOrder<>();

    private Policy policy;

    private Textbook textbook;

    public void addTest (Test test) {
        if (test == null)
            return;
        TestType type = test.getType();
        if (type == TestType.FINAL)
            finals.add (test);
        else if (type == TestType.MIDTERM)
            midterms.add (test);
        tests.add(test);
    }


    // Adds new assignments
    public void addAssignment (Assignment assignment) {
        if (assignment == null) {
            return;
        }
        assignments.add(assignment);
    }
}
