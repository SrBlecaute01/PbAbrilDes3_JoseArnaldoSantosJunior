package uol.compass.customer.repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uol.compass.customer.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    boolean existsByEmailIgnoreCaseOrCpf(String email, String cpf);

    @Query("SELECT count(id) > 0 FROM customer WHERE (UPPER(email) = UPPER(:email) OR cpf = :cpf) AND id <> :id")
    boolean existsByEmailOrCpf(String email, String cpf, Long id);

    @Transactional
    Integer deleteCustomerById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE customer SET points = points + :points WHERE id = :id")
    void updatePointsById(@Param("id") Long id, @Param("points") Integer points);

}