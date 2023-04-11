
This is an example of Mapping Enum to the database table field.
Enum is a special java Data type which is generally used to define the constants. Like types of employee can be, [Dev, QA, Manager]
======================
Use case in Example:
======================
A student will have a status field
	- Possible values : ACTIVE, DEACTIVE

====================
Code snippet:
====================
Create Enum:
---------------------------
public enum Status {
	ACTIVE, DEACTIVE;
}


Inside the Student class:
---------------------------
@Enumerated(EnumType.STRING)	// javax.persistence.Enumerated- Used to indicate Enum mapping. Enumtype.String -> Indicates the type of used Enum values.
@Column(name = "status")
private Status status;		// Status is a name of the Enum we have created.


DB Script:
-------------------------------------------------------------
For DB we have to just create database, tables will be created with the help of Hibernate.

Property:
<!-- Auto create table -->
<property name="hibernate.hbm2ddl.auto">create</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: insert into student (email, first_name, last_name, status) values (?, ?, ?, ?)

Result from DB:
-------------------------------------------------------------
student1 : Student [id=1, firstName=Some, lastName=One, email=someone@gmail.com, status=ACTIVE]

