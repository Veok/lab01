package pl.lelental.musiclibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lelental.musiclibrary.model.Author;
import pl.lelental.musiclibrary.repository.IAuthorRepository;

/**
 * @author Paweł Lelental
 **/
@Service("authorService")
public class AuthorService implements IAuthorService {

    @Autowired
    IAuthorRepository authorRepository;

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }


    @Override
    public boolean saveAuthor(Author author) {
        try {
            authorRepository.save(author);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateAuhtor(Author author) {
        try {
            Author retrievedAuthor = findById(author.getId());
            retrievedAuthor.setAlbumList(author.getAlbumList());
            retrievedAuthor.setName(author.getName());
            retrievedAuthor.setDateOfCreation(author.getDateOfCreation());
            retrievedAuthor.setCountOfAuthorsAlbumsWithGrammyAward(author.getCountOfAuthorsAlbumsWithGrammyAward());
            authorRepository.save(retrievedAuthor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAuthor(long id) {
        try {
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}