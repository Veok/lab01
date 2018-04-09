package pl.npp.nopodajpodajserver.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.npp.nopodajpodajserver.model.place.Place;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("Owner")
public class Owner extends User {

    @OneToMany
    private List<Place> places;
}
