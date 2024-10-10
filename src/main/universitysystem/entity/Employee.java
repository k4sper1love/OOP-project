package universitysystem.entity;

import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Class {@code Employee} presentы an employee in a university system.
 * Extends the functionality of the {@code User} class.
 * An employee has a salary, hire date, and can exchange messages and requests with other employees.
 * @author 
 * @version 1.0
 */
public class Employee extends User implements Serializable {
	private double salary;
	private final Date hireDate;
	private Vector<Message> messages;

	{
		this.salary = 0;
		this.hireDate = new Date();
		messages = new Vector<>();
	}
    /**
     * Default constructor.
     */
	public Employee() {
		super();
	}
    /**
     * Constructor for {@code Employee} with specified login and password.
     * @param login    The login for the employee.
     * @param password The password for the employee.
     */
	public Employee(String login, String password) {
		super(login, password);
	}
    /**
     * Constructor {@code Employee} with specified login, password, and salary.
     * @param login    The login for the employee.
     * @param password The password for the employee.
     * @param salary   The salary for the employee.
     */
	public Employee(String login, String password, double salary) {
		this(login, password);
		this.salary = salary;
	}
    /**
     * Constructor {@code Employee} with specified login, password, and email.
     * @param login    The login for the employee.
     * @param password The password for the employee.
     * @param email    The email for the employee.
     */
	public Employee(String login, String password, String email) {
		super(login, password, email);
	}
    /**
     * Constructor {@code Employee} with specified login, password, email, and salary.
     * @param login    The login for the employee.
     * @param password The password for the employee.
     * @param email    The email for the employee.
     * @param salary   The salary for the employee.
     */
	public Employee(String login, String password, String email, double salary) {
		this(login, password, email);
		this.salary = salary;
	}
    /**
     * Constructor for {@code Employee} with specified login, password, email, firstname, lastname,
     * middlename, and birth date.
     * @param login      The login for the employee.
     * @param password   The password for the employee.
     * @param email      The email for the employee.
     * @param firstname  The firstname for the employee.
     * @param lastname   The lastname for the employee.
     * @param middlename The middlename for the employee.
     * @param birthDate  The birth date for the employee.
     */
	public Employee(String login, String password, String email, String firstname, String lastname,
					String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}
    /**
     * Constructor {@code Employee} with specified login, password, email, firstname, lastname,
     * middlename, birth date, and salary.
     * @param login      The login for the employee.
     * @param password   The password for the employee.
     * @param email      The email for the employee.
     * @param firstname  The firstname for the employee.
     * @param lastname   The lastname for the employee.
     * @param middlename The middlename for the employee.
     * @param birthDate  The birth date for the employee.
     * @param salary     The salary for the employee.
     */
	public Employee(String login, String password, String email, String firstname, String lastname,
					String middlename, Date birthDate, double salary) {
		this(login, password, email, firstname, lastname, middlename, birthDate);
		this.salary = salary;
	}
    /**
     * Generates Creates a unique identifier for an employee using the current year and the system identifier counter.
     * @return The generated unique identifier for the employee.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "E" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Sets the salary for the employee.
     * @param salary The salary to set for the employee.
     */
	public void setSalary(double salary) {
		this.salary = salary;
	}

    /**
     * Gets the salary of the employee.
     * @return The salary of the employee.
     */
	public double getSalary() {
		return salary;
	}
    /**
     * Gets the hire date of the employee.
     * @return The hire date of the employee.
     */
	public Date getHireDate() {
		return hireDate;
	}
    /**
     * Sends a message to another employee.
     * @param e The employee to send the message to.
     * @param m The message to send.
     * @return {@code true} if the message is sent successfully.
     */
	public boolean sendMessage(Employee e, Message m) {
		e.addMessage(m);
		return true;
	}
    /**
     * Adds a message to the employee's messages.
     * @param m The message to add.
     */
	public void addMessage(Message m) {
		messages.add(m);
	}
    /**
     * Removes a message from the employee's messages.
     * @param m The message to remove.
     * @return {@code true} if the message is removed successfully.
     */
	public boolean removeMessage(Message m){
		if(messages != null){
			return messages.remove(m);
		}
		return false;
	}
    /**
     * Gets the messages of the employee.
     * @return The messages of the employee.
     */
	public Vector<Message> getMessages() {
		return messages;
	}
    /**
     * Sends a request to another employee.
     * @param r The request to send.
     * @return {@code true} if the request is sent successfully.
     */
	public boolean sendRequest(EmployeeRequest r) {
		//добавлена реализация
		Data.getInstance().getLogs().add(new Log(getId() + " отправил employee request"));
		Dean.addEmployeeRequest(r);
		return true;
	}

