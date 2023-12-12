package miquido.recruitment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miquido.recruitment.enums.PersonExistsAction;
import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.common.PersonValidator;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.exception.ValidationException;
import miquido.recruitment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonImportService {

    @Value("${person.exists.action}")
    private PersonExistsAction personExistsAction;
    private final PersonRepository personRepository;
    private final PersonValidator personValidator;
    private final PersonMapper personMapper;
    private final StarWarsApiRestTemplate starWarsApiRestTemplate;

    public void importPerson(Long id) {
        PersonSwApiDto personSwApiDto = starWarsApiRestTemplate.getPerson(id);
        personSwApiDto.setId(id);
        PersonEntity personEntity = personMapper.entityFromSwApi(personSwApiDto);

        if (personValidator.isPersonsHeightUnderThreshold(personEntity.getHeight()))
            throw new ValidationException("Can't import! Person's height is under threshold!");

        if(personRepository.existsById(id) && personExistsAction == PersonExistsAction.ERROR) {
            throw new ValidationException("This person already has been imported!");
        } else {
            personRepository.save(personEntity);
        }
    }
}
