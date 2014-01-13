package com.outbrain.test.tests;

import com.outbrain.test.api.Post;
import com.outbrain.test.api.PostService;
import com.outbrain.test.web.PostsQueriesController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by daniran on 1/13/14.
 */
public class ControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    PostsQueriesController controller;

    @Mock
    PostService postService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testGetPost() throws Exception {

        Post value = new Post();
        value.id = "5";
        value.title = "mock post";

        when(postService.getPost(5)).thenReturn(value);

        this.mockMvc.perform(
                get("/posts/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.title").value("mock post"));
    }


    @Test
    public void testGetBadPost() throws Exception {

        this.mockMvc.perform(
                get("/posts/{id}", 4)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post value = new Post();
            value.id = "" + i;
            value.title = "mock post" + i;
            posts.add(value);
        }

        when(postService.getPosts(10)).thenReturn(posts);

        this.mockMvc.perform(
                get("/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[3].id").value("4"))
                .andExpect(jsonPath("$[15].id").doesNotExist());
    }
}

