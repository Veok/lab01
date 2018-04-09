package pl.npp.nopodajpodajserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npp.nopodajpodajserver.model.rateSystem.Comment;
import pl.npp.nopodajpodajserver.repository.ICommentRepository;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
@RestController
@RequestMapping("/comments")
public class CommentRestController {

    @Autowired
    private ICommentRepository commentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getComments() {
        return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable long id) {
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((Comment) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/byCustomer/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> findByCustomerId(@PathVariable long id) {
        List<Comment> comments = commentRepository.findByCustomerId(id);
        if (!comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Comment>>((List<Comment>) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/byPlace/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> findByPlacerId(@PathVariable long id) {
        List<Comment> w = commentRepository.findAll();
        List<Comment> comments = commentRepository.findByPlaceId(id);
        if (!comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Comment>>((List<Comment>) null, HttpStatus.NOT_FOUND);
        }
    }
}
