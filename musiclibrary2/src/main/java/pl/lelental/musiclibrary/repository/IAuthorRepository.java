package pl.lelental.musiclibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lelental.musiclibrary.model.Author;

/**
 * @author Paweł Lelental
 **/
@Repository("authorRepository")
public interface IAuthorRepository extends JpaRepository<Author, Long> {

    Author findById(long id);

    Author findByName(String name);

}