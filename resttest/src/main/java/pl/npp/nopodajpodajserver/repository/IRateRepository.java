package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.rateSystem.Category;
import pl.npp.nopodajpodajserver.model.rateSystem.Rate;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Repository("rateRepository")
public interface IRateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findByPlaceId(long id);

    List<Rate> findByCustomerId(long customerId);

    Rate findById(long id);

    List<Rate> findByScore(int score);

    List<Rate> findByCategory(Category category);

}
