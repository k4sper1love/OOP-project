package universitysystem.entity;

import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.research.Researcher;
import universitysystem.utills.TechReport;
import universitysystem.utills.Notification;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * The class is designed for a technical specialist in the university system.
 * Inherits from the Employee class.
 */

public class TechSupportSpecialist extends Employee implements Serializable {
	private static Vector<TechReport> newOrders;
	private Vector<TechReport> acceptedOrders;
	private Vector<TechReport> completeOrders;

	static{
		newOrders = new Vector<>();
	}
	{
		acceptedOrders = new Vector<>();
		completeOrders = new Vector<>();
	}
    /**
     * Default constructor.
     */
	public TechSupportSpecialist(){
		super();
	}
    /**
     * Constructor with specified login and password.
     * @param login    The login of the specialist.
     * @param password The password of the specialist.
     */
	public TechSupportSpecialist(String login, String password) {
		super(login, password);
	}
    /**
     * Constructor with specified login, password, and salary.
     * @param login    The login of the specialist.
     * @param password The password of the specialist.
     * @param salary   The salary of the specialist.
     */

	public TechSupportSpecialist(String login, String password, double salary) {
		super(login, password, salary);
	}
    /**
     * Constructor with specified login, password, and salary.
     * @param login    The login of the specialist.
     * @param password The password of the specialist.
     * @param email   The salary of the specialist.
     */

	public TechSupportSpecialist(String login, String password, String email) {
		super(login, password, email);
	}
    /**
     * Constructor with specified login, password, and salary.
     * @param login    The login of the specialist.
     * @param password The password of the specialist.
     * @param email   The email of the specialist.
     * @param salary   The salary of the specialist.
     */

