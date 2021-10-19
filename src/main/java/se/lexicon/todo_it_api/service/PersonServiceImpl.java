package se.lexicon.todo_it_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.todo_it_api.data.PersonDAO;
import se.lexicon.todo_it_api.data.TodoItemDAO;
import se.lexicon.todo_it_api.exception.AppResourceNotFoundException;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.forms.PersonFormDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{


    private final PersonDAO personDao;
    private final TodoItemDAO todoItemDao;
    private final ConversionService convert;

    @Autowired
    public PersonServiceImpl(PersonDAO personDao,TodoItemDAO todoItemDao, ConversionService convert) {
        this.personDao = personDao;
        this.todoItemDao = todoItemDao;
        this.convert = convert;
    }


    @Override
    public PersonDto create(PersonFormDto form) {

        Person saved = personDao.save(convert.toPerson(form));

        return convert.toPersonDto(saved);

    }

    @Override
    public boolean delete(Integer personId) {

        personDao.deleteById(personId);

        return !personDao.existsById(personId);
//        return personDao.findById(personId).isPresent();
    }

    @Override
    @Transactional
    public PersonDto update(Integer personId, PersonFormDto updateForm) {

        Optional<Person> foundPerson = personDao.findById(personId);

        if (foundPerson.isPresent()){
            foundPerson.get().setFirstName(updateForm.getFirstName());
            foundPerson.get().setLastName(updateForm.getLastName());
            foundPerson.get().setBirthDate(updateForm.getBirthDate());

        }

        if (foundPerson.isPresent()){
            return convert.toPersonDto(foundPerson.get());
        } else {
            throw new IllegalArgumentException("Could not find By Id");
        }


    }

    @Override
    public PersonDto findById(Integer personId) {
        Optional<Person> foundById = personDao.findById(personId);
        Person person = foundById.orElseThrow(() -> new AppResourceNotFoundException("Could not find Person By Id " + personId));
        return convert.toPersonDto(person);
    }

    @Override
    public List<PersonDto> findAll() {

        List<Person> peopleFound = (List<Person>) personDao.findAll();

        List<PersonDto> peopleDtoList = new ArrayList<>();

        peopleFound.forEach( (person) -> peopleDtoList.add(convert.toPersonDto(person)) );

        return peopleDtoList;

    }

    @Override
    public List<PersonDto> findIdlePeople() {

        List<Person> idlePeople = personDao.findIdlePeople();

        List<PersonDto> peopleDtoList = new ArrayList<>();

        idlePeople.forEach(
                (person) -> peopleDtoList.add(convert.toPersonDto(person))
        );

        return peopleDtoList;
    }

    @Override
    public PersonDto addTodoItem(Integer personId, Integer todoItemId) {

        Person person = personDao.findById(personId).orElseThrow( () -> new AppResourceNotFoundException("person Not found"));
        TodoItem todoItem = todoItemDao.findById(todoItemId).orElseThrow( () -> new AppResourceNotFoundException("TodoItem Not found"));

            person.addTodoItem(todoItem);

        return convert.toPersonDto(person);
    }

    @Override
    public PersonDto removeTodoItem(Integer personId, Integer todoItemId) {

        Person person = personDao.findById(personId).orElseThrow( () -> new AppResourceNotFoundException("person Not found"));
        TodoItem todoItem = todoItemDao.findById(todoItemId).orElseThrow( () -> new AppResourceNotFoundException("TodoItem Not found"));

        person.removeTodoItem(todoItem);

        return convert.toPersonDto(person);

    }
}
