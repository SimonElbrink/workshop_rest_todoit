package se.lexicon.todo_it_api.model.dto;

import java.time.LocalDate;

public class TodoItemDtoSmall {

    private Integer todoId;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;

    public TodoItemDtoSmall() {
    }

    public TodoItemDtoSmall(Integer todoId, String title, String description, LocalDate deadLine, boolean done) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
