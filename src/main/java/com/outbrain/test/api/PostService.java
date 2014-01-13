package com.outbrain.test.api;

import com.outbrain.test.dal.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniran on 1/13/14.
 */
public class PostService {

    @Inject
    private PostRepository repo;

    private static Logger LOG = LoggerFactory.getLogger(PostService.class);

    public List<Post> getPosts(int limit) {
        LOG.debug("Fetching posts with limit: %s", limit);
        List<Post> reply = new ArrayList<>();
        List<com.outbrain.test.dal.Post> posts = repo.getPosts(limit);
        for (com.outbrain.test.dal.Post post : posts) {
            reply.add(new Post(post));
        }
        return reply;
    }

    public Post getPost(int id) {
        LOG.debug("Fetching post with id: %s", id);
        com.outbrain.test.dal.Post post = repo.getPost(id);
        return new Post(post);
    }

    public Post deletePost(int id) {
        LOG.debug("Deleting post with id: %s", id);
        com.outbrain.test.dal.Post post = repo.deletePost(id);
        if (post == null) {
            return null;
        }
        return new Post(post);
    }

    public Post savePost(Post post) {
        LOG.debug("Saving post: %s", post);
        if (post.id == null) {
            com.outbrain.test.dal.Post newPost = repo.addPost(post.title, post.content, post.author);
            return new Post(newPost);
        } else {
            com.outbrain.test.dal.Post existing = repo.modifyPost(Integer.parseInt(post.id), post.title, post.content, post.author);
            return new Post(existing);
        }
    }
}
