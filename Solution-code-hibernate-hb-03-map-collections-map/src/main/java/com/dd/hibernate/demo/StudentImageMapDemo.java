package com.dd.hibernate.demo;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Student;

public class StudentImageMapDemo {

	public static void main(String[] args) {

		// Create a session factory
		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create a session
		final Session session = sessionFactory.openSession();

		try {
			// Create the object to store in DB
			final Student student = new Student("Kishan", "Dalwadi", "kd@gmail.com");
			final Map<String, String> images = student.getImages();
			images.put("photo1.jpg", "photo1");
			images.put("photo2.jpg", "photo2");
			images.put("photo3.jpg", "photo3");
			images.put("photo4.jpg", "photo4");
			images.put("photo4.jpg", "photo4");
			images.put("photo5.jpg", "photo5");

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
