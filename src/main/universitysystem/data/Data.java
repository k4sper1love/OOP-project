package universitysystem.data;

import universitysystem.courses.Course;
import universitysystem.courses.Semester;
import universitysystem.entity.*;
import universitysystem.research.ResearchPaper;
import universitysystem.research.ResearchProject;
import universitysystem.research.Researcher;
import universitysystem.utills.*;

import java.io.*;
import java.util.*;

public class Data implements Serializable
{
	private static Data INSTANCE = new Data();
	private HashSet<Course> courses;
	private int logId;
	private Admin admin;
	private HashSet<Student> students;
	private HashSet<Employee> employees;
	private HashSet<Teacher> teachers;
	private HashSet<Manager> managers;
	private HashSet<Dean> deans;
	private HashSet<TechSupportSpecialist> techSpecialists;
	private HashSet<Researcher> researchers;
	private Vector<ResearchProject> researchProjects;
	private Vector<ResearchPaper> researchPapers;
	private Vector<TechReport> newOrders;
	private Vector<RegistrationRequest> registrationRequests;
	private Vector<EmployeeRequest> employeeRequests;
	private Vector<Complaint> complaints;
	private boolean registationIsOpen;
	private boolean schedulingIsOpen;
	private Vector<Log> logs;
	private Vector<News> news;
	private Vector<Journal> journals;
	private Vector<Organization> organizations;
	private int year;
	private Semester semester;
	private int idCounter;

	static{
		if(new File("data.ser").exists()){
			try {
				INSTANCE = read();
			} catch (Exception e){
				e.printStackTrace();
			}
		} else INSTANCE = new Data();
	}

	public static Data read() throws IOException, ClassNotFoundException{
		try {
			FileInputStream fis = new FileInputStream("data.ser");
			ObjectInputStream oin = new ObjectInputStream(fis);
			return (Data) oin.readObject();

		} catch (FileNotFoundException e){
			System.out.println(e);
			return new Data();
		} catch (ClassNotFoundException e){
			throw new ClassNotFoundException();
		}
	}

