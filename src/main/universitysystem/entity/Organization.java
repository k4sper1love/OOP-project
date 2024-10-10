package universitysystem.entity;


import java.io.Serializable;
import java.util.Objects;

//TODO добавить выбрать лидера если лидак ливает
/**
* Class organization is an organization in the university system, the head of which can be a student.
* @author 
* @version 1.0
*/
public class Organization implements Serializable
{
	private String name;
	private Student head;

	{
		this.head = null;
	}
    /**
     * Default constructor.
     */
	public Organization(){
		super();
	}
    /**
     * Constructor for  {@code Organization} class with specified specified name.
     * @param name The name of the organization.
     */
	public Organization(String name) {
		this.name = name;
	}
    /**
     * Constructor for {@code Organization} class with the specified name and head student.
     * @param name - name of the organization.
     * @param head - head student of the organization.
     */
	public Organization(String name,Student head) {
		this(name);
		this.head = head;
	}
    /**
     * Gets the name of the organization.
     * @return Name of the organization.
     */
	public String getName() {
		return this.name;
	}
    /**
     * Sets the name of the organization.
     * @param name The new name of the organization.
     */
	public void setName(String name) {
		this.name = name;
	}
    /**
     * Gets the head student of the organization.
     * @return The head student of the organization.
     */
	public Student getHead() {
		return head;
	}
    /**
     * Sets the head student of the organization.
     * @param head - New head student of the organization.
     */
	public void setHead(Student head) {
		this.head = head;
	}
    /**
     * Hash code for the organization.
     * @return Hash code for the organization.
     */

	@Override
	public int hashCode() {
		return Objects.hash(name, head);
	}
    /**
     * Performs a check whether any other object is equal to this one.
     * @param o using this object, a comparison is performed
     * @return {@code true} if this organization is the same as the {@code o} argument; {@code false} otherwise.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Organization that = (Organization) o;
		return name.equals(that.name);
	}
    /**
     * Returns a string representation of the organization, it has the name and ID of the main student.
     * @return Representation of the organization.
     */
	public String toString() {
		return "Organization: " + name + "\nHead: " + head.getId();
	}
	
}

