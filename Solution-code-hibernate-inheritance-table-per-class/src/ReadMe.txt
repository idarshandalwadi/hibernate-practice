Hibernate Table per class Mapping Strategy:
===============================================
In this strategy sub class/table contains its own fields as well as all the fields of super class/table.
As we have used Table per class mapping so we also need to change our Id generation strategy from Identity to TABLE.
Bcz now we have multiple tables in which we need to add value for ID, now the issue is how could we get next possible value for ID column.
Solution is - for that we need a table, which is by default create by Hibernate, table name will be "hibernate_sequence" and it will store the ID value.
So when we insert new record to any table it will get current ID from the table and will assign next possible value to inserting record,
this process done in a thread-safe manner.

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
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)	// Need to change id generation strategy to TABLE.
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
Hibernate: select user0_.id as id1_2_0_, user0_.city as city2_2_0_, user0_.email as email3_2_0_, user0_.first_name as first_na4_2_0_, user0_.last_name as last_nam5_2_0_, user0_.course as course1_1_0_, user0_.salary as salary1_0_0_, user0_.clazz_ as clazz_0_ from ( select id, city, email, first_name, last_name, null as course, null as salary, 0 as clazz_ from user union select id, city, email, first_name, last_name, course, null as salary, 1 as clazz_ from Student union select id, city, email, first_name, last_name, null as course, salary, 2 as clazz_ from Instructor ) user0_ where user0_.id=?
Hibernate: select student0_.id as id1_2_0_, student0_.city as city2_2_0_, student0_.email as email3_2_0_, student0_.first_name as first_na4_2_0_, student0_.last_name as last_nam5_2_0_, student0_.course as course1_1_0_ from Student student0_ where student0_.id=?
Hibernate: select instructor0_.id as id1_2_0_, instructor0_.city as city2_2_0_, instructor0_.email as email3_2_0_, instructor0_.first_name as first_na4_2_0_, instructor0_.last_name as last_nam5_2_0_, instructor0_.salary as salary1_0_0_ from Instructor instructor0_ where instructor0_.id=?

User : [firstName=Master, lastName=User, email=user@gmail.com, city=Ahmedabad]
Student : [firstName=BrillientStudent, lastName=One, email=student@gmail.com, city=Ahmedabad], [ course=Java]
Instructor : [firstName=PowerfulInstructor, lastName=One, email=instructor@gmail.com, city=Viramgam], [ salary=50000]
