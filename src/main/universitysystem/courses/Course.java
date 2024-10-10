package universitysystem.courses;

import universitysystem.data.Data;

import java.io.Serializable;
import java.util.*;
/**
 * class `Course` presents an academic course offered in a university.
 * It includes details such as course code, title, description, credits, and other relevant information. 
 * Courses can have prerequisites, associated faculties, lessons, and registration status.
 */
public class Course implements Serializable
{
	private static boolean registrationIsOpen;
	private HashSet<Course> prerequisites;
	private String title;
	private String description;
	private String code;
	private int lectures;
	private int practices;
	private int labs;
	private int credits;
	private HashSet<Faculty> majorFaculties;
	private HashSet<Faculty> minorFaculties;
	private HashSet<Faculty> freeElectiveFaculties;
	private TreeSet<Lesson> lessons;
	private Semester semester;
	private int year;

	static{
		registrationIsOpen = false;
	}
    /**
     * Default constructor.
     */
	public Course(){
		super();
	}
    /**
     * Constructor for Course with basic information.
     *
     * @param title - title of the course.
     * @param description - description of the course.
     * @param code - unique code assigned to the course.
     * @param lectures - number of lecture hours for the course.
     * @param practices - number of practice hours for the course.
     * @param labs - number of lab hours for the course.
     * @param credits  - credit value of the course.
     * @param year  - academic year in which the course is offered.
     * @param semester -  semester in which the course is offered.
     */
	public Course(String title,
				  String description,
				  String code,
				  int lectures,
				  int practices,
				  int labs,
				  int credits,
				  int year,
				  Semester semester)
	{
		this.prerequisites = new HashSet<>();
		this.title = title;
		this.description = description;
		this.code = code;
		this.lectures = lectures;
		this.practices = practices;
		this.labs = labs;
		this.year = year;
		this.credits = credits;
		this.majorFaculties = new HashSet<>();
		this.minorFaculties = new HashSet<>();
		this.freeElectiveFaculties = new HashSet<>();
		this.lessons = new TreeSet<>();
		this.semester = semester;
	}
    /**
     * Parameterized constructor to create a Course instance with all details.
     *
     * @param prerequisites  - set of prerequisites for the course.
     * @param title - title of the course.
     * @param description - description of the course.
     * @param code - unique code assigned to the course.
     * @param lectures - number of lecture hours for the course.
     * @param practices - number of practice hours for the course.
     * @param labs - number of lab hours for the course.
     * @param credits - credit value of the course.
     * @param year - academic year in which the course is offered.
     * @param majorFaculties - set of major faculties associated with the course.
     * @param minorFaculties - set of minor faculties associated with the course.
     * @param freeElectiveFaculties - set of free elective faculties associated with the course.
     * @param lessons - set of lessons associated with the course.
     * @param semester - semester in which the course is offered.
     */
	public Course(HashSet<Course> prerequisites,
				  String title,
				  String description,
				  String code,
				  int lectures,
				  int practices,
				  int labs,
				  int credits,
				  int year,
				  HashSet<Faculty> majorFaculties,
				  HashSet<Faculty> minorFaculties,
				  HashSet<Faculty> freeElectiveFaculties,
				  TreeSet<Lesson> lessons,
				  Semester semester)
	{
		this.prerequisites = prerequisites;
		this.title = title;
		this.description = description;
		this.code = code;
		this.lectures = lectures;
		this.practices = practices;
		this.labs = labs;
		this.credits = credits;
		this.majorFaculties = majorFaculties;
		this.minorFaculties = minorFaculties;
		this.freeElectiveFaculties = freeElectiveFaculties;
		this.lessons = lessons;
		this.semester = semester;
		this.year = year;
	}
	/**
	 * Retrieves title of the course.
	 * @return title of the course.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Sets the title of the course.
	 * @param title - new title for the course.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Retrieves the description of the course.
	 * @return description of the course.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of the course.
	 * @param description The new description for the course.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Retrieves the unique code assigned to the course.
	 * @return Code of the course.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Sets the unique code assigned to the course.
	 * @param code - new code for the course.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Retrieves number of lectures.
	 * @return number of lectures.
	 */
	public int getLectures() {
		return lectures;
	}
	/**
	 * Sets number of lectures.
	 * @param lectures - new number of lectures.
	 */
	public void setLectures(int lectures) {
		this.lectures = lectures;
	}
	/**
	 * Retrieves number of practices.
	 * @return number of practices.
	 */
	public int getPractices() {
		return practices;
	}
	/**
	 * Sets number of practices.
	 * @param practices The new number of practices.
	 */
	public void setPractices(int practices) {
		this.practices = practices;
	}
	/**
	 * Retrieves number of labs.
	 * @return The number of labs.
	 */
	public int getLabs() {
		return labs;
	}
	/**
	 * Sets number of labs.
	 * @param labs - new number of labs.
	 */
	public void setLabs(int labs) {
		this.labs = labs;
	}
	/**
	 * Retrieves number of credits.
	 * @return number of credits.
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * Sets number of credits.
	 * @param credits The new number of credits.
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}
	/**
	 * Sets major faculties associated.
	 * @param majorFaculties - set of major faculties.
	 */
	public void setMajorFaculties(HashSet<Faculty> majorFaculties) {
		this.majorFaculties = majorFaculties;
	}
	/**
	 * Sets the minor faculties associated.
	 * @param minorFaculties - set of minor faculties.
	 */
	public void setMinorFaculties(HashSet<Faculty> minorFaculties) {
		this.minorFaculties = minorFaculties;
	}
	/**
	 * Sets the free elective faculties.
	 * @param freeElectiveFaculties - set of free elective faculties.
	 */
	public void setFreeElectiveFaculties(HashSet<Faculty> freeElectiveFaculties) {
		this.freeElectiveFaculties = freeElectiveFaculties;
	}
	/**
	 * Retrieves major faculties.
	 * @return Set of major faculties.
	 */
	public HashSet<Faculty> getMajorFaculties() {
		return this.majorFaculties;
	}

