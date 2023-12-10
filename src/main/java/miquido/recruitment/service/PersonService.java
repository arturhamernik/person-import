package miquido.recruitment.service;

import lombok.RequiredArgsConstructor;
import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.entity.PersonEntity;
import miquido.recruitment.exception.NotFoundException;
import miquido.recruitment.exception.ValidationException;
import miquido.recruitment.common.PersonMapper;
import miquido.recruitment.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto findPersonById(String id) {
        PersonEntity personEntity = personRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Person not found!"));

        return PersonMapper.INSTANCE.dtoFromEntity(personEntity);
    }

    public List<PersonDto> findPersonByName(String name) {
        if(name.length() < 3)
            throw new ValidationException("Name to short, put at least three characters!");

        List<PersonDto> personDtoList = personRepository.findAllByNameContainingIgnoreCase(name)
                .stream().map(PersonMapper.INSTANCE::dtoFromEntity).toList();

        if (personDtoList.isEmpty())
            throw new NotFoundException("Person not found!");

        return personDtoList;
    }
}
