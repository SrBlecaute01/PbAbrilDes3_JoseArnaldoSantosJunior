package uol.compass.customer.repository;

import org.springframework.data.repository.CrudRepository;
import uol.compass.customer.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {


}