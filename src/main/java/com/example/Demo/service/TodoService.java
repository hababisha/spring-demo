package com.example.Demo.service;

import com.example.Demo.entity.Todo;
import com.example.Demo.entity.User;
import com.example.Demo.repository.TodoRepository;
import com.example.Demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(
            TodoRepository todoRepository,
            UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    private Long getCurrentUserId() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (Long) authentication.getDetails();
    }

    public List<Todo> getTodos() {

        Long userId = getCurrentUserId();

        return todoRepository.findByUserId(userId);
    }

    public Todo createTodo(Todo todo) {

        Long userId = getCurrentUserId();

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUser(user);

        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long todoId, Todo updatedTodo) {

        Long userId = getCurrentUserId();

        Todo todo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setTitle(updatedTodo.getTitle());
        todo.setCompleted(updatedTodo.isCompleted());

        return todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {

        Long userId = getCurrentUserId();

        Todo todo = todoRepository
                .findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todoRepository.delete(todo);
    }
}
