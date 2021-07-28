package com.todo.controller;

import com.todo.configuration.ApplicationConfig;
import com.todo.constants.Constant;
import com.todo.entities.Todo;
import com.todo.exception.NotFoundException;
import com.todo.modal.TodoDTO;
import com.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value="/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDTO searchTodo(@PathVariable("name") String name) {
        log.info("Action={}, task name={}", Constant.TodoAction.READ_TODO, name);
        try {
            return todoService.searchTodo(name);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDTO> searchTodo() {
        log.info("Action={}", Constant.TodoAction.READ_TODO);
        try {
            return todoService.getAllTodo();
        }  catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Constant.Status addTodo(@Valid @RequestBody Todo todo) {
        log.info("Action={}, task name={}", Constant.TodoAction.CREATE_TODO, todo.getName());
        try {
            todoService.searchTodo(todo.getName());
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Todo name is already available");
        }  catch (NotFoundException e) {
            if(ApplicationConfig.todoCreateAllow == 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Reach todo creation threshold.");
            }else{
                todoService.addTodo(todo);
                return Constant.Status.SUCCESS;
            }

        }catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, ex.getReason(), ex);
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Constant.Status updateTodo(@Valid @RequestBody Todo todo) {
        log.info("Action={}, task name={}", Constant.TodoAction.UPDATE_TODO, todo.getName());
        try {
            todoService.updateTodo(todo);
            return Constant.Status.SUCCESS;
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Constant.Status deleteTodo(@RequestParam("name") String name) {
        log.info("Action={}, task name={}", Constant.TodoAction.DELETE_TODO, name);
        try {
            todoService.deleteTodoByName(name);
            return Constant.Status.SUCCESS;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
