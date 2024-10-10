package universitysystem.entity;


import universitysystem.courses.*;
import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.interfaces.Scheduling;
import universitysystem.interfaces.TeachersInfo;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Class {@code Student} is a student within the university system,extending the capabilities of the User class.
 * Students can manage their courses, rate teachers,view transcripts and participate in organizational activities.
 * @author 
 * @version 1.0
 */

public class Student extends User implements TeachersInfo, Scheduling, Serializable
{
	private static int maxCredits = 30;
	private HashSet<RegisteredCourse> currentCourses;
	private HashSet<RegisteredCourse> completedCourses;
	private HashSet<RegisteredCourse> notCompletedCourses;
	private Faculty faculty;
	private int yearOfStudy;
	private Organization organization;
	private int warning = 0;

	{
		this.currentCourses = new HashSet<>();
		this.completedCourses = new HashSet<>();
		this.notCompletedCourses = new HashSet<>();
		this.faculty = null;
		this.yearOfStudy = 1;
		this.organization = null;
	}
    /**
     * Default constructor.
     */
	public Student(){
		super();
	}

	//public Student(User user){}
    /**
     * Constructs a Student object with specified login and password.
     * @param login The login for the student.
     * @param password The password for the student.
     */
	public Student(String login, String password) {
		super(login, password);;
	}
    /**
     * Constructs a Student object with specified login, password, faculty, and year of study.
     * @param login The login for the student.
     * @param password The password for the student.
     * @param faculty The faculty to which the student belongs.
     * @param yearOfStudy The year of study for the student.
     */
	public Student(String login, String password, Faculty faculty, int yearOfStudy) {
		this(login, password);
		this.faculty = faculty;
		this.yearOfStudy = yearOfStudy;
	}
    /**
     * Constructs a Student with the specified login, password, and email address.
     * @param login The student's login.
     * @param password The student's password.
     * @param email The student's email address.
     */
	public Student(String login, String password, String email) {
		super(login, password, email);
	}
    /**
     * Constructs a Student with the specified login, password, email address,
     * faculty, and year of study.
     * @param login The student's login.
     * @param password The student's password.
     * @param email The student's email address.
     * @param faculty The faculty in which the student is enrolled.
     * @param yearOfStudy The year of study for the student.
     */
	public Student(String login, String password, String email, Faculty faculty, int yearOfStudy) {
		this(login, password, email);
		this.faculty = faculty;
		this.yearOfStudy = yearOfStudy;
	}
    /**
     * Constructs a Student with the specified login, password, email address,
     * first name, last name, middle name, and date of birth.
     * @param login The student's login.
     * @param password The student's password.
     * @param email The student's email address.
     * @param firstname The student's first name.
     * @param lastname The student's last name.
     * @param middlename The student's middle name.
     * @param birthDate The student's date of birth.
     */
	public Student(String login, String password, String email, String firstname,
				   String lastname, String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}
    /**
     * Constructs a Student with the specified login, password, email address,
     * first name, last name, middle name, date of birth, faculty, and year of study.
     * @param login The student's login.
     * @param password The student's password.
     * @param email The student's email address.
     * @param firstname The student's first name.
     * @param lastname The student's last name.
     * @param middlename The student's middle name.
     * @param birthDate The student's date of birth.
     * @param faculty The faculty in which the student is enrolled.
     * @param yearOfStudy The year of study for the student.
     */
	public Student(String login, String password, String email, String firstname, String lastname,
				   String middlename, Date birthDate, Faculty faculty, int yearOfStudy) {
		this(login, password, email, firstname, lastname, middlename, birthDate);
		this.faculty = faculty;
		this.yearOfStudy = yearOfStudy;
	}
    /**
     * Generates a unique identifier based on the current year and the system's ID counter
     * @return The uniquely generated identifier for the student.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "S" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Adds a current course to the student's collections.
     * @param c The current course to add.
     * @return {@code true} if the addition is successful, {@code false} otherwise.
     */
	public boolean addCurrentCourse(RegisteredCourse c) {
		return this.currentCourses.add(c);
	}
    /**
     * Removes a current course from the student's collections.
     * @param c The current course to remove.
     * @return {@code true} if the removal is successful, {@code false} otherwise.
     */
	public boolean removeCurrentCourse(RegisteredCourse c) {
		if (this.currentCourses != null) {
			return this.currentCourses.remove(c);
		}
		return false;
	}
    /**
     * Gets the list of current courses.
     * @return The list of current courses.
     */
	public HashSet<RegisteredCourse> getCurrentCourses() {
		return currentCourses;
	}
    /**
     * Registers the student to a course with the specified registration type.
     * @param c The course to register for.
     * @param type The registration type.It is ADD or DROP.
     * @return The status of the registration process.
     * @throws CourseRegisteredException  If the course is already registered.
     * @throws MaxCreditsException  If the maximum credit limit is exceeded.
     * @throws DuplicateRegistrationException  If a duplicate registration request is found.
     */
	public boolean registerToCourse(Course c, RegistrationType type) throws CourseRegisteredException, MaxCreditsException, DuplicateRegistrationException {
		if(Data.getInstance().getRegistrationRequests().stream().anyMatch(n-> n.getSender() == this && n.getCourse() == c)){
			throw new DuplicateRegistrationException("The request has already been sent");
        } else{
			boolean courseInProgress = currentCourses.stream().anyMatch(n -> n.getCourse() == c);
			if (type == RegistrationType.ADD){
				boolean courseCompleted = completedCourses.stream().anyMatch(n -> n.getCourse() == c);
				if (courseInProgress || courseCompleted){
					throw new CourseRegisteredException("The course has already been registered");
                } else if (this.getCredits(currentCourses) + c.getCredits() > maxCredits){
					throw new MaxCreditsException("Exceeding credits");
                } else return Data.getInstance().getRegistrationRequests().add(new RegistrationRequest(this, c, type));
			} else{
				if(!courseInProgress){
					throw new CourseRegisteredException("The course was not registered");
                } else return Data.getInstance().getRegistrationRequests().add(new RegistrationRequest(this, c, type));
			}
		}
    }
    /**
     * Views the academic transcript of the student, including completed and not completed courses.
     * @return List of strings representing the student's transcript.
     */
	public List<String> viewTranscript() {
		List<String> transcript = completedCourses.stream().map(RegisteredCourse::toString).collect(Collectors.toList());
		transcript.addAll(notCompletedCourses.stream().map(RegisteredCourse::toString).toList());
		transcript.add("Total credits: " + getCredits(completedCourses) + getCredits(notCompletedCourses)
				+ ". Total GPA: " + getGpa());
		return transcript;
	}
    /**
     * Views the attestation of the student, including current courses.
     * @return The list of strings representing the student's attestation.
     */
	public List<String> viewAttestation(){
		return currentCourses.stream().map(RegisteredCourse::toString).collect(Collectors.toList());
	}
    /**
     * Rates a teacher for a specific course.
     * @param t The teacher to rate.
     * @param rate The rating value.
     * @return {@code true} if the rating is successfully added, {@code false} otherwise.
     */
	public boolean rateTeacher(Teacher t, int rate) {
		if(currentCourses.stream().anyMatch(n -> n.getLessons().stream().anyMatch(l -> l.getTeacher() == t))
		&& rate >= 0 && rate <= 10){
			return t.addRate(this, rate);
		} return false;
	}
    /**
     * Retrieve the student's GPA.
     * @return student GPA.
     */
	public double getGpa() {
		double sumGpa = completedCourses.stream().mapToDouble(n -> n.getGpa() * n.getCourse().getCredits()).sum();
		int credits = getCredits(completedCourses) + getCredits(notCompletedCourses);
		if(credits > 0) return sumGpa / credits;
		else return 0;
	}
    /**
     * Obtain the academic department to which the student is affiliated.
     * @return Faculty to which the student belongs.
     */
	public Faculty getFaculty() {
		return this.faculty;
	}
    /**
     * Sets the faculty for the student.
     * @param faculty The faculty to set.
     */
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
    /**
     * Gets the year of study for the student.
     * @return The year of study.
     */
	public int getYearOfStudy() {
		return this.yearOfStudy;
	}
    /**
     * Sets the year of study for the student.
     * @param yearOfStudy The new year of study.
     */
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
    /**
     * Gets the organization to which the student belongs.
     * @return The organization.
     */
	public Organization getOrganization() {
		return organization;
	}
    /**
	 * Creates a new organization with the specified name and adds the student to it.
	 *
	 * @param name - name of the organization.
	 */
	public void createOrganization(String name) {
		if(Data.getInstance().getOrganizations().stream().anyMatch(n -> n.getName().equals(name))){
			return;
		}
		Organization o = new Organization(name, this);
		Data.getInstance().getOrganizations().add(o);
		joinToOrganization(o);
	}
	//можно уведомлять, что лидер ушел и будут проведены выборы
    /**
	 * student can join to an organization.
	 *
	 * @param o - organization to join.
	 */
	public void joinToOrganization(Organization o) {
		leaveFromOrganization();
		this.organization = o;
	}
    /**
     * Leaves the current organization.
     */
	public void leaveFromOrganization(){
		if(organization != null){
			if(organization.getHead() == this) organization.setHead(null);
		}
	}
	/**
	 * Prints the schedule for the current courses including course code, title, and associated lessons.
	 * For each registered course, it displays the course code, title, and details of each lesson.
	 * The information is printed to the console in a formatted manner.
	 */
	public void printSchedule(){
		for (RegisteredCourse r: currentCourses){
			System.out.println("\nCourse: " + r.getCourse().getCode() + " " + r.getCourse().getTitle());
			for (Lesson l: r.getLessons()){
				System.out.println("\n" + l);
			}
		}
	}
    /**
     * Views the student's schedule, including courses and associated lessons.
     * @return The schedule of the student.
     */
	public HashMap<Course, TreeSet<Lesson>> viewSchedule() {
		HashMap<Course, TreeSet<Lesson>> schedule = new HashMap<>();
		for (RegisteredCourse r: currentCourses){
			schedule.put(r.getCourse(), r.getLessons());
		}
		return schedule;
	}
    /**
     * Views the lessons associated with the student's current courses.
     * @return The collections of lessons.
     */
	public Vector<Lesson> viewLessons(){
        return currentCourses.stream()
				.flatMap(n -> n.getLessons().stream())
				.collect(Collectors.toCollection(Vector::new));
	}
    /**
     * Gets the warning count for the student.
     * @return Warning count.
     */
	public int getWarning() {
		return this.warning;
	}
    /**
     * Adds a warning to the student.
     */
	public void addWarning() {
		this.warning++;
	}
    /**
     * Views the marks associated with a registered course.
     * @param r - registered course to view marks for.
     * @return Vector of marks.
     */
	public Vector<Mark> viewMarks(RegisteredCourse r) {
		return r.getMarks();
	}
    /**
     * Completes the semester for the student, categorizing courses as either completed or not completed depending on the retake status.
     * @return {@code true} if the semester end is successful, {@code false} otherwise.
     */
	public boolean endSemester() {
		for (RegisteredCourse r: currentCourses){
			if(r.isRetake()) notCompletedCourses.add(r);
			else completedCourses.add(r);
		}
		currentCourses.clear();
		return true;
	}
    /**
     * Gets completed courses in set.
     * @return Set of completed courses.
     */
	public HashSet<RegisteredCourse> getCompletedCourses() {
		return this.completedCourses;
	}
    /**
     * Removes a completed course from the set of completed courses.
     * @param c - completed course to remove.
     * @return {@code true} if the removal is successful, {@code false} otherwise.
     */
	public boolean removeCompletedCourse(RegisteredCourse c) {
		return completedCourses.remove(c);
	}
    /**
     * Adds a completed course to the set of completed courses.
     * @param c - completed course to add.
     * @return {@code true} if the addition is successful, {@code false} otherwise.
     */
	public boolean addCompletedCourse(RegisteredCourse c) {
		return completedCourses.add(c);
	}
    /**
     * Adds a not completed course to the set of not completed courses.
     * @param c - not completed course to add.
     * @return {@code true} if the addition is successful, {@code false} otherwise.
     */
	public boolean addNotCompletedCourse(RegisteredCourse c) {
		return notCompletedCourses.add(c);
	}
    /**
     * Removes a not completed course from the set of not completed courses.
     * @param c - not completed course to remove.
     * @return {@code true} if the removal is successful, {@code false} otherwise.
     */
	public boolean removeNotCompletedCourse(RegisteredCourse c) {
		return notCompletedCourses.remove(c);
	}
    /**
     * Gets the set of not completed courses.
     * @return Set of not completed courses.
     */
	public HashSet<RegisteredCourse> getNotCompletedCourses() {
		return notCompletedCourses;
	}
    /**
     * Total number of credits for a set of registered courses.
     * @param hs The set of registered courses.
     * @return The total number of credits.
     */
	public int getCredits(HashSet<RegisteredCourse> hs){
		return hs.stream().mapToInt(n -> n.getCourse().getCredits()).sum();
	}
    /**
     * Gets the maximum allowed credits for a student.
     * @return Maximum allowed credits.
     */
	public static int getMaxCredits(){
		return maxCredits;
	}
    /**
     * Sets the maximum allowed credits for a student.
     * @param credits The maximum allowed credits.
     */
	public static void setMaxCredits(int credits){
		maxCredits = credits;
	}
    /**
     * Gets the current registered course for a specific course.
     * @param c - course to check.
     * @return Current registered course or {@code null} if not found.
     */
	private RegisteredCourse getCurrentRegisteredCourse(Course c){
		return currentCourses.stream().filter(n -> n.getCourse() == c).findFirst().orElse(null);
	}
    /**
     * Checks if the student's schedule is complete for all current courses.
     * @return {@code true} if the schedule is complete, {@code false} otherwise.
     */
	public boolean isScheduleComplete(){
		if(currentCourses.isEmpty()) return false;
		for (RegisteredCourse r: currentCourses){
			if(!isCourseScheduleComplete(r.getCourse())) return false;
		}
		return true;
	}
    /**
     * Checks if the schedule for a specific course is complete.
     * @param c - course to check.
     * @return {@code true} if the schedule is complete, {@code false} otherwise.
     */
	public boolean isCourseScheduleComplete(Course c){
		RegisteredCourse r = getCurrentRegisteredCourse(c);
		if(r == null) return false;
		long lecturesCnt = r.getLessons().stream().filter(n -> n.getType() == LessonType.LECTURE).count();
		long practicesCnt = r.getLessons().stream().filter(n -> n.getType() == LessonType.PRACTICE).count();
		long labsCnt = r.getLessons().stream().filter(n -> n.getType() == LessonType.LAB).count();
        return lecturesCnt == r.getCourse().getLectures() && practicesCnt == r.getCourse().getPractices()
				&& labsCnt == r.getCourse().getLabs();
    };

