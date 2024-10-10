package universitysystem.courses;

import java.io.Serializable;
import java.util.*;
/**
 * The `RegisteredCourse` class presents a student's registration in course.
 * information about the course, course type, lessons, marks, attestation scores,
 * final exam score, and other relevant details.
 */
public class RegisteredCourse implements Serializable
{
	private Course course;
	private boolean retake;
	private CourseType type;
	private TreeSet<Lesson> lessons;
	private Vector<Mark> marks;
	private double firstAttestation;
	private double secondAttestation;
	private double finalExam;
	private Date lastUpdateAttestation;

	{
		lessons = new TreeSet<>();
		lastUpdateAttestation = new Date();
		retake = false;
		this.type = null;
		this.lessons = new TreeSet<Lesson>();
		this.marks = new Vector<Mark>();
		this.firstAttestation = 0;
		this.secondAttestation = 0;
		this.finalExam = 0;
		this.lastUpdateAttestation = new Date();
		this.retake = false;
	}
    /**
     * Default constructor.
     */
	public RegisteredCourse(){
		super();
	}
    /**
     * Constructor for RegisteredCourse with a specified course.
     * @param course - course for which the student is registered.
     */
	public RegisteredCourse(Course course) {
		this();
		this.course = course;
	}
    /**
     * Constructor for RegisteredCourse with a specified course and course type.
     * @param course The course for which the student is registered.
     * @param type   The type of the course (e.g., Lecture, Lab).
     */
	public RegisteredCourse(Course course, CourseType type) {
		this(course);
		this.type = type;
	}
	public RegisteredCourse(Course course, CourseType type, TreeSet<Lesson> lessons) {
		this(course,type);
		this.lessons = lessons;
	}
	
	public RegisteredCourse(Course course, CourseType type, TreeSet<Lesson> lessons, Vector<Mark> marks) {
		this(course, type, lessons);
		this.marks = marks;
	}

	public RegisteredCourse(Course course, CourseType type, TreeSet<Lesson> lessons, Vector<Mark> marks,
			double firstAttestation, double secondAttestation, double finalExam,
							Date lastUpdateAttestation, boolean retake) {
		super();
		this.course = course;
		this.type = type;
		this.lessons = lessons;
		this.marks = marks;
		this.firstAttestation = firstAttestation;
		this.secondAttestation = secondAttestation;
		this.finalExam = finalExam;
		this.lastUpdateAttestation = lastUpdateAttestation;
		this.retake = retake;
	}
    /**
     * Retrieves the course for which the student is registered.
     * @return  course for which the student is registered.
     */
	public Course getCourse() {
		return course;	
	}
    /**
     * Sets the course for which the student is registered.
     * @param c - course to be assigned.
     */
	public void setCourse(Course c) {
		this.course = c;
	}

