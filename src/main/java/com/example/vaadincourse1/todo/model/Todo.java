package com.example.vaadincourse1.todo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Todo {

    private LocalDateTime createdAt;
    private String title;
    private String body;
    private String author;
    private boolean closed = false;

}
