package miquido.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSwApiDto {

    private Long id;
    private String name;
    private String height;
    private String mass;
}

