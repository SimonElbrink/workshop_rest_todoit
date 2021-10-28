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

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/todo/api/v1/people")
@CrossOrigin("*")
public class PersonController {

    private final PersonService personService;
    private final TodoItemService todoItemService;

    @Autowired
    public PersonController(PersonService personService, TodoItemService todoItemService) {
        this.personService = personService;
        this.todoItemService = todoItemService;
    }


    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody @Valid PersonFormDto creationForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(creationForm));
    }


    @GetMapping
    public ResponseEntity<?> find(@RequestParam(value = "search", defaultValue = "all") String search) throws IllegalArgumentException {

        switch (search.toLowerCase()) {
            case "idle": return findIdlePerson();
            case "all": return findAll();

            default: throw new IllegalArgumentException("Invalid search Param: valid Params Are: all, idle");
        }
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") Integer id) {


        return ResponseEntity.ok(personService.findById(id));
    }



    public ResponseEntity<Collection<PersonDto>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }


    public ResponseEntity<Collection<PersonDto>> findIdlePerson() {
        return ResponseEntity.ok(personService.findIdlePeople());
    }


    @GetMapping(path = "/{id}/todos")
    public ResponseEntity<Collection<TodoItemDto>> getTodoItems(@PathVariable("id") Integer personId) {
        return ResponseEntity.ok(todoItemService.findAllByPersonId(personId));
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Integer personId) {

        boolean delete = personService.delete(personId);
        return ResponseEntity.ok(delete ? "Person with id " + personId + " was deleted" : "Person Not Deleted");
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable("id") Integer personId, @RequestBody @Valid PersonFormDto updateForm) {
        return ResponseEntity.ok(personService.update(personId,updateForm));
    }


    @PutMapping("/{id}/todos/add")
    public ResponseEntity<PersonDto> assignTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoId") Integer todoItemId) {
        return ResponseEntity.ok(personService.addTodoItem(personId, todoItemId));
    }


    @PutMapping("/{id}/todos/remove")
    public ResponseEntity<PersonDto> removeTodoItem(@PathVariable("id") Integer personId, @RequestParam("todoId") Integer todoItemId) {
        return ResponseEntity.ok(personService.removeTodoItem(personId, todoItemId));
    }
}
