package universitysystem.entity;


import universitysystem.courses.Course;
import universitysystem.courses.Faculty;
import universitysystem.courses.Lesson;
import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.interfaces.CanResearch;
import universitysystem.research.ResearchProject;
import universitysystem.research.Researcher;
import universitysystem.utills.RequestStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Within the university system, The class {@code GraduateStudent} presents itself as a graduate student.
 * Extends the functionality of the class {@code Student} and implements the {@code CanResearch} interface.
 * A graduate student may have a supervisor, a graduate type, and may be associated with a research project.
 */
public class GraduateStudent extends Student implements CanResearch, Serializable
{
	private Researcher supervisor;
	private GraduateType graduateType;
	private ResearchProject diplomaProject;

	{
		supervisor = null;
		graduateType = null;
		diplomaProject = null;
	}
    /**
     * Default constructor.
     */
	public GraduateStudent(){
		super();
	}
    /**
     * Constructor for {@code GraduateStudent} with specified login and password.
     *
     * @param login The login for the graduate student.
     * @param password The password for the graduate student.
     */
	public GraduateStudent(String login, String password) {
		super(login, password);
	}
    /**
     * Constructor for {@code GraduateStudent} with specified login, password, and graduate type.
     *
     * @param login The login for the graduate student.
     * @param password The password for the graduate student.
     * @param graduateType The graduate type for the graduate student.
     */
	public GraduateStudent(String login, String password, GraduateType graduateType) {
		this(login, password);
		this.graduateType = graduateType;
	}
	/**
	 * Constructor for creating a {@code GraduateStudent} with specified login, password, and email.
	 *
	 * @param login    The login for the graduate student.
	 * @param password The password for the graduate student.
	 * @param email    The email for the graduate student.
	 */
	public GraduateStudent(String login, String password, String email) {
		super(login, password, email);
	}
	/**
	 * Constructor for creating a {@code GraduateStudent} with specified login, password, email, faculty, year of study, and graduate type.
	 *
	 * @param login        The login for the graduate student.
	 * @param password     The password for the graduate student.
	 * @param email        The email for the graduate student.
	 * @param faculty      The faculty to which the graduate student belongs.
	 * @param yearOfStudy  The year of study for the graduate student.
	 * @param graduateType The graduate type for the graduate student.
	 */
	public GraduateStudent(String login, String password, String email, Faculty faculty, int yearOfStudy, GraduateType graduateType) {
		super(login, password, email, faculty, yearOfStudy);
		this.graduateType = graduateType;
	}
    /**
     * Constructor for creating a {@code GraduateStudent} with specified login, password, email, firstname, lastname,
     * middlename, and birth date.
     *
     * @param login      The login for the graduate student.
     * @param password   The password for the graduate student.
     * @param email      The email for the graduate student.
     * @param firstname  The firstname for the graduate student.
     * @param lastname   The lastname for the graduate student.
     * @param middlename The middlename for the graduate student.
     * @param birthDate  The birth date for the graduate student.
     */
	public GraduateStudent(String login, String password, String email, String firstname, String lastname,
						   String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}
    /**
     * Constructor for creating a {@code GraduateStudent} with specified login, password, email, firstname, lastname,
     * middlename, birth date, faculty, year of study, and graduate type.
     *
     * @param login        The login for the graduate student.
     * @param password     The password for the graduate student.
     * @param email        The email for the graduate student.
     * @param firstname    The firstname for the graduate student.
     * @param lastname     The lastname for the graduate student.
     * @param middlename   The middlename for the graduate student.
     * @param birthDate    The birth date for the graduate student.
     * @param faculty      The faculty to which the graduate student belongs.
     * @param yearOfStudy  The year of study for the graduate student.
     * @param graduateType The graduate type for the graduate student.
     */
	
	public GraduateStudent(String login, String password, String email, String firstname, String lastname,
						   String middlename, Date birthDate, Faculty faculty, int yearOfStudy, GraduateType graduateType) {
		super(login, password, email, firstname, lastname, middlename, birthDate, faculty, yearOfStudy);
		this.graduateType = graduateType;
	}

