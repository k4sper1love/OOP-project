package universitysystem.research;
import universitysystem.interfaces.CanResearch;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class {@code ResearchPaper} presents a research paper within the system of university.
 * Implements the {@code Comparable} interface,which provides sorting of articles by their identifiers.
 * Class is interconnected with authors, citations and pages.
 * @author 
 * @version 1.0
 */


public class ResearchPaper implements Comparable<ResearchPaper>, Serializable
{
	private static int nextId = 1;
	private int id;
	private String title;
	private HashSet<CanResearch> authors;
	private Vector<String> citations;
	private LinkedHashSet<Page> pages;
	private final Date date;
    /**
     * Constructs {@code ResearchPaper} with a unique ID, title,collections and release date .
     * @param title The title of the research paper.
     */
	public ResearchPaper(String title){
		super();
		this.id = nextId++;
        this.title = title;
        this.authors = new HashSet<>();
        this.citations = new Vector<>();
        this.pages = new LinkedHashSet<>();
        this.date = new Date();
	}
    /**
     * Gets the ID of the research paper.
     * @return ID of the research paper.
     */
	public int getId() {
		return id;	
	}
    /**
     * Gets the release date of the research paper.
     * @return Release date of the research paper.
     */
    public Date getDate() {
        return date;
    }
    public String getTitle(){
        return title;
    }
    /**
     * Gets citations from a research paper, taking into account the specified format..
     * @param f is format of the citations (PLAIN_TEXT or BIBTEX).
     * @return formatted citations as a string.
     */
	public String getCitations(Format f) {
        if (f == Format.PLAIN_TEXT) {
            return String.join("~ ", citations);
        } else if (f == Format.BIBTEX) {
            return citations.stream().map(c -> "@article{" + c + ",\n "
					+ "author = " + this.getAuthors() + "\n "
					+ "title = " + this.title + "\n "
					+ "year = " + this.date.getYear() + 1900 + "\n "
					+ "number = " + id + "\n "
					+ "pages = " + pages + "\n "
					+ "month = " + date.getMonth() + "\n"
					+ "}")
					.collect(Collectors.joining("~ "));
        } else {
        	 System.out.println("Warning: Unknown format, returning default value for format: " + f);
        	 return "";
        }
		//@article{article,
		//  author  = {Peter Adams},
		//  title   = {The title of the work},
		//  journal = {The name of the journal},
		//  year    = 1993,
		//  number  = 2,
		//  pages   = {201-213},
		//  month   = 7,
		//  note    = {An optional note},
		//  volume  = 4
		//}
	}

    /**
     * Gets the list of citations.
     * @return A list of citations as strings.
     */
    public List<String> getCitations() {
        return citations;
    }
    /**
     * Adds a citation to the research paper.
     * @param s is citation to add.
     * @return {@code true} if the citation is successfully added, {@code false} otherwise.
     */
	public boolean addCitation(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        citations.add(s);
        return true;	
	}
    /**
     * Removes a citation from the research paper.
     * @param s The citation to remove.
     * @return {@code true} if the citation is successfully removed, {@code false} otherwise.
     */
	public boolean removeCitation(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return citations.remove(s);
	}
    /**
     * Gets set of authors associated with the research paper.
     * @return {@code HashSet} of authors implementing {@code CanResearch}.
     */
	public HashSet<CanResearch> getAuthors() {
		return authors;
	}
    /**
     * Adds an author to the research paper.
     * @param author The author to add.
     * @return {@code true} if the author is successfully added, {@code false} otherwise.
     */
	public boolean addAuthor(CanResearch author) {
        if (author == null) {
            return false;
        }
        authors.add((CanResearch) author);
        return true;
	}
    /**
     * Removes the author from the list of authors of the research paper.
     * @param author The author to remove.
     * @return {@code true} if the author is successfully removed, {@code false} otherwise.
     */
	public boolean removeAuthor(CanResearch author) {
	    if (author == null) {
	        return false;
	    }
	    return authors.remove(author);
	}
    /**
     * Gets the set of pages associated with the research paper.
     * @return {@code LinkedHashSet} of pages.
     * @see Page
     */
	public LinkedHashSet<Page> getPages() {
		// TODO изменить, должен возвращать существующий
		return pages;
	}
    /**
     * Adds a page to the research paper.
     * @param p is page to add.
     * @return {@code true} if the page is successfully added, {@code false} otherwise.
     */
	public boolean addPage(Page p) {
        if (p == null) {
            return false;
        }
        pages.add(p);
        return true;
	}
    /**
     * Removes a page from the research paper.
     * @param p is page to remove.
     * @return {@code true} if the page is successfully removed, {@code false} otherwise.
     */
	public boolean removePage(Page p) {
	    if (pages != null && pages.contains(p)) {
	        return pages.remove(p);
	    }
	    return false;
	}
    /**
     * Checks if this research paper is equal to the specified object.
     * @param o The object to compare with this research paper.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(id, that.id) ||
				(Objects.equals(title, that.title) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(date, that.date));
	}
    /**
     * Hash code for this research paper.
     * @return Hash code value for this research paper.
     */
    public int hashCode() {
        return Objects.hash(id);
    }
    /**
     * Returns a string representation of this research paper.
     * @return Representation of the research paper.
     */
	public String toString() {
		// TODO implement me сделать не date а LocalDate
	    return "ResearchPaper number " + id +
				" and the title " +  title +
				" written by the " + authors +
				" contains " + pages +
				" pages and was released on " + date;
	}
    /**
     * Compares this research paper to another research paper based on their IDs.
     * @param o The research paper to compare with.
     * @return returned whether this project is less than, equal to, or greater than the specified project, respectively.
     */
    public int compareTo(ResearchPaper o) {
        return Integer.compare(this.id, o.id);
    }
}



