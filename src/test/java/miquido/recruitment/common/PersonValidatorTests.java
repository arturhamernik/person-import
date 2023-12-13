package miquido.recruitment.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {"person.height.threshold=160"})
@ExtendWith(SpringExtension.class)
public class PersonValidatorTests {

    @Autowired
    private PersonValidator personValidator;

    @Test
    public void validation_heightOverThreshold() {
        assertFalse(personValidator.isPersonsHeightUnderThreshold(new BigDecimal("170")));
    }

    @Test
    public void validation_heightEqualsThreshold() {
        assertFalse(personValidator.isPersonsHeightUnderThreshold(new BigDecimal("160")));
    }

    @Test
    public void validation_heightUnderThreshold() {
        assertTrue(personValidator.isPersonsHeightUnderThreshold(new BigDecimal("150")));
    }
}
