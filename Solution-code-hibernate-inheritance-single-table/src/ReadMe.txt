Hibernate Single Table Mapping Strategy:
===============================================
In this strategy only single table is there in DB, contains all the fields from super class as well as from all the subclasses.
While inserting record extra field will remain empty/null.

======================
Use case in Example:
======================
There is a User(Super) class having some common fields
There is a Student(Sub) class having Student specific fields
There is Instructor(Sub) class having Instructor specific fields

====================
Code changes:
====================
Inside the User(Super) class:
-----------------------------------
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "USER_TYPE")
public class User implements Serializable { .... }


Inside the Student/Instructor class:
---------------------------------------
@Entity
@DiscriminatorValue(value = "INSTRUCTOR")
public class Instructor extends User { .... }

Note: You also need to register class with sessionFactory like addAnnotatedClass(Instructor.class)

============================================================
Property changes for DB operations:
============================================================
For DB we have to just create database, tables will be created with the help of Hibernate.

Property changes:
<!-- Auto create/update table -->
<property name="hibernate.hbm2ddl.auto">update</property>

<!-- Need to increase connection pool size as we need to fetch sequence data as well -->
<property name="connection.pool_size">10</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: create table hibernate_sequences (sequence_name varchar(255) not null, next_val bigint, primary key (sequence_name)) engine=InnoDB
Hibernate: insert into hibernate_sequences(sequence_name, next_val) values ('default',0)
Hibernate: create table Instructor (id integer not null, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), salary integer, primary key (id)) engine=InnoDB
Hibernate: create table Student (id integer not null, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), course varchar(255), primary key (id)) engine=InnoDB
Hibernate: create table user (id integer not null, city varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
Hibernate: select tbl.next_val from hibernate_sequences tbl where tbl.sequence_name=? for update
Hibernate: update hibernate_sequences set next_val=?  where next_val=? and sequence_name=?
Hibernate: select tbl.next_val from hibernate_sequences tbl where tbl.sequence_name=? for update
Hibernate: update hibernate_sequences set next_val=?  where next_val=? and sequence_name=?
Hibernate: select tbl.next_val from hibernate_sequences tbl where tbl.sequence_name=? for update
Hibernate: update hibernate_sequences set next_val=?  where next_val=? and sequence_name=?
Hibernate: insert into user (city, email, first_name, last_name, id) values (?, ?, ?, ?, ?)
Hibernate: insert into Student (city, email, first_name, last_name, course, id) values (?, ?, ?, ?, ?, ?)
Hibernate: insert into Instructor (city, email, first_name, last_name, salary, id) values (?, ?, ?, ?, ?, ?)


Result from DB:
-------------------------------------------------------------
User : [firstName=Master, lastName=User, email=user@gmail.com, city=Ahmedabad]
Student : [firstName=BrillientStudent, lastName=One, email=student@gmail.com, city=Ahmedabad], [ course=Java]
Instructor : [firstName=PowerfulInstructor, lastName=One, email=instructor@gmail.com, city=Viramgam], [ salary=50000]
