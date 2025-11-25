package model;

public class TaskData {
    private final String title;
    private final String description;

    public TaskData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static TaskData random(String prefix) {
        String suffix = String.valueOf(System.currentTimeMillis());
        return new TaskData(prefix + " " + suffix, "Описание " + suffix);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