    /**
     * if the student in course taking a retake .
     * @return `true` if it's a retake, `false` otherwise.
     */
	public boolean isRetake() {
		return retake;	
	}
    /**
     * Sets whether the student in course taking a retake.
     * @param b - `true` if it's a retake, `false` otherwise.
     */
	public void setRetake(boolean b) {
		this.retake = b;	
	}
    /**
     * Retrieves the type of the course.
     * @return type of the course.
     */
	public CourseType getType() {
		return type;	
	}
    /**
     * Sets the type of the course.
     * @param c - type to be assigned.
     */
	public void setType(CourseType c) {
		this.type = c;
	}
    /**
     * Adds a lesson to the set of lessons
     * @param l - lesson to be added.
     * @return `true` if the lesson was successfully added, `false` otherwise.
     */
	public boolean addLesson(Lesson l) {
		lessons.add(l);
		return true;
	}
    /**
     * Removes lesson from the set of lessons for this registered course.
     * @param l - lesson to be removed.
     * @return `true` if the lesson was successfully removed, `false` otherwise.
     */
	public boolean removeLesson(Lesson l) {
		if (this.lessons != null) {
			return this.lessons.remove(l);
		}
		return false;
	}
    /**
     * Retrieves the set of lessons.
     * @return  Set of lessons for this registered course.
     */
	public TreeSet<Lesson> getLessons() {
		return lessons;	
	}
    /**
     * Retrieves score of the first attestation.
     * @return score of the first attestation.
     */
	public double getFirstAttestation() {
		return this.firstAttestation;
	}
    /**
     * Sets the score of the first attestation.
     * @param d - score of the first attestation to be assigned.
     */
	public void setFirstAttestation(double d) {
		this.firstAttestation = d;	
	}
    /**
     * Retrieves  score of the second attestation.
     * @return score of the second attestation.
     */
	public double getSecondAttestation() {
		return this.secondAttestation;
	}
    /**
     * Sets the score of the second attestation.
     * @param d - score of the second attestation to be assigned.
     */
	public void setSecondAttestation(double d) {
		this.secondAttestation = d;	
	}
    /**
     * Retrieves  score of the final exam.
     * @return score of the final exam.
     */
	public double getFinalExam() {
		return this.finalExam;
	}
    /**
     * Sets score of the final exam.
     * @param d - score of the final exam to be assigned.
     */
	public void setFinalExam(double d) {
		this.finalExam = d;
	}
    /**
     * Sets date of the last update to the attestation scores.
     * @param d - date of the last update to be assigned.
     */
	public void setLastUpdateAttestation(Date d) {
		this.lastUpdateAttestation = d;
	}
    /**
     * Sets the date of the last update to the current date.
     */
	public void setLastUpdateAttestation() {
		this.lastUpdateAttestation = new Date();
	}
    /**
     * Retrieves date of the last update to the attestation scores.
     * @return date of the last update to the attestation scores.
     */
	public Date getLastUpdateAttestation() {
		return this.lastUpdateAttestation;
	}
    /**
     * Adds mark to the vector of marks for this course.
     * @param m - added mark .
     * @return `true` if the mark was successfully added, `false` otherwise.
     */
	public boolean addMark(Mark m) {
		marks.add(m);
		return true;
	}
    /**
     * Removes mark from the vector of marks for this course.
     * @param m - removed mark.
     * @return `true` if the mark was successfully removed, `false` otherwise.
     */
	public boolean removeMark(Mark m) {
		if (this.marks != null) {
			return this.marks.remove(m);
		}

		return false;
	}
    /**
     * Retrieves the vector of marks.
     * @return The vector of mark.
     */
	public Vector<Mark> getMarks() {
		return marks;	
	}
    /**
     * Converts the numeric grade to the corresponding GPA.
     * @return GPA equivalent of the combined grades.
     */
	public double getGpa() {
		if(finalExam == 0 && !isRetake()) return 0;
		return Mark.gradeToGpa(firstAttestation + this.secondAttestation + this.finalExam);
	}
    /**
     * Verifies if the student has entered attestation scores for this course.
     * @return `true` if attestation scores have been added, `false` otherwise.
     */
	public boolean putAttestation(){
		if (firstAttestation == 0 && secondAttestation == 0){
			firstAttestation = marks.stream().mapToDouble(Mark::getMark).sum();
			lastUpdateAttestation = new Date();
		} else if(secondAttestation == 0){
			secondAttestation = marks.stream().filter(n -> n.getDate().compareTo(lastUpdateAttestation) > 0)
					.mapToDouble(Mark::getMark).sum();
			lastUpdateAttestation = new Date();
			if(firstAttestation + secondAttestation < 29.5) retake = true;
		} else return false;
		return true;
	}
    /**
     * Sets final exam score for this course.
     * @param d - final exam score to be assigned.
     * @return `true` if the final exam score was successfully set, `false` otherwise.
     */
	public boolean putFinalExam(Double d){
		if(isRetake()) return false;
		setFinalExam(d);
		if(finalExam < 20) retake = true;
		lastUpdateAttestation = new Date();
		return true;
	}
    /**
     * Generates a hash code for this RegisteredCourse based on the course and lessons.
     * @return The hash code for this RegisteredCourse.
     */
	@Override
	public int hashCode() {
		return Objects.hash(course, lessons);
	}
    /**
     * Verifies if this RegisteredCourse is equal to another object based on the course.
     * @param o The object to compare to this RegisteredCourse.
     * @return `true` if the RegisteredCourses are equal, `false` otherwise.
     */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegisteredCourse registeredCourse = (RegisteredCourse) o;
		return Objects.equals(course, registeredCourse.course);	
	}
    /**
     * Returns text representation of this RegisteredCourse, including course details and scores.
     * @return Representation of this RegisteredCourse.
     */
	public String toString() {
		return course.toString() + "Type: " + type + "\nFirst att: " +firstAttestation
				+ ". Second att: " + secondAttestation + ". Final exam: " + finalExam
				+ "\nGPA: " + getGpa();
	}
}

