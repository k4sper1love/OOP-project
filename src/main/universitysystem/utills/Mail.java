package universitysystem.utills;


import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public abstract class Mail implements Serializable {
	private static int numId = 0;
	private int id;
	private User sender;
	private final Date date;

	{
		id = numId++;
		date = new Date();
	}

	public Mail(){
		super();
	}

    public Mail(User sender) {
        this.sender = sender;
    }

	public int getId() {
		return id;	
	}

    public Date getDate() {
        return date;
    }
	
	public User getSender() {
		return sender;	
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return id == mail.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sender, date);
	}

	public String toString() {
		return "id: " + id + ", sender: " + sender.getId();
	}
	
}



