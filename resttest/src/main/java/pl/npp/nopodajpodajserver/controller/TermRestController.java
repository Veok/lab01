package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.reservation.Term;
import pl.npp.nopodajpodajserver.repository.ITermRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/terms")
public class TermRestController {
    @Autowired
    private ITermRepository termRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Term>> getTerms() {
        return new ResponseEntity<>(termRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Term> getTerm(@PathVariable long id) {
        Term term = termRepository.findById(id);
        if (term != null) {
            return new ResponseEntity<>(term, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Term) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addTerm(@RequestBody Term term) {
        return new ResponseEntity<>(termRepository.save(term), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTerm(@PathVariable long id) {
        termRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byReservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<Term> findByReservationId(@PathVariable long id) {
        Term term = termRepository.findByReservationId(id);
        if (term != null) {
            return new ResponseEntity<Term>(term, HttpStatus.OK);
        } else {
            return new ResponseEntity<Term>((Term) null, HttpStatus.NOT_FOUND);

        }
    }
}
