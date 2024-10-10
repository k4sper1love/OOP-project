package universitysystem.entity;

import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.interfaces.CanCreate;
import universitysystem.interfaces.CanLogin;
import universitysystem.interfaces.Runnable;
import universitysystem.interfaces.Subscription;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The abstract class {@code User} presents a user in the university system.
 * {@code Subscription} and {@code CanCreate} interfaces, allowing users to subscribe
 * to journals, create technical reports, and perform other relevant actions.
 * @author 
 * @version 1.0
 */
public abstract class User implements Subscription, CanCreate, Comparable<User>, CanLogin, Serializable {
 private String id;
    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String middlename;
    private Date birthDate;
    private SystemLanguage systemLanguage;
    private LinkedList<Notification> notifications;

    {
        this.id = generateId();
    }
    public User(){
    }
    /**
     * Constructs a new {@code User} object with the specified login and password.
     *
     * @param login    the login credentials for the user
     * @param password the password associated with the login
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        systemLanguage = SystemLanguage.EN;
        notifications = new LinkedList<>();
    }
    /**
     * Constructs a new {@code User} object with the specified login, password, and email.
     *
     * @param login    the login credentials for the user
     * @param password the password associated with the login
     * @param email    the email address associated with the user
     */
    public User(String login, String password, String email) {
        this(login, password);
        this.email = email;
    }
    /**
     * Constructs a new {@code User} object with the specified login, password, email,
     * firstname, and lastname.
     *
     * @param login     the login credentials for the user
     * @param password  the password associated with the login
     * @param email     the email address associated with the user
     * @param firstname the user's first name
     * @param lastname  the user's last name
     */
    public User(String login, String password, String email, String firstname, String lastname) {
        this(login, password, email);
        this.firstname = firstname;
        this.lastname = lastname;
    }
    /**
     * Constructs a new {@code User} object with the specified login, password, email,
     * firstname, lastname, and birthDate.
     *
     * @param login     the login credentials for the user
     * @param password  the password associated with the login
     * @param email     the email address associated with the user
     * @param firstname the user's first name
     * @param lastname  the user's last name
     * @param birthDate the user's birth date
     */
    public User(String login, String password, String email, String firstname, String lastname, Date birthDate) {
        this(login, password, email, firstname, lastname);
        this.birthDate = birthDate;
    }
    /**
     * Constructs a new {@code User} object with the specified login, password, email,
     * firstname, lastname, and middlename.
     *
     * @param login      the login credentials for the user
     * @param password   the password associated with the login
     * @param email      the email address associated with the user
     * @param firstname  the user's first name
     * @param lastname   the user's last name
     * @param middlename the user's middle name
     */
    public User(String login, String password, String email, String firstname, String lastname, String middlename) {
        this(login, password, email, firstname, lastname);
        this.middlename = middlename;
    }
    /**
     * Constructs a new {@code User} object with the specified login, password, email,
     * firstname, lastname, middlename, and birthDate.
     *
     * @param login      the login credentials for the user
     * @param password   the password associated with the login
     * @param email      the email address associated with the user
     * @param firstname  the user's first name
     * @param lastname   the user's last name
     * @param middlename the user's middle name
     * @param birthDate  the user's birth date
     */
    public User(String login, String password, String email, String firstname, String lastname, String middlename, Date birthDate) {
        this(login, password, email, firstname, lastname, middlename);
        this.birthDate = birthDate;
    }

/**
 * Returns the user's special ID.
 *
 * @return The user's unique identifier
 */
public String getId(){
     return id;
}
/**
 * Sets a unique ID for the user.
 *
 * @param id he new unique ID for the user
 */
protected void setId(String id){
     this.id = id;
}
/**
 * Generates a unique identifier for the user. Subclasses should implement this
 * method to provide a unique identifier generation mechanism.
 *
 * @return a unique identifier for the user
 */
protected abstract String generateId();
    //Setters
/**
 * Sets the user's first name.
 *
 * @param firstname the user's first name
 */
 public void setFirstname(String firstname) {
	 this.firstname = firstname;
 }
 /**
  * Sets the user's middle name.
  *
  * @param middlename the user's middle name
  */
 public void setMiddlename(String middlename) {
	 this.middlename = middlename;
 }
 /**
  * Sets the user's last name.
  * @param lastname the user's last name
  */
 public void setLastname(String lastname) {
	 this.lastname = lastname;
 }
 /**
  * Sets the user's full name.
  * @param firstname the user's first name
  * @param lastname  the user's last name
  * @param middlename the user's middle name
  */
 public void setFullName(String firstname, String lastname, String middlename) {
     this.firstname = firstname;
     this.lastname = lastname;
     this.middlename = middlename;
 }
 /**
  * Sets the user's login credentials.
  * @param login the user's login credentials
  */
 public void setLogin(String login){
     this.login = login; //возможно убрать, ведь логин не изменяемый
 }
 /**
  * Sets the user's password.
  *
  * @param password the user's password
  */
 public void setPassword(String password){
	 this.password = password;
 }
 /**
  * Sets the user's email address.
  *
  * @param email the user's email address
  */
 public void setEmail(String email){
	 this.email = email;
 }
 /**
  * Sets the user's birth date.
  *
  * @param birthDate the user's birth date
  */
 public void setBirthDate(Date birthDate){
	 this.birthDate = birthDate;
 }
 /**
  * Sets the user's preferred system language.
  *
  * @param systemLanguage the user's preferred system language
  */
 public void setSystemLanguage(SystemLanguage systemLanguage) {
	 this.systemLanguage = systemLanguage;
 }