	public TechSupportSpecialist(String login, String password, String email, double salary) {
		super(login, password, email, salary);
	}
    /**
     * Constructor with {@code TechSupportSpecialist} class with specified attributes.
     * @param login    The login of the specialist.
     * @param password The password of the specialist.
     * @param email   The email of the specialist.
     * @param firstname The firstname of the specialist.
     * @param lastname The lastname of the specialist.
     * @param middlename The middlename of the specialist.
     */
	public TechSupportSpecialist(String login, String password, String email, String firstname,
								 String lastname, String middlename, Date birthDate) {
		super(login, password, email, firstname, lastname, middlename, birthDate);
	}
    /**
     * Constructor with {@code TechSupportSpecialist} class with specified attributes.
	 * @param login The login of the technical support specialist.
	 * @param password The password associated with the login.
	 * @param email The email address of the technical support specialist.
	 * @param firstname The first name of the technical support specialist.
	 * @param lastname The last name of the technical support specialist.
	 * @param middlename The middle name of the technical support specialist.
	 * @param birthDate The birth date of the technical support specialist.
	 * @param salary The salary of the technical support specialist.
     */
	public TechSupportSpecialist(String login, String password, String email, String firstname,
								 String lastname, String middlename, Date birthDate, double salary) {
		super(login, password, email, firstname, lastname, middlename, birthDate, salary);
	}
    /**
     * Generates a unique identifier for the specialist using the current year and the system identifier counter.
     * @return The generated ID.
     */
	@Override
	protected String generateId() {
		String id = "";
		id += Data.getInstance().getYear() % 100;
		id += "H" + String.format("%04d", Data.getInstance().getIdCounter());
		return id;
	}
    /**
     * Vector of new orders which gets.
     * @return The list of new orders.
     */
	public static Vector<TechReport> getNewOrders() {
		return Data.getInstance().getNewOrders();
	}
    /**
     * Adds a new order to the vector.
     * @param t The new order to be added.
     */
	public static void addNewOrder(TechReport t) {
		Data.getInstance().getNewOrders().add(t);
	}
    /**
     * New orders that can be deleted
     * @param t The new order to be removed.
     * @return true if removal is successful, false otherwise.
     */
	public static boolean removeNewOrder(TechReport t) {
		return Data.getInstance().getNewOrders().remove(t);
	}
    /**
     * Gets the list of accepted orders.
     * @return The list of accepted orders.
     */
	public Vector<TechReport> getAcceptedOrders() {
		return acceptedOrders;
	}
    /**
     * Gets the list of complete orders.
     * @return The list of complete orders.
     */
	public Vector<TechReport> getCompleteOrders() {
		return completeOrders;
	}
    /**
     * Accepts an order, moving it from new orders to accepted orders.
     * @param t The order to be accepted.
     * @return true if the order is accepted successfully, false otherwise.
     */
	public boolean acceptOrder(TechReport t) {
		Data.getInstance().getNewOrders().remove(t);
		acceptedOrders.add(t);
		return true;
	}
    /**
     * Completes an order, moving it from accepted orders to complete orders.
     * @param t The order to be completed.
     * @return true if the order is completed successfully, false otherwise.
     */
	public boolean completeOrder(TechReport t) {
		if(acceptedOrders.stream().noneMatch(n -> n.equals(t))) return false;
		acceptedOrders.remove(t);
		completeOrders.add(t);
		return true;
	}
    /**
     * Checks if two TechSupportSpecialist objects are equal.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		TechSupportSpecialist that = (TechSupportSpecialist) o;
		return Objects.equals(acceptedOrders, that.acceptedOrders) && Objects.equals(completeOrders, that.completeOrders);
	}
    /**
     * Generates a hash code for the TechSupportSpecialist object.
     * @return The generated hash code.
     */
	public int hashCode() {
		return Objects.hash(acceptedOrders, completeOrders);
	}
    /**
     * Provides a string representation of the TechSupportSpecialist object.
     * @return The string representation.
     */
	public String toString() {
		return "ID" + getId() + " " + getLastname() + " " + getFirstname() +
                " have " + completeOrders.size() + " completed orders";
	}
    /**
     * Operations in the technical specialist's personal.
     */
    public void run() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Вход успешен!Вы вошли в личный кабинет технического специалиста.");
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Выберите действие: \n1) Новые заказы \n2) Управление заказами " +
                    "\n3) Личные данные \n4) Новости \n5) Отправить запрос менеджеру \n6) Выйти из личного кабинета");
            if(Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
                System.out.println("7) Перейти в RESEARCHER");
            }
            int choice = in.nextInt();
            if (choice == 1){
                viewNewOrders();
            } else if(choice == 2){
                manageOrders();
            } else if(choice == 3){
                viewPersonalDataRun();
            } else if(choice == 4){
                viewNewsRun();
            }else if(choice == 5) {
                sendEmployeeRequestRun();
            }else if(choice == 7 && Data.getInstance().getResearchers().stream().map(Researcher::getInitialUser).anyMatch(n -> n.equals(this))){
                goToResearcher();
            }else if (choice == 6){
                this.logout();
                isRunning = false;
            }
        }
    }

    /**
     * Manages orders in the technical specialist's personal cabinet.
     */
    private void manageOrders() {
        Scanner in = new Scanner(System.in);
        System.out.println("Управление заказами технического специалиста.");
        System.out.println("Список заказов:");
        for (int i = 0; i < acceptedOrders.size(); i++) {
            System.out.println((i + 1) + ") " + acceptedOrders.get(i));
        }

        System.out.println("Выберите номер заказа для управления (или 0 для выхода):");
        int choice = in.nextInt();

        if (choice >= 1 && choice <= acceptedOrders.size()) {
            TechReport selectedOrder = acceptedOrders.get(choice - 1);

            System.out.println("Выбран заказ: " + selectedOrder);
            System.out.println("Выберите действие: \n1) Завершить заказ");
            int action = in.nextInt();

            switch (action) {
                case 1:
                    if (completeOrder(selectedOrder)) {
                        selectedOrder.getSender().addNotification(new Notification("Тех репорт", "Ваш тех репорт был завершен"));
                        Data.getInstance().getLogs().add(new Log(getId() + " завершил выполнение тех репорта от " + selectedOrder.getSender().getId()));
                        System.out.println("Заказ завершен успешно.");
                    } else {
                        System.out.println("Ошибка при завершении заказа.");
                    }
                    break;
                default:
                    System.out.println("Возврат к списку заказов.");
            }
        } else if (choice == 0) {
            System.out.println("Возврат в главное меню.");
        } else {
            System.out.println("Попробуйте снова.");
        }
    }

    /**
     * Views new orders in the technical specialist's personal cabinet.
     */
    private void viewNewOrders() {
        Scanner in = new Scanner(System.in);
        System.out.println("Новые заказы:");
        for (int i = 0; i < Data.getInstance().getNewOrders().size(); i++) {
            System.out.println((i + 1) + ") " + Data.getInstance().getNewOrders().get(i));
        }

        System.out.println("Выберите номер заказа для принятия (или 0 для выхода):");
        int choice = in.nextInt();
        if (choice >= 1 && choice <= Data.getInstance().getNewOrders().size()) {
            TechReport selectedOrder = Data.getInstance().getNewOrders().get(choice - 1);

            System.out.println("Выбран новый заказ: " + selectedOrder);
            System.out.println("Принять заказ? \n1) Да \n2) Нет \n3) Вернуться назад");
            int action = in.nextInt();
            if(action == 1){
                if (acceptOrder(selectedOrder)) {
                    selectedOrder.getSender().addNotification(new Notification("Тех репорт", "Ваш тех репорт был принят на рассмотрение"));
                    Data.getInstance().getLogs().add(new Log(getId() + " начал рассматривать тех репорт от " + selectedOrder.getSender().getId()));
                    System.out.println("Заказ принят успешно.");
                } else {
                    System.out.println("Ошибка при принятии заказа.");
                }
                return;
            } else if(action == 2){
                System.out.println("Заказ не принят. Возврат в главное меню.");
                return;
            } else if(action == 3){
                return;
            }
        } else if (choice == 0) {
            System.out.println("Возврат в главное меню.");
            return;
        }
    }

    /**
     * Logs out of the system.
     * @throws IOException
     */

}

