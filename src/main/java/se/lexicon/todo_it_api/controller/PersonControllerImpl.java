package se.lexicon.todo_it_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.forms.PersonFormDto;
import se.lexicon.todo_it_api.service.PersonService;
import se.lexicon.todo_it_api.service.TodoItemService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/todo/api/v1/people")
@CrossOrigin("*")
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;
    private final TodoItemService todoItemService;

    @Autowired
    public PersonControllerImpl(PersonService personService, TodoItemService todoItemService) {
        this.personService = personService;
        this.todoItemService = todoItemService;
    }

    @Override
    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonFormDto creationForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(creationForm));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> find(@RequestParam(value = "search", defaultValue = "all") String search) {

        switch (search.toLowerCase()) {
            case "idle": return findIdlePerson();
            case "all": return findAll();

            default: throw new IllegalArgumentException("Invalid search Param: valid Params Are: all, idle");
        }
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(personService.findById(id));
    }


    @Override
    public ResponseEntity<Collection<PersonDto>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @Override
    public ResponseEntity<Collection<PersonDto>> findIdlePerson() {
        return ResponseEntity.ok(personService.findIdlePeople());
    }

    @Override
    @GetMapping(path = "/{id}/todos")
    public ResponseEntity<Collection<TodoItemDto>> getTodoItems(@PathVariable("id") Integer personId) {
        return ResponseEntity.ok(todoItemService.findAllByPersonId(personId));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Integer personId) {

        boolean delete = personService.delete(personId);
        return ResponseEntity.ok(delete ? "Person was Delete" : "Person Not Delete");
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable("id") Integer personId, @RequestBody PersonFormDto updateForm) {
        return ResponseEntity.ok(personService.update(personId,updateForm));
    }

    @Override
    @GetMapping("/todo/api/v1/people/{id}/add")
    public ResponseEntity<PersonDto> assignTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoItemId") Integer todoItemId) {
        return ResponseEntity.ok(personService.addTodoItem(personId, todoItemId));
    }

    @Override
    @GetMapping("/todo/api/v1/people/{id}/remove")
    public ResponseEntity<PersonDto> removeTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoItemId") Integer todoItemId) {
        return ResponseEntity.ok(personService.removeTodoItem(personId, todoItemId));
    }
}
