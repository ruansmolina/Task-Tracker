package model;

public enum Status {
    TO_DO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String description;
    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