 //Getters
 /**
  * Returns the user's first name.
  * @return the user's first name
  */
 public String getFirstname(){
	 return firstname;
 }
 /**
  * Returns the user's middle name.
  * @return the user's middle name
  */
 public String getMiddlename(){
	 return middlename;
 }
 /**
  * Returns the user's last name.
  * @return the user's last name
  */
 public String getLastname(){
	 return lastname;
 }
 /**
  * Returns the user's login credentials.
  * @return the user's login credentials
  */
 public String getLogin(){
	 return login;
 }
 /**
  * Returns the user's password.
  * @return the user's password
  */
 public String getPassword(){
	 return password;
 }
 /**
  * Returns the user's email address.
  * @return the user's email address
  */

 public String getEmail(){
     return email;
 }
 /**
  * Returns the user's birth date.
  * @return the user's birth date
  */
 public Date getBirthDate(){
	 return birthDate;
 }
 /**
  * Returns the user's preferred system language.
  * @return the user's preferred system language
  */
 public SystemLanguage getSystemLanguage() {
	 return systemLanguage;
 }
 /**
  * Returns the list of notifications associated with the user.
  * @return the list of notifications
  */
 public LinkedList<Notification> getNotifications() {
    return notifications;
 }
 /**
  * Adds a notification to the user's notification list.
  * @param n the notification to be added
  */
public void addNotification(Notification n) {
    notifications.add(n);
}
/**
 * Clears all notifications from the user's notification list
 * @return {@code true} if notifications were cleared successfully, {@code false} otherwise
 */
public boolean clearNotifications() {
    notifications.clear();
    return true;
}
/**
 * Returns a vector of news articles available for the user to view.
 * @return a vector of news articles
 */
public Vector<News> viewNews() {
    return Data.getInstance().getNews();
} 
/**
 * Sends a technical report.
 * @param report the technical report to be sent
 * @return {@code true} if the report was sent successfully, {@code false} otherwise
 */
//TODO
public boolean sendTechReport(TechReport report) {
    TechSupportSpecialist.addNewOrder(report);
    Data.getInstance().getLogs().add(new Log(getId() + " отправил tech report"));
    return true;
}
/**
 * Checks if the current user is equal to another object.
 * @param o the object to compare with
 * @return {@code true} if the objects are equal, {@code false} otherwise
 */
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User otherUser = (User) o;

    return login.equals(otherUser.login) ||
            email.equals(otherUser.email) ||
            id.equals(otherUser.id);
}
/**
 * Returns a string representation of the user.
 * @return a string representation of the user
 */
