package com.todo.entities;

import com.todo.constants.Constant;
import com.todo.constants.Priority;
import com.todo.constants.TodoStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "todo")
@ToString
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Size(min = 5, max = 25,message = "Todo name should have minimum 5 characters and maximum 25 chars")
    @Pattern(regexp = "^[A-Za-z]\\w*$", message = "Todo name should be alphanumeric")
    private String name;

    @Column(length = 15, columnDefinition = "varchar(15) default 'QUEUE'")
    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.QUEUE;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, columnDefinition = "varchar(10) default 'LOW'")
    private Priority priority = Priority.LOW;

    private Date created;

    private Date updated;

    @PrePersist
    private void setCreated() {
        this.created = new Date();
    }

    @PreUpdate
    private void setUpdated(){
        this.updated = new Date();
    }
}