	public static void write() throws IOException{
		try{
			FileOutputStream fos = new FileOutputStream("data.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(INSTANCE);
			oos.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private Data() {
		super();
		this.idCounter = 0;
		this.logId = 0;
		this.year = new Date().getYear() + 1900;
		this.semester = Semester.FALL;
		this.courses = new HashSet<Course>();
		this.complaints = new Vector<>();
		this.registationIsOpen = true;
		this.schedulingIsOpen = true;
		this.employeeRequests = new Vector<>();
		this.registrationRequests = new Vector<>();
		this.admin = new Admin("admin", "12345"); // добавить лог и пароль
		this.students = new HashSet<Student>();
		this.newOrders = new Vector<>();
		this.employees = new HashSet<Employee>();
		this.teachers = new HashSet<Teacher>();
		this.managers = new HashSet<Manager>();
		this.deans = new HashSet<Dean>();
		this.techSpecialists = new HashSet<TechSupportSpecialist>();
		this.researchers = new HashSet<Researcher>();
		this.researchProjects = new Vector<ResearchProject>();
		this.researchPapers = new Vector<ResearchPaper>();
		this.logs = new Vector<Log>();
		this.news = new Vector<News>();
		this.journals = new Vector<Journal>();
		this.organizations = new Vector<>();
	}
	public int getLogId(){
		logId++;
		return logId;
	}
	public Vector<RegistrationRequest> getRegistrationRequests(){
		return registrationRequests;
	}
	public Vector<EmployeeRequest> getEmployeeRequests(){
		return employeeRequests;
	}
	public Vector<Complaint>  getComplaints(){
		return complaints;
	}
	public Vector<TechReport>  getNewOrders(){
		return newOrders;
	}
	public boolean getRegistrationStatus(){
		return registationIsOpen;
	}
	public void setRegistrationStatus(boolean b){
		this.registationIsOpen = b;
	}
	public boolean getSchedulingStatus(){
		return registationIsOpen;
	}
	public void setSchedulingStatus(boolean b){
		this.registationIsOpen = b;
	}
	public static Data getInstance() {
		return INSTANCE;	
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public HashSet<Course> getCourses() {
		return courses;
	}
	
	public boolean addCourse(Course c) {
		this.courses.add(c);
		return true;
	}
	
	public boolean removeCourse(Course c) {
		this.courses.remove(c);
		return true;
	}

	public HashSet<Student> getStudents() {
		return students;
	}
	
	public boolean addStudent(Student s) {
		this.students.add(s);
		return true;
	}
	
	public boolean removeStudent(Student s) {
		this.students.remove(s);
		return true;
	}

	public boolean addEmployee(Employee e){
		this.employees.add(e);
		return true;
	}

	public boolean removeEmployee(Employee e){
		this.employees.remove(e);
		return true;
	}

	public HashSet<Employee> getEmployees(){
		return employees;
	}
	public HashSet<Teacher> getTeachers() {
		return teachers;
	}

	public boolean addTeacher(Teacher t) {
		this.teachers.add(t);
		return true;
	}
	
	public boolean removeTeacher(Teacher t) {
		this.teachers.remove(t);
		return true;
	}

	public HashSet<Manager> getManagers() {
		return managers;
	}

	public boolean addManager(Manager m) {
		this.managers.add(m);
		return true;
	}
	
	public boolean removeManager(Manager m) {
		this.managers.remove(m);
		return true;
	}

	public HashSet<Dean> getDeans() {
		return deans;
	}

	public boolean addDean(Dean d) {
		this.deans.add(d);
		return true;
	}
	
	public boolean removeDean(Dean d) {
		this.deans.remove(d);
		return true;
	}

	public HashSet<TechSupportSpecialist> getTechSpecialists() {
		return techSpecialists;
	}

	public boolean addTechSpecialist(TechSupportSpecialist t) {
		this.techSpecialists.add(t);
		return true;
	}
	
	public boolean removeTechSpecialist(TechSupportSpecialist t) {
		this.techSpecialists.remove(t);
		return true;
	}

	public HashSet<Researcher> getResearchers() {
		return researchers;
	}

	public boolean addReseacher(Researcher r) {
		this.researchers.add(r);
		return true;
	}

	public boolean removeResearcher(Researcher r){
		this.researchers.remove(r);
		return true;
	}

	public Vector<ResearchProject> getResearchProjects() {
		return researchProjects;
	}

	public boolean addResearchProject(ResearchProject r) {
		this.researchProjects.add(r);
		return true;
	}
	
	public boolean removeResearchProject(ResearchProject r) {
		this.researchProjects.remove(r);
		return true;
	}

	public Vector<ResearchPaper> getResearchPapers() {
		return researchPapers;
	}

	public boolean addResearchPaper(ResearchPaper r) {
		this.researchPapers.add(r);
		return true;
	}
	
	public boolean removeResearchPaper(ResearchPaper r) {
		this.researchPapers.remove(r);
		return true;
	}

	public Vector<Log> getLogs() {
		return logs;
	}

	public boolean addLog(Log l) {
		this.logs.add(l);
		return true;
	}
	
	public boolean removeLog(Log l) {
		this.logs.remove(l);
		return true;
	}

	public Vector<News> getNews() {
		return news;
	}

	public boolean addNews(News n) {
		this.news.add(n);
		return true;
	}
	
	public boolean removeNews(News n) {
		this.news.remove(n);
		return true;
	}

	public Vector<Journal> getJournals() {
		return journals;
	}

	public boolean addJournal(Journal j) {
		this.journals.add(j);
		return true;
	}
	
	public boolean removeJournal(Journal j) {
		this.journals.remove(j);
		return true;
	}

	public Vector<Organization> getOrganizations() {
		return organizations;
	}

	public boolean addOrganization(Organization o) {
		this.organizations.add(o);
		return true;
	}
	
	public boolean removeOrganization(Organization o) {
		this.organizations.remove(o);
		return true;
	}

	public int getYear(){
		return this.year;
	}

	public Semester getSemester(){
		return this.semester;
	}

	public void setSemester(Semester semester){
		this.semester = semester;
	}

	public void setYear(int year){
		this.year = year;
	}

	public int getIdCounter(){
		return ++idCounter;
	}

	public void resetIdCounter(){
		idCounter = 0;
	}

	public void nextSemester(){
		students.forEach(Student::endSemester);
		teachers.forEach(Teacher::endSemester);
		if (this.semester == Semester.FALL) this.semester = Semester.SPRING;
		else {
			this.semester = Semester.FALL;
			this.year = this.year++;
			resetIdCounter();
			students.forEach(n -> n.setYearOfStudy(n.getYearOfStudy() + 1));
		}
	}
}

