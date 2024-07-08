package uol.compass.calculate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rule")
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer parity;

}