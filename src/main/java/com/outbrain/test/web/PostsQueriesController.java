package com.outbrain.test.web;

import com.outbrain.test.api.Post;
import com.outbrain.test.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by daniran on 1/13/14.
 */

@Controller
@RequestMapping("/api/posts")
public class PostsQueriesController {
    private static Logger LOG = LoggerFactory.getLogger(PostsQueriesController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Post> getAllPosts(@RequestParam(defaultValue = "0") int limit, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date since) {
        List<Post> posts = postService.getPosts(limit, since);
        return posts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Post> viewPost(@PathVariable long id) {
        Post post = postService.getPost(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
