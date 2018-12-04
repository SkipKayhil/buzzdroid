package io.github.skipkayhil.buzz.model;

public class GTClass {

    private String title;
    private String department;
    private int classNumber;
    private String classURL;
    private Semester semester;
    private int year;

    public enum Semester {
        FALL, SUMMER, SPRING
    }

    public GTClass(String html) {
        parse(html);
    }

    private void parse(String html) {
        title = html.split("\"")[3];
        department = title.startsWith("Former")
                ? title.split(" ")[1].split("-")[0]
                : title.substring(0, title.indexOf("-"));
        classNumber = Integer.valueOf(title.split("-")[1].split(" ")[0]);
        classURL = html.split("\"")[1];

        String[] getParts = title.split(" ");
        if (getParts.length > 2) {
            String lastPart = getParts[getParts.length - 1];

            switch (lastPart.substring(0, lastPart.length() - 2)) {
                case "SPR":
                    semester = Semester.SPRING;
                    break;
                case "FALL":
                    semester = Semester.FALL;
                    break;
                // not sure what Summer code is
            }
            year = Integer.valueOf(lastPart.substring(lastPart.length() - 2, lastPart.length()));
        } else {
            // TODO: don't hard code these lol
            semester = Semester.SUMMER;
            year = 17;
        }
    }

    @Override
    public String toString() {
        return department + " " + classNumber + " " + semester + " " + year + " " + classURL;
    }
}
