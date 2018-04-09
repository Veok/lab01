package pl.npp.nopodajpodajserver.model.reservation;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Paweł Lelental
 **/
@Data
@Entity
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private boolean isTaken;
    private Date date;
    @OneToOne
    private Reservation reservation;
}
