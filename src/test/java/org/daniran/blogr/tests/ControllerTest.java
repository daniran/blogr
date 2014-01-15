package org.daniran.blogr.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.daniran.blogr.api.Post;
import org.daniran.blogr.api.PostService;
import org.daniran.blogr.utils.DateUtils;
import org.daniran.blogr.web.PostsCommandsController;
import org.daniran.blogr.web.PostsQueriesController;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller tests:
 * <p/>
 * these tests operate a mock controller
 * there main purpose is to verify the URL mappings to the correct Controller methods
 */
public class ControllerTest extends BaseTest {

    MockMvc mockQueriesMvc;

    MockMvc mockCommandsMvc;

    @InjectMocks
    PostsQueriesController queriesController;

    @InjectMocks
    PostsCommandsController commandsController;

    @Mock
    PostService postService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        this.mockQueriesMvc = MockMvcBuilders.standaloneSetup(queriesController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();


        this.mockCommandsMvc = MockMvcBuilders.standaloneSetup(commandsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testGetPost() throws Exception {

        Post value = new Post();
        value.id = "5";
        value.title = "mock post";
        value.timestamp = DateUtils.GetDateAsString(new Date());

        when(postService.getPost(5)).thenReturn(value);

        this.mockQueriesMvc.perform(
                get("/api/posts/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.title").value("mock post"));
    }


    @Test
    public void testBadGetPost() throws Exception {

        this.mockQueriesMvc.perform(
                get("/api/posts/{id}", 4)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testModifyPost() throws Exception {

        Post value = new Post();
        value.id = "5";
        value.title = "mock post modified";
        value.timestamp = DateUtils.GetDateAsString(new Date());

        when(postService.savePost(any(Post.class))).thenReturn(value);

        ObjectMapper mapper = new ObjectMapper();
        String postJson = mapper.writer().writeValueAsString(value);
        this.mockCommandsMvc.perform(
                post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andDo(print())
                .andExpect(jsonPath("$.title").value("mock post modified"));
    }

    @Test
    public void testGetPostsWithLimit() throws Exception {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post value = new Post();
            value.id = "" + i;
            value.title = "mock post" + i;
            value.timestamp = DateUtils.GetDateAsString(new Date());
            posts.add(value);
        }

        // post with limit
        when(postService.getPosts(5, null)).thenReturn(posts.subList(0, 5));

        // post with no limit
        when(postService.getPosts(0, null)).thenReturn(posts);

        // fetch posts with limit 5
        this.mockQueriesMvc.perform(
                get("/api/posts?limit=5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[3].id").value("4"))
                .andExpect(jsonPath("$[5].id").doesNotExist());
    }

    @Test
    public void testGetPostsWithSince() throws Exception {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Post value = new Post();
            value.id = "" + i;
            value.title = "mock date post" + i;
            posts.add(value);
        }

        posts.get(0).timestamp = ts1;
        posts.get(1).timestamp = ts2;
        posts.get(2).timestamp = ts3;

        // post with since
        when(postService.getPosts(0, DateUtils.StringDateToDate(ts2))).thenReturn(ImmutableList.<Post>of(posts.get(0), posts.get(1)));

        // post with since
        when(postService.getPosts(0, DateUtils.StringDateToDate(ts3))).thenReturn(ImmutableList.<Post>of(posts.get(0)));

        // post with no since
        when(postService.getPosts(0, null)).thenReturn(posts);

        // fetch posts with limit 5
        this.mockQueriesMvc.perform(
                get("/api/posts?since=" + ts2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[1].id").value("2")) // the earliest post should be 2
                .andExpect(jsonPath("$[5].id").doesNotExist());
    }
}

