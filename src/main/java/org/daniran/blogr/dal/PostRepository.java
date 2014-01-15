package org.daniran.blogr.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by daniran on 1/13/14.
 */
public class PostRepository {

    @Inject
    private EntityManagerFactory emf;

    private static Logger LOG = LoggerFactory.getLogger(PostRepository.class);

    public List<Post> getPosts(int limit, Date since) {
        LOG.debug("Fetching posts with limit: {} since: {}", limit, since);
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("select p from Post p where p.creationTime >= :since order by p.creationTime desc");
            if (since != null) {
                query.setParameter("since", since, TemporalType.TIMESTAMP);
            } else {
                query.setParameter("since", new Date(0), TemporalType.TIMESTAMP);
            }
            if (limit > 0) {
                query = query.setMaxResults(limit);
            }
            List<Post> resultList = query.getResultList();
            em.getTransaction().commit();
            em.close();
            return resultList;
        } catch (Exception e) {
            LOG.error("Error fetching posts", e);
            return null;
        }
    }

    public Post addPost(String title, String content, String author, String timestamp) {
        LOG.debug("Adding post: title: {} content {} author {}", title, content, author);
        try {
            Post post = new Post(title, content, author, timestamp);
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
            em.close();
            return post;
        } catch (Exception e) {
            LOG.error("Error adding post", e);
            return null;
        }
    }

    public Post modifyPost(long id, String title, String content, String author) {
        LOG.debug("Modifying post: id {} title: {} content {} author {}", id, title, content, author);
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Post existing = em.find(Post.class, id);
            if (existing == null) {
                LOG.error("Cannot find post with id {}", id);
                return null;
            }
            existing.setTitle(title);
            existing.setContent(content);
            existing.setAuthor(author);
            em.persist(existing);
            em.getTransaction().commit();
            em.close();
            return existing;
        } catch (Exception e) {
            LOG.error("Error modifying post", e);
            return null;
        }
    }

    public Post getPost(long id) {
        LOG.debug("Fetching post with id: {}", id);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Post post = em.find(Post.class, id);
        em.getTransaction().commit();
        em.close();
        return post;
    }

    public Post deletePost(long id) {
        LOG.debug("Deleting post with id {}", id);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Post existing = em.find(Post.class, id);
        if (existing == null) {
            LOG.error("Cannot find post with id {}", id);
            return null;
        }
        em.remove(existing);
        em.getTransaction().commit();
        em.close();
        return existing;
    }
}
