package se.lexicon.todo_it_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.forms.PersonFormDto;

import javax.validation.Valid;
import java.util.Collection;

public interface PersonController {
    @PostMapping
    ResponseEntity<PersonDto> create(@RequestBody @Valid PersonFormDto creationForm);

    @GetMapping
    ResponseEntity<?> find(@RequestParam(value = "search", defaultValue = "all") String search);

    @GetMapping(path = "/{id}")
    ResponseEntity<PersonDto> findById(@PathVariable("id") Integer id);

    ResponseEntity<Collection<PersonDto>> findAll();

    ResponseEntity<Collection<PersonDto>> findIdlePerson();

    @PutMapping(path = "/{id}")
    ResponseEntity<PersonDto> update(@PathVariable("id") Integer personId, @RequestBody @Valid PersonFormDto updateForm);

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deletePerson(@PathVariable("id") Integer personId);

    @GetMapping(path = "/{id}/todos")
    ResponseEntity<Collection<TodoItemDto>> getTodoItems(@PathVariable("id") Integer personId);

    @GetMapping("/todo/api/v1/people/{id}/add")
    ResponseEntity<PersonDto> assignTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoItemId") Integer todoItemId);

    @GetMapping("/todo/api/v1/people/{id}/remove")
    ResponseEntity<PersonDto> removeTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoItemId") Integer todoItemId);
}
