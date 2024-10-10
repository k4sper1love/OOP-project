package universitysystem.utills;

import universitysystem.entity.User;
import universitysystem.interfaces.CanCreate;

import java.io.Serializable;
import java.util.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Comment implements Serializable {
	private static int idCounter = 0;
    private int id;
    private User author;
    private String text;
    private Date publishedDate;
    private LinkedHashSet<Comment> subComments;

    {
        id = idCounter++;
        publishedDate = new Date();
        subComments = new LinkedHashSet<>();
    }

    public Comment() {
        super();
    }

    public Comment(User author, String text) {
        this();
        this.author = author;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public LinkedHashSet<Comment> getSubComments() {
        return subComments;
    }

    public boolean addSubComment(Comment subComment) {
        return subComments.add(subComment);
    }

    public boolean removeSubComment(Comment subComment) {
        return subComments.remove(subComment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(publishedDate, comment.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, text, publishedDate);
    }

    @Override
    public String toString() {
        return "@" + id + " " +  author.getId() + " " + publishedDate
                + "\n" + text;
    }
}

