package com.colak.springwebtesttutorial.service;

import com.colak.springwebtesttutorial.dto.Person;
import com.colak.springwebtesttutorial.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService {

    private final Map<Long, Person> personRepository = new HashMap<>();

    public PersonService() {
        // Sample data
        Person person1 = new Person(1L, "John", "Doe");
        Person person2 = new Person(2L, "Jane", "Smith");

        // Populate the HashMap
        personRepository.put(person1.getId(), person1);
        personRepository.put(person2.getId(), person2);
    }

    public Person getPersonById(Long id) {
        return Optional.ofNullable(personRepository.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public Person savePerson(Person person) {
        return personRepository.put(person.getId(), person);
    }
}

