package pl.npp.nopodajpodajserver.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.user.Owner;
import pl.npp.nopodajpodajserver.repository.IOwnerRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/owners")
public class OwnerRestController {

    @Autowired
    private IOwnerRepository ownerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Owner>> getOwners() {
        return new ResponseEntity<>(ownerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Owner> getOwner(@PathVariable long id) {
        Owner owner = ownerRepository.findById(id);
        if (owner != null) {
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Owner) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addOwner(@RequestBody Owner owner) {
        owner.setLevel(2);
        owner.setEnabled(true);
        String encryptPassword = DigestUtils.sha1Hex(owner.getPassword());
        owner.setPassword(encryptPassword);
        return new ResponseEntity<>(ownerRepository.save(owner), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteOwner(@PathVariable long id) {
        Owner currentOwner = ownerRepository.findById(id);

        if (currentOwner.getId() == id) {
            currentOwner.setEnabled(false);
            ownerRepository.save(currentOwner);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
