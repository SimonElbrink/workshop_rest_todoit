package se.lexicon.todo_it_api.service;

import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.dto.PersonDtoSmall;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.dto.TodoItemDtoSmall;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.forms.PersonFormDto;
import se.lexicon.todo_it_api.model.forms.TodoItemFormDto;

import java.util.ArrayList;
import java.util.List;

public class ConversionService {


    public Person toPerson(PersonFormDto formDto){
        return new Person(0,formDto.getFirstName(), formDto.getLastName(), formDto.getBirthDate(), new ArrayList<>());
    }

    public TodoItem toTodoItem(TodoItemFormDto formDto){
        return new TodoItem(0, formDto.getTitle(), formDto.getDescription(),formDto.getDeadLine(), formDto.isDone(), null);
    }

    public TodoItemDto toTodoItemDto (TodoItem todoItem){

        Person person = todoItem.getAssignee();

        PersonDtoSmall personDtoSmall = new PersonDtoSmall(person.getPersonId(), person.getFirstName(), person.getLastName(), person.getBirthDate());

        TodoItemDto todoItemDto = new TodoItemDto(todoItem.getTodoId(), todoItem.getTitle(), todoItem.getDescription(), todoItem.getDeadLine(), todoItem.isDone(), personDtoSmall);

        return todoItemDto;
    }

    public PersonDto toPersonDto(Person person){

        List<TodoItemDtoSmall> list = new ArrayList<>();

        for (TodoItem item: person.getTodoItems()){
            list.add(new TodoItemDtoSmall(item.getTodoId(),item.getTitle(), item.getDescription(), item.getDeadLine(), item.isDone()));
        }

        PersonDto personDto = new PersonDto(person.getPersonId(), person.getFirstName(), person.getLastName(), person.getBirthDate(),list);
        return personDto;
    }






}
