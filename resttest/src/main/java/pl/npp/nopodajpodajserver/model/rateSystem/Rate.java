package pl.npp.nopodajpodajserver.model.rateSystem;

import lombok.Data;
import pl.npp.nopodajpodajserver.model.place.Place;
import pl.npp.nopodajpodajserver.model.user.Customer;

import javax.persistence.*;

/**
 * @author Paweł Lelental
 **/
@Data
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private int score;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToOne
    private Place place;
}
