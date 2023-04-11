package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Student;

public class GetStudentIamges {

	public static void main(String[] args) {

		// Create a session factory
		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Get session
		final Session session = sessionFactory.getCurrentSession();

		try {

			// Begin the transaction
			session.beginTransaction();

			// Get student data
			final Student student = session.get(Student.class, 1); // 1 is an ID of the Student
			System.err.println("Details of the student : " + student);
			System.err.println("List of the student image : " + student.getImages());

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
