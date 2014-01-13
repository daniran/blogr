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
public class IntegrationTest {

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
        // create a post
        RestTemplate template = new RestTemplate();
        Post newPost = new Post();
        newPost.title = "test post 1";
        ResponseEntity<Post> entity = template.postForEntity("http://localhost:8888/outbrain/posts", newPost, Post.class, headers);
        assertTrue("Response code should be 201", entity.getStatusCode() == HttpStatus.CREATED);
        assertTrue("Checking title of created post", entity.getBody().title.equals("test post 1"));
    }

    @Test
    public void test2FetchPosts() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Post newPost = new Post();
        newPost.title = "test post 2";
        template.postForEntity("http://localhost:8888/outbrain/posts", newPost, Post.class, headers);

        // fetch posts
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/posts", List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 2);
        assertTrue("checking second post", posts.get(1).get("title").equals("test post 1"));
    }

    @Test
    public void test3ModifyPost() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // modify post
        Post existing = new Post();
        existing.id = "2";
        existing.title = "test post 2 modified";

        // modify post
        template.postForEntity("http://localhost:8888/outbrain/posts", existing, Post.class, headers);

        // fetch the post
        ResponseEntity<Post> entity = template.getForEntity("http://localhost:8888/outbrain/posts/2", Post.class, headers);

        LOG.info("Got post: {}", entity);
        assertNotNull(entity);
        assertTrue("checking modified post", entity.getBody().title.equals("test post 2 modified"));
    }

    @Test
    public void test4DeletePost() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // delete post
        template.delete("http://localhost:8888/outbrain/posts/1", headers);

        // there should now be only one post
        List<Map> posts = template.getForObject("http://localhost:8888/outbrain/posts", List.class, headers);

        assertNotNull(posts);
        LOG.info("Got posts: {}", posts);
        assertTrue("checking size of persisted posts", posts.size() == 1);
        assertTrue("checking remaining post", posts.get(0).get("title").equals("test post 2 modified"));
    }
}
