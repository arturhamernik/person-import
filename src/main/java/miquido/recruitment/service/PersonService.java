package miquido.recruitment.service;

import lombok.RequiredArgsConstructor;
import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.exception.ValidationException;
import miquido.recruitment.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonDto findPersonById(Long id) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found!"));

        return personMapper.dtoFromEntity(personEntity);
    }

    public List<PersonDto> findPersonByName(String name) {
        if(name.length() < 3)
            throw new ValidationException("Name too short, put at least three characters!");

        List<PersonDto> personDtoList = personRepository.findAllByNameContainingIgnoreCase(name)
                .stream().map(personMapper::dtoFromEntity).toList();

        if (personDtoList.isEmpty())
            throw new NotFoundException("Person not found!");

        return personDtoList;
    }
}
