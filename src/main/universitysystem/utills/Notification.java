package universitysystem.utills;

import java.io.Serializable;

public class Notification  implements Serializable  {
	private String title;
	private String text;

	public Notification() {
		super();
	}

	public Notification(String title) {
		this();
		this.title = title;
	}

	public Notification(String title, String text) {
		this(title);
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return this.title;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public String toString() {
		return title + "\n" + text;
	}

}
