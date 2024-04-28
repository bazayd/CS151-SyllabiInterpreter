package dataContainers;

public class CourseDescription implements SyllabusEntities {
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
