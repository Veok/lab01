package pl.npp.nopodajpodajserver.model.place;


import lombok.Data;
import pl.npp.nopodajpodajserver.model.rateSystem.Rate;
import pl.npp.nopodajpodajserver.model.reservation.Reservation;
import pl.npp.nopodajpodajserver.model.reservation.Term;
import pl.npp.nopodajpodajserver.model.user.Owner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Data
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String name;
    private String localizationCoordinates;
    private String street;
    private String postCode;
    private String openHours;
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    private String city;
    @ManyToOne
    private Owner owner;
    @OneToOne
    private PlaceAmenities placeAmenities;
    private BigDecimal cost;
    @OneToMany
    private List<Term> termList;
    @OneToMany
    private List<Rate> rateList;
    @OneToMany
    private List<Reservation> reservations;
    private int score;

}
