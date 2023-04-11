
It is an example of mapping one table field to another table field.

One to Many with @ElementColletion annotation.

The example is created with the java.util.List collection to store data into second table.

Here we can store duplicate image data to the Database table, as we have used List.
(When we dont care about duplication and want to perserve insertion order - use List)
====================
Code snippet:
====================
@ElementCollection //Used to define the collection Mapping
@CollectionTable(name = "image", 		// Name of the second table in which we want to store data
	joinColumns = @JoinColumn(name = "student_id")		// Column name of the second table which maps with the id of the first table.
)
@@OrderColumn(name="images_order")   // Will create a column in the table to store the sequence of data
@Column(name = "file_name")		// Name of the column of second table which stores the data.
private List<String> images = new ArrayList<>();

Note: It will create one more column in the image table to store order of the images.
Column name will be like 'nameOfTheField_ORDER'. Ex: images_order --> You can give it any name you want like @OrderColumn(name = "my_order").


DB Script:
-------------------------------------------------------------
For DB we have to just create database, tables will be created with the help of Hibernate.

Property:
<!-- Auto create table -->
<!-- It creates a table if not exists, otherwise it will update the schema based on the java annotation -->
<property name="hibernate.hbm2ddl.auto">update</property>


It will execute below SQL Script in background:
-------------------------------------------------------------
Hibernate: insert into student (email, first_name, last_name) values (?, ?, ?)
Hibernate: insert into image (student_id, images_ORDER, file_name) values (?, ?, ?)


