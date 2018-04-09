package pl.npp.nopodajpodajserver.model.reservation;

import lombok.Data;
import pl.npp.nopodajpodajserver.model.place.Place;
import pl.npp.nopodajpodajserver.model.user.Customer;

import javax.persistence.*;

/**
 * @author Paweł Lelental
 **/
@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy =GenerationType.TABLE)
    private long id;
    @OneToOne
    private Place place;
    @ManyToOne
    private Customer customer;
    private String typeOfPayment;
    private boolean isAccepted;
    @OneToOne
    private Term term;
}
