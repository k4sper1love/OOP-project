import universitysystem.entity.*;
import universitysystem.courses.*;
import universitysystem.data.*;
import universitysystem.entity.*;
import universitysystem.interfaces.CanLogin;
import universitysystem.research.*;
import universitysystem.utills.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;

//admin - login: admin pass: 12345
public class UniversitySystemTest {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Vector<CanLogin> users = new Vector<>();
		users.add(Data.getInstance().getAdmin());
		users.addAll(Data.getInstance().getEmployees());
		users.addAll(Data.getInstance().getStudents());
		users.addAll(Data.getInstance().getManagers());
		users.addAll(Data.getInstance().getDeans());
		users.addAll(Data.getInstance().getTeachers());
		users.addAll(Data.getInstance().getTechSpecialists());
		System.out.println("Добро пожаловать в систему университета! \nВведите логин: ");
		String login = in.next();
		if(users.stream().anyMatch(n -> n.getLogin().equals(login))){
			CanLogin user = users.stream().filter(n -> n.getLogin().equals(login)).findFirst().orElse(null);
			System.out.println("Введите пароль: ");
			String password = in.next();
			if(user.getPassword().equals(password)){
				System.out.println("Производим вход....");
				user.login();
			} else {
				System.out.println("Неверный пароль");
			}
		} else{
			System.out.println("Пользователя с таким логином не существует!");
		}
	}

}
