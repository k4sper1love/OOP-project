package universitysystem.courses;


import universitysystem.data.Data;
import universitysystem.entity.Teacher;

import java.io.Serializable;
import java.util.Objects;
/**
 * Class `Lesson` represents a scheduled lesson in a university system.
 * It includes information about the teacher, lesson type, day of the week, start and end times,
 * maximum number of students, and current student enrollment.
 * The class also provides methods for checking the scheduling status, setting and retrieving lesson details,
 * and determining if a lesson is full.
 */
public class Lesson implements Comparable<Lesson>, Serializable {
	private static boolean schedulingIsOpen;
	private Teacher teacher;
	private LessonType type;
	private DaysWeek day;
	private int startTime;
	private int endTime;
	private int maxStudents;

	static {
		schedulingIsOpen = false;
	}
    /**
     * Default constructor for the Lesson class.
     */
	public Lesson(){
		super();
	}
    /**
     * Сonstructor for lesson with default type and maximum students.
     *
     * @param teacher - teacher for the lesson.
     * @param day - day of the week for the lesson.
     * @param startTime - start time of the lesson.
     * @param endTime - end time of the lesson.
     */
	public Lesson(Teacher teacher, DaysWeek day, int startTime, int endTime) {
		this.teacher = teacher;
		this.type = LessonType.LECTURE;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.maxStudents = 100;
	}
    /**
     * Сonstructor for lesson with specified type, day, start and end times, and maximum students.
     * @param teacher - teacher for the lesson.
     * @param type - type of the lesson (e.g., Lecture, Lab).
     * @param day - day of the week for the lesson.
     * @param startTime - start time of the lesson.
     * @param endTime - end time of the lesson.
     * @param maxStudents - maximum number of students allowed in the lesson.
     */
	public Lesson(Teacher teacher,
				  LessonType type,
				  DaysWeek day,
				  int startTime,
				  int endTime,
				  int maxStudents) {
		this.teacher = teacher;
		this.type = type;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.maxStudents = maxStudents;
	}
    /**
     * Checks scheduling is currently open lesson.
     * @return `true` if scheduling is open, `false` otherwise.
     */
	public static boolean getSchedulingStatus(){
		return schedulingIsOpen;
	}
    /**
     * Sets status of lesson scheduling.
     * @param status `true` to open scheduling, `false` to close scheduling.
     */
	public static void setSchedulingStatus(boolean status){
		schedulingIsOpen = status;
	}
	/**
	 * Sets lesson teacher.
	 * @param t - teacher to be assigned to this lesson.
	 */
	public void setTeacher(Teacher t) {
		this.teacher = t;
	}
	/**
	 * Retrieves the teacher assigned to this lesson.
	 * @return teacher assigned to this lesson.
	 */
	public Teacher getTeacher() {
		return teacher;
	}
	/**
	 * Retrieves the type of this lesson.
	 * @return type of this lesson ( Lecture, Lab).
	 */
	public LessonType getType() {
		return type;
	}
	/**
	 * Sets the type for this lesson
	 * @param l - type to be assigned to this lesson.
	 */
	public void setType(LessonType l) {
		this.type = l;
	}
	/**
	 * Retrieves day of the week.
	 * @return The day of the week.
	 */
	public DaysWeek getDay() {
		return day;
	}
	/**
	 * Sets day of the week .
	 * @param d - day of the week.
	 */
	public void setDay(DaysWeek d) {
		this.day = d;
	}
	/**
	 * Retrieves start time.
	 * @return start time of this lesson.
	 */
	public int getStartTime() {
		return startTime;
	}
	/**
	 * Sets start time.
	 * @param time The start time.
	 */
	public void setStartTime(int time) {
		this.startTime = time;
	}
	/**
	 * Retrieves the end time.
	 * @return The end time.
	 */
	public int getEndTime() {
		return endTime;
	}
	/**
	 * Sets the end time.
	 * @param time The end time.
	 */
	public void setEndTime(int time) {
		this.endTime = time;
	}
	/**
	 * Sets maximum number of students for this lesson.
	 * @param cnt - maximum number of students to be assigned to this lesson.
	 */
	public void setMaxStudents(int cnt) {
		this.maxStudents = cnt;
	}
	/**
	 * Retrieves the maximum number of students.
	 * @return The maximum number of students.
	 */
	public int getMaxStudents() {
		return maxStudents;
	}
	/**
	 * Retrieves the current number of students enrolled.
	 * @return The current number of students enrolled.
	 */
	public long getCurrentStudents(){
        return Data.getInstance().getStudents().stream()
				.flatMap(n -> n.getCurrentCourses().stream())
				.flatMap(n -> n.getLessons().stream())
				.filter(n -> n.equals(this)).count();
	}
	/**
	 * Checks if this lesson is full based on the current number of enrolled students.
	 * @return `true` if the lesson is full, `false` otherwise.
	 */
	public boolean isLessonFull(){
		return getCurrentStudents() >= getMaxStudents();
	}
    /** Performs a check if this lesson is equal to the specified object
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Lesson lesson = (Lesson) o;
		return startTime == lesson.startTime && Objects.equals(teacher, lesson.teacher)
				&& type == lesson.type && day == lesson.day;
	}
    /**
     * Hash code for this lesson.
     * @return Hash code value for lesson.
     */
	@Override
	public int hashCode() {
		return Objects.hash(teacher, type, day, startTime);
	}
    /**
     * Returns Lesson a string representation.
     * @return Representation Lesson.
     */
	@Override
	public String toString() {
		return "Lesson type: " + type + ", time: " + startTime + ":00 - " + endTime + ":00"
				+ "\nTeacher: " + teacher.getLastname() + teacher.getFirstname()
				+ (teacher.getMiddlename() != null ? " " + teacher.getMiddlename() : "");
	}
    /**
     * Compares two lessons for ordering based on the day of the week and start time.
     *
     * @param o The lesson to be compared.
     * @return returned whether is less than, equal to, or greater than the specified project, respectively
     */
	@Override
	public int compareTo(Lesson o) {
		if (this.day == o.day)
			return Integer.compare(this.startTime, o.startTime);
		return day.compareTo(o.day);
	}
}