    /**
     * Generates a unique identifier for the graduate student based on the current year and the system's ID counter.
     * @return The generated unique identifier for the graduate student.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "G" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Gets the research project associated with the graduate student.
     * @return The research project associated with the graduate student.
     */
	public ResearchProject getProject() {
		return diplomaProject;
	}
    /**
     * Creates a research project for the graduate student.
     * @param r The research project to be associated with the graduate student.
     * @return {@code true} if the project is created successfully, {@code false} if the student already has a project.
     */
	public boolean createProject(ResearchProject r) {
		if(diplomaProject != null) return false;
		diplomaProject = r;
		r.addParticipant(this);
		return true;
	}
    /**
     * Joins the graduate student to a research project.
     * @param r The research project to join.
     * @return {@code true} if the student joins the project successfully, {@code false} if the student already has a project.
     */
	public boolean joinToProject(ResearchProject r) {
		if(diplomaProject != null) return false;
		diplomaProject = r;
		r.addParticipant(this);
		return true;
	}
    /**
     * Gets the supervisor of the graduate student.
     * @return The supervisor of the graduate student.
     */
	public Researcher getSupervisor() {
		return supervisor;
	}
    /**
     * Sets the supervisor for the graduate student.
     * @param r The supervisor to set for the graduate student.
     */
	public void setSupervisor(Researcher r) {
		supervisor = r;
	}
    /**
     * Gets the type of the graduate student.
     * @return The type of the graduate student.
     */
	public GraduateType getType() {
		return graduateType;
	}
    /**
     * Sets the type for the graduate student.
     * @param t The type to set for the graduate student.
     */
	public void setType(GraduateType t) {
		graduateType = t;
	}

    /**
     * {@code equals} method to compare two {@code GraduateStudent} objects for equality.
     * @param o The object to compare with the graduate student.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		GraduateStudent that = (GraduateStudent) o;
		return Objects.equals(supervisor, that.supervisor) && graduateType == that.graduateType && Objects.equals(diplomaProject, that.diplomaProject);
	}
    /**
     * Overrides the {@code hashCode} method to generate a hash code for the {@code GraduateStudent} object.
     * @return Hash code for the graduate student.
     */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), supervisor, graduateType, diplomaProject);
	}
    /**
     * Overrides the {@code toString} method to provide a string representation of the {@code GraduateStudent}.
     * @return A string representation of the graduate student.
     */
	@Override
	public String toString() {
		return super.toString() + "\nGraduate type: " + graduateType + ", supervisor: " + supervisor +
				"\nDiploma project: " + diplomaProject;
	}
	/**
	 * Executes the main functionality for a Graduate Student's personal cabinet.
	 * This method displays a menu of options for the Graduate Student and performs the selected action.
	 * The available options include viewing personal data, news, schedule, courses, organizations, journal,
	 * project-related actions, sending tech reports, and the option to switch to the Researcher role if applicable.
	 */
	public void run() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Сейчас вы в личном кабинете GRADUATE STUDENT. Год: " + Data.getInstance().getYear() + ", семестр: " + Data.getInstance().getSemester());
			if (Course.getRegistrationStatus()) {
				System.out.println("Регистрация на дисциплины открыта!");
				if (getCurrentCourses().isEmpty()
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
						"\n5) Организации \n6) Журнал \n7) Project \n8) Отправить tech report \n9) Exit");
				if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					System.out.println("\n10) Перейти в RESEARCHER");
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
				} else if (choice == 7) {
					projectRun();
				} else if (choice == 9) {
					this.logout();
					break menu;
				} else if(choice == 10 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					goToResearcher();
				} else if(choice == 8){
					sendTechReportRun();
				}
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
	/**
	 * Manages project-related actions for a Graduate Student.
	 * This method provides options for the Graduate Student to view, create, or join a research project.
	 * It uses the ResearchProject class for project-related operations.
	 */
	protected void projectRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		projecting: while(true){
			System.out.println("1) Посмотреть свой проект \n2) Создать проект \n3) Вступить в проект \n4) Вернуться назад");
			choice = in.nextInt();
			if(choice == 1){
				if(getProject() == null){
					System.out.println("У вас еще нет проекта");
				} else {
					System.out.println("Ваш проект: \n" + getProject());
				}
			} else if (choice == 2){
				if(getProject() != null){
					System.out.println("У вас уже есть проект!");
				} else {
					System.out.println("Введите название нового проекта: ");
					in.nextLine();
					String text = in.nextLine();
					this.createProject(new ResearchProject(text));
					Data.getInstance().getLogs().add(new Log(getId() + " создал научный проект " + text));
					System.out.println("Вы успешно создали проект!");
				}
			} else if (choice == 3){
				if(getProject() != null){
					System.out.println("У вас уже есть проект!");
				} else {
					System.out.println("Выберите проект из существующих: ");
					int cnt = 0;
					Vector<ResearchProject> actualProjects = new Vector<>(Data.getInstance().getResearchProjects());
					for (ResearchProject r: actualProjects){
						System.out.println(++cnt + ") " + r);
					}
					System.out.println(++cnt + ") Вернуться назад");
					choice = in.nextInt();
					if(choice < 1 || choice >= cnt) continue projecting;
					ResearchProject actualProject = actualProjects.get(choice - 1);
					joinToProject(actualProject);
					Data.getInstance().getLogs().add(new Log(getId() + " вступил в проект " + actualProject.getTopic()));
					System.out.println("Вы успешно вступили в проект!");
				}
			} else if(choice == 4){
				break;
			}
			System.out.println("1) Вернуться назад \n2) Exit");
			choice = in.nextInt();
			if(choice == 1) continue projecting;
			if(choice == 2) break;
		}
	}
}

