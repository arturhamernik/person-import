package miquido.recruitment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonEntity {

    @Id
    private Long id;
    private String name;
    private BigDecimal height;
    private BigDecimal mass;
}
