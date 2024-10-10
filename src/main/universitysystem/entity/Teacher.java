package universitysystem.entity;

import universitysystem.courses.*;
import universitysystem.data.Data;
import universitysystem.interfaces.Scheduling;
import universitysystem.interfaces.StudentsInfo;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Class Teacher in the university system. 
 * A teacher is a faculty member invited by the university to conduct courses,
 * evaluate students and monitor their academic performance.
 * @author 
 * @version 1.0
 */
public class Teacher extends Employee implements StudentsInfo, Scheduling, Serializable
{
	private HashSet<Course> courses;
	
	private TeacherType type;
	
	private HashMap<Student, Integer> rates;
	private Faculty faculty;

	{
		type = null;
		faculty = null;
		courses = new HashSet<>();
		rates = new HashMap<>();
	}
    /**
     * Default constructor.
     */
	public Teacher(){
		super();
	}
    /**
     * Constructor for Teacher with login and password.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     */
	public Teacher(String login, String password) {
		super(login, password);
	}
    /**
     * Constructor for Teacher with login, password, salary, and type.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     * @param salary - salary of the teacher.
     * @param type - type of the teacher.
     */
	public Teacher(String login, String password, double salary, TeacherType type) {
		super(login, password, salary);
		this.type = type;
	}
    /**
     * Constructor for Teacher with login, password, and email.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     * @param email - email of the teacher.
     */
	public Teacher(String login, String password, String email) {
		super(login, password, email);
	}
    /**
     * Constructor for Teacher  with login, password, email, salary, type, and faculty.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     * @param email - email of the teacher.
     * @param salary - salary of the teacher.
     * @param type - type of the teacher.
     * @param faculty - faculty of the teacher.
     */
	public Teacher(String login, String password, String email, double salary, TeacherType type, Faculty faculty) {
		super(login, password, email, salary);
		this.type = type;
		this.faculty = faculty;
	}
    /**
     * Constructor for Teacher with login, password, email, firstname, faculty, lastname, middlename, and birthDate.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     * @param email - email of the teacher.
     * @param firstname - first name of the teacher.
     * @param faculty - faculty of the teacher.
     * @param lastname - last name of the teacher.
     * @param middlename - middle name of the teacher.
     * @param birthDate - birth date of the teacher.
     */
	public Teacher(String login, String password, String email, String firstname, Faculty faculty,
				   String lastname, String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
		this.faculty = faculty;
	}
    /**
     * Constructor for the Teacher class with login, password, email, firstname, faculty, lastname, middlename, birthDate, salary, and type.
     * @param login - login of the teacher.
     * @param password - password of the teacher.
     * @param email - email of the teacher.
     * @param firstname - first name of the teacher.
     * @param faculty - faculty of the teacher.
     * @param lastname - last name of the teacher.
     * @param middlename - middle name of the teacher.
     * @param birthDate - birth date of the teacher.
     * @param salary - salary of the teacher.
     * @param type - type of the teacher.
     */
	public Teacher(String login, String password, String email, String firstname, Faculty faculty,
				   String lastname, String middlename, Date birthDate, double salary, TeacherType type) {
		super(login, password, email, firstname, lastname, middlename, birthDate, salary);
		this.faculty = faculty;
		this.type = type;
	}
    /**
     * Generates a unique identifier for the specialist using the current year and the system identifier counter.
     * @return generated ID for the teacher.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "T" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Adds course to the set taught by the teacher.
     * @param c - course to be added.
     * @return {@code true} if the course is added successfully, {@code false} otherwise.
     */
	public boolean addCourse(Course c) {
		return courses.add(c);
	}
    /**
     * Removes course from the set taught by the teacher.
     * @param c - course to be removed.
     * @return {@code true} if the course is removed successfully, {@code false} otherwise.
     */
	public boolean removeCourse(Course c) {
		return courses.remove(c);
	}
    /**
     * Gets the set taught by the teacher.
     * @return Set of courses.
     */
	public HashSet<Course> getCourses() {
		return courses;
	}
    /**
     * Puts attestation for a student in a specific course.
     * @param s - student for whom attestation is put.
     * @param c - course for which attestation is put.
     * @return {@code true} if attestation is put successfully, {@code false} otherwise.
     */
	public boolean putAttestation(Student s, Course c) {
		return s.getCurrentCourses().stream()
				.filter(r -> r.getCourse().equals(c))
				.findFirst()
				.map(RegisteredCourse::putAttestation)
				.orElse(false);
	}
    /**
     * Puts a final exam score for a student in a specific course.
     * @param s - student for whom the final exam score is put.
     * @param value - value of the final exam score.
     * @param c - course for which the final exam score is put.
     * @return {@code true} if the final exam score is put successfully, {@code false} otherwise.
     */
	public boolean putFinalExam(Student s, Double value, Course c){
		return s.getCurrentCourses().stream()
				.filter(r -> r.getCourse().equals(c))
				.findFirst()
				.map(r -> r.putFinalExam(value))
				.orElse(false);
	}
    /**
     * Puts a mark for a student in a specific course.
     * @param s - student for whom the mark is put.
     * @param m - mark to be put.
     * @param c - course for which the mark is put.
     * @return {@code true} if the mark is put successfully, {@code false} otherwise.
     */
	public boolean putMark(Student s, Mark m, Course c) {
		return s.getCurrentCourses().stream()
				.filter(r -> r.getCourse().equals(c))
				.findFirst()
				.map(r -> r.addMark(m))
				.orElse(false);
	}
	/**
	* Sent complaints to the dean's.
	* @parameter c - sending a complaint.
	*/
	public void sendComplaint(Complaint c) {
		Dean.addComplaint(c);
	}
    /**
     * Gets the type of the teacher.
     * @return Type of the teacher.
     */
	public TeacherType getType() {
		return type;
	}
    /**
     * Sets the type of the teacher.
     * @param type - new type of the teacher.
     */
	public void setType(TeacherType type) {
		this.type = type;
	}
    /**
     * Gets the faculty of the teacher.
     * @return Faculty of the teacher.
     */
	public Faculty getFaculty() {
		return faculty;
	}
    /**
     * Sets the faculty of the teacher.
     * @param faculty - new faculty of the teacher.
     */
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
    /**
     * Gets the average rate of the teacher.
     * @return Average rate of the teacher.
     */
	public double getRate() {
		if(rates.isEmpty()) return 0;
		return (double) rates.values().stream().mapToInt(Integer::intValue).sum() / rates.size();
	}
    /**
     * Gets the rates given by students to the teacher.
     * @return Map of rates given by students to the teacher.
     */
	public HashMap<Student, Integer> getRates(){
		return rates;
	}
    /**
     * Adds a rate given by a student to the teacher.
     * @param s - student who gave the rate.
     * @param rate - rate given by the student.
     * @return {@code true} if the rate is added successfully, {@code false} otherwise.
     */
	public boolean addRate(Student s, int rate){
		rates.put(s,rate);
		return true;
	}
    /**
     * Removes the rate given by a student to the teacher.
     * @param s - student whose rate is to be removed.
     * @return {@code true} if the rate is removed successfully, {@code false} otherwise.
     */
	public boolean removeRate(Student s){
		rates.remove(s);
		return true;
	}
    /**
     * Ends the semester for teacher, clearing the set.
     * @return {@code true} if the semester is ended successfully, {@code false} otherwise.
     */
	public boolean endSemester() {
		courses.clear();
		return true;
	}
	/**
	 * Sends an employee request to the Dean for approval.
	 * @param e - {@code EmployeeRequest} to be sent for approval.
	 * @return {@code true} if the request was successfully added to the Dean's pending requests,
	 * {@code false} otherwise.
	 */
	public boolean sendRequest(EmployeeRequest e){
		Dean.addEmployeeRequest(e);
		return true;
	}
	/**
     * Verifies if the schedule of the teacher is complete for all courses.
     * @return {@code true} if the schedule is complete for all courses, {@code false} otherwise.
     */
	public boolean isScheduleComplete(){
		return courses.stream().allMatch(this::isCourseScheduleComplete);
	};
    /**
     * Verifies if the schedule for a specific course is complete.
     * @param c - course for which to check the schedule.
     * @return {@code true} if the schedule is complete for the course, {@code false} otherwise.
     */
	public boolean isCourseScheduleComplete(Course c){
		return c.getLessons().stream().anyMatch(n -> n.getTeacher() == this);
	}
	/**
	 * Prints the schedule of lessons for the teacher.
	 */
	public void printSchedule(){
		for (Course c: courses){
			Vector<Lesson> actualLessons = c.getLessons().stream().filter(n -> n.getTeacher().equals(this)).collect(Collectors.toCollection(Vector::new));
			System.out.println("\nCourse: " + c.getCode() + " " + c.getTitle());
			for (Lesson l: actualLessons){
				System.out.println("\n" + l);
			}
		}
	}
    /**
     * Views the schedule of the teacher for all courses.
     * @return Map representing the schedule for each course, where the key is the course and the value is a sorted set of lessons.
     */
	public HashMap<Course, TreeSet<Lesson>> viewSchedule(){
		HashMap<Course, TreeSet<Lesson>> schedule = new HashMap<>();
		for (Course c: courses){
			TreeSet<Lesson> lessons = c.getLessons().stream()
					.filter(n -> n.getTeacher() == this).collect(Collectors.toCollection(TreeSet::new));
			schedule.put(c, lessons);
		}
		return schedule;
	}
    /**
     * Views all lessons taught by the teacher.
     * @return Vector containing all lessons taught by the teacher.
     */
	public Vector<Lesson> viewLessons(){
		return courses.stream()
				.flatMap(n -> n.getLessons().stream())
				.filter(n -> n.getTeacher() == this)
				.collect(Collectors.toCollection(Vector::new));
	}
    /**
     * Adds lesson to the schedule for a course.
     * @param c - course for which to add the lesson.
     * @param l - lesson to be added.
     * @return {@code true} if the lesson is added successfully, {@code false} otherwise.
     */
	public boolean addLessonToSchedule(Course c, Lesson l){
		if(!Lesson.getSchedulingStatus()) return false;
		boolean result = false;
		switch (l.getType()){
			case LECTURE -> result = (l.getEndTime() - l.getStartTime()) == c.getLectures();
			case PRACTICE -> result = (l.getEndTime() - l.getStartTime()) == c.getPractices();
			case LAB -> result = (l.getEndTime() - l.getStartTime()) == c.getLabs();
		}
		if(!result) return false;
		for (Lesson lesson: viewLessons()){
			if((l.getStartTime() == lesson.getStartTime()) || (l.getEndTime() == lesson.getEndTime())
					 || ((l.getStartTime() >= lesson.getStartTime() && l.getEndTime() <= lesson.getEndTime()))) return false;
		}
		return c.addLesson(l);
	}
    /**
     * Schedule of the teacher removes.
     * @param l - lesson to be removed.
     * @return {@code true} if the lesson is removed successfully, {@code false} otherwise.
     */
	public boolean removeLessonFromSchedule(Lesson l){
		if(!Lesson.getSchedulingStatus()) return false;
		if(l.getCurrentStudents() > 0) return false;
		Course c = courses.stream().filter(n -> n.getLessons().stream()
				.anyMatch(lesson -> lesson.equals(l))).findFirst().orElse(null);
		if(c == null) return false;
		return c.removeLesson(l);
	}
    /**
     * Verifies if this teacher is equal to another object.
     * @param o The object to compare.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Teacher teacher = (Teacher) o;
		return Objects.equals(courses, teacher.courses) && type == teacher.type && Objects.equals(rates, teacher.rates);
	}
    /**
     * Hash code for teacher.
     * @return hash code for teacher.
     */
	@Override
	public int hashCode() {
		return Objects.hash(courses, type, rates);
	}
    /**
     * Converts this teacher to a string representation.
     * @return Representation of this teacher.
     */
	@Override
	public String toString() {
		return "ID: " + getId() + ", Teacher type: " + type + ", email: " + getEmail()
				+ "\nFull name: " + getLastname() + " " + getFirstname() + " salary: " + getSalary()
				+ (getMiddlename() != null ? " " + getMiddlename() : "") + "\nRating: " + getRate();
	}
    /**
     * Views information about a student.
     * @param s - student for which to information view.
     * @return Student's information representation.
     */
	@Override
	public String viewInfoAboutStudent(Student s) {
		return s.toString();
	}
    /**
     * Run method encapsulates the primary operational loop for a teacher.
     * It offers a menu-driven interface for the teacher to execute various tasks,
     * such as managing courses, grading students, and more.
     */
	public void run(){
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Сейчас вы в личном кабинете УЧИТЕЛЯ");
			if(!isScheduleComplete()){
				System.out.println("У вас есть курсы без занятий. Пожалуйста, добавьте занятия!");
			}
			menu: while (true){
				System.out.println("Что вы хотите сделать? \n1) Личные данные \n2) Новости \n3) Расписание \n4) Курсы" +
						"\n5) Оценки \n6) Журнал \n7) Отправить запрос менеджеру \n8) Отправить tech report \n9) Exit ");
				if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					System.out.println("10) Перейти в RESEARCHER");
				}
				int choice = in.nextInt();
				if (choice == 1) {
					viewPersonalDataRun();
				} else if (choice == 2) {
					viewNewsRun();
			} else if(choice == 3){
					teacherScheduleRun();
				} else if(choice == 4){
					manageTeacherCoursesRun();
				} else if(choice == 5){
					teacherGradeRun();
				} else if(choice == 6){
					viewJournalRun();
				} else if(choice == 7){
					sendEmployeeRequestRun();
				} else if(choice == 9){
					this.logout();
					break menu;
				} else if(choice == 10 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					goToResearcher();
				} else if(choice == 8){
					sendTechReportRun();
				}
			}
		} catch (Exception e){
			System.out.println("Error");
		}
	}
    /**
     * 
     * TeacherScheduleRun method enables a teacher to interact with and manage their schedule.
     * Method offers options to view, add, and remove lessons from the schedule.
     */
	protected void teacherScheduleRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		schedule:
		while (true) {
			System.out.println("1) Посмотреть расписание \n2) Узнать статус своего расписания" +
					"\n3) Добавить урок в расписание \n4) Удалить урок из расписания \n5) Вернуться назад");
			choice = in.nextInt();
			if (choice == 1) {
				System.out.println("\nРасписание " + Data.getInstance().getYear() + " " + Data.getInstance().getSemester());
				printSchedule();
				System.out.println("\n1) Вернуться назад");
				choice = in.nextInt();
				if (choice == 1) continue schedule;
				if (choice == 2) {
					break;
				}
			} else if (choice == 2) {
				if (isScheduleComplete()) {
					System.out.println("\nВаше расписание полностью сформировано!");
				} else {
					System.out.println("\nВаше расписание сформировано не полностью!");
					for (Course c : courses) {
						System.out.println("\nКод курса: " + c.getCode() + ". Статус: " + ((isCourseScheduleComplete(c)) ? "ГОТОВО" : "НЕ ГОТОВО"));
					}
					System.out.println("\n1) Вернуться назад \n2) Exit");
					choice = in.nextInt();
					if (choice == 1) continue schedule;
					if (choice == 2) {
						break;
					}
				}
			} else if (choice == 3) {
				addingLessons:
				while (true) {
					System.out.println("\nВыберите курс: ");
					Vector<Course> coursesForScheduling = new Vector<>(courses);
					int cnt = 0;
					for (Course c : coursesForScheduling) {
						System.out.println(++cnt + ") Статус: " + (isCourseScheduleComplete(c) ? "ГОТОВО" : "НЕ ГОТОВО") + ". Курс: " + c.getTitle());
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if (choice >= cnt - 1 || choice < 1) continue schedule;
					Course currentCourse = coursesForScheduling.get(choice - 1);
					System.out.println("Выберите тип урока: \n1) LECTURE \n2)  PRACTICE \n3) LAB \n4) Вернуться назад");
					choice = in.nextInt();
					if (choice < 1 || choice > 3) continue schedule;
					LessonType lesType = null;
					if(choice == 1) lesType = LessonType.LECTURE;
					if(choice == 2) lesType = LessonType.PRACTICE;
					if(choice == 3) lesType = LessonType.LAB;
					System.out.println("Выберите день: \n1) MON \n2) TUE \n3) WED \n4) THU \n5) FRI \n6) SAT \n7) SUN \n8) Вернуться назад");
					choice = in.nextInt();
					if (choice < 1 || choice > 7) continue schedule;
					DaysWeek day = null;
					if(choice == 1) day = DaysWeek.MONDAY;
					if(choice == 2) day = DaysWeek.TUESDAY;
					if(choice == 3) day = DaysWeek.WEDNESDAY;
					if(choice == 4) day = DaysWeek.THURSDAY;
					if(choice == 5) day = DaysWeek.FRIDAY;
					if(choice == 6) day = DaysWeek.SATURDAY;
					if(choice == 7) day = DaysWeek.SUNDAY;
					System.out.println("Введите начало урока от 8 часов до 21: ");
					choice = in.nextInt();
					if(choice < 8 || choice > 21) continue schedule;
					int startTime = choice;
					System.out.println("Введите конец урока от 9 часов до 23: ");
					choice = in.nextInt();
					if(choice < 9 || choice > 23 || choice < startTime) continue schedule;
					int endTime = choice;
					System.out.println("Укажите максимальное количество студентов (мин 10, макс 150): ");
					choice = in.nextInt();
					if(choice < 10 || choice > 150) continue schedule;
					int maxStudents = choice;
					boolean addLesson = currentCourse.addLesson(new Lesson(this, lesType, day, startTime, endTime, maxStudents));
					System.out.println((addLesson ? "Успешно добавлено!" : "Ошибка при добавлении.") + "\n1) Добавить еще один урок \n2) Вернуться назад \n3) Exit");
					choice = in.nextInt();
					if (choice == 1) continue addingLessons;
					if (choice == 2) continue schedule;
					if (choice == 3) {
						break;
					}
				}
			} else if (choice == 4) {
				deletingLessons:
				while (true) {
					System.out.println("Выберите урок, который хотите удалить: ");
					int cnt = 0;
					for (Lesson l : viewLessons()) {
						System.out.println(++cnt + ") " + l);
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if (choice >= cnt || choice < 1) continue schedule;
					Lesson lessonForDeleting = viewLessons().get(choice - 1);
					boolean delLesson = removeLessonFromSchedule(lessonForDeleting);
					System.out.println((delLesson ? "Успешно удалено!" : "Ошибка при удалении.") + "\n1) Удалить еще один урок \n2) Вернуться назад \n3) Exit");
					choice = in.nextInt();
					if (choice == 1) continue deletingLessons;
					if (choice == 2) continue schedule;
					if (choice == 3) {
						break;
					}
				}
			} else if (choice == 5) break;
		}
	}
    /**
     * manageTeacherCoursesRun method allows a teacher to manage the courses they are responsible for.
     * It provides options to add or remove courses from the teacher's list.
     */
	protected void manageTeacherCoursesRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		manageCourses: while(true){
			System.out.println("1) Добавить курс \n2) Удалить курс \n3) Вернуться назад");
			choice = in.nextInt();
			if(choice == 1){
				System.out.println("Выберите курс: ");
				Vector<Course> actualCourses = Data.getInstance().getCourses().stream().filter(n -> n.getYear() == Data.getInstance().getYear())
						.filter(n -> n.getSemester() == Data.getInstance().getSemester()).collect(Collectors.toCollection(Vector::new));
				int cnt = 0;
				for (Course c : actualCourses) {
					System.out.println(++cnt + ") " + c);
				}
				System.out.println(++cnt + ") Вернуться назад");
				choice = in.nextInt();
				if (choice >= cnt || choice < 1) continue manageCourses;
				Course choicedCourse = actualCourses.get(choice - 1);
				if(courses.stream().noneMatch(n-> n.equals(choicedCourse))){
					addCourse(choicedCourse);
					System.out.println("Успешно добавлено! \n1)Добавить еще один курс \2)Вернуться назад \n3)Выход");
				} else {
					System.out.println("Не удалось добавить курс. \n1)Попробовать другой курс \2)Вернуться назад");
				}
				choice = in.nextInt();
				if(choice == 1) continue manageCourses;
				if(choice == 2) break;
			} else if(choice == 2){
				System.out.println("Выберите курс: ");
				Vector<Course> actualCourses = new Vector<>(courses);
				int cnt = 0;
				for (Course c : actualCourses) {
					System.out.println(++cnt + ") " + c);
				}
				System.out.println(++cnt + ") Вернуться назад");
				choice = in.nextInt();
				if (choice >= cnt || choice < 1) continue manageCourses;
				Course courseForDeleting = actualCourses.get(choice - 1);
				if(!isCourseScheduleComplete(courseForDeleting)){
					removeCourse(courseForDeleting);
					System.out.println("Успешно удалено! \n1)Добавить еще один курс \2)Вернуться назад \n3)Выход");
				} else{
					System.out.println("Не удалось удалить курс. \n1)Вернуться к курсам \2)Вернуться назад \n3)Выход");
				}
				choice = in.nextInt();
				if(choice == 1) continue manageCourses;
				if(choice == 2) break ;
			} else if(choice == 3){
				break;
			}
		}
	}
	/**
     * The teacherGradeRun method enables a teacher to manage and assign grades to students for a specific course. 
     * It features a menu-driven interface for the teacher to execute various grading tasks,
     * such as entering regular marks, assigning final exam grades, viewing student information, and addressing complaints.
	 * @throws Exception 
	 */
	protected void teacherGradeRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		grades: while(true) {
			System.out.println("Выберите курс: ");
			Vector<Course> actualCourses = new Vector<>(courses);
			int cnt = 0;
			for (Course c : actualCourses) {
				System.out.println(++cnt + ") " + c);
			}
			System.out.println(++cnt + ") Вернуться назад");
			choice = in.nextInt();
			if (choice >= cnt || choice < 1) break ;
			Course choicedCourse = actualCourses.get(choice - 1);
			System.out.println("Курс выбран. \n1) Поставить оценку студенту \n2) Выставить аттестацию \n3) Поставить final exam студенту \n4) Посмотреть информацию о студентах" +
					"\n5) Вернуться назад");
			choice = in.nextInt();
			Vector<Student> students = Data.getInstance().getStudents().stream()
					.filter(n -> n.getCurrentCourses().stream()
							.anyMatch(c -> c.getCourse().equals(choicedCourse))).collect(Collectors.toCollection(Vector::new));
			students = students.stream().filter(n ->n.getCurrentCourses().stream()
					.anyMatch(c -> c.getLessons().stream().anyMatch(l -> l.getTeacher().equals(this)))).collect(Collectors.toCollection(Vector::new));
			if(choice == 1 || choice == 3 || choice == 4){
				System.out.println("Выберите студента: ");
				cnt = 0;
				for (Student s: students){
					System.out.println(++cnt + ") " + s);
				}
				System.out.println(++cnt + ") Вернуться назад");
				choice = in.nextInt();
				if (choice >= cnt || choice < 1) continue grades;
				Student currentStudent = students.get(choice - 1);
				if(choice == 1){
					System.out.println("Студент выбран. Введите оценку от 0 до 100");
					double grade = in.nextDouble();
					if(grade < 0 || grade > 100) continue grades;
					System.out.println("Выберите тип занятия: \n1) LECTURE \n2) PRACTICE \n3) LAB");
					choice = in.nextInt();
					if(choice < 1 || choice > 3) continue grades;
					LessonType lesType = null;
					if(choice == 1) lesType = LessonType.LECTURE;
					if(choice == 2) lesType = LessonType.PRACTICE;
					if(choice == 3) lesType = LessonType.LAB;
					boolean graded = putMark(currentStudent, new Mark(grade, lesType), choicedCourse);
					System.out.println((graded ? "Оценка поставлена!" : "Не удалось выставить оценку")
							+ "\n1) Добавить еще одну оценку \n2) Вернуться назад \n3) Выход");
					choice = in.nextInt();
					if(choice == 1) continue grades;
					if(choice == 2) break;
				} else if(choice == 3){
					System.out.println("Введите оценку от 0 до 40");
					double grade = in.nextDouble();
					if(grade < 0 || grade > 40) continue grades;
					boolean finalGraded = putFinalExam(currentStudent, grade, choicedCourse);
					System.out.println((finalGraded ? "Final exam grade поставлен!" : "Не удалось выставить final exam")
							+ "\n1) Добавить еще одну оценку \n2) Вернуться назад ");
					choice = in.nextInt();
					if(choice == 1) continue grades;
					if(choice == 2) break ;
				} else if(choice == 4){
					System.out.println("Информация о студенте " + currentStudent.getId() + "\n" + viewInfoAboutStudent(currentStudent));
					System.out.println("1) Отправить жалобу \n2) Вернуться назад ");
					choice = in.nextInt();
					if(choice == 1){
						System.out.println("Введите текст жалобы: ");
						in.nextLine();
						String text = in.nextLine();
						sendComplaint(new Complaint(this, currentStudent, text));
						System.out.println("Жалоба отправлена в деканат! "
								+ "\n1)Вернуться к оценкам \n2) Вернуться назад \n3) Выход");
						choice = in.nextInt();
						if(choice == 1) continue grades;
						if(choice == 2) break;
					} else if(choice == 2){
						continue grades;
					} else if(choice == 3){
						break;
					}
				}
			} else if(choice == 2){
				System.out.println("Производим выставление аттестации..");
				for (Student s: students){
					putAttestation(s, choicedCourse);
				}
				System.out.println("Аттестация выставлена. "
						+ "\n1)Вернуться к оценкам \n2) Вернуться назад");
				choice = in.nextInt();
				if(choice == 1) continue grades;
				if(choice == 2) break;
			} else if(choice == 5){
				break ;
			}
		}
	}
}


