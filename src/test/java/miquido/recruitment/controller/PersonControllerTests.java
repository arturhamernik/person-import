package miquido.recruitment.controller;

import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.exception.ValidationException;
import miquido.recruitment.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private PersonDto PERSON_1;
    private PersonDto PERSON_2;
    private PersonDto PERSON_3;

    @BeforeEach
    public void setUpPeople() {
        PERSON_1 = PersonDto.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();

        PERSON_2 = PersonDto.builder()
                .id(2L)
                .name("Luke Skywalker")
                .height(new BigDecimal("170"))
                .mass(new BigDecimal("63"))
                .build();

        PERSON_3 = PersonDto.builder()
                .id(3L)
                .name("Leia")
                .height(new BigDecimal("165"))
                .mass(new BigDecimal("59"))
                .build();
    }

    @Test
    public void getPerson_success() throws Exception {
        when(personService.findPersonById(anyLong())).thenReturn(PERSON_1);

        mockMvc.perform(get("/person/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Anakin Skywalker")));
    }

    @Test
    public void getPerson_fail() throws Exception {
        doThrow(new NotFoundException("Person not found!")).when(personService).findPersonById(anyLong());

        mockMvc.perform(get("/person/{id}", anyLong()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findOnePerson_success() throws Exception {
        when(personService.findPersonByName("lei")).thenReturn(List.of(PERSON_3));

        mockMvc.perform(get("/person").queryParam("name", "lei"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Leia")));
    }

    @Test
    public void findMultiplePerson_success() throws Exception {
        when(personService.findPersonByName("sky")).thenReturn(List.of(PERSON_1, PERSON_2));

        mockMvc.perform(get("/person").queryParam("name", "sky"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("Luke Skywalker")));
    }

    @Test
    public void findPerson_fail() throws Exception {
        doThrow(new NotFoundException("Person not found!")).when(personService).findPersonByName("Solo");

        mockMvc.perform(get("/person").queryParam("name", "Solo"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findPerson_TooFewCharacters_fail() throws Exception {
        doThrow(new ValidationException("Name too short, put at least three characters!"))
                .when(personService).findPersonByName(anyString());

        mockMvc.perform(get("/person").queryParam("name", anyString()))
                .andExpect(status().is4xxClientError());
    }
}
