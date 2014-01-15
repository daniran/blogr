package org.daniran.blogr.api;

import org.daniran.blogr.dal.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daniran on 1/13/14.
 */
public class PostService {

    @Inject
    private PostRepository repo;

    private int MAX_LIMIT = 500;

    private static Logger LOG = LoggerFactory.getLogger(PostService.class);

    public List<Post> getPosts(int limit, Date since) {
        limit = Math.min(MAX_LIMIT, limit);
        LOG.debug("Fetching posts with limit: {} since: {}", limit, since);
        List<Post> reply = new ArrayList<>();
        List<org.daniran.blogr.dal.Post> posts = repo.getPosts(limit, since);
        for (org.daniran.blogr.dal.Post post : posts) {
            reply.add(new Post(post));
        }
        return reply;
    }

    public Post getPost(long id) {
        LOG.debug("Fetching post with id: {}", id);
        org.daniran.blogr.dal.Post post = repo.getPost(id);
        return new Post(post);
    }

    public Post deletePost(long id) {
        LOG.debug("Deleting post with id: {}", id);
        org.daniran.blogr.dal.Post post = repo.deletePost(id);
        if (post == null) {
            return null;
        }
        return new Post(post);
    }

    public Post savePost(Post post) {
        LOG.debug("Saving post: {}", post);
        if (post.id == null) {
            org.daniran.blogr.dal.Post newPost = repo.addPost(post.title, post.content, post.author, post.timestamp);
            return new Post(newPost);
        } else {
            org.daniran.blogr.dal.Post existing = repo.modifyPost(Long.parseLong(post.id), post.title, post.content, post.author);
            return new Post(existing);
        }
    }
}
