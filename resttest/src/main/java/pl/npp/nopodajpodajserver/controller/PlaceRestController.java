package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.place.Place;
import pl.npp.nopodajpodajserver.model.place.PlaceType;
import pl.npp.nopodajpodajserver.repository.IPlaceRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/places")
public class PlaceRestController {

    @Autowired
    private IPlaceRepository placeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Place>> getPlaces() {
        return new ResponseEntity<>(placeRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Place> getPlace(@PathVariable long id) {
        Place place = placeRepository.findById(id);
        if (place != null) {
            return new ResponseEntity<>(place, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Place) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addPlace(@RequestBody Place place) {
        return new ResponseEntity<>(placeRepository.save(place), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePlace(@PathVariable long id) {
        placeRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byOwner/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByOwnerId(@PathVariable long id) {
        List<Place> places = placeRepository.findByOwnerId(id);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCity(@PathVariable String city) {
        List<Place> places = placeRepository.findByCity(city);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/street/{street}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndStreet(@PathVariable String city, @PathVariable String street) {
        List<Place> places = placeRepository.findByCityAndStreet(city, street);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPostCode/{postCode}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByPostCode(@PathVariable String postCode) {
        List<Place> places = placeRepository.findByPostCode(postCode);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/street/{street}/postCode/{postCode}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findCityAndStreetAndPostCode(@PathVariable String city, @PathVariable String street,
                                                                    @PathVariable String postCode) {
        List<Place> places = placeRepository.findByCityAndStreetAndPostCode(city, street, postCode);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPlaceType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByPlaceType(@PathVariable String placeType) {
        List<Place> places = placeRepository.findByPlaceType(PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCoordinates/{coordinates}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCoordinates(@PathVariable String coordinates) {
        List<Place> places = placeRepository.findByLocalizationCoordinates(coordinates);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/street/{street}/postCode/{postCode}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findCityAndStreetAndPostCodeAndPlaceType(@PathVariable String city, @PathVariable String street,
                                                                                @PathVariable String postCode, @PathVariable String placeType) {
        List<Place> places = placeRepository.findByCityAndStreetAndPostCodeAndPlaceType(city, street, postCode, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndPlaceType(@PathVariable String city, @PathVariable String placeType) {
        List<Place> places = placeRepository.findByCityAndPlaceType(city, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/street/{street}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndStreetAndPlaceType(@PathVariable String city, @PathVariable String street,
                                                                       @PathVariable String placeType) {
        List<Place> places = placeRepository.findByCityAndStreetAndPlaceType(city, street, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPostCode/{postCode}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByPostCodeAndPlaceType(@PathVariable String postCode, @PathVariable String placeType) {
        List<Place> places = placeRepository.findByPostCodeAndPlaceType(postCode, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPlaceAmenities/{id}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByPlaceAmenitiesAndPlaceType(@PathVariable long id, @PathVariable String placeType) {
        List<Place> places = placeRepository.findByPlaceAmenitiesIdAndPlaceType(id, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/cost/{cost}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndCost(@PathVariable String city, @PathVariable BigDecimal cost) {
        List<Place> places = placeRepository.findByCityAndCost(city, cost);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/cost/{cost}/placeType/{placeType}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndCostAndPlaceType(@PathVariable String city, @PathVariable BigDecimal cost,
                                                                     @PathVariable String placeType) {
        List<Place> places = placeRepository.findByCityAndCostAndPlaceType(city, cost, PlaceType.valueOf(placeType));
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byCity/{city}/placeType/{placeType}/score/{score}", method = RequestMethod.GET)
    public ResponseEntity<List<Place>> findByCityAndPlaceTypeAndScore(@PathVariable String city, @PathVariable String placeType,
                                                                      @PathVariable int score) {
        List<Place> places = placeRepository.findByCityAndPlaceTypeAndScore(city, PlaceType.valueOf(placeType), score);
        if (!places.isEmpty()) {
            return new ResponseEntity<List<Place>>(places, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Place>>((List<Place>) null, HttpStatus.NOT_FOUND);
        }
    }
}
