package universitysystem.utills;

import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public class Message extends Mail implements Serializable {
	private String title;
	private String text;
	private boolean viewed;

	{
		viewed = false;
	}

	public Message(){
		super();
	}

	public Message(User sender, String title, String text) {
		super(sender);
		this.title = title;
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;	
	}
	public boolean getViewed() {
		return viewed;	
	}
	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getId() == message.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), title, text);
	}

	public String toString() {
		return super.toString() + "\nTitle: " + title + "\nText: " + text;
	}

}
