package universitysystem.utills;

import universitysystem.courses.Course;
import universitysystem.courses.RegisteredCourse;
import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public class RegistrationRequest extends Request implements Serializable
{
	private Course course;
	private RegistrationType type;

	{
		type = RegistrationType.ADD;
	}

	public RegistrationRequest() {
		super();
	}
	public RegistrationRequest(User sender, Course course) {
		super(sender);
		this.course = course;
	}
	public RegistrationRequest(User sender, Course course, RegistrationType type) {
		this(sender, course);
		this.type = type;
	}

	public Course getCourse() {
		return course;	
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public RegistrationType getRegistrationType() {

		return type;
	}
	
	public void setRegistrationType(RegistrationType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RegistrationRequest that = (RegistrationRequest) o;
		return Objects.equals(course, that.course) && type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), course, type);
	}

	@Override
	public String toString() {
		return super.toString() + ", type: " + type + ", course: " + course.getCode();
	}
}
