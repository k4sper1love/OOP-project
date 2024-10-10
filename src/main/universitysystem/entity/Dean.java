package universitysystem.entity;

import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.research.Researcher;
import universitysystem.utills.*;
import universitysystem.courses.Faculty;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code Dean} class presents a dean within the university system, extending the functionality of the {@code Employee} class.
 * A dean is responsible for managing employee requests, handling complaints, and overseeing a specific faculty.
 * @author 
 * @version 1.0
 */
public class Dean extends Employee implements Serializable {
	private Faculty faculty;
	private static Vector<Complaint> complaints;
	private static Vector<EmployeeRequest> employeeRequests;

	static {
		complaints = new Vector<>();
		employeeRequests = new Vector<>();
	}

	{
		faculty = null;
	}

	/**
	 * Default constructor
	 */
	public Dean() {
		super();
	}

	/**
	 * Constructor for {@code Dean} with specified login and password.
	 *
	 * @param login    The login for the dean.
	 * @param password The password for the dean.
	 */
	public Dean(String login, String password) {
		super(login, password);
	}

	/**
	 * Constructor for {@code Dean} with specified login, password, salary, and faculty.
	 *
	 * @param login    The login for the dean.
	 * @param password The password for the dean.
	 * @param salary   The salary for the dean.
	 * @param faculty  The faculty overseen by the dean.
	 */
	public Dean(String login, String password, double salary, Faculty faculty) {
		super(login, password, salary);
		this.faculty = faculty;
	}

	/**
	 * Constructor for  {@code Dean} with specified login, password, and email.
	 *
	 * @param login    The login for the dean.
	 * @param password The password for the dean.
	 * @param email    The email for the dean.
	 */
	public Dean(String login, String password, String email) {
		super(login, password, email);
	}

	/**
	 * Constructor for {@code Dean} with specified login, password, email, salary, and faculty.
	 *
	 * @param login    The login for the dean.
	 * @param password The password for the dean.
	 * @param email    The email for the dean.
	 * @param salary   The salary for the dean.
	 * @param faculty  The faculty overseen by the dean.
	 */
	public Dean(String login, String password, String email, double salary, Faculty faculty) {
		super(login, password, email, salary);
		this.faculty = faculty;
	}

	/**
	 * Constructor for  {@code Dean} with specified login, password, email, firstname, lastname,
	 * middlename, and birth date.
	 *
	 * @param login      The login for the dean.
	 * @param password   The password for the dean.
	 * @param email      The email for the dean.
	 * @param firstname  The firstname for the dean.
	 * @param lastname   The lastname for the dean.
	 * @param middlename The middlename for the dean.
	 * @param birthDate  The birth date for the dean.
	 */
	public Dean(String login, String password, String email, String firstname,
				String lastname, String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}

	/**
	 * Constructor for creating a {@code Dean} with specified login, password, email, firstname, lastname,
	 * middlename, birth date, salary, and faculty.
	 *
	 * @param login      The login for the dean.
	 * @param password   The password for the dean.
	 * @param email      The email for the dean.
	 * @param firstname  The firstname for the dean.
	 * @param lastname   The lastname for the dean.
	 * @param middlename The middlename for the dean.
	 * @param birthDate  The birth date for the dean.
	 * @param salary     The salary for the dean.
	 * @param faculty    The faculty overseen by the dean.
	 */
	public Dean(String login, String password, String email, String firstname,
				String lastname, String middlename, Date birthDate, double salary, Faculty faculty) {
		super(login, password, email, firstname, lastname, middlename, birthDate, salary);
		this.faculty = faculty;
	}

