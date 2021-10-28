package se.lexicon.todo_it_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.todo_it_api.data.TodoItemDAO;
import se.lexicon.todo_it_api.exception.AppResourceNotFoundException;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.forms.TodoItemFormDto;

import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class TodoItemServiceImpl implements TodoItemService{


    private final TodoItemDAO todoItemDAO;
    private final ConversionService convert;

    @Autowired
    public TodoItemServiceImpl(TodoItemDAO todoItemDAO, ConversionService convert) {
        this.todoItemDAO = todoItemDAO;
        this.convert = convert;
    }

    @Override
    @Transactional
    public TodoItemDto create(TodoItemFormDto form) {
        TodoItem entity = convert.toTodoItem(form);

        TodoItem save = todoItemDAO.save(entity);

        return convert.toTodoItemDto(save);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoItemDto findById(Integer id) {
        return convert.toTodoItemDto(todoItemDAO.findById(id).orElseThrow(()-> new AppResourceNotFoundException("Could not find TodoItem with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findAll() {
        List<TodoItem> allFound = (List<TodoItem>) todoItemDAO.findAll();
        return allFound.stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findAllUnassigned() {
        return todoItemDAO.findUnassignedTodoItems().stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findAllByPersonId(Integer personId) {
        return todoItemDAO.findByPersonId(personId).stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findByDoneStatus(Boolean doneStatus) {
        return todoItemDAO.findByDoneStatus(doneStatus).stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findByDeadlineBetween(LocalDate start, LocalDate end) {

        List<TodoItem> found = todoItemDAO.findByDeadlineBetween(start, end);

        return found.stream()
                .map((todoItem -> convert.toTodoItemDto(todoItem)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findByDeadlineBefore(LocalDate localDate) {
        return todoItemDAO.findByDeadLineBefore(localDate).stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findByDeadlineAfter(LocalDate localDate) {
        return todoItemDAO.findByDeadlineAfter(localDate).stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findByTitle(String title) {
        return todoItemDAO.findByTitleContains(title).stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoItemDto> findAllUnfinishedAndOverdue() {
        return todoItemDAO.findAllUnfinishedAndOverdue().stream()
                .map(convert::toTodoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TodoItemDto update(Integer id, TodoItemFormDto form) {

        TodoItem found = todoItemDAO.findById(id).orElseThrow( () -> new AppResourceNotFoundException("could not find by id:" + id));

        found.setTitle(form.getTitle());
        found.setDescription(form.getDescription());
        found.setDeadLine(form.getDeadLine());
        found.setDone(form.isDone());

        return convert.toTodoItemDto(found);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        todoItemDAO.deleteById(id);
        return !todoItemDAO.existsById(id);
    }
}
