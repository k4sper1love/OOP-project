package universitysystem.entity;

import universitysystem.courses.Faculty;
import universitysystem.data.Data;
import universitysystem.data.UserFactory;
import universitysystem.interfaces.CanCreate;
import universitysystem.data.Log;
import universitysystem.interfaces.CanLogin;
import universitysystem.research.Researcher;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO Adil на тебе висит
public class Admin implements CanLogin, Serializable
{
	private String login;
	private String password;

	public Admin(){
		super();
	}

	public Admin(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public User addUser(String login, String password, UsersType type, boolean isResearcher) {
		UserFactory userFactory = new UserFactory();
		return (User) userFactory.create(login, password, type, isResearcher);
	}

	private void createUser(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				Напишите логин
				""");
		String login = in.next();
		System.out.println("""
				Напишите пароль
				""");
		String password = in.next();
		System.out.println("""
				Выберите тип пользователя
				""");
		UsersType usersType = determineType();
		System.out.println("""
				Исследователь ли он/она
				0 : Нет
				1 : Да
				""");
		boolean isResearcher = switch (in.nextInt()){
			case 0 -> false;
			case 1 -> true;
			default -> throw new IllegalStateException("Unexpected value: ");
		};
		User user = addUser(login, password, usersType, isResearcher);
		Data.getInstance().getLogs().add(new Log("добавлен user: " + usersType));
		System.out.println("""
				Хотите добавить специфичные параметры?
				0 : Нет
				1 : Да
				""");
		switch (in.nextInt()) {
			case 1 -> {
				System.out.println("""
						Впишите имя
						""");
				String name = in.next();
				System.out.println("""
						Впишите фамилию
						""");
				String lastname = in.next();
				System.out.println("""
						Впишите отчество
						""");
				String middlename = in.next();
				user.setFirstname(name);
				user.setLastname(lastname);
				user.setMiddlename(middlename);
				switch (usersType){
					case STUDENT -> {
						Student student = (Student) user;
						student.setYearOfStudy(determineYear());
						student.setFaculty(determineFaculty());
					}
					case DEAN -> {
						Dean dean = (Dean) user;
						dean.setFaculty(determineFaculty());
						dean.setSalary(determineSalary());
					}
					case MANAGER -> {
						System.out.println("""
								Выберите тип менеджера
								1 : Офис регистратора
								2 : Деканат
								""");
						ManagerType managerType = switch (in.nextInt()){
							case 1 -> ManagerType.OR;
							case 2 -> ManagerType.DEANS_OFFICE;
							default -> ManagerType.OR;
						};
						Manager manager = (Manager) user;
						manager.setType(managerType);
						manager.setSalary(determineSalary());
					}
					case TEACHER -> {
						Teacher teacher = (Teacher) user;
						teacher.setFaculty(determineFaculty());
						teacher.setSalary(determineSalary());
						teacher.setType(determineTeacher());
					}
					case EMPLOYEE -> {
						Employee employee = (Employee) user;
						employee.setSalary(determineSalary());
					}
					case TECH_SUPPORT -> {
						TechSupportSpecialist specialist = (TechSupportSpecialist) user;
						specialist.setSalary(determineSalary());
					}
					case GRADUATE_STUDENT -> {
						GraduateStudent graduateStudent = (GraduateStudent) user;
						graduateStudent.setType(determineGraduate());
						graduateStudent.setYearOfStudy(determineYear());
						graduateStudent.setFaculty(determineFaculty());
					}
				}

			}
			case 0 -> {
            }
		}
	}
	
	public boolean removeUser(CanCreate c) {
		if (c instanceof Researcher){
			Data.getInstance().removeResearcher((Researcher) c);
			Data.getInstance().getLogs().add(new Log("удален researcher"));
		}
		if (c instanceof Dean){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeDean((Dean) c);
		}
		if (c instanceof Student){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeStudent((Student) c);
		}
		if (c instanceof Manager){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeManager((Manager) c);
		}
		if (c instanceof Teacher){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeTeacher((Teacher) c);
		}
		if (c instanceof TechSupportSpecialist){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeTechSpecialist((TechSupportSpecialist) c);
		}
		if (c instanceof Employee){
			User forLogs = (User) c;
			Data.getInstance().getLogs().add(new Log("удален user: " + forLogs.getId()));
			return Data.getInstance().removeEmployee((Employee) c);
		}
		return false;
	}

	public boolean setUserFirstname(CanCreate c, String firstname) {
		User forLogs = (User) c;
		Data.getInstance().getLogs().add(new Log("изменено имя userа: " + forLogs.getId()));
		((User) c).setFirstname(firstname);
		return true;
	}
	
	public boolean setUserLastname(CanCreate c, String lastname) {
		User forLogs = (User) c;
		Data.getInstance().getLogs().add(new Log("изменена фамилия userа: " + forLogs.getId()));
		((User) c).setLastname(lastname);
		return true;
	}

	public boolean setUserMiddlename(CanCreate c, String middlename) {
		User forLogs = (User) c;
		Data.getInstance().getLogs().add(new Log("изменено отчество userа: " + forLogs.getId()));
		((User) c).setMiddlename(middlename);
		return true;
	}
	
	public boolean setUserPassword(CanCreate c, String password) {
		User forLogs = (User) c;
		Data.getInstance().getLogs().add(new Log("изменен пароль userа: " + forLogs.getId()));
		((User) c).setPassword(password);
		return false;	
	}
	
	public Vector<Log> getLogs() {
		return Data.getInstance().getLogs();
	}

	public User findUserById(String id){
		return getStudents().stream().filter(student -> student.getId().equals(id)).limit(1).findFirst().orElse(null);
	}
	public List<User> findUsers(String login){
		Vector<User> users = new Vector<>();
		users.addAll(Data.getInstance().getEmployees());
		users.addAll(Data.getInstance().getStudents());
		users.addAll(Data.getInstance().getManagers());
		users.addAll(Data.getInstance().getDeans());
		users.addAll(Data.getInstance().getTeachers());
		users.addAll(Data.getInstance().getTechSpecialists());
		return users.stream().filter(user -> user.getLogin().equals(login)).toList();
	}
	public List<User> findUsers(String firstname, String lastname){
		Stream<User> userStream = Stream.of(getStudents().stream(),
						getEmployees().stream(),
						getDeans().stream(),
						getManagers().stream(),
						getTeachers().stream(),
						getTechSupportSpecialists().stream())
				.flatMap(stream -> stream);
		return userStream.filter(user -> user.getFirstname() != null && user.getFirstname().equals(firstname)
					&& user.getLastname() != null && user.getLastname().equals(lastname)).toList();
	}

	public HashSet<Student> getStudents(){
		return Data.getInstance().getStudents();
	}
	public List<Student> getStudents(Comparator<Student> comparator){
		return getStudents().stream().sorted(comparator).toList();
	}
	public List<Student> getStudentByYear(int year){
		return getStudents().stream().filter(student -> student.getYearOfStudy() == year).toList();
	}
	public List<Student> getStudentByFaculty(Faculty faculty){
		return getStudents().stream().filter(student -> student.getFaculty() != null && student.getFaculty().equals(faculty)).toList();
	}
	public List<Student> getStudentByOrganization(Organization organization){
		return getStudents().stream().filter(student -> student.getOrganization() != null && student.getOrganization().equals(organization)).toList();
	}
	public HashSet<Employee> getEmployees(){
		return Data.getInstance().getEmployees();
	}
	public List<Employee> getEmployees(Comparator<Employee> comparator) {
		return getEmployees().stream().sorted(comparator).toList();
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
				.filter(teacher -> teacher.getType() != null && teacher.getType().equals(teacherType))
				.toList();
	}
	public HashSet<Manager> getManagers(){
		return Data.getInstance().getManagers();
	}
	public List<Manager> getManagers(ManagerType type){
		return getManagers().stream().filter(manager -> manager.getType() != null && manager.getType().equals(type)).toList();
	}
	public HashSet<Dean> getDeans(){
		return Data.getInstance().getDeans();
	}
	public Dean getDean(Faculty faculty){
		return Data.getInstance().getDeans().stream().filter(dean -> dean.getFaculty() != null && dean.getFaculty().equals(faculty)).limit(1).findFirst().orElse(null);
	}
	public HashSet<TechSupportSpecialist> getTechSupportSpecialists(){
		return Data.getInstance().getTechSpecialists();
	}
	public HashSet<Researcher> getResearchers(){
		return Data.getInstance().getResearchers();
	}

	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void login(){
		Data.getInstance().getLogs().add(new Log( "Выполнен вход в admin-панель"));
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the system, Admin!");
		run();
	}

	public void logout(){
		Data.getInstance().getLogs().add(new Log( "Выход из admin-панели"));
		System.out.println("Выполняем выход...");
		try {
			save();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void save() throws IOException{
		Data.write();
	}

	public void run() {
		Scanner in = new Scanner(System.in);

		menu:
		while (true) {
			System.out.println("""
					Что вы хотите сделать:
					1 : Создать нового пользователя
					2 : Обновить пользователя
					3 : Просмотреть логи
					4 : Просмотреть всех студентов
					5 : Просмотреть всех работников
					6 : Просмотреть всех учителей
					7 : Просмотреть всех менеджеров
					8 : Просмотреть всех деканов
					9 : Просмотреть всех технических специалистов
					10: Просмотреть всех исследователей
					11: Выйти из системы
					""");
			int choice = in.nextInt();
			switch (choice) {
				case 1 -> {
					createUserLoop : while (true){
						createUser();
						System.out.println("""
								1 : Добавить ещё пользователей
								2 : Вернуться назад
								3 : Выйти
								""");
						choice = in.nextInt();
						switch (choice){
							case 1 -> {
                            }
							case 2 -> {
								break createUserLoop;
							}
							case 3 -> {
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
					}
				}
				case 2 -> {
					findUserLoop:
					while (true) {
						System.out.println("""
								Найти пользователя
								1 : По айди
								2 : По логину
								3 : По имени и фамилии
								4 : Вернуться назад
								5 : Выйти
								""");
						choice = in.nextInt();
						User user = null;
						switch (choice) {
							case 1 -> {
								findByIdLoop:
								while (true) {
									System.out.println("""
											Впишите айди (Пример: 22
											""");
									user = findUserById(in.next());
									System.out.println("Это он?\n" + user + "\n" +
													   """
                                                               0 : Нет
                                                               1 : Да
                                                               2 : Вернуться назад
                                                               3 : Вернуться в меню
                                                               4 : Выйти из системы
                                                               """);
									choice = in.nextInt();
									switch (choice) {
										case 0 -> System.out.println("""
												Попробуйте ещё раз
												""");
										case 1 -> {
											break findByIdLoop;
										}
										case 2 -> {
											continue findUserLoop;
										}
										case 3 -> {
											continue menu;
										}
										case 4 -> {
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
								}
							}
							case 2 -> {
								System.out.println("""
										Впишите логин
										""");
								user = chooseUser(findUsers(in.next()));
							}
							case 3 -> {
								System.out.println("""
										Впишите имя
										""");
								String name = in.next();
								System.out.println("""
										Впишите фамилию
										""");
								String lastname = in.next();
								user = chooseUser(findUsers(name, lastname));
							}
							case 4 -> {
								break findUserLoop;
							}
							case 5 -> {
								logout();
								break menu;
							}
						}
						changeUserLoop : while(true) {
							System.out.println("""
									1 : Обновить имя
									2 : Обновить фамилию
									3 : Обновить отчество
									4 : Изменить пароль
									5 : Удалить пользователя
									6 : Изменить другого пользователя
									7 : Вернуться в меню
									8 : Выйти
									""");
							choice = in.nextInt();
							switch (choice) {

								case 1 -> {
									in.nextLine();
									setUserFirstname(user, in.nextLine());
								}
								case 2 -> {
									in.nextLine();
									setUserLastname(user, in.nextLine());
								}
								case 3 -> {
									in.nextLine();
									setUserMiddlename(user, in.nextLine());
								}
								case 4 -> {
									in.nextLine();
									setUserPassword(user, in.nextLine());
								}
								case 5 -> {
									if (removeUser(user))
										System.out.println("""
												Пользователь удалён
												""");
									else
										System.out.println("""
												Не удалось удалить пользователя
												""");
								}
								case 6 -> {
									continue findUserLoop;
								}
								case 7 -> {
									continue menu;
								}
								case 8 -> {
									logout();
									break menu;
								}
							}
						}
					}
				}
				case 3 -> {
					System.out.println("""
							1 : Сначала старые
							2 : Сначала новые
							3 : Вернуться в меню
							4 : Выйти
							""");
					choice = in.nextInt();
					switch (choice){
						case 1 -> System.out.println(getLogs());
						case 2 -> System.out.println(getLogs().stream()
								.sorted(Comparator.comparing(Log::getId).reversed()).toList()
						);
						case 3 -> {
						}
						case 4 -> {
							logout();
							break menu;
						}
					}
				}
				case 4 -> {
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
								students = getStudentByYear(determineYear());
							}
							case 2 -> students = getStudentByFaculty(determineFaculty());
							case 3 -> {
								printOrganizations();
								choice = in.nextInt();
								students = getStudentByOrganization(Data.getInstance().getOrganizations().stream().toList().get(choice));
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
				case 5 -> {
					while (true) {
						Stream<Employee> allEmployees = Stream.of(
										getEmployees().stream(),
										getDeans().stream(),
										getManagers().stream(),
										getTeachers().stream(),
										getTechSupportSpecialists().stream())
								.flatMap(stream -> stream);
						System.out.println("""
								1 : По зарплате
								2 : По дате найма
								3 : Только персонал
								4 : Вернуться
								5 : Выход
								""");
						choice = in.nextInt();
						switch (choice) {
							case 1 -> {
								outputList(allEmployees.sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).toList());
							}
							case 2 -> {
								outputList(allEmployees.sorted(Comparator.comparing(Employee::getHireDate)).toList());
							}
							case 3 -> {
								System.out.println("""
										1 : По зарплате
										2 : По дате найма
										""");
								choice = in.nextInt();
								Comparator<Employee> comparator = switch (choice) {
									case 1 -> Comparator.comparing(Employee::getSalary).reversed();
									case 2 -> Comparator.comparing(Employee::getHireDate);
									default -> Comparator.comparing(Employee::getId);
								};
								outputList(getEmployees(comparator).stream().toList());
							}
							case 4 -> {
								 continue menu;
							}
							case 5 -> {
								logout();
								break menu;
							}
						}
					}
				}
				case 6 -> {
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
				case 7 -> {
					while (true) {
						System.out.println("""
								   1 : Просмотреть всех
								   2 : Просмотреть по типу
								   3 : Просмотреть по количеству курсов
								   4 : Вернуться
								   5 : Выход
								""");
						choice = in.nextInt();
						switch (choice) {
							case 1 -> outputList(getManagers().stream().toList());
							case 2 -> {
								System.out.println("""
										1 : Офис регистратора
										2 : Деканат
										""");
								choice = in.nextInt();
								ManagerType managerType = switch (choice) {
									case 1 -> ManagerType.OR;
									case 2 -> ManagerType.DEANS_OFFICE;
									default -> ManagerType.OR;
								};
								outputList(getManagers(managerType));
							}
							case 3 -> outputList(getTeachers(Comparator.comparing(teacher -> teacher.getCourses().size())));
							case 4 -> {
								continue menu;
							}
							case 5 -> {
								logout();
								break menu;
							}
						}
					}
				}
				case 8 -> {
					while (true) {
						System.out.println("""
								   1 : Просмотреть всех
								   2 : Просмотреть по факультету
								   3 : Вернуться
								   4 : Выйти
								""");
						choice = in.nextInt();
						switch (choice) {
							case 1 -> {
								System.out.println(getDeans().stream().map(Dean::toString).collect(Collectors.joining("\n")));
								continue menu;
							}
							case 2 -> {
								System.out.println(getDean(determineFaculty()));
								continue menu;
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
				case 9 -> {
					outputList(getTechSupportSpecialists().stream().sorted(Comparator.comparing(techSupportSpecialist -> techSupportSpecialist.getCompleteOrders().size())).toList());
				}
				case 10 -> {
					outputList(getResearchers().stream().toList());
				}
				case 11 -> {
					logout();
					break menu;
				}
			}
		}
	}

	private GraduateType determineGraduate(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
				1 : Магистрант
				2 : Докторант
				""");
		return switch (in.nextInt()){
			case 1 -> GraduateType.MASTER;
			case 2 -> GraduateType.PHD;
			default -> GraduateType.MASTER;
		};
	}

	private int determineYear(){
		Scanner in = new Scanner(System.in);
		System.out.println("""
								Выберите год обучения
								1 : 1 год
								2 : 2 год
								3 : 3 год
								4 : 4 год
								""");
		return switch (in.nextInt()){
			case 1 -> 1;
			case 2 -> 2;
			case 3 -> 3;
			case 4 -> 4;
			default -> 1;
		};
	}

	private UsersType determineType(){
		Scanner in = new Scanner(System.in);
		int choice;
		System.out.println("""
				1 : Student
				2 : Graduate Student
				3 : Employee
				4 : Manager
				5 : Dean
				6 : Teacher
				7 : Tech Support
				""");
		choice = in.nextInt();
		return switch (choice) {
			case 1 -> UsersType.STUDENT;
			case 2 -> UsersType.GRADUATE_STUDENT;
			case 3 -> UsersType.EMPLOYEE;
			case 4 -> UsersType.MANAGER;
			case 5 -> UsersType.DEAN;
			case 6 -> UsersType.TEACHER;
			case 7 -> UsersType.TECH_SUPPORT;
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		};
	}

	private int determineSalary() {
		Scanner in = new Scanner(System.in);
		System.out.println("""
				Впишите зарплату
				""");
		return in.nextInt();
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

	private <T> void outputList(List<T> list){
		System.out.println(list.stream().map(T::toString).collect(Collectors.joining("\n")));
	}

	private void printOrganizations(){
		int count = 1;
        for (Organization organization : Data.getInstance().getOrganizations()) {
            System.out.println(count++ + "  : " + organization.getName());
        }
		System.out.println("""
				Выберите организацию
				""");
	}

	private User chooseUser(List<User> users){
		Scanner in = new Scanner(System.in);
		int count = 1;
		for (User user : users) {
			System.out.println(count++ + "  : " + user.toString());
		}
		System.out.println("""
				Выберите пользователя
				Если его нет то введите 0
				""");
		int choice = in.nextInt();
		if (choice == 0) return null;
		return users.get(choice - 1);
	}

	public String toString() {
		return "Admin";
	}
	
}

