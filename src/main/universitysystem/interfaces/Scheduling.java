package universitysystem.interfaces;

import universitysystem.courses.Course;
import universitysystem.courses.Lesson;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

public interface Scheduling {
    public boolean isScheduleComplete();
    public boolean isCourseScheduleComplete(Course c);
    public boolean addLessonToSchedule(Course c, Lesson l);
    public boolean removeLessonFromSchedule(Lesson l);
    public HashMap<Course, TreeSet<Lesson>> viewSchedule();
    public Vector<Lesson> viewLessons();
}
