package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.user.Customer;

/**
 * @author Paweł Lelental
 **/
@Repository("customerRepository")
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Customer findById(long id);

    Customer findByEmail(String email);
}
