package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.reservation.Reservation;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Repository("reservationRepository")
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerId(long id);

    Reservation findById(long id);

    List<Reservation> findByPlaceId(long id);

    List<Reservation> findByTermIdAndPlaceId(long termId, long placeId);
}
