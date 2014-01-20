blogr
=====

a simple blogging app that allows users to add, edit, and view blog posts.

the posts are done using [Creole syntax](http://www.wikicreole.org/wiki/CheatSheet)

## Overview

Technologies used:

### Backend
* Servlets 3.0
* Hibernate (using JPA)
* Spring (Injections, Web MVC, Spring REST)

### Frontend
* AngularJS
* Sass

## Building
Build is done using Maven.
Just run `mvn clean install` in the project directory

## Testing

### Backend
Testing is done in two phases: (in the corresponding Maven phases)
- `test`: Tests the Controller routing - using Spring Mock MVC and Mockito
- `integration-test`: Runs an embedded Tomcat instance on port `8888`, configures JPA to use HSQLDB memory DB, and tests the REST URLs

### Frontend
Tests using Jasmine and Angular Mock

## Running

#### Target Platforms
Container: Any Servlet 3.0 Container: Tomcat 7, GlassFish v3, Jetty 8

RDBMS: Any Hibernate supported DB - the main `persistence.xml` is built for MySQL but you can modify it for a different one.

Make sure to update the main `persistence.xml` with your schema name and credentials.

At the moment I only tested this on Tomcat 7 and MySQL

#### Deployment

During the Maven install Maven tries to deploy the WAR to a running Tomcat server on port 8080.
To allow the deploy you must enable the Tomcat deploy user by adding the following entries to the `tomcat-users.xml` in the config folder:
You can choose you own user/password and update it in the project POM in the `tomcat7-maven-plugin` section

After deployment the app is available at [http://localhost:8080/blogr](http://localhost:8080/blogr)

```xml
 <role rolename="manager-script"/>
 <role rolename="manager-gui"/>
 <user username="admin" password="admin" roles="manager-gui,manager-script"/>
```
Before starting the server make sure to create the Posts Table in your RDBMS using this SQL Create:

```sql
 CREATE TABLE 'posts' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'title' varchar(50) NOT NULL,
  'content' varchar(5000) DEFAULT NULL,
  'author' varchar(100) DEFAULT NULL,
  'creation_time' datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
```
