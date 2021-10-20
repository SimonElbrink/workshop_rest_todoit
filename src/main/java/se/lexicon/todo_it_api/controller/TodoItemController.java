package se.lexicon.todo_it_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.forms.TodoItemFormDto;

import javax.validation.Valid;
import java.util.Collection;

public interface TodoItemController {
    @PostMapping
    ResponseEntity<TodoItemDto> create(@RequestBody TodoItemFormDto form);

    @GetMapping
    ResponseEntity<Collection<TodoItemDto>> find(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "values", defaultValue = "all") String[] values);

    @GetMapping("/{id}")
    ResponseEntity<TodoItemDto> findById(@PathVariable("id") Integer id);

    @PutMapping("/{id}")
    ResponseEntity<TodoItemDto> update(@PathVariable("id") Integer id, @RequestBody TodoItemFormDto form);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") Integer id);
}
