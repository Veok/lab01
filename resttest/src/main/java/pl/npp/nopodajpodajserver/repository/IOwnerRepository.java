package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.user.Owner;

/**
 * @author Paweł Lelental
 **/
@Repository("ownerRepository")
public interface IOwnerRepository extends JpaRepository<Owner, Long> {

    Owner findById(long id);

    Owner findByEmail(String email);

}
