package com.dd.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "INSTRUCTOR")
public class Instructor extends User {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "salary")
	private int salary;

	public Instructor() {
	}

	public Instructor(String firstName, String lastName, String email, String city, int salary) {

		super(firstName, lastName, email, city);
		this.salary = salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * @return the salary
	 */
	public int getSalary() {
		return salary;
	}

	@Override
	public String toString() {
		return super.toString() + ", [ salary=" + salary + "]";
	}

}
