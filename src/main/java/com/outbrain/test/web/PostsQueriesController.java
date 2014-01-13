package com.outbrain.test.web;

import com.outbrain.test.api.Post;
import com.outbrain.test.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Post> getAllPosts() {
        List<Post> posts = postService.getPosts(10);
        return posts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Post> viewPost(@PathVariable String id) {
        Post post = postService.getPost(Long.parseLong(id));
        if (post == null) {
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
