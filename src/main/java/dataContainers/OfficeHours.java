package dataContainers;

public class OfficeHours implements DatedSyllabusEntity{
    private String date;
    public static void main(String[] args) {

    }
    public OfficeHours (String date) {
        this.date = date;
    }

    public String getDueDate() {
        return date;
    }

    public void setDueDate(String date) {
        this.date = date;
    }

    public String[] getInfo() {
        return new String[0];
    }
    public String getTitle() {
        return null;
    }
    public String getLocation () {
        return "somewhere";
    }
}
