# BinaryWizards
## Online Book Shop Micro-Service Project

This project demonstrates the implementation of microservice using Spring Boot, Netflix Eureka Client and Security using Spring Boot and JSON Web Tokens (JWT).

<br>

## Features

* User registration and login with JWT authentication.
* Password encryption using BCrypt.
* Role-based (USER, ADMIN) authorization with Spring Security.
* Customized access denied handling.
* See all books.
* Do crud operations for books.
* Buy books.

<br>

## Technologies

* Spring Boot
* Spring Security
* JPA
* Netflix Eureka
* JSON Web Tokens (JWT)
* BCrypt
* Gradle
* MySQL

<br>

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Gradle
* MySQL driver

<br>

To build and run the project, follow these steps:

* Login to mySQL driver with user "root" and password "root"
* Add database "authentication_service_db", "book_service_db" and "book_inventory_db" to mySQL
* Set the path of configRepo in application.properties in configServer
* Run the project

The application will run at http://localhost:8080

<br>

## Functions 

| Mapping | Function          | URL                                           | Permission  | Request Body Input                                          |
|---------|-------------------|-----------------------------------------------|-------------|-------------------------------------------------------------|
| POST    | User Registration | http://localhost:8080/auth-server/register    | ALL         | firstName, lastName, phone, address, email, password, roles |
| POST    | User Login        | http://localhost:8080/auth-server/login       | ALL         | email, password                                             |
| GET     | View All Books    | http://localhost:8080/book-server/book/all    | ADMIN, USER |                                                             |
| GET     | View One Book     | http://localhost:8080/book-server/book/{id}   | ADMIN, USER |                                                             |
| POST    | Create One Book   | http://localhost:8080/book-server/create      | ADMIN       | bookName, authorName, genre, price, quantity                |
| PATCH   | Update One Book   | http://localhost:8080/book-server/update/{id} | ADMIN       | bookName, authorName, genre, price, quantity                |
| DELETE  | Delete One Book   | http://localhost:8080/book-server/delete/{id} | ADMIN       |                                                             |
| PATCH   | Buy One Book      | http://localhost:8080/book-server/book/buy    | USER        | bookId, quantity                                            |

<br>

## Contributors

| Name                 | Email                            |
|----------------------|----------------------------------|
| Md. Mehedi Hassan    | mdmehedi.hassan@bjitacademy.com  |
| Barha Meherun Pritha | meherun.pritha@bjitacademy.com   |
| Md. Taslim           | md.taslim@bjitacademy.com        |
| Arup Chakraborty     | arup.chakraborty@bjitacademy.com |

<br>
