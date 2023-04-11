package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Address;
import com.dd.hibernate.entity.Student;

public class CreateStudentAddressDemo {

	public static void main(String[] args) {

		// Create a session factory
		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create a session
		final Session session = sessionFactory.openSession();

		try {
			// Create the object to store in DB
			final Student student = new Student("Darshan", "Dalwadi", "dd@gmail.com");

			// Create address object for homeAddress and set into Student object
			final Address homeAddress = new Address("55, Sathwarafali", "Viramgam", "382150");
			student.setHomeAddress(homeAddress);

			// Create address object for billingAddress object and set into Student object
			final Address billingAddress = new Address("501, Nr.Munsar gate", "Ahmedabad", "380009");
			student.setBillingAddress(billingAddress);

			// Begin the transaction
			session.beginTransaction();

			// Save the object
			System.err.println("Stroing student object with images : " + student);
			session.save(student);

			// Commit the transaction
			session.getTransaction().commit();

		} catch (final Exception e) {
			e.printStackTrace();
			// Clean up the code
			session.close();
			sessionFactory.close();
		}
	}
}
