/**
 *
 */
package com.dd.hibernate.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SortComparator;

/**
 * @author darshandalwadi
 *
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@ElementCollection
	@CollectionTable(name = "image") // Name of the table
	@MapKeyColumn(name = "file_name") // Will be the key for the map
	@Column(name = "image_name") // Will be the value for the map
//	@OrderBy // Elements will be retrieved based on the @MapKeyColumn annotation value in ASC order.
	@SortComparator(value = DescendingOrderComparator.class) // Sorting key in descending order
	private SortedMap<String, String> images = new TreeMap<>();

	public Student() {
	}

	// Custom comparator to get value in Descending order (Here we are sorting key
	// in descending order)
	public static class DescendingOrderComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o2.compareTo(o1);
		}
	}

	public Student(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the images
	 */
	public SortedMap<String, String> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(SortedMap<String, String> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}
