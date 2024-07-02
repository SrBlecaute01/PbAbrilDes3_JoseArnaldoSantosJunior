package uol.compass.payments.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Integer value;

    @CreationTimestamp
    private Date createdAt;

}