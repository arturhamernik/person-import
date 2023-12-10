package miquido.recruitment.common;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@NoArgsConstructor
@Component
public class PersonValidator {

    @Value("${person.height.threshold}")
    private BigDecimal threshold;

    /**
     * Returns true if person's height is under threshold and false when person's height is over threshold
     * @param height
     * @return
     */
    public boolean isPersonsHeightUnderThreshold(BigDecimal height) {
        return height.compareTo(threshold) < 0;
    }
}
