package pl.npp.nopodajpodajserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.npp.nopodajpodajserver.model.rateSystem.Comment;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@Repository("commentRepository")
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    Comment findById(long id);

    List<Comment> findByCustomerId(long userId);

    List<Comment> findByPlaceId(long placeId);
}
