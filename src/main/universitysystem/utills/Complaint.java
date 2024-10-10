package universitysystem.utills;

import universitysystem.entity.Student;
import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public class Complaint extends Mail implements Serializable {
	private ImportanceLevel type;
	private Student student;
	private String description;

	{
		this.type = ImportanceLevel.LOW;
	}

	public Complaint(){
		super();
	}

    public Complaint(User sender, Student student, String description) {
        super(sender);
        this.student = student;
        this.description = description;
    }

	public Complaint(User sender, Student student, String description, ImportanceLevel type) {
		this(sender, student, description);
		this.type = type;
	}

	public ImportanceLevel getType() {
		return type;	
	}
	
	public void setType(ImportanceLevel type) {
		this.type = type;
	}

	public Student getStudent() {
		return student;	
	}

	public void setStudent(Student s) {
		this.student = s;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return getId() == complaint.getId();	
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), student, description);
	}

	public String toString() {
		return "Level: " + type + "\n" + super.toString()
				+ ", student: " + student.getId() + "\nDescription: " + description;
	}
	
}
