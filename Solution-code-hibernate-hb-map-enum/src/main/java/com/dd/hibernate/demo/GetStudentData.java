package com.dd.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dd.hibernate.entity.Student;

public class GetStudentData {

	public static void main(String[] args) {

		final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Student.class)
				.buildSessionFactory();

		final Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		final Student student1 = session.get(Student.class, 1);
		final Student student2 = session.get(Student.class, 2);

		System.out.println("student1 : " + student1);
		System.out.println("student2 : " + student2);

		session.getTransaction().commit();

		session.close();
		sessionFactory.close();
	}

}
