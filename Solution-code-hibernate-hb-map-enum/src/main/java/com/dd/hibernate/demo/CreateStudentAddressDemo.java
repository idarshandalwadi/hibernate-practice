package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Status;
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
			final Student student1 = new Student("Some", "One", "someone@gmail.com", Status.ACTIVE);
			final Student student2 = new Student("Any", "One", "anyone@gmail.com", Status.DEACTIVE);

			// Begin the transaction
			session.beginTransaction();

			// Save the object
			session.save(student1);
			session.save(student2);

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
