package com.example.vaadincourse1.todo.repo;

import com.example.vaadincourse1.todo.model.Todo;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryRepository {

    private final List<Todo> items = new ArrayList<>();

    @PostConstruct
    void setup() {
        for (int i = 0; i < 10; i++) {
        addToItems(Todo.builder().title("Todo number" +i).body("Body number " +i).author("admin").createdAt(LocalDateTime.now()).build());
        }
    }

    public void addToItems(Todo todo) {
        items.add(todo);
    }

    public List<Todo> getAllItems(){
        return items;
    }


}
