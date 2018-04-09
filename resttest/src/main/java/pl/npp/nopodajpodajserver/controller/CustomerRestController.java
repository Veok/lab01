package pl.npp.nopodajpodajserver.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.user.Customer;
import pl.npp.nopodajpodajserver.repository.ICustomerRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    private ICustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Customer) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        customer.setLevel(1);
        customer.setEnabled(true);
        String encryptPassword = DigestUtils.sha1Hex(customer.getPassword());
        customer.setPassword(encryptPassword);
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        Customer currentCustomer = customerRepository.findById(id);
        if (currentCustomer.getId() == id) {
            currentCustomer.setEnabled(false);
            customerRepository.save(currentCustomer);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
