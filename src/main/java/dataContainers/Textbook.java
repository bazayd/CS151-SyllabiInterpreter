package dataContainers;

public class Textbook implements SyllabusEntity {
    String desc;
    public static void main(String[] args) {
    }

    public Textbook (String desc) {
        this.desc = desc;
    }

    public String[] getInfo() {
        return new String[]{desc}; // TODO
    }

    public String getTitle() {
        return desc; // TODO
    }
}
