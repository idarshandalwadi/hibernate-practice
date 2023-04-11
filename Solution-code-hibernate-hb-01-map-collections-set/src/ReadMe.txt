
It is an example of mapping one table field to another table field.

One to Many with @ElementColletion annotation.

The example is created with the java.util.Set collection to store data into second table.

Here we can not store duplicate image data to the table, as we have used Set.
(care about duplication and dont want to perserve insertion order - use Set)
====================
Code snippet:
====================
@ElementCollection //Used to define the collection Mapping
@CollectionTable(name = "image", 		// Name of the second table in which we want to store data
	joinColumns = @JoinColumn(name = "student_id")		// Column name of the second table which maps with the id of the first table.
)
@Column(name = "file_name")		// Name of the column of second table which stores the data.
private Set<String> images = new HashSet<>();


DB Script:
-------------------------------------------------------------
CREATE DATABASE  IF NOT EXISTS `hb_student_tracker`;
USE `hb_student_tracker`;
--
-- Table structure for tables `student` and `image`
--

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `student_id` int(11) NOT NULL,
  `file_name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