	/**
	 * Retrieves the minor faculties.
	 * @return Set of minor faculties.
	 */
	public HashSet<Faculty> getMinorFaculties() {
		return this.minorFaculties;
	}
	/**
	 * Retrieves free elective faculties.
	 * @return Set of free elective faculties.
	 */
	public HashSet<Faculty> getFreeElectiveFaculties() {
		return freeElectiveFaculties;
	}
	/**
	 * Adds faculty major to the set of major faculties.
	 * @param f - added major faculty.
	 * @return `true` if the major faculty is successfully added, `false` otherwise.
	 */
	public boolean addMajorFaculty(Faculty f) {
		return majorFaculties.add(f);
	}
	/**
	 * Adds minor faculty to the set.
	 * @param f - added minor faculty.
	 * @return `true` if the minor faculty is successfully added, `false` otherwise.
	 */
	public boolean addMinorFaculty(Faculty f) {
		return minorFaculties.add(f);
	}
	/**
	 * Adds free elective faculty to the set.
	 * @param f - free elective faculty to be added.
	 * @return `true` if the free elective faculty is successfully added, `false` otherwise.
	 */
	public boolean addFreeFaculty(Faculty f) {
		return freeElectiveFaculties.add(f);
	}
	/**
	 * Removes a major faculty from the set.
	 * @param f - major faculty to be removed.
	 * @return `true` if the major faculty is successfully removed, `false` otherwise.
	 */
	public boolean removeMajorFaculty(Faculty f) {
		if (this.majorFaculties != null) {
			return this.majorFaculties.remove(f);
		}

		return false;
	}
	/**
	 * Removes a minor faculty from the set.
	 * @param f - minor faculty to be removed.
	 * @return `true` if the minor faculty is successfully removed, `false` otherwise.
	 */
	public boolean removeMinorFaculty(Faculty f) {
		if (this.minorFaculties != null) {
			return this.minorFaculties.remove(f);
		}

		return false;
	}
	/**
	 * Removes a free elective faculty from the set of free elective faculties.
	 * @param f - free elective faculty to be removed.
	 * @return `true` if the free elective faculty is successfully removed, `false` otherwise.
	 */
	public boolean removeFreeFaculty(Faculty f) {
		if (this.freeElectiveFaculties != null) {
			return this.freeElectiveFaculties.remove(f);
		}
		return false;
	}
	/**
	 * Retrieves the set of lessons.
	 * @return The set of lessons.
	 */
	public TreeSet<Lesson> getLessons() {
		return this.lessons;
	}
	/**
	 * Sets the set of lessons associated with the course.
	 * @param lessons - new set of lessons.
	 */
	public void setLessons(TreeSet<Lesson> lessons) {
		this.lessons = lessons;
	}
	/**
	 * Adds a lesson to the set of lessons.
	 * @param l - lesson to be added.
	 * @return `true` if the lesson is successfully added, `false` otherwise.
	 */
	public boolean addLesson(Lesson l) {
		return lessons.add(l);
	}
	/**
	 * Removes a lesson from the set of lessons.
	 * @param l - lesson to be removed.
	 * @return `true` if the lesson is successfully removed, `false` otherwise.
	 */
	public boolean removeLesson(Lesson l) {
		if (this.lessons != null) {
			return this.lessons.remove(l);
		}
		return false;
	}
	/**
	 * Retrieves semester which the course is offered.
	 * @return Semester of the course.
	 */
	public Semester getSemester() {
		return semester;
	}
	/**
	 * Sets semester in which the course is offered.
	 * @param semester -  semester for the course.
	 */
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	/**
	 * Retrieves the academic year of the course.
	 * @return academic year of the course.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Sets the academic year of the course.
	 * @param year - new academic year for the course.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * formula representing the distribution of labs,lectures, and practices.
	 * @return formula format "lectures/labs/practices".
	 */
	public String getFormula() {
		return lectures + "/" + labs + "/" + practices;
	}
	/**
	 * Retrieves the status registration.
	 * @return `true` if registration is open, `false` otherwise.
	 */
	public static boolean getRegistrationStatus() {
		return registrationIsOpen;
	}
	/**
	 * Sets registration status for the course.
	 * @param registrationIsOpen - new registration status.
	 */
	public static void setRegistrationStatus(boolean registrationIsOpen) {
		Course.registrationIsOpen = registrationIsOpen;
	}
	/**
	 * Retrieves the set of prerequisites for the course.
	 * @return Set of prerequisites for the course.
	 */
	public HashSet<Course> getPrerequisites() {
		return this.prerequisites;
	}
	/**
	 * Sets prerequisites for the course.
	 * @param prerequisites  set of prerequisites to be set for the course.
	 */
	public void setPrerequisites(HashSet<Course> prerequisites) {
		this.prerequisites = prerequisites;
	}
	/**
	 * Adds a prerequisite course to the set of prerequisites.
	 * @param c - course to be added as a prerequisite.
	 * @return `true` if the course was added successfully, `false` otherwise.
	 */
	public boolean addPrerequisite(Course c) {
		return prerequisites.add(c);
	}
	/**
	 * Removes a prerequisite course from the set of prerequisites.
	 * @param c - course to be removed as a prerequisite.
	 * @return `true` if the course was removed successfully, `false` otherwise.
	 */
	public boolean removePrerequisite(Course c) {
		if (this.prerequisites != null) {
			return this.prerequisites.remove(c);
		}
		return false;
	}
	/**
	 * Creates a clone of the course with the specified year and semester.
	 * @param year - new academic year for the cloned course.
	 * @param semester - new semester for the cloned course.
	 * @return cloned course.
	 */
	public Object copyCourse(int year, Semester semester) throws CloneNotSupportedException {
		Course clonedCourse = (Course) this.clone();
		clonedCourse.setYear(year);
		clonedCourse.setSemester(semester);
		return clonedCourse;
	}
	/**
	 * Checks if the current course is equal to another object.
	 * @param o The object to compare with.
	 * @return `true` if the courses are equal, `false` otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return (Objects.equals(title, course.title) || Objects.equals(code, course.code))
				&& (Objects.equals(year, course.year) && Objects.equals(semester, course.semester));
	}
	/**
	 * Hash code for the course.
	 * @return Hash code for the course.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(title, code, year, semester);
	}
	/**
	 * Returns representation of the course.
	 * @return string representation of the course.
	 */
	@Override
	public String toString() {
		return code + " " + title + "\nYear: " + year + ", semester: " + semester
				+ ", credits: " + credits + " ECTS";
	}
	/**
	 * Creates a shallow copy of the course.
	 * @return cloned course.
	 * @throws CloneNotSupportedException if cloning is not supported.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Course clonedCourse = (Course) super.clone();
		clonedCourse.lessons = new TreeSet<>(lessons);
		return clonedCourse;
	}
}
