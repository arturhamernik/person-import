package miquido.recruitment.service;

import lombok.RequiredArgsConstructor;
import miquido.recruitment.enums.SwApiEndpoint;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.exception.SwApiException;
import miquido.recruitment.dto.PersonSwApiDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static miquido.recruitment.enums.SwApiEndpoint.PEOPLE;

@RequiredArgsConstructor
@Service
public class StarWarsApiRestTemplate {

    private final RestTemplate restTemplate;
    private final String SW_API_URL = "https://swapi.dev/api/";

    public PersonSwApiDto getPerson(Long id) {
        try {
            PersonSwApiDto personSwApiDto = restTemplate.getForObject(
                    SW_API_URL + PEOPLE.getEndpoint() + "/{id}?format={format}",
                    PersonSwApiDto.class,
                    Map.of("id", id, "format", "json"));

            if (personSwApiDto == null)
                throw new NotFoundException("Person not found!");

            return personSwApiDto;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("Person not found!");
            else
                throw new SwApiException("Something went wrong when calling SwApi!");
        }
    }
}
