package universitysystem.data;


import universitysystem.entity.*;
import universitysystem.interfaces.CanCreate;
import universitysystem.research.Researcher;

import java.io.Serializable;
import java.util.Objects;

//TODO Adil на тебе висит
public class UserFactory implements Serializable {
    public UserFactory() {
        super();
    }
    public User createUser(String login, String password, UsersType type) {
        String email = login + "@wow.kz";
        return switch (type) {
            case DEAN -> {
                Dean d = new Dean(login, password, email);
                Data.getInstance().addDean(d);
                yield d;
            }
            case STUDENT -> {
                Student s = new Student(login, password, email);
                Data.getInstance().addStudent(s);
                yield s;
            }
            case MANAGER -> {
                Manager m = new Manager(login, password, email);
                Data.getInstance().addManager(m);
                yield m;
            }
            case TEACHER -> {
                Teacher t = new Teacher(login, password, email);
                Data.getInstance().addTeacher(t);
                yield t;
            }
            case TECH_SUPPORT -> {
                TechSupportSpecialist t = new TechSupportSpecialist(login, password, email);
                Data.getInstance().addTechSpecialist(t);
                yield t;
            }
            case EMPLOYEE -> {
                Employee e = new Employee(login, password, email);
                Data.getInstance().addEmployee(e);
                yield e;
            }
            case GRADUATE_STUDENT -> {
                GraduateStudent g = new GraduateStudent(login, password, email);
                Data.getInstance().addStudent(g);
                yield g;
            }
        };
    }

    public CanCreate create(String login, String password, UsersType type, boolean isResearcher) {
        User user = createUser(login, password, type);
        if (isResearcher) {
            Data.getInstance().addReseacher(new Researcher(user));
        }
        return user;
    }
}

