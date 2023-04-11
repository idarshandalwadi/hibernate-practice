
It is an example of mapping one table field to another table field.

One to Many with @ElementColletion annotation.

The example is created with the java.util.TreeMap collection to store data into second table.

Here we can not store duplicate image data to the Database table, as we have used TreeMap(child of SortedMap interface).
(java.util.SortedMap(interface) stores data in key value pair, and allows to sort data based on given field. Ex: Student stored based on the Rank Number, Waiting list)
Ex: Suppose you want to retrieve error_code(key) and error_msg(value) based on error_code(key) sorting order.
======================
Use case in Example:
======================
A student will have a sorted Map of images(file names)
1. Each image will have file name and a description
2. There should not be duplicate images

====================
Code snippet:
====================
@ElementCollection
@CollectionTable(name = "image") // Name of the table
@MapKeyColumn(name = "file_name") // Will be the key for the map
@Column(name = "image_name") // Will be the value for the map
@OrderBy
private SortedMap<String, String> images = new TreeMap<>();

Note::	@OrderBy -> Elements will be retrieved based on the @MapKeyColumn annotation value in ASCENDING order.

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
Hibernate: insert into image (Student_id, file_name, image_name) values (?, ?, ?)


To get images from DB:
-------------------------------------------------------------
Hibernate: select student0_.id as id1_1_0_, student0_.email as email2_1_0_, student0_.first_name as first_na3_1_0_, student0_.last_name as last_nam4_1_0_ from student student0_ where student0_.id=?
Details of the student : Student [id=1, firstName=Darshan, lastName=Dalwadi, email=dd@gmail.com]
Hibernate: select images0_.Student_id as Student_1_0_0_, images0_.image_name as image_na2_0_0_, images0_.file_name as file_nam3_0_ from image images0_ where images0_.Student_id=? order by images0_.file_name desc
List of the student image : {photo1.jpg=photo1, photo2.jpg=photo2, photo3.jpg=photo3, photo4.jpg=photo4, photo5.jpg=photo5}


==============================================================
To sort data in Desc or custom order:
==============================================================
You can use org.hibernate.annotations.SortComparator to sort data based on requirements.

@ElementCollection
@CollectionTable(name = "image") // Name of the table
@MapKeyColumn(name = "file_name") // Will be the key for the map
@Column(name = "image_name") // Will be the value for the map
@SortComparator(value = ReverseStringComparator.class) // Sorting key in descending order
private SortedMap<String, String> images = new TreeMap<>();

// Below comparator used to get Data in descending order.
public static class ReverseStringComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return o2.compareTo(o1);
	}
}

Resulting data on console:
---------------------------------------
Hibernate: select images0_.Student_id as Student_1_0_0_, images0_.image_name as image_na2_0_0_, images0_.file_name as file_nam3_0_ from image images0_ where images0_.Student_id=?
List of the student image : {photo5.jpg=photo5, photo4.jpg=photo4, photo3.jpg=photo3, photo2.jpg=photo2, photo1.jpg=photo1}


