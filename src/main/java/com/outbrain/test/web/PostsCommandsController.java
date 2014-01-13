package com.outbrain.test.web;

import com.outbrain.test.api.Post;
import com.outbrain.test.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by daniran on 1/13/14.
 */

@Controller
@RequestMapping("/posts")
public class PostsCommandsController {
    private static Logger LOG = LoggerFactory.getLogger(PostsCommandsController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable String id) {
        Post post = postService.deletePost(Integer.parseInt(id));
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder builder) {

        Post newPost = postService.savePost(post);
        if (newPost == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/aggregators/orders/{id}")
                        .buildAndExpand(newPost.id).toUri());

        return new ResponseEntity<>(newPost, headers, HttpStatus.CREATED);
    }
}
