package miquido.recruitment.service;

import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.common.PersonValidator;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.enums.PersonExistsAction;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.exception.SwApiException;
import miquido.recruitment.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonImportServiceTests {

    @Mock
    PersonExistsAction personExistsAction;
    @Mock
    PersonRepository personRepository;
    @Mock
    PersonValidator personValidator;
    @Mock
    PersonMapper personMapper;
    @Mock
    StarWarsApiRestTemplate starWarsApiRestTemplate;
    @InjectMocks
    PersonImportService personImportService;

    private PersonEntity PERSON_ENTITY_1;
    private PersonEntity PERSON_ENTITY_2;
    private PersonSwApiDto PERSON_SWAPI_DTO_1;
    private PersonSwApiDto PERSON_SWAPI_DTO_2;

    @BeforeEach
    public void setUpPeople() {
        PERSON_ENTITY_1 = PersonEntity.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();

        PERSON_ENTITY_2 = PersonEntity.builder()
                .id(2L)
                .name("Yoda")
                .height(new BigDecimal("60"))
                .mass(new BigDecimal("20"))
                .build();

        PERSON_SWAPI_DTO_1 = PersonSwApiDto.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height("177")
                .mass("70")
                .build();

        PERSON_SWAPI_DTO_2 = PersonSwApiDto.builder()
                .id(2L)
                .name("Yoda")
                .height("60")
                .mass("20")
                .build();
    }

    @Test
    public void importPerson_success() {
        when(starWarsApiRestTemplate.getPerson(anyLong())).thenReturn(PERSON_SWAPI_DTO_1);
        when(personMapper.entityFromSwApi(any(PersonSwApiDto.class))).thenReturn(PERSON_ENTITY_1);

        personImportService.importPerson(PERSON_ENTITY_1.getId());
    }

    @Test
    public void importPerson_notFound_fail() {
        when(starWarsApiRestTemplate.getPerson(anyLong())).thenThrow(new NotFoundException("Person not found!"));

        try {
            personImportService.importPerson(PERSON_ENTITY_1.getId());
        } catch (Exception e) {
            assertEquals("Person not found!", e.getMessage());
        }
    }

    @Test
    public void importPerson_swapi_fail() {
        when(starWarsApiRestTemplate.getPerson(anyLong())).thenThrow(new SwApiException("Something went wrong when calling SwApi!"));

        try {
            personImportService.importPerson(PERSON_ENTITY_1.getId());
        } catch (Exception e) {
            assertEquals("Something went wrong when calling SwApi!", e.getMessage());
        }
    }

    @Test
    public void importPerson_heightValidation_fail() {
        try {
            when(starWarsApiRestTemplate.getPerson(anyLong())).thenReturn(PERSON_SWAPI_DTO_2);
            when(personMapper.entityFromSwApi(any(PersonSwApiDto.class))).thenReturn(PERSON_ENTITY_2);

            personImportService.importPerson(PERSON_ENTITY_2.getId());
        } catch (Exception e) {
            assertEquals("Can't import! Person's height is under threshold!", e.getMessage());
        }
    }
}
