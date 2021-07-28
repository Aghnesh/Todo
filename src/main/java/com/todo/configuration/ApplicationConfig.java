package com.todo.configuration;

import com.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

    @Autowired
    TodoService todoService;

    @Value("${max.todo.create.allow:5}")
    public int maxTodoCreateAllow;

    public static int todoCreateAllow;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void calculateMaxAllow(){
        todoCreateAllow = maxTodoCreateAllow - (int)todoService.getCurrentDateInsertedCount();
    }
}
