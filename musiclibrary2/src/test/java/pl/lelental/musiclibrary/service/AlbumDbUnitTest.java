package pl.lelental.musiclibrary.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import pl.lelental.musiclibrary.TestConfiguration;
import pl.lelental.musiclibrary.model.Album;
import pl.lelental.musiclibrary.model.Author;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Paweł Lelental
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(classes = TestConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class AlbumDbUnitTest {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private AuthorService authorService;

    @Test
    @DatabaseSetup("fullData.xml")
    @ExpectedDatabase(value = "changeCountOfGrammyData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCountOfGrammyAward() {
        albumService.updateAlbumWithGrammyAward(2);
        Album albumWithGrammy = albumService.findById(2);
        Author author = authorService.findById(albumWithGrammy.getAuthorId());
        assertTrue(albumWithGrammy.isGrammyAward());
        assertEquals(1, author.getCountOfAuthorsAlbumsWithGrammyAward());
    }

    @Test
    @DatabaseSetup("fullData.xml")
    @ExpectedDatabase(value = "findAllAlbumData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void findAll() {
        assertEquals("DRONES", albumService.findById(2).getName());
        assertEquals("Liebe is fur alle da", albumService.findById(3).getName());

    }

    @Test
    @DatabaseSetup("fullData.xml")
    @ExpectedDatabase(value = "deleteAlbumData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void delete() {
        Album retrievedAlbum = albumService.findByName("DRONES");
        assertTrue(albumService.deleteAlbum(retrievedAlbum.getId()));
    }

    @Test
    @DatabaseSetup("fullData.xml")
    @ExpectedDatabase(value = "updateAlbumData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void update() {
        Album albumWithNewName = new Album();
        Album retrievedAlbum = albumService.findById(3);
        albumWithNewName.setId(retrievedAlbum.getId());
        albumWithNewName.setName("Reise reise");
        albumWithNewName.setDateOfPublish(retrievedAlbum.getDateOfPublish());
        albumWithNewName.setAuthorId(retrievedAlbum.getAuthorId());
        albumWithNewName.setGrammyAward(retrievedAlbum.isGrammyAward());
        assertTrue(albumService.updateAlbum(albumWithNewName));
    }

    @Test
    @DatabaseSetup("fullData.xml")
    @ExpectedDatabase(value = "insertAlbumData.xml",
            assertionMode = DatabaseAssertionMode.DEFAULT)
    public void insert() {
        Album album = new Album();
        album.setName("Reise reise");
        album.setGrammyAward(false);
        album.setAuthorId(3);
        album.setDateOfPublish(new Date(2005, 3, 11));
        assertTrue(albumService.saveAlbum(album));

    }
}
