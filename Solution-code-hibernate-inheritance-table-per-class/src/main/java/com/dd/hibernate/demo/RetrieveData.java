package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Instructor;
import com.dd.hibernate.entity.Student;
import com.dd.hibernate.entity.User;

public class RetrieveData {

	public static void main(String[] args) {

		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.addAnnotatedClass(Instructor.class).buildSessionFactory();

		final Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		final User user = session.get(User.class, 1);
		final Student student = session.get(Student.class, 2);
		final Instructor instructor = session.get(Instructor.class, 3);

		System.out.println("User : " + user);
		System.out.println("Student : " + student);
		System.out.println("Instructor : " + instructor);

		session.getTransaction().commit();

		session.close();
		sessionFactory.close();
	}
}
