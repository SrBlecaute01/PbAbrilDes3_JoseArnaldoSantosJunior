package uol.compass.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.compass.payments.model.Payment;

import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> {

    Page<Payment> findByCustomerId(Long customerId, Pageable pageable);

}