package miquido.recruitment.service;

import miquido.recruitment.dto.PersonSwApiDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StarWarsRestApiTemplateTests {

    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    StarWarsApiRestTemplate starWarsApiRestTemplate;
    private PersonSwApiDto PERSON_SWAPI_DTO_1;
    private String URL;
    @BeforeEach
    public void setUpPeople() {
        PERSON_SWAPI_DTO_1 = PersonSwApiDto.builder()
                .id(1L)
                .name("Anakin Skywalker")
                .height("177")
                .mass("70")
                .build();
        URL = "https://swapi.dev/api/people/{id}?format={format}";
    }

    @Test
    public void getPerson_fromSwApi_success() {
        when(restTemplate.getForObject(URL, PersonSwApiDto.class,
                Map.of("id", PERSON_SWAPI_DTO_1.getId(), "format", "json"))).thenReturn(PERSON_SWAPI_DTO_1);

        PersonSwApiDto personSwApiDto = starWarsApiRestTemplate.getPerson(PERSON_SWAPI_DTO_1.getId());

        assertNotNull(personSwApiDto);
        assertEquals(PERSON_SWAPI_DTO_1.getId(), personSwApiDto.getId());
    }

    @Test
    public void getPerson_fromSwApi_notFound_fail() {
        when(restTemplate.getForObject(URL, PersonSwApiDto.class,
                Map.of("id", PERSON_SWAPI_DTO_1.getId(), "format", "json")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Msg", new HttpHeaders(), null, null));

        try {
            PersonSwApiDto personSwApiDto = starWarsApiRestTemplate.getPerson(PERSON_SWAPI_DTO_1.getId());
        } catch (Exception e) {
            assertEquals("Person not found!", e.getMessage());
        }
    }

    @Test
    public void getPerson_fromSwApi_SwApi_fail() {
        when(restTemplate.getForObject(URL, PersonSwApiDto.class,
                Map.of("id", PERSON_SWAPI_DTO_1.getId(), "format", "json")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Msg", new HttpHeaders(), null, null));

        try {
            PersonSwApiDto personSwApiDto = starWarsApiRestTemplate.getPerson(PERSON_SWAPI_DTO_1.getId());
        } catch (Exception e) {
            assertEquals("Something went wrong when calling SwApi!", e.getMessage());
        }
    }
}
