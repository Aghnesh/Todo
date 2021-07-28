package com.todo.service;

import com.todo.entities.Todo;
import com.todo.exception.NotFoundException;
import com.todo.modal.TodoDTO;

import java.util.List;

public interface TodoService {

    List<TodoDTO> getAllTodo();

    TodoDTO searchTodo(String name) throws NotFoundException;

    void addTodo(Todo todo);

    void deleteTodoByName(String name) throws NotFoundException;

    boolean updateTodo(Todo todo);

    long getCurrentDateInsertedCount();
}
