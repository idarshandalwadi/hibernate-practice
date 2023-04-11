Hibernate Joined Table Mapping Strategy:
===============================================
This strategy basically working on the based on primary key and foriegn key.
In this strategy super class which has a common fields that will be used by sub-classes.
So super table has common columns, sub classes has only specific fields and the Id of the super class record.


======================
Use case in Example:
======================
There is a User(Super) class having some common fields
There is a Student(Sub) class having Student specific fields and Id from the
There is Instructor(Sub) class having Instructor specific fields

====================
Code changes:
====================
Inside the User(Super) class:
-----------------------------------
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// Will take advantage of Auto-increment feature of the DB.
	private int id;
}


Inside the Student/Instructor class:
---------------------------------------
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
Hibernate: create table Instructor (salary integer, id integer not null, primary key (id)) engine=InnoDB
Hibernate: create table Student (course varchar(255), id integer not null, primary key (id)) engine=InnoDB
Hibernate: create table user (id integer not null auto_increment, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB

Hibernate: alter table Instructor add constraint FKixr9k9sjl8jkcsx8m1vmid3ax foreign key (id) references user (id)
Hibernate: alter table Student add constraint FK8x1ew2nplc5965on36n4yplb7 foreign key (id) references user (id)

Hibernate: insert into user (city, email, first_name, last_name) values (?, ?, ?, ?)
Hibernate: insert into user (city, email, first_name, last_name) values (?, ?, ?, ?)
Hibernate: insert into Student (course, id) values (?, ?)
Hibernate: insert into user (city, email, first_name, last_name) values (?, ?, ?, ?)
Hibernate: insert into Instructor (salary, id) values (?, ?)


Result from DB:
-------------------------------------------------------------
Hibernate: select user0_.id as id1_2_0_, user0_.city as city2_2_0_, user0_.email as email3_2_0_, user0_.first_name as first_na4_2_0_, user0_.last_name as last_nam5_2_0_, user0_1_.course as course1_1_0_, user0_2_.salary as salary1_0_0_, case when user0_1_.id is not null then 1 when user0_2_.id is not null then 2 when user0_.id is not null then 0 end as clazz_0_ from user user0_ left outer join Student user0_1_ on user0_.id=user0_1_.id left outer join Instructor user0_2_ on user0_.id=user0_2_.id where user0_.id=?
Hibernate: select student0_.id as id1_2_0_, student0_1_.city as city2_2_0_, student0_1_.email as email3_2_0_, student0_1_.first_name as first_na4_2_0_, student0_1_.last_name as last_nam5_2_0_, student0_.course as course1_1_0_ from Student student0_ inner join user student0_1_ on student0_.id=student0_1_.id where student0_.id=?
Hibernate: select instructor0_.id as id1_2_0_, instructor0_1_.city as city2_2_0_, instructor0_1_.email as email3_2_0_, instructor0_1_.first_name as first_na4_2_0_, instructor0_1_.last_name as last_nam5_2_0_, instructor0_.salary as salary1_0_0_ from Instructor instructor0_ inner join user instructor0_1_ on instructor0_.id=instructor0_1_.id where instructor0_.id=?

User : [firstName=Master, lastName=User, email=user@gmail.com, city=Ahmedabad]
Student : [firstName=BrillientStudent, lastName=One, email=student@gmail.com, city=Ahmedabad], [ course=Java]
Instructor : [firstName=PowerfulInstructor, lastName=One, email=instructor@gmail.com, city=Viramgam], [ salary=50000]
