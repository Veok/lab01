package lelental.repository;

import lelental.domain.Author;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RunWith(JUnit4.class)
public class AuthorRepositoryDbTest extends DBTestCase {


    private AuthorRepository authorRepository;
    private Author author;

    @Before
    public void setUp() throws Exception {
        authorRepository = AuthorRepositoryFactory.getInstance();
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void checkAdding() throws Exception {
        setDataForTest();
        assertTrue(authorRepository.insert(author));

        // Data verification

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("AUTHOR");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("ds-2.xml");
        ITable expectedTable = expectedDataSet.getTable("AUTHOR");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
        Assertion.assertEquals(expectedTable, filteredTable);
        authorRepository.delete(authorRepository.findByName(author.getName()).stream().findFirst().get().getId());
    }

    @Test
    public void checkDelete() throws Exception {
        setDataForTest();
        assertTrue(authorRepository.insert(author));

        List<Author> authorList = authorRepository.findByName(author.getName());
        assertTrue(authorRepository.delete(authorList.stream().findFirst().get().getId()));


        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("AUTHOR");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("ds-3.xml");
        ITable expectedTable = expectedDataSet.getTable("AUTHOR");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void checkSelect() throws Exception {
        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("AUTHOR");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("ds-3.xml");
        ITable expectedTable = expectedDataSet.getTable("AUTHOR");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void checkUpdate() throws Exception{
        List<Author> authorList = authorRepository.findByName("IRA2");
        Author author = authorList.stream().findFirst().get();
        author.setName("ARTIC MONKEYS");
        assertTrue(authorRepository.update(author));


        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("AUTHOR");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("ds-4.xml");
        ITable expectedTable = expectedDataSet.getTable("AUTHOR");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return this.getDataSet("ds-1.xml");
    }


    protected IDataSet getDataSet(String datasetName) throws Exception {
        URL url = getClass().getClassLoader().getResource(datasetName);
        FlatXmlDataSet ret = new FlatXmlDataSetBuilder().build(url.openStream());
        return ret;
    }

    private void setDataForTest() throws ParseException {
        author = new Author();
        author.setName("IRA4");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "11-03-1995";
        java.util.Date date = sdf.parse(dateInString);
        author.setDateOfCreation(date);
    }
}
