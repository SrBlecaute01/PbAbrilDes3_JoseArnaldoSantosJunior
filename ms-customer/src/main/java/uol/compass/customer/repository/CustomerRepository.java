package uol.compass.customer.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import uol.compass.customer.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    boolean existsByEmailIgnoreCaseOrCpf(String email, String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByEmailIgnoreCase(String email);

    @Transactional
    Integer deleteCustomerById(Long id);

}