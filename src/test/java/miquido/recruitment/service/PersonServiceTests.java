package miquido.recruitment.service;

import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @Mock
    PersonRepository personRepository;
    @Mock
    PersonMapper personMapper;
    @InjectMocks
    PersonService personService;

    private PersonEntity PERSON_ENTITY_1;
    private PersonEntity PERSON_ENTITY_2;
    private PersonEntity PERSON_ENTITY_3;
    private PersonDto PERSON_DTO_1;
    private PersonDto PERSON_DTO_2;
    private PersonDto PERSON_DTO_3;

    @BeforeEach
    public void setUpPeople() {
        PERSON_ENTITY_1 = PersonEntity.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();

        PERSON_DTO_1 = PersonDto.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();

        PERSON_ENTITY_2 = PersonEntity.builder()
                .id(2L)
                .name("Luke Skywalker")
                .height(new BigDecimal("170"))
                .mass(new BigDecimal("63"))
                .build();

        PERSON_DTO_2 = PersonDto.builder()
                .id(2L)
                .name("Luke Skywalker")
                .height(new BigDecimal("170"))
                .mass(new BigDecimal("63"))
                .build();

        PERSON_ENTITY_3 = PersonEntity.builder()
                .id(3L)
                .name("Leia")
                .height(new BigDecimal("165"))
                .mass(new BigDecimal("59"))
                .build();

        PERSON_DTO_2 = PersonDto.builder()
                .id(3L)
                .name("Leia")
                .height(new BigDecimal("165"))
                .mass(new BigDecimal("59"))
                .build();
    }

    @Test
    public void getPerson_success() {
        when(personRepository.findById(PERSON_ENTITY_1.getId())).thenReturn(Optional.of(PERSON_ENTITY_1));
        when(personMapper.dtoFromEntity(PERSON_ENTITY_1)).thenReturn(PERSON_DTO_1);

        PersonDto personFound = personService.findPersonById(PERSON_ENTITY_1.getId());

        assertNotNull(personFound);
        assertEquals(PERSON_DTO_1.getId(), personFound.getId());
    }

    @Test
    public void getPerson_fail() {
        try {
            when(personRepository.findById(anyLong())).thenReturn(Optional.empty());
            PersonDto personNotFound = personService.findPersonById(anyLong());
        } catch (Exception e) {
            assertEquals("Person not found!", e.getMessage());
        }
    }

    @Test
    public void findOnePerson_success() {
        String name = "lei";
        when(personRepository.findAllByNameContainingIgnoreCase(name)).thenReturn(List.of(PERSON_ENTITY_3));
        when(personMapper.dtoFromEntity(PERSON_ENTITY_3)).thenReturn(PERSON_DTO_3);

        List<PersonDto> peopleFound = personService.findPersonByName(name);

        assertNotNull(peopleFound);
        assertEquals(1, peopleFound.size());
    }

    @Test
    public void findMultiplePerson_success() {
        String name = "sky";
        when(personRepository.findAllByNameContainingIgnoreCase(name)).thenReturn(List.of(PERSON_ENTITY_1, PERSON_ENTITY_2));
        when(personMapper.dtoFromEntity(PERSON_ENTITY_1)).thenReturn(PERSON_DTO_1);
        when(personMapper.dtoFromEntity(PERSON_ENTITY_2)).thenReturn(PERSON_DTO_2);

        List<PersonDto> peopleFound = personService.findPersonByName(name);

        assertNotNull(peopleFound);
        assertEquals(2, peopleFound.size());
    }

    @Test
    public void findPerson_fail() {
        try {
            String name = "solo";
            when(personRepository.findAllByNameContainingIgnoreCase(name)).thenReturn(new ArrayList<>());

            List<PersonDto> peopleNotFound = personService.findPersonByName(name);
        } catch (Exception e) {
            assertEquals("Person not found!", e.getMessage());
        }
    }

    @Test
    public void findPerson_TooFewCharacters_fail() {
        try {
            List<PersonDto> peopleNotFound = personService.findPersonByName("a");
        } catch (Exception e) {
            assertEquals("Name too short, put at least three characters!", e.getMessage());
        }
    }
}
