package uol.compass.payments.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Gender constant")
public class GenderTest {

    @Test
    @DisplayName("Gender test masculine")
    public void testMale() {
        final var gender = Gender.fromValue("masculine");
        Assertions.assertEquals(gender, Gender.MASCULINE);
    }

    @Test
    @DisplayName("Gender test feminine")
    public void testFeminine() {
        final var gender = Gender.fromValue("feminine");
        Assertions.assertEquals(gender, Gender.FEMININE);
    }

    @Test
    @DisplayName("Gender invalid")
    public void testInvalid() {
        final var gender = Gender.fromValue("invalid");
        Assertions.assertNull(gender);
    }

}
