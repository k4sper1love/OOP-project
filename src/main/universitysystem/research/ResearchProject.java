package universitysystem.research;

import universitysystem.interfaces.CanCreate;

import universitysystem.interfaces.CanResearch;

import java.io.Serializable;
import java.util.*;
//TODO Alzhan compareTo добавить
/**
 * Сlass {@code ResearchProject} presents a research project in the university system.
 * implements the {@code Comparable} interface for sorting projects based on their IDs.
 * Interacts with research papers and project participants.
 *
 * @author 
 * @version 1.0
 */
public class ResearchProject implements Comparable<ResearchProject>, Serializable
{
	private int id;
	private static int nextId = 1;
	private String topic = "";
	private HashSet<ResearchPaper> publishedPapers;
	private HashSet<CanResearch> projectParticipants;
    /**
     * Constructs {@code ResearchProject} with a unique ID and collections.
     */
	public ResearchProject(){
		this.id = nextId++;
		this.publishedPapers = new HashSet<>();
		this.projectParticipants = new HashSet<>();
	}
    /**
     * Creates a new {@code ResearchProject} instance with a unique ID and a specified topic.
     * @param topic of the research project.
     */
	public ResearchProject(String topic){
		this.id = nextId++;
        this.publishedPapers = new HashSet<>();
        this.projectParticipants = new HashSet<>();
		this.topic = topic;
	}
    /**
     * ID of the research project.
     * @return ID of the research project.
     */	
	public int getId() {
		return id;
	}
    /**
     * Sets the topic.Topic of the research project.
     * @param topic The new topic to set.
     */
	public void setTopic(String topic) {
		// TODO implement me	
		this.topic=topic;
	}
    /**
     * Gets the topic of the research project.
     * @return Topic of the research project.
     */
	public String getTopic() {
		// TODO implement me
		return topic;	
	}

    /**
     * Collects a collection of research papers related to the project.
     * @return  {@code HashSet} of {@code ResearchPaper}.
     * @see ResearchPaper
     */
	public HashSet<ResearchPaper> getPapers() {
		return publishedPapers;
	}
    /**
     * Adds a research paper to the project.
     * @param p Research paper to add.
     * @return {@code true} if the paper is successfully added, {@code false} otherwise.
     */
	public boolean addPaper(ResearchPaper p) {
		return publishedPapers.add(p);	
	}
    /**
     * Removes a research paper from the project.
     * @param p The research paper to remove.
     * @return {@code true} if the paper is successfully removed, {@code false} otherwise.
     */
	public boolean removePaper(ResearchPaper p) {
		// TODO проверить не пустой ли хешсет
	    if (publishedPapers != null && p != null) {
	        return publishedPapers.remove(p);
	    }
	    return false;
	}
    /**
     * Projects with a list of participants.Gets the set of participants 
     * @return A {@code HashSet} of participants implementing {@code CanResearch}.
     */
	public HashSet<CanResearch> getParticipants() {
		return projectParticipants;
	}

	// Custom Exception?
    /**
     * Adds a participant to the project.
     * @param c Participant to add.
     * @return {@code true} if the participant is successfully added, {@code false} otherwise.
     */
	public boolean addParticipant(CanResearch c) {
		return projectParticipants.add(c);
	}
    /**
     * Removes a participant from the project.
     * @param c Participant to remove.
     * @return {@code true} if the participant is successfully removed, {@code false} otherwise.
     */
	public boolean removeParticipant(CanCreate c) {
		// TODO проверить не пустой ли
	    if (projectParticipants != null && c != null) {
	        return projectParticipants.remove(c);
	    }
	    return false;
	}
    /**
     * Performs a check if this research project is equal to the specified object.
     * @param o The object to compare with this research project.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	public boolean equals(Object o) {
		// TODO implement me
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResearchProject that = (ResearchProject) o;

        return topic.equals(that.topic);	
	}
    /**
     * Hash code for this research project.
     * @return Hash code value for this research project.
     */
	@Override
	public int hashCode() {
		return Objects.hash(id, topic, publishedPapers, projectParticipants);
	}

	/**
     * Returns a string representation of this research project.
     * @return Representation of the research project.
     */
	public String toString() {
		// TODO Переделать, сделать более читабельным для вывода
		return "Research project number " + id + " one the topic " + topic
				+ " contains these" + publishedPapers
				+ " was written by " + projectParticipants;
	}
    /**
     * Performs a comparison of this research project with another using their identifiers.
     * @param o Research project to compare with.
     * @return returned whether this project is less than, equal to, or greater than the specified project, respectively..
     */
    public int compareTo(ResearchProject o) {
        return Integer.compare(this.id, o.id);
    }
	
}




