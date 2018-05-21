package pl.lelental.musiclibrary.service;

import pl.lelental.musiclibrary.model.Author;

/**
 * @author Paweł Lelental
 **/
public interface IAuthorService {

    Author findById(long id);

    Author findByName(String name);

    boolean saveAuthor(Author author);

    boolean updateAuhtor(Author author);

    boolean deleteAuthor(long id);

}