	/**
	 * Generates a unique identifier for the dean based on the current year and the system's ID counter.
	 *
	 * @return A unique identifier for the dean.
	 */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "D" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}

	/**
	 * Gets the list of employee requests.
	 *
	 * @return The list of employee requests.
	 */
	public static Vector<EmployeeRequest> getEmployeeRequests() {
		return Data.getInstance().getEmployeeRequests();
	}

	/**
	 * Adds an employee request to the list.
	 *
	 * @param e The employee request to add.
	 * @return {@code true} if the addition is successful, {@code false} otherwise.
	 */
	public static boolean addEmployeeRequest(EmployeeRequest e) {

		return Data.getInstance().getEmployeeRequests().add(e);
	}

	/**
	 * Removes an employee request from the list.
	 *
	 * @param e The employee request to remove.
	 * @return {@code true} if the removal is successful, {@code false} otherwise.
	 */
	public static boolean removeEmployeeRequest(EmployeeRequest e) {
		return Data.getInstance().getEmployeeRequests().remove(e);
	}

	/**
	 * Gets the list of complaints.
	 *
	 * @return The vector of complaints.
	 */
	public static Vector<Complaint> getComplaints() {
		return Data.getInstance().getComplaints();
	}

	/**
	 * Adds a complaint to the vector.
	 *
	 * @param c The complaint to add.
	 * @return {@code true} if the addition is successful, {@code false} otherwise.
	 */
	public static boolean addComplaint(Complaint c) {

		return Data.getInstance().getComplaints().add(c);
	}

	/**
	 * Removes a complaint from the vector.
	 *
	 * @param c The complaint to remove.
	 * @return {@code true} if the removal is successful, {@code false} otherwise.
	 */
	public static boolean removeComplaint(Complaint c) {
		return Data.getInstance().getComplaints().remove(c);
	}

	/**
	 * Gets the faculty overseen by the dean.
	 *
	 * @return The faculty overseen by the dean.
	 */
	public Faculty getFaculty() {
		return faculty;
	}

	/**
	 * Sets the faculty overseen by the dean.
	 *
	 * @param faculty The faculty to set.
	 */
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	/**
	 * Views the complaints related to the dean's faculty.
	 *
	 * @return A vector containing the complaints related to the dean's faculty.
	 */
	public Vector<Complaint> viewComplaints() {
		return Data.getInstance().getComplaints().stream().filter(n -> n.getStudent().getFaculty().equals(faculty))
				.collect(Collectors.toCollection(Vector::new));
	}

	public void viewComplaintsRun() {
		System.out.println("Все жалобы:");
		for (int i = 0; i < Data.getInstance().getComplaints().size(); i++) {
			System.out.println((i + 1) + ") " + Data.getInstance().getComplaints().get(i));
		}
		Scanner in = new Scanner(System.in);
		System.out.println("Выберите номер жалобы для управления (или 0 для выхода):");
		int choice = in.nextInt();

		if (choice >= 1 && choice <= Data.getInstance().getComplaints().size()) {
			Complaint selectedRequest = Data.getInstance().getComplaints().get(choice - 1);
			System.out.println("Выбрана жалоба: " + selectedRequest);
			System.out.println("Выберите действие: \n1) Рассмотреть \n2) Отклонить \n3) Вернуться назад");
			int action = in.nextInt();
			if (action == 1) {
				warn(selectedRequest.getStudent());
				Data.getInstance().getLogs().add(new Log(getId() + " принял жалобу на студента от " + selectedRequest.getSender().getId()));
				Data.getInstance().getLogs().add(new Log(getId() + " вынес выговор " + selectedRequest.getStudent().getId()));
				selectedRequest.getSender().addNotification(new Notification("Жалоба на студента", "Ваша жалоба рассмотрена. Студент наказан!"));
				selectedRequest.getStudent().addNotification(new Notification("Выговор", "Вы получили выговор от учителя"));
				Data.getInstance().getComplaints().remove(selectedRequest);
				return;
			} else if (action == 2) {
				Data.getInstance().getLogs().add(new Log(getId() + " отклонил жалобу на студента от " + selectedRequest.getSender().getId()));
				selectedRequest.getSender().addNotification(new Notification("Жалоба на студента", "Ваша жалоба отклонена. Недостаточно аргументов"));
				Data.getInstance().getComplaints().remove(selectedRequest);
				return;
			} else if(action == 3){
				return;
			}
		} else if (choice == 0) {
			System.out.println("Возврат в главное меню.");
		}
	}
    /**
     * Applies a complaint, warns the student, and removes the complaint from the vector.
     * @param c The complaint to apply.
     * @return {@code true} if the application is successful, {@code false} otherwise.
     */
	public boolean applyComplaint(Complaint c) {
		if(c.getStudent().getFaculty() != faculty) return false;
		if(complaints.stream().noneMatch(n -> n.equals(c))) return false;
		removeComplaint(c);
		warn(c.getStudent());
		return true;
	}
    /**
     * Rejects a complaint and removes it from the list.
     * @param c The complaint to reject.
     * @return {@code true} if the rejection is successful, {@code false} otherwise.
     */
	public boolean rejectComplaint(Complaint c){
		if(c.getStudent().getFaculty() != faculty) return false;
		if(complaints.stream().noneMatch(n -> n.equals(c))) return false;
		removeComplaint(c);
		return true;
	}
    /**
     * Signs an employee request, removes it from the list, and adds it to the manager's list.
     * @param r The employee request to sign.
     * @return {@code true} if the signing is successful, {@code false} otherwise.
     */
	public boolean signRequest(EmployeeRequest r) {
		if(Data.getInstance().getEmployeeRequests().stream().noneMatch(n -> n.equals(r))) return false;
		r.setSigned(true);
		return true;
	}
    /**
     * Rejects an employee request and removes it from the list.
     * @param r The employee request to reject.
     * @return {@code true} if the rejection is successful, {@code false} otherwise.
     */
	public boolean rejectRequest(EmployeeRequest r) {
		if(Data.getInstance().getEmployeeRequests().stream().noneMatch(n -> n.equals(r))) return false;
		r.setStatus(RequestStatus.REFUSED);
		Data.getInstance().getEmployeeRequests().remove(r);
		return true;
	}
    /**
     * Warns a student by adding a warning to their record.
     * @param s The student to warn.
     */
	public void warn(Student s) {
		s.addWarning();
	}
    /**
     * Checks if this dean is equal to another object.
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Dean dean = (Dean) o;
		return faculty == dean.faculty;
	}
    /**
     * Generates a hash code for this dean.
     * @return The hash code for this dean.
     */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), faculty);
	}
    /**
     * Returns a string representation of the dean.
     * @return A string representation of the dean.
     */
	@Override
	public String toString() {
		return getFirstname() + " " + getLastname() + "\n" +
			   "ID: " + super.getId() + ". Dean " + faculty + " salary: " + getSalary();
	}

    /**
     * Method for performing operations in the dean's personal cabinet.
     */
    public void run() throws IOException {
		Scanner in = new Scanner(System.in);
        System.out.println("Вход успешен!.Вы вошли в личный кабинет декана.");
        menu: while (true) {
            System.out.println("Выберите действие: \n1) Просмотр жалоб \n2) Управление запросами " +
					"\n3) Новости \n4) Журналы \n5) Отправить tech report \n6) Выйти из личного кабинета");
			if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))) {
				System.out.println("7) Перейти в RESEARCHER");
			}
            int choice = in.nextInt();
			if (choice == 1){
				viewComplaintsRun();
			} else if(choice == 2){
				manageRequests();
			} else if(choice == 3){
				viewNewsRun();
			} else if(choice == 4){
				viewJournalRun();
			} else if(choice == 5) {
				sendTechReportRun();
			} else if (choice == 7 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
				goToResearcher();
			} else {
				this.logout();
				break;
			}
        }
    }

    /**
     * Method for managing requests in the dean's personal cabinet.
     */
    private void manageRequests() {
        System.out.println("Управление запросами декана.");
        System.out.println("Все запросы:");
		Vector<EmployeeRequest> actualRequests = Data.getInstance().getEmployeeRequests().stream().filter(n -> !n.getSigned()).collect(Collectors.toCollection(Vector::new));
        for (int i = 0; i < actualRequests.size(); i++) {
            System.out.println((i + 1) + ") " + actualRequests.get(i));
        }
		Scanner in = new Scanner(System.in);
        System.out.println("Выберите номер запроса для управления (или 0 для выхода):");
        int choice = in.nextInt();

        if (choice >= 1 && choice <= actualRequests.size()) {
            EmployeeRequest selectedRequest = actualRequests.get(choice - 1);
            System.out.println("Выбран ваш запрос: " + selectedRequest);
            System.out.println("Выберите действие: \n1) Подписать запрос \n2) Отклонить запрос \n3) Вернуться назад");
            int action = in.nextInt();
            switch (action) {
                case 1:
                    if (signRequest(selectedRequest)) {
						selectedRequest.getSender().addNotification(new Notification("Запрос", "Ваш запрос был подписан!"));
						Data.getInstance().getLogs().add(new Log(getId() + " подписал запрос работника " + selectedRequest.getSender().getId()));
                        System.out.println("Запрос подписан успешно.");
                    } else {
                        System.out.println("Ошибка.");
                    }
                    break;
                case 2:
                    if (rejectRequest(selectedRequest)) {
						selectedRequest.getSender().addNotification(new Notification("Запрос", "Ваш запрос был отклонен деканатом."));
						Data.getInstance().getLogs().add(new Log(getId() + " отклонил запрос работника " + selectedRequest.getSender().getId()));
                        System.out.println("Запрос отклонен успешно.");
                    } else {
                        System.out.println("Ошибка.");
                    }
                    break;
				case 3:
					return;
                default:
                    System.out.println("Неверный выбор.Возврат к списку запросов.");
            }
        } else if (choice == 0) {
            System.out.println("Возврат в главное меню.");
        } else {
            System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    /**
     * Method for logging out of the system.
     * @throws IOException Thrown in case of input/output errors.
     */
	
}