public String toString() {
    return "ID: " + id + ", Email: " + email +
            "\nFull name: " + lastname + " " + firstname + (middlename != null ? " " + middlename : "")
            + "\nBirth date " + birthDate;
}
/**
 * Subscribes the user to a journal.
 * @param j the journal to subscribe to
 * @return {@code true} if the user is successfully subscribed, {@code false} otherwise
 */
    @Override
    public boolean subscribeToJournal(Journal j) {
        Data.getInstance().getLogs().add(new Log(getId() + " подписался на журнал " + j.getName()));
        return j.addSubscriber(this);
    }
    /**
     * Returns a vector of journals available for the user to view.
     * @return a vector of journals
     */
    @Override
    public Vector<Journal> viewJournals() {
        return Data.getInstance().getJournals();
    }
    /**
     * Compares this user to another user based on their last names.
     * @param o the user to compare with
     * @return a negative integer, zero, or a positive integer if this user is
     * less than, equal to, or greater than the specified user
     */
    @Override
    public int compareTo(User o){
        return this.lastname.compareTo(o.lastname);
    }
    /**
     * Returns a hash code value for the user.
     * @return a hash code value for the user
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }
    /**
     * Manages the viewing and modification of personal data for a User.
     * This method displays a menu allowing the User to view personal information, change the password,
     * and modify language settings. The user is prompted to enter new information based on their choice.
     */
    protected void viewPersonalDataRun(){
        Scanner in = new Scanner(System.in);
        personalData:
        while (true) {
            System.out.println("1) Посмотреть информацию о себе \n2) Изменить пароль \n3) Изменить языковые настройки \n4) Вернуться назад");
            int choice = in.nextInt();
            if (choice == 1) {
                System.out.println(toString());
            } else if (choice == 2) {
                System.out.println("Введите новый пароль: ");
                String pass = in.next();
                this.setPassword(pass);
                System.out.println("Пароль успешно обновлен! \n1) Вернуться назад \n2) Exit");
                Data.getInstance().getLogs().add(new Log(getId() + " обновил пароль"));
                choice = in.nextInt();
                if (choice == 1) continue personalData;
                if (choice == 2) {
                    break personalData;
                }
            } else if (choice == 3) {
                System.out.println("Сменить язык на: \n1) EN \n2) RU \n3) KZ \n4)Вернуться назад");
                choice = in.nextInt();
                if (choice == 1) setSystemLanguage(SystemLanguage.EN);
                else if (choice == 2) setSystemLanguage(SystemLanguage.RU);
                else if (choice == 3) setSystemLanguage(SystemLanguage.KZ);
                else if (choice == 4) continue personalData;
                System.out.println("Системный язык изменен. \n1) Вернуться назад \n2) Exit");
                choice = in.nextInt();
                if (choice == 1) continue personalData;
                if (choice == 2) {
                    break personalData;
                }
            }
            else if (choice == 4) {
                break personalData;
            }
        }
    }
    /**
     * Displays and manages the viewing of news for a User.
     * This method allows the User to view news and navigate through them.
     * The user can choose to view more news, return to the previous menu, or exit.
     */
    protected void viewNewsRun(){
        Scanner in = new Scanner(System.in);
        int choice;
        for (News n : viewNews()) {
            System.out.println(n);
            }
        System.out.println("Новости закончились. \n1) Вернуться назад");
        choice = in.nextInt();
        if (choice == 1) return;
    }
    /**
     * Manages the viewing and subscription to journals for a User.
     * This method allows the User to view all available journals, subscribe or unsubscribe from journals,
     * and return to the previous menu. It uses the Journal class for journal-related operations.
     */
    protected void viewJournalRun(){
        Scanner in = new Scanner(System.in);
        int choice;
        journals:
        while (true) {
            Vector<Journal> subJournal = viewJournals().stream().filter(n -> n.getSubscribers().contains(this))
                    .collect(Collectors.toCollection(Vector::new));
            System.out.println("Вы подписаны на " + subJournal.size() + " журнала \n1) Все журналы" +
                    "\n2) Журналы, на которые вы подписаны \n3) Вернуться назад");
            choice = in.nextInt();
            if (choice == 1) {
                System.out.println("Все журналы: ");
                int cnt = 0;
                for (Journal j : viewJournals()) {
                    System.out.println(++cnt + ") " + j);
                }
                System.out.println("1) Подписаться на журнал \n2) Вернуться назад");
                choice = in.nextInt();
                if (choice == 1) {
                    System.out.println("Выберите журнал: ");
                    choice = in.nextInt();
                    if (choice < 1 || choice > cnt) continue journals;
                    Journal actualJournal = viewJournals().get(choice - 1);
                    boolean sub = subscribeToJournal(actualJournal);
                    System.out.println((sub ? "Вы успешно подписались!" : "Не получилось подписаться")
                            + "\n1) Вернутся к журналам \n2) Exit");
                    choice = in.nextInt();
                    if (choice == 1) continue journals;
                    if (choice == 2) {
                        break;
                    }
                } else if (choice == 2) continue journals;

            } else if (choice == 2) {
                System.out.println("Ваши подписки: ");
                int cnt = 0;
                for (Journal j : subJournal) {
                    System.out.println(++cnt + ") " + j);
                }
                System.out.println("1) Отписаться от журнала \n2) Вернуться назад");
                choice = in.nextInt();
                if (choice == 1) {
                    System.out.println("Выберите журнал: ");
                    choice = in.nextInt();
                    if (choice < 1 || choice > cnt) continue journals;
                    Journal actualJournal = subJournal.get(choice - 1);
                    actualJournal.removeSubscriber(this);
                    Data.getInstance().getLogs().add(new Log(getId() + " отписался от журнала " + actualJournal.getName()));
                    System.out.println("Вы успешно отписались от журнала!"
                            + "\n1) Вернутся к журналам \n2) Exit");
                    choice = in.nextInt();
                    if (choice == 1) continue journals;
                    if (choice == 2) {
                       break;
                    }
                } else if (choice == 2) continue journals;
            } else if (choice == 3) break;
        }
    }
    /**
     * Manages the process of sending a tech report for a User.
     * This method prompts the User to choose a category for the tech report, enter the report's text,
     * and then sends the report. The user is informed about the success or failure of the report submission.
     */
    protected void sendTechReportRun(){
        Scanner in = new Scanner(System.in);
        int choice;
        teching: while (true){
            System.out.println("1) Отправить report \n2) Посмотреть необработанные report \n3) Вернуться назад");
            choice = in.nextInt();
            if (choice == 1){
                System.out.println("Выберите категорию репорта: \n1) FEEDBACK \n2) CONTENT" +
                        "\n3) SECURITY \n4) SITE PERFORMANCE \n5) ERROR \n6) OTHER \n7) Вернуться назад");
                choice = in.nextInt();
                if(choice < 1 || choice >= 7) return;
                ReportCategory category = null;
                if(choice == 1) category = ReportCategory.FEEDBACK;
                if(choice == 2) category = ReportCategory.CONTENT;
                if(choice == 3) category = ReportCategory.SECURITY;
                if(choice == 4) category = ReportCategory.SITE_PERFORMANCE;
                if(choice == 5) category = ReportCategory.ERROR;
                if(choice == 6) category = ReportCategory.OTHER;
                System.out.println("Введите текст репорта: ");
                in.nextLine();
                String text = in.nextLine();
                boolean sended = sendTechReport(new TechReport(this, text, category));
                System.out.println((sended ? "Успешно отправлено!" : "Не удалось отправить")
                        + "\n1) Вернуться назад");
                choice = in.nextInt();
                if(choice == 1) continue teching;
            } else if(choice == 2){
                Vector<TechReport> reports = Data.getInstance().getNewOrders().stream().filter(n -> n.getSender().equals(this)).collect(Collectors.toCollection(Vector::new));
                for (TechReport t: reports){
                    System.out.println(t);
                }
                System.out.println("1) Вернуться назад \n2) Выйти");
                choice = in.nextInt();
                if (choice == 1) continue teching;
                else if(choice == 2) return;
            } else if(choice == 3) return;
        }
    }
    /**
     * Initiates the transition to the Researcher role for a User.
     */
    protected void goToResearcher() throws IOException {
        System.out.println("Выполняем переход в Researcher....");
        Researcher r = Data.getInstance().getResearchers().stream().filter(n -> n.getInitialUser().equals(this)).findFirst().orElse(null);
        r.run();
        this.run();
    }
    /**
     * Logs out the User and saves any necessary data.
     */
    public void logout(){
        Data.getInstance().getLogs().add(new Log(this.getId() + " вышел из системы"));
        System.out.println("Выполняем выход...");
        try {
            save();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Saves the User's data to storage.
     * This method saves the User's data using the Data class and handles any IOException that may occur.
     * @throws IOException If an input or output exception occurs during the data saving process.
     */
    public void save() throws IOException{
        Data.write();
    }
    /**
     * Logs in the User and presents a welcome message.
     * This method displays a welcome message, checks for notifications, and provides options for navigating
     * between the User and Researcher roles. The user is prompted to choose the desired action.
     */
    public void login(){
        Scanner in = new Scanner(System.in);
        try {
            Data.getInstance().getLogs().add(new Log(this.getId() + " вошел в систему"));
            System.out.println("Добро пожаловать, " + this.getFirstname());
            if(getNotifications().isEmpty()) System.out.println("Нет новых уведомлений");
            else System.out.println("У вас " + getNotifications().size() + " notification/s");
            for (Notification n: getNotifications()){
                System.out.println(n);
            }
            clearNotifications();
            if(Data.getInstance().getResearchers().stream().anyMatch(n -> n.getInitialUser().equals(this))){
                System.out.println("Вы являетесь User и Researcher. В какой личный кабинет вы хотите зайти? \n1) User \n2) Researcher \n3) Exit");
                int choice = in.nextInt();
                if(choice == 1) this.run();
                else if(choice == 2){
                   goToResearcher();
                }
                else if(choice == 3){
                    this.logout();
                }
            } else {
                System.out.println(("Хотите перейти в личный кабинет User? \n1) Перейти \n2) Exit"));
                int choice = in.nextInt();
                if(choice == 1) this.run();
                else if(choice == 2){
                    this.logout();
                }
            }

        } catch (Exception e){
            System.out.println("Error");
        }
    }
}
