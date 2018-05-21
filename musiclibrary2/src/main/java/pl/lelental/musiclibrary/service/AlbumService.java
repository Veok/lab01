package pl.lelental.musiclibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lelental.musiclibrary.model.Album;
import pl.lelental.musiclibrary.model.Author;
import pl.lelental.musiclibrary.repository.IAlbumRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Service("alubService")
public class AlbumService implements IAlbumService {

    @Autowired
    IAlbumRepository albumRepository;
    @Autowired
    IAuthorService authorService;

    @Override
    public Album findById(long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album findByName(String name) {
        return albumRepository.findByName(name);
    }

    @Override
    public boolean saveAlbum(Album album) {
        try {
            albumRepository.save(album);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateAlbum(Album album) {
        try {
            Album retrievedAlbum = findById(album.getId());
            retrievedAlbum.setAuthorId(album.getAuthorId());
            retrievedAlbum.setDateOfPublish(album.getDateOfPublish());
            retrievedAlbum.setName(album.getName());
            retrievedAlbum.setGrammyAward(album.isGrammyAward());
            albumRepository.save(retrievedAlbum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteAlbum(long id) {
        try {
            albumRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updateAlbumWithGrammyAward(long albumId) {
        Album album = findById(albumId);
        album.setGrammyAward(true);
        Author author = authorService.findById(album.getAuthorId());
        int countOfAwards = author.getCountOfAuthorsAlbumsWithGrammyAward();
        author.setCountOfAuthorsAlbumsWithGrammyAward(countOfAwards + 1);
        updateAlbum(album);
        authorService.updateAuhtor(author);
    }
}
