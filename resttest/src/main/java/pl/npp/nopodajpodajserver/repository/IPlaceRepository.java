package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.place.Place;
import pl.npp.nopodajpodajserver.model.place.PlaceType;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Repository("placeRepository")
public interface IPlaceRepository extends JpaRepository<Place, Long> {

    Place findById(long id);

    List<Place> findByOwnerId(long id);

    List<Place> findByCity(String city);

    List<Place> findByCityAndStreet(String city, String street);

    List<Place> findByPostCode(String postCode);

    List<Place> findByCityAndStreetAndPostCode(String city, String street, String postCode);

    List<Place> findByPlaceType(PlaceType placeType);

    List<Place> findByLocalizationCoordinates(String coordinates);

    List<Place> findByCityAndStreetAndPostCodeAndPlaceType(String city, String street, String postCode, PlaceType placeType);

    List<Place> findByCityAndPlaceType(String city, PlaceType placeType);

    List<Place> findByCityAndStreetAndPlaceType(String city, String street, PlaceType placeType);

    List<Place> findByPostCodeAndPlaceType(String postCode, PlaceType placeType);

    List<Place> findByPlaceAmenitiesIdAndPlaceType(long id, PlaceType placeType);

    List<Place> findByCityAndCost(String city, BigDecimal cost);

    List<Place> findByCityAndCostAndPlaceType(String city, BigDecimal cost, PlaceType placeType);

    List<Place> findByCityAndPlaceTypeAndScore(String city, PlaceType placeType, int score);



}
