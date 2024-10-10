package universitysystem.research;
//TODO Alzhan переделать Researcher
import universitysystem.data.Data;
import universitysystem.data.Log;
import universitysystem.entity.GraduateType;
import universitysystem.interfaces.CanCreate;
import universitysystem.interfaces.CanResearch;
import universitysystem.entity.GraduateStudent;
import universitysystem.entity.User;
import universitysystem.utills.Journal;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Class {@code Researcher} presents a user with research capabilities in the system of university.
 * It implements the {@code CanCreate}, {@code CanResearch}, and {@code Comparable} interfaces.
 * This class is associated with research papers, projects, and students for supervision.
 * @author 
 * @version 1.0
 */
public class Researcher implements CanCreate, CanResearch, Comparable<Researcher>, Runnable, Serializable {
	//добавлено : Serializable implements
	//переделан сканнер
	private final User initialUser;
    /**
     * Constructs a new {@code Researcher} instance with the provided user.
     * @param user associated with the researcher.
     */
	public Researcher(User user) {
		super();
        this.initialUser = user;
	}
    /**
	 * Initial user associated with the researcher.
	 *
	 * @return The initial user.
	 */
	public User getInitialUser() {
		return initialUser;
	}
    /**
     * Sorted set of research papers authored by the researcher.
     * @param c Comparator used for sorting the papers.
     * @return Sorted {@code TreeSet} of {@code ResearchPaper} instances.
     * @see ResearchPaper
     */
	//для примера. делаешь так
    //Data.getInstance().getResearchPapers().stream().filter(n -> n.getAuthors().contains(this)); - это не совсем правильно, но суть поймешь
	//и так везде
	public TreeSet<ResearchPaper> printPapers(Comparator<ResearchPaper> c) {
	    return Data.getInstance().getResearchPapers().stream()
	            .filter(paper -> paper.getAuthors().contains(this))
	            .sorted(c)
	            .collect(Collectors.toCollection(TreeSet::new));
	}
    /**
     * Calculates and extracts the researcher's H-index based on the citation of his scientific articles.
     * @return The H-index of the researcher.
     */
	public int getHIndex() {
		List<Integer> citationsCnt = Data.getInstance().getResearchPapers().stream()
				.filter(researchPaper -> researchPaper.getAuthors().contains(this))
				.map(researchPaper -> researchPaper.getCitations().size())
				.sorted(Comparator.reverseOrder()).toList();

		return (int) Data.getInstance().getResearchPapers().stream()
				.filter(researchPaper -> researchPaper.getAuthors().contains(this))
				.map(researchPaper -> researchPaper.getCitations().size())
				.sorted(Comparator.reverseOrder())
				.takeWhile(i -> i >= citationsCnt.indexOf(i) + 1)
				.count();
	}
    /**
     * Creates a new research paper and adds.
     * @param paper The research paper to be created.
     * @return {@code true} if the paper is successfully created and added, {@code false} otherwise.
     */
	public boolean createResearchPaper(ResearchPaper paper) {
			paper.addAuthor(this);
			Data.getInstance().getLogs().add(new Log(initialUser.getId() + " создал научную статью " + paper.getTitle()));
		    return Data.getInstance().addResearchPaper(paper);
	}

	//TODO сделать метод connectToPaper(ResearchPaper paper) типо добавиться к авторам в существующей уже статье
    /**
     * Connects the researcher to an existing research paper by adding them as an author.
     * @param paper The research paper to connect to.
     * @return {@code true} if the connection is successful, {@code false} otherwise.
     */
	// потом мб сделать void
	public boolean connectToPaper(ResearchPaper paper) {
	    Data.getInstance().getResearchPapers().stream()
	            .filter(p -> p.equals(paper))
	            .findFirst()
				.ifPresent(researchPaper -> researchPaper.addAuthor(this));
		Data.getInstance().getLogs().add(new Log(initialUser.getId() + " подключился к научной статье " + paper.getTitle()));
	    return true;
	}
    /**
     * Creates a new research project and adds.
     * @param project Research project to be created.
     * @return {@code true} if the project is successfully created and added, {@code false} otherwise.
     */
	public boolean createProject(ResearchProject project) {
          project.addParticipant(this);
		Data.getInstance().getLogs().add(new Log(initialUser.getId() + " создал научный проект " + project.getTopic()));
		return Data.getInstance().addResearchProject(project);
	}
    /**
     * Adding a researcher as a participant. Connects the researcher to an existing research project.
     * @param project research project to connect to.
     * @return {@code true} if the connection is successful, {@code false} otherwise.
     */
	public boolean connectToProject(ResearchProject project) {
	    if (project != null && Data.getInstance().getResearchProjects().contains(project)) {
	        project.addParticipant(this);
			Data.getInstance().getLogs().add(new Log(initialUser.getId() + " подключился к научному проекту " + project.getTopic()));
	        return true;
	    }
	    return false;
	}
    /**
     * Appoints this graduate student as a supervisor and integrates him into the research process.
     * @param g The graduate student to supervise.
     * @return {@code true} if the researcher becomes a supervisor, {@code false} otherwise.
     * @throws LowHIndexException If the H-index of the researcher is below 3.
     */
	public boolean becomeSupervisor(GraduateStudent g) throws LowHIndexException {
		if (g.getSupervisor() != null) {
			return false;
		}
		if (getHIndex() < 3) {
			throw new LowHIndexException("HIndex is too low to become a supervisor");
		}
			boolean temp = Data.getInstance().getResearchProjects().stream()
				.anyMatch(researchProject -> researchProject.getParticipants().contains(g));
			g.setSupervisor(this);
			if (temp) {
				Data.getInstance().getResearchProjects().stream()
					.filter(researchProject -> researchProject.getParticipants().contains(g))
					.findFirst()
					.ifPresent(researchProject -> researchProject.addParticipant(this));
				Data.getInstance().getLogs().add(new Log(initialUser.getId() + " стал supervisor для " + g.getId()));
			}
			else {
				ResearchProject researchProject = new ResearchProject();
				Data.getInstance().addResearchProject(researchProject);
				g.createProject(researchProject);
				researchProject.addParticipant(this);
			}
			return true;
	}
    /**
     * Performs a check for compliance between this researcher and the specified object..
     * @param o The object to compare with this researcher.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Researcher researcher)) return false;

		return initialUser.equals(researcher.initialUser);
	}
    /**
     * Hash code for this researcher.
     * @return Hash code value for this researcher.
     */
	public int hashCode() {
	    return initialUser.hashCode();
	}
	
