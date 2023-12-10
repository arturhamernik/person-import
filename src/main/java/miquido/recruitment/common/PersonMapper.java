package miquido.recruitment.common;

import miquido.recruitment.dto.PersonDto;
import miquido.recruitment.dto.PersonSwApiDto;
import miquido.recruitment.entity.PersonEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "id", target = "id",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "name", target = "name",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "height", target = "height",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "mass", target = "mass",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonDto dtoFromEntity(PersonEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "height", target = "height", qualifiedByName = "mapDecimal")
    @Mapping(source = "mass", target = "mass", qualifiedByName = "mapDecimal")
    PersonEntity entityFromSwApi(PersonSwApiDto dto);

    @Named("mapDecimal")
    public static BigDecimal mapDecimal(String value) {
        return !value.equals("unknown") ? new BigDecimal(value) : null;
    }
}
