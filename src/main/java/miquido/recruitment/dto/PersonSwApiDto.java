package miquido.recruitment.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSwApiDto {

    private Long id;
    private String name;
    private String height;
    private String mass;
}

