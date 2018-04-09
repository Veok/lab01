package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.place.PlaceAmenities;
import pl.npp.nopodajpodajserver.repository.IPlaceAmenitiesRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/placeAmenities")
public class PlaceAmenitiesRestController {

    @Autowired
    private IPlaceAmenitiesRepository placeAmenitiesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PlaceAmenities>> getAllPlaceAmenities() {
        return new ResponseEntity<>(placeAmenitiesRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaceAmenities> getPlaceAmenities(@PathVariable long id) {
        PlaceAmenities placeAmenities = placeAmenitiesRepository.findById(id);
        if (placeAmenities != null) {
            return new ResponseEntity<>(placeAmenities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((PlaceAmenities) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addPlaceAmenities(@RequestBody PlaceAmenities placeAmenities) {
        return new ResponseEntity<>(placeAmenitiesRepository.save(placeAmenities), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePlaceAmenities(@PathVariable long id) {
        placeAmenitiesRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byPlaceId/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaceAmenities> findByPlaceId(@PathVariable long id) {
        PlaceAmenities place = placeAmenitiesRepository.findByPlaceId(id);
        if (place != null){
            return new ResponseEntity<PlaceAmenities>(place, HttpStatus.OK);
        } else{
            return new ResponseEntity<PlaceAmenities>((PlaceAmenities) null, HttpStatus.NOT_FOUND);
        }
    }

}
