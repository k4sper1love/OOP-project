package universitysystem.data;

import java.io.Serializable;
import java.util.*;

//TODO везде сделать логи
public class Log implements Serializable
{
	private final int id;
	private String text;
	private final Date time;

	{
		id = Data.getInstance().getLogId();
		time = new Date();
	}

	public Log(){
		super();
	}

	public Log(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text){
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Log log = (Log) o;
		return id == log.id && Objects.equals(text, log.text) && Objects.equals(time, log.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, time);
	}

	@Override
	public String toString() {
		return "id: " + id + ", time: " + time + "\nAction: " + text;
	}
}

