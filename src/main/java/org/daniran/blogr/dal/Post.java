package org.daniran.blogr.dal;

import org.daniran.blogr.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(name = "posts")
public class Post {

    public Post(String title, String content, String author, String timestamp) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationTime = timestamp == null ? new Date(System.currentTimeMillis()) : DateUtils.StringDateToDate(timestamp);
    }

    public Post() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    private String title;

    private String content;

    private String author;

    @Column(name = "creation_time")
    private Date creationTime;
}
