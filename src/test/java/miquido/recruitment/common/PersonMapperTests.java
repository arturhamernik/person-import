package miquido.recruitment.common;

import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PersonMapperTests {

    @InjectMocks
    private PersonMapper personMapper;

    private PersonEntity PERSON_ENTITY_1;
    private PersonDto PERSON_DTO_1;
    private PersonSwApiDto PERSON_SWAPI_1;

    @BeforeEach
    public void setupPerson() {
        Long id = 1L;
        String name = "Anakin Skywalker", height = "177", mass = "70";

        PERSON_ENTITY_1 = PersonEntity.builder()
                .id(id)
                .name(name)
                .height(new BigDecimal(height))
                .mass(new BigDecimal(mass))
                .build();

        PERSON_DTO_1 = PersonDto.builder()
                .id(id)
                .name(name)
                .height(new BigDecimal(height))
                .mass(new BigDecimal(mass))
                .build();

        PERSON_SWAPI_1 = PersonSwApiDto.builder()
                .id(id)
                .name(name)
                .height(height)
                .mass(mass)
                .build();
    }

    @Test
    public void map_Dto_from_Entity_success() {
        PersonDto personDto = personMapper.dtoFromEntity(PERSON_ENTITY_1);

        assertEquals(PERSON_DTO_1.getId(), personDto.getId());
        assertEquals(PERSON_DTO_1.getName(), personDto.getName());
        assertEquals(PERSON_DTO_1.getHeight(), personDto.getHeight());
        assertEquals(PERSON_DTO_1.getMass(), personDto.getMass());
    }

    @Test
    public void map_Entity_from_SwApiDto_success() {
        PersonEntity personEntity = personMapper.entityFromSwApi(PERSON_SWAPI_1);

        assertEquals(PERSON_ENTITY_1.getId(), personEntity.getId());
        assertEquals(PERSON_ENTITY_1.getName(), personEntity.getName());
        assertEquals(PERSON_ENTITY_1.getHeight(), personEntity.getHeight());
        assertEquals(PERSON_ENTITY_1.getMass(), personEntity.getMass());
    }
}
