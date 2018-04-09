package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.reservation.Reservation;
import pl.npp.nopodajpodajserver.repository.IReservationRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    @Autowired
    private IReservationRepository reservationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reservation> getReservation(@PathVariable long id) {
        Reservation Reservation = reservationRepository.findById(id);
        if (Reservation != null) {
            return new ResponseEntity<>(Reservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Reservation) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addReservation(@RequestBody Reservation Reservation) {
        return new ResponseEntity<>(reservationRepository.save(Reservation), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byCustomer/{id}")
    public ResponseEntity<List<Reservation>> findByCustomerId(@PathVariable long id) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(id);
        if (!reservations.isEmpty()) {
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Reservation>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPlace/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> findByPlaceId(@PathVariable long id) {
        List<Reservation> reservations = reservationRepository.findByPlaceId(id);
        if (!reservations.isEmpty()) {
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Reservation>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byTerm/{termId}/byPlace/{placeId}", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> findByTermAndPlaceId(@PathVariable long termId, @PathVariable long placeId) {
        List<Reservation> reservations = reservationRepository.findByTermIdAndPlaceId(termId, placeId);
        if (!reservations.isEmpty()) {
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Reservation>) null, HttpStatus.NOT_FOUND);
        }
    }
}
