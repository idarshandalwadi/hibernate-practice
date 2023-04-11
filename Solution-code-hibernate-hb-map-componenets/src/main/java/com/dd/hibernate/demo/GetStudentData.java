package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Address;
import com.dd.hibernate.entity.Student;

public class GetStudentData {

	public static void main(String[] args) {

		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.addAnnotatedClass(Address.class).buildSessionFactory();

		final Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		final Student student = session.get(Student.class, 2);

		System.err.println(student);
		System.err.println(student.getHomeAddress());
		System.err.println(student.getBillingAddress());

		session.getTransaction().commit();

		session.close();
		sessionFactory.close();
	}

}
