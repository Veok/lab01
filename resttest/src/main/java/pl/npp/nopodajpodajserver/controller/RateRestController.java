package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.rateSystem.Category;
import pl.npp.nopodajpodajserver.model.rateSystem.Rate;
import pl.npp.nopodajpodajserver.repository.IRateRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/rates")
public class RateRestController {

    @Autowired
    private IRateRepository rateRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Rate>> getRates() {
        return new ResponseEntity<>(rateRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Rate> getRate(@PathVariable long id) {
        Rate rate = rateRepository.findById(id);
        if (rate != null) {
            return new ResponseEntity<>(rate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Rate) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addRate(@RequestBody Rate rate) {
        return new ResponseEntity<>(rateRepository.save(rate), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRate(@PathVariable long id) {
        rateRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byPlace/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Rate>> findByPlaceId(@PathVariable long id) {
        List<Rate> rates = rateRepository.findByPlaceId(id);
        if (!rates.isEmpty()) {
            return new ResponseEntity<>(rates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Rate>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCustomer/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Rate>> findByCustomerId(@PathVariable long id) {
        List<Rate> rates = rateRepository.findByCustomerId(id);
        if (!rates.isEmpty()) {
            return new ResponseEntity<>(rates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Rate>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byScore/{score}", method = RequestMethod.GET)
    public ResponseEntity<List<Rate>> findByScore(@PathVariable int score) {
        List<Rate> rates = rateRepository.findByScore(score);
        if (!rates.isEmpty()) {
            return new ResponseEntity<>(rates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Rate>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCategory/{category}", method = RequestMethod.GET)
    public ResponseEntity<List<Rate>> findByCategory(@PathVariable String category) {
        List<Rate> rates = rateRepository.findByCategory(Category.valueOf(category));
        if (!rates.isEmpty()) {
            return new ResponseEntity<>(rates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((List<Rate>) null, HttpStatus.NOT_FOUND);
        }
    }
}