    /**
     * Returns a string representation of this researcher.
     * @return Representation of the researcher.
     */
	@Override
	public String toString() {
		return initialUser + " is Researcher";
	}
    /**
     * Performs a comparison between a given researcher and another based on the users associated with them.
     * @param o The researcher to compare with.
     * @return A negative integer, zero, or a positive integer if this researcher is less than, equal to,
     * or greater than the specified researcher.
     */
	@Override
    public int compareTo(Researcher o) {
        return initialUser.compareTo(o.initialUser);
    }

	public void createNewJournal(Journal j){
		Data.getInstance().addJournal(j);
		j.addSubscriber(getInitialUser());
	}
	/**
	 * Manages the actions and interactions available to the researcher in their personal account. 
	 * Provides options to view the H index, scientific articles, research projects, journals, and tasks related to the research supervisor. 
	 * Enables researchers to view, create, and connect to scientific articles and projects, 
	 * as well as create new journals and perform tasks associated with the research supervisor. 
	 * The method uses a console for user input and displays relevant information during the process. 
	 * Researchers can navigate through the menu and return to the user interface.
	 */
	public void run(){
		Scanner in = new Scanner(System.in);
		try {
			menu:
			while (true) {
				System.out.println("Сейчас вы в личном кабинете RESEARCHER");
				System.out.println("Что вы хотите сделать? \n1) Узнать H-index \n2) Научные статьи " +
						"\n3) Научные проекты \n4) Журналы \n5) Supervisor \n6) Вернуться в user-interface");
				int choice = in.nextInt();
				if (choice == 1) {
					System.out.println("Ваш h-index: " + getHIndex());
					System.out.println("1) Вернуться назад \n2) Вернуться в user-inerface");
					choice = in.nextInt();
					if (choice == 1) continue menu;
					if (choice == 2) break;
				} else if (choice == 2) {
					papering:
					while (true) {
						System.out.println("1) Посмотреть свои научные статьи \n2) Создать научную статью \n3) Присоединиться к научной статье \n4) Вернуться назад");
						choice = in.nextInt();
						if (choice == 1) {
							Vector<ResearchPaper> actualPapers = Data.getInstance().getResearchPapers().stream().filter(n -> n.getAuthors().contains(this)).collect(Collectors.toCollection(Vector::new));
							System.out.println("Ваши научные статьи: ");
							for (ResearchPaper p : actualPapers) {
								System.out.println(p);
							}
						} else if (choice == 2) {
							System.out.println("Введите название научной статьи: ");
							in.nextLine();
							String text = in.nextLine();
							createResearchPaper(new ResearchPaper(text));
							Data.getInstance().getLogs().add(new Log(initialUser.getId() + " создал научную статью " + text));
							System.out.println("Успешно добавлено");
						} else if (choice == 3) {
							System.out.println("Выберите существующую статью: ");
							int cnt = 0;
							Vector<ResearchPaper> papersForJoin = Data.getInstance().getResearchPapers().stream()
									.filter(n -> n.getAuthors().stream()
											.noneMatch(u -> u.equals(getInitialUser()))).collect(Collectors.toCollection(Vector::new));
							for (ResearchPaper p : papersForJoin) {
								System.out.println(++cnt + ") " + p);
							}
							System.out.println(++cnt + ") Вернуться назад");
							choice = in.nextInt();
							if (choice < 1 || choice >= cnt) continue papering;
							ResearchPaper actualPaper = papersForJoin.get(choice - 1);
							connectToPaper(actualPaper);
							Data.getInstance().getLogs().add(new Log(initialUser.getId() + " присоеденился к научной статье " + actualPaper.getTitle()));
							System.out.println("Успешно присоеденены!");
						} else if (choice == 4) {
							continue menu;
						}
						System.out.println("1) Вернуться назад \n2) Вернуться в user-interface");
						choice = in.nextInt();
						if (choice == 1) continue menu;
						if (choice == 2) break;
					}
				} else if (choice == 3) {
					projecting:
					while (true) {
						System.out.println("1) Посмотреть свои проекты \n2) Создать проект \n3) Присоединиться к проекту \n4) Вернуться назад");
						choice = in.nextInt();
						if (choice == 1) {
							Vector<ResearchProject> actualProjects = Data.getInstance().getResearchProjects().stream().filter(n -> n.getParticipants().contains(this)).collect(Collectors.toCollection(Vector::new));
							System.out.println("Ваши научные проекты: ");
							for (ResearchProject p : actualProjects) {
								System.out.println(p);
							}
						} else if (choice == 2) {
							System.out.println("Введите название проекта: ");
							in.nextLine();
							String text = in.nextLine();
							createProject(new ResearchProject(text));
							Data.getInstance().getLogs().add(new Log(initialUser.getId() + " создал научный проект " + text));
							System.out.println("Успешно добавлено");
						} else if (choice == 3) {
							System.out.println("Выберите существующий проект: ");
							int cnt = 0;
							Vector<ResearchProject> projectsForJoin = Data.getInstance().getResearchProjects().stream()
									.filter(n -> n.getParticipants().stream()
											.noneMatch(u -> u.equals(getInitialUser()))).collect(Collectors.toCollection(Vector::new));
							for (ResearchProject p : projectsForJoin) {
								System.out.println(++cnt + ") " + p);
							}
							System.out.println(++cnt + ") Вернуться назад");
							choice = in.nextInt();
							if (choice < 1 || choice >= cnt) continue projecting;
							ResearchProject actualProject = projectsForJoin.get(choice - 1);
							connectToProject(actualProject);
							Data.getInstance().getLogs().add(new Log(initialUser.getId() + " присоединился к научному проекту " + actualProject.getTopic()));
							System.out.println("Успешно присоеденены!");
						} else if (choice == 4) {
							continue menu;
						}
						System.out.println("1) Вернуться назад \n2) Вернуться в user-interface");
						choice = in.nextInt();
						if (choice == 1) continue menu;
						if (choice == 2) break;
					}
				} else if (choice == 4) {
					System.out.println("1) Создать новый журнал \n2) Вернуться назад");
					choice = in.nextInt();
					if (choice == 1) {
						System.out.println("Введите название журнала: ");
						in.nextLine();
						String text = in.nextLine();
						createNewJournal(new Journal(text));
						Data.getInstance().getLogs().add(new Log(initialUser.getId() + " создал новый журнал " + text));
						System.out.println("Вы успешно создали новый журнал " + text);
						continue menu;
					} else if (choice == 2) {
						continue menu;
					}
				} else if (choice == 5) {
					System.out.println("1) Посмотреть список для кого supervisor \n2) Стать supervisor \n3) Вернуться назад");
					choice = in.nextInt();
					Vector<GraduateStudent> students = Data.getInstance().getStudents().stream().filter(n -> n instanceof GraduateStudent)
							.map(g -> (GraduateStudent) g).collect(Collectors.toCollection(Vector::new));
					if (choice == 1) {
						students = students.stream().filter(n -> n.getSupervisor().equals(this)).collect(Collectors.toCollection(Vector::new));
						for (GraduateStudent g : students) {
							System.out.println(g);
						}
						System.out.println("1) Вернуться назад \n2) Вернуться в user-interface");
						choice = in.nextInt();
						if (choice == 1) continue menu;
						if (choice == 2) break;
					} else if (choice == 2) {
						students = students.stream().filter(n -> n.getSupervisor() == null).collect(Collectors.toCollection(Vector::new));
						int cnt = 0;
						System.out.println("Выберите студента: ");
						for (GraduateStudent g : students) {
							System.out.println(++cnt + ") " + g);
						}
						System.out.println(++cnt + ") Вернуться назад");
						choice = in.nextInt();
						if (choice < 1 || choice >= cnt) continue menu;
						GraduateStudent actualStudent = students.get(choice - 1);
						boolean canSupervisor = becomeSupervisor(actualStudent);
						System.out.println((canSupervisor ? "Вы успешно стали supervisor!" : "Произошла ошибка")
								+ "\n1) Вернуться назад \n2) Вернуться в user-interface");
						choice = in.nextInt();
						if (choice == 1) continue menu;
						if (choice == 2) break;
					} else if (choice == 3) {
						continue menu;
					}
				} else if (choice == 6) {
					break;
				}
			}
		} catch (Exception e){
			System.out.println("Error");
		}
	}
}



