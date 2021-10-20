package se.lexicon.todo_it_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.forms.TodoItemFormDto;
import se.lexicon.todo_it_api.service.TodoItemService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/todo/api/v1/todos")
@CrossOrigin("*")
public class TodoItemControllerImpl implements TodoItemController {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemControllerImpl(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    private final List<String> searchTypes = Arrays.asList(
            "all", "unassigned", "done_status", "between", "before", "after", "title", "late"
    );

    @Override
    @PostMapping
    public ResponseEntity<TodoItemDto> create(@RequestBody TodoItemFormDto form){
        TodoItemDto todoItemDTO = todoItemService.create(form);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(todoItemDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<TodoItemDto>> find(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "values", defaultValue = "all") String[] values)
    {

        List<TodoItemDto> todoItemDto;

        switch (search){
            case "all":
                todoItemDto = todoItemService.findAll();
                break;
            case "unassigned":
                todoItemDto = todoItemService.findAllUnassigned();
                break;
            case "done_status":
                boolean doneStatus = Boolean.parseBoolean(values[0]);
                todoItemDto = todoItemService.findByDoneStatus(doneStatus);
                break;
            case "between":
                List<LocalDate> dateValues = Stream.of(values)
                        .map(LocalDate::parse)
                        .collect(Collectors.toList());

                if(dateValues.size() != 2) throw new IllegalArgumentException("Invalid params: expected 2 params. Actual param(s) were " + dateValues);
                LocalDate start = dateValues.get(0);
                LocalDate end = dateValues.get(1);
                todoItemDto = todoItemService.findByDeadlineBetween(start, end);
                break;
            case "before":
                LocalDate before = LocalDate.parse(Objects.requireNonNull(values[0]));
                todoItemDto = todoItemService.findByDeadlineBefore(before);
                break;
            case "after":
                LocalDate after = LocalDate.parse(Objects.requireNonNull(values[0]));
                todoItemDto = todoItemService.findByDeadlineAfter(after);
                break;
            case "late":
                todoItemDto = todoItemService.findAllUnfinishedAndOverdue();
                break;
            case "title":
                String title = values[0];
                todoItemDto = todoItemService.findByTitle(title);
                break;
            default:
                throw new IllegalArgumentException("Invalid search type '"+ search+"' valid types are: " + searchTypes);
        }

        return ResponseEntity.ok(todoItemDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDto> findById(@PathVariable("id") Integer id){
        TodoItemDto todoItemDto = todoItemService.findById(id);
        return ResponseEntity.ok(todoItemDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TodoItemDto> update(@PathVariable("id") Integer id, @RequestBody TodoItemFormDto form){
        TodoItemDto todoItemDto = todoItemService.update(id, form);
        return ResponseEntity.ok(todoItemDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        boolean deleted = todoItemService.delete(id);
        return ResponseEntity.ok(deleted ? "TodoItem with id " + id + " was deleted" : "TodoItem with id " + id + " was not deleted");
    }
}
