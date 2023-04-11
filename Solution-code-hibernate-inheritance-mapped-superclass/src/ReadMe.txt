Hibernate Mapped Super Class Mapping Strategy:
===============================================
In this strategy, only sub-class is considered as a table, each table contains fields of own class and its super class.
The supper class is not mapped/considered as separate table.
Only subclasses have used @Entity so only subclass has tables in DB.
No joins or inheritance exists in DB schema, it olny exists in the Java code.

======================
Use case in Example:
======================
There is a User(Super) class having some common fields.
There is a Student(Sub) class having Student specific fields. (Id of parent will be added by the hibernate while storing to DB).
There is Instructor(Sub) class having Instructor specific fields. (Id of parent will be added by the hibernate while storing to DB).

====================
Code changes:
====================
Inside the User(Super) class: //Will not remove any other annotations except @Entity, @Inheritance.
-----------------------------------
@MappedSuperclass
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// Will take advantage of Auto-increment feature of the DB.
	private int id;
}


Inside the Student/Instructor (sub)classes:
--------------------------------------------
@Entity
public class Instructor extends User { .... }

Note: You also need to register class with sessionFactory like addAnnotatedClass(Instructor.class)

============================================================
Property changes for DB operations:
============================================================
For DB we have to just create database, tables will be created with the help of Hibernate.

Property changes:
<!-- Auto create/update table -->
<property name="hibernate.hbm2ddl.auto">update</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: create table Instructor (id integer not null auto_increment, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), salary integer, primary key (id)) engine=InnoDB
Hibernate: create table Student (id integer not null auto_increment, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), course varchar(255), primary key (id)) engine=InnoDB

Hibernate: insert into Student (city, email, first_name, last_name, course) values (?, ?, ?, ?, ?)
Hibernate: insert into Instructor (city, email, first_name, last_name, salary) values (?, ?, ?, ?, ?)

Result from DB:
-------------------------------------------------------------
Hibernate: select student0_.id as id1_1_0_, student0_.city as city2_1_0_, student0_.email as email3_1_0_, student0_.first_name as first_na4_1_0_, student0_.last_name as last_nam5_1_0_, student0_.course as course6_1_0_ from Student student0_ where student0_.id=?
Hibernate: select instructor0_.id as id1_0_0_, instructor0_.city as city2_0_0_, instructor0_.email as email3_0_0_, instructor0_.first_name as first_na4_0_0_, instructor0_.last_name as last_nam5_0_0_, instructor0_.salary as salary6_0_0_ from Instructor instructor0_ where instructor0_.id=?

Student : [firstName=BrillientStudent, lastName=One, email=student@gmail.com, city=Ahmedabad], [ course=Java]
Instructor : [firstName=PowerfulInstructor, lastName=One, email=instructor@gmail.com, city=Viramgam], [ salary=50000]

