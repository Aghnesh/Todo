package com.todo.service.impl;

import com.google.gson.reflect.TypeToken;
import com.todo.configuration.ApplicationConfig;
import com.todo.entities.Todo;
import com.todo.exception.NotFoundException;
import com.todo.modal.TodoDTO;
import com.todo.repositories.TodoRepository;
import com.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TodoDTO> getAllTodo() {
        return modelMapper.map(todoRepository.findAll(), new TypeToken<List<TodoDTO>>() {
        }.getType());
    }

    @Override
    public TodoDTO searchTodo(String name) throws NotFoundException {

        Optional<Todo> todo = todoRepository.findByName(name);
        if (todo.isPresent()) {
            return modelMapper.map(todo.get(), TodoDTO.class);
        } else {
            log.error("Todo name {} not found in TODO", name);
            throw new NotFoundException("Todo name {" + name + "} not found in TODO");
        }
    }

    @Override
    public void addTodo(Todo todo) {
        todoRepository.save(todo);
        ApplicationConfig.todoCreateAllow--;
    }

    @Override
    @Transactional
    public void deleteTodoByName(String name) throws NotFoundException {
        Optional<Todo> todo = todoRepository.findByName(name);
        if (todo.isPresent()) {
            todoRepository.deleteByName(name);
            ApplicationConfig.todoCreateAllow++;
            log.info("Successfully delete Todo by name {}", name);
        } else {
            log.error("Todo name {} not found in TODO", name);
            throw new NotFoundException("Todo name {" + name + "} not found in TODO");
        }
    }

    @Override
    public boolean updateTodo(Todo todo) {
        Optional<Todo> responseTodo = todoRepository.findByName(todo.getName());
        if (responseTodo.isPresent()) {
            todo.setId(responseTodo.get().getId());
            todo.setCreated(responseTodo.get().getCreated());
            return todoRepository.save(todo).getId() == 1;
        }
        return false;
    }

    @Override
    public long getCurrentDateInsertedCount(){
        List<TodoDTO> todos = getAllTodo();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return todos.stream().map(TodoDTO::getCreated).map(Date::getDay).filter(day -> day == dayOfWeek).count();
    }
}
