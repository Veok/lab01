package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.reservation.Term;

/**
 * @author Paweł Lelental
 **/
@Repository("termRepository")
public interface ITermRepository extends JpaRepository<Term, Long> {

    Term findById(long id);

    Term findByReservationId(long id);
}
