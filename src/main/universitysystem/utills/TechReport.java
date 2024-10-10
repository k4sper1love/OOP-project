package universitysystem.utills;

import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public class TechReport extends Mail implements Serializable
{
	private String text;
	private ReportCategory category;

	{
		category = ReportCategory.OTHER;
	}

	public TechReport() {
		super();
	}

	public TechReport(User sender, String text) {
		super(sender);
		this.text = text;
	}

	public TechReport(User sender, String text, ReportCategory category) {
		this(sender, text);
		this.category = category;
	}

	public void setText(String s) {
		this.text = s;
	}

	public String getText() {
		return text;	
	}

	public ReportCategory getCategory() {
		return category;	
	}
	
	public void setCategory(ReportCategory r) {
		this.category = r;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		TechReport that = (TechReport) o;
		return Objects.equals(text, that.text) && category == that.category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), text, category);
	}

	@Override
	public String toString() {
		return super.toString() + ", category: " + category + "\nText: " + text;
	}
}


