package org.daniran.blogr.api;

import org.daniran.blogr.utils.DateUtils;

/**
 * Created by daniran on 1/13/14.
 */
public class Post {
    public String id;
    public String title;
    public String content;
    public String author;
    public String timestamp;

    public Post() {
    }

    public Post(org.daniran.blogr.dal.Post post) {
        this.id = post.getId().toString();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.timestamp = DateUtils.GetDateAsString(post.getCreationTime());
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
