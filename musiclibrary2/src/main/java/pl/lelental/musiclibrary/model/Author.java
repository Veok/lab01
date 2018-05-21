package pl.lelental.musiclibrary.model;

import lombok.Data;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * @author Paweł Lelental
 **/

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Date dateOfCreation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "authorId")
    private List<Album> albumList;
    private int countOfAuthorsAlbumsWithGrammyAward;
}
