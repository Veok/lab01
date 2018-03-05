package lelental.domain;

import java.util.Date;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
public class Author {

    private long id;
    private String name;
    private Date dateOfCreation;
    private List<Cd> listOfCds;


    public Author(long id, String name, Date dateOfCreation, List<Cd> listOfCds) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.listOfCds = listOfCds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<Cd> getListOfCds() {
        return listOfCds;
    }

    public void setListOfCds(List<Cd> listOfCds) {
        this.listOfCds = listOfCds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
