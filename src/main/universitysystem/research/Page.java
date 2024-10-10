package universitysystem.research;

import java.io.Serializable;
import java.util.Objects;

public class Page implements Serializable
{
	private int id;
	private static int nextId = 1;
	private String topic;
	private String text;

	{
		this.id = nextId++;
	}
	public Page(){
		super();
	}

	public Page(String topic,String text) {
		this.topic=topic;
		this.text=text;
	}
	
	public int getId() {
		return id;	
	}

	public String getTopic() {
		return topic;	
	}

	public String getText() {
		return text;	
	}

	public void setTopic(String topic) {
		// TODO implement me
		this.topic = topic;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id &&
                Objects.equals(topic, page.topic) &&
                Objects.equals(text, page.text);
	}
	
    public int hashCode() {
        return Objects.hash(id, topic, text);
    }
	
	public String toString() {
		return "Topic: " + topic + "\nText: " + text;
	}

	
}
