package uol.compass.calculate.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.compass.calculate.model.Rule;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {

    boolean existsByCategoryIgnoreCase(String category);

    @Query("SELECT COUNT(r.id) > 0 FROM rule r WHERE r.id <> :id AND UPPER(r.category) = UPPER(:category)")
    boolean existsByCategory(Long id, String category);

    Page<Rule> findAll(Pageable pageable);

    @Transactional
    Integer deleteRuleById(Long id);

}