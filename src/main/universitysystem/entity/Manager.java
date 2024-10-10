package universitysystem.entity;

import universitysystem.courses.*;
import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.interfaces.StudentsInfo;
import universitysystem.interfaces.TeachersInfo;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Represents a Manager within the university system.
 * A manager is an employee who handles administrative tasks such as processing registration requests, managing courses,
 * and coordinating the university system.
 * @version 1.0
 */
public class Manager extends Employee implements TeachersInfo, StudentsInfo, Serializable
{
	private ManagerType type;

	private static Vector<RegistrationRequest> registrationRequests;

	private static Vector<EmployeeRequest> employeeRequests;

	static{
		registrationRequests = new Vector<>();
		employeeRequests = new Vector<>();
	}

	{
		type = null;
	}
    /**
     * Default constructs.
     */
	public Manager(){
		super();
	}
    /**
     * Constructs a Manager object with the given login and password.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     */
	public Manager(String login, String password) {
		super(login, password);
	}
    /**
     * Constructs a Manager object with the given login, password, salary, and type.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     * @param salary - salary for the Manager.
     * @param type - type of the Manager.
     */
	public Manager(String login, String password, double salary, ManagerType type) {
		super(login, password, salary);
		this.type = type;
	}
    /**
     * Constructs a Manager object with the given login, password, and email.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     * @param email - email for the Manager.
     */
	public Manager(String login, String password, String email) {
		super(login, password, email);
	}
    /**
     * Constructs a Manager object with the given login, password, email, salary, and type.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     * @param email - email for the Manager.
     * @param salary - salary for the Manager.
     * @param type - type of the Manager.
     */
	public Manager(String login, String password, String email, double salary, ManagerType type) {
		super(login, password, email, salary);
		this.type = type;
	}
    /**
     * Constructs a Manager object with the given login, password, email, firstname,
     * lastname, middlename, and birthDate.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     * @param email - email for the Manager.
     * @param firstname - first name of the Manager.
     * @param lastname - last name of the Manager.
     * @param middlename - middle name of the Manager.
     * @param birthDate - birth date of the Manager.
     */
	public Manager(String login, String password, String email, String firstname,
				   String lastname, String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}
    /**
     * Constructs a Manager object with the given login, password, email, firstname,
     * lastname, middlename, birthDate, salary, and type.
     * @param login - login for the Manager.
     * @param password - password for the Manager.
     * @param email - email for the Manager.
     * @param firstname - first name of the Manager.
     * @param lastname - last name of the Manager.
     * @param middlename - middle name of the Manager.
     * @param birthDate - birth date of the Manager.
     * @param salary - salary for the Manager.
     * @param type - type of the Manager.
     */
	public Manager(String login, String password, String email, String firstname,
				   String lastname, String middlename, Date birthDate, double salary, ManagerType type) {
		super(login, password, email, firstname, lastname, middlename, birthDate, salary);
		this.type = type;
	}
    /**
     * Generates a unique identifier for the manager using the current year and the system identifier counter.
     * @return The generated ID for the teacher.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "M" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Gets the type of the Manager.
     * @return Type of the Manager.
     */
	public ManagerType getType() {
		return type;
	}
    /**
     * Sets the type of the Manager.
     * @param type - to set for the Manager.
     */
	public void setType(ManagerType type) {
		this.type = type;
	}
    /**
     * Gets the registration requests submitted to the Manager.
     * @return Vector of registration requests.
     */
	public static Vector<RegistrationRequest> getRegistrationRequests() {

		return Data.getInstance().getRegistrationRequests();
	}
    /**
     * Sets the registration requests for the Manager.
     * @param registrationRequests The vector of registration requests to set.
     */
	//TODO я только что допер, что это очень правильно, емаеее
	//мы когда с сериализации подтягивать все будет, нам все эти методы нужны
	public static void setRegistrationRequests(Vector<RegistrationRequest> registrationRequests) {
		Manager.registrationRequests = registrationRequests;
	}
    /**
     * Gets the employee requests submitted to the Manager.
     * @return Vector of employee requests.
     */
	public static Vector<EmployeeRequest> getEmployeeRequests() {
		return Data.getInstance().getEmployeeRequests();
	}
    /**
     * Sets the employee requests for the Manager.
     * @param employeeRequests The vector of employee requests to set.
     */
	public static void setEmployeeRequests(Vector<EmployeeRequest> employeeRequests) {
		Manager.employeeRequests = employeeRequests;
	}
	/**
	 * Adds a course to the current courses of a student.
	 * @param student The student to whom the course is added.
	 * @param r The registered course to be added.
	 */
	public void addCourse(Student student, RegisteredCourse r) {
		student.addCurrentCourse(r);
	}
	/**
	 * Drops a course from the current courses of a student.
	 * @param student The student from whom the course is dropped.
	 * @param course  The course to be dropped.
	 * @return {@code true} if the course is successfully dropped, {@code false} otherwise.
	 */
	public boolean dropCourse(Student student, Course course) {
		return student.getCurrentCourses().removeIf(registeredCourse -> registeredCourse.getCourse().equals(course));
	}
    /**
     * Approves a registration request.
     * @param r The registration request to be approved.
     * @return True if the request is approved successfully, false otherwise.
     */
	public boolean approveRegistration(RegistrationRequest r){
		r.getSender().addNotification(new Notification("Заявка на регистрацию курса", "Ваша заявка была рассмотрена!"));
		r.setStatus(RequestStatus.DONE);
		return true;
	}
    /**
     * Rejects a registration request
     * @param r - registration request to be rejected.
     * @return True if the request is rejected successfully, false otherwise.
     */
	public boolean rejectRegistration(RegistrationRequest r) {
		r.getSender().addNotification(new Notification("Заявка на регистрацию курса", "Ваша заявка была отклонена."));
		r.setStatus(RequestStatus.REFUSED);
		return false;
	}
    /**
     * Processes the registration request, making a decision on its approval or rejection in accordance with certain conditions.
     * @param r - registration request to be considered.
     * @return True if the request is approved, false if rejected.
     */
	public boolean considerRegistration(RegistrationRequest r) {
		return assignCourseToStudent(r) ? (approveRegistration(r)) : (rejectRegistration(r));

	}
	/**
	 * Assigns the course is for the student according to the registration request.
	 * @param r - registration request.
	 * @return {@code true} if the course is successfully assigned, {@code false} otherwise.
	 */
	public boolean assignCourseToStudent(RegistrationRequest r) {
		Student student = (Student) r.getSender();
		CourseType courseType = null;
		if (r.getRegistrationType() == RegistrationType.DROP){
			return dropCourse(student, r.getCourse());
		}
		if (r.getCourse().getMajorFaculties().contains(student.getFaculty()))
			courseType = CourseType.MAJOR;
		if (r.getCourse().getFreeElectiveFaculties().contains(student.getFaculty()))
			courseType = CourseType.FREE_ELECTIVE;
		if (r.getCourse().getMinorFaculties().contains(student.getFaculty()))
			courseType = CourseType.MINOR;
		if (student.getCompletedCourses().stream()
				.allMatch(registeredCourse ->
						r.getCourse().getPrerequisites().contains(registeredCourse.getCourse()))
		) {
			addCourse(student, new RegisteredCourse(r.getCourse(), courseType));
			return true;
		}
		return false;
	}
	/**
	 * Adds course to the registration system.
	 * @param c - course to be added.
	 * @return {@code true} if the course is successfully added, {@code false} otherwise.
	 */
	public boolean addCourseToRegistration(Course c) {
		Data.getInstance().addCourse(c);
		return true;
	}
	/**
	 * Assigns a course to a teacher.
	 * @param c - course to be assigned.
	 * @param t - teacher to whom the course is assigned.
	 * @return {@code true} if the course is successfully assigned, {@code false} otherwise.
	 */
	public boolean assignCourseToTeacher(Course c, Teacher t) {
		if(t.getCourses().stream().anyMatch(n -> n.equals(c))) return false;
		return t.addCourse(c);
	}
	/**
	 * Adds news item to the system.
	 * @param n - news item to be added.
	 * @return {@code true} if the news item is successfully added, {@code false} otherwise.
	 */
	public boolean addNews(News n) {
		Data.getInstance().addNews(n);
		return true;
	}
	/**
	 * Deletes a news item from the system.
	 * @param n - news item to be deleted.
	 * @return {@code true} if the news item is successfully deleted, {@code false} otherwise.
	 */
	public boolean deleteNews(News n) {
		return Data.getInstance().removeNews(n);
	}
	/**
	 * Deletes a news item from the system based on its ID.
	 * @param id -  of the news item to be deleted.
	 * @return {@code true} if the news item is successfully deleted, {@code false} otherwise.
	 */
	public boolean deleteNews(int id) {
		return Data.getInstance().getNews().removeIf(news -> news.getId() == id);
	}
	/**
	 * Removes course from the registration system.
	 * @param c - course to be removed.
	 * @return {@code true} if the course is successfully removed, {@code false} otherwise.
	 */
	public boolean removeCourseFromRegistration(Course c) {
		if (Data.getInstance().getCourses() == null){
			return false;
		}
		Data.getInstance().removeCourse(c);
		return true;
	}
	/**
	 * Completes an employee request.
	 * @param e - employee request to be completed.
	 * @return {@code true} if the employee request is successfully completed, {@code false} otherwise.
	 */
	public boolean completeEmployeeRequest(EmployeeRequest e) {
		if(e.getSigned()){
			e.setStatus(RequestStatus.DONE);
			return true;
		}
		return rejectEmployeeRequest(e);
	}
	/**
	 * Rejects an employee request.
	 * @param e - employee request to be rejected.
	 * @return {@code true} if the employee request is successfully rejected, {@code false} otherwise.
	 */
	public boolean rejectEmployeeRequest(EmployeeRequest e) {
		e.setStatus(RequestStatus.REFUSED);
		return true;
	}
	/**
	 * Opens course registration.
	 */
	public void openCourseRegistration() {

		Data.getInstance().setRegistrationStatus(true);
	}
	/**
	 * Closes course registration.
	 */
	public void closeCourseRegistration() {

		Data.getInstance().setRegistrationStatus(false);
	}
	/**
	 * Opens lesson scheduling.
	 */
	public void openScheduling(){

		Data.getInstance().setSchedulingStatus(true);
	}
	/**
	 * Closes lesson scheduling.
	 */
	public void closeScheduling(){

		Data.getInstance().setSchedulingStatus(false);
	}
	/**
	 * Ends the semester, advancing to the next.
	 * @return {@code true} if the semester is successfully ended, {@code false} otherwise.
	 */
	public boolean endSemester() {
		Data.getInstance().nextSemester();
		return true;
	}
	/**
	 * Adds lesson to a course.
	 * @param c - course to which the lesson is added.
	 * @param l - lesson to be added.
	 * @return {@code true} if the lesson is successfully added, {@code false} otherwise.
	 */
	public boolean addLessonToCourse(Course c, Lesson l) {
		if(c.getLessons().stream().anyMatch(n -> n.equals(l))) return false;
		return c.addLesson(l);
	}
	/**
	 * Removes lesson.
	 * @param c - course from which the lesson is removed.
	 * @param l - lesson to be removed.
	 * @return {@code true} if the lesson is successfully removed, {@code false} otherwise.
	 */
	public boolean removeLessonFromCourse(Course c, Lesson l) {
		return c.removeLesson(l);
	}
	/**
	 * Changes the teacher of a lesson in a course.
	 * @param c - course containing the lesson.
	 * @param newTeacher - new teacher to be assigned.
	 * @param l - lesson to have its teacher changed.
	 * @return {@code true} if the teacher change is successful, {@code false} otherwise.
	 */
	//Можно просто вызвать для нового учителя метод добавления урока у него, и там уже все проверки будут
	//и если вернет тру, то значит замена успешна
	public boolean changeTeacher(Course c, Teacher newTeacher, Lesson l) {
		c.getLessons().stream().filter(lesson -> lesson.equals(l)).findFirst().ifPresent(lesson -> lesson.setTeacher(newTeacher));
		return true;
	}

