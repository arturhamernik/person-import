package miquido.recruitment.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@Entity
public class PersonEntity {

    @Id
    private Long id;
    private String name;
    private BigDecimal height;
    private BigDecimal mass;
}
