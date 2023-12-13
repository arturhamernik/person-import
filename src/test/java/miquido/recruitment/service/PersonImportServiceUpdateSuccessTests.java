package miquido.recruitment.service;

import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.common.PersonValidator;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {"person.exists.action=UPDATE"})
@ExtendWith(SpringExtension.class)
public class PersonImportServiceUpdateSuccessTests {

    @Mock
    PersonRepository personRepository;
    @Mock
    PersonValidator personValidator;
    @Mock
    PersonMapper personMapper;
    @Mock
    StarWarsApiRestTemplate starWarsApiRestTemplate;

    @Autowired
    @InjectMocks
    PersonImportService personImportService;

    private PersonEntity PERSON_ENTITY_1;
    private PersonSwApiDto PERSON_SWAPI_DTO_1;

    @BeforeEach
    public void setUpPeople() {
        PERSON_ENTITY_1 = PersonEntity.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();

        PERSON_SWAPI_DTO_1 = PersonSwApiDto.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height("177")
                .mass("70")
                .build();
    }

    @Test
    public void updatePerson_success() {
        when(starWarsApiRestTemplate.getPerson(anyLong())).thenReturn(PERSON_SWAPI_DTO_1);
        when(personMapper.entityFromSwApi(any(PersonSwApiDto.class))).thenReturn(PERSON_ENTITY_1);

        /* Import */
        personImportService.importPerson(PERSON_ENTITY_1.getId());

        /* Update */
        personImportService.importPerson(PERSON_ENTITY_1.getId());
    }
}

