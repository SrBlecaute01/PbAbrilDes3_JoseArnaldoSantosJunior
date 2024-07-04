package uol.compass.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import uol.compass.customer.constants.Gender;

import java.util.Date;

@Data
@NoArgsConstructor
@Schema(name = "Customer request", description = "The request to create or update a customer.")
public class CustomerRequest {

    @Schema(description = "The cpf.", example = "855.365.292-00")
    @NotBlank(message = "cpf must be informed")
    @CPF(message = "cpf must be valid")
    private String cpf;

    @Schema(description = "The name.", example = "John")
    @NotBlank(message = "name must be informed")
    @Length(max = 255, message = "name must have a maximum of 255 characters")
    private String name;

    @Schema(description = "The gender", example = "MASCULINE")
    @NotNull(message = "gender must be informed")
    private Gender gender;

    @Schema(description = "The email.", example = "jonh@gmail.com")
    @NotBlank(message = "email must be informed")
    @Email(message = "email must be valid")
    private String email;

    @Schema(description = "The photo in base64", example =
            "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
            "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
            "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
            "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
            "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
            "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
            "hGny82eMnIn1weAAAAAElFTkSuQmCC")
    @NotBlank(message = "photo must be informed")
    private String photo;

    @Schema(type = "string", description = "The birth date.", example = "01/01/2000", pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "birthDate must be informed")
    private Date birthDate;

}