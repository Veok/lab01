package pl.npp.nopodajpodajserver.model.user;

import lombok.Data;
import pl.npp.nopodajpodajserver.model.reservation.Reservation;

import javax.persistence.*;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Data
@MappedSuperclass
public class User {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private int level;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    @OneToMany
    private List<Reservation> reservations;
    private boolean enabled;

}
