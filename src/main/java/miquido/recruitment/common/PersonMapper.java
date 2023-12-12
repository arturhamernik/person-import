package miquido.recruitment.common;

import lombok.extern.slf4j.Slf4j;
import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PersonMapper {

    public PersonDto dtoFromEntity(PersonEntity entity) {
        if (entity == null)
            return null;

        return PersonDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .height(entity.getHeight())
                .mass(entity.getMass())
                .build();
    }

    public PersonEntity entityFromSwApi(PersonSwApiDto dto) {
        if (dto == null)
            return null;

        return PersonEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .height(mapDecimal("height", dto.getHeight()))
                .mass(mapDecimal("mass", dto.getMass()))
                .build();
    }

    private BigDecimal mapDecimal(String field, String value) {
        if(!value.equals("unknown"))
            return new BigDecimal(value);
        else {
            log.warn("Value passed in property {} is unknown!", field);
            return null;
        }
    }
}
