package uol.compass.calculate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uol.compass.calculate.model.Rule;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {

    boolean existsByCategoryIgnoreCase(String category);

}