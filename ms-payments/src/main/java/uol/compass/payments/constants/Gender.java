package uol.compass.payments.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Gender {

    FEMININE,
    MASCULINE;

    @JsonCreator
    public static Gender fromValue(String value) {
        return Arrays.stream(values())
                .filter(gender -> gender.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

}