    /**
     * Adds a lesson to the schedule for a specified course.
     * @param c The course to add the lesson to.
     * @param l The lesson to add to the schedule.
     * @return {@code true} if the addition is successful, {@code false} otherwise.
     */
    
	public boolean addLessonToSchedule(Course c, Lesson l){
		if(!Lesson.getSchedulingStatus()) return false;
		RegisteredCourse r = getCurrentRegisteredCourse(c);
		if(r == null) return false;
		if(r.getLessons().stream().anyMatch(n -> n.equals(l))) return false;
		for(Lesson lesson: viewLessons()){
			if((l.getStartTime() == lesson.getStartTime()) || (l.getEndTime() == lesson.getEndTime())
			|| ((l.getStartTime() >= lesson.getStartTime() && l.getEndTime() <= lesson.getEndTime()))) return false;
		}
		if(!l.isLessonFull()) return false;
		boolean result = false;
		switch (l.getType()){
			case LECTURE -> result = r.getCourse().getLectures() > r.getLessons().stream()
					.filter(n -> n.getType() == LessonType.LECTURE).count();
			case PRACTICE -> result = r.getCourse().getPractices() > r.getLessons().stream()
					.filter(n -> n.getType() == LessonType.PRACTICE).count();
			case LAB -> result = r.getCourse().getLabs() > r.getLessons().stream()
					.filter(n -> n.getType() == LessonType.LAB).count();
		}
		Data.getInstance().getLogs().add(new Log(getId() + " добавил урок в расписание"));
		if(result) return r.addLesson(l);
		return false;
	}
    /**
     * Removes a lesson from the student's schedule.
     * @param l - lesson to be removed.
     * @return {@code true} if the removal is successful, {@code false} otherwise.
     */
	public boolean removeLessonFromSchedule(Lesson l){
		if(Lesson.getSchedulingStatus()) return false;
		RegisteredCourse course = currentCourses.stream().filter(n -> n.getLessons().stream()
				.anyMatch(lesson -> lesson.equals(l))).findFirst().orElse(null);
		if(course == null) return false;
		return course.removeLesson(l);
	}
    /**
     * Converts the student object to a string representation.
     * @return Representation of the student object.
     */
	@Override
	public String toString() {
		return super.toString() + ",  " +
				"year of study: " + getYearOfStudy() + ", faculty: " + getFaculty() + ", GPA: " + getGpa() +
				", warnings: " + getWarning();
	}
    /**
     * Checks if the current student object is equal to another object.
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Student student = (Student) o;
		return yearOfStudy == student.yearOfStudy && warning == student.warning
				&& Objects.equals(currentCourses, student.currentCourses) && faculty == student.faculty
				&& Objects.equals(organization, student.organization);
	}
    /**
     * Hash code for the student object.
     * @return Hash code for the student object.
     */
	@Override
	public int hashCode() {
		return Objects.hash(currentCourses, faculty, yearOfStudy, warning);
	}
    /**
     * Information about a teacher.
     * @param t - teacher to view information about.
     * @return Representation of the teacher's information.
     */
	@Override
	public String viewInfoAboutTeacher(Teacher t) {
		return t.toString();
	}
	/**
	 * Sends a technical report to the tech support specialist for further action.
	 * @param t The technical report to be sent to the tech support specialist.
	 * @return {@code true} if the technical report is sent successfully, {@code false} otherwise.
	 */
	public boolean sendTechReport(TechReport t){
		TechSupportSpecialist.addNewOrder(t);
		return true;
	}
	/**
	 * Initiates the student's user interface and displays relevant information.
	 * Checks and notifies the student about the status of course registration and lesson scheduling.
	 * Informs the student about the need to register for courses or adjust their schedule if required.
	 *
	 * @throws IOException If an I/O error occurs during the execution.
	 */
	public void run() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Сейчас вы в личном кабинете СТУДЕНТА. Год: " + Data.getInstance().getYear() + ", семестр: " + Data.getInstance().getSemester());
			if (Course.getRegistrationStatus()) {
				System.out.println("Регистрация на дисциплины открыта!");
				if (currentCourses.isEmpty()
						&& Manager.getRegistrationRequests().stream().noneMatch(n -> n.getSender().equals(this) && n.getStatus() == RequestStatus.SHIPPED)) {
					System.out.println("Ваш список дисциплин и запросов на регистрацию пуст. Пожалуйста, зарегистрируйте дисциплины!");
				}
			}
			if (Lesson.getSchedulingStatus()) {
				System.out.println("Составление расписания открыто!");
				if (!isScheduleComplete()) {
					System.out.println("Ваше расписание не готово. Пожалуйста, скорректируйте свое расписание");
				}
			}
			menu:
			while (true) {
				System.out.println("Что вы хотите сделать? \n1) Личные данные \n2) Новости \n3) Расписание \n4) Курсы " +
						"\n5) Организации \n6) Журнал \n7) Отправить tech report \n8) Exit");
				if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					System.out.println("9) Перейти в RESEARCHER");
				}
				int choice = in.nextInt();
				if (choice == 1) {
					viewPersonalDataRun();
				} else if (choice == 2) {
					viewNewsRun();
				} else if (choice == 3) {
					studentScheduleRun();
				} else if (choice == 4) {
					studentCoursesRun();
				} else if (choice == 5) {
					organizationsRun();
				} else if (choice == 6) {
					viewJournalRun();
				} else if (choice == 8) {
					this.logout();
					break menu;
				} else if(choice == 9 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					goToResearcher();
				} else if(choice == 7){
					sendTechReportRun();
				}
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
	/**
	 * Manages the scheduling operations for a student, providing options to view, add, and remove lessons from the schedule.
	 * The method displays a menu allowing the student to perform various actions related to their schedule.
	 * The scheduling status and available actions depend on the current state of lesson scheduling.
	 * If lesson scheduling is open, additional options to add or remove lessons are available.
	 * The method utilizes the console for user input and displays relevant information during the scheduling process.
	 */
	protected void studentScheduleRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		schedule:
		while (true) {
			if (!Data.getInstance().getSchedulingStatus())
				System.out.println("Составление расписания закрыто! \n1) Посмотреть расписание \n2) Узнать статус своего расписания \n3) Вернуться назад");
			else
				System.out.println("Составление расписания открыто! \n1) Посмотреть расписание \n2) Узнать статус своего расписания" +
						"\n3) Добавить урок в расписание \n4) Удалить урок из расписания \n5) Вернуться назад ");
			choice = in.nextInt();
			if (choice == 1) {
				System.out.println("Расписание " + Data.getInstance().getYear() + " " + Data.getInstance().getSemester());
				printSchedule();
				System.out.println("1) Вернуться назад \n2) Exit");
				choice = in.nextInt();
				if (choice == 1) continue schedule;
				if (choice == 2) {
					break;
				}
			} else if (choice == 2) {
				if (isScheduleComplete()) {
					System.out.println("Ваше расписание полностью сформировано!");
				} else {
					System.out.println("Ваше расписание сформировано не полностью!");
					for (RegisteredCourse r : currentCourses) {
						System.out.println("Код курса: " + r.getCourse().getCode() + ". Статус: " + ((isCourseScheduleComplete(r.getCourse())) ? "ГОТОВО" : "НЕ ГОТОВО"));
					}
					System.out.println("1) Вернуться назад \n2) Exit");
					choice = in.nextInt();
					if (choice == 1) continue schedule;
					if (choice == 2) {
						break;
					}
				}
			} else if (choice == 3 && !Data.getInstance().getSchedulingStatus()) {
				break;
			} else if (choice == 3) {
				addingLessons:
				while (true) {
					System.out.println("Выберите курс: ");
					Vector<RegisteredCourse> coursesForScheduling = new Vector<>(getCurrentCourses());
					int cnt = 0;
					for (RegisteredCourse r : coursesForScheduling) {
						System.out.println(++cnt + ") Статус: " + (isCourseScheduleComplete(r.getCourse()) ? "ГОТОВО" : "НЕ ГОТОВО") + ". Курс: " + r.getCourse().getTitle());
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if (choice >= cnt - 1 || choice < 1) continue schedule;
					RegisteredCourse currentCourse = coursesForScheduling.get(choice - 1);
					System.out.println("Выберите тип урока: \n1) LECTURE \n2)  PRACTICE \n3) LAB \n4) Вернуться назад");
					choice = in.nextInt();
					if (choice < 1 || choice > 3) continue schedule;
					Vector<Lesson> currentLessons = new Vector<>();
					if (choice == 1) {
						currentLessons = currentCourse.getCourse().getLessons().stream()
								.filter(n -> n.getType() == LessonType.LECTURE).collect(Collectors.toCollection(Vector::new));
					} else if (choice == 2) {
						currentLessons = currentCourse.getCourse().getLessons().stream()
								.filter(n -> n.getType() == LessonType.PRACTICE).collect(Collectors.toCollection(Vector::new));
					} else {
						currentLessons = currentCourse.getCourse().getLessons().stream()
								.filter(n -> n.getType() == LessonType.LAB).collect(Collectors.toCollection(Vector::new));
					}
					cnt = 0;
					for (Lesson l : currentLessons) {
						System.out.println(++cnt + ") " + l);
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if (choice >= cnt - 1 || choice < 1) continue schedule;
					Lesson currentLesson = currentLessons.get(choice - 1);
					boolean addLesson = addLessonToSchedule(currentCourse.getCourse(), currentLesson);
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
					if (choice >= cnt - 1 || choice < 1) continue schedule;
					Lesson lessonForDeleting = viewLessons().get(choice - 1);
					boolean delLesson = removeLessonFromSchedule(lessonForDeleting);
					Data.getInstance().getLogs().add(new Log(getId() + " удалил урок из расписания"));
					System.out.println((delLesson ? "Успешно удалено!" : "Ошибка при удалении.") + "\n1) Удалить еще один урок \n2) Вернуться назад \n3) Exit");
					choice = in.nextInt();
					if (choice == 1) continue deletingLessons;
					if (choice == 2) continue schedule;
					if (choice == 3) {
						break ;
					}
				}
			}
		}

	}
	/**
	 * Manages various operations related to student courses, providing options to view attestation, transcript, course grades,
	 * current courses, completed courses, not completed courses, teachers' ratings, course registration, and registration requests.
	 * The available actions depend on the current status of course registration. If registration is closed, some options are restricted.
	 * The method utilizes the console for user input and displays relevant information during the process.
	 */
	protected void studentCoursesRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		courses:
		while (true) {
			if (!Data.getInstance().getRegistrationStatus()) {
				System.out.println("Регистрация на курсы закрыта! \n1) Аттестация \n2) Транскрипт \n3) Оценки курса \n4) Текущие курсы \n5) Пройденные курсы" +
						"\n6) Не пройденные курсы \n7) Учителя \n8) Вернуться назад ");
			} else {
				System.out.println("Регистрация на курсы открыта! \n1) Аттестация \n2) Транскрипт \n3) Оценки курса \n4) Текущие курсы \n5) Пройденные курсы" +
						"\n6) Не пройденные курсы \n7) Учителя  \n8) Подать заявку на ADD/DROP курса \n9) Посмотреть заявки на регистрацию" +
						"\n10) Вернуться назад");
			}
			choice = in.nextInt();
			if (choice == 1) {
				for (String s : viewAttestation()) {
					System.out.println(s);
				}
				System.out.println("1) Вернуться назад \n2) Exit");
				choice = in.nextInt();
				if (choice == 1) continue courses;
				if (choice == 2) {
					break;
				}
			} else if (choice == 2) {
				for (String s : viewTranscript()) {
					System.out.println(s);
				}
				System.out.println("1) Вернуться назад \n2) Exit");
				choice = in.nextInt();
				if (choice == 1) continue courses;
				if (choice == 2) {
					break;
				}
			} else if (choice == 3) {
				grades:
				while (true) {
					System.out.println("Выберите курс: ");
					int cnt = 0;
					Vector<RegisteredCourse> gradeCourses = new Vector<>(currentCourses);
					for (RegisteredCourse r : gradeCourses) {
						System.out.println(++cnt + ") " + r.getCourse().getCode() + " " + r.getCourse().getTitle());
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if (choice >= cnt - 1 || choice < 1) continue courses;
					RegisteredCourse gradeCourse = gradeCourses.get(choice - 1);
					System.out.println("Оценки " + gradeCourse.getCourse().getCode() + " " + gradeCourse.getCourse().getTitle());
					for (Mark m : gradeCourse.getMarks()) {
						System.out.println(m);
					}
					System.out.println("1) Посмотреть другие оценки \n2) Вернуться назад \n2) Exit");
					choice = in.nextInt();
					if (choice == 1) continue grades;
					if (choice == 2) continue courses;
					if (choice == 3) {
						break;
					}
				}
			} else if (choice >= 4 && choice <= 6) {
				if (choice == 4) {
					System.out.println("Текущие курсы за " + Data.getInstance().getYear() + " " + Data.getInstance().getSemester());
					for (RegisteredCourse r : currentCourses) {
						System.out.println(r);
					}
				} else if (choice == 5) {
					System.out.println("Пройденные курсы");
					for (RegisteredCourse r : completedCourses) {
						System.out.println(r);
					}
				} else {
					System.out.println("Не пройденные курсы");
					for (RegisteredCourse r : notCompletedCourses) {
						System.out.println(r);
					}
				}
				System.out.println("1) Вернуться назад \n2) Exit");
				choice = in.nextInt();
				if (choice == 1) continue courses;
				if (choice == 2) {
					break;
				}
			} else if (choice == 7) {
				teachersRating:
				while (true) {
					Vector<Teacher> teachers = currentCourses.stream().flatMap(n -> n.getLessons().stream())
							.map(Lesson::getTeacher).distinct().collect(Collectors.toCollection(Vector::new));
					System.out.println("Учителя, которые вам преподают: ");
					int cnt = 0;
					for (Teacher t : teachers) {
						System.out.println(++cnt + ") " + viewInfoAboutTeacher(t));
					}
					System.out.println("1) Оценить учителя(id) \n2) Вернуться назад");
					choice = in.nextInt();
					if (choice == 1) {
						System.out.println("Выберите учителя: ");
						choice = in.nextInt();
						if (choice < 1 || choice > cnt) continue courses;
						Teacher teacherRate = teachers.get(choice - 1);
						System.out.println("Введите оценку от 0 до 10 (целое число): ");
						int rate = in.nextInt();
						if (rate < 0 || rate > 10) {
							System.out.println("Неверная оценка");
							continue courses;
						}
						boolean rating = rateTeacher(teacherRate, rate);
						Data.getInstance().getLogs().add(new Log(getId() + " оценил учителя " + teacherRate.getId()));
						System.out.println((rating ? "Оценка поставлена!" : "Ошибка при оценивании") + "\n1) Оценить другого учителя \n2)Вернуться назад \n3) Exit");
						choice = in.nextInt();
						if (choice == 1) continue teachersRating;
						if (choice == 2) continue courses;
						if (choice == 3) {
							this.logout();
						}
					} else if (choice == 2) {
						continue courses;
					}
				}
			} else if (choice == 8 && !Data.getInstance().getRegistrationStatus()) {
				break;
			} else if (choice == 8) {
				addingCourses:
				while (true) {
					System.out.println("Выберите тип заявки: \n1) ADD \n2) DROP \n3) Вернуться назад");
					choice = in.nextInt();
					if (choice == 1 || choice == 2) {
						Course choicedCourse = null;
						RegistrationType type = null;
						Vector<Course> actualCourses;
						int cnt = 0;
						if (choice == 1) {
							type = RegistrationType.ADD;
							System.out.println("Выберите курс: ");
							actualCourses = Data.getInstance().getCourses().stream().filter(n -> n.getYear() == Data.getInstance().getYear())
									.filter(n -> n.getSemester() == Data.getInstance().getSemester()).collect(Collectors.toCollection(Vector::new));
						} else {
							type = RegistrationType.DROP;
							System.out.println("Выберите курс: ");
							actualCourses = currentCourses.stream().map(RegisteredCourse::getCourse).collect(Collectors.toCollection(Vector::new));
						}
						for (Course c : actualCourses) {
							System.out.println(++cnt + ") " + c);
						}
						System.out.println(++cnt + ") Вернуться назад");
						choice = in.nextInt();
						if (choice >= cnt || choice < 1) continue addingCourses;
						choicedCourse = actualCourses.get(choice - 1);
						try {
							boolean request = registerToCourse(choicedCourse, type);
							Data.getInstance().getLogs().add(new Log(getId() + " отправил заявку на регистрацию курса " + choicedCourse.getCode()));
							System.out.println((request ? "Заявка успешно отправлена!" : "Не удалось отправить заявку.")
									+ "\n1) Отправить еще одну заявку \n2) Вернутся в меню \n3) Exit");
							choice = in.nextInt();
							if (choice == 1) continue addingCourses;
							if (choice == 2) continue courses;
							if (choice == 3) {
								break ;
							}
						} catch (DuplicateRegistrationException e) {
							System.out.println("Такая заявка уже существует! \n1) Вернуться назад \n2) Exit");
							choice = in.nextInt();
							if (choice == 1) continue addingCourses;
							if (choice == 2) {
								break ;
							}
						} catch (CourseRegisteredException e) {
							System.out.println("Ошибка при регистрации! " + e + "\n1) Вернуться назад \n2) Exit");
							choice = in.nextInt();
							if (choice == 1) continue addingCourses;
							if (choice == 2) {
								break ;
							}
						} catch (MaxCreditsException e) {
							System.out.println("Превышение кредитов! \n1) Вернуться назад \n2) Exit");
							choice = in.nextInt();
							if (choice == 1) continue addingCourses;
							if (choice == 2) {
								break ;
							}
						}
					} else if (choice == 3) {
						continue courses;
					}
				}
			} else if (choice == 9) {
				Vector<RegistrationRequest> requests = Data.getInstance().getRegistrationRequests().stream().filter(n -> n.getSender().equals(this)).collect(Collectors.toCollection(Vector::new));
				System.out.println("Ваши заявки на регистрацию: ");
				for (RegistrationRequest r : requests) {
					System.out.println(r);
				}
				System.out.println("1) Вернуться назад \n2) Exit");
				choice = in.nextInt();
				if (choice == 1) continue courses;
				if (choice == 2) {
					break;
				}
			} else if (choice == 10) {
				break ;
			}
		}
	}
	/**
	 * Manages operations related to student organizations, including joining and leaving organizations.
	 * If the student is already part of an organization, they have the option to leave or remain.
	 * If not, they can choose to join an existing organization. Leaders of organizations have additional privileges.
	 * The method utilizes the console for user input and displays relevant information during the process.
	 */
	protected void organizationsRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		organizations:
		while (true) {
			if (getOrganization() != null) {
				System.out.println("Вы состоите в организации " + getOrganization().getName());
				if (getOrganization().getHead().equals(this)) {
					System.out.println("Вы глава данной организации!");
				} else if (getOrganization().getHead() == null) {
					System.out.println("Лидер данной организации отсутствует. \n1) Стать лидером \n2) Продолжить");
					choice = in.nextInt();
					if (choice == 1) {
						getOrganization().setHead(this);
						System.out.println("Поздравляем! Вы стали лидером " + getOrganization().getName());
					}
				}
				System.out.println("1) Выйти из организации \n2) Вступить в другую организацию \n3) Вернуться назад");
			} else {
				System.out.println("Вы не состоите в организации");
				System.out.println("1) Вступить в организацию \n2) Вернуться назад \n3) Создать организацию");
			}
			choice = in.nextInt();
			if (choice == 1 && getOrganization() != null) {
				leaveFromOrganization();
				Data.getInstance().getLogs().add(new Log(getId() + " вышел из организации"));
				break;
			} else if ((choice == 2 && getOrganization() != null) || (choice == 1 && getOrganization() == null)) {
				System.out.println("Выберите организацию из списка: ");
				int cnt = 0;
				Vector<Organization> allOrganizations = Data.getInstance().getOrganizations();
				for (Organization o : allOrganizations) {
					System.out.println(++cnt + ") " + o);
				}
				System.out.println(++cnt + ") Вернуться назад");
				choice = in.nextInt();
				if (choice >= cnt || choice < 1) break;
				Organization organ = allOrganizations.get(choice - 1);
				joinToOrganization(organ);
				Data.getInstance().getLogs().add(new Log(getId() + " вступил в организацию " + organ.getName()));
				System.out.println("Вы успешно вступили в организацию " + organ);
				break;
			} else if(choice == 3 && getOrganization() == null){
				System.out.println("Введите имя организации: ");
				in.nextLine();
				String text = in.nextLine();
				createOrganization(text);
				Data.getInstance().getLogs().add(new Log(getId() + " создал организацию " + text));
				System.out.println("Успешно!");
				break;
			}  else if ((choice == 3 && getOrganization() != null) || (choice == 2 && getOrganization() == null)){
				break;}

		}
	}
}
