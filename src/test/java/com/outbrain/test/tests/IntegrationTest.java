package com.outbrain.test.tests;

import com.outbrain.test.api.Post;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniran on 1/13/14.
 */
public class IntegrationTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testFetchPosts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate template = new RestTemplate();
        List<Post> posts = template.getForObject(
                "http://localhost:8888/outbrain/posts",
                List.class, headers);

        assertNotNull(posts);
        assertTrue(posts.size() == 10);

    }
}
