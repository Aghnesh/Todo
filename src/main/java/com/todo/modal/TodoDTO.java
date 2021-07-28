package com.todo.modal;

import com.todo.constants.Priority;
import com.todo.constants.TodoStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class TodoDTO {
    private String name;
    private TodoStatus status;
    private Priority priority;
    private Date created;
    private Date updated;
}
