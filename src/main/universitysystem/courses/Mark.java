package universitysystem.courses;

import java.io.Serializable;
import java.util.*;
/**
 * Class `Mark` presents a student's academic performance.
 * information about the mark value, lesson type, and the date the mark was assigned.
 */
public class Mark implements Serializable
{
	private double value;
	private LessonType lessonType;
	private Date date;

	{
		date = new Date();
	}
    /**
     * Default constructor for the Mark class.
     */
	public Mark(){
		super();
	}
    /**
     * Constructor for mark with a specified value.
     * default lesson type is set to PRACTICE.
     * @param value The mark value to be assigned.
     */
	public Mark(double value) {
		this.value = value;
		this.lessonType = LessonType.PRACTICE;
	}
    /**
     * Parameterized constructor for creating a mark with a specified value and lesson type.
     * @param value - mark value to be assigned.
     * @param lessonType - type of lesson for which the mark is assigned.
     */
	public Mark(double value, LessonType lessonType) {
		this(value);
		this.lessonType = lessonType;
	}
    /**
     * Sets the mark value for this mark.
     * @param value - mark value to be set.
     */
	public void setMark(double value) {
		this.value = value;
	}
    /**
     * Retrieves the mark value for this mark.
     * @return  mark value for this mark.
     */
	public double getMark() {
		return value;
	}
    /**
     * Retrieves the lesson type for which this mark is assigned.
     * @return lesson type for this mark.
     */
	public LessonType getLessonType() {
		return lessonType;
	}
    /**
     * Sets the lesson type for this mark.
     * @param l - lesson type to be assigned.
     */
	public void setLessonType(LessonType l) {
		this.lessonType = l;
	}
    /**
     * Sets the date for this mark.
     * @param d - date to be assigned to this mark.
     */
	public void setDate(Date d) {
		this.date = d;
	}
    /**
     * Retrieves the date when this mark was assigned.
     * @return date when this mark was assigned.
     */
	public Date getDate() {
		return date;
	}
    /**
     * Returns string representation of this mark, including the date, lesson type, and value.
     * @return representation of this mark.
     */
	@Override
	public String toString() {
		return "Date: " + date + ", lesson type: " + lessonType + ", value: " + value;
	}
    /**
     * Checks if this mark is equal to another mark based on value, lesson type, and date.
     * @param o The object to compare to this mark.
     * @return `true` if the marks are equal, `false` otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Mark mark = (Mark) o;
		return Double.compare(value, mark.value) == 0 && lessonType == mark.lessonType && Objects.equals(date, mark.date);
	}
    /**
     * Hash code for this mark based on value, lesson type, and date.
     * @return hash code for this mark.
     */
	@Override
	public int hashCode() {
		return Objects.hash(value, lessonType, date);
	}
    /**
     * Converts a numeric grade to the corresponding GPA.
     * @param grade - numeric grade to be converted.
     * @return PA equivalent of the provided grade.
     */
	public static double gradeToGpa(double grade) {
		if(grade >= 97) {
			return 4.0;
		} else if (grade >= 93) {
			return 4.0;
		} else if (grade >= 90) {
			return 3.7;
		} else if (grade >= 87) {
			return 3.3;
		} else if (grade >= 83) {
			return 3.0;
		} else if (grade >= 80) {
			return 2.7;
		} else if (grade >= 77) {
			return 2.3;
		} else if (grade >= 73) {
			return 2.0;
		} else if (grade >= 70) {
			return 1.7;
		} else if (grade >= 67) {
			return 1.3;
		} else if (grade >= 65) {
			return 1.0;
		} else {
			return 0;
		}
	}
	
}


