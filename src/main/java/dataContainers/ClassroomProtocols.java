package dataContainers;

public class ClassroomProtocols implements SyllabusEntity {
    String text;
    public ClassroomProtocols (String text) {
        this.text = text;
    }

    public String[] getInfo() {
        return new String[] {text};
    }

    public String getTitle() {
        return text;
    }
}
