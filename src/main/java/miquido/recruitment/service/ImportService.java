package miquido.recruitment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miquido.recruitment.common.PersonValidator;
import miquido.recruitment.exception.SwApiException;
import miquido.recruitment.exception.ValidationException;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportService {

    private final RestTemplate restTemplate;
    private final PersonRepository personRepository;
    private final PersonValidator personValidator;
    private final String url = "https://swapi.dev/api/people/";

    public void importPerson(Long id) {
        if(personRepository.existsById(id))
            throw new ValidationException("This person already has been imported!");

        try {
            PersonSwApiDto personSwapiDto = restTemplate.getForObject(
                    url + id,
                    PersonSwApiDto.class,
                    Map.of("format", "json"));

            if (personSwapiDto != null) {
                personSwapiDto.setId(id);
                PersonEntity personEntity = PersonMapper.INSTANCE.entityFromSwApi(personSwapiDto);

                if (personValidator.isPersonsHeightUnderThreshold(personEntity.getHeight()))
                    throw new ValidationException("Can't import! Person's height is under threshold!");

                personRepository.save(personEntity);
            } else {
                throw new NotFoundException("Person not found!");
            }
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("Person not found!");
            else
                throw new SwApiException("Something went wrong when calling SwApi!");
        }
    }
}
