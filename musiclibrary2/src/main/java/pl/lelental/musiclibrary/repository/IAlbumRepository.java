package pl.lelental.musiclibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lelental.musiclibrary.model.Album;

import java.util.List;

/**
 * @author Paweł Lelental
 **/

@Repository("albumRepository")
public interface IAlbumRepository extends JpaRepository<Album, Long> {

    Album findById(long id);

    List<Album> findByAuthorId(long authorId);

    Album findByName(String name);

}
