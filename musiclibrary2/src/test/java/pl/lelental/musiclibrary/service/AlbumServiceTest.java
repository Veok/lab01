package pl.lelental.musiclibrary.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.lelental.musiclibrary.TestConfiguration;
import pl.lelental.musiclibrary.model.Album;
import pl.lelental.musiclibrary.model.Author;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Paweł Lelental
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class AlbumServiceTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AlbumService albumService;

    private Album album;
    private Album album2;
    private Author author;


    @Before
    public void setUp() {
        album = new Album();
        author = new Author();
        album2 = new Album();

        author.setName("MUSE");
        author.setDateOfCreation(new Date(1994, 1, 1));
        author.setCountOfAuthorsAlbumsWithGrammyAward(0);

        authorService.saveAuthor(author);
        author = authorService.findByName("MUSE");

        album.setAuthorId(author.getId());
        album.setName("DRONES");
        album.setDateOfPublish(new Date(2015, 6, 8));

        album2.setAuthorId(author.getId());
        album2.setName("ABSOLUTON");
        album2.setDateOfPublish(new Date(2003, 1, 1));

        List<Album> albums = new ArrayList<>();
        albums.add(album);
        albums.add(album2);

        albumService.saveAlbum(album);
        albumService.saveAlbum(album2);
        author.setAlbumList(albums);
        authorService.updateAuhtor(author);
    }


    @After
    public void tearDown() {
        authorService.authorRepository.deleteAll();
    }


    @Test
    public void testAdding() {
        assertTrue(albumService.saveAlbum(album));
    }

    @Test
    public void testDelete() {
        Album retrievedAlbum = albumService.findByName(album.getName());
        assertTrue(albumService.deleteAlbum(retrievedAlbum.getId()));
    }

    @Test
    public void testUpdate() {
        Album albumWithNewName = new Album();
        Album retrievedAlbum = albumService.findByName(album.getName());
        albumWithNewName.setId(retrievedAlbum.getId());
        albumWithNewName.setName("BLACK HOLES");
        albumWithNewName.setDateOfPublish(retrievedAlbum.getDateOfPublish());
        albumWithNewName.setAuthorId(retrievedAlbum.getAuthorId());
        albumWithNewName.setGrammyAward(retrievedAlbum.isGrammyAward());
        assertTrue(albumService.updateAlbum(albumWithNewName));
    }

    @Test
    public void testFindByName() {
        assertEquals(album.getName(), albumService.findById(album.getId()).getName());
    }

    @Test
    public void testFindById() {
        Album album3 = albumService.findByName(album.getName());
        assertEquals(album.getId(),
                albumService.findById(album3.getId()).getId());
    }

    @Test
    public void testUpdateWithGrammy() {
        albumService.updateAlbumWithGrammyAward(album.getId());
        Album albumWithGrammy = albumService.findById(album.getId());
        Author authorWithGrammy = authorService.findById(album.getAuthorId());
        assertTrue(albumWithGrammy.isGrammyAward());
        assertEquals(1, authorWithGrammy.getCountOfAuthorsAlbumsWithGrammyAward());
    }


}