package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Instructor;
import com.dd.hibernate.entity.Student;
import com.dd.hibernate.entity.User;

public class HibernateSIngleTableInheritanceDemo {

	public static void main(String[] args) {

		// Create a session factory
		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(User.class)
				.addAnnotatedClass(Student.class).addAnnotatedClass(Instructor.class).buildSessionFactory();

		// Create a session
		final Session session = sessionFactory.openSession();

		try {
			// Create the object to store in DB
			final User user = new User("Master", "User", "user@gmail.com", "Ahmedabad");
			final Student student = new Student("BrillientStudent", "One", "student@gmail.com", "Ahmedabad", "Java");
			final Instructor instructor = new Instructor("PowerfulInstructor", "One", "instructor@gmail.com",
					"Viramgam", 50000);

			// Begin the transaction
			session.beginTransaction();

			// Save the objects
			session.save(user);
			session.save(student);
			session.save(instructor);

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
