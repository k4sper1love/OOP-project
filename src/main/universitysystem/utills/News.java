package universitysystem.utills;

import java.io.Serializable;
import java.util.*;
import java.util.LinkedHashSet;
import java.util.Objects;

public class News implements Serializable {
	private static int idCounter = 0;
	private int id;
	private TopicType topic;
	private String title;
	private String text;
	private Date publicationDate;
	private LinkedHashSet<Comment> comments;

	{
		id = idCounter++;
		publicationDate = new Date();
		comments = new LinkedHashSet<>();
		topic = TopicType.OTHER;
	}

	public News() {
		 super();
	}

	public News(String title, String text) {
		this();
		this.title = title;
		this.text = text;
	}

	public News(TopicType topic, String title, String text) {
		this(title,text);
		this.topic = topic;
	}

	public void setTopic(TopicType topic) {
		this.topic = topic;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return this.id;
	}

	public TopicType getTopic() {
		return this.topic;
	}

	public String getTitle() {
		return this.title;
	}

	public String getText() {
		return this.text;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void addComment(Comment c){
		comments.add(c);
	}
	public void removeComment(Comment c) {
		comments.remove(c);
	}
	public LinkedHashSet<Comment> getComments(){
		return comments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return id == news.id &&
				Objects.equals(topic, news.topic) &&
				Objects.equals(title, news.title) &&
				Objects.equals(publicationDate, news.publicationDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, topic, title, text, publicationDate, comments);
	}

	@Override
	public String toString() {
		return "@" + id + " " + topic + " " + publicationDate +
				"\nTitle: " + title + "\n" + text;
	}
}
