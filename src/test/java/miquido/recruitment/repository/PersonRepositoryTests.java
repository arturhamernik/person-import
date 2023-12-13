package miquido.recruitment.repository;

import miquido.recruitment.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;
    private PersonEntity PERSON_ENTITY_1;

    @BeforeEach
    public void setupPerson() {
        PERSON_ENTITY_1 = PersonEntity.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height(new BigDecimal("177"))
                .mass(new BigDecimal("70"))
                .build();
    }

    @Test
    public void savePerson_ReturnSavedPerson() {
        PersonEntity savedPerson = personRepository.save(PERSON_ENTITY_1);

        assertNotNull(savedPerson);
        assertEquals(PERSON_ENTITY_1.getId(), savedPerson.getId());
    }

    @Test
    public void savePerson_findPersonById_success() {
        personRepository.save(PERSON_ENTITY_1);

        Optional<PersonEntity> personEntity = personRepository.findById(PERSON_ENTITY_1.getId());

        assertTrue(personEntity.isPresent());
        assertEquals(PERSON_ENTITY_1.getId(), personEntity.get().getId());
    }

    @Test
    public void findPersonById_fail() {
        Optional<PersonEntity> personEntity = personRepository.findById(PERSON_ENTITY_1.getId());

        assertTrue(personEntity.isEmpty());
    }

    @Test
    public void savePerson_findPersonByName_success() {
        personRepository.save(PERSON_ENTITY_1);

        List<PersonEntity> personEntities = personRepository.findAllByNameContainingIgnoreCase("sky");

        assertNotNull(personEntities);
        assertEquals(1, personEntities.size());
        assertEquals(PERSON_ENTITY_1.getId(), personEntities.get(0).getId());
    }

    @Test
    public void findPersonByName_fail() {
        List<PersonEntity> personEntities = personRepository.findAllByNameContainingIgnoreCase("obi");

        assertEquals(0, personEntities.size());
    }
}