    /**
     * Overrides the {@code equals} method to compare two {@code Employee} objects for equality.
     * @param o The object to compare with the employee.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Employee employee)) return false;
		if (!super.equals(o)) return false;

        return Double.compare(employee.salary, salary) == 0 && hireDate.equals(employee.hireDate);
	}
    /**
     * Overrides the {@code hashCode} method to generate a hash code for the {@code Employee}.
     * @return Hash code for the employee object.
     */
	@Override
	public int hashCode() {
		return Objects.hash(salary, hireDate);
	}
    /**
     * Overrides the {@code toString} method to provide a string representation of the {@code Employee}.
     * @return A string representation of the employee.
     */
	public String toString() {
		return super.toString() + "\nHire date: " + hireDate + ", salary: " + salary;
	}
	/**
	 * Executes the main functionality for an Employee's personal cabinet.
	 * This method displays a menu of options for the Employee and performs the selected action.
	 * The available options include viewing personal data, news, journal, sending employee requests,
	 * sending tech reports, and the option to switch to the Researcher role if applicable.
	 */
	public void run() throws IOException {
		Scanner in = new Scanner(System.in);
		try {
			menu: while (true) {
				System.out.println("Вы сейчас в личном кабинете Employee");
				System.out.println("Что вы хотите сделать? \n1) Личные данные \n2) Новости \n3) Журнал" +
						"\n4) Отправить запрос менеджеру \n5) Отправить tech report \n6) Exit");
				if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))) {
					System.out.println("7) Перейти в RESEARCHER");
				}
				int choice = in.nextInt();
				if (choice == 1) {
					viewPersonalDataRun();
				} else if(choice == 2){
					viewNewsRun();
				} else if(choice == 3){
					viewJournalRun();
				} else if(choice == 4){
					sendEmployeeRequestRun();
				} else if(choice == 5){
					sendTechReportRun();
				} else if(choice == 6){
					this.logout();
					break menu;
				} else if(choice == 7 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
					goToResearcher();
				}
			}
		} catch (Exception e) {
			System.out.println("Error");;
        }
    }
	/**
	 * Manages the process of sending and viewing employee requests.
	 * This method provides options to an Employee to either send a new request, view the sent requests,
	 * or return to the previous menu. It uses the EmployeeRequest class for creating and handling requests.
	 */
	protected void sendEmployeeRequestRun(){
		Scanner in = new Scanner(System.in);
		int choice;
		System.out.println("1) Отправить новый запрос \n2) Посмотреть отправленные \n3) Вернуться назад");
		choice = in.nextInt();
		if(choice == 1){
			System.out.println("Введите текст запроса: ");
			in.nextLine();
			String text = in.nextLine();
			boolean sended = sendRequest(new EmployeeRequest(this, text));
			System.out.println((sended ? "Успешно отправлено" : "Не удалось отправить")
					+ "\n1) Вернуться назад");
			choice = in.nextInt();
			if(choice == 1) return;
		} else if(choice == 2){
			Vector<EmployeeRequest> requests = Data.getInstance().getEmployeeRequests().stream().filter(n -> n.getSender().equals(this)).collect(Collectors.toCollection(Vector::new));
			for (EmployeeRequest e: requests){
				System.out.println(e);
			}
			System.out.println("1) Вернуться назад");
			choice = in.nextInt();
			if(choice == 1) return;
		} else if(choice == 3){
			return;
		}
	}
}
