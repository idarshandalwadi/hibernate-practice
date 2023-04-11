
It is an example of mapping one table field to another table field.

One to Many with @ElementColletion annotation.

The example is created with the java.util.Map collection to store data into second table.

Here we can not store duplicate image data to the Database table, as we have used Map.
(When we want to store data in key-value pair and don't want duplication of data - use Map)
====================
Code snippet:
====================
@ElementCollection //Used to define the collection Mapping
@CollectionTable(name = "image") 		// Name of the second table in which we want to store data
@MapKeyColumn(name = "file_name") // Store as a Key in a Map
@Column(name = "image_name") // Store as a value in a Map
private Map<String, String> images = new HashMap<>();


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
Hibernate: insert into image (Student_id, file_name, image_name) values (?, ?, ?)

