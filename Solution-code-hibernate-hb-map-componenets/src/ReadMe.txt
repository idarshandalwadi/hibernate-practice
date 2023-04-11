
This is an example of including one table fields to another table.

For reference:
https://www.baeldung.com/jpa-embedded-embeddable

======================
Use case in Example:
======================
A student will have a address data(Address object)
We will create 2 pojo, 
	one is entity with all the standard of Hibernate Entity.(Id, column, etc...)
	second has only column mapping, no primary key, as it will be used whenever we need to add extra fields.

Note:It is kind of extending one class in other, but it is recommended to use @Emabadded instead of Extending a class.

====================
Code snippet:
====================
Inside the Address class:
---------------------------
@Embeddable		// This annotation used to indicate that below class (Address) can be included/embedded to any other entities.
public class Address { ........ }


Inside the Student class:
---------------------------
@Embedded 	// It is optional here, as we have annotated Address class with @Embeddable annotation.
private Address homeAddress;
// + Getters & Setters


DB Script:
-------------------------------------------------------------
For DB we have to just create database, tables will be created with the help of Hibernate.

Property:
<!-- Auto create table -->
<property name="hibernate.hbm2ddl.auto">create</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: insert into student (email, first_name, city, street, zipcode, last_name) values (?, ?, ?, ?, ?, ?)
(Will add fields/columns of the address class to the Student class/Table)

In below format data will be stored inside DB.
==============================================================
id,	 	email, 		 		first_name, 	city, 			street, 				zipcode, 		last_name
-------------------------------------------------------------------------------------------------------------
'1', 	'dd@gmail.com', 	'Darshan', 		'Viramgam', 	'55, Sathwarafali', 	'382150', 		'Dalwadi'


======================================================================================
Reuse the same pojo of Adress as a Billing Address for the student pojo.
======================================================================================

Suppose you have the same fields in more than 2 pojo, and you want to store both in one table,
So you can Overrides the attributes of the @Emabeddable class like below.

Requirement: We want homeAddress as well as billingAddress both in a single table name student.

Inside the Student class:
---------------------------------
@Embedded
// It is optional here, as we have annotated Address class with @Embeddable annotation.
private Address homeAddress;

@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
		@AttributeOverride(name = "city", column = @Column(name = "billing_city")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "billing_zipcode")) })
private Address billingAddress;


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: insert into student (billing_city, billing_street, billing_zipcode, email, first_name, city, street, zipcode, last_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?)
(Will add total 6 fields/columns to the Student class/Table like, city, street, zipcode & billing_city, billing_street, billing_zipcode)

id, 	email, 			first_name, 	city, 		street, 			zipcode, 	last_name, 	billing_city, 	billing_street, 		billing_zipcode
--------------------------------------------------------------------------------------------------------------------------------------------------------
'2', 	'dd@gmail.com', 'Darshan', 		'Viramgam', '55, Sathwarafali', '382150', 	'Dalwadi', 	'Ahmedabad', 	'501, Nr.Munsar gate', 	'380009'

