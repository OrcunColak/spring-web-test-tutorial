package com.colak.springwebtesttutorial.controller;

import com.colak.springwebtesttutorial.dto.Person;
import com.colak.springwebtesttutorial.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void testGetPersonById() throws Exception {
        // Mocking the service response
        Person mockPerson = new Person();
        mockPerson.setId(1L);
        mockPerson.setFirstName("John");
        mockPerson.setLastName("Doe");
        when(personService.getPersonById(1L)).thenReturn(mockPerson);

        // Performing the MVC request
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/people/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testSavePerson() throws Exception {
        // Creating a new person
        Person newPerson = new Person();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Smith");

        // Mocking the service save operation
        when(personService.savePerson(Mockito.any())).thenReturn(newPerson);

        // Performing the MVC request
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newPerson)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"));
    }

    // Helper method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

