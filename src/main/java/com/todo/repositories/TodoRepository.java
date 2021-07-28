package com.todo.repositories;

import com.todo.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {

    Optional<Todo> findByName(String name);

    void deleteByName(String name);
}