	public HashSet<Student> getStudents(){
		return Data.getInstance().getStudents();
	}
	public List<Student> getStudents(Comparator<Student> comparator){
		return getStudents().stream().sorted(comparator).toList();
	}
	public List<Student> getStudents(Organization organization){
		return getStudents().stream().filter(student -> student.getOrganization() != null &&
														student.getOrganization().equals(organization)).toList();
	}
	public List<Student> getStudents(int year){
		return getStudents().stream().filter(student ->
				student.getYearOfStudy() == year).toList();
	}
	public List<Student> getStudents(Faculty faculty){
		return getStudents().stream().filter(student -> student.getFaculty() != null &&
				student.getFaculty().equals(faculty)).toList();
	}
    /**
     * Views information about students enrolled in a specific course.
     * @param c - course for which information is to be retrieved.
     * @return Representation of student information in the given course.
     */
    public List<Student> getStudents(Course c) {
        return Data.getInstance().getStudents().stream()
                .filter(student -> student.getCurrentCourses().stream()
                        .allMatch(registeredCourse -> registeredCourse != null &&
								registeredCourse.getCourse().equals(c)))
                .toList();
    }
	public HashSet<Teacher> getTeachers(){
		return Data.getInstance().getTeachers();
	}
	public List<Teacher> getTeachers(Comparator<Teacher> comparator){
		return getTeachers().stream().sorted(comparator).toList();
	}
	public List<Teacher> getTeachers(Faculty faculty) {
		return getTeachers().stream()
				.filter(teacher -> teacher.getFaculty() != null && teacher.getFaculty().equals(faculty))
				.toList();
	}
	public List<Teacher> getTeachers(TeacherType teacherType){
		return getTeachers().stream()
				.filter(teacher -> teacher.getType().equals(teacherType))
				.toList();
	}
	public List<Teacher> getTeachers (Course c) {
		return c.getLessons().stream().map(Lesson::getTeacher).collect(Collectors.toSet()).stream().toList();
	}
	public void run() throws IOException {
		Scanner in = new Scanner(System.in);
		menu:
		while (true) {
			System.out.println("""
					Меню Manager. Что вы хотите сделать?
					1 : Просмотреть заявки на регистрацию
					2 : Просмотреть заявки работников
					3 : Посмотреть информацию о студентах
					4 : Посмотреть информацию о учителе
					5 : Курсы
					6 : Новости
					7 : Семестр
					8 : Регистрация на дисциплины
					9 : Расписание
					0 : Выйти
					""");
			if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))) {
				System.out.println("10: Перейти в RESEARCHER");
			}
			int choice = in.nextInt();
			switch (choice) {
				case 1 -> {
					considerRegistration:
					while (true) {
						System.out.println("""
								1 : Просмотреть неотвеченные заявки
								2 : Просмотреть отклоненные заявки
								3 : Просмотреть принятые заявки
								4 : Просмотреть все заявки
								""");
						choice = in.nextInt();
						RequestStatus status = switch (choice) {
							case 1 -> RequestStatus.SHIPPED;
							case 2 -> RequestStatus.REFUSED;
							case 3 -> RequestStatus.DONE;
							case 4 -> null;
							default -> RequestStatus.SHIPPED;
						};
						while (true) {
							List<RegistrationRequest> requests = printRequests(status);
							if (requests == null){
								continue menu;
							}
							choice = in.nextInt();
							System.out.println("""
									Что вы хотите сделать с этой заявкой?
									1 : Принять
									2 : Отклонить
									3 : Автоматический
									4 : Вернуться к выбору запросов
									5 : Вернуться в меню
									6 : Выйти
									""");
							RegistrationRequest request = requests.get(choice - 1);
							System.out.println(request);
							choice = in.nextInt();
							switch (choice) {
								case 1 -> {
									printRequestStatus(approveRegistration(request));
								}
								case 2 -> {
									printRequestStatus(rejectRegistration(request));
								}
								case 3 -> {
									printRequestStatus(considerRegistration(request));
								}
								case 4 -> {
									continue considerRegistration;
								}
								case 5 -> {
									continue menu;
								}
								case 6 -> {
									logout();
									break menu;
								}
							}
						}
					}
				}
				case 2 -> {
						System.out.println("""
								0 : Неподписанные
								1 : Подписанные
								""");
						choice = in.nextInt();
						boolean bool = choice != 0;
						System.out.println("""
								1 : Просмотреть неотвеченные заявки
								2 : Просмотреть отклоненные заявки
								3 : Просмотреть принятые заявки
								4 : Просмотреть все заявки
								""");
						choice = in.nextInt();
						RequestStatus status = switch (choice) {
							case 1 -> RequestStatus.SHIPPED;
							case 2 -> RequestStatus.REFUSED;
							case 3 -> RequestStatus.DONE;
							case 4 -> null;
							default -> throw new IllegalStateException("Unexpected value: " + choice);
						};
						if (status == null){
							System.out.println(employeeRequests.stream()
									.filter(employeeRequest -> employeeRequest.getSigned() == bool).toList());
						}
						else System.out.println(employeeRequests.stream()
								.filter(employeeRequest -> employeeRequest.getSigned() == bool)
								.filter(employeeRequest -> employeeRequest.getStatus().equals(status)).toList());
					}
				case 3 -> {
					List<Student> students = null;
					filterUsers:
					while (true) {
						System.out.println("""
								Отфильтровать
								1 : По году обучения
								2 : По факультету
								3 : По организации
								4 : Вывести всех
								5 : Вернуться в меню
								6 : Выход
								""");
						choice = in.nextInt();
						switch (choice) {
							case 1 -> {
								students = getStudents(determineYear());
							}
							case 2 -> students = getStudents(determineFaculty());
							case 3 -> {
								printOrganizations();
								choice = in.nextInt();
								students = getStudents(Data.getInstance().getOrganizations().stream().toList().get(choice));
							}
							case 4 -> students = getStudents().stream().toList();
							case 5 -> {
								break filterUsers;
							}
							case 6 -> {
								logout();
								break menu;
							}
							default -> {
								System.out.println("""
										Неправильный ввод
										""");
								continue menu;
							}
						}
						sortUser:
						while (true) {
							System.out.println("""
									Отсортировать
									1 : По гпа
									2 : По айди
									3 : По фамилии
									4 : По имени
									5 : Перейти к фильтрам
									6 : Перейти в меню
									7 : Выйти
									""");
							choice = in.nextInt();
							Comparator<Student> comparator = Comparator.comparing(Student::getId);
							switch (choice) {
								case 1 -> comparator = Comparator.comparing(Student::getGpa);
								case 2 -> {

								}
								case 3 -> {
									comparator = Comparator.comparing(Student::getLastname);
								}
								case 4 -> {
									comparator = Comparator.comparing(Student::getFirstname);
								}
								case 5 -> {
									continue filterUsers;
								}
								case 6 -> {
									continue menu;
								}
								case 7 -> {
									logout();
									break menu;
								}
								default -> {
									System.out.println("""
											Попробуйте ещё раз
											""");
								}
							};
							outputList(students != null ? students.stream().sorted(comparator).toList() : null);
						}
					}
				}
				case 4 ->{
					while (true) {
						System.out.println("""
								0 : Вывести всех
								1 : По факультету
								2 : По квалификации
								3 : Вернуться в меню
								4 : Выйти
								""");
						choice = in.nextInt();
						switch (choice) {
							case 0 -> {
								System.out.println(getTeachers().stream().map(Teacher::toString).collect(Collectors.joining("\n")));
							}
							case 1 -> {
								outputList(getTeachers(determineFaculty()));
							}
							case 2 -> {
								outputList(getTeachers(determineTeacher()));
							}
							case 3 -> {
								continue menu;
							}
							case 4 -> {
								logout();
								break menu;
							}
						}
					}
				}
				case 5 -> {
					System.out.println("""
							1 : Создать новый курс
							2 : Добавить курс на регистрацию
							3 : Удалить курс из регистрации
							4 : Добавить урок в курс
							5 : Удалить урок из курса
							6 : Поменять учителя на уроке
							""");
					choice = in.nextInt();
					switch (choice){
						case 1 -> System.out.println(createCourse());
						case 2 -> {
							Course course = chooseCourse();
							if (addCourseToRegistration(Objects.requireNonNull(course))) {
								System.out.println("""
										Курс добавлен
										""");
								Data.getInstance().addLog(new Log("New course in registration: " + course));
							}
							else {
								System.out.println("""
										Не удалось
										""");
							}
						}
						case 3 -> {
							Course course = chooseCourse();
							if (removeCourseFromRegistration(course)){
								System.out.println("""
										Курс удалён
										""");
								Data.getInstance().addLog(new Log("Course removed from registration: " + course));
							}
							else System.out.println("""
									Курсов нет
									""");
						}
						case 4 -> addLessonToCourse(Objects.requireNonNull(chooseCourse()), createLesson());
						case 5 -> {
							Course course = chooseCourse();
							removeLessonFromCourse(Objects.requireNonNull(course), chooseLesson(course));
						}
					}
				}
				case 6 -> {
					System.out.println("""
							1 : Просмотреть новости
							2 : Создать новость
							3 : Изменить новость
							4 : Удалить новость
							""");
					choice = in.nextInt();
					switch (choice) {
						case 1 -> outputList(viewNews());
						case 2 -> {
							System.out.println("""
									Введите название новости
									""");
							String title = in.next();
							System.out.println("""
									Введите текст новости
									""");
							String text = in.next();
							TopicType type = chooseTopic();

							addNews(new News(type, title, text));
						}
						case 3 -> {
							changeNews:
							while (true) {
								System.out.println("""
										Введите айди новости
										""");
								choice = in.nextInt();
								News news = viewNews().get(choice);
								System.out.println("""
											Эта новость?
											0 : Нет
											1 : Да
											""" + news);
								choice = in.nextInt();
								switch (choice) {
									case 0 -> {
										System.out.println("""
												Попробуйте ещё раз
												""");
										continue;
									}
									case 1 -> {
										System.out.println("""
												Что поменять?
												1 : Тип новости
												2 : Название
												3 : Текст
												""");
										choice = in.nextInt();
										switch (choice) {
											case 1 -> {
												news.setTopic(chooseTopic());
											}
											case 2 -> {
												news.setTitle(in.next());
											}
											case 3 -> {
												news.setText(in.next());
											}
										}
									}
								}
								break;
							}
						}
						case 4 -> {
							System.out.println("""
									Введите айди новости
									""");
							deleteNews(in.nextInt());
						}
					}
				}
				case 7 -> {
					System.out.println("""
							1 : Начать семестр
							2 : Закончить семестр
							""");
					choice = in.nextInt();
					switch (choice){
						case 1 -> {
							Data.getInstance().nextSemester();
						}
						case 2 -> {
							endSemester();
						}
					}
				}
				case 8 -> {
					System.out.println("""
							1 : Открыть регистрацию на дисциплины
							2 : Закрыть регистрацию на дисциплины
							3 : Добавить курс на регистрацию
							4 : Удалить курс из регистрации
							""");
					choice = in.nextInt();
					switch (choice){
						case 1 -> openCourseRegistration();
						case 2 -> closeCourseRegistration();
						case 3 -> {
							addCourseToRegistration(createCourse());
						}
						case 4 -> {
							removeCourseFromRegistration(chooseCourse());
						}
					}
				}
				case 9 -> {
					System.out.println("""
							1 : Открыть составление расписания
							2 : Окончить составление расписания
							""");
					choice = in.nextInt();
					switch (choice){
						case 1 -> {
							openScheduling();
						}
						case 2 -> {
							closeScheduling();
						}
					}
				}
				case 10 -> {
					if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
						goToResearcher();
					} else{
						continue menu;
					}
				}
				case 0 -> {
					logout();
					break menu;
				}
			}
		}
	}

	private <T> void outputList(List<T> list) {
		System.out.println(list.stream().map(T::toString).collect(Collectors.joining("\n")));
	}

	private List<RegistrationRequest> printRequests(RequestStatus status){
		int count = 1;
		List<RegistrationRequest> requests = Data.getInstance().getRegistrationRequests().stream()
				.filter(registrationRequest -> registrationRequest.getStatus().equals(status)).toList();
		if (status == null || requests.isEmpty()){
			System.out.println("""
					Заявок нет
					""");
			return null;
		}
		for (RegistrationRequest registrationRequest : requests) {
			System.out.println(count++ + "  : " + registrationRequest);
		}
		System.out.println("""
				Выберите заявку
				""");
		return requests;
	}


	private void printRequestStatus(boolean bool){
		if (bool) {
			System.out.println("""
					Запрос одобрен
					""");
		}
		else System.out.println("""
				Запрос отклонен
				""");
	}

	private TeacherType determineTeacher(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
			1 : Tutor
			2 : Senior Lecturer
			3 : Professor
			""");
		return switch (in.nextInt()) {
			case 1 -> TeacherType.TUTOR;
			case 2 -> TeacherType.SENIOR_LECTURE;
			case 3 -> TeacherType.PROFESSOR;
			default -> TeacherType.TUTOR;
		};
	}
	private Faculty determineFaculty() {
		Scanner in = new Scanner(System.in);
		int choice;
		System.out.println("""
				1 : SITE
				2 : BS
				3 : SEOGI
				4 : SG
				5 : ISE
				6 : KMA
				7 : SAM
				8 : SCE
				9 : SMSGT
				 """);
		choice = in.nextInt();
		return switch (choice) {
			case 1 -> Faculty.SITE;
			case 2 -> Faculty.BS;
			case 3 -> Faculty.SEOGI;
			case 4 -> Faculty.SG;
			case 5 -> Faculty.ISE;
			case 6 -> Faculty.KMA;
			case 7 -> Faculty.SAM;
			case 8 -> Faculty.SCE;
			case 9 -> Faculty.SMSGT;
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		};
	}
	private Teacher chooseTeacher(List<Teacher> teachers){
		Scanner in = new Scanner(System.in);
		int count = 1;
		for (Teacher teacher : teachers) {
			System.out.println(count++ + "  : " + teacher.toString());
		}
		System.out.println("""
				Выберите учителя для просмотра информации
				Чтобы выйти введите 0
				""");
		int choice = in.nextInt();
		if (choice == 0) return null;
		return teachers.get(choice - 1);
	}
	private Student chooseStudent(List<Student> students){
		Scanner in = new Scanner(System.in);
		int count = 1;
		for (Student student : students) {
			System.out.println(count++ + "  : " + student.toString());
		}
		System.out.println("""
				Выберите студента для просмотра информации
				Чтобы выйти введите 0
				""");
		int choice = in.nextInt();
		if (choice == 0) return null;
		return students.get(choice - 1);
	}
	private void printOrganizations(){
		int count = 1;
		List<Organization> organizations = Data.getInstance().getOrganizations();
		if (organizations.isEmpty()){
			System.out.println("""
					Организации не существует
					""");
		}
		for (Organization organization : organizations) {
			System.out.println(count++ + "  : " + organization.getName());
		}
		System.out.println("""
				Выберите организацию
				""");
	}

	private int determineYear(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				1 : 1 год
				2 : 2 год
				3 : 3 год
				4 : 4 год
				5 : Вернуться назад
				6 : Выйти
				""");
		int choice = in.nextInt();
		return switch (choice) {
			case 1 -> 1;
			case 2 -> 2;
			case 3 -> 3;
			case 4 -> 4;
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		};
	}
	private TopicType chooseTopic(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				1 : Research
				2 : Lessons
				3 : System
				4 : Study
				5 : Other
				""");
		int choice = in.nextInt();
        return switch (choice){
			case 1 -> TopicType.RESEARCH;
			case 2 -> TopicType.LESSONS;
			case 3 -> TopicType.SYSTEM;
			case 4 -> TopicType.STUDY;
			case 5 -> TopicType.OTHER;
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		};
	}

	private Course chooseCourse(){
		Scanner in = new Scanner(System.in);
		int count = 1;
		List<Course> courses = Data.getInstance().getCourses().stream().toList();
		if (courses.isEmpty()){
			return null;
		}
		for (Course course : courses) {
			System.out.println(count++ + "  : " + course.toString());
		}
		System.out.println("""
				Выберите курс
				""");
		int choice = in.nextInt();
		return courses.get(choice - 1);

	}

	private Lesson chooseLesson(Course c){
		Scanner in = new Scanner(System.in);
		int count = 1;
		for (Lesson lesson : c.getLessons()) {
			System.out.println(count++ + "  : " + lesson.toString());
		}
		System.out.println("""
				Выберите урок
				""");
		int choice = in.nextInt();
		return c.getLessons().stream().toList().get(choice - 1);

	}
	private Lesson createLesson(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				Выберите учителя
				""");
		Teacher teacher = chooseTeacher(getTeachers().stream().toList());
		System.out.println("""
				Выберите тип лекции:
				1 : Лекция
				2 : Практика
				3 : Лабораторная работа
				""");
		LessonType lessonType = switch (in.nextInt()){
			case 1 -> LessonType.LECTURE;
			case 2 -> LessonType.PRACTICE;
			case 3 -> LessonType.LAB;
			default -> throw new IllegalStateException("Unexpected value: ");
		};
		System.out.println("""
				1 : Понедельник
				2 : Вторник
				3 : Среда
				4 : Четверг
				5 : Пятница
				6 : Суббота
				7 : Воскресенье
				""");
		DaysWeek daysWeek = switch (in.nextInt()){
			case 1 -> DaysWeek.MONDAY;
			case 2 -> DaysWeek.TUESDAY;
			case 3 -> DaysWeek.WEDNESDAY;
			case 4 -> DaysWeek.THURSDAY;
			case 5 -> DaysWeek.FRIDAY;
			case 6 -> DaysWeek.SATURDAY;
			case 7 -> DaysWeek.SUNDAY;
			default -> throw new IllegalStateException("Unexpected value: ");
		};
		System.out.println("""
				Введите время начала урока
				""");
		int startTime = in.nextInt();
		System.out.println("""
				Введите время конца урока
				""");
		int endTime = in.nextInt();
		System.out.println("""
				Введите максимальное кол-во студентов
				""");
		int maxStudents = in.nextInt();
		return new Lesson(teacher, lessonType, daysWeek, startTime, endTime, maxStudents);
	}

	private Course createCourse(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				Введите название курса
				""");
		String title = in.nextLine();
		System.out.println("""
				Введите описание курса
				""");
		String description = in.nextLine();
		System.out.println("""
				Введите код курса
				""");
		String code = in.nextLine();
		System.out.println("""
				Введите количество лекции
				""");
		int lectures = in.nextInt();
		System.out.println("""
				Введите количество практик
				""");
		int practices = in.nextInt();
		System.out.println("""
				Введите кол-во лабораторных работ
				""");
		int labs = in.nextInt();
		System.out.println("""
				Введите количество кредитов
				""");
		int credits = in.nextInt();
		System.out.println("""
				Введите год
				""");
		int year = in.nextInt();
		System.out.println("""
				Выберите семестр
				1 : Осенний
				2 : Весенний
				""");
		int choice = in.nextInt();
		Semester semester = switch (choice){
			case 1 -> Semester.FALL;
			case 2 -> Semester.SPRING;
			default -> null;
		};
		Course course = new Course(title, description, code, lectures, practices, labs, credits, year, semester);
		Data.getInstance().addCourse(course);
		return course;
	}
    /**
     * Views information about teachers associated with a partice course.
     * @param c - course for which information is to be retrieved.
     * @return Representation of teacher information associated with the given course.
     */
	// Надо проверить раб	отоспособность, а то не уверен и если нет то сделать как в viewInfoAboutStudents
	public String viewInfoAboutTeachers(Course c) {
		return c.getLessons().stream().map(lesson -> lesson.getTeacher().toString()).collect(Collectors.toSet()).toString();
	}
	/**
	 * Views information about a teacher.
	 * @param t - teacher for whom information is to be viewed.
	 * @return The content information about the teacher.
	 */
	@Override
	public String viewInfoAboutTeacher(Teacher t) {
		return t.toString();
	}
	/**
	 * Views information about a student.
	 * @param s - student for whom information is to be viewed.
	 * @return The content information about the student.
	 */
	@Override
	public String viewInfoAboutStudent(Student s) {
		return s.toString();
	}
	/**
	 * Checks if this manager is equal to another object.
	 * @param o The object to compare.
	 * @return {@code true} if the objects are equal, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Manager manager = (Manager) o;
		return type == manager.type;
	}
	/**
	 * Hash code for this manager.
	 * @return The hash code for this manager.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), type);
	}
	/**
	 * Converts this manager to a string representation.
	 * @return Representation of this manager.
	 */
	@Override
	public String toString() {
		return super.toString() + "\nManager type: " + type;
	}
}

