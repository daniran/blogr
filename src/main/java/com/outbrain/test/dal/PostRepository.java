package com.outbrain.test.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by daniran on 1/13/14.
 */
public class PostRepository {

    @Inject
    private EntityManager em;

    private static Logger LOG = LoggerFactory.getLogger(PostRepository.class);

    public List<Post> getPosts(int limit) {
        LOG.debug("Fetching posts with limit: {}", limit);
        try {
            Query query = em.createQuery("select p from Post p order by p.creationTime desc");
            if (limit > 0) {
                query = query.setMaxResults(limit);
            }
            List<Post> resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            LOG.error("Error fetching posts", e);
            return null;
        }
    }

    public Post addPost(String title, String content, String author) {
        LOG.debug("Adding post: title: {} content {} author {}", title, content, author);
        try {
            Post post = new Post(title, content, author);
            em.persist(post);
            return post;
        } catch (Exception e) {
            LOG.error("Error adding post", e);
            return null;
        }
    }

    public Post modifyPost(int id, String title, String content, String author) {
        LOG.debug("Modifying post: id {} title: {} content {} author {}", id, title, content, author);
        try {
            Post existing = getPost(id);
            existing.setTitle(title);
            existing.setContent(content);
            existing.setAuthor(author);
            em.persist(existing);
            return existing;
        } catch (Exception e) {
            LOG.error("Error modifying post", e);
            return null;
        }
    }

    public Post getPost(int id) {
        LOG.debug("Fetching post with id: {}", id);
        Post post = em.find(Post.class, id);
        return post;
    }

    public Post deletePost(int id) {
        LOG.debug("Deleting post with id {}", id);
        Post post = getPost(id);
        if (post != null) {
            em.remove(post);
            return post;
        }
        return null;
    }
}
