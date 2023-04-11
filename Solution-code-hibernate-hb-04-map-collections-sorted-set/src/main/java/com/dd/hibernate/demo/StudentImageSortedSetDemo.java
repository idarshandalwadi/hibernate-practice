package com.dd.hibernate.demo;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Student;

public class StudentImageSortedSetDemo {

	public static void main(String[] args) {

		// Create a session factory
		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create a session
		final Session session = sessionFactory.openSession();

		try {
			// Create the object to store in DB
			final Student student = new Student("Darshan", "Dalwadi", "dd@gmail.com");
			final Set<String> images = student.getImages();
			images.add("photo1.jpg");
			images.add("photo2.jpg");
			images.add("photo3.jpg");
			images.add("photo4.jpg");
			images.add("photo4.jpg");
			images.add("photo5.jpg");

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
