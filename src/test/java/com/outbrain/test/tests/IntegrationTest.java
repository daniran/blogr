package com.outbrain.test.tests;

import com.outbrain.test.api.Post;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration Tests:
 * <p/>
 * These test run on an embedded Tomcat instance where the app has been deployed.
 * Each test invokes actual HTTP requests
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(IntegrationTest.class);

    private HttpHeaders headers;

    @Before
    public void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test1CreatePost() {
        RestTemplate template = new RestTemplate();

        // add a post 1
        Post newPost = new Post();
        newPost.title = "test post 1";
        newPost.timestamp = ts1;
        ResponseEntity<Post> entity = template.postForEntity("http://localhost:8888/outbrain/api/posts", newPost, Post.class, headers);
        assertTrue("Response code should be 201", entity.getStatusCode() == HttpStatus.CREATED);
        assertTrue("Checking title of created post", entity.getBody().title.equals("test post 1"));
    }

    @Test
    public void test2FetchPosts() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // add a post 2
        Post newPost2 = new Post();
        newPost2.title = "test post 2";
        newPost2.timestamp = ts2;
        template.postForEntity("http://localhost:8888/outbrain/api/posts", newPost2, Post.class, headers);

        // add a post 3
        Post newPost3 = new Post();
        newPost3.title = "test post 3";
        newPost3.timestamp = ts3;
        template.postForEntity("http://localhost:8888/outbrain/api/posts", newPost3, Post.class, headers);

        // fetch posts
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/api/posts", List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 3); // there should be 3 posts
        assertTrue("checking last post", posts.get(posts.size() - 1).get("title").equals("test post 3")); // post 3 is oldest
    }

    @Test
    public void test3FetchPostsWithLimit() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // fetch posts
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/api/posts?limit=2", List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 2); // there should be only 2 posts
        assertTrue("checking last post", posts.get(posts.size() - 1).get("title").equals("test post 2")); // post 2 is oldest
    }

    @Test
    public void test4FetchPostsWithSince() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // fetch posts
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/api/posts?since=" + ts2, List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 2); // there should be 2 posts
        assertTrue("checking last post", posts.get(posts.size() - 1).get("title").equals("test post 2")); // post 2 is oldest
    }

    @Test
    public void test5ModifyPost() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // modify post
        Post existing = new Post();
        existing.id = "2";
        existing.title = "test post 2 modified";

        // modify post
        template.postForEntity("http://localhost:8888/outbrain/api/posts", existing, Post.class, headers);

        // fetch the post
        ResponseEntity<Post> entity = template.getForEntity("http://localhost:8888/outbrain/api/posts/2", Post.class, headers);

        LOG.info("Got post: {}", entity);
        assertNotNull(entity);
        assertTrue("checking modified post", entity.getBody().title.equals("test post 2 modified"));
    }

    @Test
    public void test6DeletePost() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // delete post
        template.delete("http://localhost:8888/outbrain/api/posts/1", headers);

        // there should now be only one post
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/api/posts", List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 2); // only 2 left
    }
}
