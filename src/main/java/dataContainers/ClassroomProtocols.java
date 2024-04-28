package dataContainers;

public class ClassroomProtocols implements SyllabusEntities {
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
