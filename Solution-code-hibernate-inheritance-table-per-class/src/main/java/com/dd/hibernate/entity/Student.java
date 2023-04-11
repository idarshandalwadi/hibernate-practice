/**
 *
 */
package com.dd.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author darshandalwadi
 *
 */
@Entity
public class Student extends User {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "course")
	private String course;

	public Student() {
	}

	public Student(String firstName, String lastName, String email, String city, String course) {

		super(firstName, lastName, email, city);
		this.course = course;
	}

	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	@Override
	public String toString() {
		return super.toString() + ", [ course=" + course + "]";
	}

}
