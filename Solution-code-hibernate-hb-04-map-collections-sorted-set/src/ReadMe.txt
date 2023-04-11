
It is an example of mapping one table field to another table field.

One to Many with @ElementColletion annotation.

The example is created with the java.util.LinkedHashSet collection to store data into second table.

Here we can not store duplicate image data to the Database table, as we have used LinkedHashSet.
(Sorted set(LinkedHashSet) does not allows duplicates, and allows to sort data based on given field, Ex: Student stored based on the Rank Number, Waiting list)

======================
Use case in Example:
======================
A student will have a set of images(file names)
1. So we want to retrieve images in alphabetic order (descending order)
2. There should not be duplicate images

====================
Code snippet:
====================
@ElementCollection //Used to define the collection Mapping
@CollectionTable(name = "image") 	// Name of the second table in which we want to store data.
@OrderBy(value = "file_name DESC") 	// Elements will be retrieved based on the file_name field in DESCENDING order.
@Column(name = "file_name")
private Set<String> images = new LinkedHashSet<>();

Syntax:
	@OrderBy([columnName] [ASC | DSC])	// By default its ASCDENING
(Is it same as "select * from TABLE order by field_name;")
If the ordering field is not specified, then ordering will be performed based on the primary key entity.

Note: It will create two columns in the image table:.
	1. For storing name of the file_name --> which is a key of Map
	2. For storing a name of the image --> which is value of Map


DB Script:
-------------------------------------------------------------
For DB we have to just create database, tables will be created with the help of Hibernate.

Property:
<!-- Auto update table -->
<!-- It creates a table if not exists, otherwise it will update the schema based on the java annotation -->
<property name="hibernate.hbm2ddl.auto">update</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: insert into student (email, first_name, last_name) values (?, ?, ?)
Hibernate: insert into image (Student_id, file_name) values (?, ?)

To get images from DB:
-------------------------------------------------------------
Hibernate: select images0_.Student_id as Student_1_0_0_, images0_.file_name as file_nam2_0_0_ from image images0_ where images0_.Student_id=? order by images0_.file_name desc
List of the student image : [photo5.jpg, photo4.jpg, photo3.jpg, photo2.jpg, photo1.jpg]

